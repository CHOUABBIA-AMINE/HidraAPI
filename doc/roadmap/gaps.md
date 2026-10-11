# HidraAPI — Module Functional Gaps

## Status

CURRENT — ongoing module review; findings below are not automatically authorized development tasks.

## Register

| ID | Module | Requirement | Finding | Dependencies |
|---|---|---|---|---|
| ID-GAP-001 | identity | Secure first-use activation for locally authenticated users | UNVERIFIED | Identity credential lifecycle; onboarding policy |
| ID-GAP-002 | identity | Station monitoring limited to stations within an authorized organizational unit | PARTIAL / END-TO-END UNVERIFIED | Identity, Organization, Topology, Monitoring, Telemetry |
| ID-GAP-003 | identity | Login using LDAP or Active Directory credentials | IMPLEMENTED IN SOURCE; SITE QUALIFICATION OUTSTANDING | Identity provider configuration, directory availability, linked user |

## Identity

### ID-GAP-001 — First-use activation

**Requirement:** Support a controlled first-use experience for local users, potentially involving a temporary initial credential or a single-use activation code followed by selection of a permanent credential. The precise onboarding option, expiry, delivery and recovery policy are not yet decided.

**Observed evidence:** `LocalCredential.java` models a hashed credential and status. `LocalAuthenticationProvider.java` checks persisted credentials and account state. `LocalAdministratorBootstrapApplicationService.java` creates a persistent bootstrap administrator.

**Gap:** The files reviewed do not demonstrate an ordinary-user first-use activation or mandatory change workflow, including single redemption, expiry, or restriction of normal access until activation. This remains unverified rather than conclusively absent across the entire repository.

**Acceptance:** Synthetic first-use, expired/revoked credentials, duplicate redemption, concurrent use, login restriction before activation, lifecycle audit and provider-specific rules. Do not impose a local password on LDAP-only accounts.

**Disposition:** OPEN — verify full existing source and obtain owner onboarding-policy decision before implementation registration.

### ID-GAP-002 — Organizational station-monitoring access

**Requirement:** A user with a monitoring role may see only stations belonging to the organizational unit or delegated operational scope they are authorized to supervise, within the relevant company.

**Observed evidence:** Identity's `ScopeType.java` includes `ORGANIZATION_UNIT`, `FACILITY` and other scopes. `TopologyScopedAccessContract.java` and `TopologyScopedAccessQueryService.java` check company and facility grants for station-graph revision operations. They do not themselves establish station-monitoring permissions or an organization-unit membership/ownership relationship.

**Gap:** End-to-end enforcement across current user organizational assignments, authoritative station scope, monitoring APIs, telemetry history and subscriptions is not verified. Checking company and facility grants alone must not be mistaken for proof of organizational membership or ownership.

**Acceptance:** Synthetic companies, units, stations and roles; valid scoped access, forbidden cross-company and other-unit stations, direct-ID requests, list filtering, hierarchy policy, reassignment and revocation, history/export/subscription bypass attempts, and fail-closed missing scope evidence.

**Disposition:** OPEN — source-backed scoped primitives exist, but complete monitoring authorization requires cross-module review and evidence.

### ID-GAP-003 — LDAP / Active Directory login

**Requirement:** An authorized user can sign in through configured LDAP/Active Directory credentials, linked to an active Hidra identity.

**Observed evidence:** `IdentityAuthenticationRequestRouter.java` routes LDAP and Active Directory requests. `LdapCredentialVerificationAdapter.java` verifies credentials. `LdapAuthenticationProvider.java` requires a single active directory provider and linked, active, unlocked Hidra user. `IdentityAuthenticationController.java` exposes the direct login endpoint.

**Remaining qualification:** The generic capability is implemented in source; actual directory connectivity, TLS trust, external account linking and customer commissioning have not been demonstrated. This is a SITE qualification item, not a confirmed missing software feature.

**Acceptance:** Credential verification and failure cases, unlinked/disabled users, provider ambiguity, secure connection configuration and representative directory integration acceptance.

**Disposition:** IMPLEMENTED IN SOURCE — SITE UNQUALIFIED.

## Remaining modules

The other 23 business modules have not yet been reviewed through this question-and-answer process. No capability gap or completion claim is made for them. Add a section and numbered issue entries when evidence is reviewed.

## Governance

The authoritative execution register is [ULTIMATE_ROADMAP.md](ULTIMATE_ROADMAP.md). This file tracks review findings only. It does not approve a task, migration, source-file allowlist, release or deployment. Recheck live source before registering a bounded remediation task. PLATFORM synthetic verification and customer SITE qualification remain separate; deferred simulator development is not reopened.

**Evidence baseline:** HidraAPI `main` at `6429b8dc0e449b54db89d520ba14c22ce424db1c`, 2026-10-11.
