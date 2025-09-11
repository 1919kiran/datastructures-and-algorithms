class Edge {
    int v;
    int wt;
}

class Solution {
    /**
     * Intuition: we always want to expand the node with the smallest known tentative distance so far
     * because in a graph with non-negative edges, once you’ve fixed the shortest distance to a node, you’ll never find a shorter one later
     * so we use a min-heap (priority queue) to pick the node with the smallest tentative distance, and relax its edges
     * Note: Does not work for -ve weight or cycles
     * 
     * Why PR and not Q - since we wanna only need to get smallest distance using queue we will end up with more redundant checks
     * in an unweighted graph, every edge has the same “cost” of 1. so bfs, which expands level by level, guarantees that the first time you reach a node, 
     * it’s by the shortest number of edges. that’s why a plain queue is enough.
     * basically, shortest path is via minimum number of edges.
     * but in a weighted graph, edges don’t all cost the same. suppose i reach a node v first through an edge of weight 100. 
     * later, maybe there’s another path to v through some other route where edges are lighter, say 1+1+1. if i only used a normal fifo queue, 
     * i’d lock in the distance 100 and never reconsider v. that would be wrong.
     * with a priority queue, we always process the node with the smallest current tentative distance. so even if we discovered v early with distance 100, 
     * we’ll only pull it out of the heap to relax when it has the minimum tentative distance compared to everything else.
     * 
     * TC: E log V
     * 
     */
    public int[] dijkstraPQ(int src, int V, List<List<Edge>> graph) {
        // initialize distances array
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE); // set all as infinity

        // use priority queue (min-heap) of [distance, node]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        
        // start from the source
        dist[src] = 0;             // distance to source is 0
        pq.offer(new int[]{0, src}); // push source with distance 0

        while(pq.size() != 0) {
            int[] curr = pq.poll();  // get node with smallest distance
            int d = curr[0];         // current distance
            int u = curr[1];         // current node

            if (d > dist[u]) continue;

            for (Edge edge : graph.get(u)) {
                int v = edge.v;       // neighbor
                int w = edge.wt;      // weight
                if (dist[u] + w < dist[v]) {   // if we found shorter path perform edge relaxation
                    dist[v] = dist[u] + w;     // update distance
                    pq.offer(new int[]{dist[v], v}); // push neighbor into queue
                }
            }
        }

        return dist;
    }

    /**
     * Intuition: the node with the smallest distance is always at the beginning of the set. 
     * the set contains at most one entry per node at any time: its current best known distance
     * when we relax an edge if we see that a distance already exists for a node, it means someone already reached it before and it will be in the set
     * If that is greater that the current distance, we can discard the greater distance in the set and keep the shorter distance
     * the tradeoff is that every decrease-key operation becomes an explicit remove+add in the set, which costs log n. so complexity is still O((n + m) log n),
     * just like with priority queue.
     * 
     * TC: E log V
     */
    public int[] dijkstraSet(int src, int V, List<List<Edge>> graph) {
        // initialize distances array
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE); // set all as infinity

        // TreeSet to store pairs (dist, node) in sorted order: First check distance, then check node number
        Set<int[]> set = new TreeSet<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        
        // start from the source
        dist[src] = 0;             // distance to source is 0
        set.add(new int[]{0, src}); // push source with distance 0

        while(!set.isEmpty()) {
            int[] curr = set.pollFirst();  // get node with smallest distance
            int d = curr[0];         // current distance
            int u = curr[1];         // current node

            if (d > dist[u]) continue;

            // relax edges
            for (Edge edge : adj.get(u)) {
                int v = edge.v;
                int w = edge.wt;
                if (d + w < dist[v]) {
                    // if v already in set, remove old entry
                    if (dist[v] != Integer.MAX_VALUE) {
                        set.remove(new int[]{dist[v], v});
                        // careful: TreeSet compares by comparator, but since arrays
                        // use object identity, we should ideally wrap in a class Pair
                        // for reliable removal. here we'll trust distances are unique
                    }
                    // update distance
                    dist[v] = d + w;
                    // add updated entry
                    set.add(new int[]{dist[v], v});
                }
            }
        }

        return dist;
    }

}