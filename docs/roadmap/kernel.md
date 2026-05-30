# HidraAPI Kernel Implementation Roadmap

## 1. Document Control

| Field | Value |
|---|---|
| Project | HidraAPI |
| Product | Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Roadmap file | `docs/roadmap/kernel.md` |
| Module | `kernel` |
| Root package | `dz.sh.hidra.kernel` |
| Source root | `src/main/java/dz/sh/hidra/kernel` |
| Test root | `src/test/java/dz/sh/hidra/kernel` |
| Author | Abir MEDJERAB |
| CreatedOn | 2025-06-26 |
| UpdatedOn | 2026-05-30 |
| Status | Ready for Codex execution |
| Execution mode | One commit code at a time |

---

## 2. Kernel Mission

The `kernel` module is the smallest stable foundation of HidraAPI.

It contains only generic, stable, cross-module primitives used by other HidraAPI modules.

The kernel must not become a `shared`, `sharedkernel`, `common`, `core`, or `utils` dumping ground.

The kernel must be independent from:

```text
Spring
JPA
Hibernate
platform
identityaccess
organization
topology
telemetry
workflow
planning
monitoring
incidents
audit
integration
analytics
notification
reporting
```

---

## 3. Strict Scope Rules

### 3.1 Allowed in `kernel`

The kernel may contain only:

```text
generic value objects
generic domain event contracts
generic domain exception contracts
generic domain model contracts
generic command/query markers
generic pagination primitives
generic operation result primitives
generic API response/error shapes
unit tests for kernel primitives
architecture tests protecting kernel boundaries
```

### 3.2 Forbidden in `kernel`

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

### 3.3 Forbidden package names

Do not create:

```text
src/main/java/dz/sh/hidra/shared/**
src/main/java/dz/sh/hidra/sharedkernel/**
src/main/java/dz/sh/hidra/common/**
src/main/java/dz/sh/hidra/core/**
src/main/java/dz/sh/hidra/utils/**
```

### 3.4 Allowed imports in production kernel code

Production kernel code may use only:

```text
java.*
java.time.*
java.util.*
```

Optional later only if justified:

```text
java.math.*
```

Forbidden imports:

```text
org.springframework.*
jakarta.persistence.*
org.hibernate.*
dz.sh.hidra.platform.*
dz.sh.hidra.modules.*
```

### 3.5 Dependency direction

Allowed dependency direction:

```text
modules -> kernel
platform -> kernel
kernel -> Java standard library only
```

Forbidden dependency direction:

```text
kernel -> platform
kernel -> modules
kernel -> Spring
kernel -> JPA
kernel -> Hibernate
```

---

## 4. Canonical Java Header

Every Java file created under `kernel` must start with this exact header style.

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
 * @Type        : <Class|Interface|Enum|Record|PackageInfo>
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : <actual-package-name>
 *
 * @Description : <one-sentence responsibility>
 *
 */
```

For test classes, use the same header and set:

```text
@Layer       : Kernel Test
@Module      : kernel
```

---

## 5. Final Kernel Production File Tree

This is the complete intended production tree.

```text
src/main/java/dz/sh/hidra/kernel
├── package-info.java
├── api
│   ├── package-info.java
│   ├── error
│   │   ├── ApiErrorCode.java
│   │   ├── ApiErrorResponse.java
│   │   ├── ValidationErrorDetail.java
│   │   └── package-info.java
│   └── response
│       ├── ApiResponse.java
│       ├── PagedApiResponse.java
│       └── package-info.java
├── application
│   ├── package-info.java
│   ├── command
│   │   ├── Command.java
│   │   └── package-info.java
│   ├── pagination
│   │   ├── PageRequest.java
│   │   ├── PageResult.java
│   │   ├── SortDirection.java
│   │   └── package-info.java
│   ├── query
│   │   ├── Query.java
│   │   └── package-info.java
│   └── result
│       ├── OperationResult.java
│       ├── ResultStatus.java
│       └── package-info.java
└── domain
    ├── package-info.java
    ├── event
    │   ├── DomainEvent.java
    │   ├── DomainEventId.java
    │   └── package-info.java
    ├── exception
    │   ├── BusinessRuleViolationException.java
    │   ├── DomainException.java
    │   ├── InvalidValueObjectException.java
    │   └── package-info.java
    ├── model
    │   ├── AggregateRoot.java
    │   ├── Entity.java
    │   ├── ValueObject.java
    │   └── package-info.java
    └── value
        ├── ActorId.java
        ├── CorrelationId.java
        ├── DateRange.java
        ├── OrganizationScopeId.java
        ├── RequestId.java
        ├── TimeRange.java
        └── package-info.java
```

---

## 6. Final Kernel Test File Tree

Do not create test `package-info.java` files.

Create test files only when implementing test commits.

```text
src/test/java/dz/sh/hidra/kernel
├── KernelArchitectureTest.java
├── api
│   ├── error
│   │   ├── ApiErrorResponseTest.java
│   │   └── ValidationErrorDetailTest.java
│   └── response
│       ├── ApiResponseTest.java
│       └── PagedApiResponseTest.java
├── application
│   ├── pagination
│   │   ├── PageRequestTest.java
│   │   └── PageResultTest.java
│   └── result
│       └── OperationResultTest.java
└── domain
    ├── event
    │   └── DomainEventIdTest.java
    ├── exception
    │   ├── BusinessRuleViolationExceptionTest.java
    │   └── InvalidValueObjectExceptionTest.java
    ├── model
    │   └── DomainModelContractTest.java
    └── value
        ├── ActorIdTest.java
        ├── CorrelationIdTest.java
        ├── DateRangeTest.java
        ├── OrganizationScopeIdTest.java
        ├── RequestIdTest.java
        └── TimeRangeTest.java
```

---

## 7. Implementation Style Rules

### 7.1 Identifier value objects

The following files must be Java `record`s:

```text
CorrelationId
RequestId
ActorId
OrganizationScopeId
DomainEventId
```

Each identifier record must:

- implement `ValueObject`
- reject null
- reject blank
- trim input
- expose `static of(String value)`
- expose `static newId()` when UUID generation is appropriate
- throw `InvalidValueObjectException`, not `IllegalArgumentException`

Recommended shape:

```java
public record CorrelationId(String value) implements ValueObject {

    public CorrelationId {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("CorrelationId must not be blank.");
        }
        value = value.trim();
    }

    public static CorrelationId of(String value) {
        return new CorrelationId(value);
    }

    public static CorrelationId newId() {
        return new CorrelationId(UUID.randomUUID().toString());
    }
}
```

### 7.2 Temporal value objects

`DateRange` and `TimeRange` must:

- implement `ValueObject`
- be immutable
- reject null start values
- allow open-ended ranges through explicit factory methods only
- reject end values before start values
- expose `contains(...)`
- use `InvalidValueObjectException`

### 7.3 Exception contracts

Kernel exceptions must:

- extend `RuntimeException`
- be generic
- not mention HTTP
- not mention Spring
- not mention a specific bounded context

### 7.4 Domain model contracts

Keep model contracts minimal.

Do not add:

- persistence annotations
- lifecycle hooks
- domain event storage
- framework dependencies
- base equality implementation

### 7.5 API primitives

API error and response classes must:

- be framework-independent
- not import Spring `HttpStatus`
- use `int status` where a status code is required
- use immutable list copies for list fields
- include correlation ID support where relevant

---

## 8. Corrected Commit Plan Overview

This order is mandatory.

It fixes dependency ordering so exceptions and model contracts exist before value objects use them.

| Commit code | Commit message | Purpose |
|---|---|---|
| `KER-001` | `docs(kernel): add kernel roadmap` | Add this roadmap file |
| `KER-002` | `chore(kernel): add kernel package skeleton` | Add production `package-info.java` files only |
| `KER-003` | `feat(kernel): add domain exception contracts` | Add generic domain exceptions |
| `KER-004` | `feat(kernel): add domain model contracts` | Add `AggregateRoot`, `Entity`, `ValueObject` |
| `KER-005` | `feat(kernel): add identity and tracing value objects` | Add `CorrelationId`, `RequestId`, `ActorId`, `OrganizationScopeId` |
| `KER-006` | `feat(kernel): add temporal range value objects` | Add `DateRange`, `TimeRange` |
| `KER-007` | `feat(kernel): add domain event contracts` | Add `DomainEvent`, `DomainEventId` |
| `KER-008` | `feat(kernel): add command and query markers` | Add `Command`, `Query` |
| `KER-009` | `feat(kernel): add pagination primitives` | Add `PageRequest`, `PageResult`, `SortDirection` |
| `KER-010` | `feat(kernel): add operation result primitives` | Add `OperationResult`, `ResultStatus` |
| `KER-011` | `feat(kernel): add API error primitives` | Add `ApiErrorCode`, `ApiErrorResponse`, `ValidationErrorDetail` |
| `KER-012` | `feat(kernel): add API response primitives` | Add `ApiResponse`, `PagedApiResponse` |
| `KER-013` | `test(kernel): add kernel unit tests` | Add unit tests for kernel files |
| `KER-014` | `test(kernel): add kernel architecture guardrail` | Add ArchUnit boundary test if ArchUnit exists |
| `KER-015` | `docs(kernel): finalize kernel checklist` | Update this roadmap with final status |

---

# 9. Detailed Commit Specifications

---

## KER-001 — Add Kernel Roadmap

### Commit message

```text
docs(kernel): add kernel roadmap
```

### Description

Create the roadmap file used by AI agents and developers to implement the kernel module safely.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `docs/roadmap/kernel.md` | Kernel implementation plan and execution memory |

### Acceptance criteria

- The file exists at exactly `docs/roadmap/kernel.md`.
- It includes commit codes, commit messages, descriptions, file paths, file purposes, acceptance criteria, and validation commands.
- No Java source files are created in this commit.

### Validation

```bash
test -f docs/roadmap/kernel.md
```

---

## KER-002 — Add Kernel Package Skeleton

### Commit message

```text
chore(kernel): add kernel package skeleton
```

### Description

Create only production package structure using `package-info.java`.

Do not create implementation classes.

Do not create test package-info files.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/kernel/package-info.java` | Root kernel package boundary |
| Create | `src/main/java/dz/sh/hidra/kernel/api/package-info.java` | API primitive package boundary |
| Create | `src/main/java/dz/sh/hidra/kernel/api/error/package-info.java` | API error package boundary |
| Create | `src/main/java/dz/sh/hidra/kernel/api/response/package-info.java` | API response package boundary |
| Create | `src/main/java/dz/sh/hidra/kernel/application/package-info.java` | Application primitive package boundary |
| Create | `src/main/java/dz/sh/hidra/kernel/application/command/package-info.java` | Command marker package boundary |
| Create | `src/main/java/dz/sh/hidra/kernel/application/pagination/package-info.java` | Pagination primitive package boundary |
| Create | `src/main/java/dz/sh/hidra/kernel/application/query/package-info.java` | Query marker package boundary |
| Create | `src/main/java/dz/sh/hidra/kernel/application/result/package-info.java` | Operation result package boundary |
| Create | `src/main/java/dz/sh/hidra/kernel/domain/package-info.java` | Domain primitive package boundary |
| Create | `src/main/java/dz/sh/hidra/kernel/domain/event/package-info.java` | Domain event package boundary |
| Create | `src/main/java/dz/sh/hidra/kernel/domain/exception/package-info.java` | Domain exception package boundary |
| Create | `src/main/java/dz/sh/hidra/kernel/domain/model/package-info.java` | Domain model contract package boundary |
| Create | `src/main/java/dz/sh/hidra/kernel/domain/value/package-info.java` | Value object package boundary |
| Update | `docs/roadmap/kernel.md` | Mark `KER-002` as completed after execution |

### Package-info requirements

Each `package-info.java` must:

- use the canonical header
- use `@Type        : PackageInfo`
- use `@Layer       : Kernel`
- use `@Module      : kernel`
- use the exact package name
- include package-level JavaDoc explaining what belongs and what is forbidden

### Acceptance criteria

- Only production `package-info.java` files are created.
- No test `package-info.java` files are created.
- No class, record, enum, service, repository, DTO, controller, or entity is created.
- Every file uses the canonical HidraAPI header.
- No `shared`, `sharedkernel`, `common`, `core`, or `utils` package exists.

### Validation

```bash
find src/main/java/dz/sh/hidra/kernel -type f | sort
mvn -q -DskipTests compile
```

---

## KER-003 — Add Domain Exception Contracts

### Commit message

```text
feat(kernel): add domain exception contracts
```

### Description

Add generic exception types used by kernel and future domain modules.

Exceptions are intentionally implemented before value objects so value objects can use `InvalidValueObjectException`.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/kernel/domain/exception/DomainException.java` | Base runtime exception for generic domain failures |
| Create | `src/main/java/dz/sh/hidra/kernel/domain/exception/BusinessRuleViolationException.java` | Generic exception for violated business invariants |
| Create | `src/main/java/dz/sh/hidra/kernel/domain/exception/InvalidValueObjectException.java` | Generic exception for invalid value object construction |
| Update | `docs/roadmap/kernel.md` | Mark `KER-003` as completed after execution |

### Design requirements

- `DomainException` extends `RuntimeException`.
- `BusinessRuleViolationException` extends `DomainException`.
- `InvalidValueObjectException` extends `DomainException`.
- Include useful constructors:
  - message
  - message + cause
- No HTTP status.
- No Spring classes.
- No API/platform dependency.
- No module-specific error codes.

### Acceptance criteria

- Exceptions compile.
- Exceptions are generic.
- No Spring/JPA/platform/module imports.
- Exceptions contain canonical headers.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## KER-004 — Add Domain Model Contracts

### Commit message

```text
feat(kernel): add domain model contracts
```

### Description

Add minimal marker contracts for DDD modeling.

This commit is intentionally before value objects so future value objects can implement `ValueObject`.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/kernel/domain/model/AggregateRoot.java` | Minimal aggregate-root contract |
| Create | `src/main/java/dz/sh/hidra/kernel/domain/model/Entity.java` | Minimal domain entity contract |
| Create | `src/main/java/dz/sh/hidra/kernel/domain/model/ValueObject.java` | Minimal value object marker |
| Update | `docs/roadmap/kernel.md` | Mark `KER-004` as completed after execution |

### Design requirements

Recommended minimal shape:

```java
public interface AggregateRoot<ID> {
    ID id();
}
```

```java
public interface Entity<ID> {
    ID id();
}
```

```java
public interface ValueObject {
}
```

Do not add:

- persistence annotations
- domain event storage
- validation framework dependency
- equals/hashCode base class
- lifecycle behavior

### Acceptance criteria

- Contracts are minimal.
- Contracts compile.
- No framework coupling.
- No business concept is introduced.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## KER-005 — Add Identity and Tracing Value Objects

### Commit message

```text
feat(kernel): add identity and tracing value objects
```

### Description

Add generic identifier value objects used for tracing, request identity, actor references, and organization scoping.

These files are safe kernel primitives because they are generic and do not own module-specific business rules.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/kernel/domain/value/CorrelationId.java` | Cross-request correlation identifier for logs, audit, events, and tracing |
| Create | `src/main/java/dz/sh/hidra/kernel/domain/value/RequestId.java` | Identifier for one API/request execution |
| Create | `src/main/java/dz/sh/hidra/kernel/domain/value/ActorId.java` | Generic actor reference without knowing user/employee/system details |
| Create | `src/main/java/dz/sh/hidra/kernel/domain/value/OrganizationScopeId.java` | Generic organization/operational scope reference |
| Update | `docs/roadmap/kernel.md` | Mark `KER-005` as completed after execution |

### Design requirements

Each file must:

- be a Java `record`
- implement `ValueObject`
- reject null
- reject blank
- trim input
- expose `static of(String value)`
- expose `static newId()` where UUID generation is appropriate
- throw `InvalidValueObjectException`
- use `UUID.randomUUID().toString()` for generated identifiers
- not import Spring/JPA/platform/modules

### Acceptance criteria

- All value objects are immutable.
- All invalid values are rejected.
- All records implement `ValueObject`.
- No business-specific behavior exists.
- No dependency other than Java and kernel exception/model packages.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## KER-006 — Add Temporal Range Value Objects

### Commit message

```text
feat(kernel): add temporal range value objects
```

### Description

Add generic temporal ranges for planning periods, organization assignment periods, telemetry windows, monitoring windows, audit queries, and analytics filters.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/kernel/domain/value/DateRange.java` | Date-only range using `LocalDate` |
| Create | `src/main/java/dz/sh/hidra/kernel/domain/value/TimeRange.java` | Instant/timestamp range using `Instant` |
| Update | `docs/roadmap/kernel.md` | Mark `KER-006` as completed after execution |

### Design requirements

`DateRange` must:

- be immutable
- implement `ValueObject`
- use `java.time.LocalDate`
- provide `closed(LocalDate start, LocalDate end)`
- provide `openEnded(LocalDate start)`
- reject null start
- reject end before start
- provide `contains(LocalDate date)`
- throw `InvalidValueObjectException`

`TimeRange` must:

- be immutable
- implement `ValueObject`
- use `java.time.Instant`
- provide `closed(Instant start, Instant end)`
- provide `openEnded(Instant start)`
- reject null start
- reject end before start
- provide `contains(Instant instant)`
- throw `InvalidValueObjectException`

### Acceptance criteria

- Both value objects are immutable.
- Both value objects are generic.
- Both implement `ValueObject`.
- No business-specific period names exist.
- No Spring/JPA/platform/module imports.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## KER-007 — Add Domain Event Contracts

### Commit message

```text
feat(kernel): add domain event contracts
```

### Description

Add minimal generic domain event contracts.

Do not implement outbox here. Outbox belongs later to `platform.events.outbox`.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/kernel/domain/event/DomainEvent.java` | Generic domain event contract |
| Create | `src/main/java/dz/sh/hidra/kernel/domain/event/DomainEventId.java` | Immutable identifier for domain events |
| Update | `docs/roadmap/kernel.md` | Mark `KER-007` as completed after execution |

### Design requirements

`DomainEvent` should expose:

```text
DomainEventId eventId()
Instant occurredAt()
String eventType()
```

`DomainEventId` must:

- be a Java `record`
- implement `ValueObject`
- reject null
- reject blank
- trim input
- provide `static of(String value)`
- provide `static newId()`
- throw `InvalidValueObjectException`

Do not add:

- outbox status
- retry count
- JSON payload
- persistence annotations
- platform dependency

### Acceptance criteria

- Event contract is generic.
- Event ID is immutable and validated.
- No outbox implementation is introduced.
- No module-specific events are introduced.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## KER-008 — Add Command and Query Markers

### Commit message

```text
feat(kernel): add command and query markers
```

### Description

Add generic application-layer markers for use-case input objects.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/kernel/application/command/Command.java` | Marker for commands that mutate state |
| Create | `src/main/java/dz/sh/hidra/kernel/application/query/Query.java` | Marker for queries that read state |
| Update | `docs/roadmap/kernel.md` | Mark `KER-008` as completed after execution |

### Design requirements

- Marker interfaces only.
- No validation logic.
- No transaction logic.
- No Spring annotations.
- No business-specific command/query objects.

### Acceptance criteria

- Interfaces compile.
- No implementation classes.
- No business-specific command/query objects.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## KER-009 — Add Pagination Primitives

### Commit message

```text
feat(kernel): add pagination primitives
```

### Description

Add framework-independent pagination primitives.

Do not depend on Spring Data `Pageable`.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/kernel/application/pagination/PageRequest.java` | Generic pagination request |
| Create | `src/main/java/dz/sh/hidra/kernel/application/pagination/PageResult.java` | Generic pagination result |
| Create | `src/main/java/dz/sh/hidra/kernel/application/pagination/SortDirection.java` | Generic sort direction enum |
| Update | `docs/roadmap/kernel.md` | Mark `KER-009` as completed after execution |

### Design requirements

`PageRequest` must:

- be immutable
- reject negative page index
- reject size less than 1
- define maximum page size, recommended `200`
- expose `of(int page, int size)`
- expose optional sort field if needed
- expose optional `SortDirection`
- not import Spring Data

`PageResult<T>` must:

- be immutable
- contain items
- contain page index
- contain page size
- contain total elements
- contain total pages
- defensively copy list values
- expose empty result factory

`SortDirection` values:

```text
ASC
DESC
```

### Acceptance criteria

- No Spring Data dependency.
- Invalid paging input is rejected.
- Lists are immutable/defensive.
- No framework coupling.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## KER-010 — Add Operation Result Primitives

### Commit message

```text
feat(kernel): add operation result primitives
```

### Description

Add a generic result wrapper for application operations when explicit success/failure status is useful.

This does not replace exceptions everywhere.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/kernel/application/result/OperationResult.java` | Generic application operation result |
| Create | `src/main/java/dz/sh/hidra/kernel/application/result/ResultStatus.java` | Generic operation status enum |
| Update | `docs/roadmap/kernel.md` | Mark `KER-010` as completed after execution |

### Design requirements

`ResultStatus` values:

```text
SUCCESS
FAILURE
NOT_FOUND
VALIDATION_FAILED
FORBIDDEN
CONFLICT
```

`OperationResult<T>` must support:

- success with value
- success without value
- failure with message
- failure with status
- optional error code string
- no HTTP status
- no Spring dependency

### Acceptance criteria

- No API dependency.
- No Spring dependency.
- Immutable design.
- No business-specific result types.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## KER-011 — Add API Error Primitives

### Commit message

```text
feat(kernel): add API error primitives
```

### Description

Add generic API error structures that `platform.exception` will use later.

Do not implement exception handlers here.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/kernel/api/error/ApiErrorCode.java` | Generic API error categories |
| Create | `src/main/java/dz/sh/hidra/kernel/api/error/ApiErrorResponse.java` | Standard API error response body |
| Create | `src/main/java/dz/sh/hidra/kernel/api/error/ValidationErrorDetail.java` | Standard validation error detail |
| Update | `docs/roadmap/kernel.md` | Mark `KER-011` as completed after execution |

### Design requirements

`ApiErrorCode` initial values:

```text
VALIDATION_ERROR
DOMAIN_ERROR
AUTHENTICATION_ERROR
AUTHORIZATION_ERROR
NOT_FOUND
CONFLICT
INTERNAL_ERROR
INFRASTRUCTURE_ERROR
```

`ApiErrorResponse` fields:

```text
Instant timestamp
int status
ApiErrorCode error
String message
String path
String correlationId
List<ValidationErrorDetail> details
```

`ValidationErrorDetail` fields:

```text
String field
String message
String rejectedValue
```

Rules:

- no Spring `HttpStatus`
- no exception handler
- immutable list handling
- support correlation ID

### Acceptance criteria

- No Spring Web dependency.
- Error shape supports correlation ID.
- No platform code is created.
- No business-specific error code is created.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## KER-012 — Add API Response Primitives

### Commit message

```text
feat(kernel): add API response primitives
```

### Description

Add generic successful API response structures.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/kernel/api/response/ApiResponse.java` | Generic successful API response wrapper |
| Create | `src/main/java/dz/sh/hidra/kernel/api/response/PagedApiResponse.java` | Generic paged API response wrapper |
| Update | `docs/roadmap/kernel.md` | Mark `KER-012` as completed after execution |

### Design requirements

`ApiResponse<T>` fields:

```text
T data
String message
String correlationId
Instant timestamp
```

`PagedApiResponse<T>` fields:

```text
List<T> data
int page
int size
long totalElements
int totalPages
String correlationId
Instant timestamp
```

Rules:

- no HTTP status
- no Spring dependency
- immutable list handling

### Acceptance criteria

- Generic and reusable.
- No business concepts.
- No framework coupling.
- Supports correlation ID.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## KER-013 — Add Kernel Unit Tests

### Commit message

```text
test(kernel): add kernel unit tests
```

### Description

Add focused unit tests for kernel primitives.

Do not use Spring Boot context.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/test/java/dz/sh/hidra/kernel/domain/value/CorrelationIdTest.java` | Verifies correlation ID creation and invalid input rejection |
| Create | `src/test/java/dz/sh/hidra/kernel/domain/value/RequestIdTest.java` | Verifies request ID creation and invalid input rejection |
| Create | `src/test/java/dz/sh/hidra/kernel/domain/value/ActorIdTest.java` | Verifies actor ID creation and invalid input rejection |
| Create | `src/test/java/dz/sh/hidra/kernel/domain/value/OrganizationScopeIdTest.java` | Verifies organization scope ID creation and invalid input rejection |
| Create | `src/test/java/dz/sh/hidra/kernel/domain/value/DateRangeTest.java` | Verifies valid and invalid date ranges |
| Create | `src/test/java/dz/sh/hidra/kernel/domain/value/TimeRangeTest.java` | Verifies valid and invalid time ranges |
| Create | `src/test/java/dz/sh/hidra/kernel/domain/event/DomainEventIdTest.java` | Verifies domain event ID creation and invalid input rejection |
| Create | `src/test/java/dz/sh/hidra/kernel/domain/exception/BusinessRuleViolationExceptionTest.java` | Verifies business rule exception constructors |
| Create | `src/test/java/dz/sh/hidra/kernel/domain/exception/InvalidValueObjectExceptionTest.java` | Verifies invalid value object exception constructors |
| Create | `src/test/java/dz/sh/hidra/kernel/domain/model/DomainModelContractTest.java` | Verifies minimal domain model contracts can be implemented |
| Create | `src/test/java/dz/sh/hidra/kernel/application/pagination/PageRequestTest.java` | Verifies pagination request validation |
| Create | `src/test/java/dz/sh/hidra/kernel/application/pagination/PageResultTest.java` | Verifies pagination result creation |
| Create | `src/test/java/dz/sh/hidra/kernel/application/result/OperationResultTest.java` | Verifies operation result factories |
| Create | `src/test/java/dz/sh/hidra/kernel/api/error/ApiErrorResponseTest.java` | Verifies API error response shape |
| Create | `src/test/java/dz/sh/hidra/kernel/api/error/ValidationErrorDetailTest.java` | Verifies validation error detail shape |
| Create | `src/test/java/dz/sh/hidra/kernel/api/response/ApiResponseTest.java` | Verifies API response shape |
| Create | `src/test/java/dz/sh/hidra/kernel/api/response/PagedApiResponseTest.java` | Verifies paged response shape |
| Update | `docs/roadmap/kernel.md` | Mark `KER-013` as completed after execution |

### Test requirements

- JUnit 5.
- AssertJ if available.
- No `@SpringBootTest`.
- No database.
- No Testcontainers.
- No Spring context.
- Fast and deterministic tests only.

### Acceptance criteria

- Kernel unit tests pass.
- Tests cover invalid inputs.
- Tests cover defensive/immutable list behavior.
- Tests cover temporal range behavior.
- Tests cover result factories.

### Validation

```bash
mvn -q test
```

---

## KER-014 — Add Kernel Architecture Guardrail

### Commit message

```text
test(kernel): add kernel architecture guardrail
```

### Description

Add an ArchUnit test that prevents kernel boundary violations.

Only do this if ArchUnit exists in `pom.xml`.

If ArchUnit is missing:

- do not add `KernelArchitectureTest`
- mark `KER-014` as `Blocked`
- record missing dependency in this roadmap
- stop and ask for dependency approval

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/test/java/dz/sh/hidra/kernel/KernelArchitectureTest.java` | Ensures kernel has no forbidden dependencies or package drift |
| Update | `docs/roadmap/kernel.md` | Mark `KER-014` as completed or blocked after execution |

### Required architecture rules

The test must assert that kernel classes do not depend on:

```text
dz.sh.hidra.platform..
dz.sh.hidra.modules..
org.springframework..
jakarta.persistence..
org.hibernate..
```

It must also assert no production packages exist under:

```text
dz.sh.hidra.shared..
dz.sh.hidra.sharedkernel..
dz.sh.hidra.common..
dz.sh.hidra.core..
dz.sh.hidra.utils..
```

### Acceptance criteria

- Architecture test passes if ArchUnit exists.
- Test does not start Spring context.
- Test fails if forbidden imports are added.
- If ArchUnit is missing, the roadmap clearly marks this commit as blocked.

### Validation

```bash
mvn -q test -Dtest=KernelArchitectureTest
mvn -q test
```

---

## KER-015 — Finalize Kernel Checklist

### Commit message

```text
docs(kernel): finalize kernel checklist
```

### Description

Update this roadmap with final execution status, validation results, and remaining risks.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Update | `docs/roadmap/kernel.md` | Record final execution status and checklist |

### Required final checklist

```text
[ ] Kernel package structure exists
[ ] Kernel has no shared/sharedkernel/common/core/utils package
[ ] Kernel has no Spring dependency
[ ] Kernel has no JPA dependency
[ ] Kernel has no platform dependency
[ ] Kernel has no business module dependency
[ ] Kernel contains no business aggregates
[ ] Kernel value objects are immutable
[ ] Kernel unit tests pass
[ ] Kernel architecture guardrail test passes or is explicitly blocked with reason
[ ] mvn -q clean verify passes
```

### Validation

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
```

---

## 10. File Purpose Matrix

### 10.1 Package files

| File | Purpose |
|---|---|
| `kernel/package-info.java` | Documents the root kernel boundary |
| `kernel/api/package-info.java` | Documents API primitive boundary |
| `kernel/api/error/package-info.java` | Documents API error primitive boundary |
| `kernel/api/response/package-info.java` | Documents API response primitive boundary |
| `kernel/application/package-info.java` | Documents application primitive boundary |
| `kernel/application/command/package-info.java` | Documents command marker boundary |
| `kernel/application/pagination/package-info.java` | Documents pagination primitive boundary |
| `kernel/application/query/package-info.java` | Documents query marker boundary |
| `kernel/application/result/package-info.java` | Documents operation result boundary |
| `kernel/domain/package-info.java` | Documents domain primitive boundary |
| `kernel/domain/event/package-info.java` | Documents domain event boundary |
| `kernel/domain/exception/package-info.java` | Documents domain exception boundary |
| `kernel/domain/model/package-info.java` | Documents domain model contract boundary |
| `kernel/domain/value/package-info.java` | Documents value object boundary |

### 10.2 Production files

| File | Purpose |
|---|---|
| `ApiErrorCode.java` | Generic API error code enum |
| `ApiErrorResponse.java` | Standard API error body |
| `ValidationErrorDetail.java` | One validation error item |
| `ApiResponse.java` | Generic success response |
| `PagedApiResponse.java` | Generic paged success response |
| `Command.java` | Marker for mutating use-case inputs |
| `Query.java` | Marker for read-only use-case inputs |
| `PageRequest.java` | Framework-independent page request |
| `PageResult.java` | Framework-independent page result |
| `SortDirection.java` | Framework-independent sort direction |
| `OperationResult.java` | Generic application operation result |
| `ResultStatus.java` | Generic application result status |
| `DomainEvent.java` | Generic domain event interface |
| `DomainEventId.java` | Immutable domain event identifier |
| `DomainException.java` | Base generic domain exception |
| `BusinessRuleViolationException.java` | Generic invariant violation exception |
| `InvalidValueObjectException.java` | Generic invalid value object exception |
| `AggregateRoot.java` | Minimal aggregate-root contract |
| `Entity.java` | Minimal entity contract |
| `ValueObject.java` | Minimal value-object marker |
| `ActorId.java` | Generic actor identifier |
| `CorrelationId.java` | Generic correlation identifier |
| `DateRange.java` | Generic date range |
| `OrganizationScopeId.java` | Generic organization scope identifier |
| `RequestId.java` | Generic request identifier |
| `TimeRange.java` | Generic time range |

---

## 11. AI Agent Execution Rules

Any AI agent executing this file must follow these rules:

1. Execute exactly one commit code at a time.
2. Do not batch commits.
3. Do not create platform code during kernel work.
4. Do not create business module code during kernel work.
5. Do not create repositories, controllers, services, DTOs, or JPA entities in kernel.
6. Do not introduce Spring/JPA/Hibernate imports in kernel.
7. Do not create `shared`, `sharedkernel`, `common`, `core`, or `utils`.
8. Always use the canonical HidraAPI header.
9. Never change `@Author`.
10. Never change `@CreatedOn`.
11. Update this roadmap after each completed commit.
12. Run validation after each commit.
13. If validation cannot run, record the exact reason.
14. If a dependency is missing, stop and report it.
15. If a requested file does not belong to kernel, do not create it.

---

## 12. Current Status Table

| Commit code | Status | Notes |
|---|---|---|
| `KER-001` | Planned | Add this roadmap |
| `KER-002` | Completed | Package skeleton created; `find src/main/java/dz/sh/hidra/kernel -type f \| sort` listed production package-info files; `mvn -q -DskipTests compile` passed |
| `KER-003` | Completed | Domain exception contracts created; `mvn -q -DskipTests compile` passed |
| `KER-004` | Completed | Domain model contracts created; `mvn -q -DskipTests compile` passed |
| `KER-005` | Completed | Identity and tracing value objects created; `mvn -q -DskipTests compile` passed |
| `KER-006` | Completed | Temporal range value objects created; `mvn -q -DskipTests compile` passed |
| `KER-007` | Completed | Domain event contracts created; `mvn -q -DskipTests compile` passed |
| `KER-008` | Completed | Command and query markers created; `mvn -q -DskipTests compile` passed |
| `KER-009` | Planned | Add pagination primitives |
| `KER-010` | Planned | Add operation result primitives |
| `KER-011` | Planned | Add API error primitives |
| `KER-012` | Planned | Add API response primitives |
| `KER-013` | Planned | Add unit tests |
| `KER-014` | Planned | Add architecture guardrail if ArchUnit exists |
| `KER-015` | Planned | Finalize checklist |

---

## 13. Next Action

Start with:

```text
KER-001 — docs(kernel): add kernel roadmap
```

Then execute:

```text
KER-002 — chore(kernel): add kernel package skeleton
```

Do not implement kernel classes before the package skeleton is reviewed.
