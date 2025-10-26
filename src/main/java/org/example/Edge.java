package org.example;

/**
 * Represents an undirected weighted edge connecting two vertices in the graph.
 * Each edge has a start vertex, an end vertex, and an associated weight (cost).
 */
public class Edge {
    private String from;
    private String to;
    private int weight;

    /**
     * Constructs an Edge with specified start vertex, end vertex, and weight.
     *
     * @param from   the starting vertex of the edge
     * @param to     the ending vertex of the edge
     * @param weight the weight or cost associated with the edge
     */
    Edge(String from, String to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Edge() {}

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Determines whether the edge connects two given vertices, regardless of direction.
     *
     * @param e     the edge to check
     * @param node1 the first vertex
     * @param node2 the second vertex
     * @return true if the edge connects node1 and node2, false otherwise
     */
    public static boolean connects(Edge e, String node1, String node2) {
        // Check if edge connects node1 and node2 in either direction
        return (e.getFrom().equals(node1) && e.getTo().equals(node2)) ||
                (e.getFrom().equals(node2) && e.getTo().equals(node1));
    }
}
