/*
 * Graph.h
 *
 *  Created on: Oct 8, 2016
 *      Author: Shawn Flynn
 */

#ifndef GRAPH_H_
#define GRAPH_H_

using namespace std;

#include <iostream>
#include <iterator>
#include <fstream>

#include <vector>
#include <list>
#include <string>



// Class for the Adjacency Matrix graph representation
class Graph {

private:

	// Number of vertices
	int vertices;
	// Number of edges
	int edges;

	// The Adjacency Matrix
	vector< vector<int> > graph;

	// Three dimensional Adjacency Matrix
	vector< vector< vector<long> > > APSPgraph;

	// The neighboring node list
	list<int>* pNeighbors_list;

	// Reach array for dfsearch()
	int* reach;

	// Depth-first search
	void dfsearch(int node, int &count);

public:

	// APSP algorithms
	string sD  = "Dijkstra";
	string sFW = "Floyd-Warshall";
	string  sJ = "Johnson";

	// Output style
	enum style_t {NUMERIC, LOWERCASE, UPPERCASE};

	// Constructors

	// Default - vertices = 50
	Graph();

	// Generates a graph of the specified size
	Graph(const int);

	// Generate a 3-D graph
	Graph(int, int, const string);

	// Generates a graph from data contained in the specified file
	Graph(string);

	// Destructor
	~Graph();


	// Methods

	// Return the number of vertices
	int Vertices();

	// Return the number of edges
	int Edges();

	// Return true if vertices are adjacent
	bool adjacent(int, int);

	// Return a list of connected vertices
	list<int>* neighbors(int);

	// Add the specified x->y edge
	void add(int, int);

	// Remove the specified x->y edge
	void remove(int, int);

	// Return the edge value
	int get_edge_value(int, int);

	// Set the edge value
	void set_edge_value(int, int, int);

	// Return ASPS edge value
	long get_APSP_edge_value(int, int, int);

	// Set ASPS edge value
	void set_APSP_edge_value(int, int, int, long);

	// Get graph density
	double get_density();

	// is_connected() function
	bool is_connected(const int);

	// Print the Adjacency Matrix
	void print_matrix(style_t);

	// Print the Edge List
	void print_edge_list(style_t);

	// Print the graph statistics
	void print_stats();

		// Print all output
	void print_graph(style_t);

};	// end - Graph class


#endif /* GRAPH_H_ */
