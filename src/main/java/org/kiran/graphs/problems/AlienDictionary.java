/**
 * Def: Given a list of lexicographically sorted list of alien strings formed using K chars, return the lexicographic order of the chars
 * Assumption: The ordering is guaranteed to exist, so we can think of forming a DAG -> apply topological sorting
 * Intuition: s1 < s2, so s1[0] < s2[0], so if we were to form a DAG s1[0] -> s2[0] (since ther ordering is guarantedd to exist, we can prepare a DAG)
 * 
 */

class Solution {
    public String alienDictionary(String[] words, int K) {
        List<List<Integer>> graph = buildGraph(words, K);
        StringBuilder finalOrder = new StringBuilder();
        for (int n : topoSortDfs(K, graph)) {
            finalOrder.append((char)(n + 'a')); // convert back to char
        }
        return finalOrder.toString();
    }

    private List<List<Integer>> buildGraph(String[] words, int K) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < words.length - 1; i++) {
            String s1 = words[i];
            String s2 = words[i + 1];
            for (int j = 0; j < Math.min(s1.length(), s2.length()); j++) {
                int u = s1.charAt(j) - 'a';
                int v = s2.charAt(j) - 'a';
                if (u != v) {
                    graph.get(u).add(v);
                    break;
                }
            }
        }
        return graph;
    }

    public List<Integer> topoSortDfs(int V, List<List<Integer>> graph) {
        boolean[] visited = new boolean[V];
        Stack<Integer> stk = new Stack<>();

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i, graph, visited, stk);
            }
        }

        List<Integer> res = new ArrayList<>();
        while (!stk.isEmpty()) {
            res.add(stk.pop());
        }
        return res;
    }

    private void dfs(int node, List<List<Integer>> graph, boolean[] visited, Stack<Integer> stk) {
        visited[node] = true;
        for (Integer child : graph.get(node)) {
            if (!visited[child]) {
                dfs(child, graph, visited, stk);
            }
        }
        stk.push(node);
    }
}
