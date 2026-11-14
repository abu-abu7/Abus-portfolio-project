# TaskList — Personal Java Portfolio Project

A reusable ordered task-list component built using the OSU layered component
structure. This is a personal practice project based on the original template.
It is a Java library with console examples.

## Run in VS Code

Open this folder in VS Code. Install the recommended Java extensions when
prompted. Use JDK 21 and the libraries listed below.

In the integrated terminal run:

```sh
./run.sh
```

This compiles the component and tests, runs JUnit, then runs both examples.
You can also open either example and click **Run** above its `main` method.

## Structure

```text
Standard<TaskList>
    └── TaskListKernel       minimal operations: add, remove, task, size
          └── TaskList       enhanced interface: contains, copyFrom, removeAll
                └── TaskListSecondary   representation-independent operations
                      └── TaskList1L    ArrayList-backed kernel
```

- `src/components/tasklist/`: interfaces and implementation.
- `src/examples/StudyPlanner.java`: a one-off study plan.
- `src/examples/MaintenanceChecklist.java`: reusable templates, independent copies,
  and ownership transfer between checklists.
- `test/components/tasklist/`: kernel/Standard and enhanced/common-method tests.
- `doc/DESIGN.md`: model, contracts, tradeoffs, and testing notes.
- `doc/DIRECTIONS.md`: original teacher directions, retained for reference.

## Dependencies

JDK 21, OSU `components.jar`, JUnit 4.13.2, and Hamcrest Core 1.3.
Downloaded jars live in ignored `lib/`; they are present in this local checkout.
To restore them in a fresh checkout:

```sh
curl -fL https://cse22x1.engineering.osu.edu/common/components.jar -o lib/components.jar
curl -fL https://repo.maven.apache.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar -o lib/junit-4.13.2.jar
curl -fL https://repo.maven.apache.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar -o lib/hamcrest-core-1.3.jar
```

## API documentation

```sh
javadoc -quiet -classpath 'lib/*' -d bin/docs \
  -tag 'mathmodel:a:Mathematical model:' -tag 'initially:a:Initially:' \
  -tag 'requires:a:Requires:' -tag 'ensures:a:Ensures:' \
  -tag 'updates:a:Updates:' -tag 'replaces:a:Replaces:' \
  -tag 'clears:a:Clears:' \
  -tag 'convention:a:Representation invariant:' \
  -tag 'correspondence:a:Abstraction relation:' \
  src/components/tasklist/*.java
```

Work is on the `portfolio-project` branch; the original `main` is preserved.
Original assignment reflections remain prompts for your own experience.

## Course policy and project status

The original assignment's AI policy in [DIRECTIONS.md](doc/DIRECTIONS.md)
prohibits generative AI assistance. This project was developed with AI assistance
for personal practice and does not satisfy that restriction for a course
submission. Its layered design follows the structure described in the template;
this is not a claim of instructor approval or full course-style compliance.
The original assignment documents and policy are retained for reference.
