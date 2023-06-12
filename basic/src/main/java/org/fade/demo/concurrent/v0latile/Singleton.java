package org.fade.demo.concurrent.v0latile;

/**
 * 单例
 * @author fade
 */
public class Singleton {

    /**
     * <b>volatile 也是有必要的</b><br/>
     * <li>1. 为 instance 分配内存空间
     * <li>2. 初始化 instance
     * <li>3. instance 指向分配的内存地址
     * <p>由于 JVM 具有指令重排的特性，执行顺序有可能变成 1->3->2。
     * 指令重排在单线程环境下不会出现问题，
     * 但是在多线程环境下会导致一个线程获得还没有初始化的实例。
     * 例如，线程 T1 执行了 1 和 3，此时 T2 调用 <code>getInstance()</code>
     * 后发现 instance 不为空，因此返回 instance，
     * 但此时 instance 还未被初始化
     * */
    private static volatile Singleton instance;

    //1.构造器私有化
    private Singleton(){

    }

    //3.向外提供一个静态的公共方法获取实例对象
    //仅当使用该方法时，才去创建instance
    public static Singleton getInstance() {
        //2.类的内部创建对象
        if (instance==null){
            synchronized (Singleton.class){
                if (instance==null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

}
