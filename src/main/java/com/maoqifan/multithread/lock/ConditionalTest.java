package com.maoqifan.multithread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionalTest {
    public static void main(String[] args){
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        lock.lock();
        while(true){
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
