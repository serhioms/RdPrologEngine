package ca.mss.rdprolog;

public class Result {

	public final boolean yes;
	public final String message;

	public Result() {
		this.yes = false;
		this.message = null;
	}

	public Result(String message) {
		this.yes = true;
		this.message = message;
	}
}
