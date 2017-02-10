import java.util.ArrayList;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> lineSegments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        checkForNulls(points);
        checkForDuplicates(points);
        for (int p = 0; p < points.length - 3; p++) {
            for (int q = 1; q < points.length - 2; q++) {
                for (int r = 2; r < points.length - 1; r++) {
                    for (int s = 3; s < points.length; s++) {
                        if (points[s].slopeTo(points[p]) == points[s].slopeTo(points[q]) && points[s].slopeTo(
                                points[p]) == points[s].slopeTo(points[r]) && points[s].slopeTo(
                                points[q]) == points[s].slopeTo(points[r])) {
                            lineSegments.add(new LineSegment(points[s], points[p]));
                        }
                    }
                }
            }
        }

    }    // finds all line segments containing 4 points

    private void checkForDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = 1; j < points.length; j++) {
                if(points[i].compareTo(points[j]) == 0){
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private void checkForNulls(Point[] points) {
        for (Point point: points) {
            if (point == null) {
                throw new NullPointerException();
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }       // the number of line segments

    public LineSegment[] segments() {
        return (LineSegment[]) lineSegments.toArray();
    }          // the line segments
}