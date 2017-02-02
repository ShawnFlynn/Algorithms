import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CountingInversions {

	public static void main(String[] args) {

		int testCase = 99;

		long inversionCount = 0;

		// Test cases
		switch(testCase) {
		case (0):			// IntegerArray.txt
			inversionCount = TestAssignment();
			break;
		case (1):			// answer = 3
			inversionCount = Test1();
			break;
		case (2):			// answer = 4
			inversionCount = Test2();
			break;
		case (3):			// answer = 10
			inversionCount = Test3();
			break;
		case (4):			// answer = 5
			inversionCount = Test4();
			break;
		case (5):			// answer = 56
			inversionCount = Test5();
			break;
		default:			// answer = 0
			inversionCount = TestDefault();
			break;
		}

		// Output the Inversion Count
		System.out.println("Inversion count = " + inversionCount);

	}	// end - Main()
	
	public static long TestAssignment() {
		// read 100,000 integers into "input" array

		// Declare a scanner
		Scanner s = null;

		// Initialize the scanner
		try {
			s = new Scanner(new File("IntegerArray.txt"));	// answer = 2407905288
//			s = new Scanner(new File("TestArray.txt"));		// answer = 28
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
		int[] input = new int[inputList.size()];

		// Copy the inputList into the input array
		for (int i=0; i<inputList.size(); i++)
			input[i] = inputList.get(i);

		// Call Inversion counting routine
		return CountingInversions.countInversions(input);
 
	}

	public static long Test1() {
		int[] input = {1, 3, 5, 2, 4, 6};

		return CountingInversions.countInversions(input);
	}

	public static long Test2() {
		int[] input = {1, 5, 3, 2, 4};

		return CountingInversions.countInversions(input);
	}

	public static long Test3() {
		int[] input = {5, 4, 3, 2, 1};

		return CountingInversions.countInversions(input);
	}

	public static long Test4() {
		int[] input = {1, 6, 3, 2, 4, 5};

		return CountingInversions.countInversions(input);
	}

	public static long Test5() {
		int[] input = {9, 12, 3, 1, 6, 8, 2, 5, 14, 13, 11, 7, 10, 4, 0};

		return CountingInversions.countInversions(input);
	}

	public static long TestDefault() {
//		int[] input = {1, 2, 3, 4, 5, 6, 7, 8};
		int[] input = {5, 3, 8, 9, 1, 7, 0, 2, 6, 4};

		return CountingInversions.countInversions(input);
	}

////////////////////////////////////////////////////////////////////////////////

		// Inversion counting entry routine
	public static long countInversions(int[] input) {
		return countAndSort(input, 0, input.length);
	}

	private static long countAndSort (int[] input, int start, int end) {

		// if done -> return
		if (start == end - 1)
			return 0;

		// split in half
		int mid = (start + end)/2;

		// recursive call - split array and count
		return	countAndSort (input, start, mid)  +
				countAndSort (input, mid, end) +
				mergeAndCount (input, start, mid, end);
	}

	private static long mergeAndCount (int[] input, int start, int mid, int end) {
		long count = 0;
		int[] temp = new int[input.length];

		for (int i = start, lb = start, hb = mid; i < end; i++) {

			if (hb >= end || lb < mid && input[lb] <= input[hb]) {
				temp[i]  = input[lb++];
			} else {
				count = count + (mid - lb); 
				temp[i]  = input[hb++];
			}
		}

		System.arraycopy(temp, start, input, start, end - start);

		return count;
	}


}	// end - CountingInversions class
