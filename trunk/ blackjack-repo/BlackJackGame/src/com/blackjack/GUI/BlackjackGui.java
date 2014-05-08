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

		GameWindow gameWindow = new GameWindow();
		JOptionPane.showMessageDialog(gameWindow, BlackJackUtil.WELCOM_MSG);
		while (true) {
			int playerBalanceMoney = gameWindow.getHumanPlayer().getMoney();
			if (playerBalanceMoney < BlackJackUtil.MIN_BET) {
				JOptionPane.showMessageDialog(gameWindow, BlackJackUtil.OUT_OF_MNY_MSG);
				System.exit(0);
			}

			gameWindow.repaint();//Clears the cards from the screen.
			gameWindow.askBets();// Will ask for bet amount.
			gameWindow.deal(); // Cards will be dealt for Dealer as well as for player. (2 cards will be given for each.)
			gameWindow.repaint();	// Cards will be displayed on Screen		
			gameWindow.setButtonState(true, true);// Will enable hit and stand buttons
			
			/**
			 * By default/First turn it is true, After that it will be set base don the game state
			 */
			while (gameWindow.isTurnContinue()) {
				gameWindow.repaint();
			}
			
			gameWindow.setButtonState(false, false); //Disable buttons 
			gameWindow.repaint();
			gameWindow.doDealerTurn();
			gameWindow.repaint();
			gameWindow.doPayOuts();
			gameWindow.reset();
		}
	}

}
