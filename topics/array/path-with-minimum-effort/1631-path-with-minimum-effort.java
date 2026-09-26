import java.util.*;

class Solution {

    static class Pair {
        int row;
        int col;
        int dist;
        
        Pair(int row, int col, int dist) {
            this.row = row;
            this.col = col;
            this.dist = dist;
        }
    }

    public int minimumEffortPath(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;
        
        int[][] dist = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.dist, b.dist));
        
        pq.add(new Pair(0, 0, 0));
        dist[0][0] = 0;
        
        int[] drow = {-1, 0, 1, 0};
        int[] dcol = {0, 1, 0, -1};
        
        while (!pq.isEmpty()) {
            Pair curr = pq.poll(); 
            int ro = curr.row;
            int co = curr.col;
            int d = curr.dist;
            
            if (ro == m - 1 && co == n - 1) {
                return d;
            }
            
            if (d > dist[ro][co]) {
                continue;
            }
  
            for (int i = 0; i < 4; i++) {
                int nrow = ro + drow[i];
                int ncol = co + dcol[i];
           
                if (nrow >= 0 && nrow < m && ncol >= 0 && ncol < n) {
            
                    int newEffort = Math.max(d, Math.abs(heights[ro][co] - heights[nrow][ncol]));

                    if (newEffort < dist[nrow][ncol]) {
                        dist[nrow][ncol] = newEffort;
                        pq.add(new Pair(nrow, ncol, newEffort));
                    }
                }
            }
        }
        return 0;
    }
}
