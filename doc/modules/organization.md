# Organization Module

## Status

CURRENT — canonical HPR-P2-004 module document.

## Verification Baseline

Source baseline: `f3c703048402f3dcf1a520aceea64e64c4861035`

Package root: `dz.sh.hidra.modules.organization`

This document describes source-visible current state. It does not promote legacy `docs/**` material to authority and does not substitute for the canonical API/database contracts created by later P2 tasks.

## Responsibility

Owns organization units/types, employees/assignments, positions, operational scopes, responsibilities, hierarchy/reporting, shifts and administrative geography references.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types:

- `AdministrativeDistrict`
- `AdministrativeLocality`
- `AdministrativeState`
- `Employee`
- `EmployeeAddress`
- `EmployeeAssignment`
- `OperationalScope`
- `OrganizationContactPoint`
- `OrganizationDelegation`
- `OrganizationHierarchySnapshot`
- `OrganizationUnit`
- `OrganizationUnitType`
- `Position`
- `ReportingLine`
- `ResponsibilityAssignment`
- `Shift`
- `ShiftAssignment`

Domain policies: `OrganizationBoundaryPolicy`

Domain services: `EmployeeDisplayNameService`

Canonical semantic context: `../domain/DOMAIN_OWNERSHIP.md`.

## Application and API Surface

Current inbound/use-case ports:

- `AssignEmployeeUseCase`
- `AssignResponsibilityUseCase`
- `CreateOrganizationContactPointUseCase`
- `CreateOrganizationUnitUseCase`
- `ListResponsibilitiesUseCase`
- `OperationalScopeQueryUseCase`
- `OrganizationAdministrationQueryUseCase`
- `ReconcileResponsibilitiesUseCase`
- `RegisterEmployeeUseCase`
- `RegisterOperationalScopeUseCase`
- `RevokeResponsibilityUseCase`

Current application services:

- `AssetsOrganizationUnitReferenceQueryService`
- `EmployeeApplicationService`
- `EmployeeAssignmentApplicationService`
- `IntegrationOrganizationUnitReferenceQueryService`
- `OperationalScopeApplicationService`
- `OperationalScopeQueryApplicationService`
- `OperationalScopeRegistrationValidator`
- `OrganizationContactPointApplicationService`
- `OrganizationContactPointTargetValidator`
- `OrganizationUnitApplicationService`
- `ReportingOrganizationUnitReferenceQueryService`
- `ResponsibilityAssignmentApplicationService`
- `ResponsibilityQueryApplicationService`
- `ResponsibilityReconciliationApplicationService`
- `ResponsibilityRevocationApplicationService`
- `RiskOrganizationReferenceQueryService`

Current API/controller classes:

- `OrganizationAdministrationQueryController`
- `OrganizationController`
- `OrganizationResponsibilityController`
- `SpringOrganizationController`

These class inventories establish implemented adapters/use-case surfaces. Exact HTTP paths, request/response schemas, authentication requirements and compatibility semantics are HPR-P2-005 scope.

## Persistence

Current JPA persistence entity count: **18**.

Persistence entities:

- `AdministrativeDistrictJpaEntity`
- `AdministrativeLocalityJpaEntity`
- `AdministrativeStateJpaEntity`
- `EmployeeAddressJpaEntity`
- `EmployeeAssignmentJpaEntity`
- `EmployeeJpaEntity`
- `OperationalScopeJpaEntity`
- `OrganizationContactPointJpaEntity`
- `OrganizationDelegationJpaEntity`
- `OrganizationHierarchySnapshotJpaEntity`
- `OrganizationUnitJpaEntity`
- `OrganizationUnitTypeJpaEntity`
- `PositionJpaEntity`
- `ReportingLineJpaEntity`
- `ReportingLineTypeJpaEntity`
- `ResponsibilityAssignmentJpaEntity`
- `ShiftAssignmentJpaEntity`
- `ShiftJpaEntity`

Current persistence repository-adapter classes:

- `JpaAdministrativeDistrictRepositoryAdapter`
- `JpaAdministrativeLocalityRepositoryAdapter`
- `JpaAdministrativeStateRepositoryAdapter`
- `JpaEmployeeAddressRepositoryAdapter`
- `JpaEmployeeAssignmentRepositoryAdapter`
- `JpaEmployeeRepositoryAdapter`
- `JpaOperationalScopeRegistryRepositoryAdapter`
- `JpaOrganizationContactPointRepositoryAdapter`
- `JpaOrganizationDelegationRepositoryAdapter`
- `JpaOrganizationHierarchySnapshotRepositoryAdapter`
- `JpaOrganizationUnitRepositoryAdapter`
- `JpaOrganizationUnitTypeRepositoryAdapter`
- `JpaPositionRepositoryAdapter`
- `JpaReportingLineRepositoryAdapter`
- `JpaResponsibilityAssignmentRepositoryAdapter`
- `JpaShiftAssignmentRepositoryAdapter`
- `JpaShiftRepositoryAdapter`

Table/schema ownership and the generated data dictionary are HPR-P2-006 scope; class presence is not used here to invent database constraints or retention policy.

## Cross-Module Boundary

Exported contracts owned by this module:

- consumer `analytics`: `AnalyticsOrganizationScopeContract`
- consumer `assets`: `AssetsOrganizationUnitReferenceContract`
- consumer `integration`: `IntegrationOrganizationUnitReferenceContract`
- consumer `reporting`: `ReportingOrganizationUnitReferenceContract`
- consumer `risk`: `RiskOrganizationReferenceContract`

Current outbound application ports used to reach persistence or collaborating capabilities:

- `AdministrativeDistrictRepositoryPort`
- `AdministrativeLocalityRepositoryPort`
- `AdministrativeStateRepositoryPort`
- `EmployeeAddressRepositoryPort`
- `EmployeeAssignmentRepositoryPort`
- `EmployeeRepositoryPort`
- `OperationalScopeRegistryRepositoryPort`
- `OperationalScopeTargetResolverPort`
- `OrganizationContactPointRepositoryPort`
- `OrganizationDelegationRepositoryPort`
- `OrganizationHierarchySnapshotRepositoryPort`
- `OrganizationUnitRepositoryPort`
- `OrganizationUnitTypeRepositoryPort`
- `PositionRepositoryPort`
- `ReportingLineRepositoryPort`
- `ResponsibilityAssignmentRepositoryPort`
- `ShiftAssignmentRepositoryPort`
- `ShiftRepositoryPort`

Cross-module collaboration must preserve the canonical architecture rule: no direct import of another module's private domain, infrastructure or non-exported application packages.

## Current-State Limits

- File/class presence documents implementation structure, not proof that every business workflow, external dependency or production integration is exercised.
- HPR-P2-004 does not resolve legacy HMR/HMSR semantic obligations; HPR-P2-007/008 remain the reconciliation/remediation authority.
- `agents`, `environment` and `otsecurity` are not current implemented module roots and are not implied by this document.
