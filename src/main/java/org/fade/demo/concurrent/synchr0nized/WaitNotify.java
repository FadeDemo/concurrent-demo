package org.fade.demo.concurrent.synchr0nized;

/**
 * wait notify 使用
 * @author fade
 * */
public class WaitNotify {

    public static void main(String[] args) {
        Object o = new Object();
        try {
            o.wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            synchronized (o) {
                try {
                    System.out.println("before waiting...");
                    // 必须要在synchronized里使用
                    o.wait();
                    System.out.println("finish waiting...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (o) {
                System.out.println("notify...");
                // 必须要在synchronized里使用
                o.notify();
            }
        }).start();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("finished");
    }

}
