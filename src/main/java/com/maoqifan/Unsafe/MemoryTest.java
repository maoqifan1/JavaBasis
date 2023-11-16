package com.maoqifan.Unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class MemoryTest {
    private static Unsafe reflectGetUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void memoryTest() {
        Unsafe unsafe = reflectGetUnsafe();
        int size = 4;
        long addr = unsafe.allocateMemory(size);
        long addr3 = unsafe.reallocateMemory(addr, size * 2);
        System.out.printf("addr: %s\n", addr);
        System.out.printf("addr3: %s\n", addr3);

        try {
            unsafe.setMemory(null, addr, size, (byte) 1);
            for (int i = 0; i < 2; ++i) {
                unsafe.copyMemory(null, addr, null, addr3 + size * i, 4);
            }
            System.out.println(unsafe.getInt(addr));
            System.out.println(unsafe.getLong(addr3));
        } finally {
            unsafe.freeMemory(addr);
            unsafe.freeMemory(addr3);
        }
    }

    public static void main(String... args) {
        new MemoryTest().memoryTest();

    }
}
