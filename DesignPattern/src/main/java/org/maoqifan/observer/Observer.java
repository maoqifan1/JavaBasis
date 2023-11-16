package org.maoqifan.observer;

public abstract class Observer {
    protected Subject subject;
    public abstract void update();

    public static class BinaryObserver extends Observer{
        public BinaryObserver(Subject subject){
            this.subject = subject;
            // 将观察者添加到主题的观察者列表中
            this.subject.attach(this);
        }
        @Override
        public void update() {
            System.out.println("Binary String: " + Integer.toBinaryString(subject.getState()));
        }
    }

    public static class OctalObserver extends Observer{
        public OctalObserver(Subject subject){
            this.subject = subject;
            // 将观察者添加到主题的观察者列表中
            this.subject.attach(this);
        }
        @Override
        public void update() {
            System.out.println("Octal String: " + Integer.toOctalString(subject.getState()));
        }
    }
}