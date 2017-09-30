import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
	
	private final TreeSet<Point2D> points;
	
	public PointSET() {		// construct an empty set of points
		points = new TreeSet<>();
	}
	
	public boolean isEmpty() {	// is the set empty?
		return points.isEmpty();
	}
	
	public int size() {		// number of points in the set
		return points.size();
	}
	
	public void insert(Point2D p) {		// add the point to the set (if it is not already in the set)
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		points.add(p);
	}
	
	public boolean contains(Point2D p) {		// does the set contain point p?
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		return points.contains(p);
	}
	
	public void draw() {	// draw all points to standard draw
		for (Point2D p : points) {
			p.draw();
		}
	}
	
	public Iterable<Point2D> range(RectHV rect) {	// all points that are inside the rectangle (or on the boundary)
		if (rect == null) {
			throw new java.lang.IllegalArgumentException();
		}
		List<Point2D> result = new ArrayList<>();
		for (Point2D p : points) {
			if (rect.contains(p)) {
				result.add(p);
			}
		}
		return result;
	}
	
	public Point2D nearest(Point2D p) {		// a nearest neighbor in the set to point p; null if the set is empty
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		if (points.isEmpty()) {
			return null;
		}
		Point2D nearest = null;
		for (Point2D p2 : points) {
			if (nearest == null || p2.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
				nearest = p2;
			}
		}
		return nearest;
	}
	
}
