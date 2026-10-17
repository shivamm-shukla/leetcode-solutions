class Solution {
    public int subarraysWithKDistinct(int[] nums, int k) {
        return atMost(nums, k) - atMost(nums, k-1);
    }

    int atMost(int[] nums, int k){
        if (k < 0) return 0;
        Map<Integer, Integer> map = new HashMap<>();
        int l = 0;
        int r = 0;
        int count = 0;

        while (r < nums.length){
            map.put(nums[r], map.getOrDefault(nums[r], 0) + 1);

            if (map.size() > k){
                while (map.size() > k){
                    map.put(nums[l], map.get(nums[l])-1);
                    if (map.getOrDefault(nums[l], 0) == 0) map.remove(nums[l]);
                    l++;
                }
            }
            count += r - l + 1;
            r++;
        }
        return count;
    }
}