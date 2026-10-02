# HIDRA — Risk Management Data Definition Document

```text
Document        : HIDRA-RISK-MANAGEMENT-DATA-DEFINITION-DOCUMENT.md
Module          : risk
Package         : dz.sh.hidra.modules.risk
Product         : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner           : Sonatrach / TRC : Digitalization Initiative
Author          : Abir MEDJERAB
CreatedOn       : 2026-06-11
Status          : Target DDD / not yet implemented as a Java module
Evidence level  : Target architecture, repository-aligned boundaries
```

---

## 1. Purpose

The **Risk Management** module is the bounded context responsible for transforming trusted operational evidence into structured, explainable, versioned, and auditable risk assessments.

Risk Management answers:

```text
What can go wrong?
Where can it happen?
How likely is it?
How severe would the consequence be?
Which asset, segment, station, operation, or process is exposed?
What evidence supports the score?
What treatment or mitigation is required?
Who accepted, reduced, transferred, or rejected the risk?
What is the residual risk after treatment?
```

Risk Management is not a dashboard-only module and not an analytics-only projection. It is a source-of-truth module for governed risk scoring, risk registers, risk treatment, risk acceptance, and risk evidence.

---

## 2. Architectural position

Risk Management sits after trusted operational facts and before enterprise-level prioritization.

```text
Topology
  -> Telemetry
     -> Planning
        -> Monitoring
           -> Alarms
              -> Incidents
                 -> Integrity / HSE / Assets / Custody / Simulation
                    -> Risk
                       -> Analytics / Reporting / Executive decisions
```

Risk Management must consume evidence from other contexts without taking over their state.

```text
Monitoring may produce RiskSignal.
Incidents may produce impact and root-cause evidence.
Network Integrity may produce defect and remaining-life evidence.
HSE may produce safety/environmental consequence evidence.
Asset Management may produce failure/maintenance evidence.
Simulation may produce scenario consequence evidence.
Risk Management combines those into governed risk assessments.
```

---

## 3. Core boundary rule

```text
Risk Management owns risk scoring, risk registers, treatment, acceptance, and residual risk.

It does not own the operational fact that caused the risk.
It does not own incidents, alarms, telemetry, topology, HSE cases, integrity defects, maintenance work orders, simulations, analytics dashboards, or reports.
```

Opinionated rule:

```text
Monitoring detects signals.
Incidents manage response.
HSE manages safety/environment consequences.
Integrity assesses technical condition.
Simulation estimates possible futures.
Risk decides exposure, score, treatment, and acceptance.
```

---

## 4. Ownership matrix

| Concept | Owner | Notes |
|---|---|---|
| Pipeline, station, equipment, segment | Topology | Risk references topology assets by stable IDs/snapshots |
| Measured pressure/flow/temperature | Telemetry | Risk references trusted readings or derived evidence |
| Threshold violation / deviation | Monitoring | Risk consumes `RiskSignal` or monitoring evaluation references |
| Alarm lifecycle | Alarm Management | Risk can reference alarms as evidence |
| Operational incident lifecycle | Incident Management | Risk can reference incidents, impacts, RCA, closure |
| Safety/environment consequence | HSE Management | Risk consumes HSE consequence and compliance evidence |
| Pipeline defect / corrosion / remaining life | Network Integrity | Risk consumes integrity assessments |
| Maintenance plan/work order | Asset Management | Risk consumes maintenance history and may recommend treatment |
| Simulation scenario/result | Simulation | Risk consumes simulation outputs as consequence/likelihood evidence |
| Risk matrix, likelihood, consequence, score | Risk Management | Risk owns |
| Mitigation/treatment decision | Risk Management + Workflow | Risk owns business treatment record; Workflow owns approval route |
| Risk dashboard | Analytics / Reporting | Derived view, not source of truth |
| Audit evidence | Audit | Risk emits audit-ready events and references audit records |

---

## 5. Module scope

### 5.1 Risk Management owns

```text
RiskRegister
RiskAssessment
RiskAssessmentScope
RiskSource
RiskScenario
RiskThreat
RiskConsequence
RiskLikelihood
RiskExposure
RiskMatrix
RiskMatrixCell
RiskScore
RiskRating
RiskControl
RiskTreatmentPlan
RiskTreatmentAction
RiskMitigationMeasure
RiskAcceptance
ResidualRiskAssessment
RiskReview
RiskEvidenceLink
RiskAggregationSnapshot
RiskCatalogEntry
RiskCatalogTranslation
```

### 5.2 Risk Management references

```text
topology asset reference
telemetry reading reference
monitoring risk signal reference
monitoring evaluation reference
alarm reference
incident reference
integrity assessment reference
integrity defect reference
HSE case reference
asset maintenance reference
simulation scenario/run/result reference
planning target reference
custody transfer point reference
workflow approval reference
audit event reference
actor snapshot
organization unit snapshot
document reference
```

### 5.3 Risk Management does not own

```text
pipeline topology
telemetry readings
monitoring rules
alarm state
incident lifecycle
HSE case lifecycle
integrity defect lifecycle
maintenance work orders
simulation models
workflow tasks
audit ledger
analytics projections
report files
external-system synchronization
```

---

## 6. Core lifecycle

### 6.1 Risk assessment lifecycle

```text
DRAFT
  -> UNDER_REVIEW
  -> APPROVED
  -> ACTIVE
  -> SUPERSEDED
  -> RETIRED
```

Exceptional states:

```text
REJECTED
CANCELLED
EXPIRED
```

### 6.2 Risk treatment lifecycle

```text
PROPOSED
  -> APPROVED
  -> IN_PROGRESS
  -> COMPLETED
  -> VERIFIED
  -> CLOSED
```

Exceptional states:

```text
REJECTED
CANCELLED
OVERDUE
INEFFECTIVE
```

### 6.3 Risk acceptance lifecycle

```text
REQUESTED
  -> REVIEWED
  -> ACCEPTED
  -> EXPIRED
```

Exceptional states:

```text
REJECTED
REVOKED
```

---

## 7. Aggregates and entities

## 7.1 `RiskRegister`

### Purpose

A governed container for risks within a scope such as pipeline system, region, business unit, station, facility, operation type, integrity program, or HSE domain.

### Ownership

`RiskRegister` owns the register identity, scope, classification, status, and review policy.

### Fields

```text
id
code
nameAr
nameFr
nameEn
description
registerTypeId
ownerOrganizationUnitId
ownerOrganizationUnitNameSnapshot
scopeType
scopeId
scopeCodeSnapshot
scopeLabelSnapshot
status
reviewFrequencyId
effectiveFrom
effectiveTo
createdByActorId
createdByDisplayNameSnapshot
createdAt
updatedAt
```

### Invariants

```text
code is unique per owner organization scope
status must follow controlled lifecycle
effectiveTo must be after effectiveFrom
active register must have owner organization unit
```

---

## 7.2 `RiskAssessment`

### Purpose

A formal evaluation of one risk scenario over a defined scope, using evidence, methodology, likelihood, consequence, controls, and score.

### Fields

```text
id
riskRegisterId
assessmentNumber
title
description
assessmentTypeId
methodologyId
scopeId
riskScenarioId
status
assessmentDate
validFrom
validTo
assessedByActorId
assessedByDisplayNameSnapshot
reviewedByActorId
reviewedByDisplayNameSnapshot
approvedByActorId
approvedByDisplayNameSnapshot
approvedAt
inherentLikelihoodId
inherentConsequenceId
inherentScore
inherentRatingId
residualLikelihoodId
residualConsequenceId
residualScore
residualRatingId
confidenceLevelId
uncertaintyNote
workflowReferenceId
auditReferenceId
createdAt
updatedAt
```

### Invariants

```text
approved risk assessment must have reviewer/approver snapshot
residual risk cannot be calculated without control/treatment context
validTo must be after validFrom
inherent score must use the selected matrix version
residual score must use the same or explicitly referenced matrix version
```

---

## 7.3 `RiskAssessmentScope`

### Purpose

Defines what the assessment applies to.

### Fields

```text
id
riskAssessmentId
scopeType
scopeId
scopeCodeSnapshot
scopeLabelSnapshot
topologySnapshotId
operationalPeriodStart
operationalPeriodEnd
included
scopeNote
createdAt
```

### Examples

```text
PIPELINE_SYSTEM
PIPELINE
PIPELINE_SEGMENT
STATION
FACILITY
EQUIPMENT
MEASUREMENT_POINT
OPERATIONAL_PLAN
INCIDENT
INTEGRITY_CASE
HSE_CASE
SIMULATION_SCENARIO
```

---

## 7.4 `RiskSource`

### Purpose

Captures the origin that triggered or justified the risk assessment.

### Fields

```text
id
riskAssessmentId
sourceModule
sourceType
sourceId
sourceCodeSnapshot
sourceLabelSnapshot
sourceObservedAt
sourceSeveritySnapshot
sourceConfidenceSnapshot
createdAt
```

### Examples

```text
MONITORING_RISK_SIGNAL
ALARM
INCIDENT
HSE_CASE
INTEGRITY_DEFECT
CORROSION_FEATURE
CP_SURVEY
SIMULATION_RESULT
MAINTENANCE_FAILURE
TELEMETRY_DEVIATION
```

---

## 7.5 `RiskScenario`

### Purpose

Defines the risk event being assessed.

### Fields

```text
id
code
nameAr
nameFr
nameEn
description
scenarioTypeId
threatId
primaryConsequenceCategoryId
active
createdAt
updatedAt
```

### Examples

```text
PIPELINE_LEAK
PIPELINE_RUPTURE
OVERPRESSURE
UNDERDELIVERY
QUALITY_OFF_SPEC
STATION_SHUTDOWN
PUMP_FAILURE
COMPRESSOR_FAILURE
CUSTODY_DISPUTE
HSE_SPILL
ENVIRONMENTAL_EXPOSURE
CYBER_OT_DISRUPTION
```

---

## 7.6 `RiskThreat`

### Purpose

Represents a threat source or hazard mechanism that can lead to a risk scenario.

### Fields

```text
id
code
nameAr
nameFr
nameEn
description
threatCategoryId
active
createdAt
updatedAt
```

### Examples

```text
INTERNAL_CORROSION
EXTERNAL_CORROSION
THIRD_PARTY_DAMAGE
GROUND_MOVEMENT
OPERATOR_ERROR
EQUIPMENT_DEGRADATION
INSTRUMENT_FAILURE
SCADA_DATA_LOSS
POWER_FAILURE
SECURITY_INTRUSION
```

---

## 7.7 `RiskConsequence`

### Purpose

Captures consequence categories and scored impacts.

### Fields

```text
id
riskAssessmentId
categoryId
consequenceLevelId
description
peopleImpactLevelId
environmentImpactLevelId
productionImpactLevelId
assetImpactLevelId
financialImpactLevelId
reputationImpactLevelId
complianceImpactLevelId
estimatedCost
currencyCode
createdAt
updatedAt
```

### Invariants

```text
at least one consequence category is required for a scored assessment
estimated cost must be non-negative
impact levels must belong to the selected risk matrix methodology
```

---

## 7.8 `RiskLikelihood`

### Purpose

Captures likelihood/frequency/probability assessment.

### Fields

```text
id
riskAssessmentId
likelihoodLevelId
probabilityValue
frequencyEstimate
frequencyUnitId
likelihoodBasisId
confidenceLevelId
evidenceSummary
createdAt
updatedAt
```

### Invariants

```text
probabilityValue must be between 0 and 1 when provided
frequencyEstimate must be non-negative
likelihood level must belong to the selected matrix version
```

---

## 7.9 `RiskExposure`

### Purpose

Defines what is exposed to the risk and over which period.

### Fields

```text
id
riskAssessmentId
exposureTypeId
exposedObjectType
exposedObjectId
exposedObjectCodeSnapshot
exposedObjectLabelSnapshot
exposureStart
exposureEnd
exposureMagnitude
exposureUnitId
populationExposure
environmentalSensitivityId
productionCriticalityId
createdAt
updatedAt
```

---

## 7.10 `RiskMatrix`

### Purpose

Defines the scoring method used to transform likelihood and consequence into risk rating.

### Fields

```text
id
code
nameAr
nameFr
nameEn
description
matrixTypeId
version
status
validFrom
validTo
createdByActorId
approvedByActorId
approvedAt
createdAt
updatedAt
```

### Invariants

```text
active matrix version must be immutable
only one active matrix version per matrix code and scope
approved matrix must contain complete cells
```

---

## 7.11 `RiskMatrixCell`

### Purpose

Defines the result for a likelihood/consequence pair.

### Fields

```text
id
riskMatrixId
likelihoodLevelId
consequenceLevelId
scoreValue
ratingId
colorCode
requiresTreatment
requiresApproval
requiresExecutiveAcceptance
createdAt
updatedAt
```

### Invariants

```text
unique cell per matrix, likelihood, consequence
scoreValue must be non-negative
ratingId must be catalog-backed
```

---

## 7.12 `RiskScore`

### Purpose

Stores calculated score details for an assessment.

### Fields

```text
id
riskAssessmentId
scoreType
riskMatrixId
likelihoodLevelId
consequenceLevelId
scoreValue
ratingId
ratingLabelSnapshot
calculatedAt
calculationMethod
explanation
createdAt
```

### Score types

```text
INHERENT
CURRENT
RESIDUAL
TARGET
```

---

## 7.13 `RiskRating`

### Purpose

Catalog-backed rating value such as LOW, MEDIUM, HIGH, CRITICAL.

### Fields

```text
id
code
nameAr
nameFr
nameEn
description
severityOrder
requiresTreatment
requiresApproval
active
createdAt
updatedAt
```

---

## 7.14 `RiskControl`

### Purpose

A preventive, detective, corrective, or compensating control that reduces likelihood or consequence.

### Fields

```text
id
riskAssessmentId
controlCode
controlName
controlTypeId
controlOwnerOrganizationUnitId
controlOwnerNameSnapshot
effectivenessLevelId
effectivenessJustification
verified
verifiedByActorId
verifiedAt
createdAt
updatedAt
```

### Control types

```text
PREVENTIVE
DETECTIVE
CORRECTIVE
COMPENSATING
RECOVERY
ADMINISTRATIVE
ENGINEERING
PROCEDURAL
```

---

## 7.15 `RiskTreatmentPlan`

### Purpose

A formal plan to reduce, transfer, avoid, monitor, or accept risk.

### Fields

```text
id
riskAssessmentId
treatmentStrategyId
title
description
ownerOrganizationUnitId
ownerOrganizationUnitNameSnapshot
ownerActorId
ownerDisplayNameSnapshot
status
targetResidualRatingId
targetCompletionDate
workflowReferenceId
auditReferenceId
createdAt
updatedAt
```

### Strategies

```text
AVOID
REDUCE
TRANSFER
ACCEPT
MONITOR
ESCALATE
```

---

## 7.16 `RiskTreatmentAction`

### Purpose

A concrete action in a treatment plan.

### Fields

```text
id
riskTreatmentPlanId
actionCode
title
description
actionTypeId
ownerActorId
ownerDisplayNameSnapshot
ownerOrganizationUnitId
ownerOrganizationUnitNameSnapshot
targetDate
completedAt
verificationRequired
verifiedByActorId
verifiedAt
status
linkedWorkOrderId
linkedWorkflowTaskId
createdAt
updatedAt
```

### Boundary note

Risk may recommend or track treatment actions, but execution may belong to another module.

```text
maintenance action -> Asset Management
inspection action -> Network Integrity
operational response -> Incident Management
permit/safety action -> HSE
approval route -> Workflow
```

---

## 7.17 `RiskMitigationMeasure`

### Purpose

Reusable mitigation/control pattern that can be assigned to assessments or treatment plans.

### Fields

```text
id
code
nameAr
nameFr
nameEn
description
mitigationTypeId
applicableThreatTypeId
applicableAssetTypeId
expectedEffectOnLikelihood
expectedEffectOnConsequence
active
createdAt
updatedAt
```

---

## 7.18 `RiskAcceptance`

### Purpose

Formal record that a risk is accepted by an accountable actor/committee for a defined period and condition.

### Fields

```text
id
riskAssessmentId
acceptanceNumber
acceptedRatingId
acceptedScore
acceptanceReasonId
acceptanceJustification
acceptedByActorId
acceptedByDisplayNameSnapshot
acceptedByOrganizationUnitId
acceptedByOrganizationUnitNameSnapshot
acceptedAt
validUntil
reviewRequired
status
workflowReferenceId
auditReferenceId
createdAt
updatedAt
```

### Invariants

```text
risk acceptance must have expiry or explicit permanent-acceptance authorization
high/critical acceptance requires workflow approval
acceptedAt must be before validUntil
revoked acceptance cannot be reused
```

---

## 7.19 `ResidualRiskAssessment`

### Purpose

A reassessment after controls and treatment actions.

### Fields

```text
id
riskAssessmentId
treatmentPlanId
reassessmentDate
residualLikelihoodId
residualConsequenceId
residualScore
residualRatingId
residualConfidenceLevelId
residualJustification
assessedByActorId
approvedByActorId
approvedAt
createdAt
updatedAt
```

---

## 7.20 `RiskReview`

### Purpose

Periodic review of a risk assessment or register item.

### Fields

```text
id
riskAssessmentId
reviewTypeId
reviewStatus
reviewDueDate
reviewedAt
reviewedByActorId
reviewedByDisplayNameSnapshot
reviewFinding
ratingChanged
previousRatingId
newRatingId
nextReviewDueDate
createdAt
updatedAt
```

---

## 7.21 `RiskEvidenceLink`

### Purpose

Links evidence from other modules to a risk assessment.

### Fields

```text
id
riskAssessmentId
evidenceModule
evidenceType
evidenceId
evidenceCodeSnapshot
evidenceLabelSnapshot
evidenceTimestamp
evidenceHash
evidenceSummary
createdAt
```

### Examples

```text
MonitoringEvaluation
RiskSignal
Alarm
Incident
IncidentImpactAssessment
HseImpactAssessment
PipelineDefect
WallThicknessMeasurement
CorrosionFeature
CathodicProtectionMeasurement
MaintenanceWorkOrder
SimulationRun
SimulationOptimizationCandidate
TelemetryReading
CustodyTransferTicket
Document
AuditEvent
```

---

## 7.22 `RiskAggregationSnapshot`

### Purpose

Stores a versioned aggregation of risk posture for a scope and time.

### Fields

```text
id
scopeType
scopeId
scopeCodeSnapshot
scopeLabelSnapshot
snapshotDate
riskMatrixId
totalRiskCount
criticalRiskCount
highRiskCount
mediumRiskCount
lowRiskCount
averageRiskScore
maximumRiskScore
openTreatmentCount
overdueTreatmentCount
acceptedRiskCount
createdAt
```

### Boundary note

This is an operational risk projection inside Risk Management. Advanced KPI dashboards and BI materializations belong to Analytics/Reporting.

---

## 7.23 `RiskCatalogEntry`

### Purpose

Controlled vocabulary for Risk Management.

### Fields

```text
id
catalogName
code
active
sortOrder
systemDefined
createdAt
updatedAt
```

### Catalog names

```text
RISK_REGISTER_TYPE
RISK_ASSESSMENT_TYPE
RISK_METHODLOGY
RISK_SCENARIO_TYPE
RISK_THREAT_CATEGORY
RISK_CONSEQUENCE_CATEGORY
RISK_LIKELIHOOD_LEVEL
RISK_CONSEQUENCE_LEVEL
RISK_RATING
RISK_CONFIDENCE_LEVEL
RISK_EXPOSURE_TYPE
RISK_CONTROL_TYPE
RISK_CONTROL_EFFECTIVENESS
RISK_TREATMENT_STRATEGY
RISK_TREATMENT_STATUS
RISK_ACCEPTANCE_REASON
RISK_REVIEW_TYPE
RISK_SOURCE_TYPE
```

---

## 7.24 `RiskCatalogTranslation`

### Purpose

Multilingual labels for catalog values.

### Fields

```text
id
catalogEntryId
locale
name
description
createdAt
updatedAt
```

### Required locales

```text
fr
ar
en
```

French is mandatory for operational use; Arabic and English should be supported for enterprise reporting and multilingual UI.

---

## 8. Recommended database tables

```text
hidra_risk_register
hidra_risk_assessment
hidra_risk_assessment_scope
hidra_risk_source
hidra_risk_scenario
hidra_risk_threat
hidra_risk_consequence
hidra_risk_likelihood
hidra_risk_exposure
hidra_risk_matrix
hidra_risk_matrix_cell
hidra_risk_score
hidra_risk_rating
hidra_risk_control
hidra_risk_treatment_plan
hidra_risk_treatment_action
hidra_risk_mitigation_measure
hidra_risk_acceptance
hidra_residual_risk_assessment
hidra_risk_review
hidra_risk_evidence_link
hidra_risk_aggregation_snapshot
hidra_risk_catalog_entry
hidra_risk_catalog_translation
```

---

## 9. Package structure

```text
dz.sh.hidra.modules.risk
  api
    rest
      controller
      request
      response
      mapper
  application
    command
    query
    dto
    service
    port
      in
      out
  domain
    model
    value
    event
    policy
    service
    exception
  infrastructure
    configuration
    persistence
      entity
      repository
      mapper
      adapter
    messaging
    projection
```

---

## 10. Public inbound ports

```text
CreateRiskRegisterUseCase
UpdateRiskRegisterUseCase
CreateRiskAssessmentUseCase
SubmitRiskAssessmentForReviewUseCase
ApproveRiskAssessmentUseCase
RejectRiskAssessmentUseCase
CalculateRiskScoreUseCase
AddRiskEvidenceUseCase
AddRiskControlUseCase
CreateRiskTreatmentPlanUseCase
UpdateRiskTreatmentActionUseCase
AcceptRiskUseCase
RevokeRiskAcceptanceUseCase
ReviewRiskAssessmentUseCase
GenerateRiskAggregationSnapshotUseCase
```

---

## 11. Outbound ports

```text
RiskTopologyLookupPort
RiskTelemetryEvidenceLookupPort
RiskMonitoringEvidenceLookupPort
RiskAlarmEvidenceLookupPort
RiskIncidentEvidenceLookupPort
RiskIntegrityEvidenceLookupPort
RiskHseEvidenceLookupPort
RiskAssetEvidenceLookupPort
RiskSimulationEvidenceLookupPort
RiskWorkflowPort
RiskAuditEventPort
RiskDocumentReferencePort
RiskNotificationPort
```

Dependency rule:

```text
Risk application layer defines ports.
Risk infrastructure implements adapters.
Risk must not import foreign domain models or JPA entities.
```

---

## 12. Domain events

```text
RiskRegisterCreated
RiskAssessmentCreated
RiskEvidenceLinked
RiskScoreCalculated
RiskAssessmentSubmittedForReview
RiskAssessmentApproved
RiskAssessmentRejected
RiskTreatmentPlanCreated
RiskTreatmentActionAssigned
RiskTreatmentActionCompleted
RiskAcceptanceRequested
RiskAccepted
RiskAcceptanceRevoked
ResidualRiskAssessed
RiskReviewCompleted
RiskAggregationSnapshotCreated
```

Events should be published through platform outbox for reliable downstream processing.

---

## 13. Risk scoring rule

Minimum scoring formula:

```text
risk_score = likelihood_score × consequence_score
```

But do not hard-code this as the only possible model.

Risk Management must support matrix-based scoring:

```text
RiskMatrix
  -> RiskMatrixCell
      likelihoodLevel
      consequenceLevel
      scoreValue
      rating
      treatmentRequired
      approvalRequired
```

This lets Hidra use different matrices for:

```text
operational risk
integrity risk
HSE risk
commercial/custody risk
cyber/OT risk
project risk
enterprise risk
```

---

## 14. Risk and monitoring relationship

Monitoring owns operational evaluation and risk signals.

```text
MonitoringEvaluation
  -> RiskSignal
      signalType
      severity
      topologyAssetReference
      telemetryReference
      planningTargetReference
```

Risk Management consumes those signals as evidence.

```text
RiskSource
RiskEvidenceLink
RiskAssessment
RiskScore
RiskTreatmentPlan
```

Monitoring must not calculate enterprise risk scores. Risk must not own monitoring thresholds or real-time alert state.

---

## 15. Risk and incident relationship

Incident Management owns what happened and how response was handled.

Risk Management owns what the incident means for future exposure and risk posture.

```text
Incident
  -> IncidentImpactAssessment
  -> RootCauseAnalysis
  -> IncidentResolution
      ↓
RiskAssessment / RiskReview / RiskTreatmentPlan
```

Example:

```text
Incident: leak detected on segment SG-042
Risk: third-party damage likelihood increased for corridor C-12
Risk treatment: additional patrol / marker replacement / integrity inspection / stakeholder communication
```

---

## 16. Risk and HSE relationship

HSE owns health, safety, environmental, and compliance case details.

Risk Management consumes HSE evidence to score consequences and treatment urgency.

```text
HseCase
HseImpactAssessment
ComplianceAssessment
PermitToWork
EnvironmentalEvent
  ↓
RiskConsequence
RiskAssessment
RiskTreatmentPlan
```

Risk does not replace HSE compliance management.

---

## 17. Risk and Network Integrity relationship

Network Integrity owns technical pipeline condition evidence.

Risk Management uses it for likelihood, consequence, and residual risk.

```text
PipelineDefect
CorrosionFeature
WallThicknessMeasurement
CathodicProtectionMeasurement
RemainingLifeEstimate
IntegrityRecommendation
  ↓
RiskAssessment
RiskControl
RiskTreatmentPlan
ResidualRiskAssessment
```

Integrity says whether condition is acceptable. Risk says how that condition affects exposure and priorities.

---

## 18. Risk and Simulation relationship

Simulation estimates possible outcomes and optimal candidates.

Risk Management consumes simulation outcomes as scenario evidence.

```text
SimulationScenario
SimulationRun
SimulationOptimizationCandidate
SimulationValidationFinding
  ↓
RiskScenario
RiskConsequence
RiskLikelihood
RiskTreatmentPlan
```

Simulation recommends. Risk evaluates exposure. Topology owns approved network state.

---

## 19. Risk and Analytics relationship

Risk Management is a source-of-truth operational risk context.

Analytics can consume risk history to build:

```text
risk heat maps
trend analysis
risk reduction KPIs
critical risk dashboards
forecasted risk models
```

But analytics must not modify risk registers, risk assessments, scores, controls, treatments, or acceptance records.

---

## 20. REST API surface

Recommended base path:

```text
/api/v1/risks
```

Controller groups:

```text
RiskCatalogController
RiskRegisterController
RiskAssessmentController
RiskMatrixController
RiskTreatmentController
RiskAcceptanceController
RiskReviewController
RiskAggregationController
```

Endpoint examples:

```text
GET    /api/v1/risks/catalogs/{catalogName}
POST   /api/v1/risks/registers
GET    /api/v1/risks/registers/{registerId}
POST   /api/v1/risks/assessments
GET    /api/v1/risks/assessments/{assessmentId}
POST   /api/v1/risks/assessments/{assessmentId}/evidence
POST   /api/v1/risks/assessments/{assessmentId}/calculate
POST   /api/v1/risks/assessments/{assessmentId}/submit
POST   /api/v1/risks/assessments/{assessmentId}/approve
POST   /api/v1/risks/assessments/{assessmentId}/reject
POST   /api/v1/risks/assessments/{assessmentId}/controls
POST   /api/v1/risks/assessments/{assessmentId}/treatment-plans
POST   /api/v1/risks/assessments/{assessmentId}/acceptance
POST   /api/v1/risks/assessments/{assessmentId}/reviews
GET    /api/v1/risks/scopes/{scopeType}/{scopeId}/summary
```

---

## 21. Validation rules

```text
Risk assessment must reference a register.
Risk assessment must reference at least one scope.
Risk assessment must reference at least one scenario.
Risk assessment must have evidence before approval.
Approved risk assessment is immutable except through new review/revision.
High or critical risks require treatment or acceptance.
Risk acceptance must have accountable actor snapshot.
Risk treatment action must have owner and target date.
Residual risk requires treatment/control evidence.
Risk matrix version used by an approved assessment cannot be changed retroactively.
```

---

## 22. Security and authorization

Suggested permissions:

```text
risk.register.create
risk.register.read
risk.register.update
risk.assessment.create
risk.assessment.read
risk.assessment.update
risk.assessment.submit
risk.assessment.approve
risk.assessment.reject
risk.score.calculate
risk.evidence.add
risk.control.add
risk.treatment.create
risk.treatment.update
risk.acceptance.request
risk.acceptance.approve
risk.review.perform
risk.catalog.manage
```

Organization scope should apply to risk registers and assessments.

```text
A user may read or update only risks within authorized organization units,
topology scopes, or assigned responsibilities.
```

---

## 23. Audit requirements

Risk must emit audit-ready events for:

```text
risk register creation/update
risk assessment creation
evidence linking
score calculation
assessment approval/rejection
treatment plan approval
risk acceptance/revocation
residual risk reassessment
risk review completion
```

Audit payload must include:

```text
actor snapshot
organization unit snapshot
target reference
risk assessment reference
risk score before/after
decision reason
workflow reference
correlation id
request id
timestamp
```

---

## 24. Reporting and analytics readiness

Risk Management should expose read models for:

```text
current risk profile by pipeline system
current risk profile by station/facility
top risks by severity
overdue treatment actions
accepted high/critical risks
risk trend by month
risk reduction after treatment
risk by threat category
risk by consequence category
integrity-driven risk summary
HSE-driven risk summary
incident-driven risk summary
```

But the authoritative data remains the risk assessment and treatment records.

---

## 25. Implementation order

Recommended roadmap:

```text
RISK-001 package skeleton
RISK-002 risk catalogs and translations
RISK-003 risk matrix and matrix cells
RISK-004 risk register
RISK-005 risk scenario and threat catalogs
RISK-006 risk assessment aggregate
RISK-007 assessment scope and source
RISK-008 evidence links
RISK-009 likelihood/consequence/score calculation
RISK-010 controls
RISK-011 treatment plan and actions
RISK-012 risk acceptance
RISK-013 residual risk assessment
RISK-014 risk review
RISK-015 aggregation snapshots
RISK-016 workflow approval integration
RISK-017 audit event port
RISK-018 REST API
RISK-019 read models and search
RISK-020 architecture tests
```

---

## 26. Final design rule

```text
Risk Management is not a chart.
Risk Management is not a log.
Risk Management is not monitoring.
Risk Management is not HSE.
Risk Management is not integrity.

Risk Management is the governed explanation of exposure,
priority, treatment, acceptance, and residual risk.
```
