package ca.mss.rdprolog;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// <предикат>::= <имя>/ ‘(‘<терм> /’,’<терм>/ ‘)’/
// <терм>::= <атом> |<предикат>|<список>
public class Predicate implements Evaluate<Rule,KnowladgeBase,List<Result>>, Equals<Predicate>, Comparable<Predicate> {

	public final Notion name;
	public final List<Term> terms;
	
	private Predicate(Notion name, List<Term> terms) {
		this.name = name;
		this.terms = terms;
	}

	public Predicate(String name, Term... terms) {
		if( name == null || name.trim().isEmpty() ) {
			throw new RuntimeException("Predicate name must not be empty!");
		}
		if( terms == null ) {
			throw new RuntimeException("Predicate must not be naked!");
		}
		this.name = new Notion(name.trim().toLowerCase().intern());
		this.terms = Arrays.asList(terms);
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", name, Utils.toString(terms));
	}


	@Override
	public boolean isEquals(Predicate p) {
		return p==null? false: terms.size() == p.terms.size() && name.isEquals(p.name);
	}

	@Override
	public int compareTo(Predicate p) {
		return isEquals(p)? 0: -1;
	}

	@Override
	public List<Result> evaluate(Rule r, KnowladgeBase kb) {
		if( r.isFact() ) {
			if( compareFact(r.header.terms) ) {
				if( KnowladgeBase.IS_INFO ) System.out.printf("%s=%s\n", terms, r.header.terms);
				return Utils.toList(new Result(String.format("%s=%s\n", terms, r.header.terms)));
			} else {
				return Utils.toList(new Result());
			}
		}
		Map<String,String> map = compareRule(r.header.terms);
		return r.body.stream()
					.map(p->p.unifies(map))
					.flatMap(lp->kb.evaluate(lp).stream())
					.collect(Collectors.toList());
	}

	private boolean compareFact(List<Term> rterms) {
		if( KnowladgeBase.IS_DEBUG ) System.out.printf("Compare fact: %s vs %s\n", rterms, terms);
		for(int i=0,max=terms.size(); i<max; i++) {
			if( !terms.get(i).isEquals(rterms.get(i)) ) {
				return false;
			}
		}
		return true;
	}

	private Map<String, String> compareRule(List<Term> rterms) {
		if( KnowladgeBase.IS_DEBUG ) System.out.printf("Compare rule: %s vs %s\n", rterms, terms); 
		Map<String,String> map = new HashMap<>(terms.size()); 
		for(int i=0,max=terms.size(); i<max; i++) {
			// Unifies: X vs socratus
			switch( rterms.get(i).getType() ) {
 			case Variable:
				switch( terms.get(i).getType() ) {
				case Notion:
					map.put(rterms.get(i).getValue(), terms.get(i).getValue());
					break;
				case Variable:
					break;
				default:
					throw new RuntimeException("Unexpected term type!");
				}
				break;
			case Notion:
				break;
			default:
				throw new RuntimeException("Unexpected term type!");
			}
		}
		return map;
	}

	private List<Predicate> unifies(Map<String, String> map) {
		// Unifies: human(X) with {X=socratus}
		if( KnowladgeBase.IS_DEBUG ) System.out.printf("Unifies: %s with %s\n", this, map); 
		return Utils.toList(new Predicate(name, 
				terms.stream()
//				.map(t->!t.isVar()? t: !map.containsKey(t.getValue())? t: new Notion(map.get(t.getValue())))
				.map(t->{
					if( !t.isVar()) {
						return t;
					} else if( !map.containsKey(t.getValue())) {
						return t;
					} else {
						String name = map.get(t.getValue());
						return new Notion(name);
					}
				})
				.collect(Collectors.toList())));
	}


}
