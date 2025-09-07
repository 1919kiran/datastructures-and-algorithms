import java.util.*; // import for lists and arrays

class Solution {
    // main method to return all unique subsets
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums); // sort to group duplicates together
        List<List<Integer>> result = new ArrayList<>(); // list to hold all subsets
        backtrack(0, nums, new ArrayList<>(), result); // start backtracking from index 0
        return result; // return final answer
    }
    
    // helper method for backtracking
    private void backtrack(int index, int[] nums, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current)); // add current subset to result
        
        for (int i = index; i < nums.length; i++) {
            // skip duplicates: if current number is same as previous and we are at same recursive level
            if (i > index && nums[i] == nums[i - 1]) continue;
            
            current.add(nums[i]); // include nums[i] in current subset
            backtrack(i + 1, nums, current, result); // move to next index
            current.remove(current.size() - 1); // backtrack: remove last added number
        }
    }
}
