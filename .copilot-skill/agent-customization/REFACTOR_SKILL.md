# Agent Skill: Refactor — SOLID, Clean Code & Patterns

Purpose
-------
Provide a practical, opinionated refactoring workflow that applies SOLID, Clean Code, and common refactor patterns to improve design, readability, and maintainability. This is a workspace-scoped skill for automated agents and developers performing code improvements.

When to Use
-----------
- Before merging significant features that increase complexity.
- During code review when a pull request shows design or readability issues.
- Periodic codebase improvements (tech debt sprints).

Core Principles
---------------
- SOLID: single responsibility, open/closed, Liskov substitution, interface segregation, dependency inversion.
- Clean Code: meaningful names, small functions, expressive tests, fewer side-effects, prefer immutability when safe.
- YAGNI & KISS: avoid premature generalization; prefer simple, obvious designs.
- Law of Demeter: minimize coupling by depending on narrow interfaces.

Refactor Workflow (practical steps)
---------------------------------
1. Identify smell and intent
   - Name the problem (e.g., long method, large class, duplicated logic, feature envy).
   - State the refactor goal and measurable success criteria.

2. Add or confirm tests (safety net)
   - Ensure targeted unit tests exist, add focused tests if necessary using the AAA pattern and `should_do_this_when_this_happens()` naming.

3. Small, incremental changes
   - Make one small change per commit (rename, extract method/class, introduce interface).
   - Run tests after each commit.

4. Apply patterns guided by intent
   - Extract Method / Inline Method for long/short methods.
   - Extract Class when a class has multiple responsibilities.
   - Replace Conditionals with Polymorphism when branching hides intent.
   - Introduce Adapter or Facade to decouple third-party code.
   - Apply Dependency Injection / Inversion to remove concrete dependencies.

5. Simplify and document
   - Remove dead code and reduce public API surface.
   - Add concise Javadoc for public behavior and thread-safety expectations.

6. Verify and tidy
   - Run full test suite and static analysis. Re-run benchmarks if performance-sensitive.
   - Format and lint code (Spotless/Google Java Format, Checkstyle rules where applicable).

Decision Rules (quick guidance)
-------------------------------
- Prefer small cohesive classes over large utility classes.
- Favor composition over inheritance unless polymorphism is required.
- Use interfaces to define behavior contracts; avoid interfaces with many unrelated methods.
- Replace magic numbers and strings with named constants or value objects.

Quality Criteria / Done Conditions
---------------------------------
- All tests pass and new changes have tests covering behavior changes.
- Code is easier to read (smaller methods, clearer names) — subjective review by two peers.
- No new critical static-analysis issues introduced.
- Performance is unchanged or improved for hot paths (measured when relevant).

Common Refactor Recipes (examples)
----------------------------------
- Long Method -> Extract Method: pick a coherent block, give it a descriptive name, and replace the block with a call.
- Large Class -> Extract Class: identify a responsibility and move related fields and methods into a new class.
- Duplication -> Extract Utility or Shared Service: factor duplicated logic into a single place and test it.
- Coupled Code -> Introduce Interface + DI: define an interface, modify dependents to use it, provide implementations via DI.

Tools & Automation
------------------
- Static analysis: SpotBugs, Sonar, Checkstyle (project-specific rules).
- Formatting: Spotless or Google Java Format.
- Refactor helpers: IDE refactor tools (IntelliJ) and automated codemods for mechanical renames.
- Test tools: JUnit 5, AssertJ, Mockito for behavior and safety.

Organize imports
----------------
- Keep imports minimal and ordered: remove unused imports and group/sort remaining imports consistently (for example: `java.*`, `javax.*`, `org.*`, `com.*`).
- Prefer explicit imports over star imports in most codebases; use project policy to allow wildcard imports only when many types from the same package are used.
- Automation options:
   - IDE: use IntelliJ `Code -> Optimize Imports` or Eclipse `Source -> Organize Imports` as quick local actions.
   - Build-time: add `spotless` with an `eclipse` or `google` formatter, or use `maven-checkstyle-plugin` with `ImportOrder` rules to fail the build on import-order or unused-import issues.
   - Example command to run Spotless (if configured): `mvn spotless:apply`.
- Agent behaviour: when performing refactors the agent should run import-organization after edits (IDE-style optimize or `spotless:apply`) and include the import changes as part of the same refactor commit.

How the agent should apply this skill
------------------------------------
1. Ask for the target scope (single file, package, module, or PR).
2. Run quick static analysis to find top smells in the scope.
3. Propose an ordered list of small refactor tasks (highest impact first).
4. For each task: create a draft change (single responsibility), run tests, and add a clear commit message describing intent and risk.
5. Present a summary of changes and metrics (LOC changed, tests added, static-analysis delta).

Example Prompts
---------------
- "Refactor the `UserService` package to follow SOLID and remove duplicated code."
- "Suggest small, incremental refactors for this PR to reduce method complexity." 
- "Apply Extract Method refactor to the long method in `VehicleDataLoaderService`."

Ambiguities & Clarifying Questions
---------------------------------
- What is the acceptable test coverage threshold for this change?
- Is preserving behavior more important than simplifying implementation for this refactor?
- Are there performance constraints for the affected module?

Maintenance
-----------
- Update this skill when the team adopts new conventions or tools (formatters, linters, dependency upgrades).
- Add code-mod examples for large, repeated mechanical refactors.

Authored: Workspace refactor skill for SOLID, Clean Code, and common refactor recipes.
