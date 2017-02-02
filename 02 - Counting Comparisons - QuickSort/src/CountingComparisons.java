import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CountingComparisons {

	enum Pivot {FIRST, MEDIAN, FINAL};

	private static long comparisonCount = 0;

	public static void main(String[] args) {

		int testCase = 0;

		// Test cases
		switch(testCase) {
		case (0):			// answers = 162085, 164123, 138382
			TestAssignment();
			break;
		case (1):			// answers = 25, 29, 21
			Test10();
			break;
		case (2):			// answers = 615, 587, 518
			Test100();
			break;
		case (3):			// answers = 10297, 10184, 8921
			Test1000();
			break;
		default:			// answers = 15, 15, 13
			TestDefault();
			break;
		}

	}	// end - Main()

	// Routine to read input data lists
	public static int[] getInputArray(String filename) {

		// Declare a scanner
		Scanner s = null;

		// Initialize the scanner
		try {
			s = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Declare an input ArrayList
		ArrayList<Integer> inputList = new ArrayList<Integer>();

		// Insert the integers from the file into the ArrayList
		while (s.hasNextLine()) {
			inputList.add((Integer)s.nextInt());
		}

		// Declare and initialize the input array
		int[] inputArray = new int[inputList.size()];

		// Copy the inputList into the input array
		for (int i=0; i<inputList.size(); i++)
			inputArray[i] = inputList.get(i);

		// Return the input array
		return inputArray;

	}	// end - getInputArray()

	// Assignment test program
	public static void TestAssignment() {

		// Get the data array
		int[] dataArray = getInputArray("QuickSort.txt");

		// Count comparisons using the first element as the pivot
		CountingComparisons.countComparisons(dataArray, Pivot.FIRST);

		// Output the comparison Count
		System.out.println("comparison count (first) = " + comparisonCount);

		// Get the data array
		dataArray = getInputArray("QuickSort.txt");

		// Count comparisons using the final element as the pivot
		CountingComparisons.countComparisons(dataArray, Pivot.FINAL);

		// Output the comparison Count
		System.out.println("comparison count (final) = " + comparisonCount);

		// Get the data array
		dataArray = getInputArray("QuickSort.txt");

		// Count comparisons using the median element as the pivot
		CountingComparisons.countComparisons(dataArray, Pivot.MEDIAN);

		// Output the comparison Count
		System.out.println("comparison count (median) = " + comparisonCount);

	}

	// 10.txt
	public static void Test10() {

		// answers = 25, 29, 21

		// Get the data array
		int[] dataArray = getInputArray("10.txt");

		// Count comparisons using the first element as the pivot
		CountingComparisons.countComparisons(dataArray, Pivot.FIRST);

		// Output the comparison Count
		System.out.println("comparison count (first) = " + comparisonCount);

		dataArray = getInputArray("10.txt");

		// Count comparisons using the final element as the pivot
		CountingComparisons.countComparisons(dataArray, Pivot.FINAL);

		// Output the comparison Count
		System.out.println("comparison count (final) = " + comparisonCount);

		dataArray = getInputArray("10.txt");

		// Count comparisons using the median element as the pivot
		CountingComparisons.countComparisons(dataArray, Pivot.MEDIAN);

		// Output the comparison Count
		System.out.println("comparison count (median) = " + comparisonCount);

	}

	// 100.txt
	public static void Test100() {

		// answers = 615, 587, 518

		// Get the data array
		int[] dataArray = getInputArray("100.txt");

		// Count comparisons using the first element as the pivot
		CountingComparisons.countComparisons(dataArray, Pivot.FIRST);

		// Output the comparison Count
		System.out.println("comparison count (first) = " + comparisonCount);

		// Get the data array
		dataArray = getInputArray("100.txt");

		// Count comparisons using the final element as the pivot
		CountingComparisons.countComparisons(dataArray, Pivot.FINAL);

		// Output the comparison Count
		System.out.println("comparison count (final) = " + comparisonCount);

		// Get the data array
		dataArray = getInputArray("100.txt");

		// Count comparisons using the median element as the pivot
		CountingComparisons.countComparisons(dataArray, Pivot.MEDIAN);

		// Output the comparison Count
		System.out.println("comparison count (median) = " + comparisonCount);

	}

	// 1000.txt
	public static void Test1000() {

		// answers = 10297, 10184, 8921

		// Get the data array
		int[] dataArray = getInputArray("1000.txt");

		// Count comparisons using the first element as the pivot
		CountingComparisons.countComparisons(dataArray, Pivot.FIRST);

		// Output the comparison Count
		System.out.println("comparison count (first) = " + comparisonCount);

		// Get the data array
		dataArray = getInputArray("1000.txt");

		// Count comparisons using the final element as the pivot
		CountingComparisons.countComparisons(dataArray, Pivot.FINAL);

		// Output the comparison Count
		System.out.println("comparison count (final) = " + comparisonCount);

		// Get the data array
		dataArray = getInputArray("1000.txt");

		// Count comparisons using the median element as the pivot
		CountingComparisons.countComparisons(dataArray, Pivot.MEDIAN);

		// Output the comparison Count
		System.out.println("comparison count (median) = " + comparisonCount);

	}

	// Default test
	public static void TestDefault() {

		// Get the data array
		int[] dataArrayFirst = {3, 8, 2, 5, 1, 4, 7, 6};

		// Count comparisons using the first element as the pivot
		CountingComparisons.countComparisons(dataArrayFirst, Pivot.FIRST);

		// Output the comparison Count
		System.out.println("comparison count (first) = " + comparisonCount);

		// Get the data array
		int[] dataArrayFinal = {3, 8, 2, 5, 1, 4, 7, 6};

		// Count comparisons using the final element as the pivot
		CountingComparisons.countComparisons(dataArrayFinal, Pivot.FINAL);

		// Output the comparison Count
		System.out.println("comparison count (final) = " + comparisonCount);

		// Get the data array
		int[] dataArrayMedian = {3, 8, 2, 5, 1, 4, 7, 6};

		// Count comparisons using the median element as the pivot
		CountingComparisons.countComparisons(dataArrayMedian, Pivot.MEDIAN);

		// Output the comparison Count
		System.out.println("comparison count (median) = " + comparisonCount);

	}


////////////////////////////////////////////////////////////////////////////////

	// Comparison counting entry routine
	public static void countComparisons(int[] input, Pivot pivot) {

		// Initialize the comparison count (= first partition)
		comparisonCount = input.length - 1;

		// Call the recursive QuickSort routine
		countAndSort(input, pivot, 0, input.length);

		// Return
		return;
	}

	// Comparison counting and sort routine
	private static void countAndSort (int[] input, Pivot pivot, int start, int end) {

		// Temporary storage
		int temp;

		// Input size
		int inputSize = end - start;

		// if done -> return
		if (inputSize < 2)
			return;

		// Pivot index
		int pivotIndex = start;

		// Middle element value
		int middleValue = 0;
		// Middle array index
		int middleIndex = 0;

		// Partition around the pivot

		// Set up the FINAL pivot element
		if (pivot == Pivot.FINAL) {
			// Move last element to first
			temp = input[start];
			input[start] = input[end-1];
			input[end-1] = temp;
		}

		// Set up for MEDIAN pivot element
		if (pivot == Pivot.MEDIAN) {

			// Check for arrays of length < 3
			if (inputSize > 2 ) {

				// Initialize the middle index
				middleIndex = start + (inputSize + 1)/2 - 1;
/*
				// Initialize for even length
				middleIndex = start + inputSize/2 - 1;
	
				// Check for odd
				if ((inputSize % 2) != 0) {
					middleIndex++;
				}
*/
				// Set the middle value
				middleValue = input[middleIndex];
	
				// Check MIDDLE element
				if ((middleValue > input[start] && middleValue < input[end-1]) ||
					(middleValue > input[end-1] && middleValue < input[start])) {
					temp = input[start];
					input[middleIndex] = input[start];
					input[start] = middleValue;
				} else {
	
					// Check LAST element
					if ((input[end-1] > input[start] &&
						 input[end-1] < middleValue) ||
						(input[end-1] > middleValue  &&
						 input[end-1] < input[start])) {
						temp = input[start];
						input[start] = input[end-1];
						input[end-1] = temp;
					}
				}
			}	// inputSize < 3
		}

		// Do the partitioning

		// Pivot element value
		int pivotValue = input[start];

		// Initialize the i index
		int i = start + 1;

		// Loop through the subarray
		for (int j = start+1; j < end; j++) {
			// Compare element to the pivot
			if (input[j] < pivotValue){
				if (i != j) {
					// Swap input[i] and input[j]
					temp = input[i];
					input[i] = input[j];
					input[j] = temp;
				}
				i++;
			}
		}

		// Position the pivot
		pivotIndex = i - 1;
		if (pivotIndex != start) {
			temp = input[pivotIndex];
			input[pivotIndex] = input[start];
			input[start] = temp;
		}

		// Recursive calls - split array and count
		// Left
		if (pivotIndex - start >= 1) {
			comparisonCount += pivotIndex - start - 1;
			countAndSort (input, pivot, start, pivotIndex);
		}
		//Right
		if (end - (pivotIndex+1) >= 1) {
			comparisonCount += end - (pivotIndex + 1) - 1; 
			countAndSort (input, pivot, pivotIndex+1, end);
		}

		// Return the count
		return;

	}	// end - CountAndSort()

}	// end - CountingComparisons class
