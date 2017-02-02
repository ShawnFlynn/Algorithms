
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
#include "MST.h"


/**************************************************************************/
/**************************************************************************/

// Execution entry point

// Generates a graph from file
// Calls the Graph.MST() method
// Prints the output

int main() {

	// Data files
	const string test1		= "test1.txt";
	const string test2		= "test2.txt";
	const string assignment = "clustering1.txt";

	// # of clusters
	int k;

	// Maximum spacing
	int maximum_spacing = 0;

	// Graph class
	Graph myGraph;

	// MST class
	MST myMST = MST();

	const int TEST = 0;		// 0 = assignment / 1 = test1 / 2 = test2

	// Read the graph data from a file
	switch (TEST) {
	case 1:
		myGraph = Graph(test1);				// = 273
		k = 3;
		break;

	case 2:
		myGraph = Graph(test2);				// = 10
		k = 3;
		break;

	default:
		myGraph = Graph(assignment);			// =
		k = 4;
		break;
	}

	// Check for correct graph generation
	if (myGraph.Vertices() == 0 || myGraph.Edges() == 0)
		return 1;

	// Local vertices
	int vertices = myGraph.Vertices();

	// Process the specific algorithm and print the results
	switch (TEST) {
	case 1:
	case 2:
		myGraph.print_graph(myGraph.NUMERIC);
		if (myGraph.is_connected(vertices))
			cout << "Connected = true" << endl << endl;
		else
			cout << "Connected = false" << endl << endl;
		maximum_spacing = myMST.k_Cluster(myGraph, k);
		cout << "Maximum spacing = " << maximum_spacing << endl << endl;
		break;
	default:
		myGraph.print_graph(myGraph.NUMERIC);
		if (myGraph.is_connected(vertices))
			cout << "Connected = true" << endl << endl;
		else
			cout << "Connected = false" << endl << endl;
		maximum_spacing = myMST.k_Cluster(myGraph, k);
		cout << "Maximum spacing = " << maximum_spacing << endl << endl;
		break;
	}

	// Deallocate the graph class
//	myGraph.Graph::~Graph();

	// Return OK
	return 0;

}	// end - main()

