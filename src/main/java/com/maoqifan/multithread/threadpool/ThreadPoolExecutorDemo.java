package com.maoqifan.multithread.threadpool;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorDemo {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyRunnable implements Runnable {

        private String command;

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " Start. Time = " + new Date());
            processCommand();
            System.out.println(Thread.currentThread().getName() + " End. Time = " + new Date());
        }


        private void processCommand() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return this.command;
        }
    }

    public static void main(String[] args) {
        //  通过ThreadPoolExecutor构造函数自定义参数创建
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        Queue<Runnable> arr = new ArrayBlockingQueue<Runnable>(QUEUE_CAPACITY);

        for (int i = 0; i < 10; ++i) {
            // 创建WorkerThread对象
            Runnable worker = new ThreadPoolExecutorDemo.MyRunnable("");
            // 执行runnable
            executor.execute(worker);
        }
        // 终止线程池
        executor.shutdown();
        while (!executor.isTerminated()) ;
        System.out.println("finished all threads");
    }
}
