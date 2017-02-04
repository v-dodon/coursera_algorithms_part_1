package org.coursera.algorithms.week_2

import org.coursera.algorithms.week_2.RandomizedQueue
import spock.lang.Specification

class RandomizedQueueSpec extends Specification {
    RandomizedQueue<Integer> randomizedQueue

    def setup(){
        randomizedQueue = new RandomizedQueue<>()
    }

    def "check if randomized queue is empty"() {
        when:
        def empty = randomizedQueue.isEmpty()

        then:
        empty
        randomizedQueue.size() == 0
    }

    def "return randomized queue's size"() {
        when:
        randomizedQueue.enqueue(1)
        randomizedQueue.enqueue(2)

        then:
        randomizedQueue.size() == 2
    }

    def "remove item from deque"() {
        when:
        randomizedQueue.enqueue(5)
        Integer item = randomizedQueue.dequeue()

        then:
        item == 5
        randomizedQueue.size() == 0
        randomizedQueue.isEmpty()
    }

    def "return item from deque, don't remove it"() {
        when:
        randomizedQueue.enqueue(5)
        Integer item = randomizedQueue.sample()

        then:
        item == 5
        randomizedQueue.size() == 1
        !randomizedQueue.isEmpty()
    }

    def "iterator doesn't have elements"(){
        given:
        Iterator<Integer> iterator = randomizedQueue.iterator()

        when:
        def hasNext = iterator.hasNext()

        then:
        !hasNext
    }

    def "iterator has elements"(){
        given:
        randomizedQueue.enqueue(1)
        Iterator<Integer> iterator = randomizedQueue.iterator()

        when:
        def hasNext = iterator.hasNext()

        then:
        hasNext
    }

    def "iterates through the deque"(){
        given:
        randomizedQueue.enqueue(1)
        Iterator<Integer> iterator = randomizedQueue.iterator()

        when:
        Integer element = iterator.next()

        then:
        element == 1
        !iterator.hasNext()
    }

    def "throw NullPointerException if enqueue is called with a null argument"(){
        when:
        randomizedQueue.enqueue(null)

        then:
        thrown(NullPointerException)
    }

    def "throw NoSuchElementException if dequeue is called on an empty dequeue"(){
        when:
        randomizedQueue.dequeue()

        then:
        thrown(NoSuchElementException)
    }

    def "throw NoSuchElementException if sample is called on an empty dequeue"(){
        when:
        randomizedQueue.sample()

        then:
        thrown(NoSuchElementException)
    }

    def "throw UnsupportedOperationException if remove is called on the iterator"() {
        given:
        Iterator<Integer> iterator = randomizedQueue.iterator()

        when:
        iterator.remove()

        then:
        thrown(UnsupportedOperationException)
    }

    def "throw NoSuchElementException if next is called on an empty iterator"() {
        given:
        Iterator<Integer> iterator = randomizedQueue.iterator()

        when:
        Integer element = iterator.next()

        then:
        thrown(NoSuchElementException)
    }
}
