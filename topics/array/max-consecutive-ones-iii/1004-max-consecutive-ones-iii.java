class Solution {
    public int longestOnes(int[] nums, int k) {
        int[] freq = new int[2];
        int l = 0;
        int r = 0;
        int maxi = 0;
        while (r < nums.length){
            if (nums[r] == 0){
                freq[0] += 1;
                r++;
                while (freq[0] > k){
                    freq[nums[l]]--;
                    l++;
                }
            }
            else {
                freq[nums[r]]++;
                r++;
            }

            maxi = Math.max(maxi, freq[0]+freq[1]);
        }
        return maxi;
    }
}