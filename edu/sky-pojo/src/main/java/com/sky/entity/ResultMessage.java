package com.sky.entity;

import lombok.Data;

@Data
public class ResultMessage {
    private boolean system;
    private String fromName;
    private Object message;
}
