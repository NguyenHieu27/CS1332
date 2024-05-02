import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Comprehensive Tests for HWK 3
 * 
 * Based on my homework 2 tests
 * 
 * @author Ashwin Mudaliar
 * @version 1.0.0
 */

public class LinkedDequeComprehensiveTest {

    private static final int TIMEOUT = 500;
    private LinkedDeque<String> list;
    private static String str = "I LOVE CS1332!!!";

    private static final int NUMELEMENTS = 10;

    @Before
    public void setUp() {

        list = new LinkedDeque<>();

    }

    /**
     * Helper method for tests to populate list
     * 
     * @param list        list to add to
     * @param numElements number of elements desired
     */
    private static void populateList(LinkedDeque<String> list, int numElements) {

        for (int k = 0; k < numElements; k++) {

            list.addLast(String.valueOf(k));

        }

    }

    // Test exceptions: ** Note a complex description has more than 3 spaces

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void addFirstDataIsNull() {

        list.addFirst(null);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void addLastDataIsNull() {

        list.addLast(null);

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void removeFirstDequeIsEmpty() {
        
        list.removeFirst();

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void removeLastDuqueIsEmpty() {

        list.removeLast();

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void getFirstDequeIsEmpty() {

        list.getFirst();

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void getLastDequeIsEmpty() {

        list.getLast();

    }

    @Test(timeout = TIMEOUT)
    public void addFirstSizeZero() {

        list.addFirst(str);

        assertEquals(list.getHead(), list.getTail());

    }

    @Test(timeout = TIMEOUT)
    public void addFirstSizeOneAtFront() {

        list.addFirst("1");
        list.addFirst("0");

        assertNotEquals(list.getHead(), list.getTail());
        assertEquals(list.getHead().getData(), "0");
        assertEquals(list.getTail().getData(), "1");

    }

    @Test(timeout = TIMEOUT)
    public void addFirstTest() {

        for (int k = NUMELEMENTS - 1; k >= 0; k--) {

            list.addFirst(String.valueOf(k));

        }

        assertEquals(NUMELEMENTS, list.size());

        LinkedNode<String> curr = list.getHead();
        LinkedNode<String> prev = curr.getPrevious();
        LinkedNode<String> next = curr.getNext();

        for (int k = 0; k < NUMELEMENTS; k++) {

            System.out.println("Testing Index: " + k + " and Current Node: " + curr.getData());

            assertEquals(String.valueOf(k), curr.getData());

            if (k == 0) {

                assertEquals(null, curr.getPrevious());
                assertEquals("1", curr.getNext().getData());

            } else if (k == NUMELEMENTS - 1) {

                assertEquals(String.valueOf(k - 1), curr.getPrevious().getData());
                assertEquals(null, curr.getNext());

            } else {

                assertEquals(String.valueOf(k - 1), curr.getPrevious().getData());
                assertEquals(String.valueOf(k + 1), curr.getNext().getData());

            }

            curr = curr.getNext();

        }

    }

    @Test(timeout = TIMEOUT)
    public void addToBackSizeZero() {

        list.addLast(str);

        assertEquals(list.getHead(), list.getTail());

    }

    @Test(timeout = TIMEOUT)
    public void addToBackSizeOneAtFront() {

        list.addLast("0");
        list.addLast("1");

        assertNotEquals(list.getHead(), list.getTail());
        assertEquals(list.getHead().getData(), "0");
        assertEquals(list.getTail().getData(), "1");

    }

    @Test(timeout = TIMEOUT)
    public void addToBackTest() {

        for (int k = 0; k < NUMELEMENTS; k++) {

            list.addLast(String.valueOf(k));

        }

        assertEquals(NUMELEMENTS, list.size());

        LinkedNode<String> curr = list.getHead();
        LinkedNode<String> prev = curr.getPrevious();
        LinkedNode<String> next = curr.getNext();

        for (int k = 0; k < NUMELEMENTS; k++) {

            System.out.println("Testing Index: " + k + " and Current Node: " + curr.getData());

            assertEquals(String.valueOf(k), curr.getData());

            if (k == 0) {

                assertEquals(null, curr.getPrevious());
                assertEquals("1", curr.getNext().getData());

            } else if (k == NUMELEMENTS - 1) {

                assertEquals(String.valueOf(k - 1), curr.getPrevious().getData());
                assertEquals(null, curr.getNext());

            } else {

                assertEquals(String.valueOf(k - 1), curr.getPrevious().getData());
                assertEquals(String.valueOf(k + 1), curr.getNext().getData());

            }

            curr = curr.getNext();

        }

    }

    @Test(timeout = TIMEOUT)
    public void removeFromFrontTest() {

        populateList(list, NUMELEMENTS);

        LinkedNode<String> curr = list.getHead();

        for (int k = 0; k < NUMELEMENTS; k++) {

            String s = list.removeFirst();

            assertEquals(s, String.valueOf(k));
            assertEquals(list.size(), NUMELEMENTS - (k + 1));

            curr = curr.getNext();

        }

        assertEquals(0, list.size());

    }

    @Test(timeout = TIMEOUT)
    public void removeFrontFrontSizeOne() {

        list.addFirst(str);
        list.removeFirst();

        assertEquals(null, list.getHead());
        assertEquals(null, list.getTail());
        assertEquals(0, list.size());

    }

    @Test(timeout = TIMEOUT)
    public void removeFromFrontHeadReset() {

        populateList(list, NUMELEMENTS);
        list.addFirst("I Hate my Veggies!");
        list.addFirst(str);
        list.addFirst("I Love Cake!");

        list.removeFirst();

        assertEquals(list.getHead().getData(), str);
        assertEquals(list.getHead().getNext().getData(), "I Hate my Veggies!");
        assertEquals(list.getHead().getPrevious(), null);

    }

    @Test(timeout = TIMEOUT)
    public void removeFromBackTest() {

        populateList(list, NUMELEMENTS);

        LinkedNode<String> curr = list.getTail();

        for (int k = NUMELEMENTS - 1; k >= 0; k--) {

            String s = list.removeLast();

            assertEquals(s, String.valueOf(k));
            assertEquals(list.size(), k);

            curr = curr.getPrevious();

        }

        assertEquals(0, list.size());

    }

    @Test(timeout = TIMEOUT)
    public void removeFromBackTestTailReset() {

        populateList(list, NUMELEMENTS);
        list.addLast("I Hate my Veggies!");
        list.addLast(str);
        list.addLast("I Love Cake!");

        list.removeLast();

        assertEquals(list.getTail().getData(), str);
        assertEquals(list.getTail().getPrevious().getData(), "I Hate my Veggies!");
        assertEquals(list.getTail().getNext(), null);

    }

    @Test(timeout = TIMEOUT)
    public void removeFromBackSizeOne() {

        list.addFirst(str);
        list.removeLast();

        assertEquals(null, list.getHead());
        assertEquals(null, list.getTail());
        assertEquals(0, list.size());

    }

    @Test(timeout = TIMEOUT)
    public void testGetFirst() {

        list.addFirst(str);
        list.addFirst("I Love CS1332");
        list.addFirst("Shameless Insta Plug: @ashwinnmudaliar");
        list.addFirst(str);

        assertEquals(list.getFirst(), str);

    }

    @Test(timeout = TIMEOUT)
    public void testGetLast() {

        list.addFirst(str);
        list.addFirst("I Love CS1332");
        list.addFirst("Shameless Insta Plug: @ashwinnmudaliar");
        list.addFirst(str);

        assertEquals(list.getLast(), str);

    }

    /**
     * helper method to print for debugging
     * 
     * @param list list to print
     */
    private void listToString(LinkedDeque<String> list) {

        LinkedNode<String> current = list.getHead();

        for (int k = 0; k < list.size(); k++) {

            System.out.println("current: " + current.getData());

            current = current.getNext();

        }

    }

}
