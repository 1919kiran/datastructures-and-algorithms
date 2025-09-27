package org.kiran.trie;

class Trie {
    // Node for 26 lowercase letters
    private static class Node {
        // Tracks how many words share this prefix through this node
        int prefixCount;
        // Tracks how many words end exactly at this node
        int endCount;
        // Fixed fanout for lowercase 'a'..'z'
        Node[] next = new Node[26];
    }

    // Root does not represent any character
    private final Node root = new Node();

    // Inserts a word, increasing counts along its path
    public void insert(String word) {
        // Guard null input to keep structure consistent
        if (word == null) return;
        // Start walking from the root
        Node cur = root;
        // Traverse each character of the word
        for (int i = 0; i < word.length(); i++) {
            // Convert character to a child index 0..25
            int idx = word.charAt(i) - 'a';
            // Create missing node to extend the path
            if (cur.next[idx] == null) cur.next[idx] = new Node();
            // Move to the child to represent longer prefix
            cur = cur.next[idx];
            // Increase number of words sharing this prefix
            cur.prefixCount++;
        }
        // Increase words that end exactly at the last node
        cur.endCount++;
    }

    // Checks if a full word exists at least once
    public boolean search(String word) {
        // Guard null input to avoid traversal
        if (word == null) return false;
        // Find the terminal node for the word
        Node node = findNode(word);
        // True only if a word ends here
        return node != null && node.endCount > 0;
    }

    // Checks if any word starts with the given prefix
    public boolean startsWith(String prefix) {
        // Guard null input to avoid traversal
        if (prefix == null) return false;
        // Find the node representing the prefix
        Node node = findNode(prefix);
        // Existence plus nonzero prefixCount implies at least one extension
        return node != null && node.prefixCount > 0 || (node != null && prefix.length() == 0 && node == root && root.endCount > 0);
    }

    // Counts how many times this exact word was inserted minus erased
    public int countWordsEqualTo(String word) {
        // Guard null input to avoid traversal
        if (word == null) return 0;
        // Find the terminal node for the word
        Node node = findNode(word);
        // Return stored end count or zero if not found
        return node == null ? 0 : node.endCount;
    }

    // Counts how many words currently share this prefix
    public int countWordsStartingWith(String prefix) {
        // Guard null input to avoid traversal
        if (prefix == null) return 0;
        // Find the node representing the prefix
        Node node = findNode(prefix);
        // Return its prefix count or zero if path breaks
        return node == null ? 0 : node.prefixCount;
    }

    // Erases one occurrence of the word if present
    public void erase(String word) {
        // Guard null input to avoid traversal
        if (word == null) return;
        // Abort if the word does not exist to keep counts non-negative
        if (!search(word)) return;
        // Start from the root to walk and decrement
        Node cur = root;
        // Traverse characters to update prefix counts
        for (int i = 0; i < word.length(); i++) {
            // Map character to child index
            int idx = word.charAt(i) - 'a';
            // Get the child that must exist because search succeeded
            Node child = cur.next[idx];
            // Decrease how many words pass through that child
            child.prefixCount--;
            // If no word needs this child anymore, unlink to help GC
            if (child.prefixCount == 0 && child.endCount == 0) {
                // Cut the branch since it is no longer needed
                cur.next[idx] = null;
                // Stop early because deeper nodes are already unreachable
                return;
            }
            // Move forward to continue updates
            cur = child;
        }
        // Decrease the word ending count at the terminal node
        cur.endCount--;
    }

    // Finds the node reached by walking the given string
    private Node findNode(String s) {
        // Start from the root for traversal
        Node cur = root;
        // Walk character by character
        for (int i = 0; i < s.length(); i++) {
            // Convert character into child index
            int idx = s.charAt(i) - 'a';
            // Break early if the path does not exist
            if (idx < 0 || idx >= 26 || cur.next[idx] == null) return null;
            // Advance to the child to extend the matched prefix
            cur = cur.next[idx];
        }
        // Return the node corresponding to the full string
        return cur;
    }
}
