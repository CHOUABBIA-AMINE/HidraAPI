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

### HAPI-601 — authorized CI #1019 regression repair and physical dictionary reconciliation (2026-10-31)

**Owner scope decision.** The owner explicitly approved extending the existing ONE unified B CI-repair MODIFY allowlist with exactly three tests: `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`, `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java`, and `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReaderTest.java`. These are regression-guard corrections, not blanket cross-module exports or Package C authority. Retained B files `src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/RevisionApprovalEvidencePostgresIntegrationTest.java`, `.github/ci-test-inventory.json`, `doc/database/DATA_DICTIONARY.md` and this task evidence file remain within the previously approved B write scope. No split, new migration, public API, grant or production/SITE selection.

**Exact failed-head evidence.** Main `2001971758d21f02040b867e709270cd04b8c11d`, tree `110b93860dab42b47bf9deb74c49ee5890a36260`: [Full CI #1019](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41167006680) FAILURE; [Documentation #774](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41167006671) SUCCESS. Unit/architecture ran 2325 tests and failed six old allowlist assertions against newly admitted manufacturer calls, typed Identity reader and compiled transaction caller. PostgreSQL shard13 had one nanosecond-vs-microsecond timestamp assertion failure. The API/dictionary job migrated all 192 scripts and independently captured 539 relations/zero unresolved owners, but returned `Physical dictionary drift`. A successful new Full CI is NOT asserted at this repair preparation.

**Constrained changes.** Exact coordinator/import allowlist tests retain sibling/private-adapter denials; existing admission and platform dependency checks remain exact lists, extended only for the named compiled manufacturer actor and fixed executor identifiers. Identity reader public-method list adds only `loadManufacturer`, with a typed signature assertion and original no-cache/no-clock/no-write checks retained. Workflow fixture aligns only the expected stored timestamp to PostgreSQL microseconds; exact target/purpose/digest mismatch negatives stay intact. Dictionary uses actual CI artifact ID `13029975055`: the accepted physical comparison is a single blank line before `public.hidra_simulation_model`; capture SHA provenance is refreshed from the actual artifact, and the reconstructed dictionary blob SHA is `76ff5a1c0710950565d54777e98e6b9ed7b11e00`. Generator's strict physical equality comparison is unchanged. Manifest changes only four actual updated test-file SHA-256 digests, preserving cases, membership, lanes and historic timing/evidence.

**Commit and acceptance.** Exact bounded repair message: `fix(simulation): reconcile manufacturer authorization CI evidence`; only the seven MODIFY paths above, no CREATE/DELETE. Source assertions, JSON structure, exact digest and artifact blob identity were checked before CI-first publication. No local Maven/JUnit/PostgreSQL execution is claimed; full exact-SHA CI is required to accept the repair. HAPI-601 B remains unaccepted until the genuine native security, transaction, role, Workflow and PostgreSQL gates pass. Package C, HAPI-602 and numerical/P3 remain unselected.

### HAPI-601 — owner-authorized bounded protected manufacturer history pagination prerequisite (2026-10-31)

**Owner decision, sequence and evidence.** After the 2026-10-31 HAPI-601 unified B repair, the owner expressly approved *registration* of a source-backed, bounded backend pagination prerequisite **before Package C**, with all current security, transaction, governance and acceptance rules retained. Exact refreshed `main` was `3870d57a16c008afdba799ed45c9d3008f6b810b`, parent `2001971758d21f02040b867e709270cd04b8c11d`, tree `dc9c5d331dda1678b4d77c24f19de801897116f8`. [Full CI #1020](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41168293922) **SUCCESS** and [Docs #775](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41168293706) **SUCCESS**, both at the same SHA; Java unit/architecture, all 14 PostgreSQL shards, API/catalog and Full Verification Gate passed. The separately reported Java 21 Maven verification job was **SKIPPED** and is not claimed executed. This registers a prerequisite **within existing HAPI-601**; it does not introduce HAPI-XXX, split the already implemented unified B, select C, or move frozen statuses.

**Verified ownership and specific defect.** `ManufacturerCharacteristicRevisionUseCase.Command` has only company/source/revision and lacks page size/cursor; `Result.history` is a complete list without continuation. `ManufacturerCharacteristicRevisionRepositoryPort.history(Admission,String,String)` returns all selected revisions. `ProtectedManufacturerCharacteristicAdmissionCoordinator.acquire` currently admits the `LIST_HISTORY` native Identity permission before selecting source rows and requires a fresh `SimulationManufacturerEvidenceContract.Requirement(company,manufacturer,model)` for **each selected revision**, with Party/Identity final fences in its original protected transaction. `JdbcManufacturerCharacteristicRevisionRepositoryAdapter.history` selects `ORDER BY source_recorded_at,revision_id LIMIT 257` and denies when over 256 rows. This cap is a safety behavior, **not pagination**. Existing `V20261030_002__simulation_manufacturer_characteristic_sources.sql` already provides `idx_sim_manufacturer_characteristic_history(company_party_id,source_id,source_recorded_at,revision_id)` and immutable per-company/source/revision keys, so no new migration/index is justified by the current plan. Party eligibility is manufacturer/model-specific, **not** a blanket permission for every revision of a source. The current no-disclosure-before-owner-authorization invariant must survive continuation across pages.

**Exact registration-only commit.** Message `docs(simulation): register manufacturer history pagination prerequisite`. CREATE none; MODIFY **only** `doc/roadmap/TASKS_HPS-007.md` (append-only technical decision) and `doc/roadmap/ULTIMATE_TASKS.md` (compact reference). DELETE none. No Java, SQL, CI inventory, OpenAPI, Workflow/Identity/Party or data-dictionary mutation is authorized by this documentation commit. Preserve ROADMAP/PHASES status cells and legacy HPR-P25-054C mapping.

**Proposed prerequisite implementation after separate selection/acceptance (not executed by this registration).** Message `feat(simulation): paginate protected manufacturer history`. One bounded internal HAPI-601 prerequisite, distinct from historical unified B's already published commit and **before** any C controller implementation; not a new B1/B2/B3 package. Proposed maximum MODIFY scope, source-backed and review-gated:

- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/ManufacturerCharacteristicRevisionUseCase.java` — introduce bounded history-page request and continuation response with source-scoped selector; retain the five established operations, existing nonhistory validation and binary compatibility only if actually proven.
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/ManufacturerCharacteristicRevisionRepositoryPort.java` — typed keyset page boundary, never an independent authority or unscoped count/export.
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/ProtectedManufacturerCharacteristicAdmissionCoordinator.java` — prepare/admit/complete/final-guard each page in the genuine original restricted transaction; bind every retrieved/continuation row to fresh Party/Identity requirements and fixed owner fences.
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JdbcManufacturerCharacteristicRevisionRepositoryAdapter.java` — indexed keyset SQL with fixed predicates `company_party_id`, `source_id`, `(source_recorded_at,revision_id)` and bounded `LIMIT` + one lookahead for continuation; keep immutable source/qualification writes and strict SQL-fingerprint guards unchanged.
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/ManufacturerCharacteristicRevisionApplicationService.java` — **only if needed** for backward-compatible typed page dispatch; keep existing protected executor and nonambient transaction guarantees.
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/ManufacturerCharacteristicRevisionApplicationServiceTest.java`, `src/test/java/dz/sh/hidra/modules/simulation/qualification/ManufacturerCharacteristicNativeQualificationTest.java`, `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/ManufacturerCharacteristicRevisionPostgresIntegrationTest.java` — actual focused API-of-the-use-case, authorization and fresh PostgreSQL keyset/transaction tests.
- `.github/ci-test-inventory.json` — update only actual changed test hashes/case inventories; `doc/roadmap/TASKS_HPS-007.md` — append evidence.

**Parameter and cursor proposal.** Keep `LIST_HISTORY` as the existing dedicated permission `simulation:manufacturer-characteristic:history`; never borrow `READ_REVISION`, `READ_CERTIFICATE`, `QUALIFY`, generic Workflow permissions or the opaque cursor as authority. Proposed explicit per-page maximum **100** (default **50**, minimum **1**), with no unbounded fallback; retrieve at most `limit+1` ordered rows and produce a continuation only after its protected eligibility proof. Use the **persisted PostgreSQL** `source_recorded_at` value with its actual microsecond precision and `revision_id` tie-breaker, rather than converting an `Instant` with unproven nanosecond equality. Validate a versioned, bounded, canonical cursor binding exact company/source, ordering tuple, page selector and any chosen high-water semantics, rejecting altered, foreign, malformed, replay-misused or oversized cursor input. A cursor is a pagination hint, never a permission token, signed authorization assertion, or permission to bypass a fresh original transaction. No raw SQL, database credentials, source bytes, certificate, session token or private grant material in cursor/metadata. Any privacy-sensitive continuation or `hasNext` is protected disclosure and must be authorized before emission.

**Mandatory security and concurrent history acceptance gates.** Preserve `ProtectedTransactionExecutor` compiled-caller and original backend PID/XID/READ COMMITTED restrictions, current native Identity/Party/model scope and DENY precedence; fail closed before source existence, individual data, counts, page metadata or continuation leakage. The current whole-history behavior checks every selected revision for separate manufacturer/model grants. A paginated implementation **must not silently relax** this to disclose an apparently eligible first page while another revision makes the source-wide history ineligible; prove an equivalent scoped complete-eligibility fence using bounded metadata/owner evidence or reject the history request until it can be proved. Recheck all page and lookahead rows, original physical final fences, temporal revocation and current grant generation; ensure no cross-company same-source/cursor exchange and no returning protected results before commit. An append-only keyset plus highest visible ordering key does **not** by itself establish a repeatable-read snapshot of concurrent backdated inserts: document and test exact cursor consistency and late/backdated-insert behavior, or register a separately justified physical prerequisite if a true snapshot guarantee proves necessary. Preserve absence/denial uniformity, no skip/duplicates among preexisting rows, stable tie ordering, end-of-history behavior, >256 rows without unbounded retrieval, changing grants between pages, concurrent revoke, replay, invalid/tampered scope, exact continuation metadata visibility, empty history, fail/rollback and indeterminate commit handling. A mixed-manufacturer/model history and a grant revoked between pages are required adversarial tests. Real PostgreSQL integration tests cannot be replaced by Docker-skipped tests or synthetic interface mocks. Prove no elevated rights, no bypass of existing certificate restriction, no schema drift and no weakening of legacy nonmanufacturer behavior.

**Unresolved implementation gate.** The existing Party public contract accepts one `(company,manufacturer,model)` at a time. If proving whole-source history eligibility without unbounded grants/scans cannot be achieved under the ten proposed MODIFY paths above, **STOP for explicit scoped owner decision**: do not add a Party method, persisted snapshot/generation record, migration, new test path, new time budget or authorization shortcut by inference. Any such material addition requires a source-backed amendment first. This registration authorizes only the documentation decision; future `Next` first requires choosing/accepting the bounded implementation contract and exact write ledger under current SHA and CI evidence, then implements one registered prerequisite with its own guarded publication/CI observation.

**Status and successor.** The HAPI-601 B implementation has passed its exact-SHA development CI but that does not establish manufacturer certification, engineering/SITE acceptance or Package C. HAPI-601 remains **NOT_IMPLEMENTED/BLOCKED** in frozen ROADMAP/PHASES until applicable whole-task A/B/C acceptance. Package C retains its own separate owner technical-contract signoff and registered `feat(simulation): expose supplied manufacturer characteristic authoring` message, seven C CREATE proposals and API/OpenAPI gates; C **cannot execute until pagination prerequisite is accepted**. HAPI-602, numerical/P3, HidraWEB, OT commands, production data, release and deployment remain excluded.

### HAPI-601 — owner correction: platform property-driven pagination before Package C (2026-10-31)

**Owner decision and selected stage.** After the manufacturer-history prerequisite was registered on `1a7f12e7df7f2c267aeab8a6fe6cd188372be977` ([Docs #776](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41169335389) SUCCESS) and B source `3870d57a16c008afdba799ed45c9d3008f6b810b` passed [Full #1020](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41168293922), the owner clarified that pagination serves **all components** and is **dynamically configured in application properties**, not hardcoded independently in Simulation. This `Next` selects only one documentation-only correction of HAPI-601's currently unadmitted pagination design, without changing cross-phase governance or commencing implementation.

**Verified source conflict.** HAPI-698/KER-009 is already IMPLEMENTED/COMPLETED: the current framework-neutral `dz.sh.hidra.kernel.pagination.PageRequest` nevertheless has a static maximum `200` and no runtime-bound policy. `HidraOperationalWorkbenchService` uses default `50` / maximum `200`; `JpaTelemetryQueryAdapter` bounds reading pages to `500` and trend limit to `10,000`; `EmployeeAppointmentQuery.History` bounds requests to `100`. No shared pagination property was observed in the five `src/main/resources/application*.properties` configurations, and `HidraPlatformProperties` has no pagination section. `doc/api/API_CONVENTIONS.md` explicitly distinguishes these existing API behaviors. HAPI-601's `JdbcManufacturerCharacteristicRevisionRepositoryAdapter.history` currently loads at most `257` source rows and fails closed above `256`, without a cursor/page contract. The previously proposed fixed manufacturer defaults/maxima `50/100` and source scan/model limits `4096/128` were **not accepted** and MUST NOT become independent hardcoded public policy.

**Correct ownership and reusable direction.** HPS-023 owns any **future shared platform pagination policy**, properties binding, validation and per-operation configuration; HAPI-698's completed pure-kernel value-object deliverables and frozen status are preserved. A Spring/platform configuration adapter may read deployment/application properties into validated server-owned defaults, maxima and cursor limits; exact property names, numerical bounds, overrides, source paths and implementation stage require a **separately approved HPS-023 technical contract**. `kernel` remains pure Java and must not read Spring properties; module-specific cursor/keyset and offset implementations can share policy while preserving their existing public defaults, request/response shapes and compatibility until each changed surface is separately admitted. Configuration is read at startup unless a separately validated live-reload mechanism exists; `dynamic configuration` here means owner-controlled property values, not an assumed hot-reload feature. Module-specific stricter privacy/resource caps remain permissible and must not be weakened by a shared upper bound.

**Manufacturer continuation after platform admission.** HAPI-601 remains Simulation owner of manufacturer revision history selection only; its bounded keyset and REST consumer must reuse the admitted platform policy instead of independently owning pagination settings. Its indexed `(company_party_id,source_id,source_recorded_at,revision_id)` history is an existing performance candidate, not a bypass of entire-source eligibility or a repeatable snapshot guarantee. Preserve current Party evidence for **every** relevant company/manufacturer/model, Identity's native `LIST_HISTORY` permission and scope, original protected backend PID/XID/READ COMMITTED/SQL final guard, revocation and temporal fences, no existence/count/page metadata disclosure before authorization, certificate privacy, cursor scoping, mixed-model/adversarial and genuine fresh PostgreSQL tests. The old proposed `feat(simulation): paginate protected manufacturer history` commit and ten-file scope are **review-gated, not presently executable** until upstream platform contract adoption and a source-backed HAPI-601 integration amendment; the earlier source facts and security tests remain required. The separate HAPI-601 Package C REST admission follows only after these prerequisites are qualified. Do not infer automatic retrofit of Telemetry, Workbench, Organization or unrelated endpoints.

**Exact selected governance write and acceptance.** Commit `docs(simulation): align manufacturer pagination with platform configuration`. CREATE none; MODIFY **only** `doc/roadmap/TASKS_HPS-007.md` (append-only) and `doc/roadmap/ULTIMATE_TASKS.md` (compact pointer); DELETE none. Do **not** edit HPS-023, HAPI-698, ROADMAP/PHASES, Java, properties, tests, SQL, OpenAPI, or CI inventory. Accept only preserved preceding text, exact guarded Git parent/tree/blob, and new Documentation Validation startup; this is no Java/PostgreSQL test or software acceptance. A further owner-approved platform task admission is needed before any cross-module implementation. HAPI-601 remains NOT_IMPLEMENTED/BLOCKED; HAPI-602, P3 numerical, certification, SITE and deployment remain excluded.

### HAPI-601 — protected manufacturer history authorization and cursor security preflight (2026-10-31; CONTRACT_PENDING_ADMISSION)

**Owner-selected stage and evidence.** The owner explicitly approved a documentation-only
source/security preflight after the HAPI-601 pagination integration direction was approved.
Parent `90f97740f37b2ce3ed41a81dcf1cfc3d35a1ac20` (`feat(platform): configure shared pagination policy`) has [Full CI
#1021](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41173608113)
and [Documentation Validation #779](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41173608054)
completed SUCCESS at the same SHA. This preflight does **not** approve a new Party contract,
migration, cursor implementation, tests, expanded security privileges or a Java/SQL write.
Its exact message is `docs(simulation): preflight protected manufacturer history pagination security`;
CREATE/DELETE none; MODIFY only this append-only `TASKS_HPS-007.md` and the compact
`ULTIMATE_TASKS.md` pointer. HAPI-601 remains NOT_IMPLEMENTED/BLOCKED in frozen
ROADMAP/PHASES. No new HAPI task number or legacy mapping is invented.

**Source-backed ownership, API reality and non-disclosure invariant.**
- Simulation owns immutable characteristic revision source, indexed history retrieval and the
  protected application coordinator. `ManufacturerCharacteristicRevisionUseCase.Command`
  has no page/cursor selector, `Result.history` has no continuation, and the repository's
  `history(Admission,company,source)` yields at most 256 rows, rejecting a 257th.
- Identity owns the exact `LIST_HISTORY` operation and native permission
  `simulation:manufacturer-characteristic:history`. `ProtectedManufacturerCharacteristicAdmissionCoordinator.acquire`
  currently authorizes it before selecting source history, then calls Party separately
  for **every selected revision**, rechecks evidence and final relation fences in the
  original `ProtectedTransactionExecutor` transaction. An apparently permitted first
  page must not reveal rows, source existence, totals, `hasNext` or a cursor if any
  relevant out-of-page revision would make the whole source history ineligible.
- Party owns `SimulationManufacturerEvidenceContract.Requirement(companyPartyId,
  manufacturerPartyId,manufacturerModelReference)`. Its JDBC adapter takes a genuine
  original-transaction, owner-table lock and verifies company/model-specific active
  authorizations, Party/role state, generations and validity. It exposes **one tuple**
  per admission; no reviewed source-wide/batch authorization or completeness witness
  is implemented. Simulation must not query Party's private authorization tables
  directly or treat a cursor as a native permission.
- `V20261030_002__simulation_manufacturer_characteristic_sources.sql` has immutable
  `(company_party_id,source_id,revision_id)` identity, `source_recorded_at`,
  `inserted_at`, and an index on `(company_party_id,source_id,source_recorded_at,revision_id)`.
  `hidra_simulation_manufacturer_lock_sources()` locks source/qualification tables
  SHARE ROW EXCLUSIVE NOWAIT inside the required READ COMMITTED transaction.
  This protects each **individual** physical transaction; it does not keep a
  cross-request historical snapshot. The timestamp CHECK
  `source_recorded_at<=inserted_at` does not make historical ordering equal to
  insertion/commit order or establish a monotonic commit sequence.
- HPS-023 `HidraPaginationProperties` is already registered through Spring: startup
  `hidra.api.pagination.default-size`, `max-size`, `max-cursor-length`
  (initial defaults 50/200/4096); `resolveSize` and `validateCursor` are
  opt-in technical bounds, never authority. The older manufacturer-specific
  fixed 50/100 and 4096/128 scan/model proposals were **not** adopted.

**Security contract candidate A — bounded complete source eligibility (PROPOSED ONLY).**
Following Identity `LIST_HISTORY` admission and within the original locked
transaction, a Simulation-owned source-selection adapter may inspect a
*complete and provably bounded* projection of distinct manufacturer/model
identities for the exact company/source. A one-extra-row overflow check must
fail closed before any externally observable history metadata. Every distinct
identity requires a fresh Party-owned `Requirement` admission, exact physical
PID/XID/role checks, generation/expiry recheck and relation fences. Any
unresolved or inconsistent source identity, absent grant, excessive source
cardinality or indeterminate completeness denies the **entire** history,
including a page that otherwise appears valid. No private Party table join,
unbounded full-payload scan, extra database role or arbitrary fixed limit is
authorized. This option is **unproven** until reviewed bounded SQL execution
plans, source-integrity reconciliation, workload policy, race tests and exact
authorization checks demonstrate complete coverage at a finite cost.
Alternatively, a Party-owned batch/source-scope evidence contract might be
necessary; that requires separate Party owner interface/adapter/test scope and
approval. Neither option is implemented or authorized by this preflight.

**Security contract candidate B — cursor and cross-request consistency (PROPOSED ONLY).**
A versioned, canonical, server-integrity-protected cursor would bind exact
company/source, persisted PostgreSQL ordering tuple (microsecond-precision
`source_recorded_at` plus `revision_id`), accepted page selector,
policy version/expiry and an explicitly defined source consistency boundary.
Use separately managed secrets and key rotation if a MAC is selected; do
not reuse Identity/JWT secrets, leak certificates, grant material, SQL or
source payload, or represent a cursor as authorization evidence. Reject
malformed, oversized, altered, expired and foreign-scope tokens **before**
protected source lookup; authenticate and reauthorize each page in a *fresh*
original restricted transaction. A source-locked READ COMMITTED query and
keyset alone do **not** establish repeatable-read history across requests.
`inserted_at` high water alone does not prove monotonic commit order,
exclude late commits with earlier timestamps, or prevent backdated
`source_recorded_at` from sorting behind a cursor. Do not claim a
snapshot, no-skip guarantee or a stable `hasNext` from that evidence.
The owner must separately select either a **provable stable snapshot/
monotonic source generation** (potential new owned persistence/schema and
migration) or an explicitly described weaker live-keyset consistency
contract with corresponding caller-visible limitations; no choice is
implicit here. Source-wide eligibility remains fresh per page regardless
of cursor integrity.

**Source-backed candidate implementation ledger — NOT ADMITTED.**
Existing Simulation-only proposal `feat(simulation): paginate protected manufacturer history`
lists MODIFY `ManufacturerCharacteristicRevisionUseCase.java`,
`ManufacturerCharacteristicRevisionRepositoryPort.java`,
`ProtectedManufacturerCharacteristicAdmissionCoordinator.java`,
`JdbcManufacturerCharacteristicRevisionRepositoryAdapter.java`,
possibly `ManufacturerCharacteristicRevisionApplicationService.java`,
three existing focused Simulation tests,
`.github/ci-test-inventory.json` for true inventory changes and this phase
file. Full repository-relative paths are recorded in the preceding HAPI-601
prerequisite section. The proposed complete-eligibility contract may need
**additional** `src/main/java/dz/sh/hidra/modules/party/application/contract/simulation/SimulationManufacturerEvidenceContract.java`,
`src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JdbcSimulationManufacturerEvidenceAdapter.java`
and existing/new Party native PostgreSQL tests. A stable snapshot may need
a separate reviewed Simulation-owned schema/adapter/trigger change plus
genuine PostgreSQL tests. Any such addition is outside the original
10-path ledger; no migration name or new test path is admitted or invented.
An explicit owner-approved exact CREATE/MODIFY/DELETE ledger and dependency
registration are mandatory *before* implementation. Do not retroactively
edit past acceptance or frozen status cells.

**Mandatory acceptance cases for a future approved implementation.**
Original PID/XID/READ COMMITTED, authority before source existence and
metadata, and final Party/Identity/Workflow/source fences must survive.
Test an authorized first page with an **unauthorized manufacturer/model in
a later page** (entire request denied), mixed-model/multi-company sources,
complete distinct-identity projection and bounded overflow, source and grant
mutations/revocations during and between page requests, group grant expiry,
denied/absent indistinguishability, terminal and empty history, >256 revisions
without materializing all payloads, page-size/environment overrides with
stricter owner cap, byte-accurate SQL timestamp tie ordering, foreign-scope/
tampered/replayed/expired cursor, secret rotation if used, concurrent append
and backdated inserts (before/after cursor), qualification privacy,
no cross-company metadata leakage, rollback and indeterminate commit.
Use real fresh-schema PostgreSQL and configured authorization identities;
Docker-skipped integration tests are not proof, nor are source review and
green documentation CI. Verify existing five non-history operations, Workflow
query guards, kernel/Workbench/Telemetry/Organization API compatibility,
unchanged migration history, test inventory and applicable runtime
OpenAPI. No live OT, P3 numerical solver, certificate synthesis, SITE,
HidraWEB, release or deployment is selected.

**Pending owner decisions and next gate.** (1) Admit and cost-bound a
source-wide complete-eligibility mechanism (per-tuple bounded traversal
versus independently reviewed Party batch) without weaker authorization;
(2) select exact cursor consistency semantics and any stable snapshot
storage mechanism, including handling of backdated/late inserts;
(3) approve owner-vetted file/SQL/test ledger, finite policy values and
exact implementation package. Until these are accepted and genuinely
validated, `feat(simulation): paginate protected manufacturer history`
remains review-gated. Package C, HAPI-602 and all downstream tasks remain
blocked. This documentation stage ends after exact-head guarded publication
and Documentation Validation run creation/queue/start verification.

### HAPI-601 — approved bounded-eligibility / live-keyset direction; candidate implementation contract (2026-10-31)

**Decision and delivery boundary.** Following the source-backed security preflight
on `16489a27124844e3c7a680b3bd31aeaef92ad877` ([Documentation Validation #780](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41177485909)
SUCCESS), the owner explicitly **approved the design direction**, not the future Java/SQL
implementation: (A) bounded *complete* manufacturer/model eligibility for the entire
company/source before any page or continuation is disclosed, and (B) an integrity-
protected, openly weaker **live keyset** cursor, not a cross-request snapshot.
This commit only registers a candidate technical implementation contract:
`docs(simulation): define bounded manufacturer history pagination contract`.
CREATE/DELETE none; MODIFY **only** this append-only
`doc/roadmap/TASKS_HPS-007.md` and compact `doc/roadmap/ULTIMATE_TASKS.md`.
The exact code-path ledger and numeric policy values below are **proposals**
requiring a *separate owner acceptance* before any runtime mutation.
Do not treat an approved direction or green Docs CI as engineering acceptance.

**Bounded-context and owner contract.** HAPI-601 in Simulation owns immutable
manufacturer characteristic source revisions, history selection and protected
results. Identity exclusively owns native `LIST_HISTORY` permission
`simulation:manufacturer-characteristic:history`; Party exclusively owns
current `Requirement(companyPartyId,manufacturerPartyId,manufacturerModelReference)`
evidence, generations and revocation. HPS-023 exclusively owns startup-bound
shared pagination defaults/max/cursor-length input validation, not module
authorization or keyset semantics. No new Party public API or existing Identity
permission is proposed for the initially selected bounded-traversal direction.
The protected source/Party/Identity locks, PID/XID, restricted role,
READ COMMITTED and physical final guard all stay in the **same original
`ProtectedTransactionExecutor` transaction per request**. No user-visible
protected data, existence, counts, `hasNext`, cursor, history or qualification
is released before final guards and successful physical commit.

**Candidate protected eligibility algorithm (not implemented).**
1. Reject invalid page-size and malformed cursor inputs independently of
   protected source existence, with uniform non-disclosing faults. Resolve
   requested page size through `HidraPaginationProperties.resolveSize` and
   existing owner-specific stricter resource maximum; no fixed manufacturer
   page limit `50/100` is accepted as an independent policy.
2. Within the *new* original restricted physical transaction, acquire the
   same Simulation immutable-source lock and Identity `LIST_HISTORY`
   native permission before source lookup. A Simulation-owned repository port
   may project **only** the exact company/source's distinct
   `(manufacturer_party_id,manufacturer_model_reference)` pairs from the
   already stored canonical-source identity columns. Prefer fixed SQL with
   projected columns and `LIMIT permitted_subjects+1`, not a full payload
   materialization, raw caller SQL, or joins into private Party tables.
   A finite **resource work/subject budget must first be approved** and
   supplied through owner-controlled startup configuration (HPS-023 remains
   shared page policy; an additional stricter resource budget is separate).
   Reject no rows, overflow, mismatch, uncertainty or query budget overrun
   **before disclosure**. An output `LIMIT` alone does not bound scan CPU/IO:
   the current `(company,source,source_recorded_at,revision_id)` index is
   not a proven efficient distinct-subject index. PostgreSQL plan/buffer/
   latency results and canonical identity/provenance verification are hard
   acceptance gates. If projection cannot prove complete authorized coverage
   at finite cost, STOP and register an explicit Party-owned/batch/owned-index
   prerequisite rather than admitting a partial first page.
3. For **every** distinct pair (including those belonging only to later
   pages), call the existing Party owner contract to obtain fresh company/
   manufacturer/model authority on the exact PID/XID/role. Preserve active
   authorization, source, profile/role/generation, validity, DENY precedence,
   relation locks and final `requireCurrent` for **all** pairs. Do not
   replace source-wide authorization with page-only checks, grants from
   prior requests or a cursor assertion. Check that every page and lookahead
   revision belongs to this complete, verified source projection and that
   database identity columns match immutable canonical payload evidence.
4. Only after complete eligibility, execute fixed indexed keyset SQL ordered
   by the **persisted PostgreSQL** `source_recorded_at` (microsecond
   precision), then `revision_id`, with strict company/source predicates
   and a bounded `pageSize+1` lookahead. No total-count or source-existence
   metadata API is introduced. Qualification flags remain protected.
   Each request rechecks the entire source's applicable Party grants and
   Identity authority and completes the existing flush/final-fence/commit
   protocol before returning any result.

**Candidate live-keyset semantics (owner-approved direction, not code).**
- Cursor format: canonical versioned URL-safe encoding, scope bound to exact
  `companyPartyId`, `sourceId`, *accepted page size*, persisted ordering
  tuple (not decoded `Instant` nanos), issued/expiry instants, token key ID
  and domain-separated MAC (e.g., HMAC-SHA256) with constant-time
  authentication. `HidraPaginationProperties.validateCursor` checks only
  bounded input length; a Simulation-owned signing adapter must authenticate,
  check canonicality/version/scope/expiry and reject truncation, ambiguity,
  overlong tokens, tampering and unsupported rotation before any source
  lookup. Require a **separate** startup-managed signing key and explicit
  key rotation policy; neither the JWT key nor Identity proof key may be
  reused. This is only a navigation hint, not Identity/Party authorization.
- Each page is a **new, independently authorized READ COMMITTED transaction**.
  The cursor records the last emitted `(source_recorded_at,revision_id)`,
  not a promise that the historical set remains fixed. Existing immutable
  records strictly after the last key are eligible for subsequent pages;
  revisions inserted later with an ordering key at/before that position can
  be **missed** by an in-progress traversal. Later appended records strictly
  after the cursor may appear. A restarted traversal can see newly inserted
  and backdated rows; a repeat of a valid cursor is allowed only as a new
  authorization event, not accepted as prior permission or treated as a
  single-use anti-replay proof. Absent a persistent generation/snapshot,
  **no complete-snapshot, no-skip-under-concurrent-insert, fixed-total,
  or end-of-history permanence guarantee** is claimed. `hasNext` refers
  only to the currently authorized page and one lookahead at its transaction.
- Client documentation must tell operators that keyset pages are a
  **live view**, not a signed engineering revision baseline. Formal
  reproducibility and pinned engineering input sets are separate workflows,
  never inferred from a history cursor.

**Proposed exact runtime write ledger (NOT ADMITTED).** MODIFY only after
the owner approves exact contract/scope:
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/ManufacturerCharacteristicRevisionUseCase.java`: add source-scoped typed history-page request/response, no total; preserve existing six-argument `Command` and five current operations for compatibility.
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/ManufacturerCharacteristicRevisionRepositoryPort.java`: typed complete-source principal projection and persisted-timestamp keyset page; no unbounded public history export.
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/ProtectedManufacturerCharacteristicAdmissionCoordinator.java`: prepare/acquire/authorize-all/perform/final-guard new page.
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/ManufacturerCharacteristicRevisionApplicationService.java`: dispatch history-page publication via the same protected executor.
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JdbcManufacturerCharacteristicRevisionRepositoryAdapter.java`: fixed indexed SQL, complete principal projection, strict bounded paging and persisted microsecond ordering; preserve pinned INSERT SQL and source qualifications.
- `src/main/resources/application.properties`: *candidate only*, explicit resource work budget and **separately provisioned cursor secret reference**; startup fail-closed policy and profile compatibility must be admitted before naming values.
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/ManufacturerCharacteristicRevisionApplicationServiceTest.java`;
  `src/test/java/dz/sh/hidra/modules/simulation/qualification/ManufacturerCharacteristicNativeQualificationTest.java`;
  `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/ManufacturerCharacteristicRevisionPostgresIntegrationTest.java`:
  focused API, protected non-disclosure, and real fresh PostgreSQL tests.
- `.github/ci-test-inventory.json`: only real changed test hashes/cases;
  `doc/roadmap/TASKS_HPS-007.md`: append source/test acceptance evidence.

Candidate CREATE paths, contingent on interface/module review:
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/ManufacturerCharacteristicHistoryCursorPort.java` — engine/framework-neutral port for signed cursor claims, never an authorization credential;
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/security/HmacManufacturerCharacteristicHistoryCursorAdapter.java` — strictly validated MAC/expiry/key lifecycle with isolated secret;
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/security/HmacManufacturerCharacteristicHistoryCursorAdapterTest.java` — deterministic malformed/canonical/scope/rotation/security cases.
These are **proposed file names only**, not existing files or approved creates.
Proposed earlier ten-file implementation scope is **superseded as a sufficient
ledger** by this larger candidate; no source/CI/migration write is authorized.
No Party source changes are currently selected. New Party contract, source
index/Flyway migration or DB integrity witness requires **another narrowly
approved exact-path amendment** if feasibility evidence demands it.
No existing migrations may be rewritten.

**Mandatory implementation and acceptance gates.** (i) Exact request API
backward compatibility, synthetic tests and source-scoped permissions;
(ii) authorized page 1 with an unauthorized off-page distinct model denies
the *entire* request, including `hasNext`, foreign-company source,
same model repeated, missing grant, role deactivation/expiry/revocation,
empty/denied uniformity, overflow and unbounded-work rejection;
(iii) original PID/XID/current role, same physical Party/Identity
relations, temporal fence, concurrent revoke, final guard, successful
commit before publication, rollback/unknown commit and protected-query
non-disclosure; (iv) real PostgreSQL >256 revisions with distinct
principal projection, EXPLAIN/BUFFERS and bounded work, duplicate
recorded timestamps with SQL microseconds, adjacent pages/lookahead,
cursor HMAC expiry/key rotation/tampering/foreign scope; (v) late and
backdated inserts before/after cursor must demonstrate the **declared
weaker semantics**, including restart; (vi) no certificate leakage,
no unapproved schema change, no source-role elevation, no regression
to Workflow read gates, other four operations, platform/kernel
pagination or unrelated current APIs; (vii) test inventory,
Architecture/Java 21 and genuine PostgreSQL CI at the exact SHA.
Do not substitute Testcontainers skipped for PostgreSQL proof.
Deployment data, calibrated numerical P3, SITE and operations are excluded.

**Required next owner acceptance before code.** Explicitly accept an exact
finite source-work/subject budget and SQL cost strategy, secret
provisioning/rotation/configuration and compatibility rules, the proposed
MODIFY/CREATE ledger (including any necessary new PostgreSQL index/Party
contract), cursor expiry and error behavior, and the final implementation
commit `feat(simulation): paginate protected manufacturer history`.
Until this *separate* approval, the implementation stays
`CONTRACT_PENDING_ADMISSION`; no Package C, HAPI-602, numeric simulator,
HidraWEB or deployment is selected. Frozen ROADMAP/PHASES statuses
remain HAPI-601 NOT_IMPLEMENTED/BLOCKED.

### HAPI-601 — owner-approved streamed full-source integrity and bounded history pagination amendment (2026-10-31)

**Owner decisions and exact current evidence.** The owner accepted the previously
recorded HAPI-601 bounded whole-source authorization, explicitly weaker live-keyset
consistency, exact development settings (maximum **64 distinct company/manufacturer/
model subjects per history source**, cursor TTL **15 minutes**, separate
HMAC-SHA256 current key plus optional previous rotation key), and then expressly
approved **bounded streaming source-integrity validation** under unchanged protected
execution limits. These decisions supersede the preceding *unverified* distinct-
column-only eligibility projection as a sufficient source identity proof.
Source at `1dc095b0bd7822dce01fdab0cd5adaa4a848da6b` passed [Documentation Validation #781](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41177862392);
the latest [Full CI #1021](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41173608113)
passed at its preceding runtime SHA `90f97740f37b2ce3ed41a81dcf1cfc3d35a1ac20`,
not at this docs HEAD. This is the one **documentation-only amendment**:
`docs(simulation): admit streamed manufacturer history integrity contract`.
CREATE/DELETE none; MODIFY only `doc/roadmap/TASKS_HPS-007.md` (append only)
and `doc/roadmap/ULTIMATE_TASKS.md` (compact pointer). It executes no
Java, SQL, migration, test or configuration change and proves no runtime behavior.

**Verified threat / invariant.** The stored revision row contains
`manufacturer_party_id`, `manufacturer_model_reference`, and immutable
`canonical_payload`. `ManufacturerCharacteristicRevisionCodec.decode`
validates canonical framing, re-encoding and bounded payload bytes.
`JdbcManufacturerCharacteristicRevisionRepositoryAdapter.stored(ResultSet)`
verifies SHA-256, source/revision identities, manufacturer/model columns,
document digest and payload format against decoded source. The existing
`V20261030_002__simulation_manufacturer_characteristic_sources.sql`
BEFORE INSERT trigger verifies the SHA-256 of `canonical_payload` and
the original PID/XID/role, but it does **not** prove the manufacturer/model
relational columns agree with decoded canonical bytes. A distinct SQL
projection of those unverified columns **cannot** replace full source
validation. The old `history` reader validates all returned rows but
denies after row 256. The exact immutable schema and Flyway history remain
unchanged.

**Admitted implementation direction, fail closed.** In each new original
protected READ COMMITTED transaction, obtain the existing Simulation source
relation lock and native Identity `LIST_HISTORY` permission **before** any
scoped source query. Use one deterministic, fully-consumed, ordered streaming
JDBC traversal of **every** row for the exact company/source; keep a bounded
driver fetch batch, verify each raw row with the existing canonical
`stored`/codec checks, and discard its payload immediately rather than
accumulating `List<Stored>`. Reject any decoding, SHA, source, manufacturer/
model, canonical document or reference inconsistency, without page, existence,
cursor, qualification or count disclosure. Maintain an in-memory set of at
most **64 distinct** canonical (manufacturer party, model reference) pairs;
the 65th denies the **entire** history, never silently truncates or
authorizes a first page. Do not conflate 64 *distinct subjects* with a
64-row history cap. A complete traversal that cannot finish under actual
SQL and protected execution budgets fails the entire operation; no
partial-result or distinct-projection fallback is allowed. Source integrity
is re-proved **for every page request**, including continuation, under the
same physical locks and original PID/XID/role. No elevated database grant,
new Party interface or persistent snapshot is part of this stage.

After successful complete source validation, obtain fresh Party owner
admission/evidence for **all** verified pairs (including off-page
manufacturers/models), enforce authorization/generation/temporal/role
rechecks and all final relation fences, and deny if any fails.
Only after this complete authority proof may a bounded page of canonical
summary records and optional lookahead be materialized. A single-pass
streaming implementation may retain at most `effectivePageSize+1`
summary-only candidates (not canonical payloads) by comparing **persisted
PostgreSQL** `source_recorded_at` with `revision_id`; it must still
consume/verify *all* source rows. Protect qualification lookups on
returned rows with the same physical admission; never expand certificate
access or introduce total counts. Same timestamp ties use PostgreSQL
microsecond ordering, not unproven Java nanosecond equality.

The existing `ProtectedTransactionExecutor` manufacturer work budget
is **500 ms**, with `SET LOCAL statement_timeout='750ms'`; neither value
is to be weakened. The work budget is checked at executor phases, not a
guaranteed asynchronous interrupt of an in-flight SQL statement.
The PostgreSQL statement timeout is per statement, not a promise of
bounded source cardinality. Bounded per-row payload (codec max
1,048,576 bytes), **64 subjects**, `effectivePageSize+1` summaries and
finite SQL/transaction deadlines limit memory and work; do **not**
claim a workload-independent maximum completion time or that a large
history is automatically pageable. Existing index
`(company_party_id,source_id,source_recorded_at,revision_id)` is an
ordering candidate, not measured cost proof. Source cardinality, SQL
EXPLAIN/BUFFERS, fetch behavior, lock latency, stream exhaustion
and >256 synthetic revisions must be tested in actual PostgreSQL.
If meaningful bounded workload cannot pass without timing/privilege
relaxations, STOP for a separately accepted owner/schema/index
prerequisite, not a bypass.

**Cursor/compatibility policies (owner-accepted values, code pending).**
Reuse HPS-023 opt-in `HidraPaginationProperties.resolveSize` and
`validateCursor`; manufacturer history uses no hardcoded independent
50/100 page maximum and does not change existing Workbench/Telemetry/
Organization behavior. The proposed Simulation-specific token has
canonical version, company/source, effective page size, persisted ordered
last key, issued/expiry, key ID and domain-separated HMAC-SHA256,
verified constant-time against a separate minimum-256-bit key.
Provision a **dedicated** current key through a restricted environment/
secret provider; previous key and its ID are optional and verification-
only until expired. A missing/invalid current key must **disable/reject
the new paginated operation**, not produce unsigned cursors or block
unrelated legacy endpoints by an accidental mandatory property
reference. No built-in production secret, reuse of JWT/Identity keys or
secret logging. Cursor TTL is 900 seconds; no earlier cross-request
snapshot, stable count or no-skip-under-backdated-insert assertion
is introduced. Replayed unexpired cursors require fresh complete
source authorization. A DB insertion after an earlier page may be
missed when backdated at/before its ordering key; restarting history
is the supported way to see the new source.

**Source-scope reconciliation for the already proposed one-commit
implementation** `feat(simulation): paginate protected manufacturer history`.
The proposed MODIFY list remains the five Simulation use-case/repository/
coordinator/service/JDBC paths, three focused existing Simulation tests,
`src/main/resources/application.properties` only for approved
opt-in settings and secret references, `.github/ci-test-inventory.json`
only when actual inventory changes, and this owning phase file.
Proposed CREATE paths remain the Simulation application cursor port,
Simulation infrastructure HMAC adapter, and its focused test, as
recorded immediately above. The existing
`ManufacturerCharacteristicRevisionCodec.java` needs **no mutation**
if its bounded canonical decode and `stored` verification can be
reused; there is no approved Party source change, new Flyway migration,
new SQL index, compiler-caller expansion, or platform budget change.
Where enforcing the current 500 ms budget and genuine test evidence
requires a different path, STOP for an exact scoped amendment.
No separate B1/B2/B3 split, Package C or downstream implementation
is admitted by this amendment. The owner's values/algorithm direction
are approved; the final Java package remains an independently
validated bounded next stage using its exact registered message.

**Acceptance must demonstrate:** authorized first page plus unauthorized
manufacturer/model only on a later page denies **everything**; forged
canonical payload whose SHA matches relational bytes but manufacturer/
model columns disagree is rejected before Party/page disclosure; 64
distinct pairs pass subject to real workload limits and 65 deny;
repeated model across >256 revisions does not silently exceed the
subject cap; no materialization of full payload history; forged/
tampered/foreign-company/expired/rotated-key cursor fails; same
microsecond/tie key sorting; concurrent revoked Party grants;
backdated/later inserts and repeat cursor under documented live semantics;
empty/denied uniformity, no protected existence or count leakage;
success only after physical commit and one final guard, rollback/
indeterminate commit, no broader SQL/roles or weakened Workflow
read-before-disclosure; existing SAVE/READ/QUALIFY/CERTIFICATE paths
unchanged. Real fresh-schema PostgreSQL/JUnit, architecture boundaries,
key rotation and exact-SHA Java/DB CI are mandatory. A skipped Docker
test is **not** PostgreSQL proof. ROADMAP/PHASES retain HAPI-601
NOT_IMPLEMENTED/BLOCKED; HAPI-602, C, P3/SITE, OT and deployment
remain out of scope.

### HAPI-601 — protected manufacturer history internal pagination implementation (2026-10-31; CI_PENDING)

**Registered execution.** The selected exact message is
`feat(simulation): paginate protected manufacturer history`; scope is
the previously owner-approved HAPI-601 internal backend prerequisite, not
the separate Package C REST/API authoring or HAPI-602. The preceding
security contract requires complete streaming canonical source validation
and source-wide Identity/Party authorization before any page metadata.
Current parent `cea9aedb455c0eba60e5b7bb5e9d9898fbd94076` has [Documentation Validation #782](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41179584463)
SUCCESS; the latest pre-implementation runtime CI [Full #1021](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41173608113)
ran on earlier code SHA `90f97740f37b2ce3ed41a81dcf1cfc3d35a1ac20`.
These results are not proof of this implementation. Exact new-SHA
Java/PostgreSQL CI must be created/started and observed separately.

**Scope ledger.** CREATE exactly:
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/ManufacturerCharacteristicHistoryCursorPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/security/HmacManufacturerCharacteristicHistoryCursorAdapter.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/security/HmacManufacturerCharacteristicHistoryCursorAdapterTest.java`

MODIFY exactly:
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/ManufacturerCharacteristicRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/ManufacturerCharacteristicRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/ProtectedManufacturerCharacteristicAdmissionCoordinator.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/ManufacturerCharacteristicRevisionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JdbcManufacturerCharacteristicRevisionRepositoryAdapter.java`
- `src/main/resources/application.properties`
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/ManufacturerCharacteristicRevisionApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/ManufacturerCharacteristicRevisionPostgresIntegrationTest.java`
- `.github/ci-test-inventory.json` (actual changed/new test inventories only)
- `doc/roadmap/TASKS_HPS-007.md` (append only)
- `doc/roadmap/ULTIMATE_TASKS.md` (compact pointer).

DELETE none. No change to Party or Identity implementations, Workflow,
existing Flyway migrations, physical execution budget, native privilege
configuration, other modules, canonical codec, ROADMAP/PHASES or
HAPI-601 Package C. The existing six-argument `Command`, existing
`execute` operations, and their auth contract remain unchanged.
The new `HistoryPageQuery`/`HistoryPageResult` are internal
use-case contracts; no controller or runtime OpenAPI route is claimed.

**Implementation design.** The new opt-in use-case validates the shared
HPS-023 page and cursor limits, checks a dedicated signing key, and
authenticates any continuation before entering protected source lookup.
Each request executes a new original protected transaction under the
existing compiled manufacturer service route and unchanged physical
final-guard restrictions. The JDBC adapter uses a forward-only fetch
batch of 32 and fully consumes every row for the exact company/source,
decoding canonical manufacturer payloads and checking SHA, relational
source/revision/model identities, document digest and canonical framing.
It retains at most 64 unique canonical manufacturer/model pairs and at
most `pageSize+1` **summary-only** candidates; a 65th distinct pair,
invalid payload, incomplete stream, overrun or denied Party tuple fails
the entire history before disclosure. A fresh Party owner ticket is
required for every unique subject, including those outside the requested
page, and all tickets participate in the original Identity/Party/source
relation fences and final scalar. Qualification flags are read only
for the admitted page. No certificate reference or source payload is
returned by paging.

The ordered continuation key uses the **persisted PostgreSQL** microsecond
timestamp and `revision_id`; SQL evaluates the tuple comparison using
the database's ordering semantics, not Java locale collation. Cursor
bytes are canonical URL-safe base64 of domain-separated HMAC-SHA256
signed version/scope/page selector/last key/issue-expiry/key ID.
Tokens expire in 900 seconds; current and optional previous keys are
distinct, rotation is verification-only for the previous key. A
missing current key denies only paging, not legacy endpoints.
The signing key must be independently provisioned as base64url
from `HIDRA_SIM_MANUFACTURER_HISTORY_CURSOR_CURRENT_KEY` (minimum
256-bit entropy); no embedded live secret. HPS-023 remains startup-bound,
not hot-reloaded. Backdated inserts at/before a previously returned
cursor may be missed until traversal restarts; there is no immutable
snapshot or engineering-revision certification guarantee.

**Proposed test / remaining proof.** Focused JUnit coverage includes
cursor round-trip, foreign company/source, tampering, key rotation,
expiry and unavailable-key denial; use-case preflight denial; and
synthetic fresh-schema PostgreSQL tests with >256 canonical revisions,
SQL tie pagination, a forged manufacturer column, and the 65th
distinct model. Original 500 ms overall protected work budget and
750 ms PostgreSQL statement timeout are unchanged. Source inspection
and synthetic tests cannot by themselves establish actual PostgreSQL
streaming/locking, full Party revocation races, bounded industrial
performance or SITE acceptance. The work must **not** be marked
accepted until applicable exact-SHA Full CI and genuine non-skipped
PostgreSQL tests demonstrate the new protected path. Any CI failure
enters `Fail` repair before roadmap advancement; qualified results
do not authorize Package C, HAPI-602 or deployment.

### HAPI-601 — Package C awaits trusted Platform protected-route registration (2026-10-31; DEPENDENCY REGISTERED)

The owner explicitly chose a reusable shared-security path instead of
HAPI-601 ownership of `HidraRouteAuthorizationInterceptor`.
At parent `f28dcb475b6a0a62f39791559f539024db468df9`, [Full CI #1022](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41180564343)
and [Docs #783](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41180564336)
passed. HPS-023 now registers the Platform-owned, independently trusted
native-protected HTTP route prerequisite; HPS-002 owns permission
meaning and Identity grants. The Package C candidate remains the
existing seven Simulation REST/request/response/mapper/advice/test
CREATE proposals and exact proposed message
`feat(simulation): expose supplied manufacturer characteristic authoring`.
These files are **not** yet accepted as sufficient or executed:
the REST route mappings, DTO/error/OpenAPI behavior and source-linked
HTTP tests still require separate exact technical admission and a
qualified Platform registration. No HAPI-601 module may self-exempt
the Platform's general route checks; no generic route permission can
replace current Identity `SAVE`, `READ_REVISION`,
`LIST_HISTORY`, `READ_CERTIFICATE` or `QUALIFY` native
authorization. Existing internal history source, HMAC cursor,
whole-source Party admission, original transaction and final guards
are unchanged by this documentation-only dependency registration.
HAPI-601 stays NOT_IMPLEMENTED/BLOCKED; HAPI-602, P3, SITE,
and deployment remain excluded.


### HAPI-601 — Package C blocked pending separately admitted Platform registration (2026-10-31)

Docs #784 succeeded at `180210d6bc9b9f173bc8ec8f0df7362684e69f37`. Owner Next selected
only the HPS-023 exact implementation-ledger preflight. Its candidate
six-CREATE/eight-MODIFY Platform source/test/evidence ledger and
proposed message `feat(platform): enforce trusted native-protected
route registration` are NOT source-authorized or implemented.
No native-protected manufacturer REST controller is registered.
The existing proposed seven Simulation REST/DTO/error/test CREATE
paths cannot alone authorize a new route; independent trusted Platform
compiled tuple, original Identity/Party/Workflow and transaction/final
guards, genuine HTTP and PostgreSQL nondisclosure proof are still
required. Neither an annotation nor a generic route permission
grants native manufacturer access. The internal protected history
cursor remains unchanged. HAPI-601 stays NOT_IMPLEMENTED/BLOCKED;
HAPI-602, P3, SITE and deployment remain excluded.


### HAPI-601 — Package C native REST and trusted Platform route technical contract (2026-10-31; OWNER-SELECTED REGISTRATION ONLY)

**Selection, authority and evidence.** Owner "Yes you do have my approval" selected preparation and registration of **one** exact-source Package C technical contract following successful HPS-023 route-guard CI, not immediate Java implementation, any new privilege, or whole HAPI-601 closure. Source main `583f8e2609a737c092b30e18f48d3c2f1c98517b`, parent `fd60ab6b5f23437dc7c2ed44c01b45bd632645cc`, tree `ccd0572b4ae9f069b0b77b509ced6c7d0596556d`; [Full CI #1023](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41191513815) and [Documentation #786](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41191513825) completed SUCCESS on this exact source. Full ran Java/architecture, API/dictionary and fourteen real PostgreSQL shards with green final gate; the separate serial Maven verification job was SKIPPED and release-eligibility is not inferred. This source has the protected application use case, owner/Party/Identity/Workflow transaction fences and internal page-history contract, and a compiled Platform registry/startup validator with **zero production trusted tuples**. No manufacturer REST controller or REST-native registration exists.

**Bounded context and owner contracts.** Simulation alone owns immutable manufacturer-supplied source revisions, native use-case orchestration and REST DTO mapping; Party supplies company/manufacturer/model evidence; Identity alone supplies native operation grants, DENY/revocation, current actor and original authentication binding; Workflow independently supplies terminal qualification evidence. Platform alone owns the immutable, startup-verified *compiled* HTTP route tuple registry. The marker `@NativeProtectedOperation`, route metadata, frontend capability or generic derived `simulation:manufacturer-characteristics:execute` is **never** a grant. Preserve the five existing native operations `SAVE`, `READ_REVISION`, `LIST_HISTORY`, `READ_CERTIFICATE`, `QUALIFY` and their existing protected original physical transaction, company/resource authorization and final guards. No cross-module private aggregate import, permission catalog rewrite, permanent session authorization, SCADA/OT action, simulator or installed-unit binding.

**Proposed exact V1 HTTP route-to-handler-to-native matrix — NOT IMPLEMENTED.** Base `/api/v1/simulation/manufacturer-characteristics`. `companyPartyId` is an untrusted selector and MUST be validated by native owner authority, never accepted as authorization.

| Verb and exact compiled MVC pattern | Proposed controller method | Native owner operation | Input / non-disclosing output |
|---|---|---|---|
| `PUT /{sourceId}/revisions/{revisionId}` | `save` | `SAVE` | Bounded JSON `Save(companyPartyId, revision)`; exact supplied source/revision must match path; committed digest/identity receipt, exact replay only under original matching bound authority |
| `GET /{sourceId}/revisions/{revisionId}` | `revision` | `READ_REVISION` | Required `companyPartyId` query; authorized typed V1 revision **without certificate evidence** |
| `GET /{sourceId}/history` | `history` | `LIST_HISTORY` via `pageHistory` only | Required `companyPartyId`, optional `size`/`cursor`; authorized summaries and optional `nextCursor`, **no total count** or unbounded legacy list |
| `GET /{sourceId}/revisions/{revisionId}/certificate` | `certificate` | `READ_CERTIFICATE` | Required `companyPartyId`; independently authorized documentary certificate reference (nullable and explicitly unverified), not a manufacturer-certification claim |
| `POST /{sourceId}/revisions/{revisionId}/qualification` | `qualify` | `QUALIFY` | `Qualify(companyPartyId, exact ApprovalReference)`; committed Workflow-backed qualification receipt, never automatic approval |

All routes above expand under the same base, are **proposed**, and need one explicitly compiled method/HTTP-verb/full-path tuple each. The old speculative `POST /api/v1/simulation/manufacturer-characteristics` is **not** a verified endpoint and is superseded **as a candidate** by immutable-key `PUT`; the historical proposal remains intact above. No public `current` endpoint is asserted because the existing use case does not expose a separate authoritative-current-head command. No native broad class/prefix trust or annotation-only registration. If the actual Spring MVC mapping has additional aliases, verbs, method overloads, or unregistered handler signatures, reject startup or stop to amend the ledger; do not silently trust them.

**DTO and representation contract.** `ManufacturerCharacteristicRevisionRequests` owns explicit Bean-validated request records and a discriminated, bounded V1 family representation for `COMPRESSOR_MAP`, `VALVE_MAP`, `PARAMETER_TABLE`, `OPERATING_LIMITS`; exact quantities/units/axes/reference conditions, source/document SHA-256, origin and genuinely supplied certificate metadata mirror the existing immutable domain constraints without exposing domain/JPA types as REST transport. `ManufacturerCharacteristicRevisionResponses` owns immutable response records for saved/qualified receipts, authorized revision/family, certificate-only view and live history page with `completedAt` and opaque `nextCursor`. `ManufacturerCharacteristicRevisionRestMapper` does only explicit DTO/domain conversion; it must not infer certification, Workflow approval, source provenance or permissions. `ManufacturerCharacteristicRevisionController` invokes the inbound `ManufacturerCharacteristicRevisionUseCase` exclusively, with no persistence/port-out access, and never returns any result before the original protected transaction completes. Existing `HistoryPageQuery/Result` and V1 domain codec are unchanged; common HPS-023 opt-in size bounds, per-source cap of 64 distinct principals, source-complete integrity/Party admission and dedicated rotating HMAC cursor remain authoritative. Missing signing key denies only paging; continuation is fresh live-keyset authorization, not a cross-request snapshot.

**Security and errors (truthful missing seam).** Require real authenticated Spring Security principal, exact compiled Platform trust and owner-native resource permission **before** any source existence, history metadata, counts, certificate or qualification disclosure. Unknown source, wrong company/model, revoked/DENY/INDETERMINATE and off-page forbidden manufacturer must produce uniform protected denial with no protected details. Invalid bounded syntax/typed payload maps to 400; unauthenticated to 401; protected denial to 403; 404 only if existence may be independently disclosed after authority, otherwise never; unavailable/precommit and indeterminate commit map to distinct non-disclosing 503 problem codes, with no blind retry for an unknown physical outcome. A 409 is permitted only for a **provably authorized exact conflict**. **Source gap:** current `ProtectedManufacturerCharacteristicAdmissionCoordinator` catches failures and returns `AccessDeniedException` for many semantic conflicts; a new REST advice cannot safely invent a 409 from that. An exact, independently tested typed failure path must preserve DENY/no-existence disclosure and protected rollback/replay semantics. If the allowed source modifications cannot prove that distinction, deliver safe 403/503 and keep 409 explicitly NOT_ESTABLISHED rather than weakening a guard. All status/problem shapes require configured HTTP tests and runtime OpenAPI verification.

**Maximum proposed one-commit Package C implementation ledger — TECHNICAL CANDIDATE, NOT OWNER-ADMITTED.** Exact existing target message: `feat(simulation): expose supplied manufacturer characteristic authoring`.

CREATE only (8):
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/ManufacturerCharacteristicRevisionController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/ManufacturerCharacteristicRevisionRequests.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/ManufacturerCharacteristicRevisionResponses.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/mapper/ManufacturerCharacteristicRevisionRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/ManufacturerCharacteristicRevisionApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/model/ManufacturerCharacteristicRevisionFailure.java` (only for source-justified typed outcomes)
- `src/test/java/dz/sh/hidra/modules/simulation/api/rest/controller/ManufacturerCharacteristicRevisionControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/qualification/ManufacturerCharacteristicOperatorQualificationTest.java`

MODIFY only (11):
- `src/main/java/dz/sh/hidra/platform/permissions/HidraTrustedNativeProtectedRouteRegistry.java` — add exactly five immutable Platform-owned, fully signature-qualified route tuples; keep sole public zero-argument constructor and no module source dependency.
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/ProtectedManufacturerCharacteristicAdmissionCoordinator.java` and `src/main/java/dz/sh/hidra/modules/simulation/application/service/ManufacturerCharacteristicRevisionApplicationService.java` — **only if necessary** for typed error proof; no change to protected SQL/final guard/authorization/commit ordering.
- `src/test/java/dz/sh/hidra/platform/permissions/HidraTrustedNativeProtectedRouteRegistryTest.java`
- `src/test/java/dz/sh/hidra/platform/permissions/HidraTrustedNativeProtectedRouteStartupValidatorTest.java`
- `src/test/java/dz/sh/hidra/platform/permissions/HidraTrustedNativeProtectedRouteHttpTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/ManufacturerCharacteristicRevisionPostgresIntegrationTest.java`
- `.github/ci-test-inventory.json` — source-hashed exact added/changed test classes and method identities only.
- `doc/api/openapi.yaml` — **only from verified configured-runtime generated artifact**, preserve backward compatibility and schema.
- `doc/roadmap/TASKS_HPS-007.md` — append actual bounded implementation/CI evidence.
- `doc/roadmap/ULTIMATE_TASKS.md` — concise pointer only.

DELETE none; READ-ONLY: `HidraRouteAuthorizationInterceptor.java`, `HidraRoutePermissionCatalogService.java`, `HidraTrustedNativeProtectedRouteStartupValidator.java`, `NativeProtectedOperation.java`, Identity/Party/Workflow owner code, `ProtectedTransactionExecutor.java`, Flyway migrations, physical dictionary unless actual schema drift, security configuration, all other modules, frozen `ULTIMATE_ROADMAP.md`/`ULTIMATE_PHASES.md`. The existing architecture test forbids direct Platform-to-business-module class dependency; the registry must resolve a **Platform-owned fixed source literal** to exact controller/method parameter signature through fail-closed reflection or an equivalently verified source-owned mechanism, never a business self-registration hook. Direct Java import of the Simulation controller by Platform violates the accepted guardrail. If this cannot be done within the stated paths, STOP for another explicit owner-reviewed amendment rather than weaken architecture tests.

**Acceptance / negative tests required before declaring C implemented.** Every exact handler and operation, wrong method/route/overload, annotation-only, missing registry, startup ambiguity, unknown/anonymous/expired JWT, native DENY and revocation during original transaction, foreign company, arbitrary manufacturer/model, certificate read through ordinary revision, off-page source mixing and cursor tampering, absent/rotated key, more than 256 history rows and 65th principal, exact replay/conflict and typed 409 only after authority, genuine Workflow terminal approval/negative replay, physical PostgreSQL rollback/one final scalar, unknown commit `INDETERMINATE` without invented success, protected metadata/count non-disclosure, existing nine native owner families, generic permission fallback and architecture strictness. Preserve exact raw test inventory; run focused Java/MockMvc and real non-skipped PostgreSQL, fresh schema and exact-SHA Full/Docs, same-run API/catalog compatibility. Source inspection or a green prior Platform CI is not Package C proof. No P3 numerical, installed-unit HAPI-602, production/SITE, OT actuation, HidraWEB or deployment selection.

**This selected registration stage.** Exact documentation commit message `docs(simulation): admit manufacturer characteristic package C technical contract`; CREATE/DELETE zero, MODIFY **only** `doc/roadmap/TASKS_HPS-007.md` (append before its retained payload-end marker) and `doc/roadmap/ULTIMATE_TASKS.md` (one concise current-execution pointer). Preserve previous text and frozen states. This records a source-backed candidate after user authorization to prepare/register, **not final approval of these newly specified exact route signatures, the 8 CREATE/11 MODIFY implementation ledger, or implementation itself**. Next gate is the owner's specific final acceptance of this full contract; only a subsequent independently selected `Next` may execute the one implementation commit. Publish guarded main, check exact parent/tree/blobs and observe new-SHA Documentation Validation startup, then STOP.


### HAPI-601 — Package C source implementation submission (2026-10-31; CI PENDING)

**Owner admission and exact source.** The owner subsequently stated "I approve" specifically in answer to final acceptance of the registered HAPI-601 Package C five-route, bounded 8-CREATE/11-MODIFY/0-DELETE technical implementation contract. It is a single source stage, not acceptance of production use or further stages. Parent `517b10d947e383d7162e26a5a9b6284be868eeee`, tree `ccfed8365008415bb1fc302f3289bf78144aad0d`, Documentation Validation [#787](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41193548809) SUCCESS; baseline Full [#1023](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41191513815) succeeded only for the earlier runtime SHA. Exact single-stage publication message: `feat(simulation): expose supplied manufacturer characteristic authoring`.

**Bounded source submission, 7 CREATE / 7 MODIFY / 0 DELETE.** CREATE:
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/ManufacturerCharacteristicRevisionController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/ManufacturerCharacteristicRevisionRequests.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/ManufacturerCharacteristicRevisionResponses.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/mapper/ManufacturerCharacteristicRevisionRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/ManufacturerCharacteristicRevisionApiExceptionHandler.java`
- `src/test/java/dz/sh/hidra/modules/simulation/api/rest/controller/ManufacturerCharacteristicRevisionControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/qualification/ManufacturerCharacteristicOperatorQualificationTest.java`

MODIFY:
- `src/main/java/dz/sh/hidra/platform/permissions/HidraTrustedNativeProtectedRouteRegistry.java`
- `src/test/java/dz/sh/hidra/platform/permissions/HidraTrustedNativeProtectedRouteRegistryTest.java`
- `src/test/java/dz/sh/hidra/platform/permissions/HidraTrustedNativeProtectedRouteStartupValidatorTest.java`
- `src/test/java/dz/sh/hidra/platform/permissions/HidraTrustedNativeProtectedRouteHttpTest.java`
- `.github/ci-test-inventory.json`
- `doc/roadmap/TASKS_HPS-007.md` (this append)
- `doc/roadmap/ULTIMATE_TASKS.md` (execution pointer only).

The submitted code binds five exact `@NativeProtectedOperation` controller handlers through **independently Platform-owned, fixed reflective signatures**, startup matching and an immutable tuple list; Platform never imports private Simulation source classes and modules cannot self-register. Requests explicitly map the four discriminated supplied families, signed SHA/documentary data, exact immutable path identity, Workflow approval reference and bounded source-history query to the existing inbound Simulation use case; no persistence entity is a REST response. Separate certificate read, no total history count, source-scoped live HMAC continuation and exact company/native grants preserve the original authorization and transaction/final-guard owner. JWT authentication is required by configured infrastructure and independently checked by handler; no generic route permission is treated as a grant.

**Intentional, source-supported exceptions.** The previously proposed optional `ManufacturerCharacteristicRevisionFailure.java` and two application-service/admission-coordinator modifications were **not** selected because the protected coordinator collapses unproven semantic conflict distinctions to denial. A 409 is NOT_ESTABLISHED; 403 is nondisclosing denial and 503 distinguishes unavailability from an indeterminate physical outcome, with no blind retry advice. No 404 is manufactured for a hidden/denied revision. The pre-existing PostgreSQL integration test is preserved unchanged; no Flyway/schema, dictionary, generic security interceptor, Party/Identity/Workflow, engine/OT, SITE or application configuration changes. The `doc/api/openapi.yaml` snapshot is deliberately **not** hand-written: only a genuine configured runtime snapshot may update it after source-linked validation.

**Test and acceptance state.** Two new synthetic JVM test classes (5 controller and 3 qualification methods) and the three previously registered Platform JVM test class source hashes and changed method identities are recorded in `.github/ci-test-inventory.json`. Existing PostgreSQL source integrity, >256 revision paging and 65th manufacturer negative fixtures remain. Java 21 compiler exists only in an isolated container here; the repository Maven dependencies/runtime and Docker/PostgreSQL service were **not locally executed**. Source/blob and method/registry shape checks do not establish compilation, configured JWT/MockMvc security integration, real PostgreSQL scope, source commit/replay/rollback under HTTP, dynamic OpenAPI or documentation snapshot equivalence. Exact-SHA Full CI and Docs CI on the *new* commit, zero skipped applicable PostgreSQL checks, compiled Architecture/Platform startup and controller tests, runtime OpenAPI, error semantical acceptance and owner final HAPI-601 task reconciliation remain gates. **Source submitted / CI pending, not CI-accepted, engineering accepted, SITE accepted or deployed.** HAPI-602 and P3 remain unselected. If the new CI fails, repair this bounded stage on `Fail`, without advancing.

### HAPI-601 — CI-first failure and generated artifact repair (2026-10-31; acceptance pending)

Full [#1024](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41202837794) exposed HRA-102 REST domain representation leakage and canonical OpenAPI drift. Guarded `fix(simulation): restore manufacturer REST domain isolation` at `07af36e5061a8b300814cc396e8c0c2cdb9cee8f` changed only the Simulation REST request DTO and mapper; Full [#1025](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41203839131) confirms unit/architecture, static and fourteen PostgreSQL partitions SUCCESS. API-contract equality still FAILED, so its dictionary/compatibility stages and Full evidence gate did not pass.

The current owner `Fail` selects an artifact-only reconciliation using the exact generated diagnostic artifact `13043436381` (generation step succeeded, job/workflow FAILED): `doc/api/openapi.yaml`, `.github/documentation-validation.json`, `doc/api/OPENAPI_GOVERNANCE.md`, this append-only HPS-007 record, and one concise `ULTIMATE_TASKS.md` pointer. Exact commit message: `docs(simulation): reconcile manufacturer characteristic OpenAPI snapshot`. Manifest hash/counts and generation-step-only provenance are retained; no Java, test inventory, CI workflow, Flyway, physical dictionary, permission policy, existing HPS states or frozen ROADMAP/PHASES are changed. Required next gate is exact new-SHA CI runtime equality, real dictionary and successful-base compatibility followed by independent Package C acceptance. HAPI-601 stays IN PROGRESS / NOT ACCEPTED; HAPI-602 and P3 remain unselected.

### HAPI-601 — Owner-approved registration of Package C native HTTP/security qualification amendment (2026-10-31; REGISTRATION ONLY)

**Owner selection and exact-source review.** After a direct recommendation to *register* bounded HAPI-601 Package C HTTP/security qualification tests, the owner answered **"Approved"**. This selects only the documentation registration and test-contract definition below, **not immediate test source changes, completion of A/B/C, or HAPI-602**. Live main at selection: `b71ee1496cdff45a1bd99da3818a2e6920171b6d` (parent `07af36e5061a8b300814cc396e8c0c2cdb9cee8f`, tree `9fc2dac3802ad00fab8d8d556d2f6612fed6d5c8`). Exact-SHA [HidraAPI Full #1026](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41207778805) and [Documentation #789](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41207778975) both **completed SUCCESS**. Full #1026 passed static, Java 21 unit/architecture (403 classes, 2,371 tests, zero failure/error/skip), API contract/dictionary, all **14** real PostgreSQL shard jobs, and Full Verification Gate. Its separate optional `Java 21 Maven verification` job was **SKIPPED** by workflow design; do not call that job run. API-lane log confirms exact canonical runtime OpenAPI equality and backward compatibility against genuine successful base `583f8e2609a737c092b30e18f48d3c2f1c98517b`; accepted evidence bundle is [artifact 13044923390](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/41207778805/artifacts/13044923390). These checks verify the current software subset only, not SITE, operational or engineering calibration.

**Source-backed security/qualification gap.** The five real manufacturer methods on `ManufacturerCharacteristicRevisionController` route immutable `SAVE`, `READ_REVISION`, paginated `LIST_HISTORY`, independently permitted `READ_CERTIFICATE`, and Workflow-backed `QUALIFY` through `ManufacturerCharacteristicRevisionUseCase`. Platform's `HidraTrustedNativeProtectedRouteRegistry` verifies five exact compiled signatures/verbs/paths and never grants native owner permissions. Current `ManufacturerCharacteristicRevisionControllerTest` is **five** standalone MockMvc/direct controller tests with a mocked inbound port; `ManufacturerCharacteristicOperatorQualificationTest` is **three** mocked-port tests. `HidraTrustedNativeProtectedRouteHttpTest` exercises an independent synthetic protected Platform route, **not** all five actual manufacturer routes with real Identity/Party/Workflow. Existing `ManufacturerCharacteristicRevisionPostgresIntegrationTest` checks actual fresh PostgreSQL source/lock/digest/history constraints, including >256 revisions and 65 distinct principals, but not the configured HTTP-to-original-transaction security chain. Therefore the green aggregate Full CI cannot by itself establish every registered Package C adversarial acceptance case. Existing working precedents: `SuppliedFluidPropertyNativeHttpPostgresIntegrationTest` and `ProtectedInstalledUnitLifecyclePostgresIntegrationTest` use configured Spring security filters, genuine native issuer/session evidence and fresh PostgreSQL with synthetic source fixtures; reuse their test *patterns*, not their private module internals.

**Bounded module and authority.** Simulation owns the HTTP-to-native manufacturer acceptance, domain invariants, current-source/history and transaction audit; Identity grants and revokes exact company/resource/operation-scoped permission with DENY precedence and original LOCAL/LDAP/OIDC authentication/one-operation binding; Party supplies exact manufacturer/model eligibility; Workflow independently supplies terminal target/digest/approval evidence. Platform exclusively owns compiled route tuple validation and generic fallback. No test may fake native owner authorization by `@WithMockUser`, standalone MockMvc alone, an annotation, manually authored JWT security claims, imported protected cross-module internals, open SQL role, a test bypass of original transaction, precommitted approval, or a generic `simulation:manufacturer-characteristics:execute` grant. Synthetic approved fixtures and native test-supported source identity flows only; no live IdP/SCADA, engineering vendor measurements or numerical P3.

**Proposed one-commit qualification-test implementation — TECHNICAL CANDIDATE, NOT YET OWNER-ADMITTED.** Exact proposed future message `test(simulation): qualify manufacturer native HTTP security`. **Maximum** CREATE **1**, MODIFY **7**, DELETE **0**; no new product services, migrations, permissions, security routes, OpenAPI shape, engine or frontend code:
- CREATE `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/ManufacturerCharacteristicNativeHttpPostgresIntegrationTest.java` — one real `@SpringBootTest` configured security-filter/MockMvc/native issuer and fresh Flyway/PostgreSQL fixture; only source-justified `@MockitoBean` for external *transport*, never native Identity/Party/Workflow business authority.
- MODIFY `src/test/java/dz/sh/hidra/modules/simulation/api/rest/controller/ManufacturerCharacteristicRevisionControllerTest.java` — fixed transport validation, routing, independent certificate/error redaction tests; keep standalone cases explicitly transport-only.
- MODIFY `src/test/java/dz/sh/hidra/modules/simulation/qualification/ManufacturerCharacteristicOperatorQualificationTest.java` — independent Workflow reference/error regression; mock-only cases still explicitly not terminal-approval proof.
- MODIFY `src/test/java/dz/sh/hidra/platform/permissions/HidraTrustedNativeProtectedRouteHttpTest.java` — prove no annotation-only delegation, verb/route/overload drift and generic fallback with actual registered manufacturer handlers, without importing private owner authority.
- MODIFY `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/ManufacturerCharacteristicRevisionPostgresIntegrationTest.java` — **only if** a distinct physical negative/replay case cannot be covered by the new integrated test; retain existing five tests and greenfield policy.
- MODIFY `.github/ci-test-inventory.json` — exact new/changed test class, source SHA-256, method names, real service lane **postgres-integration** for the integrated class, no synthetic DB-skipping represented as proof, preserve 14-shard balance/classifier.
- MODIFY `doc/roadmap/TASKS_HPS-007.md` — append actual source/evidence/limitations.
- MODIFY `doc/roadmap/ULTIMATE_TASKS.md` — one compact execution pointer only.

**Required independent acceptance matrix for the candidate source implementation:**
1. The **five exact** HTTP method/route signatures dispatch through the configured Spring Security filter and exact startup-validated Platform tuple into the original protected use case; anonymous/missing/malformed/expired bearer is real 401 and owner use case/SQL is not entered. Wrong verb/sibling/unregistered marker cannot borrow trusted native status.
2. Verified native actor/session/operation binding, current resource/company permission and Party exact manufacturer/model are checked before source existence, response, history count, next cursor, certificate or qualification. DENY precedence, revoked native grants and company retirement deny current and replay requests; mixed manufacturer/model same-source history is completely refused, including off-page rows.
3. A synthetic authorized revision persists with canonical digest, exact creator/backend XID/role and immutable source; valid same-identity replay is idempotent, different payload on same immutable revision does not mutate source. Rejected or revoked writes and Workflow failure leave source/qualification/receipt rows unchanged after original transaction rollback. Cross-company same-source identifiers disclose no source existence, private error details, counts or history metadata.
4. Live history uses the registered platform pagination policy, signed dedicated HMAC cursor and fresh authorization each page. Reject tampered/foreign/replayed-across-scope cursor and absent/rotated keys fail-closed; exercise >256 revisions and 65th distinct manufacturer-model independently, no unbounded fallback, acknowledge late/backdated insert is *not* snapshot isolation.
5. Certificate metadata is **unverified documentary source evidence**; ordinary revision cannot expose certificate. Workflow terminal qualification demands actual independent exact target/company/digest/instance/task/action evidence and no approval laundering or fabricated user/signature.
6. Configured HTTP 400/401/403/503 with stable non-disclosing ProblemDetail codes: no 404 for a hidden denied source, no `409` for an unproved authorized conflict. Real `ProtectedTransactionExecutor.Outcome.INDETERMINATE` remains non-retryable 503; do not simulate successful commit or casually remap unknown outcome. Zero successful output before commit/final fences.
7. Existing nine registered native owner families, Platform boot validation, DTO/domain isolation and no generic security regression. Prove exact new test inventory membership, zero failures/errors/skips in all relevant JVM and **real** PostgreSQL classes, API/snapshot/catalog invariance (no runtime API drift), Full/Docs at the actual new SHA and same-run evidence. Source inspection, standalone mocks, Docker skip and green unrelated CI are insufficient.

**Admission, stop and remaining acceptance.** This documentation registration does **not** select or implement the candidate test source. Exact documentation commit message `docs(simulation): register manufacturer HTTP security qualification amendment`, MODIFY **only** `doc/roadmap/TASKS_HPS-007.md` (append before payload-end marker) and `doc/roadmap/ULTIMATE_TASKS.md` (pointer); CREATE/DELETE none. Preserve all earlier task history, legacy HPR-P25-054C mapping, roadmap/phase status cells, reconciliation, security configuration and immutable Flyway/SQL. After guarded `main` publication, verify exact parent/tree/blobs and that Documentation Validation was created/queued/started, then STOP. **Next gate:** separately accept the exact above maximum test source ledger under the owner-approved HAPI-601 technical-review procedure before implementation. Final HAPI-601 software acceptance still requires whole A/B/C verified evidence and a separate status-only acceptance stage. HAPI-602, numerical P3, SITE, operational signoff, production and deployment remain unselected.


<!-- task-register:payload:end -->
