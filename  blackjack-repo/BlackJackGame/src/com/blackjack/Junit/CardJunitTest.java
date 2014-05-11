package com.blackjack.JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.blackjack.bean.Card;

public class CardJUnitTest {

	@Test
	public void testgetColorName() {
		Card card = new Card(3, 4);
		assertEquals("Black", card.getColorName());
	}

	@Test
	public void testgetSuit() {
		Card card = new Card(2, 4);
		assertEquals(2, card.getSuit());
	}

	@Test
	public void testgetValue() {
		Card card = new Card(3, 4);
		assertEquals(4, card.getValue());
	}

	@Test
	public void testgetHighValue() {
		Card card = new Card(3, 4);
		assertEquals(4, card.getHighValue());
	}

	@Test
	public void testgetlowValue() {
		Card card = new Card(3, 4);

		assertEquals(4, card.getLowValue());
	}

	@Test
	public void testgetFaceName() {
		Card card = new Card(3, 4);

		assertEquals("Four", card.getFaceName());
	}

	@Test
	public void testgetSuitName() {
		Card card = new Card(3, 4);

		assertEquals("Spades", card.getSuitName());
	}
}
