class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;

        for (int num : nums) {
            sum += num;
        }

        if (sum % 2 != 0) return false;

        int n = nums.length;
        int target = sum / 2;

        int[][] dp = new int[n][target + 1];

        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }

        return targetSum(nums, n - 1, target, dp);
    }

    boolean targetSum(int[] nums, int n, int target, int[][] dp) {
        if (target == 0) return true;
        if (n < 0) return false;

        if (dp[n][target] != -1) {
            return dp[n][target] == 1;
        }

        boolean notTake = targetSum(nums, n - 1, target, dp);

        boolean take = false;
        if (target >= nums[n]) {
            take = targetSum(nums, n - 1, target - nums[n], dp);
        }

        dp[n][target] = (take || notTake) ? 1 : 0;

        return take || notTake;
    }
}