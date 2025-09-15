public class FrogJumps {

    /**
     * recurrence relation:
     * f(i) = min( |heights[i] - heights[i-j]| + f(i-j) )
     * 
     * time complexity is O(n*k)
     * space complexity is O(n) for the dp array and O(n) in the worst case for recursion depth
     * 
     */
    public int minEnergyMemo(int[] heights, int k) { // defining the function that returns the minimum energy for given heights and max jump k
        int n = heights.length; // capturing the size because we use it repeatedly and it clarifies bounds logic
        int[] dp = new int[n]; // dp[i] will mean "minimum energy to reach i" in the memoized top-down sense
        Arrays.fill(dp, -1); // using -1 to mark "unsolved" states so I can skip recomputation later
        return solve(n - 1, heights, k, dp); // asking for f(n-1), which by our definition is the goal state
    }

    // helper performs the recurrence with caching; index i is the current target state f(i)
    private int solve(int i, int[] heights, int k, int[] dp) { // classic top-down signature carrying index, data, parameter k, and memo
        if (i == 0) return 0; // base case: reaching stone 0 costs nothing because we start there
        if (dp[i] != -1) return dp[i]; // memo check: if f(i) is already computed, return it immediately to avoid rework

        int best = Integer.MAX_VALUE; // starting with a large sentinel so any real path cost will be lower
        for (int j = 1; j <= k; j++) { // iterating over all allowed jump lengths from 1 up to k
            int prev = i - j; // computing the previous index we would land from, to match the recurrence f(i) over f(i-j)
            if (prev >= 0) { // respecting array bounds so we only consider valid previous stones
                int prevCost = solve(prev, heights, k, dp); // recursively solving f(prev), trusting memo to keep this O(1) after first time
                int stepCost = Math.abs(heights[i] - heights[prev]); // computing the immediate jump cost |h[i] - h[prev]| as per problem statement
                int total = prevCost + stepCost; // combining subproblem cost with edge cost to represent this path’s total up to i
                if (total < best) best = total; // relaxing the current best using this candidate, exactly like the "min" in the recurrence
            }
        }
        dp[i] = best; // storing the solved value of f(i) so future callers reuse it and we keep linear number of solves
        return best; // returning the optimal cost for f(i) to the caller up the recursion
    }

    /**
     * copy the base case: in memo form, f(0) = 0; in tabulation that becomes dp[0] = 0. 
     * second, I need the evaluation order so that when I compute dp[i], all dp[i − j] for j in [1..k] are already ready. 
     * the natural order is increasing i from 1 to n − 1, which is the topological order of this 1D DAG. 
     * third, I translate the recurrence literally: dp[i] = min over j of dp[i − j] + |h[i] − h[i − j]|. 
     * fourth, I replace recursion with loops: the outer loop is i from 1 to n − 1, the inner loop is j from 1 to k checking bounds.
     * 
     */
    public int minEnergyTab(int[] heights, int k) { // defining bottom-up interface mirroring the memo version
        int n = heights.length; // size again for clarity and bounds checking
        int[] dp = new int[n]; // dp[i] still means minimum energy to reach i, but we’ll fill it left to right
        dp[0] = 0; // copying the memo base case f(0)=0 into the table as the starting known value

        for (int i = 1; i < n; i++) { // progressing i forward so that when we reach i all needed dp[i-j] are already computed
            int best = Integer.MAX_VALUE; // initializing a large value so any real candidate will be better
            for (int j = 1; j <= k; j++) { // trying every allowed jump size just like the recurrence demands
                int prev = i - j; // the previous index that would jump into i
                if (prev >= 0) { // ensure we only read valid dp states within the array
                    int prevCost = dp[prev];
                    int stepCost =  Math.abs(heights[i] - heights[prev]);
                    int total = prevCost + stepCost; // literal recurrence: dp[i-j] + |h[i]-h[i-j]|
                    if (total < best) best = total; // relax the answer for dp[i] with the cheapest seen so far
                }
            }
            dp[i] = best; // finalize dp[i] after considering all parents, making it available to future states
        }

        return dp[n - 1]; // the answer is the cost to reach the last index, matching the problem’s goal
    }



}