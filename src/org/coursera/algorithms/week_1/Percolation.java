package org.coursera.algorithms.week_1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] gridSites;

    private int top;

    private int bottom;

    private int openSites;

    private WeightedQuickUnionUF weightedQuickUnionUF;

    private WeightedQuickUnionUF onlyTopWeightedQuickUnionUF;

    /**
     * creates n-by-n grid, with all sites blocked
     * @param n - size of the grid
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        gridSites = new int[n][n];
        int id = 0;
        for (int i = 0; i < gridSites.length; i++) {
            for (int j = 0; j < gridSites[i].length; j++) {
                gridSites[i][j] = 1;
                id++;
            }
        }
        top = n * n + 1;
        bottom = n * n + 2;
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 3);
        onlyTopWeightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 3);
    }

    /**
     * opens site (row, col) if it is not open already
     * @param row - the row from the grid
     * @param col - the column from the grid
     */
    public void open(int row, int col) {
        if (isOutsideRange(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        if (isOpen(row, col)) {
            return;
        }
        openSites++;
        gridSites[row - 1][col - 1] = 0;
        unionNeighbour(row, col);
        if (isTheTopSite(row)) {
            onlyTopWeightedQuickUnionUF.union(top, findIndex(row, col));
            weightedQuickUnionUF.union(top, findIndex(row, col));
        }
        if (isTheBottomSite(row)) {
            weightedQuickUnionUF.union(bottom, findIndex(row, col));
        }

    }

    /**
     * unions the neighbour sites
     * @param row - the row from the grid
     * @param col - the column from the grid
     */
    private void unionNeighbour(int row, int col) {
        if (col > 1 && isOpen(row, col - 1)) {
            weightedQuickUnionUF.union(findIndex(row, col), findIndex(row, col - 1));
            onlyTopWeightedQuickUnionUF.union(findIndex(row, col), findIndex(row, col - 1));
        }
        if (col < gridSites.length && isOpen(row, col + 1)) {
            weightedQuickUnionUF.union(findIndex(row, col), findIndex(row, col + 1));
            onlyTopWeightedQuickUnionUF.union(findIndex(row, col), findIndex(row, col + 1));
        }
        if (!isTheTopSite(row) && isOpen(row - 1, col)) {
            weightedQuickUnionUF.union(findIndex(row, col), findIndex(row - 1, col));
            onlyTopWeightedQuickUnionUF.union(findIndex(row, col), findIndex(row - 1, col));
        }
        if (!isTheBottomSite(row) && isOpen(row + 1, col)) {
            weightedQuickUnionUF.union(findIndex(row, col), findIndex(row + 1, col));
            onlyTopWeightedQuickUnionUF.union(findIndex(row, col), findIndex(row + 1, col));
        }
    }

    /**
     * verifies if the row is the at the bottom
     * @param row - the row from the grid
     * @return true if it is the bottom row otherwise false
     */
    private boolean isTheBottomSite(int row) {
        return row == gridSites.length;
    }

    /**
     * verifies if the row is the at the top
     * @param row - the row from the grid
     * @return true if it is the top row otherwise false
     */
    private boolean isTheTopSite(int row) {
        return row == 1;
    }

    private boolean isOutsideRange(int row, int col) {
        return row > gridSites.length || row < 1 || col > gridSites.length || col < 1;
    }

    /**
     * verifies if the site is open
     * @param row - the row from the grid
     * @param col - the column from the grid
     * @return true if the site is open otherwise false
     */
    public boolean isOpen(int row, int col) {
        if (isOutsideRange(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return gridSites[row - 1][col - 1] == 0;
    }

    /**
     * finds the element's index from the grid
     * @param row - the row from the grid
     * @param col - the column from the grid
     * @return the element's index
     */
    private int findIndex(int row, int col) {
        return gridSites.length * row - (gridSites.length - col);
    }

    /**
     * verifies if the site (row, col) is full
     * @param row - the row from the grid
     * @param col - the column from the grid
     * @return true if the site is open and is connected with the top
     */
    public boolean isFull(int row, int col) {
        if (isOutsideRange(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return isOpen(row, col) && onlyTopWeightedQuickUnionUF.connected(top, findIndex(row, col));
    }

    /**
     * @return the number of the open sites
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     *
     * @return true if the top and the bottom are connected otherwise false
     */
    public boolean percolates() {
        return weightedQuickUnionUF.connected(top, bottom);
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(2, 2);
        percolation.open(3, 4);
        percolation.open(3, 5);
        percolation.open(3, 2);
        percolation.open(4, 2);
        percolation.open(5, 2);
        for (int i = 0; i < percolation.gridSites.length; i++) {
            for (int j = 0; j < percolation.gridSites.length; j++) {
                System.out.print(percolation.gridSites[i][j] + "    ");
            }
            System.out.println();
        }
        System.out.println(percolation.isFull(3, 4));
        System.out.println(percolation.percolates());
        System.out.println(percolation.numberOfOpenSites());
    }
}
