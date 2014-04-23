package com.mypack;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddNumbers implements ActionListener {

	JFrame frame;
	JPanel panel1;
	JPanel panel2;
	JButton addbutton, resetbutton;
	JTextField num1, num2;
	JLabel headerlabel, resultlabel;

	public AddNumbers() {
		prepareGui();
	}

	public static void main(String[] args) {
		AddNumbers a = new AddNumbers();
		a.init();
	}

	public void prepareGui() {
		frame = new JFrame("My Frame");
		headerlabel = new JLabel("", JLabel.CENTER);
		resultlabel = new JLabel("", JLabel.CENTER);
		num1 = new JTextField(6);
		num2 = new JTextField(6);
		addbutton = new JButton("ADD");
		resetbutton = new JButton("RESET");

		frame.setSize(300, 350);
		frame.setLayout(new GridLayout(4, 1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.add(addbutton);
		panel1.add(resetbutton);

		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		panel2.add(num1);
		panel2.add(num2);

		frame.add(headerlabel);
		frame.add(panel2);
		frame.add(panel1);
		frame.add(resultlabel);

		frame.setVisible(true);
	}

	public void init() {
		headerlabel.setText("ADDITION");
		resultlabel.setText("RESULT : ");
		addbutton.setActionCommand("add");
		addbutton.addActionListener(this);
		resetbutton.setActionCommand("reset");
		resetbutton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = new String(e.getActionCommand());

		if (s.equalsIgnoreCase("add")) {
			try {
				int a = Integer.parseInt(num1.getText());
				int b = Integer.parseInt(num2.getText());
				Integer c = a + b;
				resultlabel.setText(c.toString());
			} catch (NumberFormatException nfe) {
				nfe.getMessage();
				resultlabel.setText("Invalid Input");
			}
		} else if (s.equalsIgnoreCase("reset")) {
			num1.setText("");
			num2.setText("");
			resultlabel.setText("");
		}
	}

}
