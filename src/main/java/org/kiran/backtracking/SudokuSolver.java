import java.util.*;

class SudokuSolver {

    /**
     * Intuition: Iterate over the matrix to find an empty cell. Once you find an empty cell, check if all digits 1-9 can be inserted here recursively.
     */
    public void solveSudoku(char[][] board) {
        solve(board); // call the recursive solver
    }

    private boolean solve(char[][] board) {
        // iterate over all cells
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // if the cell is empty
                if (board[i][j] == '.') {
                    // try all digits '1' to '9'
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, i, j, c)) { // check if we can place c here
                            board[i][j] = c; // place it
                            if (solve(board)) return true; // if it leads to solution, return
                            else board[i][j] = '.'; // else backtrack
                        }
                    }
                    // if no digit can be placed, this path fails
                    return false;
                }
            }
        }
        // if no empty cell left, the board is solved
        return true;
    }

    private boolean isValid(char[][] board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            // check the column
            if (board[i][col] == c) return false;
            // check the row
            if (board[row][i] == c) return false;
            // check the 3x3 subgrid
            int subRow = 3 * (row / 3) + i / 3;
            int subCol = 3 * (col / 3) + i % 3;
            if (board[subRow][subCol] == c) return false;
        }
        return true;
    }
}
