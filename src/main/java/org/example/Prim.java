package org.example;

import java.util.*;

/**
 * Implements Prim’s algorithm for finding the Minimum Spanning Tree (MST)
 * in a weighted undirected graph. It starts from an arbitrary node and
 * grows the MST by repeatedly adding the smallest edge connecting the tree
 * to a vertex not yet included.
 */
public class Prim {

    /**
     * Runs Prim's algorithm on the given graph defined by nodes and edges.
     * It constructs the MST and records the total cost, operations performed,
     * and execution time.
     *
     * @param nodes list of node identifiers in the graph
     * @param edges list of edges representing connections between nodes with weights
     * @return a Result object containing the MST edges, total cost, operations count, and execution time
     */
    public Result runPrim(List<String> nodes, List<Edge> edges) {
        Result result = new Result();
        long startTime = System.nanoTime();

        // Build adjacency list from nodes and edges for quick access to neighbors
        Map<String, List<Edge>> adjList = buildAdjacencyList(nodes, edges);

        Set<String> visited = new HashSet<>();
        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        int operations = 0;

        // Priority queue to select edge with minimum weight at each step
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        // Select an arbitrary start node to begin the MST
        String startNode = selectStartNode(nodes);
        visited.add(startNode);
        // Add all edges from the start node to the priority queue
        pq.addAll(adjList.get(startNode));

        // Continue until all nodes are visited or no edges remain
        while (!pq.isEmpty() && visited.size() < nodes.size()) {
            Edge current = pq.poll();
            operations++;

            // Skip edges leading to already visited nodes to avoid cycles
            if (visited.contains(current.getTo())) continue;

            // Process the selected edge and update MST and related data
            totalCost = processEdge(pq, visited, current, mstEdges, adjList, totalCost);
        }

        long endTime = System.nanoTime();
        // Record the results including MST edges, total cost, operations, and execution time
        recordResults(result, mstEdges, totalCost, operations, startTime, endTime);

        return result;
    }

    /**
     * Builds an adjacency list representation of the graph from the given nodes and edges.
     * Each node maps to a list of edges originating from it.
     *
     * @param nodes list of node identifiers
     * @param edges list of edges connecting nodes
     * @return a map where keys are node identifiers and values are lists of adjacent edges
     */
    private Map<String, List<Edge>> buildAdjacencyList(List<String> nodes, List<Edge> edges) {
        Map<String, List<Edge>> adjList = new HashMap<>();

        // Initialize adjacency list with empty lists for each node
        for (String node : nodes) {
            adjList.put(node, new ArrayList<>());
        }

        // For each edge, add it to the adjacency lists of both connected nodes (undirected graph)
        for (Edge e : edges) {
            adjList.get(e.getFrom()).add(new Edge(e.getFrom(), e.getTo(), e.getWeight()));
            adjList.get(e.getTo()).add(new Edge(e.getTo(), e.getFrom(), e.getWeight()));
        }
        return adjList;
    }

    /**
     * Selects the starting node for Prim's algorithm.
     * Chooses the first node in the list or returns null if the list is empty.
     *
     * @param nodes list of node identifiers
     * @return the selected start node or null if nodes list is empty
     */
    private String selectStartNode(List<String> nodes) {
        return nodes.isEmpty() ? null : nodes.get(0);
    }

    /**
     * Processes the current edge by adding it to the MST if valid,
     * updating the total cost, marking the new node as visited,
     * and adding its adjacent edges to the priority queue.
     *
     * @param pq priority queue holding candidate edges for MST expansion
     * @param visited set of nodes already included in MST
     * @param current the edge currently being processed
     * @param mstEdges list of edges included in the MST so far
     * @param adjList adjacency list representation of the graph
     * @param totalCost current total cost of MST edges
     * @return updated total cost after processing the current edge
     */
    private int processEdge(PriorityQueue<Edge> pq, Set<String> visited, Edge current,
                            List<Edge> mstEdges, Map<String, List<Edge>> adjList, int totalCost) {

        // Verify the edge connects the expected nodes (defensive check)
        if (!Edge.connects(current, current.getFrom(), current.getTo())) {
            return totalCost;
        }

        // Add current edge to MST and update total cost
        mstEdges.add(current);
        totalCost += current.getWeight();

        // Mark the new node as visited
        visited.add(current.getTo());

        // Add all edges from the newly visited node that lead to unvisited nodes
        for (Edge next : adjList.get(current.getTo())) {
            if (!visited.contains(next.getTo()) && Edge.connects(next, next.getFrom(), next.getTo())) {
                pq.add(next);
            }
        }
        return totalCost;
    }

    /**
     * Records the results of the Prim's algorithm execution into the provided Result object.
     *
     * @param result the Result object to store the MST data and metrics
     * @param mstEdges list of edges included in the MST
     * @param totalCost total cost of all MST edges
     * @param operations number of operations performed during the algorithm
     * @param startTime start time in nanoseconds when the algorithm began
     * @param endTime end time in nanoseconds when the algorithm finished
     */
    private void recordResults(Result result, List<Edge> mstEdges, int totalCost, int operations,
                               long startTime, long endTime) {
        result.mstEdges = mstEdges;
        result.totalCost = totalCost;
        result.operations = operations;

        // Calculate execution time in milliseconds
        result.executionTimeMs = (endTime - startTime) / 1_000_000.0;
    }
}