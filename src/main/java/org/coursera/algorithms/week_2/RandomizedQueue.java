package org.coursera.algorithms.week_2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] itemsArray;

    private int index;

    /**
     * constructs an empty randomized queue
     */
    public RandomizedQueue() {
        itemsArray = (Item[]) new Object[1];
    }

    /**
     * verifies if the queue is empty
     */
    public boolean isEmpty() {
        return index == 0;
    }

    /**
     * returns the number of items on the queue
     */
    public int size() {
        return index;
    }

    /**
     * adds an item to the randomized queue
     *
     * @param item - item to be added in the queue
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (index == itemsArray.length) {
            resize(2 * itemsArray.length);
        }
        itemsArray[index++] = item;
    }

    /**
     * resizes the inner array
     *
     * @param capacity - the new size of the array
     */
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < index; i++) {
            copy[i] = itemsArray[i];
        }
        itemsArray = copy;
    }

    /**
     * removes and returns a random item
     *
     * @return a random item
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(index);
        Item item = itemsArray[randomIndex];
        if (randomIndex != index - 1) {
            Item lastItem = itemsArray[index - 1];
            itemsArray[randomIndex] = lastItem;
            itemsArray[index - 1] = null;
        } else {
            itemsArray[randomIndex] = null;
        }
        index--;
        if (index > 0 && index == itemsArray.length / 4) {
            resize(itemsArray.length / 2);
        }
        return item;
    }

    /**
     * returns (but does not remove) a random item
     *
     * @return a random item
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(index);
        return itemsArray[randomIndex];
    }              //

    /**
     * returns an independent iterator over items in random order
     *
     * @return an iterator
     */
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item> {

        private int i = index;

        /**
         * @return true if iterator has more elements otherwise false
         */
        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * @return the next element from the iterator
         */
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (i == index) {
                StdRandom.shuffle(itemsArray, 0, i);
            }
            return itemsArray[--i];
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        System.out.println(rq.isEmpty());
        rq.enqueue(5);
        rq.enqueue(15);
        rq.enqueue(25);
        rq.enqueue(35);
        rq.enqueue(45);
        rq.enqueue(55);
        rq.enqueue(65);
        System.out.println(rq.dequeue());
    }

}
