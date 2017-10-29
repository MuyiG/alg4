import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 光 on 2016/12/4.
 */
public class BruteCollinearPoints {
    private List<LineSegment> lineSegments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point p1 = points[i];
                        Point p2 = points[j];
                        Point p3 = points[k];
                        Point p4 = points[l];
                        if (p1.slopeTo(p2) == p2.slopeTo(p3) && p2.slopeTo(p3) == p3.slopeTo(p4)) {
                            int min = i, max = i;
                            if (points[j].compareTo(points[min]) < 0) {
                                min = j;
                            }
                            if (points[j].compareTo(points[max]) > 0) {
                                max = j;
                            }
                            if (points[k].compareTo(points[min]) < 0) {
                                min = k;
                            }
                            if (points[k].compareTo(points[max]) > 0) {
                                max = k;
                            }
                            if (points[l].compareTo(points[min]) < 0) {
                                min = l;
                            }
                            if (points[l].compareTo(points[max]) > 0) {
                                max = l;
                            }
                            lineSegments.add(new LineSegment(points[min], points[max]));
                        }
                    }
                }
            }
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] lineSegmentArray = new LineSegment[lineSegments.size()];
        for (int i = 0; i < lineSegmentArray.length; i++) {
            lineSegmentArray[i] = lineSegments.get(i);
        }
        return lineSegmentArray;
    }

    public static void main(String[] args) {
        In in = new In("input/collinear/input8.txt");
        // read the n points from a file
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
