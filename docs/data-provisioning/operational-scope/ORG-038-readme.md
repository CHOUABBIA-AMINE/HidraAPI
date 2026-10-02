# ORG-038 — Review-only scope-registry database draft

**Status:** DRAFT / NOT APPROVED AS A MIGRATION. ORG-037 remains blocked until you supply the approved database audit, owner-confirmed scope crosswalk and consumer inventory. The proposed registry architecture in ADR-0006 is not yet on `main` at the checked baseline; do not silently deploy this schema before accepting it. This ZIP is a design/testing aid, not a completed ORG-038 task.

## File and purpose

`ORG-038-registry-schema-DRAFT.sql` is placed in `docs/data-provisioning/operational-scope/`, **not** in `src/main/resources/db/migration/`. It defines a generated `BIGINT` registry ID and a unique `(operational_scope_type, target_id)` pair, permits exactly one no-target GLOBAL scope, rejects unregistered CUSTOM, and introduces a **nullable** `scope_id` FK on existing responsibility assignments without dropping any existing field or converting existing string IDs. Existing `scope_id` values will be NULL until a separately approved verified backfill.

The draft ends in `ROLLBACK` to prevent an accidental persistent schema change when tested as a standalone script. Its foreign key proves only that a registry row exists, not that its referenced pipeline/facility/equipment exists or is assignable; target-owner validation and reconciliation remain required. No scope records are inserted. There is no live-data import or user-role permission grant.

## Your local review / prerequisite checks

1. Confirm ADR-0006 (central registry design) is accepted in your local repository. Do not take ADR-0005's old direct type/ID assignment as simultaneously canonical.
2. Run the preceding ORG-037 read-only audit on an approved database copy; privately review actual legacy tuples, duplicates, GLOBAL/CUSTOM cases and owner-validated target IDs. Do not share raw operational identifiers in a public commit.
3. Check that `hidra_org_operational_scope`, `scope_id` and all constraint/index names do not already exist, and recheck the current Flyway sequence and schema search path at your installation. No migration version is selected in this draft.
4. Review the `VARCHAR(120)` target ID length against authoritative owner ID formats. Define explicit `CUSTOM` namespace rules before allowing it.
5. Optionally run this draft as a single script **only on an approved disposable PostgreSQL database with the existing organization tables**; it ends in ROLLBACK. Do not run during a production deployment.
6. After database owner approval, produce one separately numbered real Flyway migration without `BEGIN`/`ROLLBACK` and verify it with PostgreSQL integration tests. Backfill, `scope_id NOT NULL`, and old-field retirement are distinct later migrations, after sign-off.

**Proposed commit message (review-only documentation):** `docs(organization): draft additive operational scope registry schema`

**Validation in this package:** archive structural checks only; no `psql`, PostgreSQL migration, Maven or CI was run against HidraAPI.
