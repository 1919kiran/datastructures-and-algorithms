// import essentials we need
import java.util.*; // bring in List, ArrayList, Arrays, PriorityQueue, etc.


class Edge {
    int v;          // store neighbor vertex index
    int w;          // store weight to that neighbor
    Edge(int v, int w) {
        this.v = v; // set neighbor
        this.w = w; // set weight
    }
}
class PQNode {
    int node;       // the vertex we are considering to add
    int parent;     // the vertex already in MST that connects to 'node' via this edge
    int w;          // the weight of that connecting edge
    PQNode(int node, int parent, int w) {
        this.node = node;   // set the vertex id
        this.parent = parent; // set the parent in MST (or -1 for start)
        this.w = w;         // set the weight key for the heap
    }
}
class MSTResult {
    long totalWeight;         // sum of weights across all picked edges
    List<int[]> mstEdges;     // list of edges picked, each as {u, v, w}
    boolean isSingleTree;     // flag telling if the graph was fully connected
    MSTResult(long totalWeight, List<int[]> mstEdges, boolean isSingleTree) {
        this.totalWeight = totalWeight;   // store total
        this.mstEdges = mstEdges;         // store edges chosen
        this.isSingleTree = isSingleTree; // store connectivity flag
    }
}

/**
 * 
 * Intuition: It's asically a greedy BFS
 * At every step starting from a source node, we check it's neighbors and pick those neighbors into the MST whose path is cheapest
 */
class Solution {

    // public method to compute Prim's MST (or a forest if disconnected)
    public MSTResult primMST(int n, int[][] edges) {
        // build adjacency list for an undirected graph
        List<List<Edge>> adj = new ArrayList<>();     // create outer list
        for (int i = 0; i < n; i++) {                 // loop over all vertices
            adj.add(new ArrayList<>());               // init neighbor list for i
        }
        for (int[] e : edges) {                       // iterate over each input edge
            int u = e[0];                             // read one endpoint
            int v = e[1];                             // read the other endpoint
            int w = e[2];                             // read the weight
            adj.get(u).add(new Edge(v, w));           // add u -> v with weight w
            adj.get(v).add(new Edge(u, w));           // add v -> u with weight w (undirected)
        }

        // prepare result structures
        boolean[] vis = new boolean[n];               // track which vertices are already in MST
        List<int[]> picked = new ArrayList<>();       // store edges picked into MST
        long total = 0L;                              // accumulate total weight across picks
        int componentsCovered = 0;                    // count how many components we processed

        // process each component separately so we return a forest if disconnected
        for (int start = 0; start < n; start++) {     // try every possible start
            if (vis[start]) {                         // if already part of some MST piece
                continue;                             // skip to next start
            }

            // mark that we will grow a new tree from this start
            componentsCovered++;                      // increment component count

            // create a min-heap ordering by edge weight
            PriorityQueue<PQNode> pq = new PriorityQueue<>((a, b) -> a.w - b.w); // min by w

            // push the starting vertex with 0 weight and no parent
            pq.offer(new PQNode(start, -1, 0));      // seed the heap with the start vertex

            // expand while there are candidate edges
            while (!pq.isEmpty()) {                  // continue until heap is empty
                PQNode cur = pq.poll();              // pop the edge with smallest weight
                int u = cur.node;                    // get the candidate vertex to add
                int p = cur.parent;                  // get its parent in MST
                int w = cur.w;                       // get the edge weight connecting p -> u

                if (vis[u]) {                        // if u is already in MST
                    continue;                        // skip stale heap entries
                }

                vis[u] = true;                       // include u into MST now

                if (p != -1) {                       // if this is not the initial seed
                    picked.add(new int[]{p, u, w});  // record the chosen edge p - u with weight w
                    total += w;                      // add the weight to the total
                }

                for (Edge e : adj.get(u)) {          // scan all neighbors of u
                    int v = e.v;                     // neighbor vertex index
                    int wt = e.w;                    // weight of edge u - v
                    if (!vis[v]) {                   // only consider edges to outside vertices
                        pq.offer(new PQNode(v, u, wt)); // push candidate edge for neighbor
                    }
                }
            }
        }

        // graph is a single tree if we covered exactly 1 component and n>0
        boolean singleTree = (n == 0) ? true : (componentsCovered == 1); // treat empty as trivially connected

        // return the full result object
        return new MSTResult(total, picked, singleTree); // hand back total, edges, and connectivity flag
    }
}
