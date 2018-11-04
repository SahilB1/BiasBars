/*
 * File: BiasBarsGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * frequencies is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the current entry changes
 * or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.Color;

public class BiasBarsGraph extends GCanvas implements ComponentListener {
	
	/** An array of the comment sources for the data. The order of the data in gender-data.txt will line up with this array. */	
	private static final String[] COMMENT_SOURCES = {"Facebook (pol.)", "Facebook (celeb.)", "TEDTalks", "Reddit", "Fitocracy"};

	/** The number of frequencies to label, from 0 to the maximum frequency shown */
	private static final int NLABELS = 11;

	/** The number to round the up to when choosing the maximum value shown on the y-axis */
	private static final int MAGNITUDE = 10;
	
	/** The total width of the bars for each comment source (the width of an actual bar will be half of this) */
	private static final int BARS_WIDTH = 150;

	/** The number of pixels to reserve at the top and bottom of the canvas */
	private static final int VERTICAL_MARGIN = 30;
	
	/** The number of pixels between the left side of the canvas and the left y-axis */
	private static final int LEFT_MARGIN = 50;
	
	/** The number of pixels between the right side of the canvas and the right y-axis */
	private static final int RIGHT_MARGIN = 30;
	
	/** The total length of each tick mark drawn on the y-axis */
	private static final int TICK_MARK_LENGTH = 8;
	
	/** The number of pixels between each axis and its labels */
	private static final int LABEL_OFFSET = 5;

	/** The maximum frequency on the y-axis when there is no current entry */
	private static final int DEFAULT_MAX_FREQ = 1000;

	private String descriptor;
	private ArrayList<Integer> men;
	private ArrayList<Integer> women;
	private double max;
	
	
		
	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public BiasBarsGraph() {
		addComponentListener(this);
	}
	
	
	/**
	* Clears the current entry stored inside this class. This method should 
	* not affect the appearance of the graph; that is done by calling update.
	*/
	public void clear() {
		update();
	}
	
	
	/**
	* Specifies a BiasBarsEntry to be the entry shown on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(BiasBarsEntry entry) {
		// Checks to see if the entry is null and 
		// if it is, calls a method that sets the 
		// graph to be empty of data but does not throw errors
		if(entry == null) {
			initGraph();
		} else {
			men = entry.getFrequencies('M');
			women = entry.getFrequencies('W');
			max = entry.getMaxFreq();
			descriptor = entry.getDescriptor();
			update();
		}
	}
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the current entry. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		GRect showGraph = new GRect(LEFT_MARGIN, VERTICAL_MARGIN, getWidth() - (LEFT_MARGIN*2), getHeight() - (VERTICAL_MARGIN*2));
		double x;
		for(int i = 0; i < 11; i++) {
			double tickDistance = (showGraph.getHeight() / 10) * i ;
			double tickY = VERTICAL_MARGIN + showGraph.getHeight() - tickDistance;
			if(i == 0) {
				x = 0;
			} else {
				x =  (max / 10) *i;
			}
			// We convert between a double to a string since the argument must be a string
			// but we cannot perform mathematical operations on a string
			String stringX = String.valueOf(x);
			String newX = stringX.substring(0, stringX.length() - 2);
			GLabel numbers = new GLabel(newX);
			numbers.setLocation(LEFT_MARGIN - numbers.getWidth() - LABEL_OFFSET, tickY + numbers.getHeight()/4) ;
			GLine tickX = new GLine(LEFT_MARGIN - (TICK_MARK_LENGTH/2), tickY, LEFT_MARGIN + (TICK_MARK_LENGTH/2), tickY);
			if (tickY >= VERTICAL_MARGIN) {
				add(tickX);
				add(numbers);
			}
		}
		add(showGraph);
		double commentSourcesXVal = 0;
		for(int j = 0; j < COMMENT_SOURCES.length; j++) {
			GLabel commentSource = new GLabel(COMMENT_SOURCES[j]);
			commentSourcesXVal = LEFT_MARGIN - commentSource.getWidth() / 2 + ((showGraph.getWidth() / 6) * (j + 1));
			commentSource.setLocation(commentSourcesXVal, (showGraph.getHeight() + VERTICAL_MARGIN * 2 - LABEL_OFFSET));
			commentSource.setFont("*-BOLD-16");
			add(commentSource);
		}
		for(int i = 0; i < 5; i++) {
			if(women == null || men == null) {
				break;
			}
			// Creates the bars for each entry
			double womenRatio = (women.get(i)/max);
			double menRatio = (men.get(i)/max);
			double mensX = LEFT_MARGIN + ((showGraph.getWidth()/6) * (i + 1)) - BARS_WIDTH/2;
			double mensY = VERTICAL_MARGIN + showGraph.getHeight() - (menRatio * showGraph.getHeight());
			double womensX = LEFT_MARGIN + ((showGraph.getWidth()/6) * (i + 1));
			double womensY = VERTICAL_MARGIN + showGraph.getHeight() - (womenRatio * showGraph.getHeight());
			GRect barsMen = new GRect(mensX, mensY, BARS_WIDTH/2, menRatio * showGraph.getHeight());
			GRect barsWomen = new GRect(womensX, womensY, BARS_WIDTH/2, womenRatio * showGraph.getHeight());
			barsMen.setFilled(true);
			barsMen.setFillColor(Color.RED);
			barsWomen.setFilled(true);
			barsWomen.setFillColor(Color.CYAN);
			GLabel genderMale = new GLabel("M");
			GLabel genderFemale = new GLabel("F");
			if (barsMen.getHeight() > genderMale.getHeight() || barsWomen.getHeight() > genderFemale.getHeight()) {
				genderMale.setLocation(mensX + 5, mensY + genderMale.getHeight());
				genderFemale.setLocation(womensX + 5, womensY + genderFemale.getHeight());
			} else {
				genderMale.setLocation(mensX + 5, mensY - genderMale.getHeight()/2);
				genderFemale.setLocation(womensX + 5, womensY - genderFemale.getHeight()/2);
			}
			add(barsMen);
			add(barsWomen);
			add(genderFemale);
			add(genderMale);
		} 
	}
	
	// Creates an empty graph to show in case the user's first attempt is null
	public void initGraph() {
		GRect showGraph = new GRect(LEFT_MARGIN, VERTICAL_MARGIN, getWidth() - (LEFT_MARGIN*2), getHeight() - (VERTICAL_MARGIN*2));
		double x;
		for(int i = 0; i < 11; i++) {
			double tickDistance = (showGraph.getHeight() / 10) * i ;
			double tickY = VERTICAL_MARGIN + showGraph.getHeight() - tickDistance;
			if(i == 0) {
				x = 0;
			} else {
				x =  (max / 10) *i;
			}
			String stringX = String.valueOf(x);
			String newX = stringX.substring(0, stringX.length() - 2);
			GLabel numbers = new GLabel(newX);
			numbers.setLocation(LEFT_MARGIN - numbers.getWidth() - LABEL_OFFSET, tickY + numbers.getHeight()/4) ;
			GLine tickX = new GLine(LEFT_MARGIN - (TICK_MARK_LENGTH/2), tickY, LEFT_MARGIN + (TICK_MARK_LENGTH/2), tickY);
			if (tickY >= VERTICAL_MARGIN) {
				add(tickX);
				add(numbers);
			}
		}
		add(showGraph);
		double commentSourcesXVal = 0;
		for(int j = 0; j < COMMENT_SOURCES.length; j++) {
			GLabel commentSource = new GLabel(COMMENT_SOURCES[j]);
			commentSourcesXVal = LEFT_MARGIN - commentSource.getWidth() / 2 + ((showGraph.getWidth() / 6) * (j + 1));
			commentSource.setLocation(commentSourcesXVal, (showGraph.getHeight() + VERTICAL_MARGIN * 2 - LABEL_OFFSET));
			commentSource.setFont("*-BOLD-16");
			add(commentSource);
		}
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); initGraph(); }
	public void componentShown(ComponentEvent e) { }
}
