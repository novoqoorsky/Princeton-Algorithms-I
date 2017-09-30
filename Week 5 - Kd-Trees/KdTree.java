import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;

public class KdTree {
	
	private Node root;
	private int size;
	
	private class Node {
		Point2D point;
		RectHV rect;
		Node left;
		Node right;
		
		public Node(Point2D p, RectHV r) {
			point = p;
			rect = r;
		}
	}
	
	public KdTree() {	// construct an empty set of points
		root = null;
		size = 0;
	}
	
	public boolean isEmpty() {	// is the set empty?
		return (size == 0);
	}
	
	public int size() {	// number of points in the set
		return size;
	}
	
	public void insert(Point2D p) {		// add the point to the set (if it is not already in the set)
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		root = insert(root, true, p, new RectHV(0.0, 0.0, 1.0, 1.0));
	}
	
	public boolean contains(Point2D p) {		// does the set contain point p?
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		return contains(root, p, true);
	}
	
	public void draw() {	// draw all points to standard draw
		draw(root, true);
	}
	
	public Iterable<Point2D> range(RectHV rect) {	// all points that are inside the rectangle (or on the boundary)
		if (rect == null) {
			throw new java.lang.IllegalArgumentException();
		}
		Queue<Point2D> points = new Queue<>();
		range(root, rect, points);
		return points;
	}
	
	public Point2D nearest(Point2D p) {		// a nearest neighbor in the set to point p; null if the set is empty
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		return nearest(root, p, Double.POSITIVE_INFINITY);
	}
	
	private Node insert(Node n, boolean vertical, Point2D p, RectHV r) {
		if (n == null) {
			size++;
			return new Node(p, r);
		}
		if (n.point.equals(p)) {
			return n;
		}
		if (vertical) {
			if ((p.x() - n.point.x()) < 0) {
				n.left = insert(n.left, false, p, new RectHV(0.0, 0.0, n.point.x(), 1.0));
			} else {
				n.right = insert(n.right, false, p, new RectHV(n.point.x(), 0.0, 1.0, 1.0));
			}
		} else {
			if (p.y() - n.point.y() < 0) {
				n.left = insert(n.left, true, p, new RectHV(0.0, 0.0, 1.0, n.point.y()));
			} else {
				n.right = insert(n.right, true, p, new RectHV(0.0, n.point.y(), 1.0, 1.0));
			}
		}
		return n;
	}
	
	private boolean contains(Node n, Point2D p, boolean vertical) {
		if (n == null) {
			return false;
		}
		if (n.point.equals(p)) {
			return true;
		}
		if (vertical) {
			if (p.x() - n.point.x() < 0) {
				return contains(n.left, p, false);
			} else {
				return contains(n.right, p, false);
			}
		} else {
			if (p.y() - n.point.y() < 0) {
				return contains(n.left, p, true);
			} else {
				return contains(n.right, p, true);
			}
		}
	}
	
	private void draw(Node n, boolean vertical) {
		if (n == null) {
			throw new java.lang.IllegalArgumentException();
		}
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		n.point.draw();
		if (vertical) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(n.point.x(), n.rect.ymin(), n.point.x(), n.rect.ymax());
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(n.rect.xmin(), n.point.y(), n.rect.xmax(), n.point.y());
		}
		draw(n.left, !vertical);
		draw(n.right, !vertical);
	}
	
	private void range(Node n, RectHV rect, Queue<Point2D> points) {
		if (n == null) {
			return;
		}
		if (!rect.intersects(n.rect)) {
			return;
		}
		if (rect.contains(n.point)) {
			points.enqueue(n.point);
		}
		if (n.left != null) {
			range(n.left, rect, points);
		}
		if (n.right != null) {
			range(n.right, rect, points);
		}
	}
	
	private Point2D nearest(Node n, Point2D p, double currentNearest) {
		if (n == null) {
			return null;
		}
		if (n.rect.distanceSquaredTo(p) >= currentNearest) {
			return null;
		}
		Point2D nearest = null;
		double current = currentNearest;
		double distance = p.distanceSquaredTo(n.point);
		if (distance < currentNearest) {
			nearest = n.point;
			current = distance;
		}
		Node closer = n.left, further = n.right;
		if (closer != null && further != null) {
			if (closer.rect.distanceSquaredTo(p) > further.rect.distanceSquaredTo(p)) {
				closer = n.right;
				further = n.left;
			}
		}
		Point2D next = nearest(closer, p, current);
		if (next != null) {
			distance = p.distanceSquaredTo(next);
			if (distance < current) {
				nearest = next;
				current = distance;
			}
		}
		next = nearest(further, p, current);
		if (next != null) {
			distance = p.distanceSquaredTo(next);
			if (distance < current) {
				nearest = next;
				current = distance;
			}
		}
		return nearest;
	}
	
}
