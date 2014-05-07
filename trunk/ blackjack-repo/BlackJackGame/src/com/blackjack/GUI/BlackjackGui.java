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
	 * Runs the Game :)
	 * 
	 * @param args
	 *            Main Method
	 */
	public static void main(String[] args) {

		GameWindow game = new GameWindow();
		JOptionPane.showMessageDialog(game, "Welcome to the Blackjack Game.");
		while (true) {
			if (game.human.getMoney() < MIN_BET) {
				JOptionPane
						.showMessageDialog(game, "Sorry, no money, no play.");
				System.exit(0);
			}
			// System.out.println(game.deck.getCount());
			game.askBets();
			game.deal();
			game.repaint();
			//game.insurance();
			game.setButtonState(true, true, true, false, true);
			/*if (game.human.getCurrentBet() > game.human.getMoney())
				game.playerChoices.disableDouble();
			*/
			while (game.turnContinue) {
				game.repaint();
			}
			game.setButtonState(false, false, false, false, false);

			game.repaint();
			game.doDealerTurn();
			game.repaint();
			game.doPayOuts();
			game.reset();
		}
	}

}
