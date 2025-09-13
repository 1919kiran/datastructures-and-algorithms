class Solution {
    /**
     * Intuition: For each element, how many subarray contributions can it have?
     * If you think about it, it boils down to how far the next smaller element is to the left and right
     * Now, if two equal elements exist, and we always treat them the same way (like > both sides, or >= both sides), 
     * then both elements may get counted as the minimum in overlapping subarrays. That leads to double-counting.
     * So we can make either previousSmallestElementIndexLeft or nextSmallestElementIndexRight strictly smaller
     * 
     */
    public int sumOfSubarrayMinimums(int[] arr) {
        int N = arr.length;
        int[] smallestOnLeft = previousSmallestElementIndexLeft();
        int[] smallestOnRight = nextSmallestElementIndexRight();
        long ans = 0;
        for(int i=0; i<N; i++) {
            long contributions = (long)smallestOnLeft[i]*smallestOnRight[i];
            ans += (contributions*arr[i])%Integer.MAX_VALUE;
        }
        return (int) ans;
    }

    private int previousSmallestElementIndexLeft(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int N = nums.length;
        int[] res = new int[N];
        for(int i=0; i<N; i++) {
            int x = stack.peek();
            while(!stack.isEmpty() && nums[i] <= nums[x]) 
                stack.pop();
            res[i] = stack.isEmpty() ? i+1 : i-x;
            stack.push(i);
        }
        return res;
    }

    private int nextSmallestElementIndexRight(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int N = nums.length;
        int[] res = new int[N];
        for(int i=N-1; i>=0; i--) {
            int x = stack.peek();
            while(!stack.isEmpty() && nums[i] <= nums[x]) 
                stack.pop();
            res[i] = stack.isEmpty() ? n-i : x-i;
            stack.push(i);
        }
        return res;
    }
}