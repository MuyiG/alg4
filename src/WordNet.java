import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {
    private Map<String, Integer> synsetMap = new HashMap<>();
    private List<String> synsetList = new ArrayList<>();
    private Digraph wordNetGraph;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In in = new In(synsets);
        int count = 0;
        String temp;
        while ((temp = in.readLine()) != null) {
            String[] tempArr = temp.split(",");
            synsetMap.put(tempArr[1], Integer.valueOf(tempArr[0]));
            synsetList.add(tempArr[1]);
            count++;
        }
        wordNetGraph = new Digraph(count);

        In in2 = new In(hypernyms);
        while ((temp = in2.readLine()) != null) {
            String[] ids = temp.split(",");
            int from = Integer.valueOf(ids[0]);
            for (int i = 1; i < ids.length; i++) {
                int to = Integer.valueOf(ids[i]);
                wordNetGraph.addEdge(from, to);
            }
        }

        sap = new SAP(wordNetGraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetList;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return synsetMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return sap.length(synsetMap.get(nounA), synsetMap.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        int v = synsetMap.get(nounA);
        int w = synsetMap.get(nounB);
        return synsetList.get(sap.ancestor(v, w));
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
