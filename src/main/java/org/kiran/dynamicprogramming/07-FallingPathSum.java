public class FallingPathSum {

    public static int maxFallingPathSumMemo(int[][] a) {
        // handle empty safely
        if (a == null || a.length == 0 || a[0].length == 0) return 0;
        // fetch dimensions
        int r = a.length, c = a[0].length;
        // memo for max version, same sizing
        int[][] dp = new int[r][c];
        // use sentinel for uncomputed
        for (int[] row : dp) Arrays.fill(row, Integer.MIN_VALUE);
        // aggregate the maximum over all end columns
        int ans = Integer.MIN_VALUE;
        // try each last-row column as endpoint
        for (int j = 0; j < c; j++) {
            // update global maximum using f(i,j) for max case
            ans = Math.max(ans, solveMax(r - 1, j, a, dp));
        }
        // return the best achievable sum
        return ans;
    }

    // top-down recurrence for maximum case: f(i,j) = a[i][j] + max(parents)
    private static int solveMax(int i, int j, int[][] a, int[][] dp) {
        // out of bounds returns −infinity so this path is never preferred
        if (j < 0 || j >= a[0].length) return (int)-1e9;
        // base — first row value
        if (i == 0) return a[0][j];
        // memo hit check
        if (dp[i][j] != Integer.MIN_VALUE) return dp[i][j];
        // candidate via directly above
        int up = a[i][j] + solveMax(i - 1, j, a, dp);
        // candidate via up-left
        int upLeft = a[i][j] + solveMax(i - 1, j - 1, a, dp);
        // candidate via up-right
        int upRight = a[i][j] + solveMax(i - 1, j + 1, a, dp);
        // cache maximum among the three
        dp[i][j] = Math.max(up, Math.max(upLeft, upRight));
        // return memoized value
        return dp[i][j];
    }

    public static int maxFallingPathSumTab(int[][] a) {
        // empty guard
        if (a == null || a.length == 0 || a[0].length == 0) return 0;
        // dimensions
        int r = a.length, c = a[0].length;
        // dp[i][j] holds best (maximum) sum to reach (i,j)
        int[][] dp = new int[r][c];

        // initialize first row
        for (int j = 0; j < c; j++) {
            // base for max is the same copy
            dp[0][j] = a[0][j];
        }

        // fill remaining rows using max transition
        for (int i = 1; i < r; i++) {
            // iterate across columns
            for (int j = 0; j < c; j++) {
                // value from up
                int up = dp[i - 1][j];
                // value from up-left or −infinity if missing
                int upLeft = (j - 1 >= 0) ? dp[i - 1][j - 1] : (int)-1e9;
                // value from up-right or −infinity if missing
                int upRight = (j + 1 < c) ? dp[i - 1][j + 1] : (int)-1e9;
                // current = cost + maximum parent
                dp[i][j] = a[i][j] + Math.max(up, Math.max(upLeft, upRight));
            }
        }

        // pick the maximum on the last row as final answer
        int ans = Integer.MIN_VALUE;
        // scan last row to find the largest
        for (int j = 0; j < c; j++) ans = Math.max(ans, dp[r - 1][j]);
        // return maximum falling path sum
        return ans;
    }

}
