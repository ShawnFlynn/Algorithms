
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	// Distance matrix
	private static double[][] distances;

	// List size
	private static int listSize;

	// Raw file location data
	private static double location[][];

//	public static boolean DEBUG = true;
	public static boolean DEBUG = false;

	/*
	 * Main entry point
	 */
	public static void main(String[] args) {

		// Get the file data
		try {
			GetData("tsp.txt");
//			GetData("Test1.txt");	// 6.47
//			GetData("Test2.txt");	// 7.89
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// Create the distances matrix
		distances = new double[listSize][listSize];

		// Populate the distances matrix
		for (int i = 0; i < listSize; i++) {
			for (int j = 0; j < listSize; j++) {

				// Calculate the Euclidian distance
				double dist_lon = location[i][0] - location[j][0];
				dist_lon *= dist_lon;
				double dist_lat = location[i][1] - location[j][1];
				dist_lat *= dist_lat;
				double dist = Math.sqrt(dist_lon + dist_lat);

				// Put into the distance matrix
				distances[i][j] = dist;

				// Print out the distances
				if (DEBUG) {
					System.out.format("%13.8f  ",   distances[i][j]);
				}
			}
			if (DEBUG) {
				System.out.println();
			}
		}

		// Create a TSP class
		TSP tsp = new TSP(distances, listSize);

		// Calculate the solution
		tsp.generateSolution();

	}	// end - main()

	
	// Construct the Location List from text file data
	public static void GetData(String filename) throws IOException {

		// Line data = String
		String line = null;

		// Line values
		String[] values = null;

		// Open specified text file
		File fil = new File(filename);
		FileReader inputFil = null;
		try {
			inputFil = new FileReader(fil);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader in = new BufferedReader(inputFil);

		// Read a line of the file
		try {
			line = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Read the number of nodes and bits
		values = line.trim().split("\\s+");

		// Set the number of locations
		listSize = Integer.parseInt(values[0]);

		// Create the location list
		location = new double[listSize][2];

		// Read a line of the file
		try {
			line = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Loop through all of the lines in the file
		for (int locationIndex = 0; locationIndex < listSize; locationIndex++) {

			// Skip empty lines.
			line = line.trim();
			if (line.length() == 0) {
				// Read a line of the file
				try {
					line = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				continue;
			}

			// Get the location values
			values = line.trim().split("\\s+");

			// Put into the array
			location[locationIndex][0] = Double.parseDouble(values[0]);
			location[locationIndex][1] = Double.parseDouble(values[1]);

			// Print out the location list
			if (DEBUG) {
				System.out.format("%9.4f  ", location[locationIndex][0]);
				System.out.format("%9.4f%n", location[locationIndex][1]);
			}

			// Read a line of the file
			try {
				line = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}	// locationIndex loop

		// Close the Buffered Reader
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}	// end - GetData()

}	// end - Main class
