package com.blackjack.GUI;

import javax.swing.*;

/**
 * Contains the main GUI and main method.
 * 
 */
public class BlackjackGui {
	
	/**
	 * Minimum bet of the table
	 */
	public static final int MIN_BET = 10;
	
	/**
	 * Money each player starts with
	 */
	public static final int START_MONEY = 1000;

	/**
	 * Runs the Game 
	 * 
	 * @param args - Main Method
	 */
	public static void main(String[] args) {

		GameWindow game = new GameWindow(MIN_BET,START_MONEY);
		JOptionPane.showMessageDialog(game, "Welcome to the Blackjack Game.");
		while (true) {
			if (game.human.getMoney() < MIN_BET) {
				JOptionPane.showMessageDialog(game, "Sorry, You are put of money, can't paly any more.");
				System.exit(0);
			}

			game.repaint();//Clears the cards from the screen.
			game.askBets();// Will ask for bet amount.
			game.deal(); // Cards will be dealt for Dealer as well as for player. (2 cards will be given for each.)
			game.repaint();	// Cards will be displayed on Screen		
			game.setButtonState(true, true);// Will enable hit and stand buttons
			
			/**
			 * By default/First turn it is true, After that it will be set base don the game state
			 */
			while (game.isTurnContinue()) {
				game.repaint();
			}
			
			game.setButtonState(false, false); //Disable buttons 
			game.repaint();
			game.doDealerTurn();
			game.repaint();
			game.doPayOuts();
			game.reset();
		}
	}

}
