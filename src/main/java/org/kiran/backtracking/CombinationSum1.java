import java.util.*; // i need lists and sorting

class CombinationSumSolver {
    /**
     * Intuition: Pick or skip
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates); // sort so i can prune when the number exceeds remaining target
        List<List<Integer>> res = new ArrayList<>(); // this will store all valid combinations
        dfs(0, candidates, target, new ArrayList<>(), res); // start backtracking from index 0
        return res; // return all found combinations
    }
    
    // backtracking helper
    private void dfs(int idx, int[] nums, int target, List<Integer> path, List<List<Integer>> res) {
        if (target == 0) { // if remaining target is exactly zero
            res.add(new ArrayList<>(path)); // current path is a valid combination, so copy it into result
            return; // backtrack to explore other possibilities
        }
        
        if (idx == nums.length) { // if i’ve run out of candidates
            return; // nothing more to try
        }
        
        if (nums[idx] <= target) { // choice 1: take nums[idx] if it doesn’t overshoot
            path.add(nums[idx]); // include it in the current combination
            dfs(idx, nums, target - nums[idx], path, res); // stay on same index because reuse is allowed
            path.remove(path.size() - 1); // undo the choice to try other options
        }
        
        dfs(idx + 1, nums, target, path, res); // choice 2: skip nums[idx] and move to next candidate
    }
}
