package org.example;

import java.util.*;

/**
 * Represents the result of executing an MST algorithm (Prim or Kruskal).
 * Stores the minimum spanning tree edges, total cost, number of operations,
 * and execution time in milliseconds.
 */
public class Result {

    public List<Edge> mstEdges = new ArrayList<>();    // List of edges included in the minimum spanning tree.
    public int totalCost = 0;    // Total cost of the minimum spanning tree.
    public int operations = 0;    // Number of operations performed during the algorithm execution.
    public double executionTimeMs = 0;    // Execution time of the algorithm in milliseconds.
}