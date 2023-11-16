package com.maoqifan.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArraysTest {
    @Getter
    @Setter
    @AllArgsConstructor
    private static class Person {
        private String userName;
        private String password;
    }

    public static void main(String[] args) {
        /*
         * Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper,
         *                             Function<? super T, ? extends U> valueMapper,
         *                             BinaryOperator<U> mergeFunction,
         *                             Supplier<M> mapSupplier) {
         * */
        // 如何将一个arrayList转换成map, 使用stream().collect(Collectors.toMap())
        Map<String, String> res = Stream.of(
                new Person("maoqifan", "qq")
        ).collect(Collectors.toMap(Person::getUserName, Person::getPassword));

        // 如果给你个ArrayList让你去重，最好的方法是创建个set，把arraylist放进去
        Set<Integer> deduplicatedSet = new HashSet<>(List.of(1, 2, 2, 3, 4));
        deduplicatedSet.forEach(System.out::println);

        Integer[] testList = new Integer[]{1, 3, 2};
        // 使用这种方法转换的list是Arrays自己实现的Arraylist并不是我们常用的那个ArrayList
        Arrays.asList(testList);
        // 可以使用下面几种方法将array转换为list
        // 1. list.of
        List<Integer> list = List.of(testList);

        // 2. new ArrayList
        list = new ArrayList<>(Arrays.asList(testList));

        // 3. 利用stream将包装类型装箱为list
        Integer[] boxedArr = new Integer[]{1, 2, 3};
        List<Integer> boxedList = Arrays.stream(boxedArr).collect(Collectors.toList());

        // 4. 利用stream将基本类型数组装箱成为list
        int[] basicArr = new int[]{1, 2, 3};
        List<Integer> basicBoxedList = Arrays.stream(basicArr).boxed().collect(Collectors.toList());

    }
}
