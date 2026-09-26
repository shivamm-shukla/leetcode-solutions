class Solution {

    class Pair {
        int node;
        long time;

        Pair(int node, long time) {
            this.node = node;
            this.time = time;
        }
    }

    public int countPaths(int n, int[][] roads) {

        final int MOD = 1_000_000_007;

        List<List<int[]>> adj = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int t = road[2];

            adj.get(u).add(new int[]{v, t});
            adj.get(v).add(new int[]{u, t});
        }

        long[] dist = new long[n];
        long[] ways = new long[n];

        Arrays.fill(dist, Long.MAX_VALUE);

        dist[0] = 0;
        ways[0] = 1;

        PriorityQueue<Pair> pq =
            new PriorityQueue<>((a, b) -> Long.compare(a.time, b.time));

        pq.offer(new Pair(0, 0));

        while (!pq.isEmpty()) {

            Pair curr = pq.poll();

            int u = curr.node;
            long currTime = curr.time;

            // Stale entry
            if (currTime > dist[u]) {
                continue;
            }

            for (int[] edge : adj.get(u)) {

                int v = edge[0];
                long weight = edge[1];

                long newTime = currTime + weight;

                // Found a shorter path
                if (newTime < dist[v]) {

                    dist[v] = newTime;
                    ways[v] = ways[u];

                    pq.offer(new Pair(v, newTime));
                }

                // Found another shortest path
                else if (newTime == dist[v]) {

                    ways[v] = (ways[v] + ways[u]) % MOD;
                }
            }
        }

        return (int) ways[n - 1];
    }
}