import java.util.*;

class WordLadder {
    // Build all shortest ladders using BFS parents map and DFS backtrack
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        // Use a set to test membership in O(1)
        Set<String> dict = new HashSet<>(wordList);
        // Early exit when endWord is absent
        if (!dict.contains(endWord)) return new ArrayList<>();
        // Include beginWord so neighbor generation works from start
        dict.add(beginWord);

        // Store shortest distance from beginWord to each word
        Map<String, Integer> dist = new HashMap<>();
        // Store all parents that lead to a word with optimal distance
        Map<String, List<String>> parents = new HashMap<>();
        // Standard BFS queue
        ArrayDeque<String> q = new ArrayDeque<>();

        // Initialize BFS source at distance 0
        dist.put(beginWord, 0);
        // Begin exploring from the start word
        q.add(beginWord);

        // Cache word length to avoid repeated property access
        int L = beginWord.length();
        // Track the first distance at which endWord is found
        int shortestEnd = Integer.MAX_VALUE;

        // Process queue until empty
        while (!q.isEmpty()) {
            // Pop next word to expand
            String cur = q.poll();
            // Read current distance once
            int d = dist.get(cur);

            // Stop expanding deeper layers beyond discovered shortestEnd
            if (d >= shortestEnd - 1) continue;

            // Reuse array to generate all one-letter neighbors
            char[] arr = cur.toCharArray();
            // Try to flip each position
            for (int i = 0; i < L; i++) {
                // Keep original char to restore later
                char orig = arr[i];
                // Try all lowercase letters
                for (char c = 'a'; c <= 'z'; c++) {
                    // Skip no-change move
                    if (c == orig) continue;
                    // Write candidate letter
                    arr[i] = c;
                    // Build neighbor string
                    String next = new String(arr);
                    // Only consider neighbors present in dictionary
                    if (!dict.contains(next)) continue;

                    // If next is unseen, set distance and enqueue
                    if (!dist.containsKey(next)) {
                        // Record next layer distance
                        dist.put(next, d + 1);
                        // Record current as a parent for shortest path
                        parents.computeIfAbsent(next, k -> new ArrayList<>()).add(cur);
                        // Push neighbor for future expansion
                        q.add(next);
                        // If we reached endWord first time, record shortestEnd
                        if (next.equals(endWord)) shortestEnd = d + 1;
                    } else if (dist.get(next) == d + 1) {
                        // Also record alternative parent at same optimal distance
                        parents.computeIfAbsent(next, k -> new ArrayList<>()).add(cur);
                    }
                }
                // Restore original letter for next position
                arr[i] = orig;
            }
        }

        // Prepare result container for all paths
        List<List<String>> ans = new ArrayList<>();
        // If endWord is unreachable, return empty result
        if (!dist.containsKey(endWord)) return ans;

        // Use linked list to add/remove at the front during backtrack
        LinkedList<String> path = new LinkedList<>();
        // Start path from the end word to backtrack toward beginWord
        path.add(endWord);
        // Launch DFS to build all sequences
        backtrack(endWord, beginWord, parents, path, ans);
        // Return collected shortest ladders
        return ans;
    }

    // Recursively build paths from endWord back to beginWord
    private void backtrack(String cur, String start, Map<String, List<String>> parents,
                           LinkedList<String> path, List<List<String>> ans) {
        // When start is reached, we have a full path in correct order
        if (cur.equals(start)) {
            // Add a copy of path to answers
            ans.add(new ArrayList<>(path));
            // Return to explore other branches
            return;
        }
        // Get parent list or empty when none
        List<String> ps = parents.get(cur);
        // If no parents, no path continues to start
        if (ps == null) return;
        // Try each parent to explore all shortest variants
        for (String p : ps) {
            // Add parent at front to keep start→…→end order
            path.addFirst(p);
            // Recurse toward the start word
            backtrack(p, start, parents, path, ans);
            // Remove parent to restore path for next branch
            path.removeFirst();
        }
    }
}
