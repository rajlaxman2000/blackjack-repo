package com.blackjack.Junit;

import static org.junit.Assert.*;

import org.junit.Test;

import com.blackjack.bean.Card;
import com.blackjack.bean.Hand;

public class HandJunitTest {


	@Test
	public void testgetBestValue() {
		Card card1 = new Card(3, 6);
		Card card2 = new Card(2, 5);
		Hand hand = new Hand(card1, card2);
		assertEquals(6 + 5, hand.getBestValue());

	}

	@Test
	public void testisBusted() {
		Card card1 = new Card(3, 9);
		Card card2 = new Card(2,5);
		Hand hand = new Hand(card1, card2);

		assertEquals(false, hand.isBusted());

	}

	@Test
	public void testisBlackJack() {
		Card card1 = new Card(3, 0);
		Card card2 = new Card(2, 10);
		Hand hand = new Hand(card1, card2);
		//System.out.println(hand.isBlackJack());

		assertEquals(true, hand.isBlackJack());

	}
	
}
