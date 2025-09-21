public class TargetSum {

    /**
     * Problem:
     * You can assign any sign to the nums in array. In how many ways you can assign the signs to each num
     * such that the sum of array is equal to target
     * 
     * This question is exactly the same as partitionIntoSubsetsWithMinSumDiff
     * If we assign x numbers with + and y numbers with -
     * then the sum of array = sum(x) + sum(y) = s1 + -(s2) = s1 - s2
     */
    public int targetSum(int[] nums, int target) {
        return partitionIntoSubsetsWithMinSumDiff(nums, target);
    }
    
}
