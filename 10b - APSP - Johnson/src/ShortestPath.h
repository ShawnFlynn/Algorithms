/*
 * ShortestPath.h
 *
 *  Created on: Oct 8, 2016
 *      Author: Shawn Flynn
 */

#ifndef SHORTESTPATH_H_
#define SHORTESTPATH_H_

using namespace std;

#include "Graph.h"

class ShortestPath {

private:

	// Shortest path distance
	int shortest_path_distance;

	// Graph size
	int graph_size;

	// List of path nodes
	list<int>* pPath_list;

	// 3-D graph class
	Graph APSPgraph;

	// Vertex adjustment list
	int* P;

public:

	// k-plane indices
	int init = 0;
	int prev = 1;
	int curr = 2;

	// Algorithms
	string sD  = "Dijkstra";
	string sFW = "Floyd-Warshall";
	string sJ  = "Johnson";

	// Constructor for Dijkstra graphs
	ShortestPath(string);

	// Constructor for ASPS graphs
	ShortestPath(string, string);

	// Class destructor
	~ShortestPath();

	// Get number of nodes
	int getGraphSize();

	// Get a graph entry
	long GetGraphEntry(int, int, int);

	// Get the shortest path distance
	int getShortestPathDistance();

	// Single-Source Shortest Path
	// Dijkstra shortest-path algorithm
	void getShortestPath(Graph, const int, const int);

	// All-Pairs Shortest Path
	// Floyd-Warshall's shortest-path algorithm
	string getAPSPShortestPath(string);

	// Johnson's shortest-path algorithm
	string getJShortestPath(string);

	// Get the APSP Shortest-shortest path
	long getAPSPShortestShortestPath();

	// Generate the path node list
	void genPathNodeList(vector<int>&, int);

	void createPath_list();

	// Print the shortest path
	void print_ShortestPath(Graph);

	void print_APSPgraph(int);

};

#endif /* SHORTESTPATH_H_ */
