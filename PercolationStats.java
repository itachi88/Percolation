/* *****************************************************************************
 *  Name: Spandan Mishra
 *  Date: 11th Mar'19
 *  Description: Calculate percolation threshold
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int t;
    // private int n;
    private final double[] percThresholds;


    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0)
            throw new IllegalArgumentException("Invalid n or t");

        this.t = t;
        // this.n = n;

        percThresholds = new double[t];

        for (int i = 0; i < t; ++i) {
            Percolation p = new Percolation(n);
            int openSites = 0; // open sites when system percolates

            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    openSites++;
                }
            }
            double gridSize = n * n;
            percThresholds[i] = openSites / gridSize;
        }
    }

    public double mean() {
        return StdStats.mean(percThresholds);
    }

    public double stddev() {
        return StdStats.stddev(percThresholds);
    }

    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * Math.sqrt(stddev())) / Math.sqrt(t));
    }

    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * Math.sqrt(stddev())) / Math.sqrt(t));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats pst = new PercolationStats(n, t);

        System.out.println("mean = " + pst.mean());
        System.out.println("stdev = " + pst.stddev());
        System.out.println("hi = " + pst.confidenceHi() + " lo = " + pst.confidenceLo());
    }
}
