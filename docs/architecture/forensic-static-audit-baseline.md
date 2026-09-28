# HidraAPI Forensic Static Audit Baseline

**Recorded:** 2026-09-28  
**Purpose:** evidence baseline for the HRA repository-remediation roadmap.

## 1. Audit method

The forensic audit was performed against the user-supplied source ZIP. Every Java file under the supplied `src` tree was read as raw text rather than classified from filenames alone.

The analysis inspected:

- package declarations and imports;
- type declarations;
- records/components and class fields;
- JPA annotations such as `@Entity`, `@Table`, `@Id`, `@Column`, and `@Enumerated`;
- model/enum structural similarity;
- incoming static Java references;
- module and layer dependencies;
- module-crossing imports;
- domain/JPA field-shape drift;
- event and publisher references;
- API-to-domain imports.

This is a Java-source audit. Database/Flyway conclusions must be verified against the live migration/schema source before database changes are made.

## 2. Baseline metrics

| Metric | Baseline |
|---|---:|
| Java files read | 4,988 |
| Production Java files | 4,914 |
| Test Java files | 74 |
| Production model/enum types | 1,783 |
| Domain models | 467 |
| JPA entities | 465 |
| Data records | 431 |
| Record value objects | 82 |
| Enums | 338 |
| Same-named domain/JPA pairs | 465 |
| Exact domain/JPA structural clones | 451 |
| Exact behaviorless domain/JPA clones | 393 |
| Zero-incoming model/enum candidates | 210 |
| Zero-incoming module domain-event records | 100 / 100 |
| Unreferenced/unwired module event publishers | 24 |
| Exact Request/Command pairs | 65 |
| Exact Response/SummaryDto pairs | 59 |
| Cross top-level boundary imports | 30 |
| API-to-domain imports | 136 |
| API-to-domain enum imports | 125 |
| Unresolved explicit internal imports | 0 |
| Duplicate JPA table mappings | 0 |
| Duplicate entity columns | 0 |
| Explicit production import cycles | 0 |

## 3. Confirmed critical/high findings

### 3.1 Organization Employee birth-data persistence drift

Canonical Organization `Employee` includes:

```text
dateOfBirth
birthLocalityId
birthPlaceAr
birthPlaceFr
birthPlaceEn
```

The audited `EmployeeJpaEntity` did not contain those fields, and the audited
`OrganizationPersistenceMapper` did not map them. The domain-to-JPA-to-domain round trip
therefore loses these canonical values.

This is the first data-integrity defect to repair after repository safety rails are installed.

### 3.2 Employee contracts lag the canonical domain

The audited registration/application/API path still carried legacy display/contact state
(`displayNameAr`, `displayNameLt`, `emailAddress`, `mobileNumber`) while the canonical
domain work established structured-name display semantics, contact-point ownership, and birth
data.

The vertical slice must be reconciled deliberately rather than allowing the domain to advance
without persistence/application/API support.

### 3.3 Domain/JPA invariant mismatch

The audit found hundreds of same-named domain/JPA pairs in which JPA-required String state can
be normalized to null in the domain without constructor rejection. The repository-remediation
roadmap requires a module-by-module semantic classification before mass hardening.

### 3.4 Disconnected event/outbox conception

The audit found all 100 module event records with zero incoming Java references and 24 module
event-publisher implementations with no real wiring. Module event contracts were not aligned
with the kernel event contract, while the platform serializer/outbox path was incomplete.

The repository must either implement a real event/outbox architecture or delete the fictional
scaffolding after consumer verification.

### 3.5 Industrial-scale domain/JPA mirroring

Of 465 same-named domain/JPA pairs, 451 were exact structural clones and 393 exact pairs had no
detected meaningful business behavior beyond normalization/helper plumbing.

Separate persistence/domain models are not inherently wrong; however, behaviorless mirrors
must be classified as real domain, boundary models, or redundant persistence/read models.

### 3.6 Dead-code candidates

210 production model/enum types had zero incoming static Java references, including 100 module
domain-event records. These are candidates only. Reflection, configuration, serialization,
SQL, generated code, and external consumers must be checked before deletion.

### 3.7 Boundary leakage/coupling

The audit identified explicit business-module coupling including:

- Planning importing Workflow internal application ports.
- Organization importing a Topology internal application port.

It also found API-to-domain representation leakage, including a planning controller receiving
a domain `PlanRevision` and identity web code handling domain principal/session types.

### 3.8 Scalar-ID persistence model

The audited entities used scalar identifier references rather than JPA object associations.
This is not classified as a defect by itself. Same-module referential integrity must be
verified in Flyway/schema and integration tests; cross-module database foreign keys must not
be invented.

## 4. Integrity checks that passed

The audit did not fabricate failures where none were found:

- zero unresolved explicit internal imports;
- zero duplicate JPA table names;
- zero duplicate entity columns;
- no detected enum persistence field missing `@Enumerated`;
- zero explicit production import cycles;
- zero API-to-infrastructure imports;
- zero application-to-infrastructure imports.

## 5. Remediation authority

This document is evidence for `docs/roadmap/repository-remediation.md`.

The live GitHub repository remains the implementation source of truth. Before each HRA task:

1. read `AGENTS.md`;
2. read the HRA roadmap;
3. inspect current `main`;
4. execute exactly one HRA code;
5. update the HRA roadmap status;
6. use the exact HRA commit message;
7. report exact validation evidence.

Do not treat this baseline as permission to delete a type, change a schema, or merge duplicated
concepts without the task-specific evidence gate defined by the roadmap.
