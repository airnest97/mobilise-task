package com.mobilise.task.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AgeRating {
    UNIVERSAL("UNIVERSAL"), PG("PG"), PG13("PG13");
    private final String ageRating;
}
