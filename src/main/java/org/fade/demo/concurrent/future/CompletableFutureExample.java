package org.fade.demo.concurrent.future;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture示例
 * @author fade
 */
public class CompletableFutureExample {

    public static void main(String[] args) {
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            // 第一个异步任务
            System.out.println("Task 1: Executing");
            return "Result of Task 1";
        });
        CompletableFuture<String> task2 = task1.thenApply(result -> {
            // 在第一个任务完成后执行的任务
            System.out.println("Task 2: Executing");
            return result + ", Result of Task 2";
        });
        CompletableFuture<Void> task3 = task2.thenAccept(result -> {
            // 在第二个任务完成后执行的任务
            System.out.println("Task 3: Executing. Result: " + result);
        });
        // 等待所有任务完成
//        CompletableFuture.allOf(task3).join();
        task3.join();
        System.out.println("All tasks completed");
    }

}
