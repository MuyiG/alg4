import edu.princeton.cs.algs4.Graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class BoggleSolver {

    private Set<String> dictionary;
    private Set<String> validWords;

    BoggleBoard board;
    boolean[] marked;
    int[] edgeTo;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        validWords = new HashSet<>();
        this.dictionary = new HashSet<>();
        this.dictionary.addAll(Arrays.asList(dictionary));
    }

    // Returns the set of all valid validWords in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        this.board = board;
        int m = board.rows(), n = board.cols();
        Graph graph = buildGraph(m, n);
        for (int v = 0; v < graph.V(); v++) {
            marked = new boolean[m * n];
            edgeTo = new int[m * n];
            for (int i = 0; i < m * n; i++) {
                edgeTo[i] = -1;
            }
            dfs(graph, v);
        }
        return validWords;
    }

    private Graph buildGraph(int m, int n) {
        Graph graph = new Graph(m * n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int v = i * m + j;
                if (i > 0 && j < n - 1) {
                    graph.addEdge(v, (i - 1) * m + j + 1);
                }
                if (i < m - 1) {
                    graph.addEdge(v, (i + 1) * m + j);
                    if (j < n - 1) {
                        graph.addEdge(v, (i + 1) * m + j + 1);
                    }
                }
                if (j < n -1) {
                    graph.addEdge(v, i * m + j + 1);
                }
            }
        }
        return graph;
    }

    private void dfs(Graph graph, int v) {
        marked[v] = true;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(graph, w);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        Stack<Integer> stack = new Stack<>();
        int i;
        for (i = v; edgeTo[i] != -1; i = edgeTo[i]) {
            stack.push(i);
        }
        stack.push(i);
        while (!stack.isEmpty()) {
            int x = stack.pop();
            stringBuilder.append(board.getLetter(x / board.cols(), x % board.cols()));
        }
        String str = stringBuilder.toString();
        if (dictionary.contains(str)) {
            validWords.add(str);
        }
        marked[v] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        return 0;
    }

    public static void main(String[] args) {
        String[] dictionary = {"ABC", "CBD", "DA", "B"};
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("input/boggle/board2x2.txt");
        System.out.println(board);
        System.out.println(solver.getAllValidWords(board));
    }
}
