package com.maoqifan.dproxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 自定义方法拦截器
 */
public class DebugMethodInterceptor implements MethodInterceptor {
    /**
     * @param o           被代理的对象(需要增强的对象)
     * @param method      被拦截的方法(需要增强的方法)
     * @param objects     方法的参数
     * @param methodProxy 用于调用原始方法
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 调用方法之前添加额外操作
        System.out.printf("before method %s\n", method.getName());
        Object result = methodProxy.invoke(o, objects);
        // 调用方法后，同样可以添加操作
        System.out.printf("after method %s\n", method.getName());
        return result;
    }
}
