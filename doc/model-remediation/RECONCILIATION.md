# HidraAPI Semantic Remediation Reconciliation

## Status

CURRENT — HPR-P2-007 exact-source reconciliation.

## Verification Baseline

Repository: `CHOUABBIA-AMINE/HidraAPI`

Exact baseline: `aae829778d610b2912869844babfdcef82068138`

Legacy source register: `docs/roadmap/model-semantic-remediation.md`

The legacy register is preserved as execution history. This canonical reconciliation controls P2 execution disposition together with `doc/roadmap/ULTIMATE_ROADMAP.md`.

## Reconciliation Rules

HPR-P2-007 does not execute semantic remediation. It revalidates whether legacy HMR/HMSR obligations still require execution.

A legacy item is marked:

- **COMPLETED** only when current source/migration/test evidence demonstrates the registered obligation has already been implemented;
- **STILL REQUIRED** when no current evidence proves completion and the underlying source subject still exists;
- **BLOCKED** when a required owner-controlled contract/decision is still absent;
- **SUPERSEDED** only when current architecture/source makes the recorded obligation inapplicable. No HMR-050..106 item met that bar at this baseline.

Absence of the originally registered migration alone is not enough to prove an obligation is unresolved when no migration was required. For that reason the reconciliation also checks the registered dedicated semantic test and exact current architecture/contracts.

## Corrected Legacy Dispositions

### HMR-005 — telemetry.TelemetryPoint

**COMPLETED — legacy status is stale.**

Current evidence includes:

- `src/main/resources/db/migration/V20261004_005__hmr_005_telemetry_telemetry_point.sql`;
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryPointSemanticRemediationTest.java`;
- `TelemetryPoint` rejects blank French names;
- application uniqueness pre-check exists for device + code;
- database unique index exists for `(device_id, code)`;
- persistence adapter validates active catalog families;
- optional unit reference resolves to an active TelemetryUnit;
- SIGNAL_TYPE `valueShape` and POINT_TYPE `numericUnitExempt` drive numeric/text/boolean and unit compatibility.

Therefore HMR-005 must not be re-executed absent concrete regression evidence.

### HMR-009 — simulation.SimulationModel

**COMPLETED — legacy carry-over blocker note is stale.**

The HMR-009 record itself is already `Completed`, with registered migration/test/application/Topology-contract evidence. The later legacy sentence saying “HMR-009 remains unresolved” contradicts the record and is not execution authority.

HMR-009 must not be reopened absent concrete regression evidence.

### HMR-054 — topology.Equipment

**STILL REQUIRED — historical owner-contract blocker is resolved.**

The exact current tree contains:

`src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyReferenceContract.java`

That Party-owned contract is deliberately exported to Topology and is already architecture-allowlisted. The historical “no suitable exported owner lookup” prerequisite is therefore no longer a blocker.

The HMR-054 semantic implementation itself is still not evidenced by its registered migration/test, so execution remains required under HPR-P2-008 after normal preflight.

### HMR-080 — planning.Nomination

**BLOCKED — Party→Planning owner contract remains absent.**

No `party.application.contract.planning` package exists in the exact current tree. HPR-P2-008 must not invent a direct Party-domain/repository dependency or cross-module FK. The owner contract must be explicitly introduced/authorized before HMR-080 can execute.

## Reconciled Outstanding Register

| HMR | HMSR | Subject | Canonical disposition | Exact-current evidence |
|---|---|---|---|---|
| HMR-050 | HMSR-059 | integrity.IntegrityProgram | COMPLETED — HPR-P2-008 | legacy migration not required after current-schema revalidation; dedicated semantic test added; active `INTEGRITY_PROGRAM_TYPE` family enforced; Organization-owned Integrity contract validates populated owner unit; no cross-module FK |
| HMR-051 | HMSR-060 | leakdetection.LeakDetectionCase | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-052 | HMSR-061 | notification.NotificationMessage | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-053 | HMSR-062 | telemetry.TrustedTelemetryReading | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-054 | HMSR-063 | topology.Equipment | STILL REQUIRED — PREVIOUS BLOCKER RESOLVED | registered migration: absent; dedicated test: absent; Party→Topology contract now present |
| HMR-055 | HMSR-064 | workflow.WorkflowInstance | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-056 | HMSR-067 | integration.IntegrationExchangeMessage | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-057 | HMSR-068 | reporting.ReportRun | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-058 | HMSR-069 | risk.RiskAssessment | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-059 | HMSR-071 | leakdetection.LeakEscalationReference | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-060 | HMSR-072 | notification.NotificationDeliveryAttempt | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-061 | HMSR-073 | workflow.WorkflowTransition | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-062 | HMSR-074 | incident.Incident | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-063 | HMSR-075 | identity.User | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-064 | HMSR-076 | planning.PlanRevision | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-065 | HMSR-077 | planning.OperationalPlan | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-066 | HMSR-078 | workflow.WorkflowTask | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-067 | HMSR-079 | documents.Document | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-068 | HMSR-080 | documents.DocumentVersion | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-069 | HMSR-081 | assets.MaintenanceWorkOrder | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-070 | HMSR-082 | custody.CustodyTransferTicket | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-071 | HMSR-084 | integration.IntegrationDeadLetterRecord | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-072 | HMSR-085 | integrity.IntegrityAssessment | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-073 | HMSR-087 | organization.EmployeeAssignment | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-074 | HMSR-088 | organization.OrganizationDelegation | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-075 | HMSR-089 | organization.OrganizationHierarchySnapshot | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-076 | HMSR-090 | organization.ShiftAssignment | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-077 | HMSR-091 | risk.RiskEvidenceLink | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-078 | HMSR-092 | simulation.SimulationCandidateChange | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-079 | HMSR-093 | simulation.SimulationRecommendation | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-080 | HMSR-094 | planning.Nomination | BLOCKED — OWNER CONTRACT REQUIRED | registered migration: absent; dedicated test: absent; Party→Planning contract absent |
| HMR-081 | HMSR-095 | workflow.WorkflowAction | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-082 | HMSR-096 | hse.HseCase | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-083 | HMSR-097 | audit.AuditExportRequest | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-084 | HMSR-098 | documents.DocumentTargetLink | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-085 | HMSR-100 | identity.AuthorizationDecision | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-086 | HMSR-101 | identity.AuthorizationDelegationGrant | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-087 | HMSR-104 | identity.LoginSession | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-088 | HMSR-105 | identity.UserPermissionGrant | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-089 | HMSR-106 | identity.UserRoleGrant | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-090 | HMSR-107 | incident.IncidentClosure | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-091 | HMSR-108 | incident.IncidentRelatedIncident | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-092 | HMSR-109 | incident.IncidentResponseAction | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-093 | HMSR-110 | reporting.ReportOutputArtifact | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-094 | HMSR-111 | planning.PlanTarget | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-095 | HMSR-112 | audit.AuditEvent | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-096 | HMSR-113 | hse.HseClosure | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-097 | HMSR-114 | hse.HseCorrectivePreventiveAction | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-098 | HMSR-115 | integrity.IntegrityCase | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-099 | HMSR-116 | workflow.WorkflowStateHistory | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-100 | HMSR-117 | alarm.Alarm | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-101 | HMSR-118 | audit.AuditAccessRecord | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-102 | HMSR-119 | audit.AuditBeforeAfterValue | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-103 | HMSR-120 | monitoring.PlanActualDeviation | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-104 | HMSR-121 | alarm.AlarmAcknowledgement | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-105 | HMSR-122 | alarm.AlarmClosure | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-106 | HMSR-123 | alarm.AlarmShelving | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |

## Reconciliation Totals

- legacy HMR records parsed: **104**;
- HMR-005 corrected from stale planned status to **COMPLETED**;
- HMR-009 confirmed **COMPLETED** and removed as a carry-over blocker;
- HMR-050..106 evaluated: **57**;
- HMR-050..106 **STILL REQUIRED**: **55**;
- HMR-050..106 **BLOCKED**: **1**;
- HMR-050..106 **COMPLETED during HPR-P2-008**: **1**;
- HMR-050..106 **SUPERSEDED**: **0**;
- HMR-054 historical blocker resolved but remediation still required;
- HMR-080 remains blocked.

## HPR-P2-008 Progress

- HMR-050 — **COMPLETED** at the first HPR-P2-008 execution step.
- Current next dependency-safe item: **HMR-051 — leakdetection.LeakDetectionCase**.
- Remaining after HMR-050: **55 STILL REQUIRED + 1 BLOCKED (HMR-080)**.

## HPR-P2-008 Execution Rule

HPR-P2-008 may execute only the `STILL REQUIRED` items, in dependency-safe order, with a fresh preflight before each mutation or registered batch.

Before each HMR:

1. re-read its HMSR obligations;
2. verify exact current source and architecture contracts;
3. verify the registered write scope still fits;
4. decide whether a migration is actually required from current schema;
5. verify SCC/dependency ordering;
6. verify owner-controlled contracts exist;
7. preserve one HMR semantic test/commit boundary;
8. do not restart HMR-005, HMR-009 or any other completed HMR without concrete regression evidence.

HMR-080 remains excluded until its owner-contract prerequisite is resolved.

## Legacy Preservation

`docs/roadmap/model-semantic-remediation.md` is intentionally not rewritten by HPR-P2-007. Its historical statuses, batches and execution notes remain provenance. Where it conflicts with this exact-source reconciliation, this canonical document and the Ultimate Roadmap govern P2 execution.
