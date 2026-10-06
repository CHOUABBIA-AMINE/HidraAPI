# Topology Module

## Status

CURRENT — canonical HPR-P2-004 module document.

## Verification Baseline

Source baseline: `f3c703048402f3dcf1a520aceea64e64c4861035`

Package root: `dz.sh.hidra.modules.topology`

This document describes source-visible current state. It does not promote legacy `docs/**` material to authority and does not substitute for the canonical API/database contracts created by later P2 tasks.

## Responsibility

Owns pipeline-system/topology identity including pipelines, facilities, equipment and topology connections.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types:

- `Equipment`
- `Facility`
- `Pipeline`
- `PipelineSystem`
- `TopologyConnection`

Domain policies: `TopologyBoundaryPolicy`

Domain services: `TopologyConnectionValidator`

Canonical semantic context: `../domain/TOPOLOGY_TELEMETRY.md`.

## Application and API Surface

Current inbound/use-case ports:

- `CreatePipelineSystemUseCase`
- `RegisterFacilityUseCase`
- `TopologyMapVisualizationUseCase`

Current application services:

- `AssetsTopologyReferenceQueryService`
- `FacilityApplicationService`
- `PipelineSystemApplicationService`
- `RiskTopologyScopeReferenceQueryService`
- `TopologyOperationalScopeTargetQueryService`
- `TopologySimulationScopeQueryService`

Current API/controller classes:

- `SpringTopologyController`
- `TopologyController`
- `TopologyMapController`

These class inventories establish implemented adapters/use-case surfaces. Exact HTTP paths, request/response schemas, authentication requirements and compatibility semantics are HPR-P2-005 scope.

## Persistence

Current JPA persistence entity count: **22**.

Persistence entities:

- `ConnectionTypeJpaEntity`
- `EquipmentAttributeDefinitionJpaEntity`
- `EquipmentAttributeValueJpaEntity`
- `EquipmentJpaEntity`
- `EquipmentTypeJpaEntity`
- `EquipmentTypeVersionJpaEntity`
- `FacilityAttributeDefinitionJpaEntity`
- `FacilityAttributeValueJpaEntity`
- `FacilityJpaEntity`
- `FacilityNodeBindingJpaEntity`
- `FacilityTypeJpaEntity`
- `FacilityTypeVersionJpaEntity`
- `MeasurementLocationJpaEntity`
- `PipelineJpaEntity`
- `PipelineSegmentJpaEntity`
- `PipelineSystemFacilityJpaEntity`
- `PipelineSystemJpaEntity`
- `PipelineSystemTypeJpaEntity`
- `PipelineTypeJpaEntity`
- `TopologyConnectionJpaEntity`
- `TopologyNodeJpaEntity`
- `TopologySnapshotJpaEntity`

Current persistence repository-adapter classes:

- `JpaEquipmentRepositoryAdapter`
- `JpaFacilityRepositoryAdapter`
- `JpaPipelineRepositoryAdapter`
- `JpaPipelineSystemRepositoryAdapter`
- `JpaTopologyConnectionRepositoryAdapter`

Table/schema ownership and the generated data dictionary are HPR-P2-006 scope; class presence is not used here to invent database constraints or retention policy.

## Cross-Module Boundary

Exported contracts owned by this module:

- consumer `analytics`: `AnalyticsTopologyScopeContract`
- consumer `assets`: `AssetsTopologyReferenceContract`
- consumer `leakdetection`: `LeakDetectionTopologyAssetContract`
- consumer `organization`: `TopologyOperationalScopeTargetContract`
- consumer `risk`: `RiskTopologyScopeReferenceContract`
- consumer `simulation`: `SimulationTopologyScopeContract`

Current outbound application ports used to reach persistence or collaborating capabilities:

- `EquipmentRepositoryPort`
- `FacilityRepositoryPort`
- `PipelineRepositoryPort`
- `PipelineSystemRepositoryPort`
- `TopologyConnectionRepositoryPort`

Cross-module collaboration must preserve the canonical architecture rule: no direct import of another module's private domain, infrastructure or non-exported application packages.

## Current-State Limits

- File/class presence documents implementation structure, not proof that every business workflow, external dependency or production integration is exercised.
- HPR-P2-004 does not resolve legacy HMR/HMSR semantic obligations; HPR-P2-007/008 remain the reconciliation/remediation authority.
- `agents`, `environment` and `otsecurity` are not current implemented module roots and are not implied by this document.
- PostGIS is not implemented; topology identity and geometry-related semantics must not be described as PostGIS persistence.
