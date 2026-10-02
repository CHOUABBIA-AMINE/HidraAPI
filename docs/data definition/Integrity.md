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
