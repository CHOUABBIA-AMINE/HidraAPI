# HidraAPI Platform Implementation Roadmap

## 1. Document Control

| Field | Value |
|---|---|
| Project | HidraAPI |
| Product | Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Roadmap file | `docs/roadmap/platform.md` |
| Module | `platform` |
| Root package | `dz.sh.hidra.platform` |
| Source root | `src/main/java/dz/sh/hidra/platform` |
| Test root | `src/test/java/dz/sh/hidra/platform` |
| Resource root | `src/main/resources` |
| Author | Abir MEDJERAB |
| CreatedOn | 2025-06-26 |
| UpdatedOn | 2026-05-30 |
| Status | Ready for AI-agent execution after kernel baseline |
| Execution mode | One commit code at a time |

---

## 2. Platform Mission

The `platform` module contains HidraAPI technical infrastructure.

It provides runtime support for:

```text
configuration
exception handling
observability
correlation/request tracing
structured logging support
security plumbing
persistence configuration
transaction support
domain event dispatch infrastructure
outbox infrastructure
health checks
metrics hooks
future tenancy/scope context
```

The platform module must not own Hidra business meaning.

It supports modules, but it does not become a business module.

---

## 3. Required Preconditions

Before implementing platform code beyond package skeleton, verify that the kernel baseline exists.

Minimum required kernel files:

```text
src/main/java/dz/sh/hidra/kernel/domain/value/CorrelationId.java
src/main/java/dz/sh/hidra/kernel/domain/value/RequestId.java
src/main/java/dz/sh/hidra/kernel/domain/value/ActorId.java
src/main/java/dz/sh/hidra/kernel/domain/value/OrganizationScopeId.java
src/main/java/dz/sh/hidra/kernel/domain/event/DomainEvent.java
src/main/java/dz/sh/hidra/kernel/domain/exception/DomainException.java
src/main/java/dz/sh/hidra/kernel/api/error/ApiErrorCode.java
src/main/java/dz/sh/hidra/kernel/api/error/ApiErrorResponse.java
src/main/java/dz/sh/hidra/kernel/api/error/ValidationErrorDetail.java
```

If these files do not exist:

```text
Stop.
Mark the platform task as Blocked.
Record the missing kernel files in this roadmap.
Do not create temporary duplicates inside platform.
```

---

## 4. Strict Scope Rules

### 4.1 Allowed in `platform`

The platform module may contain:

```text
Spring Boot configuration
@ConfigurationProperties classes
global exception handling
correlation ID filters
request ID filters
MDC/logging context support
security filter-chain configuration
authentication/authorization technical handlers
current security context adapters
JPA/Flyway/auditing configuration
transaction helper classes
domain event serialization infrastructure
outbox JPA entity/repository/dispatcher infrastructure
health indicators
metrics binders
tenancy/scope context placeholders
platform tests
platform architecture tests
```

### 4.2 Forbidden in `platform`

The platform module must never own:

```text
User aggregate
Role aggregate
Permission aggregate
Employee aggregate
Pipeline aggregate
FlowReading aggregate
WorkflowInstance aggregate
Incident aggregate
AuditEvent aggregate
Business approval rules
Business validation rules
Business authorization meaning
Domain-specific workflow rules
Module-specific repositories
Module-specific DTOs
Module-specific controllers
```

### 4.3 Business meaning location

Business meaning belongs later to business modules:

| Business concept | Correct future module |
|---|---|
| Users, roles, permissions, groups | `modules.identityaccess` |
| Employees, organization units | `modules.organization` |
| Pipelines, stations, equipment | `modules.topology` |
| Readings and telemetry facts | `modules.telemetry` |
| Approval/validation workflows | `modules.workflow` |
| Plans and targets | `modules.planning` |
| Thresholds, alerts, risk signals | `modules.monitoring` |
| Incidents and resolutions | `modules.incidents` |
| Operational audit trail | `modules.audit` |
| Connectors and ingestion jobs | `modules.integration` |
| KPIs and projections | `modules.analytics` |

---

## 5. Canonical Java Header

Every Java file created under `platform` must start with this exact header style.

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
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : <actual-package-name>
 *
 * @Description : <one-sentence responsibility>
 *
 */
```

For test classes, use:

```text
@Layer       : Platform Test
@Module      : platform
```

---

## 6. Dependency Rules

### 6.1 Allowed platform imports

Production platform code may import:

```text
java.*
java.time.*
java.util.*
jakarta.servlet.*
jakarta.validation.*
jakarta.persistence.*
org.springframework.*
org.hibernate.*
org.flywaydb.*
io.micrometer.*
dz.sh.hidra.kernel.*
```

Use framework imports only inside platform infrastructure code.

### 6.2 Forbidden platform imports

Production platform code must not import business aggregate packages such as:

```text
dz.sh.hidra.modules.identityaccess.domain.model.*
dz.sh.hidra.modules.organization.domain.model.*
dz.sh.hidra.modules.topology.domain.model.*
dz.sh.hidra.modules.telemetry.domain.model.*
dz.sh.hidra.modules.workflow.domain.model.*
dz.sh.hidra.modules.planning.domain.model.*
dz.sh.hidra.modules.monitoring.domain.model.*
dz.sh.hidra.modules.incidents.domain.model.*
dz.sh.hidra.modules.audit.domain.model.*
```

### 6.3 Allowed with care later

Platform security may later call an application port from `identityaccess`, but never the identity domain model directly.

Allowed later only if needed:

```text
dz.sh.hidra.modules.identityaccess.application.port.in.*
dz.sh.hidra.modules.identityaccess.application.port.out.*
```

If the identityaccess module does not exist yet, do not create a dependency on it.

---

## 7. Final Platform Production File Tree

This is the intended production tree after the full platform roadmap.

```text
src/main/java/dz/sh/hidra/platform
├── package-info.java
├── configuration
│   ├── ClockConfiguration.java
│   ├── HidraPlatformProperties.java
│   ├── JacksonConfiguration.java
│   ├── OpenApiConfiguration.java
│   ├── PlatformConfiguration.java
│   └── package-info.java
├── exception
│   ├── ApiErrorFactory.java
│   ├── GlobalExceptionHandler.java
│   ├── InfrastructureException.java
│   ├── PlatformException.java
│   └── package-info.java
├── observability
│   ├── package-info.java
│   ├── correlation
│   │   ├── CorrelationContext.java
│   │   ├── CorrelationHeaders.java
│   │   ├── CorrelationIdFilter.java
│   │   ├── CorrelationIdResolver.java
│   │   └── package-info.java
│   ├── health
│   │   ├── DatabaseReadinessHealthIndicator.java
│   │   ├── OutboxReadinessHealthIndicator.java
│   │   └── package-info.java
│   ├── logging
│   │   ├── LoggingContext.java
│   │   ├── SensitiveValueMasker.java
│   │   └── package-info.java
│   └── metrics
│       ├── PlatformMetricsConfiguration.java
│       ├── PlatformMetricsTags.java
│       └── package-info.java
├── security
│   ├── package-info.java
│   ├── authentication
│   │   ├── RestAuthenticationEntryPoint.java
│   │   ├── SecurityConfiguration.java
│   │   └── package-info.java
│   ├── authorization
│   │   ├── RestAccessDeniedHandler.java
│   │   └── package-info.java
│   └── context
│       ├── AuthenticatedPrincipal.java
│       ├── CurrentActorResolver.java
│       ├── CurrentSecurityContext.java
│       └── package-info.java
├── persistence
│   ├── package-info.java
│   ├── configuration
│   │   ├── JpaAuditingConfiguration.java
│   │   ├── JpaConfiguration.java
│   │   └── package-info.java
│   ├── migration
│   │   ├── MigrationMetadata.java
│   │   └── package-info.java
│   └── transaction
│       ├── TransactionConfiguration.java
│       ├── TransactionalUseCaseExecutor.java
│       └── package-info.java
├── events
│   ├── package-info.java
│   ├── dispatcher
│   │   ├── DomainEventPublisher.java
│   │   ├── OutboxDomainEventPublisher.java
│   │   └── package-info.java
│   ├── outbox
│   │   ├── OutboxEventEntity.java
│   │   ├── OutboxEventRepository.java
│   │   ├── OutboxEventStatus.java
│   │   ├── OutboxEventStore.java
│   │   └── package-info.java
│   └── serialization
│       ├── DomainEventSerializer.java
│       ├── JacksonDomainEventSerializer.java
│       ├── SerializedDomainEvent.java
│       └── package-info.java
└── tenancy
    ├── OrganizationScopeContext.java
    ├── TenantContext.java
    └── package-info.java
```

Resource files created by this roadmap:

```text
src/main/resources/db/migration/V001__create_platform_outbox_event.sql
```

---

## 8. Final Platform Test File Tree

Do not create test `package-info.java` files.

```text
src/test/java/dz/sh/hidra/platform
├── PlatformArchitectureTest.java
├── configuration
│   └── HidraPlatformPropertiesTest.java
├── exception
│   ├── ApiErrorFactoryTest.java
│   └── GlobalExceptionHandlerTest.java
├── observability
│   ├── correlation
│   │   ├── CorrelationContextTest.java
│   │   ├── CorrelationIdFilterTest.java
│   │   └── CorrelationIdResolverTest.java
│   └── logging
│       └── SensitiveValueMaskerTest.java
├── security
│   ├── authentication
│   │   └── SecurityConfigurationTest.java
│   └── context
│       └── CurrentSecurityContextTest.java
├── persistence
│   └── transaction
│       └── TransactionalUseCaseExecutorTest.java
├── events
│   ├── outbox
│   │   └── OutboxEventStoreTest.java
│   └── serialization
│       └── JacksonDomainEventSerializerTest.java
└── tenancy
    ├── OrganizationScopeContextTest.java
    └── TenantContextTest.java
```

---

## 9. Implementation Style Rules

### 9.1 Configuration classes

Configuration classes must:

- be located under `platform.configuration` or the relevant platform subpackage
- use Spring annotations only in platform
- not include business rules
- not import business aggregates
- be small and focused

### 9.2 Configuration properties

Typed properties must use:

```text
@ConfigurationProperties(prefix = "hidra.platform")
```

Do not hardcode environment-specific values in Java.

The `application.properties` files remain the environment source of truth.

### 9.3 Observability

Correlation and request tracing must:

- support `X-Correlation-Id`
- support `X-Request-Id`
- generate missing IDs
- store IDs in MDC
- expose IDs as response headers
- use kernel `CorrelationId` and `RequestId`
- never log secrets or tokens

### 9.4 Exception handling

Global exception handling must:

- use kernel `ApiErrorResponse`
- map validation errors to `VALIDATION_ERROR`
- map domain exceptions to `DOMAIN_ERROR`
- map authentication errors to `AUTHENTICATION_ERROR`
- map authorization errors to `AUTHORIZATION_ERROR`
- not leak stack traces in production
- include correlation ID when available

### 9.5 Security

Initial security is Spring Security foundation only.

Do not add OAuth2 Resource Server unless explicitly requested later.

Security must:

- configure basic API security safely
- expose actuator health as appropriate
- use REST-style error responses
- not define Hidra business roles or permissions
- not create `User`, `Role`, or `Permission`

### 9.6 Persistence

Platform persistence may configure:

- JPA
- auditing
- transaction support
- Flyway conventions

Business repositories belong in modules, not platform.

### 9.7 Events and outbox

Platform events may implement:

- event serialization
- outbox storage
- event publisher infrastructure

Platform events must not define business event meaning.

Business domain events come from modules and implement kernel `DomainEvent`.

### 9.8 Tenancy

Tenancy is a future-ready technical context only.

Do not implement complex multi-tenancy unless explicitly requested.

---

## 10. Commit Plan Overview

This order is mandatory.

| Commit code | Commit message | Purpose |
|---|---|---|
| `PLAT-001` | `docs(platform): add platform roadmap` | Add this roadmap file |
| `PLAT-002` | `chore(platform): add platform package skeleton` | Add production `package-info.java` files only |
| `PLAT-003` | `feat(platform): add typed platform configuration properties` | Add platform configuration properties and base config |
| `PLAT-004` | `feat(platform): add observability correlation support` | Add correlation/request ID infrastructure |
| `PLAT-005` | `feat(platform): add logging context and masking support` | Add logging context helpers and sensitive value masking |
| `PLAT-006` | `feat(platform): add global exception handling foundation` | Add API error factory and exception handler |
| `PLAT-007` | `feat(platform): add security authentication foundation` | Add Spring Security configuration and authentication error handler |
| `PLAT-008` | `feat(platform): add security context support` | Add current principal and actor resolver |
| `PLAT-009` | `feat(platform): add persistence and auditing foundation` | Add JPA/auditing/transaction infrastructure |
| `PLAT-010` | `feat(platform): add event serialization foundation` | Add domain event serialization support |
| `PLAT-011` | `feat(platform): add outbox persistence foundation` | Add outbox entity, repository, store, and migration |
| `PLAT-012` | `feat(platform): add domain event publisher foundation` | Add outbox-backed domain event publisher |
| `PLAT-013` | `feat(platform): add health indicators and metrics tags` | Add health indicators and platform metric tag support |
| `PLAT-014` | `feat(platform): add tenancy context foundation` | Add tenant and organization scope context placeholders |
| `PLAT-015` | `test(platform): add platform unit and slice tests` | Add platform tests |
| `PLAT-016` | `test(platform): add platform architecture guardrail` | Add ArchUnit platform boundary tests |
| `PLAT-017` | `docs(platform): finalize platform checklist` | Update this roadmap with final status |

---

## 11. Detailed Commit Specifications

## PLAT-001 — Add Platform Roadmap

### Commit message

```text
docs(platform): add platform roadmap
```

### Description

Create the roadmap file used by AI agents and developers to implement the platform module safely.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `docs/roadmap/platform.md` | Platform implementation plan and execution memory |

### Acceptance criteria

- The file exists at exactly `docs/roadmap/platform.md`.
- It includes commit codes, commit messages, descriptions, file paths, file purposes, acceptance criteria, and validation commands.
- No Java source files are created in this commit.

### Validation

```bash
test -f docs/roadmap/platform.md
```

---

## PLAT-002 — Add Platform Package Skeleton

### Commit message

```text
chore(platform): add platform package skeleton
```

### Description

Create only production package structure using `package-info.java`.

Do not create implementation classes.

Do not create test package-info files.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/platform/package-info.java` | Root platform package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/configuration/package-info.java` | Global technical configuration package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/exception/package-info.java` | Global exception handling package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/observability/package-info.java` | Observability package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/observability/correlation/package-info.java` | Correlation/request tracing package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/observability/health/package-info.java` | Health indicator package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/observability/logging/package-info.java` | Logging support package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/observability/metrics/package-info.java` | Metrics support package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/security/package-info.java` | Security plumbing package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/security/authentication/package-info.java` | Authentication infrastructure package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/security/authorization/package-info.java` | Authorization infrastructure package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/security/context/package-info.java` | Security context package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/persistence/package-info.java` | Persistence infrastructure package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/persistence/configuration/package-info.java` | JPA/Flyway configuration package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/persistence/migration/package-info.java` | Migration metadata package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/persistence/transaction/package-info.java` | Transaction support package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/events/package-info.java` | Event infrastructure package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/events/dispatcher/package-info.java` | Event dispatcher package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/events/outbox/package-info.java` | Outbox infrastructure package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/events/serialization/package-info.java` | Event serialization package boundary |
| Create | `src/main/java/dz/sh/hidra/platform/tenancy/package-info.java` | Tenancy/scope context package boundary |
| Update | `docs/roadmap/platform.md` | Mark `PLAT-002` as completed after execution |

### Package-info requirements

Each `package-info.java` must:

- use the canonical HidraAPI header
- use `@Type        : PackageInfo`
- use `@Layer       : Platform`
- use `@Module      : platform`
- use the exact package name
- include package-level JavaDoc explaining what belongs and what is forbidden

### Acceptance criteria

- Only production `package-info.java` files are created.
- No test `package-info.java` files are created.
- No implementation class is created.
- Every file uses the canonical HidraAPI header.
- No business package or business class is created.

### Validation

```bash
find src/main/java/dz/sh/hidra/platform -type f | sort
mvn -q -DskipTests compile
```

---

## PLAT-003 — Add Typed Platform Configuration Properties

### Commit message

```text
feat(platform): add typed platform configuration properties
```

### Description

Add typed configuration properties for `hidra.platform.*` values already present in `application.properties`.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/platform/configuration/HidraPlatformProperties.java` | Typed configuration properties for `hidra.platform.*` |
| Create | `src/main/java/dz/sh/hidra/platform/configuration/PlatformConfiguration.java` | Enables platform configuration properties |
| Create | `src/main/java/dz/sh/hidra/platform/configuration/ClockConfiguration.java` | Provides UTC application `Clock` bean |
| Create | `src/main/java/dz/sh/hidra/platform/configuration/JacksonConfiguration.java` | Central place for Jackson customizations compatible with Spring Boot 4 |
| Create | `src/main/java/dz/sh/hidra/platform/configuration/OpenApiConfiguration.java` | Central OpenAPI metadata configuration |
| Update | `docs/roadmap/platform.md` | Mark `PLAT-003` as completed after execution |

### Design requirements

- Use `@ConfigurationProperties(prefix = "hidra.platform")`.
- Do not hardcode environment-specific values.
- Keep properties grouped by observability, security, persistence, events, and tenancy.
- Use records or immutable nested classes where possible.
- Do not import business modules.

### Acceptance criteria

- Properties bind successfully.
- No business concepts are added.
- No module imports.
- Maven compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## PLAT-004 — Add Observability Correlation Support

### Commit message

```text
feat(platform): add observability correlation support
```

### Description

Add correlation ID and request ID technical infrastructure.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/platform/observability/correlation/CorrelationHeaders.java` | Defines standard correlation/request header names |
| Create | `src/main/java/dz/sh/hidra/platform/observability/correlation/CorrelationContext.java` | Holds current request correlation data |
| Create | `src/main/java/dz/sh/hidra/platform/observability/correlation/CorrelationIdResolver.java` | Resolves or generates correlation/request identifiers |
| Create | `src/main/java/dz/sh/hidra/platform/observability/correlation/CorrelationIdFilter.java` | Servlet filter that manages correlation/request IDs and MDC |
| Update | `docs/roadmap/platform.md` | Mark `PLAT-004` as completed after execution |

### Design requirements

- Use kernel `CorrelationId` and `RequestId`.
- Support `X-Correlation-Id` and `X-Request-Id`.
- Generate missing IDs.
- Put IDs into MDC.
- Add IDs to response headers.
- Clear context/MDC after request.
- Do not log secrets.
- Do not depend on business modules.

### Acceptance criteria

- Missing IDs are generated.
- Existing IDs are preserved after validation/normalization.
- IDs are available to logs and response headers.
- Context is cleared after request.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## PLAT-005 — Add Logging Context and Masking Support

### Commit message

```text
feat(platform): add logging context and masking support
```

### Description

Add technical logging helpers to support structured logging and sensitive value masking.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/platform/observability/logging/LoggingContext.java` | Utility for writing safe values to MDC |
| Create | `src/main/java/dz/sh/hidra/platform/observability/logging/SensitiveValueMasker.java` | Masks sensitive values before logging |
| Update | `docs/roadmap/platform.md` | Mark `PLAT-005` as completed after execution |

### Design requirements

`SensitiveValueMasker` must mask values for keys containing:

```text
password
secret
token
authorization
credential
api-key
apikey
```

`LoggingContext` may support:

```text
correlationId
requestId
actorId
module
operation
```

### Acceptance criteria

- Masking is deterministic.
- Null values are handled safely.
- No business modules are imported.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## PLAT-006 — Add Global Exception Handling Foundation

### Commit message

```text
feat(platform): add global exception handling foundation
```

### Description

Add global REST exception handling and API error response creation.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/platform/exception/PlatformException.java` | Base platform technical exception |
| Create | `src/main/java/dz/sh/hidra/platform/exception/InfrastructureException.java` | Technical infrastructure failure exception |
| Create | `src/main/java/dz/sh/hidra/platform/exception/ApiErrorFactory.java` | Converts exceptions into kernel `ApiErrorResponse` |
| Create | `src/main/java/dz/sh/hidra/platform/exception/GlobalExceptionHandler.java` | Spring REST controller advice for API errors |
| Update | `docs/roadmap/platform.md` | Mark `PLAT-006` as completed after execution |

### Design requirements

- Use kernel `ApiErrorCode`, `ApiErrorResponse`, `ValidationErrorDetail`, and `DomainException`.
- Map validation errors to `VALIDATION_ERROR`.
- Map domain exceptions to `DOMAIN_ERROR`.
- Map authentication exceptions to `AUTHENTICATION_ERROR`.
- Map authorization exceptions to `AUTHORIZATION_ERROR`.
- Map unhandled exceptions to `INTERNAL_ERROR`.
- Include correlation ID from `CorrelationContext`.
- Do not leak stack traces in response body.

### Acceptance criteria

- API errors use kernel response shape.
- Correlation ID appears in error response when available.
- No business module dependency.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## PLAT-007 — Add Security Authentication Foundation

### Commit message

```text
feat(platform): add security authentication foundation
```

### Description

Add initial Spring Security technical configuration and REST authentication error response support.

Do not implement Hidra business users, roles, or permissions here.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/platform/security/authentication/SecurityConfiguration.java` | Defines initial Spring Security filter chain |
| Create | `src/main/java/dz/sh/hidra/platform/security/authentication/RestAuthenticationEntryPoint.java` | Returns JSON error for unauthenticated requests |
| Create | `src/main/java/dz/sh/hidra/platform/security/authorization/RestAccessDeniedHandler.java` | Returns JSON error for forbidden requests |
| Update | `docs/roadmap/platform.md` | Mark `PLAT-007` as completed after execution |

### Design requirements

- Do not add OAuth2 Resource Server.
- Do not create `User`, `Role`, `Permission`, or `Authority`.
- Disable CSRF for stateless REST API if configured.
- Configure CORS using platform properties.
- Permit health/readiness endpoints as appropriate.
- Return kernel API errors for authentication/authorization failures.
- Use `ApiErrorFactory` where possible.

### Acceptance criteria

- Spring Security starts.
- Authentication errors are JSON.
- Authorization errors are JSON.
- No business security model is created.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## PLAT-008 — Add Security Context Support

### Commit message

```text
feat(platform): add security context support
```

### Description

Add technical access to the current authenticated principal and generic actor ID.

This is plumbing only. It does not evaluate business permissions.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/platform/security/context/AuthenticatedPrincipal.java` | Lightweight technical principal record |
| Create | `src/main/java/dz/sh/hidra/platform/security/context/CurrentSecurityContext.java` | Reads Spring Security context safely |
| Create | `src/main/java/dz/sh/hidra/platform/security/context/CurrentActorResolver.java` | Resolves kernel `ActorId` from current security context |
| Update | `docs/roadmap/platform.md` | Mark `PLAT-008` as completed after execution |

### Design requirements

- Use kernel `ActorId`.
- Return anonymous/system actor safely if unauthenticated.
- Do not depend on `identityaccess`.
- Do not create permission evaluation.
- Do not model employees.

### Acceptance criteria

- Unauthenticated state is handled safely.
- No identityaccess dependency.
- No business permission logic.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## PLAT-009 — Add Persistence and Auditing Foundation

### Commit message

```text
feat(platform): add persistence and auditing foundation
```

### Description

Add technical JPA, auditing, and transaction support.

Business persistence adapters remain inside business modules.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/platform/persistence/configuration/JpaConfiguration.java` | Central JPA technical configuration |
| Create | `src/main/java/dz/sh/hidra/platform/persistence/configuration/JpaAuditingConfiguration.java` | Enables JPA auditing using current actor |
| Create | `src/main/java/dz/sh/hidra/platform/persistence/transaction/TransactionConfiguration.java` | Transaction manager/support configuration |
| Create | `src/main/java/dz/sh/hidra/platform/persistence/transaction/TransactionalUseCaseExecutor.java` | Runs use-case code inside transaction boundary |
| Create | `src/main/java/dz/sh/hidra/platform/persistence/migration/MigrationMetadata.java` | Documents migration naming/metadata conventions |
| Update | `docs/roadmap/platform.md` | Mark `PLAT-009` as completed after execution |

### Design requirements

- Do not create business repositories.
- Do not create business entities.
- Use `CurrentActorResolver` for auditing if needed.
- Keep transaction helper generic.
- Do not bypass application services.

### Acceptance criteria

- Compile passes.
- No business repository/entity exists.
- Transaction helper is generic.
- No module imports.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## PLAT-010 — Add Event Serialization Foundation

### Commit message

```text
feat(platform): add event serialization foundation
```

### Description

Add serialization support for kernel `DomainEvent` objects.

Do not implement outbox persistence in this commit.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/platform/events/serialization/SerializedDomainEvent.java` | Immutable serialized event representation |
| Create | `src/main/java/dz/sh/hidra/platform/events/serialization/DomainEventSerializer.java` | Port/interface for serializing domain events |
| Create | `src/main/java/dz/sh/hidra/platform/events/serialization/JacksonDomainEventSerializer.java` | Jackson-based implementation |
| Update | `docs/roadmap/platform.md` | Mark `PLAT-010` as completed after execution |

### Design requirements

- Use kernel `DomainEvent`.
- Store event ID, event type, occurredAt, and payload.
- Do not create outbox entity yet.
- Do not define business event classes.
- Do not dispatch events yet.

### Acceptance criteria

- Domain events can be serialized.
- No outbox persistence exists yet.
- No business event classes are created.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## PLAT-011 — Add Outbox Persistence Foundation

### Commit message

```text
feat(platform): add outbox persistence foundation
```

### Description

Add platform outbox persistence for reliable domain event publication.

This is technical infrastructure. It does not own business events.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/platform/events/outbox/OutboxEventStatus.java` | Technical outbox status enum |
| Create | `src/main/java/dz/sh/hidra/platform/events/outbox/OutboxEventEntity.java` | JPA entity for outbox event storage |
| Create | `src/main/java/dz/sh/hidra/platform/events/outbox/OutboxEventRepository.java` | Spring Data repository for outbox events |
| Create | `src/main/java/dz/sh/hidra/platform/events/outbox/OutboxEventStore.java` | Stores serialized events in outbox table |
| Create | `src/main/resources/db/migration/V001__create_platform_outbox_event.sql` | Flyway migration for platform outbox table |
| Update | `docs/roadmap/platform.md` | Mark `PLAT-011` as completed after execution |

### Design requirements

Outbox table should include at least:

```text
id
event_id
event_type
aggregate_id
aggregate_type
payload
occurred_at
status
retry_count
last_error
created_at
published_at
```

Rules:

- Use JPA only inside platform outbox infrastructure.
- Do not store business-specific columns.
- Payload should be text/json-compatible.
- Do not dispatch events in this commit.

### Acceptance criteria

- Migration file exists.
- Entity maps to migration.
- Repository is technical only.
- No business event meaning is added.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
mvn -q flyway:validate
```

If Flyway validation cannot run due to missing database connection, record the exact reason.

---

## PLAT-012 — Add Domain Event Publisher Foundation

### Commit message

```text
feat(platform): add domain event publisher foundation
```

### Description

Add infrastructure for publishing domain events into the outbox.

This commit does not integrate Kafka, RabbitMQ, or external brokers.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/platform/events/dispatcher/DomainEventPublisher.java` | Interface for publishing domain events |
| Create | `src/main/java/dz/sh/hidra/platform/events/dispatcher/OutboxDomainEventPublisher.java` | Outbox-backed domain event publisher |
| Update | `docs/roadmap/platform.md` | Mark `PLAT-012` as completed after execution |

### Design requirements

- Use kernel `DomainEvent`.
- Use `DomainEventSerializer`.
- Use `OutboxEventStore`.
- Do not add broker integration.
- Do not dispatch asynchronously unless explicitly configured later.
- Do not define business event types.

### Acceptance criteria

- Domain events can be handed to publisher.
- Publisher writes to outbox.
- No external broker dependency.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## PLAT-013 — Add Health Indicators and Metrics Tags

### Commit message

```text
feat(platform): add health indicators and metrics tags
```

### Description

Add health/readiness indicators and standard metrics tags for platform components.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/platform/observability/health/DatabaseReadinessHealthIndicator.java` | Reports database readiness |
| Create | `src/main/java/dz/sh/hidra/platform/observability/health/OutboxReadinessHealthIndicator.java` | Reports outbox infrastructure readiness |
| Create | `src/main/java/dz/sh/hidra/platform/observability/metrics/PlatformMetricsTags.java` | Standard metric tags for application/environment/module |
| Create | `src/main/java/dz/sh/hidra/platform/observability/metrics/PlatformMetricsConfiguration.java` | Registers metric tag conventions |
| Update | `docs/roadmap/platform.md` | Mark `PLAT-013` as completed after execution |

### Design requirements

- Use Spring Boot Actuator health APIs.
- Use Micrometer where needed.
- Do not expose sensitive details in production.
- Do not include business KPIs.
- Business KPIs belong to analytics later.

### Acceptance criteria

- Health indicators compile.
- Metrics config compiles.
- No business metrics are created.
- No business module imports.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## PLAT-014 — Add Tenancy Context Foundation

### Commit message

```text
feat(platform): add tenancy context foundation
```

### Description

Add minimal future-ready tenant and organization-scope context holders.

Do not implement full multi-tenancy.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/main/java/dz/sh/hidra/platform/tenancy/TenantContext.java` | Holds current technical tenant identifier if enabled later |
| Create | `src/main/java/dz/sh/hidra/platform/tenancy/OrganizationScopeContext.java` | Holds current organization/operational scope using kernel value object |
| Update | `docs/roadmap/platform.md` | Mark `PLAT-014` as completed after execution |

### Design requirements

- Keep contexts simple.
- Clear ThreadLocal state after use.
- Use kernel `OrganizationScopeId`.
- Do not implement tenant database routing.
- Do not implement business authorization.

### Acceptance criteria

- Contexts are safe to clear.
- No business module imports.
- No multi-tenant routing added.
- Compile passes.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## PLAT-015 — Add Platform Unit and Slice Tests

### Commit message

```text
test(platform): add platform unit and slice tests
```

### Description

Add focused tests for platform components.

Use Spring test support only when the component requires Spring context.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/test/java/dz/sh/hidra/platform/configuration/HidraPlatformPropertiesTest.java` | Verifies platform properties binding |
| Create | `src/test/java/dz/sh/hidra/platform/exception/ApiErrorFactoryTest.java` | Verifies error response creation |
| Create | `src/test/java/dz/sh/hidra/platform/exception/GlobalExceptionHandlerTest.java` | Verifies exception mapping behavior |
| Create | `src/test/java/dz/sh/hidra/platform/observability/correlation/CorrelationContextTest.java` | Verifies correlation context lifecycle |
| Create | `src/test/java/dz/sh/hidra/platform/observability/correlation/CorrelationIdResolverTest.java` | Verifies incoming/generated ID resolution |
| Create | `src/test/java/dz/sh/hidra/platform/observability/correlation/CorrelationIdFilterTest.java` | Verifies request/response header behavior |
| Create | `src/test/java/dz/sh/hidra/platform/observability/logging/SensitiveValueMaskerTest.java` | Verifies secret masking |
| Create | `src/test/java/dz/sh/hidra/platform/security/authentication/SecurityConfigurationTest.java` | Verifies basic security configuration loads |
| Create | `src/test/java/dz/sh/hidra/platform/security/context/CurrentSecurityContextTest.java` | Verifies security context behavior |
| Create | `src/test/java/dz/sh/hidra/platform/persistence/transaction/TransactionalUseCaseExecutorTest.java` | Verifies transaction helper behavior |
| Create | `src/test/java/dz/sh/hidra/platform/events/serialization/JacksonDomainEventSerializerTest.java` | Verifies domain event serialization |
| Create | `src/test/java/dz/sh/hidra/platform/events/outbox/OutboxEventStoreTest.java` | Verifies outbox event persistence/store behavior |
| Create | `src/test/java/dz/sh/hidra/platform/tenancy/TenantContextTest.java` | Verifies tenant context lifecycle |
| Create | `src/test/java/dz/sh/hidra/platform/tenancy/OrganizationScopeContextTest.java` | Verifies organization scope context lifecycle |
| Update | `docs/roadmap/platform.md` | Mark `PLAT-015` as completed after execution |

### Test requirements

- Use JUnit 5.
- Use AssertJ if available.
- Use Spring context only for Spring configuration tests.
- Use Testcontainers only if required for persistence tests and available.
- If persistence test cannot run due to database setup, mark exact limitation.

### Acceptance criteria

- Tests cover important platform behavior.
- Tests do not introduce business concepts.
- Tests do not require external services except optional Testcontainers.
- `mvn test` passes or limitations are recorded.

### Validation

```bash
mvn -q test
```

---

## PLAT-016 — Add Platform Architecture Guardrail

### Commit message

```text
test(platform): add platform architecture guardrail
```

### Description

Add an ArchUnit test protecting platform boundaries.

Only do this if ArchUnit exists in `pom.xml`.

If ArchUnit is missing:

```text
Do not add PlatformArchitectureTest.
Mark PLAT-016 as Blocked.
Record missing dependency in this roadmap.
Stop and ask for dependency approval.
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `src/test/java/dz/sh/hidra/platform/PlatformArchitectureTest.java` | Ensures platform does not depend on business aggregates or forbidden packages |
| Update | `docs/roadmap/platform.md` | Mark `PLAT-016` as completed or blocked |

### Required architecture rules

The test must assert:

```text
platform may depend on kernel
platform may depend on Spring/JPA/Hibernate/Micrometer/Flyway
platform must not depend on modules.*.domain.model
platform must not contain User/Role/Permission/Employee/Pipeline/FlowReading/Incident aggregate classes
platform must not create shared/sharedkernel/common/core/utils packages
```

### Acceptance criteria

- Architecture test passes if ArchUnit exists.
- Test does not require Spring context.
- Test fails if platform imports business aggregate packages.
- If ArchUnit is missing, roadmap marks task as blocked.

### Validation

```bash
mvn -q test -Dtest=PlatformArchitectureTest
mvn -q test
```

---

## PLAT-017 — Finalize Platform Checklist

### Commit message

```text
docs(platform): finalize platform checklist
```

### Description

Update this roadmap with final execution status, validation results, and remaining risks.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Update | `docs/roadmap/platform.md` | Record final execution status and checklist |

### Required final checklist

```text
[ ] Platform package structure exists
[ ] Platform depends on kernel, not business modules
[ ] Platform contains no business aggregates
[ ] Platform contains no business authorization meaning
[ ] Correlation/request tracing works
[ ] Global exception handling returns kernel ApiErrorResponse
[ ] Security foundation compiles
[ ] Persistence configuration compiles
[ ] Outbox migration exists
[ ] Outbox infrastructure compiles
[ ] Health/metrics infrastructure compiles
[ ] Tenancy context is minimal and clearable
[ ] Platform unit tests pass
[ ] Platform architecture guardrail passes or is explicitly blocked with reason
[ ] mvn -q clean verify passes
```

### Validation

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
```

---

## 12. File Purpose Matrix

### 12.1 Package files

| File | Purpose |
|---|---|
| `platform/package-info.java` | Documents root platform boundary |
| `platform/configuration/package-info.java` | Documents configuration boundary |
| `platform/exception/package-info.java` | Documents exception handling boundary |
| `platform/observability/package-info.java` | Documents observability boundary |
| `platform/observability/correlation/package-info.java` | Documents correlation tracing boundary |
| `platform/observability/health/package-info.java` | Documents health indicator boundary |
| `platform/observability/logging/package-info.java` | Documents logging boundary |
| `platform/observability/metrics/package-info.java` | Documents metrics boundary |
| `platform/security/package-info.java` | Documents security plumbing boundary |
| `platform/security/authentication/package-info.java` | Documents authentication plumbing boundary |
| `platform/security/authorization/package-info.java` | Documents authorization integration boundary |
| `platform/security/context/package-info.java` | Documents security context boundary |
| `platform/persistence/package-info.java` | Documents persistence infrastructure boundary |
| `platform/persistence/configuration/package-info.java` | Documents persistence configuration boundary |
| `platform/persistence/migration/package-info.java` | Documents migration metadata boundary |
| `platform/persistence/transaction/package-info.java` | Documents transaction support boundary |
| `platform/events/package-info.java` | Documents event infrastructure boundary |
| `platform/events/dispatcher/package-info.java` | Documents event dispatcher boundary |
| `platform/events/outbox/package-info.java` | Documents outbox boundary |
| `platform/events/serialization/package-info.java` | Documents event serialization boundary |
| `platform/tenancy/package-info.java` | Documents tenancy context boundary |

### 12.2 Production files

| File | Purpose |
|---|---|
| `HidraPlatformProperties.java` | Typed access to `hidra.platform.*` properties |
| `PlatformConfiguration.java` | Enables platform configuration properties |
| `ClockConfiguration.java` | Provides UTC `Clock` bean |
| `JacksonConfiguration.java` | Provides JSON customization point |
| `OpenApiConfiguration.java` | Provides OpenAPI metadata |
| `PlatformException.java` | Base technical platform exception |
| `InfrastructureException.java` | Technical infrastructure exception |
| `ApiErrorFactory.java` | Builds kernel API error responses |
| `GlobalExceptionHandler.java` | Handles REST exceptions globally |
| `CorrelationHeaders.java` | Standard header constants |
| `CorrelationContext.java` | Current correlation/request context |
| `CorrelationIdResolver.java` | Resolves or generates correlation/request IDs |
| `CorrelationIdFilter.java` | Adds correlation/request tracing to HTTP requests |
| `LoggingContext.java` | Safe MDC helper |
| `SensitiveValueMasker.java` | Masks secrets for logs |
| `RestAuthenticationEntryPoint.java` | JSON unauthenticated response |
| `SecurityConfiguration.java` | Technical Spring Security configuration |
| `RestAccessDeniedHandler.java` | JSON forbidden response |
| `AuthenticatedPrincipal.java` | Technical current principal representation |
| `CurrentSecurityContext.java` | Reads Spring Security context safely |
| `CurrentActorResolver.java` | Resolves kernel actor ID |
| `JpaConfiguration.java` | Technical JPA configuration |
| `JpaAuditingConfiguration.java` | JPA auditing support |
| `MigrationMetadata.java` | Flyway migration metadata conventions |
| `TransactionConfiguration.java` | Transaction configuration |
| `TransactionalUseCaseExecutor.java` | Generic transaction boundary helper |
| `SerializedDomainEvent.java` | Serialized domain event representation |
| `DomainEventSerializer.java` | Domain event serialization contract |
| `JacksonDomainEventSerializer.java` | Jackson event serializer |
| `OutboxEventStatus.java` | Outbox status enum |
| `OutboxEventEntity.java` | JPA outbox entity |
| `OutboxEventRepository.java` | Outbox Spring Data repository |
| `OutboxEventStore.java` | Stores serialized events |
| `DomainEventPublisher.java` | Domain event publication contract |
| `OutboxDomainEventPublisher.java` | Outbox-backed publisher |
| `DatabaseReadinessHealthIndicator.java` | Database readiness check |
| `OutboxReadinessHealthIndicator.java` | Outbox readiness check |
| `PlatformMetricsTags.java` | Standard platform metric tags |
| `PlatformMetricsConfiguration.java` | Metric tag configuration |
| `TenantContext.java` | Future tenant context |
| `OrganizationScopeContext.java` | Organization scope context |

---

## 13. AI Agent Execution Rules

Any AI agent executing this roadmap must follow these rules:

1. Execute exactly one commit code at a time.
2. Do not batch commits.
3. Read `AGENTS.md` before starting.
4. Read `docs/roadmap/platform.md` before starting.
5. Check kernel preconditions before implementation commits.
6. Do not create business module code.
7. Do not create business aggregate classes in platform.
8. Do not introduce business authorization meaning in platform.
9. Always use the canonical HidraAPI header.
10. Never change `@Author`.
11. Never change `@CreatedOn`.
12. Update this roadmap after each completed or blocked commit.
13. Run validation after each commit.
14. If validation cannot run, record the exact reason.
15. If a dependency is missing, stop and report it.
16. If a requested file does not belong to platform, do not create it.

---

## 14. Current Status Table

| Commit code | Status | Notes |
|---|---|---|
| `PLAT-001` | Planned | Add this roadmap |
| `PLAT-002` | Completed | Package skeleton created; `find src/main/java/dz/sh/hidra/platform -type f | sort` listed production package-info files; `mvn -q -DskipTests compile` passed |
| `PLAT-003` | Completed | Typed platform properties and base configuration created; `mvn -q -DskipTests compile` passed |
| `PLAT-004` | Completed | Correlation/request ID infrastructure created; `mvn -q -DskipTests compile` passed |
| `PLAT-005` | Completed | Logging context and sensitive value masking support created; `mvn -q -DskipTests compile` passed |
| `PLAT-003` | Planned | Add typed platform properties and configuration |
| `PLAT-004` | Planned | Add correlation support |
| `PLAT-005` | Planned | Add logging context and masking |
| `PLAT-006` | Planned | Add global exception handling |
| `PLAT-007` | Planned | Add security authentication foundation |
| `PLAT-008` | Planned | Add security context support |
| `PLAT-009` | Planned | Add persistence and auditing foundation |
| `PLAT-010` | Planned | Add event serialization foundation |
| `PLAT-011` | Planned | Add outbox persistence foundation |
| `PLAT-012` | Planned | Add domain event publisher foundation |
| `PLAT-013` | Planned | Add health indicators and metrics tags |
| `PLAT-014` | Planned | Add tenancy context foundation |
| `PLAT-015` | Planned | Add platform unit and slice tests |
| `PLAT-016` | Planned | Add platform architecture guardrail if ArchUnit exists |
| `PLAT-017` | Planned | Finalize checklist |

---

## 15. Next Action

Start with:

```text
PLAT-001 — docs(platform): add platform roadmap
```

Then execute:

```text
PLAT-002 — chore(platform): add platform package skeleton
```

Do not implement platform classes before the package skeleton is reviewed.
