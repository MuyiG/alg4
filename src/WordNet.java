import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {
    private final Map<String, Integer> nounMap = new HashMap<>();
    private final List<String> synsetList = new ArrayList<>();
    private final Digraph wordNetGraph;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In in = new In(synsets);
        int count = 0;
        String temp;
        while ((temp = in.readLine()) != null) {
            String[] tempArr = temp.split(",");
            for (String noun : tempArr[1].split(" ")) {
                nounMap.put(noun, Integer.valueOf(tempArr[0]));
            }
            synsetList.add(tempArr[1]);
            count++;
        }
        wordNetGraph = new Digraph(count);

        In in2 = new In(hypernyms);
        while ((temp = in2.readLine()) != null) {
            String[] ids = temp.split(",");
            int from = Integer.parseInt(ids[0]);
            for (int i = 1; i < ids.length; i++) {
                int to = Integer.parseInt(ids[i]);
                wordNetGraph.addEdge(from, to);
            }
        }

        sap = new SAP(wordNetGraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return nounMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        return sap.length(nounMap.get(nounA), nounMap.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        int v = nounMap.get(nounA);
        int w = nounMap.get(nounB);
        return synsetList.get(sap.ancestor(v, w));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("input/wordnet/synsets.txt", "input/wordnet/hypernyms.txt");
        System.out.println(wordNet.isNoun("reagin"));
        System.out.println(wordNet.isNoun("xxxxxxx"));
        System.out.println(wordNet.distance("calcium_ion", "clotting_factor"));
        System.out.println(wordNet.distance("calcium_ion", "factor_IV"));
        System.out.println(wordNet.sap("calcium_ion", "histone"));
        System.out.println(wordNet.distance("factor_IV", "histone"));
    }
}
