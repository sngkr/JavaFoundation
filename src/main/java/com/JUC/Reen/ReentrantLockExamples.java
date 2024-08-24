package com.JUC.Reen;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ReentrantLockExamples {

    // 创建一个非公平的可重入锁
    private static final ReentrantLock lock = new ReentrantLock();
    // 创建一个公平的可重入锁
    private static final ReentrantLock fairLock = new ReentrantLock(true);
    // 创建一个非公平的可重入锁
    private static final ReentrantLock nonFairLock = new ReentrantLock(false);
    // 创建一个条件变量
    private static final Condition condition = lock.newCondition();
    // 标记是否准备就绪
    private static boolean ready = false;

    public static void main(String[] args) {
        // 运行基本使用示例
        basicUsageExample();
        // 运行公平锁与非公平锁示例
        fairVsNonFairLockExample();
        // 运行尝试获取锁示例
        tryLockExample();
        // 运行条件变量示例
        conditionVariableExample();
    }

    /**
     * 基本使用示例：演示如何使用 ReentrantLock 来保护临界区。
     */
    private static void basicUsageExample() {
        Runnable task = () -> {
            // 获取锁
            lock.lock();
            try {
                // 执行临界区内的代码
                System.out.println(Thread.currentThread().getName() + " is executing.");
            } finally {
                // 释放锁
                lock.unlock();
            }
        };

        // 创建两个线程
        Thread thread1 = new Thread(task, "Thread 1");
        Thread thread2 = new Thread(task, "Thread 2");

        // 启动线程
        thread1.start();
        thread2.start();
    }

    /**
     * 公平锁与非公平锁示例：演示公平锁与非公平锁的区别。
     */
    private static void fairVsNonFairLockExample() {
        Runnable task = () -> {
            try {
                // 尝试获取公平锁
                System.out.println(Thread.currentThread().getName() + " is trying to acquire the fair lock.");
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + " acquired the fair lock.");
                // 暂停线程 1 秒
                Thread.sleep(1000);
                // 尝试获取非公平锁
                nonFairLock.lock();
                System.out.println(Thread.currentThread().getName() + " acquired the non-fair lock.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放非公平锁
                nonFairLock.unlock();
                // 释放公平锁
                fairLock.unlock();
            }
        };

        // 创建两个线程
        Thread thread1 = new Thread(task, "Thread 1");
        Thread thread2 = new Thread(task, "Thread 2");

        // 启动线程
        thread1.start();
        thread2.start();
    }

    /**
     * 尝试获取锁示例：演示如何尝试获取锁，并设置超时时间。
     */
    private static void tryLockExample() {
        Runnable task = () -> {
            boolean acquired = false;
            try {
                // 尝试获取锁，等待时间为 500 毫秒
                System.out.println(Thread.currentThread().getName() + " is trying to acquire the lock.");
                acquired = lock.tryLock(500, java.util.concurrent.TimeUnit.MILLISECONDS);
                if (acquired) {
                    // 成功获取锁
                    System.out.println(Thread.currentThread().getName() + " acquired the lock.");
                } else {
                    // 未能成功获取锁
                    System.out.println(Thread.currentThread().getName() + " failed to acquire the lock.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 如果成功获取锁，则释放锁
                if (acquired) {
                    lock.unlock();
                }
            }
        };

        // 创建两个线程
        Thread thread1 = new Thread(task, "Thread 1");
        Thread thread2 = new Thread(task, "Thread 2");

        // 启动线程
        thread1.start();
        thread2.start();
    }

    /**
     * 条件变量示例：演示如何使用条件变量来协调多个线程之间的操作。
     */
    private static void conditionVariableExample() {
        Runnable producerTask = () -> {
            // 获取锁
            lock.lock();
            try {
                // 生产者开始工作
                System.out.println("Producer: Starting...");
                // 设置准备就绪标记
                ready = true;
                // 通知所有等待的消费者
                condition.signalAll();
                System.out.println("Producer: Notified all consumers.");
            } finally {
                // 释放锁
                lock.unlock();
            }
        };

        Runnable consumerTask = () -> {
            // 获取锁
            lock.lock();
            try {
                // 消费者等待准备就绪
                while (!ready) {
                    System.out.println("Consumer: Waiting for signal...");
                    // 等待条件变量的信号
                    condition.await();
                }
                // 收到信号后，消费者准备消费
                System.out.println("Consumer: Received signal, ready to consume.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放锁
                lock.unlock();
            }
        };

        // 创建生产者线程
        Thread producer = new Thread(producerTask, "Producer");
        // 创建两个消费者线程
        Thread consumer1 = new Thread(consumerTask, "Consumer 1");
        Thread consumer2 = new Thread(consumerTask, "Consumer 2");

        // 启动消费者线程
        consumer1.start();
        consumer2.start();
        // 启动生产者线程
        producer.start();
    }
}
