# HidraAPI Security Architecture

## Status

CURRENT — canonical security baseline for repository-verified controls.

## Verification Baseline

- Repository head inspected: `359ae6d77bb9bb8f499941634760f4c375abb9ac`
- CI evidence: HidraAPI CI run #520 completed successfully on this exact head.
- Scope: application/runtime controls evidenced in source and configuration.
- Production network perimeter, TLS termination, infrastructure firewalling, SIEM/SOC integration, and certificate operations: **NOT ESTABLISHED by repository evidence**.

## 1. Security Ownership Boundary

HidraAPI owns application authentication, application authorization, authenticated actor resolution, credential persistence, Hidra access-token issuance/validation, external OIDC completion validation, LDAP authentication integration, request security configuration, and API-level exposure controls.

Infrastructure/network security outside the application process is not proven by this repository.

## 2. Authentication Architecture

### 2.1 Ordinary protected API

**CURRENT**

The ordinary protected API is stateless and uses Spring Security OAuth2 Resource Server JWT validation.

Verified implementation:

- `HidraSecurityConfiguration.hidraSecurityFilterChain`
- session policy: `STATELESS`
- form login: disabled
- HTTP Basic: disabled
- ordinary protected endpoints: authenticated
- public exceptions:
  - `/actuator/health`
  - `/actuator/health/**`
  - `/actuator/info`
  - `/v3/api-docs/**`
  - `/swagger-ui/**`
  - `/swagger-ui.html`
  - `/api/v1/security/oidc`
  - `/api/v1/identity/authentication/login`
  - HTTP `OPTIONS /**`

Hidra-issued bearer tokens are signed and validated with HS256 using externalized `HIDRA_JWT_HMAC_SECRET`.

`HidraJwtEncoderConfiguration` and `HidraJwtDecoderConfiguration` reject missing signing material and enforce a minimum of 32 UTF-8 bytes.

The token issuer `HidraAccessTokenIssuer` issues the Hidra-owned schema from a normalized `HidraPrincipal`; roles and permissions are derived from Hidra-owned principal data rather than copying arbitrary provider claims.

### 2.2 External OIDC completion bridge

**CURRENT**

`/api/v1/identity/authentication/oidc/complete` is isolated by a higher-priority security filter chain.

It accepts an external OIDC bearer token validated through `externalOidcJwtDecoder` using configured issuer/JWK-set data. When neither issuer nor JWK-set is configured, external OIDC completion fails closed by rejecting attempted external bearer validation.

The OpenAPI contract declares this path with a distinct `externalOidcBearerJwt` scheme.

### 2.3 LOCAL authentication

**CURRENT**

LOCAL credential verification is implemented through Identity authentication infrastructure and Spring Security `PasswordEncoder`.

`HidraSecurityConfiguration` supplies `BCryptPasswordEncoder`.

Persistent LOCAL credentials contain a password hash rather than a raw password. The controlled administrator bootstrap has no repository password default and requires externalized password input when explicitly enabled.

### 2.4 LDAP / Active Directory

**CURRENT capability; deployment activation NOT ESTABLISHED**

LDAP/Active Directory authentication infrastructure is present and configuration is externalized.

Relevant configuration includes:

- `HIDRA_LDAP_ENABLED`
- `HIDRA_LDAP_URL`
- `HIDRA_LDAP_BASE_DN`
- `HIDRA_LDAP_USER_SEARCH_BASE`
- `HIDRA_LDAP_USER_SEARCH_FILTER`
- `HIDRA_LDAP_BIND_DN`
- `HIDRA_LDAP_BIND_PASSWORD`
- LDAP connect/read timeouts

Whether production currently activates LDAP is not established by repository evidence.

## 3. Authorization Architecture

### 3.1 Route authorization

**CURRENT**

Protected API access is authenticated by Spring Security and is further subject to application permission handling including `HidraRouteAuthorizationInterceptor` and `HidraEffectivePermissionResolver`.

Exact business permission assignments are Identity-owned runtime data and are not hard-coded here.

### 3.2 Method security

**CURRENT**

`@EnableMethodSecurity` is active in `HidraSecurityConfiguration`.

### 3.3 Workbench generic-read boundary

**CURRENT — fail closed**

The generic operational Workbench no longer exposes JPA entities merely because they exist in the metamodel.

`HidraOperationalWorkbenchExposurePolicy` requires explicit resource and field approval. Blank configuration exposes no resources.

Credential/secret resources and password/secret fields are prohibited, including `identity/local-credentials` and `passwordHash`.

`ArchitectureGuardrailTest` constrains platform JPA access to the reviewed Workbench reader and requires that reader to retain the fail-closed exposure-policy dependency.

See `doc/security/WORKBENCH_DATA_EXPOSURE.md`.

## 4. Authenticated Actor and Audit Attribution

**CURRENT**

Caller-controlled `X-Actor-Id` is not a supported platform header.

`HidraRequestContextFilter` propagates correlation/request and non-authentication request metadata but does not populate authenticated actor context from request headers.

Authenticated actor identity is resolved from Spring Security through:

`SpringSecurityCurrentSecurityContext -> CurrentActorResolver`

Spring Data JPA auditing uses `CurrentActorResolver.currentActorId()`.

## 5. Session and Token Handling

**CURRENT**

- HTTP session creation policy is stateless.
- Hidra access tokens have configurable TTL; common default is 900 seconds.
- Login session persistence stores lifecycle metadata rather than raw JWT values.
- Hidra JWT issuer/audience/claim names are configurable.
- External OIDC validation can enforce issuer and audience based on configuration.

Token revocation semantics beyond stored lifecycle metadata are not asserted here unless proven by the specific Identity implementation path.

## 6. Browser/API Request Controls

**CURRENT**

- CORS configuration is explicit and defaults to no allowed origins.
- allowed methods/headers are configured properties.
- credentials are disabled in CORS.
- CSRF is disabled by default for the stateless bearer model.
- production error configuration suppresses exception/stacktrace/binding-message disclosure.
- production OpenAPI/Swagger UI are disabled by default.
- sensitive technical values written through `LoggingContext` are masked by `SensitiveValueMasker` for keys containing password, secret, token, authorization, credential, or API-key vocabulary.

## 7. Production Configuration Controls

**CURRENT**

`application-production.properties` requires externalized datasource URL/username/password and externalized authentication secrets.

Flyway production configuration disables clean and validates migrations.

Security-sensitive production logging is reduced for Spring Security and Hibernate bind values.

## 8. Explicitly Not Established

The repository does not establish:

- TLS termination location or ownership;
- application-owned TLS certificate/private-key lifecycle;
- mTLS between HidraAPI and external dependencies;
- firewall/network-zone policy;
- OT/IT network segmentation;
- WAF/API-gateway controls;
- SIEM/SOC integration;
- secrets-manager product selection;
- secret rotation cadence or automated rotation;
- certificate rotation/renewal procedure;
- production LDAP activation state;
- production OIDC provider selection;
- incident-response contacts/escalation roster;
- production HA/DR architecture;
- RTO/RPO.

These items must remain `NOT ESTABLISHED`, `TBD`, or later `TARGET` until approved evidence exists.
