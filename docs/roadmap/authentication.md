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
| Status | Active — AUTH-009 LOCAL credential persistence completed |
| Execution mode | One roadmap commit code at a time |

---

## 2. Purpose

This roadmap is a **gap-closure plan**, not a replacement of the Identity implementation that already exists in HidraAPI.

The repository already models authentication providers, users, roles, permissions, external identities, login sessions, authentication events, and authorization decisions. Those concepts must be reused when they are correct.

The target is to complete the runtime bridge between the existing Identity model and Spring Security so that an explicitly selected authentication type can dynamically reach the correct authentication strategy.

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

AUTH-002 confirmed that `ProviderType` is the existing provider taxonomy capable of distinguishing `LDAP` from `ACTIVE_DIRECTORY`. `AuthenticationProtocol` remains useful for authentication-event protocol classification but does not contain a distinct `ACTIVE_DIRECTORY` value.

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

AUTH-002 confirmed that HidraAPI currently publishes browser metadata and validates bearer JWTs as a resource server. The browser OIDC authorization-code + PKCE completion is implemented by HidraWEB against the external IdP; HidraAPI does not currently implement an OAuth2 client callback/token-exchange endpoint.

The implementation inventory distinguishes between:

```text
existing OIDC browser/bootstrap metadata
existing external JWT validation
browser-side external OIDC completion in HidraWEB
missing ExternalIdentity -> Hidra User normalization in the authentication path
missing Hidra-issued unified token issuance
```

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

### 4.1 Explicit authentication-type selection is a target contract, not a current live DTO

AUTH-002 confirmed that the baseline repository contains **no live HidraAPI login request DTO/controller and no Java request field named `authType`**. HidraWEB likewise documents no local HidraAPI username/password login endpoint. The current server-wide runtime selector is:

```text
hidra.platform.security.authentication-mode = disabled | basic | jwt
```

Therefore the conceptual request below is a **future target boundary** to be introduced deliberately; it is not an existing API contract that may be assumed or duplicated:

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

When the direct-login boundary is introduced, it must reuse the existing provider taxonomy rather than inventing a second enum. `ProviderType` is the leading reuse candidate because it already distinguishes `LOCAL`, `LDAP`, `ACTIVE_DIRECTORY`, and `OIDC`.

OIDC is different: Hidra must **not** collect an external provider password. OIDC selection initiates or converges from the existing authorization-code/OIDC flow, and the validated external result is then normalized into the same Hidra principal/token pipeline.

### 4.2 Runtime routing uses Spring Security provider delegation

The target runtime pattern is:

```text
request provider selection
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

The explicitly selected authentication type determines the credential authority. Authentication failure in that authority fails the login attempt.

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

AUTH-002 found `dz.sh.hidra.platform.security.AuthenticatedPrincipal`, but it is intentionally a lightweight technical view containing only `ActorId`, principal name, and an authenticated flag. It must be preserved for platform current-actor plumbing; it is **not** an equivalent normalized Identity principal for LOCAL/LDAP/OIDC convergence.

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

### GAP-AUTH-01 — explicit provider selection has no live direct-login dispatcher

AUTH-002 confirmed that neither HidraAPI nor the current HidraWEB production contract exposes a direct LOCAL/LDAP login DTO with an `authType` field. HidraAPI currently selects `disabled`, `basic`, or `jwt` server-wide through configuration.

Missing/required:

```text
public direct-login request contract when LOCAL/LDAP is introduced
reuse of ProviderType or another proven existing provider discriminator
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
persistent LOCAL credential model
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
Spring LDAP/Spring Security LDAP dependency
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
OIDC identity -> IdentityProvider / ExternalIdentity mapping
ExternalIdentity -> Hidra User mapping
Hidra account-state enforcement
Hidra role/permission loading
normalized HidraPrincipal result
unified Hidra token/session behavior
```

Current Identity permission resolution can resolve a Spring principal name against Hidra `username` or `id`, but the live external OIDC resource-server path does not normalize `issuer + subject` through `ExternalIdentity` first.

### GAP-AUTH-05 — provider-specific results do not yet share one Hidra-issued token contract

All providers must emit the same protected-API credential contract.

Missing/required:

```text
Hidra JwtEncoder/issuer
stable Hidra subject
issuer/audience/expiry/JTI claims
session linkage
consistent principal reconstruction
compatibility with current JwtDecoder/resource-server configuration
```

The current `JwtDecoder` is a validation capability, not a token issuer.

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
                   read/validate provider selection
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

AUTH-002 confirmed no equivalent custom provider-specific Spring `Authentication` request types are present in production source.

Why use distinct token types:

```text
LocalAuthenticationProvider.supports(LocalAuthenticationToken)
LdapAuthenticationProvider.supports(LdapAuthenticationToken)
Oidc adapter/provider supports OIDC token/result type
```

This makes routing deterministic and prevents provider-order fallback.

### 7.2 Request-to-Authentication conversion

Use a single application/platform boundary responsible for translating the selected provider type into the corresponding Spring Security `Authentication` request.

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

The example is conceptual only. AUTH-002 confirmed that `LoginRequest`/`authType` do not yet exist in the live API.

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

AUTH-002 confirmed that no custom `AuthenticationManager` bean and no custom `AuthenticationProvider` implementation currently exist in production source.

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

Unknown/disabled provider selections fail closed.

Do not reinterpret them as LOCAL or any default provider.

---

## 8. LOCAL authentication migration

### 8.1 Preserve existing LOCAL semantics

`ProviderType.LOCAL` already represents Hidra-owned username/password authentication.

Do not rename it and do not add a second `DATABASE` provider type merely because persistence is being completed.

### 8.2 Add only the missing credential persistence

AUTH-002 confirmed no equivalent LOCAL credential model, JPA entity/repository, Flyway table, or password-hash field in the Identity schema.

Introduce the minimum Identity-owned credential structure only in the later LOCAL credential tasks.

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

AUTH-002 confirmed an existing `PasswordEncoder` bean backed by `BCryptPasswordEncoder` in `HidraSecurityConfiguration`. Reuse or evolve that technical seam rather than creating password encoding inside Identity domain code.

Requirements:

```text
one-way adaptive hash only
no plaintext password persistence
no reversible password encryption
no password in DTOs/events/logs
no production default password
```

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
ProviderType.LDAP
ProviderType.ACTIVE_DIRECTORY
IdentityProvider
ExternalIdentity
User
AuthenticationEvent
LoginSession
```

AUTH-002 confirmed that `ProviderType` explicitly distinguishes LDAP from Active Directory, while `AuthenticationProtocol` groups both under the `LDAP` protocol family. Preserve that distinction unless a later domain decision proves otherwise.

### 9.2 Technical dependency

AUTH-002 confirmed that `pom.xml` includes Spring Security and OAuth2 Resource Server support but no Spring LDAP/Spring Security LDAP dependency.

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

Current browser flow evidenced by HidraWEB:

```text
GET /api/v1/security/oidc
   -> issuer discovery
   -> authorization code + PKCE in browser
   -> /auth/callback in HidraWEB
   -> browser exchanges code at external IdP token endpoint
   -> external access token sent to HidraAPI
   -> HidraAPI validates bearer token as a resource server
```

### 10.3 Preserve existing OIDC contract

Keep `GET /api/v1/security/oidc`.

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

AUTH-002 confirmed that the current Identity administration query maps a Spring principal name to a Hidra user by `username` or `id`; it does not yet perform issuer+subject `ExternalIdentity` normalization in the live authentication path.

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

### 11.3 Existing technical principal is not the normalized Identity principal

`dz.sh.hidra.platform.security.AuthenticatedPrincipal` and `SpringSecurityCurrentSecurityContext` are reusable technical adapters for reading the Spring Security context and resolving a kernel `ActorId`.

They intentionally do not own `User`, `Role`, `Permission`, provider relationships, or external identity mapping. Do not overload platform code with that business meaning.

### 11.4 Do not expose provider-specific principals downstream

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

AUTH-002 confirmed reusable existing pieces:

```text
JwtDecoder via HidraJwtDecoderConfiguration
JwtAuthenticationConverter
HidraJwtGrantedAuthoritiesConverter
issuer/audience validation
SecurityFilterChain bearer support
HidraEffectivePermissionResolver
HidraRouteAuthorizationInterceptor
IdentityEffectivePermissionSourceAdapter
CORS Authorization header handling
```

### 12.3 Token issuer

AUTH-002 found no `JwtEncoder`/Hidra token issuer in production source. Add one only in AUTH-015.

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

### 13.1 Direct LOCAL/LDAP login boundary is missing today

AUTH-002 confirmed there is no live HidraAPI username/password login endpoint, login DTO, or `authType` request field. HidraWEB's frozen production authentication contract explicitly states that no local HidraAPI username/password login exists.

When direct LOCAL/LDAP login is introduced, define one canonical provider-selection boundary and reuse the existing provider taxonomy. Do not create multiple competing login endpoints or provider enums.

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

The exact public field name remains an implementation decision for AUTH-017; `authType` is not frozen as an existing field by AUTH-002.

### 13.2 OIDC initiation

OIDC selection should continue to use the browser/external provider flow, not request an external password.

The existing OIDC contract endpoint remains part of this flow.

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

OIDC completion should converge on the same access-token/principal result when AUTH-018 is implemented.

### 13.4 Current principal/session

Reuse existing principal/identity APIs where sufficient. Current Identity administration APIs already expose `/api/v1/identity/me` and `/api/v1/identity/me/permissions`.

Do not duplicate those endpoints without a proven gap.

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

Current configuration contract discovered in AUTH-002 includes:

```text
hidra.platform.security.authentication-mode = disabled | basic | jwt
hidra.security.bootstrap.username
hidra.security.bootstrap.password
hidra.security.bootstrap.roles
hidra.platform.security.jwt.issuer-uri
hidra.platform.security.jwt.jwk-set-uri
hidra.platform.security.jwt.hmac-secret
hidra.platform.security.jwt.audience
hidra.platform.security.jwt.principal-claim
hidra.platform.security.jwt.roles-claim
hidra.platform.security.jwt.scope-claim
hidra.platform.security.jwt.authority-prefix
hidra.platform.security.oidc.client-id
hidra.platform.security.oidc.scopes
hidra.platform.security.oidc.logout-uri
```

The development profile defaults to `basic`; the common contract defaults to `jwt`. No LDAP-specific runtime properties are present.

Conceptual future configuration:

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

hidra.platform.security.access-token.*=...
```

Exact future property names must preserve compatible repository conventions and remain externalized for secrets.

Do not replace the whole security configuration just to add provider routing.

---

## 15. Database strategy

### 15.1 Database remains PostgreSQL

Authentication work uses the same Hidra transactional database and Flyway pipeline as the rest of HidraAPI.

### 15.2 Preserve current Identity tables

Do not drop or rename existing User, provider, external identity, role/permission, authentication-event, or login-session tables.

### 15.3 Add only missing LOCAL credential storage

AUTH-002 confirmed no persistent LOCAL credential structure exists in the current Identity domain/JPA/Flyway implementation.

Add one only through a new forward-only migration in AUTH-009.

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
LOCAL selection -> only LocalAuthenticationProvider handles request
LDAP selection -> only LDAP provider handles request
OIDC selection -> OIDC flow/adapter handles request
unsupported selection -> fail closed
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

## 17A. AUTH-002 live contract inventory

### 17A.1 Evidence baseline

Inventory executed against HidraAPI `main` at:

```text
acf9d503613a2fc6d415c537b54ea8e72e39a2e5
```

HidraWEB compatibility evidence was inspected on its current `main` authentication specification.

No production Java, schema, configuration, or frontend files were modified by AUTH-002.

### 17A.2 Classification

| Area / artifact | Classification | Live evidence / decision |
|---|---|---|
| `ProviderType` | REUSE | Already owns provider taxonomy including `LOCAL`, `LDAP`, `ACTIVE_DIRECTORY`, `OIDC`; leading discriminator for future provider selection. |
| `AuthenticationProtocol` | REUSE | Existing event/protocol taxonomy; contains `LOCAL`, `LDAP`, `OIDC` but no distinct `ACTIVE_DIRECTORY`, so do not use it to erase provider-type distinction. |
| `User` | REUSE | Already owns status, `lastAuthenticatedAt`, `failedLoginCount`, `lockedUntil`, and lifecycle timestamps. |
| `IdentityProvider` | REUSE | Already owns provider type plus OIDC issuer/endpoints and LDAP directory/search/attribute metadata. |
| `ExternalIdentity` | REUSE | Already links provider identities to Hidra users and carries subject, immutable ID, username/email/display name/DN and login/sync state. |
| `LoginSession` | REUSE | Existing token-free logical session model with user/provider/external-identity linkage, expiry, status, client metadata, correlation ID. |
| `AuthenticationEvent` | REUSE | Existing authentication audit model with provider/user/external identity linkage, protocol, event type and failure reason. |
| Identity JPA/Flyway persistence | REUSE | Existing Identity tables/entities/repositories/adapters cover users, providers, external identities, sessions/events, roles, permissions and grants. Released `V20260611_001__create_identity_tables.sql` remains protected. |
| LOCAL credential persistence | MISSING | No LOCAL credential domain model, JPA entity/repository, Flyway table, or password-hash field was found. |
| `AuthenticatedPrincipal` + `SpringSecurityCurrentSecurityContext` | EXTEND | Reusable platform technical current-actor view, but not a normalized Identity principal and must not become owner of User/Role/Permission business meaning. |
| Identity principal query | EXTEND | `IdentityAdministrationQueryUseCase.principal(...)` / JPA adapter currently resolve Spring principal name by Hidra username or ID. External `issuer + subject -> ExternalIdentity -> User` normalization is missing. |
| Current login request DTO/controller | MISSING | Identity REST request package contains administration/evaluation requests only; no login request/controller exists. |
| Live `authType` request field | MISSING | No Java request field/DTO named `authType` was found. The roadmap's previous JSON examples were conceptual, not live contracts. |
| Current runtime authentication selector | REUSE | `hidra.platform.security.authentication-mode` is the existing server-wide selector with `disabled`, `basic`, `jwt`; preserve during migration but do not confuse it with per-login provider selection. |
| `SecurityFilterChain` / `HidraSecurityConfiguration` | EXTEND | Reuse CORS, stateless policy, public OIDC bootstrap endpoint, Basic bootstrap and JWT resource-server wiring; later add provider-routing capabilities without wholesale replacement. |
| `InMemoryUserDetailsManager` bootstrap | DEPRECATION CANDIDATE | Ordinary Basic bootstrap only; remove from ordinary LOCAL authentication after persisted LOCAL path and safe admin bootstrap are proven. |
| `PasswordEncoder` | REUSE | Existing `BCryptPasswordEncoder` bean is the technical password-hash seam. Keep password encoding outside Identity domain. |
| Custom `AuthenticationManager` / `ProviderManager` composition | MISSING | No custom production `AuthenticationManager` bean found. |
| Custom `AuthenticationProvider` implementations | MISSING | No production custom provider implementations found. |
| Provider-specific Spring `Authentication` request tokens | MISSING | No LOCAL/LDAP/OIDC custom request-token types found. |
| `HidraOidcContractController` | REUSE | Public `GET /api/v1/security/oidc` publishes non-secret JWT/OIDC/PKCE browser metadata and must be preserved. |
| Backend OIDC OAuth2 client/callback/token exchange | MISSING | HidraAPI has resource-server support, not OAuth2-client callback completion. HidraWEB performs discovery, PKCE, callback and token exchange directly with the external IdP. |
| `JwtDecoder` / issuer/audience validation | REUSE | `HidraJwtDecoderConfiguration` validates JWK/issuer/HMAC-backed JWTs and optional audience. |
| `JwtAuthenticationConverter` / `HidraJwtGrantedAuthoritiesConverter` | REUSE | Existing principal/roles/scope claim conversion is active in JWT resource-server mode. |
| Hidra `JwtEncoder` / access-token issuer | MISSING | No production `JwtEncoder`/Hidra token issuer found. |
| `HidraEffectivePermissionResolver` | REUSE | Existing permission resolver combines Spring authorities with Identity-backed effective permissions. |
| `HidraRouteAuthorizationInterceptor` | REUSE | Existing route permission enforcement remains the protected-API authorization boundary. |
| `IdentityEffectivePermissionSourceAdapter` | REUSE | Existing adapter preserves Identity ownership of effective permissions behind platform security extension point. |
| LDAP Maven/runtime support | MISSING | `pom.xml` has Spring Security and OAuth2 Resource Server but no Spring LDAP/Spring Security LDAP dependency; no LDAP runtime properties/configuration/provider are present. |
| Common/profile configuration | REUSE / EXTEND | Preserve current security/JWT/OIDC keys. Common defaults to JWT; development defaults to Basic. LDAP/provider-availability and Hidra token-issuer properties are missing and belong to later tasks. |
| HidraWEB OIDC contract | REUSE | Current production flow is external OIDC authorization-code + PKCE, bearer token held in memory, HidraAPI resource-server validation. |
| HidraWEB LOCAL/LDAP direct-login contract | MISSING | Current frontend specification explicitly says no local HidraAPI username/password login endpoint exists. Dynamic provider-selection UX/API compatibility must be introduced deliberately later. |

### 17A.3 Direct answers required by AUTH-002

1. **Exact live login DTO:** none. No direct-login request DTO exists in HidraAPI production source.
2. **Exact authentication-type field name:** none in a login request. `authType` exists only as a conceptual roadmap name. The live server-wide property is `hidra.platform.security.authentication-mode`.
3. **Enum backing a live auth-type request:** none. For the future per-login selector, `ProviderType` is the best existing reuse candidate because it already distinguishes LOCAL/LDAP/ACTIVE_DIRECTORY/OIDC.
4. **Does LOCAL persistence already exist?** No persistent password credential structure was found.
5. **Does a custom `AuthenticationManager` already exist?** No.
6. **Are custom `AuthenticationProvider` implementations already present?** No.
7. **Is OIDC completing authentication in HidraAPI?** No. HidraAPI publishes browser OIDC metadata and validates bearer JWTs. HidraWEB performs OIDC discovery, PKCE authorization, callback handling, and external IdP token exchange.
8. **Is a `JwtEncoder` already present?** No. A `JwtDecoder` is present.
9. **How is the authenticated Spring principal currently mapped to `User`?** Spring `Authentication.getName()` is passed into Identity queries and matched to `UserJpaEntity.username` or `UserJpaEntity.id`; external issuer+subject mapping through `ExternalIdentity` is not part of the current authentication path.
10. **Which existing classes are reusable?** `ProviderType`, `AuthenticationProtocol`, `User`, `IdentityProvider`, `ExternalIdentity`, `LoginSession`, `AuthenticationEvent`, existing Identity persistence/query adapters, `HidraSecurityConfiguration`, `PasswordEncoder`, OIDC contract controller, JWT decoder/converters, permission resolver/interceptor/source adapter, and technical current-security-context adapters.
11. **What is genuinely missing?** Direct login DTO/controller/provider-selection field, provider-specific Spring authentication tokens, provider router, custom `AuthenticationManager`/providers, persistent LOCAL credentials, LDAP runtime dependency/configuration/verification/provider, normalized Identity principal, external-identity normalization in the OIDC authentication path, and Hidra JWT issuer.
12. **What must not be duplicated?** Provider taxonomy, User account state, IdentityProvider/ExternalIdentity, LoginSession/AuthenticationEvent, role/permission ownership, Identity persistence adapters, OIDC bootstrap contract, JWT decoder/resource-server wiring, and existing permission enforcement.

### 17A.4 Consequences for later roadmap tasks

```text
AUTH-003 may proceed: provider-specific request tokens are genuinely absent.
AUTH-004 must route an explicit provider selector but must not claim an existing public authType DTO.
AUTH-006 must add/normalize Identity-owned principal meaning without moving User/Role/Permission ownership into platform.
AUTH-007 must preserve browser OIDC + resource-server behavior and add only the missing Hidra identity normalization.
AUTH-008/AUTH-009 are justified because LOCAL credential persistence is absent.
AUTH-012 is justified because LDAP dependencies/configuration are absent.
AUTH-015 is justified because JwtEncoder/token issuance is absent.
AUTH-017 will introduce the first direct LOCAL/LDAP login request boundary; it must coordinate with HidraWEB rather than preserve a nonexistent endpoint.
AUTH-018 must decide how successful external OIDC authentication converges on a Hidra-issued token without regressing the current PKCE browser flow.
```

### 17A.5 AUTH-002 validation record

Validation mode required by this task: documentation/source inspection only.

Evidence inspected:

```text
AGENTS.md
docs/roadmap/authentication.md
src/main/java/dz/sh/hidra/platform/configuration/HidraSecurityConfiguration.java
src/main/java/dz/sh/hidra/platform/configuration/HidraJwtDecoderConfiguration.java
src/main/java/dz/sh/hidra/platform/security/HidraOidcContractController.java
src/main/java/dz/sh/hidra/platform/security/AuthenticatedPrincipal.java
src/main/java/dz/sh/hidra/platform/security/SpringSecurityCurrentSecurityContext.java
src/main/java/dz/sh/hidra/platform/security/HidraEffectivePermissionResolver.java
src/main/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptor.java
src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityEffectivePermissionSourceAdapter.java
src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java
src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java
src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java
src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java
src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalIdentity.java
src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java
src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java
src/main/java/dz/sh/hidra/modules/identity/api/rest/request/**
src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/**
src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/**
src/main/resources/db/migration/V20260611_001__create_identity_tables.sql
src/main/resources/application.properties
src/main/resources/application-dev.properties
src/main/resources/application-production.properties
src/main/resources/application-staging.properties
src/main/resources/application-test.properties
pom.xml
HidraWEB/docs/12-Authentication-Specification.md
```

Repository searches also verified absence of production `JwtEncoder`, custom `AuthenticationManager`, custom `AuthenticationProvider`, provider-specific authentication tokens, LOCAL credential/password-hash persistence, LDAP dependencies/runtime code, and a live `LoginRequest`/`authType` DTO.

Result:

```text
PASS — AUTH-002 inventory completed from live source evidence; no production implementation added.
```

---

## 18. Execution roadmap

This is a gap-closure sequence. Only one commit code may be executed per task.

| Code | Commit message | Purpose | Status |
|---|---|---|---|
| AUTH-001 | `docs(authentication): add ldap and local authentication roadmap` | Establish initial execution memory | Completed |
| AUTH-001A | `docs(authentication): refocus roadmap on authentication gaps` | Preserve correct Identity implementation and target runtime gaps | Completed |
| AUTH-001B | `docs(authentication): align roadmap with dynamic provider routing` | Preserve OIDC, make provider-selection runtime routing explicit, add central provider routing and unified JWT plan | Completed |
| AUTH-002 | `chore(authentication): inventory existing authentication runtime contracts` | Classify live provider DTOs, provider-selection contract, OIDC flow, Basic/in-memory path, JWT components, Identity models, persistence, and missing pieces | Completed — live inventory recorded; no production code added |
| AUTH-003 | `feat(authentication): add provider-specific authentication request tokens` | Add/reuse distinct Spring Authentication request types for deterministic LOCAL, LDAP/AD, and OIDC routing | Completed — LOCAL and LDAP/AD request tokens added; OIDC existing JWT/OIDC result path preserved; `mvn -q test` passed in CI run 93 |
| AUTH-004 | `feat(authentication): add authentication request router` | Convert an explicit provider selection into the correct Authentication request without credential verification | Completed — Identity-owned router maps LOCAL and LDAP/AD deterministically, preserves OIDC external flow, rejects unsupported direct providers, and performs no credential verification; `mvn -q test` passed in PR CI run 96 |
| AUTH-005 | `feat(authentication): compose authentication provider manager` | Register provider-specific strategies behind one AuthenticationManager/ProviderManager with no fallback | Completed — central fail-closed ProviderManager composed for provider-specific LOCAL and LDAP request types; `mvn -q test` passed in PR CI run 104 |
| AUTH-006 | `feat(identity): normalize authenticated hidra principal` | Reuse/add one Hidra principal contract shared by LOCAL, LDAP/AD, and OIDC | Completed — Identity-owned HidraPrincipal added with stable Hidra user identity, provider source, optional provider linkage, and Hidra roles/permissions; `mvn -q test` passed in PR CI run 111 |
| AUTH-007 | `refactor(authentication): normalize existing oidc authentication` | Preserve working OIDC flow while mapping successful identities to Hidra User/HidraPrincipal and Hidra authorization | Completed — validated JWT issuer+subject now resolves through active OIDC provider and linked ExternalIdentity to active Hidra User/HidraPrincipal with Hidra-owned effective permissions; `mvn -q test` passed in PR CI run 118 |
| AUTH-008 | `feat(identity): add local credential model and port` | Add the proven-missing LOCAL credential domain/application contract | Completed — Identity-owned LocalCredential and LocalCredentialRepositoryPort added without Spring PasswordEncoder/domain leakage or persistence implementation; `mvn -q -DskipTests compile` passed in PR CI run 125 |
| AUTH-009 | `feat(identity): add local credential persistence` | Add forward Flyway migration plus JPA repository/adapter for LOCAL password hashes | Completed — forward-only PostgreSQL migration plus LocalCredential JPA entity, Spring Data repository, and application-port adapter added; `mvn -q test` passed in PR CI run 131 |
| AUTH-010 | `feat(authentication): add database local authentication provider` | Replace ordinary in-memory LOCAL verification with persistent password verification behind LocalAuthenticationProvider | Planned |
| AUTH-011 | `feat(identity): record local authentication outcomes` | Apply existing User lock/login state and AuthenticationEvent to LOCAL success/failure | Planned |
| AUTH-012 | `feat(authentication): add ldap security infrastructure` | Add required LDAP dependency/configuration/TLS/timeouts without domain coupling | Planned |
| AUTH-013 | `feat(authentication): add ldap credential verification adapter` | Implement AD/LDAP bind/search credential verification | Planned |
| AUTH-014 | `feat(authentication): add ldap authentication provider` | Route LDAP token through directory verification, ExternalIdentity mapping, Hidra account state, and HidraPrincipal | Planned |
| AUTH-015 | `feat(authentication): add unified hidra access token issuer` | Add JwtEncoder and issue one standardized Hidra JWT for all providers | Planned |
| AUTH-016 | `feat(identity): complete authentication session lifecycle` | Reuse LoginSession and AuthenticationEvent for all provider paths | Planned |
| AUTH-017 | `feat(authentication): wire dynamic login endpoint` | Introduce and wire the direct LOCAL/LDAP provider-selection login boundary to router, AuthenticationManager, session, and token issuer | Planned |
| AUTH-018 | `feat(authentication): converge oidc completion on hidra token` | Ensure OIDC completion produces same Hidra principal/session/JWT result as LOCAL/LDAP while preserving current PKCE behavior | Planned |
| AUTH-019 | `refactor(security): standardize protected api bearer authentication` | Make protected APIs consume the unified Hidra JWT while preserving authorization behavior | Planned |
| AUTH-020 | `refactor(security): retire ordinary in-memory local authentication` | Remove InMemoryUserDetailsManager as ordinary LOCAL login only after DB path is proven | Planned |
| AUTH-021 | `feat(authentication): add safe local administrator bootstrap` | Provide controlled persistent LOCAL administrator provisioning without permanent in-memory fallback | Planned |
| AUTH-022 | `test(authentication): cover dynamic provider routing` | Verify provider-selection dispatch, supports contracts, unsupported type, and no fallback | Planned |
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

Status:

```text
Completed — live inventory recorded in section 17A; no production implementation added.
```

Validation: documentation/source inspection only; evidence and result are recorded in section 17A.5.

---

### AUTH-003 — Provider-specific Authentication request tokens

Commit:

```text
feat(authentication): add provider-specific authentication request tokens
```

Precondition: AUTH-002 confirmed equivalent token types do not already exist.

Requirements:

```text
distinct LOCAL request token
distinct LDAP/AD request token
OIDC request/result adapter only where required
credentials erased where Spring contract permits
no business authorization logic
no persistence access
```

Status:

```text
Completed — LocalAuthenticationToken and LdapAuthenticationToken are distinct unauthenticated Spring request types. Both implement CredentialsContainer and erase the credential reference. No duplicate password-style OIDC request token was added because the existing browser OIDC/JWT resource-server path already provides the distinct external authentication result path.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — HidraAPI CI run 93 completed both repository tests and the acceptance `mvn -q test` step successfully for commit 2ef4ea234875205fd145661466a8ad9a607eff2f.
```

---

### AUTH-004 — Authentication request router

Commit:

```text
feat(authentication): add authentication request router
```

Convert an explicit provider selection into the correct Spring `Authentication` request. AUTH-002 confirmed no existing public `authType` DTO; do not invent a second provider taxonomy merely for routing.

Requirements:

```text
LOCAL -> LOCAL token
LDAP/ACTIVE_DIRECTORY -> LDAP token
OIDC -> existing OIDC initiation/completion path
unsupported/disabled type -> fail closed
no credential verification in router
no provider fallback
```

Status:

```text
Completed — IdentityAuthenticationRequestRouter lives in Identity infrastructure so ProviderType remains Identity-owned. LOCAL maps only to LocalAuthenticationToken; LDAP and ACTIVE_DIRECTORY map only to LdapAuthenticationToken. OIDC is deliberately rejected from the direct-credential route with an explicit instruction to use the existing external OIDC flow. All other provider types fail closed. The router performs no credential verification, persistence access, token issuance, or provider fallback.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 96 completed the repository test check and the acceptance `mvn -q test` step successfully for AUTH-004 before roadmap finalization.
```

---

### AUTH-005 — ProviderManager composition

Commit:

```text
feat(authentication): compose authentication provider manager
```

Register the supported provider strategies behind one `AuthenticationManager`/`ProviderManager`.

Each provider must support only the intended Authentication request type.

Do not use provider ordering as the primary provider discriminator.

Status:

```text
Completed — HidraAuthenticationManagerConfiguration now exposes one ProviderManager-backed AuthenticationManager for direct provider authentication. It registers only AuthenticationProvider beans that support exactly one of LocalAuthenticationToken or LdapAuthenticationToken, rejects a provider that claims both request types, and uses a fail-closed parent AuthenticationManager when no matching provider is installed. Existing bootstrap Basic authentication and the external OIDC/JWT path are preserved; no LOCAL, LDAP, or OIDC provider implementation was introduced early.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 104 completed the repository test check and the acceptance `mvn -q test` step successfully for AUTH-005 implementation commit 00cc471a916fea8882ed25f5d2c6297155899b2b.
```

---

### AUTH-006 — Normalized Hidra principal

Commit:

```text
feat(identity): normalize authenticated hidra principal
```

Preserve the existing technical `AuthenticatedPrincipal`/current-security-context adapter, but add the minimum Identity-owned normalized authenticated principal required to carry stable Hidra identity and authorization information across LOCAL, LDAP/AD and OIDC.

No provider-specific business model duplication and no transfer of User/Role/Permission ownership into platform.

Status:

```text
Completed — HidraPrincipal is an Identity-domain record that implements java.security.Principal without Spring dependencies. It carries stable Hidra user ID, Hidra username/display name, ProviderType, optional IdentityProvider linkage, and immutable Hidra-owned role/permission sets. Principal.getName() returns the stable Hidra user ID. The existing platform AuthenticatedPrincipal and current-security-context adapters remain unchanged.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 111 completed the repository test check and the acceptance `mvn -q test` step successfully for AUTH-006 implementation commit 17da3f61c338621bdd9fd641442877241abd239a.
```

---

### AUTH-007 — Normalize existing OIDC authentication

Commit:

```text
refactor(authentication): normalize existing oidc authentication
```

Preserve current browser OIDC authorization-code + PKCE behavior and HidraAPI resource-server validation.

Add only missing mapping from validated external OIDC identity to:

```text
IdentityProvider / ExternalIdentity
Hidra User
Hidra account state
Hidra authorization
HidraPrincipal
```

Do not collect external provider passwords and do not replace working OIDC configuration unnecessarily.

Status:

```text
Completed — the existing resource-server JWT validation remains intact, but its post-validation converter is now Identity-owned. A validated JWT is normalized by issuer + subject through an ACTIVE OIDC IdentityProvider and LINKED ExternalIdentity to an ACTIVE, unlocked Hidra User. The resulting Spring Authentication carries HidraPrincipal with stable Hidra user ID and Hidra-owned effective permissions. External IdP roles/claims are not promoted into Hidra business authorization. Existing browser authorization-code + PKCE behavior and GET /api/v1/security/oidc are unchanged.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 118 completed the repository test check and the acceptance `mvn -q test` step successfully for AUTH-007 implementation commit 93cd247d83e9ed519554df69b6835ab8d1dfe622.
```

---

### AUTH-008 — LOCAL credential model and port

Commit:

```text
feat(identity): add local credential model and port
```

AUTH-002 proved no equivalent persistent credential model exists.

Do not put password hashes on ordinary User DTOs or expose Spring PasswordEncoder in the domain.

Status:

```text
Completed — LocalCredential is an Identity-domain record for the one-way password hash and credential metadata (id, stable Hidra user ID, credential status, password-change/create/update timestamps). LocalCredentialRepositoryPort is an application outbound port exposing save, findById, and findByUserId without Spring Data or PasswordEncoder dependencies. No plaintext password, persistence adapter, migration, authentication provider, or login API was introduced.
```

Validation:

```bash
mvn -q -DskipTests compile
```

Result:

```text
PASS — pull-request CI run 125 completed the acceptance `mvn -q -DskipTests compile` step successfully for AUTH-008 implementation commit 26585e8be08f43acd8dc8504421ae0b0fd1ff898.
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

Status:

```text
Completed — added forward-only V20260915_001 migration for hidra_identity_local_credential with one credential row per Hidra user, password hash storage, credential lifecycle metadata, user foreign key, and supporting indexes. Added LocalCredentialJpaEntity, LocalCredentialJpaRepository, and JpaLocalCredentialRepositoryAdapter implementing the existing LocalCredentialRepositoryPort. No released migration was modified, no plaintext password handling was added, and AUTH-010 authentication-provider behavior was not implemented.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 131 completed both repository and acceptance test checks successfully for AUTH-009 implementation commit 3e9bfa1a539fc545c8f75133978f023d8c01f743.
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

AUTH-002 confirmed that decoder/resource-server support exists and `JwtEncoder`/Hidra token issuance is missing.

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

AUTH-002 confirmed no current direct LOCAL/LDAP login DTO or endpoint exists. Introduce the first canonical direct-login boundary here and coordinate its provider-selection contract with HidraWEB; do not claim compatibility with a nonexistent endpoint.

The controller/application boundary may:

```text
validate request
convert selected provider type into Authentication request
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

After external OIDC validation, route the mapped HidraPrincipal through the same session/token issuance path as LOCAL and LDAP while preserving the current authorization-code + PKCE browser security semantics.

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
explicit provider selection dynamically routes to intended provider
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
2. An explicit per-login provider selection is converted deterministically into the correct runtime authentication request.
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
AUTH-010 — feat(authentication): add database local authentication provider
```

AUTH-009 now provides persistent LOCAL credential storage behind the Identity application port. Do not implement AUTH-011 or later tasks during AUTH-010.
