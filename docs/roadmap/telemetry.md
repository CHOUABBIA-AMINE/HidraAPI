# HidraAPI Telemetry Roadmap

```text
Roadmap file : docs/roadmap/telemetry.md
Roadmap code : TEL
Scope        : Telemetry bounded context for acquisition sources, devices, tags, raw readings, quality, and topology bindings
Repository   : HidraAPI
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-06-06
Status       : Prepared as the next business module roadmap after corrected baseline validation
```

---

## 1. Purpose

Telemetry is the next HidraAPI business module after the corrected foundation/topology/organization baseline is validated.

Telemetry owns the **industrial acquisition layer** for hydrocarbon pipeline operations.

Telemetry covers:

```text
SCADA / historian / PLC / IoT gateway source registration
telemetry devices
telemetry tags / points
signal definitions
raw readings
normalized reading envelope
quality codes
topology asset bindings
sampling metadata
ingestion audit metadata
source timestamp vs received timestamp
unit and signal catalogs
localized catalog labels
```

Telemetry does **not** own:

```text
physical topology modeling
organization hierarchy
identity and permissions
hydraulic flow calculation
line pack calculation
risk scoring
analytics models
workflow approvals
report generation
commercial allocation
maintenance work orders
```

Telemetry provides reliable, auditable, timestamped operational data for later modules.

---

## 2. Strategic position in Hidra

Correct module order:

```text
kernel
platform
identity
organization
topology
telemetry
operations
flow
risk
analytics
workflow
reporting
notification
```

Telemetry must come before flow/risk/analytics because those later modules need trusted operational signals.

Telemetry must come after topology because every telemetry point should be bindable to a topology asset, such as:

```text
pipeline system
pipeline
pipeline segment
facility
topology node
pipeline appurtenance
valve
injection point
extraction point
purge point
equipment
```

---

## 3. Preconditions before TEL-001 execution

Do not execute telemetry implementation tasks until this gate is true:

```text
COR-018 validation passed locally or in CI
mvn -q -DskipTests compile passes
mvn -q test passes
mvn -q test -Dtest=ControlledVocabularyArchitectureTest passes
mvn -q test -Dtest=TopologyApplicationBootSmokeTest passes with Docker/Testcontainers
mvn -q -DskipTests flyway:migrate passes
```

Also correct documentation references that still point to measurement as the next module.

Recommended documentation correction before or with TEL-001 planning:

```text
COR-021 — docs(correction): replace measurement next-module references with telemetry
```

---

## 4. Module boundary rules

Telemetry package root:

```text
src/main/java/dz/sh/hidra/modules/telemetry
src/test/java/dz/sh/hidra/modules/telemetry
```

Allowed telemetry packages:

```text
dz.sh.hidra.modules.telemetry.domain
dz.sh.hidra.modules.telemetry.application
dz.sh.hidra.modules.telemetry.infrastructure
dz.sh.hidra.modules.telemetry.api
```

Forbidden packages:

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

Forbidden direct lateral dependencies:

```text
dz.sh.hidra.modules.telemetry.* -> dz.sh.hidra.modules.identity.*
dz.sh.hidra.modules.telemetry.* -> dz.sh.hidra.modules.organization.*
dz.sh.hidra.modules.telemetry.* -> dz.sh.hidra.modules.topology.domain.*
dz.sh.hidra.modules.telemetry.* -> dz.sh.hidra.modules.topology.infrastructure.*
dz.sh.hidra.modules.telemetry.* -> dz.sh.hidra.modules.operations.*
dz.sh.hidra.modules.telemetry.* -> dz.sh.hidra.modules.flow.*
dz.sh.hidra.modules.telemetry.* -> dz.sh.hidra.modules.risk.*
dz.sh.hidra.modules.telemetry.* -> dz.sh.hidra.modules.analytics.*
```

Telemetry may store topology links as neutral references only.

Use a telemetry-owned value object:

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

Do not import topology domain objects into telemetry domain models.

---

## 5. Data conception

Telemetry is not a generic measurement module.

Telemetry is the acquisition/observability layer that answers:

```text
Where did the signal come from?
Which source system/device/tag produced it?
Which topology asset is it attached to?
When was it produced by the source?
When was it received by Hidra?
What value was received?
What unit/code/quality came with it?
Can the reading be trusted enough for downstream modules?
```

### 5.1 Core telemetry entities

```text
TelemetrySource
TelemetryDevice
TelemetryPoint
TelemetryPointBinding
TelemetryReading
TelemetryIngestionBatch
TelemetryTypeCatalog
TelemetryTypeTranslation
```

### 5.2 Entity responsibility

| Entity | Responsibility |
|---|---|
| `TelemetrySource` | External source system such as SCADA, historian, PLC gateway, IoT gateway, manual import, or simulator. |
| `TelemetryDevice` | Physical or virtual field acquisition device associated with a source. |
| `TelemetryPoint` | Stable signal/tag definition, for example pressure tag, temperature tag, valve position tag. |
| `TelemetryPointBinding` | Binding between a telemetry point and a neutral topology asset reference. |
| `TelemetryReading` | Timestamped acquired value with source timestamp, received timestamp, quality, and unit. |
| `TelemetryIngestionBatch` | Audit envelope for imported or streamed readings. |
| `TelemetryTypeCatalog` | Catalog entry for telemetry business taxonomy. |
| `TelemetryTypeTranslation` | Localized label/description for catalog entry. |

### 5.3 Controlled vocabularies

Because Hidra supports multilingual business concepts, telemetry business types must be catalog entities, not Java enums.

Catalog-backed concepts:

```text
Telemetry source type
Telemetry device type
Telemetry point type
Telemetry signal type
Telemetry unit
Telemetry quality code
Telemetry acquisition protocol
Telemetry aggregation method
Telemetry binding role
```

Allowed technical enums:

```text
TelemetrySourceStatus
TelemetryDeviceStatus
TelemetryPointStatus
TelemetryReadingState
TelemetryIngestionBatchStatus
```

Do not create Java enums named:

```text
TelemetrySourceType
TelemetryDeviceType
TelemetryPointType
TelemetrySignalType
TelemetryUnitType
TelemetryQualityCode
TelemetryProtocolType
TelemetryAggregationType
```

Use catalog reference value objects instead:

```text
TelemetrySourceTypeReference
TelemetryDeviceTypeReference
TelemetryPointTypeReference
TelemetrySignalTypeReference
TelemetryUnitReference
TelemetryQualityCodeReference
TelemetryProtocolReference
TelemetryAggregationMethodReference
TelemetryBindingRoleReference
```

### 5.4 First telemetry signal categories

Seed catalog values should cover at least:

```text
PRESSURE
TEMPERATURE
FLOW_RATE
VOLUME_TOTAL
VALVE_POSITION
PUMP_STATUS
COMPRESSOR_STATUS
DENSITY
VISCOSITY
VIBRATION
POWER
CURRENT
VOLTAGE
BATTERY_LEVEL
COMMUNICATION_STATUS
```

### 5.5 First telemetry units

Seed catalog values should cover at least:

```text
BAR
PSI
CELSIUS
KELVIN
M3_PER_HOUR
M3_PER_DAY
M3
PERCENT
BOOLEAN
RPM
HZ
AMPERE
VOLT
WATT
KILOWATT
UNITLESS
```

Unit conversion is not owned by telemetry in the first implementation. Telemetry stores the received unit and normalized display metadata. Advanced conversion belongs to a later measurement/engineering utility module if needed.

### 5.6 First quality codes

Seed catalog values should cover at least:

```text
GOOD
UNCERTAIN
BAD
STALE
MANUAL
SUBSTITUTED
ESTIMATED
OUT_OF_RANGE
COMMUNICATION_LOSS
DEVICE_FAILURE
```

---

## 6. Database conception

Telemetry migrations must not modify topology, organization, identity, platform, or kernel tables.

Recommended migration sequence:

```text
V022__create_telemetry_catalog_tables.sql
V023__create_telemetry_metadata_tables.sql
V024__create_telemetry_reading_tables.sql
```

### 6.1 Catalog tables

Expected catalog table groups:

```text
hidra_telemetry_source_type
hidra_telemetry_source_type_translation
hidra_telemetry_device_type
hidra_telemetry_device_type_translation
hidra_telemetry_point_type
hidra_telemetry_point_type_translation
hidra_telemetry_signal_type
hidra_telemetry_signal_type_translation
hidra_telemetry_unit
hidra_telemetry_unit_translation
hidra_telemetry_quality_code
hidra_telemetry_quality_code_translation
hidra_telemetry_protocol
hidra_telemetry_protocol_translation
hidra_telemetry_aggregation_method
hidra_telemetry_aggregation_method_translation
hidra_telemetry_binding_role
hidra_telemetry_binding_role_translation
```

Each base catalog table must contain:

```text
id varchar(80) primary key
code varchar(80) unique not null
status varchar(40) not null
sort_order integer not null
system_defined boolean not null
created_at timestamp with time zone not null
updated_at timestamp with time zone not null
```

Each translation table must contain:

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

### 6.2 Metadata tables

Expected metadata tables:

```text
hidra_telemetry_source
hidra_telemetry_device
hidra_telemetry_point
hidra_telemetry_point_binding
hidra_telemetry_ingestion_batch
```

`hidra_telemetry_point` should include:

```text
id
source_id
device_id nullable
tag_code unique
name
point_type_id
signal_type_id
unit_id
status
sampling_period_seconds nullable
external_reference nullable
created_at
updated_at
```

`hidra_telemetry_point_binding` should include:

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

### 6.3 Reading tables

Expected reading table:

```text
hidra_telemetry_reading
```

Minimum columns:

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

Rules:

```text
Do not make raw readings depend on REST controller transactions for high-volume ingest long term.
Do not implement aggregation or hydraulic calculations in telemetry.
Add indexes on telemetry_point_id, source_timestamp, received_at, quality_code_id.
Use append-only reading semantics in the first implementation.
```

Partitioning may be added later after the initial API and validation baseline.

---

## 7. REST endpoint conception

Telemetry endpoint groups:

```text
/api/v1/telemetry/catalogs
/api/v1/telemetry/sources
/api/v1/telemetry/devices
/api/v1/telemetry/points
/api/v1/telemetry/point-bindings
/api/v1/telemetry/readings
/api/v1/telemetry/ingestion-batches
```

Initial operation shape:

| Endpoint group | Operations |
|---|---|
| catalogs | list, resolve |
| sources | create, get, list, activate, deactivate |
| devices | create, get, list, activate, deactivate |
| points | create, get, list, activate, deactivate |
| point-bindings | create, get, list, close |
| readings | ingest single, ingest batch, get latest by point, query by point/time range |
| ingestion-batches | get, list |

REST create/list requests must use stable catalog codes:

```text
sourceTypeCode
deviceTypeCode
pointTypeCode
signalTypeCode
unitCode
qualityCode
protocolCode
aggregationMethodCode
bindingRoleCode
```

REST responses must expose localized catalog references:

```json
{
  "id": "telemetry-signal-pressure",
  "code": "PRESSURE",
  "label": "Pression",
  "locale": "fr"
}
```

Localization strategy:

```text
Accept-Language header with fallback to configured default locale
```

---

## 8. Implementation roadmap

### TEL-001 — docs(telemetry): add telemetry implementation roadmap

```text
Commit code    : TEL-001
Commit message : docs(telemetry): add telemetry implementation roadmap
Type           : Documentation
Layer          : Documentation
Module         : telemetry
```

#### Goal

Create this roadmap file:

```text
docs/roadmap/telemetry.md
```

#### Files to create

| File | Purpose |
|---|---|
| `docs/roadmap/telemetry.md` | Defines telemetry module conception, boundaries, sequence, validation, and implementation tasks. |

#### Rules

```text
Do not create telemetry packages.
Do not create Java code.
Do not create migrations.
Do not modify topology, organization, identity, platform, or kernel.
```

#### Validation

```bash
test -f docs/roadmap/telemetry.md
```

PowerShell:

```powershell
Test-Path docs/roadmap/telemetry.md
```

---

### TEL-002 — chore(telemetry): add telemetry package skeleton

```text
Commit code    : TEL-002
Commit message : chore(telemetry): add telemetry package skeleton
Type           : Chore
Layer          : Module Skeleton
Module         : telemetry
```

#### Goal

Create only production `package-info.java` files for telemetry packages.

#### Files to create

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

#### Rules

```text
Do not create test package-info.java files.
Do not create classes with behavior.
Do not create migrations.
Do not create shared/common/core/utils/helper packages.
```

#### Validation

```bash
mvn -q -DskipTests compile
```

---

### TEL-003 — feat(telemetry): add telemetry domain value objects

```text
Commit code    : TEL-003
Commit message : feat(telemetry): add telemetry domain value objects
Type           : Feature
Layer          : Domain
Module         : telemetry
```

#### Files to create

```text
TelemetrySourceId
TelemetryDeviceId
TelemetryPointId
TelemetryPointBindingId
TelemetryReadingId
TelemetryIngestionBatchId
TelemetryCatalogTypeId
TelemetryCode
TelemetryName
TelemetryTagCode
ExternalTelemetryReference
TopologyAssetReference
SourceTimestamp
ReceivedTimestamp
SamplingPeriod
TelemetryNumericValue
TelemetryTextValue
TelemetryBooleanValue
TelemetrySourceSequence
TelemetryStatus
TelemetryReadingState
TelemetrySourceTypeReference
TelemetryDeviceTypeReference
TelemetryPointTypeReference
TelemetrySignalTypeReference
TelemetryUnitReference
TelemetryQualityCodeReference
TelemetryProtocolReference
TelemetryAggregationMethodReference
TelemetryBindingRoleReference
```

#### Rules

```text
Do not create Java enums for business taxonomy types.
Technical status/state enums are allowed only for lifecycle/state behavior.
```

---

### TEL-004 — feat(telemetry): add telemetry catalog domain models

```text
Commit code    : TEL-004
Commit message : feat(telemetry): add telemetry catalog domain models
Type           : Feature
Layer          : Domain
Module         : telemetry
```

#### Files to create

```text
TelemetryTypeCatalog
TelemetryTypeTranslation
```

#### Purpose

Model catalog entries and localized labels for telemetry business taxonomy.

---

### TEL-005 — feat(telemetry): add telemetry domain models

```text
Commit code    : TEL-005
Commit message : feat(telemetry): add telemetry domain models
Type           : Feature
Layer          : Domain
Module         : telemetry
```

#### Files to create

```text
TelemetrySource
TelemetryDevice
TelemetryPoint
TelemetryPointBinding
TelemetryReading
TelemetryIngestionBatch
```

#### Rules

```text
Do not import topology domain classes.
Use TopologyAssetReference for topology binding.
Do not implement flow, risk, or analytics behavior.
```

---

### TEL-006 — feat(telemetry): add telemetry domain policies

```text
Commit code    : TEL-006
Commit message : feat(telemetry): add telemetry domain policies
Type           : Feature
Layer          : Domain
Module         : telemetry
```

#### Files to create

```text
TelemetryLifecyclePolicy
TelemetryPointBindingPolicy
TelemetryReadingQualityPolicy
TelemetryIngestionPolicy
TelemetryCatalogPolicy
```

#### Required policy rules

```text
active readings require active telemetry point
closed point bindings cannot receive new readings
source timestamp cannot be unreasonably in the future
received_at cannot be before source_timestamp by an impossible margin unless explicitly allowed
quality code is mandatory for every reading
unit is mandatory for numeric readings
at least one value field is required for a reading
```

---

### TEL-007 — feat(telemetry): add telemetry domain services

```text
Commit code    : TEL-007
Commit message : feat(telemetry): add telemetry domain services
Type           : Feature
Layer          : Domain
Module         : telemetry
```

#### Files to create

```text
TelemetryRegistrationDomainService
TelemetryBindingDomainService
TelemetryReadingIngestionDomainService
TelemetryCatalogResolutionDomainService
```

---

### TEL-008 — feat(telemetry): add telemetry application commands and queries

```text
Commit code    : TEL-008
Commit message : feat(telemetry): add telemetry application commands and queries
Type           : Feature
Layer          : Application
Module         : telemetry
```

#### Commands

```text
CreateTelemetrySourceCommand
CreateTelemetryDeviceCommand
CreateTelemetryPointCommand
BindTelemetryPointCommand
CloseTelemetryPointBindingCommand
IngestTelemetryReadingCommand
IngestTelemetryReadingBatchCommand
ActivateTelemetrySourceCommand
DeactivateTelemetrySourceCommand
ActivateTelemetryDeviceCommand
DeactivateTelemetryDeviceCommand
ActivateTelemetryPointCommand
DeactivateTelemetryPointCommand
```

#### Queries

```text
GetTelemetrySourceByIdQuery
ListTelemetrySourcesQuery
GetTelemetryDeviceByIdQuery
ListTelemetryDevicesQuery
GetTelemetryPointByIdQuery
ListTelemetryPointsQuery
GetTelemetryPointBindingByIdQuery
ListTelemetryPointBindingsQuery
GetLatestTelemetryReadingQuery
QueryTelemetryReadingsQuery
GetTelemetryIngestionBatchByIdQuery
ListTelemetryIngestionBatchesQuery
GetTelemetryCatalogTypeQuery
ListTelemetryCatalogTypesQuery
ResolveTelemetryCatalogTypeQuery
```

---

### TEL-009 — feat(telemetry): add telemetry application DTOs

```text
Commit code    : TEL-009
Commit message : feat(telemetry): add telemetry application DTOs
Type           : Feature
Layer          : Application
Module         : telemetry
```

#### Files to create

```text
TelemetrySourceDto
TelemetryDeviceDto
TelemetryPointDto
TelemetryPointBindingDto
TelemetryReadingDto
TelemetryIngestionBatchDto
TelemetryCatalogDto
TelemetryCatalogTranslationDto
TelemetryTypeReferenceDto
TopologyAssetReferenceDto
```

---

### TEL-010 — feat(telemetry): add telemetry application ports

```text
Commit code    : TEL-010
Commit message : feat(telemetry): add telemetry application ports
Type           : Feature
Layer          : Application
Module         : telemetry
```

#### Inbound ports

```text
CreateTelemetrySourceUseCase
GetTelemetrySourceUseCase
ListTelemetrySourcesUseCase
CreateTelemetryDeviceUseCase
GetTelemetryDeviceUseCase
ListTelemetryDevicesUseCase
CreateTelemetryPointUseCase
GetTelemetryPointUseCase
ListTelemetryPointsUseCase
BindTelemetryPointUseCase
CloseTelemetryPointBindingUseCase
ListTelemetryPointBindingsUseCase
IngestTelemetryReadingUseCase
IngestTelemetryReadingBatchUseCase
GetLatestTelemetryReadingUseCase
QueryTelemetryReadingsUseCase
ListTelemetryCatalogTypesUseCase
ResolveTelemetryCatalogTypeUseCase
```

#### Outbound ports

```text
TelemetrySourceRepositoryPort
TelemetryDeviceRepositoryPort
TelemetryPointRepositoryPort
TelemetryPointBindingRepositoryPort
TelemetryReadingRepositoryPort
TelemetryIngestionBatchRepositoryPort
TelemetryCatalogRepositoryPort
```

---

### TEL-011 — feat(telemetry): add telemetry application services

```text
Commit code    : TEL-011
Commit message : feat(telemetry): add telemetry application services
Type           : Feature
Layer          : Application
Module         : telemetry
```

#### Files to create

```text
TelemetrySourceApplicationService
TelemetryDeviceApplicationService
TelemetryPointApplicationService
TelemetryPointBindingApplicationService
TelemetryReadingApplicationService
TelemetryCatalogApplicationService
```

---

### TEL-012 — db(telemetry): add telemetry catalog migration

```text
Commit code    : TEL-012
Commit message : db(telemetry): add telemetry catalog migration
Type           : Database
Layer          : Infrastructure
Module         : telemetry
```

#### File to create

```text
src/main/resources/db/migration/V022__create_telemetry_catalog_tables.sql
```

#### Rules

```text
Create catalog and translation tables only.
Seed en/fr/ar values.
Do not create telemetry metadata or reading tables yet.
```

---

### TEL-013 — db(telemetry): add telemetry metadata migration

```text
Commit code    : TEL-013
Commit message : db(telemetry): add telemetry metadata migration
Type           : Database
Layer          : Infrastructure
Module         : telemetry
```

#### File to create

```text
src/main/resources/db/migration/V023__create_telemetry_metadata_tables.sql
```

#### Tables

```text
hidra_telemetry_source
hidra_telemetry_device
hidra_telemetry_point
hidra_telemetry_point_binding
hidra_telemetry_ingestion_batch
```

---

### TEL-014 — db(telemetry): add telemetry reading migration

```text
Commit code    : TEL-014
Commit message : db(telemetry): add telemetry reading migration
Type           : Database
Layer          : Infrastructure
Module         : telemetry
```

#### File to create

```text
src/main/resources/db/migration/V024__create_telemetry_reading_tables.sql
```

#### Table

```text
hidra_telemetry_reading
```

#### Rules

```text
Append-only first version.
No aggregation tables.
No calculated flow tables.
No analytics tables.
```

---

### TEL-015 — feat(telemetry): add telemetry persistence entities and repositories

```text
Commit code    : TEL-015
Commit message : feat(telemetry): add telemetry persistence entities and repositories
Type           : Feature
Layer          : Infrastructure
Module         : telemetry
```

#### Files to create

```text
TelemetrySourceJpaEntity
TelemetryDeviceJpaEntity
TelemetryPointJpaEntity
TelemetryPointBindingJpaEntity
TelemetryReadingJpaEntity
TelemetryIngestionBatchJpaEntity
TelemetryCatalogJpaEntity
TelemetryCatalogTranslationJpaEntity
TelemetrySourceJpaRepository
TelemetryDeviceJpaRepository
TelemetryPointJpaRepository
TelemetryPointBindingJpaRepository
TelemetryReadingJpaRepository
TelemetryIngestionBatchJpaRepository
TelemetryCatalogJpaRepository
TelemetryCatalogTranslationJpaRepository
```

---

### TEL-016 — feat(telemetry): add telemetry persistence mapper and adapters

```text
Commit code    : TEL-016
Commit message : feat(telemetry): add telemetry persistence mapper and adapters
Type           : Feature
Layer          : Infrastructure
Module         : telemetry
```

#### Files to create

```text
TelemetryPersistenceMapper
TelemetryCatalogPersistenceMapper
TelemetrySourceRepositoryAdapter
TelemetryDeviceRepositoryAdapter
TelemetryPointRepositoryAdapter
TelemetryPointBindingRepositoryAdapter
TelemetryReadingRepositoryAdapter
TelemetryIngestionBatchRepositoryAdapter
TelemetryCatalogRepositoryAdapter
TelemetryConfiguration
```

---

### TEL-017 — feat(telemetry): add telemetry REST request DTOs

```text
Commit code    : TEL-017
Commit message : feat(telemetry): add telemetry REST request DTOs
Type           : Feature
Layer          : API
Module         : telemetry
```

#### Files to create

```text
CreateTelemetrySourceRequest
CreateTelemetryDeviceRequest
CreateTelemetryPointRequest
BindTelemetryPointRequest
CloseTelemetryPointBindingRequest
IngestTelemetryReadingRequest
IngestTelemetryReadingBatchRequest
TopologyAssetReferenceRequest
TelemetryValueRequest
```

---

### TEL-018 — feat(telemetry): add telemetry REST response DTOs

```text
Commit code    : TEL-018
Commit message : feat(telemetry): add telemetry REST response DTOs
Type           : Feature
Layer          : API
Module         : telemetry
```

#### Files to create

```text
TelemetrySourceResponse
TelemetryDeviceResponse
TelemetryPointResponse
TelemetryPointBindingResponse
TelemetryReadingResponse
TelemetryIngestionBatchResponse
TelemetryTypeReferenceResponse
TopologyAssetReferenceResponse
```

---

### TEL-019 — feat(telemetry): add telemetry REST mapper

```text
Commit code    : TEL-019
Commit message : feat(telemetry): add telemetry REST mapper
Type           : Feature
Layer          : API
Module         : telemetry
```

#### Files to create

```text
TelemetryRestMapper
TelemetryApiRestConfiguration
```

#### Required behavior

```text
Resolve typeCode values into catalog references.
Map Accept-Language into localized response labels.
Map raw reading values without calculating derived metrics.
```

---

### TEL-020 — feat(telemetry): add telemetry REST controllers

```text
Commit code    : TEL-020
Commit message : feat(telemetry): add telemetry REST controllers
Type           : Feature
Layer          : API
Module         : telemetry
```

#### Files to create

```text
TelemetryCatalogController
TelemetrySourceController
TelemetryDeviceController
TelemetryPointController
TelemetryPointBindingController
TelemetryReadingController
TelemetryIngestionBatchController
```

---

### TEL-021 — test(telemetry): add telemetry domain tests

```text
Commit code    : TEL-021
Commit message : test(telemetry): add telemetry domain tests
Type           : Test
Layer          : Test
Module         : telemetry
```

---

### TEL-022 — test(telemetry): add telemetry application service tests

```text
Commit code    : TEL-022
Commit message : test(telemetry): add telemetry application service tests
Type           : Test
Layer          : Test
Module         : telemetry
```

---

### TEL-023 — test(telemetry): add telemetry persistence tests

```text
Commit code    : TEL-023
Commit message : test(telemetry): add telemetry persistence tests
Type           : Test
Layer          : Test
Module         : telemetry
```

---

### TEL-024 — test(telemetry): add telemetry REST mapper and controller tests

```text
Commit code    : TEL-024
Commit message : test(telemetry): add telemetry REST mapper and controller tests
Type           : Test
Layer          : Test
Module         : telemetry
```

---

### TEL-025 — test(telemetry): add telemetry application boot smoke test

```text
Commit code    : TEL-025
Commit message : test(telemetry): add telemetry application boot smoke test
Type           : Test
Layer          : Test
Module         : telemetry
```

#### Required assertions

```text
HidraApplication context loads
telemetry configuration bean exists
telemetry REST mapper bean exists
telemetry controllers are registered
telemetry application services are registered
telemetry repository ports are registered
Flyway migrations V022-V024 apply with PostgreSQL Testcontainers
```

---

### TEL-026 — docs(telemetry): finalize telemetry validation checklist

```text
Commit code    : TEL-026
Commit message : docs(telemetry): finalize telemetry validation checklist
Type           : Documentation
Layer          : Documentation
Module         : telemetry
```

#### Files to create

```text
docs/roadmap/telemetry_validation_checklist.md
```

---

## 9. Validation commands

Run from the repository root.

### 9.1 File existence

```bash
test -f docs/roadmap/telemetry.md
test -d src/main/java/dz/sh/hidra/modules/telemetry
test -d src/test/java/dz/sh/hidra/modules/telemetry
```

PowerShell:

```powershell
Test-Path docs/roadmap/telemetry.md
Test-Path src/main/java/dz/sh/hidra/modules/telemetry
Test-Path src/test/java/dz/sh/hidra/modules/telemetry
```

### 9.2 Compile and tests

```bash
mvn -q -DskipTests compile
mvn -q test -Dtest='*Telemetry*Test'
mvn -q test -Dtest=TelemetryApplicationBootSmokeTest
mvn -q test
```

### 9.3 Controlled vocabulary guardrail

```bash
mvn -q test -Dtest=ControlledVocabularyArchitectureTest
```

Expected result:

```text
No telemetry business taxonomy type is a Java enum.
```

### 9.4 Flyway

```bash
mvn -q -DskipTests flyway:migrate
```

Expected result:

```text
V022, V023, and V024 apply cleanly after existing migrations.
```

---

## 10. Acceptance criteria

Telemetry is accepted when:

```text
telemetry roadmap exists
telemetry package structure exists
telemetry business taxonomies are catalog-backed and multilingual
telemetry sources/devices/points can be registered
telemetry points can be bound to neutral topology asset references
telemetry readings can be ingested with source timestamp, received timestamp, unit, and quality
REST responses expose localized type labels
no telemetry business taxonomy is implemented as a Java enum
mvn -q test passes
TelemetryApplicationBootSmokeTest passes with PostgreSQL Testcontainers
Flyway migrations apply cleanly
```

---

## 11. Next module after telemetry

After telemetry is validated, the recommended next module is:

```text
OPS-001 — docs(operations): add operations implementation roadmap
```

Operations may consume telemetry readings and topology references, but it must not own raw telemetry acquisition.
