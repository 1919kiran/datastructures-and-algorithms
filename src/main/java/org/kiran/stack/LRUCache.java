import java.util.*;

/**
 * 
 * Intuition: If we try “ordering with an ArrayList + HashMap of key→index,” get can find a value in O(1) from the map, 
 * but promoting that key to “most recent” means removing it from the middle of the ArrayList and inserting at the end, 
 * which is O(n) due to shifts. Eviction at the front is O(n) for the same reason. 
 * You could try updating many stored indices in the map after every move, but that’s also O(n). 
 * So ArrayList breaks the O(1) requirement.
 * 
 * Next we think “a queue with a singly linked list.” Appending at tail and popping from head are O(1), 
 * which fixes eviction and insertion. But we still need to promote an arbitrary node to the front on get. 
 * With a singly list, removing a middle node needs its predecessor. If the map stores only key→node, 
 * you don’t have the predecessor, so you’d traverse O(n).
 * 
 * This naturally leads to a doubly linked list. Now each node knows both prev and next, 
 * so you can unlink or move a node in pure O(1) given the node pointer.
 * 
 */
class LRUCache {

    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;
        Node(int k, int v) { key = k; value = v; }
    }

    private final int capacity;
    // map from key to its node in the doubly linked list
    private final Map<Integer, Node> map;
    // dummy head sentinel to mark the most-recent side
    private final Node head;
    // dummy tail sentinel to mark the least-recent side
    private final Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node(-1, -1);
        this.tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    // get operation returns value if present; moves node to most-recent position
    public int get(int key) {
        Node node = map.get(key);
        if (node == null) return -1;
        moveToFront(node);
        return node.value;
    }

    // put operation inserts or updates and handles eviction if necessary
    public void put(int key, int value) {
        Node node = map.get(key);
        if (node != null) {
            node.value = value;
            moveToFront(node);
            return;
        }
        // if key does not exist, we may need to evict if at capacity
        if (map.size() == capacity) {
            // pick the least recently used node which is just before tail
            Node lru = tail.prev;
            // unlink the lru node from the list
            removeNode(lru);
            // remove the lru key from the map
            map.remove(lru.key);
        }
        // create a new node for this (key, value)
        Node fresh = new Node(key, value);
        // insert the new node right after head to mark it most-recent
        addAfterHead(fresh);
        // record it in the map
        map.put(key, fresh);
    }

    // helper to move an existing node to the front (right after head)
    private void moveToFront(Node node) {
        removeNode(node);
        addAfterHead(node);
    }

    // helper to remove a node from the doubly linked list
    private void removeNode(Node node) {
        Node p = node.prev;
        Node n = node.next;
        p.next = n;
        n.prev = p;
        node.prev = null;
        node.next = null;
    }

    // helper to add a node right after the head
    private void addAfterHead(Node node) {
        Node first = head.next;
        node.next = first;
        node.prev = head;
        head.next = node;
        first.prev = node;
    }
}
