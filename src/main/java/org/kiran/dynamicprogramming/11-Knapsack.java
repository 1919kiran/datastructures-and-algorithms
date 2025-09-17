public class Knapsack {

    /**
     * Why greedy does not work:
     * Becuase the problem does not obey law of uniformity and local best option might block 
     * global best fit option in future.
     * 
     * Recurence:
     * f(i, cap) = max( SKIP, PICK ) when wt[i] <= cap, else f(i-1, cap)
     * This denotes till index i, what is the max value you can take with weight cap
     * where SKIP = f(i-1, cap)
     * PICK = val[i] + f(i-1, cap - wt[i])
     * 
     */
    public static int knapSackMemo(int[] wt, int[] val, int W) {
        // handle trivial cases—no capacity or no items means zero value
        if (W <= 0 || wt == null || val == null || wt.length == 0) return 0;
        // store number of items once for easy access
        int n = wt.length;
        // allocate dp with sentinel -1 meaning "uncomputed" for states (i, cap)
        int[][] dp = new int[n][W + 1];
        // fill every state with -1 since valid values are >= 0
        for (int[] row : dp) Arrays.fill(row, -1);
        // start recursion from last index with full capacity
        return solve(n - 1, W, wt, val, dp);
    }

    // f(i, cap) returns best value using items [0..i] within capacity 'cap'
    private static int solve(int i, int cap, int[] wt, int[] val, int[][] dp) {
        // base case—only item 0 is available, take it if it fits
        if (i == 0) {
            // if weight of first item is within capacity, value is val[0], else 0
            return wt[0] <= cap ? val[0] : 0;
        }
        // memoization—if we’ve computed (i, cap) before, reuse it
        if (dp[i][cap] != -1) return dp[i][cap];
        // option 1—skip the current item and keep capacity unchanged
        int notTake = solve(i - 1, cap, wt, val, dp);
        // initialize take branch as impossible by default
        int take = Integer.MIN_VALUE;
        // only try taking when the item fits within remaining capacity
        if (wt[i] <= cap) {
            // value is current item's value plus best with reduced capacity
            take = val[i] + solve(i - 1, cap - wt[i], wt, val, dp);
        }
        // choose the better of taking or skipping and cache the result
        dp[i][cap] = Math.max(notTake, take);
        // return the cached optimal value for (i, cap)
        return dp[i][cap];
    }

    public static int knapSackTab(int[] wt, int[] val, int W) {
        if (W <= 0 || wt == null || val == null || wt.length == 0) return 0;
        // (index,cap)
        int[][] dp = new int[n][W + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        for(int cap=0; cap<W; cap++) {
            dp[0][cap] = (wt[0] < cap) val[0] : 0;
        }
        for(int i=0; i<n; i++) {
            for(int cap=0; cap<=W; cap++) {
                int skip = dp[i-1][cap];
                int pick = wt[i]<=cap ? dp[i-1][cap-wt[i]] : Integer.MIN_VALUE;
                dp[i][cap] = Math.max(pick, skip);
            }
        }
        return dp[n-1][W];
    }

    public static int knapSackOpt(int[] wt, int[] val, int W) {
        if (W <= 0 || wt == null || val == null || wt.length == 0) return 0;
        // (index,cap)
        int[] prev = new int[W + 1];
        for(int cap=0; cap<n; cap++) {
            prev[i] = (wt[0] < cap) val[0] : 0;
        }
        for(int i=0; i<n; i++) {
            int[] curr = new int[W+1];
            for(int cap=0; cap<=W; cap++) {
                int skip = prev[cap];
                int pick = wt[i]<=cap ? prev[cap-wt[i]] : Integer.MIN_VALUE;
                curr[cap] = Math.max(pick, skip);
            }
            prev = curr;
        }
        return prev[W];
    }
    
}
