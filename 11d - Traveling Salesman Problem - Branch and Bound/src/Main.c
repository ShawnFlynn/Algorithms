/*
 * Main.c
 *
 *  Created on: Nov 15, 2016
 *      Author: User
 */

#include <stdio.h>
#include <Math.h>
#include <time.h>

void CalcTour();
void GetData(char*);
void PrintTime(float, int);

int DEBUG = 1;
//int DEBUG = 0;

clock_t startTime, calcTime;

int queue[100], stack[100], alt[100], v[100];
int sp,head,tail,i,n,g,j,module;
double s,path,map[100][100];
double data[100][100];

char filename[] = "tsp.txt";
//char filename[] = "test1.txt";

char assign_l[] = "tsp-l.txt";
char assign_r[] = "tsp-r.txt";

int main()
{
	double totalTour;

	// Read in the file data
	GetData(assign_l);

	// Start the clock
	startTime = clock();

	// Calculate shortest tour
	CalcTour();

	// Stop the clock
	calcTime = (double)(clock() - startTime) / (CLOCKS_PER_SEC / 1000);

	// Print the execution time
	PrintTime(calcTime, 1);

	// Print the tour distance
	printf("best route = %9.4f\n", path);

	// Print the tour stops
	for (i=0 ; i<n ; i++)
		printf("%d ",v[i]);

	printf("%d\n",stack[0]+1);

	// Set the total tour distance
	totalTour = path;

	// Read in the file data
	GetData(assign_r);

	// Start the clock
	startTime = clock();

	// Calculate shortest tour
	CalcTour();

	// Stop the clock
	calcTime = (double)(clock() - startTime) / (CLOCKS_PER_SEC / 1000);

	// Print the execution time
	PrintTime(calcTime, 1);

	// Print the tour distance
	printf("best route = %9.4f\n", path);

	// Print the tour stops
	for (i=0 ; i<n ; i++)
		printf("%d ",v[i]);

	printf("%d\n",stack[0]+1);

	// Update the total tour distance
	totalTour += path;

	// Generate the full location data
	GetData(filename);

	// Print distance values
	printf("Sum of two tours: %9.4f\n", totalTour);

	// Update the total tour distance
	totalTour -= map[11][12];
	totalTour -= map[14][12];
	totalTour += map[11][14];

	// Print adjustment values
	printf("Subtract 11 -> 12 edge: %9.4f\n", map[11][12]);
	printf("Subtract 14 -> 12 edge: %9.4f\n", map[14][12]);
	printf("Add 11 -> 14 edge: %9.4f\n", map[11][14]);

	// Print out the total tour distance
	printf("\nTotal tour distance: %9.4f\n", totalTour);

	// Return OK
	return 0;

}	// end - Main()


void CalcTour() {

	//Start with an initial solution from city 1
	for (i=0 ; i<n ; i++) {
		queue[i] = i;
	}

	// Set route length to high value
	path = 50000;
	stack[0] = queue[0];
	alt[0] = 0;
	sp = 0;
	head = 0;
	tail = n-1;
	s = 0;

	// Explore a branch of the factorial tree
	while(1) {
		while(sp<n-1 && s<path)  {
			sp++;
			head++;
			if (head==n)
				head = 0;
			stack[sp] = queue[head];
			s = s + map[stack[sp]][stack[sp-1]];
			alt[sp] = n - sp - 1;
		}

		// Save a better solution
		if (s + map[stack[sp]][stack[0]]<path) {
			path = s + map[stack[sp]][stack[0]];
			for (i=0 ; i<n ; i++)
				v[i] = stack[i]+1;
		}

		// Leaving nodes when there is no more  branches
		while (alt[sp]==0 && sp>=0) {
			tail++;
			if (tail==n)
				tail=0;
			queue[tail] = stack[sp];
			s = s - map[stack[sp]][stack[sp-1]];
			sp--;
		}

		// If Bottom of stack is reached then stop
		if (sp<0)
			break;

		tail++;
		if (tail==n)
			tail=0;
		queue[tail] = stack[sp];
		s = s - map[stack[sp]][stack[sp-1]];

		// Explore an alternate branch
		alt[sp] = alt[sp] - 1;
		head++;
		if (head==n)
			head=0;
		stack[sp] = queue[head];
		s = s + map[stack[sp]][stack[sp-1]];
	}
}	// end - CalcTour()

void GetData(char* filename) {

	// Open the file
	FILE *fp;
	fp = fopen(filename, "r");

	// Read the number of locations
	fscanf(fp, "%d", &n);


	// Local line values
	double value1, value2;

	if (DEBUG)
		printf("\n");

	// Loop through the file entries
	for (int index = 0; index < n; index++) {

		// Read values from file
		fscanf(fp, "%lf%lf", &value1, &value2);

		// Insert into array
		data[index][0] = value1;
		data[index][1] = value2;

		// TEST - output the data
		if (DEBUG) {
			// Print the raw data array
			printf("%9.4lf %9.4lf\n", data[index][0], data[index][1]);
		}
	}

	if (DEBUG)
		printf("\n");

	// Set the distances into the graph
	for (int i=0; i<n; i++) {
		for (int j=0; j<n; j++) {
			// Calculate the Euclidean distance
			double dist0 = data[i][0] - data[j][0];
			dist0 *= dist0;
			double dist1 = data[i][1] - data[j][1];
			dist1 *= dist1;
			double dist = sqrt( dist0 + dist1 );

			// Set the distance in the graph
			map[i][j] = dist;

			// TEST - output the distance graph
			if (DEBUG) {
				printf("%9.5lf ", dist);
			}
		}
		printf("\n");
	}

	if (DEBUG)
		printf("\n");

	// Close the data file
	fclose(fp);
	return;

}	// end - GetData()

// Print the time
void PrintTime(float mili, int type) {

	// Set the time values
	int hr  = (int) (mili / 3600000);
	mili -= hr * 3600000;
	int min = (int) (mili / 60000);
	mili -= min * 60000;
	float sec = (mili / 1000);


	// Print the time type
	if (type == 0)
		printf("Read time: ");
	else
		printf("Execution time: ");

	// Print the time
	if (hr != 0) {
		if (hr == 1)
			printf("1 hour ");
		else
			printf("%d hours ", hr);
	}

	if (min != 0) {
		if (min == 1)
			printf("1 minute ");
		else
			printf("%d minutes ", min);
	}

	if (sec != 0) {
		if (sec == 1)
			printf("1.0 second ");
		else
			printf("%5.3f seconds ", sec);
	}

	// Print the line ending
	printf("\n");

}	// end - PrintTime()
