
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashSet;


public class A_2_Sum_Problem {

	// Test flag
//	static boolean TEST = true;		// answer = 7
	static boolean TEST = false;	// answer = 427
	
	// Filename
	static String assignment = "algo1-programming_prob-2sum.txt";
	static String test = "Test1.txt";
	
	static String filename;

	 // Size of input array
	static int inputSize;
	// int array for input data
	static long[] inputArray = new long[inputSize];


	public static void main(String[] args) {

		// Target range
		int low;
		int high;

		// TEST
		if (TEST) {
			filename = test;
			inputSize = 6;
			low = 4;
			high = 11;
		} else {
			filename = assignment;
			inputSize = 1000000;
			low = -10000;
			high = 10000;
		}

		// Initialize the input array
		inputArray = new long[inputSize];

		// Generate the sorted input array
		getSortedInputArray(filename);

		// Hash set of results
		HashSet<Long> resultSet = new HashSet<>();

		// Initialize the pointers
		int start = 0;
		int end = inputSize - 1;

		// Loop through the list
		while (start < end) {
			// Total > 10000
			if (inputArray[start] + inputArray[end] > high)
				end -= 1;
			else
				// Total < -10000
				if (inputArray[start] + inputArray[end] < low)
					start += 1;
				else {
					// Look for entries within the range
					for (int i = start; i < end; i++){
						// Calculate the total
						long total = inputArray[i] + inputArray[end];
						// Check in within the range
						// And not the same values
						if ( total >= low &&
							 total <= high &&
							 (inputArray[i] != inputArray[end]))
							// Add to the hash set
							resultSet.add(total);
						else
							break;
					}
					end -= 1;
				}	// within range
		}	// while (start < end)

		// Output the results
		System.out.println("Total number of pairs = " + Double.toString(resultSet.size()));

	}	// end - Main()

	// Routine to read input data
	public static void getSortedInputArray(String filename) {

		// Declare a scanner
		Scanner scInput = null;

		// Initialize the scanner
		try {
			scInput = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Insert the integers from the file into the int array
		for (int i = 0; i < inputSize; i++) {
			// Add input entry to input array
			inputArray[i] = scInput.nextLong();
		}

		// Sort the input array
		Arrays.sort(inputArray);
		
	}	// end - getInputArray()

}	// end - A_2_Sum_Problem class
