import java.util.ArrayList;
import java.util.List;

public class Board {
	
	private final int[][] blocks;
	private final int size;
	
	public Board(int[][] blocks) {	// construct a board from an n-by-n array of blocks
		size = blocks.length;
		this.blocks = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.blocks[i][j] = blocks[i][j];
			}
		}
	}
	
	public int dimension() {	// board dimension
		return size;
	}
	
	public int hamming() {	// number of blocks out of place
		int outs = 0;
		int expected = 1;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (blocks[i][j] != 0 && blocks[i][j] != expected) {
					outs++;
				}
				expected++;
			}
		}
		return outs;
	}
	
	public int manhattan() {	// sum of Manhattan distances between blocks and goal
		int sum = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (blocks[i][j] != 0) {
					sum += Math.abs(i - (blocks[i][j] - 1) / size);
					sum += Math.abs(j - (blocks[i][j] - 1) % size);
				}
			}
		}
		return sum;
	}
	
	public boolean isGoal() {	// is this board the goal board?
		return (hamming() == 0);
	}
	
	public Board twin() {	// a board that is obtained by exchanging any pair of blocks
		int[][] copy = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				copy[i][j] = blocks[i][j];
			}
		}
		if (copy[0][0] != 0 && copy[0][1] != 0) {
			int temp = copy[0][0];
			copy[0][0] = copy[0][1];
			copy[0][1] = temp;
		} else {
			int temp = copy[1][0];
			copy[1][0] = copy[1][1];
			copy[1][1] = temp;
		}
		return new Board(copy);
	}
	
	public boolean equals(Object y) {	// does this board equal y?
		if (y == null) {
			return false;
		}
		if (y.getClass() != this.getClass()) {
			return false;
		}
		if (y == this) {
			return true;
		}
		Board that = (Board) y;
		if (size != that.size) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (blocks[i][j] != that.blocks[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public Iterable<Board> neighbors() {	// all neighboring boards
		List<Board> neighbors = new ArrayList<>();
		int blankI = 0, blankJ = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (blocks[i][j] == 0) {
					blankI = i;
					blankJ = j;
					break;
				}
			}
		}
		if (blankI >= 1) {	// has left neighbor
			neighbors.add(new Board(neighbor(blankI, blankJ, 'L')));
		}
		if (blankI < size - 1) {	// has right neighbor
			neighbors.add(new Board(neighbor(blankI, blankJ, 'R')));
		}
		if (blankJ >= 1) {	// has top neighbor
			neighbors.add(new Board(neighbor(blankI, blankJ, 'T')));
		}
		if (blankJ < size - 1) {	// has bottom neighbor
			neighbors.add(new Board(neighbor(blankI, blankJ, 'B')));
		}
		return neighbors;
	}
	
	public String toString() {	// string representation of this board (in the output format specified below)
		StringBuilder output = new StringBuilder();
		output.append(size + "\n");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				output.append(String.format("%2d ", blocks[i][j]));
			}
			output.append("\n");
		}
		return output.toString();
	}
	
	private int[][] neighbor(int x, int y, char type) {
		int[][] copy = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				copy[i][j] = blocks[i][j];
			}
		}
		int temp;
		switch(type) {
		case 'L':
			temp = copy[x][y];
			copy[x][y] = copy[x-1][y];
			copy[x-1][y] = temp;
			break;
		case 'R':
			temp = copy[x][y];
			copy[x][y] = copy[x+1][y];
			copy[x+1][y] = temp;
			break;
		case 'T':
			temp = copy[x][y];
			copy[x][y] = copy[x][y-1];
			copy[x][y-1] = temp;
			break;
		case 'B':
			temp = copy[x][y];
			copy[x][y] = copy[x][y+1];
			copy[x][y+1] = temp;
			break;
		}
		return copy;
	}
	
}
