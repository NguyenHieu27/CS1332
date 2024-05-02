import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author Yirou (Cynthia) Wang
 * @version 1.1
 * based on:
 * @author CS 1332 TAs
 * version 1.0
 */
public class ArrayListJUnitTest {

    private static final int TIMEOUT = 200;
    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, list.size());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY],
                list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndex() {
        list.addAtIndex(0, "2a");   // 2a
        list.addAtIndex(0, "1a");   // 1a, 2a
        list.addAtIndex(2, "4a");   // 1a, 2a, 4a
        list.addAtIndex(2, "3a");   // 1a, 2a, 3a, 4a
        list.addAtIndex(0, "0a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testNoChangeInCapacityConstant() {
        assertSame(9, ArrayList.INITIAL_CAPACITY);

        list.addAtIndex(0, "NoChangeInCapacityConstant");
        assertSame(9, ArrayList.INITIAL_CAPACITY);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddNull() {
        list.addAtIndex(0, null);
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFront() {
        list.addToFront("4a");  // 4a
        list.addToFront("3a");  // 3a, 4a
        list.addToFront("2a");  // 2a, 3a, 4a
        list.addToFront("1a");  // 1a, 2a, 3a, 4a
        list.addToFront("0a");  // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBack() {
        list.addToBack("0a");   // 0a
        list.addToBack("1a");   // 0a, 1a
        list.addToBack("2a");   // 0a, 1a, 2a
        list.addToBack("3a");   // 0a, 1a, 2a, 3a
        list.addToBack("4a");   // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFrontShift() {
        String temp = new String("1a");
        list.addToFront(temp);
        list.addToFront("0a");
        assertSame(temp, list.get(1));
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndexShift() {
        String temp = new String("1a");
        list.addAtIndex(0, temp);
        list.addAtIndex(0, "0a");
        assertSame(temp, list.get(1));
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFrontResize() {
        for (int i = ArrayList.INITIAL_CAPACITY; i > 0; i--) {
            list.addToFront(String.valueOf(i) + "b");
        }

        Object[] expectedBeforeResize = new Object[ArrayList.INITIAL_CAPACITY];
        utilFillExpectedWithValues(expectedBeforeResize, 1, 9, "b");
        assertArrayEquals(expectedBeforeResize, list.getBackingArray());

        list.addToFront("0b");

        Object[] expectedAfterResize = new Object[ArrayList.INITIAL_CAPACITY * 2];
        utilFillExpectedWithValues(expectedAfterResize, 0, 9, "b");
        assertArrayEquals(expectedAfterResize, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBackResize() {
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            list.addToBack(String.valueOf(i) + "b");
        }

        Object[] expectedBeforeResize = new Object[ArrayList.INITIAL_CAPACITY];
        utilFillExpectedWithValues(expectedBeforeResize, 0, 8, "b");
        assertArrayEquals(expectedBeforeResize, list.getBackingArray());

        list.addToBack("9b");

        Object[] expectedAfterResize = new Object[ArrayList.INITIAL_CAPACITY * 2];
        utilFillExpectedWithValues(expectedAfterResize, 0, 9, "b");
        assertArrayEquals(expectedAfterResize, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndexResize() {
        list.addAtIndex(0, "0b");
        list.addAtIndex(1, "8b");
        for (int i = ArrayList.INITIAL_CAPACITY - 2; i > 0; i--) {
            list.addAtIndex(1, String.valueOf(i) + "b");
        }

        Object[] expectedBeforeResize = new Object[ArrayList.INITIAL_CAPACITY];
        utilFillExpectedWithValues(expectedBeforeResize, 0, 8, "b");
        assertArrayEquals(expectedBeforeResize, list.getBackingArray());

        list.addAtIndex(9, "9b");

        Object[] expectedAfterResize = new Object[ArrayList.INITIAL_CAPACITY * 2];
        utilFillExpectedWithValues(expectedAfterResize, 0, 9, "b");
        assertArrayEquals(expectedAfterResize, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex() {
        String temp = new String("2a"); // For equality checking.

        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, temp);   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, "5a");   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeAtIndex(2));    // 0a, 1a, 3a, 4a, 5a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront() {
        String temp = "0a"; // For equality checking.

        list.addAtIndex(0, temp);   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, "5a");   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeFromFront());   // 1a, 2a, 3a, 4a, 5a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack() {
        String temp = "5a"; // For equality checking.

        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, temp);   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeFromBack());    // 0a, 1a, 2a, 3a, 4a

        assertEquals(5, list.size());

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexShift() {
        String temp = new String("2a");

        list.addAtIndex(0, "0a");
        list.addAtIndex(1, "1a");
        list.addAtIndex(2, temp);
        list.removeAtIndex(1);

        assertSame(temp, list.get(1));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFrontShift() {
        String temp = new String("1a");

        list.addAtIndex(0, "0a");
        list.addAtIndex(1, temp);
        list.addAtIndex(2, "2a");
        list.removeFromFront();

        assertSame(temp, list.get(0));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFrontResize() {
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY + 1; i++) {
            list.addToBack(String.valueOf(i) + "b");
        }

        Object[] expectedBeforeRemove = new Object[ArrayList.INITIAL_CAPACITY * 2];
        utilFillExpectedWithValues(expectedBeforeRemove, 0, 9, "b");
        assertArrayEquals(expectedBeforeRemove, list.getBackingArray());

        list.removeFromFront();

        Object[] expectedAfterRemove = new Object[ArrayList.INITIAL_CAPACITY * 2];
        utilFillExpectedWithValues(expectedAfterRemove, 1, 9, "b");
        assertArrayEquals(expectedAfterRemove, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBackResize() {
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY + 1; i++) {
            list.addToBack(String.valueOf(i) + "b");
        }

        Object[] expectedBeforeRemove = new Object[ArrayList.INITIAL_CAPACITY * 2];
        utilFillExpectedWithValues(expectedBeforeRemove, 0, 9, "b");
        assertArrayEquals(expectedBeforeRemove, list.getBackingArray());

        list.removeFromBack();

        Object[] expectedAfterRemove = new Object[ArrayList.INITIAL_CAPACITY * 2];
        utilFillExpectedWithValues(expectedAfterRemove, 0, 8, "b");
        assertArrayEquals(expectedAfterRemove, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexResize() {
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY + 1; i++) {
            list.addToBack(String.valueOf(i) + "b");
        }

        Object[] expectedBeforeRemove = new Object[ArrayList.INITIAL_CAPACITY * 2];
        utilFillExpectedWithValues(expectedBeforeRemove, 0, 9, "b");
        assertArrayEquals(expectedBeforeRemove, list.getBackingArray());

        list.removeAtIndex(9);

        Object[] expectedAfterRemove = new Object[ArrayList.INITIAL_CAPACITY * 2];
        utilFillExpectedWithValues(expectedAfterRemove, 0, 8, "b");
        assertArrayEquals(expectedAfterRemove, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testNull() {
        Object[] backingArray = list.getBackingArray();
        for (Object o : backingArray) {
            assertNull(o);
        }

        list.addAtIndex(0, "0d");
        backingArray = list.getBackingArray();
        assertNotNull(backingArray[0]);
        for (int i = 1; i < backingArray.length; i++) {
            assertNull(backingArray[i]);
        }

        list.removeAtIndex(0);
        backingArray = list.getBackingArray();
        assertNull(backingArray[0]);
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        assertEquals("0a", list.get(0));
        assertEquals("1a", list.get(1));
        assertEquals("2a", list.get(2));
        assertEquals("3a", list.get(3));
        assertEquals("4a", list.get(4));
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetNull() {
        list.addAtIndex(0, "0c");
        list.get(1);
    }

    @Test(timeout = TIMEOUT)
    public void testIsEmpty() {
        // Should be empty at initialization
        assertTrue(list.isEmpty());

        // Should not be empty after adding elements
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        assertFalse(list.isEmpty());

        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        list.addAtIndex(0, "0a");   // 0a
        list.addAtIndex(1, "1a");   // 0a, 1a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a
        list.addAtIndex(3, "3a");   // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a");   // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());

        // Clearing the list should empty the array and reset size
        list.clear();

        assertEquals(0, list.size());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY],
                list.getBackingArray());
    }

    /**
     * Adds items to a given array with String values that look like "1k".
     * @param array The pre-initialized array to fill values in
     * @param start The lowest number corresponding to the item that will be added at index 0
     * @param end   Inclusive. The highest number corresponding to the last item that will be added
     * @param suffix The string that will immediately follow the number in filled items
     */
    private void utilFillExpectedWithValues(Object[] array, int start, int end, String suffix) {
        for (int i = start; i <= end; i++) {
            array[i - start] = String.valueOf(i) + suffix;
        }
    }
}