package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.socket.*;
@AllArgsConstructor
@Data
public class SessionBean {
    private Integer clientId;
    private WebSocketSession webSocketSession;
}