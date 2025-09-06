package org.kiran.arrays;

import org.kiran.concepts.Arrays;
import org.kiran.concepts.Math;

import java.util.*;

@Arrays @Math
public class CountSubArraysWithXorK {
    /*
    *  Maintain XOR for a prefix
    *
    * XOR property: XOR(L..R) = prefixXor(R) ^ prefixXor(L-1)
    * Is there a subarray ending at i and having xor k means
    * for subarray ending at i to have xor K, we need previous prefix value = prefixXor(i) ^ K
    *
    * */
    public long countSubarraysWithXorK(int[] nums, int k) {
        // if array is null or empty, there are no subarrays to count
        if (nums == null || nums.length == 0) return 0L; // quick exit

        // map to store how many times a given prefix xor has occurred so far
        Map<Integer, Long> freq = new HashMap<>(); // prefixXor -> count of occurrences

        // seed with prefix xor 0 seen once, meaning before we start, xor is zero
        freq.put(0, 1L); // this lets subarrays starting at index 0 be counted

        // running xor as we sweep the array
        int cur = 0; // current prefix xor

        // running answer; use long to be safe for large counts
        long ans = 0L; // total number of qualifying subarrays

        // walk through the array once
        for (int x : nums) { // for each value in the array
            cur ^= x; // update current prefix xor by xoring in this element

            // we want earlier prefixes equal to (cur ^ k), each one gives a valid subarray ending here
            int want = cur ^ k; // this is the prefix xor we’re looking for

            // if we’ve seen that prefix before, add how many times to the answer
            ans += freq.getOrDefault(want, 0L); // add all matches in one go

            // now record that we’ve seen this current prefix xor one more time
            freq.put(cur, freq.getOrDefault(cur, 0L) + 1L); // bump its frequency
        }

        // after scanning all elements, ans holds the total count
        return ans; // return the final number of subarrays with xor equal to k
    }
}
