import java.util.*;

/**
 * Intuition: The key observation is I can fix one point as an anchor and count how many points share the same slope with it.
 * All points that form the same slope with the anchor lie on the same line through that anchor.
 * Another tricky part is encoding the slope since decimals cannot be used as map keys reliably
 * We can do the encoding by getting GCD of dy/dx
 */
class MaxPointsOnALine {
    // I return the maximum number of collinear points
    public int maxPoints(int[][] points) {
        // I handle tiny inputs directly
        int n = points.length;
        if (n <= 2) return n;

        // I track the global best across all anchors
        int best = 0;

        // I iterate each point as an anchor
        for (int i = 0; i < n; i++) {
            // I map reduced slope "dy/dx" to how many points share it with anchor
            Map<String, Integer> freq = new HashMap<>();
            // I count exact duplicates of the anchor
            int dup = 0;
            // I track best slope count for this anchor
            int localMax = 0;

            // I pair anchor with later points to avoid double work
            for (int j = i + 1; j < n; j++) {
                // I compute deltas from anchor to the other point
                int dy = points[j][1] - points[i][1];
                int dx = points[j][0] - points[i][0];

                // I detect exact duplicate point
                if (dy == 0 && dx == 0) {
                    // I bump duplicates to add later
                    dup++;
                    // I continue since duplicates don't define a slope
                    continue;
                }

                // I reduce the slope (dy, dx) to a canonical pair
                int g = gcd(dy, dx);
                // I divide both by gcd to make them coprime
                dy /= g; dx /= g;

                // I normalize sign so dx stays non-negative
                if (dx < 0) {
                    // I flip both to keep the same ratio with positive dx
                    dy = -dy; dx = -dx;
                }

                // I fix vertical lines to a single representation (1, 0)
                if (dx == 0) {
                    // I force dy to be 1 to merge all verticals
                    dy = 1;
                }

                // I fix horizontal lines to a single representation (0, 1)
                if (dy == 0) {
                    // I force dx to be 1 to merge all horizontals
                    dx = 1;
                }

                // I build a compact key for the reduced slope
                String key = dy + "/" + dx;

                // I increase the count of this slope with the anchor
                int cnt = freq.getOrDefault(key, 0) + 1;
                freq.put(key, cnt);

                // I keep track of the maximum slope count for this anchor
                if (cnt > localMax) localMax = cnt;
            }

            // I combine slope count, duplicates, and the anchor itself
            int total = localMax + dup + 1;

            // I update the global best
            if (total > best) best = total;

            // I can early exit if best already equals the remaining upper bound
            if (best >= n - i || best > n / 2) {
                // I break since no future anchor can beat this bound
                // (optional micro-optimization)
            }
        }

        // I return the final maximum
        return best;
    }

    // I compute gcd on ints while handling negatives
    private int gcd(int a, int b) {
        // I take absolute values to avoid sign issues
        a = Math.abs(a); b = Math.abs(b);
        // I run iterative Euclid algorithm
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        // I return non-negative gcd
        return a == 0 ? 1 : a;
    }
}
