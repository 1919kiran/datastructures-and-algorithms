/**
 * Algorithm:
 * 1. Sort all edges by (wt, u, v)
 * 2. Find the parent of u,v
 *  - If findParent(u) == findParent(v) means they already belong to the same component, and there is no point in connecting them again using a higher weight
 *  - If findParent(u) != findParent(v) means they are not part of the same component so add them to the MST
 * 
 * Why sort - we ensure that for a given pair (u,v) we always pick the smallest edge first
 * Why not pick if parents are equal - We may end up creating cycles and increasing weight of MST - not required since all nodes are already connected.
 * 
 */
class Solution {
    public List<int[]> kruskalMST(int n, int[][] edges) {
    // sort edges by weight in non-decreasing order
    Arrays.sort(edges, new Comparator<int[]>() {     // provide a comparator for sorting
        public int compare(int[] a, int[] b) {       // compare two edges a and b
            return Integer.compare(a[2], b[2]);      // sort by weight (index 2)
        }
    });

    // initialize DSU for n vertices
    DSU dsu = new DSU(n);                            // create disjoint set structure

    // prepare to collect MST edges and total weight
    List<int[]> picked = new ArrayList<>();          // will store chosen edges
    long total = 0L;                                 // running sum of chosen edge weights

    // iterate all edges from smallest weight to largest
    for (int[] e : edges) {                          // for each edge in sorted order
        int u = e[0];                                // read one endpoint
        int v = e[1];                                // read the other endpoint
        int w = e[2];                                // read the weight

        // attempt to union their components
        if (dsu.unionByRank(u, v)) {                 // if union succeeds (no cycle created)
            picked.add(new int[]{u, v, w});          // record this edge in MST
            total += w;                              // add its weight to total
            if (picked.size() == n - 1) {            // if we have n-1 edges already
                break;                                // MST is complete for connected graph
            }
        }                                             // else, skip because it would form a cycle
    }

    // determine if we formed a single tree (i.e., exactly n-1 edges for n>0)
    boolean singleTree = (n == 0) ? true : (picked.size() == Math.max(0, n - 1)); // handle empty graph politely

    // return the result containing total, the edges, and connectivity flag
    return picked;
}
}