class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        return atMost(nums, k) - atMost(nums, k-1);
    }

    public int atMost(int[] nums, int goal) {
        if (goal < 0) return 0;  

        int count = 0;
        int sum = 0;
        int l = 0;
        int r = 0;
        int n = nums.length;

        while (r < n) {
            sum += (nums[r] % 2);

            if (sum > goal) {
                while (sum > goal) {
                    sum -= (nums[l] % 2);
                    l++;
                }
            }

            count += r - l + 1;
            r++;
        }

        return count;
    }
}