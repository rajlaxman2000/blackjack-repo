package com.blackjack.Junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CardJunitTest.class, DeckJunitTest.class, HandJunitTest.class,
		PlayerPanelTest.class })
public class BlackJackMainTests {

}
