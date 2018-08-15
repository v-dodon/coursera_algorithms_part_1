package org.coursera.algorithms.week_2.sort.algorithms;

import static org.coursera.algorithms.week_2.sort.algorithms.AlgorithmsUtils.exchange;
import static org.coursera.algorithms.week_2.sort.algorithms.AlgorithmsUtils.less;

public class SelectionSort {

    public void sortArray(int[] arrayToSort) {
        int N = arrayToSort.length;
        for (int i = 0; i < N ; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) {
                if(less(arrayToSort[j], arrayToSort[min])) {
                    min = j;
                }
            }
            exchange(arrayToSort, i, min);
        }
    }
}
