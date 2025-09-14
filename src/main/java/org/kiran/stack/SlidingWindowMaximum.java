public class SlidingWindowMaximum{
    
    /**
     * Intuiton: 
     * The key idea is to keep a deque that stores indices of elements in decreasing order of their values. 
     * The front of the deque will always be the index of the current window’s maximum. 
     * When we move the window forward, we pop from the front if it falls out of the window, 
     * and we pop from the back while the new element is greater than or equal to those at the back.
     * because they can never be a max for this or any future window.
     * 
     */
    public int[] slidingWindowMaximum(int[] arr) {
        if (nums == null || nums.length == 0) return new int[0];
        if (k == 1) return nums.clone();
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();

        for(int i=0; i<n; i++) {
            // remove indices from front if out of this window
            if (!dq.isEmpty() && dq.peekFirst() <= i - k) {
                dq.pollFirst();
            }

            // remove from back while arr[back] <= arr[i]
            while (!dq.isEmpty() && arr[dq.peekLast()] <= arr[i]) {
                dq.pollLast();
            }

            // push current index
            dq.offerLast(i);

            // once we have formed a window, record the max
            if (i >= k - 1) {
                ans[i - k + 1] = nums[dq.peekFirst()];
            }
        }

        return ans;
    }

}