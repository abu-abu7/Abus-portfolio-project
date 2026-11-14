package components.tasklist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/** Tests enhanced and common operations. */
public class TaskListTest {
    /** Search preserves state. */
    @Test
    public void searchPreservesState() {
        TaskList t = new TaskList1L();
        assertFalse(t.contains("a"));
        t.add("a");
        t.add("b");
        assertTrue(t.contains("b"));
        assertFalse(t.contains("A"));
        assertEquals("[a, b]", t.toString());
    }

    /** Copy is independent and replaces. */
    @Test
    public void copyIsIndependentAndReplaces() {
        TaskList a = new TaskList1L();
        TaskList b = new TaskList1L();
        a.add("old");
        b.add("new");
        a.copyFrom(b);
        assertEquals(a, b);
        b.add("other");
        assertEquals("[new]", a.toString());
        a.copyFrom(a);
        assertEquals("[new]", a.toString());
        a.copyFrom(new TaskList1L());
        assertEquals(0, a.size());
    }

    /** Remove matches preserves other order. */
    @Test
    public void removeMatchesPreservesOtherOrder() {
        TaskList t = new TaskList1L();
        assertEquals(0, t.removeAll("a"));
        for (String description : new String[] { "a", "b", "a", "a", "c", "a" }) {
            t.add(description);
        }
        int expectedRemoved = new String[] { "a", "a", "a", "a" }.length;
        assertEquals(expectedRemoved, t.removeAll("a"));
        assertEquals("[b, c]", t.toString());
        assertEquals(0, t.removeAll("missing"));
        t.removeAll("b");
        t.removeAll("c");
        assertEquals(0, t.size());
    }

    /** Common methods. */
    @Test
    public void commonMethods() {
        TaskList a = new TaskList1L();
        TaskList b = new TaskList1L();
        assertEquals("[]", a.toString());
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertFalse(a.equals(null));
        assertFalse(a.equals("[]"));
        a.add("x");
        assertNotEquals(a, b);
        b.add("x");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(a, a);
        b.add("y");
        assertNotEquals(a, b);
        a.add("z");
        assertNotEquals(a, b);
    }

    /** Secondary rejects null. */
    @Test
    public void secondaryRejectsNull() {
        TaskList t = new TaskList1L();
        t.add("a");
        assertThrows(NullPointerException.class, () -> t.copyFrom(null));
        assertThrows(NullPointerException.class, () -> t.contains(null));
        assertThrows(NullPointerException.class, () -> t.removeAll(null));
        assertEquals("[a]", t.toString());
    }
}
