# Hidra Micro Architecture

## 1. Document Status

| Field | Value |
|---|---|
| Product | Hidra |
| Meaning | Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Document type | Micro architecture |
| Version | 0.1 Draft |
| Status | For discussion |
| Target repository | HidraAPI |

---

## 2. Purpose

The micro architecture defines the internal design of each bounded context in Hidra.

It describes:

- aggregates
- entities
- value objects
- domain services
- application services
- commands
- queries
- ports
- adapters
- events
- validation rules
- package structure
- database ownership
- API ownership
- tests

---

## 3. Canonical Package Root

```text
dz.sonatrach.hidra
```

---

## 4. Repository Package Structure

```text
dz.sonatrach.hidra
├── bootstrap
├── modules
│   ├── identityaccess
│   ├── organization
│   ├── topology
│   ├── telemetry
│   ├── planning
│   ├── monitoring
│   ├── incidents
│   ├── workflow
│   ├── audit
│   ├── integration
│   ├── analytics
│   ├── notification
│   └── reporting
├── platform
│   ├── configuration
│   ├── exception
│   ├── observability
│   ├── security
│   ├── tenancy
│   ├── events
│   └── persistence
└── sharedkernel
    ├── domain
    ├── application
    └── api
```

---

## 5. Standard Module Structure

Each business module should follow:

```text
api
├── rest
│   ├── controller
│   ├── request
│   ├── response
│   └── mapper

application
├── command
├── query
├── dto
├── port
│   ├── in
│   └── out
├── service
└── mapper

domain
├── model
├── value
├── event
├── policy
├── service
├── repository
└── exception

infrastructure
├── persistence
│   ├── entity
│   ├── mapper
│   └── repository
├── adapter
└── configuration
```

---

## 6. Shared Kernel

### Purpose

Provide stable primitives used across contexts.

### Allowed Content

- base identifier types
- stable quantity/value primitives
- result/error primitives
- domain event marker interfaces
- correlation ID value
- request ID value
- actor reference value
- pagination primitives

### Forbidden Content

- business aggregates
- JPA entities
- controller classes
- module-specific DTOs
- utility dumping ground
- module-specific domain services

### Example Value Objects

- `CorrelationId`
- `RequestId`
- `ActorId`
- `OrganizationScopeId`
- `MeasurementUnitCode`
- `Quantity`

---

## 7. Identity & Access Context

### Purpose

Manage authentication identity, roles, permissions, authorities, groups, and authorization readiness.

### Business Capabilities

- create user
- activate/deactivate user
- assign roles
- assign permissions
- manage groups
- evaluate permissions
- support organization-scoped access
- support segregation of duties

### Aggregates

- `User`
- `Role`
- `Group`

### Entities

- `Permission`
- `Authority`
- `UserCredentialReference`
- `UserRoleAssignment`
- `GroupMembership`

### Value Objects

- `UserId`
- `RoleId`
- `PermissionId`
- `AuthorityCode`
- `Username`
- `EmailAddress`
- `UserStatus`

### Domain Events

- `UserCreatedEvent`
- `UserActivatedEvent`
- `UserDeactivatedEvent`
- `RoleAssignedToUserEvent`
- `PermissionGrantedEvent`

### Policies

- permission consistency policy
- segregation of duties policy
- organization scope policy

### Ports

- `PasswordEncoderPort`
- `IdentityProviderPort`
- `CurrentActorPort`
- `AuthorizationPolicyPort`

### Tests

- role assignment tests
- permission evaluation tests
- user lifecycle tests
- security policy tests
- controller authorization tests

---

## 8. Organization Context

### Purpose

Model Sonatrach organizational structures and link operational actors to employees and units.

### Business Capabilities

- manage employees
- manage departments
- manage regions
- manage operational units
- assign positions
- define supervisor relationships
- link employee to user identity

### Aggregates

- `Employee`
- `OrganizationUnit`
- `PositionAssignment`

### Entities

- `Department`
- `Region`
- `OperationalUnit`
- `SupervisorRelationship`

### Value Objects

- `EmployeeId`
- `OrganizationUnitId`
- `DepartmentCode`
- `RegionCode`
- `PositionCode`
- `AssignmentPeriod`

### Events

- `EmployeeRegisteredEvent`
- `EmployeeAssignedToUnitEvent`
- `SupervisorChangedEvent`

### Validation Rules

- one active primary assignment per employee
- supervisor must belong to compatible structure
- assignment period must be valid
- identity linkage must be unique

---

## 9. Topology Context

### Purpose

Model the physical and operational pipeline network.

### Business Capabilities

- manage infrastructure
- manage pipelines
- manage pipeline segments
- manage stations
- manage terminals
- manage equipment
- manage measurement points
- manage topology lifecycle and versioning

### Aggregates

- `PipelineNetwork`
- `Pipeline`
- `Station`
- `Equipment`
- `MeasurementPoint`

### Entities

- `PipelineSegment`
- `Terminal`
- `Valve`
- `Pump`
- `Meter`
- `SensorLocation`
- `GeographicArea`

### Value Objects

- `PipelineId`
- `PipelineCode`
- `SegmentId`
- `StationCode`
- `EquipmentCode`
- `GeoCoordinate`
- `OperationalStatus`
- `LifecycleStatus`
- `TopologyVersion`

### Events

- `PipelineCreatedEvent`
- `PipelineSegmentAddedEvent`
- `StationRegisteredEvent`
- `EquipmentInstalledEvent`
- `MeasurementPointActivatedEvent`

### Validation Rules

- pipeline code must be unique
- segment must belong to one pipeline
- measurement point must reference valid equipment or location
- inactive topology cannot receive new active readings unless explicitly allowed

---

## 10. Telemetry Context

### Purpose

Capture, ingest, validate, and preserve operational measurement facts.

Telemetry records what happened. Monitoring interprets whether it is normal or risky.

### Business Capabilities

- submit manual flow reading
- ingest sensor reading
- normalize measurement
- assign measurement quality
- validate reading
- correct reading
- approve/reject reading
- link to historian or SCADA reference

### Aggregates

- `FlowReading`
- `SensorReading`
- `MeasurementBatch`

### Entities

- `MeasurementSlot`
- `ReadingCorrection`
- `ReadingValidationAction`

### Value Objects

- `FlowReadingId`
- `MeasurementValue`
- `MeasurementUnit`
- `MeasurementQuality`
- `ReadingSource`
- `ReadingTimestamp`
- `IngestionTimestamp`
- `ValidationStatus`
- `HistorianReference`
- `ScadaReference`

### State Machine

```text
DRAFT
→ SUBMITTED
→ UNDER_REVIEW
→ VALIDATED
→ APPROVED
→ REJECTED
→ CORRECTED
→ SUPERSEDED
```

### Events

- `FlowReadingSubmittedEvent`
- `FlowReadingValidatedEvent`
- `FlowReadingCorrectedEvent`
- `FlowReadingApprovedEvent`
- `FlowReadingRejectedEvent`

### Validation Rules

- reading timestamp cannot be in impossible future
- unit must match measurement point type
- correction must preserve original value history
- approval requires authorized actor
- rejected reading must contain reason

---

## 11. Workflow Context

### Purpose

Orchestrate validation, approval, rejection, escalation, and delegation flows.

Workflow orchestrates process. Domains keep their business rules.

### Aggregates

- `WorkflowDefinition`
- `WorkflowInstance`
- `WorkflowTask`

### Entities

- `WorkflowAction`
- `ApprovalStep`
- `Delegation`
- `EscalationRule`

### Value Objects

- `WorkflowInstanceId`
- `WorkflowStatus`
- `TaskStatus`
- `ApprovalDecision`
- `TransitionReason`

### Events

- `WorkflowStartedEvent`
- `WorkflowTaskAssignedEvent`
- `WorkflowActionCompletedEvent`
- `WorkflowEscalatedEvent`
- `WorkflowCompletedEvent`

### Rules

- no approval without actor traceability
- rejected action must include reason
- escalation must preserve original assignee
- workflow cannot bypass domain authorization policy

---

## 12. Planning Context

### Purpose

Define operational targets and compare actual validated flow against expected plans.

### Aggregates

- `FlowPlan`
- `PlanningPeriod`

### Entities

- `OperationalTarget`
- `PlanVersion`
- `PlanApproval`

### Value Objects

- `FlowPlanId`
- `PlanningPeriodId`
- `TargetQuantity`
- `PlanStatus`
- `PlanVersionNumber`

### Events

- `FlowPlanCreatedEvent`
- `FlowPlanSubmittedEvent`
- `FlowPlanApprovedEvent`
- `FlowPlanRevisedEvent`

### Rules

- approved plan cannot be edited directly
- revision creates new version
- actual comparison uses validated/approved readings only

---

## 13. Monitoring Context

### Purpose

Evaluate operational state using validated telemetry, thresholds, monitoring rules, and risk indicators.

### Aggregates

- `MonitoringRule`
- `Threshold`
- `OperationalState`

### Entities

- `AlertRule`
- `EscalationPolicy`
- `AnomalySignal`
- `RiskSignal`

### Value Objects

- `ThresholdId`
- `Severity`
- `MonitoringStatus`
- `DeviationValue`
- `AcknowledgementStatus`
- `RiskLevel`

### Events

- `ThresholdExceededEvent`
- `OperationalStateChangedEvent`
- `AlertRaisedEvent`
- `AlertAcknowledgedEvent`
- `RiskSignalDetectedEvent`

### Rules

- monitoring must consume trusted telemetry
- threshold rule must define unit and scope
- alert severity must be deterministic
- risk level must be explainable
- acknowledgement must track actor and time

---

## 14. Incidents Context

### Purpose

Manage operational incidents from detection to closure.

### Aggregates

- `Incident`

### Entities

- `IncidentTimelineEntry`
- `ResponseAction`
- `ImpactAssessment`
- `RootCauseAnalysis`
- `IncidentResolution`

### Value Objects

- `IncidentId`
- `IncidentSeverity`
- `IncidentStatus`
- `IncidentClassification`
- `RootCauseCode`

### Events

- `IncidentOpenedEvent`
- `IncidentEscalatedEvent`
- `IncidentAssignedEvent`
- `IncidentResolvedEvent`
- `IncidentClosedEvent`

### Rules

- incident cannot close without resolution
- root cause may be required depending on severity
- incident must maintain timeline
- incident should link to alerts, telemetry, topology, and responsible actors

---

## 15. Audit Context

### Purpose

Record traceable evidence of operational and security actions.

### Aggregates

- `AuditEvent`

### Value Objects

- `AuditEventId`
- `AuditActor`
- `AuditTarget`
- `AuditAction`
- `AuditDecision`
- `CorrelationId`
- `RequestId`

### Records

- user action audit
- domain state change audit
- security decision audit
- workflow decision audit
- integration event audit

### Rules

- audit is append-only
- audit must not drive business workflow
- audit must record actor and target
- sensitive data must be masked
- hash-chain readiness should be considered for future compliance

---

## 16. Integration Context

### Purpose

Manage connections to external systems.

### Aggregates

- `ExternalSystem`
- `ConnectorConfiguration`
- `IngestionJob`

### Entities

- `MappingRule`
- `SynchronizationState`
- `RetryPolicy`
- `DeadLetterRecord`

### Value Objects

- `ExternalSystemId`
- `ConnectorType`
- `ExternalReference`
- `SyncStatus`

### Events

- `IngestionJobStartedEvent`
- `IngestionJobCompletedEvent`
- `IngestionJobFailedEvent`
- `ExternalSystemRegisteredEvent`

### Rules

- external failure must not corrupt domain state
- raw external references must be preserved
- retry policy must be explicit
- failed messages must be traceable

---

## 17. Analytics and Reporting Contexts

### Purpose

Create derived insight from trusted operational data.

### Capabilities

- KPI projections
- dashboards
- trend analysis
- planned vs actual reports
- incident reports
- validation performance reports
- operational intelligence views
- risk analytics
- digital twin readiness projections

### Rules

- analytics does not modify source-of-truth state
- projections can be rebuilt
- dashboards must indicate data freshness
- unvalidated data must be clearly marked or excluded
- risk analytics must remain explainable

---

## 18. Notification Context

### Purpose

Deliver operational notifications through configured channels.

### Capabilities

- alert notification
- workflow task notification
- incident escalation notification
- email/SMS/future channel readiness
- notification templates
- delivery tracking

### Rules

- notification does not own alert or incident rules
- failed delivery must be traceable
- sensitive data must be minimized

---

## 19. Java Header Standard

Every Java file must start with:

```java
/*
 * Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * Copyright (c) Sonatrach.
 *
 * Module: <module-name>
 * Bounded Context: <bounded-context>
 * Layer: <api|application|domain|infrastructure|platform|sharedkernel>
 * Responsibility: <one-sentence responsibility>
 *
 * This file is part of the Hidra clean architecture implementation.
 */
```

---

## 20. JavaDoc Standard

Every public class must include:

```java
/**
 * <One-sentence summary of the class responsibility.>
 *
 * <p>Business role:
 * <Explain the business purpose in Sonatrach pipeline operations.>
 *
 * <p>Architecture role:
 * <Explain whether this is domain, application, API, infrastructure, platform, or shared-kernel code.>
 *
 * <p>Usage:
 * <Explain how this class should be used and what should not depend on it.>
 */
```

---

## 21. Validation Standards

### API Validation

- DTOs use Bean Validation.
- Controllers use `@Valid`.
- Nested DTOs use `@Valid`.
- Services do not duplicate DTO validation.

### Domain Validation

- Aggregates protect invariants.
- Value objects reject invalid construction.
- Lifecycle transitions are controlled.
- Domain exceptions represent business rule violations.

### Custom Validators

Potential custom validators:

- pipeline code
- station code
- equipment code
- measurement unit
- reading quality
- operational period
- approval transition
- organization assignment
- permission consistency

---

## 22. Testing Standards

Required test types:

- domain tests
- application service tests
- API/controller tests
- persistence tests
- integration tests
- security tests
- workflow state-machine tests
- telemetry ingestion tests
- audit tests
- architecture tests
- migration tests

Required validation commands:

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q test -Dtest=*ArchitectureTest
mvn -q flyway:validate
mvn -q clean verify
```

---

## 23. Architecture Validation Rules

ArchUnit rules should enforce:

- no controller accesses repository directly
- no domain depends on infrastructure
- no domain depends on Spring Web
- no application depends on API layer
- no cross-module entity sharing
- infrastructure implements ports
- sharedkernel contains no business aggregate
- no field injection
- no classes named `Manager`, `Helper`, `Util`, or `Common` unless justified
- no top-level technical modules outside approved structure

---

## 24. First Implementation Order

Recommended order:

1. repository skeleton
2. sharedkernel
3. platform foundation
4. identityaccess
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

---

## 25. Open Discussion Questions

1. Should alerts be part of monitoring or a separate bounded context?
2. Should audit be a bounded context or platform module?
3. Should telemetry validation live fully inside telemetry, or be orchestrated by workflow?
4. Which fields from HyFloAPI are business-critical and must be preserved exactly?
5. Which NGHyFloAPI standards are mature enough to reuse without modification?
6. Should reporting and analytics be separate modules or one context at first?
7. What is the first minimum useful operational workflow?
8. Should risk be a separate bounded context later, or a cross-cutting analytical concern?
