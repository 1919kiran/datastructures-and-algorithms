class Solution {
    
    /**
     * Intuition:
     * Basically, if we look at each column of the matrix, it is similar to a histogram bar, but has breaking bars due to 0.
     * So we can consider row wise hisotgram where height if each bar is segmented by 0
     * If we can find out the max area of each row wise histogram, we will have the answer
     * A simple way to do it is by converting the input matrix to a prefixSum matrix (for calculating heigh of each row wise histogram)
     * But the prefix sum is from top to bottom
     */
    public int maximalRectangle(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int n = matrix.length, m = matrix[0].length;
        int[][] topDownPrefixSum = new int[n][m];

        for(int j=0; j<m; j++) {
            int sum = 0;
            for(int i=0; i<n; i++) {
                if(matrix[i][j] == 0) {
                    sum = 0;
                } else {
                    sum += matrix[i][j];
                    topDownPrefixSum[i][j] = sum;
                }
            }
        }

        int maxArea = 0;
        for(int i=0; i<n; i++) {
            maxArea = Math.max(maxArea, largestRectangleHistogram(matrix[i]));
        }

        return maxArea;
    }

    /**
     * We can reuse heights array.. since if element is 1 we can increment the height from previous height by 1 else we can reset to 0
     */
    public int maximalRectangleOpt(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int n = matrix.length, m = matrix[0].length;
        int[] heights = new int[m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // if this cell is 1, increment height, else reset to 0
                heights[j] = (matrix[i][j] == 1) ? heights[j] + 1 : 0;
            }
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }

        return maxArea;
    }

}