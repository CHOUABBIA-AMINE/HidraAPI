# HPS-004 — Physical Network Topology: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-004 — Physical Network Topology

<a id="hapi-048"></a>
## HAPI-048 — inventory deprecated and transitional files

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR2-002

Priority: P1

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Create the verified deletion inventory before code changes

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `README.md`
- `docs/roadmap/identity.md`
- `docs/roadmap/kernel.md`
- `docs/roadmap/organization.md`
- `docs/roadmap/platform.md`
- `docs/roadmap/topology.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnitType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/OperationalScopeType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/NodeType.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnitType.java`, `src/main/java/dz/sh/hidra/modules/organization/domain/value/OperationalScopeType.java`, `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `README.md`
- `docs/roadmap/identity.md`
- `docs/roadmap/kernel.md`
- `docs/roadmap/organization.md`
- `docs/roadmap/platform.md`
- `docs/roadmap/topology.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnitType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/OperationalScopeType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/NodeType.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## COR2-002 — Inventory Deprecated and Transitional Files

### Commit message

```text
chore(repo): inventory deprecated and transitional files
```

### Description

Create a verified cleanup inventory before deleting code.

Search the full repository for deprecated, transitional, bridge, temporary, and compatibility files or methods.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Update | `docs/roadmap/correction_02.md` | Add actual inventory result and update task status |

### Required search commands

```bash
grep -R "@Deprecated" src/main/java src/test/java || true
grep -R "SuppressWarnings(\"deprecated\")" src/main/java src/test/java || true
grep -R "transitional\|legacy\|bridge\|compatibility\|temporary\|migration adapter" src/main/java src/test/java docs README.md || true
find docs/roadmap -type f | sort
```

### Verified search inventory

Search method used for this commit:

```text
GitHub repository search for: @Deprecated
GitHub repository search for: SuppressWarnings("deprecated")
GitHub repository search for: compatibility
GitHub repository search for: legacy
GitHub repository search for: transitional
GitHub repository search for: temporary
GitHub repository search for: docs/roadmap
Representative source files fetched to verify @Deprecated annotations and compatibility intent.
```

#### Deprecated production compatibility files

| # | File | Category | Evidence | Status | Follow-up task |
|---|---|---|---|---|---|
| 1 | `src/main/java/dz/sh/hidra/modules/topology/domain/value/ProductType.java` | Deprecated catalog compatibility wrapper | Header and Javadoc say deprecated compatibility constant class; annotated `@Deprecated(forRemoval = false)`; exposes enum-style constants, `valueOf`, `values`, and `name`. | DELETE | COR2-003 |
| 2 | `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitType.java` | Deprecated catalog compatibility wrapper | Header and Javadoc say deprecated compatibility wrapper; annotated `@Deprecated(forRemoval = false)`; exposes constants, `valueOf`, `values`, `toReference`, and `name`. | DELETE | COR2-004 |
| 3 | `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityType.java` | Deprecated catalog compatibility wrapper | Header and Javadoc say deprecated compatibility constant class for facility type catalog; annotated `@Deprecated(forRemoval = false)`; exposes constants, `valueOf`, `values`, and `name`. | REPLACE_FIRST_THEN_DELETE | COR2-007 then catalog cleanup task |
| 4 | `src/main/java/dz/sh/hidra/modules/topology/domain/value/NodeType.java` | Deprecated catalog compatibility wrapper | Header says deprecated compatibility constant class for node type catalog; annotated `@Deprecated(forRemoval = false)`; exposes constants, `valueOf`, `values`, and `name`. | REPLACE_FIRST_THEN_DELETE | COR2-007 then catalog cleanup task |
| 5 | `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java` | Deprecated catalog compatibility wrapper | Header says deprecated compatibility constant class for connection type catalog; annotated `@Deprecated(forRemoval = false)`; exposes constants, `valueOf`, `values`, and `name`. | REPLACE_FIRST_THEN_DELETE | COR2-007 then catalog cleanup task |
| 6 | `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentType.java` | Deprecated catalog compatibility wrapper | Header says deprecated compatibility constant class for equipment type catalog; annotated `@Deprecated(forRemoval = false)`; exposes constants, `valueOf`, `values`, and `name`. | REPLACE_FIRST_THEN_DELETE | COR2-007 then catalog cleanup task |
| 7 | `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineAppurtenanceType.java` | Deprecated catalog compatibility wrapper | Header says deprecated compatibility constant class for pipeline appurtenance type catalog; annotated `@Deprecated(forRemoval = false)`; exposes constants, `valueOf`, `values`, `name`, and `isValve`. | REPLACE_FIRST_THEN_DELETE | COR2-007 then catalog cleanup task |
| 8 | `src/main/java/dz/sh/hidra/modules/topology/domain/value/ValveType.java` | Deprecated catalog compatibility wrapper | Header says deprecated compatibility constant class for valve type catalog; annotated `@Deprecated(forRemoval = false)`; exposes constants, `valueOf`, `values`, and `name`. | REPLACE_FIRST_THEN_DELETE | COR2-007 then catalog cleanup task |
| 9 | `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java` | Deprecated compatibility constant class | Header says deprecated compatibility constant class for reporting line types; annotated `@Deprecated(forRemoval = false)`; exposes constants, `valueOf`, `values`, `name`, and `allowsMultipleActiveLines`. | REPLACE_FIRST_THEN_DELETE | COR2-007 then organization follow-up |
| 10 | `src/main/java/dz/sh/hidra/modules/organization/domain/value/OperationalScopeType.java` | Deprecated compatibility constant class | Header says deprecated compatibility constant class for operational scope types; annotated `@Deprecated(forRemoval = false)`; exposes constants, `valueOf`, `values`, `name`, and `isStationScope`. | REPLACE_FIRST_THEN_DELETE | COR2-007 then organization follow-up |

#### Deprecated or bridge usages discovered by search

| # | File | Category | Inventory decision | Follow-up task |
|---|---|---|---|---|
| 1 | `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java` | Active aggregate bridge overloads for `ProductType` | Remove deprecated overloads and keep `ProductTypeReference` only. | COR2-003 |
| 2 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java` | Active aggregate bridge overloads for `OrganizationUnitType` | Remove deprecated overloads and keep `OrganizationUnitTypeReference` only. | COR2-004 |
| 3 | `src/main/java/dz/sh/hidra/modules/topology/domain/value/ProductTypeReference.java` | Reference object still has legacy bridge method(s) from deleted wrapper | Remove `from(ProductType)` and enum-like compatibility helpers. | COR2-003 |
| 4 | `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitTypeReference.java` | Reference object still has legacy/label behavior | Keep `id` + `code`; remove hardcoded labels once catalog response exists. | COR2-004 / COR2-006 |
| 5 | `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java` | Active model references deprecated facility type compatibility path | Replace with catalog reference object. | COR2-007 follow-up |
| 6 | `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyNode.java` | Active model references deprecated node type compatibility path | Replace with catalog reference object. | COR2-007 follow-up |
| 7 | `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java` | Active model references deprecated connection type compatibility path | Replace with catalog reference object. | COR2-007 follow-up |
| 8 | `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java` | Active model references deprecated equipment/valve type compatibility path | Replace with catalog reference object. | COR2-007 follow-up |
| 9 | `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineAppurtenance.java` | Active model references deprecated appurtenance/valve compatibility path | Replace with catalog reference object. | COR2-007 follow-up |

#### Transition and roadmap documents

| # | File | Category | Status | Notes |
|---|---|---|---|---|
| 1 | `docs/roadmap/correction_01.md` | Transition/correction roadmap | KEEP_WITH_REASON | Historical correction plan; consolidate or archive during COR2-017, not delete during inventory. |
| 2 | `docs/roadmap/correction_02.md` | Active correction roadmap | KEEP_WITH_REASON | Current executable roadmap. |
| 3 | `docs/roadmap/topology_validation_checklist.md` | Validation checklist document | KEEP_WITH_REASON | Keep until topology validation is superseded by final architecture docs. |
| 4 | `docs/roadmap/stabilization_01.md` | Stabilization roadmap | KEEP_WITH_REASON | Keep until stabilization scope is merged into architecture/developer docs. |
| 5 | `docs/roadmap/kernel.md` | Module roadmap | KEEP_WITH_REASON | Active historical/module execution memory. |
| 6 | `docs/roadmap/platform.md` | Module roadmap | KEEP_WITH_REASON | Active historical/module execution memory. |
| 7 | `docs/roadmap/identity.md` | Module roadmap | KEEP_WITH_REASON | Active historical/module execution memory. |
| 8 | `docs/roadmap/organization.md` | Module roadmap | KEEP_WITH_REASON | Active historical/module execution memory. |
| 9 | `docs/roadmap/topology.md` | Module roadmap | KEEP_WITH_REASON | Active historical/module execution memory. |

#### Clean categories

| Search category | Result | Status |
|---|---|---|
| `SuppressWarnings("deprecated")` | No connector search results found. | Clean |
| `migration adapter` | No connector search results found. | Clean |
| `transitional` | Found in `docs/roadmap/correction_01.md`, `TopologyTypeCatalog`, `PipelineSystem`, and `TopologyRestMapper`; treat as follow-up context, not immediate deletion without compilation inspection. | REVIEW_IN_COR2-007 / COR2-013 |
| `temporary` | Found in roadmap/package/migration/tooling contexts; no immediate production deletion without local compilation inspection. | REVIEW_WITH_CONTEXT |

### Acceptance criteria

- Every deprecated production file found by connector search is listed above.
- Every deprecated method/bridge usage found in active aggregate/model code is listed above.
- Every transition/roadmap document found by connector search is listed above.
- Every item has one of these statuses:

```text
DELETE
REPLACE_FIRST_THEN_DELETE
KEEP_WITH_REASON
REVIEW_WITH_CONTEXT
```

`REVIEW_WITH_CONTEXT` is used only for text-search hits where the term appears in a non-deprecated or ambiguous context and should not be deleted blindly.

### Validation

Required local validation command:

```bash
mvn -q -DskipTests compile
```

Result for this connector-executed commit:

```text
NOT_RUN_IN_SANDBOX
Reason: repository was inspected through the GitHub connector; no local checkout or Maven execution environment was available in this session.
```

### Result

```text
Status: COMPLETED
Files changed: docs/roadmap/correction_02.md
Commit message: chore(repo): inventory deprecated and transitional files
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

- `816c243f0b7f56132297c0f95b3c9bd7061bad8a` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_02.md:374](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02.md#L374)
- [docs/roadmap/fixs/correction_02.md:435](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02.md#L435)
- [docs/roadmap/fixs/correction_02.md:1441](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02.md#L1441)
- [docs/roadmap/fixs/correction_02_final_checklist.md:22](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02_final_checklist.md#L22)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/correction_02.md:374 | COR2-002 | chore(repo): inventory deprecated and transitional files | Create the verified deletion inventory before code changes
docs/roadmap/fixs/correction_02_final_checklist.md:22 | COR2-002 | 816c243f0b7f56132297c0f95b3c9bd7061bad8a | Completed | Added deprecated/transitional inventory to the roadmap.
docs/roadmap/fixs/correction_02.md:1441 | COR2-002 | Completed | Deprecated/transitional inventory recorded in this file; Maven validation not run because execution used GitHub connector only
~~~~~~~~

<a id="hapi-049"></a>
## HAPI-049 — remove product type compatibility wrapper

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR2-003

Priority: P1

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Delete deprecated ProductType wrapper and legacy topology bridges

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## COR2-003 — Remove Topology Product Type Compatibility Wrapper

### Commit message

```text
refactor(topology): remove product type compatibility wrapper
```

### Description

Remove deprecated product type compatibility code from topology.

The old `ProductType` wrapper is a user-facing business classification and must not remain as a pseudo-enum compatibility layer.

### Files to inspect

| File | Expected action |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/ProductType.java` | Delete |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/ProductTypeReference.java` | Remove legacy bridge methods if present |
| `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java` | Remove overloads accepting `ProductType` |
| `src/main/java/dz/sh/hidra/modules/topology/**` | Replace any remaining usage with `ProductTypeReference` |
| `src/test/java/dz/sh/hidra/modules/topology/**` | Update tests to use `ProductTypeReference` |

### Acceptance criteria

- `ProductType.java` no longer exists.
- No production code imports or references `ProductType`.
- `Pipeline.create(...)` accepts `ProductTypeReference`, not `ProductType`.
- `Pipeline.restore(...)` accepts `ProductTypeReference`, not `ProductType`.
- `ProductTypeReference` does not expose enum-like `values()`, `valueOf()`, or `from(ProductType)` APIs.
- Tests use explicit catalog reference fixtures.

### Validation

```bash
! test -f src/main/java/dz/sh/hidra/modules/topology/domain/value/ProductType.java
grep -R "ProductType" src/main/java src/test/java || true
mvn -q -DskipTests compile
mvn -q test -Dtest='*Topology*Test'
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

- `4f2613ae3f7a42ae0a6d81bd35a7c9fcfdadbaea` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_02.md:375](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02.md#L375)
- [docs/roadmap/fixs/correction_02.md:572](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02.md#L572)
- [docs/roadmap/fixs/correction_02.md:1442](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02.md#L1442)
- [docs/roadmap/fixs/correction_02_final_checklist.md:23](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02_final_checklist.md#L23)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/correction_02_final_checklist.md:23 | COR2-003 | PR #33 | Completed | Removed topology `ProductType` compatibility wrapper and active bridges.
docs/roadmap/fixs/correction_02.md:375 | COR2-003 | refactor(topology): remove product type compatibility wrapper | Delete deprecated `ProductType` wrapper and legacy topology bridges
docs/roadmap/fixs/correction_02.md:1442 | COR2-003 | Pending | Topology product type cleanup
~~~~~~~~

<a id="hapi-051"></a>
## HAPI-051 — complete product type catalog model

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR2-005

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Ensure product types are catalog-backed and trilingual

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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
## COR2-005 — Complete Product Type Catalog Model

### Commit message

```text
feat(topology): complete product type catalog model
```

### Description

Complete product type modeling as a multilingual catalog.

Product type is a user-facing classification, so the UI/API must be able to display Arabic, French, and English names without hardcoded enum labels.

### Target model

```text
ProductTypeCatalog or ProductTypeCatalogEntry
ProductTypeReference
ProductTypeCatalogRepository / ProductTypeCatalogPort, if persisted or externally loaded
ProductTypeResponse, if exposed through REST
```

### Required catalog fields

```text
id
code
nameAr
nameFr
nameEn
descriptionAr, optional
descriptionFr, optional
descriptionEn, optional
status, if lifecycle-managed
sortOrder, if displayed in UI
createdAt / updatedAt, if persisted
```

### Acceptance criteria

- Product type labels are not hardcoded in `ProductTypeReference`.
- Product type API output exposes trilingual labels.
- Pipeline creation/listing does not require the deleted compatibility wrapper.
- Seeded product types preserve known business codes: `GAS`, `CRUDE_OIL`, `CONDENSATE`, `LPG`, `REFINED_PRODUCT`, `MULTIPHASE`, `UNKNOWN`.
- The catalog supports future additions without Java source changes.

### Validation

```bash
grep -R "enum ProductType\|class ProductType" src/main/java/dz/sh/hidra/modules/topology || true
grep -R "nameAr\|nameFr\|nameEn" src/main/java/dz/sh/hidra/modules/topology src/main/resources/db/migration || true
mvn -q -DskipTests compile
mvn -q test -Dtest='*ProductType*Test,*Pipeline*Test'
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

- `74a3dc43b0a94d14cedc0eae80cea908968c55cb` — exact registered-message commit; surviving paths matched to current tree.
- `1cb3616b870e414c3cfb77c17d03e3ae60cedd3b` — exact registered-message commit; surviving paths matched to current tree.
- `d33ef752c943794e1af8eb471f8db3e6c01f2440` — exact registered-message commit; surviving paths matched to current tree.
- `1cf67ef9417c35d2e9d2af4210424fa946cc97ab` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_02.md:377](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02.md#L377)
- [docs/roadmap/fixs/correction_02.md:660](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02.md#L660)
- [docs/roadmap/fixs/correction_02.md:1444](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02.md#L1444)
- [docs/roadmap/fixs/correction_02_final_checklist.md:25](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02_final_checklist.md#L25)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/correction_02.md:377 | COR2-005 | feat(topology): complete product type catalog model | Ensure product types are catalog-backed and trilingual
docs/roadmap/fixs/correction_02_final_checklist.md:25 | COR2-005 | PR #35 | Completed | Added product type catalog DTO/API surface and trilingual response model.
docs/roadmap/fixs/correction_02.md:1444 | COR2-005 | Pending | Product type catalog completion
~~~~~~~~

<a id="hapi-054"></a>
## HAPI-054 — standardize pipeline multilingual labels

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR2-008

Priority: P1

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Replace single-language pipeline API labels with trilingual fields

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## COR2-008 — Standardize Pipeline Multilingual Labels

### Commit message

```text
refactor(api): standardize pipeline multilingual labels
```

### Description

Replace single-language pipeline labels in REST contracts and DTOs.

Pipeline names and descriptions are human-readable business text and must follow the trilingual convention.

### Files to inspect

| File / Package | Expected work |
|---|---|
| `topology/api/rest/request/CreatePipelineRequest.java` | Replace `name` and `description` with trilingual fields |
| `topology/api/rest/request/UpdatePipelineRequest.java`, if present | Apply same convention |
| `topology/api/rest/response/PipelineResponse.java` | Expose trilingual fields or documented locale projection |
| `topology/application/command` | Update command records |
| `topology/application/dto` | Update DTO records |
| `topology/api/rest/mapper` | Update mapping |
| `topology/domain/model/Pipeline.java` | Align with trilingual value object if needed |
| `topology/infrastructure/persistence` | Align columns if persistence stores labels |

### Required API fields

Preferred full trilingual contract:

```text
nameAr
nameFr
nameEn
descriptionAr
descriptionFr
descriptionEn
```

Alternative accepted only if explicitly documented:

```text
name
```

may appear only in a locale-projected read model where:

```text
source fields remain trilingual
projection language is determined by Accept-Language
OpenAPI documents the projection clearly
```

### Acceptance criteria

- No create/update request for pipeline accepts a single business `name` field.
- No persistence entity stores only a single pipeline display name unless explicitly technical.
- Swagger examples exist for Arabic, French, and English labels.
- Tests validate mapping from request to command to domain.

### Validation

```bash
grep -R "String name\|String description" src/main/java/dz/sh/hidra/modules/topology/api src/main/java/dz/sh/hidra/modules/topology/application || true
grep -R "nameAr\|nameFr\|nameEn" src/main/java/dz/sh/hidra/modules/topology || true
mvn -q -DskipTests compile
mvn -q test -Dtest='*Pipeline*Test,*Topology*Mapper*Test'
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

- `e97a77cf76fced454f2200722e3174c8d1f6c53a` — exact registered-message commit; surviving paths matched to current tree.
- `c7153a8fb4a30d15f0187a8b2d29694a5086ab59` — exact registered-message commit; surviving paths matched to current tree.
- `f6796a70ad5c84b8c3bb0a89064e69277d57ac91` — exact registered-message commit; surviving paths matched to current tree.
- `0b1995551bdfc301c7a68fc4d2b95591a3d8b566` — exact registered-message commit; surviving paths matched to current tree.
- `664dee9cce573e9d3b3dd9a6fad2cb8533a75b2b` — exact registered-message commit; surviving paths matched to current tree.
- `f44ec81ea5880911da0738f0838b866f0595517f` — exact registered-message commit; surviving paths matched to current tree.
- `3b8582a123325cddc0669c5882c5fdf1b9648b7d` — exact registered-message commit; surviving paths matched to current tree.
- `f06ae05a59f870d3c0527e58f09aa39bf912ce78` — exact registered-message commit; surviving paths matched to current tree.
- `c344f9c080c06200009f75f749dfbf004e99a073` — exact registered-message commit; surviving paths matched to current tree.
- `6deb3674c0175a706b9dafa55d4ac84bee8664d2` — exact registered-message commit; surviving paths matched to current tree.
- `3d255312c78db5be0b11d528ae6ff4d702a1e47d` — exact registered-message commit; surviving paths matched to current tree.
- `592e48c49deaaa9ca13441cda131431d3d68328d` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_02.md:380](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02.md#L380)
- [docs/roadmap/fixs/correction_02.md:854](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02.md#L854)
- [docs/roadmap/fixs/correction_02.md:1447](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02.md#L1447)
- [docs/roadmap/fixs/correction_02_final_checklist.md:28](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_02_final_checklist.md#L28)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/correction_02_final_checklist.md:28 | COR2-008 | PR #38 | Completed | Standardized pipeline API/domain/persistence multilingual labels.
docs/roadmap/fixs/correction_02.md:380 | COR2-008 | refactor(api): standardize pipeline multilingual labels | Replace single-language pipeline API labels with trilingual fields
docs/roadmap/fixs/correction_02.md:1447 | COR2-008 | Pending | Pipeline multilingual labels
~~~~~~~~

<a id="hapi-066"></a>
## HAPI-066 — realign topology roadmap with repository reality

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR-003

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Realign topology docs with actual TOP sequence.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/topology.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/topology.md`

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

- `99f12a4d6585af5b32dddf32499d18f4d0b80033` — exact registered-message commit; surviving paths matched to current tree.
- `14b77edbcc31aebf6ceee9f5fcf9b25c466e1e99` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_01.md:97](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L97)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/correction_01.md:97 | COR-003 | docs(topology): realign topology roadmap with repository reality | Completed | Realign topology docs with actual TOP sequence.
~~~~~~~~

<a id="hapi-068"></a>
## HAPI-068 — add topology type catalog tables

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR-005

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add topology catalog and translation tables in V003.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- `d85c0b89bbd531b6d81918572f9fd563dab8c0d0` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_01.md:99](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L99)
- [docs/roadmap/topology.md:105](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L105)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:105 | COR-005 | db(topology): add topology type catalog tables | Completed | Adds multilingual topology catalog and translation tables in V003.
docs/roadmap/fixs/correction_01.md:99 | COR-005 | db(topology): add topology type catalog tables | Completed | Add topology catalog and translation tables in V003.
~~~~~~~~

<a id="hapi-069"></a>
## HAPI-069 — add catalog domain model and references

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR-006

Priority: P1

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add topology catalog domain model and type references.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`

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

- `e92a89df283c7bdc8fc3d4d9c9db2647531c31fd` — exact registered-message commit; surviving paths matched to current tree.
- `e816daed53e9399b1164720cb5bd942f7a60a249` — exact registered-message commit; surviving paths matched to current tree.
- `284015f6293f90a67b3bbfc6678b0dc0ff37b9df` — exact registered-message commit; surviving paths matched to current tree.
- `50085e7497b5c77ea24380a610d8cfabb4483189` — exact registered-message commit; surviving paths matched to current tree.
- `405f6a58bab5f9ee743bdfb75a784981f306f25b` — exact registered-message commit; surviving paths matched to current tree.
- `6bc68a24219c020aba7e1732545f1105d118fbe5` — exact registered-message commit; surviving paths matched to current tree.
- `5403ab227f8fe64a53668e59a3abdaff5a388b1a` — exact registered-message commit; surviving paths matched to current tree.
- `5bf669be7af5f4de1cccb1deb0ae6e7b36c6514a` — exact registered-message commit; surviving paths matched to current tree.
- `59a745b39715d19c8f980429a806b29be9fee537` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_01.md:100](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L100)
- [docs/roadmap/topology.md:106](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L106)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:106 | COR-006 | feat(topology): add catalog domain model and references | Completed | Adds catalog domain model and type reference value objects.
docs/roadmap/fixs/correction_01.md:100 | COR-006 | feat(topology): add catalog domain model and references | Completed | Add topology catalog domain model and type references.
~~~~~~~~

<a id="hapi-070"></a>
## HAPI-070 — add topology catalog application services

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR-007

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add catalog DTOs, queries, ports, and application service.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- `19137769788945fb8b29b10b8c2296341f013b6a` — exact registered-message commit; surviving paths matched to current tree.
- `fd3d6a6f86e86d4563386ea1d88267af612f99b4` — exact registered-message commit; surviving paths matched to current tree.
- `968d98134569926d106d5eca39b84058efadc9d3` — exact registered-message commit; surviving paths matched to current tree.
- `c288afa4f8ec9deb20dc5024173a08ab4fd344f9` — exact registered-message commit; surviving paths matched to current tree.
- `08894c34d0e4e6007f5e45ae08dd297df04f75a5` — exact registered-message commit; surviving paths matched to current tree.
- `a12644cb0dac353b5aac3980a61742a9bfd92a86` — exact registered-message commit; surviving paths matched to current tree.
- `65c2fda0dcbf2da79afdd413bdaa87e260e88302` — exact registered-message commit; surviving paths matched to current tree.
- `d48ed7abc9d0389c58a2a486413b91bc358859fe` — exact registered-message commit; surviving paths matched to current tree.
- `289dff5d9104bf368dc354bc3f4cacc929f89c43` — exact registered-message commit; surviving paths matched to current tree.
- `66e7c773b14d08c591fea23a83c7f57152062943` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_01.md:101](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L101)
- [docs/roadmap/topology.md:107](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L107)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:107 | COR-007 | feat(topology): add topology catalog application services | Completed | Adds catalog application DTOs, queries, ports, and service.
docs/roadmap/fixs/correction_01.md:101 | COR-007 | feat(topology): add topology catalog application services | Completed | Add catalog DTOs, queries, ports, and application service.
~~~~~~~~

<a id="hapi-071"></a>
## HAPI-071 — add topology catalog persistence adapters

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR-008

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add catalog persistence adapter and configuration wiring.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- `1169d2f5ba25cb5f47a0bd2ce6078c4133cfee5d` — exact registered-message commit; surviving paths matched to current tree.
- `774c44ad8ae907483966ebb6a460730c0e2081fb` — exact registered-message commit; surviving paths matched to current tree.
- `242e6ca0e2335bfd922fa200b1fd9c31df5f6173` — exact registered-message commit; surviving paths matched to current tree.
- `f8e4b5f3982594a490c2df5ae6759c0b856034d5` — exact registered-message commit; surviving paths matched to current tree.
- `66f7b4bbe5ab085ecb057b0f8e12815b72058802` — exact registered-message commit; surviving paths matched to current tree.
- `af615f2fe6d81f1492b01554555b5aa3875380a0` — exact registered-message commit; surviving paths matched to current tree.
- `e10256caad2d511a019bed28d93539d333a9860b` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_01.md:102](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L102)
- [docs/roadmap/topology.md:108](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L108)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:108 | COR-008 | feat(topology): add topology catalog persistence adapters | Completed | Adds catalog persistence adapter and configuration wiring.
docs/roadmap/fixs/correction_01.md:102 | COR-008 | feat(topology): add topology catalog persistence adapters | Completed | Add catalog persistence adapter and configuration wiring.
~~~~~~~~

<a id="hapi-072"></a>
## HAPI-072 — replace enum usage in domain assets with type references

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR-009

Priority: P1

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Refactor topology domain/application asset contracts to use references.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`

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

- `efb85453c20c7c4ed2c31d6e75caf11d0095d25f` — exact registered-message commit; surviving paths matched to current tree.
- `e2e4d19325359bda891af032bc0eb21c715b40d7` — exact registered-message commit; surviving paths matched to current tree.
- `2ddb25f01d651e68a07a51d1e39266c976568d4c` — exact registered-message commit; surviving paths matched to current tree.
- `a941efe214b3aa87cdefc93e2a00f0705f4bb4f2` — exact registered-message commit; surviving paths matched to current tree.
- `16dde4952ed839a9c0510724152104ada2157642` — exact registered-message commit; surviving paths matched to current tree.
- `8883e4e72c1fa161e7d9cebdc5950469a12525eb` — exact registered-message commit; surviving paths matched to current tree.
- `955dec6d673b577e026bf642e486270fcebbe7e3` — exact registered-message commit; surviving paths matched to current tree.
- `d9c80aea60aff03116b636bf0cfef70b230ee944` — exact registered-message commit; surviving paths matched to current tree.
- `0544c08ba118e13a48bd463d46c1dd242a883d51` — exact registered-message commit; surviving paths matched to current tree.
- `bf17c20b28ea3014e38ca89aca7f7cab8dd0a152` — exact registered-message commit; surviving paths matched to current tree.
- `4060db9b4e1c1b3f9620192b7b8a039d76b0df18` — exact registered-message commit; surviving paths matched to current tree.
- `ad2788cec5f0bf2fe1dd11f5738032d4046f5dd8` — exact registered-message commit; surviving paths matched to current tree.
- `aff7eed2154eaf5575c217762f49dee85f0f396e` — exact registered-message commit; surviving paths matched to current tree.
- `4275c2575a5599253b8e1272aac2250c61d2c58f` — exact registered-message commit; surviving paths matched to current tree.
- `390b16169508e40dd8048d409286676a37b204f1` — exact registered-message commit; surviving paths matched to current tree.
- `7ad50e710db9be808ed370c5b23d4eebc7117d29` — exact registered-message commit; surviving paths matched to current tree.
- `8b325109726fa8d4916dea8088dbb422a4f5d6fc` — exact registered-message commit; surviving paths matched to current tree.
- `9a9d8b652707f107ffe2e7583548921ca3a14e61` — exact registered-message commit; surviving paths matched to current tree.
- `838db607540e93cabfd82ecf2d90df3072029bda` — exact registered-message commit; surviving paths matched to current tree.
- `1adc4031c4920527133cba216b94b2d810e79837` — exact registered-message commit; surviving paths matched to current tree.
- `f602b70110299f25a96a49078c8dea3bc503b245` — exact registered-message commit; surviving paths matched to current tree.
- `67e1ece3ac7d4f059b2b4da3970d51fea487f73a` — exact registered-message commit; surviving paths matched to current tree.
- `fbbaf8ff6a078b5cc8af8bf5d5fbd67e175a112c` — exact registered-message commit; surviving paths matched to current tree.
- `9718a8ef25cd340e8ad6786d97295fb5f5a62f71` — exact registered-message commit; surviving paths matched to current tree.
- `fefd3532686e0d613cdcb832e12e9c793b46e739` — exact registered-message commit; surviving paths matched to current tree.
- `110811cf8c87e39db6ffc7ed7ac5032fe1da69d3` — exact registered-message commit; surviving paths matched to current tree.
- `ff0bc872489cfe7fb9451c213855d69df2d37947` — exact registered-message commit; surviving paths matched to current tree.
- `99592650b926131de42e28f820ab5623f2a83cf7` — exact registered-message commit; surviving paths matched to current tree.
- `f5c6c83d6bd11e734d79b6ada83100333cc94f2e` — exact registered-message commit; surviving paths matched to current tree.
- `796613d6f82e241977c02d7ceb443f13665e6939` — exact registered-message commit; surviving paths matched to current tree.
- `e11d122341a62fa5d8d22c665dc9674a28a165bb` — exact registered-message commit; surviving paths matched to current tree.
- `209830b6348587a5134aa1114f9d0438f2311fdc` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_01.md:103](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L103)
- [docs/roadmap/topology.md:109](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L109)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:109 | COR-009 | refactor(topology): replace enum usage in domain assets with type references | Completed | Domain/application assets use catalog reference value objects.
docs/roadmap/fixs/correction_01.md:103 | COR-009 | refactor(topology): replace enum usage in domain assets with type references | Completed | Refactor topology domain/application asset contracts to use references.
~~~~~~~~

<a id="hapi-073"></a>
## HAPI-073 — migrate topology assets to catalog foreign keys

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR-010

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add and backfill topology catalog FK columns in V004.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- `f991715a01c17a3f11583dd7690d9817449ac28f` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_01.md:104](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L104)
- [docs/roadmap/topology.md:110](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L110)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:110 | COR-010 | db(topology): migrate topology assets to catalog foreign keys | Completed | Adds and backfills catalog FK columns in V004.
docs/roadmap/fixs/correction_01.md:104 | COR-010 | db(topology): migrate topology assets to catalog foreign keys | Completed | Add and backfill topology catalog FK columns in V004.
~~~~~~~~

<a id="hapi-074"></a>
## HAPI-074 — update topology persistence mapping to catalog foreign keys

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR-011

Priority: P1

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Use catalog FK ids in topology persistence.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyNodeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/TopologyConnectionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/TopologyNodeJpaRepository.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyNodeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/TopologyConnectionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/TopologyNodeJpaRepository.java`

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

- `b3f4b2e6a122c21f69c95a5809cefabc1ddac03c` — exact registered-message commit; surviving paths matched to current tree.
- `10e6e5aef7f3f5df679638fce08a19956dcc50f9` — exact registered-message commit; surviving paths matched to current tree.
- `adfbc74023d0492960ac38df008e72ed0f430e56` — exact registered-message commit; surviving paths matched to current tree.
- `05c3de2079c929d4fd7d30568d9330d2c15c37de` — exact registered-message commit; surviving paths matched to current tree.
- `4eeffd7703ef9f105ef662e63757b4d32643facb` — exact registered-message commit; surviving paths matched to current tree.
- `43d4700676ff243d605872ca907b2544c1d25bc4` — exact registered-message commit; surviving paths matched to current tree.
- `7af0f74763eabc681ec8bcf8084260411d240281` — exact registered-message commit; surviving paths matched to current tree.
- `f084c17721e6e0d05ed58dc8e7a1e5bafd7ecfe3` — exact registered-message commit; surviving paths matched to current tree.
- `444cccaf6cbd6e9a78c08e33504bf313e679552c` — exact registered-message commit; surviving paths matched to current tree.
- `6d4db3b6c20006462f21d66892e1153c548a98c3` — exact registered-message commit; surviving paths matched to current tree.
- `36be67bd9001df7737c0ac6ce205f5bf505864a0` — exact registered-message commit; surviving paths matched to current tree.
- `0a05d6446a6d348b3ad47a39d6e3a130c413b081` — exact registered-message commit; surviving paths matched to current tree.
- `13e0bc2d76470d6a777a25a1edfed59137eb7f78` — exact registered-message commit; surviving paths matched to current tree.
- `fad47cecb028fa6dfcd80d0cf447eddfb5280cdd` — exact registered-message commit; surviving paths matched to current tree.
- `0b76fe2de0617ad5ca7059bd687b5970e31df27e` — exact registered-message commit; surviving paths matched to current tree.
- `ab6f4c70167ebd28b0ec6d9a689102674ad182ad` — exact registered-message commit; surviving paths matched to current tree.
- `05769eee43bbc8254ba7310b6e0501e76b86cddd` — exact registered-message commit; surviving paths matched to current tree.
- `593cd5cd3a763b465198c28e8ae01c6039986e25` — exact registered-message commit; surviving paths matched to current tree.
- `403b4fe24b1f28b71dd15c94ed33c4a9e7fe39af` — exact registered-message commit; surviving paths matched to current tree.
- `c04576d4e92375cbb8e67daa748046dedddf05b0` — exact registered-message commit; surviving paths matched to current tree.
- `99d79437507caa6a39e05ffc280cac224ff797f2` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_01.md:105](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L105)
- [docs/roadmap/topology.md:111](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L111)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:111 | COR-011 | refactor(topology): update topology persistence mapping to catalog foreign keys | Completed | Persistence writes/reads catalog FK references.
docs/roadmap/fixs/correction_01.md:105 | COR-011 | refactor(topology): update topology persistence mapping to catalog foreign keys | Completed | Use catalog FK ids in topology persistence.
~~~~~~~~

<a id="hapi-075"></a>
## HAPI-075 — update topology REST contracts for catalog types and localization

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR-012

Priority: P1

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

REST uses typeCode and localized type responses.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSystemRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/FacilityResponse.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSystemResponse.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSystemRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/FacilityResponse.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSystemResponse.java`

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

- `da0f2657b83104ca9c7ec0ba15ab6093dc3dcf79` — exact registered-message commit; surviving paths matched to current tree.
- `78a862b3d8cf31759a9048517c11ac661b24a362` — exact registered-message commit; surviving paths matched to current tree.
- `918a1dfbf7f77b751d21b633e01f82177b000799` — exact registered-message commit; surviving paths matched to current tree.
- `8a66115066463ebeea31a68dc8ad7c567af0b0a9` — exact registered-message commit; surviving paths matched to current tree.
- `8e667b998644907480587b2b7dcdf630548acbd7` — exact registered-message commit; surviving paths matched to current tree.
- `1833e514ee71f2a3387d14c3adcb4840c84dc820` — exact registered-message commit; surviving paths matched to current tree.
- `ec9a7f2d4d71c2613a8190e43da74c4924220fbc` — exact registered-message commit; surviving paths matched to current tree.
- `e78ef7934bc61a374b4b1bd7647d13bb3dee09f4` — exact registered-message commit; surviving paths matched to current tree.
- `497bcd08e2914ee9f387a564d7b774a273e43df1` — exact registered-message commit; surviving paths matched to current tree.
- `e70c4db1e5d7779dd879f8b22b52346a6dc1830a` — exact registered-message commit; surviving paths matched to current tree.
- `7f970089610f0e46555ff1b6e48caaa63cdd2ed8` — exact registered-message commit; surviving paths matched to current tree.
- `e1682391293b79b41f5c90a47ec9346ec4ceb6f8` — exact registered-message commit; surviving paths matched to current tree.
- `bce5bf735c3a7b22566c96ae43f6801e24b2dbc4` — exact registered-message commit; surviving paths matched to current tree.
- `dd7bee7d1e5c54da14da3a2df61720208cc03a4c` — exact registered-message commit; surviving paths matched to current tree.
- `eefbf220f2a53c6d6ce992b68c8a78f1915488dc` — exact registered-message commit; surviving paths matched to current tree.
- `85731cff6de456db5e95618ba4bfa613f0fe9084` — exact registered-message commit; surviving paths matched to current tree.
- `4305dc07bc5f5224d18ffd362f7cd9ae8fd3840d` — exact registered-message commit; surviving paths matched to current tree.
- `2792e1ff8c0740e3b8a14576746fd0f5902b37f0` — exact registered-message commit; surviving paths matched to current tree.
- `871a39481a883e7d177c73ec41161b931955bf3b` — exact registered-message commit; surviving paths matched to current tree.
- `79332ff2b95622c52bd58574180ee97105dbb20f` — exact registered-message commit; surviving paths matched to current tree.
- `2cc213cc5b3e27f7d5476f5a12806e55cf0b274d` — exact registered-message commit; surviving paths matched to current tree.
- `ec458dad6039e093e60683593e18364b3815e4a4` — exact registered-message commit; surviving paths matched to current tree.
- `ed7923d87a526968471b2bbe3d829cbedffa6e2d` — exact registered-message commit; surviving paths matched to current tree.
- `d096e220a618859c4645760381a3e0200e9dd5fc` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_01.md:106](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L106)
- [docs/roadmap/topology.md:112](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L112)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:112 | COR-012 | refactor(topology): update topology REST contracts for catalog types and localization | Completed | REST requests use `typeCode`; responses expose localized type labels.
docs/roadmap/fixs/correction_01.md:106 | COR-012 | refactor(topology): update topology REST contracts for catalog types and localization | Completed | REST uses `typeCode` and localized type responses.
~~~~~~~~

<a id="hapi-076"></a>
## HAPI-076 — remove topology enum-style type constraints

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR-013

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Remove old topology type varchar columns/checks in V005.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- `66d338c9f7ff059802995863bf9347f7095ace10` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_01.md:107](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L107)
- [docs/roadmap/topology.md:113](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L113)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:113 | COR-013 | db(topology): remove topology enum-style type constraints | Completed | Removes old varchar taxonomy columns and old enum-style checks in V005.
docs/roadmap/fixs/correction_01.md:107 | COR-013 | db(topology): remove topology enum-style type constraints | Completed | Remove old topology type varchar columns/checks in V005.
~~~~~~~~

<a id="hapi-077"></a>
## HAPI-077 — repair unstable topology tests

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR-014

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Stabilize topology fixtures and tests after catalog conversion.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- `728e0e843002bed58dfd443a4991f1140b70073c` — exact registered-message commit; surviving paths matched to current tree.
- `94dd7c8c933c76c9e619af913da05f6fd1af3be4` — exact registered-message commit; surviving paths matched to current tree.
- `894292561c9aea9a22e326931cffa554be85333e` — exact registered-message commit; surviving paths matched to current tree.
- `dd62a5a2cbfcae41c3e3265fd1612a36c5556a55` — exact registered-message commit; surviving paths matched to current tree.
- `882e52f9cc958a39b0d121553862871fca7cf3ee` — exact registered-message commit; surviving paths matched to current tree.
- `3423c4d26e928e289155e8892f6538355bb4a0b3` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_01.md:108](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L108)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/correction_01.md:108 | COR-014 | test(topology): repair unstable topology tests | Completed | Stabilize topology fixtures and tests after catalog conversion.
~~~~~~~~

<a id="hapi-080"></a>
## HAPI-080 — update topology roadmap after catalog refactor

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR-017

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Update topology roadmap/checklist after catalog refactor.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/topology.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/topology.md`

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

- `429c8a2ae02a1d9bf93ce16fcd310e4535d8ee3b` — exact registered-message commit; surviving paths matched to current tree.
- `0960721c46e8f6f4ee25454efdacd7596519c6c5` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_01.md:111](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L111)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/correction_01.md:111 | COR-017 | docs(topology): update topology roadmap after catalog refactor | Completed | Update topology roadmap/checklist after catalog refactor.
~~~~~~~~

<a id="hapi-082"></a>
## HAPI-082 — remove remaining business taxonomy enum declarations

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR-019

Priority: P1

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Replace remaining forbidden enum declarations with compatibility classes.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/organization/domain/value/OperationalScopeType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/NodeType.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/value/OperationalScopeType.java`, `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/NodeType.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/organization/domain/value/OperationalScopeType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/NodeType.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 5. COR-019 record

```text
Commit code    : COR-019
Commit message : refactor(catalog): remove remaining business taxonomy enum declarations
Type           : Refactor
Module         : catalog
```

### Goal

Remove remaining forbidden Java enum declarations that blocked `ControlledVocabularyArchitectureTest`.

### Files updated

```text
src/main/java/dz/sh/hidra/modules/topology/domain/value/ProductType.java
src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityType.java
src/main/java/dz/sh/hidra/modules/topology/domain/value/NodeType.java
src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineAppurtenanceType.java
src/main/java/dz/sh/hidra/modules/topology/domain/value/ValveType.java
src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentType.java
src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java
src/main/java/dz/sh/hidra/modules/organization/domain/value/OperationalScopeType.java
src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java
```

### Rule

These compatibility files may temporarily remain as final classes, but must not be Java enums.

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

- `e336e8b8768da09fd764ef1874d103f0010642a3` — exact registered-message commit; surviving paths matched to current tree.
- `66366a2b883cf03daf32381a67c2a9a2b3386dc6` — exact registered-message commit; surviving paths matched to current tree.
- `807acf4847b09792349ed2945a7d8bfbd23b0d7c` — exact registered-message commit; surviving paths matched to current tree.
- `e20b7a3e00f22431e92d1ec63e1de54ebd881f7a` — exact registered-message commit; surviving paths matched to current tree.
- `4cfcddb32c28a945f258037cab9b7f4cc62f33e9` — exact registered-message commit; surviving paths matched to current tree.
- `0221b34f2f87b69810db8dc00d060beea4f13835` — exact registered-message commit; surviving paths matched to current tree.
- `caec06475f297982d09583191c1201014b174bb6` — exact registered-message commit; surviving paths matched to current tree.
- `1e1e1dea0afd558fdea50c7f0bc636a368ff228f` — exact registered-message commit; surviving paths matched to current tree.
- `19b07b4a8eb8a1ad1db033b36916dc6227af09bf` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_01.md:113](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L113)
- [docs/roadmap/fixs/correction_01.md:117](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L117)
- [docs/roadmap/topology.md:114](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L114)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:114 | COR-019 | refactor(catalog): remove remaining business taxonomy enum declarations | Completed | Removes remaining forbidden business taxonomy enum declarations.
docs/roadmap/fixs/correction_01.md:113 | COR-019 | refactor(catalog): remove remaining business taxonomy enum declarations | Completed | Replace remaining forbidden enum declarations with compatibility classes.
~~~~~~~~

<a id="hapi-083"></a>
## HAPI-083 — record COR-019 and corrected baseline validation path

Phase: HPS-004 — Physical Network Topology

Legacy IDs: COR-020

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Record COR-019/COR-020 and add transition constructor fix.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/kernel/pagination/PageRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/kernel/pagination/PageRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 6. COR-020 record

```text
Commit code    : COR-020
Commit message : docs(correction): record COR-019 and corrected baseline validation path
Type           : Documentation / Compile Compatibility
Module         : correction
```

### Goal

Record COR-019 in the correction roadmap and document the corrected baseline validation path.

Also repair the reported transitional compile error:

```text
The constructor ListFacilitiesQuery(String, FacilityTypeReference, ProductType, TopologyStatus, PageRequest) is undefined
```

### Files updated

```text
docs/roadmap/correction_01.md
src/main/java/dz/sh/hidra/modules/topology/application/query/ListFacilitiesQuery.java
```

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

- `2ca1232187a61272fcd6d62b7aaa5e399701b9dc` — exact registered-message commit; surviving paths matched to current tree.
- `221901fffe1e73057e025ad36d71f3a3b25f783b` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/correction_01.md:114](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L114)
- [docs/roadmap/fixs/correction_01.md:148](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/correction_01.md#L148)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/correction_01.md:114 | COR-020 | docs(correction): record COR-019 and corrected baseline validation path | Completed | Record COR-019/COR-020 and add transition constructor fix.
~~~~~~~~

<a id="hapi-169"></a>
## HAPI-169 — scan topology domain models

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HMC-024

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 5 topology model files.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FlowDirection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FlowDirection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 33. HMC-024 — Topology module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `topology`  
**Baseline model files:** 5  
**Scanned models:** 5  
**Declared fields/components:** 73  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Topology model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| Equipment | record | 16 | `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java` | Extracted |
| Facility | record | 18 | `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java` | Extracted |
| Pipeline | record | 15 | `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java` | Extracted |
| PipelineSystem | record | 12 | `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java` | Extracted |
| TopologyConnection | record | 12 | `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java` | Extracted |

### Topology fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| Equipment | 1 | id | String |
| Equipment | 2 | code | String |
| Equipment | 3 | name | String |
| Equipment | 4 | facilityId | String |
| Equipment | 5 | nodeId | String |
| Equipment | 6 | pipelineSegmentId | String |
| Equipment | 7 | equipmentTypeId | String |
| Equipment | 8 | equipmentKind | EquipmentKind |
| Equipment | 9 | manufacturerPartyId | String |
| Equipment | 10 | manufacturerPartyCodeSnapshot | String |
| Equipment | 11 | manufacturerPartyNameSnapshot | String |
| Equipment | 12 | status | EquipmentStatus |
| Equipment | 13 | installedAt | Instant |
| Equipment | 14 | retiredAt | Instant |
| Equipment | 15 | createdAt | Instant |
| Equipment | 16 | updatedAt | Instant |
| Facility | 1 | id | String |
| Facility | 2 | code | String |
| Facility | 3 | nameAr | String |
| Facility | 4 | nameFr | String |
| Facility | 5 | nameEn | String |
| Facility | 6 | facilityTypeId | String |
| Facility | 7 | facilityKind | FacilityKind |
| Facility | 8 | ownerPartyId | String |
| Facility | 9 | ownerPartyCodeSnapshot | String |
| Facility | 10 | ownerPartyNameSnapshot | String |
| Facility | 11 | latitude | BigDecimal |
| Facility | 12 | longitude | BigDecimal |
| Facility | 13 | elevationMeters | BigDecimal |
| Facility | 14 | status | FacilityStatus |
| Facility | 15 | commissionedAt | Instant |
| Facility | 16 | retiredAt | Instant |
| Facility | 17 | createdAt | Instant |
| Facility | 18 | updatedAt | Instant |
| Pipeline | 1 | id | String |
| Pipeline | 2 | pipelineSystemId | String |
| Pipeline | 3 | code | String |
| Pipeline | 4 | nameAr | String |
| Pipeline | 5 | nameFr | String |
| Pipeline | 6 | nameEn | String |
| Pipeline | 7 | pipelineType | PipelineType |
| Pipeline | 8 | nominalDiameter | BigDecimal |
| Pipeline | 9 | diameterUnitCode | String |
| Pipeline | 10 | designPressure | BigDecimal |
| Pipeline | 11 | pressureUnitCode | String |
| Pipeline | 12 | totalLengthKm | BigDecimal |
| Pipeline | 13 | status | TopologyStatus |
| Pipeline | 14 | createdAt | Instant |
| Pipeline | 15 | updatedAt | Instant |
| PipelineSystem | 1 | id | String |
| PipelineSystem | 2 | code | String |
| PipelineSystem | 3 | nameAr | String |
| PipelineSystem | 4 | nameFr | String |
| PipelineSystem | 5 | nameEn | String |
| PipelineSystem | 6 | systemType | PipelineSystemType |
| PipelineSystem | 7 | status | TopologyStatus |
| PipelineSystem | 8 | description | String |
| PipelineSystem | 9 | commissionedAt | Instant |
| PipelineSystem | 10 | retiredAt | Instant |
| PipelineSystem | 11 | createdAt | Instant |
| PipelineSystem | 12 | updatedAt | Instant |
| TopologyConnection | 1 | id | String |
| TopologyConnection | 2 | code | String |
| TopologyConnection | 3 | fromNodeId | String |
| TopologyConnection | 4 | toNodeId | String |
| TopologyConnection | 5 | connectionType | ConnectionType |
| TopologyConnection | 6 | flowDirection | FlowDirection |
| TopologyConnection | 7 | pipelineSegmentId | String |
| TopologyConnection | 8 | nominalCapacity | BigDecimal |
| TopologyConnection | 9 | capacityUnitCode | String |
| TopologyConnection | 10 | status | TopologyStatus |
| TopologyConnection | 11 | createdAt | Instant |
| TopologyConnection | 12 | updatedAt | Instant |

### HMC-024 reconciliation

```text
baseline model files = 5
extracted models     = 5
exceptions           = 0
declared fields      = 73
zero-field models    = 0
```

The scan records only declarations under `topology/domain/model`. Topology domain value types referenced by record components remain declared field types and are not expanded into independent model rows.

No JPA entity, API DTO, migration, application contract, infrastructure model, GIS implementation, party implementation, telemetry implementation, simulation implementation, or database column metadata was mixed into this module scan.

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

- `6bbf4168a030bac1bf07a83b4aa3dc9ae0e640bf` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:152](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L152)
- [docs/roadmap/model-field-catalogue.md:2797](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L2797)
- [docs/roadmap/model-field-catalogue.md:2895](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L2895)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:152 | HMC-024 | docs(catalogue): scan topology domain models | Scan all 5 topology model files. | **Completed** — 5/5 models, 73 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-201"></a>
## HAPI-201 — remediate semantic review PipelineSystem

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HMR-022

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review PipelineSystem

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Topology.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/topology.md`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSystemRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSystemResponse.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineSystemSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineSystemRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemFacilityJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemTypeJpaRepository.java`
- `src/main/resources/db/migration/V20261004_022__hmr_022_topology_pipeline_system.sql`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSystemSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineSystemRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemTypeJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Topology.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/topology.md`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSystemRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSystemResponse.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineSystemSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineSystemRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemFacilityJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemTypeJpaRepository.java`
- `src/main/resources/db/migration/V20261004_022__hmr_022_topology_pipeline_system.sql`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSystemSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-022 — topology.PipelineSystem

- Source review: `HMSR-024`
- Exact commit: `fix(topology): remediate semantic review PipelineSystem`
- Status: **Completed** — dedicated Topology-owned PipelineSystem classification catalog/reference implemented; the six evidence-backed legacy codes are migrated, the enum-style `system_type` representation is retired, and missing/unknown create classification now fails closed without silent `TRANSPORT` defaulting.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 2
- Additive Flyway: `src/main/resources/db/migration/V20261004_022__hmr_022_topology_pipeline_system.sql`
- Owner-contract prerequisite: No cross-module owner contract is required; the classification catalog remains Topology-owned. HMR-022A is the local architecture/persistence prerequisite.
- Exact write allowlist:
  - `docs/data definition/Topology.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/topology.md`
  - `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSystemRequest.java`
  - `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSystemResponse.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineSystemSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineSystemRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemFacilityJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemTypeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemTypeJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_022__hmr_022_topology_pipeline_system.sql`
  - `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSystemSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PipelineSystemSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Reconcile PipelineSystem business classification with the accepted Topology catalog policy.** Replace or explicitly redesign the current fixed `PipelineSystemType/system_type` representation so the final PipelineSystem contract is consistent with the repository's catalog-backed business-type architecture. The correction must define the authoritative catalog/reference semantics rather than blindly assuming `ProductTypeReference`; domain, application, REST, JPA and additive Flyway changes must remain aligned in the separately authorized Topology task.
  2. **Remove the unsupported silent `TRANSPORT` inference.** Missing PipelineSystem classification must not be converted to `TRANSPORT` unless an explicit Topology business rule establishes that default. The corrected write boundary should fail closed or use the explicitly approved classification/catalog contract.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSystemSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `3095625652aaa5fc9c44bfade20bfb4cf95b0c97` — exact registered-message commit; surviving paths matched to current tree.
- `7d5d289a6ce7cb18d959344597a1c69bb60026f1` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:168](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L168)
- [docs/roadmap/model-semantic-remediation.md:1372](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L1372)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:168 | HMR-022 | HMSR-024 | topology | PipelineSystem | — | — | fix(topology): remediate semantic review PipelineSystem | Planned
~~~~~~~~

<a id="hapi-213"></a>
## HAPI-213 — remediate semantic review TopologyConnection

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HMR-031

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review TopologyConnection

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Topology.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/topology.md`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyConnectionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/exception/InvalidTopologyConnectionException.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/service/TopologyConnectionValidator.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FlowDirection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaTopologyConnectionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/ConnectionTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/ConnectionTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/TopologyConnectionJpaRepository.java`
- `src/main/resources/db/migration/V20261004_031__hmr_031_topology_topology_connection.sql`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/TopologyConnectionSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/exception/InvalidTopologyConnectionException.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/service/TopologyConnectionValidator.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyConnectionRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaTopologyConnectionRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/ConnectionTypeJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Topology.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/topology.md`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyConnectionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/exception/InvalidTopologyConnectionException.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/service/TopologyConnectionValidator.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FlowDirection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaTopologyConnectionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/ConnectionTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/ConnectionTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/TopologyConnectionJpaRepository.java`
- `src/main/resources/db/migration/V20261004_031__hmr_031_topology_topology_connection.sql`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/TopologyConnectionSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-031 — topology.TopologyConnection

- Source review: `HMSR-034`
- Exact commit: `fix(topology): remediate semantic review TopologyConnection`
- Status: **Completed** — TopologyConnection now uses catalog-backed ConnectionTypeReference semantics, rejects self-loops at domain/database boundaries, and protects populated PipelineSegment references.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_031__hmr_031_topology_topology_connection.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Topology.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/topology.md`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyConnectionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/exception/InvalidTopologyConnectionException.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/service/TopologyConnectionValidator.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaTopologyConnectionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/ConnectionTypeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/TopologyConnectionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/ConnectionTypeJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_031__hmr_031_topology_topology_connection.sql`
  - `src/test/java/dz/sh/hidra/modules/topology/semantic/TopologyConnectionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=TopologyConnectionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Restore/reconcile catalog-backed connection-type semantics.** Replace the live fixed `ConnectionType` enum/string persistence with the accepted `ConnectionTypeReference`/catalog architecture, or explicitly revise the governing Topology architecture if that earlier decision is no longer intended. Domain, JPA, Flyway, API/application contracts and migration strategy must converge on one representation.
  2. **Enforce the no-self-connection domain rule on authoritative writes.** `fromNodeId` and `toNodeId` must not resolve to the same node when a TopologyConnection becomes authoritative. The existing unused validator is insufficient by itself.
  3. **Protect populated PipelineSegment references.** When `pipelineSegmentId` is non-null, it must resolve to the retained Topology PipelineSegment target through an additive nullable FK, application validation, guarded provisioning path, or equivalent fail-closed mechanism.


~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/semantic/TopologyConnectionSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `ecf9b13cf90bf9e3602be0099c87fd2575e54bb7` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/data definition/Topology.md:241](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Topology.md#L241)
- [docs/roadmap/model-semantic-remediation.md:177](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L177)
- [docs/roadmap/model-semantic-remediation.md:1786](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L1786)
- [docs/roadmap/topology.md:515](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L515)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:177 | HMR-031 | HMSR-034 | topology | TopologyConnection | — | — | fix(topology): remediate semantic review TopologyConnection | Planned
~~~~~~~~

<a id="hapi-220"></a>
## HAPI-220 — remediate semantic review Facility

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HMR-036

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review Facility

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Topology.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/topology.md`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/package-info.java`
- `src/main/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/RegisterFacilityRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/FacilityResponse.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/command/RegisterFacilityCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/dto/FacilitySummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/FacilityRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaFacilityRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityNodeBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityTypeVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityAttributeDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityAttributeValueJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityNodeBindingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityTypeVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemFacilityJpaRepository.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/FacilityRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java`, `src/test/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaFacilityRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeDefinitionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeValueJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Topology.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/topology.md`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/package-info.java`
- `src/main/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/RegisterFacilityRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/FacilityResponse.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/command/RegisterFacilityCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/dto/FacilitySummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/FacilityRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaFacilityRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityNodeBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityTypeVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityAttributeDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityAttributeValueJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityNodeBindingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityTypeVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemFacilityJpaRepository.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-036 — topology.Facility

- Source review: `HMSR-041`
- Exact commit: `fix(topology): remediate semantic review Facility`
- Status: **Completed** — populated Facility ownerPartyId is validated fail-closed through the Party-owned Topology application contract; snapshots remain non-authoritative and no cross-module FK is introduced.
- SCC: —
- Recorded upstream HMS dependencies: party.Party
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_036__hmr_036_topology_facility.sql`
- Owner-contract prerequisite: No suitable exported owner lookup found in live tree for party; task must introduce/authorize an owner contract by roadmap amendment before cross-module validation changes.
- Exact write allowlist:
  - `docs/data definition/Topology.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/topology.md`
  - `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/RegisterFacilityRequest.java`
  - `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/FacilityResponse.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/command/RegisterFacilityCommand.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/dto/FacilitySummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/out/FacilityRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaFacilityRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeDefinitionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeValueJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityNodeBindingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityTypeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityTypeVersionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityAttributeDefinitionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityAttributeValueJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityNodeBindingJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityTypeJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityTypeVersionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemFacilityJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_036__hmr_036_topology_facility.sql`
  - `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=FacilitySemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Validate populated Facility owner Party references through the Party owner boundary.** When `ownerPartyId` is supplied, Topology must fail closed unless that Party identity exists under the Party bounded context. Do not introduce a cross-module database FK. A separately authorized Topology/Party correction should use a deliberate Party lookup/contract or equivalent owner-controlled validation boundary and preserve snapshots as non-authoritative evidence.

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
- `src/test/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `b01c700805b3125f0173ee3ac80e9f28d9997920` — exact registered-message commit; surviving paths matched to current tree.
- `71dce59eddf14b1a2c696b9d477d3cb673699df6` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/data definition/Topology.md:279](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Topology.md#L279)
- [docs/roadmap/model-semantic-remediation.md:187](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L187)
- [docs/roadmap/model-semantic-remediation.md:2052](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2052)
- [docs/roadmap/topology.md:571](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L571)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:187 | HMR-036 | HMSR-041 | topology | Facility | — | party.Party | fix(topology): remediate semantic review Facility | Blocked — owner lookup contract prerequisite unresolved
~~~~~~~~

<a id="hapi-229"></a>
## HAPI-229 — remediate semantic review Pipeline

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HMR-042

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review Pipeline

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Topology.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/topology.md`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSystemRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSystemResponse.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineSystemSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSegmentType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineSystemRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSegmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemFacilityJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/visualization/JpaTopologyMapVisualizationAdapter.java`
- `src/main/resources/db/migration/V20261004_042__hmr_042_topology_pipeline.sql`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSegmentType.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineSystemRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Topology.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/topology.md`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSystemRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSystemResponse.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineSystemSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSegmentType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineSystemRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSegmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemFacilityJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/visualization/JpaTopologyMapVisualizationAdapter.java`
- `src/main/resources/db/migration/V20261004_042__hmr_042_topology_pipeline.sql`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-042 — topology.Pipeline

- Source review: `HMSR-050`
- Exact commit: `fix(topology): remediate semantic review Pipeline`
- Status: **Completed** — Pipeline classification now uses an open Topology-owned catalog reference with seven migrated compatibility codes, FK-backed persistence, validated legacy backfill and stable-code visualization; TopologyStatus remains the lifecycle enum.
- SCC: —
- Recorded upstream HMS dependencies: topology.PipelineSystem
- HMSR correction count: 1
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Topology.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/topology.md`
  - `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSystemRequest.java`
  - `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSystemResponse.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineSystemSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSegmentType.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineSystemRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSegmentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemFacilityJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PipelineSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Replace Pipeline's fixed `PipelineType` business taxonomy with the repository-approved catalog-reference architecture.** The correction must reconcile domain, persistence, Flyway/data migration, API/read contracts and existing values with the accepted Topology type-catalog design, using stable catalog codes/localized labels rather than a fixed business enum. Preserve `TopologyStatus` as a lifecycle enum unless separate evidence changes that classification.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `4c67d06e8ea6e4ebf42213fb9ad189514f82be83` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:193](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L193)
- [docs/roadmap/model-semantic-remediation.md:2345](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2345)
- [docs/roadmap/topology.md:580](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L580)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:193 | HMR-042 | HMSR-050 | topology | Pipeline | — | topology.PipelineSystem | fix(topology): remediate semantic review Pipeline | Planned
~~~~~~~~

<a id="hapi-230"></a>
## HAPI-230 — register Pipeline catalog prerequisite

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HMR-042A

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

register Pipeline catalog prerequisite

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/visualization/JpaTopologyMapVisualizationAdapter.java`
- `src/main/resources/db/migration/V20261004_042__hmr_042_topology_pipeline.sql`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineTypeJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineTypeJpaRepository.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/visualization/JpaTopologyMapVisualizationAdapter.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/visualization/JpaTopologyMapVisualizationAdapter.java`
- `src/main/resources/db/migration/V20261004_042__hmr_042_topology_pipeline.sql`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-042A — Pipeline classification catalog prerequisite

- Source: HMR-042 / HMSR-050 plus live Pipeline/PipelineSystem catalog evidence.
- Exact commit: `docs(model-remediation): register Pipeline catalog prerequisite`
- Status: **Completed** — the missing Pipeline classification catalog persistence/migration surface and minimal downstream compatibility scope are authorized; HMR-042A itself changes documentation only.
- Type: documentation/architecture/schema prerequisite.
- Live evidence:
  1. `Pipeline.pipelineType` is still the fixed enum `PipelineType` with values `CRUDE_OIL`, `CONDENSATE`, `NATURAL_GAS`, `LPG`, `MULTI_PRODUCT`, `WATER`, `OTHER`.
  2. `PipelineJpaEntity` still persists that enum directly in legacy column `pipeline_type`.
  3. No `PipelineTypeJpaEntity`, `PipelineTypeJpaRepository`, `hidra_topology_pipeline_type`, or `pipeline_type_id` exists on live `main`.
  4. The repository-approved Topology catalog pattern already exists for `PipelineSystemType` and `ConnectionTypeReference`: dedicated catalog table/entity/repository, domain reference object, code lookup at the application/repository boundary, FK-backed persisted ID, and migration/backfill from former enum/string values.
  5. HMR-042 explicitly requires Flyway/data migration, but its original registration had no additive migration authorization.
  6. `PipelineType` is consumed by the topology map visualization adapter and existing topology application tests outside the original HMR-042 allowlist; those callers must migrate to stable catalog-code semantics to keep the repository compiling without reintroducing enum coupling.
- Required decisions:
  1. Convert `PipelineType` from a closed business enum into the open Pipeline classification reference object, preserving the seven existing codes only as compatibility constants/seeded references, not as an exhaustive taxonomy.
  2. Introduce a dedicated Topology-owned `hidra_topology_pipeline_type` catalog following the proven PipelineSystemType/ConnectionType catalog shape: stable `id`, unique stable `code`, optional `name_ar/name_fr/name_en`, `active`, and timestamps.
  3. Seed exactly the seven codes already present in the former enum. Do not invent localized labels or new classifications.
  4. Add an immutable HMR-042 Flyway migration that validates legacy values, backfills `pipeline_type_id`, adds/validates the same-module FK, indexes the reference, and removes the legacy `pipeline_type` column only after successful backfill.
  5. Pipeline persistence must store the catalog FK/reference, not an enum string.
  6. Repository/application reads expose stable catalog code and optional localized labels. Existing lifecycle `TopologyStatus` remains an enum.
  7. Visualization must emit the Pipeline type stable code, not enum-name reflection.
  8. No generic/shared catalog abstraction is authorized; keep the correction Topology/Pipeline-specific.
- Newly authorized HMR-042 production files in addition to the original allowlist:
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineTypeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineTypeJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/visualization/JpaTopologyMapVisualizationAdapter.java`
  - `src/main/resources/db/migration/V20261004_042__hmr_042_topology_pipeline.sql`
- Newly authorized HMR-042 compatibility test file:
  - `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryServiceTest.java`
- HMR-042 remains the current production remediation after this prerequisite is observed. Do not start HMR-043 automatically.


~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryServiceTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `4f62ab53977b7f8ed2ffd258906c9b226cff63be` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:2392](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2392)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-334"></a>
## HAPI-334 — review topology PipelineSystem

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HMSR-024

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review topology PipelineSystem

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSystemRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSystemResponse.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineSystemSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSystemRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSystemResponse.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineSystemSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 37. HMSR-024 — topology.PipelineSystem review

**Decision:** REVISE  
**Review code:** HMSR-024  
**Dependency level:** 0  
**Bounded context:** topology  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `topology.Pipeline` through `pipelineSystemId`  
**Transitive dependents:** 1  
**Unresolved/non-subject references:** 0

### 37.1 Semantic role and ordering rationale

`PipelineSystem` is the Topology-owned logical transportation-system aggregate that groups pipelines and participates in broader system/facility associations. It is part of the canonical physical/logical network backbone used by downstream operational, telemetry, simulation, risk, visualization and responsibility-scope use cases.

It is Level 0 because it has no confirmed upstream HMS subject-model dependency. `Pipeline` is the one direct HMS subject dependent through mandatory `pipelineSystemId`.

Topology DDD correctly owns PipelineSystem and explicitly excludes organization, identity, telemetry values, workflow approvals, integrity findings, HSE cases and other foreign bounded-context state.

### 37.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable PipelineSystem identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable language-neutral topology business code. |
| `nameAr` | `String` | Optional localized text | Arabic system name. |
| `nameFr` | `String` | Optional localized text | French system name. |
| `nameEn` | `String` | Optional localized text | English system name. |
| `systemType` | `PipelineSystemType` | Mandatory in current live model | Current fixed classification enum: `TRANSPORT`, `GATHERING`, `DISTRIBUTION`, `EXPORT`, `IMPORT`, or `MIXED`. This is the principal semantic inconsistency identified by HMSR-024. |
| `status` | `TopologyStatus` | Mandatory | Lifecycle state: `DRAFT`, `ACTIVE`, `SUSPENDED`, `RETIRED`, or `ARCHIVED`. |
| `description` | `String` | Optional | Free-form system description. |
| `commissionedAt` | `Instant` | Optional | Commissioning timestamp when known. |
| `retiredAt` | `Instant` | Optional | Retirement timestamp when known. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The current compact constructor already rejects blank `id` and `code`, requires non-null `systemType` and `status`, and normalizes textual values.

### 37.3 Topology ownership and downstream Pipeline boundary

The validated HMS graph contains:

```text
Pipeline.pipelineSystemId
    -> PipelineSystem.id
```

`Pipeline.pipelineSystemId` is mandatory in domain and JPA state. HRA-111 installs `fk_hra111_topology_020`, linking:

```text
hidra_topology_pipeline.pipeline_system_id
    -> hidra_topology_pipeline_system.id
```

with `ON DELETE RESTRICT`.

Topology persistence also contains non-HMS-subject association models such as `PipelineSystemFacility` that point to PipelineSystem. Those persistence dependents reinforce PipelineSystem as stable topology master identity but do not change the HMS direct-subject dependent count.

### 37.4 Accepted catalog policy versus live PipelineSystem classification

The active Topology roadmap states:

```text
Topology business taxonomy concepts are no longer roadmap-accepted as Java enums.
```

and records the catalog correction sequence as completed. The same roadmap says business type concepts are modeled through catalog references with stable codes/localized labels and that REST create/list requests use stable `typeCode` fields.

The current architecture domain schema also represents PipelineSystem with a catalog-style reference:

```text
PipelineSystem
  ...
  ProductTypeReference productType
  TopologyStatus status
```

and the controlled-vocabulary correction history classifies `TopologyStatus` as an intentional lifecycle enum while requiring business-type enums to be replaced by catalog/reference semantics.

However, live PipelineSystem remains:

```text
PipelineSystemType systemType
```

where `PipelineSystemType` is a Java enum containing:

```text
TRANSPORT
GATHERING
DISTRIBUTION
EXPORT
IMPORT
MIXED
```

The JPA entity persists this as an enumerated string in mandatory `system_type`, and the base Flyway table still defines:

```text
system_type varchar(80) NOT NULL
```

No live migration reviewed by HMSR-024 replaces this field with a catalog FK/reference.

### 37.5 Application/API inconsistency with the accepted type-code contract

The unfinished taxonomy shape reaches every active write/read boundary:

- `CreatePipelineSystemCommand` accepts `PipelineSystemType systemType`;
- `CreatePipelineSystemRequest` exposes the domain enum directly;
- `PipelineSystemSummaryDto` exposes the same enum;
- `PipelineSystemResponse` exposes the same enum;
- `PipelineSystemApplicationService` constructs the aggregate using that enum.

This conflicts with the accepted Topology roadmap's general post-correction contract that create/list inputs use stable catalog `typeCode` values and responses expose catalog/localized type semantics rather than fixed business taxonomy enums.

HMSR-024 does **not** assume that the correct replacement is necessarily the existing `ProductTypeReference` despite the architecture diagram. The live enum values describe transportation-system classification, while "product type" usually represents transported hydrocarbon/product semantics. Current repository evidence is contradictory enough that a dedicated Topology correction must decide whether PipelineSystem needs its own catalog-backed system classification, an existing approved catalog, or an explicitly revised architecture contract.

What is proven is that the current fixed Java enum cannot simultaneously be the final model while the accepted Topology policy says business type concepts are catalog-backed and the catalog correction sequence is complete.

### 37.6 Unsupported silent TRANSPORT default

`PipelineSystemApplicationService.createPipelineSystem()` currently applies:

```text
command.systemType() == null
    ? PipelineSystemType.TRANSPORT
    : command.systemType()
```

The domain itself says `systemType` is mandatory, but no Topology DDD rule, roadmap rule, migration rule or application contract reviewed by HMSR-024 states that an omitted classification semantically means `TRANSPORT`.

Therefore the service can silently invent a business classification instead of failing closed or resolving an explicitly supplied catalog/reference value.

This default is especially problematic while the type model itself is unresolved: data provisioned or created through this path could encode `TRANSPORT` simply because the caller supplied no type, not because authoritative source data established that classification.

### 37.7 Multilingual and lifecycle semantics

`nameAr/nameFr/nameEn` provide an embedded multilingual system name. Current repository evidence does not require every language to be non-null, so HMSR-024 does not invent a completeness rule.

`TopologyStatus` is explicitly distinguished from business taxonomy in the correction audit and retained as a lifecycle enum. New systems are created as `DRAFT`, which is conservative and consistent with the current status vocabulary.

The repository does not define a complete PipelineSystem status-transition matrix, so HMSR-024 does not invent one.

### 37.8 Commissioning/retirement semantics deliberately not invented

`commissionedAt` and `retiredAt` are optional in both domain and persistence.

Although chronological rules may be intuitively desirable, the repository-wide invariant classification lists five approved Topology temporal invariants and does not include a PipelineSystem `commissionedAt <= retiredAt` rule. HMSR-024 therefore does not promote that assumption into the semantic baseline.

Likewise, no evidence reviewed here requires `retiredAt` whenever status is `RETIRED`, forbids it for other statuses, or requires `commissionedAt` before activation.

### 37.9 Code and persistence semantics deliberately not invented

The base schema indexes `PipelineSystem.code` but does not declare it unique. Current active Topology DDD/roadmap evidence reviewed here does not state the exact uniqueness scope for PipelineSystem code, so HMSR-024 does not invent one.

The live domain and JPA models otherwise agree on all 12 current components, and generic `createdAt`/`updatedAt` requirements remain persistence/application-boundary audit concerns under the HRA invariant policy.

### 37.10 Required revisions

Two evidence-backed obligations remain:

1. **Reconcile PipelineSystem business classification with the accepted Topology catalog policy.** Replace or explicitly redesign the current fixed `PipelineSystemType/system_type` representation so the final PipelineSystem contract is consistent with the repository's catalog-backed business-type architecture. The correction must define the authoritative catalog/reference semantics rather than blindly assuming `ProductTypeReference`; domain, application, REST, JPA and additive Flyway changes must remain aligned in the separately authorized Topology task.

2. **Remove the unsupported silent `TRANSPORT` inference.** Missing PipelineSystem classification must not be converted to `TRANSPORT` unless an explicit Topology business rule establishes that default. The corrected write boundary should fail closed or use the explicitly approved classification/catalog contract.

HMSR-024 does not modify production Java, JPA, Flyway, REST/application contracts, tests, topology master data or provisioned data.

### 37.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, PipelineSystem is a high-level network grouping used to organize pipelines and associated facilities. Whether a system is transport, gathering, export, import, distribution or mixed is operational master-data classification, not merely a technical implementation detail.

Such classification should be authoritative and traceable. Silently classifying an unspecified system as `TRANSPORT` can misrepresent network semantics, while hard-coding an evolving business classification into a Java enum conflicts with Hidra's accepted catalog-governance direction.

Topology remains the owner of this network identity and classification; other bounded contexts should consume stable topology references rather than duplicate or redefine the system taxonomy.

### 37.12 Review conclusion

**REVISE.** `PipelineSystem` has a coherent Topology ownership role, correct Level-0 placement, stable 12-field current representation, appropriate multilingual names, intentional lifecycle status and a database-protected downstream Pipeline relationship.

The target baseline cannot mark it APPROVED while live domain/JPA/API/schema still use fixed `PipelineSystemType/system_type` business taxonomy despite the accepted completed catalog policy, and while the create service silently invents `TRANSPORT` when classification is absent.

HMS reconciliation must retain these obligations until a separately authorized Topology correction reconciles the classification model and removes unsupported defaulting, or the accepted Topology catalog architecture is explicitly revised with stronger repository evidence.

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

- `f2c36c5cc0dc90cedd7d934e328da5af06bb90c9` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:76](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L76)
- [docs/roadmap/model-semantic-review.md:219](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L219)
- [docs/roadmap/model-semantic-review.md:6165](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L6165)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:219 | HMSR-024 | 0 | topology | PipelineSystem | — | — | 1 | 1 | 0 | REVISE | Completed | docs(model-review): review topology PipelineSystem
doc/domain/SEMANTIC_DECISIONS.md:76 | HMSR-024 | [topology.PipelineSystem](#topology-pipelinesystem) | REVISE | HMR-022
~~~~~~~~

<a id="hapi-344"></a>
## HAPI-344 — review topology TopologyConnection

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HMSR-034

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review topology TopologyConnection

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/service/TopologyConnectionValidator.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FlowDirection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/service/TopologyConnectionValidator.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/service/TopologyConnectionValidator.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FlowDirection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 47. HMSR-034 — topology.TopologyConnection review

**Decision:** REVISE  
**Review code:** HMSR-034  
**Dependency level:** 0  
**Bounded context:** topology  
**Confirmed upstream HMS subject dependencies:** none  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Non-subject/read-persistence prerequisites:** 3 — two TopologyNode references and one optional PipelineSegment reference

### 47.1 Semantic role and ordering rationale

`TopologyConnection` is the Topology-owned explicit graph edge between two topology nodes. It carries the connection classification, directionality, optional pipeline-segment association, optional nominal-capacity metadata, lifecycle state and audit timestamps.

The active Topology roadmap distinguishes `PipelineSegment` as the linear physical pipe asset between two nodes and `TopologyConnection` as the explicit graph edge between nodes. This distinction is retained.

It remains HMS Level 0 because HMS-005 already classifies `TopologyNode` and `PipelineSegment` as retained Topology read/persistence prerequisites outside the 123-model HMS subject graph. Resolving these references therefore does not add an HMS graph edge.

### 47.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable topology-connection identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable connection code. Current evidence does not establish a uniqueness scope. |
| `fromNodeId` | `String` | Mandatory | Source/end-A TopologyNode reference. |
| `toNodeId` | `String` | Mandatory | Target/end-B TopologyNode reference. |
| `connectionType` | `ConnectionType` in live code | Mandatory, but representation requires revision | Business classification of the graph edge; accepted Topology architecture requires a catalog-backed `ConnectionTypeReference`. |
| `flowDirection` | `FlowDirection` | Mandatory | Technical graph/flow direction: `DIRECTED`, `BIDIRECTIONAL`, or `UNKNOWN`. |
| `pipelineSegmentId` | `String` | Optional | Optional association to retained Topology PipelineSegment master data. |
| `nominalCapacity` | `BigDecimal` | Optional | Optional nominal-capacity metadata. No current authoritative range/sign rule was found. |
| `capacityUnitCode` | `String` | Optional | Unit code associated with nominal capacity when supplied; current evidence does not define a governed unit-reference model or mandatory pairing rule. |
| `status` | `TopologyStatus` | Mandatory | Technical lifecycle state: `DRAFT`, `ACTIVE`, `SUSPENDED`, `RETIRED`, or `ARCHIVED`. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The canonical record already rejects blank `id`, `code`, `fromNodeId`, and `toNodeId`, and requires non-null connection type, flow direction and lifecycle status.

### 47.3 Node dependency resolution and integrity

HMS-005 already records:

```text
topology.TopologyConnection.fromNodeId/toNodeId
    -> persistence/read TopologyNode
```

and the scalar-reference integrity inventory resolves both fields to `topology.TopologyNode`.

HRA-111 installs:

```text
fk_hra111_topology_001
  from_node_id -> hidra_topology_node(id)

fk_hra111_topology_002
  to_node_id -> hidra_topology_node(id)
```

both with `ON DELETE RESTRICT` and validated against existing rows.

Therefore both required node references are now semantically and relationally resolved. No production correction is required merely for node existence.

### 47.4 Self-connection invariant enforcement gap

The repository contains `TopologyConnectionValidator`, which states the domain rule:

```text
Topology connection must not connect a node to itself.
```

However, the canonical `TopologyConnection` constructor does not reject `fromNodeId == toNodeId`, the base schema has no corresponding CHECK constraint, and repository search found no active application write service invoking `TopologyConnectionValidator`.

Consequently, the domain rule exists but is not guaranteed by the model/persistence boundary. A self-loop can be constructed and, through a direct repository/persistence path, stored while satisfying both node foreign keys.

HMSR-034 does not prescribe whether the invariant belongs in the record constructor, an authoritative domain/application policy, an additive database CHECK, or a combination. It requires only that authoritative writes fail closed.

### 47.5 Connection-type catalog regression

The active Topology roadmap states that **Connection type** is a catalog-backed controlled vocabulary and that business type concepts must use catalog references rather than fixed Java enum fields.

The accepted architecture artifacts specifically show:

```text
TopologyConnection -> ConnectionTypeReference
```

and the COR2-007 controlled-vocabulary audit classifies `ConnectionType` as `REPLACE_FIRST_THEN_DELETE`, while `ConnectionTypeReference` is the intended retained reference object.

Live `main`, however, has no `ConnectionTypeReference.java`. `TopologyConnection` and `TopologyConnectionJpaEntity` both use the fixed Java enum:

```text
PIPELINE_SEGMENT
DIRECT_LINK
VIRTUAL_LINK
TRANSFER_LINK
MEASUREMENT_LINK
```

and persistence stores it directly in `connection_type varchar(80) NOT NULL` using `EnumType.STRING`. The consolidated current Topology migration contains no connection-type catalog table/reference column for this model.

This is a direct contradiction between accepted Topology controlled-vocabulary architecture and the current production model/persistence shape. The semantic baseline cannot preserve both as authoritative.

### 47.6 Optional PipelineSegment reference integrity gap

HMS-005 explicitly identifies:

```text
topology.TopologyConnection.pipelineSegmentId
    -> persistence/read PipelineSegment
```

so the optional field is no longer semantically unresolved.

The base schema indexes `pipeline_segment_id`, but HRA-111 does not add an FK for this nullable field, and no active TopologyConnection write service or lookup validator was found that checks a populated segment ID before persistence.

Therefore a non-null `pipelineSegmentId` can currently be stored without demonstrated fail-closed proof that the referenced PipelineSegment exists.

HMSR-034 does not make the segment association mandatory and does not infer that every `PIPELINE_SEGMENT` connection type must carry one, because current authoritative evidence does not state that coupling.

### 47.7 FlowDirection and lifecycle semantics

`FlowDirection` behaves as technical graph semantics rather than a user-maintained business taxonomy, and current evidence does not classify it for catalog replacement. HMSR-034 therefore retains the enum.

`TopologyStatus` was explicitly retained as a lifecycle enum by the controlled-vocabulary audit. No status-transition matrix specific to TopologyConnection is defined by current evidence, so none is invented.

### 47.8 Capacity semantics deliberately not invented

Current repository evidence does not establish:

- that `nominalCapacity` must be positive or nonnegative;
- that `nominalCapacity` and `capacityUnitCode` must always appear together;
- a canonical capacity-unit catalog/owner for this field;
- conversion semantics or base units;
- connection-type-specific capacity rules.

HMSR-034 therefore records no capacity invariant beyond the existing optional representation.

### 47.9 Code and graph uniqueness deliberately not invented

The base schema contains only an ordinary index on `code`, and no current Topology DDD/roadmap evidence reviewed here states a global or scoped connection-code uniqueness invariant.

Likewise, current evidence does not define uniqueness for `(fromNodeId, toNodeId, connectionType)`, nor whether parallel logical edges between the same nodes are allowed. Such rules are not inferred from generic graph modeling.

### 47.10 Required revisions

Three evidence-backed correction obligations remain:

1. **Restore/reconcile catalog-backed connection-type semantics.** Replace the live fixed `ConnectionType` enum/string persistence with the accepted `ConnectionTypeReference`/catalog architecture, or explicitly revise the governing Topology architecture if that earlier decision is no longer intended. Domain, JPA, Flyway, API/application contracts and migration strategy must converge on one representation.

2. **Enforce the no-self-connection domain rule on authoritative writes.** `fromNodeId` and `toNodeId` must not resolve to the same node when a TopologyConnection becomes authoritative. The existing unused validator is insufficient by itself.

3. **Protect populated PipelineSegment references.** When `pipelineSegmentId` is non-null, it must resolve to the retained Topology PipelineSegment target through an additive nullable FK, application validation, guarded provisioning path, or equivalent fail-closed mechanism.

HMSR-034 does not modify production Java, JPA, Flyway, API/application contracts, tests, topology master data, or provisioned data.

### 47.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, TopologyConnection is the governed graph edge used to represent network connectivity between physical/logical topology nodes. It is distinct from the pipe-segment asset itself even when a connection is associated with a PipelineSegment.

Node existence, valid edge classification and prevention of unintended self-connections are important for map rendering, routing, simulation snapshots, telemetry anchoring and downstream network reasoning. A dangling segment link or classification drift would weaken network traceability.

### 47.12 Review conclusion

**REVISE.** `TopologyConnection` has a coherent 12-field Topology-owned graph role, required node references protected by HRA-111, correct technical flow/lifecycle enums, and a clear distinction from PipelineSegment.

The target baseline cannot mark it APPROVED while the live fixed `ConnectionType` representation contradicts the accepted catalog-reference architecture, the explicit no-self-loop rule is not enforced on authoritative writes, and populated optional `pipelineSegmentId` values lack demonstrated referential protection.

HMS reconciliation must retain these obligations until an explicitly authorized Topology correction task resolves them or the governing Topology architecture is explicitly revised with stronger evidence.

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

- `764f4a8c7395db4d3a34b2491d157e2e6f7c383b` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:86](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L86)
- [docs/roadmap/model-semantic-review.md:229](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L229)
- [docs/roadmap/model-semantic-review.md:7848](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L7848)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:229 | HMSR-034 | 0 | topology | TopologyConnection | — | — | 0 | 0 | 3 | REVISE | Completed | docs(model-review): review topology TopologyConnection
doc/domain/SEMANTIC_DECISIONS.md:86 | HMSR-034 | [topology.TopologyConnection](#topology-topologyconnection) | REVISE | HMR-031
~~~~~~~~

<a id="hapi-351"></a>
## HAPI-351 — review topology Facility

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HMSR-041

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review topology Facility

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/FacilityRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/FacilityRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/FacilityRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 54. HMSR-041 — topology.Facility review

**Decision:** REVISE  
**Review code:** HMSR-041  
**Dependency level:** 1  
**Bounded context:** topology  
**Confirmed upstream subject dependencies:** `party.Party` through optional `ownerPartyId`  
**Confirmed direct HMS subject dependents:** 1 — `topology.Equipment` through its facility relationship  
**Transitive HMS subject dependents:** 1  
**Unresolved/non-subject references:** 0 — `facilityTypeId` is now resolved to retained Topology FacilityType persistence/read state outside the HMS subject set

### 54.1 Semantic role and ordering rationale

`Facility` is the Topology-owned physical-facility model for stations, terminals, depots, pump/compressor stations and other physical network facilities.

It owns physical-network identity, facility classification, location coordinates, operational lifecycle and optional external Party ownership reference. It must remain distinct from OrganizationUnit: an organizational station/unit represents people/responsibility structure, while Facility represents the physical site/network asset.

It is Level 1 because the optional `ownerPartyId` is a confirmed cross-module HMS subject reference to `party.Party`. Facility type is Topology-owned retained persistence/read state and therefore does not add an HMS subject edge.

### 54.2 Field semantics

| Field | Type | Reviewed meaning |
|---|---|---|
| `id` | `String` | Mandatory stable Topology facility identity. |
| `code` | `String` | Mandatory language-neutral facility business/reference code. No current authoritative uniqueness scope is established. |
| `nameAr` | `String` | Optional Arabic display name. |
| `nameFr` | `String` | Optional French display name. |
| `nameEn` | `String` | Optional English display name. |
| `facilityTypeId` | `String` | Mandatory same-module reference to Topology FacilityType. |
| `facilityKind` | `FacilityKind` | Mandatory Topology facility-kind classification used by the current model. |
| `ownerPartyId` | `String` | Optional cross-module stable reference to Party-owned owner identity. |
| `ownerPartyCodeSnapshot` | `String` | Optional historical/readability snapshot of Party code. |
| `ownerPartyNameSnapshot` | `String` | Optional historical/readability snapshot of Party display name. |
| `latitude` | `BigDecimal` | Optional latitude value. |
| `longitude` | `BigDecimal` | Optional longitude value. |
| `elevationMeters` | `BigDecimal` | Optional elevation in meters. |
| `status` | `FacilityStatus` | Mandatory facility lifecycle state. |
| `commissionedAt` | `Instant` | Optional commissioning timestamp. |
| `retiredAt` | `Instant` | Optional retirement timestamp. |
| `createdAt` | `Instant` | Persistence-required creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required update timestamp. |

The canonical constructor rejects blank ID, code and facility type; requires non-null facility kind and status; and normalizes text/reference/snapshot values.

### 54.3 FacilityType reference semantics

The Topology DDD owns both `Facility` and `FacilityType`.

The base facility schema requires:

```text
facility_type_id varchar(80) NOT NULL
```

and HRA-111 installs:

```text
fk_hra111_topology_014
  facility_type_id -> hidra_topology_facility_type(id)
  ON DELETE RESTRICT
```

with constraint validation.

Therefore `facilityTypeId` is no longer a generic unresolved catalog/value dependency. It is a mandatory same-module reference to the retained Topology FacilityType persistence/read model outside the 123 HMS subject set.

The database already fails closed on missing FacilityType rows. Current evidence does not separately define an active/selectable FacilityType eligibility rule for facility registration, so HMSR-041 does not invent one.

### 54.4 Party ownership boundary and integrity gap

The Party DDD explicitly establishes Party as the owner of external legal-entity/counterparty master data and states that other modules store only Party references and snapshots.

Facility follows that pattern:

```text
ownerPartyId
ownerPartyCodeSnapshot
ownerPartyNameSnapshot
```

The HMS graph correctly resolves `ownerPartyId -> party.Party`.

However, `FacilityApplicationService.registerFacility()` accepts `ownerPartyId` and both snapshots directly from the command and persists them without resolving the Party target through a Party-owned lookup/contract.

The field is nullable, so HMSR-041 does not make Party ownership mandatory. But when `ownerPartyId` is populated, current authoritative writes can persist a nonexistent Party identity.

A cross-module database FK would violate the established bounded-context reference policy. The correction therefore belongs at an application/owner-contract boundary, not in Topology schema coupling.

Current evidence also does not define a required Party role such as OWNER for every facility owner reference. HMSR-041 does not invent role eligibility beyond existence unless stronger Party/Topology evidence later establishes it.

### 54.5 Snapshot semantics

`ownerPartyCodeSnapshot` and `ownerPartyNameSnapshot` are historical/readability values and do not replace Party identity.

Because the current registration API accepts snapshots directly from the caller, they are not independently authoritative proof of Party existence.

HMSR-041 does not require snapshots to be mandatory whenever `ownerPartyId` is present because no current Topology DDD rule establishes that pairing. A future correction may choose to resolve snapshots from Party rather than trust caller-supplied values, but that is not elevated into a separate HMSR obligation without stronger explicit evidence.

### 54.6 FacilityKind and lifecycle semantics

`FacilityKind` is mandatory in the canonical model. The current registration service defaults a missing command value to `FacilityKind.OTHER`.

No current Topology DDD/roadmap evidence reviewed here establishes that FacilityKind must be replaced by FacilityType or by a catalog-backed reference, nor that the two fields are redundant. HMSR-041 therefore does not manufacture a controlled-vocabulary refactor.

`FacilityStatus` is mandatory and new registrations are created as `PLANNED`, which is conservative for a newly registered physical facility.

Current evidence does not define a full Facility status-transition matrix, commissioned/retired timestamp requirements by status, or automatic transitions. None are invented.

### 54.7 Location and chronology rules deliberately not invented

The model permits optional latitude, longitude and elevation.

Current Topology DDD/roadmap evidence does not define:

- required coordinates for every facility;
- latitude/longitude ranges at the Facility model boundary;
- mandatory coordinate pairing;
- CRS/datum semantics on this record;
- elevation sign/range limits.

The broader data-provisioning roadmap requires coordinate/CRS validation during provisioning, but that does not by itself prove a Facility constructor invariant. HMSR-041 therefore records no coordinate correction.

Likewise, current evidence does not explicitly define `commissionedAt <= retiredAt` or status/timestamp coupling for Facility, so no chronology rule is invented.

### 54.8 Code and multilingual semantics

The facility code is mandatory in domain/JPA/schema.

The base schema creates an ordinary code index, not a unique constraint. Current authoritative Topology DDD/roadmap evidence reviewed here does not explicitly establish global or scoped Facility code uniqueness. HMSR-041 therefore does not infer uniqueness from the field name.

`nameAr/nameFr/nameEn` are optional in both domain and persistence. Current Topology evidence does not require all three names or French specifically, and no separate translation model is established for Facility. No multilingual correction is recorded.

### 54.9 Application/JPA/Flyway alignment

The live domain and JPA representations agree on all 18 components.

The registration application service:

- generates a Topology-owned ID;
- accepts the mandatory code and facility type;
- defaults a missing facility kind to `OTHER`;
- initializes status to `PLANNED`;
- leaves commissioning/retirement timestamps unset; and
- persists through `FacilityRepositoryPort`.

The final persistence contract requires ID, code, facility type, facility kind, status and audit timestamps while keeping names, Party ownership, coordinates and lifecycle timestamps nullable.

The only evidence-backed alignment defect found for authoritative registration is the unvalidated populated Party owner reference.

### 54.10 Required revision

One evidence-backed correction obligation remains:

1. **Validate populated Facility owner Party references through the Party owner boundary.** When `ownerPartyId` is supplied, Topology must fail closed unless that Party identity exists under the Party bounded context. Do not introduce a cross-module database FK. A separately authorized Topology/Party correction should use a deliberate Party lookup/contract or equivalent owner-controlled validation boundary and preserve snapshots as non-authoritative evidence.

HMSR-041 does not modify production Java, JPA, Flyway, API/application contracts, tests, facility master data, Party data, or provisioned data.

### 54.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, Facility represents the physical pipeline-network site: for example a station, terminal, depot, pump station or compressor station.

A Facility must remain distinct from the OrganizationUnit that may staff or administratively represent that site. Physical ownership may reference a Party where appropriate, but the Party identity remains governed by Party master data.

A dangling owner Party would weaken ownership traceability for physical infrastructure, reporting, contractual context and downstream asset/reference use even though the physical Facility record itself remains valid.

### 54.12 Review conclusion

**REVISE.** `Facility` has a coherent 18-field Topology-owned physical-site model, correct separation from Organization, mandatory FacilityType reference protected by HRA-111, aligned domain/JPA/schema requiredness, optional multilingual/location state, and conservative PLANNED creation semantics.

The target baseline cannot mark it APPROVED while a populated `ownerPartyId` can be persisted without demonstrated validation against the Party-owned master identity.

HMS reconciliation must retain this Party-reference integrity obligation until an explicitly authorized Topology/Party correction resolves it or stronger repository evidence explicitly changes Facility ownership semantics.

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

- `66e478b714d64800563e7260b974921d65ef380c` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:93](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L93)
- [docs/roadmap/model-semantic-review.md:236](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L236)
- [docs/roadmap/model-semantic-review.md:9089](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L9089)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:236 | HMSR-041 | 1 | topology | Facility | — | party.Party | 1 | 1 | 0 | REVISE | Completed | docs(model-review): review topology Facility
doc/domain/SEMANTIC_DECISIONS.md:93 | HMSR-041 | [topology.Facility](#topology-facility) | REVISE | HMR-036
~~~~~~~~

<a id="hapi-360"></a>
## HAPI-360 — review topology Pipeline

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HMSR-050

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review topology Pipeline

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 63. HMSR-050 — topology.Pipeline review

**Decision:** REVISE  
**Review code:** HMSR-050  
**Dependency level:** 1  
**Bounded context:** topology  
**Confirmed upstream subject dependencies:** `topology.PipelineSystem` through mandatory `pipelineSystemId`  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject references:** 0

### 63.1 Semantic role and ordering rationale

`Pipeline` is the Topology-owned physical/logical pipeline record within one PipelineSystem.

Topology owns network structure, so Pipeline carries stable topology identity, system membership, engineering descriptors, multilingual labels and lifecycle state. It must remain distinct from organizational ownership, telemetry measurements, integrity findings and maintainability state.

It is Level 1 because mandatory `pipelineSystemId` references the already-reviewed HMS subject `PipelineSystem`.

### 63.2 Field semantics

| Field | Type | Reviewed meaning |
|---|---|---|
| `id` | `String` | Mandatory stable Pipeline identity. |
| `pipelineSystemId` | `String` | Mandatory same-module reference to PipelineSystem. |
| `code` | `String` | Mandatory language-neutral pipeline code. |
| `nameAr` | `String` | Optional Arabic display name. |
| `nameFr` | `String` | Optional French display name. |
| `nameEn` | `String` | Optional English display name. |
| `pipelineType` | `PipelineType` | Mandatory business classification currently implemented as a fixed Java enum. |
| `nominalDiameter` | `BigDecimal` | Optional nominal diameter. |
| `diameterUnitCode` | `String` | Optional unit code accompanying nominal diameter. |
| `designPressure` | `BigDecimal` | Optional design pressure. |
| `pressureUnitCode` | `String` | Optional unit code accompanying design pressure. |
| `totalLengthKm` | `BigDecimal` | Optional total length in kilometres. |
| `status` | `TopologyStatus` | Mandatory lifecycle/status state. |
| `createdAt` | `Instant` | Persistence-required creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required update timestamp. |

The canonical constructor requires nonblank ID, PipelineSystem ID and code, and non-null pipeline type/status.

### 63.3 PipelineSystem reference integrity

The final schema requires `pipeline_system_id NOT NULL`.

HRA-111 installs and validates:

```text
hidra_topology_pipeline.pipeline_system_id
    -> hidra_topology_pipeline_system.id
```

Therefore the mandatory HMS dependency is fail-closed at persistence.

The domain also rejects blank `pipelineSystemId`, so domain/persistence requiredness is aligned for the system relationship.

### 63.4 Topology catalog architecture conflict

The live Topology roadmap records the completed catalog-refactor baseline:

```text
COR-009  refactor(topology): replace enum usage in domain assets with type references
COR-010  db(topology): migrate topology assets to catalog foreign keys
COR-011  refactor(topology): update topology persistence mapping to catalog foreign keys
COR-013  db(topology): remove topology enum-style type constraints
COR-019  refactor(catalog): remove remaining business taxonomy enum declarations
```

It then states:

```text
Topology business taxonomy concepts are no longer roadmap-accepted as Java enums.
Business type concepts are modeled as catalog references with stable codes and localized labels,
not as fixed Java enum fields.
```

The live Pipeline model nevertheless declares:

```java
PipelineType pipelineType
```

and the current enum fixes the vocabulary to:

```text
CRUDE_OIL
CONDENSATE
NATURAL_GAS
LPG
MULTI_PRODUCT
WATER
OTHER
```

JPA persists the enum string directly in `pipeline_type`.

This conflicts with the repository's accepted Topology controlled-vocabulary architecture. The issue is not merely that an enum could be extensible later; the live roadmap explicitly records removal of business-taxonomy enums as completed architectural intent.

### 63.5 Lifecycle enum remains valid

The same Topology roadmap explicitly distinguishes lifecycle/status concepts from business taxonomies and allows technical enums where appropriate.

`TopologyStatus` therefore does not inherit the PipelineType correction obligation merely because it is also a Java enum.

HMSR-050 preserves that distinction.

### 63.6 Engineering measurements

`nominalDiameter`, `designPressure` and `totalLengthKm` are optional in both domain and persistence.

Current Topology DDD/roadmap evidence reviewed here does not state:

- positivity constraints;
- mandatory diameter/pressure for every pipeline;
- mandatory pairing between a numeric value and its unit code;
- one canonical diameter or pressure unit;
- a tolerance/precision rule beyond persistence precision.

HMSR-050 therefore does not invent those engineering invariants.

The future data-provisioning transformation task must validate units/source values against the final target contract, but that does not by itself prove additional canonical constructor rules.

### 63.7 Code and multilingual semantics

`code` is mandatory in domain and persistence.

The schema has an ordinary code index, not a unique constraint, and the current Topology evidence reviewed for HMSR-050 does not establish a global/scoped Pipeline code uniqueness invariant. None is invented.

Arabic/French/English names are optional in both domain and persistence. Current evidence does not require all three translations simultaneously, so no multilingual correction is recorded.

### 63.8 Application and repository reality

The live target inventory shows no dedicated current Pipeline create inbound use case; PipelineRepositoryPort remains actively used for owner-controlled query resolution, visualization and persistence infrastructure.

That absence does not remove Pipeline from the canonical target model, nor does it cure the domain/JPA taxonomy conflict. Any future authoritative write path must follow the catalog-backed Topology contract rather than cementing the legacy enum.

HMSR-050 does not create a new API/use-case obligation merely because a dedicated create path is not currently exposed.

### 63.9 Domain/JPA/Flyway alignment

Domain and JPA agree on all 15 components and on intrinsic requiredness for ID, PipelineSystem ID, code, pipeline type and status.

The PipelineSystem dependency is protected by a validated FK.

The structural model is otherwise coherent, but `pipelineType` remains semantically misaligned with the accepted Topology catalog architecture because the domain and persistence still encode the business taxonomy as an enum/varchar value instead of the roadmap-approved catalog-reference form.

### 63.10 Required revision

One evidence-backed production correction obligation remains:

1. **Replace Pipeline's fixed `PipelineType` business taxonomy with the repository-approved catalog-reference architecture.** The correction must reconcile domain, persistence, Flyway/data migration, API/read contracts and existing values with the accepted Topology type-catalog design, using stable catalog codes/localized labels rather than a fixed business enum. Preserve `TopologyStatus` as a lifecycle enum unless separate evidence changes that classification.

HMSR-050 does not modify production Java, JPA, Flyway, API/application contracts, tests, pipeline data, type catalogs, or provisioned data.

### 63.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, pipeline classifications such as crude-oil, condensate, natural-gas, LPG or multi-product are business reference data used across visualization, planning, monitoring, integrity, reporting and future provisioning.

Treating this vocabulary as governed catalog/reference data allows controlled extension, localization and lifecycle governance without recompiling the platform or hard-coding business vocabulary into downstream modules.

The physical Pipeline remains owned by Topology; changing the taxonomy representation does not transfer ownership to another bounded context.

### 63.12 Review conclusion

**REVISE.** `Pipeline` has a coherent 15-field Topology model, correct mandatory dependency on PipelineSystem with validated FK protection, aligned system/code/status requiredness, and conservative optional engineering/multilingual state.

The target baseline cannot mark it APPROVED while the live model still encodes `PipelineType` as a fixed Java enum contrary to the completed and explicitly documented Topology catalog-refactor architecture.

HMS reconciliation must retain this catalog-taxonomy correction until an explicitly authorized Topology production correction resolves it or stronger repository evidence explicitly changes the accepted Topology controlled-vocabulary policy.

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

- `31e3549e6492c5aece0eda278b169a145b832fb8` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:102](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L102)
- [docs/roadmap/model-semantic-review.md:245](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L245)
- [docs/roadmap/model-semantic-review.md:10573](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L10573)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:245 | HMSR-050 | 1 | topology | Pipeline | — | topology.PipelineSystem | 0 | 0 | 0 | REVISE | Completed | docs(model-review): review topology Pipeline
doc/domain/SEMANTIC_DECISIONS.md:102 | HMSR-050 | [topology.Pipeline](#topology-pipeline) | REVISE | HMR-042
~~~~~~~~

<a id="hapi-509"></a>
## HAPI-509 — expose operational twin visualization

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HPR-P25-003

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-504

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Provide authorized network map/state APIs with product and asset layers, connectivity, measured/estimated overlays, quality/freshness and issue location; verify the frontend integration contract.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

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

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver company/network drafts, physical graph editing and protected publication/history APIs after owner-fence acceptance. The exact bounded remaining delivery is: Provide authorized network map/state APIs with product and asset layers, connectivity, measured/estimated overlays, quality/freshness and issue location; verify the frontend integration contract..

### 4. Functional Requirements

Original independent acceptance; design is not implementation; F1/F3 actual cases

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.

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

- Original independent acceptance; design is not implementation; F1/F3 actual cases
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-504. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13649](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13649)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6087](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6087)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:854](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L854)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15389](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15389)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:854 | HPR-P25-003 | PENDING | Topology / Telemetry / API | Code/Test | Provide authorized network map/state APIs with product and asset layers, connectivity, measured/estimated overlays, quality/freshness and issue location; verify the frontend integration contract. | feat(topology): expose operational twin visualization | HPR-P25-002
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15389 | HPR-P25-003 | PENDING | FOUNDATION_ACTIVE | Topology / Telemetry / API | 003/004: specific independent owner contracts, not all 002; 018: amendment docs CI + owner selection; 019/020: 018 | Original independent visualization/measurement or engineering provisioning objective | Any solver use remains outside this independent scope | Original independent acceptance; design is not implementation; F1/F3 actual cases | NOT SELECTED | Own bounded preflight/implementation after prerequisites
doc/roadmap/ULTIMATE_ROADMAP.md:6087 | HPR-P25-003 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13649 | HPR-P25-003 | PENDING | FOUNDATION_ACTIVE | Topology / Telemetry / API | 003/004: specific independent owner contracts, not all 002; 018: amendment docs CI + owner selection; 019/020: 018 | Original independent visualization/measurement or engineering provisioning objective | Any solver use remains outside this independent scope | Original independent acceptance; design is not implementation; F1/F3 actual cases | NOT SELECTED | Own bounded preflight/implementation after prerequisites
~~~~~~~~

<a id="hapi-586"></a>
## HAPI-586 — Integrated closure: six topology/operator delivery packages

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HPR-P25-052

Priority: P1

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-592

Cross-Repository Dependencies: HWEB-F02, HWEB-F03 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-052; child tasks own implementation. six topology/operator delivery packages

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/StationInternalGraph.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/StationInternalGraphRevision.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevision.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver company/network drafts, physical graph editing and protected publication/history APIs after owner-fence acceptance. The exact bounded remaining delivery is: Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-052; child tasks own implementation. six topology/operator delivery packages.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/StationInternalGraph.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/StationInternalGraphRevision.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevision.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`.
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
- `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/StationInternalGraph.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/StationInternalGraphRevision.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevision.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-052 — six topology/operator delivery packages

Prerequisites: accepted051 and the retained018/019/020/032/034/036/037/038 scopes;
D3 closes exact draft/published schemas, supported geometry, permissions, CRUD/API/
errors/export packages and source paths before each relevant implementation. Existing
SpringTopologyController creates systems/facilities, and immutable revision stores exist;
neither is the missing complete editor. Reuse051's internal publication/CAS/epoch boundary
instead of implementing a competing security/head mechanism.

| Planning stage | Remaining delivery | Evidence / exit criterion |
|---|---|---|
| HPR-P25-052A | Company/network-owned drafts and bounded create/read/update/validate lifecycle. | Actual protected API round trips, optimistic draft conflict, incomplete draft diagnostics, owner isolation and action-specific permissions. No incomplete draft presented as published. |
| HPR-P25-052B | Physical network/branch/station/port graph authoring and incidence validation. | Persist/reload supported serial/parallel graphs; reject dangling/wrong-owner/wrong-kind references; retain exact source identities and graph precision. Installed-unit completion remains054. |
| HPR-P25-052C | Survey/CRS/vertical datum/chainage/elevation/profile and pipe-section authoring. | Preserve intermediate elevations/section boundaries, declared units/precision/source/evidence across revisions; reject unsupported/wrong/missing metadata, no browser/default elevation or PostGIS/physics assumption. |
| HPR-P25-052D | Public protected publish/exact read/current/history/effective/list lifecycle over051. | Expected predecessor and owner/head epoch, one CAS winner, identical/conflicting replay, withdrawal/reactivation/transfer-hold semantics, exact old formats and history cursors; denied paths reveal no foreign metadata. |
| HPR-P25-052E | Supported local typed import/export and per-editor capability/completeness closure. | Versioned bounded packages, dry-run without writes, lossless precision/provenance, atomic admitted import and explicit unsupported states. Aggregate multi-owner exchange remains055. |
| HPR-P25-052F | Cross-slice operator/API/security/history qualification and consumer-ready contract. | Real configured API/filter path across two companies/networks and station graphs, rollback/concurrency/corruption tests; exact runtime OpenAPI/permissions/DTOs and relevant consumer handoff, no new frontend implementation in this repo. |

Publish truthful capabilities with each accepted increment, not only at E. E closes
remaining local exchange/capability obligations. Source authoring pins only existing
eligible owner revisions; it never requires a future055 baseline. Accepted052 slices
may unblock relevant HidraWEB F02/F03 work independently; no claim of live paired E2E.

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-592. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23345](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23345)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1283](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1283)
- [doc/roadmap/ULTIMATE_ROADMAP.md:5097](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L5097)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-587"></a>
## HAPI-587 — Company/network-owned engineering drafts

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HPR-P25-052A

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-567

Cross-Repository Dependencies: HWEB-F02, HWEB-F03 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Company/network-owned drafts and bounded create/read/update/validate lifecycle.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

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

Deliver company/network drafts, physical graph editing and protected publication/history APIs after owner-fence acceptance. The exact bounded remaining delivery is: Company/network-owned drafts and bounded create/read/update/validate lifecycle..

### 4. Functional Requirements

Actual protected API round trips, optimistic draft conflict, incomplete draft diagnostics, owner isolation and action-specific permissions. No incomplete draft presented as published.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringNetworkDraft.java` — pure Java owned aggregate/value and invariants for company/network-owned editable drafts; completeness and expected draft version.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/EngineeringNetworkDraftUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringNetworkDraftRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringNetworkDraftApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringNetworkDraftController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringNetworkDraftRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

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

**PROPOSED, not existing:** `POST /api/v1/topology/engineering-networks` for company/network-owned editable drafts; completeness and expected draft version. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

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

- Actual protected API round trips, optimistic draft conflict, incomplete draft diagnostics, owner isolation and action-specific permissions. No incomplete draft presented as published.
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

- [doc/model-remediation/RECONCILIATION.md:23356](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23356)
- [doc/model-remediation/RECONCILIATION.md:23701](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23701)
- [doc/model-remediation/RECONCILIATION.md:23734](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23734)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1009](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1009)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1042](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1042)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1294](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1294)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1009 | HPR-P25-052A | Confirm whether one network can have multiple company engineering contexts and how network ownership transfer relates to facility publication epochs. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/roadmap/ULTIMATE_ROADMAP.md:1042 | HPR-P25-052A | REQUIRES_OWNER_DECISION | Company/network-owned engineering drafts
doc/roadmap/ULTIMATE_ROADMAP.md:1294 | HPR-P25-052A | Company/network-owned drafts and bounded create/read/update/validate lifecycle. | Actual protected API round trips, optimistic draft conflict, incomplete draft diagnostics, owner isolation and action-specific permissions. No incomplete draft presented as published.
doc/model-remediation/RECONCILIATION.md:23356 | HPR-P25-052A | Company/network-owned drafts and bounded create/read/update/validate lifecycle. | Actual protected API round trips, optimistic draft conflict, incomplete draft diagnostics, owner isolation and action-specific permissions. No incomplete draft presented as published.
doc/model-remediation/RECONCILIATION.md:23701 | HPR-P25-052A | Confirm whether one network can have multiple company engineering contexts and how network ownership transfer relates to facility publication epochs. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/model-remediation/RECONCILIATION.md:23734 | HPR-P25-052A | REQUIRES_OWNER_DECISION | Company/network-owned engineering drafts
~~~~~~~~

### HAPI-587 source-backed ownership policy review — 2026-10-19 (owner choice pending)

**Selected bounded stage and current evidence.** Owner Next selects HAPI-587's ownership-policy/draft-contract review following parent051 closure. Main `693d01950b1776714116db306ddbc36fe578cc4e`, parent `6d87455fad45eba046e43ca3f5647c67363bdfac`, tree `d296ef507d0d763f8942d18f2532059e82fb4b7c`, passed [Docs429](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39227475717). HAPI-567 is VERIFIED/COMPLETED for its accepted internal software boundary; that supplies this task's prerequisite, not a draft API or a network ownership policy. Current review message: `docs(topology): review engineering draft ownership policy`. One MODIFY only, this appended phase record; zero CREATE/DELETE, all frozen statuses and prior payload preserved. HAPI-587 stays NOT_IMPLEMENTED/BLOCKED.

**Actual source findings that control implementation.**

| Retrieved source | Established behavior and consequence |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java` and `infrastructure/persistence/entity/PipelineSystemJpaEntity.java` under the same module | Logical system identity/type/status/names exist, but neither type has a company owner or an engineering-context identity/version. No existing field resolves one-versus-many company engineering contexts. A supplied company ID cannot silently become authoritative ownership of an existing shared system. |
| `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java` | Creation persists a logical `PipelineSystem` with `TopologyStatus.DRAFT`; this is not the proposed versioned engineering draft aggregate, context ownership, optimistic draft lifecycle or validation evidence. Preserve this existing operation rather than claiming its status closes052A. |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java` | Existing pipeline-system and Facility creation routes exist; proposed `/api/v1/topology/engineering-networks` draft routes do not. Current capabilities/map metadata does not establish engineering API round trips or owner isolation. |
| `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevision.java` and `application/contract/simulation/SimulationPhysicalNetworkRevisionContract.java` under the same module | Immutable physical revisions require explicit provenance/effectivity and a structurally connected graph with at least two nodes/one real pipe and supplied SI data. Their byte integrity is not publication approval or live scope eligibility. They cannot be weakened into nullable incomplete drafts or used as authorization. Existing simulation exports remain exact immutable sources. |
| `src/main/java/dz/sh/hidra/modules/identity/application/contract/topology/TopologyProtectedAuthorizationContract.java` and `TopologyScopedAccessContract.java` | Current protected operations/permission-code mapping are fixed to station revisions and an exact company-plus-Facility request. They do not accept a pipeline-system/network context or create/read/update/validate draft actions. An arbitrary network ID must not be put in the Facility field, and a route grant or earlier ordinary permission Boolean is insufficient. |
| `src/main/java/dz/sh/hidra/modules/topology/application/service/ProtectedStationAdmissionCoordinator.java` and `src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java` | Accepted provisional statement hashes/compiled caller and private publisher credential entry are specific to the genuine station operation service. A new draft service is not automatically admitted. Future registration must define actual Identity/Party/network-context owner contribution, retained writer fences, common time and exact draft mutation/final guard integration without widening arbitrary-caller/SQL privileges. |

The live phase/reconciliation still contains the explicit original owner question: **“Confirm whether one network can have multiple company engineering contexts and how network ownership transfer relates to facility publication epochs.”** See the retained HPR-P25-052A row above and [original reconciliation](../model-remediation/RECONCILIATION.md). Source search found no later superseding decision. A targeted prior-owner-decision retrieval likewise recovered no explicit selection; historical assistant descriptions and generic Next are not owner consent. Earlier option A selected trusted publisher credentials for L and is unrelated to this task's network ownership cardinality.

**Concrete policy options for owner selection, not yet approved.** A company engineering context means an explicitly identified workspace over a logical pipeline system/network. It has one accountable company Party, independent drafts and authority; it does not transfer physical ownership of the network or any Facility. Many draft versions/candidates inside a permitted context remain possible under either option.

| Choice | Company-context cardinality | Persistence/security consequence |
|---|---|---|
| A — recommended proposal | The same logical network may have separate engineering contexts for different companies, with one context per company/network pair. Each context has exactly one company owner. | Context identity and the exact company/network pair govern uniqueness and every operation. Company A cannot discover/use B's context, drafts, validations or history. Referencing the same logical system grants no access to another company's Facilities or publications. This keeps the current ownerless logical system distinct from company engineering responsibility. |
| B — alternative | At most one company engineering context is allowed for a logical network. | A database uniqueness rule on the logical network enforces the exclusive company-context binding. A second company's context is rejected rather than silently sharing/reassigning ownership. This is a narrower product policy and cannot be inferred from today's source. |

**Proposed transfer rule common to A/B:** context company/network bindings are immutable for this bounded052A delivery; ownership-transfer/rebind requests are unsupported and fail closed. Existing Facility ownership/generation and publication-head epochs remain controlled by their respective accepted owner paths. Draft creation/edit/validation must not rewrite them, reset generations, relabel immutable history or adopt a new owner automatically. If an authoritative owner/context mismatch is observed, deny or hold the affected operation until an explicitly governed mechanism exists; changing a request company cannot recover stale authority. This rule proposes deferring a new transfer workflow, not declaring all future network transfers impossible. If transfers must be supported now, the owner must say so: policy must then specify old/new company authority, historical visibility, epoch/generation transitions and affected Facilities before any transfer code is registered. Choosing A or B includes the stated immutable-binding/no-transfer rule for this delivery; neither is selected by this review.

**Prepared bounded draft contract, common constraints pending that choice.** The six source filenames already proposed in section6 remain proposals rather than an executable allowlist. They identify the right broad owner/domain/application/REST/JDBC seams; missing request/response, security-participant/fence, test, migration and bootstrap scope must be closed together after the policy selection. No database migration version or guessed runtime filename is reserved by this review.

1. **Ownership and identity:** a company-owned engineering context/draft references an actual logical pipeline system; validate current eligible Party, actual system reference and the selected context cardinality. Do not duplicate the logical system or infer company ownership from actor claims, UI tenant context or a request field. No anonymous/default company, cross-company uniqueness disclosure or authorization fallback is admitted.
2. **Lifecycle:** expose only bounded create/read/update/validate for explicit drafts. Create returns a server identity and initial expected-version coordinate; updates require the expected draft version and atomically advance it. A stale update conflicts without overwriting another writer; failed validation does not convert a draft into a published revision. Exact DTO/method/error/replay rules belong to the chosen executable contract. The existing section7 route remains PROPOSED, not advertised as working.
3. **Incomplete data:** drafts may be incomplete, with stable typed diagnostics for missing references/content and explicit unqualified state. Required engineering values must not become invented zero/default measurements. Incomplete metadata/context does not satisfy the existing immutable physical-revision constructor or numerical qualification. Actual graph editing/incidence belongs to HAPI-588 and publication/history to HAPI-590;052A must provide the correct owned foundation and extension seam without claiming those successors completed.
4. **Authorization:** create/read/update/validate need distinct owner-controlled action permissions for the exact company/network/context. Define the genuine native-identity/paired permission contribution and actual network-context owner witness at the protected linearization point. Preserve current DENY/unknown/source/mapping/revocation behavior, transaction/time limits, ordinary/publisher credential boundary and all accepted station tests. No fake Facility selector, broad role, prior scalar permit or arbitrary provisional SQL/credential route may bridge the gap.
5. **Persistence/rollback:** use a reviewed additive schema for context identity/binding, selected uniqueness, draft version/state and owned payload/validation provenance. Authoritative bindings and expected-version checks must be database enforced where relevant; all effects roll back together on permission/owner drift, expiry, storage error or CAS conflict. Define the complete writer/fence graph and final guard for these new relations before code. Retain existing immutable revisions, station heads/events, generation triggers and Flyway history.
6. **API acceptance:** require actual authenticated create→read→update→validate round trips, stale-version conflict, two-company substituted-ID denial before protected disclosure, permission removal/DENY, invalid owner/system references, incomplete diagnostics, concurrent updates and rollback/expiry. Register real new cases/hashes/partitions only after tests exist. Require exact implementation-SHA Full/Fast/Docs, zero skipped required PostgreSQL cases, deterministic OpenAPI/compatibility and genuine migrated dictionary evidence under AGENTS CI-first. No production/IdP commissioning or deployment measurements are needed for this software policy decision.

**Decision blocker and next concrete action.** HAPI-567's dependency is satisfied, but the original HAPI-587 business/security policy is unresolved. No runtime code, API, permission, schema, owner-binding model or status advancement is selected here. The owner should choose A or B with the common transfer rule, or explicitly require transfers now. After that answer, finish one coherent exact file/contract/test/migration registration under this same HAPI-587; do not create a new canonical task, repeat generic051 preflights or silently take a policy default. No runtime implementation may precede the decision-dependent registration. This pause comes from the existing roadmap's explicit owner-policy requirement, not missing deployment data or a skill-imposed approval step.

**Validation and publication.** Local checks pass:17 documentation-validator fixtures; canonical documentation (128 documents/921 tasks/25 phases); offline OpenAPI (263 operations/244 paths/231 schemas); exact one-file append-only scope/history, unchanged statuses, six proposed-path absence checks and whitespace validation. Source observations above are static/source facts, not newly executed Java/database proof. Preserve HAPI-587 NOT_IMPLEMENTED/BLOCKED and all other task statuses. Publish only this phase append directly to main with the expected-head guard, exact message above and verified blob/tree/parent; confirm same-SHA Docs creation/startup, then stop. The next response needed is the concrete ownership-policy selection; do not run another CI or add another review merely to postpone that choice.

### HAPI-587 option A owner selection and exact executable registration — 2026-10-19

**Owner decision and selected stage.** The owner explicitly selects **A** after the preceding A/B explanation: one logical network may have separate isolated company engineering contexts, with exactly one context per company/network pair and one company owner per context. Multiple draft candidates and versions inside that context are allowed. The selected common rule makes company/network bindings immutable for this052A delivery, rejects ownership transfer/rebind, preserves Facility ownership/generations/publication epochs and never relabels old history. This is the HAPI-587 business decision, distinct from L's earlier database-credential option A. No further cardinality or transfer confirmation is needed.

Main `cdfd0e5bdeaa4d68c37b54d9f55a215e3e08e0c9`, parent `693d01950b1776714116db306ddbc36fe578cc4e`, tree `d7418fc5471425202135d7fe1e215cd0d968ce8b`, has [Docs430 SUCCESS](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39228287955). The current stage records the decision and registers one coherent implementation, exact message `docs(topology): register company-scoped engineering drafts`. Current writes are only this phase append/HAPI-587 planning status and its planning cells in ULTIMATE_ROADMAP.md and ULTIMATE_PHASES.md: three MODIFY, zero CREATE/DELETE. Implementation remains NOT_IMPLEMENTED; planning becomes REGISTERED_PENDING. All earlier source review, history and frozen fields remain intact.

**Single next implementation envelope.** After this registration passes Docs, one owner Next selects exact message `feat(topology): implement company-scoped engineering drafts`: **21 CREATE /13 MODIFY /zero DELETE** below. These are the complete admitted paths; prior six proposed Java paths are absorbed, with their missing security/API/test/schema seams now specified. No new canonical task, extra generic preflight, unregistered file or optional051 improvement is selected. A newly discovered material contract/scope defect must be stated rather than silently widening the envelope.

**CREATE — all verified absent at this baseline:**

1. `src/main/java/dz/sh/hidra/modules/identity/application/contract/topology/TopologyProtectedEngineeringDraftAuthorizationContract.java` — pure-Java fixed create/read/update/validate company+network request, opaque Prepared/Admission and scalar contribution; distinct from Facility authorization.
2. `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedEngineeringDraftAuthorizationParticipant.java` — genuine Identity-native participant and paired evaluation for the four fixed draft actions.
3. `src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringNetworkDraft.java` — pure immutable context-bound draft/version/metadata/diagnostic values and invariants.
4. `src/main/java/dz/sh/hidra/modules/topology/application/model/EngineeringNetworkDraftFailure.java` — typed application failure reasons for conflict, non-disclosing absence/denial, unavailability and indeterminate outcome.
5. `src/main/java/dz/sh/hidra/modules/topology/application/port/in/EngineeringNetworkDraftUseCase.java` — typed create/current-read/metadata-update/validate use case and materialized results.
6. `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringNetworkDraftRepositoryPort.java` — owned opaque acquisition, immutable context/draft/version storage, conditional append and final-guard port.
7. `src/main/java/dz/sh/hidra/modules/topology/application/service/ProtectedEngineeringDraftAdmissionCoordinator.java` — ordered genuine Identity/Party/network-context admission, common sample and compiled provisional ticket.
8. `src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringNetworkDraftApplicationService.java` — one compiled protected lifecycle, fresh preparation, no ambient transaction or automatic retry.
9. `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/EngineeringNetworkDraftRequests.java` — explicit bounded create/update/validate DTOs; strict rejection of ownership/graph/unknown mutation fields.
10. `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/EngineeringNetworkDraftResponses.java` — explicit context/draft/create-receipt/validation DTOs; no persistence/native credential exposure.
11. `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringNetworkDraftController.java` — four scoped routes and mapping, with no role/token shortcut around the actual use case.
12. `src/main/java/dz/sh/hidra/modules/topology/api/rest/EngineeringNetworkDraftApiExceptionHandler.java` — controller-scoped safe ProblemDetail/status mapping and precise rejected/indeterminate handling.
13. `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringNetworkDraftRepositoryAdapter.java` — real PostgreSQL owner/catalog/privilege/fence checks, scoped reads, exact insert templates and one final scalar guard.
14. `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringNetworkDraftCodec.java` — bounded deterministic metadata V1 bytes/SHA256 and strict decoding.
15. `src/main/resources/db/migration/V20261019_001__p25_topology_engineering_drafts.sql` — three new immutable Topology tables, constraints/triggers/indexes, four permission catalog entries, no grants to PUBLIC and no business grants.
16. `src/test/java/dz/sh/hidra/modules/topology/domain/model/EngineeringNetworkDraftTest.java` — domain binding/version/metadata/diagnostic/overflow tests.
17. `src/test/java/dz/sh/hidra/modules/topology/application/service/EngineeringNetworkDraftApplicationServiceTest.java` — orchestration, fresh authority, replay, rollback/failure and materialization tests.
18. `src/test/java/dz/sh/hidra/modules/topology/api/rest/EngineeringNetworkDraftControllerTest.java` — actual HTTP DTO/error/strict field/version/permission contract tests; genuine PostgreSQL HTTP proof belongs to the integration class.
19. `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/EngineeringNetworkDraftPostgresIntegrationTest.java` — fresh Flyway, real native crypto/owner/paired permission, actual HTTP/JDBC/physical completion and adversarial qualification.
20. `src/test/java/dz/sh/hidra/modules/topology/EngineeringNetworkDraftBoundaryGuardrailTest.java` — pure exports, finite reviewed callers/SQL families, no private foreign imports or station-boundary weakening.
21. `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProtectedEngineeringDraftAuthorizationPostgresIntegrationTest.java` — genuine network-specific Identity pair/source/mapping/DENY/replay/temporal/physical-context qualification.

**MODIFY — all verified present:**

1. `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedOperationIdentityParticipant.java` — add a separately tagged opaque engineering-draft pair with one actual native contribution/common sample; preserve old station/private diagnostic factories and contracts.
2. `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java` — add the one fixed, genuine-qualified engineering-draft family and pair loading; keep ordinary structural diagnostic load station-only and all original limits/claim checks.
3. `src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java` — admit exactly the new compiled draft service/owner coordinator and its finite INSERT family on the existing private publisher route; bind opaque permits to the actual issuing caller.
4. `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java` — narrowly extend exact caller assertions and reader byte pin for these named new seams; preserve every existing station assertion/case and all Party/Facility pins.
5. `src/test/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutorTest.java` — retain33 existing cases and add new compiled-caller/permit-family/SQL-template negatives.
6. `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReaderTest.java` — preserve current cases and prove fake/structural/station credentials cannot admit network-draft requests.
7. `.github/ci-test-inventory.json` — add actual new class/case/hash entries and update only source hashes/cases of changed existing tests; preserve lanes, measured baselines, all old cases and Full fallback.
8. `.github/database-dictionary-ownership.json` — truthful ownership/evidence for the three new tables only.
9. `doc/database/DATA_DICTIONARY.md` — capture/render actual new migrated catalog; if unavailable locally, clearly retain pending capture and reconcile hosted actual evidence, never invent physical facts.
10. `doc/api/openapi.yaml` — four generated draft routes/DTO/error contracts and exact backward-compatible snapshot equality.
11. `doc/roadmap/TASKS_HPS-004.md` — append implementation/evidence and synchronize only HAPI-587 current status.
12. `doc/roadmap/ULTIMATE_ROADMAP.md` — synchronize HAPI-587 status only.
13. `doc/roadmap/ULTIMATE_PHASES.md` — synchronize HAPI-587 status only.

No changes to existing migrations, station coordinator/publication adapter/service, legacy PipelineSystem model/service/routes, Facility/Party contracts or adapters, source claim retention/producer algorithms, protected DataSource grammar, publisher credentials/configuration, security filter chains, generic permission resolver, workflow or frontend files are admitted. Existing component scanning supplies the new beans; no bootstrap wildcard or architecture exception is needed. Cross-owner access stays through exported Identity/Party contracts, not foreign persistence classes. The new same-package Identity export fits the existing reviewed contract boundary.

**Owned representation and schema.** Select exactly these new tables in the new migration: `public.hidra_topology_engineering_context`, `public.hidra_topology_engineering_draft`, `public.hidra_topology_engineering_draft_version`. Context has server UUID identity, immutable company Party/pipeline-system IDs and UNIQUE(company_party_id,pipeline_system_id). Draft has server UUID identity, immutable context, client creation-request UUID and request digest; UNIQUE(context_id,creation_request_id). Version has composite primary key(draft_id,version), version>=1, immutable canonical metadata V1 bytes/format/SHA256, predecessor digest where applicable and scalar actor-binding/session/user, Party generation, common s/u and backend/XID provenance. No raw bearer, password, original external claim payload or reusable permit is persisted.

All three tables are append-only: no mutable current-head column is needed. Current draft version is the greatest immutable version via its draft/version index. Context→PipelineSystem and draft→context/version→draft use ordinary acyclic owned FKs with restrictive deletion; Party identity remains an owner-controlled scalar validated through the Party contract and final guard. Immutable guards reject update/delete/TRUNCATE and binding reassignments; version insertion requires the exact next version, initial1 and bounded monotone progression, refusing overflow. There is no dangling circular head/version FK or disabled-constraint initialization. Updates conditionally append exactly expectedVersion+1 only if the actual latest version equals expectedVersion; duplicate or concurrent stale writes cannot overwrite old bytes. Version uniqueness plus conditional append and retained fences enforce the CAS. Exactly one context may be reused for multiple independent draft candidates; uniqueness on pipeline_system_id alone is forbidden because it would implement rejected option B.

Metadata V1 contains nullable title (<=255 characters), description (<=16384 characters) and evidenceReference (<=2048 characters), with no invented defaults; context/Party/system identities are nonblank canonical IDs<=80 PostgreSQL characters, and new context/draft/request identities are canonical UUIDs. Canonical encoded metadata is bounded to32KiB. Supplied blank metadata may normalize to absent consistently before hashing; scope identities must not be silently relabeled. No graph, measured values, numerical state or publication state is added in this package. The draft state is DRAFT and never a published revision. Validation returns version-bound typed diagnostics including GRAPH_NOT_AUTHORED and any missing metadata; engineering completeness remains false in this metadata/context foundation. HAPI-588 owns typed graph editing/structural completeness and HAPI-590 governs publication/history. Do not weaken TopologyPhysicalNetworkRevision or advertise numerical/engineering qualification because metadata is valid.

**Actual permissions and Identity extension.** Four fixed codes `topology:engineering-draft:create`, `topology:engineering-draft:read`, `topology:engineering-draft:update`, `topology:engineering-draft:validate`, resource type `ENGINEERING_NETWORK_DRAFT`, resource reference=pipelineSystemId, action matching the suffix. The migration admits catalog definitions only, with consistent existing-code verification; it grants no user/group/role authority. Each operation evaluates the actual current actor twice: CUSTOM `topology-company:<companyPartyId>` and CUSTOM `topology-network:<pipelineSystemId>`. The exact request/private pair binds action, company, network, actual native source and physical context. Both must PERMIT; DENY/unknown cannot become partial authority. Existing global/role/group/policy semantics are evaluated by the same real evaluator, not by a wildcard/token-authority shortcut.

Use a new distinct typed Request/opaque participant contract; do not put a network ID into a Facility selector, change the old station Operation enum or repurpose station permission codes. The existing F participant adds a separately tagged, opaque company/network pair obtained from one genuine admitted native contribution. Its children retain exact original AuthorizationEvaluationRequest/context/thread/backend lifetime. Only that factory may tag the new family. Public diagnostic qualifyClaims or station factories cannot mint a network-qualified child by supplying a different resourceType. The reader's existing single loadQualified entry retains genuine consume/post-read checks and selects only the issuer-tagged finite family; add the separately named draft-pair load/consume/current methods. Ordinary structural load remains station-only. Reuse existing bounded parameterized graph queries and authentic external coverage/mapping/companion checks without widening arbitrary-resource inputs or adding another claim evaluator. Preserve all old station pairs/factory signatures and regression behavior.

**Owner eligibility, transaction order and final guard.** Prepare original native authentication outside protected work; reject ambient transactions. Enter through a new compiled-only draft execution entry on the existing private publisher route, with the same trusted-credential option A boundary and actual database endpoint. A disabled/misconfigured publisher route returns unavailable, never ordinary-connection fallback. New tables require ordinary principal SELECT-only and private publisher SELECT/INSERT, neither table/schema owner or elevated/inheriting DDL capabilities; PUBLIC gets no mutation privileges. Migration role names/secrets are not hardcoded. Synthetic acceptance explicitly provisions real separate admin/ordinary/publisher credentials. If actual grants violate this policy, admission fails closed.

Acquire in one writable READ COMMITTED physical transaction: actual Identity24 fences, Party2, then five new Topology fences on pipeline system, pipeline system type, engineering context, draft and version, all before common sample/eligibility. Topology acquisition uses fixed relation order and SHARE ROW EXCLUSIVE NOWAIT on those five tables. Required final subset is31 distinct relations:28 ShareRowExclusive,2 AccessShare,1 Share. Capture backend/XID/principal and actual owner requirements; extra compatible incidental locks are allowed. No Facility/publication-head fence is borrowed as network ownership. Only after all locks collect Identity's actual common s/u, verify current Party and read actual system/type/context. Logical system status DRAFT or ACTIVE and type status ACTIVE are eligible; missing/noneligible system/context/draft is non-disclosing not-found after authorization. Malformed/unsupported owner/catalog state is unavailable. Context bindings and the target draft must match the exact authorized company/network; lookup never loads an unrelated draft and then exports its owner metadata.

The coordinator owns opaque current admissions and one-use provisional permits. Platform may recognize only the two explicit old/new coordinator names and their matching compiled service entry points, with no business-class imports. Bind each permit to the issuing compiled owner and scope; old and new permits/SQL families cannot substitute. Preserve the old station allowlist and its exact behavior. The new family admits exactly the adapter's three reviewed INSERT templates (context, draft identity, conditional immutable version append), with frozen hashes derived from actual SQL constants and exact family/prefix matching; no arbitrary caller/table prefix, UPDATE, callback or dynamic SQL authority. Create may execute those three; update only the version append; read/validate/replay execute no provisional mutation. Any caught error poisons the whole physical attempt.

Flush before the one final closed scalar statement. One MATERIALIZED database t enforces s<=t<u, exact physical/principal coordinates, all31 requirements, current Party generation/eligibility, eligible logical system/type, exact immutable context/draft binding and expected current greatest version/digest/bytes. Freshly encoded/decoded V1 bytes and SHA256 must agree; every created/version row and requested receipt must match the actual stored provenance. No SQL or managed dirty flush after sealing; materialized DTOs leave only after observed physical commit. Existing500ms aggregate,250ms lock,750ms statement and1000ms idle limits are unchanged; no retry or longer timeout is introduced. Unknown commit acknowledgement is INDETERMINATE and withholds ordinary success.

**Exact bounded HTTP contract.** Native bearer authentication is necessary; live owner/paired authority remains mandatory inside the genuine use case even if a caller reaches it directly. No generic EffectivePermissionResolver/admin-role Boolean replaces admission.

| Route | Typed input and behavior | Successful output |
|---|---|---|
| POST /api/v1/topology/engineering-networks | companyPartyId, pipelineSystemId, creationRequestId UUID and nullable metadata. Find/create the exact company/network context, then create a candidate and immutable version1 atomically. |201 creation receipt containing context/draft identity and version1 metadata. |
| GET /api/v1/topology/engineering-networks/{draftId} | Required companyPartyId and pipelineSystemId query parameters, canonical UUID draftId. Exact current read after actual scoped authority. |200 materialized current version/metadata, immutable scope, DRAFT state and explicit engineering-complete=false. |
| PUT /api/v1/topology/engineering-networks/{draftId} | Same mandatory scope parameters; body expectedVersion>=1 and the complete metadata replacement. Null clears nullable metadata; missing required version is invalid. |200 appended next version; stale version409, no overwrite. |
| POST /api/v1/topology/engineering-networks/{draftId}/validate | Same mandatory scope parameters; body expectedVersion>=1. Read/validate that exact current version under distinct VALIDATE authority, with no publication or version append. |200 version-bound INCOMPLETE diagnostics, including absent graph; stale version409. |

For create, the same context/creationRequestId and identical canonical original request digest under fresh CREATE authority returns200 replayed=true and the immutable original version1 receipt without creating another candidate/version or reverting later edits. A changed request under the same key conflicts409. The receipt is explicitly historical creation output; clients GET current state before editing. Final guard checks the actual observed latest version independently from the replayed version1 receipt. Replays never bypass current authority, owner eligibility, deadlines or physical commit observation. The server never automatically retries an indeterminate attempt.

Return400 for malformed IDs/DTOs, bounds, expected-version omission and unsupported graph/owner/transfer/unknown mutation fields; use strict local request binding without changing global Jackson policy.401 comes from unauthenticated native HTTP entry;403 is a generic current-authority denial independent of protected existence;404 is generic authorized-scope target/reference absence or ineligibility;409 is exact stale-version/replay conflict;503 is safe storage/fence/time/credential unavailability or an explicitly distinguished INDETERMINATE outcome. Controller-scoped advice unwraps only recognized typed failures/outcomes, never publishes SQL, grants, PID/XID, other-company context, tokens or stack details. No list/count/export/history/delete/transfer/publication endpoint is added. Existing controllers/routes and OpenAPI contracts remain backward compatible. This registration is a backend contract, not accepted HidraWEB integration.

**Finite verification contract.** Six new test classes listed above cover domain invariants, actual application/HTTP contract, boundary enforcement and the two real PostgreSQL owner/Identity paths. Required scenarios: same network with two isolated company contexts; one company/network pair reusing exactly one context for multiple candidates; exact create replay/different-payload conflict; metadata version progression/overflow; same-expected-version contention with at most one successor; stable incomplete diagnostics and no station/publication effects; actual LOCAL/OIDC/LDAP/AD native producers and verified claim mappings; either-scope grant removal/DENY/unknown; forged/replayed/cross-thread/context credentials; actual Party/system/type ineligibility; phantom/context/native writer-first and reader-first fences through commit/rollback; expiry after real provisional context/draft/version work at the actual final guard; caught SQL/dirty flush/post-guard and unknown acknowledgement; ordinary direct DML/rebind/DDL/RLS/trigger tamper rejection; exact final lock subset; wrong-family/compiled-caller/provisional SQL rejection; HTTP round trips, strict fields and safe status/error shapes. Test source/fixture failure or arbitrary exception is never adversarial proof; require exact stage/cause and independent persisted effects.

Preserve all46 genuine station cases and other old test identities, including existing executor33 and all current source/claim/owner suites. Boundary assertion changes admit these exact new callers/family only and retain old negative cases plus concrete new negatives. Update source pins only for truly changed sources after review; do not delete fingerprint checks to obtain green CI. No new test manifest entry or invented hash/count is written by registration. Mandatory hosted implementation-SHA Full/Fast/Docs must execute every required new PostgreSQL case without skips, retain raw case/hash/partition/oracle equivalence and actual new OpenAPI/catalog artifacts. Use already available checks locally under AGENTS11.1; missing Maven/Java21/services do not require provisioning or permit a fabricated pass. Dictionary/OpenAPI generation/equality remains required; if actual catalog capture is unavailable locally, preserve truthful pending provenance and reconcile actual hosted artifacts in this same task.

**Status, validation and next action.** A resolves the original owner-policy blocker. HAPI-587 is now NOT_IMPLEMENTED/REGISTERED_PENDING; no runtime test/migration/API/credential change occurs in this registration. Future implementation changes its implementation status to PARTIAL with planning REGISTERED_PENDING in all three current definitions/registries, then a separate exact-source acceptance review may mark VERIFIED/COMPLETED only after original bounded criteria and Full evidence resolve. The acceptance follow-up message is `docs(topology): record engineering draft acceptance`. All successor and parent statuses remain unchanged.

All21 CREATE absences and13 MODIFY presences were verified against the exact current tree; the selected migration is the first unused20261019 version after the live20261018_003 tail. Local checks pass:17 documentation-validator fixtures; canonical documentation (128 documents/921 tasks/25 phases); offline OpenAPI snapshot (263 operations/244 paths/231 schemas); exact three-document scope, frozen-field/history/status-only restoration, all34 path presence/absence checks, migration-tail and whitespace checks. No Java/PostgreSQL execution is claimed by this registration. Publish this three-document registration directly to main with expected-head guard and exact registered message, verify blobs/tree/parent/scope, observe applicable same-SHA Docs creation/startup and STOP. After Docs acceptance, next owner Next implements the single complete34-path package; no repeated ownership question or additional generic design stage is selected.


### HAPI-587 implementation delivery — 2026-10-19; CI-first acceptance pending

Owner selected the registered implementation with Next and explicitly approved the PostgreSQL privilege correction in this session. Baseline main `c607bf74ee8243c5d6c21c6626794e8e1693273f`, parent `cdfd0e5bdeaa4d68c37b54d9f55a215e3e08e0c9`, tree `2fa32481a356b3d5e9c8cd9d9e3c3844f70c5907`; exact-head Documentation431 SUCCESS. The separate accepted code/test baseline remains `a06b5aa8da0d25fb793105dbe530e61a610a1469` with Full760/Fast85 SUCCESS; those conclusions are not tests of this new implementation.

**Approved privilege correction, superseding only the earlier new-table grant specification:** the private publisher needs SELECT/INSERT/UPDATE on the three new engineering tables. PostgreSQL16 requires UPDATE/DELETE/TRUNCATE or ownership to acquire SHARE ROW EXCLUSIVE; SELECT/INSERT alone cannot take the registered fences. UPDATE is granted for lock eligibility, while statement-level immutable triggers reject every UPDATE, including zero-row attempts. Neither ordinary nor publisher receives DELETE/TRUNCATE/ownership/schema/trigger-DDL privileges. Ordinary remains SELECT-only on these tables. Deployment-role grants are not hardcoded by migration. New actual-role tests assert lock acquisition, SQLSTATE23514 on publisher UPDATE, and42501 on ordinary mutation/DDL, with independently inspected stored data. This is a test specification/source claim until the new exact-SHA tests execute.

**Implemented source:** separate issuer-tagged company/network claim pairs and real existing-policy evaluation; opaque ordered Identity24/Party2/Topology5 admission; private route with issuer-bound provisional SQL families; pure immutable metadata/context/version models, bounded canonical UTF-8 codec and SHA256; additive `V20261019_001` with three tables, immutable guards, sequential-version/owned FK constraints and four permission definitions; create replay preserving original version1, current reads, conditional metadata append, version-bound incomplete diagnostics; four explicit REST routes, strict unknown-field rejection and safe typed errors. The final scalar checks common database time, physical coordinates/principal, all31 owner fences, current Party/logical-network eligibility, exact context/draft/current-version/payload and provisional provenance before physical commit. Existing station operations, migrations, timeouts and independent ownership remain preserved.

Source reality uses `hidra_topology_pipeline_system_type.active=true`, the existing boolean catalog representation of active type eligibility; no fictional status column is introduced. The admitted31-fence subset remains28 ShareRowExclusive/2 AccessShare/1 Share, taken in owner order before common eligibility sampling. New tests are registered with actual class/case/source identities and zero unmeasured timing; old cases/lane definitions/measured baselines remain.

**Actual local checks:** Java compiler module17 parsed45 recovered/new Java sources without syntax errors. The pure draft model, codec, command/result contract and typed failure compile with that available compiler; an executable smoke check passed Unicode/null roundtrip, immutable version progression, graph-incomplete diagnostics, trailing-byte rejection and encoded/title bounds. Documentation-validator fixtures17, OpenAPI-validator fixtures10 and inventory-validator fixtures26 PASS. These are not Java21/Spring/JUnit/PostgreSQL or industrial qualification. Maven and PostgreSQL/Docker services were not provisioned. Hosted Full/Fast/Docs must execute and qualify the new cases without database skips and retain original station/native cases and partition/oracle equivalence.

**Artifact/publication limit — explicitly outstanding, no fabricated capture:** preserve the existing canonical OpenAPI snapshot and actual-catalog dictionary bytes/provenance in this initial CI-first source delivery. Four new public operations require a genuine hosted OpenAPI snapshot refresh and exact equality; adding physical tables requires actual migrated-catalog capture. The canonical snapshot validator also binds `.github/documentation-validation.json` to successful hosted generation provenance/digest/counts. That manifest is outside the currently registered34-path envelope; no unregistered mutation or invented successful run/artifact identity is made. Runtime snapshot equality is therefore a known outstanding acceptance gate, expected to reject the old contract after these endpoints are generated. Resolve the exact artifact/manifest admission and reconcile genuine generated OpenAPI/catalog evidence before acceptance; do not disable equality or mark this task verified. AGENTS11.1 permits truthful pending dictionary regeneration for the initial CI-first publication. Database ownership metadata records the three new intended owners without claiming a new physical capture.

**Disposition:** HAPI-587 PARTIAL/REGISTERED_PENDING; no successor selected. Publish exact message `feat(topology): implement company-scoped engineering drafts`, only admitted implementation/test/schema/ownership/inventory paths and the three task/status documents; canonical generated artifacts remain preserved pending real evidence as explained above. Verify expected main, exact scope/parent/tree/blobs and observe applicable same-SHA CI startup, then STOP. Next remains completion/repair and evidence reconciliation of HAPI-587, followed only when its original criteria and artifacts are satisfied by `docs(topology): record engineering draft acceptance`; HAPI-588 is not selected.


### HAPI-587 Fail repair — 2026-10-19; qualification remains pending

Owner Fail selects repair of actual Full761/Fast86 failures on `a98ef0ea115b85a80941be40406511334b4b477a`, tree `a468726bd4f53b40f683fd8c2767061485d7f3fb`; Documentation432 SUCCESS. Live main/parent/tree and all failed job conclusions/logs were refreshed before this repair. Oracle reported2491 tests,4 failures,14 errors,0 skips; Fast reported1588 tests,4 failures,0 skips. PostgreSQL shard0 succeeded; shard1 failed during the14 new Topology cases' fixture setup. API contract generation succeeded but strict equality correctly rejected the stale committed snapshot. Secondary missing-report/lane/gate failures do not represent independent acceptance.

Exact repair message: `fix(topology): repair engineering draft qualification`. This is the owner-selected failure repair of HAPI-587, not a successor stage or parent acceptance.

**Source-backed root causes and corrections:**

- The new claim-family discriminator was a mutable field, violating the retained private-final credential assertion. Make it final and assign it only through private constructors; old six-argument factories continue issuing station/diagnostic credentials with false, and only the genuine draft-pair factory supplies true. Preserve the existing assertion unchanged and strengthen the new family's guardrail to check every credential field.
- Request DTO helper methods imported and returned a non-enum domain Metadata value. Remove those helpers/imports and construct the domain value explicitly in the controller mapping. Request/response DTO fields and external operation IDs remain unchanged; do not exempt domain leakage from the guard.
- The Forensic boundary assertion lacked the newly registered public Identity contract used by the one new compiled coordinator. Admit only that exact source-class/import pair, consistent with the prior station coordinator rule. No general package exception, foreign persistence import or removed existing assertion is allowed.
- The reader's exact public-method assertion omitted the admitted opaque draft-pair method. Add only that name and assert its exact two opaque/typed parameters and existing paired-evidence return type; retain every structural/fake/station denial case.
- The new Topology fixture INSERT referenced nonexistent `user_permission_grant.updated_at` and omitted actual mandatory `grant_reason`, `emergency_access` and `valid_to`. Match the current JPA/schema and already-working retained grant fixture. Use the real REVOKED/revoked_at lifecycle instead of fictional INACTIVE grants. No migration is changed. Close the newly introduced Identity test context after its class to release its pool before its disposable PostgreSQL stops; retain all case identities.

**Necessary supporting repair scope, explicitly recorded:** MODIFY exactly13 paths: `ProtectedOperationIdentityParticipant.java`; `EngineeringNetworkDraftRequests.java`; `EngineeringNetworkDraftController.java`; `ForensicRemediationClosureTest.java`; `JdbcProtectedAuthorizationEvidenceReaderTest.java`; `EngineeringNetworkDraftPostgresIntegrationTest.java`; `ProtectedEngineeringDraftAuthorizationPostgresIntegrationTest.java`; `EngineeringNetworkDraftBoundaryGuardrailTest.java`; `.github/ci-test-inventory.json`; `doc/api/openapi.yaml`; `.github/documentation-validation.json`; `doc/api/OPENAPI_GOVERNANCE.md`; this `doc/roadmap/TASKS_HPS-004.md`. All Java files retain their actual registered package paths. The Forensic test and OpenAPI manifest/governance are necessary supporting files identified by the actual failed gates; their bounded repair admission follows the owner Fail instruction and adds no product behavior. No CREATE/DELETE, workflow change, historical migration edit, other task status change, release or deployment is selected.

**Actual artifact provenance and contract correction:** retrieve artifact12191249823 from Full761/run39264047957/attempt1, `hidra-ci-diagnostics-a98ef0ea115b85a80941be40406511334b4b477a-39264047957-1-api-contract`. Verify ZIP SHA256 `db55d874636feba7df69dab8ac112403e06a79d820bdb23ae94af38fb0f70f75` before extraction. Actual `openapi/hidra-api.json` SHA256 is `1144f929d23a0b3d4bb63fc2bac645c6ca1a1cb9b9e59b91deeb8933305842ce`, with generator SHAa98ef0e. Canonical compact bytes SHA256 `b4e5f611148bce49e9855c5f5af944499e3a3cf69e995c3c7176b49ffd82546a`;247 paths/267 operations/236 schemas. Reconstruct the complete generated object without endpoint/schema/security edits. Every old path/operation and every old schema remains exactly equal; only three path items/four operations/five schemas were added. Existing strict validator code remains unchanged. The manifest explicitly scopes its success conclusion to named generation step7 in job118098882769 and records both aggregate job/workflow conclusions as failure. This neither claims Full761 success nor accepts HAPI-587. Old generation history remains preserved in OpenAPI governance.

**Actual checks:**47 recovered/new Java files parse without syntax errors using the available compiler module. Documentation-validator17/OpenAPI-validator10/inventory-validator26 fixtures PASS. Canonical snapshot validation and strict semantic equality against the actual a98ef0e runtime-generated artifact PASS. The existing backward-compatibility checker against the previous canonical contract PASS; all old path/schema objects independently compare equal. Retain the exact test cases, lanes and measured baselines; refresh only the five modified test source hashes. Java21/Spring/JUnit/PostgreSQL repair execution is not claimed locally. No complete local environment is provisioned.

**Disposition and remaining gates:** remain PARTIAL/REGISTERED_PENDING. After guarded direct-main publication, observe applicable exact-repair-SHA CI creation/start and STOP. Required new/old Java/PostgreSQL cases, same-run raw partition/oracle evidence, fresh runtime snapshot equality and actual migrated dictionary capture/reconciliation remain mandatory. The API job previously skipped dictionary collection after equality failed, so no new physical dictionary is invented from source. Preserve its current bytes pending the next genuine hosted catalog artifact. If dictionary equality then fails, repair it from that actual artifact before acceptance; never disable the check. No successor or `docs(topology): record engineering draft acceptance` is selected by this Fail repair.

### HAPI-587 Full CI #762 failure repair — 2026-10-19; exact-SHA execution pending

Owner Fail/Continue retains HAPI-587 (legacy HPR-P25-052A); HAPI-588 and HAPI-589 remain blocked. The refresh baseline is main `85e9850a2bdfc8429638b50534e252f0ef618f6a`, parent `a98ef0ea115b85a80941be40406511334b4b477a`, tree `3c8f5881f3284440cd6bc65265b797c332d317b9`. Full CI #762 / run `39267812459` FAILED on that exact SHA; Fast #87 and Docs #433 passed. Java21 unit/architecture, static checks and PostgreSQL shard0 passed; PostgreSQL shard1 and the full-suite oracle each reported 2 assertions failed (no errors/skips): engineering-draft actual bearer POST expected201 received403, and retained protected-station deadline expected a fractional input time where the qualified expiry was second-truncated. OpenAPI strict 247-path/267-operation/236-schema equality passed; actual PostgreSQL dictionary equality failed solely because the committed capture predated HAPI-587.

**Admitted bounded follow-up:** commit message `fix(topology): reconcile engineering draft CI 762 qualification`; MODIFY only `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/EngineeringNetworkDraftPostgresIntegrationTest.java`, `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/ProtectedStationAdmissionPostgresIntegrationTest.java`, `.github/ci-test-inventory.json`, `doc/database/DATA_DICTIONARY.md` and this task document; 0 CREATE/DELETE. No authorization-grant, production-code, Flyway-history, workflow or API contract relaxation is admitted. The HTTP fixture clears its deliberately synthetic ambient wrapper before exercising the actual signed bearer through Spring Security, independently checks the genuine JWT decoder, and preserves the exact201 / unknown-field400 assertions; a repeated403 retains response/exception diagnostics rather than converting DENY to success. The station test preserves its observed post-deadline materialized guard, failure and rollback, and compares the admitted deadline to the second-granularity effective value rather than its original microsecond fixture input. Both registered tests and all other cases remain in their existing lanes; refresh only their two raw source SHA-256 inventory hashes.

**Actual catalog evidence and deterministic regeneration:** Full CI #762 artifact `12194265393` / run `39267812459` contains `database/schema.json`, SHA-256 `3854093aae6661c206a240b017affe6313fce93388dd568ca65aa9a83134556b`, captured against source SHA `85e9850a2bdfc8429638b50534e252f0ef618f6a`. Its 155 successful migrations/470 JPA mappings/500 physical relations (three added engineering tables) and reviewed ownership produce regenerated CURRENT Markdown SHA-256 `20ba76f51f5b86c732caef8a04752b8a31d8807787dcc6fa0efc51bf55ab6d74`, matching the separate Full CI oracle's `HIDRA_P25_C3C_CATALOG_VERIFICATION` value. The full retained 497-relation snapshot was reconciled using the repository renderer's exact sort, catalog metadata, source migration mentions and whitespace rules; the independent expected SHA-256 matched byte-for-byte, with no fabricated catalog object or production data. Strict source-SHA capture equivalence ignores only the recorded previously verified capture SHA, as specified by the existing unmodified dictionary validator.

**Validation and status:** The inventory raw hashes are pinned to these exact proposed Java test bytes. A local deterministic dictionary rebuild reproduces the oracle's complete Markdown and Git blob SHA; real Spring/JUnit/PostgreSQL execution of the updated five-path repair has NOT occurred prior to publication. This is a CI-first repair attempt, not a claimed pass or acceptance. On guarded direct-main publication observe only new exact-SHA Full/Fast/Docs CI creation/startup, then STOP. If Full still fails, inspect actual denial and repair HAPI-587 before advancement; do not mask 403 or weaken scope checks. HAPI-587 remains PARTIAL/REGISTERED_PENDING until a separate exact-source acceptance review; neither HAPI-588 nor `docs(topology): record engineering draft acceptance` is selected here.


### HAPI-587 Full CI #763 fail repair — 2026-10-19; exact-SHA CI qualification pending

**Selection and evidence.** Owner Next after registered Full CI #763 startup selects CI-first repair within HAPI-587, not the blocked HAPI-588 successor. Baseline main `fb74fe5cd55d66ff7723c35f7942d519e3ed94de`, parent `85e9850a2bdfc8429638b50534e252f0ef618f6a`, tree `30bf69d078a155866d9cd2de50572ceed17aa24e`. Full CI run `39277479595` on this exact SHA has PostgreSQL shard1 failure (job `118145487986`) and full-suite/oracle failure (job `118145487168`), each two assertion failures and no test skips/errors. Its unit/architecture, static validation, PostgreSQL shard0 and API contract/dictionary jobs passed; Fast #88 and Documentation #434 passed on the same SHA. The overall Full CI gate was still queued when evaluated; do not infer Full success from successful jobs.

**Verified failure causes.** The real signed-bearer HTTP POST is stopped *before* the native HAPI-587 Identity/Party/Topology admission by `HidraRouteAuthorizationInterceptor`: its generic route name `topology:engineering-networks:execute` is not one of the four registered company/network dual-scope HAPI-587 permissions `topology:engineering-draft:{create,read,update,validate}`. Full CI #763 captured the exact 403 problem response and exception. An unrelated route grant would silently change the four-code contract and let the legacy generic route naming replace protected scope checks. The protected-station expiry test's actual qualified upper bound can retain the input's fractional seconds or be second-truncated, so asserting either exact representation is unsound; its real materialized database-time check must still reject commit after the actual admitted bound.

**Admitted bounded repair.** Commit message `fix(topology): repair engineering draft native route admission`. MODIFY exactly five paths: `src/main/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptor.java`; `src/test/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptorTest.java`; `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/ProtectedStationAdmissionPostgresIntegrationTest.java`; `.github/ci-test-inventory.json`; this `doc/roadmap/TASKS_HPS-004.md`. 0 CREATE/DELETE. The interceptor delegates *only* the exact four compiled `EngineeringNetworkDraftController` handler/method/HTTP-route tuples to the registered protected native authorization, and still requires an authenticated nonanonymous principal. It does not add a public path, trust token role/wildcard permission, inspect protected existence, or affect any other route's generic check. The native draft service/Identity admission remains the only permitted owner-scoped business authority, with genuine same-transaction two-scope evaluation, final guard and denial. Add regression tests covering all four exact method/route tuples, unauthenticated access, foreign handler and wrong method. The station fixture asserts its captured upper bound lies within [fractionally truncated to seconds, original deadline], while preserving observed post-expiry physical guard denial and rollback, other 45 existing cases and exact class inventory.

**Inventory and validation limitations.** Update only actual test source hashes and append two new route-interceptor test identities in the existing unit/architecture lane; preserve its measured baseline, all existing cases, the 46-case station lane and CI gate definitions. Java source syntax and the two SHA-256 inventory hashes were checked in the candidate; no local Maven/PostgreSQL run or actual runtime outcome is claimed. No changes are admitted to historical migrations, protected SQL, permissions/grant tables, OpenAPI, migrated catalog/dictionary, workflow or external operations. After guarded direct-main publication, verify parent/tree/exact five blobs and observe applicable exact-SHA CI startup, then STOP. If the new Full CI fails, repair HAPI-587 again; if it passes, perform the separately registered evidence-based HAPI-587 acceptance `docs(topology): record engineering draft acceptance`. Until acceptance HAPI-587 stays PARTIAL/REGISTERED_PENDING and HAPI-588/HAPI-589 remain BLOCKED.

### HAPI-587 exact-source software acceptance — 2026-10-19

**Registered close-out:** `docs(topology): record engineering draft acceptance`. This acceptance is confined to HAPI-587 (legacy HPR-P25-052A) company/network-scoped engineering draft create/read/update/validate. Software implementation is VERIFIED and planning COMPLETED based on the **actual current code SHA** `dcaef72fc7acd3c2079c48e4dbfb111dd377f24a` (parent `fb74fe5cd55d66ff7723c35f7942d519e3ed94de`, tree `866e877b6b83c744696700ca847103616df3e055`). Three documentation modifications only: `doc/roadmap/TASKS_HPS-004.md`, `doc/roadmap/ULTIMATE_ROADMAP.md`, `doc/roadmap/ULTIMATE_PHASES.md`. No code, tests, Flyway, security configuration, inventory, API schema, dictionary, migrations or unrelated task statuses are altered.

**Exact-source workflows (all successful; first attempts):** Full CI #764, run `39283944973`, <https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39283944973>; Fast Feedback #89, run `39283944922`, <https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39283944922>; Documentation #435, run `39283944881`, <https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39283944881>. Each run's `head_sha` exactly matches `dcaef72fc7acd3c2079c48e4dbfb111dd377f24a`. All seven Full CI jobs succeeded: unit/architecture, Maven oracle, PostgreSQL integration shards 0 and 1, API contract/dictionary, static validation and Full Verification Gate. Earlier failed Full #761/#762/#763 remain historical evidence and are not relabeled as passed.

**Retained first-party machine-readable execution proof:** Full CI run764/attempt1 artifact `12203197682`, `hidra-full-ci-evidence-dcaef72fc7acd3c2079c48e4dbfb111dd377f24a-39283944973-1`, contains `gate.json` SHA-256 `4fb7f3bbd7e30f738316066e0f879c3962a53979bb5d79ae630f7ea3c646b3e9`. Its source/run/attempt fields match the above and report `2493` tests in `419` classes, `0` failures, `0` errors, `0` skipped, `same_run_equivalence=true`, `fallback_required=true` and partition-shadow mode. Raw Surefire XML within that same artifact was independently inspected: oracle `2493/0/0/0`; PostgreSQL shard0 `243/0/0/0`; shard1 `616/0/0/0`; unit/architecture `1634/0/0/0` (tests/failures/errors/skipped). The duplicated oracle and partitions are distinct executions; do not sum these totals as unique tests.

**Named HAPI-587 positive and negative coverage executed (raw XML, oracle and matching lane):**
- `EngineeringNetworkDraftPostgresIntegrationTest` `14/0/0/0`: four-provider native create/read/update/validate; actual signed bearer HTTP with malformed field rejection; company isolation on a shared network; same-context multiple candidate drafts; immutable version history and replay digest; optimistic and concurrent writes; publisher/ordinary SQL permissions; revocation and Party ineligibility; disabled DB trigger; actual final-guard owner fence/expiry and lock rollback.
- `ProtectedEngineeringDraftAuthorizationPostgresIntegrationTest` `5/0/0/0`: native paired authorization and fail-closed identity evidence.
- `ProtectedStationAdmissionPostgresIntegrationTest` `46/0/0/0`: retained station transaction, writer exclusion, source revocation and effective deadline regressions.
- `EngineeringNetworkDraftBoundaryGuardrailTest`, `EngineeringNetworkDraftApplicationServiceTest`, `EngineeringNetworkDraftControllerTest`, `EngineeringNetworkDraftTest` each `3/0/0/0`; `HidraRouteAuthorizationInterceptorTest` `7/0/0/0`. Original security and station route boundary remain tested; a generic route permission does not substitute for the exact native company/network decision.

**Exact migrated-schema and API evidence:** Full CI run764 schema artifact `12203616020` (actual disposable PostgreSQL16.15 catalog) includes `schema.json` SHA-256 `b7e2dbd95179006d32cbd41026f9ea4a9d0e0df2ceae7d482895b8167d56e82e`, reviewed CURRENT `DATA_DICTIONARY.md` SHA-256 `bd468df08132603825828fb4963c6fdeff3cabec0a8103f18191ecce29df1a85`, `verification.json` SHA-256 `14899f7150a13a0c518500fe0046c3caa190f0b55d8a96fc8f43618f84c37979`, `reviewed_dictionary_checked=true`, 155 migrated versions, 470 JPA mappings, 500 relations, zero unresolved owners. Includes three separately identified Topology-owned tables: `hidra_topology_engineering_context`, `hidra_topology_engineering_draft`, `hidra_topology_engineering_draft_version`. Full CI run764 OpenAPI artifact `12201593108`: exact `hidra-api.json` SHA-256 `ad7c325399e924df2190a939be5a94f5b18869ef27983b930ffae06bf8ac1207`, 247 paths/267 operations/236 schemas with four draft operation IDs `createEngineeringNetworkDraft`, `readEngineeringNetworkDraft`, `updateEngineeringNetworkDraft`, `validateEngineeringNetworkDraft`. Strict API compatibility and physical dictionary CI gate passed on the same source.

**Acceptance boundary:** One logical pipeline system can hold separate company-isolated contexts, exactly one context per company/network pair, with multiple candidate drafts and append-only versions. Bindings are immutable and ownership transfer is unsupported; no draft publication, physical graph authoring, survey/CRS authoring or numerical simulation is claimed. No actual production database, live SCADA/OT access, field calibration, engineering validation of hydraulics or SITE operational acceptance is established by synthetic PostgreSQL and CI. HAPI-588 and HAPI-589 are *not selected* by this commit and remain BLOCKED in their existing register rows until separately authorized. Next owner `Next` may select one registered successor only after rechecking this documentation-only acceptance commit's exact-SHA CI.

<a id="hapi-588"></a>
## HAPI-588 — Physical network and station incidence authoring

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HPR-P25-052B

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-587

Cross-Repository Dependencies: HWEB-F02, HWEB-F03 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Physical network/branch/station/port graph authoring and incidence validation.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyPhysicalNetworkRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevision.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodec.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContractTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/TopologyPhysicalNetworkRevisionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodecTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver company/network drafts, physical graph editing and protected publication/history APIs after owner-fence acceptance. The exact bounded remaining delivery is: Physical network/branch/station/port graph authoring and incidence validation..

### 4. Functional Requirements

Persist/reload supported serial/parallel graphs; reject dangling/wrong-owner/wrong-kind references; retain exact source identities and graph precision. Installed-unit completion remains054.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevision.java`, `src/test/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevisionTest.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyPhysicalNetworkRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodec.java`, `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/TopologyPhysicalNetworkRevisionPostgresIntegrationTest.java`, `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodecTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringGraphDraft.java` — pure Java owned aggregate/value and invariants for typed nodes, branches, ports and incidence; no dangling or wrong-owner graph links.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/EngineeringGraphDraftUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringGraphDraftRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringGraphDraftApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringGraphDraftController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringGraphDraftRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyPhysicalNetworkRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevision.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodec.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContractTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/TopologyPhysicalNetworkRevisionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodecTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `PUT /api/v1/topology/engineering-networks/{id}/graph` for typed nodes, branches, ports and incidence; no dangling or wrong-owner graph links. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContractTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryServiceTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevisionTest.java` — exact-head oracle suite 20 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/TopologyPhysicalNetworkRevisionPostgresIntegrationTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodecTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Persist/reload supported serial/parallel graphs; reject dangling/wrong-owner/wrong-kind references; retain exact source identities and graph precision. Installed-unit completion remains054.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-587. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23357](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23357)
- [doc/model-remediation/RECONCILIATION.md:23735](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23735)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1043](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1043)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1295](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1295)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1043 | HPR-P25-052B | BLOCKED_SPECIFICATION | Physical network and station incidence authoring
doc/roadmap/ULTIMATE_ROADMAP.md:1295 | HPR-P25-052B | Physical network/branch/station/port graph authoring and incidence validation. | Persist/reload supported serial/parallel graphs; reject dangling/wrong-owner/wrong-kind references; retain exact source identities and graph precision. Installed-unit completion remains054.
doc/model-remediation/RECONCILIATION.md:23357 | HPR-P25-052B | Physical network/branch/station/port graph authoring and incidence validation. | Persist/reload supported serial/parallel graphs; reject dangling/wrong-owner/wrong-kind references; retain exact source identities and graph precision. Installed-unit completion remains054.
doc/model-remediation/RECONCILIATION.md:23735 | HPR-P25-052B | BLOCKED_SPECIFICATION | Physical network and station incidence authoring
~~~~~~~~

### HAPI-588 source-backed contract review — 2026-10-19; executable registration not yet admitted

**Owner selection and checkpoint.** Owner's Next/approval selects the next registered HAPI-588 specification step only, after the HAPI-587 acceptance commit `51bd252c2b86a7581a8be6a7cd983fd9dcd95ba9` (parent `dcaef72fc7acd3c2079c48e4dbfb111dd377f24a`, tree `b3696d17c42c884f9d6037ebe3e813b2e8ec1ef0`). Exact-head Documentation #436 / run `39311112300` passed; the accepted HAPI-587 implementation's Full #764/Fast #89/Docs #435 passed at `dcaef72fc7acd3c2079c48e4dbfb111dd377f24a`. HAPI-587 is VERIFIED/COMPLETED, satisfying the dependency; HAPI-588 remains NOT_IMPLEMENTED/BLOCKED_SPECIFICATION until its executable delivery envelope is reconciled. This review does not select HAPI-589, HAPI-590 or publication.

**Exact source observations (verified at acceptance SHA).**
- `EngineeringNetworkDraft` is a company/network/context-bound immutable metadata draft at an incrementing `(draftId,version)`; `diagnostics()` always currently reports `GRAPH_NOT_AUTHORED`. The application command is limited to CREATE/READ/UPDATE/VALIDATE; an independent graph writer cannot create a second head, silently change the accepted metadata version semantics or remove graph-incomplete diagnostics without a reviewed aggregate version rule.
- `V20261019_001__p25_topology_engineering_drafts.sql` already owns an append-only draft version table, a predecessor digest, a draft-row lock enforcing monotonic version, and immutable trigger protections. The JDBC adapter verifies the current immutable catalog, uses fixed provisionally admitted INSERT SQL templates and a single materialized final guard. Adding graph storage by arbitrary ordinary JDBC writes, mutating the existing version rows, or reusing the three-template permit without admission is not allowed.
- `ProtectedEngineeringDraftAdmissionCoordinator` and the Identity-native `TopologyProtectedEngineeringDraftAuthorizationContract` currently admit only the four draft actions, with Identity24/Party2/Topology5 relation fences and actual company + logical-network CUSTOM scopes. A new graph-edit endpoint needs action mapping to the existing UPDATE right or a separately approved new action, an exact compiled MVC method admission, actual relation locks/SQL family, common transaction/time and final graph-version scalar checks; the original Station/Facility authorization path is not an acceptable substitute.
- `TopologyPhysicalNetworkRevision` requires at least two connected nodes, one real pipe, supplied SI dimensions/elevation and immutable scope/provenance; it is a qualified immutable physical-source representation, not a mutable or partially authored candidate. Its `TopologyPhysicalNetworkRevisionCodec` preserves historical V1/V2 bytes. Do not reuse or reinterpret those formats for incomplete HAPI-588 drafts, and do not create synthetic engineering measurements.
- `FacilityJpaEntity` has a distinct `ownerPartyId`, `facilityKind`, coordinates and status; `PipelineSystemJpaEntity` owns a logical system identity/type/status but no company owner. A station reference therefore requires a fresh actual Topology owner/kind/eligibility witness for the company context and must not be inferred from same-network naming, a caller claim or a UI selector. HAPI-588 cannot transfer Facility ownership or reset its existing owner-generation head.

**Proposed bounded contract for executable registration (not an API claim or authorized file list).** Keep one **Topology-owned engineering draft aggregate** with exactly one monotonically increasing version across metadata and graph writes, immutable context company+network, explicit stable authored node/pipe/branch/station/port identities, and deterministic graph payload bytes/digests; represent incomplete sketches separately from qualified immutable physical revisions. Admit a PUT of typed graph content only after native UPDATE authority, actual company/network and station owner eligibility, prior-version CAS and all physical fences. Require duplicate/dangling/wrong-owner/wrong-kind rejection; support serial and parallel incidence with explicit node-port endpoints; do not invent lengths, elevations, diameters, equipment performance or operating measurements. No publication, facility transfer, effective revision or numerical solver is part of this task.

**Registration/acceptance gaps, kept explicit:** decide the precise representation of node/branch/station/port references and supported incidence cardinalities from actual domain catalog; select whether graph update appends one shared complete draft snapshot or a separately linked immutable graph event with a single canonical head; register the exact compiled classes/DTOs, new migration filename after current inventory, owner/foreign-key fences and approved provisional insert hashes, guarded repository caller, final scalar guard, complete CREATE/MODIFY scope and exact implementation message. Test real signed-bearer PUT, current native company/network permissions, owner and station-kind mismatch, parallel/serial reload, duplicate/dangling identities, competing CAS, metadata-graph interleaving, rollback/revocation/expiry, deterministic replay and unchanged historical snapshots in fresh PostgreSQL. Then require source-hashed CI inventory, strict runtime OpenAPI, migrated physical data dictionary and exact-SHA Full/Fast/Docs; no Docker skip or static inspection is database proof.

**Disposition:** this is source-backed **specification review**, not HAPI-588 registration, implementation, API acceptance or deployment. Preserve the frozen NOT_IMPLEMENTED/BLOCKED statuses in ROADMAP and PHASES. The six proposed source filenames in section6 are not yet an executable allowlist. The next authorized step is to close the above contract and register one bounded implementation envelope before any source/migration/CI-inventory modification.


### HAPI-588 exact bounded implementation registration — 2026-10-19; owner Continue

**Authorization and baseline.** Following the owner-selected source-backed HAPI-588 contract review, Continue selects the *registration* of a bounded staged execution envelope, not immediate code delivery. Source baseline `ee572d53ec46ff80c67f71614ac7c2b4a09f3d16`, parent `51bd252c2b86a7581a8be6a7cd983fd9dcd95ba9`, tree `3f929a5eae4a3c788b6c75c12f918a021f234239`; Documentation #437 SUCCESS. HAPI-587 is VERIFIED/COMPLETED with Full #764, Fast #89 and Docs #435 on implementation SHA `dcaef72fc7acd3c2079c48e4dbfb111dd377f24a` and Docs #436 on acceptance SHA `51bd252c2b86a7581a8be6a7cd983fd9dcd95ba9`. No new production/operational acceptance is inferred. Exact registration commit message: `docs(topology): register physical graph authoring delivery`. MODIFY only this phase task record and HAPI-588 planning cells in `ULTIMATE_ROADMAP.md`/`ULTIMATE_PHASES.md`: 3 MODIFY, 0 CREATE/DELETE. Implementation remains NOT_IMPLEMENTED; planning becomes REGISTERED_PENDING. Preserve the former six proposals as historical, superseded *only for future selected execution* by the exact package scopes below.

**Owner architecture and accepted design constraints.** Topology owns a single logical draft aggregate `(companyPartyId,pipelineSystemId,contextId,draftId)`, with **one shared monotonically increasing version** across metadata and graph operations; no second mutable head. Retain approved option A: one immutable context per company/network pair, multiple candidates and versions, no transfer. Model only authored network *sketches*, not qualified `TopologyPhysicalNetworkRevision`, installed equipment, commissioning, hydraulic inputs, or publication. Preserve existing metadata V1 byte format and `V20261019_001` migration checksum. Future graph writes append an existing-format metadata-version record (carrying forward unchanged metadata) **and** one new immutable graph snapshot under the *same new version*, in the same protected physical transaction. Existing metadata writes append metadata only. The current graph as of version `v` is the greatest recorded graph snapshot version not exceeding `v`; absence yields `GRAPH_NOT_AUTHORED`. Historical graph bytes and source identities cannot be rewritten; an exact historical read binds its requested draft version and the as-of graph snapshot. No independent writable graph head, no "latest" pointer mutation, no implicit graph reset on metadata edits, no transparent retries of indeterminate commits.

**Typed graph meaning (selected software contract, not current API).** `EngineeringGraphDraft` is a pure Java immutable value model with stable exact text identifiers (bounded UTF-8; no blank/normalization ambiguity): `Node(id,kind,facilityId)` where `kind=JUNCTION|STATION` and `facilityId` is required only for STATION; `Port(id,nodeId,direction)` with `INLET|OUTLET|BIDIRECTIONAL`; `Branch(id,kind,fromPortId,toPortId)` with `PIPE|CONNECTOR`. All supplied element identities are retained exactly. Reject duplicate node/port/branch identity, dangling port/node/branch references, self-connected branch, intra-node branch, incompatible endpoint direction and malformed graphs; distinct branch IDs may connect the same ports to model parallel paths. Serial topology emerges from adjacent branches and ports. Empty/disconnected drafts remain draft-only with typed incompleteness diagnostics; no assumed numeric diameter, roughness, elevation, length, pressure, flow, compressor operating curve or synthetic equipment. A PIPE here is an *authored pipe placeholder*, never evidence of a dimensioned physical pipe or hydraulic qualification. Ports on network stations are authored **network interface placeholders**, not claims of existing `StationInternalGraph` header/unit ports; explicit station-port correspondence and installed-unit completeness remain HAPI-600/other owner scope. Do not reuse the immutable physical revision V1/V2 codec.

**Station owner/wrong-kind witness.** Every referenced station `facilityId` must resolve to a *current, actual* `public.hidra_topology_facility` row with `owner_party_id` equal to the engineering context company, `status=ACTIVE`, and one of `STATION`, `COMPRESSOR_STATION`, `PUMP_STATION`, `METERING_STATION` (`FacilityKind` source enum). Reject terminal/depot/processing/other kinds, absent/inactive/wrong-owner references before storing a graph or disclosing protected station existence. Observe `hidra_topology_facility_owner_generation` under the actual transaction; no caller-supplied owner witness. Current source does **not** establish a separate authoritative facility-to-pipeline membership table, so graph authoring can declare a facility's inclusion in a network draft but cannot claim externally verified physical membership; do not invent one. Existing facility owner generation remains immutable by this use case. Historical graph bytes remain reproducible for authorized history/audit, while operational reads of formerly owned stations must enforce current scope and may deny rather than disclose newly foreign facilities.

**Protected execution.** Use existing native `TopologyProtectedEngineeringDraftAuthorizationContract.Action.UPDATE` for graph PUT and `READ` for graph GET; distinct company and logical-network CUSTOM claims, DENY/temporal/revocation behavior, genuine local/OIDC/LDAP/AD evidence and actor binding continue. Do not invent a new permission or adapt Facility authority. Keep the **same** `EngineeringNetworkDraftApplicationService` as the graph-write aggregate/application owner; implement the new graph use case there. Extend the existing guarded `ProtectedEngineeringDraftAdmissionCoordinator`, `JdbcEngineeringNetworkDraftRepositoryAdapter` and `ProtectedTransactionExecutor` with one narrowly reviewed graph operation/SQL family. For graph requests, original Identity24 + Party2 + Topology5 fences remain, plus the actual facility master/facility-owner-generation relation fences and the new graph snapshot table fence: exact reviewed total **34 distinct relations** where applicable (31+2+1), ordered and pinned before any common source sample. The original 31-fence metadata path is unchanged. No per-station ad hoc private transaction, late lock, pre-authorization row read or provisional arbitrary SQL. The only provisional graph-write SQL family is the existing reviewed version INSERT and one new parameterized graph-snapshot INSERT, with **their exact UTF-8 SHA-256 templates independently pinned**, not a widened wildcard. Final single materialized guard must verify all 34 held fences, real principal/backend PID/XID/time, current company+network+station eligibility/generations, version CAS, graph format/digest, same-bound metadata version and graph snapshot before commit. Partial failure poisons/rolls back the complete physical transaction; unknown commit outcome is INDETERMINATE, not automatic retry. The existing caller allowlist and station/facility regression contracts remain fail-closed. This is a specification; B must substantiate the exact SQL, fence modes, function trigger catalog and PostgreSQL role privileges under fresh schema before implementation acceptance.

**New migration, not an existing file.** `src/main/resources/db/migration/V20261019_002__p25_topology_engineering_graph_snapshots.sql` is reserved against the inspected 155-migration inventory ending at `V20261019_001` at this registration SHA; refresh the live inventory before B executes. Add one Topology-owned append-only `hidra_topology_engineering_graph_snapshot` table with composite FK `(draft_id,version)` referencing `hidra_topology_engineering_draft_version`, composite PK, fixed payload-format value `HIDRA_ENGINEERING_GRAPH_DRAFT_V1`, canonical bounded byte payload and database-validated SHA-256. Use immutable UPDATE/DELETE/TRUNCATE triggers with verified physical catalog and least-privilege owner/publisher restrictions. Graph INSERT is only admitted with the matched metadata version in the same transaction, with the existing draft-row monotonic-version/CAS guard enforced. No drop/rewrite of historical Flyway objects or extra grant to ordinary application actors. PostgreSQL is the only database qualifying this contract; MySQL interoperability is unverified.

**API contract to implement in B.** Extend the existing exact controller at `/api/v1/topology/engineering-networks/{draftId}` with `PUT /graph?companyPartyId=...&pipelineSystemId=...`, strict graph request `{expectedVersion,nodes,ports,branches}`, one atomic graph+metadata version increment and 200 response with `draftId,version,graphVersion,graphSha256,diagnostics`; and `GET /graph?companyPartyId=...&pipelineSystemId=...&version=...` where `version` is optional/current by default, read-only 200 with version, exact as-of graph data and diagnostics. No graph write without expectedVersion. Request/response DTOs must reject unknown fields and inappropriate scalar values; no persistence JPA entity or auth ticket exposed. 400 malformed/dangling, protected 403/404 non-disclosure policy, 409 stale CAS, 503 unavailable and indeterminate outcome semantics follow accepted draft contract. Real signed bearer + exact compiled method/route mapping in the interceptor must still reach genuine native owner authority for both new operations; do not generalize the prior route bypass. Incomplete graph is not a public effective revision or publishable result.

**Package A — immutable domain/codec contract; one future stage.** Exact commit `feat(topology): define engineering graph draft contract`. CREATE exactly:
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringGraphDraft.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringGraphDraftCodec.java`
- `src/test/java/dz/sh/hidra/modules/topology/domain/model/EngineeringGraphDraftTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringGraphDraftCodecTest.java`
MODIFY only `.github/ci-test-inventory.json`, `doc/roadmap/TASKS_HPS-004.md`, `doc/roadmap/ULTIMATE_ROADMAP.md`, `doc/roadmap/ULTIMATE_PHASES.md`. A must have pure-Java construction/copy invariants, serial+parallel positive tests, empty/disconnected diagnostics, all duplicate/dangling/wrong-direction negatives, deterministic bounded canonical UTF-8 graph encoding, strict decode/sha/truncation/trailing-byte/Unicode tests, no generated industrial values and architecture boundary tests. A changes task status to PARTIAL/REGISTERED_PENDING only after real code/test commit; no REST/storage/authorization capability is claimed. Source-hash and lane inventory must contain actual cases. Applicable exact-SHA Fast/Full/Docs must be observed started, then STOP.

**Package B — one aggregate's protected persistence/HTTP graph authoring; only after A CI acceptance.** Exact commit `feat(topology): persist protected engineering graph draft versions`. CREATE exactly:
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/EngineeringGraphDraftUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringGraphDraftRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringGraphStationWitnessPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/EngineeringGraphDraftRequests.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/EngineeringGraphDraftResponses.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringGraphDraftRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringGraphStationWitnessAdapter.java`
- `src/main/resources/db/migration/V20261019_002__p25_topology_engineering_graph_snapshots.sql`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/EngineeringGraphDraftApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/api/rest/EngineeringGraphDraftControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/EngineeringGraphDraftPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/EngineeringGraphDraftBoundaryGuardrailTest.java`
MODIFY exactly:
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringNetworkDraft.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/EngineeringNetworkDraftUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringNetworkDraftRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringNetworkDraftApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/ProtectedEngineeringDraftAdmissionCoordinator.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringNetworkDraftController.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringNetworkDraftRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java`
- `src/main/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptor.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/EngineeringNetworkDraftApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/api/rest/EngineeringNetworkDraftControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/EngineeringNetworkDraftPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/EngineeringNetworkDraftBoundaryGuardrailTest.java`
- `src/test/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutorTest.java`
- `src/test/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptorTest.java`
- `.github/ci-test-inventory.json`
- `.github/database-dictionary-ownership.json`
- `doc/roadmap/TASKS_HPS-004.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/roadmap/ULTIMATE_PHASES.md`
No other files may be changed absent separately recorded owner-authorized repair. **Mandatory B tests:** signed-bearer graph PUT/GET, native READ/UPDATE and DENY/revocation/mapping failures, station wrong-owner/kind/status and generation ABA, serial/parallel graph roundtrip, wrong directions/dangling/duplicate, exact metadata-graph interleaving and history after metadata update, two concurrent same-version writers at most one commit, unknown commit no retry, actual catalog/privilege/trigger tampering rejection, writer-first/owner-first races, one physical time/PID/XID, 34-fence guard, rollback on past deadline/foreign station, unchanged accepted station and draft cases. No skipped required PostgreSQL cases; no fake SCADA or hydraulic qualification. B can publish CI-first per AGENTS11.1, but its source commit is not accepted if canonical generated OpenAPI and migrated physical dictionary artifacts lag; preserve the last truthful versions and mark them pending until C.

**Package C — generated evidence reconciliation; only after B's exact-SHA runtime artifacts exist.** Exact commit `docs(topology): reconcile engineering graph authoring artifacts`. MODIFY only `doc/api/openapi.yaml`, `.github/documentation-validation.json`, `doc/api/OPENAPI_GOVERNANCE.md`, `doc/database/DATA_DICTIONARY.md`, `doc/roadmap/TASKS_HPS-004.md`. No fabricated source/catalog bytes; regenerate strictly from hosted B runtime OpenAPI and migrated PostgreSQL catalog, preserve old paths/schemas, run strict snapshot and dictionary equality against actual artifacts, record exact source SHA/run/job/attempt/artifact/digest counts and all test case identities. Start same-SHA applicable CI and STOP. After C Full/Fast/Docs evidence **actually passes**, a separate registered documentation-only acceptance step `docs(topology): record engineering graph authoring acceptance` may mark HAPI-588 VERIFIED/COMPLETED and set phase/task statuses accordingly in `doc/roadmap/TASKS_HPS-004.md`, `doc/roadmap/ULTIMATE_ROADMAP.md`, `doc/roadmap/ULTIMATE_PHASES.md`; no HAPI-589/590/600 implementation or deployment is automatically selected.

**Registration disposition and explicit next:** these three bounded packages are the executable scopes superseding only the earlier six speculative source filenames. Existing code, migrations, public APIs, CI/source hashes and historical registry entries are unchanged by this documentation registration. HAPI-588 remains **NOT_IMPLEMENTED/REGISTERED_PENDING** until A begins; no claim of approved graph API, physical-network qualification, steady/transient model or operational acceptance. The **next single stage is HAPI-588A**, not B/C or any downstream phase, after the registration's exact-head Docs result. Refresh live HEAD/migrations/tests before implementing A; stop on source conflict or failed Docs.

### HAPI-588 Package A delivery — 2026-10-19; Java21 CI qualification pending

**One-task sequencing:** this is implementation **Package A of HAPI-588 / HPR-P25-052B**, not a new roadmap ID. The owner explicitly authorizes three packages under one HAPI-588 task. B/C remain registered but unselected; HAPI-587 remains VERIFIED/COMPLETED, and HAPI-589/HAPI-590/HAPI-600 are not selected. Registered exact message: `feat(topology): define engineering graph draft contract`.

**Exact bounded publication:** CREATE only:
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringGraphDraft.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringGraphDraftCodec.java`
- `src/test/java/dz/sh/hidra/modules/topology/domain/model/EngineeringGraphDraftTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringGraphDraftCodecTest.java`

MODIFY only `.github/ci-test-inventory.json`, this task record, `doc/roadmap/ULTIMATE_ROADMAP.md`, and `doc/roadmap/ULTIMATE_PHASES.md`. No DELETE, historical migration rewrite, REST/API schema change, protected-operation admission change or graph publication.

**Implemented bounded source:** pure Java immutable node `JUNCTION/STATION`, station facility selector (without owner claim), port `INLET/OUTLET/BIDIRECTIONAL` and branch `PIPE/CONNECTOR` records; stable exact NFC identities bounded to 80 UTF-8 bytes; distinct element namespaces, deterministic sorted defensive copies, maximum counts, no null/duplicate/dangling/self/intra-node/incorrect-direction links. Parallel distinct branch IDs and serial links are permitted. Empty and disconnected sketches remain draft-only and expose `GRAPH_NOT_AUTHORED`, `GRAPH_DISCONNECTED`, `PIPE_NOT_AUTHORED` and `PHYSICAL_VALUES_NOT_SUPPLIED` diagnostics as applicable. No numeric engineering field, station permission, ownership transfer, physical completeness or qualified simulation claim is invented.

**Codec contract:** versioned `HIDRA_ENGINEERING_GRAPH_DRAFT_V1`, strict standard UTF-8 with bounded signed-big-endian lengths/counts, 1,048,576-byte maximum, stable sorted canonical payload, no repaired/normalized source text, rejection of invalid UTF-8/enums/counts/unknown format/noncanonical order/trailing or truncated bytes, digest via SHA-256. Historical `TopologyPhysicalNetworkRevisionCodec` V1/V2 and accepted HAPI-587 metadata V1 remain untouched. A separately packed independent fixture checks 169 exact bytes and digest `6e7fd1dde5a571f5526349c3842fc27afe2cf59ccf7b65ef8726c36f0f338f4c`; empty payload fixture checks digest `71d579c3e03468bbfc8a487649148a871ea56f43d55ede951de28a300fde34db`.

**Tests and source inventory:** 14 domain cases registered with SHA-256 `51ba5347c16509cdccc89a1dc0e483c3af9e54cb1c3977e29444e9e5370a7cfb`; 12 codec cases with SHA-256 `3d34550ae94aaa6face84ca49de02369050d2cd217ca20d646dba0f9415eae79`; original 423 inventory classes preserved and 2 added to existing `unit-architecture` / Fast lane with unmeasured baseline. Domain source import-boundary assertion included. The original complete test case and artifact inventory remain untouched.

**Evidence limitation and gate:** GitHub Java sources and JUnit tests are prepared for hosted CI; neither an actual Maven compile nor executed JUnit/database result is asserted at registration. No PostgreSQL table, API route or protected integration exists from Package A alone. On guarded direct-main publication, confirm exact parent/tree/8-path scope and exact-SHA Full/Fast/Docs created or started, then STOP without waiting. If CI fails, repair A before B. Only after its required CI acceptance may owner Next select B. HAPI-588 is now **PARTIAL/REGISTERED_PENDING**, never VERIFIED/COMPLETED from A alone.

### HAPI-588 Package A CI #765 focused compilation repair — 2026-10-19

**Owner continuation/registered scope:** This is a repair of the existing HAPI-588A Package A, not a fourth package or a new roadmap task. Same parent task HPR-P25-052B. Exact repair commit message: `test(topology): repair engineering graph draft codec test compilation`. MODIFY only `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringGraphDraftCodecTest.java`, `.github/ci-test-inventory.json`, and `doc/roadmap/TASKS_HPS-004.md`; zero CREATE or DELETE. All earlier entries/history/statuses remain unchanged.

**Verified root cause:** implementation commit `acb363796d93cfd984b9135e5d14453a5ea7c465` has Full CI #765 (run `39317875981`) FAILURE and Fast #90 (run `39317875973`) FAILURE, while Documentation #439 (run `39317875970`) SUCCESS. Maven `testCompile` reports `EngineeringGraphDraftCodecTest.java:102:70 local variables referenced from a lambda expression must be final or effectively final`: in `rejectsCorruptedHeaderAndUnknownFormat`, the first lambda captured `x`, which the same method then reassigned for the second corruption case. All Java-bearing lanes fail to compile for this identical reason, not independent PostgreSQL regressions.

**Minimal test repair:** Give the two independently malformed byte arrays distinct un-reassigned local names `corruptFormat` and `corruptLength`; retain both `IllegalArgumentException` assertions, their exact corruptions, all other original test cases and constants. Do not change graph/domain/codec runtime behavior. Pin the updated test's true UTF-8 SHA-256 `c7d9c21a6f2d91b33c7237abfae7b192201f2743f515d59d6dc54d0cf563c27d` in the retained `.github/ci-test-inventory.json`, replacing exactly one prior hash and preserving all other bytes, lanes, case identities, timing markers and class inventory. No generated schema, OpenAPI, test skip, privilege or transaction behavior is changed.

**Evidence and acceptance boundary:** Live main HEAD, parent and tree, exact-SHA failed jobs and actual Java compiler diagnostic checked before repair. The candidate source has a static compile-safety check that neither lambda captures a reassigned local and the 12 test case declarations are unchanged; `sha256` independently recalculated and source-inventory baseline verified. Actual Java21 Maven/JUnit/PostgreSQL execution of the repaired candidate is not claimed before publishing under AGENTS 11.1. Keep HAPI-588 PARTIAL/REGISTERED_PENDING; Package B remains unselected pending successful exact-repair-SHA Full/Fast/Docs CI, Package C remains later and HAPI-587 remains VERIFIED/COMPLETED. After a guarded direct-main repair commit, confirm new CI created/started on exact SHA and STOP.

<a id="hapi-590"></a>
## HAPI-590 — Public governed revision publication and history

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HPR-P25-052D

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-567, HAPI-588, HAPI-589

Cross-Repository Dependencies: HWEB-F02, HWEB-F03 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Public protected publish/exact read/current/history/effective/list lifecycle over051.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

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

Deliver company/network drafts, physical graph editing and protected publication/history APIs after owner-fence acceptance. The exact bounded remaining delivery is: Public protected publish/exact read/current/history/effective/list lifecycle over051..

### 4. Functional Requirements

Expected predecessor and owner/head epoch, one CAS winner, identical/conflicting replay, withdrawal/reactivation/transfer-hold semantics, exact old formats and history cursors; denied paths reveal no foreign metadata.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringRevisionPublication.java` — pure Java owned aggregate/value and invariants for publish/withdraw/history using accepted paired authorization and predecessor/owner/head CAS.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/EngineeringRevisionPublicationUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringRevisionPublicationRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringRevisionPublicationApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringRevisionPublicationController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringRevisionPublicationRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

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

**PROPOSED, not existing:** `POST /api/v1/topology/engineering-networks/{id}/publications` for publish/withdraw/history using accepted paired authorization and predecessor/owner/head CAS. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

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

- Expected predecessor and owner/head epoch, one CAS winner, identical/conflicting replay, withdrawal/reactivation/transfer-hold semantics, exact old formats and history cursors; denied paths reveal no foreign metadata.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-567, HAPI-588, HAPI-589. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23359](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23359)
- [doc/model-remediation/RECONCILIATION.md:23737](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23737)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1045](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1045)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1297](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1297)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1045 | HPR-P25-052D | BLOCKED_SPECIFICATION | Public governed revision publication and history
doc/roadmap/ULTIMATE_ROADMAP.md:1297 | HPR-P25-052D | Public protected publish/exact read/current/history/effective/list lifecycle over051. | Expected predecessor and owner/head epoch, one CAS winner, identical/conflicting replay, withdrawal/reactivation/transfer-hold semantics, exact old formats and history cursors; denied paths reveal no foreign metadata.
doc/model-remediation/RECONCILIATION.md:23359 | HPR-P25-052D | Public protected publish/exact read/current/history/effective/list lifecycle over051. | Expected predecessor and owner/head epoch, one CAS winner, identical/conflicting replay, withdrawal/reactivation/transfer-hold semantics, exact old formats and history cursors; denied paths reveal no foreign metadata.
doc/model-remediation/RECONCILIATION.md:23737 | HPR-P25-052D | BLOCKED_SPECIFICATION | Public governed revision publication and history
~~~~~~~~

### 2026-10-20 — HAPI-590 source-backed public publication contract review

**Selected stage and checkpoint.** Owner Next selects the next sequential
engineering task's specification review after HAPI-046's existing-run evidence
stage. Main `df10b03ece7cfc53344ee0e3f7e89fab33abfec5`, parent
`96bbd74508e06f96476616764e420c866313a6bb`, tree
`e0502406b85b377c1c6fd2aefe5993e85fe340ce`, passed
[Documentation466](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39456266004).
HAPI-567, HAPI-588 and HAPI-589 are VERIFIED/COMPLETED in the live registry.
HAPI-046 stays PARTIAL/IN_PROGRESS pending comparable natural-run evidence;
this review does not dispatch benchmarks or close HAPI-436. The accepted
runtime baseline remains Full784/Fast106, with no executable changes in the
subsequent two governance commits. HidraAPI remains development-only.

Exact review message: `docs(topology): review governed publication contract`.
MODIFY this phase record and only the stale Current execution pointer in
ULTIMATE_TASKS.md; zero CREATE/DELETE. Frozen task/status tables, all previous
evidence and every runtime/test/schema/workflow/API/catalog blob are preserved.
This is a concrete contract review, not implementation of the six proposed
filenames in section6.

**Recovered source and actual boundaries.**

| Existing owner source | Proven contract relevant to HAPI-590 |
|---|---|
| EngineeringNetworkDraft and its use case/repository | Immutable company/network context and draft IDs; append-only shared positive metadata versions; CREATE/READ/UPDATE/VALIDATE. No publication head or effective ledger. |
| EngineeringGraphDraft and graph repository | Authored JUNCTION/STATION nodes, interface ports and PIPE/CONNECTOR branches. PIPE is explicitly a placeholder. Diagnostics retain PHYSICAL_VALUES_NOT_SUPPLIED even for connected graphs. |
| SurveyRouteProfileUseCase and accepted survey repository | Exact per-branch canonical survey snapshots tied to graph version/digest and shared draft version. Omitted survey stays absent; no projection, datum conversion, inferred physical length, diameter or roughness. |
| EngineeringNetworkDraftApplicationService / ProtectedEngineeringDraftAdmissionCoordinator | One guarded transaction, native company/network authority, Party and station witnesses, immutable metadata/graph/survey binding and final physical guard. Existing private admission is not a caller-created publication permit. |
| TopologyProtectedEngineeringDraftAuthorizationContract | Only CREATE/READ/UPDATE/VALIDATE permission actions. It does not supply PUBLISH/WITHDRAW/REACTIVATE or authorize those from UPDATE. |
| ProtectedStationGraphOperationUseCase and accepted051 implementation | Internal facility-scoped station revision lifecycle, expected epoch/predecessor and physical commit semantics. Its facility identity and station payload cannot be relabeled as a company/network draft publication. |
| StationInternalGraphRevision | STRUCTURALLY_QUALIFIED requires COMPLETE station graph plus evidence; source classification remains independently declared. |
| TopologyPhysicalNetworkRevision | A different physical-source format requiring connected nodes, node elevations, real pipe lengths/internal diameters/roughness, recorded/effective time and evidence. Draft graph/survey does not contain all those values. |

The current migration tail is V20261020_001 survey snapshots. The existing
draft context is unique per company/network pair and may contain multiple draft
candidates; context bindings are immutable under the owner's HAPI-587 option A.
That existing cardinality/no-transfer choice is accepted and will not be asked
again. It is distinct from the publication-readiness question below.

**Concrete product/engineering gate that source cannot decide.** HAPI-590's
original objective says public governed publication/effective history, while
its accepted inputs are deliberately unqualified drafts. Silently converting
those inputs into TopologyPhysicalNetworkRevision would require inventing
physical properties, bypassing its constructor or changing accepted semantics.
Conversely, silently treating every incomplete draft as an operationally
effective physical revision would overstate engineering authority. The choice
changes what a public PUBLISH success means and therefore needs an explicit
owner policy selection before runtime registration.

| Owner choice | Public publication meaning and consequence |
|---|---|
| A — recommended for the current development API | Publish an immutable governed snapshot of explicitly authored metadata/graph/survey. Preserve original bytes, missing values and completeness diagnostics; expose engineeringQualified=false and no operational/hydraulic approval. Effective selection means effective within this governed snapshot ledger only. It never writes facility ownership, station publication heads, live network configuration or an engine input approval. |
| B — engineering-complete physical publication only | A PUBLISH command must fail closed until a separately registered, complete physical-input/qualification contract is satisfied. Current draft/survey success alone is insufficient. Register the missing physical authoring/qualification dependency under its existing owner task before a positive publication path; do not fabricate values or weaken physical revision invariants. |

A would unblock a useful development publication/history API without claiming
physical qualification. B preserves a stricter meaning but the current accepted
draft types cannot provide its positive publication evidence. This review
selects neither. The earlier approved database-credential A and company-context
A do not answer this different readiness policy.

**Prepared common lifecycle contract, pending that decision.**

1. Topology owns a new context-scoped publication head and immutable ledger.
   Bind every published snapshot to exact context/company/network/draft and
   shared metadata version, selected graph version/digest and each included
   survey's graph/version/digest. Multiple candidate drafts compete for the
   same company/network context head; do not create independent effective heads
   merely because draft IDs differ. No ownership transfer or context rebinding
   is introduced; authoritative owner mismatch denies/holds the operation.
2. Define separate typed PUBLISH, WITHDRAW, REACTIVATE, READ_EXACT,
   READ_CURRENT, READ_EFFECTIVE, LIST_HISTORY and replay semantics. A new
   publication requires current exact source version, expected context head
   epoch/predecessor and matching snapshot digest. Exactly one CAS wins;
   epochs are positive, monotonic and overflow-safe. Withdrawal preserves
   bytes/history, reactivation targets the same retained revision with fresh
   authority. An identical request replay requires fresh current authority and
   returns the original immutable outcome; a conflicting replay returns409.
   Unknown commit acknowledgement stays INDETERMINATE, without automatic retry.
3. Separate publication epoch from mutable draft version and Facility owner
   generation. Do not coerce the context UUID/network scope into a facility
   identity to call the station service. Preserve the existing station and
   physical-source stores and their historical codecs. A new publication
   envelope references exact source formats/digests and retains their bytes;
   future formats require explicit version dispatch and compatibility proof.
4. Identity must export a reviewed company/network publication authorization
   contract through its public application seam. Propose dedicated
   topology:engineering-publication action permissions, DENY precedence and
   the existing genuine native producer/binding path; draft UPDATE and station
   PUBLISH are not substitutes. All exact/current/effective/history/list/replay
   reads authorize before existence, count, digest or cursor disclosure.
   Current company/network and referenced-station owner witnesses remain live;
   historical author provenance is not current read authority.
5. Preserve the private-publisher credential boundary, actual physical
   connection/EntityManager/transaction, fixed owner lock order and required
   fence census. Resolve exact source snapshots and provisional event/head
   mutations before one final scalar guard; bind current native actor/owner
   generations, s<=t<u, PID/XID/session role, source bytes/digests, expected
   head/event and required locks. No SQL/owner lookup follows it. Preserve the
   existing500ms/lock/statement/idle limits and dirty-flush poisoning; do not
   stretch timeouts, add callbacks or use caller scalars/GUCs as authority.
6. Exact revision reads must be independent of history page limits. A bounded
   deterministic keyset cursor binds context, filter/as-of and stable ordering;
   reject malformed or cross-context cursor substitution without disclosure.
   READ_CURRENT and READ_EFFECTIVE are different queries. Before code, specify
   database timestamp precision, half-open effective intervals, overlap rules,
   the effect of withdrawal/reactivation on effective selection and treatment
   of future scheduling. Existing physical-source effectiveFrom/effectiveUntil
   values do not silently schedule a new governed publication.
7. Proposed REST surface remains under the existing engineering-networks
   resource, with explicit context/company/network scope and transport-owned
   request/response types. The existing proposed POST publications path is
   not yet a registered endpoint set. Request bodies never supply actors,
   permission decisions, clock samples or owner generations. Define400/401/
   non-disclosing403/404/409 and unavailable/INDETERMINATE errors, strict
   unknown-field/number binding and bounded history; no entity serialization.

**Exact registration required after the owner choice.** Finish a single
decision-dependent lifecycle contract and finite implementation envelope under
HAPI-590. Close source/repository/codec and transport type filenames, the
Identity action/catalog/route/bootstrap and Platform compiled-caller/provisional
seams, complete owner fences, actual schema privileges and separate mutable-head/
immutable-event/snapshot storage, effective-time and cursor contracts. Recover
the latest migration tail again before reserving an additive migration; no
migration filename is reserved by this review. The six existing proposed
EngineeringRevisionPublication filenames remain proposals; reuse equivalent
types rather than duplicating accepted stores.

The envelope must name invariant/service/HTTP/architecture and genuine
PostgreSQL classes/cases, inventory/source hashes, generated OpenAPI manifest/
compatibility and actual migrated dictionary reconciliation paths together.
Preserve all historical migrations and test identities. Avoid publishing partial
routing without its authorization, schema and final guard. If work needs packages,
register their complete union and dependency order once, not repeated generic
preflights. Do not add a new canonical task or assume HWEB acceptance.

**Required verification prepared for that registration.** Prove actual native
LOCAL/OIDC/LDAP/AD owner-authorized publication and exact/current/effective/
history reads, substituted context/company/network/draft denial before lookup,
permission revocation and current DENY, stale source/graph/survey binding,
owner-generation ABA, malformed/old-format/digest rejection, two-writer CAS,
identical/conflicting replay, withdrawal/reactivation, effective interval
boundaries and keyset-page continuity. Include exact historical reads beyond
the newest100 ledger events, direct ordinary-login event/head mutation denial
with constraint-valid controls, publisher role/catalog tamper refusal, writer-
first/owner-first fences, final-guard expiry/dirty-flush rollback and uncertain
commit classification without retry. The chosen readiness policy must have
explicit positive and negative tests; missing authored/qualified data must never
be synthesized to obtain success. Hosted Full/Fast/Docs, actual generated API
and fresh migrated PostgreSQL dictionary remain exact-source acceptance gates.

**Disposition and concrete next action.** HAPI-590 remains NOT_IMPLEMENTED /
BLOCKED, now specifically BLOCKED_OWNER_POLICY within this record; all frozen
status cells and completed prerequisites remain unchanged. The owner should
choose publication readiness A or B above. Once selected, register the one
coherent executable envelope rather than ask again about already approved
context cardinality, publisher credentials or deployment calibration.
HAPI-046/436 remain independently open; no timing rerun is selected.

Available checks are in-memory source/path/migration-tail recovery, original
task-preservation and unchanged-status/anchor/scope checks. The current execution
environment is unavailable: no local Python, Java/Maven, PostgreSQL, full
canonical-estate validation or new artifact generation is claimed. Hosted Docs
validates the two-file review. Publish main with expected-head guard and verified
blobs/tree/parent/message, confirm exact-SHA Docs creation/start and STOP.
No runtime publication, operational approval, release, deployment or simulation
is selected.


### 2026-10-20 — HAPI-590 owner option A and complete staged executable registration

**Owner decision and current stage.** The owner explicitly answered A to the
publication-readiness choice: publish an immutable governed snapshot of the
authored metadata/graph/survey, preserve missing values and diagnostics, and
always identify it as engineering-unqualified. This resolves that specific
HAPI-590 policy blocker. It does not select operational/hydraulic approval,
physical-property inference, publication option B, network/context ownership
transfer, station-head mutation, simulation or production deployment.

Main `9376dcfef82bf4645f26fa9e04448378ce41ea75`, parent
`9d57ce16caac60dad48cf4e1199c26ac0b88ea54`, tree
`2626138842ddee929bc0e2dbc332448387f323eb`, passed
[Documentation468](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39461329802).
HAPI-567/588/589 and the separately accepted CI043..046/436 scopes remain
complete. Full785/run39459787957 passed on44c2f90, with430 classes/2606 tests,
zero failed/errored/skipped required cases, actual cache/API/catalog evidence
and the same unchanged executable source; no standalone Full on9376dcf is
invented. HAPI-046 timing acceptance is not reopened by this registration.

This selected publication is registration only, message
`docs(topology): register governed publication delivery`. Exact current writes:
this phase record, HAPI-590's planning cells in ULTIMATE_ROADMAP.md and
ULTIMATE_PHASES.md, and its navigation paragraph in ULTIMATE_TASKS.md.
Implementation remains NOT_IMPLEMENTED; planning becomes REGISTERED_PENDING.
All previous payload/status history is preserved. The six old source proposals
are superseded only for future selected execution by the finite scopes below.

**1. Exact snapshot and scope semantics.** One existing immutable company/network
context is the publication aggregate key. Its multiple draft candidates share
one publication head; candidate draft IDs do not create separate effective
heads. Context/company/network identities must match authoritative existing
rows after current paired authorization. No company rebind or transfer command
is exposed; previous HAPI-587 cardinality/credential decisions are retained.

A snapshot pins an exact positive shared metadata version, its original format/
canonical bytes/digest, the graph selected as-of that version (or explicit
absence), and each applicable per-PIPE survey selected as-of that version.
Use the existing codecs to verify retained bytes and digests; never regenerate
source values from lossy DTOs or overwrite source records. Components carry
their original version/format/digest, with survey graph-version/digest binding.
A stale survey bound to another graph is not silently attached: retain explicit
SURVEY_GRAPH_STALE/absence diagnostics rather than pretending compatibility.
No graph, elevations, diameter, roughness, evidence or survey is synthesized.

Publishing an incomplete or empty authored draft is permitted under A, provided
its authoritative metadata version exists and its encoded components are valid.
The publication retains deterministic diagnostics, including
PHYSICAL_QUALIFICATION_NOT_ESTABLISHED, and engineeringQualified=false.
This field is server-defined and constrained false in the stored publication;
no request may claim qualification. A published snapshot is not a
TopologyPhysicalNetworkRevision or StationInternalGraphRevision and cannot
change their stricter constructors or formats.

New envelope format is HIDRA_ENGINEERING_PUBLICATION_V1, deterministic and
bounded to32MiB in total; reject an oversized complete envelope explicitly,
never truncate components or drop them to fit. Canonical component bytes may
be represented as immutable canonical Base64 strings in domain/result records;
the codec validates decoded bounds/digests and round-trip identity. Do not
return mutable byte arrays, lazy entities or open JDBC results. Preserve source
decimal scales and diagnostic ordering. Unknown envelope/source formats fail
closed; future versions require explicit codec dispatch and replay proof.
Snapshot construction uses bounded set-based reads, not one query per point
or unbounded loading of all historical versions.

**2. Lifecycle, replay and effective-time rules.** PUBLISH supplies requestId,
exact draftId/version, expected context-head epoch and predecessor revision ID.
Epoch0/null predecessor denotes no head; all existing-head operations require
its exact positive epoch/revision. New publication revision/event IDs are
server-generated UUIDs. PUBLISH appends a new immutable snapshot/event and
advances the context head ACTIVE by exactly one epoch; a new candidate may
replace either ACTIVE or WITHDRAWN head only with matching expected inputs.
WITHDRAW requires ACTIVE and preserves its exact revision; REACTIVATE requires
WITHDRAWN and the same retained revision. Epoch overflow, stale predecessor,
mismatched state, absent source or conflicting replay returns409 without effects.

Every mutating event records requestId and a canonical command digest. Uniqueness
is (context_id,actor_user_id,request_id), permitting fresh native authentication
for the same current actor. The digest includes the action, exact scope/source,
expected epoch/predecessor and other typed command fields; exclude server-made
IDs and fresh sampled clock/binding values. A repeated identical request,
after fresh current authority and current owner eligibility, returns its original
immutable outcome with replayed=true and no mutation even if later events have
advanced the head. Changed inputs under that same key return409. A replay does
not claim to be the current head. Final guard binds the actual observed current
head and retained replay event separately. No automatic retry after uncertain
COMMIT; an explicit subsequent authorized request may resolve its receipt.

Effective time is immediate within this governed software ledger only.
Set effective_at to the one authoritative PostgreSQL authorization sampled_at,
with PostgreSQL microsecond precision. No client effectiveFrom/effectiveUntil,
backdating, future scheduling or override clock fields are accepted. Require
sampled_at>=the prior context event time; a backward clock invalidates the
attempt rather than inventing a time. Equal microsecond times are ordered by
the strictly increasing epoch. READ_EFFECTIVE(asOf) selects the latest committed
event at/before asOf by effective_at DESC,epoch DESC: PUBLISH/REACTIVATE resolves
that snapshot; WITHDRAW resolves no effective snapshot. Transitions define
half-open intervals [event time,next event time), with the highest epoch
winning at equal instants. Withdraw/reactivate never erase historical intervals.
Reject asOf later than the current authorized database sample; exact ISO-8601
UTC transport parsing and precision rules are explicit and tested. These are
journal-as-of semantics over committed data, not physical operating intervals,
retroactive engineering approval or a claim that a pre-commit observer saw an
uncommitted publication.

READ_EXACT is independent of history pagination and still requires current
authority/owner evidence. Later draft edits cannot change published bytes,
epoch or history; historical lookup does not require the old draft version to
remain the latest. READ_CURRENT reports the retained head state and includes
its snapshot only when ACTIVE. LIST_HISTORY uses limit1..100 and a deterministic
descending-epoch keyset window. The bounded opaque cursor encodes version,
context/company/network, optional asOf/filter, captured high-water epoch and
strict before-epoch, with canonical encoding and authoritative ledger-boundary
validation. Treat all cursor values as untrusted query data, never authority;
it need not introduce a signing secret or claim cryptographic tamper resistance.
A next page remains in the captured window even if later epochs are added.
Cross-context/filter/time substitution, impossible boundaries, unknown versions
and oversized/noncanonical cursors reject without foreign metadata disclosure.

**3. Actual Identity and technical seams, not assumed authority.** Reuse the
existing TopologyProtectedEngineeringDraftAuthorizationContract and its genuine
participant as the company/network authorization transport. Extend Action with
exact PUBLICATION_PUBLISH, PUBLICATION_WITHDRAW, PUBLICATION_REACTIVATE and
PUBLICATION_READ values. Its permissionCode helper maps only those four to
topology:engineering-publication:publish/withdraw/reactivate/read; the original
four draft mappings stay byte-semantically unchanged. All four publication
query kinds use PUBLICATION_READ; mutations use their distinct publication
action. No draft UPDATE, generic route execute or station PUBLISH grant suffices.

ProtectedOperationIdentityParticipant.qualifyDraftPair currently fixes resource
type ENGINEERING_NETWORK_DRAFT. Add an exact action-to-resource mapping:
publication actions use ENGINEERING_NETWORK_PUBLICATION, old actions retain
ENGINEERING_NETWORK_DRAFT. Keep private issuer/actor/thread/connection/one-use
claim-pair provenance. JdbcProtectedAuthorizationEvidenceReader currently pins
only the four draft permission codes; add exactly the four publication codes
with matching resource type through that genuine private qualified path.
Diagnostic structural requests and fabricated pairs must still fail. No prefix
wildcard, arbitrary permission argument, public pair constructor or generic
Boolean permit is admitted. The existing participant owns the24 Identity fences;
the new Topology coordinator imports only approved public Identity/Party
contracts and Platform mechanics. No concrete foreign business adapter import.

The new compiled service uses a new compiled coordinator and private publisher
route on the same existing guarded DataSource/EntityManager/transaction manager.
Extend ProtectedTransactionExecutor's exact service/coordinator identities only
for this pair, with a separate owner permit and independently pinned finite
publication DML hashes. It must not mint publication permits for the old draft,
station or diagnostic callers. Preserve all old allowed DML texts/hashes and
their existing behavior. JdbcEngineeringGraphStationWitnessAdapter permits
exactly the new publication service in addition to its current compiled service;
it still requires qualified admission and held relation fences. Do not call its
supporting verifyCurrent SQL after the final guard.

HidraRouteAuthorizationInterceptor delegates only the new controller's eight
exact method/HTTP/path tuples below to its mandatory native protected use case;
authenticate first. Existing controller tuples remain unchanged. Seed exactly
the four publication permission definitions in the new migration with resource
ENGINEERING_NETWORK_PUBLICATION, explicit action/domain/status/sensitivity
metadata, and no role/user grants. Define the four sensitivity flags explicitly
using the existing draft-family policy (false); this does not waive paired
authority, current DENY, native provenance or any mandatory protected guard.

**4. One physical transaction and38-lock final proof.** Prepare actual native
authentication outside any transaction. Select publisher credentials before
beginning a new writable READ COMMITTED transaction. Acquire Identity24 and
Party2 owner fences, then the exact existing Topology source order:
pipeline_system, pipeline_system_type, engineering_context, engineering_draft,
engineering_draft_version (all ShareRowExclusive), facility (ShareRowExclusive),
facility_owner_generation (AccessShare), engineering_graph_snapshot and
engineering_survey_snapshot (ShareRowExclusive), then new publication_snapshot,
publication_event,publication_head (ShareRowExclusive), all in public schema
with hidra_topology_ prefixes and NOWAIT. Technical names below are exact new
storage names. No protected context/head/source read precedes successful
current paired Identity company/network authorization. Preauthorization
acquisition may inspect technical catalog/roles/locks only.

This union is38 distinct required relations:34 ShareRowExclusive,
3 AccessShare and1 Share. Identity still owns22+2, Party2, Topology12.
Freeze owner-defined descriptors after validating their actual opaque admissions,
before the single authorization sample. Reject missing/duplicate/conflicting
requirements; unrelated extra locks do not invalidate required-subset proof.
After Identity contribution/common s/u, require active Party and matching live
pipeline system/type/context. Resolve exact source/read target and current
referenced-station owner/kind/status/generation witnesses before disclosure.
A station owner/generation/context mismatch holds the operation fail-closed
with generic conflict/denial, not an automatic transfer, stored relabel or
silent station-head edit. HOLD is an operational refusal here, not an automatic
read-side database mutation or invented transfer workflow.

Allow only bounded provisional publication snapshot INSERT, event INSERT and
head INSERT/CAS UPDATE templates. Required row counts are exact; no draft,
Identity, Party, Facility or survey writes are permitted. Flush managed state
before one final scalar statement with MATERIALIZED clock t and s<=t<u,
PID/XID/session/current principal, current native/Party/network/station evidence,
all38 required granted locks, exact frozen source/published bytes/digests,
context/head/event/replay identities and intended epoch/state. For historical
reads, verify retained source/version membership, not latest-draft equality.
For PUBLISH verify the requested source version was current and frozen.
Retain500ms aggregate,250ms lock,750ms statement and1000ms idle limits.
Caught failures poison; unexpected dirty JPA DML and post-guard SQL roll back;
no owner lookup, lazy load or SQL follows the one final guard. Return only
materialized immutable data after observed physical COMMIT.

**5. Additive schema and real role checks.** Reserve
V20261020_002__p25_topology_engineering_publications.sql after verified live
tail V20261020_001. Recheck collision before selected B; any intervening migration
requires explicit same-task version reconciliation, never overwrite history.

Create exactly:
- public.hidra_topology_engineering_publication_snapshot: server revision UUID,
  context/draft/shared-version references, explicit envelope format, bounded
  canonical bytes, SHA256 and engineering_qualified=false constraint.
- public.hidra_topology_engineering_publication_event: immutable event UUID,
  context, positive unique context epoch, operation, prior/resulting revision,
  resulting ACTIVE/WITHDRAWN state, actor-scoped request UUID/digest, native
  binding/session/user provenance, Party and referenced-station generation
  evidence as encoded snapshot data, s/u, effective_at, backend PID and xid.
- public.hidra_topology_engineering_publication_head: one context PK, current
  revision/positive epoch/state and last event FK.

Use validated nondeferrable keys/FKs/unique/check constraints to bind snapshots
to actual draft/context/version and events/head to the same context, epoch,
state and revision. Event INSERT validation plus head transition guards must
reject mismatched predecessor, wrong current transaction provenance, illegal
state transitions, epoch reset/overflow and a head pointing at another event/
snapshot. Serialize absent-head creation through the existing context row or
equivalent reviewed lock in addition to the held table fences; do not rely on
a nullable-head SELECT FOR UPDATE to lock a missing row. Order snapshot then
event then head mutation; a replay performs none. Reject every UPDATE/DELETE/
TRUNCATE on snapshot/event (including zero-row statements), head DELETE/TRUNCATE
and invalid head UPDATE. Security-definer trigger functions have fixed safe
search_path, immutable reviewed bodies and no PUBLIC execution.

Ordinary login receives no privileges on the three new publication tables.
Publisher has only SELECT/INSERT/UPDATE there: UPDATE is required for the
selected PostgreSQL SHARE ROW EXCLUSIVE lock and is rejected on immutable
tables by triggers; head UPDATE is the finite guarded CAS. Neither runtime
principal owns tables/functions/schemas or inherits DDL/elevated/server-file/
program capabilities. New admission verifies table/column grants, owner/
function/trigger/constraint/catalog integrity and distinct actual session
principals. Migration uses separate administrator ownership, no hardcoded
deployment roles/passwords or automatic business grants. Tests use actual
separate admin/ordinary/publisher LOGINS and the same disposable migrated DB.
A protects ordinary credentials, not stolen publisher credentials or arbitrary
process code able to read them. Update only the three new table ownership
entries truthfully; actual physical dictionary regeneration waits for hosted
catalog capture, never fabricated source-derived metadata.

**6. Exact public API and transport.** All endpoints are on
/api/v1/topology/engineering-networks/{draftId}/publications and require exact
companyPartyId/pipelineSystemId scope. The existing draft's context determines
the shared head; it is not chosen from an unverified body/context ID. A head
may refer to another candidate draft in that same authorized context; response
provenance identifies its actual source draft/version.

| HTTP and suffix | Exact controller method | Meaning |
|---|---|---|
| POST base | publish | requestId,draftVersion,expectedEpoch,expectedRevisionId; no raw snapshot/actor/time/qualification input |
| POST /withdraw | withdraw | requestId,expectedEpoch,expectedRevisionId |
| POST /reactivate | reactivate | requestId,expectedEpoch,expectedRevisionId |
| GET /revisions/{revisionId} | readExact | Exact retained context revision independent of page size |
| GET /current | readCurrent | Context head state and active snapshot, if any |
| GET /effective | readEffective | Required asOf timestamp, ledger-as-of semantics |
| GET /history | listHistory | limit1..100, optional bounded cursor/asOf |
| GET /requests/{requestId} | readRequest | Currently authorized actor's retained receipt; never cross-actor receipt lookup |

Publication replay uses the mutation's current action authority, not only read.
readRequest uses current PUBLICATION_READ and actual current native user.
All methods authorize before protected lookup/disclosure. Strict transport-owned
records bound UUIDs/versions/strings and unknown/coerced fields; no entities,
callable SQL, raw IdP claims, grant lists, caller clocks/generations or permit
scalars. Responses preserve snapshot format/version/digests and explicit
engineeringQualified=false/diagnostics; no native secrets or physical SQL
details.400 malformed/unsupported/unknown fields,401 missing native auth,
generic403 denied authority, scoped404 absent target/receipt,409 stale inputs/
conflicting replay/held owner mismatch,503 safe storage/fence/time/credential
failure or explicitly classified INDETERMINATE. Never conflate unknown commit
with rejection/success or automatically retry.

**7. Complete finite execution envelope —21 CREATE /23 MODIFY /0 DELETE.**
Paths are repository-root relative. All CREATE absences and MODIFY presences
were verified at9376dcf; the migration version is currently unused. The entire
union is registered now in three dependent packages, not selected runtime code
by this registration. No further generic review stage or repeated A/B question
is selected. Newly discovered material missing dependency/path is reconciled
within this same task before mutation; it is not an unrelated-refactor license.

**CREATE union, ordered by package (A1..9, B10..21):**
1. `src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringRevisionPublication.java`
2. `src/main/java/dz/sh/hidra/modules/topology/application/port/in/EngineeringRevisionPublicationUseCase.java`
3. `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringRevisionPublicationRepositoryPort.java`
4. `src/main/java/dz/sh/hidra/modules/topology/application/model/EngineeringRevisionPublicationFailure.java`
5. `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringRevisionPublicationCodec.java`
6. `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringRevisionPublicationCursorCodec.java`
7. `src/test/java/dz/sh/hidra/modules/topology/domain/model/EngineeringRevisionPublicationTest.java`
8. `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringRevisionPublicationCodecTest.java`
9. `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringRevisionPublicationCursorCodecTest.java`
10. `src/main/resources/db/migration/V20261020_002__p25_topology_engineering_publications.sql`
11. `src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringRevisionPublicationApplicationService.java`
12. `src/main/java/dz/sh/hidra/modules/topology/application/service/ProtectedEngineeringPublicationAdmissionCoordinator.java`
13. `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringRevisionPublicationRepositoryAdapter.java`
14. `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/EngineeringRevisionPublicationRequests.java`
15. `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/EngineeringRevisionPublicationResponses.java`
16. `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringRevisionPublicationController.java`
17. `src/main/java/dz/sh/hidra/modules/topology/api/rest/EngineeringRevisionPublicationApiExceptionHandler.java`
18. `src/test/java/dz/sh/hidra/modules/topology/application/service/EngineeringRevisionPublicationApplicationServiceTest.java`
19. `src/test/java/dz/sh/hidra/modules/topology/api/rest/EngineeringRevisionPublicationControllerTest.java`
20. `src/test/java/dz/sh/hidra/modules/topology/EngineeringRevisionPublicationBoundaryGuardrailTest.java`
21. `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/EngineeringRevisionPublicationPostgresIntegrationTest.java`

**MODIFY union:**
1. `src/main/java/dz/sh/hidra/modules/identity/application/contract/topology/TopologyProtectedEngineeringDraftAuthorizationContract.java`
2. `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedOperationIdentityParticipant.java`
3. `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java`
4. `src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java`
5. `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringGraphStationWitnessAdapter.java`
6. `src/main/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptor.java`
7. `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedOperationIdentityParticipantTest.java`
8. `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReaderTest.java`
9. `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProtectedEngineeringDraftAuthorizationPostgresIntegrationTest.java`
10. `src/test/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutorTest.java`
11. `src/test/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptorTest.java`
12. `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
13. `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java`
14. `.github/database-dictionary-ownership.json`
15. `.github/ci-test-inventory.json`
16. `doc/roadmap/TASKS_HPS-004.md`
17. `doc/roadmap/ULTIMATE_ROADMAP.md`
18. `doc/roadmap/ULTIMATE_PHASES.md`
19. `doc/api/openapi.yaml`
20. `.github/documentation-validation.json`
21. `doc/api/OPENAPI_GOVERNANCE.md`
22. `doc/database/DATA_DICTIONARY.md`
23. `doc/roadmap/ULTIMATE_TASKS.md`

**Package A — pure snapshot contract/codecs.** After this registration's Docs
success, one owner Next selects exact message
`feat(topology): define governed publication snapshot contract`.
CREATE union1..9 only; MODIFY .github/ci-test-inventory.json and this phase record,
plus only HAPI-590's implementation cells in ROADMAP/PHASES
NOT_IMPLEMENTED→PARTIAL (planning stays REGISTERED_PENDING).
No migration, endpoint, Identity permission or runtime publication is delivered
by A. Public ports must compile without referring to not-yet-created B classes;
opaque repository Admission and immutable observation/result records live in
the registered port/domain, not a dependency on B's coordinator. Register the
actual new JVM cases/hashes with unmeasured timing rather than invented seconds.
Domain stays pure Java; codec/cursor infrastructure cannot confer authority.
Confirm same-SHA applicable Full/Fast/Docs startup and stop; repair actual
failures before selecting B.

**Package B — one coherent protected persistence/API integration.** After A's
required CI succeeds, the next owner Next selects
`feat(topology): implement governed engineering publication`.
CREATE union10..21; MODIFY union1..16 only. This includes six production seams,
seven existing regression suites, reviewed ownership, inventory and this phase
record. Preserve all old case identities and original draft/station permission/
SQL behavior. The existing four-action Identity test keeps its original cases;
add publication-specific authority positives/negatives rather than silently
reinterpret old methods as evidence of new permissions. Forensic adds only the
new coordinator's exact approved public Identity import. Admission source guards
add only the exact new service/coordinator/repository call sites and refresh the
reader fingerprint solely for the reviewed eight-code/resource mapping extension;
unchanged I/J owner fingerprints and all existing negative bans stay intact.
Pin new SQL hashes independently; no broad caller/prefix exception. Platform
remains technical and imports no business class.

The new PostgreSQL fixture reuses/copies actual native producer setup patterns
from existing accepted integration tests with real admin/runtime separation;
do not use mock authority, fabricated qualified pairs or SET ROLE on a privileged
session as proof of ordinary credentials. New genuine HTTP bearer tests retain
the actual decoder/security chain. No new generic fixture/base-class path is
authorized. Source/schema/runtime API and catalog collection are allowed even
when strict committed artifact equality first reports the expected new-contract
drift: retain those actual artifacts, then C reconciles them. Such an initial
artifact mismatch is not accepted B success or a waived gate. Existing CI
workflow, five DB sets, inventories' policy/baseline, deadlines, required
historical tests and release machinery are PRESERVE. Record PARTIAL/
REGISTERED_PENDING throughout B.

**Package C — generated artifacts from actual B evidence only.** After B's
actual API/schema artifacts exist, one owner Next selects
`docs(topology): reconcile governed publication artifacts`.
MODIFY only doc/api/openapi.yaml, .github/documentation-validation.json,
doc/api/OPENAPI_GOVERNANCE.md, doc/database/DATA_DICTIONARY.md and this phase
record. Generate canonical API from the actual application and dictionary from
the actual migrated catalog plus reviewed ownership/source inventory. Record
source SHA/run/attempt/job/member/ZIP digests and counts; never infer physical
metadata or weaken equality/compatibility. Confirm exact-C-SHA Full/Fast/Docs
as applicable, then stop. A/B failures are repaired before formal acceptance;
C does not mask genuine Java/database/security failures as artifact drift.

After required exact-source C verification, a separate documentation-only
`docs(topology): record governed publication acceptance` may reconcile the
complete original task plus the owner's A semantics and set VERIFIED/COMPLETED
in this phase/ROADMAP/PHASES, with matching navigation-only index update.
Only those four governance paths are admitted for acceptance. No HAPI-591,
station configuration, engine, release or deployment starts automatically.

**8. Fixed new JVM and genuine PostgreSQL identities —51 methods.**
These are required future test sources, not executed tests:
- EngineeringRevisionPublicationTest: `rejectsInvalidScopeVersionEpochAndIdentity`, `preservesIncompleteEngineeringDiagnostics`, `neverInfersPhysicalOrOperationalQualification`, `rejectsMismatchedComponentReferences`, `preservesHeadTransitionsAndEpochMonotonicity`, `rejectsConflictingReplayAndOverflow`.
- EngineeringRevisionPublicationCodecTest: `roundTripsCanonicalSourceBytesAndDecimalScales`, `preservesAbsentGraphAndSurveyDiagnostics`, `rejectsUnknownFormatAndNoncanonicalPayload`, `rejectsTruncatedTrailingAndOversizedPayload`, `rejectsComponentDigestOrContextSubstitution`, `preservesOldSourceFormatsWithoutReinterpretation`.
- EngineeringRevisionPublicationCursorCodecTest: `roundTripsContextBoundHighWaterCursor`, `rejectsMalformedOversizedAndNoncanonicalCursor`, `rejectsCrossContextFilterAndAsOfSubstitution`, `preservesStrictDescendingEpochBoundary`.
- EngineeringRevisionPublicationApplicationServiceTest: `requiresPublicationAuthorityBeforeAnyProtectedLookup`, `usesOnePublisherTransactionAndOneOwnerSample`, `rejectsDraftUpdateAsPublicationAuthority`, `publishesExactVersionWithoutEngineeringPromotion`, `rejectsStaleHeadSourceAndConflictingReplay`, `replaysOriginalOutcomeAfterFreshAuthorityWithoutHeadMutation`, `neverUsesListingWindowForExactRead`, `returnsOnlyAfterObservedCommitAndNeverRetriesIndeterminate`.
- EngineeringRevisionPublicationControllerTest: `mapsAllEightRegisteredRoutesToTypedCommands`, `rejectsUnknownFieldsCoercionAndCallerAuthority`, `requiresNativeAuthenticationAndNonDisclosingScope`, `preservesExplicitUnqualifiedStatusAndMissingValues`, `mapsStaleReplayAndOwnerHoldToConflict`, `boundsHistoryAndBindsOpaqueCursor`, `requiresExpectedEpochPredecessorAndRequestId`, `keepsStorageAndIndeterminateDetailsPrivate`.
- EngineeringRevisionPublicationBoundaryGuardrailTest: `onlyPublicationServiceAndCoordinatorReceiveTheirTechnicalPermit`, `publicationDmlTemplatesAndHashesRemainExact`, `domainAndTransportKeepOwnerAndFrameworkBoundaries`, `publicationPermissionsCannotAliasDraftOrStationActions`, `exactRoutesAndFinalGuardRemainClosed`.
- EngineeringRevisionPublicationPostgresIntegrationTest: `fourGenuineProvidersPublishAndReadExactSnapshots`, `ordinaryLoginCannotReadOrMutatePublicationStorage`, `currentDenyRevocationAndForeignScopeRevealNoPublicationData`, `sourceGraphSurveyDigestsAndIncompleteDiagnosticsRemainExact`, `draftEditsNeverRewritePublishedSnapshots`, `concurrentDraftCandidatesHaveOneContextHeadCasWinner`, `identicalReplayIsStableAndConflictingReplayIsRejected`, `withdrawReactivateAndEffectiveBoundariesPreserveHistory`, `exactReadAndKeysetHistorySurviveMoreThanHundredEvents`, `stationOwnerAbaAndTransferMismatchHoldWithoutRebinding`, `writerFirstAndOwnerFirstRetainAllThirtyEightFences`, `expiredFinalGuardAndDirtyFlushRollBackAllPublicationEffects`, `catalogRoleAndLedgerTamperingFailClosed`, `observedCommitAndIndeterminateOutcomeNeverAutoRetry`.

Add meaningful publication-family negatives to the seven existing admitted
suites while retaining their previous identities: genuine issuer transport/
scope/resource mapping, diagnostic/forged-pair rejection, publication-vs-draft
permission separation, exact compiled executor permit refusal, exact route
delegation and source boundary/fingerprint checks. Do not claim new test cases
until their executable source and exact inventory entries exist.

PostgreSQL tests must include actual signed-native LOCAL/OIDC/LDAP/AD authority,
the exact38-relation modes on one physical connection/common sample, authentic
owner revocation/DENY/ABA, latest-source and frozen-old-source distinctions,
snapshot bytes before/after draft edits, two drafts competing for one context
head, positive constraint-valid SQL controls before ordinary42501 assertions,
catalog/trigger/body/column-grant/role tamper rejection, state/head/ledger
constraints, more-than100-event exact and keyset reads, equal-microsecond and
withdraw/reactivate effective boundaries, same-key replay after fresh auth,
poisoned dirty flush/final expiry/post-guard SQL rollback and commit outcome
classification without retry. Separate known simulated acknowledgement failure
from any actually induced unknown database COMMIT. No timing acceptance or
industrial approval is inferred from these methods' names. Full required tests
must execute without skips on the fresh migrated PostgreSQL16 schema.

**9. Current disposition and stop.** The owner's A resolves publication readiness;
HAPI-590 is now NOT_IMPLEMENTED/REGISTERED_PENDING, with the above complete
decision-dependent scope. No runtime implementation, migration/permission,
API artifact, table, role or test execution occurs in this registration.
Current source-backed checks verified21 absent CREATE paths,23 present MODIFY
paths, no migration collision, native authorization/resource/reader and
compiled-permit seams, exact38-lock census and the seven new suites'51 required
names. In-memory task/registry/pointer/history/anchor/scope preservation checks
pass. The selected execution environment is unavailable, so local Python,
canonical-estate/OpenAPI, Java/Maven/PostgreSQL execution is not claimed;
hosted Docs supplies executable validation. Publish only the current four
governance files with expected-head guard and verified parent/tree/blobs/message,
confirm exact-SHA Docs creation/startup and stop. The next single stage is
Package A after Docs success, not B/C or another optional design loop.

### 2026-10-20 — HAPI-590 Package A snapshot contract and canonical codecs

**Selected implementation stage:** `feat(topology): define governed publication snapshot contract`.
Registration head `3690f57650118b267d172cb4eca9c9b481764ce3` passed
[Documentation469](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39464626002).
The owner's Next selects registered Package A only. HAPI-590 is now
PARTIAL / REGISTERED_PENDING; earlier NOT_IMPLEMENTED statements remain
dated history. HAPI-567/588/589 and accepted HAPI-046 timing are unchanged.

**Implemented source:** immutable original component format/version/SHA256/
canonical Base64, exact shared metadata version, optional as-of graph and ordered
per-PIPE survey selections with graph version/digest linkage. Absent or stale
survey selections retain explicit diagnostics; they cannot attach incompatible
source bytes. Qualification is always false and the physical-qualification
diagnostic is mandatory. Metadata missing-title/evidence and graph diagnostics
are retained by codec validation; supplied survey values, decimal scales and
diagnostic order remain unchanged. Snapshot and response collections are copied.

Pure ledger types validate predecessor/epoch/state transitions, backward sampled
time, microsecond precision, actor-scoped immutable replay outcomes, conflicting
replay and epoch overflow. These are proposals and retained evidence, not
authority or proof of an actual transaction. Eight mutually exclusive typed
commands and safe typed failure reasons are defined. The repository's opaque
Admission and immutable Observation live in its public port and reference no
future B class; observations are provenance, never scalar permits.

The new HIDRA_ENGINEERING_PUBLICATION_V1 envelope validates existing metadata,
graph and survey codec formats and original canonical bytes. Encoding is bounded
to32MiB, including envelope overhead; decoding rejects unknown formats,
inconsistent source digests/references, malformed lengths/UTF-8/markers,
truncation, trailing bytes and noncanonical roundtrips. History cursors use
bounded canonical unpadded URL Base64, exact context/company/network/asOf,
captured high-water epoch and strict descending before-epoch. Cursors are
untrusted query data, without a signing-secret or authorization claim; actual
ledger-boundary validation remains B's obligation.

**Exact scope:** CREATE registered A1..9, MODIFY inventory and this phase record
plus only HAPI-590's implementation cells in ROADMAP/PHASES. No deletion,
migration, Identity permission, controller, protected service/coordinator,
storage adapter, generated API/catalog or workflow change is included.
There is no runtime publication path in A.

**Actual new test identities (three classes, sixteen methods):**

- dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublicationTest (6):
  - `neverInfersPhysicalOrOperationalQualification`
  - `preservesHeadTransitionsAndEpochMonotonicity`
  - `preservesIncompleteEngineeringDiagnostics`
  - `rejectsConflictingReplayAndOverflow`
  - `rejectsInvalidScopeVersionEpochAndIdentity`
  - `rejectsMismatchedComponentReferences`
- dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringRevisionPublicationCodecTest (6):
  - `preservesAbsentGraphAndSurveyDiagnostics`
  - `preservesOldSourceFormatsWithoutReinterpretation`
  - `rejectsComponentDigestOrContextSubstitution`
  - `rejectsTruncatedTrailingAndOversizedPayload`
  - `rejectsUnknownFormatAndNoncanonicalPayload`
  - `roundTripsCanonicalSourceBytesAndDecimalScales`
- dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringRevisionPublicationCursorCodecTest (4):
  - `preservesStrictDescendingEpochBoundary`
  - `rejectsCrossContextFilterAndAsOfSubstitution`
  - `rejectsMalformedOversizedAndNoncanonicalCursor`
  - `roundTripsContextBoundHighWaterCursor`

Inventory contains their exact source SHA256 pins and unmeasured zero timing
weights. Existing cases, measured times, provenance, five database sets,
fresh-schema policy and mandatory fallback are preserved.

**Validation and stop:** in-memory source/header/path/case-identity, immutable
collection, codec bounds/dispatch, registry-delta and inventory-preservation
checks completed. SHA256 pin implementation was checked against the standard
abc digest. Local command execution returned "No usable execution environment
is available"; Java parsing/compilation, Maven and PostgreSQL tests were not
executed locally and are not claimed passing. Hosted Full/Fast/Docs must execute
the actual sources. Publish under expected-registration-head guard, verify
parent/tree/message/raw blobs and the exact13-file scope, confirm same-SHA
applicable workflows created/queued/started, then STOP. Review real A feedback
before selecting Package B; A does not establish full HAPI-590 acceptance.

### 2026-10-20 — HAPI-590 Package B protected persistence and API integration

**Selected registered stage:** `feat(topology): implement governed engineering publication`.
A source `d0a2d8ff21645c077498d5344f98c21d40ead4dc`, parent
`3690f57650118b267d172cb4eca9c9b481764ce3`, tree
`fd46e96f84d4d9240d4020b1019682b022f65c5e` passed
[Full786](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39473660710),
[Fast107](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39473660641)
and [Docs470](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39473660571).
Those are actual exact-SHA successful runs, not evidence that the B source below
has already executed. The owner Next selects B. HAPI-590 remains
PARTIAL / REGISTERED_PENDING; no formal software or engineering acceptance,
release, commissioning or deployment is recorded.

**Pre-publication same-task dependency reconciliation:** section7 explicitly
admits reconciliation of a discovered missing dependency/path within this task
before mutation. A's public repository port had named the required Party
generation as userGeneration, although the genuine exported Identity
contribution supplies no user-generation scalar. It also lacked the resolved
scope and pre-write observation/resolution seams required to read receipts,
source and station witnesses before the provisional DML window. Add exactly
one MODIFY to B:
`src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringRevisionPublicationRepositoryPort.java`.
Rename the scalar partyGeneration and add scope/observe/resolve; its immutable
Observation remains provenance, not permission. The actual generation comes
from Party's genuine same-transaction read. No invented Identity generation is
stored. This reconciles B to **12 CREATE /17 MODIFY /0 DELETE**, without another
optional design stage, endpoint, permission family or unrelated source change.
The remaining registered union and C/acceptance scopes are unchanged.

**Implemented protected path:** four exact publication actions/permission codes
and ENGINEERING_NETWORK_PUBLICATION resource mapping through the existing
private qualified native pair; the old four draft mappings and original
station/draft behavior are preserved. No role/user grants or alternative claim
pair constructor is added. A new compiled service/coordinator receives its own
owner-bound technical permit and four independently SHA256-pinned publication
DML templates. Old executor templates and hash sets are retained. The station
witness accepts the exact new compiled service and still requires protected
admission. Eight exact controller/method/verb/path tuples delegate only after
authentication; mandatory native provenance and current paired authority remain
inside the protected use case.

The coordinator acquires genuine Identity24, Party2 and Topology12 fences,
freezes38 distinct descriptors and collects one authorization sample. Modes
remain34 SHARE_ROW_EXCLUSIVE/3 ACCESS_SHARE/1 SHARE. There is no protected
context/source/head lookup before successful paired Identity authorization.
The adapter resolves an existing exact context/draft and current Party/network/
type eligibility; source and original replay receipt, station witnesses and
intended immutable result resolve before provisional writes. Admission SQL's
existing ban on UPDATE tokens is preserved: no SELECT FOR UPDATE escape is
added to Platform. The new event trigger takes the existing context row FOR
UPDATE before validating absent/existing head transitions, in addition to the
held relation fences.

**Source and lifecycle:** PUBLISH pins the current exact positive shared metadata
version, original format/bytes/digests, as-of graph and applicable PIPE surveys.
Survey selection filters the current bounded branch set and checks the total
selected payload size before fetching survey payloads. Incompatible survey
bindings become explicit SURVEY_GRAPH_STALE absences. Missing title/evidence,
graph and survey values/diagnostics remain visible; qualification is always
false. The complete envelope remains bounded to32MiB, with explicit rejection,
no truncation and no physical-value inference.

One immutable context ledger supports publish, withdraw/reactivate, exact/current/
effective reads, actor-scoped request resolution and descending history.
Head predecessors, state, monotonic positive epoch/overflow, backwards sampled
time, request digest and replay are validated. Fresh authorization/owner
eligibility precedes replay; identical input returns its original event and
snapshot while separately reporting the observed current head. Changed input
under the same context/actor/request key conflicts. A replay performs no DML.
Exact lookup uses the exact revision key, independent of pagination.
effective_at is the PostgreSQL authorization sample at microsecond precision;
future asOf rejects. Canonical UTC transport spelling is Instant's exact Z
form, at whole-second/millisecond/microsecond precision. Equal timestamps use
descending epoch and transitions retain half-open committed ledger intervals.
No caller effective time, actor or qualification fields exist.

History limits1..100, captured high-water and strict before-epoch remain context/
company/network/asOf bound. Token values are canonical untrusted query data,
with actual ledger boundary/filter membership checked after authority.
History batches retained publication station evidence and verifies current
owner/kind/status/generation; current/exact/receipt/effective reads also retain
the published owner-generation binding. A committed owner transfer or ABA
holds old publication access without a context rebind or station-head edit.
Station proof uses bounded parameter arrays rather than one query per point.

**Physical schema and final proof:** additive
`V20261020_002__p25_topology_engineering_publications.sql` creates exactly the
three registered publication tables with validated nondeferrable keys/FKs/
uniqueness/checks and immutable statement triggers (including zero-row
UPDATE/DELETE). Context-row event validation and head guards enforce one
successor and same-context event/revision/state/epoch. Snapshot engineering
qualification is constrained false. New security-definer bodies have fixed
safe search_path, are SHA256-pinned by admission and have no PUBLIC execution.
Ordinary credentials receive no publication-table privileges. Runtime admission
checks actual separate principals, inherited/elevated role capabilities,
table/column grants, owners, trigger definitions/enabling, column/key/FK shape,
constraint validation and pinned source/publication function bodies.
Exactly three ownership entries are added. Actual catalog capture is pending;
the physical dictionary is not fabricated or changed in B.

Provisional work permits only snapshot INSERT, event INSERT and head INSERT/CAS
UPDATE with exact affected counts. Managed state flushes before the one final
MATERIALIZED-clock scalar statement. It binds s<=t<u, PID/XID/current principal,
Party/network/context, frozen source/published bytes/digests/version membership,
head/event/replay/native provenance, current station witnesses, actual roles/
catalog and all38 required granted locks. Historical reads do not require a
retained source version to remain latest; a new PUBLISH does. Extra unrelated
locks do not invalidate the required-subset proof. There is no supporting owner
lookup or SQL after the final guard. Aggregate500ms/lock250ms/statement750ms/
idle1000ms limits, poison behavior, observed physical COMMIT delivery and explicit
INDETERMINATE/no retry remain unchanged.

**Executable source and inventory, not passing-test claims:** four new classes
contain exactly the registered35 B methods. Seven existing admitted suites each
add one publication-family method, preserving all previous identities. Their
exact source SHA256 pins and case names are registered; the four new class
weights are unmeasured zero, and existing measured weights, provenance,
five database sets, fresh-schema policy and fallback remain unchanged.

- dz.sh.hidra.modules.topology.application.service.EngineeringRevisionPublicationApplicationServiceTest:
  - `neverUsesListingWindowForExactRead`
  - `publishesExactVersionWithoutEngineeringPromotion`
  - `rejectsDraftUpdateAsPublicationAuthority`
  - `rejectsStaleHeadSourceAndConflictingReplay`
  - `replaysOriginalOutcomeAfterFreshAuthorityWithoutHeadMutation`
  - `requiresPublicationAuthorityBeforeAnyProtectedLookup`
  - `returnsOnlyAfterObservedCommitAndNeverRetriesIndeterminate`
  - `usesOnePublisherTransactionAndOneOwnerSample`
- dz.sh.hidra.modules.topology.api.rest.EngineeringRevisionPublicationControllerTest:
  - `boundsHistoryAndBindsOpaqueCursor`
  - `keepsStorageAndIndeterminateDetailsPrivate`
  - `mapsAllEightRegisteredRoutesToTypedCommands`
  - `mapsStaleReplayAndOwnerHoldToConflict`
  - `preservesExplicitUnqualifiedStatusAndMissingValues`
  - `rejectsUnknownFieldsCoercionAndCallerAuthority`
  - `requiresExpectedEpochPredecessorAndRequestId`
  - `requiresNativeAuthenticationAndNonDisclosingScope`
- dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringRevisionPublicationPostgresIntegrationTest:
  - `catalogRoleAndLedgerTamperingFailClosed`
  - `concurrentDraftCandidatesHaveOneContextHeadCasWinner`
  - `currentDenyRevocationAndForeignScopeRevealNoPublicationData`
  - `draftEditsNeverRewritePublishedSnapshots`
  - `exactReadAndKeysetHistorySurviveMoreThanHundredEvents`
  - `expiredFinalGuardAndDirtyFlushRollBackAllPublicationEffects`
  - `fourGenuineProvidersPublishAndReadExactSnapshots`
  - `identicalReplayIsStableAndConflictingReplayIsRejected`
  - `observedCommitAndIndeterminateOutcomeNeverAutoRetry`
  - `ordinaryLoginCannotReadOrMutatePublicationStorage`
  - `sourceGraphSurveyDigestsAndIncompleteDiagnosticsRemainExact`
  - `stationOwnerAbaAndTransferMismatchHoldWithoutRebinding`
  - `withdrawReactivateAndEffectiveBoundariesPreserveHistory`
  - `writerFirstAndOwnerFirstRetainAllThirtyEightFences`
- dz.sh.hidra.modules.topology.EngineeringRevisionPublicationBoundaryGuardrailTest:
  - `domainAndTransportKeepOwnerAndFrameworkBoundaries`
  - `exactRoutesAndFinalGuardRemainClosed`
  - `onlyPublicationServiceAndCoordinatorReceiveTheirTechnicalPermit`
  - `publicationDmlTemplatesAndHashesRemainExact`
  - `publicationPermissionsCannotAliasDraftOrStationActions`
- dz.sh.hidra.ForensicRemediationClosureTest:
  - `publicationCoordinatorAddsOnlyTheExactReviewedIdentityContract`
- dz.sh.hidra.modules.topology.ProtectedStationAdmissionBoundaryGuardrailTest:
  - `publicationAdmissionKeepsTheSingleClockAndPrivateSourceBoundaries`
- dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipantTest:
  - `publicationResourceMappingNeverChangesDraftOrMintsAClaimPair`
- dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReaderTest:
  - `publicationDiagnosticsAndForgedPairsNeverSelectThePrivateQualifiedFamily`
- dz.sh.hidra.modules.identity.infrastructure.persistence.ProtectedEngineeringDraftAuthorizationPostgresIntegrationTest:
  - `publicationActionsRequireTheirOwnGenuineNativePairedResourceAndDeny`
- dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest:
  - `diagnosticAndOldOwnerPathsCannotMintPublicationMutationPermits`
- dz.sh.hidra.platform.permissions.HidraRouteAuthorizationInterceptorTest:
  - `publicationDelegatesOnlyEightAuthenticatedExactCompiledTuples`

The new PostgreSQL fixture retains actual native LOCAL/OIDC/LDAP/AD producers,
signed source/native token verification, the real HTTP security chain and
independent actual admin/ordinary/publisher logins. Only external source/
directory transport is synthetic. Positive protected publication controls
precede ordinary42501 negatives. Cases cover current DENY/revocation/foreign
scope, exact bytes and stale survey diagnostics, old snapshots after draft
edits, competing candidates for one context head, fresh-auth replay, withdraw/
reactivate/effective boundaries, more-than100 events and concurrent window
stability, station owner transfer/ABA, writer-first/owner-first locks, expired
final SQL, dirty managed flush and deliberately swallowed post-guard SQL
rollback, and real trigger/body/column-grant/privilege/ledger tampering.
The equal-microsecond ledger control uses explicitly identified administrator
constraint-valid rows; it does not claim native authority for that admin.
The physical commit timestamp is checked after actual service return.
Injected acknowledgement classification is explicitly simulated and is not
claimed as an induced unknown PostgreSQL COMMIT.

**Validation and next gate:** static lexical/header/path, old-case/inventory/
timing-policy, exact source/hash/SQL-template/function-body, native seam,
38-fence and scope-preservation checks are recorded for this source. Local
command execution still reports "No usable execution environment is available".
Java parsing/compilation, Maven, Spring, PostgreSQL and documentation scripts
were not executed locally; no new runtime pass is claimed. Hosted CI must
execute the actual B sources. All generated OpenAPI/documentation/dictionary
files retain their last truthful bytes: B is intentionally not artifact
acceptance. Expected artifact drift does not waive genuine source, security,
database or test failures. After actual B artifacts exist, C may reconcile
them from their real exact-source runs.

Publish this exact29-path tree under the expected-A-head guard with the registered
message; verify parent/tree/message/raw blobs and zero deletions. Confirm exact
new-SHA Full/Fast/Docs creation/startup and STOP. Do not wait for completion or
select C/acceptance or another roadmap task automatically. Real B failures must
be repaired before dependent work proceeds.


### 2026-10-20 — HAPI-590 Package B exact-source CI repair

Owner Next refreshed live main `ee1c413ffdc049d0fc6fb6e1a84fdb0ac87daf05`,
parent `d0a2d8ff21645c077498d5344f98c21d40ead4dc`, tree
`e4797505587cfc9f493b8cd80d5c35564ddae24c`. Actual
[Full787](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39479203705)
and [Fast108](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39479203796)
failed; [Docs471](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39479203782)
passed. Failed implementation gates take precedence over C or any successor.
This is a bounded B correction under its registered exact message
`feat(topology): implement governed engineering publication`, not task acceptance.

**Recovered failures and corrections:** Fast's actual controller denial assertion
failed because Spring populated ProblemDetail.instance from the requested URL,
echoing the revision UUID. Both publication error handlers now set the fixed
scope-free instance `urn:hidra:engineering-publication`. The existing denial
test keeps its UUID non-disclosure assertion and additionally requires that
exact instance; no status, error code, protected lookup or authority is relaxed.

Full's PostgreSQL shard3 ran239 cases with one failure, zero errors/skips:
fourGenuineProvidersPublishAndReadExactSnapshots expected200 but received409.
The loop used one company/network context across all four providers, retained
its immutable ledger, yet submitted expectedEpoch0 for each new draft candidate.
The test now carries the actually returned predecessor epoch/revision into the
next provider's HTTP publication. It asserts exact epoch increments and reads
the preceding retained snapshot through the next authorized candidate. Genuine
signed LOCAL/OIDC/LDAP/ACTIVE_DIRECTORY producers, real HTTP security, separate
database principals and success/unauthenticated/unknown-field assertions remain.
The production CAS/context invariant is unchanged. All other thirteen publication
PostgreSQL methods passed in that run; that does not establish full B acceptance.

The API job successfully packaged/started the actual application and generated
OpenAPI, then failed strict snapshot equality, as expected for B's new routes.
That separate artifact drift remains pending C; no generated artifact is
hand-authored here, no workflow/equality gate is weakened and no CI pass is
claimed. Secondary missing lane-evidence uploads followed the failed checks.

**Exact five MODIFY paths:** this phase record, publication API exception handler,
its existing controller test, its existing PostgreSQL integration test and
`.github/ci-test-inventory.json`. Zero CREATE/DELETE. The inventory refreshes
only the two modified test source hashes; all case identities, timing weights,
lane assignments, five database sets, policy and historical evidence remain.
Frozen roadmap/phase status tables, migrations, authorization/transaction
mechanics and every other source/artifact retain their prior bytes.
HAPI-590 remains PARTIAL/REGISTERED_PENDING.

**Actual validation and stop:** inspected complete changed source and exact-SHA
job logs; in-memory original SHA256/source-pin verification, hash self-test,
case preservation, two-hash-only inventory comparison and five-path scope checks
passed. Shell execution is unavailable ("No usable execution environment is
available"), so no local Java/Maven/PostgreSQL or canonical documentation-script
pass is claimed. Publish with expected-ee1c413 guard, verify exact tree/parent/
message/paths/blobs and confirm applicable new-SHA CI creation/startup, then STOP.
Required test success remains pending; C artifact reconciliation is the next
registered stage only after genuine B failures are resolved. No further task,
benchmark, release or deployment is selected.

### 2026-10-20 — Owner-directed failed-gate hold and independent task continuation

The owner explicitly directs: mark this as a failing task and proceed with
another task because the artifact-processing environment is currently unavailable.
This is an authorized sequencing exception to repair-before-advance, restricted
to independent development work. It does not waive HAPI-590 acceptance or any
CI gate. Current state is **PARTIAL / BLOCKED**: source implementation exists,
but the required Full gate is failing and publication is not accepted.
Do not label the runtime DEFECTIVE solely from artifact drift.

Exact source `ce9cdf0be134fa968eecc205c53a028303da8328`, parent
`ee1c413ffdc049d0fc6fb6e1a84fdb0ac87daf05`, tree
`14c4bed0bc57a5ba319798d33a1319746dba868e` has
[Full788 FAILURE](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39496709378),
[Fast109 SUCCESS](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39496709359)
and [Docs472 SUCCESS](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39496709385).
Full's unit/architecture, static and all five PostgreSQL jobs succeeded.
API job118883532586 failed strict canonical OpenAPI equality after actual
runtime generation; the subsequent dictionary validation did not execute.
The Full gate remains FAILURE. Its serial diagnostic job was skipped; no
unrestricted serial run or full software acceptance is invented.

Package C remains recoverable from actual source/run/attempt1 artifacts,
including API diagnostics artifact12298846165. Required work is actual generated
API reconciliation and actual migrated catalog/source/ownership dictionary
validation, followed by required exact-SHA CI and later separate acceptance.
The generated contract and physical dictionary retain their truthful prior bytes;
no hand-authored physical metadata, test removal, equality relaxation,
workflow bypass, source rollback or acceptance is selected. The lack of a usable
execution environment is an agent tooling limitation, not missing owner code
or deployment calibration data.

HAPI-591, HAPI-592 and dependent integrated closure remain blocked.
The next active independent task is HAPI-778 (Organization appointment lifecycle),
which has no registered dependency on HAPI-590. Its initial source review and
next bounded design stage are recorded in TASKS_HPS-003.md.
This hold does not authorize release, deployment, simulation or live OT work.


### 2026-10-21 — HAPI-590 Package C actual-artifact repair after restored execution

**Owner selection and exact stage:** Owner Fail selects the responsible inherited
Full789 artifact failure, not a new HAPI-778 domain change. Exact source
`11084d86126b0a0a5851f4d8bd17b7fea3e1eb47`, parent
`f36fb4dcefb6dbb25df96510b10c64837c44570f`, tree
`acdcf81a6541e8b1dd4507585ca1812631fedac9` failed
[Full789](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39501079650);
[Fast110](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39501079645)
and [Docs475](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39501079637)
passed. All five DB sets, unit/architecture and static jobs succeeded.
The owner previously held HAPI-590 because artifact extraction was unavailable;
execution access now works. This bounded repair resolves that concrete common
gate using its already registered C scope, without changing HAPI-778 code.
Exact message: `docs(topology): reconcile governed publication artifacts`.
MODIFY only this phase record, doc/api/openapi.yaml, doc/api/OPENAPI_GOVERNANCE.md,
.github/documentation-validation.json and doc/database/DATA_DICTIONARY.md.
Zero CREATE/DELETE. Registry/index/task statuses, source, tests, inventory,
migration history, ownership entries, workflows and all prior evidence remain.

**Verified actual OpenAPI:** API job118898431865 built the current application
(step8) and generated OpenAPI (step9) successfully, then failed canonical equality
(step10). Standard dictionary capture, base generation and compatibility steps
were skipped. The manifest scopes conclusion=success to generation only and
explicitly records failed job/workflow conclusions; no gate is waived.
Recovered artifact12300211836, run39501079650/attempt1, ZIP
`562dfebc21389185e2fc9750144d9d243d27378f807db4326d50e57bdb4088f0`;
member openapi/hidra-api.json SHA256
`692060ef6b414abd09cd4f4099518703c49297e64c124e41e5704576ea652354`.
The canonical sorted compact snapshot is copied from that object, with digest
`0464bf15e2afcdbb7c8275fe3347118a8b60da9979de27967a4938beebb39069`.
257 paths/279 operations/267 schemas: eight publication path items/operations
and nine schemas added. Existing paths/schemas, all non-schema components/
security and top-level objects remain unchanged apart from validated source SHA.
Strict generated equality and supported local compatibility comparison to the
preceding canonical snapshot pass; fresh hosted repair-SHA checks remain required.

**Verified actual database artifact:** successful PostgreSQL shard3
job118898432732 retained the actual catalog capture in the successful11-case
SimulationEquipmentParameterRevisionPostgresIntegrationTest raw XML. Artifact
12300606228 ZIP SHA256
`acce81c7576acceb07c7be4c00d64775d7211ce2a7fcd838adc75240d2cce798`;
member
`surefire-reports/TEST-dz.sh.hidra.modules.simulation.infrastructure.persistence.SimulationEquipmentParameterRevisionPostgresIntegrationTest.xml`.
Contiguous ordered CATALOG_PART Base64/gzip chunks reconstruct the document;
catalog SHA256
`a320108f2affd477f3aaac7d9e4f76665d924b062a00439b8a1d545fc496d51c`
and actual recorded source SHA agree with the retained verification line.
The unchanged generator verifies all exact-checkout migration/JPA hashes and
reviewed ownership, then renders physical facts from that actual catalog.
158 migrations/470 mappings/505 relations/zero unresolved owners; PostgreSQL16.15;
source bundle
`edcbccdd40dc9db169773adfdec7c1793260513d4a052a36916492ccef503152`;
dictionary
`c3dfaa10a90a7872a5bdee22c1a5e59290135697ef20b6b25bcad14f2f19b1c7`.
That rendered digest matches the original test's verification digest exactly.
No physical metadata was inferred, hand-authored or captured from a local DB.
The ordinary API-lane dictionary step was skipped and must still pass after repair.

**Current validation:** verified both ZIP digests against GitHub artifact metadata;
validated raw report success, capture chunk ordering/source/catalog digests;
regenerated canonical OpenAPI and source/ownership-backed physical dictionary
with unchanged repository scripts. Strict runtime equality against the retained
generated source, supported backward compatibility and strict dictionary equality
pass locally. Canonical Python validator suites, documentation/inventory checks
and git diff whitespace/scope validation are run before publication; exact
results are recorded below. Java/Maven/PostgreSQL suites are not rerun locally;
the existing exact-source hosted successes are evidence for their actual lanes,
not a successful aggregate Full789.

**Completed local checks:** all134 Python validator tests passed (Docs17, OpenAPI10, dictionary17, base resolver14, inventory31, full-evidence45). Canonical documentation validates128 documents/8277 links/24 modules/921 tasks/25 phases. CI inventory validates443 source classes (439 active plus4 retained historical) with all five database sets preserved. Strict generated OpenAPI equality, supported backward compatibility, strict actual-catalog dictionary equality and final git diff whitespace/scope checks pass. No new Java/PostgreSQL execution is claimed.

**Status and stop:** HAPI-590 remains PARTIAL/BLOCKED pending required repair-SHA
verification and its separately selected formal acceptance. This artifact commit
is not software/engineering acceptance; neither child nor integrated dependencies
are automatically unblocked. HAPI-778 remains PARTIAL/IN_PROGRESS with actual
Fast110/Docs475 and Full789 lane successes; its domain code is untouched.
After applicable exact-SHA CI is created/queued/started, report actual links/state
and STOP. Failure feedback repairs the actual gate first. After required gates
succeed, formal HAPI-590 acceptance and the active HAPI-778 scoped-authority/
ownership review remain separately selected registered stages; no automatic
acceptance, release, deployment, simulation or OT operation occurs.

### 2026-10-25 — HAPI-590 governed publication final development acceptance

**Disposition: VERIFIED / COMPLETED for the registered governed public revision
publication/history software scope.** Owner Next after accepted HAPI-778 selects
the already registered final HAPI-590 acceptance, exact message
`docs(topology): record governed publication acceptance`.
The existing acceptance contract explicitly admits only this phase record,
ULTIMATE_ROADMAP.md, ULTIMATE_PHASES.md and the matching index pointer.
The former failed-artifact hold is resolved by actual accepted current evidence;
do not rerun implementation or repeat Package C solely because its dated
historical hold text remains preserved.

**Source and gate chain.** Current main
`d36f8c08206061d931bf0ef045a4956ee97fdf2d`,
tree `d82270976b1ad8726ea70aafed1f69dcfe64ea72`;
[Docs592](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/40232685684)
SUCCESS. Its parent
`edd66b3a4dc27b724dbb99b6eaf6d0a456fe6ae3`,
tree `a1055c6e8d1b039618b51b243eb3c6340d7b6d54`, passed
[Full899](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/40231624550)
attempt1 and Docs591. Compare the complete recursive trees: only four HAPI-778
governance files differ; every production/test/configuration/workflow/SQL/JPA/
inventory/API/dictionary blob is identical to that Full899 source.
Fast206 succeeded on the same final runtime source bc71df4d; subsequent commits
only reconciled artifacts and governance. No new Fast job was selected for the
artifact-only Full899 source or the docs-only closure.

Full899's static, unit/architecture, all seven PostgreSQL shards, actual
API/catalog and final aggregate gates succeeded. The aggregate ZIP
artifact12612804441, run40231624550 attempt1, SHA256
`7d79f0649cb415ffe80a3e9fd4d7042acdd4e09763ba1de1f69f85f6de958d58`
matches its authenticated GitHub digest. Independently recheck10 gate-to-lane
receipt hashes and all466 sealed files, source/run/attempt and raw XML results:
459 active classes,3205 executions,zero failures/errors/skips. The existing
four approved greenfield historical archives are not newly skipped active tests.
Development partition mode has serial_oracle=false, same_run_equivalence=false
and release_eligible=false; the optional serial diagnostic was intentionally
unselected. No unrestricted audit/release acceptance is fabricated.

**All58 registered acceptance cases are actually present and passing.**
The exact51 dedicated names in the original package contract match the raw
reports, plus all seven required existing-suite extension names below.
Independently hash all seven dedicated test source blobs and compare their
current inventory source_sha256 pins: every source hash matches.

| Required dedicated suite | Successful executed cases | Accepted scope |
|---|---:|---|
| EngineeringRevisionPublicationTest | 6 | Context/version/epoch identity, immutable head transitions, conflicting replay, explicit missing diagnostics and no inferred physical qualification |
| EngineeringRevisionPublicationCodecTest | 6 | Exact canonical source formats, decimal scales, source/component digests, old formats, malformed/truncated/oversized payload rejection |
| EngineeringRevisionPublicationCursorCodecTest | 4 | Context/filter/asOf binding, high-water boundary, strict descending epochs and malformed/substituted cursor rejection |
| EngineeringRevisionPublicationApplicationServiceTest | 8 | Native authority before protected lookup, one publisher transaction/sample, fresh-authority replay, exact reads independent of listing and no indeterminate retry |
| EngineeringRevisionPublicationControllerTest | 8 | Eight typed routes, strict body/coercion rejection, expected epoch/predecessor/request key, non-disclosure and bounded history |
| EngineeringRevisionPublicationBoundaryGuardrailTest | 5 | Closed route/permit/SQL/hash/owner boundaries and distinct publication permissions |
| EngineeringRevisionPublicationPostgresIntegrationTest | 14 | Actual native providers/HTTP, separated roles, CAS winner, immutable snapshots, replay, lifecycle/history, owner ABA,38-fence ordering and rollback/catalog tamper |

The seven successful existing-suite extensions are:
- ForensicRemediationClosureTest.publicationCoordinatorAddsOnlyTheExactReviewedIdentityContract
- ProtectedStationAdmissionBoundaryGuardrailTest.publicationAdmissionKeepsTheSingleClockAndPrivateSourceBoundaries
- ProtectedOperationIdentityParticipantTest.publicationResourceMappingNeverChangesDraftOrMintsAClaimPair
- JdbcProtectedAuthorizationEvidenceReaderTest.publicationDiagnosticsAndForgedPairsNeverSelectThePrivateQualifiedFamily
- ProtectedEngineeringDraftAuthorizationPostgresIntegrationTest.publicationActionsRequireTheirOwnGenuineNativePairedResourceAndDeny
- ProtectedTransactionExecutorTest.diagnosticAndOldOwnerPathsCannotMintPublicationMutationPermits
- HidraRouteAuthorizationInterceptorTest.publicationDelegatesOnlyEightAuthenticatedExactCompiledTuples

**Requirement-to-runtime closure.** The14 real PostgreSQL methods establish:
- fourGenuineProvidersPublishAndReadExactSnapshots: genuine signed LOCAL/OIDC/
  LDAP/ACTIVE_DIRECTORY native producers through the actual HTTP security chain;
  same-context epochs/predecessors advance correctly and old snapshots remain
  readable. External directory/decoder transport stays synthetic.
- concurrentDraftCandidatesHaveOneContextHeadCasWinner and
  identicalReplayIsStableAndConflictingReplayIsRejected: exactly one competing
  context-head successor; fresh native authority preserves original receipt/
  snapshot on identical replay without another event, while conflicting replay
  is rejected.
- sourceGraphSurveyDigestsAndIncompleteDiagnosticsRemainExact and
  draftEditsNeverRewritePublishedSnapshots: original formats/bytes/digests,
  optional graph/survey selections and missing/stale diagnostics remain exact.
  The owner's selected readiness A is preserved: engineeringQualified=false,
  with no invented physical or operational facts.
- withdrawReactivateAndEffectiveBoundariesPreserveHistory and
  exactReadAndKeysetHistorySurviveMoreThanHundredEvents: immutable lifecycle
  history, equal-time/effective boundary semantics, exact original read after
 103 events, stable high-water paging while event104 appends, no duplicates or
  foreign-context cursor substitution.
- currentDenyRevocationAndForeignScopeRevealNoPublicationData,
  ordinaryLoginCannotReadOrMutatePublicationStorage and
  stationOwnerAbaAndTransferMismatchHoldWithoutRebinding: current scoped authority,
  ordinary-login42501 boundaries after positive controls, station type/status/
  ownership changes and monotone owner-generation ABA reject without rebinding
  or foreign metadata.
- writerFirstAndOwnerFirstRetainAllThirtyEightFences,
  expiredFinalGuardAndDirtyFlushRollBackAllPublicationEffects and
  catalogRoleAndLedgerTamperingFailClosed: real ordered/final held fences,
  physical guard-expiry injection, dirty managed flush and deliberately swallowed
  post-guard SQL all retain rollback/poison behavior; actual catalog/trigger/
  role/ledger changes fail closed.
- observedCommitAndIndeterminateOutcomeNeverAutoRetry: actual committed-event
  transaction timestamp exists after service return. INDETERMINATE handling is
  separately tested with explicitly simulated acknowledgement classification,
  not falsely described as an induced unknown PostgreSQL COMMIT.

**Actual generated contracts/catalog accepted.** Full899 API job121284390909
passed runtime snapshot equality, generated-base compatibility, actual migrated
catalog/dictionary generation/equality and evidence sealing. Its retained API
contains all eight publication operations with hidraBearerJwt requirements:
publish,withdraw,reactivate,exact,current,effective,history,request receipt.
Canonical overall counts265 paths/287 operations/279 schemas include later
appointment additions; they are not publication-only counts.

The same retained API compares equal to the canonical snapshot after validation
and removal of only generator source stamps. Supported backward compatibility
against actual Full894 base50c86303ba5ed2174ef0183704b260370026a860 passes.
Current API SHA256
`345cad7a82d1fc5093324910476d57c6a86d8bd809dec27be098b6a4e77d03c0`;
base API SHA256
`cd0de17d857181cd4a159b3d26a2ecde5d6c9c8fae1b478426622ee011d576b1`.
Actual PostgreSQL16.15 verification reports173 migrations,470 JPA mappings,
516 relations,zero unresolved owners and reviewed_dictionary_checked=true.
Catalog SHA256
`716bba7873fbdc0502cde82ec4dc591a3a89a25974a184dd9313a83410d5b27a`;
captured dictionary SHA256
`0df8adf52bc8c2e42284849db6533e763c4a65eedd9acb18ef8e253a6414268b`;
source bundle SHA256
`6893e31c69bae45e190291858580397341414750b455da7273dfb57eba33b010`.
These exact-source captured facts close the old Package C artifact gate;
no generated artifact or physical fact is handwritten in this acceptance.

**Scope and preserved limits.** Zero CREATE / four MODIFY / zero DELETE:
- TASKS_HPS-004.md: only HAPI-590's two live status fields to VERIFIED/COMPLETED plus this task evidence.
- ULTIMATE_ROADMAP.md and ULTIMATE_PHASES.md: only matching HAPI-590 status cells to VERIFIED/COMPLETED.
- ULTIMATE_TASKS.md: only stale current-pointer descriptions of HAPI-590's resolved failed-artifact hold; preserve all other accepted work and no successor selection.

Preserve every historic source/failed-run/hold record as dated evidence.
HAPI-778 remains VERIFIED/COMPLETED; all other task IDs/statuses/priorities/
dependencies/membership and all runtime/test/artifact blobs remain unchanged.
No HAPI-591/592, station configuration, HidraWEB HWEB-F02/F03 integration,
release, engine, deployment or live OT operation is selected automatically.

Software acceptance is not engineering readiness: the owner's A remains
unqualified publication with explicit incompleteness, no calibrated simulator
or operational/physical qualification. Synthetic data and external source
transport controls do not certify real IdP/network/SITE conditions. Ordinary
publication500ms/lock250ms/statement750ms/idle1000ms limits remain unchanged;
passing functional cases is not a production latency/load percentile claim.
The separate appointment7500ms development cap is not applied to publication.

**Validation and publication.** Recomputed aggregate/file/case provenance and
all seven dedicated source hashes PASS; all51 dedicated plus7 extension cases
match actual successful reports. Existing19 documentation-validator self-tests
PASS. Before publication verify exact four-file scope, unchanged history,
all921 registry/index identities, matching status rows and phase metadata.
No new local Java/Maven/PostgreSQL execution is claimed by this docs-only stage.

Publish one registered acceptance to main under expected d36f8c0 lease, verify
exact parent/tree/four-file scope, confirm applicable Documentation CI
creation/queue/start and STOP without waiting or starting another task.
Implementation acceptance remains anchored to successful Full899; the new
documentation commit's own validation is pending its workflow conclusion.
No registered HAPI-590 implementation stage remains in this accepted bounded
development scope. This acceptance does not establish dependent integrated
or production readiness.



<a id="hapi-592"></a>
## HAPI-592 — Topology operator/API/history qualification

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HPR-P25-052F

Priority: P1

Implementation Status: NOT_IMPLEMENTED

Planning Status: IN_PROGRESS

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-591

Cross-Repository Dependencies: HWEB-F02, HWEB-F03 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Cross-slice operator/API/security/history qualification and consumer-ready contract.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/TopologyModule.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/TopologyApi.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/TopologyRestApi.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/analytics/AnalyticsTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/incident/IncidentTopologyContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/integrity/IntegrityCaseTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/leakdetection/LeakDetectionTopologyAssetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/organization/TopologyOperationalScopeTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/mapper/TopologyApplicationMapper.java`
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

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver company/network drafts, physical graph editing and protected publication/history APIs after owner-fence acceptance. The exact bounded remaining delivery is: Cross-slice operator/API/security/history qualification and consumer-ready contract..

### 4. Functional Requirements

Real configured API/filter path across two companies/networks and station graphs, rollback/concurrency/corruption tests; exact runtime OpenAPI/permissions/DTOs and relevant consumer handoff, no new frontend implementation in this repo.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — PROPOSED:**

- `src/test/java/dz/sh/hidra/modules/topology/qualification/TopologyEngineeringOperatorQualificationTest.java` — actual owner/filter/database qualification of this task's exit matrix.

**MODIFY:** `.github/ci-test-inventory.json` — actual class/case/hash admission; `doc/roadmap/ULTIMATE_TASKS.md` — exact-source evidence and limitations.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/topology/TopologyModule.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/TopologyApi.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/TopologyRestApi.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/analytics/AnalyticsTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/incident/IncidentTopologyContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/integrity/IntegrityCaseTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/leakdetection/LeakDetectionTopologyAssetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/organization/TopologyOperationalScopeTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/mapper/TopologyApplicationMapper.java`
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

**DISCOVERY_REQUIRED:** fixture/support filenames and exact test partition; use synthetic owners and real transactions, not fabricated prior results.

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

- Real configured API/filter path across two companies/networks and station graphs, rollback/concurrency/corruption tests; exact runtime OpenAPI/permissions/DTOs and relevant consumer handoff, no new frontend implementation in this repo.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-591. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23361](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23361)
- [doc/model-remediation/RECONCILIATION.md:23739](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23739)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1047](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1047)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1299](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1299)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1047 | HPR-P25-052F | BLOCKED_SPECIFICATION | Topology operator/API/history qualification
doc/roadmap/ULTIMATE_ROADMAP.md:1299 | HPR-P25-052F | Cross-slice operator/API/security/history qualification and consumer-ready contract. | Real configured API/filter path across two companies/networks and station graphs, rollback/concurrency/corruption tests; exact runtime OpenAPI/permissions/DTOs and relevant consumer handoff, no new frontend implementation in this repo.
doc/model-remediation/RECONCILIATION.md:23361 | HPR-P25-052F | Cross-slice operator/API/security/history qualification and consumer-ready contract. | Real configured API/filter path across two companies/networks and station graphs, rollback/concurrency/corruption tests; exact runtime OpenAPI/permissions/DTOs and relevant consumer handoff, no new frontend implementation in this repo.
doc/model-remediation/RECONCILIATION.md:23739 | HPR-P25-052F | BLOCKED_SPECIFICATION | Topology operator/API/history qualification
~~~~~~~~


### 2026-10-26 — HAPI-592 source-backed operator qualification registration

Owner Continue after HAPI-591 acceptance selects this dependency-ready task's
bounded discovery/registration stage. Exact message:
`docs(topology): register operator API history qualification`.
Source main38ba912f3960861a783b2a87986c2b73f2d1d432 has successful exact-head
Docs600 run40280149115. Its runtime source is unchanged from accepted Full904
on4a69624ecc635203fecb404c61933c696708d7f4 (3243 actual cases,465 classes).
HAPI-588/589/590/591 remain accepted within their recorded scopes. This stage
changes only HAPI-592 planning BLOCKED to IN_PROGRESS; implementation remains
NOT_IMPLEMENTED. No prior cumulative passing run accepts HAPI-592 itself.

**Recovered current owners and fixture placement.** EngineeringNetworkDraftController
owns eight metadata/graph/survey tuples; EngineeringRevisionPublicationController
owns eight publication/head/effective/history/request tuples;
EngineeringLocalExchangeController owns four editor tuples. All are under
/api/v1/topology/engineering-networks. The exact compiled tuple checks in
HidraRouteAuthorizationInterceptor delegate final admission to the existing
native owners; they do not grant prefix-wide access. Existing inbound owners:
EngineeringNetworkDraftUseCase, EngineeringGraphDraftUseCase,
SurveyRouteProfileUseCase, EngineeringRevisionPublicationUseCase and
EngineeringLocalExchangeUseCase in topology/application/port/in. All DTOs,
controllers, scoped advice and publication JDBC storage were recovered at this
source, together with the current actual runtime OpenAPI and inventory.

Existing real native fixtures in topology/infrastructure/persistence:
- EngineeringNetworkDraftPostgresIntegrationTest:43 cases, source SHA256
  28c9a822ec51b7e21171c20787a5081efc237af1b26d2dca9b39906963f0b1b7.
- EngineeringRevisionPublicationPostgresIntegrationTest:14 cases, source SHA256
  8cc32d91c0ba069435c13c1df9407f25e7ec81a34f8e7b95769a3e0a72c848ca.
- EngineeringGraphDraftPostgresIntegrationTest:5 isolated DDL cases, source SHA256
  2abad7c852a8dd8578e1bb4e9e47101c119daf42d229f86352f7cd14fc64ecd5.
Existing interceptor suite11 cases source SHA256
b0542a1a578589a71629892e05ef262edfdd96e702fa59eb6d0f0f91ac621713.
These retain independent obligations; copying fixture code never imports their
executed evidence into the new task. The isolated graph DDL fixture is not the
operator acceptance path. Publication storage source SHA256
dff7e940cf689eae0a64632aa592c3da37edee1e5b0a199f3497ac03fcf5b04d.

Use the publication fixture's actual full Flyway setup, real native producers,
MockMvc web context plus springSecurity filters, protected transaction manager,
synthetic directory/source transports, actual ordinary c3_source_claim_app and
publisher c3_publication_app principals and privileged setup-only connection.
Its private helpers are not inheritable. The proposed new qualification class
will contain only the necessary copied/adapted setup/helpers, not extend the old
test class or replay its fourteen methods. No new shared fixture/support file is
admitted. Keep old fixtures byte-identical. Configure Docker absence to fail the
new required class, not skip it. Setup SQL constructs synthetic owners/grants;
all operator actions and assertions of authority use the actual HTTP/owner path.
Spies may inject an explicit final-guard/flush fault but cannot replace successful
Identity, Party, Facility, repository or transaction operations with mock answers.

**Discovered consumer contract gap.** Current generated269-path/291-operation/
283-schema OpenAPI lists only200 for these20 operations, although existing
scoped advice maps malformed400, denied403, missing404, conflict409 and
unavailable/indeterminate503; authentication also yields401 through the filter
or applicable owner/controller. The source response mappings are preserved.
Register additive Swagger response annotations on the three existing controllers
to expose their actual supported statuses. Do not claim every filter error has
the scoped advice's ProblemDetail envelope.401 content is explicitly unspecified
unless the actual filter response is established; owner errors use their current
ProblemDetail DTO, fixed details and code where present. No new error handler,
runtime mapping, route, DTO, authority bypass or accepted-status-set relaxation.
Any actual runtime inconsistency found by the new tests must be recorded and
separately bounded before a production behavior repair, rather than hidden by
annotations or inferred from status alone.

**Registered implementation stage, not executed:** exact message
`test(topology): qualify operator API and revision history`.
One CREATE / five MODIFY / zero DELETE:
- CREATE src/test/java/dz/sh/hidra/modules/topology/qualification/TopologyEngineeringOperatorQualificationTest.java.
- MODIFY src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringNetworkDraftController.java.
- MODIFY src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringRevisionPublicationController.java.
- MODIFY src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringLocalExchangeController.java.
  Controller edits are documentation annotations/imports and UpdatedOn only;
  preserve method signatures/bodies, DTOs, success responses and operation IDs.
- MODIFY .github/ci-test-inventory.json: admit only the actual new class, exact
  source hash and case identities; postgres-integration, fast=false,
  baseline_seconds=0 explicitly unmeasured, dependency reason full native
  Spring/Flyway/PostgreSQL operator qualification. Preserve all current weights,
  classes, historical archives, seven shards and workflow mode. Do not tune
  weights or exclude old tests to shorten CI.
- MODIFY this phase file: append only HAPI-592 implementation evidence.
No governance status promotion occurs during source delivery.

Exactly eight planned @Test methods, one invocation each, loops inside methods
where appropriate; not executed cases or invented future report counts:
1. nativeOperatorsAuthorExchangeAndPublishExactSnapshotsAcrossFourProviders —
   real signed LOCAL/OIDC/LDAP/ACTIVE_DIRECTORY; actual HTTP create, graph and
   survey authoring, validation, all three editor export/dry-run/import routes,
   publish and exact read. Include station-bearing and serial/parallel network
   facts, decimal scale/null/unit/unknown retention, exact canonical digests,
   shared expected versions and immutable published snapshots. Keep same-graph
   survey rebind provenance separate; engineering qualification remains false.
2. twoCompaniesAndNetworksCannotReadWriteOrReplayEachOthersSelectors —
   create actual active A/B companies, A/B networks and owned stations, distinct
   identities with only their company+network grants. Try both company and
   network substitutions independently across draft, graph, survey, exchange,
   publication/current/exact/effective/history/request and transitions; assert
   fixed non-disclosing denial and complete storage invariance. Distinguish
   existing unauthorized company403 from absent owner503, not a broad status set.
3. currentDenyAndRevocationOverridePriorPreviewsAndUnrelatedRoutes —
   ordinary login cannot publish; READ preview never grants UPDATE/PUBLISH;
   actual DENY/revocation between preview and fresh write denies through HTTP;
   anonymous401 and sibling/wrong compiled tuples cannot delegate. Reuse actual
   grants, no forged actor evidence or owner mock. Assert no partial successors.
4. publishedHistoryRemainsExactAfterSourceEditsAndLifecycleTransitions —
   original exact snapshot survives metadata/graph/survey changes; explicit
   request replay and conflicting replay; withdraw/reactivate and half-open
   committed as-of boundaries, more than100 events with bounded cursor pages,
   no duplicates/omissions; exact revision lookup independent of pagination.
5. concurrentHttpWritersAndPublishersHaveOneCasWinner — concurrent HTTP writes
   sharing one explicit draft version and concurrent publication candidates
   sharing one head epoch; each scenario has exactly one committed winner,
   native409 loser and exact metadata/component/event/head row counts.
6. finalGuardAndManagedFlushFaultsRollBackEveryOperatorEffect — actual protected
   write with targeted final guard invalidation and managed flush failure;
   before/after complete draft/component/publication storage images equal.
   Report injected faults truthfully; no physical unknown COMMIT or elapsed
   deadline/performance claim is inferred, no automatic retry on indeterminate.
7. stationOwnershipAndCatalogCorruptionFailClosedWithoutHistoryMutation —
   station owner transfer/ABA and graph incidence mismatch denied without
   rebind; privileged fixture alters reviewed trigger/function/role invariants,
   actual owner refuses; restore in finally and verify ledger/head unchanged.
   No impossible update of immutable ledger is claimed as real data corruption;
   trigger/function integrity is the concrete catalog corruption exercised.
8. runtimeConsumerContractMatchesTypedRoutesErrorsAndBounds — compare actual
   in-process generated /v3/api-docs and typed operation inventory for20 tuples,
   bearer security, existing DTO references and additive error response status
   documentation. Exercise representative actual malformed/scope/stale/error
   bodies, canonical UTC/microsecond asOf, history limit/cursor, exact editor and
   branch/version requirements,24MiB raw exchange bound and existing payload
   bounds. Check fixed error fields and protected selector absence; distinguish
   filter401 from owner ProblemDetail. This is backend consumer handoff proof,
   not a completed HidraWEB integration. No remote URLs, archives or batch import.

The consumer handoff is the accepted generated OpenAPI plus this source-linked
matrix: eight draft/graph/survey tuples use topology:engineering-draft:
create/read/update/validate as applicable; publication PUBLISH/WITHDRAW/REACTIVATE
use topology:engineering-publication:publish/withdraw/reactivate, all five reads
use topology:engineering-publication:read; exchange export/dry-run/capabilities
use existing READ and imports dispatch one existing UPDATE. Company and network
scope are both required. Packages/provenance/digests do not convey write authority.
Preserve protected500ms/lock250ms/statement750ms/idle1000ms bounds unchanged;
they do not establish an end-to-end HTTP SLA. All published snapshots remain
engineering-unqualified unless a separate real qualified contract is admitted.

**Following separately selected stages.** After actual hosted outputs and any
responsible runtime repair, exact message
`docs(topology): reconcile operator qualification API contract`:
MODIFY only doc/api/openapi.yaml, .github/documentation-validation.json openapi
object and this HAPI-592 record. Use actual source/run/attempt/artifact/member
digests. No schema/JPA/source-owner change is planned; preserve physical dictionary
bytes and require normal migrated catalog equality. Preserve prior nested
provenance; strict snapshot equality and successful-runtime-base compatibility
remain unchanged. Never invent the generated error schemas or updated counts.

After applicable exact-source Full/Fast/Docs, case-level/native/cross-scope proof
and complete artifact gates, exact message
`docs(topology): record operator qualification acceptance`:
only this phase's HAPI-592 statuses/evidence, matching ROADMAP/PHASES status cells
and compact index pointer (four governance MODIFY). HAPI-608 integrated closure
remains separately selected. No new frontend, remote/multi-owner exchange,
engineering/SITE commissioning, release, deployment, simulation/P3 or OT action.

**This registration's exact scope and checks.** Zero CREATE / four MODIFY /
zero DELETE: append this record, change only HAPI-592 planning to IN_PROGRESS
in its header and ROADMAP/PHASES row; add one compact current index pointer.
Preserve all prior evidence and production/test/artifact/workflow/schema blobs.
Validate focused921-task/index and61-task selected-phase status agreement,
exact old-history/status-only scope, three present controller paths and absent
proposed class, actual20-operation runtime inventory, source hash/case recovery
and19 existing documentation-validator self-tests. No new Java/Maven/PostgreSQL
execution or new qualification/API artifact acceptance is claimed in this stage.
Follow AGENTS CI-first; do not provision a full local environment merely to test.
Publish this registration with expected38ba912 lease, verify exact one-parent/
tree/four-file scope, observe applicable exact-SHA Documentation CI creation/
queue/start and STOP. Implementation is the next separately selected stage.


#### HAPI-592 operator qualification source delivery — 2026-10-26

Owner Continue selects registered stage2 after Docs601 run40281049029 succeeded
onf5459a4f3455b7516d60d63bcdce928947e2705a. Exact message:
`test(topology): qualify operator API and revision history`.
Implement the registered one-CREATE/five-MODIFY scope; no task status changes.

The new standalone TopologyEngineeringOperatorQualificationTest contains exactly
the eight registered @Test methods, without inheritance or new support files.
It copies/adapts only native producer/full Flyway/real PostgreSQL role and
transaction setup plus pure graph builders from the existing publication fixture;
the old suites stay byte-identical. Required Docker absence fails instead of
skipping. Actual asserted operator traffic uses MockMvc with Spring Security,
real native tokens, owner admission and separate ordinary/publisher DB principals.
External directory/source transport remains synthetic. Successful owners are
not replaced by mock replies; targeted spies inject explicit rollback faults.
The interceptor sibling/wrong-tuple negative controls additionally call its
actual bean, with a genuinely decoded native token rather than forged claims.

The source covers four genuine providers; HTTP metadata/graph/survey authoring,
validation and all three editor exchange operations; exact canonical bytes,
decimal scale/unit/null retention, original-vs-target survey graph binding;
unqualified publication and exact history. Two independently authenticated LOCAL
users remain active under the same native provider with distinct company/network
grants and actual A/B station owners. The historical provider-rotation helper
would revoke the first token, so the new independent-user helper does not rotate
providers. Both actors' independent company and network substitutions exercise
all20 tuples, including retained request replay, fixed denials and storage images.
Existing unauthorized owners403 and absent company503 remain distinct assertions.

Further methods assert fresh DENY/revocation after preview, ordinary login and
anonymous negatives, immutable retained snapshots after all editor revisions,
withdraw/reactivate and committed effective boundaries,103-event bounded keyset
history immune to a later append, exact reads, competing HTTP draft and head CAS
with one200/one409 winner and exact row counts. Targeted final-guard invalidation,
managed-dirty flush and exchange UPDATE flush failures assert complete rollback
storage images. This is injected failure proof, not elapsed-time performance or
a physically induced unknown COMMIT. Station owner transfer/ABA, graph incidence,
trigger/function integrity and DB role corruption fail-closed assertions restore
privileged setup mutations in finally. Those tests do not claim an impossible
rewrite of immutable publication ledger data as a physical corruption exercise.

The consumer method requests actual in-process /v3/api-docs and checks the20
typed tuples, bearer security, DTO refs, current error responses and body/query
bounds. The three controllers gain annotations only; method signatures/bodies,
routes, operation IDs and all authorization/error behavior are unchanged.
All existing400/403/404/409/503 responses reference ProblemDetail;401 content is
left unspecified because filter and owner response envelopes differ. Success200
uses its actual return type. Source review additionally found create already
returns201 for a new draft and200 on replay, so an annotation documents that
existing201 with the existing Draft DTO. This corrects documentation without a
new runtime success response. No generated snapshot is inferred from annotations.

Inventory adds only this actual eight-case postgres-integration/fast=false suite
with duration0 explicitly unmeasured. All old entries/weights, four historical
archives, seven DB shards, mode and workflows remain unchanged. Active source
becomes466 classes; actual hosted reports own execution counts and timing.
New source SHA256: 034b65dd4f802bd3a341d1c7cf7f9d6e9f0326cba653f87f688f5b019faf1061. Current deterministic placement: postgres-1.

Available local checks: JDK17 compiler syntax parsing of all four Java files PASS;
documentation-validator19 and inventory-validator32 self-tests PASS. Exact eight
method/case/hash/discovery, unchanged old inventory/shards, canonical header,
controller executable-source equality after removing added annotations and
UpdatedOn, append-only selected-record/status-preservation and six-file scope
checks PASS. No Java21 type resolution, Maven, Spring/Jackson/native/HTTP/JUnit or
PostgreSQL execution is claimed locally; the available environment lacks the
complete runtime toolchain and is not provisioned under AGENTS CI-first policy.

Canonical OpenAPI, manifest, dictionary and schema/owner/executor/security sources
retain their actual prior bytes. The expected first strict snapshot mismatch from
documented responses is a separate artifact gate: use actual hosted generated
outputs, repair responsible runtime failures first, then separately select the
registered three-file artifact reconciliation. Do not weaken equality,
compatibility, inventory or native authority to make the suite pass.
HAPI-592 remains NOT_IMPLEMENTED/IN_PROGRESS pending exact-source hosted evidence
and final registered four-file acceptance. No HAPI-608, frontend implementation,
engineering qualification, release/deployment, simulator/P3 or OT action follows.

Publish with expectedf5459a4 lease; verify exact one-parent/tree/message and
all six blobs (one CREATE/five MODIFY/zero DELETE). Observe applicable exact-SHA
Full/Fast/Documentation CI creation/queue/start and STOP without waiting or
automatically executing artifact reconciliation or acceptance.


#### HAPI-592 NON_NULL qualification-fixture repair — 2026-10-26

Owner Next selects responsible Full905 runtime-fixture repair before the
registered API artifact stage. Source765f050b65688a048a4197b584a9c138f8413d75,
run40282660920 attempt1, PostgreSQL shard1 job121439176272 executes219 cases:
eight failures,zero errors/skips. All eight new qualification methods fail at
the same createHttp:841 description-null assertion immediately after actual
HTTP201 creation. Their downstream workflows have not passed and are not
accepted by this checkpoint. Fast211, Docs602, unit/static and the six other
PostgreSQL shards succeeded. The Full gate remains failed.

Authenticated diagnostics artifact12633148218, ZIP SHA256
bc9aa555a97cd7a69c12c2757d2f82a4b7f62648ac0d8e78500025fe8617977d,
verified on download. Raw TEST-TopologyEngineeringOperatorQualificationTest XML
SHA256 d0132dbc990c80a7c133d7232f8927851c560a015d0e12eb65e8e37ce88b6ca7 contains all eight exact inventory identities with the
same setup failure. Real application.properties configures
spring.jackson.default-property-inclusion=non_null. Nullable response record
values therefore disappear from JSON rather than becoming literal null nodes;
JsonNode.path returns a missing node, so isNull was an invalid fixture assertion.

Correct only that wire-representation assumption and its matching later checks:
description/elevation, advisory targetVersion and withdrawn snapshot must be
absent under current NON_NULL serialization. Add positive title/observedVersion/
WITHDRAWN-head/event assertions so an empty response cannot satisfy absence.
The decoded canonical survey's actual Java null assertion remains unchanged.
An absent final-page nextCursor ends pagination; a present cursor must still be
a nonblank JSON string. Preserve all eight methods, exact HTTP statuses, scope/
authority/rollback/digest/history assertions and required Docker execution.
No conditional skip, expanded acceptable-status set, mock success or changed
production serializer/DTO/controller/error/security contract is introduced.

Exact scope3 MODIFY/zero CREATE/DELETE: this test, only its existing inventory
source hash, and this append-only HAPI-592 evidence. New test source SHA256
bf81d137d272141d27a1d5a8874c990f41cf87384a77fd3997ab496f3ce66e15. Keep registered message
`test(topology): qualify operator API and revision history`.
Java syntax parse with available JDK17, inventory-validator32 and
documentation-validator19 self-tests PASS; source/case/hash/no-skip and exact
three-file history-preserving scope checks PASS. No new local Java21/Maven/
Spring/Jackson/PostgreSQL execution or passing qualification is claimed.

API job121439176328 generated the actual runtime contract and failed strict
canonical equality as expected after annotations. Compatibility/dictionary
acceptance is not inferred from that failed job; the separately registered
generated-artifact stage remains pending after responsible runtime feedback.
Preserve OpenAPI/manifest/dictionary bytes, production source, CI modes/weights,
all other inventory entries and task statuses. Publish under expected765f050
lease, verify exact parent/tree/three blobs, observe applicable exact-SHA
Full/Fast/Docs creation or startup and STOP without waiting or advancing.


#### HAPI-592 observed HTTP qualification repairs — 2026-10-26

Owner Fail selects the responsible Full906 failures at exact source
d0c21a22f5a0848300ecf8a1130da9f188684e87, run40367220120 attempt1,
PostgreSQL shard1 job121728063196. It executes219 cases with3 failures,
zero errors/skips. The new eight-method suite now has five actual passes:
four native-provider editor/exchange/publication roundtrips; DENY/revocation/
ordinary/anonymous/tuple denial; immutable source-edited103-event history;
final-guard/managed-flush rollback; station ownership/ABA/catalog corruption.
The three remaining cases fail at the concrete boundaries below; their later
assertions are not passing evidence. Unit/static and six other DB shards pass;
Full906 and its gate remain failed. Optional serial Maven verification skips.

Authenticated diagnostic artifact12671866957, ZIP SHA256
730506bad2b0a9152611cf7de3042d5f61933dbdf08575d46b1c0e28746f2e8c,
is verified on download. Raw qualification XML SHA256 e1156b4f00cd533eab76ca7dd5aa555bdc642a78f67d29fd0417db233a945c20
retains all eight identities and actual8/3/0/0 counts.

1. The ordinary test profile explicitly disables springdoc.api-docs.enabled;
   /v3/api-docs therefore returns404, not a generated consumer contract. Enable
   that property only in this qualification class's SpringBootTest properties.
   Keep the common test/production configuration and security chain unchanged.
2. The uncoordinated HTTP CAS fixture returned one200 and one503: existing
   repository OPEN takes SHARE ROW EXCLUSIVE NOWAIT relation fences, so a busy
   admission may fail UNAVAILABLE before expected-version comparison. Preserve
   that behavior; do not convert availability into conflict, broaden the
   expected result set or increase transaction/lock/time budgets. Add an explicit
   independent PostgreSQL ROW EXCLUSIVE blocker scenario requiring exact503/
   UNAVAILABLE and unchanged storage. Separately require the existing exact
   one200/one409 CAS results and row counts for draft and publication candidates.
   Both HTTP requests must be in flight (assert peak2), but a fixture-only
   servlet filter orders their admission: the second waits before authentication
   or any owner transaction until the first request commits/returns. Each still
   traverses real Spring Security, HTTP/owner/DB admission. No fence or protected
   budget is held during the wait. This proves stale expected-version/head CAS
   after overlapping HTTP arrival, not simultaneous physical DB-writer execution.
   Preserve the earlier native/owner database concurrency and fence obligations
   as independent evidence; do not inflate this controlled schedule's claim.
3. The full-body non-disclosure assertion found a real scoped draft error gap:
   Spring fills a missing ProblemDetail.instance with the request path, including
   the draft UUID. Keep the strict full-body selector prohibition unchanged.
   Bound the necessary production correction to
   src/main/java/dz/sh/hidra/modules/topology/api/rest/EngineeringNetworkDraftApiExceptionHandler.java:
   set fixed urn:hidra:engineering-draft for native failures and invalid-body
   errors, plus UpdatedOn. All status/reason/detail mappings, handler scope,
   authorization, DTOs and success behavior remain unchanged. No new permissions,
   owner access, broad exception mapping or generic security-response change.
   This explicit demonstrated privacy gap admits only that extra source file
   for this responsible repair; all other production changes remain excluded.

Exact bounded repair4 MODIFY/zero CREATE/DELETE: the qualification test, its
existing inventory source hash only, the scoped draft advice above and this
append-only HAPI-592 record. Reuse registered exact message
`test(topology): qualify operator API and revision history`.
New qualification source SHA256 53ce1cb026178a332dd85770baddeaca19930ab0491260146c7dd3d630e59a28; all eight method/case identities,
all other inventory metadata/classes/weights, seven shards, CI modes and task
statuses remain unchanged. Keep the prior NON_NULL repair and all old evidence.

Available checks: both changed Java sources parse with existing JDK17;
inventory-validator32 and documentation-validator19 self-tests PASS. Exact
case/source-hash/status/no-skip and four-file scope/history checks PASS. Hosted
Java21/Maven/Spring/Jackson/PostgreSQL execution remains authoritative; no new
local runtime pass or HAPI-592 acceptance is claimed. No full local environment
is provisioned under the owner's CI-first policy.

API job121728062231 again generates runtime output and fails strict snapshot
equality after the earlier annotations. Preserve canonical OpenAPI/manifest/
dictionary bytes; use actual hosted artifacts for the separately registered
reconciliation after responsible runtime feedback. No compatibility/catalog
pass is inferred from the failed API lane. Publish this one repair using expected
d0c21a2 lease; verify parent/tree/message/exact4 blobs, observe applicable exact-SHA
Full/Fast/Docs creation/queue/start and STOP. No automatic artifact stage,
acceptance, HAPI-608, frontend implementation, release or deployment follows.

#### HAPI-592 final malformed-body advice repair — 2026-10-26

The owner explicitly selects both current CI repair and shard-load balancing.
Execute one reviewed three-commit sequence (repair, actual API reconciliation,
measured scheduling refresh), preserving separate exact messages/scopes. Advance
main once from1d6025e after verifying all three commits; observe final applicable
CI startup and STOP. This owner request admits timing maintenance despite the
earlier source-delivery no-weight-change constraint; no unrelated feature work.

Full907 run40370015468 attempt1, source1d6025e22fb585e8b1ec6b5ad39a0d65aa3514f3,
shard1 job121737939636:219 cases,one failure,zero errors/skips. Seven of the eight
qualification methods now pass, including actual two-company isolation, explicit
NOWAIT503 and ordered HTTP200/409 CAS. The last consumer case reaches malformed
publication JSON; general Spring ProblemDetail handling responds400 with
request-derived instance before the scoped advice can supply its fixed URI.
Retained diagnostic12672262892 ZIP SHA256
ec51087b5fe3b141daaee05c643db284fecf757e3f68655ed33b932cd2ec5bb0;
raw qualification XML SHA256 f13c320cd3204fd614a243641bc77e2a93a3553fcab0c5a2c4f4c6df5a59ba1d. All diagnostic ZIP digests are checked.

Bound this production correction to the existing publication and draft scoped
ApiExceptionHandler classes: HIGHEST_PRECEDENCE, matching the exchange advice,
plus publication UpdatedOn. Their exact assignable controller scopes and all
exception/status/detail/code/instance mappings remain unchanged. No broad/global
handler, authorization bypass, altered test/status set or serializer change.
Keep the full-body selector rejection intact; no test source or inventory case/
source-hash change. This fixes dispatch precedence for malformed bodies, not
ordinary security policy. Canonical OpenAPI reconciliation follows separately.

Exact3 MODIFY/zero CREATE/DELETE: those two scoped advice files and only this
append-only HAPI-592 record. Registered message remains
`test(topology): qualify operator API and revision history`.
Available Java syntax and51 existing validator self-tests plus scope/history
checks pass before publication; no new local Java21/Maven/Spring/PostgreSQL
execution is claimed. HAPI-592 stays NOT_IMPLEMENTED/IN_PROGRESS pending hosted
complete evidence and later four-file acceptance. No release or deployment.

#### HAPI-592 actual API contract reconciliation — 2026-10-26

Owner's combined CI-failure repair request selects the registered artifact stage,
`docs(topology): reconcile operator qualification API contract`.
Actual Full907 source1d6025e22fb585e8b1ec6b5ad39a0d65aa3514f3,
run40370015468 attempt1, job121737939475: build8 and generation9 succeed;
snapshot equality10 fails; normal dictionary capture11 and later compatibility
steps skip. No Full907 catalog or hosted compatibility pass is claimed.
Artifact12671967466, member openapi/hidra-api.json, ZIP SHA256
7bc1383673dde32a29d783100a8c566324fc40f0261eb3bed8cc5819e8ad9635,
raw JSON SHA256 f7bf39bf1b1d415e964e37d1f4ce4d425e21fdb93bce9947450064167457d546, canonical SHA256 2d48275fc6699c2b028616f0b43f960b13b5ad01fa02f7a9857d31ce52fea651.

Actual269 paths/291 operations/284 schemas. All prior283 schemas, security schemes
and non-engineering paths remain structurally unchanged; ProblemDetail is added.
Twenty engineering operations now document actual400/401/403/404/409/503 responses
and existing create201; success200 uses its retained typed return schema.401
does not promise an owner error envelope. The unchanged comparator passes locally
against actual successful Full904 runtime source4a69624ecc635203fecb404c61933c696708d7f4
from verified aggregate12616595113, not an inferred immediate-parent snapshot.
Strict canonical/source/count/digest/runtime equality checks pass locally.

Exact3 MODIFY/zero CREATE/DELETE: actual captured doc/api/openapi.yaml,
.github/documentation-validation.json openapi object only and this record.
Preserve complete nested prior provenance, actual generated origin stamp and
unchanged physical dictionary/schema bytes. The preceding scoped advice priority
fix changes neither API annotations nor routes/DTO schemas; final hosted equality
still supplies exact-source proof. Do not relabel the captured source to the new
head. Required hosted native/raw-case and normal catalog/API gates remain pending;
no HAPI-592 status, release/deployment or engineering acceptance is inferred.

<a id="hapi-658"></a>
## HAPI-658 — expose operational scope resolution contract

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HRA-091

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Export the Topology scope-resolution contract consumed by Organization.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/repository-remediation.md`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/adapter/AuthoritativeOperationalScopeTargetResolverAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/organization/TopologyOperationalScopeTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/organization/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/adapter/AuthoritativeOperationalScopeTargetResolverAdapterTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/adapter/AuthoritativeOperationalScopeTargetResolverAdapter.java`, `src/test/java/dz/sh/hidra/modules/organization/infrastructure/adapter/AuthoritativeOperationalScopeTargetResolverAdapterTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/repository-remediation.md`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/adapter/AuthoritativeOperationalScopeTargetResolverAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/organization/TopologyOperationalScopeTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/organization/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/adapter/AuthoritativeOperationalScopeTargetResolverAdapterTest.java`

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
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/adapter/AuthoritativeOperationalScopeTargetResolverAdapterTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.

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

- `8d48f28d5008e006ca1969ba526f0b3963d4192a` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/repository-remediation.md:336](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/repository-remediation.md#L336)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/repository-remediation.md:336 | HRA-091 | refactor(topology): expose operational scope resolution contract | Export the Topology scope-resolution contract consumed by Organization. | Organization no longer imports Topology's internal application package. | HRA-002 | **Completed** — relocated the topology-owned operational-scope resolver into the deliberate `topology.application.contract.organization.TopologyOperationalScopeTargetContract`, migrated the Topology implementation plus Organization adapter/tests, deleted the obsolete private `port.in` contract, removed the final transitional cross-module allowlist entry, and updated ArchUnit to permit only the exported contract package.
~~~~~~~~

<a id="hapi-666"></a>
## HAPI-666 — remove residual projection vocabulary

Phase: HPS-004 — Physical Network Topology

Legacy IDs: HRA-122

Priority: P1

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Delete only the HRA-120-classified zero-incoming topology.domain.value.ProjectionType enum. Re-run static incoming-reference search immediately before deletion; if a live Java/configuration/serialization/reflection consumer appears, stop and reclassify instead of deleting. Update the orphan-classification and forensic closure evidence/guardrail to record the removal. Do not change the unrelated Analytics projectionType string field or any analytics persistence/API contract merely because it shares the word “projection”.

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/architecture/forensic-remediation-closure.md`
- `docs/architecture/static-orphan-classification.md`
- `docs/roadmap/repository-remediation.md`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/architecture/forensic-remediation-closure.md`
- `docs/architecture/static-orphan-classification.md`
- `docs/roadmap/repository-remediation.md`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## HRA-122 corrective follow-up

HRA-122 removes the separately classified Topology residual without broadening into Analytics or
other projection-related concepts.

Exact-head revalidation before deletion confirmed that
`dz.sh.hidra.modules.topology.domain.value.ProjectionType` had no Java, configuration,
serialization, or reflection consumer. The complete enum vocabulary also appeared nowhere else.
The unrelated Analytics `projectionType` field remains unchanged and continues to be owned by the
Analytics module as string state.

`ProjectionType.java` is now removed, and `ForensicRemediationClosureTest` verifies that the
source file stays absent while the orphan-classification evidence records
`DELETE_RESIDUAL → REMOVED_HRA_122`.

After HRA-121 and HRA-122, both concrete blockers found by the 2026-09-29 HRA-120 replay have been
corrected. **HRA-120 remains BLOCKED only pending a fresh replay of the same forensic methodology
against the new live head.** This HRA-122 task does not itself claim forensic closure.


~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.

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

- `d6d1454b816b819bc90c2c4ef3e382a59cd6d4c0` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/architecture/forensic-remediation-closure.md:164](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/architecture/forensic-remediation-closure.md#L164)
- [docs/architecture/static-orphan-classification.md:205](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/architecture/static-orphan-classification.md#L205)
- [docs/roadmap/repository-remediation.md:372](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/repository-remediation.md#L372)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/repository-remediation.md:372 | HRA-122 | refactor(topology): remove residual projection vocabulary | Delete only the HRA-120-classified zero-incoming `topology.domain.value.ProjectionType` enum. Re-run static incoming-reference search immediately before deletion; if a live Java/configuration/serialization/reflection consumer appears, stop and reclassify instead of deleting. Update the orphan-classification and forensic closure evidence/guardrail to record the removal. Do not change the unrelated Analytics `projectionType` string field or any analytics persistence/API contract merely because it shares the word “projection”. | ProjectionType.java` is absent; no production/test source imports or references the deleted Topology enum; Analytics projection fields/contracts are unchanged; static-orphan evidence records the classified residual as removed; repository validation passes. | HRA-120 `DELETE_RESIDUAL` evidence | **Completed** — exact-head revalidation on `9d62c46e1a14b1f866f6d48f565322411fb3d960` found no Java/configuration/serialization/reflection consumer of the Topology enum and no reuse of its complete vocabulary outside its own declaration. `ProjectionType.java` was deleted; orphan and forensic evidence record `DELETE_RESIDUAL → REMOVED_HRA_122`; the closure guardrail now asserts the file remains absent. Unrelated Analytics `projectionType` string state/contracts were not changed.
~~~~~~~~

<a id="hapi-871"></a>
## HAPI-871 — add topology implementation roadmap

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-001

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology implementation roadmap

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

Canonical prerequisites: —. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/topology.md:70](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L70)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:70 | TOP-001 | docs(topology): add topology implementation roadmap | Completed | Initial topology roadmap.
~~~~~~~~

<a id="hapi-872"></a>
## HAPI-872 — add topology package skeleton

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-002

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology package skeleton

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:71](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L71)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:71 | TOP-002 | chore(topology): add topology package skeleton | Completed | Production package skeleton.
~~~~~~~~

<a id="hapi-873"></a>
## HAPI-873 — add topology domain value objects

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-003

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology domain value objects

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:72](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L72)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:72 | TOP-003 | feat(topology): add topology domain value objects | Completed | Domain identifiers, values, and initial controlled vocabularies.
~~~~~~~~

<a id="hapi-874"></a>
## HAPI-874 — add topology domain models

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-004

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology domain models

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:73](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L73)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:73 | TOP-004 | feat(topology): add topology domain models | Completed | Physical topology aggregates/entities.
~~~~~~~~

<a id="hapi-875"></a>
## HAPI-875 — add topology domain policies

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-005

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology domain policies

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:74](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L74)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:74 | TOP-005 | feat(topology): add topology domain policies | Completed | Domain validation policies.
~~~~~~~~

<a id="hapi-876"></a>
## HAPI-876 — add topology domain services

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-006

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology domain services

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:75](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L75)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:75 | TOP-006 | feat(topology): add topology domain services | Completed | Domain orchestration services.
~~~~~~~~

<a id="hapi-877"></a>
## HAPI-877 — add topology application commands and queries

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-007

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology application commands and queries

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:76](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L76)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:76 | TOP-007 | feat(topology): add topology application commands and queries | Completed | Application input records.
~~~~~~~~

<a id="hapi-878"></a>
## HAPI-878 — add topology application DTOs

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-008

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology application DTOs

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:77](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L77)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:77 | TOP-008 | feat(topology): add topology application DTOs | Completed | Application output records.
~~~~~~~~

<a id="hapi-879"></a>
## HAPI-879 — add topology application ports

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-009

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology application ports

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:78](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L78)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:78 | TOP-009 | feat(topology): add topology application ports | Completed | Inbound and outbound ports.
~~~~~~~~

<a id="hapi-880"></a>
## HAPI-880 — add topology application services

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-010

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology application services

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:79](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L79)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:79 | TOP-010 | feat(topology): add topology application services | Completed | Application services and wiring.
~~~~~~~~

<a id="hapi-881"></a>
## HAPI-881 — add topology persistence entities and repositories

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-011

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology persistence entities and repositories

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:80](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L80)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:80 | TOP-011 | feat(topology): add topology persistence entities and repositories | Completed | JPA entities and Spring Data repository interfaces.
~~~~~~~~

<a id="hapi-882"></a>
## HAPI-882 — add topology persistence mapper and adapters

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-012

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology persistence mapper and adapters

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:81](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L81)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:81 | TOP-012 | feat(topology): add topology persistence mapper and adapters | Completed | Persistence mapper and outbound adapters.
~~~~~~~~

<a id="hapi-883"></a>
## HAPI-883 — add topology flyway migration

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-013

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology flyway migration

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:82](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L82)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:82 | TOP-013 | db(topology): add topology flyway migration | Completed | V002__create_topology_tables.sql`.
~~~~~~~~

<a id="hapi-884"></a>
## HAPI-884 — add topology REST request DTOs

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-014

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology REST request DTOs

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:83](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L83)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:83 | TOP-014 | feat(topology): add topology REST request DTOs | Completed | Request DTO contracts.
~~~~~~~~

<a id="hapi-885"></a>
## HAPI-885 — add topology REST response DTOs

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-015

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology REST response DTOs

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:84](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L84)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:84 | TOP-015 | feat(topology): add topology REST response DTOs | Completed | Response DTO contracts.
~~~~~~~~

<a id="hapi-886"></a>
## HAPI-886 — add topology REST mapper

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-016

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology REST mapper

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:85](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L85)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:85 | TOP-016 | feat(topology): add topology REST mapper | Completed | API mapper and mapper bean configuration.
~~~~~~~~

<a id="hapi-887"></a>
## HAPI-887 — add topology REST controllers

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-017

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology REST controllers

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:86](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L86)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:86 | TOP-017 | feat(topology): add topology REST controllers | Completed | REST controllers for implemented topology use cases.
~~~~~~~~

<a id="hapi-888"></a>
## HAPI-888 — add topology domain tests

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-018

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology domain tests

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:87](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L87)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:87 | TOP-018 | test(topology): add topology domain tests | Completed | Domain unit tests.
~~~~~~~~

<a id="hapi-889"></a>
## HAPI-889 — add topology application service tests

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-019

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology application service tests

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:88](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L88)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:88 | TOP-019 | test(topology): add topology application service tests | Completed | Application service unit tests.
~~~~~~~~

<a id="hapi-890"></a>
## HAPI-890 — add topology persistence tests

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-020

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology persistence tests

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:89](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L89)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:89 | TOP-020 | test(topology): add topology persistence tests | Completed | Persistence mapper/entity/repository/adapter tests.
~~~~~~~~

<a id="hapi-891"></a>
## HAPI-891 — add topology REST mapper and controller tests

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-021

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology REST mapper and controller tests

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:90](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L90)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:90 | TOP-021 | test(topology): add topology REST mapper and controller tests | Completed | REST mapper/controller tests.
~~~~~~~~

<a id="hapi-892"></a>
## HAPI-892 — add topology application boot smoke test

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-022

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add topology application boot smoke test

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/topology.md:91](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L91)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:91 | TOP-022 | test(topology): add topology application boot smoke test | Completed | Spring Boot + PostgreSQL Testcontainers topology smoke test.
~~~~~~~~

<a id="hapi-893"></a>
## HAPI-893 — finalize topology validation checklist

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-023

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

finalize topology validation checklist

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

Canonical prerequisites: —. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/topology.md:92](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L92)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/topology.md:92 | TOP-023 | docs(topology): finalize topology validation checklist | Completed | docs/roadmap/topology_validation_checklist.md`.
~~~~~~~~

<a id="hapi-894"></a>
## HAPI-894 — TOP-COR-001 historical bounded obligation

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-COR-001

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

TOP-COR-001 historical bounded obligation

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/fixs/telemetry_corrected_baseline_validated.md:44](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/telemetry_corrected_baseline_validated.md#L44)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/telemetry_corrected_baseline_validated.md:44 | TOP-COR-001 | topology | Superseded | @Primary` alone did not remove concrete application-service ambiguity.
~~~~~~~~

<a id="hapi-895"></a>
## HAPI-895 — TOP-COR-002 historical bounded obligation

Phase: HPS-004 — Physical Network Topology

Legacy IDs: TOP-COR-002

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

TOP-COR-002 historical bounded obligation

### 2. Existing Implementation

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. A map is a representation of stored facts; it is not a full engineering authoring contract. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- [docs/roadmap/fixs/telemetry_corrected_baseline_validated.md:45](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/telemetry_corrected_baseline_validated.md#L45)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/telemetry_corrected_baseline_validated.md:45 | TOP-COR-002 | topology | Accepted | Explicit `@Qualifier(...)` annotations on topology use-case factory parameters remove ambiguity.
~~~~~~~~

<!-- task-register:payload:end -->

### HAPI-588B CI-first implementation checkpoint — 2026-10-20

**Registered stage:** HAPI-588 / HPR-P25-052B Package B, commit message `feat(topology): persist protected engineering graph draft versions`. This CI-FIRST source delivery includes 12 CREATE paths, 18 MODIFY paths, additive `V20261019_002`, bounded typed graph DTOs and API, the same engineering draft aggregate and shared monotonic metadata/graph version, native READ/UPDATE authority, 34 graph-specific physical fences with the existing independent 31-fence metadata path, owner-generation station witnesses, pinned provisional SQL templates, single physical final guard, and tracked synthetic/PG regression sources. The table ownership and 429-class test inventory are source-hash pinned. Engineering graph remains DRAFT-only: no installed station membership, numerical solver, hydraulic qualification, operational publication or control.

**Truthful gate:** The source review and exact-anchor checks are not Java21 full Maven/Spring Boot, real JWT, PostgreSQL catalog/privilege/tamper, concurrency, or exact-SHA CI acceptance. Those cases must actually execute and pass. Runtime OpenAPI and physical PostgreSQL dictionary regeneration remain reserved for later Package C, not silently fabricated here. Preserve `HAPI-588 PARTIAL / REGISTERED_PENDING` and unchanged frozen ROADMAP/PHASES statuses until final registered acceptance. After publication verify commit parent/tree and observe Full/Fast/Docs CI created or started on the exact new SHA; STOP.

### HAPI-588B — exact-SHA CI failure repair checkpoint (2026-10-20)

**Observed failure evidence:** committed B parent `06a807e6230975dfe8b6af9bc21643196bbf2147`, Full CI #767 [run 39373274909](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39373274909) and Fast Feedback #92 [run 39373274875](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39373274875) FAILED; Documentation #441 [run 39373274962](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39373274962) PASSED. The failing logs show Spring AOP could not subclass a final graph JDBC repository, HRA-102 REST DTO imports of domain graph records, unrecognized strict nested JSON triggering a null mock result in the controller regression, and an existing protected-station admission caller inventory not yet including two newly reviewed graph adapters. Full CI's API-contract startup and PostgreSQL lanes also failed with the Spring context error; no successful PostgreSQL proof may be inferred from the failure.

**Selected Fail repair only:** remove `final` from both reviewed graph JDBC adapter types (preserving their protected owner/transaction checks); move all graph-to-domain construction out of REST request DTOs into the existing draft controller, keep graph DTO response classes free of domain imports with explicit transport records, and require strict object/field-type/unknown-property JSON checks before any UPDATE application command. Extend the protected-station guardrail's **requireAdmission-only** exact caller list for those two graph adapters without expanding begin/seal/reject authority. This CI-owner repair also changes `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java` outside the original Package B path inventory because that existing owner-fence test is the direct failed CI case; no wider test allowlist, security bypass, or production module change is authorized. Extend registered graph boundary/HTTP regression cases and update the three changed test-class SHA-256 digests/case inventories in `.github/ci-test-inventory.json`.

**Acceptance remains pending:** this is a CI-first repair of implementation errors, not proof of running Maven locally, success in PostgreSQL, complete graph operations or operational readiness. Exact-SHA Full/Fast/Docs must start after the guarded single repair commit, then their actual results must be inspected before proceeding to B acceptance or C. Keep HAPI-588 PARTIAL / REGISTERED_PENDING; do not modify the frozen roadmap or phase statuses.

### HAPI-588B compiler repair — 2026-10-20

**Exact-head evidence:** main `4ab2ddc7043b87da98817d364f47d65fc1557bfc` failed Full CI #768 [run 39380635910](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39380635910) and Fast Feedback #93 [run 39380635885](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39380635885); Documentation #442 [run 39380635907](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39380635907) passed. Java-bearing lanes stop at EngineeringNetworkDraftController.java:69 with a missing closing parenthesis in the graph port stream mapping. No runtime or PostgreSQL acceptance can be inferred.

**Bounded repair:** close the port mapper before collecting its list and restore the controller's canonical Java header, removing the stale unpublished-candidate comment. Only `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringNetworkDraftController.java` and this phase evidence file change. Preserve all mapping semantics, security fences, migrations, test sources, inventories and generated artifacts. Reuse the registered Package B commit message `feat(topology): persist protected engineering graph draft versions`.

**Actual local validation:** the available OpenJDK 17 compiler parser reproduced the original `')' expected` error and parsed the repaired controller without syntax diagnostics. This is syntax-only evidence: Maven is unavailable locally, and Java21 compilation, tests, Spring/API and PostgreSQL checks remain delegated to hosted CI under AGENTS.md section 11.1. HAPI-588 remains PARTIAL / REGISTERED_PENDING; frozen roadmap/phase statuses remain unchanged. Publish with expected-main guard, confirm Full/Fast/Docs creation or startup on the exact new SHA, then STOP. Inspect those actual results before any further B repair/acceptance or Package C.

### HAPI-588B Jackson/runtime-artifact failure repair and diagnostic checkpoint — 2026-10-20

**Exact failed head:** `5f21de85ed9320916ee545f28192aeb8516df27e`, parent `4ab2ddc7043b87da98817d364f47d65fc1557bfc`, tree `ef79e1f4612e7dbd6598596565aae31af0410ece`. Full #769 [run39386968386](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39386968386) and Fast #94 [run39386968106](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39386968106) FAILED; Docs #443 [run39386968042](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39386968042) PASSED. Compilation and application startup succeeded. PostgreSQL shard0 succeeded. Fast/unit/oracle report the same malformed-JSON case reaching an unstubbed graph command; graph PostgreSQL positive/history/fence/concurrency cases fail with 503/UNAVAILABLE. Shard1 additionally reports an Identity fixture initialization rejection not reproduced by the same-run oracle; that remains an unresolved observation, not a reason to weaken production authentication.

**Source-backed JSON repair:** the active Spring MVC dependency is tools.jackson databind3.1.5, whereas the graph DTO's custom deserializers/annotation used com.fasterxml Jackson2 and were ignored. Move the strict whole-request deserializer to Jackson3 ValueDeserializer/JsonDeserialize, use context.readTree, propertyNames and strict scalar inspection; remove redundant Jackson2 scalar deserializers. Preserve typed DTOs, every existing strict field check and domain conversion at the controller boundary. Extend the existing malformed-HTTP case with overflowing/null versions and non-array input. No mock response is added to hide malformed input reaching the application.

**Unresolved graph transaction cause:** existing translation discards the underlying protected-execution exception. Preserve it as an internal Java cause while retaining the fixed public reason/message and existing HTTP ProblemDetail mapping. The no-retry service regression now asserts cause retention and unchanged public error text. No graph transaction root-cause repair or green PostgreSQL evidence is claimed at this checkpoint. Keep all34 relation fences,500ms work limit, exact SQL pins, role checks and final guard intact; inspect the next actual CI cause before further repair. No speculative guard widening, retries or failure exclusions.

**Actual generated artifact repair:** recover artifact12247149549, ZIP SHA256 `8068c3274aaae5259945869583b6e88722d936edd59120fa49724adecd2a400c`, from Full769 attempt1 API job118509010097. Named generation step7 succeeded; overall job/workflow failed. Preserve that distinction in the manifest. Canonical OpenAPI248 paths/269 operations/244 schemas retains every prior path item/schema and adds the generated graph route/two operations/eight schemas. No invented contract bytes. Snapshot/manifest/governance repair is admitted by the owner's Fail instruction to remove actual equality failure and allow hosted dictionary capture. This is not Package C execution/completion; physical catalog generation was skipped after equality failed, so the truthful prior dictionary remains pending.

**Exact MODIFY scope (9 paths), registered B message reused:** `feat(topology): persist protected engineering graph draft versions`.
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/EngineeringGraphDraftRequests.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringNetworkDraftApplicationService.java`
- `src/test/java/dz/sh/hidra/modules/topology/api/rest/EngineeringGraphDraftControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/EngineeringGraphDraftApplicationServiceTest.java`
- `.github/ci-test-inventory.json` (only the two changed test source SHA256 values; cases/lanes unchanged)
- `doc/api/openapi.yaml`
- `.github/documentation-validation.json` (OpenAPI evidence only)
- `doc/api/OPENAPI_GOVERNANCE.md`
- this phase task record.

**Actual local checks:** available OpenJDK17 parser accepted all four changed Java files; syntax-only evidence, not Java21 type checking or runtime tests. Strict OpenAPI validation and equality to the retained CI-generated object passed; every old path/schema object is unchanged. All10 snapshot-validator unit tests passed. Test-source hash and artifact digest checks are exact. Maven/Java21/PostgreSQL runtime execution is not claimed locally. HAPI-588 remains PARTIAL/REGISTERED_PENDING, frozen roadmap/phase statuses unchanged, Package C/full acceptance pending. Guard publication against the failed main SHA; verify parent/tree/scope and exact-SHA Full/Fast/Docs creation/startup, then STOP without waiting.

### HAPI-588B generation-fence privilege and physical dictionary repair — 2026-10-20

**Exact-head feedback:** `063fedd2a4e5a94d90999cd34177998c72176ef5`, parent `5f21de85ed9320916ee545f28192aeb8516df27e`, tree `cb3cbb6e191e4618fbd85e7e10fa592422e78e8a`. Fast #95 [run39396712398](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39396712398) and Docs #444 [run39396712429](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39396712429) PASSED. Full #770 [run39396712608](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39396712608) FAILED: static/unit/PostgreSQL shard0 succeeded; shard1 and oracle independently show the same graph transaction root cause. Retained exception cause is PostgreSQL permission denial for `LOCK TABLE public.hidra_topology_facility_owner_generation IN SHARE ROW EXCLUSIVE MODE NOWAIT`. The runtime role intentionally has SELECT only on the owner-issued generation table. API generation and runtime OpenAPI equality succeeded; physical dictionary check then failed on real drift.

**Bounded lock repair, no privilege expansion:** align graph admission with the accepted `JdbcStationProtectedFacilityOwnershipAdapter`: retain SHARE ROW EXCLUSIVE on the facility master (blocking its DML and therefore its owner-generation trigger writes) and ACCESS SHARE on the generation registry (SELECT-only; protects relation identity/DDL). The historical `V20261018_002` SECURITY DEFINER owner trigger remains the authorized generation writer. Do not grant UPDATE to the publication role to satisfy LOCK; PostgreSQL16 permits SELECT-only actors only ACCESS SHARE. Add graph catalog rejection of table/column mutation privileges on the generation registry, checked both at admission and in the final scalar guard. Preserve all34 distinct relations, their existing acquisition order, NOWAIT, physical PID/XID/time, exact DML pins, one final guard,500ms budget and no retries. Only this graph generation-table mode changes: exact graph inventory is now30 SHARE_ROW_EXCLUSIVE +3 ACCESS_SHARE +1 SHARE; original metadata31-relation path remains28 +2 +1. This source-backed correction supersedes only the erroneous graph mode/count assumption, not the34-relation requirement or owner witness contract.

**Regression scope:** strengthen the existing34-fence PostgreSQL test to assert the exact master and generation modes. Add `graphStationRoundTripRequiresReadOnlyGenerationAuthority`: actual station graph append/read under SELECT-only generation authority, unchanged generation value, deliberate UPDATE grant rejected by catalog checks, revoke and successful read recovery. Existing positive bearer/history/concurrency tests remain required and unchanged. Inventory now contains22 exact cases for this class and its actual updated SHA256; no case exclusion, skip, lane or support-policy change. Hosted PostgreSQL execution of the repair remains pending.

**Physical catalog artifact repair:** API job118541134410, attempt1, captured actual migrated `database/schema.json` before strict dictionary equality rejected the stale document. Recovered artifact12252042346, ZIP SHA256 `a1f902e3b558fd8f6017bbabcb77d43c6c8f18b74f9376e80c94e217aacbb45f`. Canonical catalog SHA256 `d66f6f0e8a79263c0ac4100c1ee36216941b1c8b2983a28bf561bd22cddd6abd`; source bundle `9dfd75ade23bb7b2162527345f209ab0173c53ec4eb1ba8054647cbd4bdc2e47`. Recovered all626 source files plus reviewed ownership evidence at the exact capture SHA; the unchanged generator validated every migration/JPA hash and mapping against the catalog artifact. Rendered dictionary SHA256 `8bf3075df6d5c78004b492ac5f77f76ad95c74bfc740c80c3ad1861c2651b517`, PostgreSQL16.15,156 migrations/470 JPA mappings/501 relations/zero unresolved owners. Strict `--input ... --ownership ... --check doc/database/DATA_DICTIONARY.md` passed. The source SHA identifies the actual capture, not this consumer commit. No physical schema values or source evidence are invented; no migration or mapping changes occur. The capture and local render do not convert the failed workflow to success or establish graph acceptance. Package C/acceptance remains pending exact-SHA hosted qualification.

**Registered repair scope and message:** reuse B `feat(topology): persist protected engineering graph draft versions`. MODIFY exactly5:
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringNetworkDraftRepositoryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/EngineeringNetworkDraftPostgresIntegrationTest.java`
- `.github/ci-test-inventory.json` (this class's source digest and one real regression identity only)
- `doc/database/DATA_DICTIONARY.md` (actual artifact-based regeneration admitted by the owner's Fail instruction)
- this phase evidence record.

**Actual validation:** available OpenJDK17 parser accepts the changed Java files (syntax only);17 dictionary-generator tests pass; strict artifact/source inventory validation and rendered dictionary equality pass; exact22 test identities and source hash match inventory. Maven/Java21/Spring/PostgreSQL runtime tests were not run locally. Same-run noisy shutdown/connection-refused messages do not justify unrelated Identity changes; previous isolated Identity initialization error did not recur as a failed case. HAPI-588 remains PARTIAL/REGISTERED_PENDING, frozen ROADMAP/PHASES unchanged. Guard publication against this failed main SHA, verify exact parent/tree/five-file scope, observe Full/Fast/Docs created or started on the new SHA, then STOP. Require actual hosted results before further repair, C completion or acceptance.

### HAPI-588C generated evidence reconciliation — 2026-10-20; acceptance pending

**Selected registered stage:** HAPI-588 / HPR-P25-052B Package C, exact message `docs(topology): reconcile engineering graph authoring artifacts`. Owner Next selects this single package after live B implementation `e8f4f631ff45d75dbd761ee4372453e1f76721fe` (parent `063fedd2a4e5a94d90999cd34177998c72176ef5`, tree `919858827f359152ebd58c3468c36d43fcf78566`) passed Full #771 [run39400041135](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39400041135), Fast #96 [run39400041256](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39400041256), and Docs #445 [run39400041231](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39400041231). Implementation feedback is green; formal task acceptance remains independently pending. Frozen ROADMAP/PHASES and HAPI-588 PARTIAL / REGISTERED_PENDING remain unchanged.

**Exact5 MODIFY scope; no CREATE/DELETE:** `doc/api/openapi.yaml`, `.github/documentation-validation.json`, `doc/api/OPENAPI_GOVERNANCE.md`, `doc/database/DATA_DICTIONARY.md`, and this phase record. No runtime source, migration, test, CI workflow, inventory, security grant or caller allowlist changes.

**Authenticated hosted evidence:** recovered full bundle artifact12255170844, run39400041135/attempt1, ZIP SHA256 `2f3e80bea58775cf4980dd65873c0956603b21bf9cc5a937123e3db7b56e3622`, after all seven live jobs and Full Verification Gate succeeded. API job118552180381; oracle118552179948; unit118552180492; PostgreSQL shard0 job118552180596 and shard1 job118552180536; static118552180350; gate118559358088. All six lane receipt digests and every retained file digest were checked against the aggregate, including source SHA/run/attempt/inventory binding. Raw reports were reparsed and compared with exact inventory cases: unit1683 cases/331 classes, shard0 243/37, shard1 629/57; oracle2555/425, no failures/errors/skips, exact combined partition/oracle case equivalence. Inventory SHA256 `200601f2b7f409be4640e118f6115a06a86b1d58764203f80af331d1fe00ae53`. This retained receipt is still `shadow-fallback-required`, optimized-only adoption ineligible; no CI policy is changed.

**Successful generator provenance:** API step7 OpenAPI generation, step8 snapshot equality, step9 migrated catalog/dictionary capture, and step13 supported backward compatibility succeeded. API and oracle generated byte-identical API/catalog/dictionary artifacts. The manifest now points to the successful B SHA and authenticated aggregate member, replacing the stale failed-run generation pointer while preserving its historical explanation. Generated API member SHA256 `1fed7799a2ad3e5d74e04f1184b1471936625273d00b0dbf1511628fa56b592f`; canonical snapshot SHA256 `c06cac659168aa5c433b72eb57bc2c76367fc083fb6c8791cee2c2f814247461`;248 paths/269 operations/244 schemas. Strict runtime semantic equality to the old snapshot passes after removing only its capture SHA; no path/schema/security object or array order is changed. Supported compatibility passes against actual generated base `2702a010bdb659a9a238672a90b7e2b2214fd2bf`.

**Physical catalog reconciliation:** aggregate API member catalog SHA256 `101d9e2b1e5b959ab8d541e03e87ff2ce09ddc5ac6c079064ce7b965d74d2cca`; migration/JPA source bundle `9dfd75ade23bb7b2162527345f209ab0173c53ec4eb1ba8054647cbd4bdc2e47`. Reused the626 recovered migration/mapping sources and reviewed ownership evidence, revalidated by the unchanged generator against this B artifact:156 migrations,470 JPA mappings,501 relations, zero unresolved owners; PostgreSQL16.15. Regenerated dictionary SHA256 `bf652f851ad9885925bbc44496f5758cc616fc9783f84a3b1d4f8dc6e6d9d9aa`. Local output is byte-identical to the hosted successful output; strict --check passes. Only the validated capture SHA line differs from the former reviewed dictionary. No physical/schema/ownership fact is invented or inferred from an unavailable catalog.

**Validation actually performed locally:** bundle ZIP/file/receipt hashes, raw report parsing and exact partition/oracle equivalence; current source SHA256 checks for the13 task-related test classes; strict snapshot equality; supported OpenAPI compatibility; unchanged dictionary generator source/catalog validation, regeneration, hosted byte equality and strict dictionary equality. These consume actual successful hosted Java21/Maven/PostgreSQL results; no fresh local Java21/Maven/PostgreSQL execution is claimed.

**Exact task-related test identities (all132 in13 classes):** each source SHA256 below was matched to the current GitHub file and reviewed inventory; every case is present and passing in the authenticated oracle reports and its corresponding partition. These are actual cases, not proposed coverage or substituted proof.

`dz.sh.hidra.modules.topology.EngineeringGraphDraftBoundaryGuardrailTest` — source `src/test/java/dz/sh/hidra/modules/topology/EngineeringGraphDraftBoundaryGuardrailTest.java`; SHA256 `605c0781475f46421dd2cda3d730f8c920593a4df479d887a9b70cefa7ef6e04`:

- `dz.sh.hidra.modules.topology.EngineeringGraphDraftBoundaryGuardrailTest#adaptersImplementPortsInsteadOfOwningDomainAuthority`
- `dz.sh.hidra.modules.topology.EngineeringGraphDraftBoundaryGuardrailTest#exactOwnedControllerRouteTuplesMustRemainNarrowlyBound`
- `dz.sh.hidra.modules.topology.EngineeringGraphDraftBoundaryGuardrailTest#graphRestResponsesMustUseTransportOwnedRecords`
- `dz.sh.hidra.modules.topology.EngineeringGraphDraftBoundaryGuardrailTest#reviewedGraphRepositoryBeansMustRemainProxyableAndTransportDtosRemainDomainFree`
- `dz.sh.hidra.modules.topology.EngineeringGraphDraftBoundaryGuardrailTest#theOneExistingEngineeringDraftAggregateMustImplementBothUseCases`

`dz.sh.hidra.modules.topology.EngineeringNetworkDraftBoundaryGuardrailTest` — source `src/test/java/dz/sh/hidra/modules/topology/EngineeringNetworkDraftBoundaryGuardrailTest.java`; SHA256 `e53d56337bbfb4079f8fbbb9f23e11fcf909c71e6105c9bb6dca46512d74f198`:

- `dz.sh.hidra.modules.topology.EngineeringNetworkDraftBoundaryGuardrailTest#domainAndContractsRemainFreeOfForeignPersistenceAndFrameworks`
- `dz.sh.hidra.modules.topology.EngineeringNetworkDraftBoundaryGuardrailTest#finiteDraftSqlHashesAndPlatformIssuerNamesStayBound`
- `dz.sh.hidra.modules.topology.EngineeringNetworkDraftBoundaryGuardrailTest#graphMutationRetainsExactTwoStatementPinsAndPureOwnerBoundary`
- `dz.sh.hidra.modules.topology.EngineeringNetworkDraftBoundaryGuardrailTest#networkPairCannotBeConstructedOrRetaggedByCallers`

`dz.sh.hidra.modules.topology.ProtectedStationAdmissionBoundaryGuardrailTest` — source `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java`; SHA256 `9c4f6398e862c3caa861848b43b3cdb31cba2b4a32d159670f30b6261b88411b`:

- `dz.sh.hidra.modules.topology.ProtectedStationAdmissionBoundaryGuardrailTest#admissionCredentialsHaveNoPublicConstructorOrScalarFactory`
- `dz.sh.hidra.modules.topology.ProtectedStationAdmissionBoundaryGuardrailTest#coordinatorUsesOnlyApprovedOwnerContractsAndTechnicalMechanics`
- `dz.sh.hidra.modules.topology.ProtectedStationAdmissionBoundaryGuardrailTest#existingReaderAndJContractsRemainUnchanged`
- `dz.sh.hidra.modules.topology.ProtectedStationAdmissionBoundaryGuardrailTest#identityAdmissionAndCollectorHaveOnlyReviewedCallers`
- `dz.sh.hidra.modules.topology.ProtectedStationAdmissionBoundaryGuardrailTest#platformHasNoBusinessModuleDependencies`

`dz.sh.hidra.modules.topology.api.rest.EngineeringGraphDraftControllerTest` — source `src/test/java/dz/sh/hidra/modules/topology/api/rest/EngineeringGraphDraftControllerTest.java`; SHA256 `a48e5d801f38d8afa2e432dbfebb452258b7daf0598e6807120cdd2d6eaabc98`:

- `dz.sh.hidra.modules.topology.api.rest.EngineeringGraphDraftControllerTest#graphReadDefaultsToCurrentAndSupportsExplicitHistoricalVersion`
- `dz.sh.hidra.modules.topology.api.rest.EngineeringGraphDraftControllerTest#graphResponseSerializesExplicitNodePortBranchDtos`
- `dz.sh.hidra.modules.topology.api.rest.EngineeringGraphDraftControllerTest#graphUpdateRejectsScalarCoercionAndUnknownNestedProperties`
- `dz.sh.hidra.modules.topology.api.rest.EngineeringGraphDraftControllerTest#invalidRequestedVersionFailsBeforeGraphOwner`
- `dz.sh.hidra.modules.topology.api.rest.EngineeringGraphDraftControllerTest#nullNestedGraphElementsAreMalformedRatherThanAnUncaughtServerFailure`
- `dz.sh.hidra.modules.topology.api.rest.EngineeringGraphDraftControllerTest#updatePassesImmutableTypedGraphAndReturnsDraftOnlyResult`
- `dz.sh.hidra.modules.topology.api.rest.EngineeringGraphDraftControllerTest#updateRequiresExpectedVersionAndRejectsUnsupportedGraphShape`

`dz.sh.hidra.modules.topology.api.rest.EngineeringNetworkDraftControllerTest` — source `src/test/java/dz/sh/hidra/modules/topology/api/rest/EngineeringNetworkDraftControllerTest.java`; SHA256 `1ee6f9247f7496008d3c5a3cc8820dc621794a05a0a77b7181a828abb4edff6e`:

- `dz.sh.hidra.modules.topology.api.rest.EngineeringNetworkDraftControllerTest#createDistinguishesNewReceiptAndFreshlyAuthorizedReplay`
- `dz.sh.hidra.modules.topology.api.rest.EngineeringNetworkDraftControllerTest#metadataOnlyStandaloneControllerNeverOpensGraphAuthorization`
- `dz.sh.hidra.modules.topology.api.rest.EngineeringNetworkDraftControllerTest#recognizedFailuresHaveSafeNonDisclosingStatus`
- `dz.sh.hidra.modules.topology.api.rest.EngineeringNetworkDraftControllerTest#rejectsOwnershipGraphUnknownFieldsAndMissingVersion`

`dz.sh.hidra.modules.topology.application.service.EngineeringGraphDraftApplicationServiceTest` — source `src/test/java/dz/sh/hidra/modules/topology/application/service/EngineeringGraphDraftApplicationServiceTest.java`; SHA256 `44fd86881757fde4c824b4c521dfc008a4582253f0b4439de667fafe009e8963`:

- `dz.sh.hidra.modules.topology.application.service.EngineeringGraphDraftApplicationServiceTest#ambientTransactionDoesNotEvenPrepareGraphAuthority`
- `dz.sh.hidra.modules.topology.application.service.EngineeringGraphDraftApplicationServiceTest#graphAdapterCannotReplaceTheAuthoredGraphWithoutFailingTheProtectedWork`
- `dz.sh.hidra.modules.topology.application.service.EngineeringGraphDraftApplicationServiceTest#graphPutCopiesMetadataAndAppendsSnapshotWithinSameProtectedWork`
- `dz.sh.hidra.modules.topology.application.service.EngineeringGraphDraftApplicationServiceTest#historicalGraphReadDoesNotCreateAnotherMetadataOrGraphVersion`
- `dz.sh.hidra.modules.topology.application.service.EngineeringGraphDraftApplicationServiceTest#invalidCurrentStationWitnessCannotOpenGraphProvisionalWindow`
- `dz.sh.hidra.modules.topology.application.service.EngineeringGraphDraftApplicationServiceTest#metadataOnlyConstructorDoesNotActivateGraph`
- `dz.sh.hidra.modules.topology.application.service.EngineeringGraphDraftApplicationServiceTest#transientPublicationFailureDoesNotRetryGraphOperation`

`dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationServiceTest` — source `src/test/java/dz/sh/hidra/modules/topology/application/service/EngineeringNetworkDraftApplicationServiceTest.java`; SHA256 `866d6e46cdaf6c4cbdb8bdfb597ca458462112640f012242604af2640ff6c37a`:

- `dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationServiceTest#ambientTransactionNeverPreparesOrExecutes`
- `dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationServiceTest#eachAttemptPreparesFreshAuthorityWithoutAutomaticRetry`
- `dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationServiceTest#graphVersionAdvancesWithoutReplacingMetadata`
- `dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationServiceTest#updateAndValidationRequireExplicitPositiveExpectedVersion`

`dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraftTest` — source `src/test/java/dz/sh/hidra/modules/topology/domain/model/EngineeringGraphDraftTest.java`; SHA256 `51ba5347c16509cdccc89a1dc0e483c3af9e54cb1c3977e29444e9e5370a7cfb`:

- `dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraftTest#acceptsParallelBranchesBetweenTheSamePortsWithDistinctIdentities`
- `dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraftTest#acceptsSerialStationAndJunctionIncidenceWithoutDimensions`
- `dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraftTest#copiesSortsAndFreezesAllListsWithoutChangingSourceIds`
- `dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraftTest#detectsEmptyAndDisconnectedSketchesWithoutFakingQualification`
- `dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraftTest#detectsIsolatedSubgraphsWithoutDiscardingAuthoredEdges`
- `dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraftTest#domainHasNoInfrastructureOrFrameworkDependencies`
- `dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraftTest#enforcesStationOnlyFacilityBindingAndRequiredKinds`
- `dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraftTest#rejectsBlankNonnfcSurrogateControlsAndOversizedUtf8Identities`
- `dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraftTest#rejectsDanglingNodesAndPorts`
- `dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraftTest#rejectsDuplicateIdsWithinEachNamespace`
- `dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraftTest#rejectsNullElementsAndExcessiveCollectionLengths`
- `dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraftTest#rejectsSamePortAndIntraNodeBranch`
- `dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraftTest#rejectsWrongPortDirectionForEitherEndpoint`
- `dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraftTest#supportsBidirectionalEndpoints`

`dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringGraphDraftPostgresIntegrationTest` — source `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/EngineeringGraphDraftPostgresIntegrationTest.java`; SHA256 `2abad7c852a8dd8578e1bb4e9e47101c119daf42d229f86352f7cd14fc64ecd5`:

- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringGraphDraftPostgresIntegrationTest#actualCatalogConfirmsAllThreeReviewedTriggersAndImmutableSecurityDefiner`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringGraphDraftPostgresIntegrationTest#differentTransactionCannotClaimParentMetadataVersion`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringGraphDraftPostgresIntegrationTest#disablingImmutabilityTriggerIsObservableAsCatalogTampering`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringGraphDraftPostgresIntegrationTest#invalidDigestAndAbsentMetadataAreRejected`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringGraphDraftPostgresIntegrationTest#samePhysicalTransactionAppendsDigestCheckedImmutableSnapshot`

`dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest` — source `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/EngineeringNetworkDraftPostgresIntegrationTest.java`; SHA256 `2348baafd7d6d0a6deed801b7a5fefe9e8bcd1205c5c283ac474d714cc197bbc`:

- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#actualFinalGuardRejectsExpiredProvisionalVersionAndRetainsNoRows`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#actualFinalGuardVerifiesCompleteOwnerFenceSubset`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#actualPublisherFenceWorksWhileStatementTriggersRejectUpdates`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#committedNetworkRevocationAndPartyIneligibilityPreventReplay`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#concurrentSameExpectedVersionCommitsAtMostOneSuccessor`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#disabledActualCatalogTriggerFailsBeforeAnyDraftInsert`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#graphCurrentStationWrongKindAndStatusFailClosed`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#graphFinalGuardContainsThirtyFourRelationFences`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#graphStationRoundTripRequiresReadOnlyGenerationAuthority`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#graphSurvivesMetadataOnlySuccessorWithHistoricalSha`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#ordinaryDirectDmlAndDdlRemainDenied`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#ordinaryGraphSnapshotPrivilegesRemainDenied`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#originalCreationReplayDoesNotRevertLaterMetadata`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#realHttpBearerRoundTripAndSafeUnknownFieldRejection`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#realNativeProvidersCreateReadUpdateAndValidateWithoutPublishing`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#revokedGraphWriteDoesNotCreateASecondMetadataVersion`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#samePairReusesOneContextForIndependentCandidates`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#signedBearerGraphVersionAppendAndHistoricalAsOfAreDraftOnly`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#staleVersionNeverAppendsOrOverwrites`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#twoCompaniesHaveIsolatedContextsOnTheSameLogicalNetwork`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#twoConcurrentSameVersionGraphWritersCommitExactlyOneSuccessor`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest#writerFirstLockPreventsProvisionalCreate`

`dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodecTest` — source `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringGraphDraftCodecTest.java`; SHA256 `c7d9c21a6f2d91b33c7237abfae7b192201f2743f515d59d6dc54d0cf563c27d`:

- `dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodecTest#emptyAndDisconnectedDiagnosticsRemainDraftOnlyAfterDecode`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodecTest#emptyDraftHasStableIndependentCanonicalBytes`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodecTest#independentlyPackedConnectedBytesAndSha256Match`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodecTest#parallelBranchesAreRoundTrippedAndHashedDifferently`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodecTest#preservesNfcUnicodeIdentifiersWithoutCaseFolding`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodecTest#rejectsCorruptedHeaderAndUnknownFormat`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodecTest#rejectsEveryTruncationTrailingBytesAndNull`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodecTest#rejectsInvalidUtf8AndMismatchedEnum`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodecTest#rejectsNoncanonicalElementOrdering`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodecTest#rejectsOversizedAndNegativeCountsBeforeAllocation`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodecTest#rejectsOversizedPayloadAndDefensivelyDecodesBytes`
- `dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodecTest#sortingAndReorderedEquivalentGraphsHaveIdenticalBytes`

`dz.sh.hidra.platform.permissions.HidraRouteAuthorizationInterceptorTest` — source `src/test/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptorTest.java`; SHA256 `63f71ff49c8b90055daccd09b663381947d33939146d995b4925bee53301c00f`:

- `dz.sh.hidra.platform.permissions.HidraRouteAuthorizationInterceptorTest#anonymousProtectedRouteStillRequiresPermission`
- `dz.sh.hidra.platform.permissions.HidraRouteAuthorizationInterceptorTest#derivesIdentityRoadmapPermissionFormat`
- `dz.sh.hidra.platform.permissions.HidraRouteAuthorizationInterceptorTest#engineeringDraftNativeOwnerOnlyHandlesExactSixCompiledOperations`
- `dz.sh.hidra.platform.permissions.HidraRouteAuthorizationInterceptorTest#engineeringDraftNeverDelegatesAnonymousOrDifferentController`
- `dz.sh.hidra.platform.permissions.HidraRouteAuthorizationInterceptorTest#forbidsMissingEffectivePermission`
- `dz.sh.hidra.platform.permissions.HidraRouteAuthorizationInterceptorTest#permitsAnonymousDirectLoginBeforeRoutePermissionResolution`
- `dz.sh.hidra.platform.permissions.HidraRouteAuthorizationInterceptorTest#permitsMatchingEffectivePermission`

`dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest` — source `src/test/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutorTest.java`; SHA256 `cc5326e59a24ec3a4382548d95cdc56c9398b48da820e4c3d9752f6941927807`:

- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#additionalDatasourceConnectionPoisonsOwner`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#admissionGrammarKeepsQuotedDataButRejectsWritableQueryForms`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#afterCommitCallbackErrorWithholdsCommittedResult`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#ambientTransactionRejectsWithoutCreatingProtectedWork`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#batchStreamingAndStatementUnwrapReject`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#beforeCommitCallbackWriteRejectsFullAttempt`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#changedServerBackendOrTransactionIdentityRejects`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#commitDriverErrorIsIndeterminateWithoutRetry`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#crossThreadCapturedHandleRejectsAndPoisons`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#datasourceCloseDuringWorkPoisonsAttempt`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#datasourceDelegateAndBuilderEscapesPoisonCaughtErrors`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#diagnosticAdmissionCannotMintDraftMutationPermit`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#dormantCallableStatementRetainsJdbcInterfaceAndBackReference`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#dormantDatasourceKeepsOrdinaryJdbcSemantics`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#elapsedBudgetRejectsBeforeGuard`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#emptyScalarRowPoisonsCaughtGuardFailure`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#failedAttemptCleansContextForNextSuccessfulAttempt`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#finalGuardMustBeAClosedScalarQuery`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#finishedHandleCannotBecomeUnscoped`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#graphSqlProvisionalFamilyDoesNotGrantUnreviewedOwnerExecution`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#mutableOrJdbcResultsCannotBeDelivered`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#nativeControlAndMultipleSqlReject`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#nestedExecutorPoisonsOuterAttempt`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#ordinaryExecutorCannotSelectPrivateDraftCredentialByCallback`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#prematureCommitRollbackSavepointAndSettingsPoisonAttempts`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#providerMayResetAutocommitOnlyAfterObservedPhysicalCommit`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#publicationCredentialEntryRejectsUnreviewedCallerBeforeWork`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#rawConnectionAndDatasourceUnwrapReject`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#secondGuardStatementPoisonsEvenIfCaught`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#successfulScalarExecutionCommitsBeforeReturning`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#swallowedExpiredHandlePoisonsNewAttempt`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#unsupportedManagerRejectsBeforeWork`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#workSqlErrorPoisonsCaughtContinuation`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#wrapperBackReferencesStayControlled`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#wrongPhysicalReadOnlyAndIsolationReject`
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest#zeroFinalStatementsRejectAndRollBack`

**Stop and next gate:** this is C evidence delivery, not formal HAPI-588 VERIFIED/COMPLETED. Publish directly to main under expected-B-SHA guard, verify parent/tree/exact5-file scope, observe C's applicable Full/Fast/Docs created or started on the exact new SHA, then STOP without waiting. Only after those results actually pass may the next single registered stage `docs(topology): record engineering graph authoring acceptance` assess and record acceptance. No HAPI-589/HAPI-590/HAPI-600, simulator, production deployment, commissioning or autonomous control is selected.

### HAPI-588 acceptance review — registered B regression completion before acceptance — 2026-10-20

**Live evidence and disposition:** C commit `07a40f079aca790c6f93a7aa7909b2f2a771017a`, parent `e8f4f631ff45d75dbd761ee4372453e1f76721fe`, tree `35e0b782288595ef838b3d82f52487c4ffa0c4f6`, passed Full #772 [run39403043675](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39403043675) and Docs #446 [run39403043632](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39403043632). All seven Full jobs passed. Standalone Fast is path-filtered for C's documentation scope; Fast #96 passed the unchanged executable baseline, and Full772 ran the unit/architecture selection. No nonexistent C Fast run is claimed.

**Acceptance review finding:** green CI on existing cases does not establish unimplemented mandatory B scenarios. Source review of the actual22-case graph/metadata PostgreSQL class and7-case graph service class found that the registered owner-generation ABA/wrong-owner checks, graph-specific writer-first/owner-first races, graph+metadata rollback at an expired final guard, actual graph catalog/privilege rejection, and graph-level indeterminate-write classification were not explicitly exercised there. Existing generic station/metadata/technical executor cases remain useful evidence, but are not substituted for these graph-specific cases. Formal HAPI-588 VERIFIED/COMPLETED is therefore NOT recorded. The owner's Next is carried through the already-registered B test scope before the later acceptance step; no new policy or runtime feature is selected.

**One bounded regression-completion commit:** reuse the registered B message `feat(topology): persist protected engineering graph draft versions`; MODIFY only:
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/EngineeringNetworkDraftPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/EngineeringGraphDraftApplicationServiceTest.java`
- `.github/ci-test-inventory.json`
- this phase record.
These are all in the existing B write allowlist. No production source, migration, API/schema artifact, guard, grant policy, workflow, source classifier, lane or frozen registry status changes.

**Actual added test source identities:**
- `EngineeringNetworkDraftPostgresIntegrationTest.graphForeignOwnerAndOwnerAbaNeverReuseAStaleStationWitness`: genuine station graph stored, committed transfer denies current read and append without a new version, owner restored with a strictly newer generation and original graph digest recovered.
- `EngineeringNetworkDraftPostgresIntegrationTest.facilityWriterFirstPreventsBothGraphAndMetadataAppends`: an independent actual PostgreSQL writer holds the facility relation; NOWAIT graph admission fails without either version append.
- `EngineeringNetworkDraftPostgresIntegrationTest.graphOwnerFirstBlocksAConcurrentFacilityGenerationWriter`: a separate administrator transaction attempts a real owner update after graph admission; PostgreSQL lock timeout55P03 proves the master fence blocks generation-trigger writes; the graph commits with unchanged generation.
- `EngineeringNetworkDraftPostgresIntegrationTest.expiredGraphFinalGuardRollsBackBothVersionRows`: the real final guard receives an expired validity boundary after graph and metadata provisional appends; both new rows roll back.
- `EngineeringNetworkDraftPostgresIntegrationTest.actualGraphTriggerAndPrivilegeTamperingRejectBeforeMutation`: disable the actual graph transaction trigger and separately grant forbidden DELETE; each actual production owner call fails without appends, then restored catalog permits the serial/parallel append.
- `EngineeringGraphDraftApplicationServiceTest.indeterminateGraphWriteRemainsIndeterminateAndIsNeverRetried`: inject the executor's explicit unknown-commit outcome into the graph write boundary; require INDETERMINATE, retained internal cause and exactly one execution. This is a service-boundary classification/no-retry case, not a claim to have induced a real unknown database commit. Existing executor tests retain the physical technical outcome obligation.

The existing `realNativeProvidersCreateReadUpdateAndValidateWithoutPublishing` case additionally persists/reloads an actual serial-plus-parallel graph through each genuine LOCAL/OIDC/LDAP/AD producer fixture, then sets a real company-scoped UPDATE DENY and proves no new shared version. It retains the accepted metadata assertions. Test fixtures remain synthetic; no external directory or deployment data is required.

**Validation and next gate:** available OpenJDK17 parser accepts both changed Java sources; actual source method identities are27 PostgreSQL cases and8 graph service cases, preserving all previous identities, with updated exact SHA256 inventory pins. GrantEffect GRANT/DENY was confirmed in live Identity source. No Maven/Java21/Docker/PostgreSQL runtime execution is claimed locally. Hosted CI must execute these sources; their presence does not mean they passed. Preserve HAPI-588 PARTIAL/REGISTERED_PENDING and C's successful artifact record. Publish directly to main under expected-C-SHA guard, verify parent/tree/exact4-file scope, observe applicable exact-SHA Full/Fast/Docs creation/startup, then STOP without waiting. The next action is to inspect that real feedback and reassess the registered acceptance criteria; no downstream task starts automatically.

### HAPI-588 formal bounded software acceptance — 2026-10-20

**Registered acceptance stage and decision:** `docs(topology): record engineering graph authoring acceptance`. Owner Next selects acceptance after the registered A/B/C implementation, generated-artifact reconciliation and required regression completion. HAPI-588 / HPR-P25-052B is now **VERIFIED / COMPLETED** for its explicitly registered draft-only graph authoring contract. This current decision supersedes the preceding pending/status statements at their dated checkpoints; all failed-run and repair evidence remains immutable history. Only HAPI-588's Implementation/Planning Status cells and task header change, with this source-backed acceptance record. The parent HPS-004 phase and every other task retain their independent status.

**Accepted executable/test source:** `ff06520757207cbd6bbf2b3ff29e979977998cef`, parent `07a40f079aca790c6f93a7aa7909b2f2a771017a`, tree `2818cbc2b550412a1de4d703a64a106d6d714564`. Full #773 [run39406126892](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39406126892), Fast #97 [run39406126873](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39406126873), Docs #447 [run39406126844](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39406126844) all **SUCCESS on that exact SHA**. Full's seven jobs succeeded: API118572377095, unit118572377152, static118572377208, PostgreSQL shard0 118572377218, oracle118572377228, PostgreSQL shard1 118572377300, gate118579661638. C itself `07a40f079aca790c6f93a7aa7909b2f2a771017a` passed Full772/Docs446; C's standalone Fast was truthfully path-filtered, and subsequent test completion now has genuine exact-SHA Fast97 as well as Full773. No absent Fast run or inferred pass is claimed.

**Authenticated actual execution evidence:** recovered aggregate artifact12257968273 `hidra-full-ci-evidence-ff06520757207cbd6bbf2b3ff29e979977998cef-39406126892-1`, run39406126892/attempt1, ZIP SHA256 `868df597da5e6c151575c76d98f0094c66d7de9febd969e1e456817b4913eb91`. Locally verified ZIP, all six lane receipt digests, every retained file digest and exact source/run/attempt/inventory context. Raw reports reparse as unit1684 cases/331 classes, PostgreSQL0 243/37, PostgreSQL1 634/57; combined partition cases equal the unrestricted oracle's **2561 cases/425 classes with zero failures/errors/skips**. Inventory fingerprint `5dfd505fbbd461e2794abf247255963905b29f0b0067d8f0f13c85c3a91cbbcc`. Current hashes of all13 task-related test sources match that manifest; their138 exact case identities are the132 listed at C plus the six explicitly listed at the following regression-completion checkpoint. Actual graph PostgreSQL class27/0/0/0 and graph service8/0/0/0 include all newly added cases. Optimized-only CI remains ineligible and unrestricted fallback mandatory, as stated by the retained gate; acceptance changes no CI policy.

**Accepted requirements and meaningful proof:** immutable exact graph identities, bounded canonical codec and original metadata format; serial/parallel typed branch/port incidence with negative duplicate/dangling/direction/Unicode/format cases; native company/network READ/UPDATE with real LOCAL/OIDC/LDAP/AD producer fixtures, graph roundtrip and DENY, revocation and original Identity mapping/authentication regression suite; exact compiled graph routes and strict Jackson3 DTO/error behavior; station current owner/kind/status, foreign-owner rejection and committed owner ABA with fresh generations; metadata/graph shared version interleaving and historical SHA retention; concurrent CAS writers exactly one successor; writer-first exclusion and owner-first actual PostgreSQL55P03 generation-writer blocking; real graph trigger/privilege tampering rejection; full rollback of provisional graph and metadata rows at an expired final guard; indeterminate classification and no retry together with retained technical executor outcome cases. The final guard still covers34 distinct relations with30 SHARE_ROW_EXCLUSIVE/3 ACCESS_SHARE/1 SHARE, existing metadata31 path unchanged. These accept the registered software contract, not a broader industrial model.

**Current successful runtime contract artifacts:** API and oracle members in the authenticated aggregate are byte-identical. API member SHA256 `ff20f148d7778a22b328809fa8290b8e062da070a8a206a0f63deed9d62d0b9f`; generated base `07a40f079aca790c6f93a7aa7909b2f2a771017a`, base member SHA256 `bf75c75401cda1387c880e07c2e147aeea65516ac07842870894aa7b3253203f`. Local strict equality to C's reviewed snapshot (removing only validated generator SHA) and supported backward compatibility pass. Retained canonical snapshot remains248 paths/269 operations/244 schemas, digest `c06cac659168aa5c433b72eb57bc2c76367fc083fb6c8791cee2c2f814247461`; no hand-authored contract edits. Current catalog member SHA256 `1a18d59b8d962bc52ba6f94a95da4acab931a6d5b19437cc33c151eab270d6f8`, source bundle `9dfd75ade23bb7b2162527345f209ab0173c53ec4eb1ba8054647cbd4bdc2e47`; unchanged generator validated all recovered migration/JPA source hashes. Physical dictionary equality to C's reviewed dictionary passes after only the validated capture-SHA line is excluded: PostgreSQL16.15,156 migrations/470 JPA mappings/501 relations/zero unresolved owners. Physical/catalog bytes and governed ownership are actual hosted evidence; the successful C artifact pointers remain valid and unchanged.

**Accepted boundary and retained downstream gates:** one company/network-owned draft aggregate, immutable graph snapshots under the shared monotonically increasing metadata version, current and exact as-of graph reads, authored JUNCTION/STATION nodes and interface placeholders, PIPE/CONNECTOR branches and deterministic incompleteness diagnostics. Station presence in the draft does not establish external physical network membership; graph PIPE is not a dimensioned or hydraulically qualified pipe; station interface placeholders are not installed-unit port correspondence. No effective/public governed publication or history lifecycle (HAPI-590), survey/CRS/elevation/section authoring (HAPI-589), installed configuration completion (HAPI-600), numerical engine, commissioning, deployment or OT actuation is accepted here. No real calibration/SITE data was required for synthetic software verification. Blocked downstream tasks are not automatically unblocked or selected by this acceptance.

**Exact3 MODIFY scope and validation:** this phase record, `doc/roadmap/ULTIMATE_ROADMAP.md`, `doc/roadmap/ULTIMATE_PHASES.md`. Only HAPI-588's two status fields synchronize in the two frozen registries; all descriptions/objectives/IDs/priority/dependencies/order/membership and other statuses remain unchanged. Task header aligns with those rows; historical evidence is preserved. Local artifact/hash/raw-report/case-membership/source-provenance/snapshot/compatibility/dictionary checks described above actually passed; Java21/Maven/PostgreSQL results are the actual hosted runs, not newly executed local results. Publish under expected-ff065207 guard, verify parent/tree/exact3-file scope, observe this documentation-only acceptance commit's applicable CI created/started, then STOP without waiting. A future Next may review the next registered task's live prerequisites; it must not implement blocked HAPI-589/HAPI-590/HAPI-600 implicitly.
