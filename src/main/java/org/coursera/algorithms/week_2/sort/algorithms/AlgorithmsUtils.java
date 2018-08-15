package org.coursera.algorithms.week_2.sort.algorithms;

public class AlgorithmsUtils {

    public static boolean less(int currentElement, int minElement) {
        return currentElement < minElement;
    }

    public static void exchange(int[] arrayToSort, int currentIndex, int indexToChange) {
        int currentElement = arrayToSort[currentIndex];
        arrayToSort[currentIndex] = arrayToSort[indexToChange];
        arrayToSort[indexToChange] = currentElement;
    }
}
