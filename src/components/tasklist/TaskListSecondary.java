package components.tasklist;

import java.util.Objects;

/** Shared TaskList methods implemented using the kernel operations. */
public abstract class TaskListSecondary implements TaskList {
    /** Constructor for TaskList implementations. */
    protected TaskListSecondary() {}

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
        if (other == this) {
            return true;
        }
        if (!(other instanceof TaskList)) {
            return false;
        }
        TaskList that = (TaskList) other;
        if (this.size() != that.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i++) {
            if (!this.task(i).equals(that.task(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final int hashCode() {
        int result = 1;
        for (int i = 0; i < this.size(); i++) {
            result = 31 * result + this.task(i).hashCode();
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
