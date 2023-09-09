import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.Stack;

public class WordNet {

    // creates a HashMap of Strings and Integers that store nouns and
    // their ids
    private HashMap<String, Stack<Integer>> nounsMap;

    // creates a HashMap of Integers and Strings that store synsets and their
    // ids
    private HashMap<Integer, String> synsetMap;

    // creates a ShortestCommonAncestor object
    private ShortestCommonAncestor sca;

    // creates a digraph
    private Digraph digraph;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        this.nounsMap = new HashMap<String, Stack<Integer>>();
        this.synsetMap = new HashMap<Integer, String>();
        In in = new In(synsets);
        In in1 = new In(hypernyms);
        int count = 0;
        // reads through synsets
        while (in.hasNextLine()) {
            count += 1;
            String[] split = in.readLine().split(",");
            int id = Integer.parseInt(split[0]);
            String nouns = split[1];
            synsetMap.put(id, nouns);
            String[] nounsSplit = nouns.split(" ");
            for (int i = 0; i < nounsSplit.length; i++) {
                if (nounsMap.get(nounsSplit[i]) == null) {
                    Stack<Integer> toAdd = new Stack<Integer>();
                    toAdd.push(id);
                    nounsMap.put(nounsSplit[i], toAdd);
                }
                else {
                    Stack<Integer> toAdd = nounsMap.get(nounsSplit[i]);
                    toAdd.push(id);
                    nounsMap.put(nounsSplit[i], toAdd);
                }
            }
        }
        this.digraph = new Digraph(count);
        // reads through hypernyms
        while (in1.hasNextLine()) {
            String[] split = in1.readLine().split(",");
            String id1 = split[0];
            for (int i = 1; i < split.length; i++) {
                digraph.addEdge(Integer.parseInt(id1),
                                Integer.parseInt(split[i]));
            }
        }
        sca = new ShortestCommonAncestor(this.digraph);
    }

    // the set of all WordNet nouns
    public Iterable<String> nouns() {
        return nounsMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return this.nounsMap.containsKey(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        if (!this.isNoun(noun1) || !this.isNoun(noun2)) {
            throw new IllegalArgumentException();
        }
        Stack<Integer> noun1IDs = this.nounsMap.get(noun1);
        Stack<Integer> noun2IDs = this.nounsMap.get(noun2);
        int answerID = this.sca.ancestorSubset(noun1IDs, noun2IDs);
        return this.synsetMap.get(answerID);
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (!this.isNoun(noun1) || !this.isNoun(noun2)) {
            throw new IllegalArgumentException();
        }
        Stack<Integer> noun1IDs = this.nounsMap.get(noun1);
        Stack<Integer> noun2IDs = this.nounsMap.get(noun2);
        return this.sca.lengthSubset(noun1IDs, noun2IDs);
    }

    // unit testing (required)
    public static void main(String[] args) {
        WordNet test = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println(test.nouns());
        System.out.println(test.distance("component", "composer"));
        System.out.println(test.isNoun("component"));
        System.out.println(test.sca("component", "composer"));
    }

}
