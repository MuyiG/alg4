import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;
import java.util.List;

public class BurrowsWheeler {

    private static final int R = 256;

    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        String s = StdIn.readAll();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(s);
        int n = s.length();
        int first = 0;
        char[] t = new char[n];
        for (int i = 0; i < n; i++) {
            int originIndex = circularSuffixArray.index(i);
            if (originIndex == 0) {
                first = i;
            }
            t[i] = s.charAt((originIndex + n - 1) % n);
        }
        BinaryStdOut.write(first);
        for (char c : t) {
            BinaryStdOut.write(c);
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        // read first and t[]
        int first = BinaryStdIn.readInt();
        List<Character> characterList = new ArrayList<>();
        while (!BinaryStdIn.isEmpty()) {
            characterList.add(BinaryStdIn.readChar());
        }
        char[] t = new char[characterList.size()];
        for (int i = 0; i < characterList.size(); i++) {
            t[i] = characterList.get(i);
        }
//        int first = 3;
//        char[] t = {'A', 'R', 'D', '!', 'R', 'C', 'A', 'A', 'A', 'A', 'B', 'B'};

        // build next[] using key-indexed-counting
        char[] sorted = new char[t.length];
        int[] next = new int[t.length];
        int[] count = new int[R + 1];
        for (char c : t) {
            count[c + 1]++;
        }
        for (int i = 0; i < R; i++) {
            count[i+1] += count[i];
        }
        for (int i = 0; i < t.length; i++) {
            sorted[count[t[i]]] = t[i];
            next[count[t[i]]] = i;
            count[t[i]]++;
        }

        // do reverse
        int i = first;
        for (int j = 0; j < t.length; j++) {
            BinaryStdOut.write(sorted[i]);
            i = next[i];
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        }
        if (args[0].equals("+")) {
            inverseTransform();
        }
    }
}
