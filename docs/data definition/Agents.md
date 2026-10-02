# HIDRA — Agents Data Definition Document

```text
Product       : Hidra — Hydrocarbon Intelligence for Data, Risk, and Analytics
Module        : agents
Package       : dz.sh.hidra.modules.agents
Document type : Data Definition / Target DDD
Status        : Target design; no implemented Java module found during repository search
Author        : Abir MEDJERAB
Owner         : Sonatrach / TRC : Digitalization Initiative
```

---

## 1. Purpose

The **Agents** module manages controlled AI/automation agents that assist Hidra users in understanding operational context, preparing recommendations, orchestrating analysis, and proposing actions across pipeline operations.

Agents are not a generic chatbot layer and not an autonomous industrial-control engine.

Agents answer:

```text
Which agent was used?
What task was it asked to perform?
Which business context was supplied?
Which tools/data sources were used?
Which recommendation was produced?
Which evidence supports the recommendation?
Which human approval was required?
What was finally accepted, rejected, or escalated?
```

Core rule:

```text
Agents assist.
They do not own operational truth.
They do not directly control the pipeline.
```

---

## 2. Bounded Context Decision

Agents is a **decision-support and orchestration bounded context**.

It owns:

```text
agent definitions
agent capabilities
agent policies
agent tasks
agent execution plans
agent runs
agent tool invocations
agent observations
agent recommendations
agent explanations
agent approval gates
agent feedback
agent evaluation records
agent knowledge-source references
agent safety guardrails
agent run traces
```

It does **not** own:

```text
topology assets
telemetry readings
planning targets
monitoring rules
alarms
incidents
pipeline network configurations
simulation models
risk scores
analytics models
reports
document binaries
workflow approvals
audit ledger
notification delivery
external-system connectors
identity credentials
organization structure
SCADA/PLC/RTU/SIS/ESD actuation
```

---

## 3. Module Boundary Rule

```text
Agents propose and explain.
Workflow approves.
Owning modules execute domain changes.
Audit proves.
```

A generated recommendation must never mutate another module directly.

Allowed pattern:

```text
AgentRecommendation
   -> ProposedCommandReference
      -> Workflow approval if required
         -> owning module application port
            -> owning module domain validation
               -> audit/outbox evidence
```

Forbidden pattern:

```text
AgentRun -> direct database update in topology/telemetry/planning/monitoring/incidents/risk/assets/etc.
```

---

## 4. Safety Rule

Agents must never directly actuate industrial equipment.

Forbidden direct actions:

```text
open valve
close valve
start pump
stop pump
start compressor
stop compressor
change PLC/RTU/SCADA command state
change ESD/SIS state
bypass safety system
write directly to industrial control systems
```

Agents may only produce:

```text
recommendation
explanation
risk warning
simulation request
workflow draft
incident summary
report draft
operator checklist
proposed topology/planning/monitoring/risk change
```

---

## 5. Upstream and Downstream Contexts

### 5.1 Upstream contexts consumed by Agents

```text
Topology       -> network context, assets, stations, equipment, topology snapshots
Telemetry      -> trusted readings, quality status, point context
Planning       -> plans, targets, approved plan versions
Monitoring     -> states, deviations, alert candidates, risk signals
Alarms         -> alarm lifecycle state and active alarm context
Incidents      -> incident context, timeline, response state
Simulation     -> scenarios, runs, candidate configurations, recommendations
Risk           -> risk register, risk scores, treatment state
Integrity      -> defects, assessments, remaining-life estimates
Assets         -> maintainable asset status, maintenance history
HSE            -> safety/environmental case context
Environment    -> environmental records, samples, spills, emissions
OT Security    -> cyber exposure, vulnerabilities, security posture
Analytics      -> KPIs, trends, insights, derived projections
Reporting      -> report definitions and generated report references
Documents      -> document references and metadata
Configuration  -> feature flags and safe runtime agent settings
Identity       -> actor identity and permissions
Organization   -> employee/unit/role snapshots
Workflow       -> approval/task/routing state
Audit          -> evidence references
Integration    -> external tool/provider connectors through controlled adapters
Notification   -> notification requests for completed agent outcomes
```

### 5.2 Downstream contexts consuming Agents

```text
Workflow       -> approvals for recommendations and proposed commands
Audit          -> traceability of agent-supported decisions
Reporting      -> agent-generated summaries and explanations
Notification   -> notification of agent recommendations or required reviews
Analytics      -> evaluation of agent effectiveness
Risk           -> evidence for risk review when agent detects weak signals
Incidents      -> incident briefing and response-assistance context
Simulation     -> scenario proposals produced by agents
```

---

## 6. Aggregate Overview

Recommended aggregates:

```text
AgentDefinition
AgentTask
AgentRun
AgentRecommendation
AgentEvaluation
AgentCatalogEntry
```

Recommended entities:

```text
AgentCapability
AgentPolicy
AgentSafetyGuardrail
AgentToolBinding
AgentKnowledgeSource
AgentPromptTemplate
AgentPromptVersion
AgentTaskTarget
AgentContextSnapshot
AgentExecutionPlan
AgentPlanStep
AgentToolInvocation
AgentObservation
AgentRunTraceEntry
AgentDecisionPoint
AgentApprovalGate
AgentRecommendationOption
AgentRecommendationEvidence
AgentFeedback
AgentEvaluationMetric
AgentEvaluationResult
AgentCatalogTranslation
```

---

## 7. Entity Definitions

## 7.1 AgentDefinition

Represents a controlled Hidra agent definition.

Fields:

```text
id
code
nameAr
nameFr
nameEn
description
agentTypeId
status
ownerModule
riskLevelId
humanApprovalRequired
maxAutonomyLevel
createdAt
updatedAt
```

Allowed statuses:

```text
DRAFT
ACTIVE
SUSPENDED
RETIRED
```

Rules:

```text
code is unique.
ACTIVE agent must have at least one active policy.
humanApprovalRequired must be true for operational-impact recommendations.
maxAutonomyLevel must never allow industrial actuation.
```

---

## 7.2 AgentCapability

Defines what an agent is allowed to do.

Fields:

```text
id
agentDefinitionId
capabilityTypeId
capabilityCode
description
targetModule
allowed
requiresApproval
active
createdAt
updatedAt
```

Examples:

```text
SUMMARIZE_INCIDENT
EXPLAIN_ALARM
SUGGEST_RISK_TREATMENT
PREPARE_REPORT_DRAFT
PROPOSE_SIMULATION_SCENARIO
COMPARE_PLAN_ACTUAL
EXPLAIN_TELEMETRY_QUALITY
GENERATE_TOPOLOGY_CHANGE_PROPOSAL_DRAFT
```

Forbidden capability examples:

```text
DIRECT_SCADA_WRITE
DIRECT_PLC_COMMAND
DIRECT_DATABASE_MUTATION
BYPASS_WORKFLOW_APPROVAL
CHANGE_SAFETY_SYSTEM_STATE
```

---

## 7.3 AgentPolicy

Defines governance rules for agent behavior.

Fields:

```text
id
agentDefinitionId
policyTypeId
policyCode
policyExpression
severityId
enforced
active
effectiveFrom
effectiveTo
createdAt
updatedAt
```

Examples:

```text
must cite source module references
must include confidence level
must include uncertainty statement
must not recommend direct actuation
must request workflow approval for operational changes
must mask sensitive identity/security data
must not expose secrets
```

---

## 7.4 AgentSafetyGuardrail

Represents explicit safety constraints.

Fields:

```text
id
agentDefinitionId
guardrailCode
guardrailTypeId
description
blockedActionPattern
requiredHandling
severityId
active
createdAt
updatedAt
```

Examples:

```text
NO_SCADA_ACTUATION
NO_SIS_ESD_BYPASS
NO_DIRECT_TOPOLOGY_ACTIVATION
NO_UNAPPROVED_RISK_ACCEPTANCE
NO_SECRET_DISCLOSURE
NO_UNCITED_OPERATIONAL_RECOMMENDATION
```

---

## 7.5 AgentToolBinding

Defines which tools/ports the agent may call.

Fields:

```text
id
agentDefinitionId
toolCode
toolTypeId
targetModule
targetPortName
permissionCode
requiresApproval
readOnly
active
createdAt
updatedAt
```

Rules:

```text
Tools must resolve to application ports or integration adapters.
Agents must not use persistence repositories directly.
Write-capable tool binding must require explicit policy and usually workflow approval.
Industrial control tools are forbidden.
```

---

## 7.6 AgentKnowledgeSource

References knowledge made available to an agent.

Fields:

```text
id
agentDefinitionId
sourceTypeId
sourceModule
sourceReferenceId
sourceLabelSnapshot
scopeExpression
validFrom
validTo
active
createdAt
updatedAt
```

Examples:

```text
Topology snapshot reference
Telemetry point catalog reference
Operations procedure document reference
Incident postmortem report reference
Risk matrix reference
Integrity assessment reference
OT security policy document reference
```

Rules:

```text
Documents owns document metadata and binary references.
Agents only reference permitted knowledge sources.
Restricted sources require identity/organization authorization.
```

---

## 7.7 AgentPromptTemplate

Template used to structure an agent request.

Fields:

```text
id
agentDefinitionId
code
nameAr
nameFr
nameEn
templatePurposeId
status
createdAt
updatedAt
```

Rules:

```text
Prompt templates are configuration of agent behavior, not business truth.
Operational prompts must include safety and evidence requirements.
```

---

## 7.8 AgentPromptVersion

Versioned prompt content.

Fields:

```text
id
promptTemplateId
version
promptText
systemInstructionText
inputSchemaJson
outputSchemaJson
changeReason
status
createdByActorId
createdAt
approvedByWorkflowReference
activatedAt
retiredAt
```

Rules:

```text
ACTIVE prompt version must be immutable.
Changing an operational prompt creates a new version.
High-impact prompt activation requires workflow approval.
```

---

## 7.9 AgentTask

A business request submitted to an agent.

Fields:

```text
id
agentDefinitionId
taskTypeId
requestedByActorId
requestedByDisplayNameSnapshot
requestedOrganizationUnitId
requestedOrganizationUnitNameSnapshot
priorityId
status
requestText
targetModule
targetTypeId
targetId
correlationId
createdAt
updatedAt
completedAt
cancelledAt
```

Allowed statuses:

```text
DRAFT
SUBMITTED
RUNNING
WAITING_FOR_APPROVAL
COMPLETED
CANCELLED
FAILED
```

Rules:

```text
AgentTask must preserve actor and organization snapshots.
AgentTask must identify target context when task is business-object-specific.
```

---

## 7.10 AgentTaskTarget

Additional targets related to an agent task.

Fields:

```text
id
agentTaskId
targetModule
targetTypeId
targetId
targetCodeSnapshot
targetLabelSnapshot
relationshipTypeId
createdAt
```

Examples:

```text
incident under analysis
related alarm
related topology asset
related telemetry point
related simulation run
related risk assessment
```

---

## 7.11 AgentContextSnapshot

Frozen context supplied to an agent run.

Fields:

```text
id
agentTaskId
snapshotTypeId
sourceModule
sourceReferenceId
sourceVersion
payloadHash
summaryText
capturedAt
capturedByActorId
```

Rules:

```text
Context snapshot must not replace owning module data.
Snapshot exists for reproducibility and explanation.
Sensitive values may be masked.
```

---

## 7.12 AgentExecutionPlan

Plan generated before or during an agent run.

Fields:

```text
id
agentTaskId
agentRunId
planStatus
objectiveText
createdAt
approvedByActorId
approvedAt
```

Allowed statuses:

```text
DRAFT
PROPOSED
APPROVED
REJECTED
EXECUTING
COMPLETED
FAILED
```

Rules:

```text
Execution plans containing write-capable actions require approval.
Execution plans must be explainable before execution.
```

---

## 7.13 AgentPlanStep

A step in an execution plan.

Fields:

```text
id
executionPlanId
stepOrder
stepTypeId
targetModule
toolCode
description
requiresApproval
status
createdAt
updatedAt
```

Examples:

```text
Read incident timeline
Read current monitoring state
Read trusted telemetry history
Run simulation scenario
Compare risk treatment options
Draft topology change proposal
Draft report summary
```

---

## 7.14 AgentRun

An execution instance for an agent task.

Fields:

```text
id
agentTaskId
agentDefinitionId
promptVersionId
runStatus
modelProviderReference
modelNameSnapshot
modelVersionSnapshot
startedAt
completedAt
failedAt
failureReason
inputTokenCount
outputTokenCount
costAmount
costCurrency
correlationId
createdAt
updatedAt
```

Allowed statuses:

```text
QUEUED
RUNNING
WAITING_FOR_TOOL
WAITING_FOR_APPROVAL
COMPLETED
FAILED
CANCELLED
BLOCKED_BY_POLICY
```

Rules:

```text
AgentRun must be reproducible enough for audit and review.
AgentRun must record policy blocks and tool invocations.
```

---

## 7.15 AgentToolInvocation

A tool/port call made during an agent run.

Fields:

```text
id
agentRunId
planStepId
toolBindingId
toolCode
targetModule
targetPortName
invocationTypeId
requestPayloadHash
responsePayloadHash
status
startedAt
completedAt
errorCode
errorMessage
```

Rules:

```text
Do not store secrets in payload fields.
Full payload storage must be explicitly governed and masked.
Tool invocation must not bypass the target module application layer.
```

---

## 7.16 AgentObservation

Observation extracted from tool output or supplied context.

Fields:

```text
id
agentRunId
sourceModule
sourceReferenceId
observationTypeId
summaryText
confidenceScore
evidenceLevelId
createdAt
```

Examples:

```text
Pressure trend increasing upstream of station
Plan-actual deviation exceeds threshold
Incident has no assigned response owner
Integrity defect has overdue assessment
Risk treatment action is overdue
```

---

## 7.17 AgentRunTraceEntry

Trace record for the agent run.

Fields:

```text
id
agentRunId
traceOrder
traceTypeId
summaryText
redactedDetail
createdAt
```

Rules:

```text
Trace must be redacted for secrets, personal data, and security-sensitive details.
Trace is not a substitute for audit ledger.
Audit owns durable decision evidence.
```

---

## 7.18 AgentDecisionPoint

A point where the agent made or proposed a decision.

Fields:

```text
id
agentRunId
decisionTypeId
decisionText
alternativesSummary
selectedOptionReference
confidenceScore
uncertaintyText
requiresHumanApproval
createdAt
```

Rules:

```text
DecisionPoint is advisory unless explicitly accepted by a human/workflow.
Operational-impact decisions must include uncertainty and evidence.
```

---

## 7.19 AgentApprovalGate

Human approval required before an agent action continues.

Fields:

```text
id
agentRunId
planStepId
approvalTypeId
reasonText
workflowReferenceId
status
requestedAt
resolvedAt
resolvedByActorId
```

Allowed statuses:

```text
PENDING
APPROVED
REJECTED
EXPIRED
CANCELLED
```

Rules:

```text
Workflow owns approval routing and task lifecycle.
Agents only reference workflow approval gates.
```

---

## 7.20 AgentRecommendation

A recommendation produced by an agent.

Fields:

```text
id
agentTaskId
agentRunId
recommendationTypeId
targetModule
targetTypeId
targetId
title
summary
recommendationText
confidenceScore
impactLevelId
urgencyLevelId
status
createdAt
updatedAt
acceptedAt
rejectedAt
```

Allowed statuses:

```text
DRAFT
PROPOSED
UNDER_REVIEW
ACCEPTED
REJECTED
SUPERSEDED
CANCELLED
```

Rules:

```text
Recommendation is not execution.
Any accepted recommendation that changes business state must become a command in the owning module.
High-impact recommendations require Workflow approval.
```

---

## 7.21 AgentRecommendationOption

Alternative option inside a recommendation.

Fields:

```text
id
recommendationId
optionOrder
optionCode
description
expectedBenefit
expectedRisk
estimatedCostText
feasibilityScore
recommended
createdAt
```

Examples:

```text
Continue monitoring
Open incident
Run simulation
Escalate alarm
Create maintenance work order proposal
Request integrity assessment
Prepare HSE review
```

---

## 7.22 AgentRecommendationEvidence

Evidence supporting a recommendation.

Fields:

```text
id
recommendationId
sourceModule
sourceTypeId
sourceReferenceId
sourceLabelSnapshot
evidenceSummary
evidenceWeight
createdAt
```

Rules:

```text
Recommendation must be evidence-backed.
Evidence references source modules; it does not copy their full business state.
```

---

## 7.23 AgentFeedback

Human feedback on agent output.

Fields:

```text
id
agentTaskId
agentRunId
recommendationId
feedbackByActorId
feedbackByDisplayNameSnapshot
rating
feedbackText
feedbackTypeId
createdAt
```

Examples:

```text
USEFUL
INCORRECT
INSUFFICIENT_EVIDENCE
UNSAFE_RECOMMENDATION
NEEDS_MORE_CONTEXT
GOOD_EXPLANATION
```

---

## 7.24 AgentEvaluation

Formal evaluation of agent performance.

Fields:

```text
id
agentDefinitionId
evaluationPeriodStart
evaluationPeriodEnd
evaluationTypeId
status
summary
createdAt
completedAt
```

Evaluation questions:

```text
Were recommendations accepted?
Were recommendations safe?
Were citations/evidence sufficient?
Were false positives reduced?
Were workflow delays reduced?
Were incident summaries accurate?
```

---

## 7.25 AgentEvaluationMetric

Metric definition for evaluating an agent.

Fields:

```text
id
agentEvaluationId
metricCode
metricName
metricTypeId
unitId
targetValue
createdAt
```

Examples:

```text
recommendation_acceptance_rate
unsafe_output_count
missing_evidence_count
average_time_saved_minutes
operator_feedback_score
incident_summary_accuracy
```

---

## 7.26 AgentEvaluationResult

Metric result value for evaluation.

Fields:

```text
id
agentEvaluationMetricId
valueNumeric
valueText
resultStatus
calculatedAt
```

---

## 7.27 AgentCatalogEntry

Controlled vocabulary for agent business types.

Fields:

```text
id
catalogName
code
active
sortOrder
systemDefined
createdAt
updatedAt
```

Catalog examples:

```text
AGENT_TYPE
AGENT_TASK_TYPE
CAPABILITY_TYPE
POLICY_TYPE
GUARDRAIL_TYPE
TOOL_TYPE
RECOMMENDATION_TYPE
IMPACT_LEVEL
URGENCY_LEVEL
FEEDBACK_TYPE
EVIDENCE_LEVEL
AUTONOMY_LEVEL
```

---

## 7.28 AgentCatalogTranslation

Multilingual translation for agent catalog values.

Fields:

```text
id
catalogEntryId
locale
name
description
createdAt
updatedAt
```

---

## 8. Recommended Database Tables

```text
hidra_agent_definition
hidra_agent_capability
hidra_agent_policy
hidra_agent_safety_guardrail
hidra_agent_tool_binding
hidra_agent_knowledge_source
hidra_agent_prompt_template
hidra_agent_prompt_version
hidra_agent_task
hidra_agent_task_target
hidra_agent_context_snapshot
hidra_agent_execution_plan
hidra_agent_plan_step
hidra_agent_run
hidra_agent_tool_invocation
hidra_agent_observation
hidra_agent_run_trace_entry
hidra_agent_decision_point
hidra_agent_approval_gate
hidra_agent_recommendation
hidra_agent_recommendation_option
hidra_agent_recommendation_evidence
hidra_agent_feedback
hidra_agent_evaluation
hidra_agent_evaluation_metric
hidra_agent_evaluation_result
hidra_agent_catalog_entry
hidra_agent_catalog_translation
```

---

## 9. Lifecycle Examples

### 9.1 Incident assistant

```text
Incident opened
  -> AgentTask: summarize incident context
  -> Agent reads incident timeline, telemetry references, topology asset, monitoring state
  -> AgentRecommendation: missing response owner + suggested escalation
  -> Workflow reviews recommendation if operational impact exists
  -> Audit records accepted/rejected decision
```

### 9.2 Simulation assistant

```text
Operator asks for optimal operating scenario
  -> AgentTask: propose simulation scenario
  -> Agent reads topology snapshot, planning targets, trusted telemetry, constraints
  -> Agent creates SimulationScenario draft through Simulation application port
  -> Simulation executes run
  -> Agent summarizes candidate network configuration
  -> Topology receives change proposal only if human submits it
  -> Workflow approves
  -> Topology activates official snapshot
```

### 9.3 Risk assistant

```text
Monitoring emits risk signal
  -> AgentTask: explain risk signal
  -> Agent reads telemetry, monitoring evaluation, incident history, integrity data
  -> AgentRecommendation: create RiskAssessment draft
  -> Risk owns scoring and treatment
  -> Workflow approves risk acceptance if required
```

---

## 10. Cross-Module Boundary Matrix

| Area | Owning module | Agents relationship |
|---|---|---|
| Pipeline network state | Topology | Reads snapshots; may draft change proposal |
| Raw/trusted measurements | Telemetry | Reads trusted readings; explains quality |
| Plans and expected values | Planning | Reads plans; may draft plan review |
| Operational deviations | Monitoring | Reads states/signals; explains deviations |
| Formal alarms | Alarm Management | Summarizes alarm context; does not own lifecycle |
| Incident lifecycle | Incident Management | Summarizes and recommends; does not change lifecycle directly |
| Safety/environment cases | HSE / Environment | Reads context; may draft assessment request |
| Risk scoring/treatment | Risk | May recommend assessment; Risk owns score |
| Simulations | Simulation | May propose scenario; Simulation owns run/result |
| Official network activation | Topology + Workflow | Agent cannot activate directly |
| Reports | Reporting | May draft summaries; Reporting owns formal output |
| Documents | Documents | References documents; does not store binaries |
| Notifications | Notification | Requests notifications through ports; does not deliver |
| Audit evidence | Audit | Produces evidence references; Audit owns ledger |
| External tools/providers | Integration | Access through integration adapters only |
| Identity/authz | Identity | Uses permissions; does not own credentials |
| Organization responsibility | Organization | Uses snapshots; does not own structure |
```

---

## 11. Invariants

```text
AgentDefinition.code must be unique.
AgentRun must reference an AgentTask.
AgentTask must preserve actor and organization snapshots.
AgentToolInvocation must reference an allowed AgentToolBinding.
AgentRecommendation must reference evidence for operational-impact recommendations.
AgentRecommendation cannot mutate business state directly.
Write-capable recommendations require Workflow approval.
Safety guardrails must block industrial actuation.
Prompt versions are immutable after activation.
High-impact prompt changes require Workflow approval.
Agent traces must not expose secrets or unsafe security details.
Audit owns durable decision evidence.
```

---

## 12. Events

Recommended domain events:

```text
AgentDefinitionActivated
AgentDefinitionSuspended
AgentTaskSubmitted
AgentRunStarted
AgentRunCompleted
AgentRunFailed
AgentToolInvocationCompleted
AgentToolInvocationBlocked
AgentPolicyViolationDetected
AgentRecommendationProposed
AgentRecommendationAccepted
AgentRecommendationRejected
AgentApprovalGateRequested
AgentFeedbackRecorded
AgentEvaluationCompleted
```

---

## 13. Application Ports

### Inbound ports

```text
CreateAgentDefinitionUseCase
ActivateAgentDefinitionUseCase
SubmitAgentTaskUseCase
StartAgentRunUseCase
CancelAgentRunUseCase
ApproveAgentPlanStepUseCase
RecordAgentRecommendationUseCase
AcceptAgentRecommendationUseCase
RejectAgentRecommendationUseCase
RecordAgentFeedbackUseCase
EvaluateAgentUseCase
```

### Outbound ports

```text
AgentAuthorizationPort
AgentWorkflowPort
AgentAuditEventPort
AgentTopologyContextPort
AgentTelemetryContextPort
AgentPlanningContextPort
AgentMonitoringContextPort
AgentAlarmContextPort
AgentIncidentContextPort
AgentSimulationContextPort
AgentRiskContextPort
AgentIntegrityContextPort
AgentAssetContextPort
AgentHseContextPort
AgentEnvironmentContextPort
AgentOtSecurityContextPort
AgentAnalyticsContextPort
AgentReportingContextPort
AgentDocumentContextPort
AgentNotificationPort
AgentIntegrationToolPort
AgentModelProviderPort
```

Rules:

```text
Outbound ports return DTOs/snapshots, not foreign domain entities.
Agents must not import another module's domain model or JPA entity.
```

---

## 14. REST API Shape

Recommended base path:

```text
/api/v1/agents
```

Recommended endpoint groups:

```text
GET    /api/v1/agents/definitions
POST   /api/v1/agents/definitions
POST   /api/v1/agents/definitions/{id}/activate
POST   /api/v1/agents/definitions/{id}/suspend

GET    /api/v1/agents/tasks
POST   /api/v1/agents/tasks
GET    /api/v1/agents/tasks/{taskId}
POST   /api/v1/agents/tasks/{taskId}/cancel

GET    /api/v1/agents/runs/{runId}
GET    /api/v1/agents/runs/{runId}/trace
GET    /api/v1/agents/runs/{runId}/tool-invocations

GET    /api/v1/agents/recommendations
GET    /api/v1/agents/recommendations/{recommendationId}
POST   /api/v1/agents/recommendations/{recommendationId}/accept
POST   /api/v1/agents/recommendations/{recommendationId}/reject

POST   /api/v1/agents/feedback
GET    /api/v1/agents/evaluations
POST   /api/v1/agents/evaluations
```

---

## 15. Implementation Package Structure

```text
src/main/java/dz/sh/hidra/modules/agents
  api/rest/controller
  api/rest/request
  api/rest/response
  api/rest/mapper
  application/command
  application/query
  application/dto
  application/port/in
  application/port/out
  application/service
  domain/model
  domain/value
  domain/event
  domain/policy
  domain/service
  domain/exception
  infrastructure/configuration
  infrastructure/persistence/entity
  infrastructure/persistence/repository
  infrastructure/persistence/mapper
  infrastructure/persistence/adapter
  infrastructure/integration
  infrastructure/modelprovider
```

---

## 16. Explicit Non-Goals

Agents must not become:

```text
a generic chat module
a replacement for topology, telemetry, monitoring, incidents, risk, simulation, or analytics
a hidden workflow engine
a direct integration bypass
a direct SCADA/PLC/RTU controller
a secret store
a document repository
a reporting engine
a source of operational truth
```

---

## 17. Final Ownership Sentence

```text
Agents turn trusted Hidra context into explainable recommendations and assisted workflows.
They never become the owner of the operational fact, the approval decision, or the physical action.
```
