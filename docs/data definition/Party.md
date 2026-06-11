# HIDRA Party Module — Data Definition Document

```text
Document code : HIDRA-PARTY-DDD
Repository    : HidraAPI
Module        : party
Package root  : dz.sh.hidra.modules.party
Table prefix  : hidra_party_*
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC : Digitalization Initiative
Author        : Abir MEDJERAB
UpdatedOn     : 2026-06-11
Status        : Active DDD reference
Version       : 1.0
```

---

## 1. Purpose

The `party` module owns external legal-entity and counterparty master data used across Hidra / HyFlo.

It defines and governs the parties that interact with Sonatrach/TRC operations, including suppliers, vendors, contractors, manufacturers, customers, shippers, buyers, sellers, owners, operators, joint-venture partners, transport partners, inspection bodies, certification bodies, and regulatory/contact organizations when they must be referenced as business counterparties.

Party is a master-data bounded context. It answers:

```text
Who is this external organization or person?
What legal identity identifies it?
Which business roles can it play?
Which contact points and addresses are trusted?
Which registrations, certifications, qualifications, and risk status are valid?
Which internal modules may reference it?
```

Party is not organization, not identity, not contracts, not finance, and not workflow.

---

## 2. Canonical implementation identity

```text
Module name   : party
Package root  : dz.sh.hidra.modules.party
Table prefix  : hidra_party_*
```

Forbidden table prefixes:

```text
hidra_org_*
hidra_identity_*
hidra_asset_*
hidra_custody_*
hidra_contract_*
hidra_finance_*
hidra_erp_*
hidra_topology_*
```

No table in another module may store party master data. Other modules store only party references and snapshots.

---

## 3. Ownership

Party owns:

```text
Party
PartyType
PartyTypeTranslation
PartyRole
PartyRoleTranslation
PartyRoleAssignment
PartyLegalProfile
PartyRegistration
PartyTaxIdentifier
PartyAddress
PartyContactPoint
PartyContactPerson
PartyBankReference
PartyQualification
SupplierQualification
VendorQualification
ContractorQualification
ManufacturerProfile
OwnerProfile
OperatorProfile
PartyCertification
PartyComplianceStatus
PartyRiskSnapshot
PartyRelationship
PartyOwnershipLink
PartyDocumentReference
PartyExternalReference
PartyStatusHistory
PartyCatalogEntry
PartyCatalogTranslation
```

Party does not own:

```text
Employee
OrganizationUnit
User
Role
Permission
Facility
Pipeline
Equipment
MaintenanceWorkOrder
CustodyTransferTicket
Contract
PurchaseOrder
Invoice
ERP posting
WorkflowTask
AuditRecord
Document binary
```

---

## 4. Critical vocabulary

### 4.1 Party

A `Party` is a legal or recognized business actor that can be referenced by one or more modules.

Examples:

```text
national oil company
pipeline operator
supplier company
vendor company
contractor company
manufacturer
inspection body
calibration laboratory
customer
shipper
buyer
seller
joint-venture partner
asset owner
product owner
external regulator/contact organization
```

### 4.2 Party type

`PartyType` describes the nature of the party:

```text
LEGAL_ENTITY
NATURAL_PERSON
PUBLIC_AUTHORITY
INTERNAL_AFFILIATE
JOINT_VENTURE
CONSORTIUM
LABORATORY
CERTIFICATION_BODY
REGULATOR
```

Party type is not a Java enum in the domain model. It is catalog-backed because names, descriptions, and regulatory classifications are user-facing and may evolve.

### 4.3 Party role

`PartyRole` describes what the party does in a business context:

```text
SUPPLIER
VENDOR
CONTRACTOR
MANUFACTURER
OWNER
OPERATOR
SHIPPER
CUSTOMER
BUYER
SELLER
JOINT_VENTURE_PARTNER
INSPECTION_BODY
CALIBRATION_BODY
CERTIFICATION_BODY
TRANSPORT_PARTNER
SERVICE_PROVIDER
REGULATOR_CONTACT
```

A single party may have multiple roles.

Example:

```text
One company may be both VENDOR and MANUFACTURER.
One company may be both OWNER and JOINT_VENTURE_PARTNER.
One company may be SUPPLIER for spares and CONTRACTOR for maintenance.
```

---

## 5. Boundary distinctions

### 5.1 Party vs Organization

```text
Organization owns internal Sonatrach/TRC people, units, positions, shifts, and reporting lines.
Party owns external legal entities and counterparties.
```

`Employee` remains in organization.
`User`, roles, permissions, groups, and external identity-provider mappings remain in identity.
`Vendor`, `Supplier`, `Contractor`, `Manufacturer`, `Owner`, and `JointVenturePartner` are party roles, not organization units.

### 5.2 Party vs Assets

Assets may reference a manufacturer, warranty provider, maintenance contractor, or spare-part supplier, but Assets does not own party master data.

Correct pattern:

```text
AssetManufacturerReference
  partyId
  partyCodeSnapshot
  partyNameSnapshot
  partyRoleCodeSnapshot = MANUFACTURER
```

### 5.3 Party vs Custody

Custody may reference sellers, buyers, shippers, owners, operators, and agreement parties, but Custody does not own party master data.

Correct pattern:

```text
CustodyAgreementParty
  partyId
  partyCodeSnapshot
  partyNameSnapshot
  custodyRoleCode
```

### 5.4 Party vs Contracts / Commercial

Party owns who the counterparty is.
Contracts / Commercial owns the agreement terms, obligations, prices, commercial clauses, purchase orders, and contract lifecycle.

Since `contracts / commercial` does not yet have a dedicated DDD, Party must not create contract tables.

### 5.5 Party vs Finance / ERP

Party owns legal/counterparty identity.
Finance/ERP owns invoicing, accounting, vendor accounts payable, customer accounts receivable, tax posting, and ERP ledgers.

Since `finance / ERP accounting` does not yet have a dedicated DDD, Party must not create invoice, accounting, or posting tables.

---

## 6. Entity catalogue

| Entity | Table | Purpose |
|---|---|---|
| Party | `hidra_party_party` | Master record for external legal entity or recognized business actor. |
| PartyType | `hidra_party_type` | Catalog of party types. |
| PartyTypeTranslation | `hidra_party_type_translation` | Multilingual labels for party types. |
| PartyRole | `hidra_party_role` | Catalog of roles a party can play. |
| PartyRoleTranslation | `hidra_party_role_translation` | Multilingual labels for party roles. |
| PartyRoleAssignment | `hidra_party_role_assignment` | Assignment of one or more roles to a party. |
| PartyLegalProfile | `hidra_party_legal_profile` | Legal name, trade name, jurisdiction, legal form, registration summary. |
| PartyRegistration | `hidra_party_registration` | Commercial, regulatory, or legal registration. |
| PartyTaxIdentifier | `hidra_party_tax_identifier` | Tax or fiscal identifier reference. |
| PartyAddress | `hidra_party_address` | Legal, billing, shipping, operational, or correspondence address. |
| PartyContactPoint | `hidra_party_contact_point` | Phone, email, website, EDI, or other contact channel. |
| PartyContactPerson | `hidra_party_contact_person` | Contact person for a party, not an internal employee. |
| PartyBankReference | `hidra_party_bank_reference` | Bank reference metadata only; no payment execution. |
| PartyQualification | `hidra_party_qualification` | General qualification status for operational/commercial use. |
| SupplierQualification | `hidra_party_supplier_qualification` | Qualification specific to supplier role. |
| VendorQualification | `hidra_party_vendor_qualification` | Qualification specific to vendor role. |
| ContractorQualification | `hidra_party_contractor_qualification` | Qualification specific to contractor role. |
| ManufacturerProfile | `hidra_party_manufacturer_profile` | Manufacturer-specific identity and capability metadata. |
| OwnerProfile | `hidra_party_owner_profile` | Ownership role profile for assets, products, custody, or JV contexts. |
| OperatorProfile | `hidra_party_operator_profile` | Operator role profile for operational counterparties. |
| PartyCertification | `hidra_party_certification` | Certification/approval evidence for party capability. |
| PartyComplianceStatus | `hidra_party_compliance_status` | Compliance screening or eligibility status. |
| PartyRiskSnapshot | `hidra_party_risk_snapshot` | Snapshot of party risk classification from approved source. |
| PartyRelationship | `hidra_party_relationship` | Relationship between parties, such as parent/subsidiary/affiliate. |
| PartyOwnershipLink | `hidra_party_ownership_link` | Ownership relation between parties or external business objects by neutral reference. |
| PartyDocumentReference | `hidra_party_document_reference` | Neutral reference to document metadata. |
| PartyExternalReference | `hidra_party_external_reference` | Reference to ERP, procurement, registry, or external master-data system. |
| PartyStatusHistory | `hidra_party_status_history` | Append-only party lifecycle status history. |
| PartyCatalogEntry | `hidra_party_catalog_entry` | Party-owned catalog entry. |
| PartyCatalogTranslation | `hidra_party_catalog_translation` | Multilingual labels for party-owned catalog entries. |

---

## 7. Core entity definitions

### 7.1 Party

Table:

```text
hidra_party_party
```

Fields:

| Field | Logical type | Required | Description |
|---|---:|---:|---|
| id | PartyId | Yes | Stable party identifier. |
| code | BusinessCode | Yes | Unique party code. |
| partyTypeId | ReferenceId | Yes | Reference to `PartyType`. |
| legalName | LocalizedText | Yes | Official legal name. |
| tradeName | LocalizedText | No | Commercial or commonly used name. |
| shortName | LocalizedText | No | Short display name. |
| countryCode | CountryCode | Yes | Country of legal establishment. |
| jurisdictionCode | String | No | Legal jurisdiction/registry area. |
| status | StatusCode | Yes | `DRAFT`, `ACTIVE`, `SUSPENDED`, `BLOCKED`, `RETIRED`. |
| primaryRoleCodeSnapshot | String | No | Main role for display only. Source of truth is `PartyRoleAssignment`. |
| createdAt | Instant | Yes | Creation timestamp. |
| updatedAt | Instant | Yes | Last update timestamp. |

Rules:

```text
Party code must be unique.
A party must have exactly one party type.
A party may have multiple active roles.
Blocked parties must not be selected for new operational references unless explicitly overridden by authorized workflow.
Party must not duplicate employee or user identity.
```

### 7.2 PartyRoleAssignment

Table:

```text
hidra_party_role_assignment
```

Fields:

| Field | Logical type | Required | Description |
|---|---:|---:|---|
| id | PartyRoleAssignmentId | Yes | Stable assignment identifier. |
| partyId | FK -> Party | Yes | Party receiving the role. |
| roleId | FK -> PartyRole | Yes | Assigned party role. |
| validFrom | Instant | Yes | Role validity start. |
| validTo | Instant | No | Role validity end. |
| status | StatusCode | Yes | `ACTIVE`, `SUSPENDED`, `EXPIRED`, `REVOKED`. |
| qualificationRequired | Boolean | Yes | Whether this role requires qualification. |
| createdAt | Instant | Yes | Creation timestamp. |
| updatedAt | Instant | Yes | Last update timestamp. |

Rules:

```text
A party may not have duplicate active assignments for the same role.
A role assignment must be valid before another module may use that party for the matching role.
Historical role assignments must not be physically deleted.
```

### 7.3 SupplierQualification

Table:

```text
hidra_party_supplier_qualification
```

Purpose:

Defines whether a party is approved to act as a supplier for categories such as spare parts, equipment, consumables, services, or technical materials.

Fields include:

```text
id
partyId
supplierCategoryCode
qualificationStatus
approvedFrom
approvedTo
approvalReferenceId
riskLevelSnapshot
lastReviewDate
nextReviewDate
createdAt
updatedAt
```

### 7.4 VendorQualification

Table:

```text
hidra_party_vendor_qualification
```

Purpose:

Defines whether a party is approved as a vendor in procurement/commercial workflows.

Vendor qualification does not create ERP vendor accounts or accounting postings.

### 7.5 OwnerProfile

Table:

```text
hidra_party_owner_profile
```

Purpose:

Defines ownership role metadata for contexts where a party is an owner of assets, hydrocarbons, custody quantities, facilities, JV shares, or commercial rights.

OwnerProfile must not directly own topology assets, custody tickets, or finance records. Other modules reference the owner party through neutral snapshots.

---

## 8. Cross-module reference model

Other modules reference party using:

```text
partyId
partyCodeSnapshot
partyNameSnapshot
partyRoleCodeSnapshot
partyStatusSnapshot
```

When role is important:

```text
partyRoleCode
partyRoleAssignmentId
partyRoleStatusSnapshot
```

Allowed examples:

```text
AssetManufacturerReference.partyId
AssetWarranty.providerPartyId
MaintenanceWorkOrder.contractorPartyId
CustodyAgreementParty.partyId
TopologyOwnerReference.partyId
IntegrationExternalPartyMapping.partyId
```

Forbidden examples outside party:

```text
Party party;
Supplier supplier;
Vendor vendor;
Owner owner;
Manufacturer manufacturer;
Contractor contractor;
```

Other modules must not import:

```text
dz.sh.hidra.modules.party.domain.*
dz.sh.hidra.modules.party.infrastructure.*
dz.sh.hidra.modules.party.api.rest.*
```

---

## 9. External references

Party may store references to external master-data systems without becoming those systems.

Examples:

```text
ERP vendor code
ERP customer code
procurement supplier code
commercial registry identifier
tax authority identifier
external compliance screening identifier
```

These are identifiers and snapshots only. Finance/ERP and procurement/commercial modules own transactional records when their DDDs exist.

---

## 10. Documentation and annotation rule

Domain, application, and infrastructure party models must not use `@Schema` or OpenAPI annotations.

`@Schema` is allowed only in party API request/response models under:

```text
dz.sh.hidra.modules.party.api.rest.request
dz.sh.hidra.modules.party.api.rest.response
```

Controllers may use OpenAPI annotations.

---

## 11. Code-generation guardrails

A code-generation task for party may create party module artifacts only when explicitly requested.

It must not create:

```text
contracts/commercial packages
finance/ERP packages
procurement packages
asset work-order logic
custody ticket logic
identity user logic
organization employee logic
```

Party is the master-data owner for external legal entities and roles. It is not the transactional owner of contracts, invoices, payments, maintenance execution, custody transfer, workflow approval, or audit evidence.
