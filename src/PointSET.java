import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by 光 on 2017/1/15.
 */
public class PointSET {
    private Set<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : pointSet) {
            point.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }
        Set<Point2D> result = new TreeSet<>();
        for (Point2D point : pointSet) {
            if (rect.contains(point)) {
                result.add(point);
            }
        }
        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        Point2D result = null;
        double minDistance = 0;
        for (Point2D point : pointSet) {
            if (point.equals(p)) {
                continue;
            }
            double tempDistance = point.distanceTo(p);
            if (minDistance == 0) {
                minDistance = tempDistance;
                result = point;
            } else {
                if (tempDistance < minDistance) {
                    minDistance = tempDistance;
                    result = point;
                }
            }
        }
        return result;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET pointSET = new PointSET();
        System.out.println("isEmpty: " + pointSET.isEmpty() + ", size:" + pointSET.size());
        Point2D p1 = new Point2D(0.1, 0.4);
        pointSET.insert(p1);
        System.out.println("Insert point:" + p1);
        System.out.println("isEmpty: " + pointSET.isEmpty() + ", size:" + pointSET.size());
        System.out.println("Contains Point:" + p1 + " " + pointSET.contains(p1));
        Point2D p2 = new Point2D(0.2, 0.2);
        System.out.println("Contains Point:" + p2 + " " + pointSET.contains(p2));
        pointSET.insert(p2);
        System.out.println("Insert point:" + p2);
        System.out.println("Contains Point:" + p2 + " " + pointSET.contains(p2));
        RectHV rect1 = new RectHV(0.1, 0.1, 0.6, 0.3);
        System.out.println("Range(rect" + rect1 + "): " + pointSET.range(rect1));
        Point2D p3 = new Point2D(0.7, 0.6);
        pointSET.insert(p3);
        System.out.println("Insert point:" + p3);
        System.out.println("Nearest(point" + p1 + "): " + pointSET.nearest(p1));
    }
}
