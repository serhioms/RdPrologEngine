package ca.mss.rdprolog;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utils {

	public static Term[] toTerms(String[] atoms) {
		Term[] terms = new Term[atoms.length];
		for(int i=0,max=terms.length; i<max; ++i) {
			terms[i] = Character.isUpperCase(atoms[i].charAt(0))? new Var(atoms[i]): new Notion(atoms[i]);
		}
		return terms;
	}

	@SafeVarargs
	public static <A> List<A> toList(A... a) {
		return Arrays.asList(a);
	}

	public static <T> String toString(List<T> list) {
		return list.stream().filter(Objects::nonNull).map(t->t.toString()).collect(Collectors.joining(","));
	}

}
