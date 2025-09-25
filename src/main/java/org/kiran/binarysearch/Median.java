import java.util.*;

class Solution {
    // I expose the required API returning the median as double
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // I ensure binary search runs on the smaller array
        if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);

        // I store lengths for readability
        int n1 = nums1.length, n2 = nums2.length;

        // I handle the invalid case of both arrays empty
        if (n1 == 0 && n2 == 0) throw new IllegalArgumentException("Both arrays empty");

        // I compute the size of the left half including the middle when odd
        int leftSize = (n1 + n2 + 1) / 2;

        // I binary search the cut position in the first (smaller) array
        int low = 0, high = n1;

        // I iterate until low crosses high to find a valid partition
        while (low <= high) {
            // I choose cut1 mid and deduce cut2 to meet leftSize
            int cut1 = (low + high) / 2;
            int cut2 = leftSize - cut1;

            // I fetch left border from nums1 or -infinity if cut at start
            int l1 = (cut1 == 0) ? Integer.MIN_VALUE : nums1[cut1 - 1];
            // I fetch right border from nums1 or +infinity if cut at end
            int r1 = (cut1 == n1) ? Integer.MAX_VALUE : nums1[cut1];

            // I fetch left border from nums2 or -infinity if cut at start
            int l2 = (cut2 == 0) ? Integer.MIN_VALUE : nums2[cut2 - 1];
            // I fetch right border from nums2 or +infinity if cut at end
            int r2 = (cut2 == n2) ? Integer.MAX_VALUE : nums2[cut2];

            // I check if current partition satisfies order constraints
            if (l1 <= r2 && l2 <= r1) {
                // I compute median depending on total parity
                if (((n1 + n2) % 2) == 1) {
                    // I return the max of left borders for odd total
                    return Math.max(l1, l2);
                } else {
                    // I return average of middle pair for even total
                    int leftMax = Math.max(l1, l2);
                    int rightMin = Math.min(r1, r2);
                    return (leftMax + rightMin) / 2.0;
                }
            }

            // I shrink search space: l1 too big means move cut1 left
            if (l1 > r2) {
                high = cut1 - 1;
            } else {
                // I move cut1 right when l2 too big for r1
                low = cut1 + 1;
            }
        }

        // I never reach here if inputs are valid sorted arrays
        throw new IllegalStateException("No valid partition found");
    }
}
