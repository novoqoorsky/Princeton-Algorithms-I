import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
	
	private final LineSegment[] segments;
	
	public BruteCollinearPoints(Point[] points) {	// finds all line segments containing 4 points
		if (invalidArray(points)) {
			throw new IllegalArgumentException();
		}
		ArrayList<LineSegment> found = new ArrayList<>();
		Point[] pointsCopy = points.clone();
		Arrays.sort(pointsCopy);
		int len = points.length;
		for (int p = 0; p < len - 3; p++) {
			for (int q = p + 1; q < len - 2; q++) {
				for (int r = q + 1; r < len - 1; r++) {
					for (int s = r + 1; s < len; s++) {
						if (areCollinear(pointsCopy[p], pointsCopy[q], pointsCopy[r], pointsCopy[s])) {
							found.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
						}
					}
				}
			}
		}
        	segments = found.toArray(new LineSegment[found.size()]);
	}
	
	private boolean invalidArray(Point[] points) {
		if (points == null) {
			return true;
		}
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) {
				return true;
			}
			for (int j = 0; j < points.length; j++) {
				if (points[j] == null) {
					 return true;
				}
				if (j != i) {
					if (points[i].compareTo(points[j]) == 0) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean areCollinear(Point p, Point q, Point r, Point s) {
		if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {
			return true;
		}
		return false;
	}
	
	public int numberOfSegments() {		// the number of line segments
		return segments.length;
	}
	
	public LineSegment[] segments() {	// the line segments
		return segments.clone();
	}
	
}
