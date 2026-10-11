# HidraAPI — Ultimate Universal Platform Roadmap

> **Authoritative active execution document.** Complete CURRENT task table first, detailed REMAINING task descriptions second. Full completed-task actions, earlier exact task messages, historical tables, preflight records and CI evidence are preserved **verbatim** in [history/ULTIMATE_ROADMAP_HISTORY.md](history/ULTIMATE_ROADMAP_HISTORY.md). Live code, migrations, tests, `AGENTS.md` and exact-head CI outrank stale historical claims.

## 1. Control Status

| Field | Value |
|---|---|
| Status | ACTIVE |
| Purpose | Universal platform task register with detail and dependencies |

## 2. Consolidated task table — statuses and dependencies

**Product policy:** Hidra is universal, configurable and company/network/product independent. No real customer data, P&IDs, vendor curves or live SCADA are prerequisites for generic development. PLATFORM software testing uses explicit synthetic data; customer SITE commissioning is an entirely separate gate. Numerical simulation work is deferred.

**Code discipline:** `HPR-P25-037` is implemented and CI accepted; `HPR-P25-038` is the next **registered documentation-only preflight**. Codes `F1-02` etc. are **planning references only** and MUST NOT be executed or treated as approved HPR codes before registration, exact commit message and exact create/update/delete file allowlists. Dependencies are minimum known gates, not blanket permission.

| Order | Code / planning reference | Task | Current status | Dependencies / gate | Track |
|---|---|---|---|---|---|
| 1 | HPR-P25-037 | Immutable station-graph PostgreSQL persistence | COMPLETED — exact-head Production #654 and Docs #237 SUCCESS | Accepted at `0868f2d17ce0eaefc53902ce36fa711681cd406b` after bounded #653 repair | F1 |
| 2 | HPR-P25-038 / F1-02 preflight | Station revision publication/history policy and implementation decomposition | REGISTERED — preflight execution PLANNED; registration Docs CI pending | Accepted HPR-P25-037; passing registration Docs CI; separate owner Next | F1 |
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
