# HidraAPI / HyFlo — Project State Handoff

**Captured:** 2026-09-22. **Release state refreshed:** 2026-10-09. **Canonical integration branch:** [main](https://github.com/CHOUABBIA-AMINE/HidraAPI/tree/main). **Current release-state baseline before this change:** [045f062f782b0f5240f255853db2e9025e69d631](https://github.com/CHOUABBIA-AMINE/HidraAPI/commit/045f062f782b0f5240f255853db2e9025e69d631). Older workstream sections remain dated handoff evidence unless explicitly refreshed below. This file is a non-secret handoff, not an assertion about future PR/branch status, live deployments or an authorization to migrate data.

**Current release/development state — 2026-10-09:** last formal release is
[v0.6.0 / HidraAPI v0.6.0](https://github.com/CHOUABBIA-AMINE/HidraAPI/releases/tag/v0.6.0),
GitHub Release ID 407972853, published by owner at 2026-10-09T14:02:18Z.
GitHub reports latest, non-draft and non-prerelease. The lightweight/unsigned
v0.6.0 tag directly resolves to release-alignment commit
`09bf1cbf82c20f19c50ebb1ff9e27047c7f34856`, tree
`d46f669bda1bf74e2fe35ede3d57aa554d0c1919`; its POM is 0.6.0. Documentation #138
(run 37936829457) and full HidraAPI CI #610 (run 37936829442) PASSED on that
exact release SHA. No signed annotated tag or production deployment is claimed.

HPR-REL-001 is COMPLETED. The supporting readiness record
`045f062f782b0f5240f255853db2e9025e69d631` passed Documentation #139
(run 37938799294); it is not the release tag target. The semantic milestone
anchor remains P2 closure `a8905e32289a583f47b831e0381783e556ae0c8d`, verified by
Documentation #136 and production #609. P0/P1/P2 remain CLOSED within documented
evidence scope; 57 HMR completions, 123 permanent subjects and P3 DEFERRED remain.

**Current development line:** `0.7.0-SNAPSHOT`, selected by the owner under
HPR-REL-002 / `chore(release): start 0.7.0 development` after formal release
existence was verified. Only the POM project version changes; dependencies,
features, API metadata v1 and schema remain unchanged. The development transition
commit is the commit carrying this record and POM 0.7.0-SNAPSHOT; pin its real
SHA after publication during exact-head verification. HPR-REL-002 is IN PROGRESS
pending both documentation and full HidraAPI CI on the transition SHA. Release
CI #610 is not verification of the new development commit.

Next selected stage: verify those transition CI gates after startup and owner
Next/failure notification. No P3, new release, deployment or import is selected.
Production promotion requires its own approved change and controlled release
workflow. No new physical exercise, data acceptance or owner-policy approval is
inferred from release publication or the development-version transition.

This current record supersedes the dated release/development-state claims in
section 0 below. Older workstream deliverables remain dated handoff evidence;
current execution is governed by the
[Ultimate Roadmap](doc/roadmap/ULTIMATE_ROADMAP.md).

Before resuming, read [AGENTS.md](AGENTS.md), the relevant module/workstream roadmap, [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md), [coding policy](docs/policy/Coding-policy.md), and [ADR index](docs/adr/README.md). Recheck current main, PR and CI state rather than assuming this snapshot remains current.


## 0. Historical release state — refreshed 2026-09-29

- **Last formal release:** `0.5.0`.
- **Git tag / GitHub Release:** `v0.5.0` / `HidraAPI v0.5.0`.
- **Release commit:** `492d9916369a58e60c1a437647411e7c5a523990`
  (`chore(release): establish semantic version baseline`).
- **Release verification:** HidraAPI CI #436 / run `36571148141` completed successfully on the
  exact release commit; the deterministic OpenAPI artifact
  `hidra-api-openapi-492d9916369a58e60c1a437647411e7c5a523990` was published.
- **Milestone traceability anchor for 0.5.0:** `4cadc132ce4cbb9d2aa075eaaf4f3dfeb69cf2dc`
  (`docs(organization): record ORG-033 closure`). This is a semantic milestone anchor, **not** the
  release tag target, because its historical POM still contained `0.2.0`.
- **Tag form:** the GitHub-Web-created `v0.5.0` ref resolves directly to the release commit and is
  therefore a lightweight/unsigned tag; do not describe it as GPG/SSH signed.
- **Current development line:** `0.6.0-SNAPSHOT`.
- **Current development commit:** `63f3f60974ce57eb8cd5e42910397615195624fb`
  (`Start version 0.6.0-SNAPSHOT`).
- **Current development verification:** HidraAPI CI #437 / run `36573899231` completed
  successfully.
- **Authoritative release history:** [VERSIONS.md](VERSIONS.md).
- **Future release procedure:** governed by `AGENTS.md` section **Release and Versioning Rules**;
  never derive a new release solely from the current POM version or from a single task commit.

## 1. Core architecture, stack and repository layout

- **Product:** Hidra — Hydrocarbon Intelligence for Data, Risk, and Analytics. HidraAPI is the backend foundation; legacy [HyFloAPI](https://github.com/CHOUABBIA-AMINE/HyFloAPI) is an input to controlled data provisioning, not the target backend architecture.
- **Build/runtime:** Java 21, Maven, Spring Boot 4.x (README baseline 4.0.7), Spring MVC/Security, Spring Data JPA/Hibernate, PostgreSQL, Flyway, springdoc/OpenAPI; JUnit, Testcontainers and ArchUnit for verification. Verify exact dependencies/versions in pom.xml.
- **Architecture:** DDD and hexagonal/ports-and-adapters **modular monolith**, not deployed microservices. A NAPEC presentation's proposed microservices/autonomous-operation claims must not be mistaken for implemented system capabilities.
- **Canonical Java root:** src/main/java/dz/sh/hidra. kernel holds framework-neutral primitives; platform owns technical plumbing; modules contains 24 bounded business contexts. Within a module, domain/model and domain/value own business meaning, application owns use cases/ports, api owns REST contracts, infrastructure owns JPA/external adapters. API controllers may not call infrastructure repositories directly; cross-module references require approved IDs, ports or events.
- **Persistence:** Flyway migration SQL is under src/main/resources/db/migration. PostgreSQL is the target DBMS. Target authority comes from actual current Java domain models, JPA mappings, migrations, and module API/application contracts **together**; a Java String is not by itself a PostgreSQL column specification. Historical MariaDB/HyFlo SQL and source spreadsheets are never executable HidraAPI schema.
- **Decision records:** [modular monolith](docs/adr/0001-modular-monolith-hexagonal.md), [PostgreSQL/Flyway](docs/adr/0002-postgresql-flyway.md), [API and ports](docs/adr/0003-api-module-boundaries.md), [scoped legacy-data precedence](docs/adr/0004-legacy-data-precedence.md). These record previously established decisions and do not override the source code or policy.

## 2. Verified milestones and their limits

The presence of 24 module roots, domain classes, or a migration file **does not prove that every business capability is deployed or operational**. The [HDP-003 target inventory](docs/data-provisioning/target-inventory.md) documents the inspected model/persistence inventory (25 Flyway files at its baseline). The [authentication roadmap](docs/roadmap/authentication.md) reports its AUTH-030 gap closure as Completed on 2026-09-15; consult its evidence before claiming a deployed identity solution.

**Data-provisioning roadmap:** [HDP-001 PR #118](https://github.com/CHOUABBIA-AMINE/HidraAPI/pull/118) established the roadmap; [HDP-002 PR #119](https://github.com/CHOUABBIA-AMINE/HidraAPI/pull/119) inventoried 31 tracked legacy source files, with status correction [PR #120](https://github.com/CHOUABBIA-AMINE/HidraAPI/pull/120); [HDP-003 PR #121](https://github.com/CHOUABBIA-AMINE/HidraAPI/pull/121) recorded target structure. Preliminary HDP-004 classification and extraction/owner-scope documentation were merged in [PR #122](https://github.com/CHOUABBIA-AMINE/HidraAPI/pull/122), [PR #123](https://github.com/CHOUABBIA-AMINE/HidraAPI/pull/123), and [PR #124](https://github.com/CHOUABBIA-AMINE/HidraAPI/pull/124).

[HyFloAPI source-structure audit PR #5](https://github.com/CHOUABBIA-AMINE/HyFloAPI/pull/5) merged and [Actions run #35577651574](https://github.com/CHOUABBIA-AMINE/HyFloAPI/actions/runs/35577651574) succeeded. Its metadata-only inventory covered 13 XLSX and eight SQL source files (20 structural inspections, one XLSX-read failure), **not** record meanings, approved imports or confidential-data clearance.

[HDP-004 issue #125](https://github.com/CHOUABBIA-AMINE/HidraAPI/issues/125) tracks source-semantic review. At capture, legacy-SQL-schema [PR #126](https://github.com/CHOUABBIA-AMINE/HidraAPI/pull/126) was **open/unmerged**. Temporary model-extraction [PR #127](https://github.com/CHOUABBIA-AMINE/HidraAPI/pull/127) was **closed without merge**. Neither PR's changes form part of the verified main baseline.

## 3. Current task and concrete blockers

**The immediate unfinished user deliverable is a downloadable Excel catalogue of all current HidraAPI domain models, grouped by module, with individual field names, original Java types and verifiable source paths.** The temporary [model-catalogue Actions run #35713401200](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/35713401200) succeeded and reported 467 module domain-model Java files, 5,735 declared fields and zero extraction exceptions; a compressed manifest was printed to its job log. The **Excel catalogue has not been generated/delivered**. Because the temporary PR is closed and unmerged, do not assume the extractor exists on main. Regenerate from the current main code or decode the verified run output in a trusted local tool, then validate the Excel before delivery. The Java-model inventory is documentation, not a database schema inventory.

**HDP-004 is Blocked/incomplete**, as recorded in [its roadmap](docs/roadmap/data-provisioning.md) and [source classification](docs/data-provisioning/source-classification.md). HDP-005 has **not** started, gate G1 is not signed, and no records are approved for PostgreSQL import. Prior discussion created private chat-local staging and compatibility workbooks, but they are **not committed to Git and may not carry into a new chat**. Never promise future sessions have access to those binary files unless they are supplied again.

Three legacy source workbooks were supplied privately: hyflo-progress 1-11-2026.xlsx (coded-table dictionary), HyFlo_db.xlsx (legacy database records), Segment 2026.xlsx (infrastructure/topology detail). The project owner explicitly stated authorization to use these datasets for this project, but that does **not** independently certify public disclosure/security approval or approve all records for import. The owner explicitly excluded Fiche Passation.xlsx; it does not require repair for this workstream. Legacy credential-labelled LPL material and legacy user/password/hash data must remain outside general provisioning; no credentials or restricted operational records in public Git/Actions logs.

**Provisional owner-stated reconciliation rules:** Segment 2026 takes precedence when the *same infrastructure record* overlaps with HyFlo_DB; first worksheet occurrence is provisionally retained if a segment code repeats *within* Segment 2026, preserving excluded-row provenance for later engineering review. HyFlo_DB-only records stay candidates, not automatically deleted. These rules do not determine the authoritative geography, telemetry or unrelated reference sources. Some coded-table names conflict with the meaning indicated by worksheet contents; resolve that before automatic entity mapping.

**Reference Data Package 01:** User checked the five staging **scope** categories: geographical hierarchy/structure types; facility/station/terminal types; hydrocarbon-field/equipment types; operational-status classifications; alloy classifications. Earlier private extraction reported 12 source families / 972 staged entries and generated chat-local assessment/staging workbooks, with **no DB writes**. These are not inherently import-eligible. The target compatibility review found: legacy locality records refer to state whereas HidraAPI AdministrativeLocality requires districtId; ID-only subtype memberships do not automatically supply target FacilityKind/EquipmentKind and lifecycle status; some other reference families lack verified direct target destinations. Never invent administrative districts, enum classifications, dynamic telemetry or missing operational data just to satisfy a schema.

## 4. Conventions and safety

For roadmap implementation, read AGENTS.md and the owning roadmap; execute exactly one roadmap task/commit code at a time, use its specified commit message and file allowlist, update its status, and validate with the requested commands. This explicitly requested **documentation-only handoff change is not an HDP task completion** and does not change roadmap status or production code.

Java package root: dz.sh.hidra; do not create generic shared/sharedkernel/common/core/utils/helper/helpers/misc packages. Preserve the canonical Java header, including @Author Abir MEDJERAB and @CreatedOn 2025-06-26. Domain code remains independent of Spring/JPA/OpenAPI/REST; module ownership applies to persistence writes. Production secrets must be externalized. Never claim tests, merges, source-data approvals, or imports occurred without evidence.

## 5. Immediate next step

**First:** finish and deliver the outstanding **HidraAPI model/field/type Excel catalogue** from the *current main* Java sources. Include a module index, one row per model field, declared Java type, model kind and source path/commit, and clearly distinguish model fields from PostgreSQL/JPA column types. Verify counts and readability of the file; do **not** merge the closed temporary PR #127 merely to create the workbook.

**Then, as a separate workstream task:** resume controlled HDP-004 mapping of private Reference Data Package 01 against the verified target catalogue. Investigate missing locality-to-district links and approved kind/status mapping; resolve source-code/dictionary discrepancies, confidentiality, version precedence, per-dataset eligibility and G1. Do not begin HDP-005, run legacy SQL as a target migration, or import unapproved operational data.

**Branch hygiene:** canonical integration branch is main, not an old PR branch. Recheck [current open PRs](https://github.com/CHOUABBIA-AMINE/HidraAPI/pulls), the latest main commit and CI before new work.
