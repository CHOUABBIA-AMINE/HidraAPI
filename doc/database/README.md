# HidraAPI Canonical Database Documentation

## Status

CURRENT — reviewed source-chain dictionary and governance; HPR-P2-006 publication gates pending. P2 OPEN; P3 DEFERRED.

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

Flyway defines schema evolution; JPA validates mappings. TimescaleDB/PostGIS remain NOT IMPLEMENTED / DEFERRED. No schema, data, deployment or version change is made by this publication. Fresh Stage B production CI must exercise the final dictionary comparison before HPR-P2-006 completes; HPR-P2-013 separately verifies all twelve checks.
