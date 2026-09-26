class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        if (grid[0][0] == 1 || grid[m-1][n-1] == 1) return -1;
        boolean[][] visited = new boolean[m][n];
        Queue<Pair> q = new ArrayDeque<>();
        visited[0][0] = true;
        q.offer(new Pair(0, 0, 1));

        while (!q.isEmpty()){
            Pair curr = q.poll();
            int ro = curr.row;
            int co = curr.col;
            int level = curr.level;

            if (ro == m-1 && co == n-1 && grid[ro][co] == 0){
                return level;
            }

            int[] drow = {0, -1, -1, -1, 0, 1, 1, 1};
            int[] dcol = {-1, -1, 0, 1, 1, 1, 0, -1};

            for (int k = 0; k < 8; k++){
                int nrow = ro + drow[k];
                int ncol  = co + dcol[k];

                if (nrow >= 0 && nrow < m && ncol >= 0 && ncol < n && !visited[nrow][ncol] && grid[nrow][ncol] == 0){
                    visited[nrow][ncol] = true;
                    q.offer(new Pair(nrow, ncol, level + 1));
                }
            }
        }

        return -1;

    }
    class Pair {
        int row;
        int col;
        int level;

        Pair(int row, int col, int level){
            this.row = row;
            this.col = col;
            this.level = level;
        }
    }
}