class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length();
        int[] freq = new int[3];
        int l = 0;
        int r = 0;
        int count = 0;

        while (r < n) {
            char ch = s.charAt(r);
            freq[ch - 'a']++;

            while (containsAll(freq)) {
                count += n - r;
                freq[s.charAt(l) - 'a']--;
                l++;
            }

            r++;
        }

        return count;
    }

    boolean containsAll(int[] freq) {
        for (int f : freq) {
            if (f == 0) return false;
        }
        return true;
    }
}