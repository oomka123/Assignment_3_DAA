package org.example;

public class Edge {
    private String from;
    private String to;
    private int weight;

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
    public static boolean connects(Edge e, String node1, String node2) {
        return (e.getFrom().equals(node1) && e.getTo().equals(node2)) ||
                (e.getFrom().equals(node2) && e.getTo().equals(node1));
    }
}
