package examples;

import components.tasklist.TaskList;
import components.tasklist.TaskList1L;

/** A study checklist that removes tasks as they are finished. */
public final class StudyPlanner {
    /** Prevents creating instances of this demo class. */
    private StudyPlanner() {
    }

    /**
     * Creates a study plan and finishes the first task.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        TaskList today = new TaskList1L();
        today.add("Review Java interfaces");
        today.add("Practice recursion");
        today.add("Write test cases");
        System.out.println("Study plan: " + today);
        System.out.println("Finished: " + today.remove(0));
        System.out.println("Remaining: " + today);
    }
}
