# HidraAPI Production Environment Configuration

## Status

**CURRENT CONFIGURATION CONTRACT + APPROVED OPERATING REQUIREMENTS — HPR-P1-007**

Execution base: `59c8b913e703e08386bab49850405e307d16002d`

Source of truth for runtime keys remains the application configuration in `src/main/resources`.

This document must never contain real production secret values.

## 1. Profile Selection

| Variable | Requirement | Repository behavior |
|---|---|---|
| `SPRING_PROFILES_ACTIVE` | REQUIRED | Must be `production` for production deployment |
| `SERVER_PORT` | OPTIONAL | Defaults to `8080` |
| `MANAGEMENT_SERVER_PORT` | OPTIONAL | Defaults to server port |
| `HIDRA_ENVIRONMENT` | REQUIRED FOR PRODUCTION HOSTING | Must be `production`; acts as the independent production-runtime intent marker used by the fail-fast startup guard, preventing silent dev-profile fallback |

Do not rely on `SPRING_PROFILES_DEFAULT` for production. Production hosting must set both `HIDRA_ENVIRONMENT=production` and `SPRING_PROFILES_ACTIVE=production`; if production intent is declared without the production profile, HidraAPI fails before bean creation.

## 2. PostgreSQL

| Variable | Requirement | Notes |
|---|---|---|
| `HIDRA_DATASOURCE_URL` | REQUIRED | Must reference the approved stable PostgreSQL service endpoint |
| `HIDRA_DATASOURCE_USERNAME` | REQUIRED SECRET/IDENTITY | Externalized |
| `HIDRA_DATASOURCE_PASSWORD` | REQUIRED SECRET | Externalized |
| `HIDRA_DATASOURCE_MAX_POOL_SIZE` | OPTIONAL | Repository default 30; not an approved capacity target |
| `HIDRA_DATASOURCE_MIN_IDLE` | OPTIONAL | Repository default 10; not an approved capacity target |

Production also enforces JPA schema validation and Flyway validate-on-migrate with clean disabled.

## 3. Authentication Mode

| Variable | Requirement | Notes |
|---|---|---|
| `HIDRA_SECURITY_AUTHENTICATION_MODE` | REQUIRED DECISION | Repository default is `jwt`; production must intentionally use the approved mode |

The configured mode determines which identity inputs below are actually required.

## 4. Hidra JWT

| Variable | Requirement | Notes |
|---|---|---|
| `HIDRA_JWT_HMAC_SECRET` | REQUIRED SECRET when Hidra token issuance/HMAC validation is used | Must be externalized; never Git |
| `HIDRA_JWT_ISSUER_URI` | CONDITIONAL | External issuer validation when configured |
| `HIDRA_JWT_JWK_SET_URI` | CONDITIONAL | External JWK validation when configured |
| `HIDRA_JWT_TOKEN_ISSUER` | OPTIONAL | Default `hidra-api` |
| `HIDRA_JWT_AUDIENCE` | OPTIONAL | Default `hidra-api` |
| `HIDRA_JWT_ACCESS_TOKEN_TTL_SECONDS` | OPTIONAL | Default 900 seconds |
| `HIDRA_JWT_PRINCIPAL_CLAIM` | OPTIONAL | Default `sub` |
| `HIDRA_JWT_ROLES_CLAIM` | OPTIONAL | Default `roles` |
| `HIDRA_JWT_SCOPE_CLAIM` | OPTIONAL | Default `scope` |
| `HIDRA_JWT_AUTHORITY_PREFIX` | OPTIONAL | Default `ROLE_` |

The accepted security lifecycle governs rotation/invalidation of signing material.

## 5. Browser OIDC

| Variable | Requirement | Notes |
|---|---|---|
| `HIDRA_OIDC_CLIENT_ID` | CONDITIONAL | Required when browser OIDC path is enabled/used |
| `HIDRA_OIDC_SCOPES` | OPTIONAL | Default `openid,profile` |
| `HIDRA_OIDC_LOGOUT_URI` | CONDITIONAL | Provider/runtime-specific when used |

No OIDC client secret is defined as part of the repository browser PKCE contract.

## 6. LDAP / Active Directory

| Variable | Requirement | Notes |
|---|---|---|
| `HIDRA_LDAP_ENABLED` | OPTIONAL | Default false |
| `HIDRA_LDAP_URL` | REQUIRED if LDAP enabled | External endpoint |
| `HIDRA_LDAP_BASE_DN` | REQUIRED if LDAP enabled | Directory base |
| `HIDRA_LDAP_USER_SEARCH_BASE` | CONDITIONAL | Directory search scope |
| `HIDRA_LDAP_USER_SEARCH_FILTER` | OPTIONAL | Default `(sAMAccountName={0})` |
| `HIDRA_LDAP_BIND_DN` | CONDITIONAL SECRET IDENTITY | Required by configured bind model |
| `HIDRA_LDAP_BIND_PASSWORD` | CONDITIONAL SECRET | Never Git |
| `HIDRA_LDAP_CONNECT_TIMEOUT` | OPTIONAL | Default `5s` |
| `HIDRA_LDAP_READ_TIMEOUT` | OPTIONAL | Default `5s` |

Production LDAP activation/failover endpoint design remains an infrastructure/identity decision.

## 7. Administrator Bootstrap

| Variable | Requirement | Notes |
|---|---|---|
| `HIDRA_SECURITY_BOOTSTRAP_ENABLED` | OPTIONAL | Default false; enable only for controlled bootstrap |
| `HIDRA_SECURITY_BOOTSTRAP_USERNAME` | CONDITIONAL | Default `hidra-admin` |
| `HIDRA_SECURITY_BOOTSTRAP_PASSWORD` | REQUIRED SECRET when bootstrap enabled | One-time external input |
| `HIDRA_SECURITY_BOOTSTRAP_EMAIL_ADDRESS` | CONDITIONAL | No repository default |
| `HIDRA_SECURITY_BOOTSTRAP_DISPLAY_NAME` | OPTIONAL | Default `Hidra Administrator` |

Bootstrap must be disabled after the controlled provisioning purpose is complete according to the security procedure.

## 8. CORS / API Exposure

| Variable | Requirement | Notes |
|---|---|---|
| `HIDRA_CORS_ALLOWED_ORIGINS` | REQUIRED DECISION | Production origins must be explicitly approved/configured |
| `HIDRA_OPENAPI_ENABLED` | OPTIONAL | Production default false |
| `HIDRA_SWAGGER_UI_ENABLED` | OPTIONAL | Production default false |

Do not enable API documentation exposure in production without an explicit operational/security reason.

## 9. Outbox

| Variable | Requirement | Notes |
|---|---|---|
| `HIDRA_OUTBOX_CLEANUP_ENABLED` | OPTIONAL | Production default false |
| `HIDRA_OUTBOX_BATCH_SIZE` | OPTIONAL | Production default 100 |
| `HIDRA_OUTBOX_MAX_RETRY_COUNT` | OPTIONAL | Production default 5 |

The common configuration keeps the outbox feature itself disabled. Production enablement/coordination is not established by current evidence.

## 10. Notification Async Push

| Variable | Requirement | Notes |
|---|---|---|
| `HIDRA_NOTIFICATION_ASYNC_PUSH_ENABLED` | OPTIONAL | Production default true |
| `HIDRA_NOTIFICATION_ASYNC_PUSH_CORE_POOL_SIZE` | OPTIONAL | Default 4 |
| `HIDRA_NOTIFICATION_ASYNC_PUSH_MAX_POOL_SIZE` | OPTIONAL | Default 12 |
| `HIDRA_NOTIFICATION_ASYNC_PUSH_QUEUE_CAPACITY` | OPTIONAL | Default 500 |
| `HIDRA_NOTIFICATION_ASYNC_PUSH_THREAD_PREFIX` | OPTIONAL | Default `HidraNotificationPush-` |
| `HIDRA_NOTIFICATION_PUSH_PROVIDER_REFERENCE` | OPTIONAL | Default `hidra-local-async-push` |

These are node-local executor settings, not a distributed job/messaging guarantee.

## 11. Secrets Classification

The following are secret or credential material and must be supplied through the approved external mechanism:

- `HIDRA_DATASOURCE_PASSWORD`;
- `HIDRA_JWT_HMAC_SECRET`;
- `HIDRA_LDAP_BIND_PASSWORD`;
- `HIDRA_SECURITY_BOOTSTRAP_PASSWORD`;
- any platform/private-key material introduced outside these Spring properties.

Usernames, issuer URLs, JWK URLs, client IDs, directory DNs and similar configuration may still be sensitive operational metadata even when they are not secrets.

## 12. Configuration Evidence

Deployment evidence may record:

- variable names;
- configuration version/reference;
- secret version/reference where the platform supports it;
- whether a required value was present;
- non-secret endpoint identifiers where approved.

Deployment evidence must not record raw secret values.

## 13. Intentionally Unselected Infrastructure

Current repository evidence does not select:

- secret-manager product;
- environment-variable injection technology;
- TLS termination product/placement;
- certificate automation product;
- load balancer/reverse proxy/ingress;
- VM/container/orchestration platform;
- network zones/firewall implementation;
- external realtime broker;
- distributed cache.

Those selections must preserve this application configuration contract.

## 14. Change Control

A production configuration change must:

1. identify the changed key(s);
2. classify secret/security impact;
3. confirm owner approval when required;
4. preserve secret rotation procedures where applicable;
5. deploy through the approved runtime mechanism;
6. verify health/authentication/database behavior;
7. record configuration reference/version without secret values.

Configuration that changes database authority, authentication trust, or security credentials must not be treated as an ordinary cosmetic setting change.

## 15. Production Startup Guard

HidraAPI registers `HidraProductionStartupGuard` as a Spring `EnvironmentPostProcessor`.

For a production runtime it fails fast when:

- production intent is declared but the `production` Spring profile is not active;
- datasource URL, username, or password is empty;
- CORS is enabled but no allowed production origin is configured;
- authentication mode is not `jwt`;
- the Hidra JWT HMAC secret is empty;
- administrator bootstrap is enabled without a bootstrap password.

The guard runs before normal bean creation and complements, rather than replaces, existing Spring placeholder validation and security-component validation.

Optional OIDC/LDAP integration inputs remain conditional on those integration paths and are not made universally mandatory by this guard.

## 16. Production Readiness

The application-side production configuration contract is documented.

The concrete production platform, secret-injection implementation, TLS/network implementation and deployment automation remain **NOT ESTABLISHED**.
