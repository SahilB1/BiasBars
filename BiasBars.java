/*
 * File: BiasBars.java
 * ---------------------
 * When it is finished, this program will implement the viewer for
 * the descriptor frequency data described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;


public class BiasBars extends Program {
	
	/** The name of the file containing the data */
	private static final String DATA_FILE = "res/gender-data.txt";
	/** The width of the text field in the NORTH of the window */
	private static final int TEXT_FIELD_WIDTH = 16;
	
	BiasBarsDataBase database = new BiasBarsDataBase(DATA_FILE);
	BiasBarsGraph graph = new BiasBarsGraph();
	
	JTextField descriptor;
	
	/**
	 * This method has the responsibility for creating the database
	 * and initializing the interactors at the top of the window.
	 */

	// renders the search bar and buttons for the user at the start of the program 
	public void init() {
		setTitle("BiasBars");
		JLabel label = new JLabel("Descriptor: ");
		descriptor = new JTextField(TEXT_FIELD_WIDTH);
		JButton graphButton = new JButton("Graph");
		JButton clearButton = new JButton("Clear");
		add(label, NORTH);
		add(descriptor, NORTH);
		add(graphButton, NORTH);
		add(clearButton, NORTH);
		addActionListeners();
		descriptor.addKeyListener(this);
		add(graph);
	}

	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked, so you will have to define a method to respond to
	 * button actions.
	 */

	// Listens to which button the user clicked on and responds accordingly
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Graph")) {
			// Accesses method from the constructor of BiasBarsDataBase
			graph.addEntry(database.findEntry(descriptor.getText()));
		}
		if(e.getActionCommand().equals("Clear")) {
			descriptor.setText("");
		}
	}
	
	// Checks to see if the user hit the "enter" key
	// This is the same as the user clicking on the "graph" button
	// Just an additional way to do the same thing
	public void keyPressed(KeyEvent e) {
		int enterKey = 10;
		if(e.getKeyCode() == enterKey) {
			graph.addEntry(database.findEntry(descriptor.getText()));
		}
	}
}