package com.jurai;

import lombok.Getter;
import lombok.Setter;

public class LombokDebugTest {
    @Getter @Setter
    private String testField;

    public static void main(String[] args) {
        LombokDebugTest test = new LombokDebugTest();
        test.setTestField("hello");
        System.out.println(test.getTestField());
    }
}