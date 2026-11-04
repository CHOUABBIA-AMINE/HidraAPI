# HPS-018 — Engineering Baselines and Configuration: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-018 — Engineering Baselines and Configuration

<a id="hapi-534"></a>
## HAPI-534 — define multi product property provisioning

Phase: HPS-018 — Engineering Baselines and Configuration

Legacy IDs: HPR-P25-022

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `custody` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

define multi product property provisioning

### 2. Existing Implementation

Independent immutable source stores and configuration definitions/values/feature-flag records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Independent cases A–G; mixed parent not closed

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `custody`; primary phase owns this obligation. A baseline is separate from a numerical run snapshot; no source revision may mutate an accepted historical manifest. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-022 — Product definitions and property methods with explicit applicability

Objective/justification: extend supported product inputs without pretending GAS metadata
implements all fluid physics. Evidence: C01–C05, S07/S08/S10 and X10/X11.
Owners/contracts: preserve Custody canonical gas composition/method qualification;
Simulation consumes explicit immutable property/model outputs. Settle new product-owner
coverage without transferring existing gas records. Functional/data: product classification,
composition/basis, source lab data and dates, method/version, reference P/T, phases,
validity, density/viscosity/Z and uncertainty when supplied/calculated; no constant-data
substitute for a condition-dependent model. API: authorized versioned product/method
create/read/qualification and full preflight diagnostics. Persistence: preserve gas V1
and exact-digest approval; new kind/format only through a registered compatible envelope.
Acceptance: case D hydrogen switch preserves natural-gas history and either executes a
qualified family or lists missing applicability; wrong fractions/basis, units, phases,
P/T range and unknown method fail. Numerical obligations: independent property states
and model-equivalent gas/H2/blend/liquid references before any capability claim;
no model is selected here. D/H/N; product design may proceed on explicit synthetic data;
operational property qualification still requires actual evidence.

~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: —. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `66f6eae94ad085ccba18709119df87efd188faa8` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:12862](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12862)
- [doc/model-remediation/RECONCILIATION.md:12947](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12947)
- [doc/model-remediation/RECONCILIATION.md:13674](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13674)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6113](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6113)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14602](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14602)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14687](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14687)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15414](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15414)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: DESIGN COMPLETE.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14602 | HPR-P25-022 | Multi-product and property-method provisioning preflight / Custody and Simulation | 018; existing Custody gas contract | docs(custody): define multi product property provisioning | PLANNED — NOT SELECTED
doc/model-remediation/RECONCILIATION.md:12862 | HPR-P25-022 | Multi-product and property-method provisioning preflight / Custody and Simulation | 018; existing Custody gas contract | docs(custody): define multi product property provisioning | PLANNED — NOT SELECTED
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15414 | HPR-P25-022 | Registered in prior amendment; PLANNED — NOT SELECTED | FOUNDATION_SCOPE_REFINEMENT | Cross-module engineering governance | Independent successor/design 022 after 018; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 022 after 018 separately selected scope separation
doc/roadmap/ULTIMATE_ROADMAP.md:6113 | HPR-P25-022 | DESIGN COMPLETE | Future runtime assigned 050–058; design is not executable completion
doc/model-remediation/RECONCILIATION.md:13674 | HPR-P25-022 | Registered in prior amendment; PLANNED — NOT SELECTED | FOUNDATION_SCOPE_REFINEMENT | Cross-module engineering governance | Independent successor/design 022 after 018; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 022 after 018 separately selected scope separation
~~~~~~~~

<a id="hapi-540"></a>
## HAPI-540 — define authoritative engineering baseline management

Phase: HPS-018 — Engineering Baselines and Configuration

Legacy IDs: HPR-P25-028

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

define authoritative engineering baseline management

### 2. Existing Implementation

Independent immutable source stores and configuration definitions/values/feature-flag records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Detailed registration above; actual evidence required

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A baseline is separate from a numerical run snapshot; no source revision may mutate an accepted historical manifest. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-028 — Authoritative engineering baseline management preflight

Owner: Topology baseline authority with Assets/Custody/Simulation source owners and
Workflow evidence. Objective: independently specify exact freeze/read/compare/history
of a supported engineering configuration. Justification/evidence: existing Topology,
Custody and characteristic immutable stores plus general snapshot structures are useful,
but not the complete operator baseline workflow verified by a Case E test.
Dependencies: 019/020/021/022 designs (018 transitively); existing source compatibility
review, explicit owner selection. No 023 numerical capture or solver dependency.
Deliverables: owner-defined baseline identity/revision, exact geometry/profile/section/
station/unit/manufacturer/product/constraint references/digests, source/effective/capture
and qualification evidence, authorized historical retrieval and supported import/export;
concrete implementation envelopes and source-selection/retention rules.
API/persistence design: freeze/read/revision/compare and immutable reference resolution,
atomic append/rollback/replay, no installed-source duplication or simulation run required.
Acceptance: data-only cases B/C/D/E/G, old-baseline reconstruction after updates,
withdrawn current governance distinguished from authorized historic facts, corrupt/missing/
wrong-effective/dangling revision refusal, conflicting parallel writes and partial capture.
No numerical result replay or provider controls; no production data import.
Exact message: `docs(topology): define authoritative engineering baseline management`.
Selection/completion: separately owner-selected two-document preflight; accepted only
with its exact-head Documentation CI and complete implementation registration, not F2 closure.

~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: —. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `6d4164bd212b3835003e0b064dc906cbbcffec07` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13479](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13479)
- [doc/model-remediation/RECONCILIATION.md:13680](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13680)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6119](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6119)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15219](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15219)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15420](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15420)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: DESIGN COMPLETE.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15420 | HPR-P25-028 | NEW — PLANNED | FOUNDATION_ACTIVE | Topology coordinates Assets/Custody/Workflow | 019/020/021/022 accepted designs | Independent foundation objective; original mixed parents preserved | All numerical implementation excluded | Detailed registration above; actual evidence required | PLANNED — NOT SELECTED | Engineering baseline preflight after owner selection
doc/roadmap/ULTIMATE_ROADMAP.md:6119 | HPR-P25-028 | DESIGN COMPLETE | Future runtime assigned 050–058; design is not executable completion
doc/model-remediation/RECONCILIATION.md:13680 | HPR-P25-028 | NEW — PLANNED | FOUNDATION_ACTIVE | Topology coordinates Assets/Custody/Workflow | 019/020/021/022 accepted designs | Independent foundation objective; original mixed parents preserved | All numerical implementation excluded | Detailed registration above; actual evidence required | PLANNED — NOT SELECTED | Engineering baseline preflight after owner selection
~~~~~~~~

<a id="hapi-541"></a>
## HAPI-541 — separate independent monitoring and action evidence

Phase: HPS-018 — Engineering Baselines and Configuration

Legacy IDs: HPR-P25-029

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

separate independent monitoring and action evidence

### 2. Existing Implementation

Independent immutable source stores and configuration definitions/values/feature-flag records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Detailed registration above; actual evidence required

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. A baseline is separate from a numerical run snapshot; no source revision may mutate an accepted historical manifest. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-029 — Independent operational state and action evidence preflight

Owners: Telemetry raw/trusted/binding/time-quality, Topology physical reference, Monitoring
supported issue/state views, Workflow human action authority, Analytics observed-outcome
consumer and Audit evidence through existing contracts.
Objective: preserve independent monitoring and human operational records by separating
mixed 005/006/012/013/014/015/016 scope. Evidence: actual trusted-reading trust/export,
measurement binding entities, Monitoring deviation owner validation and Workflow target
transition service; these do not establish a complete monitoring or learning pipeline.
Dependencies: 018 owner design, existing trusted source/query and independent 003/004
requirements; explicit source/quality/units/observation/permission requirements where needed.
No 011 simulation prediction, 025 execution, 026 residual or 028 numerical freeze dependency.
Deliverables: bounded measured-state association/view contract with UNKNOWN semantics,
non-simulator methods only if justified, authenticated human applied-action record,
observation windows/confounders and factual outcome provenance; per-owner minimal
implementation envelopes, no generic cross-module broad implementation authorization.
API/persistence: authorized read/bind/action/history contracts, quality/freshness/effective
binding and traceable action/time/source/evidence retention using existing owner stores.
Acceptance: cases F/G, units/point/asset identity mismatch, late/duplicate/stale/missing/
unqualified data, withdrawn owner permission, incompatible effective binding, source
loss/recovery and audit replay; no unsupported physical inference or attribution claim.
Exclude solver estimates, predicted-benefit/residual/calibration datasets, automatic
learning promotion, numerical scheduling and OT commands. Ordinary detector-learning
work may later proceed only under a separately scoped approved objective/evidence policy.
Exact message: `docs(operations): separate independent monitoring and action evidence`.
Selection/completion: separately selected two-document preflight and successful exact-head
Documentation CI; F3 needs subsequent actual implementation/verification.

~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: —. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `df42bd70657ed2229bdab05e19846ac69c8da2bc` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13502](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13502)
- [doc/model-remediation/RECONCILIATION.md:13681](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13681)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6120](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6120)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15242](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15242)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15421](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15421)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: DESIGN COMPLETE.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15421 | HPR-P25-029 | NEW — PLANNED | FOUNDATION_ACTIVE | Telemetry/Monitoring/Workflow/Audit/Analytics | 018 and specific current independent owner contracts | Independent foundation objective; original mixed parents preserved | All numerical implementation excluded | Detailed registration above; actual evidence required | PLANNED — NOT SELECTED | Independent monitoring/action preflight after owner selection
doc/roadmap/ULTIMATE_ROADMAP.md:6120 | HPR-P25-029 | DESIGN COMPLETE | Future runtime assigned 050–058; design is not executable completion
doc/model-remediation/RECONCILIATION.md:13681 | HPR-P25-029 | NEW — PLANNED | FOUNDATION_ACTIVE | Telemetry/Monitoring/Workflow/Audit/Analytics | 018 and specific current independent owner contracts | Independent foundation objective; original mixed parents preserved | All numerical implementation excluded | Detailed registration above; actual evidence required | PLANNED — NOT SELECTED | Independent monitoring/action preflight after owner selection
~~~~~~~~

<a id="hapi-542"></a>
## HAPI-542 — define engineering foundation acceptance cases

Phase: HPS-018 — Engineering Baselines and Configuration

Legacy IDs: HPR-P25-030

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

define engineering foundation acceptance cases

### 2. Existing Implementation

Independent immutable source stores and configuration definitions/values/feature-flag records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Detailed registration above; actual evidence required

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. A baseline is separate from a numerical run snapshot; no source revision may mutate an accepted historical manifest. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-030 — Engineering foundation acceptance design preflight

Owners: independent verification with Topology/Assets/Custody/Telemetry/Workflow owners.
Objective: isolate data acceptance from original 027 numerical qualification and 015/016
simulation-feedback demonstration. Dependencies: 018–022, 028 and 029 design outputs;
current source inventory/CI and explicit selection. No deferred numerical dependency.
Deliverables: exact test/env/authorization matrix for cases A–G, supported record/units/
profile/product/reference/history coverage, independent frozen data-round-trip expectations,
raw evidence collection, negative/concurrency/upgrade/import-export verification and
exact future test-file envelopes/messages. API/persistence requirements: exercise actual
supported write/read/revision/baseline/binding contracts, not direct SQL seeding as proof
of an operator workflow. Preserve old revisions and applied SQL; verify integrity/retention.
Acceptance of this design requires complete case-to-contract/test/owner mapping,
no numerical result prerequisite and exact-head Documentation CI. Case execution remains
future implementation/testing; no invented coordinate/manufacturer/product values except
explicitly synthetic fixtures with declared provenance. Exclude hydraulic acceptance,
provider comparisons, numerical fitting, frontend repository changes and operational imports.
Exact message: `docs(testing): define engineering foundation acceptance cases`.

~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: —. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `39ef5b7fcb96e3f6edd9f0b1d0a5739bbccca2d0` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13530](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13530)
- [doc/model-remediation/RECONCILIATION.md:13682](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13682)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6121](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6121)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15270](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15270)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15422](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15422)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: DESIGN COMPLETE.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15422 | HPR-P25-030 | NEW — PLANNED | FOUNDATION_ACTIVE | Cross-module verification | 018–022/028/029 design outputs | Independent foundation objective; original mixed parents preserved | All numerical implementation excluded | Detailed registration above; actual evidence required | PLANNED — NOT SELECTED | Foundation acceptance-case preflight after owner selection
doc/roadmap/ULTIMATE_ROADMAP.md:6121 | HPR-P25-030 | DESIGN COMPLETE | Future runtime assigned 050–058; design is not executable completion
doc/model-remediation/RECONCILIATION.md:13682 | HPR-P25-030 | NEW — PLANNED | FOUNDATION_ACTIVE | Cross-module verification | 018–022/028/029 design outputs | Independent foundation objective; original mixed parents preserved | All numerical implementation excluded | Detailed registration above; actual evidence required | PLANNED — NOT SELECTED | Foundation acceptance-case preflight after owner selection
~~~~~~~~

<a id="hapi-604"></a>
## HAPI-604 — Integrated closure: five authoritative-baseline/exchange packages

Phase: HPS-018 — Engineering Baselines and Configuration

Legacy IDs: HPR-P25-055

Priority: P1

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-609

Cross-Repository Dependencies: HWEB-F07, HWEB-F10 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-055; child tasks own implementation. five authoritative-baseline/exchange packages

### 2. Existing Implementation

Independent immutable source stores and configuration definitions/values/feature-flag records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver complete baseline manifests, atomic freeze, compare/history/reconstruction and typed aggregate exchange after owner source publication. The exact bounded remaining delivery is: Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-055; child tasks own implementation. five authoritative-baseline/exchange packages.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Freeze exact, authorized multi-owner engineering inputs and reconstruct historical truth.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A baseline is separate from a numerical run snapshot; no source revision may mutate an accepted historical manifest. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-055 — five authoritative-baseline/exchange packages

Prerequisites:accepted052/053/054, retained028 design and D3. Topology coordinates
references; each source owner remains authoritative. Reuse actual051 protected owner
contracts and Workflow approval; no implicit distributed transaction or competing masters.

| Planning stage | Remaining delivery | Evidence / exit criterion |
|---|---|---|
| HPR-P25-055A | Versioned manifest identity/draft and exact owner-reference/completeness validation. | Pin route/profile/sections/stations/units/characteristics/products/method/source/effective/approval IDs and digests; incomplete capture diagnoses missing facts and cannot freeze. |
| HPR-P25-055B | Atomic authoritative freeze/publication and idempotent conflict handling. | All source/approval/permission/current predicates checked under accepted participation, one expected-head/CAS result, complete rollback on missing/corrupt/unavailable owner; no half baseline. |
| HPR-P25-055C | Authorized exact read/history/effective selection, compare and reconstruction. | Reconstruct an old baseline after every source revises; preserve units/precision/provenance; separate withdrawn/current-ineligible from authorized historical facts; opaque cross-company denial. |
| HPR-P25-055D | Supported aggregate engineering import/export, dry-run and conflict-safe replay. | Version/size/type/digest/source/permission limits, lossless semantic round trip and dry-run without writes; admitted multi-owner writes use explicit atomic coordination, no generic document-upload substitute. |
| HPR-P25-055E | Multi-owner freeze/replay/rollback/concurrency and API/consumer qualification. | Same/different-content retries, stale head, corruption/outage/restricted packages and partial-capture faults cannot yield an authoritative baseline; exact contracts for F07/F09 and full evidence. |

No baseline->source-authoring prerequisite is introduced. Exchange packages state their
supported types and exclusions; unsupported source references cannot be repaired by defaults.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Preserve the original bounded objective and verify its claimed output against current artifacts. Freeze exact, authorized multi-owner engineering inputs and reconstruct historical truth.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-609. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23403](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23403)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1341](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1341)
- [doc/roadmap/ULTIMATE_ROADMAP.md:5174](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L5174)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-605"></a>
## HAPI-605 — Baseline manifest drafts and completeness

Phase: HPS-018 — Engineering Baselines and Configuration

Legacy IDs: HPR-P25-055A

Priority: P1

Implementation Status: PARTIAL

Planning Status: IN_PROGRESS

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-586, HAPI-593, HAPI-598

Cross-Repository Dependencies: HWEB-F07, HWEB-F10 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Versioned manifest identity/draft and exact owner-reference/completeness validation.

### 2. Existing Implementation

Independent immutable source stores and configuration definitions/values/feature-flag records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver complete baseline manifests, atomic freeze, compare/history/reconstruction and typed aggregate exchange after owner source publication. The exact bounded remaining delivery is: Versioned manifest identity/draft and exact owner-reference/completeness validation..

### 4. Functional Requirements

Pin route/profile/sections/stations/units/characteristics/products/method/source/effective/approval IDs and digests; incomplete capture diagnoses missing facts and cannot freeze.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A baseline is separate from a numerical run snapshot; no source revision may mutate an accepted historical manifest. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringBaselineManifest.java` — pure Java owned aggregate/value and invariants for manifest pins exact owner revisions/digests, effective selection, approval evidence and completeness.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/EngineeringBaselineManifestUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringBaselineManifestRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringBaselineManifestApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringBaselineManifestController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringBaselineManifestRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/topology/engineering-baselines` for manifest pins exact owner revisions/digests, effective selection, approval evidence and completeness. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Pin route/profile/sections/stations/units/characteristics/products/method/source/effective/approval IDs and digests; incomplete capture diagnoses missing facts and cannot freeze.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-586, HAPI-593, HAPI-598. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23411](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23411)
- [doc/model-remediation/RECONCILIATION.md:23749](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23749)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1057](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1057)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1349](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1349)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1057 | HPR-P25-055A | BLOCKED_SPECIFICATION | Baseline manifest drafts and completeness
doc/roadmap/ULTIMATE_ROADMAP.md:1349 | HPR-P25-055A | Versioned manifest identity/draft and exact owner-reference/completeness validation. | Pin route/profile/sections/stations/units/characteristics/products/method/source/effective/approval IDs and digests; incomplete capture diagnoses missing facts and cannot freeze.
doc/model-remediation/RECONCILIATION.md:23411 | HPR-P25-055A | Versioned manifest identity/draft and exact owner-reference/completeness validation. | Pin route/profile/sections/stations/units/characteristics/products/method/source/effective/approval IDs and digests; incomplete capture diagnoses missing facts and cannot freeze.
doc/model-remediation/RECONCILIATION.md:23749 | HPR-P25-055A | BLOCKED_SPECIFICATION | Baseline manifest drafts and completeness
~~~~~~~~


### HAPI-605 — one-time two-commit owner exception and draft contracts (2026-11-03)

**Owner decision and registered delivery boundary.** After selecting the entire HAPI-605 objective and approving its authorization policy, the owner explicitly authorized: "you can exceptionally this time commit in two steps". This decision overrides the single-commit/indivisible-delivery restriction in AGENTS.md sections 3.1 and 3.3 **for HAPI-605 only**. It does not alter the closed historical HAPI-602/S0–S7 sequence, create a task ID, authorize additional subdivisions, waive CI, or select HAPI-606..609. Exactly two planned delivery commits remain under the same HAPI-605 identity:
1. `feat(topology): add engineering baseline draft contracts` — immutable draft manifest, completeness rules, canonical codec and focused executable tests, with this execution record and exact source inventory.
2. `feat(topology): implement protected engineering baseline drafts` — the remaining whole-task application, owner-source capture, original-transaction authorization, persistence, REST and meaningful integration tests, with exact runtime paths registered here before publication. Same-task CI repairs remain repairs under the normal failure rule.

**Already approved security policy.** Resource `ENGINEERING_BASELINE_DRAFT`; permissions `topology:engineering-baseline:create`, `topology:engineering-baseline:read`, `topology:engineering-baseline:update`, `topology:engineering-baseline:validate`. Every action requires company AND pipeline scope. Each protected source additionally requires its existing source-owner read permission in the same original physical transaction. Baseline permissions grant no source access. This first commit introduces no resource registration, grants, native route, source admission, schema or endpoint; the policy is recorded for the second commit and is not claimed implemented.

**Exact first-commit allowlist, based on live main.** Parent `29ed0a686a0e82d9370b7383f85bc3bb2e319557`; parent `5775fda2327c42b1093e3f26459e902986486b7b`; tree `34abc9bdc8135d28ac4109cd66834ba93d9de2d6`. Exact-head Documentation #864 SUCCESS at https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41541348209. The implementation Full #1083 belongs to earlier source `06b6121a79c431f1452552ecf8ca9126c6f11e4a`, not this proposed commit.

CREATE only:
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringBaselineManifest.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/service/EngineeringBaselineCompletenessPolicy.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringBaselineManifestCodec.java`
- `src/test/java/dz/sh/hidra/modules/topology/qualification/EngineeringBaselineManifestChecks.java`
- `src/test/java/dz/sh/hidra/modules/topology/qualification/EngineeringBaselineManifestContractTest.java`

MODIFY only:
- `.github/ci-test-inventory.json` — add the new 22-case JVM suite and its abstract shared-assertion support with actual current source SHA-256; preserve every existing test entry, case, timing and shard.
- `doc/roadmap/TASKS_HPS-018.md` — this append-only technical registration and HAPI-605 status header only.
- `doc/roadmap/ULTIMATE_TASKS.md` — compact execution pointer only.
- `doc/roadmap/ULTIMATE_ROADMAP.md` and `doc/roadmap/ULTIMATE_PHASES.md` — HAPI-605 status cells only, PARTIAL / IN_PROGRESS.

DELETE none. No changes to existing Java, migrations, workflows, architecture allowlists, API/dictionary snapshots or reconciliation history. Independently inspected the live recursive tree (not truncated); all five CREATE paths are absent. Existing unpublished Identity/schema/consumer-test candidates are excluded from this commit. Their presence locally does not establish implementation or runtime acceptance.

**Bounded behavior and ownership.** Topology owns immutable manifest identity, company/pipeline scope, monotonically increasing draft revision, explicit half-open effective interval and exact owner/source/revision/SHA-256 selections. Identity strings preserve exact valid Unicode without normalization. Duplicate source selections, inconsistent revisions, malformed identities/digests, invalid temporal precision and version overflow fail. Pure Java completeness evaluates exact supplied source observations and owner-derived network/station/unit membership; it reports missing roots, membership, coverage, method and approval facts without inventing them. Observations are data, never authorization evidence. The application must obtain them through actual protected owners in the second commit. Deterministic bounded V1 encoding preserves all draft fields; decoding rejects malformed UTF-8, noncanonical order/markers, invalid counts, truncation and trailing bytes. Draft completeness never confers qualification, freeze, publication or operational authority.

**Executed checks and CI-first limits.** On the available OpenJDK 17.0.20, `java --module jdk.compiler/com.sun.tools.javac.Main` compiled the three production classes and shared assertion support into scratch validation output; `java -cp <validation-output> dz.sh.hidra.modules.topology.qualification.EngineeringBaselineManifestChecks` executed **22 shared assertion cases PASS**. These include immutable/version behavior, exact source membership and digest matching, missing facts, interval coverage, canonical round-trip with Unicode, every truncated prefix, malformed input and configured bounds. This is isolated Java17 execution, not Java21/Maven/JUnit/full application/PostgreSQL proof. Maven is unavailable; no environment was provisioned. The JUnit entry points invoke the same assertion bodies. Stale checkpoint test names/hashes were corrected from actual source bytes; no old case was removed. New suite timing is zero (unmeasured), not fabricated runtime evidence. Full-repository validators and architecture tests await hosted CI because this workspace is a partial materialization.

**Acceptance disposition and remaining work.** HAPI-605 is PARTIAL / IN_PROGRESS after the first commit, never completed or CI-passed by this record. Commit 2 must deliver genuine owner-derived network/route/profile/section/station/unit/characteristic/product/method/approval capture; company/pipeline and independent source authorization; original-transaction final guards and actor binding; immutable draft history, expected-version/idempotency/conflict, rollback and indeterminate commit-ack semantics; explicit REST DTOs/mappings/errors, trusted route registration, applicable fresh-schema/Flyway/JPA/dictionary evidence, and configured HTTP/PostgreSQL denial/revocation/concurrency tests. Exact runtime scope must be reconciled against the then-current main and registered here. No independent engineering calibration, live IdP, UI/UAT, OT, SITE, numerical/P3 or deployment acceptance is implied. HAPI-606 freeze remains unselected.

**Publication gate.** Verify exact parent/tree/blob preservation and expected-head guarded direct main update. Confirm applicable HidraAPI CI and Documentation Validation created/queued/started on the actual published SHA, report their links/states and STOP without waiting for completion or starting commit 2. Its acceptance depends on fresh CI feedback; do not extend the planned two delivery commits into additional preflight stages.


### HAPI-605 — current runtime implementation and third exceptional delivery (2026-11-03)

**New explicit owner decision; this record controls delivery count.** The owner directed: "commit the current implantation, document the remaining implementation for a third exceptional stage". This authorizes publication of the present partial runtime work and **one third exceptional delivery under HAPI-605**. It supersedes the earlier exactly-two-delivery limit for this task only and the default indivisible-delivery restriction to that extent. It does not change the closed HAPI-602/S0–S7 sequence, create another task ID, waive security/CI, select HAPI-606..609, authorize further subdivisions or claim the present task complete.

| Delivery | Registered message | Disposition |
|---|---|---|
| 1 | `feat(topology): add engineering baseline draft contracts` | Published `37ef6bcfbc4c7880e7c6bc1ced743588f6ca7e98`; exact-SHA Full #1084 and Docs #865 SUCCESS |
| 2 — this publication | `feat(topology): implement protected engineering baseline drafts` | Current partial runtime implementation; Java21/application/PostgreSQL execution pending; HAPI-605 remains PARTIAL / IN_PROGRESS |
| 3 — remaining exceptional delivery | `feat(topology): complete protected engineering baseline drafts` | Owner-authorized remaining implementation and qualification; not executed by this publication |

**Live parent and review evidence.** Refreshed main `37ef6bcfbc4c7880e7c6bc1ced743588f6ca7e98`, parent `29ed0a686a0e82d9370b7383f85bc3bb2e319557`, tree `4031f7db41b9aca3e3b408132eee8832aff27b25`. Full #1084 https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41603419113 and Docs #865 https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41603418955 both completed successfully. This is acceptance evidence for delivery 1 only, never execution evidence for delivery 2. AGENTS, current canonical roadmap/task index/owning record, exact source contracts, existing qualification fixtures, migration chain and workflows were inspected. The legacy reconciliation file returned empty content at the retrieved baseline and is preserved.

**Exact delivery-2 CREATE allowlist:**
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/topology/TopologyBaselineProductSourceContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/TopologyBaselineProductSourceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/topology/TopologyEngineeringBaselineAuthorizationContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedEngineeringBaselineAuthorizationParticipant.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/EngineeringBaselineDraftApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringBaselineDraftController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/EngineeringBaselineDraftRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/EngineeringBaselineDraftRequests.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/EngineeringBaselineDraftResponses.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/model/EngineeringBaselineDraftFailure.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/EngineeringBaselineDraftUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringBaselineDraftRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringBaselineSourcePort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringBaselineDraftApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/ProtectedEngineeringBaselineAdmissionCoordinator.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/integration/EngineeringBaselineSourceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringBaselineDraftCatalog.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringBaselineDraftRepositoryAdapter.java`
- `src/main/resources/db/migration/V20261103_001__topology_engineering_baseline_drafts.sql`
- `src/test/java/dz/sh/hidra/modules/topology/qualification/EngineeringBaselineDraftNativeHttpPostgresIntegrationTest.java`

**Exact delivery-2 MODIFY allowlist:**
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TopologyManufacturerCharacteristicReferenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringBaselineManifest.java`
- `src/main/java/dz/sh/hidra/platform/permissions/HidraTrustedNativeProtectedRouteRegistry.java`
- `src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/platform/permissions/HidraTrustedNativeProtectedRouteRegistryTest.java`
- `src/test/java/dz/sh/hidra/platform/permissions/HidraTrustedNativeProtectedRouteStartupValidatorTest.java`
- `.github/ci-test-inventory.json`
- `doc/roadmap/TASKS_HPS-018.md`
- `doc/roadmap/ULTIMATE_TASKS.md`

DELETE none. This is 20 CREATE and 11 MODIFY paths. Preserve all earlier Flyway scripts, existing installed-equipment test sources/cases, source inventories outside these exact edits, workflow definitions and generated OpenAPI/dictionary bytes. The locally materialized source-reference fixtures and older unpublished consumer-test edits are excluded. ROADMAP/PHASES stay PARTIAL / IN_PROGRESS without any status promotion.

**Implemented source, not established runtime acceptance.** Identity now has the distinct baseline resource/action contract, genuine private prepared/admitted/read handles, company AND pipeline checks, independent source-owner permission evaluations, actor/thread/physical connection/PID/XID/role binding, native temporal deadline and SQL-free final marker. Source descriptors are requests, not caller-supplied permission attestations. Authorization rechecks remove only the advancing `context.evaluatedAt` observation from graph-equality comparisons and evaluate each fresh sample within the original native deadline. No source grant is implied by a baseline right. The new migration defines the four owner-approved permission codes, with no role/user assignments.

Topology now has application/source/storage ports, a coordinator and post-commit service, exact source capture adapters, immutable draft/revision/request persistence, expected-version and exact-request replay handling, explicit transport DTOs/mapper/errors, and five independently fixed native route tuples: POST collection, PUT/GET manifest, GET exact revision, POST validation. All returned manifests remain unqualified, non-authoritative and unfrozen. The source implementation captures canonical network publication/graph/survey references, station configuration/installation witnesses, characteristic target digests/approval presence and existing Simulation manufacturer evidence. Custody owns its separate product-publication reader/decoder and method/input/approval observations; Topology does not import Custody persistence or domain types.

The additive migration defines three baseline tables, immutable-history/head/original-transaction guards and three private fixed source-lock functions. These functions return no source data or business permission; PUBLIC execution is revoked and deployment grants remain explicit. Platform adds exact compiled baseline callers, four pinned DML statement digests and source/final-preparation hooks; existing caller/hash branches remain. The final guard candidate checks original coordinates/role/time, retained relation locks, exact draft/revision/receipt content, credential separation, basic relation shape, six function-body hashes and nine trigger contracts. This description is source-backed capability intent, not proof that the assembled protocol has run or that catalog coverage is complete.

**Actual local checks.** Available OpenJDK 17.0.20 parsed 54 locally materialized Java sources without syntax errors; this includes reference files and is not full-repository/type verification. The compiler module compiled the framework-free manifest/policy/codec, new application/source contracts, DTOs/mapper and catalog constant. The same standalone manifest harness executed **22 shared assertion cases PASS** after the added diagnostic categories. Final staged parsing also checked all **27 Java files** in the publication scope without syntax errors; staged source identities and the four changed/new test inventory entries were checked separately. No Maven, Java21, Spring application, HTTP or PostgreSQL run was performed locally; no missing environment was provisioned.

A new isolated native PostgreSQL fixture contains **21 unexecuted cases**: two independent/single-use native admissions; six baseline scope/source/actor/forgery cases; thirteen draft HTTP/storage cases covering incomplete/unqualified output, replay, stale version/history, authorization before existence, separate action rights, source denial, revocation/foreign company, unauthenticated requests, rollback, genuine post-COMMIT acknowledgement loss, immutable/raw-access rejection, source-lock contention and final-catalog drift. Isolation preserves the existing installed-equipment suite and its historical case membership. Architecture gains one exact baseline-export negative/positive case; Platform gains one exact five-route tuple case and updates startup expectations from eight to thirteen routes while retaining the eight legacy tuple assertions. The CI inventory adds the real new suite with unmeasured timing zero and updates only the three changed existing test-source hashes/case lists. No hosted test result or measured duration is fabricated.

**Remaining delivery 3: concrete implementation and acceptance obligations.**
1. First inspect delivery-2 exact-SHA CI. Repair its responsible failures under HAPI-605 before acceptance. Keep fail-closed permissions, the native deadline, one-use handles, executor phase checks, immutable DML and existing cases intact.
2. Complete source-backed **positive source journeys**. The present added HTTP cases predominantly use empty/incomplete manifests and deny unauthorized source reads. They do not prove a full authorized network + survey/section + station + unit/characteristic/manufacturer + approved product/method capture. Exercise real owner-created sources through the baseline API and PostgreSQL; prove common PID/XID/actor, independent permissions, exact canonical revisions/digests and historical reconstruction after owner revisions. Add positive original-transaction Custody/Simulation reader coverage. A Boolean completeness result is not a substitute for these cases.
3. Finish the source-integrity/semantics review and implement corrections it reveals: source scalar/canonical consistency, company/pipeline/station/unit membership, approved-link/Workflow references, method/source/unit applicability diagnostics, required survey/section coverage and source effective windows. Explicitly test withdrawal/reactivation and replacement boundaries; current publication interval selection uses the first publication and first later conflicting transition and does not yet demonstrate reactivation-window handling. Preserve captured findings separately from fresh findings and never convert missing physical facts into defaults or engineering qualification.
4. Add genuine competing baseline writers and exact-request replay races, expiry/revocation during an admitted transaction, all-or-nothing multi-owner failure and final-guard denial tests. Existing lock-contention/rollback/indeterminate-ack cases are useful source but do not prove those missing concurrent/temporal journeys.
5. Complete database/catalog assurance, including relevant PK/FK/CHECK/index/constraint and privilege coverage beyond the current function/trigger predicate; run actual fresh-schema Flyway validation and existing JPA validation. After this migration is published, preserve it; any required DDL repair must use a newly checked forward migration, not rewrite its history.
6. Obtain actual hosted runtime OpenAPI and migrated-catalog artifacts. Reconcile the five operations, DTO/error/native-security contract and the three tables/permissions against those artifacts; update `doc/api/openapi.yaml`, `doc/database/DATA_DICTIONARY.md` and `.github/documentation-validation.json` only from genuine generated evidence through their current generators. Current generated files are preserved, not represented as regenerated.
7. Run applicable Full/Docs gates on the final implementation SHA and independently reconcile the actual required cases, source hashes, raw PostgreSQL execution and API/schema artifacts. Only then consider HAPI-605 software completion. Engineering calibration, supplier assurance, live IdP deployment, UI/UAT, OT, SITE and release remain separate.

**Delivery-3 file envelope and sequence.** Same task/message above; prerequisites are delivery-2 publication and refreshed exact-SHA CI/governance. Reuse and amend the exact production/migration/native-suite/architecture/Platform files listed for delivery 2 as evidence requires. Proposed focused CREATE tests, to register their final exact case scope before execution: `src/test/java/dz/sh/hidra/modules/topology/qualification/EngineeringBaselineSourceNativePostgresIntegrationTest.java`, `src/test/java/dz/sh/hidra/modules/topology/application/service/EngineeringBaselineDraftApplicationServiceTest.java`, `src/test/java/dz/sh/hidra/modules/topology/api/rest/EngineeringBaselineDraftRestMapperTest.java`. Existing owner-native publication fixtures may be extended only for the corresponding baseline-source acceptance cases after precise source review. A necessary new additive migration filename remains subject to the then-live chain; no old migration edit is authorized. Evidence writes belong here and in the compact index; ROADMAP/PHASES status cells only when justified. CI inventory/source/schema/API artifacts must match actual files and actual runtime output. No additional numbered delivery or preflight is admitted.

**Publication/stop.** Owner explicitly requests this current partial commit. Publish directly to existing main with an expected-head guard; verify exact parent/tree/blobs, no deletions and preservation outside the allowlist. Confirm the applicable new exact-SHA CI/Docs runs created/queued/started and STOP. Do not wait for completion or execute delivery 3 in this turn. This record leaves HAPI-605 PARTIAL / IN_PROGRESS and records missing acceptance rather than closing it from syntax checks.


### HAPI-605 — third exceptional delivery source/qualification work (2026-11-03)

**Authorization and exact previous acceptance.** This is the third and last owner-approved exceptional HAPI-605 delivery, with registered message `feat(topology): complete protected engineering baseline drafts`. The immediately preceding `main` source is `6ee317a1667e12a3afb5d72518290fec0b635075`, parent `8ef61841253bab0c8926191531272a9c8bcbed66`, tree `ef429d2ccd8c8f3a601e3eac01cbe93d0514a717`. CI Full #1089 (https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41626002671) and Documentation #870 (https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41626002793) completed SUCCESS on the identical preceding SHA. All 14 PostgreSQL shards including shard 13 and the final verification gate succeeded, while the legacy unpartitioned Java verification job was skipped by the existing partitioned policy. These are acceptance for the preceding source only.

**Bounded existing-file modifications, with no deletions:**
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/integration/EngineeringBaselineSourceAdapter.java` — derive network-publication source validity from the latest PUBLISH/REACTIVATE ACTIVE event at or before the manifest effective start, preserving the earlier historical interval and withdrawal gap; never treat a withdrawn gap as continuous availability.
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/topology/TopologyBaselineProductSourceContract.java` and `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/TopologyBaselineProductSourceAdapter.java` — carry the manifest effective start through the public owner contract and select the corresponding immutable product-publication activation window; preserve the independent original-transaction and physical Identity/Custody permission gates.
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringBaselineDraftCatalog.java` — extend the original-transaction final guard to require three validated composite primary keys, four validated foreign keys, and no unvalidated constraints on the three baseline owner tables. Do not modify the already-published Flyway V20261103_001 migration, lock-function hashes or triggers.
- `src/test/java/dz/sh/hidra/modules/topology/qualification/EngineeringBaselineDraftNativeHttpPostgresIntegrationTest.java` — retain all original 21 native cases; add concurrent identical-request replay, competing first-writer and expected-version updates, physical PK/FK/catalog validation, and fail-closed FK removal/restore.
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/EngineeringRevisionPublicationPostgresIntegrationTest.java` and `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/ProductRevisionPublicationNativeHttpPostgresIntegrationTest.java` — add actual owner PUBLISH→WITHDRAW→REACTIVATE PostgreSQL event tests to corroborate the as-of selection and retained source history.
- `.github/ci-test-inventory.json` — register the exact new and revised test cases, class paths, and SHA-256 computed from final source bytes; preserve the existing shard count, other suites, timings and fallback.

**One focused CREATE, within the approved delivery-3 proposed tests:**
- `src/test/java/dz/sh/hidra/modules/topology/api/rest/EngineeringBaselineDraftRestMapperTest.java` — six Java21/JUnit contract cases for bounded requests, expected version, exact historical access, separate captured/current completeness, strict input, and permanently unqualified responses.

**Governance:** modify only this owning task record and the compact `doc/roadmap/ULTIMATE_TASKS.md` execution pointer. Leave `ULTIMATE_ROADMAP.md` and `ULTIMATE_PHASES.md` at PARTIAL / IN_PROGRESS, and preserve empty legacy reconciliation content and all unrelated source. No production role grants, autonomous actions, numerical/P3 or HidraWEB implementation are authorized.

**Validation and limitations (prepublication):** The above changes are source-reviewed and inventories tied to prepared Git blobs. Existing exact-head CI #1089 and Docs #870 are passing evidence for the parent commit only; they do **not** prove these changes. No new Maven, actual Java21/JUnit, PostgreSQL, application HTTP, fresh-schema or exact-commit CI execution is claimed before publication. The Java21 runtime is available to the assistant, but Maven/PostgreSQL services are not provisioned; follow the repository CI-first policy without installing infrastructure. Relevant native tests require GitHub-hosted execution before acceptance. Current generated `doc/api/openapi.yaml` includes the five draft operations; schema/dictionary provenance for this new source remains an independent hosted-artifact comparison, not a newly generated file in this delivery.

**Open software-acceptance gates: do not silently close HAPI-605.** The new native cases prove bounded negative and concurrency paths only if the new CI succeeds. A complete original-transaction *positive* source journey covering network + survey/sections + station/unit + approved manufacturer/characteristic + approved Custody product/method with exact digests remains to be independently matched and executed. Additional withdrawal/approval, expiry/revocation during the admitted transaction, multi-owner atomic failure, final-guard catalog completeness, generated contract/dictionary reconciliation, and any actual API/DB contract issues found by CI remain gates. Do not infer these from other isolated owner suites or 21 passing preliminary baseline tests. HAPI-605 stays **PARTIAL / IN_PROGRESS** until the complete task acceptance matrix is supported. No fourth exceptional delivery, separate preflight or successor task is automatically authorized; remaining work would require an explicit owner-governance decision or a same-task repair prompted by real CI failures.

**Publication protocol.** Publish as a single third-delivery commit directly to existing main with expected-head guard and exact registered message. Verify one parent, tree and changed blobs, no unrelated changes. Observe new exact-SHA Full/Documentation runs once they are queued or started; then STOP rather than waiting or advancing HAPI-606.


### HAPI-605 — owner-approved final acceptance-completion delivery registration (2026-11-03)

**Owner authorization.** Explicit instruction: "Approved: authorize one final HAPI-605 acceptance-completion delivery under the existing task, with exact scope and commit message registered before implementation. Do not start HAPI-606." This supersedes the prior three-delivery maximum **once and only once**. It admits one final bounded same-task implementation/acceptance commit, not a fourth *additional* substage sequence or automatic HAPI-606. The prior third delivery is `866e22502523866b744acb6b7de8431deae13bba` (parent `6ee317a1667e12a3afb5d72518290fec0b635075`, tree `0a0a19da91aca49b311e056dd723149808de545a`), with exact-SHA Full CI #1090 and Documentation #871 both SUCCESS. The existing HAPI-605 status is PARTIAL / IN_PROGRESS until all remaining required behaviors and hosted evidence are independently verified.

**Exact registered commit message:** `test(topology): qualify protected engineering baseline source acceptance`.

**Exact, bounded MODIFY allowlist (no other files):**
1. `src/main/java/dz/sh/hidra/modules/topology/infrastructure/integration/EngineeringBaselineSourceAdapter.java`: enforce exact canonical/source scalar consistency, graph/survey required membership and coverage, station/installation membership/interval consistency, and retained-withdrawn source facts. Keep original PID/XID/actor/role checks and native source authorization.
2. `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/TopologyBaselineProductSourceAdapter.java`: maintain exact Custody owner-approved publication, source input/method diagnostics and effective-window provenance; no synthetic qualified defaults or authority inheritance.
3. `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringBaselineDraftCatalog.java`: verify actual PK/FK/CHECK/catalog identity and privately guarded roles, not mere counts, without rewriting any Flyway history.
4. `src/test/java/dz/sh/hidra/modules/topology/qualification/EngineeringBaselineDraftNativeHttpPostgresIntegrationTest.java`: add explicit positive source-based native HTTP create/read/validate/history with captured versus current diagnostics, independent source permission, all-or-nothing failures, original PID/XID/rollback, exact-request replay/expiry/revocation and retained authorization checks; retain the existing 26 cases.
5. `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/EngineeringRevisionPublicationPostgresIntegrationTest.java`: owner-created network graph/survey/effective history interoperability and reactivation/withdrawal/retained revision verification; preserve existing cases.
6. `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/ProductRevisionPublicationNativeHttpPostgresIntegrationTest.java`: owner-created product/method/approval/effective history interoperability, including native approval evidence; preserve existing cases.
7. `.github/ci-test-inventory.json`: exact changed test membership, SHA-256 and scheduling annotations, no unrelated shards.
8. `doc/roadmap/TASKS_HPS-018.md`: this pre-implementation registration, exact post-publication evidence and missing acceptance matrix.
9. `doc/roadmap/ULTIMATE_TASKS.md`: compact execution pointer and true status.

**Generated-evidence-only conditional MODIFY allowlist:** `doc/api/openapi.yaml`, `doc/database/DATA_DICTIONARY.md`, `.github/documentation-validation.json` solely if genuine exact-SHA hosted generated files can be fetched, verified and reconciled with the current canonical generator and manifest; otherwise do not edit generated files and report the gate. **CREATE/DELETE:** none. No modifications to `V20261103_001__topology_engineering_baseline_drafts.sql`, any prior migration, Identity/Workflow public contracts, producer authorization, Spring configuration, CI workflow or HidraWEB.

**Acceptance required before closing.** Real source-published network + survey/sections + station/unit/approved manufacturer/characteristic + approved Custody product/method, independent company/pipeline/source grants, exact original transaction identity and opaque admission, safe replay/CAS and historical reconstruction; fail-closed denial on missing/foreign/moved/withdrawn/replaced/stale/incomplete/unapproved source; lost-acknoledgement, concurrent writer, revocation/expiry at final guard, multi-owner all-or-nothing; fresh-schema Flyway/JPA, catalog integrity and actual hosted OpenAPI/database artifacts. Existing isolated producer suites cannot substitute for the composed positive path. No numerical qualification, physical calibration, SITE/OT or deployment acceptance is implied.

**Execution / stop.** Register this scope/message before source mutation, implement only what is supported by the current SHA, preserve source and native security guard invariants, document any incomplete criterion truthfully. Publish directly to existing `main` with expected-head guard and **one commit** under the exact message above. Verify parent/tree/blobs, query new exact-SHA Full/Docs CI created/queued/started, then STOP without waiting. If acceptance cannot be established, leave HAPI-605 PARTIAL / IN_PROGRESS and HAPI-606 NOT_IMPLEMENTED / BLOCKED; no fifth delivery is preapproved.


**Implementation prepared for this single delivery (before hosted qualification).** Exact bounded source changes are:
- Network canonical publication pin now checks stored `draft_id` and `draft_version` as well as exact context/bytes/SHA256/format, and reports any authored graph diagnostic instead of only disconnected-graph findings. Station configuration pin now checks stored `effective_from`/`effective_until` against decoded canonical evidence, and every installed-unit event's exact unit/position/period/evidence reference against the pinned installation witness.
- Custody source verifies stored `candidate_digest` against the Workflow-approved digest retained in the immutable canonical publication, before reporting independently authorized product facts. All original backend PID, XID, principal and time checks remain.
- Baseline final catalog now requires the exact three composite primary-key column arrays and four foreign-key source/target column arrays, in addition to the existing count/validation, owner isolation, trigger/function hashes and held source relation locks. Published Flyway migrations and production authorizations are unchanged.
- Owner-produced Topology network positive native HTTP path creates a real owner publication with authored graph/survey evidence, then baseline create/replay/validation/historical reconstruction with original backend PID/XID receipt equality, independent second Custody source denial without partial draft, and a foreign owner publication replacement followed by a protected history read revocation.
- Owner-produced Custody Workflow-approved product positive native HTTP path creates a real approved publication, then baseline capture with original backend PID/XID receipt equality and preserved approval diagnostic. Withdrawal changes only fresh completeness, while a new manifest after reactivation selects the resumed source interval. No missing method is silently supplied.
- The existing isolated native baseline suite adds a falsified FK with the same total FK count: the final guard must reject, release and restore the correct FK, and preserve zero draft/revision/request rows.

**Software acceptance matrix at prepublication review:**

| Required criterion | Evidence in this delivery | Disposition before new exact-SHA CI |
|---|---|---|
| Genuine owner-created Topology network and authored survey into protected baseline HTTP | New owner-native integration case | Prepared, CI NOT YET RUN |
| Genuine Workflow-approved Custody product into protected baseline HTTP | New owner-native integration case | Prepared, CI NOT YET RUN |
| Baseline company/pipeline plus independent source read and deny-before-protected-existence | Existing 26 native tests plus native multi-source negative test | Prepared, CI NOT YET RUN |
| Same original backend PID/XID for baseline revision and receipt, exact replay/history | New owner-native assertions plus existing native regression | Prepared, CI NOT YET RUN |
| Withdrawal/reactivation, fresh versus captured diagnostics, request replay and immutable source | New Custody and existing network native cases | Prepared, CI NOT YET RUN |
| Exact PK/FK identity, source scalar/digest, no constraint-count substitution | Hardened source/catalog and added native regression | Prepared, CI NOT YET RUN |
| Combined positive network + survey/sections + station + unit/approved manufacturer characteristic + approved Custody product/method **in one baseline transaction** | No combined owner-produced all-family fixture has been demonstrated | **OPEN — cannot claim software completion** |
| Expiry/revocation *during* admitted original multi-owner transaction; moving/withdrawn characteristic approval and cross-owner atomic rollback | Isolated native rollback/lock and post-commit revocation do not prove every joined case | **OPEN** |
| Runtime OpenAPI and fresh-migrated database dictionary reconciliation | Parent CI #1090 generated OpenAPI artifact `13229561832` and DB catalog artifact `13229605844`; no new source SHA artifacts yet, and no safe generator-backed replacement was performed | **OPEN; do not fabricate generated files** |
| Hosted Java21/JUnit, Flyway fresh PostgreSQL, JPA validation, 14 shards and Docs on new SHA | Parent CI #1090 / Docs #871 success; new runs not observed until publication | **PENDING new exact-SHA CI** |

**Verified genuine parent-run artifact comparison (no generated-file mutation).** Full #1090 at exact source `866e22502523866b744acb6b7de8431deae13bba` produced OpenAPI artifact `13229561832` (member `hidra-api.json`, SHA256 `6ecd8bb50d840e0ae01b318bb5b8a225511cca25576d2cb115fdfbfe8740a227`) and migrated PostgreSQL artifact `13229605844` (`schema.json` SHA256 `3edff74c6b2fad92454936c90ed81670805a6f1897c0c218b9c43677347e71ad`, `DATA_DICTIONARY.md` SHA256 `451ac57b001bf72005b5b4a82e81571c4cdb47a3628048aaf2aab0237678f1dd`). Actual generated JSON contains five operations in four baseline paths. PostgreSQL 16.15 catalog contains 196 migrations, 470 JPA mappings and 547 relations with zero unresolved owners, exact three baseline composite PKs and four FKs. The generated dictionary's canonical Git blob is `c0fbf0fecd41c6ddb060bbddbfbc729334f31ecb`; substituting only its 40-character captured-source SHA with the earlier `1b96cdc696ed08c69c8fd5b8dc566014abc5e8ed` reproduces the unchanged repository dictionary blob `aff12e2d0bb4fd81e1193e44c49ae58f1b726fa7` byte-for-byte. Compacting the generated OpenAPI to the same sorted canonical JSON and substituting only the older captured-source SHA `adeda546c60a7f148afb27bbcde2ad6176456d9c` reproduces checked-in OpenAPI blob `22f943b76722ed636d54971ff995a9a480213c3a`. Thus this parent-run comparison finds **provenance-only metadata drift, not a business-contract or physical-schema divergence**. Do not manually replace provenance in either generated file: this delivery leaves controllers and published Flyway unchanged, and the new commit's own runtime CI must generate its own exact-SHA evidence.

**Status and gating.** Owner-approved final delivery is publication of this bounded code and acceptance evidence, **not authority to misstate a missing combined positive journey**. Keep HAPI-605 PARTIAL / IN_PROGRESS unless all rows above are evidenced. Do not start HAPI-606 (NOT_IMPLEMENTED / BLOCKED), change owner governance, apply silent calibration defaults or grant industrial/operational qualifications. CI failure, if any, is a same-task repair issue and not a fifth delivery. Any unclosed acceptance beyond the final delivery must be reported for an explicit owner disposition, not automatically split or silently waived.


<a id="hapi-606"></a>
## HAPI-606 — Atomic authoritative baseline freeze

Phase: HPS-018 — Engineering Baselines and Configuration

Legacy IDs: HPR-P25-055B

Priority: P1

Implementation Status: NOT_IMPLEMENTED

Planning Status: BLOCKED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-605

Cross-Repository Dependencies: HWEB-F07, HWEB-F10 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Atomic authoritative freeze/publication and idempotent conflict handling.

### 2. Existing Implementation

Independent immutable source stores and configuration definitions/values/feature-flag records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver complete baseline manifests, atomic freeze, compare/history/reconstruction and typed aggregate exchange after owner source publication. The exact bounded remaining delivery is: Atomic authoritative freeze/publication and idempotent conflict handling..

### 4. Functional Requirements

All source/approval/permission/current predicates checked under accepted participation, one expected-head/CAS result, complete rollback on missing/corrupt/unavailable owner; no half baseline.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A baseline is separate from a numerical run snapshot; no source revision may mutate an accepted historical manifest. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringBaselineFreeze.java` — pure Java owned aggregate/value and invariants for atomic freeze across current owner predicates with one expected head and rollback of every provisional write.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/EngineeringBaselineFreezeUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringBaselineFreezeRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringBaselineFreezeApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringBaselineFreezeController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringBaselineFreezeRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/topology/engineering-baselines/{id}/freeze` for atomic freeze across current owner predicates with one expected head and rollback of every provisional write. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- All source/approval/permission/current predicates checked under accepted participation, one expected-head/CAS result, complete rollback on missing/corrupt/unavailable owner; no half baseline.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-605. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23412](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23412)
- [doc/model-remediation/RECONCILIATION.md:23750](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23750)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1058](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1058)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1350](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1350)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1058 | HPR-P25-055B | BLOCKED_SPECIFICATION | Atomic authoritative baseline freeze
doc/roadmap/ULTIMATE_ROADMAP.md:1350 | HPR-P25-055B | Atomic authoritative freeze/publication and idempotent conflict handling. | All source/approval/permission/current predicates checked under accepted participation, one expected-head/CAS result, complete rollback on missing/corrupt/unavailable owner; no half baseline.
doc/model-remediation/RECONCILIATION.md:23412 | HPR-P25-055B | Atomic authoritative freeze/publication and idempotent conflict handling. | All source/approval/permission/current predicates checked under accepted participation, one expected-head/CAS result, complete rollback on missing/corrupt/unavailable owner; no half baseline.
doc/model-remediation/RECONCILIATION.md:23750 | HPR-P25-055B | BLOCKED_SPECIFICATION | Atomic authoritative baseline freeze
~~~~~~~~

<a id="hapi-607"></a>
## HAPI-607 — Exact baseline read, compare and reconstruction

Phase: HPS-018 — Engineering Baselines and Configuration

Legacy IDs: HPR-P25-055C

Priority: P1

Implementation Status: NOT_IMPLEMENTED

Planning Status: BLOCKED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-606

Cross-Repository Dependencies: HWEB-F07, HWEB-F10 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Authorized exact read/history/effective selection, compare and reconstruction.

### 2. Existing Implementation

Independent immutable source stores and configuration definitions/values/feature-flag records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver complete baseline manifests, atomic freeze, compare/history/reconstruction and typed aggregate exchange after owner source publication. The exact bounded remaining delivery is: Authorized exact read/history/effective selection, compare and reconstruction..

### 4. Functional Requirements

Reconstruct an old baseline after every source revises; preserve units/precision/provenance; separate withdrawn/current-ineligible from authorized historical facts; opaque cross-company denial.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A baseline is separate from a numerical run snapshot; no source revision may mutate an accepted historical manifest. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringBaselineReconstruction.java` — pure Java owned aggregate/value and invariants for exact authorized historic reconstruction, effective selection, comparison and deterministic cursors.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/EngineeringBaselineReconstructionUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringBaselineReconstructionRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringBaselineReconstructionApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringBaselineReconstructionController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringBaselineReconstructionRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `GET /api/v1/topology/engineering-baselines/{id}` for exact authorized historic reconstruction, effective selection, comparison and deterministic cursors. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Reconstruct an old baseline after every source revises; preserve units/precision/provenance; separate withdrawn/current-ineligible from authorized historical facts; opaque cross-company denial.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-606. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23413](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23413)
- [doc/model-remediation/RECONCILIATION.md:23751](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23751)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1059](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1059)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1351](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1351)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1059 | HPR-P25-055C | BLOCKED_SPECIFICATION | Exact baseline read, compare and reconstruction
doc/roadmap/ULTIMATE_ROADMAP.md:1351 | HPR-P25-055C | Authorized exact read/history/effective selection, compare and reconstruction. | Reconstruct an old baseline after every source revises; preserve units/precision/provenance; separate withdrawn/current-ineligible from authorized historical facts; opaque cross-company denial.
doc/model-remediation/RECONCILIATION.md:23413 | HPR-P25-055C | Authorized exact read/history/effective selection, compare and reconstruction. | Reconstruct an old baseline after every source revises; preserve units/precision/provenance; separate withdrawn/current-ineligible from authorized historical facts; opaque cross-company denial.
doc/model-remediation/RECONCILIATION.md:23751 | HPR-P25-055C | BLOCKED_SPECIFICATION | Exact baseline read, compare and reconstruction
~~~~~~~~

<a id="hapi-609"></a>
## HAPI-609 — Multi-owner baseline and exchange qualification

Phase: HPS-018 — Engineering Baselines and Configuration

Legacy IDs: HPR-P25-055E

Priority: P1

Implementation Status: NOT_IMPLEMENTED

Planning Status: BLOCKED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-608

Cross-Repository Dependencies: HWEB-F07, HWEB-F10 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Multi-owner freeze/replay/rollback/concurrency and API/consumer qualification.

### 2. Existing Implementation

Independent immutable source stores and configuration definitions/values/feature-flag records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver complete baseline manifests, atomic freeze, compare/history/reconstruction and typed aggregate exchange after owner source publication. The exact bounded remaining delivery is: Multi-owner freeze/replay/rollback/concurrency and API/consumer qualification..

### 4. Functional Requirements

Same/different-content retries, stale head, corruption/outage/restricted packages and partial-capture faults cannot yield an authoritative baseline; exact contracts for F07/F09 and full evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A baseline is separate from a numerical run snapshot; no source revision may mutate an accepted historical manifest. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — PROPOSED:**

- `src/test/java/dz/sh/hidra/modules/topology/qualification/EngineeringBaselineOperatorQualificationTest.java` — actual owner/filter/database qualification of this task's exit matrix.

**MODIFY:** `.github/ci-test-inventory.json` — actual class/case/hash admission; `doc/roadmap/ULTIMATE_TASKS.md` — exact-source evidence and limitations.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

**DISCOVERY_REQUIRED:** fixture/support filenames and exact test partition; use synthetic owners and real transactions, not fabricated prior results.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Same/different-content retries, stale head, corruption/outage/restricted packages and partial-capture faults cannot yield an authoritative baseline; exact contracts for F07/F09 and full evidence.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-608. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23415](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23415)
- [doc/model-remediation/RECONCILIATION.md:23753](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23753)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1061](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1061)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1353](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1353)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1061 | HPR-P25-055E | BLOCKED_SPECIFICATION | Multi-owner baseline and exchange qualification
doc/roadmap/ULTIMATE_ROADMAP.md:1353 | HPR-P25-055E | Multi-owner freeze/replay/rollback/concurrency and API/consumer qualification. | Same/different-content retries, stale head, corruption/outage/restricted packages and partial-capture faults cannot yield an authoritative baseline; exact contracts for F07/F09 and full evidence.
doc/model-remediation/RECONCILIATION.md:23415 | HPR-P25-055E | Multi-owner freeze/replay/rollback/concurrency and API/consumer qualification. | Same/different-content retries, stale head, corruption/outage/restricted packages and partial-capture faults cannot yield an authoritative baseline; exact contracts for F07/F09 and full evidence.
doc/model-remediation/RECONCILIATION.md:23753 | HPR-P25-055E | BLOCKED_SPECIFICATION | Multi-owner baseline and exchange qualification
~~~~~~~~


<a id="hapi-927"></a>
## HAPI-927 — complete typed foundation configuration lifecycle

Phase: HPS-018 — Engineering Baselines and Configuration

Legacy IDs: None — owner-approved foundation supplement B5; no historical mapping replaced.

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `configuration`

Dependencies: HAPI-923

Cross-Repository Dependencies: HidraWEB handoff remains reserved/dependency-gated; no WEB implementation selected by this registration.

### Objective and authority

Owner approved the six-module functional foundation roadmap on 2026-11-03. This whole delivery is B5 of exactly12; no nested stages. Current source baseline is `b457a28c507dab07e554d4d9e52741783e415ba2`, Full CI41642224597 and Docs41642224576 SUCCESS. Existing code and tests do not prove the complete new browser workflow. Numerical/P3, deployment and SITE acceptance remain DEFERRED/NOT ESTABLISHED.

Registered exact commit message: `feat(configuration): complete typed foundation configuration lifecycle`.

Binding scope, owner policies, exact CREATE/MODIFY/conditional migration paths, objective acceptance and limits: [adopted foundation supplement](FOUNDATION_PRODUCTION_ROADMAP.md). Section B5 / HAPI-927 contains its full executable ledger; retain module boundaries and current native security guarantees.

### Minimum policy and test gate

Minimum policy: environment-scoped non-sensitive definitions and BOOLEAN flags are the initial supported administration. Admit strict existing STRING/BOOLEAN/numeric value types only where each parser is implemented/tested; unsupported declared types fail closed rather than being stored as arbitrary text. Definitions/flags activate from DRAFT through an authorized version-guarded command. A valid current environment override wins over the admitted definition default at an explicit instant; half-open windows and overlapping active overrides are rejected. Values must match the active exact definition/version, type and bounds. Derive actor server-side, inspect raw/JSON/default payloads for forbidden secret material and block unsupported requiresApproval changes. Sensitive settings return authorized redacted metadata/reference only. No vault, universal flag rule engine or business taxonomy redesign.

- Create/activate admitted definition and boolean flag, read default, set typed environment override, persist and read exact effective result through HTTP/PostgreSQL.
- Reject wrong type/version/window/overlap/unsupported approval, impersonated actor, sensitive material and unauthorized reads/writes; rollback and competing writes proven.

Run existing applicable domain/application/HTTP/security/PostgreSQL/rollback/concurrency suites and add the named missing tests. Regenerate API/catalog only from real migrated runtime evidence. Preserve every preexisting test and hash-bound class. Missing local Java21/Maven/Docker does not waive hosted full CI, and skipped tests are not database proof. No frontend acceptance is asserted.

### 2026-11-04 HAPI-927 same-task Fail analysis and original-exception evidence — Full #1113

At `cfc5fd7046ccc2bcd86420fa0b1126273fa202de` [HidraAPI Full CI #1113](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41853130967) **FAILED** and [Documentation #898](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41853130709) **PASSED**. Java21 unit/architecture, static validation and 13 of 14 PostgreSQL shards passed. PostgreSQL shard 3's new 12-case `ConfigurationAdministrationPostgresIntegrationTest` had **11 failures, zero errors**: ten positive definition creations returned HTTP 400, one positive Boolean feature-flag creation returned HTTP 500. The full source of the underlying resolved exception is not exposed in the initial run. No security, transaction or test oracle may be relaxed to obtain a pass.

**Bounded B5 diagnosis.** Keep all twelve actual required tests, HTTP 200/4xx/409 oracles, REAL local bearer, secret redaction, company/current authority, rollback/concurrent writer and fresh-schema assertions intact. Add only the `expectedOk` helper in the existing new B5 PostgreSQL integration test to retain the strict HTTP 200 assertion while surfacing the resolved exception/root type and message on failures in synthetic CI; do not print bearer, request payload or protected response. Exact changed source SHA256 `77647a7e9d82e0e8394352c51270c43246952564d426930692feb93371fdb44a` is reconciled in the existing `.github/ci-test-inventory.json` entry without altering case inventory or shard. This is a diagnostic improvement, **not** a claim that the HTTP failures are repaired.

**Actual OpenAPI generation evidence.** Failed API lane successfully generated and retained `hidra-ci-diagnostics-cfc5fd7046ccc2bcd86420fa0b1126273fa202de-41853130967-1-api-contract` artifact 13335318163; ZIP SHA256 `51bcc35c238d2ac3eaebe0d4592053b7f6047dc199ff0f1c3b3261c06fafb6b2`, raw `openapi/hidra-api.json` SHA256 `838896353f538276f356d14fd2472d82be1a5129a305f6e8d96e981804088ab9`. Genuine runtime adds **six** Configuration path items, **ten** operations, **eight** transport schemas to previous 327/360/406. Reconstructed source-stamped canonical JSON byte-matches runtime-canonical Git blob `ac327923f81cef88e478341be4393614cca898a2` / SHA256 `c6111155542445c4152de0e91ced413932142c24c24f52e78885de5739e94c8c`. Only `doc/api/openapi.yaml` and existing `.github/documentation-validation.json` snapshot/count/digest and generation-step-only provenance change. Retain existing endpoints/security, prior provenance and the strict comparator; no successful base-runtime compatibility check yet.

**Open gates.** The failed API job stopped before actual migrated `doc/database/DATA_DICTIONARY.md` generation, so do not infer dictionary bytes or alter immutable migration `V20261103_006`. Hosted repair-SHA Full CI must still reveal the resolved HTTP failure root, pass all 12 tests without skipped/failed cases, compare actual runtime OpenAPI with a successful base, capture PostgreSQL dictionary with strict equality and seal evidence. B5 HAPI-927 remains **IMPLEMENTED / IN_PROGRESS**, not accepted. Scope: only the B5 integration test, its exact inventory SHA, real OpenAPI snapshot/manifest and this append-only owning task evidence. No production Java, security authorization, migration, release/deployment, WEB, P3 or later B6 change. Registered exact message `feat(configuration): complete typed foundation configuration lifecycle`. Direct main with expected-head guard; verify new exact-SHA Full/Docs startup and STOP.

### 2026-11-04 HAPI-927 same-task Fail root repair — Full CI #1114

**Exact-SHA failure evidence.** At `9533394e3a72472a62e98c71be44944b0fb1805b`, [HidraAPI CI #1114](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41857627220) completed **FAILURE**, whereas [Docs #899](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41857627270) was **SUCCESS**. Java 21 unit/architecture, static validation and thirteen of fourteen PostgreSQL shards passed. The new B5 PostgreSQL 12-case test failed 11 positive creation oracles, without errors or skips. Ten definition POST requests returned HTTP 400 and the instrumented exception identified `HttpMessageNotReadableException` / Jackson `MismatchedInputException: Cannot map null into type boolean`. The test's normal create-definition fixture omitted the mandatory primitive `scoped`. The positive feature-flag POST returned HTTP 500 due to `jakarta.validation.ConstraintDeclarationException HV000151`: class-level `@Validated` method validation on `SpringConfigurationController` conflicts with parameter constraints on the implemented legacy `ConfigurationController` interface. These failures are not valid evidence of authorized or validated feature creation.

**Bounded same-task repair, including previously unlisted controller path.** Add explicit `scoped=false` to the positive definition fixture. Supply the required Boolean fields in unsupported-type, unsupported-approval, secret-material and unsupported-feature-strategy negative fixtures so their 4xx assertions exercise intended server policies rather than malformed JSON deserialization. Preserve all twelve named tests, required HTTP 200/4xx/409 statuses, genuine bearer, independent authority, rollback and concurrency assertions. Update only the matching existing exact-source CI inventory hash to `e96a9c74034a1ff6bfae53b42516747d20ce297c86831c9552a5cd47b2e064c8`, without exclusion or lane changes. For the verified HV000151 conflict, remove only class-level `@Validated` and its unused import from the existing `SpringConfigurationController` adapter. **All three `@Valid @RequestBody` annotations, DTO transport shapes, server-side authorization, domain/application invariants and security filters are preserved.** This controller path is a required additional existing-file modification beyond the original B5 allowlist; reconcile it under the foundation's same-task extra-path rule because the actual hosted error establishes necessity. This is source-reasoned, not a claim of newly executed tests.

**Actual captured physical evidence.** The failed API lane's genuine migrated PostgreSQL 16.15 artifact `hidra-database-schema-9533394e3a72472a62e98c71be44944b0fb1805b-41857627220-1-api-contract` (ID `13338681872`) contains `DATA_DICTIONARY.md`. Byte exact capture: 3,673,008 bytes, SHA-256 `92cce795f1e4551894ac30710f195d48cfb95ada3acf0fcafca73e4897415248`, verified Git blob `9a758d639f5aa2b6dfe213667645118b743468a3`. Capture reports **200 migrations, 470 JPA mappings, 552 relations, zero unresolved owners**, catalog SHA-256 `a719928f9060e137fecb94871d50b350bca55892415723e9f8dba56a92824747`. Replace only `doc/database/DATA_DICTIONARY.md` with those authentic bytes; do not change the immutable `V20261103_006` migration, infer schema constraints or bypass strict dictionary equality. Same-run strict OpenAPI equality **already passed** at 333 paths, 370 operations, 414 schemas; existing reviewed OpenAPI and manifest remain untouched in this repair. Successful-base runtime compatibility did not run after dictionary failure.

**Publication and remaining gates.** HAPI-927 stays **IMPLEMENTED / IN_PROGRESS** until a new exact-repair-SHA Full CI verifies all B5 HTTP/PostgreSQL cases, required test inventory, real migrated dictionary strict equality, backwards compatible runtime API and complete evidence. No local Maven/PostgreSQL test replay is claimed. Scope is exactly existing controller/test, exact test inventory, generated physical dictionary and this append-only owning task evidence; no other files, transaction policy, unrelated stages, HIDRAWEB, P3, SITE or deployment. Publish guarded directly to main with registered message `feat(configuration): complete typed foundation configuration lifecycle`; confirm exact parent/tree/blobs and CI startup, then STOP. HAPI-928/B6 remains unselected.

<!-- task-register:payload:end -->


### HAPI-605 — delivery-2 same-task Full CI #1085 repair (2026-11-03)

[HidraAPI CI #1085](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41610334336) FAILED on published `70f8f6c879c2aa92d21ffd1691e85677f369e2e0`; [Documentation #866](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41610334343) SUCCESS. Hosted Java 21 tests reported **2,479 executed / nine failed**, comprising two stale native registry assertions (8 versus the now-registered 13 real routes), one forensic cross-module exact-owner export census, one new Identity reader method census, four Topology admission/caller/source-hash pins and one Architecture exact-owner export negative sibling check. All 14 PostgreSQL shards and the API/dictionary lane were blocked by Spring startup failure, with exact cause `Cannot subclass final class ...JdbcEngineeringBaselineDraftRepositoryAdapter` (repository exception translation/CGLIB proxy). No endpoint, schema or database acceptance is inferred from these failures.

**Same-delivery CI repair, not stage 3.** Use the existing delivery-2 exact message `feat(topology): implement protected engineering baseline drafts`. Edit only: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringBaselineDraftRepositoryAdapter.java` (remove class `final`, leave operations unchanged); `src/test/java/dz/sh/hidra/platform/permissions/HidraTrustedNativeProtectedRouteHttpTest.java` (include five baseline routes and genuine baseline handler in exact tuple tests); `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java` and `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` (permit solely the three exact public Identity/Custody/Simulation baseline source contracts to reviewed Topology consumers, with explicit negative sibling/private adapter checks, never general module export); `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReaderTest.java` (new exact private one-use handle method/signature inventory); `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java` (only the additional reviewed compiled coordinator, Identity participant, five Platform exact caller strings and source reader SHA pin); `.github/ci-test-inventory.json` (new SHA-256 values of precisely five changed source-bound tests, retain every test identity/case); this evidence and the compact `ULTIMATE_TASKS.md` pointer. No DELETE, new stage, migration rewrite, production permission/default change, unsupported class bypass, physical transaction weakening, test removal, or runtime simulator scope.

**Validation boundary:** GitHub source/log and original immutable reader SHA-256 inspected; staged exact source-hash inventory/JSON and preserved case sets checked. Local Java 21/Maven/Spring/PostgreSQL/HTTP, hosted raw suites, OpenAPI and dictionary have **not** been rerun. After expected-head guarded publication observe exact-SHA Full/Documentation CI creation/start, report actual state, and STOP. If new CI fails, treat as same HAPI-605 delivery-2 defect repair; do not advance to the separately authorized third exceptional delivery. HAPI-605 remains PARTIAL / IN_PROGRESS, HAPI-606 BLOCKED.


### HAPI-605 — delivery-2 same-task Full CI #1086 repair and remaining fail-closed gate (2026-11-03)

**Actual exact-head CI outcome.** Main `adeda546c60a7f148afb27bbcde2ad6176456d9c` [Full #1086](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41612895970) FAILED; [Docs #867](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41612895962) SUCCESS. Static validation and ten of fourteen PostgreSQL shards succeeded. Java21 unit/architecture ran 2,479 tests with two `ArchitectureGuardrailTest` failures: the HAPI-605 owner-contract sibling negative check still permitted `RootSibling` because the guard covered only the root/`$` nested form, and the new manufacturer-root deny branch accidentally rejected HAPI-602's already accepted installed-characteristic-link consumer. Three database catalog tests on PostgreSQL shards 3, 8 and 12 failed the actual generator's `Unresolved physical ownership` for `public.hidra_topology_engineering_baseline_{draft,revision,request}`. The API lane **did boot the actual Java21 application and emit the runtime OpenAPI JSON**, but rejected its stale canonical snapshot (the dictionary and compatibility steps did not run). Shard 13 executed the 21 newly added native baseline cases, of which six passed and 15 failed/errored under redacted fail-closed 403 at `ProtectedEngineeringBaselineAuthorizationParticipant#denied`; this does not prove missing grants are safe to bypass. The precise authorization rejection phase is not verified. No all-green CI or engineering acceptance is claimed.

**Registered bounded same-delivery repair, exact message `feat(topology): implement protected engineering baseline drafts`.** Maintain the frozen HAPI-605 third exceptional delivery and full parent dependencies. MODIFY only these eight paths:
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`: deny exact baseline owner root *and sibling lookalikes*, but explicitly retain the previously reviewed HAPI-602 characteristic-link to Simulation manufacturer contract. Do not widen cross-module exports.
- `src/test/java/dz/sh/hidra/modules/topology/qualification/EngineeringBaselineDraftNativeHttpPostgresIntegrationTest.java`: one existing positive synthetic authorization case gets a temporary test-only Identity reader spy producing **redacted** reader-call/failure-category/grant-count stage evidence, without changing any asserted protected status, effective authorization, test case list or production class. This is diagnosis, **not** an authorization repair or acceptance.
- `.github/ci-test-inventory.json`: update only the two actual modified test source hashes; preserve all 553 existing classes, 21 baseline cases, 28 Architecture cases, every shard and fallback.
- `.github/database-dictionary-ownership.json`: register precisely three new topology-owned baseline draft/head, immutable revision and command receipt relations, with genuine existing domain, JDBC owner and immutable Flyway migration evidence. No cross-owner JPA mapping or schema fiction.
- `doc/api/openapi.yaml`: reconstruct sorted canonical runtime JSON from actual Full #1086 API artifact **13220380937** (ZIP SHA256 `f55708177d3513a62861e7e484be3e3b43f40fb5fb0f9f2c5d5cc7cb818977d3`, member SHA256 `b7447df6df096e366b11bf31b27a2883f4575c192028d286455884dec76e9f2e`). Actual runtime added only four baseline paths, five operations and six schema names: prior 301/330/377 becomes 305/335/383. Independently reproduced **source-stamp-stripped canonical SHA256 `c80eb5b380049d9114587a4f170350432e0aab0b4090054cde92cebc6536eb2e`**, exactly matching generated runtime member; all older endpoints/schemas/components retained. Source-stamped canonical SHA256: `22ae2f354ebd5f9070149b3531e5709a2d1847f7bd5fa422fd62bb860482294c`. This is artifact equality, not passing compatibility/HTTP authorization.
- `.github/documentation-validation.json`: update the existing OpenAPI manifest counts, digest and truthful *generation-step-only* provenance; retain previous reviewed provenance; do not mark Full CI as successful.
- `doc/roadmap/TASKS_HPS-018.md` and `doc/roadmap/ULTIMATE_TASKS.md`: append-only exact-run evidence and compact pointer. ROADMAP/PHASES statuses remain HAPI-605 PARTIAL / IN_PROGRESS.

**Unchanged and outstanding.** No Java production code, Flyway script, permission/grant, native timing, transaction-fencing, 403 status, route registry, installed-equipment source tests, authorization graph algorithm or deployment artifact is changed. The refreshed ownership metadata permits later *actual* dictionary generation; `doc/database/DATA_DICTIONARY.md` is not represented as regenerated without its PostgreSQL catalog output, and CI may still require real dictionary refresh on a subsequent exact SHA. The protected baseline authority/root/source positive path is **unresolved** pending redacted test diagnosis; deny-before-disclose and independent company/network/source authority are mandatory. Hosted new-SHA Java21, Postgres, full database metadata, OpenAPI, documentation and compatibility checks remain unverified until CI starts and reports actual results. This is a responsible HAPI-605 delivery-2 CI repair, **not** a fourth delivery, HAPI-605 closure, HAPI-606 selection, P3, UI/UAT, SITE or deployment. Publish by expected-head guard and STOP once exact-SHA Full/Docs CI is created/queued/started; do not advance or wait for completion in this turn.


### HAPI-605 — delivery-2 Full CI #1087 fail-closed native preparation and physical dictionary repair (2026-11-03)

**Verified input gate.** Main `1b96cdc696ed08c69c8fd5b8dc566014abc5e8ed`; [HidraAPI CI #1087](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41616814238) COMPLETED/FAILURE, [Documentation #868](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41616814333) COMPLETED/SUCCESS. Static, Java 21 unit/architecture, and PostgreSQL shards 0–12 all SUCCESS. Shard 13 failed 15 of 21 new synthetic native baseline cases, with *actual stack trace at* `ProtectedEngineeringBaselineAuthorizationParticipant.prepare():72`. Only API/dictionary step failed; runtime OpenAPI generated and passed canonical equality. Full verification gate consequently FAILED; no Java21/PostgreSQL acceptance on this head.

**Identity cause and bounded source repair.** `@Transactional(propagation=NEVER)` permits Spring transaction synchronization to be active without a physical transaction. The new baseline participant rejected `isSynchronizationActive()` before invoking the already established `ProtectedOperationIdentityParticipant.prepare()`, which correctly rejects **actual** active transactions but not harmless synchronization. Remove solely the extra synchronization rejection in `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedEngineeringBaselineAuthorizationParticipant.java`. Keep `isActualTransactionActive()` fail-closed, existing actor identity snapshot, physical original-transaction checks, native Identity prepare, source-permission evaluation, authorization graph, one-use tickets, prepared handle, terminal guard and every denial. This is neither a transaction relaxation nor a bypass of the company/network/source grants. Actual positive scenarios remain to be proved by the **new exact-SHA CI**, not by source reasoning.

**Physical database dictionary generated evidence.** Full #1087's [API job #125852197708](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41616814238/job/125852197708) generated the true fresh PostgreSQL catalog, derived source mapping and **CURRENT** markdown (before failing the strict reviewed snapshot comparison). Downloaded GitHub artifact `hidra-database-schema-1b96cdc696ed08c69c8fd5b8dc566014abc5e8ed-41616814238-1-api-contract`, artifact ID **13220964124**, archive SHA-256 `604d94b8ae7ac238ce6683e98043ce76d89fd9129d6ee6d2f34155ac90941b70`. Actual catalog `schema.json` SHA-256 `145c84cf71baccf3110737333eaf1b6ed2440f8de2367c7577645bf6b6150a3c`. Generated `DATA_DICTIONARY.md` SHA-256 `0825e51139e3dbabbf8b687af11e41ee22b489f24a18977e59c696fb2e9f1c4d`, **3,626,106 bytes**, Git blob SHA `aff12e2d0bb4fd81e1193e44c49ae58f1b726fa7`. The byte-identical blob has been independently reconstructed and Git-verified from real reviewed hosted output; this is **not** a handwritten schema or assertion that CI dictionary equality passed. Catalog: PostgreSQL 16.15, 196 successful migrations, 470 JPA mappings, 547 relations, zero unresolved owners. The current-run strict comparator remains FAILED until rerun against this exact updated snapshot. Existing migrations and dictionary generator unchanged.

**Exact same-delivery repair envelope.** Retain the delivery-2 registered message `feat(topology): implement protected engineering baseline drafts`. MODIFY exactly: the one Identity participant noted above; `doc/database/DATA_DICTIONARY.md` using exact generated 1087 bytes; this append-only `doc/roadmap/TASKS_HPS-018.md` evidence; and the compact `doc/roadmap/ULTIMATE_TASKS.md` pointer. No tests, inventories, native source reader hashes, migration files, API snapshot or OpenAPI metadata, security grants, workflows or unrelated modules modified. Preserve all earlier 5,000+ blobs and original cases. Source-level checks and exact generated artifact SHA are preliminary; only the new published SHA's full raw CI/Docs status can establish test and catalog approval. This is one bounded HAPI-605 *delivery-2* failed-CI repair, not an added exceptional delivery nor a selected HAPI-606 stage. HAPI-605 stays **PARTIAL / IN_PROGRESS**; third exceptional delivery not yet executed, HAPI-606 BLOCKED. After expected-head guarded main publication, verify both applicable workflow runs were created/queued/started and STOP without waiting.


### HAPI-605 — one additional owner-authorized acceptance attempt, 2026-11-03
Owner direction: `Next` following the proposed single additional HAPI-605 closure delivery.
**Registered exact commit message:** `test(topology): qualify HAPI-605 four-family draft completeness`.
**Exact MODIFY allowlist:** `src/test/java/dz/sh/hidra/modules/topology/qualification/EngineeringBaselineManifestChecks.java`, `src/test/java/dz/sh/hidra/modules/topology/qualification/EngineeringBaselineManifestContractTest.java`, `doc/roadmap/TASKS_HPS-018.md`,
`doc/roadmap/ULTIMATE_TASKS.md`, `.github/ci-test-inventory.json`. **CREATE/DELETE:** none.
No production, Identity/Workflow permission, migration, freeze/exchange or HAPI-606 edits.
Added pure Java four-family composition assertions prove exact source membership
(network to station to installed characteristic), complete draft diagnostics,
canonical round-trip, and retained captured source when current product eligibility,
method provenance or characteristic approval changes. The parent Full #1091 and
Docs #872 succeeded at the authorized parent SHA.
**This synthetic composition is not a real four-producer published HTTP/PostgreSQL
journey**. In-original-transaction expiry/revocation, moving characteristic
approval, cross-owner rollback and new exact-SHA generated API/catalog proof
remain OPEN. HAPI-605 remains PARTIAL / IN_PROGRESS; HAPI-606 blocked.
The single additional delivery is not an authorization for further subtasks.

### 2026-11-04 HAPI-927 whole-B5 selection, implementation candidate and validation boundary

**Selection and exact source.** Owner `continue` selects the next registered whole foundation delivery B5 after B4 acceptance on main `73d01e4d8e88d794e36036ef347812b3aa0afc79` (tree `00739d72d7f43e182a006133798c5736a8cd4ee9`, parent `c333b7e126d81cce8c09beba618d2a3a3dd007a1`). Live [Documentation #897](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41847485941) succeeded on main; the accepted executable B4 parent has independently refreshed [Full #1112](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41845099572) SUCCESS. B5's dependency HAPI-923 is already accepted. Use only the registered exact message `feat(configuration): complete typed foundation configuration lifecycle`. This is one whole B5 implementation, not a new stage or a promotion to software acceptance.

**Executable ledger and implementation.** Exactly the twelve B5 CREATE paths in the adopted supplement, plus its reserved and still-unused `V20261103_006__foundation_configuration_lifecycle.sql`; MODIFY only the existing ConfigurationApplicationService, ConfigurationValueGuard, strict CI inventory and the three owning governance documents (this phase, ROADMAP and PHASES). No DELETE, additional production/Platform path, private cross-module import, historical migration rewrite, permission exception, frontend, release, deployment or numerical/P3 change. The existing create-definition, create-flag and set-value use-case beans retain their interfaces and transport contracts; their implementation now authenticates and checks current global administrator authority before access/mutation and again before return. The existing Platform permission resolver delegates current bearer binding/session/user state and live global administrator meaning to Identity; JWT authority claims alone are insufficient. No Identity internal class is imported by Configuration. All new administration and discovery calls require the same native, unexpired, currently authorized administrator; ordinary generic route grants alone do not establish this authority. No new native-route registry exception is introduced.

The pure TypedConfigurationPolicy owns supported type, bounded exact scalar, half-open interval, environment, expected-version, approval/scope and secret-material rules. The inbound use-case and outbound persistence/authorization ports precede the coordinating application service; infrastructure owns the transaction-bound JPA-manager JDBC adapter and security plumbing; explicit REST request/response records and mappings avoid exposing persistence entities. New schema names are explicitly unique to Configuration. A narrowly scoped nested REST advice in the new controller maps Configuration domain errors to sanitized 400 and state/database integrity conflicts to sanitized 409 for the new and existing Configuration controllers; global handlers and other modules remain unchanged.

B5 administration supports PUBLIC/INTERNAL environment values only, STRING/strict lowercase BOOLEAN/plain finite NUMBER; NUMBER precision <=38 and scale <=18, and scalar length <=2000 are supported software bounds, not industrial calibration. DATE/DURATION/JSON/REFERENCE/LIST, custom active validation rules, resource-scoped definitions and requiresApproval writes fail closed. Sensitive definitions may be created as metadata with no default, but cannot activate or accept value writes here. All defaults, raw/JSON/reference value inputs and descriptive payloads are screened for the existing forbidden secret patterns plus structured secret keys, bearer/JWT and private-key material; arbitrary secret detection or vault functionality is not claimed. Sensitive/forbidden legacy payloads are redacted before response mapping, including default and value discovery. No raw JSON or secret reference is accepted by this scalar writer. Existing request actor input is optional and non-authoritative; a mismatched actor is rejected, while the actual native actor is persisted server-side.

An active existing namespace is required for creation/activation/write. Namespace authoring and seeding production business policy are not added. Definition activation locks the owner, compares the exact PostgreSQL microsecond updatedAt token, and atomically creates one active immutable existing-table definition-version row with server actor and copied default. Activated B5 definitions and their exact version rows are immutable; new values require that exact active admitted version. Owner locking serializes overlap admission and same-version activation; the database trigger independently rejects invalid/overlapping B5 writes and preserves legacy unmarked producer contracts. Adjacent [from,to) intervals are legal; null from, empty/reversed windows and overlapping active overrides reject. Effective reads require an explicit instant and return typed default or exact environment override with definition/version/source provenance. Unsupported custom validation rules are rejected, not silently ignored. Choice lists use bounded LIMIT/OFFSET and current administrator authorization before any owner lookup.

BOOLEAN flags activate only from DRAFT with exact updatedAt, use current active BOOLEAN eligibility, and persist environment state through the existing ENVIRONMENT rule family under a single locked flag owner. Unsupported active rule families fail closed. Updating state advances the flag token, retires the previous active environment row and appends the new actor-marked rule; activation records an actor-marked inactive audit rule. Default state remains explicit for environments without overrides. The new forward migration adds exact uniqueness, typed-window/overlap guards and admitted definition/version immutability using existing owner tables; it creates no new physical relation and modifies no existing migration. Historical entity/codec shapes and other module owners remain unchanged.

**Test source and actual local checks.** New TypedConfigurationPolicyTest has six named JUnit cases; ConfigurationAdministrationPostgresIntegrationTest has twelve configured fresh PostgreSQL16 / genuine LOCAL signed-JWT HTTP cases. Together the eighteen cases cover typed defaults/activation/override/reload, exact half-open boundaries, namespace/definition/value discovery, BOOLEAN state/default/stale replay, unsupported type/approval/secret/strategy rejection, wrong definition version/type/window and impersonation, overlapping versus adjacent windows including real database-trigger rejection, sensitive legacy redaction, current administrator withdrawal before known/unknown reads and writes, rollback of activation plus version, competing activation and override writers, definition/version immutability and final authority denial rolling back provisional state. The last case deliberately withdraws the real grant inside its test-owned transaction at the final authorization call; it tests actual current-authority denial/rollback, not a claim of external-writer fencing or unknown-commit injection. All existing classes/cases/source hashes/lanes and CI policies are retained; only the two new test identities are added, with zero historical timing measurements claimed.

PASS locally: Java21 compiler parsing of all109 available Configuration Java sources; actual Java21 compilation of Configuration Domain, application ports/commands/DTOs/mapper; 34 direct executable policy assertions for scalar typing/bounds, unsupported types, secret patterns, windows, exact timestamps and stale versions. No Maven/Docker/psql executables are available; no complete environment was installed. Therefore no full Spring/application compilation, JUnit execution, real HTTP, PostgreSQL migration/JPA/rollback/concurrency result or new runtime API/catalog pass is claimed locally. Source inventory/header/dependency/frozen-status/diff checks are preliminary source evidence only.

**CI-first publication and acceptance boundary.** Keep doc/api/openapi.yaml, .github/documentation-validation.json and doc/database/DATA_DICTIONARY.md unchanged until real hosted runtime/catalog captures exist. Ten new operations and the new DDL mean strict API/catalog regeneration and equality remain explicit same-task acceptance obligations; no schema or API bytes are invented. HAPI-927 is IMPLEMENTED / IN_PROGRESS as a whole source candidate, not VERIFIED/COMPLETED. Required new-SHA Full/Documentation CI, all prior owner/architecture suites, eighteen new cases, genuine runtime API/backward compatibility and physical dictionary capture/equality plus independent whole-B5 acceptance remain open. Publish exactly nineteen paths (13 CREATE,6 MODIFY) under the refreshed expected-main guard, verify parent/tree/blobs/message and preservation, observe applicable exact-SHA CI created/queued/started and STOP. If Full cannot start, provide its actual browser dispatch steps and stop. HAPI-928/B6 is the next separately selected backend task after B5 acceptance, not executed here; all HidraWEB, engineering/SITE, external identity commissioning, OT, release and deployment gates remain separate.

### 2026-11-04 HAPI-927 same-task Fail repair — Full CI #1115

At main `c27219c347edf0bc52c79d130991ba33920e9b43`, [Full CI #1115](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41861007632) completed **FAILURE**; [Docs #900](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41861007626) succeeded. PostgreSQL shard 3 failed three of twelve ConfigurationAdministrationPostgresIntegrationTest cases; the other thirteen shards, Java/architecture, static and API/dictionary lanes passed. The logged root is PostgreSQL SQLSTATE 42804: `json_value` is `jsonb` but its bound expression is `character varying`; the three successful-write HTTP oracles received 500. The final verification gate consequently failed.

**Same-task repair:** Add Hibernate `@JdbcTypeCode(SqlTypes.JSON)` to the existing ConfigurationValueJpaEntity `jsonValue` mapping so PostgreSQL receives the correct JSONB bind type, including B5's required null JSON value for scalar overrides. This matches established JSONB entity mappings elsewhere. No application/domain/REST/authorization change, case removal, test-inventory change, schema migration or physical dictionary rewrite. Keep all existing negative, race, rollback and concurrency tests unchanged. Registered message remains `feat(configuration): complete typed foundation configuration lifecycle`.

**Evidence boundary:** No local Maven/Docker/PostgreSQL execution was possible. The correction is source-reviewed, not independently runtime-proven; HAPI-927 stays IMPLEMENTED / IN_PROGRESS. Only the new exact-SHA Full CI can establish passing PostgreSQL/JPA/HTTP, required evidence and final B5 software acceptance. HAPI-928/B6 remains unselected.

### 2026-11-04 HAPI-927 same-task Fail repair — Full CI #1116 dictionary source reconciliation

**Exact-SHA evidence.** Main `c871ac3e118565d533b751897698db5fd25a7a7f` [HidraAPI CI #1116](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41866960379) finished FAILURE; [Documentation #901](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41866960507) succeeded. All fourteen PostgreSQL shards (including the twelve B5 integration cases), Java21 unit/architecture, static validation, and generated OpenAPI strict equality (333 paths / 370 operations / 414 schemas) succeeded. The API/dictionary lane failed solely at strict comparison with `doc/database/DATA_DICTIONARY.md`; final evidence gate failed as a dependent consequence. No PostgreSQL B5 test failure remains on this SHA.

**Authenticated generated catalog, not inferred DDL.** CI #1116 preserved [the database artifact](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41866960379/artifacts/13343066305) (ID `13343066305`, ZIP SHA-256 `4bad72b6e61a2655f31f2beb108ea8f558ec93d0cb3c96db8f041cdc5bb3d64d`). Its fresh PostgreSQL 16.15 capture contains 200 successful migrations, 470 JPA mappings, 552 relations, zero unresolved owners. Schema JSON SHA-256 `c7c67302849969149822eebe8bbf4739d3c290589ca3f7a74d50d367cad5b178`. Authenticated generated `DATA_DICTIONARY.md`: 3,673,008 bytes; SHA-256 `c0b70903091e271114fee7efcc41b8b744d6f4cadef079c76d6cde82da6609a1`; independently matched Git blob `00231e22dd484dd588d6212482e8b94edea5aee2`. Exact byte comparison with the committed 1114 capture showed **only two generation-basis header changes**: capture source SHA and source-bundle SHA-256. All physical relation entries, constraints, mappings and ownership descriptions are unchanged. The source-bundle digest changed because the accepted HAPI-927 JSONB/JPA source annotation was repaired; the strict dictionary comparator intentionally ignores only the capture commit line, not source-bundle drift.

**Bounded repair:** Replace `doc/database/DATA_DICTIONARY.md` with the **byte-exact** actual #1116 generated file. Keep the existing generator, strict comparison, catalog metadata, source, tests, fourteen-shard inventory, workflow, migrations, authorization, transaction/overlap invariants, OpenAPI and security unchanged. Append this evidence and the compact task-index pointer only. Registered exact message remains `feat(configuration): complete typed foundation configuration lifecycle`. No unrelated code, second task, release or deployment.

**Acceptance boundary:** HAPI-927 remains IMPLEMENTED / IN_PROGRESS until new exact-SHA Full/Docs CI confirms the strict dictionary check, runtime OpenAPI backward compatibility, sealed evidence and all mandatory tests. No new local Maven/PostgreSQL execution or deployed industrial acceptance is claimed. HAPI-928/B6 stays unselected.


### 2026-11-04 HAPI-927 whole-B5 independent development-software acceptance — Full CI #1117

**Owner selection and bounded disposition.** Owner Next selects completion of the existing HAPI-927/B5 acceptance obligation after its source delivery, same-task repairs and actual successful feedback. HAPI-927 is now **VERIFIED / COMPLETED** solely for the registered typed Configuration foundation backend software. This is the whole task's evidence-based acceptance, not a new stage, new task ID, scope expansion or release. Earlier failed/pending records remain historical evidence. B6 is not executed by this disposition.

**Exact successful source.** Implementation/evidence main `ed3f0a65e9e1674284ae2c0a25f0ba920c1dd9c9`, tree `cf75b451048b57f08a4301a795cba7e3b210b50f`, parent `c871ac3e118565d533b751897698db5fd25a7a7f`. [HidraAPI Full CI #1117](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41884794726), run41884794726 attempt1, and [Documentation Validation #902](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41884001111) both SUCCESS on that exact SHA. Full's required Java21 unit/architecture, static, fourteen PostgreSQL shards, API/dictionary and aggregate gate succeeded. The optional serial diagnostic job was skipped by the development-partitioned mode; no unrestricted serial equivalence, optimized adoption or release qualification is claimed.

**Independent authenticated evidence review.** Retrieved the real aggregate artifact `hidra-full-ci-evidence-ed3f0a65e9e1674284ae2c0a25f0ba920c1dd9c9-41884794726-1`, GitHub artifact13352200557, 8,126,900 bytes, ZIP SHA256 `3dc1f9a2eaeebfead8711767885b57f90ea88d7d939e29ebfc17dda15c0d5a1e`. Local ZIP digest/CRC checks passed. Independently validated all17 lane receipt digests, all567 retained member digests and exact source/run/attempt/inventory context. Raw Surefire XML reaggregation gives **560 distinct classes, 4,291 distinct cases, 0 failures, 0 errors, 0 skipped**, identical to gate.json. Unit/architecture contributes418 classes/2,510 cases; PostgreSQL shard3 contributes9 classes/124 cases including all12 genuine Configuration native HTTP/PostgreSQL cases. Source inventory SHA256 `f7354d0e8e13e6a64c219bf75c4163d859e5b1bfe8b1da50ce4ac1e9bf35fcd6` matches current main bytes.

All five Configuration suites passed, **27 cases total**: TypedConfigurationPolicyTest6; ConfigurationAdministrationPostgresIntegrationTest12; ConfigurationDefinitionSemanticRemediationTest3; ConfigurationValueSemanticRemediationTest3; FeatureFlagSemanticRemediationTest3. Raw method identities match their exact inventory entries. The two new test sources were freshly fetched at the accepted SHA and independently hash-matched: pure policy `89341c8a686dec396174137c2c9841f2c65f819dfefbf09df8438770c5e74136`, native suite `e96a9c74034a1ff6bfae53b42516747d20ce297c86831c9552a5cd47b2e064c8`. Current production service/policy/security/REST/query/migration and JSONB entity mapping were re-read at that SHA. This acceptance relies on actual hosted execution plus local evidence verification, not a claim to have rerun Maven/PostgreSQL locally.

**Accepted requirements and proof.** The configured fresh PostgreSQL16 fixture uses real LOCAL sign-in and signed native bearer requests to create and activate an admitted definition, discover owner namespaces/definitions, read and canonicalize a NUMBER default, write a typed environment override with server-derived actor and exact active version, and reload the exact default/override provenance at explicit half-open boundaries. BOOLEAN flag creation/activation, environment-specific state/default and stale state-token rejection also pass. Negative cases retain wrong definition-version/type/window, actor impersonation, unsupported type/approval/strategy, forbidden raw/JSON/default material, sensitive legacy redaction and current administrator withdrawal before known/unknown reads and writes. Actual overlap-trigger rejection and adjacent-window success, competing expected-version activations/overlapping writers, immutable activated definition/version protection, downstream rollback and final current-authority denial rollback pass. The final-authority test deliberately changes the real grant inside its test-owned transaction; this is not external-writer fencing or unknown-commit qualification. Existing semantic secret-reference and ownership tests remain passing; no assertions, native routes, grant policies, source classifiers or migration history were weakened for this disposition.

The accepted policy remains the registered minimum: current native global-administrator administration; PUBLIC/INTERNAL environment scalar values; strict STRING/BOOLEAN/plain NUMBER within documented software bounds; only BOOLEAN flags; expected PostgreSQL-microsecond state tokens; immutable admitted definition versions; explicit effective instant and half-open windows; server-derived actors and redaction before serialization. Resource-scoped/approval-required writes, unsupported types/custom active validation/flag rules, a vault or universal rule engine remain unsupported and fail closed. Sensitive metadata remains restricted to authorized redacted responses. Namespace creation and business-policy seeding are not added. Accepted definition/override concurrency and transactional rollback do not establish a new cross-owner/native execution protocol.

**Actual runtime contract and catalog.** Authenticated current runtime OpenAPI SHA256 `b6dea22df480bdd3591a58556d159abf86b0e2a2d777f4e45526b3edc8b1b749`; source stamp matches accepted SHA; counts **333 paths / 370 operations / 414 schemas** agree with current reviewed documentation manifest. Hosted canonical runtime equality succeeded. Independently reran the current repository openapi_compatibility.py against the authenticated captured base and current runtime members: PASS, no supported breaking changes detected. Actual base is `c333b7e126d81cce8c09beba618d2a3a3dd007a1`, captured base API SHA256 `e66413b0e2a70d0b88ec4f6ab09ef5c3caf1029d193579b093cb8242c146ea9c`. PostgreSQL16.15 captured catalog SHA256 `becd1db0d2d44e592b1b4be3a35a22d7e4409dc90d8efd23e6647e7d66a44497`, source bundle `f77cbd7fcfb959519878ae98cfa8f7d3f8f119aba4c69bc235500787fd407e7a`, generated dictionary `04af2658979df8d41f699b53932a4870dc0aff7424808049c30a074813d880f6`. Independently verified these member digests and verification.json: **200 successful migrations, 470 JPA mappings, 552 relations, zero unresolved owners, reviewed_dictionary_checked=true**. Hosted strict dictionary equality passed; actual API/catalog captures supersede the prior pending capture obligations. Existing canonical snapshots/generator/metadata are not rewritten by acceptance.

**Exact documentation-only publication envelope.** Reuse the registered task message `feat(configuration): complete typed foundation configuration lifecycle`. MODIFY only this phase record; doc/roadmap/ULTIMATE_ROADMAP.md and ULTIMATE_PHASES.md (solely HAPI-927 Implementation/Planning status cells); and, reconciled here as the necessary existing compact-navigation path, doc/roadmap/ULTIMATE_TASKS.md (current pointer and short acceptance link only). Synchronize this task header; append technical evidence without rewriting earlier checkpoints. No CREATE/DELETE, production/test/schema/API/catalog/inventory/workflow/security change. Preserve all928 task identities,25 phases, other status cells, membership, order, dependencies, history and archived payloads. Local exact-scope/frozen-table/append-only/pointer checks must pass, then publish directly to main with expected-ed3f0a65 guard, verify exact parent/tree/blobs/message, observe this documentation-only commit's applicable exact-SHA CI and STOP.

**Retained gates and next registered task.** This accepts bounded synthetic backend development software only. HidraWEB W3, browser/UAT, enterprise directory commissioning, operator/engineering/SITE, industrial calibration, live OT, numerical/P3, release and deployment remain unaccepted/unselected. The serial-oracle/release flags remain false in the actual receipt. Next separate owner selection is HAPI-928/B6, subject to its live whole-task prerequisites and scope; B6 and all frontend deliveries remain unexecuted here.
