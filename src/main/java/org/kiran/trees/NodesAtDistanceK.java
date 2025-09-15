import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int v) { val = v; }
}

class Solution {
    /**
     * First, create a parent map using BFS/DFS so we can move upward from any node.
     * Then run a BFS starting at the target node, expanding outwards in all 3 directions: left, right, and parent.
     * Stop BFS after K steps; the queue contains all nodes at distance K.
     * This problem is exactly same as minimum time taken to burn a BT from a a node.
     */
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;

        // Step 1: build parent map
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        buildParent(root, null, parent);

        // Step 2: BFS from target
        Queue<TreeNode> q = new LinkedList<>();
        Set<TreeNode> visited = new HashSet<>();

        q.offer(target);
        visited.add(target);
        int dist = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            if (dist == k) {
                // all nodes currently in queue are distance k away
                for (TreeNode node : q) res.add(node.val);
                return res;
            }
            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                // explore neighbors: left, right, parent
                if (cur.left != null && visited.add(cur.left)) q.offer(cur.left);
                if (cur.right != null && visited.add(cur.right)) q.offer(cur.right);
                if (parent.get(cur) != null && visited.add(parent.get(cur))) q.offer(parent.get(cur));
            }
            dist++;
        }
        return res;
    }

    private void buildParent(TreeNode node, TreeNode par, Map<TreeNode, TreeNode> parent) {
        if (node == null) return;
        parent.put(node, par);
        buildParent(node.left, node, parent);
        buildParent(node.right, node, parent);
    }
}
