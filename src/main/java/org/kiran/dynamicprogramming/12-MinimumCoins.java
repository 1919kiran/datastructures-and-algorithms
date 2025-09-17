public class MinimumCoins {
    
    private static final int INF = (int)1e9;
    /**
     * Recurrence:
     * f(i, t) = min( f(i-1, t), 1 + f(i, t - coins[i]) ) when coins[i] ≤ t
     * f(i, t) is the minimum coins to make sum t using coin indices [0..i]
     */
    public static int coinChangeMemo(int[] coins, int amount) {
        // empty or zero-amount guard; zero amount needs zero coins by definition
        if (amount == 0) return 0;
        // reading the number of coin types for dimensions
        int n = coins.length;
        // building a dp table with n rows and amount+1 columns, filled with -1 for "not computed"
        int[][] dp = new int[n][amount + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        // kicking off recursion from the last index with the full target
        int ans = solve(n - 1, amount, coins, dp);
        // translating our INF sentinel back to -1 for the caller
        return ans >= INF ? -1 : ans;
    }

    // computing f(i, t): minimum coins to make t using coin indices [0..i]
    private static int solve(int i, int t, int[] coins, int[][] dp) {
        // handling the base row where only coin 0 is allowed
        if (i == 0) {
            // if t is divisible by coins[0], we can fill it with repeated coin 0
            if (t % coins[0] == 0) return t / coins[0];
            // otherwise it's impossible with just coin 0
            return INF;
        }
        // serving the cached answer when already computed
        if (dp[i][t] != -1) return dp[i][t];
        // option 1: skip coin i and rely on smaller indices
        int notTake = 0 + solve(i - 1, t, coins, dp);
        // option 2: take coin i if it fits and stay at i because it's infinite supply
        // count this coin (+1) and reduce the remaining target
        int take = coins[i] <= t ? 1 + solve(i, t - coins[i], coins, dp) : INF;
        // caching the better of the two choices for state (i, t)
        return dp[i][t] = Math.min(notTake, take);
    }
}
