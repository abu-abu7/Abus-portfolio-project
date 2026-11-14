package components.tasklist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

/** Tests kernel and Standard operations. */
public class TaskList1LTest {
    /** Empty and clear. */
    @Test
    public void emptyAndClear() {
        TaskList t = new TaskList1L();
        assertEquals(0, t.size());
        t.clear();
        t.add("a");
        t.clear();
        assertEquals(0, t.size());
        t.add("b");
        assertEquals("b", t.task(0));
    }

    /** Ordered duplicates and read. */
    @Test
    public void orderedDuplicatesAndRead() {
        TaskList t = new TaskList1L();
        t.add("a");
        t.add("b");
        t.add("a");
        assertEquals("[a, b, a]", t.toString());
        assertEquals("b", t.task(1));
        assertEquals(new String[] { "a", "b", "a" }.length, t.size());
        assertEquals("[a, b, a]", t.toString());
    }

    /** Remove boundaries. */
    @Test
    public void removeBoundaries() {
        TaskList t = new TaskList1L();
        t.add("a");
        t.add("b");
        t.add("c");
        assertEquals("b", t.remove(1));
        assertEquals("[a, c]", t.toString());
        assertEquals("c", t.remove(1));
        assertEquals("a", t.remove(0));
        assertEquals(0, t.size());
    }

    /** Fresh instance. */
    @Test
    public void freshInstance() {
        TaskList t = new TaskList1L();
        t.add("a");
        TaskList n = t.newInstance();
        assertEquals(t.getClass(), n.getClass());
        assertEquals(0, n.size());
        n.add("b");
        assertEquals("[a]", t.toString());
    }

    /** Transfer ownership. */
    @Test
    public void transferOwnership() {
        TaskList a = new TaskList1L();
        TaskList b = new TaskList1L();
        a.add("old");
        b.add("new");
        a.transferFrom(b);
        assertEquals("[new]", a.toString());
        assertEquals(0, b.size());
        b.add("independent");
        assertEquals("[new]", a.toString());
        b.clear();
        a.transferFrom(b);
        assertEquals(0, a.size());
    }

    /** Invalid inputs preserve state. */
    @Test
    public void invalidInputsPreserveState() {
        TaskList t = new TaskList1L();
        t.add("safe");
        assertThrows(NullPointerException.class, () -> t.add(null));
        assertThrows(IllegalArgumentException.class, () -> t.add("  "));
        assertThrows(IndexOutOfBoundsException.class, () -> t.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> t.remove(1));
        assertThrows(IndexOutOfBoundsException.class, () -> t.task(1));
        assertThrows(IllegalArgumentException.class, () -> t.transferFrom(t));
        assertThrows(NullPointerException.class, () -> t.transferFrom(null));
        assertEquals("[safe]", t.toString());
    }
}
