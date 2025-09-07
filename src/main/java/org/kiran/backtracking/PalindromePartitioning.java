import java.util.*; // i need lists and arraylist

class Solution {
    /**
     * Intuition: There will be (n-1)! permutations with first char is common and there will be n such permutations resulting in n*(n-1)! = n! permutations
     * and there will be (n-2)! permutations where first 2 chars are fixed and so on
     * So the first char of kth permutation is determined by out of which n blocks each of size (n-1)! k falls into = k / blocksize
     * Within the block, the position of k can be determined by k % blocksize
     * 
     */
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>(); // this will hold all the valid partitions
        List<String> path = new ArrayList<>(); // this holds the current partition we are building
        dfs(0, s, path, res); // start backtracking from index 0
        return res; // return the final list of partitions
    }

    // depth-first search to build partitions
    private void dfs(int idx, String s, List<String> path, List<List<String>> res) {
        if (idx == s.length()) { // if we reached the end of the string
            res.add(new ArrayList<>(path)); // we found a valid partition, copy it into result
            return; // backtrack to explore other options
        }

        for (int end = idx; end < s.length(); end++) { // try all end positions for the next piece
            if (isPalindrome(s, idx, end)) { // if the substring s[idx..end] is a palindrome
                path.add(s.substring(idx, end + 1)); // choose it by adding to the current path
                dfs(end + 1, s, path, res); // recurse from the next starting index
                path.remove(path.size() - 1); // undo the choice to try the next end position
            } // if it’s not a palindrome, we just skip that choice and continue the loop
        }
    }

    // helper to check if s[l..r] is a palindrome using two pointers
    private boolean isPalindrome(String s, int l, int r) {
        while (l < r) { // move pointers towards the center
            if (s.charAt(l) != s.charAt(r)) return false; // mismatch means not a palindrome
            l++; // move left pointer right
            r--; // move right pointer left
        }
        return true; // we didn’t find a mismatch, so it is a palindrome
    }
}
