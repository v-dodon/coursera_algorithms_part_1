package week_2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;

    private int size;

    public Deque() {
    }

    /**
     * @return true if the first element from the queue is null otherwise false
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * @return the number of items on the deque
     */
    public int size() {
        return size;
    }

    /**
     * adds the item to the front
     *
     * @param item - item to be added in the queue
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (size == 0) {
            last = first;
        } else {
            oldFirst.previous = first;
        }
        size++;
    }

    /**
     * adds the item to the end
     *
     * @param item - item to be added in the queue
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
            last.previous = oldLast;
        }
        size++;
    }

    /**
     * removes and returns the item from the front
     *
     * @return the item from the front
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }

    /**
     * removes and returns the item from the end
     *
     * @return the item from the end
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        if (size > 1) {
            Node previous = last.previous;
            previous.next = null;
            last = previous;
        } else {
            first = null;
            last = null;
        }
        size--;
        return item;
    }

    /**
     * @return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        /**
         * @return true if iterator has more elements otherwise false
         */
        public boolean hasNext() {
            return current != null;
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
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    private class Node {

        private Item item;

        private Node next;

        private Node previous;
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        System.out.println("Is empty: " + deque.isEmpty());
        deque.addLast(1);
        System.out.println("Removed: " + deque.removeLast());
        System.out.println("Size: " + deque.size);
    }
}