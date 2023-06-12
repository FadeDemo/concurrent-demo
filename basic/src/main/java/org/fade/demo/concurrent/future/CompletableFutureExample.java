package org.fade.demo.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
        // 消费之后不能再消费了
//        CompletableFuture<String> task4 = CompletableFuture.completedFuture("Hello World!")
//                .whenComplete((res, ex) -> {
//                    System.out.println(res);
//                    ex.printStackTrace();
//                });
        CompletableFuture<String> task4 = CompletableFuture.completedFuture("Hello World!");
        // 处理异常
        CompletableFuture<String> task5 = CompletableFuture.supplyAsync(() -> {
            if (true) {
                throw new RuntimeException();
            }
            return "hello";
        }).handle((res, ex) -> res == null ? "error" : res);
        CompletableFuture<String> task6
                = CompletableFuture.supplyAsync(() -> {
            if (true) {
                throw new RuntimeException("Computation error!");
            }
            return "hello!";
        }).exceptionally(ex -> {
            System.out.println(ex.toString());// CompletionException
            return "world!";
        });
        // 本身是异常
        CompletableFuture<String> task7 = new CompletableFuture<>();
        task7.completeExceptionally(new RuntimeException("Task 7: exception"));
        // thenCompose
        CompletableFuture<String> task8
                = CompletableFuture.supplyAsync(() -> "hello!")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "world!"));
        // thenCombine
        CompletableFuture<String> task9
                = CompletableFuture.supplyAsync(() -> "hello!")
                .thenCombine(CompletableFuture.supplyAsync(
                        () -> "world!"), (s1, s2) -> s1 + s2)
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "nice!"));
        try {
            System.out.println("Task 4: " + task4.get());
            System.out.println("Task 5: " + task5.get());
            System.out.println("Task 6: " + task6.get());
            System.out.println("Task 8: " + task8.get());
            System.out.println("Task 9: " + task9.get());
            task7.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("All tasks completed");
    }

}
