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
    
}
