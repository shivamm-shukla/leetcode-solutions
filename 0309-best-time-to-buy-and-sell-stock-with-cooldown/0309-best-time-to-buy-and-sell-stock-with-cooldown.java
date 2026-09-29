class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;

        int[][] dp = new int[n+2][2];

        for (int i = 0; i <= n+1; i++){
            Arrays.fill(dp[i], -1);
        }
        return memo(prices, dp, 0, 1);
    }

    int memo(int[] prices, int[][] dp, int ind, int buy){
        if (ind >= prices.length) return 0;

        if (dp[ind][buy] != -1) return dp[ind][buy];
        if (buy == 1){
            return dp[ind][buy] = Math.max(memo(prices, dp, ind + 1, 0) - prices[ind], memo(prices, dp, ind + 1, 1));
        }
        else {
            return dp[ind][buy] = Math.max(memo(prices, dp, ind + 2, 1) + prices[ind], memo(prices, dp, ind + 1, 0));
        }
    }
}