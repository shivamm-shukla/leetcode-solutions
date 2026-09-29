
class Solution {
    public int lengthOfLIS(int[] nums) {
        
        List<Integer> list = new ArrayList<>();
        list.add(nums[0]);

        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            
            if (num > list.get(list.size() - 1)) {
                list.add(num);
            } else {
        
                int index = Collections.binarySearch(list, num);
                
                if (index < 0) {
                    index = -index - 1;
                }
                
                list.set(index, num);
            }
        }

        return list.size();
    }
}
