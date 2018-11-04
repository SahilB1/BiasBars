/*
 * File: BiasBarsDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of descriptors.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * descriptor and get back the corresponding BiasBarsEntry.
 * Descriptors are matched independent of case, so that "nice"
 * and "NICE" are the same word.
 */

import acm.util.*;
import java.util.*;
import java.io.*;

public class BiasBarsDataBase {
	
	private static int NUMBER_OF_LINES = 2892;
	
	private Scanner data;
	private String[][] dataToString = new String[NUMBER_OF_LINES][];
	private String one;
	private String[] two;
	private String newString = "";
	
	BiasBarsEntry entryResult;
		
	/**
	 * Constructor: BiasBarsDataBase(filename)
	 * Creates a new BiasBarsDataBase and initializes it using the
	 * data in the specified file. The constructor throws an error
	 * exception if the requested file does not exist or if an error
	 * occurs as the file is being read.
	 */
 	public BiasBarsDataBase(String filename) {
		try {
			data = new Scanner(new File(filename));
			for(int i = 0; i < NUMBER_OF_LINES; i++) {
					one = String.valueOf(data.nextLine());
					two = one.split(" ");
					dataToString[i] = two;
			}
		} catch(FileNotFoundException ex) {
			System.out.println("File not found " + ex);
		}
	}

	/**
	 * Returns the BiasBarsEntry associated with this descriptor,
	 * if one exists. If the descriptor does not appear in the database, this
	 * method returns null.
	 */
	public BiasBarsEntry findEntry(String descriptor) {
		for(int i = 0; i < dataToString.length ; i++) {
				if(dataToString[i][0].toUpperCase().equals(descriptor.toUpperCase())) {
					System.out.println("line: " + Arrays.toString(dataToString[i]));
					String valuesInsideBrackets = Arrays.toString(dataToString[i]).substring(1, (Arrays.toString(dataToString[i]).length() - 1));
					System.out.println(Arrays.toString(dataToString[i]).length());
					for(int j = 0; j < valuesInsideBrackets.length(); j++) {
						if(valuesInsideBrackets.charAt(j) != ',') {
							newString += valuesInsideBrackets.charAt(j);
						}
					}
					newString += " ";
					entryResult = new BiasBarsEntry(newString);
				}
		}
		newString = "";
		return entryResult; 
	}
}

