# HidraAPI Planning Roadmap

```text
Roadmap file : docs/roadmap/planning.md
Roadmap code : PLN
Scope        : Planning bounded context query and lifecycle contracts plus verified HWEB-010 backend dependencies
Repository   : HidraAPI
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-09-12
Status       : Active
```

---

## 1. Purpose

This roadmap is the execution source of truth for the `planning` bounded context and the backend contracts required by HWEB-010. Planning owns expected operational state: planning periods, operational plans, revisions, nominations, planned targets, scenarios, constraints, expected flow state and planning-side workflow references. Telemetry remains owner of actual measurements, monitoring remains owner of planned-versus-actual comparison/deviation semantics, and workflow remains owner of approval task state.

The module already contains domain, application, persistence and limited create-oriented REST code. HWEB-010 requires deterministic contracts before frontend implementation proceeds.

---

## 2. Boundary rules

Planning must not import another business module's domain or persistence model. Cross-module references remain neutral identifiers/snapshots only. Planning must not expose telemetry readings as owned data, must not own monitoring deviation semantics, and must not own workflow task state.

Cross-module approval orchestration may use workflow public input ports and DTOs only. Planning remains owner of `PlanRevision` lifecycle state; workflow remains owner of workflow instance/task/transition state. No planning persistence may access workflow persistence and no workflow persistence may access planning persistence.

Planned-versus-actual reads remain owned by monitoring. A planning target may be referenced only by its neutral identifier; monitoring must expose the authoritative comparison rather than requiring HidraWEB to fetch raw telemetry or scan unrelated deviation pages.

Frontend-facing contracts must be published through deterministic OpenAPI. Route permissions are derived by the platform in canonical `<module>:<resource>:<action>` form and backend authorization remains authoritative.

---

## 3. HWEB-010 execution sequence

| Code | Commit message | Status | Scope |
|---|---|---:|---|
| `PLN-001` | `feat(planning): expose HWEB-010 query contracts` | Completed | Read-only list/detail contracts for periods, operational plans, revisions, nominations and plan targets; stable pagination; deterministic 400/404; route-permission publication; no new lifecycle mutations. PR CI `34657410805` passed compile, tests, full verification, acceptance compile/test/verify, deterministic OpenAPI publication and artifact upload on exact head `0735592fca3075754c06a3d1a98d0a8236e8e6ec`. |
| `PLN-002` | `feat(planning): publish authoritative workflow approval integration` | Completed | Revision-scoped approval status/actions and backend-owned transition execution using workflow public contracts; planning applies the resulting lifecycle effect; deterministic stale-task conflict is preserved; no client task scanning or transition-name inference. Tracks issue #70. Final merge `6ef581f557e42e8d96b03ccf429562e646f2e321`; merge-SHA OpenAPI artifact `10293549730`. |
| `PLN-003` | `feat(monitoring): expose plan-target-scoped deviations` | Implemented; initial exact-head CI green; final evidence CI pending | Extend the existing monitoring deviation collection with an optional exact `planTargetId` filter so HWEB-010-05 can retrieve authoritative comparison rows for one planning target without broad-page scanning or frontend arithmetic. No planning/telemetry persistence coupling and no new comparison semantics. Tracks issue #71. |

### PLN-001 public read contract

```text
GET /api/v1/planning/periods?page={page}&size={size}
GET /api/v1/planning/periods/{id}
GET /api/v1/planning/operational-plans?page={page}&size={size}
GET /api/v1/planning/operational-plans/{id}
GET /api/v1/planning/revisions?planId={planId}&page={page}&size={size}
GET /api/v1/planning/revisions/{id}
GET /api/v1/planning/nominations?revisionId={revisionId}&page={page}&size={size}
GET /api/v1/planning/nominations/{id}
GET /api/v1/planning/targets?revisionId={revisionId}&page={page}&size={size}
GET /api/v1/planning/targets/{id}
```

Paging is zero-based. Default size is 50. Valid size range is 1..200. Missing/blank relationship identifiers and invalid paging are 400-class request errors. Unknown detail identifiers are 404 through the platform exception handler.

### PLN-002 public contract

```text
GET  /api/v1/planning/revisions/{revisionId}/approval
POST /api/v1/planning/revisions/{revisionId}/approval/actions/{transitionId}/execute
```

The GET response identifies the revision, its planning status, workflow instance, authoritative current task (when actionable), task `updatedAt`, and backend-defined available actions. The POST request carries the authoritative `expectedTaskUpdatedAt` plus optional reason/note/comment/correlation fields. The backend resolves the task from the revision's workflow instance, verifies the workflow target belongs to that revision, executes the selected workflow transition, and then applies the planning lifecycle effect from the returned workflow decision.

Lifecycle mapping owned by planning for PLN-002:

```text
APPROVE            -> APPROVED
REJECT             -> REJECTED
REQUEST_CORRECTION -> REJECTED
RETURN             -> REJECTED
CANCEL             -> WITHDRAWN
CORRECT            -> SUBMITTED
DELEGATE           -> SUBMITTED
ESCALATE           -> SUBMITTED
```

`COMMENT` is not state advancing and remains unavailable through workflow transition execution. Approval writes preserve workflow's `expectedTaskUpdatedAt` stale-task conflict behavior. A revision without `workflowInstanceId`, a workflow instance targeting another module/id, or a missing current task is rejected deterministically rather than guessed.

### PLN-002 boundary evidence

- Planning API calls only `PlanningApprovalUseCase`.
- Planning application imports workflow public `application.port.in` contracts only.
- Workflow transition execution remains owned by workflow.
- Planning lifecycle persistence remains owned by planning through `PlanRevisionRepositoryPort`.
- No cross-module repository, entity, infrastructure, or domain-model import was introduced.
- Focused tests cover action resolution, wrong-target rejection, approve lifecycle effect, and stale-task conflict propagation.

### PLN-003 public contract

Existing route, extended with one optional server-side filter:

```text
GET /api/v1/monitoring/deviations?planTargetId={planTargetId}&status={status}&severity={severity}&topologyAssetId={topologyAssetId}&telemetryPointId={telemetryPointId}&from={from}&to={to}&page={page}&size={size}
```

`planTargetId` is an exact filter over monitoring-owned `PlanActualDeviation.planTargetId`. It composes with the existing monitoring filters and existing paging. When no deviation matches the requested target, the collection returns an empty page rather than forcing HidraWEB to scan other pages or infer a missing-resource lifecycle.

The returned `DeviationView` remains unchanged and monitoring-owned:

```text
id
evaluationId
planTargetId
expectedFlowStateId
trustedTelemetryReadingId
telemetryPointId
topologyAssetType
topologyAssetId
topologyAssetCode
actualValue
expectedValue
differenceValue
differencePercent
unitId
severity
status
detectedAt
resolvedAt
reasonCode
reasonMessage
```

### PLN-003 boundary evidence

- The API remains under `/api/v1/monitoring`; route ownership and permission derivation remain monitoring-owned.
- The filter is applied only to monitoring persistence data already containing the neutral `planTargetId` reference.
- Planning persistence is not queried or imported by monitoring.
- Telemetry readings are not queried by the frontend to recreate comparison semantics.
- No expected/actual arithmetic, tolerance classification, severity mapping, or state machine is added outside monitoring.
- Focused adapter tests cover exact target scoping, authoritative actual/expected/difference/unit projection values, paging across target-scoped rows, and the empty-page result for an unknown target.

---

## 4. Validation

PLN-002 final evidence:

```text
Backend merge SHA : 6ef581f557e42e8d96b03ccf429562e646f2e321
OpenAPI artifact  : hidra-api-openapi-6ef581f557e42e8d96b03ccf429562e646f2e321
Artifact id       : 10293549730
Artifact digest   : sha256:422cc6f37a7e5a6674a77d32be5ee1325bb9a8a8ad682075094a5699d3a9f243
Issue             : #70 — CLOSED
Conclusion        : SUCCESS
```

PLN-003 initial exact-head evidence:

```text
PR                 : HidraAPI #75
Implementation head: b99c459e0257110be3d036029751044537607046
PR CI run          : 34682927328
Repository compile : SUCCESS
Repository tests   : SUCCESS
Repository verify  : SUCCESS
Acceptance compile : SUCCESS
Acceptance tests   : SUCCESS
Acceptance verify  : SUCCESS
Deterministic OpenAPI: SUCCESS
Artifact upload    : SUCCESS
Artifact id        : 10295220452
Artifact name      : hidra-api-openapi-6d2e8d09e013e3489ce956a38cf17ffa945ce23b
Artifact digest    : sha256:c5345cebdbdd4cdfcb7ed7d42ba51909fc2f1f97f91e7dc70080a2e6a79c5f01
Conclusion         : SUCCESS
```

This roadmap-evidence update changes the PR head, so a final exact-head CI run is required before merge. After merge, the exact merge SHA must pass push-triggered `main` CI and publish the deterministic OpenAPI artifact before issue #71 is closed and HWEB-010-05 resumes.
