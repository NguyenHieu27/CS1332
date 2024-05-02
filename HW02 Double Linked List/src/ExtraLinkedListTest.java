import org.junit.Test;

import java.lang.ref.WeakReference;

import static org.junit.Assert.*;

/**
 * The other posted tests cover most of my code! The only bits that aren't
 * checked are whether the linked list implementation actually allows nodes to
 * be garbage collected and whether the head is updated when removing last
 * occurrence. So, this tests that!
 *
 * @author Alexander Gualino
 * @version 1.0
 */
public class ExtraLinkedListTest {
    private static final int TIMEOUT = 200;

    @Test(timeout = TIMEOUT)
    public void testNodesGetGCed() {
        var list = new DoublyLinkedList<>();

        // set up the list
        list.addToBack(new Object());
        list.addToBack(new Object());
        list.addToBack(new Object() {
            @Override
            public boolean equals(Object obj) {
                return true;
            }
        });
        list.addToBack(new Object());

        // get references to data that will be GCed
        var startData = new WeakReference<>(list.get(0));
        var endData = new WeakReference<>(list.get(3));
        var equalledData = new WeakReference<>(list.get(2));
        var stayData = new WeakReference<>(list.get(1));
        assertEquals(4, list.size());

        assertNotSame(startData.get(), endData.get());
        assertNotSame(null, startData.get());
        assertNotSame(null, endData.get());
        assertNotSame(null, equalledData.get());

        // clear the front and back and equals
        list.removeFromFront();
        list.removeFromBack();
        list.removeLastOccurrence(new Object());
        assertEquals(1, list.size());
        assertSame(list.getHead().getData(), list.getTail().getData());

        // run GC
        System.gc();

        // check that removed data is really removed
        assertSame(null, startData.get());
        assertSame(null, endData.get());
        assertSame(null, equalledData.get());
        assertNotSame(null, stayData.get());
        assertSame(stayData.get(), list.getHead().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurrenceChangesHead() {
        var list = new DoublyLinkedList<>();
        list.addToBack(new Object() {
            @Override
            public boolean equals(Object obj) {
                return true;
            }
        });
        list.addToBack(new Object());

        list.removeLastOccurrence(new Object());

        assertEquals(1, list.size());
        assertSame(list.get(0), list.getHead().getData());
        assertSame(list.get(0), list.getTail().getData());
    }
}
