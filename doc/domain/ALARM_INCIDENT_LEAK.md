# Alarm, Incident and Leak-Detection Semantics

## Status

CURRENT focused semantic baseline.

## Alarm

`Alarm` is a formal operational alarm instance carrying source provenance, severity/priority references, topology references, lifecycle state and optional workflow/incident linkage.

Current lifecycle rules include:

- closed or cancelled alarms cannot be acknowledged;
- normal close requires CLEARED or ESCALATED state, with a special allowed suppressed state when the underlying alarm has cleared; explicit cancellation follows its own close path;
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
