/*
 * Graph.cpp
 *
 *  Created on: Oct 8, 2016
 *      Author: Shawn Flynn
 */

using namespace std;


#include "Graph.h"


// Class methods

Graph::Graph(): vertices(50),
				edges(0),
				pNeighbors_list(nullptr),
				reach(nullptr) {

	// Generate the graph columns
	graph.resize(vertices);

	// Insert the graph rows
	fill(graph.begin(), graph.end(), vector<int>(vertices));

}	// end - Graph()

// Generates a graph of the specified size
// size must be specified to distinguish this from the default constructor
Graph::Graph(const int SIZE):vertices(SIZE),
							 edges(0),
							 pNeighbors_list(nullptr),
							 reach(nullptr) {

	// Check for a valid size
	if (SIZE < 2)
		return;

	// Generate the graph columns
	graph.resize(SIZE);

	// Insert the graph rows
	fill(graph.begin(), graph.end(), vector<int>(SIZE));

}	// end - Graph(size)

// Generates a graph from data contained in the specified file
// line 1 = # of nodes
// lines  = tail head distance
Graph::Graph(string file_name):	vertices(0),
								edges(0),
								pNeighbors_list(nullptr),
								reach(nullptr) {

	// graph size
	int size;

	// Setup a file stream
	ifstream data_file(file_name);
	if (!data_file) {
		cerr << "Couldn't open file: " << file_name << endl;
		return;
	}

	// Define input stream iterators
	istream_iterator<int> input(data_file);
	istream_iterator<int> eos;

	// read in the graph size
	if (input != eos)
		size = *input++;

	// Check for a valid size
	if (size < 2)
		return;

	// Initialize vertices member
	vertices = size;

	// Generate the graph columns
	graph.resize(size);

	// Insert the graph rows
	fill(graph.begin(), graph.end(), vector<int>(size));

	// Read in and add the edges
	while ( input != eos) {

		// Get the graph nodes
		int x = *input++ - 1;
		int y = *input++ - 1;

		// Check validity
		if (x < 0 || x >= size || y < 0 || y >= size) {
			cerr << "Invalid graph node: " << x << ":" << y << endl;
			return;
		}

		// Get the edge value
		int value = *input++;

		// If the edge doesn't exist increment the edge count
		if (graph[x][y] == 0)
			edges++;

		// Set the x->y value
		graph[x][y] = value;
	}

}	// end - read_graph()

// Destructor
Graph::~Graph() {

	// Deallocate the neighboring node list
	if (pNeighbors_list != nullptr)
		delete pNeighbors_list;
/*
	// Deallocate the graph
	if (!graph.empty()) {
		// Iterate through the vertices
		for (auto vertex : graph) {
			if (!vertex.empty())
				vertex.clear();
		}
		// Clear the graph
		graph.clear();
	}
*/
}	// end - ~Graph()


// Methods

// Depth-first search utility method
void Graph::dfsearch(int node, int &count) {

	// Set initial node as reachable
	reach[node] = 1;

	// Increment the count
	count++;

	// Look for reachable nodes from here
	for(int j = 1; j < vertices; j++) {
		// If nodes are connected
		if(adjacent(node, j))
		{
			// If not already reached
			if(!reach[j])
				// DFS this node
				dfsearch(j,count);
		}
	}
}

// Return the number of vertices
int Graph::Vertices() {
	return vertices;
}

// Return the number of edges
int Graph::Edges() {
	return edges;
}

// Return true if vertices are adjacent
bool Graph::adjacent(int x, int y) {
	// Check for valid arguments
	if (x < 0 || x >= vertices || y < 0 || y >= vertices)
		return false;
	// Return true if edge exists
	return (graph[x][y] != 0);
}

// Return a list of connected vertices
list<int>* Graph::neighbors(int x) {

	// Check for valid node index
	if (x < 0 || x >= vertices)
		return nullptr;

	// Generate the neighbors list if necessary
	if (pNeighbors_list == nullptr)
		pNeighbors_list = new list<int>;
	else
		// Clear the list
		pNeighbors_list->clear();

	// Add neighbors to the list
	for (auto &y : graph[x]) {
		if (y != 0) {
			pNeighbors_list->push_back(y);
		}
	}

	// return the list
	return pNeighbors_list;
}

// Add the specified x->y edge
void Graph::add(int x, int y) {

	// Check for valid arguments;
	if (x < 0 || x >= vertices || y < 0 || y >= vertices)
		return;

	// Set the x->y value to 1
	graph[x][y] =  1;

	// Increment the number of edges
	edges++;
}

// Remove the specified x->y edge
void Graph::remove(int x, int y) {

	// Check for valid arguments;
	if (x < 0 || x >= vertices || y < 0 || y >= vertices)
		return;

	// If the edge exists decrement the edge count
	if (graph[x][y] != 0)
		edges--;

	// Set the x->y value to 0
	graph[x][y] = 0;
}

// Return the edge value
int Graph::get_edge_value(int x, int y) {

	// Check for valid arguments;
	if (x < 0 || x >= vertices || y < 0 || y >= vertices)
		return 0;

	// Return the x->y edge value
	return graph[x][y];
}

// Set the edge value
void Graph::set_edge_value(int x, int y, int a) {

	// Check for valid arguments;
	if (x < 0 || x >= vertices || y < 0 || y >= vertices)
		return;

	// If the edge doesn't exist increment the edge count
	if (graph[x][y] == 0)
		edges++;

	// Set the x->y value
	graph[x][y] = a;
}

// Get graph density
double Graph::get_density() {

	// Return the graph density
	return (edges * 1.0) / (vertices * vertices);

}

// is_connected() method
bool Graph::is_connected(const int SIZE) {

	// Initialize the reach array
	reach = new int[SIZE]{};

	// Initialize the count
	int count = 0;

	// Depth-first search of the graph
	dfsearch(0, count);

	// Deallocate the reach array
	delete [] reach;

	// Check result
	if(count == vertices)
		return true;
	else
		return false;
}

void Graph::print_matrix(style_t style) {

	const int cout_width = 5;

	// Output the title
	cout << "Adjacency Matrix:" << endl << endl;

	// Output top header line
	cout << "   ";
	int i = 0;
	for (auto x : graph) {
		cout.width(cout_width);
		if (style == NUMERIC)
			cout << i++;
		if (style == LOWERCASE)
			cout << static_cast<char>(97 + i++);
		if (style == UPPERCASE)
			cout << static_cast<char>(65 + i++);
	}
	cout << endl;
	// Output the graph rows
	i = 0;
	for (auto &x : graph){
		cout.width(cout_width);
		// Output vertex index
		if (style == NUMERIC)
			cout << i++;
		if (style == LOWERCASE)
			cout << static_cast<char>(97 + i++);
		if (style == UPPERCASE)
			cout << static_cast<char>(65 + i++);
		// Output vertex values
		for (auto &y : x) {
			cout.width(cout_width);
			cout << y;
		}
		cout << endl;
	}
	cout << endl;

}	// end - print_matrix()

// Print the Edge List
void Graph::print_edge_list(style_t style) {

	// Output the title
	cout << "Edge List:" << endl << endl;

	// Output the rows
	int i = 0;
	for (auto &x : graph) {
		if (style == NUMERIC)
			cout << " " << i++;
		if (style == LOWERCASE)
			cout << " " << static_cast<char>(97 + i++);
		if (style == UPPERCASE)
			cout << " " << static_cast<char>(65 + i++);
		// Output the columns
		int j = 0;
		for (auto &y : x) {
			if (y != 0) {
				if (style == NUMERIC)
					cout << " -> " << j << ":" << y;
				if (style == LOWERCASE)
					cout << " -> "  << static_cast<char>(97 + j) << ":" << y;
				if (style == UPPERCASE)
					cout << " -> "  << static_cast<char>(65 + j) << ":" << y;
			}
			j++;
		}
		cout << endl;
	}
	cout << endl;

}	// end - print_edge_list()

// Print the graph statistics
void Graph::print_stats() {

	cout << endl;

	// Print the number of vertices
	cout << "vertices = " << vertices << endl;

	// Print the number of edges
	cout << "edges = " << edges << endl;

	// Print the graph density
	cout << "density = " << get_density() << endl;

	cout << endl;

}	// end - print_stats()

// Print all output
void Graph::print_graph(style_t style) {

	// Print graph statistics
	print_stats();

	// Print the Adjacency matrix
	print_matrix(style);

	// Print the edge list
	print_edge_list(style);

}	// end - print_graph()
