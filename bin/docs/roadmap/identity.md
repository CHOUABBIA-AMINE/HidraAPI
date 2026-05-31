# HidraAPI Identity Implementation Roadmap

## 1. Document Control

| Field | Value |
|---|---|
| Project | HidraAPI |
| Product | Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Roadmap file | `docs/roadmap/identity.md` |
| Module | `identity` |
| Root package | `dz.sh.hidra.modules.identity` |
| Source root | `src/main/java/dz/sh/hidra/modules/identity` |
| Test root | `src/test/java/dz/sh/hidra/modules/identity` |
| Resource root | `src/main/resources` |
| Author | Abir MEDJERAB |
| CreatedOn | 2025-06-26 |
| UpdatedOn | 2026-05-30 |
| Status | Ready for AI-agent execution after kernel baseline |
| Execution mode | One commit code at a time |

---

## 2. Identity Module Mission

The `identity` module owns the HidraAPI business identity and access model.

It answers:

```text
Who can use HidraAPI?
What roles do they have?
What permissions do those roles grant?
Is the user active, suspended, or disabled?
Can a given actor perform a permission-protected action?
```

The `identity` module owns business access meaning.

It does **not** own Spring Security plumbing. That belongs to `platform.security`.

It does **not** own SH/TRC employee structure. That belongs to the future `organization` module.

---

## 3. Naming Standard

Use exactly:

```text
Module name: identity
Package: dz.sh.hidra.modules.identity
API path: /api/v1/identity
Database prefix: hidra_identity_*
Roadmap file: docs/roadmap/identity.md
Commit prefix: ID-xxx
```

Do not use:

```text
identityaccess
auth
security
iam
users
account
```

---

## 4. Architectural Boundaries

### 4.1 Boundary with Platform

```text
platform.security = technical Spring Security plumbing
modules.identity = business identity, roles, permissions, and access policy model
```

| Concern | Owner |
|---|---|
| Spring Security filter chain | `platform.security` |
| Authentication entry point | `platform.security` |
| Access denied handler | `platform.security` |
| Current principal extraction | `platform.security.context` |
| User aggregate | `modules.identity` |
| Role aggregate | `modules.identity` |
| Permission catalog/model | `modules.identity` |
| Permission evaluation | `modules.identity` |
| Role assignment policy | `modules.identity` |

### 4.2 Boundary with Organization

```text
identity.User = login/security identity
organization.Employee = real SH/TRC operational person
```

The identity module may contain a generic `EmployeeReference`, but it must not import:

```text
dz.sh.hidra.modules.organization.domain.model.Employee
```

### 4.3 Boundary with Kernel

The identity module may depend on `kernel` primitives and contracts.

Do not duplicate kernel classes inside identity.

---

## 5. Required Preconditions

Before implementing identity production code beyond package skeleton, verify that the kernel baseline exists.

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
Mark the identity task as Blocked.
Record the missing kernel files in this roadmap.
Do not create temporary duplicates inside identity.
```

Platform is useful but not required for domain-only commits.

For API, persistence, and security integration commits, platform should exist or the task should be marked blocked with the exact missing dependency.

---

## 6. Strict Scope Rules

### 6.1 Allowed in `identity`

The identity module may contain:

```text
User aggregate
Role aggregate
Permission catalog/model
UserRoleAssignment entity/value
RolePermissionAssignment entity/value
PermissionCode value object
identity lifecycle rules
role assignment policy
permission evaluation policy
identity domain events
identity application commands and queries
identity use-case ports
identity application services
identity REST controllers
identity request/response DTOs
identity persistence entities
identity JPA adapters
identity database migrations
identity unit/application/persistence/API tests
identity architecture tests
```

### 6.2 Forbidden in `identity`

The identity module must never own:

```text
Spring Security filter chain
authentication entry point implementation
access denied handler implementation
raw password hashing algorithm implementation
Employee aggregate
Department aggregate
OrganizationUnit aggregate
Pipeline aggregate
FlowReading aggregate
Incident aggregate
AuditEvent aggregate
SCADA connector
Historian connector
notification delivery implementation
```

### 6.3 Delayed capabilities

Do not implement in v1 unless explicitly requested later:

```text
Group aggregate
ABAC
OAuth2 Resource Server
LDAP integration
MFA
password reset
session management
employee linkage workflow
organization-scoped permission evaluation
access decision log
```

---

## 7. Canonical Java Header

Every Java file created under `identity` must start with this exact header style.

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
 * @Layer       : <API|Application|Domain|Infrastructure|Identity Test>
 * @Module      : identity
 * @Package     : <actual-package-name>
 *
 * @Description : <one-sentence responsibility>
 *
 */
```

For test classes, use:

```text
@Layer       : Identity Test
@Module      : identity
```

---

## 8. Dependency Rules

### 8.1 Allowed identity imports

Production identity code may import:

```text
java.*
java.time.*
java.util.*
jakarta.validation.*          // API/request DTO validation only
jakarta.persistence.*         // infrastructure persistence entity package only
org.springframework.*         // API/infrastructure/configuration packages only
dz.sh.hidra.kernel.*
```

### 8.2 Forbidden identity imports in domain

Domain layer must not import:

```text
org.springframework.*
jakarta.persistence.*
org.hibernate.*
dz.sh.hidra.platform.*
dz.sh.hidra.modules.organization.*
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
identity.api -> identity.application
identity.application -> identity.domain
identity.infrastructure -> identity.application ports
identity.infrastructure -> identity.domain mappings
identity.domain -> kernel only
```

Forbidden:

```text
identity.domain -> identity.application
identity.domain -> identity.infrastructure
identity.domain -> identity.api
identity.application -> identity.api
identity.application -> identity.infrastructure
identity.api -> identity.infrastructure
```

---

## 9. Permission Model Standard

Permission codes must use this format:

```text
<context>:<resource>:<action>
```

Examples:

```text
identity:user:create
identity:user:activate
identity:user:suspend
identity:role:create
identity:role:assign
identity:permission:grant
topology:pipeline:create
telemetry:flow-reading:approve
incidents:incident:close
audit:audit-event:read
```

Permission rules:

```text
context is required
resource is required
action is required
all parts must be lowercase
parts may use hyphen for multi-word names
exactly two colon separators are required
```

Do not hardcode authorization only by role name.

Preferred future decision:

```text
Does this actor have permission telemetry:flow-reading:approve?
```

Not:

```text
Is this actor ADMIN?
```

---

## 10. Recommended v1 Domain Model

Implement identity v1 with:

```text
Aggregates:
- User
- Role

Domain catalog/model:
- Permission

Entities/value objects:
- UserRoleAssignment
- RolePermissionAssignment
```

Delay:

```text
Group
ABAC
organization scoped access
external IAM linkage
password reset
MFA
```

---

## 11. Final Identity Production File Tree

This is the intended production tree after the full identity v1 roadmap.

```text
src/main/java/dz/sh/hidra/modules/identity
├── package-info.java
├── api
│   ├── package-info.java
│   └── rest
│       ├── package-info.java
│       ├── controller
│       │   ├── IdentityPermissionController.java
│       │   ├── IdentityRoleController.java
│       │   ├── IdentityUserController.java
│       │   └── package-info.java
│       ├── mapper
│       │   ├── IdentityRestMapper.java
│       │   └── package-info.java
│       ├── request
│       │   ├── ActivateUserRequest.java
│       │   ├── AssignRoleToUserRequest.java
│       │   ├── CheckPermissionRequest.java
│       │   ├── CreateRoleRequest.java
│       │   ├── GrantPermissionToRoleRequest.java
│       │   ├── RegisterUserRequest.java
│       │   └── package-info.java
│       └── response
│           ├── PermissionDecisionResponse.java
│           ├── PermissionResponse.java
│           ├── RoleResponse.java
│           ├── UserResponse.java
│           └── package-info.java
├── application
│   ├── package-info.java
│   ├── command
│   │   ├── ActivateUserCommand.java
│   │   ├── AssignRoleToUserCommand.java
│   │   ├── CreateRoleCommand.java
│   │   ├── GrantPermissionToRoleCommand.java
│   │   ├── RegisterUserCommand.java
│   │   ├── RevokeRoleFromUserCommand.java
│   │   ├── SuspendUserCommand.java
│   │   └── package-info.java
│   ├── dto
│   │   ├── PermissionDecisionDto.java
│   │   ├── PermissionDto.java
│   │   ├── RoleDto.java
│   │   ├── UserDto.java
│   │   └── package-info.java
│   ├── mapper
│   │   ├── IdentityApplicationMapper.java
│   │   └── package-info.java
│   ├── port
│   │   ├── package-info.java
│   │   ├── in
│   │   │   ├── ActivateUserUseCase.java
│   │   │   ├── AssignRoleToUserUseCase.java
│   │   │   ├── CreateRoleUseCase.java
│   │   │   ├── EvaluatePermissionUseCase.java
│   │   │   ├── GetUserPermissionsUseCase.java
│   │   │   ├── GrantPermissionToRoleUseCase.java
│   │   │   ├── RegisterUserUseCase.java
│   │   │   ├── RevokeRoleFromUserUseCase.java
│   │   │   ├── SuspendUserUseCase.java
│   │   │   └── package-info.java
│   │   └── out
│   │       ├── DomainEventPublisherPort.java
│   │       ├── PasswordEncoderPort.java
│   │       ├── PermissionRepository.java
│   │       ├── RoleRepository.java
│   │       ├── UserRepository.java
│   │       └── package-info.java
│   ├── query
│   │   ├── CheckPermissionQuery.java
│   │   ├── GetUserByIdQuery.java
│   │   ├── GetUserPermissionsQuery.java
│   │   ├── ListPermissionsQuery.java
│   │   ├── ListRolesQuery.java
│   │   ├── SearchUsersQuery.java
│   │   └── package-info.java
│   └── service
│       ├── ActivateUserService.java
│       ├── AssignRoleToUserService.java
│       ├── CreateRoleService.java
│       ├── EvaluatePermissionService.java
│       ├── GetUserPermissionsService.java
│       ├── GrantPermissionToRoleService.java
│       ├── RegisterUserService.java
│       ├── RevokeRoleFromUserService.java
│       ├── SuspendUserService.java
│       └── package-info.java
├── domain
│   ├── package-info.java
│   ├── event
│   │   ├── PermissionGrantedToRoleEvent.java
│   │   ├── RoleAssignedToUserEvent.java
│   │   ├── RoleCreatedEvent.java
│   │   ├── UserActivatedEvent.java
│   │   ├── UserRegisteredEvent.java
│   │   ├── UserSuspendedEvent.java
│   │   └── package-info.java
│   ├── exception
│   │   ├── IdentityDomainException.java
│   │   ├── InvalidPermissionCodeException.java
│   │   ├── RoleAssignmentNotAllowedException.java
│   │   ├── UserLifecycleException.java
│   │   └── package-info.java
│   ├── model
│   │   ├── Permission.java
│   │   ├── Role.java
│   │   ├── RolePermissionAssignment.java
│   │   ├── User.java
│   │   ├── UserRoleAssignment.java
│   │   └── package-info.java
│   ├── policy
│   │   ├── PermissionCodePolicy.java
│   │   ├── PermissionEvaluationPolicy.java
│   │   ├── RoleAssignmentPolicy.java
│   │   ├── SegregationOfDutiesPolicy.java
│   │   └── package-info.java
│   ├── repository
│   │   ├── PermissionCatalog.java
│   │   ├── RoleDomainRepository.java
│   │   ├── UserDomainRepository.java
│   │   └── package-info.java
│   ├── service
│   │   ├── PermissionEvaluationDomainService.java
│   │   ├── RoleAssignmentDomainService.java
│   │   └── package-info.java
│   └── value
│       ├── EmailAddress.java
│       ├── EmployeeReference.java
│       ├── PermissionCode.java
│       ├── PermissionId.java
│       ├── PermissionName.java
│       ├── RoleCode.java
│       ├── RoleId.java
│       ├── RoleName.java
│       ├── RoleStatus.java
│       ├── UserId.java
│       ├── Username.java
│       ├── UserStatus.java
│       └── package-info.java
└── infrastructure
    ├── package-info.java
    ├── adapter
    │   ├── NoOpDomainEventPublisherAdapter.java
    │   ├── SpringPasswordEncoderAdapter.java
    │   └── package-info.java
    ├── configuration
    │   ├── IdentityConfiguration.java
    │   └── package-info.java
    └── persistence
        ├── package-info.java
        ├── entity
        │   ├── PermissionJpaEntity.java
        │   ├── RoleJpaEntity.java
        │   ├── RolePermissionJpaEntity.java
        │   ├── UserJpaEntity.java
        │   ├── UserRoleJpaEntity.java
        │   └── package-info.java
        ├── mapper
        │   ├── IdentityPersistenceMapper.java
        │   └── package-info.java
        └── repository
            ├── PermissionJpaRepository.java
            ├── PermissionRepositoryAdapter.java
            ├── RoleJpaRepository.java
            ├── RoleRepositoryAdapter.java
            ├── UserJpaRepository.java
            ├── UserRepositoryAdapter.java
            └── package-info.java
```

Resource files created by this roadmap:

```text
src/main/resources/db/migration/V010__create_identity_tables.sql
```

---

## 12. Final Identity Test File Tree

Do not create test `package-info.java` files.

```text
src/test/java/dz/sh/hidra/modules/identity
├── IdentityArchitectureTest.java
├── api
│   └── rest
│       └── controller
│           ├── IdentityPermissionControllerTest.java
│           ├── IdentityRoleControllerTest.java
│           └── IdentityUserControllerTest.java
├── application
│   └── service
│       ├── ActivateUserServiceTest.java
│       ├── AssignRoleToUserServiceTest.java
│       ├── CreateRoleServiceTest.java
│       ├── EvaluatePermissionServiceTest.java
│       ├── GrantPermissionToRoleServiceTest.java
│       └── RegisterUserServiceTest.java
├── domain
│   ├── model
│   │   ├── RoleTest.java
│   │   └── UserTest.java
│   ├── policy
│   │   ├── PermissionCodePolicyTest.java
│   │   ├── PermissionEvaluationPolicyTest.java
│   │   └── RoleAssignmentPolicyTest.java
│   └── value
│       ├── EmailAddressTest.java
│       ├── PermissionCodeTest.java
│       ├── RoleCodeTest.java
│       ├── UserIdTest.java
│       └── UsernameTest.java
└── infrastructure
    └── persistence
        ├── RoleRepositoryAdapterTest.java
        └── UserRepositoryAdapterTest.java
```

---

## 13. Commit Plan Overview

This order is mandatory.

| Commit code | Commit message | Purpose |
|---|---|---|
| `ID-001` | `docs(identity): add identity roadmap` | Add this roadmap file |
| `ID-002` | `chore(identity): add identity package skeleton` | Add production `package-info.java` files only |
| `ID-003` | `feat(identity): add identity domain value objects` | Add IDs, codes, names, email, statuses, references |
| `ID-004` | `feat(identity): add permission domain model and policy` | Add permission model and permission code policy |
| `ID-005` | `feat(identity): add role domain model` | Add role aggregate and role permission assignment |
| `ID-006` | `feat(identity): add user domain model` | Add user aggregate and user role assignment |
| `ID-007` | `feat(identity): add identity domain exceptions` | Add identity-specific domain exceptions |
| `ID-008` | `feat(identity): add identity domain events` | Add user/role/permission events |
| `ID-009` | `feat(identity): add identity domain policies and services` | Add role assignment and permission evaluation logic |
| `ID-010` | `feat(identity): add application commands and queries` | Add command/query records |
| `ID-011` | `feat(identity): add application ports and DTOs` | Add inbound/outbound ports and DTOs |
| `ID-012` | `feat(identity): add application services` | Add use-case services |
| `ID-013` | `feat(identity): add persistence entities and repositories` | Add JPA persistence layer and migration |
| `ID-014` | `feat(identity): add infrastructure adapters and configuration` | Add password encoder/domain-event adapters and configuration |
| `ID-015` | `feat(identity): add REST API contracts and controllers` | Add REST requests/responses/controllers/mappers |
| `ID-016` | `test(identity): add identity domain tests` | Add domain/value/policy tests |
| `ID-017` | `test(identity): add identity application tests` | Add application service tests |
| `ID-018` | `test(identity): add identity persistence tests` | Add persistence adapter tests |
| `ID-019` | `test(identity): add identity API tests` | Add controller/API tests |
| `ID-020` | `test(identity): add identity architecture guardrail` | Add ArchUnit architecture tests |
| `ID-021` | `docs(identity): finalize identity checklist` | Update this roadmap with final status |

---

# 14. Detailed Commit Specifications

## ID-001 — Add Identity Roadmap

### Commit message

```text
docs(identity): add identity roadmap
```

### Description

Create the roadmap file used by AI agents and developers to implement the `identity` module safely.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `docs/roadmap/identity.md` | Identity implementation plan and execution memory |

### Acceptance criteria

- File exists at exactly `docs/roadmap/identity.md`.
- File contains commit codes, messages, descriptions, files, file purposes, acceptance criteria, and validation commands.
- No Java source files are created in this commit.

### Validation

```bash
test -f docs/roadmap/identity.md
```

---

## ID-002 — Add Identity Package Skeleton

### Commit message

```text
chore(identity): add identity package skeleton
```

### Description

Create only production package structure using `package-info.java`.

Do not create implementation classes.

Do not create test package-info files.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/modules/identity/package-info.java` | Root identity package boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/api/package-info.java` | API layer boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/api/rest/package-info.java` | REST API boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/package-info.java` | REST controller boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/package-info.java` | REST request DTO boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/api/rest/response/package-info.java` | REST response DTO boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/package-info.java` | REST mapper boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/application/package-info.java` | Application layer boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/application/command/package-info.java` | Command package boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/application/query/package-info.java` | Query package boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/application/dto/package-info.java` | Application DTO boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/application/mapper/package-info.java` | Application mapper boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/application/port/package-info.java` | Application port boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/application/port/in/package-info.java` | Inbound use-case port boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/application/port/out/package-info.java` | Outbound dependency port boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/application/service/package-info.java` | Application service boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/domain/package-info.java` | Domain layer boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/domain/model/package-info.java` | Domain model boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/domain/value/package-info.java` | Domain value boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/domain/event/package-info.java` | Domain event boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/domain/policy/package-info.java` | Domain policy boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/domain/service/package-info.java` | Domain service boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/domain/repository/package-info.java` | Domain repository contract boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/domain/exception/package-info.java` | Domain exception boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/package-info.java` | Infrastructure layer boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/package-info.java` | Persistence infrastructure boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/package-info.java` | JPA entity boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/package-info.java` | Persistence mapper boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/package-info.java` | Persistence repository boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/adapter/package-info.java` | Infrastructure adapter boundary |
| Create | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/configuration/package-info.java` | Infrastructure configuration boundary |
| Update | `docs/roadmap/identity.md` | Mark `ID-002` as completed after execution |

### Acceptance criteria

- Only production `package-info.java` files are created.
- No test `package-info.java` files are created.
- No implementation class is created.
- Every file uses the canonical HidraAPI header.
- Every `@Layer` value matches the layer package.
- No `identityaccess` package is created.

### Validation

```bash
find src/main/java/dz/sh/hidra/modules/identity -type f | sort
mvn -q -DskipTests compile
```

---

## ID-003 — Add Identity Domain Value Objects

### Commit message

```text
feat(identity): add identity domain value objects
```

### Description

Add immutable domain value objects used by user, role, and permission models.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/value/UserId.java` | Stable identity user identifier |
| Create | `domain/value/Username.java` | Validated username value |
| Create | `domain/value/EmailAddress.java` | Validated email address value |
| Create | `domain/value/UserStatus.java` | User lifecycle status enum |
| Create | `domain/value/RoleId.java` | Stable role identifier |
| Create | `domain/value/RoleCode.java` | Validated role code |
| Create | `domain/value/RoleName.java` | Validated role display name |
| Create | `domain/value/RoleStatus.java` | Role lifecycle status enum |
| Create | `domain/value/PermissionId.java` | Stable permission identifier |
| Create | `domain/value/PermissionCode.java` | Validated permission code using context:resource:action format |
| Create | `domain/value/PermissionName.java` | Permission display name |
| Create | `domain/value/EmployeeReference.java` | Generic reference to future organization employee without importing organization module |
| Update | `docs/roadmap/identity.md` | Mark `ID-003` as completed after execution |

### Acceptance criteria

- Value objects are immutable.
- Invalid inputs are rejected.
- No Spring/JPA imports.
- No organization imports.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ID-004 — Add Permission Domain Model and Policy

### Commit message

```text
feat(identity): add permission domain model and policy
```

### Description

Add permission catalog model and permission code validation policy.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/model/Permission.java` | Domain model for a permission catalog entry |
| Create | `domain/policy/PermissionCodePolicy.java` | Validates permission code format and allowed structure |
| Create | `domain/repository/PermissionCatalog.java` | Domain contract for permission lookup/catalog operations |
| Update | `docs/roadmap/identity.md` | Mark `ID-004` as completed after execution |

### Acceptance criteria

- Permission model is generic to identity.
- No persistence annotations.
- Permission code format is enforced.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ID-005 — Add Role Domain Model

### Commit message

```text
feat(identity): add role domain model
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/model/Role.java` | Role aggregate that owns lifecycle and permission assignments |
| Create | `domain/model/RolePermissionAssignment.java` | Represents assignment of a permission to a role |
| Create | `domain/repository/RoleDomainRepository.java` | Domain repository contract for roles |
| Update | `docs/roadmap/identity.md` | Mark `ID-005` as completed after execution |

### Acceptance criteria

- Role is aggregate root.
- Permission assignments are controlled by methods.
- Duplicate permission assignments are rejected.
- No persistence/framework dependency.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ID-006 — Add User Domain Model

### Commit message

```text
feat(identity): add user domain model
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/model/User.java` | User aggregate representing HidraAPI security identity |
| Create | `domain/model/UserRoleAssignment.java` | Represents assignment of a role to a user |
| Create | `domain/repository/UserDomainRepository.java` | Domain repository contract for users |
| Update | `docs/roadmap/identity.md` | Mark `ID-006` as completed after execution |

### Acceptance criteria

- User is aggregate root.
- Lifecycle transitions are controlled.
- Duplicate role assignments are rejected.
- No persistence/framework dependency.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ID-007 — Add Identity Domain Exceptions

### Commit message

```text
feat(identity): add identity domain exceptions
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/exception/IdentityDomainException.java` | Base exception for identity domain failures |
| Create | `domain/exception/InvalidPermissionCodeException.java` | Exception for invalid permission code |
| Create | `domain/exception/RoleAssignmentNotAllowedException.java` | Exception for invalid role assignment |
| Create | `domain/exception/UserLifecycleException.java` | Exception for invalid user lifecycle transition |
| Update | Existing value/model/policy files if needed | Replace generic exceptions with identity-specific exceptions where appropriate |
| Update | `docs/roadmap/identity.md` | Mark `ID-007` as completed after execution |

### Acceptance criteria

- Exceptions extend kernel `DomainException` or `BusinessRuleViolationException`.
- No HTTP status.
- No Spring imports.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ID-008 — Add Identity Domain Events

### Commit message

```text
feat(identity): add identity domain events
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/event/UserRegisteredEvent.java` | Published when a user is registered |
| Create | `domain/event/UserActivatedEvent.java` | Published when a user is activated |
| Create | `domain/event/UserSuspendedEvent.java` | Published when a user is suspended |
| Create | `domain/event/RoleCreatedEvent.java` | Published when a role is created |
| Create | `domain/event/RoleAssignedToUserEvent.java` | Published when a role is assigned to a user |
| Create | `domain/event/PermissionGrantedToRoleEvent.java` | Published when a permission is granted to a role |
| Update | `docs/roadmap/identity.md` | Mark `ID-008` as completed after execution |

### Acceptance criteria

- Events implement kernel `DomainEvent`.
- Events are immutable and safe.
- Events do not include passwords, tokens, or secrets.
- Events do not import platform.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ID-009 — Add Identity Domain Policies and Services

### Commit message

```text
feat(identity): add identity domain policies and services
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/policy/RoleAssignmentPolicy.java` | Validates whether a role can be assigned to a user |
| Create | `domain/policy/PermissionEvaluationPolicy.java` | Determines permission evaluation rules |
| Create | `domain/policy/SegregationOfDutiesPolicy.java` | Prevents dangerous access combinations |
| Create | `domain/service/RoleAssignmentDomainService.java` | Coordinates role assignment domain rules |
| Create | `domain/service/PermissionEvaluationDomainService.java` | Evaluates effective permissions from roles |
| Update | `docs/roadmap/identity.md` | Mark `ID-009` as completed after execution |

### Acceptance criteria

- Policies compile.
- No Spring/JPA imports.
- No platform imports.
- Permission evaluation works from role permissions.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ID-010 — Add Application Commands and Queries

### Commit message

```text
feat(identity): add application commands and queries
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `application/command/RegisterUserCommand.java` | Input for registering a user |
| Create | `application/command/ActivateUserCommand.java` | Input for activating a user |
| Create | `application/command/SuspendUserCommand.java` | Input for suspending a user |
| Create | `application/command/CreateRoleCommand.java` | Input for creating a role |
| Create | `application/command/AssignRoleToUserCommand.java` | Input for assigning a role to a user |
| Create | `application/command/RevokeRoleFromUserCommand.java` | Input for revoking a user role |
| Create | `application/command/GrantPermissionToRoleCommand.java` | Input for granting permission to role |
| Create | `application/query/GetUserByIdQuery.java` | Query for one user |
| Create | `application/query/SearchUsersQuery.java` | Query for searching users |
| Create | `application/query/ListRolesQuery.java` | Query for listing roles |
| Create | `application/query/ListPermissionsQuery.java` | Query for listing permissions |
| Create | `application/query/CheckPermissionQuery.java` | Query for checking permission decision |
| Create | `application/query/GetUserPermissionsQuery.java` | Query for user permissions |
| Update | `docs/roadmap/identity.md` | Mark `ID-010` as completed after execution |

### Acceptance criteria

- Commands implement kernel `Command`.
- Queries implement kernel `Query`.
- No Spring or JPA annotations.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ID-011 — Add Application Ports and DTOs

### Commit message

```text
feat(identity): add application ports and DTOs
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `application/port/in/RegisterUserUseCase.java` | Inbound port for user registration |
| Create | `application/port/in/ActivateUserUseCase.java` | Inbound port for user activation |
| Create | `application/port/in/SuspendUserUseCase.java` | Inbound port for user suspension |
| Create | `application/port/in/CreateRoleUseCase.java` | Inbound port for role creation |
| Create | `application/port/in/AssignRoleToUserUseCase.java` | Inbound port for assigning role to user |
| Create | `application/port/in/RevokeRoleFromUserUseCase.java` | Inbound port for revoking user role |
| Create | `application/port/in/GrantPermissionToRoleUseCase.java` | Inbound port for granting permission to role |
| Create | `application/port/in/EvaluatePermissionUseCase.java` | Inbound port for permission checking |
| Create | `application/port/in/GetUserPermissionsUseCase.java` | Inbound port for user permissions retrieval |
| Create | `application/port/out/UserRepository.java` | Outbound user persistence port |
| Create | `application/port/out/RoleRepository.java` | Outbound role persistence port |
| Create | `application/port/out/PermissionRepository.java` | Outbound permission persistence port |
| Create | `application/port/out/PasswordEncoderPort.java` | Outbound password encoding abstraction |
| Create | `application/port/out/DomainEventPublisherPort.java` | Outbound domain event publication abstraction |
| Create | `application/dto/UserDto.java` | Application user DTO |
| Create | `application/dto/RoleDto.java` | Application role DTO |
| Create | `application/dto/PermissionDto.java` | Application permission DTO |
| Create | `application/dto/PermissionDecisionDto.java` | Application permission decision DTO |
| Create | `application/mapper/IdentityApplicationMapper.java` | Maps domain objects to application DTOs |
| Update | `docs/roadmap/identity.md` | Mark `ID-011` as completed after execution |

### Acceptance criteria

- Ports are interfaces.
- DTOs are immutable records.
- Application layer does not depend on API or infrastructure.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ID-012 — Add Application Services

### Commit message

```text
feat(identity): add application services
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `application/service/RegisterUserService.java` | Implements user registration use case |
| Create | `application/service/ActivateUserService.java` | Implements user activation use case |
| Create | `application/service/SuspendUserService.java` | Implements user suspension use case |
| Create | `application/service/CreateRoleService.java` | Implements role creation use case |
| Create | `application/service/AssignRoleToUserService.java` | Implements role assignment use case |
| Create | `application/service/RevokeRoleFromUserService.java` | Implements role revocation use case |
| Create | `application/service/GrantPermissionToRoleService.java` | Implements permission grant use case |
| Create | `application/service/EvaluatePermissionService.java` | Implements permission evaluation use case |
| Create | `application/service/GetUserPermissionsService.java` | Implements user permissions retrieval use case |
| Update | `docs/roadmap/identity.md` | Mark `ID-012` as completed after execution |

### Acceptance criteria

- Services use constructor injection.
- Services depend only on ports and domain.
- Services do not access JPA repositories directly.
- Services do not return REST DTOs.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ID-013 — Add Persistence Entities and Repositories

### Commit message

```text
feat(identity): add persistence entities and repositories
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `infrastructure/persistence/entity/UserJpaEntity.java` | JPA representation of user |
| Create | `infrastructure/persistence/entity/RoleJpaEntity.java` | JPA representation of role |
| Create | `infrastructure/persistence/entity/PermissionJpaEntity.java` | JPA representation of permission |
| Create | `infrastructure/persistence/entity/UserRoleJpaEntity.java` | JPA representation of user-role assignment |
| Create | `infrastructure/persistence/entity/RolePermissionJpaEntity.java` | JPA representation of role-permission assignment |
| Create | `infrastructure/persistence/repository/UserJpaRepository.java` | Spring Data user repository |
| Create | `infrastructure/persistence/repository/RoleJpaRepository.java` | Spring Data role repository |
| Create | `infrastructure/persistence/repository/PermissionJpaRepository.java` | Spring Data permission repository |
| Create | `infrastructure/persistence/mapper/IdentityPersistenceMapper.java` | Maps between domain and persistence |
| Create | `infrastructure/persistence/repository/UserRepositoryAdapter.java` | Implements application `UserRepository` port |
| Create | `infrastructure/persistence/repository/RoleRepositoryAdapter.java` | Implements application `RoleRepository` port |
| Create | `infrastructure/persistence/repository/PermissionRepositoryAdapter.java` | Implements application `PermissionRepository` port |
| Create | `src/main/resources/db/migration/V010__create_identity_tables.sql` | Creates identity database tables |
| Update | `docs/roadmap/identity.md` | Mark `ID-013` as completed after execution |

### Tables

Migration should create:

```text
hidra_identity_user
hidra_identity_role
hidra_identity_permission
hidra_identity_user_role
hidra_identity_role_permission
```

### Acceptance criteria

- Persistence adapters implement application outbound ports.
- Domain does not import JPA.
- Migration exists.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
mvn -q flyway:validate
```

If Flyway validation cannot run due to missing database connection, record exact reason.

---

## ID-014 — Add Infrastructure Adapters and Configuration

### Commit message

```text
feat(identity): add infrastructure adapters and configuration
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `infrastructure/adapter/SpringPasswordEncoderAdapter.java` | Adapts Spring password encoder to `PasswordEncoderPort` if needed |
| Create | `infrastructure/adapter/NoOpDomainEventPublisherAdapter.java` | Safe no-op event publisher until platform outbox integration exists |
| Create | `infrastructure/configuration/IdentityConfiguration.java` | Wires identity module beans |
| Update | `docs/roadmap/identity.md` | Mark `ID-014` as completed after execution |

### Acceptance criteria

- Infrastructure adapters compile.
- No Spring Security filter chain is created.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## ID-015 — Add REST API Contracts and Controllers

### Commit message

```text
feat(identity): add REST API contracts and controllers
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `api/rest/request/RegisterUserRequest.java` | Request body for registering user |
| Create | `api/rest/request/ActivateUserRequest.java` | Request body for user activation |
| Create | `api/rest/request/AssignRoleToUserRequest.java` | Request body for assigning role to user |
| Create | `api/rest/request/CreateRoleRequest.java` | Request body for creating role |
| Create | `api/rest/request/GrantPermissionToRoleRequest.java` | Request body for granting permission |
| Create | `api/rest/request/CheckPermissionRequest.java` | Request body for permission check |
| Create | `api/rest/response/UserResponse.java` | REST user response |
| Create | `api/rest/response/RoleResponse.java` | REST role response |
| Create | `api/rest/response/PermissionResponse.java` | REST permission response |
| Create | `api/rest/response/PermissionDecisionResponse.java` | REST permission decision response |
| Create | `api/rest/mapper/IdentityRestMapper.java` | Maps REST request/response to application commands/DTOs |
| Create | `api/rest/controller/IdentityUserController.java` | User identity REST endpoints |
| Create | `api/rest/controller/IdentityRoleController.java` | Role REST endpoints |
| Create | `api/rest/controller/IdentityPermissionController.java` | Permission REST endpoints |
| Update | `docs/roadmap/identity.md` | Mark `ID-015` as completed after execution |

### API endpoints

Create endpoints under:

```text
/api/v1/identity
```

Recommended v1 endpoints:

```text
POST   /api/v1/identity/users
GET    /api/v1/identity/users/{userId}
GET    /api/v1/identity/users
POST   /api/v1/identity/users/{userId}/activate
POST   /api/v1/identity/users/{userId}/suspend
POST   /api/v1/identity/users/{userId}/roles
DELETE /api/v1/identity/users/{userId}/roles/{roleId}

POST   /api/v1/identity/roles
GET    /api/v1/identity/roles
GET    /api/v1/identity/roles/{roleId}
POST   /api/v1/identity/roles/{roleId}/permissions
DELETE /api/v1/identity/roles/{roleId}/permissions/{permissionId}

GET    /api/v1/identity/permissions
POST   /api/v1/identity/permissions/check
```

### Acceptance criteria

- Request DTOs use Bean Validation.
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

## ID-016 — Add Identity Domain Tests

### Commit message

```text
test(identity): add identity domain tests
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `domain/value/UserIdTest.java` | Verifies user ID validation |
| Create | `domain/value/UsernameTest.java` | Verifies username validation |
| Create | `domain/value/EmailAddressTest.java` | Verifies email validation |
| Create | `domain/value/RoleCodeTest.java` | Verifies role code validation |
| Create | `domain/value/PermissionCodeTest.java` | Verifies permission code validation |
| Create | `domain/model/UserTest.java` | Verifies user lifecycle and role assignment rules |
| Create | `domain/model/RoleTest.java` | Verifies role lifecycle and permission assignment rules |
| Create | `domain/policy/PermissionCodePolicyTest.java` | Verifies permission code format policy |
| Create | `domain/policy/RoleAssignmentPolicyTest.java` | Verifies role assignment rules |
| Create | `domain/policy/PermissionEvaluationPolicyTest.java` | Verifies permission evaluation rules |
| Update | `docs/roadmap/identity.md` | Mark `ID-016` as completed after execution |

### Validation

```bash
mvn -q test -Dtest='*Identity*,*UserTest,*RoleTest,*PermissionCodeTest,*RoleAssignmentPolicyTest,*PermissionEvaluationPolicyTest'
mvn -q test
```

---

## ID-017 — Add Identity Application Tests

### Commit message

```text
test(identity): add identity application tests
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `application/service/RegisterUserServiceTest.java` | Verifies user registration use case |
| Create | `application/service/ActivateUserServiceTest.java` | Verifies user activation use case |
| Create | `application/service/CreateRoleServiceTest.java` | Verifies role creation use case |
| Create | `application/service/AssignRoleToUserServiceTest.java` | Verifies role assignment use case |
| Create | `application/service/GrantPermissionToRoleServiceTest.java` | Verifies permission grant use case |
| Create | `application/service/EvaluatePermissionServiceTest.java` | Verifies permission evaluation use case |
| Update | `docs/roadmap/identity.md` | Mark `ID-017` as completed after execution |

### Validation

```bash
mvn -q test -Dtest='*ServiceTest'
mvn -q test
```

---

## ID-018 — Add Identity Persistence Tests

### Commit message

```text
test(identity): add identity persistence tests
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `infrastructure/persistence/UserRepositoryAdapterTest.java` | Verifies user persistence adapter |
| Create | `infrastructure/persistence/RoleRepositoryAdapterTest.java` | Verifies role persistence adapter |
| Update | `docs/roadmap/identity.md` | Mark `ID-018` as completed after execution |

### Validation

```bash
mvn -q test -Dtest='*RepositoryAdapterTest'
```

If database/Testcontainers is not configured, mark exact limitation.

---

## ID-019 — Add Identity API Tests

### Commit message

```text
test(identity): add identity API tests
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `api/rest/controller/IdentityUserControllerTest.java` | Verifies user REST endpoints |
| Create | `api/rest/controller/IdentityRoleControllerTest.java` | Verifies role REST endpoints |
| Create | `api/rest/controller/IdentityPermissionControllerTest.java` | Verifies permission REST endpoints |
| Update | `docs/roadmap/identity.md` | Mark `ID-019` as completed after execution |

### Validation

```bash
mvn -q test -Dtest='*ControllerTest'
mvn -q test
```

---

## ID-020 — Add Identity Architecture Guardrail

### Commit message

```text
test(identity): add identity architecture guardrail
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/test/java/dz/sh/hidra/modules/identity/IdentityArchitectureTest.java` | Ensures identity layer boundaries and forbidden imports |
| Update | `docs/roadmap/identity.md` | Mark `ID-020` as completed or blocked |

### Required architecture rules

The test must assert:

```text
identity.domain must not depend on Spring
identity.domain must not depend on JPA
identity.domain must not depend on infrastructure
identity.domain must not depend on API
identity.application must not depend on API
identity.application must not depend on infrastructure
identity.api must not depend on infrastructure
identity must not import organization domain model
identity must not create identityaccess package
controllers must not access repositories directly
infrastructure persistence adapters implement application outbound ports
```

If ArchUnit is missing:

```text
Do not add IdentityArchitectureTest.
Mark ID-020 as Blocked.
Record missing dependency in this roadmap.
```

### Validation

```bash
mvn -q test -Dtest=IdentityArchitectureTest
mvn -q test
```

---

## ID-021 — Finalize Identity Checklist

### Commit message

```text
docs(identity): finalize identity checklist
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Update | `docs/roadmap/identity.md` | Record final execution status and checklist |

### Required final checklist

```text
[ ] Identity package structure exists
[ ] No identityaccess package exists
[ ] Identity domain has no Spring dependency
[ ] Identity domain has no JPA dependency
[ ] Identity application has no API dependency
[ ] Identity application has no infrastructure dependency
[ ] Identity API has no repository dependency
[ ] User aggregate exists
[ ] Role aggregate exists
[ ] Permission model exists
[ ] Permission code policy exists
[ ] Permission evaluation works
[ ] Role assignment policy works
[ ] REST API compiles
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

## 15. File Purpose Matrix

### 15.1 Domain files

| File | Purpose |
|---|---|
| `User.java` | User aggregate for HidraAPI security identity |
| `Role.java` | Role aggregate for permission grouping |
| `Permission.java` | Permission catalog/model |
| `UserRoleAssignment.java` | Assignment of role to user |
| `RolePermissionAssignment.java` | Assignment of permission to role |
| `PermissionCodePolicy.java` | Permission code validation policy |
| `RoleAssignmentPolicy.java` | Role assignment business policy |
| `PermissionEvaluationPolicy.java` | Permission evaluation policy |
| `SegregationOfDutiesPolicy.java` | Prevents dangerous access combinations |
| `PermissionEvaluationDomainService.java` | Evaluates effective permissions |
| `RoleAssignmentDomainService.java` | Coordinates role assignment domain rules |

### 15.2 Application files

| File | Purpose |
|---|---|
| `RegisterUserCommand.java` | Input for user registration |
| `CreateRoleCommand.java` | Input for role creation |
| `AssignRoleToUserCommand.java` | Input for assigning role |
| `GrantPermissionToRoleCommand.java` | Input for granting permission |
| `CheckPermissionQuery.java` | Input for permission checking |
| `RegisterUserUseCase.java` | Inbound port for user registration |
| `CreateRoleUseCase.java` | Inbound port for role creation |
| `EvaluatePermissionUseCase.java` | Inbound port for permission evaluation |
| `UserRepository.java` | Outbound user repository port |
| `RoleRepository.java` | Outbound role repository port |
| `PermissionRepository.java` | Outbound permission repository port |
| `DomainEventPublisherPort.java` | Outbound event publisher port |
| `IdentityApplicationMapper.java` | Maps domain models to application DTOs |

### 15.3 Infrastructure files

| File | Purpose |
|---|---|
| `UserJpaEntity.java` | JPA entity for users |
| `RoleJpaEntity.java` | JPA entity for roles |
| `PermissionJpaEntity.java` | JPA entity for permissions |
| `UserRoleJpaEntity.java` | JPA entity for user-role assignments |
| `RolePermissionJpaEntity.java` | JPA entity for role-permission assignments |
| `UserRepositoryAdapter.java` | Implements user repository port |
| `RoleRepositoryAdapter.java` | Implements role repository port |
| `PermissionRepositoryAdapter.java` | Implements permission repository port |
| `IdentityPersistenceMapper.java` | Maps domain to persistence and back |
| `IdentityConfiguration.java` | Wires identity infrastructure beans |
| `V010__create_identity_tables.sql` | Creates identity database tables |

### 15.4 API files

| File | Purpose |
|---|---|
| `IdentityUserController.java` | User REST endpoints |
| `IdentityRoleController.java` | Role REST endpoints |
| `IdentityPermissionController.java` | Permission REST endpoints |
| `RegisterUserRequest.java` | Request DTO for user registration |
| `CreateRoleRequest.java` | Request DTO for role creation |
| `CheckPermissionRequest.java` | Request DTO for permission check |
| `UserResponse.java` | User REST response |
| `RoleResponse.java` | Role REST response |
| `PermissionDecisionResponse.java` | Permission decision REST response |
| `IdentityRestMapper.java` | Maps REST DTOs to application commands/queries |

---

## 16. AI Agent Execution Rules

Any AI agent executing this roadmap must follow these rules:

1. Execute exactly one commit code at a time.
2. Do not batch commits.
3. Read `AGENTS.md` before starting.
4. Read `docs/roadmap/identity.md` before starting.
5. Check kernel preconditions before implementation commits.
6. Do not create `identityaccess`.
7. Do not create organization code.
8. Do not create platform security filter-chain code in identity.
9. Do not create business code in platform.
10. Always use the canonical HidraAPI header.
11. Never change `@Author`.
12. Never change `@CreatedOn`.
13. Update this roadmap after each completed or blocked commit.
14. Run validation after each commit.
15. If validation cannot run, record the exact reason.
16. If a dependency is missing, stop and report it.
17. If a requested file does not belong to identity, do not create it.

---

## 17. Current Status Table

| Commit code | Status | Notes |
|---|---|---|
| `ID-001` | Planned | Add this roadmap |
| `ID-002` | Planned | Add production package skeleton only |
| `ID-003` | Planned | Add identity value objects |
| `ID-004` | Planned | Add permission model and policy |
| `ID-005` | Planned | Add role aggregate |
| `ID-006` | Planned | Add user aggregate |
| `ID-007` | Planned | Add identity domain exceptions |
| `ID-008` | Planned | Add identity domain events |
| `ID-009` | Planned | Add domain policies and services |
| `ID-010` | Planned | Add application commands and queries |
| `ID-011` | Planned | Add application ports and DTOs |
| `ID-012` | Planned | Add application services |
| `ID-013` | Planned | Add persistence entities, repositories, and migration |
| `ID-014` | Planned | Add infrastructure adapters and configuration |
| `ID-015` | Planned | Add REST API |
| `ID-016` | Planned | Add domain tests |
| `ID-017` | Planned | Add application tests |
| `ID-018` | Planned | Add persistence tests |
| `ID-019` | Planned | Add API tests |
| `ID-020` | Planned | Add architecture guardrail if ArchUnit exists |
| `ID-021` | Planned | Finalize checklist |

---

## 18. Next Action

Start with:

```text
ID-001 — docs(identity): add identity roadmap
```

Then execute:

```text
ID-002 — chore(identity): add identity package skeleton
```

Do not implement identity classes before the package skeleton is reviewed.

---

# 19. Mandatory Validation and Documentation Constraints

This section is mandatory for all AI agents implementing `docs/roadmap/identity.md`.

If any earlier section appears less strict than this section, this section wins.

## 19.1 Public type documentation

Every production Java type created in the identity module must include:

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
 * <Explain what this type means in Hidra identity, access, role, or permission management.>
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

---

## 19.2 Domain model documentation rules

Every domain model class under:

```text
src/main/java/dz/sh/hidra/modules/identity/domain/model
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

Example:

```java
/**
 * Unique identifier of the Hidra security identity.
 */
private final UserId id;
```

When a domain model is implemented as a record, document every record component using `@param` JavaDoc.

Example:

```java
/**
 * Represents a permission assigned to a role.
 *
 * <p>Business role:
 * Captures that a role grants one permission in the Hidra identity model.
 *
 * <p>Validation:
 * The permission code must already be validated by {@code PermissionCode}.
 *
 * @param permissionCode permission granted to the role
 * @param assignedAt instant when the permission was assigned
 */
public record RolePermissionAssignment(PermissionCode permissionCode, Instant assignedAt) {
}
```

---

## 19.3 Domain model validation rules

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
User cannot receive duplicate active role assignment.
Inactive role cannot receive a new permission assignment.
Invalid permission code cannot be constructed.
Suspended user cannot be suspended again unless explicitly allowed.
Disabled user cannot authenticate.
Inactive role cannot be assigned to a user.
```

Bean Validation annotations are not allowed in domain model classes.

---

## 19.4 Domain value documentation and validation rules

Every value object under:

```text
src/main/java/dz/sh/hidra/modules/identity/domain/value
```

must have JavaDoc explaining:

```text
business meaning
accepted format
validation rules
normalization rules
usage restrictions
```

Identifier value objects should document whether `newId()` generation is allowed.

String value objects must:

```text
reject null
reject blank when required
trim input
normalize case where explicitly required
throw a domain or kernel value-object exception on invalid input
```

Specific required rules:

| Value object | Required validation/documentation |
|---|---|
| `Username` | Document accepted characters, min/max length, case policy |
| `EmailAddress` | Document lightweight email validation and normalization |
| `RoleCode` | Document uppercase snake-case format |
| `PermissionCode` | Document exact `context:resource:action` format |
| `EmployeeReference` | Document that it is a reference only and must not import organization classes |

---

## 19.5 DTO documentation rules

Every application DTO and REST DTO under:

```text
src/main/java/dz/sh/hidra/modules/identity/application/dto
src/main/java/dz/sh/hidra/modules/identity/api/rest/request
src/main/java/dz/sh/hidra/modules/identity/api/rest/response
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

Example:

```java
/**
 * Request body used to register a new Hidra identity user.
 *
 * <p>Validation:
 * Username is required and must respect identity username rules.
 * Email is required and must be a valid email address.
 *
 * @param username requested unique username
 * @param email requested user email
 */
public record RegisterUserRequest(
    @NotBlank
    @Size(max = 80)
    String username,

    @NotBlank
    @Email
    @Size(max = 120)
    String email
) {
}
```

---

## 19.6 DTO validation rules

REST request DTOs must use Bean Validation annotations.

Allowed examples:

```text
@NotNull
@NotBlank
@Size
@Email
@Pattern
@Valid
```

Validation belongs to request DTOs only at the API boundary.

Nested request DTOs must use `@Valid`.

Controllers must use `@Valid` on request bodies.

Application DTOs and response DTOs generally do not need Bean Validation unless they are used as inbound boundary objects.

Do not use Bean Validation annotations in domain model classes.

---

## 19.7 DTO field/component naming rules

DTO fields and record components must use clear, business-specific names.

Avoid vague names:

```text
name
code
value
data
info
object
item
```

when a precise name is possible.

Prefer:

```text
username
email
roleCode
permissionCode
userStatus
roleStatus
assignedAt
activatedAt
suspendedAt
```

Request DTOs must not expose:

```text
password hashes
tokens
database version fields
internal event IDs
JPA entities
domain entities
technical auditing fields unless explicitly required
```

---

## 19.8 Controller documentation rules

Every controller under:

```text
src/main/java/dz/sh/hidra/modules/identity/api/rest/controller
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

Controller JavaDoc must explain:

```text
business capability exposed
application ports used
validation behavior
security/permission expectation when known
what the controller must not do
```

Every controller method must document:

```text
HTTP purpose
path variables
request body
response
main validation rules
```

---

## 19.9 Controller implementation rules

Controllers must:

```text
depend only on application inbound ports
use @Valid on request bodies
use clear @PathVariable names
use clear @RequestParam names
return REST response DTOs
not return domain models
not return JPA entities
not access repositories
not contain business rules
not perform permission evaluation directly unless through an application port
```

Controllers must not call infrastructure repositories.

Controllers must not implement role assignment rules.

Controllers must not implement permission-code validation directly.

---

## 19.10 OpenAPI documentation rules

If `springdoc-openapi` is available, API-layer classes may use:

```text
@Tag
@Operation
@ApiResponse
@ApiResponses
@Schema
```

Use OpenAPI annotations only in the API layer.

Do not import OpenAPI annotations into:

```text
domain
application
infrastructure persistence
```

---

## 19.11 Commit-specific documentation and validation requirements

### ID-003 value objects

In addition to the existing ID-003 requirements:

```text
Every value object must have class-level JavaDoc.
Every record value object must document all components with @param JavaDoc.
PermissionCode must document the context:resource:action format.
RoleCode must document uppercase snake-case format.
Username must document accepted format and normalization.
EmailAddress must document email normalization.
EmployeeReference must document that it does not import organization.
```

### ID-004 permission model

In addition to the existing ID-004 requirements:

```text
Permission must document its catalog role.
PermissionCodePolicy must document the validation algorithm.
PermissionCatalog must document that it is a domain contract, not a JPA repository.
Domain validation must not use Bean Validation.
```

### ID-005 role model

In addition to the existing ID-005 requirements:

```text
Role must document aggregate ownership.
Role must document lifecycle behavior.
Role must document permission assignment invariants.
RolePermissionAssignment must document fields or record components.
Role methods must document which invariant they protect.
```

### ID-006 user model

In addition to the existing ID-006 requirements:

```text
User must document aggregate ownership.
User must document lifecycle transitions.
User must document role assignment invariants.
User must document employee-reference boundary.
UserRoleAssignment must document fields or record components.
```

### ID-010 commands and queries

In addition to the existing ID-010 requirements:

```text
Every command and query must have JavaDoc.
Every command/query record component must have @param JavaDoc.
Command/query JavaDoc must explain whether validation is syntactic, semantic, or delegated to domain.
Commands and queries must not use Spring or JPA annotations.
```

### ID-011 ports and DTOs

In addition to the existing ID-011 requirements:

```text
Every port must have JavaDoc documenting its use-case or dependency role.
Every application DTO must have JavaDoc.
Every application DTO record component must have @param JavaDoc.
Application DTOs must not expose JPA entities.
Application DTOs must not import REST DTOs.
```

### ID-015 REST API contracts and controllers

In addition to the existing ID-015 requirements:

```text
Every request DTO must have class-level JavaDoc.
Every request DTO record component must have @param JavaDoc.
Every request DTO component must use Bean Validation annotations where applicable.
Every response DTO must have class-level JavaDoc.
Every response DTO record component must have @param JavaDoc.
Every controller must have class-level JavaDoc.
Every controller endpoint method must have method-level JavaDoc.
Controllers must use OpenAPI annotations if springdoc-openapi is available.
Controllers must document validation behavior and application port usage.
API layer may import jakarta.validation.* and OpenAPI annotations.
Domain and application layers must not import OpenAPI annotations.
```

---

## 19.12 Additional final checklist items

When executing `ID-021`, include these checklist items in the final identity checklist:

```text
[ ] Domain models have class-level and field/component documentation
[ ] Domain model validation does not use Bean Validation
[ ] Domain value objects have validation and format documentation
[ ] Application DTOs have class-level and component documentation
[ ] REST request DTOs have Bean Validation annotations
[ ] REST request DTOs have class-level and component documentation
[ ] REST response DTOs have class-level and component documentation
[ ] Controllers have class-level documentation
[ ] Controller endpoint methods have method-level documentation
[ ] Controllers use OpenAPI documentation annotations where springdoc is available
[ ] Controllers depend only on application inbound ports
[ ] Controllers do not access repositories or JPA entities
```

---

## 19.13 AI-agent enforcement rule

If an AI agent creates an identity class, record, DTO, or controller without the required documentation and validation constraints from this section, the task is incomplete.

The agent must fix documentation and validation before marking the roadmap commit as completed.

---

# 20. Mandatory Swagger `@Schema` Documentation Rules

This section is mandatory for all AI agents implementing the `identity` module.

If any earlier section treats Swagger/OpenAPI documentation as optional, this section overrides it.

## 20.1 Required dependency assumption

The project is expected to include `springdoc-openapi-starter-webmvc-ui`.

If the dependency is missing:

```text
Do not silently skip Swagger annotations.
Mark the affected identity API task as Blocked.
Record the missing dependency in docs/roadmap/identity.md.
Ask for dependency approval.
```

Swagger/OpenAPI annotations must be imported only in the API layer:

```text
src/main/java/dz/sh/hidra/modules/identity/api/**
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

Forbidden OpenAPI imports outside API layer:

```text
domain
application
infrastructure
kernel
platform
```

---

## 20.2 `@Schema` requirement for REST request DTOs

Every REST request DTO under:

```text
src/main/java/dz/sh/hidra/modules/identity/api/rest/request
```

must use:

```java
@Schema
```

at both:

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

Example:

```java
@Schema(
    name = "RegisterUserRequest",
    description = "Request body used to register a new Hidra identity user."
)
public record RegisterUserRequest(

    @NotBlank
    @Size(min = 3, max = 80)
    @Schema(
        description = "Unique username requested for the Hidra identity user.",
        example = "a.medjerab",
        minLength = 3,
        maxLength = 80,
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String username,

    @NotBlank
    @Email
    @Size(max = 120)
    @Schema(
        description = "Email address associated with the Hidra identity user.",
        example = "abir.medjerab@example.com",
        maxLength = 120,
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String email
) {
}
```

---

## 20.3 `@Schema` requirement for REST response DTOs

Every REST response DTO under:

```text
src/main/java/dz/sh/hidra/modules/identity/api/rest/response
```

must use:

```java
@Schema
```

at both:

```text
record/class level
field/record-component level
```

Response DTO fields must document:

```text
business meaning
format
example
nullability if relevant
status/value constraints if relevant
```

Example:

```java
@Schema(
    name = "UserResponse",
    description = "Response representing a Hidra identity user."
)
public record UserResponse(

    @Schema(
        description = "Stable identifier of the Hidra identity user.",
        example = "usr_01JZ7K6E4M8KX2R5G9H1Q2A3B4"
    )
    String userId,

    @Schema(
        description = "Unique username of the Hidra identity user.",
        example = "a.medjerab"
    )
    String username,

    @Schema(
        description = "Current lifecycle status of the identity user.",
        example = "ACTIVE",
        allowableValues = {"REGISTERED", "ACTIVE", "SUSPENDED", "DISABLED"}
    )
    String status
) {
}
```

---

## 20.4 `@Schema` and Bean Validation alignment

Swagger `@Schema` documentation must not contradict Bean Validation.

For every request DTO:

| Bean Validation | Matching `@Schema` expectation |
|---|---|
| `@NotBlank` / `@NotNull` | `requiredMode = Schema.RequiredMode.REQUIRED` |
| `@Size(min = x, max = y)` | `minLength = x`, `maxLength = y` when applicable |
| `@Pattern(regexp = "...")` | `pattern = "..."` |
| enum-like values | `allowableValues = {...}` |
| domain format rule | `description` must explain the rule and include a valid `example` |

If a field has a required validation annotation, but no required schema mode, the task is incomplete.

If a field has a regex pattern, but no schema pattern or description of the format, the task is incomplete.

---

## 20.5 `@Schema` examples by identity type

Use realistic, non-secret examples.

Recommended examples:

| Field | Example |
|---|---|
| `username` | `a.medjerab` |
| `email` | `abir.medjerab@example.com` |
| `roleCode` | `TRC_OPERATOR` |
| `roleName` | `TRC Operator` |
| `permissionCode` | `telemetry:flow-reading:approve` |
| `userStatus` | `ACTIVE` |
| `roleStatus` | `ACTIVE` |
| `assignedAt` | `2026-05-30T10:15:30Z` |
| `correlationId` | `9f7c0d8a-9e3b-49b1-9e91-63d2f2e5f3a1` |

Do not use examples containing:

```text
real passwords
tokens
secret keys
real production credentials
private employee data
```

---

## 20.6 Controller Swagger documentation rules

Every controller under:

```text
src/main/java/dz/sh/hidra/modules/identity/api/rest/controller
```

must use:

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

Example:

```java
@Tag(
    name = "Identity Users",
    description = "Operations for managing Hidra identity users and their lifecycle."
)
@RestController
@RequestMapping("/api/v1/identity/users")
class IdentityUserController {

    /**
     * Registers a new Hidra identity user.
     *
     * @param request validated user registration request
     * @return registered user response
     */
    @Operation(
        summary = "Register identity user",
        description = "Creates a new Hidra identity user in REGISTERED status."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid registration request"),
        @ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    @PostMapping
    ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        // implementation
    }
}
```

---

## 20.7 Commit-specific Swagger requirements

### ID-015 REST API contracts and controllers

`ID-015` is incomplete unless:

```text
all request DTOs have class-level @Schema
all request DTO fields/components have @Schema
all response DTOs have class-level @Schema
all response DTO fields/components have @Schema
all request DTO Bean Validation annotations align with @Schema
all controllers have @Tag
all endpoint methods have @Operation
all endpoint methods have @ApiResponses
path variables use @Parameter when useful
OpenAPI annotations are used only in the API layer
```

### ID-019 API tests

`ID-019` should verify where practical that:

```text
request DTO validation constraints reject invalid inputs
controller endpoints return documented status behavior
controller tests cover validation failure cases
```

Swagger annotation presence does not require exhaustive reflection testing unless the team explicitly requests it.

---

## 20.8 Final checklist additions for ID-021

When executing `ID-021`, include these checklist items:

```text
[ ] REST request DTOs use @Schema at class level
[ ] REST request DTO fields/components use @Schema
[ ] REST response DTOs use @Schema at class level
[ ] REST response DTO fields/components use @Schema
[ ] @Schema documentation aligns with Bean Validation constraints
[ ] @Schema examples are realistic and contain no secrets
[ ] Identity controllers use @Tag
[ ] Identity controller endpoint methods use @Operation
[ ] Identity controller endpoint methods use @ApiResponses
[ ] OpenAPI annotations are restricted to identity API layer
```

---

## 20.9 AI-agent enforcement rule for Swagger

If an AI agent creates an identity REST request DTO, response DTO, or controller without the required Swagger/OpenAPI annotations from this section, the task is incomplete.

The agent must add the missing `@Schema`, `@Tag`, `@Operation`, `@ApiResponses`, and `@Parameter` annotations before marking the roadmap commit as completed.
