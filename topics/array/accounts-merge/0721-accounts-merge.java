import java.util.*;

class Solution {

    class DSU {
        int[] parent;
        int[] rank;

        DSU(int n) {
            parent = new int[n];
            rank = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        void union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);

            if (rootA == rootB) {
                return;
            }

            // Union by rank
            if (rank[rootA] < rank[rootB]) {
                parent[rootA] = rootB;
            } else if (rank[rootA] > rank[rootB]) {
                parent[rootB] = rootA;
            } else {
                parent[rootB] = rootA;
                rank[rootA]++;
            }
        }
    }

    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        int n = accounts.size();
        DSU dsu = new DSU(n);

        // email -> account index
        Map<String, Integer> emailToAccount = new HashMap<>();

        for (int i = 0; i < n; i++) {
            List<String> account = accounts.get(i);

            for (int j = 1; j < account.size(); j++) {
                String email = account.get(j);

                if (emailToAccount.containsKey(email)) {
                    dsu.union(i, emailToAccount.get(email));
                } else {
                    emailToAccount.put(email, i);
                }
            }
        }

        Map<Integer, List<String>> merged = new HashMap<>();

        for (String email : emailToAccount.keySet()) {
            int root = dsu.find(emailToAccount.get(email));

            merged
                .computeIfAbsent(root, k -> new ArrayList<>())
                .add(email);
        }

        List<List<String>> result = new ArrayList<>();

        for (Map.Entry<Integer, List<String>> entry : merged.entrySet()) {
            int root = entry.getKey();
            List<String> emails = entry.getValue();

            Collections.sort(emails);

            List<String> account = new ArrayList<>();
            account.add(accounts.get(root).get(0));
            account.addAll(emails);

            result.add(account);
        }

        return result;
    }
}