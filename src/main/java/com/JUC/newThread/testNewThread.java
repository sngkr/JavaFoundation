package com.JUC.newThread;

public class testNewThread {

    public static void main(String[] args) {
        myThread myThread1 = new myThread();
        myThread myThread2 = (myThread) new Thread(()->{
            System.out.println("runabale");
        });

    }

}

class myThread extends Thread{
    @Override
    public void run() {
        System.out.println("myThread");
    }
}
