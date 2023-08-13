package com.mobilise.task.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genre {
    FICTION("FICTION"), NON_FICTION("NON_FICTION"), SCI_FI("SCI_FI"), MYSTERY("MYSTERY");

    private final String genre;
}
