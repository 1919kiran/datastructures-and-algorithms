class MinStack {

    private Stack<Integer> stack;
    private int minValue;

    public MinStack() {
        stack = new Stack<>();
        minValue = Integer.MAX_VALUE;
    }
    
    /**
     * The idea is that instead of keeping an extra stack for minimums, we can store encoded values directly in the stack to remember previous minimums.
     * What will we encode??
     * We encode the pushin element with 2*val-min.
     * When we are about to insert into the stack, this condition is true:
     * val < minValue
     * val - minValue < 0
     * 2*val - minValue < val
     * 
     * So we will always endup inserting something less than val with this formula
     *  
     */
    public void push(int val) {
        if(stack.isEmpty()) {
            // if stack is empty, push val and update min
            stack.push(val);
            minValue = val;
        } else if(val < minValue) {
            // push modified value and update min
            stack.push(2 * val - minValue);
            minValue = val;
        } else {
            // push normally
            stack.push(val);
        }
    }

    public void pop() {
        if(stack.isEmpty()) return;
        int top = stack.pop();
        if(top < minValue) {
            // this was a modified value, recover previous min
            minValue = 2 * minValue - top;
        }
    }

    public int top() {
        if(stack.isEmpty()) return -1;
        int top = stack.peek();
        if(top < minValue) {
            // means actual top is current min
            return minValue;
        } else {
            return top;
        }
    }

    public int getMin() {
        return minValue;
    }
}
