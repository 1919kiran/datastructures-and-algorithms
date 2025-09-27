import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Subsequences {

    /**
     * Recurrence:
     * f(i1, i2) -> Longest common subsequence b/w s1[0..i1] and s2[0..i2]
     */
    public static int longestCommonSubsequence(String s1, String s2) {
        int[][] dp = new int[s1.length()][s1.length()];
        for(int[] row : dp) Arrays.fill(row, -1);
        return solveLcsMemo(s1, s2, s1.length()-1, s2.length()-1, dp);
    }

    private static int solveLcsMemo(String s1, String s2, int i1, int i2, int[][]dp) {
        if(i1 < = || i2 < 0)    return 0;
        if(dp[i][j] != -1)  return dp[i][j];
        if(s1.charAt(i1) == s2.charAt(i2)) {
            dp[i][j] = 1 + solveLcsMemo(s1, s2, i1-1, i2-1, dp);
        }
        int skipS1 = dp[i1-1][i2];
        int skipS2 = dp[i1][i2-1];
        dp[i][j] = Math.max(skipS1, skipS2);
        return dp[i][j];
    }

    private static int[][] solveLcsMemo(String s1, String s2, int i1, int i2, int[][]dp) {
        return null;
    }

    private static int lcsTabulation(String s1, String s2) {
        if(s1 == null || s2 == null)    return 0;
        int n = s1.length(), m = s2.length();
        int[][] dp = new int[n+1][m+1];
        for(int[] row : dp) Arrays.fill(row, -1);
        for(int i=1; i<=n; i++) {
            for(int j=1; j<=m; j++) {
                if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    int skipS1 = dp[i-1][j];
                    int skipS2 = dp[i][j-1];
                    curr[j] = Math.max(skipS1, skipS2);
                }
            }
        }
        return dp[n][m];
    }
    
    private static int lcsOptimal(String s1, String s2) {
        if(s1 == null || s2 == null)    return 0;
        int n = s1.length(), m = s2.length();
        int[] prev = new int[m+1];
        for(int i=1; i<=n; i++) {
            int[] curr = new int[m+1];
            for(int j=1; j<=m; j++) {
                if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    curr[j] = 1 + prev[j-1];
                } else {
                    int skipS1 = prev[j];
                    int skipS2 = curr[j-1];
                    curr[j] = Math.max(skipS1, skipS2);
                }
            }
            prev = curr;
        }
        return prev[m];
    }

    /**
     * Vizualize the DP table, where dp[i][j] = 1 + max(dp[i-1][j], dp[i][j-1])
     * i.e, max of elements that is up and left of the element, this operation is done when 
     * s1[i][j] == s2[i][j].. so now since we want to reverse engineer, think the other way,
     */
    public static String printLcs(String s1, String s2) {
        int[][] dp = new int[s1.length()][s1.length()];
        for(int[] row : dp) Arrays.fill(row, -1);
        int x = solveLcsMemo(s1, s2, s1.length()-1, s2.length()-1, dp);
        int n = s1.length(), m = s2.length();
        int i=n, j=m;
        StringBuilder sb = new StringBuilder();
        while(i>0 && j>0) {
            if(s1.charAt(i-1) == s2.charAt(j-1)) {
                sb.append(s1.charAt(i-1));
                i--; j--;
            } else if(dp[i-1][j] > dp[i][j-1]) {
                i--;
            } else {
                j--;
            }
        }
        return sb.toString();
    }

    public static int longestCommonSubstringTab(String s1, String s2) {
        if(s1 == null || s2 == null)    return 0;
        int n = s1.length(), m = s2.length();
        int[][] dp = new int[n+1][m+1];
        for(int[] row : dp) Arrays.fill(row, -1);
        int best = 0;

        for(int i=1; i<=n; i++) {
            for(int j=1; j<=m; j++) {
                if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    best = Math.max(best, dp[i][j]);
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        return best;
    }

    public static int longestPalindromicSubsequence(String s) {
        return lcsTabulation(s, s.reverse());
    }

    /**
     * Intuition: What can i not touch?
     * Don't touch the longest palnidrome, so that it remains constant
     * then remaining portion needs to be inserted in the middle
     */
    public static int minInsertionsToMakeStringPalindrome(String s) {
        return s.length() - longestPalindromicSubsequence(s);
    }

    /**
     * Intuition: What can i not touch?
     * Don't touch the longest common subsequence, so that it remains constant
     * To make s1 same as s2, we need to find LCS(s1,s2)
     * In order to make s1 the same as s2, we have to do:
     * deletions = n - LCS(s1,s2) to delete chars not there in s2
     * insertions = m - LCS(s1,s2) to insert chars there in s1
     * Total ops = n + m - 2*LCS(s1,s2)
     */
    public static int minUpdatesToConvertString(String s1, SString s2) {
        return s1.length()+s2.length() - 2*longestCommonSubsequence(s1, s2);
    }

    /**
     * Supersequence is a string that contains both strings s1 and s2 where ordering of chars s1 and s2 is preserved
     * Logically it is union of s1 and s2 where common characters are not copied from both i.e they are taken only once.
     * So lenght of shorted common super sequence = n + m - LCS(s1,s2)
     */
    public static int shortestCommonSuperSequence(String s1, String s2) {
        return s1.length()+s2.length() - longestCommonSubsequence(s1, s2);
    }

    public static int lengthOfLISSpaceOpt(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int n = nums.length;
        // prev: represents dp[i+1][*]; initialize to all zeros which matches the base row at i = n
        int[] prev = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            int[] curr = new int[n + 1];
            for (int prevIdx = i - 1; prevIdx >= -1; prevIdx--) {
                int notTake = prev[prevIdx + 1];
                int take = Integer.MIN_VALUE / 4;
                if (prevIdx == -1 || nums[i] > nums[prevIdx]) {
                    take = 1 + prev[i + 1];
                }
                curr[prevIdx + 1] = Math.max(notTake, take);
            }
            prev = curr;
        }
        return prev[0];
    }

    public static int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int n = nums.length;
        int[] dp = new int[n];
        int maxLen = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if(nums[j] < nums[i] && dp[i] > 1+dp[j]) {
                    dp[i] = 1 + dp[j];
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }

    /**
     * the idea is to maintain a list tails where tails[k] is the smallest possible tail value of any increasing subsequence of length k+1 we’ve seen so far. 
     * when a new number x comes in, if x is bigger than the last tail we extend the list; otherwise we find the first tail ≥ x and replace it 
     * with x using lower_bound. replacing doesn’t change any LIS length we’ve already achieved, it just makes the tails as small as possible 
     * so future numbers have a better chance to extend them. the size of tails at the end is the LIS length.
     */
    public static int lengthOfLISBinarySearch(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        // keeping the minimal possible tail for all subsequence lengths found so far
        List<Integer> tails = new ArrayList<>();
        // seeding the structure with the first element as a length-1 subsequence tail
        tails.add(nums[0]);
        // scanning the remaining elements to either extend or improve existing tails
        for (int i = 1; i < nums.length; i++) {
            // reading current value once for clarity
            int x = nums[i];
            // when x is bigger than the largest tail, it extends the longest subsequence
            if (x > tails.get(tails.size() - 1)) {
                // appending x grows the best length by one
                tails.add(x);
            } else {
                // finding the first position whose tail is >= x to keep tails minimal
                int pos = lowerBound(tails, x);
                // replacing that tail with x keeps subsequence lengths but improves future extendability
                tails.set(pos, x);
            }
        }
        // reporting the length of the longest increasing subsequence as the list size
        return tails.size();
    }

    // finding the first index in 'tails' whose value is >= target (classic lower_bound)
    private static int lowerBound(List<Integer> a, int target) {
        int l = 0, r = a.size() - 1, ans = a.size();
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (a.get(m) >= target) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }

    /**
     * We sort the given in input array and perform longest increasing subsequence with the change that rather than increasing the prev element should be
     * divisible by the current element.because if we check the prev element, all the other elements are automatically checked.
     */
    public static int longestDivisibleSubsequence(int[] nums) {
        return -1;
    }

    /**
     * We first have the original array and do LIS from left to right.. now we do an LIS from right to left to get LDS
     * LBS = LIS[i] + LDS[i] - 1 (since we count the same elemtent twice)
     */
    public static int longestBitonicSubsequence(int[] nums) {
        return -1;
    }

}