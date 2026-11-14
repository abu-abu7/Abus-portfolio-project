package components.tasklist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/** Boundary cases and tests of secondary methods with another representation. */
public final class TaskListCoverageTest {
    /** A second kernel used to check representation independence. */
    private static final class LinkedTaskList extends TaskListSecondary {
        /** Task descriptions in insertion order. */
        private final List<String> entries = new LinkedList<>();

        /** Creates an empty test implementation. */
        private LinkedTaskList() {
        }

        @Override
        public void add(String task) {
            this.entries.add(task);
        }

        @Override
        public String remove(int index) {
            return this.entries.remove(index);
        }

        @Override
        public String task(int index) {
            return this.entries.get(index);
        }

        @Override
        public int size() {
            return this.entries.size();
        }

        @Override
        public TaskList newInstance() {
            return new LinkedTaskList();
        }

        @Override
        public void clear() {
            this.entries.clear();
        }

        @Override
        public void transferFrom(TaskList source) {
            this.copyFrom(source);
            source.clear();
        }
    }

    /**
     * Checks every task rather than relying on equals or toString.
     * @param actual list being checked
     * @param expected expected descriptions in order
     */
    private static void assertTasks(TaskList actual, String... expected) {
        assertEquals(expected.length, actual.size());
        for (int index = 0; index < expected.length; index++) {
            assertEquals(expected[index], actual.task(index));
        }
        assertEquals(expected.length, actual.size());
    }

    /** Covers each possible removed position for lists of several sizes. */
    @Test
    public void removeEveryPosition() {
        String[][] cases = { { "only" }, { "a", "b" },
                { "a", "b", "c" }, { "same", "same", "other", "same" } };
        for (String[] original : cases) {
            for (int removedIndex = 0; removedIndex < original.length; removedIndex++) {
                TaskList tasks = new TaskList1L();
                for (String description : original) {
                    tasks.add(description);
                }
                assertEquals(original[removedIndex], tasks.remove(removedIndex));
                assertEquals(original.length - 1, tasks.size());
                int actualIndex = 0;
                for (int index = 0; index < original.length; index++) {
                    if (index != removedIndex) {
                        assertEquals(original[index], tasks.task(actualIndex));
                        actualIndex++;
                    }
                }
            }
        }
    }

    /** Checks Unicode, preserved whitespace, and blank input rejection. */
    @Test
    public void descriptionsRetainTheirText() {
        TaskList tasks = new TaskList1L();
        tasks.add("  Review notes  ");
        tasks.add("Réviser 日本語");
        assertTasks(tasks, "  Review notes  ", "Réviser 日本語");
        for (String blank : new String[] { "", " ", "\t", "\n", "\u2003" }) {
            assertThrows(IllegalArgumentException.class, () -> tasks.add(blank));
            assertTasks(tasks, "  Review notes  ", "Réviser 日本語");
        }
    }

    /** Reads and searches leave all task values in their original order. */
    @Test
    public void searchEachPositionAndMissingText() {
        TaskList tasks = new TaskList1L();
        for (String description : new String[] { "first", "middle", "last" }) {
            tasks.add(description);
        }
        for (String description : new String[] { "first", "middle", "last" }) {
            assertTrue(tasks.contains(description));
            assertTasks(tasks, "first", "middle", "last");
        }
        assertFalse(tasks.contains("FIRST"));
        assertFalse(tasks.contains(""));
        assertFalse(tasks.contains("absent"));
        assertTasks(tasks, "first", "middle", "last");
    }

    /** Checks invalid reads and removes on empty and populated lists. */
    @Test
    public void indexFailuresPreserveEveryTask() {
        TaskList tasks = new TaskList1L();
        for (int index : new int[] { -1, 0, 1 }) {
            assertThrows(IndexOutOfBoundsException.class, () -> tasks.task(index));
            assertThrows(IndexOutOfBoundsException.class, () -> tasks.remove(index));
            assertTasks(tasks);
        }
        tasks.add("safe");
        for (int index : new int[] { -1, 1, Integer.MAX_VALUE }) {
            assertThrows(IndexOutOfBoundsException.class, () -> tasks.task(index));
            assertThrows(IndexOutOfBoundsException.class, () -> tasks.remove(index));
            assertTasks(tasks, "safe");
        }
    }

    /** Exercises transfer between empty and populated receivers and sources. */
    @Test
    public void transferStateMatrix() {
        for (boolean receiverPopulated : new boolean[] { false, true }) {
            for (boolean sourcePopulated : new boolean[] { false, true }) {
                TaskList receiver = new TaskList1L();
                TaskList source = new TaskList1L();
                if (receiverPopulated) {
                    receiver.add("old");
                }
                if (sourcePopulated) {
                    source.add("new");
                    source.add("new");
                }
                receiver.transferFrom(source);
                assertTasks(source);
                if (sourcePopulated) {
                    assertTasks(receiver, "new", "new");
                } else {
                    assertTasks(receiver);
                }
                source.add("independent");
                receiver.clear();
                assertTasks(source, "independent");
            }
        }
    }

    /** Copy, equality, and secondary methods work with a different kernel. */
    @Test
    public void secondaryMethodsWorkAcrossRepresentations() {
        TaskList array = new TaskList1L();
        TaskList linked = new LinkedTaskList();
        linked.add("a");
        linked.add("b");
        linked.add("a");
        array.copyFrom(linked);
        assertTasks(array, "a", "b", "a");
        assertTasks(linked, "a", "b", "a");
        assertEquals(array, linked);
        assertEquals(linked, array);
        assertEquals(array.hashCode(), linked.hashCode());
        assertTrue(linked.contains("b"));
        assertEquals(2, linked.removeAll("a"));
        assertTasks(linked, "b");
        assertTasks(array, "a", "b", "a");
        linked.copyFrom(array);
        assertTasks(linked, "a", "b", "a");
        assertThrows(IllegalArgumentException.class, () -> array.transferFrom(linked));
        assertTasks(array, "a", "b", "a");
        assertTasks(linked, "a", "b", "a");
    }

    /** All matches are removed, including adjacent matches and boundary matches. */
    @Test
    public void removeAllStateMatrix() {
        String[][] cases = { {}, { "x" }, { "x", "x" },
                { "a", "b" }, { "x", "a", "x", "x", "b", "x" } };
        for (String[] original : cases) {
            TaskList tasks = new TaskList1L();
            List<String> expected = new LinkedList<>();
            int removed = 0;
            for (String description : original) {
                tasks.add(description);
                if (description.equals("x")) {
                    removed++;
                } else {
                    expected.add(description);
                }
            }
            assertEquals(removed, tasks.removeAll("x"));
            assertTasks(tasks, expected.toArray(new String[0]));
            assertEquals(0, tasks.removeAll("x"));
        }
    }

    /** Equal values share hashes, and order and duplicates affect equality. */
    @Test
    public void equalityAndHashContracts() {
        TaskList first = new TaskList1L();
        TaskList second = new TaskList1L();
        TaskList third = new TaskList1L();
        for (TaskList tasks : new TaskList[] { first, second, third }) {
            tasks.add("a");
            tasks.add("b");
        }
        assertTrue(first.equals(first));
        assertTrue(first.equals(second));
        assertTrue(second.equals(first));
        assertTrue(second.equals(third));
        assertTrue(first.equals(third));
        assertEquals(first.hashCode(), second.hashCode());
        assertEquals(first.hashCode(), first.hashCode());
        second.clear();
        second.add("b");
        second.add("a");
        assertNotEquals(first, second);
        third.add("b");
        assertNotEquals(first, third);
        assertFalse(first.equals(null));
        assertFalse(first.equals(new Object()));
        assertTasks(first, "a", "b");
    }
}
