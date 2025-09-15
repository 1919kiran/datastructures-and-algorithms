/**
 * Purpose:
 * To efficiently keep track of elements that are split into a number of disjoint (non-overlapping) sets.
 * 
 * Usecases:
 * - Detecting connectivity: To check whether two elements are in the same group. For example, in a social network
 * - Kruskal’s Minimum Spanning Tree Algorithm: Union-Find is used where you need to check if adding an edge would create a cycle.
 * - Cycle detection in graphs: Efficient for checking if an undirected graph has a cycle.
 * - Dynamic connectivity problems: When you have a set of items and keep merging subsets, DSU lets you query relationships quickly.
 * 
 * Patterns:
 * - Connected Components in Undirected Graphs: Count components after unions; also used when edges get added dynamically.
 * - Cycle Detection in undirected graphs
 * - Connected Components in Grids
 * - Accounts Merge / String Grouping
 * 
 * Algorithm:
 * 1. Let's keep a parent array for each node to keep track of who is the parent node of the node.
 * 2. Let's keep a rank array for each node to keep track of count of how many nodes are below it (not exactly depth/height)
 * 3. When doing union of (u,v):
 *  - if rank(u)!=rank(v), attach the root of smaller rank node to root of larger rank node
 *  - if rank(u)==rank(v) then make u parent of v and increase rank of u
 *  note: rank is not increased when a smaller rank node is attached to larger rank node becase rank != height, it indicates 
 *  which to pick at each step why not connect larger to smaller? because that will increase the path to be travlled to find a parent
 * 4. When doing find:
 *  - Recursively find the ultimate parent, and update the parent as this ultimate root for every node along the recursive path (backtracking). 
 *  This is called as path compression.
 * 
 */
class DisjointSetUnion {
    private int[] parent; // parent[i] stores parent of node i
    private int[] rank;   // rank[i] stores rank (approx depth) of tree rooted at i
    private int[] size;   // rank[i] stores rank (approx depth) of tree rooted at i

    // constructor to initialize DSU for n elements (0..n-1)
    public DisjointSetUnion(int n) {
        parent = new int[n]; // allocate parent array
        rank = new int[n];   // allocate rank array
        for (int i = 0; i < n; i++) {
            parent[i] = i;   // initially each node is its own parent
            rank[i] = 0;     // initially all ranks are zero
            size[i] = 1;
        }
    }

    // method to union two sets by rank
    public boolean unionByRank(int u, int v) {
        int pu = findParentPathCompression(u); // find parent of u
        int pv = findParentPathCompression(v); // find parent of v

        if (pu == pv) {         // if they are already in the same set
            return false;             // do nothing
        }

        // attach smaller rank tree under larger rank tree
        if (rank[pu] < rank[pv]) {
            parent[pu] = pv;    // make pv the parent
        } else if (rank[pv] < rank[pu]) {
            parent[pv] = pu;    // make pu the parent
        } else {
            parent[pv] = pu;    // if ranks equal, pick one (pu here)
            rank[pu]++;         // increase rank of the chosen root
        }
        return true;
    }

    public boolean unionBySize(int u, int v) {
        int pu = findParentPathCompression(u); // find parent of u
        int pv = findParentPathCompression(v); // find parent of v

        if (pu == pv) {         // if they are already in the same set
            return false;             // do nothing
        }

        // attach smaller size tree under larger size tree
        if (size[pu] < size[pv]) {
            parent[pu] = pv;        // make pv the parent
            size[pv] += size[pu];   // increase size of parent
        } else {
            parent[pv] = pu;        // if ranks equal, pick one.. not that it doesn't matter wh
            size[pu] += size[pv];   // increase rank of the chosen root
        }
        return true;
    }

    // method to find the ultimate parent of a node, with path compression
    public int findParentPathCompression(int node) {
        if (parent[node] != node) {                // if node is not its own parent
            parent[node] = findParentPathCompression(parent[node]); // recursively find root and compress path
        }
        return parent[node];                       // return the ultimate parent
    }

    // method to find the ultimate parent of a node
    public int findParent(int node) {
        return parent[node];                       // return the immediate parent
    }

}
