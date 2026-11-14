# Test plan

## Approach

Test empty, single-task, and longer lists. Include repeated descriptions and
check both returned values and the whole list after changes. Read-only methods
must leave the list unchanged.

## Cases

| Methods | Cases checked |
| --- | --- |
| Constructor, size | Empty list and counts after adding or removing tasks |
| add | New and repeated tasks, Unicode, preserved spaces, null, and blank text |
| task, remove | First, middle, and last positions; invalid indices; remaining task order |
| newInstance, clear | Empty results, reuse, and independence from other lists |
| transferFrom | Empty and nonempty source/receiver combinations; source cleared; invalid sources |
| contains | Matches at each position, no match, case differences, empty search, and null |
| copyFrom | Replacement, empty source, self-copy, null, and independent copies |
| removeAll | No matches, one match, all matches, adjacent duplicates, and remaining order |
| equals, hashCode | Equal values, different order or size, null, unrelated types, and equal hashes |
| toString | Empty and nonempty output with correct brackets and separators |
| MaintenanceChecklist | Step order, independent jobs, copies of templates and snapshots, and invalid operations |

TaskListCoverageTest also uses a small LinkedList-backed test implementation.
This checks that secondary methods work without depending on the ArrayList
representation. Both example programs are run by the tests.

## Results — November 14, 2026

- 23 JUnit tests pass.
- The bundled OSU Checkstyle configuration reports no warnings.
- JavaDoc generates without warnings.
- JaCoCo reports 58/58 component lines and 36/36 component branches covered.
- Both examples have full line coverage: 32/32 lines.

Coverage counts production code, not test classes. Full coverage means every
reported line and branch ran; it does not prove every possible case is correct.

## Run the tests

```sh
./run.sh
```

## Style check

Set CHECKSTYLE_JAR to the path of a Checkstyle executable jar:

```sh
java -jar "$CHECKSTYLE_JAR" -c .vscode/osu-cse-checkstyle-config.xml src test
```

The configuration reports warnings, so check the output as well as the exit code.

## Coverage check

Set JACOCO_AGENT to the agent jar and JACOCO_CLI to the executable reporting jar.
Coverage was measured with JaCoCo agent 0.8.15 and CLI 0.8.13.

```sh
./run.sh
java -javaagent:"$JACOCO_AGENT"=destfile=bin/coverage.exec,append=false \
  -ea -cp 'bin/test-classes:bin/classes:lib/*' org.junit.runner.JUnitCore \
  components.tasklist.TaskList1LTest components.tasklist.TaskListTest \
  components.tasklist.TaskListCoverageTest examples.MaintenanceChecklistTest
java -jar "$JACOCO_CLI" report bin/coverage.exec --classfiles bin/classes \
  --sourcefiles src --html bin/coverage --xml bin/coverage.xml
```

Open bin/coverage/index.html for the report. JavaDoc generation instructions
are in README.md.
