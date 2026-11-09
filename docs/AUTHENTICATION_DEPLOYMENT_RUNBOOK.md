# HidraAPI Authentication Provider Deployment Runbook

## 1. Purpose

This runbook documents the non-secret deployment inputs and operational checks for HidraAPI human authentication through:

- persistent Hidra `LOCAL` authentication,
- LDAP / Active Directory authentication,
- external OIDC authorization-code + PKCE acquisition followed by Hidra normalization,
- Hidra-issued JWT bearer tokens used by ordinary protected APIs.

It reflects the current production configuration and authentication architecture. It does not introduce provider behavior, provisioning automation, secret values, or authorization mappings.

## 2. Security boundary

All successful provider paths converge on a Hidra-owned identity and authorization result:

```text
external or local credential proof
    -> Hidra User / ExternalIdentity linkage
    -> Hidra account-state validation
    -> Hidra-owned effective permissions
    -> HidraPrincipal
    -> LoginSession
    -> Hidra-issued JWT
    -> ordinary protected APIs
```

External LDAP groups, OIDC groups, OIDC roles, OIDC scopes, directory DNs, or other external claims are not Hidra business authorization grants. Hidra roles and permissions remain authoritative.

Never place passwords, bearer tokens, client secrets, private keys, truststore passwords, or private certificates in this repository.

## 3. Environment selection

Select the runtime profile through:

```text
SPRING_PROFILES_ACTIVE=dev|test|staging|production
```

Staging and production are production-like for LDAP transport enforcement. When LDAP is enabled in either environment, the configured LDAP URL must use `ldaps://`.

The normal protected-API authentication mode is:

```text
HIDRA_SECURITY_AUTHENTICATION_MODE=jwt
```

Do not use server-wide Basic authentication as ordinary LOCAL authentication. Persistent LOCAL login uses the Identity authentication endpoint and then a Hidra-issued bearer token.

## 4. Persistent LOCAL authentication

Persistent LOCAL authentication is backed by Hidra Identity data and PostgreSQL-managed credentials. No directory configuration is required.

The optional one-time persistent administrator bootstrap is disabled by default:

| Environment variable | Purpose | Secret? |
|---|---|---|
| `HIDRA_SECURITY_BOOTSTRAP_ENABLED` | Enables controlled persistent administrator provisioning | No |
| `HIDRA_SECURITY_BOOTSTRAP_USERNAME` | Bootstrap username; repository default is `hidra-admin` | No |
| `HIDRA_SECURITY_BOOTSTRAP_PASSWORD` | Bootstrap password supplied externally when bootstrap is enabled | **Yes** |
| `HIDRA_SECURITY_BOOTSTRAP_EMAIL_ADDRESS` | Optional bootstrap email | No |
| `HIDRA_SECURITY_BOOTSTRAP_DISPLAY_NAME` | Optional display name | No |

Operational rules:

1. Keep bootstrap disabled unless provisioning is intentionally required.
2. Supply the bootstrap password only through an external secret source.
3. Do not rely on bootstrap to repair or overwrite an existing credential; incomplete or conflicting state fails closed.
4. After provisioning, ordinary LOCAL users authenticate against persisted Hidra credentials and receive the same Hidra JWT contract as other providers.

## 5. LDAP / Active Directory connection configuration

LDAP infrastructure is disabled by default. Enable it explicitly only when directory authentication is required.

| Environment variable | Required when LDAP enabled | Description |
|---|---:|---|
| `HIDRA_LDAP_ENABLED` | Yes | Set to `true` to create LDAP connection infrastructure |
| `HIDRA_LDAP_URL` | Yes | LDAP server URL; staging/production require `ldaps://` |
| `HIDRA_LDAP_BASE_DN` | Yes | Base DN configured on the LDAP context |
| `HIDRA_LDAP_USER_SEARCH_BASE` | No | Search base relative to the configured base DN; blank means the LDAP base itself |
| `HIDRA_LDAP_USER_SEARCH_FILTER` | Yes | User search filter; must contain the literal `{0}` principal placeholder |
| `HIDRA_LDAP_BIND_DN` | No | Optional technical bind/search identity |
| `HIDRA_LDAP_BIND_PASSWORD` | Only with protected bind account | Bind password; external secret only |
| `HIDRA_LDAP_CONNECT_TIMEOUT` | No | Connection timeout; defaults to `5s` and must be greater than zero |
| `HIDRA_LDAP_READ_TIMEOUT` | No | Read timeout; defaults to `5s` and must be greater than zero |

Repository default search filter:

```text
(sAMAccountName={0})
```

The implementation LDAP-filter-escapes the submitted principal before replacing `{0}`. Do not remove the placeholder from the configured filter.

### 5.1 Directory identity attributes

The LDAP adapter requests these identity attributes when available:

```text
objectGUID
entryUUID
userPrincipalName
sAMAccountName
uid
displayName
cn
mail
distinguishedName
```

Stable external subject selection is:

```text
objectGUID -> entryUUID -> userPrincipalName -> distinguishedName
```

For Active Directory, `objectGUID` is normalized to a stable Base64-backed subject with an `objectGUID:` prefix. Treat the immutable directory identifier as the linkage key; do not use mutable display attributes as the primary identity key.

### 5.2 Search and credential verification behavior

The configured search must resolve exactly one directory identity. The runtime then verifies the submitted password using LDAP authentication for that search result.

Expected operational failures include:

- zero search matches: authentication fails,
- more than one search match: configuration/data ambiguity fails closed,
- invalid credentials: authentication fails,
- missing stable external subject: authentication fails,
- directory outage or timeout: authentication fails; there is no LOCAL fallback.

### 5.3 Hidra LDAP/AD provider records

Runtime LDAP authentication also requires Hidra Identity provider/linkage state, not only connection properties:

1. An active IdentityProvider of type `LDAP` or `ACTIVE_DIRECTORY` must exist.
2. The current runtime expects exactly one active LDAP/AD provider candidate for direct directory authentication; ambiguous active directory-provider configuration fails closed.
3. The verified external subject must resolve to a `LINKED` ExternalIdentity.
4. That ExternalIdentity must point to an active Hidra User whose account state permits login.
5. Hidra effective permissions are loaded from Identity. Directory group membership does not directly become a Hidra permission.

This runbook does not prescribe SQL or manual database mutation for provider provisioning. Use the repository's approved Identity administration/provisioning path for provider and linkage records.

## 6. LDAPS certificate and trust requirements

Staging and production reject enabled LDAP configuration that does not start with `ldaps://`.

The application does not define a Hidra-specific truststore property. The JVM running HidraAPI must trust the LDAP server certificate chain through the deployment platform's Java trust configuration, for example a managed JVM truststore or platform-injected CA configuration.

Operational requirements:

- trust the issuing CA chain used by the LDAPS endpoint,
- keep private keys and truststore passwords outside the repository,
- ensure the LDAPS hostname matches the server certificate,
- validate connectivity from the HidraAPI runtime network, not only from an administrator workstation,
- confirm firewall/DNS rules allow the configured directory endpoint.

Do not disable TLS certificate validation to work around trust problems.

## 7. External OIDC configuration

HidraWEB performs the browser authorization-code + PKCE flow against the external identity provider. HidraAPI publishes non-secret OIDC/browser metadata and validates the external bearer token only on the OIDC completion bridge before normalizing it into Hidra Identity.

Browser-facing OIDC inputs:

| Environment variable | Description | Secret? |
|---|---|---|
| `HIDRA_OIDC_CLIENT_ID` | Public/browser OIDC client identifier | No |
| `HIDRA_OIDC_SCOPES` | Requested scopes; default `openid,profile` | No |
| `HIDRA_OIDC_LOGOUT_URI` | Optional external logout endpoint | No |

External token validation inputs:

| Environment variable | Description |
|---|---|
| `HIDRA_JWT_ISSUER_URI` | External OIDC issuer used for validation/discovery when configured |
| `HIDRA_JWT_JWK_SET_URI` | Explicit external JWK-set endpoint; when present it is used directly |
| `HIDRA_JWT_AUDIENCE` | Expected audience; also used by the Hidra JWT contract |

At least an issuer URI or JWK-set URI is required for the external OIDC completion bridge to accept an external JWT. If neither is configured, attempted OIDC completion fails closed while unrelated authentication modes can still start.

### 7.1 OIDC provider/linkage state

External JWT validation is only the first boundary. Successful OIDC normalization also requires:

1. an active Hidra `IdentityProvider` of type `OIDC` whose configured issuer URI matches the validated JWT issuer,
2. a `LINKED` ExternalIdentity matching that provider plus external JWT subject,
3. an active Hidra User referenced by the ExternalIdentity,
4. Hidra-owned effective permissions for that user.

External `roles`, `groups`, `scope`, or similar claims do not become Hidra business authorization.

## 8. Hidra-issued JWT configuration

Ordinary protected APIs accept the standardized Hidra-issued JWT, not the external OIDC JWT.

| Environment variable | Default / requirement | Purpose |
|---|---|---|
| `HIDRA_JWT_HMAC_SECRET` | Required in JWT mode; at least 32 UTF-8 bytes | HS256 signing and validation material for Hidra-issued access tokens |
| `HIDRA_JWT_TOKEN_ISSUER` | Explicit absolute HTTP(S) URI required for production; non-production inherited default `hidra-api`, dev fallback `http://localhost:8080/hidra-api` | Signed Hidra `iss` identity; FD-01 does not require an HTTPS scheme. Production preflight rejects blank/malformed values. |
| `HIDRA_JWT_AUDIENCE` | `hidra-api` | Expected audience |
| `HIDRA_JWT_ACCESS_TOKEN_TTL_SECONDS` | `900` | Access-token lifetime in seconds |
| `HIDRA_JWT_PRINCIPAL_CLAIM` | `sub` | Principal claim used by Spring Security |
| `HIDRA_JWT_ROLES_CLAIM` | `roles` | Hidra role claim |
| `HIDRA_JWT_SCOPE_CLAIM` | `scope` | Hidra effective-permission/scope claim |
| `HIDRA_JWT_AUTHORITY_PREFIX` | `ROLE_` | Spring role-authority prefix |

The HMAC secret is a security credential. Supply it through an external secret manager/environment injection and rotate it using a controlled deployment procedure. Do not place an example real secret in documentation, tests, profile files, shell history, tickets, or logs.

The currently implemented Hidra token contract uses HS256. `HIDRA_JWT_ISSUER_URI` and `HIDRA_JWT_JWK_SET_URI` belong to external OIDC token validation; they are not substitutes for `HIDRA_JWT_HMAC_SECRET` when validating Hidra-issued tokens.

## 9. Provider-selection behavior

The direct authentication API accepts explicit provider selection for:

```text
LOCAL
LDAP
ACTIVE_DIRECTORY
```

OIDC uses the external browser flow and secured OIDC completion bridge rather than collecting an external password in HidraAPI.

Provider failure never silently falls through to another provider. Operational troubleshooting must therefore diagnose the selected provider instead of expecting fallback behavior.

## 10. Deployment checklist

Before enabling an environment, verify:

- `SPRING_PROFILES_ACTIVE` selects the intended profile.
- `HIDRA_SECURITY_AUTHENTICATION_MODE=jwt` for normal protected-API operation.
- `HIDRA_JWT_HMAC_SECRET` is externally supplied and contains at least 32 UTF-8 bytes.
- `HIDRA_JWT_TOKEN_ISSUER` is explicitly supplied as an absolute HTTP(S) URI in production (either scheme accepted by FD-01); audience and TTL match the intended Hidra token contract. Issuer identity is distinct from network TLS deployment.
- Required IdentityProvider and ExternalIdentity records are active/linked.
- LDAP is enabled only when required.
- staging/production LDAP uses `ldaps://`.
- the JVM trusts the LDAPS certificate chain.
- LDAP base DN and search base are correct for the target directory tree.
- LDAP search filter contains `{0}` and returns exactly one intended user.
- connection/read timeouts are positive and operationally appropriate.
- external OIDC issuer/JWK validation data is configured when OIDC completion is enabled.
- OIDC issuer in Hidra Identity matches the validated external issuer.
- no external group/role mapping is being relied upon as a Hidra authorization shortcut.
- no secret values are present in repository configuration.

## 11. Diagnostics

### LDAP application does not start after enabling LDAP

Check:

- `HIDRA_LDAP_URL` is populated,
- `HIDRA_LDAP_BASE_DN` is populated,
- `HIDRA_LDAP_USER_SEARCH_FILTER` is populated,
- both timeout values are greater than zero,
- staging/production URL starts with `ldaps://`.

### LDAP user cannot authenticate

Check in this order:

1. network/DNS reachability to the directory,
2. LDAPS certificate trust,
3. bind/search account configuration when used,
4. base DN and user search base,
5. search filter includes `{0}` and resolves exactly one entry,
6. directory entry exposes a stable identity attribute,
7. an active Hidra LDAP/AD provider exists without ambiguity,
8. a `LINKED` ExternalIdentity exists for the verified external subject,
9. the mapped Hidra User is active and not locked.

Do not diagnose by logging the submitted password.

### OIDC completion returns authentication failure

Check:

1. external token issuer/JWK configuration,
2. issuer and audience validation,
3. active Hidra OIDC provider with matching issuer URI,
4. `LINKED` ExternalIdentity for issuer/provider plus external subject,
5. mapped Hidra User account state.

External claims that look privileged are intentionally insufficient if Hidra has not granted the corresponding permission.

### Hidra bearer token is rejected by ordinary protected APIs

Check:

- the token was issued by HidraAPI after provider completion, not copied directly from the external IdP,
- Hidra JWT issuer and audience configuration match issuance/validation,
- `HIDRA_JWT_HMAC_SECRET` is identical across the Hidra token issuer and decoder deployment and meets the minimum length,
- the token has not expired.

Never log the bearer token while diagnosing.

## 12. Health and observability notes

The application exposes Actuator health under `/actuator/health` according to profile policy. Current authentication failures are intentionally fail-closed; this runbook does not claim a dedicated LDAP or OIDC provider-health endpoint where none exists.

Use application logs and infrastructure telemetry for connection/timeout diagnosis while preserving the repository's sensitive-value masking and no-secret logging rules.

## 13. Secret-handling checklist

Never commit or log:

```text
LOCAL passwords
LDAP/AD user passwords
LDAP bind passwords
OIDC authorization codes
OIDC access tokens
OIDC ID tokens
Hidra bearer tokens
JWT HMAC secrets
private keys
private certificates
truststore passwords
```

Store deployment secrets in the environment's approved secret-management system and inject them only at runtime.


## HAPI-929 — LOCAL refresh, development opt-in (2026-11-08)

This whole Identity backend feature is **CI-first development software**, not an
operational/SITE deployment approval. LOCAL login is unchanged by default:
`HIDRA_IDENTITY_REFRESH_ENABLED=false`. On a synthetic PostgreSQL development
schema and with a reviewed signing key (at least32 effective UTF-8 bytes), explicitly set:
```powershell
$env:HIDRA_IDENTITY_REFRESH_ENABLED = 'true'
$env:HIDRA_IDENTITY_REFRESH_FAMILY_TTL_SECONDS = '604800'
# Existing external HIDRA_JWT_HMAC_SECRET must be configured by the local kit.
```
The refresh digest key is domain-separated from the configured native signing key;
a different signing key invalidates existing refresh proofs. Production and staging
keep refresh disabled unless separately approved and fully verified. Default native
JWT TTL remains900 seconds; refresh hard expiry defaults604800 seconds (seven days),
permitted300..2592000 seconds (maximum30 days). No sliding extension.

Only `LOCAL` authentication returns optional `refreshToken` plus
`refreshExpiresAt` in addition to the existing result. External
LDAP/AD/OIDC authentication returns null refresh fields and must use its real IdP.

```http
POST /api/v1/identity/authentication/refresh
Content-Type: application/json

{"refreshToken":"<80-character opaque LOCAL credential>"}
```
No bearer header (especially no expired bearer) is required. On success replace
both credentials atomically in volatile client memory. The previously issued
native bearer is retired when refresh commits, and the replacement response
contains a new JTI, native session, signed access JWT, opaque refresh credential
and the original absolute refresh deadline. Retrying a previously consumed
token after an unknown commit revokes its entire descendant family, so do not
automatically retry a timed-out refresh.

```http
POST /api/v1/identity/authentication/logout
Content-Type: application/json

{"refreshToken":"<currently held opaque LOCAL credential>"}
```
Logout returns204 without disclosing whether the family exists and revokes all
of that family's current bearer sessions. Refresh and logout use bounded JSON
bodies, never URLs. No persistent browser credential-store, cookies, localStorage,
IdP refresh, mobile behavior or HidraWEB changes are authorized here.

The database owner must run the new forward Flyway migration, verify trigger
states and provision SELECT/INSERT and the narrowly scoped `revoked_at` /
`consumed_at` UPDATE column grants on the two refresh-owned tables to the
runtime database role. Do not disable immutable binding/session/security-version
triggers. Existing installations receive no retrospective refresh grants: each
user must perform a successful fresh LOCAL login after enablement.

Verify on a disposable database: login, refresh, new `/me` response, old bearer
denial, consumed-token reuse, concurrent same-token race, family revocation,
account/credential disablement, key rotation, and original-transaction rollback.
Protected operations still recheck Identity/Authorization live and never inherit
scope from a refresh credential. Runtime OpenAPI and database physical dictionary
must match generated actual values before whole-task technical acceptance.


### LOCAL refresh attempt throttling (HAPI-929, owner-approved Option A)

The backend applies a server-side, default-on limit **when the refresh feature is
enabled**. Configuration: `HIDRA_IDENTITY_REFRESH_THROTTLE_ENABLED` (default true),
`HIDRA_IDENTITY_REFRESH_THROTTLE_MAX_ATTEMPTS` (default 30),
`HIDRA_IDENTITY_REFRESH_THROTTLE_WINDOW_SECONDS` (default 60), and
`HIDRA_IDENTITY_REFRESH_THROTTLE_MAX_KEYS` (default 32768, hard maximum 65536).
Values outside the verified constructor bounds fail startup rather than silently
removing protection. A single bucket covers one canonical 36-character LOCAL
refresh family and the server-observed servlet remote address. The key is held
as a SHA-256 digest, not as a raw credential; neither the JWT, the opaque
secret nor a supplied forwarding header is retained. Distinct families behind
one company NAT do not share an operator quota.

Exceeding the quota responds with a non-disclosing **429** and never enters
the Identity owner rotation transaction. A full bounded bucket map also
fails closed for new keys until old windows expire; this protects memory but
can deny service under high-cardinality adversarial traffic. The counter uses
a 60-second default fixed window; reset/restart clears admission counters,
**not** persisted refresh families, rotations or session state. Excessive
attempts do not revoke a family or consume a one-time token. The caller must
not retry continuously after a 429; honor the configured window. Oversized
JSON fails existing validation before family admission.

**Deployment boundary:** this is an in-memory **per-process** limiter for the
one-application-server topology. It is NOT a cluster-wide rate guarantee;
a load-balanced or multi-replica deployment needs an explicitly tested shared
rate limiter/edge abuse policy before production acceptance. The generic
source family quota cannot prevent an attacker cycling many unknown UUIDs,
although the bounded key map fails closed. This software proof does not
establish production load, anti-DDoS or industrial SITE behavior.



### Refresh-family expiry and signing-key restart acceptance

The persisted LOCAL refresh family has a fixed hard deadline. In the original
READ_COMMITTED owner transaction PostgreSQL samples `clock_timestamp()`
**after** the family row lock is acquired. The later of that authoritative
database instant and the server-observed application instant is used to
fail closed if the deadline has elapsed. Revocation is committed without
consuming the one-time proof or creating a new session; a subsequent
clock rollback cannot restore a revoked family. The seven-day default is
still bounded by the existing 300..2592000-second policy.

A new application context using the same database and the **same** JWT HMAC
secret can refresh a still-live single-use LOCAL family after the original
application context closes, even after its short-lived access JWT has
actually expired. The old JWT is not itself sufficient for renewal.
Changing the native HMAC secret invalidates old JWT signatures and existing
refresh HMAC proofs; users need a fresh LOCAL login under the new key.
No seamless multikey overlap or automatic database-wide family revocation
is provided. Changing back to a former signing key is a security-sensitive
operational reversal: operators should explicitly revoke outstanding
families before retiring or restoring keys.

Synthetic tests exercise separate closed/restarted Spring contexts on
PostgreSQL 16; they do not establish a separate operating-system process
restart, a rolling cluster migration, hot key rotation, or SITE acceptance.
