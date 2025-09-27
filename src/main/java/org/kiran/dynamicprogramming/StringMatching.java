package org.kiran.dynamicprogramming;

import java.util.Arrays;

public class StringMatching {
 
    /**
     * Recurrence: f(i1, i2) -> Number of distinct subsequences that can be found in s1[0..i1] 
     * when smaller string is s2[0..i2]
     * @return
     */
    public int distinctSubsequences(String s, String t) {
        // empty target has exactly one way regardless of s
        if (t.isEmpty()) return 1;
        // empty source cannot form a non-empty target
        if (s.isEmpty()) return 0;
        // sizes for table and indices
        int n = s.length(), m = t.length();
        // dp[i][j] holds number of ways for s[0..i] and t[0..j]; -1 means uncomputed
        int[][] dp = new int[n][m];
        // initialize all states as uncomputed
        for (int i = 0; i < n; i++) Arrays.fill(dp[i], -1);
        // start solving from the last indices of both strings
        long ways = solve(s, t, n - 1, m - 1, dp);
        // cast down; typical platforms expect the final answer within int range
        return (int) ways;
    }

    private int solve(String s1, String s2, int i1, int i2, int[][] dp) {
        if(i2 < 0)   return 1;  // when all characters of s2 are matched, the empty remainder has exactly one way
        if(i1 < 0)   return 0;  // when s1 is exhausted but s2 still has characters, there are zero ways
        if(s1.charAt(i1) == s2.charAt(i2)) {
            int pick = solve(s1, s2, i1-1, i2-1, dp); // pick immediate matching char
            int skip = solve(s1, s2, i1-1, i2, dp); // pick immediate matching char
            dp[i1][i2] = pick + skip;
            return dp[i1][i2];
        }
        dp[i1][i2] = solve(s1, s2, i1-1, i2, dp);
        return dp[i1][i2];
    }

    /**
     * Problem: Min operations to make s1 exactly same as string s2.. you can insert, remove, replace
     * Recurrence:
     * f(i1,i2) -> Min operations to make s1[0..i1] exactly the same as s2[0..i2]
     * 
     */
    private int editDistance(String s1, String s2, int i1, int i2, int[][] dp) {
        // s1 exhausted, which means we need to find min operations to convert empty string to s2[0..i2]
        // so perform i2+1 insertions
        if(i1 < 0) {
            return i2+1;
        }
        // s2 exhausted, which means we need to find min operations to convert s1[0..i1] to empty string
        // so perform i1+1 deletions
        if(i2 < 0) {
            return i1+1;
        }
        if(s1.charAt(i1) == s2.charAt(i2)) {
            dp[i1][i2] =  0 + editDistance(s1, s2, i1-1, i2-1, dp);
        } else {
            // we don't move i1 because we hypothetically inserted a char same as i2 to the right of i1
            // and have to now compare the i1 to next char i2
            int insert = 1 + editDistance(s1, s2, i1, i2-1, dp);
            // since we only delete from s1 we reduce i1 only
            int delete = 1 + editDistance(s1, s2, i1-1, i2, dp);
            // we will replace i1 with char at i2 so since they are matching we check next char in s1 and s2
            int replace = 1 + editDistance(s1, s2, i1-1, i2-1, dp);
            dp[i1][i2] = Math.min(insert, Math.min(delete, replace));
        }
        return dp[i1][i2];
    }

    private int editDistanceTab(String s1, String s2, int[][] dp) {

        for(int i1=0; i1<s1.length(); i1++) dp[i1][0] = i1;
        for(int i2=0; i2<s2.length(); i2++) dp[0][i2] = i2;

        for(int i1=1; i1<=s1.length(); i1++) {
            for(int i2=1; i2<=s2.length(); i2++) {
                if(s1.charAt(i1) == s2.charAt(i2)) {
                    dp[i1][i2] =  0 + dp[i1-1][i2-1];
                } else {
                    // we don't move i1 because we hypothetically inserted a char same as i2 to the right of i1
                    // and have to now compare the i1 to next char i2
                    int insert = 1 + dp[i1][i2-1];
                    // since we only delete from s1 we reduce i1 only
                    int delete = 1 + dp[i1-1][i2];
                    // we will replace i1 with char at i2 so since they are matching we check next char in s1 and s2
                    int replace = 1 + dp[i1-1][i2-1];
                    dp[i1][i2] = Math.min(insert, Math.min(delete, replace));
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }
}
