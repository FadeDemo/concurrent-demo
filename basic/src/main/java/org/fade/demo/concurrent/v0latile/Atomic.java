package org.fade.demo.concurrent.v0latile;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 原子性
 * @author fade
 * */
public class Atomic {

    public volatile static int inc = 0;

    // 法三 Lock
//    private Lock lock = new ReentrantLock();

    // 法二 AtomicInteger
//    public volatile static AtomicInteger inc = new AtomicInteger();

    public void increase() {
        // 不是原子操作
        inc++;
    }

    /**
     * 法三 Lock
     * */
//    public void increase() {
//        lock.lock();
//        try {
//            inc++;
//        } finally {
//            lock.unlock();
//        }
//    }

    /**
     * 法二 AtomicInteger
     * */
//    public void increase() {
//        // 不是原子操作
//        inc.getAndIncrement();
//    }



    /**
     * 法一 synchronized
     * */
//    public synchronized void increase() {
//        inc++;
//    }

    public static void main(String[] args) throws InterruptedException {
//        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        ExecutorService threadPool = new ThreadPoolExecutor(5, 5, 0,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(5),
                r -> new Thread(r, "atomic-" + UUID.randomUUID()));
        Atomic demo = new Atomic();
        for (int i = 0; i < 10; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " is working!");
                for (int j = 0; j < 500; j++) {
                    demo.increase();
                }
            });
        }
        // 等待1.5秒，保证上面程序执行完成
        Thread.sleep(1500);
        System.out.println(inc);
        threadPool.shutdown();
    }

}
