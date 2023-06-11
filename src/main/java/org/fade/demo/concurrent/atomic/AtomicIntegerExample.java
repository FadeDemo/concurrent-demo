package org.fade.demo.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger示例
 * @author fade
 */
public class AtomicIntegerExample {

    public static void main(String[] args) {
        int temvalue = 0;
        AtomicInteger i = new AtomicInteger(0);
        temvalue = i.getAndSet(3);
        //temvalue:0;  i:3
        System.out.println("temvalue:" + temvalue + ";  i:" + i);
        temvalue = i.getAndIncrement();
        //temvalue:3;  i:4
        System.out.println("temvalue:" + temvalue + ";  i:" + i);
        temvalue = i.getAndAdd(5);
        //temvalue:4;  i:9
        System.out.println("temvalue:" + temvalue + ";  i:" + i);
    }

}
