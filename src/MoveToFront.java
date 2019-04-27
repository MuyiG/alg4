import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.LinkedList;

public class MoveToFront {
    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        LinkedList<Character> sequence = initSequence();
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int index = 0;
            for (Iterator<Character> iterator = sequence.iterator(); iterator.hasNext(); index++) {
                char temp = iterator.next();
                if (temp == c) {
                    iterator.remove();
                    break;
                }
            }
            BinaryStdOut.write(index, 8);
            StdOut.printf("");
            sequence.addFirst(c);
        }
        BinaryStdOut.close();
    }

    private static LinkedList<Character> initSequence() {
        LinkedList<Character> sequence = new LinkedList<>();
        for (int i = 0; i < R; i++) {
            sequence.add((char) i);
        }
        return sequence;
    }


    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        LinkedList<Character> sequence = initSequence();
        while (!BinaryStdIn.isEmpty()) {
            int index = BinaryStdIn.readChar();
            char c = ' ';
            int i = 0;
            for (Iterator<Character> iterator = sequence.iterator(); iterator.hasNext(); i++) {
                c = iterator.next();
                if (i == index) {
                    iterator.remove();
                    break;
                }
            }
            BinaryStdOut.write(c);
            sequence.addFirst(c);
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        }
        if (args[0].equals("+")) {
            decode();
        }
    }
}
