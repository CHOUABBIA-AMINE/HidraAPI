# HidraAPI Test Strategy

## Status and applicability

CURRENT — HPR-P2-011 source-derived verification documentation, checked on 2026-10-09
against parent `35d9d949aa773a4d754c22d00181330f579f752b`. Owner authority, evidence precedence and
verification limits follow [the testing index](README.md). TARGET additions and
NOT ESTABLISHED facts below do not represent implemented gates or executed results.

## Current test populations

The [POM](../../pom.xml) declares Java 21, Spring Boot parent 4.1.1, Spring/JUnit testing,
Spring Security testing, Testcontainers PostgreSQL and ArchUnit 1.4.2. Surefire is
declared; no Failsafe or JaCoCo plugin/numeric coverage gate is configured in the
inspected POM/workflows. Integration-sounding class names ending Test remain part of
the ordinary Surefire selection; there is no separately configured integration phase.

| Population | Concrete evidence | Scope and limit |
|---|---|---|
| Domain/application behavior | [PlanTargetSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/planning/semantic/PlanTargetSemanticRemediationTest.java) | Focused invariant/reference behavior; mocks are not live owners |
| Owner-provider lookup | [MonitoringPlanTargetReferenceContractTest](../../src/test/java/dz/sh/hidra/modules/planning/semantic/MonitoringPlanTargetReferenceContractTest.java) | Scalar contract/provider semantics, not universal cross-module transactions |
| Architecture/source/reflection | [Architecture Testing](ARCHITECTURE_TESTING.md) | Dependency, source inventory and mapper rules; not behavioral coverage |
| Controller/HTTP/security | [API Testing](API_TESTING.md) | Actual configured fixture/filter/context determines scope |
| PostgreSQL migration/transaction/race | [Database Testing](DATABASE_TESTING.md) | Disposable DB and specific assertions; Docker may cause skips |
| Full application context | [HidraApplicationTests](../../src/test/java/dz/sh/hidra/HidraApplicationTests.java) | Context startup, Flyway and JPA validation; not all business use cases |
| Generated contract/compatibility | [production CI](../../.github/workflows/ci.yml) | Runtime OpenAPI/security assertions and supported comparison rules |
| Operational artifacts/procedures | [production CI](../../.github/workflows/ci.yml) and [P1 exercise](../operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md) | Static artifact checks differ from retained operator/physical evidence |
| Canonical documentation | [documentation CI](../../.github/workflows/docs.yml) | Reviewed inventory/metadata/local-link/index/module/P2 and deterministic snapshot checks; runtime equality runs in production CI |

Static source inventory at this parent: 324 test-tree Java files, 323 filenames ending
Test.java/Tests.java and one OrganizationMandatoryReferenceMigrationSupport helper.
Tests occur under all 24 module roots. All 47 directly annotated Testcontainers
classes use disabledWithoutDocker=true; no direct @Disabled annotation was found.
These are file/annotation counts, not discovered/executed cases, completeness or
proof that nothing else can skip. [Module navigation](REQUIREMENTS_TRACEABILITY.md#module-evidence-navigation)
records each actual population without claiming equal coverage.

## Commands and prerequisites

Run from the repository root with Java 21 and Maven satisfying the POM enforcer
(Maven 3.9 or newer); the wrapper supplies the configured Maven distribution. Artifact
resolution needs the configured repositories/cache and working network/DNS. These
commands describe existing selections; they were not run by this documentation task.

```bash
./mvnw -B -q -Dtest=PlanTargetSemanticRemediationTest,MonitoringPlanTargetReferenceContractTest test
./mvnw -B -q -Dtest=ArchitectureGuardrailTest,ForensicRemediationClosureTest,DomainPersistenceMirrorGuardrailTest,DomainInvariantGuardrailTest,GeneratedBoundaryMapperContractTest test
./mvnw -B -q -Dtest=PlanTargetSemanticPostgresIntegrationTest,PlanActualDeviationSemanticPostgresIntegrationTest,NominationSemanticPostgresIntegrationTest test
./mvnw -B -q test
./mvnw -B -q clean verify
```

The Docker-dependent selection needs a usable daemon and PostgreSQL images; verify
actual run/skip results. Tests reset schemas and construct fixtures in disposable
containers. Any external test datasource must be isolated/disposable, never production.
The [test profile](../../src/main/resources/application-test.properties) defaults to localhost PostgreSQL and supports
HIDRA_TEST_DATASOURCE_URL/USERNAME/PASSWORD; container tests override their datasource.
CI provides PostgreSQL 16 for test-profile application startup, while
HidraApplicationTests uses postgres:18-alpine and focused containers include
postgres:16-alpine. Those are distinct paths, not one shared database/version.

## Result recording discipline

Record exact commit, command/selection, Java/Maven/database/Docker context, exit code,
executed/failed/skipped populations and retained report identity when actually known.
Surefire reports produced by an actual local run belong under target/surefire-reports;
the current workflow explicitly uploads OpenAPI, not a configured Surefire report
artifact. Prior full CI #604 is the inspected job-level evidence, not a fabricated
per-class report or a new run at this documentation head. A missing Docker skip is
not a passing database case; a dependency failure before compilation is not a test
failure. Earlier uncached-parent/DNS limits remain historical until observed again.

## TARGET additions and NOT ESTABLISHED

Coverage thresholds, mutation/performance gates, complete endpoint/filter-chain and
real multi-owner end-to-end campaigns require their own admitted scope/evidence.
They are NOT ESTABLISHED by inventory or prior CI success. No new test runner,
report-upload step, benchmark or policy gate is implemented here.

## HPR-P2-012 validation update

Source parent `508337351eef03b82e2c6078a7c8523013efd063`, checked 2026-10-09. [Validation guide](../governance/DOCUMENTATION_VALIDATION.md) records the new Python suites and CI gates. The original Java test inventory/results retain HPR-P2-011 applicability. Current clean verify was attempted through Bash and blocked before compilation by Boot-parent/Maven Central DNS resolution; Java 17 is installed. New runtime CI remains pending after publication.
