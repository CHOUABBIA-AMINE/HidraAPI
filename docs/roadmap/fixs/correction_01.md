# HidraAPI Deep Repository Analysis and Correction Roadmap

```text
Roadmap file : docs/roadmap/correction_01.md
Roadmap code : COR
Scope        : Correct topology/organization implementation decisions before starting measurement
Repository   : CHOUABBIA-AMINE/HidraAPI
Branch       : main
Updated      : 2026-06-06
Mode         : Correction execution memory
```

## 1. Executive decision

Do **not** start the measurement module yet.

The corrected baseline must be proven by Maven, Flyway, and the topology boot smoke test before `MES-001` starts.

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
COR-018 blocked   — corrected baseline validation could not pass until remaining enum declarations were removed and Maven/Flyway were run locally
COR-019 completed — remove remaining business taxonomy enum declarations
COR-020 current   — record COR-019 and corrected baseline validation path; repair reported ListFacilitiesQuery transition constructor
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

### COR-001 — docs(correction): add repository correction roadmap

```text
Commit code    : COR-001
Commit message : docs(correction): add repository correction roadmap
Type           : Documentation
Module         : correction
```

Create `docs/roadmap/correction_01.md`.

### COR-002 — chore(repo): remove generated bin roadmap artifacts

```text
Commit code    : COR-002
Commit message : chore(repo): remove generated bin roadmap artifacts
Type           : Chore
Module         : repository
```

Remove committed generated `bin/docs/**` artifacts and add `bin/` to `.gitignore`.

### COR-003 — docs(topology): realign topology roadmap with repository reality

```text
Commit code    : COR-003
Commit message : docs(topology): realign topology roadmap with repository reality
Type           : Documentation
Module         : topology
```

Realign `docs/roadmap/topology.md` and restore/create `docs/roadmap/topology_validation_checklist.md`.

### COR-004 — docs(architecture): define controlled vocabulary policy

```text
Commit code    : COR-004
Commit message : docs(architecture): define controlled vocabulary policy
Type           : Documentation
Module         : architecture
```

Create `docs/architecture/controlled-vocabulary-policy.md`.

### COR-005 — db(topology): add topology type catalog tables

```text
Commit code    : COR-005
Commit message : db(topology): add topology type catalog tables
Type           : Database
Module         : topology
```

Create `V003__add_topology_type_catalogs.sql` with topology type catalog and translation tables.

### COR-006 — feat(topology): add catalog domain model and references

```text
Commit code    : COR-006
Commit message : feat(topology): add catalog domain model and references
Type           : Feature
Module         : topology
```

Add topology catalog domain model and type reference value objects.

### COR-007 — feat(topology): add topology catalog application services

```text
Commit code    : COR-007
Commit message : feat(topology): add topology catalog application services
Type           : Feature
Module         : topology
```

Add catalog DTOs, queries, inbound ports, outbound port, and application service.

### COR-008 — feat(topology): add topology catalog persistence adapters

```text
Commit code    : COR-008
Commit message : feat(topology): add topology catalog persistence adapters
Type           : Feature
Module         : topology
```

Add catalog persistence rows/repositories/mapper/adapter and wire `TopologyConfiguration`.

### COR-009 — refactor(topology): replace enum usage in domain assets with type references

```text
Commit code    : COR-009
Commit message : refactor(topology): replace enum usage in domain assets with type references
Type           : Refactor
Module         : topology
```

Refactor topology domain/application asset contracts to use type references while temporarily keeping compatibility bridges.

### COR-010 — db(topology): migrate topology assets to catalog foreign keys

```text
Commit code    : COR-010
Commit message : db(topology): migrate topology assets to catalog foreign keys
Type           : Database
Module         : topology
```

Create `V004__link_topology_assets_to_type_catalogs.sql`.

### COR-011 — refactor(topology): update topology persistence mapping to catalog foreign keys

```text
Commit code    : COR-011
Commit message : refactor(topology): update topology persistence mapping to catalog foreign keys
Type           : Refactor
Module         : topology
```

Update topology JPA entities, repositories, mapper, and adapters to use catalog foreign-key ids.

### COR-012 — refactor(topology): update topology REST contracts for catalog types and localization

```text
Commit code    : COR-012
Commit message : refactor(topology): update topology REST contracts for catalog types and localization
Type           : Refactor
Module         : topology
```

REST requests use stable `typeCode`; REST responses expose localized `{ id, code, label, locale }` type references.

### COR-013 — db(topology): remove topology enum-style type constraints

```text
Commit code    : COR-013
Commit message : db(topology): remove topology enum-style type constraints
Type           : Database
Module         : topology
```

Create `V005__remove_topology_enum_type_columns.sql`.

### COR-014 — test(topology): repair unstable topology tests

```text
Commit code    : COR-014
Commit message : test(topology): repair unstable topology tests
Type           : Test
Module         : topology
```

Stabilize topology fixtures and update topology tests after catalog conversion.

### COR-015 — test(architecture): add controlled vocabulary guardrails

```text
Commit code    : COR-015
Commit message : test(architecture): add controlled vocabulary guardrails
Type           : Test
Module         : architecture
```

Add `ControlledVocabularyArchitectureTest` to fail forbidden business taxonomy enum declarations.

### COR-016 — refactor(organization): convert organization unit type to catalog entity

```text
Commit code    : COR-016
Commit message : refactor(organization): convert organization unit type to catalog entity
Type           : Refactor
Module         : organization
```

Convert organization unit type from enum modeling to catalog/reference modeling.

### COR-017 — docs(topology): update topology roadmap after catalog refactor

```text
Commit code    : COR-017
Commit message : docs(topology): update topology roadmap after catalog refactor
Type           : Documentation
Module         : topology
```

Update topology roadmap/checklist after catalog refactor.

### COR-018 — test(stabilization): validate corrected baseline

```text
Commit code    : COR-018
Commit message : test(stabilization): validate corrected baseline
Type           : Test
Module         : stabilization
```

Validation-only task. It is accepted only when all commands in section 5 pass.

### COR-019 — refactor(catalog): remove remaining business taxonomy enum declarations

```text
Commit code    : COR-019
Commit message : refactor(catalog): remove remaining business taxonomy enum declarations
Type           : Refactor
Module         : catalog
```

#### Goal

Remove remaining forbidden Java enum declarations that blocked `ControlledVocabularyArchitectureTest`.

#### Files updated

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

#### Rule

These compatibility files may temporarily remain as final classes, but must not be Java enums.

### COR-020 — docs(correction): record COR-019 and corrected baseline validation path

```text
Commit code    : COR-020
Commit message : docs(correction): record COR-019 and corrected baseline validation path
Type           : Documentation / Compile Compatibility
Module         : correction
```

#### Goal

Record COR-019 in the correction roadmap and document the corrected baseline validation path.

Also repair the reported transitional compile error:

```text
The constructor ListFacilitiesQuery(String, FacilityTypeReference, ProductType, TopologyStatus, PageRequest) is undefined
```

#### Files to update

```text
docs/roadmap/correction_01.md
src/main/java/dz/sh/hidra/modules/topology/application/query/ListFacilitiesQuery.java
```

#### Purpose of each file

| File | Purpose |
|---|---|
| `docs/roadmap/correction_01.md` | Records COR-019, COR-020, and the corrected baseline validation path before measurement starts. |
| `ListFacilitiesQuery.java` | Adds narrow deprecated bridge constructors for mixed transitional callers using `FacilityTypeReference` with legacy `ProductType`, or legacy `FacilityType` with `ProductTypeReference`. |

#### Rules

```text
Do not start measurement.
Do not modify migrations.
Do not modify topology REST contracts.
Do not modify persistence mapping.
Do not modify organization code.
Keep the bridge constructors deprecated for later removal.
```

#### Validation

```bash
mvn -q test -Dtest=ControlledVocabularyArchitectureTest
mvn -q -DskipTests compile
mvn -q test
mvn -q test -Dtest=TopologyApplicationBootSmokeTest
mvn -q -DskipTests flyway:migrate
```

## 5. Corrected baseline validation path

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

## 6. Next module gate

Do not start measurement until this gate is true:

```text
COR-001 through COR-020 are reflected in docs/roadmap/correction_01.md
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

After the gate passes, the next recommended task remains:

```text
MES-001 — docs(measurement): add measurement implementation roadmap
```

## 7. Remaining risks

```text
Maven and Flyway have not been run through this GitHub connector workflow.
Compatibility classes still exist and should be removed later after all callers use catalog references directly.
Business users must validate French and Arabic catalog terminology.
Do not edit already-applied Flyway migrations; add new migrations only.
```
