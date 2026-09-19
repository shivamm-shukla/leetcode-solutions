class Solution {
    private void dfs(int[][] adj, boolean[] visited, int node){
        visited[node] = true;

        for (int i = 0; i < adj.length; i++){
            if(adj[node][i] == 1 && !visited[i]){
                dfs(adj, visited, i);
            }
        }
    }
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int count = 0;

        for (int i = 0; i < n; i++){
            if (!visited[i]){
                count++;
                dfs(isConnected, visited,i);
            }
        }
        return count;
    }
}