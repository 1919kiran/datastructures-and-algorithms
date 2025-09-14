import java.util.*;

class StockSpan {

    private Stack<int[]> st;
    private int index;

    public StockSpan() {
        this.st = new Stack<>();
        this.index = 0;
    }

    /**
     * Problem: Maximum number of consecutive days for which stock was less than or equal to current day
     * Intuiton: Find the PGEI and the element right of it will be the first smallest element less than or equal to current element
     * Difference b/w current index and PGEI+1 will be the answer
     * Stack: <val,index>
     */
    public int next(int stock) {
        while (!st.isEmpty() && stock >= st.peek()[0]) {
            st.pop();
        }
        int prevGreaterIdx = st.isEmpty() ? -1 : st.peek()[1];
        int span = index - prevGreaterIdx;
        st.push(new int[]{stock, index});
        index++;
        return span;
    }
}
