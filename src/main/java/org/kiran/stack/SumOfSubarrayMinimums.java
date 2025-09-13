class Solution {
    /**
     * Intuition: For each element, how many subarray contributions can it have?
     * If you think about it, it boils down to how far the next smaller element is to the left and right
     * Now, if two equal elements exist, and we always treat them the same way (like > both sides, or >= both sides), 
     * then both elements may get counted as the minimum in overlapping subarrays. That leads to double-counting.
     * So we can make either previousSmallestElementIndexLeft or nextSmallestElementIndexRight strictly smaller
     * 
     */
    public int sumSubarrayMins(int[] arr) {
        int N = arr.length;
        int MOD = (int)1e9 + 7;

        int[] left = previousSmallerDistance(arr);
        int[] right = nextSmallerDistance(arr);

        long ans = 0;
        for (int i = 0; i < N; i++) {
            long contrib = (long) arr[i] * left[i] * right[i];
            ans = (ans + contrib) % MOD;
        }
        return (int) ans;
    }

    // distance to previous smaller element
    private int[] previousSmallerDistance(int[] nums) {
        int N = nums.length;
        int[] res = new int[N];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty() &&  nums[i] < nums[stack.peek()]) {
                stack.pop();
            }
            // If stack is empty, no smaller element exists to the left.
            // Distance is then from current index back to the left boundary (i + 1 steps).
            res[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
            stack.push(i);
        }
        return res;
    }

    // distance to next smaller element
    private int[] nextSmallerDistance(int[] nums) {
        int N = nums.length;
        int[] res = new int[N];
        Stack<Integer> stack = new Stack<>();

        for (int i = N - 1; i >= 0; i--) {
            // allow equality here to avoid double counting
            // If stack is empty, no smaller element exists to the right.
            // Distance is then from current index forward to the right boundary (N - i steps).
            while (!stack.isEmpty() && nums[i] <= nums[stack.peek()]) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? N - i : stack.peek() - i;
            stack.push(i);
        }
        return res;
    }

}