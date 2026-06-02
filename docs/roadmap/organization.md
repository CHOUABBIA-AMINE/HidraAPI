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
| UpdatedOn | 2026-05-30 |
| Status | Ready for AI-agent execution after kernel baseline |
| Execution mode | One commit code at a time |

---

## 2. Organization Module Mission

The `organization` module owns the HidraAPI business organization model.

It answers:

```text
Who are the real operational people?
Which organization unit do they belong to?
Which position do they hold?
Who supervises whom?
How is the organization structured?
```

The organization module owns:

```text
employees
organization units
positions
employee assignments
supervisor relationships
organization hierarchy
employee lifecycle
organization unit lifecycle
```

The organization module does not own:

```text
login users
roles
permissions
permission evaluation
Spring Security plumbing
```

Those belong to:

```text
dz.sh.hidra.modules.identity
dz.sh.hidra.platform.security
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
| Position | `modules.organization` |
| Supervisor relationship | `modules.organization` |
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

The organization module may use kernel primitives:

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
SupervisorAssignment model
organization hierarchy policies
employee lifecycle rules
organization unit lifecycle rules
organization domain events
organization application commands and queries
organization use-case ports
organization application services
organization REST controllers
organization request/response DTOs
organization persistence entities
organization JPA adapters
organization database migrations
organization unit/application/persistence/API tests
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
complex matrix organization
identity synchronization
external HR integration
organization-scoped permission evaluation
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

This applies to:

```text
class
record
interface
enum
annotation
package-info.java
```

Required JavaDoc structure:

```java
/**
 * <One-sentence technical responsibility.>
 *
 * <p>Business role:
 * <Explain what this type means in Hidra organization, employee, unit, position, or hierarchy management.>
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

Domain objects must protect invariants even if called outside REST APIs.

Required examples:

```text
Employee number cannot be blank.
Organization unit code must be valid.
A disabled organization unit cannot receive new employee assignments.
An employee cannot report to themselves.
An organization unit cannot be its own parent.
Organization hierarchy cannot contain cycles.
A position code must be valid.
```

Bean Validation annotations are not allowed in domain model classes.

### 9.4 Domain value documentation and validation rules

Every value object under:

```text
src/main/java/dz/sh/hidra/modules/organization/domain/value
```

must have JavaDoc explaining:

```text
business meaning
accepted format
validation rules
normalization rules
usage restrictions
```

Specific required rules:

| Value object | Required validation/documentation |
|---|---|
| `EmployeeNumber` | Document uniqueness and allowed characters |
| `EmployeeFullName` | Document min/max length and trimming |
| `OrganizationUnitCode` | Document uppercase snake-case or business code format |
| `OrganizationUnitName` | Document min/max length |
| `PositionCode` | Document uppercase snake-case format |
| `PositionTitle` | Document min/max length |
| `IdentityUserReference` | Document that it references `modules.identity` without importing identity domain classes |

### 9.5 DTO documentation rules

Every application DTO and REST DTO under:

```text
src/main/java/dz/sh/hidra/modules/organization/application/dto
src/main/java/dz/sh/hidra/modules/organization/api/rest/request
src/main/java/dz/sh/hidra/modules/organization/api/rest/response
```

must have class-level JavaDoc explaining:

```text
purpose
source/target layer
field meaning
validation constraints
whether it is input or output
```

Request DTOs should preferably be Java records.

For record DTOs, document every component using `@param`.

### 9.6 DTO validation rules

REST request DTOs must use Bean Validation annotations.

Allowed examples:

```text
@NotNull
@NotBlank
@Size
@Pattern
@Valid
```

Validation belongs to request DTOs only at the API boundary.

Nested request DTOs must use `@Valid`.

Controllers must use `@Valid` on request bodies.

Do not use Bean Validation annotations in domain model classes.

### 9.7 Controller documentation rules

Every controller under:

```text
src/main/java/dz/sh/hidra/modules/organization/api/rest/controller
```

must have:

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

This section is mandatory for all AI agents implementing the `organization` module.

If any earlier section treats Swagger/OpenAPI documentation as optional, this section overrides it.

### 10.1 Required dependency assumption

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

Allowed OpenAPI imports in API layer:

```java
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
```

### 10.2 `@Schema` requirement for REST DTOs

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
requiredMode when the field is mandatory
maxLength/minLength when relevant
pattern when relevant
allowableValues when the value is constrained
```

Swagger `@Schema` documentation must not contradict Bean Validation.

For every request DTO:

| Bean Validation | Matching `@Schema` expectation |
|---|---|
| `@NotBlank` / `@NotNull` | `requiredMode = Schema.RequiredMode.REQUIRED` |
| `@Size(min = x, max = y)` | `minLength = x`, `maxLength = y` when applicable |
| `@Pattern(regexp = "...")` | `pattern = "..."` |
| enum-like values | `allowableValues = {...}` |

Recommended examples:

| Field | Example |
|---|---|
| `employeeNumber` | `EMP-000123` |
| `fullName` | `Abir MEDJERAB` |
| `email` | `abir.medjerab@example.com` |
| `organizationUnitCode` | `TRC_DIGITALIZATION` |
| `organizationUnitName` | `TRC Digitalization Initiative` |
| `positionCode` | `PIPELINE_OPERATIONS_ENGINEER` |
| `positionTitle` | `Pipeline Operations Engineer` |
| `employmentStatus` | `ACTIVE` |
| `unitStatus` | `ACTIVE` |
| `effectiveFrom` | `2026-05-30` |
| `effectiveTo` | `2026-12-31` |

### 10.3 Controller Swagger documentation rules

Every controller must use:

```java
@Tag
```

at class level.

Every endpoint method must use:

```java
@Operation
@ApiResponses
```

Path variables and request parameters should use:

```java
@Parameter
```

when their business meaning is not obvious.

`ORG-015` is incomplete unless all request DTOs, response DTOs, and controllers satisfy these Swagger rules.

---

## 11. Recommended v1 Domain Model

Implement organization v1 with:

```text
Aggregates:
- Employee
- OrganizationUnit

Domain models/entities:
- Position
- EmployeeAssignment
- SupervisorAssignment
```

Value objects:

```text
EmployeeId
EmployeeNumber
EmployeeFullName
EmployeeEmail
EmploymentStatus
OrganizationUnitId
OrganizationUnitCode
OrganizationUnitName
OrganizationUnitStatus
PositionId
PositionCode
PositionTitle
AssignmentId
IdentityUserReference
```

Delay:

```text
deep HR features
payroll
leave management
complex matrix organization
identity synchronization
external HR integration
```

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
│       │   └── package-info.java
│       ├── mapper
│       │   ├── OrganizationRestMapper.java
│       │   └── package-info.java
│       ├── request
│       │   ├── AssignEmployeeToUnitRequest.java
│       │   ├── CreateEmployeeRequest.java
│       │   ├── CreateOrganizationUnitRequest.java
│       │   ├── CreatePositionRequest.java
│       │   ├── SetEmployeeSupervisorRequest.java
│       │   ├── UpdateEmployeeRequest.java
│       │   ├── UpdateOrganizationUnitRequest.java
│       │   └── package-info.java
│       └── response
│           ├── EmployeeAssignmentResponse.java
│           ├── EmployeeResponse.java
│           ├── OrganizationUnitResponse.java
│           ├── PositionResponse.java
│           └── package-info.java
├── application
│   ├── package-info.java
│   ├── command
│   │   ├── AssignEmployeeToUnitCommand.java
│   │   ├── CreateEmployeeCommand.java
│   │   ├── CreateOrganizationUnitCommand.java
│   │   ├── CreatePositionCommand.java
│   │   ├── SetEmployeeSupervisorCommand.java
│   │   ├── UpdateEmployeeCommand.java
│   │   ├── UpdateOrganizationUnitCommand.java
│   │   └── package-info.java
│   ├── dto
│   │   ├── EmployeeAssignmentDto.java
│   │   ├── EmployeeDto.java
│   │   ├── OrganizationUnitDto.java
│   │   ├── PositionDto.java
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
│   │   │   ├── SetEmployeeSupervisorUseCase.java
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
│       ├── SetEmployeeSupervisorService.java
│       └── package-info.java
├── domain
│   ├── package-info.java
│   ├── event
│   │   ├── EmployeeAssignedToUnitEvent.java
│   │   ├── EmployeeCreatedEvent.java
│   │   ├── EmployeeSupervisorChangedEvent.java
│   │   ├── OrganizationUnitCreatedEvent.java
│   │   ├── PositionCreatedEvent.java
│   │   └── package-info.java
│   ├── exception
│   │   ├── EmployeeAssignmentNotAllowedException.java
│   │   ├── EmployeeLifecycleException.java
│   │   ├── OrganizationDomainException.java
│   │   ├── OrganizationHierarchyException.java
│   │   └── package-info.java
│   ├── model
│   │   ├── Employee.java
│   │   ├── EmployeeAssignment.java
│   │   ├── OrganizationUnit.java
│   │   ├── Position.java
│   │   ├── SupervisorAssignment.java
│   │   └── package-info.java
│   ├── policy
│   │   ├── EmployeeAssignmentPolicy.java
│   │   ├── EmployeeLifecyclePolicy.java
│   │   ├── OrganizationHierarchyPolicy.java
│   │   └── package-info.java
│   ├── repository
│   │   ├── EmployeeDomainRepository.java
│   │   ├── OrganizationUnitDomainRepository.java
│   │   ├── PositionCatalog.java
│   │   └── package-info.java
│   ├── service
│   │   ├── EmployeeAssignmentDomainService.java
│   │   ├── OrganizationHierarchyDomainService.java
│   │   └── package-info.java
│   └── value
│       ├── AssignmentId.java
│       ├── EmployeeEmail.java
│       ├── EmployeeFullName.java
│       ├── EmployeeId.java
│       ├── EmployeeNumber.java
│       ├── EmploymentStatus.java
│       ├── IdentityUserReference.java
│       ├── OrganizationUnitCode.java
│       ├── OrganizationUnitId.java
│       ├── OrganizationUnitName.java
│       ├── OrganizationUnitStatus.java
│       ├── PositionCode.java
│       ├── PositionId.java
│       ├── PositionTitle.java
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
        │   ├── SupervisorAssignmentJpaEntity.java
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

## 13. Final Organization Test File Tree

Do not create test `package-info.java` files.

```text
src/test/java/dz/sh/hidra/modules/organization
├── OrganizationArchitectureTest.java
├── api/rest/controller
│   ├── EmployeeControllerTest.java
│   ├── OrganizationUnitControllerTest.java
│   └── PositionControllerTest.java
├── application/service
│   ├── AssignEmployeeToUnitServiceTest.java
│   ├── CreateEmployeeServiceTest.java
│   ├── CreateOrganizationUnitServiceTest.java
│   └── SetEmployeeSupervisorServiceTest.java
├── domain/model
│   ├── EmployeeTest.java
│   └── OrganizationUnitTest.java
├── domain/policy
│   ├── EmployeeAssignmentPolicyTest.java
│   ├── EmployeeLifecyclePolicyTest.java
│   └── OrganizationHierarchyPolicyTest.java
├── domain/value
│   ├── EmployeeNumberTest.java
│   ├── OrganizationUnitCodeTest.java
│   └── PositionCodeTest.java
└── infrastructure/persistence
    ├── EmployeeRepositoryAdapterTest.java
    └── OrganizationUnitRepositoryAdapterTest.java
```

---

## 14. Commit Plan Overview

| Commit code | Commit message | Purpose |
|---|---|---|
| `ORG-001` | `docs(organization): add organization roadmap` | Add this roadmap file |
| `ORG-002` | `chore(organization): add organization package skeleton` | Add production `package-info.java` files only |
| `ORG-003` | `feat(organization): add organization domain value objects` | Add IDs, codes, names, statuses, references |
| `ORG-004` | `feat(organization): add position domain model` | Add position model and catalog |
| `ORG-005` | `feat(organization): add organization unit domain model` | Add organization unit aggregate and hierarchy rules |
| `ORG-006` | `feat(organization): add employee domain model` | Add employee aggregate and assignment model |
| `ORG-007` | `feat(organization): add organization domain exceptions` | Add organization-specific exceptions |
| `ORG-008` | `feat(organization): add organization domain events` | Add employee/unit/position events |
| `ORG-009` | `feat(organization): add organization domain policies and services` | Add hierarchy, lifecycle, assignment policies/services |
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

# 15. Detailed Commit Specifications

## ORG-001 — Add Organization Roadmap

### Commit message

```text
docs(organization): add organization roadmap
```

### Description

Create the roadmap file used by AI agents and developers to implement the `organization` module safely.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `docs/roadmap/organization.md` | Organization implementation plan and execution memory |

### Acceptance criteria

- File exists at exactly `docs/roadmap/organization.md`.
- File contains commit codes, messages, descriptions, files, file purposes, acceptance criteria, and validation commands.
- No Java source files are created in this commit.

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

### Description

Add immutable domain value objects used by employees, organization units, positions, and assignments.

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
| Create | `domain/value/PositionId.java` | Stable position identifier |
| Create | `domain/value/PositionCode.java` | Validated position code |
| Create | `domain/value/PositionTitle.java` | Validated position title |
| Create | `domain/value/AssignmentId.java` | Stable assignment identifier |
| Create | `domain/value/IdentityUserReference.java` | Neutral reference to identity user without importing identity domain |
| Update | `docs/roadmap/organization.md` | Mark `ORG-003` as completed after execution |

### Acceptance criteria

- Value objects are immutable.
- Invalid inputs are rejected.
- Every value object has class-level JavaDoc.
- Record components are documented using `@param` JavaDoc.
- No Spring/JPA imports.
- No identity/identityaccess imports.
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

### Description

Add position model and position catalog contract.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/model/Position.java` | Domain model for an organizational position/function |
| Create | `domain/repository/PositionCatalog.java` | Domain contract for position lookup/catalog operations |
| Update | `docs/roadmap/organization.md` | Mark `ORG-004` as completed after execution |

### Acceptance criteria

- Position model is documented.
- Position model is generic to organization.
- No persistence annotations.
- No framework dependency.
- Compile passes.

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

### Description

Add organization unit aggregate and hierarchy rules.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/model/OrganizationUnit.java` | Organization unit aggregate |
| Create | `domain/repository/OrganizationUnitDomainRepository.java` | Domain repository contract for organization units |
| Update | `docs/roadmap/organization.md` | Mark `ORG-005` as completed after execution |

### Acceptance criteria

- Organization unit is aggregate root.
- Hierarchy references are controlled.
- Self-parenting is rejected.
- No persistence/framework dependency.
- JavaDoc documents lifecycle and hierarchy rules.
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

### Description

Add employee aggregate, employee assignment, and supervisor assignment models.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/model/Employee.java` | Employee aggregate representing a real operational person |
| Create | `domain/model/EmployeeAssignment.java` | Assignment of employee to unit/position |
| Create | `domain/model/SupervisorAssignment.java` | Supervisor relationship for an employee |
| Create | `domain/repository/EmployeeDomainRepository.java` | Domain repository contract for employees |
| Update | `docs/roadmap/organization.md` | Mark `ORG-006` as completed after execution |

### Acceptance criteria

- Employee is aggregate root.
- Lifecycle transitions are controlled.
- Role/user/permission logic is not introduced.
- No identity domain import exists.
- No persistence/framework dependency.
- JavaDoc documents lifecycle and assignment rules.
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

### Description

Add organization-specific domain exceptions.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/exception/OrganizationDomainException.java` | Base exception for organization domain failures |
| Create | `domain/exception/EmployeeLifecycleException.java` | Exception for invalid employee lifecycle transition |
| Create | `domain/exception/EmployeeAssignmentNotAllowedException.java` | Exception for invalid employee assignment |
| Create | `domain/exception/OrganizationHierarchyException.java` | Exception for invalid organization hierarchy |
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
| Create | `domain/event/EmployeeSupervisorChangedEvent.java` | Published when supervisor assignment changes |
| Create | `domain/event/OrganizationUnitCreatedEvent.java` | Published when an organization unit is created |
| Create | `domain/event/PositionCreatedEvent.java` | Published when a position is created |
| Update | `docs/roadmap/organization.md` | Mark `ORG-008` as completed after execution |

### Acceptance criteria

- Events are immutable.
- Events are safe.
- Events do not import platform.
- Events do not import identity.
- Compile passes.

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
| Create | `domain/service/OrganizationHierarchyDomainService.java` | Coordinates hierarchy validation rules |
| Create | `domain/service/EmployeeAssignmentDomainService.java` | Coordinates employee assignment domain rules |
| Update | `docs/roadmap/organization.md` | Mark `ORG-009` as completed after execution |

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
| Create | `application/command/CreateOrganizationUnitCommand.java` | Input for creating an organization unit |
| Create | `application/command/UpdateOrganizationUnitCommand.java` | Input for updating an organization unit |
| Create | `application/command/CreatePositionCommand.java` | Input for creating a position |
| Create | `application/command/AssignEmployeeToUnitCommand.java` | Input for assigning employee to unit/position |
| Create | `application/command/SetEmployeeSupervisorCommand.java` | Input for setting supervisor relationship |
| Create | `application/query/GetEmployeeByIdQuery.java` | Query for one employee |
| Create | `application/query/ListEmployeesQuery.java` | Query for employee list/search |
| Create | `application/query/GetOrganizationUnitByIdQuery.java` | Query for one organization unit |
| Create | `application/query/ListOrganizationUnitsQuery.java` | Query for organization unit list |
| Create | `application/query/ListPositionsQuery.java` | Query for position catalog |
| Update | `docs/roadmap/organization.md` | Mark `ORG-010` as completed after execution |

### Acceptance criteria

- Commands/queries compile.
- Every command/query has JavaDoc and `@param` docs.
- No service implementation yet.
- No framework dependency.
- Compile passes.

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
| Create | `application/port/in/SetEmployeeSupervisorUseCase.java` | Inbound port for supervisor assignment |
| Create | `application/port/out/EmployeeRepository.java` | Outbound employee persistence port |
| Create | `application/port/out/OrganizationUnitRepository.java` | Outbound organization unit persistence port |
| Create | `application/port/out/PositionRepository.java` | Outbound position persistence port |
| Create | `application/port/out/DomainEventPublisherPort.java` | Outbound domain event publication abstraction |
| Create | `application/dto/EmployeeDto.java` | Application employee DTO |
| Create | `application/dto/OrganizationUnitDto.java` | Application organization unit DTO |
| Create | `application/dto/PositionDto.java` | Application position DTO |
| Create | `application/dto/EmployeeAssignmentDto.java` | Application employee assignment DTO |
| Create | `application/mapper/OrganizationApplicationMapper.java` | Maps domain objects to application DTOs |
| Update | `docs/roadmap/organization.md` | Mark `ORG-011` as completed after execution |

### Acceptance criteria

- Ports compile and have JavaDoc.
- DTOs compile and have class/component JavaDoc.
- Application layer does not depend on API or infrastructure.
- Compile passes.

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
| Create | `application/service/SetEmployeeSupervisorService.java` | Implements supervisor assignment use case |
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
| Create | `infrastructure/persistence/entity/OrganizationUnitJpaEntity.java` | JPA representation of organization unit |
| Create | `infrastructure/persistence/entity/PositionJpaEntity.java` | JPA representation of position |
| Create | `infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java` | JPA representation of employee assignment |
| Create | `infrastructure/persistence/entity/SupervisorAssignmentJpaEntity.java` | JPA representation of supervisor assignment |
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
hidra_org_supervisor_assignment
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
| Create | `api/rest/request/CreateOrganizationUnitRequest.java` | Request body for creating organization unit |
| Create | `api/rest/request/UpdateOrganizationUnitRequest.java` | Request body for updating organization unit |
| Create | `api/rest/request/CreatePositionRequest.java` | Request body for creating position |
| Create | `api/rest/request/AssignEmployeeToUnitRequest.java` | Request body for assigning employee to unit/position |
| Create | `api/rest/request/SetEmployeeSupervisorRequest.java` | Request body for setting supervisor |
| Create | `api/rest/response/EmployeeResponse.java` | REST employee response |
| Create | `api/rest/response/OrganizationUnitResponse.java` | REST organization unit response |
| Create | `api/rest/response/PositionResponse.java` | REST position response |
| Create | `api/rest/response/EmployeeAssignmentResponse.java` | REST employee assignment response |
| Create | `api/rest/mapper/OrganizationRestMapper.java` | Maps REST request/response to application commands/DTOs |
| Create | `api/rest/controller/EmployeeController.java` | Employee REST endpoints |
| Create | `api/rest/controller/OrganizationUnitController.java` | Organization unit REST endpoints |
| Create | `api/rest/controller/PositionController.java` | Position REST endpoints |
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
POST   /api/v1/organization/employees/{employeeId}/supervisor

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
| Create | `domain/value/PositionCodeTest.java` | Verifies position code validation |
| Create | `domain/model/EmployeeTest.java` | Verifies employee lifecycle and assignment rules |
| Create | `domain/model/OrganizationUnitTest.java` | Verifies organization unit lifecycle and hierarchy rules |
| Create | `domain/policy/EmployeeAssignmentPolicyTest.java` | Verifies employee assignment rules |
| Create | `domain/policy/EmployeeLifecyclePolicyTest.java` | Verifies employee lifecycle rules |
| Create | `domain/policy/OrganizationHierarchyPolicyTest.java` | Verifies hierarchy constraints |
| Update | `docs/roadmap/organization.md` | Mark `ORG-016` as completed after execution |

### Validation

```bash
mvn -q test -Dtest='*Organization*,*EmployeeTest,*OrganizationUnitTest,*EmployeeNumberTest,*OrganizationUnitCodeTest,*PositionCodeTest,*EmployeeAssignmentPolicyTest,*OrganizationHierarchyPolicyTest'
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
| Create | `application/service/CreateOrganizationUnitServiceTest.java` | Verifies organization unit creation use case |
| Create | `application/service/AssignEmployeeToUnitServiceTest.java` | Verifies employee assignment use case |
| Create | `application/service/SetEmployeeSupervisorServiceTest.java` | Verifies supervisor assignment use case |
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
| Create | `infrastructure/persistence/OrganizationUnitRepositoryAdapterTest.java` | Verifies organization unit persistence adapter |
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
| Create | `api/rest/controller/OrganizationUnitControllerTest.java` | Verifies organization unit REST endpoints |
| Create | `api/rest/controller/PositionControllerTest.java` | Verifies position REST endpoints |
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
[ ] Organization application has no API dependency
[ ] Organization application has no infrastructure dependency
[ ] Organization API has no repository dependency
[ ] Employee aggregate exists
[ ] OrganizationUnit aggregate exists
[ ] Position model exists
[ ] Employee assignment policy works
[ ] Organization hierarchy policy works
[ ] REST API compiles
[ ] Domain models have class-level and field/component documentation
[ ] Domain model validation does not use Bean Validation
[ ] Domain value objects have validation and format documentation
[ ] Application DTOs have class-level and component documentation
[ ] REST request DTOs have Bean Validation annotations
[ ] REST request DTOs have class-level and component documentation
[ ] REST response DTOs have class-level and component documentation
[ ] REST request DTOs use @Schema at class level
[ ] REST request DTO fields/components use @Schema
[ ] REST response DTOs use @Schema at class level
[ ] REST response DTO fields/components use @Schema
[ ] @Schema documentation aligns with Bean Validation constraints
[ ] @Schema examples are realistic and contain no secrets
[ ] Organization controllers use @Tag
[ ] Organization controller endpoint methods use @Operation
[ ] Organization controller endpoint methods use @ApiResponses
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

## 16. AI Agent Execution Rules

Any AI agent executing this roadmap must follow these rules:

1. Execute exactly one commit code at a time.
2. Do not batch commits.
3. Read `AGENTS.md` before starting.
4. Read `docs/roadmap/organization.md` before starting.
5. Check kernel preconditions before implementation commits.
6. Do not create `identityaccess`.
7. Do not create identity implementation files.
8. Do not create platform security filter-chain code in organization.
9. Do not create business code in platform.
10. Always use the canonical HidraAPI header.
11. Never change `@Author`.
12. Never change `@CreatedOn`.
13. Update this roadmap after each completed or blocked commit.
14. Run validation after each commit.
15. If validation cannot run, record the exact reason.
16. If a dependency is missing, stop and report it.
17. If a requested file does not belong to organization, do not create it.
18. If an API DTO/controller is missing required JavaDoc or Swagger annotations, the task is incomplete.
19. If a domain model/value object is missing validation documentation, the task is incomplete.

---

## 17. Current Status Table

| Commit code | Status | Notes |
|---|---|---|
| `ORG-001` | Planned | Add this roadmap |
| `ORG-002` | Planned | Add production package skeleton only |
| `ORG-003` | Planned | Add organization value objects |
| `ORG-004` | Planned | Add position model |
| `ORG-005` | Planned | Add organization unit aggregate |
| `ORG-006` | Planned | Add employee aggregate |
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
| `ORG-019` | Planned | Add API tests |
| `ORG-020` | Planned | Add architecture guardrail if ArchUnit exists |
| `ORG-021` | Planned | Finalize checklist |

---

## 18. Next Action

Start with:

```text
ORG-001 — docs(organization): add organization roadmap
```

Then execute:

```text
ORG-002 — chore(organization): add organization package skeleton
```

Do not implement organization classes before the package skeleton is reviewed.
