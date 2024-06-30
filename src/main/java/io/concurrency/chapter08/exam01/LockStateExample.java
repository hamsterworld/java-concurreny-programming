package io.concurrency.chapter08.exam01;

import java.util.concurrent.locks.ReentrantLock;

public class LockStateExample {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            lock.lock(); // 락 획득 (1번)
            try {
                System.out.println("thread1 get lock1.");

                lock.lock(); // 락 획득 (2번)
                try {
                    System.out.println("thread1 get lock2.");

                    lock.lock(); // 락 획득 (3번)
                    try {
                        System.out.println("thread1 get lock3.");
                    } finally {
                        lock.unlock(); // 락 해제 (1번)
                        System.out.println("thread1 get unlock1.");
                    }
                } finally {
                    lock.unlock(); // 락 해제 (2번)
                    System.out.println("thread1 get unlock2.");
                }
            } finally {
                lock.unlock(); // 락 해제 (3번)
                System.out.println("thread1 get unlock3.");
            }
        });

        Thread thread2 = new Thread(() -> {
            lock.lock(); // 락 획득 (스레드 1이 락을 세 번 해제할 때까지 대기)
            try {
                System.out.println("thread1 get lock1.");
            } finally {
                lock.unlock(); // 락 해제
                System.out.println("thread1 get unlock1.");
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
