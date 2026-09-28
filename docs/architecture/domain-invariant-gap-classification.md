# Domain Invariant Gap Classification

**Recorded:** 2026-09-28  
**Roadmap:** HRA-050  
**Live main:** `b2280cb5e5a3645c9c6a24e9794bab67ccbd9825`  
**Static source snapshot SHA-256:** `396e6206040b0acdd79c84b45ead0a6fa4329ffc527e95729982af36b383018a`

## 1. Scope and evidence

HRA-050 classifies domain/JPA nullability mismatches; it does **not** change production code.

The supplied source snapshot contains the 465 same-named domain/JPA pairs used by the forensic baseline.
A live GitHub comparison from the snapshot-era HRA-072 head
`335d6f631630c79e4595aaadb604cf61ae40bd49` to the HRA-050 head found 77 changed
files and **zero** changes under `domain/model`, `domain/value`, or
`infrastructure/persistence/entity`. The supplied snapshot is therefore current for this
classification.

The detector matched each `domain.model.<Type>` record to
`infrastructure.persistence.entity.<Type>JpaEntity`, inspected explicit
`@Column(nullable = false)` state, and inspected the domain compact constructor.

## 2. Disposition rules

| Disposition | Meaning | HRA-051 action |
|---|---|---|
| `ENFORCE` | Requiredness, temporal ordering, or direct self-reference is intrinsic and locally decidable from the record itself. | Add fail-fast constructor validation in the consolidated HRA-051 batch. |
| `ALREADY_ENFORCED` | The constructor already rejects null/blank or the domain component is a Java primitive. | No change. |
| `PERSISTENCE_ONLY_TEXT` | JPA requires a generic human-readable payload but repository evidence does not independently prove that blank/null rejection is a domain invariant. | Keep the persistence constraint; do not harden in HRA-051. |
| `PERSISTENCE_ONLY_AUDIT_TIMESTAMP` | `createdAt` / `updatedAt` is persistence/lifecycle audit metadata. | Keep the persistence constraint; do not add a new HRA-051 domain nullability/order rule. |

HRA-051 must not perform repository lookups, database uniqueness checks, cross-module existence
checks, or any validation that cannot be decided from the record's own values.

## 3. Deterministic required-field classifier

For each same-named domain/JPA pair, consider only a domain component that has a matching JPA field
with explicit `@Column(nullable = false)`.

Apply the following order:

1. Java primitive or existing constructor `require*` / `Objects.requireNonNull` rejection:
   `ALREADY_ENFORCED`.
2. `createdAt` or `updatedAt`: `PERSISTENCE_ONLY_AUDIT_TIMESTAMP`.
3. Own `id`: `ENFORCE / IDENTITY`.
4. String field ending in `Id` / `Ids` or representing a stable reference identifier:
   `ENFORCE / REFERENCE`.
5. Same-module actual Java enum: `ENFORCE / ENUM_VOCABULARY`.
6. Non-audit temporal type (`Instant`, `LocalDate`, `LocalDateTime`,
   `OffsetDateTime`, `ZonedDateTime`): `ENFORCE / TEMPORAL_ANCHOR`.
7. Boxed numeric/boolean/character value required by JPA: `ENFORCE / REQUIRED_SCALAR`.
8. String business code/number/key/token/hash/checksum/locale/version vocabulary:
   `ENFORCE / CODE_KEY`.
9. Other String payload: `PERSISTENCE_ONLY_TEXT`.

This classifier intentionally does not elevate arbitrary names, titles, descriptions, comments,
messages, labels, snapshots, expressions, paths, or other free text into business invariants merely
because the database column is `NOT NULL`.

## 4. Repository totals

- Same-named domain/JPA pairs reviewed: **465**.
- Matched JPA `nullable=false` fields: **3,385**.
- Required-field rules approved for HRA-051: **2,038**.
- Required fields already enforced: **365**.
- Generic required text kept persistence-only: **327**.
- `createdAt` / `updatedAt` nullability kept persistence-only: **655**.
- Additional locally decidable temporal-order rules approved: **83**.
- Additional direct self-reference prohibitions approved: **7**.
- Total HRA-051 rules to implement: **2,128** (`2,038 + 83 + 7`).

Required-field `ENFORCE` breakdown:

| Category | Count |
|---|---:|
| `IDENTITY` | 446 |
| `REFERENCE` | 682 |
| `CODE_KEY` | 278 |
| `ENUM_VOCABULARY` | 400 |
| `TEMPORAL_ANCHOR` | 207 |
| `REQUIRED_SCALAR` | 25 |
| **Total** | **2,038** |

## 5. Module-by-module invariant matrix

| Module | JPA required fields | ENFORCE fields | Already enforced | Persistence-only text | Persistence-only audit timestamps | Ordering rules | Self-reference rules |
|---|---:|---:|---:|---:|---:|---:|---:|
| alarm | 87 | 65 | 7 | 5 | 10 | 2 | 0 |
| analytics | 216 | 121 | 16 | 41 | 38 | 12 | 0 |
| assets | 165 | 97 | 14 | 11 | 43 | 5 | 2 |
| audit | 104 | 73 | 8 | 17 | 6 | 1 | 0 |
| configuration | 116 | 58 | 14 | 16 | 28 | 2 | 0 |
| custody | 146 | 101 | 7 | 9 | 29 | 6 | 0 |
| documents | 88 | 55 | 12 | 14 | 7 | 2 | 1 |
| hse | 128 | 79 | 8 | 13 | 28 | 2 | 0 |
| identity | 187 | 120 | 20 | 12 | 35 | 8 | 0 |
| incident | 100 | 69 | 14 | 8 | 9 | 0 | 0 |
| integration | 205 | 114 | 34 | 22 | 35 | 3 | 0 |
| integrity | 143 | 105 | 5 | 9 | 24 | 2 | 0 |
| leakdetection | 104 | 73 | 5 | 10 | 16 | 2 | 0 |
| monitoring | 71 | 47 | 6 | 7 | 11 | 1 | 0 |
| notification | 176 | 84 | 33 | 20 | 39 | 3 | 0 |
| organization | 108 | 23 | 50 | 5 | 30 | 7 | 1 |
| party | 193 | 122 | 5 | 9 | 57 | 7 | 1 |
| planning | 135 | 92 | 7 | 10 | 26 | 6 | 1 |
| reporting | 165 | 91 | 18 | 20 | 36 | 0 | 0 |
| risk | 196 | 107 | 26 | 20 | 43 | 3 | 0 |
| simulation | 170 | 109 | 16 | 21 | 24 | 0 | 0 |
| telemetry | 125 | 81 | 14 | 9 | 21 | 4 | 0 |
| topology | 130 | 84 | 6 | 3 | 37 | 5 | 0 |
| workflow | 127 | 68 | 20 | 16 | 23 | 0 | 1 |

## 6. Temporal ordering rules approved for HRA-051

When an end value is optional, enforce ordering only when it is present. Equality is permitted unless
an existing domain-specific rule proves strict ordering is required.

- **alarm (2):** `Alarm.raisedAt <= closedAt`; `AlarmRuleBinding.effectiveFrom <= effectiveTo`
- **analytics (12):** `AnalyticsModelRun.periodStart <= periodEnd`; `TrendPoint.periodStart <= periodEnd`; `MetricDefinitionVersion.validFrom <= validTo`; `KpiEvaluation.periodStart <= periodEnd`; `AnalyticsDatasetVersion.periodStart <= periodEnd`; `AnalyticsFeatureValue.periodStart <= periodEnd`; `AnalyticsDataset.validFrom <= validTo`; `AnalyticsProjectionSnapshot.periodStart <= periodEnd`; `AnalyticsProjectionRun.periodStart <= periodEnd`; `MetricEvaluationRun.periodStart <= periodEnd`; `MetricValue.periodStart <= periodEnd`; `TrendAnalysis.periodStart <= periodEnd`
- **assets (5):** `AssetTechnicalAttributeValue.effectiveFrom <= effectiveTo`; `AssetSparePartCompatibility.effectiveFrom <= effectiveTo`; `AssetWarranty.validFrom <= validTo`; `MaintenanceStrategy.effectiveFrom <= effectiveTo`; `AssetServiceContractReference.validFrom <= validTo`
- **audit (1):** `AuditRetentionPolicy.validFrom <= validTo`
- **configuration (2):** `ConfigurationValue.effectiveFrom <= effectiveTo`; `ScopedConfigurationOverride.effectiveFrom <= effectiveTo`
- **custody (6):** `CustodyMeteringSystem.effectiveFrom <= effectiveTo`; `CustodyTransferPoint.effectiveFrom <= effectiveTo`; `CustodyMeasurementPeriod.periodStart <= periodEnd`; `CustodyDiscrepancy.openedAt <= closedAt`; `CustodyAgreement.validFrom <= validTo`; `CustodyAgreementParty.effectiveFrom <= effectiveTo`
- **documents (2):** `DocumentAccessGrant.validFrom <= validTo`; `DocumentVersion.effectiveFrom <= effectiveTo`
- **hse (2):** `ComplianceObligation.effectiveFrom <= effectiveTo`; `PermitToWork.validFrom <= validTo`
- **identity (8):** `UserGroupMembership.validFrom <= validTo`; `UserPermissionGrant.validFrom <= validTo`; `AuthorizationPolicyVersion.effectiveFrom <= effectiveTo`; `GroupRoleGrant.validFrom <= validTo`; `AuthorizationDelegationGrant.validFrom <= validTo`; `UserRoleGrant.validFrom <= validTo`; `RolePermissionGrant.validFrom <= validTo`; `SubjectSecurityAttribute.validFrom <= validTo`
- **integration (3):** `IntegrationSchemaVersion.effectiveFrom <= effectiveTo`; `ExternalEndpoint.validFrom <= validTo`; `ExternalObjectReference.validFrom <= validTo`
- **integrity (2):** `IntegrityAssessmentScope.validFrom <= validTo`; `IntegrityCase.openedAt <= closedAt`
- **leakdetection (2):** `LeakDetectionCase.openedAt <= closedAt`; `LeakDetectionRule.validFrom <= validTo`
- **monitoring (1):** `MonitoringThreshold.validFrom <= validTo`
- **notification (3):** `NotificationContactPoint.validFrom <= validTo`; `NotificationRecipientGroupMember.validFrom <= validTo`; `NotificationSuppressionRule.validFrom <= validTo`
- **organization (7):** `ReportingLine.validFrom <= validTo`; `ShiftAssignment.validFrom <= validTo`; `OrganizationUnit.validFrom <= validTo`; `OrganizationDelegation.validFrom <= validTo`; `EmployeeAddress.validFrom <= validTo`; `EmployeeAssignment.validFrom <= validTo`; `ResponsibilityAssignment.validFrom <= validTo`
- **party (7):** `PartyRoleAssignment.validFrom <= validTo`; `PartyRegistration.issuedAt <= expiresAt`; `PartyDocumentReference.validFrom <= validTo`; `PartyOwnershipLink.validFrom <= validTo`; `PartyRelationship.validFrom <= validTo`; `PartyLegalProfile.effectiveFrom <= effectiveTo`; `PartyCertification.issuedAt <= expiresAt`
- **planning (6):** `PlanTarget.validFrom <= validTo`; `PlanConstraint.validFrom <= validTo`; `Nomination.periodStart <= periodEnd`; `PlanningPeriod.periodStart <= periodEnd`; `ForecastPoint.validFrom <= validTo`; `ExpectedFlowState.validFrom <= validTo`
- **risk (3):** `RiskRegister.effectiveFrom <= effectiveTo`; `RiskMatrix.validFrom <= validTo`; `RiskAssessment.validFrom <= validTo`
- **telemetry (4):** `TelemetrySourceEndpoint.validFrom <= validTo`; `TelemetryPointBinding.validFrom <= validTo`; `TelemetryExternalTagMapping.validFrom <= validTo`; `TelemetryValidationRule.validFrom <= validTo`
- **topology (5):** `FacilityAttributeValue.validFrom <= validTo`; `EquipmentTypeVersion.effectiveFrom <= effectiveTo`; `EquipmentAttributeValue.validFrom <= validTo`; `FacilityTypeVersion.effectiveFrom <= effectiveTo`; `PipelineSystemFacility.validFrom <= validTo`

No additional ordering rule is approved for `createdAt` / `updatedAt`; those are classified as
persistence/lifecycle audit metadata in HRA-050.

## 7. Direct self-reference rules approved for HRA-051

- `documents.DocumentVersion`: if `supersededByVersionId` is present, it must not equal `id`.
- `workflow.WorkflowComment`: if `parentCommentId` is present, it must not equal `id`.
- `planning.PlanRevision`: if `baseRevisionId` is present, it must not equal `id`.
- `assets.MaintainableAsset`: if `parentAssetId` is present, it must not equal `id`.
- `assets.AssetType`: if `parentTypeId` is present, it must not equal `id`.
- `organization.OrganizationUnit`: if `parentUnitId` is present, it must not equal `id`.
- `party.PartyCatalogEntry`: if `parentEntryId` is present, it must not equal `id`.

`risk.RiskReview.previousRatingId` is not classified as self-reference because the field names a
rating identity rather than the `RiskReview` record itself.

## 8. Persistence-only exclusions

HRA-051 must **not** turn every database `NOT NULL` into a domain invariant:

- generic names, titles, comments, descriptions, labels, display snapshots, messages, expressions,
  paths/URIs, and other free text remain `PERSISTENCE_ONLY_TEXT` unless a later evidence-backed
  roadmap decision changes that classification;
- `createdAt` and `updatedAt` remain `PERSISTENCE_ONLY_AUDIT_TIMESTAMP`;
- database uniqueness and foreign-target existence remain application/persistence concerns;
- cross-module existence checks remain outside domain constructors;
- nullable JPA fields are not promoted to mandatory state by HRA-050.

## 9. HRA-051 execution gate

Before changing Java code, HRA-051 must regenerate this classifier against its exact `main` head.

If the live domain/JPA tree is unchanged, the expected counts are:

```text
same-named pairs                 465
matched nullable=false fields  3,385
required-field ENFORCE         2,038
already enforced                 365
persistence-only text            327
persistence-only audit time      655
temporal-order ENFORCE            83
self-reference ENFORCE             7
total HRA-051 ENFORCE rules    2,128
```

If the counts or affected models differ, HRA-051 must reconcile the live evidence with this document
before implementation. It must not silently broaden the classification.
