# HidraAPI Authentication Gap-Closure Roadmap — Dynamic LOCAL + LDAP/AD + OIDC Routing

## 1. Document control

| Field | Value |
|---|---|
| Project | HidraAPI |
| Product | Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Roadmap file | `docs/roadmap/authentication.md` |
| Primary business module | `identity` |
| Technical owner | `platform.security` |
| Identity root package | `dz.sh.hidra.modules.identity` |
| Security roots | `dz.sh.hidra.platform.security`, `dz.sh.hidra.platform.configuration` |
| Persistence | Spring Data JPA + PostgreSQL + Flyway |
| Database prefix | `hidra_identity_*` |
| Author | Abir MEDJERAB |
| CreatedOn | 2025-06-26 |
| UpdatedOn | 2026-09-15 |
| Status | Planned — gap-closure roadmap |
| Execution mode | One roadmap commit code at a time |

---

## 2. Purpose

This roadmap is a **gap-closure plan**, not a replacement of the Identity implementation that already exists in HidraAPI.

The repository already models authentication providers, users, roles, permissions, external identities, login sessions, authentication events, and authorization decisions. Those concepts must be reused when they are correct.

The target is to complete the runtime bridge between the existing Identity model and Spring Security so that the authentication type selected by the client dynamically reaches the correct authentication strategy.

Target human authentication types:

```text
LOCAL
LDAP / ACTIVE_DIRECTORY
OIDC
```

All successful authentication paths must converge on:

```text
Hidra User
   -> Hidra account-state validation
   -> Hidra roles/permissions
   -> normalized HidraPrincipal
   -> LoginSession / AuthenticationEvent
   -> Hidra-issued JWT bearer token
   -> protected HidraAPI routes
```

The roadmap explicitly preserves working OIDC/JWT capabilities and closes only the missing LOCAL, LDAP, runtime-routing, and token-normalization gaps.

---

## 3. Current-state baseline to preserve

### 3.1 Existing Identity provider model

The repository already contains provider concepts including:

```text
ProviderType.LOCAL
ProviderType.LDAP
ProviderType.ACTIVE_DIRECTORY
ProviderType.OIDC
ProviderType.OAUTH2
ProviderType.SAML2
ProviderType.KEYCLOAK
ProviderType.AZURE_AD
ProviderType.OKTA
```

It also has `AuthenticationProtocol` values including:

```text
LOCAL
LDAP
OIDC
SAML2
OAUTH2
API_TOKEN
SYSTEM
```

Do not invent a second provider taxonomy merely to implement runtime routing.

`ProviderType` and/or `AuthenticationProtocol` must be evaluated in AUTH-002 and reused where they already express the required invariant.

### 3.2 Existing Identity business assets

Preserve unless a concrete defect is demonstrated:

```text
User
Role
Permission
IdentityProvider
ExternalIdentity
LoginSession
AuthenticationEvent
AuthorizationDecision
Group
UserRoleGrant
UserPermissionGrant
RolePermissionGrant
GroupRoleGrant
AuthorizationPolicy
```

### 3.3 Existing user security state

The existing `User` model already carries security lifecycle information such as:

```text
status
lastAuthenticatedAt
failedLoginCount
lockedUntil
```

Do not create a second account-state model for LOCAL or LDAP authentication.

### 3.4 Existing external identity linkage

The existing `ExternalIdentity` model already contains fields suitable for LDAP/AD and OIDC identity binding, including concepts such as:

```text
identityProviderId
externalSubject
externalImmutableId
externalUsername
externalEmail
externalDisplayName
externalDistinguishedName
lastLoginAt
lastSyncedAt
status
```

Reuse it rather than introducing duplicate provider-specific user aggregates.

### 3.5 Existing OIDC/JWT capability

The repository already publishes a browser OIDC/JWT acquisition contract through:

```text
GET /api/v1/security/oidc
```

and already contains JWT resource-server validation/authority conversion infrastructure.

OIDC is therefore a **reuse and normalization path**, not a capability to delete and rebuild.

The implementation inventory must distinguish between:

```text
existing OIDC browser/bootstrap behavior
existing external JWT validation
missing mapping to normalized HidraPrincipal
missing/unified Hidra token issuance where applicable
```

before changing working OIDC code.

### 3.6 Existing temporary LOCAL runtime

Current development Basic authentication uses an `InMemoryUserDetailsManager` bootstrap account.

That mechanism is temporary infrastructure and must not be confused with the existing business meaning of:

```text
ProviderType.LOCAL
```

LOCAL already means Hidra-owned username/password authentication. The gap is persistent credential verification and runtime wiring.

### 3.7 Persistence baseline

HidraAPI already uses:

```text
Spring Data JPA
PostgreSQL
Flyway
```

LOCAL credential persistence must extend that stack.

Do not introduce MySQL only for authentication. A PostgreSQL-to-MySQL platform migration, if ever required, is a separate repository-wide roadmap.

### 3.8 Released migration protection

Existing released Identity migrations, including the initial Identity schema migration, must not be edited to retrofit authentication.

All schema changes are forward-only Flyway migrations.

---

## 4. Frozen architectural decisions

### 4.1 Client-selected authentication type is meaningful

The runtime authentication contract must preserve the existing/client-accepted concept that the caller selects an authentication type.

Conceptual request:

```json
{
  "authType": "LOCAL",
  "username": "...",
  "password": "..."
}
```

or:

```json
{
  "authType": "LDAP",
  "username": "...",
  "password": "..."
}
```

OIDC is different: Hidra must **not** collect an external provider password. `authType=OIDC` initiates or completes the existing authorization-code/OIDC flow, and the validated OIDC result is then normalized into the same Hidra principal/token pipeline.

AUTH-002 must locate the exact live DTO/endpoint and reuse its actual field names rather than create a duplicate request contract.

### 4.2 Runtime routing uses Spring Security provider delegation

The target runtime pattern is:

```text
request authType
      |
      v
AuthenticationRequestFactory / router
      |
      +--> LocalAuthenticationToken
      +--> LdapAuthenticationToken
      +--> OidcAuthenticationToken / OIDC completion result
      |
      v
AuthenticationManager
      |
      v
ProviderManager
      |
      +--> LocalAuthenticationProvider
      +--> LdapAuthenticationProvider
      +--> OidcAuthenticationProvider / adapter
```

Each provider must advertise support for only its own authentication token type.

Do not route LOCAL and LDAP by registering several providers that all support the same generic username/password token and rely only on provider ordering.

### 4.3 No silent provider fallback

These flows are forbidden:

```text
LDAP fails -> try LOCAL
LOCAL fails -> try LDAP
OIDC fails -> try LOCAL
provider unavailable -> try another provider
```

`authType` selects the credential authority. Authentication failure in that authority fails the login attempt.

### 4.4 Authentication source does not own authorization

All three providers answer only:

```text
Who successfully authenticated this identity?
```

Hidra remains authoritative for:

```text
User status
Roles
Permissions
Organization/resource scope
AuthorizationPolicy
Business authorization decisions
```

Invalid patterns:

```text
AD group -> direct Spring ROLE_* -> business access
OIDC provider role -> direct controller authorization
LOCAL login -> hard-coded administrator role
LDAP DN -> permission decision
```

### 4.5 All providers converge on one Hidra principal

A successful provider authentication must resolve to a stable Hidra user and produce one normalized principal representation.

Conceptual shape:

```java
public record HidraPrincipal(
        String userId,
        String username,
        String displayName,
        ProviderType authenticationType,
        String identityProviderId,
        Set<String> roles,
        Set<String> permissions
) implements Principal { }
```

The exact class/record name is subject to AUTH-002 inventory. Reuse an equivalent existing principal contract if one already exists.

The stable identity is the Hidra user ID, not a mutable LDAP username, email address, DN, or raw OIDC subject.

### 4.6 All successful logins converge on one API bearer-token format

After LOCAL, LDAP/AD, or OIDC succeeds:

```text
provider authentication
      -> Hidra User
      -> HidraPrincipal
      -> LoginSession
      -> Hidra token issuer
      -> standardized JWT
```

Protected APIs consume the same Hidra bearer JWT regardless of original authentication source.

The original provider may be included as an informational claim such as:

```text
auth_source = LOCAL | LDAP | ACTIVE_DIRECTORY | OIDC
```

but it must not become an authorization shortcut.

---

## 5. Actual gaps to close

### GAP-AUTH-01 — selected auth type is not the complete runtime dispatcher

The API/client contract can express an authentication type, but the runtime implementation is not yet fully backed by a deterministic Spring Security provider-routing layer for all supported provider types.

Missing/required:

```text
provider-specific Authentication token types or equivalent dispatch discriminator
request -> Authentication conversion
central AuthenticationManager / ProviderManager composition
provider-specific supports(...) behavior
no-fallback rules
unsupported-provider behavior
```

### GAP-AUTH-02 — LOCAL exists conceptually but uses temporary in-memory runtime authentication

Missing/required:

```text
persistent LOCAL credential model if absent
password hash persistence
Spring Data JPA adapter
PasswordEncoder-backed verification
LOCAL AuthenticationProvider
User/account-state enforcement
failed/successful login state updates
```

### GAP-AUTH-03 — LDAP/Active Directory has no complete runtime provider

Missing/required:

```text
Spring LDAP/Spring Security LDAP dependency as required
LDAPS/TLS configuration
AD bind/search strategy
LDAP AuthenticationProvider adapter
ExternalIdentity -> Hidra User resolution
failure normalization
timeout/directory-unavailable behavior
no AD-group authorization bypass
```

### GAP-AUTH-04 — OIDC success must converge on the same Hidra principal

OIDC/JWT infrastructure already exists and must be preserved.

The gap-closure work must prove and, only where missing, add:

```text
OIDC identity -> ExternalIdentity mapping
ExternalIdentity -> Hidra User mapping
Hidra account-state enforcement
Hidra role/permission loading
normalized HidraPrincipal result
unified Hidra token/session behavior
```

### GAP-AUTH-05 — provider-specific results are not yet guaranteed to share one token contract

All providers must emit the same protected-API credential contract.

Missing/required where not already implemented:

```text
Hidra JWT encoder/issuer
stable Hidra subject
issuer/audience/expiry/JTI claims
session linkage
consistent principal reconstruction
compatibility with current JwtDecoder/resource-server configuration
```

### GAP-AUTH-06 — temporary Basic bootstrap must be retired safely

`InMemoryUserDetailsManager` must stop serving as ordinary LOCAL authentication only after database-backed LOCAL login is proven and a safe installation/bootstrap path exists.

### GAP-AUTH-07 — integration and security tests are incomplete

The repository needs explicit tests for provider routing, LOCAL persistence, LDAP, OIDC normalization, JWT unification, no-fallback behavior, and Hidra-owned authorization.

---

## 6. Target runtime flow

```text
                         Login / auth selection
                                 |
                                 v
                        read/validate authType
                                 |
                +----------------+----------------+
                |                |                |
                v                v                v
              LOCAL          LDAP / AD           OIDC
                |                |                |
                v                v                v
      LocalAuthentication  LdapAuthentication   existing OIDC
            Token               Token           browser/code flow
                |                |                |
                +----------------+----------------+
                                 |
                                 v
                       AuthenticationManager
                         (ProviderManager)
                                 |
                +----------------+----------------+
                |                |                |
                v                v                v
             LOCAL            LDAP/AD            OIDC
            Provider          Provider           Adapter
                |                |                |
                v                v                v
        PostgreSQL hash      AD via LDAPS      external IdP
                |                |                |
                +----------------+----------------+
                                 |
                                 v
                        resolve Hidra User
                                 |
                                 v
                       enforce account state
                                 |
                                 v
                      load Hidra authorization
                                 |
                                 v
                         HidraPrincipal
                                 |
                    +------------+------------+
                    |                         |
                    v                         v
            AuthenticationEvent          LoginSession
                    |                         |
                    +------------+------------+
                                 |
                                 v
                         Hidra JWT issuer
                                 |
                                 v
                    Authorization: Bearer ...
                                 |
                                 v
                             HidraAPI
```

---

## 7. Dynamic routing architecture

### 7.1 Provider-specific Authentication tokens

Preferred pattern:

```java
final class LocalAuthenticationToken extends AbstractAuthenticationToken { ... }
final class LdapAuthenticationToken extends AbstractAuthenticationToken { ... }
final class OidcAuthenticationToken extends AbstractAuthenticationToken { ... }
```

Exact class names are implementation details. AUTH-002 must first check for existing equivalent types.

Why use distinct token types:

```text
LocalAuthenticationProvider.supports(LocalAuthenticationToken)
LdapAuthenticationProvider.supports(LdapAuthenticationToken)
Oidc adapter/provider supports OIDC token/result type
```

This makes routing deterministic and prevents provider-order fallback.

### 7.2 Request-to-Authentication conversion

Use a single application/platform boundary responsible for translating the selected auth type into the corresponding Spring Security `Authentication` request.

Conceptual example:

```java
Authentication create(LoginRequest request) {
    return switch (request.authType()) {
        case LOCAL -> LocalAuthenticationToken.unauthenticated(
                request.username(), request.password());
        case LDAP, ACTIVE_DIRECTORY -> LdapAuthenticationToken.unauthenticated(
                request.username(), request.password());
        case OIDC -> oidcRequestFactory.from(request);
        default -> throw new UnsupportedAuthenticationTypeException(...);
    };
}
```

This factory/router selects the strategy. It does not verify credentials, resolve permissions, issue tokens, or query repositories directly.

### 7.3 Central AuthenticationManager

Compose providers behind one `ProviderManager`/`AuthenticationManager`.

Conceptual example:

```java
@Bean
AuthenticationManager hidraAuthenticationManager(
        LocalAuthenticationProvider localProvider,
        HidraLdapAuthenticationProvider ldapProvider,
        HidraOidcAuthenticationAdapter oidcProvider) {

    return new ProviderManager(List.of(
            localProvider,
            ldapProvider,
            oidcProvider
    ));
}
```

Reuse existing Spring configuration style and beans when possible.

### 7.4 Provider contract

Each provider:

```text
1. Accepts only its own token/result type.
2. Verifies credentials/identity through the proper authority.
3. Resolves that identity to a Hidra User.
4. Enforces Hidra User account state.
5. Loads Hidra-owned roles/permissions.
6. Returns an authenticated token containing HidraPrincipal.
7. Never issues business permissions directly from external groups/claims.
```

### 7.5 Unsupported provider handling

Unknown/disabled `authType` values fail closed.

Do not reinterpret them as LOCAL or any default provider.

---

## 8. LOCAL authentication migration

### 8.1 Preserve existing LOCAL semantics

`ProviderType.LOCAL` already represents Hidra-owned username/password authentication.

Do not rename it and do not add a second `DATABASE` provider type merely because persistence is being completed.

### 8.2 Add only the missing credential persistence

If AUTH-002 confirms no equivalent credential table/model exists, introduce the minimum Identity-owned credential structure.

Conceptual fields:

```text
id
userId
passwordHash
credentialStatus
passwordChangedAt
createdAt
updatedAt
version (only if aligned with module persistence conventions)
```

Relationship intent:

```text
hidra_identity_user
       1
       |
       | 0..1
       v
hidra_identity_local_credential
```

AD/OIDC users do not require a LOCAL credential row unless explicitly configured for LOCAL as an allowed authentication method.

### 8.3 Spring Data JPA adapter

Use the existing Identity infrastructure style.

Conceptual infrastructure:

```java
interface LocalCredentialJpaRepository
        extends JpaRepository<LocalCredentialJpaEntity, String> {

    Optional<LocalCredentialJpaEntity> findByUserId(String userId);
}
```

The application/domain layer must depend on an outbound port rather than Spring Data directly.

### 8.4 Password storage

Use Spring Security `PasswordEncoder` through a technical adapter.

Requirements:

```text
one-way adaptive hash only
no plaintext password persistence
no reversible password encryption
no password in DTOs/events/logs
no production default password
```

Prefer `DelegatingPasswordEncoder` or the repository's already accepted encoder configuration so algorithm migration remains possible.

### 8.5 LocalAuthenticationProvider

Conceptual responsibilities:

```text
resolve Hidra user
verify requested LOCAL provider is allowed
check status / lock state
load LOCAL credential
verify hash
record failed/successful login state
resolve Hidra authorization
return authenticated HidraPrincipal
```

Do not perform token signing inside the provider.

### 8.6 Remove in-memory LOCAL only after proving replacement

Migration order:

```text
1. Add persistent LOCAL credential capability.
2. Add LOCAL provider tests.
3. Provision a controlled LOCAL administrator through normal Identity persistence.
4. Switch ordinary LOCAL login to database-backed provider.
5. Remove ordinary reliance on InMemoryUserDetailsManager.
6. Keep any emergency/bootstrap behavior separate, explicit, and production-safe.
```

A failed DB-backed LOCAL login must never fall through to the old in-memory user.

---

## 9. LDAP / Active Directory integration

### 9.1 Preserve provider model

Reuse:

```text
ProviderType.LDAP and/or ProviderType.ACTIVE_DIRECTORY
IdentityProvider
ExternalIdentity
User
AuthenticationEvent
LoginSession
```

AUTH-002 must determine the intended semantic distinction between `LDAP` and `ACTIVE_DIRECTORY` before changing either enum value.

### 9.2 Technical dependency

Add only the Spring LDAP/Spring Security LDAP dependency actually required by the chosen implementation.

LDAP-specific Spring classes belong to infrastructure/platform code, not the Identity domain.

### 9.3 Production transport

Production must use a protected directory channel:

```text
LDAPS
```

or explicitly validated StartTLS/TLS configuration.

Never disable directory TLS certificate verification to make authentication work.

### 9.4 AD bind/search strategy

Support configuration for the company's real AD layout.

Possible patterns:

```text
direct user bind
service-account search + user bind
```

Repository code must not commit company bind-account passwords.

### 9.5 LDAP provider flow

Conceptual flow:

```text
LdapAuthenticationToken
     |
     v
HidraLdapAuthenticationProvider
     |
     v
Spring LDAP / AD credential verification
     |
     v
extract stable directory identity
     |
     v
ExternalIdentity lookup
     |
     v
Hidra User
     |
     v
HidraPrincipal
```

### 9.6 Stable AD identity

Prefer immutable directory identifiers when available.

Store/reuse them through the existing `ExternalIdentity` model instead of relying solely on mutable usernames, email addresses, or display names.

### 9.7 AD does not own Hidra roles

Directory groups may be synchronized or displayed if an existing feature requires them, but they must not become automatic Hidra authorities unless a separately approved policy explicitly maps them.

Default invariant:

```text
AD authenticates
Hidra authorizes
```

### 9.8 LDAP failure categories

Internally distinguish:

```text
invalid credentials
unknown directory identity
missing Hidra mapping
Hidra account disabled/locked
LDAP timeout
LDAP unavailable
TLS/configuration failure
```

Directory unavailability is not a bad password and must not trigger LOCAL fallback.

### 9.9 No AD password persistence

AD credentials exist only for the duration of the login operation.

Do not persist, cache, hash-for-reuse, publish in events, or log the AD password.

---

## 10. OIDC preservation and normalization

### 10.1 OIDC is a supported runtime path

Do not classify OIDC as a future/non-goal authentication mechanism.

Existing OIDC/JWT browser integration and resource-server code is an asset to preserve.

### 10.2 OIDC authentication is not username/password authentication

If the user selects OIDC, Hidra must initiate/use the external authorization flow.

Forbidden:

```text
Hidra collecting Google/IdP passwords
OIDC request sending external password to HidraAPI
```

Expected browser flow:

```text
authType=OIDC
   -> obtain/use OIDC bootstrap metadata
   -> authorization code + PKCE flow
   -> callback / validated provider result
   -> external identity resolution
   -> Hidra User
   -> HidraPrincipal
```

### 10.3 Preserve existing OIDC contract

Keep `GET /api/v1/security/oidc` unless AUTH-002 proves another canonical contract has replaced it.

Do not remove working issuer/client/audience/scope metadata while integrating LOCAL/LDAP.

### 10.4 Normalize external identity

After OIDC authentication succeeds:

```text
issuer + subject
      -> IdentityProvider / ExternalIdentity
      -> Hidra User
      -> Hidra account-state validation
      -> Hidra role/permission resolution
      -> HidraPrincipal
```

Email alone must not be treated as the durable external identity key.

### 10.5 OIDC authorization remains Hidra-owned

External provider roles/groups/claims must not bypass Hidra authorization.

Claims may assist identity mapping only under explicit policy.

---

## 11. Unified Hidra principal

### 11.1 Purpose

Every successful provider must return the same business-facing authenticated identity contract.

### 11.2 Required identity content

At minimum:

```text
stable Hidra user ID
username/display name
provider/authentication source
identity provider ID when relevant
Hidra roles
Hidra permissions or permission-resolution handle
account/session context needed by existing authorization
```

### 11.3 Do not expose provider-specific principals downstream

Business modules must not need to know whether the request originally came from:

```text
LDAP DN
OIDC subject
LOCAL username
```

They consume the authenticated Hidra identity/authorization contract.

---

## 12. Token unification

### 12.1 One API credential after authentication

Successful LOCAL, LDAP/AD, and OIDC authentication should all produce the same Hidra bearer-token format.

### 12.2 Reuse current JWT resource-server pieces

Before creating new token classes, inventory and reuse compatible existing:

```text
JwtDecoder
JwtAuthenticationConverter
HidraJwtGrantedAuthoritiesConverter
issuer/audience validation
SecurityFilterChain bearer support
permission resolution
CORS Authorization header handling
```

### 12.3 Token issuer

Add a Hidra `JwtEncoder`/issuer only where missing.

Conceptual claims:

```text
iss = Hidra issuer
sub = stable Hidra user ID
aud = hidra-api
iat
exp
jti
sid = LoginSession ID when adopted
auth_source = LOCAL | LDAP | ACTIVE_DIRECTORY | OIDC
```

Roles/scopes may be included only in a way compatible with the existing authorization model and documented stale-authorization policy.

### 12.4 Signing material

Production signing material must be externalized.

Do not commit private keys or production HMAC secrets.

Prefer asymmetric signing for production unless an accepted Hidra standard requires otherwise.

### 12.5 Session linkage

Reuse existing `LoginSession` rather than creating a second platform session table.

Do not store raw bearer tokens in the session table.

---

## 13. API contract

### 13.1 Preserve the existing authentication-type selection

The canonical login boundary must accept/retain the provider selection already expected by the client.

Conceptual LOCAL request:

```json
{
  "authType": "LOCAL",
  "username": "operator1",
  "password": "..."
}
```

Conceptual LDAP request:

```json
{
  "authType": "LDAP",
  "username": "operator1",
  "password": "..."
}
```

AUTH-002 must confirm exact live enum/value names, DTOs, and URL before code is added.

### 13.2 OIDC initiation

OIDC selection should initiate the browser/external provider flow, not request an external password.

The existing OIDC contract endpoint remains part of this flow unless inventory proves otherwise.

### 13.3 Unified login result

LOCAL/LDAP direct credential login should return a common safe result after successful authentication, conceptually:

```json
{
  "accessToken": "...",
  "tokenType": "Bearer",
  "expiresAt": "...",
  "principal": {
    "userId": "...",
    "username": "...",
    "authenticationType": "LOCAL"
  }
}
```

OIDC completion should converge on the same access-token/principal result.

### 13.4 Current principal/session

Reuse existing principal/identity APIs where sufficient. Add a dedicated endpoint only when a real gap exists.

Possible target:

```text
GET /api/v1/security/auth/me
```

Do not duplicate existing Identity permission endpoints.

### 13.5 Logout

Use the existing `LoginSession` lifecycle where appropriate.

Do not claim immediate JWT revocation unless the API actually enforces session/JTI revocation.

---

## 14. Configuration model

The final configuration must distinguish:

```text
provider availability
provider technical configuration
JWT bearer validation/signing
```

Conceptual configuration:

```text
hidra.identity.authentication.local.enabled=true

hidra.identity.authentication.ldap.enabled=true
hidra.identity.authentication.ldap.url=ldaps://...
hidra.identity.authentication.ldap.base-dn=...
hidra.identity.authentication.ldap.user-search-base=...
hidra.identity.authentication.ldap.user-search-filter=...
hidra.identity.authentication.ldap.manager-dn=${...}
hidra.identity.authentication.ldap.manager-password=${...}

hidra.identity.authentication.oidc.enabled=true

hidra.platform.security.jwt.issuer-uri=...
hidra.platform.security.jwt.audience=hidra-api
hidra.platform.security.access-token.*=...
```

Exact property names must preserve compatible existing repository conventions discovered in AUTH-002.

Do not replace the whole security configuration just to add provider routing.

---

## 15. Database strategy

### 15.1 Database remains PostgreSQL

Authentication work uses the same Hidra transactional database and Flyway pipeline as the rest of HidraAPI.

### 15.2 Preserve current Identity tables

Do not drop or rename existing User, provider, external identity, role/permission, authentication-event, or login-session tables.

### 15.3 Add only missing LOCAL credential storage

If AUTH-002 confirms no persistent LOCAL credential structure exists, add one forward-only migration.

### 15.4 No destructive migration during gap closure

Do not rewrite released migrations or delete unused external-provider schema while completing LOCAL/LDAP routing.

---

## 16. Error, audit, and security behavior

### 16.1 Public failures

Public authentication failures must avoid user/provider enumeration.

For invalid direct credentials, use a generic result such as:

```text
Invalid credentials.
```

### 16.2 Internal failure classification

`AuthenticationEvent` may record normalized non-secret reasons such as:

```text
INVALID_CREDENTIALS
ACCOUNT_DISABLED
ACCOUNT_LOCKED
PROVIDER_NOT_ALLOWED
IDENTITY_MAPPING_FAILED
DIRECTORY_UNAVAILABLE
DIRECTORY_TIMEOUT
OIDC_VALIDATION_FAILED
UNSUPPORTED_AUTH_TYPE
```

### 16.3 Never log secrets

Never log:

```text
LOCAL password
AD password
OIDC authorization code
OIDC access token
OIDC ID token
Hidra bearer token
LDAP bind secret
JWT private key
```

### 16.4 Account state is provider-independent

Valid LOCAL, LDAP, or OIDC authentication still fails Hidra login establishment when the mapped Hidra User is disabled, suspended, locked, or otherwise prohibited by existing Identity rules.

---

## 17. Testing strategy

### 17.1 Routing tests

Required scenarios:

```text
authType=LOCAL -> only LocalAuthenticationProvider handles request
authType=LDAP -> only LDAP provider handles request
authType=OIDC -> OIDC flow/adapter handles request
unsupported authType -> fail closed
LOCAL failure -> no LDAP/OIDC fallback
LDAP failure -> no LOCAL/OIDC fallback
OIDC failure -> no LOCAL/LDAP fallback
```

### 17.2 LOCAL tests

```text
valid persisted password -> success
invalid password -> 401
unknown user -> safe failure
LOCAL not allowed for user -> safe failure
locked/disabled/suspended user -> rejected
failedLoginCount/lockedUntil policy -> enforced
password hash -> never exposed
in-memory user -> not consulted for ordinary LOCAL login after cutover
```

### 17.3 LDAP tests

```text
valid AD credential + mapped Hidra user -> success
invalid AD credential -> 401
valid AD credential + missing Hidra mapping -> rejected
Hidra-disabled mapped user -> rejected
LDAP unavailable -> operational failure, no LOCAL fallback
timeout -> handled deterministically
TLS configuration guard -> enforced
AD group membership -> no direct Hidra permission
```

Use a controlled LDAP test fixture/server/container appropriate to the repository test strategy.

### 17.4 OIDC tests

```text
OIDC contract remains compatible
valid external identity -> maps to Hidra User
unmapped identity -> rejected unless explicit provisioning policy exists
Hidra-disabled user -> rejected
external groups/roles -> do not bypass Hidra authorization
OIDC completion -> normalized HidraPrincipal
```

### 17.5 Unified JWT tests

```text
LOCAL login -> Hidra JWT accepted by SecurityFilterChain
LDAP login -> same JWT contract
OIDC completion -> same JWT contract
sub -> stable Hidra user ID
issuer/audience/expiry validated
invalid/expired token -> 401
valid token without Hidra permission -> 403
```

### 17.6 Architecture/secret tests

Protect these boundaries:

```text
Identity domain does not import Spring LDAP implementation classes
controllers do not query JPA repositories directly
providers do not assign business roles from external systems
password hashes do not appear in ordinary DTOs
no plaintext credentials/private keys in repository
platform does not own User/Role/Permission business meaning
```

---

## 18. Execution roadmap

This is a gap-closure sequence. Only one commit code may be executed per task.

| Code | Commit message | Purpose | Status |
|---|---|---|---|
| AUTH-001 | `docs(authentication): add ldap and local authentication roadmap` | Establish initial execution memory | Completed |
| AUTH-001A | `docs(authentication): refocus roadmap on authentication gaps` | Preserve correct Identity implementation and target runtime gaps | Completed |
| AUTH-001B | `docs(authentication): align roadmap with dynamic provider routing` | Preserve OIDC, make `authType` runtime routing explicit, add central provider routing and unified JWT plan | Completed |
| AUTH-002 | `chore(authentication): inventory existing authentication runtime contracts` | Classify live provider DTOs, authType contract, OIDC flow, Basic/in-memory path, JWT components, Identity models, persistence, and missing pieces | Planned |
| AUTH-003 | `feat(authentication): add provider-specific authentication request tokens` | Add/reuse distinct Spring Authentication request types for deterministic LOCAL, LDAP/AD, and OIDC routing | Planned |
| AUTH-004 | `feat(authentication): add authentication request router` | Convert selected auth type into the correct Authentication request without credential verification | Planned |
| AUTH-005 | `feat(authentication): compose authentication provider manager` | Register provider-specific strategies behind one AuthenticationManager/ProviderManager with no fallback | Planned |
| AUTH-006 | `feat(identity): normalize authenticated hidra principal` | Reuse/add one Hidra principal contract shared by LOCAL, LDAP/AD, and OIDC | Planned |
| AUTH-007 | `refactor(authentication): normalize existing oidc authentication` | Preserve working OIDC flow while mapping successful identities to Hidra User/HidraPrincipal and Hidra authorization | Planned |
| AUTH-008 | `feat(identity): add local credential model and port` | Add only the missing LOCAL credential domain/application contract if inventory confirms absence | Planned |
| AUTH-009 | `feat(identity): add local credential persistence` | Add forward Flyway migration plus JPA repository/adapter for LOCAL password hashes | Planned |
| AUTH-010 | `feat(authentication): add database local authentication provider` | Replace ordinary in-memory LOCAL verification with persistent password verification behind LocalAuthenticationProvider | Planned |
| AUTH-011 | `feat(identity): record local authentication outcomes` | Apply existing User lock/login state and AuthenticationEvent to LOCAL success/failure | Planned |
| AUTH-012 | `feat(authentication): add ldap security infrastructure` | Add required LDAP dependency/configuration/TLS/timeouts without domain coupling | Planned |
| AUTH-013 | `feat(authentication): add ldap credential verification adapter` | Implement AD/LDAP bind/search credential verification | Planned |
| AUTH-014 | `feat(authentication): add ldap authentication provider` | Route LDAP token through directory verification, ExternalIdentity mapping, Hidra account state, and HidraPrincipal | Planned |
| AUTH-015 | `feat(authentication): add unified hidra access token issuer` | Add/reuse JwtEncoder and issue one standardized Hidra JWT for all providers | Planned |
| AUTH-016 | `feat(identity): complete authentication session lifecycle` | Reuse LoginSession and AuthenticationEvent for all provider paths | Planned |
| AUTH-017 | `feat(authentication): wire dynamic login endpoint` | Wire existing login/authType contract to request router, AuthenticationManager, session, and token issuer | Planned |
| AUTH-018 | `feat(authentication): converge oidc completion on hidra token` | Ensure OIDC completion produces same Hidra principal/session/JWT result as LOCAL/LDAP | Planned |
| AUTH-019 | `refactor(security): standardize protected api bearer authentication` | Make protected APIs consume the unified Hidra JWT while preserving authorization behavior | Planned |
| AUTH-020 | `refactor(security): retire ordinary in-memory local authentication` | Remove InMemoryUserDetailsManager as ordinary LOCAL login only after DB path is proven | Planned |
| AUTH-021 | `feat(authentication): add safe local administrator bootstrap` | Provide controlled persistent LOCAL administrator provisioning without permanent in-memory fallback | Planned |
| AUTH-022 | `test(authentication): cover dynamic provider routing` | Verify authType dispatch, supports contracts, unsupported type, and no fallback | Planned |
| AUTH-023 | `test(authentication): cover local authentication` | Unit/integration/API coverage for persisted LOCAL authentication | Planned |
| AUTH-024 | `test(authentication): cover ldap authentication` | LDAP/AD adapter, mapping, outage, TLS, and end-to-end coverage | Planned |
| AUTH-025 | `test(authentication): cover oidc normalization` | Protect existing OIDC behavior and prove Hidra principal/authorization normalization | Planned |
| AUTH-026 | `test(authentication): verify unified jwt compatibility` | Verify all providers produce tokens accepted by current security filters and permission enforcement | Planned |
| AUTH-027 | `test(authentication): protect hidra authorization ownership` | Prove external groups/claims do not bypass Hidra role/permission decisions | Planned |
| AUTH-028 | `docs(authentication): add ldap and provider deployment runbook` | Document non-secret provider/TLS/configuration inputs and diagnostics | Planned |
| AUTH-029 | `test(authentication): add authentication architecture and secret guardrails` | Enforce module boundaries, credential secrecy, and provider isolation | Planned |
| AUTH-030 | `docs(authentication): finalize authentication gap closure checklist` | Record executable evidence and remaining optional cleanup | Planned |

---

## 19. Detailed task specifications

### AUTH-002 — Inventory existing authentication runtime contracts

Commit:

```text
chore(authentication): inventory existing authentication runtime contracts
```

Purpose: establish evidence before production changes.

Inspect at minimum:

```text
ProviderType
AuthenticationProtocol
IdentityProvider
ExternalIdentity
User
LoginSession
AuthenticationEvent
existing authenticated-principal contracts
current login/auth request DTOs and controllers
exact authType field/value contract
HidraSecurityConfiguration
InMemoryUserDetailsManager bootstrap path
HidraOidcContractController
OIDC callback/client behavior where implemented
JwtDecoder / converters / claim mapping
permission resolver/interceptor
application*.properties
pom.xml security/LDAP dependencies
Identity Flyway schema
Identity JPA repositories/adapters
HidraWEB authentication contract where API compatibility requires it
```

Update this roadmap with a live inventory classifying each item as:

```text
REUSE
EXTEND
MISSING
DEPRECATION CANDIDATE
```

Do not add implementation classes in AUTH-002.

Validation: documentation/source inspection only; record evidence in this roadmap.

---

### AUTH-003 — Provider-specific Authentication request tokens

Commit:

```text
feat(authentication): add provider-specific authentication request tokens
```

Precondition: AUTH-002 confirms equivalent token types do not already exist.

Requirements:

```text
distinct LOCAL request token
distinct LDAP/AD request token
OIDC request/result adapter only where required
credentials erased where Spring contract permits
no business authorization logic
no persistence access
```

Validation:

```bash
mvn -q test
```

---

### AUTH-004 — Authentication request router

Commit:

```text
feat(authentication): add authentication request router
```

Convert the existing `authType` request contract into the correct Spring `Authentication` request.

Requirements:

```text
LOCAL -> LOCAL token
LDAP/ACTIVE_DIRECTORY -> LDAP token
OIDC -> existing OIDC initiation/completion path
unsupported/disabled type -> fail closed
no credential verification in router
no provider fallback
```

Validation:

```bash
mvn -q test
```

---

### AUTH-005 — ProviderManager composition

Commit:

```text
feat(authentication): compose authentication provider manager
```

Register the supported provider strategies behind one `AuthenticationManager`/`ProviderManager`.

Each provider must support only the intended Authentication request type.

Do not use provider ordering as the primary authType discriminator.

Validation:

```bash
mvn -q test
```

---

### AUTH-006 — Normalized Hidra principal

Commit:

```text
feat(identity): normalize authenticated hidra principal
```

Reuse an existing principal contract if AUTH-002 identifies one.

Otherwise add the minimum shared authenticated principal representation needed to carry stable Hidra identity and existing authorization information.

No provider-specific business model duplication.

Validation:

```bash
mvn -q test
```

---

### AUTH-007 — Normalize existing OIDC authentication

Commit:

```text
refactor(authentication): normalize existing oidc authentication
```

Preserve current OIDC browser/bootstrap behavior.

Add only missing mapping from validated external OIDC identity to:

```text
IdentityProvider / ExternalIdentity
Hidra User
Hidra account state
Hidra authorization
HidraPrincipal
```

Do not collect external provider passwords and do not replace working OIDC configuration unnecessarily.

Validation:

```bash
mvn -q test
```

---

### AUTH-008 — LOCAL credential model and port

Commit:

```text
feat(identity): add local credential model and port
```

Execute only if inventory proves no equivalent persistent credential model exists.

Do not put password hashes on ordinary User DTOs or expose Spring PasswordEncoder in the domain.

Validation:

```bash
mvn -q -DskipTests compile
```

---

### AUTH-009 — LOCAL credential persistence

Commit:

```text
feat(identity): add local credential persistence
```

Requirements:

```text
forward-only Flyway migration
PostgreSQL-compatible schema
JPA entity/repository/adapter aligned with Identity infrastructure style
no modification of released migrations
no hash exposure/logging
```

Validation:

```bash
mvn -q test
```

---

### AUTH-010 — Database-backed LOCAL provider

Commit:

```text
feat(authentication): add database local authentication provider
```

Requirements:

```text
resolve existing Hidra User/provider binding
verify account state
load LOCAL credential
PasswordEncoder.matches(...)
return normalized HidraPrincipal
never query in-memory bootstrap on failed ordinary LOCAL credentials
no token issuance inside provider
```

Validation:

```bash
mvn -q test
```

---

### AUTH-011 — LOCAL authentication outcomes

Commit:

```text
feat(identity): record local authentication outcomes
```

Reuse existing User login counters/lock state and AuthenticationEvent.

Never record the submitted password.

Validation:

```bash
mvn -q test
```

---

### AUTH-012 — LDAP security infrastructure

Commit:

```text
feat(authentication): add ldap security infrastructure
```

Requirements:

```text
add only required Maven LDAP dependencies
externalized URL/base DN/search/filter/bind configuration
TLS/LDAPS production guard
connection/read timeout configuration
no company secrets committed
```

Validation:

```bash
mvn -q test
```

---

### AUTH-013 — LDAP credential verification adapter

Commit:

```text
feat(authentication): add ldap credential verification adapter
```

Implement the approved AD bind/search pattern.

Return a provider-neutral verified directory identity result; do not assign Hidra permissions in the LDAP adapter.

Validation:

```bash
mvn -q test
```

---

### AUTH-014 — LDAP AuthenticationProvider

Commit:

```text
feat(authentication): add ldap authentication provider
```

Flow:

```text
LdapAuthenticationToken
 -> LDAP credential adapter
 -> stable external identity
 -> ExternalIdentity
 -> Hidra User
 -> Hidra account state
 -> existing Hidra authorization
 -> HidraPrincipal
```

No LOCAL fallback and no AD-group authorization shortcut.

Validation:

```bash
mvn -q test
```

---

### AUTH-015 — Unified Hidra access-token issuer

Commit:

```text
feat(authentication): add unified hidra access token issuer
```

Inventory/reuse current JWT components before adding new ones.

Requirements:

```text
one token schema for LOCAL/LDAP/OIDC
stable Hidra user subject
issuer/audience/expiry/JTI
externalized signing material
compatibility with existing resource server
no authorization bypass through provider claims
```

Validation:

```bash
mvn -q test
```

---

### AUTH-016 — Authentication session lifecycle

Commit:

```text
feat(identity): complete authentication session lifecycle
```

Reuse `LoginSession` and `AuthenticationEvent` across all provider paths.

Do not store raw JWTs.

Validation:

```bash
mvn -q test
```

---

### AUTH-017 — Dynamic login endpoint

Commit:

```text
feat(authentication): wire dynamic login endpoint
```

Preserve the existing login/authType API where present.

The controller/application boundary may:

```text
validate request
convert selected authType into Authentication request
authenticate through AuthenticationManager
create/update LoginSession
issue Hidra JWT
return safe principal/token response
```

It must not:

```text
query JPA repositories directly
perform LDAP binds directly
compare password hashes directly
assign roles
implement provider fallback
```

Validation:

```bash
mvn -q test
```

---

### AUTH-018 — OIDC completion to Hidra token

Commit:

```text
feat(authentication): converge oidc completion on hidra token
```

After external OIDC validation, route the mapped HidraPrincipal through the same session/token issuance path as LOCAL and LDAP.

Preserve authorization-code + PKCE security semantics.

Validation:

```bash
mvn -q test
```

---

### AUTH-019 — Standard protected-API bearer authentication

Commit:

```text
refactor(security): standardize protected api bearer authentication
```

Protected APIs should consume the standardized Hidra bearer JWT irrespective of original auth source.

Preserve current permission/interceptor semantics.

Validation:

```bash
mvn -q clean verify
```

---

### AUTH-020 — Retire ordinary in-memory LOCAL authentication

Commit:

```text
refactor(security): retire ordinary in-memory local authentication
```

Precondition: persistent LOCAL provider, unified login endpoint, and JWT path are proven.

Remove `InMemoryUserDetailsManager` as an ordinary user-login authority. Do not remove emergency/bootstrap capability until AUTH-021 exists.

Validation:

```bash
mvn -q clean verify
```

---

### AUTH-021 — Safe LOCAL administrator bootstrap

Commit:

```text
feat(authentication): add safe local administrator bootstrap
```

Requirements:

```text
one-time/idempotent provisioning
persistent normal Identity User + LOCAL credential
no production default password
secret provided externally
cannot silently overwrite existing credential
fully auditable
```

Validation:

```bash
mvn -q test
```

---

### AUTH-022 through AUTH-027 — Security proof tasks

These tasks add dedicated routing, LOCAL, LDAP, OIDC normalization, JWT compatibility, and authorization-ownership tests.

Each task must run the appropriate Maven test command and record real results in this roadmap.

Critical final proof:

```text
same Hidra user authorization semantics regardless of authentication provider
provider failure never falls through to another provider
all successful providers yield the same API token/principal contract
```

---

### AUTH-028 — Deployment runbook

Commit:

```text
docs(authentication): add ldap and provider deployment runbook
```

Document only non-secret operational requirements:

```text
provider enablement
LDAPS hosts/base DN/search filter/username attribute
immutable AD identifier
bind/search strategy
truststore/certificate requirements
OIDC issuer/client/audience/scope configuration
JWT signing/verification configuration
timeouts and health diagnostics
```

Do not commit passwords, client secrets, private certificates, or private keys.

---

### AUTH-029 — Architecture and secret guardrails

Commit:

```text
test(authentication): add authentication architecture and secret guardrails
```

Protect boundaries and credentials with automated tests where practical.

Validation:

```bash
mvn -q clean verify
```

---

### AUTH-030 — Finalize authentication gap closure

Commit:

```text
docs(authentication): finalize authentication gap closure checklist
```

Record executable evidence that:

```text
authType dynamically routes to intended provider
LOCAL authenticates against PostgreSQL-backed credentials
LDAP/AD authenticates through protected LDAP transport
OIDC remains functional and maps to Hidra identity
all providers normalize to HidraPrincipal
Hidra roles/permissions remain authoritative
all successful providers issue/produce the same Hidra JWT contract
ordinary in-memory LOCAL authentication is retired
no provider fallback exists
no secrets are committed
mvn -q clean verify passes
```

Do not mark items complete without evidence.

---

## 20. Explicit non-goals

This roadmap does not automatically implement:

```text
AD group -> Hidra role automatic mapping
JIT account creation without explicit policy
passwordless authentication
MFA
refresh-token rotation
SCADA/SAP machine client-credentials authentication
repository-wide PostgreSQL -> MySQL migration
provider-model cleanup unrelated to runtime authentication gaps
destructive historical migration rewrites
```

These require separate architecture decisions/roadmaps.

---

## 21. Definition of done

The authentication gap is closed when all of the following are true:

```text
1. Existing ProviderType/AuthenticationProtocol concepts are reused rather than replaced without cause.
2. Client-selected authType is converted deterministically into the correct runtime authentication request.
3. One AuthenticationManager/ProviderManager delegates to provider-specific strategies.
4. LOCAL users authenticate with persisted Hidra credentials in PostgreSQL.
5. LDAP/ACTIVE_DIRECTORY users authenticate against AD through LDAP/LDAPS and map to Hidra users.
6. Existing OIDC authentication remains functional and converges on the same Hidra identity contract.
7. LOCAL, LDAP/AD, and OIDC all produce the same normalized HidraPrincipal.
8. Hidra account status is enforced after every provider succeeds.
9. Hidra roles/permissions remain the final authorization authority.
10. All successful provider paths issue/produce the same protected-API JWT contract.
11. No failed provider silently falls back to another provider.
12. InMemoryUserDetailsManager is no longer ordinary LOCAL authentication.
13. Existing correct IdentityProvider, ExternalIdentity, User, LoginSession, AuthenticationEvent, and authorization structures are preserved.
14. Production secrets remain externalized.
15. Routing, LOCAL, LDAP, OIDC, JWT, authorization, and failure tests pass.
16. `mvn -q clean verify` passes before final closure.
```

---

## 22. Next task

Execute only:

```text
AUTH-002 — chore(authentication): inventory existing authentication runtime contracts
```

Do not implement provider tokens, routing, LOCAL credential persistence, LDAP adapters, principal normalization, or token issuance until AUTH-002 has identified exactly what already exists and which gaps are real.
