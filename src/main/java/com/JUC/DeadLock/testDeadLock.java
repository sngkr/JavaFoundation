package com.JUC.DeadLock;

import static java.lang.Thread.sleep;

public class testDeadLock {

    public static final Object lock1 = new Object();
    public static final Object lock2 = new Object();





    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (lock2){}
            }
        },"thread1");

        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (lock1){}
            }
        },"thread2");

        thread1.start();
        thread2.start();
    }
}
