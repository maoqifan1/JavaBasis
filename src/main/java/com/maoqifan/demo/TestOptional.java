package com.maoqifan.demo;

import com.maoqifan.demo.pojo.UserDo;

import java.util.Optional;
import java.util.Scanner;

public class TestOptional {
    @SuppressWarnings("all")
    public static void main(final String... args) {
        // 1. 将可能为空的对象转换为optional对象
        UserDo userDo = new UserDo("maoqifan", "qq", 1);
        Optional<UserDo> optionalUserDo = Optional.ofNullable(userDo);
        // 2. optional链式操作
        optionalUserDo.map(UserDo::getUserName)
                .orElse("maoqifan"); // orElse给出默认值
        // 3. Optional短路
        // 从数据库查处某个用户，如果不存在则向数据库中新建用户并返回
        UserDo UserDo1 = optionalUserDo.orElseGet(() -> new UserDo("hello", "mm", 2));
        // 4. Optional抛出异常
        optionalUserDo.orElseThrow(() -> new RuntimeException());

        int a, b, c;
        Scanner sc = new Scanner(System.in);
        a = sc.nextInt();
        b = sc.nextInt();
        c = sc.nextInt();
        if (a != 0) {
            //todo:
        }
        if (b != 0) {
            //todo:
        }
        if (c != 0) {
            //todo:
        }

        Optional<Integer> optionala = Optional.ofNullable(a);
        Optional<Integer> optionalb = Optional.ofNullable(b);
        Optional<Integer> optionalc = Optional.ofNullable(c);
        optionala.filter(i -> i != 0).ifPresent(i -> {
            //:todo
        });
        optionalb.filter(i -> i != 0).ifPresent(i -> {
            //:todo
        });
        optionalc.filter(i -> i != 0).ifPresent(i -> {
            //:todo
        });

    }
}
