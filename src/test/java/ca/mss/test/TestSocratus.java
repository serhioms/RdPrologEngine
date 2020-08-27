package ca.mss.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ca.mss.rdprolog.KnowladgeBase;
import ca.mss.rdprolog.Notion;
import ca.mss.rdprolog.Predicate;
import ca.mss.rdprolog.Query;
import ca.mss.rdprolog.Rule;
import ca.mss.rdprolog.Var;

public class TestSocratus {

	Predicate mortalAristotelFact = new Predicate("mortal", new Notion("aristotel"));
	Predicate mortalSocratusFact = new Predicate("mortal", new Notion("socratus"));

	@Test
	public void testMortalSocratusTrue() {
		KnowladgeBase kb = new KnowladgeBase();
		
		kb.addRule(
				new Predicate("mortal", new Var("X")), 
				new Predicate("human", new Var("X"))
			);
		
		kb.addFact("human", "socratus");
		
		assertTrue(kb.evaluate(new Query("mortal", "socratus")));
		
	}

	@Test
	public void testMortalSocratusFalse() {
		KnowladgeBase kb = new KnowladgeBase();
		
		kb.add(new Rule(
				new Predicate("mortal", new Var("X"), new Var("Y")), 
				new Predicate("human", new Var("X"))
			));

		kb.addFact("human", "socratus");
		
		assertTrue(!kb.evaluate(new Query(mortalSocratusFact)));
	}

	@Test
	public void testMortalAristotel() {
		assertTrue(!new KnowladgeBase(mortalSocratusFact).evaluate(new Query(mortalAristotelFact)));
	}

	@Test
	public void testMortalSocratus() {
		assertTrue(new KnowladgeBase(mortalSocratusFact).evaluate(new Query(mortalSocratusFact)));
	}
}
