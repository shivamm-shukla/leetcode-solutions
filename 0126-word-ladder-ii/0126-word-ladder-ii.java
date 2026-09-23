class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {

        List<List<String>> res = new ArrayList<>();

        Set<String> set = new HashSet<>(wordList);

        if (!set.contains(endWord)) {
            return res;
        }

        // child -> all parents which can reach child
        Map<String, List<String>> parents = new HashMap<>();

        Queue<String> q = new ArrayDeque<>();
        q.offer(beginWord);

        set.remove(beginWord);

        boolean found = false;

        while (!q.isEmpty() && !found) {

            int size = q.size();

            Set<String> usedThisLevel = new HashSet<>();

            for (int k = 0; k < size; k++) {

                String word = q.poll();

                char[] chars = word.toCharArray();

                for (int i = 0; i < chars.length; i++) {

                    char original = chars[i];

                    for (char ch = 'a'; ch <= 'z'; ch++) {

                        if (ch == original) {
                            continue;
                        }

                        chars[i] = ch;

                        String nextWord = new String(chars);

                        if (set.contains(nextWord)) {

                            // First time seeing this word
                            if (!usedThisLevel.contains(nextWord)) {
                                q.offer(nextWord);
                                usedThisLevel.add(nextWord);
                            }

                            // Store parent
                            parents
                                .computeIfAbsent(nextWord, x -> new ArrayList<>())
                                .add(word);

                            if (nextWord.equals(endWord)) {
                                found = true;
                            }
                        }
                    }

                    chars[i] = original;
                }
            }

            // Remove only after complete level
            set.removeAll(usedThisLevel);
        }

        if (!found) {
            return res;
        }

        // DFS from endWord -> beginWord
        List<String> path = new ArrayList<>();
        path.add(endWord);

        dfs(endWord, beginWord, parents, path, res);

        return res;
    }

    private void dfs(
            String word,
            String beginWord,
            Map<String, List<String>> parents,
            List<String> path,
            List<List<String>> res) {

        if (word.equals(beginWord)) {

            List<String> temp = new ArrayList<>(path);
            Collections.reverse(temp);

            res.add(temp);
            return;
        }

        if (!parents.containsKey(word)) {
            return;
        }

        for (String parent : parents.get(word)) {

            path.add(parent);

            dfs(parent, beginWord, parents, path, res);

            path.remove(path.size() - 1);
        }
    }
}