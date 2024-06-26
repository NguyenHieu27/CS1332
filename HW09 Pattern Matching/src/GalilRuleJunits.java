import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GalilRuleJunits {

    private static final int TIMEOUT = 200;

    private CharacterComparator comparator;

    @Before
    public void setUp() {
        comparator = new CharacterComparator();
    }

    @Test(timeout = TIMEOUT)
    public void  galilRuleSingle() {
        /*

            pattern: happy
            text: apohaphappylok
            indices: 6
            expected total comparisons: 11

                        failure table: [0, 0, 0, 0, 0]
            comparisons: 4

        a | p | o | h | a | p | h | a | p | p | y | l | o | k |
        --+---+---+---+---+---+---+---+---+---+---+---+---+---+
        h | a | p | p | y |   |   |   |   |   |   |   |   |   |
          |   |   |   | - |   |   |   |   |   |   |   |   |   |            comparisons: 1
          |   |   |   |   | h | a | p | p | y |   |   |   |   |
          |   |   |   |   |   |   |   |   | - |   |   |   |   |            comparisons: 1
          |   |   |   |   |   | h | a | p | p | y |   |   |   |
          |   |   |   |   |   | - | - | - | - | - |   |   |   |            comparisons: 5
          |   |   |   |   |   |   |   |   |   |   | h | a | p | -


         comparisons: 11

         */

        List<Integer> answer = new ArrayList<>();
        answer.add(6);

        assertEquals(answer,
                PatternMatching.boyerMooreGalilRule("happy",
                        "apohaphappylok", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 11.", 11, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void  galilRuleMultiple() {
        /*

            pattern: burbu
            text: laburbujabuburburburbu
            indices: 2, 11, 14, 17
            expected total comparisons: 26:
                    4 from table creation and 22 from the algorithm.

            failure table: [0, 0, 0, 1, 2]
            comparisons: 4

        l | a | b | u | r | b | u | j | a | b | u | b | u | r | b | u | r | b | u | r | b | u
        --+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---
        b | u | r | b | u |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |
          |   |   |   | - |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |         comparisons: 1
          |   | b | u | r | b | u |   |   |   |   |   |   |   |   |   |   |   |   |   |   |
          |   | - | - | - | - | - |   |   |   |   |   |   |   |   |   |   |   |   |   |   |         comparisons: 5
          |   |   |   |   | b | u | r | b | u |   |   |   |   |   |   |   |   |   |   |   |
          |   |   |   |   |   |   |   |   | - |   |   |   |   |   |   |   |   |   |   |   |         comparisons: 1
          |   |   |   |   |   | b | u | r | b | u |   |   |   |   |   |   |   |   |   |   |
          |   |   |   |   |   |   |   |   | - | - |   |   |   |   |   |   |   |   |   |   |         comparisons: 3
          |   |   |   |   |   |   |   |   | b | u | r | b | u |   |   |   |   |   |   |   |
          |   |   |   |   |   |   |   |   |   |   |   |   | - |   |   |   |   |   |   |   |         comparisons: 1
          |   |   |   |   |   |   |   |   |   |   | b | u | r | b | u |   |   |   |   |   |
          |   |   |   |   |   |   |   |   |   |   | - | - | - | - | - |   |   |   |   |   |         comparisons: 5
          |   |   |   |   |   |   |   |   |   |   |   |   |   | b | u | r | b | u |   |   |
          |   |   |   |   |   |   |   |   |   |   |   |   |   | - | - | - | - | - |   |   |         comparisons: 5
          |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   | b | u | r | b | u
          |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   | - | - | - | - | -       comparisons: 5

         comparisons: 26
         */

        List<Integer> answer = new ArrayList<>();
        answer.add(2);
        answer.add(11);
        answer.add(14);
        answer.add(17);

        assertEquals(answer,
                PatternMatching.boyerMooreGalilRule("burbu",
                        "laburbujabuburburburbu", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 26.", 26, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void  galilRuleLongerPattern() {
        /*

            pattern: abcdefghijklmno
            text: abcd
            indices: -
            expected total comparisons:0

         */

        List<Integer> startInts = new ArrayList<>();
        assertEquals(startInts,
                PatternMatching.boyerMooreGalilRule("abcdefghijklmno",
                        "abcd", comparator));
        assertEquals(0, comparator.getComparisonCount());
    }
}