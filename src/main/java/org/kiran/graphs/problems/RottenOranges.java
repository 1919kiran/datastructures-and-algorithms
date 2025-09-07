// helper class to hold cell coordinates and time
class Pair {
    int row;  // row index
    int col;  // column index
    int time; // time when this orange rots
    
    Pair(int row, int col, int time) {
        this.row = row;
        this.col = col;
        this.time = time;
    }
}

class Solution {
    public int orangesRotting(int[][] grid) {
        // number of rows and cols
        int n = grid.length;
        int m = grid[0].length;
        
        // queue for BFS
        Queue<Pair> q = new ArrayDeque<>();
        
        // visited array to mark rotten oranges
        int[][] visited = new int[n][m];
        
        // count of fresh oranges
        int freshCount = 0;
        
        // initialize queue with all rotten oranges
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 2) {
                    // rotten orange at time 0
                    q.add(new Pair(i, j, 0));
                    visited[i][j] = 2;
                } else {
                    visited[i][j] = 0;
                }
                if (grid[i][j] == 1) freshCount++;
            }
        }
        
        // directions: up, right, down, left
        int[] drow = {-1, 0, 1, 0};
        int[] dcol = {0, 1, 0, -1};
        
        // keep track of time and number of fresh that became rotten
        int time = 0;
        int cnt = 0;
        
        // BFS loop
        while (!q.isEmpty()) {
            Pair p = q.poll();
            int r = p.row;
            int c = p.col;
            int t = p.time;
            time = Math.max(time, t); // track maximum time
            
            // check 4 neighbors
            for (int k = 0; k < 4; k++) {
                int nr = r + drow[k];
                int nc = c + dcol[k];
                
                // if neighbor is inside grid and fresh and not visited
                if (nr >= 0 && nr < n && nc >= 0 && nc < m 
                    && visited[nr][nc] != 2 && grid[nr][nc] == 1) {
                    
                    // mark as rotten
                    visited[nr][nc] = 2;
                    // push into queue with time + 1
                    q.add(new Pair(nr, nc, t + 1));
                    cnt++; // one more fresh orange rotted
                }
            }
        }
        
        // if all fresh became rotten, return time, else -1
        if (cnt == freshCount) return time;
        return -1;
    }
}
