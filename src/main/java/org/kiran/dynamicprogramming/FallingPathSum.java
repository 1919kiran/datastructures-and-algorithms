public class FallingPathSum {

    public static int maxFallingPathSumMemo(int[][] a) {
        // explaining purpose: handle empty safely
        if (a == null || a.length == 0 || a[0].length == 0) return 0;
        // explaining purpose: fetch dimensions
        int r = a.length, c = a[0].length;
        // explaining purpose: memo for max version, same sizing
        int[][] dp = new int[r][c];
        // explaining purpose: use sentinel for uncomputed
        for (int[] row : dp) Arrays.fill(row, Integer.MIN_VALUE);
        // explaining purpose: aggregate the maximum over all end columns
        int ans = Integer.MIN_VALUE;
        // explaining purpose: try each last-row column as endpoint
        for (int j = 0; j < c; j++) {
            // explaining purpose: update global maximum using f(i,j) for max case
            ans = Math.max(ans, solveMax(r - 1, j, a, dp));
        }
        // explaining purpose: return the best achievable sum
        return ans;
    }

    // explaining purpose: top-down recurrence for maximum case: f(i,j) = a[i][j] + max(parents)
    private static int solveMax(int i, int j, int[][] a, int[][] dp) {
        // explaining purpose: out of bounds returns −infinity so this path is never preferred
        if (j < 0 || j >= a[0].length) return (int)-1e9;
        // explaining purpose: base — first row value
        if (i == 0) return a[0][j];
        // explaining purpose: memo hit check
        if (dp[i][j] != Integer.MIN_VALUE) return dp[i][j];
        // explaining purpose: candidate via directly above
        int up = a[i][j] + solveMax(i - 1, j, a, dp);
        // explaining purpose: candidate via up-left
        int upLeft = a[i][j] + solveMax(i - 1, j - 1, a, dp);
        // explaining purpose: candidate via up-right
        int upRight = a[i][j] + solveMax(i - 1, j + 1, a, dp);
        // explaining purpose: cache maximum among the three
        dp[i][j] = Math.max(up, Math.max(upLeft, upRight));
        // explaining purpose: return memoized value
        return dp[i][j];
    }

    public static int maxFallingPathSumTab(int[][] a) {
        // explaining purpose: empty guard
        if (a == null || a.length == 0 || a[0].length == 0) return 0;
        // explaining purpose: dimensions
        int r = a.length, c = a[0].length;
        // explaining purpose: dp[i][j] holds best (maximum) sum to reach (i,j)
        int[][] dp = new int[r][c];

        // explaining purpose: initialize first row
        for (int j = 0; j < c; j++) {
            // explaining purpose: base for max is the same copy
            dp[0][j] = a[0][j];
        }

        // explaining purpose: fill remaining rows using max transition
        for (int i = 1; i < r; i++) {
            // explaining purpose: iterate across columns
            for (int j = 0; j < c; j++) {
                // explaining purpose: value from up
                int up = dp[i - 1][j];
                // explaining purpose: value from up-left or −infinity if missing
                int upLeft = (j - 1 >= 0) ? dp[i - 1][j - 1] : (int)-1e9;
                // explaining purpose: value from up-right or −infinity if missing
                int upRight = (j + 1 < c) ? dp[i - 1][j + 1] : (int)-1e9;
                // explaining purpose: current = cost + maximum parent
                dp[i][j] = a[i][j] + Math.max(up, Math.max(upLeft, upRight));
            }
        }

        // explaining purpose: pick the maximum on the last row as final answer
        int ans = Integer.MIN_VALUE;
        // explaining purpose: scan last row to find the largest
        for (int j = 0; j < c; j++) ans = Math.max(ans, dp[r - 1][j]);
        // explaining purpose: return maximum falling path sum
        return ans;
    }

}
