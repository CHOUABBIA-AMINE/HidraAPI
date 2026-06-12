# HIDRA — Consolidated Data Definition Document

```text
Document code : HIDRA-CONSOLIDATED-DDD
Repository    : HidraAPI
Folder        : docs/data definition
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC : Digitalization Initiative
Author        : Abir MEDJERAB
UpdatedOn     : 2026-06-11
Status        : Active DDD reference
Scope         : Governs all Data Definition Documents under docs/data definition
```

---

## 1. Purpose

This document is the master Data Definition Document for Hidra / HyFlo.

It is the implementation contract for:

```text
module ownership
bounded-context boundaries
canonical package roots
source DDD inventory
table-prefix rules
cross-module reference rules
missing-module blockers
safety rules
code-generation guardrails
```

Detailed module DDD files define fields and entity-level details. This master document governs cross-module consistency. If a module DDD conflicts with this master, the master rule wins until the module DDD is corrected.

---

## 2. Source DDD inventory

The following DDD files are active implementation sources under `docs/data definition`.

| # | Source file | Module | Canonical package root | Status |
|---:|---|---|---|---|
| 1 | `Kernel.md` | kernel | `dz.sh.hidra.kernel` | Active foundation DDD |
| 2 | `Platform.md` | platform | `dz.sh.hidra.platform` | Active technical DDD |
| 3 | `Identity.md` | identity | `dz.sh.hidra.modules.identity` | Active security DDD |
| 4 | `Organization.md` | organization | `dz.sh.hidra.modules.organization` | Active organization DDD |
| 5 | `Party.md` | party | `dz.sh.hidra.modules.party` | Active external/internal legal-party DDD |
| 6 | `Topology.md` | topology | `dz.sh.hidra.modules.topology` | Active topology DDD |
| 7 | `Telemetry.md` | telemetry | `dz.sh.hidra.modules.telemetry` | Active telemetry DDD |
| 8 | `Planning.md` | planning | `dz.sh.hidra.modules.planning` | Active planning DDD |
| 9 | `Monitoring.md` | monitoring | `dz.sh.hidra.modules.monitoring` | Active monitoring DDD |
| 10 | `Alarm.md` | alarm | `dz.sh.hidra.modules.alarm` | Active alarm-lifecycle DDD |
| 11 | `LeakDetection.md` | leakdetection | `dz.sh.hidra.modules.leakdetection` | Active leak decision-support DDD |
| 12 | `Incident.md` | incident | `dz.sh.hidra.modules.incident` | Active operational-incident DDD |
| 13 | `Risk.md` | risk | `dz.sh.hidra.modules.risk` | Active risk-management DDD |
| 14 | `Hse.md` | hse | `dz.sh.hidra.modules.hse` | Active HSE consequence DDD |
| 15 | `Integrity.md` | integrity | `dz.sh.hidra.modules.integrity` | Active integrity DDD |
| 16 | `Assets.md` | assets | `dz.sh.hidra.modules.assets` | Active asset-management DDD |
| 17 | `Custody.md` | custody | `dz.sh.hidra.modules.custody` | Active custody DDD |
| 18 | `Workflow.md` | workflow | `dz.sh.hidra.modules.workflow` | Active workflow DDD |
| 19 | `Audit.md` | audit | `dz.sh.hidra.modules.audit` | Active audit DDD |
| 20 | `Documents.md` | documents | `dz.sh.hidra.modules.documents` | Active document metadata DDD |
| 21 | `Integration.md` | integration | `dz.sh.hidra.modules.integration` | Active integration DDD |
| 22 | `Configuration.md` | configuration | `dz.sh.hidra.modules.configuration` | Active configuration DDD |
| 23 | `Notification.md` | notification | `dz.sh.hidra.modules.notification` | Active notification DDD |
| 24 | `Simulation.md` | simulation | `dz.sh.hidra.modules.simulation` | Active simulation DDD |
| 25 | `Analytics.md` | analytics | `dz.sh.hidra.modules.analytics` | Active analytics DDD |
| 26 | `Reporting.md` | reporting | `dz.sh.hidra.modules.reporting` | Active reporting DDD |

The following contexts are still not implementation sources unless dedicated DDD files are added:

```text
contracts / commercial
finance / ERP accounting
```

---

## 3. Master module sequence

The implementation sequence is:

```text
kernel
  -> platform
      -> identity
      -> organization
      -> party
      -> topology
          -> telemetry
              -> planning
                  -> monitoring
                      -> alarm
                          -> leakdetection
                              -> incident
                                  -> risk
                                  -> hse
                                  -> integrity
                                  -> assets
                                  -> custody
                                      -> workflow
                                      -> audit
                                      -> documents
                                      -> integration
                                      -> configuration
                                      -> notification
                                      -> simulation
                                      -> analytics
                                      -> reporting
```

This is not a Java import chain. It is a business-data and implementation-readiness chain.

Java imports must remain layer-safe and module-safe.

---

## 4. Locked table prefixes

| Module | Locked table prefix | Forbidden alternatives |
|---|---|---|
| identity | `hidra_identity_*` | `hidra_org_*`, `hidra_security_*` |
| organization | `hidra_org_*` | `hidra_organization_*` unless a future ADR changes it |
| party | `hidra_party_*` | `hidra_org_*`, `hidra_vendor_*`, `hidra_supplier_*` |
| topology | `hidra_topology_*` | `hidra_asset_*`, `hidra_integrity_*` |
| telemetry | `hidra_telemetry_*` | `hidra_custody_*`, `hidra_monitoring_*` |
| planning | `hidra_planning_*` | `hidra_custody_*`, `hidra_monitoring_*` |
| monitoring | `hidra_monitoring_*` | `hidra_alarm_*`, `hidra_incident_*` |
| alarm | `hidra_alarm_*` | `hidra_monitoring_*`, `hidra_incident_*` |
| leakdetection | `hidra_leak_detection_*` | `hidra_leakdetection_*`, `hidra_alarm_*`, `hidra_incident_*` |
| incident | `hidra_incident_*` | `hidra_alarm_*`, `hidra_hse_*`, `hidra_leak_detection_*` |
| risk | `hidra_risk_*` | `hidra_integrity_*`, `hidra_hse_*`, `hidra_incident_*` |
| hse | `hidra_hse_*` | `hidra_incident_*`, `hidra_integrity_*`, `hidra_risk_*` |
| integrity | `hidra_integrity_*` | `hidra_topology_*`, `hidra_asset_*`, `hidra_risk_*` |
| assets | `hidra_asset_*` | `hidra_assets_*`, `hidra_topology_*`, `hidra_integrity_*` |
| custody | `hidra_custody_*` | `hidra_telemetry_*`, `hidra_planning_*`, `hidra_finance_*` |
| workflow | `hidra_workflow_*` | target-module prefixes |
| audit | `hidra_audit_*` | target-module prefixes, `hidra_platform_*` |
| documents | `hidra_document_*` | target-module prefixes |
| integration | `hidra_integration_*` | target-module prefixes |
| configuration | `hidra_configuration_*` | module-owned catalog prefixes |
| notification | `hidra_notification_*` | target-module prefixes |
| simulation | `hidra_simulation_*` | `hidra_analytics_*`, target-module prefixes |
| analytics | `hidra_analytics_*` | `hidra_reporting_*`, target-module prefixes |
| reporting | `hidra_reporting_*` | `hidra_analytics_*`, target-module prefixes |

A module must not create tables using another module's prefix.

---

## 5. Bounded-context ownership summary

| Module | Owns | Must not own |
|---|---|---|
| kernel | generic contracts and primitives | business entities or technical framework integration |
| platform | technical infrastructure mechanisms | business audit, identity, workflow, topology, telemetry, analytics |
| identity | users, groups, roles, permissions, RBAC/ABAC, external IDM mapping | employees, organization units, HTTP filters, secret storage internals |
| organization | internal units, employees, positions, reporting lines, responsibility, shifts | users, roles, permissions, vendors, suppliers, contractors, owners, topology assets |
| party | legal/business parties and party roles: supplier, vendor, contractor, manufacturer, owner, operator, shipper, customer, buyer, seller, partner | internal organization hierarchy, identity users, custody tickets, asset lifecycle |
| topology | physical/logical transportation network, facilities, pipelines, segments, nodes, equipment, measurement locations | maintenance work orders, telemetry values, integrity decisions, HSE consequences |
| telemetry | measured operational facts and trusted telemetry readings | planning targets, official custody quantities, monitoring decisions |
| planning | expected operational state, nominations, targets, scenarios, constraints | telemetry readings, custody actuals, incidents, alarms |
| monitoring | actual-vs-expected evaluation, operational state, deviations, alert candidates, risk signals | formal alarm lifecycle, incident lifecycle, leak cases |
| alarm | formal alarm lifecycle, alarm state, acknowledgement, shelving, escalation, alarm evidence | monitoring evaluation, incident response lifecycle, notification delivery internals |
| leakdetection | leak suspicion, evidence, localization, confidence, verification, leak case | formal alarm lifecycle, incident lifecycle, SCADA/OT actuation |
| incident | operational incident lifecycle, response coordination, containment, resolution, post-incident review | HSE compliance consequences, leak algorithms, alarm lifecycle, audit ledger |
| risk | risk register, risk assessments, risk scores, mitigation plans, risk acceptance | incident lifecycle, HSE case lifecycle, integrity inspections, financial accounting |
| hse | health, safety, environment, compliance consequences, corrective/preventive actions | incident lifecycle, alarm lifecycle, topology, asset maintenance |
| integrity | engineering condition, inspections, defects, corrosion, threats, remaining life, recommendations | topology asset master, maintenance work orders, HSE case lifecycle |
| assets | maintainable asset lifecycle, maintenance strategy, work orders, spares, warranties | physical topology ownership, integrity decisions, custody quantities |
| custody | official accepted transfer records, measurement snapshots, quality certificates, tickets, reconciliation | raw telemetry, planning targets, finance postings |
| workflow | process execution, assignments, tasks, approvals, state transitions | target business facts |
| audit | immutable evidence of actions, decisions, actor snapshots, target references | business decisions or mutable workflow state |
| documents | document metadata, versions, target links, retention, extraction metadata | binary storage internals or target business facts |
| integration | external systems, endpoints, mappings, messages, import/export/sync jobs | direct writes into another module's tables |
| configuration | governed runtime configuration, feature flags, scoped overrides, deployment snapshots | module-owned business taxonomies |
| notification | templates, recipients, preferences, message delivery, retry, delivery history | business severity, approval, closure, or target decisions |
| simulation | scenario models, runs, assumptions, input snapshots, outputs, recommendations | approved topology state, workflow approvals, analytics/reporting ownership |
| analytics | analytical datasets, KPIs, metrics, indicators, projections, analytical snapshots | transactional business ownership or official reporting publication |
| reporting | report definitions, report runs, report exports, scheduled reports, publication metadata | analytical computation ownership or source business facts |

---

## 6. Cross-module reference policy

Use neutral references and snapshots:

```text
targetModule
targetTypeCode
targetId
targetCodeSnapshot
targetLabelSnapshot
```

For topology assets use:

```text
topologyAssetTypeCode
topologyAssetId
topologyAssetCodeSnapshot
topologyAssetNameSnapshot
```

For party references use:

```text
partyId
partyRoleCode
partyCodeSnapshot
partyNameSnapshot
```

For actors use:

```text
actorId
actorDisplayNameSnapshot
organizationUnitId
organizationUnitNameSnapshot
```

Forbidden outside the owning module:

```text
Facility facility;
Pipeline pipeline;
Equipment equipment;
TelemetryReading reading;
TrustedTelemetryReading trustedReading;
WorkflowTask task;
WorkflowInstance workflowInstance;
AuditEvent auditEvent;
User user;
Employee employee;
Party party;
Supplier supplier;
Vendor vendor;
Document document;
MaintenanceWorkOrder workOrder;
IntegrityCase integrityCase;
HseCase hseCase;
Alarm alarm;
Incident incident;
RiskAssessment riskAssessment;
CustodyTransferTicket ticket;
ReportRun reportRun;
AnalyticsDataset analyticsDataset;
```

A business module must not import another business module's:

```text
domain.*
infrastructure.*
api.rest.*
```

Allowed cross-module communication:

```text
stable IDs
neutral references
snapshots
application ports
DTOs designed for integration
published events
platform outbox
read-only projections explicitly designed for integration
```

---

## 7. Boundary decision matrix

| Ambiguous concept | Correct owner | Allowed reference elsewhere | Forbidden interpretation |
|---|---|---|---|
| Internal employee / organization unit | organization | employee/organization snapshots | Identity or party owns internal HR structure |
| Supplier / vendor / contractor / owner / operator / customer | party | party reference and party role snapshot | Organization owns external legal parties |
| Physical pipeline / station / facility | topology | topology neutral reference | Assets or HSE owns topology |
| Maintainable lifecycle | assets | topology neutral reference | Topology owns work orders |
| Defect / corrosion / remaining life | integrity | topology neutral reference | Assets owns integrity decisions |
| Safety/environment consequence | hse | incident/leak/alarm neutral reference | Incident doubles as HSE case |
| Formal alarm lifecycle | alarm | monitoring/leak/incident neutral references | Monitoring owns alarm lifecycle |
| Operational incident lifecycle | incident | alarm/leak/hse/risk neutral references | LeakDetection or HSE owns incident lifecycle |
| Risk scoring / mitigation tracking | risk | incident/hse/integrity references | Incident or HSE owns enterprise risk register |
| Official transfer quantity | custody | telemetry/planning snapshots | Telemetry is fiscal truth |
| Deviation detection | monitoring | telemetry/planning references | Monitoring owns alarms/incidents |
| Leak suspicion | leakdetection | monitoring/alarm/incident neutral references | LeakDetection owns incidents or alarms |
| Analytical KPI / metric | analytics | source-module snapshots/read models | Reporting owns analytics computation |
| Report run / export / schedule | reporting | analytics/source references | Analytics owns report publication lifecycle |
| Runtime config | configuration | scoped config reference | Configuration owns business taxonomies |

---

## 8. Documentation and annotation policy

Domain, application, and infrastructure models must not use Swagger/OpenAPI annotations.

`@Schema` is allowed only in:

```text
dz.sh.hidra.modules.<module>.api.rest.request
dz.sh.hidra.modules.<module>.api.rest.response
```

Controllers may use:

```text
@Tag
@Operation
@ApiResponse
@ApiResponses
@Parameter
```

Domain models must use Javadoc for domain documentation.

---

## 9. Code-generation rules

1. Read this master DDD and the target module DDD before generating code.
2. Generate code only inside the requested module.
3. Never create `shared`, `common`, `core`, `utils`, `helper`, `helpers`, or `misc` packages.
4. Never import another module's domain or infrastructure packages.
5. Keep domain framework-independent.
6. Keep JPA entities in `infrastructure.persistence.entity` only.
7. Keep REST request/response models in `api.rest.request` and `api.rest.response` only.
8. Use `@Schema` only on API request/response models.
9. Represent cross-module dependencies as IDs, snapshots, ports, DTOs, or events.
10. Do not generate contracts/commercial or finance/ERP code until those DDDs exist.
11. Generate `alarm`, `incident`, `party`, `risk`, `analytics`, and `reporting` only from their dedicated DDDs, not from references in neighboring modules.
12. For skeleton tasks, create only production `package-info.java` files unless the task explicitly asks for behavior.

---

## 10. Final active module list for skeleton generation

The module skeleton ZIP must include exactly the following DDD-backed modules unless the user explicitly narrows scope:

```text
kernel
platform
identity
organization
party
topology
telemetry
planning
monitoring
alarm
leakdetection
incident
risk
hse
integrity
assets
custody
workflow
audit
documents
integration
configuration
notification
simulation
analytics
reporting
```

Blocked from skeleton generation until a dedicated DDD exists:

```text
contracts
finance
```

---

## 11. Final rule

A module owns meaning, not just tables. When another module needs that data, it references it through stable IDs, snapshots, public ports, events, or projections.
