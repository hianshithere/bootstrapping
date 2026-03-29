# Agent Skill: Java 8+ Best Practices

Purpose
-------
Provide a concise, reusable workflow and checklist for producing high-quality Java 8+ code that follows industry standards and team-friendly conventions. This skill is workspace-scoped and intended to guide automated agents and developers when reviewing, writing, or refactoring Java code.

Scope
-----
- Java 8 and later language features (streams, lambdas, Optional, method references).
- Common JVM concerns: immutability, concurrency, resource management, performance.
- Code quality: style, testing, logging, API design, and documentation.

When to Use
-----------
- Creating new Java modules or classes.
- Refactoring legacy code to modern Java idioms.
- Writing code review comments or automated review rules.
- Generating examples, templates, or coding tasks.

High-level Workflow (Step-by-step)
---------------------------------
1. Define intent and API surface
   - Clarify responsibility, inputs/outputs, and error modes.
   - Prefer small interfaces with single responsibility.

2. Choose types and immutability
   - Prefer `final` fields and immutable value classes (use builders for many fields).
   - Prefer `Optional<T>` for optional returns; avoid `Optional` as a field.

3. Use modern Java constructs
   - Prefer Streams and method references for collection processing.
   - Use `java.time` APIs instead of `Date`/`Calendar`.
   - Use functional interfaces and lambdas for short behavior passing.

4. Handle nulls and errors explicitly
   - Fail-fast with `Objects.requireNonNull` for required params.
   - Use checked exceptions for expected recoverable failures; unchecked for programming errors.

5. Resource & concurrency safety
   - Use try-with-resources for IO and AutoCloseable.
   - Prefer immutable objects and thread-safe collections for concurrency.
   - For async/executor usage, prefer `CompletableFuture` and provide explicit Executor injection.

6. Logging and observability
   - Use structured logging with slf4j; avoid System.out.println.
   - Log at appropriate levels and include contextual identifiers.

7. Tests and quality gates
   - Add unit tests covering happy path, edge cases, and error handling.
   - Write focused integration tests for external systems.
   - Ensure static analysis, formatting, and CI checks pass before merging.

Testing Conventions
-------------------
- Arrange-Act-Assert (AAA): structure each unit test with three clear sections:
   1. Arrange — build inputs, mocks, and system under test.
   2. Act — execute the behavior being tested.
 3. Assert — verify outcomes and side-effects.
- Test naming: use the `should_do_this_when_this_happens()` pattern for test method names. Examples:
   - `should_return_empty_list_when_no_users()`
   - `should_throw_InvalidArgumentException_when_input_is_null()`
- Keep tests small, focused on a single behavior, and avoid multiple assertions covering unrelated behaviour in a single test.
- Provide a short comment or `@DisplayName` when intent is not obvious from the name.
- Enforcement: add examples to test templates, and consider a CI lint rule or code review checklist to validate AAA structure and naming.

8. Documentation & API contract
   - Add concise Javadoc to public APIs explaining behavior and thread-safety.
   - Document side-effects and performance expectations.

Decision Points and Branching Logic
----------------------------------
- Prefer immutability unless mutability yields clear performance or API benefits.
- Use `Stream` vs imperative loops: use streams for declarative transformations; avoid streams when they reduce readability or introduce boxing.
- Checked vs unchecked exceptions: use checked when callers can reasonably recover; otherwise use unchecked.
- Library choices: prefer JDK-first; add third-party libs only when they provide substantial, maintained benefits.

Quality Criteria / Completion Checks
-----------------------------------
- Code compiles with project JDK and build tool.
- Unit tests cover > minimal logical branches for new code; critical paths have assertions.
- No new FindBugs/SpotBugs or major Sonar issues introduced.
- CI pipeline green: build, test, static analysis, formatting.
- Public APIs document thread-safety, nullability, and side-effects.

Checklist for Pull Requests
--------------------------
- Title + short summary of change and motivation.
- List of files changed and key design decisions.
- Tests added/updated and how to run them.
- Any migration notes (schema changes, config additions).

Example Prompts to Invoke This Skill
-----------------------------------
- "Refactor `UserService` to use Java 8 streams and be immutable-aware."
- "Review this PR for Java 8 best practices and list violations."
- "Create a small, thread-safe DTO class for the vehicles API using builder pattern."

Ambiguities & Clarifying Questions (for iteration)
-------------------------------------------------
- Is the skill workspace-scoped (project conventions) or personal (dev preferences)?
- Any existing style guide or formatter to enforce (Checkstyle, Spotless, Google Java Format)?
- Preferred testing frameworks (JUnit 4 vs JUnit 5, Mockito version)?

Suggested Next Customizations
----------------------------
- Add a `PR_TEMPLATE.md` enforcing the checklist above.
- Add a small set of automated linters/configs: `spotless`, `checkstyle` rules tuned to project.
- Create a `TEMPLATES/` folder with immutable DTO and service skeletons.

Maintenance Notes
-----------------
- Keep this skill updated with new Java LTS features and team-specific conventions.
- When team adopts a formatter or linter, add precise rules and examples here.

Authored: Generated as workspace SKILL for Java 8+ best practices.
