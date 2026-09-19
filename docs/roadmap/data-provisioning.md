# HidraAPI Legacy Data Migration and Database Provisioning Roadmap

## 1. Document control

| Field | Value |
|---|---|
| Product | Hidra — Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Repository | `CHOUABBIA-AMINE/HidraAPI` |
| Upstream source | `CHOUABBIA-AMINE/HyFloAPI` |
| Roadmap | `docs/roadmap/data-provisioning.md` |
| Roadmap code | `HDP` |
| Created | 2026-09-19 |
| Baseline at drafting | `51fb62bd7b33e46c539daeedf80a59591308e7b4` (reconfirm before each task) |
| Status | Proposed; roadmap PR under review; no data imported by this roadmap |
| Execution mode | Exactly one HDP task/commit at a time; never execute later tasks automatically |

This is a **proposed** execution plan, not evidence that any dataset has been transformed, imported, approved, or tested. HDP-001 is the roadmap authoring task; its repository status stays **In Progress** until the roadmap PR is merged. All other tasks start **Planned**.

## 2. Purpose and explicit outcome

Inventory legacy HyFlo datasets; trace their provenance and resolve contradictory versions; map them field-by-field to current HidraAPI domain and persistence contracts; clean/normalize geographical and industrial reference data; generate deterministic, auditable loading artifacts; and verify them against a disposable PostgreSQL database using current Flyway migrations and approved application/domain boundaries.

Primary source locations:
- `HyFloAPI/src/main/resources/data/**`
- `HyFloAPI/src/main/resources/extras/**`

Key discovery examples (not an exhaustive data inventory): `Algerian Infrastructure Database.xlsx`, `Data Network.xlsx`, `Segment.xlsx`, `Segment 2026.xlsx`, `Locations.xlsx`, `Locations 1-11-2026.xlsx`, `hyflo_db.sql`, `data_only.sql`, `iaas_db.sql`, `Fiche Passation.xlsx`, `Plan 2026 BSR.xlsx`, `hyflo-progress.xlsx`, `LPL/`, `algeria_pipeline_coordinates_with_locations.csv`, `geocode_pipelines.py`.

The `data_only.sql` header identifies a **MariaDB** dump; legacy SQL is input data, never executable HidraAPI PostgreSQL schema. The coordinate CSV has `FID,Point_Number,Longitude,Latitude,Location,Commune_Name,Commune_Code,Wilaya_Name,Wilaya_Code`. File names do not establish content truth or effective dates; in particular, do not interpret `1-11-2026` without checking workbook metadata and business provenance.

## 3. Authorities, exclusions, and module boundaries

Read `AGENTS.md`, this roadmap, and the applicable module roadmap before each task. Current Java/package/header/validation conventions prevail. Use `dz.sh.hidra`; do not create `shared`, `sharedkernel`, `common`, `core`, `utils`, `helper`, `helpers`, or `misc`. Preserve `@Author : Abir MEDJERAB` and `@CreatedOn : 2025-06-26` in any Java files, with the repository's exact canonical header format.

The authoritative **target** is current HidraAPI domain models, JPA mappings, Flyway SQL, and module APIs/ports, not legacy HyFlo entities or workbook column names. Consult `docs/architecture/module-catalog.md` and `docs/architecture/persistence-schema.md`; the latter is only an index, so inspect actual SQL. Existing date-based migrations, including `V20260611_001__create_identity_tables.sql` through `V20260611_024__create_reporting_tables.sql` and later applied migrations, must not be renamed/replayed destructively.

Likely candidate owners to **verify** by source inspection:
- `topology`: `PipelineSystem`, `Pipeline`, `PipelineSegment`, `TopologyNode`, `TopologyConnection`, `Facility`, `Equipment`, `MeasurementLocation`.
- `organization`: `AdministrativeState`, `AdministrativeDistrict`, `AdministrativeLocality`, `OrganizationUnit` (an operational organizational station is **not** automatically a physical `Facility`).
- `telemetry`: `TelemetrySource`, `TelemetryPoint`, `TelemetryDevice`, `TelemetryReading`.
- `planning`: `PlanningPeriod`, `OperationalPlan`, `PlanRevision`, `Nomination`, `PlanTarget`.
- `integration`: inspect existing `ExternalObjectReference`, `IntegrationFieldMapping`, `IntegrationMappingProfile`, `IntegrationInboundRecord`, `IntegrationReconciliationRun` before adding any duplicate import machinery.

A module owns its own persistence writes. Cross-context references use stable IDs and approved contracts; no direct foreign repository writes or cross-module aggregate graphs. No authentication changes, microservices/multi-module restructuring, speculative model refactors, irreversible changes to applied Flyway migrations, bulk production writes, or auto-approval of operational records are in scope. New target-model features require a separate approved module roadmap.

Do not commit source secrets, personal employee data, production credentials, restricted facility details, or unreviewed large raw industrial datasets. Classify handling and repository distribution before publication; use sanitized samples and external secured storage for restricted inputs. Never execute the legacy `geocode_pipelines.py` or SQL dumps without source/security review.

## 4. Input classes and decision policy

Classify each file/worksheet/table before import as: (a) stable reference/catalog, (b) physical network/topology master, (c) organizational/geographical reference, (d) operational time series or business transaction, (e) historical archive, (f) derived/reporting output, (g) unknown or out of scope. Do not import operational or derived material as authoritative reference data simply because it appears in a workbook. Mark duplicates, superseded snapshots, ambiguous effective dates, missing lineage, and confidential content. Conflicting values require documented source-of-truth precedence or an explicit unresolved decision; never choose newest-looking filenames automatically.

Industrial data must retain units, accuracy/precision, time zone and timestamp semantics, source effective period, material/commodity meaning where relevant, and distinctions between designed/rated vs observed values. Preserve coordinate order and CRS; verify whether CSV Longitude/Latitude are WGS84 before geospatial use; reject invalid/out-of-country or implausible points for review rather than invent coordinates. Verify Wilaya/Commune names/codes against an approved edition and record administrative reorganization/effective dates. No inferred pipelines, links, stations, hydraulic properties, locations, or SONATRACH operational facts.

## 5. Task protocol and execution gates

Each HDP code is an **independent commit** with the exact message below, only explicitly authorized files, corresponding roadmap status update, validation evidence, and clean working tree. If an implementation task's exact file allowlist cannot be stated from the approved mappings, first amend this roadmap through a separately approved documentation task; no task's broad directory label grants permission to change arbitrary files. In the table, `docs/data-provisioning/` is a **proposed** documentation directory, not a claim it already exists. Scope for code/import artifacts must be expanded to exact filenames before execution. A task remains blocked if required source evidence, target schema, or approval is absent.

Gate G1 (after HDP-006): source/target inventories complete, confidentiality and legal handling approved, and every dataset has disposition/owner.
Gate G2 (after HDP-010): field-level mappings, authority decisions, technical rules, source-to-target IDs, and target-path allowlists reviewed; ambiguous rows quarantined.
Gate G3 (after HDP-018): deterministic staged transformations and reference/master loads succeed on disposable PostgreSQL with no foreign-key/uniqueness violations, silent rejects, or unauthorized writes.
Gate G4 (after HDP-022): final reconciliation, regression, security, review, and deployment/rollback requirements evidenced before any production use.

## 6. Phase A — Discovery, inventory, and governance

| Code | Exact commit message | Deliverable, acceptance, and scope |
|---|---|---|
| HDP-001 | `docs(data-provisioning): add legacy data migration roadmap` | This roadmap only: `docs/roadmap/data-provisioning.md`. PR review/merge gate; does **not** import data. |
| HDP-002 | `docs(data-provisioning): inventory hyflo source files and provenance` | `docs/data-provisioning/source-inventory.md`: complete recursive manifest of both source directories (including `LPL/`), repo/ref/blob SHA, format, size, workbook tabs or SQL table names, row counts where obtainable, candidate date, owner, sensitivity, provenance, checksum. Flag missing/unreadable/binary/untrusted files; record real extraction methods. Never publish sensitive row values. |
| HDP-003 | `docs(data-provisioning): inventory hidra target models and schema` | `docs/data-provisioning/target-inventory.md`: actual domain model names, writable application contracts, JPA entity/table/column types and constraints, FK graph, enums, Flyway versions, existing catalog/seed/import implementations. Verify current `main`; distinguish absent functionality from uninspected functionality. |
| HDP-004 | `docs(data-provisioning): classify source datasets and import eligibility` | `docs/data-provisioning/source-classification.md`: every input classified reference/master/operational/history/derived/unknown, proposed bounded-context owner, confidentiality, legal sharing limits, canonical/superseded versions, approval status and disposition (import/defer/reject). No unauthorized data copied to target repo. |
| HDP-005 | `docs(data-provisioning): map legacy fields to hidra contracts` | `docs/data-provisioning/source-target-mapping.md`: **per source column/table** target model/property/DB field, type, nullable/enum/range, transform, units, source precedence, target owner, FK, identifier strategy, permitted loading contract, and explicit `UNMAPPED` disposition. Mapping is reviewed against code/SQL; do not invent missing fields. |
| HDP-006 | `docs(data-provisioning): approve canonical sources and migration scope` | `docs/data-provisioning/migration-decisions.md`: resolved canonical snapshot/effective dates, duplicate/conflict policy, authoritative geographical reference version, sensitive-data disposition, excluded operational datasets, accountable reviewers, staged coverage, and G1 signoff or explicit blockers. |

## 7. Phase B — Normalization and import design

| Code | Exact commit message | Deliverable, acceptance, and scope |
|---|---|---|
| HDP-007 | `docs(data-provisioning): define algerian geographic normalization rules` | `docs/data-provisioning/geography-rules.md`: approved Wilaya/Commune code/name references and effective edition; diacritics/aliases without erasing canonical names; coordinate order/CRS, point vs polyline meaning, spatial tolerances, missing-coordinate and ambiguous-locality quarantine. Review samples against source metadata; no guessed geocodes. |
| HDP-008 | `docs(data-provisioning): define pipeline and engineering normalization rules` | `docs/data-provisioning/engineering-rules.md`: units and conversions with dimensional tests, commodity/equipment controlled vocabularies, null vs zero, segment ordering/direction, design-vs-observed attributes, date/time precision, pressure/flow/length/diameter validation and non-inference rules. |
| HDP-009 | `docs(data-provisioning): define identifier and dependency mapping` | `docs/data-provisioning/identifier-dependencies.md`: legacy source-qualified key → stable Hidra ID approach, composite keys/collisions, external-reference reuse, duplicate resolution, FK load DAG and cross-context boundaries, snapshot versioning and safe retries. Preserve lineage; no positional/spreadsheet-row-number IDs unless a verified stable business key. |
| HDP-010 | `docs(data-provisioning): approve reproducible provisioning design` | `docs/data-provisioning/import-design.md`: decide existing integration ports vs governed module-specific bootstrap; separate source extraction, validation, quarantine, transformation and write stages; explicit transaction boundaries, dry run, idempotency, upsert policy, audit/reconciliation, rollback and no production-write default. Confirm exact code/data/test file allowlists and deployment approvals for HDP-011 onward by an approved roadmap amendment if necessary. G2 signoff. |

## 8. Phase C — Reproducible transformation and controlled loading

All following implementation tasks are **conditional**: exact target filenames, owners and scopes must be approved in HDP-010 and recorded here before execution; they are not permission to refactor modules or create infrastructure speculatively.

| Code | Exact commit message | Deliverable and acceptance |
|---|---|---|
| HDP-011 | `test(data-provisioning): add sanitized source fixtures and mapping contracts` | Minimal non-sensitive, provenance-linked test fixtures and contract tests for supported source formats, nulls, date/number locales, duplicate keys, bad geocodes, malformed relations, conversion edge cases and excluded/sensitive rows; no use of confidential production datasets in CI. |
| HDP-012 | `feat(data-provisioning): add deterministic extraction and quarantine` | Approved loader extracts chosen source snapshots as **data**, never executes legacy SQL or scripts; emits typed staging records, origin and source digest; produces machine-readable rejects with reason and original source reference, no silent drop. |
| HDP-013 | `feat(data-provisioning): add geographic and reference transformations` | Approved Wilaya/Commune and stable catalog mappings, source-qualified IDs, alias dictionaries, coordinate/CRS validation and reversible mapping artifacts; tests demonstrate no fabricated or cross-wilaya values. |
| HDP-014 | `feat(data-provisioning): add pipeline and facility transformations` | Validated `PipelineSystem`/`Pipeline`/`PipelineSegment`/`Facility`/`TopologyNode`/`TopologyConnection` candidates using actual target contracts; units, directed relations, geometry, distinct physical/organizational entities and unresolvable FKs fail closed. |
| HDP-015 | `feat(data-provisioning): add dry-run and idempotent import orchestration` | Use existing applicable application/integration ports if sufficient; otherwise implement only independently approved module-owned adapters. Dry-run by default, validated dependency-order transactions, existing-record policy, source snapshot marker, same-source rerun neutrality and safe failure/rollback. Do not mutate another context's tables. |
| HDP-016 | `db(data-provisioning): provision approved geographic and reference datasets` | Load approved static geographical and controlled reference values through the HDP-015 mechanism against current Flyway schema; validate known code/name/ID mappings, row counts and FK constraints. Any Flyway seed artifact requires **new** approved filename after inspecting current versions; never replace an applied migration. |
| HDP-017 | `db(data-provisioning): provision approved physical network master data` | Load pipeline systems, pipelines, segments, physical facilities, nodes/connections and approved related catalogs in dependency order; assert endpoints, ownership, uniqueness and complete legacy-ID reconciliation. No inferred physical connectivity. |
| HDP-018 | `test(data-provisioning): verify master-data imports and replay safety` | Fresh PostgreSQL + Flyway + Testcontainers integration tests, full mapped-source row reconciliation, exact duplicate/replay comparison, transactional rollback test, FK and attribute validations, and G3 review. No production DB used. |

## 9. Phase D — Optional operational data, verification, and release

| Code | Exact commit message | Deliverable and acceptance |
|---|---|---|
| HDP-019 | `docs(data-provisioning): disposition operational and historical sources` | `docs/data-provisioning/operational-disposition.md`: specifically evaluate `Fiche Passation`, `Plan 2026 BSR`, `hyflo-progress`, historical SQL and telemetry for true event time, approval status, provenance, permission/privacy, retention and their correct owners. Decide separately import/defer/archive. **Do not import them during reference-data tasks.** Any authorized operational load needs a separate module-specific roadmap and tests. |
| HDP-020 | `test(data-provisioning): add end-to-end reconciliation and security gates` | Automated per-source and per-target counts, mapped/rejected/deferred totals, FK/orphan report, topology consistency, accuracy spot checks, forbidden-secret and sensitive-data scans, ArchUnit and approved staging-to-target correctness tests. Every difference has a tracked explanation. |
| HDP-021 | `docs(data-provisioning): document import operations and rollback` | `docs/data-provisioning/runbook.md`: pinned source SHAs/checksums, required roles/secrets, nonproduction dry-run, ordered execution, environment gates, backup/restore, repeatability, schema-version compatibility, failure/rollback, and signed exception workflow. Never present deletion of production data as an automatic rollback. |
| HDP-022 | `docs(data-provisioning): finalize data migration acceptance` | `docs/data-provisioning/acceptance.md` and this roadmap's execution status: source-to-target coverage, decisions, reproducible test evidence, accepted quarantine, security review, complete clean-tree and CI results, explicit release/no-release decision. G4 complete before production deployment; production execution itself is separately authorized. |

## 10. Validation by task class

For document-only tasks: verify links/paths, current repository SHA, references to actual source/target artifacts, status and decision provenance; do not claim Maven tests ran if not executed. For transformer and import code: execute scope-appropriate unit/contract tests plus `mvn -q -DskipTests compile`, `mvn -q test`, `mvn -q clean verify` on a complete clone/CI, and Testcontainers PostgreSQL/Flyway integration tests where specified. Record exact commands, environment, SHA, results, skips, and any Docker blockers. Do not weaken architectural, security, or data-quality tests to achieve a green build.

Core reconciliation invariant for each canonical source snapshot: `eligible source records = created + matched/unchanged + explicitly rejected + explicitly deferred`; document duplicates and merges separately to prevent misleading counts. Require no unexplained rejects; no unauthorized writes; no missing target FKs; no duplicate natural/source-qualified keys; no silently fabricated coordinates/engineering facts; rerun produces no additional duplicate records.

## 11. Initial execution status

| Code | Status | Evidence / blocker |
|---|---|---|
| HDP-001 | In Progress | Roadmap proposed on a review branch; do not mark Completed until PR merged. |
| HDP-002–HDP-010 | Planned | Read-only inventories and approved mapping/decision gates required. |
| HDP-011–HDP-018 | Planned | G2 mapping approval and exact file allowlist required before code changes. |
| HDP-019–HDP-022 | Planned | G3 evidence, disposition/acceptance reviews and release controls required. |

**Immediate next task after roadmap merge: HDP-002 — inventory HyFlo source files and provenance.** Do not begin HDP-003 or implementation work while HDP-002 is active.
