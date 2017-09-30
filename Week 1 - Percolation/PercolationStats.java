import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

	private final double[] thresholds;
	private final int size;
	private final int tests;
	private final double meanValue;
	private final double devValue;
	
	public PercolationStats(int n, int trials) {	// perform trials independent experiments on an n-by-n grid
		if (n <= 0 || trials <= 0) {
			throw new java.lang.IllegalArgumentException();
		}
		size = n;
		tests = trials;
		thresholds = new double[tests];
		for (int i = 0; i < tests; i++) {
			thresholds[i] = singleTrial();
		}
		meanValue = StdStats.mean(thresholds);
		devValue = StdStats.stddev(thresholds);
	}
	
	public double mean() {		// sample mean of percolation threshold
		return meanValue;
	}
	
	public double stddev() {	// sample standard deviation of percolation threshold
		return devValue;
	}
	
	public double confidenceLo() {	// low endpoint of 95% confidence interval
		return meanValue - 1.96*devValue/Math.sqrt(tests);
	}
	
	public double confidenceHi() {	// high endpoint of 95% confidence interval
		return meanValue + 1.96*devValue/Math.sqrt(tests);
	}
	
	private double singleTrial() {	// make system percolate and count threshold
		Percolation p = new Percolation(size);
		while (!p.percolates()) {
			int row = StdRandom.uniform(1, size+1);
			int col = StdRandom.uniform(1, size+1);
			p.open(row, col);
		}
		return (double) p.numberOfOpenSites() / (size*size);
	}
	
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);
		PercolationStats ps = new PercolationStats(n, t);
		StdOut.println("mean = " + ps.mean());
		StdOut.println("stddev = " + ps.stddev());
		StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
	}

}
