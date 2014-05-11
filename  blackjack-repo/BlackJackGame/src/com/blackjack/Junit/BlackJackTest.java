package com.blackjack.JUnitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CardJUnitTest.class, HandJUnitTest.class, PlayerPanelTest.class,DealerPanelJUnitTest.class })
public class BlackJackTest {

}
