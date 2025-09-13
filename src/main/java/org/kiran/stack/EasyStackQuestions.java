public class EasyArrayQuestions {
    
    /**
     * Intuition: Our invariant is to have a monotonically decreasing stack, so keep popping until the invairant is true
     * That means until the current element is greater than top of stack we need to pop and once it is less, we can push it
     * 
     * nextGreaterElement: Traverse from right to left, in stack keep popping until current element is greater than top of stack. Maintain decreasing stack from top to bottom.
     * nextSmallerElement: Traverse from right to left, in stack keep popping until current element is smaller than top of stack. Maintain increasing stack from top to bottom.
     * prevGreaterElement: Traverse from left to right, in stack keep popping until current element is greater than top of stack. Maintain decreasing stack from top to bottom.
     * prevSmallerElement: Traverse from left to right, in stack keep popping until current element is smaller than top of stack. Maintain increasing stack from top to bottom.
     * 
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Stack<Integer> stack = new Stack<>();
        Map<Integer, Integer> references = new HashMap<>();
        for(int i=nums2.length-1; i>=0; i--) {
            int x = nums2[i];
            while(!stack.isEmpty() && x >= stack.peek())
                stack.pop();
            references.put(x, stack.isEmpty() ? -1 : stack.peek());
            stack.push(x);
        }
        int[] res = new int[nums1.length];
        for(int i=0; i<nums1.length; i++) {
            res[i] = references.getOrDefault(nums1[i], -1);
        }
        return res;
    }

    public int[] nextGreaterElement2(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int N = nums.length;
        int[] res = new int[N];
        for(int i=2*N-1; i>=0; i--) {
            int x = nums[i%N];
            while(!stack.isEmpty() && x >= stack.peek())
                stack.pop();
            if(i < N)
                res[i] = stack.isEmpty() ? -1 : stack.peek();
                
            stack.push(x);
        }
        return res;
    }

    public nearestSmallerElement(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int N = nums.length;
        int[] res = new int[N];
        for(int i=0; i<N; i++) {
            while(!stack.isEmpty() && nums[i] <= stack.peek()) 
                stack.pop();
            res[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i]);
        }
        return res;
    }

    /**
     * Blindly push into stack if it's a positive element since we are also moving towards right and positive asteroid is also moving towards right.
     * If you encounter negative asteroid, keep popping until you reach a positive asteroid more than this negative one
     * If top of stack and negative asteroid are of same value just pop it a well
     * Once edge case is when all positive asteroids are destroyed, which will happen once negative dominated so far, push the negative ones.
     */
    public int[] asteroidCollision(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        for(int i=0; i<arr.length; i++) {
            if(arr[i] >= 0) {
                stack.push(arr[i]);
            } else {
                while(!stack.isEmpty() && stack.peek() >= 0 && stack.peek() < arr[i]) {
                    stack.pop();
                }
                if(!stack.isEmpty() && stack.peek() == arr[i]) {
                    stack.pop();
                } else if(stack.isEmpty() || stack.peek() < 0) {
                    stack.push(arr[i]);
                }
            }
        }
    }

    public static void main(String args[]) {
        nextGreaterElement({}, {})
    }

}