# GAP-ALARM-004 — Alarm suppression semantics

Status: **DOMAIN DECISION APPROVED — implementation authorized through the controlled task sequence below**

Decision baseline: `260295c6eebc4b01922d2d488810a671305860a6`

## 1. Verified repository facts

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
- JPA persistence for suppression exists, but no dedicated application repository port is present at the ALM-SUP-002 baseline.

The suppression persistence record already models:

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

## 2. Approved suppression policy

The project owner approved the following policy on 2026-10-02.

### 2.1 Core semantic rule

Suppression is a **reversible operational overlay** and remains distinct from shelving.

Suppression never deletes alarm evidence. Existing alarms remain auditable. Suppression controls alarm generation/visibility/noise but does not erase the underlying business lifecycle.

### 2.2 ALARM-scoped suppression

For scope `ALARM`:

- an existing alarm may expose `Alarm.currentState = SUPPRESSED` while suppression is active;
- the underlying lifecycle state must be preserved explicitly enough to restore the correct state after suppression ends;
- release/expiry must not blindly restore `ACTIVE`;
- the resulting state is derived from authoritative alarm lifecycle evidence:
  - still active and not acknowledged -> the normal active/unacknowledged state;
  - acknowledged before or during suppression -> acknowledged state;
  - underlying condition cleared while suppressed -> cleared state;
  - closed alarm remains closed.

Implementation must not infer this from UI state. The Alarm application/domain layer owns the restoration decision.

### 2.3 Acknowledge while suppressed

Acknowledgement is allowed while an existing alarm is suppressed when normal authorization and alarm lifecycle rules permit it.

Acknowledgement remains fully audited and affects the authoritative underlying lifecycle state that is restored after suppression ends.

Suppression must not be used as a substitute for acknowledgement.

### 2.4 Clear and close while suppressed

- natural clear is allowed while suppression is active;
- administrative close is allowed when normal Alarm closure rules and permissions permit it;
- suppression does not have to be manually released before clear/close;
- closing an ALARM-scoped alarm makes that suppression non-operative and the suppression record remains historical evidence;
- no suppression record is deleted as a side effect.

### 2.5 Escalation while suppressed

Suppression does not automatically erase or block safety/business escalation semantics.

For an already-existing alarm:

- escalation remains available when the normal backend rules permit it;
- suppression alone must not silently prevent incident/workflow escalation;
- any future policy that blocks escalation for a specific suppression reason must be modeled explicitly rather than inferred from suppression status.

### 2.6 Broad-scope suppression matching

For `ALARM_TYPE`, `TOPOLOGY_ASSET`, `MONITORING_RULE`, and `SOURCE`:

- suppression is evaluated as an overlay at the alarm-generation/evaluation boundary;
- matching must be deterministic and owned by the backend;
- broad-scope suppression is not represented by changing every existing alarm's state;
- when an incoming alarm candidate matches an ACTIVE suppression, HidraAPI **retains alarm/evidence as suppressed evidence rather than silently discarding it**;
- the matching suppression reference must remain traceable from the resulting evidence;
- broad-scope suppression stops affecting future candidates when it expires, is released, or is cancelled.

No frontend or integration adapter may reimplement the matching rules independently.

### 2.7 Automatic expiry

When `suppressedUntil` is reached:

- the backend application/scheduling boundary changes `ACTIVE -> EXPIRED`;
- expiry is attributed to a server/system actor, never a fabricated user;
- one append-only suppression/lifecycle event is emitted for the state change;
- ALARM-scoped suppression restores the authoritative underlying alarm lifecycle state;
- broad-scope suppression simply ceases to match future alarm candidates;
- expiry must be idempotent and safe to retry.

### 2.8 Open-ended approval

- time-bounded suppression may be created directly by an actor with the required suppression permission and a valid suppression reason;
- open-ended suppression (`suppressedUntil == null`) requires approved Workflow evidence;
- the referenced workflow must be in the backend-defined approved/completed state before activation;
- HidraAPI must fail closed if the workflow approval cannot be verified;
- the browser must never infer approval from a workflow label or transition name.

### 2.9 Authorization

Suppression uses permissions distinct from shelving.

Canonical permission intent:

```text
alarm:suppression:read
alarm:suppression:create
alarm:suppression:release
```

The implementation task must reconcile these names with the platform's existing canonical route-permission derivation before seeding/publishing them. Backend authorization remains authoritative.

### 2.10 Conflict behavior

Creating an overlapping ACTIVE suppression for the same exact scope/reference is rejected with deterministic `409 Conflict` unless a later explicitly approved policy introduces merge/overlap semantics.

Release/expiry operations must also reject stale/already-terminal transitions deterministically and idempotently where appropriate.

### 2.11 Query visibility

- audit/history queries must always be able to retrieve suppression evidence;
- active-alarm queries must expose an explicit backend-owned way to include/exclude suppressed evidence where the API contract requires filtering;
- suppressed alarms must never disappear from historical evidence;
- normal reads must expose enough status/scope/reason/time evidence for operators without leaking unrelated internal implementation state.

## 3. Explicit non-decisions / boundaries

This approval does **not** authorize:

- treating shelving as suppression;
- deleting or rewriting historical alarm evidence;
- client-side suppression matching;
- client-side restoration-state calculation;
- frontend-generated actor attribution;
- automatic blocking of escalation;
- silent dropping of broad-scope matched alarm candidates;
- SCADA/PLC actuation;
- changes to the parked industrial-extension roadmap.

## 4. Controlled implementation sequence

Each code below is one independent roadmap task/commit. Execute exactly one code per task and stop after its CI result is checked once.

| Code | Exact commit message | Scope | Exit criteria | Status |
|---|---|---|---|---|
| `ALM-SUP-001` | `docs(alarm): approve suppression lifecycle semantics` | This document only. Record the approved suppression policy and implementation sequence. | Domain decision blocker removed without production-code change. | **Completed** |
| `ALM-SUP-002` | `feat(alarm): add suppression application contracts` | Add minimal Alarm application commands/queries/use-case ports and DTOs for create, release, expiry evaluation, and query behavior. Reuse existing domain/persistence concepts. | Application contracts encode the approved policy without REST/JPA leakage or duplicate models. | **Completed** |
| `ALM-SUP-003` | `feat(alarm): implement suppression lifecycle policy` | Implement domain/application policy for ALARM overlay/restoration, broad-scope matching, acknowledgement/clear/close/escalation interaction, conflicts, and server-derived actor handling. | Approved lifecycle rules are deterministic and unit-tested. | **Next** |
| `ALM-SUP-004` | `feat(alarm): expose suppression persistence queries` | Extend existing suppression repository adapter only as required by approved use cases: exact-scope active lookup, expiry candidates, history/query support. | No duplicate persistence model; overlap and expiry queries are deterministic. | Planned |
| `ALM-SUP-005` | `feat(alarm): integrate suppression workflow approval` | Verify open-ended suppression through Workflow public contracts/ports and fail closed when approval is absent. | No transition-name inference or direct foreign aggregate dependency. | Planned |
| `ALM-SUP-006` | `feat(alarm): add suppression expiry orchestration` | Add idempotent backend-owned expiry orchestration using the approved platform scheduling mechanism already present in the repository. | ACTIVE suppressions expire once; events/actor attribution/restoration are deterministic. | Planned |
| `ALM-SUP-007` | `feat(alarm): publish suppression REST contracts` | Publish canonical create/release/query endpoints, generated OpenAPI schemas, route-permission metadata, deterministic 400/403/404/409 behavior. | No CRUD-style leakage; only approved lifecycle operations exposed. | Planned |
| `ALM-SUP-008` | `test(alarm): verify suppression lifecycle end to end` | Domain/application/persistence/REST/Testcontainers coverage including ALARM and broad scopes, acknowledgement, clear/close, escalation preservation, workflow approval, expiry, conflicts, audit evidence, OpenAPI determinism. | Full repository verification green and suppression behavior traceable. | Planned |
| `ALM-SUP-009` | `docs(alarm): finalize suppression capability` | Record exact endpoints, permissions, events, validation evidence, remaining limits, and frontend-consumption boundary. | GAP-ALARM-004 closed with no undocumented semantics. | Planned |

## 5. Validation for ALM-SUP-001

This task is documentation-only.

Required evidence:

- current `main` and this file were inspected before editing;
- no Java, migration, OpenAPI, permission seed, or runtime behavior changed;
- approved policy covers restoration state, acknowledge/clear/close/escalation, broad-scope matching, expiry, open-ended approval, permissions, conflicts, and query visibility.

No Maven command is claimed for this documentation-only decision task.

## 6. ALM-SUP-002 completion evidence

ALM-SUP-002 was executed against HidraAPI baseline `e10f1ba9c738337b1dea9f57c1184cb5fe49d0b4`.

Live-source reconciliation found that suppression already had persistence/JPA representation and domain enums, but no current `AlarmSuppression` domain aggregate and no dedicated suppression application repository port. The application contracts therefore remain infrastructure-neutral and use only existing suppression enums plus neutral identifiers.

Added contracts:

- `CreateAlarmSuppressionCommand` for exact scope/reference, optional persisted scope evidence, reason, authenticated actor reference, optional end time, optional workflow evidence, and correlation;
- `ReleaseAlarmSuppressionCommand`;
- `EvaluateAlarmSuppressionExpiryCommand` for backend-owned idempotent expiry evaluation;
- `AlarmSuppressionQuery`;
- `AlarmSuppressionDto` and `AlarmSuppressionPageDto`;
- `ManageAlarmSuppressionUseCase`;
- `AlarmSuppressionQueryUseCase`.

No domain lifecycle implementation, repository adapter, scheduler, Workflow integration, REST endpoint, permission seed, migration, or OpenAPI contract is included in ALM-SUP-002.

## 7. Next authorized task

```text
ALM-SUP-003 — feat(alarm): implement suppression lifecycle policy
```

ALM-SUP-003 must first reconcile the existing Alarm aggregate/lifecycle event behavior with the persistence-only suppression shape. It must not create a duplicate suppression model merely to mirror the JPA entity.
