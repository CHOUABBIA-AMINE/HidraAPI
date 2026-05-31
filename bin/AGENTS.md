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
| Main roadmap directory | `docs/roadmap` |
| Kernel roadmap | `docs/roadmap/kernel.md` |

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

Before every task, read the relevant roadmap file.

For kernel work, always read:

```text
docs/roadmap/kernel.md
```

The roadmap file is the execution memory.

Do not rely only on chat instructions.

If the chat instruction conflicts with the roadmap, stop and report the conflict unless the user explicitly asks to update the roadmap.

---

## 3. Task Execution Protocol

Execute exactly **one roadmap commit code** per task.

Examples:

```text
KER-002
KER-003
KER-004
```

Do not execute multiple commit codes in one task.

Do not continue automatically to the next task.

After completing a task:

1. Update the relevant roadmap status table.
2. Record validation commands and results in the roadmap when requested.
3. Commit with the exact commit message defined in the roadmap.
4. Leave the working tree clean.
5. Report files changed, validation result, commit hash, and next recommended task.

---

## 4. Git Commit Rules

Use the exact commit message from the roadmap.

Example:

```text
chore(kernel): add kernel package skeleton
```

Do not invent alternative commit messages.

Do not squash multiple roadmap tasks together.

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
