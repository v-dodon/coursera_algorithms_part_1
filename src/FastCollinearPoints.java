import javax.sound.sampled.Line;
import java.util.*;

public class FastCollinearPoints {
    private List<LineSegment> lineSegments = new LinkedList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        checkForNulls(points);
        checkForDuplicates(points);
        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        for (Point point : points) {
            Arrays.sort(sortedPoints, point.slopeOrder());
            List<Point> collinear = new LinkedList<>();
            for (int i = 1; i < points.length; i++) {
                if (point.compareTo(points[i]) == 0) {
                    continue;
                }
                if (point.slopeTo(points[i - 1]) == point.slopeTo(points[i])) {
                    collinear.add(points[i]);
                }else{
                    if(collinear.size() >= 3){
                        collinear.add(point);

                    }
                }
            }
        }
    }     // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return lineSegments.size();
    }        // the number of line segments

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }                // the line segments

    private void checkForDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private void checkForNulls(Point[] points) {
        for (Point point : points) {
            if (point == null) {
                throw new NullPointerException();
            }
        }
    }
}