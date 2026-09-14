# HidraAPI Authentication Gap-Closure Roadmap — LOCAL + Active Directory

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
| Database prefix | `hidra_identity_*` |
| Author | Abir MEDJERAB |
| CreatedOn | 2025-06-26 |
| UpdatedOn | 2026-09-15 |
| Status | Planned — gap-closure roadmap |
| Execution mode | One roadmap commit code at a time |

---

## 2. Purpose of this roadmap

This roadmap does **not** redesign or replace the Identity model that already exists in HidraAPI.

Its purpose is to close the implementation gap between the current business identity model and the current Spring Security runtime.

The repository already models authentication providers, users, roles, permissions, external identities, login sessions, authentication events, and authorization decisions. Those concepts are retained when they are already correct.

The principal gap is that the runtime security path is not yet wired to those Identity concepts:

```text
CURRENT BUSINESS MODEL

IdentityProvider
  ├── LOCAL
  ├── LDAP
  ├── ACTIVE_DIRECTORY
  ├── ...

User
Role
Permission
ExternalIdentity
LoginSession
AuthenticationEvent

                X
                X  missing runtime integration
                X

CURRENT SPRING SECURITY RUNTIME

basic    -> InMemoryUserDetailsManager bootstrap account
jwt      -> external/resource-server bearer validation
disabled -> permit all
```

The goal is to complete the intended authentication implementation while preserving the existing domain and persistence structures that are suitable.

---

## 3. Frozen architecture decision

Hidra will support two human authentication sources for the target deployment:

```text
LOCAL
ACTIVE_DIRECTORY via LDAP/LDAPS
```

The authentication source answers only:

```text
Who verified this user's credentials?
```

Authorization remains Hidra-owned in all cases:

```text
User
Role
Permission
RolePermissionGrant
UserRoleGrant
UserPermissionGrant
AuthorizationPolicy
organization/resource scopes
```

Active Directory must not become the authority for Hidra roles or permissions.

### 3.1 LOCAL already exists and must be preserved

`ProviderType.LOCAL` is an existing Identity provider type. It is not a new concept introduced by this roadmap.

LOCAL means:

```text
username/password credentials are owned and verified by Hidra
```

The missing part is the runtime credential store and authentication adapter that connects the existing LOCAL provider concept to Spring Security and the Identity application layer.

### 3.2 ACTIVE_DIRECTORY already exists and must be preserved

`ProviderType.ACTIVE_DIRECTORY` is also already present.

The existing `IdentityProvider` model already contains directory-related configuration fields such as:

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

The missing part is an actual LDAP/LDAPS authentication adapter and its application-layer wiring.

### 3.3 Existing LDAP provider value is compatibility-sensitive

`ProviderType.LDAP` currently exists alongside `ACTIVE_DIRECTORY`.

Do **not** remove or rename it automatically.

First determine whether live data, APIs, migrations, tests, or administrative configuration use it. If the repository has no real non-AD LDAP requirement, it may later be deprecated in favor of `ACTIVE_DIRECTORY`; that is a compatibility decision, not an initial implementation task.

### 3.4 Existing external-provider model is not deleted by this roadmap by default

The current provider enum also contains OIDC/OAuth/SAML-oriented values. The target deployment does not use them, but this roadmap must not destroy correctly implemented generic Identity capabilities merely because they are unused in the present installation.

Policy:

```text
unsupported in this deployment != delete from domain
```

Only remove an existing provider type, field, class, table, API, or migration when a dedicated compatibility task proves it is obsolete and safe to retire.

---

## 4. Current repository capabilities to preserve

The following live concepts are considered existing assets and must be reused rather than replaced unless a concrete defect is demonstrated.

### 4.1 Identity business model

Preserve:

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

### 4.2 User security state

The existing `User` model already carries:

```text
status
lastAuthenticatedAt
failedLoginCount
lockedUntil
```

Do not create a second account-state model merely for authentication.

Enhance existing behavior only where required to support successful/failed login accounting.

### 4.3 Directory identity linkage

The existing `ExternalIdentity` model already represents a link from a Hidra user to an external identity and includes fields appropriate for an AD-backed identity, including:

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

Use this model for AD linkage unless implementation analysis identifies a specific incompatibility.

Do not create a duplicate `DirectoryIdentity` aggregate just because the provider is AD.

### 4.4 Login session

The existing `LoginSession` model already records logical session metadata without storing bearer tokens.

Reuse it.

Do not create a second platform-owned session table unless a demonstrated requirement cannot be represented by the current model.

### 4.5 Authentication audit

The existing `AuthenticationEvent` concept must be used for successful and failed authentication events.

Do not create a parallel login-audit subsystem in `platform.security`.

### 4.6 Existing migration

`V20260611_001__create_identity_tables.sql` is an existing released Identity migration.

Never rewrite it to fit the authentication work.

All database changes must be forward-only Flyway migrations.

### 4.7 Existing authorization implementation

Authentication work must not replace or bypass existing Hidra role/permission resolution.

After credentials are validated, authorization must continue through the existing Identity permission model and platform permission enforcement.

---

## 5. Actual gaps to close

The roadmap addresses only gaps that prevent the existing model from functioning as intended.

### GAP-AUTH-01 — LOCAL provider is not wired to persistent credentials

Current runtime Basic authentication uses an in-memory bootstrap user.

Missing:

```text
LOCAL credential persistence
password hash lifecycle
LOCAL credential repository port
LOCAL password verifier adapter
Identity user/provider resolution
```

### GAP-AUTH-02 — Active Directory provider has no LDAP runtime adapter

The domain can represent a directory provider, but no production LDAP/LDAPS authenticator is wired into the login path.

Missing:

```text
Spring LDAP/security LDAP dependency as needed
LDAPS connection configuration
AD username lookup/bind strategy
credential verification
stable AD identity mapping
failure normalization
connection/time-out handling
```

### GAP-AUTH-03 — runtime security modes and Identity provider types are disconnected

Current platform configuration understands:

```text
basic
jwt
disabled
```

Identity understands provider types such as LOCAL and ACTIVE_DIRECTORY.

The system needs one coherent login use case that resolves an existing Hidra user/provider and delegates credential verification to the proper adapter.

### GAP-AUTH-04 — no Hidra-owned login API connects frontend credentials to Identity

The backend needs an explicit authentication boundary for username/password login.

Target initial endpoint:

```text
POST /api/v1/security/auth/login
```

This endpoint should orchestrate existing Identity concepts; it must not create a second user system.

### GAP-AUTH-05 — no completed post-login bearer-token flow owned by Hidra

After either LOCAL or AD authentication succeeds, protected API calls need one consistent credential transport.

The recommended target is a short-lived Hidra-issued bearer JWT validated by HidraAPI.

This does not convert LOCAL or AD into JWT provider types. JWT is the post-authentication API credential.

### GAP-AUTH-06 — bootstrap Basic authentication is temporary infrastructure

`InMemoryUserDetailsManager` is useful for bootstrap/development, but it is not the completed LOCAL authentication mechanism.

It must be phased out only after persistent LOCAL authentication and a safe bootstrap path exist.

### GAP-AUTH-07 — missing end-to-end tests

The repository needs explicit tests proving:

```text
LOCAL username/password authentication
AD username/password authentication
Hidra account-state enforcement
Hidra-owned role/permission behavior
no role import from AD
bearer access after successful authentication
failure behavior and audit
```

---

## 6. Target login behavior

The common login flow should be:

```text
HidraWEB
   |
   | username + password over HTTPS
   v
POST /api/v1/security/auth/login
   |
   v
resolve Hidra User
   |
   v
resolve configured IdentityProvider / identity binding
   |
   +-----------------------------+
   |                             |
   v                             v
LOCAL                         ACTIVE_DIRECTORY
   |                             |
verify Hidra hash              verify with AD via LDAP/LDAPS
   |                             |
   +--------------+--------------+
                  |
                  v
         enforce Hidra User status
                  |
                  v
       record AuthenticationEvent
                  |
                  v
        create/reuse LoginSession
                  |
                  v
       issue Hidra access token
                  |
                  v
     Authorization: Bearer <token>
                  |
                  v
              HidraAPI
                  |
                  v
      existing Hidra authorization
```

---

## 7. Provider resolution rules

### 7.1 Preserve provider-driven identity semantics

Do not introduce a new `AuthenticationSource` enum if the existing Identity provider relationship already provides the necessary information.

First reuse:

```text
IdentityProvider
ExternalIdentity
User
```

Only add a new value object or field when the current model cannot represent a required invariant.

### 7.2 No silent cross-provider fallback

A failed AD authentication must not automatically retry the same username/password against LOCAL.

A failed LOCAL authentication must not automatically try AD.

Provider selection must be deterministic from existing Hidra identity/provider configuration.

### 7.3 Username collision

If the same username could exist in both LOCAL and AD, provider resolution must remain deterministic and fail closed when the identity binding is ambiguous.

Do not resolve ambiguity by trying both password stores.

### 7.4 AD user must resolve to a Hidra User

A valid AD credential does not itself grant Hidra access.

The directory identity must resolve to an existing/allowed Hidra identity according to the current provider/external-identity model.

Default first implementation:

```text
pre-provisioned Hidra user + AD external identity link
```

Automatic just-in-time provisioning is not part of the gap closure unless a separate task is approved.

---

## 8. Authorization invariant

Authentication source must never determine business authority directly.

The following are invalid implementations:

```text
AD group -> Spring ROLE_* -> bypass Hidra permissions
AD group -> direct controller access
LOCAL user -> hard-coded admin role
LDAP DN -> authorization decision
```

The valid flow is:

```text
credential verified
      |
      v
Hidra User resolved
      |
      v
existing Hidra role/permission resolution
      |
      v
request allowed/denied
```

AD groups may be synchronized or displayed if existing features require them, but they do not become authoritative Hidra roles automatically.

Do not delete existing external mapping concepts solely because this deployment does not use them. Disable/unconfigure them for this deployment unless a later compatibility task explicitly retires them.

---

## 9. LOCAL authentication gap

### 9.1 Existing concept

LOCAL already exists as a provider type and remains canonical.

### 9.2 Missing persistence

Current repository evidence does not show a dedicated persistent local-password credential model/repository in the live Identity infrastructure.

Add the minimum Identity-owned credential structure needed to support LOCAL without modifying the `User` aggregate into a password container.

Conceptual fields:

```text
id
userId
passwordHash
credentialStatus
passwordChangedAt
createdAt
updatedAt
```

Additional fields are allowed only when justified by a concrete requirement such as password reset or password history.

### 9.3 Password rules

LOCAL passwords:

```text
must never be stored plaintext
must never be reversibly encrypted for comparison
must never be logged
must never appear in domain events
must never be exposed in DTOs
```

Use Spring Security's `PasswordEncoder` behind a technical adapter.

The domain/application layer should depend on an abstraction such as credential verification, not on BCrypt/Argon2 classes directly.

### 9.4 Existing user lock state

Prefer existing `User.failedLoginCount` and `User.lockedUntil` for account lockout behavior.

Do not create duplicate lock counters in the credential table unless a later concurrency/security analysis proves necessary.

---

## 10. Active Directory authentication gap

### 10.1 Existing concepts

Reuse:

```text
ProviderType.ACTIVE_DIRECTORY
IdentityProvider
ExternalIdentity
User
AuthenticationEvent
LoginSession
```

### 10.2 LDAP is the technical protocol

The implementation may use Spring LDAP/Spring Security LDAP, but LDAP code belongs to technical infrastructure/platform integration, not to the Identity domain.

### 10.3 Production transport

Production-like environments require LDAP over a protected channel.

Preferred target:

```text
LDAPS
```

or an explicitly validated TLS-secured LDAP configuration.

Do not disable TLS certificate verification.

### 10.4 Bind/search strategy

The implementation task must choose the appropriate AD pattern based on company infrastructure:

```text
direct user bind
or
service-account search + user bind
```

Repository code must support configuration without committing company credentials.

### 10.5 Stable identity

When AD exposes a stable immutable identifier, store/use it through existing `ExternalIdentity.externalImmutableId` rather than relying solely on mutable display names.

DN/UPN/sAMAccountName may be retained according to the existing external-identity model and deployment configuration.

### 10.6 No AD password persistence

Hidra must never persist, hash for later reuse, cache, or log the AD password.

It exists only for the duration of the login authentication operation.

---

## 11. Hidra access token gap

### 11.1 Purpose

A successful credential verification should produce one consistent authenticated API transport.

Recommended:

```text
short-lived Hidra-issued JWT bearer token
```

### 11.2 Preserve current JWT infrastructure where useful

The current repository already contains JWT resource-server support and authority conversion.

Do not remove or rewrite it wholesale.

Inventory the existing decoder, claim mapping, route authorization, permission resolution, issuer/audience configuration, and tests first. Reuse compatible components.

### 11.3 Token identity

The bearer token must identify the stable Hidra user, not merely a raw LDAP username.

Minimum token identity intent:

```text
sub = Hidra user ID
iss = Hidra issuer
aud = hidra-api
iat
exp
jti
sid = LoginSession ID where adopted
auth_source = LOCAL | ACTIVE_DIRECTORY (optional informational claim)
```

### 11.4 Authorization claims

Do not make token role claims a replacement for the existing Hidra permission model.

If current JWT authority mapping requires roles/scopes, preserve compatibility only after documenting how stale role changes are handled.

Preferred security invariant:

```text
bearer token proves authenticated Hidra principal
Hidra authorization remains final authority
```

### 11.5 Signing

Production signing material must be externalized.

Do not commit private keys or production HMAC secrets.

Prefer asymmetric signing for production unless an existing accepted Hidra security standard specifies otherwise.

---

## 12. API gap

### 12.1 Login

Add a public credential submission endpoint only after the application authentication use case exists:

```text
POST /api/v1/security/auth/login
```

Request intent:

```json
{
  "username": "...",
  "password": "..."
}
```

The request does not need to expose provider selection when Hidra can resolve the provider deterministically from the existing identity model.

### 12.2 Current user/session

If not already adequately provided by existing Identity APIs, expose a minimal authenticated session/principal endpoint:

```text
GET /api/v1/security/auth/me
```

Do not duplicate the existing Identity permission endpoint.

### 12.3 Logout

Add logout/session-end behavior only to the extent supported by the token/session model:

```text
POST /api/v1/security/auth/logout
```

Do not claim immediate JWT revocation unless session/JTI checks or revocation infrastructure actually enforce it.

---

## 13. Compatibility policy for existing OIDC/external code

This deployment does not require external OIDC authentication.

However, existing generic provider/domain code must not be deleted merely to make the current deployment simpler.

Apply these rules:

```text
1. Preserve existing model/data by default.
2. Disable unused providers through configuration.
3. Remove only runtime paths that conflict with the accepted deployment after frontend compatibility is addressed.
4. Delete domain types/tables only in a separate task with usage and migration evidence.
```

The existing `GET /api/v1/security/oidc` endpoint is therefore a **compatibility review item**, not an automatic deletion item.

If HidraWEB no longer depends on it after LOCAL/AD login is implemented, a later task may deprecate or remove it. If another supported deployment still requires it, leave the capability intact but inactive in this deployment.

---

## 14. Configuration model

Do not replace the whole current security configuration before inventorying what is reused.

The final configuration should distinguish:

```text
API request authentication transport
from
human credential provider configuration
```

Conceptually:

```text
hidra.platform.security.enabled=true
hidra.platform.security.bearer.enabled=true
hidra.platform.security.access-token.*=...

hidra.identity.authentication.local.enabled=true
hidra.identity.authentication.active-directory.enabled=true
hidra.identity.authentication.active-directory.url=ldaps://...
hidra.identity.authentication.active-directory.base-dn=...
hidra.identity.authentication.active-directory.user-search-base=...
hidra.identity.authentication.active-directory.user-search-filter=...
```

Exact names must follow existing repository property conventions discovered during implementation.

Do not rename existing properties solely for stylistic consistency if compatibility can be preserved.

---

## 15. Database strategy

### 15.1 Preserve current tables

Do not drop or rename existing Identity tables as part of initial authentication wiring.

### 15.2 Add only missing LOCAL credential persistence

If live schema confirmation shows no LOCAL credential table, add a forward migration for it.

### 15.3 Preserve provider/external identity tables

Use the existing provider and external-identity persistence for AD.

### 15.4 No destructive migration in gap-closure phase

No task in the core gap-closure sequence may drop:

```text
identity providers
external identities
external role mappings
external permission mappings
login sessions
existing authorization structures
```

A separate cleanup roadmap may be created after production compatibility is proven.

---

## 16. Error and security behavior

### 16.1 Public failure response

Authentication failures should be generic enough to avoid username/provider enumeration.

Example intent:

```text
Invalid username or password.
```

Do not publicly distinguish:

```text
unknown LOCAL user
unknown AD user
wrong AD password
wrong LOCAL password
missing external identity
```

### 16.2 Internal audit

Internal `AuthenticationEvent` may record normalized reason codes without recording passwords or secrets.

### 16.3 Hidra account state

A credential success must still fail authorization/login establishment when the Hidra user is suspended, disabled, or locked according to existing Identity rules.

### 16.4 AD outages

Directory unavailability is not the same as invalid credentials.

The adapter/application layer should distinguish them internally for operational diagnostics while maintaining safe public error behavior.

Do not fall back to LOCAL on AD outage.

---

## 17. Testing strategy

Testing must protect both the new wiring and the existing correct implementation.

Required categories:

```text
LOCAL credential unit tests
LOCAL persistence tests
AD adapter tests using a controlled LDAP test server/container or suitable test fixture
provider resolution tests
account-state tests
authentication-event tests
login-session tests
JWT/token issuer and decoder compatibility tests
permission isolation tests
API login tests
CORS/preflight tests for login and bearer calls
architecture tests
```

Critical scenarios:

```text
LOCAL valid password -> authenticated
LOCAL invalid password -> rejected
LOCAL disabled user -> rejected
LOCAL locked user -> rejected
AD valid password + linked Hidra user -> authenticated
AD invalid password -> rejected
AD valid password + no Hidra link -> rejected
AD valid password + disabled Hidra user -> rejected
AD unavailable -> rejected without LOCAL fallback
AD group membership -> does not implicitly grant Hidra permission
successful login -> AuthenticationEvent persisted
failed login -> AuthenticationEvent persisted appropriately
successful login -> bearer token identifies Hidra User
bearer token -> existing Hidra permissions remain final authority
```

---

## 18. Execution roadmap

This is a gap-closure sequence. Each task must preserve existing correct behavior unless its acceptance criteria explicitly authorize a change.

| Code | Commit message | Purpose | Status |
|---|---|---|---|
| AUTH-001 | `docs(authentication): add ldap and local authentication roadmap` | Establish initial execution memory | Completed |
| AUTH-001A | `docs(authentication): refocus roadmap on authentication gaps` | Correct roadmap to preserve existing Identity implementation and target only missing runtime pieces | Completed |
| AUTH-002 | `chore(authentication): inventory existing identity authentication contracts` | Produce code-level inventory of reusable models, ports, persistence, APIs, properties, JWT components, and actual gaps | Planned |
| AUTH-003 | `feat(identity): add local credential model and port` | Add only the missing LOCAL credential concept/application port if inventory confirms absence | Planned |
| AUTH-004 | `feat(identity): add local credential persistence` | Add forward migration, JPA entity/repository/adapter for LOCAL credential hashes | Planned |
| AUTH-005 | `feat(authentication): connect local credential verification` | Wire PasswordEncoder-backed verification to existing LOCAL IdentityProvider/User resolution | Planned |
| AUTH-006 | `feat(authentication): add active directory ldap adapter` | Implement LDAP/LDAPS credential verification using existing ACTIVE_DIRECTORY provider/external identity model | Planned |
| AUTH-007 | `feat(identity): add authentication orchestration use case` | Resolve existing user/provider, delegate credential verification, enforce account state, and return authenticated Hidra principal | Planned |
| AUTH-008 | `feat(identity): record authentication outcomes` | Wire existing AuthenticationEvent/User login state to success/failure paths | Planned |
| AUTH-009 | `feat(identity): complete login session lifecycle` | Reuse existing LoginSession persistence for authenticated sessions where required | Planned |
| AUTH-010 | `feat(authentication): add hidra access token issuer` | Add/reuse token signing while preserving compatible existing JWT validation and permission behavior | Planned |
| AUTH-011 | `feat(authentication): expose unified login endpoint` | Add `POST /api/v1/security/auth/login` backed by Identity authentication use case | Planned |
| AUTH-012 | `feat(authentication): expose authenticated principal endpoints` | Add only missing `/me` and logout/session-end contracts without duplicating Identity permissions | Planned |
| AUTH-013 | `refactor(security): replace bootstrap basic login with identity local auth` | Remove runtime dependence on in-memory bootstrap Basic only after persistent LOCAL path is proven | Planned |
| AUTH-014 | `feat(authentication): add safe local administrator bootstrap` | Provide controlled first LOCAL administrator provisioning without returning to permanent in-memory auth | Planned |
| AUTH-015 | `test(authentication): cover local authentication` | Unit/integration/API coverage for LOCAL | Planned |
| AUTH-016 | `test(authentication): cover active directory authentication` | LDAP/LDAPS adapter and end-to-end AD coverage | Planned |
| AUTH-017 | `test(authentication): protect hidra authorization ownership` | Prove AD attributes/groups do not bypass Hidra roles/permissions | Planned |
| AUTH-018 | `test(authentication): verify bearer token compatibility` | Verify issued tokens work with current security filters, permission resolution, CORS, and protected routes | Planned |
| AUTH-019 | `docs(authentication): add active directory deployment runbook` | Document required non-secret AD deployment inputs and TLS requirements | Planned |
| AUTH-020 | `chore(authentication): review unused external provider runtime` | Determine whether current OIDC/external runtime should remain inactive, be deprecated, or be removed; no automatic domain deletion | Planned |
| AUTH-021 | `test(authentication): add architecture and secret guardrails` | Protect boundaries, provider ownership, and no-secret-in-repository rules | Planned |
| AUTH-022 | `docs(authentication): finalize gap closure checklist` | Record final evidence and remaining optional cleanup | Planned |

---

## 19. Detailed task specifications

### AUTH-002 — Inventory existing authentication contracts

Commit:

```text
chore(authentication): inventory existing identity authentication contracts
```

Purpose:

Create an evidence-based inventory before adding production code.

Must inspect at minimum:

```text
ProviderType
IdentityProvider
ExternalIdentity
User
LoginSession
AuthenticationEvent
Identity application ports/services
Identity persistence adapters
current provider repositories
current security configuration
JWT decoder/converter/configuration
permission resolver/interceptor
current security APIs
application*.properties
Flyway identity schema
HidraWEB login contract only when API compatibility is required
```

Output:

Update this roadmap with a `Live contract inventory` section identifying:

```text
REUSE
EXTEND
MISSING
DEPRECATION CANDIDATE
```

Do not add implementation classes in AUTH-002.

Acceptance:

- LOCAL is documented as an existing provider concept, not newly invented.
- Existing correctly implemented models are marked REUSE.
- Only proven gaps proceed to later tasks.

---

### AUTH-003 — Add LOCAL credential model and port

Commit:

```text
feat(identity): add local credential model and port
```

Execute only if AUTH-002 proves no equivalent live capability exists.

Add the minimum domain/application contracts necessary for a LOCAL password hash.

Must not put password hashes on ordinary User DTOs, expose password hashes through API, import Spring PasswordEncoder into domain, replace `IdentityProvider.LOCAL`, or create a second User aggregate.

Validation:

```bash
mvn -q -DskipTests compile
```

---

### AUTH-004 — Add LOCAL credential persistence

Commit:

```text
feat(identity): add local credential persistence
```

Requirements: forward-only Flyway migration; JPA entity/repository/adapter aligned with Identity infrastructure style; password hashes never logged; no change to old released migration.

Validation:

```bash
mvn -q test
```

---

### AUTH-005 — Connect LOCAL credential verification

Commit:

```text
feat(authentication): connect local credential verification
```

Requirements: technical password verification adapter; reuse existing `PasswordEncoder` bean when suitable; Identity application depends on abstraction, not Spring implementation; LOCAL provider/user resolved through Identity; no in-memory user used for ordinary LOCAL login.

Validation:

```bash
mvn -q test
```

---

### AUTH-006 — Add Active Directory LDAP adapter

Commit:

```text
feat(authentication): add active directory ldap adapter
```

Requirements: add only required Maven LDAP dependency; use existing ACTIVE_DIRECTORY provider configuration/model where possible; environment-based technical connection configuration; support company AD username mapping; no AD password persistence; timeout/unavailable-directory handling; production LDAPS/TLS guard; no authorization from AD groups.

Validation:

```bash
mvn -q test
```

---

### AUTH-007 — Add authentication orchestration use case

Commit:

```text
feat(identity): add authentication orchestration use case
```

Flow:

```text
normalize username
resolve Hidra user/provider binding
reject ambiguous or disabled account
select credential verifier deterministically
verify credentials
update existing user login state
produce authenticated Hidra principal/result
```

No token generation inside the domain. No controller-level provider logic.

Validation:

```bash
mvn -q test
```

---

### AUTH-008 — Record authentication outcomes

Commit:

```text
feat(identity): record authentication outcomes
```

Reuse existing AuthenticationEvent for success, invalid credentials, account blocked/disabled, identity mapping failure, and directory unavailable where appropriate. Never record credentials.

Validation:

```bash
mvn -q test
```

---

### AUTH-009 — Complete login session lifecycle

Commit:

```text
feat(identity): complete login session lifecycle
```

Reuse existing `LoginSession` domain and persistence. Add only missing lifecycle behavior necessary for issued bearer access tokens and logout/session-end metadata. Do not store tokens in the session table.

Validation:

```bash
mvn -q test
```

---

### AUTH-010 — Add Hidra access-token issuer

Commit:

```text
feat(authentication): add hidra access token issuer
```

Before implementation, inventory existing JWT configuration and reuse compatible decoder/converter behavior.

Requirements: short-lived access token; stable Hidra user subject; audience validation compatible with current API; signing material externalized; production fail-closed when signing configuration is absent; no authorization bypass through token claims.

Validation:

```bash
mvn -q test
```

---

### AUTH-011 — Expose unified login endpoint

Commit:

```text
feat(authentication): expose unified login endpoint
```

Endpoint:

```text
POST /api/v1/security/auth/login
```

Controller validates request, invokes Identity authentication use case, invokes technical access-token issuance, and returns a safe login response. It must not query JPA repositories directly, perform LDAP operations, compare password hashes, or assign roles.

Validation:

```bash
mvn -q test
```

---

### AUTH-012 — Principal and logout endpoints

Commit:

```text
feat(authentication): expose authenticated principal endpoints
```

Add only if missing:

```text
GET /api/v1/security/auth/me
POST /api/v1/security/auth/logout
```

Do not duplicate existing Identity permission APIs.

Validation:

```bash
mvn -q test
```

---

### AUTH-013 — Replace bootstrap Basic runtime path

Commit:

```text
refactor(security): replace bootstrap basic login with identity local auth
```

Precondition: AUTH-003 through AUTH-012 are validated.

Only then remove ordinary runtime dependence on `InMemoryUserDetailsManager` for LOCAL authentication. Preserve a safe installation/bootstrap mechanism through AUTH-014 rather than deleting bootstrap access prematurely.

Validation:

```bash
mvn -q clean verify
```

---

### AUTH-014 — Safe LOCAL administrator bootstrap

Commit:

```text
feat(authentication): add safe local administrator bootstrap
```

Requirements: one-time/idempotent bootstrap semantics; no production default password; secret supplied externally; resulting account stored through normal Identity/LOCAL credential persistence; bootstrap cannot silently overwrite an existing credential.

Validation:

```bash
mvn -q test
```

---

### AUTH-015 — LOCAL tests

Commit:

```text
test(authentication): cover local authentication
```

Cover valid password, invalid password, unknown user, wrong provider, locked/disabled/suspended account, failed-login state, successful-login state, and credential hash non-exposure.

---

### AUTH-016 — AD tests

Commit:

```text
test(authentication): cover active directory authentication
```

Cover valid AD credential + mapped Hidra user, invalid credential, unknown directory user, missing Hidra mapping, Hidra-disabled mapped user, directory unavailable, timeout, TLS/configuration guard, and no LOCAL fallback.

---

### AUTH-017 — Authorization ownership tests

Commit:

```text
test(authentication): protect hidra authorization ownership
```

Must prove AD authentication success does not grant permission by itself, AD group membership does not bypass Hidra permission resolver, LOCAL and AD users with same Hidra grants receive equivalent authorization behavior, and 403 remains a Hidra authorization result.

---

### AUTH-018 — Bearer compatibility tests

Commit:

```text
test(authentication): verify bearer token compatibility
```

Verify login-issued token accepted by current SecurityFilterChain, principal resolution, permissions route, identity permission API, 401 on invalid/expired token, 403 on missing Hidra permission, and CORS preflight/Authorization header behavior.

---

### AUTH-019 — AD deployment runbook

Commit:

```text
docs(authentication): add active directory deployment runbook
```

Document non-secret deployment values: LDAPS hosts, base DN, user search base, username attribute/filter, immutable ID attribute, bind/search strategy, truststore/certificate requirements, timeouts, and health diagnostics. Do not commit real passwords, bind-account secrets, private certificates, or production host secrets.

---

### AUTH-020 — Review unused external-provider runtime

Commit:

```text
chore(authentication): review unused external provider runtime
```

This is a review task, not an automatic deletion task. Classify current OIDC/OAuth/SAML-related code as KEEP, DISABLE, DEPRECATE, or REMOVE. Any destructive removal requires a later explicit roadmap task.

---

### AUTH-021 — Architecture and secret guardrails

Commit:

```text
test(authentication): add architecture and secret guardrails
```

Guard: Identity domain does not import Spring LDAP/Security implementation; platform does not own User/Role/Permission business model; controllers do not use repositories directly; password hash never appears in ordinary DTOs; no committed passwords/private keys; AD groups do not become direct Spring authorities unless explicitly mediated by Hidra policy.

Validation:

```bash
mvn -q clean verify
```

---

### AUTH-022 — Finalize checklist

Commit:

```text
docs(authentication): finalize gap closure checklist
```

Record real evidence that LOCAL authentication works against persisted Identity users, AD authentication works through LDAP/LDAPS, existing IdentityProvider/ExternalIdentity/User/AuthenticationEvent/LoginSession are reused where applicable, Hidra roles/permissions remain authoritative, in-memory bootstrap is no longer ordinary LOCAL login, issued bearer token works with protected API, no secret material is committed, and `mvn -q clean verify` passes.

Do not mark any item complete without executable evidence.

---

## 20. Explicit non-goals

This gap-closure roadmap does not implement:

```text
external Google login
new OIDC provider integration
new SAML provider integration
AD group -> Hidra role automatic mapping
JIT AD user creation
passwordless authentication
MFA
refresh-token rotation
SCADA/SAP service-account OAuth2 client credentials
full provider-model cleanup
historical migration rewrites
```

These may be separate future roadmaps.

---

## 21. Definition of done

The authentication gap is closed when all of the following are true:

```text
1. ProviderType.LOCAL remains an existing Hidra Identity concept.
2. LOCAL users authenticate with their persisted Hidra username/password credentials.
3. ACTIVE_DIRECTORY users authenticate through LDAP/LDAPS using existing provider/external identity concepts.
4. Neither authentication path bypasses Hidra account status.
5. Neither authentication path owns business roles/permissions.
6. Existing Hidra role/permission resolution remains the final authorization authority.
7. Successful login yields a consistent protected-API credential.
8. Temporary in-memory Basic authentication is no longer the ordinary LOCAL authentication implementation.
9. Existing correct Identity models/tables are preserved unless a later evidence-based cleanup explicitly replaces them.
10. End-to-end tests prove LOCAL, AD, bearer authentication, authorization isolation, and failure behavior.
11. Production configuration contains no repository-stored passwords, bind secrets, or signing private keys.
12. `mvn -q clean verify` passes.
```

---

## 22. Next task

Execute only:

```text
AUTH-002 — chore(authentication): inventory existing identity authentication contracts
```

Do not implement LOCAL credential persistence, LDAP adapters, token issuance, controller changes, or security-mode refactoring until AUTH-002 has identified exactly which live contracts already exist and which gaps are real.
