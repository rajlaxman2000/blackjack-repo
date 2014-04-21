package com.blackjack.GUI;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;

import com.blackjack.bean.Card;
import com.blackjack.bean.Deck;


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
             * Contains GUI components.
             * 
             */                
        public class GameWindow extends JFrame implements ActionListener {
                /**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				private ChoicePanel playerChoices = new ChoicePanel();
                private PlayerPanel human;
                
                private DealerPanel dealer;
        private Deck deck;
        private boolean turnContinue;
               
                private Image cardImages;
               
                /**
                 * Opens window containing Blackjack game.
                 */
                public GameWindow() {
                        super("Quest 2011: Blackjack");
                        getContentPane().setBackground(new Color(80,135,85));
                        loadImages();
                        initComponents();
                        pack();
                        setLocationRelativeTo(null);
                        //setResizable(false);
                        setDefaultCloseOperation(EXIT_ON_CLOSE);
                        setVisible(true);
                }
               
                /**
                 * Responds to button presses from the ChoicePanel.
                 * @param a The event that will be responded to
                 */
                @Override
                public void actionPerformed(ActionEvent a) {
                        String command = a.getActionCommand();
                        String bop = "That tickles!"; //Placeholder for executing actual command
                        if (command.equals("Hit")) {
                                giveCard(human);
                                boolean busted = human.getHand().isBusted();
                                turnContinue = !busted;
                                playerChoices.disableSurrender();
                                playerChoices.disableDouble();
                        } else if (command.equals("Stand")) {
                                turnContinue = false;
                        } else if (command.equals("Double")){
                                human.doubleDown();
                                giveCard(human);
                                turnContinue = false;
                        } else if (command.equals("Split")) {
                                JOptionPane.showMessageDialog(this, bop);
                        } else if (command.equals("Surrender")) {                              
                                JOptionPane.showMessageDialog(this, " Fine,  " +
                                                "take back $" + human.getCurrentBet() / 2 + ".");
                                collectCards(human);
                                human.addWinnings(human.getCurrentBet() / 2);
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
                       
                        dealer = new DealerPanel(MIN_BET, cardImages);
                        add(dealer, BorderLayout.LINE_START);
                       
                        JPanel players = new JPanel();
                        players.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), "PLAYERS"));
                        human = new PlayerPanel("Human - You", true,-1, START_MONEY, MIN_BET, cardImages);
                      
                        players.add(human);
                      
                        players.setOpaque(false);
                        add(players, BorderLayout.CENTER);      
                        playerChoices.addListener(this);
                        add(playerChoices, BorderLayout.PAGE_END);              
                }

                /**
                 * Gives or takes money from each player
                 * @param player
                 */
                private void payOut(PlayerPanel player) {      
                        // surrender check
                        if (player.getHand().length() == 0)
                                return;
                       
                        // blackjack hands
                        boolean playerHasBJ = player.getHand().isBlackJack();
                        boolean dealerHasBJ = dealer.getHand().isBlackJack();
                        if (playerHasBJ && dealerHasBJ) {
                                player.addWinnings(player.getCurrentBet());
                                if (player.isHuman())
                                        JOptionPane.showMessageDialog(this, "We both have Blackjack," +
                                                        " a push. Your $" + player.getCurrentBet() + "bet is returned.");
                                return;
                        } else if (playerHasBJ && !dealerHasBJ) {
                                player.addWinnings(player.getCurrentBet() * 5 / 2);
                                if (player.isHuman())
                                        JOptionPane.showMessageDialog(this, "Not bad, a Blackjack. " +
                                                        "You win $" + player.getCurrentBet() * 5 / 2 + ".");
                                return;
                        } else if (!playerHasBJ && dealerHasBJ) {
                                player.addWinnings(0);
                                if (player.isHuman())
                                        JOptionPane.showMessageDialog(this, "I have Blackjack. " +
                                                        "Sorry, you lose.");
                               
                                return;
                        }
                       
                        // busting check
                        boolean playerHasBusted = player.getHand().isBusted();
                        boolean dealerHasBusted = dealer.getHand().isBusted();
                        if (playerHasBusted) {
                                player.addWinnings(0);
                                if (player.isHuman())
                                        JOptionPane.showMessageDialog(this, "You have busted. " +
                                                        "Sorry, you lose.");
                                return;
                        } else if (dealerHasBusted) {
                                player.addWinnings(player.getCurrentBet() * 2);
                                if (player.isHuman())
                                        JOptionPane.showMessageDialog(this, "Damn, I've busted. " +
                                                        "You get $" + player.getCurrentBet() * 2 + ".");
                                return;
                        }

                        // normal hands
                        int playerValue = player.getHand().getBestValue();
                        int dealerValue = dealer.getHand().getBestValue();      
                        if (playerValue > dealerValue) {
                                player.addWinnings(player.getCurrentBet() * 2);
                                if (player.isHuman())
                                        JOptionPane.showMessageDialog(this, " you've won. " +
                                                        "Take your $" + player.getCurrentBet() * 2 + ".");
                                return;
                        } else if (playerValue == dealerValue){
                                player.addWinnings(player.getCurrentBet());
                                if (player.isHuman())
                                        JOptionPane.showMessageDialog(this, "A push. Your $" +
                                                        player.getCurrentBet() + "bet is returned.");
                                return;
                        } else {
                                player.addWinnings(0);
                                if (player.isHuman())
                                        JOptionPane.showMessageDialog(this, "My hand wins. Better luck" +
                                                        "next time around.");
                                return;
                        }
                }
               
                /**
                 * Asks for insurance bets from each player
                 * @param player
                 */
                private void doInsurance(PlayerPanel player) {
                    int insureBet = player.askInsurance(deck.getCount());
                    if (insureBet == 0)
                        return;
                    if (dealer.getHand().isBlackJack()) {
                        player.addWinnings(insureBet * 3);
                        if (player.isHuman())
                                JOptionPane.showMessageDialog(this, "I have Blackjack. " +
                                                "Take your $" + insureBet * 3 + ".");
                                turnContinue = false;
                        }
                    else {
                        if (player.isHuman())
                                JOptionPane.showMessageDialog(this, "Lucky you, I don't have Blackjack. " +
                                "You lose your $" + insureBet + " bet, but you still have a " +
                                "chance to win.");
                    }
                }
               
                /**
                 * Load card images file.
                 */
                private void loadImages() {
                    ClassLoader cl = GameWindow.class.getClassLoader();
                    URL imageURL = cl.getResource("cards.png");
                    if (imageURL != null)
                         cardImages = Toolkit.getDefaultToolkit().createImage(imageURL);
                    else {
                         String errorMsg = "Card image file loading failed.";
                         JOptionPane.showMessageDialog(this, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
                         System.exit(1);
                    }                  
                }
               
                /**
                 * Deals cards to the dealer
                 * @param dealer The dealer to deal cards to
                 */
                private void dealerCards(DealerPanel dealer){
                    Card c1 = deck.draw();
                    Card c2 = deck.draw();
                    dealer.startHand(c1, c2);
                    dealer.flipSecond();                    
                }
               
                /**
                 * Deals cards to the player
                 * @param player The player to deal cards to
                 */
                private void dealCards(PlayerPanel player){
                    Card c1 = deck.draw();
                    Card c2 = deck.draw();
                    player.startHand(c1, c2);
                }
               
                /**
                 * Collects cards from the dealer
                 */
                private void collectDealerCards(){
                    ArrayList<Card> toCollect = dealer.clearHand();
                    for(Card c: toCollect){
                        deck.addToBottom(c);
                    }
                }
               
                /**
                 * Collects cards from the player
                 * @param player The player to collect cards from
                 */
                private void collectCards(PlayerPanel player){
                    ArrayList<Card> toCollect = player.clearHand();
                    for(Card c: toCollect){
                        deck.addToBottom(c);
                    }
                }
               
                /**
                 * Gives a card to the player
                 * @param player
                 */
                private void giveCard(PlayerPanel player){
                    player.getHand().addCard(deck.draw());
                }
               
                /**
                 * Enables and disables some buttons
                 * @param hitState The hit button
                 * @param standState The stand button
                 * @param doubleState The double button
                 * @param splitState The split button
                 * @param surrenderState The surrender button
                 */
                private void setButtonState(boolean hitState, boolean standState,
                                boolean doubleState, boolean splitState, boolean surrenderState) {
                        if (hitState) playerChoices.enableHit(); else playerChoices.disableHit();
                        if (standState) playerChoices.enableStand(); else playerChoices.disableStand();
                        if (doubleState) playerChoices.enableDouble(); else playerChoices.disableDouble();
                        if (splitState) playerChoices.enableSplit(); else playerChoices.disableSplit();
                        if (surrenderState) playerChoices.enableSurrender(); else playerChoices.disableSurrender();
                }
               
                /**
                 * Asks for bets from players
                 */
                private void askBets() {
                        human.askBet(deck.getCount());
                      
                }    
               
                /**
                 * Deals out cards to players and dealer
                 */
                private void deal() {
                        dealerCards(dealer);
                        dealCards(human);
                       
                }                
               
                /**
                 * Asks for insurance bets from players
                 */
                public void insurance() {
                                if (dealer.checkAce()) {
                                        doInsurance(human);
                                       
                                }
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
                private void reset() {
                        collectCards(human);
                       
                        collectDealerCards();  
                        turnContinue = true;
                }
            }
       
        /**
         * Runs the Game :)
         * @param args Main Method
         */
        public static void main(String[] args) {
                BlackjackGui b = new BlackjackGui();
        GameWindow game = b.new GameWindow();
        JOptionPane.showMessageDialog(game, "Welcome to the Blackjack Game.");        
        while (true) {
                if (game.human.getMoney() < MIN_BET) {
                        JOptionPane.showMessageDialog(game, "Sorry, no money, no play.");
                        System.exit(0);
                }
                //System.out.println(game.deck.getCount());
                game.askBets();
                game.deal();            
                game.repaint();
                game.insurance();
                game.setButtonState(true, true, true, false, true);
                if (game.human.getCurrentBet() > game.human.getMoney())
                        game.playerChoices.disableDouble();
                while (game.turnContinue) { game.repaint(); }          
                game.setButtonState(false, false, false, false, false);                
              
                game.repaint();
                game.doDealerTurn();
                game.repaint();      
                game.doPayOuts();
                game.reset();          
        }        
        }

}



