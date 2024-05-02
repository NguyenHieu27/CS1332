import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

/**
 * @author Srithan Nalluri
 * @version 1.0
 * The test cases below cover a variety of methods and edge cases, including checking whether the proper
 * exceptions are thrown when necessary.
 */
public class HW1JUnit {

    private static final int TIMEOUT = 200;
    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT)
    public void addMethods() {
        list.addToBack("C"); // C
        list.addToBack("D"); // C, D
        list.addToBack("E"); // C, D, E
        list.addToBack("G"); // C, D, E, G
        list.addAtIndex(3, "F"); // C, D, E, F, G
        list.addToFront("B"); // B, C, D, E, F, G
        list.addAtIndex(0, "A"); // A, B, C, D, E, F, G

        assertEquals(7, list.size());

        Object[] correct = new Object[ArrayList.INITIAL_CAPACITY];
        correct[0] = "A";
        correct[1] = "B";
        correct[2] = "C";
        correct[3] = "D";
        correct[4] = "E";
        correct[5] = "F";
        correct[6] = "G";
        assertArrayEquals(correct, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void outOfBoundsInputAdd() {
        list.addToBack("A");
        list.addToBack("B");
        list.addAtIndex(2, "C");
        list.addAtIndex(5, "D");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void anotherOutOfBoundsInputAdd() {
        list.addToBack("A"); // A
        list.addToBack("B"); // A, B
        list.addAtIndex(2, "C"); // A, B, C
        list.addAtIndex(-1, "D"); // Exception
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void nullInputforAddAtIndex() {
        list.addToBack("B"); // B
        list.addToFront("A"); // A, B
        list.addAtIndex(2, null); // Exception
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void nullInputForAddToFront() {
        list.addToBack("B"); // B
        list.addToFront(null); // Exception
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void nullInputForAddToBack() {
        list.addToBack("A"); // A
        list.addToBack(null); // Exception
    }
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void removeAtIndexFromEmptyArrayList() {
        list.addToBack("B"); // B
        list.addToFront("A"); // A, B
        list.addAtIndex(2, "C"); // A, B, C
        list.clear(); //
        list.removeAtIndex(1); // Exception
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeFromFrontFromEmptyArrayList() {
        list.removeFromFront();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeFromBackFromEmptyArrayList() {
        list.removeFromBack();
    }



    @Test(timeout = TIMEOUT)
    public void removeTesting() {
        list.addToBack("A"); // A
        list.addToBack("B"); // A, B
        list.addToBack("C"); // A, B, C
        list.addToBack("D"); // A, B, C, D
        list.addToBack("E"); // A, B, C, D, E
        list.addToBack("F"); // A, B, C, D, E, F
        list.removeFromBack(); // A, B, C, D, E
        list.removeFromFront(); // B, C, D, E
        list.removeAtIndex(2); // B, C, E

        assertEquals(3, list.size());

        Object[] correct = new Object[ArrayList.INITIAL_CAPACITY];
        correct[0] = "B";
        correct[1] = "C";
        correct[2] = "E";
        assertArrayEquals(correct, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testingClear() {
        list.addToBack("B"); // B
        list.addToFront("A");  // A, B
        list.addAtIndex(2, "C"); // A, B, C
        list.clear(); //
        assertEquals(0, list.size());

        Object[] correct = new Object[ArrayList.INITIAL_CAPACITY];
        assertArrayEquals(correct, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void singleElement() {
        list.addToBack("A"); // A
        assertEquals(1, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void emptyInitialArray() {
        assertEquals(true, list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void isEmptyForNonEmptyArrayList() {
        list.addToBack("A"); // A
        list.addToBack("B"); // A, B
        assertEquals(false, list.isEmpty());
    }
}
