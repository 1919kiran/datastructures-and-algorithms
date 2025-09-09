class Solution{
    /**
     * Def: In the adj list for any u -> v then any liear ordering that has u appearing before v is a valid topolocial ordering
     * It is only valid for DAGs
     * Intuition: We just store the ordering in the same way it was travelled
     * The topological ordering is preserved by the DFS path so if we have the DFS path we have the topo ordering.
     * Since DFS is recursive/LIFO in nature we use a stack
     */
    public List<Integer> topoSortDfs(int V, List<List<Integer>> graph) {
        boolean[] visited = new boolean[V];        // track visited nodes
        Stack<Integer> stk = new Stack<>();        // stack to hold topo order
        
        for (int i = 0; i < V; i++) {              // loop over all vertices
            if (!visited[i]) {                     // if this vertex not yet visited
                dfs(i, graph, stk, visited);       // run dfs
            }
        }
        List<Integer> res = new ArrayList<>();     // to store final topo order
        
        while (!stk.isEmpty()) {                   // pop all from stack
            res.add(stk.pop());                    // and add to result
        }
        return res;                                // return topo order
    }

    private void dfs(int node, List<List<Integer>> graph, Stack<Integer> stk, boolean[] visited) {
        visited[node] = true;                      // mark this node visited
        for (Integer child : graph.get(node)) {    // explore all neighbors
            if (!visited[child]) {                 // if neighbor not yet visited
                dfs(child, graph, stk, visited);   // recurse
            }
        }
        stk.push(node);                            // after children, push this node
    }


    /**
     * Intuition: In similar way we preserve the ordeing by using a Queue
     * 1. Compute indegree for all nodes - there will be minimum of 1 node with indegree 0.. all these nodes must appear before nodes with indegree > 0.
     * 2. Queue the nodes with indegree = 0
     * 3. Take from a node from queue (remember a queued node's ordering is already done so we are now gonna deal with it's children), 
     * and reduce the indegree of it's children (hypothetically remove the edge) - so that when indegree of any of the neighbor becomes 0, 
     * we can fix it's ordering too by queing them.. 
     * 4. Repeat until queue is empty
     */
    public List<Integer> topoSortBfs(int V, List<List<Integer>> graph) {
        int[] indegree = new int[V];                 // indegree of each node
        List<Integer> topo = new ArrayList<>();      // stores the topo order we build

        // compute indegree by scanning all directed edges i -> node
        for (int i = 0; i < V; i++) {                // for every node i
            for (int node : graph.get(i)) {          // for every outgoing edge i -> node
                indegree[node]++;                    // bump indegree of the destination
            }
        }

        Deque<Integer> q = new ArrayDeque<>();       // queue for BFS (ArrayDeque is faster than LinkedList)
        for (int i = 0; i < V; i++) {                // push all zero-indegree nodes
            if (indegree[i] == 0) {                  // if no prerequisites
                q.offer(i);                          // enqueue it
            }
        }

        // standard Kahn’s BFS
        while (!q.isEmpty()) {                       // while there are ready nodes
            int u = q.poll();                        // pop one
            topo.add(u);                             // append to topo order

            for (int v : graph.get(u)) {             // for each neighbor u -> v
                indegree[v]--;                       // we've "used" edge u->v
                if (indegree[v] == 0) {              // if v has no more incoming edges
                    q.offer(v);                      // it’s ready to process next
                }
            }
        }

        // if we didn’t place all nodes, there’s a cycle (not a DAG)
        if (topo.size() != V) {                      // cycle detected
            // choose your convention; here I’ll return empty to signal “no topo order”
            return new ArrayList<>();                // or throw, or return partial per requirements
        }

        return topo;                                 // valid topological order
    }

}