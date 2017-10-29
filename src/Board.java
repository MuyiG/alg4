import java.util.ArrayList;
import java.util.List;

/**
 * Created by 光 on 2017/1/5.
 */
public class Board {
    private final int[][] blocks;

    private final int dimension;

    private int manhattan;

    // construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.dimension = blocks.length;
        this.blocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
        int manhattan = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int temp = blocks[i][j];
                if (temp != 0 && temp != (i * dimension + j + 1)) {
                    int desI = (temp - 1) / dimension;
                    int desJ = (temp - 1) % dimension;
                    manhattan += Math.abs(desI - i) + Math.abs(desJ - j);
                }
            }
        }
        this.manhattan = manhattan;
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        int result = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * dimension + j + 1) {
                    result++;
                }
            }
        }
        return result;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] twinBlocks = copy();
        int tempI = -1, tempJ = -1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (twinBlocks[i][j] != 0) {
                    if (tempI == -1) {
                        tempI = i;
                        tempJ = j;
                    } else {
                        int temp = twinBlocks[i][j];
                        twinBlocks[i][j] = twinBlocks[tempI][tempJ];
                        twinBlocks[tempI][tempJ] = temp;
                        return new Board(twinBlocks);
                    }
                }
            }
        }

        return null;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y != null && y.getClass() == this.getClass()) {
            Board b = (Board) y;
            if (dimension != b.dimension() || manhattan != b.manhattan) {
                return false;
            }
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    if (blocks[i][j] != b.blocks[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> result = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    if (i > 0) {
                        int[][] copy = copy();
                        copy[i][j] = copy[i - 1][j];
                        copy[i - 1][j] = 0;
                        result.add(new Board(copy));
                    }
                    if (i < dimension - 1) {
                        int[][] copy = copy();
                        copy[i][j] = copy[i + 1][j];
                        copy[i + 1][j] = 0;
                        result.add(new Board(copy));
                    }
                    if (j > 0) {
                        int[][] copy = copy();
                        copy[i][j] = copy[i][j - 1];
                        copy[i][j - 1] = 0;
                        result.add(new Board(copy));
                    }
                    if (j < dimension - 1) {
                        int[][] copy = copy();
                        copy[i][j] = copy[i][j + 1];
                        copy[i][j + 1] = 0;
                        result.add(new Board(copy));
                    }
                }
            }
        }
        return result;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sb.append(blocks[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private int[][] copy() {
        int[][] newBlocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                newBlocks[i][j] = blocks[i][j];
            }
        }
        return newBlocks;
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(blocks);
        System.out.println(board);
        System.out.println("Hamming:" + board.hamming());
        System.out.println("Manhattan:" + board.manhattan());
        System.out.println("twin:");
        System.out.println(board.twin());
        int[][] newBlocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board newBoard = new Board(newBlocks);
        System.out.println(newBoard.isGoal());
        System.out.println(board.equals(newBoard));
//        System.out.println(board.equals(board));
        System.out.println("neighbours:");
        for (Board temp : board.neighbors()) {
            System.out.println(temp);
        }
        int[][] blocks2By2 = {{1, 3}, {0, 2}};
        System.out.println("2 by 2 twin:");
        Board board2 = new Board(blocks2By2);
        System.out.println(board2.twin());
    }

}
