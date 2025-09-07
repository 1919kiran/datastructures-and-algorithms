import java.util.*; // import for lists and arrays

class Solution {
    /**
     * Intuition: Check all the candidates and make decision to pick or skip
     * Skip when the element is duplicate within the same level, but we can include it if it's from previous level
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates); // sort so duplicates are adjacent
        List<List<Integer>> result = new ArrayList<>(); // to store all unique combinations
        backtrack(0, candidates, target, new ArrayList<>(), result); // start recursion
        return result; // return final list
    }
    
    // helper method for backtracking
    private void backtrack(int index, int[] nums, int target, List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            // found a valid combination, add a copy
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = index; i < nums.length; i++) {
            // skip duplicates: if at same level and same as previous number
            if (i > index && nums[i] == nums[i - 1]) continue;
            
            // if current number is greater than target, break since further ones are larger
            if (nums[i] > target) break;
            
            current.add(nums[i]); // include current number
            backtrack(i + 1, nums, target - nums[i], current, result); // move to next index
            current.remove(current.size() - 1); // backtrack
        }
    }
}
