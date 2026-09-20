class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
//  Removing 2 sized arr with variables

        int notBuyAhead = 0;
        int buyAhead = 0;
        int notBuyCurr = 0;
        int buyCurr = 0;

        for (int i = n - 1; i >= 0; i--){
               
                buyCurr = Math.max(notBuyAhead - prices[i], buyAhead);
            
                notBuyCurr = Math.max(buyAhead + prices[i], notBuyAhead);

                notBuyAhead = notBuyCurr;
                buyAhead = buyCurr;
            }
            

        return buyAhead;

// Space optimized verison

        // int[] ahead = new int[2];
        // int[] curr = new int[2];

        // // not initialising ahead with 0, 0 bcoz in java by default all the values are zero during creation.

        // for (int i = n - 1; i >= 0; i--){
               
        //         curr[1] = Math.max(ahead[0] - prices[i], ahead[1]);
            
        //         curr[0] = Math.max(ahead[1] + prices[i], ahead[0]);

        //         ahead[0] = curr[0];
        //         ahead[1] = curr[1];
        //     }
            

        // return ahead[1];


// Iterative version

        // int[][] dp = new int[n+1][2];

        // for (int i = 0; i <=1; i++){
        //     dp[n][i] = 0;
        // }

        // for (int i = n - 1; i >= 0; i--){
        //     for (int buy = 0; buy <= 1; buy++){
        //         if (buy == 1){
        //             dp[i][buy] = Math.max(dp[i + 1][0] - prices[i], dp[i + 1][1]);
        //         }
        //         else {
        //              dp[i][buy] = Math.max(dp[i + 1][1] + prices[i], dp[i + 1][0]);
        //         }
        //     }
        // }

        // return dp[0][1];
    }


// Memoized version

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