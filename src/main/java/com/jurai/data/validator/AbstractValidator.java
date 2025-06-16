package com.jurai.data.validator;

import com.jurai.ui.modal.notif.DefaultMessageNotification;
import com.jurai.ui.modal.notif.NotificationType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractValidator<T> {
    private Map<Filter<T>, String> filters;

    protected AbstractValidator() {
        filters = new LinkedHashMap<>();
    }

    protected void ruleFor(Filter<T> filter, String msg) {
        filters.put(filter, msg);
    }

    public String validate(T object) {
        System.out.println("Requested regular validation");
        for(Map.Entry<Filter<T>, String> entry : filters.entrySet()) {
            System.out.println("Validating entry with " + entry.getValue());
            if(entry.getKey().accept(object)) {
                return entry.getValue();
            }
        }
        return "";
    }

    public boolean validateJFX(T object) {
        System.out.println("Requested JFX validation");
        for(Map.Entry<Filter<T>, String> entry : filters.entrySet()) {
            System.out.println("Validating entry with " + entry.getValue());
            if(!entry.getKey().accept(object)) {
                new DefaultMessageNotification(entry.getValue(), NotificationType.ERROR).show();
                return false;
            }
        }
        return true;
    }

}
