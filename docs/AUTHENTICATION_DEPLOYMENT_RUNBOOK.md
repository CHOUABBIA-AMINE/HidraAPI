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
| `HIDRA_JWT_TOKEN_ISSUER` | `hidra-api` | `iss` value of Hidra-issued tokens |
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
- `HIDRA_JWT_TOKEN_ISSUER`, audience, and TTL match the intended Hidra token contract.
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
