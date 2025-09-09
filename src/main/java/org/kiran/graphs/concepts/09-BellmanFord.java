/**
 * Assumption: Graph is directed
 * 
 * Algo:
 * 1. Relax all the edges E-1 times
 * 
 * Intuition:
 * 
 * Why N-1:
 * Consider graph 0 -> 1 -> 2 -> 3 -> 4 
 * Edges are in the order {(3,4,x),(2,3,x),(1,2,x),(0,1,x)}
 * since the edges can be given in any order, in worst case the order of edges can be in such a way that in 1st iteration distance array all nodes seem 
 * unreachable, so in this case each iteration 1 distance is computed using the parent distance which has been computer in previous step.
 * so if we do it n-1 times, it is guaranteed that all connected nodes will be relaxed.
 * 
 * How to detect negative cycles:
 * On nth iteration if we do relaxation again, and if we see that distances reduce it means there is a negative cycles.
 * 
 * TC: O(n*m)
 * 
 */

class Edge {
    int v;
    int wt;
}

class Solution {
    public long[] bellmanFord(int src, int V, List<List<Edge>> graph) {
        long[] dist = new long[V];
        long INF = (long) 1e8;
        Arrays.fill(dist, INF);

        dist[src] = 0L;
        

        // perform V-1 relaxation rounds
        for (int i = 1; i <= V - 1; i++) {         // loop exactly V-1 times
            boolean changed = false;               // track whether any update happened
            for (int u = 0; u < V; u++) {          // iterate each node as the 'from' node
                if (dist[u] == INF) continue;      // if 'u' is not reachable, skip relaxing from it
                for (Edge edge : graph.get(u)) {   // scan all outgoing edges from 'u'
                    int v = edge.v;                // get the destination vertex
                    int w = edge.wt;               // get the edge weight
                    long cand = dist[u] + w;       // compute candidate distance to 'v' via 'u'
                    if (cand < dist[v]) {          // if candidate improves the current distance
                        dist[v] = cand;            // update the distance to 'v'
                        changed = true;            // remember that we changed something
                    }
                }
            }
            if (!changed) {                        // if no distance improved in this round
                break;                             // we can stop early; everything is settled
            }
        }

        // Optinal code to detect negative cycles
        boolean hasNegCycle = false;               // flag to remember if we detect a cycle
        for (int u = 0; u < V; u++) {              // iterate over all 'from' nodes again
            if (dist[u] == INF) continue;          // unreachable 'u' can’t contribute to a cycle we can reach
            for (Edge edge : graph.get(u)) {       // look at each outgoing edge
                int v = edge.v;                    // destination vertex
                int w = edge.wt;                   // edge weight
                long cand = dist[u] + w;           // candidate distance if we relax once more
                if (cand < dist[v]) {              // if it still improves, that’s a negative cycle
                    hasNegCycle = true;            // set the flag to true
                    break;                         // break inner loop; we already know there is a cycle
                }
            }
            if (hasNegCycle) break;                // break outer loop too once detected
        }

        // print whether a negative cycle was detected
        if (hasNegCycle) {                         // if the flag is true
            System.out.println("Negative cycle detected"); // print detection message
        } else {                                   // otherwise
            System.out.println("No negative cycle detected"); // print absence message
        }
    }
    
}