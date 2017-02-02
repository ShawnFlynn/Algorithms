
public class TimeInterval {

	private long start_time;
	private long end_time;

	public void startTiming() {

		// Get the start time
		start_time = System.currentTimeMillis();

	}

	public void endTiming() {

		// get the end time
		end_time = System.currentTimeMillis();

	}

	public double getElapsedTime() {

		// Return the elapsed time
		return (end_time - start_time) / 1000;

	}

}	// end - TimeInterval class
