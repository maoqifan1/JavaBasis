package com.maoqifan.dproxy.jdk;

public class SmsServiceImpl implements SmsService {
    @Override
    public String send(String message) {
        System.out.printf("send message: %s\n", message);
        return message;
    }
}
