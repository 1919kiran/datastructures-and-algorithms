import java.util.*;

public class UniquePathsMemo {

    /**
     * from cell (i, j) I can come either from above (i-1, j) or from the left (i, j-1). 
     * So the number of paths to (i, j) is 
     * f(i, j) = f(i-1, j) + f(i, j-1)
     * Base case is f(0, 0) = 1
     */
    public static int uniquePathsMemo(int m, int n) {
        // allocate a memo table initialized to -1 meaning "not computed yet"
        int[][] dp = new int[m][n];
        // fill the table with sentinels so we can detect cached values
        for (int[] row : dp) Arrays.fill(row, -1);
        // start the recursion from the destination cell (m-1, n-1)
        return dfs(m - 1, n - 1, dp);
    }

    // f(i, j): number of ways to reach cell (i, j) from (0, 0) moving only right/down
    private static int dfs(int i, int j, int[][] dp) {
        // when we land exactly at the start, there is exactly one path (the trivial one)
        if (i == 0 && j == 0) return 1;
        // stepping outside the grid contributes zero paths
        if (i < 0 || j < 0) return 0;
        // reuse the computed value if present to avoid recomputation
        if (dp[i][j] != -1) return dp[i][j];
        // compute paths coming from the cell above (i-1, j)
        int up = dfs(i - 1, j, dp);
        // compute paths coming from the cell to the left (i, j-1)
        int left = dfs(i, j - 1, dp);
        // store the total number of paths into (i, j) as sum of above and left
        dp[i][j] = up + left;
        // return the memoized result
        return dp[i][j];
    }

    public static int uniquePathsTab(int m, int n) {
        // allocate a table where dp[i][j] means ways to reach (i, j) from (0, 0)
        int[][] dp = new int[m][n];
        // set the start cell to 1 because there's exactly one way to be at the start
        dp[0][0] = 1;
        // fill the grid row by row
        for (int i = 0; i < m; i++) {
            // iterate across columns for the current row
            for (int j = 0; j < n; j++) {
                // skip (0,0) since we already set it
                if (i == 0 && j == 0) continue;
                // number of ways from the top neighbor if it exists, else 0
                int up = (i > 0) ? dp[i - 1][j] : 0;
                // number of ways from the left neighbor if it exists, else 0
                int left = (j > 0) ? dp[i][j - 1] : 0;
                // sum both contributions to get ways to (i, j)
                dp[i][j] = up + left;
            }
        }
        // the destination cell holds the final count of unique paths
        return dp[m - 1][n - 1];
    }
   
    public static int uniquePathsSpace(int m, int n) {
        // prev[j] will store the number of ways to reach column j in the previous row
        int[] prev = new int[n];
        // iterate over each row of the grid
        for (int i = 0; i < m; i++) {
            // curr[j] stores the number of ways to reach column j in the current row
            int[] curr = new int[n];
            // for the very first cell in the grid, set paths to 1
            if (i == 0) curr[0] = 1;
            // fill across the columns for this row
            for (int j = 0; j < n; j++) {
                // skip re-setting (0,0) since it's already 1
                if (i == 0 && j == 0) continue;
                // ways from the top is prev[j] because that’s the same column in the previous row
                int up = (i > 0) ? prev[j] : 0;
                // ways from the left is curr[j-1] because we’re in the same row, previous column
                int left = (j > 0) ? curr[j - 1] : 0;
                // total ways to current cell is sum of up and left
                curr[j] = up + left;
            }
            // slide the window: the current row becomes the previous row for the next iteration
            prev = curr;
        }
        // after finishing all rows, the last entry of prev is the answer at (m-1, n-1)
        return prev[n - 1];
    }

}
