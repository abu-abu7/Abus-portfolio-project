# Component design

## Model

TaskList is a finite sequence of nonblank strings. Task order matters, and
repeated descriptions are allowed. Matching is case-sensitive. Removing a task
only removes its description; there is no stored completion history.

## Layers

```text
Standard<TaskList>
  └── TaskListKernel
        └── TaskList
              └── TaskListSecondary
                    └── TaskList1L
```

| File | Purpose |
| --- | --- |
| TaskListKernel | Declares add, remove, task, size, and the Standard contracts |
| TaskList | Declares contains, copyFrom, and removeAll |
| TaskListSecondary | Implements enhanced methods plus equals, hashCode, and toString using the kernel |
| TaskList1L | Stores the ArrayList and implements the kernel and Standard methods |

## Representation and contracts

The representation is an ArrayList of descriptions. The list and its entries
must be nonnull, and entries must not be blank. Its entries, in order, are the
abstract value of the component.

- `newInstance` returns a separate empty list of the same implementation type.
- `clear` makes the list empty.
- `transferFrom` moves the source's value into the receiver and clears the source.
  The source must be a different object of the same implementation type.
- `copyFrom` copies the source without changing it. Self-copy is allowed.
- `equals` compares task values and order. Equal lists have equal hash codes.

Null inputs, blank additions, and invalid indices are rejected. Method contracts
and parameter modes are documented in the interfaces.

## Use cases

StudyPlanner uses TaskList directly to create a study plan and remove finished
work.

MaintenanceChecklist uses a private TaskList as its representation. It copies a
template, adds steps, finishes the next step, and returns copies of pending work.
Two maintenance jobs can use the same template without affecting each other.

## Limits

Tasks are kept in memory. The component does not save files or track deadlines.
Reading a task is O(1), removal is O(n), and searching is O(n). Removing all
matches can take O(n²) because ArrayList shifts entries after removal.
