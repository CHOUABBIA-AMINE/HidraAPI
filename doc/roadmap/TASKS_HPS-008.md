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

Planning Status: BLOCKED

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
