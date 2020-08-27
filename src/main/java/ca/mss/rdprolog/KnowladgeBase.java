package ca.mss.rdprolog;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class KnowladgeBase {

	public static boolean IS_DEBUG = true;
	public static boolean IS_INFO = true;
	
	final private List<Rule> rules = new LinkedList<>(); 

	public KnowladgeBase() {
	}

	public KnowladgeBase(Predicate... facts) {
		Arrays.stream(facts).filter(Objects::nonNull).forEach(f->rules.add(new Rule(f)));
	}

	public void addRule(Predicate header, Predicate... body) {
		rules.add(new Rule(header, body));
	}

	public void add(Rule rule) {
		rules.add(rule);
	}

	public void addFact(String predicate, Term... terms) {
		rules.add(new Rule(predicate, terms));
	}

	public void addFact(String predicate, String... terms) {
		rules.add(new Rule(predicate, terms));
	}

	public boolean evaluate(Query query) {
		if( KnowladgeBase.IS_INFO ) System.out.printf("%s\n", query);
		List<Result> results = evaluate(query.body);
		boolean resultIs = check(results);
		if( KnowladgeBase.IS_INFO ) System.out.printf("%s\n", resultIs?"yes":"no");
		return resultIs;
	}
	
	List<Result> evaluate(List<Predicate> predicates) {
		return rules.stream()
			.flatMap(rule->rule.evaluate(predicates, this).stream())
			.collect(Collectors.toList());
	}

	private boolean check(List<Result> results) {
		for(Result result: results) {
			if( result.yes ) {
				return true;
			}
		}
		return false;
	}

}
