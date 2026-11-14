package components.tasklist;

import java.util.Objects;

/** Shared TaskList methods implemented using the kernel operations. */
public abstract class TaskListSecondary implements TaskList {
    /** Multiplier used to combine task hashes in list order. */
    private static final int HASH_MULTIPLIER = 31;

    /** Constructor for TaskList implementations. */
    protected TaskListSecondary() {
    }

    @Override
    public final boolean contains(String description) {
        Objects.requireNonNull(description, "description");
        for (int i = 0; i < this.size(); i++) {
            if (this.task(i).equals(description)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final void copyFrom(TaskList source) {
        Objects.requireNonNull(source, "source");
        if (source != this) {
            this.clear();
            for (int i = 0; i < source.size(); i++) {
                this.add(source.task(i));
            }
        }
    }

    @Override
    public final int removeAll(String description) {
        Objects.requireNonNull(description, "description");
        int removed = 0;
        for (int i = this.size() - 1; i >= 0; i--) {
            if (this.task(i).equals(description)) {
                this.remove(i);
                removed++;
            }
        }
        return removed;
    }

    @Override
    public final boolean equals(Object other) {
        boolean equal = other == this;
        if (!equal && other instanceof TaskList) {
            TaskList that = (TaskList) other;
            equal = this.size() == that.size();
            for (int i = 0; equal && i < this.size(); i++) {
                equal = this.task(i).equals(that.task(i));
            }
        }
        return equal;
    }

    @Override
    public final int hashCode() {
        int result = 1;
        for (int i = 0; i < this.size(); i++) {
            result = HASH_MULTIPLIER * result + this.task(i).hashCode();
        }
        return result;
    }

    @Override
    public final String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < this.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(this.task(i));
        }
        return result.append(']').toString();
    }
}
