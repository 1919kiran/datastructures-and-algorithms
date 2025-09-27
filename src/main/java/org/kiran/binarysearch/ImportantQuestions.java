public class ImportantQuestions {

    /**
     * Intuition: Check which half of the array is sorted and we only check within the sorted half
     * if it is not found in the sorted 
     */
    public int searchRotatedSortedArray(int[] arr, int target) {
        int n = arr.length;
        int low = 0, high = n-1;
        while(low <= high) {
            int mid = (low+high)/2;
            if(arr[mid] == target) {
                return mid;
            }
            // left half sorted?
            if(arr[low] <= arr[mid]) {
                // if sorted elimiate left or right half based on where target lies
                if(arr[low] <= target && target <= arr[mid]) {
                    high = mid-1;
                } else {
                    low = mid+1;
                }
            } 
            // right half sorted?
            else {
                if(arr[mid] <= target && target <= arr[high]) {
                    low = mid+1;
                } else {
                    high = mid-1;
                }
            }
        }
        return -1;
    }

    /**
     * Intuition: Shrink the search space when you encounter the case arr[low] == arr[mid] == arr[high]
     * Ex: arr = [3 1 2 3 3 3 3 3 3]
     * Then we are sure that arr[low] or arr[high] boundaries can be shrinked by 1
     */
    public int searchRotatedSortedArray2(int[] arr, int target) {
        // same exact code as above by add boundary checks
        int n = arr.length;
        int low = 0, high = n-1;
        while(low <= high) {
            int mid = (low+high)/2;
            if(arr[mid] == target) {
                return mid;
            }
            if(arr[low] == arr[mid] && arr[mid] == arr[high]) {
                low++; high--;
                continue;
            }
            // left half sorted?
            if(arr[low] <= arr[mid]) {
                // if sorted elimiate left or right half based on where target lies
                if(arr[low] <= target && target <= arr[mid]) {
                    high = mid-1;
                } else {
                    low = mid+1;
                }
            } 
            // right half sorted?
            else {
                if(arr[mid] <= target && target <= arr[high]) {
                    low = mid+1;
                } else {
                    high = mid-1;
                }
            }
        }
        return -1;
    }
    
}
