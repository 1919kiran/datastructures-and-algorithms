class Solution {
    
    public int largestRectangle(int[] arr) {
        /**
         * Better solution:
         * Compute NSE and PSE for each element
         * Max width for rectangle formation = NSEI[i] - PSEI[i] - 1
         * Area = arr[i] * (NSEI[i] - PSEI[i] - 1)
         * From here we can apply greedy approach and choose max area among the widht as we move from left to right.
         */
        return 0;
    }

    public int largestRectangleOptimal(int[] arr) {
        /**
         * Optimal solution:
         * 
         */
        return 0;
    }

}