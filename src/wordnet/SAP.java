package wordnet;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Shortest Ancestral Path
 */
public class SAP {
    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (!isValid(v) || !isValid(w)) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < digraph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                candidates.add(i);
            }
        }
        if (candidates.isEmpty()) {
            return -1;
        }

        int minLength = Integer.MAX_VALUE;
        for (int temp : candidates) {
            if (bfsV.distTo(temp) + bfsW.distTo(temp) < minLength) {
                minLength = bfsV.distTo(temp) + bfsW.distTo(temp);
            }
        }
        return minLength;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (!isValid(v) || !isValid(w)) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < digraph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                candidates.add(i);
            }
        }
        if (candidates.isEmpty()) {
            return -1;
        }

        int minLength = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int temp : candidates) {
            if (bfsV.distTo(temp) + bfsW.distTo(temp) < minLength) {
                minLength = bfsV.distTo(temp) + bfsW.distTo(temp);
                ancestor = temp;
            }
        }
        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (!isValid(v) || !isValid(w)) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < digraph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                candidates.add(i);
            }
        }
        if (candidates.isEmpty()) {
            return -1;
        }

        int minLength = Integer.MAX_VALUE;
        for (int temp : candidates) {
            if (bfsV.distTo(temp) + bfsW.distTo(temp) < minLength) {
                minLength = bfsV.distTo(temp) + bfsW.distTo(temp);
            }
        }
        if (minLength == Integer.MAX_VALUE) {
            minLength = -1;
        }
        return minLength;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (!isValid(v) || !isValid(w)) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < digraph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                candidates.add(i);
            }
        }
        if (candidates.isEmpty()) {
            return -1;
        }

        int minLength = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int temp : candidates) {
            if (bfsV.distTo(temp) + bfsW.distTo(temp) < minLength) {
                minLength = bfsV.distTo(temp) + bfsW.distTo(temp);
                ancestor = temp;
            }
        }
        return ancestor;
    }

    private boolean isValid(Iterable<Integer> v) {
        if (v == null) {
            return false;
        }
        for (Integer i : v) {
            if (!isValid(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValid(int v) {
        if (v < 0 || v >= digraph.V()) {
            return false;
        }
        return true;
    }


    // do unit testing of this class
    public static void main(String[] args) {
//        In in = new In("input/wordnet/digraph1.txt");
//        Digraph G = new Digraph(in);
//        SAP sap = new SAP(G);
//        while (!StdIn.isEmpty()) {
//            int v = StdIn.readInt();
//            int w = StdIn.readInt();
//            int length = sap.length(v, w);
//            int ancestor = sap.ancestor(v, w);
//            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//        }
    }
}
