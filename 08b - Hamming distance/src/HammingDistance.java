import java.io.IOException;
import java.util.ArrayList;


public class HammingDistance {

	// Print flag
//	static public boolean PRINT = true;
	static public boolean PRINT = false;

	static public boolean TEST = true;
//	static public boolean TEST = false;

	// Node entry list
	static ArrayList<NodeEntry> nodeList;

	// Number of nodes
	public static int numNodes;

	// Number of bits
	public static int numBits;

	// Number of clusters
	public static int numClusters;

	// Local node declarations
	static NodeEntry indexHead = null;
	static NodeEntry indexNode = null;
	static NodeEntry entryHead = null;
	static NodeEntry entryNode = null;

	// Minimum spacing
	public static int minimumSpacing = 3;

	
	//
	// Main entry point
	//
	public static void main(String[] args) {
		
		// Test switch value
		int testCase = 0;

		// Timer - start
		long startTime = System.nanoTime();

		switch (testCase) {
		case 1:									//Min spacing = 3/clusters = 2
												//Min spacing = 2/clusters = 3
			// Get the data from test1.txt
			try {
				GetNodeValues.GetNodes("test1.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			// Get the data from clustering_big.txt
			try {												// = 6118
				GetNodeValues.GetNodes("clustering_big.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}

		// Timer - read time
		long readTime = System.nanoTime();

		// Print the read time
		PrintTime((readTime - startTime)/1000, 0);

		// Set the number of clusters
		numClusters = numNodes;

		// Generate the clusters
		GenClusters();

		// Timer - execution time
		long execTime = System.nanoTime();

		// Print out the number of clusters
		PrintTime((execTime - readTime)/1000, 1);
		System.out.println("Number of clusters = " + numClusters);

		// Recalculate the number of clusters
		if (TEST) {
			// Cluster counter
			int clusters = 0;
			// Loop through the node list
			for (int index = 0; index < numNodes; index++) {
				// Clusters: clusterId = index
				NodeEntry nodeEntry = nodeList.get(index);
				if (nodeEntry.clusterId == index) {
					clusters++;
					if (PRINT) {
						// Print the cluster nodes
						System.out.print(index);
						while (nodeEntry.nextNode != -1) {
							System.out.print(" -> " + nodeEntry.nextNode);
							nodeEntry = nodeList.get(nodeEntry.nextNode);
						}
						System.out.println();
					}
				}
			}
			System.out.println("TEST: Number of clusters = " + clusters);
		}
	}	// end - main()


	private static void GenClusters() {

		// Node list size
		int size = nodeList.size();

		// Look for differences less than 3
		for (int index=0; index<size; index++) {

			// Get the "index" node
			indexNode = nodeList.get(index);

			// Compare to all other entries
			for (int entry=0; entry<size; entry++) {

				// Don't compare to self
				if (index == entry)
					continue;

				// Get the entry node
				entryNode = nodeList.get(entry);

				// Don't compare to same cluster members
				if (indexNode.clusterId == entryNode.clusterId)
					continue;

				// Calculate the Hamming distance
				int hammingDistance =
						Integer.bitCount(indexNode.value ^ entryNode.value);

				// If the hamming distance is less than the max
				if (hammingDistance < minimumSpacing) {

					// Update the number of clusters
					numClusters--;

					// Get the HEAD of the index node
					if (indexNode.clusterId != index) {
						indexHead = nodeList.get(indexNode.clusterId);
					} else {
						// Copy to indexHead
						indexHead = indexNode;
					}

					// Get the HEAD of the entry node
					if (entryNode.clusterSize != entry) {
						entryHead = nodeList.get(entryNode.clusterId);
					} else {
						// Copy to entryHead
						entryHead = entryNode;
					}

					// Merge the nodes
					mergeNodes(index, entry);

					if (PRINT)
						System.out.println( " index = " + index +
											" entry = " + entry +
											" hamming distance = " + 
											Integer.toString(hammingDistance));
				}
			}	// all other nodes
		}	// each node

	}	// end - GenClusters()


	public static void mergeNodes(int index, int entry) {

		// Local node declarations
		NodeEntry largerHead;
		NodeEntry smallerHead;

		// Determine the larger/smaller clusters
		if (indexHead.clusterSize >= entryHead.clusterSize) {
			// Copy the nodes
			largerHead  = indexHead;
			smallerHead = entryHead;
		} else {
			// Copy the nodes
			largerHead  = entryHead;
			smallerHead = indexHead;
		}

		// Get the tail node of the larger cluster
		NodeEntry tailNode = nodeList.get(largerHead.clusterTail);

		// Merge smaller cluster to larger
		tailNode.nextNode = smallerHead.clusterId;

		// Update the larger cluster
		largerHead.clusterSize += smallerHead.clusterSize;
		largerHead.clusterTail  = smallerHead.clusterTail;

		// Update the smaller cluster
		NodeEntry tempNode = smallerHead;
		tempNode.clusterId = largerHead.clusterId;

		// Loop through the smaller cluster
		while (tempNode.nextNode != -1) {

			// Get the next node
			tempNode = nodeList.get(tempNode.nextNode);

			// Update its cluster id
			tempNode.clusterId = largerHead.clusterId;
		}

	}	// end mergeNodes()


	private static void PrintTime(long nano, int type) {

		// Set the time values
		int min = (int) (nano / 60000000);
		nano -= min * 60000000;
		int sec = (int) (nano / 1000000);
		nano %= 1000000;
		int mil = (int) (nano / 1000);
		nano %= 1000;


		// Print the time type
		if (type == 0)
			System.out.print("Read time = ");
		else
			System.out.print("Execution time = ");

		// Print the time
		if (min != 0) {
			System.out.print(min + " minutes ");
		}

		if (sec != 0) {
			if (sec == 1)
				System.out.print(sec + " second ");
			else
				System.out.print(sec + " seconds ");
		}

		if (mil != 0 || nano != 0) {
			System.out.print(mil + "." + nano + " milliseconds");
		}

		// Print the line ending
		System.out.println();
	}


}	// end - HammingDistance class
