class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++){
            Arrays.fill(dp[i], -1);
        }

        return recursion(m - 1, n - 1, dp);
    }

    private int recursion(int m, int n, int[][] dp) {

        if (m == 0 && n == 0) {
            return 1;
        }

        if (m < 0 || n < 0) {
            return 0;
        }

        if (dp[m][n] != -1) return dp[m][n];

        

        int left = recursion(m, n - 1, dp);

        int right = recursion(m - 1, n, dp);

        return dp[m][n] = (left + right);
    }
}