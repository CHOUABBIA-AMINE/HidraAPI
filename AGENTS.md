# HidraAPI Agent Instructions

## Task governance

[HidraAPI functional delivery roadmap](doc/roadmap/ROADMAP.md) is the sole
active task source. The [archive index](doc/governance/ROADMAP_ARCHIVE.md)
preserves prior plans, decisions and verification evidence at their original
paths. Archived IDs, pointers, statuses, commit messages and exceptional
sequences grant no new execution authority. Applicable owner policies and
verified technical evidence retain their meaning.

## 1. Project Identity

| Field | Value |
|---|---|
| Project | HidraAPI |
| Product | Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Package root | `dz.sh.hidra` |
| Build tool | Maven |
| Language | Java |
| Java version | 21 |
| Main roadmap directory | `doc/roadmap` |
| Active roadmap | `doc/roadmap/ROADMAP.md` |
| Legacy roadmap/reference estate | `docs/` |

HidraAPI is the backend foundation for **Hidra**, a hydrocarbon intelligence platform focused on:

```text
Data
Risk
Analytics
Operational trust
Validation
Auditability
Industrial pipeline operations
```

---

## 2. Mandatory Source of Truth

Use `doc/roadmap/ROADMAP.md` for active task IDs, scope, sequencing, status and
acceptance. Use current source, runtime configuration, migrations and exact-SHA
tests and CI to establish implementation reality. Archived plans are evidence.
Route material scope or policy conflicts to the owner.

## 3. Task Execution Protocol

`Next` selects one eligible task after refreshing main, parent/tree, exact-SHA
CI and the applicable acceptance evidence. Before editing, confirm task scope,
dependencies, exact file allowlist, commit message and required validation.
A planned entry without this execution envelope does not authorize mutation.
Execute each selected task as one complete delivery. `Fail` addresses the
responsible failed CI scope before dependent work. Do not advance automatically,
create branches or PRs, release, deploy, or start numerical simulation without
the applicable authorization. Publish only to existing main with the
expected-head guard.

---

## 4. Git Commit Rules

Use the exact commit message from the roadmap for every roadmap code.

Example:

```text
chore(kernel): add kernel package skeleton
```

Do not invent alternative commit messages.

Do not combine multiple roadmap tasks in one commit.

Do not commit unrelated files.

---

## 5. Canonical Java Header

Every Java file must start with this exact header style.

The following fields must never change:

```text
@Author      : Abir MEDJERAB
@CreatedOn   : 2025-06-26
```

Canonical header:

```java
/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : <ClassName>
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : <Class|Interface|Enum|Record|PackageInfo>
 * @Layer       : <Bootstrap|Kernel|Platform|API|Application|Domain|Infrastructure|Test>
 * @Module      : <module-name>
 * @Package     : <actual-package-name>
 *
 * @Description : <one-sentence responsibility>
 *
 */
```

For test classes, use the same header and set an appropriate test layer, for example:

```text
@Layer       : Kernel Test
```

---

## 6. Naming Rules

Use:

```text
kernel
platform
modules
```

Do not create packages named:

```text
shared
sharedkernel
common
core
utils
helper
helpers
misc
```

The canonical base package is:

```text
dz.sh.hidra
```

Do not use:

```text
dz.sonatrach.*
dz.sh.trc.nghyflo.*
dz.sh.hidra.sharedkernel.*
dz.sh.hidra.common.*
```

---

## 7. Kernel-Specific Rules

Keep selected kernel work inside the admitted paths under
`src/main/java/dz/sh/hidra/kernel` and `src/test/java/dz/sh/hidra/kernel`.
Follow the dependency rules below. A change to another module requires its own
admitted scope. The roadmap governs selection of kernel work.

---

## 8. Kernel Dependency Rules

Kernel production code must not import:

```text
org.springframework.*
jakarta.persistence.*
org.hibernate.*
dz.sh.hidra.platform.*
dz.sh.hidra.modules.*
```

Kernel production code may use:

```text
java.*
java.time.*
java.util.*
```

Optional later only if justified:

```text
java.math.*
```

The kernel dependency direction must remain:

```text
kernel -> Java standard library only
platform -> kernel
modules -> kernel
```

Never:

```text
kernel -> platform
kernel -> modules
kernel -> Spring
kernel -> JPA
kernel -> Hibernate
```

---

## 9. Kernel Business Boundary Rules

The kernel must never contain:

```text
User
Employee
Role
Permission
Pipeline
Station
FlowReading
WorkflowInstance
Incident
AuditEvent
Controller
Service
Repository
JPA Entity
Spring Configuration
Security Configuration
Outbox Implementation
Database Migration
Business rule specific to one module
```

If a requested class represents a business concept, do not put it in `kernel`.

Stop and ask for clarification or recommend the correct module.

---

## 10. Platform-Specific Rules

The platform module is technical infrastructure only.

Platform code may later contain:

```text
configuration
exception handling
observability
security plumbing
persistence configuration
transaction support
event infrastructure
tenancy context
```

Platform must not own business concepts such as:

```text
User
Role
Permission
Employee
Pipeline
FlowReading
Incident
WorkflowInstance
Business approval rule
Business authorization meaning
```

Business meaning of users, roles, permissions, authorities, and groups belongs to the future:

```text
dz.sh.hidra.modules.identityaccess
```

---

## 11. Validation Rules

Run the validation commands specified by the roadmap task.

Common validation commands:

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
```

If the task specifies a narrower validation command, run that command.

If Maven cannot run, report the exact reason.

Do not invent validation results.

Do not say validation passed unless the command actually completed successfully.

### 11.1 Owner-approved CI-first execution — 2026-10-17

The owner explicitly directs that agents are not obliged to prepare a complete local environment
just to test code. GitHub workflows provide the authoritative test feedback after publication.

1. Run useful checks with tooling already available. Do not install/provision a complete Java,
   Maven, Docker, PostgreSQL or other service environment merely to reproduce hosted CI locally.
2. Missing local dependencies or skipped service tests are not, by themselves, blockers to
   committing the authorized, reviewed task scope. Report exactly what passed, failed or skipped;
   source inspection and skipped cases are never passing runtime evidence.
3. Commit the bounded changes directly to main using the existing expected-HEAD guard and
   registered commit message. Preserve file-scope controls, migration history and repository
   protections. This policy does not authorize unrelated work or knowingly conceal failures.
4. Let the applicable GitHub workflows execute the full verification. Confirm their creation,
   queueing or startup on the published SHA, report actual links/state, then stop without waiting
   for completion or automatically starting another task.
5. Use subsequent CI feedback to repair the responsible task before advancing dependent work.
   Keep implementation, CI acceptance, engineering acceptance and deployment readiness distinct.
6. Database-derived artifacts must remain truthful. If an actual migrated catalog is unavailable
   locally, preserve the existing dictionary, record its pending regeneration explicitly and use
   the hosted catalog artifact for the follow-up. Do not invent physical metadata or label an
   inferred schema as newly verified. Such missing local evidence does not prevent the initial
   CI-first commit; it remains an acceptance/follow-up obligation.

This owner instruction supersedes local-environment publication holds. It does not remove
required tests, weaken workflows or waive green-CI requirements for release/deployment.

---

### 11.2 One task verification step — 2026-11-13

Use one focused verification step per task. For a new or changed client workflow,
default to one synthetic client scenario that exercises the real entry point,
commits and retrieves the result, and checks the security or data-integrity
failures that materially affect that workflow. Reuse existing coverage. Add a
separate test only where the scenario cannot establish a required guarantee.

Run the applicable checks together through the existing exact-SHA CI workflow.
Use its overall result as the task's CI gate. Internal jobs remain diagnostic
detail. Keep required security, architecture, persistence and compatibility
checks applicable to the change. CI failures that block the client journey or
violate a required guarantee are defects to fix.

For T10 and future delivery tasks, verify restart across workflows once in the
registered integrated journey, unless restart behavior is itself part of the
feature or needed to establish a task-specific durability guarantee. Record
which evidence is pending and keep development software acceptance distinct
from engineering and operational qualification.

---

## 12. Roadmap Update Rules

Update task status and acceptance evidence only in `doc/roadmap/ROADMAP.md`.
Use PLANNED, IN PROGRESS, BLOCKED, COMPLETED, DEFERRED, or SKIPPED.
Include the exact implementation SHA, test and CI outcome and any remaining
gate for completion. Retain past evidence and identify blockers precisely.

---

## 13. File Creation Rules

Create only the files explicitly listed in the roadmap task.

Do not create extra files because they seem useful.

Do not implement later roadmap files early.

Do not create placeholder business code.

Do not create empty directories using `.gitkeep` unless the roadmap explicitly asks for it.

Prefer `package-info.java` for package structure when required by the roadmap.

---

## 14. Lombok Rules

Lombok is allowed in HidraAPI, but use it carefully.

Allowed:

```text
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
```

Restricted:

```text
@Builder
@EqualsAndHashCode
@ToString
```

Avoid unless explicitly approved:

```text
@Data
@Setter
@AllArgsConstructor
```

For kernel, prefer Java records and do not use Lombok unless the roadmap explicitly asks for it.

---

## 15. Testing Rules

For kernel tests:

- Use JUnit 5.
- Use AssertJ if available.
- Do not use `@SpringBootTest`.
- Do not start Spring context.
- Do not use database.
- Do not use Testcontainers.
- Keep tests fast and deterministic.

For architecture tests:

- Use ArchUnit only if the dependency exists in `pom.xml`.
- If the dependency is missing, mark the roadmap task as blocked and report it.

---

## 16. Security and Secrets Rules

Never commit:

```text
passwords
tokens
API keys
database production credentials
private certificates
secret keys
personal access tokens
```

Production configuration must use environment variables or external secret management.

---

## 17. Final Response Format

After every task, respond with:

```text
Current step:
- <commit code and task name>

Files created:
- <file list>

Files updated:
- <file list>

Implementation performed:
- <short summary>

Validation:
- <commands run>
- <result>

Commit:
- <commit hash or reason no commit was created>

Remaining risks:
- <risks or none>

Next recommended task:
- <next commit code from roadmap>
```

Do not omit validation information.

Do not claim a commit exists if it was not created.

---

## 18. Current task selection

Read the active roadmap. The first candidate is T01, subject to separate
selection and the complete execution envelope.

---

## 19. Release and Versioning Rules

HidraAPI uses **Semantic Versioning 2.0.0** for the project release line.

Release/versioning sources of truth:

```text
VERSIONS.md       -> authoritative release history and milestone traceability
pom.xml           -> actual Maven project version
PROJECT_STATE.md  -> current release/development state
roadmaps          -> capability/milestone completion evidence
```

Mandatory rules:

1. **Never change the project version arbitrarily.**
   A version change must be justified by a logical roadmap/product milestone, compatibility impact,
   or a release-management transition.

2. **Do not create one release version per commit, PR, issue, or roadmap task.**
   Release boundaries represent coherent capability or stabilization milestones.

3. **Use pre-1.0 SemVer deliberately.**
   - increment the **minor** version for a substantial capability/architecture milestone or
     compatibility-changing pre-1.0 release;
   - increment the **patch** version only for a backward-compatible correction to an already
     released line when no new capability boundary is introduced;
   - use prerelease identifiers such as `-alpha.N`, `-beta.N`, or `-rc.N` only when the
     milestone is intentionally a prerelease.

4. **Before choosing a release version, inspect:**
   - current `pom.xml`;
   - `VERSIONS.md`;
   - `PROJECT_STATE.md`;
   - the applicable completed roadmaps and exact completion commits;
   - current `main`, CI, tags, and GitHub Releases.

5. **Keep milestone anchors distinct from release commits.**
   A roadmap completion commit may be the semantic milestone anchor even when its historical
   `pom.xml` contains an older version. The formal release tag must point to a release-alignment
   commit whose `pom.xml` contains the matching release version.

6. **A release-alignment commit must contain, at minimum:**
   - the intended release version in `pom.xml`;
   - the corresponding release/milestone record in `VERSIONS.md`;
   - no unrelated feature work.

7. **A release candidate is not release-ready until exact-SHA verification is green.**
   Run the repository-required verification, normally including:

   ```bash
   ./mvnw -B -q clean verify
   ```

   and require the repository CI/OpenAPI publication gates applicable to that release.

8. **Git release tag format is:**

   ```text
   v<SemVer>
   ```

   Example:

   ```text
   v0.6.0
   ```

9. **Never tag an older milestone commit whose POM contains a different project version.**
   The tag must resolve to the exact verified release-alignment commit.

10. **Prefer signed annotated tags when the release environment supports verified signing.**
    If GitHub Web is explicitly used to create a lightweight/unsigned tag, record that fact and
    never claim the tag is GPG/SSH signed.

11. **Never rewrite, move, delete, or recreate a published release tag without explicit
    release-manager/user authorization.**

12. **Do not publish a GitHub Release automatically.**
    Release publication, tag creation, or tag replacement requires explicit user authorization.

13. **After a formal release, advance `main` to the next development line.**
    For the normal pre-1.0 minor-development flow:

    ```text
    released: 0.5.0
    next main: 0.6.0-SNAPSHOT
    ```

    Do not advance to the next `-SNAPSHOT` until the release tag/release exists or the user
    explicitly directs otherwise.

14. **A release is considered operationally complete only when:**
    - the release-alignment commit exists;
    - exact-SHA CI is green;
    - the release tag exists on that exact commit;
    - the GitHub Release exists when required;
    - `VERSIONS.md` records the milestone and release traceability;
    - `PROJECT_STATE.md` records the last release and current development line;
    - `main` has advanced to the intended next `-SNAPSHOT` version.

15. **Do not infer release history from POM numbers alone.**
    Reconstruct or validate release history from real Git commits, roadmaps, tags/releases, and
    `VERSIONS.md`. Never invent commit hashes, tags, CI results, or release dates.

Prior dated task selections and owner decisions remain available in the [pre-adoption instructions](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/85ac6d387ef5cf1421dd22a89b9c56079d665e1e/AGENTS.md) and the roadmap archive.
