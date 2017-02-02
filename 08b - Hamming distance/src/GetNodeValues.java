import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GetNodeValues {

	// Construct the Adjacency List from text file data
	public static void GetNodes(String filename) throws IOException {

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

		// Set the number of nodes
		int numNodes  = Integer.parseInt(values[0]);
		HammingDistance.numNodes = numNodes;

		// Create the node list
		HammingDistance.nodeList = new ArrayList<>(numNodes);

		// Set the number of bits
		int numBits = Integer.parseInt(values[1]);
		HammingDistance.numBits = numBits;

		// Read a line of the file
		try {
			line = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Node index
		int nodeIndex = 0;

		// Loop through all of the lines in the file
		while (line != null) {

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

			// Get the edge list values
			values = line.trim().split("\\s+");

			// Node value
			int nodeValue = 0;

			// Read the bit values and convert to an integer
			for (int i=numBits-1; i>=0; i--) {
				// Count the number of 1 bits
				int bitValue = Integer.parseInt(values[numBits-1-i]);
				if (bitValue == 1) {
					// Set the bit in the node value
					nodeValue += 1 << i;
				}
			}

			// Create a NodeEntry
			NodeEntry nodeEntry = new NodeEntry(nodeValue,		// nodeId
												nodeIndex,		// clusterId
												-1,				// nextNode
												1,				// clusterSize
												nodeIndex);		// clusterTail

			// Add current node entry to the node list
			HammingDistance.nodeList.add(nodeIndex, nodeEntry);

			// Read a line of the file
			try {
				line = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Increment the node index
			nodeIndex++;

		}	// while (line != null)

		// Close the Buffered Reader
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}	// end - GetNodes()

}
