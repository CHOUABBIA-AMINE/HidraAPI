# Hidra Macro Architecture v1.2

## 1. Document Status

- Product: Hidra – Hydrocarbon Intelligence for Data, Risk, and Analytics
- Document type: Macro architecture
- Version: 1.2
- Status: For discussion / baseline for implementation
- Target repository: HidraAPI
- Architecture style: Domain-Driven Design (DDD), Hexagonal Architecture, Modular Monolith

## 2. Architecture Mission

Hidra is a hydrocarbon intelligence platform for Sonatrach pipeline operations, turning operational data into validated, auditable, risk-aware intelligence that is ready for analytics and future digital twin scenarios.

The macro architecture must:

- Protect business truth and domain language.
- Keep bounded contexts explicit and stable.
- Enforce strict ownership and dependency rules between modules.
- Remain evolvable toward workflow-driven, event-driven, and analytics-driven capabilities.

## 3. Architecture Drivers

Key drivers and their impact on architecture:

- Pipeline operations are domain-specific  
  → Use Domain-Driven Design with clear bounded contexts that map to real operational language.

- Data must be trusted  
  → Build in validation workflows, auditability, and traceability from the start. Every important action must be explainable.

- Operational deviations create risk  
  → Model monitoring, alerts, incidents, and risk indicators explicitly instead of burying them in generic logging.

- Industrial integration will evolve gradually  
  → Use ports and adapters and a dedicated integration context; do not make SCADA or historian a hard prerequisite for core domain usage.

- Team must avoid early distributed-system complexity  
  → Start with a modular monolith, not microservices. Keep one deployable backend with strong internal modularity.

- Future analytics require clean data  
  → Separate operational writes from projections/read models. Treat analytics and dashboards as consumers of trusted history, not as the source of business truth.

- Security and accountability are critical  
  → Centralize identity, authorization, and audit. Authorization must be policy-driven, not scattered across controllers.

- Telemetry may become high-volume  
  → Start with PostgreSQL for transactional data and historian references, but design so that a dedicated time-series store can be added later without rewriting the domain.

## 4. Architecture Style

Hidra adopts:

- **Domain-Driven Design**  
  with explicit bounded contexts that match pipeline operations, telemetry, validation, planning, monitoring, incidents, risk, and audit.

- **Hexagonal architecture (ports and adapters)**  
  inside each bounded context, to separate domain and application logic from infrastructure concerns.

- **Modular monolith deployment**  
  in early versions: one backend deployable, multiple well-defined domain modules.

- **Event-aware internal design**  
  using domain events and an outbox pattern for reliable internal and external event publication.

- **API-first external integration**  
  with clear REST contracts per context and stable URL paths and payloads.

- **Layer separation**  
  between API, application, domain, infrastructure, platform, and shared kernel layers.

## 5. Modular Monolith First

Hidra must start as a modular monolith instead of microservices.

Reasons:

- Business boundaries and ownership are still being refined.
- Operational workflows cross multiple contexts (telemetry, topology, organization, incidents, risk).
- Simpler deployment lets the team focus on correctness, validation, and auditability.
- Strong consistency is required for validation, approval, and risk decisions.
- Modularity can be enforced with package rules and architecture tests without distributed complexity.

Service extraction into separate deployables can be considered later for:

- High-volume telemetry ingestion.
- Notification delivery.
- Analytics processing and reporting.
- Specialized integration connectors.
- Very heavy projections and read models.

Any extraction must be justified by clear load, ownership, operational, and lifecycle reasons.

## 6. System Context

Hidra sits between human operational actors and industrial/enterprise systems:

- **Human actors**  
  - Operator  
  - Validator  
  - Planner  
  - Supervisor  
  - Risk / HSE stakeholder  
  - Administrator  
  - Auditor  
  - Integration administrator  
  - Executive manager

- **External systems**  
  - SCADA systems  
  - Historians / PI systems  
  - Telemetry gateways  
  - Enterprise IAM / identity providers  
  - Notification channels (email, SMS, messaging)  
  - BI and analytics platforms

The technical stack:

- Backend: HidraAPI (Spring Boot 4, Java 21, PostgreSQL, Flyway, Spring Security, Actuator, Micrometer, springdoc OpenAPI).
- Frontend: HidraWEB (React / TypeScript) calling REST APIs.
- Optional: SCADA and historian connectors through the integration context.

## 7. Container Architecture

Primary containers:

- **HidraWEB** – React/TypeScript web client.
- **HidraAPI** – Spring Boot backend modular monolith.
- **PostgreSQL** – transactional database (operational truth, workflow, audit, configuration).
- **Time-series store (future)** – dedicated telemetry/time-series storage when needed.
- **Outbox table** – transactional event log for reliable event publication.
- **Cache layer** – optional cache (e.g., Redis) for reference data and projections.
- **Observability stack** – logs, metrics, traces, health and readiness endpoints.
- **External connectors** – IAM, SCADA/historian gateways, notification channels, BI/analytics, file import/export.

For v1.2, there is a single backend deployable unit (HidraAPI) containing all domain modules.

## 8. Canonical Bounded Contexts

Hidra v1.2 defines these canonical bounded contexts, each mapped to `dz.sh.hidra.modules.<context>`:

- **kernel** (shared kernel)  
  - Stable primitives: identifiers, value objects, pagination, result types, domain-event base types.  
  - No business aggregates or module-specific DTOs.

- **platform** (technical foundation)  
  - Cross-cutting technical concerns: configuration, exception handling, observability, security plumbing, persistence configuration, outbox infrastructure.  
  - No business concepts or aggregates.

- **identity** (core)  
  - Users, roles, permissions, authorities, groups.  
  - Authentication and authorization, permission evaluation, security policies.

- **organization** (core)  
  - Employees, organization units, positions, employee assignments, hierarchy, matrix reporting, station-as-organization-unit representation.  
  - Real operational people and responsibility structures.

- **topology** (core)  
  - Pipeline network, infrastructure, segments, stations, terminals, equipment, measurement points, topology lifecycle and versioning.  
  - Physical and operational topology.

- **telemetry** (core)  
  - Flow readings, sensor readings, measurement batches.  
  - Measurement quality, validation state, historian and SCADA references.

- **planning** (core)  
  - Flow plans, planning periods, targets, plan versions.  
  - Planned vs actual comparison based on validated telemetry.

- **monitoring** (core)  
  - Thresholds, monitoring rules, operational states, deviations, risk signals.  
  - Interpretation of telemetry into alerts and risk indicators.

- **incidents** (core)  
  - Incident lifecycle, classification, impact, response actions, root cause, resolution.  
  - Operational incident management.

- **workflow** (supporting core)  
  - Workflow definitions, instances, tasks, approval steps, delegations, escalations.  
  - General validation and approval orchestration across contexts.

- **audit** (cross-cutting)  
  - Append-only audit events.  
  - Actor/target/action context, correlation and request IDs, decision evidence.

- **integration** (supporting)  
  - External systems, connectors, ingestion jobs, mapping rules, retries, synchronization state, dead-letter records.  
  - Inbound and outbound integration.

- **analytics** (supporting)  
  - KPIs, projections, trends, risk analytics, digital-twin readiness views.  
  - Derived views created from validated operational truth and audit history.

- **reporting** (supporting)  
  - Operational reports and exports.  
  - Reporting projections and export formats.

- **notification** (supporting)  
  - Notification templates, channels, scheduling and delivery, delivery tracking and status.

Each context owns its own data model and APIs and must not leak internal persistence entities to others.

## 9. Dependency Rules

Logical dependency directions:

- `api` → `application` → `domain`
- `application` → `ports` (domain interfaces)
- `infrastructure` → `ports` and `domain` (adapters, persistence)
- `modules.*` → `kernel` (stable primitives only)
- `platform` → `kernel`

Allowed high-level module dependencies:

- Modules can depend on `kernel` and `platform`.
- Modules can call each other through well-defined outbound ports and integration patterns (or via integration context), not by importing foreign domain models.

Forbidden patterns:

- **Layer violations**  
  - Controllers (API layer) directly accessing repositories.  
  - Domain layer depending on Spring, JPA, or web frameworks.  
  - Application layer depending on API layer types.

- **Cross-domain leakage**  
  - Domain classes from one module importing domain classes from another module (e.g., organization.domain importing topology.domain).  
  - Sharing JPA entities across bounded contexts.

- **Shared kernel misuse**  
  - Putting business aggregates or module-specific DTOs in the shared kernel.  
  - Using kernel as a generic utility dumping ground.

- **Workflow misuse**  
  - Workflow owning business rules that belong to telemetry, topology, incidents, planning, or other contexts.

- **Analytics and integration misuse**  
  - Analytics modifying source-of-truth operational state.  
  - Integration adapters bypassing application and domain rules to write directly into persistence.

## 10. Data Architecture

Hidra distinguishes several data categories:

- **Transactional data**  
  - Stored in PostgreSQL.  
  - Examples: users, roles, employees, organization units, topology elements, telemetry readings, plans, monitoring rules, incidents, workflow instances, audit events, integration configuration.

- **Telemetry / time-series data**  
  - In v1.2, validated operational readings are stored in PostgreSQL with references to external historians or SCADA systems.  
  - Future versions may add dedicated time-series storage when required by volume, retention, or analytics workloads.

- **Audit data**  
  - Append-only, immutable audit events.  
  - Fields typically include actor, action, target, timestamps, correlation ID, request ID, before value, after value, decision reason, workflow state.

- **Outbox data**  
  - Internal event/outbox records for reliable publication to internal or external subscribers.  
  - Fields typically include event ID, aggregate ID, aggregate type, event type, payload, occurred at, published at, retry count, status.

- **Projection / reporting data**  
  - Read models and projections optimized for queries, dashboards, analytics, and reports.  
  - Do not own business truth; can be rebuilt from transactional and audit data.

Transactional and audit data are the source of truth. Projections and analytics views are derived.

## 11. Integration Architecture

Integration is port-and-adapter based and is owned by the **integration** context.

Supported integration types:

- REST APIs.
- SCADA adapters.
- Historian adapters.
- OPC UA readiness.
- MQTT readiness.
- CSV/Excel import/export.
- Enterprise IAM integration.
- Notification gateways.
- External analytics export.

The integration context owns:

- Connector registry and external system definitions.
- Mapping rules and transformation logic.
- Ingestion jobs and synchronization processes.
- Synchronization status and metrics.
- Retry policies and dead-letter handling.
- External reference tracking.

Business modules must not talk directly to external systems; they call outbound ports, implemented by adapters in the integration context.

## 12. Security Architecture

Security is centralized in the **identity** module and **platform** security infrastructure:

- **Authentication**  
  - Internal user store or external IAM integration (e.g., OAuth2/OpenID Connect).

- **Authorization**  
  - Role-based access control (RBAC) with readiness for attribute-based constraints (ABAC).  
  - Roles, permissions, authority codes, and groups.  
  - Organization-scoped permissions where applicable (e.g., permissions limited to certain organization units).

- **Auditability**  
  - Security-relevant decisions (login, permission decisions, critical actions) are auditable via the audit module.

- **Policy-driven**  
  - Authorization logic is implemented in identity/application services and policies, not scattered across controllers.

Controllers enforce access via annotations or centralized interceptors; they do not hard-code permission logic.

## 13. Observability Architecture

Hidra must provide:

- **Structured logs**  
  - Include correlation IDs, request IDs, actor IDs, and organization scopes where applicable.  
  - Avoid logging sensitive data.

- **Metrics**  
  - Operational metrics for ingestion, validation, workflow transitions, alerts, incidents, integration jobs, and performance hotspots.

- **Tracing**  
  - Traces for critical flows: ingest → validate → approve → alert → incident.

- **Health and readiness**  
  - Health and readiness endpoints for all relevant containers for deployment and orchestration platforms.

Audit events and correlation IDs allow cross-cutting tracing of business decisions.

## 14. Deployment Architecture

Initial deployment targets:

- Local development.
- Test.
- Staging.
- Production.

Deployment requirements:

- Profile-based configuration.
- Flyway database migrations.
- Secrets management (e.g., environment variables, vault).
- Health and readiness endpoints.
- Backup and restore strategy for PostgreSQL.
- Logs and metrics export to the observability stack.
- Container readiness and liveness probes.
- CI pipelines to validate build, tests, and basic quality gates.

In v1.2, HidraAPI is deployed as a single backend service with all modules inside.

## 15. Macro Architecture Decisions

Key decisions for v1.2:

- Use a **modular monolith** first; defer microservices until necessary.
- Use **DDD bounded contexts** aligned with operational language and responsibilities.
- Use **hexagonal architecture** inside each context.
- Use **PostgreSQL** as the primary transactional store; historian references for external time-series.
- Use an **outbox pattern** for reliable internal and external event publication.
- Treat **analytics as projections**, not as owners of business truth.
- **Separate telemetry from monitoring**, and **separate alerts from incidents**.
- Treat **risk as cross-operational**, emerging from telemetry, monitoring, planning, incidents, and validation, not a separate monolith on its own.

## 16. Open Architecture Questions

Open questions for future iterations:

1. Should risk become a separate bounded context later, or remain distributed across monitoring, incidents, planning, and analytics?
2. At what stage should notification and reporting be extracted from the modular monolith into separate deployables?
3. What is the first historian integration target and the expected telemetry volume for that integration?
4. Which topology elements are mandatory for the first production release (pipeline, segment, station, equipment, measurement point)?
5. What concrete SLAs do we require for ingestion, validation, alerting, and incident response in production?
6. Which read models and dashboards provide the fastest operational value for v1.2?