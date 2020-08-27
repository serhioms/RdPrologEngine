package ca.mss.rdprolog;

import static ca.mss.rdprolog.RuleType.Fact;
import static ca.mss.rdprolog.RuleType.Rule;

import java.util.List;
import java.util.stream.Collectors;


// <ПРОЛОГ-предложение> ::= <правило> | <факт> | <запрос>
// <правило> ::= <заголовок> ‘:-’<тело>
// <заголовок>::= <предикат>
// <тело> ::= <цель> /’,’<цель>/’.’
// <цель>::= <предикат> |<выражение>
// <выражение>::= <терм> /<оператор><терм>/
// <оператор>::= ‘is’ | '=' | ‘==' | ’\=' | ’>=' | ’=<’ | ‘=\=' |
public class Rule implements Evaluate<List<Predicate>, KnowladgeBase, List<Result>> {
	
	public final Predicate header;
	public final List<Predicate> body;
	
	public Rule(Predicate header, Predicate... body) {
		if( header == null ) {
			throw new RuntimeException("Rule header must not be empty!");
		}
		this.header = header;
		this.body = Utils.toList(body);
	}

	public Rule(String predicate, Term... terms) {
		header = new Predicate(predicate, terms);
		body = Utils.toList();
	}

	public Rule(String predicate, String... terms) {
		header = new Predicate(predicate, Utils.toTerms(terms));
		body = Utils.toList();
	}

	public Rule(Predicate fact) {
		if( fact == null ) {
			throw new RuntimeException("Fact must not be null!");
		}
		this.header = fact;
		this.body = Utils.toList();
	}

	@Override
	public String toString() {
		return String.format(isFact()?"%s.":"%s:-%s.", header, isFact()?null: Utils.toString(body));
	}


	@Override
	public List<Result> evaluate(List<Predicate> predicates, KnowladgeBase kb) {
		return predicates
			.stream()
			.filter(p->p.isEquals(header))
			.filter(p->printf("%s: %s\n", getType(), toString()))
			.flatMap(p->p.evaluate(this, kb).stream())
			.collect(Collectors.toList());
	}

	private RuleType getType() {
		return body.isEmpty()? Fact: Rule;
	}

	public boolean isFact() {
		return body.isEmpty();
	}

	private boolean printf(String format, Object... args) {
		if( KnowladgeBase.IS_DEBUG ) System.out.printf(format, args);
		return true;
	}
}
