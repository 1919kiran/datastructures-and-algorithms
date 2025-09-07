package org.kiran.arrays;

import org.kiran.concepts.Arrays;
import org.kiran.concepts.Greedy;

import java.util.*;

@Arrays @Greedy
public class NMeetingsInARoom {
    /**
     * Intuition: Sort the meetings by end time so that we will know which meetings finish first
     * This will maximize the rooms left for the rest
     *
     */
    public int maxMeetings(int[] start, int[] end, int n) {
        // create a list of meeting objects
        List<Meeting> meetings = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            meetings.add(new Meeting(start[i], end[i], i + 1));
        }

        // sort meetings by end time
        meetings.sort(Comparator.comparingInt(a -> a.end));

        // count meetings
        int count = 1;  // pick the first meeting
        int lastEnd = meetings.getFirst().end;  // store end time of first meeting

        // loop through rest of the meetings
        for (int i = 1; i < n; i++) {
            if (meetings.get(i).start > lastEnd) {
                // if current meeting starts after last meeting ends, select it
                count++;
                lastEnd = meetings.get(i).end;  // update lastEnd
            }
        }

        return count;  // return maximum meetings
    }
}

class Meeting {
    int start;  // start time of meeting
    int end;    // end time of meeting
    int pos;    // original index of meeting

    Meeting(int start, int end, int pos) {
        this.start = start;  // set start
        this.end = end;      // set end
        this.pos = pos;      // set index
    }
}