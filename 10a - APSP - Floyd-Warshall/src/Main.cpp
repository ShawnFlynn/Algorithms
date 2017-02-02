
/*
 * Main() execution entry point
 *
 * Generates a graph from file based data
 * Calls to calculate the shortest path distance and node list
 * Calls to calculate its minimum-spanning-tree distance and node list
 * Prints out the graph, MST results and shortest path results
 *
 */

using namespace std;

#include "Graph.h"
#include "ShortestPath.h"
#include <limits>

void PrintTime(float, int );

/**************************************************************************/
/**************************************************************************/

// Execution entry point

// Generates a graph from file
// Calls the Graph.MST() method
// Prints the output

int main() {

//	const bool DEBUG = true;
	const bool DEBUG = false;

	clock_t startTime, readTime, calcTime;

	// Data files
	const string test1		= "Test6_5.txt";
	const string test2		= "Test6_7.txt";
	const string test3		= "Test4_4.txt";
	const string test4		= "Test4_4n.txt";
	const string test5		= "Test7_10n.txt";
	const string test6		= "Test7_10.txt";

	const string assignment1 = "g1.txt";
	const string assignment2 = "g2.txt";
	const string assignment3 = "g3.txt";

	// Output value
	string output;

	// Filename
	string filename;


	const int TEST = 1;		// test = 1 - 6 / 0 = assignment

	// Read the graph data from a file
	switch (TEST) {
	case 1:									// -10003
		filename = test1;
		break;

	case 2:									// -6
		filename = test2;
		break;

	case 3:									// 1
		filename = test3;
		break;

	case 4:									// NULL
		filename = test4;
		break;

	case 5:									// NULL
		filename = test5;
		break;

	case 6:									// -4
		filename = test6;
		break;

	default:
		filename = assignment1;
		break;
	}

	// Initialize the clock
	startTime = clock();

	// Create the shortest path class
	ShortestPath shortestPath = ShortestPath(filename);

	// Get and print out the read time
	readTime = clock();
	PrintTime(readTime-startTime, 0);

	// TEST - output the graph
	if (DEBUG) {
		// Print the initialized graph
		shortestPath.print_FWgraph(0);
	}

	// Process the specific test and print the results
	switch (TEST) {
	case 1:
	case 2:
	case 3:
	case 4:
	case 5:
	case 6:

		// Call the shortest-path algorithm
		output = shortestPath.getFWShortestPath();

		// Check for negative-cost cycle
		if (!output.compare("NULL")) {
			cout << "Negative-cost cycle" << endl << endl;
		} else {
			// Get the Shortest-shortest path
			int ssp = shortestPath.getFWShortestShortestPath();
			cout << "Shortest-shortest path = " << ssp << endl << endl;
		}

		// Get and print out the calculation time
		calcTime = clock();
		PrintTime(calcTime-readTime, 1);

		break;

	default:

		// Get the first Shortest-shortest path
		output = shortestPath.getFWShortestPath();

		// Get the shortest path distance
		int g1ShortestPath = shortestPath.getFWShortestShortestPath();

		// Print the output
		if (!output.compare("NULL")) {
			cout << assignment1 <<" = NULL" << endl << endl;
		} else {
			cout << assignment1 <<" Shortest path = " << g1ShortestPath << endl << endl;
		}

		// Get and print out the calculation time
		calcTime = clock();
		PrintTime(calcTime-readTime, 1);

		// Initialize the clock
		startTime = clock();

		// Get the second Shortest-shortest path
		shortestPath = ShortestPath(assignment2);

		// Get and print out the read time
		readTime = clock();
		PrintTime(readTime-startTime, 0);

		// Calculate the shortest path
		output = shortestPath.getFWShortestPath();

		// Get the shortest path distance
		int g2ShortestPath = shortestPath.getFWShortestShortestPath();

		// Print the output
		if (!output.compare("NULL")) {
			cout << assignment2 <<" = NULL" << endl << endl;
		} else {
			cout << assignment2 <<" Shortest path = " << g2ShortestPath << endl << endl;
		}

		// Get and print out the calculation time
		calcTime = clock();
		PrintTime(calcTime-readTime, 1);

		// Initialize the clock
		startTime = clock();

		// Get the third Shortest-shortest path
		shortestPath = ShortestPath(assignment3);

		// Get and print out the read time
		readTime = clock();
		PrintTime(readTime-startTime, 0);

		// Calculate the shortest path
		output = shortestPath.getFWShortestPath();

		// Get the shortest path distance
		int g3ShortestPath = shortestPath.getFWShortestShortestPath();

		// Print the output
		if (!output.compare("NULL")) {
			cout << assignment3 <<" = NULL" << endl << endl;
		} else {
			cout << assignment3 <<" Shortest path = " << g3ShortestPath << endl << endl;
		}

		// Get and print out the calculation time
		calcTime = clock();
		PrintTime(calcTime-readTime, 1);

		break;
	}

	// Return OK
	return 0;

}	// end - main()

void PrintTime(float mili, int type) {

		// Set the time values
		int min = (int) (mili / 60000);
		mili -= min * 60000;
		float sec = (mili / 1000);


		// Print the time type
		if (type == 0)
			cout << "Read time = ";
		else
			cout << "Execution time = ";

		// Print the time
		if (min != 0) {
			cout << min << " minutes ";
		}

		if (sec != 0) {
			if (sec == 1)
				cout << sec << " second ";
			else
				cout << sec << " seconds ";
		}

		// Print the line ending
		cout << endl;
	}

