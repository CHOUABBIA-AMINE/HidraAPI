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
