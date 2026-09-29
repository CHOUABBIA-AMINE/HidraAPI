# ORG-023 — Operational-scope contracts and legacy-tuple inventory

**Issue:** [#130](https://github.com/CHOUABBIA-AMINE/HidraAPI/issues/130)  
**Design:** [ADR-0005](../adr/0005-organization-operational-scope-integrity.md)  
**Historical evidence baseline:** HidraAPI `main` at `d94b9d72aac32863d971b6f7409c407faa647152` (2026-09-24).  
**Current source refresh head:** `c1fb9fbfefaecc69b59af28a74d1509294d212c8` (2026-09-29).  
**Status:** Repository/source contracts, current owner-resolution contracts and in-repository compatibility consumers are inventoried on current `main`. On 2026-09-29 the project owner clarified that HidraAPI is greenfield: **no deployed database exists and there are no legacy rows to migrate**. Therefore the former legacy-data evidence gate is not applicable. **ORG-023 is Completed under the greenfield assumption.** Historical audit material is retained only as a defensive reference and must not be treated as a prerequisite for a database that does not exist.

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


## 6. Current-main source and consumer refresh

**Assessment execution head:** `c1fb9fbfefaecc69b59af28a74d1509294d212c8`  
**Refresh date:** 2026-09-29

Later Organization work materially changed the source model after the original ORG-023 baseline.
This section is the current source-of-truth for repository-side ORG-023 evidence; historical sections
above are retained to preserve the original findings.

### 6.1 Canonical model now present

The current responsibility identity is:

```text
ResponsibilityAssignment.scopeId : Long
        |
        v
OperationalScope.id : Long
OperationalScope.type : OperationalScopeType
OperationalScope.targetId : owner-native ID
        |
        v
OperationalScopeTargetResolverPort
```

`OperationalScopeReference` now contains only `type` plus owner-native `targetId`; mutable
code/name display attributes are not part of canonical reference identity. `GLOBAL` has no target,
entity-backed types require one, and unregistered `CUSTOM` fails closed.

The additive registry migration is already present as
`V20260927_001__add_operational_scope_registry.sql`. ORG-028 later added
`V20260929_003__harden_operational_scope_responsibility_concurrency.sql` without changing the
original registry migration.

### 6.2 Owner-resolution readiness on current main

| Scope type | Current owner-resolution evidence | Repository-side status |
|---|---|---|
| `GLOBAL` | Registry-owned targetless scope; no external owner lookup | Ready in source |
| `ORGANIZATION_UNIT` | `AuthoritativeOperationalScopeTargetResolverAdapter` resolves through Organization-owned `OrganizationUnitRepositoryPort` and derives assignability from unit lifecycle | Ready in source |
| `PIPELINE_SYSTEM` | Topology-owned `TopologyOperationalScopeTargetContract.resolvePipelineSystem` | Ready in source |
| `PIPELINE` | Topology-owned `TopologyOperationalScopeTargetContract.resolvePipeline` | Ready in source |
| `FACILITY` | Topology-owned `TopologyOperationalScopeTargetContract.resolveFacility` | Ready in source |
| `EQUIPMENT` | Topology-owned `TopologyOperationalScopeTargetContract.resolveEquipment` | Ready in source |
| `CUSTOM` | Deliberately rejected until a separately approved owner namespace exists | Not allowed |

This proves the application contracts exist. It does **not** prove that legacy IDs in a real database
match the intended owner records; that remains a data-evidence gate.

### 6.3 Current in-repository legacy compatibility consumers

A repository-wide source scan for the historical camel-case identifiers
`operationalScopeType/Id/Code/Name` finds production compatibility code only in the following
Organization files:

```text
domain/model/OrganizationUnit.java
domain/model/EmployeeAssignment.java
domain/model/ResponsibilityAssignment.java
infrastructure/persistence/entity/OrganizationUnitJpaEntity.java
infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java
infrastructure/persistence/entity/ResponsibilityAssignmentJpaEntity.java
infrastructure/persistence/mapper/OrganizationPersistenceMapper.java
```

Interpretation:

- `OrganizationUnit` and `EmployeeAssignment` no longer own canonical operational scope; their
  deprecated constructors/accessors exist only for compatibility and return no canonical scope state.
- `ResponsibilityAssignment` owns canonical `scopeId`; its deprecated textual bridge treats a
  numeric legacy argument only as an already-known registry ID and deliberately does not guess
  non-numeric owner IDs.
- the three JPA entities still map historical `operational_scope_*` columns so evidence can survive
  until controlled reconciliation/retirement;
- `OrganizationPersistenceMapper` is the remaining Java compatibility bridge for unit/employee
  legacy columns.

No current Organization REST request/response contract exposes the historical four-field tuple.
No direct Topology repository/JPA/infrastructure dependency is used for owner resolution.

The assessment test added with this refresh pins the above source-consumer set so any new legacy
consumer must be reviewed explicitly.

### 6.4 Read-only legacy assessment artifact

`docs/data-provisioning/operational-scope/ORG-037-legacy-scope-audit.sql` remains the approved
privacy-safe assessment template. The ORG-023 assessment test verifies every executable statement
is read-only (`SELECT` or `WITH ... SELECT`) and rejects DML/DDL additions.

The SQL reports aggregate source-shape evidence only. It cannot establish external target existence,
current labels, lifecycle eligibility, or business approval.

### 6.5 Greenfield clarification supersedes the legacy-data gate

On 2026-09-29 the project owner clarified the deployment state:

- HidraAPI has **no deployed database**;
- no legacy Organization rows exist;
- there is no production/staging operational-scope tuple population to backfill or quarantine;
- no external consumer can depend on legacy database columns from a database that has never existed.

Therefore the three evidence classes previously listed as blockers are **Not Applicable** for this
project state:

1. legacy tuple profile — N/A, because there are no legacy tuples;
2. owner-certified crosswalk of observed legacy IDs — N/A, because no observed legacy IDs exist;
3. external sign-off for legacy database-column consumers — N/A for deployed DB consumers, because
   no deployed legacy database exists.

Repository/API compatibility consumers remain a source-code concern and are already inventoried.
They are removed by the later greenfield cleanup tasks rather than migrated from deployed data.

The read-only ORG-037 audit SQL remains in the repository as a defensive/historical assessment
artifact. It is not required before continuing the greenfield roadmap.

## 7. Refreshed exit status

- Current repository contract/consumer inventory: **Completed**.
- Current source owner-resolution availability matrix: **Completed**.
- Privacy-safe aggregate assessment SQL: **Retained and regression-tested as read-only**.
- Legacy tuple profile: **Not Applicable — no database / no legacy rows**.
- Legacy typed-ID crosswalk: **Not Applicable — no legacy rows**.
- External legacy database-consumer sign-off: **Not Applicable — no deployed legacy database**.
- **ORG-023 overall status: Completed — greenfield/no legacy migration.**

ORG-029 is now authorized as a **greenfield persistence-alignment task**. It must not implement a
fictional backfill/quarantine pipeline for data that does not exist.


## 8. Greenfield execution decision

This section is authoritative over the historical blocked conclusions above.

**Decision date:** 2026-09-29  
**Project state:** greenfield / no database has been deployed.

Consequences for the remaining Organization scope roadmap:

- ORG-029 validates fresh-database canonical persistence and eliminates unsafe compatibility
  write paths; it performs no legacy backfill or quarantine.
- ORG-030 exposes only canonical REST contracts and server-derived security context.
- ORG-031 removes obsolete Java/JPA compatibility bridges after API cutover tests.
- ORG-032 adds a new Flyway cleanup migration that removes obsolete compatibility columns and
  enforces final canonical constraints on a fresh migration chain; previously numbered migrations
  remain immutable.
- ORG-033 verifies a database created from an empty PostgreSQL instance through the complete Flyway
  chain plus end-to-end Organization behavior.

If a real legacy database is introduced later, this greenfield decision must be reopened before any
attempt to import or reconcile it.
