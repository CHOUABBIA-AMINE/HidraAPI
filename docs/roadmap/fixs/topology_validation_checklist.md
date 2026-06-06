# Topology Module Validation Checklist — After Catalog Refactor

```text
Roadmap file : docs/roadmap/topology_validation_checklist.md
Related file : docs/roadmap/topology.md
Roadmap code : TOP
Scope        : Final validation gate for the topology module after TOP-023 and topology catalog corrections
Repository   : HidraAPI
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-06-06
Status       : Updated by COR-017 after topology catalog refactor
```

---

## 1. Purpose

This checklist validates the topology module after the original TOP implementation and the topology catalog correction path.

Expected completed ranges:

```text
TOP-001 through TOP-023
COR-004 through COR-013 for topology catalog correction
COR-014 for topology test repair
COR-015 for controlled vocabulary guardrails
COR-017 for documentation realignment after the catalog refactor
```

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
multilingual controlled vocabularies for topology business types
```

Topology does not own measurement, flow calculation, risk, workflow, analytics, reporting, notification, identity, or organization behavior.

---

## 3. Expected files

Roadmap and architecture files:

```text
docs/roadmap/topology.md
docs/roadmap/topology_validation_checklist.md
docs/roadmap/correction_01.md
docs/architecture/controlled-vocabulary-policy.md
```

Topology migrations:

```text
src/main/resources/db/migration/V002__create_topology_tables.sql
src/main/resources/db/migration/V003__add_topology_type_catalogs.sql
src/main/resources/db/migration/V004__link_topology_assets_to_type_catalogs.sql
src/main/resources/db/migration/V005__remove_topology_enum_type_columns.sql
```

Topology source roots:

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

## 5. Expected completed topology catalog correction sequence

```text
COR-004  docs(architecture): define controlled vocabulary policy
COR-005  db(topology): add topology type catalog tables
COR-006  feat(topology): add catalog domain model and references
COR-007  feat(topology): add topology catalog application services
COR-008  feat(topology): add topology catalog persistence adapters
COR-009  refactor(topology): replace enum usage in domain assets with type references
COR-010  db(topology): migrate topology assets to catalog foreign keys
COR-011  refactor(topology): update topology persistence mapping to catalog foreign keys
COR-012  refactor(topology): update topology REST contracts for catalog types and localization
COR-013  db(topology): remove topology enum-style type constraints
COR-014  test(topology): repair unstable topology tests
COR-015  test(architecture): add controlled vocabulary guardrails
COR-017  docs(topology): update topology roadmap after catalog refactor
```

---

## 6. Boundary validation

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

## 7. Catalog validation

Topology business type concepts must be catalog-backed controlled vocabularies, not Java enums accepted by the roadmap.

Expected topology catalog reference concepts:

```text
ProductTypeReference
FacilityTypeReference
NodeTypeReference
PipelineAppurtenanceTypeReference
ValveTypeReference
EquipmentTypeReference
ConnectionTypeReference
```

Expected catalog domain/application/infrastructure concepts:

```text
TopologyTypeCatalog
TopologyTypeTranslation
TopologyCatalogDto
TopologyCatalogTranslationDto
TopologyCatalogApplicationService
TopologyCatalogRepositoryPort
TopologyCatalogRepositoryAdapter
TopologyCatalogPersistenceMapper
```

Expected REST type response shape:

```json
{
  "id": "topology-ft-compression-station",
  "code": "COMPRESSION_STATION",
  "label": "Station de compression",
  "locale": "fr"
}
```

Expected localization behavior:

```text
Accept-Language is used for localized type labels.
A default locale fallback is used when the requested locale is unavailable.
Create/list requests use stable typeCode fields.
Responses expose localized type reference objects.
```

---

## 8. REST endpoint checklist

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

## 9. Database checklist

Expected topology-owned asset tables:

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

Expected topology catalog and translation tables:

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

Expected database rules:

```text
asset tables use catalog foreign-key ids for business taxonomy fields
catalog translation tables support localized labels
neutral owner/reference columns do not create foreign keys to organization tables
timestamps exist for audit baseline
status fields exist for lifecycle control
old varchar taxonomy columns are removed by V005
old enum-style taxonomy CHECK constraints are removed by V005
```

---

## 10. Validation commands

Run from repository root.

### 10.1 File existence

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

### 10.2 Roadmap catalog wording

```bash
grep -n "catalog" docs/roadmap/topology.md
grep -n "multilingual" docs/roadmap/topology.md
```

### 10.3 Maven validation

```bash
mvn -q -DskipTests compile
mvn -q test -Dtest='*Topology*Test'
mvn -q test -Dtest=ControlledVocabularyArchitectureTest
mvn -q test -Dtest=TopologyApplicationBootSmokeTest
mvn -q test
```

The boot smoke test requires Docker/Testcontainers because it starts PostgreSQL.

---

## 11. Acceptance criteria

This checklist is accepted when:

```text
docs/roadmap/topology.md exists
docs/roadmap/topology_validation_checklist.md exists
docs/roadmap/correction_01.md exists
docs/architecture/controlled-vocabulary-policy.md exists
TOP-013 through TOP-023 are represented as completed in topology.md
COR-004 through COR-013 are represented as completed for topology catalog correction
topology_validation_checklist.md documents REST, DB, tests, boundaries, and catalog correction reality
topology.md contains catalog and multilingual wording
no Java production code changed for COR-017
no Java test code changed for COR-017
no Flyway migration changed for COR-017
```

---

## 12. Next gate

Do not start measurement yet.

Continue with:

```text
COR-018 — test(stabilization): validate corrected baseline
```
