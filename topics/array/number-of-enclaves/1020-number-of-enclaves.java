class Solution {
    public int numEnclaves(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = new int[m][n];
        Queue<int[]> q = new ArrayDeque<>();

        // row
        for (int j = 0; j < n; j++){
            
            // first row
            if (grid[0][j] == 1){
                visited[0][j] = 1;
                q.offer(new int[]{0, j});
            }

            // last row
            if (grid[m-1][j] == 1){
                visited[m-1][j] = 1;
                q.offer(new int[]{m-1, j});
            }
        }

        // col

        // we are starting from 1 and going to till m-2 since we have already visited (0,0) and (m-1, 0)
        for (int i = 1; i < m-1; i++){
            // first col
            if (grid[i][0] == 1){
                visited[i][0] = 1;
                q.offer(new int[]{i, 0});
            }

            // last col
            if (grid[i][n-1] == 1){
                visited[i][n-1] = 1;
                q.offer(new int[]{i, n-1});
            }
        }

        int[] drow = {-1, 0, 1, 0};
        int[] dcol = {0, -1, 0, 1};
        while (!q.isEmpty()){
            int[] ind = q.poll();
            int row = ind[0];
            int col = ind[1];

            for (int i = 0; i < 4; i++){
                int nrow = row + drow[i];
                int ncol = col + dcol[i];

                if (nrow >= 0 && nrow < m && ncol >= 0 && ncol < n && grid[nrow][ncol] == 1 && visited[nrow][ncol] == 0){
                    visited[nrow][ncol] = 1;
                    q.offer(new int[]{nrow, ncol});
                }
            }
        }

        int count = 0;
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                if (grid[i][j] == 1 && visited[i][j] == 0) count++;
            }
        }

        return count;
    }
}