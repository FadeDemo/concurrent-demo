package org.fade.demo.concurrent.container;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList
 * @author fade
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) {
        // 创建一个 CopyOnWriteArrayList 对象
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        // 向列表中添加元素
        list.add("Java");
        list.add("Python");
        list.add("C++");
        System.out.println("初始列表：" + list);
        // 使用 get 方法获取指定位置的元素
        System.out.println("列表第二个元素为：" + list.get(1));
        // 使用 remove 方法删除指定元素
        boolean result = list.remove("C++");
        System.out.println("删除结果：" + result);
        System.out.println("列表删除元素后为：" + list);
        // 使用 set 方法更新指定位置的元素
        list.set(1, "Golang");
        System.out.println("列表更新后为：" + list);
        // 使用 add 方法在指定位置插入元素
        list.add(0, "PHP");
        System.out.println("列表插入元素后为：" + list);
        // 使用 size 方法获取列表大小
        System.out.println("列表大小为：" + list.size());
        // 使用 removeAll 方法删除指定集合中所有出现的元素
        result = list.removeAll(List.of("Java", "Golang"));
        System.out.println("批量删除结果：" + result);
        System.out.println("列表批量删除元素后为：" + list);
        // 使用 clear 方法清空列表中所有元素
        list.clear();
        System.out.println("列表清空后为：" + list);
    }

}
