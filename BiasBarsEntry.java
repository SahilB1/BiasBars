/*
 * File: BiasBarsEntry.java
 * --------------------------
 * This class represents a single entry in the database. Each
 * BiasBarsEntry contains a descriptor and two mappings from
 * comment source to number of occurrences of the descriptor, one
 * for women and one for men
 */

import acm.program.ConsoleProgram;
import acm.util.*;
import java.util.*;
import java.io.*;

import javax.swing.JTextField;

public class BiasBarsEntry extends ConsoleProgram {

	// TODO: Add instance variable(s)
	private String lines;
	private Scanner readInput;
	private ArrayList<String> linesOutput;
	private String descriptor;
	private String scannerToString;
	public ArrayList<Integer> womenNumbers = new ArrayList<Integer>();
	public ArrayList<Integer> mensNumbers = new ArrayList<Integer>();
	private ArrayList<Integer> resultArrayList;
	

	/**
	 * Creates a new BiasBarsEntry from a data line as it appears
	 * in the data file. Each line begins with the descriptor, which is
	 * followed by integers giving the occurrence of that descriptor in
	 * the RtGender database of social media comments. Before each list
	 * of integers is a letter "W" or "M" denoting which gender those
	 * frequencies correspond to
	 */
	public BiasBarsEntry(String line) {
		readInput = new Scanner(line);
		scannerToString = String.valueOf(readInput.nextLine());
		linesOutput = new ArrayList<>(Arrays.asList(scannerToString.split(" ")));
		System.out.println("LINE ARRAY LIST: " + linesOutput);
		for(int i = 0; i < linesOutput.size(); i++) {
			//System.out.println(linesOutput[i]);
		}
		//System.out.println(Arrays.toString(linesOutput));
		getDescriptor();
		getFrequencies('W');
		getFrequencies('M');
		toString();
	}
	
	/**
	 * Returns the descriptor associated with this entry.
	 */
	public String getDescriptor() {
		descriptor = linesOutput.get(0);
		return descriptor;
	}

	/**
	 * Returns the frequencies associated with an entry for a particular
	 * comment source and gender.
	 */
	public ArrayList<Integer> getFrequencies(char gender) {
		if(gender != 'M' && gender != 'W') {
			return null;
		}
		int startIndex = 0;
		//resultArrayList = new ArrayList<>();
		System.out.println(linesOutput.size());
		for(int i = 0; i < linesOutput.size(); i++) {
			System.out.print(linesOutput.get(i));
			if(linesOutput.get(i).equals(String.valueOf(gender)) || linesOutput.get(i).equals("")) {
				startIndex = i;
				System.out.println("start index: " + startIndex);
				break;
			}
		}
		
		// Loops over each index in the line in order to append the
		// numbers to an ArrayList of the numbers accordingly
		for(int i = startIndex + 1; i < linesOutput.size(); i++) {
			if(gender == 'W') {
				while(linesOutput.get(i) != "M") {
					if(linesOutput.get(i).equals("M")) {
						break;
					}
					else {
						if(linesOutput.get(i).equals("W,")) {
							i++;
						}
						womenNumbers.add(Integer.valueOf(linesOutput.get(i)));
					}
					i++;
				}
				resultArrayList = womenNumbers;
				break;
			} else {
				while(i != linesOutput.size()) {
					try {
						mensNumbers.add(Integer.valueOf(linesOutput.get(i)));
						i++;
					} catch (NumberFormatException e) {
						i++;
					}
				}
				resultArrayList = mensNumbers;
				linesOutput.clear();
				linesOutput = new ArrayList<>(Arrays.asList(scannerToString.split(" ")));
			}
		}
		return resultArrayList;
	}

	/**
	 * Returns the highest frequency associated with a descriptor for any
	 * combination of comment source and gender to help with deciding the
	 * y-axis scale during graphing.
	 */
	public int getMaxFreq() {
		int maxVal = 0;
		int result = 0;
		int resultFinal = 0;
		for(int i = 0; i < resultArrayList.size(); i++) {
			if(resultArrayList.get(i) > maxVal) {
				maxVal = resultArrayList.get(i);
				result =  maxVal / 10;
				resultFinal = result*10;
				}
		}
		getFrequencies('M');
		for(int i = 0; i < resultArrayList.size(); i++) {
			if(resultArrayList.get(i) > maxVal) {
				maxVal = resultArrayList.get(i);
				result =  maxVal / 10;
				resultFinal = result*10;
				}
		}
		return resultFinal;
	}

	/**
	 * Returns a string that makes it easy to see the value of a BiasBarsEntry.
	 */
	public String toString() {
		String toString = descriptor + ": " + "{W=" + womenNumbers + ", " + "M=" + mensNumbers + "}";
		return toString;
	}
}

