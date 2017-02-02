import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;


public class ContractGraph {

	// Randomly chosen vertices
	static Integer vertex1, vertex2;
	// Vertex indices
	static int vertex1Index, vertex2Index;

	// Edge list structures for the random vertices
	private static ArrayList<Integer> edgeList1;
	private static ArrayList<Integer> edgeList2;


	public static int ContractAdjList() {

		// Loop until only two vertices are left
		while (FindMinCuts.adjList.size() > 2) {

			// Get a random edge
			GetRandomEdge();

			// Contract the random vertices
			ContractVertices();

			// Test
			if (FindMinCuts.PRINT) {

				// Print the random vertices
				System.out.println( "(" + Integer.toString(vertex1)
									+ ", "
									+ Integer.toString(vertex2)
									+ ")");

				System.out.println();

				// Print the Adjacency List
				GetAdjacencyList.PrintAdjList();

			}

		}

		// Get the first remaining vertex entry
		for (Entry<Integer, ArrayList<Integer>> vertexEntry :
											FindMinCuts.adjList.entrySet()) {

			// Return the ArrayList size for this vertex
			return vertexEntry.getValue().size();

		}

		// Shouldn't return from here
		return 0;

	}	// end - ContractAdjList()


	private static void ContractVertices() {

		// Merge vertex2 list into vertex1

		// Add the second edge list to the first
		edgeList1.addAll(edgeList2);

		// Remove the second edge list
		FindMinCuts.adjList.remove(vertex2);

		// Swap all occurrences of the second vertex with the first

		// Loop through all of the Adjacency List entries
		for (Entry<Integer, ArrayList<Integer>> vertexEntry :
											FindMinCuts.adjList.entrySet()) {

			// Get the edge list for this entry
			ArrayList<Integer> edgeList = vertexEntry.getValue();

			// Swap the second vertex for the first
			while (edgeList.contains(vertex2)) {

				// Remove it
				edgeList.remove(vertex2);

				// Add the first vertex
				edgeList.add(vertex1);

			}	// contains(vertex2)
		}	// adjList.hasNext()

	}	// end - ContractVertices()


	private static void GetRandomEdge() {

		// Get a randomly selected edge

		// Get the number of vertex entries
		int max = FindMinCuts.adjList.size();

		// Pick a vertex at random
		vertex1Index = (int) (FindMinCuts.randomGenerator.nextInt(max));

		// Get the list of vertices
		Set<Integer> keySet = FindMinCuts.adjList.keySet();

		// Convert to an array of integers
		Integer keyList[] = new Integer[max];
		keyList = keySet.toArray(keyList);

		// Get the random vertex
		vertex1 = keyList[vertex1Index];

		// Get the first vertex's edge list
		edgeList1 = FindMinCuts.adjList.get(vertex1);

		// Get the number of edges
		max = edgeList1.size();

		// Check for a single remaining edge
		if (max == 1) {

			// Grab the remaining edge
			vertex2 = edgeList1.get(0);

			// Remove the empty edge list
			FindMinCuts.adjList.remove(vertex1);

		} else {

			// Pick one edge at random
			vertex2 = edgeList1.get(FindMinCuts.randomGenerator.nextInt(max));

			// TEST - print
			if (FindMinCuts.TEST) {
				System.out.println(FindMinCuts.adjList.size() + " Edges");
				System.out.print("{");
				System.out.print(vertex1.toString() + ", ");
				System.out.print(vertex2.toString() + ")");
				System.out.println();
			}

			// Get this vertex's edge list
			edgeList2 = FindMinCuts.adjList.get(vertex2);

			// Remove the first vertex from the second's edge list
			while (edgeList2.contains(vertex1))
				edgeList2.remove(vertex1);

		}	// max > 1

		// Remove the second vertex from the first's edge list
		while (edgeList1.contains(vertex2))
			edgeList1.remove(vertex2);

	}	// end - GetRandomVertices()

}	// end - ContractGraph class
