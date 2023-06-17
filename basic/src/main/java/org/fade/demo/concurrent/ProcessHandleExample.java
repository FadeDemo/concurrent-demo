package org.fade.demo.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fade
 */
public class ProcessHandleExample {

    public static void main(String[] args) {
        ProcessHandle current = ProcessHandle.current();
        System.out.println(current.pid());
        System.out.println(current.info());
        ThreadFactory factory = new ThreadFactoryBuilder()
                .setDaemon(false)
                .setPriority(Thread.NORM_PRIORITY).build();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2,
                0, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(5), factory);
        for (int i = 0; i < 2; ++i) {
            pool.execute(() -> {
                ProcessHandle c = ProcessHandle.current();
                System.out.println(c.pid());
                System.out.println(c.info());
            });
        }
        pool.shutdown();
    }

}
