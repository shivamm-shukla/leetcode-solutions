class Solution {
    public int findJudge(int n, int[][] trust) {
        int[] in = new int[n];
        int[] out = new int[n];

        indegree(trust, in);
        outdegree(trust, out);

        for (int i = 0; i < n; i++) {
            if (in[i] == n - 1 && out[i] == 0) {
                return i + 1;
            }
        }

        return -1;
    }

    void indegree(int[][] trust, int[] in) {
        for (int i = 0; i < trust.length; i++) {
            in[trust[i][1] - 1]++;
        }
    }

    void outdegree(int[][] trust, int[] out) {
        for (int i = 0; i < trust.length; i++) {
            out[trust[i][0] - 1]++;
        }
    }
}