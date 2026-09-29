class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;

        // int[][] dp = new int[n+1][2*k + 1];

        // for (int i = n-1; i >= 0; i--){
        //     for (int trans = 1; trans <= 2*k; trans++){
        //         if (trans % 2 == 0){
        //             dp[i][trans] = Math.max(
        //                 dp[i+1][trans-1]-prices[i], dp[i+1][trans]
        //             );
        //         }
        //         else {
        //             dp[i][trans] = Math.max(
        //                 dp[i+1][trans-1] + prices[i], dp[i+1][trans]
        //             );
        //         }
        //     }
        // }

        // return dp[0][2*k];


// spaced optimized version
        int[] after = new int[2*k + 1];
        int[] current = new int[2*k + 1];

        for (int i = n-1; i >= 0; i--){
            for (int trans = 1; trans <= 2*k; trans++){
                if (trans % 2 == 0){
                  current[trans] = Math.max(
                        after[trans-1]-prices[i], after[trans]
                    );
                }
                else {
                    current[trans] = Math.max(
                        after[trans-1] + prices[i], after[trans]
                    );
                }
            }

            after = current;
        }

        return after[2*k];

    }
}

    