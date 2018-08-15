package org.coursera.algorithms.week_1

import org.coursera.algorithms.week_1.Percolation
import spock.lang.Specification
import spock.lang.Unroll

class PercolationSpec extends Specification {

    Percolation percolation

    def setup() {
        percolation = new Percolation(5)
    }

    def "open site"() {
        when:
        percolation.open(1, 2)

        then:
        percolation.isOpen(1, 2)
        percolation.numberOfOpenSites() == 1
    }

    def "the site is not full"() {
        when:
        percolation.open(2, 2)
        percolation.open(3, 2)
        percolation.open(3, 3)


        then:
        !percolation.isFull(2, 2)
        !percolation.isFull(3, 4)
    }

    def "the site is full"() {
        when:
        percolation.open(1, 2)
        percolation.open(2, 2)
        percolation.open(2, 3)
        percolation.open(3, 3)

        then:
        percolation.isFull(3, 3)
    }

    def "return the right number of open sites"() {
        when:
        percolation.open(1, 2)
        percolation.open(2, 2)
        percolation.open(2, 3)
        percolation.open(3, 3)

        then:
        percolation.numberOfOpenSites() == 4
    }

    def "the site is open"() {
        when:
        percolation.open(1, 2)

        then:
        percolation.isOpen(1, 2)
    }

    def "the site is not open"() {
        expect:
        !percolation.isOpen(1, 2)
    }

    def "the system percolates"() {
        when:
        percolation.open(1, 2)
        percolation.open(2, 2)
        percolation.open(2, 3)
        percolation.open(3, 3)
        percolation.open(4, 3)
        percolation.open(5, 3)

        then:
        percolation.percolates()
    }

    def "the system doesn't percolate"() {
        when:
        percolation.open(1, 2)
        percolation.open(2, 2)
        percolation.open(2, 3)
        percolation.open(3, 3)
        percolation.open(4, 3)

        then:
        !percolation.percolates()
    }

    @Unroll
    def "throw IndexOutOfBoundsException when open is called with row and column index out of range"(int row, int col) {
        when:
        percolation.open(row, col)

        then:
        thrown(IndexOutOfBoundsException)

        where:
        row | col
        7   | 8
        -1  | -2
    }

    @Unroll
    def "throw IndexOutOfBoundsException when isOpen is called with row and column index out of range"(int row, int col) {
        when:
        percolation.isOpen(row, col)

        then:
        thrown(IndexOutOfBoundsException)

        where:
        row | col
        7   | 8
        -1  | -2
    }

    @Unroll
    def "throw IndexOutOfBoundsException when isFull is called with row and column index out of range"(int row, int col) {
        when:
        percolation.isFull(row, col)

        then:
        thrown(IndexOutOfBoundsException)

        where:
        row | col
        7   | 8
        -1  | -2
    }

    def "throw IllegalArgumentException when the grid's length is smaller than or equal to 0"(int length) {
        when:
        percolation = new Percolation(length)

        then:
        thrown(IllegalArgumentException)

        where:
        length << [0, -2]
    }

    def "should not open site twice"() {
        when:
        percolation.open(1, 2)
        percolation.open(1, 2)

        then:
        percolation.numberOfOpenSites() == 1
    }
}
