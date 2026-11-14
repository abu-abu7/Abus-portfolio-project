package components.tasklist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TaskList implementation using an ArrayList.
 *
 * @convention tasks != null and every entry is nonnull and nonblank
 * @correspondence this = the sequence of descriptions stored in tasks
 */
public final class TaskList1L extends TaskListSecondary {
    /** Task descriptions in list order. */
    private List<String> tasks;

    /** Creates an empty task list. */
    public TaskList1L() {
        this.tasks = new ArrayList<>();
    }

    @Override
    public void add(String task) {
        Objects.requireNonNull(task, "task");
        if (task.isBlank()) {
            throw new IllegalArgumentException("Task must not be blank");
        }
        this.tasks.add(task);
    }

    @Override
    public String remove(int index) {
        return this.tasks.remove(index);
    }

    @Override
    public String task(int index) {
        return this.tasks.get(index);
    }

    @Override
    public int size() {
        return this.tasks.size();
    }

    @Override
    public TaskList newInstance() {
        return new TaskList1L();
    }

    @Override
    public void clear() {
        this.tasks = new ArrayList<>();
    }

    @Override
    public void transferFrom(TaskList source) {
        Objects.requireNonNull(source, "source");
        if (source == this || source.getClass() != this.getClass()) {
            throw new IllegalArgumentException("Source must be a distinct TaskList1L");
        }
        TaskList1L that = (TaskList1L) source;
        this.tasks = that.tasks;
        that.tasks = new ArrayList<>();
    }
}
