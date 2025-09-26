package com.jurai.data.model.internal_state;


public class AsyncState<T> {
    private T data;
    private boolean loading;
    private Exception error;

    public AsyncState(T data, boolean loading, Exception error) {
        this.data = data;
        this.loading = loading;
        this.error = error;
    }

    public T getData() {
        return this.data;
    }

    public boolean isLoading() {
        return this.loading;
    }

    public Exception getError() {
        return this.error;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void setError(Exception error) {
        this.error = error;
    }
}
