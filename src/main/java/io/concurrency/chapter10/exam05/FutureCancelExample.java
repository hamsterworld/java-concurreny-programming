package io.concurrency.chapter10.exam05;

import java.util.concurrent.*;

public class FutureCancelExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Callable 작업 생성
        Callable<Integer> callableTask = () -> {
            System.out.println("start async..");
            Thread.sleep(1000);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("finish async.");
            return 42;
        };

        // 작업을 제출 하고 Future 객체를 받음
        Future<Integer> future = executorService.submit(callableTask);

        /*while (!future.isDone()) {
            System.out.println("Waiting for the result...");
            Thread.sleep(500);
        }*/

        Thread.sleep(999);
        // 작업 취소 시도, 결과가 완료된 경우는 효과가 없다
        future.cancel(false);


        System.out.println(future.isCancelled());
//        if (!future.isCancelled()) {
            try {
                Integer result = future.get();
                System.out.println("Result: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
//        } else {
//            System.out.println("Task was cancelled.");
//        }
        executorService.shutdown();
    }
}
