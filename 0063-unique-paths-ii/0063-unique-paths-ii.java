class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        int[][] dp = new int[m][n];

        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        return recursion(obstacleGrid, m - 1, n - 1, dp);
    }

    private int recursion(int[][] grid, int i, int j, int[][] dp) {

        if (i < 0 || j < 0) return 0;

        if (grid[i][j] == 1) return 0;

        if (i == 0 && j == 0) return 1;

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int left = recursion(grid, i, j - 1, dp);
        int right = recursion(grid, i - 1, j, dp);

        return dp[i][j] = left + right;
    }
}