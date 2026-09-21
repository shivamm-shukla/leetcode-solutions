class Solution {
    class Pair{
        int row;
        int col; 
        int time;

        Pair(int row, int col, int time){
            this.row = row; 
            this.col = col;
            this.time = time;
        }
    }
    public int orangesRotting(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        Queue<Pair> queue = new ArrayDeque<>();
        int[][] visited = new int[m][n];
        int cntFresh = 0;
        
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                if (grid[i][j] == 2){
                    queue.add(new Pair(i, j, 0));
                    visited[i][j] = 2;
                }
                else {
                    visited[i][j] = 0;
                }

                if (grid[i][j] == 1) cntFresh++;
            }
        }

        int maxTime = 0;
        int count = 0;
        int[] drow = {-1, 0, +1, 0}; // left , right
        int[] dcol = {0, +1, 0, -1}; // up, down

        while (!queue.isEmpty()){
            int r = queue.peek().row;
            int c = queue.peek().col; 
            int t = queue.peek().time;
            maxTime = Math.max(maxTime, t);
            queue.remove();

            for (int i = 0; i < 4; i++){
                int nrow = r + drow[i];
                int ncol = c + dcol[i];

                if (nrow >= 0 && nrow < m && ncol >= 0 && ncol < n && visited[nrow][ncol] == 0 && grid[nrow][ncol] == 1){
                    queue.add(new Pair(nrow, ncol, t + 1));
                    visited[nrow][ncol] = 2;
                    count++;
                }
            }
        }
        if (count != cntFresh) return -1;
        return maxTime;
    }
}