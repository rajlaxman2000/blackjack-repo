package com.blackjack.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
//import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.blackjack.bean.Card;
import com.blackjack.bean.Hand;

/**
 * A container that shows a player's name, remaining cash, and his hand. It also
 * contains fields for its current bet, the minimum bet, and the card image
 * file. The player can be called upon to do bets, insurance, a game action (for computers),
 * receive money/winnings, and clear his hand. This panel is not designed, however, 
 * to handle splits.
 * 
 */
public class PlayerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String name;
	private boolean isHuman;
	private int money;
	private int bet;
	private int minBet;
	private Hand hand;

	private Image cardImgs;

	private JLabel moneyDisp;
	private JLabel betDisp;

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

	// private static Random rnd = new Random();

	private int previousOutcome = 0;

	/**
	 * Creates a panel displaying the player's name, his remaining money, and
	 * his current bet, as well as his hand.
	 * 
	 * @param pName
	 *            Name of the player
	 * @param isHumanPlayer
	 *            is the player a human or a computer
	 * @param startMoney
	 *            the starting amount of money player has
	 * @param minimumBet
	 *            the minimum the player is allowed to bet
	 * @param cardImages
	 *            the card images file
	 */
	public PlayerPanel(String pName, boolean isHumanPlayer, int startMoney,int minimumBet, Image cardImages) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(99, 325));
		setOpaque(false);
		Color c = Color.DARK_GRAY;
		if (isHumanPlayer)
			c = Color.LIGHT_GRAY;
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(c), name));
		name = pName;
		isHuman = isHumanPlayer;
		money = startMoney;
		bet = 0;
		minBet = minimumBet;
		hand = null;
		cardImgs = cardImages;
		moneyDisp = new JLabel("$" + Integer.toString(money));
		moneyDisp.setForeground(new Color(87, 233, 100));
		moneyDisp.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
		betDisp = new JLabel("$" + Integer.toString(0));
		betDisp.setForeground(Color.RED);
		betDisp.setFont(new Font(Font.SERIF, Font.PLAIN, 14));

		add(moneyDisp);
		add(betDisp);
	}

	/**
	 * Returns the hand of the dealer, probably for comparison purposes.
	 * 
	 * @return dealers hand
	 */
	public Hand getHand() {
		return hand;
	}

	/**
	 * Clears the player's hand and returns it in an ArrayList	 * 
	 * @return ArrayList containing the cleared hand
	 */
	public ArrayList<Card> clearHand() {
		return hand.clearHand();
	}

	/**
	 * New hand for player with Card c1 and Card c2
	 * 
	 * @param c1
	 *            First Card in Hand
	 * @param c2
	 *            Second Card in Hand
	 */
	public void startHand(Card c1, Card c2) {
		hand = new Hand(c1, c2);
	}

	/**
	 * Gets the betting amount from player. If it is human, it pops up a dialog
	 * asking for an amount. For computers, it is calculated internally. The bet
	 * is automatically subtracted from the players total money.
	 * 
	 * @param count
	 *            Current card count
	 * @return amount to bet
	 */
	public int askBet(int count) {
		int betAmount = 0;
		if (isHuman) {
			betAmount = askHumanBet("Minimum Bet is $" + minBet + ". How much will are you betting?", minBet, money);
		}

		money = money - betAmount;
		bet = betAmount;
		updateText();
		return betAmount;
	}

	/**
	 * Adds to player's total money amount moneyWon.
	 * 
	 * @param moneyWon
	 *            amount of money to add
	 */
	public void addWinnings(int moneyWon) {
		money = money + moneyWon;
		updateText();

		if (moneyWon > bet) {
			this.previousOutcome = WIN;
			moneyDisp.setText(moneyDisp.getText() + "  ");
		} else if (moneyWon == bet) {
			previousOutcome = PUSH;
			moneyDisp.setText(moneyDisp.getText() + "  ");
		} else {
			previousOutcome = LOSS;
			moneyDisp.setText(moneyDisp.getText() + "  ");
		}
	}

	/**
	 * Resets player's betting amount and his hand.
	 * 
	 * @param c1
	 *            First Card in new Hand
	 * @param c2
	 *            Second Card in new Hand
	 */
	public void newRound(Card c1, Card c2) {
		bet = 0;
		updateText();
		startHand(c1, c2);
	}

	/**
	 * Gets the current betting amount of player.
	 * 
	 * @return player's current bet
	 */
	public int getCurrentBet() {
		return bet;
	}

	/**
	 * Gets the amount of money player has left.
	 * 
	 * @return player's remaining cash
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * Paints the cards stacked top-down in addition to the rest of the
	 * components. The cards are arranged so the user can still see all of the
	 * cards' values.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (hand == null)
			return;
		for (int i = 0; i < hand.length(); i++) {
			drawCard(g, hand.get(i), 10, 52 + 33 * i);
		}
	}

	/**
	 * Pops up an input dialog asking a question for amount to bet. Non-numbers
	 * and clicking on cancel/X result in getting "kicked out", but on merely
	 * illegal number values a new dialog asks for a correct input.
	 * 
	 * @param msg
	 *            question to ask to player
	 * @param min
	 *            minimum player can bet
	 * @param max
	 *            maximum player can bet
	 * @return
	 */
	private int askHumanBet(String msg, int min, int max) {
		int hBet = 0;
		String sBet = JOptionPane.showInputDialog(msg);
		try {
			hBet = Integer.valueOf(sBet);
			while (hBet < 0 || hBet < min || hBet > max) {
				String errReply;
				if (hBet < 0) {
					errReply = "Please provide +ve amount";
				} else if (hBet < min) {
					errReply = "You need to give minimum $" + min + " as bet amount";
				} else {
					errReply = "Given bet mount is not avaible in u r balance";
				}
				sBet = JOptionPane.showInputDialog(errReply);
				hBet = Integer.valueOf(sBet);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"You have not select correct otpion, So will be out of the game.");
			System.exit(0);
		}
		return hBet;
	}

	/**
	 * Updates the displays of player's remaining money and current bet.
	 */
	private void updateText() {
		moneyDisp.setText("$" + Integer.toString(money));
		betDisp.setText("$" + Integer.toString(bet));
	}

	/**
	 * Paints a card image onto (x,y) of the container. A face down card will be
	 * drawn accordingly.
	 * 
	 * @param g
	 *            the graphics context
	 * @param card
	 *            the card to be printed
	 * @param x
	 *            the x-position of the printed card in this container
	 * @param y
	 *            the y-position of the printed card in this container
	 */
	private void drawCard(Graphics g, Card card, int x, int y) {
		int cx; // top-left x of cardsImage
		int cy; // top-left y of cardsImage
		boolean faceUp = true;
		if (card.isFaceUp() != faceUp) {
			cx = 2 * 79;
			cy = 4 * 123;
		} else {
			cx = (card.getFace() - 1) * 79;
			switch (card.getSuit()) {
			case Card.DIAMONDS:
				cy = 123;
				break;
			case Card.CLUBS:
				cy = 0;
				break;
			case Card.HEARTS:
				cy = 2 * 123;
				break;
			default:
				cy = 3 * 123;
				break; // Spades
			}
		}
		g.drawImage(cardImgs, x, y, x + 79, y + 123, cx, cy, cx + 79, cy + 123,
				this);
	}

	/**
	 * Gets whether the player is a Human
	 * 
	 * @return Boolean representing whether the player is a human
	 */
	public boolean isHuman() {
		return isHuman;
	}
}
