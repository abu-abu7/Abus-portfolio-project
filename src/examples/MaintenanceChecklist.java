package examples;

import java.util.Objects;

import components.tasklist.TaskList;
import components.tasklist.TaskList1L;

/** A maintenance job whose pending steps are stored in a TaskList. */
public final class MaintenanceChecklist {
    /** Steps still needed, in the order they should be done. */
    private final TaskList pending;

    /**
     * Creates a job with its own copy of the template steps.
     * @param template initial steps
     * @requires template != null
     * @ensures pending steps equal template and template is unchanged
     */
    public MaintenanceChecklist(TaskList template) {
        Objects.requireNonNull(template, "template");
        this.pending = new TaskList1L();
        this.pending.copyFrom(template);
    }

    /**
     * Adds a step to the job.
     * @param step description of the work
     * @updates this
     * @requires step != null and step is not blank
     * @ensures step is appended to the pending steps
     */
    public void addStep(String step) {
        this.pending.add(step);
    }

    /**
     * Finishes the next step.
     * @return the finished step
     * @updates this
     * @requires remaining() &gt; 0
     * @ensures the first pending step is returned and removed
     */
    public String finishNext() {
        return this.pending.remove(0);
    }

    /**
     * Reports how many steps remain.
     * @return number of pending steps
     * @ensures remaining is the pending step count and this is unchanged
     */
    public int remaining() {
        return this.pending.size();
    }

    /**
     * Copies the remaining steps for the caller to inspect or edit.
     * @return an independent snapshot
     * @ensures snapshot contains the pending steps and this is unchanged
     */
    public TaskList snapshot() {
        TaskList result = this.pending.newInstance();
        result.copyFrom(this.pending);
        return result;
    }

    /**
     * Runs two independent jobs from a shared maintenance template.
     * @param args unused
     */
    public static void main(String[] args) {
        TaskList template = new TaskList1L();
        template.add("Inspect brakes");
        template.add("Check tire pressure");
        MaintenanceChecklist bicycle = new MaintenanceChecklist(template);
        MaintenanceChecklist spareBicycle = new MaintenanceChecklist(template);
        bicycle.addStep("Lubricate chain");
        System.out.println("Finished: " + bicycle.finishNext());
        System.out.println("Bicycle steps: " + bicycle.snapshot());
        System.out.println("Steps left: " + bicycle.remaining());
        System.out.println("Spare bicycle steps: " + spareBicycle.snapshot());
        System.out.println("Template unchanged: " + template);
    }
}
