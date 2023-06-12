package org.fade.demo.concurrent.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 给线程池命名
 * @author fade
 */
public class NamedPool {

    public static void main(String[] args) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("guava" + "-%d")
                .setDaemon(true).build();
        ExecutorService threadPool = new ThreadPoolExecutor(5, 5,
                0, TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(5), threadFactory);
        for (int i = 0; i < 5; ++i) {
            threadPool.execute(() -> System.out.println(Thread.currentThread().getName()));
        }
        // 或者自己实现线程工厂
        threadPool = new ThreadPoolExecutor(5, 5,
                0, TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(5),
                new NamingThreadFactory(Executors.defaultThreadFactory(), "SelfDefined"));
        for (int i = 0; i < 5; ++i) {
            threadPool.execute(() -> System.out.println(Thread.currentThread().getName()));
        }
        threadPool.shutdown();
    }

    public static final class NamingThreadFactory implements ThreadFactory {

        private final AtomicInteger threadNum = new AtomicInteger();
        private final ThreadFactory delegate;
        private final String name;

        /**
         * 创建一个带名字的线程池生产工厂
         */
        public NamingThreadFactory(ThreadFactory delegate, String name) {
            this.delegate = delegate;
            this.name = name;
        }

        @Override
        public Thread newThread(Runnable r) {
            // 通过注入的ThreadFactory创建线程
            Thread t = delegate.newThread(r);
            t.setName(name + " [#" + threadNum.incrementAndGet() + "]");
            return t;
        }

    }

}
