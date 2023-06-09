package org.fade.demo.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * CyclicBarrier示例
 * @author fade
 */
public class CyclicBarrierExample {

    public static void main(String[] args) {
        // 线程数量
        int numThreads = 3;
        // 创建一个CyclicBarrier，指定等待的线程数量
        CyclicBarrier cyclicBarrier = new CyclicBarrier(numThreads + 1);
        // 创建线程池
        ThreadFactory factory = new ThreadFactoryBuilder().setDaemon(false)
                .setPriority(Thread.NORM_PRIORITY)
                .setNameFormat("CountDownLatch-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(numThreads,
                numThreads, 0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(5), factory);
        // 提交多个任务给线程池执行
        for (int i = 0; i < numThreads; i++) {
            executorService.submit(new Worker(cyclicBarrier, i));
        }
        try {
            cyclicBarrier.await(); // 主线程等待所有线程到达栅栏
            System.out.println("All threads have completed");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        // 关闭线程池
        executorService.shutdown();
    }

    static class Worker implements Runnable {

        private final CyclicBarrier cyclicBarrier;

        private final int workerId;

        public Worker(CyclicBarrier cyclicBarrier, int workerId) {
            this.cyclicBarrier = cyclicBarrier;
            this.workerId = workerId;
        }

        @Override
        public void run() {
            try {
                System.out.println("Worker " + workerId + " is performing some work");
                Thread.sleep(2000); // 模拟工作时间
                System.out.println("Worker " + workerId + " has completed");
                cyclicBarrier.await(); // 等待其他线程到达栅栏
                System.out.println("Ready to do other things...");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

}

