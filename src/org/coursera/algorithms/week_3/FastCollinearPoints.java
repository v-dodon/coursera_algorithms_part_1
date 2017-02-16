package org.coursera.algorithms.week_3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
                if (Double.doubleToLongBits(point.slopeTo(points[i - 1])) == Double.doubleToLongBits(
                        point.slopeTo(points[i]))) {
                    collinear.add(points[i]);
                } else {
                    if (collinear.size() >= 3) {
                        collinear.add(point);
                        addSegment(collinear);

                    }
                    collinear.clear();
                    collinear.add(sortedPoints[i]);
                }
            }
            if (collinear.size() >= 3) {
                collinear.add(point);
                addSegment(collinear);
            }
        }
    }     // finds all line segments containing 4 or more points

    private void addSegment(List<Point> collinearPoints) {
        Point[] points = collinearPoints.toArray(new Point[collinearPoints.size()]);
        LineSegment lineSegment = new LineSegment(points[0], points[points.length - 1]);
        if (!lineSegments.contains(lineSegment)) {
            lineSegments.add(lineSegment);
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }        // the number of line segments

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }                // the line segments

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
}