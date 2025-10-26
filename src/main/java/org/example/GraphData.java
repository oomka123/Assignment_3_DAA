package org.example;

import java.util.List;

/**
 * Represents a data structure for a graph containing vertices and edges.
 * Includes methods to validate the correctness of the graph data.
 */
public class GraphData {
    private int id;
    private List<String> nodes;
    private List<Edge> edges;

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    /**
     * Validates that all edges connect existing vertices in the graph.
     *
     * @return true if all edges are valid; false if any edge contains a non-existent vertex.
     */
    public boolean validateEdges() {
        boolean isValid = true;

        // Check each edge to ensure both vertices exist in the node list
        for (Edge e : edges) {
            if (!nodes.contains(e.getFrom()) || !nodes.contains(e.getTo())) {
                System.out.println("Error: edge " + e.getFrom() + " - " + e.getTo() + " contains a non-existent vertex!");
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * Checks if there are any duplicate edges in the graph.
     *
     * @return true if duplicate edges are found; false otherwise.
     */
    public boolean hasDuplicateEdges() {

        // Compare each edge with all subsequent edges to detect duplicates
        for (int i = 0; i < edges.size(); i++) {
            for (int j = i + 1; j < edges.size(); j++) {
                if (Edge.connects(edges.get(i), edges.get(j).getFrom(), edges.get(j).getTo())) {
                    System.out.println("Warning: duplicate edge " +
                            edges.get(i).getFrom() + " - " + edges.get(i).getTo());
                    return true;
                }
            }
        }
        return false;
    }
}