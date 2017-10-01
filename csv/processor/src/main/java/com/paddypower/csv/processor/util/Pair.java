package com.paddypower.csv.processor.util;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public final class Pair<L, R> {
    private final L left;
    private final R right;
    
    public static <L, R> Pair<L, R> of(L left, R right) {
	return new Pair<>(left, right);
    }
}
