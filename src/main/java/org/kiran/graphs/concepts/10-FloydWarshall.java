// import utilities for Arrays if needed
import java.util.*; // not strictly necessary but handy to have

/**
 * Intuition:
 * For an path i->j there can be an intermediate node k such that the path goes from i->k and k->j
 * So try all possible nodes as k for all node pairs (i,j)
 */
class Solution {

    // public method: run Floyd–Warshall in-place on the given adjacency matrix
    public void floydWarshallInPlace(int[][] mat) {
        int n = mat.length;                       // get the dimension of the matrix
        final int INF = (int) 1e9;                // choose a large sentinel for "no path"

        // normalize: convert -1 to INF and force zeros on the diagonal
        for (int i = 0; i < n; i++) {             // loop over all rows
            for (int j = 0; j < n; j++) {         // loop over all columns
                if (i == j) {                     // if we are on the diagonal
                    mat[i][j] = 0;                // distance to self is zero
                } else if (mat[i][j] == -1) {     // if there is no direct edge
                    mat[i][j] = INF;              // set to INF so it behaves in min()
                } else {                          // otherwise there is an edge weight
                    // leave mat[i][j] as is; it’s already the direct edge cost
                }
            }
        }

        // core Floyd–Warshall triple loop
        for (int k = 0; k < n; k++) {             // consider each node as an intermediate
            for (int i = 0; i < n; i++) {         // iterate over all start nodes
                if (mat[i][k] == INF) continue;   // if i->k is impossible, skip j loop early
                for (int j = 0; j < n; j++) {     // iterate over all end nodes
                    if (mat[k][j] == INF) continue; // if k->j is impossible, skip
                    long cand = (long) mat[i][k] + (long) mat[k][j]; // compute i->k->j safely in long
                    if (cand < mat[i][j]) {       // if the candidate is better than current
                        mat[i][j] = (int) cand;   // update in-place with the improved distance
                    }
                }
            }
        }

        // optional: detect negative cycles by inspecting the diagonal
        // if any mat[i][i] < 0 now, that indicates a negative cycle reachable from i
        // we won’t alter outputs here, but you could handle/report it if required

        // denormalize: convert INF back to -1 for unreachable pairs
        for (int i = 0; i < n; i++) {             // loop rows again
            for (int j = 0; j < n; j++) {         // loop columns again
                if (mat[i][j] >= INF) {           // if still at our sentinel
                    mat[i][j] = -1;               // mark as unreachable in final output
                }
            }
        }
    }
}
