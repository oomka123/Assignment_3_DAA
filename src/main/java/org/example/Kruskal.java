package org.example;
import java.util.*;

/**
 * Implements Kruskal’s algorithm for finding the Minimum Spanning Tree (MST)
 * of a weighted undirected graph. It sorts all edges by weight and repeatedly
 * adds the smallest edge that does not form a cycle, using a Union-Find structure
 * for efficient cycle detection.
 */
public class Kruskal {

    /**
     * Runs Kruskal's algorithm on the given graph defined by nodes and edges.
     * Sorts edges by weight and adds them to the MST if they don't create cycles.
     *
     * @param nodes list of node identifiers in the graph
     * @param edges list of edges connecting the nodes with associated weights
     * @return a Result object containing the MST edges, total cost, operation count, and execution time
     */
    public Result runKruskal(List<String> nodes, List<Edge> edges) {
        Result result = new Result();
        long startTime = System.nanoTime();

        // Sort edges by their weight in ascending order
        edges.sort(Comparator.comparingInt(e -> e.getWeight()));
        UnionFind uf = new UnionFind(nodes);

        // Iterate over edges in ascending order of weight
        for (Edge edge : edges) {
            result.operations++;
            // Check if current edge creates a cycle by comparing root parents
            if (!uf.find(edge.getFrom()).equals(uf.find(edge.getTo()))) {
                // If no cycle, union the sets and add edge to MST
                uf.union(edge.getFrom(), edge.getTo());
                result.mstEdges.add(edge);
                result.totalCost += edge.getWeight();
            }
        }

        // Add the operations performed by UnionFind to the total operations count
        result.operations += uf.operations;
        long endTime = System.nanoTime();
        result.executionTimeMs = (endTime - startTime) / 1_000_000.0;

        return result;
    }

    /**
     * Union-Find (Disjoint Set Union) data structure implementation to efficiently
     * manage and merge sets of vertices, supporting cycle detection in Kruskal's algorithm.
     */
    static class UnionFind {
        private final Map<String, String> parent = new HashMap<>();
        private final Map<String, Integer> rank = new HashMap<>();
        private int operations = 0;

        /**
         * Initializes the Union-Find structure with each vertex in its own set.
         *
         * @param vertices list of vertices to initialize
         */
        UnionFind(List<String> vertices) {
            for (String v : vertices) {
                parent.put(v, v);  // Each vertex is initially its own parent
                rank.put(v, 0);    // Rank is used to keep tree shallow
            }
        }

        /**
         * Finds the representative (root parent) of the set that contains vertex v.
         * Applies path compression to speed up future queries.
         *
         * @param v the vertex to find the root parent of
         * @return the root parent of vertex v
         */
        String find(String v) {
            operations++;
            if (!parent.get(v).equals(v)) {
                // Path compression: update parent to root parent recursively
                parent.put(v, find(parent.get(v)));
            }
            return parent.get(v);
        }

        /**
         * Unions the sets containing vertices u and v by attaching the tree with lower rank
         * under the root of the tree with higher rank to keep the structure balanced.
         *
         * @param u one vertex
         * @param v another vertex
         */
        void union(String u, String v) {
            operations++;
            String rootU = find(u);
            String rootV = find(v);
            if (!rootU.equals(rootV)) {
                // Attach smaller rank tree under root of higher rank tree
                if (rank.get(rootU) < rank.get(rootV)) {
                    parent.put(rootU, rootV);
                } else if (rank.get(rootU) > rank.get(rootV)) {
                    parent.put(rootV, rootU);
                } else {
                    parent.put(rootV, rootU);
                    rank.put(rootU, rank.get(rootU) + 1);
                }
            }
        }
    }
}
