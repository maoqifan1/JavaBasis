package com.maoqifan.demo;

import lombok.Data;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class Either<L, R> {
    /**
     * 代表异常情况
     */
    private L left;

    /**
     * 代表正常情况
     */
    private R right;

    public boolean isLeft() {
        return this.left != null;
    }

    public boolean isRight() {
        return this.right != null;
    }

    /**
     * 工厂方法-左值
     */
    public static <L, R> Either<L, R> left(L exception) {
        Either<L, R> e = new Either<>();
        e.left = exception;
        return e;
    }

    /**
     * 工厂方法-右值
     */
    public static <L, R> Either<L, R> right(R value) {
        Either<L, R> e = new Either<>();
        e.right = value;
        return e;
    }

    public <T> Either<L, T> map(Function<R, T> function) {
        // 如果是异常值，则直接返回
        if (isLeft()) {
            return left(left);
        }
        // 如果是正常值则进行映射
        else {
            return right(function.apply(right));
        }
    }

    /**
     * 将eitherList转换成Either对象
     * */
    public static <L, R> Either<L, List<R>> sequence(List<Either<L, R>> eitherList, BinaryOperator<L> accumulator) {
        if (eitherList.stream().allMatch(Either::isRight)) {
            // 将所有数据存入
            return right(eitherList.stream().map(Either::getRight).collect(Collectors.toList()));
        } else {
            // 取单个
//            return left(eitherList.stream().filter(Either::isLeft).findFirst().orElseThrow().getLeft());
            // 取全部异常
            return left(
                    eitherList.stream()
                            .filter(Either::isLeft)
                            .map(Either::getLeft)
                            .reduce(accumulator)
                            .orElseThrow()
            );


        }
    }


}
