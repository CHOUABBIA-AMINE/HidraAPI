# HidraAPI Stabilization Roadmap 01 — Final Validation Checklist

```text
Roadmap file : docs/roadmap/stabilization_01.md
Roadmap code : STB-01
Scope        : Stabilize the current HidraAPI foundation before starting topology or any new business module
Repository   : HidraAPI
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-05-30
Finalized by : STB-011
```

---

## 1. Final purpose

This file is the execution memory for the first HidraAPI stabilization phase.

The stabilization scope covered the current foundation only:

```text
bootstrap
kernel
platform
identity
organization
```

This roadmap intentionally did **not** start or create these future modules:

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

The goal of STB-01 was to convert the boot fixes discovered during local launch into explicit repository tasks and to record the final validation state before topology work begins.

---

## 2. Boundary rules confirmed

```text
No identityaccess package was intentionally created.
No shared/sharedkernel/common/core/utils/helper/helpers/misc package was intentionally created.
No topology package was intentionally created.
No measurement, operations, flow, risk, analytics, workflow, reporting, or notification package was intentionally created.
No production business logic was added outside the stabilization scope.
No organization controller behavior was changed during OpenAPI documentation stabilization.
No identity business logic was changed during dev security stabilization.
```

---

## 3. Final commit plan status

| Commit code | Commit message | Final status | Result |
|---|---|---:|---|
| `STB-001` | `docs(stabilization): add foundation stabilization roadmap` | Completed | Roadmap file existed before this execution sequence; verified in repository. |
| `STB-002` | `fix(organization): expose jpa entity constructors to persistence mapper` | Already satisfied | Five organization JPA no-arg constructors were already public on `main`; no new commit created. |
| `STB-003` | `fix(api): register rest mapper beans` | Already satisfied | Identity and organization REST mapper bean configurations already existed on `main`; no new commit created. |
| `STB-004` | `fix(platform): register jackson object mapper bean` | Already satisfied | Platform `ObjectMapper` configuration already existed on `main`; no new commit created. |
| `STB-005` | `db(identity): add identity flyway migration` | Completed | Added identity Flyway migration `V010__create_identity_tables.sql`. |
| `STB-006` | `db(organization): confirm organization flyway migration` | Completed | Added organization Flyway migration `V020__create_organization_tables.sql`. |
| `STB-007` | `docs(api): complete organization controller openapi responses` | Completed | Added `@ApiResponses` to organization controllers. |
| `STB-008` | `fix(organization): enforce reporting line self-reporting rule` | Completed | Added explicit policy-level self-reporting rule and test. |
| `STB-009` | `chore(security): add explicit development security baseline` | Completed | Added dev-aware security baseline and deterministic dev user. |
| `STB-010` | `test(stabilization): verify application boot baseline` | Completed / local execution pending | Added PostgreSQL Testcontainers-backed Spring boot smoke test and test profile properties. |
| `STB-011` | `docs(stabilization): finalize foundation validation checklist` | Completed | This final checklist records validation state, blockers, and next recommended work. |

---

## 4. Commit evidence

| Commit code | Commit SHA / evidence | Notes |
|---|---|---|
| `STB-001` | Existing roadmap verified; no new commit during execution | File already existed on `main` when STB-001 was requested. |
| `STB-002` | Existing code verified; no new commit during execution | Constructors were already public. |
| `STB-003` | Existing code verified; no new commit during execution | REST mapper beans were already registered. |
| `STB-004` | Existing code verified; no new commit during execution | Platform Jackson bean was already registered. |
| `STB-005` | `7638896b8ba5c2b72350561dfadd32d69b7a7d2f` | Added identity migration. |
| `STB-006` | `3d21e529f7d843a4bd6203ddbed25afe339ac509` | Added organization migration. |
| `STB-007` | `14fb35b2323fcf491b20077db918657ed45f81eb` | Updated `EmployeeController`. |
| `STB-007` | `3b1359eca40a9d0063353a4e45cc007a53acc462` | Updated `OrganizationUnitController`. |
| `STB-007` | `3c0af11a8ab4d1822702b0461ebce41686cd72f9` | Updated `PositionController`. |
| `STB-007` | `57a5934c44e996d4dd2ff964f00327e41f2632e1` | Updated `ReportingLineController`. |
| `STB-008` | `5bceac46c30709228d88bb8a189416e76eb31790` | Updated `ReportingLinePolicy`. |
| `STB-008` | `f44ef63a3dd1ce606ed4ed7746e24748b824459a` | Updated `ReportingLinePolicyTest`. |
| `STB-009` | `b2bae8cb573aa9a97cc72cd37f54ac306320644b` | Updated central `SecurityConfiguration`. |
| `STB-009` | `5fd3f8f61835b74e6ad2a923d07676b5af7e963a` | Added `PlatformDevSecurityConfiguration`. |
| `STB-010` | `0ca93713879b65b00068823800a027578950752c` | Updated `HidraApplicationTests`. |
| `STB-010` | `c718eae9c7823e1e61f4404e0e0cf0a9b75bf824` | Added `application-test.properties`. |
| `STB-011` | Filled by Git commit created for this file | Final roadmap checklist update. |

> Note: Some tasks produced more than one commit because the GitHub file API committed each file update immediately. Every generated commit used the exact roadmap commit message for that task.

---

## 5. Files created or updated by stabilization

### STB-005 — Identity migration

| File | Purpose |
|---|---|
| `src/main/resources/db/migration/V010__create_identity_tables.sql` | Creates identity-owned persistence tables for users, roles, permissions, user-role links, and role-permission links. |

Tables covered:

```text
hidra_identity_user
hidra_identity_role
hidra_identity_permission
hidra_identity_user_role
hidra_identity_role_permission
```

### STB-006 — Organization migration

| File | Purpose |
|---|---|
| `src/main/resources/db/migration/V020__create_organization_tables.sql` | Creates organization-owned persistence tables for employees, positions, organization units, employee assignments, and reporting lines. |

Tables covered:

```text
hidra_org_employee
hidra_org_position
hidra_org_unit
hidra_org_employee_assignment
hidra_org_reporting_line
```

Important correction:

```text
The actual organization unit table is hidra_org_unit, not hidra_org_organization_unit.
```

### STB-007 — Organization OpenAPI responses

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/organization/api/rest/controller/EmployeeController.java` | Adds `@ApiResponses` to every employee endpoint. |
| `src/main/java/dz/sh/hidra/modules/organization/api/rest/controller/OrganizationUnitController.java` | Adds `@ApiResponses` to every organization unit endpoint. |
| `src/main/java/dz/sh/hidra/modules/organization/api/rest/controller/PositionController.java` | Adds `@ApiResponses` to every position endpoint, including the current HTTP 501 placeholder. |
| `src/main/java/dz/sh/hidra/modules/organization/api/rest/controller/ReportingLineController.java` | Adds `@ApiResponses` to reporting-line endpoint. |

Behavior preserved:

```text
Endpoint paths unchanged.
Request DTOs unchanged.
Response DTOs unchanged.
Controller behavior unchanged.
HTTP 501 placeholders preserved.
Controllers still depend on application inbound ports and REST mapper only.
```

### STB-008 — Reporting line self-reporting rule

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/organization/domain/policy/ReportingLinePolicy.java` | Adds explicit policy method `ensureNoSelfReporting(EmployeeId, EmployeeId)` and calls it during reporting-line validation. |
| `src/test/java/dz/sh/hidra/modules/organization/domain/policy/ReportingLinePolicyTest.java` | Adds `shouldRejectSelfReporting()` test proving self-reporting is rejected with `ReportingLineException`. |

Domain placement confirmed:

```text
Rule is not in a controller.
Rule is not in a JPA entity.
Rule does not introduce identity dependency.
Rule does not introduce topology dependency.
SupervisorAssignment was not created.
```

### STB-009 — Development security baseline

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/platform/security/authentication/SecurityConfiguration.java` | Makes public endpoint access dev-aware while keeping business APIs authenticated. |
| `src/main/java/dz/sh/hidra/platform/security/configuration/PlatformDevSecurityConfiguration.java` | Adds deterministic `dev` profile in-memory user so local development no longer relies on Spring Security generated password. |

Dev public endpoints:

```text
/actuator/health
/actuator/health/**
/actuator/info
/v3/api-docs
/v3/api-docs/**
/swagger-ui/**
/swagger-ui.html
```

Dev credentials for protected business APIs:

```text
username: hidra-dev
password: hidra-dev
```

Security posture:

```text
Business APIs remain authenticated.
HTTP Basic is enabled only for the dev profile.
Production profile is not weakened by the dev user configuration.
JWT/OAuth was not introduced in this stabilization task.
```

### STB-010 — Boot smoke validation baseline

| File | Purpose |
|---|---|
| `src/test/java/dz/sh/hidra/HidraApplicationTests.java` | Replaces the lightweight class-exists test with a real `@SpringBootTest` using PostgreSQL Testcontainers. |
| `src/test/resources/application-test.properties` | Adds deterministic test profile settings while datasource values are supplied dynamically by Testcontainers. |

Test baseline:

```text
@SpringBootTest(classes = HidraApplication.class)
@ActiveProfiles("test")
PostgreSQL Testcontainers
Flyway enabled
JPA ddl-auto=validate
No H2 added
No new Maven dependency added
JPA scanning not disabled
```

Execution requirement:

```text
A Docker-compatible runtime is required to run HidraApplicationTests because the project now uses PostgreSQL Testcontainers for the boot smoke test.
```

---

## 6. Final validation checklist

| Item | Status | Notes |
|---|---:|---|
| `mvn -q -DskipTests compile` passes | Not run in this execution | Must be run locally from a full working tree. GitHub connector cannot execute Maven. |
| `mvn -q test` passes | Not run in this execution | Must be run locally. Requires Docker/Testcontainers for `HidraApplicationTests`. |
| `mvn -q clean verify` passes | Not run in this execution | Must be run locally after compile and test are green. |
| App starts with dev profile | Not rerun after STB-009/STB-010 | Earlier local startup succeeded before these final stabilization changes; rerun is required. |
| PostgreSQL dev DB documented | Confirmed | Dev profile uses `jdbc:postgresql://localhost:5432/hidra_dev` by default. |
| Flyway platform migration exists | Assumed existing | `V001__create_platform_outbox_event.sql` was reported as the original migration. Confirm locally before release. |
| Flyway identity migration exists | Confirmed by repository update | `V010__create_identity_tables.sql` added in STB-005. |
| Flyway organization migration exists | Confirmed by repository update | `V020__create_organization_tables.sql` added in STB-006. |
| REST mapper beans exist | Confirmed by repository inspection | Identity and organization REST mapper configurations existed before STB-003 execution. |
| Platform ObjectMapper bean exists | Confirmed by repository inspection | Platform Jackson configuration existed before STB-004 execution. |
| Organization JPA constructors compile | Partially confirmed | Constructors were inspected as public; full Maven compile still required. |
| Organization controllers have `@ApiResponses` | Confirmed by repository update | Completed in STB-007. |
| Reporting line self-reporting rejected | Confirmed by repository update | Policy rule and test added in STB-008. |
| Dev health endpoint accessible | Not manually verified | Check after local boot. |
| Dev Swagger UI accessible | Not manually verified | Check after local boot. |
| Dev API docs accessible | Not manually verified | Check after local boot. |
| Business APIs protected in dev | Confirmed by configuration inspection | Dev public endpoints are limited; remaining requests require authentication. |
| No `identityaccess` package recreated | Needs final local repo scan | No stabilization task intentionally created it. |
| No topology package created during stabilization | Needs final local repo scan | No stabilization task intentionally created it. |
| No new business module created during stabilization | Needs final local repo scan | No stabilization task intentionally created one. |

---

## 7. Required local validation commands

Run these from the repository root after pulling the latest `main`:

```bash
mvn -q -DskipTests compile
mvn -q test -Dtest=HidraApplicationTests
mvn -q test
mvn -q clean verify
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Required manual checks after the dev app starts:

```text
GET http://localhost:8080/actuator/health
GET http://localhost:8080/actuator/info
GET http://localhost:8080/swagger-ui.html
GET http://localhost:8080/v3/api-docs
```

Expected dev protected API behavior:

```text
Business API without credentials -> 401 Unauthorized
Business API with HTTP Basic hidra-dev / hidra-dev -> authenticated request reaches controller/security authorization path
```

Recommended boundary scans:

```bash
find src/main/java -type d | grep -E 'identityaccess|sharedkernel|shared|common|core|utils|helper|helpers|misc|topology|measurement|operations|flow|risk|analytics|workflow|reporting|notification'
```

On Windows PowerShell:

```powershell
Get-ChildItem src/main/java -Directory -Recurse |
  Where-Object { $_.FullName -match 'identityaccess|sharedkernel|shared|common|core|utils|helper|helpers|misc|topology|measurement|operations|flow|risk|analytics|workflow|reporting|notification' }
```

---

## 8. Known remaining risks

```text
1. Maven compile/test/verify were not run by this execution because the GitHub connector cannot execute build commands.
2. HidraApplicationTests now requires Docker/Testcontainers.
3. Flyway migrations V010 and V020 were read back from GitHub but not applied to a clean PostgreSQL database in this execution.
4. application-dev.properties currently uses spring.jpa.hibernate.ddl-auto=update; this may be acceptable for local development but should be revisited before staging/production readiness.
5. Dev security now uses a deterministic local user; this is intentionally @Profile("dev") only and must not be copied into production authentication.
6. HTTP 501 placeholders still exist for organization update/list endpoints where application ports are not yet implemented.
7. Full local endpoint checks for health, info, Swagger UI, and API docs remain required after boot.
8. Because several earlier GitHub file updates committed one file at a time, some roadmap tasks have multiple commits with the same exact commit message.
```

---

## 9. Local PostgreSQL reminder for dev boot

Default dev profile values:

```text
Database : hidra_dev
Username : hidra
Password : hidra
URL      : jdbc:postgresql://localhost:5432/hidra_dev
```

Example local PostgreSQL container:

```bash
docker run --name hidra-postgres \
  -e POSTGRES_DB=hidra_dev \
  -e POSTGRES_USER=hidra \
  -e POSTGRES_PASSWORD=hidra \
  -p 5432:5432 \
  -d postgres:16
```

Then run:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## 10. Stabilization conclusion

STB-01 repository changes are complete.

The foundation is ready for **local validation**. It is not yet correct to claim full production readiness until these commands pass locally:

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

After those commands pass and the manual endpoint checks succeed, the next recommended roadmap is:

```text
docs/roadmap/topology.md
```

The next implementation sequence should start with:

```text
TOP-001 — docs(topology): add topology implementation roadmap
TOP-002 — chore(topology): add topology package skeleton
```
