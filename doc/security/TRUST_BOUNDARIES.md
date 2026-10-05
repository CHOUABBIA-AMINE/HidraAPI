# HidraAPI Trust Boundaries

## Status

CURRENT — canonical boundary inventory for repository-verified application trust transitions.

## Verification Baseline

Repository head: `359ae6d77bb9bb8f499941634760f4c375abb9ac`

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
