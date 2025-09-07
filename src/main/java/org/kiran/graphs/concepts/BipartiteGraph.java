/**
 * Intuition: Linear graphs are always bipartite
 * Graphs with even cycle length are bipartite
 * we try to color the graph while traversing. start with any unvisited node, give it color 0. then all its neighbors must get color 1, their neighbors get color 0, and so on.
 * if at any point we find a neighbor with the same color as the current node, the graph is not bipartite.
 * because the graph can be disconnected, we need to repeat this process for all unvisited nodes.
 */

class Solution {
    // BFS approach to check bipartite
    public boolean isBipartiteBFS(int V, List<List<Integer>> adj) {
        int[] color = new int[V];   // 0 = uncolored, 1 and -1 are the two colors
        Arrays.fill(color, 0);

        // check every component
        for (int i = 0; i < V; i++) {
            if (color[i] == 0) {
                if (!bfsCheck(i, adj, color)) return false;
            }
        }
        return true;
    }

    private boolean bfsCheck(int start, List<List<Integer>> adj, int[] color) {
        Queue<Integer> q = new ArrayDeque<>();
        q.add(start);
        color[start] = 1; // assign first color

        while (!q.isEmpty()) {
            int node = q.poll();

            for (int neighbor : adj.get(node)) {
                if (color[neighbor] == 0) {
                    // assign opposite color
                    color[neighbor] = -color[node];
                    q.add(neighbor);
                } else if (color[neighbor] == color[node]) {
                    // conflict found
                    return false;
                }
            }
        }
        return true;
    }

    // DFS approach to check bipartite
    public boolean isBipartiteDFS(int V, List<List<Integer>> adj) {
        int[] color = new int[V];
        Arrays.fill(color, 0);

        for (int i = 0; i < V; i++) {
            if (color[i] == 0) {
                if (!dfsCheck(i, 1, adj, color)) return false;
            }
        }
        return true;
    }

    private boolean dfsCheck(int node, int col, List<List<Integer>> adj, int[] color) {
        color[node] = col; // color current node

        for (int neighbor : adj.get(node)) {
            if (color[neighbor] == 0) {
                // recursively color neighbor
                if (!dfsCheck(neighbor, -col, adj, color)) return false;
            } else if (color[neighbor] == col) {
                // neighbor has same color => not bipartite
                return false;
            }
        }
        return true;
    }
}
