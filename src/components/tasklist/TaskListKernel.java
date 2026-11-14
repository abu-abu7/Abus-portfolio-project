package components.tasklist;

import components.standard.Standard;

/**
 * An ordered list of nonblank task descriptions. Duplicates are allowed.
 *
 * @mathmodel type TaskListKernel is modeled by a sequence of strings
 * @initially this is the empty sequence
 */
public interface TaskListKernel extends Standard<TaskList> {
    /**
     * Returns a new empty list of the same implementation type.
     *
     * @return a fresh empty task list
     * @ensures newInstance is empty and this = #this
     */
    @Override
    TaskList newInstance();

    /**
     * Removes all tasks.
     *
     * @clears this
     * @ensures this is empty
     */
    @Override
    void clear();

    /**
     * Replaces this list with source and leaves source empty.
     *
     * @param source the list whose value is moved
     * @replaces this
     * @clears source
     * @requires source != null and source != this and source has this dynamic type
     * @ensures this = #source and source is empty
     */
    @Override
    void transferFrom(TaskList source);

    /**
     * Adds a task at the end of the list.
     *
     * @param task description to append
     * @updates this
     * @requires task != null and task is not blank
     * @ensures this = #this followed by task
     */
    void add(String task);

    /**
     * Removes the task at an index.
     *
     * @param index zero-based position
     * @return the removed description
     * @updates this
     * @requires 0 &lt;= index and index &lt; size()
     * @ensures remove is the old task at index; other tasks retain their order
     */
    String remove(int index);

    /**
     * Returns the task at index without changing the list.
     *
     * @param index zero-based position
     * @return the description at index
     * @requires 0 &lt;= index and index &lt; size()
     * @ensures task is this[index] and this = #this
     */
    String task(int index);

    /**
     * Returns the number of tasks.
     *
     * @return task count
     * @ensures size = length of this and this = #this
     */
    int size();
}
