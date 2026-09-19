class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        int[][] dp = new int[nums.length][2 * sum + 1];

        for (int i = 0; i < nums.length; i++) {
            java.util.Arrays.fill(dp[i], -1);
        }

        return recursion(nums, 0, target, sum, dp);
    }

    private int recursion(int[] nums, int i, int target, int sum, int[][] dp) {
        if (i == nums.length) {
            return target == 0 ? 1 : 0;
        }

        if (target + sum < 0 || target + sum > 2 * sum) {
            return 0;
        }

        if (dp[i][target + sum] != -1) {
            return dp[i][target + sum];
        }

        int add = recursion(nums, i + 1, target - nums[i], sum, dp);
        int subtract = recursion(nums, i + 1, target + nums[i], sum, dp);

        return dp[i][target + sum] = add + subtract;
    }
}