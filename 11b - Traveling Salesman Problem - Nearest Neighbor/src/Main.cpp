/*
 * Main.cpp
 */


using namespace std;

#include "Graph.h"
#include <limits>
#include <iomanip>
#include <math.h>
#include <bitset>
#include <map>

void CalcTour(string);
void PrintTime(float, int );
void GetData(string);

// Maximum graph size
const int MAX_SIZE = 25;

// Data set size
int dataSize;
// Raw file data
double data[MAX_SIZE][2];

// Distance graph
Graph<double> graph(MAX_SIZE);

// Global members
double c = 0;
double cost = numeric_limits<double>::max();

//const bool DEBUG = true;
const bool DEBUG = false;

// Timer values
clock_t startTime, readTime, calcTime;


void swap (int *x, int *y) {
	int temp;
	temp = *x;
	*x = *y;
	*y = temp;
}

void copy_array(int *a, int n) {
	int i;
	float sum = 0;
	for(i = 0; i <= n; i++) {
		sum += graph.get_edge_value(a[i % dataSize], a[(i + 1) % dataSize]);
	}
	if (cost > sum) {
		cost = sum;
	}
}

void permute(int *a, int i, int n) {
	int j;
	if (i == n) {
		copy_array(a, n);
	} else {
		for (j = i; j <= n; j++) {
			swap((a + i), (a + j));
			permute(a, i + 1, n);
			swap((a + i), (a + j));
		}
	}
}

int main() {

	// Data files
	const string test1		= "Test1.txt";
	const string test2		= "Test2.txt";
	const string test3		= "Test3.txt";
	const string test4		= "Test4.txt";

	const string assignment	   = "tsp.txt";
	const string assignment_13 = "tsp-13.txt";
	const string assignment_12 = "tsp-12.txt";

	// Filename
	string filename;


	const int TEST = 0;		// test = 1 - 4 / 0 = assignment

	// Read the graph data from a file
	switch (TEST) {
	case 1:									// 6.47
		filename = test1;
		break;

	case 2:									// 7.89
		filename = test2;
		break;

	case 3:									// 8387
		filename = test3;
		break;

	case 4:									// 9498
//		filename = test4;
		filename = assignment_12;
		break;

	default:

		// Total tour cost
		double firstHalfCost;
		double secondHalfCost;
		double totalTourCost;

		// Calculate the first half of the tour
		filename = assignment_13;
		CalcTour(filename);

		// Set first half of tour cost
		firstHalfCost = cost - graph.get_edge_value(11, 12);

		// First half
		cout << "First half distance: "
			 << std::fixed << std::setprecision(8)
			 << cost << endl;
		cout << "11 -> 12 distance: "
			 << std::fixed << std::setprecision(8)
			 << graph.get_edge_value(11, 12) << endl;
		cout << "First half cost: "
			 << std::fixed << std::setprecision(8)
			 << firstHalfCost << endl;

		// Clean up
		c = 0;
		cost = numeric_limits<double>::max();

		// Calculate the second half of the tour
		filename = assignment_12;
		CalcTour(filename);

		// Set the second half cost
		secondHalfCost = (cost - graph.get_edge_value(0, 1));

		// Second half
		cout << "Second half distance: "
			 << std::fixed << std::setprecision(8)
			 << cost << endl;
		cout << "13 -> 14 distance: "
			 << std::fixed << std::setprecision(8)
			 << graph.get_edge_value(0, 1) << endl;
		cout << "Second half cost: "
			 << std::fixed << std::setprecision(8)
			 << secondHalfCost << endl;

		// Update total tour cost
		totalTourCost = firstHalfCost + secondHalfCost;

		// Generate the complete graph
		GetData(assignment);

		// Total tour cost
		cout << "Total tour distance: "
			 << std::fixed << std::setprecision(8)
			 << totalTourCost << endl;

		cout << "Connection distances: " << endl;
		cout << std::fixed << std::setprecision(8)
			 << "12 -> 13 " << graph.get_edge_value(12, 13) << endl;
		cout << std::fixed << std::setprecision(8)
			 << "11 -> 14 " << graph.get_edge_value(11, 14) << endl;

		// Connect the two halves
//		totalTourCost += graph.get_edge_value(12, 13);
//		totalTourCost += graph.get_edge_value(11, 14);
		totalTourCost += graph.get_edge_value(12, 14);
		totalTourCost += graph.get_edge_value(11, 13);

		// Second half cost
		cout << "Total tour cost: "
			 << std::fixed << std::setprecision(8)
			 << totalTourCost << endl;

		// Return OK
		return 1;

		break;
	}

	// Calculate the tour distance
	CalcTour(filename);

	// Return OK
	return 1;

}	// end - Main()

// Calculate the tour distance
void CalcTour(string filename) {

	// Initialize the clock
	startTime = clock();

	// Process the specific test and print the results
	// Read the file data
	GetData(filename);

	// Get and print out the read time
	readTime = clock();
	PrintTime(readTime-startTime, 0);

	// Generate the node array
	int a[dataSize];

	// Set the node values
	for (int i=0; i<dataSize; i++)
		a[i] = i;

	// Calculate the shortest distance
	permute(a, 0, dataSize-1);

	// Get and print out the calculation time
	calcTime = clock();
	PrintTime(calcTime-readTime, 1);

	cout << endl;
	cout << "Tour distance: "
		 << std::fixed << std::setprecision(8)
		 << cost << endl;

}	// end - CalcTour()

// Get the data from the specified file
// Generate the distance graph
void GetData(string filename) {

	// Setup a file stream
	ifstream data_file(filename);
	if (!data_file) {
		cerr << "Couldn't open file: " << filename << endl;
		return;
	}

	// Read in the number of vertices
	data_file >> dataSize;

	double value1, value2;

	// Loop through the file entries
	for (int index = 0; index < dataSize; index++) {

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

	// Set the distances into the graph
	for (int i=0; i<dataSize; i++) {
		for (int j=0; j<dataSize; j++) {
			// Calculate the Euclidean distance
			double dist0 = data[i][0] - data[j][0];
			dist0 *= dist0;
			double dist1 = data[i][1] - data[j][1];
			dist1 *= dist1;
			double dist = sqrt( dist0 + dist1 );

			// Set the distance in the graph
			graph.set_edge_value(i, j, dist);

			// TEST - output the distance graph
			if (DEBUG) {
				// Print the initialized array
					cout << setw(15) << std::fixed << std::setprecision(8)
						 << graph.get_edge_value(i, j) << " " ;
			}
		}
		if (DEBUG)
			cout << endl;
	}

	// Close the data file
	data_file.close();
	return;

}	// end - GetData()

// Print the time
void PrintTime(float mili, int type) {

	// Set the time values
	int min = (int) (mili / 60000);
	mili -= min * 60000;
	float sec = (mili / 1000);


	// Print the time type
	if (type == 0)
		cout << "Read time = ";
	else
		cout << "Execution time = ";

	// Print the time
	if (min != 0) {
		cout << min << " minutes ";
	}

	if (sec != 0) {
		if (sec == 1)
			cout << sec << " second ";
		else
			cout << sec << " seconds ";
	}

	// Print the line ending
	cout << endl;

}	// end - PrintTime()
