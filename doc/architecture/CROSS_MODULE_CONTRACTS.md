# HidraAPI Cross-Module Contracts

## Status

CURRENT — exact exported cross-module application-contract surface at the HPR-P2-002 baseline.

## Contract Rule

A module may depend on another business module only through a deliberately exported package recorded by `ArchitectureGuardrailTest`. The package convention is:

```text
dz.sh.hidra.modules.<owner>.application.contract.<consumer>
```

The current transitional cross-module dependency allowlist is empty.

## Exported Contracts

| Owner | Consumer | Contract |
|---|---|---|
| audit | alarm | `AlarmSuppressionAuditContract` |
| audit | organization | `OrganizationResponsibilityAuditContract` |
| audit | risk | `RiskRegisterAuditContract` |
| identity | reporting | `ReportingAccessAuthorizationContract` |
| organization | analytics | `AnalyticsOrganizationScopeContract` |
| organization | assets | `AssetsOrganizationUnitReferenceContract` |
| organization | integration | `IntegrationOrganizationUnitReferenceContract` |
| organization | reporting | `ReportingOrganizationUnitReferenceContract` |
| organization | risk | `RiskOrganizationReferenceContract` |
| party | assets | `AssetsPartyReferenceContract` |
| party | topology | `TopologyPartyReferenceContract` |
| telemetry | monitoring | `MonitoringTelemetryPointReferenceContract` |
| topology | analytics | `AnalyticsTopologyScopeContract` |
| topology | assets | `AssetsTopologyReferenceContract` |
| topology | leakdetection | `LeakDetectionTopologyAssetContract` |
| topology | organization | `TopologyOperationalScopeTargetContract` |
| topology | risk | `RiskTopologyScopeReferenceContract` |
| topology | simulation | `SimulationTopologyScopeContract` |
| workflow | alarm | `AlarmSuppressionWorkflowContract` |
| workflow | organization | `OrganizationResponsibilityWorkflowContract` |
| workflow | planning | `PlanningWorkflowContract` |
| workflow | reporting | `ReportingWorkflowApprovalContract` |

These names and owner/consumer package relationships are taken from the live source tree. Their detailed business semantics are not expanded here because HPR-P2-003/HPR-P2-004 own domain and module semantic documentation.

## Prohibited Alternatives

Without an explicit approved architecture change, consumers must not replace these contracts with direct imports of:

- another module's domain model;
- another module's persistence entity/repository;
- another module's private application service or port;
- platform generic persistence access.

## Change Control

Adding an exported package requires the corresponding architecture/roadmap decision and an update to the architecture enforcement allowlist. Stale exported packages should be removed when no longer used rather than retained as an implicit compatibility surface.
