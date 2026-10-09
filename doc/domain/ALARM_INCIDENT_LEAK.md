# Alarm, Incident and Leak-Detection Semantics

## Status

CURRENT focused semantic baseline.

## Alarm

`Alarm` is a formal operational alarm instance carrying source provenance, severity/priority references, topology references, lifecycle state and optional workflow/incident linkage.

Current lifecycle rules include:

- closed or cancelled alarms cannot be acknowledged;
- normal close requires CLEARED state or durable clearedAt evidence; explicit cancellation may close without prior clear. ESCALATED alone is insufficient, including when visibility is suppressed;
- suppression may target an alarm, alarm type, topology asset, monitoring rule or source;
- open-ended suppression requires workflow approval evidence;
- overlapping ACTIVE suppression for the same scope/reference is rejected;
- only ACTIVE suppression may be released;
- expired suppression restores state from durable alarm evidence (cancelled/closed/cleared/acknowledged/escalated/active).

Alarm links to telemetry, monitoring, planning, topology, workflow and incident are reference relationships; they do not transfer ownership of those domains.

## Incident

`Incident` is a governed operational incident record with classification/severity, source provenance, topology/location references, responsible organization/actor references, workflow identity and lifecycle timestamps.

Current lifecycle rules include:

- DRAFT incidents cannot receive response actions;
- CLOSED, CANCELLED or MERGED incidents are closed lifecycle;
- only RESOLVED incidents may close;
- closure additionally requires resolution and reviewed evidence.

Incident therefore represents governed response/lifecycle evidence rather than a synonym for an alarm.

## Leak Detection

`LeakCandidate` is a suspected leak event with profile/run provenance, topology asset identity, suspected/evidence timestamps, confidence score, derived severity and candidate status.

`LeakDetectionCase` groups a primary candidate into a controlled case tied to a topology asset and case lifecycle. A case is open until CLOSED or DISMISSED; closing timestamps may not precede opening.

Current `LeakConfidenceClassifier` maps confidence score as follows:

| Confidence score | Current severity classification |
|---:|---|
| null | UNKNOWN |
| >= 0.90 | CRITICAL |
| >= 0.75 and < 0.90 | HIGH |
| >= 0.50 and < 0.75 | MEDIUM |
| < 0.50 | LOW |

These thresholds document current code behavior, not an industry-standard safety threshold.

## Relationship Semantics

Alarm, incident and leak detection remain distinct bounded contexts:

- leak detection produces candidate/case semantics;
- alarm owns alarm lifecycle/acknowledgement/suppression;
- incident owns governed incident response and closure;
- references among them provide traceability but do not imply automatic promotion/conversion unless an application flow explicitly implements it.

## Not Established

No autonomous safety shutdown, valve/pump control, SCADA/PLC/RTU/SIS/ESD actuation or unverified automatic incident creation is claimed by this document.

## Durable creation and lifecycle evidence

[AlarmLifecycleGuard](../../src/main/java/dz/sh/hidra/modules/alarm/domain/service/AlarmLifecycleGuard.java)
defines acknowledgement/close eligibility. The repository lifecycle paths append
one raised/action/finish event with the parent-state mutation in the same transaction;
these are formal Alarm records rather than caller-manufactured lifecycle history.
[Alarm decisions](SEMANTIC_DECISIONS.md#alarm-decisions) cover direct saves, live
paths, immutability and duplicate/race behavior. Multiple acknowledgements remain
legal historical evidence; closure produces one immutable terminal decision.
Optional review metadata does not introduce mandatory Workflow approval for every
Alarm close/cancellation.

Shelving has strict start/end ordering, exact reason family and one ACTIVE shelf.
Expiry records contractual due time and restores durable prior alarm evidence,
without reopening closed/cancelled/cleared state or moving snapshots backward.
The [shelving expiry orchestrator](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmShelvingExpiryOrchestrator.java)
uses the transactional record boundary and isolates failed records. Suppression and
shelving synchronize under the same alarm parent lock, including release/cancellation
and manual/expiry races. Broad suppression expiry records its scope audit without
pretending it is an individual alarm mutation.

Incident closure is separately governed: a RESOLVED incident, real resolution,
policy-required persisted evidence and any required owner-confirmed Workflow approval
precede atomic parent/closure persistence. Confirmations alone cannot fabricate that
evidence. See [Incident decisions](SEMANTIC_DECISIONS.md#incident-decisions) and
[IncidentLifecycleGuard](../../src/main/java/dz/sh/hidra/modules/incident/domain/service/IncidentLifecycleGuard.java).
Response actions serialize against closure and remain work evidence, not field commands.

Leak candidate profile/run consistency, topology owner snapshots and optional case
ownership are governed by [LeakDetection decisions](SEMANTIC_DECISIONS.md#leakdetection-decisions).
The confidence thresholds above come directly from
[LeakConfidenceClassifier](../../src/main/java/dz/sh/hidra/modules/leakdetection/domain/service/LeakConfidenceClassifier.java),
not an approved external engineering safety standard.
