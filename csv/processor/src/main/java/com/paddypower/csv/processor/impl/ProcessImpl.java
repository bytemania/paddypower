package com.paddypower.csv.processor.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.paddypower.csv.model.Bet;
import com.paddypower.csv.model.Currency;
import com.paddypower.csv.model.Report;
import com.paddypower.csv.processor.Process;
import com.paddypower.csv.processor.util.Pair;
import com.paddypower.csv.processor.util.Util;

public class ProcessImpl implements Process {

    /*
     * Assumed that the validation was done previously
     */
    public List<Report> process(final List<Bet> bets, final List<Currency> currencies,
	    final Optional<String> baseCurrency) {

	final Map<List<Object>, List<Bet>> groupedBets = Util.groupListBy(bets, Bet::getSelectionName,
		Bet::getCurrency);

	final Map<String, Currency> indexedCurrencies = indexCurrencies(currencies);

	final List<Report> report = aggregate(groupedBets, indexedCurrencies, baseCurrency);

	report.sort(Report.SORTER_PAYOUT_ASC.reversed());

	report.forEach(System.out::println);

	final boolean b = true;

	return new ArrayList<>();
    }

    private List<Report> aggregate(final Map<List<Object>, List<Bet>> groupedBets,
	    final Map<String, Currency> currencies, final Optional<String> baseCurrency) {
	return groupedBets.entrySet().stream()
		.flatMap(entry ->
		Collections.singleton(entry.getValue().stream()
			.map(bet -> Pair.of(entry.getKey(), bet))
			.collect(Collectors.toList())
			.stream()
			.map(pair -> generateReport(pair, currencies, baseCurrency))
			.reduce(new Report(), (acc, rep) -> merge(acc, rep))).stream())
		.collect(Collectors.toList());
    }

    private Report generateReport(final Pair<List<Object>, Bet> pair, final Map<String, Currency> currencies, final Optional<String> baseCurrency)
    {

	final List<String> groupedColumns = pair.getLeft().stream().map(String::valueOf).collect(Collectors.toList());
	final Bet bet = pair.getRight();

	final double payout = bet.getStake() * bet.getPrice();
	final double basePayout = payout * currencies.get(bet.getCurrency()).getRate();

	final double totalBasePayout = basePayout
		/ (baseCurrency.isPresent() ? currencies.get(baseCurrency.get()).getRate() : 1);
	final double totalPayout = baseCurrency.isPresent() ? totalBasePayout : payout;

	final String currencySymbol = baseCurrency.isPresent() ? currencies.get(baseCurrency.get()).getSymbol()
		: currencies.get(bet.getCurrency()).getSymbol();

	return new Report(groupedColumns, 1L, bet.getStake(),
		totalPayout, totalBasePayout, currencySymbol);
    }

    private Report merge(final Report acc, final Report report) {
	return new Report(report.getAggregator(), acc.getNoOfBets() + report.getNoOfBets(),
		acc.getTotalStakes() + report.getTotalStakes(), acc.getTotalPayout() + report.getTotalPayout(),
		acc.getTotalBasePayout() + report.getTotalBasePayout(), report.getCurrencySymbol());
    }

    private Map<String, Currency> indexCurrencies(final List<Currency> currencies) {
	return currencies.stream().collect(Collectors.toMap(Currency::getName, Function.identity()));
    }


    public static void main(final String[] args) {
	final List<Bet> bets = new ArrayList<>();
	bets.add(new Bet("Bet-10", 1489490156000L, 1, "Selection-1", 0.5, 6.0, "GBP"));
	bets.add(new Bet("Bet-11", 1489490156000L, 2, "Selection-2", 1.25, 4.0, "EUR"));
	bets.add(new Bet("Bet-12", 1489230956000L, 4, "Selection-4", 5.0, 4.5, "GBP"));
	bets.add(new Bet("Bet-13", 1489403756000L, 3, "Selection-3", 4.5, 5.5, "GBP"));
	bets.add(new Bet("Bet-14", 1489144556000L, 2, "Selection-2", 7.9, 7.0, "EUR"));
	bets.add(new Bet("Bet-15", 1489140956000L, 1, "Selection-1", 3.4, 6.5, "EUR"));
	bets.add(new Bet("Bet-16", 1489227356000L, 4, "Selection-4", 2.5, 6.5, "GBP"));
	bets.add(new Bet("Bet-17", 1489313756000L, 2, "Selection-2", 1.5, 11.0, "EUR"));
	bets.add(new Bet("Bet-18", 1489310156000L, 2, "Selection-2", 3.8, 5.5, "GBP"));
	bets.add(new Bet("Bet-19", 1489482956000L, 3, "Selection-3", 3.4, 4.0, "GBP"));
	bets.add(new Bet("Bet-20", 1489396556000L, 4, "Selection-4", 2.25, 5.0, "EUR"));
	bets.add(new Bet("Bet-21", 1489137356000L, 2, "Selection-2", 5.4, 6.5, "EUR"));
	bets.add(new Bet("Bet-22", 1489223756000L, 3, "Selection-3", 6.7, 6.5, "GBP"));
	bets.add(new Bet("Bet-23", 1489310156000L, 3, "Selection-3", 1.1, 4.5, "EUR"));
	bets.add(new Bet("Bet-24", 1489324556000L, 4, "Selection-4", 2.0, 6.5, "GBP"));
	bets.add(new Bet("Bet-25", 1489151756000L, 2, "Selection-2", 3.2, 6.0, "GBP"));
	bets.add(new Bet("Bet-26", 1489497356000L, 2, "Selection-2", 4.2, 5.0, "EUR"));
	bets.add(new Bet("Bet-27", 1489410956000L, 3, "Selection-3", 2.1, 4.5, "EUR"));
	bets.add(new Bet("Bet-28", 1489324556000L, 1, "Selection-1", 7.8, 5.5, "GBP"));
	bets.add(new Bet("Bet-29", 1489320956000L, 4, "Selection-4", 6.2, 6.5, "GBP"));
	bets.add(new Bet("Bet-30", 1489493756000L, 4, "Selection-4", 8.4, 7.5, "EUR"));
	bets.add(new Bet("Bet-31", 1489407356000L, 1, "Selection-1", 10.5, 7.3, "GBP"));
	bets.add(new Bet("Bet-32", 1489320956000L, 3, "Selection-3", 5, 5.5, "GBP"));
	bets.add(new Bet("Bet-33", 1489234556000L, 2, "Selection-2", 0.75, 7.0, "EUR"));

	final ProcessImpl p = new ProcessImpl();

	p.process(bets, Arrays.asList(new Currency("EUR", 1 / 1.14, "€"), new Currency("GBP", 1, "£")),
		Optional.empty());
    }

}
