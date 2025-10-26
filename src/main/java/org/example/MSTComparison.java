package org.example;

import java.util.*;

/**
 * Compares the results of Prim’s and Kruskal’s algorithms for a given graph.
 * Validates the input, runs both algorithms, and records statistics and comparison summary.
 */
public class MSTComparison {

    /**
     * Compares the results of Prim’s and Kruskal’s algorithms for a given graph.
     * Validates the input graph, runs both MST algorithms, and returns a summary of the results.
     *
     * @param graph the input graph containing nodes and edges
     * @return an OutputGraph object containing MST results and summary
     */
    public static OutputGraph compareMSTs(GraphData graph) {

        // Validate the graph edges to ensure all vertices exist
        if (!graph.validateEdges()) {
            throw new IllegalArgumentException("Graph " + graph.getId() + " contains invalid edges (non-existent vertices).");
        }

        // Warn if the graph contains duplicate edges
        if (graph.hasDuplicateEdges()) {
            System.out.println("Warning: Graph " + graph.getId() + " contains duplicate edges.");
        }

        // Run Prim's and Kruskal's MST algorithms
        Prim primAlgorithm = new Prim();
        Kruskal kruskalAlgorithm = new Kruskal();

        Result primResult = primAlgorithm.runPrim(graph.getNodes(), convertEdges(graph.getEdges()));
        Result kruskalResult = kruskalAlgorithm.runKruskal(graph.getNodes(), convertEdges(graph.getEdges()));

        // Create output object and record input statistics
        OutputGraph result = new OutputGraph(graph.getId(), new HashMap<>(), primResult, kruskalResult);
        result.getInput_stats().put("vertices", graph.getNodes().size());
        result.getInput_stats().put("edges", graph.getEdges().size());

        // Compare the total costs of the MSTs and set a summary message
        if (primResult.totalCost == kruskalResult.totalCost) {
            result.setComparison_summary("MST total cost is identical (" + primResult.totalCost + "). Structures may differ.");
        } else {
            result.setComparison_summary("MST costs differ: Prim = " + primResult.totalCost + ", Kruskal = " + kruskalResult.totalCost);
        }

        return result;
    }

    /**
     * Creates a copy of the list of edges to avoid modifying the original graph data.
     *
     * @param edges the original list of edges
     * @return a new list containing copies of the edges
     */
    private static List<Edge> convertEdges(List<Edge> edges) {
        List<Edge> converted = new ArrayList<>();
        for (Edge e : edges) {
            Edge newEdge = new Edge();
            newEdge.setFrom(e.getFrom());
            newEdge.setTo(e.getTo());
            newEdge.setWeight(e.getWeight());
            converted.add(newEdge);
        }
        return converted;
    }

}
