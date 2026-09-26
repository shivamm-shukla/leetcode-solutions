class Solution {

    class Pair{
        int city;
        int price;
        int stops;

        Pair(int city, int price, int stops){
            this.city = city;
            this.price = price;
            this.stops = stops;
        }
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        if (src == dst) return 0;
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++){
            adj.add(new ArrayList<>());
        }

        for (int[] flight : flights){
            int from = flight[0];
            int to = flight[1];
            int price = flight[2];
            adj.get(from).add(new int[]{to, price});
        }

        int[] price = new int[n];
        Arrays.fill(price, Integer.MAX_VALUE);
        price[src] = 0;

       Queue<Pair> q = new ArrayDeque<>();
        q.add(new Pair(src, 0, 0));

        while (!q.isEmpty()){
            Pair curr = q.poll();
            int currCity = curr.city;
            int currPrice = curr.price;
            int currStops = curr.stops;

            for (int[] flight : adj.get(currCity)){
                int nextCity = flight[0];
                int nextPrice = flight[1];

                if (currPrice + nextPrice < price[nextCity] && currStops <= k){
                    price[nextCity] = currPrice + nextPrice;
                    q.add(new Pair(nextCity, price[nextCity], currStops + 1));
                }
            }
        }

        if (price[dst] != Integer.MAX_VALUE) return price[dst];
        return -1;
    }
}