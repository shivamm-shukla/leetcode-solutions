class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;

        int[] vis = new int[n];
        int[] pathVis = new int[n];
        int[] check = new int[n];

        for (int i = 0; i < n; i++) {
            if (vis[i] == 0) {
                dfs(graph, vis, pathVis, i, check);
            }
        }

        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (check[i] == 1) {
                res.add(i);
            }
        }

        return res;
    }

    boolean dfs(int[][] graph, int[] vis, int[] pathVis,
                int node, int[] check) {

        vis[node] = 1;
        pathVis[node] = 1;
        check[node] = 0;

        for (int nei : graph[node]) {

            // If neighbor has not been visited
            if (vis[nei] == 0) {
                if (!dfs(graph, vis, pathVis, nei, check)) {
                    return false;
                }
            }

            // Neighbor is already in current DFS path -> cycle
            else if (pathVis[nei] == 1) {
                return false;
            }
        }

        // No cycle reachable from this node
        pathVis[node] = 0;
        check[node] = 1;

        return true;
    }
}