# HidraAPI Ubiquitous Language

## Status

CURRENT vocabulary baseline, source-derived.

## Reading Rule

Terms below use current HidraAPI class/field/policy names. They are canonical for documentation unless a later code/domain decision changes them. A term's presence does not imply every lifecycle or integration path is complete.

| Term | Canonical meaning in current source |
|---|---|
| Pipeline System | `PipelineSystem`: logical transportation-system grouping identified by code, type and topology status. |
| Pipeline | `Pipeline`: physical or logical pipeline belonging to a pipeline system, with type, design attributes and topology status. |
| Topology Connection | `TopologyConnection`: graph connection between distinct nodes; self-connections are rejected. |
| Facility | `Facility`: topology-owned facility concept. |
| Equipment | `Equipment`: topology-owned equipment concept; maintenance ownership is separate in `assets`. |
| Topology Asset Reference | Stable type/id/code or snapshot-style reference to topology identity without importing topology aggregates into another module. |
| Telemetry Source | `TelemetrySource`: acquisition-source metadata; only ACTIVE source status is ingestion-eligible. |
| Telemetry Point | `TelemetryPoint`: canonical Hidra telemetry point/tag with point/signal type and optional unit/range metadata. |
| Telemetry Reading | `TelemetryReading`: raw received reading. Exactly one numeric/text/boolean value is required except REJECTED or QUARANTINED evidence may be null-valued. |
| Trusted Telemetry Reading | `TrustedTelemetryReading`: downstream trusted-reading contract carrying trust level, quality-assessment identity and optional topology snapshot/reference data. |
| Alarm | `Alarm`: formal operational alarm instance with source, severity/priority, topology references and lifecycle state. |
| Alarm Acknowledgement | `AlarmAcknowledgement`: operator acknowledgement evidence; closed/cancelled alarms cannot be acknowledged. |
| Alarm Shelving | `AlarmShelving`: alarm shelving lifecycle evidence. |
| Alarm Suppression | Governed suppression by alarm, alarm type, topology asset, monitoring rule or source. Open-ended suppression requires workflow evidence and overlapping ACTIVE suppression is rejected. |
| Incident | `Incident`: governed incident record with classification/severity, source, topology/location references, responsible ownership and lifecycle state. |
| Incident Response Action | `IncidentResponseAction`: response work associated with a non-draft, non-closed incident. |
| Leak Candidate | `LeakCandidate`: suspected leak event tied to topology provenance, confidence, severity and candidate lifecycle. |
| Leak Detection Case | `LeakDetectionCase`: controlled case grouping a primary leak candidate and topology asset under a case lifecycle. |
| Maintainable Asset | `MaintainableAsset`: maintenance-owned asset record linked to physical topology by neutral reference; it is not the owner of physical topology identity. |
| Maintenance Work Order | `MaintenanceWorkOrder`: assets-owned maintenance execution record for a maintainable asset. |
| Integrity Program | `IntegrityProgram`: integrity-owned program context. |
| Integrity Assessment | `IntegrityAssessment`: assessment over an integrity/topology scope with methodology, review/approval and lifecycle evidence. |
| Pipeline Defect | `PipelineDefect`: integrity-owned defect evidence for pipeline condition/integrity semantics. |
| Integrity Case | `IntegrityCase`: lifecycle case tied to topology and optionally incident/HSE/defect references. |
| Simulation Scenario | `SimulationScenario`: governed scenario definition/input scope for simulation. |
| Simulation Run | `SimulationRun`: execution record for a simulation scenario/model with terminal run outcomes. |
| Simulation Recommendation | `SimulationRecommendation`: human-facing recommendation derived from a run/candidate; it is not field actuation. |
| Analytics Dataset | `AnalyticsDataset`: curated analytical dataset metadata with lineage and quality state. |
| Analytics Projection Run | `AnalyticsProjectionRun`: analytical projection execution retaining definition/version, run status and source watermark for successful outcomes. |
| Analytics Insight | `AnalyticsInsight`: derived analytical observation; current domain explicitly marks insights advisory-only. |
| Analytics Model | Current persistence vocabulary for model metadata/version/run records inside analytics. This is not by itself proof of an AI inference runtime. |
| Digital Twin Readiness Assessment | `DigitalTwinReadinessAssessment`: assessment of topology/data/model readiness; current domain explicitly reports that it is not a runtime digital twin. |
| Operational Source of Truth | State owned by the business module responsible for the operational fact; analytics and simulation are prohibited from mutating foreign operational truth. |
| Snapshot / Reference | Stable copied identity/label/provenance used to preserve context without importing another module's aggregate. |
| Advisory | Derived output intended to inform a human or downstream governed workflow rather than directly mutate operational source-of-truth state. |

## Prohibited Semantic Shortcuts

- "AI" must not be used as a synonym for the current analytics module.
- "Digital twin" must not be used to claim an executing runtime twin merely because readiness assessment data exists.
- "Simulation recommendation" must not be described as automatic control.
- "Maintainable asset" must not replace topology-owned physical identity.
- "Raw telemetry reading" must not automatically be described as trusted.
- References/snapshots must not be described as cross-module aggregate ownership.

## Governed reference and evidence vocabulary

| Term | Canonical meaning and decision |
|---|---|
| Operational Scope Registry ID | Organization's independent positive registry identity; distinct from the referenced owner ID. See [OperationalScope](SEMANTIC_DECISIONS.md#organization-operationalscope). |
| Identity Role / Permission | Hidra security authority, distinct from Party business role or Organization position. See [Identity decisions](SEMANTIC_DECISIONS.md#identity-decisions). |
| Planning Target | Planning-owned expected value under exact TARGET_TYPE and approved NUMERIC/TEXT policy, with same-revision context. See [PlanTarget](SEMANTIC_DECISIONS.md#planning-plantarget). |
| Nomination | Planning movement quantity with explicitly approved Custody product and Telemetry quantity/rate roles and compatibility facts. See [Nomination](SEMANTIC_DECISIONS.md#planning-nomination). |
| Plan-Actual Deviation | Monitoring-owned comparison using Planning target and optional coherent Telemetry/evaluation evidence. See [PlanActualDeviation](SEMANTIC_DECISIONS.md#monitoring-planactualdeviation). |
| Active Fresh Reference | Subject-specific current eligibility required for new/changed identity; not a blanket rule for every optional owner reference. |
| Historical Snapshot | Preserved context from the original owner resolution; unchanged evidence is not silently refreshed after retirement or rebinding. |
| Approved Mapping | Explicit owner metadata keyed by actual registered identity; code similarity is not approval. |
| Append-only Evidence | Inserted immutable evidence on the guarded path; replay/failure cannot become a merge overwrite. See [Audit](SEMANTIC_DECISIONS.md#audit-decisions) and [Workflow](SEMANTIC_DECISIONS.md#workflow-decisions). |
| Owner-confirmed Approval | Actual decision/context confirmed by the owner, not mere presence of a Workflow ID. |
| Storage Rollback Cleanup | Cleanup of only the newly created blob after failure/confirmed rollback; unknown commit outcome preserves possibly referenced content for reconciliation. See [Documents](../modules/documents.md#permanent-semantic-decisions). |

Every precise optionality, lifecycle and provenance exception remains in
[the subject register](SEMANTIC_DECISIONS.md); vocabulary does not impose a stronger
general policy. Required/optional localized text remains subject-specific rather than
an invented universal translation requirement.
