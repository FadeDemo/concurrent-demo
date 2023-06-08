package org.fade.demo.concurrent.pool;

import org.fade.demo.concurrent.v0latile.Atomic;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 饱和策略
 * @author fade
 */
public class SaturationPolicy {

    public volatile static AtomicInteger inc = new AtomicInteger();

    public void increase() {
        // 不是原子操作
        inc.getAndIncrement();
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = new ThreadPoolExecutor(5, 5, 0,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(5),
                r -> new Thread(r, "atomic-" + UUID.randomUUID()));
        Atomic demo = new Atomic();
        // 11个刚好报错
        // AbortPolicy
        for (int i = 0; i < 11; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " is working!");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
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
