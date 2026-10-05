# HidraAPI Agent Instructions

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
| Ultimate roadmap | `doc/roadmap/ULTIMATE_ROADMAP.md` |
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

For platform-finalization work, always read:

```text
doc/roadmap/ULTIMATE_ROADMAP.md
```

The Ultimate Roadmap is the execution memory and single source of truth for platform-finalization sequencing, task codes, phase gates, and target-architecture disposition.

The existing `docs/` tree is retained as legacy/reference/evidence material. Legacy roadmaps, semantic reviews, ADRs, audits, and data-definition documents may provide task detail and historical evidence, but they do not override the Ultimate Roadmap for platform-finalization execution.

For implementation reality, current production source, runtime configuration, Flyway migrations, architecture tests, and exact-head CI evidence take precedence over stale documentation claims.

For semantic-remediation work, revalidate the legacy HMR/HMSR obligation against current source before mutation. A legacy HMR may execute only when its work is represented by, or explicitly admitted into, the Ultimate Roadmap.

Do not rely only on chat instructions.

If a chat instruction conflicts with the Ultimate Roadmap, stop and report the conflict unless the user explicitly asks to update the Ultimate Roadmap.

---

## 2.1 Platform Finalization Task Codes

Ultimate Roadmap tasks use stable `HPR-<phase>-<number>` codes, for example:

```text
HPR-P0-001
HPR-P1-001
HPR-P2-001
```

Use the exact commit message registered for each HPR code.

Do not execute a later HPR code automatically.

Legacy task codes such as `HMR-*` remain evidentiary identifiers after supersession; they do not independently select new platform-finalization execution.

---

## 3. Task Execution Protocol

### 3.1 Default execution

Execute exactly **one roadmap commit code** per task unless the applicable roadmap explicitly
registers a **batch execution envelope**.

Examples of normal single-task execution:

```text
KER-002
KER-003
HMR-021
```

Do not continue automatically to an unregistered next roadmap code.

### 3.2 Batch execution envelopes

A roadmap may explicitly register a remediation batch containing **2 to 4 compatible HMR codes**.
A batch is one user execution task but **not one semantic task and not one squashed commit**.

Every HMR inside a batch must retain independently:

```text
source HMSR review
HMSR obligations
write allowlist
migration filename/authorization
focused validation target
roadmap status
exact commit message
individual Git commit
```

Batch rules:

1. Batch only HMRs that are dependency-safe and explicitly registered by the roadmap.
2. Preserve roadmap order unless the roadmap records evidence authorizing another order.
3. Perform one exact-head CI/status check before the batch. The batch may start only from a green
   production head unless the user explicitly authorizes a documentation-only protocol change.
4. Recover live evidence for **each HMR** before preparing its commit; do not rely on the batch
   summary as a substitute for the HMR source review.
5. Create one commit per HMR, using that HMR's exact commit message and only its authorized files.
6. Chain the HMR commits in order, then advance the branch once to the final batch commit when the
   connector supports atomic ref advancement.
7. Perform one post-batch CI/status observation for the final head.
8. If post-batch CI fails, stop the batch sequence. Diagnose the responsible HMR(s), repair only
   those HMRs, and do not start another batch until the repaired head is green.
9. If an HMR reveals an unregistered prerequisite, SCC complication, owner-contract gap,
   cross-module lifecycle dependency, migration-order conflict, or materially larger semantic
   redesign, split it out and stop before mutating that HMR.
10. High-risk catalog redesign, lifecycle orchestration, unresolved cross-module ownership and SCC
    work remain solo by default unless the roadmap explicitly authorizes the combined execution.

After completing a single task or batch:

1. Update every executed roadmap status independently.
2. Record validation commands/results when requested.
3. Preserve one exact commit per roadmap code.
4. Leave the branch at the final verified task/batch head.
5. Report every commit hash, changed-file scope, validation/CI result, and next registered execution.

---

## 4. Git Commit Rules

Use the exact commit message from the roadmap for every roadmap code.

Example:

```text
chore(kernel): add kernel package skeleton
```

Do not invent alternative commit messages.

Do not squash multiple roadmap tasks together, including tasks executed in a batch envelope.

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

When executing a `KER-*` task, only modify files allowed by:

```text
docs/roadmap/kernel.md
```

Allowed kernel production root:

```text
src/main/java/dz/sh/hidra/kernel
```

Allowed kernel test root:

```text
src/test/java/dz/sh/hidra/kernel
```

Allowed roadmap file:

```text
docs/roadmap/kernel.md
```

Do not modify or create:

```text
src/main/java/dz/sh/hidra/platform/**
src/main/java/dz/sh/hidra/modules/**
src/main/java/dz/sh/hidra/shared/**
src/main/java/dz/sh/hidra/sharedkernel/**
src/main/java/dz/sh/hidra/common/**
src/main/java/dz/sh/hidra/core/**
src/main/java/dz/sh/hidra/utils/**
```

unless the user explicitly asks for a non-kernel task and an appropriate roadmap exists.

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

---

## 12. Roadmap Update Rules

After every completed roadmap task, update the roadmap status table.

Use one of these statuses:

```text
Planned
In Progress
Completed
Blocked
Skipped
```

When marking a task completed, include a short note:

```text
Completed | Package skeleton created; mvn -q -DskipTests compile passed
```

When blocked, include the reason:

```text
Blocked | ArchUnit dependency missing from pom.xml
```

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

## 18. Current Kernel Starting Point

Kernel work must begin with:

```text
KER-002 — chore(kernel): add kernel package skeleton
```

Only after `KER-002` is reviewed should the agent continue with:

```text
KER-003 — feat(kernel): add domain exception contracts
```

Do not implement `KER-003` or later tasks during `KER-002`.

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

