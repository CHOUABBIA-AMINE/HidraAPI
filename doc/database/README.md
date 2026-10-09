# HidraAPI Canonical Database Documentation

## Status

CURRENT — reviewed source-chain dictionary and governance; HPR-P2-006 COMPLETED after #134/#608. P2 closure gates pending; P3 DEFERRED.

## Verified capture and applicability

Capture source: `8b51b52b2aa31f7a2f0ca2b08a6066387663d092`, tree `7df0095e2ad73bbb41b143cf8b8906abd7f15bf0`, 2026-10-09.
[Documentation #133](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/37927031188) and
[Production #607](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/37927031269) passed at that exact source.

Reviewed artifact: `hidra-database-schema-8b51b52b2aa31f7a2f0ca2b08a6066387663d092`, ID 11614189981;
[artifact](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/37927031269/artifacts/11614189981).
Archive SHA-256: `1ba744af02d7bcdd6ffd72c5ff66dc231dbda9d40c03bf51441ca0b2ae8cde72`.
Members: schema.json, DATA_DICTIONARY.md, verification.json. Downloaded archive hash matches GitHub; draft regeneration matched byte-for-byte.
Catalog document SHA-256: `ba01997dbdb999d7bd93db4a2358b94fc3ac91d1df0fc301dc3bc365962bc0cb`.
Source bundle SHA-256: `86f6ba5923e63c0cd601f7722ca0282e486d59fc92bb120616710fb3cd1799d4`.
Generator format 1; Python standard library; psql client 16.15 (Ubuntu); server 16.15 (Debian).

The full 139-migration chain completed Flyway and Hibernate validation in disposable CI PostgreSQL 16 before read-only collection and before base-revision startup. This is source-chain evidence for that engine, not production deployment, production data acceptance or PostgreSQL-18 catalog verification.

| Verified scope | Count |
|---|---:|
| Physical tables | 481 |
| PostgreSQL sequences | 1 |
| All catalog relations | 482 |
| Catalog columns, including sequence metadata columns | 5,840 |
| JPA table mappings across 24 modules | 470 |
| Java column mappings, including two aliases | 5,781 |
| Unique mapped physical columns | 5,779 |
| Constraints / indexes / non-internal triggers | 1,355 / 3,030 / 155 |
| Foreign keys | 688 |
| Unresolved relation owners after source review | 0 |

## Canonical set

- [Database architecture](DATABASE_ARCHITECTURE.md): current application/database and P1 HA/DR architecture.
- [Schema ownership](SCHEMA_OWNERSHIP.md): physical naming, exceptions, technical and non-JPA relations, reference governance.
- [Flyway policy](FLYWAY_POLICY.md): version allocation, immutable history and safe schema/data evolution.
- [Physical dictionary](DATA_DICTIONARY.md): every captured relation and ordered column, actual SQL type/nullability/default, mapping, constraint/index/trigger/function evidence and migration mentions.

The dictionary is generated exactly; editorial provenance belongs here and in ownership policy. Migration mentions do not claim initial introduction. [Ownership metadata](../../.github/database-dictionary-ownership.json) contains 21 source-linked overrides; other owners derive from current JPA modules. It contains no policy rows, credentials or invented approvals.

## Reproduce and check

From repository root, extract the reviewed artifact into a disposable directory. Verify archive digest and exact source identity first:

```bash
python3 .github/scripts/generate_data_dictionary.py \
  --input /path/to/schema.json \
  --source-sha 8b51b52b2aa31f7a2f0ca2b08a6066387663d092 \
  --ownership .github/database-dictionary-ownership.json \
  --render /tmp/HidraAPI-DATA_DICTIONARY.md \
  --check doc/database/DATA_DICTIONARY.md
```

The input source digest must equal current migration/JPA source. A documentation-only publication has its own Git SHA; it does not replace the capture SHA. For executable or migration changes obtain a fresh full-chain CI capture, review ownership and regenerate deliberately before committing.

CI uses `--collect target/database/schema.json --source-sha "${{ github.sha }}" --render target/database/DATA_DICTIONARY.md` with the same ownership/check arguments. Collection permits only the designated local disposable Actions database and performs no migration, reset or business-row export. Ownership metadata activates final comparison. Only the validated capture SHA line is ignored; physical facts, source bundle, owners and remaining provenance must match. Partial extraction, invalid history, unsupported mappings, unresolved owners or dictionary drift fail; CI never updates Git.

## Historical generation and operating evidence

Original HPR-P2-006 generation `aeb9008d74b90f102ab8706b9a23f1a6eb6cbe9c` recorded 82 migrations/469 entities. Source inventory refresh `00c4fda266b2dfd175cca37ad789dc9462a5af0b` recorded 139/470, but did not establish the physical dictionary now supplied. Both remain historical provenance in Git.

P1 deployed/recovery evidence retains its original deployed SHA and 82-migration scope, including achieved RPO 15 seconds/RTO 37 minutes. It does not prove deployment or recovery of the current chain. PostgreSQL streaming replication/Patroni/etcd/HAProxy and pgBackRest operating procedures remain authoritative within their measured scope.
[Historical HA](POSTGRES_HIGH_AVAILABILITY.md), [runbook](DATABASE_OPERATIONS_RUNBOOK.md), [failover](POSTGRES_FAILOVER_IMPLEMENTATION.md) and [backup](BACKUP_PITR_IMPLEMENTATION.md) stages remain preserved; current [operations](../../ops/production/postgres/DATABASE_OPERATIONS.md) and closed P1 evidence control contradictions.

Flyway defines schema evolution; JPA validates mappings. TimescaleDB/PostGIS remain NOT IMPLEMENTED / DEFERRED. No schema, data, deployment or version change is made by this publication. Stage B Production #608 exercised the final dictionary comparison successfully; HPR-P2-006 is COMPLETED. Renewed HPR-P2-013 audit verified all twelve checks; closure-head CI remains pending.

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

## Phase 2.5 network revision candidate — capture pending

The registered network delivery adds a separate Topology-owned JDBC revision store via [V20261009_001](../../src/main/resources/db/migration/V20261009_001__p25_topology_physical_network_revisions.sql). Ownership metadata now contains 22 source-linked overrides. Historical counts and captures above remain their original verified scope; they do not include this new migration.

The current physical dictionary has deliberately not been hand-edited. Publication requires an actual full-chain migrated PostgreSQL 16 catalog with the candidate migration/source inventory, reviewed ownership and regeneration/check using the existing generator. The new PostgreSQL integration test captures the generator's schema-only catalog query in a read-only repeatable-read transaction after actual full-chain Flyway migration/validation. On GitHub Actions it emits compressed catalog evidence with exact checkout/source-bundle/dictionary digests for retrieval when local PostgreSQL collection is unavailable. It exports no business rows or credentials. This equivalent disposable-catalog route does not weaken the maintained CI dictionary comparison. Capture results/counts and applicability will be recorded only after actual evidence is obtained.
