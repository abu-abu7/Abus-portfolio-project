#!/bin/sh
set -eu
cd "$(dirname "$0")"
mkdir -p bin/classes bin/test-classes
find src -name '*.java' > bin/sources.txt
find test -name '*.java' > bin/tests.txt
javac --release 21 -cp 'lib/*' -d bin/classes @bin/sources.txt
javac --release 21 -cp 'bin/classes:lib/*' -d bin/test-classes @bin/tests.txt
java -ea -cp 'bin/test-classes:bin/classes:lib/*' org.junit.runner.JUnitCore \
  components.tasklist.TaskList1LTest components.tasklist.TaskListTest \
  components.tasklist.TaskListCoverageTest examples.MaintenanceChecklistTest
java -ea -cp 'bin/classes:lib/*' examples.StudyPlanner
java -ea -cp 'bin/classes:lib/*' examples.MaintenanceChecklist
