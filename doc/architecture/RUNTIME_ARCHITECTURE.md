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

**DECISION REQUIRED**

Approve multi-node requirement, load-distribution mechanism/ownership, readiness/liveness integration, failure detection and traffic removal, WebSocket/STOMP multi-node behavior, cache consistency expectations, background-work ownership, and rolling maintenance behavior.

### HPR-P1-003 — PostgreSQL HA

**DECISION REQUIRED**

Approve PostgreSQL HA topology, replication mode, failover authority, connection endpoint/routing behavior, maintenance behavior, ownership, and acceptance checks.

### HPR-P1-004 — Disaster recovery objectives

**DECISION REQUIRED**

Approve RTO, RPO, backup frequency, backup retention, WAL/PITR strategy, recovery authority, and evidence expectations. No values are inferred here.

### Later P1 decisions

**DECISION REQUIRED**

Production deployment target, promotion/rollback mechanism, secret injection implementation, TLS placement/product, network perimeter/zones, observability backend/alert routing, and database operational tooling remain unresolved.

## 9. Production Readiness

HPR-P1-001 establishes a governed runtime-architecture baseline only.

**Production readiness remains NOT ESTABLISHED.**

Application HA, PostgreSQL HA, RTO/RPO, backup/restore, WAL/PITR, disaster recovery, production deployment automation, observability operations, and measured failover/restore evidence require subsequent P1 tasks.
