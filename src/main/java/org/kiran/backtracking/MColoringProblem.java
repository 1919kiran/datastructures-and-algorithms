import java.util.*; // importing utilities

// -------- Backtracking (Striver): assign with early safety check at each step --------
class MCOLOR_Backtracking {

    /**
     * Intuition: Start from the first node. Check all possible colors that can be assigned to it.. 
     * the check is that it's adjacent nodes must not have the same color.
     * If the color is possible, color the node and proceed to next node and backtrack.
     * The key here is that whenever a node color is not possible we terminate the branch.
     */
    public boolean graphColoring(int[][] graph, int m) {
        // get number of vertices
        int n = graph.length; // adjacency matrix dimension
        // quick edge case: empty graph is trivially colorable
        if (n == 0) return true; // nothing to color
        // quick edge case: no colors with vertices => impossible
        if (m == 0) return false; // cannot color any vertex
        // allocate color array initialized to 0 meaning uncolored
        int[] color = new int[n]; // holds color for each vertex
        // start backtracking from vertex 0
        return solve(0, color, m, n, graph); // attempt to color all vertices
    }

    // recursive function that tries to color vertex 'node'
    private boolean solve(int node, int[] color, int m, int n, int[][] graph) {
        // if we reached beyond the last vertex, a valid coloring is found
        if (node == n) { // all vertices processed
            return true; // success
        }
        // try all colors from 1 to m for this node
        for (int c = 1; c <= m; c++) { // iterate over available colors
            // check if assigning color c to node is safe
            if (isSafe(node, c, graph, color, n)) { // no neighbor has color c
                color[node] = c; // assign color c to this node
                if (solve(node + 1, color, m, n, graph)) { // recurse to next node
                    return true; // if downstream succeeds, propagate success
                }
                color[node] = 0; // backtrack: remove the color and try next
            }
        }
        // if no color works for this node, return false to backtrack further
        return false; // trigger backtracking
    }

    // helper to check if color c can be assigned to 'node'
    private boolean isSafe(int node, int c, int[][] graph, int[] color, int n) {
        // iterate over all potential neighbors
        for (int v = 0; v < n; v++) { // check all vertices
            if (graph[node][v] == 1) { // if node and v are adjacent
                if (color[v] == c) { // neighbor already has the same color
                    return false; // not safe to use this color
                }
            }
        }
        // if no neighbor conflict, it's safe
        return true; // safe assignment
    }
}
