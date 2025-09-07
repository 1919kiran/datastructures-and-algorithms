package org.kiran.arrays;

import org.kiran.concepts.Arrays;
import org.kiran.concepts.SlidingWindow;

import java.util.*;


public class LongestSubstringWithoutRepeatingChars {
    public int lengthOfLongestSubstring(String s) {
        // handle null quickly; leetcode usually gives non-null, but let's be safe
        if (s == null) return 0; // null string treated as length zero

        // map from character to the last index where it appeared
        Map<Character, Integer> last = new HashMap<>(); // remembers most recent position

        // left pointer for the current window start
        int left = 0; // window is [left, i]

        // best (maximum) length found so far
        int best = 0; // start at zero

        // walk the right pointer i over the string
        for (int i = 0; i < s.length(); i++) { // iterate each character by index
            char c = s.charAt(i); // current character

            // if we've seen c before and it lies inside the current window, move left
            if (last.containsKey(c) && last.get(c) >= left) { // duplicate inside window
                left = last.get(c) + 1; // jump left just past the previous occurrence
            }

            // record the current index as the last seen position of c
            last.put(c, i); // update map with where we just saw c

            // update best with the size of the current window
            int len = i - left + 1; // current window length
            if (len > best) best = len; // keep the maximum
        }

        // return the maximum length we found
        return best; // that’s the answer
    }
}
