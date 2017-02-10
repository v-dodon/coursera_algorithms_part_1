package org.coursera.algorithms.week_1

import org.coursera.algorithms.week_1.PercolationStats
import spock.lang.Specification
import spock.lang.Unroll

class PercolationStatsSpec extends Specification {
    PercolationStats percolationStats = new PercolationStats(200, 100)

    @Unroll
    def "throw IllegalArgumentException if the grid's length or the number of trials is smaller than or equal to 0"(int gridLength, int trials) {
        when:
        percolationStats = new PercolationStats(gridLength, trials)

        then:
        thrown(IllegalArgumentException)

        where:
        gridLength | trials
        0          | 0
        -1         | -1
        5          | 0
        5          | -1
        0          | 5
        -1         | 5
    }

    def "return the average value in the array."() {
        when:
        Double mean = percolationStats.mean()

        then:
        mean > 0
        mean < 1
    }

    def "return the sample standard deviation in the array."() {
        when:
        Double stdev = percolationStats.stddev()

        then:
        stdev > 0
        stdev < 1
    }

    def "returns the low endpoint of 95% confidence interval"() {
        when:
        Double low = percolationStats.confidenceLo()

        then:
        low > 0
        low < 1
    }


    def "returns the high endpoint of 95% confidence interval"() {
        when:
        Double high = percolationStats.confidenceHi()

        then:
        high > 0
        high < 1
    }
}
