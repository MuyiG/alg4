import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class BoggleSolver {
    private final PrefixTrie<Integer> dictionaryTrie;

    private Set<String> validWords;

    private BoggleBoard board;
    private boolean[] marked;
    private int[] edgeTo;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        dictionaryTrie = new PrefixTrie<>();
        for (String temp : dictionary) {
            dictionaryTrie.put(temp, 1);
        }
    }

    // Returns the set of all valid validWords in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard boggleBoard) {
        board = boggleBoard;
        validWords = new HashSet<>();
        int m = boggleBoard.rows(), n = boggleBoard.cols();
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
                int v = i * n + j;
                if (i > 0 && j < n - 1) {
                    graph.addEdge(v, (i - 1) * n + j + 1);
                }
                if (i < m - 1) {
                    graph.addEdge(v, (i + 1) * n + j);
                    if (j < n - 1) {
                        graph.addEdge(v, (i + 1) * n + j + 1);
                    }
                }
                if (j < n - 1) {
                    graph.addEdge(v, i * n + j + 1);
                }
            }
        }
        return graph;
    }

    private void dfs(Graph graph, int v) {
        marked[v] = true;

        // backtracking optimization
        String str = getStringToV(v);
        if (!dictionaryTrie.isPrefixExist(str)) {
            marked[v] = false;
            return;
        }

        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(graph, w);
            }
        }

        if (str.length() > 2 && dictionaryTrie.contains(str)) {
            validWords.add(str);
        }
        marked[v] = false;
    }

    private String getStringToV(int v) {
        StringBuilder stringBuilder = new StringBuilder();
        Stack<Integer> stack = new Stack<>();
        int i;
        for (i = v; edgeTo[i] != -1; i = edgeTo[i]) {
            stack.push(i);
        }
        stack.push(i);
        while (!stack.isEmpty()) {
            int x = stack.pop();
            char c = board.getLetter(x / board.cols(), x % board.cols());
            stringBuilder.append(c);
            if (c == 'Q') {
                stringBuilder.append('U');
            }
        }
        return stringBuilder.toString();
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!dictionaryTrie.contains(word)) {
            return 0;
        }
        int length = word.length();
        if (length <= 2) {
            return 0;
        } else if (length <= 4) {
            return 1;
        } else if (length <= 5) {
            return 2;
        } else if (length <= 6) {
            return 3;
        } else if (length <= 7) {
            return 5;
        } else {
            return 11;
        }
    }

    public static void main(String[] args) {
        In in = new In("input/boggle/dictionary-yawl.txt");
        BoggleSolver solver = new BoggleSolver(in.readAllLines());
//        for (int i = 0; i < 50; i++) {
//            BoggleBoard board = new BoggleBoard();
//            System.out.println(board);
//            System.out.println(solver.getAllValidWords(board));
//        }

        testGetAllValidWords(solver, new BoggleBoard("input/boggle/board4x4.txt"));
        testGetAllValidWords(solver, new BoggleBoard("input/boggle/board-points1.txt"));
        testGetAllValidWords(solver, new BoggleBoard("input/boggle/board-dichlorodiphenyltrichloroethanes.txt"));
        testGetAllValidWords(solver, new BoggleBoard("input/boggle/board-pneumonoultramicroscopicsilicovolcanoconiosis.txt"));
    }

    private static void testGetAllValidWords(BoggleSolver solver, BoggleBoard board) {
        int score = 0, count = 0;
        for (String temp : solver.getAllValidWords(board)) {
            score += solver.scoreOf(temp);
            count++;
        }
        System.out.println("Score: " + score + ", Count: " + count);
    }
}
