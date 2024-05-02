import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author Yirou (Cynthia) Wang
 * @version 1.2
 */
public class DoublyLinkedListCynthiaTest {
    private static final int TIMEOUT = 200;
    private DoublyLinkedList<String> dll;

    @Before
    public void setUp() {
        dll = new DoublyLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertNull(dll.getHead());
        assertNull(dll.getTail());
        assertEquals(dll.size(), 0);
        assertTrue(dll.isEmpty());
        assertEquals(dll.toArray().length, 0);
        assertArrayEquals(dll.toArray(), new Object[0]);
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndex() {
        dll.addAtIndex(0, "1a");
        dll.addAtIndex(1, "2a");
        dll.addAtIndex(0, "0a");

        DoublyLinkedListNode<String> current = dll.getHead();
        assertEquals(current.getData(), "0a");

        current = current.getNext();
        assertEquals(current.getData(), "1a");

        current = current.getNext();
        assertEquals(current.getData(), "2a");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexLessThanZero() {
        dll.addAtIndex(-1, "3RR0R");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexMoreThanSize() {
        dll.addAtIndex(1, "3RR0R");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddAtIndexNull() {
        dll.addAtIndex(0, null);
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFront() {
        dll.addToFront("2a");
        dll.addToFront("1a");
        dll.addToFront("0a");

        DoublyLinkedListNode<String> current = dll.getHead();
        assertEquals(current.getData(), "0a");

        current = current.getNext();
        assertEquals(current.getData(), "1a");

        current = current.getNext();
        assertEquals(current.getData(), "2a");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToFrontNull() {
        dll.addToFront(null);
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBack() {
        dll.addToBack("0a");
        dll.addToBack("1a");
        dll.addToBack("2a");

        DoublyLinkedListNode<String> current = dll.getHead();
        assertEquals(current.getData(), "0a");

        current = current.getNext();
        assertEquals(current.getData(), "1a");

        current = current.getNext();
        assertEquals(current.getData(), "2a");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToBackNull() {
        dll.addToBack(null);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex() {
        String temp = "1a";
        temp = new String("1a");

        dll.addToBack("0a");
        dll.addToBack(temp);
        dll.addToBack("2a");

        String removed = dll.removeAtIndex(1);
        assertSame(removed, temp);
        assertNotSame(removed, "1a");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexLessThanZero() {
        dll.removeAtIndex(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexMoreThanSize() {
        dll.removeAtIndex(1);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront() {
        String temp = "0a";
        temp = new String("0a");

        dll.addToBack(temp);
        dll.addToBack("1a");
        dll.addToBack("2a");

        String removed = dll.removeFromFront();
        assertSame(removed, temp);
        assertNotSame(removed, "0a");
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFromFrontEmpty() {
        dll.removeFromFront();
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack() {
        String temp = "2a";
        temp = new String("2a");

        dll.addToBack("0a");
        dll.addToBack("1a");
        dll.addToBack(temp);

        String removed = dll.removeFromBack();
        assertSame(removed, temp);
        assertNotSame(removed, "2a");
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFromBackEmpty() {
        dll.removeFromBack();
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        String temp = "1a";
        temp = new String("1a");

        dll.addToBack("0a");
        dll.addToBack(temp);
        dll.addToBack("2a");

        String got = dll.get(1);
        assertSame(got, temp);
        assertNotSame(got, "1a");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetLessThanZero() {
        dll.get(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetMoreThanSize() {
        dll.get(1);
    }

    @Test(timeout = TIMEOUT)
    public void testIsEmpty() {
        dll.addToFront("0a");
        assertFalse(dll.isEmpty());
        dll.removeFromFront();
        assertTrue(dll.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        dll.addToBack("0a");
        dll.addToBack("1a");
        dll.addToBack("2a");
        dll.clear();
        assertEquals(dll.size(), 0);
        assertNull(dll.getHead());
        assertNull(dll.getTail());
        assertTrue(dll.isEmpty());
        assertEquals(dll.toArray().length, 0);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurrence() {
        String temp1 = "1a";
        String temp2 = new String("1a");

        dll.addToBack("0a");
        dll.addToBack(temp1);
        dll.addToBack(temp2);
        dll.addToBack("2a");

        String removed = dll.removeLastOccurrence("1a");
        assertSame(removed, temp2);
        assertNotSame(removed, "1a");
        assertNotSame(removed, temp1);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveLastOccurrenceNull() {
        dll.removeLastOccurrence(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveLastOccurrenceEmpty() {
        dll.removeLastOccurrence("3RR0R");
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveLastOccurrenceNotFound() {
        dll.addToBack("0a");
        dll.addToBack("1a");
        dll.addToBack("2a");

        dll.removeLastOccurrence("3RR0R");
    }

    @Test(timeout = TIMEOUT)
    public void testToArray() {
        dll.addToBack("0a");
        dll.addToBack("1a");
        dll.addToBack("2a");

        Object[] array = dll.toArray();
        assertArrayEquals(array, new Object[]{"0a", "1a", "2a"});
        assertEquals(array.length, 3);
    }

    @Test(timeout = TIMEOUT)
    public void testHeadLinksToTail() {
        dll.addToBack("0a");
        dll.addToBack("1a");
        dll.addToBack("2a");
        dll.addToBack("3a");

        DoublyLinkedListNode<String> head = dll.getHead();
        DoublyLinkedListNode<String> tail = dll.getTail();
        DoublyLinkedListNode<String> current = head;

        while (!current.equals(tail)) {  // Head to tail
            assertNotNull(current.getNext());
            current = current.getNext();
        }

        while (!current.equals(head)) {  // Tail to head
            assertNotNull(current.getPrevious());
            current = current.getPrevious();
        }
    }

    @Test(timeout = TIMEOUT)
    public void testGetHeadPrev() {
        dll.addToFront("0a");
        DoublyLinkedListNode<String> head = dll.getHead();
        assertNull(head.getPrevious());
    }

    @Test(timeout = TIMEOUT)
    public void testGetTailNext() {
        dll.addToFront("0a");
        DoublyLinkedListNode<String> tail = dll.getTail();
        assertNull(tail.getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testSize() {
        dll.addToBack("0a");
        dll.addToBack("1a");
        dll.addToBack("2a");
        dll.addToBack("4a");
        dll.addAtIndex(3, "3a");
        assertEquals(dll.size(), 5);
    }
}