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
		PlayerPanel p=new PlayerPanel("player", true, 10, 5,i );		
		assertEquals(5, p.askBet(5));
	}	
	
}
