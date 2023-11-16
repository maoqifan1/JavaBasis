package com.maoqifan.multithread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;

public class Test {
    public static void main(String... args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        Arrays.stream(threadMXBean.dumpAllThreads(false, false))
                .toList()
                .forEach(e -> {
                    System.out.println("[" + e.getThreadId() + "] " + e.getThreadName());
                });
    }
}
