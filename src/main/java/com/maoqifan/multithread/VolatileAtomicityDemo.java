package com.maoqifan.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VolatileAtomicityDemo {
    public volatile static int inc = 0;

    public void increase() {
        inc++;
    }

    public static void main(String... args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        VolatileAtomicityDemo volatileAtomicityDemo = new VolatileAtomicityDemo();
        for (int i = 0; i < 5; ++i) {
            threadPool.execute(() -> {
                for (int j = 0; j < 500; ++j) {
                    volatileAtomicityDemo.increase();
                }
            });
        }
        // 等1.5s保证上面程序执行完成
        Thread.sleep(1500);
        System.out.println(inc);
        threadPool.shutdown();
    }
}
