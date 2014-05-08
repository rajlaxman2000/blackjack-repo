package com.blackjack.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.blackjack.GUI.BlackJackUtil;

/**
 * Template for creating a deck of cards for a blackjack game. Static variables
 * can be changed to alter the number of cards/suits in a deck /Cards in the
 * first hand of the game/Faces in the deck. Methods will shuffle the deck, draw
 * a card, and add a card to the bottom of the deck.
 * 
 */
public class Deck {

	private List<Card> deck;
	
	private int numCardsRemaining;
	
	private int count;

		
	public Deck() {
		
		this.deck = new ArrayList<Card>();
		for (int i = 0; i < BlackJackUtil.NUM_DECKS; i++) {
			for (int j = 0; j < BlackJackUtil.SUITS_IN_DECK; j++) {
				for (int k = 1; k <= BlackJackUtil.FACES_IN_DECK; k++) {
					this.deck.add(new Card(j, k));
				}
			}
		}
		shuffle();
	}

	/**
	 * Shuffles the deck
	 */
	public void shuffle() {
		ArrayList<Card> tempDeck = new ArrayList<Card>();
		Random random = new Random();
		while (this.deck.size() > 0) {
			int cardToRemove = random.nextInt(this.deck.size());
			Card tempCard = this.deck.get(cardToRemove);
			this.deck.remove(cardToRemove);
			tempDeck.add(tempCard);
		}
		while (tempDeck.size() > 0) {
			Card tempCard = tempDeck.get(0);
			tempDeck.remove(0);
			this.deck.add(tempCard);
		}
		count = 0;
		numCardsRemaining = BlackJackUtil.NUM_DECKS * BlackJackUtil.CARDS_IN_DECK;
	}

	/**
	 * Draws a card from the deck
	 * 
	 * @return Card card from the top of the Deck Shoe
	 */
	public Card draw() {
		Card toDraw = this.deck.get(0);

		this.deck.remove(0);
		numCardsRemaining--;
		int face = toDraw.getFace();
		if (face >= 1 && face <= 5)
			count++;
		if (face >= 9 || face == Card.ACE)
			count--;

		return toDraw;
	}

	/**
	 * Gets the current card count
	 * 
	 * @return Integer representing the current card count
	 */
	public int getCount() {
		double decksLeft = (double) numCardsRemaining / BlackJackUtil.CARDS_IN_DECK;
		return (int) Math.round(count / decksLeft);
	}

	/**
	 * Adds a card to the bottom of the deck
	 * 
	 * @param c -	Card to be added the bottom of the deck
	 */
	public void addToBottom(Card c) {
		this.deck.add(c);
		if (numCardsRemaining < 20) {
			shuffle();
		}
	}
}
