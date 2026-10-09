# Organization Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.organization`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns organization units/types, employees/assignments, positions, operational scopes, responsibilities, hierarchy/reporting, shifts and administrative geography references.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [AdministrativeDistrict](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeDistrict.java)
- [AdministrativeLocality](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeLocality.java)
- [AdministrativeState](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeState.java)
- [Employee](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/Employee.java)
- [EmployeeAddress](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAddress.java)
- [EmployeeAssignment](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAssignment.java)
- [OperationalScope](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/OperationalScope.java)
- [OrganizationContactPoint](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationContactPoint.java)
- [OrganizationDelegation](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationDelegation.java)
- [OrganizationHierarchySnapshot](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationHierarchySnapshot.java)
- [OrganizationUnit](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java)
- [OrganizationUnitType](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnitType.java)
- [Position](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/Position.java)
- [ReportingLine](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/ReportingLine.java)
- [ResponsibilityAssignment](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/ResponsibilityAssignment.java)
- [Shift](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/Shift.java)
- [ShiftAssignment](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/ShiftAssignment.java)

Domain policies:

- [OrganizationBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/organization/domain/policy/OrganizationBoundaryPolicy.java)

Domain services:

- [EmployeeDisplayNameService](../../src/main/java/dz/sh/hidra/modules/organization/domain/service/EmployeeDisplayNameService.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [AssignEmployeeUseCase](../../src/main/java/dz/sh/hidra/modules/organization/application/port/in/AssignEmployeeUseCase.java)
- [AssignResponsibilityUseCase](../../src/main/java/dz/sh/hidra/modules/organization/application/port/in/AssignResponsibilityUseCase.java)
- [CreateOrganizationContactPointUseCase](../../src/main/java/dz/sh/hidra/modules/organization/application/port/in/CreateOrganizationContactPointUseCase.java)
- [CreateOrganizationUnitUseCase](../../src/main/java/dz/sh/hidra/modules/organization/application/port/in/CreateOrganizationUnitUseCase.java)
- [ListResponsibilitiesUseCase](../../src/main/java/dz/sh/hidra/modules/organization/application/port/in/ListResponsibilitiesUseCase.java)
- [OperationalScopeQueryUseCase](../../src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java)
- [OrganizationAdministrationQueryUseCase](../../src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java)
- [ReconcileResponsibilitiesUseCase](../../src/main/java/dz/sh/hidra/modules/organization/application/port/in/ReconcileResponsibilitiesUseCase.java)
- [RegisterEmployeeUseCase](../../src/main/java/dz/sh/hidra/modules/organization/application/port/in/RegisterEmployeeUseCase.java)
- [RegisterOperationalScopeUseCase](../../src/main/java/dz/sh/hidra/modules/organization/application/port/in/RegisterOperationalScopeUseCase.java)
- [RevokeResponsibilityUseCase](../../src/main/java/dz/sh/hidra/modules/organization/application/port/in/RevokeResponsibilityUseCase.java)

Current application services:

- [AssetsOrganizationUnitReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryService.java)
- [EmployeeApplicationService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/EmployeeApplicationService.java)
- [EmployeeAssignmentApplicationService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/EmployeeAssignmentApplicationService.java)
- [HseOrganizationReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/HseOrganizationReferenceQueryService.java)
- [IdentityEmployeeReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/IdentityEmployeeReferenceQueryService.java)
- [IncidentOrganizationQueryService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/IncidentOrganizationQueryService.java)
- [IntegrationOrganizationUnitReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryService.java)
- [IntegrityOrganizationUnitReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/IntegrityOrganizationUnitReferenceQueryService.java)
- [LeakDetectionOrganizationUnitReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/LeakDetectionOrganizationUnitReferenceQueryService.java)
- [OperationalScopeApplicationService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/OperationalScopeApplicationService.java)
- [OperationalScopeQueryApplicationService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/OperationalScopeQueryApplicationService.java)
- [OperationalScopeRegistrationValidator](../../src/main/java/dz/sh/hidra/modules/organization/application/service/OperationalScopeRegistrationValidator.java)
- [OrganizationContactPointApplicationService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/OrganizationContactPointApplicationService.java)
- [OrganizationContactPointTargetValidator](../../src/main/java/dz/sh/hidra/modules/organization/application/service/OrganizationContactPointTargetValidator.java)
- [OrganizationUnitApplicationService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/OrganizationUnitApplicationService.java)
- [ReportingOrganizationUnitReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryService.java)
- [ResponsibilityAssignmentApplicationService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/ResponsibilityAssignmentApplicationService.java)
- [ResponsibilityQueryApplicationService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/ResponsibilityQueryApplicationService.java)
- [ResponsibilityReconciliationApplicationService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/ResponsibilityReconciliationApplicationService.java)
- [ResponsibilityRevocationApplicationService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/ResponsibilityRevocationApplicationService.java)
- [RiskOrganizationReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryService.java)

Current API/controller classes:

- [OrganizationAdministrationQueryController](../../src/main/java/dz/sh/hidra/modules/organization/api/rest/controller/OrganizationAdministrationQueryController.java)
- [OrganizationController](../../src/main/java/dz/sh/hidra/modules/organization/api/rest/controller/OrganizationController.java)
- [OrganizationResponsibilityController](../../src/main/java/dz/sh/hidra/modules/organization/api/rest/controller/OrganizationResponsibilityController.java)
- [SpringOrganizationController](../../src/main/java/dz/sh/hidra/modules/organization/api/rest/controller/SpringOrganizationController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **18** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [AdministrativeDistrictJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeDistrictJpaEntity.java)
- [AdministrativeLocalityJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeLocalityJpaEntity.java)
- [AdministrativeStateJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeStateJpaEntity.java)
- [EmployeeAddressJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAddressJpaEntity.java)
- [EmployeeAssignmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java)
- [EmployeeJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeJpaEntity.java)
- [OperationalScopeJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OperationalScopeJpaEntity.java)
- [OrganizationContactPointJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationContactPointJpaEntity.java)
- [OrganizationDelegationJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationDelegationJpaEntity.java)
- [OrganizationHierarchySnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationHierarchySnapshotJpaEntity.java)
- [OrganizationUnitJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitJpaEntity.java)
- [OrganizationUnitTypeJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitTypeJpaEntity.java)
- [PositionJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/PositionJpaEntity.java)
- [ReportingLineJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ReportingLineJpaEntity.java)
- [ReportingLineTypeJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ReportingLineTypeJpaEntity.java)
- [ResponsibilityAssignmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ResponsibilityAssignmentJpaEntity.java)
- [ShiftAssignmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftAssignmentJpaEntity.java)
- [ShiftJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaAdministrativeDistrictRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaAdministrativeDistrictRepositoryAdapter.java)
- [JpaAdministrativeLocalityRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaAdministrativeLocalityRepositoryAdapter.java)
- [JpaAdministrativeStateRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaAdministrativeStateRepositoryAdapter.java)
- [JpaEmployeeAddressRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaEmployeeAddressRepositoryAdapter.java)
- [JpaEmployeeAssignmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaEmployeeAssignmentRepositoryAdapter.java)
- [JpaEmployeeRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaEmployeeRepositoryAdapter.java)
- [JpaOperationalScopeRegistryRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOperationalScopeRegistryRepositoryAdapter.java)
- [JpaOrganizationContactPointRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationContactPointRepositoryAdapter.java)
- [JpaOrganizationDelegationRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationDelegationRepositoryAdapter.java)
- [JpaOrganizationHierarchySnapshotRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationHierarchySnapshotRepositoryAdapter.java)
- [JpaOrganizationUnitRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationUnitRepositoryAdapter.java)
- [JpaOrganizationUnitTypeRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationUnitTypeRepositoryAdapter.java)
- [JpaPositionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaPositionRepositoryAdapter.java)
- [JpaReportingLineRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaReportingLineRepositoryAdapter.java)
- [JpaResponsibilityAssignmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaResponsibilityAssignmentRepositoryAdapter.java)
- [JpaShiftAssignmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaShiftAssignmentRepositoryAdapter.java)
- [JpaShiftRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaShiftRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

- [AnalyticsOrganizationScopeContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/analytics/AnalyticsOrganizationScopeContract.java)
- [AssetsOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/AssetsOrganizationUnitReferenceContract.java)
- [HseOrganizationReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/hse/HseOrganizationReferenceContract.java)
- [IdentityEmployeeReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/identity/IdentityEmployeeReferenceContract.java)
- [IncidentOrganizationContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/incident/IncidentOrganizationContract.java)
- [IntegrationOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/IntegrationOrganizationUnitReferenceContract.java)
- [IntegrityOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/integrity/IntegrityOrganizationUnitReferenceContract.java)
- [LeakDetectionOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/leakdetection/LeakDetectionOrganizationUnitReferenceContract.java)
- [PlanningResponsibleUnitContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/planning/PlanningResponsibleUnitContract.java)
- [ReportingOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/ReportingOrganizationUnitReferenceContract.java)
- [RiskOrganizationReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/RiskOrganizationReferenceContract.java)
- [WorkflowOrganizationContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/workflow/WorkflowOrganizationContract.java)

Imported scalar contracts supplied by collaborating owners:

- [OrganizationResponsibilityAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/organization/OrganizationResponsibilityAuditContract.java)
- [TopologyOperationalScopeTargetContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/organization/TopologyOperationalScopeTargetContract.java)
- [OrganizationResponsibilityWorkflowContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/organization/OrganizationResponsibilityWorkflowContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [AdministrativeDistrictRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/AdministrativeDistrictRepositoryPort.java)
- [AdministrativeLocalityRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/AdministrativeLocalityRepositoryPort.java)
- [AdministrativeStateRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/AdministrativeStateRepositoryPort.java)
- [EmployeeAddressRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/EmployeeAddressRepositoryPort.java)
- [EmployeeAssignmentRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/EmployeeAssignmentRepositoryPort.java)
- [EmployeeRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/EmployeeRepositoryPort.java)
- [OperationalScopeRegistryRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeRegistryRepositoryPort.java)
- [OperationalScopeTargetResolverPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java)
- [OrganizationContactPointRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationContactPointRepositoryPort.java)
- [OrganizationDelegationRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationDelegationRepositoryPort.java)
- [OrganizationHierarchySnapshotRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationHierarchySnapshotRepositoryPort.java)
- [OrganizationUnitRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java)
- [OrganizationUnitTypeRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitTypeRepositoryPort.java)
- [PositionRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/PositionRepositoryPort.java)
- [ReportingLineRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/ReportingLineRepositoryPort.java)
- [ResponsibilityAssignmentRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/ResponsibilityAssignmentRepositoryPort.java)
- [ShiftAssignmentRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/ShiftAssignmentRepositoryPort.java)
- [ShiftRepositoryPort](../../src/main/java/dz/sh/hidra/modules/organization/application/port/out/ShiftRepositoryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Organization owns internal structure, operational persons, geography, shifts and effective-dated responsibility. Registry scope identity is independent of owner target identity; contact channels are typed and multilingual labels remain embedded on Organization owners.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| OrganizationUnitType | [HMSR-001 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-organizationunittype) |
| AdministrativeState | [HMSR-003 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-administrativestate) |
| OperationalScope | [HMSR-016 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-operationalscope) |
| Position | [HMSR-022 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-position) |
| Shift | [HMSR-023 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-shift) |
| OrganizationContactPoint | [HMSR-030 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-organizationcontactpoint) |
| ReportingLine | [HMSR-031 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-reportingline) |
| OrganizationUnit | [HMSR-035 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-organizationunit) |
| AdministrativeDistrict | [HMSR-036 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-administrativedistrict) |
| ResponsibilityAssignment | [HMSR-040 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-responsibilityassignment) |
| AdministrativeLocality | [HMSR-053 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-administrativelocality) |
| Employee | [HMSR-065 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-employee) |
| EmployeeAddress | [HMSR-086 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-employeeaddress) |
| EmployeeAssignment | [HMSR-087 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-employeeassignment) |
| OrganizationDelegation | [HMSR-088 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-organizationdelegation) |
| OrganizationHierarchySnapshot | [HMSR-089 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-organizationhierarchysnapshot) |
| ShiftAssignment | [HMSR-090 reconciled rule](../domain/SEMANTIC_DECISIONS.md#organization-shiftassignment) |
