# HPS-007 — Manufacturer Engineering Data: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-007 — Manufacturer Engineering Data

<a id="hapi-533"></a>
## HAPI-533 — define manufacturer characteristic provisioning

Phase: HPS-007 — Manufacturer Engineering Data

Legacy IDs: HPR-P25-021

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

define manufacturer characteristic provisioning

### 2. Existing Implementation

Party manufacturer references and Simulation bounded immutable equipment parameter revision storage exist internally. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Independent cases A–G; mixed parent not closed

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. Current immutable parameter owner remains Simulation until an explicitly authorized ownership change; no interpolation or solver expansion selected. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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
#### HPR-P25-021 — Manufacturer evidence as reusable versioned engineering data

Objective/justification: expose controlled compatible characteristic provisioning while
keeping current S01–S06 stores and immutable bytes. Evidence: S01–S06/S11, A01/A02,
X04–X06/X15. Owners/contracts: Simulation characteristic store; Assets/Party manufacturer
model identity; Topology installed association; Custody exact-fluid method; Workflow exact
approval. Functional/data: independent characteristic revisions, maps/axes/units/reference
states, definitions/interpolation validity, power/driver/speed/temperature/pressure/product
limits and technical-document evidence; original manufacturer, field-characterized,
estimate, synthetic and calibrated origins remain explicit. API: compatible import,
validation diagnostics, save/read/revision and controlled qualification; no arbitrary
formula payload. Persistence: reuse immutable append/query and independently stored
characteristics; add source metadata only through a settled forward version envelope.
Acceptance: Case C compatible second-manufacturer map without solver edits, no installed
asset change; cases B/I history, wrong-fluid/method/reference refusal, malformed/flat/
nonmonotonic map handling per selected law, out-of-map refusal, forged approval and
partial-insert rollback. Numerical obligations: frozen independent interpolation/endpoint/
derivative and limit tests for each new supported family; manufacturer data is not
industrial machine-law validation. D/H/N; no actual compressor certificates requested as
a mandatory development input; no real-source promotion of synthetic regulator data.

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

- `62613734923f2d909ff86911faadc44f1d470ebb` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:12861](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12861)
- [doc/model-remediation/RECONCILIATION.md:12926](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12926)
- [doc/model-remediation/RECONCILIATION.md:13673](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13673)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6112](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6112)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14601](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14601)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14666](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14666)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15413](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15413)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: DESIGN COMPLETE.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14601 | HPR-P25-021 | Manufacturer characteristic provisioning preflight / Simulation with Assets/Party/Topology references | 020; existing 008C source disposition | docs(simulation): define manufacturer characteristic provisioning | PLANNED — NOT SELECTED
doc/model-remediation/RECONCILIATION.md:12861 | HPR-P25-021 | Manufacturer characteristic provisioning preflight / Simulation with Assets/Party/Topology references | 020; existing 008C source disposition | docs(simulation): define manufacturer characteristic provisioning | PLANNED — NOT SELECTED
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15413 | HPR-P25-021 | Registered in prior amendment; PLANNED — NOT SELECTED | FOUNDATION_SCOPE_REFINEMENT | Cross-module engineering governance | Independent successor/design 021 after 020; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 021 after 020 separately selected scope separation
doc/roadmap/ULTIMATE_ROADMAP.md:6112 | HPR-P25-021 | DESIGN COMPLETE | Future runtime assigned 050–058; design is not executable completion
doc/model-remediation/RECONCILIATION.md:13673 | HPR-P25-021 | Registered in prior amendment; PLANNED — NOT SELECTED | FOUNDATION_SCOPE_REFINEMENT | Cross-module engineering governance | Independent successor/design 021 after 020; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 021 after 020 separately selected scope separation
~~~~~~~~

<a id="hapi-601"></a>
## HAPI-601 — Supplied manufacturer maps, tables and limits

Phase: HPS-007 — Manufacturer Engineering Data

Legacy IDs: HPR-P25-054C

Priority: P1

Implementation Status: NOT_IMPLEMENTED

Planning Status: BLOCKED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-599

Cross-Repository Dependencies: HWEB-F04, HWEB-F05 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Supplied manufacturer maps/tables/limits/axes/units/reference conditions and source evidence.

### 2. Existing Implementation

Party manufacturer references and Simulation bounded immutable equipment parameter revision storage exist internally. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRecommendationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRunSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver governed supplied-fact authoring, approval/history and exact installed-unit binding; explicitly report unsupported families. The exact bounded remaining delivery is: Supplied manufacturer maps/tables/limits/axes/units/reference conditions and source evidence..

### 4. Functional Requirements

Immutable source revisions retain exact supplied facts; unsupported/missing curve/axis/unit/evidence stays incomplete, no interpolation engine or invented vendor data.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. Current immutable parameter owner remains Simulation until an explicitly authorized ownership change; no interpolation or solver expansion selected. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/ManufacturerCharacteristicRevision.java` — pure Java owned aggregate/value and invariants for supplied maps/tables/limits/axes/units/reference conditions, approved immutable source facts without interpolation.
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/ManufacturerCharacteristicRevisionUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/ManufacturerCharacteristicRevisionRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/ManufacturerCharacteristicRevisionApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/ManufacturerCharacteristicRevisionController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JdbcManufacturerCharacteristicRevisionRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRecommendationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRunSemanticRemediationTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/simulation/manufacturer-characteristics` for supplied maps/tables/limits/axes/units/reference conditions, approved immutable source facts without interpolation. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRecommendationSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRunSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Immutable source revisions retain exact supplied facts; unsupported/missing curve/axis/unit/evidence stays incomplete, no interpolation engine or invented vendor data.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-599. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23396](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23396)
- [doc/model-remediation/RECONCILIATION.md:23706](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23706)
- [doc/model-remediation/RECONCILIATION.md:23746](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23746)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1014](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1014)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1054](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1054)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1334](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1334)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1014 | HPR-P25-054C | Approve supported supplied characteristic shapes and certification/approval terminology; numerical interpolation and real calibration remain deferred. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/roadmap/ULTIMATE_ROADMAP.md:1054 | HPR-P25-054C | REQUIRES_OWNER_DECISION | Supplied manufacturer maps, tables and limits
doc/roadmap/ULTIMATE_ROADMAP.md:1334 | HPR-P25-054C | Supplied manufacturer maps/tables/limits/axes/units/reference conditions and source evidence. | Immutable source revisions retain exact supplied facts; unsupported/missing curve/axis/unit/evidence stays incomplete, no interpolation engine or invented vendor data.
doc/model-remediation/RECONCILIATION.md:23396 | HPR-P25-054C | Supplied manufacturer maps/tables/limits/axes/units/reference conditions and source evidence. | Immutable source revisions retain exact supplied facts; unsupported/missing curve/axis/unit/evidence stays incomplete, no interpolation engine or invented vendor data.
doc/model-remediation/RECONCILIATION.md:23706 | HPR-P25-054C | Approve supported supplied characteristic shapes and certification/approval terminology; numerical interpolation and real calibration remain deferred. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/model-remediation/RECONCILIATION.md:23746 | HPR-P25-054C | REQUIRES_OWNER_DECISION | Supplied manufacturer maps, tables and limits
~~~~~~~~


### HAPI-601 — owner-approved manufacturer V1 policy and stage registration (2026-10-30)

**Authorized decision and exact current stage.** Owner explicitly APPROVED the bounded V1 policy after [Docs #763](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41088444094) SUCCESS on `8790386c95dc1ad599a0a48b8bc84ba506b0618c`; independent preceding backend [Full #1014](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41086553347) SUCCESS belongs to source `4fbe9790d66bf50f6b4de633179894bd29921dfe` and does not validate this unimplemented manufacturer capability. This is one **documentation/policy registration only** with exact current commit message `docs(simulation): register approved manufacturer characteristic policy`, two-file write allowlist `doc/roadmap/TASKS_HPS-007.md` (append-only) and `doc/roadmap/ULTIMATE_TASKS.md` (compact pointer). It neither modifies the frozen phase/roadmap status cells nor starts implementation. HAPI-601 remains **NOT_IMPLEMENTED/BLOCKED** pending separately required technical admission; HAPI-602 remains blocked.

#### Approved business policy: V1 source evidence, not numerical qualification

- **Supported manufacturer-supplied V1 shapes:** (1) compressor characteristic maps/speed lines with measured/supplied flow, rotational speed, specific head and efficiency axes; (2) valve opening/pressure-drop/flow characteristic curves; (3) supplier parameter tables, including ordered axes with explicit quantity types and units; (4) governed operating minima/maxima and reference conditions. Treat each table's column types, axes, units, dimensions and reference method as an explicitly versioned *source fact*, not an arbitrary untyped JSON substitute for a supported method. Relevant existing fixed units include mass flow kg/s, speed rpm, specific head J/kg, efficiency/opening fractions, pressure absolute Pa versus differential Pa, temperature K. V1 must reject unknown/inconsistent units, axis order, impossible ranges, absent mandatory reference conditions and unsupported kinds rather than guess conversions, samples, validity or interpolation.
- **Supplier-declared:** attributable supplied fact with immutable document/data revision, issuer/manufacturer reference, evidence reference and exact digest. Supplier assertion is not certification or workflow approval.
- **Manufacturer-certified:** separate label available **only** when actual manufacturer certificate evidence exists for that exact revision, stated equipment family/model and validity scope, with certifier identity, certificate/reference, provenance, effective/expiry and document digest; no auto-upgrade from a supplier claim or a software validation. Where certificate metadata/content are absent, status remains supplier-declared or unknown.
- **Workflow-qualified for engineering use:** independent, current actual approved Workflow decision against the exact immutable source digest and scope, using the existing Simulation approval-evidence boundary where applicable. Workflow qualification never fabricates a manufacturer certificate, installed-unit compatibility, field calibration, industrial acceptance, or approval for simulator/OT control. Distinguish `approval`, `qualification` and `certification` as separate facts.
- **Explicit unsupported/incomplete:** report missing axis, unit, source document, approved method, certificate or family as incomplete/unsupported, with non-disclosing error semantics under authorization. An existing **synthetic regulator** characteristic format in Simulation remains intact but is **not** an approved manufacturer V1 family by implication. No numerical interpolation, solver, prediction, own-engine completion, external simulator interchangeability, installation choice, OT write, live SCADA, commissioning, or operational recommendation is authorized. Synthetic facts may test contracts but cannot be promoted to manufacturer-certified field facts.

#### Source/ownership review and resolved versus unresolved gates

At the exact selection tree, Simulation owns `SimulationEquipmentParameterRevision` with independent nested compressor/valve curves and governed limits, plus `SimulationRegulatorCharacteristicRevision` (synthetic). The existing `SimulationEquipmentParameterRevisionRepositoryPort`, `JdbcSimulationEquipmentParameterRevisionRepositoryAdapter` and `SimulationEquipmentParameterRevisionCodec` already store immutable digested parameter/characteristic revisions. `SimulationEquipmentParameterQualificationService` and `WorkflowEquipmentParameterApprovalEvidenceAdapter` resolve actual workflow decisions on exact source digests. These are existing **network/fluid-bound simulation inputs**; they do not establish independent manufacturer-supplied source authoring, declared versus certified provenance, protected source history, or unit binding. Preserve their published binary formats and old qualification semantics. Simulation remains the owner of new manufacturer source revisions; Party alone owns manufacturer identity; Topology alone owns installed-unit/facility identity. Use approved public query contracts, not cross-module persistence imports.

The policy decision specified by legacy HPR-P25-054C (`REQUIRES_OWNER_DECISION`) is **resolved** for the four V1 shapes and separate certification/qualification vocabulary. The **independent architecture/database technical registration review** remains OPEN for (a) source-vs-simulation-input aggregate separation and legal cross-owner reference APIs, (b) actual Identity grants/resource scopes and native ProtectedTransactionExecutor/SQL guard extension, (c) exact migration/trigger/privilege/optimistic-concurrency sequence and existing immutable store compatibility, (d) authentic Workflow approval/certificate evidence and separate protected read rights, (e) contract DTO/error/upload and exact installed-unit binding ownership. Although HAPI-601's historical gap description mentions binding, HAPI-602 **separately owns versioned installed-unit-to-characteristic links**; do not write a second competing binding owner. The exact shared contract split requires admission before code. The current highest Flyway filename observed is `V20261029_003__topology_station_configuration_protected_operations.sql` (190 migrations on CI #1014); new migration names cannot be treated as reserved permanently without a fresh main check. There is no development-data backfill requirement; industrial numerical data is deferred, not a software-contract excuse.

#### Registered bounded implementation packages — NOT yet admitted for execution

The following package sequence, exact messages and initial path boundaries are recorded as **review-gated technical targets**, not permission to execute code before the open independent technical review is satisfied. A future source-backed technical admission must either confirm these exact allowlists or append a reasoned correction here first; do not silently add source/security/DDL paths. All new paths are PROPOSED/ABSENT at this registration, not falsely existing implementation.

**Package A — pure supplied-source contract and deterministic codec**. Proposed exact message: `feat(simulation): model immutable manufacturer supplied characteristics`. CREATE exactly:
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/ManufacturerCharacteristicRevision.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/ManufacturerCharacteristicRevisionCodec.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/ManufacturerCharacteristicRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/ManufacturerCharacteristicRevisionCodecTest.java`

MODIFY only `.github/ci-test-inventory.json` (actual tests/cases/hashes) and this technical record; DELETE none. Distinct supplier source root, immutable revision and canonical digest, explicit manufacturer model/reference and optional *actual* certificate metadata, typed family/axes/quantities/units/reference conditions, exact source-evidence identifiers and non-fabricated provenance. No installed-unit foreign aggregate, backend permissions, database table, public route, evaluator/interpolator or JPA in domain. Acceptance: negative unsupported family, missing evidence, wrong units/axes/quantities, contradictory certification, duplicate points, oversize/Unicode/format corruption; deterministic byte-identical source roundtrip and independent synthetic supplier A/B revisions. Existing compressor/valve/regulator codecs unchanged.

**Package B — owner-native secure immutable authoring and qualification**. Proposed exact message: `feat(simulation): protect supplied manufacturer characteristic revisions`. Proposed new owner CREATE:
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/ManufacturerCharacteristicRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/ManufacturerCharacteristicRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/ManufacturerCharacteristicRevisionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/ProtectedManufacturerCharacteristicAdmissionCoordinator.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JdbcManufacturerCharacteristicRevisionRepositoryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/ManufacturerCharacteristicRevisionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/qualification/ManufacturerCharacteristicNativeQualificationTest.java`

Also requires **not-yet-admitted** actual Identity public-contract/participant/reader amendments, ProtectedTransactionExecutor compiled route/strict SQL pins, new additive Flyway SQL, database ownership/dictionary inventory and relevant focused identity/platform/architecture tests. Their exact CREATE/MODIFY paths, migration version/DDL and permission codes are **DISCOVERY_REQUIRED** at the independent technical admission; no B implementation may proceed until all are registered in this phase record. A future B contract must prove genuine company/manufacturer scope, time/revocation/DENY, permission-before-disclosure, separate source/certificate/Workflow qualification decisions, one final SQL guard and actual physical commit, unknown commit/replay, immutable revisions and CAS/rollback, competing authors, correct source trust, fresh PostgreSQL schema and all four login provenance families where applicable. No fabricated certificate or generic database bypass.

**Package C — configured HTTP and consumer-ready source evidence**. Proposed exact message: `feat(simulation): expose supplied manufacturer characteristic authoring`. Proposed CREATE:
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/ManufacturerCharacteristicRevisionController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/ManufacturerCharacteristicRevisionRequests.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/ManufacturerCharacteristicRevisionResponses.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/mapper/ManufacturerCharacteristicRevisionRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/ManufacturerCharacteristicRevisionApiExceptionHandler.java`
- `src/test/java/dz/sh/hidra/modules/simulation/api/rest/controller/ManufacturerCharacteristicRevisionControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/qualification/ManufacturerCharacteristicOperatorQualificationTest.java`

MODIFY only after actual generated evidence: `.github/ci-test-inventory.json`, `doc/api/openapi.yaml`, `doc/database/DATA_DICTIONARY.md` (only if actual fresh catalog differs) and this task record. All API route names, DTO types, errors and schema are PROPOSED until B native ports, authorization and generated runtime contracts are admitted; existing proposed `POST /api/v1/simulation/manufacturer-characteristics` is a planning pointer, **not** an existing endpoint. Verify configured filter chain, current authorization, document metadata provenance, certified versus workflow-qualified semantics, backward compatibility, bounded cursor/history, exact replay/conflict, no exposed private existence/count, real SQL guard and no bypass. Preserve existing simulation endpoints.

**Final software acceptance**, only after independently verified A/B/C: proposed exact message `docs(simulation): accept supplied manufacturer characteristic software`, four governance files only after the phase/status/index evidence review. Full/Fast/Docs exact implementation SHA, real JUnit case memberships/no skip, negative/concurrent/source/HTTP/security/physical PostgreSQL and generated API/catalog are mandatory. No frontend HWEB-F04/F05, HAPI-602 link, numerical P3, engineering/SITE, operator sign-off, calibration, release or deployment acceptance follows.

**Next selected action and STOP.** This approval turn registers the policy and review-gated A/B/C scope **only**. Keep HAPI-601 `NOT_IMPLEMENTED/BLOCKED` in ROADMAP/PHASES until a separately selected independent source-backed technical admission resolves the listed open scopes. After guarded main publication, verify Docs startup and STOP; do not execute Package A/B/C in the same turn. Retain all original HAPI-601, HAPI-533 and HAPI-599 records verbatim.


### HAPI-601 — source-backed technical preflight registration (2026-10-30)

**Owner authorization and execution boundary.** The owner approved this **technical preflight and registration within HAPI-601 Packages A/B/C only**; this does not confer an independent architecture/database/security sign-off or authorize new tasks. Exact documentation-stage message: `docs(simulation): register manufacturer characteristic technical preflight`. Stage write allowlist is precisely `doc/roadmap/TASKS_HPS-007.md` (append-only) plus `doc/roadmap/ULTIMATE_TASKS.md` (compact pointer); no deletion, Java, tests, permissions, OpenAPI or DDL. Selection main `291a5e7b30f5d95c769a036f5fcee646503cc9d7` passed [Documentation Validation #764](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41089696782). Earlier unchanged-runtime source `4fbe9790d66bf50f6b4de633179894bd29921dfe` passed [Full CI #1014](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41086553347), which does not prove this unimplemented manufacturer workflow.

#### Exact-source bounded-context review

- **Simulation** owns the immutable supplied equipment *simulation-input* envelopes and formats in `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentParameterRevision.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterRevisionRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JdbcSimulationEquipmentParameterRevisionRepositoryAdapter.java` and `SimulationEquipmentParameterRevisionCodec.java`. Existing compressor speed lines, valve pressure/flow maps and governed limits are **fluid/network-bound**. Existing regulator characteristics are synthetic. A new manufacturer-source revision must **not duplicate this aggregate or change frozen legacy codec bytes/hashes**.
- **Party** owns manufacturer role/profile/qualification evidence. `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyInstalledUnitManufacturerEvidenceContract.java` is specifically Topology-bound, single-physical-transaction evidence, not a permission, general Simulation export or manufacturer-document certificate. `ManufacturerProfileJpaEntity.java` has profile status, not certificate issuer proof. **Topology** `src/main/java/dz/sh/hidra/modules/topology/domain/model/InstalledUnit.java` owns independently identified physical installed units. HAPI-602, not HAPI-601, owns immutable installed-unit-to-characteristic linkage.
- **Identity** provides analogous *different-resource* protected authorization in `src/main/java/dz/sh/hidra/modules/identity/application/contract/topology/TopologyStationConfigurationAuthorizationContract.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedTopologyStationConfigurationAuthorizationParticipant.java`, `ProtectedOperationIdentityParticipant.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java` and `src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java`. Requires **new** manufacturer-specific company/resource effective grants, DENY/revocation/current recheck, trusted identity, original physical transaction and final SQL guard; route permission alone is inadequate.
- **Workflow** `src/main/java/dz/sh/hidra/modules/workflow/application/contract/target/RevisionApprovalEvidenceContract.java` supports final approval for exact target module/digest, definition/version/type/purpose and action provenance. `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/SimulationEquipmentParameterWorkflowTargetLookup.java` currently exports only `EQUIPMENT_PARAMETER_REVISION`. `SimulationEquipmentParameterQualificationService.java` and `WorkflowEquipmentParameterApprovalEvidenceAdapter.java` do not automatically establish a distinct manufacturer source target or verified certificate; **supplier declaration**, actual **manufacturer certification**, and actual **Workflow qualification** remain separate.
- **REST** `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java` does not expose manufacturer authoring; `src/main/java/dz/sh/hidra/platform/permissions/HidraRoutePermissionNaming.java` produces only route permission names. **Database** migration inventory has 190 files through `src/main/resources/db/migration/V20261029_003__topology_station_configuration_protected_operations.sql`. That file is an example of fixed locks/provenance/restricted roles, **not** an authorization export to Simulation. Existing `doc/api/openapi.yaml` and `doc/database/DATA_DICTIONARY.md` have no admitted manufacturer runtime capability by this review.
- **Evidence limitation:** AGENTS, canonical ROADMAP/PHASES, HPS-007 and these exact source paths were inspected. `doc/model-remediation/RECONCILIATION.md` exists at blob `83a05d419de93a9a389dd1c3120324148a13f3d8` but the large-file connector returned zero text; historical pinned row links are **not** claimed as a freshly read reconciliation.

#### Package A — proposed independent technical acceptance boundary

The bounded pure-Java `ManufacturerCharacteristicRevision` aggregate should hold exact source/revision, manufacturer Party/model references (scalar evidence, no cross-owner aggregate), immutable document reference and digest, recorded/effective temporal data, issuer/source attribution, typed V1 family (compressor speed lines, valve curves, supplied parameter tables, governed limits), quantity/axis/units/reference conditions, and optional **genuinely sourced** certificate issuer/scope/expiry/digest. Uncertified evidence remains `SUPPLIER_DECLARED`; synthetic development fixtures are never certified. Reject unsupported family, wrong/unknown units, missing axes or source, duplicate/unordered samples, impossible bounds, contradictory certificate and invalid effective interval. No interpolator, numerical/P3, installed-unit binding or operational decision.

The exact **A CREATE allowlist** is unchanged from the prior registration:
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/ManufacturerCharacteristicRevision.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/ManufacturerCharacteristicRevisionCodec.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/ManufacturerCharacteristicRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/ManufacturerCharacteristicRevisionCodecTest.java`

MODIFY only `.github/ci-test-inventory.json` (after actual tests/cases/hash) and HPS-007 evidence. Exact A message `feat(simulation): model immutable manufacturer supplied characteristics`. Require deterministic versioned canonical serialization, bounded UTF-8/length/counts, exact stable digest and historical reserialization, negative malformed/truncated/trailing payload, source A/B differentiation and existing codec/hash regression. A has **a review-ready design, not reviewer sign-off**. An accountable Simulation owner and independent architecture/database reviewer must accept this precise owner split before A code.

#### Package B — explicit external owner and physical-database admission gaps

The seven previously registered Simulation CREATE filenames and message `feat(simulation): protect supplied manufacturer characteristic revisions` remain *proposed*. These are **new candidate paths only**, not an executable B cross-owner allowlist:
- Party public export `src/main/java/dz/sh/hidra/modules/party/application/contract/simulation/SimulationManufacturerEvidenceContract.java` and adapter `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JdbcSimulationManufacturerEvidenceAdapter.java` (PROPOSED/ABSENT). Requires Party owner's company/manufacturer scope and fresh role/profile proof, not direct Party persistence imports.
- Identity public contract `src/main/java/dz/sh/hidra/modules/identity/application/contract/simulation/SimulationManufacturerCharacteristicAuthorizationContract.java` and native participant `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedSimulationManufacturerCharacteristicAuthorizationParticipant.java` (PROPOSED/ABSENT). Existing `ProtectedOperationIdentityParticipant.java` and `JdbcProtectedAuthorizationEvidenceReader.java` are candidate MODIFY only. Register exact author/read/history/qualify permission codes and resource grants before using them; fail closed on unknown and revoked identity.
- Existing `src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java` is a **candidate MODIFY** for exact compiled caller/SQL fingerprints and final protected commit. Suggested new migration `src/main/resources/db/migration/V20261030_001__simulation_manufacturer_characteristic_sources.sql` is **PROPOSED/NOT RESERVED**; recheck migration ordering first. Independent database reviewer must admit immutable revision/history and physical receipt relations, uniqueness/CAS, deletion and DML guards, evidence references, concurrent lock order, restricted runtime role, final SQL witness and failed/unknown commit replay. No PUBLIC grants, history rewriting, MySQL equivalence or production backfill.
- Workflow proposed adapter `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/SimulationManufacturerCharacteristicWorkflowTargetLookup.java` (PROPOSED/ABSENT); Workflow owner must register exact target type/definition and source-digest approval. Actual certification issuer/document-verification contract was **not identified**: report `UNVERIFIED` / `INCOMPLETE` until legitimate independent evidence exists; never rebrand Party qualification or a synthetic source as certificate.
- Focused possible tests: existing `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` candidate MODIFY; new `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedSimulationManufacturerCharacteristicAuthorizationParticipantTest.java` candidate CREATE; prior registered Simulation PostgreSQL and native qualification tests remain proposed. Real test paths/migration and B subpackage splits must be finalized before B implementation.

**B admission OPEN** pending the accountable Party, Identity and Workflow contract owners and **independent architecture/database/security review** of the actual public APIs, DDL/SQL/rights and safety invariants. Source inspection or owner approval of A/B/C scope does not itself close it.

#### Package C — contract-first HTTP only after B

Preserve the seven previously registered C CREATE paths and message `feat(simulation): expose supplied manufacturer characteristic authoring`. The candidate `POST /api/v1/simulation/manufacturer-characteristics` does **not** currently exist. C technical review needs explicit create/current/revision/history DTOs, distinct supplier/certificate/Workflow projection, immutable source revision, current authorized resource-scoped reads **before existence/count disclosure**, replay/idempotency, bounded history, configured JWT/filter-chain qualification, 401/403/404/409/503 handling, runtime OpenAPI/backward-compatibility and database operator tests. No HAPI-602 installed-unit binding, engine, external adapter, UI, telemetry/SCADA or OT action.

#### Independent review and execution gate

| Technical decision | Status | Responsible sign-off / later proof |
|---|---|---|
| A distinct domain aggregate/codec and exact four-file source allowlist | **REVIEW-READY, NOT SIGNED** | Accountable Simulation owner + independent architecture/database reviewer; execute focused Java/codec tests *during A* |
| B Party/Identity contracts, current grants and native operation authority | **OPEN** | Party and Identity owners plus independent security reviewer; negative authorization/expiration/rollback tests |
| B Flyway/roles/final SQL/immutable concurrency | **OPEN** | Independent database/architecture reviewer; genuine fresh PostgreSQL, race, replay, original physical commit and 503 INDETERMINATE tests |
| B real Workflow target and certificate evidence | **OPEN** | Workflow/data evidence owners; exact approval/certification provenance, no status promotion |
| C protected HTTP/API/errors and generated contract | **OPEN** | API/security reviewers; JWT, non-disclosure and OpenAPI backward compatibility |
| A/B/C software verification | **NOT EXECUTED** | Focused and exact-source Full/Fast/Docs CI, required JUnit membership, genuine schema/JPA/dictionary and all negative cases; no SITE qualification |

**Disposition and STOP:** Owner-authorized technical preflight is documentation-only; **no independent reviewer signature was obtained**. HAPI-601 remains `NOT_IMPLEMENTED / BLOCKED` in ROADMAP and PHASES, HAPI-602 separately blocked. **Next selectable action is independent sign-off or correction of A's source model/codec technical scope**, then A implementation only after admission; B/C remain independently review-gated. No approval for later tasks, release or deployment. Stop after verifying applicable Documentation CI startup on this exact publication.

### HAPI-601 — owner-authorized technical review and final decision procedure (2026-10-30)

**New owner decision; supersedes the HAPI-601 independent-signature procedure only.** On 2026-10-30 the repository owner explicitly answered **Yes** to replacing the separate independent architecture/database technical-signoff requirement for **HAPI-601 Packages A, B and C only** with **(1) documented, source-backed technical analysis prepared by the assistant and (2) explicit final decisions from the repository owner in this conversation**. This is an authorized HAPI-601 review-process exception, not a claim that independent reviewers participated or approved any design. Earlier requirements for independent review in this phase file and historical HPR-P25-054C remain preserved as dated evidence; for **this task alone**, this later owner decision replaces the reviewer-signature *procedure*. It does not remove its underlying technical architecture/database/security/Identity/Party/Workflow acceptance requirements, downgrade testing, or alter the canonical roadmap task ordering/status. It does not automatically approve the proposed A, B, or C contracts.

**Chosen one-stage publication.** Exact commit message: `docs(simulation): register owner technical review authority for HAPI-601`. Exclusive documentation-only CREATE: none; MODIFY: `doc/roadmap/TASKS_HPS-007.md` (append owner decision), `doc/roadmap/ULTIMATE_TASKS.md` (compact pointer); DELETE: none. Baseline main `1a7fb9d22e5d3eb1eb6685b7ad9d69ab103ca69a`; [Documentation Validation #765](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41094186740) **SUCCESS**. No runtime, test, migration, OpenAPI, permission, route, or status change in this decision-registration stage.

**Decision authority, review evidence and truthfulness.** The owner is the **sole final sign-off authority for HAPI-601 technical admission**, not a substitute for technical verification. The assistant must (i) independently of owner approval inspect the latest exact-source SHA and AGENTS/canonical task/implementation/CI; (ii) set out proposed domain, public ports, authorization and transaction boundaries, exact paths/commit message, safety cases, schema/CAS/immutability proof and known limitations for the selected package; (iii) identify unresolved conflicts and ask the owner directly for a concrete decision, rather than opening a GitHub issue or PR; (iv) capture the owner's **separate explicit acceptance of each package's technical contract** in this append-only phase record before its source commit; (v) implement only one authorized package per `Next` and verify the exact-SHA workflow startup before stopping. The assistant's technical analysis is **not independently certified**; tests/CI remain separate proof and the owner cannot waive immutable authorization invariants or misstate CI as passing. The owner's prior A/B/C scope authorization remains operative but must not be falsely labeled a sign-off on a specific technical contract.

**A — immediate question to owner after this registration.** Recommend acceptance of the existing reviewed pure-Java `ManufacturerCharacteristicRevision` aggregate and deterministic `ManufacturerCharacteristicRevisionCodec` **within the four CREATE paths and exact MODIFY paths already listed above**, message `feat(simulation): model immutable manufacturer supplied characteristics`. The signed technical contract must preserve separate supplier-source identity/doc digest and optional genuine certificate metadata, distinct effective/recorded history, supported typed compressor/valve/table/limit families, explicit units/axes/reference conditions, bounded strictly canonical bytes/hash, negative malformed/truncated/unsupported/certificate tests, and existing Simulation codec/hash compatibility. Domain cannot import persistence/Party/Topology/Workflow/engine APIs. A adds **no** database table, protected operation, HTTP route, manufacturer-certificate issuer or installed-unit link. **Status at this publication: TECHNICAL_REVIEW_PREPARED / OWNER_A_CONTRACT_DECISION_PENDING; NOT_IMPLEMENTED/BLOCKED.** The owner must explicitly answer the A technical-contract acceptance question; the present Yes accepts the review process only.

**B — later separate owner decision, NOT admitted here.** Preserve the previously proposed secure authoring/qualification package message `feat(simulation): protect supplied manufacturer characteristic revisions`. Prior proposed Party/Identity public evidence contracts, actual manufacturer-resource grants, native protected participant, exact compiled-SQL final guard, actual fresh PostgreSQL migration/immutable revisions, genuine Workflow target approval, separate real certificate provenance, source/CAS/replay and denial tests must be fully inspected and given an exact owner-approved write allowlist first. Until actual owner-native interfaces, permissions, DDL/role guards, migrations and tests are accepted, **B remains blocked**; no privilege bypass, fake manufacturer certificate, or stale read.

**C — later separate owner decision, NOT admitted here.** Preserve previously proposed REST/DTO/operator contract package message `feat(simulation): expose supplied manufacturer characteristic authoring`. Request owner acceptance only after B's necessary proven contracts; verify independent current read authorization before protected existence/history/count disclosure, stable errors, pagination/replay, runtime OpenAPI, actual auth/JWT and PostgreSQL tests. HAPI-602's versioned installed-unit binding remains a distinct future task; no numerical/P3, HidraWEB, deployment, live OT or SITE acceptance.

**Issue and repository interaction restriction.** At owner's direct instruction, GitHub issue #136 was closed as `not_planned`. **Do not open new GitHub issues or pull requests.** Ask the repository owner questions directly in this conversation. Publishing an authorized bounded implementation to existing `main` remains subject to expected-head guard, exact registered message, source verification and CI startup/STOP.

**Acceptance remains evidence-based.** This procedure change is not software acceptance. Package A/B/C implementations must separately produce actual tests, negative/security/temporal/transaction cases, exact fresh PostgreSQL evidence where applicable, strict inventory membership, applicable Full/Fast/Docs CI at the implementation SHA and preserved old behavior. If a required technical safeguard cannot be met, report the blocker to the owner rather than silently omit it. No modifications to ROADMAP/PHASES status cells from a docs-only decision, and no extension beyond A/B/C.

### HAPI-601 — owner acceptance of Package A technical contract (2026-10-30)

**Recorded direct owner decision.** On 2026-10-30, following the owner-authorized HAPI-601 review-process exception and a direct question presenting the four-file A contract, the repository owner answered **Yes**. This is the **owner's explicit final technical-contract acceptance for HAPI-601 Package A only**. No independent reviewer is claimed. This decision is not acceptance of Package A implementation, testing, CI, Package B, Package C, simulator capability, engineering/SITE or deployment; it does not enlarge the earlier A/B/C-only scope.

**Selected registration stage.** Parent main `05a7fb2fd4f3eaad84f3b054334d04b82f078088`; its [Documentation Validation #766](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41097757720) **SUCCESS**. Register the owner's decision only, with exact message `docs(simulation): admit manufacturer characteristic package A contract`; MODIFY `doc/roadmap/TASKS_HPS-007.md` and `doc/roadmap/ULTIMATE_TASKS.md`; CREATE/DELETE none. No Java source, migration, security contract, inventory, OpenAPI or status cell is modified at this approval-registration stage.

**Accepted A engineering boundary and next executable stage.** Simulation owns a **separate immutable manufacturer-supplied source** aggregate (distinct from existing fluid/network-bound equipment simulation inputs) with source/revision/document/provenance, effective/recorded history, typed compressor speed lines, valve opening/drop/flow curves, supplier typed tables and operating limits. Preserve explicit axes, quantity units, reference conditions, source digest and optional **genuine documentary** certificate metadata; certificate metadata is **not automatic verified manufacturer certification**, nor Workflow qualification. No inferred manufacturer certificate from Party status or synthetic tests. Require pure Java domain, deterministic independent V1 binary codec, bounded strict canonical serialization/digests, defensive immutability, negative missing/malformed/duplicate/invalid families/units/certificate tests and preservation of existing codecs/hashes. No interpolation, network/fluid/installed-unit binding, persistence, protected authoring, HTTP or live integration in A.

**Authorized next Package A implementation CREATE (exactly four):**
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/ManufacturerCharacteristicRevision.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/ManufacturerCharacteristicRevisionCodec.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/ManufacturerCharacteristicRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/ManufacturerCharacteristicRevisionCodecTest.java`

Package A MODIFY only `.github/ci-test-inventory.json` for actual cases/hashes and `doc/roadmap/TASKS_HPS-007.md` for append-only exact implementation evidence; DELETE none. The **already registered exact A source commit message** is `feat(simulation): model immutable manufacturer supplied characteristics`. Verify fresh `main` and current CI, Java 21 focused tests where tooling permits, inventory consistency and exact-SHA source CI startup, and STOP. Tests and successful CI must be verified, never presumed.

**Disposition.** HAPI-601's **A technical-contract decision is ACCEPTED by owner**. A **source implementation is still NOT_IMPLEMENTED**; HAPI-601 overall remains `NOT_IMPLEMENTED / BLOCKED` until its own separately verified A/B/C software acceptance. Package B's native Party/Identity/Workflow/PostgreSQL contracts and Package C's protected API contract need their **own direct owner decisions**, with no false bypass of security/database/testing gates. No GitHub issues or PRs. Next `Next` selects **Package A implementation only** after successful current documentation CI.

### HAPI-601 — owner-approved Package A implementation evidence (2026-10-30)

**Selected stage.** Owner explicitly approved the four-file Package A technical contract, documented at parent `a20f887aed0129879627b47bacaef5737a0a8c79`; its [Docs #767](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41099047083) **SUCCESS**. This is only **HAPI-601 Package A**; exact implementation message `feat(simulation): model immutable manufacturer supplied characteristics`. No GitHub issues or PRs. No HAPI-602 or other stage selected.

**Exact CREATE (four files):**
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/ManufacturerCharacteristicRevision.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/ManufacturerCharacteristicRevisionCodec.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/ManufacturerCharacteristicRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/ManufacturerCharacteristicRevisionCodecTest.java`

**Exact MODIFY:** `.github/ci-test-inventory.json` (two new source-hash-linked test classes, seven plus six JUnit test cases; preserve 523 previous entries) and `doc/roadmap/TASKS_HPS-007.md` (append this truthful source evidence). DELETE none; no migrations, Identity, Party, Workflow, permissions, REST, engine, UI or frozen Simulation codec modifications.

**Implementation details.** Pure-Java immutable `ManufacturerCharacteristicRevision` distinct from existing network/fluid-bound Simulation equipment-input aggregates. It carries exact immutable source/revision/document reference/SHA-256, manufacturer Party/model *references*, recorded/effective times, supplier-declared or synthetic origin, explicit reference pressure/temperature/method, typed V1 compressor speed-line maps, valve drop/opening/flow, parameter tables with typed ordered axes and outputs and operating limits. Explicit SI quantities/units; strict positive/range/ordering/uniqueness/scale/precision/text/collection checks and defensive list copies. Optional certificate reference evidence is bound to exact source/revision/doc/manufacturer/model/family/validity; it does **not** verify authenticity, certify source, or imply Workflow approval. It is forbidden on synthetic input. No interpolation, solver or installed-unit binding.

**Codec and provenance.** Separate bounded canonical `HIDRA_MANUFACTURER_CHARACTERISTIC_SOURCE_V1` binary codec; maximum 1 MiB, bounded strict UTF-8 and declared counts, tag/kind/scale/timestamps, no truncation/trailing bytes and byte-equal decode/re-encode; SHA-256 hashes exact canonical bytes. Existing equipment/regulator codecs are untouched. Java 21 local compilation passed and 13/13 new @Test cases passed in a lightweight isolated JUnit-compatible stub/reflection harness (zero failures), covering four families, provenance differences, strict malformed-byte rejection and source invariants. **Not Maven Surefire, real repository ArchUnit or exact-SHA CI**: full proof remains pending.

**Manifest source digests and lane.** `ManufacturerCharacteristicRevisionTest.java` = `bbb6cf025742afa2f55ddccb5fb9d91cbc57c874cbf924f29db1b15e9208121f` (7 cases); `ManufacturerCharacteristicRevisionCodecTest.java` = `07a36567348ff4fec78c92a4f199c80ca4bc84f5045428a770afdfc26dfd47fe` (6 cases); both are pure-Java `unit-architecture` with `fast=true`, `baseline_seconds=0` (explicitly **unmeasured**, not a speed claim).

**Truthful disposition and STOP.** A source is implemented **pending exact-SHA CI acceptance**, not yet verified or engineering accepted. HAPI-601 remains `NOT_IMPLEMENTED / BLOCKED` overall in ROADMAP/PHASES because B secure persistence/qualification and C protected HTTP are not implemented. First evaluate Full/Fast/Docs actual exact-commit status, repair any failed A-scoped CI before selecting another stage, then separately obtain the owner's B technical-contract decision. Do not automatically advance or claim release, deployment, SITE or operational acceptance.

### HAPI-601 — CI-first OpenAPI baseline lookup repair after Package A (2026-10-30)

**Selected owner-directed `Next` after CI failure.** This is a **CI-first repair**, not HAPI-601 Package B admission or implementation. Baseline `9c71b0c013b6fb8f29ce7d444a0f93d9b22fcecd` has Package A source and [Documentation CI #768](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41100661878) **SUCCESS**. [Full CI #1015](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41100661521) executed Java 21 unit/architecture and 14 PostgreSQL shards successfully, but `API contract and dictionary` failed on `OpenAPI base resolution failed: GitHub workflow-history lookup failed: HTTP 504.` The Full Verification Gate consequently failed. This is an external history lookup failure, **not** an accepted API compatibility comparison. No test suite or security check is waived.

**Exact bounded repair commit message:** `fix(ci): retry transient OpenAPI baseline history lookup`. MODIFY only `.github/scripts/resolve_openapi_base.py`, `.github/scripts/test_resolve_openapi_base.py`, and `doc/roadmap/TASKS_HPS-007.md` (append this record). CREATE/DELETE none; no workflow YAML, Java, Flyway, OpenAPI snapshots, test inventory, production behavior or status cell changes.

**Repair behavior.** Existing verified successful full-CI ancestor selection and git ancestry checks remain mandatory. Each workflow-history API page receives at most **3 attempts** (initial + two bounded retries with 1s/2s backoff) for HTTP **502/503/504** or transient `URLError`/`TimeoutError`. HTTP 401/403/404, malformed JSON, invalid history, exhausted retries, absent verified ancestor and unverified base still **fail closed**. No cached/unverified baseline fallback, no OpenAPI skip, no inflated CI timing claim. Existing 20s per-request timeout is retained; only transient infrastructure failures can add bounded delay.

**Tests and limitations.** Four additional repository Python unittest methods cover 504 recovery, 502/503 and network recovery, exhaustion and fail-closed HTTP 403/invalid JSON; existing 14 tests are preserved. A **separate isolated local Python 3.13 control-flow harness** ran six matching retry cases, **6/6 PASS**; this is **not** an execution of the actual repository Python test module, which must be proven by the new exact-SHA CI. Package A remains implemented pending exact-SHA full CI; HAPI-601 overall stays `NOT_IMPLEMENTED / BLOCKED`. Do not start Package B until a separately owner-approved B contract and applicable CI evidence. No GitHub issue or PR.


### HAPI-601 — Package B owner direction and source-backed contract registration (2026-10-30)

**Owner decision and limits.** The repository owner explicitly approved the **Package B technical direction** presented in conversation: immutable Simulation manufacturer source persistence; fresh Party manufacturer eligibility; resource/company-scoped native Identity authorization; original physical protected transaction with final SQL witness; actual Workflow qualification separate from unverified documentary certificate; real PostgreSQL/replay/revocation/concurrency/rollback proof. The owner selected **this documentation-only exact-scope and contract registration as one stage** and expressly prohibited implementing B during this stage. Under the HAPI-601-only review-process exception, this is the owner's acceptance of that **direction**, not prior explicit acceptance of every newly specified Java path, SQL migration, resource/permission identifier or final B implementation contract. Future B implementation needs its own direct exact-contract decision/selection after the remaining technical gates. Neither independent reviewer sign-off nor B runtime acceptance is claimed.

**Exact registration-stage commit and allowlist.** Baseline `main` `dbfece384345543088364d53338919ed0989b8d4`, tree `d20c89452771cfb83fce8ba5958c70b00903011e`, parent `9c71b0c013b6fb8f29ce7d444a0f93d9b22fcecd`. At selection, [Full CI #1016](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41103337261) and [Documentation #769](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41103337010) were completed SUCCESS on the exact baseline; Full's Java/architecture, fourteen PostgreSQL shards, API/dictionary and final gate succeeded. Record-only message: `docs(simulation): register manufacturer package B technical contract`. MODIFY exactly `doc/roadmap/TASKS_HPS-007.md` (append-only) and `doc/roadmap/ULTIMATE_TASKS.md` (compact index pointer). CREATE/DELETE none. Preserve ROADMAP/PHASES table status, prior evidence, A source/codec bytes and migration history. After guarded publication observe its own Documentation Validation creation/start and STOP.

#### Exact-source findings and B owner split

- **Simulation A source and codec:** `ManufacturerCharacteristicRevision.java` and `ManufacturerCharacteristicRevisionCodec.java` are implemented with separate `HIDRA_MANUFACTURER_CHARACTERISTIC_SOURCE_V1` canonical bytes, source/revision, Party manufacturer/model scalar references, source-document digest, recorded/effective times, typed V1 compressor maps, valve maps, parameter tables and limits. Optional certificate fields are **documentary assertions only**. They do **not** contain `companyPartyId` or verified certificate/Workflow status. B must attach an immutable, independently checked company/source authorization binding **outside canonical A bytes**, never rewrite the A contract or legacy Simulation equipment/regulator codecs.
- **Party:** `TopologyInstalledUnitManufacturerEvidenceContract.java` and `JdbcTopologyInstalledUnitManufacturerEvidenceAdapter.java` prove a physically fenced, fresh manufacturer role/profile/qualification reference for Topology **only**, with single-use ticket, same backend PID/transaction ID and locked Party tables. They do not assert that a manufacturer is permitted to author for an arbitrary company, that a particular model is certified or that Simulation may reuse that opaque Topology ticket. B needs a **separate Party-public Simulation consumer contract**; authoritative company/manufacturer association policy remains a hard admission requirement, not an invented check.
- **Identity and platform:** `TopologyStationConfigurationAuthorizationContract.java`, `ProtectedTopologyStationConfigurationAuthorizationParticipant.java`, `ProtectedOperationIdentityParticipant.java`, `JdbcProtectedAuthorizationEvidenceReader.java` and `ProtectedTransactionExecutor.java` establish an analogous *other-resource* native prepared/admitted/current/final-fence pattern. No B permission exists by analogy. The B company-scoped resource must be owner-bound, with current allow/deny/revocation and complete original authentication provenance, not route authorization alone. Native one-use admission, exact original transaction/connection/role/fences, bounded time, statement fingerprints and a final closed scalar SQL guard are mandatory; failure including indeterminate commit may not be retried as a successful replay.
- **Workflow and certification:** `RevisionApprovalEvidenceContract.java` resolves actual final Workflow action for exact target digest/definition/version/type/purpose. `SimulationEquipmentParameterWorkflowTargetLookup.java` currently exports only `EQUIPMENT_PARAMETER_REVISION`. A **new manufacturer-source target** and a distinct Workflow approval adapter are needed. Party manufacturer qualification, a certificate pointer and Workflow approval are three different facts. No independent certificate issuer/verifier contract is established; mark certification **UNVERIFIED** until real evidence exists, and do not prevent a separate supplier-declared revision merely because calibration/certification is missing.
- **PostgreSQL and API:** the exact-head migration directory contains **190 SQL files**, through `V20261029_003__topology_station_configuration_protected_operations.sql` and no `V20261030_*`. The station-configuration migration is a protected locking/origin precedent, **not** a manufacturer grant or schema. `SpringSimulationController.java` has no manufacturer authoring route; Package B introduces no HTTP route or OpenAPI publication, which belongs to separately approved Package C. `RECONCILIATION.md` was not freshly readable through the large-file connector; no claim of a freshly reconciled row is made.

#### B proposed executable contract — technical registration, NOT source authorization

1. **Immutable source authority.** The Simulation repository stores exactly canonical A bytes, format/version, SHA-256 digest and immutable stored identity. A separate owner-scoped record binds `companyPartyId`, source/revision and author/provenance to the immutable source without altering those bytes. Proposed physical conflict key `(company_party_id, source_id, revision_id)`; a conflicting digest or provenance under the same key is a hard 409-equivalent domain conflict, and byte-identical replay is idempotent only within the *same original company/authorization context*. Decide any global source-identity uniqueness rule explicitly before DDL; the A aggregate alone does not supply it. Record exact effective/recorded timestamps and avoid invented calibration or product physics. Never update or delete historical revisions or audit.
2. **Authorization before discovery.** Proposed native operations `SAVE`, `READ_REVISION`, `LIST_HISTORY`, `READ_CERTIFICATE`, `QUALIFY` with independent company/resource scopes and permission decisions. Suggested **unregistered** permission names are `simulation:manufacturer-characteristic:save`, `simulation:manufacturer-characteristic:read`, `simulation:manufacturer-characteristic:history`, `simulation:manufacturer-certificate:read` and `simulation:manufacturer-characteristic:qualify`. Exact permission catalog, resource type, actual company-scoping evidence and grant model need final owner admission before code; these strings are **not existing permissions**. Read/history/certificate and qualify paths must recheck authority *before* exposing existence, counts, source bytes, certificate references or approval history. All DENY, INDETERMINATE, unknown, stale, expired or revoked cases fail closed.
3. **Physical transaction boundary.** Native Identity prepared at the trusted origin; execute one restricted PostgreSQL original transaction with current identity/native evidence, fresh Party ownership evidence and ordered owner locks. Ensure source, current actor, company, backend PID, transaction ID, role, sampled/expiry interval, policy generation and document digest match one audited receipt. Use `ProtectedTransactionExecutor` only after an exact compiled-caller/DML-fingerprint registration and final *closed scalar* guard with all required relation fences. Enforce restricted grant/EXECUTE policy (no `PUBLIC` privileges), trigger-backed immutable history/receipt and unique/CAS constraints. Any missing lock, owner ambiguity, executor budget expiry, failed or unknown commit must be a failure/INDETERMINATE without unauthorized retry; never simulate physical commitment through in-memory state.
4. **Qualification versus certification.** `SUPPLIER_DECLARED` and `SYNTHETIC` are existing A origins, **not** trust decisions. Verify genuine Party manufacturer eligibility and current source applicability for protected write; store unverified certificate references solely as documentary source assertion. A separate Workflow target for the exact canonical manufacturer-source digest may produce **Workflow-qualified** evidence after authentic final action, version/definition/actor/time matching and current protected authorization. There is **no** automatic manufacturer-certified state while issuer authentication remains unverified. Qualification must be independently immutably evidenced without mutating supplied source.
5. **Acceptance.** Unit/architecture, actual Java 21 Surefire, fresh PostgreSQL migration/JPA where applicable, physical FK/check/unique/trigger/role tests, all supported LOCAL/LDAP/OIDC operation-binding provenance, current grants/DENY/INDETERMINATE, party retirement/rebinding and certificate fraud/non-assertion, exact Workflow target and digest, two-company cross-read non-disclosure, concurrent writers/readers/revocation, same/conflicting replay, source validity timeline, actual commit/rollback/indeterminate failure, and original A codec/hash regressions. Register new tests and source digests in `.github/ci-test-inventory.json`; actual source Full/Fast/Docs plus catalog/dictionary equality must pass before B acceptance. CI-first synthetic proof is not engineering/SITE or deployment validation.

#### Exact *candidate* future B CREATE/MODIFY file ledger

**Existing seven Simulation candidate CREATE paths** (preserved verbatim from prior B definition; files were not present at this registration):
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/ManufacturerCharacteristicRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/ManufacturerCharacteristicRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/ManufacturerCharacteristicRevisionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/ProtectedManufacturerCharacteristicAdmissionCoordinator.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JdbcManufacturerCharacteristicRevisionRepositoryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/ManufacturerCharacteristicRevisionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/qualification/ManufacturerCharacteristicNativeQualificationTest.java`

**Additional source-backed contract *candidate* CREATE paths, absent at this head:** `src/main/java/dz/sh/hidra/modules/party/application/contract/simulation/SimulationManufacturerEvidenceContract.java`, `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JdbcSimulationManufacturerEvidenceAdapter.java`, `src/main/java/dz/sh/hidra/modules/identity/application/contract/simulation/SimulationManufacturerCharacteristicAuthorizationContract.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedSimulationManufacturerCharacteristicAuthorizationParticipant.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/ManufacturerCharacteristicApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/WorkflowManufacturerCharacteristicApprovalEvidenceAdapter.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/SimulationManufacturerCharacteristicWorkflowTargetLookup.java`, `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedSimulationManufacturerCharacteristicAuthorizationParticipantTest.java`. These names allocate responsibilities for a future exact review, **not** an implemented interface or pre-approved cross-owner coupling.

**Existing candidate MODIFY paths:** `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedOperationIdentityParticipant.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java`, `src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java`, `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`, `.github/ci-test-inventory.json`, and `doc/roadmap/TASKS_HPS-007.md`. Actual affected Workflow target registration/metadata and Party company-owner resolution tests are **DISCOVERY_REQUIRED**; they may not be silently added. No foreign persistence imports, old codec rewrites, unrelated DML fingerprints, topology installed-unit links, numerical engine, REST DTOs or OpenAPI in B.

**Proposed additive PostgreSQL target only:** `src/main/resources/db/migration/V20261030_001__simulation_manufacturer_characteristic_sources.sql` is **NOT RESERVED** and was absent at this head. Recheck live migration order and approve exact tables, columns, references, cross-owner association, trigger bodies, ownership/privileges and source-proof test before any migration commit. Updating `doc/database/DATA_DICTIONARY.md` requires the **actual migrated PostgreSQL catalog**, never hand-authored inferred metadata. New Party/Identity/Workflow and database test filename additions require a subsequent *exact* owner-approved allowlist; no future implementation may treat this candidate ledger as a blanket write grant.

**Retained B implementation message:** `feat(simulation): protect supplied manufacturer characteristic revisions` — **not selected for this registration**, and not executable until the owner separately approves its exact complete technical/write contract. The proposed B scope is large and cross-owner; before executable admission, register coherent B1/B2/B3 packages or one bounded atomic B only after genuine dependency/SQL/role/Workflow analysis, each with exact paths/message/evidence and no unauthorized modifications. The owner decides that later selection; documentation-only agreement does not waive it.

**Disposition / next gate / STOP.** Owner's B technical **direction accepted** and this **registration** authorized. Package A source exists and Full #1016 is green, but there is no evidence of B native Party/Identity/Workflow contracts, physical tables or public API. HAPI-601 remains `NOT_IMPLEMENTED / BLOCKED` in ROADMAP/PHASES (not retroactively changed to PARTIAL); HAPI-602 and Package C remain blocked. Next eligible action is a **separate B exact contract/file/migration/security/approval admission decision**, not B runtime implementation under this registration. Do not create GitHub issues/PRs, numerical/P3, HidraWEB, SITE or release/deployment activity.


### HAPI-601 — final owner decision: ONE unified Package B, no further splits (2026-10-30)

**Direct approval.** Following verified [Documentation #770](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41105741501) SUCCESS at parent `81f50308d0d79406b19f83531b49d2bb9bb87f63`, and prior [Full CI #1016](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41103337261) SUCCESS at its unchanged Java source ancestor, the owner accepted the proposed Package B security direction with **"Ok but no more split"**. This explicitly replaces the proposed B1/B2/B3 decomposition with **ONE complete, indivisible Package B implementation**. Its Party, Identity, Simulation, Workflow, database and test components are internal bounded-context workstreams, **not separately selectable packages**. Do not propose further B fragments or request repeated approval of this same technical direction. Only actual later source conflict, impossible constraint or requested scope outside the ledger requires a new decision. This is owner **technical-contract approval**, not implemented/verified source, no independent reviewer signoff and no simulated CI result. Package C remains separately review-gated and is not selected.

**Selected registration commit:** `docs(simulation): admit unified manufacturer package B implementation contract`. MODIFY only `doc/roadmap/TASKS_HPS-007.md` (append this signed decision/ledger) and `doc/roadmap/ULTIMATE_TASKS.md` (compact pointer). CREATE/DELETE none. Preserve immutable task/status/roadmap tables, A sources and legacy codecs. Exact baseline tree `77283a03306ee28450ddfc87f4add54602d1125f`; publish to existing `main` with expected-head guard, verify paths/tree/parent/blobs, observe own Documentation CI startup and STOP. This stage writes **no code**.

#### Approved atomic Package B technical contract

1. **Simulation-owned immutable source:** Preserve A's `HIDRA_MANUFACTURER_CHARACTERISTIC_SOURCE_V1` bytes/hash, strict typed families and documentary provenance, without modifying the A model or codec or existing equipment/regulator formats. New Simulation append-only stored revision binds company Party ID, source/revision, digest, author/session/physical receipt and recorded/effective times. Enforce an owner-scoped `(company_party_id, source_id, revision_id)` unique key. Freshly authorized identical replay for the SAME original company/source/provenance is idempotent; conflicting payload/provenance or cross-company replay cannot overwrite/transfer anything. Canonical A source bytes remain immutable.
2. **Explicit Party authorization, deny by default:** Actual `PartyRelationshipType` is PARENT/SUBSIDIARY/AFFILIATE/JOINT_VENTURE/PARTNER/SUCCESSOR/OTHER; NONE proves manufacturer permission to supply source data for a particular company. Existing Party manufacturer role/profile/qualification and current company proof likewise do not create that grant. Create an additive **Party-owned** `public.hidra_party_manufacturer_source_authorization` with explicit company, manufacturer and model-scope reference, active status, validity/revocation and immutable authorization identity; no auto-grant, auto-seed or inference from OTHER, supplier status, certificate or synthetic code. Only Party-controlled authorization rows and fixed fresh physical fences may satisfy Simulation's Party-public evidence contract, including current active company/manufacturer role, qualified profile, exact scope, nonambiguous authorization and version/generation. Simulation does NOT manage Party grants, mutate Party tables or offer their REST endpoint. Positive synthetic tests provision explicit authorized fixtures; absent Party relation/permission makes runtime authoring fail closed.
3. **Native Identity:** Dedicated resource `MANUFACTURER_CHARACTERISTIC_REVISION`, explicitly company+source+revision scoped. Exact new code contract: SAVE=`simulation:manufacturer-characteristic:save`, READ_REVISION=`simulation:manufacturer-characteristic:read`, LIST_HISTORY=`simulation:manufacturer-characteristic:history`, READ_CERTIFICATE=`simulation:manufacturer-certificate:read`, QUALIFY=`simulation:manufacturer-characteristic:qualify`. These are **new contract identifiers**, not claims of existing seeded Identity grants. No automatic operator or public grant. Use actual one-use prepared/admitted native Identity current-group assertions, auth provenance (LOCAL/LDAP/OIDC), current company/resource scopes, current permission/revocation, original thread/backend PID/xid/role and temporal bounds; DENY, INDETERMINATE, missing or stale evidence fails closed **before** protected existence, counts, history, source bytes, document/certificate and approval disclosure.
4. **Physical transaction:** Original restricted PostgreSQL READ COMMITTED transaction, single trusted compiled caller and pinned SQL fingerprints via `ProtectedTransactionExecutor`, one-use opaque mutation admission, original-party and native locks, fixed owner relation fences, audited receipt and true final closed scalar guard before observed physical commit. No retry of indeterminate commit, no permissive fallback, no transaction/connection switch, no generic native authority export, no weakened budgets/guard, no PUBLIC table EXECUTE/write grants. New fresh-schema migrations supply same-owner FK/check/unique/trigger immutable receipts, provenance and revocation constraints and least privilege. No production data/backfill or imaginary credentials.
5. **Real Workflow qualification and unverified certificates:** `RevisionApprovalEvidenceQueryService.java` is **currently hardcoded** to accept Simulation type `EQUIPMENT_PARAMETER_REVISION` and purpose `EQUIPMENT_PARAMETER_QUALIFICATION`. Extend *within Workflow* with an additional exact `MANUFACTURER_CHARACTERISTIC_REVISION`/`MANUFACTURER_CHARACTERISTIC_QUALIFICATION` pair, preserving custody and existing Simulation behavior, final approved action, active definition/type/purpose and actor/sequence checks. New Simulation target lookup and outbound approval adapter expose company-domain-separated 64-hex target identity (company+source+revision+SHA) so identical bytes from two companies do NOT share approvals; preserve original byte SHA separately. Append immutable Workflow qualification only with a genuine exact terminal approval and fresh authorization/source applicability. Party qualification, Workflow approval and optional unverified documentary certificate are different states. No invented issuer verification or manufacturer certificate, and no numerical calibration.
6. **Scope:** B delivers only protected internal application/owner-storage/qualification contracts and real security/PostgreSQL proof. No API/controller/DTO/OpenAPI (C), installed-unit characteristic binding (HAPI-602), numerical P3, external engines, HidraWEB, OT control, release/SITE or deployment.

#### ONE future executable Package B commit — final exact file-scope ledger

**Registered message:** `feat(simulation): protect supplied manufacturer characteristic revisions`. One future `Next` selects ONE full implementation under this message, with one expected-head guarded commit and one subsequent exact-SHA CI observation. It is not authorized to publish partial B1/B2/B3 commits. If an acceptance precondition is impossible or main advances, report/reconcile rather than bypass checks.

**CREATE paths (19, all absent at this registration HEAD):**
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/ManufacturerCharacteristicRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/ManufacturerCharacteristicRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/ManufacturerCharacteristicApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/ManufacturerCharacteristicRevisionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/ProtectedManufacturerCharacteristicAdmissionCoordinator.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JdbcManufacturerCharacteristicRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/WorkflowManufacturerCharacteristicApprovalEvidenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/SimulationManufacturerCharacteristicWorkflowTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/simulation/SimulationManufacturerEvidenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JdbcSimulationManufacturerEvidenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/simulation/SimulationManufacturerCharacteristicAuthorizationContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedSimulationManufacturerCharacteristicAuthorizationParticipant.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/ManufacturerCharacteristicRevisionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/qualification/ManufacturerCharacteristicNativeQualificationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedSimulationManufacturerCharacteristicAuthorizationParticipantTest.java`
- `src/test/java/dz/sh/hidra/modules/party/infrastructure/persistence/SimulationManufacturerEvidencePostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/ManufacturerCharacteristicRevisionApplicationServiceTest.java`
- `src/main/resources/db/migration/V20261030_001__party_manufacturer_source_authorizations.sql`
- `src/main/resources/db/migration/V20261030_002__simulation_manufacturer_characteristic_sources.sql`

**MODIFY paths (11, all existing):**
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/PartyPersistence.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedOperationIdentityParticipant.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java`
- `src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/RevisionApprovalEvidenceQueryService.java`
- `src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/RevisionApprovalEvidencePostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `.github/ci-test-inventory.json`
- `.github/database-dictionary-ownership.json`
- `doc/database/DATA_DICTIONARY.md`
- `doc/roadmap/TASKS_HPS-007.md`

**DELETE:** none. This is an exact **maximum** write allowlist; no extra files on inference. The two ordered additive migrations replace the earlier unreserved *candidate* `V20261030_001__simulation_manufacturer_characteristic_sources.sql`: the first is Party-owned authorization, second Simulation-owned persistence. Version slots are registered here for this single B implementation, **subject to fresh normalized Flyway collision scan immediately before execution**. Do not edit old migrations, invent a grant principal, rewrite a native permission, or add cross-owner physical FKs. `doc/database/DATA_DICTIONARY.md` is modifiable solely from observed fresh PostgreSQL catalog generated proof; `.github/database-dictionary-ownership.json` must have actual corresponding ownership/evidence. Legacy ROADMAP/PHASES status cells remain frozen until valid task status acceptance, not during this source implementation.

**Required integrated acceptance — not separate stages.** Java 21 Maven compile/unit/ArchUnit and exact test inventory/source hashes, true fresh Flyway/PostgreSQL installation, JPA validate where used, constraints/triggers/role catalog, physical before-commit and rollback/indeterminate; explicit Party authorization and company retirement/rebinding, denial on absent/revoked/expired eligibility; native LOCAL/LDAP/OIDC current resource/permission/identity and cross-company privacy; concurrent revocation/replay and source physical/digest integrity; all A family codecs unchanged; Workflow exact company-separated target and final approval, misuse/denial/same digest cross-company; certificate remains UNVERIFIED. No skipped tests represented as proof and no green-CI shortcut. Existing behavior tests and Full/Docs CI gates stay intact. Synthetic tests are software contract proof only; never engineering/SITE, manufacturer-certified, release or deployment acceptance.

**Disposition and next action.** B technical decision **ACCEPTED as one unsplit package**; actual B source NOT_IMPLEMENTED, HAPI-601 overall `NOT_IMPLEMENTED / BLOCKED` until B/C acceptance. Next `Next` executes the one entire registered B implementation commit after fresh exact-source and CI review, without asking the owner to divide it or reconfirm this contract. If an actual blocker occurs, report it explicitly rather than silently reducing safeguards. Do not automatically proceed to Package C, HAPI-602 or simulation/P3.


### HAPI-601 — owner-approved unified Package B Workflow query-guard allowlist amendment (2026-10-30)

**Explicit owner decision.** Following current main `2a8655b35869bb505dcf3a9248f066534575cadc` ([Documentation CI #771](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41108239714) completed SUCCESS; last Java-source Full CI #1016 successful at `dbfece384345543088364d53338919ed0989b8d4`), the owner answered **"Approved"** to the assistant's exact, narrow request to add two Workflow MODIFY paths and one CREATE PostgreSQL integration-test path to **the same unified HAPI-601 Package B**. This decision authorizes these exact extra paths **within the single existing B implementation scope only**, NOT new B1/B2/B3 packages, a separate B implementation commit, Package C, or new public HTTP routes. The original registered B source implementation message remains `feat(simulation): protect supplied manufacturer characteristic revisions`; all previously admitted CREATE/MODIFY paths, Identity/Party/Workflow/source acceptance safeguards, unique versions and statuses are preserved without renaming or reinterpretation.

**Exact-source reason for the correction.** At the admitted source head `2a8655b35869bb505dcf3a9248f066534575cadc`, `workflow/infrastructure/query/JpaWorkflowQueryAdapter.java` independently maps `WorkflowTaskJpaEntity`, `WorkflowInstanceJpaEntity` and `WorkflowActionJpaEntity` to task inbox rows, task and current-task detail, instance detail, timelines and available transition metadata. Its page implementation calculates `totalElements`, `totalPages` and `hasNext`. `workflow/api/rest/controller/WorkflowQueryController.java` publishes the preexisting query APIs; changing it is **not approved**. Existing `WorkflowOwnedTargetLookup.java` offers only `module()`, `targetTypeCodes()` and `eligibleTarget()` and has no protected read/view authorization callback. The existing `SimulationEquipmentParameterWorkflowTargetLookup` is for `EQUIPMENT_PARAMETER_REVISION`, and no generic Workflow actor/task relationship or permission list establishes native authorization for `MANUFACTURER_CHARACTERISTIC_REVISION`. This is a concrete protected-discovery/cross-company gap, not proof of an observed production leak or authorization bypass exploitation.

**APPROVED ADDITIONS to the immutable ONE-B maximum implementation file ledger:**
- MODIFY `src/main/java/dz/sh/hidra/modules/workflow/application/contract/target/WorkflowOwnedTargetLookup.java` — extend the Workflow-public owner target contract as required for a manufacturer-specific, deny-by-default source-read guard; do not make generic role checks stand in for fresh authoritative origin proof or disrupt other module target lookups.
- MODIFY `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/query/JpaWorkflowQueryAdapter.java` — apply the per-target protection before disclosure in `tasks` (including counts/pagination), `task`, `currentTask`, `instance`, `timeline` and `availableActions`. Deny or exclude protected data **before** computing visible counts, not merely redact labels after querying. Preserve the existing behavior of nonmanufacturer targets, and do not give ordinary Workflow task membership authority over manufacturer-source details.
- CREATE `src/test/java/dz/sh/hidra/modules/workflow/infrastructure/query/WorkflowManufacturerSourceAuthorizationPostgresIntegrationTest.java` — genuine PostgreSQL positive/negative cases: authorized company + current original Identity/Party provenance; unknown/unassigned/revoked/expired manufacturer and scope; two-company same-payload isolation; early non-disclosure for task lists/totals, task/instance identifiers, history and available actions; wrong thread/connection, concurrent revocation and unchanged nonmanufacturer functionality. New test must be registered with exact source hash and correct existing CI lane in `.github/ci-test-inventory.json`; no new/shrunk CI workflow or skipped cases. This test file does not yet exist; creation is **authorized**, not implemented.

**Critical transaction/architecture gate.** `JpaWorkflowQueryAdapter` currently has class-level `@Transactional(readOnly=true)`, while the approved manufacturer native prepared/one-use Identity admission requires a trusted original physical transaction and cannot be pretended to work within the wrong ambient or a read-only transaction. The actual combined B implementation MUST establish the correct source-specific prepared/owned-read boundary *before any protected disclosure*, using only valid owner-public contracts, original same-PID/XID authorization and strict fail-closed semantics. Do not call a protected writer in an unsupported ambient transaction, synthesize native credentials, or read manufacturer existence first and check later. No new Workflow source file or extra cross-owner import beyond this amended maximum ledger is implicitly authorized. If exact-source implementation proves another file necessary, surface that real conflict transparently rather than weaken the gate.

**This approval-registration write only.** Message `docs(simulation): admit manufacturer B Workflow query guard scope`. MODIFY exactly `doc/roadmap/TASKS_HPS-007.md` (append-only) and `doc/roadmap/ULTIMATE_TASKS.md` (compact pointer); CREATE/DELETE none. No Java/SQL/CI/permission or physical dictionary writes yet. Publish with expected-main guard, verify exact parent/tree/blobs/paths, confirm actual Documentation CI creation/start and STOP. Registration is not implementation proof. Subsequent owner `Next` still means executing **the whole one-commit Package B**, never a smaller B slice. Owner has already approved the expanded exact allowlist; do not solicit duplicate approval of these same three paths.

**Disposition.** HAPI-601 remains `NOT_IMPLEMENTED / BLOCKED` globally; A source exists, B source and protected Workflow queries are pending implementation/CI; C and HAPI-602 remain independently gated. No numerical/P3, Web, OT deployment, SITE acceptance, extra issues or PRs.

### HAPI-601 — owner-approved unified B reference-read closure and implementation attempt (2026-10-31)

**Owner decision already given in project conversation.** The owner approved adding six exact existing Workflow MODIFY files to the same unified HAPI-601 Package B, then directed the assistant to complete the task without additional artificial slices or repeated scope approvals. This entry records that decision without changing the registered task message or allowing unlisted paths. The resulting maximum write allowlist is **20 CREATE and 19 MODIFY**, and may be smaller if evidence prevents dictionary edits.

**Six approved additional MODIFY paths (all within the one B commit):**
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/AuditWorkflowReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/DocumentsApprovalReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/IncidentWorkflowQueryService.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/AuditWorkflowReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/DocumentsApprovalReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/IncidentWorkflowContractTest.java`

**Architectural decision already approved.** Workflow may independently record human decisions; those decisions are evidence, not current source authorization. Simulation may qualify a manufacturer revision only after current native Identity, explicit Party company/manufacturer/model authority, source applicability and exact final Workflow evidence are jointly rechecked in the original protected transaction. Workflow query/reference existence requires prior source-owner authorization. Preserve all existing nonmanufacturer behavior.

**CI-first implementation qualification.** This registration belongs to the single `feat(simulation): protect supplied manufacturer characteristic revisions` implementation commit. It does NOT attest Java compilation, database migration, PostgreSQL role grants, concurrent revocation correctness, CI success, full B acceptance, any operational data, or industrial validation. Missing executable native-positive/negative and physical PostgreSQL gates remain acceptance work until actual tests and CI evidence prove them. The existing physical dictionary must not be modified without migrated catalog evidence. Package C, HAPI-602 and numerical/P3 remain excluded.

<!-- task-register:payload:end -->
