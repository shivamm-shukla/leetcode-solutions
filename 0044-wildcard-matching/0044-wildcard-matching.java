class Solution {
    public boolean isMatch(String s, String p) {
        int m = p.length();
        int n = s.length();

        boolean[][] dp = new boolean[m + 1][n + 1];

        dp[0][0] = true;

        for (int j = 1; j <= n; j++) {
            dp[0][j] = false;
        }

        for (int i = 1; i <= m; i++) {
            boolean f = true;

            for (int ii = 1; ii <= i; ii++) {
                if (p.charAt(ii - 1) != '*') {
                    f = false;
                    break;
                }
            }

            dp[i][0] = f;
        }


        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                char pc = p.charAt(i - 1);
                char sc = s.charAt(j - 1);

                if (pc == sc || pc == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                else if (pc == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                }
                else {
                    dp[i][j] = false;
                }
            }
        }

        return dp[m][n];
    }
}