# Hidra Micro Architecture v1.2

## 1. Document Status

- Product: Hidra – Hydrocarbon Intelligence for Data, Risk, and Analytics
- Document type: Micro architecture
- Version: 1.2
- Status: For discussion / baseline for implementation
- Target repository: HidraAPI

## 2. Purpose

The micro architecture defines the internal design of each bounded context in Hidra.

It specifies:

- Aggregates, entities, and value objects.
- Domain services and domain policies.
- Application services, commands, queries, and DTOs.
- Ports and adapters.
- Domain and integration events.
- Validation rules and invariants.
- Package and dependency structure.
- Database ownership and API ownership.
- Testing responsibilities.

## 3. Canonical Package Root

The canonical Java package root for HidraAPI is:

- `dz.sh.hidra`

Forbidden or discouraged roots:

- `dz.sonatrach.` (previous conceptual namespace, not used in implementation).
- Top-level `sharedkernel`, `common`, `core`, `utils` as generic dumping grounds.

All production code must live under `dz.sh.hidra` with clear module boundaries.

## 4. Repository Package Structure

HidraAPI uses the following top-level structure under `dz.sh.hidra`:

- `dz.sh.hidra.bootstrap`  
  Application entrypoint and startup wiring.

- `dz.sh.hidra.modules.<context>`  
  Business bounded contexts (identity, organization, topology, telemetry, planning, monitoring, incidents, workflow, audit, integration, analytics, reporting, notification).

- `dz.sh.hidra.platform`  
  Technical platform: configuration, security, exception handling, observability, persistence configuration, outbox infrastructure.

- `dz.sh.hidra.kernel`  
  Shared kernel: stable primitives reused across contexts.

Each business module follows a consistent internal structure to enforce separation of API, application, domain, and infrastructure.

## 5. Standard Module Structure

Within each module `dz.sh.hidra.modules.<context>`, the standard structure is:

- `api`  
  - `rest` – REST controllers and route definitions.  
  - `request` – REST request DTOs.  
  - `response` – REST response DTOs.  
  - `mapper` – Mappers between API DTOs and application DTOs.

- `application`  
  - `command` – Command types initiating state changes.  
  - `query` – Query types for reading data.  
  - `dto` – Application-level DTOs.  
  - `port.in` – Inbound ports (use case interfaces) used by controllers.  
  - `port.out` – Outbound ports (interfaces for persistence and external systems).  
  - `service` – Application services implementing inbound ports.  
  - `mapper` – Mappers between domain models and application DTOs.  
  - `exception` – Application-specific exceptions.

- `domain`  
  - `model` – Aggregates, entities, value objects.  
  - `event` – Domain events raised by aggregates.  
  - `policy` – Domain policies and business rules.  
  - `service` – Domain services that implement business logic independent of infrastructure.  
  - `exception` – Domain-specific exceptions and rule violations.

- `infrastructure`  
  - `persistence` – JPA entities, persistence mappers, repositories, database adapters.  
  - `adapter` – Adapters for outbound ports (integration, messaging, external systems).  
  - `configuration` – Module-specific Spring configuration and wiring.

The intent is:

- Controllers depend only on inbound ports and API DTOs.
- Application services depend on domain models and outbound ports.
- Domain models do not depend on Spring or persistence.
- Infrastructure implements outbound ports and integrates with Spring/JPA/other frameworks.

## 6. Shared Kernel

### 6.1 Purpose

The shared kernel `dz.sh.hidra.kernel` provides stable primitives with cross-context meaning:

- Base identifier types.
- Quantity and measurement value objects.
- Result and error primitives.
- Domain event marker interfaces and metadata primitives.
- Correlation ID and request ID value objects.
- Pagination and sorting primitives.

These types are stable and rarely change; they are reused across modules.

### 6.2 Allowed Content

Allowed in `dz.sh.hidra.kernel`:

- Value objects with stable meaning (e.g., CorrelationId, RequestId).
- Result wrappers and error representations shared across modules.
- Domain event base classes and interfaces.
- Pagination types (PageRequest, PageResult).

### 6.3 Forbidden Content

Forbidden in the shared kernel:

- Business aggregates or JPA entities.
- Controller classes or REST DTOs.
- Module-specific domain services, policies, or rules.
- Ad-hoc utilities with unclear ownership.

If a primitive is not truly cross-context and stable, it does not belong in the kernel.

## 7. Identity Context

### 7.1 Purpose

The identity context manages authentication and authorization:

- Users, roles, permissions, authorities, groups.
- User lifecycle: creation, activation, deactivation.
- Role and permission assignment.
- Permission evaluation and security policies.

### 7.2 Aggregates and Entities

- Aggregates:  
  - `User`  
  - `Role`  
  - `Group`

- Entities:  
  - `Permission`  
  - `Authority`  
  - `UserCredentialReference`  
  - `UserRoleAssignment`  
  - `GroupMembership`

- Value objects:  
  - `UserId`, `RoleId`, `PermissionId`  
  - `AuthorityCode`  
  - `Username`, `EmailAddress`  
  - `UserStatus`

The identity domain owns the meaning of roles and permissions; other contexts may reference permissions by code, but do not redefine them.

### 7.3 Domain Events

Typical domain events:

- `UserCreatedEvent`
- `UserActivatedEvent`
- `UserDeactivatedEvent`
- `RoleAssignedToUserEvent`
- `PermissionGrantedEvent`

These events are used to update read models, synchronize with external IAM if needed, and audit security changes.

### 7.4 Ports and Policies

- Ports:  
  - `PasswordEncoderPort` – abstraction for password hashing.  
  - `IdentityProviderPort` – abstraction around external IAM integration.  
  - `CurrentActorPort` – provides current authenticated actor.  
  - `AuthorizationPolicyPort` – central permission evaluation.

- Policies:  
  - Permission consistency policy.  
  - Segregation of duties policy.  
  - Organization scope policy (ties identity to organization units without direct imports from organization domain).

Controllers in identity call inbound ports; application services rely on these ports and domain models.

## 8. Organization Context

### 8.1 Purpose

The organization context models Sonatrach organizational structures and links operational actors to employees and units.

It answers:

- Who are the real operational people?
- Which organization unit do they belong to?
- Which position do they hold?
- Who reports to whom?
- How is the operational organization structured?
- Which station, region, division, department, team, or direction owns responsibility?

### 8.2 Aggregates and Entities

- Aggregates:  
  - `Employee`  
  - `OrganizationUnit`  
  - `PositionAssignment`

- Entities:  
  - `Department`  
  - `Region`  
  - `OperationalUnit`  
  - `SupervisorRelationship`

- Value objects:  
  - `EmployeeId`, `OrganizationUnitId`  
  - `DepartmentCode`, `RegionCode`, `PositionCode`  
  - `AssignmentPeriod`  
  - `OrganizationUnitType`  
  - `OperationalScopeReference`, `OperationalScopeType`

A station exists in two bounded contexts:

- `topology.Station` – physical/technical asset.  
- `organization.OrganizationUnit` with type `STATION` – people/responsibility structure.

Organization does not import topology domain classes; it uses `OperationalScopeReference` to point to topology elements by scope type and code.

### 8.3 Domain Events and Rules

- Events:  
  - `EmployeeRegisteredEvent`  
  - `EmployeeAssignedToUnitEvent`  
  - `SupervisorChangedEvent`

- Validation rules:  
  - One active primary assignment per employee.  
  - Supervisor must belong to a compatible structure.  
  - Assignment period must be valid and non-overlapping where required.  
  - Identity linkage (to user) must be unique and optional.  
  - Organization hierarchy cannot contain cycles.  
  - A disabled organization unit cannot receive new employee assignments.  
  - An employee cannot be their own supervisor.

Organization does not own login users, roles, or permissions.

## 9. Topology Context

### 9.1 Purpose

The topology context models the physical and operational pipeline network.

It owns:

- Pipeline networks and pipelines.
- Pipeline segments and topology versions.
- Stations and terminals.
- Equipment and measurement points.

### 9.2 Aggregates and Entities

- Aggregates:  
  - `PipelineNetwork`  
  - `Pipeline`  
  - `Station`  
  - `Equipment`  
  - `MeasurementPoint`

- Entities:  
  - `PipelineSegment`  
  - `Terminal`  
  - `Valve`  
  - `Pump`  
  - `Meter`  
  - `SensorLocation`  
  - `GeographicArea`

- Value objects:  
  - `PipelineId`, `PipelineCode`, `SegmentId`  
  - `StationCode`, `EquipmentCode`  
  - `GeoCoordinate`  
  - `OperationalStatus`, `LifecycleStatus`  
  - `TopologyVersion`

### 9.3 Events and Rules

- Events:  
  - `PipelineCreatedEvent`  
  - `PipelineSegmentAddedEvent`  
  - `StationRegisteredEvent`  
  - `EquipmentInstalledEvent`  
  - `MeasurementPointActivatedEvent`

- Validation rules:  
  - Pipeline code must be unique.  
  - A segment must belong to exactly one pipeline.  
  - A measurement point must reference valid equipment or location.  
  - Inactive topology cannot receive new active readings unless explicitly allowed by policy.  
  - Topology versioning must preserve history of changes.

Topology does not own telemetry values; it owns the physical structure onto which telemetry is mapped.

## 10. Telemetry Context

### 10.1 Purpose

The telemetry context captures, ingests, validates, and preserves operational measurement facts.

Telemetry records what happened; monitoring interprets whether it is normal, abnormal, or risky.

### 10.2 Aggregates and Entities

- Aggregates:  
  - `FlowReading`  
  - `SensorReading`  
  - `MeasurementBatch`

- Entities:  
  - `MeasurementSlot`  
  - `ReadingCorrection`  
  - `ReadingValidationAction`

- Value objects:  
  - `FlowReadingId`  
  - `MeasurementValue`, `MeasurementUnit`, `MeasurementQuality`  
  - `ReadingSource` (manual, SCADA, historian, etc.)  
  - `ReadingTimestamp`, `IngestionTimestamp`  
  - `ValidationStatus`  
  - `HistorianReference`, `ScadaReference`

Telemetry references topology and possibly organization via identifiers or neutral references; it does not import their domain models.

### 10.3 State Machine, Events, and Rules

State machine for `FlowReading`:

- `DRAFT` → `SUBMITTED` → `UNDER_REVIEW` → `VALIDATED` → `APPROVED`
- `REJECTED` and `CORRECTED` as side transitions, with clear rules.

Events:

- `FlowReadingSubmittedEvent`
- `FlowReadingValidatedEvent`
- `FlowReadingCorrectedEvent`
- `FlowReadingApprovedEvent`
- `FlowReadingRejectedEvent`

Validation rules:

- Reading timestamp cannot be in an impossible future.  
- Unit must match the measurement point type.  
- Correction must preserve original value history (no destructive update).  
- Approval requires an authorized actor and a valid workflow/compliance context.  
- Rejected reading must contain a reason.

## 11. Workflow Context

### 11.1 Purpose

The workflow context orchestrates validation and approval processes across multiple domains.

It owns the technical workflow machinery, not the business rules of telemetry, planning, incidents, or topology.

### 11.2 Aggregates and Entities

- Aggregates:  
  - `WorkflowDefinition`  
  - `WorkflowInstance`  
  - `WorkflowTask`

- Value objects:  
  - `WorkflowInstanceId`  
  - `WorkflowStatus`  
  - `TaskStatus`  
  - `ApprovalDecision`  
  - `TransitionReason`

### 11.3 Events and Rules

Events:

- `WorkflowStartedEvent`
- `WorkflowTaskAssignedEvent`
- `WorkflowActionCompletedEvent`
- `WorkflowEscalatedEvent`
- `WorkflowCompletedEvent`

Rules:

- Workflow must not embed business rules specific to telemetry, topology, incidents, or planning.  
- Workflow transitions must be auditable and explainable.  
- Workflows can be configured to drive validation lifecycles in telemetry and planning through well-defined interfaces and events.

## 12. Planning Context

### 12.1 Purpose

The planning context manages operational flow plans and targets.

It owns:

- Planning periods and operational targets.
- Plan versions and comparison rules.

### 12.2 Aggregates and Entities

- Aggregates:  
  - `FlowPlan`  
  - `PlanningPeriod`

- Value objects and entities:  
  - `FlowPlanId`  
  - `PlanningPeriodId`  
  - `TargetQuantity`  
  - `PlanStatus`  
  - `PlanVersionNumber`

### 12.3 Events and Rules

Events:

- `FlowPlanCreatedEvent`
- `FlowPlanSubmittedEvent`
- `FlowPlanApprovedEvent`
- `FlowPlanRevisedEvent`

Rules:

- Approved plans cannot be edited directly.  
- A revision always creates a new version.  
- Planned vs actual comparison uses only validated/approved telemetry readings.  
- Planning must not duplicate telemetry or topology models; it references them by IDs or references.

## 13. Monitoring Context

### 13.1 Purpose

The monitoring context interprets telemetry into operational states, deviations, alerts, and risk signals.

It owns:

- Monitoring rules and thresholds.
- State machine for operational status.

### 13.2 Aggregates, Value Objects, and Events

- Aggregates:  
  - `MonitoringRule`  
  - `Threshold`  
  - `OperationalState`

- Value objects:  
  - `ThresholdId`  
  - `Severity`  
  - `MonitoringStatus`  
  - `DeviationValue`  
  - `AcknowledgementStatus`  
  - `RiskLevel`

- Events:  
  - `ThresholdExceededEvent`  
  - `OperationalStateChangedEvent`  
  - `AlertRaisedEvent`  
  - `AlertAcknowledgedEvent`  
  - `RiskSignalDetectedEvent`

Monitoring consumes telemetry and topology references to determine current risk and deviations, but does not store full copies of telemetry or topology.

## 14. Incidents Context

### 14.1 Purpose

The incidents context manages the lifecycle of operational incidents from detection to closure.

It owns:

- Incident definitions and classifications.
- Response actions and impact assessments.
- Root cause analysis and resolution.

### 14.2 Entities, Value Objects, and Events

- Entities/aggregates:  
  - `Incident`  
  - `IncidentTimelineEntry`  
  - `ResponseAction`  
  - `ImpactAssessment`  
  - `RootCauseAnalysis`  
  - `IncidentResolution`

- Value objects:  
  - `IncidentId`  
  - `IncidentSeverity`  
  - `IncidentStatus`  
  - `IncidentClassification`  
  - `RootCauseCode`

- Events:  
  - `IncidentOpenedEvent`  
  - `IncidentEscalatedEvent`  
  - `IncidentAssignedEvent`  
  - `IncidentResolvedEvent`  
  - `IncidentClosedEvent`

Incidents can be triggered by monitoring events and telemetry deviations and can feed analytics and risk views.

## 15. Audit Context

### 15.1 Purpose

The audit context provides append-only traceability for domain and security events.

It owns:

- Canonical audit event records.
- Structures for actor, target, action, decision, and context.

### 15.2 Value Objects

- `AuditEventId`  
- `AuditActor`  
- `AuditTarget`  
- `AuditAction`  
- `AuditDecision`  
- `CorrelationId`  
- `RequestId`

Audit events are immutable and never updated in place. They may be linked to domain events, workflow actions, security decisions, and user interactions.

## 16. Integration Context

### 16.1 Purpose

The integration context owns external connectors and synchronization rules.

It handles:

- Inbound ingestion from external systems (SCADA, historians, files, etc.).
- Outbound publication to external systems (notification channels, analytics platforms, etc.).

### 16.2 Entities and Rules

- Entities:  
  - `MappingRule`  
  - `SynchronizationState`  
  - `RetryPolicy`  
  - `DeadLetterRecord`

- Rules:  
  - External failures must not corrupt domain state.  
  - Raw external references must be preserved and traceable.  
  - Retry policies must be explicit and configurable.  
  - Failed messages must be traceable, with clear dead-letter management.

Integration implements outbound ports for other modules; the modules themselves remain ignorant of external protocol details.

## 17. Analytics and Reporting Contexts

### 17.1 Capabilities

Analytics and reporting contexts provide:

- KPI projections and dashboards.
- Trend analysis and planned vs actual reports.
- Incident and validation performance reports.
- Operational intelligence views.
- Risk analytics and digital twin readiness projections.

They consume:

- Transactional data (telemetry, plans, incidents).
- Audit data.
- Monitoring and incident events.

### 17.2 Rules

- Analytics and reporting do not modify source-of-truth transactional state.  
- Projections and reporting views can be fully rebuilt from transactional and audit data.  
- Dashboards must clearly indicate data freshness and validation status.  
- Unvalidated data must be clearly marked or excluded from risk and compliance views.  
- Risk analytics must remain explainable and auditable, using domain events and audit trails as evidence.