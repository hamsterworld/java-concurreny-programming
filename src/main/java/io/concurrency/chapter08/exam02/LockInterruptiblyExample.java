package io.concurrency.chapter08.exam02;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptiblyExample {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();

        Thread thread1 = new Thread(() -> {
            try {
                lock.lockInterruptibly(); // 락을 시도하며, 인터럽트가 들어오면 중단
                try {
                    System.out.println("thread1 get lock");
                } finally {
                    lock.unlock();
                    System.out.println("thread1 get unlock");
                }
            } catch (InterruptedException e) {
                System.out.println("thread1 get interrupted");
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                lock.lockInterruptibly(); // 락을 시도하며, 인터럽트가 들어오면 중단
                try {
                    System.out.println("thread2 get lock");
                    Thread.sleep(1000);
                } finally {
                    lock.unlock();
                    System.out.println("thread2 get unlock");
                }
            } catch (InterruptedException e) {
                System.out.println("thread2 get interrupted");
            }
        });

        thread1.start();
        thread2.start();
        thread1.interrupt();
//        thread2.interrupt();

        try {
            Thread.sleep(500);
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
