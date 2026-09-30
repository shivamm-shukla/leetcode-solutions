class Solution {
    public int longestStrChain(String[] words) {
        int n = words.length;

        Arrays.sort(words, (a, b) -> a.length() - b.length());

    //     int[] dp = new int[n];
    //     Arrays.fill(dp, 1);

    //     int ans = 1;

    //     for (int i = 1; i < n; i++) {
    //         for (int j = 0; j < i; j++) {
    //             if (isPredecessor(words[j], words[i])) {
    //                 dp[i] = Math.max(dp[i], dp[j] + 1);
    //             }
    //         }

    //         ans = Math.max(ans, dp[i]);
    //     }

    //     return ans;
    // }

    // boolean isPredecessor(String s1, String s2) {
    //     if (s2.length() - s1.length() != 1) {
    //         return false;
    //     }

    //     int i = 0;
    //     int j = 0;

    //     while (i < s1.length() && j < s2.length()) {
    //         if (s1.charAt(i) == s2.charAt(j)) {
    //             i++;
    //             j++;
    //         } else {
    //             j++;
    //         }

    //         if (j - i > 1) {
    //             return false;
    //         }
    //     }

    //     return true;

// An Optimized version - Instead of thinking to check to wheather this word can be predecessor we will check that wheather after deleting a single char from a word does that word exist

        Map<String, Integer> dp = new HashMap<>();
        int ans = 1;

        for (String word : words) {
            int best = 1;

            for (int i = 0; i < word.length(); i++) {
                String prev = word.substring(0, i) + word.substring(i + 1);

                if (dp.containsKey(prev)) {
                    best = Math.max(best, dp.get(prev) + 1);
                }
            }

            dp.put(word, best);
            ans = Math.max(ans, best);
        }

        return ans;
    }
}