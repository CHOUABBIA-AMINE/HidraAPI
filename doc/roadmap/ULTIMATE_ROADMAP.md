# HidraAPI — Ultimate Universal Platform Roadmap

> **Authoritative active execution document.** Complete CURRENT task table first, detailed REMAINING task descriptions second. Full completed-task actions, earlier exact task messages, historical tables, preflight records and CI evidence are preserved **verbatim** in [history/ULTIMATE_ROADMAP_HISTORY.md](history/ULTIMATE_ROADMAP_HISTORY.md). Live code, migrations, tests, `AGENTS.md` and exact-head CI outrank stale historical claims.

## 1. Consolidated task table — statuses and dependencies

**Product policy:** Hidra is universal, configurable and company/network/product independent. No real customer data, P&IDs, vendor curves or live SCADA are prerequisites for generic development. PLATFORM software testing uses explicit synthetic data; customer SITE commissioning is an entirely separate gate. Numerical simulation work is deferred.

**Code discipline:** `HPR-P25-037` is an already **registered** execution code. Codes `F1-02` etc. are **planning references only** and MUST NOT be executed or treated as approved HPR codes before registration, exact commit message and exact create/update/delete file allowlists. Dependencies are minimum known gates, not blanket permission.

| Order | Code / planning reference | Task | Current status | Dependencies / gate | Track |
|---|---|---|---|---|---|
| 1 | HPR-P25-037 | Immutable station-graph PostgreSQL persistence | REGISTERED — awaiting CI acceptance; implementation not selected | HPR-P25-032/034/035/036; registration Docs CI; owner Next | F1 |
| 2 | F1-02 | Station revision history / transactional publication policy | PLANNED — needs HPR registration | HPR-P25-037 implemented/verified; owner/actor/CAS policy | F1 |
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

## 2. Remaining-task execution rules

1. The table above governs selection, never a historical `NEXT` note. Only registered exact HPR tasks may change code; check prerequisites, required full CI, migration authorization, exclusive file allowlist and commit message in their detailed registration.
2. At each step recheck live `main` SHA, `AGENTS.md`, `PROJECT_STATE.md`, reconciliation, production code, migrations, tests and CI. Commit guarded by expected-head and STOP after one registered code.
3. Do not alter accepted historic records or claim documentation-only design as executable functionality. All original records are in the historical snapshot, including originally pending entries.
4. Generic PLATFORM tests are synthetic and can establish only software behavior; SITE acceptance needs real operator-specific provenance and competent approval and is not implied by full CI.
5. Proposed files for unregistered tasks are **candidate ownership areas**, not exact permitted mutations; names should not be fabricated prematurely. Deletions: **none authorized** unless a future registered task expressly lists them.

## 3. Detailed descriptions of remaining tasks

### HPR-P25-037 — Immutable station-graph PostgreSQL append and exact read

**State:** REGISTERED / PLANNED — future implementation is not selected. Registration baseline is `91e74c80c9ad327150174ddada08de058e5de7f7`; do not infer CI acceptance without checking its actual completed run.

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

## 4. Full authoritative HPR-P25-037 registration — preserved verbatim

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


## 5. Governance and history

- [Full original task registry, completed actions, evidence and superseded proposals](history/ULTIMATE_ROADMAP_HISTORY.md) (unaltered original roadmap snapshot, `91e74c80c9ad327150174ddada08de058e5de7f7`).
- [PLATFORM acceptance matrix](PLATFORM_ACCEPTANCE.md): synthetic verification; no SITE certification.
- [Reconciliation history](../model-remediation/RECONCILIATION.md): independent source-backed records.
- [Agent governance](../../AGENTS.md): canonical task, commit, scope and header rules.

**Current execution selection:** Registered HPR-P25-037 implementation only after its acceptance prerequisites and separate owner Next. No code, migration, release, deployment or simulator implementation is approved by this documentation-only restructure.
