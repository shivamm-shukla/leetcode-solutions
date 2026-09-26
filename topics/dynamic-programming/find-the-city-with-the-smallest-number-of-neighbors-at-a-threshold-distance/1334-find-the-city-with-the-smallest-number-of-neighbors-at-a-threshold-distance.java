class Solution {

    static class Pair{
        int node;
        int dist; 

        Pair(int node, int dist){
            this.node = node;
            this.dist = dist;
        }
    }


    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++){
            adj.add(new ArrayList<>());
        }

        for (int[] edge : edges){
            int from = edge[0];
            int to = edge[1];
            int wt = edge[2];
            
            adj.get(from).add(new Pair(to, wt));
            adj.get(to).add(new Pair(from, wt));
        }

        int[][] distance = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(distance[i], Integer.MAX_VALUE);
        }

        for (int node = 0; node < n; node++){
            dijkstra(adj, distance, node);
        }
        int min = Integer.MAX_VALUE;
        int minNode = -1;
        for (int i = 0; i < n; i++){
            int count = 0;
            for (int j = 0; j < n; j++){
                if (distance[i][j] <= distanceThreshold){
                    count++;
                }
            }

            if (count <= min){
                minNode = i;
                min = count;
            }
        }

        return minNode;

    }

    static void dijkstra(List<List<Pair>> adj, int[][] dist, int node){
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.dist, b.dist));
        dist[node][node] = 0;
        pq.add(new Pair(node, 0));

        while (!pq.isEmpty()){
            Pair curr = pq.poll();
            int currNode = curr.node;
            int currDist = curr.dist;

            if (currDist > dist[node][currNode]) continue;

            for (Pair p : adj.get(currNode)){
                int newNode = p.node;
                int newDist = p.dist;

                int nextDist = currDist + newDist;
                if (nextDist < dist[node][newNode]){
                    dist[node][newNode] = nextDist;
                    pq.add(new Pair(newNode, nextDist));
                }
            }
        }
    }
}