import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class KnapsackEntry {

	// Previous entry
	int previous;

	// Current entry
	int current;

	// Constructors

	KnapsackEntry() {
		previous = 0;
		current  = 0;
	}

	KnapsackEntry(int previousValue, int currentValue) {
		previous = previousValue;
		current  = currentValue;
	}

}	// end - KnapsackEntry class

class ItemEntry {

	// Item value
	int value;

	// Item weight
	int weight;

	// Constructors

	ItemEntry() {
		value  = 0;
		weight = 0;
	}

	ItemEntry(int itemValue, int itemWeight) {
		value  = itemValue;
		weight = itemWeight;
	}

}	// end = ItemEntry class


public class Knapsack_Problem {

	static boolean TEST = true;
//	static boolean TEST = false;

	// Knapsack structure
	static ArrayList<KnapsackEntry> knapsack;

	// Item list
	static ArrayList<ItemEntry> items;

	// Knapsack capacity
	private static int capacity;

	// Number of items
	private static int numItems;


	// Test case
	static int testCase = 0;

	//
	// Main entry
	//
	public static void main(String[] args) {

		// Timer - start
		long startTime = System.nanoTime();

		// Input choices
		switch (testCase) {
		case 1:								// Maximum value = 8 
			GetItemValues("test1.txt");
			break;
		case 2:								// Maximum value = 35
			GetItemValues("test2.txt");
			break;
		case 3:								// Maximum value = 2,493,893
			GetItemValues("knapsack1.txt");
			break;
		default:							// Maximum value = 4,243,395
			GetItemValues("knapsack_big.txt");
			break;
		}

		// Timer - read time
		long readTime = System.nanoTime();

		// Print the read time
		PrintTime((readTime - startTime)/1000, 0);

		// Calculate the maximum value
		CalcMaxValue();

		// Timer - execution time
		long execTime = System.nanoTime();

		// Print out the number of clusters
		PrintTime((execTime - readTime)/1000, 1);

		// Print out the maximum value
		System.out.println("Maximum value = " + knapsack.get(capacity).current);

	}	// end - Main()


	private static void CalcMaxValue() {

		// Temporary knapsack entry
		KnapsackEntry knapsackEntry;

		// Loop through all of the items
		for (int item = 1; item <= numItems; item++) {

			// Copy the current entries to the previous entries
			for (int value = 0; value <= capacity; value++) {

				// Get the current value
				int current = knapsack.get(value).current;
			
				// Create a new knapsack entry
				knapsackEntry = new KnapsackEntry(current, 0);

				// Replace the current knapsack list entry
				knapsack.set(value, knapsackEntry);

			}	// Copy current -> previous loop

			// Get the item value
			int itemValue  = items.get(item-1).value;

			// Get the item weight
			int itemWeight = items.get(item-1).weight;

			// Loop through the inherited values
			for (int value = 0; value < itemWeight; value++) {

				// Get previous value
				int previousValue = knapsack.get(value).previous;

				// Create a new knapsack entry
				knapsackEntry = new KnapsackEntry(previousValue,
												  previousValue);

				// Replace the current knapsack entry
				knapsack.set(value, knapsackEntry);

			}	// value = 0 -> weight-1

			// Loop through the calculated entries
			for (int value = itemWeight; value <= capacity; value++) {

				// Get previous value
				int previousValue = knapsack.get(value).previous;

				// Calculated value
				int calcValue = 0;

				// Calculate the new value
				calcValue = itemValue + knapsack.get(value-itemWeight).previous;

				// Choose the larger value
				if (previousValue > calcValue)
					knapsackEntry = new KnapsackEntry(previousValue,
													  previousValue);
				else
					knapsackEntry = new KnapsackEntry(previousValue,
													  calcValue);

				// Replace the knapsack entry
				knapsack.set(value, knapsackEntry);

			}	// value = weight -> capacity

			// TEST - print current maximum value
			if (TEST)
				System.out.println( "Item = " + item +
									" Maximum value = " +
									knapsack.get(capacity).current);

		}	// end - items loop

	}	// end - CalcMaxValue()


	private static void GetItemValues(String filename) {

		// Line data = String
		String line = null;

		// Line values
		String[] values = null;

		// Open specified text file
		File fil = new File(filename);
		FileReader inputFil = null;
		try {
			inputFil = new FileReader(fil);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader in = new BufferedReader(inputFil);

		// Read the first line of the file
		try {
			line = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Read the knapsack capacity and number of items
		values = line.trim().split("\\s+");

		// Set the knapsack capacity
		capacity = Integer.parseInt(values[0]);

		// Create the knapsack list
		knapsack = new ArrayList<>(capacity);

		// Insert the knapsack list entries
		for (int i=0; i<=capacity; i++) {

			// Create a new length 2 array
			KnapsackEntry entry = new KnapsackEntry();

			// Add it to the knapsack list
			knapsack.add(i, entry);
		}

		// Set the number of items
		numItems  = Integer.parseInt(values[1]);

		// Create the item list
		items = new ArrayList<>(numItems);

		// Read the next line of the file
		try {
			line = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Item index
		int itemIndex = 0;

		// Loop through all of the lines in the file
		while (line != null) {

			// Skip empty lines.
			line = line.trim();
			if (line.length() == 0) {
				// Read a line of the file
				try {
					line = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				continue;
			}

			// Get the edge list values
			values = line.trim().split("\\s+");

			// Item value
			int value  = Integer.parseInt(values[0]);

			// Item weight
			int weight = Integer.parseInt(values[1]);

			// Create a new item array
			ItemEntry item = new ItemEntry(value, weight);

			// Add the item to the list
			items.add(itemIndex, item);

			// Read the next line of the file
			try {
				line = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Increment the item index
			itemIndex++;

		}	// while (line != null)

		// Close the Buffered Reader
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}	// end - GetItemValue()


	private static void PrintTime(long nano, int type) {

		// Set the time values
		int min = (int) (nano / 60000000);
		nano -= min * 60000000;
		int sec = (int) (nano / 1000000);
		nano %= 1000000;
		int mil = (int) (nano / 1000);
		nano %= 1000;


		// Print the time type
		if (type == 0)
			System.out.print("Read time = ");
		else
			System.out.print("Execution time = ");

		// Print the time
		if (min != 0) {
			System.out.print(min + " minutes ");
		}

		if (sec != 0) {
			if (sec == 1)
				System.out.print(sec + " second ");
			else
				System.out.print(sec + " seconds ");
		}

		if (mil != 0 || nano != 0) {
			System.out.print(mil + "." + nano + " milliseconds");
		}

		// Print the line ending
		System.out.println();
	}

}	// end -KnapSack_Problem class
