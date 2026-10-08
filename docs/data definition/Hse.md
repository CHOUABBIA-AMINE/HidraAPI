# HIDRA HSE Module — Data Definition Document

```text
Document code : HIDRA-HSE-DDD
Repository    : HidraAPI
Module        : hse
Package root  : dz.sh.hidra.modules.hse
Table prefix  : hidra_hse_*
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC : Digitalization Initiative
Author        : Abir MEDJERAB
UpdatedOn     : 2026-06-11
Status        : Active DDD reference
Version       : 1.1
```

---

## 1. Purpose

The `hse` module owns health, safety, environment, and compliance consequences related to operations, hazards, near misses, observations, environmental events, permits, inspections, drills, obligations, assessments, and corrective/preventive actions.

HSE records consequence and compliance truth. It does not own the incident lifecycle, leak detection lifecycle, alarm lifecycle, topology, telemetry, assets, workflow, audit, notification delivery, or SCADA/OT actuation.

---

## 2. Canonical implementation identity

```text
Module name   : hse
Package root  : dz.sh.hidra.modules.hse
Table prefix  : hidra_hse_*
```

Forbidden table prefixes:

```text
hidra_incident_*
hidra_alarm_*
hidra_leak_detection_*
hidra_integrity_*
hidra_asset_*
hidra_topology_*
```

---

## 3. Ownership

HSE owns:

```text
HseCase
HseCaseStatusHistory
HseCaseEvidenceLink
HseImpactAssessment
HseCorrectivePreventiveAction
HseClosure
HazardReport
NearMissReport
SafetyObservation
EnvironmentalEvent
PermitToWork
HseInspection
EmergencyDrill
ComplianceObligation
ComplianceAssessment
HseCatalogEntry
HseCatalogTranslation
```

HSE does not own:

```text
Incident
Alarm
LeakCandidate
LeakDetectionCase
PipelineDefect
IntegrityCase
Facility
Pipeline
Equipment
MaintainableAsset
MaintenanceWorkOrder
User
Role
Permission
OrganizationUnit
Employee
AuditRecord
NotificationMessage
SCADA command
```

---

## 4. Incident vs HSE rule

```text
Incident Management says: what operational problem happened and how was it handled?
HSE says: what health, safety, environmental, and compliance consequences exist?
```

Do not merge incident records, leak cases, alarms, HSE cases, and integrity cases into one generic case table.

Incident management is blocked until its own DDD exists. HSE may store only neutral incident references:

```text
incidentReferenceId
incidentCodeSnapshot
incidentTitleSnapshot
```

---

## 5. Entity catalogue

| Entity | Table | Purpose |
|---|---|---|
| HseCase | `hidra_hse_case` | HSE case lifecycle. |
| HseCaseStatusHistory | `hidra_hse_case_status_history` | Append-only status history. |
| HseCaseEvidenceLink | `hidra_hse_case_evidence_link` | Evidence link. |
| HseImpactAssessment | `hidra_hse_impact_assessment` | Health/safety/environment impact assessment. |
| HseCorrectivePreventiveAction | `hidra_hse_capa` | Corrective/preventive action. |
| HseClosure | `hidra_hse_closure` | Closure record. |
| HazardReport | `hidra_hse_hazard_report` | Hazard report. |
| NearMissReport | `hidra_hse_near_miss_report` | Near-miss report. |
| SafetyObservation | `hidra_hse_safety_observation` | Safety observation. |
| EnvironmentalEvent | `hidra_hse_environmental_event` | Environmental event. |
| PermitToWork | `hidra_hse_permit_to_work` | Permit to work. |
| HseInspection | `hidra_hse_inspection` | HSE inspection. |
| EmergencyDrill | `hidra_hse_emergency_drill` | Emergency drill. |
| ComplianceObligation | `hidra_hse_compliance_obligation` | Compliance obligation. |
| ComplianceAssessment | `hidra_hse_compliance_assessment` | Compliance assessment. |
| HseCatalogEntry | `hidra_hse_catalog_entry` | HSE-owned catalog. |
| HseCatalogTranslation | `hidra_hse_catalog_translation` | Multilingual catalog labels. |

---

## 6. Cross-module references

HSE may reference external targets by stable IDs and snapshots:

```text
targetModule
targetTypeCode
targetId
targetCodeSnapshot
targetLabelSnapshot
```

It must not import incident, leakdetection, topology, assets, integrity, organization, identity, workflow, audit, or notification domain models.

---

## 7. Documentation and annotation rule

Domain, application, and infrastructure HSE models must not use `@Schema` or OpenAPI annotations.

`@Schema` is allowed only in HSE API request/response models.


### HMR-082 executable contract reconciliation

HMSR-096 recovered independently from the live source review. Application closure loads
and pessimistically locks the owning case before calling the existing guard. The new
REQUIRED lifecycle coordinator revalidates under that lock, binds the authenticated
eligible Identity actor, validates optional exact HSE Workflow context, and persists
closure, CLOSED/closedAt and old-status -> CLOSED history with one server microsecond
timestamp. No RESOLVED-only or regulatoryReviewed requirement is invented. Boolean
attestations remain the existing guard inputs, not proof of independently stored findings.

Generic parent saves cannot establish CLOSED or change a recorded closed tuple. New or
changed reporter/unit/Workflow references use narrow owner contracts; unchanged historical
snapshots remain readable without owner refresh. Neutral Incident/Audit/target references
and existing case-type/severity FKs remain unchanged; exact case catalog families are still
unresolved. Forward 014 creates unseeded field-family metadata and enforces deferred
case/closure/history coherence, closed tuple immutability and append-only status history.
Legacy incoherence aborts migration without data repair. No cross-module FK is introduced.

Validation: production HSE/owner sources and focused test signatures compiled on Java 17
against temporary dependency APIs. Fourteen actual domain/application checks passed with
controlled ports. Focused owner/domain and PostgreSQL tests are prepared, not executed
locally. Maven compilation stops before execution at uncached Spring Boot parent 4.1.1
in offline mode; Java 21/Docker/PostgreSQL are unavailable locally. Real runtime/full
verification remains final-head CI responsibility. Scope, preserved SQL and Markdown
checks are required before publication. HMR-082 implementation Completed pending CI;
HMR-096 and HMR-097 now admitted/in progress; HMR-080 remains blocked.


### HMR-096 executable contract reconciliation

HMSR-113 independently recovered. Every closure repository save now delegates to the
HSE lifecycle coordinator; it never merges evidence. New closure/history records use
EntityManager.persist and flush inside the REQUIRED transaction, using own JPA repositories
and no circular repository-port dependency. Forward 015 serializes closure insertion on
the parent, enforces the existing attestation guard, checks actual old status in closure
history, and denies closure overwrite/delete/truncate and case truncate. No new uniqueness,
RESOLVED-only, regulatory flag or summary domain rule is introduced.

Four focused closure unit methods and four additional real PostgreSQL/Spring-JPA methods
are prepared, including rollback after all three flushed writes, exact shared time/actor/
correlation, concurrent one-winner closure, replay rejection and immutable evidence. These
have not run locally. Changed production and focused test signatures compiled against
temporary APIs. Twelve actual coordinator/delegation checks passed with controlled owned
repositories; this does not demonstrate database rollback or lock behavior. Focused Maven
execution stops before tests at uncached Boot 4.1.1 offline parent resolution. Real runtime
verification remains final-head CI. HMR-096 implementation Completed pending CI; next
admitted task HMR-097. HPR-P2-008 remains open and HMR-080 independently blocked.
