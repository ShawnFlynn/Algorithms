#include <queue>

// Class for the PriorityQueue node entries
class PQ_node {

private:

	int from;
	int to;
	int distance;

public:

	// Constructors
	PQ_node():from(0),to(0),distance(0) {}
	PQ_node(int from, int to, int dist):from(from),to(to),distance(dist) {}

	// Destructor
	~PQ_node() {
	}

	// Set the edge distance
	void set_distance(int dist) {
		distance = dist;
	}

	// Return the edge distance
	int get_distance() {
		return distance;
	}

	// Set the from node
	void set_from(int from) {
		this->from = from;
	}

	// Return the from node
	int get_from() {
		return this->from;
	}

	// Set the to node
	void set_to(int to) {
		this->to = to;
	}

	// Return the to node
	int get_to() {
		return this->to;
	}


};	// end - PQ_node class


// CompareDistance operator for the PriorityQueue
class CompareDistance {
public:
	bool operator()(PQ_node& n1, PQ_node& n2) {
		return (n1.get_distance() < n2.get_distance());
	}
};

// Generate the priority queue
//priority_queue<PQ_node, vector<PQ_node>, CompareDistance> PQ;

