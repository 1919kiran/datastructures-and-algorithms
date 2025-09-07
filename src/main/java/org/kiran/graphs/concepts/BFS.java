class Solution {
    // If starting node is 0
    public ArrayList<Integer> bfs(int V, List<List<Integer>> graph) {
        ArrayList<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[V];
        Queue<Integer> q = new ArrayDeque<>();

        q.add(0)
        visited[0] = true;

        while(!q.empty()) {
            Integer i = q.poll();
            for(Integer neighbor: graph.get(i)) {
                if(!visited[neighbor]) {
                    q.add(neighbor);
                    visited[neighbor] = true;
                }
            }
        }

        return result;
    }

    // If starting node is not mentioned
    public ArrayList<Integer> bfs(int V, List<List<Integer>> graph) {
        ArrayList<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[V];
        
        for(int i=0; i<V; i++) {
            if(visited[V]) continue;
            Queue<Integer> q = new ArrayDeque<>();
            q.add(i);
            visited[i] = true;
            while(!q.empty()) {
                Integer i = q.poll();
                for(Integer neighbor: graph.get(i)) {
                    if(!visited[neighbor]) {
                        q.add(neighbor);
                        visited[neighbor] = true;
                    }
                }
            }
        }

        return result;
    }
}