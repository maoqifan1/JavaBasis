package com.maoqifan.multithread;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.*;

public class ThreadLocalExample implements Runnable {
    // SDF 不是线程安全的，所以每个线程都要有独立的副本
    private static final ThreadLocal<SimpleDateFormat> formatter = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd HHmm"));

    @Override
    public void run() {
        System.out.println("Current Thread: " + Thread.currentThread().getName() + " Default Formatter" + formatter.get().toPattern());
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 当前线程改变了formatter，但是其他线程不会变
        formatter.set(new SimpleDateFormat());

        System.out.println("Current Thread: " + Thread.currentThread().getName() + " Default Formatter" + formatter.get().toPattern());
    }

    public static void main(String... args) throws InterruptedException {
        ThreadLocalExample example = new ThreadLocalExample();
        int i = 0;
        while (i++ < 10) {
            Thread t = new Thread(example, "t" + i);
            Thread.sleep(new Random().nextInt(1000));
            t.start();
        }
    }
}
