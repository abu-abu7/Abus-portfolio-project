package examples;

import components.tasklist.TaskList;
import components.tasklist.TaskList1L;

/** Copies a maintenance template and transfers the remaining work. */
public final class MaintenanceChecklist {
    private MaintenanceChecklist() {}

    /**
     * Creates a bicycle checklist from a shared template.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        TaskList template = new TaskList1L();
        template.add("Inspect brakes");
        template.add("Check tire pressure");
        TaskList bicycle = template.newInstance();
        bicycle.copyFrom(template);
        bicycle.add("Lubricate chain");
        bicycle.removeAll("Inspect brakes");
        TaskList workbench = template.newInstance();
        workbench.transferFrom(bicycle);
        System.out.println("Reusable template: " + template);
        System.out.println("Workbench tasks: " + workbench);
        System.out.println("Transferred checklist: " + bicycle);
    }
}
