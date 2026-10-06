# HidraAPI System Context

## Status

CURRENT architecture with explicitly separated TARGET/DEFERRED items.

## Current System Boundary

HidraAPI is the backend application boundary for Hidra. Current repository evidence exposes HTTP/REST APIs, WebSocket/STOMP and SSE platform capabilities, security/authentication integration, PostgreSQL persistence, operational health/metrics surfaces and 24 internal business-module boundaries.

```text
API / browser / operator clients
             |
             v
        HAProxy (P1)
             |
       +-----+-----+
       |           |
       v           v
 HidraAPI node A  HidraAPI node B
       |           |
       +-----+-----+
             |
             v
  stable PostgreSQL endpoint
          (HAProxy)
             |
             v
 PostgreSQL HA (Patroni + etcd)
             |
             +--> pgBackRest backup/WAL/PITR

HidraAPI --> optional/configured OIDC or LDAP capabilities
HidraAPI --> Actuator/Prometheus metrics --> Prometheus/Alertmanager/Grafana
HidraAPI logs ---------------------------> Loki
Runtime secret material ----------------> Vault-backed deployment boundary
```

P1 evidence establishes the multi-node REST and PostgreSQL survivability baseline. The diagram is logical: it does not invent hostnames, network zones, provider identities or secret values.

## Current External Actors and Systems

| Boundary | Current evidence |
|---|---|
| API/browser/operator client | REST/API and realtime entry surfaces exist in the application. |
| Authentication/identity systems | JWT resource-server support exists; OIDC and LDAP integration capabilities are externally configured. Exact production provider identity is not asserted here. |
| PostgreSQL | Authoritative relational persistence; Flyway owns schema migration. |
| HAProxy | P1 application traffic distribution and stable PostgreSQL application endpoint. |
| Patroni + etcd | P1 PostgreSQL HA coordination/single-writer control. |
| pgBackRest | P1 backup, WAL archive and PITR implementation. |
| Vault | Selected P1 production secret-management boundary. |
| Prometheus / Alertmanager / Grafana / Loki | P1 metrics, alerting/dashboard and centralized logging stack. |

## Current Internal Context

The application is a modular monolith. The current source contains 24 business module roots under `dz.sh.hidra.modules`, while `kernel` and `platform` provide non-business primitives and technical infrastructure respectively.

Cross-module collaboration is not unrestricted. Deliberate exported application contracts are enumerated in `CROSS_MODULE_CONTRACTS.md` and enforced by `ArchitectureGuardrailTest`.

## Target / Deferred Context

The following are not current implementation claims:

- TimescaleDB and PostGIS remain deferred.
- a shared/clustered realtime broker is not implemented for P1; STOMP remains single-active.
- no Redis/distributed cache is selected; Spring simple cache remains process-local and non-authoritative.
- `agents`, `environment` and `otsecurity` are architecture capabilities discussed in legacy material but are not current source module roots.
- specific SCADA/historian protocols or OT network topology are not established by HPR-P2-002.
