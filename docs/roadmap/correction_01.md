# HidraAPI Deep Repository Analysis and Correction Roadmap

```text
Roadmap file : docs/roadmap/correction_01.md
Roadmap code : COR
Scope        : Correct topology/organization implementation decisions before starting telemetry
Repository   : CHOUABBIA-AMINE/HidraAPI
Branch       : main
Updated      : 2026-06-06
Mode         : Correction execution memory
```

## 1. Executive decision

Do **not** start the telemetry module yet.

The corrected baseline must be proven by Maven, Flyway, and the topology boot smoke test before `TEL-001` starts.

The main correction theme remains:

```text
Business taxonomy concepts must not be Java enums.
They must be catalog/reference data with multilingual labels.
```

## 2. Current correction status

```text
COR-001 completed — add repository correction roadmap
COR-002 completed — remove generated bin/docs artifacts
COR-003 completed — realign topology roadmap with repository reality
COR-004 completed — define controlled vocabulary policy
COR-005 completed — add topology type catalog tables
COR-006 completed — add topology catalog domain model and references
COR-007 completed — add topology catalog application services
COR-008 completed — add topology catalog persistence adapters
COR-009 completed — replace enum usage in topology domain/application assets with references
COR-010 completed — migrate topology asset tables to catalog foreign keys
COR-011 completed — update topology persistence mapping to catalog foreign keys
COR-012 completed — update topology REST contracts for catalog types and localization
COR-013 completed — remove topology enum-style type columns and constraints
COR-014 completed — repair unstable topology tests
COR-015 completed — add controlled vocabulary guardrail test
COR-016 completed — convert organization unit type to catalog entity
COR-017 completed — update topology roadmap after catalog refactor
COR-018 blocked   — corrected baseline validation still requires local Maven/Flyway/Testcontainers execution
COR-019 completed — remove remaining business taxonomy enum declarations
COR-020 completed — record COR-019 and corrected baseline validation path; repair reported ListFacilitiesQuery transition constructor
COR-021 current   — replace measurement next-module references with telemetry
```

## 3. Controlled vocabulary policy

Enums may remain for stable technical lifecycle concepts:

```text
TopologyStatus
OrganizationUnitStatus
EmploymentStatus
AssignmentStatus
ReportingLineStatus
technical directions/states where values are not business-managed taxonomy
```

Business taxonomies must be catalog/reference data, not Java enums:

```text
FacilityType
PipelineAppurtenanceType
ValveType
NodeType
EquipmentType
ConnectionType
ProductType
OrganizationUnitType
OperationalScopeType
ReportingLineType
```

Transitional compatibility classes may temporarily preserve old source calls such as:

```java
ProductType.GAS
FacilityType.COMPRESSION_STATION
ProductType.valueOf("GAS")
ProductType.values()
```

but those classes must not be declared with `enum`.

## 4. Correction roadmap

| Code | Commit message | Status | Purpose |
|---|---|---:|---|
| `COR-001` | `docs(correction): add repository correction roadmap` | Completed | Add correction roadmap. |
| `COR-002` | `chore(repo): remove generated bin roadmap artifacts` | Completed | Remove committed generated `bin/docs/**` and ignore `bin/`. |
| `COR-003` | `docs(topology): realign topology roadmap with repository reality` | Completed | Realign topology docs with actual TOP sequence. |
| `COR-004` | `docs(architecture): define controlled vocabulary policy` | Completed | Define enum-vs-catalog policy. |
| `COR-005` | `db(topology): add topology type catalog tables` | Completed | Add topology catalog and translation tables in V003. |
| `COR-006` | `feat(topology): add catalog domain model and references` | Completed | Add topology catalog domain model and type references. |
| `COR-007` | `feat(topology): add topology catalog application services` | Completed | Add catalog DTOs, queries, ports, and application service. |
| `COR-008` | `feat(topology): add topology catalog persistence adapters` | Completed | Add catalog persistence adapter and configuration wiring. |
| `COR-009` | `refactor(topology): replace enum usage in domain assets with type references` | Completed | Refactor topology domain/application asset contracts to use references. |
| `COR-010` | `db(topology): migrate topology assets to catalog foreign keys` | Completed | Add and backfill topology catalog FK columns in V004. |
| `COR-011` | `refactor(topology): update topology persistence mapping to catalog foreign keys` | Completed | Use catalog FK ids in topology persistence. |
| `COR-012` | `refactor(topology): update topology REST contracts for catalog types and localization` | Completed | REST uses `typeCode` and localized type responses. |
| `COR-013` | `db(topology): remove topology enum-style type constraints` | Completed | Remove old topology type varchar columns/checks in V005. |
| `COR-014` | `test(topology): repair unstable topology tests` | Completed | Stabilize topology fixtures and tests after catalog conversion. |
| `COR-015` | `test(architecture): add controlled vocabulary guardrails` | Completed | Add test blocking forbidden business taxonomy enum declarations. |
| `COR-016` | `refactor(organization): convert organization unit type to catalog entity` | Completed | Convert organization unit type to catalog/reference modeling. |
| `COR-017` | `docs(topology): update topology roadmap after catalog refactor` | Completed | Update topology roadmap/checklist after catalog refactor. |
| `COR-018` | `test(stabilization): validate corrected baseline` | Blocked | Validation-only task requiring local Maven/Flyway/Testcontainers execution. |
| `COR-019` | `refactor(catalog): remove remaining business taxonomy enum declarations` | Completed | Replace remaining forbidden enum declarations with compatibility classes. |
| `COR-020` | `docs(correction): record COR-019 and corrected baseline validation path` | Completed | Record COR-019/COR-020 and add transition constructor fix. |
| `COR-021` | `docs(correction): replace measurement next-module references with telemetry` | Current | Correct next-module naming from measurement/MES to telemetry/TEL. |

## 5. COR-019 record

```text
Commit code    : COR-019
Commit message : refactor(catalog): remove remaining business taxonomy enum declarations
Type           : Refactor
Module         : catalog
```

### Goal

Remove remaining forbidden Java enum declarations that blocked `ControlledVocabularyArchitectureTest`.

### Files updated

```text
src/main/java/dz/sh/hidra/modules/topology/domain/value/ProductType.java
src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityType.java
src/main/java/dz/sh/hidra/modules/topology/domain/value/NodeType.java
src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineAppurtenanceType.java
src/main/java/dz/sh/hidra/modules/topology/domain/value/ValveType.java
src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentType.java
src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java
src/main/java/dz/sh/hidra/modules/organization/domain/value/OperationalScopeType.java
src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java
```

### Rule

These compatibility files may temporarily remain as final classes, but must not be Java enums.

## 6. COR-020 record

```text
Commit code    : COR-020
Commit message : docs(correction): record COR-019 and corrected baseline validation path
Type           : Documentation / Compile Compatibility
Module         : correction
```

### Goal

Record COR-019 in the correction roadmap and document the corrected baseline validation path.

Also repair the reported transitional compile error:

```text
The constructor ListFacilitiesQuery(String, FacilityTypeReference, ProductType, TopologyStatus, PageRequest) is undefined
```

### Files updated

```text
docs/roadmap/correction_01.md
src/main/java/dz/sh/hidra/modules/topology/application/query/ListFacilitiesQuery.java
```

## 7. COR-021 record

```text
Commit code    : COR-021
Commit message : docs(correction): replace measurement next-module references with telemetry
Type           : Documentation
Module         : correction
```

### Goal

Correct the repository execution memory so the next module is **telemetry**, not measurement.

### Files to update

```text
docs/roadmap/correction_01.md
docs/roadmap/topology.md
```

### Purpose of each file

| File | Purpose |
|---|---|
| `docs/roadmap/correction_01.md` | Replaces measurement/MES next-module gate with telemetry/TEL and records COR-021. |
| `docs/roadmap/topology.md` | Replaces the topology next gate from `MES-001` to `TEL-001`. |

### Rules

```text
Do not start telemetry implementation.
Do not modify production Java code.
Do not modify test Java code.
Do not modify migrations.
Do not modify topology implementation.
Do not modify organization implementation.
```

## 8. Corrected baseline validation path

Run locally from the repository root:

```bash
mvn -q test -Dtest=ControlledVocabularyArchitectureTest
mvn -q -DskipTests compile
mvn -q test
mvn -q test -Dtest=TopologyApplicationBootSmokeTest
mvn -q -DskipTests flyway:migrate
```

Expected final result:

```text
BUILD SUCCESS
```

The topology boot smoke test requires Docker/Testcontainers.

## 9. Next module gate

Do not start telemetry until this gate is true:

```text
COR-001 through COR-021 are reflected in docs/roadmap/correction_01.md
ControlledVocabularyArchitectureTest passes
mvn -q -DskipTests compile passes
mvn -q test passes
TopologyApplicationBootSmokeTest passes with Docker/Testcontainers
Flyway migrations V001 through V005 and V021 apply cleanly
Topology catalogs support multilingual labels
Topology REST responses expose localized type labels
Topology asset tables use catalog foreign keys
Organization unit type has been converted to catalog/reference modeling
```

After the gate passes, the next recommended task is:

```text
TEL-001 — docs(telemetry): add telemetry implementation roadmap
```

## 10. Remaining risks

```text
Maven and Flyway have not been run through this GitHub connector workflow.
Compatibility classes still exist and should be removed later after all callers use catalog references directly.
Business users must validate French and Arabic catalog terminology.
Do not edit already-applied Flyway migrations; add new migrations only.
Telemetry implementation must not begin until the corrected baseline passes locally or in CI.
```
