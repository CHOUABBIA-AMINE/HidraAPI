# HidraAPI Cross-Module Contracts

## Status

CURRENT — HPR-P2-013 source-verified exported cross-module application-contract inventory. The original HPR-P2-002 surface is historical generation provenance.

## Contract Rule

A module may depend on another business module only through a deliberately exported package recorded by `ArchitectureGuardrailTest`. Named module consumers use the package convention:

```text
dz.sh.hidra.modules.<owner>.application.contract.<consumer>
```

The current transitional cross-module dependency allowlist is empty.

## Exported Contracts

Verified source parent: `00c4fda266b2dfd175cca37ad789dc9462a5af0b`, 2026-10-09. **70** non-package-info Java
files in **63** unique exported packages, exactly matching
[ArchitectureGuardrailTest](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java).
Files may define interfaces or supporting types; file counts are not counts of
independent operational interactions. Source presence and export permission do not
prove deployment provider availability. The original HPR-P2-002 table had 22 entries.

| Owner | Consumer / extension role | Package suffix | Source file |
|---|---|---|---|
| assets | Module consumer: hse | `hse` | [HseWorkOrderReferenceContract](../../src/main/java/dz/sh/hidra/modules/assets/application/contract/hse/HseWorkOrderReferenceContract.java) |
| audit | Module consumer: alarm | `alarm` | [AlarmSuppressionAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/alarm/AlarmSuppressionAuditContract.java) |
| audit | Module consumer: custody | `custody` | [CustodyTicketAuditReferenceContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/custody/CustodyTicketAuditReferenceContract.java) |
| audit | Module consumer: organization | `organization` | [OrganizationResponsibilityAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/organization/OrganizationResponsibilityAuditContract.java) |
| audit | Module consumer: risk | `risk` | [RiskAssessmentAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskAssessmentAuditContract.java) |
| audit | Module consumer: risk | `risk` | [RiskRegisterAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskRegisterAuditContract.java) |
| audit | Module consumer: simulation | `simulation` | [SimulationRecommendationAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/simulation/SimulationRecommendationAuditContract.java) |
| custody | Module consumer: planning | `planning` | [PlanningProductReferenceContract](../../src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/PlanningProductReferenceContract.java) |
| documents | Module consumer: audit | `audit` | [AuditDocumentReferenceContract](../../src/main/java/dz/sh/hidra/modules/documents/application/contract/audit/AuditDocumentReferenceContract.java) |
| documents | Module consumer: reporting | `reporting` | [ReportingDocumentReferenceContract](../../src/main/java/dz/sh/hidra/modules/documents/application/contract/reporting/ReportingDocumentReferenceContract.java) |
| documents | Neutral extension: target | `target` | [DocumentsOwnedTargetLookup](../../src/main/java/dz/sh/hidra/modules/documents/application/contract/target/DocumentsOwnedTargetLookup.java) |
| identity | Module consumer: assets | `assets` | [MaintenanceWorkOrderActorReferenceContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/assets/MaintenanceWorkOrderActorReferenceContract.java) |
| identity | Module consumer: custody | `custody` | [CustodyTransferTicketActorReferenceContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/custody/CustodyTransferTicketActorReferenceContract.java) |
| identity | Module consumer: documents | `documents` | [DocumentsActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/documents/DocumentsActorContract.java) |
| identity | Module consumer: hse | `hse` | [HseActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/hse/HseActorContract.java) |
| identity | Module consumer: incident | `incident` | [IncidentActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/incident/IncidentActorContract.java) |
| identity | Module consumer: integration | `integration` | [IntegrationResolverContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/integration/IntegrationResolverContract.java) |
| identity | Module consumer: integrity | `integrity` | [IntegrityAssessmentActorReferenceContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/integrity/IntegrityAssessmentActorReferenceContract.java) |
| identity | Module consumer: integrity | `integrity` | [IntegrityCaseActorReferenceContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/integrity/IntegrityCaseActorReferenceContract.java) |
| identity | Module consumer: planning | `planning` | [PlanningCreatorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/planning/PlanningCreatorContract.java) |
| identity | Module consumer: reporting | `reporting` | [ReportingAccessAuthorizationContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/ReportingAccessAuthorizationContract.java) |
| identity | Module consumer: risk | `risk` | [RiskActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/risk/RiskActorContract.java) |
| identity | Module consumer: workflow | `workflow` | [WorkflowActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/workflow/WorkflowActorContract.java) |
| integrity | Module consumer: assets | `assets` | [MaintenanceRecommendationReferenceContract](../../src/main/java/dz/sh/hidra/modules/integrity/application/contract/assets/MaintenanceRecommendationReferenceContract.java) |
| organization | Module consumer: analytics | `analytics` | [AnalyticsOrganizationScopeContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/analytics/AnalyticsOrganizationScopeContract.java) |
| organization | Module consumer: assets | `assets` | [AssetsOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/AssetsOrganizationUnitReferenceContract.java) |
| organization | Module consumer: hse | `hse` | [HseOrganizationReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/hse/HseOrganizationReferenceContract.java) |
| organization | Module consumer: identity | `identity` | [IdentityEmployeeReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/identity/IdentityEmployeeReferenceContract.java) |
| organization | Module consumer: incident | `incident` | [IncidentOrganizationContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/incident/IncidentOrganizationContract.java) |
| organization | Module consumer: integration | `integration` | [IntegrationOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/IntegrationOrganizationUnitReferenceContract.java) |
| organization | Module consumer: integrity | `integrity` | [IntegrityOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/integrity/IntegrityOrganizationUnitReferenceContract.java) |
| organization | Module consumer: leakdetection | `leakdetection` | [LeakDetectionOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/leakdetection/LeakDetectionOrganizationUnitReferenceContract.java) |
| organization | Module consumer: planning | `planning` | [PlanningResponsibleUnitContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/planning/PlanningResponsibleUnitContract.java) |
| organization | Module consumer: reporting | `reporting` | [ReportingOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/ReportingOrganizationUnitReferenceContract.java) |
| organization | Module consumer: risk | `risk` | [RiskOrganizationReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/RiskOrganizationReferenceContract.java) |
| organization | Module consumer: workflow | `workflow` | [WorkflowOrganizationContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/workflow/WorkflowOrganizationContract.java) |
| party | Module consumer: assets | `assets` | [AssetsPartyReferenceContract](../../src/main/java/dz/sh/hidra/modules/party/application/contract/assets/AssetsPartyReferenceContract.java) |
| party | Module consumer: planning | `planning` | [PlanningPartyReferenceContract](../../src/main/java/dz/sh/hidra/modules/party/application/contract/planning/PlanningPartyReferenceContract.java) |
| party | Module consumer: topology | `topology` | [TopologyPartyReferenceContract](../../src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyReferenceContract.java) |
| planning | Module consumer: monitoring | `monitoring` | [MonitoringPlanTargetReferenceContract](../../src/main/java/dz/sh/hidra/modules/planning/application/contract/monitoring/MonitoringPlanTargetReferenceContract.java) |
| risk | Neutral extension: evidence | `evidence` | [RiskOwnedEvidenceLookup](../../src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java) |
| telemetry | Module consumer: monitoring | `monitoring` | [MonitoringTelemetryPointReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java) |
| telemetry | Module consumer: monitoring | `monitoring` | [MonitoringTrustedReadingReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTrustedReadingReferenceContract.java) |
| telemetry | Module consumer: planning | `planning` | [PlanningTelemetryPointReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningTelemetryPointReferenceContract.java) |
| telemetry | Module consumer: planning | `planning` | [PlanningUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningUnitReferenceContract.java) |
| topology | Module consumer: analytics | `analytics` | [AnalyticsTopologyScopeContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/analytics/AnalyticsTopologyScopeContract.java) |
| topology | Module consumer: assets | `assets` | [AssetsTopologyReferenceContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java) |
| topology | Module consumer: incident | `incident` | [IncidentTopologyContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/incident/IncidentTopologyContract.java) |
| topology | Module consumer: integrity | `integrity` | [IntegrityCaseTopologyReferenceContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/integrity/IntegrityCaseTopologyReferenceContract.java) |
| topology | Module consumer: leakdetection | `leakdetection` | [LeakDetectionTopologyAssetContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/leakdetection/LeakDetectionTopologyAssetContract.java) |
| topology | Module consumer: organization | `organization` | [TopologyOperationalScopeTargetContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/organization/TopologyOperationalScopeTargetContract.java) |
| topology | Module consumer: planning | `planning` | [PlanningTargetTopologyReferenceContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java) |
| topology | Module consumer: planning | `planning` | [PlanningTopologyScopeContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTopologyScopeContract.java) |
| topology | Module consumer: risk | `risk` | [RiskTopologyScopeReferenceContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java) |
| topology | Module consumer: simulation | `simulation` | [SimulationTopologyScopeContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java) |
| topology | Module consumer: simulation | `simulation` | [SimulationTopologyTargetContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java) |
| workflow | Module consumer: alarm | `alarm` | [AlarmSuppressionWorkflowContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/alarm/AlarmSuppressionWorkflowContract.java) |
| workflow | Module consumer: assets | `assets` | [MaintenanceWorkOrderWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/assets/MaintenanceWorkOrderWorkflowReferenceContract.java) |
| workflow | Module consumer: audit | `audit` | [AuditWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/audit/AuditWorkflowReferenceContract.java) |
| workflow | Module consumer: custody | `custody` | [CustodyTransferTicketWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/custody/CustodyTransferTicketWorkflowReferenceContract.java) |
| workflow | Module consumer: documents | `documents` | [DocumentsApprovalReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/documents/DocumentsApprovalReferenceContract.java) |
| workflow | Module consumer: hse | `hse` | [HseWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/hse/HseWorkflowReferenceContract.java) |
| workflow | Module consumer: incident | `incident` | [IncidentWorkflowContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/incident/IncidentWorkflowContract.java) |
| workflow | Module consumer: integrity | `integrity` | [IntegrityAssessmentWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/integrity/IntegrityAssessmentWorkflowReferenceContract.java) |
| workflow | Module consumer: integrity | `integrity` | [IntegrityCaseWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/integrity/IntegrityCaseWorkflowReferenceContract.java) |
| workflow | Module consumer: organization | `organization` | [OrganizationResponsibilityWorkflowContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/organization/OrganizationResponsibilityWorkflowContract.java) |
| workflow | Module consumer: planning | `planning` | [PlanningWorkflowContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/planning/PlanningWorkflowContract.java) |
| workflow | Module consumer: reporting | `reporting` | [ReportingWorkflowApprovalContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/ReportingWorkflowApprovalContract.java) |
| workflow | Module consumer: risk | `risk` | [RiskAssessmentApprovalContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/risk/RiskAssessmentApprovalContract.java) |
| workflow | Neutral extension: target | `target` | [WorkflowOwnedTargetLookup](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/target/WorkflowOwnedTargetLookup.java) |

`target` and `evidence` name neutral owner-lookup extension packages, not
implemented module roots or invented consumers. Their exported types are
DocumentsOwnedTargetLookup, WorkflowOwnedTargetLookup and RiskOwnedEvidenceLookup.
Named module-consumer packages describe allowed surfaces, not unrestricted access.

## Prohibited Alternatives

Without an explicit approved architecture change, consumers must not replace these contracts with direct imports of:

- another module's domain model;
- another module's persistence entity/repository;
- another module's private application service or port;
- platform generic persistence access.

## Change Control

Adding an exported package requires the corresponding architecture/roadmap decision and an update to the architecture enforcement allowlist. Stale exported packages should be removed when no longer used rather than retained as an implicit compatibility surface.

## HAPI-930 narrow Alarm owner exports — 2026-11-08

HAPI-930's approved resource-authorization boundary introduces **two exact, provider-owned application-contract types**, consumed only by `dz.sh.hidra.modules.alarm.infrastructure.security.AlarmResourceAuthorizationAdapter`:

- `dz.sh.hidra.modules.identity.application.contract.alarm.AlarmScopedAuthorizationContract`: current exact company-and-asset permission decision, not an Identity persistence or domain export.
- `dz.sh.hidra.modules.topology.application.contract.alarm.AlarmTopologyOwnershipContract` and its nested `OwnedAsset` projection: current Topology asset and owner identity, not a permission decision.

These are **exact consumer/type exceptions**, not new package-wide exceptions in the existing historical 63-package exported set. The two independently enforced guardrails, `ArchitectureGuardrailTest` and `ForensicRemediationClosureTest`, permit only these named types for the named Alarm adapter, with positive and negative tests; unrelated consumers, sibling types and private owners remain forbidden. HAPI-930 is PARTIAL / IN_PROGRESS; successful source compilation, exact-SHA CI and native permission proof are independent acceptance gates. The earlier October 2026 70-file/63-package inventory remains historical and is not retroactively rewritten.

## T12 narrow Monitoring evidence export — 2026-11-14

The T12 manual, evidence-linked Alarm command consumes only one **Monitoring-owned exported application contract**:

- Provider: `dz.sh.hidra.modules.monitoring.application.contract.alarm.AlarmMonitoringEvaluationContract`, implemented by `AlarmMonitoringEvaluationQueryService`.
- Authorized consumer: `dz.sh.hidra.modules.alarm.infrastructure.integration.MonitoringEvaluationEvidenceAdapter` exclusively.
- Authorized payload: immutable scalar evaluation identity, exact approved rule revision, company/asset/point/unit context, outcome, numeric difference, target/approval and trusted observation identifiers, and evaluation timestamps. No foreign domain model or JPA record.
- Owner access: Monitoring resolves the rule for the evaluation ID internally, then re-enters its existing `AuthoritativeDeviationUseCase.find(ruleId, evaluationId)`, which verifies current Monitoring READ before returning protected evidence. Alarm separately requires its current Alarm EXECUTE/READ resource grant.
- Architecture enforcement: `ArchitectureGuardrailTest` and `ForensicRemediationClosureTest` admit this exact consumer/type pair, including the nested immutable projection, and reject sibling contracts, unrelated consumers and private module imports.

The independent Monitoring lookup needed for the evaluation-ID-only T12 request adds a narrow owner-port method `AuthoritativeDeviationRepositoryPort.ruleIdByEvaluationId` and its implementation in `JdbcAuthoritativeDeviationRepositoryAdapter`. It never discloses source data itself; protected detail remains behind the original Monitoring read use case. This is the minimal additional Monitoring owner source required by the existing T11 interface. It does not add a general exported package, write to another module or authorize industrial alarms.

T12 operator-created alarms use `AlarmSourceType.MANUAL`, with the evaluation identity in the Alarm-owned mapping and `MONITORING_EVALUATION` evidence link. Legacy Alarm list/detail do not expose Monitoring source identifiers or numeric evidence; the new typed evidence route enforces both current owner grants. No SCADA writes, automated threshold classification, background alarm generation or deployment is implied.
## T13 narrow LeakDetection source and scoped-authorization exports — 2026-11-14

T13 is a **human-declared, source-linked suspected leak**, not an executing leak detection method or physical leak confirmation. Its three provider-controlled scalar public contracts are:

- Monitoring → LeakDetection: `dz.sh.hidra.modules.monitoring.application.contract.leakdetection.LeakDetectionMonitoringEvaluationContract`, consumed **only** by `dz.sh.hidra.modules.leakdetection.infrastructure.integration.LeakMonitoringEvidenceAdapter`. The Monitoring provider delegates to its existing authorized T11 exact-evaluation query; the contract carries a protected scalar snapshot, not a Monitoring aggregate or JPA entity.
- Identity → LeakDetection: `dz.sh.hidra.modules.identity.application.contract.leakdetection.LeakDetectionScopedAuthorizationContract`, consumed **only** by `dz.sh.hidra.modules.leakdetection.infrastructure.security.LeakCandidateResourceAuthorizationAdapter`. Identity rechecks current LOCAL actor, direct ACTIVE company and exact asset grants, full effective policy/DENY precedence and revocation in the caller's transaction. Route permission vocabulary is `leakdetection:candidates:execute` and `leakdetection:candidates:read`.
- Topology → LeakDetection: `dz.sh.hidra.modules.topology.application.contract.leakdetection.LeakDetectionTopologyOwnershipContract`, consumed **only** by the same LeakDetection resource-authorization adapter. Topology retains current company/facility/equipment ownership with its existing locked owner resolution, and does not itself grant a permission.

`ArchitectureGuardrailTest` and `ForensicRemediationClosureTest` admit only these exact consumer/type pairs, including their nested scalar projections. These do not extend the historical exported-package set or authorize imports of private owner application, domain or persistence code. T13 persists an immutable candidate/evaluation mapping and a typed `MONITORING_DEVIATION` evidence link within LeakDetection; existing `LeakCandidate` lifecycle remains separate from the suspected-condition declaration. Source eligibility and current authorization are rechecked on reads and replays; neither the score nor the existence of a nonzero deviation means a leak occurred.
