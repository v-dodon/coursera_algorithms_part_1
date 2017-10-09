package org.coursera.algorithms.week_5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private Set<Point2D> tree;
    public PointSET() {
        tree = new TreeSet<>();
    }                               // construct an empty set of points

    public boolean isEmpty() {
        return tree.isEmpty();
    }                      // is the set empty?

    public int size() {
        return tree.size();
    }                         // number of points in the set

    public void insert(Point2D p) {
        verifyIfNull(p);
        tree.add(p);
    }              // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        verifyIfNull(p);
        return tree.contains(p);
    }            // does the set contain point p?

    public void draw() {
        for (Point2D point: tree) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            point.draw();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
        }
    }                         // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        verifyIfNull(rect);
        List<Point2D> points = new ArrayList<>();
        for (Point2D point: tree) {
            if(rect.contains(point)){
                points.add(point);
            }
        }

        return points;
    }             // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        verifyIfNull(p);
        Point2D nearestPoint = null;
        if(tree.isEmpty()){
            return null;
        }
        for (Point2D point: tree) {
            double distance = -1;
            if(!p.equals(point)){
                if (distance == -1 || distance < p.distanceTo(point)){
                    distance = p.distanceTo(point);
                    nearestPoint = point;
                }
            }
        }
        return nearestPoint;
    }             // a nearest neighbor in the set to point p; null if the set is empty

    private void verifyIfNull(Object object){
        if(object == null){
            throw new IllegalArgumentException();
        }
    }
    public static void main(String[] args){

    }                  // unit testing of the methods (optional)
}
