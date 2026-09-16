class Solution {
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) {
                    dp[i][j] = grid[i][j];
                } else {
                    int left = Integer.MAX_VALUE;
                    int up = Integer.MAX_VALUE;

                    if (j > 0) {
                        left = dp[i][j - 1];
                    }

                    if (i > 0) {
                        up = dp[i - 1][j];
                    }

                    dp[i][j] = grid[i][j] + Math.min(left, up);
                }
            }
        }

        return dp[m - 1][n - 1];
    }
}