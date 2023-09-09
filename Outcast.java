import edu.princeton.cs.algs4.In;

public class Outcast {

    // creates WordNet object
    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxSum = 0;
        String maxString = null;
        for (int i = 0; i < nouns.length; i++) {
            int currSum = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i != j) {
                    int currDistance =
                            wordNet.distance(nouns[i], nouns[j]);
                    currSum += currDistance;
                }
            }
            if (currSum > maxSum) {
                maxSum = currSum;
                maxString = nouns[i];
            }
        }
        return maxString;
    }

    // test client (see below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            System.out.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}
