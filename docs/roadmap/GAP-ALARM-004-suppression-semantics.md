# GAP-ALARM-004 — Alarm suppression semantics

Status: DOMAIN DECISION REQUIRED

Baseline: `6e3f3b2829bb63f0d004c8bc9ba386d05eb3edcc`

## Verified repository facts

Alarm suppression is a distinct Alarm Management capability. It is not a synonym for shelving.

The target data definition distinguishes the two concepts:

- shelving is temporary, time-bounded operator-approved reduction of alarm visibility/noise for one alarm;
- suppression prevents alarm generation or visibility under controlled conditions and may be manual, planned-maintenance related, rule-based, or integration-driven.

The production model already contains:

- `AlarmSuppression`;
- `AlarmSuppressionScopeType` with `ALARM`, `ALARM_TYPE`, `TOPOLOGY_ASSET`, `MONITORING_RULE`, and `SOURCE`;
- `AlarmSuppressionStatus` with `ACTIVE`, `EXPIRED`, `RELEASED`, and `CANCELLED`;
- `AlarmState.SUPPRESSED`;
- lifecycle event types `SUPPRESSED` and `UNSUPPRESSED`;
- JPA persistence for `hidra_alarm_suppression`;
- `AlarmSuppressionRepositoryPort` and its JPA adapter.

The suppression record already models:

- suppression scope and reference;
- optional alarm/type/topology references;
- mandatory suppression reason;
- free-text reason;
- server/audit actor reference;
- start time;
- optional end time;
- release time and releasing actor;
- status;
- optional workflow approval reference;
- correlation id.

The data definition additionally requires:

- suppression must always have a reason;
- open-ended suppression must be exceptional and approval-controlled;
- suppression does not delete existing alarms;
- suppression must remain visible in audit and operations review;
- every alarm state change must emit exactly one append-only `AlarmLifecycleEvent`.

## What is not defined sufficiently to implement safely

The repository does not define the following business rules strongly enough to publish a mutation contract without inventing lifecycle semantics:

1. **Release state for ALARM-scoped suppression.**
   `AlarmState` contains `SUPPRESSED`, and lifecycle events contain both `previousState` and `newState`, but no rule states what state an existing alarm must enter after `UNSUPPRESSED`.

2. **Interaction with acknowledgement.**
   It is not defined whether an alarm may be acknowledged while suppressed or whether acknowledgement must occur before/after release.

3. **Interaction with clear/close.**
   It is not defined whether a suppressed alarm may clear naturally, whether it may be administratively closed while suppression is active, or whether suppression must first be released.

4. **Interaction with escalation.**
   It is not defined whether suppression blocks escalation or incident escalation, or only presentation/generation.

5. **State versus overlay semantics for broad scopes.**
   `ALARM_TYPE`, `TOPOLOGY_ASSET`, `MONITORING_RULE`, and `SOURCE` suppression can apply before an alarm exists. Those scopes cannot be represented solely as an `Alarm.currentState` transition and therefore require explicit overlay/matching semantics in the alarm-generation boundary.

6. **Automatic expiry behavior.**
   `suppressedUntil` and `EXPIRED` exist, but no authoritative scheduler/application rule defines expiry execution, release actor semantics, resulting alarm state, or lifecycle event emission.

7. **Open-ended approval rule.**
   The data definition says open-ended suppression must be approval-controlled and `workflowInstanceId` exists, but the required workflow state/approval evidence is not specified.

## Decision

`GAP-ALARM-004` is therefore **not** solved by mapping shelving endpoints to suppression or by exposing CRUD over `hidra_alarm_suppression`.

Suppression is confirmed as a distinct domain capability, but the mutation contract remains blocked until the lifecycle rules above are explicitly decided.

No REST suppression mutation should be published from this branch until those decisions are made. In particular, HidraAPI must not guess the post-release alarm state or silently treat broad-scope suppression as an alarm-state update.

## Required completion decision

Before implementation, define at minimum:

- whether `ALARM`-scoped suppression changes `Alarm.currentState` to `SUPPRESSED`;
- exact `UNSUPPRESSED` resulting-state rule;
- acknowledge/clear/close/escalate rules while suppression is active;
- matching/evaluation behavior for non-ALARM scopes at alarm-generation/query boundaries;
- automatic expiry behavior and lifecycle event attribution;
- workflow approval condition for open-ended suppression;
- authorization permission code(s);
- deterministic conflict/error behavior;
- query visibility semantics for active, historical, and suppressed alarms.

Once these rules are approved, implementation should add explicit suppression/release/query use cases, server-derived actor attribution, lifecycle events, tests, and deterministic OpenAPI publication.
