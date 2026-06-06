# Topology Module Validation Checklist

```text
Roadmap file : docs/roadmap/topology_validation_checklist.md
Related file : docs/roadmap/topology.md
Roadmap code : TOP
Scope        : Final validation gate for the topology module after TOP-023
Repository   : HidraAPI
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-06-06
Status       : Restored by COR-003
```

---

## 1. Purpose

This checklist is the final validation gate for the topology module after completing:

```text
TOP-001 through TOP-023
```

It is written for a future AI agent or developer who must validate the implemented topology module before starting the next business module.

---

## 2. Topology scope

Topology owns physical hydrocarbon network master data:

```text
pipeline systems
pipelines
pipeline segments
physical facilities
terminals
production field interfaces
processing plants
topology nodes
pipeline appurtenances
injection points
extraction points
purge points
vents
drains
valves and valve subtypes
topology connections
equipment attached to topology assets
```

Topology does not own measurement, flow calculation, risk, workflow, analytics, reporting, notification, identity, or organization behavior.

---

## 3. Expected repository files

Expected roadmap files:

```text
docs/roadmap/topology.md
docs/roadmap/topology_validation_checklist.md
docs/roadmap/correction_01.md
```

Expected topology migration:

```text
src/main/resources/db/migration/V002__create_topology_tables.sql
```

Expected topology source roots:

```text
src/main/java/dz/sh/hidra/modules/topology
src/test/java/dz/sh/hidra/modules/topology
```

---

## 4. Expected completed topology task sequence

```text
TOP-001  docs(topology): add topology implementation roadmap
TOP-002  chore(topology): add topology package skeleton
TOP-003  feat(topology): add topology domain value objects
TOP-004  feat(topology): add topology domain models
TOP-005  feat(topology): add topology domain policies
TOP-006  feat(topology): add topology domain services
TOP-007  feat(topology): add topology application commands and queries
TOP-008  feat(topology): add topology application DTOs
TOP-009  feat(topology): add topology application ports
TOP-010  feat(topology): add topology application services
TOP-011  feat(topology): add topology persistence entities and repositories
TOP-012  feat(topology): add topology persistence mapper and adapters
TOP-013  db(topology): add topology flyway migration
TOP-014  feat(topology): add topology REST request DTOs
TOP-015  feat(topology): add topology REST response DTOs
TOP-016  feat(topology): add topology REST mapper
TOP-017  feat(topology): add topology REST controllers
TOP-018  test(topology): add topology domain tests
TOP-019  test(topology): add topology application service tests
TOP-020  test(topology): add topology persistence tests
TOP-021  test(topology): add topology REST mapper and controller tests
TOP-022  test(topology): add topology application boot smoke test
TOP-023  docs(topology): finalize topology validation checklist
```

---

## 5. Boundary validation

Allowed topology package roots:

```text
src/main/java/dz/sh/hidra/modules/topology/domain
src/main/java/dz/sh/hidra/modules/topology/application
src/main/java/dz/sh/hidra/modules/topology/infrastructure
src/main/java/dz/sh/hidra/modules/topology/api
src/test/java/dz/sh/hidra/modules/topology
```

Forbidden package roots under topology:

```text
identityaccess
shared
sharedkernel
common
core
utils
helper
helpers
misc
```

Forbidden lateral imports from topology:

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

Topology may reference organization ownership only through neutral scalar/reference objects such as:

```text
OrganizationUnitReference
OperationalOwnerReference
```

---

## 6. REST endpoint checklist

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

Do not add unsupported get/list/update/delete endpoints unless a later roadmap adds the corresponding application use case.

---

## 7. Database checklist

Expected topology-owned tables:

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

Expected database rules:

```text
codes are unique where required
foreign-key relationships stay inside topology tables
neutral owner/reference columns do not create foreign keys to organization tables
timestamps exist for audit baseline
status fields exist for lifecycle control
```

---

## 8. Known correction issue: business types currently need catalog refactor

The current topology baseline uses Java enums and database check constraints for multiple business type concepts.

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

This is not final for multilingual operation.

These concepts must become module-owned catalog/reference entities with localized labels in the correction roadmap.

Relevant correction tasks:

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

Until these corrections are complete, topology is structurally implemented but not ready for multilingual measurement-module dependency.

---

## 9. Validation commands

Run from repository root.

### 9.1 File existence

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

### 9.2 Forbidden lateral imports

Linux/macOS/Git Bash:

```bash
grep -R "dz\.sh\.hidra\.modules\.\(identity\|organization\|measurement\|operations\|flow\|risk\|analytics\|workflow\|reporting\|notification\)" \
  src/main/java/dz/sh/hidra/modules/topology src/test/java/dz/sh/hidra/modules/topology \
  && exit 1 || exit 0
```

Windows PowerShell:

```powershell
Select-String -Path src/main/java/dz/sh/hidra/modules/topology/**/*.java,src/test/java/dz/sh/hidra/modules/topology/**/*.java `
  -Pattern 'dz\.sh\.hidra\.modules\.(identity|organization|measurement|operations|flow|risk|analytics|workflow|reporting|notification)' `
  -ErrorAction SilentlyContinue
```

Expected result:

```text
no matches
```

### 9.3 Maven validation

```bash
mvn -q -DskipTests compile
mvn -q test -Dtest='*Topology*Test'
mvn -q test -Dtest=TopologyApplicationBootSmokeTest
mvn -q test
```

The boot smoke test requires Docker/Testcontainers because it starts PostgreSQL.

---

## 10. Acceptance criteria

This checklist is accepted when:

```text
docs/roadmap/topology.md exists
docs/roadmap/topology_validation_checklist.md exists
docs/roadmap/correction_01.md exists
TOP-013 through TOP-023 are represented as completed in topology.md
topology_validation_checklist.md documents REST, DB, tests, boundaries, and correction risks
no Java production code changed for COR-003
no Java test code changed for COR-003
no Flyway migration changed for COR-003
```

---

## 11. Next gate

Do not start measurement yet.

Continue with:

```text
COR-004 — docs(architecture): define controlled vocabulary policy
```
