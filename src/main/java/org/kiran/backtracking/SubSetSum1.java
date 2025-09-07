import java.util.*; // i need Arrays for nothing fancy but it’s standard to import utilities

import java.util.*; // for list and arraylist

class SubsetSumSolver {
    /**
     * Intuition: Pick or skip
     */
    public List<Integer> subsetSums(int[] arr, int n) {
        List<Integer> res = new ArrayList<>(); // to store all sums
        dfs(0, 0, arr, res); // start recursion at index 0 with sum = 0
        Collections.sort(res); // often we sort the sums as striver does
        return res; // return all sums
    }
    
    // recursive helper to explore subsets
    private void dfs(int idx, int sum, int[] arr, List<Integer> res) {
        if (idx == arr.length) { 
            // reached end of array, record the current sum
            res.add(sum);
            return;
        }
        
        // choice 1: include current element
        dfs(idx + 1, sum + arr[idx], arr, res);
        
        // choice 2: exclude current element
        dfs(idx + 1, sum, arr, res);
    }
}
