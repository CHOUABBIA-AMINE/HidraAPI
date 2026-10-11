# HidraAPI — Module Functional Gaps

## Status

CURRENT — ongoing module review; findings below are not automatically authorized development tasks.

## Register

| ID | Module | Requirement | Finding | Dependencies |
|---|---|---|---|---|
| ID-GAP-001 | identity | Secure first-use activation for locally authenticated users | UNVERIFIED | Identity credential lifecycle; onboarding policy |
| ID-GAP-002 | identity | Station monitoring limited to stations within an authorized organizational unit | PARTIAL / END-TO-END UNVERIFIED | Identity, Organization, Topology, Monitoring, Telemetry |
| ID-GAP-003 | identity | Login using LDAP or Active Directory credentials | IMPLEMENTED IN SOURCE; SITE QUALIFICATION OUTSTANDING | Identity provider configuration, directory availability, linked user |
| ORG-GAP-001 | party / organization | Country of a third-party company and its addresses | IMPLEMENTED IN PARTY; reference-code validation unverified | Party master data, country-reference policy |
| ORG-GAP-002 | organization | Parent organizational unit and hierarchy read | IMPLEMENTED; reorganization history/validation requires qualification | Organization hierarchy, effective dates |
| ORG-GAP-003 | organization | Complete historical occupancy of employee jobs and positions | PARTIAL — assignment timeline query implemented; lifecycle unverified | EmployeeAssignment, Position, effective periods, employee APIs |

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

## Organization — Review 02

### ORG-GAP-001 — Third-party company country

**Question:** Can Hidra register a third-party company's country?

**Evidence:** Party's `Party.java` has mandatory `countryCode` and optional `jurisdictionCode`. `RegisterPartyRequest.java` exposes both. `PartyAddressJpaEntity.java` independently holds mandatory address `countryCode`, plus region, city and postal address.

**Disposition:** IMPLEMENTED in **Party** (not Organization). No duplicate Organization country field is recommended. Country-code catalogue/ISO validation and company-vs-address consistency have not been fully verified and are **qualification questions**, not proven missing features.

**Acceptance:** Synthetic companies in different countries and multiple international addresses; reject invalid/unapproved codes under registered policy; verify country-specific authorization and scope where required.

### ORG-GAP-002 — Parent organization and hierarchy

**Evidence:** `OrganizationUnit.java` includes `parentUnitId`, temporal validity and self-parent rejection. `OrganizationUnitJpaRepository.java` supplies a recursive would-create-cycle test. `OrganizationAdministrationQueryController.java` exposes `GET /api/v1/organization/hierarchy` and `GET /api/v1/organization/units/{id}/children`, backed by `JpaOrganizationAdministrationQueryAdapter.java`.

**Disposition:** IMPLEMENTED for basic parent/child relationships and reading hierarchical trees. A complete reparenting/reorganization workflow, time-versioned parent ancestry, cycle safeguards at every mutation and cross-company isolation are **not established by this review**.

**Acceptance:** Multi-level synthetic trees, orphan/unknown parent, cross-company link rejection, cyclic changes, concurrent reparenting, and as-of-date ancestry only if owner requirements require historical org charts.

### ORG-GAP-003 — History of occupied jobs and positions

**Evidence:** `EmployeeAssignment.java` captures `employeeId`, `organizationUnitId`, `positionId`, `assignmentType`, `validFrom`, `validTo` and `status`. `OrganizationAdministrationQueryController.java` exposes `GET /api/v1/organization/employees/{id}/assignments` and filtered `GET /api/v1/organization/assignments`; its query adapter orders records by `validFrom` descending.

**Gap:** The assignment read timeline exists, but `EmployeeAssignmentApplicationService.java` shows only assignment creation and `EmployeeAssignmentRepositoryPort.java` exposes save and exact read. Closing old appointments, appointment overlap rules, reassignment with effective dating, corrections, and querying all past occupants of a position have **not been verified end-to-end**.

**Expected:** For each employee, authorized users can inspect current and past positions, organizational units and dates; for a position, inspect permitted succession history. Historical facts survive a new assignment, organization reparenting and corrections; enforce explicit simultaneous/acting assignment policy rather than inferring it.

**Acceptance:** Synthetic promotion, transfer, dual/acting role, end-dating, invalid overlap, correction/audit and historical authorization, plus position-occupant history.

**Disposition:** PARTIAL — preservation and retrieval primitives exist; full assignment lifecycle requires deeper verification.

## Remaining modules

Identity and Organization have now been reviewed for the specific questions raised; Party has been partially checked for company country. The other 22 modules have not been reviewed through this question-and-answer process. No capability gap or completion claim is made for them. Add a section and numbered issue entries when evidence is reviewed.

## Governance

The authoritative execution register is [ULTIMATE_ROADMAP.md](ULTIMATE_ROADMAP.md). This file tracks review findings only. It does not approve a task, migration, source-file allowlist, release or deployment. Recheck live source before registering a bounded remediation task. PLATFORM synthetic verification and customer SITE qualification remain separate; deferred simulator development is not reopened.

**Evidence baseline:** HidraAPI `main` at `14cb079832da0781858d5b224b578a73e6c47b7c`, 2026-10-11 (Organization source refresh; original Identity review remains independently qualified as recorded).

### Organization review evidence refresh — 2026-10-11

**Live source baseline:** `14cb079832da0781858d5b224b578a73e6c47b7c`. This is a source inspection, not a new execution task or proof of full runtime qualification.

- **ORG-GAP-001 — IMPLEMENTED (Party ownership):** `src/main/java/dz/sh/hidra/modules/party/domain/model/Party.java` requires a nonblank `countryCode`; `src/main/java/dz/sh/hidra/modules/party/api/rest/request/RegisterPartyRequest.java` accepts it; `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyAddressJpaEntity.java` maps address country independently. Registry-backed country-code validity and company/address cross-field policy remain unverified.
- **ORG-GAP-002 — IMPLEMENTED (basic hierarchy):** `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java` models `parentUnitId` and rejects self-parenting; `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/OrganizationUnitJpaRepository.java` provides recursive cycle detection; `src/main/java/dz/sh/hidra/modules/organization/api/rest/controller/OrganizationAdministrationQueryController.java` and `src/main/java/dz/sh/hidra/modules/organization/infrastructure/query/JpaOrganizationAdministrationQueryAdapter.java` expose and assemble hierarchy/children. Historical ancestry and concurrent reparenting safeguards are not established.
- **ORG-GAP-003 — PARTIAL:** `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAssignment.java` contains position, unit, assignment type and effective dates. The controller/query adapter expose ordered per-employee assignment history and general filtering by employee, unit and status, but not a dedicated position-occupancy query. `src/main/java/dz/sh/hidra/modules/organization/application/service/EmployeeAssignmentApplicationService.java` creates assignments but does not implement an atomic close/transfer/correction lifecycle; `src/main/java/dz/sh/hidra/modules/organization/application/port/out/EmployeeAssignmentRepositoryPort.java` only declares save and lookup by ID. `src/test/java/dz/sh/hidra/modules/organization/semantic/EmployeeAssignmentSemanticRemediationTest.java` covers active-unit eligibility, not complete transfer, overlapping assignment or historical succession behavior.

**Verification limitation:** No local Maven execution, physical database migration replay, or fresh end-to-end API test was performed in this documentation review; source evidence and existing exact-head CI must not be misreported as such. No production, migrations, tests, OpenAPI, or Ultimate Roadmap content is modified.
