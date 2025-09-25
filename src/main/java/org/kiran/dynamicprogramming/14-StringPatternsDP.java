
public class StringPatternsDP {

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
     * we encounter 
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

    }

    public static int longestPalindromicSubstring(String s) {
        // I handle empty input quickly
        if (s == null || s.isEmpty()) return 0;

        // I store the best length found so far
        int bestLen = 1;

        // I scan every index as a potential center
        for (int i = 0; i < s.length(); i++) {
            // I expand for odd-length palindromes centered at i
            int lenOdd = expand(s, i, i);
            // I update best when odd gives a longer palindrome
            if (lenOdd > bestLen) bestLen = lenOdd;

            // I expand for even-length palindromes centered between i and i+1
            int lenEven = expand(s, i, i + 1);
            // I update best when even gives a longer palindrome
            if (lenEven > bestLen) bestLen = lenEven;
        }

        // I return only the length as your signature indicates
        return bestLen;
    }

    private static int expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    /**
     * Intuition: DP: pal[i][j] is true if s[i] == s[j] and either j−i < 2 or pal[i+1][j−1] is true. 
     * We can fill this for all i, j in O(n^2)
     * suppose we want to pick a palindrome starting at some index i. If there exists any palindrome starting at i with 
     * length ≥ k, then there also exists one with length exactly k or k+1. That greedy choice is safe exactly because 
     * any longer pick can be shrunk to k or k+1 at the same start, and picking shorter never blocks a 
     * better future since we only care about count, not total covered length.
     */
    public int maxPalindromes(String s, int k) {
        // Handle trivial case where each char can be taken
        if (k <= 1) return s.length();

        int n = s.length();

        // Precompute palindrome table pal[i][j]: true if s[i..j] is palindrome
        boolean[][] pal = new boolean[n][n];

        // Every single character is a palindrome
        for (int i = 0; i < n; i++) {
            pal[i][i] = true;
        }

        // Fill table by increasing length to satisfy dependency on pal[i+1][j-1]
        for (int len = 2; len <= n; len++) {
            // Iterate all start indices for this length
            for (int i = 0; i + len - 1 < n; i++) {
                // Compute end index for current substring
                int j = i + len - 1;

                // Check matching ends first
                if (s.charAt(i) == s.charAt(j)) {
                    // For length 2, matching ends suffice; otherwise rely on inner substring
                    if (len == 2) {
                        pal[i][j] = true;
                    } else {
                        // Use DP relation pal[i][j] = pal[i+1][j-1] when ends match
                        pal[i][j] = pal[i + 1][j - 1];
                    }
                } else {
                    // Ends mismatch means not a palindrome
                    pal[i][j] = false;
                }
            }
        }

        // Greedy scan to pick shortest valid palindrome at each step
        int i = 0;
        int count = 0;

        while (i < n) {
            // Check length k first to prefer shorter split
            if (i + k - 1 < n && pal[i][i + k - 1]) {
                // Take the length-k palindrome starting at i
                count++;
                // Jump past this chosen substring to keep non-overlap
                i += k;
            } else if (i + k < n && pal[i][i + k]) {
                // Take the length-(k+1) palindrome if length-k is not available
                count++;
                // Jump past this chosen substring
                i += k + 1;
            } else {
                // No valid palindrome starting at i, move forward by one
                i++;
            }
        }

        return count;
    }


}