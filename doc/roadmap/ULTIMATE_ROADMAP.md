# HidraAPI — Ultimate Universal Platform Roadmap

> **Authoritative active execution document.** Complete CURRENT task table first, detailed REMAINING task descriptions second. Full completed-task actions, earlier exact task messages, historical tables, preflight records and CI evidence are preserved **verbatim** in [history/ULTIMATE_ROADMAP_HISTORY.md](history/ULTIMATE_ROADMAP_HISTORY.md). Live code, migrations, tests, `AGENTS.md` and exact-head CI outrank stale historical claims.

## 1. Control Status

| Field | Value |
|---|---|
| Status | ACTIVE |
| Purpose | Universal platform task register with detail and dependencies |

## 1.1 Development execution progress — 2026-10-11

This is an execution-status summary of the authoritative task table below, **not** a module functional-gap review, new HPR registration, or claim of release/site acceptance. Detailed completed-task history remains in [history/ULTIMATE_ROADMAP_HISTORY.md](history/ULTIMATE_ROADMAP_HISTORY.md).

| Development stage | Recorded execution status | Boundary / next step |
|---|---|---|
| P0 / P1 / P2 platform foundation, security, governance and remediation | CLOSED within the roadmap's recorded acceptance scope | No reopening inferred |
| P2.5 topology groundwork HPR-P25-032, 034, 036, 037 | CODE IMPLEMENTED; recorded production CI successful | Immutable station graph storage does not yet establish publication REST APIs |
| HPR-P25-038 revision publication/history policy | COMPLETED as design / documentation | Runtime history/publication remains future implementation |
| HPR-P25-039, 040, 041 owner, actor and paired scoped permission exports | CODE IMPLEMENTED; recorded production CI successful | No implication of complete cross-owner publication/authorization |
| **HPR-P25-042 live Facility/Party owner-reference query** | **COMPLETED / PRODUCTION #659 AND DOCS #251 SUCCESS** | Accepted owner observation only; no publication authority |
| **HPR-P25-043 cross-owner access linearization preflight** | **DESIGN COMPLETED / DOCS #253 SUCCESS** | Runtime B1/B2/B3 and race/performance gates remain OPEN |
| **HPR-P25-044 B1 operation-authentication provenance policy** | **DESIGN COMPLETED / DOCS #255 SUCCESS** | Dedicated issuer/jti session binding selected; runtime storage/issuance/reader and B2/B3 remain OPEN |
| **HPR-P25-045 B1 storage preflight** | **PLANNED / REGISTERED / DESIGN ONLY** | Successful registration Docs and separate owner Next select exact two-document storage design |
| F1-02 through F1-04 publication, APIs and topology verification | PLANNED; subsequent implementation slices not registered | Register bounded HPRs before execution |
| F2-01 through F2-04 product/equipment/engineering history | PLANNED; not registered | Dependent F1 ownership and history work |
| F3-01 through F3-04 telemetry/monitoring/action evidence | PLANNED; not registered | Dependent topology and source-scope contracts |
| F4-01 through F4-03 synthetic acceptance and platform gate | PLANNED; not registered | Dependent F1–F3 verifications |
| Numerical simulation; P3 industrial extensions | DEFERRED | Explicit future authorization / gates required |

**Status source:** Current consolidated task table and recorded exact-head CI references below. Completion counts across all historical tasks or a percentage of overall platform delivery are **not** inferred from this planning table. No production code, migrations, API contract or implementation status is changed by this documentation correction.

## 2. Consolidated task table — statuses and dependencies

**Product policy:** Hidra is universal, configurable and company/network/product independent. No real customer data, P&IDs, vendor curves or live SCADA are prerequisites for generic development. PLATFORM software testing uses explicit synthetic data; customer SITE commissioning is an entirely separate gate. Numerical simulation work is deferred.

**Code discipline:** `HPR-P25-037` is implemented and CI accepted; `HPR-P25-038` policy design is accepted; `HPR-P25-039` Party eligibility is accepted; `HPR-P25-040` trusted Topology actor export is CI accepted; `HPR-P25-041` paired scoped permission export is CI accepted; `HPR-P25-042` live Facility/Party owner-reference query is CI accepted; `HPR-P25-043` cross-owner access linearization design is Docs accepted; `HPR-P25-044` B1 operation-authentication provenance policy is completed as design below; policy Docs #255 SUCCESS. `HPR-P25-045` additive binding/credential-security storage preflight is registered below, not executed. Runtime provenance, common-time and transaction-participation gates remain OPEN. Runtime revocation/linearization and publication slices remain unregistered. Codes `F1-02` etc. are **planning references only** and MUST NOT be executed or treated as approved HPR codes before registration, exact commit message and exact create/update/delete file allowlists. Dependencies are minimum known gates, not blanket permission.

| Order | Code / planning reference | Task | Current status | Dependencies / gate | Track |
|---|---|---|---|---|---|
| 1 | HPR-P25-037 | Immutable station-graph PostgreSQL persistence | COMPLETED — exact-head Production #654 and Docs #237 SUCCESS | Accepted at `0868f2d17ce0eaefc53902ce36fa711681cd406b` after bounded #653 repair | F1 |
| 2 | HPR-P25-038 / F1-02 preflight | Station revision publication/history policy and implementation decomposition | COMPLETED — policy Docs #239 SUCCESS | Accepted at `34222374ef5451144766bc07dfe5957d67b4c5fb` | F1 |
| 2.1 | HPR-P25-039 | Party-owned eligibility contract exported to Topology | COMPLETED — exact-head Production #655 and Docs #240 SUCCESS | Accepted at `5a279f27cd3058c909629f78b67b417a7e607e18` | F1 |
| 2.2 | HPR-P25-040 | Identity-owned trusted current human actor export to Topology | COMPLETED — exact-head Production #656 and Docs #242 SUCCESS | Accepted at `d96e420757f79aaf04e28b3f65c719f12e3029a0` | F1 |
| 2.3 | HPR-P25-041 | Identity-owned paired company/facility permission export | COMPLETED — exact-head Production #657 and Docs #244 SUCCESS | Accepted at `6429b8dc0e449b54db89d520ba14c22ce424db1c` | F1 |
| 2.4 | HPR-P25-042 | Topology live Facility/Party owner-reference query | COMPLETED — exact-head Production #659 and Docs #251 SUCCESS | Accepted at `ed6ec83f1b2163fc32a15f2f3c6964cf6758a025` | F1 |
| 2.5 | HPR-P25-043 | Cross-owner access linearization design preflight | COMPLETED — design only; exact-head Docs #253 SUCCESS | Accepted at `f6dabb6a4585c7c30203352f9f96a2892aa79a84`; runtime B1/B2/B3 OPEN | F1 |
| 2.6 | HPR-P25-044 | B1 operation-authentication provenance and session-binding policy preflight | COMPLETED — design only; exact-head Docs #255 SUCCESS | Accepted at `8a2c692825f0466441ae23d49ecc24324de89775`; B1 runtime/B2/B3 OPEN | F1 |
| 2.7 | HPR-P25-045 | B1 binding and credential-security storage preflight | PLANNED — registered below; NOT EXECUTED | Accepted 044; passing registration Docs and separate owner Next; exact two-document scope | F1 |
| 2a | F1-02 implementation | Station revision history / transactional publication | PLANNED — not registered or implemented | HPR-P25-038 policy design; exact implementation allowlist and subsequent owner Next | F1 |
| 3 | F1-03 | Company-scoped authoring, topology APIs and exact-history access | PLANNED — needs HPR registration | F1-02; authorization/API contracts | F1 |
| 4 | F1-04 | Synthetic multi-company topology validation | PLANNED — needs HPR registration | F1-03; registered acceptance test envelope | F1 |
| 5 | F2-01 | Company-owned product/property/method configuration | PLANNED — needs HPR registration | F1 owner and scope model; property-method policy | F2 |
| 6 | F2-02 | Equipment characteristics and source references | PLANNED — needs HPR registration | F2-01; equipment ownership model | F2 |
| 7 | F2-03 | Immutable engineering revisions and effective history | PLANNED — needs HPR registration | F2-01/02; owner/revision contracts | F2 |
| 8 | F2-04 | Frozen authoritative engineering baseline manifests | PLANNED — needs HPR registration | F1-02; F2-03 | F2 |
| 9 | F3-01 | Configurable telemetry sources and tag mappings | PLANNED — needs HPR registration | F1-03; source security/ownership policy | F3 |
| 10 | F3-02 | Telemetry quality, freshness, UNKNOWN and replay | PLANNED — needs HPR registration | F3-01; timestamp/idempotency contract | F3 |
| 11 | F3-03 | Monitoring evidence independent of simulation | PLANNED — needs HPR registration | F3-02; monitoring/alarm contracts | F3 |
| 12 | F3-04 | Authenticated action and actual observed outcome records | PLANNED — needs HPR registration | F3-03; audit/workflow ownership | F3 |
| 13 | F4-01 | Versioned synthetic PLATFORM acceptance matrix A–I | PLANNED — incrementally develop | HPR-P25-030 cases; F1–F3 features | F4 |
| 14 | F4-02 | Tenant/security/database/replay/OpenAPI full verification | PLANNED — needs HPR registration | F4-01; implementable F1–F3 slices | F4 |
| 15 | F4-03 | Independent PLATFORM gate review and closure | PLANNED — needs HPR registration | F4-02; accepted full exact-head CI | F4 |
| — | SIM-DEF | Numerical simulator, calibration and regulator Slice B/C | DEFERRED | Fresh explicit owner authorization | Simulator |
| — | P3-DEF | Industrial scale/TimescaleDB/PostGIS extensions | DEFERRED | Approved business requirements and new gates | P3 |
| — | HIST-REQ | Old mixed/parent P2.5 pending proposals | HISTORICAL — not directly executable | Reconcile independent needs into F1–F4; simulation stays deferred | Governance |

### Completed work and historical navigation

| Completed evidence group | Status | Historical location |
|---|---|---|
| P0 / P1 / P2 security, survivability, governance, semantic remediation | CLOSED within recorded acceptance scope | [Original task registry and actions](history/ULTIMATE_ROADMAP_HISTORY.md) |
| HPR-CI-001 / HPR-CI-002 | COMPLETED within focused scope | [Historical CI evidence](history/ULTIMATE_ROADMAP_HISTORY.md) |
| HPR-P25-032, HPR-P25-034, HPR-P25-036 | CODE IMPLEMENTED with recorded full CI #646/#648/#649 | [Historical implementation and tests](history/ULTIMATE_ROADMAP_HISTORY.md) |
| P2.5 design/preflight 018–030, 033, 035 | DESIGN/PREFLIGHT DELIVERED only; no implied runtime acceptance | [Historical definitions](history/ULTIMATE_ROADMAP_HISTORY.md) |
| Formal v0.6.0 / development 0.7.0-SNAPSHOT | RETAINED release and development state | [Historical release evidence](history/ULTIMATE_ROADMAP_HISTORY.md) |

## 3. Remaining-task execution rules

1. The table above governs selection, never a historical `NEXT` note. Only registered exact HPR tasks may change code; check prerequisites, required full CI, migration authorization, exclusive file allowlist and commit message in their detailed registration.
2. At each step recheck live `main` SHA, `AGENTS.md`, `PROJECT_STATE.md`, reconciliation, production code, migrations, tests and CI. Commit guarded by expected-head and STOP after one registered code.
3. Do not alter accepted historic records or claim documentation-only design as executable functionality. All original records are in the historical snapshot, including originally pending entries.
4. Generic PLATFORM tests are synthetic and can establish only software behavior; SITE acceptance needs real operator-specific provenance and competent approval and is not implied by full CI.
5. Proposed files for unregistered tasks are **candidate ownership areas**, not exact permitted mutations; names should not be fabricated prematurely. Deletions: **none authorized** unless a future registered task expressly lists them.

## 4. Detailed descriptions of remaining tasks

### HPR-P25-037 — Immutable station-graph PostgreSQL append and exact read

**State:** COMPLETED within immutable storage scope. Implementation `4e7ac44f9eb917fa6eee42aaf858e2e6b0886fa4` plus corrective test commit `0868f2d17ce0eaefc53902ce36fa711681cd406b`; exact-head Production #654 and Documentation #237 SUCCESS. The complete original registration below remains historical and binding for its delivered scope.

**Exact approved implementation commit:** `feat(topology): persist immutable station graph revisions`

**Prerequisites:** successful registration-documentation CI, separate owner Next, refreshed `main`, migration/path collision check; HPR-P25-032/034/035/036 accepted foundations.

**Exact exclusive future implementation files:**

| Operation | Path |
|---|---|
| CREATE | `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcStationInternalGraphRevisionRepositoryAdapter.java` |
| CREATE | `src/main/resources/db/migration/V20261011_002__p25_topology_station_graph_revisions.sql` |
| CREATE | `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/StationInternalGraphRevisionPostgresIntegrationTest.java` |
| UPDATE | `.github/database-dictionary-ownership.json` |
| UPDATE | `doc/database/DATA_DICTIONARY.md` |
| UPDATE | `doc/roadmap/ULTIMATE_ROADMAP.md` |
| UPDATE | `doc/model-remediation/RECONCILIATION.md` |
| DELETE | **None authorized** |

**Engineering contract:** Store V1 canonical byte payload and verified digest under exact (facility, revision) identity; identical replays succeed; differing payload at same key conflicts; invalid format/digest/canonical encoding or altered identity raises error; immutable SQL mutation guards; concurrency, rollback and actual PostgreSQL 16 fresh/upgrade tests. Owner-private persistence does **not** establish company authorization, REST publication, approval/head CAS or SITE acceptance. The complete original exact approved specifications, SQL constraints, test commands, source references and limitations appear next in the **verbatim 037 registration** section; these remain binding, not replaced by this summary.

### F1-02 — Govern revision history and publication

**Scope:** Define owner-controlled predecessor/head CAS, concurrent publication and exact immutable historical access; company authorization must be designed separately from the owner-private port.

**Files to create/update:** Topology application/service, persistence, authorization and tests — exact create/update paths to be selected by live source preflight. **Files to delete:** None authorized. Any actual exclusive allowlist/migration must be approved through a separate HPR registration.

**Acceptance:** Concurrency, authenticated ownership, rollback, reproducible revision retrieval.

### F1-03 — Expose configurable topology and authorized APIs

**Scope:** Implement configuration of networks/stations/equipment/ports and scoped authoring/query contracts without site defaults.

**Files to create/update:** Topology domain, application, API/controller/DTO, adapter and integration tests — exact paths TBD. **Files to delete:** None authorized. Any actual exclusive allowlist/migration must be approved through a separate HPR registration.

**Acceptance:** Invalid graphs, cross-company denial, access logs, OpenAPI compatibility.

### F1-04 — Qualify universal topology

**Scope:** Verify distinct synthetic organizations and network graphs, disconnected/unknown structures and immutability.

**Files to create/update:** Topology and integration test/fixture paths — exact filenames TBD. **Files to delete:** None authorized. Any actual exclusive allowlist/migration must be approved through a separate HPR registration.

**Acceptance:** Isolation, valid/invalid inputs, deterministic replay and recorded gaps.

### F2-01 — Configure product and property methods

**Scope:** Allow different company product definitions and property/method metadata; unsupported combinations must fail.

**Files to create/update:** Existing product/custody/topology owner modules to be determined; domain/application/persistence/API/tests and migration only after authorization. **Files to delete:** None authorized. Any actual exclusive allowlist/migration must be approved through a separate HPR registration.

**Acceptance:** Independent multi-product fixtures, restrictions and negative cases.

### F2-02 — Manage equipment characteristics

**Scope:** Support configurable characteristics and qualified manufacturer/source references; real equipment curves are not needed for software tests.

**Files to create/update:** Existing equipment/assets and topology ownership to be verified; precise classes/tests/migrations TBD. **Files to delete:** None authorized. Any actual exclusive allowlist/migration must be approved through a separate HPR registration.

**Acceptance:** Versioned source provenance and missing-data behavior.

### F2-03 — Persist immutable engineering history

**Scope:** Implement declared/effective versions, authoritative ownership, exact retrieval and comparisons.

**Files to create/update:** Engineering owner modules and database/test paths TBD. **Files to delete:** None authorized. Any actual exclusive allowlist/migration must be approved through a separate HPR registration.

**Acceptance:** History immutability, idempotency, conflicting revisions and permissions.

### F2-04 — Freeze engineering baselines

**Scope:** Freeze manifests of topology and equipment/product/property revisions, with diagnostics for missing references.

**Files to create/update:** Owner application/services, persistence, API and tests TBD. **Files to delete:** None authorized. Any actual exclusive allowlist/migration must be approved through a separate HPR registration.

**Acceptance:** Verifiable immutable freeze, compare, provenance and recovery.

### F3-01 — Configure telemetry source/tag bindings

**Scope:** Implement adapter/source registration, asset mapping, units and trust policy without fixed SCADA protocols.

**Files to create/update:** Telemetry/integration modules, contracts, mapping/persistence/API/tests TBD. **Files to delete:** None authorized. Any actual exclusive allowlist/migration must be approved through a separate HPR registration.

**Acceptance:** Multi-company source isolation, invalid mapping and unauthorized ingest.

### F3-02 — Enforce quality/freshness and replay

**Scope:** Handle duplicates, out-of-order, missing and stale samples, UNKNOWN values, temporal provenance and deterministic replay.

**Files to create/update:** Telemetry application/infrastructure and database integration test paths TBD. **Files to delete:** None authorized. Any actual exclusive allowlist/migration must be approved through a separate HPR registration.

**Acceptance:** Idempotency, time coherence, quality preservation and negative cases.

### F3-03 — Capture independent monitoring evidence

**Scope:** Capture verifiable monitoring conditions without requiring a physical simulator.

**Files to create/update:** Monitoring, telemetry, alarm application/persistence/tests TBD. **Files to delete:** None authorized. Any actual exclusive allowlist/migration must be approved through a separate HPR registration.

**Acceptance:** No inferred certainty from stale/UNKNOWN measurements.

### F3-04 — Separate decisions from outcomes

**Scope:** Record authenticated human actions, declared interventions and actual observed changes separately.

**Files to create/update:** Workflow/audit/monitoring application, persistence and authorization tests TBD. **Files to delete:** None authorized. Any actual exclusive allowlist/migration must be approved through a separate HPR registration.

**Acceptance:** Actor provenance, immutable evidence, non-attribution and absent observations.

### F4-01 — Implement A–I acceptance matrix

**Scope:** Define and implement reusable, versioned synthetic cross-company scenarios, aligned with original HPR-P25-030 acceptance obligations.

**Files to create/update:** `doc/roadmap/PLATFORM_ACCEPTANCE.md` and authorized automated acceptance test files. **Files to delete:** None authorized. Any actual exclusive allowlist/migration must be approved through a separate HPR registration.

**Acceptance:** No SITE or numerical accuracy implication.

### F4-02 — Verify end-to-end PLATFORM quality

**Scope:** Verify tenant/role boundaries, PostgreSQL/Flyway integrity, rollback, replay, history, OpenAPI and full exact-head CI.

**Files to create/update:** Architecture/integration/API tests, CI and docs only if registered. **Files to delete:** None authorized. Any actual exclusive allowlist/migration must be approved through a separate HPR registration.

**Acceptance:** Exact-SHA evidence with explicit gaps and executed tests.

### F4-03 — Independently close PLATFORM gate

**Scope:** Audit the accepted F1–F4 scopes and record remaining unsupported cases.

**Files to create/update:** `doc/roadmap/ULTIMATE_ROADMAP.md`, `doc/roadmap/PLATFORM_ACCEPTANCE.md`, reconciliation if separately admitted. **Files to delete:** None authorized. Any actual exclusive allowlist/migration must be approved through a separate HPR registration.

**Acceptance:** Do not mark SITE or physical certification as passed.

### SIM-DEF — Deferred numerical simulation

**State:** DEFERRED. Preserve prior engines, tests, SQL, contracts and historical P2.5 obligations. **Files create/update/delete:** None authorized. Reopen only on explicit owner approval.

### P3-DEF — Deferred industrial extensions

**State:** DEFERRED. Original HPR-P3-001..008 requirements, dependencies and commit messages are preserved in [history](history/ULTIMATE_ROADMAP_HISTORY.md). **Files create/update/delete:** None authorized.

### HIST-REQ — Unresolved historical requirements

Parent P2.5 002–017, 008C–F, mixed 020–031 and other old `PENDING` rows are preserved as historical requirements, not a second queue. Reconcile independent engineering/configuration/monitoring requirements into explicitly registered F1–F4 tasks; simulator-dependent work remains deferred. **Files create/update/delete:** None authorized by this classification.

## 5. Full authoritative HPR-P25-037 registration — preserved verbatim

> The following is copied exactly from the previously committed HPR-P25-037 registration. It preserves the authorized SQL design, immutable storage constraints, exact test and file rules. Its historical baseline/CI-pending language refers to the time of registration, not an assertion about current CI status.

## 2026-10-11 — Register HPR-P25-037 immutable station graph PostgreSQL persistence

### Active selection, baseline and registration scope

This is the next bounded **P35-B registration stage** selected by the owner's continuation
instruction and the active Universal Hidra Platform dashboard. It supersedes that dashboard's
"NOT REGISTERED" persistence disposition only after this registration is published; it does
not select its future implementation or P35-C. Supporting registration message:
`docs(topology): register immutable station graph sql persistence`.
Only this roadmap and `doc/model-remediation/RECONCILIATION.md` change, append-only.

Starting main `da0f0836ed8d1f6dbbaa447d711b9c63be469b05`, tree
`c916981506f0250c69b81dd58c89701aa8f54ca8`; Documentation #229/run 38146884638
SUCCESS on that exact SHA. HPR-P25-036 implementation at
`a8f34638b31ca18ead266450c1f91a57d693d914` passed full Production #649/run
38145851093 and Documentation #228/run 38145851090. Git comparison shows only the
two governance files changed between that production baseline and starting main.
Source equivalence is not a production run on the documentation-only head.
AGENTS.md, PROJECT_STATE.md, active execution view and reconciliation were read.

### Recovered implementation and governance anchors

- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
  already defines append/find/findStored, exact facility/revision identity, canonical replay,
  conflicting-content rejection and stored metadata. It exports no foreign-module API.
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/StationInternalGraphRevisionCodec.java`
  supplies V1 canonical bytes, strict decode/re-encode, SHA-256 and bounded payload handling.
  All source/time/evidence/predecessor/qualification fields remain inside those exact bytes.
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter.java`
  supplies the existing JdbcTemplate/transaction/insert-on-conflict/exact verified read pattern.
  Its physical revision data, codec and SQL are read-only precedents.
- `src/main/resources/db/migration/V20261009_001__p25_topology_physical_network_revisions.sql`
  demonstrates composite identity, bytea/digest constraints and immutable mutation triggers.
  `doc/database/FLYWAY_POLICY.md` requires forward-only allocation, fresh/upgrade validation,
  actual-catalog dictionary regeneration and no applied-migration edits.
- `.github/scripts/generate_data_dictionary.py`, `.github/database-dictionary-ownership.json`
  and `.github/workflows/ci.yml` make source/catalog/ownership drift a full-CI gate. The
  dictionary must be regenerated from actual migrated PostgreSQL, never guessed from SQL.
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/TopologyPhysicalNetworkRevisionPostgresIntegrationTest.java`
  supplies PostgreSQL 16, transactional proxies, race/rollback tests and full-chain catalog
  capture precedent; `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` preserves
  application-port and private module boundaries.
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java` carries ownerPartyId;
  `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java`
  verifies supplied Party references. Neither grants access to an arbitrary revision identity.
  `src/main/java/dz/sh/hidra/platform/security/TenantContext.java` is a thread-local holder,
  not proof of database isolation; `src/main/java/dz/sh/hidra/platform/configuration/HidraSecurityConfiguration.java`
  supplies JWT authentication, not station-history authorization.
  Existing `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
  exposes facility/pipeline-system operations, no station revision publication.

### Registered future task and exclusive write contract

| Field | Binding contract |
|---|---|
| Code / owner | HPR-P25-037 / Topology |
| Objective | Implement owner-private immutable PostgreSQL station graph append and exact verified read |
| Exact implementation message | `feat(topology): persist immutable station graph revisions` |
| Initial status | REGISTERED / PLANNED — NOT SELECTED, NOT IMPLEMENTED |
| Prerequisites | Successful exact-head Documentation CI for this registration; separate owner Next; refreshed main/source/CI; migration/path collision recheck |
| Existing accepted contract | HPR-P25-032 graph, 034 envelope, 036 codec/port; preserve all bytes and signatures |
| Future implementation acceptance | Java 21, PostgreSQL 16 fresh and upgrade tests, architecture checks, dictionary drift check, full exact-head production CI |
| Excluded | REST/API/export/Workbench exposure, company authorization, publication/head selection, numerical engines, release or phase closure |

Exclusive **future implementation** allowlist (this registration creates none of these files):

1. NEW `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcStationInternalGraphRevisionRepositoryAdapter.java`
2. NEW `src/main/resources/db/migration/V20261011_002__p25_topology_station_graph_revisions.sql`
3. NEW `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/StationInternalGraphRevisionPostgresIntegrationTest.java`
4. EXISTING `.github/database-dictionary-ownership.json` — add only the new table's source-backed Topology ownership entry.
5. EXISTING `doc/database/DATA_DICTIONARY.md` — deterministic actual-catalog regeneration for the additive migration, preserving reviewed existing ownership.
6. EXISTING `doc/roadmap/ULTIMATE_ROADMAP.md` — append task status and evidence.
7. EXISTING `doc/model-remediation/RECONCILIATION.md` — append matching evidence.

No other file is writable: domain/port/codec, existing tests/migrations, security, dependencies,
workflow, exports and version stay unchanged. Preserve canonical Java headers. Stop and
re-register if the implementation requires a broader contract, catalog-owner correction or
additional source file. No migration or PostgreSQL execution occurs in this registration.

### Migration reservation, storage and integrity

The exact main tree contains **144** SQL migration files and 144 unique normalized versions;
actual tail is `V20261011_001__p25_simulation_regulator_parameter_revision_format.sql`.
HPR-P25-037 code and all three new paths were absent. Reserve normalized **20261011.002**
for the named additive migration. Relevant remote candidate branch snapshots
`codex/p25-fluid-equipment-candidate` at `aa100e90d35673510ac5ca87dc0ec334e24abd47`
and `codex/p25-network-revision-candidate` at `570e3b816e5b04c56c6e2c25969461c79e63a998`
contain neither that version nor a station-graph migration. This is a bounded collision
check, not a lock over every external branch; re-scan live main/relevant parallel work
before implementation and publication. Any collision requires a new registration, not
silent reassignment. Historical counts in Flyway policy retain their recorded applicability.

New table `hidra_topology_station_graph_revision`: nonblank normalized text facility_id
and revision_id as composite primary key; payload_format text; canonical_payload bytea;
sha256 varchar(64); all NOT NULL. Admit only `HIDRA_TOPOLOGY_STATION_GRAPH_REVISION_V1`,
1..1048576 payload bytes and lowercase 64-hex digest equal to PostgreSQL SHA-256 of those
bytes. Identity checks reject surrounding ASCII trim characters and whitespace-only values
following the existing physical-table convention. No default organization, fabricated site,
recorded-time replacement, projection timestamp truncation or seeded station is permitted.
All supplied metadata/Instant nanoseconds are authoritative in V1 bytes; this task introduces
no duplicate mutable metadata columns, facility FK or authoritative master-data eligibility.
Synthetic declarations can be stored without provisioning a live installation.

Use dedicated Topology function `hidra_topology_reject_station_graph_revision_mutation`
and triggers `topology_station_graph_revision_no_update_delete` /
`topology_station_graph_revision_no_truncate` to reject UPDATE/DELETE/TRUNCATE. They are
append-only application safeguards, not protection against privileged administrative bypass.
Verify bytes, format, digest and row/payload identities on **every** read; use existing strict
codec so hash-consistent malformed/noncanonical bytes fail. Corruption is an error, never
Optional.empty. Exact facility/revision absence alone returns empty; no latest fallback.

### Append, transaction and security boundary

The adapter implements the existing port using JdbcTemplate and Spring transactions.
Encode first; insert with ON CONFLICT (facility_id,revision_id) DO NOTHING; exact read
within the same transaction and compare format, digest and complete canonical bytes.
Same bytes, including reordered equivalent graph members, return the canonical stored
revision without mutation. Different bytes at the same key fail under existing
InvalidTopologyValueException conventions. Distinct facility keys may reuse revision IDs.

Test concurrent identical and conflicting appends through actual transaction proxies under
PostgreSQL READ COMMITTED: one durable row, both identical calls succeed; conflicting
calls have one winner and one domain conflict. Exceptions and outer-transaction failure
must roll back new appends. Stricter ambient transaction isolation may produce database
serialization failures which propagate; no retry/idempotency claim beyond the tested
contract and no caught-and-ignored database failure is authorized.

Predecessor remains supplied intent only. No existence lookup, current head, expected-head
compare-and-set, withdrawal, source verification, Workflow approval, actor attribution or
published graph is created. Only owner-private internal persistence is exposed. No new
permissions/grants, controller, cross-module contract or Workbench resource are admitted.
Facility-key separation is **not company/tenant access isolation**. P35-C must separately
register authenticated company/facility scope and historical access checks before user-facing
reads/writes; F1/F4 company-isolation gates remain OPEN. Real site data is optional deployment
input, never a dependency of this synthetic storage slice.

Migration is additive and unseeded. Test fresh complete Flyway chain and upgrade from the
actual 144-file predecessor chain with retained existing records/catalog evidence. Verify
new empty store, applied history, validate(), immutable old checksums and unchanged physical
V1/V2 retrieval. Application rollback retains the new unused table and applied migration;
no down migration, production DROP/TRUNCATE, Flyway clean/repair or database restore is
authorized. SQL failure must leave no partial table/functions/triggers when transactional
migration execution fails. Production backup/PITR operations remain separate.

### Future validation and stop boundary

Focused future command:
`bash mvnw -B -q -Dtest=StationInternalGraphRevisionPostgresIntegrationTest,StationInternalGraphRevisionCodecTest,StationInternalGraphRevisionRepositoryPortTest,ArchitectureGuardrailTest test`.

Integration tests must execute, not merely skip for absent Docker. Cover labelled synthetic
serial/parallel and DRAFT/qualified declarations; original exact historical bytes after newer
appends; two distinct facility namespaces; exact absence/blank lookup; canonical permutation
replay; conflicting content; actual concurrent races and transaction rollback; empty/oversized
payload, invalid identity/format/digest; duplicate key and mutation guards; hash-consistent
malformed/noncanonical and mismatched identity payload; privileged digest-bypass read rejection
in disposable schema only. Check source/evidence/predecessor/time/qualification round trips;
supplied VERIFIED is not independent attestation. No manufacturer or live SCADA inputs.

Generate the dictionary through the existing collector/renderer against the actual full
migrated PostgreSQL 16 catalog and reviewed new ownership entry; verify only expected
additive catalog/source changes. Run canonical documentation/OpenAPI validators and
`bash mvnw -B -q clean verify` (or full CI's equivalent two-fork command), JPA validation
and generated OpenAPI equality/compatibility. If local dependencies/database cannot run,
report exact limitations and leave acceptance pending; never hand-author physical catalog
facts or count skipped integration tests as a pass.

| Updated active disposition | Status / next action |
|---|---|
| P35-B / HPR-P25-037 registration | Published documentation contract; exact-head Documentation CI acceptance PENDING |
| HPR-P25-037 implementation | PLANNED — separate owner Next after accepted registration |
| P35-C lifecycle / API | NOT REGISTERED — separate company-scope/actor/publication contract required |
| F1–F4 PLATFORM / SITE | End-to-end PLATFORM outcomes OPEN; no SITE or industrial acceptance granted |
| Simulator / P3 | DEFERRED; gas-first, optional GZ2 pilot, own-engine steady/transient direction preserved in history |

Stop after this registration's exact-head Documentation Validation starts. No implementation,
release, version change, closure, deployment or automatic next stage.

### Registration validation actually completed

- `python3 .github/scripts/test_validate_docs.py`: 10 tests PASS.
- `python3 .github/scripts/test_validate_openapi_snapshot.py`: 10 tests PASS.
- `python3 .github/scripts/test_generate_data_dictionary.py`: 17 tests PASS.
- `python3 .github/scripts/validate_docs.py`: PASS — 95 documents, 5,286 relative links, 24 modules, 13 P2 rows.
- `python3 .github/scripts/validate_openapi_snapshot.py`: PASS — offline snapshot only, 263 operations, 244 paths, 231 schemas.
- `git diff --check`: PASS after removing added blank EOF lines.
- Exact two-document allowlist, unchanged original prefixes and unchanged remaining Git tree checked before publication.
No Maven, JUnit, PostgreSQL, migration or industrial test execution is claimed for this documentation-only stage.


## 6. Governance and history

- [Full original task registry, completed actions, evidence and superseded proposals](history/ULTIMATE_ROADMAP_HISTORY.md) (unaltered original roadmap snapshot, `91e74c80c9ad327150174ddada08de058e5de7f7`).
- [PLATFORM acceptance matrix](PLATFORM_ACCEPTANCE.md): synthetic verification; no SITE certification.
- [Reconciliation history](../model-remediation/RECONCILIATION.md): independent source-backed records.
- [Agent governance](../../AGENTS.md): canonical task, commit, scope and header rules.

**Current execution selection:** Registered HPR-P25-037 implementation only after its acceptance prerequisites and separate owner Next. No code, migration, release, deployment or simulator implementation is approved by this documentation-only restructure.

## Legacy heading anchors — links retained for historical references

The corresponding full content is preserved in [the historical roadmap](history/ULTIMATE_ROADMAP_HISTORY.md). These anchors keep older document links resolvable without restoring the interleaved historical narrative.

<a id="hidraapi-ultimate-platform-finalization-roadmap"></a>
<a id="current-execution-roadmap--universal-hidra-platform-2026-10-11"></a>
<a id="product-invariant-and-two-independent-acceptance-gates"></a>
<a id="what-f1f4-mean-for-platform"></a>
<a id="compact-live-task-dashboard"></a>
<a id="clean-delivery-sequence--not-blanket-implementation-authorization"></a>
<a id="1-control-status"></a>
<a id="2-governing-rules"></a>
<a id="3-in-flight-semantic-remediation-disposition"></a>
<a id="4-state-of-the-platform-gate"></a>
<a id="5-execution-registry"></a>
<a id="phase-p0--security--code-defect-resolution"></a>
<a id="phase-p1--production-infrastructure--survivability"></a>
<a id="p1-independent-audit-remediation-baseline--2026-10-05"></a>
<a id="p1-independent-survivability-re-audit--2026-10-06"></a>
<a id="p1-final-survivability-closure--2026-10-06"></a>
<a id="phase-p2--canonical-governance-api-contracts--semantic-integration"></a>
<a id="phase-p25--operational-digital-twin-and-decision-feedback"></a>
<a id="verified-foundation-and-missing-execution-boundary"></a>
<a id="hpr-p25-001-requirements-and-execution-preflight--2026-10-09"></a>
<a id="exact-source-gap-and-ownership-matrix"></a>
<a id="required-product-and-physical-input-contract"></a>
<a id="decision-register--no-fabricated-operational-policy"></a>
<a id="acceptance-fixture-register--specifications-not-executed-tests"></a>
<a id="bounded-implementation-envelopes-and-next-selection"></a>
<a id="approved-direction-and-dynamic-input-decision-record--2026-10-09"></a>
<a id="dynamic-data-lifecycle"></a>
<a id="own-engine-decomposition-under-hpr-p25-008"></a>
<a id="next-bounded-preflight"></a>
<a id="hpr-p25-002-input-contract-execution-preflight--2026-10-09"></a>
<a id="source-revalidation-and-selected-technical-boundaries"></a>
<a id="input-decomposition-and-version-semantics"></a>
<a id="bounded-hpr-p25-002-implementation-stages"></a>
<a id="hpr-p25-002a-exact-implementation-envelope"></a>
<a id="hpr-p25-002b-physical-input-payload-preflight--2026-10-09"></a>
<a id="source-and-data-contract-findings"></a>
<a id="physical-payload-stages"></a>
<a id="hpr-p25-002b1-exact-next-implementation-envelope"></a>
<a id="execution-registry"></a>
<a id="boundaries-acceptance-gates-and-unresolved-decisions"></a>
<a id="bounded-task-selection-and-write-authorization"></a>
<a id="phase-p3--deferred-industrial-scale--future-capabilities"></a>
<a id="governed-release-preparation--separate-from-p3"></a>
<a id="owner-selected-production-ci-optimization--2026-10-09"></a>
<a id="6-immediate-next-execution"></a>
<a id="retained-p2-closure-disposition"></a>
<a id="hpr-p2-013-renewed-audit-and-closure-metadata-implementation--2026-10-09"></a>
<a id="hpr-p2-006-verification-and-renewed-hpr-p2-013-preflight--2026-10-09"></a>
<a id="renewed-bounded-execution"></a>
<a id="future-closure-metadata-allowlist--fifteen-existing-paths"></a>
<a id="this-supporting-publication"></a>
<a id="hpr-p2-006-stage-b-implementation--2026-10-09"></a>
<a id="hpr-p2-006-stage-a-implementation--2026-10-09"></a>
<a id="hpr-p2-006-concrete-database-correction-preflight--2026-10-09"></a>
<a id="verified-evidence-and-execution-limitation"></a>
<a id="ordered-two-stage-hpr-p2-006-execution-envelope"></a>
<a id="stage-a-exact-write-allowlist--six-paths"></a>
<a id="stage-a-extraction-protocol-and-acceptance"></a>
<a id="stage-b-exact-write-allowlist--eight-paths"></a>
<a id="stage-b-dictionarypolicy-acceptance-and-drift-verification"></a>
<a id="hpr-p2-005-api-correction-implementation--2026-10-09"></a>
<a id="hpr-p2-005-concrete-api-correction-preflight--2026-10-09"></a>
<a id="exact-implementation-write-allowlist"></a>
<a id="concrete-source-to-document-mapping"></a>
<a id="test-evidence-and-claim-limits"></a>
<a id="implementation-acceptance-and-validation"></a>
<a id="independent-p2-audit-analysis-and-bounded-correction-plan--2026-10-09"></a>
<a id="hpr-p2-005-bounded-correction--audit-check-9"></a>
<a id="hpr-p2-006-bounded-correction--audit-checks-10-and-11"></a>
<a id="hpr-p2-013-renewed-closure-gate"></a>
<a id="p1-evidence-block-parallel-progression-authorization--2026-10-06"></a>
<a id="hpr-p1-029-completion-evidence--2026-10-06"></a>
<a id="hpr-p1-029-initial-blocked-execution-evidence--2026-10-06-historical"></a>
<a id="hpr-p1-028-retention-enforcement-evidence"></a>
<a id="hpr-p1-027-database-operations-completion-evidence"></a>
<a id="hpr-p1-026-observability-wiring-evidence"></a>
<a id="hpr-p1-025-availability-preserving-rollout-evidence"></a>
<a id="hpr-p1-024-application-failover-recovery-evidence"></a>
<a id="hpr-p1-023-application-ha-exercise-hardening-evidence"></a>
<a id="hpr-p1-022-patronihaproxy-tls-alignment-evidence"></a>
<a id="hpr-p1-021-backuppitr-binding-evidence"></a>
<a id="hpr-p1-017-exact-head-verification-evidence"></a>
<a id="hpr-p1-017-retention-binding-repair-evidence"></a>
<a id="hpr-p1-017-monthly-retention-binding-evidence"></a>
<a id="p1-owner-value-approval-evidence--2026-10-05"></a>
<a id="hpr-p1-020-database-operations-implementation-evidence"></a>
<a id="hpr-p1-019-production-alerting-implementation-evidence"></a>
<a id="hpr-p1-018-controlled-release-implementation-evidence"></a>
<a id="hpr-p1-017-backupwalpitr-implementation-evidence"></a>
<a id="hpr-p1-016-postgresql-failover-implementation-evidence"></a>
<a id="hpr-p1-015-application-ha-implementation-evidence"></a>
<a id="hpr-p1-014-production-startup-safety-evidence"></a>
<a id="hpr-p1-013-production-infrastructure-decision-evidence"></a>
<a id="p1-independent-audit-ingestion-evidence"></a>
<a id="final-p0-closure-evidence"></a>
<a id="hpr-p1-001-runtime-architecture-evidence"></a>
<a id="hpr-p1-002-application-ha-decision-evidence"></a>
<a id="hpr-p1-003-postgresql-ha-decision-evidence"></a>
<a id="ci-documentation-validation-protocol-adjustment"></a>
<a id="hpr-p1-004-disaster-recovery-objective-evidence"></a>
<a id="hpr-p1-005-disaster-recovery-runbook-evidence"></a>
<a id="hpr-p1-006-high-availability-architecture-evidence"></a>
<a id="hpr-p1-007-production-deployment-documentation-evidence"></a>
<a id="hpr-p1-008-openapi-compatibility-evidence"></a>
<a id="hpr-p1-010-observabilitysre-evidence"></a>
<a id="hpr-p1-011-database-operations-runbook-evidence"></a>
<a id="7-original-p0-closure-evidence"></a>
<a id="8-independent-p0-verification-audit--gap-reconciliation"></a>
<a id="81-disposition-by-failed-check"></a>
<a id="82-gate-semantics"></a>
<a id="9-independent-p0-re-audit-closure"></a>
<a id="user-authorized-p2-batch-1--2026-10-06"></a>
<a id="user-authorized-p2-batch-2--2026-10-06"></a>
<a id="batch-2-regression-repair--hmr-052--ci-571"></a>
<a id="hpr-p2-008-solo-execution-admission--hmr-053--2026-10-06"></a>
<a id="hpr-p2-008-solo-execution-admission--hmr-054--2026-10-06"></a>
<a id="hmr-054-ci-574-guardrail-inventory-correction"></a>
<a id="hpr-p2-008-batch-5-execution-admission--2026-10-06"></a>
<a id="hpr-p2-008-batch-5-execution-result--2026-10-06"></a>
<a id="hpr-p2-008-batch-6-preflight-disposition--hmr-085--2026-10-06"></a>
<a id="hpr-p2-008-batch-6-scope-admission--hmr-085--2026-10-06"></a>
<a id="hpr-p2-008-batch-6-implementation--2026-10-06"></a>
<a id="hpr-p2-008-batch-6-ci-577-repair--hmr-085--2026-10-06"></a>
<a id="hpr-p2-008-batch-6-ci-578-repair--2026-10-06"></a>
<a id="hpr-p2-008-batch-7-preflight-disposition--2026-10-06"></a>
<a id="hpr-p2-008-batch-7-contract-and-scope-admission--2026-10-06"></a>
<a id="hmr-055-additional-exact-allowlist"></a>
<a id="hmr-061-additional-exact-allowlist"></a>
<a id="hmr-066-additional-exact-allowlist"></a>
<a id="hmr-081-additional-exact-allowlist"></a>
<a id="hmr-099-additional-exact-allowlist"></a>
<a id="batch-7-hmr-055--implemented-ci-pending"></a>
<a id="batch-7-hmr-061--implemented-ci-pending"></a>
<a id="batch-7-hmr-066--implemented-ci-pending"></a>
<a id="batch-7-hmr-081-fixture-scope-extension"></a>
<a id="batch-7-hmr-081--implemented-ci-pending"></a>
<a id="batch-7-hmr-099--implemented-ci-pending"></a>
<a id="hpr-p2-008-batch-7-implementation-and-validation-disposition--2026-10-06"></a>
<a id="hpr-p2-008-batch-7-ci-580-guardrail-repair-admission--2026-10-07"></a>
<a id="batch-7-ci-580-repair-implementation--2026-10-07"></a>
<a id="hpr-p2-008-batch-8-preflight-split--2026-10-07"></a>
<a id="hpr-p2-008-batch-8-contract-and-scope-admission--2026-10-07"></a>
<a id="hmr-064-admitted-files"></a>
<a id="hmr-065-admitted-files"></a>
<a id="batch-8-hmr-064--implemented-ci-pending"></a>
<a id="batch-8-hmr-065--implemented-ci-pending"></a>
<a id="hpr-p2-008-batch-8-final-disposition--2026-10-07"></a>
<a id="hpr-p2-008-batch-9-preflight-split--2026-10-07"></a>
<a id="hpr-p2-008-batch-9-accepted-contract-and-exact-scope--2026-10-07"></a>
<a id="hmr-067-exact-files"></a>
<a id="hmr-068-exact-files"></a>
<a id="hmr-084-exact-files"></a>
<a id="batch-9-hmr-067--implemented-ci-pending"></a>
<a id="batch-9-hmr-068--implemented-ci-pending"></a>
<a id="batch-9-hmr-084--implemented-ci-pending"></a>
<a id="hpr-p2-008-batch-9-final-disposition--2026-10-07"></a>
<a id="hpr-p2-008-batch-10-preflight-split--2026-10-07"></a>
<a id="hpr-p2-008-batch-10-accepted-policy-and-exact-scope--2026-10-07"></a>
<a id="hmr-083-admitted-files"></a>
<a id="hmr-095-admitted-files"></a>
<a id="hmr-101-admitted-files"></a>
<a id="hmr-102-admitted-files"></a>
<a id="batch-10-hmr-083--implemented-ci-pending"></a>
<a id="batch-10-hmr-095--implemented-ci-pending"></a>
<a id="batch-10-hmr-101--implemented-ci-pending"></a>
<a id="batch-10-hmr-102--implemented-ci-pending"></a>
<a id="hpr-p2-008-batch-10-final-disposition--2026-10-07"></a>
<a id="hpr-p2-008-batch-11-preflight-split--2026-10-07"></a>
<a id="hpr-p2-008-batch-11-accepted-resolver-and-exact-scope--2026-10-07"></a>
<a id="hmr-056-admitted-files"></a>
<a id="hmr-071-admitted-files"></a>
<a id="batch-11-hmr-056--implemented-ci-pending"></a>
<a id="batch-11-hmr-071--implemented-ci-pending"></a>
<a id="hpr-p2-008-batch-11-final-disposition--2026-10-07"></a>
<a id="hpr-p2-008-batch-12-reporting-prerequisite-preflight--2026-10-07"></a>
<a id="live-evidence-and-prerequisite-rep-prereq-01"></a>
<a id="concrete-decision-proposed-for-owner-acceptance"></a>
<a id="hpr-p2-008-batch-12-accepted-execution-and-exact-scope--2026-10-07"></a>
<a id="hmr-57-admitted-paths"></a>
<a id="hmr-93-admitted-paths"></a>
<a id="batch-12-hmr-057--implemented-ci-pending"></a>
<a id="batch-12-hmr-093--implemented-ci-pending"></a>
<a id="hpr-p2-008-batch-12-final-disposition--2026-10-07"></a>
<a id="batch-12-ci-586-regression-repair--2026-10-08"></a>
<a id="batch-12-ci-587-parameter-fixture-repair--2026-10-08"></a>
<a id="hpr-p2-008-batch-13-simulation-prerequisite-preflight--2026-10-08"></a>
<a id="sim-prereq-01--concrete-live-owner-boundary-and-write-scope-gap"></a>
<a id="concrete-proposal-for-owner-acceptance"></a>
<a id="hpr-p2-008-batch-13-accepted-execution-envelope--2026-10-08"></a>
<a id="exhaustive-hmr-078-write-scope"></a>
<a id="exhaustive-hmr-079-write-scope"></a>
<a id="batch-13-hmr-078-implementation-result--2026-10-08"></a>
<a id="batch-13-hmr-079-implementation-result--2026-10-08"></a>
<a id="hpr-p2-008-batch-13-ci-589-migration-fixture-repair--2026-10-08"></a>
<a id="ci-589-repair-result"></a>
<a id="hpr-p2-008-batch-14-risk-prerequisite-preflight--2026-10-08"></a>
<a id="risk-prereq-01--live-unregistered-ownerlifecycle-prerequisites"></a>
<a id="concrete-proposal-for-owner-acceptance-1"></a>
<a id="hpr-p2-008-batch-14-accepted-execution-envelope--2026-10-08"></a>
<a id="exhaustive-hmr-077-scope"></a>
<a id="exhaustive-hmr-058-scope"></a>
<a id="hmr-077-implementation--owner-validated-risk-evidence"></a>
<a id="hpr-p2-008-batch-14-implementation-result--2026-10-08"></a>
<a id="assessment-obligations-and-enforcement"></a>
<a id="forward-migrations-and-api"></a>
<a id="actual-local-validation-and-limits"></a>
<a id="hpr-p2-008-batch-14--ci-591-repair-admission--2026-10-08"></a>
<a id="ci-591-repair-result"></a>
<a id="hpr-p2-008-batch-14--ci-592-compatibility-base-repair--2026-10-08"></a>
<a id="ci-592-repair-result"></a>
<a id="hpr-p2-008-batch-15-incident-execution-preflight--2026-10-08"></a>
<a id="inc-prereq-01--independently-recovered-live-gaps"></a>
<a id="concrete-execution-proposal-for-acceptance"></a>
<a id="current-disposition-and-validation"></a>
<a id="hpr-p2-008-batch-15-accepted-execution-envelope--2026-10-08"></a>
<a id="exhaustive-hmr-062-scope"></a>
<a id="exhaustive-hmr-091-scope"></a>
<a id="exhaustive-hmr-092-scope"></a>
<a id="exhaustive-hmr-090-scope"></a>
<a id="batch-15-hmr-062-implementation-result--2026-10-08"></a>
<a id="batch-15-hmr-091-implementation-result--2026-10-08"></a>
<a id="batch-15-hmr-092-implementation-result--2026-10-08"></a>
<a id="batch-15-hmr-090-implementation-result--2026-10-08"></a>
<a id="hpr-p2-008-batch-15-final-implementation-disposition--2026-10-08"></a>
<a id="hpr-p2-008-batch-15-ci-594-repair-admission--2026-10-08"></a>
<a id="hpr-p2-008-batch-16-execution-preflight--2026-10-08"></a>
<a id="work-prereq-01--live-owner-contract-and-migration-order-gaps"></a>
<a id="concrete-batch-16-execution-proposal-for-acceptance"></a>
<a id="proposed-exhaustive-per-hmr-write-scopes"></a>
<a id="hmr-069-proposed-scope"></a>
<a id="hmr-070-proposed-scope"></a>
<a id="hmr-072-proposed-scope"></a>
<a id="preflight-disposition"></a>
<a id="hpr-p2-008-batch-16-accepted-execution-envelope--2026-10-08"></a>
<a id="batch-16-hmr-069-implementation-result--2026-10-08"></a>
<a id="batch-16-hmr-070-implementation-result--2026-10-08"></a>
<a id="batch-16-hmr-072-implementation-result--2026-10-08"></a>
<a id="hpr-p2-008-batch-16-final-implementation-disposition--2026-10-08"></a>
<a id="hpr-p2-008-batch-17-hse-execution-preflight--2026-10-08"></a>
<a id="hse-prereq-01--independently-recovered-live-scope-and-policy-gaps"></a>
<a id="concrete-coordinated-execution-proposal-for-acceptance"></a>
<a id="proposed-exhaustive-per-hmr-write-scopes-1"></a>
<a id="hmr-082-proposed-scope"></a>
<a id="hmr-096-proposed-scope"></a>
<a id="hmr-097-proposed-scope"></a>
<a id="current-disposition-and-preflight-validation"></a>
<a id="hpr-p2-008-batch-17-accepted-execution-envelope--2026-10-08"></a>
<a id="batch-17-hmr-082-implementation-result--2026-10-08"></a>
<a id="batch-17-hmr-096-implementation-result--2026-10-08"></a>
<a id="batch-17-hmr-097-implementation-result--2026-10-08"></a>
<a id="hpr-p2-008-batch-18-integritycase-execution-preflight--2026-10-08"></a>
<a id="ic-prereq-01--independently-recovered-scope-and-taxonomy-gaps"></a>
<a id="concrete-solo-execution-proposal-for-acceptance"></a>
<a id="exhaustive-proposed-hmr-098-write-scope"></a>
<a id="current-disposition-and-preflight-validation-1"></a>
<a id="hpr-p2-008-batch-18-accepted-execution-envelope--2026-10-08"></a>
<a id="batch-18-hmr-098-implementation-result--2026-10-08"></a>
<a id="hpr-p2-008-batch-19-planning-targets-and-monitoring-execution-preflight--2026-10-08"></a>
<a id="ptmd-prereq-01--independently-recovered-write-policy-and-owner-contract-gaps"></a>
<a id="concrete-proposed-two-commit-execution-envelope"></a>
<a id="exhaustive-proposed-hmr-094-scope"></a>
<a id="exhaustive-proposed-hmr-103-scope"></a>
<a id="current-disposition-and-preflight-validation-2"></a>
<a id="hpr-p2-008-batch-19-accepted-execution--hmr-094--2026-10-08"></a>
<a id="hpr-p2-008-batch-19-independent-execution--hmr-103--2026-10-08"></a>
<a id="hpr-p2-008-batch-19-ci-599-repair--hmr-094--2026-10-08"></a>
<a id="hpr-p2-008-batch-19-ci-closure-and-batch-20-execution-preflight--2026-10-08"></a>
<a id="verified-position-and-attached-selection"></a>
<a id="live-hmsr-recovery-and-prerequisite-disposition"></a>
<a id="concrete-proposed-four-commit-design"></a>
<a id="independent-exact-scopes-and-semantic-messages"></a>
<a id="hmr-100--fixalarm-remediate-semantic-review-alarm"></a>
<a id="hmr-104--fixalarm-remediate-semantic-review-alarmacknowledgement"></a>
<a id="hmr-105--fixalarm-remediate-semantic-review-alarmclosure"></a>
<a id="hmr-106--fixalarm-remediate-semantic-review-alarmshelving"></a>
<a id="forward-migration-authorization-proposed"></a>
<a id="admitted-validation-proposal"></a>
<a id="publication-and-next-owner-action"></a>
<a id="hpr-p2-008-batch-20-acceptance--2026-10-08"></a>
<a id="hpr-p2-008-batch-20-independent-execution--hmr-100--2026-10-08"></a>
<a id="hpr-p2-008-batch-20-independent-execution--hmr-104--2026-10-08"></a>
<a id="hpr-p2-008-batch-20-independent-execution--hmr-105--2026-10-08"></a>
<a id="hpr-p2-008-batch-20-independent-execution--hmr-106--2026-10-08"></a>
<a id="final-tree-validation-and-truthful-limits"></a>
<a id="hpr-p2-008-batch-20-ci-601-repair-admission--2026-10-08"></a>
<a id="repair-validation-and-publication-disposition"></a>
<a id="hpr-p2-008-batch-20-confirmation-and-nomination-ownership-preflight--2026-10-08"></a>
<a id="solo-hmr-080-live-prerequisite-recovery"></a>
<a id="documentation-publication"></a>
<a id="hpr-p2-008-solo-hmr-080-ownership-acceptance-and-execution-preflight--2026-10-08"></a>
<a id="prerequisite-disposition-and-next-registered-execution"></a>
<a id="complete-proposed-design"></a>
<a id="exact-independent-hmr-080-write-scope"></a>
<a id="forward-migration-design-and-historical-transition"></a>
<a id="admitted-implementation-validation"></a>
<a id="preflight-publication-and-next-action"></a>
<a id="hpr-p2-008-solo-hmr-080-execution-acceptance--2026-10-08"></a>
<a id="hpr-p2-008-solo-hmr-080-implementation--2026-10-08"></a>
<a id="hpr-p2-008-solo-hmr-080-ci-603-repair--2026-10-08"></a>
<a id="hpr-p2-008-solo-hmr-080-ci-confirmation--2026-10-09"></a>
<a id="hpr-p2-008-final-reconciliation-preflight--2026-10-09"></a>
<a id="bounded-current-source-reconciliation"></a>
<a id="canonical-drift-corrected"></a>
<a id="remaining-closure-and-deployment-boundaries"></a>
<a id="supporting-publication-scope-and-checks"></a>
<a id="hpr-p2-008-owner-authorized-closure--2026-10-09"></a>
<a id="hpr-p2-009-semantic-canonicalization-preflight--2026-10-09"></a>
<a id="selection-gates-and-publication-boundary"></a>
<a id="recovered-evidence-and-reason-for-the-envelope"></a>
<a id="complete-implementation-design"></a>
<a id="exhaustive-future-implementation-write-scope--37-paths"></a>
<a id="admitted-validation-and-completion-evidence"></a>
<a id="this-preflights-narrow-supporting-publication"></a>
<a id="hpr-p2-009-accepted-execution--2026-10-09"></a>
<a id="completed-transfer-and-validation"></a>
<a id="hpr-p2-010-data-governance-preflight--2026-10-09"></a>
<a id="selection-and-verified-gates"></a>
<a id="current-source-findings"></a>
<a id="complete-proposed-documentation-design"></a>
<a id="exhaustive-future-implementation-write-scope--nine-paths"></a>
<a id="admitted-validation-and-publication"></a>
<a id="this-preflights-supporting-scope"></a>
<a id="hpr-p2-010-accepted-execution--2026-10-09"></a>
<a id="hpr-p2-010-completed-governance-baseline--2026-10-09"></a>
<a id="implementation-and-evidence-limits"></a>
<a id="actual-local-validation"></a>
<a id="hpr-p2-011-verification-documentation-preflight--2026-10-09"></a>
<a id="selection-and-exact-baseline"></a>
<a id="current-evidence-recovered"></a>
<a id="proposed-implementation-design--ten-parts"></a>
<a id="exhaustive-future-implementation-write-scope--ten-paths"></a>
<a id="admitted-implementation-validation-and-publication"></a>
<a id="this-preflights-supporting-execution"></a>
<a id="actual-preflight-validation"></a>
<a id="hpr-p2-011-accepted-execution--2026-10-09"></a>
<a id="hpr-p2-011-completed-verification-documentation--2026-10-09"></a>
<a id="actual-implementation-and-limits"></a>
<a id="actual-local-validation-1"></a>
<a id="hpr-p2-012-canonical-validation-preflight--2026-10-09"></a>
<a id="selection-and-verified-baseline"></a>
<a id="current-validation-and-concrete-drift-recovered"></a>
<a id="accepted-on-next-proposed-design--twelve-parts"></a>
<a id="exhaustive-future-implementation-write-scope--21-paths"></a>
<a id="admitted-validation-and-publication-1"></a>
<a id="this-preflights-supporting-execution-and-actual-checks"></a>
<a id="actual-supporting-validation-result"></a>
<a id="hpr-p2-012-accepted-execution--2026-10-09"></a>
<a id="hpr-p2-012-actual-implementation-checks--2026-10-09"></a>
<a id="hpr-p2-013-canonical-governance-closure-preflight--2026-10-09"></a>
<a id="selection-and-verified-baseline-1"></a>
<a id="actual-read-only-review-and-concrete-closure-defect"></a>
<a id="proposed-accepted-on-next-bounded-design"></a>
<a id="exhaustive-future-implementation-write-scope--thirteen-existing-paths"></a>
<a id="supporting-preflight-publication-and-actual-validation"></a>
<a id="hpr-p2-013-closure-scope-re-preflight--2026-10-09"></a>
<a id="accepted-selection-verified-gates-and-responsible-scope-stop"></a>
<a id="additional-current-source-evidence"></a>
<a id="revised-accepted-on-next-proposal"></a>
<a id="exhaustive-revised-future-implementation-scope--sixteen-existing-paths"></a>
<a id="actual-checks-and-supporting-publication"></a>
<a id="hpr-p2-013-accepted-implementation--2026-10-09"></a>
<a id="hpr-p2-013-actual-implementation-validation"></a>
<a id="hpr-p2-013-final-exact-head-verification--2026-10-09"></a>
<a id="hpr-p2-013-renewed-final-exact-head-verification--2026-10-09"></a>
<a id="hpr-rel-001-bounded-release-alignment-preparation--2026-10-09"></a>
<a id="hpr-rel-001-exact-candidate-verification--2026-10-09"></a>
<a id="hpr-rel-002-published-release-record-and-development-transition--2026-10-09"></a>
<a id="hpr-rel-002-final-exact-transition-verification--2026-10-09"></a>
<a id="hpr-p25-002a-immutable-input-manifest-implementation--2026-10-09"></a>
<a id="hpr-ci-001-verified-optimization-result--2026-10-09"></a>
<a id="hpr-p25-002b-physical-payload-preflight--2026-10-09"></a>
<a id="hpr-p25-002b1-physical-network-input-implementation--2026-10-09"></a>
<a id="hpr-p25-002b2-fluid-and-equipment-input-preflight--2026-10-09"></a>
<a id="current-source-and-engineering-boundary"></a>
<a id="exact-b2-implementation-envelope-for-a-later-owner-next"></a>
<a id="assembly-and-graph-limits"></a>
<a id="required-meaningful-synthetic-tests-and-checks"></a>
<a id="hpr-p25-002b2-fluid-and-equipment-input-implementation--2026-10-09"></a>
<a id="hpr-p25-002b3-state-and-boundary-timeline-preflight--2026-10-09"></a>
<a id="source-findings-and-boundary"></a>
<a id="exact-later-b3-implementation-envelope"></a>
<a id="missing-data-completeness-and-follow-up-gates"></a>
<a id="meaningful-synthetic-tests-and-validation"></a>
<a id="hpr-p25-002b3-state-and-boundary-timeline-implementation--2026-10-09"></a>
<a id="hpr-p25-002b4-consistent-physical-payload-preflight--2026-10-09"></a>
<a id="source-backed-design-and-graph-prerequisite-resolution"></a>
<a id="exact-b4-implementation-envelope-for-a-later-owner-next"></a>
<a id="meaningful-synthetic-tests-and-required-verification"></a>
<a id="hpr-p25-002b4-consistent-physical-payload-implementation--2026-10-09"></a>
<a id="hpr-p25-002c-owner-query-and-adapter-preflight--2026-10-09"></a>
<a id="owner-evidence-and-missing-data-boundary"></a>
<a id="bounded-c-stages"></a>
<a id="c1-exact-next-implementation-envelope"></a>
<a id="required-c1-validation-and-stop-boundary"></a>
<a id="hpr-p25-002c1-trusted-reading-evidence-implementation--2026-10-09"></a>
<a id="hpr-p25-002c1-r1-failed-ci-export-registry-repair--2026-10-09"></a>
<a id="hpr-p25-002c2-topology-and-product-evidence-preflight--2026-10-09"></a>
<a id="revalidated-source-and-eligibility-boundary"></a>
<a id="c2-exact-next-implementation-envelope"></a>
<a id="exact-contract-adapter-and-missing-input-semantics"></a>
<a id="meaningful-tests-and-validation"></a>
<a id="hpr-p25-002c2-owner-eligibility-evidence-implementation--2026-10-09"></a>
<a id="hpr-p25-002c3-qualified-physical-source-preflight--2026-10-09"></a>
<a id="source-backed-gaps-and-qualification-disposition"></a>
<a id="source-storage-and-approval-design-decisions"></a>
<a id="bounded-c3-execution-registry"></a>
<a id="c3a-exact-next-implementation-envelope"></a>
<a id="c3a-meaningful-validation-and-stop-boundary"></a>
<a id="hpr-p25-002-consolidated-delivery-plan--2026-10-09"></a>
<a id="four-delivery-outcomes-and-acceptance-evidence"></a>
<a id="consolidated-execution-protocol"></a>
<a id="next-registered-selection"></a>
<a id="engine-work-and-phase-25-position"></a>
<a id="hpr-p25-002-shared-network-revision-delivery-preflight--2026-10-09"></a>
<a id="selection-evidence-and-retained-tasks"></a>
<a id="source-review-and-representation-choice"></a>
<a id="c3a-retained-exact-scope-and-requirements"></a>
<a id="c3b-exact-write-allowlist"></a>
<a id="exact-owner-ports-and-public-query-boundary"></a>
<a id="canonical-bytes-and-digest"></a>
<a id="authorized-forward-migration-and-append-semantics"></a>
<a id="acceptance-tests-and-complete-verification"></a>
<a id="current-validation-and-next-control"></a>
<a id="hpr-p25-002c3a-local-candidate--2026-10-09"></a>
<a id="hpr-p25-002c3b-network-delivery-candidate--2026-10-09"></a>
<a id="hpr-p25-002-network-candidate-java-21-validation--2026-10-09"></a>
<a id="hpr-p25-002-network-candidate-postgresql-evidence--2026-10-09"></a>
<a id="hpr-p25-002-network-candidate-dictionary-regeneration--2026-10-09"></a>
<a id="hpr-p25-002-network-revision-delivery-publication--2026-10-10"></a>
<a id="hpr-p25-002-shared-fluid-equipment-delivery-preflight--2026-10-10"></a>
<a id="authority-verified-base-and-owner-approved-boundary"></a>
<a id="current-source-evidence-and-honest-qualification-meaning"></a>
<a id="c3c-exact-content-and-validation"></a>
<a id="shared-workflow-approval-evidence-implemented-once-in-c3c"></a>
<a id="c3d-exact-engineering-parameter-content-and-validation"></a>
<a id="separate-append-only-schema-and-canonical-persistence"></a>
<a id="binding-c3c-file-allowlist"></a>
<a id="binding-c3d-file-allowlist"></a>
<a id="acceptance-and-exact-validation"></a>
<a id="publication-protocol-and-retained-position"></a>
<a id="hpr-p25-002-shared-fluid-equipment-delivery-preflight--characteristic-provenance-correction--2026-10-10"></a>
<a id="verified-position-and-material-gap"></a>
<a id="binding-replacement-fields-and-qualification-rules"></a>
<a id="acceptance-and-unchanged-execution-envelope"></a>
<a id="hpr-p25-002c3c-fluid-source-candidate--2026-10-10"></a>
<a id="hpr-p25-002c3c-actual-postgresql-candidate-evidence--2026-10-10"></a>
<a id="hpr-p25-002c3c-accepted-gas-fluid-revision-delivery--2026-10-10"></a>
<a id="hpr-p25-002c3d-equipment-parameter-revision-candidate--2026-10-10"></a>
<a id="c3d-candidate-629-dictionary-repair--2026-10-10"></a>
<a id="hpr-p25-002c3d-accepted-equipment-parameter-delivery--2026-10-10"></a>
<a id="hpr-p25-002-shared-operating-state-delivery-preflight--2026-10-10"></a>
<a id="selection-and-immutable-baseline"></a>
<a id="exact-source-observations-and-unresolved-qualification-boundary"></a>
<a id="hpr-p25-002c3e-delivery-contract--blocked-implementation-authorization"></a>
<a id="acceptance-and-next-state-control"></a>
<a id="hpr-p25-pilot-selection-amendment--gz4-first-gas-demonstrator-2026-10-10"></a>
<a id="owner-selection-and-scope"></a>
<a id="qualification-and-evidence-gates"></a>
<a id="hpr-p25-002c3e-synthetic-gz4-fixture-evidence-admission--2026-10-10"></a>
<a id="decision-and-provenance"></a>
<a id="admissible-synthetic-only-verification-coverage"></a>
<a id="c3e-gates-preserved-no-implementation-allowlist"></a>
<a id="hpr-p25-002c3e--synthetic-fixture-to-contract-specification-2026-10-10"></a>
<a id="authorization-and-exact-reference-head"></a>
<a id="verified-contracts-and-fixture-admissibility"></a>
<a id="bounded-target-behavior--not-implementation-authorization"></a>
<a id="required-remaining-source-policy-approvals-and-exact-file-preflight"></a>
<a id="hpr-p25-008a--internal-gas-numerical-design-execution-preflight-2026-10-10"></a>
<a id="selected-task-and-verified-evidence"></a>
<a id="hpr-p25-008a-next-design-envelope-requires-separate-owner-next-after-passing-documentation-ci"></a>
<a id="exact-design-output-ownership-and-gates"></a>
<a id="hpr-p25-008a--exact-design-document-allowlist-resolution-2026-10-10"></a>
<a id="hpr-p25-008a--numerical-design-delivery-2026-10-10"></a>
<a id="hpr-p25-008b--steady-state-gas-solver-execution-preflight-2026-10-10"></a>
<a id="exact-source-prerequisites-and-bounded-selection"></a>
<a id="preflight-only-filecommit-boundary"></a>
<a id="required-exact-file-implementation-authorization-before-any-008b-code"></a>
<a id="hpr-p25-008b--exact-javatest-write-allowlist-authorization-2026-10-10"></a>
<a id="evidence-and-scope-of-owner-next"></a>
<a id="exact-authorized-later-implementation-file-set"></a>
<a id="physical-and-numerical-admissibility-limitations"></a>
<a id="later-validation-and-progress-control"></a>
<a id="hpr-p25-008b-implementation-candidate--2026-10-10"></a>
<a id="hpr-p25-008b-ci-632-forensic-repair--2026-10-10"></a>
<a id="hpr-p25-008b--post-repair-acceptance-adequacy-review-2026-10-10"></a>
<a id="code-backed-coverage"></a>
<a id="unmet-original-hpr-p25-008b-acceptance-gates"></a>
<a id="exact-next-step-rule"></a>
<a id="hpr-p25-008b--acceptance-remediation-execution-preflight-2026-10-10"></a>
<a id="exact-selection-and-verification"></a>
<a id="required-source-and-mathematical-review-for-the-next-separate-authorization"></a>
<a id="sequential-gate"></a>
<a id="hpr-p25-008b--remediation-exact-file-and-independent-benchmark-authorization-2026-10-10"></a>
<a id="selection-source-review-and-status"></a>
<a id="independent-equation-and-dimensional-review"></a>
<a id="minimum-mixed-boundary-connected-network-formulation"></a>
<a id="independently-calculated-benchmark-inputs-and-machine-acceptance"></a>
<a id="exact-future-implementation-allowlist-and-owner-contracts"></a>
<a id="future-validation-and-publication-gates"></a>
<a id="hpr-p25-008b--connected-synthetic-gas-network-remediation-delivery-2026-10-10"></a>
<a id="hpr-p25-008b--verified-synthetic-numerical-acceptance-2026-10-10"></a>
<a id="hpr-p25-008c--equipment-execution-preflight-2026-10-10"></a>
<a id="binding-owner-clarification-use-available-data-optional-requests"></a>
<a id="source-findings-and-reusable-evidence"></a>
<a id="material-methodschema-gaps-and-bounded-disposition"></a>
<a id="candidate-implementation-envelope-and-independent-acceptance"></a>
<a id="next-selection-validation-and-publication"></a>
<a id="hpr-p25-008c--synthetic-equipment-methods-and-implementation-authorization-2026-10-10"></a>
<a id="revalidated-source-ownership-and-primary-technical-basis"></a>
<a id="named-executable-synthetic-semantics"></a>
<a id="union-network-input-equations-and-result-design"></a>
<a id="independently-derived-references-and-fixed-numerical-acceptance"></a>
<a id="final-exact-file-implementation-allowlist-and-outstanding-regulator-obligation"></a>
<a id="current-documentation-validation-and-stop-condition"></a>
<a id="hpr-p25-008c--synthetic-compressorvalve-code-candidate-2026-10-10"></a>
<a id="hpr-p25-008c--synthetic-equipment-candidate-numerical-acceptance-review-2026-10-10"></a>
<a id="verified-head--ci--exact-prior-scope"></a>
<a id="contract-and-mathematical-implementation-evidence"></a>
<a id="exact-outstanding-numerical-acceptance-gaps"></a>
<a id="remaining-equipment-family-obligation-and-next-gate"></a>
<a id="hpr-p25-008c--independent-numerical-test-coverage-remediation-authorization-2026-10-10"></a>
<a id="one-bounded-owner-next-verified-evidence-and-commit"></a>
<a id="subsequent-separately-selected-008c-test-only-write-allowlist"></a>
<a id="frozen-independent-reference-cases-and-tolerances"></a>
<a id="gates-exclusions-and-next-permissible-selection"></a>
<a id="hpr-p25-008c--bounded-independent-numerical-test-remediation-candidate-2026-10-10"></a>
<a id="hpr-p25-008c--independent-post-repair-numerical-acceptance-review-2026-10-10"></a>
<a id="hpr-p25-008c--numerical-acceptance-completion-test-scope-authorization-2026-10-10"></a>
<a id="owner-next-evidence-and-exact-one-task-boundary"></a>
<a id="separate-future-test-only-continuation-exactly-five-allowed-update-paths"></a>
<a id="frozen-evidence-and-non-negotiable-acceptance"></a>
<a id="hpr-p25-008c--remaining-independent-numerical-matrix-test-candidate-2026-10-10"></a>
<a id="hpr-p25-008c--independent-numerical-acceptance-disposition-after-exact-head-ci-639-2026-10-10"></a>
<a id="independent-numerical-reference-and-coverage-assessment"></a>
<a id="remaining-bounded-reasons-not-to-assert-complete-compressorvalve-numerical-acceptance"></a>
<a id="hpr-p25-008c--final-narrow-compressorvalve-acceptance-test-preflight-2026-10-10"></a>
<a id="selection-and-verified-baseline-2"></a>
<a id="next-separately-selected-narrower-three-file-test-only-correction"></a>
<a id="fixed-mathematical-acceptance-and-ci-gates"></a>
<a id="hpr-p25-008c--final-narrow-numerical-acceptance-test-candidate-2026-10-10"></a>
<a id="hpr-p25-008c--compressorvalve-synthetic-numerical-acceptance-disposition-2026-10-10"></a>
<a id="actual-exact-commit-ci-and-independent-mathematical-evidence"></a>
<a id="explicit-acceptance-boundary-and-residual-obligations"></a>
<a id="hpr-p25-008c--regulator-cross-module-sourceschemaownership-preflight-2026-10-10"></a>
<a id="one-action-selection-and-immutable-baseline"></a>
<a id="verified-active-source-contracts-and-owner-boundaries"></a>
<a id="regulator-method-decision-that-must-be-made-before-code"></a>
<a id="candidate-readreview-scope-for-the-next-authorization-not-a-production-write-allowlist"></a>
<a id="disposition-and-next-authorized-gate"></a>
<a id="hpr-p25-008c--regulator-synthetic-methodcontract-authorization-2026-10-10"></a>
<a id="selection-exact-scope-and-stop-rule"></a>
<a id="source-derived-ownership-and-compatibility-boundary"></a>
<a id="selected-development-only-mathematical-candidate-not-a-field-law"></a>
<a id="independent-frozen-synthetic-design-oracles-and-future-gates"></a>
<a id="future-work-partition-no-java-authorization-granted"></a>
<a id="p25--three-engine-independent-numerical-reference-evaluation-amendment-2026-10-10"></a>
<a id="owner-selection-evidence-classification-exact-scope"></a>
<a id="candidate-versions-ownership-strengths-and-compatibility-traps"></a>
<a id="ordered-researchverification-workstream--no-implementation-authorization"></a>
<a id="preservation-of-roadmap-gates-next-execution"></a>
<a id="hpr-p25-008c--regulator-slice-a-topology-contract-implementation-preflight-2026-10-10"></a>
<a id="selected-action-exact-baseline-and-documentation-only-write-scope"></a>
<a id="source-backed-topology-findings-and-consequential-versioning-decision"></a>
<a id="exact-closed-allowlist-for-a-separately-selected-slice-a-implementation"></a>
<a id="future-implementation-acceptance-gates-present-disposition"></a>
<a id="hpr-p25-008c--regulator-slice-a-topology-implementation-candidate-2026-10-10"></a>
<a id="selected-bounded-code-execution-exact-upstream-evidence"></a>
<a id="implemented-topology-only-candidate-compatibility-invariants"></a>
<a id="deliberate-slice-b-boundary-and-acceptance-gate"></a>
<a id="hpr-p25-008c--regulator-slice-a-database-dictionary-ci-remediation-preflight-2026-10-11"></a>
<a id="selection-current-failure-and-bounded-action"></a>
<a id="sourceprecedent-review-and-selected-future-repair-procedure-not-executed-here"></a>
<a id="status-and-stop"></a>
<a id="hpr-p25-008c--regulator-slice-a-authenticated-physical-dictionary-synchronization-2026-10-11"></a>
<a id="owner-selection-exact-scope-previous-blockers"></a>
<a id="authenticated-exact-head-postgresql--maintained-generator-evidence"></a>
<a id="pending-exact-head-final-verification-and-scope-boundary"></a>
<a id="hpr-p25-008c--regulator-slice-a-verified-acceptance-disposition-2026-10-11"></a>
<a id="selected-action-and-exact-evidence-baseline"></a>
<a id="independent-sourcescope-review-and-accepted-boundary"></a>
<a id="remaining-work-and-next-selected-action"></a>
<a id="hpr-p25-008c--regulator-slice-b-simulation-source-and-storage-preflight-2026-10-11"></a>
<a id="selection-baseline-and-current-write-boundary"></a>
<a id="recovered-source-findings"></a>
<a id="concrete-bounded-slice-b-design-for-subsequent-owner-selection"></a>
<a id="registered-future-implementation-scope-and-verification"></a>
<a id="present-status-and-stop"></a>
<a id="hpr-p25-008c-regulator-slice-b-candidate--compile-blocker-and-scope-correction-2026-10-11"></a>
<a id="prepared-candidate-and-actual-validation"></a>
<a id="concrete-newly-discovered-compile-prerequisite"></a>
<a id="current-disposition"></a>
<a id="hpr-p25-008c-regulator-slice-b-implementation-candidate-and-ci-handoff-2026-10-11"></a>
<a id="actual-local-validation-and-its-limits"></a>
<a id="publication-disposition-and-mandatory-remaining-reconciliation"></a>
<a id="2026-10-11--ci-643-repair-authentic-slice-b-regulator-dictionary-reconciliation"></a>
<a id="2026-10-11--flexible-operator-configured-simulation-roadmap-realignment"></a>
<a id="authorization-exact-baseline-and-current-execution-gate"></a>
<a id="audit-method-and-evidence-limitations"></a>
<a id="engineering-data-persistence-and-provisioning-audit"></a>
<a id="numerical-and-execution-architecture-assessment"></a>
<a id="binding-target-architecture-decisions"></a>
<a id="externalreference-provider-research-disposition--checked-2026-10-11"></a>
<a id="dependency-realignment-and-foundation-milestones"></a>
<a id="newly-admitted-bounded-task-registry"></a>
<a id="hpr-p25-018--complete-supported-engineering-authoring-before-operational-use"></a>
<a id="hpr-p25-019--authoritative-route-geodesy-and-sectionprofile-preservation"></a>
<a id="hpr-p25-020--independent-installed-units-and-station-hydraulic-configurations"></a>
<a id="hpr-p25-021--manufacturer-evidence-as-reusable-versioned-engineering-data"></a>
<a id="hpr-p25-022--product-definitions-and-property-methods-with-explicit-applicability"></a>
<a id="hpr-p25-023--baseline-manifests-scenario-overlays-and-historical-retrieval"></a>
<a id="hpr-p25-024--configurable-models-and-eligible-internalexternal-providers"></a>
<a id="hpr-p25-025--one-validated-request-lifecycle-for-manual-and-proactive-modes"></a>
<a id="hpr-p25-026--calibration-ready-correspondence-without-changing-asset-facts"></a>
<a id="hpr-p25-027--independent-end-to-end-engineering-and-productmode-acceptance"></a>
<a id="independent-future-acceptance-cases-ai"></a>
<a id="preservation-and-next-action"></a>
<a id="exact-source-evidence-index"></a>
<a id="actual-amendment-validation-and-publication-disposition"></a>
<a id="2026-10-11--owner-decision-defer-simulator-and-prioritize-engineering-foundations"></a>
<a id="current-authority-live-baseline-and-selected-scope"></a>
<a id="truthful-regulator-and-pending-source-disposition"></a>
<a id="source-and-ownership-findings-supporting-the-split"></a>
<a id="classification-rules-and-complete-inventory-coverage"></a>
<a id="binding-scope-and-dependency-changes-for-engineering-design-tasks"></a>
<a id="track-a--active-engineering-and-operational-foundation-milestones"></a>
<a id="dependency-graph-and-hidden-edge-disposition"></a>
<a id="new-independently-executable-split-tasks"></a>
<a id="hpr-p25-028--authoritative-engineering-baseline-management-preflight"></a>
<a id="hpr-p25-029--independent-operational-state-and-action-evidence-preflight"></a>
<a id="hpr-p25-030--engineering-foundation-acceptance-design-preflight"></a>
<a id="hpr-p25-031--independent-engineering-foundation-closure-audit"></a>
<a id="active-foundation-acceptance-cases--independent-of-simulation"></a>
<a id="track-b--deferred-simulator-registry-and-reactivation"></a>
<a id="preservation-and-single-next-eligible-task"></a>
<a id="complete-affected-task-disposition-matrix"></a>
<a id="complete-remaining-roadmap-inventory--preserved-and-independent-obligations"></a>
<a id="amendment-validation-and-publication-boundary"></a>
<a id="2026-10-11--hpr-p25-018-engineering-provisioning-and-canonical-ownership-preflight"></a>
<a id="selection-source-authority-and-scope"></a>
<a id="source-backed-existing-capability-and-gap-inventory"></a>
<a id="canonical-ownership-and-reference-contract"></a>
<a id="operator-authoring-lifecycle-and-completeness-semantics"></a>
<a id="api-persistence-and-failure-behavior-design"></a>
<a id="owner-separated-implementation-envelopes--design-only-not-executable-tasks"></a>
<a id="required-acceptance-mapping-and-negative-cases"></a>
<a id="current-task-disposition-and-next-selection-boundary"></a>
<a id="executed-hpr-p25-018-validation"></a>
<a id="2026-10-11--hpr-p25-019-revisioned-route-elevation-profile-and-pipe-engineering-preflight"></a>
<a id="live-baseline-prior-acceptance-and-selected-scope"></a>
<a id="current-source-findings-and-evidence-anchors"></a>
<a id="canonical-ownership-and-p3-overlap-resolution"></a>
<a id="proposed-semantic-model--independent-of-storage-technology"></a>
<a id="coordinates-elevation-chainage-and-dimensions"></a>
<a id="authoring-revision-and-owner-api-design"></a>
<a id="persistence-integrity-compatibility-and-proposed-envelopes"></a>
<a id="required-future-acceptance-and-negative-evidence"></a>
<a id="deferred-numerical-consumption-and-future-compatibility"></a>
<a id="current-status-and-next-task"></a>
<a id="executed-hpr-p25-019-validation-and-publication-boundary"></a>
<a id="2026-10-11--hpr-p25-020-station-and-installed-unit-engineering-provisioning-preflight"></a>
<a id="acceptance-boundary-and-disposition"></a>
<a id="inspected-live-ownership-and-compatibility"></a>
<a id="canonical-engineering-objects-and-invariants"></a>
<a id="graph-and-configuration-semantics"></a>
<a id="authorized-workflows-validation-and-negative-cases"></a>
<a id="bounded-implementation-envelopes--not-authorized-by-this-preflight"></a>
<a id="task-status-and-evidence-boundary"></a>
<a id="2026-10-11--hpr-p25-021-independent-manufacturer-characteristic-provisioning-preflight"></a>
<a id="live-prerequisites-selection-and-scope-control"></a>
<a id="current-source-evidence-and-non-invented-capability-boundary"></a>
<a id="canonical-source-revision-and-provenance-model"></a>
<a id="operator-controlled-workflow-and-cross-owner-guards"></a>
<a id="compatibility-and-bounded-future-implementation-envelopes"></a>
<a id="required-future-acceptance-and-negative-evidence-design-cases-only"></a>
<a id="outcome-and-next-design-task"></a>
<a id="2026-10-11--hpr-p25-022-multi-product-and-property-method-provisioning-preflight"></a>
<a id="authorization-prerequisite-acceptance-and-non-goals"></a>
<a id="inspected-source-and-compatibility-inventory"></a>
<a id="canonical-product-and-property-source-semantics-proposed-not-implemented"></a>
<a id="ownership-and-authorized-workflow-preflight"></a>
<a id="proposed-bounded-implementation-envelopes--not-authorized"></a>
<a id="future-positivenegative-acceptance-design-not-executed"></a>
<a id="status-sequence-and-stopping-rule"></a>
<a id="2026-10-11--hpr-p25-028-authoritative-engineering-baseline-management-preflight"></a>
<a id="prerequisite-acceptance-registered-scope-and-evidence-boundary"></a>
<a id="live-source-anchors-and-authority"></a>
<a id="canonical-baseline-manifest-and-revision-requirements"></a>
<a id="owner-contracts-and-operator-workflows"></a>
<a id="persistence-compatibility-and-bounded-implementation-envelopes--not-authorized"></a>
<a id="required-future-data-only-acceptance-and-negative-cases"></a>
<a id="status-and-next-task"></a>
<a id="2026-10-11--hpr-p25-029-independent-operational-monitoring-and-human-action-evidence-preflight"></a>
<a id="task-selection-and-acceptance-boundary"></a>
<a id="source-backed-module-contracts-and-non-claims"></a>
<a id="independent-measured-state-association-contract"></a>
<a id="authenticated-human-applied-action-and-observed-outcome-contracts"></a>
<a id="persistenceapi-authorization-and-integrity-boundaries"></a>
<a id="bounded-future-implementation-envelopes--not-executable-authorization"></a>
<a id="future-acceptance-design--cases-fg-and-negatives-not-executed"></a>
<a id="next-recommended-work"></a>
<a id="2026-10-11--hpr-p25-030-independent-engineering-foundation-acceptance-design"></a>
<a id="prerequisite-and-execution-envelope"></a>
<a id="evidence-classes-and-acceptance-rules"></a>
<a id="canonical-ag-case-to-ownertestcontract-matrix"></a>
<a id="test-environment-negative-and-migration-matrix"></a>
<a id="separate-implementation-acceptance-envelopes"></a>
<a id="status-and-next-boundary"></a>
<a id="2026-10-11--first-f1-implementation-registration-after-hpr-p25-030"></a>
<a id="live-gate-decision-and-disposition"></a>
<a id="registered-next-executable-implementation-not-executed"></a>
<a id="2026-10-11--hpr-ci-002-fast-feedback-test-execution-registration-documentation-only"></a>
<a id="live-prerequisites-and-execution-authorization"></a>
<a id="registered-future-execution--hpr-ci-002"></a>
<a id="2026-10-11--hpr-p25-033-station-graph-revision-persistence-design-registration"></a>
<a id="authority-scope-and-gate"></a>
<a id="registered-next-work--hpr-p25-033-design-preflight-only-not-executed"></a>
<a id="stop-and-disposition"></a>
<a id="2026-10-11--hpr-p25-033-topology-station-graph-revision-persistence-contract-design"></a>
<a id="verified-gate-execution-boundary"></a>
<a id="source-reality-and-ownership"></a>
<a id="proposed-persistence-protocol-for-separately-authorized-implementation"></a>
<a id="applicationapi-and-security-contract-design-gates"></a>
<a id="future-evidence-matrix-not-performed"></a>
<a id="execution-disposition"></a>
<a id="2026-10-11--hpr-p25-034-immutable-station-graph-revision-envelope-registration"></a>
<a id="2026-10-11--hpr-p25-035-topology-station-graph-persistence-implementation-preflight-registration"></a>
<a id="live-evidence-and-authorized-registration"></a>
<a id="hpr-p25-035--preflight-not-implementation"></a>
<a id="governance-and-stop"></a>
<a id="2026-10-11--hpr-p25-035-station-graph-persistence-prerequisite-inventory-documentation-only"></a>
<a id="verified-implementation-anchors"></a>
<a id="required-separate-implementation-decomposition"></a>
<a id="explicit-future-file-candidates--not-an-implementation-allowlist"></a>
<a id="2026-10-11--register-hpr-p25-036-station-graph-codec-and-repository-contract"></a>
<a id="live-recovery-and-selected-registration"></a>
<a id="source-backed-decomposition-and-ownership"></a>
<a id="registered-future-task--hpr-p25-036"></a>
<a id="exclusive-future-implementation-write-allowlist"></a>
<a id="canonical-station-revision-wire-contract--v1"></a>
<a id="owner-private-repository-port-contract--no-implementation-yet"></a>
<a id="focused-tests-and-validation-gates"></a>
<a id="current-disposition-and-stop-boundary"></a>
<a id="registration-validation-actually-executed"></a>
<a id="2026-10-11--execute-hpr-p25-036-station-graph-codec-and-repository-contract"></a>
<a id="selection-and-baseline"></a>
<a id="implementation-and-closed-scope"></a>
<a id="validation-actually-performed-and-limitations"></a>
<a id="updated-execution-disposition"></a>


## 2026-10-11 — Execute HPR-P25-037 immutable station graph persistence

### Selection and current disposition

Owner Next selects exactly registered HPR-P25-037, message
`feat(topology): persist immutable station graph revisions`.
Starting main `b0da4134248949b922af3e46904f2a8d27a66726`, tree
`f99f1d1171aa8e06a2cf3122a1711bbac691c5d2`, full Production CI #652/run
38149648448 SUCCESS. Original registration `91e74c80c9ad327150174ddada08de058e5de7f7`
passed exact-head Documentation #230/run 38148494634. Subsequent owner roadmap/history
reorganization preserved the complete 037 contract. Current AGENTS.md, active roadmap,
PROJECT_STATE.md, reconciliation, codec/port, physical persistence and test conventions
were recovered. Reserved 20261011.002 and all three CREATE paths remained absent; 144
predecessor versions remained unique. Relevant p25 candidate branches retained their
previous inspected heads without a reserved-version collision.

This appended status table supersedes the earlier registration-only 037 selection/status
summaries while preserving the verbatim registration and original history:

| Task | Current status | Next action / acceptance boundary |
|---|---|---|
| HPR-P25-037 registration | Completed — Documentation #230 SUCCESS | Registered seven-file contract accepted by owner Next |
| HPR-P25-037 implementation | Completed — source and local focused validation; exact-head production CI acceptance PENDING | Stop at publication-head CI startup; full CI acceptance still required |
| F1-02 / P35-C history/publication policy | PLANNED — NOT REGISTERED / NOT IMPLEMENTED | After accepted 037 full CI and separate Next, recover and register bounded actor/company/CAS/history contract |
| F1–F4 / P2.5 | OPEN — end-to-end PLATFORM acceptance not established | No company-isolation, publication, SITE or numerical acceptance |
| Simulator / P3 | DEFERRED | No new engine/calibration work, version, release, deployment or phase closure |

### Implementation and exact scope

Only the seven registered files change. The new Topology JDBC adapter implements existing
append/find/findStored without changing port/domain/codec. V1 canonical bytes preserve every
supplied field and Instant nanosecond. Same identity/bytes replay returns stored canonical
content; differing bytes conflict. Every exact read verifies format, nonempty bounded payload,
SHA-256, strict canonical decode and row/envelope identity. Absent exact keys return empty;
corruption propagates an error. Spring transactions retain atomic append/outer rollback and
database failure propagation. No retries, latest fallback, head CAS or predecessor enforcement.

New additive Flyway `V20261011_002__p25_topology_station_graph_revisions.sql` creates the
unseeded composite-key table, NOT NULL/normalized key/V1 format/1 MiB size/digest constraints
and dedicated UPDATE/DELETE/TRUNCATE rejection triggers. No master-data FK or company
default is introduced. One source-backed ownership JSON entry is added. The dictionary is
generator-produced from actual migrated PostgreSQL, not handwritten schema facts.
Existing physical V1/V2 bytes, all 144 migration files, other ownership entries, current
domain/codec/API/security/Workbench/export/version and historical roadmap snapshot remain
unchanged. Facility/revision namespace separation is not company authorization; storage
does not independently attest supplied VERIFIED evidence or publish an operator decision.

### Validation actually executed

- Exact committed production/test sources compiled with local OpenJDK 21.0.12 and the
  repository-managed Spring 7.0.9, JDBC 42.7.13, Flyway 12.4.0 and JUnit 6.0.3 dependencies.
- Local JUnit Platform execution: **34 tests PASS, 0 failures/errors/skips**: 14 new PostgreSQL
  integration methods, 17 existing codec methods and 3 existing port-metadata methods.
  Initial PostgreSQL 16.11 run passed; repeated only to qualify the CI-matching
  **16.15 (Debian 16.15-1.pgdg13+2)** database and catalog capture, again all 34 passed.
- Docker is absent locally. Temporary test-source copy replaces only Testcontainers transport
  with the disposable native database endpoint; committed test methods and Spring transaction
  proxies are unchanged. Committed Testcontainers code compiled; Docker execution remains
  an exact-head CI gate. The UID-0-only local namespace required disabling only startup root
  checks in temporary native PostgreSQL tools; no catalog/SQL/transaction engine logic or
  version metadata was altered. This is focused local validation, not full Maven/CI acceptance.
- Exercised fresh full 145-migration Flyway chain and validation; actual 144-file predecessor
  upgrade adds one migration and preserves prior Flyway checksums plus physical V1/V2 bytes.
  Tests also cover supplied provenance/time/qualification, DRAFT and unprovisioned fixtures,
  serial/parallel graphs, canonical replay/history, separate facility keys, conflicting and
  identical races, outer/database failure rollback, invalid SQL inputs and immutable guards,
  hash-consistent malformed/noncanonical/mismatched identity, privileged constraint-bypass
  read failure, and transactional migration failure without partial table/triggers.
- Actual read-only catalog query and existing generator/renderer/check PASS:
  **145 migrations / 470 JPA mappings / 490 relations / zero unresolved owners**.
  Source bundle SHA-256 `78811d82a24ae8c1aee2cd3597f8d653c1bac78734ff07b7ec244701dcab4885`;
  generated dictionary SHA-256 `74aa1b71d3a9f0841073986d3ac182fda6cf50850186f66a73cec69049ac12e0`.
  Capture SHA identifies the starting checkout; bundle hashes identify the candidate source
  including the new migration. Dictionary diff contains only new relation and capture metadata.
  Full CI must independently collect/check the actual implementation publication head.
- Documentation/OpenAPI/dictionary validator unit commands: **10 + 10 + 17 PASS**.
- Registered Maven focused invocation could not resolve Spring Boot parent 4.1.1 because
  Java direct Maven Central DNS failed; proxy retry was unavailable. Java-21 offline
  `bash mvnw -o -B -q clean verify` also failed on that uncached parent. No full Maven,
  ArchUnit, Hibernate startup or generated OpenAPI pass is claimed locally.
- Final canonical documentation, offline OpenAPI, P1 closure consistency, whitespace,
  preserved-prefix, ownership/dictionary delta and exclusive seven-file checks are recorded
  at publication. They do not imply operational, industrial or production deployment approval.

Publish one guarded direct-main commit on the recovered expected head, verify its exact
tree/parent/scope and new-head production/documentation CI startup, then STOP. No PR or
automatic successor implementation.


## 2026-10-11 — HPR-P25-037 CI #653 repair: bounded regulator upgrade target

Owner Fail selects only the failed-CI repair on main
`4e7ac44f9eb917fa6eee42aaf858e2e6b0886fa4`, tree
`f5ae80a8f0eecdc127161bc8d9e6fe61db031ea0`. Production #653/run 38152092321
FAILED; exact-head Documentation #236/run 38152092327 SUCCESS. Current
AGENTS.md, active roadmap and reconciliation were recovered before the repair.

The Maven log reports 1,606 tests, one failure, zero errors/skips. The sole failure
is `SimulationEquipmentParameterRevisionPostgresIntegrationTest` method
`forwardMigrationPreservesHistoricalPayloadBytesAndImmutability`, line 270:
expected one migration, observed two. The test starts at 20261010.003 and migrates
to latest, so the additive 20261011.002 station-graph migration joins its intended
20261011.001 regulator upgrade. This is a stale test boundary, not evidence of
lost historical bytes or a production persistence failure.

The existing regulator test now explicitly targets `20261011.001`, retaining the
exact one-migration assertion, Flyway validation, historical byte/hash/format
checks and UPDATE/DELETE/TRUNCATE rejection checks. The unchanged station-graph
integration suite independently covers the full fresh chain and real predecessor
to latest upgrade, including preserved Flyway checksums and physical V1/V2 bytes.
No assertion is weakened to accept an arbitrary migration count.

Exact repair message: `test(simulation): bound regulator upgrade migration target`.
Closed repair write scope, admitted by owner Fail: UPDATE only
`src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/SimulationEquipmentParameterRevisionPostgresIntegrationTest.java`,
`doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md`. No new/deleted file, production source,
SQL migration, dictionary, dependency, workflow, API, version or historical
snapshot changes. This corrective admission does not authorize another roadmap task.

Validation: migration-version inventory confirms exactly one migration between
20261010.003 and bounded 20261011.001; 20261011.002 remains its separate successor.
Focused Java-21 Maven invocation was attempted:
`bash mvnw -B -q '-Dtest=SimulationEquipmentParameterRevisionPostgresIntegrationTest#forwardMigrationPreservesHistoricalPayloadBytesAndImmutability,StationInternalGraphRevisionPostgresIntegrationTest' test`.
It is BLOCKED before compilation by Spring Boot parent 4.1.1 resolution:
`repo.maven.apache.org: Temporary failure in name resolution`. Local Docker is
also absent. No local Java/PostgreSQL test pass is claimed for this repair.
Documentation/OpenAPI validator tests, canonical documentation, offline OpenAPI,
P1 evidence consistency, whitespace and closed-scope checks are run before
publication and reported with their actual outcomes.

| Task | Current disposition | Gate |
|---|---|---|
| HPR-P25-037 source implementation | Completed; full CI acceptance BLOCKED by #653 test boundary | Corrective commit prepared; exact-head full CI acceptance still required |
| CI #653 repair | Completed in source; CI acceptance PENDING | Publish one guarded main commit, confirm exact-head CI starts, STOP |
| F1-02 / P35-C | PLANNED / NOT REGISTERED / NOT IMPLEMENTED | Passing repaired full CI and separate owner Next before registration |
| F1–F4 / P2.5 | OPEN | No PLATFORM or SITE acceptance/closure inferred |
| Simulator / P3 | DEFERRED | No numerical continuation selected |

Repair publication checks actually completed: 10 documentation validator tests and
10 OpenAPI validator tests PASS; canonical docs PASS (97 documents, 5,302 links,
24 modules, 13 P2 rows); offline OpenAPI PASS (263 operations, 244 paths, 231 schemas);
existing P1 closure evidence consistency PASS. These are not full Java CI acceptance.


## 2026-10-11 — Register HPR-P25-038 station revision publication/history policy preflight

### Selected registration and accepted prerequisite

Owner Next selects the active F1-02 prerequisite recovery and registration after
HPR-P25-037 acceptance. Current main `0868f2d17ce0eaefc53902ce36fa711681cd406b`,
tree `c1ad7d374fa19a7529b6aef741acb13ca73badbb`. Exact-head
[Production #654](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38155023632)
and [Documentation #237](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38155023649)
completed SUCCESS. Production job 114516116736 completed repository verification,
OpenAPI publication/snapshot/compatibility and actual database dictionary gates.
Its test summary reports **1,606 tests, zero failures/errors/skips**. Actual PostgreSQL
16.15 capture checks 145 migrations, 470 JPA mappings, 490 relations and no unresolved
owners. This accepts owner-private station storage and the bounded regulator test
repair; it does not establish publication authority or multi-company isolation.

Current AGENTS.md, active roadmap, reconciliation and current production source were
read. HPR-P25-038 was absent from the active/history registry. Only two governance
files change in this registration; the active dashboard now records accepted 037
and next registered 038. Original verbatim 037 registration and the immutable
historical snapshot are retained. Registration message:
`docs(topology): register station revision publication preflight`.

### Recovered source and unresolved contract boundaries

| Source | Observed implementation | Consequence for 038 |
|---|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/domain/model/StationInternalGraphRevision.java` | Immutable declaration with supplied predecessor, qualification and provenance; no company or authenticated publisher | Keep V1 bytes stable; publication metadata must be separate |
| `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java` and `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcStationInternalGraphRevisionRepositoryAdapter.java` | Exact facility/revision append/read and integrity verification; no head, grant or predecessor enforcement | Do not turn this storage port into an authorization oracle |
| `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java` and `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java` | Optional ownerPartyId plus descriptive snapshots; registration checks Party existence | Party existence and mutable owner reference are insufficient for company grants or historical access |
| `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyReferenceContract.java` | Public existence-only export | Company semantics need an explicit owner contract; no importing private Party domain/persistence |
| `src/main/java/dz/sh/hidra/platform/security/CurrentActorResolver.java`, `src/main/java/dz/sh/hidra/platform/security/SpringSecurityCurrentSecurityContext.java` and `src/main/java/dz/sh/hidra/platform/security/AuthenticatedPrincipal.java` | Technical authenticated actor/name resolution with anonymous fallback | Derive actor from trusted current context; reject anonymous publisher and caller-supplied identity |
| `src/main/java/dz/sh/hidra/platform/security/TenantContext.java` | Optional thread-local string, including currentOrDefault | A technical tenant string/default is not proof of company membership or facility access |
| `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAuthorizationApplicationService.java` and `src/main/java/dz/sh/hidra/modules/identity/domain/value/ScopeType.java` | Evidence-backed permission evaluation; scopes include FACILITY and CUSTOM, not COMPANY | Review reuse through an Identity-owned public export; do not invent a COMPANY enum or bypass policy evaluation |
| `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` | Explicit exported cross-module packages; no Identity-to-Topology authorization export currently listed | Any later new export and architecture allowlist amendment need exact registration |
| `src/main/java/dz/sh/hidra/modules/topology/api/rest/TopologyRestApi.java` | REST marker interface only; no station publication operation is declared there | REST/OpenAPI changes are separate future F1-03 implementation |

These are source-backed findings, not new permission grants or an executed policy.
No default company, Sonatrach-specific access, automatic owner transfer or arbitrary
JWT/header company assertion is selected. Synthetic companies/facilities/actors are
sufficient for all generic design and later software acceptance cases.

### Registered next executable task — HPR-P25-038

**Exact future execution message:**
`docs(topology): define station revision publication and history policy`.

**Task type:** documentation-only design preflight. A separate owner Next after this
registration's successful exact-head Documentation CI selects 038. It does not select
publication Java, SQL, company grants, API or closure in the same instruction.

**Exclusive execution write allowlist:**

| Operation | Exact path |
|---|---|
| UPDATE | `doc/roadmap/ULTIMATE_ROADMAP.md` — current statuses plus detailed source-backed policy and bounded future implementation registration |
| UPDATE | `doc/model-remediation/RECONCILIATION.md` — matching policy, acceptance gaps and validation evidence |
| CREATE / DELETE | None authorized |

All other paths, including `doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md`, Java,
SQL, dictionary, OpenAPI, frontend, dependencies, security settings, versions and
release artifacts are read-only in 038. No migration version is reserved or authorized
by this preflight. Later migration numbers must be selected from refreshed live source.

### Binding preflight outputs and decisions to resolve

1. **Identity and ownership:** distinguish technical tenant, Company/Party identity,
   authenticated Identity actor, facility ownership and access grant. Choose the
   authoritative company representation using existing Party/Identity contracts and
   evidence-backed grants. Specify rejection of absent/unowned facilities, absent
   authenticated actor, ambiguous owner, default tenant and unverified supplied company.
   Document delegation/administrative access separately from ownership. Existing V1
   stored synthetic revisions stay intact; storage alone never grants access.
2. **Authorized history:** define separate append/author, publish, current-head,
   exact-history and list-history operations, with authorization before existence or
   payload disclosure. Freeze authoritative ownership attribution in publication
   evidence and resolve transfer semantics explicitly: historical ownership cannot
   silently grant current access to either old or new owner. Specify not-found versus
   access-denied disclosure, bounded deterministic pagination and corrupt-read failure.
   Access checks apply even to identical replay and old revisions.
3. **Publication semantics:** separate stored DRAFT, structural qualification, declared
   VERIFIED evidence and actual authenticated publication. Define first publication,
   expected predecessor/head, conflicts and immutable predecessor existence under the
   same authorized facility. Explicitly decide whether STRUCTURALLY_QUALIFIED is a
   publication eligibility condition; it cannot stand in for human/operational approval.
   No source attestation or workflow approval may be invented from a classification.
4. **Atomic compare-and-set:** define a monotonic head generation plus exact predecessor
   comparison so stale requests and ABA-style reuse cannot pass. Choose one transaction
   boundary for validated stored revision, immutable publication evidence and head CAS;
   initial-head and competing publication races have one winner, and losers leave no
   head/event/aggregate side effects. No retry may silently substitute a newer head.
5. **Replay and corrections:** define bounded request identity scoped by authoritative
   company/facility/operation, digest and authenticated actor. Identical retry must not
   increment generation or duplicate events; changed request content/actor/scope conflicts.
   Reauthorization precedes returning any replay result. Corrections, withdrawal and
   reactivation are explicit immutable events; define whether withdrawn state retains
   generation and how subsequent expected-head rules work. Existing revision bytes are
   never rewritten or deleted. Transport outcomes must distinguish rejection from
   absence and storage/integrity failures.
6. **Persistence and boundaries:** propose separate owner/publication/head metadata,
   constraints, foreign references, locks/CAS and transaction ownership without
   modifying V1 serialization. State owner-transfer and grant-revocation races and
   how reads/publication fail closed. Reuse only public owner contracts; enumerate
   any required Identity/Party export and architecture allowlist prerequisite. Do not
   smuggle cross-module private imports into a Topology implementation.
7. **Executable decomposition:** produce dependency-ordered small implementation
   slices and register at most the first resolved slice, with an exact unique HPR code,
   message, CREATE/UPDATE/DELETE paths, migration authorization only if needed,
   focused tests and full CI/dictionary gates. Candidates are not permission until
   registered. If an ownership/security contract is unresolved, record the concrete
   prerequisite and register its bounded resolution instead of broad publication code.

The expected next design yields an implementable policy, not another generic inventory.
Every consequential decision must cite current source, identify newly proposed behavior,
and distinguish PLATFORM acceptance from optional SITE commissioning. No real deployment
calibration or manufacturer data is required for this policy.

### Required future acceptance matrix and current validation

The 038 design must map each future test to an owner and proposed implementation slice:
first publication; exact historical retrieval; two authorized synthetic companies;
cross-company/anonymous/forged-actor/default-tenant denial; unowned/missing facility;
revoked grant and owner transfer; stale/absent/wrong-facility predecessor; concurrent
first/head updates; ABA protection; same/different request replay; transaction failure
without publication side effects; corrupt bytes/digest; effective versus recorded and
publication instants; corrections/withdrawal without rewriting old payload; bounded
history ordering; fresh/actual-predecessor migrations and OpenAPI compatibility where
API is later introduced. Do not claim those future tests have executed in registration.

Exact present registration and future 038 documentation checks:
`python3 .github/scripts/test_validate_docs.py`,
`python3 .github/scripts/test_validate_openapi_snapshot.py`,
`python3 .github/scripts/validate_docs.py`,
`python3 .github/scripts/validate_openapi_snapshot.py`,
`python3 .github/scripts/validate_p1_closure.py`, `git diff --check`, exact two-document
scope and unchanged historical/verbatim registration checks. Maven/database execution
is not applicable to a documentation-only registration or design.

| Task | Current status | Next authorized selection |
|---|---|---|
| HPR-P25-037 and #653 correction | Completed — exact-head Production #654 and Docs #237 SUCCESS | Retain accepted owner-private storage scope |
| HPR-P25-038 registration | Completed in source; exact-head Docs CI PENDING | Publish `docs(topology): register station revision publication preflight` |
| HPR-P25-038 execution | Planned / NOT EXECUTED | Passing registration Docs CI and separate owner Next select only the two-document policy preflight |
| F1-02 runtime publication and F1-03 API | Planned / NOT REGISTERED / NOT IMPLEMENTED | Exact later implementation contract and subsequent owner selection required |
| F1–F4 / P2.5 | OPEN | No end-to-end PLATFORM or SITE gate/closure implied |
| Simulator / P3 | DEFERRED | Prior gas-first/GZ2/own-engine steady/transient direction retained; no numerical execution selected |

Publish only this two-document registration directly to main with guarded expected
head; verify new commit/tree/parent/scope and exact-head Documentation CI starts,
then STOP without waiting for completion or executing 038. Version unchanged.


Registration validation actually completed: 10 documentation-validator tests and 10
OpenAPI-validator tests PASS; canonical documentation PASS (97 documents, 5,302 links,
24 modules, 13 P2 rows); offline OpenAPI PASS (263 operations, 244 paths, 231 schemas);
existing P1 closure evidence consistency PASS. Exact two-document scope, unchanged
verbatim 037 registration/history and whitespace checks PASS before publication.
No new Java/database execution is claimed by this registration.


## 2026-10-11 — Execute HPR-P25-038 station revision publication/history policy

### Selection, evidence and current scope

Owner Next selects registered HPR-P25-038 and exact message
`docs(topology): define station revision publication and history policy`.
Starting main `e2bec053214f1f4696c32fe4b0885f36def6572c`, tree
`395617f0aa0e3c424a1c43bc3ac428e8ad8d9782`;
[registration Documentation #238](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38156075810)
SUCCESS. Last code baseline `0868f2d17ce0eaefc53902ce36fa711681cd406b` passed
Production #654 and Docs #237. Only the two governance documents changed between
that accepted code and current main; no production run on the docs head is claimed.
Current AGENTS.md, roadmap, reconciliation, PROJECT_STATE.md and source were reread.
Only these two documents change now. All policies below are selected target design,
not deployed authorization, head storage, transfer or publication functionality.

### Source-backed ownership and authorization decisions

Company context is a configured **Party identity**, never a name, Party code snapshot,
organization-unit name, tenant header or provider claim. A facility's authoritative
ownerPartyId must resolve through a Party-owned public contract. Generic ownership
uses stable Party.id and a separate publication ownership epoch; it does not infer
legal-company classification from arbitrary partyTypeId/catalog labels. Party is
already the master record for legal entities/business actors. Provisioning selects
the owning Party explicitly; no operator/company default or seeded Sonatrach record.

Source: `src/main/java/dz/sh/hidra/modules/party/domain/model/Party.java` already
implements `selectableForNewReference()` as status ACTIVE. `PartyStatus` contains
DRAFT, ACTIVE, SUSPENDED, BLOCKED, RETIRED. Existing
`src/main/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryService.java`
checks only existence; existing optional Facility.ownerPartyId is not membership.
Selected prerequisite: export active-reference eligibility separately, preserving
existence-only behavior for historical/existing consumers. Eligibility is neither
company membership nor publication permission. Non-active/unresolved owner denies
new publication and user-facing publication history; privileged forensic retention
access requires a separately registered administrative path, never implicit fallback.

Publisher identity is the current authenticated **Identity User.id**. Do not copy
`Authentication.getName()` or technical CurrentActorResolver actor into User.id without
Identity binding. Source: `SpringSecurityCurrentSecurityContext` resolves a technical
name; `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverter.java`
resolves an external issuer/subject to active local User and `HidraPrincipal`;
`src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringAuthorizationContextAdapter.java`
binds assertions to the current HidraPrincipal.userId. A future Identity-owned
Topology export must resolve the current authenticated user and evaluate permissions;
its request cannot accept a caller-chosen actor. Native-token User binding must be
reviewed and tested in that export's separate preflight; unsupported principal shapes,
anonymous/system actors, inactive/locked users and invalid assertions deny access.

Selected access policy requires **both** an operation-specific company-context decision
and a facility-context decision from Identity's current evidence-backed evaluator.
Company context uses CUSTOM scope with exact reference `topology-company:<Party.id>`;
facility uses FACILITY with Facility.id, resource type `STATION_GRAPH_REVISION`.
No COMPANY enum or direct imports of Identity private models in Topology. Each future
operation has a distinct permission code under `topology.station-revision.`:
`author`, `publish`, `read-current`, `read-history`, `list-history`, `withdraw`,
`reactivate`, `transfer`. These are proposed codes, not registered/seeded permissions.
No GLOBAL scope request or null-scope default. Source
`src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationEvidenceAdapter.java`
allows an explicitly provisioned GLOBAL grant to cover a scoped request; that is
existing policy behavior, not proof of company membership. Administrative/delegated
access is permitted only by explicit active Identity evidence satisfying both decisions;
no role-name, owner equality or JWT-authority shortcut. DENY/INDETERMINATE wins.

Grant revocation and owner-change race semantics are mandatory implementation gates.
A plain boolean authorization call plus a later JDBC append cannot promise atomic
revocation. The future export/transaction design must define an evidence generation
or consistent database lock/version protocol covering grant/policy/user/Party/facility
changes and validate it at the operation's linearization point. Until tested, there
is no claim of atomic cross-owner authorization. This concrete prerequisite follows
Party eligibility and precedes user-facing reads/writes; no broad publication service
is authorized here.

### Exact historical access, ownership epochs and disclosure

Each facility has one publication binding `(facilityId, companyPartyId, ownershipEpoch)`
validated against live Facility.ownerPartyId. Existing unprovisioned synthetic V1 rows
remain private historical storage, never automatically adopted or published. Null,
missing/inactive/mismatched owner fails closed; arbitrary company argument cannot choose
another namespace. Party/company IDs and Facility IDs are normalized by their owners;
new public identity lengths must match underlying master storage limits, not silently
truncate. Revisions retain their original global facility/revision keys and V1 bytes.

Every public append, publish, head read, exact-history read, history list and replay
first resolves trusted actor and authorized company/facility. Before authorization,
missing and out-of-scope targets have the same opaque `ACCESS_DENIED` outcome; payload,
revision existence, digest and count are not exposed. Once authorized, absent exact
revision is NOT_FOUND; corrupt stored content is INTEGRITY_FAILURE and never absence
or a latest fallback. Draft access is limited to the author operation's explicit scope;
publication history cannot silently expose unpublished stored drafts.

Immutable publication evidence freezes companyPartyId, ownershipEpoch, actor User.id,
revision key/digest/format, previous head key, expected/result generation, operation,
request key and server publication Instant. Original recorded/effective instants remain
in V1. A later owner reference change places publication access on hold until explicit
transfer succeeds; it does not alter old evidence. Transfer is a separately authorized
action requiring both company contexts, facility permission and immutable transfer
evidence; it increments ownership epoch and head generation. No transfer is implemented
or automatically triggered by a master-data update. New owner sees only its epoch's
publication history; prior company loses ordinary facility access after transfer.
Cross-epoch historical access requires an explicit historical delegation/retention
policy and separately registered implementation. Neither prior ownership nor current
ownership alone grants past-epoch access. Initial publication in a new epoch creates
an explicit new publication of a new revision; no silent inheritance of old active head.

List-history requires the list permission and returns only the authorized epoch,
ordered by unique monotonic event generation. Selected bounds: page size 1..100,
opaque continuation bound to facility/company/epoch and frozen maximum generation;
subsequent pages exclude events appended after that snapshot. Generation cursor is
validated, not trusted for scope. Exact reads require their own history permission.
No unbounded all-companies search, cross-owner count, guessed revision lookup or
client-defined sort. Withdrawn events remain available under authorized history.

### Publication, head compare-and-set and immutable event protocol

A published revision must be STRUCTURALLY_QUALIFIED, graph COMPLETE, and pass existing
structural/codec integrity checks. Stored DRAFT is admissible authoring but cannot
publish. SYNTHETIC or DECLARED evidence can publish in a synthetic PLATFORM deployment;
VERIFIED remains supplied provenance and is never elevated into source authenticity,
Workflow approval, engineering certification or operating authorization. Publication
means an authorized declaration, not a command to OT equipment. Qualification/evidence
must not be fabricated by the publication service.

Head metadata has a persistent facility generation, ownership epoch, last declared
revision and state `UNPUBLISHED`, `PUBLISHED` or `WITHDRAWN`. Generation begins at 0;
no deleting/reinitializing it after withdrawal or transfer. First publication requires
expected generation 0 and null predecessor. Subsequent publication/correction requires
exact current generation and last revision, matching the candidate V1 supplied
expectedPredecessorRevisionId. A predecessor must be verified stored content at the
same facility and the last publication in this epoch. Cannot branch from an older
head, another facility, unpublished revision or previous ownership epoch. Generation
overflow fails, never wraps. No stale request retry substitutes a new expected head.

Selected linearization point is a successful guarded head write under one database
transaction containing scope/evidence recheck, canonical revision append/conflict
check, immutable publication/request evidence, and compare-and-set. Competing initial
publications or updates from the same generation have exactly one winner; losers
rollback all newly inserted revision/event/request rows. Previously stored private
revisions remain intact. Exact PostgreSQL locking/unique-key/CAS technique must be
registered with its future SQL slice. Integrity/storage failures propagate; no partial
success, external side effect or guessed transaction retry. A repeatable authorized
history snapshot must read ownership/access evidence and result consistently under
that same documented protocol.

| Operation | State and supplied expectation | Successful result |
|---|---|---|
| Initial publish | UNPUBLISHED in current epoch; expected current generation, no epoch predecessor | New immutable revision/event; PUBLISHED; generation +1 |
| Publish/correct | Exact generation and last revision; new never-published revision ID in this epoch; candidate predecessor matches | New immutable revision/event; PUBLISHED; generation +1 |
| Withdraw | PUBLISHED; exact generation and revision; distinct withdraw permission | WITHDRAWN event; retain last revision and payload; generation +1 |
| Reactivate | WITHDRAWN; exact generation and retained revision; distinct reactivate permission | PUBLISHED event for retained revision; no append or qualification rewrite; generation +1 |
| Transfer | Explicit transfer policy plus exact generation/epoch; no implicit owner update | Transfer event; epoch +1; generation +1; UNPUBLISHED for new epoch |

For new-epoch initial publication generation is the retained facility generation,
not 0; absence of an epoch predecessor is explicit. Ordinary first-ever publication
is the only generation-0 case. Transfer details/security are a later separate slice.
Reactivate cannot select arbitrary older content; publishing a new corrected revision
is a different operation. Reusing a previously published revision as a correction is
rejected, preventing revision-head ABA; generation also protects withdrawal/reactivation.
New effective intervals do not retroactively rewrite history. The head represents the
last published declaration, not necessarily the hydraulically/temporally applicable
operating configuration. Effective-at selection needs a separately registered contract;
publication timestamps never replace declared effective/recorded instants.

### Bounded replay and time rules

Request key is a nonblank opaque UTF-8 token of at most 128 bytes. Its uniqueness scope
is `(companyPartyId, facilityId, ownershipEpoch, operation, requestKey)`; immutable
request content additionally binds authenticated actor, candidate canonical digest,
expected generation/predecessor and any withdrawal/reactivation reason. Selected retry
window is 24 hours from server-recorded commit time, including nanoseconds. Do not trust
a client expiry or clock to extend it. Replay lookup is a bounded exact key query.
Authorization and live ownership recheck precede replay disclosure even inside the window.
Identical actor/content retry within window returns the original result without another
event or generation increment, even when head advanced later; it does not assert that
old result is the current head. Changed actor/content conflicts. After expiry return
REQUEST_EXPIRED; retain immutable key/evidence so expiry never permits key reuse.
There is no automatic history/tombstone deletion. Rate/retention administration is a
separate policy, not an in-memory unbounded replay map or silently evicted request key.

Record server publication time independently from domain recordedAt/effectiveFrom/end;
use Instant nanosecond-safe serialization in immutable evidence. A revision can describe
past/future effects without triggering real-time execution. History order is generation,
not timestamp ties; no overwriting supplied origin/evidence on retry.

### Owner-separated implementation decomposition and acceptance mapping

| Ordered slice | Responsibility and gate | Required synthetic acceptance |
|---|---|---|
| HPR-P25-039 — registered below | Party-only active-reference eligibility export; no grant or Topology call site | ACTIVE true, all other PartyStatus false, unknown/blank false, exact identity, errors propagate; old existence behavior preserved |
| Identity authorization export — NOT REGISTERED | Current User.id binding, paired company/facility decisions, permission/evidence protocol, new exported package and architecture entry | Two companies, anonymous/forged/name-only/default-tenant denial, inactive/locked/revoked evidence, explicit admin/delegation, deny/indeterminate; cross-owner linearization |
| Topology scope binding/transfer — NOT REGISTERED | Live Facility/Party scope, epoch evidence, explicit transfer/hold, coordinated authorization | Missing/unowned/inactive/mismatched facility; transfer races; no inherited access; scoped drafts and history |
| Publication domain/application/SQL — NOT REGISTERED | V1-stable request/event/head models; one transactional append/CAS/replay operation; immutable SQL guards | First/competing/stale/wrong-facility predecessor, generation/ABA, same/different/expired replay, rollback and corrupt bytes; recorded/effective/publication instants |
| History/correction/withdrawal — NOT REGISTERED | Scoped exact/snapshot list and event lifecycle using accepted authority protocol | Bounded stable pagination, revoked/transfer access, retained old bytes, withdraw/reactivate, no automatic latest/effective fallback |
| F1-03 API — NOT REGISTERED | Authenticated transport, safe outcomes and owner adapters | OpenAPI backward compatibility, opaque denial/not-found distinction, invalid inputs and cross-company disclosure tests |

SQL-bearing slices must separately register their actual files/version and constraints,
exercise fresh full Flyway plus actual predecessor/checksum/payload upgrade, regenerate
owner-backed dictionary from the migrated catalog and pass exact-head full CI. All slices
remain independently selectable; no deferred provider/simulator/calibration implementation.
The company/Identity evidence concurrency prerequisite is concrete and remains unimplemented;
register only the resolved Party slice now, not a pretend all-owner solution.

### Registered next implementation — HPR-P25-039

**Exact message:** `feat(party): export topology party reference eligibility`.
**Selection gate:** successful 038 exact-head Docs CI and separate owner Next, refreshed
main/source and path collision check. The implementation is NOT executed in 038.

| Operation | Exact allowed path |
|---|---|
| CREATE | `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyEligibilityContract.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/party/application/service/TopologyPartyEligibilityQueryService.java` |
| CREATE | `src/test/java/dz/sh/hidra/modules/party/application/service/TopologyPartyEligibilityQueryServiceTest.java` |
| UPDATE | `doc/roadmap/ULTIMATE_ROADMAP.md` |
| UPDATE | `doc/model-remediation/RECONCILIATION.md` |
| DELETE | None authorized |

Public Party-owned interface defines only
`boolean eligibleForNewReference(String partyId)`; the service implements it using
existing PartyRepositoryPort.findById and Party.selectableForNewReference. Null/blank
returns false without repository access; trimmed exact identity lookup; absent or
non-ACTIVE returns false. Resolved Party.id must match the trimmed requested id; a
mismatched returned identity fails closed with false. Repository exceptions propagate
rather than masquerading as absence. No exposed Party domain/JPA object, cache, fallback,
Party type inference, company membership, facility permission, actor, default tenant,
new database table, migration or permission seed. Existing exists contract/service remain
unchanged for all statuses. New service is a Party Spring @Service, constructor null
repository rejected, stateless and explicitly documented as eligibility only.
Existing exported Party application.contract.topology package already permits this public
contract; no architecture-rule change or Topology caller is authorized. Canonical Java
headers required on all three new files with author/date per AGENTS.md.

The new test must use real synthetic Party records for ACTIVE and each non-ACTIVE status,
two independent Party identities, whitespace lookup, null/blank no repository invocation,
missing reference, mismatched repository identity, constructor null and repository failure.
Existing TopologyPartyReferenceQueryServiceTest must pass unchanged. Future validation:
`bash mvnw -B -q -Dtest=TopologyPartyEligibilityQueryServiceTest,TopologyPartyReferenceQueryServiceTest,PartySemanticRemediationTest,ArchitectureGuardrailTest test`,
`bash mvnw -B -q clean verify`, registered docs validators and exact five-file diff checks.
If local Maven is blocked, disclose actual cause; exact-head Production CI remains the
acceptance gate. No database/dictionary regeneration applies to this schema-free slice.

### Executed disposition and stop

| Task | Status | Next |
|---|---|---|
| HPR-P25-038 registration | Completed — Docs #238 SUCCESS | Accepted exact registered scope |
| HPR-P25-038 policy | Completed in source; new-head Docs acceptance PENDING | Publish exact registered message and STOP at CI startup |
| HPR-P25-039 Party eligibility | Registered / Planned / NOT IMPLEMENTED | After successful 038 Docs CI, separate owner Next implements only five allowed files |
| Remaining Identity/Topology publication/history/API slices | Planned / NOT REGISTERED / NOT IMPLEMENTED | Dependency-specific contracts, files and exact CI acceptance required |
| F1–F4/P2.5 | OPEN | No PLATFORM/SITE closure |
| Simulator/P3 | DEFERRED | Preserve prior gas-first/GZ2/own-engine steady/transient direction; no numerical execution |

No code, migration, permission or API is changed in this policy task. Version/release,
industrial/source authenticity and closure remain unchanged. Two-document guarded direct
main publication, exact parent/tree/scope and new-head Docs startup, then STOP.


HPR-P25-038 validation actually completed: 10 documentation-validator tests and 10
OpenAPI-validator tests PASS; canonical docs PASS (97 documents, 5,302 links, 24 modules,
13 P2 rows); offline OpenAPI PASS (263 operations, 244 paths, 231 schemas); existing
P1 closure evidence consistency PASS. Two-document/retained-record/source-path/collision
and whitespace checks PASS. No new Java, PostgreSQL or authorization acceptance run
is claimed by this documentation-only policy design.


## 2026-10-11 — Execute HPR-P25-039 Party eligibility export

Owner Next selects exactly registered message
`feat(party): export topology party reference eligibility` on main
`34222374ef5451144766bc07dfe5957d67b4c5fb`, tree
`fcde730e5aebeb01d28e1040fdb55d58baf553ae`.
[HPR-P25-038 Docs #239](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38156540664)
SUCCESS on that exact SHA. Last full accepted code baseline
`0868f2d17ce0eaefc53902ce36fa711681cd406b` passed Production #654 and Docs #237;
intervening commits change only the two governance documents. Current AGENTS.md,
roadmap, reconciliation, Party model/status/repository, public export conventions,
existing existence service/tests and architecture exported-package rule were reread.
Three CREATE paths were absent. Exact five-file allowlist retained; no added prerequisite.

Three new files deliver the Party-owned `TopologyPartyEligibilityContract`, stateless
Spring `TopologyPartyEligibilityQueryService`, and focused test. Only the roadmap and
reconciliation are updated. The public method eligibleForNewReference(String) uses
trimmed exact lookup, verifies returned identity and delegates the ACTIVE rule to
existing Party.selectableForNewReference. Blank/null avoids repository access;
missing/non-ACTIVE/mismatched identity returns false. Repository failures propagate,
constructor null is rejected, each query observes current repository state. No cache,
Party domain/JPA object exported, caller actor/company default, permission or source
attestation. Existing exists behavior stays unchanged for every stored status.

Nine new tests use actual synthetic Party records for all five statuses, two identities,
whitespace/case preservation, blank/no interaction, missing and mismatched identity,
status change/no caching, repository error and null constructor. They also exercise
existing existence behavior alongside eligibility for every status. No Spring context,
database or real deployment data is needed for this slice. Existing exported
Party application.contract.topology package already covers the new interface;
ArchitectureGuardrailTest and its allowlist are unchanged. All new Java headers
retain canonical author Abir MEDJERAB and CreatedOn 2025-06-26.

Validation actually executed:

- Registered Java-21 focused Maven command attempted:
  `bash mvnw -B -q -Dtest=TopologyPartyEligibilityQueryServiceTest,TopologyPartyReferenceQueryServiceTest,PartySemanticRemediationTest,ArchitectureGuardrailTest test`.
  BLOCKED before compilation: parent Spring Boot 4.1.1 cannot resolve due to Maven
  Central `Temporary failure in name resolution`. Java-21
  `bash mvnw -o -B -q clean verify` also attempted, blocked by the uncached same parent.
  No full Maven/ArchUnit/whole-application success is claimed locally.
- Actual final production/test sources compiled with OpenJDK 21.0.12 using downloaded
  repository-managed dependencies from Boot 4.1.1 BOM: Spring 7.0.9, JUnit 6.0.3,
  Mockito 5.23.0, Byte Buddy 1.18.11, AssertJ 3.27.7 and Mockito's Objenesis 3.3.
  No stub/edited test sources or substitute assertions. Local JUnit Platform execution
  **13 tests PASS, zero failures/errors/skips**: 9 new eligibility tests, unchanged
  TopologyPartyReferenceQueryServiceTest (1) and PartySemanticRemediationTest (3).
  This is focused direct Java/JUnit execution; it does not substitute for the pending
  full architecture/CI gates. Final rerun follows the test-source cleanup only.
- Registered documentation validator units, canonical docs/offline OpenAPI, existing
  P1 evidence consistency and exact five-file/retained-record/header/whitespace checks
  are completed before publication and reported with actual results.

No old production/test source, application caller, Party existence contract, domain,
JPA mapping, migration, dictionary, API/export route, dependency, architecture rule,
version/release or historical snapshot is changed. Source bundle for migration/JPA
catalog stays unchanged; full CI independently verifies dictionary/OpenAPI gates.
Party eligibility remains a prerequisite only, not authenticated company access.

| Task | Current status | Next gate |
|---|---|---|
| HPR-P25-038 | Completed — exact-head Docs #239 SUCCESS | Retain target policy, no runtime authority inferred |
| HPR-P25-039 | Completed in source — full exact-head CI acceptance PENDING | Guarded main publication, verify full/Docs startup, STOP |
| Identity authorization export | Planned / NOT REGISTERED / NOT IMPLEMENTED | After accepted 039 full CI and separate Next, recover current-user binding/paired scoped decisions/revocation protocol and register the first bounded prerequisite |
| Topology owner/epoch/publication/history/API | Planned / NOT REGISTERED / NOT IMPLEMENTED | Depends on accepted Identity contract and individually registered scopes |
| F1–F4/P2.5 | OPEN | No PLATFORM or SITE closure |
| Simulator/P3 | DEFERRED | Prior gas-first/GZ2/own-engine steady/transient direction retained without numerical work |

One exact commit, direct main with expected-head lease; verify parent/tree/five paths
and exact-head Production/Documentation CI startup then STOP. No PR, CI-completion
wait, automatic next slice, release, version or closure.


HPR-P25-039 publication validation: 10 documentation-validator and 10 OpenAPI-validator
unit tests PASS; canonical docs PASS (97 documents, 5,302 links, 24 modules, 13 P2 rows);
offline OpenAPI PASS (263 operations, 244 paths, 231 schemas); existing P1 closure evidence
consistency PASS. Exact five-file scope, retained records/history, canonical Java headers
and whitespace checks PASS before publishing. Full Maven/ArchUnit acceptance remains pending.


## 2026-10-11 — Register HPR-P25-040 trusted Topology actor export

### Selection and accepted evidence

Owner Next selects one bounded Identity prerequisite registration following accepted
HPR-P25-039. Current main `5a279f27cd3058c909629f78b67b417a7e607e18`, tree
`2ee33701a94212719bba9ce09080881ba0b450da`;
[Production #655](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38157165810)
and [Docs #240](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38157165726)
SUCCESS on that exact SHA. Production job 114522415052 passes repository verification,
OpenAPI publication/snapshot/backward compatibility and migrated catalog/dictionary.
Its summary reports **1,615 tests, zero failures/errors/skips**. Actual PostgreSQL 16.15
capture: 145 migrations, 470 JPA mappings, 490 relations, zero unresolved owners;
source bundle `78811d82a24ae8c1aee2cd3597f8d653c1bac78734ff07b7ec244701dcab4885`.
This accepts Party eligibility only, not company authorization or publication authority.

AGENTS.md, active roadmap, reconciliation and current identity/security source were
recovered. HPR-P25-040 and its three CREATE paths are absent. Current registration
message `docs(identity): register trusted topology actor export` changes only the
two governance files, records accepted 039 and registers the first resolved Identity
slice. No Java, policy grant, SQL, API or architecture test is changed in registration.

### Recovered actor-binding source and design boundary

| Source | Observed contract | Selected consequence |
|---|---|---|
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java` | Common bearer subject is principal.userId; optional configured principal claim also carries that ID; username is separate | Native bearer actor comes from revalidated sub, never configurable getName/username claim |
| `src/main/java/dz/sh/hidra/platform/configuration/HidraJwtDecoderConfiguration.java` | Qualified hidraJwtDecoder validates own HS256 issuer/time and configured audience; external decoder is separate | Reuse this exact local-token decoder for native-token binding; no manual claim/signature shortcut |
| `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java` | Stable userId distinct from username; getName returns userId | A normalized provider principal supplies userId only after trusted authenticated context |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java` and `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProvider.java` | Verified local/directory credentials yield authenticated UsernamePasswordAuthenticationToken with HidraPrincipal | Permit these concrete normalized provider contexts, not arbitrary string principals |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverter.java` and `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraOidcAuthenticationToken.java` | External issuer/subject is resolved to local User and normalized OIDC token; this is distinct from ordinary native bearer | Support normalized OIDC token binding; do not parse raw external JWT as a native User.id |
| `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRepositoryPort.java` and `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java` | findById/findByUsername; active and locked(Instant) domain rules | Use exact findById only; recheck current ACTIVE, unlocked HUMAN user each invocation |
| `src/main/java/dz/sh/hidra/platform/security/SpringSecurityCurrentSecurityContext.java` | Technical name resolver without User lookup | Do not equate technical ActorId/name with publication User.id or reuse anonymous fallback |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringAuthorizationContextAdapter.java` | Authorization assertion binds to current normalized User.id; native bearer has no HidraPrincipal | Actor resolution is separate from later scoped permission and external evidence adaptation; no permission shortcut from token roles |
| `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` | Public module exports are explicitly listed; no Identity contract.topology currently | Register exactly one new export package with its public contract, preserving all other guard rules |

The first slice exposes only a stable authenticated human actor reference. It does
not resolve company/facility ownership, check permissions, carry authorization claims,
prove token/provider revocation, grant publication or serialize concurrent revocation.
UserType.HUMAN is selected for this operator-facing contract; SERVICE, SYSTEM,
INTEGRATION and BREAK_GLASS are not eligible. Administrative/break-glass or automated
actors require their own registered semantics rather than anonymous/system fallback.
Local/directory/OIDC normalized tokens are trusted authentication-context inputs,
not caller-created REST DTOs. This adapter does not reauthenticate provider credentials
or independently validate every upstream provider/link status; those remain owner
permission/evidence gates before any user-facing publication or history access.

### Registered next executable implementation — HPR-P25-040

**Exact message:** `feat(identity): export trusted current topology actor`.
**Gate:** passing current registration exact-head Docs CI, separate owner Next,
refreshed source/path checks, accepted HPR-P25-039. NOT implemented by registration.

| Operation | Exact authorized implementation path |
|---|---|
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/application/contract/topology/TopologyCurrentActorContract.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/TopologyCurrentActorQueryAdapter.java` |
| CREATE | `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TopologyCurrentActorQueryAdapterTest.java` |
| UPDATE | `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only `dz.sh.hidra.modules.identity.application.contract.topology` to existing explicit exported-package set |
| UPDATE | `doc/roadmap/ULTIMATE_ROADMAP.md` |
| UPDATE | `doc/model-remediation/RECONCILIATION.md` |
| DELETE | None authorized |

No migration, permission seed, controller, dependency, configuration, existing actor
contract/adapter, JWT issuer/decoder/converter or domain mutation is authorized. No
Topology consumer is added early. Three new Java files use canonical AGENTS.md headers.
The public contract imports only JDK types, exposes `Optional<Actor> currentActor()`
and nested immutable `record Actor(String userId)`. Actor rejects null/blank IDs,
normalizes outer whitespace consistently with Identity and exposes only User.id;
no username, employee, provider claim, credentials, permission, company or JPA type.
No input argument allows a caller to choose user identity.

The new Identity infrastructure @Component adapter owns Spring Security integration.
Its injected production constructor takes UserRepositoryPort and
`@Qualifier("hidraJwtDecoder") JwtDecoder`, rejects null, and delegates to a same-package
constructor with Clock.systemUTC for fixed-clock tests; use explicit @Autowired on the
production constructor if necessary for unambiguous Spring selection. No new global
Clock bean. Resolve current SecurityContextHolder.Authentication once per call, require
non-null authenticated context and exclude AnonymousAuthenticationToken regardless
of flags. Do not write/clear/replace caller SecurityContext or token details.

Supported identity extraction is closed:

1. **JwtAuthenticationToken:** re-decode its original bearer value through the injected
   hidraJwtDecoder. Derive candidate from returned validated JWT sub, not the possibly
   forged/cached Jwt object or Authentication.getName/roles/details. Missing/blank
   token or subject denies without User lookup. Require subject already canonical
   (no outer-whitespace alternate User identity). JwtException means no eligible actor;
   do not swallow unrelated decoder/runtime or repository failures. Raw external
   issuer tokens are rejected by the own-issuer decoder. Do not log/copy bearer values.
2. **Authenticated UsernamePasswordAuthenticationToken + HidraPrincipal:** accept only
   LOCAL, LDAP or ACTIVE_DIRECTORY authenticationType. Extract principal.userId;
   generic string/custom principal, OIDC-shaped password token and unsupported provider
   types deny. Local/directory authentication remains the existing providers' job.
3. **Authenticated HidraOidcAuthenticationToken:** require HidraPrincipal.authenticationType
   OIDC and extract its userId. Do not convert raw external subject into local User ID
   or use arbitrary Authentication subclasses just because their principal looks similar.

For a valid extracted ID, capture trusted clock Instant once, exact repository findById,
require returned User.id equals candidate, User.active(), !User.locked(now), and
userType HUMAN. Return only fresh Actor(user.id). Missing, mismatched, non-active,
time-locked or nonhuman user gives empty. Do not findByUsername, reuse role claims,
use ThreadLocal tenant/company defaults, cache User status, or treat repository failure
as absence. At lockedUntil equality the existing User.locked rule no longer time-locks
an ACTIVE user; status LOCKED still denies. Constructor clock null is rejected.

### Required tests and exact validation

New tests use synthetic real User/HidraPrincipal records and clear SecurityContext in
@AfterEach. Fixed Clock isolates temporal boundaries. Required matrix:

- Trusted native bearer subject resolves ACTIVE HUMAN even when token authentication
  name, username, roles, details and other actor-like claims disagree; use redecoded
  subject as sole candidate and verify decoder receives original token exactly.
- Forged cached JWT sub cannot override decoder result; missing/noncanonical subject,
  expired/wrong-issuer/tampered token (JwtException) denies before User repository access.
  Include at least one real HS256 issuer/time/signature test with disposable test-only
  key/Nimbus decoder, complementing existing HidraUnifiedJwtCompatibilityTest. Do not
  replace production decoder behavior with a permissive test stub in that case.
- Normalized LOCAL/LDAP/ACTIVE_DIRECTORY and OIDC contexts resolve local User.id without
  username fallback. Unsupported provider/principal/token, missing/anonymous/unauthenticated
  contexts deny and do not access repositories/decoder. Supplied details/authorities
  alone never produce identity. Include OIDC external subject != internal User.id.
- Missing, mismatched ID, each non-ACTIVE UserStatus, active future lock and all non-HUMAN
  UserType values deny; exact lock expiry is tested; status/lock changes are observed
  on new invocation. Two different User IDs remain independent.
- Repository failure and unrelated decoder failure propagate; null dependencies and
  contract blank Actor ID reject; existing SecurityContext is unchanged by resolution.
  Reflection/type checks ensure contract remains framework/domain/JPA-free as needed
  by ArchitectureGuardrailTest. These are future tests, not executed here.

Future focused command:
`bash mvnw -B -q -Dtest=TopologyCurrentActorQueryAdapterTest,HidraUnifiedJwtCompatibilityTest,AuthorizationAssertionTrustTest,ArchitectureGuardrailTest test`;
then `bash mvnw -B -q clean verify`, existing documentation/OpenAPI checks and exact
six-file/header/export-delta validation. Full exact-head Production CI including
architecture, Spring bean wiring, dictionary and OpenAPI gates is mandatory. Local
Maven failure must be disclosed rather than represented as acceptance. No database
schema changes imply no authorized dictionary rewrite. Export prefix addition must
not weaken any forbidden private cross-module import or other architecture rule.

### Deferred obligations and present disposition

Next after this actor slice passes full CI is a separately registered paired
company/facility permission export and current authorization-evidence/revocation
protocol. Actor identity alone never satisfies either HPR-P25-038 scoped decision.
Provider/link/user/grant/Party/facility races and transaction linearization remain
unimplemented, as do ownership epoch, transfer, CAS publication, replay and history API.
No broad publish/permission service is admitted by the current narrow contract.

| Task | Current status | Next |
|---|---|---|
| HPR-P25-039 | Completed — exact-head Production #655 / Docs #240 SUCCESS | Retain Party eligibility-only boundary |
| HPR-P25-040 registration | Completed in source; Docs acceptance PENDING | Publish `docs(identity): register trusted topology actor export` and stop |
| HPR-P25-040 implementation | Planned / NOT SELECTED / NOT IMPLEMENTED | Successful registration Docs CI and separate owner Next select exact six files |
| Scoped Identity permissions / revocation and Topology lifecycle | Planned / NOT REGISTERED / NOT IMPLEMENTED | Separate contract, allowlist and acceptance gates |
| F1–F4/P2.5 | OPEN | No PLATFORM/SITE closure |
| Simulator/P3 | DEFERRED | Prior gas-first/GZ2/own-engine steady/transient direction retained without numerical work |

Present registration validation is documentation-only: registered doc-validator unit
commands, canonical docs/offline OpenAPI, existing P1 closure evidence consistency,
exact two-document diff and preserved registration/history checks. No current Java
or security execution is claimed. Guarded direct-main commit; verify exact parent,
tree/two paths and new-head Docs CI startup, then STOP without waiting for completion.
No PR, next implementation, version, release, deployment, real-data requirement or closure.


HPR-P25-040 registration publication validation: 10 documentation-validator and 10
OpenAPI-validator unit tests PASS; canonical docs PASS (97 documents, 5,302 relative
links, 24 modules, 13 P2 rows); offline OpenAPI PASS (263 operations, 244 paths,
231 schemas); existing P1 closure evidence consistency PASS. Exact two-document
scope, absent future CREATE paths, preserved historical registrations/reconciliation
and whitespace checks PASS. No Java/Maven execution or closure performed.


## 2026-10-11 — HPR-P25-040 trusted current Topology actor implementation

Owner Next selects the registered implementation after exact-head
[Docs #241](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38158841101)
SUCCESS on main `55e1e929f859b0512a7fa4e3ec45123ec7246185`, tree
`d8027cb598820522f7df3f5cd6e1453990c46ba1`. Latest Production #655 on accepted
`5a279f27cd3058c909629f78b67b417a7e607e18` remains SUCCESS. Refreshed AGENTS.md,
roadmap/reconciliation, Identity owner source and CREATE-path absence before mutation.
Exact message: `feat(identity): export trusted current topology actor`.

Executed exactly the registered six-file allowlist: three new canonical-header Java
files (TopologyCurrentActorContract, TopologyCurrentActorQueryAdapter and its test),
the single ArchitectureGuardrailTest export-prefix addition, and these two governance
documents. No dependency/configuration, existing decoder/issuer/provider/domain,
Topology consumer, controller, SQL, migration or dictionary artifact mutation.

The JDK-only no-argument Optional<Actor(userId)> contract validates/normalizes its
sole stable reference. Identity's infrastructure adapter snapshots authentication,
rejects anonymous/unauthenticated and unsupported contexts, revalidates the original
native bearer through qualified hidraJwtDecoder, and trusts only returned canonical
sub. It ignores cached JWT subject, configured name, username, roles and details.
Explicit normalized LOCAL/LDAP/ACTIVE_DIRECTORY password contexts and normalized
OIDC contexts use HidraPrincipal.userId. Every call reads exact findById and requires
matching ACTIVE, currently unlocked HUMAN User. One captured clock Instant, no
username fallback/cache/default company/context mutation; invalid JWT denies while
unrelated decoder and repository runtime failures propagate. Two-argument @Autowired
constructor selects native decoder without a new global Clock bean.

Synthetic test coverage: 44 actor tests including parameterized provider/status/type
matrix, cached subject/name/details rejection, actual HS256 wrong issuer/expiry/signature,
OIDC external-subject separation, current locks/status changes and exact lock expiry,
independent identities, missing/mismatched records, exception propagation, public
reference validation and Spring wiring with both decoder beans. Bearer fixtures use
the authenticated authorities constructor; one-argument Spring bearer tokens are
unauthenticated and remain denied.

Validation: registered focused Maven test selection PASS — 66 tests, zero
failures/errors/skips: actor 44, unified JWT compatibility 4, assertion trust 3,
architecture guardrails 15. Java 21; runtime-only Maven proxy settings resolve the
initial Central DNS failure; runtime-only `-DargLine=-javaagent:<local mockito-core
5.23.0 jar>` avoids this environment's Mockito self-attachment restriction. No build
configuration change. Documentation-validator 10 and OpenAPI-validator 10 tests PASS;
canonical docs PASS (97 documents, 5,302 relative links, 24 modules, 13 P2 rows),
offline OpenAPI PASS (263 operations, 244 paths, 231 schemas); existing P1 closure
evidence consistency PASS. This validates existing evidence, not execution of closure.

Actor identity is not a company/facility permission, provider-link revocation proof
or publication authority. Those paired owner decisions and transaction/evidence
linearization remain unregistered; no publication/history API is enabled. After full
exact-head CI succeeds and a separate owner Next, recover/register that bounded
permission/revocation prerequisite before any lifecycle implementation. F1–F4/P2.5
OPEN; simulator/P3 DEFERRED with prior gas-first/GZ2 own-engine steady/transient
direction retained. Version/release, deployment, real-data calibration and closure
unchanged. Source implementation Completed, full exact-head CI acceptance PENDING.
Publish direct main with expected-head guard; confirm exact commit/tree/six paths and
Production/Docs CI startup, then STOP without waiting or advancing.


HPR-P25-040 final local verification: `bash mvnw -B -q clean verify` with the same
runtime-only proxy settings and explicit Mockito Java agent completed successfully
(exit 0), produced the executable jar, and reported 1,659 tests: **1,330 passed,
329 skipped, zero failures/errors** across 365 reports. The 329 skips in 53 suites
are Docker-dependent integrations; this environment has no Docker socket. These
skips are not full PostgreSQL/dictionary/OpenAPI runtime acceptance; exact-head
Production CI must execute those gates. All 44 new actor tests and the other focused
tests passed in the full run too. Exact six-file create/update scope, canonical new
Java headers, JDK-only contract, sole architecture export addition, preserved
historical roadmap/reconciliation and whitespace checks PASS. No other files changed.


## 2026-10-11 — Register HPR-P25-041 paired Topology scoped permission export

### Selection and accepted evidence

Owner Next selects the bounded registration following accepted HPR-P25-040.
Current main `d96e420757f79aaf04e28b3f65c719f12e3029a0`, tree
`9fdcffd5de33c1c924aa10d75bfc77cd7f9a4d58`;
[Production #656](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38163429403)
and [Docs #242](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38163429395)
SUCCESS on that exact SHA. Production job 114540937978 reports **1,659 tests,
zero failures/errors/skips**, 365 reports, and passes full verification, runtime
OpenAPI/snapshot/backward compatibility and migrated PostgreSQL dictionary gates.
PostgreSQL 16.15: 145 migrations, 470 JPA mappings, 490 relations, no unresolved
owners; source bundle `78811d82a24ae8c1aee2cd3597f8d653c1bac78734ff07b7ec244701dcab4885`.
This accepts trusted actor resolution only, not company membership or publication.

AGENTS.md, current roadmap/reconciliation and Identity owner/evaluator/persistence
source were recovered before mutation. HPR-P25-041 and four future CREATE paths
were absent. Current exact registration message:
`docs(identity): register paired topology permission export`.
Current changes are restricted to this roadmap and reconciliation. No Java, grant,
permission seed, SQL, domain, architecture guard or API implementation occurs here.

### Source recovery and resolved boundary

| Current source | Observation | Registered consequence |
|---|---|---|
| `src/main/java/dz/sh/hidra/modules/identity/application/contract/topology/TopologyCurrentActorContract.java` | Accepted no-argument trusted human User.id export | Resolve once; never accept caller-selected actor or technical name |
| `src/main/java/dz/sh/hidra/modules/identity/application/port/in/EvaluatePermissionUseCase.java` and `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAuthorizationApplicationService.java` | Existing evidence-backed owner evaluation; each call has its own time/transaction and optional decision persistence | Delegate two explicit scoped requests; no new evidence engine or atomicity claim |
| `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java` and `src/main/java/dz/sh/hidra/modules/identity/domain/value/ScopeType.java` | CUSTOM/FACILITY available; null scopeType defaults GLOBAL | Construct explicit CUSTOM and FACILITY, never null/GLOBAL requests |
| `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java` and `src/main/resources/db/migration/V20261004_011__hmr_011_identity_permission.sql` | Both require `^[a-z0-9-]+:[a-z0-9-]+:[a-z0-9-]+$` | Supersede unregistered dotted proposal with valid colon-delimited codes |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationEvidenceAdapter.java` | Current user/permission, scope grants, provider/link/assertion and policy evidence; existing GLOBAL covering semantics | Reuse unchanged; no roles/details/company-label shortcut, no added delegation or membership semantics |
| `src/main/java/dz/sh/hidra/modules/identity/domain/service/AuthorizationPolicyEvaluator.java` | DENY precedence, INDETERMINATE never permits | Require both DTO.permitted and decision PERMIT for each result |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringAuthorizationContextAdapter.java` | Assertion bound to normalized principal; ordinary native bearer carries no trusted external assertion | Never reconstruct permissions from native token roles or cached claims |
| `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` | Identity application.contract.topology already explicitly exported by 040 | No architecture-file update needed or allowed |

The historical HPR-P25-038 dotted `topology.station-revision.<action>` proposal
was not implemented/seeded and is invalid against current owner source. The exact
registered replacement is `topology:station-revision:<action>` below. This corrects
syntax only, retaining distinct actions and paired company/facility decisions;
no migration, relaxed regex, compatibility alias or permission provisioning.

| Public Operation | Exact permission code |
|---|---|
| AUTHOR | `topology:station-revision:author` |
| PUBLISH | `topology:station-revision:publish` |
| READ_CURRENT | `topology:station-revision:read-current` |
| READ_HISTORY | `topology:station-revision:read-history` |
| LIST_HISTORY | `topology:station-revision:list-history` |
| WITHDRAW | `topology:station-revision:withdraw` |
| REACTIVATE | `topology:station-revision:reactivate` |
| TRANSFER | `topology:station-revision:transfer` |

### Registered next executable implementation — HPR-P25-041

**Exact message:** `feat(identity): export paired topology scoped permission checks`.
**Gate:** successful current registration exact-head Docs CI, separate owner Next,
refreshed source/path checks, accepted HPR-P25-040. NOT selected/implemented here.

| Operation | Exact authorized implementation path |
|---|---|
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/application/contract/topology/TopologyScopedAccessContract.java` |
| CREATE | `src/main/java/dz/sh/hidra/modules/identity/application/service/TopologyScopedAccessQueryService.java` |
| CREATE | `src/test/java/dz/sh/hidra/modules/identity/application/service/TopologyScopedAccessQueryServiceTest.java` |
| CREATE | `src/test/java/dz/sh/hidra/modules/identity/application/service/TopologyScopedAccessPostgresIntegrationTest.java` |
| UPDATE | `doc/roadmap/ULTIMATE_ROADMAP.md` |
| UPDATE | `doc/model-remediation/RECONCILIATION.md` |
| DELETE | None authorized |

Four new Java files use canonical AGENTS.md headers. No dependency, configuration,
existing actor/authorization/evidence adapter/domain, architecture test, migration,
production data/permission, dictionary, Topology consumer/controller/API mutation.

Public JDK-only contract exposes `boolean permitted(Request request)` with nested
`Operation` enum from the exact table and immutable `Request(Operation operation,
String companyPartyId, String facilityId)`. Request rejects null operation and
null/blank references with IllegalArgumentException; trims references consistently
with owners. No user ID, arbitrary permission code, scope type, roles, claim,
company default, epoch or authorization-model/JPA/framework type is accepted.
The references are scope inputs, not evidence of Facility ownership or Party eligibility.

New Identity application @Service implements this contract. Its public constructor
injects non-null TopologyCurrentActorContract and EvaluatePermissionUseCase. Null
request returns false without accessing either dependency. Resolve currentActor once;
absent actor returns false without evaluator calls. Use that single trusted User.id
for both evaluations, never resolve a second actor or use caller identity.

Map Operation through closed exact mapping. Both queries use that permission code,
resource type `STATION_GRAPH_REVISION`, resourceReferenceId = normalized facilityId.
First query: explicit `AuthorizationScope(ScopeType.CUSTOM,
"topology-company:" + normalized companyPartyId, null)`.
Second query: explicit `AuthorizationScope(ScopeType.FACILITY,
normalized facilityId, null)`. No GLOBAL/null-scope request, prefix parsing,
username/owner equality, display-name or configured-claim fallback. The same target
resource ID binds both permission queries; distinct scopes remain separate decisions.

Evaluate company first; unless non-null result has permitted=true and decision=PERMIT,
return false and do not evaluate facility. Facility result must satisfy the same
conjunction. DENY, INDETERMINATE, null/inconsistent DTO deny; no debug/reason/claims
or partial result is exposed. Dependency/infrastructure failures propagate, never
become permits or silently synthesized denials. No caching/context mutation or
new transaction/lock wrapper; owner use-case persistence behavior remains unchanged.
Absent/inactive permissions deny through the owner evaluator; production permission
seeding/provisioning is not authorized. Explicit eligible GLOBAL grants retain the
existing evaluator's coverage behavior; they do not prove company membership.

### Required future validation and acceptance

Unit tests: all eight operation mappings and owner-valid Permission construction;
exact actor/resource/scope/reference on both queries; trimming/invalid Request fields;
null request and absent actor no evaluator; actor captured once/two different users;
company-only/facility-only grant insufficient; each DENY/INDETERMINATE, null result,
permitted=false+PERMIT and permitted=true+non-PERMIT deny; company rejection short
circuits; evaluator/actor failures propagate; no implicit/global/default-company
request; no actor/scope/permission field in public request; dependency null guards.
Use real DTOs and synthetic IDs; capture actual queries, not implementation-only mocks.

New PostgreSQL integration test uses synthetic isolated fixtures and real
TopologyScopedAccessQueryService -> IdentityAuthorizationApplicationService ->
JpaAuthorizationEvidenceAdapter -> AuthorizationPolicyEvaluator, with existing owner
schema/migrations and explicit CUSTOM/FACILITY grants for the canonical permission.
Use real current evidence, not stubbed permit results. Trusted actor stub isolates
this pair export (actual authentication already covered by 040); assertion port null
and persistence settings false isolate internal grants without credentials. Cover:
both grants required; two companies/two facilities and two users remain independent;
wrong action/resource, missing/inactive Permission, revoked/expired/future grant,
explicit denial on either scope and inactive/locked User deny; revocation/status changes
are observed on subsequent calls. Verify Permission regex/SQL accepts exact code.
Use bounded synthetic grant time windows; same established isolated Testcontainers
PostgreSQL pattern with disabledWithoutDocker=true, honestly report local skips and
require exact-head CI execution. No concurrent atomic-revocation claim from these tests.

Focused command:
`bash mvnw -B -q -Dtest=TopologyScopedAccessQueryServiceTest,TopologyScopedAccessPostgresIntegrationTest,TopologyCurrentActorQueryAdapterTest,AuthorizationAssertionTrustTest,AuthorizationEvidencePostgresTest,ArchitectureGuardrailTest test`;
then `bash mvnw -B -q clean verify`, existing docs/OpenAPI validators and exact
six-file/header/export-unchanged checks. Runtime-only local proxy/Mockito agent
adaptation may be disclosed without changing repository configuration. Production
CI must run database integrations without skips and runtime OpenAPI/dictionary gates.

### Deferred mandatory gates and present disposition

A pair of sequential current-evidence evaluations is not atomic with later reads,
JDBC publication or revocation. Both may observe different times/transactions.
HPR-P25-041 is an owner permission-query prerequisite only: no ownership/epoch,
Party/company eligibility, provider-link revocation proof for every native context,
evidence generation/locks, transfer across both companies, delegation extension,
publication/history/API or permission seed. No user-facing access is enabled by it.
Topology must later bind live authoritative Facility.ownerPartyId and active Party;
transfer requires both old/new company contexts and its distinct facility decision.
Cross-owner linearization covering user/grant/policy/provider/Party/Facility changes
remains a separately registered mandatory prerequisite before those operations.
Never retain/use a returned boolean as later publication authority or concurrency proof.

| Task | Current status | Next |
|---|---|---|
| HPR-P25-040 | Completed — Production #656 / Docs #242 SUCCESS | Retain trusted human actor-only boundary |
| HPR-P25-041 registration | Completed in source; Docs acceptance PENDING | Publish exact two docs and stop |
| HPR-P25-041 implementation | Planned / NOT SELECTED / NOT IMPLEMENTED | Passing registration Docs and separate owner Next select exact six files |
| Cross-owner revocation/linearization and Topology owner/epoch/lifecycle/API | Planned / NOT REGISTERED / NOT IMPLEMENTED | Source-backed separate allowlists/gates required |
| F1–F4/P2.5 | OPEN | No closure or PLATFORM/SITE acceptance |
| Simulator/P3 | DEFERRED | Retain gas-first/GZ2 own-engine steady/transient direction |

Current stage validation is docs-only; no future Java/Maven/database tests are claimed.
Guarded direct-main commit, verify exact parent/tree/two paths and exact-head Docs
startup, then STOP without waiting or advancing. Version/release, deployment,
operator control, real-data calibration requirements and closure unchanged.


HPR-P25-041 registration publication validation: 10 documentation-validator and 10
OpenAPI-validator unit tests PASS; canonical docs PASS (97 documents, 5,302 relative
links, 24 modules, 13 P2 rows); offline OpenAPI PASS (263 operations, 244 paths,
231 schemas); existing P1 closure evidence consistency PASS. Exact two-document
scope, four absent future CREATE paths, existing source references, eight owner-valid
permission-code formats, historical registration/reconciliation preservation and
whitespace checks PASS. No Java/Maven/database execution or closure performed.


## 2026-10-11 — HPR-P25-041 paired scoped Topology permission implementation

Owner Next selects the registered implementation after exact-head
[Docs #243](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38164933070)
SUCCESS on current main `096272b2fa8e0cb11711f5942d99402ed368d944`, tree
`59d2b293e479c26d8145334d1b8434563a249b47`. Latest Production #656 and Docs #242
on accepted HPR-P25-040 remain SUCCESS. AGENTS.md, roadmap/reconciliation and live
Identity contracts/application/evidence/domain/migration/test source were recovered;
four CREATE paths were absent. Exact message:
`feat(identity): export paired topology scoped permission checks`.

Executed the exact six-file allowlist: CREATE TopologyScopedAccessContract,
TopologyScopedAccessQueryService, its unit test and its PostgreSQL integration test;
UPDATE these two governance documents. Existing export prefix and guardrails unchanged.
No dependencies/configuration, existing authentication/authorization/evidence/domain,
SQL/migrations/production permissions, dictionary artifacts, Topology consumer/API,
version/release, deployment or closure mutation.

The public contract contains only boolean permitted(Request), eight Operation values
and immutable operation/companyPartyId/facilityId references. Null operation or blank
references reject; owner-style trimming; no caller actor, permission or scope field.
The service captures accepted currentActor once, denies absence/null request without
evaluator access, and maps the exact colon-delimited operation code. Both queries
use trusted User.id, STATION_GRAPH_REVISION and the same facility resource ID.
Company CUSTOM topology-company:<Party.id> is evaluated first; facility FACILITY
<Facility.id> only follows an explicit company permit. Each result must be non-null,
permitted=true and decision=PERMIT. Null/inconsistent/DENY/INDETERMINATE outcomes
deny; runtime failures propagate. No cache, GLOBAL/null-scope request, arbitrary
claims/name/owner shortcut, context mutation or additional transaction wrapper.

Synthetic unit matrix covers all operation mappings and real owner Permission
construction, both exact resource/scope queries, one fresh actor per invocation,
independent user/company/facility inputs, absent actor/null request, malformed public
references, constructor guards, each null/contradictory/non-PERMIT result, short circuit
and dependency failure. New isolated PostgreSQL tests use the real owner application,
JPA evidence and policy evaluator with existing schema/migrations, bounded synthetic
direct CUSTOM/FACILITY grants, null assertion and disabled decision persistence.
They cover both scopes required, two companies/facilities/users, explicit denial,
revocation/subsequent state, expiry/future bounds, inactive/locked users, missing or
inactive/wrong-action/resource permission and SQL rejection of the dotted proposal.
The trusted actor stub isolates permission evidence only; actual authentication is
covered by accepted 040. No concurrent atomic-revocation assertion is made.

This owner-query prerequisite does not enable user-facing publication/history/API
or prove company membership/Facility ownership. Existing owner evaluator/global grant
coverage and persistence behavior remain unchanged; no new delegation/provisioning.
Sequential permission checks do not authorize a later transaction, freeze grant or
provider/Party/Facility status, validate ownership epoch, or perform both-company
transfer. Live authoritative Facility/Party binding and cross-owner evidence
linearization remain separate mandatory unregistered prerequisites. After exact-head
full CI succeeds and separate owner Next, recover/register that bounded prerequisite.
F1–F4/P2.5 OPEN; simulator/P3 DEFERRED, prior gas-first/GZ2 own-engine steady/transient
and operator-controlled direction retained. Synthetic development and owner-supplied
deployment calibration retained; no real-data requirement or closure introduced.
Source Completed; full exact-head acceptance PENDING. Publish direct main, verify
exact parent/tree/six paths and Production/Docs startup, then STOP without waiting
or advancing.


HPR-P25-041 focused validation: registered Maven selection completed successfully
(exit 0), **96 passed, 17 skipped, zero failures/errors**: new unit 34, trusted actor
44, assertion trust 3, architecture guardrails 15; existing PostgreSQL evidence 9
and new PostgreSQL suite 8 local skipped entries. Testcontainers disables these suites
because /var/run/docker.sock is absent. The new suite includes three parameterized
methods whose invocations require Docker; skipped templates do not prove database
acceptance. CI must execute the actual integrations without skips. Java 21, runtime-only
Maven proxy settings and explicit `-DargLine=-javaagent:<local mockito-core 5.23.0 jar>`
were used; no repository build configuration change. Exact six-file scope, new Java
canonical headers, JDK-only contract, unchanged architecture/export guards and preserved
historical roadmap/reconciliation checks PASS. Documentation-validator 10 and
OpenAPI-validator 10 unit tests PASS; canonical docs PASS (97 documents, 5,302 relative
links, 24 modules, 13 P2 rows), offline OpenAPI PASS (263 operations, 244 paths,
231 schemas); existing P1 closure evidence consistency PASS, without executing closure.


HPR-P25-041 final local verification: registered `bash mvnw -B -q clean verify`
with the same runtime-only proxy settings and Mockito Java agent completed successfully
(exit 0), produced the executable jar and reported 1,701 tests: **1,364 passed,
337 skipped, zero failures/errors**, 367 reports. Docker-dependent integrations remain
skipped locally; this is not full PostgreSQL/runtime dictionary/OpenAPI acceptance.
Exact-head Production CI must execute those gates. All 34 new unit cases passed
in the full run. Final exact-scope/header/history/whitespace checks and canonical
docs/offline OpenAPI validation passed before publication; no version/closure change.


## 2026-10-11 — Register HPR-P25-042 live Facility/Party owner-reference query

### Selected documentation stage and accepted evidence

Owner Next selects prerequisite recovery and registration following accepted 041.
Current main is `3b6fc769f9362bc394f9de732cab82f49207be2e`, tree
`be86de5cc1bbfbb1c72213adc32a7ff659b53146`. Main advanced during preparation from
`74d0d222c470fcb16402db41aec3de0c57836d0a` through a gap-register-only Organization
review; that prior commit added the gap register, document-register entry and docs
configuration. Both commits leave Java, migrations, runtime and active roadmap/
reconciliation unchanged. Current exact-head
[Docs #246](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38169890562)
is SUCCESS; earlier Docs #245 is SUCCESS. Production #658 on 74d0d222 was observed
IN_PROGRESS and is not claimed accepted; 3b6fc769 has only its Docs run.
This documentation-only stage uses green registration/docs evidence and unchanged
runtime source accepted on 041; it does not bypass a failed runtime gate. Before
future code execution recheck then-current main and latest Production/Docs evidence.

HPR-P25-041 implementation `6429b8dc0e449b54db89d520ba14c22ce424db1c` has exact-head
[Production #657](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38168669849)
and [Docs #244](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38168669827)
SUCCESS. Production job `114556451251` reports 1,704 tests, zero failures/errors/skips,
367 reports. PostgreSQL 16.15 actual-catalog dictionary verification covers 145
migrations, 470 JPA mappings and 490 relations with no unresolved owners; runtime
OpenAPI and repository gates passed. Source bundle SHA-256 is
`78811d82a24ae8c1aee2cd3597f8d653c1bac78734ff07b7ec244701dcab4885`.
This accepts only 041's paired permission query, not membership, live ownership,
linearization, publication or PLATFORM/SITE completion.

Read AGENTS.md, PROJECT_STATE.md, current roadmap/reconciliation and
[gap register](gaps.md). Gap findings do not authorize execution: ID-GAP-001 first-use
activation still needs onboarding policy; ID-GAP-002 organization/station scope is
not company membership; ID-GAP-003 directory source implementation is not SITE
qualification. ORG-GAP-001 country-reference qualification, ORG-GAP-002 hierarchy/reorganization
qualification and ORG-GAP-003 assignment lifecycle remain separate reviews. No duplicate
Organization country field or unrelated lifecycle implementation is selected. This
stage follows the active F1 order and preserves the owner's concurrent findings.
Exact current-stage message:
`docs(topology): register live station revision owner query`.
Current-stage exclusive UPDATE allowlist is these two governance documents only:
`doc/roadmap/ULTIMATE_ROADMAP.md` and `doc/model-remediation/RECONCILIATION.md`.
CREATE/DELETE: none. No future implementation is selected in this registration commit.

### Source recovery and bounded decomposition

- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java` exposes
  normalized optional ownerPartyId and descriptive owner code/name snapshots. Identity
  is Facility.id, not its code, facility type, tenant or display name.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/FacilityRepositoryPort.java`
  and `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaFacilityRepositoryAdapter.java`
  provide current exact-ID lookup. Registration checks Party existence only; no accepted
  query currently derives a station revision's live company context.
- `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyEligibilityContract.java`
  and its owner `TopologyPartyEligibilityQueryService` deny absent, mismatched or
  non-ACTIVE Party references. Existing exported prefix is already accepted by
  `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`; no new export is needed.
- Facility and Party JPA identity columns and Facility.owner_party_id are length 80.
  The new query must normalize and validate bounded references without truncation.
  PostgreSQL character limits mean 80 Unicode code points, not 80 UTF-16 code units.
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java` and
  `FacilityKind.java` are descriptive master state/type. This owner-reference-only
  query introduces no new facility lifecycle or station-kind permission policy.
  Publication eligibility and historical access still require their later gates.
- Accepted Identity actor/paired permission exports are read-only prerequisites. A
  company supplied to 041 is not proof that Facility belongs to it. First resolve
  Topology's live owner; later permission evaluation must use that derived reference.

### Registered next executable task — HPR-P25-042

Exact implementation message:
`feat(topology): resolve live station revision owner references`.
State: PLANNED / REGISTERED / NOT IMPLEMENTED. Separate owner Next after passing
registration Docs selects only this bounded query. Recheck source and latest CI first;
failed CI must be repaired before feature execution.

Exclusive implementation allowlist (four files):

| Action | Exact path |
|---|---|
| CREATE | `src/main/java/dz/sh/hidra/modules/topology/application/service/StationRevisionOwnerQueryService.java` |
| CREATE | `src/test/java/dz/sh/hidra/modules/topology/application/service/StationRevisionOwnerQueryServiceTest.java` |
| UPDATE | `doc/roadmap/ULTIMATE_ROADMAP.md` |
| UPDATE | `doc/model-remediation/RECONCILIATION.md` |

DELETE: none. No migration, repository/mapper/entity/domain mutation, public cross-module
export, architecture allowlist, permission seed, configuration, dependency, API,
dictionary, gap-register, release/version, deployment or closure changes authorized.
Both CREATE paths were checked absent. Canonical Java headers preserve Author Abir
MEDJERAB and CreatedOn 2025-06-26; Application and appropriate Test layers.

Register a final Spring @Service with constructor-required FacilityRepositoryPort and
accepted TopologyPartyEligibilityContract. Provide
`Optional<OwnerReference> resolve(String facilityId)` with a nested immutable public
JDK-only `OwnerReference(String facilityId, String companyPartyId)` record. This is
Topology-private application data, not a newly exported consumer contract. Record
references are trimmed, nonblank and bounded to 80 code points; reject invalid record
construction with IllegalArgumentException. No actor, caller company, snapshot,
permission, epoch, generation, grant flag, payload or revision lookup in this interface.

Null/blank/overlong query identity returns Optional.empty without repository access.
Find the trimmed exact Facility.id once. Missing or differently identified result,
blank/overlong live ownerPartyId, or an ineligible Party returns Optional.empty.
Only call Party eligibility for that derived owner after Facility identity validation.
Return the normalized exact facility/owner pair only if eligibility is true. Do not
consult caller company, owner snapshots, code/name, authentication claims or defaults.
No save/mutation, Identity evaluation, cache, transaction wrapper or retained authority.
Every invocation rereads the Facility and owner eligibility; repository/Party failures
propagate, never become an empty successful lookup. Existing Facility statuses/kinds
are not additional filters here: returning a reference is not granting any operation.

### Focused validation and mandatory deferred gates

Use synthetic fixtures, deterministic repository/Party fakes and captured inputs.
Tests must cover null/blank/overlong input and no dependencies invoked; exact trimmed
lookup; absent/mismatched Facility; missing/overlong owner despite plausible snapshots;
Party false/true and exact derived identity; independent facilities/owners; fresh owner
and Party eligibility on subsequent calls; no save; both dependency failures and null
constructor guards. Cover the nested record's normalization, null/blank/length bounds
and Unicode 80-code-point boundary. Parameterize all FacilityStatus/FacilityKind values
to prove the query introduces no implicit lifecycle/type access rule. No real data,
Spring context or database-specific behavior is introduced by this small application
query; existing database suites remain mandatory in full CI.

Focused command:
`bash mvnw -B -q -Dtest=StationRevisionOwnerQueryServiceTest,TopologyPartyEligibilityQueryServiceTest,TopologyScopedAccessQueryServiceTest,ArchitectureGuardrailTest test`;
then `bash mvnw -B -q clean verify`, documentation/OpenAPI validators and exact
four-file/header/unchanged-export/history checks. Report Docker skips honestly; full
exact-head Production CI must execute PostgreSQL/runtime dictionary/OpenAPI gates.
Runtime-only proxy/Mockito agent adaptation may be disclosed without repository edits.

OwnerReference is an observation, not permission, legal-company classification,
membership, an ownership epoch, publication binding or transfer evidence. A later
owner/Party change invalidates assumptions based on it; a prior result cannot authorize
later work. Party eligibility and Facility lookup are sequential, not atomic. Cross-owner
revocation/linearization across user/grant/policy/provider/Party/Facility remains a
mandatory separately registered prerequisite before public reads/writes. Snapshotting
Party.id cannot replace that protocol. 042 neither calls the paired permission export
nor turns its boolean into transaction authority. No user-facing operation is enabled.

| Task | Current status | Next |
|---|---|---|
| HPR-P25-041 | Completed — Production #657 / Docs #244 SUCCESS | Retain paired owner-query-only evidence scope |
| HPR-P25-042 registration | Completed in source; exact-head Docs acceptance PENDING | Publish exact two docs; verify Docs startup and STOP |
| HPR-P25-042 implementation | Planned / REGISTERED / NOT IMPLEMENTED | Passing registration Docs, current CI recheck and separate owner Next select exact four files |
| Cross-owner linearization, ownership epoch/transfer and publication/history/API | Planned / NOT REGISTERED / NOT IMPLEMENTED | Recover and register separate bounded owner protocols |
| F1–F4 / P2.5 | OPEN | No closure or PLATFORM/SITE acceptance |
| Simulator / P3 | DEFERRED | Retain gas-first/GZ2 own-engine steady/transient and operator control |

Synthetic development and owner-supplied deployment calibration remain the policy.
Current version and formal release remain unchanged. This stage validates documents
only; no future Java/Maven/database results are claimed. Publish one guarded direct-main
commit, verify exact parent/tree/two paths and exact-head Docs startup, then STOP
without waiting for CI completion or selecting another stage.


HPR-P25-042 registration publication validation: documentation-validator 10 and
OpenAPI-validator 10 unit tests PASS; canonical docs PASS (98 documents, 5,305
relative links, 24 modules, 13 P2 rows); offline OpenAPI PASS (263 operations,
244 paths, 231 schemas); existing P1 closure evidence consistency PASS without
executing closure. Exact two-document scope, both future CREATE paths absent,
existing source references, preserved historical roadmap/reconciliation and
whitespace checks PASS. No Java/Maven/database execution in this docs-only stage.


## 2026-10-11 — HPR-P25-042 live Facility/Party owner-reference implementation

Owner Next selects the registered four-file implementation after exact-head
[Docs #247](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38170018239)
SUCCESS on registration `14cb079832da0781858d5b224b578a73e6c47b7c`.
Current main `58f1b3b70adc13053f7427bb130125ededea7854`, tree
`ec2746a52d12f8807b7fd1396022154ef5f16ddf`, includes concurrent documentation-only
Organization gap review, roadmap progress tracking and the owner's execution-summary
correction. All were recovered. Current section 1.1 is preserved except the selected
042 row now records implementation complete in source and pending exact-head CI.
Registration, execution order, four-file allowlist and runtime source are unchanged.
[Docs #250](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38170636571)
is SUCCESS on this current head; Docs #249 is also SUCCESS. Latest
[Production #658](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38169424558)
is SUCCESS on `74d0d222c470fcb16402db41aec3de0c57836d0a`; Docs #248 was observed
queued at initial recovery and subsequently recorded SUCCESS in the owner's section 1.1. Registration Docs and full runtime prerequisite
gates are green; no failed CI is bypassed. AGENTS.md, current roadmap/reconciliation,
Facility/domain/port, accepted Party export, storage limits and architecture rules
were reread; both CREATE paths absent before mutation. Concurrent gap review remains
unchanged and does not select unrelated remediation.
Exact message: `feat(topology): resolve live station revision owner references`.

Executed only the four-file allowlist: CREATE StationRevisionOwnerQueryService and
its unit test; UPDATE these two governance documents. The final Spring service
constructor requires FacilityRepositoryPort and accepted TopologyPartyEligibilityContract.
Its Optional<OwnerReference> resolve(String facilityId) returns a nested immutable
record of facilityId/companyPartyId. Records trim and reject blank/null or over-80
code-point references with IllegalArgumentException; query invalid input returns empty
without touching either dependency. Bounds use Unicode code points without truncation.

Each call finds the trimmed exact Facility.id once, rejects absence or mismatched
identity before querying Party, derives only live Facility.ownerPartyId, rejects
missing/overlong owner and requires accepted Party active-reference eligibility.
Success returns only those exact identities. Populated owner snapshots, names,
facility code/type, tenant/default/company arguments and actor claims do not supply
ownership. Every call rereads current owner and eligibility; failures propagate.
No save, cache, Identity permission query or transaction wrapper is introduced.
FacilityKind/FacilityStatus are not extra query filters; an owner reference grants
no lifecycle/type access. Existing owner contracts/guardrails remain unchanged.

Synthetic deterministic tests capture actual Facility/Party lookups and prohibit saves.
They cover invalid/no-access input, normalization with case preservation, absent and
mismatched facility, plausible snapshots with invalid owner, eligible/ineligible owner,
independent facilities/owners, owner/eligibility/removal changes across calls, every
FacilityStatus/FacilityKind, ASCII/supplementary Unicode 80/81-code-point boundaries,
immutable prior observation, both dependency failures and constructor/record guards.
No new Spring context or database-specific behavior is claimed for these tests.

OwnerReference remains a sequential observation only: no permission, membership,
legal-company classification, ownership epoch/publication binding, atomic revocation
or transfer evidence. No user-facing operation is enabled. Accepted 041 permissions
must later be evaluated using authoritative derived owner under a separately registered
cross-owner evidence protocol; no previous reference/boolean authorizes a later
transaction. User/grant/policy/provider/Party/Facility linearization remains mandatory
before public publication/history/API; owner epoch, transfer and publication services
remain unregistered. After full exact-head CI succeeds and separate owner Next,
recover/register that bounded protocol; do not execute it in this stage.

F1–F4/P2.5 OPEN and simulator/P3 DEFERRED. Gas-first/GZ2 own-engine steady/transient,
dynamic versioned inputs, operator-controlled recommendations, synthetic development
and owner-supplied deployment calibration retained. Version/release/deployment and
closure unchanged. Source Completed; full exact-head CI acceptance PENDING. Publish
one guarded direct-main commit, verify exact parent/tree/four paths and Production/
Docs startup, then STOP without waiting for completion or advancing.


HPR-P25-042 focused validation: registered four-suite Maven selection completed
successfully (exit 0), 100 tests passed, zero failures/errors/skips: owner query 42,
Party eligibility 9, paired permission 34 and architecture guards 15. Full registered
`bash mvnw -B -q clean verify` completed successfully (exit 0), produced the executable
jar and reported 1,743 tests across 368 reports: 1,406 passed, 337 skipped, zero
failures/errors. All 42 new tests passed in the full run. Docker-dependent suites
remain locally skipped because /var/run/docker.sock is absent; exact-head Production
CI must execute PostgreSQL/runtime OpenAPI/dictionary gates before full acceptance.
Java 21, runtime-only Maven proxy settings and explicit Mockito 5.23.0 Java agent
were used without repository configuration changes. Concurrent main advancement
changed only documentation; tested production/test source is unchanged.

Documentation-validator 10 and OpenAPI-validator 10 unit tests PASS; canonical docs
PASS (98 documents, 5,306 relative links, 24 modules, 13 P2 rows); offline OpenAPI
PASS (263 operations, 244 paths, 231 schemas); existing P1 closure evidence consistency
PASS without executing closure. Final four-file/header/absent-at-baseline CREATE,
unchanged exports/configuration/gap register, historical roadmap/reconciliation,
owner execution-summary preservation apart from selected 042 status and whitespace checks PASS. Full exact-head
CI acceptance remains PENDING; publish and verify startup, then STOP.


## 2026-10-11 — Register HPR-P25-043 cross-owner access linearization preflight

### Selected registration and exact-head acceptance

Owner Next selects the roadmap's mandatory prerequisite recovery/registration after
HPR-P25-042 acceptance. Current main `ed6ec83f1b2163fc32a15f2f3c6964cf6758a025`,
tree `f256abeadcbae9f9e9aa12d27abd9b6ae8aad003`, has exact-head
[Production #659](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38171115192)
and [Docs #251](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38171115202)
SUCCESS. Production job `114563745937` reports 1,746 tests, zero failures/errors/skips,
368 reports. PostgreSQL 16.15 actual-catalog verification covers 145 migrations,
470 JPA mappings and 490 relations with no unresolved owners; repository/runtime
OpenAPI gates passed. Source bundle SHA-256 remains
`78811d82a24ae8c1aee2cd3597f8d653c1bac78734ff07b7ec244701dcab4885`.
This accepts 042's live owner-reference observation only, not operation permission,
company membership, ownership epoch, atomic revocation or publication functionality.

AGENTS.md, PROJECT_STATE.md, current roadmap/reconciliation, accepted 039–042
contracts/services, Identity evidence/transaction boundaries, representative writers,
Party locking precedent, Topology storage and architecture rules were recovered.
HPR-P25-043 is absent from earlier active/history/reconciliation registrations.
The gap-review programme remains a separate source of findings, not execution
selection. F1 remains the selected path; no numerical simulator or unrelated module
remediation is reopened.

Current-stage exact message:
`docs(topology): register cross-owner access linearization preflight`.
Current-stage exclusive UPDATE allowlist:
`doc/roadmap/ULTIMATE_ROADMAP.md` and `doc/model-remediation/RECONCILIATION.md`.
CREATE/DELETE: none. This commit registers a design task, not its execution or runtime
implementation. Current status tables record 042 acceptance; earlier pending records
remain historical and unchanged. Owner execution-summary structure is retained.

### Concrete recovered gap and owner boundaries

| Live source | Observed responsibility | Remaining gate |
|---|---|---|
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/TopologyCurrentActorQueryAdapter.java` | Validates supported authenticated human User and current local status | Actor lookup alone does not coordinate subsequent provider/user changes |
| `src/main/java/dz/sh/hidra/modules/identity/application/service/TopologyScopedAccessQueryService.java` | Captures actor once; sequential company CUSTOM and facility FACILITY evaluations | Returned boolean is not a transaction-bound authority or ownership proof |
| `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAuthorizationApplicationService.java` | Each evaluate method is transactional; loads evidence at Instant.now and optionally saves decision | Independent evaluations do not establish one publication/history consistency boundary |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationEvidenceAdapter.java` | Reads user/permission, direct/role/group grants, external mappings/provenance, attributes and policy versions/rules | Evidence dependency set includes query predicates and rows that can appear/disappear, not just selected grants |
| `src/main/java/dz/sh/hidra/modules/topology/application/service/StationRevisionOwnerQueryService.java` | Reads exact Facility.ownerPartyId, then Party active-reference eligibility | Sequential observation has no retained epoch, generation or lock authority |
| `src/main/java/dz/sh/hidra/modules/party/application/service/TopologyPartyEligibilityQueryService.java` | Ordinary exact Party repository read; active-reference eligibility | No common cross-owner fence with Identity or Topology |
| `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyJpaRepository.java` | Existing findByIdForShare uses PESSIMISTIC_READ | This local precedent is not used by accepted eligibility lookup and is not a full evidence protocol |
| `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcStationInternalGraphRevisionRepositoryAdapter.java` | Transactional immutable append and exact integrity-checked read | Private storage has no publication head, company binding or cross-owner authorization |

Representative ordinary write paths include
`src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserPermissionGrantRepositoryAdapter.java`,
`src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JpaPartyRepositoryAdapter.java`
and `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaFacilityRepositoryAdapter.java`.
`src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAuthenticationOutcomeApplicationService.java`
also updates User state under REQUIRES_NEW. This is an initial inventory, not a claim
that every writer, authentication context or SQL path has been covered. The design
must recover their complete dependency/mutation graph before selecting a protocol.

### Registered next executable task — HPR-P25-043

Exact design-task message:
`docs(topology): define cross-owner access linearization policy`.
State: PLANNED / REGISTERED / NOT EXECUTED. Passing exact-head registration Docs and
separate owner Next select only this solo documentation preflight. Recheck current
main and latest CI before mutation; failed CI must be repaired first.

Exclusive design-task allowlist:

| Action | Exact path |
|---|---|
| UPDATE | `doc/roadmap/ULTIMATE_ROADMAP.md` |
| UPDATE | `doc/model-remediation/RECONCILIATION.md` |

CREATE/DELETE: none. All production/test Java, runtime/build configuration, SQL/Flyway,
architecture exports/guardrails, API/OpenAPI, dictionary/ownership artifacts, gap
register, version/release/deployment and closure files are read-only. No migration
number or future Java filename is reserved. No runtime lock, trigger, epoch or token
is implemented or accepted by this registration or the forthcoming design commit.

### Required bounded design output and acceptance gates

1. **Complete source-derived read/write inventory.** Map owner, entity/table,
   exact read predicates and every mutator for user status/type/lock, permission,
   direct grants, user-role/group-membership/group-role/role-permission paths,
   group/role status, external identity/provider/mappings, subject attributes and
   definitions, policy/version/rule state and time windows, Party status and Facility
   owner/state. Include inserts/deletes/new DENY evidence, mapping/link changes,
   authentication outcomes/bootstrap/synchronization, repository save/bulk/native SQL
   and permitted administrative/import paths. Explain delegation evidence actually
   consumed by the current evaluator instead of assuming an unused grant is honored.
   Record unresolved writers explicitly; do not declare coverage from representative
   adapters or an application method's annotation alone.
2. **Exact operation consistency contract.** Define trusted actor and provenance,
   current derived company/facility, both distinct permission decisions and Party
   eligibility in the same specified operation boundary. Cover all accepted local,
   native bearer, OIDC, LDAP and Active Directory contexts, their provider/link and
   token/session revocation limits. State unsupported contexts and fail-closed
   outcomes; never infer membership from role/name/provider claims or GLOBAL grants.
3. **Linearization and revocation proof.** Define successful write and protected
   read linearization points, ordering of a concurrent revocation/owner change,
   already committed revocation, no-check/later-use gaps and rollback behavior.
   A boolean, repeated read, cached DTO, existing updatedAt or plain @Transactional
   declaration is not a proof. Address missing-row/predicate/phantom changes as well
   as updates to known evidence. Preserve explicit DENY/INDETERMINATE and storage/
   integrity failures; no namespace/existence disclosure before authorized reads.
4. **Source-compatible mechanism comparison and selection.** Compare owner-owned
   lock/version fences and any database-enforced invalidation needed for all writers,
   versus a database-wide/coarser fence and its cost. Define transaction propagation,
   shared JPA/JDBC connection and transaction-manager participation, isolation,
   lock ordering, deadlock/timeout handling and denial/decision-persistence side effects.
   Existing Party share-read is a precedent only. A selected mechanism must account
   for every bypass and newly inserted evidence; do not choose a guard that works
   only when a new API writer cooperates. Verify specific PostgreSQL/Spring mechanics
   against primary documentation when relied upon. If no bounded source-compatible
   protocol can be justified, record the concrete blocker and separate prerequisite;
   do not enable publication to work around it.
5. **Time and authentication limits.** Specify one trusted evaluation instant,
   future/expired grants, policy/assertion validity and lock windows; distinguish
   clock expiry without a database write from persisted revocation. Bound transaction
   duration and define what validity at the chosen linearization point means. Never
   promise that remote directory/issuer changes are instantly known without an
   implemented owner evidence contract; retain SITE qualification separation.
6. **Topology lifecycle boundary.** Explain how live owner recheck will compose
   with separately persisted ownership epochs/head generations, hold on owner change,
   explicit transfer and both-company authorization, exact history/replay/list snapshots.
   The owner-reference query intentionally has no FacilityStatus/FacilityKind access
   policy; define any necessary operation restrictions explicitly rather than treating
   a successful reference as eligibility. No epoch/head/transfer/publication schema
   or service is authorized here; unchanged private V1 revision bytes remain private.
7. **Synthetic PostgreSQL race-test specification.** Use deterministic barriers,
   independent transactions/connections and explicit expected order, not sleeps.
   Cover revocation before and during protected read/write, new denial/permission
   removal, user lock/status, provider/link/mapping, role/group/attribute/policy,
   Party suspension and Facility transfer, plus rollback, timeout and deadlock recovery.
   Include independent users/companies/facilities and unsupported auth contexts.
   Tests must prove the selected fence at the operation boundary and exercise real
   owner evidence; sequential unit calls or stubbed permit do not prove concurrency.
8. **Implementation decomposition.** Document owner contracts and any architecture
   implications without private cross-module imports or circular callbacks. Identify
   source-backed prerequisite slices, validation gates and eventual migration/catalog
   requirements. Proposed areas are not write permission. A later separate registration
   must assign each runtime slice its exact HPR/message/allowlist and CI gate; the
   design commit may not implement them or silently register a broad batch.

The preflight's acceptance is a reviewable source-backed design and concrete blocker/
prerequisite disposition, not concurrency or runtime acceptance. Documentation-only
validation: 10 documentation-validator and 10 OpenAPI-validator unit tests; canonical
documentation and offline OpenAPI validators; existing P1 closure evidence consistency
check only; exact two-document scope/history/reference/whitespace checks. No Maven,
database execution, synthetic race result, closure or SITE commissioning is claimed.

| Task | Current status | Next |
|---|---|---|
| HPR-P25-042 | Completed — Production #659 / Docs #251 SUCCESS | Retain live owner observation boundary |
| HPR-P25-043 registration | Completed in source; exact-head Docs acceptance PENDING | Publish exact two docs; verify startup and STOP |
| HPR-P25-043 design execution | Planned / REGISTERED / NOT EXECUTED | Passing registration Docs and separate owner Next select this two-document preflight |
| Runtime evidence coordination, ownership epoch/transfer and publication/history/API | Planned / NOT REGISTERED / NOT IMPLEMENTED | Source-backed separate bounded registrations required |
| F1–F4 / P2.5 | OPEN | No closure or PLATFORM/SITE acceptance |
| Simulator / P3 | DEFERRED | Retain gas-first/GZ2 own-engine steady/transient and operator control |

Dynamic versioned inputs, synthetic development and owner-supplied deployment
calibration remain the policy. Current version/release remain unchanged. Publish
one guarded direct-main commit, verify exact parent/tree/two paths and exact-head
Docs startup, then STOP without waiting for CI completion or automatically advancing.


HPR-P25-043 registration publication validation: documentation-validator 10 and
OpenAPI-validator 10 unit tests PASS; canonical docs PASS (98 documents, 5,306
relative links, 24 modules, 13 P2 rows); offline OpenAPI PASS (263 operations,
244 paths, 231 schemas); existing P1 closure evidence consistency PASS without
executing closure. Exact two-document scope, absent earlier 043 registration,
existing source references, historical roadmap/reconciliation preservation and
whitespace checks PASS. No Java/Maven/database execution, runtime coordination,
release/version/deployment or closure change in this documentation-only stage.


## 2026-10-12 — Execute HPR-P25-043 cross-owner access linearization design

### Selection, scope and disposition

Owner Next selects exact message
`docs(topology): define cross-owner access linearization policy` after registration
[Docs #252](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38203928712)
SUCCESS on current main `b6ca8c5b199649d0602eee6bc548700f5742bc68`, tree
`1d807899239f48164b686f9fb4e7979835509db2`. Latest accepted runtime remains 042,
Production #659 and Docs #251 SUCCESS. AGENTS.md, active roadmap, reconciliation,
PROJECT_STATE.md, current source, transactions, repositories/security and relevant
primary PostgreSQL/Spring documentation were read. Only UPDATE these two governance
documents; CREATE/DELETE none. No Java, migration, tests, API, permission seed,
configuration, architecture export, dictionary, version, release, deployment or
closure mutation. Earlier registration/implementation records remain historical.

**Design result:** select an owner-composed coarse database table fence as the first
source-compatible mechanism for persisted evidence, subject to the gates below.
It covers ordinary SQL mutations without rewriting every existing writer. It is
not implemented, performance-qualified or a complete access protocol. Runtime remains
BLOCKED by native-token/session provenance, common-time evaluation and demonstrated
transaction participation. These are concrete prerequisites, not implicit acceptance
of 040/041/042 as publication authority. No new HPR/runtime allowlist is registered here.

### Exact current evidence and mutation inventory

Read root:
`src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationEvidenceAdapter.java`.
Its 19 entity classes, @Table mappings and predicates are enumerated below; each
corresponding Spring Data repository is inspected. Table names are schema-qualified
as public in the candidate fence, never caller supplied. Source prefixes:
`src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/` and
`src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/`.

| Exact table | Entity / repository basename | Read predicate / consumed state |
|---|---|---|
| `hidra_identity_attribute_definition` | `AttributeDefinitionJpaEntity` / `AttributeDefinitionJpaRepository` | attributeDefinitionId; status, target, dataType, multiValued, code |
| `hidra_identity_authorization_policy` | `AuthorizationPolicyJpaEntity` / `AuthorizationPolicyJpaRepository` | policyDomain = Permission.permissionDomain; ACTIVE status |
| `hidra_identity_authorization_policy_rule` | `AuthorizationPolicyRuleJpaEntity` / `AuthorizationPolicyRuleJpaRepository` | policyVersionId; ACTIVE, priority/effect/expressions/obligation |
| `hidra_identity_authorization_policy_version` | `AuthorizationPolicyVersionJpaEntity` / `AuthorizationPolicyVersionJpaRepository` | policyId; ACTIVE and effective interval; exactly one eligible version |
| `hidra_identity_external_group_mapping` | `ExternalGroupMappingJpaEntity` / `ExternalGroupMappingJpaRepository` | provider ID and membership sourceMappingId; status/mode/group/claim identifiers |
| `hidra_identity_external_identity` | `ExternalIdentityJpaEntity` / `ExternalIdentityJpaRepository` | assertion external ID or user ID; LINKED status, provider/user/subject identity |
| `hidra_identity_external_permission_mapping` | `ExternalPermissionMappingJpaEntity` / `ExternalPermissionMappingJpaRepository` | provider ID; DIRECT_GRANT, ACTIVE, permission, claim/effect |
| `hidra_identity_external_role_mapping` | `ExternalRoleMappingJpaEntity` / `ExternalRoleMappingJpaRepository` | provider ID; DIRECT_GRANT, ACTIVE, role/claim/scope |
| `hidra_identity_group` | `GroupJpaEntity` / `GroupJpaRepository` | group ID; ACTIVE status |
| `hidra_identity_group_role_grant` | `GroupRoleGrantJpaEntity` / `GroupRoleGrantJpaRepository` | groupId; ACTIVE, validity, scope and role |
| `hidra_identity_provider` | `IdentityProviderJpaEntity` / `IdentityProviderJpaRepository` | provider ID; ACTIVE for assertion, synchronization and attribute sources |
| `hidra_identity_permission` | `PermissionJpaEntity` / `PermissionJpaRepository` | exact code; exactly one ACTIVE row, resourceType/domain/action |
| `hidra_identity_role` | `RoleJpaEntity` / `RoleJpaRepository` | role ID; ACTIVE status |
| `hidra_identity_role_permission_grant` | `RolePermissionGrantJpaEntity` / `RolePermissionGrantJpaRepository` | roleId plus permissionId; ACTIVE, validity, effect and condition |
| `hidra_identity_subject_security_attribute` | `SubjectSecurityAttributeJpaEntity` / `SubjectSecurityAttributeJpaRepository` | subjectId and USER/GROUP/EXTERNAL_IDENTITY type; status/time/source/value |
| `hidra_identity_user_group_membership` | `UserGroupMembershipJpaEntity` / `UserGroupMembershipJpaRepository` | userId; status, validity, type/group and source provider/mapping |
| `hidra_identity_user` | `UserJpaEntity` / `UserJpaRepository` | user ID; ACTIVE/type/lockedUntil; trusted actor additionally HUMAN |
| `hidra_identity_user_permission_grant` | `UserPermissionGrantJpaEntity` / `UserPermissionGrantJpaRepository` | userId plus permissionId; ACTIVE, validity, effect and matching scope |
| `hidra_identity_user_role_grant` | `UserRoleGrantJpaEntity` / `UserRoleGrantJpaRepository` | userId; ACTIVE, validity, scope and role |

Each listed repository extends JpaRepository: inherited save/saveAll/delete operations
are mutator routes even where no current application command exposes them. Do not
invent an implemented policy editor, group synchronizer or import API from those beans.
Dedicated modifying adapters currently present for User, Permission, Role,
UserPermissionGrant, UserRoleGrant, RolePermissionGrant, GroupRoleGrant,
IdentityProvider and ExternalRoleMapping are their exact
`Jpa<basename>RepositoryAdapter.java` files in the Identity persistence adapter directory.
Other listed evidence rows have no dedicated modifying adapter found in that directory;
ordinary repository/admin/import SQL still affects the same fenced relations.

Concrete production callers/writers recovered:

| Owner source | Mutations / boundary |
|---|---|
| `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationService.java` | Creates role/permission and user-role/role-permission/user-permission grants through owner ports; new DENY grants matter |
| `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityUserApplicationService.java` | User creation via UserRepositoryPort |
| `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAdministratorBootstrapApplicationService.java` | User, local credential, admin role and user-role grant writes |
| `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAuthenticationOutcomeApplicationService.java` | Login outcome updates User lock/failure state under REQUIRES_NEW and writes authentication event |
| `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java` | Session start/touch/end/expiry and events; currently not consumed by 040/041 |
| `src/main/java/dz/sh/hidra/modules/party/application/service/PartyApplicationService.java` and `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JpaPartyRepositoryAdapter.java` | Party save/registration; PartyJpaRepository inherits general CRUD |
| `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java` and `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaFacilityRepositoryAdapter.java` | Facility save/registration; FacilityJpaRepository inherits CRUD including owner/state replacement |

Ownership relations add `public.hidra_party_party` and
`public.hidra_topology_facility`: **21 current evidence relations** in total. Party is
exact ACTIVE Party.id; Facility is exact Facility.id/live ownerPartyId. Owner snapshots,
legal names and arbitrary partyTypeId do not define membership or legal-company type.
LocalCredential and LoginSession are authentication lifecycle relations outside the
current 19-table evaluator; the provenance prerequisite must decide their exact
participation before the registry is considered complete for operation authentication.
They cannot be silently omitted once the new contract consumes them.

Production source scans covered repository inheritance, owner adapters/application
callers, @Modifying/executeUpdate, JdbcTemplate mutations and direct table-name SQL.
No custom native modifying statement for the 21 evidence relations was found in
production Java. The reviewed Topology JDBC append targets private revision storage,
not evidence. Flyway/admin SQL, cascades and future imports are not claimed absent:
all ordinary mutations to a covered relation participate in PostgreSQL's table locking.
Schema/read-graph changes require a new inventory and race test, not a stale hardcoded
list. No external writer or deployed integration was exercised in this static preflight.

`AuthorizationDelegationGrant` storage and adapter exist, but neither the current
JpaAuthorizationEvidenceAdapter nor AuthorizationPolicyEvaluator consumes that table.
This design does not add delegation permission; explicit access must continue to meet
both actual evaluator decisions. `AuthorizationDecision` persistence is an output,
not authorization evidence; excluding its table permits existing decision writes without
upgrading an evidence-table lock. Decision failure still aborts the protected operation.

### Authentication contexts and concrete blocker B1

`src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java`
issues signed sub/jti/expiry, authentication_type and optional identity_provider_id.
It does not bind externalIdentityId or session ID. In
`src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java`,
the token is issued before session creation. LoginSessionJpaEntity retains user,
provider/external identity and state, but no token jti; LoginSessionJpaRepository has
only inherited CRUD. There is no exact token/session relation for revocation lookup.

`src/main/java/dz/sh/hidra/platform/configuration/HidraSecurityConfiguration.java`
uses a standard JwtAuthenticationConverter for native tokens.
`TopologyCurrentActorQueryAdapter` redecodes native JWT and reads exact subject User,
but does not consume authentication_type/provider/session provenance.
`SpringAuthorizationContextAdapter.currentFor` returns null for a JWT principal that
is not HidraPrincipal. Thus a native token minted after LDAP/OIDC completion can use
internal grants without rechecking its source provider/link through that adapter.
This is a concrete limitation of accepted actor/query prerequisites, not a claim that
signature validation is missing or existing generic endpoints must be altered now.

| Context | Present source behavior | Required protected-operation disposition |
|---|---|---|
| Native Hidra bearer after LOCAL/LDAP/AD/OIDC completion | Validated token subject/User, but no exact session/source link recheck | B1 OPEN; no protected-operation fallback based on sub or authentication_type alone |
| In-memory LOCAL HidraPrincipal password authentication | Human/User state; no external assertion | Require trusted origin, current local provider/credential/session policy; no arbitrary authenticated token shape |
| LDAP / ACTIVE_DIRECTORY HidraPrincipal | Provider/link assertion without live directory claims; current evidence verifies stored eligibility | Bind exact trusted provider/link and validity in operation evidence; remote directory changes unknown until reflected locally |
| HidraOidcAuthenticationToken | Verified assertion binds issuer subject, provider/link/User, claims and expiry | Recheck current owner state/mappings and assertion freshness under fence; preserve verified signed provenance, never stored claims snapshots |
| Anonymous/system/unsupported/malformed provenance | No accepted trusted human context | Opaque ACCESS_DENIED before any payload/existence/count lookup |

B1 prerequisite: a separately registered Identity-owned operation-authentication
contract must bind verified token/context to exact current User, provider/link and
session evidence and define legacy unbound tokens, logout/revocation and credential
change behavior. Caller-supplied actor/provider/session IDs never repair the gap.
Existing issued claims are hints until verified against authoritative bindings;
username, roles, scope claims and equality to an owner grant no access. Issuer/directory
revocation unknown to Hidra remains a SITE/freshness limit, not instantaneous global
revocation. No new authentication flow or token/schema compatibility change occurs here.

### Mechanism comparison and selected persisted-state fence

| Mechanism | Disposition and reason |
|---|---|
| Boolean plus later write / updatedAt comparison / reread | Rejected: no common authority lifetime or predicate protection |
| Lock only the selected user/grants/Party/Facility rows | Rejected as complete solution: a missing/new denial or mapping/policy row is not one of those locked rows |
| Per-owner generation row + cooperative writers | Deferred: current inherited CRUD, bootstrap, auth outcome and SQL paths have no universally enforced invalidation contract |
| Database triggers feeding owner generations | Deferred optimization: requires complete trigger/cascade coverage, new migrations/catalog ownership, deterministic fence order and concurrent invalidation proof |
| Serializable annotation alone | Rejected as sufficient registration: current writers/boundary/auth clocks differ; annotation is not proof of commit ordering or provider freshness |
| Coarse database evidence table fence | Selected persisted-state baseline: explicit SHARE locks on all enumerated relations before owner reads; known cost and remaining auth/time/transaction gates below |

Technical basis checked on 2026-10-12:
[PostgreSQL 16 explicit locking](https://www.postgresql.org/docs/16/explicit-locking.html),
[LOCK](https://www.postgresql.org/docs/16/sql-lock.html),
[transaction isolation](https://www.postgresql.org/docs/16/transaction-iso.html),
[Spring propagation](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/tx-propagation.html),
[transaction proxy rules](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html)
and [JpaTransactionManager](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/orm/jpa/JpaTransactionManager.html).
The following operation policy is our source-derived design, not a claim of runtime
verification or that the framework automatically implements it.

PostgreSQL SHARE table locks conflict with ordinary evidence DML's ROW EXCLUSIVE
locks, including insertion of previously absent rows. Acquire all covered relations
before authority reads, then retain the locks through commit/rollback. READ COMMITTED
reads after successful acquisition see committed evidence after earlier conflicting
writers finish. Use fixed owner order Identity, Party, Topology; within an owner sort
fully qualified relation names. Include child relations if the schema later gains
inheritance/partitioning. A lock attempt outside an actual transaction fails closed.
No table identifier comes from requests. Current Party findByIdForShare is a local
row-lock precedent, not this fence.

Ordinary protected reads/writes only read the 21 authority tables. Their own changes
are to separately registered revision/head/event/request tables; acquire the facility
head row lock after authority fences, in exact facility/epoch order. Transfer that
also changes Facility must acquire its stronger SHARE ROW EXCLUSIVE Facility fence
at the start, not upgrade later; then both old/new company decisions and epoch/head
CAS. Transfer remains a later separate slice. Master owner updates outside transfer
are serialized against operations but cause hold on the next operation if binding
and live owner differ; no automatic epoch adoption or rewriting old history.

Readers can share these fences; a protected operation blocks unrelated evidence
writers too, including local login-outcome User updates and maintenance. This is a
coarse baseline, not multi-company concurrency optimization. No network call, OT
command, approval wait, long export/stream or directory authentication runs while
fenced. Bound histories to the existing 1..100 design page and freeze generation.
Before runtime selection benchmark lock hold/wait and auth/admin latency with
synthetic companies and a registered timeout/transaction-duration envelope. Failure
of that gate requires redesign; it does not authorize disabling the fence.

### Operation boundary, temporal blocker B2 and participation blocker B3

Future Topology application boundary owns the actual transaction and result:
acquire owner fences; capture trusted operation provenance; derive exact current
Facility/eligible Party; read persisted owner epoch/head; reject hold/mismatch; evaluate
both operation permissions; validate request/predecessor/qualification; perform
protected read or guarded head write plus immutable events/request/revision; commit;
only then return materialized result. No service exposes a reusable permit or grants
outside this boundary. Payload/count/revision-existence lookup follows authorization;
absence/out-of-scope uses the same opaque denial. Stored integrity failures remain
failures, not absence. F1 publication, payload and CAS schemas remain unregistered.

**Ordering contract:** a committed revocation before fence acquisition must be seen
and deny under current evidence. A revoker blocked by the operation's held fence can
commit only after the operation terminates; the successful operation orders before
that revocation. A revoked/changed Party/Facility has the same ordering. Rollback
produces no publication/head/event/request mutation. Fences prevent persistent-state
changes but do not freeze time, the issuer, or externally mutable directory state.

**Write linearization remains successful guarded head mutation**, as accepted in 038;
read linearization is the authorized materialized head/history snapshot under the
same fence/epoch/head protection. A read result describes that point, not continuing
permission when a later HTTP client receives it. Do not change the 038 contract to an
earlier convenient permission query. No precomputed boolean may authorize a later CAS.

**B2 OPEN:** current IdentityAuthorizationApplicationService chooses a different
Instant.now per evaluate call; actor and Party calls also do not expose one operation
time. User lock, grant/membership/attribute/policy/assertion intervals may change with
no DB mutation. Two PERMIT results do not prove permission at head-write instant.
The later owner contract must support one authoritative operation instant and a
proven-valid interval through the actual linearization point, or evaluate the required
time predicates at that point using a fully specified atomic technique. A validity
horizon must consider future DENY/version/attribute activation and token expiry, not
only the selected permitting grant; unsupported time expressions are indeterminate.
No arbitrary grace period, transaction-start timestamp, final Java reread or fake
clock token closes this gate. Current PermissionDecisionDto does not carry that proof.

**B3 OPEN:** `src/main/java/dz/sh/hidra/platform/configuration/HidraTransactionConfiguration.java`
only enables transaction management; it does not explicitly prove one JPA/JDBC
physical connection. Candidate guards require an existing non-read-only transaction,
READ COMMITTED, fresh owner persistence reads and transaction-bound invocation; no
cached pre-fence managed entities or second-level/query cache evidence. Establish
outer proxy interception/programmatic transaction explicitly and reject incompatible
ambient transactions. Do not rely on an inner annotation overriding an outer isolation.
JpaTransactionManager can combine JPA/JdbcTemplate with a matching DataSource/dialect;
future integration tests must prove same backend transaction, connection/lock lifetime
and rollback across all owner calls. open-in-view=false is useful, not that proof.

Nested savepoint rollback can release locks acquired after a savepoint; prohibit
partial rollback followed by continued protected work. No REQUIRES_NEW owner evaluation,
async continuation, detached permit or nested authentication-outcome writer inside the
fence. Existing REQUIRES_NEW login outcomes run outside protected operation boundaries.
Potential deadlock/lock timeout/storage/decision-persistence failure aborts the whole
operation, with no successful/denied-as-absence result or automatic retry changing
expected generation. Commit uncertainty is an error; later registered exact replay
handles retries after reauthorization. PostgreSQL's deadlock victim is not predetermined.

Identity and Party own their guarded evidence/eligibility exports; Topology owns its
facility/head orchestration. Public contracts use immutable JDK data and fail-closed
outcomes, not private JPA/domain types or callback imports back into Topology. Platform
may own technical transaction checks only. Exact public methods/files and any export
allowlist change need separate registration; no names or migrations are fabricated here.

### Synthetic race specification and prerequisite decomposition

Future tests must use PostgreSQL 16, real owner evaluators/adapters, independent
connections and barriers/latches with bounded waits, inspecting pg_locks or a confirmed
statement-start barrier rather than sleeping. No result below is claimed executed.

| Race / validation | Required observable result |
|---|---|
| Revocation commits before operation fence | New operation sees denied current evidence; no payload or publication side effect |
| Operation acquires fence before revocation DML | Writer is demonstrably blocked; operation commit/rollback releases it; ordering matches contract |
| Insert new DENY / delete permission / activate rule or mapping absent on earlier query | Same fence protection as updates, including phantom evidence; later operation denies or becomes indeterminate |
| User lock/status/type, role/group/grant/membership/attribute/provider/link changes | Real corresponding current evidence path changes next call; no stale actor or assertion snapshot authority |
| Future grant/DENY/version/attribute and assertion/token expiry during operation | Boundary-time proof rejects stale permission; both company/facility share the same authoritative instant |
| Party suspension or Facility owner replacement | Current operation ordered under fence; next owner/epoch mismatch held; no cross-company payload inheritance |
| Same-generation competing publications / first publish | Exactly one CAS winner; loser rolls back all new revision/event/request data |
| Protected head/history/list and concurrent publish/transfer | Materialized result is authorized for one epoch/frozen generation, no cross-epoch count/cursor leakage |
| Two users/companies/facilities | Scope isolation, exact derived owner and pair decisions; explicitly measure global evidence-writer blocking |
| Native token provider disable/link removal/session logout and unsupported provenance | B1's real binding denies; no internal-grant fallback from an unbound JWT |
| Missing transaction/wrong isolation/different DataSource, preloaded EntityManager/cache, savepoint escape | Guard rejects; JPA/JDBC rollback/lock-lifetime proof cannot pass on mocks |
| Deadlock, lock timeout, persistence error, request conflict, commit uncertainty | Entire operation fails/rolls back where commit not established; no false success or silent retry |
| Both-company transfer plus qualified predecessor/corrupt stored bytes | Explicit transfer policy and new epoch; integrity error remains error, existing V1 bytes unchanged |

Required separately registered order:

| Prerequisite area | Current disposition / necessary next evidence |
|---|---|
| B1 operation authentication provenance/session binding | OPEN; source-backed Identity design/execution preflight before token/schema compatibility choices |
| B2 single operation-time evidence and boundary-time proof | OPEN; Identity evidence contract and bounded temporal semantics before authority is returned |
| B3 transaction participation and owner-composed fences | OPEN; technical owner contracts, same-connection/rollback/cache/lock-lifetime tests; no publication API |
| Database race and contention gate | NOT IMPLEMENTED; deterministic tests and bounded performance envelope after B1–B3 implementation acceptance |
| Epoch/head/event/replay/transfer schema and Topology publication service | NOT REGISTERED; follow those gates, then separate F1 APIs/acceptance |

Next eligible work after exact-head design Docs succeeds and separate owner Next is
**recover/register the bounded B1/B2 Identity operation-evidence prerequisite**.
If their source-backed scope needs splitting, register B1 first; do not bundle the
whole authentication lifecycle, table fence and publication schema into one task.
No next runtime code or later HPR is selected by this document. Source-compatible
persisted-state fence is the chosen design direction; end-to-end runtime is BLOCKED,
not solved. This disposition satisfies the registered preflight's blocker path.

F1–F4/P2.5 OPEN; simulator/P3 DEFERRED. Retain gas-first/GZ2, Hidra own engine,
steady/transient direction, dynamic versioned inputs and operator-controlled
recommendations; synthetic development and owner-supplied deployment calibration.
Version/release/deployment and closure unchanged. Design Completed in source;
exact-head Docs acceptance PENDING. Validate only the registered two-document scope,
publish one guarded direct-main commit, verify exact-head Docs startup and STOP
without waiting for completion or advancing.


HPR-P25-043 design publication validation: documentation-validator 10 and
OpenAPI-validator 10 unit tests PASS; canonical docs PASS (98 documents, 5,306
relative links, 24 modules, 13 P2 rows); offline OpenAPI PASS (263 operations,
244 paths, 231 schemas); existing P1 closure evidence consistency PASS without
executing closure. Exact two-document scope, 19 entity/repository source references,
all cited production paths, preserved historical registration/reconciliation and
whitespace checks PASS. PostgreSQL/Spring mechanics checked against linked primary
documentation; selected protocol and B1/B2/B3 are design findings, not executed
race/transaction/performance evidence. No Java/Maven/database execution or version/
release/deployment/closure mutation. Design acceptance remains exact-head Docs PENDING.


## 2026-10-12 — Register HPR-P25-044 operation-authentication provenance preflight

### Selected registration and accepted design boundary

Owner Next selects recovery/registration of 043's B1 prerequisite first, keeping B2
operation-time evidence and B3 transaction/fences separate. Current main is
`f6dabb6a4585c7c30203352f9f96a2892aa79a84`, tree
`04eaaacc5033cf0d870c4ad44377774831358ac4`; exact-head
[Docs #253](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38204661494)
is SUCCESS. Latest Production #659 and Docs #251 on accepted 042 remain SUCCESS;
043 changed only the two governance documents. Its coarse persisted-state fence,
race specification and B1/B2/B3 disposition are accepted as design only. No runtime
authentication, timing, concurrency, publication or SITE acceptance is inferred.

Read AGENTS.md, PROJECT_STATE.md, active roadmap/reconciliation, actual completion,
issuer/ports/models, native actor/assertion handling, session lifecycle/persistence,
existing migrations/tests and accepted architecture export. 044 was absent from
prior active/history/reconciliation registrations. HPR-P25-043 design acceptance is
recorded in current tables; historical pending records remain preserved.

Current-stage exact message:
`docs(identity): register operation authentication provenance preflight`.
Current-stage exclusive UPDATE allowlist is
`doc/roadmap/ULTIMATE_ROADMAP.md` and `doc/model-remediation/RECONCILIATION.md`.
CREATE/DELETE: none. This is registration only; no future task executes in this commit.

### Recovered source and reason for the B1-only split

| Current exact source | Observed behavior / bounded gap |
|---|---|
| `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java` | Builds normalized principal, issues access token, then creates session; response returns both jti and sessionId but no persistent relation between them |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java` | Generates signed sub/jti/expiry/authentication_type and optional identity_provider_id; no session or external-identity binding claim |
| `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AccessTokenIssuerPort.java` | issue accepts principal only; changing issuance inputs is an owner-contract compatibility decision |
| `src/main/java/dz/sh/hidra/modules/identity/application/model/IssuedAccessToken.java` | Validates bearer material, jti and issued/expires interval; not a database session binding |
| `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java` and `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java` | Exact session/user/provider/external identities, status, expiry, protocol and endedAt; no token jti field |
| `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LoginSessionRepositoryPort.java` and `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/LoginSessionJpaRepository.java` | Save/exact session read and inherited CRUD; no exact token-to-session query |
| `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java` | ACTIVE/EXPIRED/REVOKED/LOGGED_OUT lifecycle; logout/revoke/expire persist status, but accepted actor lookup does not consume this session state |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/TopologyCurrentActorQueryAdapter.java` | Redecodes native token and resolves exact active/unlocked human User; does not expose session/source-provider/link proof |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringAuthorizationContextAdapter.java` | Uses trusted HidraPrincipal/OIDC assertion; ordinary JWT principal does not yield that provider/link assertion |
| `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql` and `src/main/resources/db/migration/V20261006_012__hmr_087_login_session_evidence.sql` | Existing session schema and explicit protocol/endedAt integrity; no token binding is safely derivable from user/provider/time or correlationId |

Existing source tests include
`src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java`,
`src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionIntegrityMigrationTest.java`,
`src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TopologyCurrentActorQueryAdapterTest.java`
and `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/AuthorizationAssertionTrustTest.java`.
They are read-only precedents, not proof of native token/session association or runtime
atomic revocation. Existing accepted export prefix remains unchanged in
`src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`.

The binding affects issuance order, failure/rollback, persisted uniqueness and legacy
tokens; it cannot be safely treated as one more read-only boolean query. No raw token
storage or guessed existing-session match is selected. B2/B3 are not solved by a new
session ID claim. The next bounded step is the B1 policy/compatibility design, not
an authentication rewrite or combined fence/publication implementation.

### Registered next executable task — HPR-P25-044

Exact policy-task message:
`docs(identity): define operation authentication provenance policy`.
State: PLANNED / REGISTERED / NOT EXECUTED. Passing exact-head registration Docs and
separate owner Next select this solo documentation preflight. Recheck then-current
main and latest CI; repair failed CI first.

Exclusive policy-task allowlist:

| Action | Exact path |
|---|---|
| UPDATE | `doc/roadmap/ULTIMATE_ROADMAP.md` |
| UPDATE | `doc/model-remediation/RECONCILIATION.md` |

CREATE/DELETE: none. Java/tests, schema/Flyway, token issuer/completion, session
contracts/persistence/lifecycle, security configuration, permission evaluator, exports,
API/OpenAPI, dictionary/configuration/dependencies, gap register, release/version,
deployment and closure are read-only. No migration version or new Java filename is
reserved. This registration and forthcoming policy are not runtime authorization.

### Required B1 policy output and acceptance

1. **Exact trusted provenance matrix.** Define operation eligibility for native
   Hidra bearer minted through LOCAL/LDAP/ACTIVE_DIRECTORY/OIDC, their in-memory
   trusted principal/assertion counterparts and unsupported contexts. Bind verified
   issuer/audience/signature/subject, auth type, User, provider/link and session;
   caller values and role/scope/name claims never supply missing proof. Preserve
   current actual provider protocols; AD versus LDAP cannot be inferred only from
   a session protocol label. Do not claim remote directory/issuer state is instantly
   known when only locally persisted evidence is available.
2. **Select one token/session association design.** Compare signed session identity
   plus authoritative exact binding against persisted jti association, using current
   issuer/model/storage limits. Choose uniqueness/cardinality, normalization/lengths,
   expiry/protocol/provider/external-subject agreement, server-issued immutable IDs
   and rollback/integrity behavior. No raw bearer/password storage or lookup by
   approximate user/time/correlationId. Determine whether new dedicated binding
   storage is required rather than silently overloading historical session fields.
3. **Issuance/lifecycle and compatibility.** Define successful completion ordering,
   one physical transaction for persistent binding/session work, token/signing and
   session-save failure, retry/idempotency, concurrent logout/revoke, and when a
   token may be returned. Specify legacy unbound tokens and non-active/missing/
   mismatched sessions for future protected operations, keeping existing generic
   actor/authorization behavior distinct until an explicitly registered integration.
   Decide credential rotation/provider disable/link removal effects without imposing
   a local password workflow on directory users. No automatic migration/backfill
   pretending old tokens had trusted provenance. API/client compatibility and session
   end-state preservation must be explicit; release/version remains unchanged here.
4. **Owner contract boundary and remaining gates.** Define Identity-owned immutable
   operation-authentication evidence with minimum provenance/validity, no reusable
   grant or caller actor. Preserve original verified OIDC assertion handling and
   current-evidence recheck; source snapshots/decoded unsigned claims are insufficient.
   Identify new session/binding/credential reads that enlarge 043's fence registry.
   B1 lookup remains subject to separately registered B2 temporal proof and B3 same
   transaction/lock participation; neither an ID DTO nor an expiry timestamp proves
   permission at the operation linearization point. No private cross-module imports
   or callback cycle, session mutation from Topology, or user-facing publication API.
5. **Synthetic verification specification.** Cover all accepted contexts, two
   users/providers/links/sessions and independent companies/facilities; forged or
   malformed claims, legacy/unbound token, swapped session/jti/subject, duplicate
   issuance, missing/expired/revoked/logged-out session, disabled/locked/nonhuman user,
   provider/link changes, credential policy and unsupported auth. Specify real
   signature/issuer and PostgreSQL uniqueness/rollback tests and deterministic
   concurrent issuance/logout/revocation barriers. No live provider, credentials,
   real-data fixture or SITE commissioning prerequisite; owner calibration remains
   a separate deployment input.
6. **Bounded implementation decomposition.** Recover exact source-backed owners,
   prerequisites, candidate mutation/test/migration/dictionary areas and compatibility
   risks. If B1 needs additive binding storage before issuer/reader work, separate
   those stages; do not combine them with B2, B3 or publication. A later separate
   registration must assign exact HPR/message/file allowlists and acceptance gates.
   Proposed areas in the policy do not approve files, migrations or execution. If a
   policy choice cannot be justified from owner direction/source, record a concrete
   blocker instead of guessing or enabling a fallback.

Policy acceptance is a reviewable source-backed B1 design and prerequisite disposition,
not schema/API/session-revocation functionality. Validate only the two-document scope,
10 documentation-validator and 10 OpenAPI-validator unit tests, canonical docs/offline
OpenAPI, existing P1 closure evidence consistency check, source/history/whitespace.
No Maven/database/provider/race test or closure execution is claimed in this stage.

| Task | Current status | Next |
|---|---|---|
| HPR-P25-043 | Completed design — Docs #253 SUCCESS | Persisted-state fence direction only; runtime B1/B2/B3 remain OPEN |
| HPR-P25-044 registration | Completed in source; exact-head Docs acceptance PENDING | Publish exact two docs; verify startup and STOP |
| HPR-P25-044 policy execution | Planned / REGISTERED / NOT EXECUTED | Passing registration Docs and separate owner Next select only this B1 policy preflight |
| B1 runtime, B2 time evidence, B3 fences/transaction and publication/history/API | OPEN / NOT REGISTERED / NOT IMPLEMENTED | Separate source-backed registrations and runtime acceptance required |
| F1–F4 / P2.5 | OPEN | No closure or PLATFORM/SITE acceptance |
| Simulator / P3 | DEFERRED | Retain gas-first/GZ2 own-engine steady/transient and operator control |

Dynamic versioned inputs, synthetic development and owner-supplied deployment
calibration retained. Current version/release unchanged. Publish one guarded direct-main
commit, verify exact parent/tree/two paths and exact-head Docs startup, then STOP
without waiting for completion or selecting another stage.


HPR-P25-044 registration publication validation: documentation-validator 10 and
OpenAPI-validator 10 unit tests PASS; canonical docs PASS (98 documents, 5,306
relative links, 24 modules, 13 P2 rows); offline OpenAPI PASS (263 operations,
244 paths, 231 schemas); existing P1 closure evidence consistency PASS without
executing closure. Exact two-document scope, absent prior 044 registration, all
cited source/test/migration references, historical roadmap/reconciliation preservation
and whitespace checks PASS. No Java/Maven/database/provider/race execution, token/
session/schema mutation or release/version/deployment/closure change. Registration
acceptance remains exact-head Docs PENDING; B1 policy execution is not selected here.


## 2026-10-12 — Execute HPR-P25-044 operation-authentication provenance policy

### Selection and source-backed disposition

Owner continuation selects only `docs(identity): define operation authentication provenance policy`.
Baseline main `ad37bb1da77f3a57b73fa06e2fe39417bc979ad4`, tree
`45fcb81433f61b1bad47fa00ed8761a99a95b33e`; registration
[Docs #254](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38211020728)
SUCCESS. Latest runtime Production #659 remains SUCCESS at accepted 042.
AGENTS.md, PROJECT_STATE.md, current roadmap/reconciliation, accepted 043 and the
sources below were reviewed. Exclusive UPDATE scope is these two governance documents;
CREATE/DELETE none. Historical records, including originally pending registrations,
remain preserved. Policy Completed in source; exact-head policy Docs acceptance PENDING.

**Decision:** dedicated immutable persisted association from validated Hidra issuer/jti
to one exact LoginSession, with provider-specific provenance and current eligibility.
This is a B1 design, not implemented authentication, revocation or publication authority.
Storage, trusted completion and owner reader require separate registrations; B2/B3 remain
OPEN. No new HPR code, Java filename, migration version or runtime allowlist is assigned.

### Revalidated exact source and compatibility constraints

| Exact source | Finding governing this policy |
|---|---|
| `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java` | Constructs HidraPrincipal, calls issuer, then session lifecycle; completion has no transaction annotation and returns both sessionId and jti without association |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java` | HS256 claims include sub, server UUID jti, iat/exp, authentication_type and provider ID; no session/link claim; timestamps use Instant.now |
| `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AccessTokenIssuerPort.java` and `src/main/java/dz/sh/hidra/modules/identity/application/model/IssuedAccessToken.java` | Principal-only issuance port; issued metadata has jti/time but not issuer/audience; future completion must obtain exact verified/configured metadata without unsigned parsing |
| `src/main/java/dz/sh/hidra/platform/configuration/HidraJwtDecoderConfiguration.java` | Separate qualified native/external decoders; native HS256 issuer/time validation, audience conditional on configuration; protected-operation policy requires nonblank expected audience and verified membership |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java` | Current active LOCAL provider, credential and User; local success outcome recorded before completion |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LdapAuthenticationProvider.java` | Actual provider is selected from exactly one active LDAP/ACTIVE_DIRECTORY candidate; verified external subject resolves link; selected type, not caller selector, is authoritative |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverter.java` | Validated issuer resolves active OIDC provider and LINKED subject/User; captures original verified assertion |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringAuthorizationContextAdapter.java` and `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraOidcAuthenticationToken.java` | OIDC carries bounded verified claims/expiry; ordinary native JWT carries no original external assertion; token class constructors alone are not an origin proof |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/TopologyCurrentActorQueryAdapter.java` | Accepted actor query redecodes native subject and checks human/current User; no session or provider/link proof |
| `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java` | Session UUID, ACTIVE lifecycle and expiry; LDAP and AD both record LDAP protocol; terminal sessions are preserved; no token association |
| `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java` and `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java` | Existing ID references varchar(80), protocol varchar(80), nullable expiry and end time; no reliable auth-type or jti reconstruction |
| `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LoginSessionRepositoryPort.java` and `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/LoginSessionJpaRepository.java` | Existing exact session read/save, no association lookup |
| `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java` and `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LocalCredentialJpaEntity.java` | Credential identity/status/passwordChangedAt, but no monotonic security version; timestamp equality cannot prove no rotation |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentityProviderJpaEntity.java` and `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalIdentityJpaEntity.java` | Provider type/status/issuer and link user/provider/externalSubject/status; external subject uses text |
| `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql` and `src/main/resources/db/migration/V20261006_012__hmr_087_login_session_evidence.sql` | Existing logical session fields and explicit protocol migration; old sessions are evidence, not token bindings; existing checksums remain immutable |
| `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedAuthorizationAssertion.java` | Immutable bounded claims from trusted infrastructure only; missing proof must not be filled by claims snapshots |
| `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` | Accepted public Topology contract prefix only; no export change authorized here |

### Trusted provenance matrix for future protected operations

All eligible paths require exact current ACTIVE/unlocked HUMAN User, current active
provider of the recorded type, ACTIVE session with endedAt absent and finite expiry,
plus exact immutable binding. No role, scope, username, getName, caller actor/provider/
session, correlationId or approximate user/time query supplies absent proof.

| Context | Required exact provenance / eligibility |
|---|---|
| Native Hidra bearer minted through LOCAL | Redecode original bearer with qualified native decoder; verified signature/algorithm, configured issuer/audience, sub/jti/iat/exp/authentication_type/provider match binding. LOCAL protocol, LOCAL provider, absent external link/subject; exact current active local credential and matching security version |
| Native Hidra bearer minted through LDAP | Same native proof; recorded type LDAP and session protocol LDAP; current LDAP provider, LINKED exact link with identical User/provider/external subject; no LOCAL credential requirement |
| Native Hidra bearer minted through ACTIVE_DIRECTORY | Same native proof; recorded type ACTIVE_DIRECTORY, LDAP session protocol and current AD provider/link agreement. Never infer AD from LDAP protocol or the request selector |
| Native Hidra bearer minted through OIDC | Same native proof plus OIDC protocol, exact current OIDC provider/issuer and LINKED external subject. Binding retains verified source assertion deadline/issuer provenance; native token cannot recreate its live external claims |
| Trusted in-memory LOCAL or LDAP/AD HidraPrincipal | Existing password-token shape alone is insufficient. Require server-only completion association to the same committed session/binding, provider-specific checks above and bounded validity; without it deny. Never accept arbitrary authenticated token constructors/details as receipt |
| Trusted HidraOidcAuthenticationToken | Require original validated OIDC assertion with exact User/provider/link/subject, bounded expiry and current evidence plus server-only completion association. Current converter-before-completion context lacks that receipt: deny protected operations until separately integrated. Preserve existing completion bridge |
| Anonymous, unauthenticated, system/service/nonhuman, generic strings, opaque principals, bare external JWT, SAML2/OAUTH2/API_TOKEN and other provider enum values | ACCESS_DENIED; no automatic protocol/provider fallback. KEYCLOAK/AZURE_AD/OKTA enum support in lifecycle is not an implemented accepted login route |

Malformed/unbound/ineligible authentication produces opaque ACCESS_DENIED before
payload/existence/count/history lookup; storage/decoder infrastructure failure or corrupt
binding is an operational error, never a successful/absent result. No general API behavior
is changed by this policy. Remote LDAP password/account changes and external OIDC logout
remain unknown until locally reflected or bounded validity ends; no online introspection
or instantaneous remote revocation is claimed.

Native bound external-source authentication can support current internal permissions
only after provider/link proof is established. It cannot supply missing external claims,
ignore a DENY/indeterminate outcome or substitute an internal grant where an actual
rule requires a verified external assertion. Preserve original trusted OIDC assertion
processing; do not persist/replay its claim snapshot as a new assertion or copy native
role/scope claims into one. Later reader/evaluator integration must prove this distinction.

### Association alternatives and selected cardinality/integrity

| Alternative | Disposition |
|---|---|
| Add signed session identity and authoritative association | Viable but needs preallocation and issuer-port/claim changes; still needs persisted provenance, lifecycle reads and legacy policy; session claim alone cannot bind source evidence |
| Dedicated persisted issuer+jti association | SELECTED: existing server UUID jti provides exact lookup; leaves native claim envelope intact and avoids guessing session by user/time; additive owner storage still required |
| Overload correlationId, client metadata or historical session fields | REJECTED: nonunique/caller metadata, wrong semantics and no trusted association |

Select one new immutable binding per newly completed logical session and exactly one
native access token per binding. Database uniqueness on (native issuer, jti) AND session
identity; multiple independent sessions for one User are allowed. Refresh/multiple tokens
per session, rebind, deletion/recreation and token replay as login are not approved.
Existing sessions remain unbound; no retrofit or backfill by user/provider/time. Exact
same binding retry may only compare identical fields in a registered internal persistence
contract; any differing duplicate conflicts and rolls back. No bearer/signature/password/
hash, claim snapshot, private key or credential material is stored in the binding.

Selected persisted minimum: immutable binding identity/schema version, native issuer,
configured audience, jti, subject/User, session reference, actual authentication type,
provider identity/type, optional exact external-link identity and subject/source issuer,
issuedAt/expiresAt, OIDC source expiresAt when applicable, and LOCAL credential identity/
security-version only for LOCAL. Session/provider/User/link references must agree across
all records; database foreign keys plus uniqueness/check constraints and immutable-write
protection are required, with owner reader/completion verifying relational semantics.
No cascading delete may silently erase/reassign association evidence; later registration
must inventory existing owner deletes and choose explicit rejection/retention behavior.

New binding/session/jti UUIDs are canonical lowercase hyphenated UUID text (36 characters)
within current varchar(80) identity limits. Referenced owner IDs retain exact existing
case/content, nonblank and <=80 characters; do not retrofit UUID rules onto existing User/
provider/link IDs. Reject whitespace-bearing signed identity claims, wrong JSON types,
unknown auth types, oversize IDs and mismatches; never trim or truncate an authenticated
identifier to make it match. Issuer/audience/external subject preserve exact verified
strings and case. Source external subject currently text: no arbitrary truncation/new
site limit; future registration must document a transport/parser bound compatible with
existing data and block incompatible values. Issuer/audience come from trusted deployment
configuration, never request values; changing them requires reauthentication.

Choose UTC whole-second token/session/binding timestamps to match JWT NumericDate
representation; one completion time and exact shared finite deadline, issuedAt < expiresAt.
Session start equals completion issuedAt; session expiry equals binding/token expiry and
is capped by verified OIDC source expiry. LOCAL/directory tokens use configured positive
Hidra TTL; OIDC requires source expiry, not a guessed deadline. Missing/null expiry is
ineligible even though legacy session schema permits it. Current independently sampled
session Instant.now does not yet satisfy this equality; completion/lifecycle integration
is a prerequisite. Decoder skew must never extend protected-operation eligibility beyond
actual deadline. B2 defines the authoritative operation instant and boundary-time proof.

### Issuance, termination, credential policy and compatibility

Trusted completion input must originate only from the actual successful provider path,
not an arbitrary CompleteAuthenticatedPrincipalUseCase caller. Capture LOCAL credential
ID/security version when its password is verified and compare that same version during
completion; capturing only the new current version would wrongly accept a password
verified before a concurrent rotation. Carry directory provider/link/subject provenance
and original OIDC verified issuer/assertion/deadline through a bounded server-only input.
No receipt may be synthesized from a caller's DTO or authenticated=true token shape.
Current AuthenticatedPrincipalInput/HidraPrincipal do not prove all of this; trusted
transport and writer synchronization require separately registered integration/tests.

Future sequence: trusted provider authentication first; then an actual proxied Identity
completion transaction rechecks current User/provider/link/credential, generates IDs and
one time, creates the session, signs token using that bounded metadata, verifies issued
metadata agreement, inserts immutable association and required session events, then
commits all persistent work together. Server-only context receipt and existing response
are released after successful commit; no detached or REQUIRES_NEW session/binding write.
Signing/session/binding/event/uniqueness failure rolls back persistent completion and
returns no token/receipt. Signed but unreturned token is discarded without logging; DB
commit uncertainty returns error, never success or guessed recovery. Network loss after
commit can leave a valid unseen session; a new explicit login creates a new pair. No
existing API idempotency promise or automatic retry is inferred from correlationId.

Existing LOCAL outcome service success means credentials verified before completion;
its independent event is not rewritten as proof of committed token delivery. Keep that
trace semantics explicit; non-LOCAL session LOGIN_SUCCESS stays in completion transaction.
Authenticate providers outside the future protected-operation fence; do not perform slow
remote verification while holding cross-owner locks. A completion-time recheck cannot
retroactively prove a remote provider did not change between verification and commit.

Logout/revoke/expiry retains the immutable binding and original terminal status/endedAt;
no touch/retry/credential reauthentication resurrects the old session. End-state updates
are owner-controlled, serialized with issuance and future protected operations. If a
termination commits first, next operation denies; if operation fence wins, termination
waits through operation commit/rollback. Real races and all lifecycle writers need B3
acceptance; no result is claimed from current annotations. A terminal session cannot gain
another binding. Reenable/relink must not resurrect sessions invalidated by an actual
security event: provider disable/link unlink/reassignment must terminalize affected active
sessions or advance an enforced security generation before returning success. That writer
coverage is a later prerequisite, not existing runtime behavior.

LOCAL credential disable/removal/replacement/reset/rotation must invalidate old LOCAL
bindings and require fresh login, including rotate-away-and-back. Select an owner-controlled
monotonic credential security version plus exact credential ID; increment on security
changes atomically with credential writes, compare captured version on every operation.
Ordinary metadata/rehash that does not change credentials needs an explicit classified
writer contract before exemption. Current passwordChangedAt/updatedAt are not a version;
no timestamp/hash fallback or local password hash in operation evidence. All credential
writers including administrative/native SQL paths need database-enforced invalidation
or a demonstrably exhaustive protocol, with upgrade handling separately registered.
This is a concrete additional storage/writer prerequisite. LDAP/AD/OIDC paths require no
local password setup; remote rotation does not imply local knowledge. Current provider/
link changes deny mismatched or disabled evidence; irreversible invalidation semantics
above also require writer implementation and concurrent tests.

Existing AuthenticationResult/API fields sessionId, token type/value, jti and timestamps
remain the intended response envelope; no additional client-supplied identity is accepted.
Fresh login through already supported routes produces future eligible bindings after
registered implementation. Legacy unbound tokens keep existing generic API behavior
until separately authorized integration, but are denied at new protected-operation
boundaries even if cryptographically valid; fresh login is required there. No backfill,
forced rewrite of old sessions, breaking generic actor/permission export, version bump,
release publication or API/OpenAPI edit is performed here. Compatibility tests must
prove both envelopes and the deliberate protected-operation restriction before rollout.

### Identity owner evidence and remaining fence/time gates

Future immutable JDK-only Identity operation evidence carries trusted User/provider/type,
exact session/binding identity, source/link provenance, credential version where relevant,
issued/deadline metadata and validity outcome. It carries no bearer, password/hash, JPA
entity, callable callback, reusable grant or caller actor. Identity resolves the ambient
server-authenticated origin; Topology cannot set or mutate session provenance. Exact
public method/package/DTO names and architecture export changes require registration.
Existing 040 actor and 041 pair query remain accepted within their narrow scope; neither
substitutes this evidence or authorizes publication by itself.

Extend 043's Identity read/fence inventory with `public.hidra_identity_login_session`,
`public.hidra_identity_local_credential` for LOCAL, and the separately named future binding/
security-generation relations. User/provider/external-link relations already appear in
043; actual full evidence graph still determines fence membership. Acquire all needed
locks before reads in deterministic owner/relation order; never upgrade a partly read
fence or reuse preloaded JPA/cache evidence. Session/binding reads, credential writes,
provider/link invalidation and protected work must demonstrate same transaction/connection,
rollback and lifetime under B3. Platform owns only technical transaction checks.

B1 lookup establishes authentication provenance, not temporal permission. B2 remains
required for one operation instant, strict deadlines and company/facility paired decisions;
B3 remains required for participation, persisted-state fences and revocation ordering.
Expiry DTOs, authenticated token constructors and signature revalidation alone prove
neither gate. Global table fence contention, new writer coverage and remote state freshness
remain explicit limits. No detached evidence may be cached as a permit across operations.

### Synthetic verification specification — not executed in this stage

| Required future test | Observable acceptance |
|---|---|
| All four supported native source types and trusted completed context counterparts | Correct exact binding and human/current owner state; LOCAL credential policy only on LOCAL; directory type retained despite LDAP protocol |
| Real test-key signed JWT, wrong issuer/audience/algorithm/signature, malformed claims and decoder failures | Authentic valid tuple alone accepted; opaque rejection versus infrastructure error; no unsigned parsing or role/scope/name substitution |
| Two users/providers/links/sessions; independent synthetic companies/facilities | Swap sub/jti/provider/link/session rejected; same-user different-session swap also rejected; authentication never grants either company's permission |
| Legacy/unbound/missing/duplicate binding and unsupported/context-constructor-only authentication | Protected operation denies without payload/count/existence; duplicate conflict cannot deliver token; generic compatibility preserved |
| Missing/expired/revoked/logged-out session; endAt inconsistency; locked/disabled/nonhuman User | Exact denial; expired boundary does not inherit decoder skew; old terminal evidence never resurrected |
| Provider disable/reenable, link unlink/relink/reassignment, LOCAL credential rotation/back/replace | Original bindings remain invalid after security events; no LOCAL requirement on directory/OIDC; unobserved remote changes documented |
| OIDC source expiry/provider issuer change; claim-dependent allow/DENY/missing assertion | Original trusted assertion preserved; source deadline caps session; no snapshot recreation or internal-grant bypass |
| PostgreSQL 16 fresh and upgrade store, immutable rows, uniqueness/FK/check constraints | Existing migrations/checksums unchanged; old sessions remain unbound; exact binding and one-token/session constraints enforced by real database |
| Completion signing/session/binding/event failure and response/commit uncertainty | Whole persistent completion rollback where commit not established; no token returned; existing LOCAL outcome distinguished |
| Independent connections with barriers/latches for issuance/logout/revoke/credential/provider/link changes | Confirm actual statement/lock blocking with bounded waits; no sleeps; terminal writer/operation ordering and rollback proved after B3 |
| Existing API envelope, original assertions and architecture contracts | Current clients need no session claim input; no private module imports/callback cycle; existing actor/pair behavior unchanged until selected integration |

Synthetic credentials and test keys only, no live directory/issuer/SCADA or customer data.
These are required test specifications, not passing Maven/database/provider/race results.
B2/B3 operation and concurrency suites need separate registration and exact-head runtime CI.

### Bounded prerequisite decomposition and next boundary

| Proposed stage area — NOT REGISTERED | Source-backed ownership / required acceptance |
|---|---|
| Additive binding and LOCAL security-version storage preflight FIRST | Identity session/credential models, ports, persistence and exact schema constraints; recover all security writers/deletes, immutable association, version coverage, fresh/upgrade/replay and dictionary ownership. Forward migration and exact files/message/code require separate registration |
| Trusted completion and lifecycle integration AFTER storage | Existing completion, issuer metadata contract, LOCAL/LDAP/OIDC trusted adapters, session lifecycle and APIs' existing response mapping; one physical completion transaction, source deadline, immutable receipt, invalidation and rollback tests |
| Identity operation reader and permission integration AFTER issuance | Existing actor/security/context/evidence ownership plus separately registered immutable public contract/export; current session/provider/link/credential reads, error distinction and original assertion tests |
| B2 common-time / boundary-time evidence | Separately registered Identity evaluator/clock contracts and paired permission proof; not bundled into B1 reader |
| B3 owner-composed persisted-state fence and race/contention gate | Separately registered Identity/Party/Topology technical participation, enlarged inventory, real PostgreSQL lock/cache/rollback and deterministic races, performance envelope |
| Publication/history/schema/API | Follow accepted B1/B2/B3 and race gates; no allowlist or execution selected |

Candidate existing source owners above are review areas only; no new filenames/migration
versions are fabricated or reserved. LOCAL version coverage and provider/link terminal
invalidation are concrete unimplemented prerequisites; if recovery cannot establish
complete writer/schema compatibility, record that blocker before implementation instead
of enabling a fallback. This policy completes 044's design requirement, not the runtime.

Next eligible action after policy exact-head Docs succeeds and separate owner Next is
**recover/register the additive B1 binding/credential-security storage preflight first**.
No next code/message/file allowlist is yet registered; do not execute a runtime stage.
F1–F4/P2.5 remain OPEN, simulator/P3 DEFERRED. Retain gas-first/GZ2, Hidra own engine,
steady/transient direction, dynamic versioned inputs and operator-controlled recommendations.
Use synthetic development; owner supplies deployment calibration. Version/release,
deployment and closure unchanged. Publish one guarded two-document direct-main commit,
verify exact parent/tree/scope and exact-head Docs startup, then STOP without waiting
for completion or advancing.


HPR-P25-044 policy publication validation: documentation-validator 10,
OpenAPI-validator 10 and dictionary-generator 17 unit tests PASS; canonical docs
PASS (98 documents, 5,306 relative links, 24 modules, 13 P2 rows); offline OpenAPI
PASS (263 operations, 244 paths, 231 schemas); existing P1 closure evidence
consistency PASS without executing closure. Exact two-document scope, 24 cited
production/model/port/migration/export source paths, unchanged historical detailed
registrations/reconciliation, historical roadmap and whitespace checks PASS.
Existing session integrity/semantic, actor and assertion trust tests were reviewed
as precedents, not executed or claimed as native association/race proof. No Maven,
database, provider, race or closure execution; no runtime/schema/API/export, version,
release or deployment change. Policy design Completed; exact-head Docs acceptance
PENDING. Next is separate B1 storage prerequisite recovery/registration only after
successful policy Docs and owner Next; no automatic advance.


## 2026-10-12 — Register HPR-P25-045 B1 binding and credential-security storage preflight

### Accepted baseline and one-stage registration

Owner Next selects 044's next eligible additive B1 storage prerequisite recovery and
registration only. Main `8a2c692825f0466441ae23d49ecc24324de89775`, tree
`e5f0dd5b2696812f4f0c21874d2d5793bfd55813`; exact-head
[Docs #255](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/38263783300)
SUCCESS. Latest Production #659 remains SUCCESS on accepted 042. The 044 provenance
policy is accepted as design: dedicated immutable issuer/jti-to-session association,
server-trusted completion, LOCAL credential security version and provider/link security
invalidation. It has not implemented any runtime storage, issuance, reader or revocation.
B2/B3 and publication remain OPEN; no general authentication/API rewrite is inferred.

Read AGENTS.md, PROJECT_STATE.md, current roadmap/reconciliation, accepted 043/044,
source owners/migrations/tests, dictionary ownership and supporting architecture/coding
references. HPR-P25-045 is absent from earlier active/history/reconciliation registrations.
This recovery establishes a bounded storage-design task because exact DDL, native-writer
coverage, legacy upgrade handling and Java compatibility are still unresolved.

Current registration exact message:
`docs(identity): register operation binding storage preflight`.
Exclusive UPDATE scope: `doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md`; CREATE/DELETE none. No future task is executed.

### Recovered storage and writer evidence

| Exact current source | Observed storage/writer boundary |
|---|---|
| `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java` | Seven-component record has credential identity/status/hash and timestamps, no generation; changing constructor affects bootstrap/adapter/tests |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LocalCredentialJpaEntity.java` | Existing table has varchar(80) identity/User, unique User and varchar(255) hash; no database security version |
| `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LocalCredentialRepositoryPort.java` | Save and exact ID/User reads; no version capture/CAS contract |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLocalCredentialRepositoryAdapter.java` | Ordinary repository.save with fresh mapped entity, no security invalidation/version return contract |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/LocalCredentialJpaRepository.java` | Inherited JpaRepository mutation/delete paths exist; no complete writer enforcement |
| `src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAdministratorBootstrapApplicationService.java` | Creates credential for new User; existing complete administrator is retained, incomplete existing state rejected; does not reset/rotate existing credentials |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/LocalAuthenticationProvider.java` | Reads current credential and verifies hash; no credential-generation proof transported to completion |
| `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java` and `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAccessTokenIssuer.java` | Token issued before session; server UUID jti exists, no binding persisted; storage stage must not activate issuance/reader accidentally |
| `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java` and `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java` | Logical session state/protocol/endedAt, no token binding; legacy expiry/provider/link may be nullable |
| `src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java` and `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLoginSessionRepositoryAdapter.java` | Lifecycle save/copy/touch/end and ordinary JPA save; new database invalidation must not be overwritten or resurrected by stale saves |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaIdentityProviderRepositoryAdapter.java` and `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/IdentityProviderJpaRepository.java` | Provider save validation plus inherited CRUD; security-event generation/terminalization not established |
| `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/ExternalIdentityJpaRepository.java` | Exact provider/subject lookup plus inherited CRUD; no invalidation writer protocol; absence of a dedicated writer service is not proof that SQL cannot change links |
| `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql` | Credential table unique User and FK; old checksum must remain unchanged; no version/reuse prevention |
| `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql` | Provider/link/session storage, external subject text and legacy nullable session references; new binding FKs cannot be assumed to repair historical rows |
| `src/main/resources/db/migration/V20261004_010__hmr_010_identity_identity_provider.sql` | Current provider uniqueness/status/issuer constraints; preserve LOCAL/directory selection and active OIDC issuer rules |
| `src/main/resources/db/migration/V20261006_012__hmr_087_login_session_evidence.sql` | Explicit legacy protocol preflight and independent endedAt; do not guess token provenance or overwrite terminal history |
| `.github/database-dictionary-ownership.json`, `.github/scripts/generate_data_dictionary.py` and `doc/database/DATA_DICTIONARY.md` | Forward storage changes require owner metadata and reproducible generated dictionary in a later registered implementation |

Read-only source/test precedents:
`src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java`,
`src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionIntegrityMigrationTest.java`,
`src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/AuthorizationAssertionTrustTest.java`
and `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/TopologyCurrentActorQueryAdapterTest.java`.
They do not prove binding/version/native-writer/race behavior. Repository-wide Java/SQL
search found bootstrap as the explicit production credential-save caller and the generic
adapter/repository paths; no dedicated password-rotation service was found. Administrative
SQL, deployed writers, cascades, TRUNCATE and privileged trigger bypass are not claimed
absent or tested. Future policy must state the supported writer/privilege boundary.

### Registered next executable task — HPR-P25-045

Exact design-task message:
`docs(identity): define operation binding storage policy`.
State: PLANNED / REGISTERED / NOT EXECUTED. Passing this registration's exact-head Docs
and separate owner Next select only the solo documentation storage preflight. Recheck
then-current main and latest CI; repair failure first.

| Action | Exclusive exact path |
|---|---|
| UPDATE | `doc/roadmap/ULTIMATE_ROADMAP.md` |
| UPDATE | `doc/model-remediation/RECONCILIATION.md` |

CREATE/DELETE none. Production/test Java, SQL/Flyway, issuer/completion/lifecycle,
provider/credential writers, public exports, security configuration, APIs/OpenAPI,
dictionary/generator/ownership, dependency/build/CI, gap register, version/release,
deployment and closure remain read-only. No migration version, table name or Java
filename is reserved by this registration. Proposed implementation paths must be
separately registered with exact HPR/message/allowlists before mutation.

### Required source-backed storage design and acceptance

1. **Binding schema and immutable contract.** Produce an implementable field/type/
   nullability/length/uniqueness/FK/check/index and mutation-guard specification for
   044's one native token per newly completed session. Include issuer+jti uniqueness,
   unique session, exact provenance/provider-type/protocol, OIDC source deadline,
   LOCAL-only credential identity/version, whole-second time and relational agreement.
   Compare JDBC/JPA owner adapters and additive storage/port versus overloading session;
   select one consistent with immutable append/exact replay/lookup/error semantics.
   Preserve external subject case/text and legacy non-UUID owner IDs; resolve actual
   parser/storage limits without truncation. Do not create SQL or runtime code here.
2. **Credential security version and full mutation enforcement.** Select generation
   storage/initialization, non-resettable monotonicity, overflow handling, credential
   identity/User reassignment and deletion/recreation behavior. Compare database
   enforcement versus exhaustive cooperative writers; inherited/native DML requires
   explicit coverage. Define security-change classification for hash/status/identity/
   reset and metadata/rehash, stale JPA merge behavior and version readback after flush.
   No timestamp/hash equality fallback, stored credential hash in binding or caller-
   supplied generation. Capture-at-password-verification remains a later issuance gate.
3. **Provider/link invalidation and session preservation.** Specify disable/reenable,
   type/issuer changes, unlink/relink/reassignment/deletion and associated active-session
   invalidation/generation behavior. Bound creation races, end-state preservation,
   stale lifecycle writes, native DML and delete restrictions/retention. If separate
   storage prerequisite is needed, split it; do not hide provider/link writer changes
   in a token-binding migration or claim existing annotations solve races. Preserve
   044's no-resurrection requirement and LDAP/AD protocol distinction.
4. **Fresh/upgrade/replay and compatibility.** Inventory exact current constraints,
   legacy nullable/corrupt/orphan data, deletes/cascades and all affected constructors/
   mappers/bootstrap/test fixtures. Choose additive forward migration sequencing,
   preflight failures and rollback/retention behavior; immutable existing checksums.
   Initialize new security state for future logins without pretending old tokens had
   provenance; old sessions stay unbound. Assess rolling old/new application writers,
   default generation/JPA-readback and fail-closed rollout prerequisites. Do not make
   real customer data or live credentials prerequisites for synthetic development.
5. **Synthetic storage proof specification.** PostgreSQL 16 real fresh/upgrade schema,
   Flyway validate/checksums and dictionary coverage; invalid foreign/mismatched tuple,
   duplicate issuer+jti/session, exact/differing replay, mutation/delete/truncate guard,
   version manipulation/overflow and credential delete/recreate, provider/link disable-
   reenable, terminal-state stale merge, transaction rollback and deterministic
   independent-connection races. Two synthetic Users/providers/links/sessions and
   companies/facilities. Use bounded barriers and observed SQL/locks, no sleeps or mock
   uniqueness proof. Privileged administrative bypass must be stated as an operational
   limit. No tests in this specification are claimed executed in the design stage.
6. **Bounded implementation decomposition.** Recover exact owners and propose the
   smallest separate storage implementation slices, migration/dictionary/test areas
   and compatibility gates. Distinguish additive inert binding storage, credential
   generation and provider/link invalidation if their writer/schema obligations differ.
   A later registration assigns exact files/message/HPR and tests; proposal alone is
   not execution authorization. Block concretely if schema/writer coverage cannot be
   justified; no insecure fallback. Completion/issuance/reader, B2 time proof, B3 owner
   fences/race/performance and publication remain separate, unregistered runtime work.

Policy acceptance is a reviewable storage contract and prerequisite disposition, not
implemented schema/revocation or a commissioning gate. Validate documentation-validator
10, OpenAPI-validator 10 and dictionary-generator 17 unit tests, canonical docs/offline
OpenAPI, existing P1 closure evidence consistency, exact source/history/scope/whitespace.
No Maven/database/provider/race or closure execution in this documentation stage.

| Task | Current disposition | Next boundary |
|---|---|---|
| HPR-P25-044 | Completed design — Docs #255 SUCCESS | Accepted provenance policy; runtime not established |
| HPR-P25-045 registration | Completed in source; exact-head Docs PENDING | Publish exact two docs; confirm startup and STOP |
| HPR-P25-045 storage design | Planned / REGISTERED / NOT EXECUTED | Successful registration Docs and separate owner Next select only this storage preflight |
| B1 storage/issuance/reader; B2/B3 and publication | OPEN / NOT IMPLEMENTED / no runtime allowlist | Separate source-backed registration and acceptance |
| F1–F4/P2.5; simulator/P3 | OPEN; simulation/P3 DEFERRED | Preserve gas-first/GZ2, own engine, steady/transient, versioned inputs and operator control |

Synthetic development and owner-supplied deployment calibration retained. Version,
release, deployment and closure unchanged. One guarded direct-main registration commit;
verify exact parent/tree/two paths and exact-head Docs startup, then STOP without waiting
for completion or executing storage design/implementation.


HPR-P25-045 registration publication validation: documentation-validator 10,
OpenAPI-validator 10 and dictionary-generator 17 unit tests PASS; canonical docs
PASS (98 documents, 5,306 relative links, 24 modules, 13 P2 rows); offline OpenAPI
PASS (263 operations, 244 paths, 231 schemas); existing P1 closure evidence
consistency PASS without executing closure. Exact two-document scope, 27 cited
source/test/dictionary paths, absent earlier 045 registration, unchanged detailed
registrations/reconciliation and historical roadmap, and whitespace checks PASS.
No Java/Maven/database/provider/race execution, storage/token/session mutation,
version/release/deployment or closure change. Registration acceptance remains
exact-head Docs PENDING; 045 design and subsequent runtime work are not executed.
