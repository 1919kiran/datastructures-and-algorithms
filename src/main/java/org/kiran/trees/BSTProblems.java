public class BSTProblems {

    /**
     * Traverse only as much as needed. For k-th smallest, do iterative inorder with a stack: keep going left, 
     * pop one by one, decrement k when you visit a node, and stop when k hits zero. For k-th largest, 
     * do the same but in reverse: keep going right first. This touches at most k nodes plus the path 
     * from root to the k-th node, giving O(h + k) time and O(h) space.
     */
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> st = new ArrayDeque<>();
        TreeNode cur = root;

        while (cur != null || !st.isEmpty()) {
            while (cur != null) {
                st.push(cur);
                cur = cur.left;
            }
            cur = st.pop();
            if (--k == 0) return cur.val;
            cur = cur.right;
        }

        throw new IllegalArgumentException("k out of range");
    }

    public boolean findTarget(TreeNode root, int k) {
        if (root == null) return false;

        BSTIterator leftIt = new BSTIterator(root, false);
        BSTIterator rightIt = new BSTIterator(root, true);

        if (!leftIt.hasNext() || !rightIt.hasNext()) return false;
        int i = leftIt.next();
        int j = rightIt.next();

        while (i < j) {
            int sum = i + j;
            // if match, we found a valid pair
            if (sum == k) return true;
            // if sum too small, advance the left iterator
            if (sum < k) {
                // ensure we can advance; if not, stop
                if (!leftIt.hasNext()) break;
                i = leftIt.next();
            } else {
                // sum too large, advance the right iterator
                if (!rightIt.hasNext()) break;
                j = rightIt.next();
            }
        }
        // no pair found
        return false;
    }

}

/**
 * Use a controlled inorder traversal with a stack. The iterator keeps a stack of the path to the current node. 
 * For forward iteration we push left spines; for reverse we push right spines. Each next()/prev() pops one, 
 * then pushes the opposite-side spine, so each node is pushed/popped once overall, giving amortized O(1). 
 */
class BSTIterator {
    private Deque<TreeNode> st = new ArrayDeque<>();
    private final boolean reverse;

    public BSTIterator(TreeNode root, boolean reverse) {
        this.reverse = reverse;
        pushSpine(root);
    }

    // check if there is a next value in this iteration direction
    public boolean hasNext() {
        return !st.isEmpty();
    }

    // get the next value in sorted order for this direction
    public int next() {
        TreeNode node = st.pop();
        if (!reverse) {
            pushSpine(node.right);
        } else {
            pushSpine(node.left);
        }
        return node.val;
    }

    // helper to push a left or right spine according to direction
    private void pushSpine(TreeNode cur) {
        while (cur != null) {
            st.push(cur);
            cur = (!reverse) ? cur.left : cur.right;
        }
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int v) { val = v; }
}
