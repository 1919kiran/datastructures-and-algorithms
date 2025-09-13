class Solution {
    
    public int trappingRainWater(int[] blocks) {
        int N = blocks.length;
        int[] prefixMax = new int[N];
        int[] suffixMax = new int[N];
        int total = 0;
        prefixMax[0] = blocks[0];
        suffixMax[N-1] = blocks[N-1];
        for(int i=1; i<N; i++) {
            prefixMax[i] = Math.max(blocks[i], prefixMax[i-1]);
        }
        for(int i=N-2; i>=0; i--) {
            suffixMax[i] = Math.max(blocks[i], suffixMax[i+1]);
        }
        for(int i=0; i<N; i++) {
            if(blocks[i] < prefixMax[i] && blocks[i] < suffixMax[i])
                total += Math.min(prefixMax[i], suffixMax[i]) - blocks[i];
        }
        return total;
    }

    /**
     * Water trapped at index i depends on the smaller of the max on the left and the max on the right. 
     * If the left max is smaller, then the amount of water we can trap on the left side is guaranteed, no matter what happens on the right. 
     * Why? Because even if the right max increases further, it doesn’t affect the result for left side — the bottleneck is the left max and vice versa
     */
    public int trappingRainWaterOptimal(int[] blocks) {
        int N = blocks.length;
        int left = 0; right = N-1;
        int leftMax = blocks[i]; rightMax = blocks[N-1];
        int total = 0;
        while(left <= right) {
            if(leftMax <= rightMax) {
                if(blocks[left] < leftMax) {
                    total += leftMax - blocks[left];
                } else {
                    leftMax = blocks[left];
                }
                left++;
            } else {
                if(blocks[right] < rightMax) {
                    total += rightMax - blocks[right];
                } else {
                    rightMax = blocks[right];
                }
                right--;
            }
        }
        return total;
    }
}