package org.kiran.arrays;

import java.util.Arrays;

public class MinimumPlatformsRequired {
    /**
     * Intuition: The order of the events only matters
     * If we sort arrivals and departures, we can find out number of overlaps
     */
    public int findPlatform(int[] arr, int[] dep, int n) {
        Arrays.sort(arr); // sort all arrival times so we process arrivals in order
        Arrays.sort(dep); // sort all departure times so we process departures in order

        int i = 0; // pointer over arrivals starting from the earliest arrival
        int j = 0; // pointer over departures starting from the earliest departure
        int curr = 0; // current number of platforms occupied at this moment
        int ans = 0; // best (maximum) number of platforms needed so far

        while (i < n && j < n) { // keep going while we have unprocessed arrivals and departures
            if (arr[i] <= dep[j]) { // if the next event is an arrival (or exactly equals a departure)
                curr++; // we need one more platform because a train has arrived
                ans = Math.max(ans, curr); // update the maximum platforms seen so far
                i++; // move to the next arrival event
            } else { // otherwise the next event is a departure strictly before the next arrival
                curr--; // one train leaves, so we free one platform
                j++; // move to the next departure event
            }
        }

        // no need to process the rest because once one list is exhausted,
        // remaining departures will just decrease curr and cannot increase ans

        return ans; // return the minimum number of platforms required, which is the maximum overlap
    }
}
