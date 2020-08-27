package ca.mss.rdprolog;

public class Notion implements Term {

	public final String val;

	public Notion(String val) {
		if( val == null || val.trim().isEmpty() ) {
			throw new RuntimeException("Notion must not be empty!");
		}
		this.val = val.trim().toLowerCase().intern();
	}

	@Override
	public Atom getType() {
		return Atom.Notion;
	}
	
	@Override
	public boolean isVar() {
		return false;
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
