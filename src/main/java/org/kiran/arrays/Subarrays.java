public class Subarrays {

    /**
     * Intuition: Two cases we need to handle carefully:
     * - When there is a zero - means the answer can be max of left side or right side
     * - When there is an odd number on minus - if we arrive at a minus number, it will split array into 
     * 2 arrays where there are even number of minus
     * Maintain a running prefix and running suffix 
     */
    public int maxProductSubarray(int nums) {
        int i = 0, n = nums.length;
        int prefix = 1, suffix = 1;
        int maxProduct = Integer.MIN_VALUE;
        for(int i=0; i<nums.length; i++) {
            if(prefix == 0) prefix = 1;
            if(suffix == 0) suffix = 1;
            prefix = prefix * nums[i];
            suffix = suffix * nums[n-i-1];
            maxProduct = Math.max(maxProduct, Math.max(prefix,suffix));
        }
        return maxProduct;
    }
    
}
