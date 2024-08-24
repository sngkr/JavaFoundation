package com.JUC.SingleTon;

public class SingletonTest {

    // 1. 懒汉式，线程不安全
    public static class LazyUnsynchronizedSingleton {
        // 单例对象
        private static LazyUnsynchronizedSingleton instance;

        // 私有构造函数，防止外部实例化
        private LazyUnsynchronizedSingleton() {}

        // 获取单例对象的方法
        public static LazyUnsynchronizedSingleton getInstance() {
            // 如果 instance 为空，则创建新的实例
            if (instance == null) {
                instance = new LazyUnsynchronizedSingleton();
            }
            // 返回单例对象
            return instance;
        }
    }

    // 2. 懒汉式，线程安全
    public static class LazySynchronizedSingleton {
        // 单例对象
        private static LazySynchronizedSingleton instance;

        // 私有构造函数，防止外部实例化
        private LazySynchronizedSingleton() {}

        // 获取单例对象的方法，使用 synchronized 关键字确保线程安全
        public static synchronized LazySynchronizedSingleton getInstance() {
            // 如果 instance 为空，则创建新的实例
            if (instance == null) {
                instance = new LazySynchronizedSingleton();
            }
            // 返回单例对象
            return instance;
        }
    }

    // 3. 饿汉式
    public static class EagerSingleton {
        // 单例对象，在类加载时就创建了
        private static EagerSingleton instance = new EagerSingleton();

        // 私有构造函数，防止外部实例化
        private EagerSingleton() {}

        // 获取单例对象的方法
        public static EagerSingleton getInstance() {
            // 直接返回单例对象
            return instance;
        }
    }

    // 4. 双重校验锁
    public static class DoubleCheckedLockingSingleton {
        // 单例对象，使用 volatile 关键字确保多线程环境下的可见性和禁止指令重排
        private volatile static DoubleCheckedLockingSingleton singleton;

        // 私有构造函数，防止外部实例化
        private DoubleCheckedLockingSingleton() {}

        // 获取单例对象的方法
        public static DoubleCheckedLockingSingleton getInstance() {
            // 第一次检查，如果 singleton 为空，则进入同步块
            if (singleton == null) {
                synchronized (DoubleCheckedLockingSingleton.class) {
                    // 第二次检查，确保 singleton 仍然为空
                    if (singleton == null) {
                        // 实例化单例对象
                        singleton = new DoubleCheckedLockingSingleton();
                    }
                }
            }
            // 返回单例对象
            return singleton;
        }
    }

    // 5. 静态内部类
    public static class StaticInnerClassSingleton {
        // 静态内部类，持有单例对象
        private static class SingletonHolder {
            // 在类加载时创建单例对象
            private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
        }

        // 私有构造函数，防止外部实例化
        private StaticInnerClassSingleton() {}

        // 获取单例对象的方法
        public static final StaticInnerClassSingleton getInstance() {
            // 返回静态内部类中的单例对象
            return SingletonHolder.INSTANCE;
        }
    }

    // 6. 枚举
    public enum EnumSingleton {
        // 枚举常量，作为单例对象
        INSTANCE;

        // 枚举中的方法
        public void someMethod() {
            // 方法实现
        }
    }

    // 测试方法
    public static void main(String[] args) {
        // 测试懒汉式，线程不安全
        LazyUnsynchronizedSingleton lazyUnsync = LazyUnsynchronizedSingleton.getInstance();

        // 测试懒汉式，线程安全
        LazySynchronizedSingleton lazySync = LazySynchronizedSingleton.getInstance();

        // 测试饿汉式
        EagerSingleton eager = EagerSingleton.getInstance();

        // 测试双重校验锁
        DoubleCheckedLockingSingleton doubleChecked = DoubleCheckedLockingSingleton.getInstance();

        // 测试静态内部类
        StaticInnerClassSingleton staticInner = StaticInnerClassSingleton.getInstance();

        // 测试枚举
        EnumSingleton enumSingleton = EnumSingleton.INSTANCE;
        enumSingleton.someMethod();

        // 输出测试结果
        System.out.println("LazyUnsynchronizedSingleton: " + lazyUnsync);
        System.out.println("LazySynchronizedSingleton: " + lazySync);
        System.out.println("EagerSingleton: " + eager);
        System.out.println("DoubleCheckedLockingSingleton: " + doubleChecked);
        System.out.println("StaticInnerClassSingleton: " + staticInner);
        System.out.println("EnumSingleton: " + enumSingleton);
    }
}
