class Solution {
    public ArrayList<Integer> dfs(int node, List<List> graph, boolean[] visited, List<Integer> res) {
        visited[node] = true;
        res.add(neighbor);
        if(!visited[node]) {
            for(Integer neighbor: graph.get(node)) {
                if(!visited[neighbor]){
                    dfs(neighbor, graph, visited, res);
                }
            }
        }
        return res;
    }
    
    // traverse whole graph without assuming start node = 0
    public ArrayList<Integer> traverseDfs(List<List> graph, int V) {
        boolean[] visited = new boolean[V];
        visited[0] = true;
        ArrayList<Integer> result = new ArrayList<>();
        return dfs(0, graph, visited, result);
    }

    // traverse whole graph without assuming start node
    public ArrayList<Integer> traverseDfs(List<List<Integer>> graph, int V) {
        boolean[] visited = new boolean[V];     // visited array
        ArrayList<Integer> result = new ArrayList<>(); 
        
        // loop over every node to handle disconnected components
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i, graph, visited, result);
            }
        }
        
        return result;
    }
}