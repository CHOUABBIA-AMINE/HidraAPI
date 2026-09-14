# HidraAPI Authentication Roadmap — LDAP/Active Directory + Local

## 1. Document control

| Field | Value |
|---|---|
| Project | HidraAPI |
| Product | Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Roadmap file | `docs/roadmap/authentication.md` |
| Primary business module | `identity` |
| Technical owner | `platform.security` |
| Identity root package | `dz.sh.hidra.modules.identity` |
| Security root package | `dz.sh.hidra.platform.security` / `dz.sh.hidra.platform.configuration` |
| API root | `/api/v1/security/auth` |
| Database prefix | `hidra_identity_*` |
| Author | Abir MEDJERAB |
| CreatedOn | 2025-06-26 |
| Roadmap baseline date | 2026-09-14 |
| Status | Planned |
| Execution mode | One roadmap commit code at a time |

---

## 2. Decision summary

HidraAPI will support exactly two human credential sources:

```text
ACTIVE_DIRECTORY (LDAP/LDAPS)
LOCAL            (Hidra-owned credentials)
```

HidraAPI will not support external human identity providers such as:

```text
OIDC
OAuth2 login providers
SAML2
Keycloak
Azure AD / Entra ID as an external OIDC provider
Okta
Google login
```

This roadmap intentionally distinguishes **credential authentication** from **API token transport**.

LDAP/AD and LOCAL are the only credential verification sources. After either source successfully authenticates a user, Hidra issues its own short-lived signed access token. Protected API calls use that Hidra-issued bearer token. Therefore JWT remains an internal Hidra session/access-token mechanism; it is not an external authentication provider.

The invariant is:

```text
Authentication source may vary.
Authorization authority does not vary.

AD verifies AD credentials.
Hidra verifies LOCAL credentials.
Hidra always owns roles, permissions, scopes, account enablement, and authorization decisions.
```

---

## 3. Current repository baseline

The live repository already contains useful foundations but its authentication runtime does not yet implement the target model.

### 3.1 Current platform security behavior

`HidraSecurityConfiguration` currently supports runtime modes:

```text
basic
jwt
disabled
```

Current `basic` mode creates an in-memory bootstrap user using `InMemoryUserDetailsManager`; it does not authenticate `modules.identity.User` records from PostgreSQL.

Current `jwt` mode configures HidraAPI as an OAuth2 Resource Server and expects a JWT that is validated by the configured decoder/issuer infrastructure.

Current public security surface includes:

```text
GET /api/v1/security/oidc
```

That endpoint and its external-OIDC contract are incompatible with this roadmap's final target and must be retired in a controlled compatibility step.

### 3.2 Current Identity domain

The Identity module already owns:

```text
User
Role
Permission
IdentityProvider
ExternalIdentity
LoginSession
AuthenticationEvent
AuthorizationDecision
role/permission grants
provider/synchronization concepts
```

This roadmap extends and narrows the live model rather than creating a second auth/IAM module.

### 3.3 Current provider taxonomy

`ProviderType` currently permits:

```text
LOCAL
LDAP
ACTIVE_DIRECTORY
OIDC
OAUTH2
SAML2
KEYCLOAK
AZURE_AD
OKTA
```

Final target:

```text
LOCAL
ACTIVE_DIRECTORY
```

`LDAP` is the technical protocol used by the `ACTIVE_DIRECTORY` provider and must not remain a separate business provider type after migration unless live data proves that non-AD LDAP directories must be retained. This roadmap assumes the company's directory is Microsoft Active Directory.

### 3.4 Current IdentityProvider model

The existing `IdentityProvider` record already contains directory-oriented fields such as:

```text
directoryBaseDn
userSearchBase
groupSearchBase
usernameAttribute
emailAttribute
displayNameAttribute
externalIdAttribute
groupMembershipAttribute
secretReference
```

It also contains OIDC/OAuth-oriented fields:

```text
issuerUri
authorizationEndpoint
tokenEndpoint
jwksUri
```

Those external-provider fields become removal/deprecation candidates under this roadmap.

### 3.5 Current User model

`User` already owns important Hidra-side account state:

```text
status
lastAuthenticatedAt
failedLoginCount
lockedUntil
```

Those fields must remain Hidra-owned for both AD and LOCAL users. A successful AD bind does not bypass a disabled, suspended, or locked Hidra account.

### 3.6 Current database

The existing Identity migration already creates provider, external identity, login session, authentication event, and authorization structures. It does not provide the final LOCAL credential store required by this roadmap.

Never modify `V20260611_001__create_identity_tables.sql` after it has been released/applied. Add forward-only Flyway migrations.

### 3.7 Current dependency baseline

The Maven build contains Spring Security and OAuth2 Resource Server support but no dedicated LDAP authentication dependency. LDAP support must be added deliberately in the LDAP adapter task.

---

## 4. Target architecture

```text
                         HIDRA HUMAN AUTHENTICATION

          +-----------------------------------------------+
          |                  HidraWEB                     |
          |        username + password over HTTPS         |
          +--------------------------+--------------------+
                                     |
                                     v
                       POST /api/v1/security/auth/login
                                     |
                                     v
                         Identity Authentication Use Case
                                     |
                    +----------------+----------------+
                    |                                 |
                    v                                 v
          ACTIVE_DIRECTORY                         LOCAL
          LDAP/LDAPS adapter                 Local credential adapter
                    |                                 |
                    v                                 v
          Company Active Directory             PostgreSQL hash
                    |                                 |
                    +----------------+----------------+
                                     |
                              credential valid
                                     |
                                     v
                         Hidra account validation
                                     |
                         status / lock / provider link
                                     |
                                     v
                       Hidra authorization resolution
                                     |
                           roles + permissions + scope
                                     |
                                     v
                         Hidra access-token issuer
                                     |
                                     v
                       signed short-lived Hidra JWT
                                     |
                                     v
                 Authorization: Bearer <hidra-access-token>
                                     |
                                     v
                                  HidraAPI
```

### 4.1 Boundary ownership

`modules.identity` owns business meaning:

```text
which Hidra user exists
which authentication source is bound to that user
whether the account is active/suspended/disabled/locked
LOCAL credential lifecycle rules
AD identity linkage
role grants
permission grants
authorization evaluation
login-session metadata
authentication audit events
```

`platform.security` owns technical security plumbing:

```text
Spring Security filter chain
bearer-token extraction
JWT signing/verification implementation
LDAP client/bind implementation
PasswordEncoder implementation
HTTP security/CORS integration
security configuration binding
```

Do not move Role, Permission, User, provider-selection rules, account lockout meaning, or credential business policy into platform.

---

## 5. Authentication rules

### 5.1 No authentication fallback

Hidra must not try AD and then silently fall back to LOCAL using the same username/password.

Every enabled human Hidra user has one explicit primary authentication source:

```text
ACTIVE_DIRECTORY
or
LOCAL
```

The source is resolved from Hidra identity data before password verification. This prevents ambiguous accounts and prevents an AD failure from accidentally authenticating against a local account with the same username.

### 5.2 AD users must exist in Hidra

Default policy is **pre-provisioned AD linkage**.

An AD bind may prove the credentials, but login succeeds only when Hidra can resolve the directory identity to an enabled Hidra user.

Automatic just-in-time user creation is out of scope for the first implementation. It may be introduced only by a later explicit roadmap task.

### 5.3 AD does not grant Hidra roles

AD groups may be read for diagnostics/synchronization if required later, but they must not automatically become roles or permissions in this roadmap.

The following remain authoritative:

```text
hidra_identity_user_role_grant
hidra_identity_role_permission_grant
hidra_identity_user_permission_grant
Hidra authorization policies
organization/resource scope grants
```

Any existing external-role/external-permission mapping behavior must not participate in runtime authorization after this roadmap is complete.

### 5.4 Hidra account state wins

Credential validity alone is insufficient.

For both authentication sources, deny login when Hidra account state is incompatible with login, including at minimum:

```text
SUSPENDED
DISABLED
LOCKED
```

A disabled Hidra user must remain denied even when AD credentials are valid.

### 5.5 AD account state also matters

The AD adapter must reject directory authentication when AD itself rejects the credentials or account. Hidra must not cache an AD password.

### 5.6 LDAPS required for production

Production-like environments must not permit plaintext credential transmission to an LDAP server.

Target:

```text
ldaps://...
```

or a verified TLS-protected LDAP connection supported by the chosen Spring LDAP integration.

TLS certificate validation must remain enabled. Trust material must come from environment/deployment secret management, not repository files containing private material.

### 5.7 Password storage

Hidra never stores AD passwords.

LOCAL passwords are stored only as an adaptive one-way password hash. No plaintext password, reversible encrypted password, password log, or password event payload is permitted.

The initial implementation should use Spring Security's supported password-encoding abstraction and a modern adaptive encoder. The concrete encoder parameters must be configurable/evolvable without changing the domain model.

### 5.8 Generic login failures

Public login responses must not reveal whether:

```text
the username exists
the user is LOCAL vs AD
the directory account exists
the password was wrong
the local account was absent
```

Audit data may record an internal reason code subject to sensitive-data rules.

---

## 6. Token/session model

### 6.1 Hidra-issued access token

After successful LDAP or LOCAL authentication, Hidra issues its own signed access token.

The token must identify the Hidra principal, not merely the AD DN.

Minimum claims:

```text
iss  = Hidra issuer identifier
sub  = stable Hidra user ID
aud  = hidra-api
iat
exp
jti
sid  = Hidra login-session ID
auth_source = ACTIVE_DIRECTORY | LOCAL
```

Authorization should continue to be resolved from Hidra's authoritative permission model. Do not make the token an immutable long-lived database of all authorization state.

If role/scope claims are included for request efficiency, define an explicit freshness/revocation strategy and keep API-side authorization final.

### 6.2 Signing keys

Production token signing must use asymmetric keys and support rotation.

Repository policy:

```text
no committed private key
no hard-coded production secret
no default production signing credential
```

Development may use an explicitly development-only key configuration, but production startup must fail closed when valid signing material is absent.

### 6.3 Session record

Reuse/evolve the existing Identity `LoginSession` model instead of creating an unrelated platform session model.

A session records logical metadata, including:

```text
Hidra user
provider/source
start time
last seen time where applicable
expiry
status
correlation information
```

Passwords and bearer tokens must never be persisted in the login-session table.

### 6.4 Refresh strategy

Phase 1 must not invent an uncontrolled refresh-token system.

Initial target:

```text
short-lived access token
explicit re-authentication after expiry
```

A refresh-token capability requires a later explicit security task covering token hashing, rotation, replay detection, revocation, logout semantics, and theft recovery.

### 6.5 Logout

Logout in the initial stateless token phase means:

```text
client deletes bearer token
Hidra LoginSession is marked ended/revoked when an authenticated logout endpoint is used
```

Immediate rejection of a previously issued access token requires session/JTI validation on each request or a revocation cache. Do not claim immediate cryptographic revocation unless that mechanism is implemented.

---

## 7. Proposed API contract

### Public login endpoint

```text
POST /api/v1/security/auth/login
```

Request intent:

```json
{
  "username": "employee-or-local-username",
  "password": "user-supplied-password"
}
```

The client does not choose `LOCAL` or `ACTIVE_DIRECTORY`. Hidra resolves the configured source from the user's identity binding.

Successful response intent:

```json
{
  "tokenType": "Bearer",
  "accessToken": "<opaque-to-client-jwt>",
  "expiresAt": "<timestamp>",
  "user": {
    "id": "<hidra-user-id>",
    "username": "<username>",
    "displayName": "<display-name>",
    "authenticationSource": "ACTIVE_DIRECTORY"
  }
}
```

Do not return password hashes, LDAP DNs unless explicitly required by an administrative API, provider secrets, signing data, or internal failure details.

### Authenticated endpoints

```text
POST /api/v1/security/auth/logout
GET  /api/v1/security/auth/me
```

`/me` is an authentication/session summary. Effective business permissions remain under the Identity authorization contract rather than being duplicated in platform security.

### Retired endpoint

Final target removes:

```text
GET /api/v1/security/oidc
```

Its removal must be coordinated with HidraWEB because the current frontend OIDC flow consumes it.

---

## 8. Data-model target

### 8.1 Provider types

Final business provider types:

```text
LOCAL
ACTIVE_DIRECTORY
```

Do not retain external OIDC/OAuth/SAML provider types as dead runtime options.

### 8.2 AD identity linkage

The existing external/directory identity model may be retained and renamed/evolved only after checking persistence compatibility.

Required AD linkage data includes conceptually:

```text
userId
identityProviderId
immutable directory identifier (preferred)
distinguished name when available
directory username / UPN / sAMAccountName according to deployment mapping
last successful login
last synchronization metadata
status
```

Use a stable AD identifier where available; do not make mutable display name the identity key.

### 8.3 LOCAL credential

Add an Identity-owned credential persistence concept with at minimum:

```text
id
userId
passwordHash
algorithm/encoding metadata when needed
passwordChangedAt
credentialStatus
createdAt
updatedAt
```

Lockout counters remain on the Hidra `User` unless implementation evidence justifies a dedicated authentication-state aggregate.

Never expose this record through ordinary user DTOs.

### 8.4 Provider configuration

ACTIVE_DIRECTORY configuration must support environment-driven technical settings such as:

```text
LDAP/LDAPS URL
base DN
user search base
user search filter / username attribute
bind strategy
service-account reference only if search-bind is required
connection/read timeouts
TLS trust configuration reference
```

Secrets must be referenced, not persisted as plaintext configuration values.

### 8.5 Remove external authorization mapping from runtime

Because Hidra owns roles and permissions, these concepts must not grant runtime authority after completion:

```text
ExternalRoleMapping
ExternalPermissionMapping
external role claims
external permission claims
```

A later data-cleanup task may remove their tables/classes after compatibility analysis. Do not drop potentially populated tables in the same commit that changes runtime behavior.

---

## 9. Configuration target

Replace the current top-level `basic|jwt|disabled` interpretation with a configuration model where bearer JWT is always the protected API transport while login sources are Identity concerns.

Conceptual configuration:

```text
hidra.platform.security.enabled=true
hidra.platform.security.access-token.issuer=hidra
hidra.platform.security.access-token.audience=hidra-api
hidra.platform.security.access-token.ttl=...
hidra.platform.security.access-token.key-id=...
hidra.platform.security.access-token.private-key-reference=...
hidra.platform.security.access-token.public-key-reference=...

hidra.identity.authentication.local.enabled=true
hidra.identity.authentication.active-directory.enabled=true
hidra.identity.authentication.active-directory.url=ldaps://...
hidra.identity.authentication.active-directory.base-dn=...
hidra.identity.authentication.active-directory.user-search-base=...
hidra.identity.authentication.active-directory.user-search-filter=...
```

Exact property names are finalized in the configuration task after checking existing configuration metadata conventions.

Production-like startup must fail closed when:

```text
security is disabled without an explicit development/test allowance
signing material is absent/invalid
ACTIVE_DIRECTORY is enabled with an insecure directory connection contrary to environment policy
required directory search/bind settings are incomplete
LOCAL bootstrap uses a known development password
```

---

## 10. Migration and compatibility strategy

This change affects both HidraAPI and HidraWEB. HidraAPI roadmap tasks remain repository-local, but API compatibility must be explicit.

Recommended transition:

```text
Phase A: add Identity authentication use cases + LOCAL/AD adapters behind tests
Phase B: add Hidra token issuer + /security/auth/login
Phase C: migrate HidraWEB to the new login contract
Phase D: disable Basic bootstrap for ordinary runtime login
Phase E: remove OIDC contract and external-provider runtime paths
Phase F: remove obsolete schema/types only after data inspection
```

Do not delete the current OIDC endpoint before HidraWEB has an accepted replacement.

---

## 11. Roadmap task overview

Execute exactly one code per implementation request.

| Code | Commit message | Status | Purpose |
|---|---|---|---|
| AUTH-001 | `docs(authentication): add ldap and local authentication roadmap` | Completed | Freeze this architecture and execution sequence |
| AUTH-002 | `refactor(identity): narrow human authentication provider types` | Planned | Restrict provider taxonomy and semantics to LOCAL + ACTIVE_DIRECTORY |
| AUTH-003 | `feat(identity): add local credential domain model` | Planned | Add LOCAL credential lifecycle/value contracts without persistence |
| AUTH-004 | `feat(identity): add authentication application contracts` | Planned | Add source resolution, credential verification ports, login result contracts |
| AUTH-005 | `feat(identity): add authentication orchestration service` | Planned | Implement provider-neutral login orchestration and Hidra account checks |
| AUTH-006 | `feat(identity): add local credential persistence` | Planned | Add forward-only Flyway migration, JPA entity/repository/adapter |
| AUTH-007 | `feat(authentication): add local password verification adapter` | Planned | Connect PasswordEncoder to Identity authentication port |
| AUTH-008 | `feat(authentication): add active directory ldap adapter` | Planned | Add LDAP dependency/configuration and AD credential verification adapter |
| AUTH-009 | `feat(identity): add authentication lockout and audit policy` | Planned | Standardize failed-login counters, lockout, successful-login updates, audit events |
| AUTH-010 | `feat(authentication): add hidra access token issuer` | Planned | Issue and validate Hidra-signed JWTs with asymmetric signing |
| AUTH-011 | `feat(authentication): add login session lifecycle` | Planned | Persist/reuse LoginSession metadata and session status transitions |
| AUTH-012 | `feat(authentication): add login and session api` | Planned | Add login/logout/me REST contracts and public-login security rule |
| AUTH-013 | `refactor(authentication): make hidra bearer token the api security contract` | Planned | Remove ordinary Basic runtime auth and validate Hidra-issued bearer tokens |
| AUTH-014 | `feat(identity): add safe local administrator bootstrap` | Planned | Replace in-memory bootstrap with controlled Identity LOCAL bootstrap path |
| AUTH-015 | `test(authentication): add local authentication coverage` | Planned | LOCAL success/failure/status/lockout/token tests |
| AUTH-016 | `test(authentication): add active directory authentication coverage` | Planned | LDAP mapping/bind/failure/TLS/config tests |
| AUTH-017 | `test(authentication): add authorization isolation coverage` | Planned | Prove AD groups do not grant Hidra roles/permissions |
| AUTH-018 | `test(authentication): add security integration coverage` | Planned | End-to-end login -> bearer -> protected route -> logout/session behavior |
| AUTH-019 | `refactor(authentication): retire external oidc runtime contract` | Planned | Remove `/security/oidc` and external provider runtime configuration after frontend migration |
| AUTH-020 | `refactor(identity): retire external authorization mapping runtime` | Planned | Disable/remove external role/permission mapping behavior after data compatibility review |
| AUTH-021 | `docs(authentication): add deployment and active directory runbook` | Planned | Document AD configuration, LOCAL operations, keys, rotation, troubleshooting |
| AUTH-022 | `test(authentication): add architecture and secret guardrails` | Planned | Protect module boundaries, no OIDC runtime, no secrets/logged passwords |
| AUTH-023 | `docs(authentication): finalize authentication checklist` | Planned | Final validation/status record |

---

# 12. Detailed commit specifications

## AUTH-001 — Add LDAP + LOCAL authentication roadmap

### Commit message

```text
docs(authentication): add ldap and local authentication roadmap
```

### Files

```text
Create docs/roadmap/authentication.md
```

### Acceptance criteria

- Exactly two target human credential sources are documented: `LOCAL` and `ACTIVE_DIRECTORY`.
- Hidra remains sole authorization authority.
- Hidra-issued JWT is documented as API token transport, not external provider authentication.
- Existing Identity/platform boundaries are preserved.
- No production code is modified.

### Validation

```bash
test -f docs/roadmap/authentication.md
git diff --check
```

---

## AUTH-002 — Narrow human authentication provider types

### Commit message

```text
refactor(identity): narrow human authentication provider types
```

### Scope

Inventory all references to `ProviderType`, provider-specific switch logic, API DTOs, persistence mappings, synchronization logic, and seed/test fixtures.

Final domain semantics:

```text
LOCAL
ACTIVE_DIRECTORY
```

Do not simply delete enum constants if persisted rows can contain them. Add migration/compatibility handling when necessary.

### Expected files

Primarily:

```text
src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java
identity provider application/API mapping files that expose unsupported types
relevant tests
```

Do not change authentication runtime behavior yet.

### Acceptance criteria

- Unsupported external provider types cannot be newly configured through supported Identity APIs.
- Existing data compatibility is explicitly handled.
- Domain remains Spring-free.

### Validation

```bash
mvn -q -DskipTests compile
mvn -q test
```

---

## AUTH-003 — Add local credential domain model

### Commit message

```text
feat(identity): add local credential domain model
```

### Scope

Add framework-neutral LOCAL credential concepts under Identity domain.

The domain may model:

```text
LocalCredential
LocalCredentialStatus
PasswordHash (opaque encoded representation, if a dedicated value type improves invariants)
credential lifecycle policy
```

Do not put password hashing algorithms in the domain.

### Required invariants

- one active LOCAL credential per LOCAL human user unless a later policy explicitly permits history records;
- hash value is non-blank but treated as opaque;
- disabled/expired credential cannot authenticate;
- no plaintext password field exists in persisted domain state.

### Validation

```bash
mvn -q -DskipTests compile
mvn -q test
```

---

## AUTH-004 — Add authentication application contracts

### Commit message

```text
feat(identity): add authentication application contracts
```

### Scope

Add framework-neutral contracts for:

```text
AuthenticateUserCommand
AuthenticatedUserResult
AuthenticationSourceResolver
CredentialAuthenticationPort
LocalCredentialStore port
DirectoryAuthenticationPort
AccessTokenIssuerPort
LoginSessionStore port as required
AuthenticationEvent output/audit port as required
```

Names must follow existing Identity application naming conventions discovered at execution time.

### Security rule

Raw password is an application input only. It must not be included in domain events, DTO responses, logs, or persistence ports.

### Validation

```bash
mvn -q -DskipTests compile
mvn -q test
```

---

## AUTH-005 — Add authentication orchestration service

### Commit message

```text
feat(identity): add authentication orchestration service
```

### Flow

```text
normalize login identifier
resolve Hidra user
verify account state
resolve explicit configured auth source
invoke exactly one credential-verification port
record success/failure policy outcome
resolve Hidra authorization identity
create logical login session
request Hidra access token
return sanitized result
```

### Must not

```text
fallback AD -> LOCAL
trust AD group as Hidra role
log password
return provider secret
bypass Hidra user status because AD accepted credentials
```

---

## AUTH-006 — Add local credential persistence

### Commit message

```text
feat(identity): add local credential persistence
```

### Scope

Add a forward-only Flyway migration and Identity persistence adapter.

Target table concept:

```text
hidra_identity_local_credential
```

Never edit the original released Identity migration.

### Constraints

- unique active/user binding as appropriate;
- password hash column must not be returned by normal user repository projections;
- timestamps/status required;
- no password-history feature unless separately justified.

### Validation

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
```

---

## AUTH-007 — Add local password verification adapter

### Commit message

```text
feat(authentication): add local password verification adapter
```

### Ownership

Technical password hashing/verifying implementation belongs to platform/infrastructure, while credential state remains Identity business data.

### Requirements

- use Spring Security `PasswordEncoder` abstraction;
- support encoded hash upgrade/re-hash path where safely possible;
- never compare plaintext manually;
- never log supplied password;
- generic authentication failure externally.

---

## AUTH-008 — Add Active Directory LDAP adapter

### Commit message

```text
feat(authentication): add active directory ldap adapter
```

### Scope

Add the Spring LDAP/security dependency required by the selected Spring Boot 4.1.1-compatible implementation.

Implement directory credential verification behind the Identity application port.

### Required configuration

```text
URL
base DN
user search base
user search filter or attribute mapping
timeouts
TLS/trust settings
optional technical bind-account secret reference if search-bind is required
```

### Required production behavior

- production-like mode rejects insecure directory configuration according to the deployment policy;
- TLS certificate validation stays enabled;
- supplied user password is used only for authentication and is not stored;
- directory outage is distinguishable internally from bad credentials but may share a safe external failure response where appropriate;
- directory identity is mapped to the pre-provisioned Hidra user.

### Authorization isolation

Do not import AD roles/groups into Hidra authorization during login.

---

## AUTH-009 — Add lockout and authentication audit policy

### Commit message

```text
feat(identity): add authentication lockout and audit policy
```

### Scope

Use/evolve existing fields and AuthenticationEvent concepts to implement consistent behavior for both sources.

Policy must define:

```text
failed attempt counting
lock threshold
lock duration/reset policy
successful login reset/update
lastAuthenticatedAt
internal failure reason codes
correlation ID
source/provider
client metadata where legally/operationally appropriate
```

Avoid creating a denial-of-service vector where unauthenticated attackers can trivially lock privileged accounts without operational mitigation. Document the chosen trade-off.

---

## AUTH-010 — Add Hidra access-token issuer

### Commit message

```text
feat(authentication): add hidra access token issuer
```

### Requirements

- Hidra signs tokens itself;
- asymmetric signing in production;
- key ID (`kid`) support;
- issuer/audience validation;
- short TTL;
- stable Hidra user ID in `sub`;
- session ID and authentication source claims;
- no password or sensitive AD attributes in token;
- startup fails closed without production signing configuration.

Keep Spring/JWT implementation outside Identity domain.

---

## AUTH-011 — Add login-session lifecycle

### Commit message

```text
feat(authentication): add login session lifecycle
```

### Scope

Reuse/evolve `LoginSession` and its persistence instead of creating a duplicate platform session aggregate.

Define:

```text
start
expire
logout/end
revoke status semantics
last-seen update strategy if used
```

Do not store raw JWTs.

---

## AUTH-012 — Add login/session REST API

### Commit message

```text
feat(authentication): add login and session api
```

### Endpoints

```text
POST /api/v1/security/auth/login   public
GET  /api/v1/security/auth/me      bearer-authenticated
POST /api/v1/security/auth/logout  bearer-authenticated
```

### API rules

- Bean Validation at API boundary;
- generic `401` for invalid authentication;
- `403` remains authorization denial after authentication;
- rate-limiting hook/guard required before production acceptance;
- OpenAPI examples contain no real credentials;
- login endpoint is the only endpoint allowed to receive human passwords.

---

## AUTH-013 — Make Hidra bearer token the protected API contract

### Commit message

```text
refactor(authentication): make hidra bearer token the api security contract
```

### Scope

Refactor `HidraSecurityConfiguration` so ordinary protected requests use only Hidra bearer tokens.

Remove production dependence on `InMemoryUserDetailsManager` and ordinary Basic authentication.

Keep any temporary compatibility switch strictly development-scoped until AUTH-014 and HidraWEB migration are complete.

### Public endpoints

At minimum:

```text
health/info as already approved
OpenAPI endpoints according to environment policy
POST /api/v1/security/auth/login
OPTIONS preflight
```

Do not leave `/api/v1/security/oidc` public in the final state after AUTH-019.

---

## AUTH-014 — Add safe LOCAL administrator bootstrap

### Commit message

```text
feat(identity): add safe local administrator bootstrap
```

### Goal

Replace the current in-memory bootstrap principal with a controlled bootstrap path that creates/activates a real Hidra Identity LOCAL administrator only when explicitly enabled and when no equivalent bootstrap has already completed.

### Requirements

- no committed password;
- production requires externally supplied initial secret or controlled provisioning mechanism;
- password stored only encoded;
- bootstrap is idempotent;
- audit event records bootstrap without recording secret;
- bootstrap can be disabled permanently after initial provisioning;
- default development credential, if retained at all, must be development-only and visibly unsafe for production.

---

## AUTH-015 — LOCAL authentication tests

### Commit message

```text
test(authentication): add local authentication coverage
```

Cover at minimum:

```text
valid password
invalid password
unknown user
wrong auth source
inactive/suspended/disabled user
locked user
credential disabled
failed-attempt update
successful-login reset
hash never exposed
JWT issued only on success
```

---

## AUTH-016 — Active Directory tests

### Commit message

```text
test(authentication): add active directory authentication coverage
```

Cover at minimum:

```text
valid AD bind
invalid credentials
unknown/unlinked directory user
disabled Hidra account with valid AD credentials
AD outage/timeout
incorrect base/search mapping
TLS/configuration failure
no password persistence
no fallback to LOCAL
```

Use a deterministic test strategy compatible with the repository test stack. Do not require access to the company's real AD from CI.

---

## AUTH-017 — Authorization isolation tests

### Commit message

```text
test(authentication): add authorization isolation coverage
```

Prove:

```text
AD credential success does not create role grants
AD group membership does not directly become ROLE_* authorities
LOCAL and AD users with identical Hidra grants receive equivalent Hidra authorization
removing a Hidra role affects authorization independently of AD account validity
```

---

## AUTH-018 — Security integration tests

### Commit message

```text
test(authentication): add security integration coverage
```

End-to-end scenarios:

```text
LOCAL login -> Hidra JWT -> protected endpoint
AD login -> Hidra JWT -> protected endpoint
bad credentials -> 401
valid login but missing permission -> 403
expired token -> 401
wrong issuer/audience/signature -> 401
logout/session transition behavior
CORS preflight for login and bearer requests
correlation/audit event linkage
```

---

## AUTH-019 — Retire external OIDC runtime contract

### Commit message

```text
refactor(authentication): retire external oidc runtime contract
```

### Preconditions

- HidraWEB no longer depends on `/api/v1/security/oidc`.
- New `/api/v1/security/auth/login` integration is accepted.

### Scope

Remove:

```text
HidraOidcContractController
external OIDC browser contract configuration
issuer/client/logout browser bootstrap properties no longer needed
OIDC-only tests/docs
external-provider login paths
```

Do not remove OAuth2 Resource Server/JWT libraries merely because external OIDC is removed if they remain required to validate Hidra-issued bearer tokens.

---

## AUTH-020 — Retire external authorization mapping runtime

### Commit message

```text
refactor(identity): retire external authorization mapping runtime
```

### Scope

Inventory and disable/remove runtime use of:

```text
ExternalRoleMapping
ExternalPermissionMapping
external-claims-derived authority behavior
OIDC/SAML provider-specific synchronization behavior
```

AD groups must not become Hidra roles under this roadmap.

Database table removal, if desired, must be a separate forward-only migration after confirming production data retention/audit requirements.

---

## AUTH-021 — Deployment and AD runbook

### Commit message

```text
docs(authentication): add deployment and active directory runbook
```

Document:

```text
required AD information from infrastructure team
LDAPS endpoint and certificate trust
base/search DN
username mapping choice (UPN vs sAMAccountName)
Hidra user-to-AD identity provisioning
LOCAL user provisioning/password reset
signing-key provisioning and rotation
token TTL
lockout policy
directory outage behavior
health checks without credential leakage
troubleshooting 401 vs 403
```

Do not document real company passwords, DNs containing sensitive personal information, private keys, or production secret values.

---

## AUTH-022 — Architecture and secret guardrails

### Commit message

```text
test(authentication): add architecture and secret guardrails
```

Guard at minimum:

```text
identity.domain has no Spring/LDAP/JWT implementation dependencies
platform security does not own Role/Permission/User business models
no OIDC provider runtime controller remains
no Basic bootstrap authentication remains in production contract
no password fields are serialized in API responses
no known development secret is allowed in production-like startup
```

Add repository secret scanning configuration only if it matches existing project tooling; do not invent a disconnected scanner without CI adoption.

---

## AUTH-023 — Finalize authentication checklist

### Commit message

```text
docs(authentication): finalize authentication checklist
```

Final checklist:

```text
[ ] Provider taxonomy supports only LOCAL + ACTIVE_DIRECTORY for human credentials
[ ] AD authentication uses LDAP/LDAPS adapter
[ ] AD passwords are never persisted
[ ] LOCAL passwords are stored only as adaptive hashes
[ ] No automatic AD -> LOCAL fallback exists
[ ] Hidra account status gates both auth sources
[ ] Hidra owns all roles and permissions
[ ] AD groups do not directly grant runtime authority
[ ] Hidra issues signed short-lived access tokens
[ ] Production signing uses externally supplied asymmetric key material
[ ] Protected API uses Hidra bearer token contract
[ ] In-memory Basic bootstrap is retired
[ ] `/api/v1/security/oidc` is retired
[ ] LOCAL bootstrap is safe and idempotent
[ ] Authentication events are audited without secrets
[ ] LOCAL tests pass
[ ] AD tests pass without real corporate AD dependency
[ ] Authorization-isolation tests pass
[ ] Security integration tests pass
[ ] Architecture guardrails pass
[ ] mvn -q clean verify passes
```

---

## 13. HidraWEB dependency

This roadmap is for HidraAPI only, but HidraWEB must later align to the API contract.

Frontend target:

```text
single username/password login form
POST credentials once to /api/v1/security/auth/login
store access token according to approved frontend security policy
send Authorization: Bearer <token>
no OIDC discovery
no external redirect/callback flow
no user-selectable AD-vs-LOCAL switch by default
```

The backend resolves the user's configured source. This avoids revealing account type and avoids ambiguous fallback behavior.

HidraWEB migration must be completed before AUTH-019 removes the current OIDC contract.

---

## 14. Out of scope

Not part of this roadmap unless a later task explicitly adds them:

```text
Google login
Microsoft Entra OIDC
AD FS OIDC
SAML
Keycloak
Okta
social login
JIT AD user creation
automatic AD-group-to-Hidra-role mapping
refresh tokens
passwordless authentication
WebAuthn/passkeys
TOTP/SMS MFA
machine/service-client OAuth authorization server
cross-domain federation
```

MFA is strongly recommended for privileged LOCAL accounts as a later roadmap, but it is not silently added here.

---

## 15. Operational information required before AUTH-008 production acceptance

The infrastructure/AD team will need to provide deployment values outside source control:

```text
AD DNS/LDAPS endpoint(s)
TLS certificate chain / approved trust mechanism
base DN
user search base
chosen login attribute (commonly UPN or sAMAccountName)
immutable identity attribute to bind to Hidra where available
whether anonymous search is allowed
whether a service bind account is required for lookup
service-bind secret through approved secret management if required
network/firewall reachability from HidraAPI runtime
availability/failover endpoints if applicable
```

These values are deployment inputs, not code constants.

---

## 16. Execution rule

After this roadmap commit, the next implementation task is:

```text
AUTH-002 — refactor(identity): narrow human authentication provider types
```

Do not execute AUTH-003 or any later authentication task in the same request.
