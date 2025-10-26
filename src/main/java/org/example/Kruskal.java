package org.example;
import java.util.*;

public class Kruskal {

    public Result runKruskal(List<String> nodes, List<Edge> edges) {
        Result result = new Result();
        long startTime = System.nanoTime();

        edges.sort(Comparator.comparingInt(e -> e.getWeight()));
        UnionFind uf = new UnionFind(nodes);

        for (Edge edge : edges) {
            result.operations++;
            if (!uf.find(edge.getFrom()).equals(uf.find(edge.getTo()))) {
                uf.union(edge.getFrom(), edge.getTo());
                result.mstEdges.add(edge);
                result.totalCost += edge.getWeight();
            }
        }

        result.operations += uf.operations;
        long endTime = System.nanoTime();
        result.executionTimeMs = (endTime - startTime) / 1_000_000.0;

        return result;
    }

    static class UnionFind {
        private final Map<String, String> parent = new HashMap<>();
        private final Map<String, Integer> rank = new HashMap<>();
        private int operations = 0;

        UnionFind(List<String> vertices) {
            for (String v : vertices) {
                parent.put(v, v);
                rank.put(v, 0);
            }
        }

        String find(String v) {
            operations++;
            if (!parent.get(v).equals(v)) {
                parent.put(v, find(parent.get(v)));
            }
            return parent.get(v);
        }

        void union(String u, String v) {
            operations++;
            String rootU = find(u);
            String rootV = find(v);
            if (!rootU.equals(rootV)) {
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
