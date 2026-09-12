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

The module already contains domain, application, persistence and limited create-oriented REST code. HWEB-010 requires deterministic frontend-facing contracts without moving planning lifecycle authority into HidraWEB.

---

## 2. Boundary rules

Planning must not import another business module's domain or persistence model. Cross-module references remain neutral identifiers/snapshots only. Planning may integrate through another module's published application ports/contracts from infrastructure adapters. Planning must not expose telemetry readings as owned data, must not own workflow task state, and must not infer workflow decisions from labels or transition names.

Frontend-facing contracts must be published through deterministic OpenAPI. Route permissions are derived by the platform in canonical `<module>:<resource>:<action>` form and backend authorization remains authoritative.

---

## 3. HWEB-010 execution sequence

| Code | Commit message | Status | Scope |
|---|---|---:|---|
| `PLN-001` | `feat(planning): expose HWEB-010 query contracts` | Completed | Read-only list/detail contracts for periods, operational plans, revisions, nominations and plan targets; stable pagination; deterministic 400/404; route-permission publication; no new lifecycle mutations. PR CI `34657410805` passed compile, tests, full verification, acceptance compile/test/verify, deterministic OpenAPI publication and artifact upload on exact head `0735592fca3075754c06a3d1a98d0a8236e8e6ec`. |
| `PLN-002` | `feat(planning): integrate authoritative workflow approval` | In Progress | Publish revision-scoped approval context and execution; exact workflow target/current-task resolution; backend-defined permitted actions; existing workflow stale-task precondition; atomic workflow decision -> planning revision `APPROVED`/`REJECTED` lifecycle effect; deterministic 400/403/404/409; no submit command, no planning-owned workflow state, no frontend state machine. |

### PLN-001 allowed production changes

- add a read-only inbound query use case under `planning.application.port.in`;
- add a read-only outbound query port under `planning.application.port.out`;
- add an application query service;
- add a Spring MVC query controller under `/api/v1/planning`;
- add a persistence query adapter using existing planning JPA repositories and the existing persistence mapper;
- extend only the planning JPA repositories needed for relationship-scoped reads.

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

No new POST, PUT, PATCH or DELETE endpoints belong to PLN-001.

---

## 4. PLN-002 — authoritative workflow approval integration

### Public planning contract

```text
GET  /api/v1/planning/revisions/{revisionId}/approval
POST /api/v1/planning/revisions/{revisionId}/approval/actions/{transitionId}/execute
```

`GET .../approval` must return whether the revision is currently under approval. For a submitted revision with a workflow instance, the backend resolves the exact workflow instance target tuple, exact current task, current task `updatedAt` token, and backend-defined action list. HidraWEB must not scan the generic workflow inbox or infer an action from names/status strings.

`POST .../execute` accepts the workflow task stale-write token (`expectedTaskUpdatedAt`) plus optional reason/note/comment/correlation data. Only backend-defined, actor-permitted `APPROVE` and `REJECT` actions are valid through the planning approval surface. The workflow transition and planning revision update participate in one transaction.

Authoritative lifecycle effect:

```text
workflow decision APPROVE -> PlanRevisionStatus.APPROVED
workflow decision REJECT  -> PlanRevisionStatus.REJECTED
```

Approval metadata (`approvedByActorId`, `approvedAt`) is written only for `APPROVED`. Submission metadata and `workflowInstanceId` remain planning-owned revision history.

PLN-002 does **not** add:

- revision create/update commands;
- revision submit/supersede/withdraw commands;
- planning-owned workflow task/transition persistence;
- target-module callback conventions inferred by the frontend;
- planning mutation version semantics beyond the workflow task stale token;
- planned-vs-actual behavior;
- realtime planning events.

### PLN-002 allowed production changes

- add a planning inbound approval use case and application service;
- add a planning outbound workflow approval port using neutral records only;
- add a planning infrastructure adapter that consumes workflow **application** contracts only;
- add revision-scoped planning approval REST request/controller/error translation;
- add a workflow target-approval inbound contract, outbound query port, application service and JPA query adapter;
- reuse the existing authoritative workflow transition executor and its `expectedTaskUpdatedAt` conflict guard;
- do not modify workflow or planning persistence schemas.

### PLN-002 required tests

- exact workflow target/current-task resolution is enforced server-side;
- unavailable or non-permitted transitions do not execute;
- stale task timestamps fail before workflow mutation;
- workflow `APPROVE` persists planning revision `APPROVED` with approval actor/time;
- workflow `REJECT` persists planning revision `REJECTED` without approval metadata;
- cross-module integration uses public application contracts, not domain/persistence imports.

---

## 5. Validation

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

Evidence:

```text
PR CI run : 34657410805
Exact SHA : 0735592fca3075754c06a3d1a98d0a8236e8e6ec
Conclusion: SUCCESS
```

PLN-002 must pass repository compile, tests, full verify, acceptance compile/test/verify, deterministic OpenAPI generation and exact-head artifact publication before it may be marked Completed or issue #70 may close.
