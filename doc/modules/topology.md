# Topology Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.topology`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns pipeline-system/topology identity including pipelines, facilities, equipment and topology connections.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [Equipment](../../src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java)
- [Facility](../../src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java)
- [Pipeline](../../src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java)
- [PipelineSystem](../../src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java)
- [TopologyConnection](../../src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java)

Domain policies:

- [TopologyBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/topology/domain/policy/TopologyBoundaryPolicy.java)

Domain services:

- [TopologyConnectionValidator](../../src/main/java/dz/sh/hidra/modules/topology/domain/service/TopologyConnectionValidator.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CreatePipelineSystemUseCase](../../src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java)
- [RegisterFacilityUseCase](../../src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java)
- [TopologyMapVisualizationUseCase](../../src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java)

Current application services:

- [AssetsTopologyReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryService.java)
- [DocumentsTopologyTargetLookup](../../src/main/java/dz/sh/hidra/modules/topology/application/service/DocumentsTopologyTargetLookup.java)
- [FacilityApplicationService](../../src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java)
- [PipelineSystemApplicationService](../../src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java)
- [PlanningTopologyScopeQueryService](../../src/main/java/dz/sh/hidra/modules/topology/application/service/PlanningTopologyScopeQueryService.java)
- [RiskTopologyScopeReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java)
- [TopologyOperationalScopeTargetQueryService](../../src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java)
- [TopologySimulationScopeQueryService](../../src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryService.java)

Current API/controller classes:

- [SpringTopologyController](../../src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java)
- [TopologyController](../../src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyController.java)
- [TopologyMapController](../../src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **22** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [ConnectionTypeJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/ConnectionTypeJpaEntity.java)
- [EquipmentAttributeDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeDefinitionJpaEntity.java)
- [EquipmentAttributeValueJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeValueJpaEntity.java)
- [EquipmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java)
- [EquipmentTypeJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentTypeJpaEntity.java)
- [EquipmentTypeVersionJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentTypeVersionJpaEntity.java)
- [FacilityAttributeDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeDefinitionJpaEntity.java)
- [FacilityAttributeValueJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeValueJpaEntity.java)
- [FacilityJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java)
- [FacilityNodeBindingJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityNodeBindingJpaEntity.java)
- [FacilityTypeJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityTypeJpaEntity.java)
- [FacilityTypeVersionJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityTypeVersionJpaEntity.java)
- [MeasurementLocationJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/MeasurementLocationJpaEntity.java)
- [PipelineJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java)
- [PipelineSegmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java)
- [PipelineSystemFacilityJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java)
- [PipelineSystemJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java)
- [PipelineSystemTypeJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemTypeJpaEntity.java)
- [PipelineTypeJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineTypeJpaEntity.java)
- [TopologyConnectionJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java)
- [TopologyNodeJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyNodeJpaEntity.java)
- [TopologySnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologySnapshotJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaEquipmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaEquipmentRepositoryAdapter.java)
- [JpaFacilityRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaFacilityRepositoryAdapter.java)
- [JpaPipelineRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineRepositoryAdapter.java)
- [JpaPipelineSystemRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineSystemRepositoryAdapter.java)
- [JpaTopologyConnectionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaTopologyConnectionRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

- [AnalyticsTopologyScopeContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/analytics/AnalyticsTopologyScopeContract.java)
- [AssetsTopologyReferenceContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java)
- [IncidentTopologyContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/incident/IncidentTopologyContract.java)
- [IntegrityCaseTopologyReferenceContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/integrity/IntegrityCaseTopologyReferenceContract.java)
- [LeakDetectionTopologyAssetContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/leakdetection/LeakDetectionTopologyAssetContract.java)
- [TopologyOperationalScopeTargetContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/organization/TopologyOperationalScopeTargetContract.java)
- [PlanningTargetTopologyReferenceContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java)
- [PlanningTopologyScopeContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTopologyScopeContract.java)
- [RiskTopologyScopeReferenceContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java)
- [SimulationTopologyScopeContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java)
- [SimulationTopologyTargetContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java)

Imported scalar contracts supplied by collaborating owners:

- [DocumentsOwnedTargetLookup](../../src/main/java/dz/sh/hidra/modules/documents/application/contract/target/DocumentsOwnedTargetLookup.java)
- [TopologyPartyReferenceContract](../../src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyReferenceContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [EquipmentRepositoryPort](../../src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java)
- [FacilityRepositoryPort](../../src/main/java/dz/sh/hidra/modules/topology/application/port/out/FacilityRepositoryPort.java)
- [PipelineRepositoryPort](../../src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineRepositoryPort.java)
- [PipelineSystemRepositoryPort](../../src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java)
- [TopologyConnectionRepositoryPort](../../src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyConnectionRepositoryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Topology owns network graph and physical identities. Catalog-backed pipeline/system/connection/equipment types remain extensible; graph/attachment integrity and fresh optional Party references are validated without transferring maintenance or organizational ownership.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| PipelineSystem | [HMSR-024 reconciled rule](../domain/SEMANTIC_DECISIONS.md#topology-pipelinesystem) |
| TopologyConnection | [HMSR-034 reconciled rule](../domain/SEMANTIC_DECISIONS.md#topology-topologyconnection) |
| Facility | [HMSR-041 reconciled rule](../domain/SEMANTIC_DECISIONS.md#topology-facility) |
| Pipeline | [HMSR-050 reconciled rule](../domain/SEMANTIC_DECISIONS.md#topology-pipeline) |
| Equipment | [HMSR-063 reconciled rule](../domain/SEMANTIC_DECISIONS.md#topology-equipment) |

## Phase 2.5 physical source revisions

Topology owns [immutable physical network revisions](../../src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevision.java), independently of mutable live assets and the existing topology snapshot lifecycle. Sources carry explicit source/revision/scope identities, full-precision effective times, declared origin/evidence, ordered nodes/pipes and compressor/valve incidence. SI geometry is supplied, with no defaults or live nominal-size inference. Equipment incidence does not provide curves, operating state or solver qualification.

The internal [revision port](../../src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyPhysicalNetworkRevisionRepositoryPort.java) and [JDBC adapter](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter.java) append canonical binary payloads, compute SHA-256 and verify every exact-source/revision read. Identical replay is atomic; conflicting content for one identity is rejected. The [forward migration](../../src/main/resources/db/migration/V20261009_001__p25_topology_physical_network_revisions.sql) adds a separate JDBC-owned table and ordinary-write UPDATE/DELETE/TRUNCATE protections; administrators can still disable database protections.

The already exported [Simulation contract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContract.java) and [query service](../../src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java) return immutable standard-Java DTOs for exact identities; absence is explicit and integrity failure propagates. No latest-revision fallback, public write API, Simulation resolver, live eligibility inference or operational approval is added. Synthetic revisions remain development evidence; actual GZ2 data and separate qualification/calibration gate operational claims. Runtime validation of this candidate remains pending; see the execution memories.
