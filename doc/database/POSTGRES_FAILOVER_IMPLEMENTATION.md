# PostgreSQL Failover Implementation

## Status

**IMPLEMENTED-PENDING-EXERCISE — HPR-P1-016**

Execution base: `9053572efc804bcba44b14cd3aec4fa8d4900746`.

Selected stack:

- PostgreSQL streaming replication;
- Patroni;
- three-member etcd quorum;
- HAProxy stable PostgreSQL write endpoint;
- HidraAPI HikariCP through the stable endpoint.

## Single-writer enforcement

Patroni/etcd owns database role authority. HAProxy does not elect a primary; it routes writable application connections only to the PostgreSQL node whose Patroni REST `/primary` health check returns success.

The application must never connect directly to a node-specific PostgreSQL address.

## Promotion and fencing model

Patroni/etcd consensus owns promotion. A former primary must no longer hold leader/primary authority before HAProxy routes write traffic to a promoted node.

The production-equivalent exercise must verify exactly one primary both before and after the role change and must confirm the former primary is not still reported as leader/primary.

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
