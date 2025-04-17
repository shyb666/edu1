package com.sky.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky.dto.MessageDTO;
import com.sky.entity.SessionBean;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class MyWsHandler extends AbstractWebSocketHandler {

    private static Map<String, SessionBean> sessionBeanMap; // key: sessionId, value: SessionBean
    private static Map<Integer, String> userSessionMap; // key: userId, value: sessionId
    private static AtomicInteger clientIdMaker;

    static {
        sessionBeanMap = new ConcurrentHashMap<>();
        userSessionMap = new ConcurrentHashMap<>();
        clientIdMaker = new AtomicInteger(0);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        SessionBean sessionBean = new SessionBean(clientIdMaker.getAndIncrement(), session);
        sessionBeanMap.put(session.getId(), sessionBean);
        log.info("客户端 " + sessionBean.getClientId() + " 创建了连接");

        // 从 URL 查询参数中获取 userId
        String query = session.getUri().getQuery();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                if (param.startsWith("userId=")) {
                    String userId = param.substring("userId=".length());
                    log.info("接收 userId: " + userId);
                    if (!userId.isEmpty()) {
                        userSessionMap.put(Integer.parseInt(userId), session.getId());
                        log.info("用户 " + userId + " 已连接到会话 " + session.getId());
                    }
                    break;
                }
            }
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        String payload = message.getPayload();
        int clientId = sessionBeanMap.get(session.getId()).getClientId();

        // 解析消息
        MessageDTO messageDTO = parseMessage(payload);
        if (messageDTO == null) {
            log.error("消息格式错误: " + payload);
            session.sendMessage(new TextMessage("消息格式错误"));
            return;
        }

        log.info("客户端 " + clientId + " 发送了消息: " + payload);

        // 获取发送者ID
        Integer senderId = messageDTO.getSenderId();
        if (senderId == null) {
            senderId = getUserIdBySessionId(session.getId());
        }

        // 发送私聊消息
        boolean sendSuccess = sendPrivateMessage(
                messageDTO.getReceiverId(),
                senderId+":"+messageDTO.getContent()
        );

        // 如果发送失败，通知发送者
        if (!sendSuccess && senderId != null) {
            String senderSessionId = userSessionMap.get(senderId);
            if (senderSessionId != null) {
                WebSocketSession senderSession = sessionBeanMap.get(senderSessionId).getWebSocketSession();
                if (senderSession != null && senderSession.isOpen()) {
                    senderSession.sendMessage(new TextMessage("对方不在线"));
                }
            }
        }
    }

    /**
     * 发送私聊消息
     * @param receiverId 接收者ID
     * @param message 消息内容
     * @return 是否发送成功
     */
    private boolean sendPrivateMessage(int receiverId, String message) throws IOException {
        String receiverSessionId = userSessionMap.get(receiverId);
        if (receiverSessionId != null) {
            WebSocketSession receiverSession = sessionBeanMap.get(receiverSessionId).getWebSocketSession();
            if (receiverSession != null && receiverSession.isOpen()) {
                receiverSession.sendMessage(new TextMessage(message));
                log.info("消息已发送给用户 " + receiverId);
                return true;
            } else {
                log.warn("用户 " + receiverId + " 的会话已关闭");
                return false;
            }
        } else {
            log.warn("用户 " + receiverId + " 未找到对应的会话");
            return false;
        }
    }

    /**
     * 根据sessionId获取用户ID
     */
    private Integer getUserIdBySessionId(String sessionId) {
        return userSessionMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(sessionId))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    private MessageDTO parseMessage(String payload) {
        try {
            return new ObjectMapper().readValue(payload, MessageDTO.class);
        } catch (Exception e) {
            log.error("解析消息失败: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        if (session.isOpen()) {
            session.close();
        }
        removeSession(session.getId());
        log.error("传输异常: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        removeSession(session.getId());
        log.info("客户端 " + sessionBeanMap.get(session.getId()).getClientId() + " 关闭了连接");
    }

    /**
     * 移除会话和相关映射
     */
    private void removeSession(String sessionId) {
        SessionBean sessionBean = sessionBeanMap.remove(sessionId);
        if (sessionBean != null) {
            userSessionMap.values().removeIf(sessionId::equals);
        }
    }
}