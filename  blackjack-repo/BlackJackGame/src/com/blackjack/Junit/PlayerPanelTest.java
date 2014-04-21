package com.blackjack.Junit;

import static org.junit.Assert.*;

import java.awt.Image;

import org.junit.Test;

import com.blackjack.GUI.PlayerPanel;
import com.blackjack.bean.Card;



public class PlayerPanelTest {

	@Test
	public void testaskBet(){
		Image i =null;
		PlayerPanel p=new PlayerPanel("player", true, 1, 10, 5,i );
		
		//assertEquals(5, p.askBet(5));
	}
	
	
	@Test
	public void testaskComputerAction(){
		Image i =null;
		PlayerPanel p=new PlayerPanel("Player", true, 1, 20, 5,i );
		Card card=new Card(3, 4);
		
		assertEquals(-1, p.askComputerAction(card));
	}
	
	@Test
	public void testaskInsurance(){
		Image i =null;
		PlayerPanel p=new PlayerPanel("Player", true, 1, 20, 5,i );
		//int insureBet=0;
		//System.out.println(p.askInsurance(insureBet));
		
		//assertEquals(0, p.askInsurance(0));
	}
	
	
	
}
