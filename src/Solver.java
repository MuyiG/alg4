import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by 光 on 2017/1/6.
 */
public class Solver {

    private List<Board> solutionPath;

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int num;
        SearchNode previous;

        SearchNode(Board board, int num, SearchNode previous) {
            this.board = board;
            this.num = num;
            this.previous = previous;
        }

        @Override
        public int compareTo(SearchNode o) {
            return (num + board.manhattan()) - (o.num + o.board.manhattan());
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }
        MinPQ<SearchNode> searchNodeMinPQ1 = new MinPQ<>();
        searchNodeMinPQ1.insert(new SearchNode(initial, 0, null));
        MinPQ<SearchNode> searchNodeMinPQ2 = new MinPQ<>();
        searchNodeMinPQ2.insert(new SearchNode(initial.twin(), 0, null));
        // A* Search
        while (!searchNodeMinPQ1.isEmpty() && !searchNodeMinPQ2.isEmpty()) {
            // initial
            SearchNode current1 = searchNodeMinPQ1.delMin();
            if (current1.board.isGoal()) {
                solutionPath = constructPath(current1);
                return;
            }
            for (Board neighbour : current1.board.neighbors()) {
                if (current1.previous != null && neighbour.equals(current1.previous.board)) {
                    continue;
                }
                searchNodeMinPQ1.insert(new SearchNode(neighbour, current1.num + 1, current1));
            }

            // twin
            SearchNode current2 = searchNodeMinPQ2.delMin();
            if (current2.board.isGoal()) {
                solutionPath = null;
                return;
            }
            for (Board neighbour : current2.board.neighbors()) {
                if (current2.previous != null && neighbour.equals(current2.previous.board)) {
                    continue;
                }
                searchNodeMinPQ2.insert(new SearchNode(neighbour, current2.num + 1, current2));
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solutionPath != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return solutionPath.size() - 1;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solutionPath;
    }

    private List<Board> constructPath(SearchNode current) {
        Stack<Board> tempStack = new Stack<>();
        while (current != null) {
            tempStack.push(current.board);
            current = current.previous;
        }
        List<Board> result = new ArrayList<>();
        while (!tempStack.isEmpty()) {
            result.add(tempStack.pop());
        }
        return result;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
