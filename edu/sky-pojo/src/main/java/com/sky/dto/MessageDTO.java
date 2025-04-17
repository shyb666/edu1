package com.sky.dto;
import lombok.Data;

@Data
public class MessageDTO {

    private int senderId; // 发送者 ID
    private int receiverId; // 接收者 ID（私聊时使用）
    private String content; // 消息内容
}