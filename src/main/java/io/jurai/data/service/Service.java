package io.jurai.data.service;

import java.util.List;

public interface Service<T> {
    List<T> getAll();
    void add();
    void remove();

}
