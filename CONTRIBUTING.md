# Contributing to HidraAPI

## 1. Contribution Principles

HidraAPI is a strict modular monolith for Oil & Gas pipeline operations.

Every contribution must preserve:

```text
DDD
Hexagonal architecture
Strict module boundaries
Catalog-backed user-facing classifications
Trilingual Arabic/French/English business labels
OpenAPI documentation at the API boundary
One roadmap task per branch or PR
```

Do not optimize for quick shortcuts that weaken boundaries.

---

## 2. Branch Naming

Use descriptive branch names tied to the roadmap task.

Examples:

```text
cor2-015-add-contributing-and-execution-rules
cor2-016-add-correction-02-architecture-guardrails
identity-004-add-role-aggregate
organization-012-add-organization-unit-repository
```

Avoid vague names:

```text
fix
changes
cleanup
misc
wip
```

---

## 3. Commit Message Format

Use the exact roadmap commit message when executing a roadmap task.

Examples:

```text
docs(contributing): add contribution and execution rules
test(architecture): add correction 02 guardrails
refactor(topology): remove product type compatibility wrapper
feat(organization): complete organization unit type catalog model
```

Commit scopes should be concrete:

```text
identity
organization
topology
kernel
platform
api
openapi
persistence
architecture
roadmap
```

Do not use broad or unclear scopes such as:

```text
misc
stuff
common
helpers
big-update
```

---

## 4. One Roadmap Task per Branch or PR

Each roadmap task must be executed independently.

Rules:

```text
One roadmap task per branch.
One roadmap task per PR.
Do not combine unrelated roadmap tasks.
Do not skip ahead to later tasks.
Do not mix cleanup, feature work, tests, and documentation unless the task explicitly requires them.
```

Correct example:

```text
Branch: cor2-015-add-contributing-and-execution-rules
Scope: only CONTRIBUTING.md and README link update
```

Incorrect example:

```text
Branch: cor2-015-add-contributing-and-execution-rules
Scope: CONTRIBUTING.md + architecture tests + README rewrite + catalog refactor
```

---

## 5. Module Ownership Rules

Business modules own their domain concepts.

| Module | Owns |
|---|---|
| `identity` | Users, roles, permissions, identity lifecycle, identity references |
| `organization` | Employees, organization units, positions, assignments, reporting lines, organization catalogs |
| `topology` | Pipeline systems, pipelines, facilities, nodes, segments, appurtenances, equipment, topology catalogs |
| `kernel` | Generic DDD/application primitives only |
| `platform` | Technical infrastructure only |

Allowed cross-module modeling uses references, not aggregate imports.

Allowed examples:

```text
identity.domain.value.EmployeeReference
organization.domain.value.IdentityUserReference
topology.domain.value.OrganizationUnitReference
topology.domain.value.OperationalOwnerReference
```

Forbidden examples:

```text
organization.domain -> identity.domain.model.User
topology.domain -> organization.domain.model.OrganizationUnit
identity.domain -> organization.domain.model.Employee
```

---

## 6. Forbidden Packages and Imports

Do not create broad catch-all packages.

Forbidden package names:

```text
shared
sharedkernel
common
core
utils
helper
helpers
misc
identityaccess
```

Domain packages must not import:

```text
Spring MVC
Spring components
JPA / Hibernate annotations
OpenAPI / Swagger annotations
platform infrastructure
foreign module aggregate classes
```

Forbidden domain import examples:

```text
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.*;
import dz.sh.hidra.platform.*;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
```

---

## 7. Java Header Rule

Production Java files must use the canonical HidraAPI header.

Template:

```java
/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : <TypeName>
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : <YYYY-MM-DD>
 *
 * @Type        : <Class|Record|Enum|Interface|PackageInfo>
 * @Layer       : <Domain|Application|API|Infrastructure|Kernel|Platform>
 * @Module      : <identity|organization|topology|kernel|platform>
 * @Package     : <actual.package.name>
 *
 * @Description : <short description>.
 *
 */
```

Use the actual package name in `@Package`.

Do not change:

```text
@Author: Abir MEDJERAB
@CreatedOn: 2025-06-26
```

unless a roadmap explicitly instructs otherwise.

---

## 8. DDD Aggregate and Value Object Rules

Aggregates belong in:

```text
src/main/java/dz/sh/hidra/modules/<module>/domain/model
```

Value objects belong in:

```text
src/main/java/dz/sh/hidra/modules/<module>/domain/value
```

Repository ports belong behind domain/application boundaries. Infrastructure adapters must stay under infrastructure packages.

Rules:

```text
Aggregates enforce invariants.
Value objects validate their own values.
Application services orchestrate use cases.
REST controllers do not contain business logic.
JPA entities do not replace domain aggregates.
Mappers translate between layers.
```

Do not return domain aggregates directly from REST controllers.

---

## 9. OpenAPI Documentation Rules

HidraAPI uses the API-boundary-only OpenAPI policy documented in:

```text
docs/ARCHITECTURE.md
```

Controllers must use:

```text
@Tag
@Operation
@ApiResponse or @ApiResponses
@Parameter on every path, query, and header parameter
```

REST request and response records must use:

```text
@Schema(name = "...", description = "...")
@Schema(description = "...", example = "...") on public components where practical
@Schema(type = "string", format = "date-time", example = "2026-06-06T12:00:00Z") for timestamps
```

Domain classes must not import Swagger/OpenAPI annotations.

---

## 10. Trilingual Label Rules

Any persisted or API-exposed human-readable business label must be modeled with Arabic, French, and English fields.

Names:

```text
nameAr
nameFr
nameEn
```

Descriptions:

```text
descriptionAr
descriptionFr
descriptionEn
```

Titles:

```text
titleAr
titleFr
titleEn
```

Examples that must be trilingual:

```text
RoleName
OrganizationUnitName
PositionTitle
TopologyMultilingualName
Product type catalog labels
Organization unit type catalog labels
Pipeline names and descriptions
```

Allowed single-value exceptions:

```text
technical codes
identifiers
usernames
email addresses
employee numbers
personal names such as EmployeeFullName
external immutable references
```

---

## 11. Catalog-Backed Classification Rules

User-facing business classifications must be catalog-backed.

Do not introduce enum-like wrappers or compatibility classes for classifications users see or administrators manage.

Use this target shape:

```text
<Type>Catalog or <Type>CatalogEntry
<Type>Reference
```

Reference objects carry only:

```text
id
code
```

Localized labels belong in catalog entries, DTOs, responses, or read models.

Examples of catalog-backed classifications:

```text
ProductTypeReference
OrganizationUnitTypeReference
FacilityTypeReference
NodeTypeReference
ConnectionTypeReference
EquipmentTypeReference
PipelineAppurtenanceTypeReference
ValveTypeReference
```

Lifecycle/status enums may remain enums:

```text
UserStatus
RoleStatus
EmploymentStatus
OrganizationUnitStatus
TopologyStatus
```

---

## 12. Test Expectations

For local development, run at least:

```bash
mvn -q -DskipTests compile
```

When changing domain logic, run relevant tests:

```bash
mvn -q test -Dtest='*Domain*Test'
```

When changing persistence, run relevant persistence tests:

```bash
mvn -q test -Dtest='*Persistence*Test,*RepositoryAdapterTest'
```

When changing architecture rules, run architecture tests when present:

```bash
mvn -q test -Dtest='*ArchitectureTest'
```

Before merging large changes, prefer:

```bash
mvn -q clean verify
```

If validation cannot be run, record the exact reason in the PR body.

---

## 13. PR Checklist

Before opening or merging a PR, verify:

```text
[ ] The PR executes exactly one roadmap task.
[ ] The branch name matches the roadmap task.
[ ] The commit message matches the roadmap task.
[ ] No unrelated cleanup or feature work is included.
[ ] No shared/common/helper/misc package was introduced.
[ ] No identityaccess package was introduced.
[ ] Domain code does not import Spring MVC, JPA, OpenAPI, platform infrastructure, or foreign aggregates.
[ ] REST parameters have @Parameter where applicable.
[ ] REST request/response records have @Schema where applicable.
[ ] User-facing labels are trilingual.
[ ] User-facing classifications are catalog-backed.
[ ] Deprecated compatibility wrappers were not reintroduced.
[ ] Tests or task-specific validation commands were run, or the exact reason they were not run is recorded.
[ ] README/docs are updated if the task changes developer-facing behavior.
```

---

## 14. Useful Checks

Forbidden Swagger imports in domain packages:

```bash
grep -R "io.swagger.v3.oas.annotations" src/main/java/dz/sh/hidra/modules/*/domain src/main/java/dz/sh/hidra/kernel/domain || true
```

Deprecated compatibility wrappers:

```bash
grep -R "@Deprecated" src/main/java/dz/sh/hidra/modules src/test/java/dz/sh/hidra/modules || true
```

Forbidden broad package names:

```bash
find src/main/java/dz/sh/hidra -type d | grep -E '/(shared|sharedkernel|common|core|utils|helper|helpers|misc|identityaccess)$' || true
```

Multilingual fields:

```bash
grep -R "nameAr\|nameFr\|nameEn\|descriptionAr\|descriptionFr\|descriptionEn\|titleAr\|titleFr\|titleEn" src/main/java/dz/sh/hidra/modules src/main/resources/db/migration || true
```
