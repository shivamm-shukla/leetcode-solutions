class Solution {

    class Pair {
        int row;
        int col;

        Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    class DSU {
        Pair[][] parent;
        int[][] size;

        DSU(int m, int n) {
            parent = new Pair[m][n];
            size = new int[m][n];

            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    parent[r][c] = new Pair(r, c);
                    size[r][c] = 1;
                }
            }
        }

        Pair findParent(int r, int c) {
            if (parent[r][c].row == r &&
                parent[r][c].col == c) {
                return parent[r][c];
            }

            Pair p = parent[r][c];
            parent[r][c] = findParent(p.row, p.col);

            return parent[r][c];
        }

        void union(int r1, int c1, int r2, int c2) {
            Pair p1 = findParent(r1, c1);
            Pair p2 = findParent(r2, c2);

            if (p1.equals(p2)) {
                return;
            }

            if (size[p1.row][p1.col] < size[p2.row][p2.col]) {
                parent[p1.row][p1.col] = p2;
                size[p2.row][p2.col] += size[p1.row][p1.col];
            } else {
                parent[p2.row][p2.col] = p1;
                size[p1.row][p1.col] += size[p2.row][p2.col];
            }
        }
    }

    public int largestIsland(int[][] grid) {
        int n = grid.length;

        DSU dsu = new DSU(n, n);

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {

                if (grid[r][c] == 0) {
                    continue;
                }

                for (int k = 0; k < 4; k++) {
                    int nr = r + dr[k];
                    int nc = c + dc[k];

                    if (nr >= 0 && nr < n &&
                        nc >= 0 && nc < n &&
                        grid[nr][nc] == 1) {

                        dsu.union(r, c, nr, nc);
                    }
                }
            }
        }

        int ans = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {

                if (grid[r][c] == 1) {
                    Pair root = dsu.findParent(r, c);
                    ans = Math.max(ans, dsu.size[root.row][root.col]);
                    continue;
                }

                int currentSize = 1;

                Pair[] roots = new Pair[4];
                int count = 0;

                for (int k = 0; k < 4; k++) {
                    int nr = r + dr[k];
                    int nc = c + dc[k];

                    if (nr >= 0 && nr < n &&
                        nc >= 0 && nc < n &&
                        grid[nr][nc] == 1) {

                        Pair root = dsu.findParent(nr, nc);

                        boolean exists = false;

                        for (int i = 0; i < count; i++) {
                            if (roots[i].equals(root)) {
                                exists = true;
                                break;
                            }
                        }

                        if (!exists) {
                            roots[count++] = root;
                            currentSize += dsu.size[root.row][root.col];
                        }
                    }
                }

                ans = Math.max(ans, currentSize);
            }
        }

        return ans;
    }
}