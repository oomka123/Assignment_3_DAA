package org.example;

import java.util.*;

public class MSTComparison {

    public static OutputGraph compareMSTs(GraphData graph) {

        if (!graph.validateEdges()) {
            throw new IllegalArgumentException("Graph " + graph.getId() + " contains invalid edges (non-existent vertices).");
        }

        if (graph.hasDuplicateEdges()) {
            System.out.println("Warning: Graph " + graph.getId() + " contains duplicate edges.");
        }

        Prim primAlgorithm = new Prim();
        Kruskal kruskalAlgorithm = new Kruskal();

        Result primResult = primAlgorithm.runPrim(graph.getNodes(), convertEdges(graph.getEdges()));
        Result kruskalResult = kruskalAlgorithm.runKruskal(graph.getNodes(), convertEdges(graph.getEdges()));

        OutputGraph result = new OutputGraph(graph.getId(), new HashMap<>(), primResult, kruskalResult);
        result.getInput_stats().put("vertices", graph.getNodes().size());
        result.getInput_stats().put("edges", graph.getEdges().size());

        if (primResult.totalCost == kruskalResult.totalCost) {
            result.setComparison_summary("MST total cost is identical (" + primResult.totalCost + "). Structures may differ.");
        } else {
            result.setComparison_summary("MST costs differ: Prim = " + primResult.totalCost + ", Kruskal = " + kruskalResult.totalCost);
        }

        return result;
    }

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
