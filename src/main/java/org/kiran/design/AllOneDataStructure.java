import java.util.*;

class AllOneDataStructure {

    // I define a bucket node storing one count and its keys
    private static class Node {
        int cnt;
        Set<String> keys;
        Node prev, next;
        Node(int c) { cnt = c; keys = new HashSet<>(); }
    }

    // I use sentinels to simplify head/tail edge handling
    private final Node head = new Node(Integer.MIN_VALUE);
    private final Node tail = new Node(Integer.MAX_VALUE);

    // I map each key to the bucket node where it currently lives
    private final Map<String, Node> where = new HashMap<>();

    // I link sentinels once to start with an empty list
    public AllOne() {
        // I connect head to tail and tail back to head
        head.next = tail; tail.prev = head;
    }

    // I increase the count of key by 1, inserting if missing
    public void inc(String key) {
        // I handle new key by placing it into count 1 bucket
        if (!where.containsKey(key)) {
            // I ensure a bucket with count 1 exists after head
            Node n1 = (head.next != tail && head.next.cnt == 1) ? head.next : insertAfter(head, new Node(1));
            // I add key to the count 1 bucket
            n1.keys.add(key);
            // I remember this location for key
            where.put(key, n1);
            // I finish since new key needs no further movement
            return;
        }

        // I fetch current bucket of key
        Node cur = where.get(key);
        // I compute desired next count
        int need = cur.cnt + 1;

        // I ensure next bucket has the desired count
        Node nxt = cur.next;
        // I create next bucket if absent or different count
        if (nxt == tail || nxt.cnt != need) nxt = insertAfter(cur, new Node(need));

        // I move key into the next bucket’s set
        nxt.keys.add(key);
        // I update key location to the new bucket
        where.put(key, nxt);

        // I remove key from its old bucket’s set
        cur.keys.remove(key);
        // I delete the old bucket when it becomes empty
        if (cur.keys.isEmpty()) remove(cur);
    }

    // I decrease the count of key by 1, removing key if it hits zero
    public void dec(String key) {
        // I ignore if key not present
        Node cur = where.get(key);
        if (cur == null) return;

        // I handle the special case when new count becomes zero
        if (cur.cnt == 1) {
            // I erase key from map first
            where.remove(key);
            // I erase key from its bucket’s set
            cur.keys.remove(key);
            // I delete bucket if empty after removal
            if (cur.keys.isEmpty()) remove(cur);
            // I finish since key is completely removed
            return;
        }

        // I compute desired previous count
        int need = cur.cnt - 1;

        // I ensure previous bucket has the desired count
        Node prv = cur.prev;
        // I create previous bucket if absent or different count
        if (prv == head || prv.cnt != need) prv = insertAfter(cur.prev, new Node(need));

        // I move key into the previous bucket’s set
        prv.keys.add(key);
        // I update location in the map
        where.put(key, prv);

        // I remove key from current bucket’s set
        cur.keys.remove(key);
        // I delete the current bucket if it becomes empty
        if (cur.keys.isEmpty()) remove(cur);
    }

    // I return any key with maximum count, or empty when none
    public String getMaxKey() {
        // I check empty structure by looking at tail’s previous
        if (tail.prev == head) return "";
        // I return any key from the max bucket
        return tail.prev.keys.iterator().next();
    }

    // I return any key with minimum count, or empty when none
    public String getMinKey() {
        // I check empty structure by looking at head’s next
        if (head.next == tail) return "";
        // I return any key from the min bucket
        return head.next.keys.iterator().next();
    }

    // I insert node 'x' right after node 'p' in O(1)
    private Node insertAfter(Node p, Node x) {
        // I wire x between p and p.next
        x.next = p.next; x.prev = p;
        // I link neighbors to include x
        p.next.prev = x; p.next = x;
        // I return the inserted node for chaining
        return x;
    }

    // I remove node 'x' from the list in O(1)
    private void remove(Node x) {
        // I relink neighbors to bypass x
        x.prev.next = x.next; x.next.prev = x.prev;
        // I clear links to help GC (optional)
        x.prev = x.next = null;
    }
}
