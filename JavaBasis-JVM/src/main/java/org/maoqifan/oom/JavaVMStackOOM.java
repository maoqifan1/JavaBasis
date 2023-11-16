package org.maoqifan.oom;

/**
 * VM Args：-Xss2M （这时候不妨设大些，请在32位系统下运行）
 */
public class JavaVMStackOOM {
    private void leak() {
        while (true) ;
    }

    public void stackLeakByThread() {
        while (true) {
            new Thread(this::leak).start();
        }
    }

    public static void main(String... args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }
}
