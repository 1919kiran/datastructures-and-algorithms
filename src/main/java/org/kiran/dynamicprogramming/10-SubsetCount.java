import java.util.*;

public class SubsetCount {

    /**
     * Recurrence: 
     * f(i, t) as “how many subsets from indices [0..i] sum to t?”. 
     * Transition is f(i, t) = f(i-1, t) + f(i-1, t - arr[i])
     * 
     */
    public static int countSubsetsMemo(int[] arr, int target) {
        // handle null/empty and simple base where target is 0 (empty subset counts)
        if (arr == null || arr.length == 0) return target == 0 ? 1 : 0;
        // size of the array
        int n = arr.length;
        // dp[i][t] caches number of ways using indices [0..i] to make sum t; -1 means "uncomputed"
        int[][] dp = new int[n][target + 1];
        // fill memo table with sentinel -1 because valid counts are ≥ 0
        for (int[] row : dp) Arrays.fill(row, -1);
        // start recursion from the last index trying to make full target
        return solve(n - 1, target, arr, dp);
    }

    /**
     * Recurrence:
     * f(i, s1, s2, D) -> Count of subsets till index i such that s1-s2=D
     * By dimentionality reduction, we can reduce this recuurence to 
     * f(i, (total-D)/2)
     */
    public static boolean countSubsetsMemo(int[] arr, int D) {
        int total = 0;
        for(int n: arr) {
            total += arr;
        }
        if(total-D < 0 || total-D %2 == 0)  return false;
        return countSubsetsOpt(arr, (total-D)/2);
    }

    // purpose: f(i, t) -> number of subsets from [0..i] that sum exactly to t
    private static int solve(int i, int t, int[] arr, int[][] dp) {
        // base: when we’re at the very first element, enumerate all correct cases carefully
        if (i == 0) {
            // if target is 0 and arr[0] is 0, we can either take or not take that zero → 2 ways
            if (t == 0 && arr[0] == 0) return 2;
            // if target is 0 (and arr[0] != 0), only one way: take nothing
            if (t == 0) return 1;
            // if target equals the first element, exactly one way: take it
            if (arr[0] == t) return 1;
            // otherwise, no way at the base
            return 0;
        }
        // memoization check: reuse computed state if available
        if (dp[i][t] != -1) return dp[i][t];
        // option 1: do not take arr[i], stay at the same target t
        int notTake = solve(i - 1, t, arr, dp);
        // option 2: take arr[i] if it fits, and count ways to make the remaining sum
        int take = 0;
        // only attempt the "take" branch when t - arr[i] is non-negative
        if (arr[i] <= t) take = solve(i - 1, t - arr[i], arr, dp);
        // cache total ways for this state
        dp[i][t] = take + notTake;
        // return the computed count
        return dp[i][t];
    }

    public static int countSubsetsTab(int[] arr, int target) {
        // quick guard for empty input
        if (arr == null || arr.length == 0) return target == 0 ? 1 : 0;
        // array length
        int n = arr.length;
        // dp[i][t] means number of subsets from [0..i] that sum to t
        int[][] dp = new int[n][target + 1];

        // initialize base row (i == 0) carefully to handle zeros
        // for t == 0, if arr[0] == 0 we have two choices (pick/skip), else exactly one way (skip)
        dp[0][0] = (arr[0] == 0) ? 2 : 1;
        // if first element is non-zero and within target, we can make that exact sum in one way (pick it)
        if (arr[0] != 0 && arr[0] <= target) dp[0][arr[0]] = 1;

        // fill the table for i = 1..n-1
        for (int i = 1; i < n; i++) {
            // compute counts for all possible targets t = 0..target
            for (int t = 0; t <= target; t++) {
                // not taking the current element inherits ways from previous row
                int notTake = dp[i - 1][t];
                // taking the current element adds ways to make (t - arr[i]) previously
                int take = 0;
                // only valid when arr[i] ≤ t
                if (arr[i] <= t) take = dp[i - 1][t - arr[i]];
                // total ways are sum of both choices
                dp[i][t] = take + notTake;
            }
        }

        // answer is the number of ways to make 'target' using all elements
        return dp[n - 1][target];
    }

    public static int countSubsetsOpt(int[] arr, int target) {
        // handle null/empty input
        if (arr == null || arr.length == 0) return target == 0 ? 1 : 0;
        // number of elements
        int n = arr.length;
        // prev[t] holds number of ways to make sum t using elements [0..i-1]
        int[] prev = new int[target + 1];

        // initialize prev for i == 0
        // ways to make sum 0: doubled if first element is 0 (pick or skip), else 1 way (skip)
        prev[0] = (arr[0] == 0) ? 2 : 1;
        // if arr[0] is non-zero and ≤ target, one way exists to make that exact sum
        if (arr[0] != 0 && arr[0] <= target) prev[arr[0]] = 1;

        // iterate remaining items i = 1..n-1
        for (int i = 1; i < n; i++) {
            // curr[t] will represent number of ways for prefix [0..i]
            int[] curr = new int[target + 1];
            // fill all targets t = 0..target using the same transition
            for (int t = 0; t <= target; t++) {
                // do not take arr[i]
                int notTake = prev[t];
                // take arr[i] if it fits
                int take = 0;
                // only valid when arr[i] ≤ t
                if (arr[i] <= t) take = prev[t - arr[i]];
                // sum both choices
                curr[t] = take + notTake;
            }
            // roll the window forward
            prev = curr;
        }

        // final count for target after processing all items
        return prev[target];
    }

}
