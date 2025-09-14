public class MaxOfMinsOfAllWindows{
    
    /**
     * Intuition:
     */
    // main method that returns max of minimums for every window size 1..n
    public int[] maxOfMinForEveryWindow(int[] arr) {
        int n = arr.length;
        int[] left = previousSmallerStrict(arr);
        int[] right = nextSmallerOrEqual(arr);
        int[] ans = new int[n];
        Arrays.fill(ans, Integer.MIN_VALUE);

        // iterate each element to compute the window length it governs as the minimum
        for (int i = 0; i < n; i++) {
            // compute length as gap between next and previous smaller boundaries minus one
            int len = right[i] - left[i] - 1;
            // place arr[i] as a candidate for window size len (store at index len-1)
            ans[len - 1] = Math.max(ans[len - 1], arr[i]);
        }

        // propagate best values from larger windows down to smaller windows
        for (int i = n - 2; i >= 0; i--) {
            // ensure smaller window size inherits a valid maximum if missing
            ans[i] = Math.max(ans[i], ans[i + 1]);
        }

        // return the array where ans[k-1] is the maximum of minimums for window size k
        return ans;
    }

    private int[] previousSmallerStrict(int[] arr) {
        throw new UnsupportedOperationException("Assumed implemented: PSEI (previous strictly smaller index).");
    }

    private int[] nextSmallerOrEqual(int[] arr) {
        throw new UnsupportedOperationException("Assumed implemented: NSEI (next smaller-or-equal index).");
    }
}