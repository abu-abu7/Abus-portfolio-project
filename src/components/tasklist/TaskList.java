package components.tasklist;

/** A task list with search, copy, and bulk removal operations. */
public interface TaskList extends TaskListKernel {
    /**
     * Checks whether a task is in the list. Matching is case-sensitive.
     *
     * @param description search text
     * @return whether a matching task exists
     * @requires description != null
     * @ensures contains iff description occurs in this; this = #this
     */
    boolean contains(String description);

    /**
     * Replaces this list with a copy of source. Self-copy leaves it unchanged.
     *
     * @param source list to copy
     * @replaces this
     * @requires source != null
     * @ensures this = #source and source = #source
     */
    void copyFrom(TaskList source);

    /**
     * Removes all tasks matching the given description.
     *
     * @param description exact text to remove
     * @return number of tasks removed
     * @updates this
     * @requires description != null
     * @ensures all matching tasks are removed; other tasks retain their order
     */
    int removeAll(String description);
}
