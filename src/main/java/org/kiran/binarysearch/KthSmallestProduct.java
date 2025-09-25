import java.util.*;

class Solution {
    // I return the k-th smallest product as a long
    public long kthSmallestProduct(int[] nums1, int[] nums2, long k) {
        // I ensure both arrays are non-empty as expected by the problem
        if (nums1.length == 0 || nums2.length == 0) throw new IllegalArgumentException("Empty array");

        // I compute four corner products to bound the search space tightly
        long a1 = 1L * nums1[0] * nums2[0];
        long a2 = 1L * nums1[0] * nums2[nums2.length - 1];
        long a3 = 1L * nums1[nums1.length - 1] * nums2[0];
        long a4 = 1L * nums1[nums1.length - 1] * nums2[nums2.length - 1];

        // I set low to the smallest possible product
        long low = Math.min(Math.min(a1, a2), Math.min(a3, a4));
        // I set high to the largest possible product
        long high = Math.max(Math.max(a1, a2), Math.max(a3, a4));

        // I binary search the smallest x with count(x) >= k
        while (low < high) {
            // I take mid this way to avoid overflow on long
            long mid = low + ((high - low) >>> 1);

            // I count how many pairs have product <= mid
            long cnt = countLE(nums1, nums2, mid);

            // I shrink the search based on monotonicity
            if (cnt >= k) {
                // I keep mid because it might be the answer
                high = mid;
            } else {
                // I need larger products if not enough pairs
                low = mid + 1;
            }
        }

        // I return the found threshold as the k-th product
        return low;
    }

    // I count pairs (i, j) with nums1[i] * nums2[j] <= x
    private long countLE(int[] a, int[] b, long x) {
        // I store count in long because it may exceed int
        long count = 0;

        // I iterate each element of the first array once
        for (int val : a) {
            // I handle zero separately to avoid division hassle
            if (val == 0) {
                // I add all m when x is non-negative
                if (x >= 0) count += b.length;
                // I skip when x < 0 since 0 <= x is false
                continue;
            }

            // I branch on sign to keep inequality direction correct
            if (val > 0) {
                // I compute the largest b allowed: floor(x / val)
                long limit = floorDiv(x, val);
                // I count how many b's are <= limit
                int up = upperBound(b, limit);
                // I add the number of valid partners for this val
                count += up;
            } else { // val < 0
                // I compute the smallest b allowed: ceil(x / val)
                long start = ceilDiv(x, val);
                // I find first index where b >= start
                int lo = lowerBound(b, start);
                // I add all elements from lo to end
                count += (b.length - lo);
            }
        }

        // I return total pairs not exceeding x
        return count;
    }

    // I return index of first element > key (array is sorted ascending)
    private int upperBound(int[] arr, long key) {
        // I use standard binary search template
        int l = 0, r = arr.length;
        // I search the first position with arr[pos] > key
        while (l < r) {
            int m = l + ((r - l) >>> 1);
            // I move right if current value <= key
            if (arr[m] <= key) l = m + 1;
            else r = m;
        }
        // I return number of elements <= key
        return l;
    }

    // I return index of first element >= key (array is sorted ascending)
    private int lowerBound(int[] arr, long key) {
        // I use similar binary search but with >= condition
        int l = 0, r = arr.length;
        // I narrow until l is the first index with arr[l] >= key
        while (l < r) {
            int m = l + ((r - l) >>> 1);
            // I move left boundary when value is smaller than key
            if (arr[m] < key) l = m + 1;
            else r = m;
        }
        // I return insertion point for key
        return l;
    }

    // I compute floor(a / b) on longs with correct negative handling
    private long floorDiv(long a, long b) {
        // I use Java’s built-in floorDiv for correctness
        return Math.floorDiv(a, b);
    }

    // I compute ceil(a / b) on longs with correct negative handling
    private long ceilDiv(long a, long b) {
        // I reuse floorDiv by flipping signs: ceil(a/b) = -floor(-a/b)
        return -Math.floorDiv(-a, b);
    }
}
