package io.concurrency.chapter08.exam01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockOrderExample {
    private static final ReentrantLock lock1 = new ReentrantLock();
    private static final ReentrantLock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            lock1.lock(); // 1번 락 획득
            try {
                System.out.println("thread get lock1");

                lock2.lock(); // 2번 락 획득
                try {
                    System.out.println("thread get lock2");
                } finally {
                    lock1.unlock(); // 1번 락 해제
                    System.out.println("thread get unlock1.");
                }
            } finally {
                lock2.unlock(); // 1번 락 해제
                System.out.println("thread get unlock2.");
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
