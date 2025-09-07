package org.kiran.arrays;

import java.util.Arrays;

public class JobSequencing {

    /**
     * Intuition: Schedule the most profitable jobs first but shcdule them at the end so they are guaranteed to be scheduled
     * Why at the end? - it preserves room for others
     */
    public int[] JobScheduling(Job[] jobs, int n) {
        int maxDeadline = 0; // i will track the largest deadline to know how many slots we need
        for (int i = 0; i < n; i++) { // loop over all jobs once
            maxDeadline = Math.max(maxDeadline, jobs[i].deadline); // update the maximum deadline seen
        }

        Arrays.sort(jobs, (a, b) -> b.profit - a.profit); // sort jobs by profit descending so high profit comes first

        int[] slot = new int[maxDeadline + 1]; // array to mark occupied time slots (1-based indexing for convenience)
        Arrays.fill(slot, -1); // initialize all slots as free using -1

        int jobsDone = 0;  // count how many jobs we successfully schedule
        int totalProfit = 0; // sum up the profit we earn

        for (int i = 0; i < n; i++) { // go through each job in decreasing profit order
            int d = Math.min(maxDeadline, jobs[i].deadline); // the latest slot we’re allowed to use for this job
            for (int t = d; t >= 1; t--) { // try to place this job as late as possible
                if (slot[t] == -1) { // if this slot is free
                    slot[t] = jobs[i].id; // place the job here (store id just for traceability)
                    jobsDone++; // we’ve scheduled one more job
                    totalProfit += jobs[i].profit; // add its profit to the total
                    break; // stop searching for a slot for this job; move to the next job
                }
            }
            // if we didn’t find a free slot, this job is skipped automatically and we continue
        }

        return new int[]{jobsDone, totalProfit}; // return the number of jobs done and the total profit we got
    }
}

class Job {
    int id;        // unique identifier for the job (not strictly needed for profit but common in problems)
    int deadline;  // the last time slot when this job can be scheduled
    int profit;    // profit earned if we complete this job

    Job(int id, int deadline, int profit) { // constructor to set fields
        this.id = id;           // set the job id
        this.deadline = deadline; // set the deadline
        this.profit = profit;     // set the profit
    }
}