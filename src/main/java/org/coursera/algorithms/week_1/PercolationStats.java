package org.coursera.algorithms.week_1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] threshold;

    private int trials;

    /**
     * performs trials independent experiments on an n-by-n grid
     *
     * @param n      - the grid's size
     * @param trials - the number of trials
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        threshold = new double[trials];
        this.trials = trials;
        for (int i = 0; i < threshold.length; i++) {
            threshold[i] = findThreshold(n);
        }
    }

    private double findThreshold(int n) {
        Percolation percolation = new Percolation(n);
        double times = 0;
        int row, col;
        while (!percolation.percolates()) {
            row = StdRandom.uniform(n) + 1;
            col = StdRandom.uniform(n) + 1;
            if (!percolation.isOpen(row, col)) {
                percolation.open(row, col);
                times++;
            }
        }
        return times / (n * n);
    }

    /**
     * sample mean of percolation threshold
     *
     * @return the average value in the array
     */
    public double mean() {
        return StdStats.mean(threshold);
    }

    /**
     * sample standard deviation of percolation threshold
     *
     * @return the standard deviation in the array.
     */
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    /**
     * @return the low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / (Math.sqrt(trials)));
    }

    /**
     * @return the high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / (Math.sqrt(trials)));
    }

    public static void main(String[] args) {
        int arraySize = Integer.parseInt(args[0]);
        int tries = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(arraySize, tries);
        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stdev = " + percolationStats.stddev());
        System.out.println("95% confidence interval = " +
                percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
    }
}