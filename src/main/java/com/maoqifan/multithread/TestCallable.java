package com.maoqifan.multithread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.ReentrantLock;

public class TestCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        return "callable thread done";
    }

    public static void testFutureTask() throws ExecutionException, InterruptedException {
        TestCallable test = new TestCallable();
        FutureTask<String> futureTask = new FutureTask<>(test);

        new Thread(futureTask).start();

        // 获取运算结果是同步过程，即call方法执行完成才能获取结果
        String res = futureTask.get();

        System.out.println(res);
    }

    /**
     * supplyAsync是创建带有返回值的异步任务。它有如下两个方法，
     * 一个是使用默认线程池（ForkJoinPool.commonPool()）的方法，
     * 一个是带有自定义线程池的重载方法
     */
    public static void testCompletableFutureSupply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("hello completable Future");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result";
        });
        System.out.println("hello world");
        // 等待任务执行完成
        System.out.println("结果->" + future.get());
    }

    // 使用自定义线程池
    public static void testCompletableFutureSupply2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("hello completableFuture");
            return "result";
        }, executorService);
        // 等待任务执行完成
        System.out.println("结果->" + cf.get());
    }

    /**
     * thenApply 表示某个任务执行完成后执行的动作，即回调方法，
     * 会将该任务的执行结果即方法返回值作为入参传递到回调方法中，带有返回值。
     */
    public static void testCompletableFutureThenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " cf1 do something....");
            return 1;
        });

        CompletableFuture<Integer> cf2 = cf1.thenApplyAsync(res -> {
            System.out.println(Thread.currentThread() + " cf2 do something....");
            res += 2;
            return res;
        });
        //等待任务1执行完成
        System.out.println("cf1结果->" + cf1.get());
        //等待任务2执行完成
        System.out.println("cf2结果->" + cf2.get());
    }

    /**
     * allOf：CompletableFuture是多个任务都执行完成后才会执行，只有有一个任务执行异常，
     * 则返回的CompletableFuture执行get方法时会抛出异常，如果都是正常执行，则get返回null。
     * <p>
     * anyOfw w ：CompletableFuture是多个任务只要有一个任务执行完成，
     * 则返回的CompletableFuture执行get方法时会抛出异常，如果都是正常执行，
     * 则get返回执行完成任务的结果。
     */
    public static void testCompletableFutureAllOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf1 do something....");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf1 任务完成");
            return "cf1 任务完成";
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf2 do something....");
                int a = 1 / 0;
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf2 任务完成");
            return "cf2 任务完成";
        });

        CompletableFuture<Void> cfAll = CompletableFuture.allOf(cf1, cf2);
        System.out.println("cfAll结果->" + cfAll.get());

    }

    public static void main(String... args) throws ExecutionException, InterruptedException {
        testCompletableFutureSupply();
    }


}
