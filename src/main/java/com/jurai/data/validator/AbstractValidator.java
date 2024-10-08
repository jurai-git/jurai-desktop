package com.jurai.data.validator;

import javafx.scene.control.Alert;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractValidator<T> {
    private Map<Filter<T>, String> filters;

    protected AbstractValidator() {
        filters = new HashMap<>();
    }

    protected void ruleFor(Filter<T> filter, String msg) {
        filters.put(filter, msg);
    }

    public String validate(T object) {
        for(Map.Entry<Filter<T>, String> entry : filters.entrySet()) {
            if(!entry.getKey().accept(object)) {
                return entry.getValue();
            }
        }
        return "";
    }

    public void validateJFX(T object) {
        for(Map.Entry<Filter<T>, String> entry : filters.entrySet()) {
            if(!entry.getKey().accept(object)) {
                new Alert(Alert.AlertType.ERROR, entry.getValue()).show();
                return;
            }
        }
    }

}
