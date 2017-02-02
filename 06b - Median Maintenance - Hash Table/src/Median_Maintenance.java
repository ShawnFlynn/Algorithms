
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Median_Maintenance {

	// Test flag
//	static boolean TEST = true;		// answer = 9335
	static boolean TEST = false;	// answer = 

	// Filename
	static String assignment = "Median.txt";
	static String test = "Test1.txt";

	// Input filename
	static String filename;

	// Input size
	static int inputSize;


	public static void main(String[] args) {

		// TEST
		if (TEST) {
			filename = test;
			inputSize = 10;
		} else {
			filename = assignment;
			inputSize = 10000;
		}

		// Hash set of results
		Map<Integer, Integer> inputMap = new HashMap<>();

		// Initialize the median key
		int medianKey = 0;

		// Define the median total
		long medianTotal = 0;

		// Declare a scanner
		Scanner scInput = null;

		// Initialize the scanner
		try {
			scInput = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Loop through all of the input values
		for (int key = 1; key <= inputSize; key++) {

			// Get the next input entry
			int inputValue = scInput.nextInt();

			// Add the input entry to the input map
			inputMap.put(key, inputValue);

			// Recalculate the median key
			if ((key % 2) == 0) {

				// Even number of entries
				medianKey = key / 2;

			} else {

				// Odd number of entries
				medianKey = (key + 1) / 2;
			}

			// Get the list of vertices
			Collection<Integer> valueSet = inputMap.values();

			// Convert to an array of integers
			Integer valueList[] = new Integer[valueSet.size()];
			valueList = valueSet.toArray(valueList);

			// Sort the list
			if (valueSet.size() > 1)
				Arrays.sort(valueList);

			// Add this median value to the total
			medianTotal += valueList[medianKey-1].intValue();

			// TEST
			if (TEST) {
				System.out.println( "key = " + key +
									" medianKey = " + medianKey +
									" medianValue = " +
									valueList[medianKey-1].intValue());
			}

		}	// for all input values

		// Output the results
		System.out.println("Median total = " + (medianTotal % 10000));

	}	// end  - Main()

}	// end - Median_Maintenance class
