# HidraAPI Telemetry Roadmap and Analysis

```text
Roadmap file : docs/roadmap/telemetry.md
Roadmap code : TEL
Scope        : Telemetry bounded context for industrial acquisition, telemetry sources, devices, points, readings, quality, topology bindings, catalogs, and localized labels
Repository   : HidraAPI
Namespace    : dz.sh.hidra
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-06-06
Status       : Planning and execution roadmap; no implementation code in this file
```

---

## 0. Scope check

| Check | Result | Rule |
|---|---:|---|
| Artifact type | Pass | This file is a roadmap and analysis artifact, not a raw build log. |
| Repository context | Pass | The file belongs to `HidraAPI` and package namespace `dz.sh.hidra`. |
| Roadmap location | Pass | The file is under `docs/roadmap/` because it supports planning and execution. |
| Raw compiler output | Pass | Raw compiler output must not be pasted into this file. |
| Stack traces | Pass | Unresolved stack traces must not be kept as roadmap analysis sections. |
| Repository namespace | Pass | Do not mix another repository, package root, or product namespace. |
| Telemetry treatment | Pass | Telemetry findings are treated as evidence and converted into sprint tasks. |

This document must be executable by a developer or AI agent. Each roadmap item must include a task code, commit message, files to create or update, purpose, rules, validation, and acceptance criteria.

---

## 1. Repository overview

| Field | Value |
|---|---|
| Repository | `HidraAPI` |
| Package root | `dz.sh.hidra` |
| Build tool | Maven |
| Runtime language | Java 21 |
| Main roadmap directory | `docs/roadmap` |
| Telemetry module root | `src/main/java/dz/sh/hidra/modules/telemetry` |
| Telemetry test root | `src/test/java/dz/sh/hidra/modules/telemetry` |
| Main migration directory | `src/main/resources/db/migration` |

HidraAPI is the backend foundation for Hidra, a hydrocarbon intelligence platform focused on data, risk, analytics, operational trust, validation, auditability, and industrial pipeline operations.

Telemetry is the next business module after the corrected foundation, organization, and topology baseline is validated.

Telemetry owns the industrial acquisition layer. It answers:

```text
Which source produced the signal?
Which device or tag produced it?
Which topology asset is it attached to?
When did the source produce it?
When did Hidra receive it?
What value, unit, quality, and state were received?
Can downstream modules trust the reading?
```

Telemetry does not own physical topology, hydraulic flow calculation, risk scoring, analytics models, workflow approvals, reporting, commercial allocation, or maintenance work orders.

---

## 2. Structural analysis by module

| Module | Status before telemetry | Telemetry dependency rule | Notes |
|---|---:|---|---|
| `kernel` | Existing foundation | May use kernel value-object, pagination, command/query, and exception primitives | Do not create `shared`, `common`, `core`, or `utils` packages. |
| `platform` | Existing infrastructure | May use platform cross-cutting infrastructure only through approved Spring configuration | Do not couple telemetry domain to platform. |
| `identity` | Existing security/permission module | No direct telemetry domain dependency | Telemetry authorization belongs to API/security integration only. |
| `organization` | Existing business module | Store neutral org references only when needed | Do not import organization domain models. |
| `topology` | Required upstream module | Use `TopologyAssetReference` as neutral snapshot | Do not import topology domain or infrastructure classes into telemetry domain. |
| `telemetry` | New module | Owns acquisition sources, devices, points, readings, quality, and telemetry catalogs | Must follow domain/application/infrastructure/API layering. |
| `operations` | Future downstream module | Consumes telemetry readings later | Do not implement operations behavior in telemetry. |
| `flow` | Future downstream module | Consumes trusted telemetry later | Do not calculate flow in telemetry. |
| `risk` | Future downstream module | Consumes validated telemetry later | Do not score risk in telemetry. |
| `analytics` | Future downstream module | Consumes historical telemetry later | Do not implement models or dashboards in telemetry. |

Correct module order:

```text
kernel -> platform -> identity -> organization -> topology -> telemetry -> operations -> flow -> risk -> analytics -> workflow -> reporting -> notification
```

---

## 3. Module boundary and package rules

Allowed telemetry packages:

```text
dz.sh.hidra.modules.telemetry
dz.sh.hidra.modules.telemetry.domain
dz.sh.hidra.modules.telemetry.domain.model
dz.sh.hidra.modules.telemetry.domain.value
dz.sh.hidra.modules.telemetry.domain.policy
dz.sh.hidra.modules.telemetry.domain.service
dz.sh.hidra.modules.telemetry.application
dz.sh.hidra.modules.telemetry.application.command
dz.sh.hidra.modules.telemetry.application.query
dz.sh.hidra.modules.telemetry.application.dto
dz.sh.hidra.modules.telemetry.application.port.in
dz.sh.hidra.modules.telemetry.application.port.out
dz.sh.hidra.modules.telemetry.application.service
dz.sh.hidra.modules.telemetry.infrastructure
dz.sh.hidra.modules.telemetry.infrastructure.configuration
dz.sh.hidra.modules.telemetry.infrastructure.persistence
dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository
dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper
dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
dz.sh.hidra.modules.telemetry.api
dz.sh.hidra.modules.telemetry.api.rest
dz.sh.hidra.modules.telemetry.api.rest.request
dz.sh.hidra.modules.telemetry.api.rest.response
dz.sh.hidra.modules.telemetry.api.rest.mapper
dz.sh.hidra.modules.telemetry.api.rest.controller
dz.sh.hidra.modules.telemetry.api.rest.configuration
```

Forbidden telemetry packages:

```text
dz.sh.hidra.modules.telemetry.shared
dz.sh.hidra.modules.telemetry.sharedkernel
dz.sh.hidra.modules.telemetry.common
dz.sh.hidra.modules.telemetry.core
dz.sh.hidra.modules.telemetry.utils
dz.sh.hidra.modules.telemetry.helper
dz.sh.hidra.modules.telemetry.helpers
dz.sh.hidra.modules.telemetry.misc
```

Forbidden direct lateral imports:

```text
dz.sh.hidra.modules.telemetry.* -> dz.sh.hidra.modules.identity.*
dz.sh.hidra.modules.telemetry.* -> dz.sh.hidra.modules.organization.*
dz.sh.hidra.modules.telemetry.domain.* -> dz.sh.hidra.modules.topology.*
dz.sh.hidra.modules.telemetry.application.* -> dz.sh.hidra.modules.topology.infrastructure.*
dz.sh.hidra.modules.telemetry.infrastructure.* -> dz.sh.hidra.modules.topology.infrastructure.*
dz.sh.hidra.modules.telemetry.* -> dz.sh.hidra.modules.operations.*
dz.sh.hidra.modules.telemetry.* -> dz.sh.hidra.modules.flow.*
dz.sh.hidra.modules.telemetry.* -> dz.sh.hidra.modules.risk.*
dz.sh.hidra.modules.telemetry.* -> dz.sh.hidra.modules.analytics.*
```

Telemetry may store topology links only as neutral references.

Required value object:

```text
TopologyAssetReference
```

Required fields:

```text
assetTypeCode
assetId
assetCode
assetNameSnapshot optional
```

---

## 4. Findings extracted from telemetry evidence

| Finding code | Evidence | Impact | Roadmap action |
|---|---|---|---|
| `TEL-FND-001` | Telemetry is the acquisition layer, not a generic measurement module | Prevents incorrect scope and module naming | Keep module name `telemetry`; do not create `measurement` as next module. |
| `TEL-FND-002` | Later modules need trusted operational signals | Telemetry must precede operations/flow/risk/analytics | Implement telemetry before downstream calculations. |
| `TEL-FND-003` | Telemetry types need multilingual labels | Java enums are not acceptable for business taxonomy | Use catalog tables and translation tables. |
| `TEL-FND-004` | Topology asset bindings are required | Direct topology imports would break module boundaries | Use `TopologyAssetReference`. |
| `TEL-FND-005` | Raw readings can be high volume | REST-only transaction model is not sufficient long term | Start with REST ingestion but preserve append-only schema and later async path. |
| `TEL-FND-006` | Quality and units are mandatory for trustworthy signals | Readings without quality/unit are not reliable | Add domain policies and required fields. |
| `TEL-FND-007` | Swagger/OpenAPI must expose the acquisition contract | API consumers need clear DTOs and localized catalog references | Add OpenAPI annotations in API tasks. |
| `TEL-FND-008` | Compatibility shims created during corrections must not spread into telemetry | Avoid repeating topology/organization transition debt | Do not create bridge/legacy compatibility classes unless explicitly required by a task. |

---

## 5. Entity, class, enum, and catalog distinction

| Concept | Must be | Must not be | Reason |
|---|---|---|---|
| `TelemetrySource` | Domain entity / aggregate participant | enum | Has identity, lifecycle, source metadata, audit. |
| `TelemetryDevice` | Domain entity | enum | Has identity, source association, external reference, lifecycle. |
| `TelemetryPoint` | Domain entity | enum | Has tag code, unit, signal, source, device, lifecycle. |
| `TelemetryPointBinding` | Domain entity | enum | Has validity interval and topology snapshot. |
| `TelemetryReading` | Domain entity or append-only record model | enum | Has timestamps, value, quality, unit, audit. |
| `TelemetryIngestionBatch` | Domain entity | enum | Groups ingestion events and audit state. |
| `TelemetryTypeCatalog` | Domain model/entity | enum | Represents configurable taxonomy entries. |
| `TelemetryTypeTranslation` | Domain model/entity/value | enum | Represents localized labels/descriptions. |
| `TelemetrySourceStatus` | Technical enum allowed | catalog unless business needs labels | Stable lifecycle state. |
| `TelemetryDeviceStatus` | Technical enum allowed | catalog unless business needs labels | Stable lifecycle state. |
| `TelemetryPointStatus` | Technical enum allowed | catalog unless business needs labels | Stable lifecycle state. |
| `TelemetryReadingState` | Technical enum allowed | catalog unless business needs labels | Internal processing state. |
| `TelemetryIngestionBatchStatus` | Technical enum allowed | catalog unless business needs labels | Batch lifecycle state. |
| `TelemetrySourceType` | Catalog reference | Java enum | Multilingual user-facing taxonomy. |
| `TelemetryDeviceType` | Catalog reference | Java enum | Multilingual user-facing taxonomy. |
| `TelemetryPointType` | Catalog reference | Java enum | Multilingual user-facing taxonomy. |
| `TelemetrySignalType` | Catalog reference | Java enum | Multilingual user-facing taxonomy. |
| `TelemetryUnit` | Catalog reference | Java enum | Labels/symbols/descriptions are localized and configurable. |
| `TelemetryQualityCode` | Catalog reference | Java enum | Quality labels/descriptions are user-facing. |
| `TelemetryProtocol` | Catalog reference | Java enum | SCADA/historian/PLC protocols are configurable. |
| `TelemetryAggregationMethod` | Catalog reference | Java enum | User-facing acquisition/aggregation method. |
| `TelemetryBindingRole` | Catalog reference | Java enum | User-facing binding role. |

---

## 6. Enum audit with status vs type classification

| Candidate | Classification | Java enum allowed? | Implementation rule |
|---|---|---:|---|
| `TelemetrySourceStatus` | Status | Yes | Technical lifecycle enum. |
| `TelemetryDeviceStatus` | Status | Yes | Technical lifecycle enum. |
| `TelemetryPointStatus` | Status | Yes | Technical lifecycle enum. |
| `TelemetryReadingState` | Status/state | Yes | Internal reading processing state. |
| `TelemetryIngestionBatchStatus` | Status | Yes | Batch lifecycle enum. |
| `TelemetrySourceType` | Type taxonomy | No | Use `TelemetrySourceTypeReference`. |
| `TelemetryDeviceType` | Type taxonomy | No | Use `TelemetryDeviceTypeReference`. |
| `TelemetryPointType` | Type taxonomy | No | Use `TelemetryPointTypeReference`. |
| `TelemetrySignalType` | Type taxonomy | No | Use `TelemetrySignalTypeReference`. |
| `TelemetryUnitType` | Type taxonomy | No | Use `TelemetryUnitReference`; avoid `UnitType` enum. |
| `TelemetryQualityCode` | Type taxonomy | No | Use `TelemetryQualityCodeReference`. |
| `TelemetryProtocolType` | Type taxonomy | No | Use `TelemetryProtocolReference`. |
| `TelemetryAggregationType` | Type taxonomy | No | Use `TelemetryAggregationMethodReference`. |
| `TelemetryBindingRoleType` | Type taxonomy | No | Use `TelemetryBindingRoleReference`. |

Forbidden Java enum declarations:

```text
enum TelemetrySourceType
enum TelemetryDeviceType
enum TelemetryPointType
enum TelemetrySignalType
enum TelemetryUnitType
enum TelemetryQualityCode
enum TelemetryProtocolType
enum TelemetryAggregationType
enum TelemetryBindingRoleType
```

---

## 7. Multilingual field audit

User-facing labels must not remain as single-language fields. Any catalog/entity exposed to users must support `nameAr`, `nameFr`, and `nameEn` through translation rows or DTO projection.

| Item | User-facing? | Required multilingual fields | Storage rule | API response rule |
|---|---:|---|---|---|
| Telemetry source type | Yes | `nameAr`, `nameFr`, `nameEn` | Translation table | Return localized `label` plus `locale`; admin DTO may expose all three. |
| Telemetry device type | Yes | `nameAr`, `nameFr`, `nameEn` | Translation table | Return localized `label` plus `locale`; admin DTO may expose all three. |
| Telemetry point type | Yes | `nameAr`, `nameFr`, `nameEn` | Translation table | Return localized `label` plus `locale`; admin DTO may expose all three. |
| Telemetry signal type | Yes | `nameAr`, `nameFr`, `nameEn` | Translation table | Return localized `label` plus `locale`. |
| Telemetry unit | Yes | `nameAr`, `nameFr`, `nameEn` plus optional symbol | Translation table + base symbol | Return localized label and unit symbol. |
| Telemetry quality code | Yes | `nameAr`, `nameFr`, `nameEn` | Translation table | Return localized quality label. |
| Telemetry protocol | Yes | `nameAr`, `nameFr`, `nameEn` | Translation table | Return localized protocol label. |
| Telemetry aggregation method | Yes | `nameAr`, `nameFr`, `nameEn` | Translation table | Return localized method label. |
| Telemetry binding role | Yes | `nameAr`, `nameFr`, `nameEn` | Translation table | Return localized role label. |
| Telemetry source name | Yes | `nameAr`, `nameFr`, `nameEn` if displayed as business label | Translation table or explicit localized fields | Do not expose only `name`. |
| Telemetry device name | Yes | `nameAr`, `nameFr`, `nameEn` if displayed as business label | Translation table or explicit localized fields | Do not expose only `name`. |
| Telemetry point name | Yes | `nameAr`, `nameFr`, `nameEn` if displayed as business label | Translation table or explicit localized fields | Do not expose only `name`. |
| Topology asset snapshot name | Yes | Snapshot may include `assetNameSnapshot`; future enrichment may support localized snapshot | Store neutral snapshot only | Do not depend on topology domain. |

Minimum translation table fields:

```text
id
<catalog>_id
locale
name
description
created_at
updated_at
unique(<catalog>_id, locale)
```

DTO projection for admin/list screens may expose:

```text
nameAr
nameFr
nameEn
descriptionAr
descriptionFr
descriptionEn
```

Normal business responses should use:

```json
{
  "id": "telemetry-signal-pressure",
  "code": "PRESSURE",
  "label": "Pression",
  "locale": "fr"
}
```

---

## 8. Swagger/OpenAPI coverage audit

| API group | Required OpenAPI coverage | Missing until task | Acceptance rule |
|---|---|---|---|
| `/api/v1/telemetry/catalogs` | `@Tag`, `@Operation`, query parameter docs, localized response schema | TEL-021/TEL-022 | Catalog list/resolve endpoints documented. |
| `/api/v1/telemetry/sources` | Request/response schemas, create/get/list/activate/deactivate operations | TEL-021/TEL-022 | All source endpoints appear in Swagger. |
| `/api/v1/telemetry/devices` | Request/response schemas and source/device filters | TEL-021/TEL-022 | Device endpoints documented with catalog references. |
| `/api/v1/telemetry/points` | Request/response schemas, source/device/signal/unit filters | TEL-021/TEL-022 | Point endpoints documented with localized type fields. |
| `/api/v1/telemetry/point-bindings` | Binding request, close operation, topology reference fields | TEL-021/TEL-022 | Topology neutral reference is documented. |
| `/api/v1/telemetry/readings` | Single/batch ingest, latest, query by point/time range | TEL-021/TEL-022 | Reading value model and quality rules documented. |
| `/api/v1/telemetry/ingestion-batches` | Get/list batch audit endpoints | TEL-021/TEL-022 | Batch status and audit fields documented. |

OpenAPI rules:

```text
Every request DTO must use examples.
Every localized catalog reference response must document id, code, label, and locale.
Do not expose Java enum allowableValues for business taxonomy types.
Technical status/state enums may expose allowableValues.
Document Accept-Language fallback behavior.
Document timestamp format as ISO-8601.
Document numeric/text/boolean value exclusivity rules.
```

---

## 9. Cleanup candidates audit

Telemetry must not introduce correction debt. Cleanup candidates must be tracked explicitly.

| Candidate type | Do not introduce | Explicit exception policy |
|---|---|---|
| Deprecated | Deprecated compatibility wrappers for telemetry type catalogs | Only allowed if a later migration/refactor task explicitly demands a temporary bridge. |
| Transitional | Dual old/new type fields in telemetry tables | Avoid; telemetry starts with catalogs from day one. |
| Legacy | Enum-style varchar type columns with CHECK constraints | Forbidden for business taxonomy. |
| Bridge | Enum-to-reference bridge constructors | Forbidden unless explicitly required and time-boxed. |
| Orphaned | Package skeletons without roadmap ownership | Every created file must map to a TEL task. |
| Raw logs | Compiler output pasted into roadmap | Forbidden. Extract findings into tables. |
| Stack traces | Unresolved exception traces as analysis sections | Forbidden. Convert to finding and task. |
| Single-language labels | `name` only for user-facing taxonomy | Forbidden. Use translation tables or `nameAr/nameFr/nameEn` projections. |

---

## 10. Entity-relationship schema

### 10.1 Conceptual ER view

```text
TelemetrySourceTypeCatalog 1 --- * TelemetrySourceTypeTranslation
TelemetryDeviceTypeCatalog 1 --- * TelemetryDeviceTypeTranslation
TelemetryPointTypeCatalog  1 --- * TelemetryPointTypeTranslation
TelemetrySignalTypeCatalog 1 --- * TelemetrySignalTypeTranslation
TelemetryUnitCatalog       1 --- * TelemetryUnitTranslation
TelemetryQualityCodeCatalog 1 -- * TelemetryQualityCodeTranslation
TelemetryProtocolCatalog   1 --- * TelemetryProtocolTranslation
TelemetryBindingRoleCatalog 1 -- * TelemetryBindingRoleTranslation
TelemetryAggregationMethodCatalog 1 -- * TelemetryAggregationMethodTranslation

TelemetrySourceTypeCatalog 1 --- * TelemetrySource
TelemetrySource 1 --- * TelemetryDevice
TelemetrySource 1 --- * TelemetryPoint
TelemetryDevice 0..1 --- * TelemetryPoint
TelemetryPointTypeCatalog 1 --- * TelemetryPoint
TelemetrySignalTypeCatalog 1 --- * TelemetryPoint
TelemetryUnitCatalog 1 --- * TelemetryPoint
TelemetryPoint 1 --- * TelemetryPointBinding
TelemetryBindingRoleCatalog 1 --- * TelemetryPointBinding
TelemetryPoint 1 --- * TelemetryReading
TelemetryQualityCodeCatalog 1 --- * TelemetryReading
TelemetryUnitCatalog 1 --- * TelemetryReading
TelemetryIngestionBatch 0..1 --- * TelemetryReading
```

### 10.2 Database schema artifact plan

| Migration | Purpose | Tables |
|---|---|---|
| `V022__create_telemetry_catalog_tables.sql` | Catalogs and translations | source type, device type, point type, signal type, unit, quality code, protocol, aggregation method, binding role. |
| `V023__create_telemetry_metadata_tables.sql` | Source/device/point/binding metadata | `hidra_telemetry_source`, `hidra_telemetry_device`, `hidra_telemetry_point`, `hidra_telemetry_point_binding`, `hidra_telemetry_ingestion_batch`. |
| `V024__create_telemetry_reading_tables.sql` | Append-only raw readings | `hidra_telemetry_reading` plus indexes. |

### 10.3 Catalog table template

Base table:

```text
id varchar(80) primary key
code varchar(80) unique not null
status varchar(40) not null
sort_order integer not null
system_defined boolean not null
created_at timestamp with time zone not null
updated_at timestamp with time zone not null
```

Translation table:

```text
id varchar(80) primary key
<catalog>_id varchar(80) not null references base table(id)
locale varchar(10) not null
name varchar(160) not null
description varchar(500)
created_at timestamp with time zone not null
updated_at timestamp with time zone not null
unique(<catalog>_id, locale)
```

Required locales:

```text
en
fr
ar
```

### 10.4 Metadata tables

`hidra_telemetry_source`:

```text
id
code unique
source_type_id
protocol_id
name_ar nullable
name_fr not null
name_en nullable
external_reference nullable
endpoint_uri nullable
status
created_at
updated_at
```

`hidra_telemetry_device`:

```text
id
source_id
device_type_id
code unique
name_ar nullable
name_fr not null
name_en nullable
external_reference nullable
status
created_at
updated_at
```

`hidra_telemetry_point`:

```text
id
source_id
device_id nullable
tag_code unique
name_ar nullable
name_fr not null
name_en nullable
point_type_id
signal_type_id
unit_id
aggregation_method_id nullable
status
sampling_period_seconds nullable
external_reference nullable
created_at
updated_at
```

`hidra_telemetry_point_binding`:

```text
id
telemetry_point_id
binding_role_id
topology_asset_type_code
topology_asset_id
topology_asset_code
topology_asset_name_snapshot nullable
valid_from
valid_to nullable
status
created_at
updated_at
```

`hidra_telemetry_ingestion_batch`:

```text
id
source_id
ingestion_reference nullable
received_at
status
total_count
accepted_count
rejected_count
correlation_id nullable
created_at
updated_at
```

### 10.5 Reading table

`hidra_telemetry_reading`:

```text
id
telemetry_point_id
ingestion_batch_id nullable
source_timestamp
received_at
numeric_value nullable
text_value nullable
boolean_value nullable
quality_code_id
unit_id
state
source_sequence nullable
correlation_id nullable
created_at
```

Reading rules:

```text
At least one of numeric_value, text_value, boolean_value is required.
Numeric readings require unit_id.
Every reading requires quality_code_id.
Append-only semantics in the first implementation.
Indexes required on telemetry_point_id, source_timestamp, received_at, quality_code_id, ingestion_batch_id.
```

---

## 11. Prioritized roadmap with sprint items

### Sprint 0 — Preconditions and documentation

| Task | Commit message | Purpose | Acceptance criteria |
|---|---|---|---|
| TEL-001 | `docs(telemetry): add telemetry implementation roadmap` | Create and maintain this roadmap. | `docs/roadmap/telemetry.md` exists and passes this document's pass criteria. |
| TEL-002 | `chore(telemetry): add telemetry package skeleton` | Add package skeleton only. | Package-info files exist; no behavior. |

### Sprint 1 — Domain core and catalogs

| Task | Commit message | Purpose | Acceptance criteria |
|---|---|---|---|
| TEL-003 | `feat(telemetry): add telemetry domain value objects` | Add ids, codes, timestamps, value objects, topology references, catalog references, technical statuses. | No business taxonomy enum exists. |
| TEL-004 | `feat(telemetry): add telemetry catalog domain models` | Add `TelemetryTypeCatalog` and `TelemetryTypeTranslation`. | Translation model supports `en`, `fr`, `ar`. |
| TEL-005 | `feat(telemetry): add telemetry domain models` | Add source, device, point, binding, reading, batch models. | Domain models do not import topology domain classes. |
| TEL-006 | `feat(telemetry): add telemetry domain policies` | Add lifecycle, binding, quality, ingestion, catalog policies. | Invalid readings/bindings are rejected. |
| TEL-007 | `feat(telemetry): add telemetry domain services` | Add registration, binding, ingestion, catalog resolution services. | Services orchestrate domain rules only. |

### Sprint 2 — Application layer

| Task | Commit message | Purpose | Acceptance criteria |
|---|---|---|---|
| TEL-008 | `feat(telemetry): add telemetry application commands and queries` | Add create/list/get/ingest command-query contracts. | Contracts use catalog codes/references, not enums. |
| TEL-009 | `feat(telemetry): add telemetry application DTOs` | Add localized DTOs and reading DTOs. | User-facing DTOs include localized catalog reference fields. |
| TEL-010 | `feat(telemetry): add telemetry application ports` | Add inbound use cases and outbound repository ports. | Ports are technology-agnostic. |
| TEL-011 | `feat(telemetry): add telemetry application services` | Add source/device/point/binding/reading/catalog services. | Services validate parent existence and domain policies. |

### Sprint 3 — Persistence and migrations

| Task | Commit message | Purpose | Acceptance criteria |
|---|---|---|---|
| TEL-012 | `db(telemetry): add telemetry catalog tables` | Add V022 catalog/translation tables and seeds. | Flyway migrates; `en`, `fr`, `ar` seeds exist. |
| TEL-013 | `db(telemetry): add telemetry metadata tables` | Add V023 source/device/point/binding/batch tables. | Tables use FK catalog ids. |
| TEL-014 | `db(telemetry): add telemetry reading tables` | Add V024 append-only reading table and indexes. | Reading table and indexes exist. |
| TEL-015 | `feat(telemetry): add telemetry persistence entities and repositories` | Add JPA entities/repositories. | No legacy enum-style type columns. |
| TEL-016 | `feat(telemetry): add telemetry persistence mapper and adapters` | Add mappers/adapters implementing outbound ports. | Repository ports are wired. |
| TEL-017 | `feat(telemetry): add telemetry infrastructure configuration` | Add Spring bean configuration. | Application services and ports are registered. |

### Sprint 4 — API and Swagger/OpenAPI

| Task | Commit message | Purpose | Acceptance criteria |
|---|---|---|---|
| TEL-018 | `feat(telemetry): add telemetry REST request DTOs` | Add request DTOs for sources/devices/points/bindings/readings/catalogs. | Requests use catalog codes and examples. |
| TEL-019 | `feat(telemetry): add telemetry REST response DTOs` | Add localized response DTOs. | Responses expose localized catalog reference shape. |
| TEL-020 | `feat(telemetry): add telemetry REST mapper` | Map REST to app contracts and localized responses. | `Accept-Language` fallback works. |
| TEL-021 | `feat(telemetry): add telemetry REST controllers` | Add endpoint groups under `/api/v1/telemetry/**`. | Controllers expose only supported operations. |
| TEL-022 | `docs(telemetry): add telemetry OpenAPI coverage checklist` | Ensure Swagger/OpenAPI coverage. | Every endpoint group has `@Tag`, `@Operation`, and documented schemas. |

### Sprint 5 — Tests and stabilization

| Task | Commit message | Purpose | Acceptance criteria |
|---|---|---|---|
| TEL-023 | `test(telemetry): add telemetry domain tests` | Test value objects, models, policies, services. | Domain tests pass without Spring. |
| TEL-024 | `test(telemetry): add telemetry application service tests` | Test application services with fakes. | No DB/Spring required. |
| TEL-025 | `test(telemetry): add telemetry persistence tests` | Test mappers/adapters/repository contracts. | Persistence tests pass. |
| TEL-026 | `test(telemetry): add telemetry REST mapper and controller tests` | Test REST mapper/controllers with fake use cases. | No MockMvc required unless explicitly chosen. |
| TEL-027 | `test(telemetry): add telemetry application boot smoke test` | Validate full Spring context with telemetry. | Testcontainers boot test passes. |
| TEL-028 | `docs(telemetry): finalize telemetry validation checklist` | Final checklist and handoff. | Roadmap status and validation checklist complete. |

---

## 12. Detailed TEL task structure

### TEL-001 — docs(telemetry): add telemetry implementation roadmap

| Field | Value |
|---|---|
| Commit code | `TEL-001` |
| Commit message | `docs(telemetry): add telemetry implementation roadmap` |
| Type | Documentation |
| Files | `docs/roadmap/telemetry.md` |
| Purpose | Define telemetry conception, audits, schema, module boundaries, roadmap, and validation. |
| Validation | `test -f docs/roadmap/telemetry.md` |

Acceptance criteria:

```text
The file is a roadmap/analysis artifact.
The file matches HidraAPI and dz.sh.hidra namespace.
The file includes scope checks, findings tables, audits, schema, task structure, and pass/reject criteria.
```

### TEL-002 — chore(telemetry): add telemetry package skeleton

Files to create:

```text
src/main/java/dz/sh/hidra/modules/telemetry/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/domain/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/domain/model/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/domain/value/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/domain/policy/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/domain/service/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/command/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/query/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/dto/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/port/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/service/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/configuration/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/api/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/api/rest/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/api/rest/configuration/package-info.java
```

Rules:

```text
Create only package-info.java files.
Do not create tests.
Do not create behavior.
Do not create migrations.
```

Validation:

```bash
mvn -q -DskipTests compile
```

### TEL-003 through TEL-028 execution rule

For every task after TEL-002:

```text
Read AGENTS.md.
Read docs/roadmap/telemetry.md.
Execute only the named TEL task.
Create or update only the files listed by that task category.
Run the validation command listed for the task.
Commit with the exact commit message.
Report files changed, validation result, commit hash, remaining risks, and next recommended task.
```

---

## 13. Module/file mapping

| Roadmap area | Production package/files | Test package/files |
|---|---|---|
| Domain values | `src/main/java/dz/sh/hidra/modules/telemetry/domain/value` | `src/test/java/dz/sh/hidra/modules/telemetry/domain/value` |
| Domain models | `src/main/java/dz/sh/hidra/modules/telemetry/domain/model` | `src/test/java/dz/sh/hidra/modules/telemetry/domain/model` |
| Domain policies | `src/main/java/dz/sh/hidra/modules/telemetry/domain/policy` | `src/test/java/dz/sh/hidra/modules/telemetry/domain/policy` |
| Domain services | `src/main/java/dz/sh/hidra/modules/telemetry/domain/service` | `src/test/java/dz/sh/hidra/modules/telemetry/domain/service` |
| Application commands/queries | `src/main/java/dz/sh/hidra/modules/telemetry/application/command`, `query` | `src/test/java/dz/sh/hidra/modules/telemetry/application/service` |
| Application DTOs | `src/main/java/dz/sh/hidra/modules/telemetry/application/dto` | `src/test/java/dz/sh/hidra/modules/telemetry/application/service` |
| Application ports | `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in`, `port/out` | Application service tests with fakes |
| Application services | `src/main/java/dz/sh/hidra/modules/telemetry/application/service` | `src/test/java/dz/sh/hidra/modules/telemetry/application/service` |
| Persistence | `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence` | `src/test/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence` |
| REST API | `src/main/java/dz/sh/hidra/modules/telemetry/api/rest` | `src/test/java/dz/sh/hidra/modules/telemetry/api/rest` |
| Migrations | `src/main/resources/db/migration/V022...V024` | Boot/Flyway validation |
| Roadmap docs | `docs/roadmap/telemetry.md` | Not applicable |

---

## 14. Documentation policy

```text
Roadmaps belong under docs/roadmap/.
Architecture policy belongs under docs/architecture/.
Do not paste raw build logs into roadmap files.
Do not paste unresolved stack traces into roadmap files.
Convert logs/traces into finding tables with impact and action.
Every roadmap task must include commit code, commit message, file mapping, validation, and acceptance criteria.
Keep repository namespace dz.sh.hidra.
Do not mention unrelated repositories or package roots.
```

---

## 15. Alignment rules

```text
Do not accept raw compiler output as roadmap content.
Do not accept unresolved stack traces as analysis sections.
Do not mix a different repository namespace with HidraAPI.
Do not keep compatibility shims unless a task explicitly requires them and gives a removal plan.
Do not leave user-facing labels as single-language fields.
Do not omit entity/class/enum/catalog distinction.
If telemetry evidence is collected, extract issues into findings tables.
Convert findings into sprint tasks.
Add or update ER/schema views when domain modeling changes.
```

---

## 16. Reject criteria

Reject the telemetry roadmap or a generated telemetry artifact if any of the following are true:

```text
No task structure.
No acceptance criteria.
No module/file mapping.
No entity/class/enum/catalog distinction.
No multilingual policy.
No documentation policy.
Raw compiler output is used as roadmap content.
Unresolved stack traces are kept as analysis sections.
A different repository namespace is mixed into the document.
Business taxonomy is modeled as Java enums.
User-facing labels are single-language only.
Telemetry imports topology domain or infrastructure classes directly.
```

---

## 17. Pass criteria

The telemetry roadmap passes when:

```text
The document can be executed by a developer or AI agent.
The document matches HidraAPI and dz.sh.hidra namespace.
The document is under docs/roadmap/ because it supports planning/execution.
The document includes repository overview.
The document includes structural module analysis.
The document includes enum audit with status/type classification.
The document includes multilingual audit with nameAr, nameFr, nameEn.
The document includes Swagger/OpenAPI coverage audit.
The document includes cleanup candidates.
The document includes ER/schema artifact plan.
The document includes prioritized sprint roadmap.
The document includes module/file mapping.
The document includes acceptance criteria.
The document includes documentation policy.
```

---

## 18. Validation commands

Run after each telemetry implementation task as applicable:

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q test -Dtest='*Telemetry*Test'
mvn -q test -Dtest=ControlledVocabularyArchitectureTest
mvn -q -DskipTests flyway:migrate
```

For TEL-001 only:

```bash
test -f docs/roadmap/telemetry.md
grep -n "Repository overview" docs/roadmap/telemetry.md
grep -n "Enum audit" docs/roadmap/telemetry.md
grep -n "Multilingual field audit" docs/roadmap/telemetry.md
grep -n "Entity-relationship schema" docs/roadmap/telemetry.md
```

PowerShell:

```powershell
Test-Path docs/roadmap/telemetry.md
Select-String -Path docs/roadmap/telemetry.md -Pattern 'Repository overview'
Select-String -Path docs/roadmap/telemetry.md -Pattern 'Enum audit'
Select-String -Path docs/roadmap/telemetry.md -Pattern 'Multilingual field audit'
Select-String -Path docs/roadmap/telemetry.md -Pattern 'Entity-relationship schema'
```

---

## 19. Next execution task

After corrected baseline validation passes, execute:

```text
TEL-002 — chore(telemetry): add telemetry package skeleton
```

Do not start TEL-002 until:

```text
COR-018 validation passes locally or in CI.
COR-021 documentation correction is present.
docs/roadmap/telemetry.md passes the pass criteria in this file.
```
