public class Solution {
    // compute sum of subarray ranges: sum(max) - sum(min)
    public long subArrayRanges(int[] nums) {
        int n = nums.length;
        if (n < 2) return 0L;

        // prevSmallerDistance: distance to previous strictly smaller (or i+1 to boundary)
        int[] leftSmaller = prevSmallerDistance(nums);
        // nextSmallerDistance: distance to next smaller-or-equal (or n-i to boundary)
        int[] rightSmaller = nextSmallerDistance(nums);
        // prevGreaterDistance: distance to previous strictly greater (or i+1 to boundary)
        int[] leftGreater = prevGreaterDistance(nums);
        // nextGreaterDistance: distance to next greater-or-equal (or n-i to boundary)
        int[] rightGreater = nextGreaterDistance(nums);

        long sumMin = 0L, sumMax = 0L;

        // walk every index to add its contribution as a minimum
        for (int i = 0; i < n; i++) {
            // compute how many subarrays take nums[i] as the minimum
            long cnt = 1L * leftSmaller[i] * rightSmaller[i];
            // add weighted contribution to sumMin
            sumMin += cnt * nums[i];
        }

        // walk every index to add its contribution as a maximum
        for (int i = 0; i < n; i++) {
            // compute how many subarrays take nums[i] as the maximum
            long cnt = 1L * leftGreater[i] * rightGreater[i];
            // add weighted contribution to sumMax
            sumMax += cnt * nums[i];
        }

        // final answer is sum of maxima minus sum of minima
        return sumMax - sumMin;
    }
}
