# HidraAPI Trust Boundaries

## Status

CURRENT — canonical boundary inventory for repository-verified application trust transitions.

## Verification Baseline

Repository head: `0e26ce4e604a2c09aae1b03ee8bf58ac7bd9f20a`

CI evidence: HidraAPI CI run #523 completed successfully on this exact head.

Anything outside current source/configuration evidence is explicitly marked **NOT ESTABLISHED**.

## 1. Boundary: API Client -> HidraAPI

### Inputs crossing the boundary

- HTTP method/path/body/query parameters
- `Authorization: Bearer ...`
- correlation/request identifiers
- organization/tenant metadata headers currently accepted by request context
- OIDC completion bearer token on its dedicated path

### Current controls

- Spring Security filter chains
- stateless resource-server authentication
- route/method authorization controls
- input mapping/validation in API layer
- fail-closed Workbench exposure policy
- production error-detail suppression

### Untrusted by design

Caller headers are not authenticated identity.

`X-Actor-Id` is not a supported header and is ignored even if a caller sends that literal header name.

## 2. Boundary: External OIDC Provider -> OIDC Completion Bridge

### Current trust decision

Only `/api/v1/identity/authentication/oidc/complete` accepts the external OIDC bearer context.

### Current controls

- dedicated higher-priority security chain;
- external OIDC JWT decoder;
- issuer/JWK-set based validation when configured;
- optional audience validation;
- fails closed when neither issuer nor JWK-set configuration exists.

### Not established

- production identity-provider identity;
- IdP operational ownership;
- key-rotation SLA;
- outage/failover procedure;
- network/TLS termination topology.

## 3. Boundary: HidraAPI -> LDAP / Active Directory

### Current trust decision

LDAP/AD is an implemented authentication-provider capability selected by Identity authentication routing when configured.

### Current controls

- externalized URL/base/search/bind configuration;
- externalized bind password;
- configurable connect/read timeout;
- provider-specific authentication routing.

### Not established

- production LDAP endpoint;
- TLS mode/certificate validation policy;
- bind-account rotation process;
- directory availability/failover model.

## 4. Boundary: HidraAPI -> PostgreSQL

### Current trust decision

PostgreSQL is the authoritative persistence store for current implementation.

### Current controls

- externalized datasource credentials in production;
- JPA schema validation;
- Flyway migration validation;
- Flyway clean disabled in production;
- application module/persistence architecture guardrails.

### Not established

- database network segmentation;
- TLS/mTLS connection policy;
- production replication/failover topology;
- credential rotation cadence;
- backup/restore security ownership.

## 5. Boundary: Identity Credential Store -> Generic Workbench

### Current trust decision

Credential persistence is **not trusted for generic exposure**.

### Current controls

- default Workbench resource exposure is empty;
- explicit resource/field opt-in required;
- credential/secret resource names prohibited;
- password/secret field names prohibited;
- `identity/local-credentials` explicitly prohibited;
- architecture and regression tests protect the boundary.

## 6. Boundary: External Authentication -> Hidra Principal / Hidra JWT

### Current trust decision

External/provider authentication does not automatically make arbitrary provider claims Hidra authorization truth.

### Current controls

`HidraAccessTokenIssuer` issues the Hidra token from normalized `HidraPrincipal` data and promotes Hidra-owned roles/permissions into authorization claims.

## 7. Boundary: Request Metadata -> Authenticated Actor Identity

### Current trust decision

Correlation/request metadata and authenticated actor identity are separate trust domains.

### Current controls

- correlation/request IDs may be caller-provided or server-generated;
- authenticated actor ID is resolved from Spring Security;
- caller-supplied actor header cannot populate actor logging context;
- JPA auditing uses `CurrentActorResolver`.

## 8. Boundary: Application -> Logs/Observability

### Current controls

`LoggingContext` masks values when keys indicate password, secret, token, authorization, credential, or API key.

Production configuration suppresses verbose Hibernate bind logging.

### Not established

- centralized log destination;
- SIEM;
- retention period;
- access controls on log platform;
- security alert rules and on-call ownership.

## 9. Boundary: API Controller -> Module Application

### Current trust decision

REST controllers are adapters. They may translate validated HTTP input into module application commands/queries, but they are not trusted to bypass the application boundary and reach persistence directly.

### Current controls

- controller dependencies are constrained to API mapping and application inbound contracts;
- `ArchitectureGuardrailTest` prohibits REST controllers from depending on `application.port.out`, `infrastructure.persistence`, or Spring Data repositories;
- module API packages may not depend on module infrastructure;
- application packages may not depend on API or infrastructure packages.

### Trust transition

`HTTP request -> controller/API adapter -> application inbound port/use case`

The application layer owns orchestration and domain-policy invocation after this transition.

## 10. Boundary: Module Application -> Another Business Module

### Current trust decision

Private internals of another business module are not trusted cross-module integration surfaces.

### Permitted transition

A cross-module dependency is allowed only when the target package is an explicitly exported `.application.contract.<consumer>` contract recorded in `ArchitectureGuardrailTest`.

Examples currently enforced include:

- Workflow contracts exported to Planning, Organization, Alarm, and Reporting;
- Topology contracts exported to Organization, Simulation, Leak Detection, Analytics, Assets, and Risk;
- Organization contracts exported to Analytics, Assets, Integration, Reporting, and Risk;
- Audit contracts exported to Organization, Alarm, and Risk;
- Party contracts exported to Topology and Assets;
- Telemetry contract exported to Monitoring;
- Identity contract exported to Reporting.

The exact package-prefix list is maintained in `ArchitectureGuardrailTest.EXPORTED_CROSS_MODULE_PACKAGE_PREFIXES`.

### Forbidden transition

A module may not import another module's private domain, infrastructure, or ordinary application package. The transitional exception map is currently empty.

## 11. Boundary: Generic Workbench -> Persistence Metadata

### Current trust decision

The generic Workbench is the only reviewed platform JPA reader and is treated as a privileged exception to normal module application boundaries.

### Trust transition

`Workbench HTTP route -> HidraOperationalWorkbenchController -> HidraOperationalWorkbenchService -> EntityManager/metamodel`

### Current controls

- `HidraOperationalWorkbenchExposurePolicy` is mandatory;
- no configured resources means no exposure;
- resource and field approval is explicit;
- credential/secret resources and password/secret fields are prohibited;
- platform dependencies on module persistence packages are forbidden;
- architecture guardrail permits direct platform JPA access only from the reviewed Workbench service;
- HTTP-level regressions cover discovery, list, detail, search, direct prohibited credential-resource access, and serialized absence of `passwordHash`.

## 12. Boundary: Operator/API Client -> Telemetry Source Registration

### Current trust decision

Telemetry source registration is an application command boundary, not direct persistence access.

### Verified transition

`POST /api/v1/telemetry/sources -> SpringTelemetryController -> CreateTelemetrySourceUseCase -> TelemetrySourceApplicationService -> TelemetrySourceRepositoryPort`

### Current controls

The application service verifies source-code uniqueness and active `SOURCE_TYPE` and `PROTOCOL` catalog references before creating a DRAFT telemetry source.

No repository evidence establishes that the HTTP caller is a SCADA device or field system; it is an authenticated API-side registration operation.

## 13. Boundary: Operator/API Client -> Telemetry Point Registration

### Verified transition

`POST /api/v1/telemetry/points -> SpringTelemetryController -> RegisterTelemetryPointUseCase -> TelemetryPointApplicationService -> TelemetryPointRepositoryPort`

### Current controls

The application service verifies point-code uniqueness per device and creates the point in the controlled PLANNED lifecycle state before persistence.

The controller does not access persistence directly.

## 14. Boundary: API Client -> Telemetry Query Contract

### Verified transition

`GET /api/v1/telemetry/... -> TelemetryQueryController -> TelemetryQueryUseCase -> telemetry query adapter`

Current HTTP reads include point reading history, latest reading, trend, reading states, and quality codes.

### Not established

This query boundary does not establish:

- a telemetry reading-ingestion HTTP endpoint;
- SCADA/PLC protocol termination;
- MQTT/Sparkplug;
- OPC;
- historian connectivity;
- OT/IT network zones;
- field-device trust.

Those remain unverified/deferred unless separately evidenced.

## 9. Boundary: Network / TLS / OT Zones

**NOT ESTABLISHED**

No repository evidence establishes production:

- reverse proxy/API gateway;
- TLS termination;
- certificate ownership;
- network ACL/firewall policy;
- OT/IT segmentation;
- DMZ architecture.

These boundaries must be defined later from approved runtime architecture; they must not be inferred from application code.
