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

<!-- task-register:payload:end -->
