# HPS-001 — Identity and Authentication: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-001 — Identity and Authentication

<a id="hapi-011"></a>
## HAPI-011 — add ldap and local authentication roadmap

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-001

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Establish initial execution memory

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`

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

- `ccaa8727f6a3dc659b5c5c99dda985cacc68b575` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1397](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1397)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1397 | AUTH-001 | docs(authentication): add ldap and local authentication roadmap | Establish initial execution memory | Completed
~~~~~~~~

<a id="hapi-012"></a>
## HAPI-012 — refocus roadmap on authentication gaps

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-001A

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Preserve correct Identity implementation and target runtime gaps

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/IdentityModule.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/IdentityApi.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/IdentityRestApi.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationCommandController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationQueryController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityAuthenticationWebMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/IdentityAdministrationRequests.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/IdentityApplicationServiceRegistry.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/command/IdentityAdministrationCommands.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/mapper/IdentityApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedDirectoryIdentity.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationCommandUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationCommandUseCase.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/IdentityModule.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/IdentityApi.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/IdentityRestApi.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationCommandController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationQueryController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityAuthenticationWebMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/IdentityAdministrationRequests.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/IdentityApplicationServiceRegistry.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/command/IdentityAdministrationCommands.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/mapper/IdentityApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedDirectoryIdentity.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationCommandUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`

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

- `53d08eacdc67f0ad7ef8132531c69aa448881029` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1398](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1398)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1398 | AUTH-001A | docs(authentication): refocus roadmap on authentication gaps | Preserve correct Identity implementation and target runtime gaps | Completed
~~~~~~~~

<a id="hapi-013"></a>
## HAPI-013 — align roadmap with dynamic provider routing

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-001B

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Preserve OIDC, make provider-selection runtime routing explicit, add central provider routing and unified JWT plan

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`

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

- `acf9d503613a2fc6d415c537b54ea8e72e39a2e5` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1399](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1399)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1399 | AUTH-001B | docs(authentication): align roadmap with dynamic provider routing | Preserve OIDC, make provider-selection runtime routing explicit, add central provider routing and unified JWT plan | Completed
~~~~~~~~

<a id="hapi-014"></a>
## HAPI-014 — inventory existing authentication runtime contracts

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-002

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Classify live provider DTOs, provider-selection contract, OIDC flow, Basic/in-memory path, JWT components, Identity models, persistence, and missing pieces

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/IdentityModule.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/IdentityApi.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/IdentityRestApi.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationCommandController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationQueryController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityAuthenticationWebMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/IdentityAdministrationRequests.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/IdentityApplicationServiceRegistry.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/command/IdentityAdministrationCommands.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/mapper/IdentityApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedDirectoryIdentity.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationCommandUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationCommandUseCase.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/IdentityModule.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/IdentityApi.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/IdentityRestApi.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationCommandController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationQueryController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityAuthenticationWebMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/IdentityAdministrationRequests.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/IdentityApplicationServiceRegistry.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/command/IdentityAdministrationCommands.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/mapper/IdentityApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedDirectoryIdentity.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationCommandUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-002 — Inventory existing authentication runtime contracts

Commit:

```text
chore(authentication): inventory existing authentication runtime contracts
```

Purpose: establish evidence before production changes.

Status:

```text
Completed — live inventory recorded in section 17A; no production implementation added.
```

Validation: documentation/source inspection only; evidence and result are recorded in section 17A.5.

---

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

- `6f6ca33f6deb2352b3f31d2b04112856c8442f1d` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1400](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1400)
- [docs/roadmap/authentication.md:1434](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1434)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1400 | AUTH-002 | chore(authentication): inventory existing authentication runtime contracts | Classify live provider DTOs, provider-selection contract, OIDC flow, Basic/in-memory path, JWT components, Identity models, persistence, and missing pieces | Completed — live inventory recorded; no production code added
~~~~~~~~

<a id="hapi-015"></a>
## HAPI-015 — add provider-specific authentication request tokens

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-003

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add/reuse distinct Spring Authentication request types for deterministic LOCAL, LDAP/AD, and OIDC routing

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/platform/security/LdapAuthenticationToken.java`
- `src/main/java/dz/sh/hidra/platform/security/LocalAuthenticationToken.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/platform/security/LdapAuthenticationToken.java`
- `src/main/java/dz/sh/hidra/platform/security/LocalAuthenticationToken.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-003 — Provider-specific Authentication request tokens

Commit:

```text
feat(authentication): add provider-specific authentication request tokens
```

Precondition: AUTH-002 confirmed equivalent token types do not already exist.

Requirements:

```text
distinct LOCAL request token
distinct LDAP/AD request token
OIDC request/result adapter only where required
credentials erased where Spring contract permits
no business authorization logic
no persistence access
```

Status:

```text
Completed — LocalAuthenticationToken and LdapAuthenticationToken are distinct unauthenticated Spring request types. Both implement CredentialsContainer and erase the credential reference. No duplicate password-style OIDC request token was added because the existing browser OIDC/JWT resource-server path already provides the distinct external authentication result path.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — HidraAPI CI run 93 completed both repository tests and the acceptance `mvn -q test` step successfully for commit 2ef4ea234875205fd145661466a8ad9a607eff2f.
```

---

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

- `2ef4ea234875205fd145661466a8ad9a607eff2f` — exact registered-message commit; surviving paths matched to current tree.
- `91e2de7ed17cc609fd1b6630ca5926a9eed4ab86` — exact registered-message commit; surviving paths matched to current tree.
- `1cf6fba4c612d2b1df5d4052cd49bbb1a12e8618` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1401](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1401)
- [docs/roadmap/authentication.md:1454](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1454)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1401 | AUTH-003 | feat(authentication): add provider-specific authentication request tokens | Add/reuse distinct Spring Authentication request types for deterministic LOCAL, LDAP/AD, and OIDC routing | Completed — LOCAL and LDAP/AD request tokens added; OIDC existing JWT/OIDC result path preserved; `mvn -q test` passed in CI run 93
~~~~~~~~

<a id="hapi-016"></a>
## HAPI-016 — add authentication request router

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-004

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Convert an explicit provider selection into the correct Authentication request without credential verification

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityAuthenticationRequestRouter.java`
- `src/main/java/dz/sh/hidra/platform/security/LdapAuthenticationToken.java`
- `src/main/java/dz/sh/hidra/platform/security/LocalAuthenticationToken.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityAuthenticationRequestRouter.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityAuthenticationRequestRouter.java`
- `src/main/java/dz/sh/hidra/platform/security/LdapAuthenticationToken.java`
- `src/main/java/dz/sh/hidra/platform/security/LocalAuthenticationToken.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-004 — Authentication request router

Commit:

```text
feat(authentication): add authentication request router
```

Convert an explicit provider selection into the correct Spring `Authentication` request. AUTH-002 confirmed no existing public `authType` DTO; do not invent a second provider taxonomy merely for routing.

Requirements:

```text
LOCAL -> LOCAL token
LDAP/ACTIVE_DIRECTORY -> LDAP token
OIDC -> existing OIDC initiation/completion path
unsupported/disabled type -> fail closed
no credential verification in router
no provider fallback
```

Status:

```text
Completed — IdentityAuthenticationRequestRouter lives in Identity infrastructure so ProviderType remains Identity-owned. LOCAL maps only to LocalAuthenticationToken; LDAP and ACTIVE_DIRECTORY map only to LdapAuthenticationToken. OIDC is deliberately rejected from the direct-credential route with an explicit instruction to use the existing external OIDC flow. All other provider types fail closed. The router performs no credential verification, persistence access, token issuance, or provider fallback.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 96 completed the repository test check and the acceptance `mvn -q test` step successfully for AUTH-004 before roadmap finalization.
```

---

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

- `a7d5787ef32379ea16010cb5d4110cb850369bf2` — exact registered-message commit; surviving paths matched to current tree.
- `e9045efcf72e9ae6c94c02a7f1f98c44b2c9d137` — exact registered-message commit; surviving paths matched to current tree.
- `166836b608901da229ddc012e6b1c675a9f4c644` — exact registered-message commit; surviving paths matched to current tree.
- `394232f220186c9bd8e7d277573363830de74106` — exact registered-message commit; surviving paths matched to current tree.
- `943890732264b045e5549dd93de5691a27502e46` — exact registered-message commit; surviving paths matched to current tree.
- `98963b3877db13f6cec7cb210ebb7c402f219ce9` — exact registered-message commit; surviving paths matched to current tree.
- `abca9fa980e577f74fcab2f8e8aad70668335e43` — exact registered-message commit; surviving paths matched to current tree.
- `ccd52a87377f650724142d180a97741577d856d2` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1402](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1402)
- [docs/roadmap/authentication.md:1495](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1495)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1402 | AUTH-004 | feat(authentication): add authentication request router | Convert an explicit provider selection into the correct Authentication request without credential verification | Completed — Identity-owned router maps LOCAL and LDAP/AD deterministically, preserves OIDC external flow, rejects unsupported direct providers, and performs no credential verification; `mvn -q test` passed in PR CI run 96
~~~~~~~~

<a id="hapi-017"></a>
## HAPI-017 — compose authentication provider manager

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-005

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Register provider-specific strategies behind one AuthenticationManager/ProviderManager with no fallback

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraAuthenticationManagerConfiguration.java`
- `src/main/java/dz/sh/hidra/platform/security/LdapAuthenticationToken.java`
- `src/main/java/dz/sh/hidra/platform/security/LocalAuthenticationToken.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraAuthenticationManagerConfiguration.java`
- `src/main/java/dz/sh/hidra/platform/security/LdapAuthenticationToken.java`
- `src/main/java/dz/sh/hidra/platform/security/LocalAuthenticationToken.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-005 — ProviderManager composition

Commit:

```text
feat(authentication): compose authentication provider manager
```

Register the supported provider strategies behind one `AuthenticationManager`/`ProviderManager`.

Each provider must support only the intended Authentication request type.

Do not use provider ordering as the primary provider discriminator.

Status:

```text
Completed — HidraAuthenticationManagerConfiguration now exposes one ProviderManager-backed AuthenticationManager for direct provider authentication. It registers only AuthenticationProvider beans that support exactly one of LocalAuthenticationToken or LdapAuthenticationToken, rejects a provider that claims both request types, and uses a fail-closed parent AuthenticationManager when no matching provider is installed. Existing bootstrap Basic authentication and the external OIDC/JWT path are preserved; no LOCAL, LDAP, or OIDC provider implementation was introduced early.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 104 completed the repository test check and the acceptance `mvn -q test` step successfully for AUTH-005 implementation commit 00cc471a916fea8882ed25f5d2c6297155899b2b.
```

---

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

- `2d35d961c912550b94b4b8c696f3e6388ca53285` — exact registered-message commit; surviving paths matched to current tree.
- `4707c9b8224ad0a6adf5d130af6bff70bbc249e4` — exact registered-message commit; surviving paths matched to current tree.
- `b8414df64aef6560ae435c23aef3c850edebeed8` — exact registered-message commit; surviving paths matched to current tree.
- `4a1d7fdd52ec0ba71e91cd74980ae857e271eec2` — exact registered-message commit; surviving paths matched to current tree.
- `d4bcf87192b3c338578de855ca4707893fd36d2d` — exact registered-message commit; surviving paths matched to current tree.
- `44f7a0e49236c5f1a0ddb2052bd58e032fdd0d39` — exact registered-message commit; surviving paths matched to current tree.
- `00cc471a916fea8882ed25f5d2c6297155899b2b` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1403](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1403)
- [docs/roadmap/authentication.md:1536](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1536)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1403 | AUTH-005 | feat(authentication): compose authentication provider manager | Register provider-specific strategies behind one AuthenticationManager/ProviderManager with no fallback | Completed — central fail-closed ProviderManager composed for provider-specific LOCAL and LDAP request types; `mvn -q test` passed in PR CI run 104
~~~~~~~~

<a id="hapi-018"></a>
## HAPI-018 — normalize authenticated hidra principal

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-006

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Reuse/add one Hidra principal contract shared by LOCAL, LDAP/AD, and OIDC

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/platform/security/AuthenticatedPrincipal.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/platform/security/AuthenticatedPrincipal.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-006 — Normalized Hidra principal

Commit:

```text
feat(identity): normalize authenticated hidra principal
```

Preserve the existing technical `AuthenticatedPrincipal`/current-security-context adapter, but add the minimum Identity-owned normalized authenticated principal required to carry stable Hidra identity and authorization information across LOCAL, LDAP/AD and OIDC.

No provider-specific business model duplication and no transfer of User/Role/Permission ownership into platform.

Status:

```text
Completed — HidraPrincipal is an Identity-domain record that implements java.security.Principal without Spring dependencies. It carries stable Hidra user ID, Hidra username/display name, ProviderType, optional IdentityProvider linkage, and immutable Hidra-owned role/permission sets. Principal.getName() returns the stable Hidra user ID. The existing platform AuthenticatedPrincipal and current-security-context adapters remain unchanged.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 111 completed the repository test check and the acceptance `mvn -q test` step successfully for AUTH-006 implementation commit 17da3f61c338621bdd9fd641442877241abd239a.
```

---

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

- `50df7c5cc3ace5614115186f0309c397b1ca00ff` — exact registered-message commit; surviving paths matched to current tree.
- `d080340db8f6249ca2e93eb5eefdd97f48df8257` — exact registered-message commit; surviving paths matched to current tree.
- `0c66e2c5dd9c2e00d81c01eb3b6dce3189d389f0` — exact registered-message commit; surviving paths matched to current tree.
- `89718a7fa0cf245e947994a2184a8d7c3fdda111` — exact registered-message commit; surviving paths matched to current tree.
- `5378f20decc7161048d4dd7798d8a529cc6ac097` — exact registered-message commit; surviving paths matched to current tree.
- `e3c36b1b4ed322303c26f21ad29bb1db5a097049` — exact registered-message commit; surviving paths matched to current tree.
- `17da3f61c338621bdd9fd641442877241abd239a` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1404](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1404)
- [docs/roadmap/authentication.md:1570](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1570)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1404 | AUTH-006 | feat(identity): normalize authenticated hidra principal | Reuse/add one Hidra principal contract shared by LOCAL, LDAP/AD, and OIDC | Completed — Identity-owned HidraPrincipal added with stable Hidra user identity, provider source, optional provider linkage, and Hidra roles/permissions; `mvn -q test` passed in PR CI run 111
~~~~~~~~

<a id="hapi-020"></a>
## HAPI-020 — add local credential model and port

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-008

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add the proven-missing LOCAL credential domain/application contract

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LocalCredentialRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LocalCredentialRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LocalCredentialRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-008 — LOCAL credential model and port

Commit:

```text
feat(identity): add local credential model and port
```

AUTH-002 proved no equivalent persistent credential model exists.

Do not put password hashes on ordinary User DTOs or expose Spring PasswordEncoder in the domain.

Status:

```text
Completed — LocalCredential is an Identity-domain record for the one-way password hash and credential metadata (id, stable Hidra user ID, credential status, password-change/create/update timestamps). LocalCredentialRepositoryPort is an application outbound port exposing save, findById, and findByUserId without Spring Data or PasswordEncoder dependencies. No plaintext password, persistence adapter, migration, authentication provider, or login API was introduced.
```

Validation:

```bash
mvn -q -DskipTests compile
```

Result:

```text
PASS — pull-request CI run 125 completed the acceptance `mvn -q -DskipTests compile` step successfully for AUTH-008 implementation commit 26585e8be08f43acd8dc8504421ae0b0fd1ff898.
```

---

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

- `d46e39192b2e7ceae425fd3b9802ad6df85c8cb0` — exact registered-message commit; surviving paths matched to current tree.
- `85327f96a64c8289031b48e446f3aa88fe72b69e` — exact registered-message commit; surviving paths matched to current tree.
- `dd93a44eff0392c27ccbd0b1ce8730ebcb1d2d55` — exact registered-message commit; surviving paths matched to current tree.
- `dfc5d1f72c34dae4a7bb4496c8bbc3980c3504cb` — exact registered-message commit; surviving paths matched to current tree.
- `51a9eb086c652638068e3dbc7a150100ca678857` — exact registered-message commit; surviving paths matched to current tree.
- `e68c1794ee609361d153a35e1e0b9f2f64f26c9d` — exact registered-message commit; surviving paths matched to current tree.
- `26585e8be08f43acd8dc8504421ae0b0fd1ff898` — exact registered-message commit; surviving paths matched to current tree.
- `e96aa0157dc39670883f3d50ddb0356d8f9d057f` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1406](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1406)
- [docs/roadmap/authentication.md:1644](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1644)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1406 | AUTH-008 | feat(identity): add local credential model and port | Add the proven-missing LOCAL credential domain/application contract | Completed — Identity-owned LocalCredential and LocalCredentialRepositoryPort added without Spring PasswordEncoder/domain leakage or persistence implementation; `mvn -q -DskipTests compile` passed in PR CI run 125
~~~~~~~~

<a id="hapi-021"></a>
## HAPI-021 — add local credential persistence

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-009

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add forward Flyway migration plus JPA repository/adapter for LOCAL password hashes

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LocalCredentialRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLocalCredentialRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LocalCredentialJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/LocalCredentialJpaRepository.java`
- `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LocalCredentialRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLocalCredentialRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LocalCredentialJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/LocalCredentialJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LocalCredentialRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLocalCredentialRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LocalCredentialJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/LocalCredentialJpaRepository.java`
- `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-009 — LOCAL credential persistence

Commit:

```text
feat(identity): add local credential persistence
```

Requirements:

```text
forward-only Flyway migration
PostgreSQL-compatible schema
JPA entity/repository/adapter aligned with Identity infrastructure style
no modification of released migrations
no hash exposure/logging
```

Status:

```text
Completed — added forward-only V20260915_001 migration for hidra_identity_local_credential with one credential row per Hidra user, password hash storage, credential lifecycle metadata, user foreign key, and supporting indexes. Added LocalCredentialJpaEntity, LocalCredentialJpaRepository, and JpaLocalCredentialRepositoryAdapter implementing the existing LocalCredentialRepositoryPort. No released migration was modified, no plaintext password handling was added, and AUTH-010 authentication-provider behavior was not implemented.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 131 completed both repository and acceptance test checks successfully for AUTH-009 implementation commit 3e9bfa1a539fc545c8f75133978f023d8c01f743.
```

---

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

- `1e6539d81a3b598ef1b4f163ad2e359eacdccfb3` — exact registered-message commit; surviving paths matched to current tree.
- `df9645407da09e6cf58a9da43f97a61dcf04f47a` — exact registered-message commit; surviving paths matched to current tree.
- `5a5ba8fe8c5a1d62f4673f247be683259a67b030` — exact registered-message commit; surviving paths matched to current tree.
- `34da8ebec31109c9bfb87ecc0566835f1a80c7cb` — exact registered-message commit; surviving paths matched to current tree.
- `587230e3c2d39447220d1e768f62a0f92220ec52` — exact registered-message commit; surviving paths matched to current tree.
- `a5fae23b0d62372a3db4a313f1a95e04107ecea6` — exact registered-message commit; surviving paths matched to current tree.
- `3e9bfa1a539fc545c8f75133978f023d8c01f743` — exact registered-message commit; surviving paths matched to current tree.
- `ae0f82a77a82f4e3d2d04ff702096616bbde64c2` — exact registered-message commit; surviving paths matched to current tree.
- `43c47163fd74077d7cfd796bdc38593ba71f9e53` — exact registered-message commit; surviving paths matched to current tree.
- `d0bf04f88a8d549b50b245360271e3f9455e4145` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1407](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1407)
- [docs/roadmap/authentication.md:1676](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1676)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1407 | AUTH-009 | feat(identity): add local credential persistence | Add forward Flyway migration plus JPA repository/adapter for LOCAL password hashes | Completed — forward-only PostgreSQL migration plus LocalCredential JPA entity, Spring Data repository, and application-port adapter added; `mvn -q test` passed in PR CI run 131
~~~~~~~~

<a id="hapi-022"></a>
## HAPI-022 — add database local authentication provider

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-010

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Replace ordinary in-memory LOCAL verification with persistent password verification behind LocalAuthenticationProvider

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java`
- `src/main/java/dz/sh/hidra/platform/security/LocalAuthenticationToken.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserJpaRepository.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java`
- `src/main/java/dz/sh/hidra/platform/security/LocalAuthenticationToken.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-010 — Database-backed LOCAL provider

Commit:

```text
feat(authentication): add database local authentication provider
```

Requirements:

```text
resolve existing Hidra User/provider binding
verify account state
load LOCAL credential
PasswordEncoder.matches(...)
return normalized HidraPrincipal
never query in-memory bootstrap on failed ordinary LOCAL credentials
no token issuance inside provider
```

Status:

```text
Completed — LocalAuthenticationProvider handles only LocalAuthenticationToken, resolves one ACTIVE LOCAL IdentityProvider plus the persisted Hidra User and LOCAL credential, rejects locked/non-active users and non-active credentials, verifies the submitted password using the existing PasswordEncoder, resolves Hidra-owned effective permissions, and returns an authenticated HidraPrincipal. Failed ordinary LOCAL credentials never consult InMemoryUserDetailsManager, and the provider does not issue tokens or record AUTH-011 login outcomes.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 138 completed repository and acceptance `mvn -q test` checks successfully for AUTH-010 implementation commit b659f11ff30fbb3ddf3971b51ea31b4ba834b73f.
```

---

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

- `ff408e44486b1f450cd7a0d261d5db54a4568fc1` — exact registered-message commit; surviving paths matched to current tree.
- `2744a7984740d3310747034aa9b2cc7a70e53bc3` — exact registered-message commit; surviving paths matched to current tree.
- `b11812cff92d197abeeacaa58c9635388a6fe1f2` — exact registered-message commit; surviving paths matched to current tree.
- `a9839edb03357fa8d342ae7164f63c79dfb54d89` — exact registered-message commit; surviving paths matched to current tree.
- `a592cfe5676509944594f02a48961aced2553349` — exact registered-message commit; surviving paths matched to current tree.
- `e2eda02a056ea5c5281142d4a9bc7cd156224adc` — exact registered-message commit; surviving paths matched to current tree.
- `b659f11ff30fbb3ddf3971b51ea31b4ba834b73f` — exact registered-message commit; surviving paths matched to current tree.
- `4bd606e6125eb57bdb2d1b937989ef941fe008cd` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1408](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1408)
- [docs/roadmap/authentication.md:1714](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1714)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1408 | AUTH-010 | feat(authentication): add database local authentication provider | Replace ordinary in-memory LOCAL verification with persistent password verification behind LocalAuthenticationProvider | Completed — LOCAL requests now resolve the active LOCAL IdentityProvider and persisted Hidra User/LocalCredential, enforce account/credential state, verify via PasswordEncoder, and return HidraPrincipal without in-memory fallback or token issuance; `mvn -q test` passed in PR CI run 138
~~~~~~~~

<a id="hapi-023"></a>
## HAPI-023 — record local authentication outcomes

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-011

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Apply existing User lock/login state and AuthenticationEvent to LOCAL success/failure

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAuthenticationOutcomeApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAuthenticationOutcomeApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAuthenticationOutcomeApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-011 — LOCAL authentication outcomes

Commit:

```text
feat(identity): record local authentication outcomes
```

Reuse existing User login counters/lock state and AuthenticationEvent.

Never record the submitted password.

Status:

```text
Completed — LOCAL authentication outcomes are recorded through an Identity application service in independent transactions. Success resets failedLoginCount, updates lastAuthenticatedAt, preserves existing account/lock state, and records LOGIN_SUCCESS with AuthenticationProtocol.LOCAL. Failures against a resolved user increment failedLoginCount without inventing a new lockout threshold or duration, preserve existing lockedUntil/status, and record LOGIN_FAILED with sanitized reason codes. The submitted password is never passed to or persisted by the outcome recorder.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 146 completed repository and acceptance `mvn -q test` checks successfully for AUTH-011 implementation commit 1918492f9d6ba894571b1c1c403c5dbadf39b924.
```

---

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

- `6cb03e75365d4003de09c1e9f765299f6b041c03` — exact registered-message commit; surviving paths matched to current tree.
- `d89578e1f0e80fbae4036fd92e3c7a306ca6bb77` — exact registered-message commit; surviving paths matched to current tree.
- `75dd24eaaced9b6cb6b2d6a587e3e601c1decaba` — exact registered-message commit; surviving paths matched to current tree.
- `1e6cad4f03c452685f787f05e7df1170c3b24b6a` — exact registered-message commit; surviving paths matched to current tree.
- `8bd0266f15d2b7519797226af4099763a4fcf98f` — exact registered-message commit; surviving paths matched to current tree.
- `5a9b717480dcf12bb5e33970ce03046ce046b0d3` — exact registered-message commit; surviving paths matched to current tree.
- `1918492f9d6ba894571b1c1c403c5dbadf39b924` — exact registered-message commit; surviving paths matched to current tree.
- `cf61aed5b647e24a10b84f4158283815fb95369c` — exact registered-message commit; surviving paths matched to current tree.
- `f666a5366491d38a33cc052c9549f21409cb3c5e` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1409](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1409)
- [docs/roadmap/authentication.md:1754](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1754)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1409 | AUTH-011 | feat(identity): record local authentication outcomes | Apply existing User lock/login state and AuthenticationEvent to LOCAL success/failure | Completed — LOCAL success resets failed-login count, updates last-authenticated state, and records LOGIN_SUCCESS; failures for resolved users increment failed-login count while preserving existing lock state and record sanitized LOGIN_FAILED events; submitted passwords are never recorded; `mvn -q test` passed in PR CI run 146
~~~~~~~~

<a id="hapi-024"></a>
## HAPI-024 — add ldap security infrastructure

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-012

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add required LDAP dependency/configuration/TLS/timeouts without domain coupling

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `pom.xml`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraLdapSecurityConfiguration.java`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraLdapSecurityProperties.java`
- `src/main/resources/application.properties`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `pom.xml`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraLdapSecurityConfiguration.java`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraLdapSecurityProperties.java`
- `src/main/resources/application.properties`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-012 — LDAP security infrastructure

Commit:

```text
feat(authentication): add ldap security infrastructure
```

Requirements:

```text
add only required Maven LDAP dependencies
externalized URL/base DN/search/filter/bind configuration
TLS/LDAPS production guard
connection/read timeout configuration
no company secrets committed
```

Status:

```text
Completed — added Spring LDAP core as the only LDAP dependency required for the technical connection seam. Added HidraLdapSecurityProperties and HidraLdapSecurityConfiguration, with LDAP disabled by default, externalized URL/base DN/user search/filter/bind settings, externalized bind password, configurable connection/read timeouts, and an enabled-only LdapContextSource. Enabled staging/production LDAP must use ldaps://. No directory credential verification, Hidra user mapping, LDAP AuthenticationProvider, company secret, or AUTH-013 behavior was introduced.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 153 completed repository and acceptance `mvn -q test` checks successfully for AUTH-012 implementation commit 4d62eb5f1530f4a4f20acb778a7740dfbf407f1a.
```

---

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

- `0fafe7b257e7942e4adb38befad5567d338f4232` — exact registered-message commit; surviving paths matched to current tree.
- `3bf90694535105782db4c4ea8ed08592cc8d2afe` — exact registered-message commit; surviving paths matched to current tree.
- `9e26e35c5d497c238673a716f573e8dbe3678add` — exact registered-message commit; surviving paths matched to current tree.
- `e66332c18487beae67157729ef685231697cc4bb` — exact registered-message commit; surviving paths matched to current tree.
- `bc61681c8ce4d37ffe143e6646a7978d0bc7ff7a` — exact registered-message commit; surviving paths matched to current tree.
- `c0e6163132b778a6415c9484211cd7787ea51991` — exact registered-message commit; surviving paths matched to current tree.
- `4d62eb5f1530f4a4f20acb778a7740dfbf407f1a` — exact registered-message commit; surviving paths matched to current tree.
- `555bd4e820f3584184a798a4b28cbb3438dc4ce1` — exact registered-message commit; surviving paths matched to current tree.
- `a237420597bb90e614c78ea7cab66665882bd552` — exact registered-message commit; surviving paths matched to current tree.
- `f68cb7603140ce29c2c5f85e5f389b4bb4c05b01` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1410](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1410)
- [docs/roadmap/authentication.md:1786](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1786)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1410 | AUTH-012 | feat(authentication): add ldap security infrastructure | Add required LDAP dependency/configuration/TLS/timeouts without domain coupling | Completed — Spring LDAP core plus externalized URL/base/search/bind configuration, connection/read timeouts, enabled-only LdapContextSource, and staging/production LDAPS guard added; no secrets, credential verification, or LDAP AuthenticationProvider introduced; `mvn -q test` passed in PR CI run 153
~~~~~~~~

<a id="hapi-025"></a>
## HAPI-025 — add ldap credential verification adapter

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-013

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Implement AD/LDAP bind/search credential verification

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedDirectoryIdentity.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LdapCredentialVerificationPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapCredentialVerificationAdapter.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LdapCredentialVerificationPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapCredentialVerificationAdapter.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedDirectoryIdentity.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LdapCredentialVerificationPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapCredentialVerificationAdapter.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-013 — LDAP credential verification adapter

Commit:

```text
feat(authentication): add ldap credential verification adapter
```

Implement the approved AD bind/search pattern.

Return a provider-neutral verified directory identity result; do not assign Hidra permissions in the LDAP adapter.

Status:

```text
Completed — LdapCredentialVerificationPort isolates the application layer from Spring LDAP and returns VerifiedDirectoryIdentity only after the configured LDAP user search resolves exactly one entry and LdapTemplate bind authentication succeeds. The supplied principal is escaped before filter substitution. The verified result carries a stable external subject plus normalized username/display/email/DN attributes, with AD objectGUID preferred and entryUUID/userPrincipalName/DN fallbacks. Submitted credentials are never returned, persisted, or logged. The adapter assigns no Hidra role/permission meaning and does not implement the AUTH-014 AuthenticationProvider.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 160 completed repository and acceptance `mvn -q test` checks successfully for AUTH-013 implementation commit 744f715fe916f19f9d7d43251c675dadb07db83b.
```

---

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

- `64ceacdb2e41df42e345d35a0929fda43a09067e` — exact registered-message commit; surviving paths matched to current tree.
- `8e1ed4ddcad31666c08cef9a4861e20acad3ad70` — exact registered-message commit; surviving paths matched to current tree.
- `514617e5dc3ae2d5985e8da6d581b570924b3bea` — exact registered-message commit; surviving paths matched to current tree.
- `03a8f5c245d256e06ec9752f7bdda5c8e75d6c57` — exact registered-message commit; surviving paths matched to current tree.
- `2fda1cfa2e58efdfcc36eafce7482c0f86cedce9` — exact registered-message commit; surviving paths matched to current tree.
- `0e2a7c8814948453e0b1f4a0b27bb17abe0ef1c9` — exact registered-message commit; surviving paths matched to current tree.
- `744f715fe916f19f9d7d43251c675dadb07db83b` — exact registered-message commit; surviving paths matched to current tree.
- `472d4ca58d87af62c199ab6e984c57e1afe93a6b` — exact registered-message commit; surviving paths matched to current tree.
- `c5be80a60633b1823f0e5e7e1f89ac728ed1c683` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1411](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1411)
- [docs/roadmap/authentication.md:1824](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1824)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1411 | AUTH-013 | feat(authentication): add ldap credential verification adapter | Implement AD/LDAP bind/search credential verification | Completed — Identity application port plus provider-neutral VerifiedDirectoryIdentity contract and Spring LDAP adapter added; principal values are LDAP-filter escaped, search must resolve exactly one identity, credentials are verified by LDAP bind, and no Hidra permissions or AUTH-014 provider behavior is introduced; `mvn -q test` passed in PR CI run 160
~~~~~~~~

<a id="hapi-026"></a>
## HAPI-026 — add ldap authentication provider

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-014

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Route LDAP token through directory verification, ExternalIdentity mapping, Hidra account state, and HidraPrincipal

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LdapCredentialVerificationPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProvider.java`
- `src/main/java/dz/sh/hidra/platform/security/LdapAuthenticationToken.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LdapCredentialVerificationPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProvider.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LdapCredentialVerificationPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProvider.java`
- `src/main/java/dz/sh/hidra/platform/security/LdapAuthenticationToken.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-014 — LDAP AuthenticationProvider

Commit:

```text
feat(authentication): add ldap authentication provider
```

Flow:

```text
LdapAuthenticationToken
 -> LDAP credential adapter
 -> stable external identity
 -> ExternalIdentity
 -> Hidra User
 -> Hidra account state
 -> existing Hidra authorization
 -> HidraPrincipal
```

No LOCAL fallback and no AD-group authorization shortcut.

Status:

```text
Completed — LdapAuthenticationProvider is enabled only with LDAP infrastructure, supports only LdapAuthenticationToken, verifies submitted directory credentials through LdapCredentialVerificationPort, requires exactly one active LDAP or ACTIVE_DIRECTORY IdentityProvider, resolves the verified stable subject to a LINKED ExternalIdentity and Hidra User, enforces locked/non-ACTIVE Hidra account state, resolves existing Hidra-owned effective permissions, and returns HidraPrincipal. Directory groups are not promoted to Hidra authorization, no LOCAL fallback exists, and no token/session/login endpoint behavior from later tasks was added.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 167 completed repository and acceptance `mvn -q test` checks successfully for AUTH-014 implementation commit fcb0a011536ef38b316fa8dd69343db03493f9ba.
```

---

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

- `5dca08e627e75ee5a688b0762f2d024b6ef413f8` — exact registered-message commit; surviving paths matched to current tree.
- `4cf1cb60b25a82f68668acc8ec088dd361890687` — exact registered-message commit; surviving paths matched to current tree.
- `ee4f480020ab04dee330b8ea990f0a072e78fcca` — exact registered-message commit; surviving paths matched to current tree.
- `c74a331a16e9ad4f4a7eadd1330a0dab6ca7ad4b` — exact registered-message commit; surviving paths matched to current tree.
- `e25aecf4cd689f4a8f23a576e3f99536204c0c46` — exact registered-message commit; surviving paths matched to current tree.
- `cb795cf00cec1814b7cf7f0214edb9d931e5c2d1` — exact registered-message commit; surviving paths matched to current tree.
- `fcb0a011536ef38b316fa8dd69343db03493f9ba` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1412](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1412)
- [docs/roadmap/authentication.md:1856](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1856)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1412 | AUTH-014 | feat(authentication): add ldap authentication provider | Route LDAP token through directory verification, ExternalIdentity mapping, Hidra account state, and HidraPrincipal | Completed — LdapAuthenticationProvider handles only LdapAuthenticationToken, delegates credential verification to AUTH-013, resolves one active LDAP/AD IdentityProvider and LINKED ExternalIdentity, enforces Hidra user state and Hidra-owned effective permissions, and returns HidraPrincipal without LOCAL fallback or AD-group authorization shortcuts; `mvn -q test` passed in PR CI run 167
~~~~~~~~

<a id="hapi-027"></a>
## HAPI-027 — add unified hidra access token issuer

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-015

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add JwtEncoder and issue one standardized Hidra JWT for all providers

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/IssuedAccessToken.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraJwtDecoderConfiguration.java`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraJwtEncoderConfiguration.java`
- `src/main/resources/application.properties`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/IssuedAccessToken.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraJwtDecoderConfiguration.java`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraJwtEncoderConfiguration.java`
- `src/main/resources/application.properties`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-015 — Unified Hidra access-token issuer

Commit:

```text
feat(authentication): add unified hidra access token issuer
```

AUTH-002 confirmed that decoder/resource-server support exists and `JwtEncoder`/Hidra token issuance is missing.

Requirements:

```text
one token schema for LOCAL/LDAP/OIDC
stable Hidra user subject
issuer/audience/expiry/JTI
externalized signing material
compatibility with existing resource server
no authorization bypass through provider claims
```

Status:

```text
Completed — HidraAccessTokenIssuer accepts the normalized HidraPrincipal shared by LOCAL, LDAP/Active Directory, and OIDC and produces one Bearer JWT schema. The standard subject is the stable Hidra user ID; issuer, audience, expiry, issued-at, and JTI are present; configured roles and scope claims are populated only from HidraPrincipal roles/permissions; authentication type and provider linkage are metadata only. HidraJwtEncoderConfiguration signs HS256 tokens with the existing externalized HIDRA_JWT_HMAC_SECRET and enforces a minimum 32-byte secret. The HMAC resource-server decoder validates the dedicated Hidra token issuer while external JWK/issuer OIDC validation remains unchanged. No external IdP roles/groups are promoted, and no session/login endpoint behavior from AUTH-016 or later was implemented.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 174 completed repository and acceptance `mvn -q test` checks successfully for AUTH-015 implementation commit 5a28df47c5107a5c93d1c35c127429e5427a4cc4.
```

---

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

- `cdb26e74278d4a4052de8fcbc024a12f276c8afd` — exact registered-message commit; surviving paths matched to current tree.
- `db821aaf92fca4e7614f9bf842f21eab9c6a469f` — exact registered-message commit; surviving paths matched to current tree.
- `6d6f2fdd25aca4904e4e82c8e5152de37fb3f9c2` — exact registered-message commit; surviving paths matched to current tree.
- `c945faf5fdf8b6bff48d745e874af6b1c4886121` — exact registered-message commit; surviving paths matched to current tree.
- `0c8470619e72a58978b881e762074fd7f41dd0a9` — exact registered-message commit; surviving paths matched to current tree.
- `3133b83f486e3561c986832f7acbac1a6a0cbbe3` — exact registered-message commit; surviving paths matched to current tree.
- `5a28df47c5107a5c93d1c35c127429e5427a4cc4` — exact registered-message commit; surviving paths matched to current tree.
- `37ae052420a3f17db3ada9e7c99350c6b79e97d3` — exact registered-message commit; surviving paths matched to current tree.
- `389307e1176435dc66cc7f0b56a3dc43f1567b51` — exact registered-message commit; surviving paths matched to current tree.
- `702140bcacae909e1055d9e9397344c73eff92d0` — exact registered-message commit; surviving paths matched to current tree.
- `077a3bdd4a45507efea8a466ab325e5e4bcf9beb` — exact registered-message commit; surviving paths matched to current tree.
- `1c1bcb16858926641e6d4a6bb949eb2fb024ece5` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1413](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1413)
- [docs/roadmap/authentication.md:1899](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1899)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1413 | AUTH-015 | feat(authentication): add unified hidra access token issuer | Add JwtEncoder and issue one standardized Hidra JWT for all providers | Completed — provider-neutral HidraAccessTokenIssuer now emits one JWT schema from HidraPrincipal with stable Hidra subject, issuer/audience/expiry/JTI, Hidra-owned roles/permissions, externalized HS256 signing material, and HMAC resource-server issuer compatibility; `mvn -q test` passed in PR CI run 174
~~~~~~~~

<a id="hapi-028"></a>
## HAPI-028 — complete authentication session lifecycle

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-016

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Reuse LoginSession and AuthenticationEvent for all provider paths

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAuthenticationOutcomeApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAuthenticationOutcomeApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAuthenticationOutcomeApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-016 — Authentication session lifecycle

Commit:

```text
feat(identity): complete authentication session lifecycle
```

Reuse `LoginSession` and `AuthenticationEvent` across all provider paths.

Do not store raw JWTs.

Status:

```text
Completed — AuthenticationSessionLifecycleApplicationService provides provider-neutral logical session start, touch/automatic expiry, explicit expiry, revocation, and logout using the existing LoginSession repository contract. Session persistence stores only lifecycle/client/correlation/provider metadata and never accepts or stores raw JWT values. LDAP/AD/OIDC normalized session starts record LOGIN_SUCCESS through the existing AuthenticationEvent repository; LOCAL success remains recorded by LocalAuthenticationOutcomeApplicationService from AUTH-011 to avoid duplicate LOGIN_SUCCESS events. Logout records a LOGOUT event with provider-derived AuthenticationProtocol. No dynamic login endpoint, token response orchestration, provider routing, or AUTH-017 behavior was introduced.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 181 completed repository and acceptance `mvn -q test` checks successfully for AUTH-016 implementation commit fb39522e817ddfd9ea2aa94ba1c525ed7f5f7ecc.
```

---

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

- `c930fe0dc9d67514ba01a6ff211a35b40e9272e3` — exact registered-message commit; surviving paths matched to current tree.
- `848ecc9b4d49c4c7bf0235af45c0a7fe06fbd876` — exact registered-message commit; surviving paths matched to current tree.
- `9be46d7ccb41b27041990becc7eed19a3c843fe6` — exact registered-message commit; surviving paths matched to current tree.
- `93e79e151606c23ea2280a0b397b257e400e48d8` — exact registered-message commit; surviving paths matched to current tree.
- `015a436111f79681e458f2365503c521aabde951` — exact registered-message commit; surviving paths matched to current tree.
- `d700394cef6fd158c647e20f10335815c8a82a66` — exact registered-message commit; surviving paths matched to current tree.
- `fb39522e817ddfd9ea2aa94ba1c525ed7f5f7ecc` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1414](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1414)
- [docs/roadmap/authentication.md:1940](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1940)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1414 | AUTH-016 | feat(identity): complete authentication session lifecycle | Reuse LoginSession and AuthenticationEvent for all provider paths | Completed — provider-neutral AuthenticationSessionLifecycleApplicationService now starts, touches, expires, revokes, and logs out LoginSession records without storing raw JWTs; non-LOCAL successful sessions record LOGIN_SUCCESS, LOCAL success remains with AUTH-011 to avoid duplicates, and logout records provider-correct AuthenticationEvent metadata; `mvn -q test` passed in PR CI run 181
~~~~~~~~

<a id="hapi-029"></a>
## HAPI-029 — wire dynamic login endpoint

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-017

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Introduce and wire the direct LOCAL/LDAP provider-selection login boundary to router, AuthenticationManager, session, and token issuer

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/AuthenticationLoginRequest.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/response/AuthenticationLoginResponse.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/DirectAuthenticationCommand.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AccessTokenIssuerPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/DirectAuthenticationPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringDirectAuthenticationAdapter.java`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraSecurityConfiguration.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AccessTokenIssuerPort.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/out/DirectAuthenticationPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringDirectAuthenticationAdapter.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/AuthenticationLoginRequest.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/response/AuthenticationLoginResponse.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/DirectAuthenticationCommand.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AccessTokenIssuerPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/DirectAuthenticationPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringDirectAuthenticationAdapter.java`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraSecurityConfiguration.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-017 — Dynamic login endpoint

Commit:

```text
feat(authentication): wire dynamic login endpoint
```

AUTH-002 confirmed no current direct LOCAL/LDAP login DTO or endpoint exists. Introduce the first canonical direct-login boundary here and coordinate its provider-selection contract with HidraWEB; do not claim compatibility with a nonexistent endpoint.

The controller/application boundary may:

```text
validate request
convert selected provider type into Authentication request
authenticate through AuthenticationManager
create/update LoginSession
issue Hidra JWT
return safe principal/token response
```

It must not:

```text
query JPA repositories directly
perform LDAP binds directly
compare password hashes directly
assign roles
implement provider fallback
```

Status:

```text
Completed — the canonical direct-login boundary is POST /api/v1/identity/authentication/login. AuthenticationLoginRequest requires explicit ProviderType, principal, and credentials; validation rejects missing values and the existing router rejects OIDC/unsupported direct providers without fallback. The API depends only on AuthenticateDirectUserUseCase. DirectAuthenticationApplicationService orchestrates DirectAuthenticationPort, AccessTokenIssuerPort, and the existing AuthenticationSessionLifecycleApplicationService; SpringDirectAuthenticationAdapter owns the Spring Security router/AuthenticationManager bridge, while HidraAccessTokenIssuer implements the issuance port. Successful LOCAL/LDAP/AD login returns session id, Hidra bearer token lifecycle metadata, and normalized HidraPrincipal identity/roles/permissions. Submitted credentials are never returned or persisted. The endpoint is explicitly permitAll so unauthenticated callers can authenticate. OIDC browser PKCE completion remains untouched for AUTH-018.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — initial PR CI run 188 exposed and blocked an API-to-infrastructure architecture violation. The implementation was corrected with application ports/adapters; replacement PR CI run 197 completed repository and acceptance `mvn -q test` checks successfully for AUTH-017 implementation commit 6aab35936511036ffa445954c36d4b661eae107c.
```

---

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

- `8f38ad8ad34117dd5391c8f2c28689439087caf5` — exact registered-message commit; surviving paths matched to current tree.
- `6f4eef423ba2b76dcd8b29dd9caa00bd4cfa4f9b` — exact registered-message commit; surviving paths matched to current tree.
- `7b2b85fe5a785945f50515373b345a55debd2d3a` — exact registered-message commit; surviving paths matched to current tree.
- `33493dc1870a4d8b94513dfa23e3fb0242496601` — exact registered-message commit; surviving paths matched to current tree.
- `9a715a6a6b7b0fba79b9831db805a01d6ab59999` — exact registered-message commit; surviving paths matched to current tree.
- `5d884c065297564c45a00dc15be2847d0469f259` — exact registered-message commit; surviving paths matched to current tree.
- `6aab35936511036ffa445954c36d4b661eae107c` — exact registered-message commit; surviving paths matched to current tree.
- `bdc8c9f0e5f2aefe3210e08cbc34fc61795b06dc` — exact registered-message commit; surviving paths matched to current tree.
- `84c0393aada3bd27f94ed6b4cbb7de84af4394f7` — exact registered-message commit; surviving paths matched to current tree.
- `499cbbd745833e15a882b89bcdb0147982a4a17b` — exact registered-message commit; surviving paths matched to current tree.
- `5e4bf8ebe0ba8e2f2ec5ee3a24d857edc1dfb7f5` — exact registered-message commit; surviving paths matched to current tree.
- `1b62011c7e07b7b391f253dde23fb1522e596caf` — exact registered-message commit; surviving paths matched to current tree.
- `dfe67eb73c42357b0b40b688190d7f965130062d` — exact registered-message commit; surviving paths matched to current tree.
- `7b545a66e4303137e661a7b69219a2a3d73e789d` — exact registered-message commit; surviving paths matched to current tree.
- `23dedfa9b97369ce478e74c2a8eb00351833922f` — exact registered-message commit; surviving paths matched to current tree.
- `4922f6754bc503dff9d72605e16f8396b41a2159` — exact registered-message commit; surviving paths matched to current tree.
- `c5cea3b66d9dcc7e53dee8f5fa599a2d1b0f6adb` — exact registered-message commit; surviving paths matched to current tree.
- `8aa2d628f763ee22165631a69e66340607c8d6ee` — exact registered-message commit; surviving paths matched to current tree.
- `7dfd446533164d6accec94791e1dc2cb1e676371` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1415](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1415)
- [docs/roadmap/authentication.md:1972](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1972)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1415 | AUTH-017 | feat(authentication): wire dynamic login endpoint | Introduce and wire the direct LOCAL/LDAP provider-selection login boundary to router, AuthenticationManager, session, and token issuer | Completed — POST /api/v1/identity/authentication/login now accepts explicit LOCAL/LDAP/ACTIVE_DIRECTORY provider selection, routes through application ports to the existing request router and fail-closed AuthenticationManager, creates the AUTH-016 logical session, issues the AUTH-015 Hidra bearer JWT, and returns safe normalized principal/session/token metadata; API-to-infrastructure coupling found by CI run 188 was corrected with hexagonal ports/adapters; `mvn -q test` passed in PR CI run 197
~~~~~~~~

<a id="hapi-030"></a>
## HAPI-030 — converge oidc completion on hidra token

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-018

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Ensure OIDC completion produces same Hidra principal/session/JWT result as LOCAL/LDAP while preserving current PKCE behavior

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/response/AuthenticationLoginResponse.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CompleteAuthenticatedPrincipalUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverter.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CompleteAuthenticatedPrincipalUseCase.java`.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverter.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/response/AuthenticationLoginResponse.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CompleteAuthenticatedPrincipalUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverter.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-018 — OIDC completion to Hidra token

Commit:

```text
feat(authentication): converge oidc completion on hidra token
```

After external OIDC validation, route the mapped HidraPrincipal through the same session/token issuance path as LOCAL and LDAP while preserving the current authorization-code + PKCE browser security semantics.

Status:

```text
Completed — AuthenticationCompletionApplicationService is now the provider-neutral post-authentication seam for HidraPrincipal -> unified access token -> logical LoginSession. DirectAuthenticationApplicationService delegates to this shared seam after LOCAL/LDAP/AD credential authentication. POST /api/v1/identity/authentication/oidc/complete is intentionally protected rather than permitAll: the external OIDC bearer JWT must first be validated by the existing resource-server decoder and normalized by IdentityOidcJwtAuthenticationConverter. Only an OIDC HidraPrincipal can complete the endpoint. The completion response reuses AuthenticationLoginResponse and therefore returns the same session/token/principal shape as direct login. Browser authorization-code + PKCE remains owned by HidraWEB/external IdP; HidraAPI still does not implement an OAuth2 client callback or authorization-code/token exchange. AUTH-019 protected-bearer standardization was not implemented.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 204 completed repository and acceptance `mvn -q test` checks successfully for AUTH-018 implementation commit c87808ce41d77e89330776681d4583740ad035b6.
```

---

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

- `9556025d4ec11a4f706ef6180dab6f8ba0db69ef` — exact registered-message commit; surviving paths matched to current tree.
- `2c42fe1be81d7f1677f5012fe87c3c7f6550e188` — exact registered-message commit; surviving paths matched to current tree.
- `73fbd28c9f9b1561f04fec7632f5e131caaacb0d` — exact registered-message commit; surviving paths matched to current tree.
- `36568fd15ddfe56d39db82f991e214bf1258d8a7` — exact registered-message commit; surviving paths matched to current tree.
- `3dbaa2d4b8b9f15266ed55e7e83252d56bc0f98d` — exact registered-message commit; surviving paths matched to current tree.
- `e687900afb2e4126619e1fe4534431152a352e0d` — exact registered-message commit; surviving paths matched to current tree.
- `c87808ce41d77e89330776681d4583740ad035b6` — exact registered-message commit; surviving paths matched to current tree.
- `f2c49b93f2d1500ba9e9d2045d8ca39cabfaa24b` — exact registered-message commit; surviving paths matched to current tree.
- `0bae904ecc4c224dfe4c0b6e686bc15f0c4486dd` — exact registered-message commit; surviving paths matched to current tree.
- `feb98fc6962088121fd25b127a1f7d733c32e56b` — exact registered-message commit; surviving paths matched to current tree.
- `644761f015d7aacd08ac33dfdd05ce0e05e5b13b` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1416](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1416)
- [docs/roadmap/authentication.md:2023](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L2023)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1416 | AUTH-018 | feat(authentication): converge oidc completion on hidra token | Ensure OIDC completion produces same Hidra principal/session/JWT result as LOCAL/LDAP while preserving current PKCE behavior | Completed — externally validated OIDC HidraPrincipal now completes through the same provider-neutral session/token application path used by direct authentication; secured POST /api/v1/identity/authentication/oidc/complete issues the AUTH-015 Hidra bearer JWT and AUTH-016 LoginSession while preserving browser authorization-code + PKCE semantics and adding no OAuth callback/token exchange; `mvn -q test` passed in PR CI run 204
~~~~~~~~

<a id="hapi-032"></a>
## HAPI-032 — retire ordinary in-memory local authentication

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-020

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Remove InMemoryUserDetailsManager as ordinary LOCAL login only after DB path is proven

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/resources/application-dev.properties`
- `src/main/resources/application-test.properties`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/resources/application-dev.properties`
- `src/main/resources/application-test.properties`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-020 — Retire ordinary in-memory LOCAL authentication

Commit:

```text
refactor(security): retire ordinary in-memory local authentication
```

Precondition: persistent LOCAL provider, unified login endpoint, and JWT path are proven.

Remove `InMemoryUserDetailsManager` as an ordinary user-login authority. Do not remove emergency/bootstrap capability until AUTH-021 exists.

Status:

```text
Completed — ordinary development and test startup no longer selects the Basic/InMemoryUserDetailsManager path. Both profiles now default to the standardized Hidra JWT resource-server flow, matching the proven protected-API contract from AUTH-019. The existing Basic/in-memory bootstrap mechanism was intentionally retained only when HIDRA_SECURITY_AUTHENTICATION_MODE=basic is explicitly selected, preserving emergency/bootstrap compatibility until AUTH-021 replaces it with persistent administrator provisioning. No persistent bootstrap user/credential provisioning or AUTH-021 behavior was introduced.
```

Validation:

```bash
mvn -q clean verify
```

Result:

```text
PASS — PR CI run 220 completed repository and acceptance clean verification successfully for AUTH-020 implementation commit cce77bfd8e7dc518258d7606fa6617b68c77be06.
```

---

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

- `5c405b6a35566ec57a99200bc157f82e02a59796` — exact registered-message commit; surviving paths matched to current tree.
- `a7cc8d0ac5337586d21e080ef9035fac6b19c92a` — exact registered-message commit; surviving paths matched to current tree.
- `ca2a3b2de959a802af68ddbcec373d7759a3e80b` — exact registered-message commit; surviving paths matched to current tree.
- `0eae00df0982132b0a15a80d31f4e31f7d9588a0` — exact registered-message commit; surviving paths matched to current tree.
- `bddc6bdb2011e7ce1c12326e3ff37e5c63c65728` — exact registered-message commit; surviving paths matched to current tree.
- `b9b765942069a3bac83f81727e944e397df8e666` — exact registered-message commit; surviving paths matched to current tree.
- `cce77bfd8e7dc518258d7606fa6617b68c77be06` — exact registered-message commit; surviving paths matched to current tree.
- `8a5c4e22081ab74734c2b9d6d7280198a5178c94` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1418](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1418)
- [docs/roadmap/authentication.md:2085](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L2085)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1418 | AUTH-020 | refactor(security): retire ordinary in-memory local authentication | Remove InMemoryUserDetailsManager as ordinary LOCAL login only after DB path is proven | Completed — development and test now default to the unified Hidra JWT path instead of server-wide Basic authentication; InMemoryUserDetailsManager remains only behind explicit `HIDRA_SECURITY_AUTHENTICATION_MODE=basic` selection as temporary emergency/bootstrap compatibility until AUTH-021; `mvn -q clean verify` passed in PR CI run 220
~~~~~~~~

<a id="hapi-033"></a>
## HAPI-033 — add safe local administrator bootstrap

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-021

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Provide controlled persistent LOCAL administrator provisioning without permanent in-memory fallback

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/bootstrap/LocalAdministratorBootstrapRunner.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/BootstrapLocalAdministratorUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LocalPasswordHashPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/RoleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRoleGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAdministratorBootstrapApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaRoleRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRoleGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/RoleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserRoleGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringLocalPasswordHashAdapter.java`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraSecurityConfiguration.java`
- `src/main/resources/application-dev.properties`
- `src/main/resources/application-production.properties`
- `src/main/resources/application-staging.properties`
- `src/main/resources/application-test.properties`
- `src/main/resources/application.properties`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/in/BootstrapLocalAdministratorUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LocalPasswordHashPort.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/out/RoleRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRoleGrantRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAdministratorBootstrapApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaRoleRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRoleGrantRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/RoleJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/bootstrap/LocalAdministratorBootstrapRunner.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/BootstrapLocalAdministratorUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LocalPasswordHashPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/RoleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRoleGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAdministratorBootstrapApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaRoleRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRoleGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/RoleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserRoleGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringLocalPasswordHashAdapter.java`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraSecurityConfiguration.java`
- `src/main/resources/application-dev.properties`
- `src/main/resources/application-production.properties`
- `src/main/resources/application-staging.properties`
- `src/main/resources/application-test.properties`
- `src/main/resources/application.properties`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-021 — Safe LOCAL administrator bootstrap

Commit:

```text
feat(authentication): add safe local administrator bootstrap
```

Requirements:

```text
one-time/idempotent provisioning
persistent normal Identity User + LOCAL credential
no production default password
secret provided externally
cannot silently overwrite existing credential
fully auditable
```

Status:

```text
Completed — LocalAdministratorBootstrapApplicationService provisions a normal ACTIVE HUMAN Identity User, an ACTIVE BCrypt-hashed LocalCredential, and a global ACTIVE HIDRA_ADMIN UserRoleGrant inside a transaction. The HIDRA_ADMIN role is reused when active or created when absent. A rerun is a no-op only when the same username is already an ACTIVE user with an ACTIVE LOCAL credential and active administrator grant; any other pre-existing/incomplete state is rejected and never repaired, reset, or escalated silently. LocalAdministratorBootstrapRunner is disabled by default, requires an externally supplied nonblank password when enabled, and records successful creation through the existing append-only Audit use case in the same startup transaction without recording plaintext credentials. The temporary Basic/InMemoryUserDetailsManager authority retained through AUTH-020 is removed; JWT/direct LOCAL authentication remains the normal runtime path.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — PR CI run 229 completed repository and acceptance mvn -q test successfully for AUTH-021 corrected implementation commit 91e49d0ac13227d1075278616b1273c21c2d373e. Earlier run 227 correctly blocked final transactional classes that could not be proxied; both transactional classes were made proxy-compatible before validation passed.
```

---

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

- `5e7ec4dbb90742682c453d4c2c0b22e66c62e505` — exact registered-message commit; surviving paths matched to current tree.
- `f3962f6c7c471df8028c9cdf9680e468fba725cd` — exact registered-message commit; surviving paths matched to current tree.
- `502d8704b404372401b7fcdb1b2c65f2f172cb39` — exact registered-message commit; surviving paths matched to current tree.
- `f687a34d1dc631ec19b7ab4544761de6ad142378` — exact registered-message commit; surviving paths matched to current tree.
- `110748859b91f5a876d134742db4f15d8ee76097` — exact registered-message commit; surviving paths matched to current tree.
- `124308198d4d36763cfb6d8e92720352ebf837d1` — exact registered-message commit; surviving paths matched to current tree.
- `45efbcc1df330677b11e2763cc0a665c5c23375a` — exact registered-message commit; surviving paths matched to current tree.
- `f40233b6ae3632f82f87e52f1c4fd9c5246feaee` — exact registered-message commit; surviving paths matched to current tree.
- `91e49d0ac13227d1075278616b1273c21c2d373e` — exact registered-message commit; surviving paths matched to current tree.
- `5e5694df428d9cdbf10fbbb5db0447a9a510fb8f` — exact registered-message commit; surviving paths matched to current tree.
- `f6db4552ef7524d735e94867a0ed8f2b3bda55e2` — exact registered-message commit; surviving paths matched to current tree.
- `bac7536c373551e353b7b5e09b0fd4def01ceafd` — exact registered-message commit; surviving paths matched to current tree.
- `e27efb0c153237bb55446ff4ba4a557914f1ac49` — exact registered-message commit; surviving paths matched to current tree.
- `7e5c477cafb020a135dfbeb872fc53bea6ccf9ae` — exact registered-message commit; surviving paths matched to current tree.
- `4c88bbb19a1027b79e0523ef45df2b0e3532e562` — exact registered-message commit; surviving paths matched to current tree.
- `bbe03c3df90c0d7d91af3c328bf87fe82efd10d4` — exact registered-message commit; surviving paths matched to current tree.
- `c465ffc509df4df7213362d680f8f01401e5ad3e` — exact registered-message commit; surviving paths matched to current tree.
- `2abe2d1743f50f60ab372b242b3455f63747ce9c` — exact registered-message commit; surviving paths matched to current tree.
- `4ef475bd588223852027b250331ce471c569e977` — exact registered-message commit; surviving paths matched to current tree.
- `0521b7d8a698a628fc472f0b9ae0f9d2ac8defc5` — exact registered-message commit; surviving paths matched to current tree.
- `91502edb35cb315915981e61daa942679a22287e` — exact registered-message commit; surviving paths matched to current tree.
- `7e85fcc72cacc006f99fb2bc17141f5dd8dbd997` — exact registered-message commit; surviving paths matched to current tree.
- `13bccffee07e674810c1fa57f38385fbd2c21b34` — exact registered-message commit; surviving paths matched to current tree.
- `28b0e4a203ab2b9eb8095a12bdc8d579fe72f26f` — exact registered-message commit; surviving paths matched to current tree.
- `a4c99d49cda53017cafb29d32342ac871e9157f9` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1419](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1419)
- [docs/roadmap/authentication.md:2117](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L2117)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1419 | AUTH-021 | feat(authentication): add safe local administrator bootstrap | Provide controlled persistent LOCAL administrator provisioning without permanent in-memory fallback | Completed — explicitly enabled bootstrap now provisions a persistent ACTIVE HUMAN User, BCrypt-backed ACTIVE LocalCredential, active HIDRA_ADMIN role/global grant, and append-only audit event; fully provisioned reruns are idempotent while pre-existing or incomplete identity state fails closed without credential overwrite; bootstrap password has no repository default and the temporary Basic/InMemoryUserDetailsManager authority is removed; `mvn -q test` passed in PR CI run 229
~~~~~~~~

<a id="hapi-034"></a>
## HAPI-034 — cover dynamic provider routing

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-022

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Verify provider-selection dispatch, supports contracts, unsupported type, and no fallback

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/platform/security/LdapAuthenticationToken.java`
- `src/main/java/dz/sh/hidra/platform/security/LocalAuthenticationToken.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityAuthenticationRequestRouterTest.java`
- `src/test/java/dz/sh/hidra/platform/configuration/HidraAuthenticationManagerConfigurationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityAuthenticationRequestRouterTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/platform/security/LdapAuthenticationToken.java`
- `src/main/java/dz/sh/hidra/platform/security/LocalAuthenticationToken.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityAuthenticationRequestRouterTest.java`
- `src/test/java/dz/sh/hidra/platform/configuration/HidraAuthenticationManagerConfigurationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-022 — Dynamic provider routing proof

Commit:

```text
test(authentication): cover dynamic provider routing
```

Status:

```text
Completed — IdentityAuthenticationRequestRouterTest proves deterministic explicit provider selection: LOCAL produces only LocalAuthenticationToken; LDAP and ACTIVE_DIRECTORY produce only LdapAuthenticationToken; OIDC is rejected from direct-credential routing; all other ProviderType values fail closed; and a missing provider selection is rejected. HidraAuthenticationManagerConfigurationTest proves supports-contract isolation, dispatches LOCAL/LDAP requests only to matching providers, rejects any provider claiming both Hidra request token types, rejects unsupported request types, and verifies a failed selected LOCAL provider never invokes the LDAP provider. No production behavior or AUTH-023+ provider integration coverage was added.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — PR CI run 239 completed repository mvn -q test successfully for AUTH-022 corrected implementation commit 4dba3e6cb138c388dbf9de248069052824e264a9. The same run also passed repository full verification, acceptance tests, acceptance clean verify, and deterministic OpenAPI publication. Earlier run 238 exposed only an over-specific assertion on Spring ProviderManager error text; the proof was corrected without changing production behavior.
```

---

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityAuthenticationRequestRouterTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/platform/configuration/HidraAuthenticationManagerConfigurationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `16e436a7c3bd8663725b72a464e65459d53d14ec` — exact registered-message commit; surviving paths matched to current tree.
- `9457a73b1b887604e4628cec3f1ab4d666650d7d` — exact registered-message commit; surviving paths matched to current tree.
- `d43a9b3b6833b0e39c060c397552c802ce3219ec` — exact registered-message commit; surviving paths matched to current tree.
- `b703b5d4788305fe772d0990b02339b5b92dedf8` — exact registered-message commit; surviving paths matched to current tree.
- `4dba3e6cb138c388dbf9de248069052824e264a9` — exact registered-message commit; surviving paths matched to current tree.
- `115c31c68d1b8b8f56dbd4211a69f312f655030c` — exact registered-message commit; surviving paths matched to current tree.
- `dd48d95bc6e51f931c98f99b79336f94e94e42ea` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1420](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1420)
- [docs/roadmap/authentication.md:2156](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L2156)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1420 | AUTH-022 | test(authentication): cover dynamic provider routing | Verify provider-selection dispatch, supports contracts, unsupported type, and no fallback | Completed — focused router and ProviderManager tests prove LOCAL -> LocalAuthenticationToken, LDAP/ACTIVE_DIRECTORY -> LdapAuthenticationToken, OIDC/unsupported selections fail closed, providers may support only one Hidra request token type, selected-provider failure never invokes another provider, and unsupported request types are rejected; `mvn -q test` passed in PR CI run 239
~~~~~~~~

<a id="hapi-035"></a>
## HAPI-035 — cover local authentication

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-023

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Unit/integration/API coverage for persisted LOCAL authentication

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/DirectAuthenticationCommand.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLocalApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProviderTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java`, `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationIntegrationTest.java`, `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProviderTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/DirectAuthenticationCommand.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLocalApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProviderTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-023 — Persisted LOCAL authentication proof

Commit:

```text
test(authentication): cover local authentication
```

Status:

```text
Completed — LocalAuthenticationProviderTest exercises successful persisted BCrypt-backed LOCAL authentication plus wrong-password, locked-account, inactive-credential, unavailable-provider, supports-contract, sanitized failure-outcome, permission-resolution, and HidraPrincipal normalization behavior. LocalAuthenticationIntegrationTest uses PostgreSQL Testcontainers and the real Spring Identity infrastructure to persist a normal active user and LocalCredential, seed the active LOCAL provider in the migrated schema, authenticate through the real LocalAuthenticationProvider, and prove the persisted BCrypt hash and successful last-authenticated state update. IdentityAuthenticationControllerLocalApiTest proves the LOCAL API boundary forwards explicit provider selection, credentials, client metadata, and correlation ID into DirectAuthenticationCommand and returns the normalized Hidra session/token/principal response. No production behavior or AUTH-024+ LDAP/OIDC/JWT proof scope was added.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — PR CI run 248 completed repository mvn -q test successfully for AUTH-023 corrected implementation commit cbf1b00d6942e427c15842b4cab5f70436037dfe. The same run also passed repository full verification, acceptance tests, acceptance clean verify, and deterministic OpenAPI publication. Earlier runs 246 and 247 exposed only integration-fixture issues: the existing IdentityProvider String-to-jsonb JPA insert quirk and JdbcTemplate Instant type inference respectively; the test fixture was narrowed to direct migrated-table provider seeding with JDBC Timestamp values without changing production behavior.
```

---

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLocalApiTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationIntegrationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProviderTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.

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

- `4ffaa056db6a88ea6ae3b6c3cc643362f166bbf3` — exact registered-message commit; surviving paths matched to current tree.
- `12093516066f6dba40be645657f560f5cefd2a9b` — exact registered-message commit; surviving paths matched to current tree.
- `16f48222d5cc1bface64f58acc0439381d49b8cf` — exact registered-message commit; surviving paths matched to current tree.
- `85aa56a3329249ee7add6a30222e0b6b7ef19468` — exact registered-message commit; surviving paths matched to current tree.
- `cbf1b00d6942e427c15842b4cab5f70436037dfe` — exact registered-message commit; surviving paths matched to current tree.
- `079b4b410f07cd884f749ca024da7577184e9281` — exact registered-message commit; surviving paths matched to current tree.
- `1b71323015c2c80d74f8f1c3bf1801b4a19eae45` — exact registered-message commit; surviving paths matched to current tree.
- `d224a49c0218303d5c22513e0b54bd7d00b234ec` — exact registered-message commit; surviving paths matched to current tree.
- `6055b361390d6c0dcbb65f1da6beb98684e02b65` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1421](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1421)
- [docs/roadmap/authentication.md:2184](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L2184)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1421 | AUTH-023 | test(authentication): cover local authentication | Unit/integration/API coverage for persisted LOCAL authentication | Completed — provider unit tests, PostgreSQL Testcontainers integration, and the LOCAL login API boundary prove persisted BCrypt credential verification, account/credential state enforcement, sanitized outcomes, normalized HidraPrincipal/response contracts, and successful persisted login-state update; `mvn -q test` passed in PR CI run 248
~~~~~~~~

<a id="hapi-036"></a>
## HAPI-036 — cover ldap authentication

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-024

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

LDAP/AD adapter, mapping, outage, TLS, and end-to-end coverage

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/workflows/ci.yml`
- `docs/roadmap/authentication.md`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLdapApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProviderTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapCredentialVerificationAdapterTest.java`
- `src/test/java/dz/sh/hidra/platform/configuration/HidraLdapSecurityConfigurationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProviderTest.java`, `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapCredentialVerificationAdapterTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/workflows/ci.yml`
- `docs/roadmap/authentication.md`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLdapApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProviderTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapCredentialVerificationAdapterTest.java`
- `src/test/java/dz/sh/hidra/platform/configuration/HidraLdapSecurityConfigurationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-024 — LDAP/Active Directory authentication proof

Commit:

```text
test(authentication): cover ldap authentication
```

Status:

```text
Completed — LdapAuthenticationProviderTest proves successful LDAP and ACTIVE_DIRECTORY normalization through stable ExternalIdentity linkage to an active Hidra User, Hidra-owned effective permissions with no directory-group authority promotion, invalid credentials, missing mappings, inactive external identity, locked account, unavailable/ambiguous provider configuration, directory outage fail-closed behavior, and provider token support isolation. LdapCredentialVerificationAdapterTest proves submitted principals are LDAP-filter escaped, the configured search filter must contain the principal placeholder, AD objectGUID bytes normalize to a stable Base64 subject, directory identity attributes are normalized, and blank credentials fail before directory access. HidraLdapSecurityConfigurationTest proves production/staging require LDAPS and validates positive connection/read timeouts. IdentityAuthenticationControllerLdapApiTest proves the canonical direct-login API preserves explicit LDAP and ACTIVE_DIRECTORY provider selection, credentials, client metadata, correlation ID, and the normalized Hidra response contract. No production behavior or AUTH-025+ OIDC/JWT proof scope was added.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — PR CI run 257 completed repository mvn -q test successfully for AUTH-024 corrected implementation commit 60437b8660a7ff59f5fcd9513142fe5d118ef240. The same run also passed repository compile/full verification, acceptance compile/tests/clean verify, and deterministic OpenAPI publication. Earlier run 256 exposed only a test assertion that expected lower-case LDAP base-DN text although LdapContextSource preserves the configured DN casing; the assertion was corrected without changing production behavior.
```

---

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLdapApiTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProviderTest.java` — exact-head oracle suite 9 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapCredentialVerificationAdapterTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/platform/configuration/HidraLdapSecurityConfigurationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.

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

- `afd24cc1e4b35ca6c4477096ffbc97be849724d3` — exact registered-message commit; surviving paths matched to current tree.
- `b1294f5e83bb980cf11557e85ace083ec688999a` — exact registered-message commit; surviving paths matched to current tree.
- `77acdd3cfc8c395aa0366f24e2f5f62284f89f66` — exact registered-message commit; surviving paths matched to current tree.
- `82d6b7e2d409757a930faefd1f3709426dcf50b3` — exact registered-message commit; surviving paths matched to current tree.
- `656393f59fa238626720c83b9eadc94e07fcd1f1` — exact registered-message commit; surviving paths matched to current tree.
- `cd6fb12cc0d2766fba5837de14ba5ae0ba36438d` — exact registered-message commit; surviving paths matched to current tree.
- `5ec6c90d1ec240167162b75726a1a1e4808b28cd` — exact registered-message commit; surviving paths matched to current tree.
- `53af5446459d0cb619239bb7bc66cb80a8b39f96` — exact registered-message commit; surviving paths matched to current tree.
- `5106aca763e4e015e93b2c7ca719290eb3948f1b` — exact registered-message commit; surviving paths matched to current tree.
- `76d44758536cb5186cef030b4615c600f50c5f1d` — exact registered-message commit; surviving paths matched to current tree.
- `5f6edcc3a909da65c7a7e53b0268522815aafd50` — exact registered-message commit; surviving paths matched to current tree.
- `52b6ab6eb20206d433eb9f476250aa8c4bcffcc3` — exact registered-message commit; surviving paths matched to current tree.
- `60437b8660a7ff59f5fcd9513142fe5d118ef240` — exact registered-message commit; surviving paths matched to current tree.
- `6b276ebf9befe600982c6a799ffb49160639b335` — exact registered-message commit; surviving paths matched to current tree.
- `1a072cef9a32802b66ff527b97f06a3190e438bb` — exact registered-message commit; surviving paths matched to current tree.
- `988bbebd42f06303ec84342bb43b55659013a38e` — exact registered-message commit; surviving paths matched to current tree.
- `30cd0d3cb1c92a1118edea62e87c4514859a5658` — exact registered-message commit; surviving paths matched to current tree.
- `0b137570d50fa061d858fbda68f81eb4833bbabd` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1422](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1422)
- [docs/roadmap/authentication.md:2212](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L2212)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1422 | AUTH-024 | test(authentication): cover ldap authentication | LDAP/AD adapter, mapping, outage, TLS, and end-to-end coverage | Completed — provider, credential-adapter, security-configuration, and direct-login API tests prove LDAP/ACTIVE_DIRECTORY normalization, stable external identity linkage, Hidra account-state/permission ownership, invalid/unmapped/inactive/locked failure behavior, directory outage fail-closed behavior, LDAP filter escaping, AD objectGUID normalization, production/staging LDAPS enforcement, timeout validation, and explicit LDAP/AD API provider selection; `mvn -q test` passed in PR CI run 257
~~~~~~~~

<a id="hapi-040"></a>
## HAPI-040 — add ldap and provider deployment runbook

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-028

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Document non-secret provider/TLS/configuration inputs and diagnostics

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/AUTHENTICATION_DEPLOYMENT_RUNBOOK.md`
- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/AUTHENTICATION_DEPLOYMENT_RUNBOOK.md`
- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-028 — Deployment runbook

Commit:

```text
docs(authentication): add ldap and provider deployment runbook
```

Status:

```text
Completed — docs/AUTHENTICATION_DEPLOYMENT_RUNBOOK.md records the current non-secret deployment contract for persistent LOCAL bootstrap, LDAP/Active Directory enablement and search behavior, immutable directory identity selection, staging/production LDAPS enforcement, external JVM trust requirements, OIDC browser/external-token validation inputs, Hidra-issued JWT signing/validation inputs, IdentityProvider/ExternalIdentity prerequisites, provider-selection behavior, deployment checks, diagnostics, and explicit secret-handling rules. The runbook is grounded in live application properties and authentication configuration; it introduces no production behavior, provider provisioning shortcut, external group-to-role mapping, or AUTH-029+ guardrail implementation.
```

Validation:

```text
Documentation/source inspection plus repository CI.
```

Result:

```text
PASS — PR CI run 293 completed repository compile/tests/full verification, acceptance compile/tests/clean verify, deterministic OpenAPI publication, and artifact upload successfully for AUTH-028 implementation commit 2c477be58e0fcf776f86837c6408f686599d2604.
```

---

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

- `2eed117dd61834800906f4e8a20f9e81edeffccc` — exact registered-message commit; surviving paths matched to current tree.
- `6d21ba622ff818e3fe4443960987bbcba2b79265` — exact registered-message commit; surviving paths matched to current tree.
- `64aa156b6e0940b2e8180a1fd9d54bbdf5e14d4f` — exact registered-message commit; surviving paths matched to current tree.
- `01f132cf760f3d9651e767cad20da3278ef8ce07` — exact registered-message commit; surviving paths matched to current tree.
- `e6407f296494d96dca4acb0e902b54317302cd59` — exact registered-message commit; surviving paths matched to current tree.
- `91a9cdb8e816cefb9844e24222b2eba3b4fff411` — exact registered-message commit; surviving paths matched to current tree.
- `2c477be58e0fcf776f86837c6408f686599d2604` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1426](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1426)
- [docs/roadmap/authentication.md:2324](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L2324)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1426 | AUTH-028 | docs(authentication): add ldap and provider deployment runbook | Document non-secret provider/TLS/configuration inputs and diagnostics | Completed — `docs/AUTHENTICATION_DEPLOYMENT_RUNBOOK.md` documents LOCAL bootstrap inputs, LDAP/AD connection/search/LDAPS/trust requirements, OIDC validation/linkage, Hidra JWT settings, provider prerequisites, deployment checks, diagnostics, and secret-handling rules; repository CI run 293 passed
~~~~~~~~

<a id="hapi-041"></a>
## HAPI-041 — add authentication architecture and secret guardrails

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-029

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Enforce module boundaries, credential secrecy, and provider isolation

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`
- `src/test/java/dz/sh/hidra/modules/identity/AuthenticationArchitectureAndSecretGuardrailTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`
- `src/test/java/dz/sh/hidra/modules/identity/AuthenticationArchitectureAndSecretGuardrailTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-029 — Architecture and secret guardrails

Commit:

```text
test(authentication): add authentication architecture and secret guardrails
```

Protect boundaries and credentials with automated tests where practical.

Status:

```text
Completed — AuthenticationArchitectureAndSecretGuardrailTest supplements the repository-wide ArchUnit baseline with authentication-specific checks. Identity domain code is prohibited from Spring LDAP implementation dependencies; Identity REST controllers are prohibited from persistence/Spring Data dependencies; LDAP/OIDC normalization classes are prohibited from depending on external group/role/permission mapping models; platform code is prohibited from depending on Identity User/Role/Permission domain models; ordinary Identity API Java sources are scanned for password-hash exposure; production authentication bootstrap/JWT-HMAC/LDAP-bind secret properties must remain empty or environment placeholders; and text production sources/resources are rejected if they contain embedded PEM private-key material. No production behavior or AUTH-030 closure work was introduced.
```

Validation:

```bash
mvn -q clean verify
```

Result:

```text
PASS — pull-request CI run 300 completed repository compile/tests/full verification, acceptance compile/tests/clean verify, deterministic OpenAPI publication, and artifact upload successfully for AUTH-029 implementation commit aaf9691c941b2d3182cd2f9fb4975a7397ec53ed.
```

---

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/identity/AuthenticationArchitectureAndSecretGuardrailTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.

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

- `b4aa6d0fffd028d6c3d4163f9a71e8e982e707c3` — exact registered-message commit; surviving paths matched to current tree.
- `bc557f863a770d7b23d68894caff9195d53085d5` — exact registered-message commit; surviving paths matched to current tree.
- `fc849c2aa67992ac564ba221b64d01bae3243290` — exact registered-message commit; surviving paths matched to current tree.
- `fa0e3994599ad4571b8f3c136a0281d32414c9e2` — exact registered-message commit; surviving paths matched to current tree.
- `08b80de1fbd71012c652aa3e5d00171e3ac1d995` — exact registered-message commit; surviving paths matched to current tree.
- `cfea65c8a0f082b830d173dd0001616f381ac06f` — exact registered-message commit; surviving paths matched to current tree.
- `aaf9691c941b2d3182cd2f9fb4975a7397ec53ed` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1427](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1427)
- [docs/roadmap/authentication.md:2352](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L2352)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1427 | AUTH-029 | test(authentication): add authentication architecture and secret guardrails | Enforce module boundaries, credential secrecy, and provider isolation | Completed — authentication-specific architecture/secret tests protect Identity LDAP-domain isolation, controller persistence isolation, external-provider authorization mapping isolation, platform business-model ownership, API password-hash non-exposure, externalized authentication secret properties, and embedded private-key exclusion; repository CI run 300 passed
~~~~~~~~

<a id="hapi-042"></a>
## HAPI-042 — finalize authentication gap closure checklist

Phase: HPS-001 — Identity and Authentication

Legacy IDs: AUTH-030

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Record executable evidence and remaining optional cleanup

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityAuthenticationRequestRouterTest.java`
- `src/test/java/dz/sh/hidra/platform/configuration/HidraAuthenticationManagerConfigurationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityAuthenticationRequestRouterTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/authentication.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityAuthenticationRequestRouterTest.java`
- `src/test/java/dz/sh/hidra/platform/configuration/HidraAuthenticationManagerConfigurationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### AUTH-030 — Finalize authentication gap closure

Commit:

```text
docs(authentication): finalize authentication gap closure checklist
```

Record executable evidence that:

```text
explicit provider selection dynamically routes to intended provider
LOCAL authenticates against PostgreSQL-backed credentials
LDAP/AD authenticates through protected LDAP transport
OIDC remains functional and maps to Hidra identity
all providers normalize to HidraPrincipal
Hidra roles/permissions remain authoritative
all successful providers issue/produce the same Hidra JWT contract
ordinary in-memory LOCAL authentication is retired
no provider fallback exists
no secrets are committed
mvn -q clean verify passes
```

Do not mark items complete without evidence.

Status:

```text
Completed — executable evidence for every required authentication gap-closure criterion is recorded below, and AUTH-030 PR CI run 307 passed the required final repository and acceptance `mvn -q clean verify` gates.
```

Closure evidence checklist:

| Closure criterion | Evidence | Result |
|---|---|---|
| Explicit provider selection dynamically routes to the intended provider | AUTH-022 `IdentityAuthenticationRequestRouterTest` and `HidraAuthenticationManagerConfigurationTest`; CI run 239 | PASS — LOCAL, LDAP, and ACTIVE_DIRECTORY dispatch deterministically; OIDC direct-password routing and unsupported types fail closed |
| LOCAL authenticates against PostgreSQL-backed credentials | AUTH-023 provider/unit/API proof plus PostgreSQL Testcontainers integration; CI run 248 | PASS — persisted `LocalCredential` BCrypt hash is used by the real LOCAL provider |
| LDAP/AD authenticates through protected LDAP transport | AUTH-024 LDAP provider/adapter/configuration proof; CI run 257; AUTH-012 infrastructure baseline CI run 153 | PASS — directory verification/linkage is covered and staging/production enforce `ldaps://` with positive connection/read timeouts |
| OIDC remains functional and maps to Hidra identity | AUTH-025 OIDC normalization/API proof; CI run 270; existing OIDC contract remains protected | PASS — validated issuer+subject resolves through active provider + linked `ExternalIdentity` to Hidra User |
| All providers normalize to `HidraPrincipal` | AUTH-023, AUTH-024, AUTH-025 provider-specific proofs plus AUTH-026 unified JWT compatibility proof; CI runs 248, 257, 270, 279 | PASS — LOCAL, LDAP, ACTIVE_DIRECTORY, and OIDC converge on stable Hidra identity |
| Hidra roles/permissions remain authoritative | AUTH-027 authorization-ownership proof; CI run 286 | PASS — external OIDC claims and directory context cannot grant business permissions beyond Identity-owned effective permissions |
| All successful providers issue/produce the same Hidra JWT contract | AUTH-026 production encoder/decoder/converter compatibility proof; CI run 279 | PASS — common subject, issuer/audience/JTI, provider metadata, roles/scope reconstruction, and bearer factor verified across all provider sources |
| Ordinary in-memory LOCAL authentication is retired | AUTH-020 retirement; CI run 220; AUTH-021 persistent administrator bootstrap; CI run 229 | PASS — ordinary runtime no longer relies on `InMemoryUserDetailsManager`; bootstrap provisions normal persisted Identity state |
| No provider fallback exists | AUTH-022 routing/provider-manager proof; CI run 239 | PASS — failed selected LOCAL authentication does not invoke LDAP and unsupported/OIDC direct routes fail closed |
| No secrets are committed | AUTH-029 architecture/secret guardrails; CI run 300 and clean-tree CI run 305; AUTH-028 deployment runbook | PASS — secret-bearing properties remain externally supplied and production source/resource checks reject embedded private-key material |
| `mvn -q clean verify` passes for final closure | AUTH-030 PR CI run 307 on closure-documentation commit `ea29d1747f6f3dd2a5b0cb89cd0a12fa4003819e` | PASS — repository compile/tests/full `clean verify`, acceptance compile/tests/`clean verify`, deterministic OpenAPI publication, and artifact upload all succeeded |

Definition-of-done cross-check:

```text
Items 1-15 are backed by AUTH-002 through AUTH-029 implementation/test evidence recorded in this roadmap.
Item 16 is backed by AUTH-030 PR CI run 307, which passed the final repository and acceptance `mvn -q clean verify` gates.
All 16 definition-of-done items are therefore evidenced. No mandatory authentication gap remains open; section 20 items remain explicit optional/non-goal work requiring separate architecture decisions/roadmaps.
```

Validation:

```bash
mvn -q clean verify
```

Result:

```text
PASS — AUTH-030 PR CI run 307 completed repository compile/tests/full `mvn -q clean verify`, acceptance compile/tests/`mvn -q clean verify`, deterministic OpenAPI publication, and artifact upload successfully for closure-documentation commit `ea29d1747f6f3dd2a5b0cb89cd0a12fa4003819e`.
```

---

~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityAuthenticationRequestRouterTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/platform/configuration/HidraAuthenticationManagerConfigurationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `7b24dc122e52dd0c5471307e3611f2a3d999ae7d` — exact registered-message commit; surviving paths matched to current tree.
- `2681c81f35432f354d39a5cc599a4112b7590df4` — exact registered-message commit; surviving paths matched to current tree.
- `f3e71002f6f4bcc30c7e6388803253ff06ce0dc4` — exact registered-message commit; surviving paths matched to current tree.
- `7509a29584ed3548948ebcb3880f5a9951583279` — exact registered-message commit; surviving paths matched to current tree.
- `d0da9fb1d7f9136f2613ee023060ed2da33cab7b` — exact registered-message commit; surviving paths matched to current tree.
- `c5e8744d1c9a611eaf1d2f1b5920de370f18ac70` — exact registered-message commit; surviving paths matched to current tree.
- `6c778c65d3dc11819cc005da5871349b17815e7c` — exact registered-message commit; surviving paths matched to current tree.
- `ea29d1747f6f3dd2a5b0cb89cd0a12fa4003819e` — exact registered-message commit; surviving paths matched to current tree.
- `06fb803c567f97e685d0f664a8922e0a890cc480` — exact registered-message commit; surviving paths matched to current tree.
- `41ef2bdd10b637bd149fab164f0dfd87b7a7328a` — exact registered-message commit; surviving paths matched to current tree.
- `6de9a15ccd19985c248ed9e8f45f09466021eafc` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/authentication.md:1428](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L1428)
- [docs/roadmap/authentication.md:2382](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/authentication.md#L2382)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/authentication.md:1428 | AUTH-030 | docs(authentication): finalize authentication gap closure checklist | Record executable evidence and remaining optional cleanup | Completed — closure checklist fully evidenced; PR CI run 307 passed repository and acceptance `mvn -q clean verify` plus OpenAPI publication/artifact upload
~~~~~~~~

<a id="hapi-155"></a>
## HAPI-155 — scan identity domain models

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HMC-010

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 15 identity model files.

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDecision.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDelegationGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/GroupRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Role.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/RolePermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationEventType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationDecisionValue.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingMode.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantEffect.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/IdentityProviderStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/LoginSessionStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/PermissionStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDecision.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDelegationGrant.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDecision.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDelegationGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/GroupRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Role.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/RolePermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationEventType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationDecisionValue.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingMode.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantEffect.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/IdentityProviderStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/LoginSessionStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/PermissionStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 19. HMC-010 — Identity module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `identity`  
**Baseline model files:** 15  
**Scanned models:** 15  
**Declared fields/components:** 180  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Identity model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| AuthenticationEvent | record | 13 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java` | Extracted |
| AuthorizationDecision | record | 15 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDecision.java` | Extracted |
| AuthorizationDelegationGrant | record | 12 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDelegationGrant.java` | Extracted |
| ExternalRoleMapping | record | 10 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java` | Extracted |
| GroupRoleGrant | record | 10 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/GroupRoleGrant.java` | Extracted |
| HidraPrincipal | record | 7 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java` | Extracted |
| IdentityProvider | record | 23 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java` | Extracted |
| LocalCredential | record | 7 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java` | Extracted |
| LoginSession | record | 11 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java` | Extracted |
| Permission | record | 13 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java` | Extracted |
| Role | record | 10 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/Role.java` | Extracted |
| RolePermissionGrant | record | 9 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/RolePermissionGrant.java` | Extracted |
| User | record | 15 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java` | Extracted |
| UserPermissionGrant | record | 13 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java` | Extracted |
| UserRoleGrant | record | 12 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java` | Extracted |

### Identity fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| AuthenticationEvent | 1 | id | String |
| AuthenticationEvent | 2 | userId | String |
| AuthenticationEvent | 3 | identityProviderId | String |
| AuthenticationEvent | 4 | externalIdentityId | String |
| AuthenticationEvent | 5 | externalSubject | String |
| AuthenticationEvent | 6 | eventType | AuthenticationEventType |
| AuthenticationEvent | 7 | protocol | AuthenticationProtocol |
| AuthenticationEvent | 8 | clientIp | String |
| AuthenticationEvent | 9 | userAgent | String |
| AuthenticationEvent | 10 | failureReason | String |
| AuthenticationEvent | 11 | riskScore | java.math.BigDecimal |
| AuthenticationEvent | 12 | occurredAt | Instant |
| AuthenticationEvent | 13 | correlationId | String |
| AuthorizationDecision | 1 | id | String |
| AuthorizationDecision | 2 | userId | String |
| AuthorizationDecision | 3 | permissionCode | String |
| AuthorizationDecision | 4 | resourceType | String |
| AuthorizationDecision | 5 | resourceReferenceId | String |
| AuthorizationDecision | 6 | scope | AuthorizationScope |
| AuthorizationDecision | 7 | decision | AuthorizationDecisionValue |
| AuthorizationDecision | 8 | reasonCode | String |
| AuthorizationDecision | 9 | reasonMessage | String |
| AuthorizationDecision | 10 | matchedGrantIds | String |
| AuthorizationDecision | 11 | matchedPolicyRuleIds | String |
| AuthorizationDecision | 12 | externalClaimsUsed | String |
| AuthorizationDecision | 13 | evaluatedAt | Instant |
| AuthorizationDecision | 14 | correlationId | String |
| AuthorizationDecision | 15 | requestId | String |
| AuthorizationDelegationGrant | 1 | id | String |
| AuthorizationDelegationGrant | 2 | delegatorUserId | String |
| AuthorizationDelegationGrant | 3 | delegateUserId | String |
| AuthorizationDelegationGrant | 4 | permissionId | String |
| AuthorizationDelegationGrant | 5 | roleId | String |
| AuthorizationDelegationGrant | 6 | scope | AuthorizationScope |
| AuthorizationDelegationGrant | 7 | approvedByWorkflowId | String |
| AuthorizationDelegationGrant | 8 | validFrom | Instant |
| AuthorizationDelegationGrant | 9 | validTo | Instant |
| AuthorizationDelegationGrant | 10 | status | DelegationStatus |
| AuthorizationDelegationGrant | 11 | createdAt | Instant |
| AuthorizationDelegationGrant | 12 | revokedAt | Instant |
| ExternalRoleMapping | 1 | id | String |
| ExternalRoleMapping | 2 | identityProviderId | String |
| ExternalRoleMapping | 3 | roleId | String |
| ExternalRoleMapping | 4 | externalRoleCode | String |
| ExternalRoleMapping | 5 | claimName | String |
| ExternalRoleMapping | 6 | mappingMode | ExternalMappingMode |
| ExternalRoleMapping | 7 | scope | AuthorizationScope |
| ExternalRoleMapping | 8 | status | ExternalMappingStatus |
| ExternalRoleMapping | 9 | createdAt | Instant |
| ExternalRoleMapping | 10 | updatedAt | Instant |
| GroupRoleGrant | 1 | id | String |
| GroupRoleGrant | 2 | groupId | String |
| GroupRoleGrant | 3 | roleId | String |
| GroupRoleGrant | 4 | scope | AuthorizationScope |
| GroupRoleGrant | 5 | grantReason | String |
| GroupRoleGrant | 6 | approvedByWorkflowId | String |
| GroupRoleGrant | 7 | validFrom | Instant |
| GroupRoleGrant | 8 | validTo | Instant |
| GroupRoleGrant | 9 | status | GrantStatus |
| GroupRoleGrant | 10 | createdAt | Instant |
| HidraPrincipal | 1 | userId | String |
| HidraPrincipal | 2 | username | String |
| HidraPrincipal | 3 | displayName | String |
| HidraPrincipal | 4 | authenticationType | ProviderType |
| HidraPrincipal | 5 | identityProviderId | String |
| HidraPrincipal | 6 | roles | Set<String> |
| HidraPrincipal | 7 | permissions | Set<String> |
| IdentityProvider | 1 | id | String |
| IdentityProvider | 2 | code | String |
| IdentityProvider | 3 | name | String |
| IdentityProvider | 4 | providerType | ProviderType |
| IdentityProvider | 5 | issuerUri | String |
| IdentityProvider | 6 | authorizationEndpoint | String |
| IdentityProvider | 7 | tokenEndpoint | String |
| IdentityProvider | 8 | jwksUri | String |
| IdentityProvider | 9 | directoryBaseDn | String |
| IdentityProvider | 10 | userSearchBase | String |
| IdentityProvider | 11 | groupSearchBase | String |
| IdentityProvider | 12 | usernameAttribute | String |
| IdentityProvider | 13 | emailAttribute | String |
| IdentityProvider | 14 | displayNameAttribute | String |
| IdentityProvider | 15 | externalIdAttribute | String |
| IdentityProvider | 16 | groupMembershipAttribute | String |
| IdentityProvider | 17 | syncEnabled | boolean |
| IdentityProvider | 18 | justInTimeProvisioningEnabled | boolean |
| IdentityProvider | 19 | status | IdentityProviderStatus |
| IdentityProvider | 20 | metadata | String |
| IdentityProvider | 21 | secretReference | String |
| IdentityProvider | 22 | createdAt | Instant |
| IdentityProvider | 23 | updatedAt | Instant |
| LocalCredential | 1 | id | String |
| LocalCredential | 2 | userId | String |
| LocalCredential | 3 | passwordHash | String |
| LocalCredential | 4 | credentialStatus | String |
| LocalCredential | 5 | passwordChangedAt | Instant |
| LocalCredential | 6 | createdAt | Instant |
| LocalCredential | 7 | updatedAt | Instant |
| LoginSession | 1 | id | String |
| LoginSession | 2 | userId | String |
| LoginSession | 3 | identityProviderId | String |
| LoginSession | 4 | externalIdentityId | String |
| LoginSession | 5 | startedAt | Instant |
| LoginSession | 6 | lastSeenAt | Instant |
| LoginSession | 7 | expiresAt | Instant |
| LoginSession | 8 | clientIp | String |
| LoginSession | 9 | userAgent | String |
| LoginSession | 10 | status | LoginSessionStatus |
| LoginSession | 11 | correlationId | String |
| Permission | 1 | id | String |
| Permission | 2 | code | String |
| Permission | 3 | nameAr | String |
| Permission | 4 | nameFr | String |
| Permission | 5 | nameEn | String |
| Permission | 6 | description | String |
| Permission | 7 | permissionDomain | String |
| Permission | 8 | resourceType | String |
| Permission | 9 | action | String |
| Permission | 10 | sensitive | boolean |
| Permission | 11 | status | PermissionStatus |
| Permission | 12 | createdAt | Instant |
| Permission | 13 | updatedAt | Instant |
| Role | 1 | id | String |
| Role | 2 | code | String |
| Role | 3 | nameAr | String |
| Role | 4 | nameFr | String |
| Role | 5 | nameEn | String |
| Role | 6 | description | String |
| Role | 7 | roleType | RoleType |
| Role | 8 | status | RoleStatus |
| Role | 9 | createdAt | Instant |
| Role | 10 | updatedAt | Instant |
| RolePermissionGrant | 1 | id | String |
| RolePermissionGrant | 2 | roleId | String |
| RolePermissionGrant | 3 | permissionId | String |
| RolePermissionGrant | 4 | effect | GrantEffect |
| RolePermissionGrant | 5 | conditionExpression | String |
| RolePermissionGrant | 6 | validFrom | Instant |
| RolePermissionGrant | 7 | validTo | Instant |
| RolePermissionGrant | 8 | status | GrantStatus |
| RolePermissionGrant | 9 | createdAt | Instant |
| User | 1 | id | String |
| User | 2 | username | String |
| User | 3 | emailAddress | String |
| User | 4 | displayName | String |
| User | 5 | userType | UserType |
| User | 6 | status | UserStatus |
| User | 7 | employeeReferenceId | String |
| User | 8 | lastAuthenticatedAt | Instant |
| User | 9 | failedLoginCount | int |
| User | 10 | lockedUntil | Instant |
| User | 11 | createdAt | Instant |
| User | 12 | activatedAt | Instant |
| User | 13 | suspendedAt | Instant |
| User | 14 | disabledAt | Instant |
| User | 15 | updatedAt | Instant |
| UserPermissionGrant | 1 | id | String |
| UserPermissionGrant | 2 | userId | String |
| UserPermissionGrant | 3 | permissionId | String |
| UserPermissionGrant | 4 | effect | GrantEffect |
| UserPermissionGrant | 5 | scope | AuthorizationScope |
| UserPermissionGrant | 6 | grantReason | String |
| UserPermissionGrant | 7 | approvedByWorkflowId | String |
| UserPermissionGrant | 8 | emergencyAccess | boolean |
| UserPermissionGrant | 9 | validFrom | Instant |
| UserPermissionGrant | 10 | validTo | Instant |
| UserPermissionGrant | 11 | status | GrantStatus |
| UserPermissionGrant | 12 | createdAt | Instant |
| UserPermissionGrant | 13 | revokedAt | Instant |
| UserRoleGrant | 1 | id | String |
| UserRoleGrant | 2 | userId | String |
| UserRoleGrant | 3 | roleId | String |
| UserRoleGrant | 4 | scope | AuthorizationScope |
| UserRoleGrant | 5 | grantReason | String |
| UserRoleGrant | 6 | approvedByWorkflowId | String |
| UserRoleGrant | 7 | validFrom | Instant |
| UserRoleGrant | 8 | validTo | Instant |
| UserRoleGrant | 9 | status | GrantStatus |
| UserRoleGrant | 10 | createdAt | Instant |
| UserRoleGrant | 11 | revokedAt | Instant |
| UserRoleGrant | 12 | revokedReason | String |

### HMC-010 reconciliation

```text
baseline model files = 15
extracted models     = 15
exceptions           = 0
declared fields      = 180
zero-field models    = 0
```

The scan records only declarations under `identity/domain/model`. Identity domain value types and Java collection element types referenced by record components remain declared field types; they are not independent model rows for this catalogue scope.

No application, infrastructure, API, JPA, Flyway, DTO, authentication-provider implementation, credential persistence mapping, or database column metadata was mixed into this module scan.

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

- `b51f502b4d82b151b047be3211242b9e49edbc32` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:138](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L138)
- [docs/roadmap/model-field-catalogue.md:1123](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L1123)
- [docs/roadmap/model-field-catalogue.md:1338](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L1338)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:138 | HMC-010 | docs(catalogue): scan identity domain models | Scan all 15 identity model files. | **Completed** — 15/15 models, 180 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-187"></a>
## HAPI-187 — remediate semantic review IdentityProvider

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HMR-010

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review IdentityProvider

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Identity.md`
- `docs/roadmap/identity.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/IdentityProviderStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/integration/ExternalIdentityProviderClient.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/integration/NoopExternalIdentityProviderClient.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaIdentityProviderRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentityProviderJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/IdentityProviderJpaRepository.java`
- `src/main/resources/db/migration/V20261004_010__hmr_010_identity_identity_provider.sql`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/IdentityProviderSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/IdentityProviderStatus.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/integration/ExternalIdentityProviderClient.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/integration/NoopExternalIdentityProviderClient.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaIdentityProviderRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentityProviderJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Identity.md`
- `docs/roadmap/identity.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/IdentityProviderStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/integration/ExternalIdentityProviderClient.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/integration/NoopExternalIdentityProviderClient.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaIdentityProviderRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentityProviderJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/IdentityProviderJpaRepository.java`
- `src/main/resources/db/migration/V20261004_010__hmr_010_identity_identity_provider.sql`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/IdentityProviderSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-010 — identity.IdentityProvider

- Source review: `HMSR-010`
- Exact commit: `fix(identity): remediate semantic review IdentityProvider`
- Status: **Completed** — provider code uniqueness is enforced before persistence and by a unique database index; provider name and timestamps are intrinsic domain invariants; active OIDC providers require an issuer and active OIDC issuer resolution is uniqueness-safe; at most one ACTIVE LOCAL provider and one ACTIVE LDAP/ACTIVE_DIRECTORY provider candidate may exist while inactive/deprecated history remains allowed.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_010__hmr_010_identity_identity_provider.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Identity.md`
  - `docs/roadmap/identity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/value/IdentityProviderStatus.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/integration/ExternalIdentityProviderClient.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/integration/NoopExternalIdentityProviderClient.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaIdentityProviderRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentityProviderJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/IdentityProviderJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_010__hmr_010_identity_identity_provider.sql`
  - `src/test/java/dz/sh/hidra/modules/identity/semantic/IdentityProviderSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=IdentityProviderSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Unique provider code:** the Identity DDD explicitly requires `IdentityProvider.code` to be unique and recommends `uk_identity_provider_code`. The live schema has only non-unique `ix_hidra_identity_provider_code`; the application repository port has no code lookup/uniqueness contract.
  2. **Required provider name:** `name` is required by the Identity DDD and is `NOT NULL` in JPA/schema, but the domain constructor accepts null/blank values and normalizes blank input to `null`.
  3. **Unambiguous OIDC issuer resolution:** the live OIDC converter calls an `Optional` repository method keyed by `(ProviderType.OIDC, issuerUri)`, which assumes at most one matching provider. The schema has no demonstrated uniqueness constraint for that lookup. The current runtime therefore needs either explicit provisioning/application validation for an unambiguous active OIDC issuer mapping or a deliberately different provider-selection contract.
  4. **Provider-specific activation validation:** provider-specific fields are intentionally optional at the generic record level, but an ACTIVE provider must have the metadata required by its runtime path. At minimum, an OIDC provider used by the current converter requires a usable issuer URI; LOCAL and LDAP/AD active-provider cardinality must remain governed so ambiguous configuration fails closed. These checks belong in provider administration/provisioning/runtime validation rather than as unconditional constructor rules across all provider types.
  5. **Required timestamps:** `createdAt` and `updatedAt` are persistence-required but not guarded by the domain constructor. The final baseline must retain one deliberate enforcement boundary for provider creation/update metadata.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/identity/semantic/IdentityProviderSemanticRemediationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.

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

- `eb19630bdedf5c74fd6a919683cd93a446522049` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/data definition/Identity.md:714](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Identity.md#L714)
- [docs/roadmap/model-semantic-remediation.md:156](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L156)
- [docs/roadmap/model-semantic-remediation.md:830](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L830)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:156 | HMR-010 | HMSR-010 | identity | IdentityProvider | — | — | fix(identity): remediate semantic review IdentityProvider | **Completed**
~~~~~~~~

<a id="hapi-257"></a>
## HAPI-257 — remediate semantic review User

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HMR-063

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review User

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `docs/data definition/Identity.md`
- `docs/roadmap/identity.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/CreateUserRequest.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/response/UserResponse.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/command/CreateUserCommand.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/dto/UserSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CreateUserUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserPermissionGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRoleGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityUserApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserType.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserPermissionGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRoleGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserGroupMembershipJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserPermissionGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserRoleGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserGroupMembershipJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserPermissionGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserRoleGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/identity/IdentityEmployeeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/IdentityEmployeeReferenceQueryService.java`
- `src/main/resources/db/migration/V20261006_010__hmr_063_identity_user_uniqueness.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/UserIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/UserSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CreateUserUseCase.java`, `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserPermissionGrantRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRoleGrantRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityUserApplicationService.java`, `src/main/java/dz/sh/hidra/modules/organization/application/service/IdentityEmployeeReferenceQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserPermissionGrantRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRoleGrantRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserGroupMembershipJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `docs/data definition/Identity.md`
- `docs/roadmap/identity.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/CreateUserRequest.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/response/UserResponse.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/command/CreateUserCommand.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/dto/UserSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CreateUserUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserPermissionGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRoleGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityUserApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserType.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserPermissionGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRoleGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserGroupMembershipJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserPermissionGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserRoleGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserGroupMembershipJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserPermissionGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserRoleGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/identity/IdentityEmployeeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/IdentityEmployeeReferenceQueryService.java`
- `src/main/resources/db/migration/V20261006_010__hmr_063_identity_user_uniqueness.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/UserIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/UserSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-063 — identity.User

- Source review: `HMSR-075`
- Exact commit: `fix(identity): remediate semantic review User`
- Status: **Completed — Batch 5; final CI pending**
- SCC: —
- Recorded upstream HMS dependencies: organization.Employee
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261006_010__hmr_063_identity_user_uniqueness.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java
- Exact write allowlist:
  - `docs/data definition/Identity.md`
  - `docs/roadmap/identity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/CreateUserRequest.java`
  - `src/main/java/dz/sh/hidra/modules/identity/api/rest/response/UserResponse.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/command/CreateUserCommand.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/dto/UserSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CreateUserUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserPermissionGrantRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRoleGrantRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityUserApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserType.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserPermissionGrantRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRoleGrantRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserGroupMembershipJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserPermissionGrantJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserRoleGrantJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserGroupMembershipJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserPermissionGrantJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserRoleGrantJpaRepository.java`
  - `src/main/resources/db/migration/V20261006_010__hmr_063_identity_user_uniqueness.sql`
  - `src/test/java/dz/sh/hidra/modules/identity/semantic/UserSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=UserSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce non-blank username at the authoritative Identity domain/application boundary.** Do not rely on the JPA/Flyway NOT NULL failure as the business validation mechanism.
  2. **Enforce DDD-declared username and nullable-email uniqueness in PostgreSQL.** Add concurrency-safe uniqueness matching `uk_identity_user_username` and unique-if-present email semantics; optional application duplicate checks may supplement but not replace database protection.
  3. **Validate populated `employeeReferenceId` through an Organization-owned Employee lookup/application contract.** Preserve the scalar reference and do not add a cross-module database FK.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — exact-head oracle suite 16 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/UserIntegrityMigrationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/UserSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `8047881d58b3e9bd5490daecc547eebece2cd901` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:98](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L98)
- [doc/model-remediation/RECONCILIATION.md:13710](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13710)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15450](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15450)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:97](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L97)
- [docs/data definition/Identity.md:1419](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Identity.md#L1419)
- [docs/roadmap/model-semantic-remediation.md:229](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L229)
- [docs/roadmap/model-semantic-remediation.md:3499](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3499)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:229 | HMR-063 | HMSR-075 | identity | User | — | organization.Employee | fix(identity): remediate semantic review User | Completed — Batch 5; final CI pending
doc/model-remediation/RECONCILIATION.md:98 | HMR-063 | HMSR-075 | identity.User | COMPLETED — HPR-P2-008 | Nonblank username enforced in domain; PostgreSQL named username/email uniqueness, nullable-email semantics and fail-closed legacy preflight; optional Employee resolved through Organization-owned contract; no cross-module FK. V20261006_010 and focused tests added; final CI #604 passed.
doc/model-remediation/RECONCILIATION.md:13710 | HMR-063 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:97 | HMR-063 | HMSR-075 | [identity.User](../domain/SEMANTIC_DECISIONS.md#identity-user) | [UserSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/identity/semantic/UserSemanticRemediationTest.java) | [V20261006_010__hmr_063_identity_user_uniqueness](../../src/main/resources/db/migration/V20261006_010__hmr_063_identity_user_uniqueness.sql); [IdentityEmployeeReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/identity/IdentityEmployeeReferenceContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15450 | HMR-063 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-281"></a>
## HAPI-281 — remediate semantic review LoginSession

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HMR-087

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review LoginSession

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `docs/data definition/Identity.md`
- `docs/roadmap/identity.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityAuthenticationWebMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/AuthenticatedPrincipalInput.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LoginSessionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/LoginSessionStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLoginSessionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/LoginSessionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProvider.java`
- `src/main/resources/db/migration/V20261006_012__hmr_087_login_session_evidence.sql`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LoginSessionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLoginSessionRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/LoginSessionJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `docs/data definition/Identity.md`
- `docs/roadmap/identity.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityAuthenticationWebMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/AuthenticatedPrincipalInput.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LoginSessionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/LoginSessionStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLoginSessionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/LoginSessionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProvider.java`
- `src/main/resources/db/migration/V20261006_012__hmr_087_login_session_evidence.sql`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-087 — identity.LoginSession

- Source review: `HMSR-104`
- Exact commit: `fix(identity): remediate semantic review LoginSession`
- Status: **Completed — Batch 5; final CI pending**
- SCC: —
- Recorded upstream HMS dependencies: identity.IdentityProvider, identity.User
- HMSR correction count: 3
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Identity.md`
  - `docs/roadmap/identity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LoginSessionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/value/LoginSessionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLoginSessionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/LoginSessionJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=LoginSessionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Add/preserve the DDD-required `sessionType` on the canonical LoginSession model and persistence contract**, reusing the established authentication-protocol vocabulary rather than inventing an unnecessary taxonomy.
  2. **Propagate `externalIdentityId` for externally authenticated sessions when an ExternalIdentity actually participated**, preserving optionality for LOCAL/system paths.
  3. **Add/preserve an explicit `endedAt` lifecycle timestamp and stop using `lastSeenAt` as a substitute for session termination time**; keep activity tracking separate if still required.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionIntegrityMigrationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.

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

- `c73cf5326156b0850c317195b41d4ecbc24a553a` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:122](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L122)
- [doc/model-remediation/RECONCILIATION.md:13734](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13734)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15474](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15474)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:121](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L121)
- [docs/data definition/Identity.md:1427](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Identity.md#L1427)
- [docs/roadmap/model-semantic-remediation.md:258](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L258)
- [docs/roadmap/model-semantic-remediation.md:4521](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L4521)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:258 | HMR-087 | HMSR-104 | identity | LoginSession | — | identity.IdentityProvider, identity.User | fix(identity): remediate semantic review LoginSession | Completed — Batch 5; final CI pending
doc/model-remediation/RECONCILIATION.md:122 | HMR-087 | HMSR-104 | identity.LoginSession | COMPLETED — HPR-P2-008 | AuthenticationProtocol sessionType and independent endedAt carried through domain/JPA/mapper; exact ExternalIdentity propagated from LDAP/OIDC through principal/input/completion; terminal lifecycle preserves lastSeenAt and prior termination; V20261006_012 requires explicit legacy protocol evidence; no inferred historical termination; focused tests added; final CI #604 passed.
doc/model-remediation/RECONCILIATION.md:13734 | HMR-087 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:121 | HMR-087 | HMSR-104 | [identity.LoginSession](../domain/SEMANTIC_DECISIONS.md#identity-loginsession) | [LoginSessionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java) | [V20261006_012__hmr_087_login_session_evidence](../../src/main/resources/db/migration/V20261006_012__hmr_087_login_session_evidence.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15474 | HMR-087 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-320"></a>
## HAPI-320 — review identity IdentityProvider

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HMSR-010

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review identity IdentityProvider

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/IdentityProviderStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/IdentityProviderStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 23. HMSR-010 — identity.IdentityProvider review

**Decision:** REVISE  
**Review code:** HMSR-010  
**Dependency level:** 0  
**Bounded context:** identity  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 4 — `identity.ExternalRoleMapping`, `identity.AuthenticationEvent`, `identity.HidraPrincipal`, `identity.LoginSession`  
**Transitive dependents:** 4  
**Unresolved/non-subject references:** 0

### 23.1 Semantic role and ordering rationale

`IdentityProvider` is the Identity-owned configuration/master record for one authentication or external identity source. It represents providers such as LOCAL, LDAP, Active Directory, OIDC, OAuth2, SAML2, Keycloak, Azure AD / Entra ID and Okta while preserving Hidra-owned authorization as the final authority.

It is Level 0 because it has no upstream dependency on another HMS subject model. Its subject dependents use the provider identity for external-role mapping, normalized principals, login-session provenance and authentication-event traceability.

Several Identity read/persistence models outside the 123 HMS subjects also depend on it, including `ExternalIdentity`, `ExternalGroupMapping`, `ExternalPermissionMapping` and `IdentitySynchronizationJob`. Those dependencies do not change the HMS subject graph.

### 23.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable provider identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable provider business/configuration code; current Identity DDD requires uniqueness. |
| `name` | `String` | Mandatory | Human-readable provider display name. |
| `providerType` | `ProviderType` | Mandatory | Provider technology/source classification: `LOCAL`, `LDAP`, `ACTIVE_DIRECTORY`, `OIDC`, `OAUTH2`, `SAML2`, `KEYCLOAK`, `AZURE_AD`, or `OKTA`. |
| `issuerUri` | `String` | Provider-specific optional | Issuer URI for OIDC/SAML-style providers; current OIDC normalization resolves providers by issuer. |
| `authorizationEndpoint` | `String` | Provider-specific optional | OIDC/OAuth authorization endpoint metadata. |
| `tokenEndpoint` | `String` | Provider-specific optional | OIDC/OAuth token endpoint metadata. |
| `jwksUri` | `String` | Provider-specific optional | JWK-set endpoint metadata. |
| `directoryBaseDn` | `String` | Provider-specific optional | LDAP/AD directory base DN metadata. |
| `userSearchBase` | `String` | Provider-specific optional | LDAP/AD user-search base metadata. |
| `groupSearchBase` | `String` | Provider-specific optional | LDAP/AD group-search base metadata. |
| `usernameAttribute` | `String` | Provider-specific optional | Provider attribute used for username mapping. |
| `emailAttribute` | `String` | Provider-specific optional | Provider attribute used for email mapping. |
| `displayNameAttribute` | `String` | Provider-specific optional | Provider attribute used for display-name mapping. |
| `externalIdAttribute` | `String` | Provider-specific optional | Stable external identity attribute such as objectGUID/objectId/sub/NameID. |
| `groupMembershipAttribute` | `String` | Provider-specific optional | External group/role membership attribute such as `memberOf`, `groups`, or `roles`. |
| `syncEnabled` | `boolean` | Mandatory persisted state | Whether synchronization is enabled for the provider. |
| `justInTimeProvisioningEnabled` | `boolean` | Mandatory persisted state | Whether login-time creation/update may be performed under provider/mapping policy. |
| `status` | `IdentityProviderStatus` | Mandatory | Provider lifecycle: `ACTIVE`, `INACTIVE`, `FAILED`, or `DEPRECATED`. |
| `metadata` | JSON text / `jsonb` persistence | Optional | Non-secret provider metadata and mapping hints only. |
| `secretReference` | `String` | Optional | Reference to a vault/platform secret; never the secret value itself. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update timestamp. |

The domain constructor already rejects blank `id` and `code`, rejects null `providerType` and `status`, trims textual values, and converts blank optional text to `null`.

### 23.3 Provider taxonomy versus authentication protocol

The existing provider taxonomy is intentional and must not be duplicated.

`ProviderType` identifies the configured source technology and distinguishes, for example, LDAP from Active Directory and OIDC from vendor-specific providers. `AuthenticationProtocol` is a separate authentication-event/session classification.

The current session lifecycle maps:

- `LOCAL -> LOCAL`;
- `LDAP` and `ACTIVE_DIRECTORY -> LDAP`;
- `OIDC`, `KEYCLOAK`, `AZURE_AD`, and `OKTA -> OIDC`;
- `OAUTH2 -> OAUTH2`;
- `SAML2 -> SAML2`.

This is semantically coherent and is not a duplicate-enum defect.

### 23.4 Active-provider runtime semantics

The Identity DDD requires external login/synchronization to use only ACTIVE providers. Current authentication paths enforce that rule fail-closed:

- LOCAL authentication requires exactly one ACTIVE `ProviderType.LOCAL` provider and rejects none/multiple candidates;
- LDAP/AD authentication requires exactly one ACTIVE provider among `LDAP` / `ACTIVE_DIRECTORY` and rejects none/multiple candidates;
- OIDC normalization resolves `ProviderType.OIDC + issuerUri`, then requires the provider to be ACTIVE;
- the OIDC path also requires a LINKED `ExternalIdentity` and ACTIVE Hidra `User`;
- external roles/groups/scopes do not directly become Hidra authorization; effective permissions remain Identity-owned.

The exactly-one active LOCAL/directory behavior is a runtime/deployment governance rule, not evidence for making provider type globally unique in the table. Inactive/deprecated historical provider records may coexist.

### 23.5 Persistence and dependency consistency

The live domain and JPA models agree on all 23 declared components.

The base Identity migration makes `id`, `code`, `name`, `provider_type`, `sync_enabled`, `just_in_time_provisioning_enabled`, `status`, `created_at`, and `updated_at` non-null. Provider-specific metadata fields remain nullable.

The schema currently creates ordinary indexes for provider `code`, `status`, and timestamps; no unique constraint for provider code was found.

`IdentityProviderRepositoryPort` exposes only `save` and `findById`. The infrastructure repository additionally exposes `findByProviderTypeAndIssuerUri` for OIDC resolution.

HRA-111 protects provider references for same-module persistence models, including external mappings and synchronization records, with FKs to `hidra_identity_provider(id)`. The four HMS subject dependents remain the graph-facing dependencies recorded by the review register.

### 23.6 Secret-safety boundary

The Identity DDD is explicit:

```text
Provider configuration must not contain secret values.
```

`secretReference` is therefore pointer metadata only. LDAP bind passwords, OAuth/OIDC client secrets, tokens, private keys, certificate private material and similar credentials belong in vault/platform secret management.

The current authentication deployment runbook follows that policy by keeping sensitive runtime inputs outside the repository/database. HMSR-010 does not redefine `secretReference` as credential storage and does not authorize secret material inside `metadata`.

Secret safety is primarily a provisioning/platform governance concern; arbitrary secret detection must not be guessed from string contents inside the domain record.

### 23.7 Required revisions

The model ownership and provider taxonomy are sound, but the target baseline cannot be APPROVED until these evidence-backed gaps are resolved:

1. **Unique provider code:** the Identity DDD explicitly requires `IdentityProvider.code` to be unique and recommends `uk_identity_provider_code`. The live schema has only non-unique `ix_hidra_identity_provider_code`; the application repository port has no code lookup/uniqueness contract.
2. **Required provider name:** `name` is required by the Identity DDD and is `NOT NULL` in JPA/schema, but the domain constructor accepts null/blank values and normalizes blank input to `null`.
3. **Unambiguous OIDC issuer resolution:** the live OIDC converter calls an `Optional` repository method keyed by `(ProviderType.OIDC, issuerUri)`, which assumes at most one matching provider. The schema has no demonstrated uniqueness constraint for that lookup. The current runtime therefore needs either explicit provisioning/application validation for an unambiguous active OIDC issuer mapping or a deliberately different provider-selection contract.
4. **Provider-specific activation validation:** provider-specific fields are intentionally optional at the generic record level, but an ACTIVE provider must have the metadata required by its runtime path. At minimum, an OIDC provider used by the current converter requires a usable issuer URI; LOCAL and LDAP/AD active-provider cardinality must remain governed so ambiguous configuration fails closed. These checks belong in provider administration/provisioning/runtime validation rather than as unconditional constructor rules across all provider types.
5. **Required timestamps:** `createdAt` and `updatedAt` are persistence-required but not guarded by the domain constructor. The final baseline must retain one deliberate enforcement boundary for provider creation/update metadata.

HMSR-010 does not change production Java, JPA, Flyway, authentication runtime, API/application contracts, tests, provider records or secret configuration.

### 23.8 Operational and security interpretation

For SONATRACH/TRC, `IdentityProvider` identifies the trusted authentication source used to establish who the user is. It does **not** transfer authorization authority to the external provider.

LDAP/AD groups, OIDC roles, Keycloak realm roles, Azure/Entra claims or Okta claims must be mapped explicitly into Hidra Identity structures. Hidra roles, permissions, scope validity and authorization decisions remain locally governed and auditable.

Provider failure or ambiguity must fail closed; it must never fall back silently from an external provider to LOCAL authentication.

### 23.9 Review conclusion

**REVISE.** `IdentityProvider` has the correct Identity ownership, 23-field shape, provider/status vocabulary, secret-reference semantics and runtime authorization boundary. Its four HMS subject dependencies are coherent.

The target baseline cannot mark it APPROVED while provider-code uniqueness, mandatory provider name and unambiguous/provider-specific activation rules are not consistently enforced across persistence/provisioning/runtime boundaries. HMS reconciliation must retain these obligations until an explicitly authorized Identity correction task resolves them or the target semantics are explicitly changed.

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

- `9157881cac013793dcfc61eb1fdd0af5a8e789ff` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:62](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L62)
- [docs/roadmap/model-semantic-review.md:205](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L205)
- [docs/roadmap/model-semantic-review.md:4149](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L4149)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:205 | HMSR-010 | 0 | identity | IdentityProvider | — | — | 4 | 4 | 0 | REVISE | Completed | docs(model-review): review identity IdentityProvider
doc/domain/SEMANTIC_DECISIONS.md:62 | HMSR-010 | [identity.IdentityProvider](#identity-identityprovider) | REVISE | HMR-010
~~~~~~~~

<a id="hapi-355"></a>
## HAPI-355 — review identity ExternalRoleMapping

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HMSR-045

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review identity ExternalRoleMapping

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingMode.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingMode.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingMode.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 58. HMSR-045 — identity.ExternalRoleMapping review

**Decision:** APPROVED  
**Review code:** HMSR-045  
**Dependency level:** 1  
**Bounded context:** identity  
**Confirmed upstream subject dependencies:** `identity.IdentityProvider`, `identity.Role`  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject references:** 0

### 58.1 Semantic role and ordering rationale

`ExternalRoleMapping` maps one trusted external role/claim from an IdentityProvider to one internal Hidra Role.

The Identity DDD explicitly prefers external group mapping for common enterprise integration and reserves external role mapping for trusted role claims emitted by the external IDM.

It is Level 1 because both `identityProviderId` and `roleId` reference already-reviewed Identity HMS subjects.

### 58.2 Field semantics

| Field | Type | Reviewed meaning |
|---|---|---|
| `id` | `String` | Mandatory stable mapping identity. |
| `identityProviderId` | `String` | Mandatory same-module reference to IdentityProvider. |
| `roleId` | `String` | Mandatory same-module reference to internal Role. |
| `externalRoleCode` | `String` | Mandatory external role/claim value. |
| `claimName` | `String` | Optional claim-name selector. |
| `mappingMode` | `ExternalMappingMode` | Mandatory mapping policy: direct grant, local approval, or disabled. |
| `scope` | `AuthorizationScope` | Optional neutral authorization scope in the domain, persisted as type/reference/snapshot components. |
| `status` | `ExternalMappingStatus` | Mandatory mapping lifecycle state. |
| `createdAt` | `Instant` | Persistence-required creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required update timestamp. |

The canonical constructor requires nonblank ID, IdentityProvider ID, Role ID and external role code, and requires non-null mapping mode and status.

### 58.3 IdentityProvider and Role integrity

The final schema requires both:

```text
identity_provider_id NOT NULL
role_id NOT NULL
```

HRA-111 installs and validates:

```text
identity_provider_id -> hidra_identity_provider.id
role_id              -> hidra_identity_role.id
```

Therefore both mandatory HMS dependencies are fail-closed at persistence.

No additional graph edge is needed.

### 58.4 External-claim semantics

The DDD defines `externalRoleCode` as the external role/claim value and optional `claimName` as the claim source such as `roles`, `realm_access.roles`, or `groups`.

The current model does not embed provider protocol details, credentials, tokens, or secret material.

No current DDD rule establishes a uniqueness key for role mappings, so HMSR-045 does not invent uniqueness across provider/claim/role combinations.

### 58.5 Mapping mode and approval semantics

The DDD defines:

```text
DIRECT_GRANT
REQUIRES_LOCAL_APPROVAL
DISABLED
```

as allowed mapping modes.

Current repository evidence does not state that `REQUIRES_LOCAL_APPROVAL` must store a Workflow approval identifier directly on ExternalRoleMapping. The Identity DDD uses explicit workflow references on other entities where required, but does not define one here.

HMSR-045 therefore preserves the mapping-mode semantics without inventing an approval-reference field or workflow dependency.

### 58.6 AuthorizationScope semantics

The domain represents scope as `AuthorizationScope`, while persistence stores:

```text
scope_type
scope_reference_id
scope_code_snapshot
```

The mapper deliberately converts between the domain value object and flattened persistence fields.

`AuthorizationScope` uses neutral references and defaults a null type inside a non-null scope object to `GLOBAL`; GLOBAL clears any reference/snapshot. A completely absent scope remains nullable through the persistence mapper.

This is consistent with the Identity boundary rule that Organization/Topology scope targets remain externally owned and must not become cross-module FKs.

No single HMS target is created from scoped references.

### 58.7 Lifecycle and eligibility rules deliberately not invented

`ExternalMappingStatus` is mandatory and persistence-aligned.

Current Identity DDD evidence does not establish:

- that only ACTIVE IdentityProviders may have stored mappings;
- that only ACTIVE Roles may be referenced;
- automatic deactivation when a provider or role changes status;
- mapping uniqueness constraints;
- mandatory local approval evidence stored on this model;
- mandatory claimName;
- mandatory scope for any mapping mode.

HMSR-045 therefore records none of those as production obligations.

### 58.8 Domain/JPA/Flyway alignment

The domain model has 10 components; JPA flattens the single `AuthorizationScope` value object into three columns, producing 12 persistence fields while preserving the same semantic state.

Required business fields align between domain and persistence:

```text
id
identityProviderId
roleId
externalRoleCode
mappingMode
status
```

Persistence additionally requires audit timestamps, which remain application/persistence metadata.

The mapper correctly reconstructs the domain scope from the flattened scope fields.

### 58.9 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, ExternalRoleMapping supports controlled federation from enterprise IDM platforms such as Active Directory, Entra ID, Keycloak or other trusted providers into Hidra's internal RBAC model.

The mapping allows external role claims to resolve to internal Hidra roles while keeping authorization scope neutral and leaving Organization/Topology ownership outside Identity.

This separation prevents external directory structure from becoming an accidental organizational or asset master-data authority inside Hidra.

### 58.10 Review conclusion

**APPROVED.** `ExternalRoleMapping` has coherent provider/role dependencies, validated same-module FK protection, required external-claim semantics, explicit mapping modes, neutral scoped authorization representation, lifecycle status, and clean separation from external IDM protocol/secrets and foreign bounded-context ownership.

No evidence-backed production correction obligation remains for HMSR-045.

HMSR-045 does not modify production Java, JPA, Flyway, application/API contracts, tests, role mappings, identity providers, roles, or provisioned data.

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

- `592c8774d1a058523ef9b54aedae0e5ee0ddc052` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:97](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L97)
- [docs/roadmap/model-semantic-review.md:240](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L240)
- [docs/roadmap/model-semantic-review.md:9815](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L9815)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:240 | HMSR-045 | 1 | identity | ExternalRoleMapping | — | identity.IdentityProvider, identity.Role | 0 | 0 | 0 | APPROVED | Completed | docs(model-review): review identity ExternalRoleMapping
doc/domain/SEMANTIC_DECISIONS.md:97 | HMSR-045 | [identity.ExternalRoleMapping](#identity-externalrolemapping) | APPROVED | —
~~~~~~~~

<a id="hapi-385"></a>
## HAPI-385 — review identity User

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HMSR-075

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review identity User

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityUserApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityUserApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityUserApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 88. HMSR-075 — identity.User review

**Decision:** REVISE  
**Review code:** HMSR-075  
**Dependency level:** 4  
**Bounded context:** identity  
**Confirmed upstream subject dependencies:** optional `organization.Employee`  
**Confirmed direct HMS subject dependents:** 8  
**Transitive HMS subject dependents:** 8

### 88.1 Semantic role

`User` is the Identity-owned application security identity. It owns Hidra username, optional email/display snapshot, user type, account lifecycle status, authentication/lockout metadata and lifecycle timestamps.

It is explicitly not the employee master record. Organization owns Employee. External-provider subjects and provider-specific identity attributes belong to `ExternalIdentity`, and local password state belongs to `LocalCredential`.

### 88.2 Username requiredness

The Identity DDD explicitly defines:

```text
username | CODE | required
Unique username | username must be unique
```

and the JPA/Flyway schema declares `username NOT NULL`.

However, the live `User` record constructor validates only `id`, `userType` and `status`. It normalizes a blank username to null instead of rejecting it.

`IdentityUserApplicationService.createUser()` also passes `command.username()` directly into the aggregate without application-level validation.

Therefore a semantically invalid user can cross the domain/application boundary and fail only later at persistence.

The authoritative Identity write boundary must enforce non-blank username before persistence.

### 88.3 Username and email uniqueness

The Identity DDD explicitly requires:

```text
username must be unique
emailAddress should be unique if present
```

and its physical-index section names:

```text
uk_identity_user_username
uk_identity_user_email
```

The live Flyway table currently creates `hidra_identity_user` and ordinary indexes for status, employee reference and timestamps, but it does not create unique constraints/indexes for username or email.

`UserRepositoryPort` exposes `findByUsername(...)` but no database-enforced uniqueness guarantee, and the current create service performs no pre-save duplicate check.

For concurrency-safe identity semantics, username uniqueness must be enforced by PostgreSQL. Nullable email uniqueness should likewise follow the DDD's stated unique-if-present rule.

Application duplicate detection may improve errors, but it must not replace database uniqueness.

### 88.4 Employee boundary

`employeeReferenceId` is optional and explicitly defined by the Identity DDD as:

```text
Reference to organization.Employee.
Identity does not own employee fields.
```

The field is correctly stored as a scalar cross-context reference with an index rather than a database FK.

However, `createUser()` copies a populated employee reference directly from the command, and repository search found no Organization-owned employee lookup port used by Identity.

When populated, the employee reference should fail closed through an Organization-owned application/lookup contract. No cross-module database FK should be introduced.

HMSR-075 does not make employee linkage mandatory because SERVICE, SYSTEM, INTEGRATION and other legitimate non-employee users exist.

### 88.5 User type and account status

`UserType` defines:

```text
HUMAN
SERVICE
SYSTEM
INTEGRATION
BREAK_GLASS
```

`UserStatus` defines:

```text
REGISTERED
ACTIVE
SUSPENDED
DISABLED
LOCKED
```

These are Identity-owned security/lifecycle enums, not descriptive enterprise catalog taxonomies. Current evidence does not require catalog migration.

The active creation path correctly defaults missing user type to HUMAN and starts a new account as REGISTERED.

### 88.6 Credentials and external identity separation

The model contains no password hash, bind secret, provider subject, LDAP DN, AD GUID, Keycloak ID, Azure object ID or provider ID.

This matches the DDD's explicit separation:

```text
User                 -> Hidra security identity
LocalCredential      -> local password credential
ExternalIdentity     -> external provider linkage
platform/vault       -> provider secrets and technical protocol plumbing
```

No correction is required for credential/provider ownership.

### 88.7 Authentication and lockout metadata

`lastAuthenticatedAt`, `failedLoginCount` and `lockedUntil` are Identity-owned authentication/account metadata.

The model's `locked(at)` helper correctly considers both explicit LOCKED status and a future `lockedUntil`.

Current Identity DDD evidence does not define a minimum/maximum failed-login counter, timestamp ordering among account lifecycle fields, or a mandatory coupling such as LOCKED requiring `lockedUntil`.

HMSR-075 therefore does not invent those invariants.

### 88.8 Email and display semantics

`emailAddress` and `displayName` are optional.

The DDD describes email as required for human users unless an external provider does not supply one. That condition depends on provisioning/provider context not represented solely by the User record.

HMSR-075 therefore does not impose a universal HUMAN-email constructor invariant.

`displayName` remains snapshot/display state and must not become employee master data.

### 88.9 Grant eligibility boundary

The DDD states that only ACTIVE users should receive new grants except controlled emergency/break-glass flows.

That rule belongs to UserRoleGrant/UserPermissionGrant/Group membership/delegation write paths rather than User construction itself.

HMSR-075 records no duplicate production obligation here; the downstream grant models remain separately reviewable under their registered HMSR tasks.

### 88.10 Required revisions

Three evidence-backed obligations remain:

1. **Enforce non-blank username at the authoritative Identity domain/application boundary.** Do not rely on the JPA/Flyway NOT NULL failure as the business validation mechanism.

2. **Enforce DDD-declared username and nullable-email uniqueness in PostgreSQL.** Add concurrency-safe uniqueness matching `uk_identity_user_username` and unique-if-present email semantics; optional application duplicate checks may supplement but not replace database protection.

3. **Validate populated `employeeReferenceId` through an Organization-owned Employee lookup/application contract.** Preserve the scalar reference and do not add a cross-module database FK.

HMSR-075 does not modify production Java, JPA, Flyway, API/application contracts, tests, Identity users/credentials/external identities, Organization employee data, or provisioned data.

### 88.11 Review conclusion

**REVISE.** `User` is a coherent 15-field Identity-owned security aggregate with correct separation from Employee, LocalCredential, ExternalIdentity and platform/vault concerns. User type and status are appropriate Identity enums, and employee linkage is correctly non-relational across bounded contexts.

The target baseline cannot mark it APPROVED while required username semantics are not enforced before persistence, the DDD's username/email uniqueness rules are absent from the live database schema, and populated employee references are not owner-validated.

HMS reconciliation must retain these obligations until explicitly authorized Identity production corrections resolve them or stronger repository evidence changes the governing Identity DDD.

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

- `e44771307827088df88c46435d078de0c6f89df6` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:127](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L127)
- [docs/roadmap/model-semantic-review.md:270](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L270)
- [docs/roadmap/model-semantic-review.md:14290](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L14290)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:270 | HMSR-075 | 4 | identity | User | — | organization.Employee | 8 | 8 | 1 | REVISE | Completed | docs(model-review): review identity User
doc/domain/SEMANTIC_DECISIONS.md:127 | HMSR-075 | [identity.User](#identity-user) | REVISE | HMR-063
~~~~~~~~

<a id="hapi-409"></a>
## HAPI-409 — review identity AuthenticationEvent

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HMSR-099

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review identity AuthenticationEvent

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationEventType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationEventType.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationEventType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 112. HMSR-099 — identity.AuthenticationEvent review

**Decision:** APPROVED  
**Review code:** HMSR-099  
**Dependency level:** 5  
**Bounded context:** identity  
**Confirmed upstream subject dependencies:** optional `identity.User`; optional `identity.IdentityProvider`; optional external-identity trace reference  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject reference count:** 1

### 112.1 Semantic role

`AuthenticationEvent` is Identity-owned security trace evidence for authentication outcomes.

The Identity DDD explicitly states that the event may be persisted by Identity and/or forwarded to Audit/SIEM. It is therefore historical security evidence, not mutable business-state ownership.

### 112.2 Required event invariants

The DDD requires:

```text
id
eventType
protocol
occurredAt
```

The live domain constructor rejects blank/null values for all four.

JPA/Flyway persist the corresponding required columns as non-null.

No gap exists between the DDD-required event core and the live domain/persistence contract.

### 112.3 Event and protocol taxonomies

`AuthenticationEventType` exactly matches the DDD vocabulary:

```text
LOGIN_SUCCESS
LOGIN_FAILED
LOGOUT
TOKEN_REFRESH
ACCOUNT_LOCKED
MFA_REQUIRED
MFA_FAILED
```

`AuthenticationProtocol` also matches the DDD vocabulary:

```text
LOCAL
LDAP
OIDC
SAML2
OAUTH2
API_TOKEN
SYSTEM
```

These values represent stable technical authentication semantics. Current evidence supports retaining them as enums rather than converting them to catalog-backed business taxonomies.

### 112.4 Optional identity references

`userId`, `identityProviderId`, and `externalIdentityId` are all optional in the governing DDD.

That optionality is semantically necessary for authentication evidence: failed or partially resolved authentication attempts may not have a fully resolved user or external identity.

Because `AuthenticationEvent` is historical security trace evidence, HMSR-099 does not force database foreign keys onto these optional identifiers merely because the referenced records are Identity-owned. Doing so could incorrectly prevent retention of security evidence when an identity/provider is unresolved or later retired.

The current scalar-reference shape is therefore acceptable.

### 112.5 External subject and request metadata

`externalSubject`, `clientIp`, `userAgent`, `failureReason`, and `correlationId` are optional snapshots/context according to the DDD.

The live model preserves those fields as optional scalar evidence and does not import provider/session implementation types.

That is consistent with the Identity boundary.

### 112.6 Risk score

`riskScore` is explicitly optional in the DDD.

The reviewed Identity DDD does not define a numeric range, normalization rule, mandatory precision rule beyond persistence type, or lifecycle condition under which it must be present.

HMSR-099 therefore does not invent a `0..1`, percentage, or other range constraint.

### 112.7 Application evidence

The live authentication implementation records events from both:

- persisted LOCAL authentication outcomes; and
- provider-neutral authentication session lifecycle operations.

LOCAL success/failure events and non-LOCAL success/logout events use the same canonical `AuthenticationEvent` domain type.

The authentication roadmap also records completed provider-routing, LOCAL, LDAP/AD, OIDC, normalized-principal, JWT compatibility, authorization-ownership, and architecture/secret guardrail proof. No current repository evidence shows a semantic contradiction in the `AuthenticationEvent` model itself.

### 112.8 Audit/SIEM boundary

The DDD allows AuthenticationEvent evidence to be forwarded to Audit/SIEM but does not make Audit the owner of the Identity event record.

HMSR-099 therefore preserves Identity ownership and does not introduce cross-module Audit database coupling.

Any forwarding mechanism remains an application/integration concern outside this model review.

### 112.9 Review conclusion

**APPROVED.** `AuthenticationEvent` matches the governing Identity DDD: its required event core is enforced, technical enums align exactly with the DDD, optional identity/provider/external-subject fields correctly support partially resolved and historical authentication evidence, and no unsupported business invariant needs to be invented.

No production correction obligation is recorded for HMSR-099.

HMSR-099 does not modify production Java, JPA, Flyway, API/application contracts, tests, Identity/Audit data, or provisioned data.

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

- `fd8ad49416fc9a7d08da6b574b2db8e40ac8efee` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:151](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L151)
- [docs/roadmap/model-semantic-review.md:294](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L294)
- [docs/roadmap/model-semantic-review.md:17519](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L17519)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:294 | HMSR-099 | 5 | identity | AuthenticationEvent | — | identity.IdentityProvider, identity.User | 0 | 0 | 1 | APPROVED | Completed | docs(model-review): review identity AuthenticationEvent
doc/domain/SEMANTIC_DECISIONS.md:151 | HMSR-099 | [identity.AuthenticationEvent](#identity-authenticationevent) | APPROVED | —
~~~~~~~~

<a id="hapi-412"></a>
## HAPI-412 — review identity HidraPrincipal

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HMSR-102

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review identity HidraPrincipal

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 115. HMSR-102 — identity.HidraPrincipal review

**Decision:** APPROVED  
**Review code:** HMSR-102  
**Dependency level:** 5  
**Bounded context:** identity  
**Confirmed upstream subject dependencies:** resolved `identity.User`; optional `identity.IdentityProvider` metadata  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject reference count:** 0

### 115.1 Semantic role

`HidraPrincipal` is the Identity-owned normalized authenticated principal used after LOCAL, LDAP/Active Directory, or OIDC authentication succeeds.

The completed authentication roadmap explicitly defines this normalized runtime model so all providers converge on one stable Hidra security identity before session creation, token issuance, and protected-route authorization.

It is runtime domain state, not a persistent aggregate/table.

### 115.2 Governing principal contract

The authentication roadmap defines the conceptual principal shape as:

```text
userId
username
displayName
authenticationType
identityProviderId
roles
permissions
```

The live `HidraPrincipal` record matches that shape exactly.

No missing or surplus field creates a semantic contradiction with the governing authentication contract.

### 115.3 Stable Hidra identity

The roadmap requires the stable authenticated identity to be the Hidra user ID rather than a mutable LDAP username, email, distinguished name, or raw OIDC subject.

The live model enforces non-blank `userId` and implements `Principal.getName()` using that stable identifier.

`username` is also required as the normalized Hidra username, while `displayName` remains optional presentation metadata.

This matches the provider-normalization contract.

### 115.4 Authentication-source semantics

`authenticationType` is mandatory and uses the existing Identity `ProviderType` taxonomy.

The authentication roadmap explicitly requires reuse of the established provider taxonomy rather than creating a second authentication-source enum.

`identityProviderId` remains optional because the normalized principal contract allows provider metadata only when applicable.

No second provider taxonomy or protocol duplicate is needed.

### 115.5 Authorization ownership

The authentication roadmap is explicit that authentication providers answer only who authenticated, while Hidra Identity remains authoritative for roles, permissions, scopes, policies, and business authorization decisions.

The live principal stores normalized `roles` and `permissions` as immutable sets.

Completed authentication evidence further proves that OIDC/LDAP external roles, groups, scopes, permissions, wildcard-like claims, and administrator claims do not directly become Hidra business authority; only Identity-owned effective permissions/roles are propagated.

This is the correct semantic boundary for `HidraPrincipal`.

### 115.6 Immutability and normalization

The live constructor:

- requires non-blank `userId`;
- requires non-blank `username`;
- requires non-null `authenticationType`;
- normalizes optional text;
- removes blank role/permission values; and
- exposes roles/permissions as unmodifiable sets.

These semantics are appropriate for a normalized authenticated-principal snapshot.

### 115.7 Application and security integration

The live authentication flow consistently uses `HidraPrincipal` across:

- LOCAL authentication;
- LDAP/Active Directory authentication;
- OIDC normalization;
- provider-neutral authentication completion;
- LoginSession creation;
- Hidra JWT issuance; and
- Spring Security principal propagation.

Repository remediation has already isolated REST/web contracts from directly exposing this domain model where unnecessary, while retaining `HidraPrincipal` at the Identity/security boundary where it is semantically appropriate.

### 115.8 Persistence classification

No `HidraPrincipalJpaEntity`, repository port, or Flyway table exists or is required.

That is intentional: the principal is reconstructed from authenticated Identity state and authorization resolution, then used as runtime security context.

HMSR-102 therefore does not invent persistence for `HidraPrincipal`.

### 115.9 Review conclusion

**APPROVED.** `HidraPrincipal` matches the completed authentication roadmap's normalized-principal contract, preserves stable Hidra user identity, uses the canonical provider taxonomy, carries only Identity-owned effective roles/permissions, and is correctly treated as immutable runtime security state rather than persisted business data.

No production correction obligation is recorded for HMSR-102.

HMSR-102 does not modify production Java, JPA, Flyway, API/application contracts, tests, Identity/platform data, or provisioned data.

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

- `d9f7cbd277a7849ec84fdb6cb4dde441198e180c` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:154](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L154)
- [docs/roadmap/model-semantic-review.md:297](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L297)
- [docs/roadmap/model-semantic-review.md:17915](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L17915)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:297 | HMSR-102 | 5 | identity | HidraPrincipal | — | identity.IdentityProvider, identity.User | 0 | 0 | 0 | APPROVED | Completed | docs(model-review): review identity HidraPrincipal
doc/domain/SEMANTIC_DECISIONS.md:154 | HMSR-102 | [identity.HidraPrincipal](#identity-hidraprincipal) | APPROVED | —
~~~~~~~~

<a id="hapi-413"></a>
## HAPI-413 — review identity LocalCredential

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HMSR-103

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review identity LocalCredential

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 116. HMSR-103 — identity.LocalCredential review

**Decision:** APPROVED  
**Review code:** HMSR-103  
**Dependency level:** 5  
**Bounded context:** identity  
**Confirmed upstream subject dependency:** mandatory `identity.User`  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject reference count:** 0

### 116.1 Semantic role

`LocalCredential` is the Identity-owned persisted credential record for LOCAL username/password authentication.

It stores only the one-way password hash and credential lifecycle metadata. Plaintext credentials remain transient authentication input and do not belong in the domain record, DTO persistence, logs, events, or database.

### 116.2 User ownership and cardinality

`userId` is mandatory and identifies the owning Hidra `User`.

The live domain rejects blank `userId`, the JPA mapping makes it non-null and unique, and the forward AUTH-009 migration enforces both:

```text
UNIQUE (user_id)
FOREIGN KEY (user_id) REFERENCES hidra_identity_user(id)
```

This correctly models one LOCAL credential row per Hidra user and provides same-module fail-closed persistence integrity.

### 116.3 Credential secret semantics

The authentication roadmap requires:

```text
one-way adaptive hash only
no plaintext password persistence
no reversible password encryption
no password in DTOs/events/logs
no production default password
```

The live `LocalCredential` contains only `passwordHash`; it does not contain a plaintext password field.

The domain has no dependency on Spring `PasswordEncoder`. Hashing remains behind the technical/application seam, which preserves domain isolation.

### 116.4 Runtime verification

The live `LocalAuthenticationProvider` loads the credential by `userId`, requires credential status `ACTIVE`, and verifies the submitted password using the configured `PasswordEncoder.matches(...)`.

It also enforces active/locked User state, resolves Hidra-owned authorization, and never falls back to an in-memory user store on ordinary LOCAL authentication failure.

This behavior matches the completed authentication roadmap.

### 116.5 Credential status

The persisted credential status remains a string and the current LOCAL provider recognizes `ACTIVE` as the usable state.

The authentication roadmap does not define a richer canonical credential-status taxonomy requiring a domain enum. HMSR-103 therefore does not invent additional lifecycle values or force an enum conversion.

### 116.6 Timestamp classification

`passwordChangedAt` is optional credential metadata.

`createdAt` and `updatedAt` are database-required audit timestamps. Repository invariant architecture classifies generic audit timestamps as persistence-only metadata rather than automatically promoting them into domain constructor invariants.

The current shape is therefore consistent with the established invariant-classification policy.

### 116.7 Authentication evidence

Completed AUTH-023 integration evidence uses PostgreSQL-backed `LocalCredential` data and the real LOCAL provider to prove persisted BCrypt-backed authentication and successful account-state update behavior.

Authentication failure/success outcome recording is handled separately through the existing User/AuthenticationEvent flow, keeping credential storage focused on credential state rather than duplicating authentication-event evidence.

### 116.8 Review conclusion

**APPROVED.** `LocalCredential` matches the completed authentication architecture: Identity owns the credential, User ownership is protected and one-to-one, only a one-way password hash is persisted, password verification remains in technical infrastructure, and ordinary LOCAL authentication uses the persisted credential fail-closed.

No production correction obligation is recorded for HMSR-103.

HMSR-103 does not modify production Java, JPA, Flyway, API/application contracts, tests, Identity data, or provisioned data.

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

- `69a6a917748e537a1ae2a7ba00f1b810aca76394` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:155](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L155)
- [docs/roadmap/model-semantic-review.md:298](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L298)
- [docs/roadmap/model-semantic-review.md:18025](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L18025)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:298 | HMSR-103 | 5 | identity | LocalCredential | — | identity.User | 0 | 0 | 0 | APPROVED | Completed | docs(model-review): review identity LocalCredential
doc/domain/SEMANTIC_DECISIONS.md:155 | HMSR-103 | [identity.LocalCredential](#identity-localcredential) | APPROVED | —
~~~~~~~~

<a id="hapi-414"></a>
## HAPI-414 — review identity LoginSession

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HMSR-104

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review identity LoginSession

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/LoginSessionStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/value/LoginSessionStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/LoginSessionStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 117. HMSR-104 — identity.LoginSession review

**Decision:** REVISE  
**Review code:** HMSR-104  
**Dependency level:** 5  
**Bounded context:** identity  
**Confirmed upstream subject dependencies:** mandatory `identity.User`; optional `identity.IdentityProvider`; optional external-identity trace reference  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject reference count:** 1

### 117.1 Semantic role

`LoginSession` is the Identity-owned logical authentication-session record.

The governing Identity DDD explicitly states that it stores session metadata only and must not store access tokens or refresh tokens. Token internals remain platform/security infrastructure concerns.

### 117.2 Mandatory session core

The DDD requires:

```text
id
userId
sessionType
startedAt
status
```

The live domain enforces `id`, `userId`, `startedAt`, and `status`.

However, the canonical `LoginSession` model contains no `sessionType` field at all.

That is a direct target-model mismatch. The future production model must preserve the DDD-required authentication/session protocol classification rather than infer it only indirectly from the provider at selected runtime operations.

### 117.3 Session-type vocabulary

The DDD defines the session-type vocabulary as:

```text
LOCAL
LDAP
OIDC
SAML2
OAUTH2
API_TOKEN
SYSTEM
```

This aligns with the existing `AuthenticationProtocol` technical enum.

Future production reconciliation should therefore reuse the established protocol vocabulary unless stronger repository evidence requires a distinct session enum. HMSR-104 does not invent a duplicate taxonomy.

### 117.4 User integrity

`userId` is mandatory and same-module.

HRA-111 already protects it with:

```text
hidra_identity_login_session.user_id
  -> hidra_identity_user.id
```

The domain also rejects blank `userId`.

No further basic User-existence correction is required.

### 117.5 Provider and external-identity linkage

`identityProviderId` and `externalIdentityId` are optional because LOCAL/system sessions may not require both.

The authentication roadmap explicitly describes the reused `LoginSession` as carrying:

```text
user/provider/external-identity linkage
```

The live session-start path copies `principal.identityProviderId()` but always passes:

```text
externalIdentityId = null
```

Repository search found no later assignment of `externalIdentityId` during authentication completion.

Therefore LDAP/OIDC/other external sessions can lose the exact external-identity linkage even when authentication resolved through an `ExternalIdentity`.

The future production flow should propagate the resolved external-identity identifier when one actually participated, while keeping the field optional for LOCAL/system paths.

### 117.6 End-of-session timestamp mismatch

The DDD defines optional:

```text
endedAt
```

as the session end time.

The live domain/JPA/table contain `lastSeenAt` but no `endedAt`.

Logout, revoke, and expiry operations currently call `saveWithStatus(..., Instant.now())`, which writes the supplied timestamp into `lastSeenAt`.

That conflates “last activity observed” with “session ended at” and does not preserve the DDD's explicit end-time semantic.

Future production reconciliation must add/preserve an explicit `endedAt` lifecycle timestamp while retaining `lastSeenAt` only if the repository still needs activity tracking as a separate concern.

### 117.7 Expiry semantics

`expiresAt` is optional in the DDD.

The provider-neutral live session-start service currently requires a non-null expiry strictly after the start time because the current Hidra-issued JWT/session completion path always supplies an expiry.

That stronger application-path requirement does not require changing the DDD field to globally mandatory; other future session types such as SYSTEM/API_TOKEN may have different policies.

HMSR-104 therefore records no model defect for the optional DDD declaration itself.

### 117.8 Lifecycle status

`LoginSessionStatus` contains:

```text
ACTIVE
EXPIRED
REVOKED
LOGGED_OUT
```

This exactly matches the Identity DDD and remains an appropriate technical lifecycle enum.

No catalog conversion is required.

### 117.9 Token separation

The live model contains no access token, refresh token, token hash, or raw bearer material.

The application lifecycle service also explicitly operates without storing token values.

This correctly satisfies the Identity DDD boundary.

### 117.10 Required revisions

Three evidence-backed production obligations remain:

1. **Add/preserve the DDD-required `sessionType` on the canonical LoginSession model and persistence contract**, reusing the established authentication-protocol vocabulary rather than inventing an unnecessary taxonomy.
2. **Propagate `externalIdentityId` for externally authenticated sessions when an ExternalIdentity actually participated**, preserving optionality for LOCAL/system paths.
3. **Add/preserve an explicit `endedAt` lifecycle timestamp and stop using `lastSeenAt` as a substitute for session termination time**; keep activity tracking separate if still required.

HMSR-104 does not modify production Java, JPA, Flyway, API/application contracts, tests, Identity data, or provisioned data.

### 117.11 Review conclusion

**REVISE.** `LoginSession` has correct Identity ownership, protected mandatory User linkage, correct status vocabulary, and proper token-free session semantics.

The target baseline cannot mark it APPROVED while the DDD-required `sessionType` is absent, externally authenticated sessions do not preserve their resolved external-identity linkage, and the DDD's explicit `endedAt` semantic is missing while lifecycle transitions overwrite `lastSeenAt`.

HMS reconciliation must retain these obligations until explicitly authorized Identity production corrections resolve them or stronger repository evidence changes the governing Identity contract.

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

- `c344d915b1b96d9af7ed38176b388190b4448dff` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:156](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L156)
- [docs/roadmap/model-semantic-review.md:299](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L299)
- [docs/roadmap/model-semantic-review.md:18107](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L18107)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:299 | HMSR-104 | 5 | identity | LoginSession | — | identity.IdentityProvider, identity.User | 0 | 0 | 1 | REVISE | Completed | docs(model-review): review identity LoginSession
doc/domain/SEMANTIC_DECISIONS.md:156 | HMSR-104 | [identity.LoginSession](#identity-loginsession) | REVISE | HMR-087
~~~~~~~~

<a id="hapi-556"></a>
## HAPI-556 — HPR-P25-044 historical bounded obligation

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HPR-P25-044

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

HPR-P25-044 historical bounded obligation

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [doc/model-remediation/RECONCILIATION.md:16610](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L16610)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6135](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6135)
- [doc/roadmap/ULTIMATE_ROADMAP.md:9827](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L9827)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DESIGN COMPLETE.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:6135 | HPR-P25-044 | DESIGN COMPLETE | Future runtime assigned 050–058; design is not executable completion
doc/roadmap/ULTIMATE_ROADMAP.md:9827 | HPR-P25-044 | Completed design — Docs #255 SUCCESS | Accepted provenance policy; runtime not established
doc/model-remediation/RECONCILIATION.md:16610 | HPR-P25-044 | Completed design — Docs #255 SUCCESS | Accepted provenance policy; runtime not established
~~~~~~~~

<a id="hapi-557"></a>
## HAPI-557 — HPR-P25-045 historical bounded obligation

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HPR-P25-045

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

HPR-P25-045 historical bounded obligation

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [doc/roadmap/ULTIMATE_ROADMAP.md:6136](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6136)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DESIGN COMPLETE.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:6136 | HPR-P25-045 | DESIGN COMPLETE | Future runtime assigned 050–058; design is not executable completion
~~~~~~~~

<a id="hapi-558"></a>
## HAPI-558 — HPR-P25-046 historical bounded obligation

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HPR-P25-046

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

HPR-P25-046 historical bounded obligation

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [doc/roadmap/ULTIMATE_ROADMAP.md:6137](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6137)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:6137 | HPR-P25-046 | COMPLETED / ACCEPTED (recorded bounded scope) | 049 directly confirmed #666/#266; other accepted subsets retain original source/CI limits
~~~~~~~~

<a id="hapi-559"></a>
## HAPI-559 — Existing frozen seven-file task

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HPR-P25-047

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Existing frozen seven-file task

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/database-dictionary-ownership.json`
- `doc/database/DATA_DICTIONARY.md`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProviderLinkSecurityGenerationPostgresIntegrationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProviderLinkSecurityGenerationPostgresIntegrationTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/database-dictionary-ownership.json`
- `doc/database/DATA_DICTIONARY.md`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProviderLinkSecurityGenerationPostgresIntegrationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-047 — Existing frozen seven-file task

The complete original registration and acceptance requirements below remain binding.
Blockers 048/049 are resolved by accepted #665/#666; 047 is now COMPLETED / CI ACCEPTED
at c1f25af, exact-head Production #668 and Docs #268 SUCCESS. Exact CREATE: the registered generation SQL and
`src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProviderLinkSecurityGenerationPostgresIntegrationTest.java`.
Exact UPDATE: the registered LOCAL generation test, `.github/database-dictionary-ownership.json`,
`doc/database/DATA_DICTIONARY.md`, this roadmap and reconciliation. DELETE none.
All production Java remains excluded. Preserve per-field generation/neutral classifications,
native/bulk/ORM/COPY/MERGE/UPSERT proofs, immutable IDs, overflow, FK/uniqueness, A/B/A,
concurrency, rollback and JSONB saves. The bounded LOCAL-test inventory/prefix correction
is already authorized; 049 preserves future JSONB full-chain compatibility.

Focused command remains exactly the seven-class registration selection below. DoD is
actual fresh/upgrade PostgreSQL16/JPA proof and authentic dictionary plus exact-head
full Production/Docs SUCCESS. Migration failure rolls back without partial guards; old
application rollback retains additive generated columns/guards. Generation transport,
bound token/session issuance and B2/B3 are 050/051, never bundled into 047.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProviderLinkSecurityGenerationPostgresIntegrationTest.java` — exact-head oracle suite 13 cases, zero failures/errors/skips.

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

- [doc/roadmap/ULTIMATE_ROADMAP.md:5011](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L5011)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6138](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6138)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / CI ACCEPTED.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:6138 | HPR-P25-047 | COMPLETED / CI ACCEPTED | Production #668 and Docs #268 SUCCESS at c1f25af; generation transport remains later
~~~~~~~~

<a id="hapi-560"></a>
## HAPI-560 — HPR-P25-048 historical bounded obligation

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HPR-P25-048

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

HPR-P25-048 historical bounded obligation

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [doc/roadmap/ULTIMATE_ROADMAP.md:6139](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6139)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:6139 | HPR-P25-048 | COMPLETED / ACCEPTED (recorded bounded scope) | 049 directly confirmed #666/#266; other accepted subsets retain original source/CI limits
~~~~~~~~

<a id="hapi-561"></a>
## HAPI-561 — HPR-P25-049 historical bounded obligation

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HPR-P25-049

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

HPR-P25-049 historical bounded obligation

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [doc/roadmap/ULTIMATE_ROADMAP.md:6140](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6140)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:6140 | HPR-P25-049 | COMPLETED / ACCEPTED (recorded bounded scope) | 049 directly confirmed #666/#266; other accepted subsets retain original source/CI limits
~~~~~~~~

<a id="hapi-562"></a>
## HAPI-562 — Identity binding, issuance and operation reader

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HPR-P25-050

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Identity binding, issuance and operation reader

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/identity/application/model/AuthenticatedPrincipalInput.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/IssuedAccessToken.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/TopologyCurrentActorQueryAdapter.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/TopologyCurrentActorQueryAdapter.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/identity/application/model/AuthenticatedPrincipalInput.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/IssuedAccessToken.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/TopologyCurrentActorQueryAdapter.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-050 — Identity binding, issuance and operation reader

Source: accepted 044/045 policy; current `AuthenticationCompletionApplicationService`,
`AuthenticationSessionLifecycleApplicationService`, `AuthenticatedPrincipalInput`,
`IssuedAccessToken`, `HidraAccessTokenIssuer`, LOCAL/LDAP/OIDC provider paths and
`TopologyCurrentActorQueryAdapter`. Current token issuance precedes session save and
does not persist the selected immutable operation association. 046/047 generations alone
do not establish trustworthy transport or an authenticated session association.

Deliverable: the accepted `hidra_identity_operation_binding` format and DB tuple guards;
trusted monotonic version reads at successful authentication/completion; atomic logical
session + immutable issuer/jti binding issuance; Identity-owned exact operation reader;
session termination/immutable-field compatibility. LOCAL, LDAP, AD and OIDC provenance
and native audience/issuer/sub/jti/deadline agreement must follow 044/045 exactly.
Capture credentials/provider/link identity/generation from real trusted paths, not caller
JSON, profile snapshots, roles or correlationId. No retrospective binding of old tokens;
compatibility behavior for ordinary routes stays explicit, protected operations fail closed.

D1 closes exact new model/port/JDBC/migration/test paths and enumerates every affected
existing command/provider/session/issuer/reader/test file. Candidate UPDATE anchors are
the exact sources above, not authority to mutate them yet. CREATE list unresolved at D1;
UPDATE list unresolved at D1; DELETE none. Own immutable binding plus restrictive FKs,
canonical UUID and exact issuer/audience/time/nullability tuple validation, append/replay/
conflict and anti-mutation guards. Existing mapped sessions must not be merged into a
state that invalidates immutable binding evidence. No retention period invented.

Tests: authenticated paths for all four provider types, signature/issuer/audience/jti/sub
mismatch, expired/assertion-capped deadlines, ended/changed sessions, credential/provider/
link A/B/A, disabled/reactivated Users, duplicate same/different binding, native writer
tuple rejection, malformed storage, SQL NULL branches, deletion/truncate rejection,
completion/save/token failures and same transaction rollback. API regression covers
native result shape, legacy unbound-token compatibility boundary and denial without leak.
DoD: B1 real owner/SQL/JPA proof + common full gates; B2/B3 remain 051. Fail issuance
atomically; never deliver a token claiming a committed binding after failed commit.

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

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/roadmap/ULTIMATE_ROADMAP.md:5030](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L5030)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Source-backed bounded stage accepted; current exact-SHA raw regression reports independently recovered. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-563"></a>
## HAPI-563 — add trusted authentication binding foundations

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HPR-P25-050A

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Trusted binding foundations and private verification receipts

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/ci-test-inventory.json`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/topology/TopologyOperationAuthenticationContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/AccessTokenIssuanceContext.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/AccessTokenIssuerSettings.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/AuthenticatedPrincipalInput.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/AuthenticationVerificationSnapshot.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/LocalCredentialVerificationState.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedDirectAuthenticationResult.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthenticationSecurityStateQueryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/OperationAuthenticationBindingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/TrustedAuthenticationVerificationPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/OperationAuthenticationBinding.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraOidcAuthenticationToken.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityAuthenticationClockConfiguration.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationRegistry.java`
- `src/test/java/dz/sh/hidra/modules/identity/AuthenticationArchitectureAndSecretGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/OperationAuthenticationBoundaryGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAuthorizationOwnershipTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/OperationAuthenticationBinding.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthenticationSecurityStateQueryPort.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/out/OperationAuthenticationBindingRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/out/TrustedAuthenticationVerificationPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraOidcAuthenticationToken.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityAuthenticationClockConfiguration.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationRegistry.java`, `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAuthorizationOwnershipTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/ci-test-inventory.json`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/topology/TopologyOperationAuthenticationContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/AccessTokenIssuanceContext.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/AccessTokenIssuerSettings.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/AuthenticatedPrincipalInput.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/AuthenticationVerificationSnapshot.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/LocalCredentialVerificationState.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedDirectAuthenticationResult.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthenticationSecurityStateQueryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/OperationAuthenticationBindingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/TrustedAuthenticationVerificationPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/OperationAuthenticationBinding.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraOidcAuthenticationToken.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityAuthenticationClockConfiguration.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationRegistry.java`
- `src/test/java/dz/sh/hidra/modules/identity/AuthenticationArchitectureAndSecretGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/OperationAuthenticationBoundaryGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAuthorizationOwnershipTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-050A — Trusted binding foundations and private verification receipts

Exact scope: 14 CREATE / 7 UPDATE; DELETE none.

| Action | Exact path |
|---|---|
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/domain/model/OperationAuthenticationBinding.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/application/model/AuthenticationVerificationSnapshot.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/application/model/LocalCredentialVerificationState.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedDirectAuthenticationResult.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/application/model/AccessTokenIssuerSettings.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/application/model/AccessTokenIssuanceContext.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/application/port/out/OperationAuthenticationBindingRepositoryPort.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthenticationSecurityStateQueryPort.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/application/port/out/TrustedAuthenticationVerificationPort.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/application/contract/topology/TopologyOperationAuthenticationContract.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationRegistry.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityAuthenticationClockConfiguration.java` |
| CREATE | `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationTest.java` |
| CREATE | `src/test/java/dz/sh/hidra/modules/identity/OperationAuthenticationBoundaryGuardrailTest.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/application/model/AuthenticatedPrincipalInput.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraOidcAuthenticationToken.java` |
| UPDATE | `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAuthorizationOwnershipTest.java` |
| UPDATE | `src/test/java/dz/sh/hidra/modules/identity/AuthenticationArchitectureAndSecretGuardrailTest.java` |
| UPDATE | `.github/ci-test-inventory.json` |
| UPDATE | `doc/roadmap/ULTIMATE_ROADMAP.md` |
| UPDATE | `doc/model-remediation/RECONCILIATION.md` |

Acceptance: validated immutable format-one values, strict Unicode/native byte and
PostgreSQL-representable time/null branches, defensive tag copies/redaction, exact
invocation-bound one-use receipts, consumed-origin attestation, RFC5869 vectors and
canonical tuple authentication, transaction-enforced after-commit publication, failure
cleanup and constructor/DTO/details/roles/correlation rejection. Public old input/OIDC
constructors remain available but create no trusted receipt. New models/ports/registry
are dormant foundations: existing providers/completion/issuance and routes are unchanged;
no SQL, active bound completion or reader adapter is installed in A. Guardrails allow
only the three registered future receipt producers; zero production callers at A is
expected, not proof of successful provider authentication. The JDK-only contract contains
no raw token, tag/key/hash/claims/PERMIT or callback authority. Unit/mocked transaction
checks are reported as such, never as actual commit/PostgreSQL or four-provider proof.

Focused tests: `TrustedAuthenticationVerificationTest`,
`OperationAuthenticationBoundaryGuardrailTest`, `HidraAuthorizationOwnershipTest`,
`AuthenticationArchitectureAndSecretGuardrailTest` and retained original-assertion tests.
Required full gates additionally establish no regression in unchanged runtime behavior.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/identity/AuthenticationArchitectureAndSecretGuardrailTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/OperationAuthenticationBoundaryGuardrailTest.java` — exact-head oracle suite 13 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAuthorizationOwnershipTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationTest.java` — exact-head oracle suite 38 cases, zero failures/errors/skips.

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

- `7f62f72f232707d547c2038dbb0f7110834d2a75` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:19468](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L19468)
- [doc/model-remediation/RECONCILIATION.md:19490](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L19490)
- [doc/roadmap/ULTIMATE_ROADMAP.md:12454](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L12454)
- [doc/roadmap/ULTIMATE_ROADMAP.md:12476](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L12476)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Source-backed bounded stage accepted; current exact-SHA raw regression reports independently recovered. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:12454 | HPR-P25-050A | Trusted binding foundations and private verification receipts | accepted 044/045/046/047/049 and this amendment | COMPLETED / CI ACCEPTED | feat(identity): add trusted authentication binding foundations
doc/model-remediation/RECONCILIATION.md:19468 | HPR-P25-050A | Trusted binding foundations and private verification receipts | accepted 044/045/046/047/049 and this amendment | PLANNED / NEXT AFTER AMENDMENT ACCEPTANCE | feat(identity): add trusted authentication binding foundations
~~~~~~~~

<a id="hapi-564"></a>
## HAPI-564 — persist immutable authentication bindings

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HPR-P25-050B

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Fresh-schema binding storage and session guards

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/ci-test-inventory.json`
- `.github/database-dictionary-ownership.json`
- `doc/database/DATA_DICTIONARY.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcAuthenticationSecurityStateQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcOperationAuthenticationBindingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLoginSessionRepositoryAdapter.java`
- `src/main/resources/db/migration/V20261013_001__p25_identity_operation_binding.sql`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/OperationAuthenticationBindingPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcAuthenticationSecurityStateQueryAdapter.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcOperationAuthenticationBindingRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLoginSessionRepositoryAdapter.java`, `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/OperationAuthenticationBindingPostgresIntegrationTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/ci-test-inventory.json`
- `.github/database-dictionary-ownership.json`
- `doc/database/DATA_DICTIONARY.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcAuthenticationSecurityStateQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcOperationAuthenticationBindingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLoginSessionRepositoryAdapter.java`
- `src/main/resources/db/migration/V20261013_001__p25_identity_operation_binding.sql`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/OperationAuthenticationBindingPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-050B — Fresh-schema binding storage and session guards

Exact scope: 4 CREATE / 7 UPDATE; DELETE none.

| Action | Exact path |
|---|---|
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcOperationAuthenticationBindingRepositoryAdapter.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcAuthenticationSecurityStateQueryAdapter.java` |
| CREATE | `src/main/resources/db/migration/V20261013_001__p25_identity_operation_binding.sql` |
| CREATE | `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/OperationAuthenticationBindingPostgresIntegrationTest.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLoginSessionRepositoryAdapter.java` |
| UPDATE | `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java` |
| UPDATE | `.github/database-dictionary-ownership.json` |
| UPDATE | `doc/database/DATA_DICTIONARY.md` |
| UPDATE | `.github/ci-test-inventory.json` |
| UPDATE | `doc/roadmap/ULTIMATE_ROADMAP.md` |
| UPDATE | `doc/model-remediation/RECONCILIATION.md` |

Acceptance: one newly reserved `V20261013_001__p25_identity_operation_binding.sql`
at Flyway 20261013.001 (refresh collision/inventory before creation); actual PostgreSQL16
fresh-schema application of every inventoried migration with no pending/failed entries.
Use the previously registered 21 columns including the private 32-byte producer tag,
constraints, indexes, three functions, eight triggers and fixed owner lock order.
Snapshot generations are not version-pair FKs. Binding insert/replay/conflict checks
use fresh owner rows; UPDATE/DELETE/TRUNCATE reject. Bound-session immutable/terminal
guards and wrong-isolation rejection apply; session-table TRUNCATE rejects unconditionally.
Real COPY/MERGE/native/JPA mutation, same/different replay, NULL/type/finite-time/byte
boundaries and independent-backend observed lock/barrier tests run on synthetic fixtures.
No sleeps as race proof, no trigger/role/replication escape, no deployed-data upgrade
proof. SQL tuple validity alone is not trusted producer origin; A's authenticated tag
is necessary and C/D establish production-path provenance and reader validation.

JPA session adapter uses flush/fresh locking where registered; current ordinary synthetic
row lifecycle behavior remains covered. Actual current physical dictionary and Identity
ownership are captured/rendered/checked through the existing catalog policy; there is
no simulated catalog or generator-policy waiver. If the required physical environment is
unavailable, record the exact limitation; do not mark B accepted or manufacture metadata.
There is still no bound completion/reader or new protected API after this stage.

Focused tests: `OperationAuthenticationBindingPostgresIntegrationTest`,
`LoginSessionSemanticRemediationTest`, `LoginSessionIntegrityMigrationTest` and retained
046/047/049 PostgreSQL proofs. This stage's binding test owns session append/mutation/
delete/TRUNCATE/isolation races; D adds the reader-specific races and current-state proof.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/OperationAuthenticationBindingPostgresIntegrationTest.java` — exact-head oracle suite 37 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionIntegrityMigrationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.

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

- `04d6fbc6f290873b1b25cbda8f7860c582568ea0` — exact registered-message commit; surviving paths matched to current tree.
- `778dd05c422ae655322bdc8232e7a5eeefe86875` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:19469](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L19469)
- [doc/model-remediation/RECONCILIATION.md:19536](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L19536)
- [doc/roadmap/ULTIMATE_ROADMAP.md:12455](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L12455)
- [doc/roadmap/ULTIMATE_ROADMAP.md:12522](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L12522)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Source-backed bounded stage accepted; current exact-SHA raw regression reports independently recovered. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:12455 | HPR-P25-050B | Fresh-schema binding storage and session guards | CI-accepted HPR-P25-050A | COMPLETED / CI ACCEPTED | feat(identity): persist immutable authentication bindings
doc/model-remediation/RECONCILIATION.md:19469 | HPR-P25-050B | Fresh-schema binding storage and session guards | CI-accepted HPR-P25-050A | BLOCKED / PREDECESSOR NOT ACCEPTED | feat(identity): persist immutable authentication bindings
~~~~~~~~

<a id="hapi-565"></a>
## HAPI-565 — complete trusted bound authentication

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HPR-P25-050C

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Real provider verification and atomic bound issuance

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/ci-test-inventory.json`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/IssuedAccessToken.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedDirectoryIdentity.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AccessTokenIssuerPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/DirectAuthenticationPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapCredentialVerificationAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringDirectAuthenticationAdapter.java`
- `src/test/java/dz/sh/hidra/modules/identity/OperationAuthenticationBoundaryGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/BoundAuthenticationCompletionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/AuthorizationAssertionTrustTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverterTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProviderTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapCredentialVerificationAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProviderTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationTest.java`
- `src/test/java/dz/sh/hidra/platform/configuration/HidraAdministratorJwtAuthorizationTest.java`
- `src/test/java/dz/sh/hidra/platform/configuration/HidraUnifiedJwtCompatibilityTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AccessTokenIssuerPort.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/out/DirectAuthenticationPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverter.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProvider.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapCredentialVerificationAdapter.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/ci-test-inventory.json`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/IssuedAccessToken.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedDirectoryIdentity.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AccessTokenIssuerPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/DirectAuthenticationPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapCredentialVerificationAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringDirectAuthenticationAdapter.java`
- `src/test/java/dz/sh/hidra/modules/identity/OperationAuthenticationBoundaryGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/BoundAuthenticationCompletionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/AuthorizationAssertionTrustTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverterTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProviderTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapCredentialVerificationAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProviderTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationTest.java`
- `src/test/java/dz/sh/hidra/platform/configuration/HidraAdministratorJwtAuthorizationTest.java`
- `src/test/java/dz/sh/hidra/platform/configuration/HidraUnifiedJwtCompatibilityTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-050C — Real provider verification and atomic bound issuance

Exact scope: 1 CREATE / 22 UPDATE; DELETE none.

| Action | Exact path |
|---|---|
| CREATE | `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/BoundAuthenticationCompletionPostgresIntegrationTest.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/application/model/IssuedAccessToken.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/application/port/out/DirectAuthenticationPort.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AccessTokenIssuerPort.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProvider.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverter.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringDirectAuthenticationAdapter.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java` |
| UPDATE | `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProviderTest.java` |
| UPDATE | `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProviderTest.java` |
| UPDATE | `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverterTest.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedDirectoryIdentity.java` |
| UPDATE | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapCredentialVerificationAdapter.java` |
| UPDATE | `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapCredentialVerificationAdapterTest.java` |
| UPDATE | `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationTest.java` |
| UPDATE | `src/test/java/dz/sh/hidra/modules/identity/OperationAuthenticationBoundaryGuardrailTest.java` |
| UPDATE | `.github/ci-test-inventory.json` |
| UPDATE | `doc/roadmap/ULTIMATE_ROADMAP.md` |
| UPDATE | `doc/model-remediation/RECONCILIATION.md` |

Acceptance: actual successful LOCAL/LDAP/AD/OIDC producers supply private receipts.
LOCAL verifies the atomic captured credential hash/id/generation, with provider generation
captured beforehand. Directory uses actual selected LDAP/AD type/configuration and exact
authenticated-entry subject, followed by atomic link/User/generation capture. The verifier
must transport the authenticated entry directly; normalized display/profile attributes
cannot become authority. OIDC captures exact provider/link images before final qualified
external decoding of the original bounded bearer and checks strict raw identities/deadline;
existing original-assertion limits/semantics remain. No forged Jwt or public DTO is proof.

Through the real Spring proxy, REQUIRED READ_COMMITTED non-read-only completion generates
chosen IDs and one whole-second interval, floors the OIDC-capped expiry, flushes the session,
signs and cryptographically reads back exact raw issuer/audience/sub/jti/type/provider/iat/
exp, appends binding and non-LOCAL event in one physical owner transaction. Persistent
attestation is minted only from the consumed producer receipt. Token/receipt delivery is
after successful commit; save/sign/append/event/commit faults roll back and expose none.
LOCAL credential-verification outcome keeps its separate established meaning. No fallback
unbound success on verification failure, no automatic token retry or secret diagnostics.
Input/output token bounds remain configurable at the accepted default 65,536 bytes.

Real PostgreSQL/JPA/JDBC, test-key native signatures, after-commit callbacks, same backend/
transaction observations, generation A/B/A and fault rollback are required. A deterministic
directory port is a synthetic verifier, not a live deployment claim. Existing public API
result/claims and ordinary route behavior remain; B1 reader is not yet installed and B2/B3
are still unimplemented. Receipt/guardrail tests now require the exact three real producer
call sites and retain A's denial/vectors/canonical-frame tests.

Focused tests: `BoundAuthenticationCompletionPostgresIntegrationTest`,
`LocalAuthenticationIntegrationTest`, `LocalAuthenticationProviderTest`,
`LdapAuthenticationProviderTest`, `LdapCredentialVerificationAdapterTest`,
`IdentityOidcJwtAuthenticationConverterTest`, `AuthorizationAssertionTrustTest`,
all three retained authentication-controller API tests, `HidraUnifiedJwtCompatibilityTest`,
`HidraAdministratorJwtAuthorizationTest` and A's receipt/guardrail tests.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/identity/OperationAuthenticationBoundaryGuardrailTest.java` — exact-head oracle suite 13 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/BoundAuthenticationCompletionPostgresIntegrationTest.java` — exact-head oracle suite 22 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/AuthorizationAssertionTrustTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverterTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProviderTest.java` — exact-head oracle suite 9 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapCredentialVerificationAdapterTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationIntegrationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProviderTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationTest.java` — exact-head oracle suite 38 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/platform/configuration/HidraAdministratorJwtAuthorizationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/platform/configuration/HidraUnifiedJwtCompatibilityTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `3facf12aef110cb33f0b896398c9b1cee51af5f3` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:19470](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L19470)
- [doc/model-remediation/RECONCILIATION.md:19580](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L19580)
- [doc/roadmap/ULTIMATE_ROADMAP.md:12456](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L12456)
- [doc/roadmap/ULTIMATE_ROADMAP.md:12566](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L12566)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Source-backed bounded stage accepted; current exact-SHA raw regression reports independently recovered. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:12456 | HPR-P25-050C | Real provider verification and atomic bound issuance | CI-accepted HPR-P25-050B | COMPLETED / CI ACCEPTED | feat(identity): complete trusted bound authentication
doc/model-remediation/RECONCILIATION.md:19470 | HPR-P25-050C | Real provider verification and atomic bound issuance | CI-accepted HPR-P25-050B | BLOCKED / PREDECESSOR NOT ACCEPTED | feat(identity): complete trusted bound authentication
~~~~~~~~

<a id="hapi-566"></a>
## HAPI-566 — bind protected operations to trusted authentication

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HPR-P25-050D

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Bound-operation reader and integrated B1 acceptance

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/ci-test-inventory.json`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/OperationAuthenticationQueryApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/TopologyOperationAuthenticationQueryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/identity/OperationAuthenticationBoundaryGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/OperationAuthenticationQueryPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/OperationAuthenticationQueryApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/TopologyOperationAuthenticationQueryAdapter.java`, `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/OperationAuthenticationQueryPostgresIntegrationTest.java`, `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/ci-test-inventory.json`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/OperationAuthenticationQueryApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/TopologyOperationAuthenticationQueryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/identity/OperationAuthenticationBoundaryGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/OperationAuthenticationQueryPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-050D — Bound-operation reader and integrated B1 acceptance

Exact scope: 3 CREATE / 5 UPDATE; DELETE none.

| Action | Exact path |
|---|---|
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/application/service/OperationAuthenticationQueryApplicationService.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/TopologyOperationAuthenticationQueryAdapter.java` |
| CREATE | `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/OperationAuthenticationQueryPostgresIntegrationTest.java` |
| UPDATE | `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationTest.java` |
| UPDATE | `src/test/java/dz/sh/hidra/modules/identity/OperationAuthenticationBoundaryGuardrailTest.java` |
| UPDATE | `.github/ci-test-inventory.json` |
| UPDATE | `doc/roadmap/ULTIMATE_ROADMAP.md` |
| UPDATE | `doc/model-remediation/RECONCILIATION.md` |

Acceptance: argument-free Identity-owned B1 reader resolves the actual ambient bearer
through the qualified native decoder, enforces strict signed raw types/UUIDs/issuer/
audience/sub/jti/provider/iat/exp and validates private persistent producer authentication
before returning eligible evidence. In-memory identity requires its actual producer-owned
after-commit receipt. One joined owner query reads current HUMAN/active/unlocked User,
provider/link/credential generations, exact session/binding tuple and real deadlines.
Provider/link/credential A/B/A, disabled/locked/ended/expired/unbound contexts deny without
identity leakage; zero/copied/arbitrary-tag legacy binding forgeries or corrupt tuples
cannot authenticate. Storage/decoder infrastructure corruption is an operational error,
not a successful absence. No decoder skew or second lookup extends expiry. This is
point-in-time B1, not B2/B3 authorization or transaction-valid permission evidence.

`OperationAuthenticationQueryPostgresIntegrationTest` supplies real signature/current-state/
corruption/expiry/legacy and reader-versus-native/JPA writer race proof, complementing B's
storage races and C's four-provider/commit proof. Old Topology actor, pair, permission,
evaluator and write paths remain read-only. The complete original focused regression
command plus the directory-verifier tests, all five new classes, six Python policy
suites, actual dictionary, canonical docs/offline OpenAPI/P1 consistency, exact scope and
existing full same-SHA gates must pass before parent 050 acceptance. Do not replace this
with A's model smoke checks or an intermediate child's green CI.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/identity/OperationAuthenticationBoundaryGuardrailTest.java` — exact-head oracle suite 13 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/OperationAuthenticationQueryPostgresIntegrationTest.java` — exact-head oracle suite 26 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TrustedAuthenticationVerificationTest.java` — exact-head oracle suite 38 cases, zero failures/errors/skips.

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

- `81d46bcb8bc8821f67eafbb26cd1279f6d091457` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:19471](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L19471)
- [doc/model-remediation/RECONCILIATION.md:19643](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L19643)
- [doc/roadmap/ULTIMATE_ROADMAP.md:12457](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L12457)
- [doc/roadmap/ULTIMATE_ROADMAP.md:12629](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L12629)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Source-backed bounded stage accepted; current exact-SHA raw regression reports independently recovered. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:12457 | HPR-P25-050D | Bound-operation reader and integrated B1 acceptance | CI-accepted HPR-P25-050C | COMPLETED / CI ACCEPTED | feat(identity): bind protected operations to trusted authentication
doc/model-remediation/RECONCILIATION.md:19471 | HPR-P25-050D | Bound-operation reader and integrated B1 acceptance | CI-accepted HPR-P25-050C | BLOCKED / PREDECESSOR NOT ACCEPTED | feat(identity): bind protected operations to trusted authentication
~~~~~~~~

<a id="hapi-660"></a>
## HAPI-660 — isolate authentication web contract

Phase: HPS-001 — Identity and Authentication

Legacy IDs: HRA-101

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Adapt Spring principal/session details into application-level authentication inputs/results.

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/repository-remediation.md`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityAuthenticationWebMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/AuthenticatedPrincipalInput.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/AuthenticationResult.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CompleteAuthenticatedPrincipalUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLdapApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLocalApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerOidcApiTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CompleteAuthenticatedPrincipalUseCase.java`.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/repository-remediation.md`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityAuthenticationWebMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/AuthenticatedPrincipalInput.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/AuthenticationResult.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CompleteAuthenticatedPrincipalUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLdapApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLocalApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerOidcApiTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLdapApiTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLocalApiTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerOidcApiTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `8217df88f26f597d60b70955a289840c33c37d1c` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/repository-remediation.md:345](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/repository-remediation.md#L345)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/repository-remediation.md:345 | HRA-101 | refactor(identity): isolate authentication web contract | Adapt Spring principal/session details into application-level authentication inputs/results. | Authentication controller no longer exposes unnecessary domain representation. | HRA-002 | **Completed** — introduced application-owned `AuthenticatedPrincipalInput` and flattened `AuthenticationResult`; direct and OIDC completion use cases now exchange those contracts instead of `HidraPrincipal`/`LoginSession`; `IdentityAuthenticationWebMapper` owns Spring-injected principal adaptation and REST-result mapping; the authentication controller no longer imports Identity domain models; LOCAL/LDAP/AD/OIDC API tests preserve the existing response and provider-selection behavior.
~~~~~~~~

<a id="hapi-667"></a>
## HAPI-667 — add identity roadmap

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-001

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Create execution memory

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/CreateUserRequest.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/command/CreateUserCommand.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CreateUserUseCase.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CreateUserUseCase.java`.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/CreateUserRequest.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/command/CreateUserCommand.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CreateUserUseCase.java`

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:162](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L162)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:162 | ID-001 | docs(identity): add identity roadmap | Create execution memory
~~~~~~~~

<a id="hapi-668"></a>
## HAPI-668 — add identity package skeleton

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-002

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Production package boundaries

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:163](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L163)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:163 | ID-002 | chore(identity): add identity package skeleton | Production package boundaries
~~~~~~~~

<a id="hapi-669"></a>
## HAPI-669 — add identity domain value objects

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-003

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

IDs, username, email, codes, names, statuses, references

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:164](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L164)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:164 | ID-003 | feat(identity): add identity domain value objects | IDs, username, email, codes, names, statuses, references
~~~~~~~~

<a id="hapi-673"></a>
## HAPI-673 — add identity domain exceptions

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-007

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Domain-specific failures

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/identity/domain/exception/IdentityDomainException.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/configuration/IdentityDomainServiceConfiguration.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/exception/IdentityDomainException.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/configuration/IdentityDomainServiceConfiguration.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/identity/domain/exception/IdentityDomainException.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/configuration/IdentityDomainServiceConfiguration.java`

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:168](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L168)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:168 | ID-007 | feat(identity): add identity domain exceptions | Domain-specific failures
~~~~~~~~

<a id="hapi-675"></a>
## HAPI-675 — add identity domain policies and services

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-009

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Assignment/evaluation/SoD policies

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:170](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L170)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:170 | ID-009 | feat(identity): add identity domain policies and services | Assignment/evaluation/SoD policies
~~~~~~~~

<a id="hapi-676"></a>
## HAPI-676 — add application commands and queries

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-010

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Framework-neutral use-case inputs

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:171](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L171)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:171 | ID-010 | feat(identity): add application commands and queries | Framework-neutral use-case inputs
~~~~~~~~

<a id="hapi-677"></a>
## HAPI-677 — add application ports and DTOs

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-011

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Inbound/outbound contracts

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:172](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L172)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:172 | ID-011 | feat(identity): add application ports and DTOs | Inbound/outbound contracts
~~~~~~~~

<a id="hapi-678"></a>
## HAPI-678 — add application services

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-012

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Use-case orchestration

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:173](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L173)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:173 | ID-012 | feat(identity): add application services | Use-case orchestration
~~~~~~~~

<a id="hapi-679"></a>
## HAPI-679 — add persistence entities and repositories

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-013

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

JPA adapters and migration

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:174](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L174)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:174 | ID-013 | feat(identity): add persistence entities and repositories | JPA adapters and migration
~~~~~~~~

<a id="hapi-680"></a>
## HAPI-680 — add infrastructure adapters and configuration

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-014

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Password/event/configuration adapters

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LocalPasswordHashPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringLocalPasswordHashAdapter.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LocalPasswordHashPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringLocalPasswordHashAdapter.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LocalPasswordHashPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringLocalPasswordHashAdapter.java`

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:175](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L175)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:175 | ID-014 | feat(identity): add infrastructure adapters and configuration | Password/event/configuration adapters
~~~~~~~~

<a id="hapi-681"></a>
## HAPI-681 — add REST API contracts and controllers

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-015

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

REST boundary

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:176](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L176)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:176 | ID-015 | feat(identity): add REST API contracts and controllers | REST boundary
~~~~~~~~

<a id="hapi-682"></a>
## HAPI-682 — add identity domain tests

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-016

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Domain/value/policy tests

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/identity/domain/exception/IdentityDomainException.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/configuration/IdentityDomainServiceConfiguration.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/exception/IdentityDomainException.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/configuration/IdentityDomainServiceConfiguration.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/identity/domain/exception/IdentityDomainException.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/configuration/IdentityDomainServiceConfiguration.java`

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:177](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L177)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:177 | ID-016 | test(identity): add identity domain tests | Domain/value/policy tests
~~~~~~~~

<a id="hapi-683"></a>
## HAPI-683 — add identity application tests

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-017

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Application service tests

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/identity/application/IdentityApplicationServiceRegistry.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/mapper/IdentityApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAuthorizationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityUserApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAdministratorBootstrapApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAuthenticationOutcomeApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/OperationAuthenticationQueryApplicationService.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/identity/application/IdentityApplicationServiceRegistry.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/mapper/IdentityApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAuthorizationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityUserApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAdministratorBootstrapApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAuthenticationOutcomeApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/OperationAuthenticationQueryApplicationService.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationServiceTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:178](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L178)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:178 | ID-017 | test(identity): add identity application tests | Application service tests
~~~~~~~~

<a id="hapi-684"></a>
## HAPI-684 — add identity persistence tests

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-018

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Persistence adapter tests

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/IdentityPersistence.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/IdentityPersistenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/IdentityPersistence.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/IdentityPersistenceAdapter.java`, `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/IdentityPersistence.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/IdentityPersistenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:179](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L179)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:179 | ID-018 | test(identity): add identity persistence tests | Persistence adapter tests
~~~~~~~~

<a id="hapi-685"></a>
## HAPI-685 — add identity API tests

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-019

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Controller/API tests

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationCommandController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationQueryController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityController.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLdapApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLocalApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerOidcApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityControllerPermissionScopeApiTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationCommandController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationQueryController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityController.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLdapApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLocalApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerOidcApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityControllerPermissionScopeApiTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLdapApiTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerLocalApiTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationControllerOidcApiTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationControllerTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityControllerPermissionScopeApiTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:180](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L180)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:180 | ID-019 | test(identity): add identity API tests | Controller/API tests
~~~~~~~~

<a id="hapi-686"></a>
## HAPI-686 — add identity architecture guardrail

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-020

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

ArchUnit boundary checks

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:181](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L181)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:181 | ID-020 | test(identity): add identity architecture guardrail | ArchUnit boundary checks
~~~~~~~~

<a id="hapi-687"></a>
## HAPI-687 — finalize identity checklist

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-021

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Final execution status

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/identity.md:182](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/identity.md#L182)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/identity.md:182 | ID-021 | docs(identity): finalize identity checklist | Final execution status
~~~~~~~~

<a id="hapi-688"></a>
## HAPI-688 — Qualify ordinary-user first-use activation

Phase: HPS-001 — Identity and Authentication

Legacy IDs: ID-GAP-001

Priority: P0

Implementation Status: NOT_VERIFIABLE

Planning Status: BLOCKED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Determine the actual ordinary-user activation boundary and deliver the owner-selected single-use/expiry/access-restriction policy, without imposing local credentials on external-only users.

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationCommandController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/EvaluatePermissionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationAssertionPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/ClaimNameCatalogPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/RolePermissionGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/CustodyTransferTicketActorReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAuthenticationOutcomeApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/MaintenanceWorkOrderActorReferenceQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Complete source-claim issuance/binding/reader integration and qualify ordinary-user first-use activation; actual IdP TLS and deployment linkage remain SITE gates. The exact bounded remaining delivery is: Determine the actual ordinary-user activation boundary and deliver the owner-selected single-use/expiry/access-restriction policy, without imposing local credentials on external-only users..

### 4. Functional Requirements

Owner selects activation option, delivery/recovery and expiry policy; single redemption and concurrent replay refusal; ordinary access restricted before activation; disabled/revoked/expired states fail closed; LDAP/OIDC-only users are not assigned invented local-password obligations.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/in/EvaluatePermissionUseCase.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationAssertionPort.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/out/ClaimNameCatalogPort.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/out/RolePermissionGrantRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/CustodyTransferTicketActorReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAuthenticationOutcomeApplicationService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/MaintenanceWorkOrderActorReferenceQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE:** DISCOVERY_REQUIRED — first resolve the bounded policy and inspect existing primitives; no invented migration or production class filename.

**MODIFY candidates — existing verified owner seams:**

- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationCommandController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/EvaluatePermissionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationAssertionPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/ClaimNameCatalogPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/RolePermissionGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/CustodyTransferTicketActorReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAuthenticationOutcomeApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/MaintenanceWorkOrderActorReferenceQueryService.java`

**DELETE:** none.

**PRESERVE / REUSE:** current completed authentication/responsibility/source behavior and historical records.

Register an exact small implementation package after the explicit decisions and test matrix below; do not revive the old module scaffold.

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

- Owner selects activation option, delivery/recovery and expiry policy; single redemption and concurrent replay refusal; ordinary access restricted before activation; disabled/revoked/expired states fail closed; LDAP/OIDC-only users are not assigned invented local-password obligations.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- The exact identity is retained in the pinned full source records; a self-contained original task definition is NOT ESTABLISHED.

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: New bounded verification/correction obligation admitted by initial baseline authorization; preserve predecessor behavior. Exact owner policy/path admission remains explicit. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

### HAPI-688 owner-approved policy registration — 2026-10-27

**Authority and task boundary.** The repository owner approved decisions OD-HAPI-688-01 through OD-HAPI-688-07 on 2026-10-27 and explicitly directed that those decisions be recorded in the repository. This is a documentation-only policy registration limited to HAPI-688 (HPS-001, legacy ID-GAP-001). It is not the execution of a feature implementation stage. Exact documentation commit message: **docs(identity): record owner-selected first-use activation policy**. Preserve preceding evidence, statuses, source, tests, migrations, APIs, workflows and all other task records.

**Owner-selected policies (each Option A):**

- **OD-HAPI-688-01 — Controlled hybrid activation.** Authorized administrators provision or approve accounts. LOCAL users redeem secure, single-use invitations. LDAP/AD/OIDC-only accounts use approved and verified federated identity binding rather than invented LOCAL passwords. No automatic privileges.
- **OD-HAPI-688-02 — 24-hour invitation and administrator reissue.** LOCAL invitations expire 24 hours after issuance, redeem once, and may be reissued only by an authorized administrator. Reissue immediately invalidates earlier invitations; reject expired/redeemed/revoked/replaced and concurrent replay attempts atomically. No ordinary access before activation.
- **OD-HAPI-688-03 — Verified corporate email plus supervised fallback.** Verify recipient identity and corporate delivery address before issuance. Use supervised identity-checked, authorized administration for personnel without an approved corporate mailbox. Do not default to unverified personal messaging/email or disclose invitation tokens in logs, audit payloads or unauthorized responses.
- **OD-HAPI-688-04 — Password plus mandatory MFA before ordinary LOCAL access.** The invitation recipient establishes a policy-compliant LOCAL password, enrolls and successfully verifies an approved MFA factor before ordinary access. Federated-only accounts do not acquire a LOCAL-password obligation.
- **OD-HAPI-688-05 — FIDO2/WebAuthn preferred with TOTP fallback.** Prefer verified, phishing-resistant security keys/passkeys; permit enrolled authenticator-app TOTP if FIDO2 is unavailable/unprovisioned. SMS/email are not approved default MFA factors.
- **OD-HAPI-688-06 — Administrator-controlled factor recovery.** Independently verify user identity and authorize recovery; revoke lost factors and relevant sessions; require enrolled and verified replacement MFA before restored access. Privileged-account recovery requires additional independent authorized approval. Email possession alone is insufficient.
- **OD-HAPI-688-07 — Trusted federation with MFA fallback.** Recognize upstream IdP MFA only from approved providers with trustworthy verifiable assurance. A standard LDAP password bind does not prove MFA; when evidence is insufficient, require Hidra MFA/step-up without inventing federated users' LOCAL passwords. External groups never directly imply Hidra permissions.

**Boundaries and acceptance.** Identity owns account activation, invitation state, factor enrollment/recovery and federated assurance; HPS-002 owns company/resource/role grants independently. Enforce company isolation, deny precedence, account disabled/revoked/expired states and non-disclosure. Domain remains pure Java and application coordinates security/transactions through ports. Before implementation, inspect actual Identity source, security configuration, current REST/OpenAPI, Flyway schema and runtime boundary to admit a small explicit implementation stage with its own exact message and allowlist. Test synthetic first-use versus ordinary access, expiry boundary, single-use and concurrent replay, replacement, lost-factor recovery, verified and unverified federated MFA, LDAP password-only, denied/disabled users, actor/scope isolation, atomic rollback and PostgreSQL constraints. Record actual tests, skips, exact-SHA Full/Fast/Docs and API evidence separately; do not infer an existing MFA SDK, invitation transport, IdP integration or operational acceptance.

**Status and remaining gate.** At prepublication main 556bad407259544d6e82bc8bd18f23b5b8bd15dc, HAPI-688 remains NOT_VERIFIABLE / BLOCKED. Approval of these seven policies closes only the selection gate; implementation, testing, CI acceptance, engineering acceptance and any SITE gate remain outstanding. This stage changes **no** ULTIMATE_ROADMAP or ULTIMATE_PHASES status cells. Decisions for HAPI-689, HAPI-599 and HAPI-611 belong in their own owning task records and are not silently executed here.

<a id="hapi-822"></a>
## HAPI-822 — restore identity roadmap execution memory

Phase: HPS-001 — Identity and Authentication

Legacy IDs: STB2-003

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Recover Identity roadmap history and merge current-state record

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/identity.md`
- `src/main/java/dz/sh/hidra/modules/identity/IdentityModule.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/IdentityApi.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/IdentityRestApi.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationCommandController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationQueryController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityAuthenticationWebMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/IdentityAdministrationRequests.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/IdentityApplicationServiceRegistry.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/command/IdentityAdministrationCommands.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/mapper/IdentityApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedDirectoryIdentity.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationCommandUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationCommandUseCase.java`, `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/identity.md`
- `src/main/java/dz/sh/hidra/modules/identity/IdentityModule.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/IdentityApi.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/IdentityRestApi.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationCommandController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationQueryController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityController.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityAuthenticationWebMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/IdentityAdministrationRequests.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/IdentityApplicationServiceRegistry.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/command/IdentityAdministrationCommands.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/mapper/IdentityApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedDirectoryIdentity.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationCommandUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/stabilization_02.md:71](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/stabilization_02.md#L71)
- [docs/roadmap/fixs/stabilization_02.md:116](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/stabilization_02.md#L116)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/stabilization_02.md:71 | STB2-003 | docs(identity): restore identity roadmap execution memory | Recover Identity roadmap history and merge current-state record | docs/roadmap/identity.md
docs/roadmap/fixs/stabilization_02.md:116 | STB2-003 | Prepared artifact | Identity execution memory recovered into a current-state roadmap
~~~~~~~~

<a id="hapi-832"></a>
## HAPI-832 — add identity flyway migration

Phase: HPS-001 — Identity and Authentication

Legacy IDs: STB-005

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `identity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add identity flyway migration

### 2. Existing Implementation

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `identity`; primary phase owns this obligation. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### STB-005 — Identity migration

| File | Purpose |
|---|---|
| `src/main/resources/db/migration/V010__create_identity_tables.sql` | Creates identity-owned persistence tables for users, roles, permissions, user-role links, and role-permission links. |

Tables covered:

```text
hidra_identity_user
hidra_identity_role
hidra_identity_permission
hidra_identity_user_role
hidra_identity_role_permission
```

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

- `7638896b8ba5c2b72350561dfadd32d69b7a7d2f` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/stabilization_01.md:71](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/stabilization_01.md#L71)
- [docs/roadmap/fixs/stabilization_01.md:89](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/stabilization_01.md#L89)
- [docs/roadmap/fixs/stabilization_01.md:109](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/stabilization_01.md#L109)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/stabilization_01.md:71 | STB-005 | db(identity): add identity flyway migration | Completed | Added identity Flyway migration `V010__create_identity_tables.sql`.
docs/roadmap/fixs/stabilization_01.md:89 | STB-005 | 7638896b8ba5c2b72350561dfadd32d69b7a7d2f | Added identity migration.
~~~~~~~~

<!-- task-register:payload:end -->
