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


## Current source and CI recovery — 2026-10-13

Source `8c173f4942371b377f9155e7d99af4e4c5fc288c`, exact Production #666 and Docs #266
SUCCESS. Current test-tree inventory is 371 Java files, including one abstract
OrganizationMandatoryReferenceMigrationSupport and 370 actual executable classes.
Recovered #666 artifact 11801709752 contains 370 XML suites / 1,763 tests /
0 failures / 0 errors / 0 skips, matched to current source including inherited methods.
Counts are source-dated observations, not fixed acceptance totals or coverage percentages.

Current full CI retains raw Surefire XML and a timing/count summary; this supersedes the
older no-report-upload claim only for current workflow behavior. Missing report upload
still warns; the proposed fail-closed report/selection policy is not installed.
Fast remains manual-only with two Topology tests. Full remains a sequential Java21 job,
two reused JVM test forks and PostgreSQL16 service plus existing16/18 class fixtures;
main Full/Docs may cancel superseded runs under current configuration. No selection
profile, JUnit tag policy, Failsafe or JaCoCo gate is configured. Eight full application
context source classes and one manual lightweight Spring context are retained as actual
source paths in the roadmap inventory; inventories do not prove they can be replaced
by narrower contexts without changing semantics.

The [prospective development and CI baseline](../roadmap/ULTIMATE_ROADMAP.md#development-consolidation--authoritative-prospective-register-2026-10-13)
contains the complete per-class candidate lane/timing inventory, obligation matrix,
measured accepted runs, exact CI-003 scope, automatic triggers, partition/aggregate/
release/cancellation design and equivalence/measurement acceptance. Runtime improvements
are PLANNED, not implemented. Existing unrestricted full verify remains authoritative
until same-SHA oracle equivalence succeeds. Generic PLATFORM and customer SITE remain
separate; no simulator accuracy or customer commissioning is established by full CI.


## HPR-CI-003 installed shadow verification — 2026-10-13

This source update supersedes the prior manual-only/two-test/current cancellation and
warning-only report claims for the new workflow revision. Accepted source baseline is
c1f25af, Production #668 / Docs #268 SUCCESS: 371 concrete XML classes / 1,776 executed
cases / no failures/errors/skips; 372 Java test sources include the inherited abstract
support. The [reviewed manifest](../../.github/ci-test-inventory.json) records exact source
and case identities; new or modified tests require reviewed inventory updates rather
than annotation/name guesses. Existing Java/POM/Testcontainers fixtures remain unchanged.

Fast now triggers on relevant main pushes and PRs plus manual dispatch, selecting 294
classes / 1,262 cases (reviewed unit/architecture only), no Postgres/Docker service.
Full unit owns 295 / 1,306 including the initially full-only lightweight context. Its
two isolated complete-class DB shards own 37 / 241 and 39 / 229, using existing16/18
fixtures, one fork per shard, no parallel methods. Source/report counts are dated facts.

Run selections through the manifest checker:

```bash
python3 .github/scripts/verify_ci_test_inventory.py
classes="$(python3 .github/scripts/verify_ci_test_inventory.py --select fast)"
bash ./mvnw -B -q test "-Dtest=${classes}"
python3 .github/scripts/verify_ci_test_inventory.py --select fast --reports target/surefire-reports
```

Full test partitions each run clean verify with exact classes, retaining build/package
lifecycle; independent API jobs own Java21 current boot/security/snapshot equality,
real dictionary and actual historical OpenAPI compatibility. Current and historical
apps use separate disposable databases. Static retains all validators/ops checks.
Unrestricted two-fork full verify remains REQUIRED as an oracle throughout shadow.
The always-evaluated aggregate rejects absent/extra/duplicate/failed/errored/skipped/
cancelled/stale artifacts or jobs and compares actual class/case/API/catalog facts on
the same SHA/run/attempt. Raw diagnostics and lane summaries remain available on failure.
Mandatory main Full/Docs no longer cancel for a newer SHA; Fast PR feedback may cancel.

Optimization/equivalence/performance acceptance is pending hosted execution and the
registered multi-revision/repeated measurement campaign. Warm Fast<=3m and Full<=7m
are targets, not results. Shadow deliberately duplicates the full oracle; it does not
claim reduced full wall time or runner cost. Java21/Docker prerequisites are unchanged.
Owner workflow procedure is main only, no PR/new branches/proof images; observe exact
push CI startup then STOP. Unsupported manual dispatch is handed to the owner BEFORE
starting it. PLATFORM regression proof remains separate from customer SITE commissioning.
