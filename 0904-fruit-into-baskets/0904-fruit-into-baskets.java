import java.util.HashMap;
import java.util.Map;

class Solution {
    public int totalFruit(int[] fruits) {
        Map<Integer, Integer> freq = new HashMap<>();
        int l = 0;
        int r = 0;
        int maxi = 0;

        while (r < fruits.length) {

            if (freq.containsKey(fruits[r]) || freq.size() < 2) {
                freq.put(fruits[r], freq.getOrDefault(fruits[r], 0) + 1);
                r++;
            } 
          
            else {
                while (freq.size() == 2) {
                    freq.put(fruits[l], freq.get(fruits[l]) - 1);
                    if (freq.get(fruits[l]) == 0) {
                        freq.remove(fruits[l]);
                    }
                    l++;
                }
             
                freq.put(fruits[r], 1);
                r++;
            }

            
            int sum = 0;
            for (int value : freq.values()) {
                sum += value;
            }

            maxi = Math.max(maxi, sum);
        }
        return maxi;
    }
}
