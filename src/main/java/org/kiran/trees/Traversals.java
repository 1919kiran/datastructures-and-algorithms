import java.util.*;

// TreeNode definition
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

public class Traversals {

    public List<List<Integer>> allTraversals(TreeNode root) {
        List<Integer> preorder = new ArrayList<>();
        List<Integer> inorder = new ArrayList<>();
        List<Integer> postorder = new ArrayList<>();
        
        if (root == null) return Arrays.asList(preorder, inorder, postorder);

        // stack holds pair of (node, state)
        class Pair {
            TreeNode node; int state;
            Pair(TreeNode n, int s) { node = n; state = s; }
        }
        Deque<Pair> st = new ArrayDeque<>();
        st.push(new Pair(root, 1));

        while (!st.isEmpty()) {
            Pair top = st.pop();
            if (top.state == 1) {
                // preorder step
                preorder.add(top.node.val);
                top.state++;
                st.push(top);                  // push back with state=2
                if (top.node.left != null) {
                    st.push(new Pair(top.node.left, 1));
                }
            } else if (top.state == 2) {
                // inorder step
                inorder.add(top.node.val);
                top.state++;
                st.push(top);                  // push back with state=3
                if (top.node.right != null) {
                    st.push(new Pair(top.node.right, 1));
                }
            } else {
                // postorder step
                postorder.add(top.node.val);
            }
        }

        return Arrays.asList(preorder, inorder, postorder);
    }
    
}
