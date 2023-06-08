package org.fade.demo.concurrent.synchr0nized;

/**
 * @author fade
 * */
public class Demo {

    public void method() {
        synchronized (this) {
            System.out.println("synchronized 代码块");
        }
    }

    public synchronized void method1() {
        System.out.println("synchronized 方法");
    }

    public static void main(String[] args) {

    }

}
