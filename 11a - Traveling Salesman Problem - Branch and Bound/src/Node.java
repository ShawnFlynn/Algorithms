

/**
* The information contained in a tree used for solving TSP using
* branch and bound.
*/

import java.util.*;
import java.awt.*;

public class Node {

	// Fields
	private int lowerBound;
	private int numRows, numCols;
	private byte [][] constraint;

	private double MAXIMUM = Double.MAX_VALUE;

	// -1 indicates no edge from row to column allowed,
	// 1 indicates that edge from row to column required,
	// 0 indicates that edge from row to column allowed
//	private short [] nodeCosts; // Used to compute smallest and
	private double [] nodeCosts; // Used to compute smallest and
	// nexSmallest
//	private int edges; // Used by isTour query
//	private int tourCost; // Used by isTour query
	private double tourCost; // Used by isTour query
	private byte [] trip;
	private String nodeAsString; // Used by isTour query
//	private boolean isLoop = false;
	static BitSet b; // Used by isCycle and initialized in TSPUI

	// Constructor
	public Node (int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
//		nodeCosts = new short[numCols + 1]; // Natural indexing
		nodeCosts = new double[numCols + 1]; // Natural indexing
		constraint = new byte[numRows + 1][numCols + 1]; // Natural indexing
		trip = new byte[numRows + 1];
	}

	// Commands
	public void assignConstraint (byte value, int row, int col) {
		constraint[row][col] = value;
		constraint[col][row] = value;
	}

	public int assignPoint (Point p, int edgeIndex) {
		// Advance edgeIndex until edge that is unconstrained is found
		Point pt = p;
		while (edgeIndex < TSP.newEdge.size() &&
			constraint[(int) Math.abs(pt.getX())][(int)Math.abs(pt.getY())] != 0) {
				edgeIndex++;
				if (edgeIndex < TSP.newEdge.size()) {
					pt = (Point) TSP.newEdge.get(edgeIndex);
				}
			}
			if (edgeIndex < TSP.newEdge.size()) {
				if (pt.getX() < 0) {
					assignConstraint((byte) -1, (int) Math.abs(pt.getX()),
									 (int) Math.abs(pt.getY()));
				} else {
					assignConstraint((byte) 1, (int) pt.getX(),
									 (int) pt.getY());
				}
			}
		return edgeIndex;
	}

	public void setConstraint (byte [][] constraint) {
		this.constraint = constraint;
	}

	public void addDisallowedEdges () {
		for (int row = 1; row <= numRows; row++) {
			// Count the number of paths from row.
			// If the count exceeds one then disallow all other paths
			// from row
			int count = 0;
			for (int col = 1; col <= numCols; col++) {
				if (row != col && constraint[row][col] == 1) {
					count++;
				}
			}
			if (count >= 2) {
				for (int col = 1; col <= numCols; col++) {
					if (row != col && constraint[row][col] == 0) {
						constraint[row][col] = -1;
						constraint[col][row] = -1;
					}
				}
			}
		}
		// Check to see whether the presence of a col causes a premature
		// circuit
		for (int row = 1; row <= numRows; row++) {
			for (int col = 1; col <= numCols; col++) {
				if (row != col && isCycle(row, col) &&
						numCities(b) < numRows) {
					if (constraint[row][col] == 0) {
						constraint[row][col] = -1;
						constraint[col][row] = -1;
					}
				}
			}
		}
	}

	public void addRequiredEdges () {
		for (int row = 1; row <= numRows; row++) {
			// Count the number of paths excluded from row
			// If the count equals numCols - 3, include all remaining
			// paths
			int count = 0;
			for (int col = 1; col <= numCols; col++) {
				if (row != col && constraint[row][col] == -1) {
					count++;
				}
			}
			if (count >= numRows - 3) {
				for (int col = 1; col <= numCols; col++) {
					if (row != col && constraint[row][col] == 0) {
						constraint[row][col] = 1;
						constraint[col][row] = 1;
					}
				}
			}
		}
	}

	public void computeLowerBound () {
		int lowB = 0;
		for (int row = 1; row <= numRows; row++) {
			for (int col = 1; col <= numCols; col++) {
				nodeCosts[col] = TSP.c.cost(row, col);
			}
//			nodeCosts[row] = Short.MAX_VALUE;
			nodeCosts[row] = MAXIMUM;
			// Eliminate edges that are not allowed
			for (int col = 1; col <= numCols; col++) {
				if (constraint[row][col] == -1) {
					 // Taken out of contention
//					nodeCosts[col] = Short.MAX_VALUE;
					nodeCosts[col] = MAXIMUM;
				}
			}
//			int [] required = new int[numCols - 1]; // Natural indexing
			double [] required = new double[numCols - 1]; // Natural indexing
			int numRequired = 0;
			// Determine whether an edge is required
			for (int col = 1; col <= numCols; col++) {
				if (constraint[row][col] == 1) {
					numRequired++;
					required[numRequired] = nodeCosts[col];
					 // Taken out of contention
//					nodeCosts[col] = Short.MAX_VALUE; // Taken out of
					nodeCosts[col] = MAXIMUM;
				}
			}
//			int smallest = 0, nextSmallest = 0;
			double smallest = 0, nextSmallest = 0;
			if (numRequired == 0) {
				smallest = smallest();
				nextSmallest = nextSmallest();
			} else if (numRequired == 1) {
				smallest = required[1];
				nextSmallest = smallest();
			} else if (numRequired == 2) {
				smallest = required[1];
				nextSmallest = required[2];
			}
//			if (smallest == Short.MAX_VALUE) {
			if (smallest == MAXIMUM) {
				smallest = 0;
			}
//			if (nextSmallest == Short.MAX_VALUE) {
			if (nextSmallest == MAXIMUM) {
				nextSmallest = 0;
			}
			lowB += smallest + nextSmallest;
		}
		lowerBound = lowB; // This is twice the actual lower bound
	}

	public void setTour () {
		byte path = 0;
		for (int col = 2; col <= numCols; col++) {
			if (constraint[1][col] == 1) {
				path = (byte) col;
				break;
			}
		}
		tourCost = TSP.c.cost(1, path);
		trip[1] = path;
		int row = 1;
		int col = path;
		int from = row;
		byte pos = path;
		nodeAsString = "" + row + " " + col;
		while (pos != row) {
			for (byte column = 1; column <= numCols; column++) {
				if (column != from && constraint[pos][column] == 1) {
					from = pos;
					pos = column;
					nodeAsString += " " + pos;
					tourCost += TSP.c.cost(from, pos);
					trip[from] = pos;
					break;
				}
			}
		}
	}

	// Queries
//	public int tourCost () {
	public double tourCost () {
		return tourCost;
	}

	public byte [] trip () {
		return trip;
	}

	public byte constraint (int row, int col) {
		return constraint[row][col];
	}

	public byte [][] constraint () {
		return constraint;
	}

	public int lowerBound () {
		return lowerBound;
	}

	public boolean isTour () {
		// Determine path from 1
		int path = 0;
		for (int col = 2; col <= numCols; col++) {
			if (constraint[1][col] == 1) {
				path = col;
				break;
			}
		}
		if (path > 0) {
			boolean cycle = isCycle(1, path);
			return cycle && numCities(b) == numRows;
		} else {
			return false;
		}
	}

	public boolean isCycle (int row, int col) {
		// b = new BitSet(numRows + 1);
		b = new BitSet(numRows + 1);
		for (int i = 0; i < numRows + 1; i++) {
			b.clear(i);
		}
		b.set(row);
		b.set(col);
		int from = row;
		int pos = col;
		int edges = 1;
		boolean quit = false;
		while (pos != row && edges <= numCols && !quit) {
			quit = true;
			for (int column = 1; column <= numCols; column++) {
				if (column != from && constraint[pos][column] == 1) {
					edges++;
					from = pos;
					pos = column;
					b.set(pos);
					quit = false;
					break;
				}
			}
		}
		return pos == row || edges >= numCols;
	}

	public String tour () {
		return nodeAsString;
	}

	public String toString () {
		// String representation of constraint matrix
		String returnString = "";
		for (int row = 1; row <= numRows; row++) {
			for (int col = row + 1; col <= numCols; col++) {
				if (constraint[row][col] == 1) {
					returnString += "" + row + col + " ";
				} else if (constraint[row][col] == -1) {
					returnString += "*" + row + col + " ";
				}
			}
		}
		return returnString;
	}

	// Internal methods
//	private int smallest () {
	private double smallest () {
//		int s = nodeCosts[1];
		double s = nodeCosts[1];
		int index = 1;
		for (int i = 2; i <= numCols; i++) {
			if (nodeCosts[i] < s) {
				s = nodeCosts[i];
				index = i;
			}
		}
//		short temp = nodeCosts[1];
		double temp = nodeCosts[1];
		nodeCosts[1] = nodeCosts[index];
		nodeCosts[index] = temp;
		return nodeCosts[1];
	}

//	private int nextSmallest () {
	private double nextSmallest () {
//		int ns = nodeCosts[2];
		double ns = nodeCosts[2];
		int index = 2;
		for (int i = 2; i <= numCols; i++) {
			if (nodeCosts[i] < ns) {
				ns = nodeCosts[i];
				index = i;
			}
		}
//		short temp = nodeCosts[2];
		double temp = nodeCosts[2];
		nodeCosts[2] = nodeCosts[index];
		nodeCosts[index] = temp;
		return nodeCosts[2];
	}

	private int numCities (BitSet b) {
		int num = 0;
		for (int i = 1; i <= numRows; i++) {
			if (b.get(i)) {
				num++;
			}
		}
		return num;
	}

}	// end - Node class
