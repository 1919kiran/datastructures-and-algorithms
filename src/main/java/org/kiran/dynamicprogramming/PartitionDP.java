package org.kiran.dynamicprogramming;

public class PartitionDP {

    int minCostMCM(int[] arr) {
        if (arr == null || arr.length <= 2) return 0;
        int n = arr.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            // purpose: fill each row with sentinel -1 to mark uncomputed states
            java.util.Arrays.fill(dp[i], -1);
        }
        return solve(1, n - 1, arr, dp);
    }

    private int solve(int i, int j, int[] arr, int[][] dp) {
        // purpose: base case — a single matrix (i == j) needs zero multiplications
        if (i == j) return 0;
        // purpose: return memoized value if available to avoid recomputing this interval
        if (dp[i][j] != -1) return dp[i][j];
        // purpose: start with a large value since we are minimizing over possible splits
        int best = Integer.MAX_VALUE;
        // purpose: try all partition points k that split [i..j] into [i..k] and [k+1..j]
        for (int k = i; k <= j - 1; k++) {
            // purpose: optimal cost of left subchain [i..k]
            int left = solve(i, k, arr, dp);
            // purpose: optimal cost of right subchain [k+1..j]
            int right = solve(k + 1, j, arr, dp);
            // purpose: extra cost to multiply the two resulting matrices after splitting at k
            int merge = arr[i - 1] * arr[k] * arr[j];
            // purpose: total cost if we place the split at k
            int cost = left + right + merge;
            // purpose: keep the minimum cost over all possible k
            if (cost < best) best = cost;
        }
        // purpose: memoize the optimal cost for [i..j] so repeated queries are O(1)
        dp[i][j] = best;
        // purpose: return the cached value for this interval
        return dp[i][j];
    }

    int minCostMCMTab(int[] arr) {
        // purpose: handle chains with <=1 matrix (no multiplications needed)
        if (arr == null || arr.length <= 2) return 0;

        // purpose: n is the dimension array length; valid matrix indices are 1..n-1
        int n = arr.length;

        // purpose: dp[i][j] stores the min cost to multiply matrices i..j (1-based over matrices)
        int[][] dp = new int[n][n];

        // purpose: fill the table so dependencies are available when needed
        for (int i = n - 1; i >= 1; i--) {
            // purpose: base case for single matrix — zero cost on the diagonal
            dp[i][i] = 0;

            // purpose: j starts just to the right of i and moves outward to build larger intervals
            for (int j = i + 1; j <= n - 1; j++) {
                // purpose: we’re minimizing, so start with a large value
                int mini = Integer.MAX_VALUE;

                // purpose: try every split k that partitions [i..j] into [i..k] and [k+1..j]
                for (int k = i; k <= j - 1; k++) {
                    // purpose: cost to multiply left and right subchains plus the merge cost at this split
                    int steps =
                        // purpose: merge cost for resulting matrices: (arr[i-1] x arr[k]) * (arr[k] x arr[j])
                        arr[i - 1] * arr[k] * arr[j]
                        // purpose: add optimal cost to produce left result matrix
                        + dp[i][k]
                        // purpose: add optimal cost to produce right result matrix
                        + dp[k + 1][j];

                    // purpose: keep the best split seen so far
                    if (steps < mini) mini = steps;
                }

                // purpose: store the optimal cost for interval [i..j]
                dp[i][j] = mini;
            }
        }

        // purpose: final answer is the cost to multiply the whole chain [1..n-1]
        return dp[1][n - 1];
    }
    
}
