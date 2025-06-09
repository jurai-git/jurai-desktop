package com.jurai.data.model.internal_state;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AsyncState<T> {
    private T data;
    private boolean loading;
    private Exception error;
}
