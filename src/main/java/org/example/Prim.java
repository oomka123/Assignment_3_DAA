package org.example;

import java.util.*;

public class Prim {

    public Result runPrim(List<String> nodes, List<Edge> edges) {
        Result result = new Result();
        long startTime = System.nanoTime();

        Map<String, List<Edge>> adjList = buildAdjacencyList(nodes, edges);

        Set<String> visited = new HashSet<>();
        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        int operations = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        String startNode = selectStartNode(nodes);
        visited.add(startNode);
        pq.addAll(adjList.get(startNode));

        while (!pq.isEmpty() && visited.size() < nodes.size()) {
            Edge current = pq.poll();
            operations++;
            if (visited.contains(current.getTo())) continue;

            totalCost = processEdge(pq, visited, current, mstEdges, adjList, totalCost);
        }

        long endTime = System.nanoTime();
        recordResults(result, mstEdges, totalCost, operations, startTime, endTime);

        return result;
    }

    private Map<String, List<Edge>> buildAdjacencyList(List<String> nodes, List<Edge> edges) {
        Map<String, List<Edge>> adjList = new HashMap<>();
        for (String node : nodes) {
            adjList.put(node, new ArrayList<>());
        }
        for (Edge e : edges) {
            adjList.get(e.getFrom()).add(new Edge(e.getFrom(), e.getTo(), e.getWeight()));
            adjList.get(e.getTo()).add(new Edge(e.getTo(), e.getFrom(), e.getWeight()));
        }
        return adjList;
    }

    private String selectStartNode(List<String> nodes) {
        return nodes.isEmpty() ? null : nodes.get(0);
    }

    private int processEdge(PriorityQueue<Edge> pq, Set<String> visited, Edge current,
                            List<Edge> mstEdges, Map<String, List<Edge>> adjList, int totalCost) {
        if (!Edge.connects(current, current.getFrom(), current.getTo())) {
            return totalCost; // если ребро не соединяет эти вершины — пропускаем
        }

        mstEdges.add(current);
        totalCost += current.getWeight();
        visited.add(current.getTo());

        for (Edge next : adjList.get(current.getTo())) {
            if (!visited.contains(next.getTo()) && Edge.connects(next, next.getFrom(), next.getTo())) {
                pq.add(next);
            }
        }
        return totalCost;
    }

    private void recordResults(Result result, List<Edge> mstEdges, int totalCost, int operations,
                               long startTime, long endTime) {
        result.mstEdges = mstEdges;
        result.totalCost = totalCost;
        result.operations = operations;
        result.executionTimeMs = (endTime - startTime) / 1_000_000.0;
    }
}