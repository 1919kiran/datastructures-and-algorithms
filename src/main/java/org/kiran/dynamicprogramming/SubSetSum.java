import java.util.*;

public class SubSetSum {

    /**
     * Recurrence:
     * f(i, t) = f(i-1, t) OR (t≥nums[i] AND f(i-1, t-nums[i]))
     * using elements up to index i, can I make sum t
     */
    public static boolean isSubsetSumMemo(int[] nums, int target) {
        // explaining purpose: quick handle when target is zero; empty subset always works
        if (target == 0) return true;
        // explaining purpose: if no numbers, only target 0 could be true (already handled)
        if (nums == null || nums.length == 0) return false;
        // explaining purpose: dimensions for dp table: indices 0..n-1 and sums 0..target
        int n = nums.length;
        // explaining purpose: use ints as tri-state: -1 = unknown, 0 = false, 1 = true
        int[][] dp = new int[n][target + 1];
        // explaining purpose: initialize entire table to "unknown"
        for (int[] row : dp) Arrays.fill(row, -1);
        // explaining purpose: kick off recursion from last index with full target
        return solve(n - 1, target, nums, dp);
    }

    public static boolean canPartitionEqualSubset(int[] nums, int target) {
        if (target == 0) return true;
        if (nums == null || nums.length == 0) return false;
        int n = nums.length;
        int[][] dp = new int[n][target + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        int totalSum = 0;
        for(int n: nums) {
            totalSum += n;
        }
        target = totalSum / 2;
        return solve(n - 1, target, nums, dp);
    }

    // explaining purpose: f(i, t) — can we form sum t using elements in [0..i]?
    private static boolean solve(int i, int t, int[] nums, int[][] dp) {
        // explaining purpose: if target is 0, we can always form it by choosing nothing
        if (t == 0) return true;
        // explaining purpose: if we’re at the first element, only true when it exactly equals t
        if (i == 0) return nums[0] == t;
        // explaining purpose: return cached boolean if already computed
        if (dp[i][t] != -1) return dp[i][t] == 1;

        // explaining purpose: option 1 — don’t take nums[i], stay at same t
        boolean notTake = solve(i - 1, t, nums, dp);

        // explaining purpose: option 2 — take nums[i] if it fits, reduce target accordingly
        boolean take = false;
        // explaining purpose: only try taking when nums[i] isn’t larger than remaining target
        if (nums[i] <= t) {
            // explaining purpose: check if we can complete the rest with reduced target
            take = solve(i - 1, t - nums[i], nums, dp);
        }

        // explaining purpose: store 1 if any option works, else 0
        dp[i][t] = (take || notTake) ? 1 : 0;
        // explaining purpose: return the computed boolean
        return dp[i][t] == 1;
    }

    public static boolean isSubsetSumTab(int[] nums, int target) {
        // explaining purpose: target 0 is always achievable (empty subset)
        if (target == 0) return true;
        // explaining purpose: handle empty input early
        if (nums == null || nums.length == 0) return false;

        // explaining purpose: number of items
        int n = nums.length;
        // explaining purpose: allocate table where dp[i][t] answers "can we form t using [0..i]?"
        boolean[][] dp = new boolean[n][target + 1];

        // explaining purpose: base — sum 0 is true for all i (pick nothing)
        for (int i = 0; i < n; i++) {
            // explaining purpose: mark dp[i][0] true
            dp[i][0] = true;
        }
        // explaining purpose: base — with only first item, we can form nums[0] if it doesn’t exceed target
        if (nums[0] <= target) {
            // explaining purpose: set dp[0][nums[0]] accordingly
            dp[0][nums[0]] = true;
        }

        // explaining purpose: fill remaining rows using the same recurrence as memo
        for (int i = 1; i < n; i++) {
            // explaining purpose: iterate all target sums from 1..target
            for (int t = 1; t <= target; t++) {
                // explaining purpose: option 1 — don’t take current item
                boolean notTake = dp[i - 1][t];
                // explaining purpose: option 2 — take it if it fits, then we need t - nums[i]
                boolean take = false;
                // explaining purpose: only set take when nums[i] ≤ t
                if (nums[i] <= t) {
                    // explaining purpose: check feasibility from previous row with reduced sum
                    take = dp[i - 1][t - nums[i]];
                }
                // explaining purpose: current cell is true if either option works
                dp[i][t] = take || notTake;
            }
        }

        // explaining purpose: final answer is whether we can form "target" using all items
        return dp[n - 1][target];
    }

    public static boolean isSubsetSumSpace(int[] nums, int target) {
        // explaining purpose: target 0 is always achievable with the empty subset
        if (target == 0) return true;
        // explaining purpose: empty array can’t make a positive target
        if (nums == null || nums.length == 0) return false;

        // explaining purpose: number of items
        int n = nums.length;
        // explaining purpose: prev[t] represents dp[i-1][t] — can we form t using items up to i-1?
        boolean[] prev = new boolean[target + 1];
        // explaining purpose: base — sum 0 is true
        prev[0] = true;
        // explaining purpose: base — first item alone can form nums[0] if it’s within target
        if (nums[0] <= target) {
            // explaining purpose: mark that sum achievable
            prev[nums[0]] = true;
        }

        // explaining purpose: iterate items from index 1 to end
        for (int i = 1; i < n; i++) {
            // explaining purpose: curr[t] will represent dp[i][t] for this row
            boolean[] curr = new boolean[target + 1];
            // explaining purpose: sum 0 remains true for any i (choose nothing)
            curr[0] = true;

            // explaining purpose: compute feasibility for all target sums
            for (int t = 1; t <= target; t++) {
                // explaining purpose: option 1 — skip current item, inherit prev
                boolean notTake = prev[t];
                // explaining purpose: option 2 — take it if it fits and see if the remainder was doable
                boolean take = false;
                // explaining purpose: we can only take when nums[i] ≤ t
                if (nums[i] <= t) {
                    // explaining purpose: check if (t - nums[i]) was achievable with previous items
                    take = prev[t - nums[i]];
                }
                // explaining purpose: current cell is true if either path succeeds
                curr[t] = take || notTake;
            }

            // explaining purpose: roll the window — current row becomes previous for next iteration
            prev = curr;
        }

        // explaining purpose: after processing all items, prev[target] answers the question
        return prev[target];
    }

}
