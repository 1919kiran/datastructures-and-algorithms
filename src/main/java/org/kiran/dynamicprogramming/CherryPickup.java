import java.util.*;

public class CherryPickupMemo {

    // using a large negative value as "impossible" so it never wins a max
    private static final int (int)-1e9 = (int)-1e9;

    /**
     * f(i, j1, j2) = val(i, j1, j2) + max over d1∈{-1,0,1}, d2∈{-1,0,1} of f(i+1, j1+d1, j2+d2)
     * where val(i, j1, j2) = grid[i][j1] if j1==j2 else grid[i][j1] + grid[i][j2]
     * Base: i == r−1 → return val(r−1, j1, j2).
     */
    public static int cherryPickup(int[][] grid) {
        // guard against empty input to avoid index issues
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        // rows and columns
        int r = grid.length, c = grid[0].length;
        // dp[i][j1][j2] caches best cherries from state (i, j1, j2)
        int[][][] dp = new int[r][c][c];
        // fill dp with sentinel to mark "uncomputed"
        for (int i = 0; i < r; i++) for (int j1 = 0; j1 < c; j1++) Arrays.fill(dp[i][j1], Integer.MIN_VALUE);
        // start recursion from the initial positions
        return solve(0, 0, c - 1, grid, dp);
    }

    // f(i, j1, j2): best cherries starting at row i with robots at columns j1 and j2
    private static int solve(int i, int j1, int j2, int[][] grid, int[][][] dp) {
        // if either robot is outside the grid, this path is invalid; return negative infinity
        if (j1 < 0 || j1 >= grid[0].length || j2 < 0 || j2 >= grid[0].length) return (int)-1e9;
        // if we are at the last row, return the cherries on these cells (once if same)
        if (i == grid.length - 1) {
            // if both robots share the cell, count it once
            if (j1 == j2) return grid[i][j1];
            // otherwise sum both cells
            return grid[i][j1] + grid[i][j2];
        }
        // if already computed, reuse the cached answer
        if (dp[i][j1][j2] != Integer.MIN_VALUE) return dp[i][j1][j2];
        // collect cherries at current positions; de-duplicate if same cell
        int curr = (j1 == j2) ? grid[i][j1] : grid[i][j1] + grid[i][j2];
        // initialize best with negative infinity so any valid move will beat it
        int best = (int)-1e9;
        // try all 9 combinations of next moves for both robots
        for (int d1 = -1; d1 <= 1; d1++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                // compute next columns after moving down to the next row
                int nj1 = j1 + d1, nj2 = j2 + d2;
                // recursively compute best from next state
                int candidate = solve(i + 1, nj1, nj2, grid, dp);
                // keep the maximum total cherries including what we grabbed at this row
                best = Math.max(best, curr + candidate);
            }
        }
        // cache and return the best result for this state
        dp[i][j1][j2] = best;
        return best;
    }

    public static int cherryPickup(int[][] grid) {
        // guard for empty grid
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        // dimensions
        int r = grid.length, c = grid[0].length;
        // dp[i][j1][j2] = best cherries starting from row i with robots at j1 and j2
        int[][][] dp = new int[r][c][c];

        // initialize base row i = r-1
        for (int j1 = 0; j1 < c; j1++) {
            for (int j2 = 0; j2 < c; j2++) {
                // on the last row, take one or two cells, de-duplicating if same
                dp[r - 1][j1][j2] = (j1 == j2) ? grid[r - 1][j1] : grid[r - 1][j1] + grid[r - 1][j2];
            }
        }

        // fill rows from r-2 up to 0 so that children on row i+1 are already computed
        for (int i = r - 2; i >= 0; i--) {
            // iterate all column pairs for the two robots
            for (int j1 = 0; j1 < c; j1++) {
                for (int j2 = 0; j2 < c; j2++) {
                    // cherries collected at current row by the two robots
                    int curr = (j1 == j2) ? grid[i][j1] : grid[i][j1] + grid[i][j2];
                    // start with negative infinity
                    int best = (int)-1e9;
                    // explore all 9 next-move combinations
                    for (int d1 = -1; d1 <= 1; d1++) {
                        for (int d2 = -1; d2 <= 1; d2++) {
                            // compute next columns on the row below
                            int nj1 = j1 + d1, nj2 = j2 + d2;
                            // check bounds; skip if outside
                            if (nj1 < 0 || nj1 >= c || nj2 < 0 || nj2 >= c) continue;
                            // candidate is current cherries plus best from the next row state
                            best = Math.max(best, curr + dp[i + 1][nj1][nj2]);
                        }
                    }
                    // write best into dp for this state
                    dp[i][j1][j2] = best;
                }
            }
        }

        // answer is starting at top row with robots at columns 0 and c-1
        return dp[0][0][c - 1];
    }

    public static int cherryPickup(int[][] grid) {
        // handle empty grid defensively
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        // dimensions
        int r = grid.length, c = grid[0].length;

        // prev[j1][j2] will represent dp[i+1][j1][j2] — best from the row below
        int[][] prev = new int[c][c];

        // initialize prev to the base on the last row
        for (int j1 = 0; j1 < c; j1++) {
            for (int j2 = 0; j2 < c; j2++) {
                // last row contribution with de-duplication
                prev[j1][j2] = (j1 == j2) ? grid[r - 1][j1] : grid[r - 1][j1] + grid[r - 1][j2];
            }
        }

        // iterate upward from the second-last row to the top
        for (int i = r - 2; i >= 0; i--) {
            // curr holds dp for the current row i across all (j1, j2)
            int[][] curr = new int[c][c];
            // compute all column pairs
            for (int j1 = 0; j1 < c; j1++) {
                for (int j2 = 0; j2 < c; j2++) {
                    // cherries collected on this row; de-duplicate if same column
                    int base = (j1 == j2) ? grid[i][j1] : grid[i][j1] + grid[i][j2];
                    // start with negative infinity so any valid child improves it
                    int best = (int)-1e9;
                    // consider 9 pairs of moves to row i+1
                    for (int d1 = -1; d1 <= 1; d1++) {
                        for (int d2 = -1; d2 <= 1; d2++) {
                            // compute next columns
                            int nj1 = j1 + d1, nj2 = j2 + d2;
                            // skip invalid next positions
                            if (nj1 < 0 || nj1 >= c || nj2 < 0 || nj2 >= c) continue;
                            // total cherries if we take this pair of moves
                            best = Math.max(best, base + prev[nj1][nj2]);
                        }
                    }
                    // record best for this (j1, j2) on row i
                    curr[j1][j2] = best;
                }
            }
            // roll layers: the current row becomes the "row below" for the next iteration up
            prev = curr;
        }

        // final answer starting from (0,0) and (0,c-1)
        return prev[0][c - 1];
    }

}
