import java.util.*;

public class EasyArrayQuestions {
    
    /**
     * 
     * Intuition: The invariant is that the stack should remain monotonic in the right direction.
     * While traversing, keep popping until the invariant holds again, then push the current element.
     * 
     * Similarly we can formulate these problems:
     * 
     * nextGreaterElement: Traverse from right to left, pop while stack.peek() <= current. 
     *                     Stack is increasing from top to bottom (smallest on top).
     *
     * nextSmallerElement: Traverse from right to left, pop while stack.peek() >= current. 
     *                     Stack is decreasing from top to bottom (largest on top).
     *
     * prevGreaterElement: Traverse from left to right, pop while stack.peek() <= current. 
     *                     Stack is increasing from top to bottom (smallest on top).
     *
     * prevSmallerElement: Traverse from left to right, pop while stack.peek() >= current. 
     *                     Stack is decreasing from top to bottom (largest on top).
     * 
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Stack<Integer> stack = new Stack<>();
        Map<Integer, Integer> references = new HashMap<>();
        
        for (int i = nums2.length - 1; i >= 0; i--) {
            int x = nums2[i];
            // Why x >= stack.peek() because we want to keep elements that are greater than x and pop the rest
            while (!stack.isEmpty() && x >= stack.peek()) {
                stack.pop();
            }
            references.put(x, stack.isEmpty() ? -1 : stack.peek());
            stack.push(x);
        }
        
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            res[i] = references.getOrDefault(nums1[i], -1);
        }
        return res;
    }

    public int[] nextGreaterIndex(int[] arr) {
        int n = arr.length;
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && arr[st.peek()] <= arr[i]) {
                st.pop();
            }
            ans[i] = st.isEmpty() ? -1 : st.peek();
            st.push(i);
        }

        return ans;
    }


    // Circular version
    public int[] nextGreaterElement2(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int N = nums.length;
        int[] res = new int[N];
        
        for (int i = 2 * N - 1; i >= 0; i--) {
            int x = nums[i % N];
            while (!stack.isEmpty() && x >= stack.peek()) {
                stack.pop();
            }
            if (i < N) {
                res[i] = stack.isEmpty() ? -1 : stack.peek();
            }
            stack.push(x);
        }
        return res;
    }

    /**
     * Blindly push into stack if it's a positive element since we and positive asteroid are moving towards right 
     * If you encounter negative asteroid, keep popping until you reach a positive asteroid more than this negative one.
     * If top of stack and negative asteroid are of same value just pop it as well.
     * One edge case is when all positive asteroids are destroyed, 
     * which will happen once negative dominates so far, then push the negative one.
     */
    public int[] asteroidCollision(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        
        for (int asteroid : arr) {
            boolean alive = true;
            
            // Only collide if asteroid is negative and stack top is positive
            while (alive && asteroid < 0 && !stack.isEmpty() && stack.peek() > 0) {
                int top = stack.peek();
                if (top < -asteroid) {
                    // top explodes, keep checking
                    stack.pop();
                } else if (top == -asteroid) {
                    // both explode
                    stack.pop();
                    alive = false;
                } else {
                    // asteroid explodes
                    alive = false;
                }
            }
            
            if (alive) {
                stack.push(asteroid);
            }
        }
        
        int[] res = new int[stack.size()];
        for (int i = res.length - 1; i >= 0; i--) {
            res[i] = stack.pop();
        }
        return res;
    }

}
