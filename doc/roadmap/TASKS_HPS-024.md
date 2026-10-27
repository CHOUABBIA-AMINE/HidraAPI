# HPS-024 — Security, DevSecOps and Quality: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-024 — Security, DevSecOps and Quality

<a id="hapi-043"></a>
## HAPI-043 — Measured critical path and execution analysis

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: CI-003A

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Refresh current job/step/raw-class timings, runner CPU/RAM/forks, cache and scheduling context.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Identify the actual critical path on comparable successful runs, account for source/test growth; distinguish suite sums, job wall time, queue and workflow elapsed. Preserve old measurements as dated evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

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

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23332](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23332)
- [doc/model-remediation/RECONCILIATION.md:23730](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23730)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1038](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1038)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1270](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1270)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed measured-evidence/no-code-needed documentation disposition in the pinned execution record; not CI optimization adoption. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1038 | CI-003A | READY_FOR_REGISTRATION | Measured critical path and execution analysis
doc/roadmap/ULTIMATE_ROADMAP.md:1270 | CI-003A | Refresh current job/step/raw-class timings, runner CPU/RAM/forks, cache and scheduling context. | Identify the actual critical path on comparable successful runs, account for source/test growth; distinguish suite sums, job wall time, queue and workflow elapsed. Preserve old measurements as dated evidence.
doc/model-remediation/RECONCILIATION.md:23332 | CI-003A | Refresh current job/step/raw-class timings, runner CPU/RAM/forks, cache and scheduling context. | Identify the actual critical path on comparable successful runs, account for source/test growth; distinguish suite sums, job wall time, queue and workflow elapsed. Preserve old measurements as dated evidence.
doc/model-remediation/RECONCILIATION.md:23730 | CI-003A | READY_FOR_REGISTRATION | Measured critical path and execution analysis
~~~~~~~~

<a id="hapi-044"></a>
## HAPI-044 — Evidence-justified CI scheduling correction

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: CI-003B

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Apply only a justified bounded scheduling/resource/evidence correction if measurements require one; otherwise record no code needed.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing exact CI scope, unchanged required class/case identities, isolated containers and complete clean-verify/API/dictionary/release behavior. No skipped tests, unsafe target/report/DB caches or speculative extra shards.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

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

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23333](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23333)
- [doc/model-remediation/RECONCILIATION.md:23731](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23731)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1039](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1039)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1271](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1271)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed measured-evidence/no-code-needed documentation disposition in the pinned execution record; not CI optimization adoption. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1039 | CI-003B | BLOCKED_SPECIFICATION | Evidence-justified CI scheduling correction
doc/roadmap/ULTIMATE_ROADMAP.md:1271 | CI-003B | Apply only a justified bounded scheduling/resource/evidence correction if measurements require one; otherwise record no code needed. | Existing exact CI scope, unchanged required class/case identities, isolated containers and complete clean-verify/API/dictionary/release behavior. No skipped tests, unsafe target/report/DB caches or speculative extra shards.
doc/model-remediation/RECONCILIATION.md:23333 | CI-003B | Apply only a justified bounded scheduling/resource/evidence correction if measurements require one; otherwise record no code needed. | Existing exact CI scope, unchanged required class/case identities, isolated containers and complete clean-verify/API/dictionary/release behavior. No skipped tests, unsafe target/report/DB caches or speculative extra shards.
doc/model-remediation/RECONCILIATION.md:23731 | CI-003B | BLOCKED_SPECIFICATION | Evidence-justified CI scheduling correction
~~~~~~~~

<a id="hapi-045"></a>
## HAPI-045 — Equivalence and negative acceptance gates

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: CI-003C

Priority: P0

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-043, HAPI-044

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Complete representative code/security, migration and API revision equivalence, cold/warm and negative acceptance proof.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Complete CI-003 representative/negative/performance qualification and explicit adoption decision; preserve mandatory fallback, security generations and IT/OT segregation. The exact bounded remaining delivery is: Complete representative code/security, migration and API revision equivalence, cold/warm and negative acceptance proof..

### 4. Functional Requirements

Same-run exact source/lifecycle/report/API/catalog equality; omitted/failed/skipped/overlapping/stale/cancelled evidence denies acceptance. Use naturally authorized representative revisions, not artificial application changes for benchmarks.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / DELETE:** none preauthorized.

**MODIFY — only if accepted evidence demonstrates a correction is necessary:**

- `.github/workflows/ci.yml` — preserve complete mandatory selections, services and lifecycle; no oracle removal until explicit adoption.

- `.github/scripts/verify_full_ci_evidence.py` — preserve same-run class/case/API/catalog/source/attempt equality and negative rejection.

- `.github/scripts/test_verify_full_ci_evidence.py` — actual new independent negative cases when necessary.

- `doc/roadmap/ULTIMATE_TASKS.md` — retain measured evidence or no-code-needed decision.

**PRESERVE / REUSE:** `.github/ci-test-inventory.json`, `.github/workflows/ci-fast-feedback.yml`, unrestricted oracle and retained four historical upgrade tests.

**DISCOVERY_REQUIRED:** exact remaining representative/cold-warm/timing evidence package and any change allowlist. Never rerun unchanged Full solely to repeat known latency.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Same-run exact source/lifecycle/report/API/catalog equality; omitted/failed/skipped/overlapping/stale/cancelled evidence denies acceptance. Use naturally authorized representative revisions, not artificial application changes for benchmarks.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-043, HAPI-044. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23334](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23334)
- [doc/model-remediation/RECONCILIATION.md:23732](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23732)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1040](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1040)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1272](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1272)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1040 | CI-003C | BLOCKED_SPECIFICATION | Equivalence and negative acceptance gates
doc/roadmap/ULTIMATE_ROADMAP.md:1272 | CI-003C | Complete representative code/security, migration and API revision equivalence, cold/warm and negative acceptance proof. | Same-run exact source/lifecycle/report/API/catalog equality; omitted/failed/skipped/overlapping/stale/cancelled evidence denies acceptance. Use naturally authorized representative revisions, not artificial application changes for benchmarks.
doc/model-remediation/RECONCILIATION.md:23334 | CI-003C | Complete representative code/security, migration and API revision equivalence, cold/warm and negative acceptance proof. | Same-run exact source/lifecycle/report/API/catalog equality; omitted/failed/skipped/overlapping/stale/cancelled evidence denies acceptance. Use naturally authorized representative revisions, not artificial application changes for benchmarks.
doc/model-remediation/RECONCILIATION.md:23732 | CI-003C | BLOCKED_SPECIFICATION | Equivalence and negative acceptance gates
~~~~~~~~

### 2026-10-20 — owner-selected development CI partition optimization

Owner direction: resume HAPI-045, treat HidraAPI as a development API with no
production deployment/database, avoid running the full Java suite again alongside
complete parallel sets, review obsolete tests and use previous runs for timing.
This same-ID bounded development-policy amendment supersedes the per-push oracle
requirement in sections 3/4/6 for development only. It is not CI-003 programme
closure, production adoption, release approval or completion of HAPI-046/HAPI-436.
An explicit manual `workflow_dispatch` with `verification_mode: audit` retains the
unrestricted oracle, same-run equivalence and the strict release proof.

Exact registered implementation message (retained HPR-CI-003 message):
`ci: automate fast feedback and preserve full verification gates`.

**Bounded write scope:** `.github/workflows/ci.yml`,
`.github/ci-test-inventory.json`, `.github/scripts/verify_ci_test_inventory.py`,
`.github/scripts/test_verify_ci_test_inventory.py`,
`.github/scripts/verify_full_ci_evidence.py`,
`.github/scripts/test_verify_full_ci_evidence.py`, `PROJECT_STATE.md` and this
owning task record. The two inventory files/helpers are admitted because measured
new classes had zero scheduling weights and the existing two DB sets were uneven.
No Java source/test, POM, schema, migration, API snapshot, dictionary, Fast workflow
or release workflow changes. All existing required static validators remain.
Older use of the word production for source, CI or artifact checks does not imply
an operating production API; PROJECT_STATE now records the owner's current scope.

**Previous-run observations:** successful GitHub jobs, wall seconds (start to
completion, excluding queue). These are existing naturally completed runs, not a
new benchmark campaign or five comparable samples of the new configuration.

| Full run | Unit | PostgreSQL 0 | PostgreSQL 1 | API/dictionary | Serial verification |
|---|---:|---:|---:|---:|---:|
| [777](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39423247020) | 100 | 312 | 748 | 152 | 1295 |
| [774](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39410913571) | 97 | 353 | 628 | 186 | 919 |
| [773](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39406126892) | 91 | 352 | 846 | 164 | 1113 |
| [772](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39403043675) | 97 | 359 | 619 | 160 | 1127 |
| [771](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39400041135) | 69 | 370 | 835 | 122 | 1051 |

Observed serial-job median is 1113 seconds (18m33s); it includes the duplicate
suite and duplicate API/dictionary/base-generation work. This is not a workflow
elapsed-time median. Test growth, runner/cache state and source differ between
samples; cold/warm classification, flake rate and cost acceptance are not inferred.
Full777 total job wall time sums to 2643 seconds (44m03s runner time), excluding
queue and any billing rounding. No new-configuration runner cost is yet measured.

**Timing provenance and partition scope:** Full777 on source
`067929306170ad69b453823a90bb7beeb6b8130d`, attempt 1, authenticated aggregate
ZIP SHA256 `9aa04df6a595ed37388f08125d16db7efcba4d52fa181dd01fdc4f83c80e93c3`.
Downloaded raw XML/receipts were rechecked for hashes, class/case coverage,
API/catalog equality and dictionary source provenance. Every test/support source
and the POM match current parent `0d11c29657ee6af5859a321c929dae4caaf89593`.
Only `baseline_seconds` changes inside existing class entries; identities, cases,
source pins, owners, dependencies, Fast membership and archive policy are unchanged.
The old baseline remains for original case provenance; `timing_baseline` records
the newer scheduling measurements separately.

| Development test set | Classes | Cases | Previous oracle suite seconds, summed |
|---|---:|---:|---:|
| Unit/architecture | 336 | 1717 | 54.231 |
| PostgreSQL 0 | 30 | 265 | 341.868 |
| PostgreSQL 1 | 34 | 315 | 341.872 |
| PostgreSQL 2 | 30 | 309 | 341.875 |
| Complete disjoint union | 430 | 2606 | 1079.846 |

Two balanced DB sets would each have about 513 suite seconds before job/setup
cost, already exceeding the seven-minute target. Three isolated runners are
therefore evidence-justified; JVM forks remain one, deadlines/retries and container
isolation remain unchanged. Suite sums are scheduling estimates, not measured new
job times or a promised speedup. Every lane still executes Java21 `clean verify`;
the API lane still generates current/base OpenAPI and the real migrated catalog.
The development gate requires every mandatory lane and case, and requires only the
explicit audit job to be intentionally skipped. Missing, failed, cancelled,
overlapping, stale or skipped test evidence fails. It emits
`same_run_equivalence: false`, `release_eligible: false`; no synthetic oracle is
constructed. Audit mode requires every lane plus the independent full oracle and
compares classes/cases/API/catalog in the same run. Release lookup still requires
the oracle job to succeed and recomputes audit evidence from authenticated raw
artifacts; a development run cannot pass it.

**Database and obsolete-test audit:** the test profile uses PostgreSQL16 with
Flyway migration validation and Hibernate `ddl-auto=validate`. The API job starts
an empty isolated database; it does not reuse a pre-green database, cached target,
report or seeded production catalog. Full777's actual dictionary source contains
157 Flyway migrations, 470 JPA mapping sources and 502 catalog relations with no
unresolved ownership. The dictionary proves current fresh-schema migration and
physical-catalog/source consistency, not a production-data upgrade. Integrity,
invalid-reference, rollback, authorization and application-state tests remain
required. The four previously owner-approved historical organization upgrade tests
(17 cases) remain in source and manually selectable with `--select historical`;
they are not represented as passing development cases. No additional test was
proven obsolete, so no additional exclusions or deletions are made. New tasks must
continue inventorying their tests; missing/stale classification fails validation.

**Local validation:** `python3 -m unittest discover -s ci-review/.github/scripts
-p 'test_*.py'` passed 77 fixtures (60 classifier/aggregate/release and 17 docs).
New negatives cover missing third shard, missing/overlapping/skipped cases with
updated hashes, dependency skips, unexpected oracle receipts/modes and release
rejection of intentionally skipped development oracle. The weighted scheduler
fixture exercises three disjoint, nonempty balanced sets; invalid/empty shard
counts fail. YAML parsed with development default, explicit audit input and three
matrix members. Prior Full777 artifact verification and current-tree test/POM pin
comparison passed. Full canonical repository doc validation, Java21 Maven and live
PostgreSQL tests were not run locally: the checkout is partial, Maven is absent and
installed Java is 17. No environment was provisioned. Hosted exact-commit CI is the
authoritative acceptance follow-up; only queue/start observation follows publishing.

**Disposition:** HAPI-045 remains PARTIAL / IN_PROGRESS pending exact-commit hosted
acceptance and remaining representative/negative/cold-warm qualification. HAPI-046
and HAPI-436 statuses do not change. The next evidence step is to inspect the
naturally produced development run; future explicit audit evidence and comparable
new timings remain separate acceptance obligations. No production deployment,
release, tag or additional task is selected.

### 2026-10-20 — Full778 acceptance and measured fourth database set

Owner selected the next HAPI-045 stage and asked why three database sets rather
than four or five. Current main `2cd16f45aa0fa324eb4f89107ff5d7f9f805b9a8` has
successful Full778, Fast102 and Documentation458. Full778 development verification
is accepted within its exact scope: 430 classes, 2606 cases, zero failures/errors/
skips; no independent oracle or release qualification is claimed.

[Full778](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39430323819)
started at 13:25:54Z and completed with workflow update at 13:34:08Z. Job wall
measurements, excluding queue: static 21s, unit/architecture 70s, API/dictionary
180s, PostgreSQL0 432s, PostgreSQL1 321s, PostgreSQL2 472s, gate 13s. The explicit
audit oracle was intentionally skipped, as required for development mode. Job wall
sum is 1509s (25m09s runner time before billing rounding); the slowest DB job was
7m52s and missed the seven-minute target. Three was the smallest configuration
suggested by Full777's oracle timings, not an optimum or a hard concurrency limit.
This first hosted result justifies revising that choice; no unchanged benchmark
workflow was rerun.

The authenticated Full778 aggregate artifact 12268336552 has ZIP SHA256
`c9c9c3c94dd3274aa221051d962b4ffbe4e3d5b0211c3138a2c683820beb6d65`.
Downloaded receipt/file hashes, exact source/run/attempt/classifier/inventory,
raw XML classes/cases/counts/timings and Java21 clean-verify lifecycle were checked.
The six lane receipts cover the complete disjoint inventory and deny release
eligibility. Current/base OpenAPI, actual PostgreSQL16 catalog and the reviewed
physical dictionary passed `api_facts` against the unchanged migration/mapping
sources. This accepts the development run, not same-run oracle equivalence.

**Four versus five:** summing the actual database class times from Full778 gives
1042.315 suite seconds. Deterministic weighted scheduling using those measurements
produces the following estimates; no setup/compile/container/queue overhead is
included and no speedup is promised.

| Sets | Largest estimated suite sum | Change from preceding option |
|---|---:|---:|
| Three, refreshed weights | 347.442s (5m47s) | existing count, rebalanced |
| Four | 260.590s (4m21s) | about 87s less suite work on the critical set |
| Five | 208.482s (3m28s) | about 52s further reduction |

Full778 DB-job overhead above reported suite sums ranged from about 53s to 74s.
It varies with runner, compilation and Spring/container startup. Four supplies
more timing headroom with one extra isolated runner; five adds another complete
runner setup for a smaller estimated gain. Neither runner-cost neutrality nor
repeatable sub-seven-minute elapsed time is established. If four still misses,
inspect its actual timing evidence before deciding on five. Do not repeat runs
solely to manufacture acceptance samples. Three-set weights came from a serial
oracle; this correction uses actual partition executions, while acknowledging
that regrouping classes can change context reuse and timings again.

**Bounded correction:** preserve the previous HAPI-045 seven CI/record paths
(exclude PROJECT_STATE, already correct): ci.yml matrix, inventory scheduling
weights/provenance/count, classifier plus fixture, aggregate/release helper plus
fixture, and this phase file. Four DB sets contain respectively 20/23/24/27 classes
and 235/225/219/210 cases, with suite sums 260.581/260.590/260.587/260.557s.
The 336 unit/architecture classes and 1717 cases are unchanged. Every active class
still runs once in development CI. Required fourth-set evidence is enforced by
the aggregate and strict audit/release job list. Manual audit remains available.
No test source/case, archive selection, security deadline, retry, fork count,
container isolation, API, POM, migration, dictionary or Fast/release workflow is
changed. HidraAPI remains development-only with no production deployment/data.

**Validation:** 78 local fixtures passed (61 inventory/aggregate/release plus 17
docs), including four-set coverage, missing fourth receipt, corrupt/omitted/
overlapping/skipped fourth-set reports and release rejection of development
results. YAML matrix and manifest agree on four. Inventory comparison preserves
every field inside class entries except timing weights and preserves all original
case/source/dependency/owner/Fast/archive metadata. No Java21 Maven/PostgreSQL run
was performed locally; existing Java is 17 and Maven is absent. Exact published
CI follows, then stop at queue/start observation per AGENTS.md §11.1.

**Disposition:** HAPI-045 remains PARTIAL / IN_PROGRESS. Development acceptance
for Full778 is recorded; fourth-set runtime acceptance and representative audit/
cold-warm qualification remain pending. HAPI-046/HAPI-436 are not completed; one
new successful sample is not five comparable samples or a performance-adoption
campaign. Retain registered message `ci: automate fast feedback and preserve full
verification gates`. No release, tag, deployment or unrelated task is selected.

### 2026-10-20 — Full779 acceptance and owner-selected five database sets

Owner selected the next HAPI-045 stage and explicitly preferred five database
sets. Current main `d15cfa28c598aedd8371a98c943b8919b3b7a52f` passed Full779,
Fast103 and Documentation459. This stage prioritizes shorter development feedback
with one additional isolated runner; it does not claim a cost optimum or change
production/release policy. HidraAPI remains development-only.

[Full779](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39432208352)
job wall seconds: PostgreSQL0 282, PostgreSQL1 318, PostgreSQL2 373, PostgreSQL3
347; unit/architecture 80; static 23; API/dictionary 170; gate 14. The audit job was
intentionally skipped. The slowest DB job was 6m13s, compared with 7m52s for
Full778. Job wall sum was 1607s (26m47s runner time), compared with 1509s for
Full778. These are two individual natural runs, not comparable-configuration
medians, billing amounts or controlled proof that the extra runner caused all
of the difference. Four met the per-job seven-minute target in this sample;
repeatable critical-path/cold-warm/cost qualification remains incomplete.

Authenticated Full779 aggregate artifact 12268717648, ZIP SHA256
`1f2e109c1cbce9c78d0b59875ca54b134ddfdf8ca6c5dd883bdc2b5f038cf605`,
was downloaded and checked. Exact SHA/run/attempt/classifier/inventory, lane/file
hashes, raw XML class/case/count/timing payloads and Java21 clean-verify lifecycle
passed. Seven lane receipts cover 430 classes and 2606 cases exactly once, with
zero failures/errors/skips. Actual current/base OpenAPI, PostgreSQL16 catalog,
source bundle and reviewed physical dictionary passed `api_facts`. This accepts
Full779's development scope; there was no independent oracle and the aggregate
correctly denies same-run equivalence and release eligibility.

**Five-set scope:** same seven paths as the preceding correction: ci.yml,
ci-test-inventory.json, classifier and fixture, aggregate/release helper and
fixture, and this owning phase file. Refresh scheduling weights from Full779's
actual partition reports, retain original case/source provenance, set the DB
matrix and inventory to five, and require the fifth receipt/job in development
and audit/release verification. Every test/dependency/owner/case/source pin,
Fast membership, historical archive policy, lifecycle, fork count, security
deadline, retry and isolated database requirement is preserved. No Java, POM,
Flyway, API, dictionary, Fast/release workflow or PROJECT_STATE change.

Full779 DB suite sums total 1072.034s. With the refreshed weights, four sets model
a largest suite sum of 268.046s; five model 214.465s, about 54s less. This is a
scheduling estimate before setup/compile/container/queue overhead, not a promised
elapsed-time improvement. Regrouping classes changes context reuse; runner and
cache variation remain. Five is the owner's latency preference and its actual
runner cost and speed must be measured by subsequent naturally authorized runs.

| Five-set selection | Classes | Cases | Prior suite seconds summed |
|---|---:|---:|---:|
| PostgreSQL0 | 16 | 163 | 214.400 |
| PostgreSQL1 | 18 | 145 | 214.393 |
| PostgreSQL2 | 19 | 126 | 214.465 |
| PostgreSQL3 | 22 | 225 | 214.388 |
| PostgreSQL4 | 19 | 230 | 214.388 |

Unit/architecture remains 336 classes and 1717 cases; the DB union remains 94
classes and 889 cases. No additional obsolete tests are excluded. Manual audit
continues to require the unrestricted oracle alongside all five partitions;
development results cannot satisfy release proof.

**Local validation:** 79 fixtures passed (62 classifier/aggregate/release and 17
docs). Five-set positive coverage, missing fifth receipt, corrupt/omitted/
overlapping/skipped fifth-set evidence and development release rejection are
covered. YAML/manifest agree on five; the complete disjoint class/case union and
all unchanged non-timing inventory metadata were checked. Full779 authenticated
artifact verification passed. Full canonical checkout docs and Java21 Maven/
PostgreSQL tests are not run locally (partial checkout, Java17 and no Maven);
hosted exact-commit CI remains authoritative. No environment was provisioned.

**Disposition:** HAPI-045 remains PARTIAL / IN_PROGRESS; Full779 development
acceptance is recorded, five-set runtime acceptance and representative audit/
cold-warm qualification remain pending. HAPI-046 and HAPI-436 do not advance.
Retain exact registered message `ci: automate fast feedback and preserve full
verification gates`. Publish only this bounded stage, confirm exact-SHA workflows
queued/started and stop per AGENTS.md §11.1. No release, tag or deployment follows.

### 2026-10-20 — Full780 five-set development acceptance

Owner selected the next HAPI-045 stage. Main
`5a923ce0a91898d9ea17e0e97fa9f4f87d80faa1` passed Full780, Fast104 and
Documentation460. Accept the five-set implementation within its development
scope; retain five sets and current scheduling weights. No additional tuning or
unchanged benchmark rerun is justified by this successful result.

[Full780](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39433631502)
started at 13:48:41Z and completed with workflow update at 13:54:29Z, an observed
workflow interval of 348s (5m48s). Job wall seconds: PostgreSQL0 268, PostgreSQL1
313, PostgreSQL2 330, PostgreSQL3 307, PostgreSQL4 293; unit/architecture 97,
static 20, API/dictionary 183 and gate 10. The unrestricted audit oracle was
intentionally skipped under the development policy; GitHub's skipped-job timestamps
are not meaningful runtime and are excluded from runner sums.

| Naturally completed configuration | Slowest DB job | Sum of executed job wall time |
|---|---:|---:|
| Full778, three DB sets | 7m52s | 25m09s |
| Full779, four DB sets | 6m13s | 26m47s |
| Full780, five DB sets | 5m30s | 30m21s |

Five's slowest DB job is 43s shorter than four's in these samples, while summed
job time is 214s higher. These are observations from individual runs with different
scheduling weights/runner/context/cache conditions; they do not isolate causality,
prove a stable speedup or represent GitHub billed minutes. The owner prefers five
for shorter elapsed feedback. Full780 is below the seven-minute elapsed target;
one sample does not satisfy HAPI-046's five-comparable-sample or cold/warm rules.

Authenticated aggregate artifact 12268913382, ZIP SHA256
`860f96dabc1f8f8e5adfb0ea691a792a33d4cb973f2ab34fedff77810f7822d1`,
was downloaded and checked. Its eight receipts and hashed files match the exact
SHA/run39433631502/attempt1/classifier/inventory. Raw report membership, invocation
identities, counts/timings and Java21 clean-verify lifecycle agree. The complete
disjoint union is 430 classes and 2606 cases, with zero failed, errored or skipped
cases. DB cases by set are 163/145/126/225/230 (889 total); unit/architecture has
1717 cases. No tests were deleted or newly archived. Development evidence correctly
sets same-run equivalence and release eligibility false.

Actual current/base OpenAPI and migrated physical PostgreSQL16 dictionary evidence
passed `api_facts` against the unchanged source bundle and reviewed dictionary.
The catalog is captured from a fresh migrated database, not a reused pre-green
schema. This does not prove production-data upgrade or certify deployment.
HidraAPI remains a development API without production deployment/data.

**This stage writes only this owning task record.** No executable/configuration,
source, test inventory, weight, migration, API or dictionary changes. Artifact
verification and 17 documentation-validator fixtures passed locally; existing
payload boundaries/anchors and all HAPI task/status fields are preserved. Hosted
Full780 supplies Java21/PostgreSQL evidence for the implementation SHA. No new
Java/service execution is claimed for this documentation-only stage.

**Remaining qualification:** HAPI-045 stays PARTIAL / IN_PROGRESS because
representative code/security, migration/API audit equivalence and cold/warm proof
are not all established for the new arrangement. The next distinct verification
is `.github/workflows/ci.yml` via `workflow_dispatch` on main with
`verification_mode: audit`; this runs the independent oracle plus all five sets
and checks same-run equivalence. It is an equivalence exercise, not a repeat of
unchanged development timing. The connected GitHub tools expose no workflow-
dispatch action, so this record does not claim that audit was launched. A manual
operator run remains available at the workflow Actions page. No production
calibration input is needed for this software qualification. HAPI-046/HAPI-436
remain open; no release, tag or deployment is selected.

Retain registered commit message `ci: automate fast feedback and preserve full
verification gates`. Confirm documentation CI queued/started after publication,
then stop per AGENTS.md §11.1.

### 2026-10-20 — Full781 independent five-set oracle acceptance

Owner selected the next HAPI-045 stage after manually dispatching the audit.
Main `96af69e5c02ac246becbc608cb9443428a3f115a` has successful Documentation461
and [Full781 audit](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39435132363),
attempt1, event workflow_dispatch. Every required audit job succeeded, including
the independent Java21 Maven verification. No retry or rerun was requested here.

Authenticated aggregate artifact 12272213168 has ZIP SHA256
`b88c3b3004570477d79f683fbbfab0512314fc3d591f31686139e768f11dd5ea`.
The downloaded archive and all nine receipt/file hashes were checked against the
exact source/run/attempt/inventory/classifier. The actual job list is the expected
five DB jobs, unit/architecture, static, API/dictionary, oracle and full gate, all
successful. Report payloads were recomputed from the hashed raw XML. Required
Java21 clean-verify lifecycle/POM/package identity, owning job identity and static
workflow/check identity agree.

**Accepted independent equivalence:** the disjoint unit plus five DB sets and the
separate unrestricted oracle each cover exactly 430 classes and 2606 cases.
Both executions have zero failures/errors/skipped cases. This means 2606 unique
required cases executed independently in both arrangements, not 5212 distinct
cases. No omitted or overlapping class/case is accepted. Current/base OpenAPI,
historical base identity, physical catalog, source bundle and PostgreSQL version
match between the API lane and oracle via `api_facts`; reviewed dictionary equality
also passed. Aggregate `same_run_equivalence: true` is now backed by this run.
`release_eligible: true` describes full-CI evidence only, not release authorization,
production deployment readiness or CI-003 performance adoption; the aggregate
still sets `optimized_adoption_eligible: false`.

Matched API SHA256:
`4313e274aeb067b22992d75f623b11c4f77bc725e8a7895c817884da32629e21`.
Matched physical catalog SHA256:
`d8ef5265ef60620860627ae62ef5977daa5acbfb50581f1a0af1f1247e630032`.
Source bundle SHA256 remains
`be0f97cd7280aa97c0e84de040a632fbf494518e705fa10b358f793d136b9221`;
base is `5a923ce0a91898d9ea17e0e97fa9f4f87d80faa1`.

**Timing and cache limits:** DB jobs0..4 took 234/313/312/325/298s; unit 185s,
static 27s, API/dictionary 150s, oracle 1271s and gate 13s. The 21m11s oracle job
is intentionally confined to explicit audit mode; it does not replace the
accepted development Full780 result of 5m48s. Do not count full audit workflow
elapsed time as a default-development timing sample. No weights or scheduling
changes follow from this audit. The five-set configuration is retained.

Authenticated unit job118670054802 and DB3 job118670054519 logs both explicitly
report a Maven dependency-cache primary-key hit and successful restoration of
about119MB. This establishes warm dependency cache for those jobs only. It does
not prove that all jobs were warm, that Spring/container state was shared or that
a cold-cache run occurred. The test databases remain freshly migrated and isolated.
No production data or calibration input is needed for this software qualification.

| HAPI-045 obligation | Current disposition |
|---|---|
| Five-set development implementation and exact coverage | Accepted Full780 |
| Independent same-run class/case/API/catalog equivalence | Accepted Full781 |
| Negative omitted/failed/skipped/overlap/stale/attempt/dependency/release checks | Existing 79 local fixtures and successful hosted static validator run; no live fault injection claimed |
| Warm dependency-cache evidence | Matched unit and DB3 logs only |
| Cold-cache equivalence | Not established |
| Representative code/security, migration and API revision qualification | Remaining source-specific matrix; current audit source follows CI/docs changes, not a new representative application revision |
| Repeatable performance/cost adoption | HAPI-046, not completed by one development and one audit run |

**Bounded stage:** only this owning task file changes. No runtime, workflow,
classifier, test, inventory, migration, API, dictionary, version, release or tag
changes. Artifact verification passed;17 documentation-validator fixtures and
preserved task/status/anchor/payload checks passed locally. No new Java/service
test execution is claimed for this documentation-only stage.

**Disposition:** HAPI-045 remains PARTIAL / IN_PROGRESS, with independent audit
acceptance now recorded rather than pending manual dispatch. Remaining work is
cold-cache and representative-revision qualification using explicit or naturally
authorized evidence, not repeated unchanged development benchmarks. HAPI-046 and
HAPI-436 remain open. HidraAPI remains development-only with no production
API/database deployment. No release, tag, deployment or unrelated task is selected.
Retain exact registered message `ci: automate fast feedback and preserve full
verification gates`; observe documentation CI queued/started and stop.

### 2026-10-20 — explicit cold dependency-cache qualification path

Owner selected the next HAPI-045 stage from green main
`80a4962d85301fecd3931ed989f4f2aa28f7cbae` (Documentation462 success).
Full781 already accepted independent five-set equivalence, but its matched logs
proved warm Maven dependencies only. Implement a bounded cold-cache evidence path;
do not fabricate a cold observation or rerun unchanged development timings.

**Exact write scope:** `.github/workflows/ci.yml`,
`.github/scripts/verify_ci_test_inventory.py`,
`.github/scripts/test_verify_ci_test_inventory.py`,
`.github/scripts/verify_full_ci_evidence.py`,
`.github/scripts/test_verify_full_ci_evidence.py` and this owning task record.
This refines HAPI-045's existing CI/helper/negative-proof scope to close the
identified cache-provenance gap. No new file or inventory/class/case/weight change.
Retain registered message `ci: automate fast feedback and preserve full
verification gates`.

Manual workflow input `dependency_cache` offers `cached` (default) or `cold`.
Push/PR development verification retains normal Maven cache restoration, five DB
sets and the same 2606 required cases. Cache policy is separate from the existing
`verification_mode` input: a cold equivalence qualification uses `audit` plus
`cold`. A development-only cold run does not prove independent oracle equality.

Every Java lane records pre-build cache provenance before invoking Maven. In cold
mode setup-java has no Maven cache setting; the preparation helper creates a new
`RUNNER_TEMP/hidra-cold-maven-repository` with fail-if-existing semantics and
observes zero entries. It selects that path through `-Dmaven.repo.local` in
MAVEN_OPTS for current and historical-base builds. The oracle's bounded heap
override explicitly retains the same repository property. No restored ~/.m2
repository is used for dependency resolution, and nothing is deleted. The receipt
and isolated repository live outside target so clean verify cannot erase them.
This is cold Maven dependency-repository proof, not a claim that Java toolchains,
Maven wrapper distributions, Docker image layers or operating-system caches are
cold. Warm-path cache hits/misses are observed, not presumed.

The pre-build receipt records source/run/attempt/owning job, requested policy,
restore enablement, observed cache hit, initial entry count and selected path.
Existing hashed lane receipts embed it. The aggregate requires policy consistency
across unit, every DB set, API and oracle where applicable; missing/stale/mixed
receipts fail. Cold acceptance additionally rejects nonempty/reused repositories,
a restoration hit, enabled restoration or the wrong isolated path. Existing
class/case/lifecycle/API/catalog/attempt/dependency negative checks remain. Release
recomputation preserves the recorded cache mode while still requiring complete
independent audit evidence; development remains ineligible.

**Validation:** 88 Python fixtures passed (71 classifier/aggregate/release and17
docs). New cases cover empty isolated preparation and exported Maven property,
reuse of even an empty repository, restored-cache/conflicting-property rejection,
receipt propagation into source-linked evidence, cold audit equivalence, missing/
mixed/stale/nonempty/wrong-path cache observations, and truthful cache-miss
reporting. Existing fixture coverage remains. YAML parsed; all four Java job
templates prepare their repository before the first Maven invocation, cold setup
has no cache restore, defaults remain cached and the oracle retains the explicit
repository property. Five-set inventory and test source/case selection are
unchanged. No Maven/Java21/service environment was provisioned or run locally;
hosted exact-SHA CI validates the cached path after publication.

**Acceptance follow-up:** after the normal exact-commit CI result, a manual
`.github/workflows/ci.yml` dispatch on main with `verification_mode: audit` and
`dependency_cache: cold` produces the distinct cold equivalence proof. The current
connector has no workflow-dispatch action; no such run is claimed here. The
Actions workflow page exposes both inputs. Existing audit job timeout and all
security deadlines remain unchanged; a dependency-download timeout must fail and
be diagnosed, not be relabeled passing or have tests skipped. No production data
or owner calibration input is needed.

HAPI-045 remains PARTIAL / IN_PROGRESS until hosted cache-path acceptance,
actual cold audit and remaining representative-revision coverage. HAPI-046 and
HAPI-436 do not advance. No repeated benchmark campaign, release, tag or production
deployment is selected. HidraAPI remains development-only. Publish this bounded
stage, observe applicable exact-SHA CI queued/started and stop per AGENTS.md §11.1.

### 2026-10-20 — cold Full783 acceptance and optional serial diagnostic

Owner explicitly approved the proposal to keep the slow unrestricted serial
oracle optional for this development-only API, reported Full783 passing and
selected the next HAPI-045 stage. This is a bounded owner-selected amendment of
the earlier requirement that every manual audit include the oracle. Default
push/PR and manual audit execution now use the complete parallel sets. The
serial oracle remains available only through a separate explicit audit opt-in;
parallel-only success never claims independent same-run equivalence.

Current parent `11b1ca458d623ed4fcca269b7a82f00b88c0b8f9` passed Full782,
Fast105 and Documentation463. [Full783](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39441957834)
was independently checked as an actual cold-cache audit on that exact source,
attempt1. Authenticated aggregate artifact12276945252 has ZIP SHA256
`e23cc9a8562a0e75bb80157d5ea40c3d7637127b53a612cab1eb93318bdf23e7`.
Archive/receipt/file hashes, source/run/attempt/classifier/inventory, actual job
success, raw class/case/count/timing reports and Java21 clean-verify lifecycle
agree. All eight Java lane cache observations passed cold-mode validation: no
cache restoration, zero initial entries and the expected new runner-temporary
Maven dependency repository. Independent oracle and partition union each cover
430 classes/2606 cases with zero failures/errors/skips; current/base OpenAPI and
actual migrated catalog/dictionary agree. Cold Maven dependency-repository
equivalence is now accepted within this source/configuration, not pending.
This does not claim cold Java distributions, Maven wrapper or Docker image caches.

Full783's oracle job took1279s (21m19s); DB sets0..4 took315/340/356/340/314s,
unit116s, static21s, API208s and gate16s. This explicit audit proves correctness
and cache provenance; its serial critical path is not a default-development
latency sample. No timing rerun was requested in this stage.

**Approved execution semantics:** new boolean workflow input `serial_oracle`
defaults false. Job `verify` requires workflow_dispatch, `verification_mode:
audit` AND `serial_oracle: true`. Both development and audit otherwise execute
all active cases through unit/architecture plus five isolated DB sets, with the
existing API/base compatibility and actual dictionary lane. Dependency cache
selection remains independent (`cached` default, `cold` optional). No test case,
class/source pin, archive, scheduling weight, fork count, deadline or retry changes.

The aggregate receives the oracle opt-in explicitly from the workflow. Without
it, only the oracle job may be intentionally skipped; all other required jobs,
lanes/classes/cases/lifecycle/cache/API/catalog evidence remain mandatory. An
unexpected oracle artifact or failed/cancelled oracle cannot be hidden as a skip.
Parallel audit receipts say `mode: audit-partitioned`, `serial_oracle: false`,
`same_run_equivalence: false` and `release_eligible: false`. With the opt-in, all
independent same-run comparison requirements remain and the serial job must
succeed. Internal release recomputation remains strict and requires oracle
success/evidence; a parallel-only audit cannot qualify a release. This preserves
future release controls without running the serial diagnostic by default.

**Exact write scope:** `.github/workflows/ci.yml`,
`.github/scripts/verify_full_ci_evidence.py`,
`.github/scripts/test_verify_full_ci_evidence.py`, `PROJECT_STATE.md` and this
owning phase file. PROJECT_STATE records the approved optional policy rather
than the superseded always-serial audit wording. No CREATE/DELETE, inventory,
classifier, Java, POM, Flyway, dictionary, API, Fast/release workflow or version
changes. Retain registered commit message `ci: automate fast feedback and
preserve full verification gates`.

**Local validation:**93 fixtures passed (76 classifier/aggregate/release and17
docs). New cases exercise parallel audit positive coverage, cold-cache parallel
audit and retained negative checks, unexpected oracle/hidden failure rejection,
explicit serial comparison, invalid flags and CLI opt-in defaults. Existing
release rejection, missing/stale/skipped/overlapping report/cache checks remain.
YAML parsed with serial default false, matching job/gate predicates and all five
DB matrix members. Full783 raw artifact verification passed. No new local
Java21/Maven/PostgreSQL execution was performed or environment provisioned;
hosted exact-commit CI follows publication, then stop at queue/start observation.

**Disposition:** keep HAPI-045 PARTIAL / IN_PROGRESS pending hosted acceptance of
this optional-mode correction and the remaining representative code/security,
migration and API revision qualification. Warm Full781 and cold Full783
independent equality are recorded, so do not ask for the same cold audit again
merely to repeat evidence. HAPI-046 still owns comparable timing/cost/adoption
qualification; HAPI-436 does not close. HidraAPI remains development-only, with
no release, tag or production deployment selected. Manual independent diagnostic
when actually needed: audit plus `serial_oracle: true`; normal audits leave the
checkbox unchecked and use the parallel sets.

### 2026-10-20 — HAPI-045 representative matrix and completion

Owner reported the optional-mode test passing and selected the next stage.
Exact main `887b84c68479a0df01e0825b52f347ce798a7ed5` passed Full784,
Fast106 and Documentation464. Reconcile existing representative application
changes and actual raw executions; no application mutation or benchmark rerun is
needed. HAPI-045 is VERIFIED / COMPLETED within this development CI scope.
This does not complete HAPI-046 performance/cost/adoption or parent HAPI-436.

**Current implementation acceptance:**
[Full784](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39445933061),
attempt1, authenticated artifact12277231545, ZIP SHA256
`9dbf449cb416721bba8f403c0a000a774449d4f8ff9a9c860bbaeccf27b1c436`.
Downloaded receipt/file hashes, source/run/attempt/classifier/inventory, all raw
class/case/count/timing payloads, Java21 clean-verify lifecycle, actual current/
base API and physical dictionary/catalog were verified. The complete disjoint
union is430 classes/2606 cases with zero failures/errors/skips. All seven Java
execution sets report successful dependency-cache restoration; this is actual
warm-cache evidence. The optional serial job was intentionally skipped, as required
for default development. The aggregate truthfully denies independent same-run
comparison and release eligibility. The 93 hosted validator fixtures passed with
all required static checks; no live CI fault-injection campaign is claimed.

Full784 workflow interval was369s (6m09s); its slowest DB job was348s (5m48s).
These are single-run observations, not a repeatable-performance acceptance claim.
No weights, set counts, deadlines, forks or retries change in this stage.

**Representative revision recovered from actual GitHub history:**
[accepted pre-feature to protected survey authoring](https://github.com/CHOUABBIA-AMINE/HidraAPI/compare/b49fbeadea2319f7349540aa5ac08d3d3afdebe4...067929306170ad69b453823a90bb7beeb6b8130d).
This naturally authorized HAPI-589 feature changed15 runtime/configuration paths,
including protected authorization/transaction code, an additive Flyway migration
and GET/PUT API operations. Earlier notes left this matrix pending because only
cumulative run results had been listed. The reconciliation below now binds the
actual source delta, new methods, exact source hashes and raw invocation identities.
One cohesive feature supplies all three required change categories; it is not
three invented application changes or an exemption from representative coverage.

GitHub's complete source comparison from feature source0679293 through current
887b84c contains9 commits and11 changed paths, with no src/ or POM changes. The
representative runtime/test blobs match current main. This permits those exact
feature identities to be traced through the baseline and the later five-set runs;
it does not assume that an unrelated green cumulative run proves a task.

| Exact representative suite (package root dz.sh.hidra) | Executed cases | Added cases | Matched scope |
|---|---:|---:|---|
| modules.topology.SurveyRouteProfileBoundaryGuardrailTest | 3 | 3 | Owner/transport boundaries; exactly two registered operations |
| modules.topology.api.rest.SurveyRouteProfileControllerTest | 5 | 5 | Strict DTOs, unknown/coerced fields, versions and non-disclosing errors |
| modules.topology.application.service.SurveyRouteProfileApplicationServiceTest | 4 | 4 | Typed command, native owner, rollback and no uncertain-write retry |
| modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest | 39 | 12 | Actual migrated database, negative references/privileges, rollback, history and concurrent writers |
| modules.topology.infrastructure.persistence.adapter.SurveyRouteProfileCodecTest | 5 | 5 | Canonical bytes, malformed/truncated/noncanonical payload rejection |
| platform.permissions.HidraRouteAuthorizationInterceptorTest | 8 | 1 | Exact authenticated native survey controller/method delegation |
| platform.persistence.ProtectedTransactionExecutorTest | 37 | 1 | Diagnostic admission cannot mint a survey mutation permit; rollback |
| Distinct suite total | 101 | 31 | Actual feature-source changes and unchanged current identities |

All101 exact invocation identities, including31 methods added by the real GitHub
diff, were independently matched in raw XML for Full777, Full781, Full783 and
Full784. In Full777/781/783 the full partition union equals the separate unrestricted
oracle; Full781 is the warm five-set audit and Full783 the instrumented cold
five-set audit. Full784 is the accepted optional-mode/default execution, correctly
without an oracle. These are executions of the same unchanged representative
feature source across CI arrangements, not a claim that new application revisions
were introduced after every CI change.

**Migration and API evidence:** the actual added migration is
`src/main/resources/db/migration/V20261020_001__p25_topology_survey_profile_snapshots.sql`,
Git blob `929934e6add72be201b12d0a831e9660c88b0e7c`. It creates
`public.hidra_topology_engineering_survey_snapshot`, owned graph/metadata references,
digest/transaction constraints and the guarded same-transaction trigger.
The 12 added PostgreSQL cases include
`surveyDatabaseRejectsWrongTransactionDigestAndGraphBinding`,
`surveyCatalogAndPrivilegeTamperingRejects`,
`expiredSurveyGuardRollsBackBothRows`, historical exact reads and concurrent
successor/facility-fence checks. Actual Flyway validation and current catalog/
reviewed dictionary equality passed; a Docker skip is not counted as proof.

The actual generated current API contains GET and PUT at
`/api/v1/topology/engineering-networks/{draftId}/survey/{branchId}`; the changed
controller/DTO snapshot and five transport cases are matched above. Same-run
current/base compatibility and physical catalog equality in the warm/cold audits
remain accepted. Physical source bundle SHA256 is unchanged:
`be0f97cd7280aa97c0e84de040a632fbf494518e705fa10b358f793d136b9221`.
No production-data upgrade, deployment or engineering calibration claim follows.

**Acceptance disposition:**

- Representative code/security, migration and API changes: source-and-case matrix above, independently matched against baseline/warm/cold/current raw evidence.
- Warm/cold equality: Full781 and Full783 independent comparisons; Full784 records successful warm dependency restoration for every active Java lane. Cold is Maven dependency-repository state, not all machine caches.
- Missing/failed/skipped/overlapping/stale/cancelled/wrong-attempt/cache-policy evidence: required helper checks and 93 fixtures remain; parallel-only evidence cannot claim oracle/release proof.
- Explicit owner development adoption: five complete parallel sets, optional serial diagnostic, retained historical tests and truthful release restrictions remain as approved; no extra exclusions.
- Applicable exact-source Full/Fast/Docs: passed on887b84c; this stage is documentation-only.

**Exact stage scope:** only this owning HPS-024 record and the HAPI-045 status
cells in ULTIMATE_ROADMAP.md and ULTIMATE_PHASES.md. Set HAPI-045 to VERIFIED /
COMPLETED in all three; preserve every other task/status and immutable historical
payload. No runtime/workflow/helper/inventory/dictionary/API/SQL/version change.
The raw-artifact verification and representative101-case/31-added-method matrix
checks passed;17 documentation-validator fixtures and targeted status/anchor/
payload checks passed locally. Full canonical repository validation is left to
hosted Documentation CI because the local checkout is partial. Retain registered
message `ci: automate fast feedback and preserve full verification gates`.

**Next:** HAPI-046 owns the separate comparable timing, runner cost and independent
adoption/closure decision. Its prerequisite HAPI-045 is now satisfied; its existing
status is not automatically promoted or its acceptance waived here. HAPI-436
remains open. Use previous naturally completed runs and account for configuration/
source/cache differences; do not create unchanged benchmark repeats to fill a
sample quota. HidraAPI remains development-only. No production deployment, release,
tag or unrelated task is selected. Observe Documentation CI queued/started, then
stop per AGENTS.md §11.1.

#### HAPI-045 owner-requested measured scheduling maintenance — 2026-10-26

The owner explicitly raises two concerns: workflow failure and overloaded shard1.
This timing maintenance accompanies the independently scoped HAPI-592 repairs;
it does not reopen or promote the accepted HAPI-045/046/436 statuses. Registered
message: `ci: automate fast feedback and preserve full verification gates`.
Exact2 MODIFY/zero CREATE/DELETE: .github/ci-test-inventory.json and only this
append-only HAPI-045 phase record. No workflow/helper/mode or Java source change.

Old timing_refresh comes from Full826; the new eight-case qualification class
still has explicitly unmeasured weight0. The greedy seven-shard scheduler cannot
balance today's workload from stale/zero weights. Recover actual Full907
run40370015468 attempt1 source1d6025e22fb585e8b1ec6b5ad39a0d65aa3514f3:
all102 active PostgreSQL class XMLs across all seven shards, exact existing case
membership, declared/actual report context and authenticated ZIP/member digests.
Six successful lane receipts retain source/run/attempt and member hashes; failed
shard1 uses its raw diagnostic artifact. Preserve all eight artifact metadata
origins separately; timing weights cannot turn the failed run into acceptance.
Inventory timing_refresh records all seven ZIP IDs/digests,102 raw XML digests,
same-source context and complete nested prior timing-refresh history.

Actual suite-time sums by previous shard0..6 (seconds):
195.558 /651.508 /159.004 /266.985 /160.887 /217.054 /216.805.
The large employee-appointment concurrency class alone measures352.623s;
network-draft127.809s, station-admission133.209s and new qualification73.780s
also contribute. That qualification time includes seven passes and one failed
consumer case, so it is explicitly a partial scheduling observation/lower bound,
not a fully accepted duration or a new passing benchmark. Retain the actual
observed value rather than invent a repaired runtime. Other classes have zero
failure/error/skip counts. The next real hosted run must expose current timing.

Update baseline_seconds for only those102 active PostgreSQL classes to the actual
reported suite times. Do not alter their source/case identities, dependency
reasons, Fast membership, archived classes, unit weights or seven-shard count.
The unchanged descending-weight least-loaded scheduler yields estimated sums:
352.623 /252.510 /252.480 /252.518 /252.694 /252.483 /252.493 seconds.
The largest class is alone on shard0; all six others are about252.5s. No scheduling
of indivisible classes can beat the352.623s largest-class lower bound for this
sample; adding more shards would not remove it. No class split or same-database
parallel execution is admitted. Predicted largest suite sum decreases from
651.508 to352.623s (about45.9%), but compilation/startup/runner contention/queue
time are not in this metric. This is not a measured final workflow speedup or SLO.

Preserve every current executable class and case, historical archive selection,
source hashes, exact-class validation and seven isolated full database sets.
The union of all seven new selections is identical and disjoint; unit/Fast/oracle/
historical selections stay identical. Optional serial diagnostic and all API/
catalog/provenance/failure/release gates remain unchanged. No obsolete-test
deletion, excluded failing case, hidden retry or relaxed failure handling.

Available51 validator self-tests, current partition/identity/hash/receipt/member/
timing-arithmetic and exact scope/history checks PASS. The failed Full907 supplies
scheduling evidence only, never green acceptance. Preserve older dated CI timing
and cost decisions; this sample is not a cold/warm benchmark repeat. Publish as
the third commit in the owner's reviewed repair/artifact/balance sequence under
the original expected1d6025e lease; verify each parent/tree/scope and observe one
final applicable Full/Fast/Docs startup, then STOP without waiting. HAPI-592 stays
pending exact-source acceptance; no release/deployment, simulator or OT action.

### 2026-10-27 — Full928 repair and measured seven-shard rebalance

Owner explicitly requests failing CI repair, load redistribution and one/two
additional shards only if needed. This is one bounded corrective stage under
the retained exact message
`ci: automate fast feedback and preserve full verification gates`.
Expected main97f3295d1b7dbfde3b27f08c08a9cfad45c415f9,
tree32d829c82fe4848f4d03afce422108ead6df6c0e. Full928/
run40581505626 fails; Fast232/Docs639 succeed. Failure disposition is retained.
No programme, deployment or performance acceptance is inferred from this repair.

Exact MODIFY scope: .github/ci-test-inventory.json,
.github/scripts/verify_ci_test_inventory.py,
.github/scripts/test_verify_ci_test_inventory.py,
src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProtectedProductPublicationAuthorizationPostgresIntegrationTest.java,
doc/database/DATA_DICTIONARY.md, TASKS_HPS-008.md and this owning phase.
No CREATE/DELETE, workflow/matrix change, test exclusion, POM, migration, oracle,
Fast membership, archive policy, test timeout, native time budget or release-gate
change. The simultaneous Fail/rebalance instruction admits the responsible
reviewer fixture and actual catalog correction in this same CI repair envelope;
their detailed evidence belongs to596. No dependent runtime package is selected.

**Raw evidence and source integrity.** Download the seven actual PostgreSQL
artifacts and the unit lane from Full928 attempt1. Verify ZIP hashes, receipt
SHA/run/attempt/classifier/inventory and every retained report hash; independently
match all481 raw class identities/cases against the original exact inventory.
The failed PostgreSQL2 diagnostic lacks a successful lane receipt: verify its
ZIP and all26 raw class/case identities without inventing a receipt. There are
3473 executed cases and one reviewer fixture error, zero failures/skips. All112
active PostgreSQL suite times are recovered, including ten formerly zero-weight
suites. Keep that failed suite's31.200s as explicitly partial scheduling evidence,
not a passing runtime result; its fixture source hash changes independently.
The inventory retains original timing provenance under prior_refresh.

| Original lane | Artifact ID | ZIP SHA256 |
|---|---:|---|
| PostgreSQL0 |12769303603|794bfd43236a5a96df5b03537a76c2504cff11b36ee7da92945fb6a5a5dde30b|
| PostgreSQL1 |12769103801|f06e88bb4c1ab7f26dd92af57be1094f081335abf90ce814b72a6eec5fd8f834|
| PostgreSQL2 diagnostic |12769978740|19351dc7c02b5e594b53cd0499f9d16d464f9bf0335f538af29025537b12c153|
| PostgreSQL3 |12770382190|7d9fe716826a27e6235d06c26dbb54d41b275edbc8aa67f236fc90205126aa1d|
| PostgreSQL4 |12769523110|3087879e10e3b4a2880037e37712fab47ceb37e5daa29296867809ed0d19d57e|
| PostgreSQL5 |12769718480|33adcf6c5b96f2d13bcfe7cd7688efadeab65f18c68c1294dfa9e46e7fc8aa00|
| PostgreSQL6 |12770257285|d0099be059792ada2aa665a92b9372858b0b0cc11c84b5c398de25992aec56d8|
| Unit/architecture |12770570880|687120488579d61bdcbbb03eb72b961a9f9f2f3da765478b1da6acbcb6fc87f5|

**Observed imbalance and selected correction.** New suites used0/unmeasured
weights. The previous greedy scheduler added zero to its minimum lane, so every
subsequent unknown suite could select that same lane. PostgreSQL2 consequently
carried26 classes and524.204 measured suite seconds despite a252.480s old
weight total. Refresh only actual112 PostgreSQL weights and the one repaired
fixture source pin; preserve all class/case/owner/lane/Fast/support identities.

The scheduler now uses the median positive active PostgreSQL measurement as a
scheduling-only weight for unmeasured suites, or1 if none are measured. It leaves
recorded zero values untouched and sorts/assigns deterministically. This is an
explicit estimate, never a fabricated measured time. Two new regressions prove
mixed measured/unmeasured and entirely unmeasured suites stay complete, disjoint,
nonempty and deterministic without mutating the manifest. Both fail against the
old implementation; all34 current classifier tests pass.

| Shard | Prior measured suite sum, seconds | Rebalanced estimate, seconds | New classes |
|---|---:|---:|---:|
|0|388.002|388.002|1|
|1|256.215|308.024|18|
|2|524.204|308.261|18|
|3|277.402|308.189|17|
|4|280.125|308.221|19|
|5|254.991|308.033|20|
|6|255.903|308.112|19|

The largest estimated suite load falls from524.204s to388.002s, about26 percent.
This is scheduling analysis of an existing run, not measured new job/wall speed.
Original job wall seconds were452,322,594,341,350,330,330 respectively, including
setup/compilation/container/cleanup overhead; their sum is2719 runner seconds.
Queueing, environment/cache variation and future overhead are not predicted.

**Why keep seven rather than add one/two.** The82-case
EmployeeAppointmentConcurrencyPostgresIntegrationTest alone costs388.002s under
the existing whole-class contract. Seven refreshed sets put all other sets near
308s; eight would put them near264s and nine near231s, but each option retains
that same388.002s critical suite. Additional runners do not lower this measured
whole-class bound. Keep seven, with no new matrix cost or unreviewed count.
Any future decomposition of that suite requires a separately reviewed scope
preserving all82 cases and oracle/evidence identities; do not split or omit it
implicitly in this balancing repair.

**Preservation and validation.** Strict exact-SHA/run/attempt/raw case verification,
development/audit distinction, independent audit oracle and release rejection
remain unchanged. All51 Full-gate self-tests PASS alongside34 classifier tests.
No validators or tests are weakened. Full local repository execution remains
unavailable; use existing tools only under AGENTS11.1. Actual API-lane catalog
recovery independently repairs the dictionary; native positive fixture now
declares the human display it asserted. The19 documentation self-tests, one-file Java syntax parsing,
actual catalog equality and final seven-file scope/status/history/source checks
also PASS before the guarded direct-main commit. Observe new applicable Full/Fast/Docs CI creation/
queue/start and STOP; no unchanged benchmark run, B package, release or deployment
is selected automatically. Current source statuses remain unchanged; hosted
acceptance and actual new timings are pending the corrective run.


<a id="hapi-046"></a>
## HAPI-046 — Timing, runner cost and adoption decision

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: CI-003D

Priority: P0

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-045

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Evaluate repeatable timing/cost and make the independent adoption/closure decision.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Complete CI-003 representative/negative/performance qualification and explicit adoption decision; preserve mandatory fallback, security generations and IT/OT segregation. The exact bounded remaining delivery is: Evaluate repeatable timing/cost and make the independent adoption/closure decision..

### 4. Functional Requirements

At least five comparable samples per configuration, warm Fast median<=3m and Full critical-path median<=7m targets, cold-cache/failure/flake limits and runner-minute accounting. Unmet target means CI-003 remains open with fallback; no favorable single-run or assumed speedup.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / DELETE:** none preauthorized.

**MODIFY — only if accepted evidence demonstrates a correction is necessary:**

- `.github/workflows/ci.yml` — preserve complete mandatory selections, services and lifecycle; no oracle removal until explicit adoption.

- `.github/scripts/verify_full_ci_evidence.py` — preserve same-run class/case/API/catalog/source/attempt equality and negative rejection.

- `.github/scripts/test_verify_full_ci_evidence.py` — actual new independent negative cases when necessary.

- `doc/roadmap/ULTIMATE_TASKS.md` — retain measured evidence or no-code-needed decision.

**PRESERVE / REUSE:** `.github/ci-test-inventory.json`, `.github/workflows/ci-fast-feedback.yml`, unrestricted oracle and retained four historical upgrade tests.

**DISCOVERY_REQUIRED:** exact remaining representative/cold-warm/timing evidence package and any change allowlist. Never rerun unchanged Full solely to repeat known latency.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- At least five comparable samples per configuration, warm Fast median<=3m and Full critical-path median<=7m targets, cold-cache/failure/flake limits and runner-minute accounting. Unmet target means CI-003 remains open with fallback; no favorable single-run or assumed speedup.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-045. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23335](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23335)
- [doc/model-remediation/RECONCILIATION.md:23733](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23733)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1041](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1041)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1273](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1273)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1041 | CI-003D | DEPENDENCY_PENDING | Timing, runner cost and adoption decision
doc/roadmap/ULTIMATE_ROADMAP.md:1273 | CI-003D | Evaluate repeatable timing/cost and make the independent adoption/closure decision. | At least five comparable samples per configuration, warm Fast median<=3m and Full critical-path median<=7m targets, cold-cache/failure/flake limits and runner-minute accounting. Unmet target means CI-003 remains open with fallback; no favorable single-run or assumed speedup.
doc/model-remediation/RECONCILIATION.md:23335 | CI-003D | Evaluate repeatable timing/cost and make the independent adoption/closure decision. | At least five comparable samples per configuration, warm Fast median<=3m and Full critical-path median<=7m targets, cold-cache/failure/flake limits and runner-minute accounting. Unmet target means CI-003 remains open with fallback; no favorable single-run or assumed speedup.
doc/model-remediation/RECONCILIATION.md:23733 | CI-003D | DEPENDENCY_PENDING | Timing, runner cost and adoption decision
~~~~~~~~

### 2026-10-20 — HAPI-046 existing-run timing and runner-cost reconciliation

Owner Next selects HAPI-046 after HAPI-045 acceptance. Exact main
`96bbd74508e06f96476616764e420c866313a6bb`, parent
`887b84c68479a0df01e0825b52f347ce798a7ed5`, tree
`aad4405f1e0a316935b1ac4b70b35d0abdbf5899`, passed
[Documentation465](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39448522073).
The preceding executable configuration passed Full784/Fast106/Docs464 as
reconciled under HAPI-045. This stage reads naturally completed runs only; it
does not dispatch unchanged benchmarks or modify the accepted CI policy.
Registered message remains `ci: automate fast feedback and preserve full verification gates`.

**Measurement definitions and provenance.** The tables below use GitHub run
attempt1 metadata and complete job lists retrieved for each exact run ID.
Workflow interval is run_started_at to updated_at after completed success; it
includes orchestration/queue gaps and is not a sum of test-case durations.
Slowest job is actual completed_at minus started_at. Runner seconds sum only
executed jobs; intentionally skipped oracle timestamps are excluded. Rounded
job-minute proxies sum ceil(each executed job seconds/60). Neither proxy is a
GitHub billing statement, monetary charge, multiplier-adjusted usage or CPU time.
All observed job labels are ubuntu-latest; no paid runner capacity is selected.

**Five comparable warm Fast observations.** All five use the identical Fast
workflow blob `1c7294dc97fb8c5ecf297dd36c15d2690ba2061f`, unchanged
335-class/1673-case Fast selection including exact case/source/support identities,
and the same restored Maven dependency cache key. Each job log confirms actual
cache restoration and zero test failures/errors/skips. Scheduling-only inventory
differences outside the Fast selection do not change these selected inputs.

| Fast run / exact source | Workflow interval, seconds | Executed job seconds |
|---|---:|---:|
| [102](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39430323649), 2cd16f45aa0fa324eb4f89107ff5d7f9f805b9a8 | 94 | 90 |
| [103](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39432208147), d15cfa28c598aedd8371a98c943b8919b3b7a52f | 109 | 103 |
| [104](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39433631521), 5a923ce0a91898d9ea17e0e97fa9f4f87d80faa1 | 100 | 96 |
| [105](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39440924056), 11b1ca458d623ed4fcca269b7a82f00b88c0b8f9 | 106 | 101 |
| [106](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39445933420), 887b84c68479a0df01e0825b52f347ce798a7ed5 | 114 | 109 |

Observed workflow median106s (1m46s), job median101s; workflow range94..114s.
The five-sample warm Fast <=180s criterion is met for these matched inputs.
Each is two rounded job-minute proxies. This does not establish a cold Fast
distribution, long-term flake rate or performance on other hosted runner images.

**Full observations, separated by actual configuration.**

| Full run / exact source | Configuration | Workflow seconds | Slowest executed job seconds | Summed runner seconds | Rounded job-minute proxy |
|---|---|---:|---:|---:|---:|
| [778](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39430323819), 2cd16f45aa0fa324eb4f89107ff5d7f9f805b9a8 | Three DB sets, development | 494 | 472 | 1509 | 29 |
| [779](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39432208352), d15cfa28c598aedd8371a98c943b8919b3b7a52f | Four DB sets, development | 395 | 373 | 1607 | 31 |
| [780](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39433631502), 5a923ce0a91898d9ea17e0e97fa9f4f87d80faa1 | Five DB sets, development, before cache-receipt instrumentation | 348 | 330 | 1821 | 36 |
| [781](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39435132363), 96af69e5c02ac246becbc608cb9443428a3f115a | Five DB sets plus independent serial audit, warm | 1294 | 1271 | 3128 | 58 |
| [782](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39440924194), 11b1ca458d623ed4fcca269b7a82f00b88c0b8f9 | Five DB sets, development, cache receipts | 377 | 352 | 1846 | 34 |
| [783](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39441957834), 11b1ca458d623ed4fcca269b7a82f00b88c0b8f9 | Five DB sets plus independent serial audit, cold dependency repository | 1304 | 1279 | 3305 | 60 |
| [784](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39445933061), 887b84c68479a0df01e0825b52f347ce798a7ed5 | Five DB sets, development, optional serial input defaults false | 369 | 348 | 1801 | 33 |

Full780/782/784 have the identical five-set inventory blob
`d44fbc666cf9f3ddf7e8c8bddcb26456231f760e`, inventory SHA256
`ee14f591e0f0ef84c89d6eda6e609fa7259748c506e03f0d56e4bde6d44b15b4`.
Their aggregate job outputs confirm430 executable classes/2606 cases, zero
failures/errors/skips, current/base API verification and actual PostgreSQL16
catalog/dictionary evidence, with source bundle SHA256
`be0f97cd7280aa97c0e84de040a632fbf494518e705fa10b358f793d136b9221`.
Full782/784 receipts explicitly prove successful cache restoration in all seven
Java lanes; Full780 predates that instrumentation and is not upgraded to an
instrumented cache observation by its success. Full784 also changes optional
audit-input handling. These source/policy distinctions prevent claiming five
strictly comparable samples from this table.

For the three five-set development workload observations only, the descriptive
workflow median is369s (6m09s), slowest-job median348s (5m48s), and runner-sum
median1821s (30m21s). All three workflow intervals are below420s, but n=3 is not
the required n>=5 and the instrumented/current-policy subsets are smaller.
The serial audits' approximately21-minute paths cannot be substituted as
extra development samples; their oracle deliberately performs additional work.
Nor may their partition jobs be turned into invented parallel-only workflow
completion times, because their real final gate waited for the serial oracle.

The one-run three/four/five-set progression shows shorter elapsed feedback
alongside higher summed runner time. It is not controlled causal evidence or
proof that five is the cost optimum. Retain the owner's explicit preference
for five sets and the already accepted complete selection/security/lifecycle/
API/catalog checks. No extra shards, weight refresh, relaxed deadlines, case
exclusions, source pin changes or retry policy are justified by these samples.

**Failure/flake and adoption disposition.** All listed runs completed success
on attempt1. This is a bounded observation, not an estimated population flake
rate or permission to exclude earlier failures. Full775/776 and Fast99 remain
failed historical runs of earlier feature/configuration states; they cannot be
pooled into the later identical-input cohort or erased as obsolete. HAPI-045's
accepted warm/cold independent equivalence and negative-validation fixtures
remain applicable to their exact scope. No new fault-injection campaign or
independent local replay of aggregate ZIPs was performed here; current Full
facts above are retrieved hosted aggregate output, not newly replayed artifacts.

HAPI-046 becomes PARTIAL / IN_PROGRESS: its prerequisite is satisfied and the
Fast timing and runner-cost analysis are now concrete. Full comparable-sample/
cold-cache/failure/flake qualification and independent final adoption remain
open. HAPI-436 remains PARTIAL / IN_PROGRESS. The owner-approved development
five-set/optional-serial policy continues; no optimized_adoption_eligible or
release_eligible flag is changed and no independent equivalence is claimed for
parallel-only runs. Keep the explicit serial diagnostic available under its
existing opt-in. HidraAPI is a development-only API; no release, tag, production
data or deployment is selected.

**Exact publication scope and validation.** MODIFY this owning phase record
and only HAPI-046's two status cells in ULTIMATE_ROADMAP.md and
ULTIMATE_PHASES.md; zero CREATE/DELETE. Prior evidence bytes and every other
task/status remain unchanged. Current environment is unavailable, so no local
Python fixture, canonical-estate, Java/Maven or database execution is claimed.
Available in-memory checks verify arithmetic, workflow/selection equality,
three-way status agreement, exact preservation on reversal, anchors and the
three-file scope. Hosted Docs supplies executable validation. Use expected-head
guard, verify all blobs/tree/parent/message, confirm exact-SHA Documentation CI
creation/startup and stop. No extra Full/Fast or benchmark dispatch occurs.

**Remaining evidence source:** collect future naturally authorized development
runs with the same complete workload, cache mode and execution policy, keeping
configuration cohorts separate. Do not rerun unchanged Full merely to fill the
quota and do not repeat the already accepted cold equivalence audit. Until
sufficient comparable evidence exists, report the measured subset above and
leave HAPI-046/436 open; an independent ready task requires its own owner Next.


### 2026-10-20 — HAPI-046 owner timing acceptance and development adoption

The owner explicitly instructed "Do 046 than 436", then "Timing accepted next".
This selects ordered HAPI-046 acceptance followed by HAPI-436's independent
parent reconciliation. The owner accepts the already measured timing/cost
evidence for this development API and removes the remaining sample-count/
repeatability hold as a condition of development adoption. This is an explicit
owner amendment of the original timing qualification gate, not a claim that
five comparable Full runs, a repeatable cold-performance distribution or a
long-term flake-rate bound were demonstrated. Original criteria and every dated
measurement above remain historical evidence. No unchanged benchmark campaign
is required to complete this accepted bounded scope.

**Fresh exact-source verification.** Main
`44c2f904d9e1abd4fb9d7158ff1f302ebab6f3be`, parent
`df10b03ece7cfc53344ee0e3f7e89fab33abfec5`, tree
`ad238c6474bac5afe54c4ce103b63be32b4ccb6e`, passed
[Full785](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39459787957)
and [Documentation467](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39457344569).
Full785 is a completed workflow_dispatch, attempt1, development/cached,
serial_oracle=false. All nine executed jobs succeeded; the optional serial
job was intentionally skipped. Gate118758744100 confirms430 executable classes/
2606 cases, zero failures/errors/skips, identical inventory SHA256
`ee14f591e0f0ef84c89d6eda6e609fa7259748c506e03f0d56e4bde6d44b15b4`,
successful cache restoration for all seven Java lanes and current/base API/
actual PostgreSQL16.15 catalog/dictionary evidence. Source bundle SHA256 remains
`be0f97cd7280aa97c0e84de040a632fbf494518e705fa10b358f793d136b9221`.
Hosted aggregate artifact12283290924 has reported ZIP SHA256
`ff2b70eafa634d0f3aac3d71900afa93f0da80157679ab9f82bc8aa669f6a9c2`.
These facts are retrieved gate output/artifact metadata; no new local archive
download or replay is claimed.

Full785 ran16:42:23Z..16:48:13Z:350s (5m50s) workflow interval, slowest job332s,
summed executed runner time1725s (28m45s),34 rounded job-minute proxies.
Skipped-job timestamps are excluded. These are measured runner-time proxies,
not monetary or billing amounts. Adding this observation to the earlier
three five-set development workload observations gives four intervals
348/377/369/350s and descriptive median359.5s (5m59.5s). Their instrumentation/
policy differences remain as recorded; four is not five, and the current
strictly comparable subsets remain smaller. The previously established warm
Fast five-sample median106s (1m46s) remains accepted. Cold Full783 and warm
Full781 independently establish correctness/cache scope, not additional
default-development elapsed samples or a measured cold latency distribution.

A complete GitHub comparison from Full784/Fast106 source887b84c to current44c2f90
contains three commits and five Markdown-only changed paths. Executable,
test, inventory, workflow/helper, API and schema behavior is unchanged.
Therefore Fast106's exact unchanged selection/source evidence remains applicable;
no nonexistent standalone Fast on44c2f90 is asserted. Full785 freshly exercises
the complete current parallel selection and actual catalog gates.

**Decision and scope.** Accept the measured latency/cost tradeoff for the owner's
development use: complete unit/architecture plus five isolated DB sets, API/base
compatibility, actual migrated dictionary validation and mandatory aggregate
provenance/negative checks. Keep serial verification an explicit optional audit
diagnostic; release verification still requires its independent oracle proof.
No numerical production SLO, calibrated industrial latency, population failure/
flake bound, cost optimum, paid-runner selection, production deployment or
release readiness is accepted. Future workload growth is measured honestly in
its own cohort; it does not invalidate this dated adoption or automatically
reopen HAPI-046 merely to fill the superseded development quota.

HAPI-046 is now VERIFIED/COMPLETED for this owner-amended development timing/
cost/adoption obligation. The original five-sample criterion is superseded only
as the remaining development acceptance hold by the owner's explicit timing
acceptance. Existing helper values optimized_adoption_eligible=false and
release_eligible=false for partition-only runs remain unchanged; governance
acceptance does not grant release eligibility or independent same-run equality.

This is the first of the owner's ordered two-commit governance sequence.
Exact message: `ci: automate fast feedback and preserve full verification gates`.
MODIFY only this owning phase record and HAPI-046's two status cells in
ULTIMATE_ROADMAP.md and ULTIMATE_PHASES.md; zero CREATE/DELETE. The next commit
independently reconciles HAPI-436, preserving one commit per task; advance main
once to the final verified governance commit and observe final exact-SHA Docs
startup, then stop. HAPI-436 is not marked complete in this first commit.
In-memory arithmetic, raw status/source comparison, three-way field agreement,
history/anchor and scope checks pass. The execution environment is unavailable;
no local Python, Java/Maven, database or full canonical validation is claimed.
Hosted Docs validates the final documentation-only sequence.

<a id="hapi-434"></a>
## HAPI-434 — parallelize isolated test JVM execution

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-CI-001, CI-001

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Run the unchanged full test suite in two reused isolated JVM forks; retain Surefire reports and count summaries for performance/coverage comparison; preserve all production/API/database/operations gates and verify exact-head CI.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/workflows/ci.yml`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/workflows/ci.yml`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## HPR-CI-001 Verified Optimization Result — 2026-10-09

Owner Next selects evidence reconciliation before resuming Phase 2.5. Main remains
3e9c19142a750b1aab3ca6fb3afdf5df5906b318, tree
664d21d3be2fc3b867032abc7170074ac61adf27. Mandatory AGENTS/roadmap/
reconciliation reads completed; local tree matches. Full CI #613/run 37961219860
and Documentation #147/run 37961220149 completed SUCCESS on this exact SHA.
All full-CI steps succeeded, including Java 21 clean verify with two isolated reused
forks, current OpenAPI equality, historical backward compatibility, migrated
dictionary comparison and existing P1/operations gates. HPR-CI-001 COMPLETED.

| Measured step | Baseline #612 | Optimized #613 | Observed reduction |
|---|---|---|---|
| Verification job elapsed | 726 seconds (12m06s) | 395 seconds (6m35s) | 331 seconds / 45.6 percent |
| Maven repository verification | 592 seconds (9m52s) | 281 seconds (4m41s) | 311 seconds / 52.5 percent |

Timings come from GitHub job/step timestamps: baseline job 113915537522 and
optimized job 113924240920. The unchanged test source set and retained original
workflow gates were checked at optimization publication. Optimized logs directly
report 324 Surefire XML files and 1,277 tests, failures 0, errors 0, skipped 0.
Test report upload succeeded; artifact ID 11631059984, size 1,631,791 bytes,
digest sha256:8337e0167dd7f1e911e0d8f5027746d59b4de2a88d7c6a56ceae4cfefdb1b2cc,
name hidra-test-reports-3e9c19142a750b1aab3ca6fb3afdf5df5906b318, retained
until 2026-10-23T16:49:24Z. Artifact metadata verified through GitHub.
The binary archive was not independently downloaded/parsed; counts are the executed
workflow log summary. Baseline #612 did not publish this report, so an independent
per-class baseline-versus-optimized XML comparison is not claimed.

Runtime OpenAPI equality verified 244 paths, 263 operations and 231 schemas.
Dictionary log evidence reports 139 migrations, 470 JPA mappings, 482 relations,
reviewed_dictionary_checked true, unresolved_owners [] and source_sha equal to
3e9c19142a750b1aab3ca6fb3afdf5df5906b318. Source bundle remains
86f6ba5923e63c0cd601f7722ca0282e486d59fc92bb120616710fb3cd1799d4.
No per-class manifest test count is inferred from the aggregate alone.

This single before/after comparison supports observed improvement, not a fixed SLO
or causal timing guarantee across variable runner/cache conditions. No failures or
skips are reported in the optimized run; no further fork increase or workflow
change is selected. Continue retaining diagnostics for future regressions.

Supporting exact message: `docs(ci): record verified production CI optimization`.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
No source/test/workflow/POM/schema/API/dependency change or full-runtime rerun is
needed solely for this evidence record. Full #613 remains pinned to the optimized
executable commit, not this documentation-only record. Validate canonical docs,
offline OpenAPI, retained P1 evidence, whitespace and exact two-file scope.
Publish with expected-parent lease, verify parent/tree/files, observe Documentation
Validation startup and STOP. Next recommended owner selection is HPR-P25-002B
physical-input preflight / `docs(twin): register HPR-P25-002B execution preflight`.
P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, 57 HMR completions, 123 subjects,
formal v0.6.0 and current 0.7.0-SNAPSHOT remain preserved.

Actual supporting verification checks PASSED: canonical documentation (95 documents,
5,063 links, 24 modules, 13 P2 rows), offline OpenAPI (244 paths, 263 operations,
231 schemas), P1 evidence and git diff --check. Exact two-document scope and
retained phase/HMR identities verified. Supporting Documentation CI follows publication.


~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

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

- `3e9c19142a750b1aab3ca6fb3afdf5df5906b318` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:7533](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L7533)
- [doc/model-remediation/RECONCILIATION.md:7577](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L7577)
- [doc/model-remediation/RECONCILIATION.md:13754](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13754)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6007](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6007)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:980](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L980)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:9317](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L9317)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15494](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15494)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:980 | HPR-CI-001 | COMPLETED — optimized full CI #613 and Documentation #147 PASSED at 3e9c19142a750b1aab3ca6fb3afdf5df5906b318; job 726s to 395s, Maven 592s to 281s; log summary 1,277 tests / zero failures, errors or skips | CI / Verification | Infra | Run the unchanged full test suite in two reused isolated JVM forks; retain Surefire reports and count summaries for performance/coverage comparison; preserve all production/API/database/operations gates and verify exact-head CI. | perf(ci): parallelize isolated test JVM execution | owner instruction; full #612 green
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15494 | HPR-CI-001 | COMPLETED within recorded scope | COMPLETED_PRESERVED | CI / Verification; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6007 | HPR-CI-001 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13754 | HPR-CI-001 | COMPLETED within recorded scope | COMPLETED_PRESERVED | CI / Verification; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-435"></a>
## HAPI-435 — HPR-CI-002 historical bounded obligation

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-CI-002, CI-002

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

HPR-CI-002 historical bounded obligation

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Make security and exact-source verification fail closed while preserving meaningful CI evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

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

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/roadmap/ULTIMATE_ROADMAP.md:6008](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6008)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:6008 | HPR-CI-002 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
~~~~~~~~

<a id="hapi-436"></a>
## HAPI-436 — Integrated closure: automate fast feedback and preserve full verification gates

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-CI-003, CI-003

Priority: P0

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-046

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Independently reconcile and accept the complete child-deliverable matrix for HPR-CI-003; child tasks own implementation. automate fast feedback and preserve full verification gates

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/ci-test-inventory.json`
- `.github/scripts/test_verify_ci_test_inventory.py`
- `.github/scripts/test_verify_full_ci_evidence.py`
- `.github/scripts/verify_ci_test_inventory.py`
- `.github/scripts/verify_full_ci_evidence.py`
- `.github/workflows/ci-fast-feedback.yml`
- `.github/workflows/ci.yml`
- `.github/workflows/docs.yml`
- `.github/workflows/release.yml`
- `doc/database/FLYWAY_POLICY.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/operations/CI_CD_RELEASE_GUIDE.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/testing/TEST_STRATEGY.md`
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/persistence/OrganizationEmployeeContactBackfillMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/persistence/OrganizationMultilingualCompatibilityRetirementMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/persistence/OrganizationMultilingualMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/persistence/OrganizationMultilingualTableRetirementMigrationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Complete CI-003 representative/negative/performance qualification and explicit adoption decision; preserve mandatory fallback, security generations and IT/OT segregation. The exact bounded remaining delivery is: Independently reconcile and accept the complete child-deliverable matrix for HPR-CI-003; child tasks own implementation. automate fast feedback and preserve full verification gates.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Make security and exact-source verification fail closed while preserving meaningful CI evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/test/java/dz/sh/hidra/modules/organization/infrastructure/persistence/OrganizationEmployeeContactBackfillMigrationTest.java`, `src/test/java/dz/sh/hidra/modules/organization/infrastructure/persistence/OrganizationMultilingualCompatibilityRetirementMigrationTest.java`, `src/test/java/dz/sh/hidra/modules/organization/infrastructure/persistence/OrganizationMultilingualMigrationTest.java`, `src/test/java/dz/sh/hidra/modules/organization/infrastructure/persistence/OrganizationMultilingualTableRetirementMigrationTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / DELETE:** none preauthorized.

**MODIFY — only if accepted evidence demonstrates a correction is necessary:**

- `.github/workflows/ci.yml` — preserve complete mandatory selections, services and lifecycle; no oracle removal until explicit adoption.

- `.github/scripts/verify_full_ci_evidence.py` — preserve same-run class/case/API/catalog/source/attempt equality and negative rejection.

- `.github/scripts/test_verify_full_ci_evidence.py` — actual new independent negative cases when necessary.

- `doc/roadmap/ULTIMATE_TASKS.md` — retain measured evidence or no-code-needed decision.

**PRESERVE / REUSE:** `.github/ci-test-inventory.json`, `.github/workflows/ci-fast-feedback.yml`, unrestricted oracle and retained four historical upgrade tests.

**DISCOVERY_REQUIRED:** exact remaining representative/cold-warm/timing evidence package and any change allowlist. Never rerun unchanged Full solely to repeat known latency.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-CI-003 — four remaining qualification packages

Existing automatic Fast, partitioned Full, unrestricted oracle, aggregate and release
proof machinery are implemented. The five older same-SHA successes and recent Full697
do not automatically prove the outstanding representative-change/cold-cache/performance
campaign. Keep current shadow-fallback-required and optimized_adoption_eligible=false
until the complete registered transition criteria are met.

| Planning stage | Remaining delivery | Evidence / exit criterion |
|---|---|---|
| CI-003A | Refresh current job/step/raw-class timings, runner CPU/RAM/forks, cache and scheduling context. | Identify the actual critical path on comparable successful runs, account for source/test growth; distinguish suite sums, job wall time, queue and workflow elapsed. Preserve old measurements as dated evidence. |
| CI-003B | Apply only a justified bounded scheduling/resource/evidence correction if measurements require one; otherwise record no code needed. | Existing exact CI scope, unchanged required class/case identities, isolated containers and complete clean-verify/API/dictionary/release behavior. No skipped tests, unsafe target/report/DB caches or speculative extra shards. |
| CI-003C | Complete representative code/security, migration and API revision equivalence, cold/warm and negative acceptance proof. | Same-run exact source/lifecycle/report/API/catalog equality; omitted/failed/skipped/overlapping/stale/cancelled evidence denies acceptance. Use naturally authorized representative revisions, not artificial application changes for benchmarks. |
| CI-003D | Evaluate repeatable timing/cost and make the independent adoption/closure decision. | At least five comparable samples per configuration, warm Fast median<=3m and Full critical-path median<=7m targets, cold-cache/failure/flake limits and runner-minute accounting. Unmet target means CI-003 remains open with fallback; no favorable single-run or assumed speedup. |

Collect existing completed evidence first. Any new benchmark/failure workflow exercise
requires its separately selected authorized CI action and normal startup-and-STOP rule;
this plan does not dispatch a campaign, wait for runs or bypass owner feedback.
Failure injections use isolated approved validation fixtures, never production tampering.
An optimized-only transition requires its exact same-ID amendment and release-gate proof;
full fallback remains available. CI-003 can proceed independently when separately selected
and does not block G or existing supported operational maintenance.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Preserve the original bounded objective and verify its claimed output against current artifacts. Make security and exact-source verification fail closed while preserving meaningful CI evidence.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-046. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `ec274eb7ecaa97c666b93c2c69fc11f344301293` — exact registered-message commit; surviving paths matched to current tree.
- `ca0d2aed0077857277883deef79fee83adc4fc61` — exact registered-message commit; surviving paths matched to current tree.
- `3aa9a359800e9070969e28ee1a42f550fbe8871f` — exact registered-message commit; surviving paths matched to current tree.
- `b17fcf28d0b04a2ab2e176ccf32b350a57e7ed25` — exact registered-message commit; surviving paths matched to current tree.
- `b6a180c23a6a523c25009792f5c63f719d6d4ca4` — exact registered-message commit; surviving paths matched to current tree.
- `3f5d7f333e034685bf439e1c733140d471fbfa29` — exact registered-message commit; surviving paths matched to current tree.
- `3c6361215c3bb72d33993d052f02637e3baebce8` — exact registered-message commit; surviving paths matched to current tree.
- `5129a22c1df2a8ceba2782e1f0e440ca2a7d4b42` — exact registered-message commit; surviving paths matched to current tree.
- `e1695a0848e005f3fac272dd80d2a80f03f5ce2c` — exact registered-message commit; surviving paths matched to current tree.
- `117d0d673a8ab8dcb069e95422ef52886a29f787` — exact registered-message commit; surviving paths matched to current tree.
- `deda8c27f9997e4ece5fa7185098f8123d197c81` — exact registered-message commit; surviving paths matched to current tree.
- `84425a0423c149ed691261b7b6cce5213ae439e5` — exact registered-message commit; surviving paths matched to current tree.
- `003a98d3fbf20b59e76a4cc80de78b70552310fc` — exact registered-message commit; surviving paths matched to current tree.
- `dab5e2522993358f875c5f89d2deebc8fbc889ec` — exact registered-message commit; surviving paths matched to current tree.
- `d4a3d99d0840a8791eb79e5b25f86bd377470b54` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23322](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23322)
- [doc/operations/CI_CD_RELEASE_GUIDE.md:107](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/operations/CI_CD_RELEASE_GUIDE.md#L107)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1260](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1260)
- [doc/roadmap/ULTIMATE_ROADMAP.md:4680](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L4680)
- [doc/roadmap/ULTIMATE_ROADMAP.md:4722](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L4722)
- [doc/roadmap/ULTIMATE_ROADMAP.md:4770](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L4770)
- [doc/roadmap/ULTIMATE_ROADMAP.md:4958](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L4958)
- [doc/testing/TEST_STRATEGY.md:114](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/TEST_STRATEGY.md#L114)
- [doc/testing/TEST_STRATEGY.md:188](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/TEST_STRATEGY.md#L188)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

### 2026-10-20 — HAPI-436 independent integrated development CI closure

The owner selected "Do 046 than 436" and then explicitly accepted timing.
This is the second ordered governance task, after HAPI-046 acceptance commit
`9d57ce16caac60dad48cf4e1199c26ac0b88ea54` (parent44c2f904d9e1abd4fb9d7158ff1f302ebab6f3be).
The first task records the owner-amended timing criterion and its statistical
limits; this parent does not duplicate or invent missing measurements.

**Independent child-deliverable reconciliation.**

| Child | Final bounded disposition | Evidence accepted for this parent |
|---|---|---|
| HAPI-043 / CI-003A | VERIFIED/COMPLETED | Retained measured-analysis/no-code-needed outcome, supplemented by exact run/job timing, queue-versus-workflow-versus-runner definitions in046. Historical source applicability stays dated. |
| HAPI-044 / CI-003B | VERIFIED/COMPLETED | Retained bounded scheduling obligation; the later045 implementation/acceptance records establish complete disjoint five-set execution and isolated clean-verify lifecycles, without deleted cases, broadened exclusions or unsafe report/DB caches. |
| HAPI-045 / CI-003C | VERIFIED/COMPLETED | Source-linked representative code/security, additive migration and API matrix:101 exact cases/31 added methods; Full777/781/783 independent partition/oracle equality, actual warm/cold dependency-repository proof, Full784 optional-mode acceptance and required negative fixture gates. |
| HAPI-046 / CI-003D | VERIFIED/COMPLETED under explicit owner timing acceptance | Five comparable warm Fast runs median106s; dated development Full timing/cost analysis and newly verified Full785 at350s. Owner accepts the measured tradeoff and removes the remaining five-Full-sample/repeatability development hold. No statistically complete Full/cold/flake distribution is asserted. |

The original child scopes remain separate. HAPI-043/044 acceptance is not
reinterpreted as evidence that they independently implemented all later045
machinery. Current adoption follows their complete combined delivery plus
the explicit owner amendments, not a cumulative green-run shortcut.

**Current source and integrated proof.** Full785/run39459787957 attempt1
succeeded on44c2f904d9e1abd4fb9d7158ff1f302ebab6f3be; its final aggregate
job118758744100 checks source/run/attempt/inventory, lane evidence, all430
executable class/2606 case identities, successful Java21 clean-verify lifecycle
and mandatory job completion, current/base OpenAPI compatibility and actual
migrated PostgreSQL16.15 catalog/dictionary evidence. Counts are zero failed,
errored or skipped required tests. The intentionally absent optional serial
job is not a skipped test. All seven active Java execution lanes prove actual
warm Maven dependency restoration.

The source comparison from accepted Full784/Fast106 source887b84c to44c2f90
contains only three governance commits/five Markdown paths; no workflow/helper,
inventory, runtime/test, API or migration source changed. Full785 therefore
freshly exercises the accepted current five-set implementation. Its hosted
aggregate artifact12283290924 and reported ZIP SHA256
`ff2b70eafa634d0f3aac3d71900afa93f0da80157679ab9f82bc8aa669f6a9c2`
are recorded under046. No separate local ZIP replay is claimed in this parent
review. HAPI-045's earlier independently checked raw artifacts remain its
representative/warm/cold provenance rather than being replaced by job success.

**Accepted integrated policy.** Development verification uses the complete
reviewed unit/architecture selection and five isolated database sets, plus
static, API/catalog and final aggregate gates. Fast remains an exact reviewed
feedback subset and cannot substitute for Full acceptance. Tests use migrated
disposable databases and actual dictionary equality; source-only or greenfield
success does not waive the retained historical upgrade cases. No test is
removed or deemed obsolete by this closure. Exact case/source provenance,
negative receipt validation, missing/failed/skipped/overlapping/stale/cancelled/
wrong-attempt rejection, supported API compatibility and security owner
boundaries remain mandatory.

The unrestricted serial oracle remains available only via the already approved
explicit audit opt-in. Parallel-only receipts continue to report
same_run_equivalence=false and release_eligible=false. Strict release
recomputation still requires independent oracle evidence and actual success.
The helper currently hardcodes optimized_adoption_eligible=false; that legacy
machine field is preserved as a conservative evidence/release restriction.
Owner acceptance of development scheduling does not require flipping it or
claiming independent equivalence for a partition-only run. This parent closure
selects no release-mode, release workflow, tag, deployment or production API.

**Decision.** HAPI-436 is VERIFIED/COMPLETED for integrated development Fast/
Full automation and its owner-approved five-set/optional-serial policy.
All four child obligations are reconciled, with the046 timing amendment
explicit rather than hidden. There is no remaining child implementation
selected by this parent. Future defects still require repair; future workload
growth retains source-linked coverage and honest timing. This is not an
industrial availability, latency, cost-optimum, long-term flake-rate or SITE
certification. Existing completed HPS-024 tasks retain their independent
historical VERIFIED/IMPLEMENTED/NOT_VERIFIABLE dispositions; this decision
does not relabel the entire phase as independently VERIFIED.

**Exact scope and validation.** Registered message remains
`ci: automate fast feedback and preserve full verification gates`.
MODIFY this phase record, only HAPI-436's two status fields in
ULTIMATE_ROADMAP.md/ULTIMATE_PHASES.md and the corresponding046/436 navigation
paragraph in ULTIMATE_TASKS.md. Zero CREATE/DELETE. Every earlier task record,
other status, ID/objective/priority/dependency/order and all executable/schema/
API/dictionary/workflow/helper/inventory/version blobs are preserved.
Available in-memory checks validate the child/header/registry/pointer agreement,
exact restoration of original payload on reversal, all anchors and exact
per-commit paths. The execution environment is unavailable, so no local Python,
Java/Maven/PostgreSQL or full canonical-estate run is claimed. Final exact-SHA
Documentation CI supplies executable validation of this documentation-only
sequence. Verify both ordered commits' parent/tree/message/raw blobs and
per-task scope, advance main once using the original expected-head guard,
confirm final Docs startup and stop.

HAPI-590 remains NOT_IMPLEMENTED/BLOCKED pending the previously documented
publication-readiness choice. "Timing accepted" resolves046, not that separate
business-policy decision. No publication option or downstream implementation
is silently selected by closing CI governance.


<a id="hapi-437"></a>
## HAPI-437 — secure operational workbench data exposure

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P0-001

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Re-inspect the exact-head Workbench path, replace automatic metamodel exposure with a fail-closed approved-resource policy, replace unrestricted reflective response fields with a fail-closed approved-field/API-safe projection policy, and explicitly prevent exposure of credential/secret persistence including LocalCredentialJpaEntity.passwordHash.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/security/WORKBENCH_DATA_EXPOSURE.md`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LocalCredentialJpaEntity.java`
- `src/main/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchExposurePolicy.java`
- `src/main/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LocalCredentialJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/security/WORKBENCH_DATA_EXPOSURE.md`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LocalCredentialJpaEntity.java`
- `src/main/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchExposurePolicy.java`
- `src/main/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchService.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## HPR-P0-001 Write Discipline

Before code changes:

1. recover exact current Workbench controller/service/response behavior;
2. recover current Identity credential persistence fields;
3. recover current route authorization behavior;
4. identify actual Workbench resource requirements from current repository evidence;
5. choose the narrowest fail-closed implementation that preserves required behavior.

Do not add unrelated features.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

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

- `0e1a16902189929d095c015269607eaf5e706503` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13755](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13755)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6009](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6009)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:172](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L172)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15495](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15495)
- [doc/security/WORKBENCH_DATA_EXPOSURE.md:48](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/security/WORKBENCH_DATA_EXPOSURE.md#L48)
- [doc/security/WORKBENCH_DATA_EXPOSURE.md:86](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/security/WORKBENCH_DATA_EXPOSURE.md#L86)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:172 | HPR-P0-001 | COMPLETED — fail-closed opt-in resource/field policy implemented in the task commit containing this status transition; default exposure is empty; credential/secret resources and password/secret fields are prohibited | Platform / Workbench / Identity | Code | Re-inspect the exact-head Workbench path, replace automatic metamodel exposure with a fail-closed approved-resource policy, replace unrestricted reflective response fields with a fail-closed approved-field/API-safe projection policy, and explicitly prevent exposure of credential/secret persistence including `LocalCredentialJpaEntity.passwordHash`. | fix(platform): secure operational workbench data exposure | Bootstrap
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15495 | HPR-P0-001 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Platform / Workbench / Identity; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6009 | HPR-P0-001 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13755 | HPR-P0-001 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Platform / Workbench / Identity; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-438"></a>
## HAPI-438 — guard workbench sensitive data exposure

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P0-002

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-437

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add focused regression tests covering list, detail, filter/search and generic attribute maps; assert that credential resources and passwordHash cannot be returned.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/test/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchExposurePolicyTest.java`
- `src/test/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/test/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchExposurePolicyTest.java`
- `src/test/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchExposurePolicyTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchServiceTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-437. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `42e000fa789c3ccfc0f7644e6dcf954fa428a339` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13756](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13756)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6010](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6010)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:173](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L173)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15496](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15496)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:173 | HPR-P0-002 | COMPLETED — focused policy/service regressions added for default-deny discovery, explicit fields, identifier requirement, unknown fields, credential/secret rejection, list/detail/search attribute exposure, hidden filter/sort rejection, and `passwordHash` prohibition | Platform / Workbench / Identity | Code | Add focused regression tests covering list, detail, filter/search and generic attribute maps; assert that credential resources and `passwordHash` cannot be returned. | test(platform): guard workbench sensitive data exposure | HPR-P0-001
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15496 | HPR-P0-002 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Platform / Workbench / Identity; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6010 | HPR-P0-002 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13756 | HPR-P0-002 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Platform / Workbench / Identity; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-439"></a>
## HAPI-439 — guard generic persistence exposure

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P0-003

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-437

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Extend architecture/security guardrails so a future generic platform reader cannot silently introduce unrestricted module-JPA exposure outside an explicitly reviewed boundary.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — exact-head oracle suite 16 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-437. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `fe82015b029d170a9d7239a83d5f71d20e46416a` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13757](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13757)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6011](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6011)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:174](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L174)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15497](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15497)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:174 | HPR-P0-003 | COMPLETED — `ArchitectureGuardrailTest` now restricts platform JPA access to the reviewed Workbench reader, forbids platform dependencies on module persistence packages, and requires that reader to retain the fail-closed exposure-policy dependency | Architecture Testing | Code | Extend architecture/security guardrails so a future generic platform reader cannot silently introduce unrestricted module-JPA exposure outside an explicitly reviewed boundary. | test(architecture): guard generic persistence exposure | HPR-P0-001
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15497 | HPR-P0-003 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Architecture Testing; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6011 | HPR-P0-003 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13757 | HPR-P0-003 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Architecture Testing; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-440"></a>
## HAPI-440 — declare openapi security requirements

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P0-004

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-437

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add machine-readable OpenAPI security scheme and applicable security requirements for secured endpoints without weakening runtime security.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraOpenApiSecurityConfiguration.java`
- `src/test/java/dz/sh/hidra/platform/configuration/HidraOpenApiSecurityConfigurationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraOpenApiSecurityConfiguration.java`
- `src/test/java/dz/sh/hidra/platform/configuration/HidraOpenApiSecurityConfigurationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/platform/configuration/HidraOpenApiSecurityConfigurationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-437. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `28dfeccb5c0193107b51e80e9fc2812ee3b356dd` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13758](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13758)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6012](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6012)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:175](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L175)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15498](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15498)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:175 | HPR-P0-004 | COMPLETED — generated OpenAPI now declares separate Hidra-issued JWT and external-OIDC bearer schemes; ordinary protected operations use Hidra bearer, OIDC completion uses external OIDC bearer, and verified public endpoints are explicitly unauthenticated | API / Security | Code | Add machine-readable OpenAPI security scheme and applicable security requirements for secured endpoints without weakening runtime security. | fix(api): declare openapi security requirements | HPR-P0-001
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15498 | HPR-P0-004 | COMPLETED within recorded scope | COMPLETED_PRESERVED | API / Security; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6012 | HPR-P0-004 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13758 | HPR-P0-004 | COMPLETED within recorded scope | COMPLETED_PRESERVED | API / Security; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-441"></a>
## HAPI-441 — clarify audit actor provenance

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P0-005

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-437

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Revalidate caller-supplied actor-header handling; ensure it is not represented as authenticated audit identity unless a verified binding exists; correct code/tests/documentation only where evidence requires it.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/platform/security/CurrentActorResolver.java`
- `src/main/java/dz/sh/hidra/platform/security/CurrentSecurityContext.java`
- `src/main/java/dz/sh/hidra/platform/web/HidraRequestContextFilter.java`
- `src/main/java/dz/sh/hidra/platform/web/PlatformHeaders.java`
- `src/test/java/dz/sh/hidra/platform/web/HidraRequestContextFilterTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/platform/security/CurrentActorResolver.java`
- `src/main/java/dz/sh/hidra/platform/security/CurrentSecurityContext.java`
- `src/main/java/dz/sh/hidra/platform/web/HidraRequestContextFilter.java`
- `src/main/java/dz/sh/hidra/platform/web/PlatformHeaders.java`
- `src/test/java/dz/sh/hidra/platform/web/HidraRequestContextFilterTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/platform/web/HidraRequestContextFilterTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-437. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `359ae6d77bb9bb8f499941634760f4c375abb9ac` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13759](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13759)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6013](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6013)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:176](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L176)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15499](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15499)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:176 | HPR-P0-005 | COMPLETED — `X-Actor-Id` is no longer a supported platform header; the early request-context filter no longer reads caller actor identity or populates actor MDC/logging context; authenticated audit/JPA attribution remains sourced from `CurrentSecurityContext`/`CurrentActorResolver`; spoofing regression added | Platform / Logging | Code | Revalidate caller-supplied actor-header handling; ensure it is not represented as authenticated audit identity unless a verified binding exists; correct code/tests/documentation only where evidence requires it. | fix(platform): clarify audit actor provenance | HPR-P0-001
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15499 | HPR-P0-005 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Platform / Logging; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6013 | HPR-P0-005 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13759 | HPR-P0-005 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Platform / Logging; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-442"></a>
## HAPI-442 — establish canonical security baseline

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P0-006

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-437

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Complete canonical security documents: SECURITY_ARCHITECTURE.md, TRUST_BOUNDARIES.md, THREAT_MODEL.md, SECRETS_AND_CERTIFICATES.md, and INCIDENT_RESPONSE.md, using only verified current controls and explicit TARGET/TBD markers.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/security/INCIDENT_RESPONSE.md`
- `doc/security/SECRETS_AND_CERTIFICATES.md`
- `doc/security/SECURITY_ARCHITECTURE.md`
- `doc/security/THREAT_MODEL.md`
- `doc/security/TRUST_BOUNDARIES.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/security/INCIDENT_RESPONSE.md`
- `doc/security/SECRETS_AND_CERTIFICATES.md`
- `doc/security/SECURITY_ARCHITECTURE.md`
- `doc/security/THREAT_MODEL.md`
- `doc/security/TRUST_BOUNDARIES.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: HAPI-437. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `ad32650f71900a60c0ed7ee3123191ad26c688ed` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13760](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13760)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6014](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6014)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:177](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L177)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15500](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15500)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:177 | HPR-P0-006 | COMPLETED — canonical security architecture, trust-boundary, threat-model, secrets/certificates, and incident-response documents created from exact-head repository evidence; TLS/certificate lifecycle, secret rotation, SIEM/SOC, security contacts, severity model, HA/DR and RTO/RPO remain explicitly NOT ESTABLISHED/TBD | Security | Doc | Complete canonical security documents: `SECURITY_ARCHITECTURE.md`, `TRUST_BOUNDARIES.md`, `THREAT_MODEL.md`, `SECRETS_AND_CERTIFICATES.md`, and `INCIDENT_RESPONSE.md`, using only verified current controls and explicit TARGET/TBD markers. | docs(security): establish canonical security baseline | HPR-P0-001..005
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15500 | HPR-P0-006 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Security; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6014 | HPR-P0-006 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13760 | HPR-P0-006 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Security; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-443"></a>
## HAPI-443 — close P0 security remediation

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P0-007

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-437

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Run full Maven verification, architecture/security tests, database/Flyway startup verification and deterministic OpenAPI generation; record exact-head evidence and close P0 only if all required checks pass.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: HAPI-437. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `dcf69e1a4a4b788cd125ba6289384642efc4caa0` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13761](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13761)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6015](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6015)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:178](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L178)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15501](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15501)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:178 | HPR-P0-007 | COMPLETED — P0 CLOSED. CI run #520 on executable verification anchor `359ae6d77bb9bb8f499941634760f4c375abb9ac` passed Java 21 `./mvnw -B -q clean verify`, architecture/security tests, PostgreSQL/Testcontainers + Flyway validation/migration, application startup/health, deterministic `/v3/api-docs` generation, and artifact upload. The only intervening commit `ad32650f71900a60c0ed7ee3123191ad26c688ed` changed six Markdown files, so the executable tree remained identical; the closure commit changes only this roadmap. | Repository | Code/Doc | Run full Maven verification, architecture/security tests, database/Flyway startup verification and deterministic OpenAPI generation; record exact-head evidence and close P0 only if all required checks pass. | docs(roadmap): close P0 security remediation | HPR-P0-001..006
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15501 | HPR-P0-007 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Repository; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6015 | HPR-P0-007 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13761 | HPR-P0-007 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Repository; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-444"></a>
## HAPI-444 — verify workbench http exposure boundary

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P0-008

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-437

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Close audit Checks 6–7 with HTTP-level Workbench regression tests using the real controller/serialization path. Cover resource discovery, list, detail, and search; directly request prohibited credential resources and assert denial before persistence access; assert serialized responses contain neither the passwordHash property nor a distinctive password-hash marker value. Preserve the existing fail-closed resource/field policy.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/test/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchHttpExposureTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/test/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchHttpExposureTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchHttpExposureTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-437. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `142dcd71be3ff52f714145bd418f0db9255e9c15` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13762](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13762)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6016](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6016)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:179](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L179)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15502](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15502)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:179 | HPR-P0-008 | COMPLETED — `HidraOperationalWorkbenchHttpExposureTest` now exercises real MockMvc controller/serialization for discovery, list, detail, and search; direct `identity/local-credentials` list/detail/search requests return HTTP 400 before credential persistence access; serialized responses exclude `passwordHash` and a distinctive hash marker; hidden search/filter fields cannot reach JPA criteria | Platform / Workbench / API | Code/Test | Close audit Checks 6–7 with HTTP-level Workbench regression tests using the real controller/serialization path. Cover resource discovery, list, detail, and search; directly request prohibited credential resources and assert denial before persistence access; assert serialized responses contain neither the `passwordHash` property nor a distinctive password-hash marker value. Preserve the existing fail-closed resource/field policy. | test(platform): verify workbench http exposure boundary | HPR-P0-001..003
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15502 | HPR-P0-008 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Platform / Workbench / API; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6016 | HPR-P0-008 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13762 | HPR-P0-008 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Platform / Workbench / API; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-445"></a>
## HAPI-445 — require exact-head openapi evidence

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P0-009

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-444

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Close audit Check 9 as an evidence gap, not a code reimplementation. Adjust CI triggering so canonical doc/ security/roadmap changes do not silently bypass verification, then obtain a successful CI run on the exact task SHA that executes ./mvnw -B -q clean verify, starts the application, fetches deterministic /v3/api-docs, retains the artifact, and verifies both hidraBearerJwt and externalOidcBearerJwt plus representative operation security references in the generated artifact.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/workflows/ci.yml`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/workflows/ci.yml`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

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

Canonical prerequisites: HAPI-444. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `5ae7905fd51db6efb6f688b025e0dd29a041e200` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13763](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13763)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6017](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6017)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:180](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L180)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15503](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15503)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:180 | HPR-P0-009 | COMPLETED — exact task SHA `5ae7905fd51db6efb6f688b025e0dd29a041e200` passed CI run #522; repository verification and deterministic OpenAPI publication succeeded; retained artifact `hidra-api-openapi-5ae7905fd51db6efb6f688b025e0dd29a041e200` (artifact id `11348855890`) embeds the exact SHA and verifies both bearer schemes plus representative protected/OIDC/public operation security requirements; canonical `doc/**` Markdown now triggers CI while legacy `docs/**` remains ignored | CI / OpenAPI | Infra/Test | Close audit Check 9 as an evidence gap, not a code reimplementation. Adjust CI triggering so canonical `doc/` security/roadmap changes do not silently bypass verification, then obtain a successful CI run on the exact task SHA that executes `./mvnw -B -q clean verify`, starts the application, fetches deterministic `/v3/api-docs`, retains the artifact, and verifies both `hidraBearerJwt` and `externalOidcBearerJwt` plus representative operation security references in the generated artifact. | ci(security): require exact-head openapi evidence | HPR-P0-008
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15503 | HPR-P0-009 | COMPLETED within recorded scope | COMPLETED_PRESERVED | CI / OpenAPI; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6017 | HPR-P0-009 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13763 | HPR-P0-009 | COMPLETED within recorded scope | COMPLETED_PRESERVED | CI / OpenAPI; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-446"></a>
## HAPI-446 — map application trust transitions

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P0-010

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-444

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Close audit Check 11 by extending SECURITY_ARCHITECTURE.md and TRUST_BOUNDARIES.md with repository-verified API → controller → application/use-case → module transitions, permitted cross-module application-contract transitions, the reviewed Workbench persistence exception, and the existing telemetry source/point registration/operator API boundary. Keep OT network architecture, deployment perimeter, and unverified infrastructure explicitly NOT ESTABLISHED.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/security/SECURITY_ARCHITECTURE.md`
- `doc/security/TRUST_BOUNDARIES.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/security/SECURITY_ARCHITECTURE.md`
- `doc/security/TRUST_BOUNDARIES.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: HAPI-444. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `205a9a3010705bffd0a45c464a4e69f9b2a6b3a9` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13764](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13764)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6018](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6018)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:181](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L181)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15504](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15504)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:181 | HPR-P0-010 | COMPLETED — security architecture/trust-boundary docs now map normal API→controller→application→domain/outbound-port flow, explicitly exported cross-module application contracts, the reviewed Workbench→JPA exception, telemetry source/point registration flows, and telemetry query flow; private cross-module internals and OT/deployment/network assumptions remain prohibited or NOT ESTABLISHED | Security Architecture | Doc | Close audit Check 11 by extending `SECURITY_ARCHITECTURE.md` and `TRUST_BOUNDARIES.md` with repository-verified API → controller → application/use-case → module transitions, permitted cross-module application-contract transitions, the reviewed Workbench persistence exception, and the existing telemetry source/point registration/operator API boundary. Keep OT network architecture, deployment perimeter, and unverified infrastructure explicitly NOT ESTABLISHED. | docs(security): map application trust transitions | HPR-P0-008
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15504 | HPR-P0-010 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Security Architecture; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6018 | HPR-P0-010 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13764 | HPR-P0-010 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Security Architecture; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-447"></a>
## HAPI-447 — extend operator threat model

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P0-011

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-446

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Close audit Check 12 by extending THREAT_MODEL.md with existing telemetry source/point registration and operator-action abuse paths. For each threat record asset, actor/source, attack path, trust boundary, impact, current control, and residual risk. Do not invent SCADA transport, telemetry reading-ingestion endpoints, or an unverified vulnerability.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/security/THREAT_MODEL.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/security/THREAT_MODEL.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: HAPI-446. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `64dacaf85720df75d4b4079ccb8630215f6b3410` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13765](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13765)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6019](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6019)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:182](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L182)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15505](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15505)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:182 | HPR-P0-011 | COMPLETED — threat model now includes explicit telemetry source registration, telemetry point registration, telemetry query, alarm acknowledgement/closure, alarm shelving/unshelving, and alarm-raise abuse scenarios with asset, actor/source, attack path, boundary, impact, verified controls, and residual risk; no SCADA/PLC ingestion transport or unverified vulnerability is invented | Security / Telemetry | Doc | Close audit Check 12 by extending `THREAT_MODEL.md` with existing telemetry source/point registration and operator-action abuse paths. For each threat record asset, actor/source, attack path, trust boundary, impact, current control, and residual risk. Do not invent SCADA transport, telemetry reading-ingestion endpoints, or an unverified vulnerability. | docs(security): extend operator threat model | HPR-P0-010
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15505 | HPR-P0-011 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Security / Telemetry; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6019 | HPR-P0-011 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13765 | HPR-P0-011 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Security / Telemetry; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-448"></a>
## HAPI-448 — approve secret and certificate lifecycle

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P0-012

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Obtain owner-approved lifecycle decisions required by audit Check 13: secret-store ownership, JWT signing-secret routine/emergency rotation and invalidation sequence, datasource and LDAP credential rotation sequence, TLS termination ownership, certificate authority/trust model, certificate/private-key storage, renewal/expiry monitoring, revocation procedure, emergency authority, and recovery verification. Values/procedures remain TBD until approved.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/security/SECRETS_AND_CERTIFICATES.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/security/SECRETS_AND_CERTIFICATES.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

- `ad2fee6b064e66dfa50aca2dd89c75a218bc5d36` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13766](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13766)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6020](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6020)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:183](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L183)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15506](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15506)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:183 | HPR-P0-012 | COMPLETED — owner explicitly accepted the recommended lifecycle baseline on 2026-10-05; `SECRETS_AND_CERTIFICATES.md` now records role-based secret ownership, least privilege, 90-day JWT/database/LDAP rotation, coordinated HS256 emergency invalidation with no claimed dual-key overlap, one-time bootstrap handling, Platform/Infrastructure TLS ownership, enterprise/public CA trust model, protected private-key storage, 45/30/14/7-day expiry thresholds, compromise revocation/replacement, emergency authority, and mandatory recovery evidence; tooling remains pending actual platform implementation | Security / Secrets / Certificates | Doc/Decision | Obtain owner-approved lifecycle decisions required by audit Check 13: secret-store ownership, JWT signing-secret routine/emergency rotation and invalidation sequence, datasource and LDAP credential rotation sequence, TLS termination ownership, certificate authority/trust model, certificate/private-key storage, renewal/expiry monitoring, revocation procedure, emergency authority, and recovery verification. Values/procedures remain TBD until approved. | docs(security): approve secret and certificate lifecycle | accountable Security/Operations owner decision
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15506 | HPR-P0-012 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Security / Secrets / Certificates; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6020 | HPR-P0-012 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13766 | HPR-P0-012 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Security / Secrets / Certificates; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-449"></a>
## HAPI-449 — approve incident governance model

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P0-013

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Obtain owner-approved incident-governance decisions required by audit Check 13: incident commander/decision authority, SOC/on-call/security escalation contacts, severity taxonomy, notification/escalation timing, external IdP/LDAP/provider escalation contacts, legal/regulatory notification ownership, and evidence-retention ownership. Do not invent names, timelines, or regulatory obligations.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/security/INCIDENT_RESPONSE.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/security/INCIDENT_RESPONSE.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

- `6b2d2bbfe1bb28c6fef663f635ad98fd7fc6d5d1` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13767](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13767)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6021](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6021)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:184](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L184)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15507](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15507)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:184 | HPR-P0-013 | COMPLETED — owner explicitly accepted the incident-governance baseline on 2026-10-05; `INCIDENT_RESPONSE.md` now records role-based incident command, technical/operations/DB/identity/legal/business ownership, SEV-1..SEV-4 taxonomy, immediate SEV-1 and 30-minute SEV-2 escalation targets, emergency containment authority, provider/database escalation ownership, Legal/Compliance notification authority, evidence ownership/integrity, mandatory SEV-1/2 PIR, and joint closure authority; personal contacts and jurisdiction-specific deadlines remain outside Git | Security / Incident Governance | Doc/Decision | Obtain owner-approved incident-governance decisions required by audit Check 13: incident commander/decision authority, SOC/on-call/security escalation contacts, severity taxonomy, notification/escalation timing, external IdP/LDAP/provider escalation contacts, legal/regulatory notification ownership, and evidence-retention ownership. Do not invent names, timelines, or regulatory obligations. | docs(security): approve incident governance model | accountable Security/Operations/Business owner decision
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15507 | HPR-P0-013 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Security / Incident Governance; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6021 | HPR-P0-013 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13767 | HPR-P0-013 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Security / Incident Governance; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-450"></a>
## HAPI-450 — operationalize security lifecycle procedures

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P0-014

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-448

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

After HPR-P0-012 and HPR-P0-013 approvals, replace the applicable TBD sections in SECRETS_AND_CERTIFICATES.md and INCIDENT_RESPONSE.md with the approved concrete rotation, invalidation, certificate renewal/revocation, compromise escalation, recovery-verification, and closure procedures. Preserve provenance of the approving authority/decision.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/security/INCIDENT_RESPONSE.md`
- `doc/security/SECRETS_AND_CERTIFICATES.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/security/INCIDENT_RESPONSE.md`
- `doc/security/SECRETS_AND_CERTIFICATES.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: HAPI-448. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `850ee4770adc2aff50e81682223458ee28af1530` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13768](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13768)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6022](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6022)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:185](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L185)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15508](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15508)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:185 | HPR-P0-014 | COMPLETED — approved lifecycle/governance decisions are operationalized as platform-neutral runbooks covering routine/emergency JWT rotation and invalidation, PostgreSQL/LDAP rotation, bootstrap handling, certificate renewal/revocation, rotation evidence, recovery acceptance gates, incident activation/evidence/containment/eradication/recovery/communication/closure/PIR; product-specific runtime/secrets/certificate/SIEM commands remain deferred to actual P1 architecture | Security Operations | Doc | After HPR-P0-012 and HPR-P0-013 approvals, replace the applicable TBD sections in `SECRETS_AND_CERTIFICATES.md` and `INCIDENT_RESPONSE.md` with the approved concrete rotation, invalidation, certificate renewal/revocation, compromise escalation, recovery-verification, and closure procedures. Preserve provenance of the approving authority/decision. | docs(security): operationalize security lifecycle procedures | HPR-P0-012..013
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15508 | HPR-P0-014 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Security Operations; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6022 | HPR-P0-014 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13768 | HPR-P0-014 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Security Operations; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-451"></a>
## HAPI-451 — close P0 audit verification gaps

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P0-015

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-444

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Re-run the complete P0 verification gate on the exact final remediation SHA: full Maven verification, HTTP Workbench leakage tests, architecture/security tests, PostgreSQL/Flyway verification, application health/startup, deterministic OpenAPI artifact generation/inspection, and documentary re-audit of Checks 1–13. Close the audit gap only if all 13 checks pass; otherwise record failures and keep P1 blocked.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: HAPI-444. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `bada4bb882b634762756dd115c7d66c864bd0b3b` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13769](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13769)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6023](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6023)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:186](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L186)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15509](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15509)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:186 | HPR-P0-015 | COMPLETED — P0 AUDIT GAP CLOSED. Remediation baseline `850ee4770adc2aff50e81682223458ee28af1530` passed CI run #528. Final closure/audit baseline `bada4bb882b634762756dd115c7d66c864bd0b3b` passed exact-head CI run #529 / run id `37326280500`, job `111817745388`, including Java 21 `./mvnw -B -q clean verify`, PostgreSQL/Testcontainers/Flyway validation of 82 migrations, application startup/health, Workbench HTTP regressions, deterministic OpenAPI generation/security assertions, and artifact upload. Independent re-audit at the closure SHA: **PASS — 13/13 VERIFIED**. Exact-head artifact `11352572397` embeds the closure SHA; all 259 generated operations have explicit security declarations. | Repository / Independent Audit | Code/Doc | Re-run the complete P0 verification gate on the exact final remediation SHA: full Maven verification, HTTP Workbench leakage tests, architecture/security tests, PostgreSQL/Flyway verification, application health/startup, deterministic OpenAPI artifact generation/inspection, and documentary re-audit of Checks 1–13. Close the audit gap only if all 13 checks pass; otherwise record failures and keep P1 blocked. | docs(roadmap): close P0 audit verification gaps | HPR-P0-008..014
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15509 | HPR-P0-015 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Repository / Independent Audit; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6023 | HPR-P0-015 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13769 | HPR-P0-015 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Repository / Independent Audit; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-458"></a>
## HAPI-458 — add production deployment runbook

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P1-007

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-452

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Create DEPLOYMENT_RUNBOOK.md and ENVIRONMENT_CONFIGURATION.md; require explicit production profile, secrets, PostgreSQL preparation, Flyway, startup, acceptance and rollback.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/operations/DEPLOYMENT_RUNBOOK.md`
- `doc/operations/ENVIRONMENT_CONFIGURATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/operations/DEPLOYMENT_RUNBOOK.md`
- `doc/operations/ENVIRONMENT_CONFIGURATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-007 production-deployment documentation evidence

- execution base SHA: `59c8b913e703e08386bab49850405e307d16002d`;
- pre-task lightweight documentation validation: run #4 / run id `37343323013`: **SUCCESS**;
- deployment requires an identified verified HidraAPI artifact/revision and explicit `SPRING_PROFILES_ACTIVE=production`;
- production datasource configuration uses the approved stable database endpoint and externalized credentials;
- production startup must preserve Flyway validation/clean-disabled behavior and JPA `ddl-auto=validate`;
- deployment must prevent uncontrolled concurrent schema migration from multiple application nodes;
- nodes are admitted to traffic only after required health/readiness and security/database acceptance checks pass;
- rollback distinguishes application-artifact rollback from database-schema recovery and forbids rewriting applied Flyway migrations;
- runtime secret values must not be committed to Git or deployment evidence;
- hosting/orchestration, load-distribution, secret-manager, TLS termination, broker/cache, and CD products remain unselected;
- HPR-P1-009 remains blocked on deployment-target approval.


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

Canonical prerequisites: HAPI-452. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `656a42256d3849c78e1b93d3792e7cceceed4123` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13776](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13776)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6030](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6030)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:198](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L198)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2201](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2201)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15516](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15516)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:198 | HPR-P1-007 | COMPLETED — product-neutral production deployment runbook and environment configuration created from repository evidence and approved runtime/HA/security decisions; they require exact artifact identity, explicit production profile, externalized secrets, stable PostgreSQL endpoint, controlled Flyway/JPA validation, serialized migration authority, readiness-based admission, acceptance checks, evidence capture and rollback/abort criteria; hosting/traffic/secret-manager/TLS/CD products remain unselected | Deployment | Doc | Create `DEPLOYMENT_RUNBOOK.md` and `ENVIRONMENT_CONFIGURATION.md`; require explicit production profile, secrets, PostgreSQL preparation, Flyway, startup, acceptance and rollback. | docs(operations): add production deployment runbook | HPR-P1-001..003
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15516 | HPR-P1-007 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Deployment; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6030 | HPR-P1-007 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13776 | HPR-P1-007 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Deployment; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-463"></a>
## HAPI-463 — close P1 survivability verification

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P1-012

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-472

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Close P1 only after all repository defects identified by the 2026-10-06 re-audit are remediated and the integrated production-equivalent exercise evidence is retained.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/scripts/validate_p1_closure.py`
- `.github/workflows/ci.yml`
- `doc/operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/scripts/validate_p1_closure.py`
- `.github/workflows/ci.yml`
- `doc/operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## HPR-P1-012 relationship

HPR-P1-012 must use real output from the PITR harness and must independently calculate:

- achieved RPO against ≤ 5 minutes;
- achieved RTO against ≤ 60 minutes;
- application/security acceptance after recovery.

Configuration presence alone is not DR evidence.
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

Canonical prerequisites: HAPI-472. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `6822f3ce79b305e1c22f48ff20ef0b0bd2c7f135` — exact registered-message commit; surviving paths matched to current tree.
- `c7d4fad3b317e401b4ff68d78f5338435ff593f5` — exact registered-message commit; surviving paths matched to current tree.
- `52bc53b6bc9cc9b00f893996b095f808c0d0ce53` — exact registered-message commit; surviving paths matched to current tree.
- `948eb2af2c8b723841ae3d07aba8abdb29c5f446` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/database/BACKUP_PITR_IMPLEMENTATION.md:61](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/database/BACKUP_PITR_IMPLEMENTATION.md#L61)
- [doc/database/DATABASE_OPERATIONS_RUNBOOK.md:458](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/database/DATABASE_OPERATIONS_RUNBOOK.md#L458)
- [doc/model-remediation/RECONCILIATION.md:13781](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13781)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6035](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6035)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:203](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L203)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15521](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15521)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:203 | HPR-P1-012 | COMPLETED — P1 CLOSED. HPR-P1-021..029 are complete; canonical production-equivalent evidence is retained in `doc/operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md`. Final reconciliation verifies all 12 independent P1 audit checks: preserved checks 1/7/8 plus remediated-and-exercised checks 2–6 and 9–12. Measured RPO 15s ≤5m and RTO 37m ≤60m; both-node REST HA, PostgreSQL single-writer/fencing/Hikari recovery, exact-schema PITR, controlled deployment/rollback, alert delivery/recovery, database maintenance acceptance and independent repo2/bootstrap retention evidence are retained. This closure commit is valid only with successful exact-head full CI, including production artifact validators, Java 21 `clean verify`, PostgreSQL/Flyway startup verification and deterministic OpenAPI compatibility/publication gates. | Survivability Verification | Infra/Doc | Close P1 only after all repository defects identified by the 2026-10-06 re-audit are remediated and the integrated production-equivalent exercise evidence is retained. | docs(roadmap): close P1 survivability verification | HPR-P1-021..029
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15521 | HPR-P1-012 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Survivability Verification; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6035 | HPR-P1-012 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13781 | HPR-P1-012 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Survivability Verification; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-464"></a>
## HAPI-464 — approve production infrastructure stack

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P1-013

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Obtain and record the concrete production infrastructure selections required to implement P1: deployment/runtime target, traffic-distribution mechanism, PostgreSQL HA/promotion/fencing/stable-endpoint mechanism, backup/WAL/PITR tooling and protected storage, secret/configuration injection mechanism, monitoring/alert-routing/logging platform, and the controlling enterprise policy identifier plus exact monthly backup recovery-point retention/hold rule. Preserve approved RTO/RPO and do not invent products or retention values.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/operations/PRODUCTION_INFRASTRUCTURE_STACK.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/operations/PRODUCTION_INFRASTRUCTURE_STACK.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-013 production infrastructure decision evidence

Owner acceptance date: 2026-10-05.

Approved stack:

- HidraAPI runtime: Linux virtual machines with systemd-managed Java 21 services;
- application traffic distribution: HAProxy;
- PostgreSQL HA: Patroni-managed PostgreSQL with etcd quorum for leader/coordination state;
- stable application-facing PostgreSQL endpoint: HAProxy;
- database backup/WAL/PITR: pgBackRest;
- backup repository/storage: protected storage independent from the live PostgreSQL primary/standby nodes and failure path;
- runtime secrets/configuration: HashiCorp Vault for secret material, with non-secret configuration externalized through the approved runtime mechanism;
- metrics collection: Prometheus;
- alert evaluation/routing: Alertmanager;
- dashboards: Grafana;
- centralized application logs: Grafana Loki;
- realtime for P1: single-active realtime operation while REST remains multi-node; no clustered realtime HA claim until a shared broker/mechanism is separately approved and verified;
- cache for P1: retain Spring process-local cache only for non-authoritative optimization; do not introduce Redis solely for P1;
- operational backup retention remains the already-approved 35-day window.

Still unresolved:

- controlling enterprise/SONATRACH records-policy identifier for monthly retained recovery points;
- exact monthly backup recovery-point retention/hold duration/rule under that policy.

No monthly duration is inferred or fabricated. HPR-P1-017 may implement pgBackRest/WAL/PITR mechanics and 35-day operational retention, but it cannot be marked complete until the monthly policy rule is supplied and enforced.

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

- `7469a424c84d3b4454ff8db2eda27d660a8e437c` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13782](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13782)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6036](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6036)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:204](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L204)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2034](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2034)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15522](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15522)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:204 | HPR-P1-013 | COMPLETED — owner accepted the production technology stack on 2026-10-05 and subsequently approved the remaining P1 owner values: 35-day operational retention plus 12 monthly recovery points retained for 12 months under `HIDRA-P1-BACKUP-RETENTION-001`, two-reviewer GitHub production approval, independent monitoring, 30-day Prometheus and 90-day operational-log retention, defined alert-routing domains, and the production-equivalent exercise strategy. Actual deployed hostnames/endpoints/versions remain evidence rather than architecture decisions. | Production Infrastructure Decisions | Decision/Doc | Obtain and record the concrete production infrastructure selections required to implement P1: deployment/runtime target, traffic-distribution mechanism, PostgreSQL HA/promotion/fencing/stable-endpoint mechanism, backup/WAL/PITR tooling and protected storage, secret/configuration injection mechanism, monitoring/alert-routing/logging platform, and the controlling enterprise policy identifier plus exact monthly backup recovery-point retention/hold rule. Preserve approved RTO/RPO and do not invent products or retention values. | docs(operations): approve production infrastructure stack | accountable Platform/DB/Security/Operations owner decisions
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15522 | HPR-P1-013 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Production Infrastructure Decisions; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6036 | HPR-P1-013 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13782 | HPR-P1-013 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Production Infrastructure Decisions; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-469"></a>
## HAPI-469 — add controlled deployment pipeline

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P1-018

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-458, HAPI-464

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Complete former HPR-P1-009 after deployment-target approval: implement controlled release promotion, approval, exact-artifact deployment, explicit production-profile/configuration validation, post-deployment health/security/database acceptance, rollback gates and doc/operations/CI_CD_RELEASE_GUIDE.md; retain executable evidence from the selected target.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/workflows/ci.yml`
- `.github/workflows/release.yml`
- `doc/operations/CI_CD_RELEASE_GUIDE.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/release/deploy-production-node.sh`
- `ops/production/release/install-release.sh`
- `ops/production/release/rollback-release.sh`
- `ops/production/release/validate-release-artifacts.sh`
- `ops/production/systemd/hidra-api.service`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/workflows/ci.yml`
- `.github/workflows/release.yml`
- `doc/operations/CI_CD_RELEASE_GUIDE.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/release/deploy-production-node.sh`
- `ops/production/release/install-release.sh`
- `ops/production/release/rollback-release.sh`
- `ops/production/release/validate-release-artifacts.sh`
- `ops/production/systemd/hidra-api.service`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-018 controlled release implementation evidence

- execution base SHA: `4923d8cc3a770e108e9171feb3d53a833fa7300f`;
- pre-task full CI run #539 / run id `37361134307`: **SUCCESS**;
- pre-task documentation validation run #14 / run id `37361134225`: **SUCCESS**;
- added `.github/workflows/release.yml` with validate-only and deploy-production modes;
- release identity requires a full 40-character SHA reachable from main plus successful exact-SHA HidraAPI CI;
- the exact candidate JAR is built once, SHA-256 hashed, manifested, uploaded, downloaded and digest-verified before deployment;
- deploy job targets GitHub Environment production; required-reviewer protection is an external repository setting and cannot be proven by repository source through the available connector;
- runtime application secrets remain Vault-backed on the VMs; workflow does not transport them, while systemd/install preflight requires non-empty /run/hidra/hidra-secrets.env with HIDRA_SECRETS_SOURCE=vault;
- rollout drains/deploys hidra-api-2 before realtime-active hidra-api-1, checks local readiness, executes a configured authenticated database-backed acceptance request, rejoins HAProxy and checks HA readiness;
- schema-change mode is explicit; automatic artifact rollback is allowed only when previous-artifact schema compatibility is explicitly declared;
- rollback never edits Flyway history or implicitly reverses database schema;
- static CI validates workflow/scripts/systemd release controls;
- no live production-equivalent deployment or rollback exercise is claimed; HPR-P1-018 remains IMPLEMENTED-PENDING-ENVIRONMENT-AND-EXERCISE.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

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

Canonical prerequisites: HAPI-458, HAPI-464. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `deb24ec6d3707b94bae7704581ad30877516fc78` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13787](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13787)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6041](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6041)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:209](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L209)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1964](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1964)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15527](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15527)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:209 | HPR-P1-018 | COMPLETED — production-equivalent exact-artifact deployment/rollback and two-approval governance evidence retained by HPR-P1-029;  added manual controlled release automation with exact-SHA main ancestry and exact-SHA full-CI success gates, immutable JAR + SHA-256 manifest, GitHub `production` Environment boundary, sequential HAProxy drain/deploy/rejoin, mandatory production/Vault runtime preflight, configurable authenticated database-backed acceptance, and schema-aware rollback gating. The systemd runtime now requires the Vault-rendered secret file. Static CI validates release artifacts and `CI_CD_RELEASE_GUIDE.md` documents required production variables/secrets/protection. Repository source cannot prove required-reviewer protection is configured, and no production-equivalent deployment/rollback exercise has yet run. | CD / Release | Infra/Test/Doc | Complete former HPR-P1-009 after deployment-target approval: implement controlled release promotion, approval, exact-artifact deployment, explicit production-profile/configuration validation, post-deployment health/security/database acceptance, rollback gates and `doc/operations/CI_CD_RELEASE_GUIDE.md`; retain executable evidence from the selected target. | ci(release): add controlled deployment pipeline | HPR-P1-013..014 + HPR-P1-007
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15527 | HPR-P1-018 | COMPLETED within recorded scope | COMPLETED_PRESERVED | CD / Release; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6041 | HPR-P1-018 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13787 | HPR-P1-018 | COMPLETED within recorded scope | COMPLETED_PRESERVED | CD / Release; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-470"></a>
## HAPI-470 — implement production alerting

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P1-019

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-461, HAPI-464

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Complete former HPR-P1-010 after observability decisions: approve concrete SLIs/SLOs/evaluation windows and numeric thresholds, configure collection/dashboards/log routing as applicable, implement executable alert rules and on-call/escalation routing for application/HA/database/backup/security conditions, and verify representative alert firing, delivery and recovery without secret leakage.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/workflows/ci.yml`
- `doc/operations/OBSERVABILITY_AND_SRE.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/observability/README.md`
- `ops/production/observability/alertmanager/alertmanager.yml.tpl`
- `ops/production/observability/grafana/datasources.yml.tpl`
- `ops/production/observability/hidra-pgbackrest-exporter.service`
- `ops/production/observability/loki/loki.yml`
- `ops/production/observability/pgbackrest_exporter.py`
- `ops/production/observability/prometheus/prometheus.yml.tpl`
- `ops/production/observability/prometheus/rules/hidra-alerts.yml`
- `ops/production/observability/validate-observability-artifacts.sh`
- `ops/production/observability/verify-alert-routing.sh`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/workflows/ci.yml`
- `doc/operations/OBSERVABILITY_AND_SRE.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/observability/README.md`
- `ops/production/observability/alertmanager/alertmanager.yml.tpl`
- `ops/production/observability/grafana/datasources.yml.tpl`
- `ops/production/observability/hidra-pgbackrest-exporter.service`
- `ops/production/observability/loki/loki.yml`
- `ops/production/observability/pgbackrest_exporter.py`
- `ops/production/observability/prometheus/prometheus.yml.tpl`
- `ops/production/observability/prometheus/rules/hidra-alerts.yml`
- `ops/production/observability/validate-observability-artifacts.sh`
- `ops/production/observability/verify-alert-routing.sh`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-019 production alerting implementation evidence

- execution base SHA: `deb24ec6d3707b94bae7704581ad30877516fc78`;
- pre-task exact-head full CI #540 attempt 2: **SUCCESS**;
- pre-task exact-head documentation validation #15 attempt 3: **SUCCESS**;
- owner approved the P1 SLO/threshold baseline on 2026-10-05: 99.9% monthly availability excluding approved maintenance; HTTP 5xx, p95 latency, node redundancy, CPU/JVM, Hikari, PostgreSQL replication, backup and monitoring thresholds are now explicit;
- selected stack is Prometheus + Alertmanager + Grafana + Loki;
- Prometheus scrape template covers HidraAPI, HAProxy, Patroni and the repository-owned pgBackRest metrics exporter;
- executable Prometheus rules implement application/HA/database/backup/monitoring alerts with owner/domain/severity labels;
- Alertmanager routes critical, database/backup and security domains to separate controlled receiver placeholders; no real endpoint or credential is committed;
- pgBackRest exporter emits latest backup age and pgBackRest stanza-check success without secret-bearing labels;
- Grafana datasource provisioning binds Prometheus and Loki; Loki baseline config is included without claiming Loki HA;
- static CI validates the observability package and performs syntax checks where promtool/amtool are available;
- synthetic routing script submits warning/critical/database/security alerts to Alertmanager for production-equivalent route testing;
- no live receiver delivery, escalation, resolve event, Loki ingestion or no-secret runtime evidence is claimed; HPR-P1-019 remains IMPLEMENTED-PENDING-LIVE-EXERCISE.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

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

Canonical prerequisites: HAPI-461, HAPI-464. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `68e1b9f5d4b29078c8918ce61c5732b99b00874e` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13788](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13788)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6042](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6042)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:210](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L210)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1948](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1948)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15528](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15528)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:210 | HPR-P1-019 | COMPLETED — production-equivalent alert firing/delivery/acknowledgement/resolve evidence retained by HPR-P1-029;  owner approved the P1 SLI/SLO/threshold baseline on 2026-10-05. Repository implementation now provides Prometheus scrape configuration and executable alert rules for application availability/redundancy, HTTP 5xx/latency, CPU/JVM, Hikari, PostgreSQL HA/replication lag, pgBackRest backup/WAL continuity and monitoring self-health; Alertmanager routing templates for operations/database/security; Grafana Prometheus/Loki datasource provisioning; Loki baseline configuration; a repository-owned pgBackRest metrics exporter; static CI validation; and a synthetic Alertmanager routing exercise. Live production-equivalent rule firing, receiver delivery/escalation, resolve behavior, Loki ingestion and no-secret evidence are still required before this HPR is measured/verified. | Observability / SRE | Infra/Test/Doc | Complete former HPR-P1-010 after observability decisions: approve concrete SLIs/SLOs/evaluation windows and numeric thresholds, configure collection/dashboards/log routing as applicable, implement executable alert rules and on-call/escalation routing for application/HA/database/backup/security conditions, and verify representative alert firing, delivery and recovery without secret leakage. | feat(operations): implement production alerting | HPR-P1-013 + HPR-P1-010 operating model
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15528 | HPR-P1-019 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Observability / SRE; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6042 | HPR-P1-019 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13788 | HPR-P1-019 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Observability / SRE; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-477"></a>
## HAPI-477 — wire production observability signals

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P1-026

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-470

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Complete production observability wiring: provide an approved authenticated scrape path for protected HidraAPI metrics without exposing them publicly, enable the HTTP histogram required by latency rules, expose/scrape HAProxy metrics used by availability rules, correct Alertmanager route precedence so critical database/security/backup alerts reach their domain receivers as approved, and extend static/native validation for the resulting Prometheus/Alertmanager/HAProxy configuration.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `ops/production/haproxy/hidra-api.cfg`
- `ops/production/observability/README.md`
- `ops/production/observability/alertmanager/alertmanager.yml.tpl`
- `ops/production/observability/prometheus/prometheus.yml.tpl`
- `ops/production/observability/validate-observability-artifacts.sh`
- `ops/production/postgres/haproxy/hidra-postgres.cfg.tpl`
- `src/main/resources/application-production.properties`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `ops/production/haproxy/hidra-api.cfg`
- `ops/production/observability/README.md`
- `ops/production/observability/alertmanager/alertmanager.yml.tpl`
- `ops/production/observability/prometheus/prometheus.yml.tpl`
- `ops/production/observability/validate-observability-artifacts.sh`
- `ops/production/postgres/haproxy/hidra-postgres.cfg.tpl`
- `src/main/resources/application-production.properties`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-026 observability wiring evidence

- implementation SHA: `472811292c271241c94647e71deb1ffe59086d0c`;
- exact-head full CI #553 / run id `37430996964`: **SUCCESS**;
- protected HidraAPI metrics remain authenticated; Prometheus uses an externally rendered bearer-token file rather than public Actuator exposure;
- production enables the HTTP request histogram required by the p95 Prometheus rule;
- both application and PostgreSQL HAProxy configurations expose dedicated monitoring-only Prometheus exporters and the Prometheus template scrapes both;
- Alertmanager route ordering now gives security/database/backup ownership precedence over generic critical routing;
- static/native observability validation covers authenticated scraping, histogram binding, HAProxy exporters and route order;
- actual production-equivalent scrape success, rule firing, recipient delivery and resolve/recovery evidence remain HPR-P1-029 scope.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

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

Canonical prerequisites: HAPI-470. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `472811292c271241c94647e71deb1ffe59086d0c` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13795](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13795)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6049](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6049)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:217](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L217)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1823](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1823)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15535](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15535)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:217 | HPR-P1-026 | COMPLETED — implementation SHA `472811292c271241c94647e71deb1ffe59086d0c` passed exact-head full CI #553 / run id `37430996964`. Prometheus now has an authenticated bearer-token-file path for protected HidraAPI metrics, production explicitly enables the `http.server.requests` histogram required by p95 rules, both application and PostgreSQL HAProxy configurations expose dedicated monitoring-only Prometheus exporters, Prometheus scrapes both proxies, Alertmanager evaluates security/database/backup domains before the generic critical route, and static/native validation asserts those bindings. Live rule firing, receiver delivery and resolve evidence remain HPR-P1-029 scope. | Observability / SRE | Infra/Test/Doc | Complete production observability wiring: provide an approved authenticated scrape path for protected HidraAPI metrics without exposing them publicly, enable the HTTP histogram required by latency rules, expose/scrape HAProxy metrics used by availability rules, correct Alertmanager route precedence so critical database/security/backup alerts reach their domain receivers as approved, and extend static/native validation for the resulting Prometheus/Alertmanager/HAProxy configuration. | fix(operations): wire production observability signals | HPR-P1-019
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15535 | HPR-P1-026 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Observability / SRE; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6049 | HPR-P1-026 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13795 | HPR-P1-026 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Observability / SRE; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-491"></a>
## HAPI-491 — establish verification documentation

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P2-011

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-482

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Create test strategy, architecture testing, database testing, API testing and requirements traceability documents tied to executable evidence.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/scripts/resolve_openapi_base.py`
- `.github/scripts/validate_p1_closure.py`
- `.github/workflows/ci.yml`
- `.github/workflows/docs.yml`
- `README.md`
- `doc/README.md`
- `doc/api/openapi.yaml`
- `doc/governance/DOCUMENT_REGISTER.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/testing/API_TESTING.md`
- `doc/testing/ARCHITECTURE_TESTING.md`
- `doc/testing/DATABASE_TESTING.md`
- `doc/testing/README.md`
- `doc/testing/REQUIREMENTS_TRACEABILITY.md`
- `doc/testing/TEST_STRATEGY.md`
- `pom.xml`
- `src/main/resources/application-test.properties`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/DomainInvariantGuardrailTest.java`
- `src/test/java/dz/sh/hidra/DomainPersistenceMirrorGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/HidraApplicationTests.java`
- `src/test/java/dz/sh/hidra/InternalReferenceIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/GeneratedBoundaryMapperContractTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/PlanActualDeviationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationMandatoryReferenceMigrationSupport.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/NominationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanTargetSemanticPostgresIntegrationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`.
- Primary/secondary adapters: `src/test/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/PlanActualDeviationSemanticPostgresIntegrationTest.java`, `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/NominationSemanticPostgresIntegrationTest.java`, `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanTargetSemanticPostgresIntegrationTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/scripts/resolve_openapi_base.py`
- `.github/scripts/validate_p1_closure.py`
- `.github/workflows/ci.yml`
- `.github/workflows/docs.yml`
- `README.md`
- `doc/README.md`
- `doc/api/openapi.yaml`
- `doc/governance/DOCUMENT_REGISTER.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/testing/API_TESTING.md`
- `doc/testing/ARCHITECTURE_TESTING.md`
- `doc/testing/DATABASE_TESTING.md`
- `doc/testing/README.md`
- `doc/testing/REQUIREMENTS_TRACEABILITY.md`
- `doc/testing/TEST_STRATEGY.md`
- `pom.xml`
- `src/main/resources/application-test.properties`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/DomainInvariantGuardrailTest.java`
- `src/test/java/dz/sh/hidra/DomainPersistenceMirrorGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/HidraApplicationTests.java`
- `src/test/java/dz/sh/hidra/InternalReferenceIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/GeneratedBoundaryMapperContractTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/PlanActualDeviationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationMandatoryReferenceMigrationSupport.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/NominationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanTargetSemanticPostgresIntegrationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## HPR-P2-011 verification-documentation preflight — 2026-10-09

### Selection and exact baseline

The owner's Next after HPR-P2-010 selects this preflight only. Main is unchanged at
`50fd7f9cb97343a6495c2dff5bf54175145bba39`, tree
`67f4b002c6450729402d9a5a8fc351b68662665e`. Exact-head Documentation Validation
#120 (run 37896521355) PASSED. Production CI #604 (run 37841205677) PASSED on
executable baseline `617c2eec812e3a5734957ee9fa0360f6f5613032`, tree
`e450c699544f99e96f2b447fc04215f7f4c5f344`. Its job steps confirm repository
clean verify, operational-artifact validation, OpenAPI generation/base selection,
compatibility and artifact upload succeeded. These are prior CI results checked now,
not a new local execution, per-class report inspection or physical exercise.

HPR-P2-011 remains PENDING; no testing set is created now. A subsequent Next after
this published preflight accepts the complete design and exhaustive scope below for
one implementation commit: `docs(testing): establish verification documentation`.
HPR-P2-012/013 remain PENDING, P3 DEFERRED and P2 OPEN.

### Current evidence recovered

- `pom.xml` declares Java 21, Spring Boot parent 4.1.1, JUnit/Spring testing,
  Spring Security testing, Testcontainers PostgreSQL and ArchUnit 1.4.2, and a
  Surefire plugin. No Failsafe or JaCoCo plugin or numeric coverage gate is declared
  in the inspected POM/workflows. Do not invent a separate integration-test phase,
  coverage percentage, mutation gate or test count from file counts.
- Static inventory: 324 test-tree Java files; 323 filenames ending Test.java/Tests.java
  and one OrganizationMandatoryReferenceMigrationSupport.java helper; tests occur
  under all 24 module roots. All 47 directly annotated Testcontainers classes use
  disabledWithoutDocker=true. No direct @Disabled annotation was found. These are
  source counts, not discovered/executed test cases or proof that nothing can skip.
- `.github/workflows/ci.yml` runs `./mvnw -B -q clean verify` with Java 21 and a
  PostgreSQL 16 service. The test profile is actually
  `src/main/resources/application-test.properties`, not src/test/resources. It
  defaults to localhost PostgreSQL and permits datasource overrides; individual
  containers override their own URLs. HidraApplicationTests uses postgres:18-alpine,
  Flyway validation and JPA validate; focused containers include postgres:16-alpine.
  Do not turn these distinct paths into a claim that every test uses one DB/version.
- `ArchitectureGuardrailTest`, `ForensicRemediationClosureTest`,
  `DomainPersistenceMirrorGuardrailTest`, `DomainInvariantGuardrailTest` and
  `modules/GeneratedBoundaryMapperContractTest` enforce distinct boundaries through
  ArchUnit, source/reflection and generated-mapper checks. Owner-provider tests are
  a separate contract population, not substitutes for live transaction evidence.
- `InternalReferenceIntegrityMigrationTest` exercises Flyway and classified local
  foreign keys, including orphan fail-closed/rollback checks. Focused
  PlanTargetSemanticPostgresIntegrationTest, PlanActualDeviationSemanticPostgresIntegrationTest
  and NominationSemanticPostgresIntegrationTest execute selected migrations, direct
  SQL and real Spring/JPA transactions, rollback and controlled race cases. Some
  owner-contract inputs are stubbed in those fixtures; database/transaction reality
  does not imply a complete live cross-module end-to-end environment.
- HTTP/controller tests include standalone MockMvc and mocks; security/authorization,
  request context, workbench exposure, document transfer and generated contracts have
  different scopes. DocumentContentTransferServiceTest distinguishes confirmed
  rollback from unknown/committed outcomes. Presence of a controller test is not a
  blanket assertion that the full security filter chain or every route was exercised.
- CI generates sorted OpenAPI JSON with source-SHA provenance and representative
  security assertions; `.github/scripts/resolve_openapi_base.py` uses the exact PR
  target or a successful production-CI ancestor for push/dispatch, with ancestry and
  fail-closed selection. It runs test_resolve_openapi_base.py before selection and
  openapi_compatibility.py against generated base/current contracts. The canonical
  `doc/api/openapi.yaml` retains its own older HPR-P2-005 generation provenance; this
  task neither refreshes it nor asserts exact current snapshot equality.
- `.github/workflows/docs.yml` checks canonical Markdown UTF-8/nonempty/conflicts,
  not links/status/index drift or canonical-vs-generated OpenAPI equality. Production
  CI ignores doc/**, docs/** and Markdown-only scope. Its explicit uploaded artifact
  is the generated OpenAPI contract; do not describe a configured Surefire report
  upload or invent per-test results without actual retained report evidence.
- P1 scripts in ops/production/ validate artifacts/procedures and CI checks
  `.github/scripts/validate_p1_closure.py`. The retained P1 exercise distinguishes
  repository-verified from operator-supplied observations at deployed
  `66f6d7f12d1f7d52f8725cd4747cf4c777bfd29a` with 82 migrations. A static artifact
  check or PostgreSQL fixture is not new HA/DR/physical retention evidence for the
  current 139-version migration chain.

### Proposed implementation design — ten parts

1. Create a six-document CURRENT testing set with inherited index metadata: source
   parent/date, status, repository/module owner authority, actual evidence and
   explicit unresolved facts. No invented named testing authority, production
   acceptance or executed result. Separate implemented checks from TARGET additions.
2. TEST_STRATEGY.md documents the actual test populations and their limits: pure
   domain/application/unit, owner-provider contracts, source/ArchUnit/reflection,
   generated mappers, standalone HTTP/security, PostgreSQL migration, Spring-JPA
   transaction/race, full application context, CI OpenAPI and operational artifact
   checks. Distinguish source presence, selected local run, exact-head CI, skipped
   classes, deployment acceptance and retained physical/operator evidence.
3. Give source-backed commands for focused selections, full test and clean verify;
   describe Java/Maven prerequisites, dependency availability, Docker/images, isolated
   disposable PostgreSQL and actual test profile overrides. The current preflight
   and docs-only implementation require no Maven/runtime rerun; prior dependency/DNS
   limitations are historical, not a diagnosis of the present environment. Any later
   attempted command must report actual exit/results/skips and exact limitation.
   Never point destructive schema-reset tests at production or disguise a skip as pass.
4. ARCHITECTURE_TESTING.md explains actual rules and their owning tests, the narrow
   exported contract/allowlist mechanism, kernel/framework and application/API/JPA
   boundaries, reviewed workbench exception, owned persistence models and DTO mapper
   contracts. Describe rule-test mechanisms and scope accurately; package/file counts
   are not dynamic behavioral or universal semantic coverage.
5. DATABASE_TESTING.md separates full Flyway/context validation from targeted SQL
   migration fixtures and real Spring/JPA adapter tests, including explicit mocked
   owner inputs. Link concrete orphan/policy fail-closed, replay/snapshot/optionality,
   transaction rollback and both-order race examples. Test fixtures are approved test
   facts, not production policy seeds; passing empty fixtures does not prove populated
   deployments have approved mappings. Preserve all applied migrations/history.
6. API_TESTING.md distinguishes standalone MockMvc/controller tests, security/context
   and permission tests, full application startup and runtime contract generation.
   Record current compatibility-base resolver/checker, representative security
   assertions and artifact provenance, plus limitations of its supported comparison.
   Canonical OpenAPI and older API/error documents retain their own applicability;
   no contract regeneration, endpoint change or universal error-envelope claim.
7. REQUIREMENTS_TRACEABILITY.md gives a source-backed evidence matrix for completed
   platform gate categories and permanent module semantics. Include all 57 closed
   HMR-050..106 obligations with their HMSR/subject, canonical decision/module,
   exact test source, relevant migration/contract evidence and historical CI scope
   where actually established. Reference the 123-subject semantic catalogue rather
   than duplicate it as a new execution register. Verify uniqueness/contiguity;
   review disposition, HMR completion, test presence and actual execution remain
   different facts. Explicitly mark missing/partial/runtime-unverified evidence.
8. Cover all 24 implemented module roots by actual evidence navigation; link existing
   module/domain/data/database/API/security/operations sources without editing them.
   Preserve the closed 57-row HMR register and all unrelated HPR statuses. Do not
   infer exhaustive branch/endpoint/performance/load/OT safety coverage, AI/solver
   execution, imported production acceptance or mature physical backup coverage.
9. Update doc/README.md and DOCUMENT_REGISTER.md to register all six testing documents
   with exact source applicability. Existing docs/** and canonical historical evidence
   stay byte-preserved. No code, test, configuration, POM, migration, workflow, dataset,
   release/tag or extra testing artifact is admitted by this documentation task.
10. Record owner acceptance, actual validation and completion in both canonical
    execution memories after transfer. Complete only HPR-P2-011. Documentation drift
    enforcement belongs to HPR-P2-012, final closure to HPR-P2-013; neither is executed
    now. Unknown coverage/enforcement alone is a documented gap, not a new runtime
    requirement or a reason to manufacture validation evidence.

### Exhaustive future implementation write scope — ten paths

Create only:

- `doc/testing/README.md`
- `doc/testing/TEST_STRATEGY.md`
- `doc/testing/ARCHITECTURE_TESTING.md`
- `doc/testing/DATABASE_TESTING.md`
- `doc/testing/API_TESTING.md`
- `doc/testing/REQUIREMENTS_TRACEABILITY.md`

Update only:

- `doc/README.md`
- `doc/governance/DOCUMENT_REGISTER.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`

### Admitted implementation validation and publication

Recheck current main, exact-head documentation CI and the green executable baseline
before implementation. Recover actual POM/workflow/test profile, source inventory,
architecture/owner/HTTP/database examples, compatibility logic and prior P1 applicability.
Verify six-document metadata, 24-module navigation and all 57 HMR traceability rows;
resolve all touched-set relative links/anchors and cited source paths. Do not equate
source counts or a job success with per-class execution/no skips without reports.

Validate canonical UTF-8/nonempty/conflict checks (88 currently; six new files make
94), git diff --check, exhaustive ten-path scope, untouched legacy/source/configuration
and published migration preservation. HMR rows and unrelated HPR statuses remain
unchanged; P2 rows retain seven columns; project version stays 0.6.0-SNAPSHOT.
Documentation-only scope requires no Maven/runtime rerun. If a runtime check is
actually attempted, record its real results and prerequisites instead of importing
old local limitations. No deployment/import/physical campaign is authorized here.

Use `docs(testing): establish verification documentation`, one commit, direct main
publication once with a fresh expected-head lease. Verify immutable parent/tree/blobs
and all other files unchanged. Confirm Documentation Validation starts, then STOP
without waiting for completion. Production CI ignores this docs-only scope. A later
Next selects HPR-P2-012 preflight after this implementation's exact-head documentation
result; failure requires repair of the responsible scope first.

### This preflight's supporting execution

Registered supporting message: `docs(testing): record verification documentation preflight`.
Write ONLY `doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md`; no doc/testing/ document is created now.
Check identical envelope text, ten unique future paths, preserved HMR/HPR rows, all
88 canonical Markdown files, git diff --check and exact two-file scope. Publish one
supporting commit directly to main using expected head
`50fd7f9cb97343a6495c2dff5bf54175145bba39`; verify immutable tree/blobs and every
other tracked file unchanged. Confirm Documentation Validation starts and STOP.
HPR-P2-011 remains PENDING until accepted implementation actually completes.

### Actual preflight validation

`python3 /workspace/scratch/4115643ef669/p2-011-preflight-validate.py` PASSED:
identical preflight envelopes in both memories; exactly two modified files; ten unique
future paths with six new documents; all 88 canonical Markdown files UTF-8/nonempty
and free of conflict markers; cited test classes/helpers resolve; all HMR/HPR table
rows unchanged and P2 row shape preserved. `git diff --check` PASSED. No testing set,
Maven/runtime check, source/test/configuration change, migration or operational
execution was performed. Published-tree validation additionally verifies that every
other tracked blob is byte-identical to the parent. The registered implementation
remains PENDING and requires the subsequent acceptance described above.

~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — exact-head oracle suite 16 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/DomainInvariantGuardrailTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/DomainPersistenceMirrorGuardrailTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/HidraApplicationTests.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/InternalReferenceIntegrityMigrationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/GeneratedBoundaryMapperContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/PlanActualDeviationSemanticPostgresIntegrationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/NominationSemanticPostgresIntegrationTest.java` — exact-head oracle suite 13 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanTargetSemanticPostgresIntegrationTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-482. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `6cc9974b54e9d5c82334a62214a76f89519e7aef` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:5918](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L5918)
- [doc/model-remediation/RECONCILIATION.md:6117](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L6117)
- [doc/model-remediation/RECONCILIATION.md:6127](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L6127)
- [doc/model-remediation/RECONCILIATION.md:13809](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13809)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6063](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6063)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:342](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L342)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:8033](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L8033)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:8232](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L8232)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:8242](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L8242)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15549](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15549)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:342 | HPR-P2-011 | COMPLETED — six source-backed verification documents established; all 24 module roots and 57 closed HMR obligations linked; ten-path and 94-file Markdown checks passed; test presence, historical CI and uninspected execution/skip evidence remain distinct; exact-head documentation CI follows publication | Testing | Doc | Create test strategy, architecture testing, database testing, API testing and requirements traceability documents tied to executable evidence. | docs(testing): establish verification documentation | HPR-P2-002..006
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15549 | HPR-P2-011 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Testing; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6063 | HPR-P2-011 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13809 | HPR-P2-011 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Testing; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-492"></a>
## HAPI-492 — validate canonical documentation

Phase: HPS-024 — Security, DevSecOps and Quality

Legacy IDs: HPR-P2-012

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-481

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add documentation validation for canonical links/status/index drift and deterministic OpenAPI contract checks.

### 2. Existing Implementation

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/documentation-validation.json`
- `.github/scripts/openapi_compatibility.py`
- `.github/scripts/test_validate_docs.py`
- `.github/scripts/test_validate_openapi_snapshot.py`
- `.github/scripts/validate_docs.py`
- `.github/scripts/validate_openapi_snapshot.py`
- `.github/workflows/ci.yml`
- `.github/workflows/docs.yml`
- `doc/README.md`
- `doc/api/API_OVERVIEW.md`
- `doc/api/OPENAPI_GOVERNANCE.md`
- `doc/api/README.md`
- `doc/api/openapi.yaml`
- `doc/governance/DOCUMENTATION_VALIDATION.md`
- `doc/governance/DOCUMENT_REGISTER.md`
- `doc/governance/README.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/testing/API_TESTING.md`
- `doc/testing/README.md`
- `doc/testing/REQUIREMENTS_TRACEABILITY.md`
- `doc/testing/TEST_STRATEGY.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/documentation-validation.json`
- `.github/scripts/openapi_compatibility.py`
- `.github/scripts/test_validate_docs.py`
- `.github/scripts/test_validate_openapi_snapshot.py`
- `.github/scripts/validate_docs.py`
- `.github/scripts/validate_openapi_snapshot.py`
- `.github/workflows/ci.yml`
- `.github/workflows/docs.yml`
- `doc/README.md`
- `doc/api/API_OVERVIEW.md`
- `doc/api/OPENAPI_GOVERNANCE.md`
- `doc/api/README.md`
- `doc/api/openapi.yaml`
- `doc/governance/DOCUMENTATION_VALIDATION.md`
- `doc/governance/DOCUMENT_REGISTER.md`
- `doc/governance/README.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/testing/API_TESTING.md`
- `doc/testing/README.md`
- `doc/testing/REQUIREMENTS_TRACEABILITY.md`
- `doc/testing/TEST_STRATEGY.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## HPR-P2-012 canonical-validation preflight — 2026-10-09

### Selection and verified baseline

The owner's Next after HPR-P2-011 selects this preflight only. Current main is
unchanged at `6cc9974b54e9d5c82334a62214a76f89519e7aef`, tree
`e2d1696dc2a36da65afbd332964505ad0b1813da`. Documentation Validation #122
(run 37898624297) PASSED. Production CI #604 (37841205677) PASSED at executable
baseline `617c2eec812e3a5734957ee9fa0360f6f5613032`, tree
`e450c699544f99e96f2b447fc04215f7f4c5f344`. Those existing results were checked;
no fresh Maven/runtime or physical campaign was performed for this preflight.

HPR-P2-012 stays PENDING. A subsequent Next after this published proposal accepts
its full twelve-part design and exhaustive 21-path implementation scope, exact commit
`ci(docs): validate canonical documentation`. HPR-P2-013 stays PENDING, P3 DEFERRED
and P2 OPEN; this preflight creates no validator, workflow change or snapshot refresh.

### Current validation and concrete drift recovered

- `.github/workflows/docs.yml` currently checks doc/ Markdown UTF-8/nonempty/conflict
  markers only. It triggers on doc/** and Markdown, not validation-script/manifest
  changes. Production CI ignores doc/**, docs/** and Markdown but runs on workflow/
  script changes. Its full checkout permits Git ancestry/provenance checks.
- A read-only audit of all 94 canonical Markdown files resolved 3,194 current inline
  relative Markdown links/anchors with zero missing targets/anchors. That local audit
  is not yet a maintained CI checker, semantic approval or external-link availability
  evidence. Ordinary CURRENT/HISTORICAL statuses coexist with bold formatting,
  eleven preserved P1 operating/status phrases and roadmap ACTIVE control metadata.
  Historical P1 descriptions must not be rewritten to satisfy a new parser.
- The canonical snapshot is sorted compact JSON plus newline, valid as the existing
  YAML 1.2 JSON subset. It records generator
  `6822f3ce79b305e1c22f48ff20ef0b0bd2c7f135`, 240 paths, 259 operations and
  227 schemas. Its file SHA-256 is
  `97f601447dc5291afe9bc1775c8ea30b04eb1d2e412e8a3a4e04ce28f03b50d8`.
  This checksum is a file digest, not the ZIP artifact digest or Git blob SHA.
- Retrieved exact successful CI #604 OpenAPI artifact ID 11577329466,
  name hidra-api-openapi-617c2eec812e3a5734957ee9fa0360f6f5613032. The downloaded
  ZIP SHA-256 was independently matched to GitHub metadata:
  `2ff35c384c54b2d49e50aa2a9e646a67c62427e8afcc8db92e17e5368a91b334`.
  ZIP contains exactly hidra-api.json; source-SHA provenance equals the executable
  baseline. Initial direct-download HTTP 403 was resolved with an ordinary HTTP
  client User-Agent; artifact retrieval/digest validation ultimately succeeded.
- The retained generated JSON SHA-256 is
  `1ee03ad711b79c7ef42767a6aa3c685f4857800f09f03002deeac49e4e2d23d4`.
  Deterministic compact conversion of that exact object has SHA-256
  `ba9511455ebabb51c704ef510d08f4fdc766f29d8def0a34ef7a8f1acf86d9fd`.
  It has 244 paths, 263 operations and 231 schemas. Ignoring only source-SHA
  provenance, it differs from the canonical snapshot in paths/components. Four paths
  are added: OIDC evaluate and Risk assessment approve/score/submit; no paths removed
  or existing path objects changed. Four schemas are added and one existing schema
  differs. These are generated evidence differences, not hand-authored API changes.
- Actual command `python3 .github/scripts/openapi_compatibility.py
  doc/api/openapi.yaml /workspace/scratch/4115643ef669/hidra-openapi-ci604.json`
  PASSED: no supported breaking changes detected. That bounded compatibility result
  does not imply equality or complete OpenAPI/JSON Schema compatibility coverage.
  Current API governance explicitly treats executable/snapshot divergence as a
  defect requiring generated reconciliation, not silently accepted historical drift.

### Accepted-on-Next proposed design — twelve parts

1. Introduce standard-library Python canonical documentation and OpenAPI validators,
   each with meaningful positive/negative unittest fixtures. Add one reviewed JSON
   validation manifest and a canonical governance validation guide. These files
   implement bounded structural/governance checks, not business approval, physical
   survivability, arbitrary semantic coverage or a new legal/operational standard.
2. Preserve existing UTF-8/nonempty/conflict checks across all canonical Markdown.
   Resolve inline/reference-style local Markdown links and fragments, including
   duplicate GitHub-style heading anchors, formatting, percent encoding and explicit
   IDs where present. Ignore fenced examples and external/mailto links without
   networking; reject missing/case-mismatched targets and repository escapes.
   Code examples/artifact paths are not indiscriminately treated as hyperlinks.
3. Validate declared document statuses/metadata via the containing-index inheritance
   allowed by DOCUMENTATION_STANDARD.md. Handle bold status formatting; preserve
   HISTORICAL evidence and explicitly admit exact eleven legacy P1 status phrases
   with path-specific rationale, plus the roadmap ACTIVE control format. Unknown
   statuses must fail; never globally whitelist arbitrary text, scan historical
   appended execution paragraphs as current status or rewrite P1 evidence.
4. The reviewed manifest declares the canonical document inventory, required domain/
   index/register entry points, 24 implemented module roots/documents and their
   navigation obligations. Compare actual files/source module roots/index targets,
   reject missing/duplicate/unregistered current module documents and inconsistent
   CURRENT/register/index claims. Treat security/operations directories without a
   README according to their actual register/navigation, not fabricated indexes.
   Inventory updates require deliberate manifest/document review, not automatic
   acceptance of new files or broad permanent exemptions.
5. Check the primary roadmap P2 registry for unique codes, seven-column shape, allowed
   task statuses and coherent selected-next/closure claims, together with canonical
   register/index readiness. Do not freeze all historical prose or clone execution
   statuses into another register. Preserve unrelated HMR/HPR rows; this task completes
   only HPR-P2-012 and leaves HPR-P2-013 as the next preflight. Closure consistency
   checks must permit a later deliberately admitted HPR-P2-013 completion.
6. Recover the exact verified CI #604 generated object and refresh doc/api/openapi.yaml
   solely by sorted compact JSON conversion plus newline. Record generator/run/
   artifact/digest provenance and update API index/overview counts to 244/263/231.
   Preserve old HPR-P2-005 provenance as historical generation evidence. Do not
   hand-edit endpoint/schema/security content, label this docs/CI commit as the
   generator, or silently retain the known 240/259/227 snapshot mismatch.
7. OpenAPI validation rejects malformed/duplicate-key/nonfinite JSON, invalid source
   SHA/provenance, unsupported snapshot structure and unresolved local references.
   Enforce exact deterministic snapshot serialization, expected generated-source
   identity, reviewed manifest file digest/counts and representative bearer/public/
   protected/OIDC security assertions. Clearly limit this to the repository's JSON
   snapshot subset and supported structural checks, not full OpenAPI certification.
8. In production CI, compare the freshly generated object with the committed canonical
   object after removing ONLY x-hidra-ci-source-sha for semantic equality; validate
   each actual source SHA separately against its expected generation context. Preserve
   every other field/list/security/description. Reordered object keys are irrelevant;
   arrays and all semantic values remain significant. Genuine difference fails closed
   and requires an artifact-derived, reviewed snapshot/provenance refresh, never an
   ignore list or compatibility-as-equality shortcut. Retain existing compatibility
   base resolver, its tests, supported compatibility check and artifact upload.
9. Wire validators and their tests into documentation CI and production CI. Broaden
   documentation triggers to the exact validation scripts/tests/manifest and workflow
   paths; retain normal doc/Markdown triggers, read-only permissions and concurrency.
   Documentation CI stays offline/no Maven for structural/snapshot checks. Production
   CI additionally verifies fresh runtime snapshot equality after generation, retains
   Java 21/PostgreSQL/full clean verify and the existing HA/release/backup/observability/
   maintenance evidence gates. No release.yml or deployment changes are admitted.
10. Document current control coverage, commands, manifest maintenance, historical
    status handling and generated-only snapshot refresh in DOCUMENTATION_VALIDATION.md.
    Update root/governance/API navigation and only the four admitted testing documents'
    affected CI/snapshot claims. Preserve their HPR-P2-011 test/traceability identities
    and prior execution limits; change governance applicability explicitly rather
    than presenting refreshed CI as new per-class/physical evidence.
11. Run both standard-library test suites and actual full-repository validators,
    including negative cases for paths/anchors/case/escapes/status/index/module/registry
    drift and OpenAPI source/refs/security/serialization/real semantic differences.
    Validate old-to-refreshed supported compatibility and exact object/digest fidelity.
    Assess current Java/Maven/dependency/Docker prerequisites and attempt the admitted
    clean verify; record real results/skips or exact environmental limitations, not
    earlier assumed DNS/dependency failures. Do not turn a pre-compilation block into
    a test failure or claim per-class no skips without actual report evidence.
12. Record acceptance, exact checks and only HPR-P2-012 completion in canonical memory.
    Preserve legacy docs/**, all runtime Java/tests/POM/configuration, migrations and
    other canonical evidence. Publish one exact-message commit to main once using a
    fresh expected-head lease; verify immutable parent/tree/blobs and all other files
    unchanged. This implementation changes workflow/scripts, so BOTH documentation
    and production CI must start. Confirm startup and STOP, never wait for completion
    or execute HPR-P2-013/P3 automatically. A failure requires responsible-scope repair.

### Exhaustive future implementation write scope — 21 paths

Create only:

- `.github/scripts/validate_docs.py`
- `.github/scripts/test_validate_docs.py`
- `.github/scripts/validate_openapi_snapshot.py`
- `.github/scripts/test_validate_openapi_snapshot.py`
- `.github/documentation-validation.json`
- `doc/governance/DOCUMENTATION_VALIDATION.md`

Update only:

- `.github/workflows/docs.yml`
- `.github/workflows/ci.yml`
- `doc/api/openapi.yaml`
- `doc/api/README.md`
- `doc/api/API_OVERVIEW.md`
- `doc/api/OPENAPI_GOVERNANCE.md`
- `doc/README.md`
- `doc/governance/README.md`
- `doc/governance/DOCUMENT_REGISTER.md`
- `doc/testing/README.md`
- `doc/testing/TEST_STRATEGY.md`
- `doc/testing/API_TESTING.md`
- `doc/testing/REQUIREMENTS_TRACEABILITY.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`

No production Java/test/resource/POM/migration, release workflow, dependency, source
import, tag/release or physical campaign. Existing compatibility/resolver scripts
are preserved. Only the known generated snapshot and admitted metadata/CI statements
are refreshed; no mass rewrite of historical canonical/legacy documentation.

### Admitted validation and publication

Before implementation, verify fresh main, exact-head documentation CI and successful
executable baseline/artifact provenance. Reacquire the approved artifact if needed;
check ZIP/member/source/digests and no extraction path escape before using its object.
Run `python3 .github/scripts/test_validate_docs.py` and
`python3 .github/scripts/test_validate_openapi_snapshot.py`, then actual full canonical
and OpenAPI validators with their documented CLI. Run supported old/new compatibility
and compare refreshed bytes/digest to the approved generated object. Attempt
`./mvnw -B -q clean verify` with accurate result/environment/report limits. Workflow
wiring/trigger/permission checks must prove both structural and fresh-runtime gates
are invoked; do not weaken existing production verification or disable failures.

Canonical count is currently 94 Markdown documents; the new guide makes 95. Verify
all relative links/anchors, document/index/status inheritance and reviewed legacy
status exceptions, 24 module roots, 57 HMR and 123 subject identities preserved,
primary P2 registry shape/next gate, exhaustive 21-path scope, version
0.6.0-SNAPSHOT, git diff --check and every other tracked blob unchanged. New tests
must exercise actual validation failures, not merely mirror implementation text.
No execution success is fabricated when prerequisites fail. Implementation publication
uses `ci(docs): validate canonical documentation`; confirm both CI workflows start
then STOP. A later Next selects HPR-P2-013 preflight only after both exact-head gates
are checked; its closure is not executed by this task.

### This preflight's supporting execution and actual checks

Registered supporting message: `ci(docs): record canonical validation preflight`.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and
 doc/model-remediation/RECONCILIATION.md. No future implementation file is changed
or created now. Validate identical envelopes, 21 unique future paths (six new/fifteen
existing), all 94 canonical Markdown files, preserved HMR/HPR rows and P2 row shape,
exact two-file scope and git diff --check. Read-only 3,194 inline relative links and
artifact retrieval/digest/object comparison plus supported compatibility checks above
actually ran; snapshot refresh, validators and Maven/runtime execution did not.
Publish one supporting commit directly to main using expected head
`6cc9974b54e9d5c82334a62214a76f89519e7aef`; verify immutable tree/blobs and every
other file unchanged, confirm Documentation Validation starts and STOP. Production
CI ignores this preflight's two-document scope. HPR-P2-012 remains PENDING.

### Actual supporting validation result

`python3 /workspace/scratch/4115643ef669/p2-012-preflight-validate.py` PASSED:
identical envelopes; exactly two modified memories; 21 unique future paths with six
new/fifteen existing; all 94 canonical Markdown files UTF-8/nonempty/conflict-free;
all HMR/HPR rows unchanged and primary P2 row shape preserved. Project version remains
0.6.0-SNAPSHOT. `git diff --check` PASSED. No proposed validator/test/manifest/guide,
workflow or snapshot was created/changed. Exact publication checks additionally
require all other tracked blobs unchanged. Current source/artifact comparison,
3,194-link read-only audit and supported old/generated compatibility results are
recorded above; no Maven/runtime or physical execution is claimed.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

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

Canonical prerequisites: HAPI-481. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `e4dba168c9e612a5fd49d50b155fa3b2d8d64e40` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/api/OPENAPI_GOVERNANCE.md:48](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/api/OPENAPI_GOVERNANCE.md#L48)
- [doc/api/README.md:28](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/api/README.md#L28)
- [doc/governance/DOCUMENT_REGISTER.md:102](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/governance/DOCUMENT_REGISTER.md#L102)
- [doc/model-remediation/RECONCILIATION.md:6195](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L6195)
- [doc/model-remediation/RECONCILIATION.md:6420](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L6420)
- [doc/model-remediation/RECONCILIATION.md:6431](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L6431)
- [doc/model-remediation/RECONCILIATION.md:13810](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13810)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6064](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6064)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:343](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L343)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:8310](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L8310)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:8535](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L8535)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:8546](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L8546)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15550](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15550)
- [doc/testing/API_TESTING.md:63](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/API_TESTING.md#L63)
- [doc/testing/README.md:49](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/README.md#L49)
- [doc/testing/TEST_STRATEGY.md:80](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/TEST_STRATEGY.md#L80)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:343 | HPR-P2-012 | COMPLETED — maintained canonical metadata/link/index/module/P2 and deterministic OpenAPI validators/tests wired into both CI workflows; snapshot refreshed from verified CI #604 artifact; strict fresh-runtime equality added; local Maven blocked before compilation by Boot-parent/Central DNS; exact-head CI follows publication | Documentation CI | Infra | Add documentation validation for canonical links/status/index drift and deterministic OpenAPI contract checks. | ci(docs): validate canonical documentation | HPR-P2-001..011
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15550 | HPR-P2-012 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Documentation CI; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6064 | HPR-P2-012 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13810 | HPR-P2-012 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Documentation CI; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<!-- task-register:payload:end -->
