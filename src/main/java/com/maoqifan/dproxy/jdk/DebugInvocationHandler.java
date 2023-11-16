package com.maoqifan.dproxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 定一个一个JDK动态代理类
 */
public record DebugInvocationHandler(Object target) implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 调用方法之前添加额外操作
        System.out.printf("before method %s\n", method.getName());
        Object result = method.invoke(target, args);
        // 调用方法后，同样可以添加操作
        System.out.printf("after method %s\n", method.getName());
        return result;
    }
}
