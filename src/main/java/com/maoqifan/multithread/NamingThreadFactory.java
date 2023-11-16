package com.maoqifan.multithread;

import lombok.AllArgsConstructor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工程，设置线程名称，有利于定位问题
 */
@AllArgsConstructor
public class NamingThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNum = new AtomicInteger();
    private final ThreadFactory delegate;
    private final String name;

    @Override
    public Thread newThread(Runnable r) {
        Thread t = delegate.newThread(r);
        t.setName(name + "[#" + threadNum.incrementAndGet() + "]");
        return t;
    }
}
