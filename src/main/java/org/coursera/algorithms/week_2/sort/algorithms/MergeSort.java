package org.coursera.algorithms.week_2.sort.algorithms;

import static org.coursera.algorithms.week_2.sort.algorithms.AlgorithmsUtils.less;

public class MergeSort {

    public void sort(int[] arrayToSort){
        int aux[] = new int[arrayToSort.length];
        sort(arrayToSort, aux, 0, arrayToSort.length - 1);
    }

    private void sort(int[] arrayToSort, int[] aux, int lo, int hi) {
        if(hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(arrayToSort, aux, lo, mid);
        sort(arrayToSort, aux, mid + 1, hi);
        merge(arrayToSort, aux, lo, mid, hi);
    }

    private void merge(int[] a, int[] aux, int lo, int mid, int high) {
        for (int i = lo; i <= high ; i++) {
            aux[i] = a[i];
        }
        int k = lo, j = mid + 1;

        for (int i = lo; i <= high; i++) {
            if(k > mid) a[i] = aux[j++];
            else if(j > high) a[i] = aux[k++];
            else if(less(aux[j], aux[k])) a[i] = aux[j++];
            else  a[i] = aux[k++];
        }
    }
}
