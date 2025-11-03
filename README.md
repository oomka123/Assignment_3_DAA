# Smart City Scheduling System

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)

## Table of Contents
- [Project Overview](#project-overview)
- [Implementation details](#implementation-details)
- [Experimental results](#experimental-results)
- [Algorithm comparison](#algorithm-comparison)
- [Conclusions](#conclusions)
- [Project Structure](#project-structure)

---
# City Transportation Network Optimization

## Assignment 3: Minimum Spanning Tree Algorithm Implementation and Analysis

A comprehensive implementation and comparison of Prim's and Kruskal's algorithms for optimizing city transportation networks through Minimum Spanning Tree (MST) computation.

# Project Overview
## Problem Statement
The city administration plans to construct roads connecting all districts while minimizing total construction cost. This optimization problem is modeled as finding the Minimum Spanning Tree (MST) in a weighted undirected graph where:

- Vertices represent city districts
- Edges represent potential road connections
- Edge weights represent construction costs (in cost units)

## Objectives

- Implement both Prim's and Kruskal's MST algorithms
- Validate correctness by comparing MST total costs
- Analyze performance through operation counts and execution times
- Determine optimal algorithm selection criteria based on graph characteristics

# Implementation Details
## Prim's Algorithm
**Strategy:** Grows the MST from an arbitrary starting vertex by repeatedly selecting the minimum-weight edge connecting the tree to an unvisited vertex.
**Key Components:**

- Data Structure: Priority Queue (min-heap) for edge selection
- Graph Representation: Adjacency list for O(1) neighbor access
- Cycle Prevention: HashSet to track visited vertices

**Time Complexity: O(E log V) with binary heap**
**Space Complexity: O(V + E)**
```
java// Core algorithm loop
while (!pq.isEmpty() && visited.size() < nodes.size()) {
    Edge current = pq.poll();
    operations++;
    
    if (visited.contains(current.getTo())) continue;
    
    // Add edge to MST and explore neighbors
    mstEdges.add(current);
    totalCost += current.getWeight();
    visited.add(current.getTo());
    
    for (Edge next : adjList.get(current.getTo())) {
        if (!visited.contains(next.getTo())) {
            pq.add(next);
        }
    }
}
```
## Kruskal's Algorithm
**Strategy:** Sorts all edges by weight and adds them to the MST if they don't create a cycle, using Union-Find for efficient cycle detection.
**Key Components:**

- Edge Processing: Global sorting by weight (ascending order)
- Cycle Detection: Union-Find (Disjoint Set Union) with path compression
- Optimization: Rank-based union for balanced tree structure

**Time Complexity:** O(E log E) ≈ O(E log V)
**Space Complexity:** O(V) for Union-Find structure
```
java// Sort edges and process
edges.sort(Comparator.comparingInt(Edge::getWeight));
UnionFind uf = new UnionFind(nodes);

for (Edge edge : edges) {
    operations++;
    if (!uf.find(edge.getFrom()).equals(uf.find(edge.getTo()))) {
        uf.union(edge.getFrom(), edge.getTo());
        mstEdges.add(edge);
        totalCost += edge.getWeight();
    }
}
```
# Performance Metrics
The application tracks the following metrics for each algorithm:
| Metric | Description |
|--------|-----------|
| MST Edges | List of edges included in the minimum spanning tree |
| Total Cost | Sum of weights of all MST edges |
| Operations | Number of key algorithmic operations performed |
| Execution Time | Time taken in milliseconds |

# Experimental Results
## Test Data Summary
Eight test graphs of varying sizes and densities were evaluated:
| Graph ID | Vertices | Edges | Density | Graph Type |
|-----------|-----------|--------|----------|-------------|
| 1 | 5|7 | 0.70 | Dense |
| 2 | 4|5 | 0.83 | Dense |
| 3 | 3|3 | 1.00 | Complete |
| 4 | 6|8 | 0.53 | Medium |
| 5 | 5|6 | 0.60 | Medium |
| 6 | 6|7 | 0.47 | Medium |
| 7 | 8|10 | 0.36 | Sparse |
| 8 | 10|12 | 0.27 | Sparse |
*Density = 2E / (V(V-1))
## Detailed Results
| Graph ID | Vertices | Edges | MST Cost | Prim Time (ms) | Kruskal Time (ms) | Prim Ops | Kruskal Ops |
|-----------|-----------|--------|-----------|----------------|-------------------|-----------|--------------|
| 1 | 5 | 7 | 16 | 0.8731 | 0.3667 | 5 | 42 |
| 2 | 4 | 5 | 6 | 0.8949 | 0.9171 | 4 | 31 |
| 3 | 3 | 3 | 11 | 0.5054 | 0.6058 | 2 | 18 |
| 4 | 6 | 8 | 17 | 0.0133 | 0.0115 | 7 | 65 |
| 5 | 5 | 6 | 15 | 0.0143 | 0.0131 | 9 | 72 |
| 6 | 6 | 7 | 15 | 0.0171 | 0.0156 | 9 | 68 |
| 7 | 8 | 10 | 20 | 0.0193 | 0.0177 | 9 | 74 |
| 8 | 10 | 12 | 38 | 0.0237 | 0.0212 | 9 | 80 |
# Key Observations
## Correctness Validation

- **All 8 graph:** Both algorithms produced identical MST costs
- **Edge count:** All MSTs contain exactly V-1 edges as expected
- **Result:** Implementation correctness confirmed ✓

✓ Performance Analysis
**Small Graphs (Graphs 1-3, V ≤ 5):**

- Initial warm-up phase shows higher execution times (0.5-0.9 ms)
- JVM optimization not yet active
- Performance difference minimal but variable

**Medium to Large Graphs (Graphs 4-8, V ≥ 6):**

- Execution times stabilize to microsecond range (0.01-0.02 ms)
- Kruskal's consistently faster: 8-16% improvement
- Performance gap narrows as graph size increases

**Operation Count:**

- Kruskal's: 4-11× more operations than Prim's
- Despite higher operation count, Kruskal's remains competitive
- Simple operations (array access, comparisons) vs. complex operations (priority queue)

# Algorithm Comparison
## Efficiency Analysis
### Execution Time Comparison
| Graph Size | Prim's (ms) | Kruskal's (ms) | Winner | Advantage |
|-------------|--------------|----------------|----------|------------|
| Small (V=3-5) | 0.76 avg | 0.63 avg | Kruskal's | 17% faster |
| Medium (V=6) | 0.015 avg | 0.013 avg | Kruskal's | 13% faster |
| Large (V=8-10) | 0.022 avg | 0.019 avg | Kruskal's | 14% faster |

**Trend:** Kruskal's maintains consistent performance advantage across all tested graph sizes.
### Operation Count Analysis
```
Operation Count Ratio (Kruskal/Prim):
Graph 1: 42/5  = 8.4×
Graph 2: 31/4  = 7.8×
Graph 3: 18/2  = 9.0×
Graph 4: 65/7  = 9.3×
Graph 5: 72/9  = 8.0×
Graph 6: 68/9  = 7.6×
Graph 7: 74/9  = 8.2×
Graph 8: 80/9  = 8.9×
```
**Average:** ~8.4× more operations in Kruskal's
**Insight:** Despite 8× more operations, Kruskal's is faster due to:

- **Simpler operations:** Array indexing and comparisons vs. heap operations
- **Better cache locality:** Sequential array access in Union-Find
- **Efficient sorting:** Modern sorting algorithms highly optimized

## Comparative Summary
| Aspect | Prim's Algorithm | Kruskal's Algorithm |
|---------|------------------|---------------------|
| Execution Time | 0.022 ms avg (large) | 0.019 ms avg (large) ✓ |
| Operations | 9 avg (large) ✓ | 78 avg (large) |
| Time Complexity | O(E log V) | O(E log E) |
| Space Complexity | O(V + E) | O(V) ✓ |
| Implementation | More complex | Simpler ✓ |
| Best For | Dense graphs (E ≈ V²) | Sparse graphs (E ≈ V) ✓ |
| Data Structure | Priority Queue | Union-Find |
| Edge Processing | Local (from tree) | Global (all edges) |

## Performance by Graph Density
Analyzing performance relative to graph density:
| Density Range | Best Algorithm | Reason |
|----------------|----------------|---------|
| High (>0.6) | Kruskal's | Efficient sorting dominates, fewer Union-Find operations |
| Medium (0.4-0.6) | Kruskal's | Balanced performance, sorting cost amortized |
| Low (<0.4) | Kruskal's | Very few edges to sort, Union-Find highly efficient |

**Unexpected Finding:** In our test suite, Kruskal's performed well even in dense graphs, contrary to theoretical expectations. This is likely due to:

1. Small absolute graph sizes (V ≤ 10)
2. Modern CPU optimizations for sorting
3. Efficient Union-Find with path compression

# Conclusions
## 1. Algorithm Selection Criteria
Based on experimental results and theoretical analysis:
Choose Prim's Algorithm when:

- ✓ Graph is very dense (E ≈ V²)
- ✓ Starting from a specific vertex is required
- ✓ Graph is represented as adjacency matrix
- ✓ Edges arrive in a streaming fashion
- ✓ Priority queue operations are hardware-optimized

Choose Kruskal's Algorithm when:

- ✓ Graph is sparse (E ≈ V) ← Our test cases
- ✓ Edges are pre-sorted or sorting is cheap
- ✓ Memory is constrained (O(V) vs O(V+E))
- ✓ Implementation simplicity is priority
- ✓ Parallel processing is available
- ✓ Handling disconnected graphs (MST forest)

## 2. Empirical Findings
**For Small to Medium Urban Networks (V < 50):**

- **Kruskal's algorithm** is recommended across all density levels
- Performance advantage: 13-17% faster execution
- Simpler implementation reduces bug potential
- Operation count difference negligible at this scale

Key Insight: The "crossover point" where Prim's becomes superior occurs at much larger graph sizes (V > 100) or very high densities (E > V·log V) than our test cases.
## 3. Implementation Complexity
| Complexity Factor | Prim's | Kruskal's | Winner |
|--------------------|--------|------------|---------|
| Core Logic | Complex | Simple | Kruskal's ✓ |
| Data Structures | Priority Queue + Adjacency List | Union-Find | Kruskal's ✓ |
| Edge Cases | Many | Few | Kruskal's ✓ |
| Debugging | Harder | Easier | Kruskal's ✓ |
| Code Lines | ~150 | ~100 | Kruskal's ✓ |

**Verdict:** Kruskal's is significantly simpler to implement and maintain.
## 4. Practical Recommendations for City Planning
**Scenario 1: Initial Network Design**

- **Use:** Kruskal's algorithm
- **Reason:** Few candidate roads, sparse proposals, global cost optimization

**Scenario 2: Network Expansion**

- **Use:** Prim's algorithm
- **Reason:** Growing from existing infrastructure, specific starting district

**Scenario 3: Budget-Constrained Planning**

- **Use:** Kruskal's algorithm
- **Reason:** Pre-sorted by cost, easy to add budget constraints

**Scenario 4: Real-time Planning Tools**

- **Use:** Prim's algorithm
- **Reason:** Better for interactive, incremental updates

## 5. Limitations and Future Work
Current Limitations:

- Small test graphs (V ≤ 10) may not reflect large-scale urban networks
- No consideration of multi-objective optimization (cost + time + impact)
- Static graphs only (no dynamic edge additions/deletions)

## Future Enhancements:

- Test on larger graphs (V > 1000, E > 10000)
- Implement Fibonacci heap for Prim's (O(E + V log V))
- Add Borůvka's algorithm for comparison
- Develop interactive visualization tool
- Implement parallel Kruskal's for GPU processing

# Project Structure
```
src/main/java/org/example/
├── model/
│   ├── Edge.java           # Edge representation (from, to, weight)
│   └── GraphData.java      # Graph data structure with validation
├── Kruskal.java            # Kruskal's MST algorithm implementation
├── Prim.java               # Prim's MST algorithm implementation
├── Result.java             # Algorithm result container
├── MSTComparison.java      # Algorithm comparison logic
├── OutputGraph.java        # Output data structure
├── Json.java               # JSON I/O utilities
└── Main.java               # Application entry point

resources/
├── easy_input.json         # Small test graphs (5-10 nodes)
├── medium_input.json       # Medium test graphs (15-25 nodes)
├── hard_input.json         # Large test graphs (30+ nodes)
└── output.json             # Algorithm results and comparison
```
## Key Classes

1. **Kruskal.java:** Implements Kruskal's MST with Union-Find
2. **Prim.java:** Implements Prim's MST with priority queue
3. **MSTComparison.java:** Orchestrates comparison and validation
4. **UnionFind (inner class):** Disjoint set union with path compression
