package com.blackjack.GUI;

import javax.swing.*;

/**
 * Contains the main GUI and main method.
 * 
 */
public class BlackjackGui {	
	

	/**
	 * Runs the Game 
	 * 
	 * @param args - Main Method
	 */
	public static void main(String[] args) {

		GameWindow game = new GameWindow();
		JOptionPane.showMessageDialog(game, BlackJackUtil.WELCOM_MSG);
		while (true) {
			int playerBalanceMoney = game.getHumanPlayer().getMoney();
			if (playerBalanceMoney < BlackJackUtil.MIN_BET) {
				JOptionPane.showMessageDialog(game, BlackJackUtil.OUT_OF_MNY_MSG);
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
