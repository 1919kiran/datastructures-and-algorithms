// Standard binary tree node
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int v) { val = v; }
}

class Solution {
    // Convert the tree so that every node's value equals the sum of its children's values
    public void convertToChildrenSum(TreeNode root) {
        if (root == null) return;

        // compute current children's sum
        int child = 0;
        if (root.left  != null) child += root.left.val;
        if (root.right != null) child += root.right.val;

        // push down or pull up as per Striver's approach
        if (child >= root.val) {
            root.val = child;
        } else {
            if (root.left != null)      root.left.val = root.val;
            else if (root.right != null) root.right.val = root.val;
        }

        // recurse
        convertToChildrenSum(root.left);
        convertToChildrenSum(root.right);

        // post-order fix: set parent to sum of (possibly updated) children
        int tot = 0;
        if (root.left  != null) tot += root.left.val;
        if (root.right != null) tot += root.right.val;
        if (root.left != null || root.right != null) root.val = tot;
    }
}
