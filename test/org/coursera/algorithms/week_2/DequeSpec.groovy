package org.coursera.algorithms.week_2

import org.coursera.algorithms.week_2.Deque
import spock.lang.Specification

class DequeSpec extends Specification {
    Deque<Integer> deque

   def setup(){
        deque = new Deque<>()
    }

    def "size is 0 if the deque is empty"() {
        when:
        Boolean isEmpty = deque.isEmpty()

        then:
        isEmpty
        deque.size() == 0
    }

    def "the deque is not empty"() {
        when:
        deque.addFirst(1)
        Boolean isEmpty = deque.isEmpty()

        then:
        !isEmpty
    }

    def "return the right size"() {
        when:
        deque.addFirst(1)
        deque.addLast(2)

        then:
        deque.size() == 2
    }

    def "add element on the first position"() {
        when:
        deque.addFirst(2)
        deque.addFirst(1)

        then:
        Integer first = deque.removeFirst()
        first == 1
    }

    def "add element on the last position"() {
        when:
        deque.addLast(2)
        deque.addLast(1)

        then:
        Integer last = deque.removeLast()
        last == 1
    }

    def "remove first element"(){
        when:
        deque.addFirst(1)
        deque.addFirst(2)
        def first = deque.removeFirst()

        then:
        deque.size() == 1
        first == 2
    }

    def "remove last element"(){
        when:
        deque.addFirst(1)
        deque.addLast(2)
        def first = deque.removeLast()

        then:
        deque.size() == 1
        first == 2
    }

    def "throw NullPointerException if addFirst is called with null argument"() {
        when:
        deque.addFirst(null)

        then:
        thrown(NullPointerException)
    }

    def "throw NullPointerException if addLast is called with null argument"() {
        when:
        deque.addLast(null)

        then:
        thrown(NullPointerException)
    }

    def "throw NoSuchElementException if removeFirst is called on an empty deque"(){
        when:
        deque.removeFirst()

        then:
        thrown(NoSuchElementException)
    }

    def "throw NoSuchElementException if removeLast is called on an empty deque"(){
        when:
        deque.removeLast()

        then:
        thrown(NoSuchElementException)
    }

    def "throw UnsupportedOperationException if remove is clled on the iterator"(){
        given:
        Iterator<Integer> iterator = deque.iterator()

        when:
        iterator.remove()

        then:
        thrown(UnsupportedOperationException)
    }

    def "throw NoSuchElementException if next is called on an empty iterator"() {
        given:
        Iterator<Integer> iterator = deque.iterator()

        when:
        iterator.next()

        then:
        !iterator.hasNext()
        thrown(NoSuchElementException)
    }

    def "iterator has elements"() {
        given:
        deque.addFirst(1)
        Iterator<Integer> iterator = deque.iterator()

        when:
        Boolean hasNext = iterator.hasNext()

        then:
        hasNext
    }

    def "iterates through the items"() {
        given:
        deque.addFirst(1)
        deque.addLast(2)
        Iterator<Integer> iterator = deque.iterator()

        when:
        Boolean hasNext = iterator.hasNext()

        then:
        iterator.next() == 1
        iterator.next() == 2
    }
}
