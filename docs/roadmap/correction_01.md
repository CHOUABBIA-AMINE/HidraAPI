# HidraAPI Deep Repository Analysis and Correction Roadmap

```text
Roadmap file : docs/roadmap/correction_01.md
Roadmap code : COR
Scope        : Correct topology/organization implementation decisions before starting measurement
Repository   : CHOUABBIA-AMINE/HidraAPI
Branch       : main
Generated    : 2026-06-06
Mode         : Analysis + correction roadmap only
```

## 1. Executive decision

Do **not** start the next business module yet.

The repository is close to a usable modular-monolith baseline, but it is **not ready for measurement implementation** until the correction tasks below are completed.

The blocking issue is conceptual, not only technical:

```text
Type-like business concepts were modeled as Java enums and database CHECK constraints.
This is not compatible with multilingual labels, configurable business catalogs, or future industrial taxonomy governance.
```

The correction must happen before `measurement`, because measurement will reference topology assets and will inherit these type contracts.

---

## 2. Live repository analysis summary

### 2.1 App vision alignment

HidraAPI is the backend foundation for Hidra, a hydrocarbon intelligence platform focused on:

```text
Data
Risk
Analytics
Operational trust
Validation
Auditability
Industrial pipeline operations
```

The topology module direction is aligned with this vision because it models physical hydrocarbon network master data:

```text
pipeline systems
pipelines
pipeline segments
facilities
terminals
processing plants
production field interfaces
topology nodes
valves
injection points
extraction points
purge points
equipment references
physical network connectivity
operational topology status
```

However, the current enum-based type modeling is not aligned with multilingual, configurable, long-lived industrial taxonomy requirements.

### 2.2 Architecture alignment

Current package structure is mostly aligned with the modular-monolith architecture:

```text
src/main/java/dz/sh/hidra/modules/topology/domain
src/main/java/dz/sh/hidra/modules/topology/application
src/main/java/dz/sh/hidra/modules/topology/infrastructure
src/main/java/dz/sh/hidra/modules/topology/api
```

Expected dependency direction is mostly respected:

```text
API -> application
application -> domain + ports
infrastructure -> application ports + persistence
domain -> kernel
```

Correction needed:

```text
Catalog/type taxonomy should become an explicit bounded submodel inside the owning module,
not Java enums spread through domain, API, persistence, and database constraints.
```

### 2.3 Roadmap alignment

The topology roadmap is not fully aligned with repository reality. It still contains outdated planned statuses around TOP-013 onward, while production files, migrations, REST files, and tests exist.

Future AI agents depend on roadmap files as execution memory, so the roadmap must be corrected before more implementation starts.

### 2.4 Type modeling issue

The current implementation uses Java enums for business type catalogs, including:

```text
FacilityType
PipelineAppurtenanceType
ValveType
NodeType
EquipmentType
ConnectionType
ProductType
```

Organization also has the same risk with:

```text
OrganizationUnitType
```

These are not true technical states. They are configurable business vocabularies and should become catalog entities with localized labels.

### 2.5 Database issue

The topology migration stores type values in varchar columns and restricts them with `CHECK (...) IN (...)` constraints. This means adding or renaming business taxonomy values requires code and migration changes.

That is not acceptable for multilingual taxonomy governance.

### 2.6 Test stability issue

Some tests likely call test-data factories multiple times and therefore use different generated identifiers for save and lookup operations. These tests must be stabilized by saving fixture instances into local variables and reusing the same object id.

### 2.7 Repository hygiene issue

Generated `bin/docs/roadmap/**` files appear to be committed. These should be removed and `bin/` should be ignored unless there is a deliberate source-controlled bin directory.

---

## 3. Controlled vocabulary policy

### 3.1 Keep enums for true technical states

Enums may remain for stable state-machine concepts such as:

```text
TopologyStatus
OrganizationUnitStatus
EmploymentStatus
AssignmentStatus
ReportingLineStatus
```

### 3.2 Replace enums with catalog entities for business taxonomies

The following should not remain Java enums:

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

Reason:

```text
They need multilingual labels.
They may need descriptions.
They may need display order.
They may need active/inactive status.
They may need future extension without Java deployment.
They may need governance and auditability.
```

### 3.3 Recommended catalog table shape

Base table pattern:

```text
id varchar(80) primary key
code varchar(80) unique not null
status varchar(40) not null
sort_order integer not null
system_defined boolean not null
created_at timestamp with time zone not null
updated_at timestamp with time zone not null
```

Translation table pattern:

```text
id varchar(80) primary key
<type>_id varchar(80) not null references base table(id)
locale varchar(10) not null
name varchar(160) not null
description varchar(500)
created_at timestamp with time zone not null
updated_at timestamp with time zone not null
unique(<type>_id, locale)
```

Required first locales:

```text
en
fr
ar
```

### 3.4 Recommended domain references

Instead of passing business taxonomy enums through the domain, use explicit reference value objects:

```text
FacilityTypeReference
ProductTypeReference
NodeTypeReference
PipelineAppurtenanceTypeReference
ValveTypeReference
EquipmentTypeReference
ConnectionTypeReference
OrganizationUnitTypeReference
```

Each reference should carry at least:

```text
id
code
```

REST responses may additionally expose localized labels.

---

# 4. Correction roadmap

## COR-001 — docs(correction): add repository correction roadmap

```text
Commit code    : COR-001
Commit message : docs(correction): add repository correction roadmap
Type           : Documentation
Layer          : Documentation
Module         : correction
```

### Goal

Create this roadmap in the repository:

```text
docs/roadmap/correction_01.md
```

### Files to create

| File | Purpose |
|---|---|
| `docs/roadmap/correction_01.md` | Defines all correction tasks before the next business module starts. |

### Files to update

None.

### Rules

```text
Do not modify production Java code.
Do not modify test Java code.
Do not modify migrations.
Do not modify existing roadmap files.
```

### Validation

```bash
test -f docs/roadmap/correction_01.md
```

PowerShell:

```powershell
Test-Path docs/roadmap/correction_01.md
```

---

## COR-002 — chore(repo): remove generated bin roadmap artifacts

```text
Commit code    : COR-002
Commit message : chore(repo): remove generated bin roadmap artifacts
Type           : Chore
Layer          : Repository Hygiene
Module         : repository
```

### Goal

Remove committed generated files under:

```text
bin/docs/**
```

and prevent recurrence.

### Files to delete

| File or directory | Purpose |
|---|---|
| `bin/docs/**` | Remove generated/duplicated roadmap output from source control. |

### Files to update

| File | Purpose |
|---|---|
| `.gitignore` | Add `bin/` unless there is a deliberate source-controlled bin directory. |

### Rules

```text
Do not delete docs/roadmap/**
Do not delete src/**
Do not delete Maven files
Do not modify production logic
```

### Validation

```bash
test ! -d bin/docs
grep -n "^bin/$" .gitignore
mvn -q -DskipTests compile
```

---

## COR-003 — docs(topology): realign topology roadmap with repository reality

```text
Commit code    : COR-003
Commit message : docs(topology): realign topology roadmap with repository reality
Type           : Documentation
Layer          : Documentation
Module         : topology
```

### Goal

Update `docs/roadmap/topology.md` so it reflects the actual task sequence and current repository state.

### Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Replace outdated TOP-013 onward status/commit plan with actual TOP-013 through TOP-023 split. |
| `docs/roadmap/topology_validation_checklist.md` | Create or restore the final validation checklist if missing. |

### Required status sequence

```text
TOP-013 db(topology): add topology flyway migration
TOP-014 feat(topology): add topology REST request DTOs
TOP-015 feat(topology): add topology REST response DTOs
TOP-016 feat(topology): add topology REST mapper
TOP-017 feat(topology): add topology REST controllers
TOP-018 test(topology): add topology domain tests
TOP-019 test(topology): add topology application service tests
TOP-020 test(topology): add topology persistence tests
TOP-021 test(topology): add topology REST mapper and controller tests
TOP-022 test(topology): add topology application boot smoke test
TOP-023 docs(topology): finalize topology validation checklist
```

### Validation

```bash
grep -n "TOP-023" docs/roadmap/topology.md
test -f docs/roadmap/topology_validation_checklist.md
```

---

## COR-004 — docs(architecture): define controlled vocabulary policy

```text
Commit code    : COR-004
Commit message : docs(architecture): define controlled vocabulary policy
Type           : Documentation
Layer          : Architecture
Module         : architecture
```

### Goal

Document when to use enums and when to use catalog entities.

### Files to create

| File | Purpose |
|---|---|
| `docs/architecture/controlled-vocabulary-policy.md` | Defines enum-vs-catalog rules for multilingual and configurable business types. |

### Required policy

Enums allowed for:

```text
technical lifecycle states
state-machine statuses
small internal constants not shown as business taxonomy
```

Catalog entities required for:

```text
facility types
organization unit types
pipeline appurtenance types
valve types
node types
equipment types
product types
connection types
reporting line types
operational scope types
any type requiring multilingual labels
```

### Validation

```bash
test -f docs/architecture/controlled-vocabulary-policy.md
```

---

## COR-005 — db(topology): add topology type catalog tables

```text
Commit code    : COR-005
Commit message : db(topology): add topology type catalog tables
Type           : Database
Layer          : Infrastructure
Module         : topology
```

### Goal

Add additive catalog tables without breaking existing topology data.

### Files to create

| File | Purpose |
|---|---|
| `src/main/resources/db/migration/V003__add_topology_type_catalogs.sql` | Adds topology catalog and translation tables. |

### Tables to create

```text
hidra_topology_product_type
hidra_topology_product_type_translation
hidra_topology_facility_type
hidra_topology_facility_type_translation
hidra_topology_node_type
hidra_topology_node_type_translation
hidra_topology_pipeline_appurtenance_type
hidra_topology_pipeline_appurtenance_type_translation
hidra_topology_valve_type
hidra_topology_valve_type_translation
hidra_topology_equipment_type
hidra_topology_equipment_type_translation
hidra_topology_connection_type
hidra_topology_connection_type_translation
```

### Required seed locales

```text
en
fr
ar
```

### Rules

```text
Do not remove old varchar type columns in this task.
Do not drop existing check constraints in this task.
This must be additive only.
```

### Validation

```bash
mvn -q -DskipTests compile
mvn -q -DskipTests flyway:migrate
```

---

## COR-006 — feat(topology): add catalog domain model and references

```text
Commit code    : COR-006
Commit message : feat(topology): add catalog domain model and references
Type           : Feature
Layer          : Domain
Module         : topology
```

### Goal

Add topology catalog domain models and reference value objects while keeping existing enum-based assets untouched temporarily.

### Files to create

```text
TopologyTypeCatalog.java
TopologyTypeTranslation.java
FacilityTypeReference.java
ProductTypeReference.java
NodeTypeReference.java
PipelineAppurtenanceTypeReference.java
ValveTypeReference.java
EquipmentTypeReference.java
ConnectionTypeReference.java
```

### Rules

```text
Do not delete old enums yet.
Do not modify asset models yet.
Do not create REST endpoints in this task.
```

### Validation

```bash
mvn -q -DskipTests compile
```

---

## COR-007 — feat(topology): add catalog application ports and services

```text
Commit code    : COR-007
Commit message : feat(topology): add topology catalog application services
Type           : Feature
Layer          : Application
Module         : topology
```

### Goal

Add use cases for resolving type references and localized labels.

### Files to create

```text
GetTopologyCatalogTypeUseCase
ListTopologyCatalogTypesUseCase
ResolveTopologyCatalogTypeUseCase
TopologyCatalogRepositoryPort
TopologyCatalogApplicationService
TopologyCatalogDto
TopologyCatalogTranslationDto
GetTopologyCatalogTypeQuery
ListTopologyCatalogTypesQuery
ResolveTopologyCatalogTypeQuery
```

### Required behavior

```text
resolve by catalog name + code
resolve by id
list by catalog name + locale
return fallback locale when requested locale is missing
reject inactive type when used for create commands
```

### Validation

```bash
mvn -q -DskipTests compile
```

---

## COR-008 — feat(topology): add catalog persistence adapters

```text
Commit code    : COR-008
Commit message : feat(topology): add topology catalog persistence adapters
Type           : Feature
Layer          : Infrastructure
Module         : topology
```

### Goal

Implement persistence for topology catalog tables.

### Files to create

```text
TopologyCatalogJpaEntity
TopologyCatalogTranslationJpaEntity
TopologyCatalogJpaRepository
TopologyCatalogTranslationJpaRepository
TopologyCatalogPersistenceMapper
TopologyCatalogRepositoryAdapter
```

### Files to update

```text
TopologyConfiguration
```

### Validation

```bash
mvn -q -DskipTests compile
```

---

## COR-009 — refactor(topology): replace enum usage in domain assets with type references

```text
Commit code    : COR-009
Commit message : refactor(topology): replace enum usage in domain assets with type references
Type           : Refactor
Layer          : Domain/Application
Module         : topology
```

### Goal

Refactor asset models and application commands/queries away from enum-based type fields.

### Files to update

```text
Facility
PipelineSystem
Pipeline
TopologyNode
PipelineAppurtenance
TopologyConnection
Equipment
Create*Command records
List*Query records
*Dto records
Application services
Domain policies
Domain services
```

### Replacement examples

```text
FacilityType -> FacilityTypeReference
ProductType -> ProductTypeReference
NodeType -> NodeTypeReference
PipelineAppurtenanceType -> PipelineAppurtenanceTypeReference
ValveType -> ValveTypeReference
EquipmentType -> EquipmentTypeReference
ConnectionType -> ConnectionTypeReference
```

### Rules

```text
Keep TopologyStatus as enum.
Keep TopologyAssetType as enum only if it is purely internal and not displayed as a business taxonomy.
```

### Validation

```bash
mvn -q -DskipTests compile
```

---

## COR-010 — db(topology): migrate topology assets to catalog foreign keys

```text
Commit code    : COR-010
Commit message : db(topology): migrate topology assets to catalog foreign keys
Type           : Database
Layer          : Infrastructure
Module         : topology
```

### Goal

Add type foreign-key columns to topology asset tables and backfill from existing varchar code columns.

### Files to create

| File | Purpose |
|---|---|
| `src/main/resources/db/migration/V004__link_topology_assets_to_type_catalogs.sql` | Adds type FK columns and backfills from catalog code. |

### Example column changes

```text
hidra_topology_facility.facility_type_id
hidra_topology_pipeline_system.product_type_id
hidra_topology_pipeline.product_type_id
hidra_topology_node.node_type_id
hidra_topology_pipeline_appurtenance.appurtenance_type_id
hidra_topology_pipeline_appurtenance.valve_type_id
hidra_topology_equipment.equipment_type_id
hidra_topology_connection.connection_type_id
```

### Rules

```text
Keep old varchar type columns during this task.
Do not drop old CHECK constraints yet.
Backfill every existing row.
Add NOT NULL only after backfill.
```

### Validation

```bash
mvn -q -DskipTests flyway:migrate
```

---

## COR-011 — refactor(topology): update persistence mapping to catalog foreign keys

```text
Commit code    : COR-011
Commit message : refactor(topology): update topology persistence mapping to catalog foreign keys
Type           : Refactor
Layer          : Infrastructure
Module         : topology
```

### Goal

Update JPA entities, repositories, persistence mapper, and adapters to use catalog id/code references.

### Files to update

```text
TopologyPersistenceMapper
FacilityJpaEntity
PipelineSystemJpaEntity
PipelineJpaEntity
TopologyNodeJpaEntity
PipelineAppurtenanceJpaEntity
TopologyConnectionJpaEntity
EquipmentJpaEntity
repository adapters
repository interfaces where filters use types
```

### Rules

```text
Do not expose JPA entity relationships outside infrastructure.
Prefer scalar foreign-key ids unless there is a clear reason for @ManyToOne.
```

### Validation

```bash
mvn -q -DskipTests compile
```

---

## COR-012 — refactor(topology): update REST contracts for catalog types and localization

```text
Commit code    : COR-012
Commit message : refactor(topology): update topology REST contracts for catalog types and localization
Type           : Refactor
Layer          : API
Module         : topology
```

### Goal

Expose catalog references and localized labels in REST.

### Files to update

```text
request DTOs
response DTOs
TopologyRestMapper
controllers
OpenAPI annotations
```

### Request policy

Create/update requests should accept one canonical stable type reference. Recommended first version:

```text
typeCode
```

### Response policy

Responses should return localized type data:

```json
{
  "type": {
    "id": "...",
    "code": "COMPRESSION_STATION",
    "label": "Station de compression",
    "locale": "fr"
  }
}
```

### Localization strategy

Recommended first version:

```text
Accept-Language with fallback to configured default locale
```

### Validation

```bash
mvn -q -DskipTests compile
```

---

## COR-013 — db(topology): remove enum-style CHECK constraints and old type columns

```text
Commit code    : COR-013
Commit message : db(topology): remove topology enum-style type constraints
Type           : Database
Layer          : Infrastructure
Module         : topology
```

### Goal

After code has switched to catalog foreign keys, remove old enum-style columns and check constraints.

### Files to create

| File | Purpose |
|---|---|
| `src/main/resources/db/migration/V005__remove_topology_enum_type_columns.sql` | Drops old type varchar columns and CHECK constraints. |

### Rules

```text
Do not drop status checks.
Do not drop topology lifecycle status columns.
Do not drop neutral organization references.
```

### Validation

```bash
mvn -q -DskipTests flyway:migrate
```

---

## COR-014 — test(topology): repair unstable topology tests

```text
Commit code    : COR-014
Commit message : test(topology): repair unstable topology tests
Type           : Test
Layer          : Test
Module         : topology
```

### Goal

Fix tests that call factory methods multiple times and accidentally compare/query different generated ids.

### Files to update

```text
src/test/java/dz/sh/hidra/modules/topology/domain/TopologyDomainTestData.java
src/test/java/dz/sh/hidra/modules/topology/application/service/*Test.java
src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/*/*Test.java
src/test/java/dz/sh/hidra/modules/topology/api/rest/**/*Test.java
```

### Rules

```text
Store factory output in a local variable before saving and querying.
Do not call factory methods again when the id must match.
Prefer deterministic restore fixtures for tests that require stable ids.
```

### Validation

```bash
mvn -q test -Dtest='*Topology*Test'
```

---

## COR-015 — test(architecture): add enum/catalog guardrails

```text
Commit code    : COR-015
Commit message : test(architecture): add controlled vocabulary guardrails
Type           : Test
Layer          : Architecture Test
Module         : architecture
```

### Goal

Prevent future agents from reintroducing enum-based business taxonomies.

### Files to create

```text
src/test/java/dz/sh/hidra/architecture/ControlledVocabularyArchitectureTest.java
```

### Required rule

Fail when these are Java enums:

```text
*FacilityType
*PipelineAppurtenanceType
*ValveType
*NodeType
*EquipmentType
*ProductType
*ConnectionType
*OrganizationUnitType
*OperationalScopeType
*ReportingLineType
```

Allow enums named:

```text
*Status
*State
*Direction
```

when they represent lifecycle/state-machine behavior.

### Validation

```bash
mvn -q test -Dtest=ControlledVocabularyArchitectureTest
```

---

## COR-016 — refactor(organization): convert organization unit type to catalog entity

```text
Commit code    : COR-016
Commit message : refactor(organization): convert organization unit type to catalog entity
Type           : Refactor
Layer          : Domain/Application/Infrastructure/API
Module         : organization
```

### Goal

Apply the same multilingual catalog pattern to organization unit types.

### Files to update or create

```text
OrganizationUnitTypeCatalog
OrganizationUnitTypeTranslation
OrganizationUnitTypeReference
organization catalog tables and translations
OrganizationUnit domain model
organization commands/DTOs/REST mapper
organization persistence mapper/entities/adapters
organization tests
```

### Rules

```text
Do not touch topology except neutral references if required.
Do not convert status enums.
Do not modify identity.
```

### Validation

```bash
mvn -q test -Dtest='*Organization*Test'
```

---

## COR-017 — docs(topology): update topology roadmap after catalog refactor

```text
Commit code    : COR-017
Commit message : docs(topology): update topology roadmap after catalog refactor
Type           : Documentation
Layer          : Documentation
Module         : topology
```

### Goal

Update `docs/roadmap/topology.md` to show that topology was corrected from enum-based type modeling to catalog-based multilingual type modeling.

### Files to update

```text
docs/roadmap/topology.md
docs/roadmap/topology_validation_checklist.md
```

### Validation

```bash
grep -n "catalog" docs/roadmap/topology.md
grep -n "multilingual" docs/roadmap/topology.md
```

---

## COR-018 — test(stabilization): run full corrected baseline

```text
Commit code    : COR-018
Commit message : test(stabilization): validate corrected baseline
Type           : Test
Layer          : Stabilization
Module         : stabilization
```

### Goal

Run the full baseline after all corrections.

### Validation commands

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q test -Dtest=TopologyApplicationBootSmokeTest
```

### Expected result

```text
BUILD SUCCESS
```

### Completion criteria

```text
no compilation errors
no failing unit tests
boot smoke test passes with Docker/Testcontainers
Flyway migrations apply cleanly
no enum-based business taxonomy remains
roadmaps match repository reality
```

---

# 5. Next module gate

Do not start measurement until all correction tasks through COR-018 pass.

The first measurement task should be:

```text
MES-001 — docs(measurement): add measurement implementation roadmap
```

but only after this gate is true:

```text
COR-001 through COR-018 complete
mvn -q test passes
Topology type catalogs support multilingual labels
Topology REST responses can expose localized type labels
Topology asset tables use catalog foreign keys
Organization unit type strategy is corrected or explicitly deferred with a documented risk
```

---

# 6. Practical implementation order

```text
1. COR-001
2. COR-002
3. COR-003
4. COR-004
5. COR-005
6. COR-006
7. COR-007
8. COR-008
9. COR-009
10. COR-010
11. COR-011
12. COR-012
13. COR-013
14. COR-014
15. COR-015
16. COR-016
17. COR-017
18. COR-018
```

Follow AGENTS.md: one commit code per task, no automatic continuation.

---

# 7. Remaining risks

```text
This roadmap was produced by static repository analysis; Maven was not executed by this roadmap task.
The exact multilingual labels must be confirmed by business users, especially Arabic terminology.
Existing deployed databases may already have V002 applied, so V002 must not be edited; use additive V003/V004/V005 migrations.
Converting type enums to catalogs will touch domain, application, persistence, REST, tests, and documentation.
The correction should happen before measurement to prevent propagating bad contracts.
```
