public class Traingle {

    public static int minimumTotalMemo(int[][] triangle) {
        // guard: empty triangle returns 0 to be safe
        if (triangle == null || triangle.length == 0) return 0;
        // cache: number of rows for sizing and base checks
        int n = triangle.length;
        // storage: dp[i][j] caches f(i,j); using MIN_VALUE as "uncomputed" sentinel
        int[][] dp = new int[n][n];
        // init: mark every state as not computed yet
        for (int[] row : dp) Arrays.fill(row, Integer.MIN_VALUE);
        // start: compute from the fixed start (0,0)
        return dfs(0, 0, triangle, dp);
    }

    // purpose: f(i,j) = min path sum from (i,j) to any cell on the bottom row
    private static int dfs(int i, int j, int[][] tri, int[][] dp) {
        // base: when we’re on the last row, the cost is exactly the cell’s value
        if (i == tri.length - 1) return tri[i][j];
        // memo-hit: reuse previously computed state
        if (dp[i][j] != Integer.MIN_VALUE) return dp[i][j];
        // transition: go straight down to (i+1,j) and add current cell cost
        int down = tri[i][j] + dfs(i + 1, j, tri, dp);
        // transition: go down-right to (i+1,j+1) and add current cell cost
        int diag = tri[i][j] + dfs(i + 1, j + 1, tri, dp);
        // choice: keep the cheaper of the two routes
        dp[i][j] = Math.min(down, diag);
        // return: hand back cached answer
        return dp[i][j];
    }

    public static int minimumTotalSpace(int[][] triangle) {
        // guard: empty triangle
        if (triangle == null || triangle.length == 0) return 0;
        // cache: number of rows
        int n = triangle.length;
        // storage: prev holds the dp values for the row below current i
        int[] prev = new int[n];
        // init: base row becomes our starting "prev"
        for (int j = 0; j < n; j++) {
            // copy: f(n-1,j) = triangle[n-1][j]
            prev[j] = triangle[n - 1][j];
        }
        // loop: move upward from second-last row to the top
        for (int i = n - 2; i >= 0; i--) {
            // storage: curr holds the dp values for row i
            int[] curr = new int[n];
            // iterate: process only valid columns 0..i on this row
            for (int j = 0; j <= i; j++) {
                // read: going down uses prev[j] which is dp[i+1][j]
                int down = triangle[i][j] + prev[j];
                // read: going diag uses prev[j+1] which is dp[i+1][j+1]
                int diag = triangle[i][j] + prev[j + 1];
                // write: keep the cheaper route for state (i,j)
                curr[j] = Math.min(down, diag);
            }
            // roll: make current row the "below row" for the next step up
            prev = curr;
        }
        // answer: dp[0][0] is stored at prev[0] after rolling to the top
        return prev[0];
    }
    
}
