
/**
* TSP Branch and Bound
*/

import java.awt.*;
import java.util.*;

public class TSP {

	// Fields
	private final int numRows;
	private final int numCols;

	private TimeInterval t = new TimeInterval();

	//	private int bestTour = Integer.MAX_VALUE / 4;
	private double bestTour = Double.MAX_VALUE / 4;

	private Node bestNode;

	public static Cost c;

	public static ArrayList<Point> newEdge = new ArrayList<Point>();

	// Contains objects of type Point
	private int newNodeCount = 0;
	private int numberPrunedNodes = 0;

//	private Random rnd = new Random();

//	public TSP (short [][] costMatrix, int size, int bestTour) {
	public TSP (double [][] costMatrix, int size, double bestTour) {
		this.bestTour = bestTour;
		numRows = numCols = size;
		c = new Cost(numRows, numCols);
		for (int row = 1; row <= size; row++) {
			for (int col = 1; col <= size; col++) {
//				c.assignCost(costMatrix[row][col], row, col);
				c.assignCost(costMatrix[row-1][col-1], row, col);
			}
		}
	}

	public TSP (short [][] costMatrix, int size) {
		numRows = numCols = size;
		c = new Cost(numRows, numCols);
		for (int row = 1; row <= size; row++) {
			for (int col = 1; col <= size; col++) {
				c.assignCost(costMatrix[row][col], row, col);
			}
		}
	}

	public TSP (double [][] costMatrix, int size) {
		numRows = numCols = size;
		c = new Cost(numRows, numCols);
//		c = new Cost(numRows+1, numCols+1);
		for (int row = 1; row <= size; row++) {
			for (int col = 1; col <= size; col++) {
//				c.assignCost(costMatrix[row-1][col-1], row, col);
				c.assignCost(costMatrix[row-1][col-1], row, col);
			}
		}
	}

	public void generateSolution () {
		Point pt;
		// Load newEdge Vector of edge points
		for (int row = 1; row <= numRows; row++) {
			for (int col = row + 1; col <= numCols; col++) {
				pt = new Point(row, col);
				newEdge.add(pt);
				pt = new Point(-row, -col);
				newEdge.add(pt);
			}
		}
		// Create root node
		Node root = new Node(numRows, numCols);
		newNodeCount++;
		root.computeLowerBound();
		if (Main.DEBUG) {
			System.out.println(
					"Twice the lower bound for root node (no constraints): " +
							root.lowerBound());
		}
		// Apply the branch and bound algorithm
		t.startTiming();
		branchAndBound(root, -1);
		t.endTiming();
		if (bestNode != null) {
			System.out.println("\n\nCost of optimum tour: " +
					bestTour + "\nOptimum tour: " + bestNode.tour() +
					"\nTotal of nodes generated: " + newNodeCount +
					"\nTotal number of nodes pruned: " +
					numberPrunedNodes);
		} else {
			System.out.println(
					"Tour obtained heuristically is the best tour.");
		}
		System.out.println("Elapsed time for entire algorithm: " +
										t.getElapsedTime() + " seconds.");
		System.out.println();
	}

	// Queries

	public int nodesCreated () {
		return newNodeCount;
	}

	public int nodesPruned () {
		return numberPrunedNodes;
	}

	public String tour () {
		if (bestNode != null) {
			return bestNode.tour();
		} else {
			return "";
		}
	}

//	public int tourCost () {
	public double tourCost () {
		return bestTour;
	}

	public byte [] trip () {
		if (bestNode != null) {
			return bestNode.trip();
		} else {
			return null;
		}

	}

	private void branchAndBound (Node node, int edgeIndex) {
		if (node != null && edgeIndex < newEdge.size()) {
			Node leftChild, rightChild;
			int leftEdgeIndex = 0, rightEdgeIndex = 0;
			if (node.isTour()) {
				node.setTour();
				if (node.tourCost() < bestTour) {
					bestTour = node.tourCost();
					bestNode = node;
					if (Main.DEBUG) {
						System.out.println("\n\nBest tour cost so far: " +
								bestTour + "\nBest tour so far: " +
								bestNode.tour() +
								"\nNumber of nodes generated so far: " +
								newNodeCount +
								"\nTotal number of nodes pruned so far: " +
								numberPrunedNodes +
								"\nElapsed time to date for branch and bound: " +
								t.getElapsedTime() + " seconds.\n");
					}
				}
			} else {
				if (node.lowerBound() < 2 * bestTour) {
					// Create left child node
					leftChild = new Node(numRows, numCols);
					newNodeCount++;
					if (Main.DEBUG) {
						if (newNodeCount % 1000 == 0) {
//							Point p = (Point) newEdge.get(edgeIndex);
							t.endTiming();
							System.out.println(
									"\nTotal number of nodes created so far: " +
											newNodeCount +
											"\nTotal number of nodes pruned so far: " +
											numberPrunedNodes +
											"\nElapsed time to date for branch and bound: " +
											t.getElapsedTime() + " seconds.");
						} else if (newNodeCount % 25 == 0) {
							System.out.print(".");
						}
						if (newNodeCount % 10000 == 0 &&
								bestNode != null) {
							System.out.println(
									"\n\nBest tour cost so far: " +
											bestTour + "\nBest tour so far: " +
											bestNode.tour());
						}
					}
					leftChild.setConstraint(copy(node.constraint()));
					if (edgeIndex != -1 &&
							((Point) newEdge.get(edgeIndex)).getX() > 0) {
						edgeIndex += 2;
					} else {
						edgeIndex++;
					}
					if (edgeIndex >= newEdge.size()) {
						return;
					}
					Point p = (Point) newEdge.get(edgeIndex);
					leftEdgeIndex =
							leftChild.assignPoint(p, edgeIndex);
					leftChild.addDisallowedEdges();
					leftChild.addRequiredEdges();
					leftChild.addDisallowedEdges();
					leftChild.addRequiredEdges();
					leftChild.computeLowerBound();
					if (leftChild.lowerBound() >= 2 * bestTour) {
						leftChild = null;
						numberPrunedNodes++;
					}
					// Create right child node
					rightChild = new Node(numRows, numCols);
					newNodeCount++;
					if (Main.DEBUG) {
						if (newNodeCount % 1000 == 0) {
							System.out.println(
									"\nTotal number of nodes created so far: " +
											newNodeCount +
											"\nTotal number of nodes pruned so far: " +
											numberPrunedNodes);
						} else if (newNodeCount % 25 == 0) {
							System.out.print(".");
						}
					}
					rightChild.setConstraint(copy(node.constraint()));
					if (leftEdgeIndex >= newEdge.size()) {
						return;
					}
					p = (Point) newEdge.get(leftEdgeIndex + 1);
					rightEdgeIndex =
							rightChild.assignPoint(p, leftEdgeIndex + 1);
					rightChild.addDisallowedEdges();
					rightChild.addRequiredEdges();
					rightChild.addDisallowedEdges();
					rightChild.addRequiredEdges();
					rightChild.computeLowerBound();
					if (rightChild.lowerBound() > 2 * bestTour) {
						rightChild = null;
						numberPrunedNodes++;
					}
					if (leftChild != null && rightChild == null) {
						branchAndBound(leftChild, leftEdgeIndex);
					} else if (leftChild == null &&
							rightChild != null) {
						branchAndBound(rightChild, rightEdgeIndex);
					} else if (leftChild != null &&
							rightChild != null &&
							leftChild.lowerBound() <= rightChild.
							lowerBound()) {
						if (leftChild.lowerBound() < 2 * bestTour) {
							branchAndBound(leftChild,
									leftEdgeIndex);
						} else {
							leftChild = null;
							numberPrunedNodes++;
						}
						if (rightChild.lowerBound() < 2 * bestTour) {
							branchAndBound(rightChild,
									rightEdgeIndex);
						} else {
							rightChild = null;
							numberPrunedNodes++;
						}
					} else if (rightChild != null) {
						if (rightChild.lowerBound() < 2 * bestTour) {
							branchAndBound(rightChild,
									rightEdgeIndex);
						} else {
							rightChild = null;
							numberPrunedNodes++;
						}
						if (leftChild.lowerBound() < 2 * bestTour) {
							branchAndBound(leftChild,
									leftEdgeIndex);
						} else {
							leftChild = null;
							numberPrunedNodes++;
						}
					}
				}
			}
		}
	}

	private byte [][] copy (byte [][] constraint) {
		byte [][] toReturn = new byte[numRows + 1][numCols + 1];
		for (int row = 1; row <= numRows; row++) {
			for (int col = 1; col <= numCols; col++) {
				toReturn[row][col] = constraint[row][col];
			}
		}
		return toReturn;
	}

}	// end - TSP class
