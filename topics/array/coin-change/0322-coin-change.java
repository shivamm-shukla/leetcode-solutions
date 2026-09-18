import java.util.Arrays;

class Solution {
    public int coinChange(int[] coins, int amount) {

        int n = coins.length;

        int[][] dp = new int[n][amount + 1];

        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        int ans = recursion(coins, n - 1, amount, dp);

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private int recursion(int[] coins, int ind,
                          int amount, int[][] dp) {

        if (amount == 0) return 0;

        if (ind == 0) {
            if (amount % coins[0] == 0) {
                return amount / coins[0];
            } else {
                return Integer.MAX_VALUE;
            }
        }

        if (dp[ind][amount] != -1) {
            return dp[ind][amount];
        }

        int notTake = recursion(coins, ind - 1, amount, dp);

        int take = Integer.MAX_VALUE;

        if (coins[ind] <= amount) {
            int result = recursion(
                coins, ind, amount - coins[ind], dp
            );

            if (result != Integer.MAX_VALUE) {
                take = 1 + result;
            }
        }

        return dp[ind][amount] = Math.min(take, notTake);
    }
}