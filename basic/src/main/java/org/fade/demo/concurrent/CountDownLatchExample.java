package org.fade.demo.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * CountDownLatch例子
 * @author fade
 */
public class CountDownLatchExample {

    public static void main(String[] args) {
        ThreadFactory factory = new ThreadFactoryBuilder().setDaemon(false)
                .setPriority(Thread.NORM_PRIORITY)
                .setNameFormat("CountDownLatch-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(5,
                5, 0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(5), factory);
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; ++i) {
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " is working...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        pool.shutdown();
        System.out.println("finish.");
    }

}
