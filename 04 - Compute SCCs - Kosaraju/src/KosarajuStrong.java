

import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.StringReader; 
import java.util.ArrayList; 
import java.util.Set; 
import java.util.Stack; 

public class KosarajuStrong {

	Stack<Integer> stack; 
	ArrayList<Integer> markedNodes; 
	static Graph graph; 

	static int testCase = 0;


	void kosarajuExec() { 
		allDFS();
		System.out.println("Pass 1:");
		transposeGraph(); 
		System.out.println("Pass 2:");
		DFS2(); 
	} 

	void allDFS() { 
		for (int i = 0; i < graph.nodesCnt; i++) {
			if (markedNodes.get(i) == -1) { 
				markedNodes.set(i, 1); 
				DFS(false, i, null);
			} 
		} 
	} 

	void DFS(boolean second, int vertex, ArrayList<Integer> component) {

		Set<Edge> edges = graph.getAdjacent(vertex); 
		for (Edge e : edges) { 

			if (markedNodes.get(e.to) != -1) { 
				continue; 
			} 
			markedNodes.set(e.to, 1); 

			if (second) { 
				component.add(e.to); 
			} 
			DFS(second, e.to, component); 

		} 
		if (!second) { 
			stack.push(vertex); 
		} 
	} 

	void transposeGraph() { 
		Graph gT = new Graph(graph.nodesCnt, graph.edgesCnt); 

		for (Edge e : graph) { 
			Edge eT = new Edge(e.getTo(), e.getFrom()); 
			gT.addEdge(eT); 
		} 
		// printGraphEdges(gT); 
		graph = gT; 
		markedNodes = new ArrayList<Integer>(); 
		for (int i = 0; i < graph.nodesCnt; i++) {
			markedNodes.add(i, -1); 
		} 
	} 

	void DFS2() { 

		int v; 
		ArrayList<Integer> component = new ArrayList<Integer>(); 
		int compCount = 0;
		for (int i = 0; i < graph.nodesCnt; i++) {
			markedNodes.set(i, -1); 
		} 

		while (!stack.empty()) { 
			v = stack.pop(); 
			markedNodes.set(v, 1); 
			component.add(v); 
			DFS(true, v, component); 
			printComponent(component); 
			compCount++; 
			for (Integer i : component) { 
				stack.remove(i); 
			} 
			component.clear(); 
		} 
		System.out.println("Total components " + compCount);
	} 

	void printComponent(ArrayList<Integer> component) { 

		// filter the number of components
		int cSize = 0;
		if (testCase == 0)
			cSize = 100;

		if (component.size() > cSize) {
			System.out.print("Component : "); 
			System.out.println(component.size());
//			for (Integer i : component) { 
//				System.out.print("-> " + i); 
//			} 
//			System.out.println();
		}
	} 

	public static void main(String[] args) {
		BufferedReader bufReader = null; 
		if (args.length > 0) {
			// Unit Test Mode 
			bufReader = new BufferedReader(new StringReader(
					"1\n4\n4\n0 1\n1 2\n2 0\n2 3\n")); 
		} else { /* 
//			File file = new File("C:\\Test\\strongComp3.txt"); 
			File file = new File("SCC.txt"); 
			try { 
				bufReader = new BufferedReader(new FileReader(file));
			} catch (Exception e) { 
				e.printStackTrace(); 
				return; 
			} 
*/
		} 

		KosarajuStrong ff = new KosarajuStrong();
		String fileName = null;

		try { 
//			int totalGraphs = ff.readTotalGraphCount(bufReader);
//			int totalGraphs = 1;
			System.out.println("Kosaraju Strong Components "); 
//			for (int i = 0; i < totalGraphs; i++) {
//				System.out.println("************** Start Graph " + (i + 1) 
//						+ "******************************");

				// Test cases
				switch(testCase) {
				case 33300:						// Answer: 3,3,3,0,0
					fileName = "33300.txt";
					graph = new Graph(9, 11);
					break;
				case 33200:						// Answer: 3,3,2,0,0
					fileName = "33200.txt";
					graph = new Graph(8, 14);
					break;
				case 33110:						// Answer: 3,3,1,1,0
					fileName = "33110.txt";
					graph = new Graph(8, 9);
					break;
				case 71000:						// Answer: 7,1,0,0,0 
					fileName = "71000.txt";
					graph = new Graph(8, 11);
					break;
				case 63210:						// Answer: 6,3,2,1,0
					fileName = "63210.txt";
					graph = new Graph(12, 20);
					break;
				case 1:						// Answer:
//					fileName = "Test1.txt";		// cyclic
//					graph = new Graph(6, 7);
//					fileName = "Test2.txt";		// 3 -> 6 - between components
//					graph = new Graph(6, 8);
					fileName = "Test3.txt";		// 3 -> 1 - within a component
					graph = new Graph(6, 8);
					break;
				default:						// Answer: 
					fileName = "SCC.txt";
					graph = new Graph(875714, 5105043);
					break;
				}

				// Open the test file
				try { 
					bufReader = new BufferedReader(new FileReader(fileName));
				} catch (Exception e) { 
					e.printStackTrace(); 
					return; 
				}

				ff.readNextGraph(bufReader); 

				System.out.println( "************** Start Graph " +
									"******************************");

				ff.kosarajuExec(); 

		} catch (Exception e) { 
			e.printStackTrace(); 
			System.err.println("Exiting : " + e); 
		} finally { 
			try { 
				bufReader.close(); 
			} catch (Exception f) { 

			} 
		} 
	}	// end - Main()

	int readTotalGraphCount(BufferedReader bufReader) throws Exception {

		return Integer.parseInt(bufReader.readLine()); 
	} 

	// Reads Input 
	void readNextGraph(BufferedReader bufReader) throws Exception {
		try { 
//			graph = new Graph(Integer.parseInt(bufReader.readLine()),
//					Integer.parseInt(bufReader.readLine())); 

			init(); 

			for (int k = 0; k < graph.edgesCnt; k++) {

				String[] strArr = bufReader.readLine().split(" "); 
				int u = Integer.parseInt(strArr[0])-1;
				int v = Integer.parseInt(strArr[1])-1;

				Edge e = new Edge(u, v); 
				graph.addEdge(e); 

			} 

		} catch (Exception e) { 
			e.printStackTrace(); 
			throw e; 
		} 
	} 

	void init() { 
		stack = new Stack<Integer>(); 
		markedNodes = new ArrayList<Integer>(); 
		for (int i = 0; i < graph.nodesCnt; i++) {
			markedNodes.add(i, -1); 
		} 
	} 

	void printGraphEdges(Graph graph) { 
		for (Edge e : graph) { 
			System.out.println("" + e.getFrom() + " -> " + e.getTo()); 
		} 
	} 

} 
