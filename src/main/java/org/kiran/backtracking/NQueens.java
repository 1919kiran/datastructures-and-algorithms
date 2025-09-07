import java.util.*;

class NQueens {

    /**
     * Intuition: We first try to go column by column and check the possible rows where we can insert the queen
     * If we can insert the queen at a row, we insert it and backtrack
     * For safeInsertion check: since we are inserting from left to right and up to down, all these dirs will be empty: UP, DOWN, RIGHT, RIGHT-UP, RIGHT-DOWN
     */
    public List<List<String>> solveNQueensNormal(int n) {
        List<List<String>> ans = new ArrayList<>(); // this will hold all boards
        char[][] board = new char[n][n]; // 2D board
        for (int i = 0; i < n; i++) { // initialize each row
            Arrays.fill(board[i], '.'); // fill row with dots
        }
        backtrackNormal(0, n, board, ans); // start from column 0
        return ans; // return all solutions
    }

    // helper backtracking for the normal version
    private void backtrackNormal(int col, int n, char[][] board, List<List<String>> ans) {
        if (col == n) { // if we placed queens in all columns
            ans.add(buildBoard(board)); // convert board to list of strings and add
            return; // done for this path
        }
        for (int row = 0; row < n; row++) { // try every row in this column
            if (isSafeScan(row, col, board, n)) { // check safety by scanning
                board[row][col] = 'Q'; // place the queen
                backtrackNormal(col + 1, n, board, ans); // move to next column
                board[row][col] = '.'; // backtrack: remove the queen
            }
        }
    }

    // safety check for the normal version: scan left side only (row, upper-left diag, lower-left diag)
    private boolean isSafeScan(int row, int col, char[][] board, int n) {
        // check left side on the current row
        for (int c = col - 1; c >= 0; c--) { // move left along the row
            if (board[row][c] == 'Q') return false; // queen found on same row
        }
        // check upper-left diagonal
        for (int r = row - 1, c = col - 1; r >= 0 && c >= 0; r--, c--) { // move up-left
            if (board[r][c] == 'Q') return false; // queen found on upper-left diagonal
        }
        // check lower-left diagonal
        for (int r = row + 1, c = col - 1; r < n && c >= 0; r++, c--) { // move down-left
            if (board[r][c] == 'Q') return false; // queen found on lower-left diagonal
        }
        return true; // no conflict found
    }

    /**
     * Intuition: Since safety check is additionally costing O(3n), we will keep track of queens inserted in LEFT, LEFT-DOWN, LEFT-UP
     * In chess board there are 2 types of diagonals: main diagonal(RIGHT-DOWN and LEFT-UP) and anti-diagonal(LEFT-DOWN and RIGHT-UP)
     * main diagonal family cells: (0,0), (1,1), ... which have row-col = 0 -> since row-col can be negative we shift by n-1 to make: row-col + (n-1) = 0 (modular arithmetic)
     * anti diagonal family cells: (0,3), (1,2), ... which have row+col = n-1 -> since row+col is always within range [0,2n-1], we don't have to make any shifts (although we can we don't have to)
     * 
     * So hashes are:
     * LEFT:        (col)
     * LEFT-UP:     (row-col)+(n+1)
     * LEFT-DOWN:   (row+col)
     */
    public List<List<String>> solveNQueensOptimal(int n) {
        List<List<String>> ans = new ArrayList<>(); // store all boards
        char[][] board = new char[n][n]; // board grid
        for (int i = 0; i < n; i++) { // initialize board with dots
            Arrays.fill(board[i], '.'); // fill row with dots
        }
        boolean[] colUsed = new boolean[n]; // marks if a column already has a queen
        boolean[] diagUsed = new boolean[2 * n - 1]; // marks main diagonals (row - col + n - 1)
        boolean[] antiDiagUsed = new boolean[2 * n - 1]; // marks anti diagonals (row + col)
        backtrackOptimal(0, n, board, ans, colUsed, diagUsed, antiDiagUsed); // start recursion
        return ans; // return solutions
    }

    // helper backtracking for the optimal version
    private void backtrackOptimal(int col, int n, char[][] board, List<List<String>> ans,
                                  boolean[] colUsed, boolean[] diagUsed, boolean[] antiDiagUsed) {
        if (col == n) { // if all columns are filled
            ans.add(buildBoard(board)); // push current board as a solution
            return; // return up the stack
        }
        for (int row = 0; row < n; row++) { // try each row for this column
            if (isSafeFast(row, col, n, colUsed, diagUsed, antiDiagUsed)) { // O(1) safety check
                board[row][col] = 'Q'; // place queen on the board
                colUsed[col] = true; // mark this column used
                diagUsed[row - col + n - 1] = true; // mark main diagonal used
                antiDiagUsed[row + col] = true; // mark anti diagonal used

                backtrackOptimal(col + 1, n, board, ans, colUsed, diagUsed, antiDiagUsed); // recurse

                board[row][col] = '.'; // backtrack: remove queen
                colUsed[col] = false; // unmark column
                diagUsed[row - col + n - 1] = false; // unmark main diagonal
                antiDiagUsed[row + col] = false; // unmark anti diagonal
            }
        }
    }

    // safety check for the optimal version using precomputed occupancy arrays
    private boolean isSafeFast(int row, int col, int n,
                               boolean[] colUsed, boolean[] diagUsed, boolean[] antiDiagUsed) {
        int d = row - col + n - 1; // compute index for main diagonal
        int ad = row + col; // compute index for anti diagonal
        if (colUsed[col]) return false; // column already has a queen
        if (diagUsed[d]) return false; // main diagonal already taken
        if (antiDiagUsed[ad]) return false; // anti diagonal already taken
        return true; // safe to place
    }

    // small utility to convert the char board into list of strings
    private List<String> buildBoard(char[][] board) {
        List<String> out = new ArrayList<>(); // create a new list
        for (int i = 0; i < board.length; i++) { // iterate each row
            out.add(new String(board[i])); // add the row as a string
        }
        return out; // return the list
    }

