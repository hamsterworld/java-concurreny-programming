package io.concurrency.chapter04.exam01;

public class ThreadExceptionExample {

    public static void main(String[] args) {

        System.err.println("안녕");

        try {
            new Thread(() -> {
                // 이거 err 출력이라 실제로 log 에 안남을걸?
                throw new RuntimeException("스레드 1 예외!");
            }).start();
        } catch(Exception e){
            sendNotificationToAdmin(e);
        }

    }

    // 여기로 안옴
    private static void sendNotificationToAdmin(Throwable e) {
        System.out.println("관리자에게 알림: " + e.getMessage());
    }
}
