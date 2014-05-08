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
import com.blackjack.bean.Hand;

/**
 * Contains GUI components.
 * 
 */
public class GameWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private DealerPanel dealer;
	
	private PlayerPanel humanPlayer;
	
	public ChoicePanel playerChoices;
	
	private Deck deck;
	
	private boolean turnContinue;
	private Image cardImages;	

	/**
	 * Opens window containing Blackjack game.
	 */
	public GameWindow() {
		
		super(BlackJackUtil.WINDOW_HEADING);
		getContentPane().setBackground(new Color(80, 135, 85));

		//This will load the main card image into  cardImages variable
		loadImages();
		
		//Creates player and dealer panels 
		initComponents();
		
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}



	/**
	 * Adds components to the frame.
	 */
	private void initComponents() {
		
		this.deck = new Deck();
		setTurnContinue(true);

		setLayout(new BorderLayout(10, 10));

		this.dealer = new DealerPanel(BlackJackUtil.MIN_BET, this.cardImages);
		add(this.dealer, BorderLayout.LINE_START);

		JPanel players = new JPanel();
		players.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), "PLAYERS"));
		
		this.humanPlayer = new PlayerPanel("Human - You", true, BlackJackUtil.START_MONEY,BlackJackUtil.MIN_BET, this.cardImages);

		players.add(this.humanPlayer);
		players.setOpaque(false);
		
		add(players, BorderLayout.CENTER);
		
		playerChoices = new ChoicePanel();
		playerChoices.addListener(this);
		add(playerChoices, BorderLayout.PAGE_END);
	}

	/**
	 * Gives or takes money from player
	 * 
	 * @param player
	 */
	private void payOut(PlayerPanel player) {

		Hand playerHand = player.getHand();
		Hand dealerHand = this.dealer.getHand();
		
		// Black Jack Check
		boolean playerHasBJ = playerHand.isBlackJack();
		boolean dealerHasBJ = dealerHand.isBlackJack();
		
		if (playerHasBJ && dealerHasBJ) {
			player.addWinnings(player.getCurrentBet());
			if (player.isHuman())
				JOptionPane.showMessageDialog(this,"We both have Blackjack, a push. Your $"+ player.getCurrentBet() + "bet is returned.");
			return;
		} else if (playerHasBJ && !dealerHasBJ) {
			player.addWinnings(player.getCurrentBet() * 2);
			if (player.isHuman())
				JOptionPane.showMessageDialog(this, "Not bad, a Blackjack. You win $" + player.getCurrentBet() * 2 + ".");
			return;
		} else if (!playerHasBJ && dealerHasBJ) {
			player.addWinnings(0);
			if (player.isHuman())
				JOptionPane.showMessageDialog(this,"I have Blackjack. Sorry, you lose.");

			return;
		}

		// Busted Check
		boolean playerHasBusted = playerHand.isBusted();
		boolean dealerHasBusted = dealerHand.isBusted();
		
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

		// Normal Score Check
		int playerValue = playerHand.getBestValue();
		int dealerValue = dealerHand.getBestValue();
		
		if (playerValue > dealerValue) {
			player.addWinnings(player.getCurrentBet() * 2);
			if (player.isHuman())
				JOptionPane.showMessageDialog(this,
						" Dealer Score = " + dealerValue + "; Your Score = "+ playerValue + ";\n you've won. " + "Take your $"+ player.getCurrentBet() * 2 + ".");
			return;
		} else if (playerValue == dealerValue) {
			player.addWinnings(player.getCurrentBet());
			if (player.isHuman())
				JOptionPane.showMessageDialog(this, " Dealer Score = "+ dealerValue + "; Your Score = " + playerValue+ ";\n A push. Your $" 
												+ player.getCurrentBet()+ "bet is returned.");
			return;
		} else {
			player.addWinnings(0);
			if (player.isHuman())
				JOptionPane.showMessageDialog(this, " Dealer Score = "+ dealerValue + "; Your Score = " + playerValue
												+ ";\n My hand wins. Better luck next time around.");
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
			this.cardImages = Toolkit.getDefaultToolkit().createImage(imageURL);
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
		this.dealer.startHand(c1, c2);
		this.dealer.flipSecond();
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
		ArrayList<Card> collectedCards = dealer.clearHand();
		for (Card card : collectedCards) {
			this.deck.addToBottom(card);
		}
	}

	/**
	 * Collects cards from the player
	 * 
	 * @param player
	 *            The player to collect cards from
	 */
	private void collectCards(PlayerPanel player) {
		ArrayList<Card> collectedCards = player.clearHand();
		for (Card card : collectedCards) {
			this.deck.addToBottom(card);
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
	public void setButtonState(boolean hitState, boolean standState) {
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
		humanPlayer.askBet(deck.getCount());

	}

	/**
	 * Deals out cards to players and dealer
	 */
	public void deal() {
		dealerCards(dealer);
		dealCards(humanPlayer);

	}

	/**
	 * Does the dealer's turn
	 */
	public void doDealerTurn() {
		this.dealer.flipSecond();
		while (this.dealer.getHand().getBestValue() < 17) {
			this.dealer.getHand().addCard(deck.draw());
		}
	}

	/**
	 * Gives out the money
	 */
	public void doPayOuts() {
		payOut(this.humanPlayer);
	}

	/**
	 * Clears the cards on the table
	 */
	public void reset() {
		collectCards(humanPlayer);
		collectDealerCards();
		setTurnContinue(true);
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
			giveCard(humanPlayer);
			boolean busted = humanPlayer.getHand().isBusted();
			setTurnContinue(!busted);
		} else if (command.equals("Stand")) {
			setTurnContinue(false);
		}
	}

	/**
	 * @return the turnContinue
	 */
	public boolean isTurnContinue() {
		return turnContinue;
	}

	/**
	 * @param turnContinue the turnContinue to set
	 */
	public void setTurnContinue(boolean turnContinue) {
		this.turnContinue = turnContinue;
	}
	
	/**
	 * @return the cardImages
	 */
	public Image getCardImages() {
		return cardImages;
	}

	/**
	 * @param cardImages the cardImages to set
	 */
	public void setCardImages(Image cardImages) {
		this.cardImages = cardImages;
	}

	/**
	 * @return the humanPlayer
	 */
	public PlayerPanel getHumanPlayer() {
		return humanPlayer;
	}

	/**
	 * @param humanPlayer the humanPlayer to set
	 */
	public void setHumanPlayer(PlayerPanel humanPlayer) {
		this.humanPlayer = humanPlayer;
	}
	
	

}