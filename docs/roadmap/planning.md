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
| `PLN-002` | `feat(planning): publish authoritative workflow approval integration` | Completed | Revision-scoped approval status/actions and backend-owned transition execution using workflow public contracts; planning applies the resulting lifecycle effect; deterministic stale-task conflict is preserved; no client task scanning or transition-name inference. PR CI `34680620727` passed repository compile/test/full verify, acceptance compile/test/verify, deterministic OpenAPI publication and artifact upload on head `bea61b3727168fe276d3995ce07d749698307ab8`. Tracks issue #70. |

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

---

## 4. Validation

PLN-002 validation evidence:

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

Evidence:

```text
PR CI run : 34680620727
Head SHA  : bea61b3727168fe276d3995ce07d749698307ab8
Artifact  : 10293294417
Digest    : sha256:9aa3efe866b57b267ab1e444f9885e336309e0e78ec84af16405c3320856a27c
Conclusion: SUCCESS
```

A final exact-head CI run is required after this roadmap evidence commit before merge. The merge SHA must then pass push-triggered `main` CI and publish the deterministic OpenAPI artifact before issue #70 is closed.
