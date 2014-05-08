package com.blackjack.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for creating hands in a game of blackjack. Static variables can be
 * changed for different blackjack rules. Methods will get the best (highest)
 * value of the hand, the number of cards in the hand, whether the hand has
 * busted or is blackjack. They can also add cards to the hand and clear the
 * hand. A Hand is initialized with INITIAL_HAND_SIZE number of cards, and cards
 * cannot be removed by the user.
 * 
 */
public class Hand {
	
	private List<Card> playerHand; 
	
	/**
	 * The number of cards in the first hand
	 */
	public final static int INITIAL_HAND_SIZE = 2;	
	
	

	/**
	 * Creates a hand with Card c1 and Card c2
	 * 
	 * @param card1 - The first Card to be added to the Hand
	 * @param card2 - The second Card to be added to the Hand
	 */
	public Hand(Card card1, Card card2) {
		this.playerHand = new ArrayList<Card>();
		this.playerHand.add(card1);
		this.playerHand.add(card2);
	}
	
	
	/**
	 * Gets the best(highest) value of the hand
	 * 
	 * @return Integer value of the hand
	 */
	public int getBestValue() {
		int totalValue = 0;
		for (Card card : this.playerHand) {
			if (card.getFace() != Card.ACE) {
				totalValue += card.getValue();
			}
		}
		for (Card card : this.playerHand) {
			if (card.getFace() == Card.ACE) {
				if (totalValue + card.getHighValue() <= 21) {
					totalValue += card.getHighValue();
				} else {
					totalValue += card.getLowValue(); // 1
				}
			}
		}
		return totalValue;
	}

	/**
	 * Tells whether the hand has busted
	 * 
	 * @return boolean representing whether the Hand has busted
	 */
	public boolean isBusted() {
		return getBestValue() > 21;
	}

	/**
	 * Hand is Blackjack if and only if it has 2 cards: an Ace and a 10/Jack/Queen/King
	 * 
	 * @return boolean representing whether the Hand is BlackJack
	 */
	public boolean isBlackJack() {
		boolean hasAce = false;
		boolean hasTen = false;
		for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
			if (this.playerHand.get(i).getFace() == Card.ACE)
				hasAce = true;
			if (this.playerHand.get(i).getValue() == 10)
				hasTen = true;
		}
		return hasAce && hasTen;
	}

	/**
	 * Removes the cards from the hand and returns them in an ArrayList
	 * 
	 * @return ArrayList containing the removed cards
	 */
	public ArrayList<Card> clearHand() {
		ArrayList<Card> removedHandCards = new ArrayList<Card>();
		for (int i = 0; i < this.playerHand.size(); i++) {
			removedHandCards.add(this.playerHand.get(i));
		}
		this.playerHand.clear();
		return removedHandCards;
	}
	
	/**
	 * Adds a card to the hand
	 * 
	 * @param toAdd - The Card to be added to the Hand
	 */
	public void addCard(Card card) {
		this.playerHand.add(card);
	}

	/**
	 * Gets the card at index position of the hand
	 * 
	 * @param index - Position of the card to be retrieved
	 * @return Card at position index
	 */
	public Card get(int index) {
		return this.playerHand.get(index);
	}
	

	/**
	 * Gets the number of cards in the Hand
	 * 
	 * @return Integer value of the cards in the hand
	 */
	public int length() {
		return this.playerHand.size();
	}

}
