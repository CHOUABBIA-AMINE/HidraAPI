# ORG-023 — Operational-scope contracts and legacy-tuple inventory

**Issue:** [#130](https://github.com/CHOUABBIA-AMINE/HidraAPI/issues/130)  
**Design:** [ADR-0005](../adr/0005-organization-operational-scope-integrity.md)  
**Evidence baseline:** HidraAPI `main` at `d94b9d72aac32863d971b6f7409c407faa647152` (2026-09-24).  
**Status:** Source-contract inventory completed; **legacy database tuple inventory and owner-resolver review incomplete; ORG-023 Blocked** pending authorized read-only database/source access and full consumer verification. This document is assessment only; no production code, SQL, data or security state was modified.

## 1. Current owner and data-contract inventory

All links below refer to the inspected baseline commit. Paths are repository-relative; replace the prefix `src/main/java/dz/sh/hidra/modules/organization/` for the organization entries.

| Owner / layer | Inspected contract | Current scope behavior / correction impact |
|---|---|---|
| organization domain | `domain/value/OperationalScopeType.java` | Enum: `GLOBAL`, `ORGANIZATION_UNIT`, `PIPELINE_SYSTEM`, `PIPELINE`, `FACILITY`, `EQUIPMENT`, `CUSTOM`. Enum presence does not establish owner lookup availability. |
| organization domain | `domain/value/OperationalScopeReference.java` | Record `(OperationalScopeType, String id, String code, String name)`, string trim/null normalization and `none()`; no authoritative owner resolution or pair consistency validation in the record. |
| organization domain | `domain/model/OrganizationUnit.java` | Four independently persisted *String* fields for scope. Can represent a single embedded tuple, not a complete many-scope responsibility perimeter. |
| organization domain | `domain/model/EmployeeAssignment.java` | Same four fields, optional in SQL; risk of a second authority over employee operational responsibilities. |
| organization domain | `domain/model/ResponsibilityAssignment.java` | `assigneeType`, `assigneeId`, `ResponsibilityType`, four String scope fields, `validFrom/validTo`, `AssignmentStatus`; suitable starting point for repeatable scoped responsibilities but lacks audited existence/overlap validation in its record constructor. |
| organization domain | `domain/value/ResponsibilityType.java` | `OWNER`, `ACCOUNTABLE`, `RESPONSIBLE`, `SUPPORT`, `ESCALATION`, `APPROVER`. Role-specific overlap policy needed. |
| organization API | `api/rest/request/CreateOrganizationUnitRequest.java`, `api/rest/response/OrganizationUnitResponse.java` | Neither currently exposes an operational-scope tuple; do not fabricate a public create-unit scope contract from the domain fields. |
| organization application | `application/command/CreateOrganizationUnitCommand.java`, `application/service/OrganizationUnitApplicationService.java` | Unit service creates the four scope fields as `null`. |
| organization API + application | `api/rest/request/AssignEmployeeRequest.java`, `api/rest/mapper/OrganizationRestMapper.java`, `application/command/AssignEmployeeCommand.java`, `application/service/EmployeeAssignmentApplicationService.java` | Four client-provided scope fields are passed through independently; inspected service constructs and saves assignment without owner lookup. This is a **confirmed input-boundary risk**, not proof of any incorrect live records. |
| organization persistence | `infrastructure/persistence/entity/{OrganizationUnit,EmployeeAssignment,ResponsibilityAssignment}JpaEntity.java`, `infrastructure/persistence/mapper/OrganizationPersistenceMapper.java` | Persistence entity and mapper copy all four values in both directions. JPA/SQL nullability differs between unit/employee and responsibility assignments. |
| organization persistence | `application/port/out/ResponsibilityAssignmentRepositoryPort.java`, `infrastructure/persistence/adapter/JpaResponsibilityAssignmentRepositoryAdapter.java`, `infrastructure/persistence/repository/ResponsibilityAssignmentJpaRepository.java` | Only `save` and `findById` exposed by the application port; no scope/assignee/effective-time listing, overlap checking or owner resolution contract identified in these files. |
| topology public API | `topology/application/port/in/{CreatePipelineSystemUseCase,RegisterFacilityUseCase,TopologyMapVisualizationUseCase}.java` | Inspected current input-port directory contains creation and read-only map visualization; **no dedicated typed scope-ID existence/assignability resolver identified there**. Map features include entity IDs, codes, localized names and status, but GIS search must not be repurposed as a guaranteed owner validation contract. Other integration surfaces need review. |

**Additional known consumers:** the legacy org scope columns appear in `OrganizationPersistenceMapper`, the three entity types above and employee assignment request/command/service. A complete repository-wide source-content scan, including other modules, tests, serialization clients and HidraWEB, **has not been performed**. The current Git tree listing alone does not establish absence of other references. Expand this matrix after approved full-source scanning.

## 2. Physical schema inventory (definition, not inspected live DB)

Baseline: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`.

| Table | Scope type | Scope ID | Duplicate code/name | Other important facts |
|---|---|---|---|---|
| `hidra_org_unit` | `varchar(80)`, nullable | `varchar(120)`, nullable | `varchar(120)`, `varchar(255)`, nullable | one tuple per unit; single-column scope-ID index; no type+ID FK/validation shown |
| `hidra_org_employee_assignment` | `varchar(80)`, nullable | `varchar(120)`, nullable | `varchar(120)`, `varchar(255)`, nullable | independent tuple per employee membership; scope-ID index |
| `hidra_org_responsibility_assignment` | `varchar(80)`, **NOT NULL** | `varchar(120)`, **NOT NULL** | `varchar(120)`, `varchar(255)`, nullable | `assignee_type/id`, `responsibility_type`, mandatory `valid_from`, nullable `valid_to`; `GLOBAL` with no target ID cannot currently be persisted |

The inspected organization migration contains **no `FOREIGN KEY` declarations**. This observation applies to this SQL file, not to live DBA-managed constraints or later migrations. Do not modify the applied baseline migration. Do not assume one scope ID is unique across different target types.

## 3. Owner-resolver availability and readiness matrix

| Scope type | Authoritative owner / target concept | Verified lookup + current display + assignability contract at inspected boundary | Required evidence before use |
|---|---|---|---|
| `GLOBAL` | Organization policy marker; no entity | N/A; existing responsibility SQL requires ID, contrary to ADR | New additive migration supporting null target ID; validator ensures code/name/ID absent; explicit role permissions |
| `ORGANIZATION_UNIT` | Organization | `OrganizationUnitRepositoryPort` exists; not yet validated as an externally suitable registered owner resolver | ID existence, lifecycle, self/cycle policy, historical semantics |
| `PIPELINE_SYSTEM` | Topology | No dedicated resolver identified in inspected public input-port directory | Public owner port proving ID/type/status, current code/name and eligibility |
| `PIPELINE` | Topology | Same; not established by presence of graph or JPA model | Public resolver and retirement events/version policy |
| `FACILITY` | Topology | Same; `RegisterFacilityUseCase` writes, not a general reference resolver | Public read/validation port; do not equate facility name with ID |
| `EQUIPMENT` | Topology | Same; repository ports are internal outbound contracts, not cross-module public APIs | Public read/validation port and lifecycle rules |
| `CUSTOM` | Registered extension owner only | No owner registry contract verified | Reject until explicit approved namespace, resolver, access and lifecycle contract exists |

**Do not** grant responsibility merely from unit membership, use a geography code as a pipeline ID or use a topology JPA repository directly from organization.

## 4. Legacy tuple classification and privacy-safe reconciliation specification

**Data availability:** Authorized read-only production/staging PostgreSQL access, an approved sanitized export, and full legacy source-to-target mapping were **not available in this task session**. Actual counts for orphan targets, duplicate assignments, mismatched labels, invalid types and date overlaps are **UNKNOWN**, not zero. No source data or credentials have been placed in this document.

Classify each existing tuple (type, ID, code, name) from the three organization tables using **both** the source row's owner type and the target owner's verified record:

| Finding | Detection criterion | Safe disposition |
|---|---|---|
| empty legitimate unit/employee tuple | all four fields NULL/blank | Keep unassigned; do not create a scope. |
| partial tuple | type without ID, ID without type, code/name without identity | Quarantine; only reconcile against approved source/target evidence. |
| unsupported type | raw type absent from current enum or no implemented owner contract | Quarantine and investigate; never coerce to CUSTOM/GLOBAL. |
| type/ID mismatch or missing target | owner resolver rejects typed ID or target absent/ineligible | Quarantine; no blind backfill. |
| stale code/name snapshot | typed ID resolves but stored code/name differ from owner | Keep original for evidentiary review if necessary; use owner-resolved current labels only. |
| exact repeated assignment | same assignee/role/typed target and overlapping effective interval | Decide idempotency/dedup only after history and status review; no irreversible SQL deletion. |
| conflicting active overlaps | same assignee/role/typed target, overlapping windows | Quarantine for business decision; preserve different roles and scopes. |
| invalid dates | end <= start or missing mandatory start | Quarantine and reconstruct from approved history if possible. |
| GLOBAL carrying target fields | GLOBAL with ID/code/name populated | Quarantine; no synthetic GLOBAL ID. |
| independent employee scope differing from responsibility rows | different employee, role, target or interval | Trace provenance and approver; do not automatically overwrite either side. |

**Read-only aggregate query template — run only on an approved snapshot, privately; do not publish row-level operational identifiers in GitHub/CI logs:**

```sql
WITH legacy AS (
    SELECT 'unit' AS source_table, operational_scope_type AS t,
           operational_scope_id AS id, operational_scope_code AS code,
           operational_scope_name AS name
      FROM hidra_org_unit
    UNION ALL
    SELECT 'employee_assignment', operational_scope_type,
           operational_scope_id, operational_scope_code, operational_scope_name
      FROM hidra_org_employee_assignment
    UNION ALL
    SELECT 'responsibility_assignment', operational_scope_type,
           operational_scope_id, operational_scope_code, operational_scope_name
      FROM hidra_org_responsibility_assignment
)
SELECT source_table,
       COUNT(*) AS rows_total,
       COUNT(*) FILTER (WHERE NULLIF(BTRIM(COALESCE(t,'')), '') IS NULL
                         AND NULLIF(BTRIM(COALESCE(id,'')), '') IS NULL
                         AND NULLIF(BTRIM(COALESCE(code,'')), '') IS NULL
                         AND NULLIF(BTRIM(COALESCE(name,'')), '') IS NULL) AS empty_tuple,
       COUNT(*) FILTER (WHERE
           (NULLIF(BTRIM(COALESCE(t,'')), '') IS NULL) <>
           (NULLIF(BTRIM(COALESCE(id,'')), '') IS NULL)) AS partial_type_id,
       COUNT(*) FILTER (WHERE t = 'GLOBAL' AND (
           NULLIF(BTRIM(COALESCE(id,'')), '') IS NOT NULL OR
           NULLIF(BTRIM(COALESCE(code,'')), '') IS NOT NULL OR
           NULLIF(BTRIM(COALESCE(name,'')), '') IS NOT NULL)) AS global_with_target
  FROM legacy GROUP BY source_table ORDER BY source_table;
```

Additional authorized private checks: group by `(operational_scope_type, operational_scope_id)` per owner and resolve each ID; compare canonical current code/name to stored snapshots; count unmatched or retired targets by source table/type; count responsibility duplicates by `assignee_type, assignee_id, responsibility_type, scope_type, scope_id, status`; calculate true half-open window intersections by status; verify existing duplicate IDs/codes and foreign-key realities against the **live** PostgreSQL schema. Avoid false positives when responsibility roles differ.

**Approval gate:** write reconciliation findings as aggregate counts + classification, store any row-level triage only in approved private storage; require source owner and target owner sign-off before backfill, scope assignment or deletion. The SONATRACH organizational hierarchy does **not** prove a unit's physical network scope.

## 5. Exit status and next step

- Source-layer contract baseline and a preliminary topology owner-readiness matrix: **documented**.
- Legacy tuple profile, proof of owner-resolvable IDs and complete current consumer scan: **blocked / not verified**.
- Maven/test/DB execution: **not performed** (read-only repository inspection; no local full-repository workspace and no approved DB access supplied).
- ORG-023 must stay **Blocked** until complete evidence allows the task's original acceptance gate; do not start ORG-024 as though validation were complete. Once read-only DB access and complete consumer inventory are available, amend **the same ORG-023 task** with exact results and move it to Completed in a separate roadmap-controlled change. No migrations or other ORG implementation were authorized or performed here.
