
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

	// Timer values
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

	const string large = "large.txt";

	// Algorithm's
	string sFW = "Floyd-Warshall";
	string sJ  = "Johnson";

	// Output value
	string output;

	// Filename
	string filename;


	const int TEST = 8;		// test = 1 - 6 / 7 & 8 = large / 0 = assignment

	// Read the graph data from a file
	switch (TEST) {
	case 1:									// -10003
		filename = test1;
		break;

	case 2:									// -6
		filename = test2;
		break;

	case 3:									// -1
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

	case 7:					// Floyd-Warshall	//
	case 8:					// Johnson			//
		filename = assignment3;
//		filename = test6;
//		filename = large;
		break;

	default:				// Floyd-Warshall	// -19
		filename = assignment1;
		break;
	}

	// Initialize the clock
	startTime = clock();

	// Process the specific test and print the results
	switch (TEST) {
	case 1:
	case 2:
	case 3:
	case 4:
	case 5:
	case 6:
		{
			// Create a FW shortest path class
			ShortestPath FWshortestPath = ShortestPath(filename, sFW);

			// TEST - output the graph
			if (DEBUG) {
				// Print the initialized graph
				FWshortestPath.print_APSPgraph(0);
			}

			// Get and print out the read time
			readTime = clock();
			PrintTime(readTime-startTime, 0);

			// Call the shortest-path algorithm
			output = FWshortestPath.getAPSPShortestPath(sFW);

			// Check for negative-cost cycle
			if (!output.compare("NULL")) {
				cout << filename << " Negative-cost cycle" << endl << endl;
			} else {
				// Get the Shortest-shortest path
				int ssp = FWshortestPath.getAPSPShortestShortestPath();
				cout << filename << "FW Shortest-shortest path = " <<
						ssp << endl << endl;
			}

			// Get and print out the calculation time
			calcTime = clock();
			PrintTime(calcTime-readTime, 1);

			break;

		}	// end - cases 1 -> 6

	case 7:		// Floyd-Warshall
		{
			// Create a Floyd-Warshall shortest path class
			ShortestPath largeFWSP = ShortestPath(filename, sFW);

			// TEST - output the graph
			if (DEBUG) {
				// Print the initialized graph
				largeFWSP.print_APSPgraph(0);
			}

			// Get and print out the read time
			readTime = clock();
			PrintTime(readTime-startTime, 0);

			// Call the Shortest path algorithm
			output = largeFWSP.getAPSPShortestPath(sFW);

			// Get the shortest-shortest path distance
			int g1ShortestPath = largeFWSP.getAPSPShortestShortestPath();

			// Print the output
			if (!output.compare("NULL")) {
				cout << filename << " FW "<< " = NULL" << endl << endl;
			} else {
				cout << filename <<" FW Shortest path = " << g1ShortestPath <<
						endl << endl;
			}

			// Get and print out the calculation time
			calcTime = clock();
			PrintTime(calcTime-readTime, 1);

			break;

		}	// end - case 7

	case 8:		// Johnson
		{
			// Create a Johnson shortest path class
			ShortestPath largeJSP = ShortestPath(filename, sJ);

			// TEST - output the graph
			if (DEBUG) {
				// Print the initialized graph
				largeJSP.print_APSPgraph(0);
			}

			// Get and print out the read time
			readTime = clock();
			PrintTime(readTime-startTime, 0);

			// Calculate the Johnson shortest paths
			output = largeJSP.getJShortestPath(filename);

			// Get the (shortest) shortest path distance
			int shortestPath = largeJSP.getShortestPathDistance();

			// Print the output
			if (!output.compare("NULL")) {
				cout << filename << " J = NULL" << endl << endl;
			} else {
				cout << filename <<" J Shortest path = " << shortestPath <<
						endl << endl;
			}

			// Get and print out the calculation time
			calcTime = clock();
			PrintTime(calcTime-readTime, 1);

			break;

		}	// end - case 8


	default:
		{
			// Create a Floyd-Warshall shortest path class
			ShortestPath shortestPath = ShortestPath(filename, sFW);

			// TEST - output the graph
			if (DEBUG) {
				// Print the initialized graph
				shortestPath.print_APSPgraph(0);
			}

			// Call the shortest path algorithm
			output = shortestPath.getAPSPShortestPath(sFW);

			// Get and print out the read time
			readTime = clock();
			PrintTime(readTime-startTime, 0);

			// Get the shortest path distance
			int g1ShortestPath = shortestPath.getAPSPShortestShortestPath();

			// Print the output
			if (!output.compare("NULL")) {
				cout << filename << " FW = NULL" << endl;
			} else {
				cout << filename << " FW Shortest path = " << g1ShortestPath <<
						endl;
			}

			// Get and print out the calculation time
			calcTime = clock();
			PrintTime(calcTime-readTime, 1);

			// Initialize the clock
			startTime = clock();

			// Get the second shortest path class
			shortestPath = ShortestPath(assignment2, sFW);

			// Get and print out the read time
			readTime = clock();
			PrintTime(readTime-startTime, 0);

			// Calculate the shortest path
			output = shortestPath.getAPSPShortestPath(sFW);

			// Get the shortest path distance
			int g2ShortestPath = shortestPath.getAPSPShortestShortestPath();

			// Print the output
			if (!output.compare("NULL")) {
				cout << assignment2 << " FW = NULL" << endl;
			} else {
				cout << assignment2 << " FW Shortest path = " <<
						g2ShortestPath << endl;
			}

			// Get and print out the calculation time
			calcTime = clock();
			PrintTime(calcTime-readTime, 1);

			// Initialize the clock
			startTime = clock();

			// Get the third shortest path class
			shortestPath = ShortestPath(assignment3, sFW);

			// Get and print out the read time
			readTime = clock();
			PrintTime(readTime-startTime, 0);

			// Calculate the shortest path
			output = shortestPath.getAPSPShortestPath(sFW);

			// Get the shortest path distance
			int g3ShortestPath = shortestPath.getAPSPShortestShortestPath();

			// Print the output
			if (!output.compare("NULL")) {
				cout << assignment3 << " FW = NULL" << endl;
			} else {
				cout << assignment3 << " FW Shortest path = " <<
						g3ShortestPath << endl;
			}

			// Get and print out the calculation time
			calcTime = clock();
			PrintTime(calcTime-readTime, 1);

			cout << endl;

			break;

		}	// end - default
	}	// end - switch(TEST)

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
			cout << endl << "Read time = ";
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
