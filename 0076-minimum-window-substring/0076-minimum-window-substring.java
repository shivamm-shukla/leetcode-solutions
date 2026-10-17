class Solution {
    public String minWindow(String s, String t) {
        int m = s.length();
        int n = t.length();

        if (n == 0 || m < n) return "";

        int[] freq = new int[52];

        for (int i = 0; i < n; i++) {
            freq[getIndex(t.charAt(i))]++;
        }

        int l = 0;
        int r = 0;
        int cnt = 0;
        int st = -1;
        int end = -1;
        int minLen = Integer.MAX_VALUE;

        while (r < m) {
            char ch = s.charAt(r);
            int idx = getIndex(ch);

            if (freq[idx] > 0) {
                cnt++;
            }

            freq[idx]--;
            r++;

            if (cnt == n) {
                while (cnt == n) {
                    int leftIdx = getIndex(s.charAt(l));
                    freq[leftIdx]++;

                    if (freq[leftIdx] > 0) {
                        cnt--;
                    }

                    if (r - l < minLen) {
                        minLen = r - l;
                        st = l;
                        end = r;
                    }

                    l++;
                }
            }
        }

        return st == -1 ? "" : s.substring(st, end);
    }

    public int getIndex(char c) {
        if (c >= 'a' && c <= 'z') {
            return c - 'a';
        } else {
            return (c - 'A') + 26;
        }
    }
}