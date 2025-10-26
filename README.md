# Analytical Report — Optimization of a City Transportation Network #

## 1. Summary of Input Data and Algorithm Results ##

The experiment applied Prim’s and Kruskal’s algorithms to several graphs of different sizes (from 3 to 10 vertices).
Each graph was modeled as a weighted undirected graph where vertices represent city districts and edge weights represent road construction costs.

For every graph, the following metrics were recorded:
- Total MST cost – identical for both algorithms (correctness verified);
- Execution time (ms) – measured with System.nanoTime();
- Number of operations – counted comparisons, unions, and key algorithmic actions.

Example results (based on output.json):

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

In all cases, both algorithms produced the same MST total cost, confirming correctness.

---

## 2. Comparison of Prim’s and Kruskal’s Algorithms ##

### Efficiency
1. Prim’s Algorithm
- Complexity: O(E log V) with a priority queue.
- Works efficiently with dense graphs where many edges exist.
- Requires an adjacency list or matrix for best performance.
2. Kruskal’s Algorithm
- Complexity: O(E log E) due to sorting all edges.
- Performs better on sparse graphs where sorting is not expensive.
- Simple to implement using edge lists and a Union-Find structure.

### Performance (based on results)
- Execution times were almost identical on small graphs (<1 ms).
- Kruskal often performed slightly faster due to smaller graph sizes.
- Operation counts were higher for Kruskal because it includes many find() and union() operations for cycle detection.

---

## 3. Conclusions ## 
1. Correctness:
- Both algorithms consistently produced the same MST cost.
2. Efficiency:
- For dense graphs, Prim’s algorithm is generally preferable.
- For sparse graphs, Kruskal’s algorithm performs better.
3. Implementation complexity:
- Kruskal’s algorithm is easier to implement since it mainly involves sorting edges and using Union-Find, while Prim’s requires a more complex priority queue structure.
4. Practical recommendation:
- Use Kruskal for small or sparse datasets represented as edge lists.
- Use Prim for large, dense graphs with adjacency structures.
