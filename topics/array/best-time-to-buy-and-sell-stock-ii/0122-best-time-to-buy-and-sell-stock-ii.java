class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+1][2];

        for (int i = 0; i <=1; i++){
            dp[n][i] = 0;
        }

        for (int i = n - 1; i >= 0; i--){
            for (int buy = 0; buy <= 1; buy++){
                if (buy == 1){
                    dp[i][buy] = Math.max(dp[i + 1][0] - prices[i], dp[i + 1][1]);
                }
                else {
                     dp[i][buy] = Math.max(dp[i + 1][1] + prices[i], dp[i + 1][0]);
                }
            }
        }

        return dp[0][1];
    }

    // int stock(int[] prices, int i, int buy){
    //     if (i == prices.length) return 0;

    //     int profit = 0;
    //     if (buy == 1){
    //         profit = Math.max(stock(prices, i + 1, 0) - prices[i], stock(prices, i + 1, 1));
    //     }
    //     else {
    //         profit = Math.max(stock(prices, i+1, 1) + prices[i], stock(prices, i+1, 0));
    //     }
    //     return profit;
    // }
}