package com.paddypower.csv.processor.util;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Util {

    @SafeVarargs
    public static <T> Map<List<Object>, List<T>> groupListBy(final List<T> data, final Function<T, ?> mandatory,
	    final Function<T, ?>... others) {
	return data.stream().collect(Collectors.groupingBy(cl -> Stream.concat(Stream.of(mandatory), Stream.of(others))
		.map(f -> f.apply(cl)).collect(Collectors.toList())));
    }

}
