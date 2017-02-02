import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class FindMinCuts {

	// Print flag
//	static public boolean PRINT = true;
	static public boolean PRINT = false;

//	static public boolean TEST = true;
	static public boolean TEST = false;

	// Print initial AdjList once flag
	static public boolean printOnce = true;

	// Count of minimum cuts
	private static int minCutCount = 0;

	// Random number generator
	public static Random randomGenerator = new Random();
// Works - but wrong min cut = 21	public static Random randomGenerator = new Random( 4690 );
// 118 - (119,119)	public static Random randomGenerator = new Random( 123 );
//	public static Random randomGenerator = new Random( 58950 );

	// Adjacency list
	static Map<Integer, ArrayList<Integer>> adjList =
							new HashMap<Integer, ArrayList<Integer>>();


	////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) {

		// Number of runs
		int[] runNumber = {/*92042*/ 1000, 58, 58, 58, 58, 58, 2564, 29};

		// Test switch value
		int testCase = 0;

		// Lowest min cut count flag
		int savedCutCount = 0;

		// Run the algorithm runNumber times
		for (int i = 0; i < runNumber[testCase]; i++) {

			// Print the run number
			if (PRINT) {
				System.out.println("+++ Run Number = " + Integer.toString(i+1));
				System.out.println();
			}

			// Test cases
			switch(testCase) {
			case (0):			// answer = 17
				TestAssignment();
				break;
			case (1):			// answers = 2 - (1,7) & (4,5) 
				Test8_1();
				break;
			case (2):			// answers = 2 - (1,7) & (4,5)
				Test8_2();
				break;
			case (3):			// answers = 1 - (4,5)
				Test8_3();
				break;
			case (4):			// answers = 1 - (4,5)
				Test8_4();
				break;
			case (5):			// answers = 2 - 
				Test8_5();
				break;
			case (6):			// answers = 3 - 
				Test40();
				break;
			default:			// answers = 3 -
				TestDefault();
				break;
			}	// end - switch()

			// Initialize the saved min cut count
			if (savedCutCount == 0) {
				// Save the minimum cut count
				savedCutCount = minCutCount;
			} else {

				// Check if equal min cut count
				if (minCutCount == savedCutCount) {
				}

				// Check current min cut count
				if (minCutCount < savedCutCount) {
					// Save the new min cut count
					savedCutCount = minCutCount;
				}
			}

			// Print out the run results
			if (PRINT) {
				System.out.println("Run min cut size = "
									+ Integer.toString(minCutCount));

				System.out.println();
			}

		}	// end - < runNumber

		// Print out the results
		System.out.println("Min cut size = "
				+ Integer.toString(savedCutCount));

	}	// end - Main()


	public static void TestAssignment() {

		// Clear the adjacency list
		adjList.clear();

		// Get the data from Test8-1.txt
		try {
			GetAdjacencyList.GetAdjList("kargerMinCut.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Print the adjacency list
		if (printOnce) {
			GetAdjacencyList.PrintAdjList();
			printOnce = false;
		}

		// Find the Minimum Cuts
		minCutCount = ContractGraph.ContractAdjList();

	}

	public static void Test8_1() {

		// Clear the adjacency list
		adjList.clear();

		// Get the data from Test8-1.txt
		try {
			GetAdjacencyList.GetAdjList("Test8-1.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Print the adjacency list
		if (printOnce) {
			GetAdjacencyList.PrintAdjList();
			printOnce = false;
		}

		// Find the Minimum Cuts
		minCutCount = ContractGraph.ContractAdjList();

	}

	public static void Test8_2() {

		// Clear the adjacency list
		adjList.clear();

		// Get the data from Test8-1.txt
		try {
			GetAdjacencyList.GetAdjList("Test8-2.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Print the adjacency list
		if (printOnce) {
			GetAdjacencyList.PrintAdjList();
			printOnce = false;
		}

		// Find the Minimum Cuts
		minCutCount = ContractGraph.ContractAdjList();

	}

	public static void Test8_3() {

		// Clear the adjacency list
		adjList.clear();

		// Get the data from Test8-1.txt
		try {
			GetAdjacencyList.GetAdjList("Test8-3.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Print the adjacency list
		if (printOnce) {
			GetAdjacencyList.PrintAdjList();
			printOnce = false;
		}

		// Find the Minimum Cuts
		minCutCount = ContractGraph.ContractAdjList();

	}

	public static void Test8_4() {

		// Clear the adjacency list
		adjList.clear();

		// Get the data from Test8-1.txt
		try {
			GetAdjacencyList.GetAdjList("Test8-4.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Print the adjacency list
		if (printOnce) {
			GetAdjacencyList.PrintAdjList();
			printOnce = false;
		}

		// Find the Minimum Cuts
		minCutCount = ContractGraph.ContractAdjList();

	}

	public static void Test8_5() {

		// Clear the adjacency list
		adjList.clear();

		// Get the data from Test8-1.txt
		try {
			GetAdjacencyList.GetAdjList("Test8-5.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Print the adjacency list
		if (printOnce) {
			GetAdjacencyList.PrintAdjList();
			printOnce = false;
		}

		// Find the Minimum Cuts
		minCutCount = ContractGraph.ContractAdjList();

	}

	public static void Test40() {

		// Clear the adjacency list
		adjList.clear();

		// Get the data from Test8-1.txt
		try {
			GetAdjacencyList.GetAdjList("Test40.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Print the adjacency list
		if (printOnce) {
			GetAdjacencyList.PrintAdjList();
			printOnce = false;
		}

		// Find the Minimum Cuts
		minCutCount = ContractGraph.ContractAdjList();

	}

	public static void TestDefault() {

		// Clear the adjacency list
		adjList.clear();

		// Get the data from Default.txt
		try {
			GetAdjacencyList.GetAdjList("Default.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Print the adjacency list
		if (printOnce) {
			GetAdjacencyList.PrintAdjList();
			printOnce = false;
		}

		// Find the Minimum Cuts
		minCutCount = ContractGraph.ContractAdjList();

	}

}	// end - FindMinCuts class
