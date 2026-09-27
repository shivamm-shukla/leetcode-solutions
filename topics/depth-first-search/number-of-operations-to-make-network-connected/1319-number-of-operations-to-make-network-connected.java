class Solution {

    class DisjointSet {

        int[] size;
        int[] parent;

        DisjointSet(int n) {
            this.size = new int[n];
            this.parent = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int findParent(int v) {
            if (parent[v] == v) return v;

            return parent[v] = findParent(parent[v]);
        }

        void unionBySize(int u, int v) {
            int ulpu = findParent(u);
            int ulpv = findParent(v);

            if (ulpu != ulpv) {
                if (size[ulpu] < size[ulpv]) {
                    parent[ulpu] = ulpv;
                    size[ulpv] += size[ulpu];
                } else {
                    parent[ulpv] = ulpu;
                    size[ulpu] += size[ulpv];
                }
            }
        }
    }

    public int makeConnected(int n, int[][] connections) {

        // Not enough cables to connect n computers
        if (connections.length < n - 1) {
            return -1;
        }

        DisjointSet ds = new DisjointSet(n);

        // Connect all possible components
        for (int[] edge : connections) {

            int u = edge[0];
            int v = edge[1];

            if (ds.findParent(u) != ds.findParent(v)) {
                ds.unionBySize(u, v);
            }
        }

        int components = 0;

        for (int i = 0; i < n; i++) {
            if (ds.findParent(i) == i) {
                components++;
            }
        }

        return components - 1;
    }
}