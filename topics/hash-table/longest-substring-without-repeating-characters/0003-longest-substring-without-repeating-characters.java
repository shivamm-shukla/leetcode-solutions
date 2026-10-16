class Solution {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int l = 0;
        int r = 0;
        int maxi = 0;
        while (r < s.length()){
            if (map.containsKey(s.charAt(r))){
                if (map.get(s.charAt(r)) >= l) l = map.get(s.charAt(r)) + 1;
            }
            
            maxi = Math.max(maxi, r-l+1);
            map.put(s.charAt(r), r);
            r++;

        }

        return maxi;
    }
}