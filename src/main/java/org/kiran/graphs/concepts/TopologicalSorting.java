class Solution{
    /**
     * Def: In the adj list for any u -> v then any liear ordering that has u appearing before v is a valid topolocial ordering
     * It is only valid fro directed acyclic graphs
     * Intuition: We just store the ordering in the same way it was travelled
     * The topological ordering is preserved by the DFS path so if we have the DFS path we have the topo ordering.
     * Since DFS is recursive/LIFO in nature we use a stack
     */
    public List<Integer> topoSortDfs(int V, List<List<Integer>> graph) {
        boolean[] visited = new boolean[V];
        Stack<Integer> stk = new Stack<>();
        for(int i=0, i<V, i++) {
            if(!visited[i]) {
                dfs(i, graph, stk);
            }
        }
        List<Integer> res = new ArrayList<>();
        while(!stk.isEmpty()) {
            res.add(stk.pop());
        }
        return res;
    }

    private void dfs(int node, List<List<Integer>> graph, List<Integer> stk) {
        visited[node] = true;
        for(Integer child: grapgh.get(node)) {
            if(!visited[child]) {
                dfs(child, graph, stk);
            }
        }
        stk.push(node);
    }

    /**
     * Intuition: In similar way we preserve the ordeing by using a Queue
     * 1. Compute indegree for all nodes - there will be minimum of 1 node with indegree 0.. all these nodes must appear before nodes with indegree > 0.
     * 2. Queue the nodes with indegree = 0
     * 3. Take from a node from queue (remember a queued node's ordering is already done so we are now gonna deal with it's children), and reduce the indegree of it's children (hypothetically remove the edge) - so that when indegree of any of the neighbor becomes 0, we can fix it's ordering too by queing them.. 
     * 4. Repat until queue is empty
     */
    public List<Integer> topoSortBfs(int V, List<List<Integer>> graph) {
        int[] indegree = new int[V];
        List<Integer> topo = new ArrayList<>();
        for(int i=0; i<V; i++) {
            for(Integer node: graph.get(i)) {
                indegree[node]++;
            }
        }
        Queue<Integer> q = new LinkedList<>();
        for(int i=0; i<V; i++) {
            if(indegree[i] == 0){
                q.add(i);
            }
        }

        while(!q.isEmpty()) {
            int node = q.poll();
            for(Integer child: graph.get(node)) {
                indegree[child]--;
                if(indegree[child] == 0) {
                    q.add(child);
                }
            }
        }
        return topo;
    }

}