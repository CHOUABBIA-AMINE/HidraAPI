# HidraAPI Topology Roadmap — Final Conception

```text
Roadmap file : docs/roadmap/topology.md
Roadmap code : TOP
Scope        : Implement the topology bounded context for physical hydrocarbon network assets
Repository   : HidraAPI
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-06-05
Status       : Final conception before implementation
```

---

## 1. Purpose

This roadmap defines the implementation plan for the `topology` module.

The `topology` module owns the **physical hydrocarbon network structure** used by future HidraAPI modules:

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

Topology is the next module after stabilization because future modules need stable physical network references before they can safely model:

```text
measurements
operations
flow calculations
risk assessments
analytics
workflow
reporting
notifications
```

---

## 2. Final data conception

The final topology conception is:

```text
Topology is a physical network graph.

TopologyNode = graph vertex / physical connection point.
PipelineSegment = linear pipe asset between two nodes.
TopologyConnection = explicit graph edge between two nodes.
Facility = physical site/facility connected to the network.
PipelineAppurtenance = physical point asset installed along a pipeline.
Equipment = optional physical component reference attached to a topology asset.
```

Final core entities:

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

Do **not** use `Station` as the main model name. Use `Facility`.

Reason:

```text
compression stations, pumping stations, terminals, processing plants, production field interfaces,
gathering centers, storage facilities, and delivery/receipt facilities are all physical facilities.
```

---

## 3. Business meaning of topology

Topology answers:

```text
Where is the hydrocarbon asset?
How are physical assets connected?
Which pipeline, segment, facility, node, valve, injection point, purge point, or extraction point is involved?
Which facility connects to which pipeline?
Which point asset exists at which pipeline kilometer point?
Which physical network path exists between assets?
Which assets are active, inactive, planned, retired, or under maintenance?
```

Topology does **not** answer:

```text
Who works there?
Who manages the team?
Who reports to whom?
Who has access?
What SCADA values were captured?
What flow calculation result was produced?
What risk score exists?
What operation/permit/workflow is pending?
What production volume was allocated?
```

Those questions belong to other modules.

---

## 4. Strong boundary rules

The AI agent must obey these rules for every topology task:

```text
1. Read AGENTS.md first.
2. Read docs/roadmap/stabilization_01.md to confirm foundation status.
3. Read docs/roadmap/kernel.md to understand kernel status and constraints.
4. Read docs/roadmap/platform.md to understand platform status and constraints.
5. Read docs/roadmap/identity.md to understand identity boundaries.
6. Read docs/roadmap/organization.md to understand organization boundaries.
7. Do not create identityaccess.
8. Do not modify kernel files unless the current topology task explicitly says so.
9. Do not modify platform files unless the current topology task explicitly says so.
10. Do not modify identity files.
11. Do not modify organization files unless the current topology task explicitly says so.
12. Do not create measurement, operations, flow, risk, analytics, workflow, reporting, or notification modules.
13. Do not create shared, sharedkernel, common, core, utils, helper, helpers, or misc packages.
14. Do not put topology classes under kernel or platform.
15. Do not put organization classes under topology.
16. Do not put identity users, roles, permissions, or access policies in topology.
17. Do not put telemetry/SCADA time-series values in topology.
18. Do not put hydraulic calculation behavior in topology.
19. Do not put risk scoring behavior in topology.
20. Do not put workflow or approval behavior in topology.
21. Every Java file must use the canonical HidraAPI Java header.
22. Keep @Author as Abir MEDJERAB.
23. Keep @CreatedOn as 2025-06-26.
24. Set @Module to topology.
25. Set @Package to the actual package name.
26. Set @Layer according to the package: API, Application, Domain, or Infrastructure.
27. Use Bean Validation annotations on request DTO fields.
28. Use Swagger/OpenAPI @Schema on request and response DTO fields.
29. Use @Tag, @Operation, and @ApiResponses on REST controllers.
30. Keep controllers thin.
31. Controllers must depend only on application inbound ports and REST mappers.
32. Application services must depend on domain services/policies and outbound ports.
33. Domain model must not depend on Spring, JPA, REST DTOs, platform, identity, organization implementation, or database classes.
34. Infrastructure adapters must implement topology outbound ports.
35. Persistence entities must not contain business behavior.
36. Mappers must stay deterministic and side-effect free.
37. Tests must be added at the same architectural level as the implemented behavior.
38. Run validation commands required by each task.
39. Update the final status table in this roadmap after each task.
40. Commit only the files listed by the current task.
41. Do not execute later tasks unless explicitly requested.
```

---

## 5. Critical organization/topology distinction

This distinction is mandatory:

```text
organization owns facility/station as organization unit / team
topology owns facility/station as physical asset
```

Example:

```text
Compression Station East 01 as a physical installation with facility code, location,
inlet/outlet nodes, pipeline connections, valves, purge points, and equipment references -> topology

Compression Station East 01 as an operational team with employees, positions,
assignments, reporting lines, and managers -> organization
```

Topology may reference organization only through neutral references:

```text
OrganizationUnitReference
OperationalOwnerReference
```

Topology must not import:

```text
organization domain models
organization entities
organization repositories
organization services
identity users
identity roles
identity permissions
```

---

## 6. Facility conception

Use `Facility`, not `Station`, as the main physical site model.

Table:

```text
hidra_topology_facility
```

Facility examples:

```text
compression station
pumping station
metering station
valve station
terminal
processing plant
production field interface
gathering center
storage facility
delivery facility
receipt facility
dispatching center
```

Recommended enum:

```java
public enum FacilityType {
    COMPRESSION_STATION,
    PUMPING_STATION,
    METERING_STATION,
    VALVE_STATION,
    TERMINAL,
    PROCESSING_PLANT,
    PRODUCTION_FIELD,
    GATHERING_CENTER,
    STORAGE_FACILITY,
    DELIVERY_FACILITY,
    RECEIPT_FACILITY,
    DISPATCHING_CENTER,
    OTHER
}
```

Topology owns only the physical facility identity, location, status, and graph connection points.

Topology does not own terminal loading, inventory, plant process calculations, reservoir/well details, or production accounting.

---

## 7. Terminal, processing plant, and production field handling

### Terminal

Represent as:

```text
Facility
facility_type = TERMINAL
```

Topology owns:

```text
terminal physical identity
terminal code/name/type/status
terminal location
terminal inlet/outlet/metering nodes
terminal connection to pipelines
terminal equipment references
```

Topology does not own:

```text
ship loading operations
cargo scheduling
commercial nominations
tank inventory accounting
```

### Processing plant

Represent as:

```text
Facility
facility_type = PROCESSING_PLANT
```

Topology owns:

```text
plant physical identity
plant physical location
feed/product outlet topology nodes
connection to pipelines
equipment references
```

Topology does not own:

```text
process simulation
separation logic
yield calculation
gas treatment chemistry
plant production accounting
```

### Production field

Represent as:

```text
Facility
facility_type = PRODUCTION_FIELD
```

Meaning:

```text
Production field is modeled only as a physical source/interface to the pipeline network.
```

Topology owns:

```text
field interface identity
field export/receipt topology nodes
connection to gathering lines, processing plants, or main pipelines
```

Topology does not own:

```text
reservoirs
wells
well tests
drilling
completion
well production allocation
reservoir engineering
```

---

## 8. Pipeline appurtenance conception

Use `PipelineAppurtenance` for physical point assets installed along a pipeline.

Table:

```text
hidra_topology_pipeline_appurtenance
```

Examples:

```text
block valve
sectionalizing valve
isolation valve
shutdown valve
control valve
check valve
relief valve
pressure regulating valve
injection point
extraction point
purge point
vent point
drain point
sampling point
metering point
scraper launcher
scraper receiver
hot tap point
bypass point
connection point
```

Recommended enum:

```java
public enum PipelineAppurtenanceType {
    VALVE,
    INJECTION_POINT,
    EXTRACTION_POINT,
    PURGE_POINT,
    VENT_POINT,
    DRAIN_POINT,
    SAMPLING_POINT,
    METERING_POINT,
    SCRAPER_LAUNCHER,
    SCRAPER_RECEIVER,
    HOT_TAP_POINT,
    BYPASS_POINT,
    CONNECTION_POINT,
    OTHER
}
```

Recommended valve enum:

```java
public enum ValveType {
    BLOCK_VALVE,
    SECTIONALIZING_VALVE,
    ISOLATION_VALVE,
    SHUTDOWN_VALVE,
    CONTROL_VALVE,
    CHECK_VALVE,
    RELIEF_VALVE,
    PRESSURE_REGULATING_VALVE,
    BYPASS_VALVE,
    DRAIN_VALVE,
    VENT_VALVE,
    ESD_VALVE,
    MANUAL_VALVE,
    MOTORIZED_VALVE,
    OTHER
}
```

Rules:

```text
PipelineAppurtenance belongs to one Pipeline.
PipelineAppurtenance normally has one TopologyNode.
PipelineAppurtenance has a pipeline kilometer point / KP / PK / chainage.
valve_type is set only when appurtenance_type = VALVE.
Injection, purge, extraction, vent, drain, and scraper points are topology point assets.
Detailed operation history belongs later to operations/workflow/measurement modules.
```

---

## 9. Topology node conception

`TopologyNode` is the graph vertex.

Table:

```text
hidra_topology_node
```

Recommended enum:

```java
public enum NodeType {
    FACILITY_INLET,
    FACILITY_OUTLET,
    FACILITY_INTERNAL,
    PIPELINE_JUNCTION,
    PIPELINE_VALVE_POINT,
    INJECTION_POINT,
    EXTRACTION_POINT,
    PURGE_POINT,
    VENT_POINT,
    DRAIN_POINT,
    METERING_POINT,
    SAMPLING_POINT,
    SCRAPER_POINT,
    RECEIPT_POINT,
    DELIVERY_POINT,
    CONNECTION_POINT,
    OTHER
}
```

Node relationships:

```text
Facility 1 ---- N TopologyNode
PipelineAppurtenance 1 ---- 1 TopologyNode
PipelineSegment N ---- 1 TopologyNode as from_node
PipelineSegment N ---- 1 TopologyNode as to_node
TopologyConnection N ---- 1 TopologyNode as from_node
TopologyConnection N ---- 1 TopologyNode as to_node
```

---

## 10. Updated ERD

```text
PipelineSystem
   1 ---- N Pipeline
              1 ---- N PipelineSegment
              1 ---- N PipelineAppurtenance

Facility
   1 ---- N TopologyNode

PipelineAppurtenance
   N ---- 1 Pipeline
   1 ---- 1 TopologyNode

PipelineSegment
   N ---- 1 TopologyNode as from_node
   N ---- 1 TopologyNode as to_node

TopologyConnection
   N ---- 1 TopologyNode as from_node
   N ---- 1 TopologyNode as to_node

Equipment
   N ---- 1 parent asset by parent_asset_type + parent_asset_id
```

Final recommended first topology tables:

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

---

## 11. Main entity definitions

### PipelineSystem

Represents a named hydrocarbon transportation system.

Main fields:

```text
id
code
name
description
product_type
status
operational_owner_reference_type
operational_owner_reference_id
operational_owner_reference_code
operational_owner_reference_name
created_at
updated_at
```

### Pipeline

Represents a physical pipeline belonging to a pipeline system.

Main fields:

```text
id
pipeline_system_id
code
name
description
product_type
nominal_diameter_inches
design_length_km
status
created_at
updated_at
```

### Facility

Represents a physical facility connected to the hydrocarbon network.

Main fields:

```text
id
code
name
facility_type
product_type
status
latitude
longitude
organization_unit_reference_type
organization_unit_reference_id
organization_unit_reference_code
organization_unit_reference_name
created_at
updated_at
```

### TopologyNode

Represents a physical connection point.

Main fields:

```text
id
code
name
node_type
facility_id nullable
latitude nullable
longitude nullable
elevation_m nullable
status
created_at
updated_at
```

### PipelineSegment

Represents a pipe section between two nodes.

Main fields:

```text
id
pipeline_id
code
name
from_node_id
to_node_id
length_km
diameter_inches
status
created_at
updated_at
```

Rule:

```text
from_node_id != to_node_id
```

### PipelineAppurtenance

Represents a physical point asset installed along a pipeline.

Main fields:

```text
id
pipeline_id
node_id
code
name
appurtenance_type
valve_type nullable
pipeline_kilometer_point
status
latitude nullable
longitude nullable
description
created_at
updated_at
```

Rules:

```text
node_id should normally be non-null.
pipeline_kilometer_point must be non-negative.
valve_type is allowed only when appurtenance_type is VALVE.
valve_type must be null when appurtenance_type is not VALVE.
```

### TopologyConnection

Represents explicit graph connectivity between two nodes.

Main fields:

```text
id
code
name
from_node_id
to_node_id
connection_type
linked_asset_type
linked_asset_id
status
created_at
updated_at
```

Recommended connection types:

```text
PIPELINE_SEGMENT
FACILITY_INTERNAL
VALVE_CONNECTION
METERING_CONNECTION
JUNCTION_CONNECTION
APPURTENANCE_CONNECTION
OTHER
```

### Equipment

Represents optional topology-owned equipment reference.

Main fields:

```text
id
code
name
equipment_type
parent_asset_type
parent_asset_id
status
created_at
updated_at
```

Important:

```text
PipelineAppurtenance = topology-level physical point
Equipment = optional detailed physical component attached to a topology asset
```

Example:

```text
PipelineAppurtenance: BV-045 block valve at KP 45
Equipment:
  actuator
  local control panel
  limit switch
```

---

## 12. Package structure

```text
src/main/java/dz/sh/hidra/modules/topology
├── api
│   └── rest
│       ├── configuration
│       ├── controller
│       ├── mapper
│       ├── request
│       └── response
├── application
│   ├── command
│   ├── dto
│   ├── port
│   │   ├── in
│   │   └── out
│   ├── query
│   └── service
├── domain
│   ├── exception
│   ├── model
│   ├── policy
│   ├── service
│   └── value
└── infrastructure
    ├── configuration
    └── persistence
        ├── adapter
        ├── entity
        ├── mapper
        └── repository
```

Each production package must have a `package-info.java` when the skeleton task is executed.

Do not create test `package-info.java` files.

---

## 13. Database naming rules

All topology tables must start with:

```text
hidra_topology_
```

Required table names for initial implementation:

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

Do not create these inside topology migrations:

```text
hidra_org_*
hidra_identity_*
hidra_platform_*
hidra_measurement_*
hidra_flow_*
hidra_risk_*
hidra_workflow_*
hidra_reporting_*
```

---

## 14. Naming rules

Use these names consistently:

```text
Module name       : topology
Java root package : dz.sh.hidra.modules.topology
Database prefix   : hidra_topology_
Roadmap prefix    : TOP
Physical site     : Facility
Pipeline point    : PipelineAppurtenance
```

Do not use these as module/model replacements:

```text
network
assetnetwork
pipeline_network
physicalasset
Station as main aggregate
```

`Station` may appear only as part of `FacilityType`, for example `COMPRESSION_STATION`.

---

## 15. What must not be in topology

Do not put this data or behavior in topology:

```text
employees
users
roles
permissions
team leaders
reporting lines
SCADA tag values
pressure time-series values
temperature time-series values
flow time-series values
alarms
incidents
work permits
risk scores
hydraulic simulation results
workflow approvals
maintenance work orders
ship loading operations
inventory accounting
well tests
reservoir models
production allocation
```

Those belong later to:

```text
organization  -> employees, OUs, positions, reporting lines
identity      -> users, roles, permissions
measurement   -> tags, readings, time-series values, quality
operations    -> shifts, incidents, operating events, permits
flow          -> hydraulic calculations, balances, line pack
risk          -> risk factors, assessments, scores
workflow      -> approvals, tasks, state transitions
maintenance   -> work orders, inspections, maintenance history
production    -> wells, reservoirs, allocation, upstream production
inventory     -> terminal/storage stock accounting
```

---

## 16. Commit plan

| Commit code | Commit message | Status | Description |
|---|---|---:|---|
| `TOP-001` | `docs(topology): add topology implementation roadmap` | Planned | Add this roadmap file only. |
| `TOP-002` | `chore(topology): add topology package skeleton` | Completed locally | Added topology production package-info.java skeleton files only; Maven compile not run in this local ZIP generation. |
| `TOP-003` | `feat(topology): add topology domain value objects` | Completed locally | Added topology domain value records and enums only; Maven compile must be run after copying into the repository. |
| `TOP-004` | `feat(topology): add topology domain models` | Completed locally | Added topology domain model classes only; Maven compile must be run after copying into the repository. |
| `TOP-005` | `feat(topology): add topology domain policies` | Completed locally | Added topology domain policies and topology domain exceptions only; Maven compile must be run after copying into the repository. |
| `TOP-006` | `feat(topology): add topology domain services` | Completed locally | Added pure topology domain services only; Maven compile must be run after copying into the repository. |
| `TOP-007` | `feat(topology): add topology application commands and queries` | Completed locally | Added topology application command and query records only; Maven compile must be run after copying into the repository. |
| `TOP-008` | `feat(topology): add topology application DTOs` | Completed locally | Added topology application DTO records only; Maven compile must be run after copying into the repository. |
| `TOP-009` | `feat(topology): add topology application ports` | Completed locally | Added topology application inbound and outbound port interfaces only; Maven compile must be run after copying into the repository. |
| `TOP-010` | `feat(topology): add topology application services` | Completed locally | Added topology application service classes and infrastructure bean wiring only; Maven compile must be run after copying into the repository. |
| `TOP-011` | `feat(topology): add topology persistence entities and repositories` | Completed locally | Added topology JPA entities and Spring Data repository interfaces only; Maven compile must be run after copying into the repository. |
| `TOP-012` | `feat(topology): add topology persistence mapper and adapters` | Completed locally | Added topology persistence mapper, repository adapters, and configuration wiring updates only; Maven compile must be run after copying into the repository. |
| `TOP-013` | `db(topology): add topology flyway migration` | Planned | Add topology schema migration. |
| `TOP-014` | `feat(topology): add topology REST DTOs and mapper` | Planned | Add request/response DTOs and REST mapper. |
| `TOP-015` | `feat(topology): add topology REST controllers` | Planned | Add documented controllers for topology endpoints. |
| `TOP-016` | `feat(topology): add topology REST mapper` | Completed locally | Added topology REST mapper and mapper bean configuration only; Maven compile must be run after copying into the repository. |
| `TOP-017` | `test(topology): add topology application tests` | Planned | Add tests for application services and ports. |
| `TOP-018` | `test(topology): add topology API and mapper tests` | Planned | Add controller and REST mapper tests. |
| `TOP-019` | `test(topology): add topology architecture guardrails` | Planned | Add dependency/boundary tests if project strategy supports them. |
| `TOP-020` | `docs(topology): finalize topology roadmap status` | Planned | Update final status, validations, risks, and next module recommendation. |

---

# TOP-001 — Add topology implementation roadmap

```text
Commit code    : TOP-001
Commit message : docs(topology): add topology implementation roadmap
Type           : Documentation
Layer          : Documentation
Module         : topology
```

## Description

Create the topology roadmap used by Codex or another AI agent to implement the topology module safely.

## Files to create

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Defines topology tasks, ownership rules, data conception, package structure, file scope, validation commands, and completion criteria. |

## Files to update

None.

## Rules

```text
- Create only docs/roadmap/topology.md.
- Do not create production Java files.
- Do not create test Java files.
- Do not create migrations.
- Do not modify existing roadmap files.
- Do not modify kernel files.
- Do not modify platform files.
- Do not modify identity files.
- Do not modify organization files.
- Do not create topology package skeleton in this task.
- Do not execute TOP-002 or later.
```

## Validation commands

```bash
test -f docs/roadmap/topology.md
```

On Windows PowerShell:

```powershell
Test-Path docs/roadmap/topology.md
```

## Completion criteria

```text
docs/roadmap/topology.md exists
roadmap contains commit code, commit message, description, files to create/update, and purpose of each file
no production code was changed
no test code was changed
no migration was created
```

---

# TOP-002 — Add topology package skeleton

```text
Commit code    : TOP-002
Commit message : chore(topology): add topology package skeleton
Type           : Chore
Layer          : API, Application, Domain, Infrastructure
Module         : topology
```

## Description

Create only the topology production package skeleton through `package-info.java` files.

## Files to create

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/package-info.java` | Documents topology module root boundary. |
| `src/main/java/dz/sh/hidra/modules/topology/api/package-info.java` | Documents topology API layer boundary. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/package-info.java` | Documents topology REST API boundary. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/configuration/package-info.java` | Documents REST configuration package. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/package-info.java` | Documents controller package. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/package-info.java` | Documents REST mapper package. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/package-info.java` | Documents REST request DTO package. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/package-info.java` | Documents REST response DTO package. |
| `src/main/java/dz/sh/hidra/modules/topology/application/package-info.java` | Documents application layer boundary. |
| `src/main/java/dz/sh/hidra/modules/topology/application/command/package-info.java` | Documents command package. |
| `src/main/java/dz/sh/hidra/modules/topology/application/dto/package-info.java` | Documents application DTO package. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/package-info.java` | Documents port package. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/package-info.java` | Documents inbound use-case port package. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/out/package-info.java` | Documents outbound port package. |
| `src/main/java/dz/sh/hidra/modules/topology/application/query/package-info.java` | Documents query package. |
| `src/main/java/dz/sh/hidra/modules/topology/application/service/package-info.java` | Documents application service package. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/package-info.java` | Documents domain layer boundary. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/exception/package-info.java` | Documents domain exception package. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/model/package-info.java` | Documents domain model package. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/policy/package-info.java` | Documents domain policy package. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/service/package-info.java` | Documents domain service package. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/package-info.java` | Documents value object package. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/package-info.java` | Documents infrastructure layer boundary. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/configuration/package-info.java` | Documents infrastructure configuration package. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/package-info.java` | Documents persistence boundary. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/package-info.java` | Documents persistence adapter package. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/package-info.java` | Documents JPA entity package. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/package-info.java` | Documents persistence mapper package. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/package-info.java` | Documents Spring Data repository package. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-002 status after implementation. |

## Rules

```text
- Do not create classes, records, enums, services, repositories, controllers, DTOs, entities, mappers, configurations, migrations, or tests with behavior.
- Do not create test package-info.java files.
- Do not create identityaccess package.
- Do not modify kernel/platform/identity/organization code.
- Every package-info.java must include package-level JavaDoc.
- JavaDoc must explain package responsibility.
- JavaDoc must explain what belongs in the package.
- JavaDoc must explain what is forbidden in the package.
- JavaDoc must mention topology owns physical facilities, pipelines, nodes, appurtenances, and network connectivity.
- JavaDoc must mention organization owns facility/station-as-organization-unit.
- JavaDoc must mention identity owns users, roles, permissions, and access policies.
- JavaDoc must mention future measurement owns telemetry/time-series values.
- JavaDoc must mention future flow owns hydraulic calculations.
```

## Validation commands

```bash
mvn -q -DskipTests compile
```

## Completion criteria

```text
all listed production package-info.java files exist
no test package-info.java files were created
no behavior classes were created
mvn -q -DskipTests compile passes or exact unrelated blocker is documented
```

---

# TOP-003 — Add topology domain value objects

```text
Commit code    : TOP-003
Commit message : feat(topology): add topology domain value objects
Type           : Feature
Layer          : Domain
Module         : topology
```

## Description

Add immutable topology value objects and enums used by domain models.

## Files to create

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemId.java` | Strong identifier for pipeline systems. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineId.java` | Strong identifier for pipelines. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSegmentId.java` | Strong identifier for pipeline segments. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyNodeId.java` | Strong identifier for topology nodes. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityId.java` | Strong identifier for physical facilities. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineAppurtenanceId.java` | Strong identifier for pipeline point assets/appurtenances. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyConnectionId.java` | Strong identifier for topology connections. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentId.java` | Strong identifier for topology equipment references. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyCode.java` | Validated business code used by topology assets. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyName.java` | Validated display/business name used by topology assets. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java` | Enum for PLANNED, ACTIVE, INACTIVE, UNDER_MAINTENANCE, RETIRED, DECOMMISSIONED. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityType.java` | Enum for compression station, pumping station, terminal, processing plant, production field, etc. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineAppurtenanceType.java` | Enum for valve, injection, extraction, purge, vent, drain, scraper, etc. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/ValveType.java` | Enum for block, sectionalizing, isolation, shutdown, control, check, relief, ESD, etc. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/NodeType.java` | Enum for facility inlet/outlet, junction, valve point, injection, extraction, purge, vent, drain, etc. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentType.java` | Enum for compressor, pump, valve component, meter, separator, actuator, panel, etc. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/ProductType.java` | Enum for GAS, CRUDE_OIL, CONDENSATE, LPG, REFINED_PRODUCT, MULTIPHASE, UNKNOWN. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java` | Enum for PIPELINE_SEGMENT, FACILITY_INTERNAL, VALVE_CONNECTION, METERING_CONNECTION, etc. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyAssetType.java` | Enum for PIPELINE_SYSTEM, PIPELINE, FACILITY, NODE, SEGMENT, APPURTENANCE, CONNECTION, EQUIPMENT. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/GeoCoordinate.java` | Latitude/longitude value object for physical location. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/LengthInKilometers.java` | Validated positive pipeline/segment length value. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/DiameterInInches.java` | Validated positive nominal diameter value. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineKilometerPoint.java` | Validated non-negative KP/PK/chainage value for pipeline point assets. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/OrganizationUnitReference.java` | Neutral reference to organization unit owner/operator without importing organization implementation. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/OperationalOwnerReference.java` | Neutral reference to operational owner of a physical topology asset. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-003 status after implementation. |

## Rules

```text
- Use records where appropriate.
- Use enums for closed value sets.
- Validate null, blank, length, and numeric constraints.
- Do not use Spring annotations in domain value objects.
- Do not use JPA annotations in domain value objects.
- Do not import organization domain classes.
- Do not import identity classes.
- Keep value objects immutable.
- PipelineKilometerPoint must reject negative values.
- ValveType must be modeled separately from PipelineAppurtenanceType.
```

## Validation commands

```bash
mvn -q -DskipTests compile
```

## Completion criteria

```text
value objects compile
invalid values are rejected
domain value objects have no Spring/JPA/API/identity/organization implementation dependency
```

---

# TOP-004 — Add topology domain models

```text
Commit code    : TOP-004
Commit message : feat(topology): add topology domain models
Type           : Feature
Layer          : Domain
Module         : topology
```

## Description

Add the main topology domain models.

## Files to create

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java` | Represents a hydrocarbon transportation system grouping pipelines. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java` | Represents a physical pipeline belonging to a pipeline system. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java` | Represents a physical facility: station, terminal, processing plant, field interface, etc. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyNode.java` | Represents a physical connection node in the network graph. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSegment.java` | Represents a physical pipe segment between two topology nodes. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineAppurtenance.java` | Represents a point asset along a pipeline: valve, injection, purge, extraction, vent, drain, scraper, etc. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java` | Represents explicit physical connectivity between topology nodes. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java` | Represents optional equipment/component references attached to topology assets. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-004 status after implementation. |

## Required behavior

Domain models must support:

```text
creation through static factory methods
activation/deactivation/status transition methods where relevant
basic invariant enforcement
no public setters
immutable identifiers
clear business validation errors
```

Specific invariants:

```text
Pipeline belongs to PipelineSystem.
PipelineSegment belongs to Pipeline.
PipelineSegment from node must differ from to node.
Facility owns physical facility only, not organization team.
TopologyNode may belong to Facility or PipelineAppurtenance context.
PipelineAppurtenance belongs to Pipeline.
PipelineAppurtenance normally references one TopologyNode.
PipelineAppurtenance pipeline kilometer point must be non-negative.
PipelineAppurtenance with type VALVE must have ValveType.
PipelineAppurtenance with type not VALVE must not have ValveType.
TopologyConnection from node must differ from to node.
Equipment parent is represented by neutral parent_asset_type + parent_asset_id.
```

## Rules

```text
- Do not add JPA annotations to domain models.
- Do not add Spring annotations to domain models.
- Do not add REST/Swagger annotations to domain models.
- Do not model employees, users, roles, permissions, shifts, telemetry values, hydraulic calculations, risk scores, workflows, work permits, or production allocation.
- Do not create station-as-organization-unit here.
- Facility here means physical facility asset only.
```

## Validation commands

```bash
mvn -q -DskipTests compile
```

## Completion criteria

```text
domain models compile
domain invariants are enforced
domain models have no Spring/JPA/API dependencies
```

---

# TOP-005 — Add topology domain policies

```text
Commit code    : TOP-005
Commit message : feat(topology): add topology domain policies
Type           : Feature
Layer          : Domain
Module         : topology
```

## Description

Add domain policies for topology connectivity, asset status transitions, facility rules, and appurtenance rules.

## Files to create

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/domain/policy/TopologyAssetStatusPolicy.java` | Validates allowed status transitions for topology assets. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/policy/TopologyConnectivityPolicy.java` | Validates node/segment/connection consistency. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/policy/FacilityTopologyPolicy.java` | Validates physical facility rules and facility node requirements. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/policy/PipelineAppurtenancePolicy.java` | Validates appurtenance rules, including valve type, KP, node, and pipeline consistency. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/exception/TopologyException.java` | Base topology domain exception if not already created. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/exception/TopologyValidationException.java` | Exception for topology validation failures. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-005 status after implementation. |

## Rules

```text
- Policies must be pure domain classes.
- Do not inject Spring beans into policies.
- Do not query repositories from policies.
- Do not import infrastructure classes.
- Do not import organization or identity implementations.
- Appurtenance policy must reject valve_type when appurtenance_type is not VALVE.
- Appurtenance policy must require valve_type when appurtenance_type is VALVE.
```

## Validation commands

```bash
mvn -q -DskipTests compile
```

## Completion criteria

```text
policies compile
status, connectivity, facility, and appurtenance rules are explicit
policies remain pure domain classes
```

---

# TOP-006 — Add topology domain services

```text
Commit code    : TOP-006
Commit message : feat(topology): add topology domain services
Type           : Feature
Layer          : Domain
Module         : topology
```

## Description

Add domain services for physical topology consistency that requires multiple domain objects.

## Files to create

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/domain/service/TopologyRegistrationDomainService.java` | Coordinates domain validation when registering topology assets. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/service/TopologyConnectivityDomainService.java` | Validates and assembles physical node/segment/connection relationships. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/service/PipelineAppurtenanceDomainService.java` | Validates point assets along pipelines against pipeline, node, KP, and appurtenance rules. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-006 status after implementation. |

## Rules

```text
- Domain services must not use Spring annotations.
- Domain services must not access repositories directly.
- Domain services must not import persistence, API, identity, or organization implementation.
- Domain services may use domain policies and domain models only.
```

## Validation commands

```bash
mvn -q -DskipTests compile
```

## Completion criteria

```text
domain services compile
domain services coordinate domain-only behavior
no infrastructure dependency is introduced
```

---

# TOP-007 — Add topology application commands and queries

```text
Commit code    : TOP-007
Commit message : feat(topology): add topology application commands and queries
Type           : Feature
Layer          : Application
Module         : topology
```

## Files to create

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java` | Input for creating a pipeline system. |
| `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineCommand.java` | Input for creating a pipeline. |
| `src/main/java/dz/sh/hidra/modules/topology/application/command/CreateFacilityCommand.java` | Input for creating a physical facility. |
| `src/main/java/dz/sh/hidra/modules/topology/application/command/CreateTopologyNodeCommand.java` | Input for creating a topology node. |
| `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSegmentCommand.java` | Input for creating a pipeline segment. |
| `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineAppurtenanceCommand.java` | Input for creating a valve/injection/purge/extraction/other pipeline point asset. |
| `src/main/java/dz/sh/hidra/modules/topology/application/command/CreateTopologyConnectionCommand.java` | Input for creating a topology connection. |
| `src/main/java/dz/sh/hidra/modules/topology/application/command/RegisterEquipmentCommand.java` | Input for registering topology equipment. |
| `src/main/java/dz/sh/hidra/modules/topology/application/query/GetPipelineSystemByIdQuery.java` | Query for fetching a pipeline system. |
| `src/main/java/dz/sh/hidra/modules/topology/application/query/GetPipelineByIdQuery.java` | Query for fetching a pipeline. |
| `src/main/java/dz/sh/hidra/modules/topology/application/query/GetFacilityByIdQuery.java` | Query for fetching a physical facility. |
| `src/main/java/dz/sh/hidra/modules/topology/application/query/GetTopologyNodeByIdQuery.java` | Query for fetching a topology node. |
| `src/main/java/dz/sh/hidra/modules/topology/application/query/GetPipelineAppurtenanceByIdQuery.java` | Query for fetching a pipeline appurtenance. |
| `src/main/java/dz/sh/hidra/modules/topology/application/query/ListPipelineSystemsQuery.java` | Query for paginated pipeline-system listing. |
| `src/main/java/dz/sh/hidra/modules/topology/application/query/ListPipelinesQuery.java` | Query for paginated pipeline listing. |
| `src/main/java/dz/sh/hidra/modules/topology/application/query/ListFacilitiesQuery.java` | Query for paginated facility listing. |
| `src/main/java/dz/sh/hidra/modules/topology/application/query/ListTopologyNodesQuery.java` | Query for paginated topology-node listing. |
| `src/main/java/dz/sh/hidra/modules/topology/application/query/ListPipelineSegmentsQuery.java` | Query for paginated segment listing. |
| `src/main/java/dz/sh/hidra/modules/topology/application/query/ListPipelineAppurtenancesQuery.java` | Query for paginated appurtenance listing. |
| `src/main/java/dz/sh/hidra/modules/topology/application/query/ListTopologyConnectionsQuery.java` | Query for paginated connection listing. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-007 status after implementation. |

## Rules

```text
- Use records where appropriate.
- Keep commands/queries framework-free.
- Do not use REST annotations.
- Do not use JPA annotations.
- Do not include business behavior in commands/queries.
```

## Validation commands

```bash
mvn -q -DskipTests compile
```

## Completion criteria

```text
commands and queries compile
they do not depend on API or infrastructure
```

---

# TOP-008 — Add topology application DTOs

```text
Commit code    : TOP-008
Commit message : feat(topology): add topology application DTOs
Type           : Feature
Layer          : Application
Module         : topology
```

## Files to create

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineSystemDto.java` | Application DTO for pipeline systems. |
| `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineDto.java` | Application DTO for pipelines. |
| `src/main/java/dz/sh/hidra/modules/topology/application/dto/FacilityDto.java` | Application DTO for physical facilities. |
| `src/main/java/dz/sh/hidra/modules/topology/application/dto/TopologyNodeDto.java` | Application DTO for nodes. |
| `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineSegmentDto.java` | Application DTO for pipeline segments. |
| `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineAppurtenanceDto.java` | Application DTO for pipeline appurtenances. |
| `src/main/java/dz/sh/hidra/modules/topology/application/dto/TopologyConnectionDto.java` | Application DTO for connections. |
| `src/main/java/dz/sh/hidra/modules/topology/application/dto/EquipmentDto.java` | Application DTO for equipment references. |
| `src/main/java/dz/sh/hidra/modules/topology/application/dto/OrganizationUnitReferenceDto.java` | Application DTO for neutral organization-unit reference. |
| `src/main/java/dz/sh/hidra/modules/topology/application/dto/GeoCoordinateDto.java` | Application DTO for geographical coordinates. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-008 status after implementation. |

## Rules

```text
- Application DTOs must not use Swagger annotations.
- Application DTOs must not use JPA annotations.
- Application DTOs must not expose persistence entities.
- Application DTOs may use simple scalar fields and nested DTOs.
```

## Validation commands

```bash
mvn -q -DskipTests compile
```

## Completion criteria

```text
application DTOs compile
application layer remains independent from API and infrastructure
```

---

# TOP-009 — Add topology application ports

```text
Commit code    : TOP-009
Commit message : feat(topology): add topology application ports
Type           : Feature
Layer          : Application
Module         : topology
```

## Files to create

### Inbound ports

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java` | Inbound port for creating pipeline systems. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineUseCase.java` | Inbound port for creating pipelines. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreateFacilityUseCase.java` | Inbound port for creating physical facilities. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreateTopologyNodeUseCase.java` | Inbound port for creating topology nodes. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSegmentUseCase.java` | Inbound port for creating pipeline segments. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineAppurtenanceUseCase.java` | Inbound port for creating pipeline appurtenances. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreateTopologyConnectionUseCase.java` | Inbound port for creating topology connections. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterEquipmentUseCase.java` | Inbound port for registering equipment. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/GetPipelineSystemUseCase.java` | Inbound port for retrieving pipeline systems. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/GetPipelineUseCase.java` | Inbound port for retrieving pipelines. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/GetFacilityUseCase.java` | Inbound port for retrieving facilities. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/GetTopologyNodeUseCase.java` | Inbound port for retrieving topology nodes. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/GetPipelineAppurtenanceUseCase.java` | Inbound port for retrieving pipeline appurtenances. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/ListPipelineSystemsUseCase.java` | Inbound port for listing pipeline systems. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/ListPipelinesUseCase.java` | Inbound port for listing pipelines. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/ListFacilitiesUseCase.java` | Inbound port for listing facilities. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/ListTopologyNodesUseCase.java` | Inbound port for listing topology nodes. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/ListPipelineSegmentsUseCase.java` | Inbound port for listing pipeline segments. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/ListPipelineAppurtenancesUseCase.java` | Inbound port for listing appurtenances. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/in/ListTopologyConnectionsUseCase.java` | Inbound port for listing topology connections. |

### Outbound ports

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java` | Outbound persistence port for pipeline systems. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineRepositoryPort.java` | Outbound persistence port for pipelines. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/out/FacilityRepositoryPort.java` | Outbound persistence port for facilities. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyNodeRepositoryPort.java` | Outbound persistence port for topology nodes. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSegmentRepositoryPort.java` | Outbound persistence port for pipeline segments. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineAppurtenanceRepositoryPort.java` | Outbound persistence port for pipeline appurtenances. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyConnectionRepositoryPort.java` | Outbound persistence port for topology connections. |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java` | Outbound persistence port for equipment references. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-009 status after implementation. |

## Rules

```text
- Ports must be interfaces.
- Inbound ports belong in application/port/in.
- Outbound ports belong in application/port/out.
- Ports must not depend on Spring, JPA, or REST.
- Do not return persistence entities from ports.
```

## Validation commands

```bash
mvn -q -DskipTests compile
```

## Completion criteria

```text
ports compile
dependency direction remains API -> Application -> Domain and Infrastructure -> Application
```

---

# TOP-010 — Add topology application services

```text
Commit code    : TOP-010
Commit message : feat(topology): add topology application services
Type           : Feature
Layer          : Application
Module         : topology
```

## Files to create

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java` | Implements create/get/list pipeline system use cases. |
| `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineApplicationService.java` | Implements create/get/list pipeline use cases. |
| `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java` | Implements create/get/list physical facility use cases. |
| `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyNodeApplicationService.java` | Implements create/get/list topology node use cases. |
| `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSegmentApplicationService.java` | Implements create/list segment use cases. |
| `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineAppurtenanceApplicationService.java` | Implements create/get/list appurtenance use cases. |
| `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyConnectionApplicationService.java` | Implements create/list connection use cases. |
| `src/main/java/dz/sh/hidra/modules/topology/application/service/EquipmentApplicationService.java` | Implements register equipment use case. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/configuration/TopologyConfiguration.java` | Wires topology application services, policies, and domain services as Spring beans if existing architecture uses explicit configuration. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-010 status after implementation. |

## Rules

```text
- Application services may use Spring @Service only if consistent with existing modules.
- Application services must depend on inbound/outbound ports, domain models, domain services, and policies.
- Application services must not depend on REST DTOs.
- Application services must not depend on JPA entities.
- Application services must not call other module repositories.
- Do not implement measurement, operations, flow, risk, workflow, maintenance, inventory, or production behavior here.
```

## Validation commands

```bash
mvn -q -DskipTests compile
```

## Completion criteria

```text
application services compile
services enforce domain validation
services persist through outbound ports only
```

---

# TOP-011 — Add topology persistence entities and repositories

```text
Commit code    : TOP-011
Commit message : feat(topology): add topology persistence entities and repositories
Type           : Feature
Layer          : Infrastructure
Module         : topology
```

## Files to create

### JPA entities

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java` | JPA representation of pipeline systems. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java` | JPA representation of pipelines. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java` | JPA representation of physical facilities. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyNodeJpaEntity.java` | JPA representation of topology nodes. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java` | JPA representation of pipeline segments. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineAppurtenanceJpaEntity.java` | JPA representation of pipeline appurtenances. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java` | JPA representation of topology connections. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java` | JPA representation of topology equipment. |

### Spring Data repositories

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemJpaRepository.java` | Spring Data repository for pipeline systems. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineJpaRepository.java` | Spring Data repository for pipelines. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityJpaRepository.java` | Spring Data repository for facilities. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/TopologyNodeJpaRepository.java` | Spring Data repository for topology nodes. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSegmentJpaRepository.java` | Spring Data repository for pipeline segments. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineAppurtenanceJpaRepository.java` | Spring Data repository for pipeline appurtenances. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/TopologyConnectionJpaRepository.java` | Spring Data repository for topology connections. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentJpaRepository.java` | Spring Data repository for equipment. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-011 status after implementation. |

## Rules

```text
- JPA entities must be in infrastructure.persistence.entity.
- Spring Data repositories must be in infrastructure.persistence.repository.
- JPA entities must not contain business behavior.
- Table names must start with hidra_topology_.
- Use hidra_topology_facility, not hidra_topology_station.
- Use hidra_topology_pipeline_appurtenance for valves, injection, purge, extraction, and similar point assets.
- Do not create Flyway migration in this task.
- Do not create API controllers in this task.
```

## Validation commands

```bash
mvn -q -DskipTests compile
```

## Completion criteria

```text
JPA entities compile
repositories compile
table names are explicit
no business behavior is placed in persistence entities
```

---

# TOP-012 — Add topology persistence mapper and adapters

```text
Commit code    : TOP-012
Commit message : feat(topology): add topology persistence mapper and adapters
Type           : Feature
Layer          : Infrastructure
Module         : topology
```

## Files to create

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java` | Maps between topology domain/application objects and JPA entities. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/PipelineSystemRepositoryAdapter.java` | Implements pipeline system repository port. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/PipelineRepositoryAdapter.java` | Implements pipeline repository port. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/FacilityRepositoryAdapter.java` | Implements facility repository port. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyNodeRepositoryAdapter.java` | Implements topology node repository port. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/PipelineSegmentRepositoryAdapter.java` | Implements segment repository port. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/PipelineAppurtenanceRepositoryAdapter.java` | Implements appurtenance repository port. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyConnectionRepositoryAdapter.java` | Implements connection repository port. |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EquipmentRepositoryAdapter.java` | Implements equipment repository port. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-012 status after implementation. |

## Rules

```text
- Adapters implement application outbound ports.
- Adapters may depend on Spring Data repositories and persistence mapper.
- Adapters must not depend on REST DTOs.
- Mapper must not call repositories.
- Mapper must not contain business decisions.
- Do not create migrations in this task.
```

## Validation commands

```bash
mvn -q -DskipTests compile
```

## Completion criteria

```text
adapters compile
outbound ports have infrastructure implementations
mapping is deterministic and side-effect free
```

---

# TOP-013 — Add topology Flyway migration

```text
Commit code    : TOP-013
Commit message : db(topology): add topology flyway migration
Type           : Database
Layer          : Infrastructure
Module         : topology
```

## Files to create

| File | Purpose |
|---|---|
| `src/main/resources/db/migration/V030__create_topology_tables.sql` | Creates topology tables, constraints, indexes, and foreign keys. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-013 status after implementation. |

## Required tables

Use exact table names from JPA entities. Expected names:

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

## Required database design

The migration must include:

```text
primary keys
unique constraints for stable codes
foreign keys for system/pipeline/facility/node/segment/appurtenance relationships
indexes for status, type, system, pipeline, facility, nodes, KP, and owner references
columns for neutral organization owner references where mapped
created_at and updated_at columns if mapped by entities
CHECK from_node_id <> to_node_id for segments/connections if represented as scalar IDs
CHECK pipeline_kilometer_point >= 0 for appurtenances
CHECK valve_type consistency if practical in SQL, or enforce in domain/persistence validation
```

## Rules

```text
- Inspect topology JPA entity mappings before writing SQL.
- Do not guess column names.
- Match @Table and @Column exactly.
- Do not create hidra_topology_station.
- Do not create identity tables.
- Do not create organization tables.
- Do not create platform tables.
- Do not create measurement, flow, risk, analytics, workflow, reporting, inventory, maintenance, or production tables.
- Do not modify V001, V010, or V020.
```

## Validation commands

```bash
mvn -q -DskipTests compile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Recommended DB checks:

```sql
SELECT installed_rank, version, description, success
FROM flyway_schema_history
ORDER BY installed_rank;

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
  AND table_name LIKE 'hidra_topology_%'
ORDER BY table_name;
```

## Completion criteria

```text
Flyway applies V030 successfully on a clean dev database
all topology entity tables exist
Hibernate validation/update no longer fails on topology tables
```

---

# TOP-014 — Add topology REST DTOs and mapper

```text
Commit code    : TOP-014
Commit message : feat(topology): add topology REST DTOs and mapper
Type           : Feature
Layer          : API
Module         : topology
```

## Files to create

### Requests

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSystemRequest.java` | REST request for creating pipeline systems. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineRequest.java` | REST request for creating pipelines. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreateFacilityRequest.java` | REST request for creating physical facilities. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreateTopologyNodeRequest.java` | REST request for creating topology nodes. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSegmentRequest.java` | REST request for creating pipeline segments. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineAppurtenanceRequest.java` | REST request for creating valves, injection points, purge points, extraction points, and other pipeline point assets. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreateTopologyConnectionRequest.java` | REST request for creating topology connections. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/RegisterEquipmentRequest.java` | REST request for registering equipment. |

### Responses

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSystemResponse.java` | REST response for pipeline systems. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineResponse.java` | REST response for pipelines. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/FacilityResponse.java` | REST response for physical facilities. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/TopologyNodeResponse.java` | REST response for nodes. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSegmentResponse.java` | REST response for segments. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineAppurtenanceResponse.java` | REST response for appurtenances. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/TopologyConnectionResponse.java` | REST response for connections. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/EquipmentResponse.java` | REST response for equipment. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/GeoCoordinateResponse.java` | REST response for coordinates. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/OrganizationUnitReferenceResponse.java` | REST response for neutral organization owner reference. |

### Mapper/configuration

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyRestMapper.java` | Maps REST DTOs to application commands/queries and application DTOs to REST responses. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/configuration/TopologyApiRestConfiguration.java` | Registers `TopologyRestMapper` bean if mapper is a plain class. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-014 status after implementation. |

## Documentation and validation rules

```text
- Every request field must use Bean Validation where applicable.
- Every request and response field must use @Schema.
- Use clear examples in @Schema.
- Do not expose JPA entity fields directly.
- Do not expose internal exception names as API model fields.
- Mapper must not call application services.
- Mapper must not call repositories.
```

## Validation commands

```bash
mvn -q -DskipTests compile
```

## Completion criteria

```text
REST DTOs compile
REST mapper compiles
all DTO fields have @Schema
request fields have validation annotations where applicable
```

---

# TOP-015 — Add topology REST controllers

```text
Commit code    : TOP-015
Commit message : feat(topology): add topology REST controllers
Type           : Feature
Layer          : API
Module         : topology
```

## Files to create

| File | Purpose |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/PipelineSystemController.java` | REST endpoints for pipeline systems. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/PipelineController.java` | REST endpoints for pipelines. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/FacilityController.java` | REST endpoints for physical facilities. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyNodeController.java` | REST endpoints for topology nodes. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/PipelineSegmentController.java` | REST endpoints for pipeline segments. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/PipelineAppurtenanceController.java` | REST endpoints for pipeline valves, injection points, purge points, extraction points, and other appurtenances. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyConnectionController.java` | REST endpoints for topology connections. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EquipmentController.java` | REST endpoints for equipment references. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-015 status after implementation. |

## Required endpoint base paths

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

Do not use:

```text
/api/v1/topology/stations
```

as the main base path. A station is a facility type, not the root aggregate name.

## Required documentation

Every controller must include:

```text
@Tag
@Operation on every endpoint
@ApiResponses on every endpoint
@Parameter for path variables where useful
```

Recommended response codes:

```text
201 Created
200 OK
400 Bad Request
404 Not Found
409 Conflict
500 Internal Server Error
```

## Rules

```text
- Controllers must be thin.
- Controllers must depend only on inbound application ports and TopologyRestMapper.
- Controllers must not inject repositories.
- Controllers must not use JPA entities.
- Controllers must not implement business rules.
- Controllers must not access identity or organization implementations.
```

## Validation commands

```bash
mvn -q -DskipTests compile
mvn -q test -Dtest='*ControllerTest,*RestMapperTest'
```

## Completion criteria

```text
controllers compile
OpenAPI annotations are present
controllers remain thin
```

---

# TOP-016 — Add topology domain tests

```text
Commit code    : TOP-016
Commit message : test(topology): add topology domain tests
Type           : Test
Layer          : Domain
Module         : topology
```

## Files to create

| File | Purpose |
|---|---|
| `src/test/java/dz/sh/hidra/modules/topology/domain/value/TopologyCodeTest.java` | Tests topology code validation. |
| `src/test/java/dz/sh/hidra/modules/topology/domain/value/TopologyNameTest.java` | Tests topology name validation. |
| `src/test/java/dz/sh/hidra/modules/topology/domain/value/GeoCoordinateTest.java` | Tests coordinate validation. |
| `src/test/java/dz/sh/hidra/modules/topology/domain/value/LengthInKilometersTest.java` | Tests length validation. |
| `src/test/java/dz/sh/hidra/modules/topology/domain/value/PipelineKilometerPointTest.java` | Tests KP/PK/chainage validation. |
| `src/test/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystemTest.java` | Tests pipeline system invariants. |
| `src/test/java/dz/sh/hidra/modules/topology/domain/model/PipelineTest.java` | Tests pipeline invariants. |
| `src/test/java/dz/sh/hidra/modules/topology/domain/model/FacilityTest.java` | Tests physical facility invariants. |
| `src/test/java/dz/sh/hidra/modules/topology/domain/model/PipelineSegmentTest.java` | Tests segment invariants. |
| `src/test/java/dz/sh/hidra/modules/topology/domain/model/PipelineAppurtenanceTest.java` | Tests valve/injection/purge/extraction appurtenance invariants. |
| `src/test/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnectionTest.java` | Tests connection invariants. |
| `src/test/java/dz/sh/hidra/modules/topology/domain/policy/TopologyAssetStatusPolicyTest.java` | Tests allowed/forbidden status transitions. |
| `src/test/java/dz/sh/hidra/modules/topology/domain/policy/TopologyConnectivityPolicyTest.java` | Tests connectivity rules. |
| `src/test/java/dz/sh/hidra/modules/topology/domain/policy/PipelineAppurtenancePolicyTest.java` | Tests valve type and pipeline point asset rules. |
| `src/test/java/dz/sh/hidra/modules/topology/domain/service/TopologyConnectivityDomainServiceTest.java` | Tests multi-object domain connectivity validation. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-016 status after implementation. |

## Rules

```text
- Domain tests must not load Spring context.
- Domain tests must not use database.
- Domain tests must not import JPA entities.
- Domain tests must not import REST DTOs.
```

## Validation commands

```bash
mvn -q test -Dtest='*Topology*Test,*Pipeline*Test,*Facility*Test'
```

## Completion criteria

```text
domain tests pass
domain rules are covered
```

---

# TOP-017 — Add topology application tests

```text
Commit code    : TOP-017
Commit message : test(topology): add topology application tests
Type           : Test
Layer          : Application
Module         : topology
```

## Files to create

| File | Purpose |
|---|---|
| `src/test/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationServiceTest.java` | Tests pipeline system use cases with fake repository ports. |
| `src/test/java/dz/sh/hidra/modules/topology/application/service/PipelineApplicationServiceTest.java` | Tests pipeline use cases. |
| `src/test/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationServiceTest.java` | Tests facility use cases. |
| `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyNodeApplicationServiceTest.java` | Tests topology node use cases. |
| `src/test/java/dz/sh/hidra/modules/topology/application/service/PipelineSegmentApplicationServiceTest.java` | Tests segment use cases. |
| `src/test/java/dz/sh/hidra/modules/topology/application/service/PipelineAppurtenanceApplicationServiceTest.java` | Tests appurtenance use cases. |
| `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyConnectionApplicationServiceTest.java` | Tests connection use cases. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-017 status after implementation. |

## Rules

```text
- Prefer fake in-memory outbound ports unless project standard requires mocks.
- Do not load Spring context unless necessary.
- Do not use JPA entities.
- Do not call real database.
- Verify duplicate code conflicts and not-found flows.
```

## Validation commands

```bash
mvn -q test -Dtest='*ApplicationServiceTest'
```

## Completion criteria

```text
application service tests pass
use-case success and failure paths are covered
```

---

# TOP-018 — Add topology API and mapper tests

```text
Commit code    : TOP-018
Commit message : test(topology): add topology API and mapper tests
Type           : Test
Layer          : API
Module         : topology
```

## Files to create

| File | Purpose |
|---|---|
| `src/test/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyRestMapperTest.java` | Tests REST/application mapping. |
| `src/test/java/dz/sh/hidra/modules/topology/api/rest/controller/PipelineSystemControllerTest.java` | Tests pipeline system controller behavior. |
| `src/test/java/dz/sh/hidra/modules/topology/api/rest/controller/PipelineControllerTest.java` | Tests pipeline controller behavior. |
| `src/test/java/dz/sh/hidra/modules/topology/api/rest/controller/FacilityControllerTest.java` | Tests facility controller behavior. |
| `src/test/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyNodeControllerTest.java` | Tests topology node controller behavior. |
| `src/test/java/dz/sh/hidra/modules/topology/api/rest/controller/PipelineSegmentControllerTest.java` | Tests segment controller behavior. |
| `src/test/java/dz/sh/hidra/modules/topology/api/rest/controller/PipelineAppurtenanceControllerTest.java` | Tests appurtenance controller behavior. |
| `src/test/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyConnectionControllerTest.java` | Tests connection controller behavior. |

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update TOP-018 status after implementation. |

## Rules

```text
- Controller tests must not depend on real database.
- Controller tests must not use JPA entities.
- Mapper tests must cover null/invalid mapping where applicable.
- Verify 201, 200, 400, 404, and 409 paths where feasible.
```

## Validation commands

```bash
mvn -q test -Dtest='*ControllerTest,*RestMapperTest'
```

## Completion criteria

```text
API and mapper tests pass
REST contracts are covered
```

---

# TOP-019 — Add topology architecture guardrails

```text
Commit code    : TOP-019
Commit message : test(topology): add topology architecture guardrails
Type           : Test
Layer          : Architecture
Module         : topology
```

## Files to create or update

| File | Purpose |
|---|---|
| `src/test/java/dz/sh/hidra/modules/topology/architecture/TopologyArchitectureTest.java` | Verifies topology layer boundaries and forbidden dependencies. |

## Required checks

```text
domain must not depend on Spring
domain must not depend on JPA
domain must not depend on REST DTOs
topology must not depend on identity implementation
topology must not depend on organization implementation
topology must not depend on measurement/flow/risk/analytics/workflow/reporting/notification
controllers must not depend on repositories
application must not depend on infrastructure
Facility must not import organization Employee/OrganizationUnit aggregate
PipelineAppurtenance must not import measurement/operations/flow/risk modules
```

## Rules

```text
- Do not add a new architecture testing dependency unless approved.
- If ArchUnit or equivalent dependency already exists, use it.
- If no architecture testing dependency exists, document this task as blocked and do not add dependencies without approval.
```

## Validation commands

```bash
mvn -q test -Dtest='TopologyArchitectureTest'
```

## Completion criteria

```text
architecture boundaries are enforced by tests or blocker is explicitly documented
```

---

# TOP-020 — Finalize topology roadmap status

```text
Commit code    : TOP-020
Commit message : docs(topology): finalize topology roadmap status
Type           : Documentation
Layer          : Documentation
Module         : topology
```

## Files to update

| File | Purpose |
|---|---|
| `docs/roadmap/topology.md` | Update final status table, validations, blockers, risks, and next recommended module. |

## Required final validation commands

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Manual checks:

```text
GET http://localhost:8080/actuator/health
GET http://localhost:8080/swagger-ui.html
GET http://localhost:8080/v3/api-docs
GET http://localhost:8080/api/v1/topology/pipeline-systems
GET http://localhost:8080/api/v1/topology/pipelines
GET http://localhost:8080/api/v1/topology/facilities
GET http://localhost:8080/api/v1/topology/nodes
GET http://localhost:8080/api/v1/topology/pipeline-segments
GET http://localhost:8080/api/v1/topology/pipeline-appurtenances
```

## Completion criteria

```text
topology roadmap accurately reflects final implementation status
all validations are recorded
remaining risks are explicit
next module recommendation is recorded
```

---

## 17. Recommended next module after topology

After topology is implemented and validated, the next module should be:

```text
measurement
```

Reason:

```text
measurement needs stable topology references for measurement points, sensors, SCADA tags,
historian tags, manual readings, and operational data quality.
```

Do not start flow before measurement exists.

---

## 18. Final status table

| Commit code | Status | Validation result | Notes |
|---|---:|---|---|
| `TOP-001` | Planned | Pending | Add this topology roadmap only. |
| `TOP-002` | Completed locally | Static ZIP validation passed | Added production package-info.java skeleton files only; Maven compile must be run after copying into the repository. |
| `TOP-003` | Completed locally | Static/Javac validation passed for generated value objects | Added topology domain value records and enums only; Maven compile must be run after copying into the repository. |
| `TOP-004` | Completed locally | Static/Javac validation passed for generated domain models | Added topology domain models only; Maven compile must be run after copying into the repository. |
| `TOP-005` | Completed locally | Static/Javac validation passed for generated policies and exceptions | Added topology domain policies and topology domain exceptions only; Maven compile must be run after copying into the repository. |
| `TOP-006` | Completed locally | Static/Javac validation passed for generated domain services | Added pure topology domain services only; Maven compile must be run after copying into the repository. |
| `TOP-007` | Completed locally | Static/Javac validation passed for generated commands and queries | Added topology application command and query records only; Maven compile must be run after copying into the repository. |
| `TOP-008` | Completed locally | Static/Javac validation passed for generated application DTOs | Added topology application DTO records only; Maven compile must be run after copying into the repository. |
| `TOP-009` | Completed locally | Static/Javac validation passed for generated application ports | Added topology application inbound and outbound port interfaces only; Maven compile must be run after copying into the repository. |
| `TOP-010` | Completed locally | Static/Javac validation passed for generated application services and configuration | Added topology application service classes and infrastructure bean wiring only; Maven compile must be run after copying into the repository. |
| `TOP-011` | Completed locally | Static/Javac validation passed for generated JPA entities and repository interfaces | Added topology JPA entities and Spring Data repository interfaces only; Maven compile must be run after copying into the repository. |
| `TOP-012` | Completed locally | Static/Javac validation passed for generated persistence mapper, adapters, and configuration update | Added topology persistence mapper, repository adapters, and configuration wiring updates only; Maven compile must be run after copying into the repository. |
| `TOP-013` | Planned | Pending | Add Flyway migration. |
| `TOP-014` | Planned | Pending | Add REST DTOs and mapper. |
| `TOP-015` | Planned | Pending | Add REST controllers. |
| `TOP-016` | Planned | Pending | Add domain tests. |
| `TOP-017` | Planned | Pending | Add application tests. |
| `TOP-018` | Planned | Pending | Add API and mapper tests. |
| `TOP-019` | Planned | Pending | Add architecture guardrails or document blocker. |
| `TOP-020` | Planned | Pending | Finalize roadmap status. |


---

## TOP-002 Local Execution Note

```text
Commit code    : TOP-002
Commit message : chore(topology): add topology package skeleton
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only production `package-info.java` files listed in TOP-002 were generated.

### Validation performed locally

```text
- verified every listed production package-info.java file exists
- verified no test package-info.java files were created
- verified no classes, records, enums, services, repositories, controllers, DTOs, entities, mappers, configurations, migrations, or tests with behavior were created
- verified no identityaccess package was created
- verified no shared/sharedkernel/common/core/utils/helper/helpers/misc packages were created
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
```


---

## TOP-003 Local Execution Note

```text
Commit code    : TOP-003
Commit message : feat(topology): add topology domain value objects
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology domain value records and enums listed in TOP-003 were generated.

### Validation performed locally

```text
- verified all expected value object files exist
- verified every Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified no Spring, JPA, REST, identity, or organization implementation imports exist
- verified generated value objects compile with local stubs for kernel ValueObject and InvalidValueObjectException
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
```


---

## TOP-004 Local Execution Note

```text
Commit code    : TOP-004
Commit message : feat(topology): add topology domain models
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology domain models listed in TOP-004 were generated.

### Validation performed locally

```text
- verified all expected domain model files exist
- verified every Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified no Spring, JPA, REST, identity, organization, measurement, flow, risk, workflow, or infrastructure imports exist
- verified generated domain models compile with local stubs for kernel contracts/exceptions and generated value objects
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
```


---

## TOP-005 Local Execution Note

```text
Commit code    : TOP-005
Commit message : feat(topology): add topology domain policies
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology domain policies and topology domain exceptions listed in TOP-005 were generated.

### Validation performed locally

```text
- verified all expected policy and exception files exist
- verified every Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified no Spring, JPA, REST, identity, organization, measurement, flow, risk, workflow, application, API, persistence, or infrastructure imports exist
- verified generated policies and exceptions compile with local stubs for kernel contracts/exceptions and generated topology values/models
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
```


---

## TOP-006 Local Execution Note

```text
Commit code    : TOP-006
Commit message : feat(topology): add topology domain services
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology domain services listed in TOP-006 were generated.

### Validation performed locally

```text
- verified all expected domain service files exist
- verified every Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified no Spring, JPA, REST, identity, organization, measurement, flow, risk, workflow, application, API, persistence, or infrastructure imports exist
- verified generated domain services compile with local stubs for kernel contracts/exceptions and generated topology values/models/policies
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
```


---

## TOP-007 Local Execution Note

```text
Commit code    : TOP-007
Commit message : feat(topology): add topology application commands and queries
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology application command and query records listed in TOP-007 were generated.

### Validation performed locally

```text
- verified all expected command and query files exist
- verified every Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified no Spring, JPA, REST, identity, organization, measurement, flow, risk, workflow, API, persistence, infrastructure, DTO, port, or service imports exist
- verified generated commands and queries compile with local stubs for kernel contracts and generated topology domain values/models/policies/services
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
```


---

## TOP-008 Local Execution Note

```text
Commit code    : TOP-008
Commit message : feat(topology): add topology application DTOs
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology application DTO records listed in TOP-008 were generated.

### Validation performed locally

```text
- verified all expected application DTO files exist
- verified every Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified no Spring, JPA, REST, Swagger, identity, organization, measurement, flow, risk, workflow, API, persistence, infrastructure, port, or service imports exist
- verified generated application DTOs compile with local stubs for kernel contracts and generated topology domain/application files
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
```


---

## TOP-009 Local Execution Note

```text
Commit code    : TOP-009
Commit message : feat(topology): add topology application ports
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology application inbound and outbound port interfaces listed in TOP-009 were generated.

### Validation performed locally

```text
- verified all expected inbound and outbound port files exist
- verified every Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified no Spring, JPA, REST, Swagger, identity, organization, measurement, flow, risk, workflow, API, persistence, infrastructure, service, or controller imports exist
- verified generated application ports compile with local stubs for kernel contracts and generated topology domain/application files
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
```


---

## TOP-010 Local Execution Note

```text
Commit code    : TOP-010
Commit message : feat(topology): add topology application services
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology application service classes and `TopologyConfiguration` listed in TOP-010 were generated.

### Validation performed locally

```text
- verified all expected application service files exist
- verified TopologyConfiguration exists
- verified every generated Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified application services do not import Spring, JPA, REST, Swagger, identity, organization, measurement, flow, risk, workflow, API, persistence, or infrastructure packages
- verified TopologyConfiguration is the only Spring-specific generated file
- verified generated services and configuration compile with local stubs for kernel and Spring annotations
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
```


---

## TOP-011 Local Execution Note

```text
Commit code    : TOP-011
Commit message : feat(topology): add topology persistence entities and repositories
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology JPA entities and Spring Data repository interfaces listed in TOP-011 were generated.

### Validation performed locally

```text
- verified all expected JPA entity and Spring Data repository files exist
- verified every generated Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified no migration files were created
- verified no persistence mapper or persistence adapter files were created
- verified no REST DTOs, controllers, tests, or API files were created
- verified generated entities and repositories compile with local stubs for kernel, Spring, Spring Data JPA, and Jakarta Persistence
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
```


---

## TOP-012 Local Execution Note

```text
Commit code    : TOP-012
Commit message : feat(topology): add topology persistence mapper and adapters
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology persistence mapper, repository adapters, and `TopologyConfiguration` wiring updates listed in TOP-012 were generated.

### Validation performed locally

```text
- verified expected persistence mapper and adapter files exist
- verified every generated Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified no Flyway migration files were created
- verified no controllers, REST DTOs, Swagger files, or tests were created
- verified generated mapper/adapters/configuration compile with local stubs for kernel, Spring, Spring Data JPA, and Jakarta Persistence
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
```


---

## TOP-013 Local Execution Note

```text
Commit code    : TOP-013
Commit message : db(topology): add topology flyway migration
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only the topology Flyway migration was generated for TOP-013.

### Validation performed locally

```text
- verified exactly one new Flyway migration was created for TOP-013
- verified migration filename is V002__create_topology_tables.sql
- verified all expected topology tables are declared
- verified primary keys, unique code constraints, check constraints, foreign keys, and indexes are declared
- verified no Java files were generated or modified by TOP-013 logic
- verified no controllers, REST DTOs, Swagger files, tests, or identityaccess files were created
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
mvn -q -DskipTests flyway:migrate
```

If Flyway is not configured as a Maven plugin, run the application with the `dev` profile against a clean database and verify the migration is applied.


---

## TOP-014 Local Execution Note

```text
Commit code    : TOP-014
Commit message : feat(topology): add topology REST request DTOs
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology REST request DTO records listed in TOP-014 were generated.

### Validation performed locally

```text
- verified all expected topology REST request DTO files exist
- verified every generated Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified every generated request DTO uses @Schema
- verified generated request DTOs use Bean Validation constraints
- verified no controllers, REST response DTOs, REST mappers, application services, persistence files, migrations, or tests were created by TOP-014
- verified generated request DTOs compile with local stubs for Swagger and Bean Validation annotations
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
```


---

## TOP-015 Local Execution Note

```text
Commit code    : TOP-015
Commit message : feat(topology): add topology REST response DTOs
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology REST response DTO records listed in TOP-015 were generated.

### Validation performed locally

```text
- verified all expected topology REST response DTO files exist
- verified every generated Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified every generated response DTO uses @Schema
- verified every response record component has @Schema
- verified generated response DTOs do not use Bean Validation annotations
- verified no controllers, REST mapper, request DTO changes, application services, persistence files, migrations, or tests were created by TOP-015
- verified generated response DTOs compile with local stubs for Swagger annotations
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
```


---

## TOP-016 Local Execution Note

```text
Commit code    : TOP-016
Commit message : feat(topology): add topology REST mapper
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only the topology REST mapper and mapper bean configuration were generated for TOP-016.

### Validation performed locally

```text
- verified TopologyRestMapper exists
- verified TopologyApiRestConfiguration exists
- verified every generated Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified mapper converts REST request DTOs to application commands
- verified mapper converts application DTOs to REST response DTOs
- verified mapper converts list query parameters to application queries
- verified mapper converts PageResult application DTO pages to PageResult response pages
- verified no controllers, application services, persistence files, migrations, or tests were created by TOP-016
- verified generated REST mapper and configuration compile with local stubs for kernel, Spring, Swagger, Bean Validation, Spring Data JPA, and Jakarta Persistence
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
```


---

## TOP-017 Local Execution Note

```text
Commit code    : TOP-017
Commit message : feat(topology): add topology REST controllers
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology REST controllers were generated for TOP-017.

### Validation performed locally

```text
- verified all expected topology REST controller files exist
- verified every generated Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified controllers depend only on topology application inbound ports and TopologyRestMapper
- verified no unsupported get/list endpoints were invented for ports that do not exist
- verified no request DTOs, response DTOs, REST mapper, application services, persistence files, migrations, or tests were created by TOP-017
- verified generated controllers compile with local stubs for kernel, Spring Web, Swagger, Bean Validation, Spring Data JPA, and Jakarta Persistence
```

### Validation still required after copying into repository

```bash
mvn -q -DskipTests compile
```


---

## TOP-018 Local Execution Note

```text
Commit code    : TOP-018
Commit message : test(topology): add topology domain tests
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology domain test files were generated for TOP-018.

### Validation performed locally

```text
- verified all expected topology domain test files exist
- verified every generated test Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified tests target value objects, domain models, domain policies, and domain services
- verified no production Java files were created or modified by TOP-018
- verified no API tests, application tests, persistence tests, migrations, controllers, services, mappers, repositories, or DTOs were created by TOP-018
- verified generated test sources compile with local stubs for kernel and JUnit
```

### Validation still required after copying into repository

```bash
mvn -q test
```


---

## TOP-019 Local Execution Note

```text
Commit code    : TOP-019
Commit message : test(topology): add topology application service tests
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology application service test files were generated for TOP-019.

### Validation performed locally

```text
- verified all expected topology application service test files exist
- verified every generated test Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified tests target application service classes and application outbound ports only
- verified tests use in-memory outbound port fakes, not Mockito, Spring, JPA, REST, or database infrastructure
- verified no production Java files were created or modified by TOP-019
- verified no API tests, persistence tests, migration files, controllers, DTOs, mappers, services, repositories, or adapters were created by TOP-019
- verified generated test sources compile with local stubs for kernel and JUnit
```

### Validation still required after copying into repository

```bash
mvn -q test
```


---

## TOP-020 Local Execution Note

```text
Commit code    : TOP-020
Commit message : test(topology): add topology persistence tests
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology persistence test files were generated for TOP-020.

### Validation performed locally

```text
- verified all expected topology persistence test files exist
- verified every generated test Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified tests target JPA entities, Spring Data repository interfaces, persistence mapper, and persistence adapters
- verified tests do not require Spring Boot context, PostgreSQL, Testcontainers, H2, Flyway execution, or Mockito
- verified no production Java files were created or modified by TOP-020
- verified no API tests, application service tests, migration files, controllers, DTOs, mappers, repositories, adapters, or services were created by TOP-020
- verified generated test sources compile with local stubs for kernel, JUnit, Jakarta Persistence, and Spring Data JPA
```

### Validation still required after copying into repository

```bash
mvn -q test
```


---

## TOP-021 Local Execution Note

```text
Commit code    : TOP-021
Commit message : test(topology): add topology REST mapper and controller tests
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only topology REST mapper/controller test files were generated for TOP-021.

### Validation performed locally

```text
- verified all expected topology REST mapper/controller test files exist
- verified every generated test Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified tests target TopologyRestMapper, TopologyApiRestConfiguration, and REST controllers
- verified tests instantiate controllers directly with fake inbound ports, without Spring Boot context or MockMvc
- verified tests do not require databases, Flyway execution, JPA, Mockito, Testcontainers, or web server startup
- verified no production Java files were created or modified by TOP-021
- verified no migrations, application service tests, persistence tests, DTOs, controllers, mappers, services, repositories, or adapters were created by TOP-021
- verified generated test sources compile with local stubs for kernel, JUnit, Spring Web, Swagger, Bean Validation, Spring Data JPA, and Jakarta Persistence
```

### Validation still required after copying into repository

```bash
mvn -q test
```


---

## TOP-022 Local Execution Note

```text
Commit code    : TOP-022
Commit message : test(topology): add topology application boot smoke test
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated

Only one topology application boot smoke test was generated for TOP-022.

### Validation performed locally

```text
- verified the expected topology boot smoke test file exists
- verified the generated test Java file uses the canonical HidraAPI header
- verified @Author remains Abir MEDJERAB
- verified @CreatedOn remains 2025-06-26
- verified @Module is topology
- verified @Package matches actual package
- verified the test uses @SpringBootTest against HidraApplication
- verified the test uses PostgreSQL Testcontainers and does not disable JPA, Flyway, repositories, security, or topology wiring
- verified the test asserts topology REST mapper, controllers, application use cases, application services, domain service/policy beans, persistence mapper, and repository ports are registered
- verified no production Java files were created or modified by TOP-022
- verified no migrations, unit tests, controllers, DTOs, mappers, services, repositories, or adapters were created by TOP-022
- verified generated test source compiles with local stubs for kernel, JUnit, Spring Boot Test, Spring context, Testcontainers, and topology production sources
```

### Validation still required after copying into repository

```bash
mvn -q test -Dtest=TopologyApplicationBootSmokeTest
```

This test requires a Docker-compatible runtime because it starts PostgreSQL with Testcontainers.

| `TOP-023` | `docs(topology): finalize topology validation checklist` | Completed locally | Added final topology validation checklist and validation commands. |


---

## TOP-023 Local Execution Note

```text
Commit code    : TOP-023
Commit message : docs(topology): finalize topology validation checklist
Execution mode : Local ZIP generation only
GitHub push    : Not performed
Git commit     : Not created
```

### Files generated or updated

```text
docs/roadmap/topology.md
docs/roadmap/topology_validation_checklist.md
README-TOP-023.md
```

### Validation performed locally

```text
- verified the topology validation checklist file exists
- verified the topology roadmap file exists
- verified TOP-023 is documented in the roadmap
- verified the checklist contains commit code, commit message, module scope, validation commands, acceptance criteria, and remaining risks
- verified the checklist covers topology data conception, including terminals, production fields, processing plants, injection points, extraction points, purge points, and valve subtypes
- verified the checklist includes Linux/macOS/Git Bash and Windows PowerShell validation commands
- verified TOP-023 created no production Java files
- verified TOP-023 created no test Java files
- verified TOP-023 created no migration files
- verified TOP-023 created no forbidden packages
```

### Validation still required after copying into repository

```bash
test -f docs/roadmap/topology_validation_checklist.md
test -f docs/roadmap/topology.md
mvn -q test
```

Windows PowerShell:

```powershell
Test-Path docs/roadmap/topology_validation_checklist.md
Test-Path docs/roadmap/topology.md
mvn -q test
```
