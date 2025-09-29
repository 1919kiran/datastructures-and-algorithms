class Solution {
    // method to detect cycle
    public boolean isCycle(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] visited = new boolean[V];  // visited array
        boolean[] pathVisited = new boolean[V];  // visited array
        
        // check each component
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                if (dfsCheck(i, adj, visited, pathVisited)) return true;
            }
        }
        return false;
    }

    /**
     * Intuition: whether this node is part of the current DFS call stack?? if during DFS we reach a node that is already in the current recursion stack, 
     * that means we found a cycle. We can do that by maintain visited that is local to the call stack... 
     * similar to backtracking we takeout from pathvisited once we go back
     */
    private boolean dfsCheck(int node, int parent, ArrayList<ArrayList<Integer>> adj, boolean[] visited, boolean[] pathVisited) {
        vistited[node] = true;
        pathVisited[node] = true;
        // go through neighbors
        for (Integer neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                // recurse on unvisited neighbor
                if (dfsCheck(neighbor, node, visited, adj)) return true;
            } else if (pathVisited[neighbor]) {
                // path already visited in 
                return true;
            }
        }
        pathVisited[node] = false;  // backtrack removal
        return false;
    }

    /**
     * Intuition: If a valid topo ordering cannot be determined then it contains a cycle
     * So if the computed topo order does not contain all nodes, then topo sorting did not work -> not a DAG.
     * The key here is that the queue becomes empty before all nodes indegree becomes 0..
     * So if there is a cycle, then there will be a case where we hypothetically remove edge from curr node to child (indegree--) but
     *  - there are no more nodes with indegree 0 (no start points)
     *  - indegree of child node != 0 (there is some other incoming edge to child node that is forming a cycle)
     */
    private boolean dfsCheck(int node, int parent, ArrayList<ArrayList<Integer>> adj, boolean[] visited, , boolean[] pathVisited)

}
