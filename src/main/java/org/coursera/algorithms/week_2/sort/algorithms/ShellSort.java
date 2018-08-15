package org.coursera.algorithms.week_2.sort.algorithms;

import static org.coursera.algorithms.week_2.sort.algorithms.AlgorithmsUtils.exchange;
import static org.coursera.algorithms.week_2.sort.algorithms.AlgorithmsUtils.less;

public class ShellSort {

    public void sortArray(int[] arrayToSort) {
        int N = arrayToSort.length;

        int h = 1;

        while (h < N/3) h = 3 * h + 1;

        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h ; j -= h) {
                    if(less(arrayToSort[j], arrayToSort[j-h])){
                        exchange(arrayToSort, j, j-h);
                    }
                }
            }
            h = h/3;
        }
    }
}
