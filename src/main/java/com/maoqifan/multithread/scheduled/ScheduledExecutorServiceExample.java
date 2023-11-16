package com.maoqifan.multithread.scheduled;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceExample {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(
                2
        );
        // 2秒后执行runnable任务
        scheduledThreadPoolExecutor.schedule(() -> {
            System.out.println("this is runnable task 1");
        }, 2, TimeUnit.SECONDS);

        // 提交一个2秒后才执行的runnable任务
        // 既然runnable无法返回结果，为什么需要Future呢？
        // 因为可以通过Future进行取消任务等操作
        ScheduledFuture<?> runnableFuture = scheduledThreadPoolExecutor.schedule(
                () -> {
                    System.out.println("this is runnable task 2");
                }, 2, TimeUnit.SECONDS
        );
        // 取消任务
        runnableFuture.cancel(true);

        // 休眠3秒，确保上面任务都被执行完
        System.out.println("=========================");

    }

    private static void mySleep(int seconds) {
        try{
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
