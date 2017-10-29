import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by 光 on 2017/1/15.
 */
public class KdTree {
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private int size;       // the size of the tree

        public Point2D getP() {
            return p;
        }

        public void setP(Point2D p) {
            this.p = p;
        }

        public RectHV getRect() {
            return rect;
        }

        public void setRect(RectHV rect) {
            this.rect = rect;
        }

        public Node getLb() {
            return lb;
        }

        public void setLb(Node lb) {
            this.lb = lb;
        }

        public Node getRt() {
            return rt;
        }

        public void setRt(Node rt) {
            this.rt = rt;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    private Node root;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        if (root == null) return 0;
        else return root.getSize();
    }

//    private int size(Node node) {
//        if (node == null) {
//            return 0;
//        }
//        return size(node.getLb()) + size(node.getRt()) + 1;
//    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        root = insert(root, 0, p);
    }

    private Node insert(Node node, int height, Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        if (node == null) {
            Node newNode = new Node();
            newNode.setP(p);
            if (height == 0) {
                newNode.setRect(new RectHV(0, 0, 1, 1));
            }
            newNode.setSize(1);
            return newNode;
        }
        if (contains(p)) {
            return node;
        }

        if (height % 2 == 0) {
            double cmp = p.x() - node.getP().x();
            if (cmp < 0) {
                Node lb = insert(node.getLb(), height + 1, p);
                lb.setRect(new RectHV(node.getRect().xmin(), node.getRect().ymin(), node.getP().x(), node.getRect().ymax()));
                if (node.getLb() != null) {
                    lb.setSize(node.getLb().getSize() + 1);
                }
                node.setLb(lb);
                node.setSize(node.getSize() + 1);
            } else {
                Node rt = insert(node.getRt(), height + 1, p);
                rt.setRect(new RectHV(node.getP().x(), node.getRect().ymin(), node.getRect().xmax(), node.getRect().ymax()));
                if (node.getRt() != null) {
                    rt.setSize(node.getRt().getSize() + 1);
                }
                node.setRt(rt);
                node.setSize(node.getSize() + 1);
            }
        } else {
            double cmp = p.y() - node.getP().y();
            if (cmp < 0) {
                Node lb = insert(node.getLb(), height + 1, p);
                lb.setRect(new RectHV(node.getRect().xmin(), node.getRect().ymin(), node.getRect().xmax(), node.getP().y()));
                if (node.getLb() != null) {
                    lb.setSize(node.getLb().getSize() + 1);
                }
                node.setLb(lb);
                node.setSize(node.getSize() + 1);
            } else {
                Node rt = insert(node.getRt(), height + 1, p);
                rt.setRect(new RectHV(node.getRect().xmin(), node.getP().y(), node.getRect().xmax(), node.getRect().ymax()));
                if (node.getRt() != null) {
                    rt.setSize(node.getRt().getSize() + 1);
                }
                node.setRt(rt);
                node.setSize(node.getSize() + 1);
            }
        }

        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        return contains(root, 0, p);
    }

    private boolean contains(Node node, int height, Point2D p) {
        if (node == null) {
            return false;
        }
        if (node.getP().equals(p)) {
            return true;
        }

        double cmp = 0;
        if (height % 2 == 0) {
            cmp = p.x() - node.getP().x();
        } else {
            cmp = p.y() - node.getP().y();
        }
        if (cmp < 0) {
            return contains(node.getLb(), height + 1, p);
        } else {
            return contains(node.getRt(), height + 1, p);
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, 0);
    }

    private void draw(Node node, int height) {
        if (node == null) {
            return;
        }

        if (height % 2 == 0) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.getP().x(), node.getRect().ymin(), node.getP().x(), node.getRect().ymax());
        } else {
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.getRect().xmin(), node.getP().y(), node.getRect().xmax(), node.getP().y());
        }
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.02);
        node.getP().draw();

        draw(node.getLb(), height + 1);
        draw(node.getRt(), height + 1);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }
        return range(root, rect);
    }

    private Collection<Point2D> range(Node node, RectHV rect) {
        Set<Point2D> result = new TreeSet<>();
        if (node == null || !node.getRect().intersects(rect)) {
            return result;
        }
        if (rect.contains(node.getP())) {
            result.add(node.getP());
        }
        result.addAll(range(node.getLb(), rect));
        result.addAll(range(node.getRt(), rect));
        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            return null;
        }
        return nearest(root, 0, p, null);
    }

    private Point2D nearest(Node node, int height, Point2D p, Point2D nearest) {
        if (node == null) {
            return null;
        }
        if (!node.getP().equals(p) && (nearest == null || node.getP().distanceTo(p) < nearest.distanceTo(p))) {
            nearest = node.getP();
        }

        if (nearest != null && nearest.distanceTo(p) <= node.getRect().distanceTo(p)) {
            return nearest;
        }

        if ((height % 2 == 0 && p.x() < node.getP().x()) || (height % 2 != 0 && p.y() < node.getP().y())) {
            Point2D lb = nearest(node.getLb(), height + 1, p, nearest);
            if (lb != null && (nearest == null || lb.distanceTo(p) < nearest.distanceTo(p))) {
                nearest = lb;
            }
            Point2D rt = nearest(node.getRt(), height + 1, p, nearest);
            if (rt != null && (nearest == null || rt.distanceTo(p) < nearest.distanceTo(p))) {
                nearest = rt;
            }
        } else {
            Point2D rt = nearest(node.getRt(), height + 1, p, nearest);
            if (rt != null && (nearest == null || rt.distanceTo(p) < nearest.distanceTo(p))) {
                nearest = rt;
            }
            Point2D lb = nearest(node.getLb(), height + 1, p, nearest);
            if (lb != null && (nearest == null || lb.distanceTo(p) < nearest.distanceTo(p))) {
                nearest = lb;
            }
        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        System.out.println("isEmpty: " + kdTree.isEmpty() + ", size:" + kdTree.size());
        Point2D p1 = new Point2D(0.1, 0.4);
        kdTree.insert(p1);
        System.out.println("Insert point:" + p1);
        System.out.println("isEmpty: " + kdTree.isEmpty() + ", size:" + kdTree.size());
        System.out.println("Contains Point:" + p1 + " " + kdTree.contains(p1));
        Point2D p2 = new Point2D(0.2, 0.2);
        System.out.println("Contains Point:" + p2 + " " + kdTree.contains(p2));
        kdTree.insert(p2);
        System.out.println("Insert point:" + p2);
        System.out.println("Contains Point:" + p2 + " " + kdTree.contains(p2));
        Point2D p3 = new Point2D(0.4, 0.5);
        kdTree.insert(p3);
        System.out.println("Insert point:" + p3);
        Point2D p4 = new Point2D(0.3, 0.8);
        kdTree.insert(p4);
        System.out.println("Insert point:" + p4);
        Point2D p5 = new Point2D(0.7, 0.4);
        kdTree.insert(p5);
        System.out.println("Insert point:" + p5);
        Point2D p6 = new Point2D(0.8, 0.7);
        kdTree.insert(p6);
        System.out.println("Insert point:" + p6);
        System.out.println("Size: " + kdTree.size());

//        kdTree.draw();

        RectHV rect1 = new RectHV(0.4, 0.3, 0.9, 0.6);
        System.out.println("Range(rect" + rect1 + ") result: ");
        for (Point2D point : kdTree.range(rect1)) {
            System.out.print(point + " ");
        }
        System.out.println();
        System.out.println("Nearest(point" + p1 + "): " + kdTree.nearest(p1));
        System.out.println("Nearest(point" + p2 + "): " + kdTree.nearest(p2));
        System.out.println("Nearest(point" + p3 + "): " + kdTree.nearest(p3));
        System.out.println("Nearest(point" + p4 + "): " + kdTree.nearest(p4));
        System.out.println("Nearest(point" + p5 + "): " + kdTree.nearest(p5));
        System.out.println("Nearest(point" + p6 + "): " + kdTree.nearest(p6));
    }
}
