import java.util.*;

class SolutionApproach1 {

    /**
     * Intuition: For all elements in the array, check if it was already picked or not
     * If not picked, then pick it and add it to the current permutation 
     */
    public List<List<Integer>> permute1(int[] nums) {
        // result list to store all permutations
        List<List<Integer>> result = new ArrayList<>();
        // list to keep track of current path
        List<Integer> current = new ArrayList<>();
        // boolean array to mark visited elements
        boolean[] visited = new boolean[nums.length];
        // start recursion
        backtrack(nums, visited, current, result);
        return result;
    }

    // helper recursion method
    private void backtrack1(int[] nums, boolean[] visited, List<Integer> current, List<List<Integer>> result) {
        // if path has length equal to nums, add to result
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current)); // copy current path
            return;
        }

        // try each element
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i]) { // if not used yet
                visited[i] = true; // mark it used
                current.add(nums[i]); // add to path
                backtrack(nums, visited, current, result); // recurse
                current.remove(current.size() - 1); // remove last added
                visited[i] = false; // unmark for next use
            }
        }
    }

    /**
     * Intuition: For all elements in the array, try swapping it with all elements to the right of it
     * 
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, result); // start recursion from index 0
        return result;
    }

    // helper method for recursion
    private void backtrack(int[] nums, int index, List<List<Integer>> result) {
        // if index reached the end, add current permutation
        if (index == nums.length) {
            List<Integer> permutation = new ArrayList<>();
            for (int num : nums) {
                permutation.add(num); // convert array to list
            }
            result.add(permutation);
            return;
        }

        // swap current index with each i
        for (int i = index; i < nums.length; i++) {
            swap(nums, index, i); // put nums[i] at current index
            backtrack(nums, index + 1, result); // recurse for next index
            swap(nums, index, i); // backtrack to restore
        }
    }

    // simple swap method
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
