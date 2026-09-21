class Solution {
    class Pair{
        int row; 
        int col;

        Pair(int row, int col){
            this.row = row;
            this.col = col;
        }
    }

    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int m = image.length;
        int n = image[0].length;

        int initialColor = image[sr][sc];
        if (initialColor == color) return image;

        Queue<Pair> queue = new ArrayDeque<>();

        int[] drow = {-1, 0, +1, 0};
        int[] dcol = {0, +1, 0, -1};

        queue.add(new Pair(sr, sc));
        image[sr][sc] = color;

        while(!queue.isEmpty()){
            int r = queue.peek().row;
            int c = queue.peek().col;
            queue.remove();

            for (int i = 0; i < 4; i++){
                int nrow = r + drow[i];
                int ncol = c + dcol[i];

                if(nrow >= 0 && nrow < m && ncol >= 0 && ncol < n 
                   && image[nrow][ncol] == initialColor){
                    
                    queue.add(new Pair(nrow, ncol));
                    image[nrow][ncol] = color;
                }
            }
        }
        return image;
    }
}