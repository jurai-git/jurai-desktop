package com.jurai.ui.modal;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LoadingStrategy {
    enum Strategy { LAZY, EAGER }
    Strategy value() default Strategy.LAZY;
}
