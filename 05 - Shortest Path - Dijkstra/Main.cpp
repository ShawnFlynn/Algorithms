
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
	const string dijkstraData = "dijkstraData.txt";

			// 1,0 2,1 3,2 4,3 5,4 6,4 7,3 8,2
	const string sample_1 = "Sample_1.txt";
			// 1,0 2,1 3,3 4,2 5,0
	const string sample_2 = "Sample_2.txt";
			// 1,0 2,1 3,4 4,5 5,3 6,4 7,3 8,2 9,3 10,6 11,5
	const string sample_3 = "Sample_3.txt";
			// 1,0 2,1 3,2 4,3 5,5
	const string sample_4 = "Sample_4.txt";
			// 1,0 2,1 3,4 4,3 5,11 6,7 7,2 8,INF 9,12 10,9
	const string sample_5 = "Sample_5.txt";
			// 1,0 2,3 3,2 4,4 5,5 6,5
	const string sample_6 = "Sample_6.txt";

	// Graph class
	Graph myGraph;

	const int TEST = 0;

	// Read the graph data from a file
	switch (TEST) {
	case 0:
		myGraph = Graph(dijkstraData);
		break;

	case 1:
		myGraph = Graph(sample_1);
		break;

	case 2:
		myGraph = Graph(sample_2);
		break;

	case 3:
		myGraph = Graph(sample_3);
		break;

	case 4:
		myGraph = Graph(sample_4);
		break;

	case 5:
		myGraph = Graph(sample_5);
		break;

	case 6:
		myGraph = Graph(sample_6);
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

//	myGraph.print_matrix(myGraph.NUMERIC);

	// Vector of end nodes
//	vector<int> endNodes = {7, 37, 59, 82, 99, 115, 133, 165, 188, 197};
	vector<int> endNodes = {6, 36, 58, 81, 98, 114, 132, 164, 187, 196};

	// Loop through all of the specified end nodes
	for (int node: endNodes) {
//	for (int node = 0; node < myGraph.V(); node++) {

		// Get the shortest path to this node

		myGraph.getShortestPath(0, node);

		// Distance
		int distance = myGraph.get_shortest_path_distance();

		// Print the result
		if (distance != 0 || node == 0)
			cout << node+1 << "," << distance << endl;
		else
			cout << node+1 << ",INF" << endl;

	}

	// Return OK
	return 0;

}	// end - main()
