package com.blackjack.GUI;

import java.awt.event.*;
import javax.swing.*;

/**
 * A container of buttons for player options in Blackjack: Hit, Stand, Double,
 * Split, Surrender.
 * 
 * 
 */
public class ChoicePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton hit = new JButton("Hit");
	private JButton stand = new JButton("Stand");

	/**
	 * Makes a choice panel with the above buttons
	 */
	public ChoicePanel() {
		super();
		setOpaque(false);
		add(hit);
		add(stand);		
	}

	/**
	 * Enable hit button
	 */
	public void enableHit() {
		hit.setEnabled(true);
	}

	/**
	 * Enable stand button
	 */
	public void enableStand() {
		stand.setEnabled(true);
	}
	
	/**
	 * Disable hit button
	 */
	public void disableHit() {
		hit.setEnabled(false);
	}

	/**
	 * Disable stand button
	 */
	public void disableStand() {
		stand.setEnabled(false);
	}

	

	/**
	 * Adds a listener for these buttons. The listener should contain responses
	 * for each of these button commands.
	 * 
	 * @param a
	 *            listener of these buttons
	 */
	public void addListener(ActionListener a) {
		hit.addActionListener(a);
		stand.addActionListener(a);		
	}
}
