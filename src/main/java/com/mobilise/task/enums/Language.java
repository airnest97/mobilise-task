package com.mobilise.task.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {
    ENGLISH("ENGLISH"), FRENCH("FRENCH"), GERMAN("GERMAN");

    private final String language;
}
