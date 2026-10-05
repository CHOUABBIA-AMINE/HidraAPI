# HidraAPI PostgreSQL HA / Connection Recovery

## Status

**IMPLEMENTED CONFIGURATION / PRODUCTION-EQUIVALENT EXERCISE PENDING — HPR-P1-016**

The approved database HA stack is PostgreSQL + Patroni + a three-member etcd quorum + HAProxy stable write endpoint.

This repository provides renderable templates and an exercise harness. It does not claim these hosts are already provisioned.

## Stable write endpoint

HAProxy exposes one application-facing PostgreSQL endpoint. Backend health uses Patroni's `/primary` endpoint, so only the Patroni member currently holding primary/leader authority is eligible for write traffic.

`HIDRA_DATASOURCE_URL` must point to this HAProxy endpoint, never directly to a PostgreSQL node.

## Patroni / etcd

`patroni/patroni.yml.tpl` defines:

- Patroni DCS through three etcd members;
- TLS-protected Patroni and etcd connectivity;
- PostgreSQL streaming replication;
- data checksums;
- replication slots;
- `pg_rewind`;
- explicit synchronous-mode placeholders, because the approved policy remains synchronous local replication where measured latency permits;
- failover-lag and WAL sizing placeholders that must be set from the approved production capacity/latency design.

`etcd/etcd.env.tpl` defines a three-member TLS-authenticated etcd cluster template.

Exact product versions and host values must be pinned during provisioning and retained as HPR-P1-012 evidence.

## Hikari/JDBC connection recovery baseline

Production makes these values explicit and externally tunable:

- connection timeout: 30,000 ms;
- validation timeout: 5,000 ms;
- idle timeout: 600,000 ms;
- max lifetime: 1,800,000 ms;
- keepalive time: 120,000 ms;
- maximum pool size: 30 by current default;
- minimum idle: 10 by current default.

These values are an engineering connection-lifecycle baseline, not an SLO or database-capacity claim.

Hikari does not perform database leader election. After a role change, dead/invalid pooled connections may fail and are replaced with new connections through the unchanged HAProxy endpoint.

## Interrupted transactions

A PostgreSQL failover can terminate TCP sessions and transactions.

HidraAPI does not globally or blindly retry writes whose outcome is uncertain. The caller/application layer remains responsible for retry only where the operation has explicit idempotency/transaction semantics.

A failed in-flight transaction is not considered successful merely because the connection pool later recovers.

## Static validation

Run:

```bash
ops/production/postgres/scripts/validate-postgres-ha-artifacts.sh
```

CI executes the same validation.

## Controlled role-change exercise

The provided `verify-postgres-failover.sh` uses `patronictl switchover` against an approved healthy production-equivalent cluster. It records Patroni topology, verifies exactly one primary, exercises a reversible temporary-table write through the stable HAProxy endpoint, performs a controlled role change, verifies the candidate becomes primary, reopens a write connection through the unchanged endpoint, confirms the former primary is no longer primary, and waits for HidraAPI readiness recovery.

The script deliberately refuses to execute unless `HIDRA_DB_DESTRUCTIVE_EXERCISE=YES`.

This repository task does not run the destructive exercise because no authorized production-equivalent Patroni/etcd/PostgreSQL hosts are connected to this execution environment.

## Completion state

Repository implementation is complete, but measured database failover evidence remains pending.

HPR-P1-016 therefore remains **IMPLEMENTED-PENDING-EXERCISE** until the controlled role-change exercise is executed on the approved production-equivalent environment and its evidence is retained for HPR-P1-012.
