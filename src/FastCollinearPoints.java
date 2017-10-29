import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 光 on 2016/12/11.
 */
public class FastCollinearPoints {
    private List<LineSegment> lineSegments = new ArrayList<>();

    private List<Point> starts = new ArrayList<>();
    private List<Point> ends = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
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

        Point[] pointsCopy = new Point[points.length];
        System.arraycopy(points, 0, pointsCopy, 0, points.length);
        for (Point p : points) {
            Arrays.sort(pointsCopy, p.slopeOrder());
            List<Point> tempPoints = new ArrayList<>();
            for (int i = 0; i < pointsCopy.length; i++) {
                if (i > 0 && p.slopeTo(pointsCopy[i]) != p.slopeTo(pointsCopy[i - 1])) {
                    if (tempPoints.size() >= 3) {
                        tempPoints.add(p);
                        constructLineSegment(tempPoints);
                    }
                    tempPoints.clear();
                }
                tempPoints.add(pointsCopy[i]);
            }

            // deal with boundary
            if (tempPoints.size() >= 3) {
                tempPoints.add(p);
                constructLineSegment(tempPoints);
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

    private void constructLineSegment(List<Point> points) {
        Point start = points.get(0);
        Point end = points.get(0);
        for (Point tempPoint : points) {
            if (tempPoint.compareTo(start) < 0) {
                start = tempPoint;
            }
            if (tempPoint.compareTo(end) > 0) {
                end = tempPoint;
            }
        }

        // avoid duplication
        boolean exist = false;
        for (int k = 0; k < starts.size(); k++) {
            if (starts.get(k).compareTo(start) == 0 && ends.get(k).compareTo(end) == 0) {
                exist = true;
            }
        }
        if (!exist) {
            starts.add(start);
            ends.add(end);
            lineSegments.add(new LineSegment(start, end));
        }
    }

    public static void main(String[] args) {
        In in = new In("input/collinear/random6.txt");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}