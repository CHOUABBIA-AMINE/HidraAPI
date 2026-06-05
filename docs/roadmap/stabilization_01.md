# HidraAPI Stabilization Roadmap 01

```text
Roadmap file : docs/roadmap/stabilization_01.md
Roadmap code : STB-01
Scope        : Stabilize the current HidraAPI foundation before starting topology or any new business module
Repository   : HidraAPI
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-05-30
```

---

## 1. Purpose

This roadmap stabilizes the currently generated HidraAPI foundation before adding new modules.

The current foundation includes:

```text
kernel
platform
identity
organization
bootstrap
```

The application has already reached successful startup locally after ad-hoc fixes for:

```text
REST mapper beans
platform ObjectMapper bean
JPA entity constructor visibility
database schema availability
```

However, those fixes must be converted into explicit repository tasks so another AI agent can apply, verify, and commit them safely.

This roadmap must be completed before starting:

```text
topology
measurement
operations
flow
risk
analytics
workflow
reporting
notification
```

---

## 2. Stabilization principles

The AI agent must follow these rules for every task in this roadmap:

```text
1. Read AGENTS.md first.
2. Read docs/roadmap/kernel.md to understand kernel constraints.
3. Read docs/roadmap/platform.md to understand platform constraints.
4. Read docs/roadmap/identity.md to understand identity boundaries.
5. Read docs/roadmap/organization.md to understand organization boundaries.
6. Do not create new business modules.
7. Do not create topology, measurement, operations, flow, risk, analytics, workflow, reporting, or notification packages.
8. Do not recreate identityaccess.
9. Do not create shared, sharedkernel, common, core, utils, helper, helpers, or misc packages.
10. Do not rename packages.
11. Do not change package ownership rules.
12. Use the canonical HidraAPI Java header for every Java file created or updated.
13. Keep @Author as Abir MEDJERAB.
14. Keep @CreatedOn as 2025-06-26.
15. Use @UpdatedOn as 2026-05-30 unless the repository already uses a later stabilization date.
16. Keep changes small and task-scoped.
17. Run the validation commands specified in each task.
18. Update the status table in this roadmap after each task.
19. Commit only the files listed by the current task.
20. Do not execute later tasks unless explicitly requested.
```

---

## 3. Known current issues to stabilize

The current application can boot locally, but the following items must be fixed or confirmed in repository form:

```text
1. Missing or incomplete Flyway migrations for identity and organization.
2. REST mapper classes are plain classes and require Spring beans.
3. Platform ObjectMapper bean is required by JacksonDomainEventSerializer.
4. Organization JPA entity no-arg constructors must be visible to the persistence mapper.
5. Organization controllers must fully satisfy OpenAPI documentation requirements, including @ApiResponses.
6. Organization HTTP 501 placeholders must be explicitly documented and isolated.
7. ReportingLinePolicy must explicitly prevent self-reporting.
8. Dev security must be explicit instead of relying on generated Spring Security password.
9. Dev bootstrap/health/Swagger endpoint access must be confirmed.
10. Full compile, test, and clean verify must pass or produce exact documented blockers.
```

---

## 4. Commit plan

| Commit code | Commit message | Status | Description |
|---|---|---:|---|
| `STB-001` | `docs(stabilization): add foundation stabilization roadmap` | Planned | Add this roadmap file. |
| `STB-002` | `fix(organization): expose jpa entity constructors to persistence mapper` | Planned | Make organization JPA no-arg constructors visible to the persistence mapper. |
| `STB-003` | `fix(api): register rest mapper beans` | Planned | Register identity and organization REST mapper beans. |
| `STB-004` | `fix(platform): register jackson object mapper bean` | Planned | Register a shared ObjectMapper bean for platform serialization. |
| `STB-005` | `db(identity): add identity flyway migration` | Planned | Add or confirm identity database migration. |
| `STB-006` | `db(organization): confirm organization flyway migration` | Planned | Confirm organization database migration is present and aligned with entities. |
| `STB-007` | `docs(api): complete organization controller openapi responses` | Planned | Add missing @ApiResponses to organization controllers. |
| `STB-008` | `fix(organization): enforce reporting line self-reporting rule` | Planned | Ensure reporting line policy/model explicitly rejects self-reporting. |
| `STB-009` | `chore(security): add explicit development security baseline` | Planned | Stop relying on generated Spring Security password for dev. |
| `STB-010` | `test(stabilization): verify application boot baseline` | Planned | Add or update boot smoke validation. |
| `STB-011` | `docs(stabilization): finalize foundation validation checklist` | Planned | Record final compile/test/boot results and remaining risks. |

---

# STB-001 — Add foundation stabilization roadmap

```text
Commit code    : STB-001
Commit message : docs(stabilization): add foundation stabilization roadmap
Type           : Documentation
Layer          : Documentation
Module         : stabilization
```

## Description

Create the stabilization roadmap used by Codex or another AI agent to complete the boot baseline before new module development starts.

## Files to create

| File | Purpose |
|---|---|
| `docs/roadmap/stabilization_01.md` | Defines stabilization tasks, file scopes, validation commands, boundaries, and completion criteria. |

## Files to update

None.

## Rules

```text
- Create only docs/roadmap/stabilization_01.md.
- Do not update production code.
- Do not update test code.
- Do not update migrations.
- Do not update existing roadmap files.
- Do not create new module packages.
```

## Validation commands

```bash
test -f docs/roadmap/stabilization_01.md
```

On Windows PowerShell:

```powershell
Test-Path docs/roadmap/stabilization_01.md
```

## Completion criteria

```text
docs/roadmap/stabilization_01.md exists
the roadmap contains commit code, message, description, files to create/update, and purpose of each file
```

---

# STB-002 — Expose organization JPA entity constructors to persistence mapper

```text
Commit code    : STB-002
Commit message : fix(organization): expose jpa entity constructors to persistence mapper
Type           : Fix
Layer          : Infrastructure
Module         : organization
```

## Description

Fix the constructor visibility error where `OrganizationPersistenceMapper` cannot instantiate organization JPA entities because the entity no-arg constructors are not visible from the mapper package.

The error appears as:

```text
The constructor OrganizationUnitJpaEntity() is not visible
The constructor EmployeeJpaEntity() is not visible
The constructor PositionJpaEntity() is not visible
The constructor EmployeeAssignmentJpaEntity() is not visible
The constructor ReportingLineJpaEntity() is not visible
```

## Root cause

`OrganizationPersistenceMapper` is in:

```text
dz.sh.hidra.modules.organization.infrastructure.persistence.mapper
```

The JPA entities are in:

```text
dz.sh.hidra.modules.organization.infrastructure.persistence.entity
```

A `protected` no-arg constructor is not visible from a different package unless called through inheritance. The mapper directly calls `new EntityName()`, so the no-arg constructors must be public, package placement must change, or factory methods must be added.

For stabilization, use the smallest fix:

```text
make the no-arg constructors public
```

## Files to update

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeJpaEntity.java` | Make `EmployeeJpaEntity()` public so `OrganizationPersistenceMapper` can instantiate it. |
| `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitJpaEntity.java` | Make `OrganizationUnitJpaEntity()` public so `OrganizationPersistenceMapper` can instantiate it. |
| `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/PositionJpaEntity.java` | Make `PositionJpaEntity()` public so `OrganizationPersistenceMapper` can instantiate it. |
| `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java` | Make `EmployeeAssignmentJpaEntity()` public so `OrganizationPersistenceMapper` can instantiate it. |
| `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ReportingLineJpaEntity.java` | Make `ReportingLineJpaEntity()` public so `OrganizationPersistenceMapper` can instantiate it. |

## Files to create

None.

## Rules

```text
- Do not move the mapper.
- Do not move entity classes.
- Do not add Lombok.
- Do not change table names.
- Do not change column names.
- Do not change business mapping logic.
- Do not change repositories.
- Do not change migrations in this task.
```

## Validation commands

```bash
mvn -q -DskipTests compile
```

## Completion criteria

```text
mvn -q -DskipTests compile passes or fails only on an unrelated documented issue
no protected no-arg constructors remain for the listed organization JPA entities
no table/column/mapping logic changed
```

---

# STB-003 — Register REST mapper beans

```text
Commit code    : STB-003
Commit message : fix(api): register rest mapper beans
Type           : Fix
Layer          : API
Module         : identity, organization
```

## Description

Fix Spring startup errors caused by REST mapper classes being plain Java classes without Spring bean registration.

Observed failure:

```text
Parameter 1 of constructor in dz.sh.hidra.modules.identity.api.rest.controller.IdentityPermissionController
required a bean of type 'dz.sh.hidra.modules.identity.api.rest.mapper.IdentityRestMapper'
that could not be found.
```

Organization has the same risk because organization controllers also depend on `OrganizationRestMapper`.

## Files to create

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/identity/api/rest/configuration/IdentityApiRestConfiguration.java` | Provides `IdentityRestMapper` as a Spring bean for identity REST controllers. |
| `src/main/java/dz/sh/hidra/modules/organization/api/rest/configuration/OrganizationApiRestConfiguration.java` | Provides `OrganizationRestMapper` as a Spring bean for organization REST controllers. |

## Files to update

None.

## Required implementation

`IdentityApiRestConfiguration` must:

```text
- be in package dz.sh.hidra.modules.identity.api.rest.configuration
- be annotated with @Configuration(proxyBeanMethods = false)
- expose @Bean @ConditionalOnMissingBean IdentityRestMapper identityRestMapper()
- instantiate new IdentityRestMapper()
- not configure repositories, services, security, persistence, or platform infrastructure
```

`OrganizationApiRestConfiguration` must:

```text
- be in package dz.sh.hidra.modules.organization.api.rest.configuration
- be annotated with @Configuration(proxyBeanMethods = false)
- expose @Bean @ConditionalOnMissingBean OrganizationRestMapper organizationRestMapper()
- instantiate new OrganizationRestMapper()
- not configure repositories, services, security, persistence, identity implementation, topology implementation, or platform infrastructure
```

## Rules

```text
- Do not annotate mapper classes with @Component in this task.
- Do not change mapper logic.
- Do not change controllers.
- Do not change application services.
- Do not create identityaccess.
- Do not create topology files.
```

## Validation commands

```bash
mvn -q -DskipTests compile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Completion criteria

```text
Spring no longer fails because IdentityRestMapper bean is missing
Spring no longer fails because OrganizationRestMapper bean is missing
```

---

# STB-004 — Register platform Jackson ObjectMapper bean

```text
Commit code    : STB-004
Commit message : fix(platform): register jackson object mapper bean
Type           : Fix
Layer          : Platform
Module         : platform
```

## Description

Fix Spring startup error caused by `JacksonDomainEventSerializer` requiring an `ObjectMapper` bean.

Observed failure:

```text
Parameter 0 of constructor in dz.sh.hidra.platform.events.serialization.JacksonDomainEventSerializer
required a bean of type 'com.fasterxml.jackson.databind.ObjectMapper' that could not be found.
```

## Files to create

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/platform/configuration/PlatformJacksonConfiguration.java` | Provides the shared Jackson `ObjectMapper` bean for platform infrastructure, especially domain event serialization. |

## Files to update

None.

## Required implementation

`PlatformJacksonConfiguration` must:

```text
- be in package dz.sh.hidra.platform.configuration
- be annotated with @Configuration(proxyBeanMethods = false)
- expose @Bean @ConditionalOnMissingBean(ObjectMapper.class)
- return ObjectMapper configured with findAndRegisterModules()
- disable SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
- disable DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
```

## Rules

```text
- Do not modify JacksonDomainEventSerializer.
- Do not place ObjectMapper configuration in identity or organization.
- Do not create a business module dependency from platform to identity or organization.
- Do not change domain event payload semantics.
```

## Validation commands

```bash
mvn -q -DskipTests compile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Completion criteria

```text
Spring no longer fails because ObjectMapper bean is missing
JacksonDomainEventSerializer receives ObjectMapper by constructor injection
Application still starts with dev profile
```

---

# STB-005 — Add identity Flyway migration

```text
Commit code    : STB-005
Commit message : db(identity): add identity flyway migration
Type           : Database
Layer          : Infrastructure
Module         : identity
```

## Description

Add or confirm the Flyway migration that creates identity tables required by identity JPA entities.

Earlier boot failed with:

```text
Schema validation: missing table [hidra_identity_permission]
```

This means Hibernate scans identity JPA entities while the database only has the platform outbox migration.

## Files to create

| File | Purpose |
|---|---|
| `src/main/resources/db/migration/V010__create_identity_tables.sql` | Creates identity tables, constraints, indexes, and join tables required by identity persistence. |

## Files to update

None, unless `V010__create_identity_tables.sql` already exists and is incomplete.

## Tables to confirm/create

The migration must create all identity tables required by the current identity JPA entities.

At minimum, confirm the exact `@Table` names from identity entities and include:

```text
hidra_identity_user
hidra_identity_role
hidra_identity_permission
hidra_identity_user_role
hidra_identity_role_permission
```

If the actual entity table names differ, the migration must follow the entity names exactly.

## Required database design

The migration must include:

```text
- primary keys
- unique constraints for stable business identifiers
- foreign keys for user-role and role-permission join tables
- created/updated timestamp columns if entities map them
- boolean active/enabled columns if entities map them
- indexes for frequent lookups
```

## Rules

```text
- Inspect identity JPA entity mappings before writing SQL.
- Do not guess column names.
- Match entity @Column names exactly.
- Do not create organization tables in this migration.
- Do not create platform tables in this migration.
- Do not create topology tables.
- Do not rename existing V001 migration.
- Do not use Hibernate ddl-auto=update as the permanent fix.
```

## Validation commands

```bash
mvn -q -DskipTests compile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Recommended DB checks:

```sql
SELECT installed_rank, version, description, success
FROM flyway_schema_history
ORDER BY installed_rank;

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
ORDER BY table_name;
```

## Completion criteria

```text
Flyway applies V010 successfully on a clean dev database
hidra_identity_permission exists
all identity entity tables exist
Hibernate no longer fails on missing identity tables
```

---

# STB-006 — Confirm organization Flyway migration

```text
Commit code    : STB-006
Commit message : db(organization): confirm organization flyway migration
Type           : Database
Layer          : Infrastructure
Module         : organization
```

## Description

Confirm that the organization Flyway migration exists and matches current organization JPA entities.

The organization module previously generated a migration named:

```text
V020__create_organization_tables.sql
```

This migration must be present under:

```text
src/main/resources/db/migration
```

and must align with organization JPA entity mappings.

## Files to create

| File | Purpose |
|---|---|
| `src/main/resources/db/migration/V020__create_organization_tables.sql` | Creates organization tables, constraints, indexes, and relationships if missing. |

## Files to update

| File | Purpose |
|---|---|
| `src/main/resources/db/migration/V020__create_organization_tables.sql` | Fix the migration if present but inconsistent with current organization JPA entities. |

Only one of the above applies depending on repository state.

## Tables to confirm/create

Confirm exact table names from organization JPA entities. Expected names include:

```text
hidra_org_employee
hidra_org_position
hidra_org_organization_unit
hidra_org_employee_assignment
hidra_org_reporting_line
```

If actual entity table names differ, follow the entity mappings exactly.

## Required database design

The migration must include:

```text
- primary keys
- unique constraints for employee number, organization unit code, and position code
- foreign keys for assignments and reporting lines
- columns for station-as-organization-unit neutral operational scope reference
- indexes for organization unit type, status, parent, operational scope, employee assignment, and reporting line lookups
```

## Rules

```text
- Inspect organization JPA entity mappings before writing SQL.
- Do not guess column names.
- Match entity @Column names exactly.
- Do not create identity tables in this migration.
- Do not create topology tables.
- Do not create physical station asset tables here.
- Keep station-as-organization-unit in organization.
- Keep physical station assets for future topology.
```

## Validation commands

```bash
mvn -q -DskipTests compile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Recommended DB checks:

```sql
SELECT installed_rank, version, description, success
FROM flyway_schema_history
ORDER BY installed_rank;

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
ORDER BY table_name;
```

## Completion criteria

```text
Flyway applies V020 successfully on a clean dev database
all organization entity tables exist
Hibernate no longer fails on missing organization tables
application starts with dev profile
```

---

# STB-007 — Complete organization controller OpenAPI responses

```text
Commit code    : STB-007
Commit message : docs(api): complete organization controller openapi responses
Type           : Documentation/Fix
Layer          : API
Module         : organization
```

## Description

Complete the organization controller OpenAPI documentation constraint.

The organization roadmap required controller documentation. Generated controllers included `@Tag` and `@Operation`, but the final ORG checklist recorded that controllers still need `@ApiResponses`.

## Files to update

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/organization/api/rest/controller/EmployeeController.java` | Add `@ApiResponses` to every endpoint method. |
| `src/main/java/dz/sh/hidra/modules/organization/api/rest/controller/OrganizationUnitController.java` | Add `@ApiResponses` to every endpoint method. |
| `src/main/java/dz/sh/hidra/modules/organization/api/rest/controller/PositionController.java` | Add `@ApiResponses` to every endpoint method. |
| `src/main/java/dz/sh/hidra/modules/organization/api/rest/controller/ReportingLineController.java` | Add `@ApiResponses` to every endpoint method. |

## Files to create

None.

## Required implementation

For each endpoint, add appropriate OpenAPI responses such as:

```text
201 Created
200 OK
204 No Content if applicable
400 Bad Request
404 Not Found where applicable
409 Conflict where applicable
501 Not Implemented for placeholder endpoints
500 Internal Server Error
```

Use:

```text
io.swagger.v3.oas.annotations.responses.ApiResponse
io.swagger.v3.oas.annotations.responses.ApiResponses
```

## Rules

```text
- Do not change endpoint paths.
- Do not change request DTOs.
- Do not change response DTOs.
- Do not change controller behavior except documentation annotations.
- Do not implement HTTP 501 placeholders in this task.
- Do not access repositories from controllers.
- Controllers must still depend only on application inbound ports and REST mapper.
```

## Validation commands

```bash
mvn -q -DskipTests compile
mvn -q test -Dtest='*ControllerTest,*RestMapperTest'
```

## Completion criteria

```text
all organization controller methods have @Operation and @ApiResponses
Swagger/OpenAPI generation still works
API tests pass or exact unrelated blockers are documented
```

---

# STB-008 — Enforce reporting line self-reporting rule

```text
Commit code    : STB-008
Commit message : fix(organization): enforce reporting line self-reporting rule
Type           : Fix
Layer          : Domain
Module         : organization
```

## Description

Ensure the organization domain explicitly prevents an employee from reporting to themselves.

The final ORG checklist recorded this risk:

```text
ReportingLinePolicy should be reviewed for explicit self-reporting protection.
```

This stabilization task must confirm whether the rule already exists in `ReportingLine`, `ReportingLinePolicy`, or `ReportingLineDomainService`. If missing, implement it in the domain policy/service path.

## Files to inspect

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/organization/domain/model/ReportingLine.java` | Check whether self-reporting is rejected at model construction. |
| `src/main/java/dz/sh/hidra/modules/organization/domain/policy/ReportingLinePolicy.java` | Check whether policy explicitly rejects self-reporting. |
| `src/main/java/dz/sh/hidra/modules/organization/domain/service/ReportingLineDomainService.java` | Check whether service calls the policy for self-reporting checks. |

## Files to update

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/organization/domain/policy/ReportingLinePolicy.java` | Add explicit self-reporting validation if missing. |
| `src/main/java/dz/sh/hidra/modules/organization/domain/service/ReportingLineDomainService.java` | Ensure the policy is called during reporting line creation if missing. |
| `src/test/java/dz/sh/hidra/modules/organization/domain/policy/ReportingLinePolicyTest.java` | Add a test that self-reporting is rejected. |
| `src/test/java/dz/sh/hidra/modules/organization/domain/model/EmployeeTest.java` | Add or adjust aggregate test if self-reporting is enforced through aggregate/model behavior. |

Update only the files required by the existing implementation shape.

## Files to create

None, unless the listed test file does not exist.

## Required rule

The domain must reject:

```text
employeeId == managerEmployeeId
```

with a domain/business exception.

Preferred exception:

```text
ReportingLineException
```

unless the existing domain model already uses another organization exception consistently.

## Rules

```text
- Do not create SupervisorAssignment.
- Do not introduce identity or topology dependencies.
- Do not place this rule in the controller.
- Do not place this rule in a JPA entity.
- Keep the rule in domain model/policy/service.
```

## Validation commands

```bash
mvn -q test -Dtest='ReportingLinePolicyTest,EmployeeTest'
mvn -q -DskipTests compile
```

## Completion criteria

```text
self-reporting is explicitly rejected
a test proves the rejection
domain still has no Spring/JPA/API dependency
```

---

# STB-009 — Add explicit development security baseline

```text
Commit code    : STB-009
Commit message : chore(security): add explicit development security baseline
Type           : Configuration
Layer          : Platform
Module         : platform
```

## Description

The app currently starts with Spring Security's generated development password:

```text
Using generated security password: <generated-password>
```

This is acceptable for a temporary boot check but not a stable dev baseline.

Create an explicit dev-only security configuration or properties setup that allows predictable local development access without weakening production security.

## Files to inspect

| File | Purpose |
|---|---|
| `src/main/resources/application.properties` | Confirm common security and management settings. |
| `src/main/resources/application-dev.properties` | Confirm dev profile configuration. |
| `src/main/java/dz/sh/hidra/platform/security/**` | Check whether platform security configuration already exists. |

## Files to create or update

Choose the smallest approach aligned with existing platform architecture.

Preferred option:

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/platform/security/configuration/PlatformDevSecurityConfiguration.java` | Dev-profile-only security filter chain permitting health, Swagger, and API docs while keeping a clear dev-only boundary. |

Alternative if project already has central security config:

| File | Purpose |
|---|---|
| existing platform security configuration file | Add dev profile behavior without duplicating security filter chains. |

## Required dev behavior

For profile `dev`, permit unauthenticated access to:

```text
/actuator/health
/actuator/info
/swagger-ui/**
/swagger-ui.html
/v3/api-docs
/v3/api-docs/**
```

Decide explicitly whether business APIs should be:

```text
A. authenticated with a known dev user
B. temporarily permitted for local development only
```

Recommended for this phase:

```text
Permit documentation and health only.
Keep business APIs protected.
```

## Rules

```text
- Do not disable security globally.
- Do not weaken production security.
- Add @Profile("dev") if creating dev-only config.
- Do not hardcode production credentials.
- Do not implement JWT/OAuth in this stabilization task unless it already exists.
- Do not modify identity business logic.
```

## Validation commands

```bash
mvn -q -DskipTests compile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Manual checks:

```text
GET http://localhost:8080/actuator/health
GET http://localhost:8080/swagger-ui.html
GET http://localhost:8080/v3/api-docs
```

## Completion criteria

```text
application no longer relies on generated password for basic dev documentation/health access
health endpoint is accessible in dev
Swagger UI is accessible in dev
business API security behavior is documented
production profile is not weakened
```

---

# STB-010 — Verify application boot baseline

```text
Commit code    : STB-010
Commit message : test(stabilization): verify application boot baseline
Type           : Test
Layer          : Bootstrap
Module         : bootstrap, platform
```

## Description

Add or fix a minimal boot smoke test that proves the current foundation can load its Spring context with the dev/test configuration.

The existing app has previously had test failures because the Spring context required a datasource. This task must create a deterministic test baseline.

## Files to inspect

| File | Purpose |
|---|---|
| `src/test/java/dz/sh/hidra/HidraApplicationTests.java` | Check current Spring context test and datasource behavior. |
| `src/test/resources/application-test.properties` | Check whether test profile exists and supplies DB/test configuration. |
| `pom.xml` | Confirm H2/Testcontainers availability or avoid requiring either. |

## Files to create or update

Use the existing project style. Preferred:

| File | Purpose |
|---|---|
| `src/test/java/dz/sh/hidra/HidraApplicationTests.java` | Make the boot test deterministic and compatible with current infrastructure. |
| `src/test/resources/application-test.properties` | Provide test profile settings if needed. |

Optional only if the project already supports Testcontainers:

| File | Purpose |
|---|---|
| `src/test/java/dz/sh/hidra/support/PostgresTestContainerSupport.java` | Centralize PostgreSQL Testcontainer setup if Testcontainers dependency exists. |

## Rules

```text
- Do not add H2 unless explicitly approved.
- Do not add Testcontainers unless dependency already exists or the task explicitly updates pom.xml.
- Do not disable all JPA scanning just to make the test pass.
- Do not delete the boot test.
- Do not make the test meaningless.
- If a real DB is required, document exact requirement.
```

## Validation commands

```bash
mvn -q test -Dtest=HidraApplicationTests
mvn -q test
```

## Completion criteria

```text
HidraApplicationTests passes or exact blocker is documented
mvn -q test passes or exact unrelated blocker is documented
test profile behavior is deterministic
```

---

# STB-011 — Finalize foundation validation checklist

```text
Commit code    : STB-011
Commit message : docs(stabilization): finalize foundation validation checklist
Type           : Documentation
Layer          : Documentation
Module         : stabilization
```

## Description

Update this roadmap with the final results of stabilization.

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/stabilization_01.md` | Record final status for all stabilization tasks, validation commands, blockers, and remaining risks. |

## Required final validation commands

Run these commands from the repository root:

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

If the application is started manually, verify:

```text
http://localhost:8080/actuator/health
http://localhost:8080/swagger-ui.html
http://localhost:8080/v3/api-docs
```

## Required final checklist

| Item | Status | Notes |
|---|---:|---|
| `mvn -q -DskipTests compile` passes | Pending | Fill during STB-011. |
| `mvn -q test` passes | Pending | Fill during STB-011. |
| `mvn -q clean verify` passes | Pending | Fill during STB-011. |
| App starts with dev profile | Pending | Fill during STB-011. |
| PostgreSQL dev DB documented | Pending | Fill during STB-011. |
| Flyway platform migration exists | Pending | Confirm `V001__create_platform_outbox_event.sql`. |
| Flyway identity migration exists | Pending | Confirm `V010__create_identity_tables.sql`. |
| Flyway organization migration exists | Pending | Confirm `V020__create_organization_tables.sql`. |
| REST mapper beans exist | Pending | Confirm identity and organization REST mapper configs. |
| Platform ObjectMapper bean exists | Pending | Confirm platform Jackson config. |
| Organization JPA constructors compile | Pending | Confirm mapper can instantiate entities. |
| Organization controllers have `@ApiResponses` | Pending | Confirm all endpoint methods. |
| Reporting line self-reporting rejected | Pending | Confirm test and domain behavior. |
| Dev health endpoint accessible | Pending | Confirm manually. |
| Dev Swagger UI accessible | Pending | Confirm manually. |
| No `identityaccess` package recreated | Pending | Repo scan. |
| No topology package created during stabilization | Pending | Repo scan. |
| No new business module created during stabilization | Pending | Repo scan. |

## Rules

```text
- Do not create new production code in STB-011.
- Do not create new tests in STB-011.
- Only update docs/roadmap/stabilization_01.md.
- If validation fails, record the exact command, exact error, and whether it is related or unrelated.
```

## Completion criteria

```text
this roadmap has an accurate final status table
all validation commands are recorded
remaining risks are explicit
foundation is ready for topology roadmap work only after this task is complete
```

---

## 5. Boundaries after stabilization

After this stabilization roadmap is complete, the next module should be:

```text
topology
```

The next roadmap file should be:

```text
docs/roadmap/topology.md
```

The first topology implementation task should be:

```text
TOP-002 — chore(topology): add topology package skeleton
```

Do not start topology until the stabilization checklist is complete or blockers are explicitly accepted.

---

## 6. Final status table

| Commit code | Status | Validation result | Notes |
|---|---:|---|---|
| `STB-001` | Planned | Pending | Add this roadmap. |
| `STB-002` | Planned | Pending | Organization JPA constructor visibility. |
| `STB-003` | Planned | Pending | REST mapper beans. |
| `STB-004` | Planned | Pending | Platform ObjectMapper bean. |
| `STB-005` | Planned | Pending | Identity migration. |
| `STB-006` | Planned | Pending | Organization migration. |
| `STB-007` | Planned | Pending | Organization controller OpenAPI responses. |
| `STB-008` | Planned | Pending | Reporting line self-reporting rule. |
| `STB-009` | Planned | Pending | Dev security baseline. |
| `STB-010` | Planned | Pending | Boot smoke validation. |
| `STB-011` | Planned | Pending | Final validation checklist. |
