class Solution {
    public int rob(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, -1);

        return recursion(nums, nums.length - 1, dp);
    }

    private int recursion(int[] nums, int n, int[] dp) {
        if (n == 0) return nums[0];
        if (n < 0) return 0;


        if (dp[n] != -1) {
            return dp[n];
        }

        int rob = nums[n] + recursion(nums, n - 2, dp);

        int skip = recursion(nums, n - 1, dp);

      
        

        return dp[n] = Math.max(rob, skip);
    }
}