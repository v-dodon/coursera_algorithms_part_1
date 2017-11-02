package org.coursera.algorithms.week_5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private Node node;
    private int size;
    private Node searchedNode;
    private Point2D nearestPoint;

    private static class Node {
        private final Point2D p;      // the point
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p) {
            this.p = p;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        node = insertPoint(node, p, true);
    }

    private Node insertPoint(Node n, Point2D point, boolean isVertical) {
        if (n == null) {
            size++;
            return new Node(point);
        } else if (!n.p.equals(point)) {
            if (isVertical) {
                if (n.p.x() > point.x()) {
                    n.lb = insertPoint(n.lb, point, !isVertical);
                } else {
                    n.rt = insertPoint(n.rt, point, !isVertical);
                }
            } else {
                if (n.p.y() > point.y()) {
                    n.lb = insertPoint(n.lb, point, !isVertical);
                } else {
                    n.rt = insertPoint(n.rt, point, !isVertical);
                }
            }
        }
        return n;
    }

    private void searchPoint(Node n, Point2D point) {
        if (n == null) {
            return;
        }
        if (n.p.equals(point)) {
            searchedNode = n;
            return;
        }
        if (n.p.x() > point.x()) {
            searchPoint(n.lb, point);
        } else {
            searchPoint(n.rt, point);
        }
    }

    public boolean contains(Point2D p) {
        searchPoint(node, p);
        return searchedNode == null;
    }

    public void draw() {
        draw(node);
    }

    private void draw(Node n) {
        if (n == null) {
            return;
        }
        n.p.draw();
        draw(n.rt);
        draw(n.lb);
    }

    private void pointsInsideRect(Node n, RectHV rect, List<Point2D> pointsInside) {
        if (n == null) {
            return;
        }
        if (rect.contains(n.p)) {
            pointsInside.add(n.p);
        }
        pointsInsideRect(n.lb, rect, pointsInside);
        pointsInsideRect(n.rt, rect, pointsInside);
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> pointsInside = new ArrayList<>();
        pointsInsideRect(node, rect, pointsInside);

        return pointsInside;
    }

    public Point2D nearest(Point2D p) {
        findNearest(node, p, node.p);
        return nearestPoint;
    }

    private void findNearest(Node n, Point2D p, Point2D nearestPointToSelected) {
        if (n == null) {
            return;
        }
        if (n.rt != null && n.rt.p.distanceSquaredTo(p) < nearestPointToSelected.distanceSquaredTo(p)) {
            nearestPoint = n.rt.p;
            findNearest(n.rt, p, n.rt.p);
        } else if (n.lb != null && n.lb.p.distanceSquaredTo(p) < nearestPointToSelected.distanceSquaredTo(p)) {
            nearestPoint = n.lb.p;
            findNearest(n.lb, p, n.lb.p);
        }
    }
}