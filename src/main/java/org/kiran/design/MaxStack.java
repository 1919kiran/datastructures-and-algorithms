import java.util.*;

class MaxStack {
    // Node stored in both the DLL and the value buckets
    private static class Node {
        // Stores the integer value for max lookup
        int val;
        // Doubly links enable O(1) removal from the stack
        Node prev, next;
        // Simple constructor capturing the value
        Node(int v) { this.val = v; }
    }

    // Sentinel head and tail make insert/remove logic simpler
    private final Node head = new Node(0), tail = new Node(0);
    // Maps each value to nodes holding that value, newest at the end
    private final TreeMap<Integer, Deque<Node>> buckets = new TreeMap<>();

    // Initializes the empty doubly linked list
    public MaxStack() {
        // Wire head and tail together to represent emptiness
        head.next = tail; tail.prev = head;
    }

    // Pushes x on top of the stack
    public void push(int x) {
        // Create a new node for x that will sit at the top
        Node node = new Node(x);
        // Insert node right after head to become new top
        insertAfter(head, node);
        // Get the deque for value x (create if missing)
        buckets.computeIfAbsent(x, k -> new ArrayDeque<>()).addLast(node);
    }

    // Removes and returns the top element
    public int pop() {
        // Grab the real first element right after head
        Node node = head.next;
        // Unlink it from the doubly linked list in O(1)
        remove(node);
        // Remove this exact node from its value bucket (last occurrence)
        Deque<Node> d = buckets.get(node.val);
        // Remove from the tail since it was the most recent node with this value
        d.removeLast();
        // Drop the bucket if empty to keep max queries correct
        if (d.isEmpty()) buckets.remove(node.val);
        // Return the popped value
        return node.val;
    }

    // Returns the top element without removing it
    public int top() {
        // Look at node right after head which is always current top
        return head.next.val;
    }

    // Returns the current maximum element
    public int peekMax() {
        // lastKey fetches the greatest key present in the map
        return buckets.lastKey();
    }

    // Removes and returns the current maximum element
    public int popMax() {
        // Find the maximum value key among all elements
        int max = buckets.lastKey();
        // Get the deque of nodes holding this value
        Deque<Node> d = buckets.get(max);
        // Take the most recent node with that value
        Node node = d.removeLast();
        // Unlink this node from the linked list in O(1)
        remove(node);
        // Clean up the bucket when no nodes remain
        if (d.isEmpty()) buckets.remove(max);
        // Return the removed maximum value
        return max;
    }

    // Inserts 'node' right after 'prev' in the doubly linked list
    private void insertAfter(Node prev, Node node) {
        // Hook node to the right neighbor first to avoid losing references
        node.next = prev.next;
        // Hook node back to prev to complete forward link
        node.prev = prev;
        // Link the old next back to node to maintain bidirectional links
        prev.next.prev = node;
        // Link prev forward to node to finalize insertion
        prev.next = node;
    }

    // Removes 'node' from the doubly linked list
    private void remove(Node node) {
        // Bridge node's previous directly to node's next
        node.prev.next = node.next;
        // Bridge node's next back to node's previous
        node.next.prev = node.prev;
        // Help GC by clearing pointers (optional but tidy)
        node.prev = node.next = null;
    }
}
