class Solution {
    public void dfs(int node, List<List<Integer>> graph, boolean[] visited, List<Integer> res) {
        visited[node] = true;                  // mark current node
        res.add(node);                         // record current node in traversal
        for (Integer neighbor : graph.get(node)) { // explore neighbors
            if (!visited[neighbor]) {          // only recurse if not seen
                dfs(neighbor, graph, visited, res);
            }
        }
    }
    
    // traverse whole graph without assuming start node = 0
    public ArrayList<Integer> traverseDfsFromZero(List<List<Integer>> graph, int V) {
        boolean[] visited = new boolean[V];    // all false initially
        ArrayList<Integer> result = new ArrayList<>();
        if (V == 0) return result;             // guard empty graph
        dfs(0, graph, visited, result);        // helper will mark and traverse
        return result;
    }

    // traverse whole graph without assuming start node
    // can be used when graph is disconnected
    public ArrayList<Integer> traverseDfsAllComponents(List<List<Integer>> graph, int V) {
        boolean[] visited = new boolean[V];    // visited array
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < V; i++) {          // try every node as a start
            if (!visited[i]) {                 // if its component not seen yet
                dfs(i, graph, visited, result);
            }
        }
        return result;
    }

    // DFS implementation using stack
    public ArrayList<Integer> dfsIterativeAllComponents(int V, List<List<Integer>> graph) {
        ArrayList<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[V];
        Deque<Integer> stk = new ArrayDeque<>();

        for (int s = 0; s < V; s++) {
            if (visited[s]) continue;
            stk.push(s);
            visited[s] = true;

            while (!stk.isEmpty()) {
                int u = stk.pop();
                result.add(u);
                for (Integer neighbor : graph.get(u)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        stk.push(neighbor);
                    }
                }
            }
        }
        return result;
    }

}