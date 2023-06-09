package org.fade.demo.concurrent;

import java.util.concurrent.Semaphore;

/**
 * 信号量
 * @author fade
 */
public class SemaphoreExample {

    public static void main(String[] args) {
        // 创建一个Semaphore对象，设置许可证数量为2
        Semaphore semaphore = new Semaphore(2);
        // 创建并启动多个线程
        for (int i = 1; i <= 5; i++) {
            Thread thread = new Thread(new Worker(semaphore, "Thread " + i));
            thread.start();
        }
    }

    static class Worker implements Runnable {

        private final Semaphore semaphore;

        private final String name;

        public Worker(Semaphore semaphore, String name) {
            this.semaphore = semaphore;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + " is waiting");
                semaphore.acquire(); // 获取许可证
                System.out.println(name + " starts working");
                // 模拟工作
                Thread.sleep(2000);
                System.out.println(name + " finishes working");
                semaphore.release(); // 释放许可证
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
