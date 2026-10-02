# Hidra Macro Architecture

## 1. Document Status

| Field | Value |
|---|---|
| Product | Hidra |
| Meaning | Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Document type | Macro architecture |
| Version | 0.2 Draft |
| Status | For discussion |
| Target repository | HidraAPI |
| Architecture style | DDD + Hexagonal Architecture + Modular Monolith |
| Canonical package root | `dz.sh.hidra` |
| Foundation module name | `kernel` |
| Identity module name | `identity` |

---

## 2. Architecture Mission

The macro architecture defines the global structure of Hidra as an enterprise-grade hydrocarbon intelligence platform for Sonatrach pipeline operations.

It must support:

```text
business clarity
modularity
data trust
operational risk awareness
auditability
validation discipline
integration readiness
analytics readiness
long-term digital twin readiness
```

---

## 3. Naming Standard

The canonical Java package root is:

```text
dz.sh.hidra
```

The foundation module is:

```text
kernel
```

The identity module is:

```text
identity
```


---

## 4. Architecture Drivers

| Driver | Impact on architecture |
|---|---|
| Pipeline operations are domain-specific | Use Domain-Driven Design and bounded contexts |
| Data must be trusted | Introduce validation workflow and audit by design |
| Operational deviations create risk | Model monitoring, alerts, incidents, and risk indicators explicitly |
| Industrial integration will evolve gradually | Use ports/adapters and an integration context |
| Team must avoid early complexity | Start with modular monolith, not microservices |
| Future analytics require clean data | Separate operational writes from projections/read models |
| Security and accountability are critical | Centralize identity, authorization, and audit |
| Telemetry may become high-volume | Separate transactional model from time-series strategy |

---

## 5. Architecture Style

Hidra uses:

```text
Domain-Driven Design
Hexagonal Architecture
Modular Monolith first
Event-aware internal architecture
API-first external integration
Clear separation of API, application, domain, infrastructure, platform, and kernel layers
```

---

## 6. Why Modular Monolith First

Hidra should not start as microservices.

Reasons:

```text
business boundaries are still being consolidated
operational workflows cross multiple domains
deployment simplicity is important
strong consistency is needed for validation and audit
team velocity is higher with one deployable unit
modularity can be enforced without distributed-system complexity
```

Future extraction may be possible for:

```text
telemetry ingestion
notification delivery
analytics processing
integration connectors
reporting projections
```

Extraction must happen only after clear load, ownership, operational, and lifecycle reasons appear.

---

## 7. System Context

Hidra sits between human operational actors and industrial/enterprise systems.

```mermaid
flowchart LR
    Operator[Operator]
    Validator[Validator]
    Planner[Planner]
    Supervisor[Supervisor]
    RiskUser[Risk / HSE Stakeholder]
    Admin[Administrator]
    Auditor[Auditor]

    SCADA[SCADA Systems]
    Historian[Historian / PI System]
    IAM[Enterprise IAM]
    Notify[Notification Channels]
    BI[BI / Reporting Tools]
    Analytics[External Analytics]

    Hidra[Hidra Platform]

    Operator --> Hidra
    Validator --> Hidra
    Planner --> Hidra
    Supervisor --> Hidra
    RiskUser --> Hidra
    Admin --> Hidra
    Auditor --> Hidra

    SCADA --> Hidra
    Historian <--> Hidra
    IAM --> Hidra
    Hidra --> Notify
    Hidra --> BI
    Hidra --> Analytics
```

---

## 8. Container Architecture

```mermaid
flowchart TB
    Web[Web Client / Future UI]
    API[Hidra Backend Application]
    DB[(PostgreSQL Transactional Database)]
    TS[(Time-Series Storage / Historian Reference)]
    Cache[(Cache)]
    Outbox[(Outbox / Event Table)]
    Broker[Message Broker - Future Optional]
    Observability[Logging / Metrics / Tracing Stack]
    IAM[Identity Provider]
    SCADA[SCADA / OPC UA / MQTT Gateways]
    Historian[Historian System]

    Web --> API
    API --> DB
    API --> Cache
    API --> Outbox
    Outbox --> Broker
    API --> Observability
    IAM --> API
    SCADA --> API
    API <--> Historian
    API --> TS
```

---

## 9. Canonical Bounded Contexts

| Context | Type | Responsibility | Owns | Does Not Own |
|---|---|---|---|---|
| kernel | Foundation | Stable primitives, IDs, base value objects, result types | Cross-context primitives | Business aggregates |
| platform | Technical foundation | Configuration, exception handling, observability, security plumbing | Technical services | Business concepts |
| identity | Core/platform | Users, roles, permissions, authorities, groups | Authentication and authorization model | Employee hierarchy |
| organization | Core | Employees, departments, operational units, structure assignments | Human/organizational structure | Login credentials |
| topology | Core | Pipeline network, infrastructure, equipment, measurement points | Physical/operational topology | Flow values |
| telemetry | Core | Readings, sensor facts, measurement quality, validation state | Measurement data | Incident lifecycle |
| workflow | Supporting/core | Validation and approval orchestration | Workflow instances/tasks/actions | Domain business rules |
| planning | Core | Flow plans, targets, planning periods, planned vs actual comparison | Operational targets | Raw telemetry ingestion |
| monitoring | Core | Thresholds, monitoring rules, operational state, anomaly/risk signals | State interpretation | Incident resolution |
| incidents | Core | Incident lifecycle, classification, response, root cause, resolution | Incident state | Raw alert rule evaluation |
| audit | Cross-cutting | Audit events, actor traceability, before/after values | Audit trail | Business decisions |
| integration | Supporting | External systems, connectors, ingestion jobs, mappings, retries | Integration configuration | Domain ownership |
| analytics | Supporting | KPIs, projections, trends, insights, risk analytics | Derived views | Source-of-truth state |
| reporting | Supporting | Operational reports and exports | Report definitions/projections | Source data ownership |
| notification | Supporting | Notification templates, delivery, channels | Delivery events | Alert business rules |

---

## 10. Dependency Rules

Allowed direction:

```text
api -> application -> domain
application -> ports
infrastructure -> ports/domain mappings
platform -> technical support
kernel -> stable primitives only
```

Forbidden:

```text
controller directly accesses repository
domain depends on infrastructure
domain depends on Spring Web
application depends on API layer
modules share persistence entities
kernel becomes a dumping ground
workflow owns business rules that belong to a domain
analytics modifies source-of-truth operational state
integration adapters bypass application/domain rules
```

---

## 11. Data Architecture

Hidra distinguishes between:

```text
transactional data
telemetry/time-series data
audit data
event/outbox data
projection data
```

### 11.1 Transactional Data

Stored in PostgreSQL.

Examples:

```text
users
roles
employees
topology
workflow instances
plans
monitoring rules
incidents
audit events
integration configuration
```

### 11.2 Telemetry and Time-Series Data

Initial approach:

```text
1. Store validated operational readings in PostgreSQL for the first version.
2. Keep historian references for external systems.
3. Add dedicated time-series storage later if volume, retention, or analytics workloads require it.
```

The initial design must not block future time-series extraction.

### 11.3 Audit Data

Audit data must be append-only.

Audit records should include:

```text
actor
action
target
timestamp
correlation ID
request ID
before value
after value
decision reason
workflow state
```

### 11.4 Outbox Data

Outbox records support reliable event publication.

Fields:

```text
event ID
aggregate ID
aggregate type
event type
payload
occurred at
published at
retry count
status
```

### 11.5 Projection Data

Analytics and reporting use projections/read models where possible.

Read models do not own business truth.

---

## 12. Integration Architecture

Integration must be port/adapter-based.

Target integration types:

```text
REST APIs
SCADA adapters
historian adapters
OPC UA readiness
MQTT readiness
CSV/Excel import/export
enterprise IAM integration
notification gateways
external analytics export
```

Integration context owns:

```text
connector registry
external system definitions
mapping rules
ingestion jobs
sync status
retry policy
dead-letter handling
external reference tracking
```

---

## 13. Security Architecture

Security must support:

```text
authentication through internal or external IAM
RBAC
ABAC readiness
organization-scoped permissions
role and permission catalog
authority model
group assignment
segregation of duties
auditability of security decisions
```

Authorization should be policy-driven, not scattered across controllers.

---

## 14. Observability Architecture

Hidra must provide:

```text
structured logs
correlation IDs
request IDs
actor IDs
organization IDs where applicable
module names
operation names
metrics
health checks
readiness checks
integration job monitoring
ingestion monitoring
audit visibility
```

Sensitive data must never be logged.

---

## 15. Deployment Architecture

Initial deployment targets:

```text
local development
test
staging
production
```

Required deployment concerns:

```text
profile-based configuration
Flyway database migrations
secrets management
health and readiness endpoints
backup and restore
logs and metrics export
container readiness
CI validation
```

---

## 16. First Implementation Order

```text
1. repository skeleton
2. kernel
3. platform foundation
4. identity
5. organization
6. topology
7. telemetry
8. workflow
9. planning
10. monitoring
11. incidents
12. audit hardening
13. integration
14. analytics/reporting
15. notification
```

Dependency rule:

```text
Workflow follows telemetry.
Planning follows workflow-validated telemetry.
Monitoring follows trusted telemetry and planning.
Incidents follow monitoring.
Audit hardening follows incidents but audit-ready ports/events are designed earlier.
Analytics/reporting follow trusted operational data.
Notification follows workflow, monitoring, and incident event producers.
```

---

## 17. Macro Architecture Decisions

| Decision | Status | Rationale |
|---|---|---|
| Use modular monolith first | Proposed | Simpler deployment and stronger consistency |
| Use DDD bounded contexts | Proposed | Protect business model from technical drift |
| Use hexagonal architecture | Proposed | Keep domain independent from frameworks and integrations |
| Use PostgreSQL first | Proposed | Reliable transactional foundation |
| Delay microservices | Proposed | Avoid premature distributed complexity |
| Use outbox pattern | Proposed | Reliable internal/external event publication readiness |
| Treat analytics as projections | Proposed | Prevent derived data from becoming source of truth |
| Separate telemetry from monitoring | Proposed | Telemetry records facts; monitoring interprets state |
| Separate alerts from incidents | Proposed | Alerts indicate attention; incidents manage operational problems |
| Treat risk as cross-operational concern | Proposed | Risk signals emerge from monitoring, incidents, planning, validation workflow, and analytics |

---

## 18. Open Architecture Questions

```text
1. Should risk become a separate bounded context later, or remain distributed across monitoring, incidents, planning, and analytics?
2. Should notification and reporting be separate modules from the start, or introduced after operational core stabilization?
3. What is the first historian integration target?
4. What telemetry volume is expected in the first production use case?
5. Which topology elements are mandatory for version 1?
