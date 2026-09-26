class Solution {

    static class Pair {
        int node;
        int time;

        Pair(int node, int time) {
            this.node = node;
            this.time = time;
        }
    }

    public int networkDelayTime(int[][] times, int n, int k) {

        List<List<Pair>> adj = new ArrayList<>();

        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] time : times) {
            int from = time[0];
            int to = time[1];
            int weight = time[2];

            adj.get(from).add(new Pair(to, weight));
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[k] = 0;

        PriorityQueue<Pair> pq =
            new PriorityQueue<>((a, b) -> Integer.compare(a.time, b.time));

        pq.add(new Pair(k, 0));

        while (!pq.isEmpty()) {

            Pair curr = pq.poll();

            int node = curr.node;
            int currTime = curr.time;

            if (currTime > dist[node]) {
                continue;
            }

            for (Pair next : adj.get(node)) {

                int nextNode = next.node;
                int nextTime = currTime + next.time;

                if (nextTime < dist[nextNode]) {
                    dist[nextNode] = nextTime;
                    pq.add(new Pair(nextNode, nextTime));
                }
            }
        }

        // maximum shortest distance
        int answer = 0;

        for (int i = 1; i <= n; i++) {

            if (dist[i] == Integer.MAX_VALUE) {
                return -1;
            }

            answer = Math.max(answer, dist[i]);
        }

        return answer;
    }
}