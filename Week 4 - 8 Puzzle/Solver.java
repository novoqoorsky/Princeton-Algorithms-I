import edu.princeton.cs.algs4.MinPQ;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Solver {
	
	private boolean solvable;
	private Node wanted;
	
	private class Node implements Comparable<Node> {
		Board board;
		Node previous;
		int moves;
		
		public Node(Board b, Node p) {
			board = b;
			if (p != null) {
				moves = p.moves + 1;
				previous = p;
			} else {
				moves = 0;
			}
		}
		
		public int compareTo(Node n) {
			return (board.manhattan() + moves) - (n.board.manhattan() + n.moves);
		}
	}
	
	public Solver(Board initial) {	// find a solution to the initial board (using the A* algorithm)
		if (initial == null) {
			throw new java.lang.IllegalArgumentException();
		}
		MinPQ<Node> pq = new MinPQ<>();
		MinPQ<Node> twinPQ = new MinPQ<>();
		solvable = true;
		wanted = new Node(initial, null);
		Node twinNode = new Node(initial.twin(), null);
		
		while (true) {
			if (wanted.board.isGoal()) {
				break;
			}
			if (twinNode.board.isGoal()) {
				solvable = false;
				break;
			}
			Iterable<Board> b1 = wanted.board.neighbors();
			Iterable<Board> b2 = twinNode.board.neighbors();
			for (Board b : b1) {
				if (wanted.previous == null || !wanted.previous.board.equals(b)) {
					pq.insert(new Node(b, wanted));
				}
			}
			wanted = pq.delMin();
			for (Board b : b2) {
				if (twinNode.previous == null || !twinNode.previous.board.equals(b)) {
					twinPQ.insert(new Node(b, twinNode));
				}
			}
			twinNode = twinPQ.delMin();
		}
	}
	
	public boolean isSolvable() {	// is the initial board solvable?
		return solvable;
	}
	
	public int moves() {	// min number of moves to solve initial board; -1 if unsolvable
		if (solvable) {
			return wanted.moves;
		}
		return -1;
	}
	
	public Iterable<Board> solution() {		// sequence of boards in a shortest solution; null if unsolvable
		if (!solvable) {
			return null;
		}
		List<Board> solution = new ArrayList<>();
		Node temp = wanted;
		while (temp != null) {
			solution.add(temp.board);
			temp = temp.previous;
		}
		Collections.reverse(solution);
		return solution;
	}
	
}
