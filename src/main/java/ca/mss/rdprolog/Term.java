package ca.mss.rdprolog;

// <терм>::= <атом> |<предикат>|<список>
// <атом>::= <переменная> |<число> |<строка>|<имя>
// <список>::= <список с заголовком>| <простой список>
// <список с заголовком >::= ‘[‘ <терм >/’,’<терм>/’|’ < терм>’]’
// < простой список>::= ‘[‘ <терм >/’,’<терм>/’]’|‘['’]’
public interface Term extends Equals<Term> {

	public String getValue();
	public Atom getType();
	public boolean isVar();

	@Override
	default public boolean isEquals(Term t) {
		return t==null? false: getType() == t.getType()? getValue().equals(t.getValue()): true;
	}
	
}
