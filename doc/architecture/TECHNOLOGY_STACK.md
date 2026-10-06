# HidraAPI Technology Stack

## Status

CURRENT application and P1 infrastructure stack, with TARGET/DEFERRED technologies separated below.

## Application Build and Runtime

| Technology | Current evidence |
|---|---|
| Java | Java 21; Maven compiler release 21. |
| Maven | Build tool; enforcer requires Maven 3.9+. |
| Spring Boot | 4.1.1 parent. |
| Spring MVC | REST/web application stack. |
| WebSocket/STOMP | Spring WebSocket; current configuration uses the in-process simple broker. |
| Bean Validation | Spring Boot validation starter. |
| Persistence | Spring Data JPA / Hibernate with PostgreSQL JDBC. |
| Schema migration | Flyway with PostgreSQL support; validate-on-migrate enabled and clean disabled. |
| Security | Spring Security, OAuth2 Resource Server JWT, LDAP client infrastructure; OIDC-related configuration is externalized. |
| Observability | Actuator + Micrometer Prometheus registry. |
| Cache | Spring cache abstraction with `spring.cache.type=simple`; process-local and non-authoritative. |
| OpenAPI | springdoc 3.0.3. |
| Mapping | MapStruct 1.6.3. |
| Code generation | Lombok provided at compile time subject to repository coding rules. |
| Architecture testing | ArchUnit 1.4.2. |
| Integration testing | Testcontainers 1.21.4 with PostgreSQL support. |

PostgreSQL and other transitive/library versions not explicitly pinned in `pom.xml` must not be invented from this table.

## Current Production Configuration Properties

The production profile requires external datasource credentials, uses the named `HidraProductionHikariPool`, validates JPA schema, preserves Flyway validation/clean-disabled behavior, exposes controlled Actuator metrics, and keeps OpenAPI/Swagger disabled by default unless explicitly enabled.

Sensitive values remain external; this document records configuration surfaces, not values.

## P1 Production Infrastructure

Current repository implementation under `ops/production/**` contains:

| Area | Current P1 technology |
|---|---|
| Host/service runtime | Linux virtual machines + systemd |
| Application load distribution | HAProxy |
| Database | PostgreSQL streaming replication |
| Database HA control | Patroni + etcd |
| Stable database endpoint | HAProxy |
| Backup/WAL/PITR | pgBackRest |
| Secret-management boundary | HashiCorp Vault |
| Metrics | Prometheus |
| Alert routing | Alertmanager |
| Dashboards | Grafana |
| Centralized logs | Grafana Loki |
| Release operations | repository-owned exact-artifact deploy/install/rollback plus node drain/rejoin scripts |

The completed P1 production-equivalent evidence retains operator-supplied deployed versions for Java, PostgreSQL, Patroni, etcd, HAProxy, pgBackRest, Prometheus, Alertmanager, Grafana and Loki. Those evidence versions describe the P1 exercise environment; they are not silently treated as dependency-manager pins for future deployments.

## Current P1 Runtime Constraints

- REST/API is multi-node and verified under P1.
- STOMP realtime remains single-active and uses the Spring simple broker.
- Spring simple cache is process-local and cannot hold correctness-critical shared state.
- local asynchronous notification execution is not a distributed scheduler.
- PostgreSQL remains the authoritative persistence technology.

## Target / Deferred Technologies

The following are not current stack claims:

- TimescaleDB — DEFERRED / not implemented.
- PostGIS — DEFERRED / not implemented.
- Redis or another distributed cache — not selected by P1.
- shared/clustered realtime broker — not implemented by P1.
- Kubernetes/OpenShift — not selected for P1.
- specific SCADA/historian/industrial messaging protocols — not established by HPR-P2-002.
