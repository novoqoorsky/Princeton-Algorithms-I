import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private final WeightedQuickUnionUF unionFind;
	private final int size;
	private boolean[][] opened;
	private int openedCounter;
	
	public Percolation(int n) {	// create n-by-n grid, with all sites blocked
		if (n <= 0) {
			throw new java.lang.IllegalArgumentException();
		}
		size = n;
		openedCounter = 0;
		opened = new boolean[size][size];
		unionFind = new WeightedQuickUnionUF(size*size + 2);
	}
	
	private void checkIndexes(int row, int col) {
		if (row > size || col > size || row <= 0 || col <= 0) {
			throw new java.lang.IllegalArgumentException();
		}
	}
	
	public boolean isOpen(int row, int col) {	// is site (row, col) open?
		checkIndexes(row, col);
		return opened[row-1][col-1];
	}
	
	public boolean isFull(int row, int col) {	// is site (row, col) full?
		checkIndexes(row, col);
		return unionFind.connected(0, (row-1)*size + col);
	}
	
	private void connect(int row, int col) {	// connect two sites
		unionFind.union(row, col);
	}
	
	public void open(int row, int col) {		// open site (row, col) if it is not open already
		if (!isOpen(row, col)) {
			opened[row-1][col-1] = true;
			openedCounter++;
		}
		int unionIndex = (row-1)*size + col;
		if (row == size) {		// connect to the last row union representation
			connect(unionIndex, size*size + 1);
		}
		if (row == 1) {		// connect to the first row union representation
			connect(unionIndex, 0);
		}
		if (row > 1 && isOpen(row-1, col)) {		// connect to top site
			connect(unionIndex, (row-2)*size + col);
		}
		if (col > 1 && isOpen(row, col-1)) {		// connect to left site
			connect(unionIndex, (row-1)*size + col-1);
		}
		if (row < size && isOpen(row+1, col)) {		// connect to bottom site
			connect(unionIndex, row*size + col);
		}
		if (col < size && isOpen(row, col+1)) {		// connect to right site
			connect(unionIndex, (row-1)*size + col+1);
		}
	}
	
	public int numberOfOpenSites() {	// number of open sites
		return openedCounter;
	}
	
	public boolean percolates() {	// does the system percolate?
		return unionFind.connected(size*size+1, 0);
	}
	
}
