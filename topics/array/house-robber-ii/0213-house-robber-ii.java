class Solution {

    public int rob(int[] nums) {
        int n = nums.length;

        if (n == 1) {
            return nums[0];
        }

        // Case 1: Don't consider last house
        int case1 = robRange(nums, 0, n - 2);

        // Case 2: Don't consider first house
        int case2 = robRange(nums, 1, n - 1);

        return Math.max(case1, case2);
    }

    private int robRange(int[] nums, int start, int end) {

        int[] dp = new int[end - start + 1];

        dp[0] = nums[start];

        if (dp.length > 1) {
            dp[1] = Math.max(nums[start], nums[start + 1]);
        }

        for (int i = 2; i < dp.length; i++) {
            int rob = nums[start + i] + dp[i - 2];
            int skip = dp[i - 1];

            dp[i] = Math.max(rob, skip);
        }

        return dp[dp.length - 1];
    }
}