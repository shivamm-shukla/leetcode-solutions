class Solution {
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;

        int[][] visited = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1' && visited[i][j] == 0) {
                    count++;
                    bfs(grid, visited, i, j);
                }
            }
        }

        return count;
    }

    void bfs(char[][] grid, int[][] visited, int ro, int co) {
        int m = grid.length;
        int n = grid[0].length;

        Queue<Pair> q = new ArrayDeque<>();

        int[] drow = {-1, 0, 1, 0};
        int[] dcol = {0, 1, 0, -1};

        visited[ro][co] = 1;
        q.offer(new Pair(ro, co));

        while (!q.isEmpty()) {
            int row = q.peek().first;
            int col = q.peek().second;
            q.poll();

            for (int i = 0; i < 4; i++) {
                int nrow = row + drow[i];
                int ncol = col + dcol[i];

                if (nrow >= 0 && nrow < m &&
                    ncol >= 0 && ncol < n &&
                    grid[nrow][ncol] == '1' &&
                    visited[nrow][ncol] == 0) {

                    visited[nrow][ncol] = 1;
                    q.offer(new Pair(nrow, ncol));
                }
            }
        }
    }

    class Pair {
        int first;
        int second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
}