package com.maoqifan.multithread;

public class SynchronizedDemo {
    public void method(){
        synchronized (this){
            System.out.println("synchronized 代码块");
        }
    }
}
