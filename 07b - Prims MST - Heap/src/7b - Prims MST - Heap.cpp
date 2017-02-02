
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

#include "graph.h"

/**************************************************************************/
/**************************************************************************/

// Execution entry point

// Generates a graph from file
// Calls the dijkstra's Shortest Path method
// Prints the output

int main() {

	// Data files
	const string edgesFile = "edges.txt";

	const string test4_5 = "Test4_5.txt";

	const string test4_5_neg = "Test4_5-neg.txt";

	const string test5_7 = "Test5_7.txt";

	const string test8_9 = "Test8_9.txt";

	// Graph class
	Graph myGraph;

	const int TEST = 0;

	// Read the graph data from a file
	switch (TEST) {
	case 0:								// answer = -3612829
		myGraph = Graph(edgesFile);
		break;

	case 1:								// answer = 4
		myGraph = Graph(test4_5);
		break;

	case 2:								// answer = -3
		myGraph = Graph(test4_5_neg);
		break;

	case 3:								// answer = 16
		myGraph = Graph(test5_7);
		break;

	case 4:								// answer = 15
		myGraph = Graph(test8_9);
		break;

	default:
		cout << "Invalid Test ID:" << endl << endl;
		break;
	}

	// Check for correct graph generation
	if (myGraph.V() == 0 || myGraph.E() == 0) {
		cout << "Invalid graph:" << endl << endl;
		return 1;
	}

	// calculate the MST distance
	myGraph.PrimMST();

	// Print the MST distance
	myGraph.print_MST(myGraph.NUMERIC, myGraph.PRIM);

	// Return OK
	return 0;

}	// end - main()
