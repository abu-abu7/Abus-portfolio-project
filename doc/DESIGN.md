# TaskList design

## Scope and model

The abstract value is an ordered finite sequence of nonblank strings. Duplicate
and Unicode descriptions are supported; exact matching is case-sensitive.
Descriptions retain their original whitespace. Removing a task marks it done;
this component does not store completion history, dates, IDs, or persistence.

A task tracker was chosen because it has a small useful kernel and supports
separate workflows: a disposable study plan and reusable maintenance templates.
An alternative design with priorities and dates was deferred to keep the model
small enough to understand and test.

## Layering and contracts

TaskListKernel extends the real OSU Standard<TaskList>. TaskList extends that
kernel. TaskListSecondary implements enhanced operations using only public
kernel/Standard methods, with no access to the backing representation.
TaskList1L supplies the kernel and Standard operations using ArrayList.

The representation invariant is a nonnull list of nonnull, nonblank strings;
the abstraction relation maps the backing list in order to the abstract sequence.
Interface JavaDoc describes parameter modes and method contracts.

Standard operations: newInstance returns a fresh empty TaskList1L; clear replaces
the receiver with an empty sequence; transferFrom replaces the receiver with the
source value and clears the source. Transfer requires a distinct TaskList1L and
moves ownership of the backing list. copyFrom permits self-copy, preserves its
source, and creates independent list storage. Strings themselves are immutable.

Null parameters raise NullPointerException; blank additions and invalid transfer
sources raise IllegalArgumentException; invalid indices raise
IndexOutOfBoundsException. Rejected kernel calls preserve state.

Equality compares ordered contents of TaskList implementations; hashCode follows
the same ordered model. toString prints bracketed descriptions for debugging.
Instances are mutable and not thread-safe; do not mutate an instance while it is
used as a hash-map key.

## Complexity

For n tasks: indexed read and size are O(1); append is amortized O(1); indexed
removal is O(n). Search, copy, equality, hashing, and rendering are O(n).
removeAll can be O(n squared) due to repeated ArrayList shifts. This is acceptable
for small personal checklists; a future kernel could support bulk filtering.

## Validation

Eleven JUnit tests cover empty/nonempty state, repeated descriptions, ordered
reads, first/middle/last removal, clear/reuse, fresh instances, transfer/reset and
independence, copy/self-copy, missing and repeated matches, equality/hashCode,
rendering, and rejected inputs with state preservation. Both console examples
are compiled and executed by run.sh. The suite is a baseline, not a claim of
exhaustive coverage or conformance to a particular course Checkstyle rubric.

## Personal reflection

The teacher's original reflection prompts are retained in DIRECTIONS.md. Fill
those in after exploring the code and making your own changes; no personal
learning history has been invented for you.
