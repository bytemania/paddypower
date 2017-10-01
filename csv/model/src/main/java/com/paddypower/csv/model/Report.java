package com.paddypower.csv.model;

import java.util.Comparator;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Report {

    public static Comparator<Report> SORTER_PAYOUT_ASC = (r1, r2) -> r1.getTotalBasePayout() - r2.getTotalBasePayout() < 0 ? -1 : 1;

    private List<String> aggregator;
    private long noOfBets;
    private double totalStakes;
    private double totalPayout;
    private double totalBasePayout;
    private String currencySymbol;
}
