# COR2-007 — Remaining User-Facing Type Enum Audit

## 1. Task

| Field | Value |
|---|---|
| Roadmap | `docs/roadmap/correction_02.md` |
| Task | `COR2-007` |
| Commit message | `chore(domain): audit remaining user-facing type enums` |
| Scope | Classify remaining enum and enum-like `domain/value` type classifications |
| Status | Completed as audit artifact |

---

## 2. Search Inputs

The audit used connector-backed repository searches equivalent to the roadmap commands:

```bash
find src/main/java/dz/sh/hidra/modules -path '*/domain/value/*.java' -print | sort
grep -R "enum " src/main/java/dz/sh/hidra/modules/*/domain/value || true
grep -R "@Deprecated" src/main/java/dz/sh/hidra/modules/*/domain/value || true
grep -R "TypeReference" src/main/java/dz/sh/hidra/modules/*/domain/value || true
```

Verified search groups:

```text
1. Remaining real Java enum declarations in module domain/value packages.
2. Deprecated enum-like compatibility constant classes in module domain/value packages.
3. Catalog reference objects already available as target replacements.
4. Status enums that should remain enums.
```

---

## 3. Classification Matrix

| Type | Module | Current kind | Used by / meaning | Decision | Replacement / follow-up |
|---|---|---|---|---|---|
| `RoleStatus` | identity | `STATUS_ENUM` | Role lifecycle status | KEEP | Remains enum because it is lifecycle state, not editable catalog data. |
| `UserStatus` | identity | `STATUS_ENUM` | User lifecycle status | KEEP | Remains enum because it is lifecycle state, not editable catalog data. |
| `EmploymentStatus` | organization | `STATUS_ENUM` | Employee lifecycle status | KEEP | Remains enum because it is lifecycle state, not editable catalog data. |
| `OrganizationUnitStatus` | organization | `STATUS_ENUM` | Organization unit lifecycle status | KEEP | Remains enum because it is lifecycle state, not editable catalog data. |
| `TopologyStatus` | topology | `STATUS_ENUM` | Topology asset lifecycle status | KEEP | Remains enum because it is lifecycle state, not editable catalog data. |
| `TopologyAssetType` | topology | `TYPE_ENUM_TO_CATALOG` | Neutral topology parent/asset classification: pipeline system, pipeline, facility, node, segment, appurtenance, connection, equipment | REPLACE_FIRST_THEN_DELETE | Add `TopologyAssetTypeReference` / catalog or reuse `TopologyTypeCatalog` asset category model; then remove enum. |
| `FacilityType` | topology | Deprecated enum-like compatibility class | Facility business classification | REPLACE_FIRST_THEN_DELETE | Replace active usage with `FacilityTypeReference`; then delete wrapper and `FacilityTypeReference.from(FacilityType)`. |
| `NodeType` | topology | Deprecated enum-like compatibility class | Topology node business classification | REPLACE_FIRST_THEN_DELETE | Replace active usage with `NodeTypeReference`; then delete wrapper and bridge methods. |
| `ConnectionType` | topology | Deprecated enum-like compatibility class | Topology connection business classification | REPLACE_FIRST_THEN_DELETE | Replace active usage with `ConnectionTypeReference`; then delete wrapper and bridge methods. |
| `EquipmentType` | topology | Deprecated enum-like compatibility class | Equipment business classification | REPLACE_FIRST_THEN_DELETE | Replace active usage with `EquipmentTypeReference`; then delete wrapper and bridge methods. |
| `PipelineAppurtenanceType` | topology | Deprecated enum-like compatibility class | Pipeline appurtenance business classification | REPLACE_FIRST_THEN_DELETE | Replace active usage with `PipelineAppurtenanceTypeReference`; then delete wrapper and bridge methods. |
| `ValveType` | topology | Deprecated enum-like compatibility class | Valve business classification | REPLACE_FIRST_THEN_DELETE | Replace active usage with `ValveTypeReference`; then delete wrapper and bridge methods. |
| `ReportingLineType` | organization | Deprecated enum-like compatibility class | Reporting-line business classification with rule behavior | REPLACE_FIRST_THEN_DELETE | Replace with catalog reference plus explicit domain policy for behavior such as `allowsMultipleActiveLines`. |
| `OperationalScopeType` | organization | Deprecated enum-like compatibility class | Operational-scope classification exposed through organization APIs | REPLACE_FIRST_THEN_DELETE | Replace with catalog/reference model or a technical scope reference policy; delete wrapper after active callers move. |
| `ProductTypeReference` | topology | `REFERENCE_OBJECT` | Product type catalog reference | KEEP | Target model already used after COR2-003 / COR2-005. |
| `OrganizationUnitTypeReference` | organization | `REFERENCE_OBJECT` | Organization unit type catalog reference | KEEP | Target model already used after COR2-004 / COR2-006. |
| `FacilityTypeReference` | topology | `REFERENCE_OBJECT` | Facility type catalog reference | KEEP_AND_CLEAN_BRIDGE | Keep reference; remove `from(FacilityType)` after wrapper migration. |
| `NodeTypeReference` | topology | `REFERENCE_OBJECT` | Node type catalog reference | KEEP_AND_CLEAN_BRIDGE | Keep reference; remove deprecated wrapper bridge after active callers migrate. |
| `ConnectionTypeReference` | topology | `REFERENCE_OBJECT` | Connection type catalog reference | KEEP_AND_CLEAN_BRIDGE | Keep reference; remove deprecated wrapper bridge after active callers migrate. |
| `EquipmentTypeReference` | topology | `REFERENCE_OBJECT` | Equipment type catalog reference | KEEP_AND_CLEAN_BRIDGE | Keep reference; remove deprecated wrapper bridge after active callers migrate. |
| `PipelineAppurtenanceTypeReference` | topology | `REFERENCE_OBJECT` | Pipeline appurtenance type catalog reference | KEEP_AND_CLEAN_BRIDGE | Keep reference; remove deprecated wrapper bridge after active callers migrate. |
| `ValveTypeReference` | topology | `REFERENCE_OBJECT` | Valve type catalog reference | KEEP_AND_CLEAN_BRIDGE | Keep reference; remove deprecated wrapper bridge after active callers migrate. |

---

## 4. Required Follow-Up Work

### 4.1 Topology asset type

`TopologyAssetType` is the only remaining real business-type Java enum found in `domain/value`.

Decision:

```text
TYPE_ENUM_TO_CATALOG
```

Required follow-up:

```text
- Add or reuse a topology asset type catalog model.
- Add `TopologyAssetTypeReference` if a dedicated reference is needed.
- Replace enum imports in topology models/policies with references or catalog-aware policies.
- Remove `TopologyAssetType.java` after active callers migrate.
```

### 4.2 Deprecated topology wrappers

The following wrappers must be removed in follow-up cleanup tasks:

```text
FacilityType
NodeType
ConnectionType
EquipmentType
PipelineAppurtenanceType
ValveType
```

Required cleanup pattern:

```text
1. Replace constructor, command, query, mapper, and test usages with the matching `*TypeReference`.
2. Remove `from(<DeprecatedType>)` bridge methods from reference objects.
3. Delete the deprecated wrapper class.
4. Keep catalog labels in catalog tables/DTOs/responses, not reference objects.
```

### 4.3 Deprecated organization wrappers

The following wrappers must be removed in follow-up cleanup tasks:

```text
ReportingLineType
OperationalScopeType
```

Required cleanup pattern:

```text
1. Decide whether the concept is editable catalog data or technical policy state.
2. If user-facing or API-facing, replace with catalog/reference model.
3. Move behavior such as `allowsMultipleActiveLines` or `isStationScope` into explicit domain policy/services.
4. Delete the deprecated wrapper class once active callers migrate.
```

---

## 5. Accepted Enum Categories

The following are accepted as enums:

```text
RoleStatus
UserStatus
EmploymentStatus
OrganizationUnitStatus
TopologyStatus
```

Reason:

```text
They represent lifecycle state, not user-managed business classification catalogs.
```

---

## 6. Validation Status

Required local validation from the roadmap:

```bash
find src/main/java/dz/sh/hidra/modules -path '*/domain/value/*.java' -print | sort
grep -R "enum " src/main/java/dz/sh/hidra/modules/*/domain/value || true
mvn -q -DskipTests compile
```

Result in this connector-only session:

```text
NOT_RUN_IN_SANDBOX
Reason: repository was inspected through the GitHub connector; no local checkout or Maven execution environment was available in this session.
```

---

## 7. Acceptance Criteria Result

| Criterion | Result |
|---|---|
| Every `domain/value` enum or enum-like type is classified | Completed for connector-discovered enum/status/wrapper/reference type classifications |
| Every user-facing type enum has follow-up task or Sprint 1 scope | Completed: `TopologyAssetType` and deprecated wrappers have follow-up actions |
| Status enums explicitly marked as staying enum | Completed |
| Technical enums explicitly marked as staying enum | No standalone technical enum beyond status classification was found by connector search |
| Deprecated wrappers listed by COR2-002 have concrete plan | Completed |
