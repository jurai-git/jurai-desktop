package com.jurai.data.service;

import java.util.List;

public interface Service<T> {
    List<T> getAll();

    void create(T t);

    void remove(T t);
}
