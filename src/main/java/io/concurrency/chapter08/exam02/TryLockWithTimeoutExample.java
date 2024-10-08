package io.concurrency.chapter08.exam02;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockWithTimeoutExample {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Thread thread1 = new Thread(() -> {
            try {
                if (lock.tryLock(2, TimeUnit.SECONDS)) {
                    try {
                        System.out.println("thread1 get lock");
                        Thread.sleep(3000); // 스레드 1이 락을 보유 (시간 초과)
                    } finally {
                        lock.unlock();
                        System.out.println("thread1 get unlock");
                    }
                } else {
                    System.out.println("thread1 get fail lock");
                }
            } catch (InterruptedException e) {
                // 인터럽트 처리
                System.out.println("스레드 1이 interrupt 를 받았습니다");
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                if (lock.tryLock(2, TimeUnit.SECONDS)) {
                    try {
                        System.out.println("스레드 2가 락을 획득했습니다");
                    } finally {
                        lock.unlock();
                        System.out.println("스레드 2가 락을 해제했습니다");
                    }
                } else {
                    System.out.println("thread2 get fail lock");
                }
            } catch (InterruptedException e) {
                // 인터럽트 처리
                System.out.println("스레드 2가 interrupt 받았습니다");
            }
        });

        thread1.start();
        thread2.start();
    }
}
