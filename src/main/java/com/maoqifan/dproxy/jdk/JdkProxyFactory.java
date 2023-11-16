package com.maoqifan.dproxy.jdk;

import java.lang.reflect.Proxy;

/**
 * 获取代理对象的工厂类
 * */
public class JdkProxyFactory {
    public static Object getProxy(Object target) {
        // 通过该方法获取某个类的代理对象
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new DebugInvocationHandler(target)
        );
    }
}
