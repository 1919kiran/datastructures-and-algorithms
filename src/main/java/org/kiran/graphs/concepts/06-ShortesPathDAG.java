/**
 * Algo:
 * 1. Perform topo sort on the DAG to get an order
 * 2. Take the nodes out of stack and relax the edges:
 *      - Now let's create a dist array to calculate the distance from src node to the individual node
 *      - 
 * 
 * Intuition: 
 * In the topo sort order, the order deretmines a sequence of reachability.. i.e, d[2] can be reached by d[0] -> d[1] 
 * so as we move forward we will have more ways to reach the node and we can pick the shortest path at each step greedily.
 * if you process nodes in a topological order, by the time you reach a node, all edges that point into it come from 
 * nodes earlier in the order
 */

// define the class that contains our methods
class Solution {

    // define a tiny edge class to hold neighbor and weight
    static class Edge {
        int v;          // store the destination node
        int wt;         // store the weight of the edge
        Edge(int v, int wt) {
            this.v = v; // set destination
            this.wt = wt; // set weight
        }
    }

    // public method to compute shortest paths in a DAG from a source
    public int[] shortestPathDAG(int n, int[][] edges, int src) {
        // create an adjacency list for the graph
        List<List<Edge>> adj = new ArrayList<>(); // list of lists to store edges
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>()); // initialize each adjacency bucket
        }

        // fill the adjacency list from the edges array
        for (int[] e : edges) {
            int u = e[0];          // read the from node
            int v = e[1];          // read the to node
            int w = e[2];          // read the weight
            adj.get(u).add(new Edge(v, w)); // add directed edge u -> v with weight w
        }

        // prepare structures for topo sort via dfs
        boolean[] vis = new boolean[n];     // visited array for dfs
        Deque<Integer> stack = new ArrayDeque<>(); // stack to store topo order in reverse

        // run dfs from every node to ensure full coverage (graph might be disconnected)
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {               // if this node is not visited
                dfsTopo(i, adj, vis, stack); // run dfs to fill the stack
            }
        }

        // prepare distance array and initialize with "infinity"
        int[] dist = new int[n];                     // distances from src
        Arrays.fill(dist, Integer.MAX_VALUE);        // set all to max to mean unreachable
        if (src >= 0 && src < n) {                   // guard the source index
            dist[src] = 0;                           // distance to source is zero
        }

        // process nodes in topological order by popping the stack
        while (!stack.isEmpty()) {                   // while there are nodes in order
            int u = stack.pop();                     // get next node in topo order
            if (dist[u] == Integer.MAX_VALUE) {      // if u is not reachable
                continue;                            // skip relaxing its outgoing edges
            }
            for (Edge e : adj.get(u)) {              // iterate over outgoing edges from u
                int v = e.v;                         // neighbor node
                int w = e.wt;                        // weight of edge u->v
                if (dist[u] + w < dist[v]) {         // check if going through u improves v
                    dist[v] = dist[u] + w;           // relax the edge and update distance
                }
            }
        }

        // convert unreachable markers to -1 for cleaner output
        for (int i = 0; i < n; i++) {                // loop through all nodes
            if (dist[i] == Integer.MAX_VALUE) {      // if still infinity
                dist[i] = -1;                        // mark unreachable as -1
            }
        }

        // return the final distance array
        return dist;                                  // give back the shortest path results
    }

    // helper method: dfs to build topological order
    private void dfsTopo(int u, List<List<Edge>> adj, boolean[] vis, Deque<Integer> stack) {
        vis[u] = true;                                // mark current node as visited
        for (Edge e : adj.get(u)) {                   // visit all neighbors
            int v = e.v;                              // get neighbor index
            if (!vis[v]) {                            // if neighbor not visited
                dfsTopo(v, adj, vis, stack);          // recurse on neighbor
            }
        }
        stack.push(u);                                // push node after exploring children
    }
}
