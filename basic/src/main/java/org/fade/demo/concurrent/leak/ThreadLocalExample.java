package org.fade.demo.concurrent.leak;

import java.lang.reflect.Field;

/**
 * {@link ThreadLocal} 内存泄露
 * @author fade
 */
public class ThreadLocalExample {

    public static void main(String[] args) throws NoSuchFieldException,
            IllegalAccessException, InterruptedException {
        Thread t = new Thread(()->test("abc",false));
        t.start();
        t.join();
        System.out.println("--gc后--");
        Thread t2 = new Thread(() -> test("def", true));
        t2.start();
        t2.join();
    }

    private static void test(String s,boolean isGc)  {
        try {
            // warn: memory leak
            new ThreadLocal<>().set(s);
//            ThreadLocal<Object> threadLocal = new ThreadLocal<>();
//            threadLocal.set(s);
            if (isGc) {
                System.gc();
            }
            Thread t = Thread.currentThread();
            Class<? extends Thread> clz = t.getClass();
            Field field = clz.getDeclaredField("threadLocals");
            field.setAccessible(true);
            Object threadLocalMap = field.get(t);
            Class<?> tlmClass = threadLocalMap.getClass();
            Field tableField = tlmClass.getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] arr = (Object[]) tableField.get(threadLocalMap);
            for (Object o : arr) {
                if (o != null) {
                    Class<?> entryClass = o.getClass();
                    Field valueField = entryClass.getDeclaredField("value");
                    Field referenceField = entryClass.getSuperclass()
                            .getSuperclass().getDeclaredField("referent");
                    valueField.setAccessible(true);
                    referenceField.setAccessible(true);
                    System.out.printf("弱引用key:%s,值:%s%n", referenceField.get(o),
                            valueField.get(o));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
