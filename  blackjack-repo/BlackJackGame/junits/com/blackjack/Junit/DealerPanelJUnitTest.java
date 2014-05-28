package com.blackjack.Junit;

import static org.junit.Assert.*;
import java.awt.Image;
import org.junit.Test;

import com.blackjack.GUI.DealerPanel;
import com.blackjack.bean.Card;
import com.blackjack.bean.Hand;

public class DealerPanelJUnitTest {
	Card c1 = new Card(4, 5);
	Card c2 = new Card(5, 6);
	Hand hand = new Hand(c1, c2);
	Image cardImages = null;
	DealerPanel d = new DealerPanel(5, cardImages);

	@Test
	public void teststartHand() {

		hand.getBestValue();
		assertEquals(11, hand.getBestValue());

	}

	@Test
	public void testcheckAce() {
		int face = hand.get(0).getFace();
		boolean result = false;
		if (face == Card.ACE)
			result = true;

		assertEquals(false, result);

	}

}
