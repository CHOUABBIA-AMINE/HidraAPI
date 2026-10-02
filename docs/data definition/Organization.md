# HIDRA Organization Module — Data Definition Document

```text
Document code : HIDRA-ORGANIZATION-DDD
Repository    : HidraAPI
Module        : organization
Package root  : dz.sh.hidra.modules.organization
Table prefix  : hidra_org_*
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC : Digitalization Initiative
Author        : Abir MEDJERAB
UpdatedOn     : 2026-06-11
Status        : Active DDD reference
Version       : 1.3
```

---

## 1. Purpose

The `organization` module owns Sonatrach/TRC internal organizational structure, employees, positions, assignments, reporting lines, responsibility scopes, shifts, contact points, and internal delegation of responsibility.

It is not an identity module, not a topology module, and not an external legal-party master-data module.

---

## 2. Canonical implementation identity

```text
Module name   : organization
Package root  : dz.sh.hidra.modules.organization
Table prefix  : hidra_org_*
```

No implementation class, migration, or package may use `dz.sh.hidra` as the organization module root. Older repository-baseline wording is historical only.

Forbidden table prefixes:

```text
hidra_organization_*
hidra_identity_*
hidra_topology_*
hidra_workflow_*
hidra_audit_*
```

---

## 3. Ownership

Organization owns:

```text
OrganizationUnit
OrganizationUnitType
OrganizationUnitTypeTranslation
Position
Employee
EmployeeAddress
AdministrativeState
AdministrativeDistrict
AdministrativeLocality
EmployeeAssignment
ReportingLine
ResponsibilityAssignment
OrganizationDelegation
Shift
ShiftAssignment
OrganizationContactPoint
OrganizationHierarchySnapshot
```

Organization does not own:

```text
User
Group
Role
Permission
Facility
Pipeline
Equipment
WorkflowTask
WorkflowInstance
AuditRecord
TelemetryPoint
TelemetryReading
Party
Vendor
Contractor
Manufacturer
JointVenturePartner
```

External legal parties remain blocked until a dedicated `party` DDD exists.

---

## 4. Critical distinctions

```text
OrganizationUnit(type = STATION)
```

means a people/responsibility organization unit for a station. It is not the physical station facility. The physical station is owned by `topology.Facility`.

A `Position` is an operational function. It is not an identity `Role` and does not grant permissions by itself.

An `Employee` is an organizational person. It is not an identity `User`. The optional `identityUserReference` is a neutral reference only.

---

## 5. Entity catalogue

| Entity | Table | Purpose |
|---|---|---|
| OrganizationUnit | `hidra_org_unit` | Internal organization unit such as company, division, region, area, station-as-organization-unit, team. |
| OrganizationUnitType | `hidra_org_unit_type` | Catalog of organization unit types. |
| OrganizationUnitTypeTranslation | `hidra_org_unit_type_translation` | Multilingual labels for unit types. |
| Position | `hidra_org_position` | Operational position/function. |
| Employee | `hidra_org_employee` | Operational employee/person. |
| EmployeeAddress | `hidra_org_employee_address` | Employee address using locality as the normalized anchor. |
| AdministrativeState | `hidra_org_administrative_state` | Algerian state/wilaya reference. |
| AdministrativeDistrict | `hidra_org_administrative_district` | District/daïra reference under state. |
| AdministrativeLocality | `hidra_org_administrative_locality` | Locality/commune reference under district. |
| EmployeeAssignment | `hidra_org_employee_assignment` | Assignment of employee to unit/position/scope. |
| ReportingLine | `hidra_org_reporting_line` | Reporting relation between positions, units, or employees. |
| ResponsibilityAssignment | `hidra_org_responsibility_assignment` | Responsibility over operational scopes by neutral reference. |
| OrganizationDelegation | `hidra_org_delegation` | Temporary delegation of responsibility. |
| Shift | `hidra_org_shift` | Shift definition. |
| ShiftAssignment | `hidra_org_shift_assignment` | Employee assignment to shift. |
| OrganizationContactPoint | `hidra_org_contact_point` | Operational contact information. |
| OrganizationHierarchySnapshot | `hidra_org_hierarchy_snapshot` | Snapshot of organization hierarchy for historical display/use. |

---

## 6. Employee identity rules

`fullName` must not be the canonical persisted person-name field.

Employee names must be stored separately:

```text
firstNameAr
lastNameAr
firstNameLt
lastNameLt
displayNameAr
displayNameLt
```

`displayNameAr` and `displayNameLt` are display projections and must not replace structured names.

---

## 7. Address normalization

`EmployeeAddress` stores only `localityId` for normalized Algerian administrative address hierarchy.

Derivation:

```text
EmployeeAddress.localityId
  -> AdministrativeLocality.districtId
      -> AdministrativeDistrict.stateId
          -> AdministrativeState
```

`EmployeeAddress` must not duplicate `stateId` or `districtId` as canonical fields.

---

## 8. Cross-module references

Organization may store neutral references and snapshots, for example:

```text
identityUserReference
operationalScopeType
operationalScopeId
operationalScopeCode
operationalScopeName
```

It must not import identity, topology, workflow, audit, telemetry, or party domain models.

---

## 9. Documentation and annotation rule

Domain, application, and infrastructure organization models must not use `@Schema` or other OpenAPI annotations.

`@Schema` is allowed only in organization API request/response models under:

```text
dz.sh.hidra.modules.organization.api.rest.request
dz.sh.hidra.modules.organization.api.rest.response
```
