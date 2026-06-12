# HIDRA-ARCH-MICRO-v3.4-corrected-enhanced-final

```text
Document code       : HIDRA-ARCH-MICRO
Repository          : HidraAPI
Canonical namespace : dz.sh.hidra
Product             : Hidra — Hydrocarbon Intelligence for Data, Risk, and Analytics
Document type       : Micro architecture standard
Version             : 3.4
Status              : Corrected, enhanced, and final implementation baseline
Owner               : Sonatrach TRC Digitalization Initiative
Scope               : All HidraAPI bounded contexts and all backend implementation decisions
UpdatedOn           : 2026-06-11
Related documents   : HIDRA-ARCH-MACRO, Master Product Vision
```

---

## 1. Purpose

This document defines the enforceable micro architecture for HidraAPI.

It converts the Hidra product vision and macro architecture into code-level rules for:

- modules,
- packages,
- ports,
- persistence,
- events,
- workflow,
- audit,
- integration,
- topology visualization,
- observability,
- and automated enforcement.

This document exists to keep Hidra aligned with Sonatrach's real operational need: a trusted, auditable, topology-aware, workflow-governed operational intelligence platform for hydrocarbon transportation systems.

If an implementation choice does not strengthen that purpose, it does not belong in Hidra.

---

## 2. Architectural Thesis

Hidra evolves in one direction only:

```text
trusted data -> live operations -> engineering calculation -> enterprise integration -> explainable intelligence
```

That order is not roadmap decoration. It is the primary implementation guardrail.

Therefore:

- analytics must never outrank untrusted telemetry,
- workflow must never own business facts,
- integration must never bypass domain validation,
- simulation must never replace approved topology and trusted measurements,
- AI agents must never become autonomous operational control,
- dashboards must never become operational truth,
- external systems must never mutate Hidra source-of-truth state directly.

---

## 3. Rule Language

This document uses strict rule language.

| Term | Meaning |
|---|---|
| **MUST** | Mandatory rule. Violations are architecture defects. |
| **MUST NOT** | Forbidden rule. Violations must be rejected or corrected. |
| **SHOULD** | Strong default. Exceptions require justification. |
| **MAY** | Allowed option when it does not violate stronger rules. |
| **ADR-ONLY** | Allowed only through an Architecture Decision Record. |
| **FORBIDDEN** | Not allowed, even if convenient. |

Where this document conflicts with local coding convenience, this document wins.

---

## 4. What This Architecture Optimizes For

This micro architecture optimizes for:

- strong bounded contexts and explicit ownership,
- topology as the operational graph backbone,
- workflow as governance, not domain ownership,
- audit-first, append-only evidence for state changes,
- modular monolith discipline before extraction,
- controlled industrial and enterprise integration through explicit ports,
- explainable and traceable intelligence layers,
- human-governed operational decisions,
- enforceability through code rules, schema rules, and CI checks.

This micro architecture does not optimize for:

- premature microservices,
- generic enterprise scaffolding,
- cross-module convenience imports,
- uncontrolled shared DTOs,
- generic facades,
- direct industrial control,
- unsafe automation,
- or framework-driven domain design.

---

## 5. Non-Negotiable Rules

### 5.1 Ownership Rule

Each module owns:

- its business language,
- its aggregates and invariants,
- its persistence namespace,
- its state transitions,
- its public input ports,
- its output port contracts,
- its events,
- its projections,
- its module-specific exceptions,
- and its module-specific API contracts.

No other module may:

- import its internal services,
- import its persistence entities,
- call its repositories,
- write its tables,
- copy its lifecycle state as operational truth,
- or bypass its application input ports.

### 5.2 Workflow Rule

Workflow owns:

- tasks,
- approvals,
- rejections,
- delegations,
- escalations,
- due dates,
- assignment routing,
- workflow state machines,
- decision evidence,
- and workflow history.

Workflow may reference a target object, but it MUST NOT own:

- telemetry quality state,
- incident severity,
- planning targets,
- monitoring thresholds,
- alarm lifecycle facts,
- topology graph structure,
- simulation results,
- asset maintenance logic,
- integrity diagnosis,
- or custody accounting records.

Workflow is a governance module, not a business-fact owner.

### 5.3 Audit Rule

Every state-changing operation MUST produce append-only audit evidence containing at least:

- actor identity,
- actor organization scope where applicable,
- time,
- target reference,
- action,
- reason or context,
- module owner,
- correlation token,
- request token where applicable,
- workflow reference where applicable,
- before/after evidence where relevant,
- source reference where imported,
- and idempotency reference where relevant.

Audit records evidence only. Audit MUST NOT become a business decision engine.

### 5.4 Safety Rule

No Hidra module may directly actuate:

- valves,
- pumps,
- compressors,
- PLCs,
- RTUs,
- SCADA control loops,
- emergency shutdown systems,
- or safety instrumented systems

as part of normal architecture scope.

Monitoring, simulation, risk, analytics, and AI agents are decision-support capabilities only.

### 5.5 Integration Rule

All external exchange MUST pass through:

```text
external system -> integration adapter -> target module import/export port -> domain validation -> audit/outbox
```

The following are FORBIDDEN:

- silent imports,
- direct table mutation,
- validation bypass,
- uncontrolled external writes,
- external payloads becoming domain models directly,
- connectors calling module repositories,
- connectors importing module infrastructure,
- external systems becoming Hidra source of truth without validation.

---

## 6. Canonical Build Structure

HidraAPI SHOULD use a modular build structure that makes module boundaries visible.

Recommended Maven or Gradle structure:

```text
hidra-api
  hidra-bootstrap
  hidra-kernel
  hidra-platform

  hidra-identity
  hidra-organization
  hidra-workflow
  hidra-audit

  hidra-topology
  hidra-telemetry
  hidra-planning
  hidra-monitoring
  hidra-alarms
  hidra-incidents

  hidra-simulation
  hidra-leakdetection
  hidra-integrity
  hidra-assets
  hidra-hse

  hidra-custody
  hidra-integration
  hidra-notification
  hidra-documents
  hidra-configuration

  hidra-risk
  hidra-analytics
  hidra-reporting
  hidra-environment
  hidra-otsecurity
  hidra-agents
```

### 6.1 Bootstrap Module

`hidra-bootstrap` owns only:

- application startup,
- Spring Boot entry point,
- module assembly,
- runtime profile selection,
- application-level configuration imports.

It MUST NOT contain business use cases, repositories, controllers, or domain rules.

### 6.2 Foundation Modules

`hidra-kernel` and `hidra-platform` are foundation modules.

They may be depended on by business modules, but they MUST remain small and controlled.

### 6.3 Business Modules

Every business module SHOULD be independently understandable and SHOULD expose only explicit public contracts.

Business modules MUST NOT become generic libraries for each other.

---

## 7. Canonical Package Structure

Each module follows this package shape unless an ADR-approved exception exists.

```text
dz.sh.hidra.modules.<module>
  api
    rest
    request
    response
    mapper
  application
    command
    query
    dto
    service
    port
      in
      out
  domain
    model
    value
    event
    policy
    service
    exception
  infrastructure
    configuration
    persistence
      entity
      repository
      mapper
      adapter
    messaging
    projection
    scheduler
    adapter
```

### 7.1 Forbidden Package Names

The following package names are FORBIDDEN inside business modules unless explicitly approved by ADR:

```text
shared
common
core
utils
helper
helpers
misc
base
generic
facade
```

These names are forbidden because they become dumping grounds and destroy module accountability.

### 7.2 Package Intent

| Package | Owns | Must not own |
|---|---|---|
| `api.rest` | REST controllers | Business rules, repositories |
| `api.request` | Transport request contracts | Domain entities |
| `api.response` | Transport response contracts | Persistence entities |
| `api.mapper` | API mapping only | Domain behavior |
| `application.command` | State-changing command objects | Persistence concerns |
| `application.query` | Read/query request objects | Mutation logic |
| `application.dto` | Use-case DTOs | Cross-module shared DTO dumping |
| `application.service` | Use-case implementations | Generic CRUD services |
| `application.port.in` | Input ports/use cases | Infrastructure details |
| `application.port.out` | Required output contracts | Adapter implementations |
| `domain.model` | Aggregates/entities | JPA annotations |
| `domain.value` | Value objects | Framework annotations |
| `domain.event` | Domain events | Messaging infrastructure |
| `domain.policy` | Business policies | Transport validation |
| `domain.service` | Domain services | Application orchestration |
| `domain.exception` | Module domain errors | HTTP serialization |
| `infrastructure.persistence.entity` | ORM entities | Domain behavior |
| `infrastructure.persistence.repository` | Spring Data repositories | Business decisions |
| `infrastructure.persistence.mapper` | ORM/domain mapping | API mapping |
| `infrastructure.persistence.adapter` | Persistence port implementations | Controller logic |
| `infrastructure.messaging` | Messaging adapters | Domain events as infrastructure types |
| `infrastructure.projection` | Read models/projections | Source-of-truth mutation |
| `infrastructure.scheduler` | Scheduled triggers | Domain rules |
| `infrastructure.adapter` | External client adapters | Business ownership |

---

## 8. Layer Contracts

### 8.1 API Layer

The API layer owns:

- REST controllers,
- transport validation,
- request contracts,
- response contracts,
- endpoint annotations,
- API mappers,
- OpenAPI metadata where applicable.

The API layer MUST call only input ports from its own module.

It MUST NOT access:

- repositories,
- `EntityManager`,
- persistence entities,
- external clients,
- another module's infrastructure,
- another module's private application service,
- or domain internals of another module.

### 8.2 Application Layer

The application layer owns:

- use cases,
- commands,
- queries,
- transactions,
- orchestration,
- workflow invocation,
- audit invocation,
- idempotency boundaries,
- authorization checks,
- output port invocation,
- and event publication through ports.

Application services may coordinate domain objects but MUST NOT become dumping grounds for business rules.

### 8.3 Domain Layer

The domain layer owns:

- aggregates,
- entities,
- value objects,
- invariants,
- policies,
- lifecycle rules,
- domain events,
- and domain-specific errors.

The domain layer MUST remain framework-free.

It MUST NOT import:

- Spring MVC,
- Spring Data,
- JPA,
- Hibernate,
- Jackson transport concerns,
- repositories,
- external clients,
- infrastructure adapters,
- or application services.

### 8.4 Infrastructure Layer

The infrastructure layer owns:

- persistence adapters,
- ORM entities,
- Spring Data repositories,
- messaging adapters,
- file adapters,
- schedulers,
- outbox implementations,
- and external clients implementing application output ports.

Infrastructure serves the business model. It MUST NOT dictate domain design.

---

## 9. Public Contract Rules

Each module has a private interior and a narrow public surface.

### 9.1 Public Contract Packages

The following may be imported by another module only when necessary and explicitly allowed by dependency rules:

```text
dz.sh.hidra.modules.<module>.application.port.in
dz.sh.hidra.modules.<module>.application.dto
dz.sh.hidra.modules.<module>.domain.event
```

### 9.2 Private Packages

The following are private to the owning module and MUST NOT be imported by other modules:

```text
dz.sh.hidra.modules.<module>.domain.model
dz.sh.hidra.modules.<module>.domain.policy
dz.sh.hidra.modules.<module>.domain.service
dz.sh.hidra.modules.<module>.infrastructure..
dz.sh.hidra.modules.<module>.api..
dz.sh.hidra.modules.<module>.application.service
```

### 9.3 Cross-Module Communication

Modules may communicate only through:

1. public input ports,
2. published events,
3. read models/projections,
4. controlled import/export ports,
5. stable identifiers and references.

Direct cross-module access to repositories, persistence entities, or private application services is FORBIDDEN.

---

## 10. Naming Rules

### 10.1 Input Ports

Input ports MUST be named after business use cases.

Good examples:

```text
ApproveTelemetryReadingUseCase
CreatePlanRevisionUseCase
DetectDeviationUseCase
AcknowledgeAlarmUseCase
OpenIncidentUseCase
LaunchSimulationRunUseCase
ApproveConfigurationChangeUseCase
```

Bad examples:

```text
TelemetryService
TopologyManager
WorkflowFacade
CommonProcessor
CrudUseCase
```

### 10.2 Output Ports

Output ports MUST describe required capabilities.

Good examples:

```text
LoadTelemetryPointPort
SaveTrustedReadingPort
AppendAuditRecordPort
PublishTelemetryEventPort
LoadApprovedTopologySnapshotPort
ResolveWorkflowDecisionPort
StoreSimulationResultPort
```

Bad examples:

```text
TelemetryRepositoryWrapper
DatabaseService
ExternalHelper
CommonPort
GenericGateway
```

### 10.3 Application Services

Application service implementations SHOULD be named after their use case.

Example:

```text
ApproveTelemetryReadingService implements ApproveTelemetryReadingUseCase
OpenIncidentService implements OpenIncidentUseCase
```

Generic service names are FORBIDDEN.

---

## 11. Shared Foundations

### 11.1 Kernel

`kernel` contains stable primitives only.

Allowed examples:

```text
EntityId
AggregateRoot
DomainEvent
BusinessCode
DateRange
Quantity
Percentage
GeoPoint
CorrelationId
Version
Result
ErrorReason
```

Kernel MUST NOT contain:

- Spring configuration,
- JPA entities,
- repositories,
- HTTP clients,
- module-specific DTOs,
- business services,
- workflow rules,
- audit implementation,
- security implementation.

### 11.2 Platform

`platform` contains technical support only.

Allowed examples:

```text
security plumbing
transaction support
observability support
logging correlation
outbox infrastructure
database migration support
technical exception serialization
persistence base configuration
```

Platform MUST NOT contain:

- business rules,
- domain entities,
- module repositories,
- generic god services,
- generic CRUD abstractions for business modules,
- business DTOs.

---

## 12. Cross-Cutting Mandatory Contracts

### 12.1 Identity and Authorization

Authorization MUST be centralized and policy-driven.

Minimum contracts:

```text
CurrentActorPort
ActorSnapshot
AuthorizationDecision
AuthorityCode
OrganizationScope
```

Authorization rules MUST NOT be scattered as ad hoc checks in controllers.

### 12.2 Workflow

Minimum contracts:

```text
StartWorkflowUseCase
RecordWorkflowDecisionUseCase
WorkflowReference
WorkflowTargetReference
DecisionReason
DecisionComment
DelegationRequest
EscalationRequest
```

Workflow targets MUST use stable references, not foreign aggregate imports.

### 12.3 Audit

Minimum contracts:

```text
AuditAppenderPort
AuditRecord
AuditTargetReference
BeforeAfterSnapshot
CorrelationContext
SourceReference
WorkflowAuditReference
```

Audit appenders MUST be idempotent where repeated command submission is possible.

### 12.4 Events

Minimum contracts:

```text
DomainEvent
ApplicationEvent
EventEnvelope
EventMetadata
OutboxPublisherPort
IdempotencyKey
```

Events MUST include enough metadata for traceability.

### 12.5 Observability

Minimum observability contracts:

```text
CorrelationId
RequestId
ActorId
ModuleName
OperationName
structured logs
metrics
traces
health checks
readiness checks
```

Sensitive data MUST never be logged.

---

## 13. Dependency Rules

### 13.1 Allowed Directions

```text
api -> application
application -> domain
application -> application.port.out
infrastructure -> application.port.out
infrastructure -> platform
module -> kernel
module -> platform for technical concerns only
```

### 13.2 Forbidden Directions

```text
domain -> infrastructure
domain -> Spring MVC / JPA / Hibernate / transport frameworks
api -> infrastructure.persistence
module -> another module's infrastructure
module -> another module's persistence entity
module -> another module's private application service
workflow -> another module's state ownership
analytics -> source-of-truth mutation
integration -> validation bypass
agents -> autonomous final actuation
```

### 13.3 Module Dependency Matrix

| Caller | Allowed dependencies | Forbidden dependencies |
|---|---|---|
| `identity` | kernel, platform | organization internals, workflow internals |
| `organization` | kernel, platform | identity internals |
| `workflow` | kernel, platform, identity public contracts, organization public contracts | domain facts of target modules |
| `audit` | kernel, platform, identity public contracts | business decision ownership |
| `topology` | kernel, platform, workflow public contracts, audit public contracts | telemetry ownership |
| `telemetry` | kernel, platform, topology public contracts, workflow public contracts, audit public contracts | incident lifecycle ownership |
| `planning` | kernel, platform, topology public contracts, telemetry public contracts, workflow public contracts | telemetry ingestion |
| `monitoring` | kernel, platform, topology public contracts, telemetry public contracts, planning public contracts, configuration public contracts | alarm lifecycle ownership |
| `alarms` | kernel, platform, monitoring public contracts, workflow public contracts, notification public contracts, audit public contracts | monitoring threshold ownership |
| `incidents` | kernel, platform, alarms public contracts, leakdetection public contracts, topology public contracts, workflow public contracts, documents public contracts, audit public contracts | monitoring rules |
| `simulation` | kernel, platform, topology public contracts, telemetry public contracts, planning public contracts, audit public contracts | topology ownership, live control |
| `leakdetection` | kernel, platform, topology public contracts, telemetry public contracts, simulation public contracts, monitoring public contracts, audit public contracts | generic incident ownership |
| `integrity` | kernel, platform, topology public contracts, telemetry public contracts, incidents public contracts, simulation public contracts, risk public contracts | maintenance execution |
| `assets` | kernel, platform, topology public contracts, integrity public contracts, incidents public contracts, documents public contracts, workflow public contracts | integrity diagnosis |
| `integration` | kernel, platform, target module import/export ports, audit public contracts | direct table writes, target repositories |
| `analytics` | kernel, platform, projections/read models, audit public contracts | source-of-truth mutation |
| `reporting` | kernel, platform, analytics public contracts, audit public contracts, projections | analytic calculation ownership |
| `risk` | kernel, platform, monitoring/incidents/integrity/hse/simulation public contracts | source domain mutation |
| `agents` | kernel, platform, analytics/risk/simulation/monitoring public contracts | autonomous final decision ownership |

Dependencies not listed are not automatically allowed. They require review and, where necessary, an ADR.

---

## 14. Persistence and Schema Policy

Because Hidra is a modular monolith, database boundaries matter as much as package boundaries.

Each module MUST own its schema or table namespace. Only the owning module may write it.

Preferred schemas:

```text
hidraidentity
hidraorganization
hidraworkflow
hidraaudit
hidratopology
hidratelemetry
hidraplanning
hidramonitoring
hidraalarms
hidraincidents
hidrasimulation
hidraleakdetection
hidraintegrity
hidraassets
hidrahse
hidracustody
hidraintegration
hidranotification
hidradocuments
hidraconfiguration
hidrarisk
hidraanalytics
hidrareporting
hidraenvironment
hidraotsecurity
hidraagents
```

### 14.1 Cross-Module References

Cross-module foreign keys are avoided by default.

Modules SHOULD store stable references such as:

```text
TopologySegmentId
TelemetryPointId
DeviationId
AlarmId
IncidentId
WorkflowTargetReference
SimulationRunId
ExternalReference
```

Consistency SHOULD be enforced in application logic through public ports unless an ADR explicitly approves a database-level constraint.

### 14.2 Migration Ownership

Each module owns its migrations.

Recommended migration naming:

```text
db/migration/<module>/V20260610_001__create_<module>_schema.sql
```

A migration MUST NOT create or alter another module's tables without ADR approval.

### 14.3 Read Models

Read models and projections are derived artifacts.

They MAY be rebuilt. They MUST NOT become operational truth.

---

## 15. Approved Interaction Styles

Hidra uses only four interaction patterns.

### 15.1 Direct Synchronous Input-Port Call

Use when strong consistency or immediate response is required.

Examples:

- telemetry validation checking topology point binding,
- monitoring retrieving approved thresholds,
- custody validating a trusted measurement set.

### 15.2 Internal Event Publication

Use after state changes when coupling should remain low and downstream consistency may be eventual.

Examples:

- `TelemetryReadingApproved`,
- `DeviationDetected`,
- `AlarmRaised`,
- `IncidentOpened`,
- `SimulationRunCompleted`.

### 15.3 Read-Model or Projection Consumption

Use for:

- overlays,
- dashboards,
- reporting,
- analytics inputs,
- executive views,
- topology visualization.

### 15.4 Controlled Import/Export Port

Use for all external data exchange through integration.

Anything outside these four patterns is architecture drift.

---

## 16. Event and Outbox Rules

### 16.1 Event Naming

Events MUST use past-tense business names.

Good examples:

```text
TopologySnapshotApproved
TelemetryReadingQualified
TelemetryReadingApproved
PlanRevisionApproved
DeviationDetected
AlarmRaised
AlarmAcknowledged
IncidentOpened
IncidentSeverityChanged
SimulationRunCompleted
LeakHypothesisCreated
MaintenanceActionScheduled
ConfigurationChangeApproved
ReportGenerated
RiskScoreRecalculated
```

Bad examples:

```text
DataChanged
EntityUpdated
StatusProcessed
ThingSaved
NotificationSentEvent
GenericDomainEvent
```

### 16.2 Event Metadata

Every published event MUST include:

- event id,
- event type,
- aggregate id or target reference,
- module owner,
- occurred time,
- actor reference where applicable,
- correlation id,
- causation id where applicable,
- source reference where imported,
- schema version.

### 16.3 Outbox

State change and event publication MUST be atomic through an outbox pattern where event publication crosses transaction boundaries.

Outbox records MUST support:

- idempotency,
- retry count,
- dispatch status,
- error reason,
- next retry time,
- correlation id,
- payload schema version.

---

## 17. Bounded-Context Blueprint

### 17.1 Foundation Plane

| Module | Owns | Must not own |
|---|---|---|
| `identity` | users, roles, permissions, authorities, authentication context | employee hierarchy, business assets |
| `organization` | employees, departments, regions, responsibility structure | login credentials, permission engine |
| `workflow` | tasks, approvals, delegations, escalations, decision state machines, approval evidence | target business facts |
| `audit` | append-only trace evidence, before/after evidence, correlation proof | business decisions |
| `topology` | pipelines, segments, nodes, stations, equipment, measurement locations, graph structure, versions, snapshots | telemetry readings, incidents |
| `telemetry` | sources, devices, telemetry points, readings, quality, trusted-reading lifecycle | incident lifecycle, planning targets |

### 17.2 Operations Plane

| Module | Owns | Must not own |
|---|---|---|
| `planning` | plans, periods, targets, revisions, approved expected state | raw telemetry ingestion |
| `monitoring` | thresholds, envelopes, deviations, anomaly candidates, monitored state | alarm lifecycle |
| `alarms` | alarm lifecycle, prioritization, shelving, acknowledgement, response evidence | threshold definition |
| `incidents` | severity, triage, timeline, RCA, remediation tracking, evidence bundle | monitoring rules |

### 17.3 Calculation and Safety Plane

| Module | Owns | Must not own |
|---|---|---|
| `simulation` | scenarios, runs, solver metadata, assumptions, benchmark traceability, results | topology ownership, live control |
| `leakdetection` | leak hypotheses, localization logic, hydraulic correlation evidence | generic incident ownership |
| `integrity` | degradation, condition assessment, structural risk indicators | maintenance execution |
| `assets` | inspections, work orders, maintenance execution, service history | integrity diagnosis |
| `hse` | PTW, safety obligations, compliance processes, HSE evidence | operational incident RCA ownership |

### 17.4 Enterprise and Intelligence Plane

| Module | Owns | Must not own |
|---|---|---|
| `custody` | metering validation, fiscal records, reconciliation, accounting evidence | telemetry ingestion |
| `integration` | connectors, mappings, retries, dead letters, jobs, external references | trusted domain truth |
| `notification` | channels, templates, routing, delivery status, retry evidence | workflow rules |
| `documents` | controlled files, procedures, P&IDs, certificates, attachments | domain state |
| `configuration` | governed setpoints, thresholds, coefficients, approval of runtime changes | monitoring evaluations |
| `analytics` | KPIs, trends, derived insights, anomaly candidates, historical read models | source-of-truth mutation |
| `reporting` | report definitions, runs, schedules, rendered report evidence | analytic calculation ownership |
| `risk` | cross-domain risk synthesis and scoring evidence | source domain mutation |
| `environment` | emissions, flare indicators, methane/environmental metrics | HSE workflow ownership |
| `otsecurity` | OT anomaly indicators, security posture, cyber event visibility | identity authorization, SCADA control logic |
| `agents` | explainable AI assistance and recommendation orchestration | autonomous final decision ownership |

---

## 18. Topology and Visualization Rules

Topology is the operational graph backbone of Hidra.

### 18.1 Topology Domain Responsibilities

The topology module MUST support:

- pipelines,
- segments,
- nodes,
- stations,
- equipment,
- measurement locations,
- adjacency relationships,
- graph snapshots,
- spatial references,
- topology versions,
- approved topology states.

### 18.2 Visualization Read Models

Topology visualization SHOULD be served through derived read models and projections, not direct domain aggregate exposure.

Expected projections include:

```text
topology graph read model
pipeline segment adjacency view
station-node-equipment hierarchy view
measurement point overlay view
telemetry-on-topology projection
alarm-on-topology projection
deviation-on-topology projection
incident-on-topology projection
simulation-result overlay
risk heat-map projection
integrity condition overlay
maintenance activity overlay
```

### 18.3 Overlay Ownership

Overlay source ownership remains with the source module.

Examples:

- telemetry owns telemetry readings,
- monitoring owns deviations,
- alarms owns alarm lifecycle,
- incidents owns incidents,
- simulation owns simulation results,
- risk owns risk synthesis.

Topology visualization may compose these overlays but MUST NOT become owner of their source facts.

---

## 19. Sonatrach Operational Alignment

Hidra must remain focused on Sonatrach pipeline operations.

The micro architecture MUST favor:

- topology-bound telemetry,
- workflow-governed validation of operational facts,
- planning-versus-actual supervision,
- deviations, alarms, incidents, and risk visibility,
- simulation tied to approved topology and trusted telemetry,
- controlled ingestion from SCADA and historians,
- auditable decision trails for operational and engineering actions,
- multilingual business language through governed catalogs.

It MUST reject unrelated enterprise patterns that dilute hydrocarbon operational intelligence.

---

## 20. Backend Hardening Mandates

The legacy HyFlo build history exposed issues that must not be repeated:

- missing shared exception classes,
- cross-module DTO leakage,
- unstable facade coupling,
- generic service overreach,
- audit model instability,
- weak package governance,
- accidental shared-kernel expansion,
- unclear ownership of validation and audit evidence.

Therefore the following mandates are immediate:

- No cross-module DTO imports except approved public application DTOs.
- No generic `GenericService`, `BaseService`, `CrudService`, or ungoverned `Facade` in business modules.
- No shared exception dumping ground.
- No direct repository access from controllers.
- No domain imports of JPA or Spring.
- No persistence entity sharing across modules.
- No generic global mapper package.
- No accidental extension of legacy `dz.sh.trc.hyflo` design into Hidra.
- No module may define business truth for another module.

---

## 21. Exception Strategy

Hidra standardizes exception ownership aggressively.

Rules:

- domain exceptions live in `domain.exception`,
- application may translate failures for use-case semantics,
- platform owns HTTP problem serialization,
- security exceptions belong to centralized platform security contracts,
- integration exceptions belong to the integration module or adapter boundary,
- no vague shared exception package may define business meaning.

Minimum categories:

```text
DomainRuleViolationException
AggregateNotFoundException
InvalidStateTransitionException
AuthorizationDeniedException
ExternalImportValidationException
IntegrationMappingException
IdempotencyConflictException
ConfigurationNotApprovedException
WorkflowDecisionRequiredException
AuditAppendFailedException
TopologySnapshotNotApprovedException
TelemetryQualityRejectedException
```

### 21.1 HTTP Error Mapping

HTTP serialization belongs to platform API error handling.

Domain exceptions MUST NOT know HTTP status codes.

Recommended mapping:

| Exception | HTTP status |
|---|---|
| `AggregateNotFoundException` | 404 |
| `DomainRuleViolationException` | 422 |
| `InvalidStateTransitionException` | 409 |
| `AuthorizationDeniedException` | 403 |
| `IdempotencyConflictException` | 409 |
| `ExternalImportValidationException` | 422 |
| `ConfigurationNotApprovedException` | 409 |

---

## 22. Testing Strategy

Each module SHOULD have tests aligned with layers.

```text
<module>
  domain tests
  application tests
  adapter tests
  api tests
  architecture tests
```

### 22.1 Domain Tests

Domain tests verify:

- invariants,
- lifecycle transitions,
- value object validation,
- domain policies,
- emitted domain events.

Domain tests MUST NOT require Spring.

### 22.2 Application Tests

Application tests verify:

- use-case orchestration,
- authorization decisions,
- workflow interactions,
- audit append calls,
- idempotency behavior,
- output port usage.

### 22.3 Adapter Tests

Adapter tests verify:

- persistence mappings,
- repository behavior,
- external client mapping,
- messaging behavior,
- outbox dispatch behavior.

### 22.4 Architecture Tests

Architecture tests verify dependency rules and package rules.

They are mandatory for CI.

---

## 23. ArchUnit Rule Catalog

The following rules SHOULD be implemented as automated ArchUnit tests.

### 23.1 Domain Independence

```java
noClasses()
    .that().resideInAPackage("..domain..")
    .should().dependOnClassesThat()
    .resideInAnyPackage(
        "org.springframework..",
        "jakarta.persistence..",
        "javax.persistence..",
        "org.hibernate.."
    );
```

### 23.2 API Must Not Access Persistence

```java
noClasses()
    .that().resideInAPackage("..api..")
    .should().dependOnClassesThat()
    .resideInAnyPackage("..infrastructure.persistence..", "jakarta.persistence..", "javax.persistence..");
```

### 23.3 Controllers Call Input Ports Only

```java
classes()
    .that().resideInAPackage("..api.rest..")
    .and().haveSimpleNameEndingWith("Controller")
    .should().onlyDependOnClassesThat()
    .resideOutsideOfPackages("..infrastructure..", "..domain.model..", "..domain.service..");
```

### 23.4 No Infrastructure Imports Across Modules

```java
noClasses()
    .that().resideInAPackage("dz.sh.hidra.(*)..")
    .should().dependOnClassesThat()
    .resideInAPackage("dz.sh.hidra.(*)..infrastructure..");
```

Actual implementation may require custom predicates to compare module names.

### 23.5 Forbidden Package Names

```java
noClasses()
    .should().resideInAnyPackage(
        "..shared..",
        "..common..",
        "..utils..",
        "..helpers..",
        "..misc..",
        "..generic.."
    );
```

### 23.6 No Generic Services

```java
noClasses()
    .should().haveSimpleNameMatching(".*(GenericService|BaseService|CrudService|CommonFacade|BaseManager|CoreHelper).*");
```

### 23.7 Persistence Entities Stay Private

```java
noClasses()
    .that().resideOutsideOfPackage("..infrastructure.persistence..")
    .should().dependOnClassesThat()
    .resideInAPackage("..infrastructure.persistence.entity..");
```

---

## 24. CI Enforcement

The build pipeline MUST include:

- compilation,
- unit tests,
- architecture tests,
- database migration validation,
- dependency convergence checks,
- forbidden package checks,
- forbidden import checks,
- static analysis,
- secret scanning,
- test coverage reporting.

A pull request that violates mandatory architecture rules SHOULD fail CI.

---

## 25. ADR Exception Process

Some rules allow ADR-approved exceptions.

An ADR is required for:

- cross-module foreign keys,
- public contract expansion,
- new shared kernel primitive,
- new platform abstraction,
- dependency not listed in the dependency matrix,
- extraction of a module from the monolith,
- introduction of a new integration protocol,
- direct synchronous dependency between later-phase modules,
- module schema mutation by another module,
- deviation from canonical package structure.

Each ADR MUST include:

- context,
- decision,
- alternatives considered,
- affected modules,
- dependency impact,
- schema impact,
- operational risk,
- rollback strategy,
- enforcement update if applicable.

---

## 26. Phase Enforcement

### Phase 1 — Data Trust

Focus only on:

```text
kernel
platform
identity
organization
workflow
audit
topology
telemetry
```

Phase 1 must establish:

- canonical package structure,
- identity and actor context,
- organization scope,
- topology graph foundation,
- telemetry source/point binding,
- workflow-governed validation,
- audit append readiness,
- architecture tests.

### Phase 2 — Live Operations

Add:

```text
planning
monitoring
alarms
incidents
configuration basic
notification basic
operational read views
```

### Phase 3 — Safety, Simulation, Integrity

Add:

```text
simulation
leakdetection
integrity
hse
assets
documents basic
```

### Phase 4 — Commercial and Integration

Add:

```text
custody
integration industrialization
documents full
configuration full
```

### Phase 5 — Intelligence and Compliance

Add:

```text
analytics
reporting
risk mature
environment
otsecurity
agents
```

Later-phase modules may exist structurally earlier, but they MUST NOT bypass earlier maturity gates.

---

## 27. Documentation and Module Annex Rules

Every active module SHOULD have a module annex under `docs/architecture/modules/<module>.md`.

A module annex MUST define:

- module purpose;
- owned business language;
- owned aggregates;
- owned schema or table namespace;
- public input ports;
- public events;
- accepted inbound events;
- projections and read models;
- workflow touchpoints;
- audit evidence produced by state-changing use cases;
- integration import/export ports where applicable;
- forbidden responsibilities;
- known ADR exceptions.

A module annex MUST NOT duplicate the entire macro or micro architecture. It exists to specialize this standard for one bounded context.

### 27.1 Documentation Naming

Recommended document names:

```text
docs/architecture/HIDRA-ARCH-MACRO.md
docs/architecture/HIDRA-ARCH-MICRO.md
docs/architecture/modules/HIDRA-MODULE-TOPOLOGY.md
docs/architecture/modules/HIDRA-MODULE-TELEMETRY.md
docs/adr/ADR-0001-<decision-title>.md
```

### 27.2 Documentation Ownership

Architecture documentation is not decorative. A module implementation that contradicts its architecture annex MUST be corrected or accompanied by an approved ADR.

---

## 28. Implementation Sequencing Rules

Hidra implementation MUST follow the architecture sequence rather than isolated CRUD convenience.

### 28.1 First Executable Slice

The first executable slice SHOULD prove:

```text
identity actor -> topology asset -> telemetry point -> reading ingestion -> validation workflow -> trusted reading -> audit record -> outbox event -> read projection
```

This slice validates the most important architectural chain before adding advanced operations.

### 28.2 Early Modules May Exist Structurally

Later-phase modules MAY exist as package skeletons before they are functionally active. They MUST NOT:

- own source-of-truth state prematurely;
- bypass topology or telemetry trust gates;
- publish operational claims without audit evidence;
- simulate against unapproved topology snapshots;
- provide analytics from untrusted readings;
- introduce integration writes before import/export ports are approved.

### 28.3 Minimal Acceptable Module Skeleton

A new module skeleton MAY contain package declarations, `package-info.java`, configuration placeholders, and documentation. It MUST NOT contain fake generic services, placeholder CRUD controllers, fake repositories, or domain objects without real ownership rules.

### 28.4 Definition of Architecture Done

A module is architecture-ready only when:

- public contracts are named;
- private packages are isolated;
- schema ownership is defined;
- migrations are owned;
- audit evidence for state changes is specified;
- events are named using business language;
- ArchUnit rules cover its forbidden dependencies;
- ADR exceptions, if any, are recorded.

---

## 29. Module Extraction Rules

Hidra is modular monolith first.

A module MAY be extracted only when at least one of the following becomes materially true:

- distinct scaling profile,
- independent operational lifecycle,
- isolated team ownership,
- strict security isolation requirement,
- protocol-specific runtime needs,
- unacceptable release coupling.

Likely future extraction candidates:

```text
integration
simulation
analytics
reporting
agents
```

Extraction MUST preserve:

- bounded-context ownership,
- explicit contracts,
- auditability,
- idempotency,
- workflow integrity,
- observability,
- and source-of-truth boundaries.

---

## 30. Final Decision Test

Every backend decision must pass this test:

> Does this strengthen Hidra as a trusted, validated, auditable, topology-aware, workflow-governed, risk-aware operational intelligence platform for hydrocarbon transportation systems?

If yes, it belongs.

If not, it is premature, misplaced, unsafe, or out of scope.

---

## 31. Summary

HidraAPI is a modular monolith with strict internal boundaries.

Its micro architecture is built around:

- explicit bounded contexts,
- package-level discipline,
- build-level dependency control,
- topology as operational graph backbone,
- workflow as governance,
- audit as append-only evidence,
- integration as controlled gateway,
- simulation as explainable calculation,
- analytics and AI as derived decision support,
- and CI-enforced architectural rules.

The implementation direction remains fixed:

```text
facts first -> supervision second -> calculation third -> integration fourth -> intelligence last
```

That order is the engineering guardrail of HidraAPI.
