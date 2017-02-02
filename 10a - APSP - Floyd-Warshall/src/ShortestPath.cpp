/*
 * ShortestPath.cpp
 *
 *  Created on: Oct 8, 2016
 *      Author: Shawn Flynn
 */

using namespace std;

#include "ShortestPath.h"

#include <iostream>
#include <vector>
#include <limits>

// Constructor for Floyd-Warshall graph
ShortestPath::ShortestPath(string filename): shortest_path_distance(0),
											 graph_size(0),
											 pPath_list(nullptr) {

	// Setup a file stream
	ifstream data_file(filename);
	if (!data_file) {
		cerr << "Couldn't open file: " << filename << endl;
		return;
	}

	// Define input stream iterators
	istream_iterator<int> input(data_file);
	istream_iterator<int> eos;

	// Read in the number of nodes
	if (input != eos)
		graph_size = *input++;

	// Read in the number of edges
	if (input != eos)
		*input++;

	// Check for a valid size
	if (graph_size < 2)
		return;

	// Generate the graph columns
	FWgraph.resize(graph_size);

	// Define infinity
	long infinity = numeric_limits<int>::max();

	// Initialize to infinity
	for(int i=0;i<graph_size;i++)
	{
		FWgraph[i].resize(graph_size);
		for(int j=0;j<graph_size;j++)
		{
			FWgraph[i][j].resize(graph_size, infinity);
		}
	}

	// Set all i to i node distances to 0
	for (int k=0; k<graph_size; k++) {
		for (int i=0; i<graph_size; i++) {
			FWgraph.at(i).at(i).at(k) = 0;
		}
	}

	// Set all existing edges from the file
	while ( input != eos ) {

		// Read the edge values
		int tail = *input++;
		int head = *input++;
		long dist = *input++;

		// Put into each of the k graphs
		// Convert to zero-based graph
		for (int k=0; k<graph_size; k++)
			FWgraph.at(tail-1).at(head-1).at(k) = dist;

	}

}	// end - ShortestPath(filename)


// Get the graph size
int ShortestPath::getGraphSize() {
	return graph_size;
}


// Get a graph entry
long ShortestPath::GetGraphEntry(int i, int j, int k) {

	// Define infinity
	int infinity = numeric_limits<int>::max();

	// Check for valid i
	if (i<0 || i>=graph_size)
		return infinity;

	// Check for valid j
	if (j<0 || j>=graph_size)
		return infinity;

	// Check for valid k
	if (k<0 || k>=graph_size)
		return infinity;

	// Return the graph entry
	return FWgraph.at(i).at(j).at(k);

}

// Dijkstra shortest-path algorithm
void ShortestPath::getShortestPath( Graph graph,
									const int start,
									const int end) {

	const bool DEBUG = false;

	const int MAX_DIST = 999999;

	// Calculate the graph size
	int size = end - start + 1;

	// Define and initialize the local lists
	vector<bool> visited(size, false);
	vector<int> node_dist(size, MAX_DIST);

	// Generate a local path node vector
	vector<int>* pNode_list = new vector<int>(size, -1);

	// Set the start node distance to 0
	node_dist[start] = 0;

	// Loop counter
	int count = 0;

	// Loop through the vertices
	while(count < size) {

		int minDistance = MAX_DIST;
		int closestNode = 0;

		// Find the closest adjacent node
		for(int i = 0; i < size; i++) {
			if((!visited[i]) && (minDistance >= node_dist[i])) {
				minDistance = node_dist[i];
				closestNode = i;
			}
		}

		// Indicate this node has been visited
		visited[closestNode] = true;

		if (DEBUG) {
			cout << "count = " << count << endl;
			cout << "closestNode = " << closestNode << endl;
			cout << "minDistance = " << minDistance << endl;
		}

		// Loop through the nodes
		for(int i = 0; i < size; i++) {

			// Get the distance to this node from the start node
			int cur_distance = node_dist[closestNode] +
										graph.get_edge_value(closestNode, i);

			// If the node has not been visited and the distance is not 0
			if (!visited[i] && (graph.get_edge_value(closestNode, i) != 0) ) {

				// If this node is closer
				if (node_dist[i] > cur_distance) {

					// Put the current distance into the list
					node_dist[i] = cur_distance;

					// Put the current node into the path list
					pNode_list->at(i) = closestNode;
				}
			}
		}

		// Move to the next node
		count++;
	}

	// Set the shortest path distance
	shortest_path_distance = node_dist[end];
	if (shortest_path_distance == MAX_DIST)
		shortest_path_distance = 0;
	else
		shortest_path_distance -= node_dist[start];

	// Create the shortest path list
	createPath_list();

	// Generate the path node list
	genPathNodeList(*pNode_list, end);

	// Test output
	if (DEBUG) {
		cout << "The distances to the other nodes are:" << endl;
		for(int i = start; i <= end; i++) {
			cout << i << " : " << node_dist[i] << endl;
		}
		cout << endl;
		cout << "The path node list is:" << endl;
		for (auto i : *pNode_list) {
			if (i == 0)
				cout << i;
			else
				cout << " -> " << i;
		}
		cout << endl;
	}

}	// end - getShortestPath()

// Floyd-Warshall shortest path algorithm
string ShortestPath::getFWShortestPath() {

	// Define infinity
	long infinity = numeric_limits<int>::max();

	// Define the current distance
	long distance;

	// Loop through all of the calculations
	for (int k=1; k<graph_size; k++) {
		for (int i=0; i<graph_size; i++) {
			for (int j=0; j<graph_size; j++) {

				// Local values
				long dist1 = FWgraph.at(i).at(k).at(k-1);
				long dist2 = FWgraph.at(k).at(j).at(k-1);

				// Get current value
				if ((dist1 == infinity) ||
					(dist2 == infinity))
					distance = infinity;
				else
					distance = dist1 + dist2;

				long dist3 = FWgraph.at(i).at(j).at(k-1);

				// Compare to inheritable value
				if (dist3 < distance)
					FWgraph.at(i).at(j).at(k) = dist3;
				else
					FWgraph.at(i).at(j).at(k) = distance;
			}	// for i
		}	// for j
	}	//for k

	// Check for negative-cost cycle
	int graphSize = graph_size - 1;
	for (int i=0; i<graph_size; i++) {
		long dist = FWgraph.at(i).at(i).at(graphSize);
		if (dist < 0) {
			cout << "FWgraph[" << i << "][" << i << "][" << graphSize << "] = " << dist << endl;
			return "NULL";
		}
	}

	// Return result
	return "OK";

}	// end getShortestPath()

// Get shortest-shortest path
long ShortestPath::getFWShortestShortestPath() {

	// Local shortest-shortest-path variable
	long ssp = numeric_limits<int>::max();

	// Find the shortest-shortest path
	for (int i=0; i<graph_size; i++)
		for (int j=0; j<graph_size; j++) {

			// Current shortest path value
			long dist = FWgraph.at(i).at(j).at(graph_size-1);

			// Check if smaller
			if (dist < ssp)
				ssp = dist;
		}

	// Return the shortest-shortest path distance
	return ssp;

}	// end - GetfWShortestShortestPath()

void ShortestPath::createPath_list() {

	// Generate if necessary
	if (pPath_list == NULL)
		pPath_list = new list<int>;
	// Else clear it
	else
		pPath_list->clear();

}

// Generate the path node list
void ShortestPath::genPathNodeList(vector<int>& path_list, int node) {

	// Check for a valid path node list
	if (pPath_list == NULL) {
		cout << "No path node list" << endl;
		return;
	}

	// At starting node
	if(node == 0) {
		pPath_list->push_back(0);
	}
	// No path to this node
	else if(path_list.at(node) == -1)
		return;
	// Recurse to next node
	else {
		genPathNodeList(path_list, path_list.at(node));
		// Put the node into path node list
		pPath_list->push_back(node);
	}

}	// end - genPathNodeList()

// Print the shortest path node list and distance
void ShortestPath::print_ShortestPath(Graph graph) {

	// Check for a valid path node list
	if (pPath_list == NULL) {
		cout << "No path node list" << endl;
		return;
	}


	// Print the header
	cout << "Shortest Path:" << endl;

	// Check for a valid list
	if (pPath_list == NULL) {
		cout << "No path node list" << endl;
		return;
	}

	// Output the nodes
	bool firstNode = true;
	for (auto node : *pPath_list) {
		if (firstNode) {
			cout << node;
			firstNode = false;
		} else {
			cout << " -> " << node;
		}
	}

	// Print the distance
	cout << " = " << shortest_path_distance << endl << endl;

}	// end - printPath()

void ShortestPath::print_FWgraph(int k) {

	// Define infinity
	long infinity = numeric_limits<int>::max();

	// Print the k plane value
	cout << "k = " << k << endl;

	// Print the graph
	for (int i=graph_size-1; i>=0; i--) {
		for (int j=0; j<graph_size; j++) {

			// Print the graph
			long entry = GetGraphEntry(i, j, k);
			if (entry == infinity)
				cout << "  ";
			else
				cout << GetGraphEntry(i, j, k) << " ";
		}
		// Print the line ending
		cout << endl;
	}
	// Print a divider
	cout << endl;

}
