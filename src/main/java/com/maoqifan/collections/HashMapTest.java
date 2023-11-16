package com.maoqifan.collections;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime) // 测试完成时间
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS) // 预热 2 轮，每次 1s
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS) // 测试 5 轮，每次 1s
@Fork(1) // fork 1 个线程
@State(Scope.Thread) // 每个测试线程一个实例
public class HashMapTest {
    static Map<Integer, String> map = new HashMap<>();

    static {
        for (int i = 0; i < 100; ++i) {
            map.put(i, "value" + i);
        }
    }

    static final int MAXIMUM_CAPACITY = 1 << 30;

    @Benchmark
    public void entrySetIterator() {
        Set<Map.Entry<Integer, String>> entrySet = map.entrySet();
        Iterator<Map.Entry<Integer, String>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> next = iterator.next();
            if (next.getKey() == 1) {
                iterator.remove();
            }
            System.out.println(next.getValue() + "\t");
        }
    }

    @Benchmark
    public void keySetIterator() {
        Set<Integer> keySet = map.keySet();
        Iterator<Integer> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            Integer k = iterator.next();
            if ( k == 1) {
                iterator.remove();
            }
            System.out.println(map.get(k) + "\t");
        }
    }

    @Benchmark
    public void entrySetForEach() {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getValue() + "\t");
        }
    }

    @Benchmark
    public void keySetForEach() {
        for (Integer key : map.keySet()) {
            System.out.println(map.get(key) + "\t");
        }
    }

    @Benchmark
    public void mapForEach() {
        // 删除
        map.keySet().removeIf(k -> k == 1);
        map.forEach((k, v) -> {
            System.out.println(v + "\t");
        });
    }

    @Benchmark
    public void streamSingleThread() {

        map.entrySet().stream().filter(e -> e.getKey()==1).forEach(e -> {
            System.out.println(e.getValue() + "\t");
        });
    }

    @Benchmark
    public void streamMultiThread() {
        map.entrySet().parallelStream().forEach(e -> {
            System.out.println(e.getValue() + "\t");
        });
    }


    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public static void main(String... args) throws RunnerException {
        // 基准测试
        Options options = new OptionsBuilder()
                .include(HashMapTest.class.getSimpleName())
                .output("./test_result.txt").build();
        new Runner(options).run();
    }


}
