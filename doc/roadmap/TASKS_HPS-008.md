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

Implementation Status: VERIFIED

Planning Status: COMPLETED

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

### HAPI-594 Package B guardrail-scope reconciliation — 2026-10-27

Owner Next refreshes main cd14834249037b4d0773a69d740310beda37e4e6
(parent a9d8b4fb5913ba76229a652f0e77f0fff549fc19; tree
38b5fb4ddd199b58c34f2a8cd0e2bbccf6769a22). Exact-head Full911/
run40453455685, Fast217/run40453455714 and Docs612/run40453455675
are completed/success. This resolves the reported Package A workflow failures;
it does not establish the absent B/C workflow or complete HAPI-594. The current
recursive tree still has none of B's ten CREATE paths. Latest migration remains
V20261026_001__custody_product_draft_authority.sql; the registered
V20261026_002__custody_product_engineering_drafts.sql is still absent.
No historical migration, runtime, inventory or status is changed here.

**Concrete recovered scope gap.** Package B registers exact new compiled product
callers in ProtectedTransactionExecutor, but its MODIFY list omits the existing
ProtectedStationAdmissionBoundaryGuardrailTest. That source test's
coordinatorUsesOnlyApprovedOwnerContractsAndTechnicalMechanics scans every
production Java file and asserts exact caller sets for beginAdmission,
sealAdmission, requireAdmission and rejectAdmission. The registered product
coordinator necessarily adds a caller; its owner repository may use the existing
rejectAdmission poisoning hook. Separately, platformHasNoBusinessModuleDependencies
removes only the already reviewed exact compiled owner class-name literals before
rejecting all remaining business-module strings. B's two product literals are
therefore rejected by the unchanged test. These are source-confirmed prospective
assertion conflicts, not newly executed Java failures. Editing the test without
registration would exceed B's exact file scope; bypassing its checks would weaken
the required owner boundary.

**Bounded supporting registration, selected instead of starting incomplete B.**
Exact current message:
`docs(custody): complete product draft guardrail registration`.
Current commit: zero CREATE, one MODIFY, zero DELETE; this owning phase record
only. Preserve every preceding history byte, all task states and the frozen
ROADMAP/PHASES/index. This corrects the technical allowlist under the original
registration's requirement to admit additional required paths before use;
it introduces no new product policy or standalone task.

For the next separately selected Package B, add exactly this one MODIFY path to
its existing allowlist:
- `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java`

The ten CREATE and four prior MODIFY paths, exact B commit message, dependencies,
contracts and acceptance matrix remain unchanged. The additional test update is
limited to the two actual affected methods above:
1. Add the actual compiled ProtectedProductDraftAdmissionCoordinator to each
   applicable exact admission-hook caller set. Admit
   JdbcProductEngineeringDraftRepositoryAdapter only in rejectAdmission if its
   actual implementation uses that hook; no speculative caller entries.
   Keep equality with the complete source-discovered set; no containsAll,
   wildcard, skip, removed assertion or broad package authorization.
2. Recognize exactly the full product service and coordinator class-name literals
   that B actually registers in ProtectedTransactionExecutor, then retain the
   existing rejection of all other business-module references. Preserve every
   prior caller/string count and every other boundary test, including native
   Identity caller sets, the reviewed reader hash and the four original owner pins.

B must update this test's actual source hash in the already admitted inventory;
its six existing case identities, execution lane, timing and shard membership
remain unchanged. Retain and run the actual guard suite with the new B sources.
Independently retain ProtectedTransactionExecutorTest coverage for unreviewed
callers, forged/reused/wrong-owner permits and exact product statement hashes;
the extra guard path is no substitute for native authorization, owner admission,
PostgreSQL replay/CAS/rollback/expiry/tamper or later native HTTP evidence.

**Available validation and remaining gates.** Exact-head workflow conclusions,
full source review of the affected guard methods, executor caller/provisional
checks and credential routing, original B allowlist comparison, absent CREATE
paths and current migration inventory are recovered from the pinned GitHub
source. Existing documentation-validator self-tests:19 PASS. Exact append-only history,
ten absent B CREATE paths, single-path scope and new-text whitespace checks PASS
for this supporting commit. No Java,
PostgreSQL, product source compilation, raw A case-artifact revalidation or B
runtime outcome is claimed by this registration. Overall HAPI-594 remains
NOT_IMPLEMENTED/IN_PROGRESS; Policy A and HAPI-595..597 boundaries remain.

Publish this one documentation correction directly to main with expected
cd14834249037b4d0773a69d740310beda37e4e6; verify parent/tree/blob/message and
observe exact-SHA Documentation CI creation/queue/start, then STOP.
Next registered runtime package remains
`feat(custody): add protected catalog-linked product drafts`.
Do not reexecute A, start C, claim product acceptance or alter unrelated guards.

### HAPI-594 Package B owner drafts implemented; hosted acceptance pending — 2026-10-27

Owner Next selects the registered B runtime package after exact-head Docs613/
run40469396072 succeeded on ab745e8bf1030345d3682bc0caa0536f0ca824cf
(parent cd14834249037b4d0773a69d740310beda37e4e6; tree
52f37c4dad097e2b739e85ddf2b82c1d394d7108). Package A's repaired source
cd148342 has successful Full911, Fast217 and Docs612 as independently observed
in the preceding supporting record. No A implementation is repeated.

Exact message: `feat(custody): add protected catalog-linked product drafts`.
Exact scope: the ten B CREATE paths and five MODIFY paths, including the
separately admitted ProtectedStationAdmissionBoundaryGuardrailTest. No DELETE.
The current migration chain ends at V20261026_001; additive
V20261026_002__custody_product_engineering_drafts.sql remains the registered
new filename. All historical migrations, existing consumers, permission policy,
workflows, shard weights and other task states are preserved.

**Implemented contracts and owner behavior.** ProductEngineeringDraft is pure
Java. Immutable local UUID/company/exact code/shared catalog/create-request
identity stays distinct from versioned metadata. Its revision transition enforces
expected version and overflow rejection. Missing name/evidence/classification
remain diagnostics; GAS/LIQUID are only authored labels. Exact Unicode scalar
bounds reject malformed surrogate sequences, NUL, blanks and surrounding
whitespace; no input is silently normalized. The typed inbound command excludes
identity mutation on READ/UPDATE. Result data contains no persistence entity.

ProductEngineeringDraftRepositoryPort carries opaque admissions; application
service and ProtectedProductDraftAdmissionCoordinator use the A Identity/Party
exports and technical executor, never another module's private implementation.
For READ/UPDATE the server resolves only the company-bound scalar catalog
reference under storage fences before native two-scope authorization; missing
and foreign bindings both produce the same DENIED failure, never a row/count/
metadata response. Descriptive data, replay/conflict and current version selection
follow native authorization. No caller-selected catalog can relink a draft.

The coordinator combines24 Identity,2 Party and4 Custody relation fences, checks
one backend/XID, native company/product decisions and Party generation, and
rechecks Identity before completion. Original issuer/thread/transaction admissions
and one-use permits are retained. Cross-thread misuse marks the original
coordinator admission rejected; its SQL-free beforeCommit vote rejects swallowed
failures. requireCurrent performs its own transaction checks so a missing-worker-
transaction rejection cannot be intercepted before poisoning the original ticket.
Storage tickets also reject suspension, completed/foreign/forged reuse and poison
their original holder. Public scalar contributions are never transferable permits.

The JDBC adapter uses the actual protected credential route, owner locks,
company/code and company/request uniqueness, row locks and a sequential version
CAS. Exact CREATE replay compares original canonical request plus initial metadata,
returns the existing identity/current revision and never reverts later metadata.
The fixed-order strict UTF-8 codec preserves nulls and rejects unknown labels,
malformed encodings and trailing bytes. The two product DML SHA256 templates are
computed from actual statements and independently pinned in coordinator/executor;
no SQL-prefix or arbitrary callback permission is added for products. All prior
owner routing, statement rules, deadlines and budgets remain unchanged.

**Additive schema and final boundary.** Custody engineering_product_policy,
product_engineering_draft and product_engineering_draft_version are separate
tables under the existing hidra_custody prefix. The policy is empty, catalog-linked
and active only when explicitly maintained; no operational catalog products,
role assignments or authority grants are seeded. Draft company/code comparisons
use C collation; identity and authored history reject UPDATE/DELETE/TRUNCATE.
The version trigger locks the owner, enforces sequence/predecessor and actual
backend/XID/time provenance and supplies the database creation timestamp.
Database checks bind typed metadata/Unicode bounds, canonical bytes/format and
digest, nullable supported classification and positive audit/version coordinates.

Runtime checks cover the actual non-owner principals, read-only ordinary draft/
policy privileges, read-only Party generations, relation kinds/column shape,
keys/constraints and the exact trigger bodies/activation/placement. Publisher
UPDATE permits required relation locks but immutable statement triggers reject
history mutation. Catalog and policy are fenced through commit. One final SQL
statement takes a materialized database time sample, checks the validity window,
PID/XID/current principal, Party generation/status, active catalog/policy, exact
draft identity/request digest, latest canonical bytes/digest, mutation audit origin,
all30 actual relation locks and retained storage guards. Failed admission,
conflict, timeout, expiry, guard rejection and unknown commit retain classified
failure/rollback semantics; writes are never silently retried.

**Authored tests and actual local evidence.** ProductEngineeringDraftTest has9
JUnit cases for invariants, exact Unicode/bounds, null diagnostics, revision/CAS/
overflow, strict codec corruption and immutable command/request digest semantics.
ProductEngineeringDraftPostgresIntegrationTest has19 native/database cases:
four genuine authentication producers; metadata/null/Unicode round trips;
original replay after revision; conflicting replay/duplicate code; stale CAS;
same-error foreign/missing read; separate companies sharing one catalog/code;
absent/inactive policy/catalog; scope denial and inactive Party; final SQL expiry;
final fence substitution; disabled trigger/excess privilege; ordinary DML and
publisher history mutation; writer-first and owner-first policy races; concurrent
same-version writers; completed/forged admissions; swallowed cross-thread reuse;
and direct forged version/bytes/origin rejection. Native producer fixture helpers
are retained locally in the new suite; only directory/source transport is mocked,
not Identity/Party authority, protected commit, migrations or PostgreSQL.

ProtectedTransactionExecutorTest adds2 cases for diagnostic product-permit
rejection and exact reviewed template hashes. The admitted topology guard updates
only the two reviewed methods, preserving full exact caller equality, old counts,
all native Identity caller restrictions and all original reader/owner pins.
Inventory adds the two real classes/28 case names plus those2 platform cases;
only changed test hashes are refreshed. All existing case identities, timing
values, lanes, shard assignments and settings remain. New baseline_seconds=0
means unmeasured, never a measured performance result.

Actually completed locally:19 documentation-validator self-tests PASS; Java
compiler parsing of all12 bounded Java files PASS; the pure domain/command/codec
subset compiled with the installed Java17 compiler module;27 standalone smoke
assertions on that subset PASS. Exact current migration ordering/new-path absence,
source-derived SQL/function hashes, parameter count, scope, append-only history,
inventory membership/hash/partition preservation and whitespace checks are run
before guarded publication. An initial parser invocation from the wrong working
directory failed to locate sources; rerun from the source root passed. These
checks are not Java21 application compilation, JUnit execution, native provider
qualification or PostgreSQL/Flyway/JPA acceptance. Maven and Docker are absent;
no full environment was provisioned. Hosted exact-SHA checks remain authoritative.

No public endpoint, runtime OpenAPI regeneration or physical dictionary refresh
is claimed. The existing dictionary remains the previous actual catalog artifact;
B's newly migrated catalog capture/freshness is explicitly pending, to be recovered
from hosted evidence for the registered C artifact work. Do not infer physical
metadata or call B accepted from source presence. Overall HAPI-594 status fields
remain conservative pending its full registered workflow and final governance
acceptance; B implementation/CI-pending is recorded here separately.

Publish this one bounded B commit directly to main with expected
ab745e8bf1030345d3682bc0caa0536f0ca824cf. Verify exact15 paths, parent/tree/blobs/
message and observe applicable exact-SHA Full/Fast/Docs creation/queue/start, then
STOP. Repair any responsible failed gate before advancing. Next registered package,
only after B acceptance, is `feat(custody): expose protected product draft operations`.
No C, HAPI-595..597, simulator, release or deployment is executed in this stage.

### HAPI-594 Package B responsible CI repair — 2026-10-27

Owner Fail selects repair of main d85f9d4c7b7da0312b1217fffe085e0bf496a9fd,
tree a0da0afd4f584bb1cc53da32271dccf4aaca70e7. Exact-head Docs614 passed;
Full912/run40472480382 and Fast218/run40472480404 failed. No C or later task
is selected. Exact repair message:
`fix(custody): repair product draft admission and CI evidence`.

**Recovered failures and bounded admission.** Full unit/architecture reports two
failures: the compiled ArchitectureGuardrailTest scan never calls its already
approved Custody/Party export helper, although its classifier self-test does;
ForensicRemediationClosureTest separately lacks that same exact root admission.
Native postgres-2 reports14 errors across19 new product cases, all on the initial
positive create path with sanitized UNAVAILABLE errors; the five rejection-only
cases do not prove their intended late-boundary behavior. The product repository
has two client SELECT FOR UPDATE statements. The actual unchanged datasource
admission grammar rejects the UPDATE token before execution. Its standalone
reproduction confirms both rejected locking-read shapes and accepted SELECT
shapes. This is a concrete repair target; hosted rerun must still establish
whether any additional native defect remains.

API/dictionary and three other PostgreSQL shards report unresolved ownership
for exactly the three new Custody tables. Full175-migration catalog capture
exists; this is a missing source ownership/dictionary integration, not grounds
to weaken those existing native suites or their zero-unresolved-owner gate.

Admitted repair MODIFY allowlist, zero CREATE/DELETE:
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JdbcProductEngineeringDraftRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/ProductEngineeringDraftUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/ProductEngineeringDraftApplicationService.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/ProductEngineeringDraftPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `.github/ci-test-inventory.json`
- `.github/database-dictionary-ownership.json`
- `.github/scripts/generate_data_dictionary.py`
- `.github/scripts/test_generate_data_dictionary.py`
- `doc/database/DATA_DICTIONARY.md`
- `doc/roadmap/TASKS_HPS-008.md`

This source-backed repair extends B's supporting acceptance scope before using
the additional exact paths. Bringing the existing actual-catalog freshness gate
forward from C repairs B's failed CI; it does not select HTTP/API implementation.
Preserve all migration bytes, native authority semantics, SQL grammar, statement
hashes, relation fences, version CAS, time budgets and existing test identities.

**Correction and preserved guarantees.** Remove only the two client FOR UPDATE
clauses. The four Custody SHARE ROW EXCLUSIVE relation fences already serialize
owner changes and prevent catalog/policy revocation; the actual append trigger
still locks the draft row FOR UPDATE and enforces sequence/predecessor/physical
origin, and the admitted version INSERT still uses exact expected-version CAS.
No general locking-read, UPDATE, callback or SQL-prefix permission is added.

Use the same narrow Custody export rule in the actual compiled architecture scan
and its classifier, limited to the concrete coordinator and its nested classes
consuming the exact Party root/nested contracts. Add the same exact root/source
pair to the source import scan, with negative sibling/private/foreign-consumer
assertions. Keep all other cross-module rules. Retain original exception causes
inside classified application failures for native diagnostic evidence, while the
public reason/message stay unchanged; no raw detail is added to a REST response.
Strengthen the two existing final-guard rejection tests to require that apply and
the intended guard were reached, preventing unrelated early failures from
satisfying those negative cases. Preserve all19 native cases and existing lanes.

**Actual catalog evidence and artifact boundary.** API diagnostic artifact
12719715919 from Full912 attempt1 has ZIP SHA256
76b7655def7a14f5c6a0770552fd32f42db6349c14e997be311c62ddd2716e13.
Its actual schema.json source is d85f9d4c7b7da0312b1217fffe085e0bf496a9fd.
All645 migration/JPA source fingerprints were recovered from that pinned GitHub
source and matched the artifact. Ownership evidence paths were recovered from
the same source. The generator validates this actual catalog with three explicit Custody ownership
entries. Initial rendering exposed literal carriage returns in actual CHECK
text: text-file reads normalize those bytes, making render/write/read checks
drift. Escape carriage returns in the Markdown cell display and add a focused
round-trip regression; preserve the capture SQL, source/catalog validation and
all other generator behavior. No physical column, constraint, trigger or index
metadata is handwritten or inferred.
The captured SHA truthfully remains the actual catalog source. No schema/JPA
source changes in this repair, so the capture's physical source inventory remains
applicable. Source/raw artifact recovery is not a passing native workflow.

**Completed repair validation.**18 dictionary-generator self-tests and19
documentation-validator self-tests PASS. Six changed Java sources parse using
the installed Java17 compiler module; the unchanged pure domain/codec plus
updated command/failure type compile as a Java17 subset, with27 standalone smoke
assertions PASS. Eight classifier assertions extracted from the actual repaired
helpers PASS, and actual compiled/source scans now call those helpers. The
unchanged admission grammar reproduction rejects the old locking reads and
accepts the replacement SELECT shape. Exact two product DML/function pins,
retained migration bytes, scope and source hashes are checked before publication.

The generator's actual catalog render and subsequent --check both PASS after
the carriage-return display repair:175 migrations,470 JPA mappings,519 physical
relations, zero unresolved owners. Source bundle SHA256:
18a637865d4575ee176cf0ec58e510e7b9e403b6f38c27557d1853d20437372a.
Dictionary SHA256:
f94e25839e9f89b634b7127f0fc220c6abafa00d913b066904976435baafd954.
The initial unchanged-generator check failed because of the real captured
carriage returns; that failure is corrected rather than ignored. The capture
JSON/SQL, migration/JPA inventory, ownership validation and comparable-Markdown
rules are unchanged. No schema history was rewritten. Only three explicit
ownership entries and their actual source evidence are added.

Inventory changes only the three modified Java test source hashes: the19
product cases,18 architecture cases and7 forensic cases keep their names,
lane, timing and shard assignments. The dictionary Python suite gains one
carriage-return write/read regression (17 to18 cases); it is not a Java test
partition change. No new runtime, native or full Java21 passing result is
claimed. Corrected exact-SHA CI is the remaining acceptance gate. The one
repair commit is twelve MODIFY paths, zero CREATE/DELETE. No Package C work
or final HAPI-594 acceptance follows automatically.


Validation and exact publication checks above precede guarded publication. Corrected Java21/Spring/native/PostgreSQL execution remains
a new exact-SHA hosted gate. Do not call Package B accepted on inspection or
repeat C's implementation. Publish this one responsible repair with expected
d85f9d4c7b7da0312b1217fffe085e0bf496a9fd; verify scope/parent/tree/blobs/message,
observe applicable Full/Fast/Docs creation/queue/start and STOP.
Next remains C only after B's required gates are accepted.

### HAPI-594 Package C HTTP implementation; runtime contract capture pending — 2026-10-27

Owner Next selects the registered C package on main
bcaca876ae2ffe1627540d82eec7f3e8f002bc43 (parent
d85f9d4c7b7da0312b1217fffe085e0bf496a9fd; tree
aca5e5a41db775c9409e28bc39048bb926166dab).
Exact-head Full913/run40476182252, Fast219/run40476182246 and
Docs615/run40476182234 are completed/success.

**B prerequisite evidence accepted in its bounded internal software scope.**
Retained postgres-2 evidence artifact12720389189 identifies this exact source,
run and attempt1. Its inventory fingerprint matches the pinned repository
inventory; the actual source hash and all19 ProductEngineeringDraft native case
identities match that inventory. The XML report SHA256
19896ed0ff1b7d92e5342a7b3cddaee76ff016f2885f61c65f797e73426ebe9f
matches the evidence manifest and reports19 tests, zero failures/errors/skips.
This accepts B's registered internal owner/admission/persistence scope alongside
the successful native/regression/architecture/API/dictionary gates; it does not
substitute for the new HTTP workflow, engineering qualification or deployment.
Do not repeat A/B implementations or change their tests.

Exact current message:
`feat(custody): expose protected product draft operations`.
Current commit has exactly the five registered C CREATE paths and two MODIFY
paths: the Java test inventory and this owning phase record. Zero DELETE.
The two originally admitted artifact paths are preserved until actual runtime
capture permits a truthful refresh. No unregistered source or configuration
path is added.

**HTTP/DTO implementation.** ProductEngineeringDraftController injects only the
typed inbound use case and exposes exactly:
- POST /api/v1/custody/engineering-products:201 new,200 exact freshly authorized replay.
- GET /api/v1/custody/engineering-products/{draftId}: current metadata in the exact companyPartyId scope.
- PUT on that same identity: metadata-only revision with positive expectedVersion and exact companyPartyId scope.

Request/response records live in the registered explicit API files. They import
no persistence entity or private foreign owner type. DTO creators inspect actual
JSON value kinds rather than silently converting numeric/Boolean identities,
textual/fractional/overflow versions or unknown enum labels. Missing mandatory
identity/request fields fail400; optional metadata retains null. Supported exact
GAS/LIQUID labels map explicitly to the domain; no engineering property, source
or qualification is inferred. Text uses the registered Unicode scalar limits,
rejects malformed surrogate sequences/NUL/blanks/surrounding whitespace and
never trims input. UUID strings use canonical lowercase syntax. Unknown JSON
fields are rejected, including caller actor/grant/completeness/version fields
and identity fields in PUT. Duplicated or extra query scope parameters reject
before use-case invocation.

Responses explicitly map distinct local/catalog identities, immutable company/
code, current version and metadata, DRAFT state, replay flag and server-computed
metadata completeness/diagnostics. Null classification is serialized explicitly
despite the application's default non-null inclusion. Metadata completeness is
not engineering qualification. No caller-controlled audit fields, native
credentials, internal persistence object, numerical property, history/export,
list/search or publication operation is exposed.

The existing configured security chain supplies native bearer authentication;
all owner authority/transaction/concurrency checks remain in the accepted use
case. Local controller error mappings preserve400/401 authentication-chain
behavior,403 non-disclosing target/authority denial,409 authorized conflict and
503 unavailable/indeterminate completion. Missing and foreign company-bound
draft identities both retain the accepted B403 denial; no nonexistent-target
404 behavior is invented. Unknown route404 remains framework behavior outside
these three operations. Error bodies expose fixed ProblemDetail descriptions
and reason codes, never retained causes, SQL, stack traces or actor evidence.
503 does not instruct an automatic write retry.

**Authored acceptance cases.** ProductEngineeringDraftControllerTest has9 actual
standalone MockMvc cases:201/200 replay and typed create binding; read/update
company/version binding; unknown server-owned/immutable-field rejection;
coercion/overflow/unsupported-label rejection; Unicode scalar boundary; blank/
NUL/surrogate/oversize text rejection; malformed body/UUID/missing/duplicated/
extra scope rejection; explicit-null wire response without native/persistence
fields; and safe classified errors including retained-cause non-disclosure.
These isolated boundary tests do not claim native authentication proof.

ProductEngineeringDraftNativeHttpPostgresIntegrationTest has12 actual cases
through the configured filter chain and actual PostgreSQL: all four native
producer bearer round trips; missing/malformed bearer; exact original replay
after revision and conflicting replay; stale CAS/duplicate-code rollback;
two-company isolation with one shared catalog/code and uniform foreign/missing
denial; current DENY on either scope for read/update/replay; inactive policy/
catalog/Party; malformed/coerced/forged body rejection with unchanged storage;
metadata-only update and duplicate scope; explicit null and LIQUID label without
qualification; revoked native session; and actual final database guard expiry
returning503 with both provisional rows rolled back. The latter requires the
real apply and intended guard to be reached. Native crypto, owner admission,
storage, migrations and commit are real; only the accepted directory/source
transport fixtures remain mocked. Helpers are local to this new suite, with no
inheritance, alteration or reexecution claim for B's accepted suite.

Inventory admits exactly those two classes/21 real case names. Prior entries,
source hashes, cases, lanes, timings, shard settings and existing partition
assignments remain unchanged. New baseline_seconds=0 means unmeasured.
The standalone suite joins Fast/unit and the native suite the existing service
partition; no weighting, workflow or budget is changed.

**Actual local validation and remaining runtime artifact gate.**19 existing
documentation-validator self-tests PASS; all five new Java files parse using
the installed Java17 compiler module. Header/wire-import/new-path checks and
exact inventory source-hash/case/unchanged-partition checks PASS. B's raw native
report/source/inventory membership was actually checked as described above.
These results are not Java21 application compilation, C JUnit/native HTTP/
database execution or a newly generated OpenAPI contract. The reviewed Jackson
3 JsonNode primary source confirms the exact tree APIs used for strict DTO
parsing. Full framework execution remains hosted exact-SHA CI; no complete
local environment was provisioned.

The existing physical dictionary was generated from an actual175-migration/
470-JPA/519-relation catalog and passed Full913's dictionary gate. C adds no
schema/JPA change; its physical source bundle and three Custody ownership
records stay valid. Preserve that artifact rather than rewriting capture facts.
The canonical OpenAPI snapshot remains the accepted pre-C runtime artifact.
It does not yet claim these three new operations. Under AGENTS.md11.1 CI-first
policy, publish the reviewed code with this explicit pending freshness gate;
do not synthesize or hand-edit a predicted runtime schema. The current source
may therefore fail the existing API equality gate until its real hosted capture
is adopted. This is a retained acceptance obligation, not a waived check.

After this source commit's actual hosted feedback, the separately selected C
artifact follow-up is registered with exact message
`docs(api): refresh protected product draft runtime contract`.
Its MODIFY allowlist is only doc/api/openapi.yaml and this owning phase record,
plus doc/database/DATA_DICTIONARY.md only if the actual hosted catalog review
demonstrates a genuine physical drift. Zero CREATE/DELETE. Retrieve the source-
bound generated OpenAPI artifact, verify exact source/run/attempt and all three
real operations/DTOs/errors/security plus preservation of old routes; run the
canonical validator and compatibility checks against the actual captures.
Replace the snapshot with that actual generated document, retaining truthful
capture provenance. Correct any source/test failure first in its responsible
scope; never call a schema-equality failure passing or advance to acceptance.
This is the remainder of the original C artifact scope, not a new capability
or permission to implement HAPI-595..597.

Publish only this C source commit directly to main with expected
bcaca876ae2ffe1627540d82eec7f3e8f002bc43. Verify exact seven paths, parent/tree/
blobs/message and append-only history; observe exact-SHA applicable Full/Fast/
Docs creation/queue/start and STOP. Do not wait for completion, refresh from an
uncaptured source, start the artifact follow-up or claim HAPI-594 completed.
Final four-governance-file acceptance remains separately
`docs(custody): accept protected product draft authoring`, after C's real
HTTP/API/catalog and required exact-SHA CI gates are satisfied.


### HAPI-594 Package C responsible route/admission CI repair — 2026-10-27

Owner Fail selects responsible repair of main ba601a219fad4f6e7d8cd560c395365c59e137ac,
tree 69c5b9296b951c48702175b01b4b95020719852b. Full914/run40479972879 attempt1
failed; exact-head Fast220/run40479972882 and Docs616/run40479972885 succeeded.
Exact repair message: `fix(custody): repair protected product HTTP admission`.

Admitted MODIFY allowlist, zero CREATE/DELETE:
- `src/main/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptor.java`
- `src/test/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptorTest.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/ProductEngineeringDraftApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/ProtectedProductDraftAdmissionCoordinator.java`
- `.github/ci-test-inventory.json`
- `doc/roadmap/TASKS_HPS-008.md`

The actual registered MVC interceptor lacks delegation for all three new product
routes; it derives an unrelated custody:engineering-products:execute/read/update
permission before body parsing or the native owner. Eleven of12 native HTTP cases
fail403, including malformed-body400 cases. Admit only the exact compiled
ProductEngineeringDraftController create/read/update method, HTTP verb and
registered route tuple to the existing authenticated owner-delegation path.
Authentication remains required and the use case still obtains both independent
native scoped grants; no permission seed, static grant, prefix or global bypass.

One existing B native create failed closed at521420088ns with493 statements,
exceeding the unchanged500ms work budget. Source inspection identifies duplicate
full native graph rereads: collect calls identity.requireCurrent immediately
before its own requireCurrent (which calls the same method), and a mutation
returns from endProvisional's requireCurrent straight into the service's identical
check. Remove only these adjacent duplicates. Retain fresh collection, the
post-eligibility/seal check, the pre-write check, post-write check, read/replay
post-apply check, exact one scalar final guard, rollback and all budget limits.
No accepted native case, native reader pin, SQL grammar or migration changes.

Retained postgres-2 diagnostic artifact12723430238 ZIP SHA256
827d0dfd625cbc5fb799d9ebc1d20c5da899f9a267dc502e20992b814134a82f
contains the actual19-case B report (one error) and12-case C HTTP report
(11 failures). Unit/architecture and all other PostgreSQL shards passed.
Runtime OpenAPI equality also failed as explicitly anticipated by C's pending
capture obligation. API diagnostic artifact12723010945 ZIP SHA256
7fa7897dae2e6408f0bc9df10d06ad0c5e6aec02a5b533e697efeec561ee4bd6
retains the actual source-bound capture. Preserve the snapshot in this source
repair; its separately registered runtime artifact refresh follows responsible
source feedback. Do not call Full914 or this unpublished repair accepted.

**Repair checks and limits.** Four changed Java files parse with the installed
Java17 compiler module;24 assertions on the extracted actual exact-tuple helper
PASS. The19 existing documentation-validator self-tests PASS. The route JUnit
suite gains one comprehensive case (11 to12), covering all three valid tuples
without static permission lookup, wrong verb/pattern/controller/method and
missing/unauthenticated/anonymous authentication rejection without invoking the
business use case. Only that class's case list and source hash change in the CI
inventory; other cases, source hashes, lane/timing/shard settings remain intact.
These checks are not Spring/JUnit/native HTTP/PostgreSQL execution. All existing
B19 and C12 native case identities and source bytes are preserved for hosted
verification, including scoped DENY, commit-guard expiry and rollback cases.

No runtime OpenAPI, schema, dictionary, migration, authority definitions or
release state changes in this six-path repair. The actual API capture identifies
ba601a219fad4f6e7d8cd560c395365c59e137ac and contains the three product operations;
it is retained for the separate registered refresh, not labeled accepted.
The equality gate remains a known pending artifact obligation; source/native
corrections still require exact-SHA feedback. Overall HAPI-594 statuses stay
conservative. Publish directly to main with expectedba601a2, verify the six
MODIFY paths/parent/tree/blobs/message, observe applicable exact-SHA Full/Fast/
Docs creation/queue/start, then STOP. Do not wait for completion or execute the
artifact follow-up or final acceptance automatically.


### HAPI-594 Package C denial oracle and actual runtime artifact repair — 2026-10-27

Owner Fail selects the remaining failures at main
21ee2864991c920aedf085db76e09f0a370644af, tree
c770145dfb4c1dfd13b03e5637dbc8fdf28e35ba. Exact-head Full915/run40486323285
attempt1 failed; Fast221/run40486323308 and Docs617/run40486323360 succeeded.
One bounded responsible repair now completes the pending C runtime capture
refresh together with its remaining test-oracle correction. Exact message:
`fix(custody): align product HTTP denial and runtime contract`.
This supersedes the separate unexecuted artifact-only message for this capture;
final four-file acceptance remains separate and unselected.

Admitted MODIFY allowlist, zero CREATE/DELETE:
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/ProductEngineeringDraftNativeHttpPostgresIntegrationTest.java`
- `.github/ci-test-inventory.json`
- `doc/api/openapi.yaml`
- `.github/documentation-validation.json`
- `doc/roadmap/TASKS_HPS-008.md`

The manifest is a necessary existing companion of the previously registered
OpenAPI artifact: the unchanged validator requires its exact snapshot digest,
counts and generation provenance. Its omission from the prior proposed refresh
allowlist is closed explicitly here before mutation. Do not change validators,
workflows, runtime configuration, native authorization, migration history or
budget policy.

**Recovered native result and status meaning.** Full915 postgres-2 diagnostic
artifact12725018899 ZIP SHA256
6086bc95ed6279a3e47607003ea6fbd3b00a67bdd28692e9d60a2835acc2686d
contains the B report:19 cases, zero failures/errors/skips, report SHA256
d80851f32ee95cf66b5299629499c01d0480dbcd074e8b476b59a504245643ef.
The C HTTP report has12 cases, one failure and zero errors/skips; SHA256
9ae358eb8c953ad07f877e66f3bae11511ff62561ba91133aff1ee03a7300828.
The only failing case is expiredNativeSessionCannotReadOrAppend: the fixture
actually revokes a persistent native session after successful create; its still
cryptographically valid bearer reaches native Identity, which explicitly denies
non-current state. ProtectedOperationIdentityParticipant.finish rejects
!states.current(binding,sample) using AccessDeniedException. The unchanged
application translates explicit AccessDeniedException to DENIED; the unchanged
controller maps DENIED to403. This is the already documented owner-denial
contract, distinct from missing/malformed bearer401 or unavailable503.

Replace the erroneous 401-or503 oracle with exact403 and DENIED/detail
assertions for both read and update. Require no metadata/identity/SQL disclosure,
no repository apply call, unchanged history versions/digests and unchanged draft
row count. Preserve the case name and all other11 HTTP cases. Inventory changes
only this suite's actual source hash: no changed case membership, lane, timing,
partition or workflow. This tightens the rejection proof; it does not add
alternate accepted statuses or relax native authorization.

**Actual API capture admission.** Full915 API job122128888326 successfully built
the application (step8) and generated its deterministic OpenAPI (step9), then
failed existing snapshot equality atstep10; compatibility and dictionary steps
were skipped. Diagnostic artifact12725042645 ZIP SHA256
8f9ed8fd5651b64d761b508d263f69f67a6696b731d4f2db66b72fbbd38f558d
contains actual openapi/hidra-api.json stamped with the exact21ee286 source.
Generation success is scoped only to those steps; the API job and Full workflow
remain failed. The test-only correction here does not change production routes,
DTOs, annotations, native policy or runtime contract.

Use this actual capture for the canonical snapshot and exact manifest, retain
the old provenance under prior_reviewed_provenance, and record failed job/full
conclusions plus pending same-run equality/compatibility/dictionary gates.
Validate the three actual product operations, bearer schemes, required IDs/
expectedVersion, nullable GAS/LIQUID metadata and fixed error responses.
Compare using the unchanged supported comparator against actual successful
Full913/run40476182252 runtime output atbcaca876ae2ffe1627540d82eec7f3e8f002bc43,
artifact12721426411 ZIP SHA256
7d53c2a676caf074736101002328538d88efd57779b1c9a73fa0f297962f145e.
No physical dictionary change is admitted: production schema/JPA remain unchanged.

**Completed local artifact review and remaining gates.** The actual captured
contract contains271 paths,294 operations and288 schemas. All269 prior paths/
291 operations and284 schemas, non-schema components, security schemes and
other top-level contract fields are structurally unchanged; only two product
paths/three operations and four explicit product DTO schemas are added.
Required create IDs, positive integral expectedVersion, nullable GAS/LIQUID
labels, hidraBearerJwt and400/401/403/409/503 responses were inspected in the
actual capture. No inferred or handwritten runtime schema is substituted.

Actual raw current JSON SHA256:
6bef1e3cb25f37d49e30005c67d5429129f83b449beb2d01240bcf9da7f3047b.
Canonical snapshot SHA256:
599536d8226024b64093b9075f793fe5f445b4e93772d329c4583620cb8237f3.
Actual successful Full913 base JSON SHA256:
0f5d9d016194defa8208c2c08cefac7add0f1eb1c28d7e2d928e2c9d010237dc.
The unchanged repository snapshot validator PASSes canonical digest/count/
provenance checks and semantic equality against the source-bound runtime
capture. The unchanged supported OpenAPI comparator PASSes against the actual
successful Full913 generated document. These are local comparisons of captured
runtime outputs, not passing hosted API/dictionary gates on Full915.

Ten OpenAPI-validator self-tests and19 documentation-validator self-tests PASS.
The changed native test and four preserved HTTP sources parse using the
installed Java17 compiler module. The12 native test identities remain unchanged;
only its source hash changes in the inventory. No Java21/Spring/native test
rerun is claimed locally; Maven/Docker are not provisioned solely for that.
The complete corrected12-case native run and same-run API snapshot equality,
actual-base compatibility, physical dictionary capture/equality and overall
required workflows remain exact repair-SHA gates.

Only the five admitted MODIFY paths are published. This completes the pending
C snapshot refresh in the responsible repair but does not accept HAPI-594 or
advance HAPI-595..597. Preserve conservative parent status fields. Publish
directly to main with expected21ee286, verify exact scope/parent/tree/blobs/
message and observe applicable exact-SHA Full/Fast/Docs creation/queue/start,
then STOP. No wait for completion or automatic final acceptance. After green
required feedback and independent case-level evidence review, the next
registered closure remains `docs(custody): accept protected product draft authoring`.


### HAPI-594 final bounded software acceptance — 2026-10-27

Owner Next selects the separately registered closure
`docs(custody): accept protected product draft authoring`.
Accepted implementation source/main parent:
f16b71a4aa19188a5d58b3ad0bfa1470af8c391a; tree
fea2a5c349ef377c00ffc8dd10e193b76e6f17ce.
Exact-source Full916/run40488460576 attempt1, Fast222/run40488460596 and
Docs618/run40488460521 are completed/success. All required partition jobs and
the Full Verification Gate succeeded. The optional serial diagnostic job was
skipped by design; it is not reported as executed. Verification mode is
development-partitioned, serial_oracle=false, same_run_equivalence=false and
release_eligible=false. This acceptance does not change those restrictions.

This closure modifies only this owning phase record, the compact
ULTIMATE_TASKS execution pointer and HAPI-594's two status cells in each of
ULTIMATE_ROADMAP and ULTIMATE_PHASES. Zero CREATE/DELETE; no production, test,
migration, inventory, artifact, workflow or version change. HAPI-594 changes
from NOT_IMPLEMENTED/IN_PROGRESS to VERIFIED/COMPLETED for its registered
company-local product identity and metadata draft objective. Other task and
phase statuses, frozen descriptions, ordering and dependencies are preserved.

**Independent raw evidence verification.** Retained authenticated aggregate
artifact12727850130,
hidra-full-ci-evidence-f16b71a4aa19188a5d58b3ad0bfa1470af8c391a-40488460576-1,
has ZIP SHA256
26f84c8ebd8043677e608a9c3c03dd73e93ac84b422a6d2ac91ebe11b4d9821f.
The extracted gate, every lane evidence manifest and every retained file digest
were checked against actual bytes. Source SHA/run/attempt match the accepted
context. Inventory SHA256
f66477b4c1778f98acd628e487314c3cbea435a8de593c03385d121e0e256678
matches the exact-source repository inventory. Independently parsed472 raw XML
suites total3331 tests with zero failures/errors/skips, matching the gate counts.
These aggregate results are supporting regression evidence, not a substitute
for the task-specific matrix below.

All11 selected suites' source bytes were recovered from that exact GitHub SHA
and matched the inventory source fingerprints. Their176 exact case names match
both actual XML testcase membership and lane evidence membership. Six dedicated
product/owner suites contribute76 cases; five supporting route/architecture/
reader/executor suites contribute100 cases. No case was inferred from suite
names, skipped or accepted solely from an Actions summary.

| Suite (exact inventory class suffix) | Cases | Actual XML report SHA256 |
|---|---:|---|
| `ArchitectureGuardrailTest` | 18 | `d05489e5f8725e0501aa48c6cbbab81a495dddc7c141de97ca64f047069767af` |
| `ForensicRemediationClosureTest` | 7 | `ddac268d6c117da47c58954bb0b5a3c8128c004591f8037b71360d0e672e9ca0` |
| `ProductEngineeringDraftControllerTest` | 9 | `b56de344fc959c8d4024b0d8e6a4401ee65af4b3e36c528979dc7bcfb712a111` |
| `ProductEngineeringDraftTest` | 9 | `9d5bb66a6500b08b5f0acc720de69673e8fbcc12cf7894f3efeba786c020b25d` |
| `ProductEngineeringDraftNativeHttpPostgresIntegrationTest` | 12 | `cfc6152e85dd9eefddaedcf1ff7244c4e3a1ba962e596e2b8f98db8cb11d203b` |
| `ProductEngineeringDraftPostgresIntegrationTest` | 19 | `2e0178bc56592af68732d8636f2d5b6d44ae101e42fecfdfd0bc9d4b28ae2eee` |
| `ProtectedProductDraftAuthorizationPostgresIntegrationTest` | 16 | `e7c2959cc4b8e021dd55771f33fadfa55f83c23f1a84b8c58e10a34a7738d37c` |
| `JdbcProtectedAuthorizationEvidenceReaderTest` | 16 | `4a6e52505bd56f66276e86e9e9f60a933f770b94033ec304e44fffd33b731c10` |
| `CustodyProtectedPartyEligibilityPostgresIntegrationTest` | 11 | `a9a3128831e16554d3a7885e8becded9ba6a206da8a134da8291916e8c765828` |
| `HidraRouteAuthorizationInterceptorTest` | 12 | `2bd3c651cd51c0627ab07af73cea017f8ecb6a9aae83b9b18af74f241d350d36` |
| `ProtectedTransactionExecutorTest` | 47 | `6dd335b2fd32c9f97be0fda7c7c18d45f11cb13bec147a0e7d1f354fd5b9d977` |

The full class paths and source fingerprints remain in the accepted exact-source
inventory; the retained report hashes above bind the independently checked raw
case results. Original failed runs and repair records remain historical, with
no retroactive passing claim.

**Requirement-to-executed-evidence disposition.**

| Registered requirement | Accepted actual cases and boundary |
|---|---|
| Stable local UUID/company/code/catalog identity, metadata/null/Unicode scalar bounds and strict canonical codec | ProductEngineeringDraftTest9 plus controller9; malformed UTF-8, unknown labels, trailing bytes, coercion, immutable fields and overflow reject. No normalization or inferred property. |
| Both independent native company and product scopes; four real producer families; no borrowed credentials or fabricated evidence | ProtectedProductDraftAuthorizationPostgresIntegrationTest16; all actions/families, scoped DENY/missing/indeterminate, substitution, replay, cross-thread/suspend and current user/provider/session/grant rejection. CustodyProtectedPartyEligibilityPostgresIntegrationTest11 proves real owner status/generation and physical admission. |
| Actual transactional create/current read/metadata revision, exact original replay returning current metadata, uniqueness/CAS and isolation | ProductEngineeringDraftPostgresIntegrationTest19 and native HTTP12; two companies share catalog/code without disclosure, foreign/missing targets deny uniformly, stale updates/duplicate codes/conflicting replay leave exact rows unchanged, concurrent same-version writers yield one successor. |
| Authority/storage revocation, fences, time and immutable history | Native19/HTTP12 exercise writer-first/owner-first catalog-policy behavior, revoked Party/session/scopes, forged rows/sequence/origin, trigger/privilege checks, admission misuse, final expiry/fence substitution and rollback of provisional rows. Existing500ms budget remains enforced; no performance or all-load latency guarantee is inferred. |
| Real configured HTTP path and safe DTO/errors | HTTP12 proves all four native bearer round trips, missing/malformed401, current scoped/revoked-session403, authorized409 and final-guard503, safe bodies/no retry invitation; controller9 covers strict shapes, scalar bounds, explicit nulls and fixed classified errors. Revoked-session case requires DENIED, no apply call and unchanged version/digest/row count. |
| Architecture and technical protected executor preserved | Architecture18, forensic7, reader16, route12 and executor47 supporting cases pass. Exact product/Party exports, caller sets, native reader pin, exact DML hashes, provisional admission, one scalar final guard and safe denial remain enforced. |
| Actual runtime API and physical catalog freshness | Same-run API lane successfully passes snapshot equality, actual successful-base compatibility and migrated dictionary capture/equality; reviewed captures and fingerprints below. |

**Accepted runtime contract and physical dictionary.** Same-run API lane
job122136074565 and its retained evidence identify the accepted source/run/attempt.
Actual runtime OpenAPI SHA256:
7b37e6c3f85d5677ca2205db494ad2bc581ef3bcc7d31cb4aec5d97c371986f7.
It contains271 paths/294 operations/288 schemas and equals the reviewed snapshot
semantically except the truthful capture source stamp. The reviewed snapshot
retains its actual generation source21ee2864991c920aedf085db76e09f0a370644af and
SHA256599536d8226024b64093b9075f793fe5f445b4e93772d329c4583620cb8237f3;
this acceptance does not restamp old captures. Actual compatibility base is
successful Full913 atbcaca876ae2ffe1627540d82eec7f3e8f002bc43; its same-run
generated JSON SHA256:
1dcf2742ff39b91d792131fae368186a6f6bc7df9de5c416a0b3d07cc7952f35.
The unchanged supported comparator was also rerun locally on those actual
current/base documents and passed. All prior paths/schemas/security are retained;
the three product operations and four DTO schemas are additive. POST201 new/
200 replay, GET200 and PUT200 retain native bearer, explicit company scope,
required identities/expectedVersion and400/401/403/409/503 semantics.

Actual same-run catalog SHA256:
e94788a87d30b60547f079ce8f69dd2d8291fb3c1ad9bda9c705137df0db1031.
Actual same-run rendered dictionary SHA256:
0eca7ba0850172d998fb38a2031db594d2a92f4a53329c64c65cd4f2fd03f855.
Source bundle SHA256:
18a637865d4575ee176cf0ec58e510e7b9e403b6f38c27557d1853d20437372a.
PostgreSQL16.15 catalog has175 migrations,470 JPA mappings and519 relations,
zero unresolved owners. verification.json reports reviewed_dictionary_checked=true.
The unchanged dictionary generator was rerun locally against the retained actual
catalog, exact source inventory and reviewed ownership; render/check passed.
Only its validated capture source line differs from the previously reviewed
dictionary. No inferred physical metadata, database reset, schema rewrite,
operational product seed or access grant is introduced.

**Accepted scope and preserved limits.** Policy A is implemented: Custody owns
company-local immutable draft identity/code with a distinct admitted active
shared catalog product reference. Optional metadata and explicit null GAS/LIQUID
classification remain authored labels; completeness is diagnostic only. Fresh
native create/read/update authority, ACTIVE Party, policy/catalog eligibility,
current replay, expected-version immutable append and safe HTTP mapping are
accepted for development with synthetic data. A/B/C and their responsible
repairs are not reexecuted by this governance closure. The initial proposed-path
and NOT_IMPLEMENTED statements earlier in this record retain dated applicability;
this final accepted source/evidence disposition supersedes their execution hold.

This does not accept supplied composition/properties/methods (HAPI-595),
public publication/history/owner exports (596), consumer qualification (597),
frontend integration, numerical/thermodynamic/hydraulic qualification, liquid
engine support, production/SITE calibration, benchmarking, deployment or release.
Existing gas-first contracts and catalog-ID consumers remain unchanged.
HAPI-595..597 remain separately blocked/unselected; completing a dependency
does not resolve their remaining semantic/file registration gates. No next task
is selected automatically.


**Closure validation.**19 existing documentation-validator self-tests PASS.
Exact local checks confirm all six phase task records, HAPI-594 status agreement
with both frozen registries, all921 index compatibility anchors and document
size bounds. Each frozen registry differs only in594's two status cells; every
other row is byte-identical. Phase history is append-only apart from594's two
header status fields, and all other task definitions are unchanged. Comprehensive
documentation-estate/link validation remains the new exact closure-SHA hosted
Documentation gate. No new Java/native runtime execution is claimed for this
documentation-only commit; accepted runtime evidence is the actual source above.

The new closure commit itself is documentation-only and its CI is pending until
published. Run available documentation self-tests and exact status/scope checks;
publish with expectedf16b71a, verify parent/tree/blobs/message and exactly four
MODIFY paths, observe applicable exact-SHA Documentation CI creation/queue/start
and STOP. Do not wait for completion or automatically implement a successor.

<a id="hapi-595"></a>
## HAPI-595 — Supplied composition, properties and methods

Phase: HPS-008 — Products and Fluid Properties

Legacy IDs: HPR-P25-053B

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

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


### HAPI-595 supplied-fluid source recovery and policy proposal — 2026-10-27

Owner Next selects this bounded preflight after HAPI-594 acceptance. Main/source
eb2cfb19aa58d97efb7b155795717b810c127f00, tree
1bbf51c72a0ddad6ec0715b396fc826ff9eef3a6; exact-source Docs619/run40490120575
completed/success. Runtime remains the accepted Full916/Fast222/Docs618 source
f16b71a4aa19188a5d58b3ad0bfa1470af8c391a. HAPI-594 is VERIFIED/COMPLETED;
do not reimplement its metadata/API/authorization packages.

Exact preflight message:
`docs(custody): propose supplied fluid representation policy`.
Current write allowlist is exactly this HPS-008 phase file and the compact
ULTIMATE_TASKS execution pointer. Zero CREATE/two MODIFY/zero DELETE.
HAPI-595 remains NOT_IMPLEMENTED/BLOCKED in its header and both frozen
registries. This stage proposes policy and records actual source; it does not
register executable production paths or infer approval from generic Next.

**Existing owner decision gate.** The task's preserved source explicitly requires:
"Choose representational property/composition families to support as metadata,
keeping numerical qualification explicitly unsupported."
HAPI-594's approved GAS/LIQUID identity labels do not choose fraction bases,
property families or method semantics for595. The owner must select or amend
the concrete proposal below before its implementation registration. No real
product data, operating measurements, method license or deployment calibration
is needed for this software representation decision.

**Recovered current source, distinct from proposed behavior.**
- CustodyGasFluidRevision supports ProductKind.GAS and GAS_MOLE_FRACTION only.
  Components are positive exact BigDecimal fractions <=one, unique by reference,
  sum exactly to one, and belong to an explicitly supplied method component list.
  Source/method origin, evidence, recording/effectivity, pressure/temperature
  applicability ranges, declared STEADY_STATE/TRANSIENT uses and governance
  binding are explicit. It has no general scalar-property list, MASS_FRACTION,
  liquid representation or new product-draft public authoring flow.
- SimulationGasFluidRevisionContract exports that existing immutable stored/
  qualified GAS family; SimulationGasFluidInput likewise requires exact supplied
  mole fractions and source/method revision evidence. They do not accept a
  ProductEngineeringDraft UUID or a LIQUID metadata label as qualified input.
- CustodyGasFluidQualificationService rechecks the actual current shared product
  snapshot and exact source-bound Workflow approval at coherent source/method
  times. A new supplied declaration must not bypass or implicitly satisfy it.
- CustodyGasFluidRevisionCodec has existing versioned gas/source/method/
  qualification formats. Preserve their exact bytes, IDs and replay behavior;
  do not extend an old discriminator to reinterpret stored history.
- ProductEngineeringDraft is now the separate stable company-owned local
  identity/metadata contract, referencing an admitted shared Custody product.
  Its null/GAS/LIQUID classification and metadata diagnostics are not composition,
  supplied-property completeness or engineering qualification.
- TelemetryUnitJpaEntity owns unit ID/code/symbol/dimension/activity and optional
  conversion metadata. A matching text symbol or an existing conversion factor
  does not establish approved usage for fluid properties. The inspected
  PlanningUnitReferenceContract/QueryAdapter is specifically QUANTITY/RATE
  nomination-role/pair admission, not a reusable fluid-property authority.
  No Custody-protected fluid unit export, quantity-dimension usage policy or
  atomic unit revocation proof was established. Admit that exact owner seam
  during technical registration; never import Telemetry's private JPA adapter
  into Custody or reuse Planning grants as a substitute.

Exact-source fingerprints:

| Existing source path | SHA256 |
|---|---|
| `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyGasFluidRevision.java` | `d103b5c72f44595e0faf618b13f871c95a66ac92eaea68c363e0c940b1aa53af` |
| `src/main/java/dz/sh/hidra/modules/custody/domain/model/ProductEngineeringDraft.java` | `0ec36fe66cb8e7dcbd6cf427ae09d08f6001bb4e29d54ecb1216a4ccc38ba64b` |
| `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java` | `d7871364531ee5ce415e33f7a3157220e0cf91261969d46dadcbcf81dc6bc04d` |
| `src/main/java/dz/sh/hidra/modules/custody/application/contract/simulation/SimulationGasFluidRevisionContract.java` | `368ac22f3d92d557f2daff738f7a47aa38176a4a1a728f5aff127a8905e6edc4` |
| `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationGasFluidInput.java` | `d63141b149dd80f8ebb8696edece940f73e61a808ddf1ce1f6118d4c83383b19` |
| `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryUnitJpaEntity.java` | `53743e123fecd5da2e3afc5878702cb51780f603cd378fc390ec1d8ab764c4e9` |
| `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningUnitReferenceContract.java` | `6ad11c42eb837967e88b73d55d3aaa15517267d2c1d5158f746e3183b8749cae` |
| `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/integration/PlanningUnitReferenceQueryAdapter.java` | `8110ed3ed7b34b3e6c553d22a06b7574baff2a7dd265c742602c987d61e5890a` |
| `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/CustodyGasFluidRevisionCodec.java` | `a45749172152feaa60f4d934ace27cd86308176050343260265008729b7ed791` |

All six SuppliedFluidPropertyRevision production filenames originally proposed
in section6 are absent from the pinned recursive tree. Their earlier PROPOSED
labels remain accurate; no unregistered type is created by this preflight.
The last current Flyway entry is
V20261026_002__custody_product_engineering_drafts.sql. Reserve no new migration
version for this unselected runtime package. Existing source tests found include
CustodyGasFluidRevisionTest, CustodyGasFluidRevisionCodecTest,
CustodyGasFluidRevisionPostgresIntegrationTest,
CustodyGasFluidQualificationServiceTest and
CustodyGasFluidRevisionQueryServiceTest; source presence is not new execution.

**Recommended Policy A — bounded supplied declarations, awaiting owner choice.**

| Family | Proposed supported representation | Explicit exclusions/meaning |
|---|---|---|
| Composition | MOLE_FRACTION or MASS_FRACTION, explicitly selected, for an explicitly labeled GAS or LIQUID product draft | A declaration of basis; no conversion, phase-equilibrium result, inferred component, normalized total or numerical eligibility |
| Supplied scalar properties | DENSITY and DYNAMIC_VISCOSITY for GAS/LIQUID; COMPRESSIBILITY_FACTOR for GAS only | Positive exact supplied decimals; no calculation, fitted correlation, inferred liquid Z, heating-value or additional property code |
| Units and reference conditions | Owner-admitted unit ID/dimension, explicit absolute pressure and temperature values with their own admitted units for each supplied property observation | No gauge-to-absolute conversion, unit conversion, standard-condition default, atmospheric assumption or symbol-only admission |
| Method applicability | Explicit method reference/revision, origin/evidence, representation/basis, covered components/property families and declared pressure/temperature validity ranges | A supplied applicability statement; no standard certification, equation implementation, method catalog seed or solver selection |
| Provenance and completeness | Explicit source reference/revision/evidence and origin DECLARED_PARAMETER or SYNTHETIC; missing optional blocks retain named incomplete diagnostics | Synthetic evidence is never silently promoted; metadata completeness does not certify physical truth or method applicability |
| Revision binding | Separate company-owned supplied-fluid revision linked to exact local product UUID, shared catalog ID and metadata version/classification snapshot | No relink/ownership transfer, silent reclassification, replacement of594 metadata history or automatic export/publication |

1. Composition, property and method blocks may be absent while drafting, with
   separate diagnostics. A supplied composition block must declare one basis,
   a nonempty unique component-reference list, positive fractions <=one and an
   exact sum of one. Reject supplied malformed totals/bases/duplicate children;
   absence is not a fabricated zero fraction or an automatic balance component.
   Neither mass nor mole fractions are percentages. Do not normalize or
   convert between bases. Component references and method coverage are explicit
   Custody-owned declarations; this does not invent an enterprise chemical
   component master or mark arbitrary references as certified substances.
2. Property observations preserve exact supplied decimal text/value, unit
   identity and source/reference conditions. Density and dynamic viscosity
   must be positive; GAS compressibility factor is explicitly dimensionless
   and positive without an invented universal upper bound. Its dimensionless
   unit still requires explicit reviewed owner semantics. Reject unsupported
   property codes, incompatible family/unit dimensions, invalid value kinds,
   duplicate observation identity, unsupported pressure/temperature bases and
   incoherent supplied ranges. Numeric format/size and child-count limits
   remain a concrete technical-registration obligation, not an unbounded JSON
   or arbitrary property-name map.
3. GAS/LIQUID are the explicit594 authored classification snapshot, not an
   inference from name/catalog code or measured phase. Before accepting
   composition/property facts, require the linked draft to have a supported
   explicit classification at that exact metadata version; absent/unsupported
   classification cannot be silently treated as GAS. Later metadata revision
   never rewrites the stored snapshot or automatically revalidates old facts.
4. Evidence may remain absent for an incomplete draft; a present malformed or
   contradictory block rejects instead of being hidden by an incomplete flag.
   Missing method/source/evidence yields explicit diagnostics. A supplied
   method must declare coherent basis/family/component coverage and ranges;
   no applicability claim is manufactured when required facts are absent.
   Sources, methods and reference conditions retain their original units and
   provenance; no conversion factor is executed by this metadata authoring.
5. Custody remains product/composition/property semantic owner; Telemetry
   remains unit/dimension owner, Identity owns native authorization and Party
   owns company eligibility. Technical registration must define an explicit
   protected Telemetry export and authoritative unit usage admission, exact
   owner permissions, relation fences, current revocation, canonical codec,
   immutable version/CAS/idempotency, rollback and non-disclosing HTTP errors.
   Existing594 grants do not automatically authorize new supplied-property
   writes, and a plain controller/Boolean unit precheck is not sufficient.
6. Preserve every existing GAS source/method/qualification payload, enum meaning,
   native credential family and Planning/Simulation catalog-ID contract.
   No new declaration becomes executable SimulationGasFluidInput or qualified
   revision by structural similarity. Publication/history/owner exports belong
   to596 and compatibility qualification to597. Numerical EOS, fraction/unit
   conversion, property estimation, solver execution, H2-specific qualification,
   production calibration and releases remain outside this policy.

**Alternative B — narrower first delivery.** If the owner prefers a smaller
initial support set, admit GAS plus MOLE_FRACTION only, the same explicit
density/viscosity/GAS-compressibility observations and method/evidence rules;
defer MASS_FRACTION and all LIQUID fluid-fact representations. This reuses the
existing gas-first representation without changing its historical bytes.
Choosing B changes the proposed595 scope explicitly; it does not claim that
the existing internal gas revision already completes protected595 authoring.
The owner may instead amend exact property families/bases before registration.
A generic Next does not silently approve A, B or an expanded property registry.

**Next technical gate after explicit policy selection.** Recover the real native
Identity scopes and Telemetry owner admission/currentness seams; close exact
domain/DTO/codec/schema/SQL/storage/permission/export paths and numerical/text/
child-count bounds. Register bounded packages, tests, exact migration filenames
after checking the live writer chain, actual API/dictionary capture obligations
and exact commit messages. Register before implementation and before changing
595 planning from BLOCKED; source presence alone never yields VERIFIED.
Proposed registration message after policy selection:
`docs(custody): register supplied fluid property execution`.
No additional owner-contract filename, permission code or immutable migration
filename is invented as executable scope by this proposal.

**This preflight's checks and stop.** Actual pinned source/model/codec/export/
unit ownership recovery, nine source fingerprints, absent-six-proposed-file
check and live prerequisite/CI evidence have been recorded. Available existing
documentation self-tests and append-only/scope/status checks must complete
before publication. No Java/native/PostgreSQL execution, new API/catalog capture,
property calculation or supplied-fluid runtime acceptance is claimed.
Preserve all status fields, other task history, production/test/migration/
inventory/artifact bytes, workflow weights and the accepted594 baseline.
Completed checks:19 existing documentation-validator self-tests PASS. Exact
checks confirm append-only595 history, isolated compact index insertion,
unchanged status metadata, six phase tasks,921 compatibility anchors and size
bounds. Full documentation-estate/link validation remains the hosted exact-SHA
Documentation gate; no complete Java/service environment was provisioned.
Publish this one two-document preflight directly to main with expectedeb2cfb1,
verify exact parent/tree/blobs/message/scope, observe applicable exact-SHA
Documentation CI creation/queue/start and STOP. Policy selection is the next
gate; do not implement595, start596/597 or make deployment data obligatory.


### HAPI-595 Policy A approved and supplied-fluid execution registered — 2026-10-27

**Owner decision.** The owner explicitly selected "A" in response to the published
A/B representation choice at3e8f32c9ea313c0021dae32731e6fe7f90c86095.
All six numbered Policy A rules in the preceding preflight are adopted.
Support declared MOLE_FRACTION and MASS_FRACTION for explicit GAS/LIQUID
product metadata; supplied DENSITY/DYNAMIC_VISCOSITY for either label and
COMPRESSIBILITY_FACTOR for GAS only. Preserve explicit units, absolute reference
conditions, source/method applicability and evidence. No numerical execution,
fraction/unit conversion, physical inference or automatic qualification follows.
The prior awaiting-selection statements retain their historical applicability;
the choice is now resolved and must not be requested again.

Source/main parent3e8f32c9ea313c0021dae32731e6fe7f90c86095; tree
f41db009f1c737fdc269c9cd52f7c9523df27ed7. Exact-head Docs620/run40491859779
completed/success. Accepted runtime remains Full916/Fast222 source
f16b71a4aa19188a5d58b3ad0bfa1470af8c391a, with HAPI-594 VERIFIED/COMPLETED.
Current exact message:
`docs(custody): register supplied fluid property execution`.

This registration modifies exactly this owning phase record, the compact
ULTIMATE_TASKS pointer, and595's planning status cells in ULTIMATE_ROADMAP and
ULTIMATE_PHASES. HAPI-595 becomes NOT_IMPLEMENTED/IN_PROGRESS, not implemented
or verified. Zero CREATE/DELETE. No runtime/test/schema/workflow/artifact change
in this documentation stage. Three sequential runtime packages below are
registered individually, not as an automatic batch; each needs its own owner
Next and exact-head prerequisite feedback. Final acceptance is separate.

#### Recovered exact seams and preservation constraints

The inspected current Identity product contract has only CREATE/READ/UPDATE,
PRODUCT_ENGINEERING_DRAFT and custody:engineering-product:* permissions.
ProtectedOperationIdentityParticipant has an issuer-bound ProductClaimPair;
JdbcProtectedAuthorizationEvidenceReader checks that exact product family,
permission/resource/scope triple and fresh database-time graph. It cannot safely
authorize property facts merely by adding strings to the old metadata whitelist.
Register a separate typed native family and exported contract below, while old
product, Topology, organization and diagnostic credential predicates remain
exclusive. No old metadata grant becomes a property-write grant.

The actual ProtectedTransactionExecutor restricts exact compiled service/
coordinator callers and exact product DML hashes. Extend it only for the new
concrete supplied-fluid service/coordinator after their code exists. Its ordinary
credential router, original family callers, statement grammar and500ms standard
work budget remain unchanged. No general arbitrary INSERT/UPDATE callback.
ProtectedStationAdmissionBoundaryGuardrailTest currently pins the native reader
to cf9f662ffc47df6117f20ca179e1471d167732f3dfc88844aba21cca166b4659;
only a source-reviewed newly computed reader hash may replace that pin when A
actually changes the reader. Preserve all other owner pins, negative assertions
and exact caller membership. Pin updates are not test relaxation.

The inspected Telemetry unit entity owns unit ID/code/symbol/dimension/activity.
Its Planning export admits only nomination QUANTITY/RATE roles/pairs. Add a
Custody-specific protected owner export and empty unit-usage policy; never
repurpose Planning roles, import private Telemetry JPA types or trust a request's
dimension/symbol as owner evidence. The existing Custody Party contract remains
the exact ACTIVE-company/generation owner seam. The supplied-fluid coordinator
is the sole new cross-module consumer of those exported roots/nested scalars.

All23 CREATE paths below were verified absent and all12 distinct existing
production/test MODIFY paths verified present in the pinned recursive tree.
Last migration is V20261026_002__custody_product_engineering_drafts.sql.
The three additive filenames below are reserved for the ordered packages;
recheck the live chain before each package and reconcile any collision before
mutation. Never rewrite a published migration or create operational unit/product/
method seeds or role/user grants.

#### Shared supplied-fact and revision contract

- Link each property draft to one existing company-local product draft UUID,
  immutable company/code/catalog reference and an exact authored metadata
  version/classification snapshot. Properties have their own monotonic version
  sequence, not594's metadata sequence. A new write requires the caller's
  expectedProductMetadataVersion to match the actual current metadata revision
  and explicit GAS/LIQUID classification. Current catalog/product policy,
  company eligibility and native authority remain mandatory.
- READ returns the current supplied revision only. If product metadata later
  changes, retain the authored snapshot and expose metadataBindingCurrent=false
  with an explicit stale-binding diagnostic; do not reinterpret old GAS facts
  as LIQUID or requalify them. New writes must bind the new current metadata
  version afresh. Current native/unit/policy eligibility is still checked before
  disclosure; historical storage never grants current access.
- WRITE accepts requestId canonical lowercase UUID and expectedVersion>=0:
  zero only creates the first supplied version1; a successor requires the exact
  current positive property version and appends exactly one immutable version.
  Never edit or delete an earlier version. Unique(productDraftId,requestId)
  binds original expected versions and exact supplied payload. Freshly authorized
  exact replay returns the current supplied revision without reverting later
  facts; different original request content is an authorized409. A metadata
  change alone does not mutate original replay bytes or create a new revision.
- Canonical supplied decimals are JSON strings, with grammar
  `(0|[1-9][0-9]{0,33})(\.[0-9]{1,18})? and no sign/exponent/whitespace.`
  Preserve the original accepted decimal lexeme and scale in a new versioned
  strict UTF-8 codec; do not pass through binary floating point, trim, round,
  strip trailing zeros or normalize a fraction total. Numeric equality validates
  ranges/sums; exact request replay binds exact stored lexemes. Positive values
  only for supplied fractions/properties/absolute pressure/temperature; fractions
  <=one and composition sums exactly to one using BigDecimal arithmetic.
- Composition is optional/null; a supplied block must have one explicit
  MOLE_FRACTION/MASS_FRACTION basis and1..64 distinct non-null components.
  Component references are exact1..80 Unicode scalars, source declarations
  rather than invented certified chemical identities. Null composition is
  incomplete, not a zero-filled or normalized blend. GAS/LIQUID labels are
  representation metadata, not measured phase equilibrium.
- At most32 property observations, each with a distinct exact1..80 scalar
  observationId, one of the three approved property codes, explicit value and
  owner-admitted unit, absolute pressure and temperature values/units, and its
  source/method references when supplied. GAS Z requires the explicit
  dimensionless owner role and has no invented universal upper bound. Reject
  LIQUID Z, unknown property codes/bases, numeric JSON coercion, duplicate
  children, contradictory units/family/ranges and unsupported absolute bases.
  No conversion factor or atmospheric/standard-state default is executed.
- At most16 inline method declarations, distinct by exact reference/revision;
  each supplied method declares origin, representation/basis, component/property
  coverage and positive ordered pressure/temperature applicability bounds with
  admitted absolute units. Evidence/source completeness stays distinct from
  this structural validity. A missing method/source/evidence gives explicit
  incomplete diagnostics; a supplied contradictory method cannot be concealed
  by an incomplete flag. Method references are authored declarations, not
  approval of a named standard or executable equation.
- Reuse594 exact Unicode validity/no-NUL/no-surrounding-whitespace rules.
  Identity/reference fields max80 scalars, observation names max240,
  descriptive text max4000, evidence references max1000. Optional null and
  explicitly empty allowed collections remain distinguishable in the codec;
  composition itself cannot be an empty supplied component block. Bound any
  method component list to64 and unit requests to128 distinct role/unit pairs;
  reject overflow before owner reads. Properties/methods may be absent/empty
  with explicit diagnostics. Never silently synthesize evidence or properties.
- Source/method origin is DECLARED_PARAMETER or SYNTHETIC; synthetic evidence
  cannot be relabeled as nonsynthetic qualification. No caller actor, grant,
  computed completeness, qualification, audit time/PID/xid or generated digest
  is accepted. Persist server actor and actual transaction evidence separately.
  This complete/incomplete authoring status is not thermodynamic or hydraulic
  qualification.

#### Package A — distinct native authorization and protected Telemetry units

Exact message:
`feat(custody): add protected supplied fluid owner contracts`.

CREATE exactly:
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/custody/CustodyProtectedFluidPropertyAuthorizationContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedFluidPropertyAuthorizationParticipant.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProtectedFluidPropertyAuthorizationPostgresIntegrationTest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/custody/CustodyProtectedFluidUnitContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JdbcCustodyProtectedFluidUnitAdapter.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/CustodyProtectedFluidUnitPostgresIntegrationTest.java`
- `src/main/resources/db/migration/V20261027_001__custody_fluid_property_authority.sql`
- `src/main/resources/db/migration/V20261027_002__telemetry_fluid_unit_policy.sql`

MODIFY exactly:
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedOperationIdentityParticipant.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java`
- `src/test/java/dz/sh/hidra/modules/identity/OperationAuthenticationBoundaryGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedOperationIdentityParticipantTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReaderTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java`
- `.github/ci-test-inventory.json`
- `.github/database-dictionary-ownership.json`
- `doc/roadmap/TASKS_HPS-008.md`

Identity export actions are READ and WRITE, resource type
PRODUCT_ENGINEERING_PROPERTIES, permission codes
custody:engineering-product-properties:read and
custody:engineering-product-properties:write. Require independent native CUSTOM
custody-company:<companyId> and custody-product:<catalogProductId> scopes for
the same action/resource and one fresh binding/session/source contribution.
These scopes do not confer property knowledge or Planning/simulation approval.
The authority migration inserts only these two permission definitions, checks
an existing conflicting definition fail-closed and assigns no grants.

Add a separate issuer-tagged SuppliedFluidClaimPair/currentness path in the
actual native participant/reader and the exported opaque preparation/admission
owner. Authentication tokens remain genuine existing native LOCAL/OIDC/LDAP/
ACTIVE_DIRECTORY producers. Only the exact new Identity participant may consume
its native family. Preserve fresh common database sampling, graph/effective
responsibility/DENY precedence, strict resource/scope matching, failure poisoning,
thread/connection/holder identity and one-use/current transaction semantics.
Old metadata/Topology/organization/station/diagnostic tokens must reject rather
than aliasing this family. Test four producers and both scopes/actions, missing/
DENY/indeterminate, expired or revoked user/provider/session/grant/source,
substitution, replay, mixed/forged credentials, cross-thread and suspension.
Existing-family regression cases and their owner/private source pins remain.

Telemetry's new empty hidra_telemetry_fluid_unit_policy is keyed by unit ID and
one fixed usage role: DENSITY, DYNAMIC_VISCOSITY, COMPRESSIBILITY_FACTOR,
ABSOLUTE_PRESSURE or ABSOLUTE_TEMPERATURE. It holds explicit expected dimension
and active state, with an owner-local unit reference and strict role/uniqueness/
nonblank constraints. A request cannot invent or override the dimension policy.
No seed or inference from symbol, baseUnitId or conversion factor. COMPRESSIBILITY
requires the explicitly admitted dimensionless semantic role; reference pressure/
temperature require explicitly admitted absolute-unit roles.

The owner adapter takes a bounded set of typed role/unit IDs, establishes
deterministically ordered NOWAIT relation fences on hidra_telemetry_unit and
hidra_telemetry_fluid_unit_policy, verifies actual role/catalog/table safety and
collects only active units with matching admitted roles/dimensions. Export scalar
snapshots, opaque issuer/thread/holder/connection-bound one-use admission and
the exact two final fences. Unit ID/code/symbol/dimension/role/activity are
snapshotted; conversion metadata is never executed. Preserve fences through
commit/disclosure and recheck current eligibility under the common final guard.
Ordinary application credentials must not mutate the unit policy; any private
publisher privilege needed for relation admission is bounded by the protected
credential route/statement grammar. No unrelated DML permission is admitted.
Tests prove valid owner semantics, missing/inactive/wrong role/dimension,
writer-first/owner-first races, rollback, fabricated/replayed/cross-thread tickets,
lost locks and privileged/unsafe physical metadata rejection.

Register only actual authored test names/hashes and existing lanes. Add one
explicit ownership row for the new Telemetry policy table. The physical dictionary
must be refreshed from the actual migrated catalog, never inferred. If unavailable
locally, retain a truthful pending capture obligation under AGENTS11.1. After
source feedback, admitted artifact-only message:
`docs(telemetry): refresh fluid unit policy dictionary`;
MODIFY only doc/database/DATA_DICTIONARY.md and this phase record. No generation
claim from a skipped database test and no equality/ownership validator weakening.
A alone has no public property API or Custody property storage.

#### Package B — Custody supplied revision, canonical storage and atomic admission

Depends on acceptedA and its actual dictionary gate. Exact message:
`feat(custody): add versioned supplied fluid property drafts`.

CREATE exactly:
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/SuppliedFluidPropertyRevision.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/SuppliedFluidPropertyRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/SuppliedFluidPropertyRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/SuppliedFluidPropertyRevisionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/ProtectedSuppliedFluidPropertyAdmissionCoordinator.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JdbcSuppliedFluidPropertyRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/SuppliedFluidPropertyRevisionCodec.java`
- `src/test/java/dz/sh/hidra/modules/custody/domain/model/SuppliedFluidPropertyRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/SuppliedFluidPropertyPostgresIntegrationTest.java`
- `src/main/resources/db/migration/V20261027_003__custody_supplied_fluid_properties.sql`

MODIFY exactly:
- `src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java`
- `src/test/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutorTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `.github/ci-test-inventory.json`
- `.github/database-dictionary-ownership.json`
- `doc/roadmap/TASKS_HPS-008.md`

Implement the shared contract through pure Java domain, typed ports and explicit
JDBC codec/storage. Reuse accepted594 identity/catalog/metadata history without
editing its aggregate, SQL, codec, migration or tests. New owner tables are
hidra_custody_supplied_fluid_property_draft and
hidra_custody_supplied_fluid_property_version. Enforce one immutable root per
product draft, unique write request identity, positive contiguous append-only
sequence, expected-version CAS, strict payload format/digest, immutable version
bytes and origin/transaction/audit integrity. Bind product metadata version to
the actual existing Custody version history using only owner-local references;
no cross-owner Party/Telemetry aggregate foreign key.

The concrete coordinator is the sole cross-owner consumer of the exact new
Identity export, Telemetry export and existing Custody Party export roots/nested
data. Resolve actual company-bound product/catalog binding without revealing
existence before native authority; missing/foreign targets share the accepted
non-disclosing DENIED403 outcome. Collect Identity24, Party2, Custody6 (existing
four product/catalog/policy relations plus the two new roots/history relations)
and Telemetry2 relation fences:34 distinct reviewed fences in one physical
transaction. No caller-supplied scalar permission substitutes for an admission.

Extend ProtectedTransactionExecutor only for the two exact concrete new callers
and their actual reviewed DML statement SHA256 templates. Keep all original
family constants/hashes/caller sets, credential routing, SQL admission grammar,
standard500ms work budget,750ms statement timeout and250ms lock timeout. Recheck
native/unit/catalog/Party eligibility, metadata pin, actual held relation fences,
physical trigger/function/schema/role safety and common final database deadline.
Exactly one closed scalar SQL final guard seals disclosure/commit; it performs
no provisional DML or post-guard application query. All rejection/failed guard/
stale CAS paths roll back provisional rows, and unknown commit stays explicitly
indeterminate without an automatic retry. Avoid repeated identical expensive
native graph rereads at the same boundary; preserve before/after-write checks.

Add only the exact new coordinator/export pair to actual architecture/import
scans and their classifier tests, with sibling/private/foreign-consumer negatives.
Do not change accepted594 grants, Party adapter, raw catalog APIs or other owner
exports. Register two exact Custody dictionary ownership entries. Test all
supported/unsupported shapes/units/bases and decimals, strict canonical payload
round-trip/corruption, incomplete-vs-invalid behavior, exact replay, stale metadata/
property version, concurrent writes, two-company isolation, current revocations,
opaque admission misuse, forbidden ordinary DML, history immutability and real
final-guard rollback. No mocked Boolean alone proves protected storage.

After actual source feedback, admitted artifact-only message:
`docs(custody): refresh supplied fluid storage dictionary`;
MODIFY only doc/database/DATA_DICTIONARY.md and this phase record. Capture truth
from the complete actual migrated PostgreSQL catalog, retain migration history
and zero-unresolved-owner/equality gates; no handwritten physical metadata.
B is internal software only until C proves configured HTTP integration.

#### Package C — configured HTTP integration, actual runtime artifacts

Depends on acceptedA/B. Exact message:
`feat(custody): expose supplied fluid property draft operations`.

CREATE exactly:
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/SuppliedFluidPropertyRevisionController.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/SuppliedFluidPropertyRevisionRequests.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/SuppliedFluidPropertyRevisionResponses.java`
- `src/test/java/dz/sh/hidra/modules/custody/api/rest/controller/SuppliedFluidPropertyRevisionControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/SuppliedFluidPropertyNativeHttpPostgresIntegrationTest.java`

MODIFY exactly:
- `src/main/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptor.java`
- `src/test/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptorTest.java`
- `.github/ci-test-inventory.json`
- `.github/documentation-validation.json`
- `doc/api/openapi.yaml`
- `doc/database/DATA_DICTIONARY.md`
- `doc/roadmap/TASKS_HPS-008.md`

Expose exactly GET and PUT
/api/v1/custody/engineering-products/{draftId}/properties, both with a single exact
companyPartyId query. PUT supplies requestId, expectedVersion,
expectedProductMetadataVersion and bounded optional composition/property/method/
source blocks above. GET has no client-selected expected-version or actor.
Explicit strict DTOs reject unknown/coerced fields, duplicate/extra scopes and
server-owned facts. Return distinct product/shared-catalog/property revision
identities, authored metadata binding, current supplied version, exact supplied
facts, explicit null/incomplete diagnostics and metadataBindingCurrent.

PUT returns201 for the first property revision,200 for successor/exact current
replay; GET200. Preserve400 malformed/unsupported supplied facts,401 missing/
malformed authentication,403 explicit native/target denial,409 authorized CAS/
identity/replay conflict and503 unavailable/indeterminate evidence. Current unit
eligibility/owner proof failure is fail-closed unavailable, not fabricated valid
unit evidence. No native cause, SQL, stack trace or retry instruction escapes.
No list/search/delete/publication/history/export/solver endpoint.

The actual MVC interceptor must delegate only these two exact compiled
SuppliedFluidPropertyRevisionController handler/verb/route tuples to the native
owner after authentication. Existing metadata tuples and all unrelated static
route permission checks remain unchanged. Add positive and wrong-verb/pattern/
handler/controller/anonymous negatives; do not grant generic route permission
to bypass the new property owner. Standalone DTO tests do not prove native auth.
Real configured filter/HTTP/PostgreSQL tests prove both scoped actions and four
producers, all shared revision/basis/unit/metadata-pin/diagnostic behavior, two
companies, deny/revocation/availability and no provisional effect after failure.

Generate OpenAPI only from the actual running application and dictionary only
from actual migrated PostgreSQL. Verify actual source/run/attempt, all old
routes/schemas/security and supported compatibility before adopting snapshots.
The existing manifest is an inseparable reviewed digest/count/provenance
companion; preserve previous provenance and report failed generation/job/
workflow gates accurately. If locally unavailable, use CI-first pending artifact
disposition and separately selected follow-up message
`docs(api): refresh supplied fluid runtime contract`;
MODIFY only doc/api/openapi.yaml, .github/documentation-validation.json and this
phase record, plus doc/database/DATA_DICTIONARY.md only for demonstrated actual
physical drift. No predicted or handwritten capture, source-stamp forgery,
skipped gate treated as success or validation script change.

#### Final acceptance and execution stop

After registered source exists, accept595 only from exact-SHA Full/Fast/Docs
as applicable, independent case/source/inventory/raw report membership and
actual same-run API/catalog/compatibility evidence. Retain software acceptance,
engineering qualification and production/SITE readiness as separate states.
Exact final message:
`docs(custody): accept supplied fluid property authoring`;
MODIFY only this phase file, ULTIMATE_TASKS and status-only595 cells in
ULTIMATE_ROADMAP/ULTIMATE_PHASES. Preserve all other task statuses and original
history. No596/597, frontend, numerical engine, calibration, release or deployment
is selected by this registration or its eventual acceptance.

**Completed registration checks.**19 existing documentation-validator self-tests
PASS. Exact checks confirm all six phase task records,595 metadata/status-table
agreement, all921 compatibility anchors, bounded document sizes and correct
literal decimal grammar. Both frozen registries differ only in595's planning
status cell; the owning phase preserves prior history apart from that same
header field. All other task definitions/statuses and accepted594 are unchanged.
Full documentation-estate/link validation remains the new hosted exact-SHA
Documentation gate; no local Java/native runtime result is asserted.

Current registration validation recovers the actual native family, source guard,
executor, MVC and unit-owner seams; verifies23 CREATE paths absent and12 distinct
existing production/test MODIFY paths present. Available documentation self-tests,
unchanged other task/status metadata, source-scope/history and frozen-table
status-only checks must complete before publication. No Java/native/PostgreSQL
execution, runtime API or new catalog capture is claimed in this documentation
stage. Publish directly to main with expected3e8f32c, verify exact four-document
scope/parent/tree/blobs/message, observe applicable exact-SHA Documentation CI
creation/queue/start and STOP. Package A is next only after this registration's
Documentation gate succeeds and a subsequent owner Next; no runtime package
executes automatically in this turn.


### HAPI-595 registration documentation CI repair — 2026-10-27

Owner Next encountered failed exact-head Docs621/run40493727477 at
4ec2ddca7cf84b5e95730638b3a7605c0c7a2524 before Package A's prerequisite gate.
Job122153776848 fails with "Unresolved reference link: 0-9": the registered
decimal grammar was plain Markdown, so adjacent character classes were read
as a reference link. The intended literal grammar itself is unchanged.

Exact repair message: `docs(custody): escape supplied decimal grammar`.
Write allowlist is only this owning phase file: one MODIFY, zero CREATE/DELETE.
Wrap the existing grammar line in inline code, preserving its characters and
meaning. Retain all prior history and append this failure/correction record.
Do not weaken the Markdown validator or change any runtime, status, package,
payload bound, owner decision, migration, source inventory or artifact.

Completed local checks: the unchanged link parser accepts the complete phase
(34 links), with all six task definitions retained;19 documentation-validator
self-tests PASS. No Java/native execution is required or claimed for this
formatting-only repair. Hosted comprehensive validation remains pending.

Policy A remains explicitly approved. HAPI-595 stays NOT_IMPLEMENTED/IN_PROGRESS.
Package A remains selected next only after the registration repair's
Documentation gate succeeds; no implementation follows this repair automatically.
Validate the actual complete phase with the unchanged link parser, run available
documentation self-tests and verify the one-file delta before guarded publication.
Observe applicable exact-SHA Documentation CI creation/queue/start and STOP;
do not wait for completion or treat failed Docs621 as a passing prerequisite.


### HAPI-595 Package A owner contracts implemented; hosted acceptance pending — 2026-10-27

Owner Next selects registered Package A after repaired exact-head Docs622/
run40494004250 completed/success on91de7246d6f3b28b7ae562b84dadc7c328eca31a,
tree576da637abaf0d8edb5d815eed2746be05008bfc. Policy A remains explicitly
approved. HAPI-594's accepted runtime baseline and all existing GAS/metadata
semantics are preserved. Current exact message:
`feat(custody): add protected supplied fluid owner contracts`.
This is the registered eight CREATE/six prior Java MODIFY paths plus the
inventory, one ownership entry and this phase record: exactly17 changed paths,
zero DELETE. No Package B/C implementation or final595 acceptance is selected.

**Distinct native Identity family.** The new
CustodyProtectedFluidPropertyAuthorizationContract exports READ/WRITE requests
and opaque issuer-bound Prepared/Admission, with explicit scalar contributions
and24 existing Identity relation fences. Its resource is
PRODUCT_ENGINEERING_PROPERTIES, permissions
custody:engineering-product-properties:read/write, with both exact independent
CUSTOM custody-company and custody-product scopes bound to one native action,
catalog ID and identity contribution. Scope identifiers validate exact Unicode,
NUL/whitespace and80-scalar bounds. No native metadata/product/Topology/
organization/diagnostic credential or old metadata grant authorizes this family.

ProtectedOperationIdentityParticipant issues only a new private-tagged
SuppliedFluidClaimPair. All old private constructors remain with the new tag
false; their old family predicates/contexts explicitly exclude it. The new
pair retains actual authentication, immutable binding, physical holder/
connection, one-use child reads, common contribution, active transaction/thread
and current native deadline. Its native database sample rechecks binding,
session/provider/source state and expiry, then the reader rechecks full scoped
graphs at that sample. The fresh sample cannot be supplied by a caller.

JdbcProtectedAuthorizationEvidenceReader adds only the typed native initial/
recheck entry points and a private branch for the exact new resource, two
permission codes and exact company/product scope shape. Public diagnostic
evidence still cannot select this family; old family validation bodies remain.
The new company-prefix bound uses the actual prefix length, including a native
80-character boundary test. The old family request/permission semantics are
not rewritten. ProtectedFluidPropertyAuthorizationParticipant evaluates both
actual graphs and compares complete fresh evidence/current decisions; rejected
reuse poisons the actual issuing transaction rather than accepting a caller
Boolean. All preparation/acquisition/read/currentness remain explicit physical
transaction checks; private entry/ticket/pair constructors are not exported.

V20261027_001__custody_fluid_property_authority.sql inserts exactly the two
permission definitions, rejects conflicting resource/action/status definitions
and assigns no grants, roles, operational products or units. Existing published
migration bytes remain unchanged.

**Telemetry unit owner.** CustodyProtectedFluidUnitContract exports typed fixed
roles DENSITY, DYNAMIC_VISCOSITY, COMPRESSIBILITY_FACTOR, ABSOLUTE_PRESSURE and
ABSOLUTE_TEMPERATURE; exact bounded unit IDs; immutable scalar snapshots; actual
backend PID/transaction; and opaque admission. Requests are unique, sorted and
bounded to128 role/unit pairs; empty requests support explicitly absent fluid
blocks without inventing a unit. Exact UTF-16/Unicode/NUL/whitespace validation
and copied collections prevent implicit aliasing. Snapshots do not execute
conversion factors, establish physical truth or serve as an authorization permit.

V20261027_002__telemetry_fluid_unit_policy.sql creates only the empty
Telemetry-owned role/dimension/activity policy, with local unit FK, unique
unit/role, five-role/check constraints and explicit coherent audit timestamps.
No unit seed, nomination-role reuse, arbitrary role, method default, production
data or user/role grant is introduced. A single explicit dictionary ownership
entry names this policy and actual migration/contract/adapter evidence.

JdbcCustodyProtectedFluidUnitAdapter acquires deterministic NOWAIT SHARE ROW
EXCLUSIVE relation fences on actual unit and fluid policy tables. It validates
READ COMMITTED, read/write/origin, non-elevated/non-owner/no-schema-CREATE/no-
TRIGGER credentials, ordinary permanent non-RLS/non-inherited/nonpartitioned
relations, absence of unexpected user triggers, policy column/key/FK/check shape
and valid unit primary key. Read/recheck verifies the retained locks, actual
PID/xid/role and the same issuing holder/connection/thread.

Each requested role/unit must have exactly one active admitted policy and active
unit with an exact matching declared dimension. Returned unit IDs, policy IDs,
role and dimensions are also compared using exact Java strings, independently
of database collation equality. Supplied dimension/symbol or conversion metadata
cannot manufacture admission. Currentness compares fresh owner snapshots;
private tickets reject reuse, foreign issuer, completed/read-only/wrong-thread/
lost-fence contexts. Rejection poisons the original ticket/holder, including
swallowed cross-thread misuse. The SQL-free commit vote rejects a poisoned
ticket. Package B must still collect these facts with native/Party/Custody
admissions and the one common final scalar guard before disclosure/commit.

**Authored executable cases, not yet hosted passing evidence.**
- ProtectedFluidPropertyAuthorizationPostgresIntegrationTest:18 cases, using
  genuine four-producer native fixtures/crypto/storage with directory/source
  transport fixtures only. Covers both exact scopes/actions, DENY/missing/
  indeterminate, company/product/action and family substitution, forged/replayed/
  moved/suspended admissions, user/provider/session/grant/source deadlines and
  revocation, rollback, migration idempotence/conflict/no-grants, exact maximum
  scope IDs and actual old-metadata grants denied for property writes.
- CustodyProtectedFluidUnitPostgresIntegrationTest:11 cases with actual full
  Flyway/PostgreSQL and proxied owner bean. Covers every fixed role/exact owner
  snapshot without conversion; missing/inactive/wrong-role/dimension; malformed/
  duplicate/oversize requests; transaction/read-only/isolation/issuer/replay;
  own-transaction unit/policy revocation and rollback; swallowed worker misuse
  requiring a commit-vote rejection; existing/absent writer-first NOWAIT;
  owner-first retained locks against both writers; lost savepoint locks; RLS/
  inheritance/unexpected triggers/elevated owner; and ordinary credentials denied policy mutation. Worker
  assertion failures cannot falsely satisfy the expected commit rejection.
- Three existing fast suites gain one case each: exact new native caller/
  private-pair boundaries; rejection of null/structural native credentials;
  rejection of diagnostic/forged reader selection. Existing case identities
  remain intact. Reader public-entry whitelist gains only the two actual typed
  methods; the exact F admission caller set gains only the new Identity owner.

The reviewed reader SHA256 pin is now
16b9aae3799086a800f6690ad64f26ac970604d788a9e6d289bd108b93746d72.
All other original owner pins, source-boundary assertions, executor callers/DML
hashes, credential routing, SQL admission grammar and time budgets are preserved.
No protected executor source, old metadata service, Party adapter or qualified
GAS domain/codec/export is modified in A.

Inventory adds exactly two PostgreSQL classes/29 real case names and the three
new fast cases; updates only the four changed existing test source hashes and
actual case lists. Baseline seconds=0 for the new classes means unmeasured.
Prior cases, lane flags, timing values, shard settings, fallback and original
partition membership are unchanged. No test is archived, disabled or removed
for a passing gate. Native tests require actual Docker and cannot be accepted
from skipped reports.

**Actual local validation and remaining gates.** All12 new/changed Java sources
parse using the installed Java17 compiler module. The two new pure contracts
plus unchanged relation-fence primitive compile as a Java17 subset, with16
standalone contract smoke assertions PASS.19 existing documentation-validator
self-tests PASS. Exact inventory/case/hash/lane/unchanged-partition, header,
scope and source-pin checks must complete before publication. These are not
Java21/Spring/JUnit/native PostgreSQL passing results. Maven and Docker are not
available locally; no full environment is provisioned merely to reproduce CI.

The accepted dictionary remains the previous actual catalog, not a fabricated
new physical schema. These two new migration sources and the real new policy
relation require a hosted migrated catalog and truthful regeneration. Existing
dictionary equality may therefore fail until the registered artifact follow-up.
Ownership is registered now; freshness is an explicit retained obligation, not
a waived test or a passing dictionary claim. After actual source feedback,
repair any responsible native/source errors first, then select the registered
`docs(telemetry): refresh fluid unit policy dictionary` using the actual capture.
Only after A and its real API/catalog/full gates are accepted may B proceed.

**Completed publication checks.** Exact inventory/source/case hashes, all prior
partition membership/settings, one added ownership row, immutable Java author/
creation fields, allowed file set and append-only task history PASS. The unchanged
link parser accepts the complete phase (34 links). Five exact new native caller
sets and the reviewed reader pin are checked against actual source. Twelve-file
syntax parsing was repeated after the final unit/collation and test corrections.
These checks retain the hosted execution/catalog gates described above.

HAPI-595 parent statuses remain NOT_IMPLEMENTED/IN_PROGRESS pending its complete
registered authoring flow and final acceptance; this record distinguishes
Package A source implementation from CI/software/engineering acceptance.
Publish one guarded main source commit with expected91de724, verify exact17
paths/parent/tree/blobs/message, observe applicable exact-SHA Full/Fast/Docs
creation/queue/start and STOP. Do not wait for completion, implement B/C,
restamp old artifacts, select596/597 or make deployment data obligatory.


### HAPI-595 Package A — Java test-compile repair, 2026-10-27

Owner Fail repairs only the introduced source-compilation blocker at exact parent
`0d6ec2c025456f72149169ec995eb20ce638b68a` (tree
`5c7b4588926637aeacbace790d4fb636b67f85f4`). Fast Feedback #223
(run 40499417710) and full CI #917 (run 40499417983) both failed.
The Java compiler reports ambiguous `Role` at lines 69/74 of
`CustodyProtectedFluidUnitPostgresIntegrationTest.java`, where the Telemetry
owner's `CustodyProtectedFluidUnitContract.Role` collided with
`org.springframework.context.annotation.Role` admitted by a wildcard import.
The same test-compilation error blocks the reviewed Java fast suite and every
selected full CI test shard; secondary missing Surefire/evidence artifacts are
consequences, not independently verified test failures.

Repair changes only the test's Spring context wildcard import into explicit
`AnnotationConfigApplicationContext` and `Configuration` imports, leaving the
Telemetry `Role` contract, all eleven test cases, actual PostgreSQL checks,
transaction/authorization invariants and production behavior unchanged.
Update only its `.github/ci-test-inventory.json` `source_sha256` from
`f317cd66c2fd99f402e5bcb67a03f23f90b0293557a2ce61a5c5e297e6f1cfd0` to
`3dc72ef2f9bcd0a8420a029ac4af6e27d6fd73417febba17860f91d9d91be8ca`. Existing partition/case/lane selections and source
hashes are otherwise preserved. This append-only record is the third changed
path. No failed gate, test, relation, grant, migration or coverage is disabled.

The API-contract lane also failed while checking the migrated physical data
dictionary for Package A's new Telemetry-owned policy relation. It is a
separate outstanding hosted-schema/dictionary acceptance obligation. The
previously registered follow-up
`docs(telemetry): refresh fluid unit policy dictionary` must use an actual
PostgreSQL catalog capture; this repair does not invent a dictionary, claim
a passing API-contract lane or select Package B/C. No Java/Maven/PostgreSQL
local execution is claimed. This source-only correction remains subject to
new exact-SHA Fast/Full/Docs CI feedback before HAPI-595 Package A acceptance.


### HAPI-595 Package A — Hosted physical dictionary correction, 2026-10-27

Owner Fail reviews exact implementation head
`4bd0da89e77b27597b4399fcc1198382e5f84b10` (tree
`8a17e32c966107911a7c9cd5036ca63dd8c41dd0`).
Fast Feedback #224/run 40501151777 and Documentation #624/run
40501151763 are completed/success. Full #918/run 40501151799 fails
only its mandatory API contract/database dictionary lane; all seven PostgreSQL
shards, Java unit/architecture and static validation succeeded. The generator
exited with `Physical dictionary drift; review regenerated metadata/content`;
Full Verification Gate failure is consequent to the missing successful lane.

Recovered actual hosted PostgreSQL16 migrated catalog evidence from the
exact-head Full #918 API-contract diagnostic artifact `12732956715`
(`hidra-ci-diagnostics-4bd0da89e77b27597b4399fcc1198382e5f84b10-40501151799-1-api-contract`).
Its `database/schema.json` is 6,379,228 bytes, SHA256
`e7825c993ba1ba774ac17785ad98073cc4a65ec33014514800089ccbfc5997e7`,
with exact captured `source_sha` of 4bd0da89, 177 applied source migration
identities, 470 JPA mappings, 520 physical relations and actual PostgreSQL
server 16.15 (Debian 16.15-1.pgdg13+2). Reviewed source-bundle SHA256 is
`0ed456292230e222e4cff9ae6c61e8e5a996a573d039c60345f10f5590c35fa7`.
The observed prior reviewed dictionary had 175 migrations/519 relations.

Within the previously admitted artifact-only two-file allowlist, update
`doc/database/DATA_DICTIONARY.md` from the actual catalog: add the one
Telemetry-owned `hidra_telemetry_fluid_unit_policy` relation with six physical
columns, five constraints, its primary index and source-linked owner evidence;
record exact new migration mentions for that table, `hidra_telemetry_unit`
and `hidra_identity_permission`; and refresh source-bundle/generation counts.
Retain the generator's format, unchanged prior physical data, ownership, SQL
facts, migration history and no-unresolved-owner claims. The physical catalog
was captured by CI, not fabricated from source-only schema inference. Its
existing `verification.json` reports the genuine previous drift failure.

The refreshed dictionary text is reconstructed deterministically from the
actual captured catalog and repository's existing generator rendering rules;
the complete current CI generator/equality/ownership check has not yet passed
on this corrected head and remains the explicit acceptance gate. No migration,
JPA, Java test, workflow, grant or validator is changed; no attempt is made to
skip dictionary equality or other Full checks. Exact registered message:
`docs(telemetry): refresh fluid unit policy dictionary`.
Publish with expected 4bd0da89 and observe fresh exact-SHA applicable CI
creation/start, then STOP. HAPI-595 Package A remains implementation-complete
in this bounded scope but not Full-accepted; B/C and HAPI-596 remain unselected.


### HAPI-595 Package A — CI #919 physical dictionary sort repair, 2026-10-27

Owner Fail follows exact main `c4fb680fa9991351970c913fa1747b4f92cf2bed`
(tree `f74bc2a89d3d452107d95db7395c8da8121fb31e`). Full CI
#919/run 40508077295 completed failure, with the API contract/dictionary lane
failing the unchanged `Physical dictionary drift; review regenerated
metadata/content` equality check; static, Java unit/architecture, and all
seven PostgreSQL shards passed. The dependent Full Verification Gate failed.

The #919 API diagnostic artifact 12736421054 contains the real freshly
migrated PostgreSQL16 schema.json (177 migrations, 470 JPA mappings and
520 physical relations). This was checked against the completed/success
Full CI #916 reviewed dictionary artifact 12725837593 and the same
generator rendering/ownership/column/constraint/ordering rules. There were
no changed pre-existing relation facts or JPA mappings: the only added
catalog relation is `public.hidra_telemetry_fluid_unit_policy`.

The preceding dictionary refresh accurately reproduced the new physical
relation but inserted its complete rendered section after
`public.hidra_telemetry_ingestion_batch`. The generator sorts by
(schema,name), so the policy section must immediately precede that
ingestion section. Moving the whole section to the canonical position,
preserving all 520 relations and existing catalog fields, was compared
line-for-line to a complete local reconstruction from the #919 captured
schema and #916 passing dictionary. The reconstructed text matched
exactly; refresh the single captured-source line to #919's source SHA.
This is a source-artifact reproduction, not a claim that a new hosted
Full CI or PostgreSQL check has already passed.

Only `doc/database/DATA_DICTIONARY.md` and this append-only owning
phase record are modified under the previously registered dictionary
artifact follow-up. No SQL migration, ownership grant, Java source,
test, generator, CI lane, failed check or numerical calibration changes.
Preserve the registered exact message
`docs(telemetry): refresh fluid unit policy dictionary`.
After guarded direct main publication observe applicable documentation CI
startup and STOP; Full/Fast are path-filtered for docs-only changes and
the exact-SHA manual Full run remains an acceptance gate. HAPI-595
Package B/C and HAPI-596 are not selected automatically.

### HAPI-595 Package A — Full #920 fixture contention repair, 2026-10-27

Owner Fail follows exact main `c6eb3786771412c05ed6ba3b73e34b634303d7a7`
(tree `b4cba5c12ff9cc11f5d7525bdc5c22e3abf520c0`). Full #920/run
40512412096 attempt 2 completed failure. API contract/dictionary, static,
unit/architecture and six PostgreSQL shards passed; PostgreSQL shard 2
and the dependent aggregate gate failed. The failing case
`ProductEngineeringDraftPostgresIntegrationTest.ownerFirstFenceBlocksConcurrentPolicyRevocationUntilCommit`
never reached its body: `initialize:241` failed acquiring the unchanged
Identity SHARE ROW EXCLUSIVE NOWAIT fence, SQLSTATE 55P03, on
`public.hidra_identity_provider`. The lane reported 258 tests, zero
assertion failures and one setup error.

The setup is serial and precedes the case's explicit competing writer.
Background autovacuum's SHARE UPDATE EXCLUSIVE relation lock is a plausible
incidental contender after repeated fixture deletes; the retained stack
does not identify the lock holder, so this is an inference, not a measured
blocker identity. Reuse the existing EngineeringNetworkDraft PostgreSQL
fixture isolation precedent: disable autovacuum only in this disposable
test container and assert SHOW autovacuum is off before setup admission.
Preserve all 19 cases, explicit writer-first/owner-first contention checks,
NOWAIT production fences and production database configuration.

This responsible CI repair registers exactly three MODIFY paths:
`src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/ProductEngineeringDraftPostgresIntegrationTest.java`,
`.github/ci-test-inventory.json` (only that class's actual source hash),
and this append-only owning phase. CREATE/DELETE: none. Exact message:
`test(custody): isolate product fixture from background vacuum`.
No production source, SQL migration, dictionary, permission, budget,
retry, lane, case membership or accepted outcome changes.

Local Java parsing, source/hash/inventory preservation and documentation
self-tests are useful checks only; native PostgreSQL execution remains
hosted CI feedback. Publish with expected c6eb3786 and verify applicable
exact-SHA workflows created/queued/started, then STOP. HAPI-595 remains
NOT_IMPLEMENTED / IN_PROGRESS overall; Package A acceptance and B/C remain
pending, and HAPI-596 is not selected.

### HAPI-595 Package A — Full #921 fluid fixture contention repair, 2026-10-27

Owner Fail follows exact main `2977021c4810d1414a5205fbd1b44c070234de42`
(tree `d9d12cc04eac691b561091ad96584ecec7c78e47`). Full #921/run
40516432516 attempt 1 completed failure. Exact-SHA Fast #225 and Docs #627
completed success. Full's API contract/dictionary, static and Java
unit/architecture lanes passed. PostgreSQL shard 2 reported 258 tests,
zero assertion failures and one error; the aggregate correctly rejected
the failed mandatory PostgreSQL dependency.

The sole reported test error is now
`ProtectedFluidPropertyAuthorizationPostgresIntegrationTest.fourNativeProducersRequireBothExactScopesForTwoActions`
at line 716, through run lines 265/274. The unchanged native Identity
SHARE ROW EXCLUSIVE NOWAIT fence failed on
`public.hidra_identity_provider`, SQLSTATE 55P03, before contribution.
The product fixture repaired in the parent has no reported test failure.
The new fluid fixture still starts its own separate PostgreSQL container
with autovacuum enabled and repeatedly deletes/recreates Identity rows.

Apply the same existing disposable-fixture isolation precedent to this
responsible Package A test: start this container with autovacuum=off and
assert SHOW autovacuum is off before initialization. Background vacuum
contention remains an inference: the retained exception does not name
the lock holder. Do not claim it is conclusively established or that this
repair has passed hosted native tests before feedback. Preserve all 18
case names, actual four-producer paths, expected permissions/decisions,
explicit competing-writer tests and fail-closed production NOWAIT guards.

This repair registers exactly three MODIFY paths:
`src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProtectedFluidPropertyAuthorizationPostgresIntegrationTest.java`,
`.github/ci-test-inventory.json` (only that test's source hash), and this
append-only owning phase. CREATE/DELETE: none. Exact commit message:
`test(identity): isolate fluid fixture from background vacuum`.
No production code/configuration, SQL migration, permission, dictionary,
timeout/budget, retry, lane or case membership changes are admitted.

Run available Java syntax, inventory preservation and documentation
checks; native Java21/PostgreSQL verification remains hosted CI feedback.
Publish directly with expected 2977021c, verify exact candidate
parent/tree/paths/blobs and applicable workflow creation, then STOP.
HAPI-595 remains NOT_IMPLEMENTED / IN_PROGRESS overall. Package A
acceptance is pending; B/C and HAPI-596 are not selected.

### HAPI-595 Package B — supplied revision storage implemented; hosted acceptance pending — 2026-10-27

**Selection and accepted prerequisite.** Owner Next selected Package B, followed by
Continue of its in-progress work. Live source parent7b696df2f4c0cfdec3df341bff845c069d08acc2,
tree79562b2425be8fbc5c2d1d0aebc02f8d7bfa2c87; Full922/run40518372201,
Fast226/run40518372155 and Docs628/run40518372230 completed/success.
Retained Full922 gate records3363 executed tests, zero failures/errors/skips.
The two Package A native suites report18 authorization and11 unit cases with
zero failures/errors/skips. Its same-source API/catalog lane passed, including
the actual migrated dictionary equality/ownership gate. This accepts the A
prerequisite for B; it does not close the HAPI-595 parent.

**Bounded implementation.** Exact registered message:
`feat(custody): add versioned supplied fluid property drafts`.
Exactly ten CREATE and eight MODIFY paths from Package B above; zero DELETE.
The pure supplied-fact model validates explicit GAS/LIQUID, MOLE/MASS fraction
bases, positive exact decimal strings, exact composition sum, method coverage,
absolute conditions and incomplete diagnostics. The versioned strict UTF-8
codec preserves scale, child ordering and null-versus-empty declarations.
No property computation, conversion, fraction normalization or qualification.

The application/coordinator joins native property authority, current Party,
Custody metadata/catalog and Telemetry unit admissions in one transaction with
34 distinct relation fences. The separate immutable root/history binds authored
metadata version/classification, request identity, exact bytes/digest and actual
server transaction origin. Fresh exact replay returns current facts; stale
metadata is diagnosed without reinterpreting the original declaration. New writes
require current metadata and property CAS. Only the two exact compiled callers
and two reviewed DML SHA256 templates are added to the executor. Existing
credential routes, family hashes, private source pins and500/750/250ms work/
statement/lock budgets remain. One scalar final database guard seals the result;
there is no post-guard application query or automatic retry after unknown commit.

New migration V20261027_003 is additive; all existing migrations are preserved.
Its18 version constraints, immutable triggers and trigger-source pins were
reviewed together. Two explicit Custody ownership entries are added. Actual
physical dictionary regeneration is PENDING: this workspace has no PostgreSQL
catalog runtime. Preserve the existing published dictionary and obtain the new
complete catalog from hosted CI before the registered artifact-only refresh.
An initial dictionary equality failure is an outstanding capture obligation,
not permission to weaken the validator or invent physical metadata.

**Tests and actual local checks.** Twelve domain/codec and24 native storage
cases are authored; three additional guard/classifier cases are registered.
Native cases cover four producers, exact supplied bytes/bases/classifications,
replay, CAS, stale metadata, two-company isolation, current session/company/
catalog/unit denials, opaque admissions, swallowed worker reuse, writer-first/
owner-first unit contention, forbidden history mutation, forged version/origin,
and real final-guard rollback. These native cases have NOT executed locally.
Existing inventory cases, timings, lane/shard assignments and supporting
associations are preserved; only changed source hashes/case additions and two
new unmeasured suites are registered. Architecture exceptions add only the exact
new coordinator and three public owner roots/nested data, with sibling/private/
foreign-consumer negatives; existing exceptions remain unchanged.

Available checks PASS: syntax parsing of14 changed Java sources; Java17 pure
model/codec compilation;58 standalone supplied model/codec assertions;19 existing
documentation-validator self-tests. The initial compile invocation used the
wrong working directory and failed to locate sources; rerunning from the source
root passed. No Maven, javac launcher or Docker is installed; the installed
Java17 compiler module enabled these bounded checks. Full Java21/Spring/native
execution, PostgreSQL schema/origin/races and physical dictionary equality remain
hosted exact-SHA acceptance gates. No source review or skipped test is counted
as runtime evidence.

HAPI-595 stays NOT_IMPLEMENTED/IN_PROGRESS overall. B is internal software;
Package C HTTP operations and final acceptance remain separately selected.
Publish only the registered18 paths directly to main with expected7b696df,
verify parent/tree/blobs/message and scope, confirm exact-SHA Full/Fast/Docs
creation/queue/start, then STOP. After B source feedback, its actual catalog
refresh is the next bounded follow-up before Package C acceptance.


### HAPI-595 Package B — Full923 fixture, phase-boundary and physical dictionary repair — 2026-10-27

**Owner Fail and exact evidence.** Current main/source
e91de456670a8db835218d90a2fdee92904b4785, parent7b696df,
tree9cf3b16780ae9661f3afbf085a247102e78c0286. Fast227/run40528752035
completed/success. Full923/run40528751993 and Docs629/run40528752259
completed/failure; no complete B acceptance is asserted. The unit/architecture
lane passed. Raw retained SuppliedFluidPropertyPostgresIntegrationTest XML has
24 cases, zero assertion failures/skips and three errors: the unit-revocation,
writer-first and owner-first cases all fail in units() before the intended
checks because created_at/updated_at were omitted from the owner policy insert.
The other21 cases passed; a failed suite/lane is not accepted as a passing gate.

This Fail repair registers exact message:
`fix(custody): repair supplied fluid acceptance evidence`.
MODIFY exactly four existing paths: this phase record;
`src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/SuppliedFluidPropertyPostgresIntegrationTest.java`;
`.github/ci-test-inventory.json`; and `doc/database/DATA_DICTIONARY.md`.
Zero CREATE/DELETE, no production Java, migration, workflow, validator, timing,
shard or test-case removal. This bounded failure repair includes the already
registered B physical-catalog obligation using actual source feedback; it does
not select C or use the artifact-only message for a mixed commit.

**Corrections.** Supply both required policy timestamps explicitly in the
synthetic fixture; retain all24 case names and update only its inventory source
hash. Move the accidentally separated hapi-596 anchor back immediately before
its own task heading. Preserve the complete prior B evidence and all task
metadata/status values. The unchanged full-phase record parser reproduces
HAPI-595's duplicate Phase metadata before correction and accepts all six
HPS-008 records after it; no parser exemption is introduced.

**Actual physical evidence.** Recover Full923 attempt1 API diagnostics artifact
12745581092; ZIP SHA256
434b686209698e1e2daeb64763eb037c16783794912c260116a1e208a50544ba.
Its database/schema.json source is exactly e91de456. Independently match all648
migration/JPA source-file SHA256 values before rendering with the unchanged
generator and reviewed ownership file. Captured catalog SHA256
32fa679547036ee0d8f8b368d0689a63d1267ce303944f7a487324688d266521;
source bundle93924c520ff84e21e5d50ad46a92961162eab9957416d22bdf076157d462056e.
Actual PostgreSQL16.15 disposable catalog:178 migrations,470 JPA mappings and
522 relations, zero unresolved owners. The original API/dictionary job failed
physical dictionary equality; its partial diagnostics are not a passing lane.

Regenerate the complete dictionary from that captured catalog. Relative to the
verified current dictionary blobb469fe2aaaf72bff7781480d1d0079d3d2f192de,
add exactly the two supplied-fluid relation sections. Existing sections differ
only by the new migration mention in the product root/history sections; no old
physical facts change or relation disappears. Refresh real capture provenance.
Dictionary SHA2560738eac0bc489f43dddffeef3d60ff42091281fcf0ace0318ce6ed2c406d44ad.
The unchanged generator's render/write/read equality check passes with no
unresolved owners. This satisfies local artifact regeneration, not the future
exact-SHA hosted dictionary gate.

**Actual validation and stop.** PASS: Java syntax parsing; unchanged18 dictionary
and19 documentation-validator self-tests; real full-phase parser reproduction
and correction; source/catalog hash validation and dictionary freshness;
unchanged case membership/timing/lane inventory checks and four-path scope.
Native PostgreSQL/Spring rerun remains unavailable locally under CI-first policy.
No runtime authorization or500/750/250ms budget change. Publish directly with
expectede91de456, verify exact parent/tree/blobs/message and applicable
Full/Fast/Docs creation/queue/start, then STOP. HAPI-595 remains
NOT_IMPLEMENTED/IN_PROGRESS; B acceptance and Package C remain pending.

### HAPI-595 Package C — invalid-declaration mapping scope correction — 2026-10-27

**Selection and prerequisite evidence.** Owner Next selects the registered C
continuation. Live main0539ec99ecb6bc511530dae60f67b57eb65a76b0,
parente91de456670a8db835218d90a2fdee92904b4785,
treebf478cdeb2766c6bf47f1a5921d17ffa26a919ba. Full924/run40530784333,
Fast228/run40530784473 and Docs630/run40530784268 completed/success.
Retained Full924 gate reports3402 tests, zero failures/errors/skips. Independent
raw suite review confirms all24 supplied storage and12 pure domain/codec cases
passed. The same-run API/catalog lane passed. No C runtime acceptance follows.

**Concrete missing scope.** C promises400 for unsupported/contradictory supplied
facts and503 for unavailable owner evidence. The existing Facts.validate method
rejects LIQUID COMPRESSIBILITY_FACTOR with IllegalArgumentException, invoked by
the repository only after resolving the actual current product classification.
The application translates unclassified runtime failures to UNAVAILABLE; the
current exported Reason enum has no invalid-input outcome. Thus source review
identifies a legitimate user declaration rejection that would be presented as
503. The top-level request alone cannot know the authoritative product label.
This is source analysis, not a claimed HTTP execution result. Current green B
cases do not prove the missing public400 outcome.

A broad catch of every internal IllegalArgumentException in the controller is
not admitted: stored-codec corruption or unavailable current owner facts must
remain unavailable. Client-supplied classification is not a substitute for the
actual current metadata binding. Do not change GAS-only Z policy or infer phase.

**Supporting registration only, exact current message:**
`docs(custody): register supplied fluid invalid declaration mapping`.
MODIFY only this phase record, appending this correction before the intact
hapi-596 anchor/heading. Zero CREATE/DELETE; no runtime, test, artifact, workflow,
validator or task-status change in this publication. Preserve all prior evidence.
The existing C source drafts remain unpublished; C runtime executes only on a
subsequent Next after this registration's documentation feedback.

**Extended exact future C allowlist.** Retain all C CREATE/MODIFY paths,
registered message `feat(custody): expose supplied fluid property draft operations`,
API/dictionary capture rules and every previous acceptance/preservation gate.
Additionally MODIFY exactly these three existing paths in that future C commit:

- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/SuppliedFluidPropertyRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JdbcSuppliedFluidPropertyRevisionRepositoryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/SuppliedFluidPropertyPostgresIntegrationTest.java`

Add a distinct typed INVALID_REQUEST failure outcome. Only the new-write
validation of supplied facts against the freshly resolved GAS/LIQUID metadata
may translate its declaration IllegalArgumentException into that outcome.
Keep stored payload decoding/integrity errors, unit/owner availability failures,
unknown completion and other internal exceptions fail-closed under their current
semantics. The application already preserves typed Failure instances; no service,
Identity/Party/Telemetry contract, domain or migration change is authorized here.
Map only the typed INVALID_REQUEST to400 with the existing stable public invalid
request problem. Preserve DENIED403, CONFLICT409 and UNAVAILABLE/INDETERMINATE503.

Add real native storage and configured HTTP cases for LIQUID Z: valid scoped
authority and current product/metadata binding,400 INVALID_REQUEST, no repository
apply/provisional row/history append. Also prove supported GAS Z and supplied
GAS/LIQUID density/viscosity retain their admitted units, conditions, methods and
exact decimal lexemes, and genuine owner-unit failures still return503. Retain
all24 existing storage cases. Register the new native case/hash through C's
existing inventory path without changing existing timings, lane or shard policy.
The supporting path addition changes no status, product policy, solver scope or
transaction budget and does not select596/597.

**Actual checks and stop.** Exact current three paths exist; all five C CREATE
paths are absent in the current remote tree. Available19 documentation-validator
self-tests and the full HPS-008 metadata/anchor parser pass. New evidence is
append-only and all six phase task statuses are unchanged. This stage has no
runtime implementation, executed HTTP test or new physical/API capture.
Publish this one-document correction directly with expected0539ec99, verify
exact parent/tree/blob/message and documentation CI creation/queue/start,
then STOP. Next remains the extended Package C implementation after feedback.

### HAPI-595 Package C — protected HTTP source implemented; runtime capture pending — 2026-10-27

**Selection and exact baseline.** Owner explicitly selects Package C after the
invalid-declaration mapping registration. Docs631/run40536951929 completed/success
on556bad407259544d6e82bc8bd18f23b5b8bd15dc. Main subsequently advanced to
ad360bafdd84b197f34e1284c72ebc13d45ed2ce,
tree6276f2fbd5667bd71857acba07fc1deca394ec8d; Docs632/run40544106110
completed/success. Review confirms that intervening commit changes only
TASKS_HPS-001's owner-selected Identity policy registration; preserve it intact.
Accepted runtime source remains0539ec99ecb6bc511530dae60f67b57eb65a76b0,
Full924/Fast228/Docs630 passed. Retained Full924 gate reports3402 tests and zero
failures/errors/skips; its raw supplied suites prove24 native storage and12
domain/codec cases. Source/run/attempt and actual API/catalog lane evidence were
independently recovered in this continuation; no C test is covered by those runs.

**Bounded source publication.** Exact registered message:
`feat(custody): expose supplied fluid property draft operations`.
CREATE the five C controller/request/response/standalone/native-HTTP files.
MODIFY the route interceptor, its test, inventory and this phase record, plus the
three existing paths admitted by the preceding invalid-declaration correction.
Exactly12 changed paths: five CREATE, seven MODIFY, zero DELETE. Preserve all
other files, task statuses, existing migrations and concurrent Identity policy.

Expose only GET and PUT
`/api/v1/custody/engineering-products/{draftId}/properties`, with exactly one
companyPartyId query. Strict explicit nested DTO parsing rejects unknown fields,
JSON coercion, unsupported enums/decimal lexemes, null/duplicate/oversized children,
contradictory methods and server-owned identity/actor/qualification facts.
Independent supplied version, product draft/catalog IDs, authored metadata
version/classification, exact fact strings and null-versus-empty, incomplete and
stale-binding diagnostics are mapped through explicit response DTOs. No
persistence entity or calculated property is returned. First write201; successor/
fresh current replay200; current read200. There is no history/list/publication/
export/solver endpoint. Completeness is separate from engineering qualification.

The interceptor delegates only the exact compiled supplied controller/read/GET
and write/PUT route tuples after authentication. Preserve all existing metadata
and other owner tuples, static permissions and native company/product authority.
Add only INVALID_REQUEST to the typed failure family and translate only freshly
submitted facts.validate rejection against already-resolved current metadata.
Keep metadata enum resolution and stored payload decoding outside that catch.
Controller maps the typed rejection to400; DENIED403, CONFLICT409 and owner/
storage/unknown-completion503 remain. No untyped internal-exception classifier,
extra owner export, domain, schema or budget change.

**Real tests authored; execution pending.** Add11 standalone HTTP/DTO cases,
14 configured native HTTP/PostgreSQL cases, one exact-route delegation case and
two native storage regression cases;28 added cases total. Preserve every old
case/timing/lane/shard/support association and register only changed hashes and
two new unmeasured suites. Tests cover four native bearer producers, both actions,
exact replay/CAS/stale metadata, GAS/LIQUID and both fraction bases, supplied
property/method/source/condition/unit bytes, null/incomplete data, missing/forged
bearer, metadata-only authority, native scope DENY, current session/company/
catalog/unit denials, two-company isolation, strict requests/scopes, and actual
final-database-guard rollback. The new B regression requires LIQUID Z to return
INVALID_REQUEST before apply with zero root/history rows. The second corrupts
synthetic stored codec bytes under an administrator fixture, restores them in
finally, and requires UNAVAILABLE rather than invalid user input. These native
cases have not executed locally; source review is not runtime proof.

**Actual local checks.** PASS: syntax parsing of all10 changed Java files; six
actual domain/port/DTO sources type-check on the available Java17 compiler using
temporary dependency signatures;34 focused DTO/mapping assertions using those
signatures;16 extracted exact-route classifier assertions;19 unchanged
documentation-validator self-tests; full-phase metadata/anchor parsing;
append-only evidence/scope and case/timing/lane preservation checks. Dependency
signatures are not installed framework libraries: none of these checks proves
real Jackson, Spring, HTTP filters, Java21 or PostgreSQL acceptance. Maven,
javac launcher and Docker are unavailable; apply AGENTS11.1 CI-first validation.

**Truthful runtime artifacts and stop.** Keep the existing OpenAPI snapshot and
its inseparable manifest unchanged: no actual application capture can be produced
locally. The new two-route/nested-DTO runtime capture is PENDING hosted feedback;
its equality gate may fail until the registered actual-capture follow-up. Do not
handwrite the contract, forge source provenance or weaken equality/compatibility.
No migration/JPA/ownership change occurs, so preserve the actual reviewed522-
relation physical dictionary and require the next hosted catalog equality check.
The separately selected follow-up remains
`docs(api): refresh supplied fluid runtime contract` with its registered artifact
allowlist. HAPI-595 remains NOT_IMPLEMENTED/IN_PROGRESS; final acceptance and
HAPI-596/597 are not selected. Publish with expectedad360baf, verify exact
parent/tree/blobs/message and12-path scope, observe exact-SHA Full/Fast/Docs
creation/queue/start, then STOP for source feedback and actual capture recovery.

### HAPI-595 Package C — Full925 HTTP test compile and actual API capture repair — 2026-10-27

**Owner Fail and actual source.** Live mainbf5a45368af015a416505c55095160105aa32b21,
parentad360bafdd84b197f34e1284c72ebc13d45ed2ce,
treec7319a5ede25602c676c606edc390e7c48793cd5. Docs633/run40546257278
completed/success; Fast229/run40546257283 and Full925/run40546257297 failed.
Every Java test lane fails testCompile on the same four builder expressions in
SuppliedFluidPropertyNativeHttpPostgresIntegrationTest: fixture-local get/put
methods hide the statically imported MockMvcRequestBuilders names. No new native,
HTTP or standalone case executed in Full925. Preserve this failed acceptance
state; syntax/temporary DTO checks were not whole-test Java type checking.

**Exact current repair message:**
`fix(custody): repair supplied fluid HTTP verification`.
MODIFY exactly five existing paths: the native HTTP test named above,
`.github/ci-test-inventory.json`, `doc/api/openapi.yaml`,
`.github/documentation-validation.json`, and this owning phase record.
Zero CREATE/DELETE; no production Java, migration, authorization, timing,
workflow, validator or test-oracle changes. The owner Fail selects this bounded
source repair and already registered actual-runtime capture obligation together;
it does not use the artifact-only message for a mixed commit or select596/597.

Fully qualify exactly one PUT and three GET request-builder calls. Preserve all14
native HTTP case bodies/expectations apart from those four name resolutions and
all fixture-local use-case helpers. Update only this class's inventory source
hash; every case, measured timing, lane/shard and other inventory field remains.

**Actual generation, failed lane and preserved provenance.** Full925 attempt1
API job122337578666 successfully completed application-build step8 and
"Publish deterministic OpenAPI contract" step9. Equality step10 failed;
compatibility, dictionary and lane-sealing steps were skipped. API job and Full
workflow therefore FAILED. Diagnostics artifact12753311772 contains the actual
openapi/hidra-api.json and application log, not complete passing-lane evidence.
ZIP SHA256e0987a17ed8f085b635e00b443aea9be2ea8a5cc15f5d672311baf06894ec233;
raw JSON SHA25683df8e59152b56405febf6ac77c17d379c90d37a8874f4bc362c25dfcf3546e0.
Verify actual generated source stampbf5a4536 and adopt only that unchanged
runtime capture, normalized by the unchanged snapshot validator's canonical
JSON serializer. Never rewrite its source stamp to the later repair SHA.

Canonical snapshot SHA256
b4abd391eae241fc3ca7609ea742ae7c72dcfe0a74d25c5951cf68b281606f65;
272 paths,296 operations,307 schemas. All271 prior path items/294 operations,
288 schemas and security definitions are structurally unchanged; add exactly the
supplied properties path, GET/PUT operations and19 explicit DTO schemas. Keep
old provenance nested intact and scope the new provenance's success solely to
the actually successful build/generation steps, retaining failed job/workflow
conclusions and all outstanding verification gates. Only the OpenAPI section of
the manifest changes; the digest/count/source/provenance companion is inseparable.

The unchanged local compatibility comparator passes against actual successful
Full924 API artifact12747260669, ZIP SHA256
6af1a50ac6e3b73528b6867588c2d4c3126f3b5b8e947fb5ff52d629f8702582,
source0539ec99, runtime JSON SHA256
91f6f3c3d4c91e619c5580e04554fbe23193a2dc6125298a718799a9c11927a7.
Its retained source/run/attempt and JSON digest match its API evidence. This is
local comparison of real captures, not a hosted Full925 compatibility pass.
No new physical dictionary is adopted:925 never executed that gate; require the
repair's actual same-run dictionary capture/equality. Previous dictionary and
migration/JPA source remain intact.

**Actual local checks and remaining acceptance.** PASS: Java syntax parsing;
all19 unchanged documentation and10 unchanged API-validator self-tests;
actual capture validity and canonical snapshot/runtime semantic equality;
supported compatibility against the actual924 capture; exactly four qualified
calls/all14 case names unchanged; one inventory hash update, preserved manifest
provenance/other fields; full-phase metadata and append-only scope checks.
Maven/Java21 test compilation and native execution remain hosted feedback.
No test lane is counted as passing from a skipped/missing Surefire report.
HAPI-595 remains NOT_IMPLEMENTED/IN_PROGRESS overall. Publish only these five
paths with expectedbf5a4536, verify exact parent/tree/blobs/message, observe
Full/Fast/Docs creation/queue/start on the repair SHA, then STOP. Package C
execution, exact-SHA API/dictionary acceptance and final595 closure remain gates.

### HAPI-595 Package C — Full926 DTO/domain boundary repair — 2026-10-27

**Owner Fail and verified source evidence.** Current main
bfdeda295f939a34801ac3288fb2835a8b88c248,
parentbf5a45368af015a416505c55095160105aa32b21,
tree44baf7099e923fb390916781cc45e6b9c6ea5a8f. Docs634/run40555699333
completed/success. Fast230/run40555699335 and Full926/run40555699448 failed.
All seven PostgreSQL shards, static validation and API/catalog lane passed.
Unit/architecture lane has2102 executed tests, one failure and zero errors/skips:
ForensicRemediationClosureTest.directWireDomainRepresentationLeakageRemainsAbsent.
The unchanged HRA-102 rule flags the two supplied-fluid request/response classes'
non-enum domain imports used by their conversion helper methods. This is an
actual boundary violation; do not change the rule, expected blockers or imports
to conceal an unchanged dependency.

Independently recover attempt1 native shard2 artifact12757532451,
ZIP SHA256cc4e00d6556bbcd28c689349a1d54e0844c8e5c4d3f4c58e4d290e589bf12073,
and unit diagnostics12756947426,
ZIP SHA256d3a0c5df843b8495bf5a17784b1f9c0863e69abb56390ee8ef49abb3b21e9293.
Raw reports prove all14 supplied native HTTP,26 storage,11 standalone controller
and12 domain/codec cases passed, zero failures/errors/skips in those four suites.
Match every raw case name to the current inventory and its source-file SHA256;
these successful subsets do not make the failed Full926 gate acceptable.

**Exact bounded repair message:**
`fix(custody): separate supplied fluid DTOs from domain conversions`.
MODIFY exactly the existing supplied-fluid controller, request DTO class,
response DTO class and this phase record: four existing paths, zero CREATE/DELETE.
All are registered C paths. No test/inventory/architecture-rule, domain/port,
owner/security, SQL/migration, workflow, artifact or budget change is selected.

Move the actual inbound domain construction/validation and outbound explicit
response mapping out of the request/response classes into private typed methods
on SuppliedFluidPropertyRevisionController. The DTO classes now contain wire
records, structural JSON parsing and wire-only bounds, without domain imports,
references or conversion methods. Domain invariants remain in their actual model;
the controller invokes them on mapped declarations before calling the use case.
Unsupported/contradictory values still receive400 before an application write;
the separate current-classification INVALID_REQUEST path and503 owner/integrity
semantics remain unchanged. Preserve exact decimal strings, null/empty values,
methods/evidence, independent versions and all response diagnostics.

No fully-qualified-name escape inside DTOs, replacement domain aggregate,
reflection dispatch, new mapper type, relaxed guardrail or broad cross-module
exception. Controller mapping uses typed domain/wire parameters, following the
existing accepted product REST-adapter convention. All wire record components
and every schema annotation are byte-identical before/after; route methods,
request field parsing, response wire shapes and security tuples are preserved.
The conversion executes once before use-case submission instead of DTO-side
construction followed by controller-side reconstruction. No validation invariant
is removed or moved into an untrusted caller.

**Actual artifact position.** Full926 API/catalog artifact12757561602,
ZIP SHA25674600c147c0cdcdd97e726d1ed3cce98d29b627a6081d75dc9df9b1e0460c42e,
retains sourcebfdeda295, run40555699448/attempt1 and actual runtime JSON SHA256
80725179fa72b06fbc4514cba3f6c59157a3dd74ab9f0ae7d46b90cb07edace3.
Its actual capture semantically equals the reviewed canonical API. The same job
successfully executed compatibility and physical dictionary capture/equality;
source bundle93924c520ff84e21e5d50ad46a92961162eab9957416d22bdf076157d462056e,
catalog SHA2566ef460d7c22af1895319f0383439cc48f85346c1ad390d13a44b4d2ffdb6b93a.
Preserve the truthful current canonical snapshots and provenance; do not change
their generated source stamp merely because Java conversion code moved. Require
repair-SHA runtime equality/compatibility and actual catalog proof anew in CI.

**Actual local checks and stop.** PASS: syntax parsing of the changed source
within the10-file C parser; six actual domain/port/DTO sources type-check using
temporary dependency signatures; extracted actual private controller conversion
code type-checks and34 focused parser/mapping assertions pass with the same
signatures. These are not actual Jackson/Spring/native execution. Source checks
confirm neither DTO references domain types and all record components/schema
annotations are identical. All19 unchanged documentation self-tests and exact
full-phase metadata/anchor, append-only evidence and four-path scope checks pass.
No new local Maven/Java21/PostgreSQL execution is claimed; all repository tests
and inventory/source hashes are unchanged by this repair.

HAPI-595 remains NOT_IMPLEMENTED/IN_PROGRESS. Publish only the four paths with
expectedbfdeda29, verify exact parent/tree/blobs/message, confirm applicable
Full/Fast/Docs creation/queue/start on the new SHA and STOP. Full acceptance,
Package C acceptance and separate final595 closure remain pending.

### HAPI-595 final authoring acceptance — VERIFIED / COMPLETED — 2026-10-27

**Selection, exact source and green gates.** Owner Next selects the separately
registered final acceptance after Package C's responsible boundary repair.
Accepted source/main3de14d1dbdd7aad6f328bbe627b729a783c65042,
parentbfdeda295f939a34801ac3288fb2835a8b88c248,
tree59630c0f25d3e5a5ee19fb9fad3e50072702af7c.
Full927/run40558759235, Fast231/run40558759385 and
Docs635/run40558759466 completed/success on that exact source. Retained Full927
attempt1 gate proves3430 executed tests across478 test classes, zero failures/errors/
skips. This is development software acceptance, not a deployment, release,
industrial calibration, engineering qualification or SITE acceptance.

**Current exact final message:**
`docs(custody): accept supplied fluid property authoring`.
MODIFY exactly this phase, ULTIMATE_TASKS and only595's status cells in
ULTIMATE_ROADMAP/ULTIMATE_PHASES. Zero CREATE/DELETE or runtime/artifact change.
Set595 to VERIFIED/COMPLETED in its header and both registries; replace only its
compact execution pointer. All other task statuses, original definitions,
historical evidence, legacy mappings, source, tests, migrations and snapshots
remain unchanged. This closure does not implicitly select596/597 or the593 parent.

**Independent evidence recovery.** Download Full927 attempt1 artifacts and verify
ZIP SHA256 before reading raw evidence:

| Artifact | ID | ZIP SHA256 |
|---|---|---|
| Full gate and retained evidence |12759176629|8b1cbf465ba20a0a150153260cd1911b08040aae482bbaa1347acc226a07afbb|
| PostgreSQL shard2 |12760685583|8fd4202697b80d442c1ce39ac730fab663a6eed537018ab8d934f605a64e1e5d|
| Unit/architecture |12758276854|2b56e66ca43d5acccd048532315db6194f57a0a01d8bd500a728f31a9af31540|
| API/catalog lane |12758311831|7d6a75e52f457d48140fc0da5e2636b67e1fc0790ad16cfdfd31ca17c3e8dbbe|

Independently match source SHA, run/attempt, exact inventory digest, every retained
lane file digest, raw suite/case identities, selected lane membership and current
source-file hashes. A stale local Telemetry test review copy was refreshed from
the exact accepted remote source before its hash/case comparison; no repository
source change is made. Aggregate gate success is corroborated by raw evidence,
not substituted for it. Original failed runs remain failed historical records.

**Task-specific acceptance matrix:99 executed cases.** The six dedicated suites
below contribute92 exact cases; every case appears in the raw report, unchanged
source-linked inventory and same-run lane evidence, without failure/error/skip.

| Actual source suite | Cases | Accepted software boundary |
|---|---:|---|
| ProtectedFluidPropertyAuthorizationPostgresIntegrationTest |18|Distinct native property family, four producers, both scopes/actions, currentness, denial and opaque-origin misuse.|
| CustodyProtectedFluidUnitPostgresIntegrationTest |11|Actual owner roles/dimensions/activity, opaque admissions, locks, races, privilege/catalog safety and rollback; no unit conversion.|
| SuppliedFluidPropertyRevisionTest |12|Exact decimals/fractions, both bases/labels, bounded methods/conditions/evidence, incomplete-vs-invalid, strict canonical codec and request bytes.|
| SuppliedFluidPropertyPostgresIntegrationTest |26|Immutable independent history, metadata pin/staleness, exact current replay/CAS, concurrency/company isolation, current denials, unit fences, forged origin, real final-guard rollback, typed invalid input versus stored corruption.|
| SuppliedFluidPropertyRevisionControllerTest |11|Strict actual JSON/controller parsing, exact scopes/identities/status/problems, nested rejection, lexeme/null/empty/diagnostic preservation and non-disclosure.|
| SuppliedFluidPropertyNativeHttpPostgresIntegrationTest |14|Configured native filters/HTTP/database for four producers, both actions, supported facts/methods/units, replay/CAS/stale bindings, denied/revoked/foreign targets,400-vs503 and real rollback.|

Additionally verify these seven introduced guard cases in the passing same-run
unit lane, matching their current source hashes and complete raw case sets:

- OperationAuthenticationBoundaryGuardrailTest.suppliedFluidSeamsHaveOnlyTheirExactPrivateOwnerCallers
- ProtectedOperationIdentityParticipantTest.suppliedFluidPrivateFamilyRejectsNullOrForeignStructuralCredentials
- JdbcProtectedAuthorizationEvidenceReaderTest.suppliedFluidFamilyCannotBeSelectedByDiagnosticEvidenceOrForgedPair
- ArchitectureGuardrailTest.suppliedFluidExportsStayExactToTheirCoordinator
- ForensicRemediationClosureTest.suppliedFluidExportsStayExactToTheirCoordinator
- ProtectedTransactionExecutorTest.fluidProvisionalStatementsAreExactReviewedTemplates
- HidraRouteAuthorizationInterceptorTest.suppliedFluidDelegationRequiresExactAuthenticatedOwnerTuples

The complete unchanged Forensic wire/domain and architecture rules pass after
moving conversions to the REST controller. No test was removed, disabled,
reclassified or weakened to accept this task. Existing families/source pins,
permission/current-scope checks,34 reviewed fences, exact DML templates,
credential routing and500/750/250ms work/statement/lock budgets remain.

**Actual same-run contract/catalog evidence.** API lane source is exactly
3de14d1d, run40558759235/attempt1. Actual runtime JSON SHA256
4825ab74f6b04f452951e340a7a8da90bd9f5b079fced24a2a557d43b99c52ef;
272 paths,296 operations,307 schemas. Runtime/canonical semantic equality,
actual successful-base compatibility and complete migrated dictionary checks
passed in that same job. Canonical generated source provenance remains the
truthful original capture, not restamped to an acceptance/repair commit.

Actual catalog SHA256
b75493db3e4d63664cfc4391326515b0c4d1181e3308c6b107da6a3944bdbef4;
source bundle93924c520ff84e21e5d50ad46a92961162eab9957416d22bdf076157d462056e.
Independently match all648 captured migration/JPA source hashes to accepted
source, validate catalog shape, render using the unchanged generator/ownership
rules and compare the physical dictionary:522 relations, zero unresolved owners.
No invented physical metadata, skipped catalog proof or handwritten OpenAPI.
Prior artifact-only refresh obligations are satisfied by the recorded actual
captures and this exact-source same-run equality evidence.

**Accepted behavior and claim limits.** Policy A GAS/LIQUID authoring supports
explicit MOLE/MASS fractions and supplied density/dynamic-viscosity, plus supplied
GAS compressibility only, with explicit owner-admitted units/absolute reference
conditions/method/source evidence. Fresh independent native authority and current
owner facts protect reads and writes. Supplied versions are immutable and
independent of product metadata versions; exact replay returns current facts,
and stale authored metadata remains explicit without reinterpretation. Exact
lexemes, synthetic origin and absent/incomplete evidence remain distinguishable.
Public GET/PUT operations return typed explicit DTOs and the registered status
contract. This is supplied-data authoring: no property calculation, EOS, unit/
fraction conversion, solver invocation, publication, downstream qualification,
engineering approval, live SCADA access or autonomous operational action exists
by acceptance of595. Existing qualified GAS contracts are preserved.

**Closure validation and stopping rule.** Available19 unchanged documentation
self-tests, exact six-record phase metadata/status agreement, status-only frozen
row diffs, compact-pointer bounds, append-only history and four-document scope
checks must pass before publication. No redundant Java/database run is required
for this documentation-only child of the exact accepted runtime source. Publish
with expected3de14d1d, verify exact parent/tree/blobs/message and confirm the
closure's applicable Documentation CI creation/queue/start, then STOP.
HAPI-596 publication/history/owner-export policy and scope preflight is the next
separate candidate; its blocked status is unchanged and no runtime stage or
owner policy is silently selected. HAPI-597 and593 integrated acceptance,
frontend work, numerical engines, releases and deployment remain separate.

<a id="hapi-596"></a>
## HAPI-596 — Product publication, history and owner exports

Phase: HPS-008 — Products and Fluid Properties

Legacy IDs: HPR-P25-053C

Priority: P1

Implementation Status: NOT_IMPLEMENTED

Planning Status: IN_PROGRESS

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

### HAPI-596 publication/history source review and policy preflight — 2026-10-27

**Selected action and accepted prerequisite.** Owner Next selects the bounded
publication/history preflight after HAPI-595 closure. Current main
d212e650d50b568e94cb91a2eca2e21f65a7f580,
parent3de14d1dbdd7aad6f328bbe627b729a783c65042,
tree54d19bf9d29971ce16e966834d8ff98a4c027022. Docs636/run40573488344
completed/success on that closure. Accepted runtime remains3de14d1d with
Full927/Fast231/Docs635 success and the99-case HAPI-595 acceptance above.
594/595 are VERIFIED/COMPLETED; their authoring contracts are prerequisites,
not a595 publication capability or automatic596 selection of runtime code.

**This exact message and scope:**
`docs(custody): define product publication policy preflight`.
MODIFY only this owning phase and596's compact current pointer in ULTIMATE_TASKS.
Append this record before the intact597 anchor; preserve every prior task/header/
status/evidence byte. Zero CREATE/DELETE or source/test/migration/API/dictionary/
workflow change. HAPI-596 stays NOT_IMPLEMENTED/BLOCKED until policy selection
and exact source-backed implementation registration. Do not alter the frozen
ROADMAP/PHASES status cells or claim a publication endpoint already exists.

#### Recovered source and concrete owner boundaries

- Accepted594 owns company-local product UUID/code/catalog binding and immutable
  metadata versions. Accepted595 owns a separate supplied version sequence,
  exact canonical decimal/Unicode bytes, units/conditions/method/source evidence
  and metadata-binding diagnostics. Both permit incomplete authoring. New
  publication must pin their exact existing bytes and version/digest pairs,
  rather than reconstructing the source through a lossy DTO or creating a
  second mutable product owner.
- PlanningProductReferenceContract exports only shared catalog id/code/activity;
  its current adapter uses the Planning product-policy row. It does not export
  a company-owned supplied publication, nor authorize publication/history.
  SimulationProductCandidateContract exports raw shared-catalog facts explicitly
  without Simulation classification/approval. Preserve both old contracts and
  adapters; publication cannot silently replace their reference meanings.
- SimulationGasFluidRevisionContract is the existing GAS/GAS_MOLE_FRACTION,
  explicitly qualified stored-revision contract. CustodyGasFluidQualificationService
  uses CustodyGasFluidApprovalEvidencePort and WorkflowGasFluidApprovalEvidenceAdapter
  for actual exact final Workflow approval. A new supplied source publication
  cannot masquerade as that qualification or broaden its representation to MASS
  fractions/LIQUID without separate qualified-consumer scope.
- The actual Workflow RevisionApprovalEvidenceContract carries target module/
  digest, exact definition version/target type/purpose, instance/task/action,
  evaluation time and server actor/time. Its ordinary Optional resolve method
  does not export issuer/thread/connection-bound admission, retained owner
  relation fences or a common final guard. Reusing its record shape alone is
  not atomic protected multi-owner approval proof. A future implementation must
  register and test an actual protected Workflow owner seam or an independently
  justified equivalent before relying on approval in publication transactions.
  The unrelated transfer-ticket Boolean reference contract is insufficient.
- Identity's accepted metadata CREATE/READ/UPDATE and properties READ/WRITE
  families are exact, exclusive grants. Neither is permission to publish,
  withdraw, reactivate, disclose publication history or issue consumer exports.
  Publication needs a separately reviewed native family and owner contracts;
  no string aliasing or existing-grant escalation. The existing Party and
  Telemetry protected exports provide concrete current owner facts to preserve.
- Topology EngineeringRevisionPublication supplies a reviewed precedent for an
  immutable original-byte snapshot, monotone CAS head, append-only PUBLISH/
  WITHDRAW/REACTIVATE events, actor-bound request receipts, bounded history and
  explicit unqualified diagnostics. Its actual domain carries engineeringQualified=false.
  This is a design precedent, not a Custody capability or permission to import
  Topology domain types. Custody must retain its own publication aggregate and
  independent metadata/supplied versions.

#### Proposed common publication policy — not yet adopted

1. **Owned immutable envelope.** Pin one company-local product draft, its
   immutable shared-catalog reference, one explicit metadata version and one
   supplied-property version with exact original formats/bytes/digests. A new
   publish requires both selected versions current and their metadata binding
   consistent, plus fresh company/catalog/unit/native eligibility. At least one
   supplied revision must exist, even when its facts are explicitly incomplete.
   Preserve declared GAS/LIQUID, fraction basis, units, absolute conditions,
   source/method evidence and synthetic origin. Server-derived completeness/
   diagnostics are immutable snapshot facts, never caller overrides.
2. **Approval is scoped source governance.** Require an actual server-verified
   human Workflow decision bound to the entire publication-envelope digest,
   including company/product identity and both exact version/digest pairs, plus
   the configured definition version/target type/purpose. A client approval ID,
   actor, permission Boolean or old gas qualification is not sufficient. Retain
   approval provenance in the immutable publication. The approval means reviewed
   source publication, not engineering/numerical suitability; enforce any
   separation-of-duties rules from the actual configured Workflow definition,
   without inventing an automatic self-approval. Incomplete-source approval, if
   permitted below, must explicitly retain that incomplete authoring status.
3. **One governed head per owned product.** Publish a new immutable publication
   ID and append a committed PUBLISH event with expected head epoch and prior
   publication ID. WITHDRAW/REACTIVATE append events and advance the same monotone
   epoch; do not update/delete old snapshots, events or approvals. Withdraw has
   no silent fallback to an older publication. Reactivation rechecks fresh native/
   owner eligibility and applicable approval for the same selected snapshot.
   Original exact request replay remains actor/company/product/command bound;
   it returns its original receipt plus an explicitly separate observed current
   head, never silently re-executes or reverses a later transition.
4. **Current versus effective versus historical.** Current means the selected
   committed head/state, not implicit scientific qualification. Effective(asOf)
   selects the committed transition history at the explicit bounded database-time
   instant; a withdrawn head yields no effective publication, with no fallback.
   Initial scope proposes server-dated transitions only; future-dated scheduling
   is not inferred from asOf or included without separate owner selection.
   Later draft edits never mutate an existing publication or silently repoint
   the head. Exact/history reads preserve the authored snapshot rather than
   reinterpret old GAS facts under a new LIQUID draft classification.
5. **History access and current eligibility are independent.** Every exact,
   current, effective, receipt and paged-history read requires fresh applicable
   native publication authority and company/product scope before existence,
   payload, count or history disclosure. Historical bytes and recorded approval
   alone never grant access. Current consumer eligibility must be assessed
   separately from exact historical reproducibility; current unit/catalog
   revocation must not erase history or fabricate validity. Withdrawal must
   remain possible under authorized product/company governance when a unit or
   supplied-source eligibility fails; it must not require requalifying bad facts.
   This is not a bypass of native authorization or fresh company ownership.
6. **Consumer exports are typed, explicit and unqualified.** Propose a new
   Custody-owned scalar immutable publication export with exact IDs/versions/
   digests, authored values/origin/diagnostics, selected event/epoch and approval
   provenance. Distinguish exact historical source from currently eligible
   selection; fail closed on missing fresh owner evidence. A Boolean supplied
   by an importer is not an admission. Do not overwrite old Planning/catalog or
   qualified GAS exports. Consumer-specific representation and engineering/
   simulation qualification remains597/later scope; neither policy below
   authorizes solver execution or operational use of incomplete data.
7. **Atomicity and bounded verification.** Preserve native-family isolation,
   company scope, inward dependencies and pure Java domain ownership. Register
   exact participating relation/trigger/role/fence images, actor-bound receipts,
   expected-head CAS, additive schema constraints and exact permitted SQL from
   the actual source. Use one observed physical transaction and scalar final
   database guard with no provisional effect after a failed guard; keep existing
  500/750/250ms budgets unless separately justified/approved. No premature fence
   count, copied source pin, new migration filename or unmeasured performance
   claim is invented in this preflight. Calibration values are not required.

#### One remaining owner decision — choose A or B

**A — recommended: explicit incomplete-source publication.** Allow a structurally
valid supplied snapshot to be published with its actual complete/incomplete
status and immutable diagnostics, subject to all common native/owner/approval
requirements. Invalid/contradictory or stale-bound declarations still reject.
Publication and export stay explicitly unqualified; downstream consumers cannot
infer scientific suitability from publication or discard the diagnostics. This
matches accepted incomplete authoring and the reviewed Topology snapshot
precedent while keeping consumer qualification separate.

**B — complete-authoring publication only.** Apply the same common guarantees,
but require both existing product metadata and supplied-fact completeness
diagnostics empty before publication/reactivation. Incomplete drafts remain
editable and cannot become published source snapshots. Completeness still does
not establish thermodynamic/hydraulic qualification or authorize a solver.
Do not invent a universal required property set beyond the existing declared
completeness rules; any new family-specific required fields need explicit policy.

The choice is unresolved. A Next alone selected this source preflight, not one
of these publication policies. No new workflow grants, deployment measurements
or real production data are requested to make the policy/software concrete.

#### Future registration and acceptance sequence — design only

After explicit policy selection and successful preflight Documentation feedback,
register exact paths/messages for bounded sequential packages: protected native/
Workflow owner admissions; pure publication model/codec and atomic Custody
storage; configured HTTP/exact/current/effective/history operations and new
scalar owner exports. Register actual DTO/mappers/tests, architecture/source
pins, private executor caller/DML additions, additive migrations, physical
ownership and runtime artifacts before use; none of the six old PROPOSED source
filenames above becomes executable merely through this preflight. Source-backed
registration must settle current/history authorization and withdrawal eligibility
without weakening existing owner contracts. Do not add frontend or consumer
qualification work to these packages.

Acceptance must prove real LOCAL/OIDC/LDAP/AD native actions and both scopes;
selected A/B completeness behavior; exact payload/approval substitution and
stale-binding rejection; independent draft/publication/head versions; scoped
actor-bound replay/conflict; current eligibility versus immutable historical
reads; authorized withdrawal without unit requalification; exact effective-time
boundaries, high-water bounded history and cursor substitution; forged/expired/
revoked approvals; current-denial/non-disclosure; concurrent publishers and
withdraw/reactivate CAS; immutable history/ordinary-DML restrictions and actual
final-guard rollback. Preserve old catalog and qualified GAS consumers/tests.
Require exact-SHA Full/Fast/Docs and actual same-run API/compatibility/physical
catalog evidence before a separate final596 acceptance. Engineering/SITE
acceptance and597/593 closure remain independent.

**Actual preflight checks and stop.** Available19 unchanged documentation
self-tests; actual six-task phase metadata/anchor parsing; preserved595 closure
and all task statuses; append-only phase history and bounded compact-pointer/
two-document scope checks must pass before publication. No Java/native run,
publication API, new schema, protected Workflow admission or scalar consumer
export is claimed. Publish only the two documents directly with expectedd212e650,
verify parent/tree/blobs/message, confirm applicable Documentation CI creation/
queue/start and STOP at the policy choice. Do not implement an unselected policy.

### HAPI-596 Policy A adopted and bounded execution registered — 2026-10-27

**Owner decision and prerequisite.** Owner A adopts explicit incomplete-source
publication: structurally valid complete OR incomplete authored snapshots may be
published, retaining their actual immutable completeness diagnostics. All seven
common policy rules in the preceding preflight are adopted. Contradictory,
invalid, stale-bound or unapproved source rejects; publication always remains
engineeringQualified=false. Policy B is not selected. This record supersedes the
preflight's pending-decision and unregistered-scope statements prospectively;
their original historical text remains intact.

Exact main62c0563e7bef07ada8149cba73c7925f8fc0e667,
tree4f359d6753adb91307db8dcebb533720c421635c,
parentd212e650d50b568e94cb91a2eca2e21f65a7f580 has Docs637/
run40574581256 completed/success. Accepted594/595 remain closed. HAPI-596
becomes NOT_IMPLEMENTED/IN_PROGRESS for registered work, not runtime acceptance.
This turn registers scope only; it executes none of Packages A/B/C below.

**This registration's exact message:**
`docs(custody): register governed product publication execution`.
MODIFY exactly this owning phase,596's compact ULTIMATE_TASKS pointer and only
596's planning-status cells in ULTIMATE_ROADMAP/ULTIMATE_PHASES. Zero CREATE/
DELETE, runtime, test, migration, workflow configuration or artifact change.
The six old PROPOSED source names gain authority only where explicitly admitted
below. Every package is a separate selected commit, never an automatic batch.

#### Source-backed design and settled policy

The reviewed Workflow RevisionApprovalEvidenceQueryService recognizes only
GAS_FLUID_REVISION/GAS_FLUID_INPUT_QUALIFICATION for custody. Its ordinary read
has no protected transaction proof. WorkflowExecutionOwnership selects exactly
one WorkflowOwnedTargetLookup by module/type. WorkflowTransitionApplicationService
creates real authenticated APPROVE actions, terminal tasks/instances and state
history. WorkflowActorQueryService currently checks activity/lock/display name,
not HUMAN user type. Therefore a new target, protected evidence export and
explicit human-review check are required; neither an old gas digest nor ordinary
actor eligibility proves this new approval. Preserve all existing semantics.

1. Introduce PREPARE as a governed, immutable source-envelope operation. It
   requires a supplied revision, both explicit current metadata/property versions,
   matching metadata binding, fresh native/company/catalog/unit evidence and an
   active configured publication Workflow policy. Store exact existing canonical
   formats/bytes/digests, original authored values, classification/origin and
   server diagnostics. Include sorted exact admitted unit facts and configured
   definition ID/version/target type/purpose in a versioned canonical envelope.
   SHA256 covers company/product/catalog identity, both version/digest pairs,
   their bytes and all envelope facts. Exclude generated publication ID, request
   ID, audit time/PID/xid and approval result from that digest to avoid circular
   approval. Those are separate immutable server evidence. No client digest,
   actor, completeness, unit snapshot, approval Boolean or qualification input.
2. PREPARE commits an immutable candidate, not a publication or head transition.
   Its digest is the exact Workflow target ID. The new custody owner lookup
   resolves only persisted, verified candidates for PRODUCT_SOURCE_PUBLICATION;
   purpose is PRODUCT_SOURCE_PUBLICATION_APPROVAL. The lookup uses the same
   publication READ authority before confirming target existence, with generic
   digest-only labels. It may suspend an ordinary Workflow transaction to enter
   a separate protected read; never suspend/reuse an existing protected admission.
   No owner/private table access by Workflow, no permissive fallback resolver.
3. Empty Workflow-owned hidra_workflow_custody_publication_policy selects one
   active exact definition/version/type/purpose per company scalar ID, with
   owner-local references and no cross-owner company FK. No caller-selected
   definition can replace this policy. No grants, live definitions, assignments
   or production policies are seeded. Synthetic tests configure actual supported
   definitions and execute real human transitions, not fabricated action rows
   as positive approval evidence. Existing configured assignment/permission/
   reason/terminal-transition rules remain mandatory. No invented separation
   rule, service actor approval, generic automatic approval or gas alias.
4. PUBLISH requires a prepared envelope, current exact source versions/binding,
   unchanged admitted envelope facts/policy and exact current final human
   APPROVE evidence for that digest. Persist publication ID, original envelope
   and exact actual approval identity/actor/display/time plus transaction audit.
   No reconstructed or lossy payload. Policy A permits missing optional facts
   with immutable diagnostics; never conceal contradictions as incompleteness.
5. PUBLISH/WITHDRAW/REACTIVATE append one monotone event and CAS one product
   head using expectedEpoch and expectedPublicationId. Initial head is epoch0
   with no selected ID. WITHDRAW must name the selected publication and removes
   effective selection without fallback. REACTIVATE selects an existing exact
   snapshot with fresh company/catalog/unit/native and currently applicable
   exact approval/policy proof; it cannot silently approve a changed envelope.
   Subsequent authoring edits do not mutate/repoint a publication. They alone
   do not erase its historical identity or constitute a head transition.
6. Native READ and current company/product ownership protect every exact,
   candidate, current, effective, receipt, history and scalar-export disclosure.
   Historical reads preserve original bytes even when units/catalog/Workflow
   configuration later become ineligible. They report historical evidence, not
   current eligibility. Current/effective consumer exports additionally require
   fresh catalog/unit and applicable approval/policy eligibility. WITHDRAW
   requires fresh native WITHDRAW/company ownership and valid storage/head,
   but not active units/catalog qualification or an available approval; otherwise
   revoked inputs could prevent withdrawing their own publication. No denial
   bypass or payload/count/existence disclosure on failed native authority.
7. Transitions are server-dated, database microsecond instants only. Effective
   asOf is explicit, no future, and selects the latest committed event at or
   before that instant; withdrawal yields none. Serialize same-product event
   time/epoch assignment and reject ambiguous/backward clock boundaries rather
   than inventing a future effective time. History is bounded1..100 with cursor
   high-water epoch, before-epoch, company/product and asOf binding; new commits
   cannot alter an existing page window. Reject malformed/substituted cursors.
8. Every mutating request has exact actor/company/product/kind/request binding
   and canonical request digest. Replays return original immutable receipt plus
   separately observed current head after fresh authorization, without new DML,
   revalidation as a new publish or reversing subsequent transitions. Changed
   body/actor/target conflicts. Unknown commit is indeterminate, no auto retry.

#### Package A — isolated native authority and protected Workflow approval

Exact message:
`feat(custody): add protected product publication owner contracts`.

CREATE exactly:
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/custody/CustodyProtectedProductPublicationAuthorizationContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedProductPublicationAuthorizationParticipant.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProtectedProductPublicationAuthorizationPostgresIntegrationTest.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/workflow/WorkflowHumanActorContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/WorkflowHumanActorQueryService.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/WorkflowHumanActorQueryServiceTest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/custody/CustodyProtectedProductPublicationApprovalContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JdbcCustodyProtectedProductPublicationApprovalAdapter.java`
- `src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/CustodyProtectedProductPublicationApprovalPostgresIntegrationTest.java`
- `src/main/resources/db/migration/V20261027_004__custody_product_publication_authority.sql`
- `src/main/resources/db/migration/V20261027_005__workflow_custody_publication_policy.sql`

MODIFY exactly:
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedOperationIdentityParticipant.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/OperationAuthenticationBoundaryGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedOperationIdentityParticipantTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReaderTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `.github/ci-test-inventory.json`
- `.github/database-dictionary-ownership.json`
- `doc/roadmap/TASKS_HPS-008.md`

The native family is PRODUCT_ENGINEERING_PUBLICATION, with distinct READ,
PREPARE, PUBLISH, WITHDRAW and REACTIVATE actions and permission codes
custody:engineering-product-publication:read, :prepare, :publish, :withdraw and
:reactivate respectively (the same full prefix for every suffix). Every action
requires independent CUSTOM custody-company:<companyId> and
custody-product:<catalogProductId> scope decisions, with current native LOCAL/
OIDC/LDAP/ACTIVE_DIRECTORY binding/session/source evidence. Migration004 adds
only those five definitions, rejects conflicting existing definitions and grants
nothing. Keep metadata/properties/Topology/organization families exclusive.
Extend the native private reader/participant only with the exact issuer-tagged
publication family. Preserve its24 existing Identity relation fences, current
DENY/graph/responsibility precedence, common sampling/deadline, one-use tickets,
holder/thread/connection/transaction identity and rejection poisoning. Include
a protected reviewer fact read for the actual Workflow actor, requiring current
active unlocked HUMAN identity under the retained Identity user fence; a caller
cannot replace the actual Workflow action actor with another human ID.

The separate ordinary WorkflowHumanActorContract resolves only scalar eligible
HUMAN identity, through Identity-owned repositories. For the new publication
target's APPROVE transition only, WorkflowTransitionApplicationService requires
that fact for its actual authenticated actor before writing action/task/history.
Keep existing gas/equipment/other transitions unchanged. Protected publication
consumption additionally rechecks the actual reviewer through the native owner;
ordinary Optional actor lookup is not an atomic admission. No Workflow query of
Identity private tables and no blanket HUMAN restriction on unrelated workflows.

Workflow's new export provides typed policy and approval requests/evidence,
opaque issuer/thread/holder/connection-bound admissions, one-use reads,
currentness and exact final fences. Policy-only preparation does not manufacture
approval. Exact approval requires active configured definition/version/binding/
catalog codes; COMPLETED instance, APPROVED task, last action exactly APPROVE,
matching instance/task/action/target digest/definition/type/purpose, actor and
terminal timestamps, and action time no later than common evaluation time.
Reject CORRECT, nonterminal approve, substituted digest/version/purpose, missing,
revoked, fabricated, stale or future evidence. Preserve actual immutable action
and history trigger/function safety, physical schema/role/lock images and final
eligibility through commit. Existing ordinary gas/equipment evidence stays intact.

The Workflow participant fences its new policy and actual participating owner
relations: hidra_workflow_definition, hidra_workflow_definition_target_binding,
hidra_workflow_type_catalog, hidra_workflow_instance, hidra_workflow_task,
hidra_workflow_action, hidra_workflow_step, hidra_workflow_transition,
hidra_workflow_step_assignment_rule and hidra_workflow_state_history. Acquire
deterministic NOWAIT relation fences before reads; derive and test the exact
union from actual SQL/trigger dependencies, never silently omit a participant.
One explicit dictionary ownership entry covers the new Workflow policy table.
Ordinary application credentials cannot mutate this policy. No new executor
business caller or provisional Custody DML is admitted in A.

Tests cover four real native producers, both scopes/all five actions, family
substitution, denial/revocation/expiry/indeterminate evidence, replay/forgery,
cross-thread/suspension/lost locks, configured real human terminal approval,
nonhuman denial before any transition effect, revoked policy and concurrent
Workflow writer-first/owner-first behavior. Actual Workflow target tests may use
a nested test-only digest lookup for isolated A fixtures; it is not a production
target or end-to-end Custody acceptance. Register exact new tests/hashes in
existing lanes and precise architecture/private source pins with negative cases.
No source-pin bypass, weakened validator, or claimed count before execution.

CI-first dictionary follow-up, only after actual migrated catalog capture:
`docs(workflow): refresh product publication policy dictionary`;
MODIFY only doc/database/DATA_DICTIONARY.md and this phase. Preserve existing
catalog equality/ownership gates and migration history. Missing local PostgreSQL
is pending evidence under AGENTS11.1, never permission to handwrite metadata.

#### Package B — immutable candidate/publication storage and atomic head

Depends on accepted A and its actual catalog disposition. Exact message:
`feat(custody): add governed product publication storage`.

CREATE exactly:
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/ProductRevisionPublication.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/ProductRevisionPublicationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/ProductRevisionPublicationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/ProductRevisionPublicationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/ProtectedProductPublicationAdmissionCoordinator.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JdbcProductRevisionPublicationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/ProductRevisionPublicationCodec.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/CustodyProductPublicationWorkflowTargetLookup.java`
- `src/test/java/dz/sh/hidra/modules/custody/domain/model/ProductRevisionPublicationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/ProductRevisionPublicationPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/CustodyProductPublicationWorkflowTargetLookupTest.java`
- `src/main/resources/db/migration/V20261027_006__custody_product_revision_publications.sql`

MODIFY exactly:
- `src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java`
- `src/test/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutorTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `.github/ci-test-inventory.json`
- `.github/database-dictionary-ownership.json`
- `doc/roadmap/TASKS_HPS-008.md`

Pure Java domain, typed ports, strict versioned codec, original byte verification
and the settled contract above are mandatory. New Custody-owned relations are
hidra_custody_product_publication_candidate, hidra_custody_product_publication,
hidra_custody_product_publication_head, hidra_custody_product_publication_event
and hidra_custody_product_publication_request. Register exactly those five
ownership entries. Candidate/publication/event/receipt bytes and audit are
append-only, with ordinary DML and update/delete/truncate prohibited. Only the
head has bounded protected CAS updates. Owner-local source-version references,
company/product consistency, unique digest/request binding, positive contiguous
epochs, state transitions and format/hash checks must reject contradictory SQL
inserts too. No cross-owner FK or changes to accepted594/595 tables/migrations.

The sole cross-owner coordinator consumes A's Identity/Workflow exports plus
existing Party/Telemetry exports. Storage includes the existing six metadata/
catalog/policy/supplied relations and five new relations, protected before
reading company/catalog bindings. Authorization precedes disclosure. Freeze
and test the actual distinct final fence union, including action-specific
participants: historical reads and WITHDRAW omit live unit/approval eligibility
as specified, not native/company/storage proof. PREPARE uses Workflow policy;
PUBLISH/REACTIVATE use actual approval and reviewer facts. No fixed fake fence
count or Boolean contribution. Match one PID/xid/holder/common database-time
window and preserve relation/trigger/role safety through the final scalar guard.

Extend ProtectedTransactionExecutor solely for the exact new application service
and coordinator and their actual reviewed SQL SHA256 templates; preserve every
old caller/hash/family, private credential route and SQL admission grammar.
Keep500ms work/750ms statement/250ms lock budgets. Materialize immutable response
before exactly one closed scalar SQL final guard; disclose only after known
commit. No post-guard query, provisional effect on rejection or automatic retry
on uncertain commit. Source-pin updates must reflect actual reviewed source.

The Workflow target lookup delegates to a restricted READ_CANDIDATE_FOR_WORKFLOW
use-case path: internally resolve digest to owned scalar bindings, obtain fresh
native READ and company proof before any existence result, then verify envelope
bytes. It exposes only digest and fixed generic label. Normal HTTP candidate
reads additionally bind explicit company/product. Ordinary Workflow transaction
suspension must be tested; an already active protected context rejects nesting.
This adapter exports no domain types, bypass grants or permission Boolean.

Test exact codec Unicode/decimal/null/empty preservation, incomplete-vs-invalid
A behavior, absent supplied revision, approval substitution and stale pins,
independent version sequences, exact replay after later head changes, scoped
non-disclosure, real prepare/approve/publish, CAS concurrent publishers, withdraw/
reactivate, eligibility revocation without history loss, withdrawal despite bad
units/approval, effective boundaries/high-water cursor binding, clock conflicts,
immutable history, ordinary DML rejection and actual final-guard rollback. Real
Workflow transition evidence and native owner proofs are required for positives.
Preserve old raw catalog/qualified GAS consumers and negative import scans.

Actual catalog-only follow-up message:
`docs(custody): refresh product publication storage dictionary`;
MODIFY only doc/database/DATA_DICTIONARY.md and this phase. Obtain complete
migrated-catalog equality and zero unresolved owners; no predicted capture.
B is internal software until C proves configured HTTP and scalar integration.

#### Package C — configured HTTP and typed scalar source export

Depends on accepted A/B. Exact message:
`feat(custody): expose governed product publications`.

CREATE exactly:
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/ProductRevisionPublicationController.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/ProductRevisionPublicationRequests.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/ProductRevisionPublicationResponses.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/publication/ProductPublicationSourceContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/ProductPublicationSourceQueryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/custody/api/rest/controller/ProductRevisionPublicationControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/ProductRevisionPublicationNativeHttpPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/ProductPublicationSourceQueryAdapterTest.java`

MODIFY exactly:
- `src/main/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptor.java`
- `src/test/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptorTest.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java`
- `.github/ci-test-inventory.json`
- `.github/documentation-validation.json`
- `doc/api/openapi.yaml`
- `doc/database/DATA_DICTIONARY.md`
- `doc/roadmap/TASKS_HPS-008.md`

All routes are under /api/v1/custody/engineering-products/{draftId}/publications
and require exactly one companyPartyId query; no alternate caller company scope.
Exact suffix/verb set:

| Verb | Suffix | Governed operation |
|---|---|---|
| POST | /candidates | PREPARE current explicit metadata/property version pair |
| GET | /candidates/{digest} | exact authorized prepared envelope |
| POST | empty | PUBLISH approved candidate with expected head |
| POST | /{publicationId}/withdraw | WITHDRAW selected head with expected head |
| POST | /{publicationId}/reactivate | REACTIVATE snapshot with expected head |
| GET | /{publicationId} | exact historical snapshot |
| GET | /current | current head/state and historical snapshot semantics |
| GET | /effective | explicit asOf committed selection, no future instant |
| GET | /history | bounded limit/cursor/asOf event page |
| GET | /requests/{requestId} | original actor-bound receipt and observed head |

Explicit strict request/response DTOs preserve original string decimals and
distinguish product/catalog/candidate/publication/source-version/head/event/
request identities. Mutations carry requestId; PREPARE carries both expected
source versions; PUBLISH carries candidate digest, expected head and actual
Workflow instance/task/action selectors; REACTIVATE carries expected head and
applicable approval selectors for the same digest; WITHDRAW carries expected
head only. Server derives policy/actor/approval/completeness/time/digests.
Reject unknown/coerced fields, extra scopes, unbounded strings and client-owned
server facts. Initial bounds reuse594/595 facts; digests64 lowercase hex,
UUID identities, referenced owner IDs max80 Unicode scalars, cursor max4096,
page1..100 and microsecond instants. No silent numeric or Unicode normalization.

Return201 for first PREPARE/PUBLISH creation and200 for exact replay/transitions/
reads, explicit empty selected state without fallback. Preserve400 malformed
input,401 missing/malformed authentication,403 non-disclosing native/foreign
target denial,409 authorized CAS/replay/stale-source conflict and503 unavailable/
indeterminate evidence. No SQL, native cause, stack trace or automatic retry
instruction. Current HTTP state is not a current-eligibility certificate.

Delegate only the exact compiled controller/handler/verb/pattern tuples to
native publication admission after authentication; generic route grants cannot
authorize publication. Preserve existing metadata/property and unrelated routes,
with wrong-controller/handler/verb/pattern/anonymous negatives. Full configured
filter/HTTP/PostgreSQL tests must prove all four native producers, both scopes/
all actions, actual Workflow human review, Policy A diagnostics, exact bytes,
history/effective/replay, revocation, two companies, no disclosure or provisional
effect after failure. Standalone MVC or fake permission alone is insufficient.

ProductPublicationSourceContract exports scalar immutable DTOs only: exact
historical and separately currently eligible current/effective modes, explicit
company/product/publication and source IDs/versions/digests, original source bytes/
formats, authored facts/units/origin/diagnostics, actual approval and selected
event/head facts, always engineeringQualified=false. It delegates the same
protected use case; no importer Boolean or actor may substitute native authority.
No existing PlanningProductReferenceContract, SimulationProductCandidateContract
or SimulationGasFluidRevisionContract change, new consumer wiring, numerical
execution, qualification or frontend implementation. Add exact exported-type
classifier/import tests and foreign/private/sibling negatives without admitting
new unregistered consumers. Consumer-specific suitability remains597/later.

Generate OpenAPI only from the actual configured running app and dictionary
only from complete migrated PostgreSQL. Match source/run/attempt, old supported
routes/schemas/security, semantic snapshot equality and compatibility. Preserve
manifest provenance, source pin/count/digest truth and failed gate history.
If unavailable locally, record pending actual capture under AGENTS11.1; the
separate admitted artifact message is
`docs(api): refresh product publication runtime contract`;
MODIFY only doc/api/openapi.yaml, .github/documentation-validation.json and this
phase, plus doc/database/DATA_DICTIONARY.md only for demonstrated physical drift.
No generated snapshot guessed by hand or validator/workflow weakening.

#### Separate final acceptance and current stop

Final exact message:
`docs(custody): accept governed product publication`;
MODIFY only this phase, ULTIMATE_TASKS and status-only596 cells in
ULTIMATE_ROADMAP/ULTIMATE_PHASES. Require exact-SHA Full/Fast/Docs as applicable,
independent task-case/source/inventory/raw report membership and actual same-run
API/compatibility/catalog evidence. Accept software only after every selected
package and responsible repair is independently supported; no predicted test
counts or acceptance from source alone. HAPI-597, integrated593, engineering/
SITE readiness, frontend, solver, release/deployment remain separate.

**Completed registration checks.** The exact current remote documentation
validator and self-test sources were recovered before rerunning all19 tests:
PASS. Live recursive tree checks confirm31 CREATE paths absent and all existing
MODIFY paths present. All six phase task identities/metadata and both status
tables agree; only596's planning status changes. All921 compatibility anchors,
prior phase evidence and other task definitions/statuses are preserved. The
compact index changes only596's current pointer; index/phase sizes remain within
unchanged bounds. The local review tree lacks the remaining phase/archive files,
so full documentation-estate/link validation remains the hosted exact-SHA gate.
No runtime result is inferred from these documentation/source checks.

Before this registration publishes, run available unchanged documentation
self-tests, phase/task/status/anchor/size checks, exact CREATE-absent/MODIFY-
present checks against live source, preserved-history/frozen-status-only diff
and selected four-file blob/tree verification. No Java/PostgreSQL runtime or
new API/catalog capture is claimed in this documentation stage. Missing local
Java21/Maven/Docker is not provisioned under AGENTS11.1. Publish with expected
62c0563e7bef07ada8149cba73c7925f8fc0e667, verify exact parent/tree/message/scope,
observe applicable exact-SHA Documentation CI creation/queue/start and STOP.
Package A is next only after this registration's Docs success and a subsequent
owner Next; A's policy choice does not execute a runtime package automatically.


### HAPI-596 Package A owner authority and Workflow approval source submission — 2026-10-27

Owner Next selects registered Package A after exact-main01ef567bdf62ca54f1c7599db888117fb15a8ae4,
tree9d4f91e40f64a72217a66dfa5d710b0cf079dbae,
parent62c0563e7bef07ada8149cba73c7925f8fc0e667 and Docs638/
run40576017130 completed/success. Live source and AGENTS/roadmap/reconciliation
were recovered; the registration remains controlling. Policy A, accepted594/595,
old gas qualification and all previous evidence remain unchanged.

Exact message:
`feat(custody): add protected product publication owner contracts`.
Scope is exactly the registered11 CREATE and12 MODIFY paths:18 Java sources/
tests, two additive migrations, CI inventory, one dictionary-ownership entry and
this append-only phase record. No DELETE, public publication API, candidate/
publication/head/event/receipt storage, consumer wiring or B/C implementation.
HAPI-596 remains NOT_IMPLEMENTED/IN_PROGRESS overall; source submission is not
hosted acceptance or engineering qualification.

**Implemented owner boundaries.** New native PRODUCT_ENGINEERING_PUBLICATION
READ/PREPARE/PUBLISH/WITHDRAW/REACTIVATE definitions and private exclusive claim
family retain independent company/product CUSTOM decisions, genuine four-provider
origin, current native sampling/revocation/claims,24 Identity relation fences,
opaque issuer/thread/holder/connection-bound one-use state and rejection poisoning.
Existing family markers explicitly exclude publication credentials. The new
owner participant additionally verifies the actual selected reviewer as current
active unlocked HUMAN under the retained Identity user fence; its reviewer fact
is tied to the permitted publication admission and rechecked on currentness.
Migration004 adds five definitions only, rejects conflicts and grants nothing.

WorkflowHumanActorContract/QueryService perform the ordinary Identity-owned
HUMAN/activity/lock/display check. The actual publication-target APPROVE path in
WorkflowTransitionApplicationService requires the actual authenticated human
before action/task/history mutation. The new target has no production lookup in
A: B supplies the immutable Custody candidate owner. The separately protected
publication consumer must also compare actual Workflow action actor to the
protected native reviewer; the ordinary human lookup is not that admission.

The Workflow export issues opaque tickets for configured policy-only preparation
or exact approval, never approval decisions. Empty per-company owner policy pins
definition/version/type/purpose. Its owner-local FKs and strict schema contain no
cross-owner company reference, assignments or seed decisions. Read policy requires
the exact active PRODUCT_SOURCE_PUBLICATION and PRODUCT_SOURCE_PUBLICATION_APPROVAL
catalogs/binding. Approval requires actual terminal instance/task/last APPROVE,
exact envelope digest, current configured policy, actual server actor/display/
time and consistent immutable final state history. CORRECT and nonterminal or
substituted evidence cannot qualify. Current human reviewer proof is retained
as the separate Identity contribution for the future sole Custody coordinator.

The actual adapter retains11 deterministic SHARE NOWAIT owner relation fences:
the new policy plus the ten registered existing Workflow relations. SHARE blocks
row writers while permitting concurrent readers. PostgreSQL16 SHARE admission
requires a writer privilege: the existing protected private-publisher credential
route supplies that technical lock privilege; it is not an ordinary application
credential or permission to issue policy DML. The synthetic fixture explicitly
separates its restricted publisher and SELECT-only ordinary role, as in the
accepted Telemetry owner fixture. No policy DML statement is admitted to the
executor. It rejects elevated/owner/unsafe-schema roles, unsafe relation/partition/RLS/rule/constraint/PK metadata, lost locks,
altered/extra/disabled triggers and changed function bodies. Thirteen actual
trigger/type/function-body images are derived from the reviewed immutable055/
061/066/081/099 migrations, not a guessed catalog capture. Policy column/type/
constraint checks, same PID/xid/role/holder, database-time window and final
currentness checks remain fail closed. Tickets reject replay, foreign issuers,
cross-thread use, suspension and completion; swallowed misuse poisons rollback.
No executor caller/DML grammar, time budget or existing protected family semantics change.

**Authored test evidence, pending execution.** Three new suites contain36 cases:
ProtectedProductPublicationAuthorizationPostgresIntegrationTest20;
WorkflowHumanActorQueryServiceTest3;
CustodyProtectedProductPublicationApprovalPostgresIntegrationTest13.
Seven additional cases extend the six registered existing transition/native/
architecture suites, preserving every old case. Total43 newly authored cases,
not43 passing runtime results. The native fixture exercises four genuine native
producers across all five actions/both scopes, family isolation, denial/current
revocation/deadlines and opaque misuse. Workflow positives execute an actual
configured authenticated human transition with a nested, explicitly test-only
target lookup. Cases cover nonhuman denial before effects, exact/substituted/
unapproved/CORRECT proof, revoked policy/definition/binding,11 fences, ordinary
policy DML rejection, actual action/history immutability, writer/owner-first
races, replay, privileged/disabled-trigger metadata, thread and suspension.
The inherited native physical fixture retains real final-guard rollback cases.

Exact test names/source SHA256 and existing fast/unit/PostgreSQL lanes are
registered. New timings are0/unmeasured, not performance measurements. Existing
source pins are refreshed only for reviewed reader changes; private caller scans
and new export classifier include sibling/private/foreign-consumer negatives.
Only the actual human transition consumer may import the new human export;
protected publication exports have no foreign production consumer until B's
separate exact admission. One Workflow dictionary ownership entry is added.

**Available validation and limits.** All18 changed/new Java sources parse with
the available compiler module. The three new scalar contracts plus existing
ProtectedRelationFence compile using available Java17. These checks are syntax/
standalone-contract evidence, not Java21 project/type/native/PostgreSQL execution.
All19 unchanged exact-current documentation-validator self-tests PASS. Selected
inventory case/hash/membership, private caller/source-pin, migration-only create,
owner entry, preserved-history/status and exact allowlist checks PASS. All12
pre-mutation existing blobs match the live authoritative tree;11 new paths were
absent there. Full local estate validation is unavailable in the partial review
tree. Java21/Maven/Docker/PostgreSQL are unavailable; no environment is installed
under AGENTS11.1. Hosted Full/Fast/Docs and raw task-specific reports remain the
runtime acceptance gate; no skipped database test is called passing evidence.

Physical dictionary capture is explicitly pending the real migrated PostgreSQL
catalog. Preserve DATA_DICTIONARY.md and all existing API/manifest provenance in
this source commit. Use the registered catalog-only follow-up
`docs(workflow): refresh product publication policy dictionary` after actual
same-source catalog recovery; do not infer physical metadata from the new SQL.
Publish directly with expected01ef567bdf62ca54f1c7599db888117fb15a8ae4,
verify exact parent/tree/blobs/message and23-file scope, observe applicable
exact-SHA Full/Fast/Docs creation/queue/start, then STOP. Repair responsible
feedback and satisfy A's dictionary/acceptance gate before selecting B. HAPI-597/
593, numerical computation, engineering/SITE acceptance and deployment remain
separate and unselected.


### HAPI-596 Package A — Full928 reviewer fixture and actual dictionary repair — 2026-10-27

Owner Fail additionally directs measured shard rebalancing and adding runners
only if needed. Main97f3295d1b7dbfde3b27f08c08a9cfad45c415f9,
tree32d829c82fe4848f4d03afce422108ead6df6c0e has Full928/
run40581505626 failure, Fast232 and Docs639 success. Full928's481 raw suites
contain3473 cases, zero failures/skips and one error. PostgreSQL2 reports the
single error in reviewerRequiresPermittedAdmissionAndFreshHumanIdentity; the
API lane independently fails physical dictionary equality. Preserve this failed
run as failure; none of its scheduling data is new Package A acceptance.

**Responsible source repair.** Raw diagnostic artifact12769978740, ZIP SHA256
19351dc7c02b5e594b53cd0499f9d16d464f9bf0335f538af29025537b12c153,
pins the error to reviewer line150, the required eligible-human result count.
The shared authentication fixture intentionally has no display_name. That is
valid for other native authorization tests but cannot satisfy the new reviewer
contract's actual nonblank human display requirement. Set an explicit synthetic
display on this positive reviewer fixture before authenticating; assert both
actual reviewer ID and display. Preserve its nonhuman rejection, original20
case identities and every production eligibility/authorization/currentness rule.
Only the test source hash changes; no production bypass, retry or budget change.

**Actual catalog repair, no inferred metadata.** Recover Full928 API diagnostic
artifact12770745003, ZIP SHA256
98856428a054a1810137954e794e39d110f710fefc8a296ab5b8ebe20f4d6575.
Its real migrated PostgreSQL16.15 schema.json has180 migrations,470 JPA mappings,
523 physical relations and650 source files. All source file SHA256 values match
the current local source inventory. Run the unchanged exact-current generator
against that artifact and actual ownership map; zero owners are unresolved.
Captured source remains97f3295d, never relabeled as this repair's future SHA.
Catalog JSON SHA256f6af18768f8758f488714020dff6c6318ae08b0305516ff4295bcc807ff5f66a;
source bundle125aaeb09ba13168ef870987d3556a6824ac9714dc175eba1efb00568d08c956;
rendered dictionary271082e15ee3ae2a59af891cdc3e796779781043c35e393ac4a944305ce62555.
This closes the pending physical capture obligation, not Full acceptance. No
OpenAPI/manifest, migration, owner mapping or actual database is edited.

**One owner-selected CI repair envelope.** The simultaneous Fail/rebalance
instruction selects this bounded combined corrective submission under the
retained registered HAPI-045 message:
`ci: automate fast feedback and preserve full verification gates`.
MODIFY exactly the reviewer test, .github/ci-test-inventory.json,
.github/scripts/verify_ci_test_inventory.py,
.github/scripts/test_verify_ci_test_inventory.py,
doc/database/DATA_DICTIONARY.md, this phase and TASKS_HPS-024.md.
Seven existing paths, zero CREATE/DELETE. The catalog-only follow-up is satisfied
inside this explicitly selected repair rather than silently starting another
package. HAPI-045's append-only record holds full raw timing/provenance analysis.
Keep seven shards, strict complete membership and all runtime/security gates;
new unmeasured suites receive a scheduling-only median estimate so zeros cannot
pile into one shard. No measured baseline value is fabricated by that fallback.

Available checks:34 scheduler/classification tests and51 Full-gate self-tests
PASS; the two new zero-weight regressions fail against the previous scheduler.
Actual catalog/source/ownership verification and deterministic rendering PASS.
The19 unchanged documentation self-tests, one-file Java syntax parsing and exact
seven-file scope/history/inventory checks also PASS. Java21/Maven/PostgreSQL native
tests remain hosted feedback under AGENTS11.1; no runtime pass is inferred from
the fixture edit. Publish with expected97f3295d, verify exact message/parent/tree/
blobs/scope and observe applicable exact-SHA CI creation/queue/start, then STOP.
Package A and HAPI-596 acceptance remain pending. B/C,597/593, solver and
engineering/SITE/deployment work are not selected.


### HAPI-596 Package A accepted; Package B Workflow fixture scope completed — 2026-10-27

**Exact green prerequisite.** Owner Next follows corrective main
9fe646f0b7780c91b3f5a96abc0cea9d21efa8f6,
tree9cf1ba328e37bb202e8208f8807c24ce537e1c1b,
parent97f3295d1b7dbfde3b27f08c08a9cfad45c415f9. Full929/
run40585259236, Fast233/run40585259325 and Docs640/run40585259241
completed/success on that exact source. HAPI-596 Package A's native/Workflow
owner contracts and actual dictionary obligation are now development-software
accepted. The parent task remains NOT_IMPLEMENTED/IN_PROGRESS: candidate/
publication storage and configured HTTP/scalar integration are still absent.
Policy A and closed594/595 remain unchanged; this is not source publication,
scientific qualification, SITE acceptance or deployment authorization.

**Independent retained evidence.** Full929 attempt1 aggregate artifact12772371045
has ZIP SHA256f2d232d0d439a4b531b755afac8d8da342271e3f1a1f44fb57f4dcf8c191e3a0.
Recover and verify lane SHA/run/attempt/inventory/classifier, every retained file
hash, raw XML class/case/count/timing identities and complete disjoint inventory.
All481 active classes execute3473 cases with zero failures/errors/skips. The
development-partitioned gate correctly retains same_run_equivalence=false and
release_eligible=false; the deliberately skipped independent audit is not called
a passing oracle or production proof.

Package A's exact43 cases are independently matched to current source SHA256,
manifest identities and raw reports. The three direct suites contribute36:
ProtectedProductPublicationAuthorizationPostgresIntegrationTest20,
WorkflowHumanActorQueryServiceTest3,
CustodyProtectedProductPublicationApprovalPostgresIntegrationTest13. The seven
specific registered transition/private-reader/import-guard cases also execute
without failure/error/skip. Full928's positive reviewer fixture failure remains
failed history; Full929 proves its corrected real human display and retained
nonhuman denial. No test result is inferred merely from aggregate green status.

Same-run API facts and all payload hashes match the gate. Actual runtime OpenAPI
has272 paths,296 operations and307 schemas; canonical semantic snapshot equality
and backward compatibility against the captured3de14d1d base PASS independently.
Current API SHA2568b9d9d043ef49dc23a7eeff99787d90b5a7f3c0483485f5a9bdfae76ba0f5da0;
base API9f5dd7301cd1b75ba6fda1c6bf745b1ccb312c79212143c81e7205eff5b4233a.
The actual PostgreSQL16.15 capture retains180 migrations,470 JPA mappings,
650 source hashes and523 relations with zero unresolved owners and reviewed
dictionary equality. Catalog JSON SHA256
638a19ed26ffc28419365b655fa32d06a2c8f79b8bbb8e9ad3af38f1abf35817;
source bundle125aaeb09ba13168ef870987d3556a6824ac9714dc175eba1efb00568d08c956.
Its captured source stays9fe646f0, not this documentation commit's future SHA.

#### Concrete B integration dependency discovered before source mutation

The current A approval integration test imports its nested TargetFixture, whose
publicationTargetFixture bean advertises custody/PRODUCT_SOURCE_PUBLICATION and
resolves only its synthetic digest set. That fixture is explicitly permitted for
isolated A approval evidence and does not persist Custody candidates. The real
WorkflowExecutionOwnership.requireTarget filters all WorkflowOwnedTargetLookup
beans by module/type and requires matches.size()==1. B's registered production
CustodyProductPublicationWorkflowTargetLookup advertises that same module/type.
Adding it unchanged would create two matches in A's test context and reject the
actual positive approval transitions. Removing the synthetic fixture without
replacement would instead send its unpersisted A-only digest to real Custody
storage. Neither result is correct isolated-owner or complete B evidence.

The original B MODIFY list omitted this actual affected test file. Complete that
precise existing-path admission before implementing the production lookup. This
is a source-backed test-composition prerequisite, not missing business approval,
industrial data or a reason to weaken the exactly-one owner rule.

#### Prospective amendment to the registered Package B scope

Retain B's exact implementation message:
`feat(custody): add governed product publication storage`.
Retain all12 registered CREATE and eight original MODIFY paths. ADD exactly this
one existing MODIFY path:
- `src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/CustodyProtectedProductPublicationApprovalPostgresIntegrationTest.java`

B therefore has12 CREATE and nine MODIFY paths. Its existing inventory/phase
paths already cover this test's real new case/source hash. No other path or
runtime policy is admitted by this amendment.

The production lookup must use the exact Spring bean name
custodyProductPublicationWorkflowTargetLookup and the registered protected
READ_CANDIDATE_FOR_WORKFLOW path. In A's isolated test context only, replace that
existing named bean through the already used Spring test mock mechanism and
the public WorkflowOwnedTargetLookup interface; require a real named production
bean to be replaced. Preserve the nested synthetic fixture, all13 actual
transition/approval/lock/denial cases and their assertions. Add the focused case
productionLookupDoesNotDuplicateTheIsolatedApprovalFixture to prove that only
one resolver advertises this target in the isolated context and that the
replaced production lookup cannot resolve or receive its synthetic digest.
Do not broadly mock all lookup beans, remove the test, relax matches.size()==1,
change production behavior by test/profile flags or introduce a permissive
fallback. The test mock is not a publication authority or B acceptance proof.

The already registered CustodyProductPublicationWorkflowTargetLookupTest must
check exact module/type/digest/response binding, authorized use-case delegation,
ordinary-transaction suspension and rejection of protected-context nesting.
The already registered ProductRevisionPublicationPostgresIntegrationTest must
retain an unmocked production lookup and prove real native PREPARE, persisted
envelope digest, actual configured human Workflow transition and atomic PUBLISH.
Only the explicitly isolated A context replaces the production bean. B must
rerun A's original13 cases plus this exact isolation regression and independently
prove its real owner round trip. Every other registered B invariant, strict
import/private guard, current/history/withdrawal policy, immutable bytes/CAS/
replay, final guard,500/750/250 budgets, dictionary follow-up and C/final gate
remains unchanged.

**Current bounded documentation stage.** Exact message:
`docs(custody): accept publication owners and admit storage integration`.
MODIFY only this append-only phase record and596's compact ULTIMATE_TASKS
execution pointer. Zero CREATE/DELETE or source/test/migration/workflow/API/
dictionary change. Frozen status tables and every original registration/history
byte remain unchanged; this prospective scope supplement is explicit. All12
future CREATE paths remain absent and all nine future MODIFY paths are matched
to live source. No B placeholder or partial implementation is submitted.

Completed independent checks cover all43 task cases,481 classes/3473 raw cases,
source pins and all lane/file hashes; actual API semantic/compatibility and
physical catalog/source/dictionary evidence. Available19 unchanged documentation
self-tests and exact two-file scope, metadata/status/compatibility-anchor,
history-preservation and path-admission checks PASS before publication.
No new Java/native run is asserted for this documentation stage. Publish with
expected9fe646f0b7780c91b3f5a96abc0cea9d21efa8f6, verify exact parent/tree/
blobs/message/scope, observe applicable exact-SHA Documentation CI creation/
queue/start and STOP. The next owner Next after Docs success selects amended B
implementation, not another policy choice or repetition of A. C,597/593,
numerical engine, frontend, release/deployment remain unselected.


### HAPI-596 Package B storage implemented; hosted verification pending — 2026-10-28

**Selected stage and current source.** Owner explicitly selected Package B. Its
accepted A runtime is9fe646f0b7780c91b3f5a96abc0cea9d21efa8f6 (Full929,
Fast233 and Docs640 success); the prospective test-isolation amendment is
ce0f4c06d9bce8f91d36655f5c75f08771ff7a25 (Docs641 success). Current green
main79735d1949c4825f460b4d479f058cfd1117ebaa/tree
7bfc78749936904bd8e23276f70f596d604c40ab includes three subsequent,
independent policy-document commits only; its Docs run40590959373 succeeded.
Preserve those commits. Exact selected message:
`feat(custody): add governed product publication storage`.

**Bounded source scope.** Exactly the12 CREATE and nine MODIFY paths registered
above, including the named Workflow fixture amendment; zero DELETE. Domain,
typed use-case/repository ports, coordinator/service, strict binary codec,
private JDBC storage, production Workflow target lookup, three new test classes
and migrationV20261027_006 are implemented. Only the five registered Custody
relations gain dictionary-owner entries. Existing594/595 migrations, source,
old raw/qualified consumers, HTTP routes and generated artifacts stay outside
this stage. No business policy/Workflow definition or permission grant is seeded.

**Implemented contract.** PREPARE pins independently numbered, current metadata
and supplied revisions and exact original formats/bytes/hashes, metadata binding,
classification, both owners' immutable completeness diagnostics, sorted admitted
unit facts and configured Workflow definition/version/type/purpose. Its canonical
digest excludes request/publication IDs, audit and approval, and stores only a
candidate. Complete and incomplete valid source remains unqualified. PUBLISH
requires unchanged current source and envelope facts plus real final human
Workflow approval for the persisted digest. REACTIVATE preserves the exact old
snapshot and admits fresh applicable policy/unit/approval and human facts.
WITHDRAW preserves native company/product authority and valid storage/head while
omitting live catalog/unit/approval eligibility. Historical disclosures retain
original bytes under fresh READ/company proof. Consumer eligibility is separate;
a withdrawal produces no effective source or fallback. Server database time and
strictly increasing per-product event epochs/times drive CAS and explicit bounded
asOf/history cursors. Actor/company/product/kind/request/body-bound replay returns
the original receipt plus observed current head without repeating publication or
reversing subsequent transitions.

**Atomic admission and SQL.** Actual distinct fence unions are37 for historical
reads/replays/withdrawal (24 Identity+2 Party+11 Custody) and50 when fresh Workflow
and unit eligibility participates (+11 Workflow+2 Telemetry). Same PID/xid,
thread/holder/connection, native500ms window and unchanged750ms statement/250ms
lock budgets remain enforced. Exact compiled service/coordinator names and six
actual SQL SHA256 templates extend the private executor route; older families
and SQL hashes remain intact. Source-checked relation/column/key/constraint,
trigger/function and role safety is retained through a captured physical image,
owner currentness and one final scalar SQL guard. Immutable candidate/publication/
event/receipt rows and protected head CAS reject ordinary DML, history mutation,
invalid source binding, epoch/state/time/origin and stale head proposals.
Responses materialize before the guard and leave only after known commit;
indeterminate commit has no automatic retry. No query follows the final guard.

**Workflow integration and tests.** The exact named production bean
custodyProductPublicationWorkflowTargetLookup advertises only custody/
PRODUCT_SOURCE_PUBLICATION and delegates to protected
READ_CANDIDATE_FOR_WORKFLOW, with internally resolved scope, native READ/company
proof, envelope verification and digest-only generic labels. Ordinary Workflow
work suspends for that read; an existing protected context rejects before
suspension and poisons its issuer. A's isolated context replaces only that named
existing bean via enforceOverride and preserves its13 cases, adding the exact
productionLookupDoesNotDuplicateTheIsolatedApprovalFixture regression. The new
Custody PostgreSQL class keeps the real lookup and actual native authentication
and human Workflow transition. New cases cover exact source/codec diagnostics,
Unicode/decimal/null/empty, stale sources/approval, replay, native denial,
company isolation/revocation, catalog/unit/approval revocation vs history and
withdrawal, reactivation, CAS contention, effective/history boundaries, owner
fence unions, immutable/ordinary SQL, forged epochs/time, ticket reuse and actual
expired final scalar guard rollback. Existing import/architecture negatives are
preserved and receive exact new coordinator exports/caller checks.

**Local evidence and explicit limits.** All104 unchanged Python self-tests
passed:34 CI classifier,51 full-evidence gate and19 documentation tests. All17
selected Java sources parsed successfully with the available Java17 compiler
module; production domain/codec/ports compiled and17 authored domain/codec
methods passed a standalone runner using a temporary minimal assertion adapter.
This is not a hosted JUnit/Maven result. Supplemental Java17 type checking with
temporary Spring/technical stubs is advisory only. `mvn -q -DskipTests compile`
could not run (exit127: mvn command not found); Java21/Maven/Docker/PostgreSQL
were not installed under AGENTS11.1. The partial local review tree cannot prove
the full repository inventory/architecture/documentation runtime; the whole-tree
inventory and documentation validators reject missing files in this partial copy,
not a verified hosted defect. Unchanged inventory entries
must remain exact and the hosted lane provides whole-tree proof. No PostgreSQL,
HTTP, OpenAPI compatibility, schema-catalog equality, full-suite or acceptance
pass is claimed from local inspection. New classes retain unmeasured zero
baseline durations and the existing conservative seven-shard scheduler policy.

**Pending catalog and stop.** DATA_DICTIONARY.md remains unchanged because no
actual migrated PostgreSQL catalog was available locally. Capture the exact
hosted migrated catalog and perform only the separately registered
`docs(custody): refresh product publication storage dictionary` follow-up after
actual evidence. Preserve full catalog equality and zero unresolved owners;
never synthesize physical metadata or waive a failed gate. Publish this bounded
source commit with expected main79735d1, verify exact tree/parent/message/blobs/
scope and observe applicable CI creation/queue/start, then STOP. Package B hosted
verification and catalog disposition precede C. HAPI-596 remains
NOT_IMPLEMENTED/IN_PROGRESS pending its independent final acceptance; HAPI-594/
595 remain closed. No solver, engineering qualification, production/SITE
acceptance, release or dependent package is selected.

### HAPI-596 Package B — Full930 owner-boundary and actual dictionary repair — 2026-10-28

**Owner-selected responsible repair.** Owner Fail selects repair of exact main
cd4d94fb618568b02ccd684b6c13ba9a5acd4d73/tree
2be4606ceae6ec31f835daba2904fb073407c8eb. Docs645/run40628015176 succeeded;
Fast234/run40628015202 and Full930/run40628015250 failed. Do not accept B or
advance C. This bounded repair's exact message is
`fix(custody): preserve publication owner boundary and catalog evidence`.
MODIFY only the existing coordinator, publication JDBC adapter, its PostgreSQL
test, ArchitectureGuardrailTest, CI test inventory, physical dictionary and
this owning phase (seven files; no CREATE/DELETE). This combines responsible
source repair with the already registered actual-catalog dictionary follow-up
under the owner's Fail instruction; no independent package or policy is selected.

**Demonstrated source defect and repair.** Fast234 ran2096 cases and the Full930
unit lane ran2140; each had exactly one architecture assertion failure, zero
errors/skips. The actual ArchUnit dependency graph found the publication JDBC
adapter reaching CustodyProtectedProductPublicationAuthorizationContract$Request
through Contribution.request(), despite importing no Identity Java type directly.
Native identity contributions must remain within the exact coordinator. Replace
that outward field with the already defined Custody ProductRevisionPublication.Scope,
constructed from the admitted repository scope; the repository checks full exact
scope equality (including product draft) without accessing Identity types. Keep
native Request and every native authority/reviewer check private to the coordinator.
Update the expired final-guard test's contribution construction only; retain all
28 PostgreSQL cases and original rollback assertions. The architecture regression
still rejects this repository's dependency on Identity Request, and additionally
checks that Contribution exposes the exact Custody-owned Scope record. No broad
export exception, weakened architecture test or new allowlist is introduced.

**Independent actual catalog evidence.** Full930 API diagnostics artifact
12789854221 (ZIP SHA256
79c5c49bd5372b5112a4adda5bbd7fdd65a14f4ddb6df99e2c04ca7bf71952ef)
contains the actual migrated schema.json and verification.json. The latter
explicitly reports physical dictionary drift, the registered initial-source
follow-up. The unchanged generator independently validates all651 migration/JPA
source hashes and the complete artifact, then deterministically renders and
checks the reviewed dictionary with zero unresolved owners:181 migrations,
470 mappings and528 relations on PostgreSQL16.15. Catalog document SHA256
baa2a75289ffeb30f2a89c8c30440b271cb70ebf8f96f5f30f50bda7b027ecc7;
source bundle SHA256
d39e6b6cde437245b05a6dd4116a4685498c909aafe8f80b7ffef13c90ab1418;
dictionary SHA256
af1cd7d7936fa7a9fe12fa054b6f1f4ee092a180cd11c5bebe27da3e5d910b4d.
Captured source stays cd4d94f; it is not relabeled as this future repair commit.
The same API log independently reports runtime OpenAPI semantic equality at
272 paths/296 operations/307 schemas; the API lane as a whole failed and its
later compatibility steps are not claimed passing. No API artifact is edited.

**Infrastructure failure and verification limits.** Six PostgreSQL lanes passed;
postgres-4/job122617508267 failed in dependency setup because wget could not
fetch apache-maven-3.9.11-bin.zip from Maven Central. It never executed tests,
including the28 new publication PostgreSQL cases assigned to that shard. Missing
reports and the final gate failure follow those real failures. Preserve seven
shards, Maven download/cache policy, required-report gates and all test assertions;
the new exact-SHA workflow is required to exercise the missing lane. No runtime
failure inside those unexecuted cases is asserted or concealed.

**Actual available local checks and stop.** The unchanged dictionary generator
passed complete source/catalog validation, deterministic render and exact reviewed
dictionary equality. Dictionary/documentation self-tests are run unchanged. A
supplemental Java17 check using temporary Spring/technical stubs reproduces the
old repository's Identity dependency in bytecode and proves its absence after the
repair; this is advisory and not full Maven/ArchUnit acceptance. All selected
Java sources must parse, original case inventories remain intact, and only the
two modified test source hashes refresh. Java21/Maven/Docker/PostgreSQL are not
provisioned under AGENTS11.1. Full runtime/architecture/PG verification remains
hosted. Publish directly with expected cd4d94f, verify exact tree, seven blobs,
parent/message and applicable CI creation/queue/start, then STOP. B acceptance
and C stay pending; HAPI-596 remains NOT_IMPLEMENTED/IN_PROGRESS and594/595 stay
closed. No solver, engineering qualification, release, production/SITE or
unselected implementation is claimed.

### HAPI-596 Package B — Full931 regression-test import repair — 2026-10-28

**Owner-selected responsible repair.** Owner Fail selects exact main
cb952838eeb389ccc326b5c661110324295d3388/tree
81b3a9fead72da8c9c0fdc4055ee0dabf1730f15. Docs646 succeeded. Full931/
run40629278240 and Fast235/run40629278458 failed. Their common testCompile
failure is ArchitectureGuardrailTest.java:798: unqualified assertEquals cannot
resolve because the file statically imports only assertTrue/assertFalse. All
seven PostgreSQL job logs and the unit log report that same compile error; no
PostgreSQL runtime test result is inferred from those lanes. The new scope
regression introduced in the previous repair caused this source defect.

Exact repair message:
`fix(custody): compile publication scope boundary regression`.
MODIFY exactly ArchitectureGuardrailTest, its source hash in the CI test
inventory, and this owning phase (three existing files; no CREATE/DELETE).
Add the single explicit JUnit Assertions.assertEquals static import. Preserve
all assertions, all21 architecture test cases, every other test invocation and
inventory field, and all production/SQL/migration/fence/budget behavior. No
boundary exception, assertion removal, workflow edit or test skip is admitted.

Full931's actual API contract/dictionary lane and static lane succeeded, which
confirms the prior physical-dictionary repair on that SHA; no regenerated
artifact is changed here. B's actual publication PostgreSQL runtime verification
remains pending. Available Java syntax parsing and unchanged documentation
self-tests are local checks only; full Java21/Maven test compilation and
execution remain hosted under AGENTS11.1. Publish with expected cb95283, verify
exact parent/tree/message/three blobs and applicable CI creation/queue/start,
then STOP. HAPI-596 remains NOT_IMPLEMENTED/IN_PROGRESS; B acceptance and C are
not selected. HAPI-594/595 remain closed.

### HAPI-596 Package B — Full932 provisional SQL-phase repair — 2026-10-28

Owner Fail selects exact9087f89c88729bc4975a29e51ba7c5470682aefa.
Full932/run40631168448 passed unit/architecture, API/dictionary, static and six
PostgreSQL lanes. Shard4/job122627628371 ran311 cases with2 failures and23 errors,
zero skips. Publication positives fail with SQLSTATE25000: SELECT clock_timestamp()
is not admitted during the provisional phase. Its downstream final-guard test
cannot reach its intended assertion. The executor correctly rejects this read;
do not admit SELECT or broaden its six exact mutation SQL templates.

Exact repair message: `fix(custody): plan publication reads before provisional writes`.
MODIFY only ProductRevisionPublicationRepositoryPort,
ProductRevisionPublicationApplicationService,
JdbcProductRevisionPublicationRepositoryAdapter and this phase (four existing
files; no CREATE/DELETE). Add a one-use repository plan step after collected
owner evidence and before beginProvisional. It validates the immutable envelope,
samples real database time and checks any existing candidate under the same
retained fences/native window. Bind the resulting plan to its exact contribution.
Apply consumes that plan once and issues only the already reviewed DML. Move
both clock sampling and candidate-existence/integrity reads out of the provisional
interval. Preserve response materialization, post-write owner currentness,
physical-image capture, one final scalar guard, commit disclosure and all500/750/
250ms limits. No schema, migration, SQL hash, production permission, architecture
exception, dictionary or test assertion changes. Existing28 publication cases
remain regression coverage, including native round trip, duplicate candidates,
replay and actual expired final-guard rollback.

The separate existing ProductEngineeringDraftNativeHttpPostgresIntegrationTest
expiredNativeSessionCannotReadOrAppend also failed: expected403, observed503.
Its log does not expose a sufficient underlying owner cause. Preserve that
assertion and accepted594 production behavior; do not reinterpret503 as passing
revocation evidence. This observation remains unresolved until exact-SHA hosted
feedback; neither that test nor B acceptance is claimed green here.

Available local Java syntax and supplemental Java17 stub-based type checks are
advisory; hosted Java21/PostgreSQL tests remain required under AGENTS11.1.
Inspect apply for absence of JDBC reads and retain existing mutation templates;
publish with expected9087f89, verify four blobs/tree/parent/message and CI
creation/queue/start, then STOP. HAPI-596 remains NOT_IMPLEMENTED/IN_PROGRESS;
B acceptance/C and final engineering/SITE acceptance remain pending.594/595 stay
closed; no subsequent package or qualification is selected.

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
