import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;

public class GetAdjacencyList {


	// Construct the Adjacency List from text file data
	public static void GetAdjList(String filename) throws IOException {

		// Line data = String
		String line = null;

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

		// Loop through all of the lines in the file
		while (line != null) {

			// Edge list
			ArrayList<Integer> edgeList = new ArrayList<>();

			// Skip empty lines.
			line = line.trim();
			if (line.length() == 0) {
				continue;
			}

			// Get the edge list values
			String[] edges = line.trim().split("\\s+");

			// The first one is the key
			Integer vertex = Integer.parseInt(edges[0]);

			// The rest are adjacent vertices
			for (int i = 1; i < edges.length; i++) {

				// Add current edge to the list
				edgeList.add(Integer.parseInt(edges[i]));
			}

			// Add current vertex and edge list
			FindMinCuts.adjList.put(vertex, edgeList);

			// Read the next line of the file
			line = in.readLine();

		}

		// Close the Buffered Reader
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}	// end - GetAdjList()

	public static void PrintAdjList() {

		// Loop through all of the Adjacency List entries
		for (Entry<Integer, ArrayList<Integer>> entry : FindMinCuts.adjList.entrySet()) {

			// Print out the entry
			System.out.println(entry);
		}

		// Print a blank line
		System.out.println();

	}	// end - PrintAdjList()

}	// end - GetAdjacencyList class
