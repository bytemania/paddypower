package com.paddypower.csv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Currency {
    private final String name;
    private final double rate;
    private final String symbol;
}
