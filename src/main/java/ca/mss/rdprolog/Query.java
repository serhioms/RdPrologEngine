package ca.mss.rdprolog;

import java.util.List;

// <ПРОЛОГ-предложение> ::= <правило> | <факт> | <запрос>
// <запрос> ::= <тело>‘.’
// <тело> ::= <цель> /’,’<цель>/’.’
// <цель>::= <предикат> |<выражение>
// <выражение>::= <терм> /<оператор><терм>/
// <оператор>::= ‘is’ | '=' | ‘==' | ’\=' | ’>=' | ’=<’ | ‘=\=' |
public class Query {
	
	public final List<Predicate> body;

	public Query(Predicate... body) {
		if( body == null ) {
			throw new RuntimeException("Query must not be empty!");
		}
		this.body = Utils.toList(body);
	}

	public Query(String predicate, String... terms) {
		this.body = Utils.toList(new Predicate(predicate, Utils.toTerms(terms)));
	}

	@Override
	public String toString() {
		return String.format("?-%s.", Utils.toString(body));
	}
}
