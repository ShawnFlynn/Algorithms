
public class NodeEntry {

	// The node value
	public int value;

	// The cluster name
	public int clusterId;

	// Next node index
	public int nextNode;

	// Cluster size
	public int clusterSize;

	// Cluster tail index
	public int clusterTail;

	// Constructors

	// Blank
	NodeEntry() {

		// Initialize with default values
		value = -1;
		clusterId = -1;
		nextNode = -1;
		clusterSize = 0;
		clusterTail = -1;
	}

	NodeEntry(int nVal,
			  int cId,
			  int xId,
			  int cSize,
			  int cTail) {

		// Initialize
		value = nVal;
		clusterId = cId;
		nextNode = xId;
		clusterSize = cSize;
		clusterTail = cTail;
	}
}
