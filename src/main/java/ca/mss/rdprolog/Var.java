package ca.mss.rdprolog;

// <атом>::= <переменная> |<число> |<строка>|<имя>
public class Var implements Term {

	private final String val;

	public Var(String val) {
		if( val == null || val.trim().isEmpty() ) {
			throw new RuntimeException("Var must not be empty!");
		}
		this.val = val.trim().toUpperCase().intern();
	}

	@Override
	public Atom getType() {
		return Atom.Variable;
	}

	@Override
	public boolean isVar() {
		return true;
	}

	@Override
	public String getValue() {
		return val;
	}

	@Override
	public String toString() {
		return val;
	}
	
}
