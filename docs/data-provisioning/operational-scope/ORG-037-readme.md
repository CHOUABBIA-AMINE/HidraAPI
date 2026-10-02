# ORG-037 — Read-only operational scope migration evidence pack

**Status:** Assessment aid only; ORG-037 remains **Blocked** until a database owner runs the script against an approved copy and confirms the results with scope owners. Not a migration, backfill, verification of foreign targets or permission to remove columns.

## Applicability

Designed for the tables defined in `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`. Verify actual schema/table names and the role's read-only permissions before running. It does not create the new `OperationalScope` registry, does not assume scope registry IDs already exist, and does not convert organization UUID-string IDs to Long.

## Run locally against an approved, access-controlled PostgreSQL copy

```bash
psql --set=ON_ERROR_STOP=1 --dbname='<approved database connection>' \
  --file=docs/data-provisioning/operational-scope/ORG-037-legacy-scope-audit.sql
```

Use a read-only role. Do not commit credentials, raw row-level identifiers or unredacted database output to GitHub. This script only emits aggregate counts. For very large assignment tables, the overlap self-join can be costly; DBAs should review its execution plan before running it on a large dataset.

## Review checklist

- [ ] Obtain authorized read-only SQL access to the correct migration version or a representative anonymized export; record environment, snapshot date and source owner privately.
- [ ] Run the five SELECT assessments; securely record aggregate counts by table/type. A value of zero in a **source-shape** category is not evidence that referenced objects exist.
- [ ] Independently verify every candidate `(type, targetId)` using that target owner's approved public contract and its native ID format. Record counts for orphan IDs, mismatched type, retired/ineligible targets and stale codes/names; the SQL script **cannot** measure these without authoritative owner data.
- [ ] Investigate `GLOBAL` rows with target IDs and unregistered `CUSTOM`; do not construct synthetic target IDs. The proposed new registry's GLOBAL row uses a generated registry ID but has a NULL owner target ID.
- [ ] Separate intentional multiple responsibilities from true duplicates. Distinct roles or distinct targets are allowed; inspect overlapping same-role ACTIVE pairs without disclosing private assignee IDs.
- [ ] Check non-organization consumers including HidraWEB, reporting, APIs, provisioning and external integrations; sign off compatibility and phased cutover.
- [ ] Approve a private legacy-to-registry crosswalk only for verified targets. Quarantine unmatched/ambiguous rows; preserve assignment history and audit provenance.
- [ ] Inventory all String organization IDs and foreign references separately before proposing a Long conversion; never cast existing UUID strings to BIGINT.

**Completion gate:** Published aggregate assessment + private verified target crosswalk + owner and consumer sign-off + safe backfill/rollback plan. No ORG-038 schema changes, field removals or IDs conversion before this gate is approved.

**Proposed commit message (documentation assessment only):** `test(organization): prepare read-only operational scope migration audit`
