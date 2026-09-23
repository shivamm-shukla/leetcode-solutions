class Solution {

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Queue<Pair<String, Integer>> q = new LinkedList<>();
        q.add(new Pair<>(beginWord, 1));

        Set<String> st = new HashSet<>(wordList);
        if (!st.contains(endWord)) return 0;

        st.remove(beginWord);

        while (!q.isEmpty()) {
            Pair<String, Integer> curr = q.poll();
            String word = curr.getKey();
            int steps = curr.getValue();

            if (word.equals(endWord)) return steps;

            for (int i = 0; i < word.length(); i++) {
                char[] arr = word.toCharArray();
                char original = arr[i];

                for (char ch = 'a'; ch <= 'z'; ch++) {
                    if (ch == original) continue;

                    arr[i] = ch;
                    String newWord = new String(arr);

                    if (st.contains(newWord)) {
                        st.remove(newWord);
                        q.add(new Pair<>(newWord, steps + 1));
                    }
                }
            }
        }
        return 0;
    }
}

class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }
}