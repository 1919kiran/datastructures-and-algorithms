/**
 * The key idea is to group keys by their current frequency. Think of a map from frequency to a doubly linked list 
 * that holds the keys with that frequency, ordered by recency within that bucket. We also keep a map from key to its 
 * node so we can reach and move it in O(1). When a key is accessed, we remove it from its current frequency list and 
 * push it to the front of the next frequency’s list, because it just got one more use and is now most recent within 
 * that new bucket. We keep track of the smallest frequency present in the cache with a minFreq variable. 
 * When we need to evict, we look up the list at minFreq and pop from the tail, which is the least recent 
 * among the least frequent, all in O(1).
 */
class LFUCache {

    // Node lives inside a freq-bucket list and carries key, value, and current freq
    private static class Node {
        int key, value, freq;
        Node prev, next;
        Node(int k, int v) { key = k; value = v; freq = 1; }
    }

    // Doubly linked list with sentinels for O(1) add/remove and LRU within a frequency
    private static class DLList {
        Node head = new Node(-1, -1), tail = new Node(-1, -1);
        int size = 0;
        DLList() { head.next = tail; tail.prev = head; }
        void addFirst(Node x) { 
            x.next = head.next; x.prev = head;
            head.next.prev = x; head.next = x; 
            size++; 
        }
        void remove(Node x) { 
            x.prev.next = x.next; x.next.prev = x.prev; 
            x.prev = x.next = null; 
            size--; 
        }
        Node removeLast() { 
            if (size == 0) return null; 
            Node x = tail.prev; 
            remove(x); 
            return x; 
        }
        boolean isEmpty() { return size == 0; }
    }

    // Capacity and the minimum frequency currently in cache
    private final int capacity;
    private int minFreq = 0;

    // Key → Node for O(1) access; Freq → DLList for O(1) bucket operations
    private final Map<Integer, Node> keyToNode = new HashMap<>();
    private final Map<Integer, DLList> freqToList = new HashMap<>();

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (capacity == 0) return -1;
        Node node = keyToNode.get(key);
        if (node == null) return -1;
        touch(node);                              // bump frequency and move across buckets
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;
        Node node = keyToNode.get(key);
        if (node != null) {                       // update existing and bump freq
            node.value = value;
            touch(node);
            return;
        }
        if (keyToNode.size() == capacity) {       // evict LFU, LRU within that freq
            DLList list = freqToList.get(minFreq);
            Node evict = list.removeLast();
            keyToNode.remove(evict.key);
        }
        Node fresh = new Node(key, value);        // insert new at freq = 1
        keyToNode.put(key, fresh);
        freqToList.computeIfAbsent(1, f -> new DLList()).addFirst(fresh);
        minFreq = 1;                              // new minimum frequency present
    }

    // Move node from freq f to f+1 and update minFreq if needed
    private void touch(Node node) {
        int f = node.freq;
        DLList from = freqToList.get(f);
        from.remove(node);
        if (f == minFreq && from.isEmpty()) minFreq = f + 1;
        node.freq = f + 1;
        freqToList.computeIfAbsent(node.freq, x -> new DLList()).addFirst(node);
    }
}
