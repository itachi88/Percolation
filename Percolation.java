/* *****************************************************************************
 *  Name: Spandan Mishra
 *  Date: 10th Mar'19
 *  Description: Percolation API
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private boolean[][] grid;
    private final WeightedQuickUnionUF qu;

    private final int virtualTop;
    private final int virtualBot;
    private int numberOfOpenSites;

    public Percolation(int n) {

        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;
        this.grid = new boolean[n][n]; // initially false i.e. no open sites

        int maxIndex = xyMapper(n, n);

        virtualTop = maxIndex + 1;
        virtualBot = maxIndex + 2;

        // System.out.println("TOP = " + virtualTop + " BOT = " + virtualBot);
        qu = new WeightedQuickUnionUF(virtualBot + 1); // as O based arr Index
    }

    private int xyMapper(int x, int y) {
        return (x - 1) * n + (y - 1);
    }

    // Since I am using 0 based index -> won't it cause any problem?
    // I think it  won't matter now as I am decrementing x and y before mapping

    public boolean isOpen(int x, int y) {
        isValidIndex(x - 1, y - 1);
        return grid[x - 1][y - 1];
    }

    // Checks if the given site is connected to Virtual TOP or not
    public boolean isFull(int x, int y) {
        isValidIndex(x - 1, y - 1);
        return qu.connected(virtualTop, xyMapper(x, y));
    }

    public void open(int x, int y) {
        isValidIndex(x - 1, y - 1);

        if (!isOpen(x, y)) numberOfOpenSites++;

        grid[x - 1][y - 1] = true;

        int currentSite = xyMapper(x, y);
        // left edge check
        if (!isLowerBoundary(y - 1)) {
            // System.out.println("if 1 " + xyMapper(x, y - 1));
            if (isOpen(x, y - 1))
                qu.union(currentSite, xyMapper(x, y - 1));
        }

        // right edge check
        if (!isUpperBoundary(y - 1)) {

            if (isOpen(x, y + 1))
                // System.out.println("if 2 " + xyMapper(x, y + 1));
                qu.union(currentSite, xyMapper(x, y + 1));
        }

        // top edge check
        if (!isLowerBoundary(x - 1)) {

            if (isOpen(x - 1, y))
                // System.out.println("if 3 " + xyMapper(x - 1, y));
                qu.union(currentSite, xyMapper(x - 1, y));
        }
        else {
            qu.union(currentSite, virtualTop);
        }

        // bottom edge check
        if (!isUpperBoundary(x - 1)) {
            if (isOpen(x + 1, y))
                // System.out.println("if 4 " + xyMapper(x + 1, y));
                qu.union(currentSite, xyMapper(x + 1, y));
        }

        else {
            qu.union(currentSite, virtualBot);
        }

    }

    public boolean percolates() {
        return qu.connected(virtualTop, virtualBot);
    }


    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    private void isValidIndex(int x, int y) {
        if (x < 0 && x >= n && y < 0 && y >= n)
            throw new IllegalArgumentException();
    }

    // boundary checks

    private boolean isLowerBoundary(int i) {
        return i == 0;
    }

    private boolean isUpperBoundary(int i) {
        return i == n - 1;
    }


    public static void main(String[] args) {
        // Percolation p = new Percolation(3);
        // System.out.println(p.isOpen(0, 0));
        // System.out.println(p.isOpen(1, 1));
        // p.open(2, 2);
        // p.open(2, 1);
        // p.open(2, 2);
        // System.out.println(p.isOpen(2, 2));
        // System.out.println(p.isOpen(2, 1));
        // System.out.println(p.isOpen(2, 3));
        // System.out.println(p.isOpen(1, 2));
        // System.out.println(p.isOpen(2, 3));
        // System.out.println(p.isOpen(3, 2));
        //
        // System.out.println("Open sites = " + p.numberOfOpenSites());
    }
}
