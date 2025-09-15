import java.util.*;

// TreeNode definition
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

class NodePos {
    TreeNode node;
    int row;
    int col;
    NodePos(TreeNode n, int r, int c) { node = n; row = r; col = c; }
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

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        boolean leftToRight = true;   // flag to control direction

        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                level.add(node.val);

                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }

            // reverse if current level is right-to-left
            if (!leftToRight) {
                Collections.reverse(level);
            }

            ans.add(level);
            // flip direction for next level
            leftToRight = !leftToRight;
        }

        return ans;
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        if (root == null) return new ArrayList<>();

        // map: col -> (row -> min-heap of values)
        TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> cols = new TreeMap<>();

        // queue for BFS carrying node with its row and column
        Deque<NodePos> q = new ArrayDeque<>();

        q.offer(new NodePos(root, 0, 0));

        while (!q.isEmpty()) {
            NodePos cur = q.poll();
            TreeNode node = cur.node;
            int row = cur.row, col = cur.col;

            // ensure column entry exists
            cols.computeIfAbsent(col, c -> new TreeMap<>());
            // ensure row entry exists for this column
            cols.get(col).computeIfAbsent(row, r -> new PriorityQueue<>());
            // add current node value into its cell's min-heap
            cols.get(col).get(row).offer(node.val);

            // push left child with row+1 and col-1 and right child with row+1 and col+1
            if (node.left != null) q.offer(new NodePos(node.left, row + 1, col - 1));
            if (node.right != null) q.offer(new NodePos(node.right, row + 1, col + 1));
        }

        List<List<Integer>> ans = new ArrayList<>();

        // iterate columns from leftmost to rightmost
        for (Map.Entry<Integer, TreeMap<Integer, PriorityQueue<Integer>>> colEntry : cols.entrySet()) {
            List<Integer> colVals = new ArrayList<>();
            // iterate rows from top to bottom within this column
            for (Map.Entry<Integer, PriorityQueue<Integer>> rowEntry : colEntry.getValue().entrySet()) {
                // drain the heap so values at the same cell are sorted
                PriorityQueue<Integer> heap = rowEntry.getValue();
                while (!heap.isEmpty()) {
                    colVals.add(heap.poll());
                }
            }
            ans.add(colVals);
        }
        return ans;
    }

}

class TreeViews {

    /**
     * Intuition: 
     * 1. Get the left boundary
     * 2. Get the leaf nodes
     * 3. Get the right boundary nodes in reverse
     */
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        if (!isLeaf(root)) res.add(root.val); // add root if it's not a leaf

        addLeftBoundary(root.left, res);
        addLeaves(root, res);
        addRightBoundary(root.right, res);

        return res;
    }

    private boolean isLeaf(TreeNode node) {
        return node != null && node.left == null && node.right == null;
    }

    private void addLeftBoundary(TreeNode node, List<Integer> res) {
        while (node != null) {
            if (!isLeaf(node)) res.add(node.val);
            if (node.left != null) node = node.left;
            else node = node.right;
        }
    }

    private void addLeaves(TreeNode node, List<Integer> res) {
        if (node == null) return;
        if (isLeaf(node)) {
            res.add(node.val);
            return;
        }
        addLeaves(node.left, res);
        addLeaves(node.right, res);
    }

    private void addRightBoundary(TreeNode node, List<Integer> res) {
        List<Integer> temp = new ArrayList<>();
        while (node != null) {
            if (!isLeaf(node)) temp.add(node.val);
            if (node.right != null) node = node.right;
            else node = node.left;
        }
        // reverse before adding
        for (int i = temp.size() - 1; i >= 0; i--) {
            res.add(temp.get(i));
        }
    }

    /**
     * Intuition: Use BFS so we naturally go level by level (top to bottom). Track each node’s horizontal distance (hd),
     * where root is 0, left is hd−1, right is hd+1. The first time we see an hd in BFS, 
     * that node is the top view for that column. Store it and never overwrite.
     * Use a TreeMap to keep columns ordered from left to right. This is O(n) time and O(n) space.
     */
    public List<Integer> topView(TreeNode root) {
        if (root == null) return new ArrayList<>();
        
        Deque<Pair> q = new ArrayDeque<>();
        q.offerLast(new Pair(root, 0));
        Map<Integer, Integer> firstAtHd = new TreeMap<>();

        while (!q.isEmpty()) {
            Pair cur = q.pollFirst();
            // if this horizontal distance not seen before, record it
            firstAtHd.putIfAbsent(cur.hd, cur.node.val);
            // push left child with hd - 1
            if (cur.node.left != null) q.offerLast(new Pair(cur.node.left, cur.hd - 1));
            // push right child with hd + 1
            if (cur.node.right != null) q.offerLast(new Pair(cur.node.right, cur.hd + 1));
        }

        List<Integer> ans = new ArrayList<>(firstAtHd.size());
        // iterate in key order (TreeMap guarantees ascending hd)
        for (int hd : firstAtHd.keySet()) {
            // append the node value for this column
            ans.add(firstAtHd.get(hd));
        }
        return ans;
    }

    /** 
     * Intuition: For the bottom view, whenever we see a node at a given hd, we overwrite the entry so the last node 
     * we visit at that column (i.e., the lowest one encountered as BFS progresses level by level) stays in the map.
     * 
     */
    public List<Integer> bottomView(TreeNode root) {
        // same code as topView but instead of firstAtHd.putIfAbsent just do firstAtHd.put
    }

    public List<Integer> leftView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (i == 0) res.add(node.val);  // first node in this level → left view
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
        }
        return res;
    }

    public List<Integer> rightView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (i == size - 1) res.add(node.val);  // last node in this level → right view
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
        }
        return res;
    }

}
