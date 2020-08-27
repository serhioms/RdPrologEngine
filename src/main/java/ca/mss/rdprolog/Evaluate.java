package ca.mss.rdprolog;

public interface Evaluate<P, K, R> {
	
	public R evaluate(P p, K k);
	
}
