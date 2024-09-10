package com.jurai.data.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractLabeledEnum {
    protected String label;

    public static <T extends Enum<T> & LabeledEnum> List<String> asList(Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(LabeledEnum::getLabel)
                .collect(Collectors.toList());
    }

    public AbstractLabeledEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
