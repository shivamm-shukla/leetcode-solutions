class Solution {
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;

        int[][] dp = new int[n+1][2];
        for (int i = 0; i <= n; i++){
            Arrays.fill(dp[i], -1);
        }

        int memo = memo(prices, dp, 0, 1, fee);

        return memo;
    }

    int memo(int[] prices, int[][] dp, int ind, int buy, int fee){
        if (ind == prices.length) return 0;

        if (dp[ind][buy] != -1) return dp[ind][buy];
        if (buy == 1) {
            return dp[ind][buy] = Math.max(memo(prices, dp, ind + 1, 0, fee) - prices[ind], memo(prices, dp, ind+1, 1, fee));
        }
        else {
            return dp[ind][buy] = Math.max(memo(prices, dp, ind + 1, 1, fee) + prices[ind] - fee, memo(prices, dp, ind + 1, 0, fee));
        }
    }
}