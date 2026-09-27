# HidraAPI Organization Implementation Roadmap

## 1. Document Control

| Field | Value |
|---|---|
| Project | HidraAPI |
| Product | Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Roadmap file | `docs/roadmap/organization.md` |
| Module | `organization` |
| Root package | `dz.sh.hidra.modules.organization` |
| Source root | `src/main/java/dz/sh/hidra/modules/organization` |
| Test root | `src/test/java/dz/sh/hidra/modules/organization` |
| Resource root | `src/main/resources` |
| Author | Abir MEDJERAB |
| CreatedOn | 2025-06-26 |
| UpdatedOn | 2026-09-27 |
| Status | Active — base Organization implementation and correction sequences ORG-034–ORG-048 are complete; issue #130 remains open for the explicitly listed residual scope-integrity gates |
| Execution mode | One commit code at a time |

### 1.1 Current authoritative state — 2026-09-27

This subsection is the **authoritative execution entry point**. Older planning text in this
roadmap is retained as implementation history only when it conflicts with the state below.

Repository baseline at reconciliation:

```text
main: 34e49d6232d84894280a1fa2dae45d0cfefb85fa
latest Organization change:
feat(organization): add responsibility reconciliation use case
open Organization issue: #130
open Organization pull requests: none
```

Current architecture implemented on `main`:

```text
OrganizationUnit
  owns organizational identity/hierarchy only
  does not own an active operational-scope tuple

OperationalScopeReference
  type
  targetId
  canonical owner-target registration input
  no independently writable current code/name

OperationalScope
  id: positive generated Long registry identity
  type
  targetId

ResponsibilityAssignment
  assigneeType
  assigneeId
  scopeId -> OperationalScope.id
  responsibilityType
  effective-dated lifecycle
```

Current owner resolution is fail-closed. Entity-backed scope targets are validated through
approved owner-resolution ports; current target code/name belong to the owning module.
`GLOBAL` has no target ID. Ungoverned `CUSTOM` is rejected. Organization must never
create a database foreign key to topology, identity, or another bounded context.

Current multilingual policy for Organization is same-entity storage only:

```text
Arabic  -> *Ar
French  -> *Fr
English -> *En
```

The separate unit-type translation code/table and the remaining multilingual compatibility
columns have been retired through later immutable Flyway migrations.

Current correction status:

- `ORG-034` through `ORG-048`: **Completed**.
- `ORG-027`: **In Progress**. Assign, revoke, typed list/query and read-only reconciliation
  exist. Identity authorization and workflow/audit integration remain open.
- `ORG-023`: **Blocked** on authorized legacy/consumer evidence. Later validated
  implementation does not retroactively satisfy that evidence gate.
- `ORG-028`, `ORG-029`, `ORG-031`, and `ORG-033`: still partially open as documented
  in section 18.
- `ORG-030` and `ORG-032`: not complete; API cutover and final redundant-scope-column
  retirement remain gated.
- Issue #130 must remain open until the residual acceptance matrix in section 18 is met.

Important ADR note: ADR-0005 remains the accepted repository ADR, but parts of its original
"typed pair directly on each assignment" representation are now stale relative to the
implemented registry model above. Closed PR #133 proposed ADR-0006 but was never merged and
must **not** be treated as repository authority. A future ADR correction must be prepared
from current `main`; do not resurrect or merge the stale PR branch.

CI evidence is recorded only when a run has actually completed. At the time of this roadmap
reconciliation, CI #383 for the latest reconciliation commit is still in progress, so this
document does not claim that run passed.

---

## 2. Organization Module Mission

The `organization` module owns the HidraAPI business organization model.

It answers:

```text
Who are the real operational people?
Which organization unit do they belong to?
Which position do they hold?
Who reports to whom?
How is the operational organization structured?
Which station, region, division, department, team, or direction owns responsibility?
```

The organization module owns:

```text
employees
organization units
positions
employee assignments
matrix reporting lines
organization hierarchy
employee lifecycle
organization unit lifecycle
station-as-organization-unit representation
```

The organization module does **not** own:

```text
login users
identity roles
identity permissions
permission evaluation
Spring Security plumbing
physical topology assets
```

Those belong to:

```text
dz.sh.hidra.modules.identity
dz.sh.hidra.platform.security
dz.sh.hidra.modules.topology later
```

The old name `identityaccess` must not be used.

---

## 3. Naming Standard

Use:

```text
Module name: organization
Package: dz.sh.hidra.modules.organization
API path: /api/v1/organization
Database prefix: hidra_org_*
Roadmap file: docs/roadmap/organization.md
Commit prefix: ORG-xxx
```

Do not create:

```text
dz.sh.hidra.modules.identityaccess
dz.sh.hidra.modules.org
dz.sh.hidra.modules.hr
dz.sh.hidra.modules.employees
```

---

## 4. Architectural Boundaries

### 4.1 Boundary with Identity

```text
modules.identity = security identity, users, roles, permissions, access policy
modules.organization = real SH/TRC people, units, positions, assignments, hierarchy
```

Correct ownership:

| Concern | Owner |
|---|---|
| Login user | `modules.identity` |
| Role | `modules.identity` |
| Permission | `modules.identity` |
| Permission evaluation | `modules.identity` |
| Employee | `modules.organization` |
| Organization unit | `modules.organization` |
| Position / operational function | `modules.organization` |
| Reporting line | `modules.organization` |
| Employee assignment | `modules.organization` |

Organization may use a neutral reference:

```text
IdentityUserReference
```

but must not import:

```text
dz.sh.hidra.modules.identity.domain.*
dz.sh.hidra.modules.identityaccess.*
```

### 4.2 Boundary with Platform

```text
platform = technical infrastructure
organization = business organization model
```

The organization module must not own:

```text
Spring Security filter chain
global exception handler
correlation filters
outbox implementation
platform transaction infrastructure
```

### 4.3 Boundary with Kernel

Organization may use kernel primitives:

```text
dz.sh.hidra.kernel.domain.model.AggregateRoot
dz.sh.hidra.kernel.domain.model.Entity
dz.sh.hidra.kernel.domain.model.ValueObject
dz.sh.hidra.kernel.domain.event.DomainEvent
dz.sh.hidra.kernel.domain.exception.DomainException
dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException
dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException
dz.sh.hidra.kernel.application.command.Command
dz.sh.hidra.kernel.application.query.Query
dz.sh.hidra.kernel.application.pagination.PageRequest
dz.sh.hidra.kernel.application.pagination.PageResult
```

Do not duplicate kernel primitives inside organization.

### 4.4 Boundary with Topology: station asset versus station organization unit

A station may exist in two bounded contexts with different meanings:

```text
topology = physical asset / facility / network object
organization.OrganizationUnit(type = STATION) = people, hierarchy and responsibility structure
```

Organization never imports topology domain/JPA/repository types and never owns the physical
asset. The same real-world station may therefore have both a topology representation and an
Organization unit representing the operating team.

The canonical cross-module responsibility path is **not** an embedded scope tuple on
`OrganizationUnit`. It is:

```text
OrganizationUnit / Employee
        |
        | assignee
        v
ResponsibilityAssignment
        |
        | scopeId
        v
OperationalScope registry
        |
        | type + owner-native targetId
        v
approved owner-resolution port
        |
        v
Topology or other owning bounded context
```

`OperationalScopeReference(type, targetId)` is used when registering a scope target.
The generated `OperationalScope.id` is the identity persisted by responsibility
assignments. Current target code/name/status are resolved from the owner and are not copied
as writable Organization identity fields.

No cross-module database foreign key is permitted. Same-module Organization references may
use database FKs after orphan preflight; topology/identity targets remain resolver-backed.


---

## 5. Required Preconditions

Before implementing organization production code beyond package skeleton, verify that the kernel baseline exists.

Minimum required kernel files:

```text
src/main/java/dz/sh/hidra/kernel/domain/model/AggregateRoot.java
src/main/java/dz/sh/hidra/kernel/domain/model/Entity.java
src/main/java/dz/sh/hidra/kernel/domain/model/ValueObject.java
src/main/java/dz/sh/hidra/kernel/domain/event/DomainEvent.java
src/main/java/dz/sh/hidra/kernel/domain/exception/DomainException.java
src/main/java/dz/sh/hidra/kernel/domain/exception/BusinessRuleViolationException.java
src/main/java/dz/sh/hidra/kernel/domain/exception/InvalidValueObjectException.java
src/main/java/dz/sh/hidra/kernel/application/command/Command.java
src/main/java/dz/sh/hidra/kernel/application/query/Query.java
src/main/java/dz/sh/hidra/kernel/application/pagination/PageRequest.java
src/main/java/dz/sh/hidra/kernel/application/pagination/PageResult.java
```

If these files do not exist:

```text
Stop.
Mark the organization task as Blocked.
Record the missing kernel files in this roadmap.
Do not create temporary duplicates inside organization.
```

---

## 6. Strict Scope Rules

### 6.1 Allowed in `organization`

The organization module may contain:

```text
Employee aggregate
OrganizationUnit aggregate
Position model
EmployeeAssignment model
ReportingLine model
OrganizationUnitType
OperationalScopeReference
OperationalScopeType
organization hierarchy policies
employee lifecycle rules
organization unit lifecycle rules
reporting line policies
organization domain events
organization application commands and queries
organization use-case ports
organization application services
organization REST controllers
organization request/response DTOs
organization persistence entities
organization JPA adapters
organization database migrations
organization tests
organization architecture tests
```

### 6.2 Forbidden in `organization`

The organization module must never own:

```text
User aggregate
Role aggregate
Permission aggregate
PermissionCode business meaning
identityaccess package
Spring Security filter chain
authentication entry point implementation
access denied handler implementation
password management
Pipeline aggregate
Topology Station aggregate
FlowReading aggregate
WorkflowInstance aggregate
Incident aggregate
AuditEvent aggregate
SCADA connector
Historian connector
notification delivery implementation
```

### 6.3 Delayed capabilities

Do not implement in v1 unless explicitly requested later:

```text
full HR system
payroll
leave management
complex HR workflows
identity synchronization
external HR integration
organization-scoped permission evaluation
topology asset management
```

---

## 7. Canonical Java Header

Every Java file created under `organization` must start with this exact header style.

`@Author` and `@CreatedOn` must always stay the same.

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
 * @Type        : <Class|Interface|Enum|Record|Annotation|PackageInfo>
 * @Layer       : <API|Application|Domain|Infrastructure|Organization Test>
 * @Module      : organization
 * @Package     : <actual-package-name>
 *
 * @Description : <one-sentence responsibility>
 *
 */
```

For test classes, use:

```text
@Layer       : Organization Test
@Module      : organization
```

---

## 8. Dependency Rules

### 8.1 Allowed organization imports

Production organization code may import:

```text
java.*
java.time.*
java.util.*
jakarta.validation.*          // API/request DTO validation only
jakarta.persistence.*         // infrastructure persistence entity package only
org.springframework.*         // API/infrastructure/configuration packages only
dz.sh.hidra.kernel.*
```

### 8.2 Forbidden organization imports in domain

Domain layer must not import:

```text
org.springframework.*
jakarta.persistence.*
org.hibernate.*
dz.sh.hidra.platform.*
dz.sh.hidra.modules.identity.*
dz.sh.hidra.modules.identityaccess.*
dz.sh.hidra.modules.topology.*
dz.sh.hidra.modules.telemetry.*
dz.sh.hidra.modules.workflow.*
dz.sh.hidra.modules.planning.*
dz.sh.hidra.modules.monitoring.*
dz.sh.hidra.modules.incidents.*
dz.sh.hidra.modules.audit.*
dz.sh.hidra.modules.integration.*
dz.sh.hidra.modules.analytics.*
```

### 8.3 Layer dependency direction

Allowed:

```text
organization.api -> organization.application
organization.application -> organization.domain
organization.infrastructure -> organization.application ports
organization.infrastructure -> organization.domain mappings
organization.domain -> kernel only
```

Forbidden:

```text
organization.domain -> organization.application
organization.domain -> organization.infrastructure
organization.domain -> organization.api
organization.application -> organization.api
organization.application -> organization.infrastructure
organization.api -> organization.infrastructure
```

---

## 9. Mandatory Validation and Documentation Standards

These standards are mandatory for all organization implementation commits.

If any earlier section appears less strict than this section, this section wins.

### 9.1 Public type documentation

Every production Java type created in the organization module must include:

1. The canonical HidraAPI header.
2. Class-level JavaDoc immediately after the header.
3. Clear explanation of business role, architecture role, validation responsibility, and usage.

Required JavaDoc structure:

```java
/**
 * <One-sentence technical responsibility.>
 *
 * <p>Business role:
 * <Explain what this type means in Hidra organization, employee, unit, position, station-OU, or reporting management.>
 *
 * <p>Architecture role:
 * <Explain whether this belongs to API, application, domain, infrastructure, or configuration.>
 *
 * <p>Validation:
 * <Explain where validation happens and which invariants this type protects.>
 *
 * <p>Usage:
 * <Explain who should depend on this type and who must not depend on it.>
 */
```

No undocumented public production type is allowed.

### 9.2 Domain model documentation rules

Every domain model class under:

```text
src/main/java/dz/sh/hidra/modules/organization/domain/model
```

must document:

```text
business meaning
aggregate/entity responsibility
owned fields
invariants
allowed lifecycle transitions
forbidden state transitions
domain events raised, if any
station/operational scope relation when relevant
matrix reporting behavior when relevant
```

When a domain model is implemented as a class with fields, every meaningful field must have field-level JavaDoc.

When a domain model is implemented as a record, document every record component using `@param` JavaDoc.

### 9.3 Domain validation rules

Domain validation must happen inside:

```text
value object constructors/factories
aggregate factory methods
aggregate behavior methods
domain policies
domain services
```

Domain validation must not depend on:

```text
jakarta.validation.*
Spring
JPA
REST DTO annotations
controller logic
```

Required examples:

```text
Employee number cannot be blank.
Organization unit code must be valid.
OrganizationUnitType is required.
A station OU can reference a topology station only through OperationalScopeReference.
OperationalScopeReference must not import topology.
A disabled organization unit cannot receive new employee assignments.
An employee cannot report to themselves.
An organization unit cannot be its own parent.
Organization hierarchy cannot contain cycles.
An employee can have only one active primary LINE reporting line.
An employee may have multiple active FUNCTIONAL reporting lines.
A disabled employee cannot receive new reporting lines.
A disabled employee cannot be assigned as manager.
A position code must be valid.
```

Bean Validation annotations are not allowed in domain model classes.

### 9.4 DTO documentation and validation rules

Every application DTO and REST DTO must have class-level JavaDoc explaining:

```text
purpose
source/target layer
field meaning
validation constraints
whether it is input or output
```

REST request DTOs must use Bean Validation annotations where applicable:

```text
@NotNull
@NotBlank
@Size
@Pattern
@Valid
```

Controllers must use `@Valid` on request bodies.

Do not use Bean Validation annotations in domain model classes.

### 9.5 Controller documentation rules

Every controller must have:

```text
canonical HidraAPI header
class-level JavaDoc
method-level JavaDoc for each endpoint
OpenAPI @Tag at class level when springdoc is available
OpenAPI @Operation at method level when springdoc is available
OpenAPI @ApiResponses where useful
```

Controllers must:

```text
depend only on application inbound ports
use @Valid on request bodies
return REST response DTOs
not return domain models
not return JPA entities
not access repositories
not contain business rules
```

---

## 10. Mandatory Swagger `@Schema` Documentation Rules

If any earlier section treats Swagger/OpenAPI documentation as optional, this section overrides it.

The project is expected to include `springdoc-openapi-starter-webmvc-ui`.

If the dependency is missing:

```text
Do not silently skip Swagger annotations.
Mark the affected organization API task as Blocked.
Record the missing dependency in docs/roadmap/organization.md.
Ask for dependency approval.
```

Swagger/OpenAPI annotations must be imported only in the API layer:

```text
src/main/java/dz/sh/hidra/modules/organization/api/**
```

Every REST request and response DTO must use `@Schema` at both:

```text
record/class level
field/record-component level
```

Required class-level `@Schema` attributes:

```text
name
description
```

Required field/component-level `@Schema` attributes:

```text
description
example
requiredMode when mandatory
maxLength/minLength when relevant
pattern when relevant
allowableValues when constrained
```

Swagger `@Schema` documentation must not contradict Bean Validation.

Recommended examples:

| Field | Example |
|---|---|
| `employeeNumber` | `EMP-000123` |
| `fullName` | `Abir MEDJERAB` |
| `email` | `abir.medjerab@example.com` |
| `organizationUnitCode` | `CS_EAST_01` |
| `organizationUnitName` | `Compression Station East 01` |
| `organizationUnitType` | `STATION` |
| `operationalScopeType` | `TOPOLOGY_COMPRESSION_STATION` |
| `operationalScopeCode` | `CS-EAST-01` |
| `positionCode` | `STATION_TEAM_LEADER` |
| `positionTitle` | `Station Team Leader` |
| `reportingLineType` | `FUNCTIONAL` |
| `employmentStatus` | `ACTIVE` |
| `unitStatus` | `ACTIVE` |
| `effectiveFrom` | `2026-05-30` |
| `effectiveTo` | `2026-12-31` |

Every controller must use `@Tag` at class level.

Every endpoint method must use:

```java
@Operation
@ApiResponses
```

Path variables and request parameters should use `@Parameter` when their business meaning is not obvious.

`ORG-015` is incomplete unless all request DTOs, response DTOs, and controllers satisfy these Swagger rules.

---

## 11. Mandatory Station-OU and Matrix Reporting Model

This section is mandatory.

If any earlier text suggests a simple supervisor-only model, this section overrides it.

### 11.1 Station as organization unit

Organization must support station-like organization units.

A station can be represented as:

```text
OrganizationUnitType.STATION
```

This applies to any operational station type:

```text
compression station
pumping station
delivery station
metering station
valve station
control station
generic operational station
```

The physical station asset belongs to topology.

The operational people structure belongs to organization.

Therefore:

```text
topology.Station = physical asset
organization.OrganizationUnit(type = STATION) = operational unit/team/responsibility structure
```

### 11.2 Operational scope registry and reference

The old recommendation to embed `scopeType/scopeId/scopeCode/scopeName` directly on an
organization unit is superseded by the implementation on current `main`.

Canonical value/reference:

```text
OperationalScopeReference
- OperationalScopeType type
- String targetId
```

Canonical registry model:

```text
OperationalScope
- Long id                # generated registry identity
- OperationalScopeType type
- String targetId        # owner-native ID; null only for GLOBAL
```

Canonical responsibility relationship:

```text
ResponsibilityAssignment.scopeId -> OperationalScope.id
```

Implemented scope-type semantics include Organization-owned and owner-resolved types such
as `ORGANIZATION_UNIT`, `PIPELINE_SYSTEM`, `PIPELINE`, `FACILITY`, `EQUIPMENT`,
plus `GLOBAL`. `CUSTOM` remains fail-closed unless a separately approved owner contract
is introduced.

Rules:

```text
OperationalScopeReference must not import owner-domain classes.
GLOBAL requires targetId = null.
Entity-backed types require a nonblank owner-native targetId.
CUSTOM without a governed owner contract is rejected.
Registration validates owner existence, identity and assignability.
OperationalScope.id is distinct from the owner-native targetId.
Current owner code/name/status are resolved; they are not writable scope identity.
Organization must not add database FKs to external bounded-context tables.
```


### 11.3 Organization unit type

`OrganizationUnitType` is required.

Recommended values:

```text
COMPANY
DIVISION
DIRECTION
DEPARTMENT
REGION
AREA
DISTRICT
STATION
TEAM
PROJECT_TEAM
OTHER
```

Example hierarchy:

```text
TRC
└── Exploitation Division
    └── Operational East Region
        └── Compression Station East 01
            └── CS East 01 Operations Team
```

### 11.4 Reporting line instead of simple supervisor assignment

Use `ReportingLine`, not `SupervisorAssignment`.

`ReportingLine` supports simple and matrix reporting.

Recommended fields:

```text
ReportingLineId id
EmployeeId employeeId
EmployeeId managerEmployeeId
ReportingLineType type
boolean primaryLine
LocalDate effectiveFrom
LocalDate effectiveTo optional
String description optional
```

Recommended `ReportingLineType` values:

```text
LINE
OPERATIONAL
FUNCTIONAL
ADMINISTRATIVE
TECHNICAL
DOTTED_LINE
```

Rules:

```text
An employee cannot report to themselves.
An employee can have only one active primary LINE reporting line.
An employee may have multiple active FUNCTIONAL reporting lines.
An employee may have multiple ADMINISTRATIVE, TECHNICAL, or DOTTED_LINE reporting lines.
LINE reporting cycles are forbidden.
A disabled employee cannot receive new reporting lines.
A disabled employee cannot be assigned as manager.
Reporting lines must have an effective start date.
```

### 11.5 Required supported case

The roadmap must support this case:

```text
Employee A
- belongs to Compression Station East 01 Operations Team
- has Position: Station Team Leader
- operational scope: TOPOLOGY_COMPRESSION_STATION / CS-EAST-01
- primary reporting line: Station Boss

Station Boss
- primary reporting line: Operational East Region Director

Operational East Region Director
- primary LINE reporting line: Exploitation Division Director at TRC
- FUNCTIONAL reporting line: Gas Flux Director
- ADMINISTRATIVE reporting line: Department Chief
```

This is the target organization capability.

---

## 12. Final Organization Production File Tree

```text
src/main/java/dz/sh/hidra/modules/organization
├── package-info.java
├── api
│   ├── package-info.java
│   └── rest
│       ├── package-info.java
│       ├── controller
│       │   ├── EmployeeController.java
│       │   ├── OrganizationUnitController.java
│       │   ├── PositionController.java
│       │   ├── ReportingLineController.java
│       │   └── package-info.java
│       ├── mapper
│       │   ├── OrganizationRestMapper.java
│       │   └── package-info.java
│       ├── request
│       │   ├── AssignEmployeeToUnitRequest.java
│       │   ├── CreateEmployeeRequest.java
│       │   ├── CreateOrganizationUnitRequest.java
│       │   ├── CreatePositionRequest.java
│       │   ├── SetEmployeeReportingLineRequest.java
│       │   ├── UpdateEmployeeRequest.java
│       │   ├── UpdateOrganizationUnitRequest.java
│       │   └── package-info.java
│       └── response
│           ├── EmployeeAssignmentResponse.java
│           ├── EmployeeResponse.java
│           ├── OrganizationUnitResponse.java
│           ├── PositionResponse.java
│           ├── ReportingLineResponse.java
│           └── package-info.java
├── application
│   ├── package-info.java
│   ├── command
│   │   ├── AssignEmployeeToUnitCommand.java
│   │   ├── CreateEmployeeCommand.java
│   │   ├── CreateOrganizationUnitCommand.java
│   │   ├── CreatePositionCommand.java
│   │   ├── SetEmployeeReportingLineCommand.java
│   │   ├── UpdateEmployeeCommand.java
│   │   ├── UpdateOrganizationUnitCommand.java
│   │   └── package-info.java
│   ├── dto
│   │   ├── EmployeeAssignmentDto.java
│   │   ├── EmployeeDto.java
│   │   ├── OrganizationUnitDto.java
│   │   ├── PositionDto.java
│   │   ├── ReportingLineDto.java
│   │   └── package-info.java
│   ├── mapper
│   │   ├── OrganizationApplicationMapper.java
│   │   └── package-info.java
│   ├── port
│   │   ├── package-info.java
│   │   ├── in
│   │   │   ├── AssignEmployeeToUnitUseCase.java
│   │   │   ├── CreateEmployeeUseCase.java
│   │   │   ├── CreateOrganizationUnitUseCase.java
│   │   │   ├── CreatePositionUseCase.java
│   │   │   ├── GetEmployeeUseCase.java
│   │   │   ├── GetOrganizationUnitUseCase.java
│   │   │   ├── ListEmployeesUseCase.java
│   │   │   ├── ListOrganizationUnitsUseCase.java
│   │   │   ├── SetEmployeeReportingLineUseCase.java
│   │   │   └── package-info.java
│   │   └── out
│   │       ├── DomainEventPublisherPort.java
│   │       ├── EmployeeRepository.java
│   │       ├── OrganizationUnitRepository.java
│   │       ├── PositionRepository.java
│   │       └── package-info.java
│   ├── query
│   │   ├── GetEmployeeByIdQuery.java
│   │   ├── GetOrganizationUnitByIdQuery.java
│   │   ├── ListEmployeesQuery.java
│   │   ├── ListOrganizationUnitsQuery.java
│   │   ├── ListPositionsQuery.java
│   │   └── package-info.java
│   └── service
│       ├── AssignEmployeeToUnitService.java
│       ├── CreateEmployeeService.java
│       ├── CreateOrganizationUnitService.java
│       ├── CreatePositionService.java
│       ├── GetEmployeeService.java
│       ├── GetOrganizationUnitService.java
│       ├── ListEmployeesService.java
│       ├── ListOrganizationUnitsService.java
│       ├── SetEmployeeReportingLineService.java
│       └── package-info.java
├── domain
│   ├── package-info.java
│   ├── event
│   │   ├── EmployeeAssignedToUnitEvent.java
│   │   ├── EmployeeCreatedEvent.java
│   │   ├── EmployeeReportingLineChangedEvent.java
│   │   ├── OrganizationUnitCreatedEvent.java
│   │   ├── PositionCreatedEvent.java
│   │   └── package-info.java
│   ├── exception
│   │   ├── EmployeeAssignmentNotAllowedException.java
│   │   ├── EmployeeLifecycleException.java
│   │   ├── OrganizationDomainException.java
│   │   ├── OrganizationHierarchyException.java
│   │   ├── ReportingLineException.java
│   │   └── package-info.java
│   ├── model
│   │   ├── Employee.java
│   │   ├── EmployeeAssignment.java
│   │   ├── OperationalScopeReference.java
│   │   ├── OrganizationUnit.java
│   │   ├── Position.java
│   │   ├── ReportingLine.java
│   │   └── package-info.java
│   ├── policy
│   │   ├── EmployeeAssignmentPolicy.java
│   │   ├── EmployeeLifecyclePolicy.java
│   │   ├── OrganizationHierarchyPolicy.java
│   │   ├── ReportingLinePolicy.java
│   │   └── package-info.java
│   ├── repository
│   │   ├── EmployeeDomainRepository.java
│   │   ├── OrganizationUnitDomainRepository.java
│   │   ├── PositionCatalog.java
│   │   └── package-info.java
│   ├── service
│   │   ├── EmployeeAssignmentDomainService.java
│   │   ├── OrganizationHierarchyDomainService.java
│   │   ├── ReportingLineDomainService.java
│   │   └── package-info.java
│   └── value
│       ├── AssignmentId.java
│       ├── EmployeeEmail.java
│       ├── EmployeeFullName.java
│       ├── EmployeeId.java
│       ├── EmployeeNumber.java
│       ├── EmploymentStatus.java
│       ├── IdentityUserReference.java
│       ├── OperationalScopeType.java
│       ├── OrganizationUnitCode.java
│       ├── OrganizationUnitId.java
│       ├── OrganizationUnitName.java
│       ├── OrganizationUnitStatus.java
│       ├── OrganizationUnitType.java
│       ├── PositionCode.java
│       ├── PositionId.java
│       ├── PositionTitle.java
│       ├── ReportingLineId.java
│       ├── ReportingLineType.java
│       └── package-info.java
└── infrastructure
    ├── package-info.java
    ├── adapter
    │   ├── NoOpDomainEventPublisherAdapter.java
    │   └── package-info.java
    ├── configuration
    │   ├── OrganizationConfiguration.java
    │   └── package-info.java
    └── persistence
        ├── package-info.java
        ├── entity
        │   ├── EmployeeAssignmentJpaEntity.java
        │   ├── EmployeeJpaEntity.java
        │   ├── OrganizationUnitJpaEntity.java
        │   ├── PositionJpaEntity.java
        │   ├── ReportingLineJpaEntity.java
        │   └── package-info.java
        ├── mapper
        │   ├── OrganizationPersistenceMapper.java
        │   └── package-info.java
        └── repository
            ├── EmployeeJpaRepository.java
            ├── EmployeeRepositoryAdapter.java
            ├── OrganizationUnitJpaRepository.java
            ├── OrganizationUnitRepositoryAdapter.java
            ├── PositionJpaRepository.java
            ├── PositionRepositoryAdapter.java
            └── package-info.java
```

Resource file:

```text
src/main/resources/db/migration/V020__create_organization_tables.sql
```

---

## 13. Commit Plan Overview

| Commit code | Commit message | Purpose |
|---|---|---|
| `ORG-001` | `docs(organization): add organization roadmap` | Add this roadmap file |
| `ORG-002` | `chore(organization): add organization package skeleton` | Add production `package-info.java` files only |
| `ORG-003` | `feat(organization): add organization domain value objects` | Add IDs, codes, names, statuses, unit types, reporting types, operational scope types |
| `ORG-004` | `feat(organization): add position domain model` | Add position model and catalog |
| `ORG-005` | `feat(organization): add organization unit domain model` | Add organization unit aggregate with unit type and operational scope reference |
| `ORG-006` | `feat(organization): add employee domain model` | Add employee aggregate, employee assignment, and reporting line model |
| `ORG-007` | `feat(organization): add organization domain exceptions` | Add organization-specific exceptions including reporting line exception |
| `ORG-008` | `feat(organization): add organization domain events` | Add employee/unit/position/reporting events |
| `ORG-009` | `feat(organization): add organization domain policies and services` | Add hierarchy, lifecycle, assignment, and reporting line policies/services |
| `ORG-010` | `feat(organization): add application commands and queries` | Add command/query records |
| `ORG-011` | `feat(organization): add application ports and DTOs` | Add inbound/outbound ports and DTOs |
| `ORG-012` | `feat(organization): add application services` | Add use-case services |
| `ORG-013` | `feat(organization): add persistence entities and repositories` | Add JPA persistence layer and migration |
| `ORG-014` | `feat(organization): add infrastructure adapters and configuration` | Add no-op event adapter and configuration |
| `ORG-015` | `feat(organization): add REST API contracts and controllers` | Add REST requests/responses/controllers/mappers with Swagger |
| `ORG-016` | `test(organization): add organization domain tests` | Add domain/value/policy tests |
| `ORG-017` | `test(organization): add organization application tests` | Add application service tests |
| `ORG-018` | `test(organization): add organization persistence tests` | Add persistence adapter tests |
| `ORG-019` | `test(organization): add organization API tests` | Add controller/API tests |
| `ORG-020` | `test(organization): add organization architecture guardrail` | Add ArchUnit architecture tests |
| `ORG-021` | `docs(organization): finalize organization checklist` | Update this roadmap with final status |

---

# 14. Detailed Commit Specifications

## ORG-001 — Add Organization Roadmap

### Commit message

```text
docs(organization): add organization roadmap
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `docs/roadmap/organization.md` | Organization implementation plan and execution memory |

### Validation

```bash
test -f docs/roadmap/organization.md
```

---

## ORG-002 — Add Organization Package Skeleton

### Commit message

```text
chore(organization): add organization package skeleton
```

### Description

Create only production package structure using `package-info.java`.

Do not create implementation classes.

Do not create test package-info files.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/modules/organization/package-info.java` | Root organization package boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/api/package-info.java` | API layer boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/api/rest/package-info.java` | REST API boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/api/rest/controller/package-info.java` | REST controller boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/api/rest/request/package-info.java` | REST request DTO boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/api/rest/response/package-info.java` | REST response DTO boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/api/rest/mapper/package-info.java` | REST mapper boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/application/package-info.java` | Application layer boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/application/command/package-info.java` | Command package boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/application/query/package-info.java` | Query package boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/application/dto/package-info.java` | Application DTO boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/application/mapper/package-info.java` | Application mapper boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/application/port/package-info.java` | Application port boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/application/port/in/package-info.java` | Inbound use-case port boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/application/port/out/package-info.java` | Outbound dependency port boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/application/service/package-info.java` | Application service boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/domain/package-info.java` | Domain layer boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/domain/model/package-info.java` | Domain model boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/domain/value/package-info.java` | Domain value boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/domain/event/package-info.java` | Domain event boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/domain/policy/package-info.java` | Domain policy boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/domain/service/package-info.java` | Domain service boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/domain/repository/package-info.java` | Domain repository contract boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/domain/exception/package-info.java` | Domain exception boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/infrastructure/package-info.java` | Infrastructure layer boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/package-info.java` | Persistence infrastructure boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/package-info.java` | JPA entity boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/package-info.java` | Persistence mapper boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/package-info.java` | Persistence repository boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/infrastructure/adapter/package-info.java` | Infrastructure adapter boundary |
| Create | `src/main/java/dz/sh/hidra/modules/organization/infrastructure/configuration/package-info.java` | Infrastructure configuration boundary |
| Update | `docs/roadmap/organization.md` | Mark `ORG-002` as completed after execution |

### Acceptance criteria

- Only production `package-info.java` files are created.
- No test `package-info.java` files are created.
- No implementation class is created.
- Every file uses the canonical HidraAPI header.
- Every `@Layer` value matches the layer package.
- No `identityaccess` package is created.
- No `identity` implementation is created.

### Validation

```bash
find src/main/java/dz/sh/hidra/modules/organization -type f | sort
mvn -q -DskipTests compile
```

---

## ORG-003 — Add Organization Domain Value Objects

### Commit message

```text
feat(organization): add organization domain value objects
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/value/EmployeeId.java` | Stable employee identifier |
| Create | `domain/value/EmployeeNumber.java` | Validated business employee number |
| Create | `domain/value/EmployeeFullName.java` | Validated employee full name |
| Create | `domain/value/EmployeeEmail.java` | Validated employee email |
| Create | `domain/value/EmploymentStatus.java` | Employee lifecycle status enum |
| Create | `domain/value/OrganizationUnitId.java` | Stable organization unit identifier |
| Create | `domain/value/OrganizationUnitCode.java` | Validated organization unit code |
| Create | `domain/value/OrganizationUnitName.java` | Validated organization unit name |
| Create | `domain/value/OrganizationUnitStatus.java` | Organization unit lifecycle status enum |
| Create | `domain/value/OrganizationUnitType.java` | Classifies units such as division, region, station, or team |
| Create | `domain/value/PositionId.java` | Stable position identifier |
| Create | `domain/value/PositionCode.java` | Validated position code |
| Create | `domain/value/PositionTitle.java` | Validated position title |
| Create | `domain/value/AssignmentId.java` | Stable assignment identifier |
| Create | `domain/value/ReportingLineId.java` | Stable reporting line identifier |
| Create | `domain/value/ReportingLineType.java` | Matrix reporting line type enum |
| Create | `domain/value/OperationalScopeType.java` | Classifies referenced operational scopes such as topology station or pipeline |
| Create | `domain/value/IdentityUserReference.java` | Neutral reference to identity user without importing identity domain |
| Update | `docs/roadmap/organization.md` | Mark `ORG-003` as completed after execution |

### Acceptance criteria

- Value objects are immutable.
- Invalid inputs are rejected.
- Every value object has class-level JavaDoc.
- Record components are documented using `@param` JavaDoc.
- `OrganizationUnitType` includes `STATION`.
- `ReportingLineType` includes `LINE`, `OPERATIONAL`, `FUNCTIONAL`, `ADMINISTRATIVE`, `TECHNICAL`, and `DOTTED_LINE`.
- `OperationalScopeType` includes station-oriented topology references.
- No Spring/JPA imports.
- No identity/identityaccess/topology imports.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ORG-004 — Add Position Domain Model

### Commit message

```text
feat(organization): add position domain model
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/model/Position.java` | Domain model for an organizational position/function |
| Create | `domain/repository/PositionCatalog.java` | Domain contract for position lookup/catalog operations |
| Update | `docs/roadmap/organization.md` | Mark `ORG-004` as completed after execution |

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ORG-005 — Add Organization Unit Domain Model

### Commit message

```text
feat(organization): add organization unit domain model
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/model/OperationalScopeReference.java` | Neutral reference from an organization unit to a future topology/operational scope |
| Create | `domain/model/OrganizationUnit.java` | Organization unit aggregate supporting hierarchy, unit type, and optional operational scope |
| Create | `domain/repository/OrganizationUnitDomainRepository.java` | Domain repository contract for organization units |
| Update | `docs/roadmap/organization.md` | Mark `ORG-005` as completed after execution |

### Acceptance criteria

- Organization unit is aggregate root.
- Organization unit supports `OrganizationUnitType`.
- Organization unit supports type `STATION`.
- Organization unit may hold `OperationalScopeReference`.
- `OperationalScopeReference` does not import topology.
- Hierarchy references are controlled.
- Self-parenting is rejected.
- No persistence/framework dependency.
- JavaDoc documents lifecycle, hierarchy, station-as-OU, and operational scope rules.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ORG-006 — Add Employee Domain Model

### Commit message

```text
feat(organization): add employee domain model
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/model/Employee.java` | Employee aggregate representing a real operational person |
| Create | `domain/model/EmployeeAssignment.java` | Assignment of employee to unit/position and optional operational scope |
| Create | `domain/model/ReportingLine.java` | Matrix-capable reporting line for employee-to-manager relationships |
| Create | `domain/repository/EmployeeDomainRepository.java` | Domain repository contract for employees |
| Update | `docs/roadmap/organization.md` | Mark `ORG-006` as completed after execution |

### Acceptance criteria

- Employee is aggregate root.
- Employee supports assignments to station/team organization units.
- Employee supports reporting lines.
- `ReportingLine` supports matrix reporting.
- Lifecycle transitions are controlled.
- Role/user/permission logic is not introduced.
- No identity domain import exists.
- No topology domain import exists.
- No persistence/framework dependency.
- JavaDoc documents lifecycle, assignment, and reporting rules.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ORG-007 — Add Organization Domain Exceptions

### Commit message

```text
feat(organization): add organization domain exceptions
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/exception/OrganizationDomainException.java` | Base exception for organization domain failures |
| Create | `domain/exception/EmployeeLifecycleException.java` | Exception for invalid employee lifecycle transition |
| Create | `domain/exception/EmployeeAssignmentNotAllowedException.java` | Exception for invalid employee assignment |
| Create | `domain/exception/OrganizationHierarchyException.java` | Exception for invalid organization hierarchy |
| Create | `domain/exception/ReportingLineException.java` | Exception for invalid matrix reporting line |
| Update | Existing value/model/policy files if needed | Replace generic exceptions with organization-specific exceptions where appropriate |
| Update | `docs/roadmap/organization.md` | Mark `ORG-007` as completed after execution |

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ORG-008 — Add Organization Domain Events

### Commit message

```text
feat(organization): add organization domain events
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/event/EmployeeCreatedEvent.java` | Published when an employee is created |
| Create | `domain/event/EmployeeAssignedToUnitEvent.java` | Published when an employee is assigned to a unit/position |
| Create | `domain/event/EmployeeReportingLineChangedEvent.java` | Published when reporting line changes |
| Create | `domain/event/OrganizationUnitCreatedEvent.java` | Published when an organization unit is created |
| Create | `domain/event/PositionCreatedEvent.java` | Published when a position is created |
| Update | `docs/roadmap/organization.md` | Mark `ORG-008` as completed after execution |

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ORG-009 — Add Organization Domain Policies and Services

### Commit message

```text
feat(organization): add organization domain policies and services
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/policy/OrganizationHierarchyPolicy.java` | Validates unit parent/child hierarchy constraints |
| Create | `domain/policy/EmployeeLifecyclePolicy.java` | Validates employee lifecycle transitions |
| Create | `domain/policy/EmployeeAssignmentPolicy.java` | Validates employee assignment rules |
| Create | `domain/policy/ReportingLinePolicy.java` | Validates matrix reporting rules |
| Create | `domain/service/OrganizationHierarchyDomainService.java` | Coordinates hierarchy validation rules |
| Create | `domain/service/EmployeeAssignmentDomainService.java` | Coordinates employee assignment domain rules |
| Create | `domain/service/ReportingLineDomainService.java` | Coordinates reporting line validation and matrix reporting rules |
| Update | `docs/roadmap/organization.md` | Mark `ORG-009` as completed after execution |

### Acceptance criteria

- Policies compile.
- Reporting line policy prevents self-reporting.
- Reporting line policy supports one active primary LINE relation.
- Reporting line policy supports multiple FUNCTIONAL lines.
- Organization hierarchy policy prevents cycles.
- No Spring/JPA imports.
- No identity/identityaccess/topology imports.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ORG-010 — Add Application Commands and Queries

### Commit message

```text
feat(organization): add application commands and queries
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `application/command/CreateEmployeeCommand.java` | Input for creating an employee |
| Create | `application/command/UpdateEmployeeCommand.java` | Input for updating employee basic information |
| Create | `application/command/CreateOrganizationUnitCommand.java` | Input for creating an organization unit with optional operational scope |
| Create | `application/command/UpdateOrganizationUnitCommand.java` | Input for updating an organization unit |
| Create | `application/command/CreatePositionCommand.java` | Input for creating a position |
| Create | `application/command/AssignEmployeeToUnitCommand.java` | Input for assigning employee to unit/position |
| Create | `application/command/SetEmployeeReportingLineCommand.java` | Input for creating/updating an employee reporting line |
| Create | `application/query/GetEmployeeByIdQuery.java` | Query for one employee |
| Create | `application/query/ListEmployeesQuery.java` | Query for employee list/search |
| Create | `application/query/GetOrganizationUnitByIdQuery.java` | Query for one organization unit |
| Create | `application/query/ListOrganizationUnitsQuery.java` | Query for organization unit list |
| Create | `application/query/ListPositionsQuery.java` | Query for position catalog |
| Update | `docs/roadmap/organization.md` | Mark `ORG-010` as completed after execution |

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ORG-011 — Add Application Ports and DTOs

### Commit message

```text
feat(organization): add application ports and DTOs
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `application/port/in/CreateEmployeeUseCase.java` | Inbound port for employee creation |
| Create | `application/port/in/GetEmployeeUseCase.java` | Inbound port for retrieving employee |
| Create | `application/port/in/ListEmployeesUseCase.java` | Inbound port for listing/searching employees |
| Create | `application/port/in/CreateOrganizationUnitUseCase.java` | Inbound port for organization unit creation |
| Create | `application/port/in/GetOrganizationUnitUseCase.java` | Inbound port for retrieving organization unit |
| Create | `application/port/in/ListOrganizationUnitsUseCase.java` | Inbound port for listing organization units |
| Create | `application/port/in/CreatePositionUseCase.java` | Inbound port for position creation |
| Create | `application/port/in/AssignEmployeeToUnitUseCase.java` | Inbound port for employee assignment |
| Create | `application/port/in/SetEmployeeReportingLineUseCase.java` | Inbound port for reporting line creation/update |
| Create | `application/port/out/EmployeeRepository.java` | Outbound employee persistence port |
| Create | `application/port/out/OrganizationUnitRepository.java` | Outbound organization unit persistence port |
| Create | `application/port/out/PositionRepository.java` | Outbound position persistence port |
| Create | `application/port/out/DomainEventPublisherPort.java` | Outbound domain event publication abstraction |
| Create | `application/dto/EmployeeDto.java` | Application employee DTO |
| Create | `application/dto/OrganizationUnitDto.java` | Application organization unit DTO |
| Create | `application/dto/PositionDto.java` | Application position DTO |
| Create | `application/dto/EmployeeAssignmentDto.java` | Application employee assignment DTO |
| Create | `application/dto/ReportingLineDto.java` | Application reporting line DTO |
| Create | `application/mapper/OrganizationApplicationMapper.java` | Maps domain objects to application DTOs |
| Update | `docs/roadmap/organization.md` | Mark `ORG-011` as completed after execution |

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ORG-012 — Add Application Services

### Commit message

```text
feat(organization): add application services
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `application/service/CreateEmployeeService.java` | Implements employee creation use case |
| Create | `application/service/GetEmployeeService.java` | Implements employee retrieval use case |
| Create | `application/service/ListEmployeesService.java` | Implements employee listing/search use case |
| Create | `application/service/CreateOrganizationUnitService.java` | Implements organization unit creation use case |
| Create | `application/service/GetOrganizationUnitService.java` | Implements organization unit retrieval use case |
| Create | `application/service/ListOrganizationUnitsService.java` | Implements organization unit listing use case |
| Create | `application/service/CreatePositionService.java` | Implements position creation use case |
| Create | `application/service/AssignEmployeeToUnitService.java` | Implements employee assignment use case |
| Create | `application/service/SetEmployeeReportingLineService.java` | Implements reporting line use case |
| Update | `docs/roadmap/organization.md` | Mark `ORG-012` as completed after execution |

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ORG-013 — Add Persistence Entities and Repositories

### Commit message

```text
feat(organization): add persistence entities and repositories
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `infrastructure/persistence/entity/EmployeeJpaEntity.java` | JPA representation of employee |
| Create | `infrastructure/persistence/entity/OrganizationUnitJpaEntity.java` | JPA representation of organization unit, including unit type and operational scope columns |
| Create | `infrastructure/persistence/entity/PositionJpaEntity.java` | JPA representation of position |
| Create | `infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java` | JPA representation of employee assignment |
| Create | `infrastructure/persistence/entity/ReportingLineJpaEntity.java` | JPA representation of matrix reporting line |
| Create | `infrastructure/persistence/repository/EmployeeJpaRepository.java` | Spring Data employee repository |
| Create | `infrastructure/persistence/repository/OrganizationUnitJpaRepository.java` | Spring Data organization unit repository |
| Create | `infrastructure/persistence/repository/PositionJpaRepository.java` | Spring Data position repository |
| Create | `infrastructure/persistence/mapper/OrganizationPersistenceMapper.java` | Maps between domain and persistence |
| Create | `infrastructure/persistence/repository/EmployeeRepositoryAdapter.java` | Implements application `EmployeeRepository` port |
| Create | `infrastructure/persistence/repository/OrganizationUnitRepositoryAdapter.java` | Implements application `OrganizationUnitRepository` port |
| Create | `infrastructure/persistence/repository/PositionRepositoryAdapter.java` | Implements application `PositionRepository` port |
| Create | `src/main/resources/db/migration/V020__create_organization_tables.sql` | Creates organization database tables |
| Update | `docs/roadmap/organization.md` | Mark `ORG-013` as completed after execution |

### Database tables

Migration should create:

```text
hidra_org_employee
hidra_org_unit
hidra_org_position
hidra_org_employee_assignment
hidra_org_reporting_line
```

Additional required schema support:

```text
hidra_org_unit.unit_type
hidra_org_unit.operational_scope_type
hidra_org_unit.operational_scope_code
hidra_org_unit.operational_scope_id optional
hidra_org_unit.operational_scope_name optional
hidra_org_reporting_line.reporting_line_type
hidra_org_reporting_line.primary_line
hidra_org_reporting_line.effective_from
hidra_org_reporting_line.effective_to optional
```

### Validation

```bash
mvn -q -DskipTests compile
mvn -q flyway:validate
```

If Flyway validation cannot run due to missing database connection, record exact reason.

---

## ORG-014 — Add Infrastructure Adapters and Configuration

### Commit message

```text
feat(organization): add infrastructure adapters and configuration
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `infrastructure/adapter/NoOpDomainEventPublisherAdapter.java` | Safe no-op event publisher until platform outbox integration exists |
| Create | `infrastructure/configuration/OrganizationConfiguration.java` | Wires organization module beans |
| Update | `docs/roadmap/organization.md` | Mark `ORG-014` as completed after execution |

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ORG-015 — Add REST API Contracts and Controllers

### Commit message

```text
feat(organization): add REST API contracts and controllers
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `api/rest/request/CreateEmployeeRequest.java` | Request body for creating employee |
| Create | `api/rest/request/UpdateEmployeeRequest.java` | Request body for updating employee |
| Create | `api/rest/request/CreateOrganizationUnitRequest.java` | Request body for creating organization unit, including type and optional operational scope |
| Create | `api/rest/request/UpdateOrganizationUnitRequest.java` | Request body for updating organization unit |
| Create | `api/rest/request/CreatePositionRequest.java` | Request body for creating position |
| Create | `api/rest/request/AssignEmployeeToUnitRequest.java` | Request body for assigning employee to unit/position |
| Create | `api/rest/request/SetEmployeeReportingLineRequest.java` | Request body for setting matrix reporting line |
| Create | `api/rest/response/EmployeeResponse.java` | REST employee response |
| Create | `api/rest/response/OrganizationUnitResponse.java` | REST organization unit response, including type and optional operational scope |
| Create | `api/rest/response/PositionResponse.java` | REST position response |
| Create | `api/rest/response/EmployeeAssignmentResponse.java` | REST employee assignment response |
| Create | `api/rest/response/ReportingLineResponse.java` | REST reporting line response |
| Create | `api/rest/mapper/OrganizationRestMapper.java` | Maps REST request/response to application commands/DTOs |
| Create | `api/rest/controller/EmployeeController.java` | Employee REST endpoints |
| Create | `api/rest/controller/OrganizationUnitController.java` | Organization unit REST endpoints |
| Create | `api/rest/controller/PositionController.java` | Position REST endpoints |
| Create | `api/rest/controller/ReportingLineController.java` | Reporting line REST endpoints |
| Update | `docs/roadmap/organization.md` | Mark `ORG-015` as completed after execution |

### API endpoints

Create endpoints under:

```text
/api/v1/organization
```

Recommended v1 endpoints:

```text
POST   /api/v1/organization/employees
GET    /api/v1/organization/employees/{employeeId}
GET    /api/v1/organization/employees
PUT    /api/v1/organization/employees/{employeeId}
POST   /api/v1/organization/employees/{employeeId}/assignments
POST   /api/v1/organization/employees/{employeeId}/reporting-lines

POST   /api/v1/organization/units
GET    /api/v1/organization/units/{unitId}
GET    /api/v1/organization/units
PUT    /api/v1/organization/units/{unitId}

POST   /api/v1/organization/positions
GET    /api/v1/organization/positions
```

### Acceptance criteria

- Request DTOs use Bean Validation.
- Request DTOs are documented.
- Request DTOs use Swagger `@Schema`.
- Response DTOs are documented.
- Response DTOs use Swagger `@Schema`.
- Controllers are documented at class and endpoint method level.
- Controllers use OpenAPI annotations.
- Controllers use `@Valid`.
- Controllers depend on application ports only.
- Controllers do not access repositories.
- REST DTOs do not leak JPA entities.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ORG-016 — Add Organization Domain Tests

### Commit message

```text
test(organization): add organization domain tests
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/value/EmployeeNumberTest.java` | Verifies employee number validation |
| Create | `domain/value/OrganizationUnitCodeTest.java` | Verifies organization unit code validation |
| Create | `domain/value/OrganizationUnitTypeTest.java` | Verifies organization unit type includes station support |
| Create | `domain/value/OperationalScopeReferenceTest.java` | Verifies operational scope reference rules |
| Create | `domain/value/PositionCodeTest.java` | Verifies position code validation |
| Create | `domain/value/ReportingLineTypeTest.java` | Verifies reporting line type values |
| Create | `domain/model/EmployeeTest.java` | Verifies employee lifecycle, assignment, and reporting line rules |
| Create | `domain/model/OrganizationUnitTest.java` | Verifies organization unit lifecycle, station type, scope, and hierarchy rules |
| Create | `domain/policy/EmployeeAssignmentPolicyTest.java` | Verifies employee assignment rules |
| Create | `domain/policy/EmployeeLifecyclePolicyTest.java` | Verifies employee lifecycle rules |
| Create | `domain/policy/OrganizationHierarchyPolicyTest.java` | Verifies hierarchy constraints |
| Create | `domain/policy/ReportingLinePolicyTest.java` | Verifies matrix reporting line rules |
| Update | `docs/roadmap/organization.md` | Mark `ORG-016` as completed after execution |

### Validation

```bash
mvn -q test -Dtest='*Organization*,*EmployeeTest,*OrganizationUnitTest,*EmployeeNumberTest,*OrganizationUnitCodeTest,*OrganizationUnitTypeTest,*OperationalScopeReferenceTest,*ReportingLineTypeTest,*ReportingLinePolicyTest,*OrganizationHierarchyPolicyTest'
mvn -q test
```

---

## ORG-017 — Add Organization Application Tests

### Commit message

```text
test(organization): add organization application tests
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `application/service/CreateEmployeeServiceTest.java` | Verifies employee creation use case |
| Create | `application/service/CreateOrganizationUnitServiceTest.java` | Verifies organization unit creation use case including station-type unit |
| Create | `application/service/AssignEmployeeToUnitServiceTest.java` | Verifies employee assignment use case |
| Create | `application/service/SetEmployeeReportingLineServiceTest.java` | Verifies reporting line use case |
| Update | `docs/roadmap/organization.md` | Mark `ORG-017` as completed after execution |

### Validation

```bash
mvn -q test -Dtest='*ServiceTest'
mvn -q test
```

---

## ORG-018 — Add Organization Persistence Tests

### Commit message

```text
test(organization): add organization persistence tests
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `infrastructure/persistence/EmployeeRepositoryAdapterTest.java` | Verifies employee persistence adapter |
| Create | `infrastructure/persistence/OrganizationUnitRepositoryAdapterTest.java` | Verifies organization unit persistence including station type and operational scope columns |
| Create | `infrastructure/persistence/ReportingLineRepositoryAdapterTest.java` | Verifies reporting line persistence |
| Update | `docs/roadmap/organization.md` | Mark `ORG-018` as completed after execution |

### Validation

```bash
mvn -q test -Dtest='*RepositoryAdapterTest'
```

---

## ORG-019 — Add Organization API Tests

### Commit message

```text
test(organization): add organization API tests
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `api/rest/controller/EmployeeControllerTest.java` | Verifies employee REST endpoints |
| Create | `api/rest/controller/OrganizationUnitControllerTest.java` | Verifies organization unit REST endpoints including station-as-OU input |
| Create | `api/rest/controller/PositionControllerTest.java` | Verifies position REST endpoints |
| Create | `api/rest/controller/ReportingLineControllerTest.java` | Verifies reporting line REST endpoints |
| Update | `docs/roadmap/organization.md` | Mark `ORG-019` as completed after execution |

### Validation

```bash
mvn -q test -Dtest='*ControllerTest'
mvn -q test
```

---

## ORG-020 — Add Organization Architecture Guardrail

### Commit message

```text
test(organization): add organization architecture guardrail
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/test/java/dz/sh/hidra/modules/organization/OrganizationArchitectureTest.java` | Ensures organization layer boundaries and forbidden imports |
| Update | `docs/roadmap/organization.md` | Mark `ORG-020` as completed or blocked |

### Required architecture rules

The test must assert:

```text
organization.domain must not depend on Spring
organization.domain must not depend on JPA
organization.domain must not depend on infrastructure
organization.domain must not depend on API
organization.application must not depend on API
organization.application must not depend on infrastructure
organization.api must not depend on infrastructure
organization must not import identity domain model
organization must not import identityaccess
organization must not import topology domain model
organization must not create identityaccess package
controllers must not access repositories directly
infrastructure persistence adapters implement application outbound ports
```

### Validation

```bash
mvn -q test -Dtest=OrganizationArchitectureTest
mvn -q test
```

---

## ORG-021 — Finalize Organization Checklist

### Commit message

```text
docs(organization): finalize organization checklist
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Update | `docs/roadmap/organization.md` | Record final execution status and checklist |

### Required final checklist

```text
[ ] Organization package structure exists
[ ] No identityaccess package exists
[ ] Organization domain has no Spring dependency
[ ] Organization domain has no JPA dependency
[ ] Organization domain has no identity domain dependency
[ ] Organization domain has no topology domain dependency
[ ] Organization application has no API dependency
[ ] Organization application has no infrastructure dependency
[ ] Organization API has no repository dependency
[ ] Employee aggregate exists
[ ] OrganizationUnit aggregate exists
[ ] Position model exists
[ ] OrganizationUnitType exists
[ ] OrganizationUnit supports STATION as operational OU
[ ] OperationalScopeReference exists and does not import topology
[ ] ReportingLine replaces simple SupervisorAssignment
[ ] ReportingLineType supports LINE, OPERATIONAL, FUNCTIONAL, ADMINISTRATIVE, TECHNICAL, DOTTED_LINE
[ ] Reporting line policy prevents self-reporting
[ ] Reporting line policy supports one active primary LINE relation
[ ] Employee assignment policy works
[ ] Organization hierarchy policy works
[ ] REST API compiles
[ ] Domain models have class-level and field/component documentation
[ ] Domain model validation does not use Bean Validation
[ ] Domain value objects have validation and format documentation
[ ] REST request DTOs have Bean Validation annotations
[ ] REST request DTOs use @Schema at class and component level
[ ] REST response DTOs use @Schema at class and component level
[ ] Organization controllers use @Tag, @Operation, and @ApiResponses
[ ] OpenAPI annotations are restricted to organization API layer
[ ] Controllers depend only on application inbound ports
[ ] Controllers do not access repositories or JPA entities
[ ] Persistence migration exists
[ ] Domain tests pass
[ ] Application tests pass
[ ] API tests pass
[ ] Persistence tests pass or are blocked with exact reason
[ ] Architecture guardrail passes or is blocked with exact reason
[ ] mvn -q clean verify passes or unrelated blocker is recorded
```

### Validation

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
```

---

## 15. AI Agent Execution Rules

Any AI agent executing this roadmap must follow these rules:

1. Execute exactly one commit code at a time.
2. Do not batch commits.
3. Read `AGENTS.md` before starting.
4. Read `docs/roadmap/organization.md` before starting.
5. Check kernel preconditions before implementation commits.
6. Do not create `identityaccess`.
7. Do not create identity implementation files.
8. Do not create topology implementation files.
9. Do not import topology domain classes.
10. Do not create platform security filter-chain code in organization.
11. Always use the canonical HidraAPI header.
12. Never change `@Author`.
13. Never change `@CreatedOn`.
14. Update this roadmap after each completed or blocked commit.
15. Run validation after each commit.
16. If validation cannot run, record the exact reason.
17. If a dependency is missing, stop and report it.
18. If a requested file does not belong to organization, do not create it.
19. If an API DTO/controller is missing required JavaDoc or Swagger annotations, the task is incomplete.
20. If a domain model/value object is missing validation documentation, the task is incomplete.
21. If a station is modeled as a topology asset inside organization, the task is incomplete.
22. If `SupervisorAssignment` is created instead of `ReportingLine`, the task is incomplete.

---

## 16. Current Status Table

| Commit code | Status | Notes |
|---|---|---|
| `ORG-001` | Planned | Add this roadmap |
| `ORG-002` | Planned | Add production package skeleton only |
| `ORG-003` | Planned | Add organization value objects including unit type, reporting line type, and operational scope type |
| `ORG-004` | Planned | Add position model |
| `ORG-005` | Planned | Add organization unit aggregate with station-as-OU and operational scope reference |
| `ORG-006` | Planned | Add employee aggregate, assignment, and reporting line model |
| `ORG-007` | Planned | Add organization domain exceptions |
| `ORG-008` | Planned | Add organization domain events |
| `ORG-009` | Planned | Add domain policies and services |
| `ORG-010` | Planned | Add application commands and queries |
| `ORG-011` | Planned | Add application ports and DTOs |
| `ORG-012` | Planned | Add application services |
| `ORG-013` | Planned | Add persistence entities, repositories, and migration |
| `ORG-014` | Planned | Add infrastructure adapters and configuration |
| `ORG-015` | Planned | Add REST API |
| `ORG-016` | Planned | Add domain tests |
| `ORG-017` | Planned | Add application tests |
| `ORG-018` | Planned | Add persistence tests |
| `ORG-019` | Completed | Generated locally in ZIP; organization API/controller/REST mapper tests only; Maven test not run outside full repository workspace |
| `ORG-020` | Completed | Generated locally in ZIP; ArchUnit organization architecture guardrail test only; Maven test not run outside full repository workspace |
| `ORG-021` | Planned | Finalize checklist |

---

## 17. Next Action

Start with:

```text
ORG-001 — docs(organization): add organization roadmap
```

Then execute:

```text
ORG-002 — chore(organization): add organization package skeleton
```

Do not implement organization classes before the package skeleton is reviewed.
---

## ORG-018 Local ZIP Execution Note

```text
Status: Completed locally as downloadable ZIP.
Commit: Not created because user requested no GitHub push/commit.
Validation:
- File generation completed.
- Persistence adapter test file count verified locally.
- Tests use JUnit 5 and Mockito.
- Tests verify repository adapters and persistence mapper behavior without loading a database.
- No Spring Boot test context, DataJpaTest, identity, identityaccess, topology, platform, API, controller, application service, or ORG-019 files were generated.
- Maven tests not run because this ZIP is not the full repository workspace.
Known note:
- ReportingLineRepositoryAdapterTest verifies reporting-line persistence through EmployeeRepositoryAdapter because ORG-013 stores reporting lines as employee-owned persistence rows and does not define an independent reporting-line repository adapter.
```
---

## ORG-019 Local ZIP Execution Note

```text
Status: Completed locally as downloadable ZIP.
Commit: Not created because user requested no GitHub push/commit.
Validation:
- File generation completed.
- API mapper and controller test file counts verified locally.
- Tests use JUnit 5 assertions and fake application inbound ports.
- Tests do not use Spring Boot test context, MockMvc, Mockito, database, repositories, or persistence adapters.
- No identity, identityaccess, topology, platform, persistence, application service, architecture guardrail, or ORG-020 files were generated.
- Maven tests not run because this ZIP is not the full repository workspace.
Known limitation:
- Tests assert HTTP 501 placeholders for update employee, update organization unit, and list positions because ORG-011 did not define corresponding inbound ports and ORG-015 generated these endpoints as contract placeholders.
```
---

## ORG-020 Local ZIP Execution Note

```text
Status: Completed locally as downloadable ZIP.
Commit: Not created because user requested no GitHub push/commit.
ArchUnit status:
- ArchUnit dependency exists in pom.xml as com.tngtech.archunit:archunit-junit5.
Validation:
- File generation completed.
- Architecture guardrail test file count verified locally.
- Test uses ArchUnit only and imports production classes without including test classes.
- Guardrail covers organization domain, application, API, infrastructure, identityaccess, identity, topology, OpenAPI, Bean Validation, JPA, Spring Data, Spring Web, and Spring configuration boundaries.
- No domain, application, persistence, API controller, migration, identity, identityaccess, topology, platform, or ORG-021 finalization files were generated.
- Maven tests not run because this ZIP is not the full repository workspace.
```


| `ORG-021` | Completed | Generated locally in ZIP; final organization checklist/status recorded; Maven compile/test/verify not run outside full repository workspace |

---

# ORG-021 Final Organization Checklist

```text
Commit code    : ORG-021
Commit message : docs(organization): finalize organization checklist
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

## Final execution status

The organization module was generated across local ZIP packages from `ORG-002` through `ORG-020`.
This checklist records what was generated and what still requires validation after copying all ZIP
contents into the real HidraAPI repository workspace.

Because each ORG task was generated as an isolated downloadable ZIP, this final checklist **does not
claim that the full repository compiles or that the full Maven test suite passes**. Full validation
must be run after merging the generated files into the repository.

## Required final checklist

| Item | Status | Evidence / Notes |
|---|---:|---|
| Organization package structure exists | Generated locally | `ORG-002` generated production `package-info.java` skeleton files. |
| No `identityaccess` package exists | Generated locally / needs repo scan | No ORG ZIP intentionally generated `identityaccess`; full repository scan still required. |
| Organization domain has no Spring dependency | Generated locally / needs Maven + ArchUnit | ORG domain files were generated without Spring imports; ORG-020 guardrail covers this. |
| Organization domain has no JPA dependency | Generated locally / needs Maven + ArchUnit | JPA was generated only under infrastructure persistence in `ORG-013`; ORG-020 guardrail covers this. |
| Organization domain has no identity domain dependency | Generated locally / needs ArchUnit | Domain uses neutral references only; full guardrail run still required. |
| Organization domain has no topology domain dependency | Generated locally / needs ArchUnit | `OperationalScopeReference` is neutral and does not import topology; full guardrail run still required. |
| Organization application has no API dependency | Generated locally / needs ArchUnit | Application files were generated before API and do not intentionally import API contracts. |
| Organization application has no infrastructure dependency | Generated locally / needs ArchUnit | Application uses outbound ports, not adapters. |
| Organization API has no repository dependency | Generated locally / needs ArchUnit | Controllers depend on inbound ports and REST mapper only. |
| Employee aggregate exists | Generated locally | `ORG-006` generated `Employee`. |
| OrganizationUnit aggregate exists | Generated locally | `ORG-005` generated `OrganizationUnit`. |
| Position model exists | Generated locally | `ORG-004` generated `Position`. |
| OrganizationUnitType exists | Generated locally | `ORG-003` generated `OrganizationUnitType`. |
| OrganizationUnit supports STATION as operational OU | Generated locally | `ORG-005` and related tests support station-as-organization-unit. |
| OperationalScopeReference exists and does not import topology | Generated locally / needs ArchUnit | `ORG-003` generated neutral operational scope reference. |
| ReportingLine replaces simple SupervisorAssignment | Generated locally / needs repo scan | `ORG-006` generated `ReportingLine`; no ORG ZIP intentionally generated `SupervisorAssignment`. |
| ReportingLineType supports LINE, OPERATIONAL, FUNCTIONAL, ADMINISTRATIVE, TECHNICAL, DOTTED_LINE | Generated locally | `ORG-003` generated matrix reporting types. |
| Reporting line policy prevents self-reporting | Needs verification / possible gap | `ReportingLine` model may enforce this, but `ORG-009` policy generation should be reviewed because the policy itself may not contain an explicit self-reporting check. |
| Reporting line policy supports one active primary LINE relation | Generated locally | `ORG-009` policy and `ORG-016` tests target one active primary LINE rule. |
| Employee assignment policy works | Generated locally / tests not run | `ORG-009` policy and `ORG-016` tests generated. |
| Organization hierarchy policy works | Generated locally / tests not run | `ORG-009` policy and `ORG-016` tests generated. |
| REST API compiles | Not verified | Maven compile was not run in a full repository workspace. |
| Domain models have class-level and field/component documentation | Generated locally / needs review | JavaDoc was generated on domain classes and record components where applicable. |
| Domain model validation does not use Bean Validation | Generated locally / needs ArchUnit | Bean Validation was intended only for REST request DTOs. |
| Domain value objects have validation and format documentation | Generated locally / needs review | `ORG-003` generated value object validation and documentation. |
| REST request DTOs have Bean Validation annotations | Generated locally | `ORG-015` generated request DTOs with Bean Validation annotations. |
| REST request DTOs use `@Schema` at class and component level | Generated locally | `ORG-015` generated Swagger `@Schema` on request DTO classes and components. |
| REST response DTOs use `@Schema` at class and component level | Generated locally | `ORG-015` generated Swagger `@Schema` on response DTO classes and components. |
| Organization controllers use `@Tag`, `@Operation`, and `@ApiResponses` | Not fully satisfied | `ORG-015` generated `@Tag` and `@Operation`; generated controllers should be updated to add `@ApiResponses`. |
| OpenAPI annotations are restricted to organization API layer | Generated locally / needs ArchUnit | `ORG-020` guardrail covers this. |
| Controllers depend only on application inbound ports | Generated locally / needs ArchUnit | `ORG-015` controllers depend on inbound ports and mapper. |
| Controllers do not access repositories or JPA entities | Generated locally / needs ArchUnit | `ORG-015` controllers were generated without repository/JPA access. |
| Persistence migration exists | Generated locally | `ORG-013` generated `V020__create_organization_tables.sql`. |
| Domain tests pass | Not verified | `ORG-016` tests were generated but not run. |
| Application tests pass | Not verified | `ORG-017` tests were generated but not run. |
| API tests pass | Not verified | `ORG-019` tests were generated but not run. |
| Persistence tests pass or are blocked with exact reason | Blocked | Persistence tests were generated in `ORG-018`; not run because ZIPs are not the full repository workspace and no full Maven/test environment is available here. |
| Architecture guardrail passes or is blocked with exact reason | Blocked | ArchUnit guardrail was generated in `ORG-020`; not run because ZIPs are not the full repository workspace. |
| `mvn -q clean verify` passes or unrelated blocker is recorded | Blocked | Not run because this environment generated isolated ZIPs, not a merged full HidraAPI workspace. |

## Validation commands required after merging ZIPs into HidraAPI

Run from the repository root after copying every ORG ZIP into the same working tree:

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
```

Recommended targeted commands if the full suite fails because of unrelated pre-existing tests:

```bash
mvn -q test -Dtest='*OrganizationArchitectureGuardrailTest'
mvn -q test -Dtest='*ControllerTest,*RestMapperTest'
mvn -q test -Dtest='*RepositoryAdapterTest'
mvn -q test -Dtest='*ServiceTest'
mvn -q test -Dtest='*Organization*,*EmployeeTest,*OrganizationUnitTest,*ReportingLinePolicyTest,*OrganizationHierarchyPolicyTest'
```

## Known remaining risks before declaring organization complete

1. `@ApiResponses` is missing from the generated ORG-015 controllers and should be added before the checklist item is considered complete.
2. `UpdateEmployeeUseCase`, `UpdateOrganizationUnitUseCase`, and `ListPositionsUseCase` were not generated in ORG-011, so ORG-015 exposes those endpoints as HTTP 501 placeholders.
3. `ListEmployeesService` returns an empty page when no `organizationUnitId` filter is supplied because ORG-011 did not define a broad employee search outbound port.
4. `ListOrganizationUnitsService` returns an empty page when no `parentId` or `type` filter is supplied because ORG-011 did not define a broad organization-unit search outbound port.
5. `ReportingLinePolicy` should be reviewed for explicit self-reporting protection; if protection exists only in the model, either update the policy or revise the checklist wording.
6. All generated ORG ZIPs must be merged into one repository workspace before Maven compile/test/verify can produce trustworthy results.
7. The persistence migration should be checked against the actual Flyway migration sequence already present in the repository before applying it.
8. The final architecture guardrail may reveal boundary violations after all generated files are merged; fix violations instead of weakening the guardrail.

## ORG-021 validation result

```text
Local file generation: passed
Roadmap updated       : passed
GitHub push           : not performed
Git commit            : not created
Maven compile         : not run — isolated ZIP generation, not full repository workspace
Maven test            : not run — isolated ZIP generation, not full repository workspace
Maven clean verify    : not run — isolated ZIP generation, not full repository workspace
```


---

## 18. Issue #130 — Operational-scope integrity correction

> **Current-authority note:** ADR-0005 is still the accepted repository ADR, but its
> direct type+target-per-assignment representation is partially stale relative to the
> registry implementation now on `main`. Use section 1.1 and the reconciled task status
> below as the execution authority. Closed, unmerged PR #133/ADR-0006 is historical only.



**Decision:** [ADR-0005](../adr/0005-organization-operational-scope-integrity.md) is the accepted target architecture. **This section is planning only:** no application code, production database changes, scope references or tests have been run by documenting it. The prior ORG-001–ORG-021 history/status remains unchanged; local ZIP checklists are not evidence of a merged or verified deployment. Before any implementation task, read `AGENTS.md`, review the current main and all affected code, and execute **exactly one** code below per commit/PR. Do not infer operational scope from the SONATRACH organization workbook.

### Design and release gates

- OrganizationUnit has zero-to-many effective-dated responsibility assignments; employee membership does not grant blanket scope responsibility.
- ResponsibilityAssignment is the chosen relationship owner; do not introduce a second competing relationship without another ADR.
- Entity-backed references use typed, owner-validated IDs; GLOBAL has no target ID; CUSTOM requires a registered owner contract.
- Resolve **current** code/name through owning module public ports; immutable labeled historical snapshots are optional and non-authoritative.
- Cross-module IDs require application-level integrity, lifecycle reconciliation, authorization and audit; no direct topology/JPA coupling.
- Make schema changes additively and only after legacy reconciliation planning. Existing applied migrations are immutable.
- Defer destructive removals until consumers, accepted backfills and recovery tests are verified.

### Discrete task plan

| Code | Exact commit message | Scope and deliverable | Mandatory validation and exit gate | Prerequisite |
|---|---|---|---|---|
| `ORG-022` | `docs(organization): approve scope integrity ADR and correction roadmap` | ADR-0005, ADR index and this planning section only; no domain/schema/API edits. | Check doc links, task uniqueness, existing ID/code/roadmap conflicts; no Maven runtime assertion. | Documentation decision (this change); mark completed only after merge to main. |
| `ORG-023` | `test(organization): inventory operational scope contracts and legacy tuples` | Read current models, enum, API DTOs, mappers, queries, JPA entities, Flyway and all actual consumers; prepare a privacy-safe baseline, owner-resolver availability matrix and migration/quarantine specification. | Document observed current types, duplicate/wrong-code and orphan patterns without exposing restricted data; add read-only contract/assessment tests where allowed. | ORG-022 |
| `ORG-024` | `feat(organization): introduce canonical operational scope reference invariants` | Refine OperationalScopeReference/OperationalScopeType validation for typed identity; define GLOBAL no-target and reject unregistered CUSTOM; add value-object and policy tests, without touching persistence contracts. | Domain unit tests for null/partial/type-disallowed references and no invented identifiers. | ORG-023 |
| `ORG-025` | `feat(organization): add scope owner-resolution application ports` | Design/implement owner registry and read-only public-port resolution for currently implemented scope owners, current code/name and assignability; keep organization domain independent of topology internals. | Resolver adapter/contract tests for wrong type, absent ID, retired owner, unavailable owner and current display projection. | ORG-024 |
| `ORG-026` | `feat(organization): enforce multi-scope responsibility domain policy` | Reuse ResponsibilityAssignment for unit/employee assignees, effective-dated multi-target roles, overlap/idempotency and revocation policies; clarify ORGANIZATION_UNIT self/cycle semantics and owner lifecycle. | Domain and application tests: two scopes per unit, concurrent distinct roles, duplicate/overlap, temporal boundaries and invalid assignees. | ORG-025 |
| `ORG-027` | `feat(organization): add validated responsibility application use cases` | Public input ports/commands/queries to assign, revoke, list and reconcile responsibilities; enforce resolver, identity authorization, workflow/audit and concurrency policy. Do not imply UI or live actuation. | Application contract tests for validation and authorization, retirement/recheck, idempotency and no repository leakage. | ORG-026 |
| `ORG-028` | `chore(organization): add backward-compatible scope schema` | Add a NEW Flyway migration with required constraints/indexes/nullable GLOBAL target ID and needed assignment versioning; keep existing table/data and old columns until verified cutover. | Testcontainers/PostgreSQL migration and constraint tests incl. GLOBAL, temporal and null semantics; document no-downtime assumptions. | ORG-027 |
| `ORG-029` | `feat(organization): align responsibility persistence and reconciliation` | Adapt JPA adapters to canonical pair, implement quarantined legacy tuple reconciliation/backfill under reviewed data rules and preserve history; no guessed topology IDs. | Representative DB tests for existing/mismatched/orphan/duplicate tuples and safe recovery; record reconciliation totals privately. | ORG-028 |
| `ORG-030` | `feat(organization): migrate operational scope API contracts` | Add versioned read/write assignment endpoints and owner-resolved display; reject caller-supplied independent current code/name; inventory and migrate organization and employee consumers. | REST/OpenAPI, API backward-compatibility, access control and owner-freshness tests. | ORG-029 |
| `ORG-031` | `refactor(organization): remove duplicated active scope ownership` | After proven migration and consumer sign-off, remove canonical embedded scope tuple from OrganizationUnit and EmployeeAssignment domain/API mapping; preserve historical snapshots where explicitly governed. | Compile, API and integration regressions; confirm multi-scope remains intact and no duplicated active source of truth. | ORG-030 |
| `ORG-032` | `chore(organization): retire legacy redundant scope columns` | In a separate later Flyway migration, remove deprecated scope code/name and retired unit/employee tuple columns only once migration/reporting consumers are fully reconciled; retain audit history. | DB migration/recovery tests and reviewed pre-deployment gates. Do not drop data without signed backfill and rollback. | ORG-031 |
| `ORG-033` | `test(organization): verify scope integrity end to end` | Harden architecture guardrails and integration tests across module owner ports, concurrent edits, target retirement, temporal lookup, audit and authorization; update completion evidence. | mvn -q -DskipTests compile; mvn -q test; mvn -q clean verify, plus real PostgreSQL/Testcontainers when available; report blockers honestly. | ORG-032 |

### Correction status (issue #130)

The status below was re-reconciled on **2026-09-27** against GitHub `main` at
`34e49d6232d84894280a1fa2dae45d0cfefb85fa`. Earlier CI #357 evidence remains historical;
CI #378 through #382 validated the later Organization correction sequence, while CI #383
for the newest reconciliation increment is still in progress at the time of this edit.
Status reflects repository evidence rather than old ZIP-era execution notes. A task is not
marked Completed unless its full roadmap exit gate is satisfied.

| Code | Status | Reconciled evidence on current main |
|---|---|---|
| `ORG-022` | Completed | ADR-0005 and the correction roadmap are present on main; prior completion evidence remains valid. |
| `ORG-023` | Blocked | Source inventory exists, but authorized live/sanitized legacy tuple evidence, full owner-target resolution evidence and external consumer evidence are still incomplete. Implementation work proceeded beyond this evidence gate; that does not make the inventory gate complete. |
| `ORG-024` | Completed | `OperationalScopeReference` is now a canonical typed owner-target value object containing only `type` and owner-native `targetId`; GLOBAL forbids a target, entity-backed types require one, and ungoverned CUSTOM is rejected. `OperationalScopeType` exposes the corresponding domain semantics and focused domain tests cover null, blank, GLOBAL, CUSTOM and normalization cases. |
| `ORG-025` | Completed | Organization now resolves `ORGANIZATION_UNIT` locally and `PIPELINE_SYSTEM`, `PIPELINE`, `FACILITY`, and `EQUIPMENT` through a topology-owned public application input port. Current code/name and lifecycle-derived assignability come from the owning module; organization imports no topology domain, repository, JPA, or infrastructure type. Existing validator tests cover wrong type, missing target, unassignable target and owner failure; new owner-query/adapter tests cover current display, retired owners and supported-type routing. |
| `ORG-026` | Completed | Responsibility assignment now accepts only existing ACTIVE employee or organization-unit assignees, revalidates entity-backed owner existence/assignability before new assignment, rejects direct organization-unit self-target responsibility, preserves historical rows on owner retirement, and retains half-open overlap/idempotency/revocation behavior. Focused tests cover invalid/inactive assignees and retired owners. |
| `ORG-027` | In Progress | Validated register-scope, assign-responsibility, revoke-responsibility and typed list/query flows exist. Read-only reconciliation is now implemented with current assignee/scope-owner rechecks and no automatic repair. Authorization plus workflow/audit integration requirements remain open. |
| `ORG-028` | In Progress | Additive migration `V20260927_001__add_operational_scope_registry.sql` creates the canonical registry and nullable responsibility `scope_id` FK while retaining legacy columns. Required dedicated PostgreSQL/Testcontainers constraint coverage and the full roadmap versioning gate are not yet evidenced. |
| `ORG-029` | In Progress | Canonical registry and responsibility JPA entity/repository/mapper/adapter code exists and responsibility persistence uses `scopeId`. Verified reconciliation/backfill/quarantine of legacy tuples is still missing, so the task is not complete. |
| `ORG-030` | Planned | No versioned responsibility/scope REST migration with owner-resolved display and backward-compatibility evidence is present yet. |
| `ORG-031` | In Progress | Canonical embedded scope components were removed from `OrganizationUnit` and `EmployeeAssignment`, and responsibility uses `scopeId`. Transitional deprecated constructors/accessors and legacy persistence mappings remain; prerequisite migration/consumer sign-off is not complete. |
| `ORG-032` | Planned | Legacy `operational_scope_*` database columns remain intentionally in place. No destructive retirement migration has been added. |
| `ORG-033` | In Progress | Current main passes the repository compile/test/full verify and acceptance compile/test/clean verify stages, plus OpenAPI publication, in CI #357. End-to-end scope integrity is still incomplete because authoritative entity-backed owner resolvers, legacy data reconciliation/backfill, API cutover and destructive retirement gates remain open. |

### ORG-027 reconciliation increment

**Commit scope:** responsibility reconciliation only. ORG-027 remains **In Progress**.

Live-main inspection before this increment confirmed that `ListResponsibilitiesUseCase`
and `ResponsibilityQueryApplicationService` already provide the typed list/query
capability, despite the older status text saying list was still missing. That validated
work is retained.

This increment adds a read-only `ReconcileResponsibilitiesUseCase` and
`ResponsibilityReconciliationApplicationService`. It scans domain assignments through
`ResponsibilityAssignmentRepositoryPort.findAll()` and reports, without mutation:

```text
missing assignee
inactive assignee
legacy assignment with no canonical scopeId
unknown registry scope
unsupported authoritative owner resolver
missing owner target
resolver identity mismatch
owner target no longer assignable
organization-unit direct self-target responsibility
```

No identifier, owner type, replacement scope, or repair is inferred. Historical rows are
not rewritten or deleted. The JPA adapter exposes `findAll()` only through the domain
repository port; persistence entities do not leak into the inbound contract.

Exact implementation allowlist for this increment:

```text
application/dto/ResponsibilityReconciliationResult.java
application/port/in/ReconcileResponsibilitiesUseCase.java
application/service/ResponsibilityReconciliationApplicationService.java
application/port/out/ResponsibilityAssignmentRepositoryPort.java
infrastructure/persistence/adapter/JpaResponsibilityAssignmentRepositoryAdapter.java
test/.../ResponsibilityReconciliationApplicationServiceTest.java
docs/roadmap/organization.md
```

Focused tests prove healthy rows produce no finding, reconciliation never calls save,
legacy missing-scope rows are reported rather than guessed, missing/inactive assignees
are detected, owner retirement/unavailability fails closed, resolver identity mismatch is
reported, and direct organization-unit self-target responsibility is surfaced.

**Remaining ORG-027 gate:** identity authorization and workflow/audit integration around
responsibility writes/reconciliation. Those concerns are not invented or marked complete
by this reconciliation increment.

### Reconciliation notes

- The implementation sequence on main diverged from the original ORG-024 through
  ORG-033 dependency order. This table records that fact rather than rewriting task
  definitions or pretending earlier gates were satisfied.
- The fail-closed resolver configuration keeps the Spring context bootable while
  preserving the rule that entity-backed scope registration must not succeed without
  an approved authoritative owner resolver.
- The canonical target state is now a generated `OperationalScope.id` referenced by
  `ResponsibilityAssignment.scopeId`; current target code/name remain owner data.
- Legacy compatibility code and database columns are transitional and must not be
  treated as the canonical source of operational-scope identity.
- ORG-027 remains In Progress. The multilingual and dependency-integrity correction sequences
  in section 19 are complete, so execution may now continue only with ORG-027's remaining
  authorization/workflow/audit gate or another explicitly open section-18 gate.

**Execution rule:** A roadmap task's first implementation action must specify exact file allowlists and verification commands after inspecting current main; do not silently rewrite old task descriptions or mark future tasks complete. `ORG-028` and `ORG-032` must use separately numbered, never-reused migrations after rechecking the live Flyway sequence. Issue #130 remains open until the acceptance matrix is satisfied.

---

## 19. Organization multilingual storage correction — embedded Arabic/French/English fields

### Decision

For the **organization module only**, multilingual business attributes are stored on the
same owning entity. Hidra Organization supports exactly these three application languages:

```text
Arabic  -> *Ar
French  -> *Fr
English -> *En
```

The organization module must **not** introduce or retain a separate translation
aggregate/table as the canonical storage model for these three languages.

Canonical examples:

```text
OrganizationUnit
- nameAr
- nameFr
- nameEn

Position
- titleAr
- titleFr
- titleEn
- descriptionAr
- descriptionFr
- descriptionEn

OrganizationUnitType
- nameAr
- nameFr
- nameEn
- descriptionAr
- descriptionFr
- descriptionEn
```

Language-neutral attributes remain stored once:

```text
id
code
kind/type
status
active
parent/reference IDs
effective dates
timestamps
```

This decision is intentionally limited to `modules.organization`. It does **not**
change topology or any other module. Other modules will be reviewed separately later.

### Current organization alignment

Repository reconciliation on 2026-09-27 confirms the multilingual correction is complete:

| Model | Current storage on `main` | Status |
|---|---|---|
| `OrganizationUnit` | `nameAr`, `nameFr`, `nameEn` | Canonical same-entity storage. |
| `Position` | `titleAr/Fr/En`, `descriptionAr/Fr/En` | Canonical same-entity storage; legacy `description` retired. |
| `AdministrativeState` | `nameAr`, `nameFr`, `nameEn` | Canonical same-entity storage. |
| `AdministrativeDistrict` | `nameAr`, `nameFr`, `nameEn` | Canonical same-entity storage. |
| `AdministrativeLocality` | `nameAr`, `nameFr`, `nameEn` | Canonical same-entity storage. |
| `OrganizationUnitType` | `nameAr/Fr/En`, `descriptionAr/Fr/En` | Canonical same-entity storage; translation code/table retired. |
| `Shift` | `nameAr`, `nameFr`, `nameEn` | Canonical same-entity storage; legacy `name` retired. |

`OrganizationUnitTypeTranslation` production code has been removed and
`hidra_org_unit_type_translation` was retired by
`V20260927_003__retire_organization_unit_type_translation_table.sql` after fail-closed
parity checks.

The remaining legacy multilingual compatibility columns were retired by
`V20260927_005__retire_organization_multilingual_compatibility_columns.sql`.
Historical task-plan text below is retained to explain the migration sequence; it is not a
description of the current schema.


### Data and migration rules

- Do not edit or reuse an already-applied Flyway migration.
- Add new migrations only after rechecking the live migration sequence on `main`.
- Migration from `hidra_org_unit_type_translation` must map only recognized
  `language_code` values `ar`, `fr`, and `en`.
- Do not invent missing translations.
- Do not silently map an unlabeled legacy base `description` to a language.
- Detect and report duplicate translation rows for the same unit type/language before
  applying uniqueness or NOT NULL assumptions.
- Preserve the old translation table until code/API consumers have cut over and data
  parity/recovery checks pass.
- Drop the old translation table only in a later, separately numbered migration.
- API/admin contracts may expose all three language fields directly. Storage must not
  depend on request locale.
- Search/query logic may match all three language fields, but locale preference is a
  presentation/query concern rather than a separate persistence model.

### Discrete correction task plan

| Code | Exact commit message | Scope and deliverable | Mandatory validation and exit gate | Prerequisite |
|---|---|---|---|---|
| `ORG-034` | `docs(organization): define embedded multilingual field policy` | Record the organization-only Arabic/French/English same-entity storage decision, current alignment, migration safety rules and correction sequence. No production code or schema change. | Roadmap consistency review; verify task codes are unique and scope excludes other modules. | User architecture decision. |
| `ORG-035` | `test(organization): inventory multilingual fields and unit-type translations` | Inventory all organization user-facing multilingual fields and current `OrganizationUnitTypeTranslation` consumers/data assumptions; document recognized language codes, duplicates, missing translations and legacy description ambiguity without modifying data. | Repository-wide organization scan plus privacy-safe read-only assessment/tests where available. No guessed translations. | ORG-034 |
| `ORG-036` | `chore(organization): add embedded multilingual unit-type schema` | Add a NEW Flyway migration introducing `name_ar`, `name_fr`, `name_en`, `description_ar`, `description_fr`, `description_en` on `hidra_org_unit_type`; add `description_ar/fr/en` on `hidra_org_position` and `name_ar/fr/en` on `hidra_org_shift`. Backfill unit-type fields only from deterministic `ar/fr/en` translation rows after preflight; retain the legacy unit-type translation table plus legacy `hidra_org_unit_type.description`, `hidra_org_position.description`, and `hidra_org_shift.name` columns through cutover. | PostgreSQL/Testcontainers migration tests for language-code normalization, duplicates, missing translations, orphans, deterministic backfill, legacy-column preservation, rollback/recovery assumptions and no data loss. | ORG-035 |
| `ORG-037` | `feat(organization): embed multilingual unit-type fields` | Refactor `OrganizationUnitType`, `Position`, and `Shift` domain/JPA/persistence/application/API contracts to use the embedded fields introduced by ORG-036. Preserve compatibility where required during cutover. Do not alter employee proper-name/transliteration semantics, transactional free text, or any other module. | Compile, focused domain/application/persistence/API tests, OpenAPI compatibility review, search behavior across embedded language fields where applicable, and full `mvn -q test`. | ORG-036 |
| `ORG-038` | `refactor(organization): retire unit-type translation code` | After consumer cutover, remove `OrganizationUnitTypeTranslation` domain/JPA/repository/adapter code and any organization application/API dependency on it. Keep the legacy DB table for recovery until the next gate. | Repository scan proves no organization production consumer remains; compile/test/clean verify pass. | ORG-037 |
| `ORG-039` | `chore(organization): retire unit-type translation table` | Add a separately numbered Flyway migration removing `hidra_org_unit_type_translation` only after data-parity, recovery and consumer sign-off. Never modify the original organization-table migration. | PostgreSQL/Testcontainers forward/recovery tests and reviewed migration evidence showing embedded fields preserve accepted `ar/fr/en` values. | ORG-038 |
| `ORG-040` | `test(organization): verify embedded multilingual integrity` | Harden organization tests/architecture checks so translatable organization entity fields follow the explicit `Ar/Fr/En` pattern and no separate organization translation model/table remains canonical. | `mvn -q -DskipTests compile`; `mvn -q test`; `mvn -q clean verify`; targeted persistence/API tests and architecture scan. | ORG-039 |
| `ORG-041` | `refactor(organization): type responsibility assignee discriminator` | Replace raw-string responsibility assignee state with `ResponsibilityAssigneeType { EMPLOYEE, ORGANIZATION_UNIT }` across canonical domain, application and persistence contracts. Keep narrow deprecated textual bridges only for current compatibility; existing VARCHAR values remain unchanged through `EnumType.STRING`. | Focused discriminator/responsibility tests, compile/test/clean verify, exact-SHA CI. | ORG-040 |
| `ORG-042` | `refactor(organization): govern reporting line subjects` | Replace `ReportingLine.sourceType/targetType` strings with governed Organization subject types/references. Do not add external target types without an approved resolver contract. | Domain/persistence tests, invalid discriminator rejection, compile/test/clean verify. | ORG-041 |
| `ORG-043` | `refactor(organization): govern contact point targets` | Replace `OrganizationContactPoint.targetType` string with a governed Organization target type/reference and application validation. | Domain/application/persistence tests and architecture scan. | ORG-042 |
| `ORG-044` | `refactor(organization): use operational scope reference value` | Make `OperationalScopeReference` the canonical scope-registration input while preserving generated `OperationalScope.id` as responsibility identity. | Scope registration/resolver tests and compile/test/clean verify. | ORG-043 |
| `ORG-045` | `refactor(organization): adopt organization code value` | Adopt `OrganizationCode` consistently for compatible stable Organization business codes, or document/remove it if repository constraints prove adoption unsafe. | Consumer scan, normalization tests, API/OpenAPI compatibility review. | ORG-044 |
| `ORG-046` | `chore(organization): enforce internal reference integrity` | Add a new fail-closed Flyway migration for same-module FKs and discriminator checks. Preflight orphan rows; never add DB FKs to topology/identity targets. | PostgreSQL/Testcontainers orphan-preflight, FK/check and rollback tests. | ORG-045 |
| `ORG-047` | `chore(organization): retire multilingual compatibility columns` | After runtime/data parity verification, retire legacy unit-type/position descriptions and shift name with a separately numbered fail-closed migration. | PostgreSQL/Testcontainers parity/recovery tests and Hibernate validation. | ORG-046 |
| `ORG-048` | `test(organization): enforce typed dependency integrity` | Add final guardrails against raw finite discriminators, unvalidated internal references, retired multilingual structures, and accidental cross-module DB ownership. | Architecture tests and full compile/test/clean verify. | ORG-047 |

### ORG-035 inventory result

**Baseline reviewed:** GitHub `main` at
`9f88b07d5c70096733d776ca6e34daac81fd910d` after CI #364 passed.

The repository-wide Organization scan classifies multilingual storage as follows:

| Model / concern | Current evidence | ORG-034 alignment / action |
|---|---|---|
| `OrganizationUnit` | `nameAr`, `nameFr`, `nameEn` exist in domain/JPA/schema/application/API paths. | Aligned; retain same-entity fields. |
| `AdministrativeState` | `nameAr`, `nameFr`, `nameEn` exist in domain/JPA/schema. | Aligned; retain. |
| `AdministrativeDistrict` | `nameAr`, `nameFr`, `nameEn` exist in domain/JPA/schema. | Aligned; retain. |
| `AdministrativeLocality` | `nameAr`, `nameFr`, `nameEn` exist in domain/JPA/schema. | Aligned; retain. |
| `Position` | `titleAr`, `titleFr`, `titleEn` exist, but `description` is one non-localized column. | Title aligned; migrate confirmed user-facing description additively to `descriptionAr/Fr/En` while retaining legacy `description` through cutover. |
| `Shift` | One user-facing `name` exists in domain/JPA/schema. | Gap discovered by ORG-035; add `nameAr/Fr/En` additively and retain legacy `name` through cutover. |
| `OrganizationUnitType` | Base entity has no localized name fields and has one ambiguous `description`; labels/descriptions are in `OrganizationUnitTypeTranslation`. | Primary correction target: embed `nameAr/Fr/En` and `descriptionAr/Fr/En`. Do not guess the language of the legacy base `description`. |
| `Employee` | Proper-name fields are Arabic plus Latin-script/transliteration fields (`*Ar`, `*Lt`). | Not a catalog translation model; do not mechanically convert personal-name/transliteration data to French/English fields. |
| `OrganizationContactPoint.label` | Single operator/business label. | Record as user-facing free-text candidate; no automatic multilingual migration in ORG-036 without clarified semantics. |
| `OrganizationDelegation.reason` | Single transactional free-text reason. | Do not auto-translate or triplicate; retain as entered text unless a later requirement explicitly makes it localized content. |
| `OrganizationHierarchySnapshot.description` | Single historical free-text description. | Do not auto-translate or triplicate in this correction. |
| `ResponsibilityAssignment.description` | Single transactional free-text description. | Do not auto-translate or triplicate in this correction. |
| Employee address street lines | Free-form address text anchored to normalized locality. | Not treated as catalog translations by this correction. |

#### Existing unit-type translation implementation inventory

The separate unit-type translation path currently consists of:

```text
domain/model/OrganizationUnitTypeTranslation.java
application/port/out/OrganizationUnitTypeTranslationRepositoryPort.java
infrastructure/persistence/entity/OrganizationUnitTypeTranslationJpaEntity.java
infrastructure/persistence/repository/OrganizationUnitTypeTranslationJpaRepository.java
infrastructure/persistence/adapter/JpaOrganizationUnitTypeTranslationRepositoryAdapter.java
OrganizationPersistenceMapper translation mapping methods
OrganizationPersistence.ORGANIZATION_UNIT_TYPE_TRANSLATION_TABLE
hidra_org_unit_type_translation
```

No Organization application service, inbound use case, REST controller, REST DTO or
administration query consumer of `OrganizationUnitTypeTranslation` was found on this
baseline. The repository port only exposes `save` and `findById`; there is no
parent-plus-language lookup contract.

#### Schema/data assumptions that ORG-036 must not hide

Current `hidra_org_unit_type_translation` schema has:

```text
id              PRIMARY KEY
unit_type_id    NOT NULL
language_code   NOT NULL
label           NOT NULL
description     nullable
```

Repository evidence does **not** show:

```text
UNIQUE (unit_type_id, language_code)
CHECK language_code IN ('ar','fr','en')
FOREIGN KEY unit_type_id -> hidra_org_unit_type(id)
seed INSERT rows for unit-type translations
```

Therefore:

- `ar`, `fr`, and `en` are the **target recognized codes from ORG-034**, not a
  claim about existing persisted rows.
- Duplicate rows for the same unit type/language cannot be ruled out from schema.
- Missing `ar/fr/en` rows cannot be measured from Git because no authoritative
  production/sanitized translation dataset is present in the repository.
- Unrecognized/variant codes such as uppercase or regional forms cannot be ruled out.
- The language of `hidra_org_unit_type.description` is ambiguous and must not be
  copied into one localized description column without evidence.
- No live database values were modified or inferred during ORG-035.

Before ORG-036 backfill executes against a populated database, its migration/test plan
must preflight and fail safely on:

1. normalized language-code distribution using `lower(trim(language_code))`;
2. duplicates by `(unit_type_id, normalized language_code)`;
3. orphan `unit_type_id` values;
4. missing `ar`, `fr`, or `en` rows per unit type;
5. conflicting multiple labels/descriptions for one language;
6. non-null legacy base descriptions whose language cannot be proven.

ORG-035 is complete as a repository/schema inventory. Live data quality remains an
explicit ORG-036 deployment precondition and must not be replaced with guessed values.


### ORG-036 implementation result

**Migration:** `V20260927_002__add_embedded_organization_multilingual_fields.sql`

ORG-036 is additive and limited to Organization persistence:

```text
hidra_org_unit_type
  + name_ar / name_fr / name_en
  + description_ar / description_fr / description_en

hidra_org_position
  + description_ar / description_fr / description_en

hidra_org_shift
  + name_ar / name_fr / name_en
```

Before any unit-type backfill, the migration fails transactionally on unsupported
normalized language codes, duplicate `(unit_type_id, language)` rows, orphan unit-type
references, or any existing unit type lacking deterministic `ar`, `fr`, and `en`
translation rows. Recognized language codes are compared using
`lower(btrim(language_code))`.

Only `OrganizationUnitTypeTranslation.label/description` values are backfilled into
the new unit-type fields. The migration intentionally does **not** infer a language for
the legacy base `hidra_org_unit_type.description`, `hidra_org_position.description`,
or `hidra_org_shift.name`.

The following recovery/cutover structures remain unchanged:

```text
hidra_org_unit_type_translation
hidra_org_unit_type.description
hidra_org_position.description
hidra_org_shift.name
```

Focused Testcontainers/Flyway coverage verifies deterministic backfill, case/whitespace
language normalization, preservation of legacy fields/table, duplicate rejection and
transaction rollback, missing-language rejection, orphan rejection, and unsupported
language-code rejection. No domain/JPA/API contract is switched to the new columns in
ORG-036; that belongs to ORG-037.

### ORG-037 implementation result

ORG-037 cuts existing Organization model/persistence contracts over to the fields
introduced by ORG-036:

```text
OrganizationUnitType
  nameAr / nameFr / nameEn
  descriptionAr / descriptionFr / descriptionEn

Position
  titleAr / titleFr / titleEn
  descriptionAr / descriptionFr / descriptionEn

Shift
  nameAr / nameFr / nameEn
```

The language-ambiguous legacy `hidra_org_unit_type.description` and
`hidra_org_position.description` columns remain JPA-mapped read-only so ordinary
repository saves cannot erase recovery data. The legacy non-null
`hidra_org_shift.name` column remains writable only as a compatibility projection:
new saves derive it from English, then French, then Arabic, then the language-neutral
shift code. It is not exposed as canonical domain state.

A live repository trace found no current application input/output contract, REST DTO,
controller endpoint, or administration query that exposes `OrganizationUnitType`,
`Position`, or `Shift` localized content. ORG-037 therefore does not invent new API
surface. Existing organization-unit, employee, and assignment APIs remain unchanged.

No `OrganizationUnitTypeTranslation` code is removed in ORG-037. It remains
transitional until ORG-038.

Focused mapper/domain tests prove trimming/normalization and canonical multilingual
round trips for all three corrected models. There is currently no applicable search
query for unit types, positions, or shifts; existing organization-unit search already
matches `nameAr/nameFr/nameEn` and is unchanged.

### ORG-038 implementation result

ORG-038 retires the obsolete Java translation path after ORG-037 cut organization
runtime contracts over to embedded `Ar/Fr/En` fields.

Removed production types:

```text
domain/model/OrganizationUnitTypeTranslation.java
application/port/out/OrganizationUnitTypeTranslationRepositoryPort.java
infrastructure/persistence/entity/OrganizationUnitTypeTranslationJpaEntity.java
infrastructure/persistence/repository/OrganizationUnitTypeTranslationJpaRepository.java
infrastructure/persistence/adapter/JpaOrganizationUnitTypeTranslationRepositoryAdapter.java
```

`OrganizationPersistenceMapper` no longer contains translation entity/domain
conversion methods, and the unused
`OrganizationPersistence.ORGANIZATION_UNIT_TYPE_TRANSLATION_TABLE` runtime constant
is removed.

Repository-wide production scans before the change found no application service,
inbound use case, REST controller, request/response DTO, administration query, or other
organization production consumer beyond the isolated persistence path above.

ORG-038 intentionally does **not** modify schema or migration history. The legacy
`hidra_org_unit_type_translation` table remains created by the original Flyway
migration and is still referenced by the additive ORG-036 migration and its
PostgreSQL/Testcontainers recovery/parity tests. Table retirement belongs exclusively
to ORG-039 after its separate parity and recovery gate.

### ORG-039 implementation result

**Migration:** `V20260927_003__retire_organization_unit_type_translation_table.sql`

ORG-039 retires only the legacy unit-type translation table. The migration first
performs an in-transaction parity gate over every remaining legacy row:

```text
ar.label        == hidra_org_unit_type.name_ar
fr.label        == hidra_org_unit_type.name_fr
en.label        == hidra_org_unit_type.name_en

ar.description  IS NOT DISTINCT FROM description_ar
fr.description  IS NOT DISTINCT FROM description_fr
en.description  IS NOT DISTINCT FROM description_en
```

Language codes are normalized with `lower(btrim(language_code))`. Unsupported codes,
orphan translation rows, label mismatches, or null-safe description mismatches abort
the migration before the destructive step. When parity holds, the migration executes:

```sql
DROP TABLE hidra_org_unit_type_translation;
```

The embedded multilingual columns are never modified by ORG-039. Unit types created
after the ORG-037 cutover do not need legacy translation rows; an empty legacy table is
therefore valid and can be retired.

The pre-existing ORG-036 migration tests are pinned explicitly to Flyway version
`20260927.002`, preserving their recovery-checkpoint semantics instead of silently
running through the new destructive migration.

Focused PostgreSQL/Testcontainers retirement tests prove:

1. exact `ar/fr/en` parity survives table retirement;
2. embedded-only post-cutover unit types remain valid;
3. label mismatch aborts and preserves the legacy table;
4. description mismatch is checked null-safely and preserves the legacy table;
5. unsupported language codes abort before the drop.

ORG-039 does not drop the separate language-ambiguous legacy columns
`hidra_org_unit_type.description`, `hidra_org_position.description`, or
`hidra_org_shift.name`; their lifecycle is outside this translation-table retirement.

### ORG-040 implementation result

ORG-040 adds a permanent architecture/integrity guardrail rather than another schema
or production-model change.

The test explicitly protects the known Organization translatable attributes:

```text
AdministrativeState.name*
AdministrativeDistrict.name*
AdministrativeLocality.name*
OrganizationUnit.name*
OrganizationUnitType.name*
OrganizationUnitType.description*
Position.title*
Position.description*
Shift.name*
```

For each domain model the guardrail requires the complete Java triplet
`Ar/Fr/En`. For each corresponding JPA entity it additionally requires the matching
database columns `*_ar`, `*_fr`, and `*_en`.

The existing OrganizationUnit create/query/API chain is also checked end-to-end for
`nameAr/nameFr/nameEn` on:

```text
CreateOrganizationUnitRequest
CreateOrganizationUnitCommand
OrganizationUnitSummaryDto
OrganizationUnitResponse
OrganizationAdministrationQueryUseCase.OrganizationUnitView
```

The architecture scan rejects any future Organization production class whose name
contains `Translation` and any Organization production JPA/runtime source mapping
`hidra_org_unit_type_translation`.

Migration-history compatibility is preserved: historical migrations may still contain
the retired table because Flyway history is immutable. The guardrail instead requires
the ORG-039 retirement migration to contain the drop and rejects any migration ordered
after `V20260927_003__retire_organization_unit_type_translation_table.sql` that
references the retired table.

Employee `*Ar/*Lt` proper-name/transliteration fields, address lines, delegation
reasons, responsibility descriptions, hierarchy snapshot descriptions, and other
transactional/free-form text remain intentionally outside this fixed multilingual
catalog guardrail.

After ORG-040 passes its compile/test/clean-verify and CI gates, the Organization
multilingual correction sequence ORG-034 through ORG-040 is complete and normal
roadmap execution may resume from the previously in-progress ORG-027 work.

### Organization dependency-integrity correction sequence

The post-ORG-040 dependency inventory identified two different concerns that must not
be conflated: valid reference/root models with no inbound API, and weakly governed
relationships that still use arbitrary strings or lack same-module referential
enforcement.

Correction policy:

1. finite Organization-owned discriminators become enums or typed value references;
2. same-module references receive application validation and, after orphan preflight,
   database foreign keys/check constraints;
3. cross-module targets remain resolver-backed typed references and never receive
   cross-module database foreign keys;
4. persistence cleanup uses new fail-closed migrations and never edits migration history;
5. deferred capabilities are not deleted merely because they currently lack an inbound API.

ORG-041 begins with responsibility assignees because current live application logic
already recognizes exactly `EMPLOYEE` and `ORGANIZATION_UNIT`. Canonical Java/JPA
state becomes `ResponsibilityAssigneeType`; narrow deprecated textual constructors/
bridges preserve current callers while they are migrated. Stored database values remain
the same because JPA persists the enum with `EnumType.STRING`.

### Dependency-integrity correction status

| Code | Status | Evidence / next gate |
|---|---|---|
| `ORG-041` | Completed | Canonical responsibility assignee state is typed with `ResponsibilityAssigneeType`; textual compatibility bridges are deprecated; JPA uses `EnumType.STRING` so stored values do not change. |
| `ORG-042` | Completed | `ReportingLine` now owns typed `ReportingSubjectReference` source/target values backed by `ReportingSubjectType { EMPLOYEE, POSITION, ORGANIZATION_UNIT }`; JPA persists governed type names through `EnumType.STRING`, with deprecated textual bridges only for compatibility. |
| `ORG-043` | Completed | `OrganizationContactPoint` now owns a typed `ContactPointTargetReference` backed by `ContactPointTargetType { EMPLOYEE, ORGANIZATION_UNIT }`; JPA persists governed names through `EnumType.STRING`, and `OrganizationContactPointTargetValidator` verifies target existence through existing Organization repository ports. |
| `ORG-044` | Completed | `OperationalScopeReference` is now the canonical registration input across command, owner validation, application service, and registry persistence. The returned/generated `OperationalScope.id` remains the separate identity persisted by responsibility assignments; deprecated split type/target bridges remain only for compatibility. |
| `ORG-045` | Completed | `OrganizationCode` is adopted as the canonical normalization/validation policy for stable Organization business codes. Administrative state/district/locality, organization unit type/unit, position, and shift constructors normalize through it; the active `CreateOrganizationUnitCommand` carries `OrganizationCode` directly while REST/persistence/query string contracts remain compatible. |
| `ORG-046` | Completed | Added `V20260927_004__enforce_organization_internal_reference_integrity.sql`: fail-closed orphan/discriminator preflight, 16 same-module `ON DELETE RESTRICT` foreign keys, and closed checks for contact/reporting/responsibility polymorphic types. PostgreSQL/Testcontainers coverage verifies installation and transactional rollback on direct/polymorphic orphan data. |
| `ORG-047` | Completed | Added `V20260927_005__retire_organization_multilingual_compatibility_columns.sql`. Retirement is fail-closed: ambiguous unit-type/position descriptions must already be preserved exactly in an embedded language, and Shift legacy `name` must match the canonical `en -> fr -> ar -> code` compatibility projection. Legacy JPA mappings were removed so existing Hibernate `ddl-auto=validate` smoke coverage validates the post-drop schema. |
| `ORG-048` | Completed | Added `OrganizationTypedDependencyIntegrityTest`: canonical Organization polymorphic domain/JPA state must remain typed, textual compatibility bridges must remain explicitly deprecated for removal, ORG-046 same-module FK/check protection must remain present, ORG-039/047 multilingual retirements may not be reversed, and Organization migrations may not add foreign keys to non-Organization tables. |

### ORG-042 implementation result

ORG-042 replaces arbitrary ReportingLine source/target discriminator strings with a
closed Organization-owned subject contract:

```text
ReportingSubjectType
  EMPLOYEE
  POSITION
  ORGANIZATION_UNIT

ReportingSubjectReference
  type
  targetId
```

`ReportingLine` now stores typed `source` and `target` references. The existing
database columns `source_type/source_id` and `target_type/target_id` are preserved;
the JPA entity maps the type columns with `EnumType.STRING`, so accepted persisted
values remain the same enum names.

No identity or topology subject type is introduced. Repository evidence describes
ReportingLine as a relation between positions, units, or employees, and ORG-042 keeps
that boundary exact. Deprecated textual constructors/accessors remain only to avoid a
hard compatibility break while callers migrate.

No Flyway migration is added in ORG-042. Existing-data discriminator preflight and
database CHECK/FK enforcement belong to ORG-046. Consequently, any historical row with
a discriminator outside EMPLOYEE/POSITION/ORGANIZATION_UNIT remains a deployment data
quality risk until that preflight is executed.

Focused domain tests reject unsupported/blank textual discriminators and normalize
target IDs. Persistence-mapper tests prove typed domain references round-trip through
the existing VARCHAR representation without adding cross-module ownership.

### ORG-043 implementation result

ORG-043 replaces the arbitrary `OrganizationContactPoint.targetType/targetId` pair
with an Organization-owned typed reference:

```text
ContactPointTargetType
  EMPLOYEE
  ORGANIZATION_UNIT

ContactPointTargetReference
  type
  targetId
```

The closed set intentionally excludes Position, Identity, Topology, Party, and other
bounded-context targets. Contact points remain Organization-owned operational contact
data rather than a generic cross-module reference mechanism.

`OrganizationContactPoint` now stores a canonical typed `target`. The existing
database `target_type` and `target_id` columns remain unchanged; JPA maps
`target_type` with `EnumType.STRING`, preserving the accepted EMPLOYEE and
ORGANIZATION_UNIT textual representation. Deprecated textual constructor/accessors
remain only for compatibility.

Because the module currently has no inbound contact-point write use case or REST
endpoint, ORG-043 does not invent one. Instead it adds
`OrganizationContactPointTargetValidator`, an application-layer prerequisite that
verifies target existence through the existing EmployeeRepositoryPort and
OrganizationUnitRepositoryPort. It validates existence only; no undocumented employee
or unit lifecycle rule is invented.

No Flyway migration is added in ORG-043. Existing-data target-type preflight and
database CHECK/FK enforcement belong to ORG-046. Any historical contact-point row with
an unsupported target_type therefore remains a deployment data-quality risk until that
migration preflight is executed.

Focused domain tests verify the closed target set and ID normalization; application
tests verify repository-specific existence checks; persistence tests prove typed
round-trip through the current VARCHAR representation.

### ORG-044 implementation result

ORG-044 makes the existing `OperationalScopeReference` value object the canonical
registration input from application command through owner validation and registry
persistence:

```text
RegisterOperationalScopeCommand
        |
        v
OperationalScopeReference
  type
  targetId
        |
        v
OperationalScopeRegistrationValidator
        |
        v
OperationalScopeRegistryRepositoryPort.register(reference)
        |
        v
OperationalScope
  id          <- generated registry identity
  type
  targetId
```

The correction removes duplicate local scope-shape validation from the command,
validator, and JPA adapter. `OperationalScopeReference` remains responsible for
GLOBAL/entity-backed/CUSTOM shape rules; the application validator remains responsible
for owner resolver support, exact identity, existence, and assignability.

The registry adapter now receives the validated reference as one value. It still
persists the same columns and returns an `OperationalScope` with a positive,
database-generated `id`. Responsibility assignments remain keyed by that generated
scope ID; they do not persist an owner-native target ID as responsibility identity.

Deprecated split `type/targetId` command, validator, and repository bridges remain
only for compatibility with existing callers/anonymous repository implementations.
New code is expected to use `OperationalScopeReference` directly.

No Flyway migration, schema change, REST endpoint, topology import, or responsibility
storage change is part of ORG-044.

Focused tests prove that the canonical reference survives command/validation into the
repository unchanged, GLOBAL bypasses external owner lookup, rejected owners never
reach persistence, and the returned registry ID remains independent of the owner target
identifier.

### ORG-045 implementation result

Repository review found that `OrganizationCode` was a genuine orphan value object even
though seven Organization reference/master models already expose stable,
language-neutral `code` columns that are all `NOT NULL` in the schema:

```text
AdministrativeState.code
AdministrativeDistrict.code
AdministrativeLocality.code
OrganizationUnitType.code
OrganizationUnit.code
Position.code
Shift.code
```

ORG-045 adopts `OrganizationCode` as their single normalization/validation policy.
All seven domain constructors now route code input through
`OrganizationCode.of(code).value()`, which rejects null/blank values, trims boundary
whitespace, and normalizes letters with `Locale.ROOT` upper case.

The public domain record components remain `String code` for these existing models.
Changing all seven accessor types to `OrganizationCode` would create broad persistence,
query, DTO, and compatibility churn without adding business value at this stage.
Instead, the active organization-unit write boundary adopts the value type directly:

```text
CreateOrganizationUnitRequest.code : String
        |
        v
OrganizationRestMapper
        |
        v
OrganizationCode
        |
        v
CreateOrganizationUnitCommand.code : OrganizationCode
        |
        v
OrganizationUnit.code : normalized String
```

A deprecated textual command constructor remains temporarily for internal compatibility.
API/OpenAPI request and response shapes remain textual and therefore do not change.

The value object is intentionally not applied to employee numbers, postal codes,
database IDs, topology resolver display codes, or polymorphic target IDs because those
belong to different identifier semantics.

No Flyway migration is added in ORG-045. Existing rows are not rewritten here; any
database-level canonical-code preflight or constraint can be considered together with
the ORG-046 integrity migration. Reads through the domain will normalize code values,
but the underlying historical stored value remains untouched until an explicit data
migration is approved.

Focused tests verify OrganizationCode normalization/rejection, all seven supported
model constructors, and typed adoption at the organization-unit creation command.

### ORG-046 implementation result

**Migration:** `V20260927_004__enforce_organization_internal_reference_integrity.sql`

ORG-046 adds database integrity only for references owned by the Organization module.
Before installing any constraint, one transactional preflight rejects direct orphan rows,
unsupported finite discriminators, and orphan polymorphic Organization targets.

Installed direct same-module foreign keys use `ON DELETE RESTRICT` for:

```text
AdministrativeDistrict.stateId -> AdministrativeState
AdministrativeLocality.districtId -> AdministrativeDistrict
EmployeeAddress.employeeId -> Employee
EmployeeAddress.localityId -> AdministrativeLocality
OrganizationUnit.unitTypeId -> OrganizationUnitType
OrganizationUnit.parentUnitId -> OrganizationUnit
EmployeeAssignment.employeeId -> Employee
EmployeeAssignment.organizationUnitId -> OrganizationUnit
EmployeeAssignment.positionId -> Position
ShiftAssignment.employeeId -> Employee
ShiftAssignment.shiftId -> Shift
ShiftAssignment.organizationUnitId -> OrganizationUnit
OrganizationDelegation.delegatorEmployeeId -> Employee
OrganizationDelegation.delegateEmployeeId -> Employee
OrganizationDelegation.responsibilityAssignmentId -> ResponsibilityAssignment
OrganizationHierarchySnapshot.capturedByEmployeeId -> Employee
```

Closed discriminator checks govern the polymorphic references already typed by earlier
Organization corrections:

```text
OrganizationContactPoint.targetType
  EMPLOYEE | ORGANIZATION_UNIT

ReportingLine.sourceType / targetType
  EMPLOYEE | POSITION | ORGANIZATION_UNIT

ResponsibilityAssignment.assigneeType
  EMPLOYEE | ORGANIZATION_UNIT
```

The migration also preflights target existence for those polymorphic references before
constraint installation. Their target IDs are intentionally not modeled with cross-table
or cross-module database foreign keys because one column can reference multiple
Organization tables and external ownership remains resolver-backed.

No topology, identity, pipeline, facility, equipment, or other bounded-context foreign
key is introduced. The existing canonical
`ResponsibilityAssignment.scopeId -> hidra_org_operational_scope.id` foreign key from
the operational-scope migration remains unchanged.

Focused PostgreSQL/Testcontainers tests prove direct FK/check installation, rejection of
invalid discriminator writes, and transaction rollback with source data preserved when
either a direct orphan or a polymorphic orphan is present.

### ORG-047 implementation result

**Migration:** `V20260927_005__retire_organization_multilingual_compatibility_columns.sql`

ORG-047 retires exactly the three remaining language-ambiguous compatibility columns:

```text
hidra_org_unit_type.description
hidra_org_position.description
hidra_org_shift.name
```

The migration does not infer or translate any value. It fails transactionally before
the first DROP when a non-null legacy unit-type or position description is not already
preserved exactly in at least one corresponding embedded
`descriptionAr/descriptionFr/descriptionEn` field.

For Shift, retirement requires the legacy `name` to match the exact cutover projection
used by runtime compatibility writes: first nonblank English, then French, then Arabic,
then the stable code. Embedded names are boundary-trimmed for comparison because the
runtime compatibility projection trimmed the selected value.

The obsolete JPA mappings/accessors are removed from
`OrganizationUnitTypeJpaEntity`, `PositionJpaEntity`, and `ShiftJpaEntity`.
No domain, application, or REST multilingual contract changes are introduced.

Focused PostgreSQL/Testcontainers tests cover successful retirement and fail-closed
rollback for mismatched unit-type descriptions, position descriptions, and shift names.
The repository Boot smoke test already uses PostgreSQL/Testcontainers with
`spring.jpa.hibernate.ddl-auto=validate`, providing Hibernate validation against the
post-retirement schema in normal CI.

CI #380 initially failed during test compilation because the pre-existing multilingual
persistence mapper test still asserted the deliberately removed `ShiftJpaEntity.legacyName()`
compatibility accessor. The ORG-047 repair removes that obsolete assertion and renames the
test to describe post-retirement behavior; no production or migration semantics change.

### ORG-048 implementation result

**Guardrail:** `OrganizationTypedDependencyIntegrityTest`

ORG-048 closes the typed-dependency correction sequence with repository-level tests only;
it does not change runtime behavior or schema.

The guardrail fixes the following invariants:

```text
Domain canonical polymorphic state
  OrganizationContactPoint.target -> ContactPointTargetReference
  ReportingLine.source/target -> ReportingSubjectReference
  ResponsibilityAssignment.assigneeType -> ResponsibilityAssigneeType

JPA canonical finite discriminators
  OrganizationContactPointJpaEntity.targetType -> ContactPointTargetType
  ReportingLineJpaEntity.sourceType/targetType -> ReportingSubjectType
  ResponsibilityAssignmentJpaEntity.assigneeType -> ResponsibilityAssigneeType
  all persisted with EnumType.STRING
```

Existing textual migration bridges are not deleted by this task because earlier roadmap
steps deliberately retained them for compatibility. ORG-048 instead requires every
wide textual constructor/accessor bridge in the governed models/entities to remain
explicitly `@Deprecated(forRemoval = true)`, preventing a raw-string compatibility path
from silently becoming canonical again.

Database guardrails assert that the ORG-046 same-module referential-integrity migration
continues to contain all 16 Organization-owned foreign keys and the four governed
polymorphic discriminator checks, together with its fail-closed preflight contract.

Multilingual guardrails assert that the ORG-047 JPA compatibility fields remain absent,
that the retirement migration still drops the three legacy columns, and that no later
migration restores those columns or the ORG-039 retired translation table.

Finally, every migration marked `Module: organization` is scanned for foreign keys to
non-`hidra_org_*` tables. This preserves the architecture rule that topology, identity,
and other bounded contexts are resolved through public contracts/stable references rather
than database ownership from Organization.

Normal CI provides the requested full compile/test/clean-verify gate for this final
guardrail task.

### Multilingual correction status

| Code | Status | Evidence / next gate |
|---|---|---|
| `ORG-034` | Completed | Organization-only embedded multilingual decision and safe correction sequence recorded in this roadmap. |
| `ORG-035` | Completed | Repository/schema inventory completed on `9f88b07d...`: aligned embedded fields identified, `OrganizationUnitTypeTranslation` consumer path isolated, `Shift.name` and `Position.description` gaps recorded, and live-data preflight requirements documented without guessing data. |
| `ORG-036` | Completed | Added `V20260927_002__add_embedded_organization_multilingual_fields.sql` plus PostgreSQL/Testcontainers migration coverage. Unit-type `ar/fr/en` values backfill only after fail-closed preflight; Position description and Shift name receive nullable embedded language columns without guessed backfill; all legacy columns/table are retained for cutover and recovery. |
| `ORG-037` | Completed | `OrganizationUnitType`, `Position`, and `Shift` now use embedded Arabic/French/English domain and JPA fields; persistence mapping round-trips canonical multilingual content. Legacy ambiguous descriptions remain read-only in JPA and Shift maintains its legacy non-null `name` only as a compatibility projection. Current main exposes no unit-type/position/shift application or REST content contract, so no speculative API was added. |
| `ORG-038` | Completed | Retired the separate `OrganizationUnitTypeTranslation` domain model, outbound repository port, JPA entity/repository/adapter, mapper conversions, and unused runtime table constant. Repository scans show no remaining organization production consumer; the physical legacy table and Flyway history remain intact for ORG-039 recovery gating. |
| `ORG-039` | Completed | Added `V20260927_003__retire_organization_unit_type_translation_table.sql`. The migration performs a fail-closed, null-safe parity check between every remaining normalized `ar/fr/en` legacy row and embedded names/descriptions before dropping the table. Testcontainers coverage proves successful retirement, embedded-only post-cutover rows, and transactional rollback with the recovery table intact on label/description/language mismatches. |
| `ORG-040` | Completed | Added final Organization multilingual architecture/integrity guardrails. Explicit domain/JPA `Ar/Fr/En` triplets are verified for the known translatable models, the existing OrganizationUnit application/API chain is checked for all three names, separate Organization translation production types/JPA mappings are forbidden, and migrations after `V20260927_003` may not reintroduce the retired translation table. |

**Execution priority:** correction sequences ORG-034 through ORG-048 are complete and
must not be reopened without new repository evidence. The next Organization work is the
remaining ORG-027 identity-authorization and workflow/audit integration gate. After that,
reconcile the still-open ORG-028/029/030/031/032/033 gates in dependency-safe order against
current `main`. Do not execute destructive schema retirement before reconciliation,
consumer/API cutover and recovery evidence are complete.

