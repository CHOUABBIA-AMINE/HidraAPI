# HidraAPI Module Catalog

## 1. Purpose

This document inventories the business-module roots present in the live repository and
maps them to the bounded-context intent defined by the Hidra macro architecture.

**Important:** source-package presence is not the same as operational maturity.
The macro architecture explicitly distinguishes Prepare, Operate, Industrialize, and
Intelligize maturity states.

---

## 2. Foundation and governance

| Source module | Architectural ownership |
|---|---|
| `identity` | Users, roles, permissions, authorities, authentication context and technical security principal meaning; does not own employee hierarchy. |
| `organization` | Employees, departments/regions, responsibility structure and business assignment model; does not own login credentials. |
| `workflow` | Tasks, approvals, rejections, delegations, escalations, process state and approval evidence; governs decisions without owning target-domain facts. |
| `audit` | Append-only trace evidence, actor/action/target proof, before/after and correlation evidence. |
| `documents` | Controlled files, procedures, P&IDs, as-builts, certificates, attachments and document-version evidence. |
| `configuration` | Governed setpoints, thresholds, coefficients and effective-dated runtime configuration. |
| `notification` | Delivery channels, templates, routing, dispatch status, retries and delivery evidence. |
| `simulation` | Scenarios, solver runs, assumptions, results, benchmark traceability and calculation lineage. |
| `risk` | Cross-domain risk indicators, exposure views and risk-synthesis evidence. |
| `analytics` | KPIs, trends, anomaly candidates, analytic projections and derived historical insights. |
| `reporting` | Report definitions, runs, schedules, exports and rendered-report evidence. |

---

## 3. Core operational plane

| Source module | Architectural ownership |
|---|---|
| `topology` | Pipelines, segments, nodes, stations/facilities, equipment, measurement locations, graph structure, spatial/versioned topology and visualization model. |
| `telemetry` | Sources/devices, telemetry points, readings, quality, validation and trusted-reading lifecycle. |
| `planning` | Plans, periods, targets, nominations, revisions and expected operating state. |
| `monitoring` | Threshold rules, operating envelopes, deviations, anomaly candidates and monitored state. |
| `alarm` | Alarm lifecycle, priority, shelving, acknowledgement, alarm KPIs and operator-response evidence. The macro document labels this context “alarms”; the current Java package is singular `alarm`. |
| `leakdetection` | Leak hypotheses, evidence, localization logic, leak cases and hydraulic-evidence correlation. |
| `incident` | Incident lifecycle, severity, triage, timeline, root-cause analysis, remediation and evidence bundle. |
| `integrity` | Structural condition, degradation, integrity risk indicators and engineering condition assessment. |
| `assets` | Maintenance schedules, inspections, work orders, lifecycle execution and equipment service history. |
| `custody` | Metering validation, fiscal records, reconciliation, delivery accounting and contractual measurement evidence. |
| `hse` | Permit/safety obligations, regulatory events, HSE evidence and safety-compliance processes. |
| `integration` | External-system connectors, mappings, retries, dead letters, import/export jobs and external references. |
| `party` | Repository source root for external/legal/business-party concepts; detailed ownership should be validated under a dedicated module roadmap before expansion. |

---

## 4. Current source-root inventory

The live `src/main/java/dz/sh/hidra/modules` tree contains exactly these 24 roots at the
stabilization baseline:

```text
alarm
analytics
assets
audit
configuration
custody
documents
hse
identity
incident
integration
integrity
leakdetection
monitoring
notification
organization
party
planning
reporting
risk
simulation
telemetry
topology
workflow
```

Dedicated top-level roadmaps currently exist for:

```text
identity
organization
telemetry
topology
workflow
```

Kernel and Platform have their own roadmaps as well.

For other module roots, do not infer completion from package presence. Validate their
domain/application/API/infrastructure contents and migrations before assigning maturity.

---

## 5. Architecture-only contexts not currently represented as source roots

The macro architecture also defines or discusses:

```text
environment
otsecurity
agents
```

They are architectural capabilities, but they are not module roots in the current source
inventory. Do not create them merely to make the package tree match the architecture
diagram; start them only through an approved roadmap.

---

## 6. Cross-module rule

Prefer references and explicit ports/events:

```text
topology reference ID
organization reference ID
identity principal/reference
domain event / integration contract
```

Avoid direct foreign aggregate imports such as:

```text
organization.domain -> identity.domain.model.User
topology.domain -> organization.domain.model.OrganizationUnit
identity.domain -> organization.domain.model.Employee
```
