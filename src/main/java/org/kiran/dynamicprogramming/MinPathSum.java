public class MinPathSum {

    /**
     * Intuition:
     * Exactly the same as grid unique path but here we pick minimal path of up and left at every cell
     */
    public static int minPathSumTab(int[][] grid) {
        // handle empty input up front
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        // rows count
        int m = grid.length;
        // columns count
        int n = grid[0].length;
        // allocate dp where dp[i][j] is min sum to reach (i,j)
        int[][] dp = new int[m][n];

        // iterate over every cell in row-major order so dependencies are ready
        for (int i = 0; i < m; i++) {
            // walk across columns
            for (int j = 0; j < n; j++) {
                // when we’re at the start, it’s just the start cell cost
                if (i == 0 && j == 0) {
                    // initialize base cell
                    dp[i][j] = grid[i][j];
                    // continue to next cell after setting base
                    continue;
                }
                // cost from above; if no above, treat as infinity so it won’t be chosen
                int up = (i > 0) ? dp[i - 1][j] : (int)1e9;
                // cost from left; if no left, treat as infinity similarly
                int left = (j > 0) ? dp[i][j - 1] : (int)1e9;
                // pick the cheaper predecessor and add current cell’s cost
                dp[i][j] = grid[i][j] + Math.min(up, left);
            }
        }
        // bottom-right now holds the minimum path sum to the destination
        return dp[m - 1][n - 1];
    }
    
}
