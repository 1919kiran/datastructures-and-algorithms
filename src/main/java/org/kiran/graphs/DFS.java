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
    public ArrayList<Integer> traverseDfs(List<List> graph, int V) {
        boolean[] visited = new boolean[V];
        visited[0] = true;
        ArrayList<Integer> result = new ArrayList<>();
        return dfs(0, graph, visited, result);
    }
}