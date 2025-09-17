import java.util.*;

public class NinjasTrainingMemo {

    /**
     * Recurrence:
     * best(day, j) = points[day][j] + max(best(day-1, other1), best(day-1, other2))
     */
    public static int maxPointsMemo(int[][] points) {
        // guard against no days so we cleanly return 0
        if (points == null || points.length == 0) return 0;
        // capture number of days
        int n = points.length;
        // allocate dp with n rows and 3 cols where dp[d][j] = best total if we pick j on day d
        int[][] dp = new int[n][3];
        // initialize dp with a sentinel to indicate "not computed"
        for (int[] row : dp) Arrays.fill(row, Integer.MIN_VALUE);
        // compute the best ending with activity 0 on the last day
        int end0 = solve(n - 1, 0, points, dp);
        // compute the best ending with activity 1 on the last day
        int end1 = solve(n - 1, 1, points, dp);
        // compute the best ending with activity 2 on the last day
        int end2 = solve(n - 1, 2, points, dp);
        // return the maximum among the three possible last-day activities
        return Math.max(end0, Math.max(end1, end2));
    }

    // top-down recurrence where we force day d to use activity j
    private static int solve(int day, int j, int[][] points, int[][] dp) {
        // base case for the first day, just take that day's points for activity j
        if (day == 0) return points[0][j];
        // reuse cached value if already computed
        if (dp[day][j] != Integer.MIN_VALUE) return dp[day][j];
        // compute the best among the two allowed previous-day activities (not equal to j)
        int bestPrev = Integer.MIN_VALUE;
        // iterate over the three activities to choose a previous different from j
        for (int prev = 0; prev < 3; prev++) {
            // skip the same activity to obey the constraint
            if (prev == j) continue;
            // candidate total if yesterday was 'prev' and today is 'j'
            int candidate = solve(day - 1, prev, points, dp);
            // keep the best achievable yesterday
            bestPrev = Math.max(bestPrev, candidate);
        }
        // today we add points[day][j] on top of bestPrev
        dp[day][j] = points[day][j] + bestPrev;
        // return and allow reuse
        return dp[day][j];
    }

    public static int maxPointsTab(int[][] points) {
        // handle empty schedule safely
        if (points == null || points.length == 0) return 0;
        // number of days
        int n = points.length;
        // dp table where dp[d][last] is best score up to day d if previous activity is 'last'
        int[][] dp = new int[n][4];

        // initialize the base for day 0 according to allowed tasks
        // when last == 0, we can do tasks 1 or 2 on day 0
        dp[0][0] = Math.max(points[0][1], points[0][2]);
        // when last == 1, we can do tasks 0 or 2 on day 0
        dp[0][1] = Math.max(points[0][0], points[0][2]);
        // when last == 2, we can do tasks 0 or 1 on day 0
        dp[0][2] = Math.max(points[0][0], points[0][1]);
        // when last == 3 (no restriction), we can pick the best among all three
        dp[0][3] = Math.max(points[0][0], Math.max(points[0][1], points[0][2]));

        // fill the table day by day from 1 to n-1
        for (int day = 1; day < n; day++) {
            // compute dp[day][last] for all four values of 'last'
            for (int last = 0; last < 4; last++) {
                // start with zero because scores are non-negative; we’ll maximize over choices
                int best = 0;
                // try each task as today's pick
                for (int task = 0; task < 3; task++) {
                    // skip if this task violates the 'no same as last' rule
                    if (task == last) continue;
                    // today’s points plus best up to previous day if we end yesterday with 'task'
                    int candidate = points[day][task] + dp[day - 1][task];
                    // keep the max across allowed tasks
                    best = Math.max(best, candidate);
                }
                // store the best value for state (day, last)
                dp[day][last] = best;
            }
        }

        // final answer is with 'no restriction' at the last day
        return dp[n - 1][3];
    }

    public static int maxPointsOpt(int[][] points) {
        // handle no days
        if (points == null || points.length == 0) return 0;
        // number of days
        int n = points.length;
        // prev[last] corresponds to dp[day-1][last]
        int[] prev = new int[4];

        // initialize prev as the base for day 0
        // last==0 forbids task 0, so take max of tasks 1 and 2
        prev[0] = Math.max(points[0][1], points[0][2]);
        // last==1 forbids task 1, so take max of tasks 0 and 2
        prev[1] = Math.max(points[0][0], points[0][2]);
        // last==2 forbids task 2, so take max of tasks 0 and 1
        prev[2] = Math.max(points[0][0], points[0][1]);
        // last==3 allows any; take max among all three tasks
        prev[3] = Math.max(points[0][0], Math.max(points[0][1], points[0][2]));

        // iterate over days building current from previous
        for (int day = 1; day < n; day++) {
            // curr[last] will store dp[day][last]
            int[] curr = new int[4];
            // compute for each possible 'last'
            for (int last = 0; last < 4; last++) {
                // start best at 0 and maximize across allowed tasks
                int best = 0;
                // try each task as today’s choice
                for (int task = 0; task < 3; task++) {
                    // enforce the constraint by skipping equal task
                    if (task == last) continue;
                    // combine today’s points with the best for previous day ending at 'task'
                    int candidate = points[day][task] + prev[task];
                    // track the maximum candidate
                    best = Math.max(best, candidate);
                }
                // write the result for state (day, last)
                curr[last] = best;
            }
            // roll the window; current becomes previous for the next iteration
            prev = curr;
        }

        // answer is with no restriction after the final day
        return prev[n == 0 ? 3 : 3];
    }

}
