# HidraAPI Flyway Policy

## Status

CURRENT — canonical HPR-P2-006 schema-migration policy.

## Current Configuration

Repository configuration establishes:

```text
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=false
spring.flyway.validate-on-migrate=true
spring.flyway.clean-disabled=true
spring.jpa.hibernate.ddl-auto=validate
```

Production retains validation and clean-disabled behavior.

## Migration Authority

Flyway is the only approved application-schema evolution authority.

Rules:

1. applied versioned migrations are immutable;
2. do not edit an applied file to force a checksum match;
3. do not falsify Flyway history;
4. do not disable validation to force startup;
5. do not use Flyway clean in production;
6. new schema changes use new forward migrations;
7. schema migration executes against the authoritative writable primary;
8. PostgreSQL standbys receive schema changes through replication rather than independent Flyway execution;
9. deployment must prevent uncontrolled concurrent migration attempts from multiple application nodes;
10. JPA validation must succeed after migration.

## Current Chain

Migration directory: `src/main/resources/db/migration`

Migration count: **139**

First migration: `V20260611_001__create_identity_tables.sql`

Current tail: `V20261008_026__hmr_080_planning_nomination_integrity.sql`

Historical closed P1 recovery evidence reconciled its own 82-migration restored executable tree; it does not prove deployment/recovery of the current 139-migration source chain.

## Migration Failure

On migration failure, preserve Flyway/error/history evidence and determine whether partial state exists. Prefer a reviewed forward corrective migration where safe.

Exceptional Flyway history repair requires explicit Database Operations + Application Engineering review and retained justification.

## Naming and version allocation

Current files use `V<version>__<description>.sql`, for example `V20261008_026__hmr_080_planning_nomination_integrity.sql`. Underscore-separated numeric components normalize to Flyway dotted versions (`20261008.026`); ordering is numeric, not lexical. Reserve the next unique version in repository coordination before preparing a migration; scan the entire current chain and parallel branches for collisions, recheck after rebasing, and never reuse a normalized version even with a different filename. No sequence value is allocated by this documentation change.

The current chain has 139 unique versions, from `V20260611_001__create_identity_tables.sql` through the tail above. CI retains ordered script/version/checksum/success history and source hashes. Startup Flyway validation checks applied-file consistency; the collector preserves actual Flyway checksums, not a substitute home-grown checksum algorithm.

## Additive, destructive and data-change preflight

Every new migration requires a bounded roadmap authorization, owning module, exact file/version, expected schema/data effect, affected contracts and validation evidence. Run the complete ordered chain and Hibernate validation on disposable PostgreSQL; include meaningful negative/history/concurrency cases where constraints or orchestration change. Refresh the dictionary from actual catalogs after migration.

Prefer additive evolution: introduce compatible structure, reconcile actual historical data with reviewed mappings, verify readers/writers and then separately authorize retirement. Assess NOT NULL/default/index/FK/check additions for existing data, scan/lock duration, rewrite/storage needs and deployment compatibility. If using NOT VALID during an approved transition, record the later validation step; an unvalidated constraint must never be silently described as enforced historical integrity.

Destructive or irreversible changes (drop/rename/type narrowing, record deletion, semantic rewrite) require explicit owner and Database Operations review of retained history, data inventory, dependency impact, lock/downtime budget, tested recovery and reconciliation. Establish a recoverable backup/WAL position and a restore verification plan from the [operations procedures](../../ops/production/postgres/DATABASE_OPERATIONS.md) and [PITR exercise](../../ops/production/postgres/scripts/verify-pitr-restore.sh). Existing P1 RPO/RTO measurements have their own scope; they are not a fresh restore rehearsal for a new migration.

Data migrations must use approved source-to-owner mappings, classification/policy values and provenance. Define preconditions, affected-row bounds, duplicate/orphan checks, concurrency control and postconditions; fail closed on ambiguous historical records. Do not seed invented business approvals or resolve a failed migration by guessed defaults. HMR-080 illustrates separately provisioned approved metadata before guarded integrity changes.

Record reversal feasibility before execution. Application rollback does not reverse an applied migration. Prefer forward corrective evolution compatible with restored application behavior; if reversal requires restoring the database, document recovery point, possible data loss, application compatibility and controlled acceptance. PostgreSQL transactionality is not proof every operation is safely reversible.

## History, failure and exceptional repair

Retain the original error, migration script/hash, actual Flyway history and database state before intervention; inspect partial effects and stop uncontrolled retries. [History capture](../../ops/production/postgres/scripts/capture-flyway-history.sh) and controlled operating procedures supply evidence. Never edit applied migrations, delete/falsify history, disable validate-on-migrate, enable baseline-on-migrate to hide mismatch or run production clean.

Exceptional repair requires explicit Database Operations and Application Engineering review, semantic-owner involvement for data effects, a documented root cause and approved before/after history/checksum evidence. Repair metadata only after reconciling actual schema/data state and rerunning validation; repair is not schema rollback or authorization to accept changed SQL. No repair command or production execution is performed here.

## Dictionary regeneration and CI drift gate

Use the exact [regeneration/check command](README.md#reproduce-and-check) and reviewed ownership metadata. Fresh migrated PostgreSQL catalogs control physical facts; source parsing supplies mappings and fails on unsupported forms. Current schema collection precedes base-revision startup. Ownership metadata activates `--check doc/database/DATA_DICTIONARY.md` in full CI; comparison ignores only the validated capture SHA line. Source bundle, columns/types/defaults/nullability, ordered keys/FKs/actions, constraint flags, indexes, triggers/functions and ownership drift must be reviewed and committed deliberately. CI never auto-updates Git.

Markdown CI stays database-free. Full production CI must pass at the publication SHA and exercise the integrated check before HPR-P2-006 completes. The new metadata path triggers full CI under the existing workflow; no workflow change is required. Future documentation-only changes still require dispatch when a fresh physical comparison is necessary.

Historical generation at `aeb9008d74b90f102ab8706b9a23f1a6eb6cbe9c` (82/469), later source inventory refresh (139/470), and P1 deployed/recovery evidence retain their original applicability. Current physical capture/provenance is recorded in the [index](README.md); no production deployment or data approval follows from CI. P2 OPEN/P3 DEFERRED.

## Renewed HPR-P2-013 audit and closure gate — 2026-10-09

The read-only P2 audit at `7be1c9cb47ed9b6f73a7692d0a328ad870e2b4d4`
returned PASS: all twelve checks VERIFIED. Preflight Documentation #135 passed at
that SHA. HPR-P2-005 correction is COMPLETED after #131; HPR-P2-006 is COMPLETED
at `1bc3c1bba2d08a0b493e5ece43e834e8d56d04f0` after Documentation #134 and
Production #608 passed, including fresh physical dictionary comparison with zero
unresolved owners. These later facts supersede earlier publication-pending summaries;
dated historical records and their original applicability remain preserved.

This closure metadata implementation records the audit and verified prerequisites.
HPR-P2-013 is IN PROGRESS; P2 remains OPEN pending both documentation and full
production CI on the resulting closure commit. The exact current disposition and
retained twelve-check evidence follow [the roadmap](../roadmap/ULTIMATE_ROADMAP.md).
Docs-only push does not start full production CI: the existing HidraAPI CI manual
workflow must run on the exact closure head. No preceding CI result substitutes for
that gate. Stop after startup observation; no P3 task is selected.

All 24 module slices, 123 semantic subjects and 57 completed HMR identities remain.
Source-derived schema facts retain disposable PostgreSQL-16 capture applicability;
P1 deployed/recovery evidence retains its original scope. No production-data/import
approval, business retention/policy values, hydraulic/ML runtime execution or field
actuation is established. Version remains 0.6.0-SNAPSHOT. No executable, migration,
contract snapshot, dictionary, ownership metadata or operating artifact is changed.
