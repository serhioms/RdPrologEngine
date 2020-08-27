package ca.mss.rdprolog;

import java.util.HashMap;
import java.util.Map;

public class Arguments {
	
	public final Map<String,String> args = new HashMap<>(); 
			
	public Arguments() {
	}
	
	@Override
	public String toString() {
		return args.toString();
	}
}
