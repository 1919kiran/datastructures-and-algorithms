/**
 * 
 * Intuition: Use BFS - because the way you reach a node is already via fewest edges path if edges are unweighted
 * since BFS explores neighbors with dist=0,1,2,.. so we will never find node with more distance later
 * During BFS
 */

class Solution {
    // If starting node is 0
    public int[] shortestPath(int n, List<List<Integer>> graph, int src) {
        // ArrayList<Integer> result = new ArrayList<>();
        // boolean[] visited = new boolean[V]; // no need for visiteed because dist already does the job
        Queue<Integer> q = new ArrayDeque<>();
        int[] dist = new int[n];
        Arrays.fill(dist, INT_MIN);
        if (src < 0 || src >= n) { // check bounds on source index
            return dist; // return unreachable array when src is out of range
        }

        dist[src] = 0;
        q.add(0);

        while(!q.empty()) {
            Integer i = q.poll();
            for(Integer neighbor: graph.get(i)) {
                if(dist[neighbor] = INT_MIN) {
                    q.add(neighbor);
                    dist[v] = dist[u] + 1;
                }
            }
        }

        return dist;
    }

}