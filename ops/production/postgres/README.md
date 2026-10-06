# HidraAPI PostgreSQL HA / Connection Recovery

## Status

**IMPLEMENTED CONFIGURATION / PRODUCTION-EQUIVALENT EXERCISE PENDING — HPR-P1-016 / HPR-P1-024**

The approved database HA stack is PostgreSQL + Patroni + a three-member etcd quorum + HAProxy stable write endpoint.

This repository provides renderable templates and an exercise harness. It does not claim these hosts are already provisioned.

## Stable write endpoint

HAProxy exposes one application-facing PostgreSQL endpoint. Backend health uses Patroni's `/primary` endpoint, so only the Patroni member currently holding primary/leader authority is eligible for write traffic.

Patroni REST health checks use mutual TLS. The HAProxy template applies TLS only to the health-check connection on port 8008 via `check-ssl`, validates the Patroni server certificate against the rendered CA, verifies the expected per-node certificate hostname, and presents the externally rendered HAProxy client certificate/private-key PEM required by Patroni `verify_client: required`. The certificate paths/identities are deployment inputs and no private key is committed to Git.

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

The failover exercise now observes this behavior through Spring Boot's authenticated Actuator Hikari metric `hikaricp.connections.creation`. The harness records the pre-switchover connection-creation count, then requires that count to increase after the Patroni role change while an authenticated database-backed HidraAPI request succeeds. This is application-managed pool replacement evidence, not a claim that an in-flight transaction survived.

The application acceptance endpoint is not hard-coded. Operators must supply `HIDRA_APP_DB_ACCEPTANCE_URL` as an approved harmless authenticated database-backed read path. Authentication for both the acceptance request and protected Actuator metric is supplied by an external `HIDRA_APP_CURL_CONFIG`; credentials are not committed or printed into exercise evidence.

## Interrupted transactions

A PostgreSQL failover can terminate TCP sessions and transactions.

HidraAPI does not globally or blindly retry writes whose outcome is uncertain. The caller/application layer remains responsible for retry only where the operation has explicit idempotency/transaction semantics.

A failed in-flight transaction is not considered successful merely because the connection pool later recovers.

## Static validation

Run:

```bash
ops/production/postgres/scripts/validate-postgres-ha-artifacts.sh
```

CI executes the same validation. It verifies the Patroni/etcd/HAProxy mTLS contract, explicit Hikari lifecycle settings, authenticated database-backed acceptance inputs, the Hikari connection-creation metric, recovery timing output, interrupted-transaction responsibility and shell syntax.

## Controlled role-change exercise

The provided `verify-postgres-failover.sh` uses `patronictl switchover` against an approved healthy production-equivalent cluster. It records Patroni topology plus `patronictl show-config` authority context before and after the role change, verifies exactly one primary, exercises a reversible temporary-table write through the stable HAProxy endpoint, performs a controlled role change, verifies the candidate becomes primary, reopens a direct database connection through the unchanged endpoint, and proves application-level recovery through two independent signals:

1. an authenticated database-backed HidraAPI acceptance request succeeds after the role change; and
2. `hikaricp.connections.creation` increases from the pre-switchover baseline, demonstrating that the application pool created replacement connections.

The recovery interval is measured from the start of the controlled switchover to the first observation that both application acceptance and Hikari replacement evidence are satisfied. This interval is exercise evidence only; it is not declared as an SLO.

That control-plane evidence documents the selected Patroni/etcd leader-authority mechanism, but a controlled switchover is not a network-partition/fencing exercise. HPR-P1-029 must still retain production-equivalent partition/fencing evidence before split-brain protection is considered measured.

The script deliberately refuses to execute unless `HIDRA_DB_DESTRUCTIVE_EXERCISE=YES`.

This repository task does not run the destructive exercise because no authorized production-equivalent Patroni/etcd/PostgreSQL hosts are connected to this execution environment.

## Completion state

Repository implementation now includes the HPR-P1-024 application-managed recovery assertions, but measured database failover evidence remains pending.

HPR-P1-016 remains **IMPLEMENTED-PENDING-EXERCISE** and HPR-P1-024 remains repository-complete but not measured until the controlled role-change exercise is executed on the approved production-equivalent environment and its evidence is retained under HPR-P1-029/HPR-P1-012.
