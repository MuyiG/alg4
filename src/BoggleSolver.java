import edu.princeton.cs.algs4.Graph;

public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {

    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int m = board.rows(), n = board.cols();
        Graph graph = buildGraph(m, n);
        boolean[] marked = new boolean[m * n];
        int[] edgeTo = new int[m * n];

        for (int v = 0; v < graph.V(); v++) {

        }

        return null;
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

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        return 0;
    }

    public static void main(String[] args) {
        BoggleSolver solver = new BoggleSolver(null);
        System.out.println(solver.buildGraph(3, 3));
    }
}
