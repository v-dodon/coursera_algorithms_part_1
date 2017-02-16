package org.coursera.algorithms.week_3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {

    private List<LineSegment> lineSegments = new LinkedList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        checkForNulls(points);
        checkForDuplicates(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        for (int p = 0; p < pointsCopy.length - 3; p++) {
            for (int q = p + 1; q < pointsCopy.length - 2; q++) {
                for (int r = q + 1; r < pointsCopy.length - 1; r++) {
                    for (int s = r + 1; s < pointsCopy.length; s++) {
                        if (Double.doubleToLongBits(pointsCopy[p].slopeTo(pointsCopy[q])) == Double.doubleToLongBits(
                                pointsCopy[p].slopeTo(
                                        pointsCopy[r])) && Double.doubleToLongBits(pointsCopy[p].slopeTo(
                                pointsCopy[q])) == Double.doubleToLongBits((pointsCopy[p].slopeTo(pointsCopy[s])))) {
                            lineSegments.add(new LineSegment(pointsCopy[s], pointsCopy[p]));
                        }
                    }
                }
            }
        }

    }    // finds all line segments containing 4 points

    private void checkForDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
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

    public int numberOfSegments() {
        return lineSegments.size();
    }       // the number of line segments

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }          // the line segments
}