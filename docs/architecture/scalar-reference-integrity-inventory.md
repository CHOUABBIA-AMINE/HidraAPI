# Scalar Reference Integrity Inventory

**Recorded:** 2026-09-29  
**Roadmap:** HRA-110  
**Live main:** `0a2a5597cd6f6356acec4ba8e8fbc30884f7e6ea`  
**Verified source snapshot SHA-256:** `396e6206040b0acdd79c84b45ead0a6fa4329ffc527e95729982af36b383018a`

## 1. Purpose

HRA-110 inventories scalar JPA reference identifiers before HRA-111 changes application/domain
validation or database constraints.

The inventory covers non-primary entity fields whose Java name ends in `Id`. The HRA-111
enforcement scope is restricted to **mandatory** references: fields mapped with explicit
`@Column(nullable = false)`.

HRA-061 retained all JPA entities/repositories and left Flyway/schema unchanged. The only later
commit before HRA-110 is HRA-080 documentation, so the verified source snapshot remains valid for
persistence-entity field inventory at this execution point.

## 2. Inventory totals

Across **465 JPA entities**:

- non-primary scalar `*Id` fields: **1,447**;
- mandatory scalar `*Id` fields (`nullable = false`): **698**;
- optional/non-mandatory scalar `*Id` fields: **749**.

Every one of the 698 mandatory scalar references has an integrity owner below.

| Classification | Mandatory count | Integrity policy |
|---|---:|---|
| `SAME_MODULE_REFERENCE` | **572** | HRA-111 enforcement candidate. Validate fail-closed in module/application logic and add/retain same-module DB FK/check protection where live schema evidence supports it. |
| `CROSS_MODULE_STABLE_REFERENCE` | **74** | Owning module validates through an exported contract/application boundary when needed. **Never add a cross-module DB FK.** |
| `HISTORICAL_SNAPSHOT` | **5** | Preserve immutable/historical identity semantics. Do not convert to a live cross-module FK merely because an entity exists. |
| `EXTERNAL_IDENTIFIER` | **2** | Integrity belongs to the external namespace/integration contract, not an internal DB FK. |
| `NON_RELATIONAL_IDENTIFIER` | **45** | Typed/polymorphic/correlation/reference namespace; validate discriminator/format locally but do not force a relational FK. |
| **Total** | **698** | |

Therefore HRA-111's maximum classified internal-reference population is **572 mandatory
same-module references**. The other **126 mandatory IDs are explicitly outside cross-table FK
consolidation** unless this classification is amended with new evidence.

## 3. Deterministic ownership rules

Mandatory references are classified in this order.

### 3.1 Historical snapshots

The following are historical/snapshot ownership, even when a same-module snapshot entity exists:

- `analytics.DigitalTwinReadinessAssessment.topologySnapshotId` → `topology.TopologySnapshot`;
- `simulation.SimulationScenario.topologySnapshotId` → `topology.TopologySnapshot`;
- `simulation.SimulationInputSnapshot.topologySnapshotId` → `topology.TopologySnapshot`;
- `simulation.SimulationRun.inputSnapshotId` → `simulation.SimulationInputSnapshot`;
- `simulation.SimulationInputDataset.inputSnapshotId` → `simulation.SimulationInputSnapshot`.

### 3.2 External identifiers

- `documents.DocumentExternalReference.externalObjectId`;
- `integration.ExternalObjectReference.externalObjectId`.

These identify objects in an external system/object namespace and are not internal FK targets.

### 3.3 Cross-module stable references

The following mandatory patterns are cross-module ownership:

- every non-Identity `actorId` / `*ByActorId` reference → Identity actor identity: **51**;
- `topologyAssetId` → Topology asset identity: **23**;
- `workflowInstanceId` outside Workflow → `workflow.WorkflowInstance`: **2**;
- non-Documents `documentReferenceId` / `documentId` → Documents-owned document identity: **4**;
- `assets.AssetManufacturerReference.manufacturerPartyId` → `party.Party`;
- `custody.CustodyAgreementParty.partyRoleId` → `party.PartyRole`;
- `monitoring.PlanActualDeviation.planTargetId` → `planning.PlanTarget`;
- `documents.DocumentExternalReference.externalSystemId` → `integration.ExternalSystem`;
- `planning.OperationalPlan.topologyScopeId` → Topology operational-scope identity.

Cross-module stable references are allowed scalar IDs, but HRA-111 must not introduce database
foreign keys across business modules.

### 3.4 Non-relational / typed identifiers

These mandatory identifiers are intentionally not direct relational ownership:

- generic/polymorphic `targetId`: **17**;
- `evidenceReferenceId`: **5**;
- `referenceId`: **2**;
- `recipientReferenceId`: **2**;
- `sourceId`: **2**;
- `sourceObjectId`: **2**;
- one each of `scopeReferenceId`, `evidenceId`, `exposedObjectId`,
  `memberReferenceId`, `entityId`, `sourceReferenceId`, `ownerId`,
  `targetReferenceId`, `assigneeId`, `analyticsObjectId`, `accessScopeId`;
- `notification.NotificationRequest.sourceEventId` → source-event/correlation namespace;
- `documents.DocumentAccessGrant.principalId` → discriminator-owned
  `principalType + principalId`;
- `identity.SubjectSecurityAttribute.subjectId` → discriminator-owned subject identity;
- `integration.IntegrationRetryAttempt.targetRecordId` → retry-target record namespace;
- `audit.AuditEvidenceLink.referenceId` remains non-relational because the evidence target is
  typed/reference based rather than a single table.

For these references HRA-111 may add discriminator/format validation where evidence already defines
it, but must not invent a relational target.

### 3.5 Same-module references

A mandatory ID is `SAME_MODULE_REFERENCE` when one of these rules resolves exactly one owner:

1. removing the `Id` suffix identifies a JPA entity in the same module, or uniquely matches the
   semantic suffix of one same-module entity — **378 fields**;
2. a known same-module alias resolves the field to a concrete owner — **22 fields**;
3. a module-owned catalog ID (`*TypeId`, `*CategoryId`, `*SeverityId`, `*ReasonId`,
   `*UnitId`, `*LevelId`, `*StatusId`, `*MethodId`, etc.) resolves to the module's
   `*CatalogEntry`/method catalog — **159 fields**;
4. an evidence-backed explicit exception resolves to a concrete same-module owner — **13 fields**.

Total: **572**.

## 4. Same-module alias resolutions

| Mandatory reference | Integrity owner |
|---|---|
| `party.PartyRelationship.sourcePartyId` | `party.Party` |
| `party.PartyRelationship.targetPartyId` | `party.Party` |
| `party.PartyOwnershipLink.ownerPartyId` | `party.Party` |
| `identity.AuthorizationDelegationGrant.delegatorUserId` | `identity.User` |
| `identity.AuthorizationDelegationGrant.delegateUserId` | `identity.User` |
| `topology.PipelineSegment.fromNodeId` | `topology.TopologyNode` |
| `topology.PipelineSegment.toNodeId` | `topology.TopologyNode` |
| `topology.FacilityAttributeValue.attributeDefinitionId` | `topology.FacilityAttributeDefinition` |
| `topology.TopologyConnection.fromNodeId` | `topology.TopologyNode` |
| `topology.TopologyConnection.toNodeId` | `topology.TopologyNode` |
| `topology.EquipmentAttributeValue.attributeDefinitionId` | `topology.EquipmentAttributeDefinition` |
| `telemetry.TrustedTelemetryReading.readingId` | `telemetry.TelemetryReading` |
| `telemetry.TelemetryQualityAssessment.readingId` | `telemetry.TelemetryReading` |
| `simulation.SimulationSensitivityAnalysis.baseRunId` | `simulation.SimulationRun` |
| `workflow.WorkflowTransition.fromStepId` | `workflow.WorkflowStep` |
| `workflow.WorkflowTransition.toStepId` | `workflow.WorkflowStep` |
| `integrity.IntegrityAssessmentScope.assessmentId` | `integrity.IntegrityAssessment` |
| `leakdetection.LeakDetectionCase.primaryCandidateId` | `leakdetection.LeakCandidate` |
| `organization.OrganizationDelegation.delegatorEmployeeId` | `organization.Employee` |
| `organization.OrganizationDelegation.delegateEmployeeId` | `organization.Employee` |
| `analytics.AnalyticsFeatureSet.sourceDatasetId` | `analytics.AnalyticsDataset` |
| `analytics.KpiDefinition.primaryMetricDefinitionId` | `analytics.MetricDefinition` |

## 5. Explicit same-module exceptions

These fields do not resolve safely from the field name alone, but repository naming/ownership
evidence makes their same-module owner explicit:

| Mandatory reference | Integrity owner |
|---|---|
| `risk.RiskAssessment.methodologyId` | `risk.RiskCatalogEntry` |
| `risk.ResidualRiskAssessment.residualLikelihoodId` | `risk.RiskCatalogEntry` |
| `risk.ResidualRiskAssessment.residualConsequenceId` | `risk.RiskCatalogEntry` |
| `risk.ResidualRiskAssessment.residualRatingId` | `risk.RiskRating` |
| `risk.RiskAcceptance.acceptedRatingId` | `risk.RiskRating` |
| `documents.Document.classificationId` | `documents.DocumentCatalogEntry` |
| `telemetry.TelemetrySourceEndpoint.protocolId` | `telemetry.TelemetryCatalogEntry` |
| `telemetry.TelemetrySource.protocolId` | `telemetry.TelemetryCatalogEntry` |
| `incident.Incident.classificationId` | `incident.IncidentCatalogEntry` |
| `integrity.CoatingConditionObservation.coatingConditionId` | `integrity.IntegrityCatalogEntry` |
| `integration.IntegrationDataContract.payloadFormatId` | `integration.IntegrationCatalogEntry` |
| `integration.ExternalEndpoint.protocolId` | `integration.IntegrationCatalogEntry` |
| `integration.IntegrationExchangeMessage.payloadFormatId` | `integration.IntegrationCatalogEntry` |

## 6. Module matrix

| Module | Mandatory *Id | Same-module | Cross-module | Historical snapshot | External ID | Non-relational |
|---|---:|---:|---:|---:|---:|---:|
| alarm | 24 | 14 | 8 | 0 | 0 | 2 |
| analytics | 37 | 32 | 0 | 1 | 0 | 4 |
| assets | 30 | 26 | 4 | 0 | 0 | 0 |
| audit | 21 | 15 | 2 | 0 | 0 | 4 |
| configuration | 14 | 12 | 1 | 0 | 0 | 1 |
| custody | 39 | 34 | 3 | 0 | 0 | 2 |
| documents | 26 | 16 | 7 | 0 | 1 | 2 |
| hse | 19 | 17 | 1 | 0 | 0 | 1 |
| identity | 29 | 28 | 0 | 0 | 0 | 1 |
| incident | 36 | 24 | 11 | 0 | 0 | 1 |
| integration | 36 | 32 | 0 | 0 | 1 | 3 |
| integrity | 34 | 24 | 8 | 0 | 0 | 2 |
| leakdetection | 21 | 15 | 4 | 0 | 0 | 2 |
| monitoring | 11 | 5 | 5 | 0 | 0 | 1 |
| notification | 29 | 23 | 0 | 0 | 0 | 6 |
| organization | 16 | 12 | 0 | 0 | 0 | 4 |
| party | 31 | 29 | 1 | 0 | 0 | 1 |
| planning | 32 | 25 | 7 | 0 | 0 | 0 |
| reporting | 30 | 27 | 2 | 0 | 0 | 1 |
| risk | 52 | 48 | 2 | 0 | 0 | 2 |
| simulation | 51 | 41 | 2 | 4 | 0 | 4 |
| telemetry | 29 | 28 | 1 | 0 | 0 | 0 |
| topology | 20 | 20 | 0 | 0 | 0 | 0 |
| workflow | 31 | 25 | 5 | 0 | 0 | 1 |

## 7. HRA-111 authorization boundary

HRA-111 is authorized to inspect and enforce only the **572 mandatory
`SAME_MODULE_REFERENCE`** dispositions from this inventory.

For each such field HRA-111 must inspect the live Flyway/schema state and classify the actual repair
as one of:

- `ALREADY_PROTECTED` — existing DB FK/check plus application/domain validation is sufficient;
- `ADD_DB_FK` — add an additive Flyway migration for a same-module FK and fail-closed validation;
- `ADD_APPLICATION_VALIDATION` — DB FK is inappropriate but same-module existence must fail closed;
- `RECLASSIFY_WITH_EVIDENCE` — live schema/semantics prove HRA-110 ownership was too broad; record
  the evidence before omitting the field.

HRA-111 must **not**:

- modify an applied Flyway migration;
- add cross-module database foreign keys;
- add FKs to polymorphic `targetId`/reference namespaces;
- turn external IDs or historical snapshot semantics into live relational dependencies;
- add repository lookups inside domain constructors.

The 74 cross-module, 5 historical, 2 external, and 45 non-relational mandatory IDs remain outside
DB-FK consolidation by design.
