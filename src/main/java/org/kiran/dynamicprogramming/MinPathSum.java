public class MinPathSum {

    /**
     * Intuition:
     * Exactly the same as grid unique path but here we pick minimal path of up and left at every cell
     */
    public static int minPathSumTab(int[][] grid) {
        // explaining purpose: handle empty input up front
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        // explaining purpose: rows count
        int m = grid.length;
        // explaining purpose: columns count
        int n = grid[0].length;
        // explaining purpose: allocate dp where dp[i][j] is min sum to reach (i,j)
        int[][] dp = new int[m][n];

        // explaining purpose: iterate over every cell in row-major order so dependencies are ready
        for (int i = 0; i < m; i++) {
            // explaining purpose: walk across columns
            for (int j = 0; j < n; j++) {
                // explaining purpose: when we’re at the start, it’s just the start cell cost
                if (i == 0 && j == 0) {
                    // explaining purpose: initialize base cell
                    dp[i][j] = grid[i][j];
                    // explaining purpose: continue to next cell after setting base
                    continue;
                }
                // explaining purpose: cost from above; if no above, treat as infinity so it won’t be chosen
                int up = (i > 0) ? dp[i - 1][j] : (int)1e9;
                // explaining purpose: cost from left; if no left, treat as infinity similarly
                int left = (j > 0) ? dp[i][j - 1] : (int)1e9;
                // explaining purpose: pick the cheaper predecessor and add current cell’s cost
                dp[i][j] = grid[i][j] + Math.min(up, left);
            }
        }
        // explaining purpose: bottom-right now holds the minimum path sum to the destination
        return dp[m - 1][n - 1];
    }
    
}
