class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

public class EasyTreeQuestions {

    public int maxDepth(TreeNode root) {
        if(root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public boolean isBalancedBT(TreeNode root) {
        if(root == null)    return true;
        int leftHeight = maxDepth(root.left);
        int rightHeight = maxDepth(root.right);
        if(Math.abs(leftHeight-rightHeight) > 1)    return false;
        return isBalancedBT(root.left) && isBalancedBT(root.right);
    }

    public boolean isBalancedBTOptimal(TreeNode root) {
        return dfsHeight(root) != -1;
    }

    private int dfsHeight(TreeNode node) {
        if (node == null) return 0;
        int left = dfsHeight(node.left);
        if (left == -1) return -1;   // left not balanced
        int right = dfsHeight(node.right);
        if (right == -1) return -1;  // right not balanced
        if (Math.abs(left - right) > 1) return -1;  // this node not balanced
        return 1 + Math.max(left, right);
    }

    /**
     * Do a postorder traversal. For each node, compute: height of its left subtree height of its right subtree 
     * candidate diameter = leftHeight + rightHeight Update a global maximum with this candidate. 
     * Return 1 + max(leftHeight, rightHeight) upward
     */
    public int diameterOfBinaryTree(TreeNode root) {
        int[] diameter = new int[1];
        depth(root, diameter);
        return diameter[0];
    }

    // returns height of subtree rooted at node
    private int depth(TreeNode node, int[] diameter) {
        if (node == null) return 0;
        int left = depth(node.left);
        int right = depth(node.right);
        // update diameter as sum of left and right heights
        diameter[0] = Math.max(diameter[0], left + right);
        // return height for parent computation
        return 1 + Math.max(left, right);
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        // check whether left and right subtrees are mirrors
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode a, TreeNode b) {
        // if both are null, they mirror perfectly here
        if (a == null && b == null) return true;
        // if only one is null, shapes differ → not symmetric
        if (a == null || b == null) return false;
        // values must match and cross-children must mirror
        if (a.val != b.val) return false;
        // left of a mirrors right of b, and right of a mirrors left of b
        return isMirror(a.left, b.right) && isMirror(a.right, b.left);
    }

    public List<Integer> rootToNodePath(TreeNode root, int num) {
        List<Integer> path = new ArrayList<>();
        if(root == null) return path;
        rootToNode(root, path, num);
        return path;
    }

    private boolean rootToNode(TreeNode root, List<Integer> path, int n) {
        if(root == null)    return false;
        path.add(root.val);
        if(root.val == n)   return true;
        if(rootToNode(root.left, path, n) || rootToNode(root.right, path, n))   return true;
        path.remove(path.size()-1);
        return false;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, int a, int b) {
        if(node == null) return null;
        if(node.val == a || node.val == b)   return node;
        TreeNode left = lowestCommonAncestor(node.left,a,b);
        TreeNode right = lowestCommonAncestor(node.right,a,b);
        if(left != null && right != null) return node;
        return left != null ? left : right;
    }

    public TreeNode lowestCommonAncestorBST(TreeNode root, int a, int b) {
        TreeNode cur = root;

        while (cur != null) {
            if (p.val < cur.val && q.val < cur.val) {
                // both in left subtree
                cur = cur.left;
            } else if (p.val > cur.val && q.val > cur.val) {
                // both in right subtree
                cur = cur.right;
            } else {
                // split point found → this is the LCA
                return cur;
            }
        }
        return null;
    }

    /**
     * In a complete BT, if the height of the leftmost path equals the height of the rightmost path → the tree is perfect.
     * A perfect binary tree of height h has (2^h − 1) nodes.
     * Otherwise, recursively count left and right subtrees.
     * This gives O(log² n): at each level we compute left/right heights in O(log n), and recursion depth is O(log n).
     */
    public int countNodes(TreeNode root) {
        if (root == null) return 0;

        int leftHeight = leftDepth(root);
        int rightHeight = rightDepth(root);

        if (leftHeight == rightHeight) {
            // perfect binary tree i.e, no. 
            return (1 << leftHeight) - 1;
        } else {
            // recurse on both sides
            return 1 + countNodes(root.left) + countNodes(root.right);
        }
    }

    private int leftDepth(TreeNode node) {
        int h = 0;
        while (node != null) {
            h++;
            node = node.left;
        }
        return h;
    }

    private int rightDepth(TreeNode node) {
        int h = 0;
        while (node != null) {
            h++;
            node = node.right;
        }
        return h;
    }

}
