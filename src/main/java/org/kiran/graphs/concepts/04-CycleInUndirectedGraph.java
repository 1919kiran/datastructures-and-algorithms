// helper class to store node and its parent
class Pair {
    int node;   // current node
    int parent; // parent node in BFS tree
    Pair(int node, int parent) {
        this.node = node;
        this.parent = parent;
    }
}

class Solution {
    // method to detect cycle
    public boolean isCycle(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] visited = new boolean[V];  // visited array
        
        // check each component
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                if (bfsCheck(i, adj, visited)) return true;
            }
        }
        return false;
    }
    
    /**
     * Intuition: If at the same level, we see that a node is already visited then it means there is a cycle
     * This is possible is we can distiguish a node that is visited in previous level vs same level
     * We can do this if we store parent node of each node in the queue.
     */
    private boolean bfsCheck(int start, ArrayList<ArrayList<Integer>> adj, boolean[] visited) {
        Queue<Pair> q = new ArrayDeque<>();
        
        visited[start] = true;       // mark start visited
        q.add(new Pair(start, -1));  // push start with parent = -1
        
        while (!q.isEmpty()) {
            Pair p = q.poll();
            int node = p.node;
            int parent = p.parent;
            
            // check neighbors
            for (Integer neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;           // mark visited
                    q.add(new Pair(neighbor, node));    // push with parent
                } else if (visited[neighbor] && neighbor != parent) { // just for understanding
                    // visited but not parent means cycle
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Intuition: if during recursion we reach a neighbor that is already visited and that neighbor is not the parent 
     * of the current node, then we found a cycle
     * 
     */
    private boolean dfsCheck(int node, int parent, ArrayList<ArrayList<Integer>> adj, boolean[] visited) {
        vistited[node] = true;

        // go through neighbors
        for (Integer neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                // recurse on unvisited neighbor
                if (dfsCheck(neighbor, node, visited, adj)) return true;
            } else if (neighbor != parent) {
                // if visited and not parent, cycle found
                return true;
            }
        }
        return false;
    }

}
