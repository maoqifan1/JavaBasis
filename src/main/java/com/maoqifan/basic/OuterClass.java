package com.maoqifan.basic;

class OuterClass {
    private final int outerClassInt = 10;
    private final static int staticOuterClassInt = 15;

    public class InnerClass {
        public void print() {
            System.out.printf("outerClassInt: %d", outerClassInt);
        }
    }

    public static class StaticInnerClass {
        public void print() {
            System.out.printf("outerClassInt: %d", staticOuterClassInt);
        }

    }

    public static void main(String... args) {
        OuterClass outerClass = new OuterClass();
        OuterClass.StaticInnerClass staticInnerClass = new OuterClass.StaticInnerClass();
        OuterClass.InnerClass innerClass = outerClass.new InnerClass();
    }
}
