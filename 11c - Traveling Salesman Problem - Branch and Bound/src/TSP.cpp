/*
 * TSP.cpp
 *
 *  Created on: Nov 14, 2016
 *      Author: User
 */


// C++ program to solve Traveling Salesman Problem
// using Branch and Bound.
#include <bits/stdc++.h>

using namespace std;

const bool DEBUG = true;
//	const bool DEBUG = false;

// File name
const string assignment = "tsp.txt";

//const int N = 4;
int N;

//adjacency matrix
double distances[25][25];

//file data
double data[25][2];

// final_path[] stores the final solution ie, the
// path of the salesman.
//int final_path[N+1];
int final_path[26];

// visited[] keeps track of the already visited nodes
// in a particular path
//bool visited[N];
bool visited[25];

// Stores the final minimum weight of shortest tour.
//int final_res = INT_MAX;
double final_res = DBL_MAX;

// Function to copy temporary solution to
// the final solution
void copyToFinal(int curr_path[])
{
	for (int i=0; i<N; i++)
		final_path[i] = curr_path[i];
	final_path[N] = curr_path[0];
}

// Function to find the minimum edge cost
// having an end at the vertex i
//int firstMin(int adj[N][N], int i)
double firstMin(double adj[25][25], int i)
{
//	int min = INT_MAX;
	double min = DBL_MAX;
	for (int k=0; k<N; k++)
		if (adj[i][k]<min && i != k)
			min = adj[i][k];
	return min;
}

// function to find the second minimum edge cost
// having an end at the vertex i
//int secondMin(int adj[N][N], int i)
double secondMin(double adj[25][25], int i)
{
//	int first = INT_MAX, second = INT_MAX;
	double first = DBL_MAX, second = DBL_MAX;
	for (int j=0; j<N; j++)
	{
		if (i == j)
			continue;

		if (adj[i][j] <= first)
		{
			second = first;
			first = adj[i][j];
		}
		else if (adj[i][j] <= second &&
				 adj[i][j] != first)
			second = adj[i][j];
	}
	return second;
}

// function that takes as arguments:
// curr_bound -> lower bound of the root node
// curr_weight-> stores the weight of the path so far
// level-> current level while moving in the search
//         space tree
// curr_path[] -> where the solution is being stored which
//                would later be copied to final_path[]
//void TSPRec(int adj[N][N], int curr_bound, int curr_weight,
//			int level, int curr_path[])
void TSPRec(double adj[25][25], double curr_bound, double curr_weight,
			int level, int curr_path[])
{
	// base case is when we have reached level N which
	// means we have covered all the nodes once
	if (level==N)
	{
		// check if there is an edge from last vertex in
		// path back to the first vertex
		if (adj[curr_path[level-1]][curr_path[0]] != 0)
		{
			// curr_res has the total weight of the
			// solution we got
//			int curr_res = curr_weight + adj[curr_path[level-1]][curr_path[0]];
			double curr_res = curr_weight + adj[curr_path[level-1]][curr_path[0]];

			// Update final result and final path if
			// current result is better.
			if (curr_res < final_res)
			{
				copyToFinal(curr_path);
				final_res = curr_res;
			}
		}
		return;
	}

	// for any other level iterate for all vertices to
	// build the search space tree recursively
	for (int i=0; i<N; i++)
	{
		// Consider next vertex if it is not same (diagonal
		// entry in adjacency matrix and not visited
		// already)
		if (adj[curr_path[level-1]][i] != 0 &&
			visited[i] == false)
		{
//			int temp = curr_bound;
			double temp = curr_bound;
			curr_weight += adj[curr_path[level-1]][i];

			// different computation of curr_bound for
			// level 2 from the other levels
			if (level==1)
				curr_bound -= ((firstMin(adj, curr_path[level-1]) +
								firstMin(adj, i))/2);
			else
				curr_bound -= ((secondMin(adj, curr_path[level-1]) +
								firstMin(adj, i))/2);

			// curr_bound + curr_weight is the actual lower bound
			// for the node that we have arrived on
			// If current lower bound < final_res, we need to explore
			// the node further
			if (curr_bound + curr_weight < final_res)
			{
				curr_path[level] = i;
				visited[i] = true;

				// call TSPRec for the next level
				TSPRec(adj, curr_bound, curr_weight, level+1, curr_path);
			}

			// Else we have to prune the node by resetting
			// all changes to curr_weight and curr_bound
			curr_weight -= adj[curr_path[level-1]][i];
			curr_bound = temp;

			// Also reset the visited array
			memset(visited, false, sizeof(visited));
			for (int j=0; j<=level-1; j++)
				visited[curr_path[j]] = true;
		}
	}
}

// This function sets up final_path[]
//void TSP(int adj[N][N])
void TSP(double adj[25][25])
{
	int curr_path[N+1];

	// Calculate initial lower bound for the root node
	// using the formula 1/2 * (sum of first min +
	// second min) for all edges.
	// Also initialize the curr_path and visited array
//	int curr_bound = 0;
	double curr_bound = 0;
	memset(curr_path, -1, sizeof(curr_path));
	memset(visited, 0, sizeof(curr_path));

	// Compute initial bound
	for (int i=0; i<N; i++)
		curr_bound +=  (firstMin(adj, i) + secondMin(adj, i));

	// Rounding off the lower bound to an integer
//	curr_bound = (curr_bound&1)? curr_bound/2 + 1 : curr_bound/2;
	curr_bound = (int(curr_bound)&1)? curr_bound/2 + 1 : curr_bound/2;

	// We start at vertex 1 so the first vertex
	// in curr_path[] is 0
	visited[0] = true;
	curr_path[0] = 0;

	// Call to TSPRec for curr_weight equal to
	// 0 and level 1
	TSPRec(adj, curr_bound, 0, 1, curr_path);
}


void GenDist() {

	// Set the distances into the graph
	for (int i=0; i<N; i++) {
		for (int j=0; j<N; j++) {
			// Calculate the Euclidean distance
			double dist0 = data[i][0] - data[j][0];
			dist0 *= dist0;
			double dist1 = data[i][1] - data[j][1];
			dist1 *= dist1;
			double dist = sqrt( dist0 + dist1 );

			// Set the distance in the graph
			distances[i][j] = dist;

			// TEST - output the distance graph
			if (DEBUG) {
				cout << setw(15) << std::fixed << std::setprecision(8)
					 << dist << " " ;
			}
		}
		cout << endl;
	}

}

void GetData(string filename) {

	// Setup a file stream
	ifstream data_file(filename);
	if (!data_file) {
		cerr << "Couldn't open file: " << filename << endl;
		return;
	}

	// Read in the number of vertices
	data_file >> N;

	// Local line values
	double value1, value2;

	// Loop through the file entries
	for (int index = 0; index < N; index++) {

		// Read values from file
		data_file >> value1 >> value2;

		// Insert into array
		data[index][0] = value1;
		data[index][1] = value2;

		// TEST - output the data
		if (DEBUG) {
			// Print the raw data array
			cout << std::fixed << std::setprecision(4)
				 << data[index][0] << " " << data[index][1] << endl;
		}
	}

	// Close the data file
	data_file.close();
	return;

}	// end - GetData()

// Print the time
void PrintTime(float mili, int type) {

	// Set the time values
	int hr  = (int) (mili / 36000);
	mili -= hr * 360000;
	int min = (int) (mili / 60000);
	mili -= min * 60000;
	float sec = (mili / 1000);


	// Print the time type
	if (type == 0)
		cout << "Read time = ";
	else
		cout << "Execution time = ";

	// Print the time
	if (hr != 0) {
		if (hr == 1)
			cout << " 1 hour ";
		else
			cout << hr << " hours ";
	}

	if (min != 0) {
		if (min == 1)
			cout << " 1 minute ";
		else
			cout << min << " minutes ";
	}

	if (sec != 0) {
		if (sec == 1)
			cout << " 1 second ";
		else
			cout << sec << " seconds ";
	}

	// Print the line ending
	cout << endl;

}	// end - PrintTime()

// Driver code
int main()
{
	//Adjacency matrix for the given graph
/*
	int adj[N][N] = {	{0, 10, 15, 20},
						{10, 0, 35, 25},
						{15, 35, 0, 30},
						{20, 25, 30, 0}
	};
*/

	// Read location data from file
	GetData(assignment);

	// Generate the distance matrix
	GenDist();

	// Calculate the tour distance
	TSP(distances);

	printf("Minimum cost : %f\n", final_res);
	printf("Path Taken : ");
	for (int i=0; i<=N; i++)
		printf("%d ", final_path[i]);

	return 0;
}

