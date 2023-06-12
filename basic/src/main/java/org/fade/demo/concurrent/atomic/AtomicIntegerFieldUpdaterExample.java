package org.fade.demo.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * {@link java.util.concurrent.atomic.AtomicIntegerFieldUpdater} 示例
 * @author fade
 */
public class AtomicIntegerFieldUpdaterExample {

    public static void main(String[] args) {
        AtomicIntegerFieldUpdater<User> a =
                AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
        User user = new User("Java", 22);
        // 22
        System.out.println(a.getAndIncrement(user));
        // 23
        System.out.println(a.get(user));
    }

    static class User {

        private String name;

        public volatile int age;

        public User(String name, int age) {
            super();
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

    }

}
