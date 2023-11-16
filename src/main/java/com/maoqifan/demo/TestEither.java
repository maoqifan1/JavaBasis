package com.maoqifan.demo;

import com.maoqifan.demo.pojo.UserDo;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestEither {
    public static void main(final String... args) {
        List<Either<String, UserDo>> users = Stream
                .iterate(1, i -> i + 1)
                .limit(100)
                .map(TestEither::readLine)
                .collect(Collectors.toList());
        Either<String, List<UserDo>> either = Either.sequence(users, (s1, s2) -> s1 + "\n" + s2);

        // 有错
        if (either.isLeft()) {
            System.out.println(either.getLeft());
        } // 没错
        else {
            for (UserDo user : either.getRight()) {
                System.out.println(user);
            }
        }

    }

    public static Either<String, UserDo> readLine(int i) {
        // 正常没报错
        if (new Random().nextInt(100) <= 50) {
            return Either.right(new UserDo("maoqifan", "qq", 1));
        }
        // 异常、报错
        else {
            return Either.left("第" + i + "行报错");
        }
    }
}
