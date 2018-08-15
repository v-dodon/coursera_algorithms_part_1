package org.coursera.algorithms.week_2.sort.algorithms;

import static org.coursera.algorithms.week_2.sort.algorithms.AlgorithmsUtils.exchange;
import static org.coursera.algorithms.week_2.sort.algorithms.AlgorithmsUtils.less;

public class InsertionSort {

    public void sortArray(int[] arrayToSort) {
        int N = arrayToSort.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0; j--) {
                if(less(arrayToSort[j], arrayToSort[j -1])) {
                    exchange(arrayToSort, j, j-1);
                }
                else break;
            }
        }
    }
}
