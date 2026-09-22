import java.util.Queue;
import java.util.ArrayDeque;

class Solution { 
    public boolean isBipartite(int[][] graph) { 
        int n = graph.length; 
        int[] color = new int[n]; 

        for (int i = 0; i < n; i++) { 
            
            if (color[i] == 0) { 
                if (!bfs(graph, color, i)) return false; 
            } 
        } 
        return true; 
    } 

    private boolean bfs(int[][] graph, int[] color, int start) { 
        Queue<Integer> q = new ArrayDeque<>(); 
        q.offer(start); 
        color[start] = 1; 

        while (!q.isEmpty()) { 
            int node = q.poll(); 
            for (int nei : graph[node]) { 
                if (color[nei] == 0) { 
                    color[nei] = -color[node]; 
                    q.offer(nei); 
                } 
    
                else if (color[nei] == color[node]) { 
                    return false; 
                } 
            } 
        } 
        return true; 
    } 
}
