package org.coursera.algorithms.week_2.sort.algorithms;

import edu.princeton.cs.algs4.StdRandom;

import static org.coursera.algorithms.week_2.sort.algorithms.AlgorithmsUtils.exchange;
import static org.coursera.algorithms.week_2.sort.algorithms.AlgorithmsUtils.less;

public class QuickSort {

    public void sort(int[] arrrayToSort) {
        StdRandom.shuffle(arrrayToSort);
        sort(arrrayToSort, 0, arrrayToSort.length - 1);
    }

    private void sort(int[] arrayToSort, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int j = partition(arrayToSort, lo, hi);
        sort(arrayToSort, lo, j - 1);
        sort(arrayToSort, j + 1, hi);
    }

    private int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (less(a[++i], a[lo])) {
                if (i == hi) break;
            }
            while (less(a[lo], a[--j])) {
                if (j == lo) break;
            }
            if (i >= j) {
                break;
            }
            exchange(a, i, j);
        }
        exchange(a, lo, j);
        return j;
    }
}
