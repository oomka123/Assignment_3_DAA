package org.example;

import java.util.Map;

/**
 * Represents the output result for a single graph, including MST results
 * obtained from both Prim's and Kruskal's algorithms, as well as basic
 * graph statistics and a summary comparing the two algorithms.
 */
public class OutputGraph {

    private int graph_id;
    private Map<String, Integer> input_stats;
    private Result prim;
    private Result kruskal;
    private String comparison_summary;

    /**
     * Constructs an OutputGraph object to store the results of Prim's and Kruskal's algorithms
     * for a specific graph, as well as input statistics and a comparison summary.
     *
     * @param graph_id the unique identifier for the graph
     * @param input_stats a map containing statistics about the input graph (e.g., node and edge counts)
     * @param prim the result of running Prim's algorithm on the graph
     * @param kruskal the result of running Kruskal's algorithm on the graph
     */
    public OutputGraph(int graph_id, Map<String, Integer> input_stats, Result prim, Result kruskal) {
        this.graph_id = graph_id;
        this.input_stats = input_stats;
        this.prim = prim;
        this.kruskal = kruskal;
    }

    public String getComparison_summary() {
        return comparison_summary;
    }

    public void setComparison_summary(String comparison_summary) {
        this.comparison_summary = comparison_summary;
    }

    public int getGraph_id() {
        return graph_id;
    }

    public void setGraph_id(int graph_id) {
        this.graph_id = graph_id;
    }

    public Map<String, Integer> getInput_stats() {
        return input_stats;
    }

    public void setInput_stats(Map<String, Integer> input_stats) {
        this.input_stats = input_stats;
    }

    public Result getKruskal() {
        return kruskal;
    }

    public void setKruskal(Result kruskal) {
        this.kruskal = kruskal;
    }

    public Result getPrim() {
        return prim;
    }

    public void setPrim(Result prim) {
        this.prim = prim;
    }
}