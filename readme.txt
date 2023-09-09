Programming Assignment 8: WordNet with Java Libraries

/* *****************************************************************************
 *  Please take a moment now to fill out the mid-semester survey:
 *  https://forms.gle/diTbj5r4o4xXbJm89
 *
 *  If you're working with a partner, please do this separately.
 *
 *  Type your initials below to confirm that you've completed the survey.
 **************************************************************************** */



/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in synsets.txt. Why did you make this choice?
 **************************************************************************** */

To store the information in synsets.txt, we used 2 different HashMaps. The
first HashMap stored the nouns as keys, and the values being a stack of
integers representing the possible ids that the noun has. The second hashmap
stores the ids as keys and the values being complete synsets as strings. We
made this choice because HashMaps are relatively efficient,can go through
keys quickly, and objects in WordNet need to have an id and a string associated
with them, so a hashmap was the best choice because you can have a key and
value for an object.

/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in hypernyms.txt. Why did you make this choice?
 **************************************************************************** */

To store the information in hypernyms.txt, we used a digraph with the size
being the amount of synsets in synsets.txt We made this choice because to find
the shortest common ancestor it is easy to do with a digraph, because you can
do a breadth first search on the digraph of synsets/hypernyms.

/* *****************************************************************************
 *  Describe concisely the algorithm you use in the constructor of
 *  ShortestCommonAncestor to check if the digraph is a rooted DAG.
 *  What is the order of growth of the worst-case running times of
 *  your algorithm? Express your answer as a function of the
 *  number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) Use Big Theta notation to simplify
 *  your answer.
 **************************************************************************** */

Description: The algorithm we use in the constructor of ShortestCommonAncestor
checks if the given digraph G is null, if so: throw an
IllegalArgumentException. We then initialize our marked, and onStack arrays,
and loop through every vertex and call the cycleCheck() helper method on that
vertex. The cycleCheck() helper method keeps track of the marked and the
onStack(), and if a neighbor of the vertex to check is already on the stack,
then there is a cycle, so we update the cycle boolean to true. We then throw
an IllegalArgumentException if cycle == true. We then check the amount of
vertices with an outdegree of zero (roots) and if there are more than 1:
throw an IllegalArgumentException. Finally if there is no
IllegalArgumentException thrown, then we create a copy of G, and store it
as this.Digraph.


Order of growth of running time: O(E + V)


/* *****************************************************************************
 *  Describe concisely your algorithm to compute the shortest common ancestor
 *  in ShortestCommonAncestor. For each method, give the order of growth of
 *  the best- and worst-case running times. Express your answers as functions
 *  of the number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) Use Big Theta notation to simplify your
 *  answers.
 *
 *  If you use hashing, assume the uniform hashing assumption so that put()
 *  and get() take constant time per operation.
 *
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't forget
 *  to count the time needed to initialize the marked[], edgeTo[], and
 *  distTo[] arrays.
 **************************************************************************** */

Description: To compute the shortest common ancestor we use a helper function
called search(), which uses a hashmap to do a bfs search through a given nodes
ancestors. We call search for v and w, and then loop through v's keys, and then
check if w contains that key. If w contains that key, then we calculate the
distance. We keep track of the shortest distance and the shortest node. We
return shortest for length() and shortestNode for ancestor(). We do the same
algorithm for subset search, but we have a different helper function called
subsetSearch, which returns a hashmap using a bfs search through every node in
the subset's neighbors.


                                 running time
method                  best case            worst case
--------------------------------------------------------
length()                0(1)                 O(E + V)

ancestor()              0(1)                 O(E + V)

lengthSubset()          0(1)                 O(E + V)

ancestorSubset()        0(1)                 O(E + V)

For length() and ancestor(), the best case would be that node v and node w
are the same node. Therefore, the shortest common ancestor would be that node.
So the length to the shortest common ancestor would just be 0 because the length
would be to itself, and the shortest common ancestor would just be either v or w
(since they are the same). In the worst case for length() and ancestor(), the
adj lists could include the entire digraph, which would mean that these methods
would have to iterate through all the vertices and edges. For lengthSubset() and
ancestorSubset(), the best case would be that the two subsets include all of the
same nodes, in which case the shortest common ancestor would just be one of the
pairs of the same nodes. Length would be 0, and the shortest common ancestor
could be any of the nodes (since they all have an identical match in the other
subset). For the worst case, one or both of the subsets could include all the
nodes in the digraph which would require iterating through all the vertices and
edges.

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

None.

/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */

Darius's office hours.

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

None.

/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */

Dillon Remuck: Helped implement code and conceptualize ideas for the code.
Abigail Wilson: Helped implement code and conceptualize ideas for the code.

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */

We both enjoyed the project.
