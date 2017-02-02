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

	// Floyd-Warshall graph
//	vector< vector < vector<int> > > FWgraph;
	vector< vector < vector<long> > > FWgraph;

public:

	// Constructor for FW graphs
	ShortestPath(string);

	// Get number of nodes
	int getGraphSize();

	// Get a graph entry
	long GetGraphEntry(int, int, int);

	// Get the shortest path distance
	int getShortestPathDistance();

	// Dijkstra shortest-path algorithm
	void getShortestPath(Graph, const int, const int);

	// Floyd-Warshall shortest-path algorithm
	string getFWShortestPath();

	// Get the FW Shortest-shortest path
	long getFWShortestShortestPath();

	// Generate the path node list
	void genPathNodeList(vector<int>&, int);

	void createPath_list();

	// Print the shortest path
	void print_ShortestPath(Graph);

	void print_FWgraph(int);

};

#endif /* SHORTESTPATH_H_ */
