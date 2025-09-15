import java.util.*;

public class MaxSumNonAdjacentMemo {

    /**
     * Recurrence: 
     * f(i) = max(nums[i]+f(i-2), 0+f(i-1))
     */
    public static int maxNonAdjacentSumMemo(int[] nums) {
        // purpose: handle empty input safely by returning 0 as there's nothing to pick
        if (nums == null || nums.length == 0) return 0;
        // purpose: allocate dp with sentinel values to mark "uncomputed" states
        int n = nums.length;
        // purpose: using Integer.MIN_VALUE to indicate a state hasn't been computed yet
        int[] dp = new int[n];
        // purpose: fill the memo array with sentinel so we can check computed vs not computed
        Arrays.fill(dp, Integer.MIN_VALUE);
        // purpose: kick off recursion from the last index since f(i) depends on smaller indices
        return solve(n - 1, nums, dp);
    }

    // Cannot pick adjacent elements and nums is circular (so can't pick 0 and n-1 in the same combo)
    public static int maxNonAdjacentSumCircularMemo(int[] nums) {
        // purpose: handle empty input safely by returning 0 as there's nothing to pick
        if (nums == null || nums.length == 0) return 0;
        // purpose: allocate dp with sentinel values to mark "uncomputed" states
        int n = nums.length;
        // purpose: using Integer.MIN_VALUE to indicate a state hasn't been computed yet
        int[] dp = new int[n];
        // purpose: fill the memo array with sentinel so we can check computed vs not computed
        Arrays.fill(dp, Integer.MIN_VALUE);
        // Need to remove nums[0] in withFirst and remove nums[n-1] in withLast
        int withFirst = solve(n - 1, nums, dp);
        int withLastt = solve(n - 1, nums, dp)
        return Math.max(withFirst, withLastt);
    }

    // purpose: recursive function f(i) that returns best sum using indices [0..i] with no adjacent picks
    private static int solve(int i, int[] nums, int[] dp) {
        // purpose: when i is below zero, there is nothing left to pick, so the contribution is zero
        if (i < 0) return 0;
        // purpose: base case for the first element; choose it as the only candidate
        if (i == 0) return nums[0];
        // purpose: if we have already computed f(i), just reuse it to avoid recomputation
        if (dp[i] != Integer.MIN_VALUE) return dp[i];
        // purpose: option 1 - pick nums[i], which forces us to skip i-1 and jump to i-2
        int pick = nums[i] + solve(i - 2, nums, dp);
        // purpose: option 2 - skip nums[i], so we move to i-1 without adding anything
        int skip = solve(i - 1, nums, dp);
        // purpose: take the better of picking or skipping and store in the memo
        dp[i] = Math.max(pick, skip);
        // purpose: return the cached answer to callers
        return dp[i];
    }

    /**
     * dp[0] = nums[0]. For i = 1, the best is max(nums[0], nums[1]) because I can only pick one of them. 
     * For i ≥ 2, I copy the same transition: dp[i] = max(nums[i] + dp[i-2], dp[i-1])
     */
    public static int maxNonAdjacentSumTab(int[] nums) {
        // purpose: handle empty input; no picks means zero sum
        if (nums == null || nums.length == 0) return 0;
        // purpose: if there is only one element, the answer is that element itself
        if (nums.length == 1) return nums[0];
        // purpose: allocate dp array where dp[i] represents best sum using indices [0..i]
        int n = nums.length;
        int[] dp = new int[n];
        // purpose: base initialization for i=0, mirroring f(0) from the recurrence
        dp[0] = nums[0];
        // purpose: initialization for i=1, choose the better of picking index 0 or index 1
        dp[1] = Math.max(nums[0], nums[1]);
        // purpose: fill the table for i >= 2 using the same recurrence turned iterative
        for (int i = 2; i < n; i++) {
            // purpose: try picking nums[i] and add dp[i-2] since we must skip the adjacent
            int pick = nums[i] + dp[i - 2];
            // purpose: try skipping nums[i] and carry forward the best up to i-1
            int skip = dp[i - 1];
            // purpose: take the maximum of the two options and store as dp[i]
            dp[i] = Math.max(pick, skip);
        }
        // purpose: dp[n-1] holds the optimal sum for the whole array
        return dp[n - 1];
    }

    public static int maxNonAdjacentSumSpace(int[] nums) {
        // purpose: empty input yields zero because we cannot pick anything
        if (nums == null || nums.length == 0) return 0;
        // purpose: single element case is straightforward; pick the only element
        if (nums.length == 1) return nums[0];
        // purpose: prev represents dp[i-1]; initialize with dp[0] = nums[0]
        int prev = nums[0];
        // purpose: prev2 represents dp[i-2]; initialize with f(-1) = 0
        int prev2 = 0;
        // purpose: iterate from i=1 to end, building the answer in O(1) space
        for (int i = 1; i < nums.length; i++) {
            // purpose: compute picking nums[i] which adds prev2 since we skip adjacent
            int pick = nums[i] + prev2;
            // purpose: compute skipping nums[i] which just keeps prev (best up to i-1)
            int skip = prev;
            // purpose: curr is dp[i], the best between picking or skipping
            int curr = Math.max(pick, skip);
            // purpose: roll the window so next iteration sees these as i-1 and i-2
            prev2 = prev;
            // purpose: update prev to the newly computed dp[i]
            prev = curr;
        }
        // purpose: prev now holds dp[n-1], which is the final answer
        return prev;
    }

}
