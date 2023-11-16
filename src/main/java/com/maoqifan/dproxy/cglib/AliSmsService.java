package com.maoqifan.dproxy.cglib;

public class AliSmsService {
    public String send(String message) {
        System.out.printf("send message: %s\n", message);
        return message;
    }
}
