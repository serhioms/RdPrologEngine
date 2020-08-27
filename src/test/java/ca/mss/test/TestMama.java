package ca.mss.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ca.mss.rdprolog.KnowladgeBase;
import ca.mss.rdprolog.Notion;
import ca.mss.rdprolog.Query;

public class TestMama {

	@Test
	public void testWashTrue() {
		KnowladgeBase kb = new KnowladgeBase();
		kb.addFact("wash", "mama", "frame");
		assertTrue(kb.evaluate(new Query("wash", "mama", "frame")));
	}

	@Test
	public void testWashXY() {
		KnowladgeBase kb = new KnowladgeBase();
		kb.addFact("wash", "mama", "frame");
		assertTrue(kb.evaluate(new Query("wash", "X", "Y")));
	}

	@Test
	public void testWashFalse() {
		KnowladgeBase kb = new KnowladgeBase();
		kb.addFact("wash", "mama", "frame");
		assertTrue(!kb.evaluate(new Query("wash", "frame", "mama")));
	}


	@Test
	public void testWashX() {
		KnowladgeBase kb = new KnowladgeBase();
		kb.addFact("wash", new Notion("mama"));
		kb.addFact("wash", new Notion("frame"));
		assertTrue(kb.evaluate(new Query("wash", "X")));
		
	}

}
