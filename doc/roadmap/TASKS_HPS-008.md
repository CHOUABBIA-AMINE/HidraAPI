# HPS-008 — Products and Fluid Properties: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-008 — Products and Fluid Properties

<a id="hapi-532"></a>
## HAPI-532 — define station and installed unit provisioning

Phase: HPS-008 — Products and Fluid Properties

Legacy IDs: HPR-P25-020

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

define station and installed unit provisioning

### 2. Existing Implementation

Custody product references and bounded immutable gas-fluid revision/qualification contracts exist internally. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Original independent acceptance; design is not implementation; F1/F3 actual cases

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. Preserve gas-first behavior; metadata for liquids does not establish thermodynamic or hydraulic qualification. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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
#### HPR-P25-020 — Independent installed units and station hydraulic configurations

Objective/justification: represent three stations with nine individually configured
units and real headers/series/parallel incidence without reducing a station to one
compressor. Evidence: T01/T11/T15–T20 and A01–A05. Owners/contracts: Topology hydraulic
identity/configuration; Assets maintainable/model/lifecycle linkage; Party manufacturer
existence; Simulation read-consumer. Functional/data: unit identity, facility, type,
manufacturer/model, installation/retirement, source/limits/characteristic references;
station header/internal path state and effective configuration; typed attribute schemas
with dimensional/required-value semantics. API: exact authorized station/unit and
attribute definition/value authoring/read/revision contracts; no owner bypass.
Persistence: reconcile existing tables first, version only missing config, enforce typed
values and identity/reference constraints atomically. Acceptance: Case A independent-unit
round-trip and I replacements/history; reject duplicate identity, wrong family/model,
invalid lifecycle, dangling hydraulic endpoints, required-attribute/unit errors and
conflicting concurrent changes. Numerical acceptance: preserved oriented edge identities
and supported series/parallel/isolated state preflight, not industrial machine equations.
D/H/N; exclude duplicating maintainable assets or altering existing Assets lifecycle policy.

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

- `dc01eeb3d8a2ea2fe0115a589948dd1eb8f77d67` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:12860](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12860)
- [doc/model-remediation/RECONCILIATION.md:12907](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12907)
- [doc/model-remediation/RECONCILIATION.md:13672](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13672)
- [doc/model-remediation/RECONCILIATION.md:14265](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L14265)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6111](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6111)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14600](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14600)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14647](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14647)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15412](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15412)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:16005](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L16005)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: DESIGN COMPLETE.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14600 | HPR-P25-020 | Station/unit/attribute provisioning preflight / Topology and Assets | 018 | docs(topology): define station and installed unit provisioning | PLANNED — NOT SELECTED
doc/model-remediation/RECONCILIATION.md:12860 | HPR-P25-020 | Station/unit/attribute provisioning preflight / Topology and Assets | 018 | docs(topology): define station and installed unit provisioning | PLANNED — NOT SELECTED
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15412 | HPR-P25-020 | Registered in prior amendment; PLANNED — NOT SELECTED | FOUNDATION_ACTIVE | Cross-module engineering governance | 003/004: specific independent owner contracts, not all 002; 018: amendment docs CI + owner selection; 019/020: 018 | Original independent visualization/measurement or engineering provisioning objective | Any solver use remains outside this independent scope | Original independent acceptance; design is not implementation; F1/F3 actual cases | NOT SELECTED | Own bounded preflight/implementation after prerequisites
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:16005 | HPR-P25-020 | PLANNED — NOT SELECTED; next recommended preflight | Owner next instruction after 019 documentation acceptance; refresh station/unit/source/ownership contracts
doc/roadmap/ULTIMATE_ROADMAP.md:6111 | HPR-P25-020 | DESIGN COMPLETE | Future runtime assigned 050–058; design is not executable completion
doc/model-remediation/RECONCILIATION.md:13672 | HPR-P25-020 | Registered in prior amendment; PLANNED — NOT SELECTED | FOUNDATION_ACTIVE | Cross-module engineering governance | 003/004: specific independent owner contracts, not all 002; 018: amendment docs CI + owner selection; 019/020: 018 | Original independent visualization/measurement or engineering provisioning objective | Any solver use remains outside this independent scope | Original independent acceptance; design is not implementation; F1/F3 actual cases | NOT SELECTED | Own bounded preflight/implementation after prerequisites
doc/model-remediation/RECONCILIATION.md:14265 | HPR-P25-020 | PLANNED — NOT SELECTED; next recommended preflight | Owner next instruction after 019 documentation acceptance; refresh station/unit/source/ownership contracts
~~~~~~~~

<a id="hapi-593"></a>
## HAPI-593 — Integrated closure: four product/composition/method packages

Phase: HPS-008 — Products and Fluid Properties

Legacy IDs: HPR-P25-053

Priority: P1

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `custody` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-597

Cross-Repository Dependencies: HWEB-F06 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-053; child tasks own implementation. four product/composition/method packages

### 2. Existing Implementation

Custody product references and bounded immutable gas-fluid revision/qualification contracts exist internally. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/SpringCustodyController.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyGasFluidRevision.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver company-owned product/property/method drafts, exact composition/basis/units and published revision/history contracts. The exact bounded remaining delivery is: Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-053; child tasks own implementation. four product/composition/method packages.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern product identity, composition, supplied properties, methods and reference bases.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `custody`; primary phase owns this obligation. Preserve gas-first behavior; metadata for liquids does not establish thermodynamic or hydraulic qualification. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyGasFluidRevision.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`, `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`, `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/SpringCustodyController.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyGasFluidRevision.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-053 — four product/composition/method packages

Prerequisites:051, retained018/022 design and D3's supported supplied-metadata/owner
contract. This is Custody-owned engineering data, not new fluid computation. Existing
GAS exact sum/codec/history and qualified use retain their original meaning; do not
loosen a GAS type to claim qualified H2/liquid/arbitrary-mixture physics.

| Planning stage | Remaining delivery | Evidence / exit criterion |
|---|---|---|
| HPR-P25-053A | Company-owned product identity/classification and versioned metadata drafts. | Actual authorized create/read/revise, explicit completeness and supported representation, two-company isolation; no invented properties or source. |
| HPR-P25-053B | Supplied composition/property/reference-basis/units/method applicability and evidence. | Exact supplied-fact round trip; invalid fraction/basis/unit/method/absent evidence rejected or incomplete. Preserve old GAS behavior; no EOS, normalization or solver. |
| HPR-P25-053C | Governed publish/withdrawal/current/history/effective selection and scalar owner exports. | Immutable revisions/digests/source/approval preserved, current eligibility separate from historical access, concurrency/replay/rollback; usable downstream source authoring without055. |
| HPR-P25-053D | Multi-product owner/API/compatibility qualification and consumer-ready contract. | Explicit synthetic supported definitions, old GAS exact replay, denied read/publish/export, unsupported shapes and source corruption; exact API/dictionary/full evidence, no numerical qualification claim. |

053 can progress after its own051/D3 prerequisites independently of052 implementation.
Frontend product editing consumes only accepted exact DTO/permission contracts.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern product identity, composition, supplied properties, methods and reference bases.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-597. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23368](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23368)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1306](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1306)
- [doc/roadmap/ULTIMATE_ROADMAP.md:5128](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L5128)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-594"></a>
## HAPI-594 — Product identity and metadata drafts

Phase: HPS-008 — Products and Fluid Properties

Legacy IDs: HPR-P25-053A

Priority: P1

Implementation Status: NOT_IMPLEMENTED

Planning Status: IN_PROGRESS

Owning Module: `custody` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-567

Cross-Repository Dependencies: HWEB-F06 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Company-owned product identity/classification and versioned metadata drafts.

### 2. Existing Implementation

Custody product references and bounded immutable gas-fluid revision/qualification contracts exist internally. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/SpringCustodyController.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/PlanningProductReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/simulation/SimulationProductCandidateContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapterTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver company-owned product/property/method drafts, exact composition/basis/units and published revision/history contracts. The exact bounded remaining delivery is: Company-owned product identity/classification and versioned metadata drafts..

### 4. Functional Requirements

Actual authorized create/read/revise, explicit completeness and supported representation, two-company isolation; no invented properties or source.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `custody`; primary phase owns this obligation. Preserve gas-first behavior; metadata for liquids does not establish thermodynamic or hydraulic qualification. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`, `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`, `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapter.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapter.java`, `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapterTest.java`, `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapterTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/custody/domain/model/ProductEngineeringDraft.java` — pure Java owned aggregate/value and invariants for company-owned product identity and draft completeness without duplicating custody catalog ownership.
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/ProductEngineeringDraftUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/ProductEngineeringDraftRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/custody/application/service/ProductEngineeringDraftApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/ProductEngineeringDraftController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JdbcProductEngineeringDraftRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/SpringCustodyController.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/PlanningProductReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/simulation/SimulationProductCandidateContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapterTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/custody/engineering-products` for company-owned product identity and draft completeness without duplicating custody catalog ownership. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapterTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapterTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Actual authorized create/read/revise, explicit completeness and supported representation, two-company isolation; no invented properties or source.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-567. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23377](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23377)
- [doc/model-remediation/RECONCILIATION.md:23703](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23703)
- [doc/model-remediation/RECONCILIATION.md:23740](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23740)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1011](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1011)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1048](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1048)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1315](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1315)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1011 | HPR-P25-053A | Select supported metadata classifications and whether company-local products reference or extend existing shared catalog identity. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/roadmap/ULTIMATE_ROADMAP.md:1048 | HPR-P25-053A | REQUIRES_OWNER_DECISION | Product identity and metadata drafts
doc/roadmap/ULTIMATE_ROADMAP.md:1315 | HPR-P25-053A | Company-owned product identity/classification and versioned metadata drafts. | Actual authorized create/read/revise, explicit completeness and supported representation, two-company isolation; no invented properties or source.
doc/model-remediation/RECONCILIATION.md:23377 | HPR-P25-053A | Company-owned product identity/classification and versioned metadata drafts. | Actual authorized create/read/revise, explicit completeness and supported representation, two-company isolation; no invented properties or source.
doc/model-remediation/RECONCILIATION.md:23703 | HPR-P25-053A | Select supported metadata classifications and whether company-local products reference or extend existing shared catalog identity. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/model-remediation/RECONCILIATION.md:23740 | HPR-P25-053A | REQUIRES_OWNER_DECISION | Product identity and metadata drafts
~~~~~~~~

### HAPI-594 source-backed product identity policy proposal — 2026-10-26

Owner Next after successful topology closure Docs607 run40384546960 selects
HAPI-594/HPR-P25-053A source recovery and a concrete policy proposal only.
Current main3bf68ab45e6e5cdd26146bf31dc331d52a44dc02; runtime remains the successful
Full908/Fast214 sourceef075097142df9e665bc89eb3b2bd4e6b2c9bc17. HAPI-586 and its
six children are accepted within their own scopes. HAPI-567, this task's actual
registered prerequisite, is VERIFIED/COMPLETED. No dependency completion resolves
the independently recorded product policy choice by itself.

Exact preflight message:
`docs(custody): propose product draft ownership policy`.
Zero CREATE/two MODIFY/zero DELETE: only append this HAPI-594 proposal/evidence
and a compact current index pointer. Preserve NOT_IMPLEMENTED/BLOCKED in this
header and both frozen registries. There is no executable registration, runtime
acceptance or inferred approval in this commit. Six proposed ProductEngineeringDraft
source paths remain absent in the exact live tree; do not manufacture an
implementation or reuse a stale source-review statement as a passing workflow.

**Existing unresolved owner gate.** The original record explicitly says:
"Select supported metadata classifications and whether company-local products
reference or extend existing shared catalog identity."
The same HPR-P25-053A REQUIRES_OWNER_DECISION remains in live RECONCILIATION and
the owner-decision register. Retrieved prior context confirms Custody product
ownership, Telemetry unit ownership and Party counterparty ownership, but no
explicit approval of this company-local classification/identity choice was found.
General gas-first direction and generic Next are not silently substituted for
that business decision. No deployment measurements, real catalog seeds or
calibration values are required to approve the supported semantics below.

**Current source facts recovered, not proposed capabilities.**
- CustodyCatalogEntryJpaEntity maps existing shared hidra_custody_catalog_entry:
  opaque80-character identity, catalog_name, code, active/system_defined,
  ordering and creation/update timestamps. It contains no company owner or
  company-local product draft/version lifecycle. Catalog strings alone do not
  prove approved product classifications or shared-catalog extension policy.
- CustodyCatalogEntryJpaRepository exposes locked findByIdForShare. The current
  PlanningProductReferenceQueryAdapter requires an actual active row in
  hidra_custody_planning_product_policy as well as catalog activity; migration
  V20261008_025 creates empty owner-approved policy metadata, not product seeds.
  Preserve that nomination-specific approval meaning; a draft must not grant it.
- SimulationProductCandidateQueryAdapter returns the exact existing catalog
  id/name/code/activity/timestamps without adopting Planning approval or making
  thermodynamic qualification. Neither existing consumer accepts a new local
  draft UUID as a substitute for the existing shared catalog identity.
- CustodyGasFluidRevision currently admits ProductKind.GAS and GAS_MOLE_FRACTION
  only, explicit exact supplied fractions/method ranges/governance evidence and
  immutable product snapshots. CustodyGasFluidQualificationService reattests the
  actual current product snapshot and exact final Workflow approval. A metadata
  label cannot normalize fractions, invent properties, qualify a liquid model
  or replace that evidence. Preserve these internal gas contracts unchanged.
- CustodyTransferTicketActorReferenceContract exposes only eligible(actorId,at).
  That actor reference is not atomic current company/product draft authorization.
  Existing protected Topology contracts are typed for Topology resource scopes;
  do not pass product IDs as network/facility IDs or borrow their native family.
  A later admitted execution contract must specify Identity-owned product scope,
  fresh native authority and fail-closed Party eligibility inside the real owner
  transaction. An isolated controller permission precheck is not sufficient.

Recovered exact-source file fingerprints:

| Existing source path | SHA256 |
|---|---|
|src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyCatalogEntryJpaEntity.java|4e1572254a2ab55e5b3c8193fefaeebd1db66af78178e3de8f5a479cb35d957f|
|src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyCatalogEntryJpaRepository.java|0baf4eee1e45474b95e58bb628c2cf13c211b60668326339bfd23eaa2405370d|
|src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapter.java|687dc07443b959864bfb4bd094a3043e7847c5b43f51d5ff2d8621cf953ad475|
|src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapter.java|0dc1a429bef501a3035f6e9fa843c1cc8a8c051aa3119e99210297838ee3c6be|
|src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyGasFluidRevision.java|d103b5c72f44595e0faf618b13f871c95a66ac92eaea68c363e0c940b1aa53af|
|src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java|d7871364531ee5ce415e33f7a3157220e0cf91261969d46dadcbcf81dc6bc04d|
|src/main/resources/db/migration/V20261008_025__hmr_080_nomination_owner_reference_policies.sql|11f6cc0915cfc70784827fd8cce62e14abb9878c59b1fa1bc5d46ba0e83b2fb3|
|src/main/java/dz/sh/hidra/modules/identity/application/contract/custody/CustodyTransferTicketActorReferenceContract.java|204f091bafea0d46572e71f71aaa450036caa08919e2a36e4aa400424207a42e|

**Concrete recommended policy A — proposed, awaiting owner selection.**
1. Custody remains the sole product-identity/type owner. Company-owned engineering
   drafts have their own stable local identity/code and versioned authored
   metadata, but reference an explicitly approved existing active Custody catalog
   product. They do not create/fork/rename global catalog entries, mutate shared
   product types or pretend to be a separate enterprise product master.
2. Support explicitly declared GAS and LIQUID as representational metadata labels
   only. An absent classification remains absent/UNSPECIFIED with incomplete
   diagnostics; it is never inferred from a product name/code or defaulted to
   GAS. These labels are not phase-equilibrium results, approved compositions,
   standards classifications or thermodynamic/hydraulic eligibility. Existing
   gas-first numerical/qualification contracts remain GAS-only and unchanged.
3. Keep authored company-local code/name/description and source/evidence
   references separate from the shared catalog snapshot. Candidate mandatory
   identity facts are company, local code and an approved shared catalog
   reference; incomplete descriptive/classification fields can remain explicit
   missing values. Exact immutable identity/code uniqueness and supported bounds
   must be registered technically after the policy choice, not invented here.
4. Multiple companies may reference the same shared catalog product while their
   draft metadata/revisions are isolated by native authority. Creation/replay,
   revision CAS, exact history and company non-disclosure must be proved with
   real two-company/native/HTTP/PostgreSQL tests. No ownership transfer, automatic
   catalog merge, clone/remapping or cross-company lookup is silently supported.
5. Product-reference admission must be explicit Custody-owned policy. Do not infer
   supported families from arbitrary catalog_name/code values, seed operational
   product entries or treat Planning-specific policy as general product approval.
   A later technical registration must name the actual admission source and its
   locked/revocation semantics. Synthetic fixtures may supply approved examples.
6. Preserve all existing Planning/Simulation catalog-ID contracts and gas-fluid
   source/method/Workflow qualification. HAPI-595 owns later supplied property/
   composition/method representation,596 publication/history/owner exports and
   597 consumer qualification. No public material-property API, engine execution,
   fluid conversion, baseline or aggregate exchange follows from594 metadata.

**Alternative B — requires an explicit different owner choice.** If company-local
product identities must be creatable without a pre-existing shared catalog
reference, retain Custody ownership but register a distinct local identity and
optional later governed association/extension policy. That needs explicit
identity resolution, duplicate/merge/link provenance and consumer mapping rules.
It cannot silently replace an existing catalog ID or migrate old references.
Do not implement A or B by assuming which outcome the owner intended.
The owner may also narrow/broaden metadata labels explicitly; no plant data is
needed for this supported-behavior selection.

**Next technical gate after explicit policy approval/change.** Recover current
native actor/product/company authority seams and complete exact DTO, codec,
repository/transaction/SQL/fence/permit wiring, owner-reference admission and
schema constraints. Admit an actual bounded executable allowlist and test cases
in this owning record before code; six earlier proposed source filenames do not
cover new security/DTO/test/migration obligations by themselves. Current last
migration is V20261025_001__organization_appointment_current_read_guard.sql;
reserve no migration filename/version for an unselected product implementation.
Preserve migration history, product catalog ownership and existing consumers.
Technical registration may then move only594 planning to IN_PROGRESS in the
four governance files; implementation remains NOT_IMPLEMENTED until accepted
source/evidence. Proposed later exact registration message:
`docs(custody): register protected product draft execution`.
This is not a promise that unresolved authority/schema gaps already pass.

**This preflight's validation and stop.** Live owner/model/adaptor/catalog-policy/
actor contract and migration recovery, six absent proposed-file check, source
fingerprints, unchanged task metadata/history/registry/index compatibility and
19 existing documentation-validator self-tests PASS. No new Java/Maven/native/
HTTP/PostgreSQL execution, API/dictionary generation or product acceptance is
claimed. Preserve all runtime/tests/inventory/CI/shard weights/artifacts, other
tasks and the actual gas-first/units/Party ownership boundaries. Generic Next
does not retroactively approve an unresolved business policy; explicit approval
or amendment of this concrete proposal is needed before runtime work.
Publish this one two-file proposal on main with expected3bf68ab lease, verify
exact parent/tree/message/blobs, observe applicable exact-SHA Documentation CI
creation/queue/start and STOP. No594 production code,595..597 implementation,
other owner package, release/deployment, numerical engine or OT action follows.


### HAPI-594 Policy A approved and protected execution registered — 2026-10-26

**Owner decision.** The owner explicitly selected "A" in response to the
published A/B choice on main1380f9a78bc72a3ea52239c4c8f5c322356fc16b.
This adopts all six numbered Policy A rules above. Custody remains the sole
product owner; company-local draft identity/code and versioned metadata reference
an explicitly admitted existing active shared Custody catalog product. GAS and
LIQUID are representational labels only; null classification is UNSPECIFIED and
diagnostically incomplete. There is no inference from names, property invention,
catalog fork, consumer-ID substitution or liquid numerical qualification.
This resolves the HPR-P25-053A policy gate, not later053B classifications.
The dated unresolved-policy statements above remain historical evidence.

Source parent1380f9a; tree0be778180fe2b6e79cd3cd573ff56de331a6910f.
Docs608/run40385819951 succeeded on this exact parent; unchanged runtime baseline
Full908/Fast214 remains ef075097142df9e665bc89eb3b2bd4e6b2c9bc17.
HAPI-594 changes to NOT_IMPLEMENTED/IN_PROGRESS only. HAPI-595..597 stay blocked
and unselected. This is one documentation registration stage, exact message:
`docs(custody): register protected product draft execution`.
Current write scope is exactly this phase file, the compact ULTIMATE_TASKS index
and status-only ULTIMATE_ROADMAP/ULTIMATE_PHASES rows for594. No reconciliation
history rewrite, runtime, schema, CI weights, test inventory or artifact change.

#### Recovered seams and constraints

- Identity's TopologyProtectedEngineeringDraftAuthorizationContract has
  pipelineSystemId, topology-company/topology-network scopes and Topology-specific
  permission/resource types. ProtectedOperationIdentityParticipant.qualifyDraftPair
  and JdbcProtectedAuthorizationEvidenceReader.validate enforce that exact family.
  New product IDs must never be smuggled through those methods/scopes. Add a
  distinct issuer-tagged product family, with unchanged existing-family checks.
- ProtectedTransactionExecutor.executePublication, sealAdmission,
  beginProvisional and endProvisional restrict exact compiled callers. Extend only
  for the concrete product service/coordinator, retaining credential routing,
  original budgets, final common database time, private permits and all existing
  hashes. Product DML must use its own reviewed exact statement hashes, never
  generic INSERT/UPDATE-prefix admission or an arbitrary callback.
- Party's protected Topology adapter proves ACTIVE Party status and generation
  in the actual physical transaction, with two relation fences and opaque,
  issuer/thread/connection-bound one-use admissions. Add a Custody-exported
  contract/adapter with the same owner semantics; do not depend on Party's
  private adapter or expose a Boolean controller precheck.
- CustodyCatalogEntryJpaEntity has no company/local revision fields; keep it and
  existing Planning/Simulation contracts unchanged. Planning's product policy
  is nomination-specific. Add independent Custody engineering-reference policy,
  empty on migration, keyed to existing catalog IDs. Require both active policy
  and active catalog, under fences/locks through the final boundary.
- Last live migration is V20261025_001__organization_appointment_current_read_guard.sql.
  All new filenames below are currently absent; recheck ordering before each
  code package. No historical migration edits, operational seeds or grants.

#### Shared contract for the three bounded packages

Product domain is pure Java; company, exact local code and catalog reference are
mandatory immutable identity. Server-generated draft UUID, company-local exact
case-sensitive code (1..80 Unicode code points, no trimming), shared catalog ID
(1..80) and creation-request UUID form explicit identities. Unique(company,code)
and unique(company,creationRequestId); no ownership transfer, rename or relink.
Metadata has optional name (max240), description (max4000), evidence reference
(max1000) and classification null/GAS/LIQUID; supplied non-null text cannot be
blank or silently normalized. Enforce UTF-8/Unicode validity and actual storage
bounds in the domain, DTO and database. Missing name, evidence or classification
produces explicit completeness diagnostics; creation need not invent them.

CREATE starts version1. UPDATE requires positive expectedVersion and appends
exactly one immutable canonical metadata version. CREATE replay requires exact
original canonical identity/metadata match and returns the existing identity;
different content conflicts. A replay is authorized afresh and never discloses
another company's record. READ returns current metadata/version only. Retain
immutable versions internally; public publication/history/export belongs to596.
Every response includes local draft identity and shared catalog identity as
distinct fields, company, local code, current version and diagnostics. No list,
count, search, delete, merge, property API or public revision publication here.

Native Identity authority uses resource type PRODUCT_ENGINEERING_DRAFT and
permissions custody:engineering-product:create/read/update. Require independent
CUSTOM scopes custody-company:<companyId> and custody-product:<catalogProductId>
against the same action/resource and one fresh native identity contribution.
The second scope means approval to author metadata referencing that shared
product, not thermodynamic or Planning qualification. Resource reference is the
catalog ID; requested local draft/company binding is checked by Custody only
after authority. Retain DENY precedence, current effective responsibility,
provider/session/binding/claim generations, expiry, actor audit and no cached
caller permission. New family must reject station/draft/organization/diagnostic
credentials and fabricated/mixed/reused admissions. No grants are seeded.

The actual owner transaction must collect Identity, Party and Custody catalog/
policy/draft evidence from one backend PID/transaction, acquire the full reviewed
relation fences, use common final database time and revalidate before disclosure
or commit. Missing/deactivated/revoked catalog/policy/Party/Identity evidence
fails closed. Timeouts, lock contention, unknown commit and stale versions retain
existing classified errors and rollback guarantees; never retry a write silently.
Preserve original identity snapshot and authored evidence for internal revisions.
Historical metadata does not become current policy or engineering qualification.

#### Package A — distinct native product authorization

Next executable package after successful registration Documentation CI:
`feat(identity): add protected product draft authorization`.

CREATE exactly:
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/custody/CustodyProtectedProductDraftAuthorizationContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedProductDraftAuthorizationParticipant.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/custody/CustodyProtectedPartyEligibilityContract.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JdbcCustodyProtectedPartyEligibilityAdapter.java`
- `src/main/resources/db/migration/V20261026_001__custody_product_draft_authority.sql`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProtectedProductDraftAuthorizationPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/party/infrastructure/persistence/CustodyProtectedPartyEligibilityPostgresIntegrationTest.java`

MODIFY exactly:
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedOperationIdentityParticipant.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `.github/ci-test-inventory.json`
- `doc/roadmap/TASKS_HPS-008.md`

Migration adds only the three explicit permission definitions, asserting exact
existing-row compatibility, with no assignment, role grant, product seed or
modification of existing permission meaning. Party uses existing owner storage
and generation triggers; no new Party lifecycle policy. Participant/component
wiring follows existing Spring constructor injection. No product public endpoint
or storage is accepted inA. Focused actual native PostgreSQL tests must prove
both scope permits, one-side DENY, indeterminate/missing graph, disabled user/
provider/session/binding, exact expiry, changed actual authentication, wrong
company/product/action, family forgery/reuse, cross-thread/transaction attempts,
Party deactivation and rollback. Regression: existing protected Topology and
Organization authorization suites and ArchitectureGuardrailTest. Register only
real new test cases/hashes, retaining existing inventory lanes and weights.

#### Package B — owner aggregate, admission and persistence

Depends on exact-SHA acceptedA. Exact message:
`feat(custody): add protected catalog-linked product drafts`.

CREATE exactly:
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/ProductEngineeringDraft.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/ProductEngineeringDraftUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/ProductEngineeringDraftRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/ProductEngineeringDraftApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/ProtectedProductDraftAdmissionCoordinator.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JdbcProductEngineeringDraftRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/ProductEngineeringDraftMetadataCodec.java`
- `src/main/resources/db/migration/V20261026_002__custody_product_engineering_drafts.sql`
- `src/test/java/dz/sh/hidra/modules/custody/domain/model/ProductEngineeringDraftTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/ProductEngineeringDraftPostgresIntegrationTest.java`

MODIFY exactly:
- `src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java`
- `src/test/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutorTest.java`
- `.github/ci-test-inventory.json`
- `doc/roadmap/TASKS_HPS-008.md`

Use Custody-owned engineering_product_policy, product_engineering_draft and
product_engineering_draft_version tables with hidra_custody_ prefix.
Policy has existing catalog FK and explicit active admission, no default seeds.
Draft stores immutable UUID/company/local-code/catalog/create-request and initial
canonical request digest; versions have draft FK, positive sequential version,
canonical payload/format/digest, actual audit actor and database timestamp.
Use unique constraints and owner row locking/CAS for concurrent creates/appends;
reject UPDATE/DELETE/TRUNCATE of immutable authored history through triggers.
Catalog/policy fencing must cover activation changes and deletion through final
commit/disclosure; row existence alone is insufficient. Parameterize all SQL;
exact codec round-trip rejects malformed/unknown classifications and preserves
nulls. SQL trigger definitions, privilege checks, relation shape and reviewed
exact product DML hashes must be pinned from the actual implementation, never
filled with guessed hashes. Recheck the live migration chain before publication.
No JPA entity exposure or cross-module private imports. Service/coordinator/
repository constructors provide real wiring. Reuse platform routing unchanged
apart from exact new caller/statement admission; no widening of existing callers.

Acceptance: native two-company create/read/update, immutable identity and payload,
case/Unicode/bounds/null diagnostics, exact replay vs conflicting replay, duplicate
local code, sequential revision/CAS races, active/inactive/missing admission/catalog,
revocation between collection and commit, forged/reused permit, wrong transaction,
commit-boundary expiry, final-fence tamper and complete rollback with zero hidden
rows. Existing consumers keep original catalog IDs and qualification behavior.
Use actual migrated PostgreSQL; skips are non-proof. Regenerated catalog artifact
is required for final acceptance, not an inferred dictionary. Artifact refresh
is admitted inC after retrieving exact successful hosted evidence.

#### Package C — HTTP integration, artifacts and bounded acceptance

Depends on acceptedA/B. Exact message:
`feat(custody): expose protected product draft operations`.

CREATE exactly:
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/ProductEngineeringDraftController.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/ProductEngineeringDraftRequests.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/ProductEngineeringDraftResponses.java`
- `src/test/java/dz/sh/hidra/modules/custody/api/rest/controller/ProductEngineeringDraftControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/ProductEngineeringDraftNativeHttpPostgresIntegrationTest.java`

MODIFY exactly:
- `.github/ci-test-inventory.json`
- `doc/api/openapi.yaml`
- `doc/database/DATA_DICTIONARY.md`
- `doc/roadmap/TASKS_HPS-008.md`

Expose POST /api/v1/custody/engineering-products, GET /{draftId} with exact
companyPartyId scope, PUT /{draftId} with exact companyPartyId and expectedVersion.
POST body: companyPartyId, localCode, catalogProductId, creationRequestId and
optional metadata fields; PUT accepts metadata/expectedVersion only. Actor,
grants, audit coordinates and computed completeness are server-owned.
Explicit REST DTO mapping, documented 201 new/200 replay/read/update and existing
400/401/403/404/409/503 semantics. Authorize before protected existence, conflicts
or metadata disclosure; uniform cross-company failure. Real configured native
HTTP/database tests, not mocks alone, prove two-company isolation, authentication,
malformed input, denied/inactive scopes, exact replay, CAS and revocation.
Generate runtime OpenAPI from actual application and dictionary from actual
migrated database; preserve existing operations/physical history. If their actual
repository paths differ, reconcile this registration before mutation, do not
create a second artifact. No handwritten physical metadata.

After implementation, exact-SHA Full/Fast/Docs and actual test-case/API/catalog
evidence must establish bounded acceptance. Status remains NOT_IMPLEMENTED until
source exists, and source existence alone is not VERIFIED. Final governance
closure is separately `docs(custody): accept protected product draft authoring`,
limited to the four governance files named for this registration. No automatic
execution ofA, B, C or closure in this documentation turn. DELETE none for all
packages. Additional required source/migration paths or changed semantics must
be admitted in this owning record before use, without repeating this approved
business choice or requesting real deployment data.

#### Registration validation and stop

Recovered exact-head native identity/read-family guards, Party adapter,
platform credential/exact-caller/provisional admission and Custody/catalog/DTO
conventions; checked all proposed CREATE paths against the live recursive tree.
19 existing documentation-validator self-tests PASS after fetching their actual
OpenAPI-validator import dependency. Changed-task metadata/status synchronization,
unchanged other registry rows and append-only phase history are checked locally.
Full documentation-estate/link validation remains the hosted Documentation CI
gate; no full source checkout or Java/Maven/native/HTTP/PostgreSQL execution,
API/dictionary generation or runtime acceptance is claimed in this registration.
Publish one guarded main commit, verify exact four paths/parent/tree/message/blobs,
observe exact-SHA Documentation CI creation/queue/start and STOP.


### HAPI-594 Package A implementation — 2026-10-26

Owner Next after exact-head Docs609/run40387827780 success selects registeredA.
Source parent5ccf113aff28ba27d08f9b03bb80cf8d990c5de6; tree
5bdd674f80b35776288ec7513f65f0be32a88cf9. Policy A stays approved.
Exact message: `feat(identity): add protected product draft authorization`.

**Delivered source, pending hosted acceptance.** The seven CREATE/five MODIFY
allowlist forA is used exactly; no DELETE. New Identity and Party contracts,
their real native/JDBC participants, additive permission-definition migration,
two native PostgreSQL suites, guarded Identity reader/issuer-family extensions,
one architecture case, inventory entries and this append are the entire scope.
No product aggregate, local draft storage, public API, catalog-admission seed,
role grant, OpenAPI or dictionary change is part ofA. HAPI-594 as a whole remains
NOT_IMPLEMENTED/IN_PROGRESS: its create/read/update workflow requiresB/C.

The Identity contract uses PRODUCT_ENGINEERING_DRAFT, the three registered
custody:engineering-product actions, custody-company and custody-product scopes.
An issuer-private ProductClaimPair is distinct from Topology/Organization/station/
diagnostic families. Native actor, source provenance, session/provider/binding
state and exact scope/action/resource are checked in the actual transaction.
Both graphs use one fresh database sample, including grant/policy/attribute
temporal windows; rechecks obtain another common sample and re-evaluate current
policies. Graph comparison excludes only the advancing context.evaluatedAt fact;
that fact remains in the actual policy evaluation. Changed normalized authority,
expired grants, session/source/native deadlines, changed actual Authentication,
foreign/mixed/replayed admission and suspended/completed transactions fail closed.
Cross-thread rejection poisons the original holder; SQL-free beforeCommit votes
also reject swallowed failures. No new caller route or mutation permit exists
yet; those exact platform extensions remain inB.

CustodyProtectedPartyEligibilityContract exports ACTIVE Party evidence/generation
through a separate owner-issued admission in the real physical transaction.
Its adapter retains the reviewed Party table/function/trigger/privilege and fence
checks, returns exact PID/XID, rejects foreign/consumed/lost-fence admissions,
and rejects surrounding whitespace instead of silently changing supplied identity.
Architecture allows only this exact Party root/nested types to Custody, keeping
private Party/Identity implementations unavailable across module boundaries.
Existing Topology/Organization contracts and owner storage/migrations remain intact.

V20261026_001__custody_product_draft_authority.sql adds only three permission
definitions. Code conflicts retain existing rows and verify exact supported
domain/resource/action/status/sensitivity; incompatible existing meanings raise
an error. There are no operational products, role assignments or policy seeds.
The migration's actual PostgreSQL idempotency/mismatch/no-grant test is included;
it has not been executed locally.

**Exact new test inventory.** ProtectedProductDraftAuthorizationPostgresIntegrationTest
contains16 cases: four actual native authentication producers/three actions;
both-scope DENY/missing authority; company/product/action substitution; Topology
scope rejection; prepared replay/actual-authentication replacement; fabricated
markers/pair; consumed/completed admission; actual user/provider/session
revocation; own-transaction grant revocation/rollback; fresh-time grant expiry;
actual source/native deadline passage without editing immutable bindings;
cross-thread original-transaction poisoning; transaction suspension; inactive
permission; unresolved policy; and permission-migration compatibility.
CustodyProtectedPartyEligibilityPostgresIntegrationTest contains11 actual
PostgreSQL cases covering ACTIVE semantics, missing/corrupt owner witnesses,
replay/foreign/transaction rejection, rollback, writer/admission ordering,
retained/lost fences, trigger/function/RLS/elevated-owner/unknown-state rejection,
exact supplied IDs and separate Topology admission types.
ArchitectureGuardrailTest adds one contract/private-type boundary case (18 total).
These are authored executable cases, not claims of passing Java/native runs.

Inventory records exact actual source hashes/cases and service lanes. New suite
baseline_seconds=0 explicitly means unmeasured; every old class's measured timing,
lane, case membership and Full/Fast/shard assignment is preserved, except the
one added architecture invocation and its necessary source hash. The active
seven-shard policy and all workflows, routing, old hashes and budgets are intact.

**Available validation.** 19 existing documentation-validator self-tests PASS.
Targeted inventory source-hash/exact-case classification and partition checks
PASS; comparison against the pinned prior inventory confirms all existing
partition memberships/timings/settings are retained. Registered file scope,
new-path absence, immutable migration preservation and append-only task history
are checked before guarded publication. Full documentation-estate validation,
Java21 compilation, native PostgreSQL suites, architecture execution and
regression outcomes remain hosted exact-SHA CI gates. Local JRE is17;
javac, Maven and Docker are unavailable (attempted javac exits127). No local
application compilation or PostgreSQL pass is claimed and no environment was
provisioned. Dictionary generation remains with acceptedB/C actual artifacts.

Publish only this registeredA commit on main with expected5ccf113 lease;
verify exact paths/message/parent/tree/blobs and observe applicable Full/Fast/Docs
creation/queue/start, then STOP. Do not startB or claimA accepted before actual
CI feedback. After successful exact-SHA required gates, next registered package:
`feat(custody): add protected catalog-linked product drafts`.


### HAPI-594 Package A CI repair — 2026-10-26

Owner Fail selects responsible Package A repair on main
b1fee00e9232eee49c97b25b5c336ad75664a8fc; tree
9317114416655f050fe16a757980f1c2b13b068c. Docs610 passed; Full909 and Fast215
failed. No Package B/C execution or HAPI-594 acceptance is selected.
Exact repair message: `fix(identity): repair product draft authorization CI`.

Full909 evidence: unit/architecture has3 source-guard failures; postgres-2 has7
ProductDraft native-suite errors; API/dictionary reports physical dictionary drift.
Other six PostgreSQL shards succeeded. The new Party suite is not the reported
failing source. These observations do not make the overall Package A CI-passed.

Admitted repair MODIFY allowlist (zero CREATE/DELETE):
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedOperationIdentityParticipant.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProtectedProductDraftAuthorizationPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java`
- `.github/ci-test-inventory.json`
- `doc/database/DATA_DICTIONARY.md`
- `doc/roadmap/TASKS_HPS-008.md`

The reader's pre-existing ban on clock queries remains unchanged. Native Identity
now issues ProductReads with its own checked database sample alongside the private
pair's credentials; both product authorization graphs consume that same sample.
The reader cannot accept an arbitrary caller-supplied time and no longer queries
clock_timestamp. Native expiry/current-state and issuer/transaction checks remain.
Existing Topology/Organization families and SQL guard budgets are preserved.

The native test helper had called requireCurrent from the executor's final SQL
guard callback. The datasource correctly rejected subsequent SQL. That recheck
now runs at the end of the work body before the executor seals its guard; the
original final guarded sentinel statement/expiry proof remains. No production
SQL-after-guard allowance is added. The indeterminate-policy fixture now uses
supported static syntax with an incompatible typed fact, rather than an
unsupported expression rejected by the temporal compiler. All16 native test
methods and all6 source-boundary guard methods remain, including the inactive
permission, expiry, rollback, replay and adversarial assertions.

The source-boundary guard admits the exact new reviewed compiled product
participant as an Identity admit caller, and repins the reviewed reader source.
It retains exact caller-set equality, the sole collector/acquire callers and all
four unchanged Party/Facility contract/adapter pins. JdbcProtectedAuthorizationEvidenceReaderTest
and its clock/JPA/cache/write/authority-export prohibition are unchanged.
Inventory updates only the two changed test source hashes; all cases, timings,
lanes, shard assignments, counts and other entries remain unchanged.

The API diagnostic artifact12683970846 from Full909/run40391446494 attempt1
contains actual catalog schema.json for sourceb1fee00e. Download ZIP SHA256:
03dcaf5b4dddd3f94349704bb7d9a67393067a0f08fb5441f52d7ae2c0a67dd4.
All644 migration/JPA source fingerprints were recovered from that exact GitHub
SHA and matched the artifact; ownership evidence was recovered from the same
source. The unmodified generator validates/renders/checks174 migrations,
470 JPA mappings,516 physical relations and zero unresolved owners. Source bundle:
1cb982d80d9ff74d0b1589e58ac838c6c86b4def4be7f8e9fc69e62a531de228.
Dictionary SHA256:
15f24aee5f84715c94aebd8e564f2fc74996e9c1851941f9d349b3aa17b1bb1a.
Reviewed dictionary diff is capture SHA/bundle/migration count173-to174 and one
actual permission-table migration mention only; no physical metadata was inferred
or handwritten. Its captured SHA truthfully remains the actual catalog source,
not the as-yet-unpublished repair commit. No migration source/JPA mapping changed.

Validation: existing19 documentation-validator self-tests, original reader ban,
reviewed source pin, supported fixture syntax inspection, exact test source hashes/
unchanged case sets and partition preservation, artifact/source verification and
unmodified dictionary render/check. Java21 application compilation succeeded in
the failed parent workflow, but corrected Java/native regression execution remains
a new exact-SHA hosted gate; local javac/Maven/Docker remain unavailable. The seven
native errors, including inactive-permission handling, must be rechecked rather
than called passing on inspection alone. Preserve NOT_IMPLEMENTED/IN_PROGRESS.

Publish this one bounded repair using expectedb1fee00 lease; verify exact seven
paths/parent/tree/blobs/message, observe applicable exact-SHA Full/Fast/Docs creation
or startup and STOP. Package B remains next only after required acceptance.


### HAPI-594 Package A remaining fixture/guard repair — 2026-10-27

Owner Fail selects the remaining failures at
a9d8b4fb5913ba76229a652f0e77f0fff549fc19, tree
3bd3d352b6294fb81da92909c53b4c64b3bb84a5. Docs611 passed.
Full910/run40405354877: API/dictionary, static validation and six PostgreSQL
shards passed; postgres-2 retained one error out of198 cases, in the16-case
product native suite. Fast216/run40405354846 and Full unit/architecture retained
one reader-surface assertion failure. The previous clock, final-guard, policy
syntax and dictionary defects are no longer the reported failures.

Exact message:
`test(identity): align product authorization fixtures and guardrails`.
Admitted MODIFY allowlist (zero CREATE/DELETE):
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProtectedProductDraftAuthorizationPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReaderTest.java`
- `.github/ci-test-inventory.json`
- `doc/roadmap/TASKS_HPS-008.md`

**Source-backed diagnosis.** PermissionStatus supports ACTIVE, DISABLED and
DEPRECATED. The fixture used INACTIVE, which is not a valid Permission lifecycle
state; the reader's enum validation correctly classified that persisted value
as corrupt evidence and rejected the protected operation. This was not a
failure to deny a supported disabled permission. The native case now exercises
both supported non-active states and requires explicit DENY on both scopes.
It separately retains INACTIVE as a corruption fixture and requires the protected
execution to reject it. Production validation is unchanged; unknown state is
not normalized or relabeled as a supported business state.

The reader's public-method guard expected the six pre-product methods while the
registered product family deliberately added loadQualifiedProductPair and
recheckProductPair. The guard now names all eight methods exactly and verifies
each new method's exact ProductClaimPair/typed product Request input and
PairedAuthorizationEvidence return. Existing Organization/Topology signatures
and the no-clock/JPA/cache/writes/authority-export bans remain intact. No wildcard,
ignored method, relaxed type check, skip or expected-error conversion is added.

Both test classes retain all16 methods each. Inventory changes exactly their
source hashes; case lists, timing values, lane/Full/Fast/shard memberships and all
other entries/settings remain unchanged. No production Java, migration, API,
dictionary, workflow, shard weight, caller pin or business policy is modified.

Available validation:19 existing documentation-validator self-tests PASS;
exact source-case/hash/unchanged-partition comparison, supported PermissionStatus
fixture comparison and retained reader source-ban/type-surface review PASS.
Corrected Java21/native execution remains the new exact-SHA hosted gate; no
local Java/Maven/Docker outcome is claimed. No already-passed runtime gate is
represented as acceptance of this unpublished repair. Preserve overall
NOT_IMPLEMENTED/IN_PROGRESS and the approved Policy A; B/C remain unexecuted.

Publish one guarded main repair with expecteda9d8b4f lease, verify the exact four
paths/parent/tree/message/blobs, observe applicable Full/Fast/Docs creation or
startup and STOP. Next remains registeredB only after Package A acceptance.

<a id="hapi-595"></a>
## HAPI-595 — Supplied composition, properties and methods

Phase: HPS-008 — Products and Fluid Properties

Legacy IDs: HPR-P25-053B

Priority: P1

Implementation Status: NOT_IMPLEMENTED

Planning Status: BLOCKED

Owning Module: `custody` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-594

Cross-Repository Dependencies: HWEB-F06 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Supplied composition/property/reference-basis/units/method applicability and evidence.

### 2. Existing Implementation

Custody product references and bounded immutable gas-fluid revision/qualification contracts exist internally. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/SpringCustodyController.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver company-owned product/property/method drafts, exact composition/basis/units and published revision/history contracts. The exact bounded remaining delivery is: Supplied composition/property/reference-basis/units/method applicability and evidence..

### 4. Functional Requirements

Exact supplied-fact round trip; invalid fraction/basis/unit/method/absent evidence rejected or incomplete. Preserve old GAS behavior; no EOS, normalization or solver.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `custody`; primary phase owns this obligation. Preserve gas-first behavior; metadata for liquids does not establish thermodynamic or hydraulic qualification. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`, `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`, `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/custody/domain/model/SuppliedFluidPropertyRevision.java` — pure Java owned aggregate/value and invariants for supplied composition, fraction basis, unit/reference basis, method applicability and provenance.
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/SuppliedFluidPropertyRevisionUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/SuppliedFluidPropertyRevisionRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/custody/application/service/SuppliedFluidPropertyRevisionApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/SuppliedFluidPropertyRevisionController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JdbcSuppliedFluidPropertyRevisionRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/SpringCustodyController.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `PUT /api/v1/custody/engineering-products/{id}/properties` for supplied composition, fraction basis, unit/reference basis, method applicability and provenance. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Exact supplied-fact round trip; invalid fraction/basis/unit/method/absent evidence rejected or incomplete. Preserve old GAS behavior; no EOS, normalization or solver.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-594. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23378](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23378)
- [doc/model-remediation/RECONCILIATION.md:23704](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23704)
- [doc/model-remediation/RECONCILIATION.md:23741](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23741)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1012](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1012)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1049](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1049)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1316](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1316)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1012 | HPR-P25-053B | Choose representational property/composition families to support as metadata, keeping numerical qualification explicitly unsupported. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/roadmap/ULTIMATE_ROADMAP.md:1049 | HPR-P25-053B | REQUIRES_OWNER_DECISION | Supplied composition, properties and methods
doc/roadmap/ULTIMATE_ROADMAP.md:1316 | HPR-P25-053B | Supplied composition/property/reference-basis/units/method applicability and evidence. | Exact supplied-fact round trip; invalid fraction/basis/unit/method/absent evidence rejected or incomplete. Preserve old GAS behavior; no EOS, normalization or solver.
doc/model-remediation/RECONCILIATION.md:23378 | HPR-P25-053B | Supplied composition/property/reference-basis/units/method applicability and evidence. | Exact supplied-fact round trip; invalid fraction/basis/unit/method/absent evidence rejected or incomplete. Preserve old GAS behavior; no EOS, normalization or solver.
doc/model-remediation/RECONCILIATION.md:23704 | HPR-P25-053B | Choose representational property/composition families to support as metadata, keeping numerical qualification explicitly unsupported. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/model-remediation/RECONCILIATION.md:23741 | HPR-P25-053B | REQUIRES_OWNER_DECISION | Supplied composition, properties and methods
~~~~~~~~

<a id="hapi-596"></a>
## HAPI-596 — Product publication, history and owner exports

Phase: HPS-008 — Products and Fluid Properties

Legacy IDs: HPR-P25-053C

Priority: P1

Implementation Status: NOT_IMPLEMENTED

Planning Status: BLOCKED

Owning Module: `custody` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-595

Cross-Repository Dependencies: HWEB-F06 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Governed publish/withdrawal/current/history/effective selection and scalar owner exports.

### 2. Existing Implementation

Custody product references and bounded immutable gas-fluid revision/qualification contracts exist internally. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/SpringCustodyController.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/PlanningProductReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/simulation/SimulationProductCandidateContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapterTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver company-owned product/property/method drafts, exact composition/basis/units and published revision/history contracts. The exact bounded remaining delivery is: Governed publish/withdrawal/current/history/effective selection and scalar owner exports..

### 4. Functional Requirements

Immutable revisions/digests/source/approval preserved, current eligibility separate from historical access, concurrency/replay/rollback; usable downstream source authoring without055.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `custody`; primary phase owns this obligation. Preserve gas-first behavior; metadata for liquids does not establish thermodynamic or hydraulic qualification. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`, `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`, `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapter.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapter.java`, `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapterTest.java`, `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapterTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/custody/domain/model/ProductRevisionPublication.java` — pure Java owned aggregate/value and invariants for immutable publication/withdrawal/current/history and scalar exports to engineering consumers.
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/ProductRevisionPublicationUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/ProductRevisionPublicationRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/custody/application/service/ProductRevisionPublicationApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/ProductRevisionPublicationController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JdbcProductRevisionPublicationRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/SpringCustodyController.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/PlanningProductReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/simulation/SimulationProductCandidateContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapterTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/custody/engineering-products/{id}/publications` for immutable publication/withdrawal/current/history and scalar exports to engineering consumers. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapterTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapterTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Immutable revisions/digests/source/approval preserved, current eligibility separate from historical access, concurrency/replay/rollback; usable downstream source authoring without055.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-595. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23379](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23379)
- [doc/model-remediation/RECONCILIATION.md:23742](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23742)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1050](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1050)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1317](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1317)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1050 | HPR-P25-053C | BLOCKED_SPECIFICATION | Product publication, history and owner exports
doc/roadmap/ULTIMATE_ROADMAP.md:1317 | HPR-P25-053C | Governed publish/withdrawal/current/history/effective selection and scalar owner exports. | Immutable revisions/digests/source/approval preserved, current eligibility separate from historical access, concurrency/replay/rollback; usable downstream source authoring without055.
doc/model-remediation/RECONCILIATION.md:23379 | HPR-P25-053C | Governed publish/withdrawal/current/history/effective selection and scalar owner exports. | Immutable revisions/digests/source/approval preserved, current eligibility separate from historical access, concurrency/replay/rollback; usable downstream source authoring without055.
doc/model-remediation/RECONCILIATION.md:23742 | HPR-P25-053C | BLOCKED_SPECIFICATION | Product publication, history and owner exports
~~~~~~~~

<a id="hapi-597"></a>
## HAPI-597 — Multi-product ownership/API compatibility qualification

Phase: HPS-008 — Products and Fluid Properties

Legacy IDs: HPR-P25-053D

Priority: P1

Implementation Status: NOT_IMPLEMENTED

Planning Status: BLOCKED

Owning Module: `custody` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-596

Cross-Repository Dependencies: HWEB-F06 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Multi-product owner/API/compatibility qualification and consumer-ready contract.

### 2. Existing Implementation

Custody product references and bounded immutable gas-fluid revision/qualification contracts exist internally. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/SpringCustodyController.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver company-owned product/property/method drafts, exact composition/basis/units and published revision/history contracts. The exact bounded remaining delivery is: Multi-product owner/API/compatibility qualification and consumer-ready contract..

### 4. Functional Requirements

Explicit synthetic supported definitions, old GAS exact replay, denied read/publish/export, unsupported shapes and source corruption; exact API/dictionary/full evidence, no numerical qualification claim.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `custody`; primary phase owns this obligation. Preserve gas-first behavior; metadata for liquids does not establish thermodynamic or hydraulic qualification. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`, `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`, `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — PROPOSED:**

- `src/test/java/dz/sh/hidra/modules/custody/qualification/ProductEngineeringOperatorQualificationTest.java` — actual owner/filter/database qualification of this task's exit matrix.

**MODIFY:** `.github/ci-test-inventory.json` — actual class/case/hash admission; `doc/roadmap/ULTIMATE_TASKS.md` — exact-source evidence and limitations.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/SpringCustodyController.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java`

**DISCOVERY_REQUIRED:** fixture/support filenames and exact test partition; use synthetic owners and real transactions, not fabricated prior results.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Explicit synthetic supported definitions, old GAS exact replay, denied read/publish/export, unsupported shapes and source corruption; exact API/dictionary/full evidence, no numerical qualification claim.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-596. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23380](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23380)
- [doc/model-remediation/RECONCILIATION.md:23743](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23743)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1051](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1051)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1318](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1318)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1051 | HPR-P25-053D | BLOCKED_SPECIFICATION | Multi-product ownership/API compatibility qualification
doc/roadmap/ULTIMATE_ROADMAP.md:1318 | HPR-P25-053D | Multi-product owner/API/compatibility qualification and consumer-ready contract. | Explicit synthetic supported definitions, old GAS exact replay, denied read/publish/export, unsupported shapes and source corruption; exact API/dictionary/full evidence, no numerical qualification claim.
doc/model-remediation/RECONCILIATION.md:23380 | HPR-P25-053D | Multi-product owner/API/compatibility qualification and consumer-ready contract. | Explicit synthetic supported definitions, old GAS exact replay, denied read/publish/export, unsupported shapes and source corruption; exact API/dictionary/full evidence, no numerical qualification claim.
doc/model-remediation/RECONCILIATION.md:23743 | HPR-P25-053D | BLOCKED_SPECIFICATION | Multi-product ownership/API compatibility qualification
~~~~~~~~

<!-- task-register:payload:end -->
