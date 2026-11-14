package examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import components.tasklist.TaskList;
import components.tasklist.TaskList1L;

/** Tests ownership and step ordering in the representation use case. */
public final class MaintenanceChecklistTest {
    /** Jobs copy their template and do not change each other's steps. */
    @Test
    public void jobsAreIndependent() {
        TaskList template = new TaskList1L();
        template.add("Inspect brakes");
        MaintenanceChecklist first = new MaintenanceChecklist(template);
        MaintenanceChecklist second = new MaintenanceChecklist(template);
        first.addStep("Check tires");
        assertEquals("Inspect brakes", first.finishNext());
        assertEquals(1, first.remaining());
        assertEquals("Check tires", first.finishNext());
        assertEquals(0, first.remaining());
        assertEquals(1, second.remaining());
        assertEquals("Inspect brakes", template.task(0));
        assertEquals(1, template.size());
    }

    /** The caller cannot change a job through a template or snapshot. */
    @Test
    public void snapshotsAndTemplatesDoNotExposeState() {
        TaskList template = new TaskList1L();
        template.add("Inspect brakes");
        MaintenanceChecklist job = new MaintenanceChecklist(template);
        template.clear();
        TaskList snapshot = job.snapshot();
        assertEquals("Inspect brakes", snapshot.task(0));
        snapshot.clear();
        assertEquals(1, job.remaining());
        assertEquals("Inspect brakes", job.snapshot().task(0));
    }

    /** Empty jobs and invalid input follow the underlying component contracts. */
    @Test
    public void emptyAndInvalidOperations() {
        MaintenanceChecklist job = new MaintenanceChecklist(new TaskList1L());
        assertEquals(0, job.remaining());
        assertEquals(0, job.snapshot().size());
        assertThrows(IndexOutOfBoundsException.class, job::finishNext);
        assertThrows(IllegalArgumentException.class, () -> job.addStep(" "));
        assertThrows(NullPointerException.class, () -> job.addStep(null));
        assertThrows(NullPointerException.class, () -> new MaintenanceChecklist(null));
        assertEquals(0, job.remaining());
    }

    /** Both public entry points run without setup or external services. */
    @Test
    public void demoEntryPointsRun() {
        StudyPlanner.main(new String[0]);
        MaintenanceChecklist.main(new String[0]);
    }
}
