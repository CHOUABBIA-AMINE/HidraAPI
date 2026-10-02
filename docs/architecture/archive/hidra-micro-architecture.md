# Hidra Micro Architecture

## 1. Document Status

| Field | Value |
|---|---|
| Product | Hidra |
| Meaning | Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Document type | Micro architecture |
| Version | 0.2 Draft |
| Status | For discussion |
| Target repository | HidraAPI |
| Canonical package root | `dz.sh.hidra` |
| Foundation module name | `kernel` |
| Identity module name | `identity` |

---

## 2. Purpose

The micro architecture defines the internal design of each bounded context in Hidra.

It describes:

```text
aggregates
entities
value objects
domain services
application services
commands
queries
ports
adapters
events
validation rules
package structure
database ownership
API ownership
tests
```

---

## 3. Canonical Package Root

```text
dz.sh.hidra
```


---

## 4. Repository Package Structure

```text
dz.sh.hidra
├── HidraApplication
├── kernel
│   ├── domain
│   ├── application
│   └── api
├── platform
│   ├── configuration
│   ├── exception
│   ├── observability
│   ├── security
│   ├── persistence
│   └── events
└── modules
    ├── identity
    ├── organization
    ├── topology
    ├── telemetry
    ├── workflow
    ├── planning
    ├── monitoring
    ├── incidents
    ├── audit
    ├── integration
    ├── analytics
    ├── reporting
    └── notification
```


---

## 5. Standard Module Structure

Each business module follows:

```text
api
└── rest
    ├── controller
    ├── request
    ├── response
    └── mapper

application
├── command
├── query
├── dto
├── port
│   ├── in
│   └── out
└── service

domain
├── model
├── value
├── event
├── policy
├── service
└── exception

infrastructure
├── configuration
├── persistence
│   ├── entity
│   ├── repository
│   ├── mapper
│   └── adapter
└── <external-module-adapter>
```

---

## 6. Kernel

### Purpose

The `kernel` module provides stable primitives used across contexts.

### Allowed content

```text
base identifier primitives
stable value primitives
result/error primitives
domain event marker interfaces
correlation id value
request id value
actor reference value
organization scope id value
pagination primitives
```

### Forbidden content

```text
business aggregates
JPA entities
controller classes
module-specific DTOs
utility dumping ground
module-specific domain services
```

### Example value objects

```text
CorrelationId
RequestId
ActorId
OrganizationScopeId
```

---

## 7. Identity Context

### Purpose

Manage authentication identity, users, roles, permissions, authorities, groups, and authorization readiness.

### Business capabilities

```text
create user
activate/deactivate user
assign roles
assign permissions
manage groups
evaluate permissions
support organization-scoped access
support segregation of duties
```

### Aggregates

```text
User
Role
Group
```

### Entities

```text
Permission
Authority
UserCredentialReference
UserRoleAssignment
GroupMembership
```

### Value objects

```text
UserId
RoleId
PermissionId
AuthorityCode
Username
EmailAddress
UserStatus
```

### Ports

```text
PasswordEncoderPort
IdentityProviderPort
CurrentActorPort
AuthorizationPolicyPort
```

---

## 8. Organization Context

### Purpose

Model Sonatrach organizational structures and link operational actors to employees and units.

### Business capabilities

```text
manage employees
manage departments
manage regions
manage operational units
assign positions
define supervisor relationships
link employee to user identity
```

### Aggregates

```text
Employee
OrganizationUnit
PositionAssignment
```

### Entities

```text
Department
Region
OperationalUnit
SupervisorRelationship
```

### Validation rules

```text
one active primary assignment per employee
supervisor must belong to compatible structure
assignment period must be valid
identity linkage must be unique
```

---

## 9. Topology Context

### Purpose

Model the physical and operational pipeline network.

### Business capabilities

```text
manage infrastructure
manage pipelines
manage pipeline segments
manage stations
manage terminals
manage equipment
manage measurement points
manage topology lifecycle and versioning
```

### Aggregates

```text
PipelineNetwork
Pipeline
Station
Equipment
MeasurementPoint
```

### Validation rules

```text
pipeline code must be unique
segment must belong to one pipeline
measurement point must reference valid equipment or location
inactive topology cannot receive new active readings unless explicitly allowed
```

---

## 10. Telemetry Context

### Purpose

Capture, ingest, validate, and preserve operational measurement facts.

Telemetry records what happened. Monitoring interprets whether it is normal or risky.

### Business capabilities

```text
submit manual flow reading
ingest sensor reading
normalize measurement
assign measurement quality
maintain reading validation state
correct reading
approve/reject reading through workflow decision result
link to historian or SCADA reference
```

### Aggregates

```text
TelemetrySource
TelemetryDevice
TelemetryPoint
TelemetryReading
TelemetryIngestionBatch
```

### Entities

```text
TelemetryPointBinding
ReadingCorrection
ReadingValidationAction
```

### Value objects

```text
TelemetryReadingId
TelemetryReadingValue
TelemetryUnitReference
TelemetryQualityCodeReference
TelemetryTimestamp
TelemetrySourceReference
HistorianReference
ScadaReference
TelemetryReadingState
```

### State machine

```text
RECEIVED
→ ACCEPTED
→ REJECTED
→ QUARANTINED
→ DUPLICATE
→ CORRECTED
→ SUPERSEDED
```

### Rules

```text
reading timestamp cannot be in impossible future
unit must match telemetry point type
correction must preserve original value history
approval requires authorized actor or workflow decision result
rejected reading must contain reason
business taxonomy is catalog-backed, not Java enum-backed
```

---

## 11. Workflow Context

### Purpose

Orchestrate validation, approval, rejection, correction request, escalation, and delegation flows.

Workflow orchestrates process. Domains keep their business rules.

### Aggregates

```text
WorkflowDefinition
WorkflowInstance
WorkflowTask
```

### Entities

```text
WorkflowAction
ApprovalStep
Delegation
EscalationRule
WorkflowComment
WorkflowStateHistory
```

### Value objects

```text
WorkflowInstanceId
WorkflowStatus
TaskStatus
ApprovalDecision
TransitionReason
WorkflowTargetReference
WorkflowActorReference
WorkflowReasonReference
```

### Events

```text
WorkflowStartedEvent
WorkflowTaskAssignedEvent
WorkflowActionCompletedEvent
WorkflowEscalatedEvent
WorkflowCompletedEvent
```

### Rules

```text
no approval without actor traceability
rejected action must include reason
escalation must preserve original assignee
workflow cannot bypass domain authorization policy
workflow does not own telemetry readings
workflow does not own topology assets
workflow emits audit-ready events but audit hardening comes later
```

---

## 12. Planning Context

### Purpose

Define operational targets and compare actual validated flow against expected plans.

### Aggregates

```text
FlowPlan
PlanningPeriod
```

### Entities

```text
OperationalTarget
PlanVersion
PlanApproval
```

### Rules

```text
approved plan cannot be edited directly
revision creates new version
actual comparison uses validated/approved readings only
planning depends on topology, telemetry, and workflow-validated data
```

---

## 13. Monitoring Context

### Purpose

Evaluate operational state using validated telemetry, thresholds, monitoring rules, and risk indicators.

### Aggregates

```text
MonitoringRule
Threshold
OperationalState
```

### Entities

```text
AlertRule
EscalationPolicy
AnomalySignal
RiskSignal
```

### Rules

```text
monitoring must consume trusted telemetry
threshold rule must define unit and scope
alert severity must be deterministic
risk level must be explainable
acknowledgement must track actor and time
```

---

## 14. Incidents Context

### Purpose

Manage operational incidents from detection to closure.

### Aggregates

```text
Incident
```

### Entities

```text
IncidentTimelineEntry
ResponseAction
ImpactAssessment
RootCauseAnalysis
IncidentResolution
```

### Rules

```text
incident cannot close without resolution
root cause may be required depending on severity
incident must maintain timeline
incident should link to alerts, telemetry, topology, and responsible actors
```

---

## 15. Audit Context

### Purpose

Record traceable evidence of operational and security actions.

### Aggregates

```text
AuditEvent
```

### Value objects

```text
AuditEventId
AuditActor
AuditTarget
AuditAction
AuditDecision
CorrelationId
RequestId
```

### Records

```text
user action audit
domain state change audit
security decision audit
workflow decision audit
integration event audit
```

### Rules

```text
audit is append-only
audit must not drive business workflow
audit must record actor and target
sensitive data must be masked
hash-chain readiness should be considered for future compliance
```

### Implementation order note

Workflow defines audit-ready outbound ports and events early.

Full audit hardening follows incidents in the first implementation order.

---

## 16. Integration Context

### Purpose

Manage connections to external systems.

### Aggregates

```text
ExternalSystem
ConnectorConfiguration
IngestionJob
```

### Rules

```text
external failure must not corrupt domain state
raw external references must be preserved
retry policy must be explicit
failed messages must be traceable
integration adapters must not bypass application/domain rules
```

---

## 17. Analytics and Reporting Contexts

### Purpose

Create derived insight from trusted operational data.

### Capabilities

```text
KPI projections
dashboards
trend analysis
planned vs actual reports
incident reports
validation performance reports
operational intelligence views
risk analytics
digital twin readiness projections
```

### Rules

```text
analytics does not modify source-of-truth state
projections can be rebuilt
dashboards must indicate data freshness
unvalidated data must be clearly marked or excluded
risk analytics must remain explainable
```

---

## 18. Notification Context

### Purpose

Deliver operational notifications through configured channels.

### Capabilities

```text
alert notification
workflow task notification
incident escalation notification
email/SMS/future channel readiness
notification templates
delivery tracking
```

### Rules

```text
notification does not own alert or incident rules
failed delivery must be traceable
sensitive data must be minimized
```

---

## 19. Validation Standards

### API validation

```text
DTOs use Bean Validation.
Controllers use @Valid.
Nested DTOs use @Valid.
Services do not duplicate DTO validation.
```

### Domain validation

```text
Aggregates protect invariants.
Value objects reject invalid construction.
Lifecycle transitions are controlled.
Domain exceptions represent business rule violations.
```

---

## 20. Testing Standards

Required test types:

```text
domain tests
application service tests
API/controller tests
persistence tests
integration tests
security tests
workflow state-machine tests
telemetry ingestion tests
audit tests
architecture tests
migration tests
```

Required validation commands:

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q test -Dtest=*ArchitectureTest
mvn -q flyway:validate
mvn -q clean verify
```

---

## 21. Architecture Validation Rules

ArchUnit rules should enforce:

```text
no controller accesses repository directly
no domain depends on infrastructure
no domain depends on Spring Web
no application depends on API layer
no cross-module entity sharing
infrastructure implements ports
kernel contains no business aggregate
no field injection
no classes named Manager, Helper, Util, or Common unless justified
no top-level technical modules outside approved structure
```

---

## 22. First Implementation Order

Recommended order:

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

Dependency interpretation:

```text
workflow follows telemetry because it orchestrates validation and approval around telemetry facts
planning follows workflow because actual-vs-plan comparison uses validated/approved readings
monitoring follows planning and telemetry because it interprets trusted operational state
incidents follow monitoring because incidents manage operational problems after detection
audit hardening follows incidents, while audit-ready ports/events exist earlier
analytics/reporting follow trusted operational data
notification follows alert/task/incident ownership and does not own business rules
```

---

## 23. Open Discussion Questions

```text
1. Should alerts be part of monitoring or a separate bounded context?
2. Should audit be a bounded context or platform module?
3. Which telemetry validation state changes belong in telemetry, and which process steps belong in workflow?
4. Which fields from HyFloAPI are business-critical and must be preserved exactly?
5. Which NGHyFloAPI standards are mature enough to reuse without modification?
6. Should reporting and analytics be separate modules or one context at first?
7. What is the first minimum useful operational workflow?
