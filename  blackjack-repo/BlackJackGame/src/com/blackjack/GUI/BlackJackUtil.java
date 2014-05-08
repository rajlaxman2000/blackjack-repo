package com.blackjack.GUI;

public abstract class BlackJackUtil {
	
	
	public static final int NUM_DECKS =2;
	
	public final static int FACES_IN_DECK = 13;
	
	public final static int SUITS_IN_DECK = 4;
	
	public final static int CARDS_IN_DECK = 52;
	
	
	/**
	 * Integer representing a win
	 */
	public static final int WIN = 1;

	/**
	 * Integer representing a push
	 */
	public static final int PUSH = 0;

	/**
	 * Integer representing a loss
	 */
	public static final int LOSS = -1;
	
	
	public static final int MIN_BET = 10;
	
	public static final int START_MONEY = 1000;
	
	public static final String WINDOW_HEADING = "Geetha's : Blackjack";

	public static final String WELCOM_MSG = "Welcome to the Blackjack Game.";
	
	public static final String OUT_OF_MNY_MSG = "Sorry, You are put of money, can't paly any more.";
	
	public static final String PLAYER_NAME = "Geetha";
}
