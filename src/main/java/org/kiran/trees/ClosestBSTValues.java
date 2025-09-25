import java.util.*;

// I use the standard TreeNode definition used in the platform
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode() {}
    TreeNode(int v) { val = v; }
    TreeNode(int v, TreeNode l, TreeNode r) { val = v; left = l; right = r; }
}

class ClosestBSTValues {
    // I return k values from the BST that are closest to target
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        // I guard trivial cases where no work is needed
        List<Integer> ans = new ArrayList<>();
        if (root == null || k == 0) return ans;

        // I keep predecessors (<= target) and successors (> target) separately
        Deque<TreeNode> pred = new ArrayDeque<>();
        Deque<TreeNode> succ = new ArrayDeque<>();

        // I initialize both stacks by walking down from root once
        //   nodes <= target go to pred while moving right,
        //   nodes  > target go to succ while moving left
        TreeNode cur = root;
        while (cur != null) {
            // I push to pred and go right when current value is ≤ target
            if (cur.val <= target) {
                pred.push(cur);
                cur = cur.right;
            } else {
                // I push to succ and go left when current value is > target
                succ.push(cur);
                cur = cur.left;
            }
        }

        // I pick k closest values by comparing current pred and succ
        while (k-- > 0) {
            // I choose predecessor if successor stack is empty
            if (succ.isEmpty()) {
                ans.add(nextPred(pred));
                continue;
            }
            // I choose successor if predecessor stack is empty
            if (pred.isEmpty()) {
                ans.add(nextSucc(succ));
                continue;
            }
            // I compare distances to decide which side is closer
            double dPred = Math.abs(target - pred.peek().val);
            double dSucc = Math.abs(succ.peek().val - target);
            // I take the closer of the two and advance that iterator
            if (dPred <= dSucc) {
                ans.add(nextPred(pred));
            } else {
                ans.add(nextSucc(succ));
            }
        }

        // I return the collected k closest values
        return ans;
    }

    // I pop one predecessor and advance to the next smaller value
    private int nextPred(Deque<TreeNode> pred) {
        // I pop the current predecessor node
        TreeNode node = pred.pop();
        // I remember its value to return
        int val = node.val;
        // I move into its left subtree to find the next smaller candidate
        node = node.left;
        // I push the rightmost path to reach the in-order predecessor
        while (node != null) {
            pred.push(node);
            node = node.right;
        }
        // I return the predecessor value extracted
        return val;
    }

    // I pop one successor and advance to the next larger value
    private int nextSucc(Deque<TreeNode> succ) {
        // I pop the current successor node
        TreeNode node = succ.pop();
        // I remember its value to return
        int val = node.val;
        // I move into its right subtree to find the next larger candidate
        node = node.right;
        // I push the leftmost path to reach the in-order successor
        while (node != null) {
            succ.push(node);
            node = node.left;
        }
        // I return the successor value extracted
        return val;
    }
}
