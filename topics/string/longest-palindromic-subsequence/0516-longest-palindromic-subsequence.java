class Solution {
    public int longestPalindromeSubseq(String s) {
        int n = s.length();

        int[][] dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return recursion(s, 0, n - 1, dp);
    }

    private int recursion(String s, int i, int j, int[][] dp) {

        if (i > j) {
            return 0;
        }

        if (i == j) {
            return 1;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        if (s.charAt(i) == s.charAt(j)) {
            dp[i][j] = 2 + recursion(s, i + 1, j - 1, dp);
        } else {
            int notTakeLeft = recursion(s, i + 1, j, dp);
            int notTakeRight = recursion(s, i, j - 1, dp);

            dp[i][j] = Math.max(notTakeLeft, notTakeRight);
        }

        return dp[i][j];
    }
}