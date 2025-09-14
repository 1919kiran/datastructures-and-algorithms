class Solution {
    
    /**
     * Better solution:
     * Compute NSE and PSE for each element
     * Why? BEcause PSE and NSE will be the bottleneck heights for area
     * Max width for rectangle formation = NSEI[i] - PSEI[i] - 1
     * Area = arr[i] * (NSEI[i] - PSEI[i] - 1)
     * From here we can apply greedy approach and choose max area among the widht as we move from left to right.
    */
    public int largestRectangleHistogram(int[] arr) {
        int n = arr.length;
        Stack<Integer> st = new Stack<>();
        int bestArea = 0;
        int[] nse = nextSmallerElemenDistance(arr);
        int[] pse = prevSmallerElementDistance(arr);

        for (int i = 0; i <= n; i++) {
            int width = nse[i] - pse[i] - 1;
            int currArea = arr[i] * width;
            bestArea = Math.max(bestArea, currArea);
        }

        return bestArea;
    }

    

    public int largestRectangleOptimal(int[] arr) {
        /**
         * Optimal solution: We should not pre-compute NSE and PSE
         * While traversing we can compute PSE since are going left->right we cannot do it for NSE
         * So we have to compute NSe by somehow coming back. When come back? 
         * Logic: Since for i=0 -> n, while computing PSE(i), we will pop element from stack if stack.peek() 
         * is less than arr[i]. Note that at the same time, we can say the NSE of the popped element is arr[i]
         * In this way we have PSE and NSE while popping for each element
         * After the iteration is complete, we can 2 elements in stack:
         *  - The top one will not have a NSE but may have PSE (only one may of these 2 points holds true. THINK!)
         *  - The bottom one will not have a PSE but may have NSE
         * Basically, while in stack the below element will be it's NSE
         */
        int n = arr.length;
        int maxArea = 0;
        Stack<Integer> st = new Stack<>();

        for(int i=0; i<n; i++) {
            while(!st.isEmpty() && arr[i] < arr[st.peek()]) {
                int poppedHeight = st.pop();
                // NSEI of poppedHeight will be calculated when that bar is being popped by an even smaller bar
                // So the bar responsible for popping poppedHeight will be the next smaller element
                // And the bar below it will be automatically of lesser height since we are maintaining an increasing stack from bottom to top
                int nsei = i;
                int psei = st.isEmpty() ? -1 : st.peek();
                int width = nsei - psei - 1;
                int currArea = width * poppedHeight;
                maxArea = Math.max(maxArea, currArea);
            }
            st.push(i);
        }
        return maxArea;
    }

}