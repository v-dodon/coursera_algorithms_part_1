package org.coursera.algorithms.week_5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.List;

public class KdTree {

    private Node node;
    private int size;

    private static class Node {
        private final Point2D p;      // the point
        private final RectHV rectHV;
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private final boolean isVertical;

        public Node(Point2D p, RectHV rectHV, boolean isVertical) {
            this.p = p;
            this.rectHV = rectHV;
            this.isVertical = isVertical;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        verifyIfNull(p);
        double xmin = node == null ? p.x() : 0.0;
        node = insertPoint(node, p, true, xmin, 0.0, 1.0, 1.0);
    }

    private Node insertPoint(Node n, Point2D point, boolean isVertical, double xmin, double ymin, double xmax, double ymax) {
        if (n == null) {
            size++;
            return new Node(point, new RectHV(xmin, ymin, xmax, ymax), isVertical);
        } else if (!n.p.equals(point)) {
            if (n.isVertical) {
                if (n.p.x() > point.x()) {
                    n.lb = insertPoint(n.lb, point, !isVertical, xmin, point.y(), n.p.x(), point.y());
                } else {
                    n.rt = insertPoint(n.rt, point, !isVertical, n.p.x(), point.y(), xmax, ymax);
                }
            } else {
                if (n.p.y() > point.y()) {
                    n.lb = insertPoint(n.lb, point, !isVertical, point.x(), 0.0, xmax, n.p.y());
                } else {
                    n.rt = insertPoint(n.rt, point, !isVertical, point.x(), n.p.y(), xmax, 1.0);
                }
            }
        }
        return n;
    }

    private boolean contains(Node n, Point2D point, boolean isVertical) {
        if (n == null) {
            return false;
        }
        if (n.p.equals(point)) {
            return true;
        }
        if (isVertical) {
            if (point.x() < n.p.x()) {
                return contains(n.lb, point, !isVertical);
            } else {
                return contains(n.rt, point, !isVertical);
            }
        } else {
            if (point.y() < n.p.y()) {
                return contains(n.lb, point, !isVertical);
            } else {
                return contains(n.rt, point, !isVertical);
            }
        }
    }

    public boolean contains(Point2D p) {
        verifyIfNull(p);
        return contains(node, p, true);
    }

    public void draw() {
        draw(node);
    }

    private void draw(Node n) {
        if (n == null) {
            return;
        }
        n.p.draw();
        n.rectHV.draw();
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
        if (n.isVertical) {
            if (rect.xmax() < n.p.x()) {
                pointsInsideRect(n.lb, rect, pointsInside);
            } else {
                pointsInsideRect(n.rt, rect, pointsInside);
                if (rect.xmin() < n.p.x()) {
                    pointsInsideRect(n.lb, rect, pointsInside);
                }
            }
        } else {
            if (rect.ymax() < n.p.y()) {
                pointsInsideRect(n.lb, rect, pointsInside);
            } else {
                pointsInsideRect(n.rt, rect, pointsInside);
                if (rect.ymin() < n.p.y()) {
                    pointsInsideRect(n.lb, rect, pointsInside);
                }
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        verifyIfNull(rect);
        List<Point2D> pointsInside = new LinkedList<>();
        pointsInsideRect(node, rect, pointsInside);

        return pointsInside;
    }

    public Point2D nearest(Point2D p) {
        verifyIfNull(p);
        if (node == null) {
            return null;
        }
        return findNearest(node, p, node.p);
    }

    private Point2D findNearest(Node n, Point2D p, Point2D nearestPointToSelected) {
        if (n == null) {
            return nearestPointToSelected;
        }
        boolean shouldUpdateNearest = n.p.distanceSquaredTo(p) < nearestPointToSelected.distanceSquaredTo(p);
        Point2D nearest = shouldUpdateNearest ? n.p : nearestPointToSelected;
        if (n.rectHV.distanceSquaredTo(p) > nearestPointToSelected.distanceSquaredTo(p)) {
            return nearestPointToSelected;
        } else {
            nearest = findNearest(n.lb, p, nearest);
            nearest = findNearest(n.rt, p, nearest);
        }
        return nearest;
    }

    private void verifyIfNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }
}