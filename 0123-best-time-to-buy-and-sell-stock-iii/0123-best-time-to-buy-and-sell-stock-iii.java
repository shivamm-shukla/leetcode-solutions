class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;

        // int[][][] dp = new int[n+1][2][3];

        // for (int i = n-1; i >= 0; i--){
        //     for (int buy = 0; buy <= 1; buy++){
        //         for (int cap = 1; cap <= 2; cap++){
        //             if (buy == 1){
        //                 dp[i][buy][cap] = Math.max(
        //                     dp[i+1][0][cap] - prices[i], dp[i+1][1][cap]
        //                 );
        //             }
        //             else {
        //                 dp[i][buy][cap] = Math.max(
        //                     dp[i+1][1][cap-1] + prices[i], dp[i+1][0][cap]
        //                 );
        //             }
        //         }
        //     }
        // }

        // return dp[0][1][2];


// space optimize version


    int[][] after = new int[2][3];
    int[][] current = new int[2][3];

        for (int i = n-1; i >= 0; i--){
            for (int buy = 0; buy <= 1; buy++){
                for (int cap = 1; cap <= 2; cap++){
                    if (buy == 1){
                        current[buy][cap] = Math.max(
                            after[0][cap] - prices[i], after[1][cap]
                        );
                    }
                    else {
                        current[buy][cap] = Math.max(
                            after[1][cap-1] + prices[i], after[0][cap]
                        );
                    }
                }
            }

            after = current;
        }

        return after[1][2];

        
    }


}