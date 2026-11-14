#!/bin/sh
set -eu
cd "$(dirname "$0")"
mkdir -p bin
find src test -name '*.java' > bin/sources.txt
javac --release 21 -cp 'lib/*' -d bin @bin/sources.txt
java -ea -cp 'bin:lib/*' org.junit.runner.JUnitCore components.tasklist.TaskList1LTest components.tasklist.TaskListTest
java -ea -cp 'bin:lib/*' examples.StudyPlanner
java -ea -cp 'bin:lib/*' examples.MaintenanceChecklist
