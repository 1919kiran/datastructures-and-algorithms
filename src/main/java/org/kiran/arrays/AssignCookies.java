package org.kiran.arrays;

import java.util.*; // for arrays sorting

class Solution {
    /** 
     * Intuition: we should satisfy the least greedy child with the smallest possible cookie that fits
     * 
    */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g); // sort children greed factors
        Arrays.sort(s); // sort cookie sizes
        
        int i = 0; // pointer for children
        int j = 0; // pointer for cookies
        int count = 0; // satisfied children count
        
        // walk through both arrays
        while (i < g.length && j < s.length) {
            if (s[j] >= g[i]) { 
                // cookie can satisfy child
                count++; // child satisfied
                i++; // move to next child
                j++; // move to next cookie
            } else {
                // cookie too small, skip it
                j++;
            }
        }
        
        return count; // return max satisfied children
    }
}
