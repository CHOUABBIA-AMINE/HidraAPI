# HidraAPI Agent Instructions

## Canonical task-register navigation — owner-approved 2026-10-18

`doc/roadmap/ULTIMATE_TASKS.md` is the compact task index and execution pointer.
Read the selected task's full definition and append technical evidence in its owning
`doc/roadmap/TASKS_HPS-XXX.md` file. The index links all25 phase files and preserves
all921 legacy `#hapi-XXX` entry points. Registered governance writes to the former
monolithic task file follow the same task into its phase file; this relocation adds
no runtime scope. ROADMAP/PHASES remain the frozen registry and status baseline.
`ULTIMATE_TASKS_BASELINE.md` and `ULTIMATE_TASKS_EVIDENCE.md` retain immutable
historical payloads; former execution pointers there do not select current work.
Keep task detail out of the index. The documentation validator checks phase/task
membership, table statuses/targets, compatibility links, archive integrity and file
size bounds. This owner-approved layout migration changes no task ID or status.

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

### 3.3 Owner execution rule — never split a registered task (2026-11-01)

**Binding for future selections:** execute each registered roadmap task as **one
complete, indivisible delivery**. Do not decompose a task into A/B/C packages,
S0/S1 stages, sub-stages, serial preflights, individual test commits, or
repeated small `Next` steps. The single authorized task scope must contain
its necessary Domain, Application, owner/public contracts, adapters, database,
authorization, REST, tests and acceptance criteria. Planning and source review
within the same task are allowed, but are not independent delivery stages.
Size and register that whole scope before beginning implementation.

**Failures and blockers:** CI-triggered defect fixes may require additional
commits using the same registered task and its approved repair scope; these
are repairs, **not** new stages. Never split a task to work around failed CI
or a missing owner/transaction/security decision. If a blocker makes the
whole task unsafe or impossible to deliver, STOP, report the evidence and
request an owner decision on the whole task. Existing separately registered
roadmap codes remain independent tasks. Section 3.2 batch handling combines
independent codes; its split-out rule does not permit subdividing one code.

**One-time owner-approved exception:** only the existing proposed **flat**
S0–S7 sequence may be followed: S0 exact approval-policy/technical-ledger
registration; S1 immutable characteristic-link approval model; S2 Workflow
target and final-decision integration; S3 original-transaction approval
authorization/storage; S4 approved-link REST and native qualification;
S5 HAPI-602 software acceptance; S6 already registered HAPI-603 consumer
qualification; S7 already registered HAPI-598 integrated closure. S0–S5
are the sole permitted division of HAPI-602, **not** new roadmap task IDs.
Every one of S0–S7 is indivisible: **no substages** (including S1-A,
S1.1, B/C or further planning/test subdivisions). Owner acceptance of the
flat sequence does not by itself admit proposed commit messages, exact
source/DDL paths or a business approval policy. S0 must record exact
decisions and executable ledgers before S1–S5 can run. This governance
change does not execute S0, advance any later task, change status/dependencies,
waive HAPI-601 acceptance gaps, or authorize simulation/P3, HidraWEB,
deployment or SITE acceptance.

This instruction supersedes older discretionary single-task splitting
practices for future work without rewriting historical package evidence.

### 3.4 Final official S0–S7 stage list — owner freeze (2026-11-02)

**Binding stage-register freeze:** the owner reconfirms the sole official, flat,
unchanged sequence below. No added stage, renamed stage, renumbering, reordering,
merger, parallel stage, A/B/C package, separate CI-preflight stage or expansion
of a stage's registered objective is accepted under the current authorization.
A blocked criterion is recorded as a blocker *inside its existing registered
task*, never turned into a newly invented step.

1. **S0** — HAPI-602 approval policy and exact technical registration.
2. **S1** — HAPI-602 immutable characteristic-link approval domain.
3. **S2** — HAPI-602 genuine Workflow target and human approval evidence.
4. **S3** — HAPI-602 protected original-transaction approval persistence and Identity authorization.
5. **S4** — HAPI-602 approval REST API and configured native JWT/PostgreSQL qualification.
6. **S5** — HAPI-602 independent development-software acceptance.
7. **S6** — HAPI-603 / HPR-P25-054E installed-equipment consumer qualification.
8. **S7** — HAPI-598 / HPR-P25-054 integrated five-child closure.

**Status is not frozen or fabricated.** At evidence head
`f0ae75a066498de7eee53a17a93cb99ce4b56651`, HAPI-602 is
VERIFIED / COMPLETED (S0–S5); HAPI-603 S6 is IMPLEMENTED / IN_PROGRESS;
HAPI-598 S7 is PARTIAL / BLOCKED. Independent manufacturer HAPI-601 remains
IMPLEMENTED / IN_PROGRESS, with its accepted software and external assurance
limitations; it is not a ninth stage. Exact-head [Full CI #1080](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41523276539)
and [Documentation #856](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41523277117)
SUCCEEDED, but green CI does not close S6 or qualify S7.

**Permitted without changing the frozen list:** same-task `Fail` repairs within
authorized scope, execution of the still-unfinished *whole* S6 task,
reconciliation/acceptance within S7 only after prerequisites, append-only
evidence and legitimately justified status-cell updates under existing rules.
`Next` selects no extra stage. No new task ID, scope widening, HAPI-601
assurance waiver, frontend/SITE acceptance or numerical/P3 execution follows
from this freeze. A contrary future instruction would require an **explicit
new owner governance decision**; this freeze supplies no such permission.
It is a repository governance rule, **not** a claim of GitHub branch/ruleset
write protection or inability to change code.

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



## Owner-approved six-module foundation selection — 2026-11-03

The owner approved `doc/roadmap/FOUNDATION_PRODUCTION_ROADMAP.md` and its P0 registration. This is an explicit bounded sequencing and membership amendment: preserve the original921 tasks and25 phases; append HAPI-922–928 and corresponding compatibility links/rows, with the manifest expecting exactly928 tasks. Do not relax any validation or rewrite historical archives/IDs/statuses. The completed historical S0–S7 sequence remains unchanged and supplies no S8.

For this milestone, execute the supplement's minimum Identity, Organization, Party, Topology, Configuration and Workflow journeys before enhancements. Existing HAPI-605 and HAPI-604/606–609 statuses remain unchanged; their broader baseline/freeze/exchange objectives are not prerequisites for the admitted minimum. Reuse accepted graph, station/equipment, authentication and Workflow implementation; retain all native authority, version/history and concurrency guarantees. No numerical/P3, live OT, deployment/release or supplier/SITE acceptance is selected.

P0 is HAPI-922, exact message `docs(roadmap): register six-module functional foundation`, governance only. After exact-P0 documentation CI succeeds, the next separately selected whole task is HAPI-923/B1, `feat(identity): complete foundation user access lifecycle`. The ten implementation deliveries and final acceptance remain distinct registered/dependency-gated scopes, not automatic authorization to execute them all. Frontend handoff IDs HWEB-F11–F15 are reserved in the supplement; HidraWEB canonical adoption occurs in its selected whole delivery. Existing HWEB-F01–F10 and all prior mappings remain unchanged.

Only this bounded supplement supersedes older foundation sequencing pointers. Preserve the original no-substage, direct-main expected-head, CI-first, one-task/stop rules. Current source and exact-SHA test/artifact evidence establish implementation reality; this document does not promote an unfinished six-module workflow.

## Owner-approved standalone Identity refresh-token registration — 2026-11-08

The owner explicitly selected the structural addition of **HAPI-929** to the existing **HPS-001** phase, bringing the canonical registry to **929 tasks in 25 phases**. Prior historical 921/928 statements remain historical; HAPI-001–928 rows, task evidence, immutable archives and old CI acceptance remain unchanged. The owning source of technical scope, exact full implementation message, tests and prerequisites is [HAPI-929](doc/roadmap/TASKS_HPS-001.md#hapi-929). It is **NOT_IMPLEMENTED / REGISTERED_PENDING** after registration, not a shipped API. Execute the whole task once separately selected and verified; no A/B/C or serial stages. This amendment authorizes no release, live OT, numerical/P3, frontend work or bypass of native Identity provenance.

## Owner-directed GZ2 Alarm authorization membership — 2026-11-08

After HWEB-F16's recorded HOLD and the offered next action to register its owning API prerequisite, the owner's contextual `Next` selected **documentation-only registration** of HAPI-930 in existing HPS-011. The canonical count is now 930 tasks in 25 phases; all previous task IDs, statuses, immutable archives and older acceptance remain unchanged. [HAPI-930 owning definition](doc/roadmap/TASKS_HPS-011.md#hapi-930) is NOT_IMPLEMENTED/BLOCKED pending exact company/asset authorization policy and cross-owner agreement. No implementation, frontend scope, production/SITE, P3 or deployment is selected.


## Owner-approved FD-01 native bearer issuer preflight — 2026-11-09

The owner approved registration and whole-task execution of **HAPI-931** in existing
**HPS-001** as FD-01, expressly excluding the proposal's HTTPS-only point 1.
The bounded task validates explicit production native issuer configuration and
regresses existing LOCAL bearer/permission denial behavior. Both HTTP and HTTPS
issuer schemes are allowed; no TLS deployment policy or protected authorization
control is removed. The exact registered message is `fix(identity): validate LOCAL bearer issuer preflight`.
See [HAPI-931](doc/roadmap/TASKS_HPS-001.md#hapi-931). Preserve all previous
930 tasks, 25 phases, immutable evidence, and independent HAPI-930 status.
No WEB, OT, release, deployment or other phase is authorized.

## Owner-selected FD-02 API runtime qualification registration — 2026-11-09

The owner's contextual `Next`, following the API-only proposed-task trace,
selects **documentation-only registration** of **HAPI-932 / FD-02** in the
existing **HPS-025 Deployment and Operational Readiness** phase. This registers
one indivisible synthetic **single-application + PostgreSQL** backend
qualification; implementation is NOT_IMPLEMENTED / REGISTERED_PENDING and
requires a later explicit selection after this registration's exact-SHA
documentation validation. No release, production deployment, SITE, browser,
live OT, numerical/P3 or future task execution is selected.

HAPI-932 is the next contiguous unregistered ID after accepted HAPI-931.
The previously mentioned *HAPI-932* label for direct Pipeline/Pipeline-System
Alarm ownership was only a noncanonical suggestion, **not a registered
identifier or reservation**. That separate Alarm ownership proposal remains
open, unnamed in the canonical register, and must obtain a new owner-approved
identifier if selected. No existing HAPI-001–931 meaning or accepted status is
changed. The technical owner, exact future message, complete file allowlist,
synthetic acceptance and limits live only in
[HPS-025 HAPI-932](doc/roadmap/TASKS_HPS-025.md#hapi-932);
the 25-phase structure and immutable archives remain unchanged.


## Owner-selected FD-03 native Telemetry registration — 2026-11-10

The owner's contextual Next after the 2026-11-09 proposed-task crosswalk
registers HAPI-933 / FD-03 / R05 in the existing HPS-010 Telemetry and
Monitoring phase as one future indivisible synthetic API reading-ingest
delivery. This increases the canonical register to 933 tasks across 25
unchanged phases. Earlier HAPI-001–932 IDs, immutable evidence, status
rows and historical HAPI-100/EXT-004 deferred high-velocity ingestion
scope remain unchanged. The crosswalk's FD-02 disposition predates
HAPI-932 software verification and is not current implementation evidence.

Current source has Telemetry source/point POST and history/latest/trend
GET but no reading POST. The task is NOT_IMPLEMENTED / BLOCKED for
owner-approved producer/source/point/asset authority and replay/time/unit/
quality rules. [HAPI-933 owning task](doc/roadmap/TASKS_HPS-010.md#hapi-933)
records the full-task candidate path ledger, admission policy and acceptance.
This selection performs registration only. No Java, migration, OpenAPI,
CI workflow, browser, external SCADA/OT, SITE, simulation or release
is executed. Implementation requires a later distinct owner selection
and complete approved source scope.
