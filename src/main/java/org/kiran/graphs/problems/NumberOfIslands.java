// class to solve number of islands with BFS
class Solution {
    // main method
    public int numIslandsGrid(char[][] grid) {
        if (grid == null || grid.length == 0) return 0; // empty grid case

        int n = grid.length;           // number of rows
        int m = grid[0].length;        // number of columns
        boolean[][] visited = new boolean[n][m]; // visited matrix
        int islands = 0;               // answer counter

        // loop over all cells
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                // if this is fresh land, start BFS
                if (grid[r][c] == '1' && !visited[r][c]) {
                    islands++;            // new island found
                    bfs(r, c, grid, visited); // mark the whole island
                }
            }
        }

        return islands; // total islands
    }

    public int numIslandsGraph(int V, java.util.List<java.util.List<Integer>> adj) {
        // edge case: no vertices means no components
        if (V == 0) return 0;                                           // empty graph, answer is 0

        // visited array to track which nodes we have already seen
        boolean[] visited = new boolean[V];                             // default false for all nodes

        // answer counter for number of components
        int islands = 0;                                             // start with zero islands

        // scan all vertices to handle disconnected graph
        for (int i = 0; i < V; i++) {                                   // loop over all nodes
            if (!visited[i]) {                                          // if this node starts a new island
                islands++;                                            // found a new component
                dfs(i, adj, visited);                                   // mark everything reachable from i
            }
        }

        // return the total number of islands found
        return islands;                                              // final count
    }

    private void dfs(int node, java.util.List<java.util.List<Integer>> adj, boolean[] visited) {
        visited[node] = true;                                           // mark current node as visited

        // iterate through all neighbors of the current node
        for (Integer nei : adj.get(node)) {                             // for each adjacent vertex
            if (!visited[nei]) {                                        // if neighbor not yet visited
                dfs(nei, adj, visited);                                 // recurse to cover neighbor
            }
        }
    }

    /**
     * In BFS, we add the current node to the queue and start exploring it's neighbors in all directions
     * Once a bfs is done for a node and it is don exploring all it's neighbors we will have an island
     */
    private void bfs(int row, int col, char[][] grid, boolean[][] visited) {
        int n = grid.length;
        int m = grid[0].length;

        // queue to hold coordinates of land cells
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{row, col});      // push starting cell
        visited[row][col] = true;        // mark visited

        // direction arrays for up, right, down, left
        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};

        // process until queue is empty
        while (!q.isEmpty()) {
            int[] cell = q.poll();        // get current cell
            int r = cell[0];
            int c = cell[1];

            // explore 4 neighbors
            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k];
                int nc = c + dc[k];

                // boundary and condition checks
                if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
                    if (grid[nr][nc] == '1' && !visited[nr][nc]) {
                        visited[nr][nc] = true;       // mark neighbor visited
                        q.add(new int[]{nr, nc});     // push neighbor to queue
                    }
                }
            }
        }
    }

    private void dfs(int row, int col, char[][] grid, boolean[][] visited) {
        // mark current cell as visited right away
        visited[row][col] = true;               // so we don’t revisit this land

        // arrays for 4-directional movement: up, right, down, left
        int[] dr = {-1, 0, 1, 0};               // row deltas
        int[] dc = {0, 1, 0, -1};               // col deltas

        // explore all 4 neighbors
        for (int k = 0; k < 4; k++) {           // check each direction
            int nr = row + dr[k];               // next row
            int nc = col + dc[k];               // next column

            // check boundaries first
            if (nr < 0 || nr >= grid.length) continue;    // out of row bounds
            if (nc < 0 || nc >= grid[0].length) continue; // out of col bounds

            // proceed only if neighbor is land and not yet visited
            if (grid[nr][nc] == '1' && !visited[nr][nc]) { // valid neighbor land
                dfs(nr, nc, grid, visited);    // recurse to cover the neighbor
            }
        }
    }

    private void dfsIterative(int row, int col, char[][] grid, boolean[][] visited) {
        int n = grid.length, m = grid[0].length;
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{row, col});
        visited[row][col] = true;

        int[] dr = {-1, 0, 1, 0};
        int[] dc = {0, 1, 0, -1};

        while (!stack.isEmpty()) {
            int[] cell = stack.pop();
            int r = cell[0], c = cell[1];

            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k], nc = c + dc[k];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m
                        && grid[nr][nc] == '1' && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    stack.push(new int[]{nr, nc});
                }
            }
        }
    }
}
