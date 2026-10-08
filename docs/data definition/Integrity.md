# HIDRA Integrity Module — Data Definition Document

```text
Document code : HIDRA-INTEGRITY-DDD
Repository    : HidraAPI
Module        : integrity
Package root  : dz.sh.hidra.modules.integrity
Table prefix  : hidra_integrity_*
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC : Digitalization Initiative
Author        : Abir MEDJERAB
UpdatedOn     : 2026-06-11
Status        : Active DDD reference
Version       : 1.1
```

---

## 1. Purpose

The `integrity` module owns the technical condition and engineering integrity decision record for pipelines, segments, facilities, and related topology assets.

It manages inspection campaigns, findings, defects, measurements, corrosion observations, cathodic-protection surveys, threats, remaining-life estimates, recommendations, and integrity cases.

---

## 2. Canonical implementation identity

```text
Module name   : integrity
Package root  : dz.sh.hidra.modules.integrity
Table prefix  : hidra_integrity_*
```

Forbidden table prefixes:

```text
hidra_topology_*
hidra_asset_*
hidra_assets_*
hidra_hse_*
hidra_incident_*
```

---

## 3. Ownership

Integrity owns:

```text
IntegrityProgram
IntegrityAssessment
IntegrityAssessmentScope
InspectionCampaign
InspectionRun
InspectionFinding
PipelineDefect
DefectMeasurement
WallThicknessMeasurement
CorrosionFeature
CoatingConditionObservation
CathodicProtectionSurvey
CathodicProtectionMeasurement
IntegrityThreat
DefectAssessment
RemainingLifeEstimate
IntegrityRecommendation
IntegrityCase
IntegrityCaseStatusHistory
IntegrityEvidenceLink
IntegrityCatalogEntry
IntegrityCatalogTranslation
```

Integrity does not own:

```text
Facility
Pipeline
PipelineSegment
TopologyNode
Equipment
MaintainableAsset
MaintenanceWorkOrder
HseCase
Incident
Alarm
TelemetryReading
Document binary storage
WorkflowTask
AuditRecord
SCADA/OT actuation
```

---

## 4. Topology reference model

Integrity references topology assets by neutral reference only:

```text
topologyAssetTypeCode
topologyAssetId
topologyAssetCodeSnapshot
topologyAssetNameSnapshot
```

Integrity must not import topology domain models, topology JPA entities, or topology repositories.

---

## 5. Entity catalogue

| Entity | Table | Purpose |
|---|---|---|
| IntegrityProgram | `hidra_integrity_program` | Long-term integrity program. |
| IntegrityAssessment | `hidra_integrity_assessment` | Assessment over topology scope. |
| IntegrityAssessmentScope | `hidra_integrity_assessment_scope` | Referenced topology scope. |
| InspectionCampaign | `hidra_integrity_inspection_campaign` | Inspection campaign. |
| InspectionRun | `hidra_integrity_inspection_run` | Inspection execution run. |
| InspectionFinding | `hidra_integrity_inspection_finding` | Finding from inspection. |
| PipelineDefect | `hidra_integrity_pipeline_defect` | Pipeline defect record. |
| DefectMeasurement | `hidra_integrity_defect_measurement` | Defect measurement. |
| WallThicknessMeasurement | `hidra_integrity_wall_thickness_measurement` | Wall-thickness measurement. |
| CorrosionFeature | `hidra_integrity_corrosion_feature` | Corrosion feature. |
| CoatingConditionObservation | `hidra_integrity_coating_condition_observation` | Coating observation. |
| CathodicProtectionSurvey | `hidra_integrity_cathodic_protection_survey` | CP survey. |
| CathodicProtectionMeasurement | `hidra_integrity_cathodic_protection_measurement` | CP measurement. |
| IntegrityThreat | `hidra_integrity_threat` | Integrity threat classification. |
| DefectAssessment | `hidra_integrity_defect_assessment` | Engineering assessment of defect. |
| RemainingLifeEstimate | `hidra_integrity_remaining_life_estimate` | Remaining-life estimate. |
| IntegrityRecommendation | `hidra_integrity_recommendation` | Recommendation for remediation or monitoring. |
| IntegrityCase | `hidra_integrity_case` | Integrity lifecycle case. |
| IntegrityCaseStatusHistory | `hidra_integrity_case_status_history` | Append-only status history. |
| IntegrityEvidenceLink | `hidra_integrity_evidence_link` | Evidence link. |
| IntegrityCatalogEntry | `hidra_integrity_catalog_entry` | Integrity-owned catalog entry. |
| IntegrityCatalogTranslation | `hidra_integrity_catalog_translation` | Multilingual catalog labels. |

---

## 6. Assets boundary

Integrity may recommend maintenance but must not create or own maintenance work orders directly.

Correct flow:

```text
IntegrityRecommendation
  -> application port/event
      -> assets.MaintenanceWorkOrder
```

---

## 7. HSE and incident boundary

Integrity may reference an HSE or incident context by neutral reference only. It does not own incident or HSE case lifecycle.

Incident management remains blocked until its own DDD exists.

---

## 8. Documentation and annotation rule

Domain, application, and infrastructure integrity models must not use `@Schema` or OpenAPI annotations.

`@Schema` is allowed only in integrity API request/response models.


---

## 8. PipelineDefect source-finding provenance

`PipelineDefect.sourceFindingId` is an optional same-module provenance reference to the
Integrity-owned `InspectionFinding`.

```text
sourceFindingId = null
  -> valid; the defect is not required to originate from an inspection finding

sourceFindingId != null
  -> InspectionFinding with that id must exist
  -> dangling provenance is rejected before persistence
  -> PostgreSQL independently protects the nullable reference with ON DELETE RESTRICT
```

HMR-019 does not require `InspectionFinding.linkedDefectId` to be reciprocal, one-to-one, or
automatically synchronized because the active Integrity semantics do not establish such a rule.
It also does not invent finding-status eligibility, defect-number uniqueness, Topology validation,
coordinate constraints, closure timing, or a defect lifecycle transition matrix.


---

## HMR-050 — IntegrityProgram executable semantics

Current HMR-050 baseline:

```text
programTypeId -> existing, active IntegrityCatalogEntry.catalogName = INTEGRITY_PROGRAM_TYPE
ownerOrganizationUnitId -> Organization-owned IntegrityOrganizationUnitReferenceContract when populated
ownerOrganizationUnitNameSnapshot -> preserved as historical/display snapshot; owner validation does not rewrite it
cross-module Organization FK -> forbidden / not introduced
```

The existing HRA-111 same-module foreign key continues to protect
`hidra_integrity_program.program_type_id -> hidra_integrity_catalog_entry.id`.
HMR-050 therefore requires no new schema migration after exact-current revalidation.


## HMR-072 execution reconciliation — 2026-10-08

Nullable programme membership now fails closed to Integrity-owned records with validated forward 013 FK. Identity validates new/changed assessor, reviewer and approver references; Workflow validates the exact assessment module/type/ID and configured purpose binding. Locked transactional adapter saves preserve unchanged historical provenance and reject missing/changed owners. Methodology, title domain semantics, optional programme, lifecycle and current null Audit metadata retain their original contracts. Changed production and 18 focused unit signatures compiled with temporary APIs; eight actual domain/reference checks passed using controlled owner fixtures. Four real PostgreSQL tests prepared for nullable links, orphan rollback and parent-delete races. Focused Maven plus compile/full-test/clean-verify targets stop before execution at uncached offline Spring Boot 4.1.1 parent.

Actual production verification remains the final-head CI gate.

## HMR-098 — IntegrityCase executable reference contract

- primaryDefectId remains optional; supplied IDs require an existing Integrity-owned
  PipelineDefect, with a nullable ON DELETE RESTRICT FK. No defect-status or topology
  equality restriction is adopted.
- caseTypeId requires exact family membership from explicitly approved
  hidra_integrity_catalog_field_policy metadata for field role CASE_TYPE. The role
  is not a catalog_name. New/changed references require active mapping and entry.
  No family/default metadata is automatically seeded. Used mapping/catalog identity
  is protected; valid unchanged inactive history remains readable and writable.
- New/changed topology linkage uses Topology's case reference export for PIPELINE,
  SEGMENT, FACILITY, EQUIPMENT, NODE and CONNECTION. Its code snapshot is canonical
  on fresh linkage and immutable for an unchanged historical target.
- Optional new/changed opening actor, unit and Workflow context use their owner
  contracts. Workflow targets the actual Integrity case and configured active binding;
  this attests context, not approval. Neutral HSE/Incident sources and optional severity
  remain scalar references without invented live-state/family requirements.
- The 20-field contract, stable lifecycle, optional values and openedAt <= closedAt
  remain. No stronger CLOSED/time, close/resolve operation, Workflow orchestration,
  required optional reference, foreign database FK or asset action is introduced.
- Forward 017/018 preserve all published migrations. Existing cases without approved
  family metadata fail 018, leaving committed 017 available for approved configuration
  and retry. Orphan/family/time inconsistency requires reconciliation from real evidence.
  Deployments must provision approved CASE_TYPE metadata before fresh case writes.

Validation: 53 actual controlled-fixture checks passed; Java source/test signatures
compiled using temporary APIs. Thirty unit and twelve real PostgreSQL/Spring-JPA test
methods are prepared, not executed locally. Maven verification stops before execution
at uncached offline Spring Boot parent 4.1.1. Actual runtime verification remains CI.
