# TaskList

TaskList stores task descriptions in order. It supports adding, removing,
searching, copying, and transferring tasks. The component follows the OSU
kernel, enhanced interface, secondary class, and implementation structure.

## Running the project

Open the folder in VS Code and use JDK 21 or newer. In the terminal, run:

```sh
./run.sh
```

This compiles the project, runs the 23 JUnit tests, and runs both examples.
The libraries needed for this local copy are already in `lib`.

## Files

- `src/components/tasklist`: the four component files.
- `src/examples`: study planner and maintenance checklist examples.
- `test`: component and example tests.
- `doc/DESIGN.md`: component model and layers.
- `doc/TEST_PLAN.md`: test cases and results.
- `doc/REFLECTION.md`: draft reflection answers to review.

The tests pass, and the bundled OSU Checkstyle check reports no warnings.
Coverage results and instructions are in the test plan.

## Dependencies

JDK 21, OSU `components.jar`, JUnit 4.13.2, and Hamcrest Core 1.3.
The jar files are kept in `lib` and are ignored by Git.
For a fresh clone, download them with:

```sh
mkdir -p lib
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
