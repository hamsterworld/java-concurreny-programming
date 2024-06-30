package io.concurrency.chapter09.exam04;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {

        int numThreads = 5;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(5);

        for (int i = 0; i <numThreads; i++) {
            new Thread(new Worker(startSignal, doneSignal)).start();
        }

        Thread.sleep(3000);
        startSignal.countDown();

        System.out.println("notify start sign");

        doneSignal.await();

        System.out.println("all of threads is done");
    }

    static class Worker implements Runnable{

        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        public Worker(CountDownLatch startSignal, CountDownLatch doneSignal){

            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                startSignal.await();

                System.out.println(Thread.currentThread().getName() + " is working");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " is finished");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                doneSignal.countDown();
            }
        }
    }
}
