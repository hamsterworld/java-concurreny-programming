package io.concurrency.chapter06.exam02;

public class CountingSemaphore implements CommonSemaphore {
    private int signal;
    private int permits;

    public CountingSemaphore(int permits) {
        this.permits = permits;
        this.signal = permits;
    }

    public synchronized void acquired() {
        while (this.signal == 0) {
            try {
                System.out.println("start waiting " + Thread.currentThread().getName());
                wait();
                System.out.println(Thread.currentThread().getName() + " : restart ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.signal--;
        System.out.println(Thread.currentThread().getName() + " get lock signal value =  " + signal);
    }

    public synchronized void release() {
        if (this.signal < permits) { // signal 값이 permits 보다 작을 때만 증가
            this.signal++;
            System.out.println(Thread.currentThread().getName() + " lost lock signal value = " + signal);
            notify();
        }
    }
}
