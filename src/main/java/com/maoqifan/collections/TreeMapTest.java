package com.maoqifan.collections;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.TreeMap;

public class TreeMapTest {
    @Getter
    @Setter
    @Builder
    public static class Person {
        private String name;
        private int old;
    }

    public static void main(String... args) {
        TreeMap<Person, String> treeMap = new TreeMap<>((o1, o2) -> {
            //  逆序
            if (o1.getOld() > o2.getOld()) {
                return -1;
            } else if (o1.getOld() < o2.getOld()) {
                return 1;
            }
            return 0;
        });
        treeMap.put(Person.builder().old(10).name("person1").build(), "person1");
        treeMap.put(Person.builder().old(12).name("person2").build(), "person2");
        treeMap.put(Person.builder().old(15).name("person3").build(), "person3");
        treeMap.put(Person.builder().old(8).name("person4").build(), "person4");

        treeMap.forEach((key, value) -> System.out.println(key.old + '\t'));

    }
}
