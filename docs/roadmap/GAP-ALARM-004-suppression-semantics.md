# GAP-ALARM-004 — Alarm suppression semantics

Status: **CLOSED — suppression capability implemented and repository verification green**

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
| `ALM-SUP-003` | `feat(alarm): implement suppression lifecycle policy` | Implement domain/application policy for ALARM overlay/restoration, broad-scope matching, acknowledgement/clear/close/escalation interaction, conflicts, and server-derived actor handling. | Approved lifecycle rules are deterministic and unit-tested. | **Completed** |
| `ALM-SUP-004` | `feat(alarm): expose suppression persistence queries` | Extend existing suppression repository adapter only as required by approved use cases: exact-scope active lookup, expiry candidates, history/query support. | No duplicate persistence model; overlap and expiry queries are deterministic. | **Completed** |
| `ALM-SUP-005` | `feat(alarm): integrate suppression workflow approval` | Verify open-ended suppression through Workflow public contracts/ports and fail closed when approval is absent. | No transition-name inference or direct foreign aggregate dependency. | **Completed** |
| `ALM-SUP-006A` | `feat(alarm): add suppression expiry audit contract` | Export a narrow Audit contract and provision canonical `ALARM_SUPPRESSION_EXPIRED` taxonomy for all suppression scopes. | Broad-scope expiry can be attributed to a scheduled/system actor without overloading release fields. | **Completed** |
| `ALM-SUP-006` | `feat(alarm): add suppression expiry orchestration` | Add idempotent backend-owned expiry orchestration using the existing Spring scheduling mechanism. | ACTIVE suppressions expire once; Audit evidence, actor attribution, and ALARM restoration are deterministic. | **Completed** |
| `ALM-SUP-007` | `feat(alarm): publish suppression REST contracts` | Publish canonical create/release/query endpoints, generated OpenAPI schemas, route-permission metadata, deterministic 400/403/404/409 behavior. | No CRUD-style leakage; only approved lifecycle operations exposed. | **Completed** |
| `ALM-SUP-008` | `test(alarm): verify suppression lifecycle end to end` | Domain/application/persistence/REST/Testcontainers coverage including ALARM and broad scopes, acknowledgement, clear/close, escalation preservation, workflow approval, expiry, conflicts, audit evidence, OpenAPI determinism. | Full repository verification green and suppression behavior traceable. | **Completed** |
| `ALM-SUP-009` | `docs(alarm): finalize suppression capability` | Record exact endpoints, permissions, events, validation evidence, remaining limits, and frontend-consumption boundary. | GAP-ALARM-004 closed with no undocumented semantics. | **Completed** |

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

## 7. ALM-SUP-003 completion evidence

ALM-SUP-003 was executed against HidraAPI baseline `283307fe9f08c6405060699d2cd6e1462d61f637`.

The implementation adds a pure domain `AlarmSuppressionPolicy` rather than a duplicate suppression aggregate. It defines:

- creation validation, including mandatory Workflow evidence for open-ended suppression;
- exact-scope deterministic matching for ALARM, ALARM_TYPE, TOPOLOGY_ASSET, MONITORING_RULE, and SOURCE;
- overlap rejection policy for an existing ACTIVE exact-scope suppression;
- release eligibility limited to ACTIVE suppression;
- idempotency-friendly expiry eligibility for ACTIVE time-bounded suppression;
- restoration precedence from existing authoritative Alarm evidence: CANCELLED, CLOSED, CLEARED, ACKNOWLEDGED, ESCALATED, then ACTIVE;
- acknowledgement and escalation remain allowed while a nonterminal alarm is suppressed;
- the existing close guard now permits a SUPPRESSED alarm to close only when its underlying clear evidence is present, preserving the pre-existing normal closure rule.

Focused unit coverage locks the approved semantics. No repository adapter, scheduler, Workflow adapter, REST endpoint, migration, or OpenAPI publication is included.

Actor identity remains a required application contract value and must be server-derived at the trusted API/application boundary in later integration work; ALM-SUP-003 does not introduce frontend actor trust.

## 8. ALM-SUP-004 completion evidence

ALM-SUP-004 was executed against HidraAPI baseline `b88af5dc5f7eb8d622cc343041f2ae788ad38fcd`.

The existing `AlarmSuppressionJpaEntity` and `hidra_alarm_suppression` table remain authoritative persistence. The task adds:

- HRA-061 explicitly classifies `AlarmSuppression` as a retained infrastructure read/persistence model whose dedicated domain mirror, application repository port, and JPA adapter must remain retired;
- the existing `AlarmSuppressionJpaRepository` is therefore extended in place, without reintroducing those retired mirrors;
- exact active-scope/status existence lookup is available for overlap enforcement;
- deterministic due-expiry lookup is available for ACTIVE suppressions whose end time is at or before the authoritative evaluation instant;
- `JpaSpecificationExecutor` support remains infrastructure-owned for later filtered history/query composition without leaking JPA types into application contracts.

The initial ALM-SUP-004 commit temporarily reintroduced a retired repository port and adapter; CI guardrail `DomainPersistenceMirrorGuardrailTest` correctly rejected that architecture drift. The corrective commit removes them and preserves HRA-061.

No database migration, new table, application repository mirror, JPA adapter mirror, scheduler, Workflow approval adapter, REST endpoint, permission publication, or OpenAPI change is included.

## 9. Next authorized task

```text
ALM-SUP-005 — feat(alarm): integrate suppression workflow approval
```

ALM-SUP-005 must use Workflow's public application contract/port rather than direct Workflow persistence or transition-name inference. Open-ended suppression must fail closed when approval cannot be verified.


## 10. ALM-SUP-005 completion evidence

ALM-SUP-005 was executed against HidraAPI baseline `c7ebe34e27e56506e70ad6d9de54988bdd3642e1`.

The implementation follows the repository's existing deliberate cross-module Workflow contract pattern:

- Workflow exports `AlarmSuppressionWorkflowContract` rather than exposing Workflow persistence or aggregates to Alarm;
- `AlarmSuppressionWorkflowContractAdapter` validates the instance targets module `alarm`, target type `ALARM_SUPPRESSION`, and the exact backend operation reference;
- the Workflow instance must be `COMPLETED`;
- the final timeline decision must be `APPROVE`;
- Alarm consumes the narrow contract through `AlarmSuppressionApprovalService`;
- time-bounded suppression does not invoke Workflow;
- open-ended suppression fails closed if approval evidence is absent, mismatched, pending, or rejected.

The stable approval operation reference is backend-derived as `<SCOPE_TYPE>:<scopeReferenceId>`, preventing approval evidence from being reused for a different suppression scope/reference. No Workflow transition-name inference and no direct Workflow domain/persistence dependency is introduced.

No scheduler, suppression mutation service, REST endpoint, permission publication, migration, or OpenAPI change is included.

## 11. ALM-SUP-006 precondition audit

ALM-SUP-006 was re-audited against HidraAPI baseline `5a11a1bf0030355a05b8c10ab81a4c3c421cb52d` before any production-code change.

The first precondition audit incorrectly concluded that no scheduling mechanism was configured. A deeper live-source inspection corrected that finding:

- `HidraRealtimeConfiguration` already enables Spring scheduling with `@EnableScheduling`;
- `HidraRealtimeHeartbeatJob` already uses `@Scheduled`.

Therefore the repository already has an approved platform scheduling mechanism that Alarm can reuse without introducing a second scheduler.

The remaining real prerequisite was suppression expiry audit evidence for every scope:
   - `AlarmSuppressionJpaEntity` records creation actor and manual release actor, but has no expiry actor/time fields;
   - `AlarmLifecycleEvent` can represent ALARM-scoped `UNSUPPRESSED` evidence because an alarm id exists;
   - broad scopes (`ALARM_TYPE`, `TOPOLOGY_ASSET`, `MONITORING_RULE`, `SOURCE`) have no alarm instance on which to attach that lifecycle event;
   - no exported Alarm-specific Audit contract or suppression lifecycle event persistence currently exists.

ALM-SUP-006A resolves the remaining prerequisite by exporting `Audit.application.contract.alarm.AlarmSuppressionAuditContract`. Its Audit-owned adapter:

- records target type `ALARM_SUPPRESSION` and the suppression id;
- records the exact suppression scope as target code `<scopeType>:<scopeReferenceId>`;
- attributes automatic expiry to `AuditActorType.SCHEDULED_JOB`;
- records decision `EXPIRED`;
- resolves `ALARM_SUPPRESSION_EXPIRED` and `BUSINESS` through active Audit taxonomy and fails closed if taxonomy is unavailable.

Migration `V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql` provisions the required event type idempotently. The Audit contract package is explicitly exported through the repository architecture guardrails.

Release fields remain reserved for manual release; automatic expiry is represented by status plus canonical Audit evidence.

## 12. Current next action

```text
ALM-SUP-006 — feat(alarm): add suppression expiry orchestration
```

ALM-SUP-006 may now reuse the existing Spring scheduling mechanism. It must process only ACTIVE due suppressions, append Audit evidence before considering expiry complete, restore ALARM-scoped state from authoritative alarm evidence, and remain idempotent.


## 13. ALM-SUP-006 completion evidence

ALM-SUP-006 was executed against HidraAPI baseline `dbea0996384c9b09d14cc1d52745d76620226441`.

Implementation:

- reuses the repository's existing Spring scheduling mechanism through `@Scheduled`;
- uses a dedicated infrastructure scheduler and a transactional expiry orchestrator;
- acquires pessimistic write locks on ACTIVE due suppression rows;
- defensively re-checks due/ACTIVE semantics through `AlarmSuppressionPolicy`;
- appends canonical `ALARM_SUPPRESSION_EXPIRED` Audit evidence with scheduled-job actor attribution before marking expiry complete;
- keeps broad-scope suppression as an evaluation overlay and does not mass-mutate matching alarms;
- for ALARM scope only, restores state only when the alarm is still visibly `SUPPRESSED`;
- restoration is derived from authoritative Alarm evidence through `AlarmSuppressionPolicy.restorationState`;
- appends `UNSUPPRESSED` Alarm lifecycle evidence with server actor attribution;
- marks persistence status `ACTIVE -> EXPIRED` without overloading manual release fields;
- all mutation occurs in one transaction so Audit or restoration failure prevents expiry completion;
- repeated execution is idempotent because only ACTIVE due rows are selected and each row is locked.

The Alarm domain record now provides a narrow `withState` operation so state restoration remains domain-owned rather than being reconstructed in infrastructure.

Focused tests cover broad-scope expiry, ALARM restoration, defensive non-due handling, and scheduled system actor delegation.

## 14. Next authorized task

```text
ALM-SUP-007 — feat(alarm): publish suppression REST contracts
```

ALM-SUP-007 must expose only backend-owned create/release/query behavior, derive actor identity server-side, use canonical route permissions, and publish deterministic conflict/approval/not-found errors without exposing JPA types.


## 15. ALM-SUP-007 completion evidence

ALM-SUP-007 was executed against HidraAPI baseline `6d18f11baf12075ec1ac12cdaeab2d8edd256163`.

REST contract:

- `POST /api/v1/alarm/suppressions` creates a governed suppression;
- `POST /api/v1/alarm/suppressions/{suppressionId}/release` releases an ACTIVE suppression;
- `GET /api/v1/alarm/suppressions/{suppressionId}` returns suppression evidence;
- `GET /api/v1/alarm/suppressions` supports paged filtering by id, scope type/reference, alarm id, and status.

The API derives actor identity exclusively through `CurrentActorResolver`; actor ids are not accepted from request bodies.

HRA-061 is preserved by `AlarmSuppressionApplicationAdapter`: it implements the existing application input ports from infrastructure and owns access to the retained suppression JPA model. Controllers depend only on application input ports and never access JPA repositories.

Lifecycle behavior:

- exact ACTIVE overlap is pre-checked and additionally enforced by partial unique index `ux_alarm_suppression_active_scope`;
- both overlap paths map to stable HTTP `409 ALARM_SUPPRESSION_CONFLICT`;
- ALARM-scoped create sets the existing nonterminal Alarm state to `SUPPRESSED` and appends a `SUPPRESSED` lifecycle event;
- ALARM-scoped release restores authoritative state through `AlarmSuppressionPolicy.restorationState` when the Alarm is still visibly `SUPPRESSED`, then appends `UNSUPPRESSED`;
- broad scopes do not mass-mutate Alarm rows;
- open-ended creation continues to fail closed through `AlarmSuppressionApprovalService`;
- release uses a pessimistic row lock and rejects already-terminal suppression state.

Permission reconciliation:

The platform derives route permissions from URL + HTTP method. The published canonical route permissions are therefore:

- `alarm:suppressions:read` for GET routes;
- `alarm:suppressions:execute` for POST create/release routes.

These platform-derived names supersede the earlier conceptual `alarm:suppression:read/create/release` intent; no conflicting manual permission seed is introduced.

OpenAPI publication remains automatic through Spring MVC/springdoc registration of these routes.

## 16. Next authorized task

```text
ALM-SUP-008 — test(alarm): verify suppression lifecycle end to end
```

ALM-SUP-008 must harden the full approved lifecycle with database-backed/Testcontainers evidence, including exact-scope concurrency conflict, Workflow-approved open-ended creation, ALARM restoration, broad-scope expiry, route permissions, and HTTP error contracts.


## 17. ALM-SUP-008 completion evidence

ALM-SUP-008 was executed against HidraAPI baseline `a5de4d1b1024b854bf8917141438f7cfb527baf2`.

The end-to-end verification layer now combines the focused tests added throughout ALM-SUP-003 through ALM-SUP-007 with new database-backed and route-permission evidence:

- `AlarmSuppressionPolicyTest` covers creation rules, exact-scope matching, expiry eligibility, overlap/release guards, restoration precedence, acknowledgement/escalation, and suppressed-close semantics;
- `AlarmSuppressionApprovalServiceTest` and `AlarmSuppressionWorkflowContractAdapterTest` cover approved open-ended Workflow evidence and fail-closed rejection paths;
- `AlarmSuppressionExpiryOrchestratorTest` covers broad-scope expiry and ALARM-scoped restoration/lifecycle evidence;
- `AlarmSuppressionControllerTest` proves REST commands derive actor identity from `CurrentActorResolver`;
- `AlarmSuppressionApiExceptionHandlerTest` proves stable HTTP 409 conflict mapping;
- `AlarmSuppressionPersistenceMigrationTest` migrates an empty PostgreSQL Testcontainer and proves:
  - the canonical `ALARM_SUPPRESSION_EXPIRED` Audit taxonomy is provisioned;
  - the partial unique index prevents a second exact-scope ACTIVE suppression;
  - historical terminal suppression rows for the same scope/reference remain allowed;
- `AlarmSuppressionRoutePermissionTest` proves canonical route-derived permissions:
  - `alarm:suppressions:read`;
  - `alarm:suppressions:execute`.

This task adds verification only. No suppression production semantics are changed.

## 18. Next authorized task

```text
ALM-SUP-009 — docs(alarm): finalize suppression capability
```

ALM-SUP-009 must finalize the suppression roadmap/documentation only after ALM-SUP-008 CI is green. It must capture final commits, CI evidence, REST routes, canonical permissions, Workflow/Audit integration, expiry scheduling, and any known residual limitations without introducing production code.


## 19. ALM-SUP-009 final capability record

ALM-SUP-009 finalizes GAP-ALARM-004 against verified repository baseline `5028a90248ddf3a04d344308f6cfc2510f1d0ca2`.

### 19.1 Verified implementation chain

The implemented suppression capability is traceable through the following primary commits:

- `e10f1ba9c738337b1dea9f57c1184cb5fe49d0b4` — `docs(alarm): approve suppression lifecycle semantics`;
- `283307fe9f08c6405060699d2cd6e1462d61f637` — `feat(alarm): add suppression application contracts`;
- `b88af5dc5f7eb8d622cc343041f2ae788ad38fcd` — `feat(alarm): implement suppression lifecycle policy`;
- `6f4bdacae0000fdf7f25698d863695fb8a5454c0` plus HRA-061 corrective commits — suppression persistence query support;
- `37580030f84acb66953b03b5a2e29f543ecb1d56` plus architecture-export correctives — Workflow approval integration;
- `dbea0996384c9b09d14cc1d52745d76620226441` — suppression expiry Audit contract and taxonomy;
- `6d18f11baf12075ec1ac12cdaeab2d8edd256163` — backend-owned suppression expiry orchestration;
- `c8bc04e8e9659ac211c3e41238606b2d757e5083` plus `a5de4d1b1024b854bf8917141438f7cfb527baf2` — REST publication and Spring proxy corrective;
- `4c2a4d47abf6f624c2e47ba631ed48aa7e24521f` plus `5028a90248ddf3a04d344308f6cfc2510f1d0ca2` — end-to-end verification and route-handler test corrective.

Repository CI run **#451** (`36996917162`) completed successfully on
`5028a90248ddf3a04d344308f6cfc2510f1d0ca2`, establishing the green verification gate used to close this roadmap.

### 19.2 Published backend contract

Canonical REST routes:

```text
POST /api/v1/alarm/suppressions
POST /api/v1/alarm/suppressions/{suppressionId}/release
GET  /api/v1/alarm/suppressions/{suppressionId}
GET  /api/v1/alarm/suppressions
```

Canonical route-derived permissions:

```text
alarm:suppressions:read
alarm:suppressions:execute
```

The earlier conceptual permission names `alarm:suppression:read/create/release` are superseded by the platform's canonical route-permission derivation and must not be independently seeded.

Authenticated actor identity is server-derived through `CurrentActorResolver`. Browser-supplied actor attribution is not part of the contract.

### 19.3 Workflow and Audit integration

Open-ended suppression remains fail-closed and requires Workflow evidence through the exported
`AlarmSuppressionWorkflowContract`. Approval is based on backend-owned instance status, decision evidence, target module/type, and exact operation reference; transition-name inference is prohibited.

Automatic expiry uses the exported `AlarmSuppressionAuditContract` and canonical
`ALARM_SUPPRESSION_EXPIRED` Audit taxonomy. Expiry actor type is `SCHEDULED_JOB`; manual release actor fields are not overloaded for automatic expiry.

### 19.4 Scheduling and lifecycle evidence

The capability reuses the repository's existing Spring scheduling infrastructure.

Automatic expiry:

- selects only ACTIVE due suppressions;
- pessimistically locks selected rows;
- re-checks expiry eligibility;
- restores ALARM-scoped state from authoritative Alarm evidence when the alarm is still visibly `SUPPRESSED`;
- appends `UNSUPPRESSED` lifecycle evidence for ALARM restoration;
- appends canonical Audit expiry evidence;
- transitions suppression status to `EXPIRED`;
- leaves manual release fields untouched;
- remains retry/idempotency oriented because terminal rows are not selected again.

Manual ALARM-scoped release similarly restores authoritative state when appropriate and records `UNSUPPRESSED` evidence.

### 19.5 Persistence and conflict guarantees

Suppression persistence remains an HRA-061 retained infrastructure model. No retired domain/application persistence mirror is reintroduced.

Exact-scope ACTIVE overlap is protected by both application pre-check and PostgreSQL partial unique index
`ux_alarm_suppression_active_scope`. Conflict is exposed as stable HTTP
`409 ALARM_SUPPRESSION_CONFLICT`.

Historical RELEASED/EXPIRED/CANCELLED rows remain available and do not prevent later suppression for the same exact scope/reference.

### 19.6 Frontend consumption boundary

HidraWEB and other clients must consume the REST/OpenAPI contract and must not:

- calculate suppression matching independently;
- derive restoration state;
- fabricate actor ids;
- infer Workflow approval from labels or transition names;
- mutate suppression persistence directly;
- treat shelving as suppression;
- assume broad-scope suppression means mass state mutation of existing alarms.

The backend remains authoritative for lifecycle policy, approval, expiry, conflict detection, state restoration, actor attribution, and Audit evidence.

### 19.7 Known residual boundaries

GAP-ALARM-004 closes the suppression lifecycle/API capability. The following are not claimed as newly implemented by this roadmap:

- wiring broad-scope suppression matching into every alarm-generation producer/evaluation path; the deterministic matching policy exists, but each producer boundary must explicitly consume it before claiming generation-time suppression coverage;
- a universal include/exclude-suppressed flag on all existing active-alarm query endpoints; suppression evidence has dedicated query endpoints, while any additional active-alarm filtering must be introduced through the owning query contract rather than client-side filtering;
- SCADA/PLC actuation or parked industrial-extension work.

These boundaries are not permission to implement them implicitly under this closed roadmap. Any required expansion must be separately authorized and traced.

## 20. Closure

```text
GAP-ALARM-004 — CLOSED
Verified gate: HidraAPI CI #451 / run 36996917162 — SUCCESS
Verified SHA: 5028a90248ddf3a04d344308f6cfc2510f1d0ca2
```

No production code, migration, runtime configuration, REST behavior, or permission behavior is changed by ALM-SUP-009.
