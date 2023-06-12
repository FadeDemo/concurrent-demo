package org.fade.demo.concurrent.future;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * Future
 * @author fade
 */
public class Demo {

    public static void main(String[] args) {
        // 创建线程池
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("FutureTest" + "-%d")
                .setDaemon(true).build();
        ExecutorService executor = new ThreadPoolExecutor(1,
                1, 0,
                TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>(5),
                threadFactory);
        // 创建Callable任务
        Callable<Integer> task = () -> {
            // 模拟耗时操作
            Thread.sleep(10);
            return 42;
        };
        // 提交任务并获取Future对象
        Future<Integer> future = executor.submit(task);
        // 执行其他操作
        System.out.println("执行其它操作......");
        try {
            // 等待任务执行完成并获取结果
            while (!future.isDone()) {
                System.out.println("任务未完成......");
            }
            Integer result = future.get();
            System.out.println("任务结果：" + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        // 关闭线程池
        executor.shutdown();
    }

}
