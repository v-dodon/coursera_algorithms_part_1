public class FastCollinearPoints {

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        checkForNulls(points);
        checkForDuplicates(points);
    }     // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return 0;
    }        // the number of line segments

    public LineSegment[] segments() {
        return null;
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