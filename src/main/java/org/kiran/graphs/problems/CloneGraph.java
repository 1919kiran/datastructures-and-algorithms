/**
 * Assumption: Graph is undirected and connected
 * Intuition: when you see a node for the first time, create its copy and put it in the map. then whenever you encounter that original node again, just return the already-created copy from the map. that way, we don’t clone things multiple times
 */
// definition for a graph node (same as leetcode’s)
class Node {
    public int val;                      // value of the node
    public List<Node> neighbors;         // adjacency list of neighbors
    public Node() {
        neighbors = new ArrayList<>();   // initialize neighbors list
    }
    public Node(int val) {
        this.val = val;                  // set the node value
        neighbors = new ArrayList<>();   // initialize neighbors list
    }
    public Node(int val, List<Node> neighbors) {
        this.val = val;                  // set the node value
        this.neighbors = neighbors;      // set neighbors list
    }
}

class Solution {
    // map to remember already cloned nodes
    private Map<Node, Node> map = new HashMap<>();

    // main method to clone the graph
    public Node cloneGraph(Node node) {
        if (node == null) {                       // handle empty graph
            return null;                          // return null if no input
        }
        if (map.containsKey(node)) {              // if this node was already cloned
            return map.get(node);                 // return its clone from map
        }

        // create a new node with same value
        Node copy = new Node(node.val);           // clone current node
        map.put(node, copy);                      // store mapping original -> clone

        // recursively clone all neighbors
        for (Node nei : node.neighbors) {         // loop over each neighbor
            copy.neighbors.add(cloneGraph(nei));  // add cloned neighbor to copy’s list
        }

        // return the cloned node
        return copy;                              // finished cloning this node
    }
}
