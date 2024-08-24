package com.JUC.ProAndCon;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public class pcByQueue {
    public static void main(String[] args) {
        ExecutorService consumer = Executors.newFixedThreadPool(6);
        ExecutorService producer = Executors.newFixedThreadPool(4);
        BlockingQueue<Integer> queue = new SynchronousQueue<>();
        producer.submit(() -> {
            try {
                System.out.println("生产...");
                Thread.sleep(100);
                queue.put(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        consumer.submit(() -> {
            try {
                System.out.println("等待消费...");
                Integer result = queue.take();
                System.out.println("结果为:" + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
