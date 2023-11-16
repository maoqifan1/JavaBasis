package org.maoqifan.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * java 7 及以下（永久代而不是元空间）
 * VM Args：-XX:PermSize=10M -XX:MaxPermSize=10M
 */
public class JavaMethodAreaOOM {
    static class OOMObject {
    }

    public static void main(String... args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) ->
                    methodProxy.invokeSuper(o, args)
            );
            enhancer.create();
        }
    }
}
