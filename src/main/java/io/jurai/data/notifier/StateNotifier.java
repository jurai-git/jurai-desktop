package io.jurai.data.notifier;

@FunctionalInterface
public interface StateNotifier<T> {
    public void stateChanged(T newState);
}
