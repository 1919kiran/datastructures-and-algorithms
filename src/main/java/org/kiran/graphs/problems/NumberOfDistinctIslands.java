// class that counts number of distinct island shapes

class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Solution {

    /**
     * Intuition: If we put the shapes in a set, we can get unique shapes
     * How to identify a shape: we can use a pair that represent connection while keeping the traversal and direction order constant
     * How to make sure identical shapes have same list of pairs: since dir and traversal is constant we can subtract the next coordinate from base coordinate
     */
    public int countDistinctIslands(int[][] grid) {
        // guard for empty input
        if (grid == null || grid.length == 0) return 0;             // no rows means no islands

        // dimensions of the grid
        int n = grid.length;                                         // number of rows
        int m = grid[0].length;                                      // number of columns

        // visited matrix to avoid revisiting cells
        boolean[][] visited = new boolean[n][m];                     // tracks if a cell is already processed

        // set to store unique island signatures
        java.util.Set<String> shapes = new java.util.HashSet<>();    // holds canonical shape strings

        // iterate over every cell in the grid
        for (int r = 0; r < n; r++) {                                // loop through rows
            for (int c = 0; c < m; c++) {                            // loop through columns
                // start a new island DFS if we find unvisited land
                if (grid[r][c] == 1 && !visited[r][c]) {             // fresh land cell found
                    StringBuilder sig = new StringBuilder();         // builder for the island signature
                    Coordinate base = new Coordinate(r, c);
                    dfs(base, base, grid, visited, sig);             // explore island and build signature
                    shapes.add(sig.toString());                      // add this island's signature to the set
                }
            }
        }

        // number of distinct shapes is the size of the set
        return shapes.size();                                        // return count of unique signatures
    }

    // depth-first search to record an island's shape using relative coordinates
    private void dfs(Coordinate base, Coordinate curr, int[][] grid, boolean[][] visited, StringBuilder sig) {
        // mark current cell as visited
        visited[curr.x][curr.y] = true;                                        // prevent revisiting this land

        sig.append(curr.x - base.x).append(',').append(curr.y - base.y).append(';');      // append normalized coordinate to the signature

        int[] dr = {-1, 0, 1, 0};                               // row deltas for up, right, down, left
        int[] dc = {0, 1, 0, -1};                               // col deltas for up, right, down, left

        for (int k = 0; k < 4; k++) {                           // check all four neighbors
            int nr = curr.x + dr[k];                            // neighbor row from the current cell
            int nc = curr.y + dc[k];                            // neighbor col from the current cell

            if (nr >= 0 && nr < grid.length                    // make sure row is in bounds
                && nc >= 0 && nc < grid[0].length              // make sure col is in bounds
                && grid[nr][nc] == 1                           // must be land
                && !visited[nr][nc]) {                         // must be unvisited
                dfs(base, new Coordinate(nr, nc), grid, visited, sig); // recurse with same base and new current
            }
        }
    }
}