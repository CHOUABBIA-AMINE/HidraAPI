# PostgreSQL Failover Implementation

## Status

**HISTORICAL — HPR-P1-016 implementation-stage baseline; retained for P1 stage provenance.**

Current database authority is the HPR-P2-006 set indexed by `doc/database/README.md`, together with executable configuration/migrations and the closed P1 evidence. Later P1 closure supersedes any pre-closure “pending”, “target”, “not selected” or “not established” statements below when they conflict with current evidence.

Execution base: `9053572efc804bcba44b14cd3aec4fa8d4900746`.

Selected stack:

- PostgreSQL streaming replication;
- Patroni;
- three-member etcd quorum;
- HAProxy stable PostgreSQL write endpoint;
- HidraAPI HikariCP through the stable endpoint.

## Single-writer enforcement

Patroni/etcd owns database role authority. HAProxy does not elect a primary; it routes writable application connections only to the PostgreSQL node whose Patroni REST `/primary` health check returns success.

The Patroni REST API requires mutual TLS. HAProxy therefore performs `/primary` checks over TLS on port 8008, validates the Patroni server certificate against the rendered CA and expected per-node certificate hostname, and presents an externally rendered client certificate/private-key PEM. `check-ssl` applies this TLS contract to the health check without changing ordinary PostgreSQL backend transport semantics.

The application must never connect directly to a node-specific PostgreSQL address.

## Promotion and fencing model

Patroni/etcd consensus owns promotion. A former primary must no longer hold leader/primary authority before HAProxy routes write traffic to a promoted node.

The production-equivalent exercise must verify exactly one primary both before and after the role change and must confirm the former primary is not still reported as leader/primary. The repository harness now also records Patroni dynamic configuration and cluster-member authority context before and after the switchover.

That evidence demonstrates the selected Patroni/etcd control-plane authority used to prevent concurrent promotion. It does **not** by itself prove behavior under network partition or DCS-loss conditions. HPR-P1-029 retains the requirement for production-equivalent fencing/partition evidence before split-brain protection is considered measured.

## Connection recovery

HidraAPI continues using one stable JDBC URL. Existing connections may break on role change. Hikari then replaces invalid/dead connections by creating new connections through the stable endpoint.

Explicit pool lifecycle settings are now configured in the production profile and externally tunable.

## Transaction responsibility

No transparent transaction survival is claimed.

No global automatic write retry is introduced.

If an in-flight write fails or its outcome is uncertain, retry is permitted only where the owning application use case defines safe idempotency/transaction semantics.

## Required measured evidence

HPR-P1-016 cannot be considered verified until an approved production-equivalent exercise records:

- exact Patroni/etcd/PostgreSQL/HAProxy versions;
- initial topology with exactly one writable primary and an eligible standby;
- controlled Patroni switchover/failover authority;
- candidate promotion;
- former-primary demotion/non-primary status;
- stable HAProxy endpoint unchanged;
- new writable connection through that endpoint;
- application readiness recovery;
- representative application read/write acceptance;
- elapsed recovery timing;
- residual failures or interrupted requests/transactions.

The repository includes the executable harness under `ops/production/postgres/scripts/verify-postgres-failover.sh`.

Production readiness remains **NOT ESTABLISHED** until measured evidence exists.
