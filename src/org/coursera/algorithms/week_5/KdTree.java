package org.coursera.algorithms.week_5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, RectHV rect, Node lb, Node rt) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
        }
    }

    private Node node;
    private int size;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        node = insertPoint(node, p, true);
    }

    private Node insertPoint(Node n, Point2D point, Boolean isVertical){
        if(n == null){
            size ++;
            return new Node(point, new RectHV(point.x(), point.y(), point.x(), point.y()), null, null);
        }else if(!n.p.equals(point)){
            if(isVertical){
                if(n.p.x() > point.x()){
                    n.lb = insertPoint(n.lb, point, !isVertical);
                }else{
                    n.rt = insertPoint(n.rt, point, !isVertical);
                }
            }else {
                if (n.p.y() > point.y()) {
                    n.lb = insertPoint(n.lb, point, !isVertical);
                } else {
                    n.rt = insertPoint(n.rt, point, !isVertical);
                }
            }
        }
        return n;
    }

    private Node searchPoint(Node n, Point2D point) {
        Node searchedNode = null;
        if(n.p.equals(point)){
            searchedNode = n;
        }
            if (n.p.x() > point.x()) {
                searchPoint(n.lb, point);
            } else {
               searchPoint(n.rt, point);
            }
        return searchedNode;
    }

    public boolean contains(Point2D p) {
        return searchPoint(node, p) == null;
    }

    public void draw() {
        draw(node);
    }

    private void draw(Node node) {
        if(node == null){
            return;
        }
        node.p.draw();
        draw(node.rt);
        draw(node.lb);
    }

    private List<Point2D> pointsInsideRect(Node n, RectHV rect, List<Point2D> pointsInside){
        if(node == null){
            return pointsInside;
        }
        if(rect.contains(n.p)){
            pointsInside.add(n.p);
        }
        pointsInsideRect(n.lb, rect, pointsInside);
        pointsInsideRect(n.rt, rect, pointsInside);
        return pointsInside;
    }
    public Iterable<Point2D> range(RectHV rect) {
        return pointsInsideRect(node, rect, new ArrayList<>());
    }

//    public Point2D nearest(Point2D p) {
//
//    }

}
