import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ShortestCommonAncestor {

    // creates Digraph
    private Digraph digraph;

    // creates boolean cycle
    private boolean cycle;

    // creates marked array for cycleCheck
    private boolean[] marked;

    // created onStack array for cycleCheck
    private boolean[] onStack;

    // root node
    private int root;

    // constructor takes a digraph as an argument and checks if it is rooted
    // and has a cycle
    public ShortestCommonAncestor(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        root = 0;
        int roots = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0) {
                roots += 1;
                root = i;
            }
        }
        if (roots > 1) {
            throw new IllegalArgumentException();
        }
        // creates marked array with all nodes
        marked = new boolean[G.V()];
        // creates onStack array with all nodes
        onStack = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            // if v hasn't been marked and a cycle hasn't been found yet, then
            // do cycleCheck()
            if (!marked[v] && !cycle) {
                cycleCheck(G, v);
            }
        }
        if (cycle) {
            throw new IllegalArgumentException();
        }
        this.digraph = new Digraph(G);
    }

    // helper method that uses Digraph d and node v, and determines
    // if a cycle exists using v
    private void cycleCheck(Digraph d, int v) {
        onStack[v] = true;
        marked[v] = true;
        // loop through v's neighbors
        for (int w : d.adj(v)) {
            // if there is already a cycle in d, then return
            if (cycle) return;
                // if neighbor w hasn't been marked, then run cycleCheck on w
            else if (!marked[w]) {
                cycleCheck(d, w);
            }
            // if w is already onStack then there is a cycle
            else if (onStack[w]) {
                cycle = true;
            }
        }
        onStack[v] = false;
    }

    // helper method that does a bfs search using hashmap
    private HashMap<Integer, Integer> search(int v) {
        // creates a HashMap to search through v's ancestors
        HashMap<Integer, Integer> dist = new HashMap<Integer, Integer>();
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(v);
        dist.put(v, 0);
        while (queue.peek() != null) {
            Integer i = queue.remove();
            for (int a : this.digraph.adj(i)) {
                if (!dist.containsKey(a)) {
                    queue.add(a);
                    dist.put(a, 1 + dist.get(i));
                }
            }
        }
        return dist;
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        if (v < 0 || v >= digraph.V()) {
            throw new IllegalArgumentException();
        }
        if (w < 0 || w >= digraph.V()) {
            throw new IllegalArgumentException();
        }
        if (v == w) {
            return 0;
        }
        // calls our helper method search()
        HashMap<Integer, Integer> hash1 = search(v);
        HashMap<Integer, Integer> hash2 = search(w);
        // we now calculate shortest distance
        int shortest = Integer.MAX_VALUE;
        for (int i : hash1.keySet()) {
            if (hash2.containsKey(i)) {
                int currDistance = hash1.get(i) + hash2.get(i);
                if (currDistance < shortest) {
                    shortest = currDistance;
                }
            }
        }
        return shortest;
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        if (v < 0 || v >= digraph.V()) {
            throw new IllegalArgumentException();
        }
        if (w < 0 || w >= digraph.V()) {
            throw new IllegalArgumentException();
        }
        if (v == w) {
            return v;
        }
        // calls our helper method search()
        HashMap<Integer, Integer> hash1 = search(v);
        HashMap<Integer, Integer> hash2 = search(w);
        // we now calculate shortest distance
        int shortest = Integer.MAX_VALUE;
        int shortestNode = root;
        for (int i : hash1.keySet()) {
            if (hash2.containsKey(i)) {
                int currDistance = hash1.get(i) + hash2.get(i);
                if (currDistance < shortest) {
                    shortest = currDistance;
                    shortestNode = i;
                }
            }
        }
        return shortestNode;
    }

    // helper method that does a bfs search through a subset of nodes
    private HashMap<Integer, Integer> subsetSearch(Iterable<Integer> subset) {
        // creates a HashMap for subset search
        HashMap<Integer, Integer> dist = new HashMap<Integer, Integer>();
        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i : subset) {
            // for every node in subset A, add it to the queue and dist HashMap
            queue.add(i);
            dist.put(i, 0);
        }
        while (queue.peek() != null) {
            Integer i = queue.remove();
            for (int a : this.digraph.adj(i)) {
                if (!dist.containsKey(a)) {
                    queue.add(a);
                    dist.put(a, 1 + dist.get(i));
                }
            }
        }
        return dist;
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null) {
            throw new IllegalArgumentException();
        }
        if (!subsetA.iterator().hasNext() || !subsetB.iterator().hasNext()) {
            throw new IllegalArgumentException();
        }
        for (Integer i : subsetA) {
            if (i == null) {
                throw new IllegalArgumentException();
            }
            if (i < 0 || i >= this.digraph.V()) {
                throw new IllegalArgumentException();
            }
        }
        for (Integer i : subsetB) {
            if (i == null) {
                throw new IllegalArgumentException();
            }
            if (i < 0 || i >= this.digraph.V()) {
                throw new IllegalArgumentException();
            }
        }
        if (subsetA.equals(subsetB)) {
            return 0;
        }
        // calls our helper method subsetSearch()
        HashMap<Integer, Integer> hash1 = subsetSearch(subsetA);
        HashMap<Integer, Integer> hash2 = subsetSearch(subsetB);
        // calculate shortest
        int shortest = Integer.MAX_VALUE;
        for (int i : hash1.keySet()) {
            if (hash2.containsKey(i)) {
                int currDistance = hash1.get(i) + hash2.get(i);
                if (currDistance < shortest) {
                    shortest = currDistance;
                }
            }
        }
        return shortest;
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null) {
            throw new IllegalArgumentException();
        }
        if (!subsetA.iterator().hasNext() || !subsetB.iterator().hasNext()) {
            throw new IllegalArgumentException();
        }
        for (Integer i : subsetA) {
            if (i == null) {
                throw new IllegalArgumentException();
            }
            if (i < 0 || i >= this.digraph.V()) {
                throw new IllegalArgumentException();
            }
        }
        for (Integer i : subsetB) {
            if (i == null) {
                throw new IllegalArgumentException();
            }
            if (i < 0 || i >= this.digraph.V()) {
                throw new IllegalArgumentException();
            }
        }
        if (subsetA.equals(subsetB)) {
            for (Integer i : subsetA) {
                return i;
            }
        }
        // calls our helper method subsetSearch()
        HashMap<Integer, Integer> hash1 = subsetSearch(subsetA);
        HashMap<Integer, Integer> hash2 = subsetSearch(subsetB);
        // calculate shortest
        int shortest = Integer.MAX_VALUE;
        int shortestNode = root;
        for (int i : hash1.keySet()) {
            if (hash2.containsKey(i)) {
                int currDistance = hash1.get(i) + hash2.get(i);
                if (currDistance < shortest) {
                    shortest = currDistance;
                    shortestNode = i;
                }
            }
        }
        return shortestNode;
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        for (int i = 0; i < G.V(); i++) {
            for (int j = 0; j < G.V(); j++) {
                int length = sca.length(i, j);
                int ancestor = sca.ancestor(i, j);
                System.out.printf("node1=" + i + " node2=" + j + " length = %d, "
                                          + "ancestor = "
                                          + "%d\n", length, ancestor);
            }
        }
        Stack<Integer> test1 = new Stack<Integer>();
        test1.push(1);
        test1.push(2);
        test1.push(3);
        Stack<Integer> test2 = new Stack<Integer>();
        test2.push(4);
        test2.push(5);
        test2.push(6);
        System.out.println(sca.lengthSubset(test1, test2));
        System.out.println(sca.ancestorSubset(test1, test2));
    }
}

