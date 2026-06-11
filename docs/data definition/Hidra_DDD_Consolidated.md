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

It is the implementation contract for module ownership, bounded-context boundaries, package roots, table prefixes, missing-module blockers, and code-generation guardrails.

Detailed module DDD files define fields and entity-level details. This master document governs cross-module consistency. If a module DDD conflicts with this master, the master rule wins.

---

## 2. Source DDD inventory

| Source file | Module | Canonical package root | Status |
|---|---|---|---|
| `Kernel.md` | kernel | `dz.sh.hidra.kernel` | Active foundation DDD |
| `Platform.md` | platform | `dz.sh.hidra.platform` | Active technical DDD |
| `Identity.md` | identity | `dz.sh.hidra.modules.identity` | Active security DDD |
| `Organization.md` | organization | `dz.sh.hidra.modules.organization` | Active organization DDD |
| `Topology.md` | topology | `dz.sh.hidra.modules.topology` | Active topology DDD |
| `Telemetry.md` | telemetry | `dz.sh.hidra.modules.telemetry` | Active telemetry DDD |
| `Planning.md` | planning | `dz.sh.hidra.modules.planning` | Active planning DDD |
| `Monitoring.md` | monitoring | `dz.sh.hidra.modules.monitoring` | Active monitoring DDD |
| `LeakDetection.md` | leakdetection | `dz.sh.hidra.modules.leakdetection` | Active leak decision-support DDD |
| `Hse.md` | hse | `dz.sh.hidra.modules.hse` | Active HSE consequence DDD |
| `Integrity.md` | integrity | `dz.sh.hidra.modules.integrity` | Active integrity DDD |
| `Assets.md` | assets | `dz.sh.hidra.modules.assets` | Active asset-management DDD |
| `Custody.md` | custody | `dz.sh.hidra.modules.custody` | Active custody DDD |
| `Workflow.md` | workflow | `dz.sh.hidra.modules.workflow` | Active workflow DDD |
| `Audit.md` | audit | `dz.sh.hidra.modules.audit` | Active audit DDD |
| `Documents.md` | documents | `dz.sh.hidra.modules.documents` | Active document metadata DDD |
| `Integration.md` | integration | `dz.sh.hidra.modules.integration` | Active integration DDD |
| `Configuration.md` | configuration | `dz.sh.hidra.modules.configuration` | Active configuration DDD |
| `Notification.md` | notification | `dz.sh.hidra.modules.notification` | Active notification DDD |
| `Simulation.md` | simulation | `dz.sh.hidra.modules.simulation` | Active simulation DDD |

---

## 3. Locked table prefixes

| Module | Locked table prefix | Forbidden alternatives |
|---|---|---|
| organization | `hidra_org_*` | `hidra_organization_*` unless a future ADR changes it |
| topology | `hidra_topology_*` | `hidra_asset_*`, `hidra_integrity_*` |
| telemetry | `hidra_telemetry_*` | `hidra_custody_*`, `hidra_monitoring_*` |
| planning | `hidra_planning_*` | `hidra_custody_*`, `hidra_monitoring_*` |
| monitoring | `hidra_monitoring_*` | `hidra_alarm_*`, `hidra_incident_*` |
| leakdetection | `hidra_leak_detection_*` | `hidra_leakdetection_*`, `hidra_alarm_*`, `hidra_incident_*` |
| hse | `hidra_hse_*` | `hidra_incident_*`, `hidra_integrity_*` |
| integrity | `hidra_integrity_*` | `hidra_topology_*`, `hidra_asset_*` |
| assets | `hidra_asset_*` | `hidra_assets_*`, `hidra_topology_*`, `hidra_integrity_*` |
| custody | `hidra_custody_*` | `hidra_telemetry_*`, `hidra_planning_*`, `hidra_finance_*` |
| configuration | `hidra_configuration_*` | module-owned catalog prefixes |

A module must not create tables using another module's prefix.

---

## 4. Missing-DDD blockers

The following contexts are referenced but blocked until a dedicated DDD file exists under `docs/data definition`:

```text
alarm management
incident management
party / external legal-entity master data
risk
analytics
reporting
contracts / commercial
finance / ERP accounting
```

Blocked means no Java package, package-info skeleton, migration, JPA entity, repository, controller, DTO, or service may be generated for these contexts from references alone.

Allowed until a DDD exists: neutral reference fields, snapshot fields, target type codes, and future-module notes.

---

## 5. Cross-module reference policy

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
Document document;
MaintenanceWorkOrder workOrder;
IntegrityCase integrityCase;
HseCase hseCase;
CustodyTransferTicket ticket;
```

A business module must not import another business module's `domain.*`, `infrastructure.*`, or `api.rest.*` packages.

---

## 6. Documentation and annotation policy

Domain, application, and infrastructure models must not use Swagger/OpenAPI annotations.

`@Schema` is allowed only in:

```text
dz.sh.hidra.modules.<module>.api.rest.request
dz.sh.hidra.modules.<module>.api.rest.response
```

Controllers may use `@Tag`, `@Operation`, `@ApiResponse`, `@ApiResponses`, and `@Parameter`.

Domain models must use Javadoc for domain documentation.

---

## 7. Boundary decision matrix

| Ambiguous concept | Correct owner | Allowed reference elsewhere | Forbidden interpretation |
|---|---|---|---|
| Physical pipeline / station / facility | topology | topology neutral reference | Assets or HSE owns topology |
| Maintainable lifecycle | assets | topology neutral reference | Topology owns work orders |
| Defect / corrosion / remaining life | integrity | topology neutral reference | Assets owns integrity decisions |
| Safety/environment consequence | hse | incident/leak/alarm neutral reference | Incident doubles as HSE case |
| Official transfer quantity | custody | telemetry/planning snapshots | Telemetry is fiscal truth |
| Deviation detection | monitoring | telemetry/planning references | Monitoring owns alarms/incidents |
| Leak suspicion | leakdetection | monitoring/alarm/incident neutral references | LeakDetection owns incidents |
| Runtime config | configuration | scoped config reference | Configuration owns business taxonomies |

---

## 8. Code-generation rules

1. Read this master DDD and the target module DDD before generating code.
2. Generate code only inside the requested module.
3. Never create `shared`, `common`, `core`, `utils`, `helper`, `helpers`, or `misc` packages.
4. Never import another module's domain or infrastructure packages.
5. Keep domain framework-independent.
6. Keep JPA entities in `infrastructure.persistence.entity` only.
7. Keep REST request/response models in `api.rest.request` and `api.rest.response` only.
8. Use `@Schema` only on API request/response models.
9. Represent cross-module dependencies as IDs, snapshots, ports, DTOs, or events.
10. Stop if a task requires a blocked module.

---

## 9. Final rule

A module owns meaning, not just tables. When another module needs that data, it references it through stable IDs, snapshots, public ports, events, or projections.
