// defining the class
class Solution {
    /**
     * Intuition: While checking if a substring is present in wordDict or not we can do this:
     * 1. Check if the current segment prefix was already present in the wordDict
     * 2. Check if the word is present in the dict
     * 
     * The idea is simple - we set a prefix from the outer loop
     * In inner loop from left to right we check if it is in the set
     * 
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        // put all words in a HashSet for O(1) lookups
        Set<String> set = new HashSet<>(wordDict);

        // dp[i] means whether prefix of length i can be segmented
        boolean[] dp = new boolean[s.length() + 1];

        // base case: empty string is valid
        dp[0] = true;

        // loop through the string
        for (int i = 1; i <= s.length(); i++) {
            // check all possible splits
            for (int j = 0; j < i; j++) {
                // if prefix until j is valid and substring j..i is in dictionary
                if (dp[j] && set.contains(s.substring(j, i))) {
                    dp[i] = true;  // then prefix until i is valid
                    break;  // no need to check further splits
                }
            }
        }

        // final answer: can we segment the whole string
        return dp[s.length()];
    }
}
