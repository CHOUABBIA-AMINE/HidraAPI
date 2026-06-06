# HidraAPI Topology Roadmap — Repository Reality

```text
Roadmap file : docs/roadmap/topology.md
Roadmap code : TOP
Scope        : Topology bounded context for physical hydrocarbon network assets
Repository   : HidraAPI
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-06-06
Status       : Realigned with repository reality after TOP-023
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

The old roadmap text still described several implemented tasks as planned. This file is now realigned so future agents do not repeat or mis-order topology work.

The authoritative completion sequence is:

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
| `TOP-023` | `docs(topology): finalize topology validation checklist` | Completed by COR-003 realignment | `docs/roadmap/topology_validation_checklist.md` is restored/created by COR-003. |

---

## 3. Final topology data conception

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
```

Do **not** use `Station` as the main physical asset model. Use `Facility`.

Reason:

```text
compression stations, pumping stations, metering stations, terminals, processing plants,
production field interfaces, gathering centers, storage facilities, delivery facilities,
and receipt facilities are all physical facilities.
```

---

## 4. Boundary rules

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

## 5. REST endpoint reality

Expected endpoint groups after TOP-017:

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

Do not add unsupported get/list/update/delete endpoints unless a later roadmap explicitly adds the matching application use cases.

---

## 6. Database reality

Expected topology migration:

```text
src/main/resources/db/migration/V002__create_topology_tables.sql
```

Expected topology-owned table groups:

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

The migration must not alter identity, organization, platform, kernel, or unrelated module tables.

---

## 7. Known correction after repository analysis

A later repository analysis identified that several business type concepts were implemented as Java enums and database check constraints.

Examples:

```text
FacilityType
PipelineAppurtenanceType
ValveType
NodeType
EquipmentType
ConnectionType
ProductType
```

These were acceptable for the initial topology baseline, but they are **not sufficient for multilingual, configurable business taxonomies**.

The correction is intentionally **not performed in COR-003**.

The correction belongs to the dedicated correction roadmap:

```text
docs/roadmap/correction_01.md
```

Relevant future correction tasks:

```text
COR-004 — docs(architecture): define controlled vocabulary policy
COR-005 — db(topology): add topology type catalog tables
COR-006 — feat(topology): add catalog domain model and references
COR-007 — feat(topology): add catalog application services
COR-008 — feat(topology): add catalog persistence adapters
COR-009 — refactor(topology): replace enum usage in domain assets with type references
COR-010 — db(topology): migrate topology assets to catalog foreign keys
COR-011 — refactor(topology): update persistence mapping to catalog foreign keys
COR-012 — refactor(topology): update REST contracts for catalog types and localization
COR-013 — db(topology): remove enum-style type constraints
```

Until those corrections are complete, do not start measurement implementation.

---

## 8. Validation commands

Run from the repository root.

### 8.1 File existence

Linux/macOS/Git Bash:

```bash
test -f docs/roadmap/topology.md
test -f docs/roadmap/topology_validation_checklist.md
test -f docs/roadmap/correction_01.md
test -f src/main/resources/db/migration/V002__create_topology_tables.sql
test -d src/main/java/dz/sh/hidra/modules/topology
test -d src/test/java/dz/sh/hidra/modules/topology
```

Windows PowerShell:

```powershell
Test-Path docs/roadmap/topology.md
Test-Path docs/roadmap/topology_validation_checklist.md
Test-Path docs/roadmap/correction_01.md
Test-Path src/main/resources/db/migration/V002__create_topology_tables.sql
Test-Path src/main/java/dz/sh/hidra/modules/topology
Test-Path src/test/java/dz/sh/hidra/modules/topology
```

### 8.2 Compile and test

```bash
mvn -q -DskipTests compile
mvn -q test -Dtest='*Topology*Test'
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

until the correction roadmap has completed at least the topology catalog correction path and the full baseline passes:

```text
COR-001 through COR-018 complete
mvn -q test passes
Topology type catalogs support multilingual labels
Topology REST responses can expose localized type labels
Topology asset tables use catalog foreign keys
```

---

## 10. Current next recommended task

```text
COR-004 — docs(architecture): define controlled vocabulary policy
```
