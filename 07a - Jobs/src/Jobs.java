import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Jobs {

	// TEST flag
//	public static boolean PRINT = true;
	public static boolean PRINT = false;

	// The jobData class
	public static class jobData{
		float score;
		int weight;
		int length;

		// Constructor
		public jobData( float s, int w, int l) {
			score  = s;
			weight = w;
			length = l;
		}
	}

	// The jobData class comparator
	public static class JobDataComparator implements Comparator<jobData> {

		@Override
		public int compare(jobData jobEntryA, jobData jobEntryB) {

			// Compare the scores
			// if A < B
			if (jobEntryA.score < jobEntryB.score)
				return 1;

			// if A > B
			if (jobEntryA.score > jobEntryB.score)
				return -1;

			// if A = B
			// Compare the weights
			if (jobEntryA.score == jobEntryB.score) {

				if (jobEntryA.weight == jobEntryB.weight)
					// if A = B
					return 0;

				if (jobEntryA.weight < jobEntryB.weight)
					// if A < B
					return 1;
				else
					// if A > B
					return -1;
			}

			// Default return
			return 0;

		}	// end - compare()

	}	// end - JobDataComparator class

	// Array of jobData
	public static ArrayList<jobData> Jobs = new ArrayList<jobData>();

	// Sum of completion times
	static long sum_completion_times = 0;
	// Completion time
	static long completion_time = 0;

	// DIFF/RATIO enum
	enum ScoreType {DIFF, RATIO};

	// Global members
	static File file;
	static FileReader inputFile;
	static BufferedReader input;
	static String line = null;
	static String[] lineData;

	public static void main(String[] args) {

		// Test switch value
		int testCase = 0;

		// Test cases											diff/ratio
		switch(testCase) {
		case (6):							// answers =       31814/31814  
			runAnalysis("test_6.txt");
			break;
		case (10):							// answers =       61545/60213
			runAnalysis("test_10.txt");
			break;
		case (32):							// answers =      688647/674634
			runAnalysis("test_32.txt");
			break;
		default:							// answers = 69119377652/67311454237
			runAnalysis("jobs.txt");
			break;
		}	// end - switch()

	}	// end - Main()


	// Run the Diff and Ratio calculations
	public static void runAnalysis(String filename) {
		
		// Get the data
		getData(filename, ScoreType.DIFF);

		// Calculate the DIFF sum
		processData();

		// Output the sum of the completion times
		System.out.println("Diff completion time: " + sum_completion_times);

		// Clear the array
		Jobs.clear();

		// Clear the completion time
		completion_time = 0;

		// Clear the sum of the completion times
		sum_completion_times = 0;

		// Get the data
		getData(filename, ScoreType.RATIO);

		// Calculate the RATIO sum
		processData();

		// Output the sum of the completion times
		System.out.println("RATIO completion time: " + sum_completion_times);

	}	// end - runAnalysis()

	// Calculate the weighted completion time sum
	public static void processData() {

		// Calculate the weighted sum of completion times
		for (int i = 0; i < Jobs.size(); i++) {

			// Update the job completion time
			completion_time += Jobs.get(i).length; 

			// Add the completion time
			sum_completion_times += completion_time * Jobs.get(i).weight;
		}
		
	}	// end - processData()

	// Read data from file into a (score) sorted array
	public static void getData(String filename, ScoreType scoreType) {

		// Open specified text file
		file = new File(filename);
		inputFile = null;
		try {
			inputFile = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		input = new BufferedReader(inputFile);

		// Read a line of the file
		try {
			line = input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Get the number of jobs - isn't needed
		lineData = line.trim().split("\\s+");

		// Read the first job data line
		try {
			line = input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Loop through all of the job entries
		while (line != null) {

			// Split the line into fields
			lineData = line.trim().split("\\s+");

			// Fill in the fields
			int weight = Integer.parseInt(lineData[0]);
			int length = Integer.parseInt(lineData[1]);
			float score;

			// Diff or Ratio
			if (scoreType == ScoreType.DIFF )
				score = weight - length;
			else
				score = (float)weight / (float)length;

			// Add a new record to the array list
			Jobs.add(new jobData(score, weight, length));

			// Read the next job data line
			try {
				line = input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	// end - while(line != null)

		// Sort the Jobs array
		Collections.sort(Jobs, new JobDataComparator());

		// TEST - print out the sorted Jobs array
		if (PRINT) {
			for (int i = 0; i < Jobs.size(); i ++) {
				System.out.println( "Score: "  + Jobs.get(i).score +  '\t' +
									"Weight: " + Jobs.get(i).weight + '\t' +
									"Length: " + Jobs.get(i).length);
			}
		}

	}	// end - getData()

}	// end - Jobs class
