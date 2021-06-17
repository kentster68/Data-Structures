import java.util.List;
import java.util.Set;
import java.util.Queue;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Kent Barber
 * @version 1.0
 * @userid kbarber9
 * @GTID 903326160
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Can not have null inputs.");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start does not exist.");
        }

        List<Vertex<T>> list = new ArrayList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjMap = graph.getAdjList();
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> current = ((LinkedList<Vertex<T>>) queue).removeFirst();
            if (!visitedSet.contains(current)) {
                visitedSet.add(current);
                list.add(current);
                List<VertexDistance<T>> adjList = adjMap.get(current);
                for (VertexDistance<T> adjObject : adjList) {
                    if (!visitedSet.contains(adjObject.getVertex())) {
                        queue.add(adjObject.getVertex());
                    } else {
                        queue.add(current);
                    }
                }
            }
        }

        return list;

    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     * <p>
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Can not have a null input.");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The start does not exist.");
        }
        List<Vertex<T>> visitedSet = new ArrayList<Vertex<T>>();
        dfsHelper(start, graph, visitedSet);

        return visitedSet;
    }


    /**
     * Private helper method for dfs
     * @param start Starting index
     * @param graph graph of edges and vertices
     * @param visitedSet list of vertices
     * @param <T> generic type
     */
    private static <T> void dfsHelper(Vertex<T> start, Graph<T> graph, List<Vertex<T>> visitedSet) {
        visitedSet.add(start);
        for (VertexDistance<T> edge : graph.getAdjList().get(start)) {
            Vertex<T> v = edge.getVertex();
            if (!visitedSet.contains(v)) {
                dfsHelper(v, graph, visitedSet);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * <p>
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * <p>
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * <p>
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Can not have a null input.");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start does not exist.");
        }

        Map<Vertex<T>, Integer> hM = new HashMap<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjMap = graph.getAdjList();
        Set<Vertex<T>> vertices = graph.getVertices();

        for (Vertex<T> eachVertex : vertices) {
            hM.put(eachVertex, Integer.MAX_VALUE);
        }

        hM.put(start, 0);

        PriorityQueue<VertexDistance<T>> pQ = new PriorityQueue<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        pQ.add(new VertexDistance<>(start, 0));

        while (!pQ.isEmpty() && !visitedSet.equals(graph.getVertices())) {
            VertexDistance<T> current = pQ.poll();
            visitedSet.add(current.getVertex());
            List<VertexDistance<T>> currentAdj = adjMap.get(current.getVertex());
            for (VertexDistance<T> eachVD : currentAdj) {
                if (hM.get(eachVD.getVertex()) > eachVD.getDistance() + current.getDistance()) {
                    hM.put(eachVD.getVertex(), eachVD.getDistance() + current.getDistance());
                    pQ.add(new VertexDistance<>(eachVD.getVertex(), eachVD.getDistance() + current.getDistance()));
                }
            }
        }
        return hM;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     * <p>
     * You should NOT allow self-loops or parallel edges into the MST.
     * <p>
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interface.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The graph is null.");
        }

        Set<Edge<T>> set = new HashSet<Edge<T>>();
        PriorityQueue<Edge<T>> queue = new PriorityQueue<Edge<T>>();

        for (Edge<T> edge: graph.getEdges()) {
            if (!edge.getU().equals(edge.getV())) {
                queue.add(edge);
            }
        }

        DisjointSet<Vertex<T>> djSet = new DisjointSet<Vertex<T>>();

        while (!queue.isEmpty() && set.size() < 2 * (graph.getAdjList().keySet().size() - 1)) {
            Edge<T> currEdge = queue.poll();
            Vertex<T> start = currEdge.getU();
            Vertex<T> toVertex = currEdge.getV();
            if (!djSet.find(start).equals(djSet.find(toVertex))) {
                set.add(currEdge);
                set.add(new Edge<T>(toVertex, start, currEdge.getWeight()));
                djSet.union(start, toVertex);
            }
        }

        if (graph.getAdjList().keySet().size() == 0) {
            return null;
        }
        if (set.size() < (2 * (Math.abs(graph.getAdjList().keySet().size()) - 1))) {
            return null;
        }
        return set;
    }

    private static void merge(int[] left, int[] right, int[] arr) {
        int l = 0;
        int curr = 0;
        int r = 0;
        int leng = left.length + right.length;
        int middle = leng/2;
        while (l < middle && r < leng - middle) {
            if () {
            l++;
            }

        }
    }
}
