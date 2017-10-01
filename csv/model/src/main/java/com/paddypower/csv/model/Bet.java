package com.paddypower.csv.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Bet {

    private final String bedId;
    private final long betTimestamp;
    private final long selectionId;
    private final String selectionName;
    private final double stake;
    private final double price;
    private final String currency;
}
