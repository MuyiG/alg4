import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxDistance = 0, maxDistanceIndex = -1;
        for (int i = 0; i < nouns.length; i++) {
            if (distance(nouns, i) > maxDistance) {
                maxDistanceIndex = i;
            }
        }
        return nouns[maxDistanceIndex];
    }

    private int distance(String[] nouns, int index) {
        int result = 0;
        String temp = nouns[index];
        for (int i = 0; i < nouns.length; i++) {
            if (i == index) {
                continue;
            }
            result += wordNet.distance(nouns[i], temp);
        }
        return result;
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
