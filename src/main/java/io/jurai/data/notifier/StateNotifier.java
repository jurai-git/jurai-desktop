package io.jurai.data.notifier;

public interface StateNotifier<T> {
    public void stateChanged(T newState);
}
