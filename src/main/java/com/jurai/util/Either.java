package com.jurai.util;

import java.util.function.Consumer;
import java.util.function.Function;

public class Either<L, R> {
    private final L left;
    private final R right;

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Either<L, R> left(L value) {
        return new Either<>(value, null);
    }

    public static <L, R> Either<L, R> right(R value) {
        return new Either<>(null, value);
    }

    public boolean isLeft() {
        return left != null;
    }

    public boolean isRight() {
        return right != null;
    }

    public <T> T ifLPresent(Function<? super L, T> ifPresent) {
        if(isLeft())
            return ifPresent.apply(left);
        return null;
    }

    public <T> T ifRPresent(Function<? super R, T> ifPresent) {
        if(isRight())
            return ifPresent.apply(right);
        return null;
    }

    public <T> T ifLPresentOrElse(Function<? super L, T> ifPresent, Function<? super R, T> orElse) {
        if (isLeft())
            return ifPresent.apply(left);
        else
            return orElse.apply(right);
    }

    public <T> T ifRPresentOrElse(Function<? super R, T> ifPresent, Function<? super L, T> orElse) {
        if (isRight())
            return ifPresent.apply(right);
        else
            return orElse.apply(left);
    }

    public L getLeft() {
        if (!isLeft()) {
            throw new IllegalStateException("No value present in left");
        }
        return left;
    }

    public R getRight() {
        if (!isRight()) {
            throw new IllegalStateException("No value present in right");
        }
        return right;
    }
}
