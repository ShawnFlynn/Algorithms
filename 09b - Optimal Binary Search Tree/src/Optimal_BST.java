
public class Optimal_BST {

	public static void main(String[] args) {

/*
		float[] T = {(float) 0.05,
					 (float) 0.4,
					 (float) 0.08,
					 (float) 0.04,
					 (float) 0.1,
					 (float) 0.1,
					 (float) 0.23};
*/
		// Final #2
		float[] T = {(float) 0.2,
					 (float) 0.05,
					 (float) 0.17,
					 (float) 0.1,
					 (float) 0.2,
					 (float) 0.03,
					 (float) 0.25};

		int sizeTree = T.length;

		// 2-D array
		float A[][] = new float[sizeTree][sizeTree];

		// Loop through all of the nodes
		for (int s=0; s<sizeTree; s++) {

			// Loop through subset sizes
			for (int i=0; i<sizeTree; i++) {

				// Skip invalid nodes
				if (i+s >= sizeTree)
					continue;

				float split1;
				float split2;
				float minSplit = 9999;
				float pkSum = 0;

				// Scan each substructure
				for (int r=i; r<=(i+s); r++) {

					pkSum += T[r];

					if (r-1 < i)
						split1 = 0;
					else
						split1 = A[i][r-1];

					if (r+1 > s+i)
						split2 = 0;
					else 
						split2 = A[r+1][i+s];

					if (split1+split2 < minSplit)
						minSplit = split1+split2;
				}
				A[i][i+s] = minSplit + pkSum;
			}
		}

		// Print out the minimum-possible average search
		System.out.println("Minimum-possible average search time = "
							+ A[0][sizeTree-1]);

	}	// end - Main()

}	// end - Optimal_BST class

