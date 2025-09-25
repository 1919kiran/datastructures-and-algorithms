import java.util.HashMap;
import java.util.Map;

public class TrieProblems {

    private static class Node {
        // Fixed fanout for lowercase 'a'..'z'
        Node[] next = new Node[26];
    }

    // Counts distinct non-empty substrings, reusing the 26-way trie idea
    public long countDistinctSubstrings(String s) {
        // Early return keeps behavior clear for empty input
        if (s == null || s.isEmpty()) return 0L;

        // Fresh root holds the empty path
        Node root = new Node();
        // Counts how many new nodes we materialize (i.e., new substrings)
        long created = 0L;

        // Start a new traversal from each starting index i
        for (int i = 0; i < s.length(); i++) {
            // Reset walker to root for the current suffix
            Node node = root;

            // Extend the suffix one character at a time
            for (int j = i; j < s.length(); j++) {
                // Convert current character into 0..25 index
                int idx = s.charAt(j) - 'a';
                // Guard invalid characters to keep indices safe
                if (idx < 0 || idx >= 26) throw new IllegalArgumentException("only 'a'..'z' supported");
                // Create child only when this substring hasn't appeared before
                if (node.next[idx] == null) {
                    // Count discovery of a brand-new substring
                    created++;
                    // Allocate the node to record this new path
                    node.next[idx] = new Node();
                }
                // Walk forward to continue growing the substring
                node = node.next[idx];
            }
        }

        // Return distinct non-empty substring count (root not counted)
        return created;
        // To also count the empty substring, return created + 1;
    }

    /**
     * Problem: Finds the maximum XOR among any two different numbers
     * Intuition: From left to right try to check if there exists a bit that can make the result bit 1
     * Becuase if we have 1s in all bits, the answer will be maximal.
     * So build a trie for all numbers in the array in bit representation (0|1) 
     * 
     * Bit manipulation:
     * 
     * - Check if nth bit is set or not in number num: 1 & (num >> n) -> 1 if bit is set 0 if not
     * - Turn on the nth bit of a number num: num | (1 << n)
     */
    public int findMaximumXOR(int[] nums) {
        // Guard tiny arrays where no pair exists

        Node root = new Node();
        int best = 0;

        // Insert the first number so we can query from the second
        // Using separate method to keep traversal logic clear
        insert(root, nums[0]);

        // Process each remaining number as the right element of the pair
        for (int i = 1; i < nums.length; i++) {
            // Query the trie to get the best partner xor for nums[i]
            int candidate = queryMaxXor(root, nums[i]);
            // Update the global best when this pair improves the answer
            if (candidate > best) best = candidate;
            // Insert current number for future pairs
            insert(root, nums[i]);
        }

        // Return the best xor seen across all pairs
        return best;
    }

    private int queryMaxXor(Node root, int x) {
        // Start from root to search for the most opposite bits
        Node cur = root;
        // Accumulates the xor we can achieve against some stored number
        int ans = 0;
        // Walk from most significant bit to least
        for (int b = 30; b >= 0; b--) {
            // Pull current bit of x
            int bit = (x >>> b) & 1;
            // Prefer the opposite bit to get a 1 at this position
            int want = bit ^ 1;
            // If opposite branch exists, take it to improve xor
            if (cur.nxt[want] != null) {
                // Set this bit in answer because we achieved opposite bits
                ans = ans | (1 << b);
                // Move along the preferred opposite branch
                cur = cur.nxt[want];
            } else {
                // Fall back to same bit branch since opposite is missing
                cur = cur.nxt[bit];
            }
        }
        // Return the maximum xor we can get against any inserted number
        return ans;
    }

    /**
     * Problem: Given a list of queries of pairs (xi, ai), for each query pair, check the max XOR possible for all
     * nums <= ai
     * Intuition:
     * We build a trie starting from smaller ai and keep building to largest ai and insert only elements <= ai
     * Once we build a trie for a query, it satisfies the rule that all nums will be <= ai
     * For each query call queryMaxXor
     */
    public int[] maximizeXor(int[] nums, int[][] queries) {
        // Sort nums ascending so we can insert up to each threshold
        Arrays.sort(nums);

        // Prepare queries with original indices preserved
        int q = queries.length;
        // Each element: [m, x, originalIndex]
        int[][] qs = new int[q][3];

        // Pack queries to sort by m while keeping x and index
        for (int i = 0; i < q; i++) {
            // Store threshold for ordering
            qs[i][0] = queries[i][1];
            // Store the x to be xored
            qs[i][1] = queries[i][0];
            // Store where to place the answer later
            qs[i][2] = i;
        }

        // Sort by threshold m to enable incremental inserts
        Arrays.sort(qs, (a, b) -> Integer.compare(a[0], b[0]));

        // Allocate the result array in original order
        int[] ans = new int[q];
        // Root of the trie that holds numbers <= current m
        Node root = new Node();
        // Pointer into nums for incremental insertion
        int i = 0;

        // Process queries in increasing m
        for (int[] entry : qs) {
            // Pull current threshold, value to xor, and original index
            int m = entry[0], x = entry[1], idx = entry[2];

            // Insert all numbers that are <= m into the trie
            while (i < nums.length && nums[i] <= m) {
                // Insert nums[i] so it becomes available to future queries
                insert(root, nums[i]);
                // Advance to the next number for potential insertion
                i++;
            }

            // If trie is still empty, no valid y exists for this query
            if (i == 0) {
                // Record -1 since nothing satisfies y <= m
                ans[idx] = -1;
                // Continue to next query without a trie walk
                continue;
            }

            // Compute best xor partner for x using the current trie
            ans[idx] = queryMaxXor(root, x);
        }

        // Return answers aligned with original query order
        return ans;
    }
    
}



class Trie {
    private static class Node {
        int prefixCount;
        int endCount;
        Node[] next = new Node[26];
    }
    private final Node root = new Node();
    public void insert(String word) {
        if (word == null) return;
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
   
            int idx = word.charAt(i) - 'a';
   
            if (cur.next[idx] == null) cur.next[idx] = new Node();
   
            cur = cur.next[idx];
   
            cur.prefixCount++;
        }
        cur.endCount++;
    }
    public boolean search(String word) {
        if (word == null) return false;
        Node node = findNode(word);
        return node != null && node.endCount > 0;
    }
    public boolean startsWith(String prefix) {
        if (prefix == null) return false;
        Node node = findNode(prefix);
        return node != null && node.prefixCount > 0 || (node != null && prefix.length() == 0 && node == root && root.endCount > 0);
    }
    public int countWordsEqualTo(String word) {
        if (word == null) return 0;
        Node node = findNode(word);
        return node == null ? 0 : node.endCount;
    }
    public int countWordsStartingWith(String prefix) {
        if (prefix == null) return 0;
        Node node = findNode(prefix);
        return node == null ? 0 : node.prefixCount;
    }
    public void erase(String word) {
        if (word == null) return;
        if (!search(word)) return;
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
   
            int idx = word.charAt(i) - 'a';
   
            Node child = cur.next[idx];
   
            child.prefixCount--;
   
            if (child.prefixCount == 0 && child.endCount == 0) {
       
                cur.next[idx] = null;
       
                return;
            }
   
            cur = child;
        }
        cur.endCount--;
    }
    private Node findNode(String s) {
        Node cur = root;
        for (int i = 0; i < s.length(); i++) {
   
            int idx = s.charAt(i) - 'a';
   
            if (idx < 0 || idx >= 26 || cur.next[idx] == null) return null;
   
            cur = cur.next[idx];
        }
        return cur;
    }
}


class DistinctSubstringsTrie {
    private static class Node {
        Map<Character, Node> next = new HashMap<>();
    }
    public long countDistinctSubstrings(String s) {
        if (s == null || s.isEmpty()) return 0L;

        Node root = new Node();
        long created = 0L;

        for (int i = 0; i < s.length(); i++) {
   
            Node node = root;

   
            for (int j = i; j < s.length(); j++) {
       
                char c = s.charAt(j);
       
                if (!node.next.containsKey(c)) {
           
                    created++;
           
                    node.next.put(c, new Node());
                }
       
                node = node.next.get(c);
            }
        }

        return created;
    }
}

