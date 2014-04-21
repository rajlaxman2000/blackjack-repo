package com.blackjack.Junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.blackjack.bean.Card;
import com.blackjack.bean.Deck;

public class DeckJunitTest {

	@Test
	public void testDeck() {
		Deck deck=new Deck();
		deck.shuffle();
		
	}
	
	@Test
	public void testaddToBottom() {
		Deck d=new Deck();
		Card card=new Card(3, 4);

		
		//assertEquals("true",d.addToBottom(card));
		
		//assertEquals(is, d.draw())
	}
	

	@Test
	public void testdraw() {
		Deck d=new Deck();
		Card card=new Card(3, 4);
		ArrayList<Card> deck=new ArrayList<>();
		
		//assertEquals(is, d.draw())
	}


}
