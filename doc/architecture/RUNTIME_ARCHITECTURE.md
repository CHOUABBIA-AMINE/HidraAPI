# HidraAPI Production Runtime Architecture

## Status

**CURRENT + APPROVED TARGET + DECISION REQUIRED**

This is the canonical HPR-P1-001 runtime-architecture baseline. It records only repository-verified runtime facts and previously approved operating decisions. It deliberately does not select a production deployment technology.

Verification base: `912b76396651c197d4db9ecccb21cd3de6a83e86`

P0 closure CI run #529 / run id `37326280500` succeeded on closure SHA `bada4bb882b634762756dd115c7d66c864bd0b3b`. The latest pre-task `main` SHA `912b76396651c197d4db9ecccb21cd3de6a83e86` also succeeded in CI run #530 / run id `37328378048`.

## 1. Classification

- **CURRENT — repository verified**: directly evidenced by current source, configuration, build, or CI.
- **APPROVED TARGET**: owner-approved operating requirements that production runtime must satisfy without implying a product.
- **NOT ESTABLISHED / DECISION REQUIRED**: not proven or not yet approved and therefore unavailable as a production-readiness claim.

## 2. Current Application Runtime

### 2.1 Process and technology

**CURRENT — repository verified**

HidraAPI is a Java 21 Spring Boot 4.1.1 executable application built with Maven. Current runtime dependencies establish Spring MVC, WebSocket/STOMP, Spring Data JPA/Hibernate, PostgreSQL JDBC, Flyway, Spring Security, OAuth2 resource-server JWT support, LDAP client infrastructure, Actuator, Micrometer Prometheus, Spring cache abstraction, and springdoc/OpenAPI.

The application name is `hidra-api`. The HTTP port is externalized through `SERVER_PORT` with repository default 8080. Graceful shutdown is enabled.

The repository provides `dev`, `test`, and `production` profiles. Production is expected to run with `SPRING_PROFILES_ACTIVE=production`.

### 2.2 Persistence

**CURRENT — repository verified**

PostgreSQL is the current authoritative persistence technology. Production datasource URL, username, and password are externalized through `HIDRA_DATASOURCE_URL`, `HIDRA_DATASOURCE_USERNAME`, and `HIDRA_DATASOURCE_PASSWORD`.

JPA uses `ddl-auto=validate`. Flyway is enabled, validates on migrate, and disables clean in production. The production Hikari pool is configurable; repository defaults are maximum 30 and minimum idle 10. These are implementation defaults, not approved capacity targets.

**NOT ESTABLISHED / DECISION REQUIRED**

The repository does not establish PostgreSQL replication/failover topology, replication mode, failover authority, connection routing during failover, maintenance failover behavior, backup frequency/retention, WAL archiving/PITR, or database RTO/RPO. These belong to HPR-P1-003 and HPR-P1-004.

### 2.3 HTTP state and node-local state

**CURRENT — repository verified**

Spring Security configures HTTP session creation as `STATELESS` for protected API chains. Hidra bearer JWT settings are externalized; external OIDC completion has a dedicated trust path; LDAP capability exists but production activation is not established.

Stateless HTTP authentication does not prove complete multi-node safety.

The common cache is `spring.cache.type=simple`, which is process-local. Realtime STOMP uses Spring's in-process simple broker. Notification async push uses an application-local executor. The platform outbox is disabled by common default and production enablement is not established.

**Architecture implication**

HPR-P1-002 must decide how multi-node operation handles load distribution, readiness/liveness, node-local cache consistency, WebSocket/STOMP connections and message distribution, async/background work, node failure, and rolling maintenance before application HA can be claimed.

## 3. Health and Observability

**CURRENT — repository verified**

Actuator exposes health, info, metrics, and Prometheus-format metrics. Readiness and liveness probes are enabled. Production health details are suppressed. Metrics carry application/environment tags.

The canonical security baseline records correlation/request identifiers and masked sensitive logging.

**NOT ESTABLISHED / DECISION REQUIRED**

No production metrics collector, dashboarding system, centralized log platform, SIEM, alert-routing product, on-call integration, SLO/error-budget target, or retention policy is established. HPR-P1-010 must define this operating model from approved decisions.

## 4. Security and Secrets

### 4.1 Current externalization

**CURRENT — repository verified**

Production-sensitive runtime inputs are externalized, including datasource credentials, JWT issuer/JWK/HMAC inputs, OIDC inputs, LDAP endpoint/bind credentials when enabled, bootstrap password when explicitly enabled, and CORS origins.

Production OpenAPI and Swagger UI are disabled by default unless explicitly enabled.

### 4.2 Approved target requirements

**APPROVED TARGET**

Existing approved operating decisions remain binding:

- Platform/Infrastructure owns TLS termination.
- Production private keys must not live in Git, images, templates, or developer workstations.
- Enterprise/private CA is preferred for internal service trust; approved public CA applies where public trust is required.
- Certificate monitoring thresholds are 45/30/14/7 days.
- JWT HS256, PostgreSQL credentials, and LDAP bind credentials have 90-day routine rotation baselines unless stricter enterprise policy applies.
- Emergency rotation is immediate after credible compromise.
- Rotation/recovery requires verification evidence.

These requirements do not select a secret manager, TLS proxy, ingress technology, certificate automation product, or deployment platform.

### 4.3 Unresolved security-runtime technology

**NOT ESTABLISHED / DECISION REQUIRED**

The repository does not establish a secret-manager product, runtime secret injection mechanism, TLS termination product/exact placement, mTLS topology, firewall/ACL product, ingress/reverse-proxy/API-gateway product, OT/IT zones or DMZ, production OIDC provider identity, or production LDAP endpoint/failover model.

## 5. Deployment Model

**CURRENT — repository verified**

GitHub Actions builds and verifies the Spring Boot application, starts the built JAR in the test profile, polls application health, generates deterministic OpenAPI, and uploads the OpenAPI artifact. CI uses PostgreSQL 16 as a service.

This is CI/test evidence, not production deployment architecture.

Repository inspection for HPR-P1-001 did not establish a production Dockerfile, Docker Compose deployment, Kubernetes/OpenShift manifests, Helm charts, or infrastructure-as-code deployment definition.

**NOT ESTABLISHED / DECISION REQUIRED**

No production deployment target is approved in repository evidence. Therefore this document does not choose a VM, bare-metal JVM service, container runtime, Docker, Kubernetes, OpenShift, cloud provider, on-premises orchestrator, load balancer, reverse proxy, or ingress controller.

The deployment target must be approved before HPR-P1-007/HPR-P1-009 can safely become product-specific.

## 6. Safe Logical Topology

```text
API / browser / operator client
        |
        v
HidraAPI Spring Boot process
  |        |          |
  |        |          +--> optional external identity/directory capabilities
  |        |               (OIDC and/or LDAP when configured)
  |        |
  |        +--> Actuator / Prometheus-format observability surfaces
  |
  +--> PostgreSQL
       +--> Flyway-managed schema
```

This diagram does not imply the number of HidraAPI nodes, a load balancer, proxy, network zone, TLS placement, PostgreSQL replicas, or monitoring backend.

## 7. Required Production Properties Already Established

The eventual production runtime must preserve:

- explicit production profile activation;
- externalized production secrets and credentials;
- fail-closed security boundaries;
- restricted production error disclosure;
- Flyway validation and disabled clean;
- readiness/liveness health surfaces;
- correlation/request/audit attribution;
- approved secret/certificate lifecycle governance;
- graceful shutdown behavior.

These properties do not establish HA or DR.

## 8. P1 Decision Register

### HPR-P1-002 — Application runtime HA

**APPROVED TARGET — owner accepted 2026-10-05**

Production requires at least two simultaneously active HidraAPI nodes behind a product-neutral managed load-distribution mechanism. Ordinary REST traffic must not require sticky sessions.

Only ready nodes may receive new traffic. Loss of readiness requires removal from new traffic; liveness remains the process-recovery signal. Planned maintenance requires traffic drain followed by graceful shutdown.

The current process-local Spring `simple` cache may be used only where divergence cannot affect correctness. Correctness-sensitive shared state requires an approved cross-node strategy.

The current in-process STOMP simple broker is not an approved clustered realtime mechanism. Multi-node realtime HA may be claimed only after an external/shared broker or equivalent approved cross-node mechanism is implemented and verified.

No load-balancer, orchestrator, cache, broker, VM/container, or hosting product is selected by this decision.

### HPR-P1-003 — PostgreSQL HA

**APPROVED TARGET — owner accepted 2026-10-05**

Production PostgreSQL uses one writable primary and at least one local streaming standby. The local HA standby uses synchronous replication where the approved production latency budget permits; any exception to synchronous local protection requires an explicit operational decision rather than silent degradation.

An additional geographically or failure-domain separated standby may use asynchronous replication as the DR replication tier. That does not replace backups or establish HPR-P1-004 recovery objectives.

HidraAPI connects through one stable database service endpoint and must not encode primary/standby node identities. Database Operations owns controlled switchover and normal failover. Emergency failover follows the approved incident-governance authority with Database Operations and Platform/Operations participation.

The chosen infrastructure must prevent two writable primaries through explicit failover authority/fencing. Application recovery after a role change is based on reconnecting to the stable endpoint; transparent survival of an in-flight transaction is not claimed.

No HA manager, proxy, virtual IP, DNS mechanism, database appliance, cloud service, or orchestration product is selected by this decision.

### HPR-P1-004 — Disaster recovery objectives

**APPROVED TARGET — owner accepted 2026-10-05**

The production recovery objective is **RTO ≤ 60 minutes**, measured from formal DR declaration until the recovered HidraAPI production service passes the required acceptance gate.

The production data objective is **RPO ≤ 5 minutes**, measured as the maximum acceptable gap between the chosen recovery point and the latest production data that must be recoverable.

PostgreSQL requires continuous WAL archiving and PITR capability. Backup coverage requires at least one successful recoverable base/full backup every 24 hours plus the WAL required to reach approved recovery points.

Operational backup retention is **35 days**. Monthly recovery points are retained according to applicable enterprise/records-governance policy; no longer monthly-retention duration is invented where policy evidence is absent.

Backups must be independent of the live primary/standby failure domain. Database replicas do not count as backups.

DR declaration follows the approved incident-governance authority. Database Operations owns restore/PITR execution, Platform/Operations owns application/runtime recovery, and recovered-service acceptance requires the accountable operational/business authority.

No backup, archive, storage, snapshot, orchestration, or DR product is selected by this decision.

### Later P1 decisions

**DECISION REQUIRED**

Production deployment target, promotion/rollback mechanism, secret injection implementation, TLS placement/product, network perimeter/zones, observability backend/alert routing, and database operational tooling remain unresolved.

## 9. Production Readiness

HPR-P1-001 establishes a governed runtime-architecture baseline only.

**Production readiness remains NOT ESTABLISHED.**

Application HA and PostgreSQL HA target models plus RTO/RPO/backup/PITR objectives are now approved, but their infrastructure, executable recovery procedures, production deployment automation, observability operations, and measured failover/restore evidence still require subsequent P1 tasks.
