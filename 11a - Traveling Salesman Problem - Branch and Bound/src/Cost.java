
public class Cost {

	private int numRows;
	private int numCols;

	private double costMatrix[][];

	// Constructor
	public Cost(int rows, int cols){

		// Set the private members
		numRows = rows;
		numCols = cols;

		// Check for valid size
		if (numRows > 1 && numCols > 1) {
			// Generate the cost matrix
			costMatrix = new double[rows+1][cols+1];
		}

	}

	// short value
	public void assignCost(short value, int row, int col) {

		// Check for valid indices
		if (row <= numRows && col <= numCols) {
			// Set the specified value
			costMatrix[row][col] = value;
		}

	}

	// double value
	public void assignCost(double value, int row, int col) {

		// Check for valid indices
		if (row <= numRows && col <= numCols) {
			// Set the specified value
			costMatrix[row][col] = value;
		}

	}

	// Return cost
	public double cost(int row, int col) {

		// Check for valid indices
		if (row <= numRows && col < numCols) {
			// Set the specified value
			return costMatrix[row][col];
		}

		// Return 0
		return 0;

	}

}	// end - Cost class
