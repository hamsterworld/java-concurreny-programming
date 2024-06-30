package io.concurrency.chapter04.exam05.logger;

import java.util.ArrayList;
import java.util.List;

public class ThreadLocalLogger {

    private static final ThreadLocal<List<String>> THREAD_LOG = ThreadLocal.withInitial(ArrayList::new);

    public static void addLog(String log) {
        THREAD_LOG.get().add(log);
    }

    public static void printLog() {
        List<String> logs = THREAD_LOG.get();
        System.out.println("[" + Thread.currentThread().getName() + "] Log: " + String.join(" -> ", logs));
    }

    public static void clearLog() {
        THREAD_LOG.remove();
    }

    static class ServiceA {
        public void process() {
            addLog("ServiceA Logic Operation");
        }
    }

    static class ServiceB {
        public void process() {
            addLog("ServiceB Logic Operation");
        }
    }

    static class ServiceC {
        public void process() {
            addLog("ServiceC Logic Operation");
        }
    }
}
