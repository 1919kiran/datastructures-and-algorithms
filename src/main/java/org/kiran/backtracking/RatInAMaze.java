import java.util.*; // import utilities for List and ArrayList

class Solution {

    /**
     * Intuition: In order to control the path ordering, we can create a control loop for explore the direction interatively at each cell
     * The further explorations beyond this cell can be done recursively and backtrack from there.
     */
    public List<String> findPath(int[][] grid, int n) {
        List<String> ans = new ArrayList<>(); // this will store all valid path strings
        if (n == 0) return ans; // if grid has no size, return empty list
        if (grid[0][0] == 0 || grid[n - 1][n - 1] == 0) return ans; // if start or end blocked, no paths

        boolean[][] visited = new boolean[n][n]; // keep track of cells currently in the path
        StringBuilder path = new StringBuilder(); // build the path string incrementally

        // direction arrays in Striver order: Down, Left, Right, Up
        int[] dr = {+1, 0, 0, -1}; // row deltas for D, L, R, U
        int[] dc = {0, -1, +1, 0}; // col deltas for D, L, R, U
        char[] dir = {'D', 'L', 'R', 'U'}; // direction characters to append

        // start DFS from the top-left corner (0,0)
        dfs(0, 0, grid, n, visited, path, ans, dr, dc, dir); // explore all possible paths

        return ans; // return the collected list (already lexicographic due to move order)
    }

    // depth-first search with backtracking
    private void dfs(int r, int c,
                     int[][] grid, int n,
                     boolean[][] visited,
                     StringBuilder path,
                     List<String> ans,
                     int[] dr, int[] dc, char[] dir) {

        if (r == n - 1 && c == n - 1) { // if we reached destination cell
            ans.add(path.toString()); // add the current path string to the answers
            return; // backtrack to explore other possibilities
        }

        visited[r][c] = true; // mark this cell as part of current path

        for (int k = 0; k < 4; k++) { // try all four directions in D, L, R, U order
            int nr = r + dr[k]; // compute next row
            int nc = c + dc[k]; // compute next col

            if (isSafe(nr, nc, grid, n, visited)) { // check bounds, open cell, and not visited
                path.append(dir[k]); // choose: append the direction char
                dfs(nr, nc, grid, n, visited, path, ans, dr, dc, dir); // explore deeper
                path.deleteCharAt(path.length() - 1); // un-choose: remove last char to backtrack
            }
        }

        visited[r][c] = false; // unmark this cell before returning so other paths can use it
    }

    // helper to verify whether we can step onto (r, c)
    private boolean isSafe(int r, int c, int[][] grid, int n, boolean[][] visited) {
        if (r < 0 || c < 0) return false; // out of top or left bound
        if (r >= n || c >= n) return false; // out of bottom or right bound
        if (grid[r][c] == 0) return false; // blocked cell cannot be used
        if (visited[r][c]) return false; // cannot revisit a cell in the current path
        return true; // otherwise it is safe to move there
    }
}
