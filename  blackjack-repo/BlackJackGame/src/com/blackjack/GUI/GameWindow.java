package com.blackjack.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.blackjack.bean.Card;
import com.blackjack.bean.Deck;

/**
 * Contains GUI components.
 * 
 */
public class GameWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	ChoicePanel playerChoices;
	public PlayerPanel human;

	private DealerPanel dealer;
	private Deck deck;
	public boolean turnContinue;

	private Image cardImages;
	
	/**
	 *  Minimum bet amount which will be accepted, this will be set while create this class object
	 */
	private int minBet;

	/**
	 * Money each player starts with
	 */
	private int initialMoney;

	/**
	 * Opens window containing Blackjack game.
	 */
	public GameWindow(int minBet, int initialMoney) {
		
		super("Geetha's : Blackjack");
		
		setMinBet(minBet);
		setInitialMoney(initialMoney);

		playerChoices = new ChoicePanel();
		getContentPane().setBackground(new Color(80, 135, 85));
		loadImages();
		initComponents();
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * Responds to button presses from the ChoicePanel.
	 * 
	 * @param a
	 *            The event that will be responded to
	 */
	@Override
	public void actionPerformed(ActionEvent a) {

		String command = a.getActionCommand();
		if (command.equals("Hit")) {
			giveCard(human);
			boolean busted = human.getHand().isBusted();
			turnContinue = !busted;
		} else if (command.equals("Stand")) {
			turnContinue = false;
		}
	}

	/**
	 * Adds components to the frame.
	 */
	private void initComponents() {
		deck = new Deck();
		turnContinue = true;

		setLayout(new BorderLayout(10, 10));

		this.dealer = new DealerPanel(this.minBet, cardImages);
		add(dealer, BorderLayout.LINE_START);

		JPanel players = new JPanel();
		players.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), "PLAYERS"));
		
		human = new PlayerPanel("Human - You", true, this.initialMoney,this.minBet, cardImages);

		players.add(human);
		players.setOpaque(false);
		
		add(players, BorderLayout.CENTER);
		playerChoices.addListener(this);
		add(playerChoices, BorderLayout.PAGE_END);
	}

	/**
	 * Gives or takes money from player
	 * 
	 * @param player
	 */
	private void payOut(PlayerPanel player) {

		// blackjack hands
		boolean playerHasBJ = player.getHand().isBlackJack();
		boolean dealerHasBJ = dealer.getHand().isBlackJack();
		if (playerHasBJ && dealerHasBJ) {
			player.addWinnings(player.getCurrentBet());
			if (player.isHuman())
				JOptionPane.showMessageDialog(this,"We both have Blackjack, a push. Your $"+ player.getCurrentBet() + "bet is returned.");
			return;
		} else if (playerHasBJ && !dealerHasBJ) {
			player.addWinnings(player.getCurrentBet() * 5 / 2);
			if (player.isHuman())
				JOptionPane.showMessageDialog(this, "Not bad, a Blackjack. You win $" + player.getCurrentBet() * 5 / 2 + ".");
			return;
		} else if (!playerHasBJ && dealerHasBJ) {
			player.addWinnings(0);
			if (player.isHuman())
				JOptionPane.showMessageDialog(this,"I have Blackjack. Sorry, you lose.");

			return;
		}

		// busting check
		boolean playerHasBusted = player.getHand().isBusted();
		boolean dealerHasBusted = dealer.getHand().isBusted();
		if (playerHasBusted) {
			player.addWinnings(0);
			if (player.isHuman())
				JOptionPane.showMessageDialog(this,"You have busted. Sorry, you lose.");
			return;
		} else if (dealerHasBusted) {
			player.addWinnings(player.getCurrentBet() * 2);
			if (player.isHuman())
				JOptionPane.showMessageDialog(this,"Damn, I've busted. You get $" + player.getCurrentBet()* 2 + ".");
			return;
		}

		// normal hands
		int playerValue = player.getHand().getBestValue();
		int dealerValue = dealer.getHand().getBestValue();
		if (playerValue > dealerValue) {
			player.addWinnings(player.getCurrentBet() * 2);
			if (player.isHuman())
				JOptionPane.showMessageDialog(this,
						"dealer Score=" + dealerValue + "Your Score="+ playerValue + " you've won. " + "Take your $"+ player.getCurrentBet() * 2 + ".");
			return;
		} else if (playerValue == dealerValue) {
			player.addWinnings(player.getCurrentBet());
			if (player.isHuman())
				JOptionPane.showMessageDialog(this, "dealer Score="+ dealerValue + "Your Score=" + playerValue+ "A push. Your $" 
												+ player.getCurrentBet()+ "bet is returned.");
			return;
		} else {
			player.addWinnings(0);
			if (player.isHuman())
				JOptionPane.showMessageDialog(this, "dealer Score="+ dealerValue + "Your Score=" + playerValue
												+ "My hand wins. Better luck" + "next time around.");
			return;
		}
	}

	/**
	 * Load card images file.
	 */
	private void loadImages() {
		ClassLoader cl = GameWindow.class.getClassLoader();
		URL imageURL = cl.getResource("cards.png");
		if (imageURL != null){
			cardImages = Toolkit.getDefaultToolkit().createImage(imageURL);
		} else {
			String errorMsg = "Card image file loading failed.";
			JOptionPane.showMessageDialog(this, errorMsg, "Error",JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * Deals cards to the dealer
	 * 
	 * @param dealer
	 *            The dealer to deal cards to
	 */
	private void dealerCards(DealerPanel dealer) {
		Card c1 = deck.draw();
		Card c2 = deck.draw();
		dealer.startHand(c1, c2);
		dealer.flipSecond();
	}

	/**
	 * Deals cards to the player
	 * 
	 * @param player
	 *            The player to deal cards to
	 */
	private void dealCards(PlayerPanel player) {
		Card c1 = deck.draw();
		Card c2 = deck.draw();
		player.startHand(c1, c2);
	}

	/**
	 * Collects cards from the dealer
	 */
	private void collectDealerCards() {
		ArrayList<Card> toCollect = dealer.clearHand();
		for (Card c : toCollect) {
			deck.addToBottom(c);
		}
	}

	/**
	 * Collects cards from the player
	 * 
	 * @param player
	 *            The player to collect cards from
	 */
	private void collectCards(PlayerPanel player) {
		ArrayList<Card> toCollect = player.clearHand();
		for (Card c : toCollect) {
			deck.addToBottom(c);
		}
	}

	/**
	 * Gives a card to the player
	 * 
	 * @param player
	 */
	private void giveCard(PlayerPanel player) {
		player.getHand().addCard(deck.draw());
	}

	/**
	 * Enables and disables some buttons
	 * 
	 * @param hitState
	 *            - The hit button
	 * @param standState
	 *            - The stand button
	 */
	void setButtonState(boolean hitState, boolean standState) {
		if (hitState)
			playerChoices.enableHit();
		else
			playerChoices.disableHit();
		if (standState)
			playerChoices.enableStand();
		else
			playerChoices.disableStand();
	}

	/**
	 * Asks for bets from players
	 */
	public void askBets() {
		human.askBet(deck.getCount());

	}

	/**
	 * Deals out cards to players and dealer
	 */
	void deal() {
		dealerCards(dealer);
		dealCards(human);

	}

	/**
	 * Does the dealer's turn
	 */
	public void doDealerTurn() {
		dealer.flipSecond();
		while (dealer.getHand().getBestValue() < 17) {
			dealer.getHand().addCard(deck.draw());
		}
	}

	/**
	 * Gives out the money
	 */
	public void doPayOuts() {
		payOut(human);
	}

	/**
	 * Clears the cards on the table
	 */
	public void reset() {
		collectCards(human);
		collectDealerCards();
		turnContinue = true;
	}

	/**
	 * @return the initialMoney
	 */
	public int getInitialMoney() {
		return initialMoney;
	}

	/**
	 * @param initialMoney the initialMoney to set
	 */
	public void setInitialMoney(int initialMoney) {
		this.initialMoney = initialMoney;
	}

	/**
	 * @return the minBet
	 */
	public int getMinBet() {
		return minBet;
	}

	/**
	 * @param minBet the minBet to set
	 */
	public void setMinBet(int minBet) {
		this.minBet = minBet;
	}
	
	

}