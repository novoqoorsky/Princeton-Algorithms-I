import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
	
	 private final ArrayList<LineSegment> segments = new ArrayList<>();
	
	 public FastCollinearPoints(Point[] points) {	// finds all line segments containing 4 or more points
		 if (invalidArray(points)) {
			 throw new IllegalArgumentException();
		 }
		 Point[] pointsCopy = points.clone();
	     	 for (int i = 0; i < pointsCopy.length - 3; i++) {
	    	 	Arrays.sort(pointsCopy);
	    	 	Arrays.sort(pointsCopy, pointsCopy[i].slopeOrder());
	         	for (int j = 0, k = 1, m = 2; m < pointsCopy.length; m++) {
	        		while (m < pointsCopy.length && areCollinear(pointsCopy[j], pointsCopy[k], pointsCopy[m])) {
	        			m++;
	             		}
	             		if (m - k >= 3 && pointsCopy[j].compareTo(pointsCopy[k]) < 0) {
	            			segments.add(new LineSegment(pointsCopy[j], pointsCopy[m - 1]));
	             		}
	             		k = m;
	         	}
	     	 }
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
	 
	 private boolean areCollinear(Point p, Point q, Point r) {
		 if (p.slopeTo(q) == p.slopeTo(r)) {
			 return true;
		 }
		 return false;
	 }
	
	 public int numberOfSegments() {	// the number of line segments
		 return segments.size();
	 }
	 
	 public LineSegment[] segments() {	// the line segments
		 return segments.toArray(new LineSegment[numberOfSegments()]);
	 }
	
}
