# HidraAPI Topology Roadmap — Repository Reality After Catalog Refactor

```text
Roadmap file : docs/roadmap/topology.md
Roadmap code : TOP
Scope        : Topology bounded context for physical hydrocarbon network assets
Repository   : HidraAPI
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-06-06
Status       : Realigned after TOP-023 and updated after topology catalog refactor corrections
```

---

## 1. Purpose

This roadmap is the current execution record for the `topology` module in the HidraAPI repository.

The topology module owns the **physical hydrocarbon network structure** used by later Hidra modules.

Topology covers:

```text
pipeline systems
pipelines
pipeline segments
topology nodes
physical facilities
terminals
processing plants
production field interfaces
pipeline point assets / appurtenances
valves along pipelines
injection points
extraction points
purge points
vents
drains
scraper launchers and receivers
equipment references
physical network connectivity
operational topology status
```

Topology is required before:

```text
measurement
operations
flow calculations
risk assessments
analytics
workflow
reporting
notifications
```

---

## 2. Repository reality after TOP-023

The repository contains topology production code, REST code, persistence code, migration code, tests, and the application boot smoke test.

The authoritative completed TOP sequence is:

| Code | Commit message | Repository status | Notes |
|---|---|---:|---|
| `TOP-001` | `docs(topology): add topology implementation roadmap` | Completed | Initial topology roadmap. |
| `TOP-002` | `chore(topology): add topology package skeleton` | Completed | Production package skeleton. |
| `TOP-003` | `feat(topology): add topology domain value objects` | Completed | Domain identifiers, values, and initial controlled vocabularies. |
| `TOP-004` | `feat(topology): add topology domain models` | Completed | Physical topology aggregates/entities. |
| `TOP-005` | `feat(topology): add topology domain policies` | Completed | Domain validation policies. |
| `TOP-006` | `feat(topology): add topology domain services` | Completed | Domain orchestration services. |
| `TOP-007` | `feat(topology): add topology application commands and queries` | Completed | Application input records. |
| `TOP-008` | `feat(topology): add topology application DTOs` | Completed | Application output records. |
| `TOP-009` | `feat(topology): add topology application ports` | Completed | Inbound and outbound ports. |
| `TOP-010` | `feat(topology): add topology application services` | Completed | Application services and wiring. |
| `TOP-011` | `feat(topology): add topology persistence entities and repositories` | Completed | JPA entities and Spring Data repository interfaces. |
| `TOP-012` | `feat(topology): add topology persistence mapper and adapters` | Completed | Persistence mapper and outbound adapters. |
| `TOP-013` | `db(topology): add topology flyway migration` | Completed | `V002__create_topology_tables.sql`. |
| `TOP-014` | `feat(topology): add topology REST request DTOs` | Completed | Request DTO contracts. |
| `TOP-015` | `feat(topology): add topology REST response DTOs` | Completed | Response DTO contracts. |
| `TOP-016` | `feat(topology): add topology REST mapper` | Completed | API mapper and mapper bean configuration. |
| `TOP-017` | `feat(topology): add topology REST controllers` | Completed | REST controllers for implemented topology use cases. |
| `TOP-018` | `test(topology): add topology domain tests` | Completed | Domain unit tests. |
| `TOP-019` | `test(topology): add topology application service tests` | Completed | Application service unit tests. |
| `TOP-020` | `test(topology): add topology persistence tests` | Completed | Persistence mapper/entity/repository/adapter tests. |
| `TOP-021` | `test(topology): add topology REST mapper and controller tests` | Completed | REST mapper/controller tests. |
| `TOP-022` | `test(topology): add topology application boot smoke test` | Completed | Spring Boot + PostgreSQL Testcontainers topology smoke test. |
| `TOP-023` | `docs(topology): finalize topology validation checklist` | Completed | `docs/roadmap/topology_validation_checklist.md`. |

---

## 3. Catalog correction reality after COR-013

A repository analysis found that topology business type concepts were initially implemented as Java enums and database `CHECK` constraints.

The topology correction path has now been applied through the catalog refactor tasks:

| Code | Commit message | Repository status | Notes |
|---|---|---:|---|
| `COR-004` | `docs(architecture): define controlled vocabulary policy` | Completed | Defines enum-vs-catalog policy. |
| `COR-005` | `db(topology): add topology type catalog tables` | Completed | Adds multilingual topology catalog and translation tables in V003. |
| `COR-006` | `feat(topology): add catalog domain model and references` | Completed | Adds catalog domain model and type reference value objects. |
| `COR-007` | `feat(topology): add topology catalog application services` | Completed | Adds catalog application DTOs, queries, ports, and service. |
| `COR-008` | `feat(topology): add topology catalog persistence adapters` | Completed | Adds catalog persistence adapter and configuration wiring. |
| `COR-009` | `refactor(topology): replace enum usage in domain assets with type references` | Completed | Domain/application assets use catalog reference value objects. |
| `COR-010` | `db(topology): migrate topology assets to catalog foreign keys` | Completed | Adds and backfills catalog FK columns in V004. |
| `COR-011` | `refactor(topology): update topology persistence mapping to catalog foreign keys` | Completed | Persistence writes/reads catalog FK references. |
| `COR-012` | `refactor(topology): update topology REST contracts for catalog types and localization` | Completed | REST requests use `typeCode`; responses expose localized type labels. |
| `COR-013` | `db(topology): remove topology enum-style type constraints` | Completed | Removes old varchar taxonomy columns and old enum-style checks in V005. |

Topology business taxonomy concepts are no longer roadmap-accepted as Java enums.

The following topology concepts are catalog-backed controlled vocabularies:

```text
Product type
Facility type
Topology node type
Pipeline appurtenance type
Valve type
Equipment type
Connection type
```

Lifecycle/status concepts remain technical enums where appropriate.

---

## 4. Final topology data conception

Topology is a physical network graph.

```text
TopologyNode         = graph vertex / physical connection point
PipelineSegment      = linear pipe asset between two nodes
TopologyConnection   = explicit graph edge between two nodes
Facility             = physical site/facility connected to the network
PipelineAppurtenance = physical point asset installed along a pipeline
Equipment            = optional physical component reference attached to a topology asset
```

Final topology entities:

```text
PipelineSystem
Pipeline
PipelineSegment
TopologyNode
Facility
PipelineAppurtenance
TopologyConnection
Equipment
TopologyTypeCatalog
TopologyTypeTranslation
```

Do **not** use `Station` as the main physical asset model. Use `Facility`.

Reason:

```text
compression stations, pumping stations, metering stations, terminals, processing plants,
production field interfaces, gathering centers, storage facilities, delivery facilities,
and receipt facilities are all physical facilities.
```

Business type concepts are modeled as catalog references with stable codes and localized labels, not as fixed Java enum fields.

---

## 5. Boundary rules

Topology owns physical network structure only.

Topology does **not** own:

```text
employees
positions
assignments
reporting lines
users
roles
permissions
SCADA values
measurement time series
flow calculations
risk scoring
workflow approvals
work permits
production allocation
commercial nominations
terminal loading operations
plant process simulation
reservoir engineering
well data
```

Topology may reference organization only through neutral references:

```text
OrganizationUnitReference
OperationalOwnerReference
```

Topology must not import organization or identity implementation classes.

Forbidden lateral module dependencies from topology:

```text
dz.sh.hidra.modules.identity.*
dz.sh.hidra.modules.organization.*
dz.sh.hidra.modules.measurement.*
dz.sh.hidra.modules.operations.*
dz.sh.hidra.modules.flow.*
dz.sh.hidra.modules.risk.*
dz.sh.hidra.modules.analytics.*
dz.sh.hidra.modules.workflow.*
dz.sh.hidra.modules.reporting.*
dz.sh.hidra.modules.notification.*
```

---

## 6. REST endpoint reality after catalog refactor

Expected endpoint groups:

```text
/api/v1/topology/pipeline-systems
/api/v1/topology/pipelines
/api/v1/topology/facilities
/api/v1/topology/nodes
/api/v1/topology/pipeline-segments
/api/v1/topology/pipeline-appurtenances
/api/v1/topology/connections
/api/v1/topology/equipment
```

Expected operation shape:

| Asset group | Operations exposed |
|---|---|
| pipeline systems | create, get, list |
| pipelines | create, get, list |
| facilities | create, get, list |
| topology nodes | create, get, list |
| pipeline segments | create, list |
| pipeline appurtenances | create, get, list |
| topology connections | create, list |
| equipment | register |

REST create/list requests use stable catalog codes, for example:

```text
productTypeCode
facilityTypeCode
nodeTypeCode
appurtenanceTypeCode
valveTypeCode
connectionTypeCode
equipmentTypeCode
```

REST responses expose localized type references:

```json
{
  "id": "topology-ft-compression-station",
  "code": "COMPRESSION_STATION",
  "label": "Station de compression",
  "locale": "fr"
}
```

Localization is driven by `Accept-Language` with fallback to the configured default locale.

Do not add unsupported get/list/update/delete endpoints unless a later roadmap explicitly adds the matching application use cases.

---

## 7. Database reality after catalog refactor

Topology migrations:

```text
V002__create_topology_tables.sql
V003__add_topology_type_catalogs.sql
V004__link_topology_assets_to_type_catalogs.sql
V005__remove_topology_enum_type_columns.sql
```

Expected topology-owned asset table groups:

```text
hidra_topology_pipeline_system
hidra_topology_pipeline
hidra_topology_facility
hidra_topology_node
hidra_topology_pipeline_segment
hidra_topology_pipeline_appurtenance
hidra_topology_connection
hidra_topology_equipment
```

Expected topology catalog table groups:

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

Current database rules:

```text
asset tables use catalog foreign-key ids for business taxonomy fields
catalog translation tables support localized labels
neutral owner/reference columns do not create foreign keys to organization tables
status fields exist for lifecycle control
old enum-style varchar taxonomy columns are removed by V005
old enum-style taxonomy CHECK constraints are removed by V005
```

The migration path must not alter identity, organization, platform, kernel, or unrelated module tables.

---

## 8. Validation commands

Run from the repository root.

### 8.1 File existence

Linux/macOS/Git Bash:

```bash
test -f docs/roadmap/topology.md
test -f docs/roadmap/topology_validation_checklist.md
test -f docs/roadmap/correction_01.md
test -f docs/architecture/controlled-vocabulary-policy.md
test -f src/main/resources/db/migration/V002__create_topology_tables.sql
test -f src/main/resources/db/migration/V003__add_topology_type_catalogs.sql
test -f src/main/resources/db/migration/V004__link_topology_assets_to_type_catalogs.sql
test -f src/main/resources/db/migration/V005__remove_topology_enum_type_columns.sql
test -d src/main/java/dz/sh/hidra/modules/topology
test -d src/test/java/dz/sh/hidra/modules/topology
```

Windows PowerShell:

```powershell
Test-Path docs/roadmap/topology.md
Test-Path docs/roadmap/topology_validation_checklist.md
Test-Path docs/roadmap/correction_01.md
Test-Path docs/architecture/controlled-vocabulary-policy.md
Test-Path src/main/resources/db/migration/V002__create_topology_tables.sql
Test-Path src/main/resources/db/migration/V003__add_topology_type_catalogs.sql
Test-Path src/main/resources/db/migration/V004__link_topology_assets_to_type_catalogs.sql
Test-Path src/main/resources/db/migration/V005__remove_topology_enum_type_columns.sql
Test-Path src/main/java/dz/sh/hidra/modules/topology
Test-Path src/test/java/dz/sh/hidra/modules/topology
```

### 8.2 Roadmap catalog wording

```bash
grep -n "catalog" docs/roadmap/topology.md
grep -n "multilingual" docs/roadmap/topology.md
```

### 8.3 Compile and test

```bash
mvn -q -DskipTests compile
mvn -q test -Dtest='*Topology*Test'
mvn -q test -Dtest=ControlledVocabularyArchitectureTest
mvn -q test -Dtest=TopologyApplicationBootSmokeTest
mvn -q test
```

The boot smoke test requires Docker/Testcontainers.

---

## 9. Next gate

Do not start:

```text
MES-001 — docs(measurement): add measurement implementation roadmap
```

until the corrected baseline is validated:

```text
COR-018 complete
mvn -q test passes
Flyway migrations apply cleanly
ControlledVocabularyArchitectureTest passes
Topology type catalogs support multilingual labels
Topology REST responses expose localized type labels
Topology asset tables use catalog foreign keys
```

---

## 10. Current next recommended task

```text
COR-018 — test(stabilization): validate corrected baseline
```
