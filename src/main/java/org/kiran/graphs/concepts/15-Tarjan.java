/**
 * 
 * Algorithm:
 * 1. We run a DFS to keep two numbers for any node - discoveryTime and lowestDiscoveryTime
 *  - discoveryTime: Time/steps taken in the dfs to reach tht node
 *  - lowestDiscoveryTime: Minimum discovery time of all it's adjacent nodes excpet parent
 * 
 * Why we need lowestDiscoveryTime? - So within a DFS path, let's say we reached the end of the call. Now we backtrack, while backtracking we check '
 * if the edge connecting this node and parent is a bridge or not.. if we take the minimum discoveryTime among all the nodes adjacent nodes
 * and compare it with the discoveryTime of it's parent - it means that in the same DFS path, the adjacent node was reached before the parent node
 * So even if the connection b/w this node and parent node is broken we can still reach the node.
 * but if the lowestDiscoverytime is greater than discoveryTime of parent, that means adjacent nodes cannot be visited before parent so if connection
 * is broken, the nodes becomes disconnected.
 * 
 */
class Solution {
    // a timer to assign increasing discovery times to nodes
    private int timer = 1; // start from 1 just to keep 0 as "unset" if we want

    // depth-first search that fills discoveryTime/lowestDiscoveryTime and collects bridges
    private void dfs(int node, int parent,
                     int[] vis,                             // visited marker: 0 = not visited, 1 = visited
                     ArrayList<ArrayList<Integer>> adj,     // adjacency list (undirected)
                     int[] discoveryTime,                   // discovery time of each node
                     int[] lowestDiscoveryTime,             // lowest discovery time reachable from node
                     List<List<Integer>> bridges) { // output list of bridges
        vis[node] = 1;                 // mark this node as visited
        discoveryTime[node] = lowestDiscoveryTime[node] = timer; // set discovery and lowestDiscoveryTime to current timer
        timer++;                       // increment timer for next node

        for (Integer it : adj.get(node)) {     // iterate over all neighbors of node
            if (it == parent) continue;        // skip the edge back to parent (undirected graph)

            if (vis[it] == 0) {                // if neighbor is not visited, it's a tree edge
                dfs(it, node, vis, adj, discoveryTime, lowestDiscoveryTime, bridges); // recurse into the neighbor

                lowestDiscoveryTime[node] = Math.min(lowestDiscoveryTime[node], lowestDiscoveryTime[it]);   // after return, update lowestDiscoveryTime-link of node

                if (lowestDiscoveryTime[it] > discoveryTime[node]) {    // if child subtree cannot reach node or ancestors
                    bridges.add(Arrays.asList(node, it)); // (node, it) is a bridge; record it
                }
            } else {
                lowestDiscoveryTime[node] = Math.min(lowestDiscoveryTime[node], discoveryTime[it]);   // back edge: update lowestDiscoveryTime using neighbor's discoveryTime
            }
        }
    }

    // public api: find all critical connections (bridges) in an undirected graph
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>(); // create adjacency list
        for (int i = 0; i < n; i++) {                         // for each node 0..n-1
            adj.add(new ArrayList<>());                       // make an empty neighbor bucket
        }

        for (List<Integer> e : connections) {                 // read each edge from input
            int u = e.get(0);                                 // first endpoint
            int v = e.get(1);                                 // second endpoint
            if (u == v) continue;                             // ignore self-loop; can't be a bridge
            adj.get(u).add(v);                                // add u -> v (undirected)
            adj.get(v).add(u);                                // add v -> u
        }

        int[] vis = new int[n];                               // visited array
        int[] discoveryTime = new int[n];                     // discovery time array
        int[] lowestDiscoveryTime = new int[n];               // lowestDiscoveryTime array
        List<List<Integer>> bridges = new ArrayList<>();      // answer list for bridges

        for (int i = 0; i < n; i++) {                         // graph might be disconnected
            if (vis[i] == 0) {                                // if this component not explored yet
                dfs(i, -1, vis, adj, discoveryTime, lowestDiscoveryTime, bridges);      // run dfs with parent = -1
            }
        }

        return bridges;                                       // return all critical connections
    }
}
