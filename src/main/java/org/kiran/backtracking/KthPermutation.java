import java.util.ArrayList; // import arraylist to hold remaining numbers
import java.util.List;      // import list interface for clarity

class Solution {
    /**
     * Intuition: There will be (n-1)! permutations with first char is common and there will be n such permutations resulting in n*(n-1)! = n! permutations
     * and there will be (n-2)! permutations where first 2 chars are fixed and so on
     * So the first char of kth permutation is determined by out of which n blocks each of size (n-1)! k falls into = k / blocksize
     * Within the block, the position of k can be determined by k % blocksize
     * 
     */
    public String getPermutation(int n, int k) {
        int fact = 1;
        List<Integer> nums = new ArrayList<>(); // list of remaining digits
        for (int i = 1; i <= n; i++) {          // loop from 1 to n
            fact *= i;
            nums.add(i);                         // add i into the list
        }
        int blockSize = fact / n;

        // convert k to zero-based so that division gives correct index
        k--;

        // build the answer using a StringBuilder for efficiency
        StringBuilder sb = new StringBuilder(); // will hold the final permutation

        while(true) {
            int block = k/blockSize;
            sb.append(nums.get(block));
            nums.remove(block); // remove it so it’s not reused
            if(nums.size() == 0) {
                break;
            }
            k = k % blockSize;
            blockSize = blockSize / nums.size();
        }

        // convert builder to string and return
        return sb.toString();                    // final k-th permutation
    }
}
