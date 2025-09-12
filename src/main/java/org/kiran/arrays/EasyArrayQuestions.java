public class EasyArrayQuestions {
    
    /**
     * Intuition: Our invariant is to have a monotonically decreasing stack, so keep popping until the invairant is true
     * That means until the current element is greater than top of stack we need to pop and once it is less, we can push it
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

    public static void main(String args[]) {
        nextGreaterElement({}, {})
    }

}