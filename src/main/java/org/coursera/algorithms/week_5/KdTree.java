package org.coursera.algorithms.week_5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.List;

public class KdTree {

    private Node node;
    private int size;
    private Point2D nearestPoint;

    private static class Node {
        private final Point2D p;      // the point
        private RectHV rectHV;
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, RectHV rectHV) {
            this.p = p;
            this.rectHV = rectHV;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        node = insertPoint(node, p, true, 0.0, 0.0, 1.0, 1.0);
    }

    private Node insertPoint(Node n, Point2D point, boolean isVertical, double xmin, double ymin, double xmax, double ymax) {
        if (n == null) {
            size++;
            return new Node(point, new RectHV(xmin, ymin, xmax, ymax));

        } else if (!n.p.equals(point)) {
            if (isVertical) {
                if (n.p.x() > point.x()) {
                    n.lb = insertPoint(n.lb, point, !isVertical, xmin, ymin, point.x(), ymax);
                } else {
                    n.rt = insertPoint(n.rt, point, !isVertical, point.x(), ymin, xmax, ymax);
                }
            } else {
                if (n.p.y() > point.y()) {
                    n.lb = insertPoint(n.lb, point, !isVertical, xmin, ymin, xmax, point.y());
                } else {
                    n.rt = insertPoint(n.rt, point, !isVertical, xmin, point.y(), xmax, ymax);
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
        if (rect.intersects(n.rectHV)) {
            pointsInsideRect(n.lb, rect, pointsInside);
            pointsInsideRect(n.rt, rect, pointsInside);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> pointsInside = new LinkedList<>();
        pointsInsideRect(node, rect, pointsInside);

        return pointsInside;
    }

    public Point2D nearest(Point2D p) {
        nearestPoint = null;
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
        } else if (nearestPoint == null) {
            nearestPoint = nearestPointToSelected;
        }
    }
}