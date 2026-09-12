# HidraAPI Planning Roadmap

```text
Roadmap file : docs/roadmap/planning.md
Roadmap code : PLN
Scope        : Planning bounded context query and lifecycle contracts
Repository   : HidraAPI
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-09-12
Status       : Active
```

---

## 1. Purpose

This roadmap is the execution source of truth for the `planning` bounded context. Planning owns expected operational state: planning periods, operational plans, revisions, nominations, planned targets, scenarios, constraints, expected flow state and planning-side workflow references. Telemetry remains owner of actual measurements and workflow remains owner of approval task state.

The module already contains domain, application, persistence and limited create-oriented REST code. HWEB-010 requires deterministic contracts before frontend implementation proceeds.

---

## 2. Boundary rules

Planning must not import another business module's domain or persistence model. Cross-module references remain neutral identifiers/snapshots only. Planning must not expose telemetry readings as owned data and must not own workflow task state.

Cross-module approval orchestration may use workflow public input ports and DTOs only. Planning remains owner of `PlanRevision` lifecycle state; workflow remains owner of workflow instance/task/transition state. No planning persistence may access workflow persistence and no workflow persistence may access planning persistence.

Frontend-facing contracts must be published through deterministic OpenAPI. Route permissions are derived by the platform in canonical `<module>:<resource>:<action>` form and backend authorization remains authoritative.

---

## 3. HWEB-010 execution sequence

| Code | Commit message | Status | Scope |
|---|---|---:|---|
| `PLN-001` | `feat(planning): expose HWEB-010 query contracts` | Completed | Read-only list/detail contracts for periods, operational plans, revisions, nominations and plan targets; stable pagination; deterministic 400/404; route-permission publication; no new lifecycle mutations. PR CI `34657410805` passed compile, tests, full verification, acceptance compile/test/verify, deterministic OpenAPI publication and artifact upload on exact head `0735592fca3075754c06a3d1a98d0a8236e8e6ec`. |
| `PLN-002` | `feat(planning): publish authoritative workflow approval integration` | In Progress | Publish revision-scoped approval status/actions and backend-owned transition execution using workflow public contracts; planning applies the resulting lifecycle effect; deterministic stale-task conflict; no client task scanning or transition-name inference. Tracks issue #70. |

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

### PLN-002 allowed production changes

- add a planning approval inbound use case and application service;
- add planning approval REST request/response contracts and controller under `/api/v1/planning/revisions/{revisionId}/approval`;
- extend the workflow public query input port with deterministic current-task resolution for one workflow instance;
- extend the workflow query adapter only as required to implement that public query;
- use the existing workflow transition execution input port for backend-defined actions;
- update `PlanRevision` through the existing planning repository port after a successful workflow decision;
- add focused planning/workflow tests proving relation validation, action exposure, lifecycle effect and stale-task conflict propagation.

PLN-002 must not add direct cross-module repository/entity imports, client-side status mappings, generic workflow inbox scanning, or a second workflow state machine.

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

`COMMENT` is not state advancing and remains unavailable through workflow transition execution. Approval writes must preserve workflow's `expectedTaskUpdatedAt` stale-task conflict behavior. A revision without `workflowInstanceId`, a workflow instance targeting another module/type/id, or a missing current task is rejected deterministically rather than guessed.

---

## 4. Validation

PLN-001 validation evidence:

```text
mvn -q -DskipTests compile  -> SUCCESS (CI repository compile check)
mvn -q test                 -> SUCCESS (CI repository test check)
mvn -q clean verify         -> SUCCESS (CI repository full verification)
acceptance compile          -> SUCCESS
acceptance test             -> SUCCESS
acceptance clean verify     -> SUCCESS
deterministic OpenAPI       -> SUCCESS
OpenAPI artifact upload     -> SUCCESS
```

PLN-002 required validation before completion:

```text
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
acceptance compile/test/verify
deterministic OpenAPI generation and artifact upload
```
