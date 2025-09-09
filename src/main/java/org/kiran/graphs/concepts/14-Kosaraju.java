/**
 * Algorithm:
 * 1. Sort the nodes in the order of finishing time from a starting node (similar to topo sort but here there can be cycles) and keep it in a stack.
 * 2. Reverse/transpose the graph by flipping all the edges on the graph
 * 3. Keep popping nodes from stack - each pop starts a dfs on the transposed graph, and that dfs exactly recovers one scc
 * 
 * Intuition:
 * Why sort by finishing time - because we can deal with source SCC first and tail SCC at last
 * Why DFS - because once reverse we have to start again from the same source node from where we performed the sort
 * Why reverse - because in an SCC directionality does not matter.. only differentiating bit in two SCC is if SCC1 -> SCC2 then in transpose graph SCC1 !-> SCC2
 */

class Solution {

    // public method to compute strongly connected components using Kosaraju
    public List<List<Integer>> kosarajuSCC(int n, int[][] edges) {
        // create adjacency list for the original directed graph
        List<List<Integer>> adj = new ArrayList<>();            // outer list for neighbors of each node
        for (int i = 0; i < n; i++) {                           // loop over all nodes
            adj.add(new ArrayList<>());                         // init an empty neighbor list
        }

        // fill the adjacency list from the input edge list
        for (int[] e : edges) {                                 // iterate every edge (u, v)
            int u = e[0];                                       // extract from-node
            int v = e[1];                                       // extract to-node
            if (u >= 0 && u < n) {                              // guard bounds for u
                adj.get(u).add(v);                              // add directed edge u -> v
            }
        }

        // first pass: dfs on original graph to get nodes ordered by finish time
        boolean[] vis = new boolean[n];                         // visited array for first dfs
        Deque<Integer> stack = new ArrayDeque<>();              // stack to store nodes by finish order
        for (int i = 0; i < n; i++) {                           // try starting dfs from every node
            if (!vis[i]) {                                      // if this node not visited yet
                dfsOrder(i, adj, vis, stack);                   // run dfs that pushes to stack on finish
            }
        }

        // build the transpose (reverse) graph
        List<List<Integer>> radj = new ArrayList<>();           // adjacency for reversed edges
        for (int i = 0; i < n; i++) {                           // loop over all nodes
            radj.add(new ArrayList<>());                        // init neighbor bucket for reversed graph
        }
        for (int u = 0; u < n; u++) {                           // walk through original adjacency
            for (int v : adj.get(u)) {                          // for each edge u -> v
                radj.get(v).add(u);                             // add reversed edge v -> u to transpose
            }
        }

        // second pass: pop nodes from stack and dfs on transpose to collect components
        Arrays.fill(vis, false);                                // reset visited for the second dfs
        List<List<Integer>> components = new ArrayList<>();     // list to store all sccs
        while (!stack.isEmpty()) {                              // process nodes in decreasing finish time
            int u = stack.pop();                                // get next node from the stack
            if (!vis[u]) {                                      // if not visited in transpose yet
                List<Integer> comp = new ArrayList<>();         // list to collect one component
                dfsCollect(u, radj, vis, comp);                 // dfs on transpose to gather this scc
                components.add(comp);                           // save this scc to answer
            }
        }

        // return the list of strongly connected components
        return components;                                      // done
    }

    // helper dfs to fill stack by finish time on the original graph
    private void dfsOrder(int u, List<List<Integer>> adj, boolean[] vis, Deque<Integer> stack) {
        vis[u] = true;                                          // mark current node as visited
        for (int v : adj.get(u)) {                              // iterate over all neighbors v of u
            if (!vis[v]) {                                      // if neighbor not visited yet
                dfsOrder(v, adj, vis, stack);                   // recurse to explore deeper
            }
        }
        stack.push(u);                                          // push node onto stack after exploring children
    }

    // helper dfs to collect one component on the transpose graph
    private void dfsCollect(int u, List<List<Integer>> radj, boolean[] vis, List<Integer> comp) {
        vis[u] = true;                                          // mark node as visited in transpose
        comp.add(u);                                            // add node to the current component
        for (int v : radj.get(u)) {                             // iterate over reversed neighbors
            if (!vis[v]) {                                      // if neighbor not yet visited
                dfsCollect(v, radj, vis, comp);                 // continue dfs to gather all nodes in this scc
            }
        }
    }
}
