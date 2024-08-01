package io.jurai.data.validator;

@FunctionalInterface
public interface Filter<T> {
    boolean accept(T t);
}
