# HidraAPI — Canonical Functional Phases

## Status

CURRENT — owner-authorized initial canonical baseline, verified 2026-10-16. Accountable authority: repository owner; technical ownership follows the named bounded context. Source parent/applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`. TARGET and DEFERRED work is explicitly separated; unresolved acceptance is never implied by a registered task. No current production deployment or SITE acceptance is established.

Baseline source, audit limitations and technical reconciliation are in [ULTIMATE_TASKS.md](ULTIMATE_TASKS.md). This is the initial functional baseline. After adoption, only Implementation Status and Planning Status table cells change; descriptions, objectives, scope, membership, IDs, priority, dependencies and ordering remain frozen unless the owner explicitly authorizes a structural revision.

## HPS-001 — Identity and Authentication

### 1. Description

Authenticate accountable local and enterprise identities and preserve genuine verification provenance.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `identity`. Authorization decisions belong to HPS-002; never treat external groups as Hidra grants.

### 4. Existing Capabilities

LOCAL/LDAP/AD/OIDC authentication, native issuance and bound-operation primitives exist; the opt-in original OIDC receipt has seven passing synthetic cases. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Complete source-claim issuance/binding/reader integration and qualify ordinary-user first-use activation; actual IdP TLS and deployment linkage remain SITE gates. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: No additional cross-phase edge independently established; preserve original bounded prerequisites in ULTIMATE_TASKS.md. Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-011](ULTIMATE_TASKS.md#hapi-011) | add ldap and local authentication roadmap | Establish initial execution memory | P2 | — | VERIFIED | COMPLETED |
| [HAPI-012](ULTIMATE_TASKS.md#hapi-012) | refocus roadmap on authentication gaps | Preserve correct Identity implementation and target runtime gaps | P2 | — | VERIFIED | COMPLETED |
| [HAPI-013](ULTIMATE_TASKS.md#hapi-013) | align roadmap with dynamic provider routing | Preserve OIDC, make provider-selection runtime routing explicit, add central provider routing and unified JWT plan | P2 | — | VERIFIED | COMPLETED |
| [HAPI-014](ULTIMATE_TASKS.md#hapi-014) | inventory existing authentication runtime contracts | Classify live provider DTOs, provider-selection contract, OIDC flow, Basic/in-memory path, JWT components, Identity models, persistence, and missing pieces | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-015](ULTIMATE_TASKS.md#hapi-015) | add provider-specific authentication request tokens | Add/reuse distinct Spring Authentication request types for deterministic LOCAL, LDAP/AD, and OIDC routing | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-016](ULTIMATE_TASKS.md#hapi-016) | add authentication request router | Convert an explicit provider selection into the correct Authentication request without credential verification | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-017](ULTIMATE_TASKS.md#hapi-017) | compose authentication provider manager | Register provider-specific strategies behind one AuthenticationManager/ProviderManager with no fallback | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-018](ULTIMATE_TASKS.md#hapi-018) | normalize authenticated hidra principal | Reuse/add one Hidra principal contract shared by LOCAL, LDAP/AD, and OIDC | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-020](ULTIMATE_TASKS.md#hapi-020) | add local credential model and port | Add the proven-missing LOCAL credential domain/application contract | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-021](ULTIMATE_TASKS.md#hapi-021) | add local credential persistence | Add forward Flyway migration plus JPA repository/adapter for LOCAL password hashes | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-022](ULTIMATE_TASKS.md#hapi-022) | add database local authentication provider | Replace ordinary in-memory LOCAL verification with persistent password verification behind LocalAuthenticationProvider | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-023](ULTIMATE_TASKS.md#hapi-023) | record local authentication outcomes | Apply existing User lock/login state and AuthenticationEvent to LOCAL success/failure | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-024](ULTIMATE_TASKS.md#hapi-024) | add ldap security infrastructure | Add required LDAP dependency/configuration/TLS/timeouts without domain coupling | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-025](ULTIMATE_TASKS.md#hapi-025) | add ldap credential verification adapter | Implement AD/LDAP bind/search credential verification | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-026](ULTIMATE_TASKS.md#hapi-026) | add ldap authentication provider | Route LDAP token through directory verification, ExternalIdentity mapping, Hidra account state, and HidraPrincipal | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-027](ULTIMATE_TASKS.md#hapi-027) | add unified hidra access token issuer | Add JwtEncoder and issue one standardized Hidra JWT for all providers | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-028](ULTIMATE_TASKS.md#hapi-028) | complete authentication session lifecycle | Reuse LoginSession and AuthenticationEvent for all provider paths | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-029](ULTIMATE_TASKS.md#hapi-029) | wire dynamic login endpoint | Introduce and wire the direct LOCAL/LDAP provider-selection login boundary to router, AuthenticationManager, session, and token issuer | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-030](ULTIMATE_TASKS.md#hapi-030) | converge oidc completion on hidra token | Ensure OIDC completion produces same Hidra principal/session/JWT result as LOCAL/LDAP while preserving current PKCE behavior | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-032](ULTIMATE_TASKS.md#hapi-032) | retire ordinary in-memory local authentication | Remove InMemoryUserDetailsManager as ordinary LOCAL login only after DB path is proven | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-033](ULTIMATE_TASKS.md#hapi-033) | add safe local administrator bootstrap | Provide controlled persistent LOCAL administrator provisioning without permanent in-memory fallback | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-034](ULTIMATE_TASKS.md#hapi-034) | cover dynamic provider routing | Verify provider-selection dispatch, supports contracts, unsupported type, and no fallback | P2 | — | VERIFIED | COMPLETED |
| [HAPI-035](ULTIMATE_TASKS.md#hapi-035) | cover local authentication | Unit/integration/API coverage for persisted LOCAL authentication | P2 | — | VERIFIED | COMPLETED |
| [HAPI-036](ULTIMATE_TASKS.md#hapi-036) | cover ldap authentication | LDAP/AD adapter, mapping, outage, TLS, and end-to-end coverage | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-040](ULTIMATE_TASKS.md#hapi-040) | add ldap and provider deployment runbook | Document non-secret provider/TLS/configuration inputs and diagnostics | P2 | — | VERIFIED | COMPLETED |
| [HAPI-041](ULTIMATE_TASKS.md#hapi-041) | add authentication architecture and secret guardrails | Enforce module boundaries, credential secrecy, and provider isolation | P2 | — | VERIFIED | COMPLETED |
| [HAPI-042](ULTIMATE_TASKS.md#hapi-042) | finalize authentication gap closure checklist | Record executable evidence and remaining optional cleanup | P2 | — | VERIFIED | COMPLETED |
| [HAPI-155](ULTIMATE_TASKS.md#hapi-155) | scan identity domain models | Scan all 15 identity model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-187](ULTIMATE_TASKS.md#hapi-187) | remediate semantic review IdentityProvider | remediate semantic review IdentityProvider | P2 | — | VERIFIED | COMPLETED |
| [HAPI-257](ULTIMATE_TASKS.md#hapi-257) | remediate semantic review User | remediate semantic review User | P2 | — | VERIFIED | COMPLETED |
| [HAPI-281](ULTIMATE_TASKS.md#hapi-281) | remediate semantic review LoginSession | remediate semantic review LoginSession | P2 | — | VERIFIED | COMPLETED |
| [HAPI-320](ULTIMATE_TASKS.md#hapi-320) | review identity IdentityProvider | review identity IdentityProvider | P2 | — | VERIFIED | COMPLETED |
| [HAPI-355](ULTIMATE_TASKS.md#hapi-355) | review identity ExternalRoleMapping | review identity ExternalRoleMapping | P2 | — | VERIFIED | COMPLETED |
| [HAPI-385](ULTIMATE_TASKS.md#hapi-385) | review identity User | review identity User | P2 | — | VERIFIED | COMPLETED |
| [HAPI-409](ULTIMATE_TASKS.md#hapi-409) | review identity AuthenticationEvent | review identity AuthenticationEvent | P2 | — | VERIFIED | COMPLETED |
| [HAPI-412](ULTIMATE_TASKS.md#hapi-412) | review identity HidraPrincipal | review identity HidraPrincipal | P2 | — | VERIFIED | COMPLETED |
| [HAPI-413](ULTIMATE_TASKS.md#hapi-413) | review identity LocalCredential | review identity LocalCredential | P2 | — | VERIFIED | COMPLETED |
| [HAPI-414](ULTIMATE_TASKS.md#hapi-414) | review identity LoginSession | review identity LoginSession | P2 | — | VERIFIED | COMPLETED |
| [HAPI-556](ULTIMATE_TASKS.md#hapi-556) | HPR-P25-044 historical bounded obligation | HPR-P25-044 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-557](ULTIMATE_TASKS.md#hapi-557) | HPR-P25-045 historical bounded obligation | HPR-P25-045 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-558](ULTIMATE_TASKS.md#hapi-558) | HPR-P25-046 historical bounded obligation | HPR-P25-046 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-559](ULTIMATE_TASKS.md#hapi-559) | Existing frozen seven-file task | Existing frozen seven-file task | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-560](ULTIMATE_TASKS.md#hapi-560) | HPR-P25-048 historical bounded obligation | HPR-P25-048 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-561](ULTIMATE_TASKS.md#hapi-561) | HPR-P25-049 historical bounded obligation | HPR-P25-049 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-562](ULTIMATE_TASKS.md#hapi-562) | Identity binding, issuance and operation reader | Identity binding, issuance and operation reader | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-563](ULTIMATE_TASKS.md#hapi-563) | add trusted authentication binding foundations | Trusted binding foundations and private verification receipts | P2 | — | VERIFIED | COMPLETED |
| [HAPI-564](ULTIMATE_TASKS.md#hapi-564) | persist immutable authentication bindings | Fresh-schema binding storage and session guards | P2 | — | VERIFIED | COMPLETED |
| [HAPI-565](ULTIMATE_TASKS.md#hapi-565) | complete trusted bound authentication | Real provider verification and atomic bound issuance | P2 | — | VERIFIED | COMPLETED |
| [HAPI-566](ULTIMATE_TASKS.md#hapi-566) | bind protected operations to trusted authentication | Bound-operation reader and integrated B1 acceptance | P2 | — | VERIFIED | COMPLETED |
| [HAPI-660](ULTIMATE_TASKS.md#hapi-660) | isolate authentication web contract | Adapt Spring principal/session details into application-level authentication inputs/results. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-667](ULTIMATE_TASKS.md#hapi-667) | add identity roadmap | Create execution memory | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-668](ULTIMATE_TASKS.md#hapi-668) | add identity package skeleton | Production package boundaries | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-669](ULTIMATE_TASKS.md#hapi-669) | add identity domain value objects | IDs, username, email, codes, names, statuses, references | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-673](ULTIMATE_TASKS.md#hapi-673) | add identity domain exceptions | Domain-specific failures | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-675](ULTIMATE_TASKS.md#hapi-675) | add identity domain policies and services | Assignment/evaluation/SoD policies | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-676](ULTIMATE_TASKS.md#hapi-676) | add application commands and queries | Framework-neutral use-case inputs | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-677](ULTIMATE_TASKS.md#hapi-677) | add application ports and DTOs | Inbound/outbound contracts | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-678](ULTIMATE_TASKS.md#hapi-678) | add application services | Use-case orchestration | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-679](ULTIMATE_TASKS.md#hapi-679) | add persistence entities and repositories | JPA adapters and migration | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-680](ULTIMATE_TASKS.md#hapi-680) | add infrastructure adapters and configuration | Password/event/configuration adapters | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-681](ULTIMATE_TASKS.md#hapi-681) | add REST API contracts and controllers | REST boundary | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-682](ULTIMATE_TASKS.md#hapi-682) | add identity domain tests | Domain/value/policy tests | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-683](ULTIMATE_TASKS.md#hapi-683) | add identity application tests | Application service tests | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-684](ULTIMATE_TASKS.md#hapi-684) | add identity persistence tests | Persistence adapter tests | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-685](ULTIMATE_TASKS.md#hapi-685) | add identity API tests | Controller/API tests | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-686](ULTIMATE_TASKS.md#hapi-686) | add identity architecture guardrail | ArchUnit boundary checks | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-687](ULTIMATE_TASKS.md#hapi-687) | finalize identity checklist | Final execution status | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-688](ULTIMATE_TASKS.md#hapi-688) | Qualify ordinary-user first-use activation | Determine the actual ordinary-user activation boundary and deliver the owner-selected single-use/expiry/access-restriction policy, without imposing local… | P0 | — | NOT_VERIFIABLE | BLOCKED |
| [HAPI-822](ULTIMATE_TASKS.md#hapi-822) | restore identity roadmap execution memory | Recover Identity roadmap history and merge current-state record | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-832](ULTIMATE_TASKS.md#hapi-832) | add identity flyway migration | add identity flyway migration | P2 | — | NOT_VERIFIABLE | COMPLETED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-002 — Authorization and Access Control

### 1. Description

Decide protected actions from current owner-controlled evidence, scoped grants and deterministic revocation fences.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `identity`, `topology`, `party`. Authentication evidence is a prerequisite; existence, counts, exports and historical reads require current authority.

### 4. Existing Capabilities

Route grants, scoped exports, generation fences and bounded Identity-private protected readers exist. Complete multi-owner publication authorization is partial. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Finish paired permissions, Party/Facility participation, common transaction/time fences, CAS publication and adversarial qualification. Prove organization-to-station monitoring isolation. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: No additional cross-phase edge independently established; preserve original bounded prerequisites in ULTIMATE_TASKS.md. Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-019](ULTIMATE_TASKS.md#hapi-019) | normalize existing oidc authentication | Preserve working OIDC flow while mapping successful identities to Hidra User/HidraPrincipal and Hidra authorization | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-031](ULTIMATE_TASKS.md#hapi-031) | standardize protected api bearer authentication | Make protected APIs consume the unified Hidra JWT while preserving authorization behavior | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-037](ULTIMATE_TASKS.md#hapi-037) | cover oidc normalization | Protect existing OIDC behavior and prove Hidra principal/authorization normalization | P2 | — | VERIFIED | COMPLETED |
| [HAPI-038](ULTIMATE_TASKS.md#hapi-038) | verify unified jwt compatibility | Verify all providers produce tokens accepted by current security filters and permission enforcement | P2 | — | VERIFIED | COMPLETED |
| [HAPI-039](ULTIMATE_TASKS.md#hapi-039) | protect hidra authorization ownership | Prove external groups/claims do not bypass Hidra role/permission decisions | P2 | — | VERIFIED | COMPLETED |
| [HAPI-181](ULTIMATE_TASKS.md#hapi-181) | remediate semantic review Role | remediate semantic review Role | P2 | — | VERIFIED | COMPLETED |
| [HAPI-182](ULTIMATE_TASKS.md#hapi-182) | amend Role uniqueness remediation scope | amend Role uniqueness remediation scope | P2 | — | VERIFIED | COMPLETED |
| [HAPI-188](ULTIMATE_TASKS.md#hapi-188) | remediate semantic review Permission | remediate semantic review Permission | P2 | — | VERIFIED | COMPLETED |
| [HAPI-279](ULTIMATE_TASKS.md#hapi-279) | remediate semantic review AuthorizationDecision | remediate semantic review AuthorizationDecision | P2 | — | VERIFIED | COMPLETED |
| [HAPI-280](ULTIMATE_TASKS.md#hapi-280) | remediate semantic review AuthorizationDelegationGrant | remediate semantic review AuthorizationDelegationGrant | P2 | — | VERIFIED | COMPLETED |
| [HAPI-282](ULTIMATE_TASKS.md#hapi-282) | remediate semantic review UserPermissionGrant | remediate semantic review UserPermissionGrant | P2 | — | VERIFIED | COMPLETED |
| [HAPI-283](ULTIMATE_TASKS.md#hapi-283) | remediate semantic review UserRoleGrant | remediate semantic review UserRoleGrant | P2 | — | VERIFIED | COMPLETED |
| [HAPI-317](ULTIMATE_TASKS.md#hapi-317) | review identity Role | review identity Role | P2 | — | VERIFIED | COMPLETED |
| [HAPI-321](ULTIMATE_TASKS.md#hapi-321) | review identity Permission | review identity Permission | P2 | — | VERIFIED | COMPLETED |
| [HAPI-356](ULTIMATE_TASKS.md#hapi-356) | review identity GroupRoleGrant | review identity GroupRoleGrant | P2 | — | VERIFIED | COMPLETED |
| [HAPI-357](ULTIMATE_TASKS.md#hapi-357) | review identity RolePermissionGrant | review identity RolePermissionGrant | P2 | — | VERIFIED | COMPLETED |
| [HAPI-410](ULTIMATE_TASKS.md#hapi-410) | review identity AuthorizationDecision | review identity AuthorizationDecision | P2 | — | VERIFIED | COMPLETED |
| [HAPI-411](ULTIMATE_TASKS.md#hapi-411) | review identity AuthorizationDelegationGrant | review identity AuthorizationDelegationGrant | P2 | — | VERIFIED | COMPLETED |
| [HAPI-415](ULTIMATE_TASKS.md#hapi-415) | review identity UserPermissionGrant | review identity UserPermissionGrant | P2 | — | VERIFIED | COMPLETED |
| [HAPI-416](ULTIMATE_TASKS.md#hapi-416) | review identity UserRoleGrant | review identity UserRoleGrant | P2 | — | VERIFIED | COMPLETED |
| [HAPI-552](ULTIMATE_TASKS.md#hapi-552) | HPR-P25-040 historical bounded obligation | HPR-P25-040 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-553](ULTIMATE_TASKS.md#hapi-553) | HPR-P25-041 historical bounded obligation | HPR-P25-041 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-555](ULTIMATE_TASKS.md#hapi-555) | HPR-P25-043 historical bounded obligation | HPR-P25-043 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-567](ULTIMATE_TASKS.md#hapi-567) | Integrated closure: Revocation, operation time and physical transaction fence | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-051; child tasks own implementation. Revocation, operation time and physical… | P0 | HAPI-585 | PARTIAL | IN_PROGRESS |
| [HAPI-568](ULTIMATE_TASKS.md#hapi-568) | enforce protected transaction lifecycle | enforce protected transaction lifecycle | P2 | — | VERIFIED | COMPLETED |
| [HAPI-569](ULTIMATE_TASKS.md#hapi-569) | compile protected static expression syntax | compile protected static expression syntax | P2 | HAPI-568 | VERIFIED | COMPLETED |
| [HAPI-570](ULTIMATE_TASKS.md#hapi-570) | reduce protected temporal boundaries | reduce protected temporal boundaries | P2 | HAPI-569 | VERIFIED | COMPLETED |
| [HAPI-571](ULTIMATE_TASKS.md#hapi-571) | collect fenced temporal event candidates | collect fenced temporal event candidates | P2 | HAPI-570 | VERIFIED | COMPLETED |
| [HAPI-572](ULTIMATE_TASKS.md#hapi-572) | fence complete authority dependency relations | fence complete authority dependency relations | P2 | HAPI-571 | VERIFIED | COMPLETED |
| [HAPI-573](ULTIMATE_TASKS.md#hapi-573) | contribute protected bound authentication | contribute protected bound authentication | P2 | HAPI-572 | VERIFIED | COMPLETED |
| [HAPI-574](ULTIMATE_TASKS.md#hapi-574) | load fenced authorization evidence | Fresh fenced authorization evidence; Identity | P2 | HAPI-573 | VERIFIED | COMPLETED |
| [HAPI-575](ULTIMATE_TASKS.md#hapi-575) | select operation time fencing proof | Trusted authorization-claim provenance and coverage; Identity | P0 | HAPI-574, HAPI-580 | VERIFIED | COMPLETED |
| [HAPI-576](ULTIMATE_TASKS.md#hapi-576) | define bounded claim coverage semantics | define bounded claim coverage semantics | P2 | HAPI-574 | VERIFIED | COMPLETED |
| [HAPI-577](ULTIMATE_TASKS.md#hapi-577) | preserve claim catalog fail-closed exception | preserve claim catalog fail-closed exception | P2 | HAPI-576 | VERIFIED | COMPLETED |
| [HAPI-578](ULTIMATE_TASKS.md#hapi-578) | project bounded oidc claim coverage | project bounded oidc claim coverage | P2 | HAPI-577 | VERIFIED | COMPLETED |
| [HAPI-579](ULTIMATE_TASKS.md#hapi-579) | project bounded oidc claim coverage | project bounded oidc claim coverage | P2 | HAPI-578 | VERIFIED | COMPLETED |
| [HAPI-580](ULTIMATE_TASKS.md#hapi-580) | capture verified oidc claim receipt | capture verified oidc claim receipt | P2 | HAPI-579 | VERIFIED | COMPLETED |
| [HAPI-581](ULTIMATE_TASKS.md#hapi-581) | Paired protected Identity authorization | Paired protected Identity authorization; Identity | P0 | HAPI-575 | VERIFIED | COMPLETED |
| [HAPI-582](ULTIMATE_TASKS.md#hapi-582) | Party eligibility and Facility ownership participation | Party eligibility and Facility ownership participation; Party/Topology | P0 | HAPI-581 | PARTIAL | BLOCKED |
| [HAPI-583](ULTIMATE_TASKS.md#hapi-583) | Global admission, fences and common database time | Global admission/fences and common time; trusted coordinator with owner contracts, Platform technical mechanics | P0 | HAPI-582 | PARTIAL | BLOCKED |
| [HAPI-584](ULTIMATE_TASKS.md#hapi-584) | Protected internal Topology head and operation boundary | Protected internal Topology operation boundary; Topology | P0 | HAPI-583 | PARTIAL | BLOCKED |
| [HAPI-585](ULTIMATE_TASKS.md#hapi-585) | Integrated adversarial, concurrency and latency qualification | Integrated adversarial/concurrency/latency qualification and parent closure; independent verification | P0 | HAPI-584 | PARTIAL | BLOCKED |
| [HAPI-670](ULTIMATE_TASKS.md#hapi-670) | add permission domain model and policy | Permission model and code policy | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-671](ULTIMATE_TASKS.md#hapi-671) | add role domain model | Role aggregate and permission assignment | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-672](ULTIMATE_TASKS.md#hapi-672) | add user domain model | User aggregate and role assignment | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-674](ULTIMATE_TASKS.md#hapi-674) | add identity domain events | User/role/permission events | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-689](ULTIMATE_TASKS.md#hapi-689) | Qualify organization-scoped station monitoring | Prove station monitoring, history, counts, exports and subscriptions enforce current company and organizational responsibility; absorb compatible implementation… | P0 | — | PARTIAL | BLOCKED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-003 — Organization and Responsibility

### 1. Description

Represent companies, organizational hierarchy, employees, positions and accountable operational responsibility.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `organization`, `party`. Organization owns assignments; Party owns legal/manufacturer identity; Topology owns facilities.

### 4. Existing Capabilities

Party country/address records, hierarchy and employee assignment reads, responsibility lifecycle and operational-scope contracts exist. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Qualify end-dated transfer/correction/overlap and position-occupant history without duplicating Party identity or deleting appointment history. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: No additional cross-phase edge independently established; preserve original bounded prerequisites in ULTIMATE_TASKS.md. Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-050](ULTIMATE_TASKS.md#hapi-050) | remove organization unit type compatibility wrapper | Delete deprecated OrganizationUnitType wrapper and legacy organization bridges | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-052](ULTIMATE_TASKS.md#hapi-052) | complete organization unit type catalog model | Ensure organization unit types are catalog-backed and trilingual | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-079](ULTIMATE_TASKS.md#hapi-079) | convert organization unit type to catalog entity | Convert organization unit type to catalog/reference modeling. | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-162](ULTIMATE_TASKS.md#hapi-162) | scan organization domain models | Scan all 17 organization model files. | P1 | — | VERIFIED | COMPLETED |
| [HAPI-163](ULTIMATE_TASKS.md#hapi-163) | scan party domain models | Scan all 2 party model files. | P1 | — | VERIFIED | COMPLETED |
| [HAPI-177](ULTIMATE_TASKS.md#hapi-177) | remediate semantic review Party | remediate semantic review Party | P1 | — | VERIFIED | COMPLETED |
| [HAPI-199](ULTIMATE_TASKS.md#hapi-199) | remediate semantic review Position | remediate semantic review Position | P1 | — | VERIFIED | COMPLETED |
| [HAPI-200](ULTIMATE_TASKS.md#hapi-200) | remediate semantic review Shift | remediate semantic review Shift | P1 | — | VERIFIED | COMPLETED |
| [HAPI-209](ULTIMATE_TASKS.md#hapi-209) | remediate semantic review ReportingLine | remediate semantic review ReportingLine | P1 | — | VERIFIED | COMPLETED |
| [HAPI-215](ULTIMATE_TASKS.md#hapi-215) | remediate semantic review OrganizationUnit | remediate semantic review OrganizationUnit | P1 | — | VERIFIED | COMPLETED |
| [HAPI-221](ULTIMATE_TASKS.md#hapi-221) | register Facility owner lookup prerequisite | register Facility owner lookup prerequisite | P1 | — | VERIFIED | COMPLETED |
| [HAPI-228](ULTIMATE_TASKS.md#hapi-228) | remediate semantic review PartyRoleAssignment | remediate semantic review PartyRoleAssignment | P1 | — | VERIFIED | COMPLETED |
| [HAPI-267](ULTIMATE_TASKS.md#hapi-267) | remediate semantic review EmployeeAssignment | remediate semantic review EmployeeAssignment | P1 | — | VERIFIED | COMPLETED |
| [HAPI-268](ULTIMATE_TASKS.md#hapi-268) | remediate semantic review OrganizationDelegation | remediate semantic review OrganizationDelegation | P1 | — | VERIFIED | COMPLETED |
| [HAPI-269](ULTIMATE_TASKS.md#hapi-269) | remediate semantic review OrganizationHierarchySnapshot | remediate semantic review OrganizationHierarchySnapshot | P1 | — | VERIFIED | COMPLETED |
| [HAPI-270](ULTIMATE_TASKS.md#hapi-270) | remediate semantic review ShiftAssignment | remediate semantic review ShiftAssignment | P1 | — | VERIFIED | COMPLETED |
| [HAPI-311](ULTIMATE_TASKS.md#hapi-311) | review organization OrganizationUnitType | review organization OrganizationUnitType | P1 | — | VERIFIED | COMPLETED |
| [HAPI-313](ULTIMATE_TASKS.md#hapi-313) | review organization AdministrativeState | review organization AdministrativeState | P1 | — | VERIFIED | COMPLETED |
| [HAPI-314](ULTIMATE_TASKS.md#hapi-314) | review party Party | review party Party | P1 | — | VERIFIED | COMPLETED |
| [HAPI-326](ULTIMATE_TASKS.md#hapi-326) | review organization OperationalScope | review organization OperationalScope | P1 | — | VERIFIED | COMPLETED |
| [HAPI-332](ULTIMATE_TASKS.md#hapi-332) | review organization Position | review organization Position | P1 | — | VERIFIED | COMPLETED |
| [HAPI-333](ULTIMATE_TASKS.md#hapi-333) | review organization Shift | review organization Shift | P1 | — | VERIFIED | COMPLETED |
| [HAPI-340](ULTIMATE_TASKS.md#hapi-340) | review organization OrganizationContactPoint | review organization OrganizationContactPoint | P1 | — | VERIFIED | COMPLETED |
| [HAPI-341](ULTIMATE_TASKS.md#hapi-341) | review organization ReportingLine | review organization ReportingLine | P1 | — | VERIFIED | COMPLETED |
| [HAPI-345](ULTIMATE_TASKS.md#hapi-345) | review organization OrganizationUnit | review organization OrganizationUnit | P1 | — | VERIFIED | COMPLETED |
| [HAPI-346](ULTIMATE_TASKS.md#hapi-346) | review organization AdministrativeDistrict | review organization AdministrativeDistrict | P1 | — | VERIFIED | COMPLETED |
| [HAPI-350](ULTIMATE_TASKS.md#hapi-350) | review organization ResponsibilityAssignment | review organization ResponsibilityAssignment | P1 | — | VERIFIED | COMPLETED |
| [HAPI-359](ULTIMATE_TASKS.md#hapi-359) | review party PartyRoleAssignment | review party PartyRoleAssignment | P1 | — | VERIFIED | COMPLETED |
| [HAPI-363](ULTIMATE_TASKS.md#hapi-363) | review organization AdministrativeLocality | review organization AdministrativeLocality | P1 | — | VERIFIED | COMPLETED |
| [HAPI-375](ULTIMATE_TASKS.md#hapi-375) | review organization Employee | review organization Employee | P1 | — | VERIFIED | COMPLETED |
| [HAPI-396](ULTIMATE_TASKS.md#hapi-396) | review organization EmployeeAddress | review organization EmployeeAddress | P1 | — | VERIFIED | COMPLETED |
| [HAPI-397](ULTIMATE_TASKS.md#hapi-397) | review organization EmployeeAssignment | review organization EmployeeAssignment | P1 | — | VERIFIED | COMPLETED |
| [HAPI-398](ULTIMATE_TASKS.md#hapi-398) | review organization OrganizationDelegation | review organization OrganizationDelegation | P1 | — | VERIFIED | COMPLETED |
| [HAPI-399](ULTIMATE_TASKS.md#hapi-399) | review organization OrganizationHierarchySnapshot | review organization OrganizationHierarchySnapshot | P1 | — | VERIFIED | COMPLETED |
| [HAPI-400](ULTIMATE_TASKS.md#hapi-400) | review organization ShiftAssignment | review organization ShiftAssignment | P1 | — | VERIFIED | COMPLETED |
| [HAPI-551](ULTIMATE_TASKS.md#hapi-551) | HPR-P25-039 historical bounded obligation | HPR-P25-039 historical bounded obligation | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-632](ULTIMATE_TASKS.md#hapi-632) | persist employee birth fields | Add dateOfBirth, birthLocalityId, birthPlaceAr, birthPlaceFr, birthPlaceEn to EmployeeJpaEntity. | P1 | — | VERIFIED | COMPLETED |
| [HAPI-633](ULTIMATE_TASKS.md#hapi-633) | round trip employee birth data | Update both directions of OrganizationPersistenceMapper; stop using a compatibility constructor where it drops canonical state; add round-trip tests. | P1 | — | VERIFIED | COMPLETED |
| [HAPI-634](ULTIMATE_TASKS.md#hapi-634) | align employee personal data contracts | Give every Employee birth field an explicit application/REST read/write policy; update registration or add a dedicated personal-data update use case as supported… | P1 | — | VERIFIED | COMPLETED |
| [HAPI-635](ULTIMATE_TASKS.md#hapi-635) | migrate employee birth data schema | Inspect live Flyway/schema and add only missing immutable schema changes; preserve optional birthplace locality semantics. | P1 | — | VERIFIED | COMPLETED |
| [HAPI-636](ULTIMATE_TASKS.md#hapi-636) | route employee contacts through contact points | Make OrganizationContactPoint the new-write path for employee EMAIL/MOBILE/PHONE; add missing contact-point application use cases. | P1 | — | VERIFIED | COMPLETED |
| [HAPI-638](ULTIMATE_TASKS.md#hapi-638) | derive employee display names at boundaries | Stop accepting caller-supplied display names as authoritative; return derived Arabic/Latin display names. | P1 | — | VERIFIED | COMPLETED |
| [HAPI-639](ULTIMATE_TASKS.md#hapi-639) | retire employee compatibility fields | Remove legacy Employee contact/display Java/schema state only after parity, consumer migration, and rollback evidence. | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-723](ULTIMATE_TASKS.md#hapi-723) | add organization roadmap | Add this roadmap file | P1 | — | VERIFIED | COMPLETED |
| [HAPI-724](ULTIMATE_TASKS.md#hapi-724) | add organization package skeleton | Add production package-info.java files only | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-725](ULTIMATE_TASKS.md#hapi-725) | add organization domain value objects | Add IDs, codes, names, statuses, unit types, reporting types, operational scope types | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-726](ULTIMATE_TASKS.md#hapi-726) | add position domain model | Add position model and catalog | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-727](ULTIMATE_TASKS.md#hapi-727) | add organization unit domain model | Add organization unit aggregate with unit type and operational scope reference | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-728](ULTIMATE_TASKS.md#hapi-728) | add employee domain model | Add employee aggregate, employee assignment, and reporting line model | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-729](ULTIMATE_TASKS.md#hapi-729) | add organization domain exceptions | Add organization-specific exceptions including reporting line exception | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-730](ULTIMATE_TASKS.md#hapi-730) | add organization domain events | Add employee/unit/position/reporting events | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-731](ULTIMATE_TASKS.md#hapi-731) | add organization domain policies and services | Add hierarchy, lifecycle, assignment, and reporting line policies/services | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-732](ULTIMATE_TASKS.md#hapi-732) | add application commands and queries | Add command/query records | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-733](ULTIMATE_TASKS.md#hapi-733) | add application ports and DTOs | Add inbound/outbound ports and DTOs | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-734](ULTIMATE_TASKS.md#hapi-734) | add application services | Add use-case services | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-735](ULTIMATE_TASKS.md#hapi-735) | add persistence entities and repositories | Add JPA persistence layer and migration | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-736](ULTIMATE_TASKS.md#hapi-736) | add infrastructure adapters and configuration | Add no-op event adapter and configuration | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-737](ULTIMATE_TASKS.md#hapi-737) | add REST API contracts and controllers | Add REST requests/responses/controllers/mappers with Swagger | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-738](ULTIMATE_TASKS.md#hapi-738) | add organization domain tests | Add domain/value/policy tests | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-739](ULTIMATE_TASKS.md#hapi-739) | add organization application tests | Add application service tests | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-740](ULTIMATE_TASKS.md#hapi-740) | add organization persistence tests | Add persistence adapter tests | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-741](ULTIMATE_TASKS.md#hapi-741) | add organization API tests | Add controller/API tests | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-742](ULTIMATE_TASKS.md#hapi-742) | add organization architecture guardrail | Add ArchUnit architecture tests | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-743](ULTIMATE_TASKS.md#hapi-743) | finalize organization checklist | Update this roadmap with final status | P1 | — | VERIFIED | COMPLETED |
| [HAPI-744](ULTIMATE_TASKS.md#hapi-744) | approve scope integrity ADR and correction roadmap | approve scope integrity ADR and correction roadmap | P1 | — | VERIFIED | COMPLETED |
| [HAPI-745](ULTIMATE_TASKS.md#hapi-745) | inventory operational scope contracts and legacy tuples | inventory operational scope contracts and legacy tuples | P1 | — | VERIFIED | COMPLETED |
| [HAPI-746](ULTIMATE_TASKS.md#hapi-746) | introduce canonical operational scope reference invariants | introduce canonical operational scope reference invariants | P1 | — | VERIFIED | COMPLETED |
| [HAPI-747](ULTIMATE_TASKS.md#hapi-747) | add scope owner-resolution application ports | add scope owner-resolution application ports | P1 | — | VERIFIED | COMPLETED |
| [HAPI-748](ULTIMATE_TASKS.md#hapi-748) | enforce multi-scope responsibility domain policy | enforce multi-scope responsibility domain policy | P1 | — | VERIFIED | COMPLETED |
| [HAPI-749](ULTIMATE_TASKS.md#hapi-749) | add validated responsibility application use cases | add validated responsibility application use cases | P1 | — | VERIFIED | COMPLETED |
| [HAPI-750](ULTIMATE_TASKS.md#hapi-750) | add backward-compatible scope schema | add backward-compatible scope schema | P1 | — | VERIFIED | COMPLETED |
| [HAPI-751](ULTIMATE_TASKS.md#hapi-751) | align responsibility persistence and reconciliation | align responsibility persistence and reconciliation | P1 | — | VERIFIED | COMPLETED |
| [HAPI-752](ULTIMATE_TASKS.md#hapi-752) | migrate operational scope API contracts | migrate operational scope API contracts | P1 | — | VERIFIED | COMPLETED |
| [HAPI-753](ULTIMATE_TASKS.md#hapi-753) | remove duplicated active scope ownership | remove duplicated active scope ownership | P1 | — | VERIFIED | COMPLETED |
| [HAPI-754](ULTIMATE_TASKS.md#hapi-754) | retire legacy redundant scope columns | retire legacy redundant scope columns | P1 | — | VERIFIED | COMPLETED |
| [HAPI-755](ULTIMATE_TASKS.md#hapi-755) | verify scope integrity end to end | verify scope integrity end to end | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-756](ULTIMATE_TASKS.md#hapi-756) | define embedded multilingual field policy | define embedded multilingual field policy | P1 | — | VERIFIED | COMPLETED |
| [HAPI-757](ULTIMATE_TASKS.md#hapi-757) | inventory multilingual fields and unit-type translations | inventory multilingual fields and unit-type translations | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-758](ULTIMATE_TASKS.md#hapi-758) | add embedded multilingual unit-type schema | add embedded multilingual unit-type schema | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-759](ULTIMATE_TASKS.md#hapi-759) | embed multilingual unit-type fields | embed multilingual unit-type fields | P1 | — | VERIFIED | COMPLETED |
| [HAPI-760](ULTIMATE_TASKS.md#hapi-760) | retire unit-type translation code | retire unit-type translation code | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-761](ULTIMATE_TASKS.md#hapi-761) | retire unit-type translation table | retire unit-type translation table | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-762](ULTIMATE_TASKS.md#hapi-762) | verify embedded multilingual integrity | verify embedded multilingual integrity | P1 | — | VERIFIED | COMPLETED |
| [HAPI-763](ULTIMATE_TASKS.md#hapi-763) | type responsibility assignee discriminator | type responsibility assignee discriminator | P1 | — | VERIFIED | COMPLETED |
| [HAPI-764](ULTIMATE_TASKS.md#hapi-764) | govern reporting line subjects | govern reporting line subjects | P1 | — | VERIFIED | COMPLETED |
| [HAPI-765](ULTIMATE_TASKS.md#hapi-765) | govern contact point targets | govern contact point targets | P1 | — | VERIFIED | COMPLETED |
| [HAPI-766](ULTIMATE_TASKS.md#hapi-766) | use operational scope reference value | use operational scope reference value | P1 | — | VERIFIED | COMPLETED |
| [HAPI-767](ULTIMATE_TASKS.md#hapi-767) | adopt organization code value | adopt organization code value | P1 | — | VERIFIED | COMPLETED |
| [HAPI-768](ULTIMATE_TASKS.md#hapi-768) | enforce internal reference integrity | enforce internal reference integrity | P1 | — | VERIFIED | COMPLETED |
| [HAPI-769](ULTIMATE_TASKS.md#hapi-769) | retire multilingual compatibility columns | retire multilingual compatibility columns | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-770](ULTIMATE_TASKS.md#hapi-770) | enforce typed dependency integrity | enforce typed dependency integrity | P1 | — | VERIFIED | COMPLETED |
| [HAPI-771](ULTIMATE_TASKS.md#hapi-771) | define final employee domain ownership | define final employee domain ownership | P1 | — | VERIFIED | COMPLETED |
| [HAPI-772](ULTIMATE_TASKS.md#hapi-772) | complete employee personal domain model | complete employee personal domain model | P1 | — | VERIFIED | COMPLETED |
| [HAPI-773](ULTIMATE_TASKS.md#hapi-773) | separate employee contact ownership | separate employee contact ownership | P1 | — | VERIFIED | COMPLETED |
| [HAPI-774](ULTIMATE_TASKS.md#hapi-774) | normalize employee display name semantics | normalize employee display name semantics | P1 | — | VERIFIED | COMPLETED |
| [HAPI-775](ULTIMATE_TASKS.md#hapi-775) | harden organization domain invariants | harden organization domain invariants | P1 | — | VERIFIED | COMPLETED |
| [HAPI-776](ULTIMATE_TASKS.md#hapi-776) | clarify organization identifier value semantics | clarify organization identifier value semantics | P1 | — | VERIFIED | COMPLETED |
| [HAPI-777](ULTIMATE_TASKS.md#hapi-777) | verify model and value integrity | verify model and value integrity | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-778](ULTIMATE_TASKS.md#hapi-778) | Complete employee appointment lifecycle and position history | Provide atomic end-date/transfer/correction and position-occupant history under an explicit concurrent/acting-assignment policy, preserving existing appointment… | P1 | — | PARTIAL | REGISTERED_PENDING |
| [HAPI-829](ULTIMATE_TASKS.md#hapi-829) | expose jpa entity constructors to persistence mapper | expose jpa entity constructors to persistence mapper | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-833](ULTIMATE_TASKS.md#hapi-833) | confirm organization flyway migration | confirm organization flyway migration | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-834](ULTIMATE_TASKS.md#hapi-834) | complete organization controller openapi responses | complete organization controller openapi responses | P1 | — | VERIFIED | COMPLETED |
| [HAPI-835](ULTIMATE_TASKS.md#hapi-835) | enforce reporting line self-reporting rule | enforce reporting line self-reporting rule | P1 | — | NOT_VERIFIABLE | COMPLETED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-004 — Physical Network Topology

### 1. Description

Maintain authoritative physical nodes, segments, branches, station incidence and governed network revisions.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `topology`. A map is a representation of stored facts; it is not a full engineering authoring contract.

### 4. Existing Capabilities

Pipeline-system/facility creation, map reads and immutable internal physical/station graph revision stores exist. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Deliver company/network drafts, physical graph editing and protected publication/history APIs after owner-fence acceptance. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: HPS-002, HPS-005, HPS-006, HPS-020 Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-048](ULTIMATE_TASKS.md#hapi-048) | inventory deprecated and transitional files | Create the verified deletion inventory before code changes | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-049](ULTIMATE_TASKS.md#hapi-049) | remove product type compatibility wrapper | Delete deprecated ProductType wrapper and legacy topology bridges | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-051](ULTIMATE_TASKS.md#hapi-051) | complete product type catalog model | Ensure product types are catalog-backed and trilingual | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-054](ULTIMATE_TASKS.md#hapi-054) | standardize pipeline multilingual labels | Replace single-language pipeline API labels with trilingual fields | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-066](ULTIMATE_TASKS.md#hapi-066) | realign topology roadmap with repository reality | Realign topology docs with actual TOP sequence. | P1 | — | VERIFIED | COMPLETED |
| [HAPI-068](ULTIMATE_TASKS.md#hapi-068) | add topology type catalog tables | Add topology catalog and translation tables in V003. | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-069](ULTIMATE_TASKS.md#hapi-069) | add catalog domain model and references | Add topology catalog domain model and type references. | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-070](ULTIMATE_TASKS.md#hapi-070) | add topology catalog application services | Add catalog DTOs, queries, ports, and application service. | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-071](ULTIMATE_TASKS.md#hapi-071) | add topology catalog persistence adapters | Add catalog persistence adapter and configuration wiring. | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-072](ULTIMATE_TASKS.md#hapi-072) | replace enum usage in domain assets with type references | Refactor topology domain/application asset contracts to use references. | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-073](ULTIMATE_TASKS.md#hapi-073) | migrate topology assets to catalog foreign keys | Add and backfill topology catalog FK columns in V004. | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-074](ULTIMATE_TASKS.md#hapi-074) | update topology persistence mapping to catalog foreign keys | Use catalog FK ids in topology persistence. | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-075](ULTIMATE_TASKS.md#hapi-075) | update topology REST contracts for catalog types and localization | REST uses typeCode and localized type responses. | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-076](ULTIMATE_TASKS.md#hapi-076) | remove topology enum-style type constraints | Remove old topology type varchar columns/checks in V005. | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-077](ULTIMATE_TASKS.md#hapi-077) | repair unstable topology tests | Stabilize topology fixtures and tests after catalog conversion. | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-080](ULTIMATE_TASKS.md#hapi-080) | update topology roadmap after catalog refactor | Update topology roadmap/checklist after catalog refactor. | P1 | — | VERIFIED | COMPLETED |
| [HAPI-082](ULTIMATE_TASKS.md#hapi-082) | remove remaining business taxonomy enum declarations | Replace remaining forbidden enum declarations with compatibility classes. | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-083](ULTIMATE_TASKS.md#hapi-083) | record COR-019 and corrected baseline validation path | Record COR-019/COR-020 and add transition constructor fix. | P1 | — | VERIFIED | COMPLETED |
| [HAPI-169](ULTIMATE_TASKS.md#hapi-169) | scan topology domain models | Scan all 5 topology model files. | P1 | — | VERIFIED | COMPLETED |
| [HAPI-201](ULTIMATE_TASKS.md#hapi-201) | remediate semantic review PipelineSystem | remediate semantic review PipelineSystem | P1 | — | VERIFIED | COMPLETED |
| [HAPI-213](ULTIMATE_TASKS.md#hapi-213) | remediate semantic review TopologyConnection | remediate semantic review TopologyConnection | P1 | — | VERIFIED | COMPLETED |
| [HAPI-220](ULTIMATE_TASKS.md#hapi-220) | remediate semantic review Facility | remediate semantic review Facility | P1 | — | VERIFIED | COMPLETED |
| [HAPI-229](ULTIMATE_TASKS.md#hapi-229) | remediate semantic review Pipeline | remediate semantic review Pipeline | P1 | — | VERIFIED | COMPLETED |
| [HAPI-230](ULTIMATE_TASKS.md#hapi-230) | register Pipeline catalog prerequisite | register Pipeline catalog prerequisite | P1 | — | VERIFIED | COMPLETED |
| [HAPI-334](ULTIMATE_TASKS.md#hapi-334) | review topology PipelineSystem | review topology PipelineSystem | P1 | — | VERIFIED | COMPLETED |
| [HAPI-344](ULTIMATE_TASKS.md#hapi-344) | review topology TopologyConnection | review topology TopologyConnection | P1 | — | VERIFIED | COMPLETED |
| [HAPI-351](ULTIMATE_TASKS.md#hapi-351) | review topology Facility | review topology Facility | P1 | — | VERIFIED | COMPLETED |
| [HAPI-360](ULTIMATE_TASKS.md#hapi-360) | review topology Pipeline | review topology Pipeline | P1 | — | VERIFIED | COMPLETED |
| [HAPI-509](ULTIMATE_TASKS.md#hapi-509) | expose operational twin visualization | Provide authorized network map/state APIs with product and asset layers, connectivity, measured/estimated overlays, quality/freshness and issue location; verify… | P3 | HAPI-504 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-586](ULTIMATE_TASKS.md#hapi-586) | Integrated closure: six topology/operator delivery packages | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-052; child tasks own implementation. six topology/operator delivery packages | P1 | HAPI-592 | PARTIAL | BLOCKED |
| [HAPI-587](ULTIMATE_TASKS.md#hapi-587) | Company/network-owned engineering drafts | Company/network-owned drafts and bounded create/read/update/validate lifecycle. | P1 | HAPI-567 | NOT_IMPLEMENTED | BLOCKED |
| [HAPI-588](ULTIMATE_TASKS.md#hapi-588) | Physical network and station incidence authoring | Physical network/branch/station/port graph authoring and incidence validation. | P1 | HAPI-587 | NOT_IMPLEMENTED | BLOCKED |
| [HAPI-590](ULTIMATE_TASKS.md#hapi-590) | Public governed revision publication and history | Public protected publish/exact read/current/history/effective/list lifecycle over051. | P1 | HAPI-567, HAPI-588, HAPI-589 | NOT_IMPLEMENTED | BLOCKED |
| [HAPI-592](ULTIMATE_TASKS.md#hapi-592) | Topology operator/API/history qualification | Cross-slice operator/API/security/history qualification and consumer-ready contract. | P1 | HAPI-591 | NOT_IMPLEMENTED | BLOCKED |
| [HAPI-658](ULTIMATE_TASKS.md#hapi-658) | expose operational scope resolution contract | Export the Topology scope-resolution contract consumed by Organization. | P1 | — | VERIFIED | COMPLETED |
| [HAPI-666](ULTIMATE_TASKS.md#hapi-666) | remove residual projection vocabulary | Delete only the HRA-120-classified zero-incoming topology.domain.value.ProjectionType enum. Re-run static incoming-reference search immediately before deletion; if… | P1 | — | IMPLEMENTED | COMPLETED |
| [HAPI-871](ULTIMATE_TASKS.md#hapi-871) | add topology implementation roadmap | add topology implementation roadmap | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-872](ULTIMATE_TASKS.md#hapi-872) | add topology package skeleton | add topology package skeleton | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-873](ULTIMATE_TASKS.md#hapi-873) | add topology domain value objects | add topology domain value objects | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-874](ULTIMATE_TASKS.md#hapi-874) | add topology domain models | add topology domain models | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-875](ULTIMATE_TASKS.md#hapi-875) | add topology domain policies | add topology domain policies | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-876](ULTIMATE_TASKS.md#hapi-876) | add topology domain services | add topology domain services | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-877](ULTIMATE_TASKS.md#hapi-877) | add topology application commands and queries | add topology application commands and queries | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-878](ULTIMATE_TASKS.md#hapi-878) | add topology application DTOs | add topology application DTOs | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-879](ULTIMATE_TASKS.md#hapi-879) | add topology application ports | add topology application ports | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-880](ULTIMATE_TASKS.md#hapi-880) | add topology application services | add topology application services | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-881](ULTIMATE_TASKS.md#hapi-881) | add topology persistence entities and repositories | add topology persistence entities and repositories | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-882](ULTIMATE_TASKS.md#hapi-882) | add topology persistence mapper and adapters | add topology persistence mapper and adapters | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-883](ULTIMATE_TASKS.md#hapi-883) | add topology flyway migration | add topology flyway migration | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-884](ULTIMATE_TASKS.md#hapi-884) | add topology REST request DTOs | add topology REST request DTOs | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-885](ULTIMATE_TASKS.md#hapi-885) | add topology REST response DTOs | add topology REST response DTOs | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-886](ULTIMATE_TASKS.md#hapi-886) | add topology REST mapper | add topology REST mapper | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-887](ULTIMATE_TASKS.md#hapi-887) | add topology REST controllers | add topology REST controllers | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-888](ULTIMATE_TASKS.md#hapi-888) | add topology domain tests | add topology domain tests | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-889](ULTIMATE_TASKS.md#hapi-889) | add topology application service tests | add topology application service tests | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-890](ULTIMATE_TASKS.md#hapi-890) | add topology persistence tests | add topology persistence tests | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-891](ULTIMATE_TASKS.md#hapi-891) | add topology REST mapper and controller tests | add topology REST mapper and controller tests | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-892](ULTIMATE_TASKS.md#hapi-892) | add topology application boot smoke test | add topology application boot smoke test | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-893](ULTIMATE_TASKS.md#hapi-893) | finalize topology validation checklist | finalize topology validation checklist | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-894](ULTIMATE_TASKS.md#hapi-894) | TOP-COR-001 historical bounded obligation | TOP-COR-001 historical bounded obligation | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-895](ULTIMATE_TASKS.md#hapi-895) | TOP-COR-002 historical bounded obligation | TOP-COR-002 historical bounded obligation | P1 | — | NOT_VERIFIABLE | COMPLETED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-005 — Geospatial Routing and Elevation

### 1. Description

Preserve surveyed route geometry, intermediate elevation, chainage, pipe sections and explicit spatial reference.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `topology`. No inferred elevations or automatic PostGIS adoption; deployment measurements remain owner supplied.

### 4. Existing Capabilities

Map GeoJSON is public; bounded physical revision DTOs/codecs preserve internal engineering input. Public survey authoring is absent. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Provide typed survey/profile/section lifecycle with CRS, vertical datum, units, precision and source evidence; retain old revisions. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: HPS-004 Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-112](ULTIMATE_TASKS.md#hapi-112) | prove postgis linear reference compatibility | Add a focused proof-of-concept for PostGIS geometry, Hibernate Spatial/JTS and measured-coordinate handling before schema commitment. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-113](ULTIMATE_TASKS.md#hapi-113) | add authoritative pipeline route geometry | Add approved PostGIS route geometry to Topology using actual next migration. | P3 | — | PARTIAL | DEFERRED |
| [HAPI-114](ULTIMATE_TASKS.md#hapi-114) | add pipeline route persistence | Add Topology domain/application/persistence support required by the approved route design. | P3 | — | PARTIAL | DEFERRED |
| [HAPI-115](ULTIMATE_TASKS.md#hapi-115) | define ili spatial extension | Map ILI run/anomaly requirements onto existing inspection run/finding, corrosion, wall-thickness and defect models. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-116](ULTIMATE_TASKS.md#hapi-116) | add ili spatial evidence | Add only missing PostGIS/ILI columns/tables using actual next Flyway migration. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-117](ULTIMATE_TASKS.md#hapi-117) | add ili spatial mapping | Add approved domain/application/persistence mapping for inspection anomalies and geometry/KP reconciliation. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-531](ULTIMATE_TASKS.md#hapi-531) | define revisioned route and elevation profiles | define revisioned route and elevation profiles | P1 | — | VERIFIED | COMPLETED |
| [HAPI-589](ULTIMATE_TASKS.md#hapi-589) | Survey, CRS, profile and pipe-section authoring | Survey/CRS/vertical datum/chainage/elevation/profile and pipe-section authoring. | P1 | HAPI-587 | NOT_IMPLEMENTED | BLOCKED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-006 — Stations and Equipment

### 1. Description

Distinguish installed physical units and station connections from maintainable-asset management.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `topology`, `assets`. Do not merge manufacturer model, installed unit, station configuration and maintainable asset aggregates.

### 4. Existing Capabilities

Topology Equipment and station graph revisions exist; Assets has independent maintainable-asset creation and concurrency-protected update. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Deliver independent installed-unit/model/serial identity, effective station membership and series/parallel incidence with versioned links. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: HPS-002, HPS-004, HPS-007, HPS-023 Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-248](ULTIMATE_TASKS.md#hapi-248) | remediate semantic review Equipment | remediate semantic review Equipment | P1 | — | VERIFIED | COMPLETED |
| [HAPI-373](ULTIMATE_TASKS.md#hapi-373) | review topology Equipment | review topology Equipment | P1 | — | VERIFIED | COMPLETED |
| [HAPI-504](ULTIMATE_TASKS.md#hapi-504) | establish product aware twin network inputs | Extend only demonstrated gaps in product-aware connected topology, fluid-property/composition references, equipment characteristics and versioned operating… | P3 | HAPI-503 | PARTIAL | DEFERRED |
| [HAPI-547](ULTIMATE_TASKS.md#hapi-547) | inventory station graph persistence prerequisites | f1659fe7677050c1a0d80542506db401ccbbdcec, Documentation #226 SUCCESS | P1 | — | VERIFIED | COMPLETED |
| [HAPI-549](ULTIMATE_TASKS.md#hapi-549) | persist immutable station graph revisions | persist immutable station graph revisions | P1 | — | VERIFIED | COMPLETED |
| [HAPI-598](ULTIMATE_TASKS.md#hapi-598) | Integrated closure: five installed-unit/manufacturer-data packages | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-054; child tasks own implementation. five installed-unit/manufacturer-data… | P1 | HAPI-603 | PARTIAL | BLOCKED |
| [HAPI-599](ULTIMATE_TASKS.md#hapi-599) | Independent installed-unit identity and references | Independent installed-unit identity, model/serial/lifecycle and correct source-owner references. | P1 | HAPI-567 | NOT_IMPLEMENTED | BLOCKED |
| [HAPI-600](ULTIMATE_TASKS.md#hapi-600) | Effective station configuration and connectivity | Effective facility/station configuration membership and series/parallel installed incidence. | P1 | HAPI-588, HAPI-599 | NOT_IMPLEMENTED | BLOCKED |
| [HAPI-602](ULTIMATE_TASKS.md#hapi-602) | Versioned installed-unit/characteristic links | Versioned installed-unit to characteristic links, approval and current/historical read. | P1 | HAPI-600, HAPI-601 | NOT_IMPLEMENTED | BLOCKED |
| [HAPI-603](ULTIMATE_TASKS.md#hapi-603) | Installed-equipment consumer qualification | Owner/API/security/compatibility and consumer-ready qualification. | P1 | HAPI-602 | NOT_IMPLEMENTED | BLOCKED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-007 — Manufacturer Engineering Data

### 1. Description

Govern supplied manufacturer curves, tables, limits, axes and certified reference conditions.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `simulation`, `party`, `topology`. Current immutable parameter owner remains Simulation until an explicitly authorized ownership change; no interpolation or solver expansion selected.

### 4. Existing Capabilities

Party manufacturer references and Simulation bounded immutable equipment parameter revision storage exist internally. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Deliver governed supplied-fact authoring, approval/history and exact installed-unit binding; explicitly report unsupported families. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: HPS-006 Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-533](ULTIMATE_TASKS.md#hapi-533) | define manufacturer characteristic provisioning | define manufacturer characteristic provisioning | P1 | — | VERIFIED | COMPLETED |
| [HAPI-601](ULTIMATE_TASKS.md#hapi-601) | Supplied manufacturer maps, tables and limits | Supplied manufacturer maps/tables/limits/axes/units/reference conditions and source evidence. | P1 | HAPI-599 | NOT_IMPLEMENTED | BLOCKED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-008 — Products and Fluid Properties

### 1. Description

Govern product identity, composition, supplied properties, methods and reference bases.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `custody`. Preserve gas-first behavior; metadata for liquids does not establish thermodynamic or hydraulic qualification.

### 4. Existing Capabilities

Custody product references and bounded immutable gas-fluid revision/qualification contracts exist internally. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Deliver company-owned product/property/method drafts, exact composition/basis/units and published revision/history contracts. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: HPS-002 Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-532](ULTIMATE_TASKS.md#hapi-532) | define station and installed unit provisioning | define station and installed unit provisioning | P1 | — | VERIFIED | COMPLETED |
| [HAPI-593](ULTIMATE_TASKS.md#hapi-593) | Integrated closure: four product/composition/method packages | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-053; child tasks own implementation. four product/composition/method packages | P1 | HAPI-597 | PARTIAL | BLOCKED |
| [HAPI-594](ULTIMATE_TASKS.md#hapi-594) | Product identity and metadata drafts | Company-owned product identity/classification and versioned metadata drafts. | P1 | HAPI-567 | NOT_IMPLEMENTED | BLOCKED |
| [HAPI-595](ULTIMATE_TASKS.md#hapi-595) | Supplied composition, properties and methods | Supplied composition/property/reference-basis/units/method applicability and evidence. | P1 | HAPI-594 | NOT_IMPLEMENTED | BLOCKED |
| [HAPI-596](ULTIMATE_TASKS.md#hapi-596) | Product publication, history and owner exports | Governed publish/withdrawal/current/history/effective selection and scalar owner exports. | P1 | HAPI-595 | NOT_IMPLEMENTED | BLOCKED |
| [HAPI-597](ULTIMATE_TASKS.md#hapi-597) | Multi-product ownership/API compatibility qualification | Multi-product owner/API/compatibility qualification and consumer-ready contract. | P1 | HAPI-596 | NOT_IMPLEMENTED | BLOCKED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-009 — Assets and Maintenance

### 1. Description

Maintain assets, conditions, maintenance plans/orders and traceable integrity-driven work.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `assets`. Assets owns maintenance; Topology owns physical incidence; Integrity owns engineering assessment.

### 4. Existing Capabilities

Asset registration/update, condition recording and work-order creation have domain, persistence and focused verification. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Qualify integrated maintenance execution and reuse installed-unit identities through owner contracts; do not claim field work from a recorded order. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: No additional cross-phase edge independently established; preserve original bounded prerequisites in ULTIMATE_TASKS.md. Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-149](ULTIMATE_TASKS.md#hapi-149) | scan assets domain models | Scan all 3 assets model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-233](ULTIMATE_TASKS.md#hapi-233) | remediate semantic review MaintainableAsset | remediate semantic review MaintainableAsset | P2 | — | VERIFIED | COMPLETED |
| [HAPI-234](ULTIMATE_TASKS.md#hapi-234) | register MaintainableAsset owner prerequisites | register MaintainableAsset owner prerequisites | P2 | — | VERIFIED | COMPLETED |
| [HAPI-263](ULTIMATE_TASKS.md#hapi-263) | remediate semantic review MaintenanceWorkOrder | remediate semantic review MaintenanceWorkOrder | P2 | — | VERIFIED | COMPLETED |
| [HAPI-364](ULTIMATE_TASKS.md#hapi-364) | review assets MaintainableAsset | review assets MaintainableAsset | P2 | — | VERIFIED | COMPLETED |
| [HAPI-380](ULTIMATE_TASKS.md#hapi-380) | review assets AssetConditionRecord | review assets AssetConditionRecord | P2 | — | VERIFIED | COMPLETED |
| [HAPI-391](ULTIMATE_TASKS.md#hapi-391) | review assets MaintenanceWorkOrder | review assets MaintenanceWorkOrder | P2 | — | VERIFIED | COMPLETED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-010 — Telemetry and Monitoring

### 1. Description

Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `telemetry`, `monitoring`. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred.

### 4. Existing Capabilities

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: HPS-002, HPS-004, HPS-006, HPS-017, HPS-023 Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-100](ULTIMATE_TASKS.md#hapi-100) | add high velocity ingestion contracts | Add the minimum Telemetry input/output contracts for validated batch ingestion and append persistence; reuse existing reading/source/point concepts. | P3 | — | PARTIAL | DEFERRED |
| [HAPI-101](ULTIMATE_TASKS.md#hapi-101) | add timeseries storage migration | Add the actual next Flyway migration implementing the approved Timescale/PostgreSQL strategy. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-102](ULTIMATE_TASKS.md#hapi-102) | add jdbc high velocity persistence adapter | Implement approved JDBC batch persistence behind the Telemetry output port. | P3 | — | PARTIAL | DEFERRED |
| [HAPI-103](ULTIMATE_TASKS.md#hapi-103) | verify high velocity ingestion | Add integration/performance-oriented tests for batch writes, duplicates, quality/timestamps and schema compatibility. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-160](ULTIMATE_TASKS.md#hapi-160) | scan monitoring domain models | Scan all 2 monitoring model files. | P1 | — | VERIFIED | COMPLETED |
| [HAPI-168](ULTIMATE_TASKS.md#hapi-168) | scan telemetry domain models | Scan all 4 telemetry model files. | P1 | — | VERIFIED | COMPLETED |
| [HAPI-178](ULTIMATE_TASKS.md#hapi-178) | remediate semantic review TelemetryPoint | remediate semantic review TelemetryPoint | P1 | — | VERIFIED | COMPLETED |
| [HAPI-179](ULTIMATE_TASKS.md#hapi-179) | define point signal-unit compatibility metadata | define point signal-unit compatibility metadata | P1 | — | VERIFIED | COMPLETED |
| [HAPI-212](ULTIMATE_TASKS.md#hapi-212) | remediate semantic review TelemetrySource | remediate semantic review TelemetrySource | P1 | — | VERIFIED | COMPLETED |
| [HAPI-216](ULTIMATE_TASKS.md#hapi-216) | remediate semantic review TelemetryReading | remediate semantic review TelemetryReading | P1 | — | VERIFIED | COMPLETED |
| [HAPI-226](ULTIMATE_TASKS.md#hapi-226) | remediate semantic review MonitoringRule | remediate semantic review MonitoringRule | P1 | — | VERIFIED | COMPLETED |
| [HAPI-227](ULTIMATE_TASKS.md#hapi-227) | register MonitoringRule telemetry owner prerequisite | register MonitoringRule telemetry owner prerequisite | P1 | — | VERIFIED | COMPLETED |
| [HAPI-247](ULTIMATE_TASKS.md#hapi-247) | remediate semantic review TrustedTelemetryReading | remediate semantic review TrustedTelemetryReading | P1 | — | VERIFIED | COMPLETED |
| [HAPI-297](ULTIMATE_TASKS.md#hapi-297) | remediate semantic review PlanActualDeviation | remediate semantic review PlanActualDeviation | P1 | — | VERIFIED | COMPLETED |
| [HAPI-315](ULTIMATE_TASKS.md#hapi-315) | review telemetry TelemetryPoint | review telemetry TelemetryPoint | P1 | — | VERIFIED | COMPLETED |
| [HAPI-343](ULTIMATE_TASKS.md#hapi-343) | review telemetry TelemetrySource | review telemetry TelemetrySource | P1 | — | VERIFIED | COMPLETED |
| [HAPI-347](ULTIMATE_TASKS.md#hapi-347) | review telemetry TelemetryReading | review telemetry TelemetryReading | P1 | — | VERIFIED | COMPLETED |
| [HAPI-358](ULTIMATE_TASKS.md#hapi-358) | review monitoring MonitoringRule | review monitoring MonitoringRule | P1 | — | VERIFIED | COMPLETED |
| [HAPI-372](ULTIMATE_TASKS.md#hapi-372) | review telemetry TrustedTelemetryReading | review telemetry TrustedTelemetryReading | P1 | — | VERIFIED | COMPLETED |
| [HAPI-430](ULTIMATE_TASKS.md#hapi-430) | review monitoring PlanActualDeviation | review monitoring PlanActualDeviation | P1 | — | VERIFIED | COMPLETED |
| [HAPI-494](ULTIMATE_TASKS.md#hapi-494) | establish capacity baseline | Define approved telemetry throughput, burst, retention, latency, backlog and replay requirements and benchmark current PostgreSQL/JPA. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-495](ULTIMATE_TASKS.md#hapi-495) | introduce timescaledb persistence | Adopt TimescaleDB only if HPR-P3-001 evidence justifies it; provision extension and convert only approved high-volume tables to hypertables. | P3 | HAPI-494 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-496](ULTIMATE_TASKS.md#hapi-496) | add timescale lifecycle policies | Add approved compression, retention and continuous aggregates only from verified workload/retention requirements. | P3 | HAPI-495 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-497](ULTIMATE_TASKS.md#hapi-497) | add high throughput ingestion adapter | Replace per-entity JPA ingestion only where benchmarks prove it inadequate; retain new adapter behind Telemetry ports and verify burst/replay/failure behavior. | P3 | HAPI-494 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-510](ULTIMATE_TASKS.md#hapi-510) | synchronize trusted network measurements | Implement the approved read-only acquisition adapter and coherent timestamp/unit/asset binding; verify replay, duplicates, late/missing readings, disconnect and… | P3 | HAPI-503, HAPI-504 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-512](ULTIMATE_TASKS.md#hapi-512) | detect network issues from trusted state | Implement the approved issue catalogue and correlated detection across the connected network; distinguish sensor/data faults, suspected process faults and… | P3 | HAPI-511 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-610](ULTIMATE_TASKS.md#hapi-610) | Integrated closure: five telemetry-trust/quality/replay packages | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-056; child tasks own implementation. five telemetry-trust/quality/replay… | P1 | HAPI-615 | PARTIAL | BLOCKED |
| [HAPI-611](ULTIMATE_TASKS.md#hapi-611) | Configured telemetry source and effective bindings | Configured source identity and versioned effective point/tag/asset/unit bindings. | P1 | HAPI-567, HAPI-588 | PARTIAL | BLOCKED |
| [HAPI-612](ULTIMATE_TASKS.md#hapi-612) | Authorized ingestion and immutable raw provenance | Authorized ingest with immutable raw provenance and idempotency/conflict semantics. | P1 | HAPI-611 | PARTIAL | BLOCKED |
| [HAPI-613](ULTIMATE_TASKS.md#hapi-613) | Configured quality, freshness and ordering assessment | Configured quality, freshness, clock-skew and late/out-of-order assessment. | P1 | HAPI-612 | PARTIAL | BLOCKED |
| [HAPI-614](ULTIMATE_TASKS.md#hapi-614) | Deterministic telemetry history, replay and recovery | Deterministic history/replay and source outage/recovery/dead-letter behavior. | P1 | HAPI-613 | PARTIAL | BLOCKED |
| [HAPI-615](ULTIMATE_TASKS.md#hapi-615) | Telemetry API/security/monitoring qualification | Actual API/DB/monitoring export security and consumer qualification. | P1 | HAPI-614 | PARTIAL | BLOCKED |
| [HAPI-839](ULTIMATE_TASKS.md#hapi-839) | add telemetry implementation roadmap | Create and maintain this roadmap. | P1 | — | VERIFIED | COMPLETED |
| [HAPI-840](ULTIMATE_TASKS.md#hapi-840) | add telemetry package skeleton | Add package skeleton only. | P1 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-841](ULTIMATE_TASKS.md#hapi-841) | add telemetry domain value objects | Add ids, codes, timestamps, value objects, topology references, catalog references, technical statuses. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-842](ULTIMATE_TASKS.md#hapi-842) | add telemetry catalog domain models | Add TelemetryTypeCatalog and TelemetryTypeTranslation. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-843](ULTIMATE_TASKS.md#hapi-843) | add telemetry domain models | Add source, device, point, binding, reading, batch models. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-844](ULTIMATE_TASKS.md#hapi-844) | add telemetry domain policies | Add lifecycle, binding, quality, ingestion, catalog policies. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-845](ULTIMATE_TASKS.md#hapi-845) | add telemetry domain services | Add registration, binding, ingestion, catalog resolution services. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-846](ULTIMATE_TASKS.md#hapi-846) | add telemetry application commands and queries | Add create/list/get/ingest command-query contracts. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-847](ULTIMATE_TASKS.md#hapi-847) | add telemetry application DTOs | Add localized DTOs and reading DTOs. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-848](ULTIMATE_TASKS.md#hapi-848) | add telemetry application ports | Add inbound use cases and outbound repository ports. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-849](ULTIMATE_TASKS.md#hapi-849) | add telemetry application services | Add source/device/point/binding/reading/catalog services. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-850](ULTIMATE_TASKS.md#hapi-850) | add telemetry catalog tables | Add V022 catalog/translation tables and seeds. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-851](ULTIMATE_TASKS.md#hapi-851) | add telemetry metadata tables | Add V023 source/device/point/binding/batch tables. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-852](ULTIMATE_TASKS.md#hapi-852) | add telemetry reading tables | Add V024 append-only reading table and indexes. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-853](ULTIMATE_TASKS.md#hapi-853) | add telemetry persistence entities and repositories | Add JPA entities/repositories. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-854](ULTIMATE_TASKS.md#hapi-854) | add telemetry persistence mapper and adapters | Add mappers/adapters implementing outbound ports. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-855](ULTIMATE_TASKS.md#hapi-855) | add telemetry infrastructure configuration | Add Spring bean configuration. | P1 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-856](ULTIMATE_TASKS.md#hapi-856) | add telemetry REST request DTOs | Add request DTOs for sources/devices/points/bindings/readings/catalogs. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-857](ULTIMATE_TASKS.md#hapi-857) | add telemetry REST response DTOs | Add localized response DTOs. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-858](ULTIMATE_TASKS.md#hapi-858) | add telemetry REST mapper | Map REST to app contracts and localized responses. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-859](ULTIMATE_TASKS.md#hapi-859) | add telemetry REST controllers | Add endpoint groups under /api/v1/telemetry/. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-860](ULTIMATE_TASKS.md#hapi-860) | add telemetry OpenAPI coverage checklist | Ensure Swagger/OpenAPI coverage. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-861](ULTIMATE_TASKS.md#hapi-861) | add telemetry domain tests | Test value objects, models, policies, services. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-862](ULTIMATE_TASKS.md#hapi-862) | add telemetry application service tests | Test application services with fakes. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-863](ULTIMATE_TASKS.md#hapi-863) | add telemetry persistence tests | Test mappers/adapters/repository contracts. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-864](ULTIMATE_TASKS.md#hapi-864) | add telemetry REST mapper and controller tests | Test REST mapper/controllers with fake use cases. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-865](ULTIMATE_TASKS.md#hapi-865) | add telemetry application boot smoke test | Validate full Spring context with telemetry. | P1 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-866](ULTIMATE_TASKS.md#hapi-866) | finalize telemetry validation checklist | Final checklist and handoff. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-867](ULTIMATE_TASKS.md#hapi-867) | TEL-COR-001 historical bounded obligation | TEL-COR-001 historical bounded obligation | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-868](ULTIMATE_TASKS.md#hapi-868) | TEL-COR-002 historical bounded obligation | TEL-COR-002 historical bounded obligation | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-869](ULTIMATE_TASKS.md#hapi-869) | TEL-COR-003 historical bounded obligation | TEL-COR-003 historical bounded obligation | P1 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-870](ULTIMATE_TASKS.md#hapi-870) | TEL-COR-004 historical bounded obligation | TEL-COR-004 historical bounded obligation | P1 | — | NOT_VERIFIABLE | SUPERSEDED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-011 — Alarms and Events

### 1. Description

Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `alarm`, `notification`. Recording a notification attempt does not establish external delivery; no invented STOMP event families.

### 4. Existing Capabilities

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: No additional cross-phase edge independently established; preserve original bounded prerequisites in ULTIMATE_TASKS.md. Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-001](ULTIMATE_TASKS.md#hapi-001) | approve suppression lifecycle semantics | This document only. Record the approved suppression policy and implementation sequence. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-002](ULTIMATE_TASKS.md#hapi-002) | add suppression application contracts | Add minimal Alarm application commands/queries/use-case ports and DTOs for create, release, expiry evaluation, and query behavior. Reuse existing… | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-003](ULTIMATE_TASKS.md#hapi-003) | implement suppression lifecycle policy | Implement domain/application policy for ALARM overlay/restoration, broad-scope matching, acknowledgement/clear/close/escalation interaction, conflicts, and… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-004](ULTIMATE_TASKS.md#hapi-004) | expose suppression persistence queries | Extend existing suppression repository adapter only as required by approved use cases: exact-scope active lookup, expiry candidates, history/query support. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-005](ULTIMATE_TASKS.md#hapi-005) | integrate suppression workflow approval | Verify open-ended suppression through Workflow public contracts/ports and fail closed when approval is absent. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-006](ULTIMATE_TASKS.md#hapi-006) | add suppression expiry orchestration | Add idempotent backend-owned expiry orchestration using the existing Spring scheduling mechanism. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-007](ULTIMATE_TASKS.md#hapi-007) | add suppression expiry audit contract | Export a narrow Audit contract and provision canonical ALARM_SUPPRESSION_EXPIRED taxonomy for all suppression scopes. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-008](ULTIMATE_TASKS.md#hapi-008) | publish suppression REST contracts | Publish canonical create/release/query endpoints, generated OpenAPI schemas, route-permission metadata, deterministic 400/403/404/409 behavior. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-009](ULTIMATE_TASKS.md#hapi-009) | verify suppression lifecycle end to end | Domain/application/persistence/REST/Testcontainers coverage including ALARM and broad scopes, acknowledgement, clear/close, escalation preservation, workflow… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-010](ULTIMATE_TASKS.md#hapi-010) | finalize suppression capability | Record exact endpoints, permissions, events, validation evidence, remaining limits, and frontend-consumption boundary. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-147](ULTIMATE_TASKS.md#hapi-147) | scan alarm domain models | Scan all 4 alarm/domain/model files; record models, fields and Java types. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-161](ULTIMATE_TASKS.md#hapi-161) | scan notification domain models | Scan all 4 notification model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-189](ULTIMATE_TASKS.md#hapi-189) | remediate semantic review NotificationTemplate | remediate semantic review NotificationTemplate | P2 | — | VERIFIED | COMPLETED |
| [HAPI-219](ULTIMATE_TASKS.md#hapi-219) | remediate semantic review NotificationRequest | remediate semantic review NotificationRequest | P2 | — | VERIFIED | COMPLETED |
| [HAPI-246](ULTIMATE_TASKS.md#hapi-246) | remediate semantic review NotificationMessage | remediate semantic review NotificationMessage | P2 | — | VERIFIED | COMPLETED |
| [HAPI-254](ULTIMATE_TASKS.md#hapi-254) | remediate semantic review NotificationDeliveryAttempt | remediate semantic review NotificationDeliveryAttempt | P2 | — | VERIFIED | COMPLETED |
| [HAPI-294](ULTIMATE_TASKS.md#hapi-294) | remediate semantic review Alarm | remediate semantic review Alarm | P2 | — | VERIFIED | COMPLETED |
| [HAPI-298](ULTIMATE_TASKS.md#hapi-298) | remediate semantic review AlarmAcknowledgement | remediate semantic review AlarmAcknowledgement | P2 | — | VERIFIED | COMPLETED |
| [HAPI-299](ULTIMATE_TASKS.md#hapi-299) | remediate semantic review AlarmClosure | remediate semantic review AlarmClosure | P2 | — | VERIFIED | COMPLETED |
| [HAPI-300](ULTIMATE_TASKS.md#hapi-300) | remediate semantic review AlarmShelving | remediate semantic review AlarmShelving | P2 | — | VERIFIED | COMPLETED |
| [HAPI-304](ULTIMATE_TASKS.md#hapi-304) | inventory target model dependency evidence | Inventory candidate relationship evidence for all 123 models from domain source, JPA, Flyway, contracts and enums. Every candidate edge retains… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-322](ULTIMATE_TASKS.md#hapi-322) | review notification NotificationTemplate | review notification NotificationTemplate | P2 | — | VERIFIED | COMPLETED |
| [HAPI-349](ULTIMATE_TASKS.md#hapi-349) | review notification NotificationRequest | review notification NotificationRequest | P2 | — | VERIFIED | COMPLETED |
| [HAPI-371](ULTIMATE_TASKS.md#hapi-371) | review notification NotificationMessage | review notification NotificationMessage | P2 | — | VERIFIED | COMPLETED |
| [HAPI-382](ULTIMATE_TASKS.md#hapi-382) | review notification NotificationDeliveryAttempt | review notification NotificationDeliveryAttempt | P2 | — | VERIFIED | COMPLETED |
| [HAPI-427](ULTIMATE_TASKS.md#hapi-427) | review alarm Alarm | review alarm Alarm | P2 | — | VERIFIED | COMPLETED |
| [HAPI-431](ULTIMATE_TASKS.md#hapi-431) | review alarm AlarmAcknowledgement | review alarm AlarmAcknowledgement | P2 | — | VERIFIED | COMPLETED |
| [HAPI-432](ULTIMATE_TASKS.md#hapi-432) | review alarm AlarmClosure | review alarm AlarmClosure | P2 | — | VERIFIED | COMPLETED |
| [HAPI-433](ULTIMATE_TASKS.md#hapi-433) | review alarm AlarmShelving | review alarm AlarmShelving | P2 | — | VERIFIED | COMPLETED |
| [HAPI-652](ULTIMATE_TASKS.md#hapi-652) | simplify boundary dto mapping | Pilot compile-time generated mapping for the HRA-070-approved exact alarm pairs only; retain security-enriched alarm mappings as hand-written code. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-707](ULTIMATE_TASKS.md#hapi-707) | NOTIF-001 historical bounded obligation | NOTIF-001 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-708](ULTIMATE_TASKS.md#hapi-708) | NOTIF-002 historical bounded obligation | NOTIF-002 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-709](ULTIMATE_TASKS.md#hapi-709) | NOTIF-003 historical bounded obligation | NOTIF-003 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-710](ULTIMATE_TASKS.md#hapi-710) | NOTIF-004 historical bounded obligation | NOTIF-004 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-711](ULTIMATE_TASKS.md#hapi-711) | NOTIF-005 historical bounded obligation | NOTIF-005 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-712](ULTIMATE_TASKS.md#hapi-712) | NOTIF-006 historical bounded obligation | NOTIF-006 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-713](ULTIMATE_TASKS.md#hapi-713) | NOTIF-007 historical bounded obligation | NOTIF-007 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-714](ULTIMATE_TASKS.md#hapi-714) | NOTIF-008 historical bounded obligation | NOTIF-008 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-715](ULTIMATE_TASKS.md#hapi-715) | NOTIF-009 historical bounded obligation | NOTIF-009 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-716](ULTIMATE_TASKS.md#hapi-716) | NOTIF-010 historical bounded obligation | NOTIF-010 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-717](ULTIMATE_TASKS.md#hapi-717) | NOTIF-011 historical bounded obligation | NOTIF-011 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-718](ULTIMATE_TASKS.md#hapi-718) | NOTIF-012 historical bounded obligation | NOTIF-012 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-719](ULTIMATE_TASKS.md#hapi-719) | NOTIF-013 historical bounded obligation | NOTIF-013 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-720](ULTIMATE_TASKS.md#hapi-720) | NOTIF-014 historical bounded obligation | NOTIF-014 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-721](ULTIMATE_TASKS.md#hapi-721) | NOTIF-015 historical bounded obligation | NOTIF-015 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-722](ULTIMATE_TASKS.md#hapi-722) | NOTIF-016 historical bounded obligation | NOTIF-016 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-012 — Incidents, HSE and Integrity

### 1. Description

Preserve incident response, HSE/CAPA, integrity assessments and suspected-leak evidence.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `incident`, `hse`, `integrity`, `leakdetection`. Leak case management does not prove a calibrated leak detector; HSE records do not replace safety-instrumented functions.

### 4. Existing Capabilities

Case/candidate queries, response actions, locked-parent transitions and semantic owner-reference guards exist. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Qualify linked observation/action/outcome workflows and preserve deferred CPM/spatial/ILI engineering work. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: No additional cross-phase edge independently established; preserve original bounded prerequisites in ULTIMATE_TASKS.md. Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-085](ULTIMATE_TASKS.md#hapi-085) | ENV-001 historical bounded obligation | ENV-001 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| [HAPI-086](ULTIMATE_TASKS.md#hapi-086) | ENV-002 historical bounded obligation | ENV-002 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| [HAPI-087](ULTIMATE_TASKS.md#hapi-087) | ENV-003 historical bounded obligation | ENV-003 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| [HAPI-088](ULTIMATE_TASKS.md#hapi-088) | ENV-004 historical bounded obligation | ENV-004 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| [HAPI-089](ULTIMATE_TASKS.md#hapi-089) | ENV-005 historical bounded obligation | ENV-005 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| [HAPI-090](ULTIMATE_TASKS.md#hapi-090) | ENV-006 historical bounded obligation | ENV-006 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| [HAPI-091](ULTIMATE_TASKS.md#hapi-091) | ENV-007 historical bounded obligation | ENV-007 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| [HAPI-092](ULTIMATE_TASKS.md#hapi-092) | ENV-008 historical bounded obligation | ENV-008 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| [HAPI-093](ULTIMATE_TASKS.md#hapi-093) | ENV-009 historical bounded obligation | ENV-009 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| [HAPI-094](ULTIMATE_TASKS.md#hapi-094) | ENV-010 historical bounded obligation | ENV-010 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| [HAPI-095](ULTIMATE_TASKS.md#hapi-095) | ENV-011 historical bounded obligation | ENV-011 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| [HAPI-096](ULTIMATE_TASKS.md#hapi-096) | ENV-012 historical bounded obligation | ENV-012 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| [HAPI-107](ULTIMATE_TASKS.md#hapi-107) | define cpm service contract | Define LeakDetectionAPI responsibility, model/config input, telemetry input, anomaly output, availability and idempotency semantics. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-108](ULTIMATE_TASKS.md#hapi-108) | add cpm protobuf contract | Add the approved versioned Protobuf contract in the authorized contract location. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-109](ULTIMATE_TASKS.md#hapi-109) | add cpm grpc client port | Add Hidra application output port and mapping contracts for anomaly stream consumption. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-110](ULTIMATE_TASKS.md#hapi-110) | add cpm grpc client adapter | Add gRPC client configuration/adapter, stream resilience and mapping to existing CreateLeakCandidateUseCase. | P3 | — | PARTIAL | DEFERRED |
| [HAPI-111](ULTIMATE_TASKS.md#hapi-111) | verify cpm anomaly integration | Contract/integration tests for anomaly mapping and idempotent candidate creation. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-118](ULTIMATE_TASKS.md#hapi-118) | specify modified b31g calculation | Record approved engineering equation set, standard/edition, inputs, units, limits, ERF/MAOP semantics, rounding and reference cases. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-119](ULTIMATE_TASKS.md#hapi-119) | add modified b31g assessment | Implement the calculator from EXT-022 and map outputs into the existing assessment model or an explicitly approved extension. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-120](ULTIMATE_TASKS.md#hapi-120) | verify ili b31g workflow | End-to-end tests from approved ILI evidence through spatial/KP resolution and assessment persistence. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-121](ULTIMATE_TASKS.md#hapi-121) | enforce industrial extension boundaries | Add architecture guards for Integration→Telemetry handoff, gRPC isolation and Topology/Integrity ownership. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-122](ULTIMATE_TASKS.md#hapi-122) | add industrial extension runbooks | Document MQTT, Timescale/PostGIS prerequisites, gRPC operations, secrets, health and rollback. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-123](ULTIMATE_TASKS.md#hapi-123) | close extended industrial capability milestone | Reconcile all EXT tasks, CI evidence, compatibility and unresolved risks. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-154](ULTIMATE_TASKS.md#hapi-154) | scan hse domain models | Scan all 4 hse model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-156](ULTIMATE_TASKS.md#hapi-156) | scan incident domain models | Scan all 4 incident model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-158](ULTIMATE_TASKS.md#hapi-158) | scan integrity domain models | Scan all 4 integrity model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-159](ULTIMATE_TASKS.md#hapi-159) | scan leak detection domain models | Scan all 3 leakdetection model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-192](ULTIMATE_TASKS.md#hapi-192) | remediate semantic review LeakCandidate | remediate semantic review LeakCandidate | P2 | — | VERIFIED | COMPLETED |
| [HAPI-198](ULTIMATE_TASKS.md#hapi-198) | remediate semantic review PipelineDefect | remediate semantic review PipelineDefect | P2 | — | VERIFIED | COMPLETED |
| [HAPI-244](ULTIMATE_TASKS.md#hapi-244) | remediate semantic review IntegrityProgram | remediate semantic review IntegrityProgram | P2 | — | VERIFIED | COMPLETED |
| [HAPI-245](ULTIMATE_TASKS.md#hapi-245) | remediate semantic review LeakDetectionCase | remediate semantic review LeakDetectionCase | P2 | — | VERIFIED | COMPLETED |
| [HAPI-253](ULTIMATE_TASKS.md#hapi-253) | remediate semantic review LeakEscalationReference | remediate semantic review LeakEscalationReference | P2 | — | VERIFIED | COMPLETED |
| [HAPI-256](ULTIMATE_TASKS.md#hapi-256) | remediate semantic review Incident | remediate semantic review Incident | P2 | — | VERIFIED | COMPLETED |
| [HAPI-266](ULTIMATE_TASKS.md#hapi-266) | remediate semantic review IntegrityAssessment | remediate semantic review IntegrityAssessment | P2 | — | VERIFIED | COMPLETED |
| [HAPI-276](ULTIMATE_TASKS.md#hapi-276) | remediate semantic review HseCase | remediate semantic review HseCase | P2 | — | VERIFIED | COMPLETED |
| [HAPI-284](ULTIMATE_TASKS.md#hapi-284) | remediate semantic review IncidentClosure | remediate semantic review IncidentClosure | P2 | — | VERIFIED | COMPLETED |
| [HAPI-285](ULTIMATE_TASKS.md#hapi-285) | remediate semantic review IncidentRelatedIncident | remediate semantic review IncidentRelatedIncident | P2 | — | VERIFIED | COMPLETED |
| [HAPI-286](ULTIMATE_TASKS.md#hapi-286) | remediate semantic review IncidentResponseAction | remediate semantic review IncidentResponseAction | P2 | — | VERIFIED | COMPLETED |
| [HAPI-290](ULTIMATE_TASKS.md#hapi-290) | remediate semantic review HseClosure | remediate semantic review HseClosure | P2 | — | VERIFIED | COMPLETED |
| [HAPI-291](ULTIMATE_TASKS.md#hapi-291) | remediate semantic review HseCorrectivePreventiveAction | remediate semantic review HseCorrectivePreventiveAction | P2 | — | VERIFIED | COMPLETED |
| [HAPI-292](ULTIMATE_TASKS.md#hapi-292) | remediate semantic review IntegrityCase | remediate semantic review IntegrityCase | P2 | — | VERIFIED | COMPLETED |
| [HAPI-325](ULTIMATE_TASKS.md#hapi-325) | review leakdetection LeakCandidate | review leakdetection LeakCandidate | P2 | — | VERIFIED | COMPLETED |
| [HAPI-331](ULTIMATE_TASKS.md#hapi-331) | review integrity PipelineDefect | review integrity PipelineDefect | P2 | — | VERIFIED | COMPLETED |
| [HAPI-369](ULTIMATE_TASKS.md#hapi-369) | review integrity IntegrityProgram | review integrity IntegrityProgram | P2 | — | VERIFIED | COMPLETED |
| [HAPI-370](ULTIMATE_TASKS.md#hapi-370) | review leakdetection LeakDetectionCase | review leakdetection LeakDetectionCase | P2 | — | VERIFIED | COMPLETED |
| [HAPI-381](ULTIMATE_TASKS.md#hapi-381) | review leakdetection LeakEscalationReference | review leakdetection LeakEscalationReference | P2 | — | VERIFIED | COMPLETED |
| [HAPI-384](ULTIMATE_TASKS.md#hapi-384) | review incident Incident | review incident Incident | P2 | — | VERIFIED | COMPLETED |
| [HAPI-393](ULTIMATE_TASKS.md#hapi-393) | review hse PermitToWork | review hse PermitToWork | P2 | — | VERIFIED | COMPLETED |
| [HAPI-395](ULTIMATE_TASKS.md#hapi-395) | review integrity IntegrityAssessment | review integrity IntegrityAssessment | P2 | — | VERIFIED | COMPLETED |
| [HAPI-406](ULTIMATE_TASKS.md#hapi-406) | review hse HseCase | review hse HseCase | P2 | — | VERIFIED | COMPLETED |
| [HAPI-417](ULTIMATE_TASKS.md#hapi-417) | review incident IncidentClosure | review incident IncidentClosure | P2 | — | VERIFIED | COMPLETED |
| [HAPI-418](ULTIMATE_TASKS.md#hapi-418) | review incident IncidentRelatedIncident | review incident IncidentRelatedIncident | P2 | — | VERIFIED | COMPLETED |
| [HAPI-419](ULTIMATE_TASKS.md#hapi-419) | review incident IncidentResponseAction | review incident IncidentResponseAction | P2 | — | VERIFIED | COMPLETED |
| [HAPI-423](ULTIMATE_TASKS.md#hapi-423) | review hse HseClosure | review hse HseClosure | P2 | — | VERIFIED | COMPLETED |
| [HAPI-424](ULTIMATE_TASKS.md#hapi-424) | review hse HseCorrectivePreventiveAction | review hse HseCorrectivePreventiveAction | P2 | — | VERIFIED | COMPLETED |
| [HAPI-425](ULTIMATE_TASKS.md#hapi-425) | review integrity IntegrityCase | review integrity IntegrityCase | P2 | — | VERIFIED | COMPLETED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-013 — Planning and Nominations

### 1. Description

Govern periods, plan revisions, targets, nominations and accountable approval.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `planning`. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement.

### 4. Existing Capabilities

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Preserve accepted nomination semantics and qualify integrated dispatch/national/export scenarios against versioned engineering evidence. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: No additional cross-phase edge independently established; preserve original bounded prerequisites in ULTIMATE_TASKS.md. Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-164](ULTIMATE_TASKS.md#hapi-164) | scan planning domain models | Scan all 5 planning model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-180](ULTIMATE_TASKS.md#hapi-180) | remediate semantic review PlanningPeriod | remediate semantic review PlanningPeriod | P2 | — | VERIFIED | COMPLETED |
| [HAPI-258](ULTIMATE_TASKS.md#hapi-258) | remediate semantic review PlanRevision | remediate semantic review PlanRevision | P2 | — | VERIFIED | COMPLETED |
| [HAPI-259](ULTIMATE_TASKS.md#hapi-259) | remediate semantic review OperationalPlan | remediate semantic review OperationalPlan | P2 | — | VERIFIED | COMPLETED |
| [HAPI-274](ULTIMATE_TASKS.md#hapi-274) | remediate semantic review Nomination | remediate semantic review Nomination | P2 | — | VERIFIED | COMPLETED |
| [HAPI-288](ULTIMATE_TASKS.md#hapi-288) | remediate semantic review PlanTarget | remediate semantic review PlanTarget | P2 | — | VERIFIED | COMPLETED |
| [HAPI-316](ULTIMATE_TASKS.md#hapi-316) | review planning PlanningPeriod | review planning PlanningPeriod | P2 | — | VERIFIED | COMPLETED |
| [HAPI-386](ULTIMATE_TASKS.md#hapi-386) | review planning PlanRevision | review planning PlanRevision | P2 | — | VERIFIED | COMPLETED |
| [HAPI-387](ULTIMATE_TASKS.md#hapi-387) | review planning OperationalPlan | review planning OperationalPlan | P2 | — | VERIFIED | COMPLETED |
| [HAPI-404](ULTIMATE_TASKS.md#hapi-404) | review planning Nomination | review planning Nomination | P2 | — | VERIFIED | COMPLETED |
| [HAPI-421](ULTIMATE_TASKS.md#hapi-421) | review planning PlanTarget | review planning PlanTarget | P2 | — | VERIFIED | COMPLETED |
| [HAPI-659](ULTIMATE_TASKS.md#hapi-659) | return application revision result | Return an application result DTO from plan-revision updates rather than a domain PlanRevision to REST. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-796](ULTIMATE_TASKS.md#hapi-796) | expose HWEB-010 query contracts | Read-only list/detail contracts for periods, operational plans, revisions, nominations and plan targets; stable pagination; deterministic 400/404; route-permission… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-797](ULTIMATE_TASKS.md#hapi-797) | publish authoritative workflow approval integration | Revision-scoped approval status/actions and backend-owned transition execution using workflow public contracts; planning applies the resulting lifecycle effect;… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-798](ULTIMATE_TASKS.md#hapi-798) | expose plan-target-scoped deviations | Extend the existing monitoring deviation collection with an optional exact planTargetId filter so HWEB-010-05 can retrieve authoritative comparison rows for one… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-799](ULTIMATE_TASKS.md#hapi-799) | publish revision concurrency contract | Publish an explicit concurrency-protected update for the current editable plan revision's change-reason metadata. expectedUpdatedAt is the authoritative client… | P2 | — | VERIFIED | COMPLETED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-014 — Custody and Measurement

### 1. Description

Preserve commercial measurement periods, transfer tickets and discrepancy provenance.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `custody`. Recorded quantities and method metadata are distinct from certified metrology or fiscal acceptance.

### 4. Existing Capabilities

Measurement-period/ticket/discrepancy creation and governed audit/reference checks exist. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Qualify complete reconciliation, calculation-method applicability and counterparty workflow without duplicating product revision work. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: No additional cross-phase edge independently established; preserve original bounded prerequisites in ULTIMATE_TASKS.md. Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-152](ULTIMATE_TASKS.md#hapi-152) | scan custody domain models | Scan all 3 custody model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-197](ULTIMATE_TASKS.md#hapi-197) | remediate semantic review CustodyMeasurementPeriod | remediate semantic review CustodyMeasurementPeriod | P2 | — | VERIFIED | COMPLETED |
| [HAPI-208](ULTIMATE_TASKS.md#hapi-208) | remediate semantic review CustodyDiscrepancy | remediate semantic review CustodyDiscrepancy | P2 | — | VERIFIED | COMPLETED |
| [HAPI-264](ULTIMATE_TASKS.md#hapi-264) | remediate semantic review CustodyTransferTicket | remediate semantic review CustodyTransferTicket | P2 | — | VERIFIED | COMPLETED |
| [HAPI-330](ULTIMATE_TASKS.md#hapi-330) | review custody CustodyMeasurementPeriod | review custody CustodyMeasurementPeriod | P2 | — | VERIFIED | COMPLETED |
| [HAPI-339](ULTIMATE_TASKS.md#hapi-339) | review custody CustodyDiscrepancy | review custody CustodyDiscrepancy | P2 | — | VERIFIED | COMPLETED |
| [HAPI-392](ULTIMATE_TASKS.md#hapi-392) | review custody CustodyTransferTicket | review custody CustodyTransferTicket | P2 | — | VERIFIED | COMPLETED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-015 — Workflow and Approvals

### 1. Description

Coordinate authorized human tasks, actions, transitions and owner-governed approval.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `workflow`. Workflow records govern decisions; physical execution and numerical qualification belong to their owners.

### 4. Existing Capabilities

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Reuse these contracts for engineering publication and accountable operational action; qualify rollback and revocation across owners. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: HPS-019 Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-170](ULTIMATE_TASKS.md#hapi-170) | scan workflow domain models | Scan all 8 workflow model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-175](ULTIMATE_TASKS.md#hapi-175) | prepare executable remediation register | Re-read all 104 REVISE sections against live main; populate exact per-task file allowlists, validations, prerequisites, already-fixed evidence, SCC ordering, and… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-176](ULTIMATE_TASKS.md#hapi-176) | remediate semantic review WorkflowDefinition | remediate semantic review WorkflowDefinition | P2 | — | VERIFIED | COMPLETED |
| [HAPI-231](ULTIMATE_TASKS.md#hapi-231) | remediate semantic review WorkflowStep | remediate semantic review WorkflowStep | P2 | — | VERIFIED | COMPLETED |
| [HAPI-232](ULTIMATE_TASKS.md#hapi-232) | remediate semantic review WorkflowStepAssignmentRule | remediate semantic review WorkflowStepAssignmentRule | P2 | — | VERIFIED | COMPLETED |
| [HAPI-249](ULTIMATE_TASKS.md#hapi-249) | remediate semantic review WorkflowInstance | remediate semantic review WorkflowInstance | P2 | — | VERIFIED | COMPLETED |
| [HAPI-255](ULTIMATE_TASKS.md#hapi-255) | remediate semantic review WorkflowTransition | remediate semantic review WorkflowTransition | P2 | — | VERIFIED | COMPLETED |
| [HAPI-260](ULTIMATE_TASKS.md#hapi-260) | remediate semantic review WorkflowTask | remediate semantic review WorkflowTask | P2 | — | VERIFIED | COMPLETED |
| [HAPI-275](ULTIMATE_TASKS.md#hapi-275) | remediate semantic review WorkflowAction | remediate semantic review WorkflowAction | P2 | — | VERIFIED | COMPLETED |
| [HAPI-293](ULTIMATE_TASKS.md#hapi-293) | remediate semantic review WorkflowStateHistory | remediate semantic review WorkflowStateHistory | P2 | — | VERIFIED | COMPLETED |
| [HAPI-312](ULTIMATE_TASKS.md#hapi-312) | review workflow WorkflowDefinition | review workflow WorkflowDefinition | P2 | — | VERIFIED | COMPLETED |
| [HAPI-361](ULTIMATE_TASKS.md#hapi-361) | review workflow WorkflowStep | review workflow WorkflowStep | P2 | — | VERIFIED | COMPLETED |
| [HAPI-362](ULTIMATE_TASKS.md#hapi-362) | review workflow WorkflowStepAssignmentRule | review workflow WorkflowStepAssignmentRule | P2 | — | VERIFIED | COMPLETED |
| [HAPI-374](ULTIMATE_TASKS.md#hapi-374) | review workflow WorkflowInstance | review workflow WorkflowInstance | P2 | — | VERIFIED | COMPLETED |
| [HAPI-383](ULTIMATE_TASKS.md#hapi-383) | review workflow WorkflowTransition | review workflow WorkflowTransition | P2 | — | VERIFIED | COMPLETED |
| [HAPI-388](ULTIMATE_TASKS.md#hapi-388) | review workflow WorkflowTask | review workflow WorkflowTask | P2 | — | VERIFIED | COMPLETED |
| [HAPI-405](ULTIMATE_TASKS.md#hapi-405) | review workflow WorkflowAction | review workflow WorkflowAction | P2 | — | VERIFIED | COMPLETED |
| [HAPI-426](ULTIMATE_TASKS.md#hapi-426) | review workflow WorkflowStateHistory | review workflow WorkflowStateHistory | P2 | — | VERIFIED | COMPLETED |
| [HAPI-524](ULTIMATE_TASKS.md#hapi-524) | record operator decisions and applied actions | Record authenticated human approval, rejection/modification and the action actually applied with time/scope/context; preserve advisory boundaries and immutable… | P3 | HAPI-523 | PARTIAL | DEFERRED |
| [HAPI-657](ULTIMATE_TASKS.md#hapi-657) | expose planning workflow contract | Export Workflow contracts consumed by Planning; stop Planning importing Workflow's internal application package. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-896](ULTIMATE_TASKS.md#hapi-896) | docs(workflow): prepare workflow module roadmap | docs(workflow): prepare workflow module roadmap | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-897](ULTIMATE_TASKS.md#hapi-897) | chore(workflow): add workflow package skeleton | chore(workflow): add workflow package skeleton | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-898](ULTIMATE_TASKS.md#hapi-898) | feat(workflow): add workflow domain value objects | feat(workflow): add workflow domain value objects | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-899](ULTIMATE_TASKS.md#hapi-899) | feat(workflow): add workflow catalog domain models | feat(workflow): add workflow catalog domain models | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-900](ULTIMATE_TASKS.md#hapi-900) | feat(workflow): add workflow core domain models | feat(workflow): add workflow core domain models | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-901](ULTIMATE_TASKS.md#hapi-901) | feat(workflow): add workflow domain policies | feat(workflow): add workflow domain policies | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-902](ULTIMATE_TASKS.md#hapi-902) | feat(workflow): add workflow domain services | feat(workflow): add workflow domain services | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-903](ULTIMATE_TASKS.md#hapi-903) | feat(workflow): add workflow application commands and queries | feat(workflow): add workflow application commands and queries | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-904](ULTIMATE_TASKS.md#hapi-904) | feat(workflow): add workflow application DTOs | feat(workflow): add workflow application DTOs | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-905](ULTIMATE_TASKS.md#hapi-905) | feat(workflow): add workflow application ports | feat(workflow): add workflow application ports | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-906](ULTIMATE_TASKS.md#hapi-906) | feat(workflow): add workflow application services | feat(workflow): add workflow application services | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-907](ULTIMATE_TASKS.md#hapi-907) | feat(workflow): add workflow infrastructure configuration | feat(workflow): add workflow infrastructure configuration | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-908](ULTIMATE_TASKS.md#hapi-908) | db(workflow): add workflow catalog and core tables migration | db(workflow): add workflow catalog and core tables migration | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-909](ULTIMATE_TASKS.md#hapi-909) | feat(workflow): add workflow persistence entities and repositories | feat(workflow): add workflow persistence entities and repositories | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-910](ULTIMATE_TASKS.md#hapi-910) | feat(workflow): add workflow persistence mappers and adapters | feat(workflow): add workflow persistence mappers and adapters | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-911](ULTIMATE_TASKS.md#hapi-911) | feat(workflow): add workflow telemetry target lookup adapter | feat(workflow): add workflow telemetry target lookup adapter | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-912](ULTIMATE_TASKS.md#hapi-912) | feat(workflow): add workflow REST request DTOs | feat(workflow): add workflow REST request DTOs | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-913](ULTIMATE_TASKS.md#hapi-913) | feat(workflow): add workflow REST response DTOs | feat(workflow): add workflow REST response DTOs | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-914](ULTIMATE_TASKS.md#hapi-914) | feat(workflow): add workflow REST mapper | feat(workflow): add workflow REST mapper | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-915](ULTIMATE_TASKS.md#hapi-915) | feat(workflow): add workflow REST controllers | feat(workflow): add workflow REST controllers | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-916](ULTIMATE_TASKS.md#hapi-916) | test(workflow): add workflow domain tests | test(workflow): add workflow domain tests | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-917](ULTIMATE_TASKS.md#hapi-917) | test(workflow): add workflow application service tests | test(workflow): add workflow application service tests | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-918](ULTIMATE_TASKS.md#hapi-918) | test(workflow): add workflow persistence tests | test(workflow): add workflow persistence tests | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-919](ULTIMATE_TASKS.md#hapi-919) | test(workflow): add workflow REST mapper and controller tests | test(workflow): add workflow REST mapper and controller tests | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-920](ULTIMATE_TASKS.md#hapi-920) | test(workflow): add workflow application boot smoke test | test(workflow): add workflow application boot smoke test | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-921](ULTIMATE_TASKS.md#hapi-921) | docs(workflow): finalize workflow validation checklist | docs(workflow): finalize workflow validation checklist | P2 | — | NOT_VERIFIABLE | SUPERSEDED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-016 — Risk and Operational Intelligence

### 1. Description

Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `risk`, `monitoring`, `analytics`. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records.

### 4. Existing Capabilities

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: HPS-010, HPS-018 Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-166](ULTIMATE_TASKS.md#hapi-166) | scan risk domain models | Scan all 4 risk model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-211](ULTIMATE_TASKS.md#hapi-211) | remediate semantic review RiskMatrixCell | remediate semantic review RiskMatrixCell | P2 | — | VERIFIED | COMPLETED |
| [HAPI-241](ULTIMATE_TASKS.md#hapi-241) | remediate semantic review RiskRegister | remediate semantic review RiskRegister | P2 | — | VERIFIED | COMPLETED |
| [HAPI-242](ULTIMATE_TASKS.md#hapi-242) | register RiskRegister owner prerequisites | register RiskRegister owner prerequisites | P2 | — | VERIFIED | COMPLETED |
| [HAPI-252](ULTIMATE_TASKS.md#hapi-252) | remediate semantic review RiskAssessment | remediate semantic review RiskAssessment | P2 | — | VERIFIED | COMPLETED |
| [HAPI-271](ULTIMATE_TASKS.md#hapi-271) | remediate semantic review RiskEvidenceLink | remediate semantic review RiskEvidenceLink | P2 | — | VERIFIED | COMPLETED |
| [HAPI-342](ULTIMATE_TASKS.md#hapi-342) | review risk RiskMatrixCell | review risk RiskMatrixCell | P2 | — | VERIFIED | COMPLETED |
| [HAPI-368](ULTIMATE_TASKS.md#hapi-368) | review risk RiskRegister | review risk RiskRegister | P2 | — | VERIFIED | COMPLETED |
| [HAPI-379](ULTIMATE_TASKS.md#hapi-379) | review risk RiskAssessment | review risk RiskAssessment | P2 | — | VERIFIED | COMPLETED |
| [HAPI-401](ULTIMATE_TASKS.md#hapi-401) | review risk RiskEvidenceLink | review risk RiskEvidenceLink | P2 | — | VERIFIED | COMPLETED |
| [HAPI-616](ULTIMATE_TASKS.md#hapi-616) | Integrated closure: four observation/action/outcome packages | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-057; child tasks own implementation. four observation/action/outcome packages | P2 | HAPI-620 | PARTIAL | BLOCKED |
| [HAPI-617](ULTIMATE_TASKS.md#hapi-617) | Monitoring conditions and observation evidence | Monitoring conditions and measured/declared/estimated/UNKNOWN observation evidence. | P2 | HAPI-610 | PARTIAL | BLOCKED |
| [HAPI-618](ULTIMATE_TASKS.md#hapi-618) | Authenticated human action declarations and Workflow | Authenticated operator action declaration and actual supported Workflow target transitions. | P2 | HAPI-617 | PARTIAL | BLOCKED |
| [HAPI-619](ULTIMATE_TASKS.md#hapi-619) | Observation/outcome correlation and recovery | Subsequent observation/outcome correlation, factual audit/query and recovery. | P2 | HAPI-618 | PARTIAL | BLOCKED |
| [HAPI-620](ULTIMATE_TASKS.md#hapi-620) | Cross-owner operational action/observation qualification | Cross-owner action/observation security, rollback and consumer qualification. | P2 | HAPI-619 | PARTIAL | BLOCKED |
| [HAPI-705](ULTIMATE_TASKS.md#hapi-705) | Implement accountable AI-assisted evidence synthesis | Add a governed, provider-neutral synthesis adapter behind an outbound port; use authorized versioned evidence, deterministic safety checks, provenance, explicit… | P2 | HAPI-604, HAPI-616 | NOT_IMPLEMENTED | BLOCKED |
| [HAPI-800](ULTIMATE_TASKS.md#hapi-800) | RISK-001 historical bounded obligation | RISK-001 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-801](ULTIMATE_TASKS.md#hapi-801) | RISK-002 historical bounded obligation | RISK-002 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-802](ULTIMATE_TASKS.md#hapi-802) | RISK-003 historical bounded obligation | RISK-003 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-803](ULTIMATE_TASKS.md#hapi-803) | RISK-004 historical bounded obligation | RISK-004 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-804](ULTIMATE_TASKS.md#hapi-804) | RISK-005 historical bounded obligation | RISK-005 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-805](ULTIMATE_TASKS.md#hapi-805) | RISK-006 historical bounded obligation | RISK-006 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-806](ULTIMATE_TASKS.md#hapi-806) | RISK-007 historical bounded obligation | RISK-007 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-807](ULTIMATE_TASKS.md#hapi-807) | RISK-008 historical bounded obligation | RISK-008 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-808](ULTIMATE_TASKS.md#hapi-808) | RISK-009 historical bounded obligation | RISK-009 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-809](ULTIMATE_TASKS.md#hapi-809) | RISK-010 historical bounded obligation | RISK-010 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-810](ULTIMATE_TASKS.md#hapi-810) | RISK-011 historical bounded obligation | RISK-011 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-811](ULTIMATE_TASKS.md#hapi-811) | RISK-012 historical bounded obligation | RISK-012 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-812](ULTIMATE_TASKS.md#hapi-812) | RISK-013 historical bounded obligation | RISK-013 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-813](ULTIMATE_TASKS.md#hapi-813) | RISK-014 historical bounded obligation | RISK-014 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-814](ULTIMATE_TASKS.md#hapi-814) | RISK-015 historical bounded obligation | RISK-015 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-815](ULTIMATE_TASKS.md#hapi-815) | RISK-016 historical bounded obligation | RISK-016 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-816](ULTIMATE_TASKS.md#hapi-816) | RISK-017 historical bounded obligation | RISK-017 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-817](ULTIMATE_TASKS.md#hapi-817) | RISK-018 historical bounded obligation | RISK-018 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-818](ULTIMATE_TASKS.md#hapi-818) | RISK-019 historical bounded obligation | RISK-019 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-819](ULTIMATE_TASKS.md#hapi-819) | RISK-020 historical bounded obligation | RISK-020 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-017 — Analytics and Reporting

### 1. Description

Provide reproducible analytical lineage and governed role-specific reporting outputs.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `analytics`, `reporting`. No implemented LLM agent or automatic report synthesis was established by this audit.

### 4. Existing Capabilities

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Qualify actual computation/rendering/provider execution and reproducible outputs rather than treating run/artifact registration as execution. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: HPS-010, HPS-015, HPS-018 Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-148](ULTIMATE_TASKS.md#hapi-148) | scan analytics domain models | Scan all 7 analytics model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-165](ULTIMATE_TASKS.md#hapi-165) | scan reporting domain models | Scan all 4 reporting model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-190](ULTIMATE_TASKS.md#hapi-190) | remediate semantic review ReportDefinition | remediate semantic review ReportDefinition | P2 | — | VERIFIED | COMPLETED |
| [HAPI-194](ULTIMATE_TASKS.md#hapi-194) | remediate semantic review MetricEvaluationRun | remediate semantic review MetricEvaluationRun | P2 | — | VERIFIED | COMPLETED |
| [HAPI-203](ULTIMATE_TASKS.md#hapi-203) | remediate semantic review AnalyticsInsight | remediate semantic review AnalyticsInsight | P2 | — | VERIFIED | COMPLETED |
| [HAPI-204](ULTIMATE_TASKS.md#hapi-204) | remediate semantic review AnalyticsProjectionRun | remediate semantic review AnalyticsProjectionRun | P2 | — | VERIFIED | COMPLETED |
| [HAPI-206](ULTIMATE_TASKS.md#hapi-206) | remediate semantic review DigitalTwinReadinessAssessment | remediate semantic review DigitalTwinReadinessAssessment | P2 | — | VERIFIED | COMPLETED |
| [HAPI-223](ULTIMATE_TASKS.md#hapi-223) | remediate semantic review AnalyticsDatasetVersion | remediate semantic review AnalyticsDatasetVersion | P2 | — | VERIFIED | COMPLETED |
| [HAPI-224](ULTIMATE_TASKS.md#hapi-224) | remediate semantic review MetricValue | remediate semantic review MetricValue | P2 | — | VERIFIED | COMPLETED |
| [HAPI-239](ULTIMATE_TASKS.md#hapi-239) | remediate semantic review ReportRequest | remediate semantic review ReportRequest | P2 | — | VERIFIED | COMPLETED |
| [HAPI-240](ULTIMATE_TASKS.md#hapi-240) | register ReportRequest owner prerequisites | register ReportRequest owner prerequisites | P2 | — | VERIFIED | COMPLETED |
| [HAPI-251](ULTIMATE_TASKS.md#hapi-251) | remediate semantic review ReportRun | remediate semantic review ReportRun | P2 | — | VERIFIED | COMPLETED |
| [HAPI-287](ULTIMATE_TASKS.md#hapi-287) | remediate semantic review ReportOutputArtifact | remediate semantic review ReportOutputArtifact | P2 | — | VERIFIED | COMPLETED |
| [HAPI-323](ULTIMATE_TASKS.md#hapi-323) | review reporting ReportDefinition | review reporting ReportDefinition | P2 | — | VERIFIED | COMPLETED |
| [HAPI-327](ULTIMATE_TASKS.md#hapi-327) | review analytics AnalyticsDataset | review analytics AnalyticsDataset | P2 | — | VERIFIED | COMPLETED |
| [HAPI-328](ULTIMATE_TASKS.md#hapi-328) | review analytics MetricEvaluationRun | review analytics MetricEvaluationRun | P2 | — | VERIFIED | COMPLETED |
| [HAPI-335](ULTIMATE_TASKS.md#hapi-335) | review analytics AnalyticsInsight | review analytics AnalyticsInsight | P2 | — | VERIFIED | COMPLETED |
| [HAPI-336](ULTIMATE_TASKS.md#hapi-336) | review analytics AnalyticsProjectionRun | review analytics AnalyticsProjectionRun | P2 | — | VERIFIED | COMPLETED |
| [HAPI-337](ULTIMATE_TASKS.md#hapi-337) | review analytics DigitalTwinReadinessAssessment | review analytics DigitalTwinReadinessAssessment | P2 | — | VERIFIED | COMPLETED |
| [HAPI-352](ULTIMATE_TASKS.md#hapi-352) | review analytics AnalyticsDatasetVersion | review analytics AnalyticsDatasetVersion | P2 | — | VERIFIED | COMPLETED |
| [HAPI-353](ULTIMATE_TASKS.md#hapi-353) | review analytics MetricValue | review analytics MetricValue | P2 | — | VERIFIED | COMPLETED |
| [HAPI-367](ULTIMATE_TASKS.md#hapi-367) | review reporting ReportRequest | review reporting ReportRequest | P2 | — | VERIFIED | COMPLETED |
| [HAPI-378](ULTIMATE_TASKS.md#hapi-378) | review reporting ReportRun | review reporting ReportRun | P2 | — | VERIFIED | COMPLETED |
| [HAPI-420](ULTIMATE_TASKS.md#hapi-420) | review reporting ReportOutputArtifact | review reporting ReportOutputArtifact | P2 | — | VERIFIED | COMPLETED |
| [HAPI-511](ULTIMATE_TASKS.md#hapi-511) | reconstruct operational network state | Reconstruct a versioned time-coherent network state using approved physical methods; distinguish measured, estimated and unknown values and observability; retain… | P3 | HAPI-510 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-525](ULTIMATE_TASKS.md#hapi-525) | evaluate observed decision outcomes | Compare post-action telemetry with predicted outcomes in an approved observation window; retain actual action, confounders, confidence and attribution limits;… | P3 | HAPI-510, HAPI-524 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-526](ULTIMATE_TASKS.md#hapi-526) | govern learning from operational outcomes | Build provenance-controlled learning datasets from actual actions/outcomes; evaluate proposed detector, calibration or recommendation updates offline, version them… | P3 | HAPI-525 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-653](ULTIMATE_TASKS.md#hapi-653) | simplify boundary dto mapping | Generate the eight HRA-070-approved exact analytics request/command and summary/response mappings. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-706](ULTIMATE_TASKS.md#hapi-706) | Execute reproducible report rendering and artifact completion | Execute an admitted report template against an exact authorized input revision, persist immutable output content through Documents, and complete or fail the… | P2 | HAPI-604 | PARTIAL | BLOCKED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-018 — Engineering Baselines and Configuration

### 1. Description

Freeze exact, authorized multi-owner engineering inputs and reconstruct historical truth.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `topology`, `custody`, `simulation`, `configuration`. A baseline is separate from a numerical run snapshot; no source revision may mutate an accepted historical manifest.

### 4. Existing Capabilities

Independent immutable source stores and configuration definitions/values/feature-flag records exist. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Deliver complete baseline manifests, atomic freeze, compare/history/reconstruction and typed aggregate exchange after owner source publication. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: HPS-004, HPS-006, HPS-008, HPS-020 Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-534](ULTIMATE_TASKS.md#hapi-534) | define multi product property provisioning | define multi product property provisioning | P1 | — | VERIFIED | COMPLETED |
| [HAPI-540](ULTIMATE_TASKS.md#hapi-540) | define authoritative engineering baseline management | define authoritative engineering baseline management | P1 | — | VERIFIED | COMPLETED |
| [HAPI-541](ULTIMATE_TASKS.md#hapi-541) | separate independent monitoring and action evidence | separate independent monitoring and action evidence | P1 | — | VERIFIED | COMPLETED |
| [HAPI-542](ULTIMATE_TASKS.md#hapi-542) | define engineering foundation acceptance cases | define engineering foundation acceptance cases | P1 | — | VERIFIED | COMPLETED |
| [HAPI-604](ULTIMATE_TASKS.md#hapi-604) | Integrated closure: five authoritative-baseline/exchange packages | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-055; child tasks own implementation. five authoritative-baseline/exchange… | P1 | HAPI-609 | PARTIAL | BLOCKED |
| [HAPI-605](ULTIMATE_TASKS.md#hapi-605) | Baseline manifest drafts and completeness | Versioned manifest identity/draft and exact owner-reference/completeness validation. | P1 | HAPI-586, HAPI-593, HAPI-598 | NOT_IMPLEMENTED | BLOCKED |
| [HAPI-606](ULTIMATE_TASKS.md#hapi-606) | Atomic authoritative baseline freeze | Atomic authoritative freeze/publication and idempotent conflict handling. | P1 | HAPI-605 | NOT_IMPLEMENTED | BLOCKED |
| [HAPI-607](ULTIMATE_TASKS.md#hapi-607) | Exact baseline read, compare and reconstruction | Authorized exact read/history/effective selection, compare and reconstruction. | P1 | HAPI-606 | NOT_IMPLEMENTED | BLOCKED |
| [HAPI-609](ULTIMATE_TASKS.md#hapi-609) | Multi-owner baseline and exchange qualification | Multi-owner freeze/replay/rollback/concurrency and API/consumer qualification. | P1 | HAPI-608 | NOT_IMPLEMENTED | BLOCKED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-019 — Simulation and Digital Twin

### 1. Description

Preserve reproducible gas-first GZ2 orchestration and a future internal/external-engine evolution path.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `simulation`. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin.

### 4. Existing Capabilities

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: HPS-006, HPS-010, HPS-017, HPS-023 Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-167](ULTIMATE_TASKS.md#hapi-167) | scan simulation domain models | Scan all 6 simulation model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-185](ULTIMATE_TASKS.md#hapi-185) | remediate semantic review SimulationModel | remediate semantic review SimulationModel | P2 | — | VERIFIED | COMPLETED |
| [HAPI-217](ULTIMATE_TASKS.md#hapi-217) | remediate semantic review SimulationScenario | remediate semantic review SimulationScenario | P2 | — | VERIFIED | COMPLETED |
| [HAPI-218](ULTIMATE_TASKS.md#hapi-218) | register SimulationScenario reference prerequisite | register SimulationScenario reference prerequisite | P2 | — | VERIFIED | COMPLETED |
| [HAPI-235](ULTIMATE_TASKS.md#hapi-235) | remediate semantic review SimulationRun | remediate semantic review SimulationRun | P2 | — | VERIFIED | COMPLETED |
| [HAPI-236](ULTIMATE_TASKS.md#hapi-236) | register SimulationRun application prerequisite | register SimulationRun application prerequisite | P2 | — | VERIFIED | COMPLETED |
| [HAPI-272](ULTIMATE_TASKS.md#hapi-272) | remediate semantic review SimulationCandidateChange | remediate semantic review SimulationCandidateChange | P2 | — | VERIFIED | COMPLETED |
| [HAPI-273](ULTIMATE_TASKS.md#hapi-273) | remediate semantic review SimulationRecommendation | remediate semantic review SimulationRecommendation | P2 | — | VERIFIED | COMPLETED |
| [HAPI-319](ULTIMATE_TASKS.md#hapi-319) | review simulation SimulationModel | review simulation SimulationModel | P2 | — | VERIFIED | COMPLETED |
| [HAPI-348](ULTIMATE_TASKS.md#hapi-348) | review simulation SimulationScenario | review simulation SimulationScenario | P2 | — | VERIFIED | COMPLETED |
| [HAPI-365](ULTIMATE_TASKS.md#hapi-365) | review simulation SimulationRun | review simulation SimulationRun | P2 | — | VERIFIED | COMPLETED |
| [HAPI-376](ULTIMATE_TASKS.md#hapi-376) | review simulation SimulationOptimizationCandidate | review simulation SimulationOptimizationCandidate | P2 | — | VERIFIED | COMPLETED |
| [HAPI-402](ULTIMATE_TASKS.md#hapi-402) | review simulation SimulationCandidateChange | review simulation SimulationCandidateChange | P2 | — | VERIFIED | COMPLETED |
| [HAPI-403](ULTIMATE_TASKS.md#hapi-403) | review simulation SimulationRecommendation | review simulation SimulationRecommendation | P2 | — | VERIFIED | COMPLETED |
| [HAPI-505](ULTIMATE_TASKS.md#hapi-505) | establish immutable input manifest contracts | Implement immutable manifest, source-version identities/validity/origin and steady-state/transient metadata invariants under the exact six-file envelope below. | P2 | HAPI-504 | VERIFIED | COMPLETED |
| [HAPI-506](ULTIMATE_TASKS.md#hapi-506) | define versioned physical input payloads | Define validated typed network/fluid/equipment/initial-state and transient timeline payloads with units, reference basis and missing-data semantics; register exact… | P2 | HAPI-505 | VERIFIED | COMPLETED |
| [HAPI-507](ULTIMATE_TASKS.md#hapi-507) | resolve versioned owner input references | Implement bounded owner-exported topology/product/measurement queries and Simulation adapters; preserve eligibility/trust/ownership and reject unavailable physical… | P3 | HAPI-506 | PARTIAL | DEFERRED |
| [HAPI-508](ULTIMATE_TASKS.md#hapi-508) | capture reproducible network calculation inputs | Assemble coherent immutable payload/manifest captures, verify canonical hashes and evidence, persist reproducible revisions and reject incompatible/missing inputs;… | P3 | HAPI-507 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-513](ULTIMATE_TASKS.md#hapi-513) | resolve connected scope and boundary conditions | Select connected local/regional/full-network simulation scope; derive validated boundary conditions, propagate potential effects and expand scope or reject… | P3 | HAPI-504, HAPI-511 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-514](ULTIMATE_TASKS.md#hapi-514) | execute approved hydraulic solver | After HPR-P25-008A..F, establish governed execution of Hidra-owned gas engine for both modes with immutable inputs, product/mode checks, diagnostics, failure… | P3 | HAPI-513, HAPI-515 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-515](ULTIMATE_TASKS.md#hapi-515) | define internal gas engine numerical design | Specify gas equations/assumptions, gas-property methods, units/reference basis, boundary/initial conditions, supported network/equipment behavior, numerical… | P2 | HAPI-503, HAPI-504 | VERIFIED | COMPLETED |
| [HAPI-516](ULTIMATE_TASKS.md#hapi-516) | implement internal steady state gas solver | Implement verified steady-state pipe/network calculation against analytical or independent synthetic reference cases; expose residuals, conservation, validity and… | P2 | HAPI-515 | VERIFIED | COMPLETED |
| [HAPI-517](ULTIMATE_TASKS.md#hapi-517) | model gas network equipment behavior | Integrate approved compressor/valve/regulator behavior and current curve/configuration input contracts; test constraints, flow changes and effects across connected… | P3 | HAPI-504, HAPI-516 | PARTIAL | DEFERRED |
| [HAPI-518](ULTIMATE_TASKS.md#hapi-518) | implement internal transient gas solver | Implement time-dependent gas-network behavior with initial state and boundary/action timelines; verify conservation, spatial/time refinement, stability and… | P3 | HAPI-517 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-519](ULTIMATE_TASKS.md#hapi-519) | verify internal gas engine numerical behavior | Independently verify both engine modes against reproducible analytical/manufactured/reference cases, numerical refinement and adverse/failure cases; publish… | P3 | HAPI-516 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-520](ULTIMATE_TASKS.md#hapi-520) | integrate internal gas engine execution | Bind versioned engine to run lifecycle and immutable input/result contracts; verify actual execution, cancellation/timeouts/resource budgets and reproducibility;… | P3 | HAPI-513, HAPI-519 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-521](ULTIMATE_TASKS.md#hapi-521) | validate calibrated network physics | Calibrate and validate solver/estimator behavior against approved measured/reference cases and current equipment curves; publish per-product/mode tolerances,… | P3 | HAPI-511, HAPI-514 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-522](ULTIMATE_TASKS.md#hapi-522) | orchestrate issue triggered network runs | Automatically trigger governed real simulations from eligible issues using traceable state snapshots, debounce/deduplication, priorities and bounded resource… | P3 | HAPI-512 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-523](ULTIMATE_TASKS.md#hapi-523) | propose network validated operator decisions | Evaluate candidate operator actions against baseline and affected-network constraints; present expected benefits, adverse effects elsewhere, uncertainty and… | P3 | HAPI-521 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-535](ULTIMATE_TASKS.md#hapi-535) | define reproducible engineering baselines | define reproducible engineering baselines | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-536](ULTIMATE_TASKS.md#hapi-536) | define model and provider extension contracts | define model and provider extension contracts | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-537](ULTIMATE_TASKS.md#hapi-537) | define governed execution modes | define governed execution modes | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-538](ULTIMATE_TASKS.md#hapi-538) | define calibration readiness contracts | define calibration readiness contracts | P3 | — | NOT_VERIFIABLE | DEFERRED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-020 — Documents and Data Exchange

### 1. Description

Preserve document versions/content and typed engineering exchange with validation and provenance.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `documents`, `topology`, `configuration`. No production backfill or data import is selected. Documents do not own foreign engineering aggregates.

### 4. Existing Capabilities

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: HPS-004, HPS-018 Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-124](ULTIMATE_TASKS.md#hapi-124) | add legacy data migration roadmap | add legacy data migration roadmap | P2 | — | VERIFIED | COMPLETED |
| [HAPI-125](ULTIMATE_TASKS.md#hapi-125) | inventory hyflo source files and provenance | inventory hyflo source files and provenance | P2 | — | VERIFIED | COMPLETED |
| [HAPI-126](ULTIMATE_TASKS.md#hapi-126) | inventory hidra target models and schema | inventory hidra target models and schema | P2 | — | VERIFIED | COMPLETED |
| [HAPI-127](ULTIMATE_TASKS.md#hapi-127) | classify source datasets and import eligibility | classify source datasets and import eligibility | P2 | — | VERIFIED | COMPLETED |
| [HAPI-128](ULTIMATE_TASKS.md#hapi-128) | map legacy fields to hidra contracts | map legacy fields to hidra contracts | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-129](ULTIMATE_TASKS.md#hapi-129) | approve canonical sources and migration scope | approve canonical sources and migration scope | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-130](ULTIMATE_TASKS.md#hapi-130) | define algerian geographic normalization rules | define algerian geographic normalization rules | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-131](ULTIMATE_TASKS.md#hapi-131) | define pipeline and engineering normalization rules | define pipeline and engineering normalization rules | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-132](ULTIMATE_TASKS.md#hapi-132) | define identifier and dependency mapping | define identifier and dependency mapping | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-133](ULTIMATE_TASKS.md#hapi-133) | approve reproducible provisioning design | approve reproducible provisioning design | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-134](ULTIMATE_TASKS.md#hapi-134) | add sanitized source fixtures and mapping contracts | add sanitized source fixtures and mapping contracts | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-135](ULTIMATE_TASKS.md#hapi-135) | add deterministic extraction and quarantine | add deterministic extraction and quarantine | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-136](ULTIMATE_TASKS.md#hapi-136) | add geographic and reference transformations | add geographic and reference transformations | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-137](ULTIMATE_TASKS.md#hapi-137) | add pipeline and facility transformations | add pipeline and facility transformations | P3 | — | PARTIAL | DEFERRED |
| [HAPI-138](ULTIMATE_TASKS.md#hapi-138) | add dry-run and idempotent import orchestration | add dry-run and idempotent import orchestration | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-139](ULTIMATE_TASKS.md#hapi-139) | provision approved geographic and reference datasets | provision approved geographic and reference datasets | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-140](ULTIMATE_TASKS.md#hapi-140) | provision approved physical network master data | provision approved physical network master data | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-141](ULTIMATE_TASKS.md#hapi-141) | verify master-data imports and replay safety | verify master-data imports and replay safety | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-142](ULTIMATE_TASKS.md#hapi-142) | disposition operational and historical sources | disposition operational and historical sources | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-143](ULTIMATE_TASKS.md#hapi-143) | add end-to-end reconciliation and security gates | add end-to-end reconciliation and security gates | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-144](ULTIMATE_TASKS.md#hapi-144) | document import operations and rollback | document import operations and rollback | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-145](ULTIMATE_TASKS.md#hapi-145) | finalize data migration acceptance | finalize data migration acceptance | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-153](ULTIMATE_TASKS.md#hapi-153) | scan documents domain models | Scan all 4 documents model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-183](ULTIMATE_TASKS.md#hapi-183) | remediate semantic review DocumentStorageObject | remediate semantic review DocumentStorageObject | P2 | — | VERIFIED | COMPLETED |
| [HAPI-184](ULTIMATE_TASKS.md#hapi-184) | amend DocumentStorageObject remediation scope | amend DocumentStorageObject remediation scope | P2 | — | VERIFIED | COMPLETED |
| [HAPI-261](ULTIMATE_TASKS.md#hapi-261) | remediate semantic review Document | remediate semantic review Document | P2 | — | VERIFIED | COMPLETED |
| [HAPI-262](ULTIMATE_TASKS.md#hapi-262) | remediate semantic review DocumentVersion | remediate semantic review DocumentVersion | P2 | — | VERIFIED | COMPLETED |
| [HAPI-278](ULTIMATE_TASKS.md#hapi-278) | remediate semantic review DocumentTargetLink | remediate semantic review DocumentTargetLink | P2 | — | VERIFIED | COMPLETED |
| [HAPI-318](ULTIMATE_TASKS.md#hapi-318) | review documents DocumentStorageObject | review documents DocumentStorageObject | P2 | — | VERIFIED | COMPLETED |
| [HAPI-389](ULTIMATE_TASKS.md#hapi-389) | review documents Document | review documents Document | P2 | — | VERIFIED | COMPLETED |
| [HAPI-390](ULTIMATE_TASKS.md#hapi-390) | review documents DocumentVersion | review documents DocumentVersion | P2 | — | VERIFIED | COMPLETED |
| [HAPI-408](ULTIMATE_TASKS.md#hapi-408) | review documents DocumentTargetLink | review documents DocumentTargetLink | P2 | — | VERIFIED | COMPLETED |
| [HAPI-591](ULTIMATE_TASKS.md#hapi-591) | Typed local engineering exchange and capabilities | Supported local typed import/export and per-editor capability/completeness closure. | P2 | HAPI-590 | NOT_IMPLEMENTED | BLOCKED |
| [HAPI-608](ULTIMATE_TASKS.md#hapi-608) | Aggregate typed engineering exchange | Supported aggregate engineering import/export, dry-run and conflict-safe replay. | P2 | HAPI-607 | NOT_IMPLEMENTED | BLOCKED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-021 — Audit and Traceability

### 1. Description

Preserve accountable actor, source, revision, approval and export evidence across operations.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `audit`. Do not conflate a stored audit export request with a generated external artifact or successful delivery.

### 4. Existing Capabilities

Audit events/access/export records and owner validation contracts exist with secret/payload guards. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Reuse immutable audit boundaries for engineering publication and observation/action/outcome qualification; prove denied paths do not leak protected metadata. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: No additional cross-phase edge independently established; preserve original bounded prerequisites in ULTIMATE_TASKS.md. Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-150](ULTIMATE_TASKS.md#hapi-150) | scan audit domain models | Scan all 4 audit model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-277](ULTIMATE_TASKS.md#hapi-277) | remediate semantic review AuditExportRequest | remediate semantic review AuditExportRequest | P2 | — | VERIFIED | COMPLETED |
| [HAPI-289](ULTIMATE_TASKS.md#hapi-289) | remediate semantic review AuditEvent | remediate semantic review AuditEvent | P2 | — | VERIFIED | COMPLETED |
| [HAPI-295](ULTIMATE_TASKS.md#hapi-295) | remediate semantic review AuditAccessRecord | remediate semantic review AuditAccessRecord | P2 | — | VERIFIED | COMPLETED |
| [HAPI-296](ULTIMATE_TASKS.md#hapi-296) | remediate semantic review AuditBeforeAfterValue | remediate semantic review AuditBeforeAfterValue | P2 | — | VERIFIED | COMPLETED |
| [HAPI-407](ULTIMATE_TASKS.md#hapi-407) | review audit AuditExportRequest | review audit AuditExportRequest | P2 | — | VERIFIED | COMPLETED |
| [HAPI-422](ULTIMATE_TASKS.md#hapi-422) | review audit AuditEvent | review audit AuditEvent | P2 | — | VERIFIED | COMPLETED |
| [HAPI-428](ULTIMATE_TASKS.md#hapi-428) | review audit AuditAccessRecord | review audit AuditAccessRecord | P2 | — | VERIFIED | COMPLETED |
| [HAPI-429](ULTIMATE_TASKS.md#hapi-429) | review audit AuditBeforeAfterValue | review audit AuditBeforeAfterValue | P2 | — | VERIFIED | COMPLETED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-022 — Integration and Interoperability

### 1. Description

Expose stable consumer contracts and govern external identities, exchange and recovery.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `integration`, `notification`, `telemetry`. No direct foreign-table write, live OT access or engine interchangeability is inferred.

### 4. Existing Capabilities

External-system/job/exchange-message registration, OpenAPI publication and HidraWEB artifact consumption exist. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Qualify actual protocol adapters, domain event publishers and external providers; preserve parked MQTT/Sparkplug/CPM interfaces. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: No additional cross-phase edge independently established; preserve original bounded prerequisites in ULTIMATE_TASKS.md. Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-104](ULTIMATE_TASKS.md#hapi-104) | add sparkplug boundary contracts | Define Integration-side external identities and mapping of Group/Edge Node/Device/Metric to Telemetry references. | P3 | — | PARTIAL | DEFERRED |
| [HAPI-105](ULTIMATE_TASKS.md#hapi-105) | add mqtt sparkplug adapter | Add the approved MQTT client/configuration and Sparkplug decoding adapter. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-106](ULTIMATE_TASKS.md#hapi-106) | verify mqtt telemetry handoff | Verify MQTT/Sparkplug adapter hands accepted measurements to Telemetry through the approved application boundary. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-157](ULTIMATE_TASKS.md#hapi-157) | scan integration domain models | Scan all 4 integration model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-191](ULTIMATE_TASKS.md#hapi-191) | remediate semantic review IntegrationJobRun | remediate semantic review IntegrationJobRun | P2 | — | VERIFIED | COMPLETED |
| [HAPI-237](ULTIMATE_TASKS.md#hapi-237) | remediate semantic review ExternalSystem | remediate semantic review ExternalSystem | P2 | — | VERIFIED | COMPLETED |
| [HAPI-238](ULTIMATE_TASKS.md#hapi-238) | register ExternalSystem owner prerequisite | register ExternalSystem owner prerequisite | P2 | — | VERIFIED | COMPLETED |
| [HAPI-250](ULTIMATE_TASKS.md#hapi-250) | remediate semantic review IntegrationExchangeMessage | remediate semantic review IntegrationExchangeMessage | P2 | — | VERIFIED | COMPLETED |
| [HAPI-265](ULTIMATE_TASKS.md#hapi-265) | remediate semantic review IntegrationDeadLetterRecord | remediate semantic review IntegrationDeadLetterRecord | P2 | — | VERIFIED | COMPLETED |
| [HAPI-324](ULTIMATE_TASKS.md#hapi-324) | review integration IntegrationJobRun | review integration IntegrationJobRun | P2 | — | VERIFIED | COMPLETED |
| [HAPI-366](ULTIMATE_TASKS.md#hapi-366) | review integration ExternalSystem | review integration ExternalSystem | P2 | — | VERIFIED | COMPLETED |
| [HAPI-377](ULTIMATE_TASKS.md#hapi-377) | review integration IntegrationExchangeMessage | review integration IntegrationExchangeMessage | P2 | — | VERIFIED | COMPLETED |
| [HAPI-394](ULTIMATE_TASKS.md#hapi-394) | review integration IntegrationDeadLetterRecord | review integration IntegrationDeadLetterRecord | P2 | — | VERIFIED | COMPLETED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-023 — Platform Architecture and Infrastructure

### 1. Description

Keep one modular Java/Spring application with inward dependencies and explicit technical infrastructure.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `kernel`, `platform`, `configuration`. No architecture restart, new module root, microservice extraction or persistence-ownership refactor selected.

### 4. Existing Capabilities

One Maven build, pure domain/kernel boundaries, mapped persistence adapters, observability/realtime/configuration plumbing and ArchUnit enforcement exist. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Maintain architecture/contract/schema documentation and explicit exception controls; MySQL is an additional proposal only, unsupported by current driver/migration proof. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: HPS-004, HPS-019, HPS-025 Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-047](ULTIMATE_TASKS.md#hapi-047) | add correction 02 roadmap | Add this executable correction roadmap | P2 | — | VERIFIED | COMPLETED |
| [HAPI-053](ULTIMATE_TASKS.md#hapi-053) | audit remaining user-facing type enums | Classify remaining domain/value type enums and references | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-055](ULTIMATE_TASKS.md#hapi-055) | standardize domain name value objects | Align name/title/designation value objects with trilingual convention | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-056](ULTIMATE_TASKS.md#hapi-056) | standardize multilingual columns | Align JPA entities and migrations with trilingual label fields | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-057](ULTIMATE_TASKS.md#hapi-057) | complete REST parameter documentation | Add missing @Parameter, examples, and API response documentation | P2 | — | VERIFIED | COMPLETED |
| [HAPI-058](ULTIMATE_TASKS.md#hapi-058) | decide domain schema annotation policy | Make the domain @Schema policy explicit and consistent | P2 | — | VERIFIED | COMPLETED |
| [HAPI-059](ULTIMATE_TASKS.md#hapi-059) | add domain and persistence schema diagrams | Add Mermaid domain and persistence diagrams | P2 | — | VERIFIED | COMPLETED |
| [HAPI-060](ULTIMATE_TASKS.md#hapi-060) | rewrite project README | Replace minimal README with full developer onboarding documentation | P2 | — | VERIFIED | COMPLETED |
| [HAPI-061](ULTIMATE_TASKS.md#hapi-061) | add contribution and execution rules | Add CONTRIBUTING.md with module and review rules | P2 | — | VERIFIED | COMPLETED |
| [HAPI-062](ULTIMATE_TASKS.md#hapi-062) | add correction 02 guardrails | Add/extend architecture tests for deprecated imports and module boundaries | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-063](ULTIMATE_TASKS.md#hapi-063) | finalize correction 02 checklist | Record final validation status and checklist | P2 | — | VERIFIED | COMPLETED |
| [HAPI-064](ULTIMATE_TASKS.md#hapi-064) | add repository correction roadmap | Add correction roadmap. | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-065](ULTIMATE_TASKS.md#hapi-065) | remove generated bin roadmap artifacts | Remove committed generated bin/docs/ and ignore bin/. | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-067](ULTIMATE_TASKS.md#hapi-067) | define controlled vocabulary policy | Define enum-vs-catalog policy. | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-078](ULTIMATE_TASKS.md#hapi-078) | add controlled vocabulary guardrails | Add test blocking forbidden business taxonomy enum declarations. | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-081](ULTIMATE_TASKS.md#hapi-081) | validate corrected baseline | Validation-only task requiring local Maven/Flyway/Testcontainers execution. | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-084](ULTIMATE_TASKS.md#hapi-084) | replace measurement next-module references with telemetry | Correct next-module naming from measurement/MES to telemetry/TEL. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-097](ULTIMATE_TASKS.md#hapi-097) | audit extended industrial capabilities | Create the live-repository capability audit and this roadmap only. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-098](ULTIMATE_TASKS.md#hapi-098) | decide industrial extension boundaries | Add ADRs for MQTT/Sparkplug ownership, high-volume telemetry persistence, PostGIS/route ownership, gRPC service boundary, database-extension provisioning, and B31G… | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-099](ULTIMATE_TASKS.md#hapi-099) | define industrial extension contracts | Specify versioned cross-boundary contracts and exact file allowlists for first implementation tasks, including idempotency and failure semantics. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-146](ULTIMATE_TASKS.md#hapi-146) | add module model scan roadmap | Create this roadmap only. No model extraction. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-151](ULTIMATE_TASKS.md#hapi-151) | scan configuration domain models | Scan all 3 configuration model files. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-171](ULTIMATE_TASKS.md#hapi-171) | reconcile module model catalogue | Reconcile all 24 module outputs against the pinned 123-file baseline; resolve extraction exceptions and duplicate/missing rows. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-172](ULTIMATE_TASKS.md#hapi-172) | generate final model field workbook | Generate the consolidated Excel document from the reconciled catalogue. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-173](ULTIMATE_TASKS.md#hapi-173) | validate and finalize model field catalogue | Validate workbook readability, row/model/module counts, provenance, and final delivery evidence. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-174](ULTIMATE_TASKS.md#hapi-174) | establish semantic remediation roadmap | Create this roadmap from the completed HMS-006 reconciliation; reserve one production task per REVISE model; gate HMS-007 behind HMR; no production changes. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-186](ULTIMATE_TASKS.md#hapi-186) | register SimulationModel topology owner-contract prerequisite | register SimulationModel topology owner-contract prerequisite | P2 | — | VERIFIED | COMPLETED |
| [HAPI-193](ULTIMATE_TASKS.md#hapi-193) | register LeakCandidate topology owner-contract prerequisite | register LeakCandidate topology owner-contract prerequisite | P2 | — | VERIFIED | COMPLETED |
| [HAPI-195](ULTIMATE_TASKS.md#hapi-195) | register MetricEvaluationRun scope and lifecycle prerequisite | register MetricEvaluationRun scope and lifecycle prerequisite | P2 | — | VERIFIED | COMPLETED |
| [HAPI-196](ULTIMATE_TASKS.md#hapi-196) | remediate semantic review ConfigurationDefinition | remediate semantic review ConfigurationDefinition | P2 | — | VERIFIED | COMPLETED |
| [HAPI-202](ULTIMATE_TASKS.md#hapi-202) | register PipelineSystem classification catalog prerequisite | register PipelineSystem classification catalog prerequisite | P2 | — | VERIFIED | COMPLETED |
| [HAPI-205](ULTIMATE_TASKS.md#hapi-205) | register AnalyticsProjectionRun reproducibility prerequisite | register AnalyticsProjectionRun reproducibility prerequisite | P2 | — | VERIFIED | COMPLETED |
| [HAPI-207](ULTIMATE_TASKS.md#hapi-207) | remediate semantic review FeatureFlag | remediate semantic review FeatureFlag | P2 | — | VERIFIED | COMPLETED |
| [HAPI-210](ULTIMATE_TASKS.md#hapi-210) | register ReportingLine catalog and matrix-policy prerequisite | register ReportingLine catalog and matrix-policy prerequisite | P2 | — | VERIFIED | COMPLETED |
| [HAPI-214](ULTIMATE_TASKS.md#hapi-214) | register TopologyConnection catalog prerequisite | register TopologyConnection catalog prerequisite | P2 | — | VERIFIED | COMPLETED |
| [HAPI-222](ULTIMATE_TASKS.md#hapi-222) | register Facility Party contract guardrail prerequisite | register Facility Party contract guardrail prerequisite | P2 | — | VERIFIED | COMPLETED |
| [HAPI-225](ULTIMATE_TASKS.md#hapi-225) | remediate semantic review ConfigurationValue | remediate semantic review ConfigurationValue | P2 | — | VERIFIED | COMPLETED |
| [HAPI-243](ULTIMATE_TASKS.md#hapi-243) | register RiskRegister audit taxonomy prerequisite | register RiskRegister audit taxonomy prerequisite | P2 | — | VERIFIED | COMPLETED |
| [HAPI-301](ULTIMATE_TASKS.md#hapi-301) | verify semantic remediation closure | After all model tasks resolve, run the repository-required full exact-SHA verification, validate Flyway replay/architecture/OpenAPI gates, and prove the 104-source… | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-302](ULTIMATE_TASKS.md#hapi-302) | close semantic remediation programme | Reconcile HMR outcomes, record completed/skipped/deferred obligations, and explicitly determine whether the HMS-007 completion gate is satisfied. No HMS-007… | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-303](ULTIMATE_TASKS.md#hapi-303) | establish target model semantic review roadmap | Create this roadmap and amend HDP so HDP-004 is explicitly paused behind the semantic-review prerequisite. No dependency analysis or production code changes. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-305](ULTIMATE_TASKS.md#hapi-305) | classify target model dependencies | Resolve candidate edges into the dependency classifications in section 4; separate true dependencies from snapshots/codes and record unresolved edges. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-306](ULTIMATE_TASKS.md#hapi-306) | validate dependency graph and cycles | Reconcile graph against persistence/contracts, identify strongly connected components, missing targets, contradictory edges and cross-module boundary concerns. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-307](ULTIMATE_TASKS.md#hapi-307) | generate dependency ordered model review register | Compute deterministic review levels/order and generate the 123-model review register with HMSR-001…HMSR-123 codes and exact per-model commit messages. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-308](ULTIMATE_TASKS.md#hapi-308) | reconcile interactive model decisions | After all HMSR tasks are resolved, reconcile APPROVED/REVISE/DEFER/REMOVE decisions, outstanding corrections, unresolved cycles and dependency impacts. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-309](ULTIMATE_TASKS.md#hapi-309) | finalize approved target model semantic baseline | Publish the final target-model semantic baseline only when every model has a resolved disposition and any required model corrections are implemented or explicitly… | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-310](ULTIMATE_TASKS.md#hapi-310) | align provisioning roadmap to semantic model baseline | Amend HDP target assumptions, record the approved HMS baseline, and determine whether HDP-004 may resume. Does not itself classify source data or start HDP-005. | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-329](ULTIMATE_TASKS.md#hapi-329) | review configuration ConfigurationDefinition | review configuration ConfigurationDefinition | P2 | — | VERIFIED | COMPLETED |
| [HAPI-338](ULTIMATE_TASKS.md#hapi-338) | review configuration FeatureFlag | review configuration FeatureFlag | P2 | — | VERIFIED | COMPLETED |
| [HAPI-354](ULTIMATE_TASKS.md#hapi-354) | review configuration ConfigurationValue | review configuration ConfigurationValue | P2 | — | VERIFIED | COMPLETED |
| [HAPI-481](ULTIMATE_TASKS.md#hapi-481) | complete canonical documentation controls | Complete canonical doc/ governance/index structure and status metadata; preserve docs/ as evidence. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-482](ULTIMATE_TASKS.md#hapi-482) | establish canonical architecture set | Create current/target-separated system context, architecture overview, bounded-context map, Hexagonal boundaries, module boundaries, cross-module contracts and… | P2 | HAPI-481 | VERIFIED | COMPLETED |
| [HAPI-483](ULTIMATE_TASKS.md#hapi-483) | establish ubiquitous language baseline | Create UBIQUITOUS_LANGUAGE.md, domain ownership and focused topology/telemetry/alarm-incident-leak/assets-integrity/simulation-analytics-AI semantic documents. | P2 | HAPI-481 | VERIFIED | COMPLETED |
| [HAPI-484](ULTIMATE_TASKS.md#hapi-484) | add canonical module documentation | Create one current-state doc/modules/<module>.md for each of the 24 implemented modules; do not create current-state module docs for agents/environment/otsecurity. | P2 | HAPI-482 | VERIFIED | COMPLETED |
| [HAPI-485](ULTIMATE_TASKS.md#hapi-485) | establish versioned api contract | Complete source-backed API conventions and runtime error documentation under the bounded audit amendment below; preserve the generated-envelope limitation and… | P2 | HAPI-459 | VERIFIED | COMPLETED |
| [HAPI-486](ULTIMATE_TASKS.md#hapi-486) | establish canonical database documentation | Complete the current physical table/column dictionary and explicit ownership/FK/migration governance under the bounded audit amendment below; preserve applied SQL… | P2 | HAPI-481 | VERIFIED | COMPLETED |
| [HAPI-487](ULTIMATE_TASKS.md#hapi-487) | reconcile remaining semantic obligations | Inventory unresolved HMR/HMSR obligations against exact current source; mark each as completed, still required, blocked, or superseded with evidence. | P2 | HAPI-483 | VERIFIED | COMPLETED |
| [HAPI-488](ULTIMATE_TASKS.md#hapi-488) | continue reconciled semantic remediation | Execute still-required semantic remediation in dependency order using revalidated HMSR obligations; do not restart completed HMRs without regression evidence. | P2 | HAPI-487 | VERIFIED | COMPLETED |
| [HAPI-489](ULTIMATE_TASKS.md#hapi-489) | canonicalize semantic decisions | Transfer permanent semantic decisions from legacy review/roadmaps into doc/domain/ and doc/modules/, then preserve legacy files as execution history. | P2 | HAPI-488 | VERIFIED | COMPLETED |
| [HAPI-490](ULTIMATE_TASKS.md#hapi-490) | establish data governance baseline | Create data governance, retention/archival, provenance and legacy-data migration documents without inventing retention values. | P2 | HAPI-481 | VERIFIED | COMPLETED |
| [HAPI-493](ULTIMATE_TASKS.md#hapi-493) | close P2 canonical governance | Reverify all twelve P2 checks after HPR-P2-005/006 corrections, canonical status/index consistency and exact-head CI; close P2 only with 12/12 VERIFIED. | P2 | HAPI-481 | VERIFIED | COMPLETED |
| [HAPI-498](ULTIMATE_TASKS.md#hapi-498) | define spatial persistence architecture | Approve spatial architecture: route ownership, geometry model, CRS/SRID, KP reconciliation and query requirements; current scalar/KP storage remains authoritative… | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-499](ULTIMATE_TASKS.md#hapi-499) | introduce postgis route persistence | Provision PostGIS and authoritative route geometry only after HPR-P3-005 approval; add appropriate spatial indexes. | P3 | HAPI-498 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-500](ULTIMATE_TASKS.md#hapi-500) | verify spatial route semantics | Reconcile route geometry with existing KP/linear referencing; verify SRID, geometry validity, route/KP consistency and query performance. | P3 | HAPI-499 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-501](ULTIMATE_TASKS.md#hapi-501) | reconcile deferred industrial capabilities | Keep MQTT/Sparkplug and other audit-classified industrial extensions deferred until a separately approved requirement exists; update CURRENT status only after… | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-502](ULTIMATE_TASKS.md#hapi-502) | register phase 2.5 operational digital twin | Register the owner-defined operational twin cycle, ordered tasks, prerequisites, acceptance gates and next requirements preflight. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-503](ULTIMATE_TASKS.md#hapi-503) | define requirements and execution preflight | Inventory exact-source gaps and owners; specify product/use-case coverage, data needs, solver integration options, steady-state/transient scope,… | P2 | HAPI-502 | VERIFIED | COMPLETED |
| [HAPI-527](ULTIMATE_TASKS.md#hapi-527) | verify operator visualization and decision workflow | Demonstrate operator visualization, issue-to-run explanation, candidate comparisons, decision capture and outcome views with the selected frontend; record separate… | P3 | HAPI-509, HAPI-522 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-528](ULTIMATE_TASKS.md#hapi-528) | verify operational decision feedback cycle | Execute end-to-end historical replay and governed pilot cases including adverse downstream effects, scope expansion, unavailable solver, poor/stale data,… | P3 | HAPI-527 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-529](ULTIMATE_TASKS.md#hapi-529) | close phase 2.5 operational twin verification | Independently audit all P2.5 gates against actual execution and per-product/mode evidence; close only after exact-head applicable CI and operator acceptance, with… | P3 | HAPI-503, HAPI-505, HAPI-515 | NOT_VERIFIABLE | DEFERRED |
| [HAPI-530](ULTIMATE_TASKS.md#hapi-530) | define engineering provisioning contracts | define engineering provisioning contracts | P2 | — | VERIFIED | COMPLETED |
| [HAPI-539](ULTIMATE_TASKS.md#hapi-539) | define engineering acceptance and product qualification | define engineering acceptance and product qualification | P3 | — | NOT_VERIFIABLE | DEFERRED |
| [HAPI-544](ULTIMATE_TASKS.md#hapi-544) | HPR-P25-032 historical bounded obligation | HPR-P25-032 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-545](ULTIMATE_TASKS.md#hapi-545) | HPR-P25-033 historical bounded obligation | HPR-P25-033 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-546](ULTIMATE_TASKS.md#hapi-546) | HPR-P25-034 historical bounded obligation | HPR-P25-034 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-548](ULTIMATE_TASKS.md#hapi-548) | Separate owner instruction after this registration's documentation acceptance | Separate owner instruction after this registration's documentation acceptance | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-550](ULTIMATE_TASKS.md#hapi-550) | HPR-P25-038 historical bounded obligation | HPR-P25-038 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-554](ULTIMATE_TASKS.md#hapi-554) | HPR-P25-042 historical bounded obligation | HPR-P25-042 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-628](ULTIMATE_TASKS.md#hapi-628) | record forensic remediation baseline | Version this repository remediation roadmap and the evidence-backed forensic baseline. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-629](ULTIMATE_TASKS.md#hapi-629) | enforce repository structural guardrails | Extend architecture/static-analysis guardrails for cross-module private-package imports, existing layer rules, duplicate JPA tables/columns, and deliberate… | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-630](ULTIMATE_TASKS.md#hapi-630) | detect domain mapper field drift | Add domain-to-JPA mapper completeness/round-trip protection, beginning with Organization Employee and explicit exclusions for derived/compatibility state. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-631](ULTIMATE_TASKS.md#hapi-631) | optimize repository verification workflow | Remove redundant Maven executions from GitHub Actions, retain one wrapper-based clean verify, keep PostgreSQL/OpenAPI verification for code changes, and skip the… | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-637](ULTIMATE_TASKS.md#hapi-637) | Preflight and backfill deterministic legacy email/mobile values into contact points; quarantine ambiguity. | Preflight and backfill deterministic legacy email/mobile values into contact points; quarantine ambiguity. | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-640](ULTIMATE_TASKS.md#hapi-640) | decide domain event and outbox strategy | Choose one canonical path: implement events/outbox or remove fictional scaffolding. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-641](ULTIMATE_TASKS.md#hapi-641) | unify module domain event contract | If events are retained, reconcile module event interfaces with kernel DomainEvent, event IDs, timestamps, and version metadata. | P2 | — | IMPLEMENTED | SUPERSEDED |
| [HAPI-642](ULTIMATE_TASKS.md#hapi-642) | remove unused event scaffolding | If events are not currently required, remove unreferenced module event records/publishers and unused outbox contracts after consumer verification. | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-643](ULTIMATE_TASKS.md#hapi-643) | implement transactional outbox pipeline | Implement serializer, outbox repository adapter, publisher, Spring wiring, and transactional tests. | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-644](ULTIMATE_TASKS.md#hapi-644) | publish domain events from state transitions | Emit retained events from real state-changing use cases and remove replaced in-memory publisher stubs. | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-645](ULTIMATE_TASKS.md#hapi-645) | classify static orphan candidates | Classify all 210 zero-incoming candidates as DELETE or evidence-backed KEEP. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-646](ULTIMATE_TASKS.md#hapi-646) | remove confirmed orphan types | Delete only HRA-040 DELETE candidates and update imports/tests/docs. | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-647](ULTIMATE_TASKS.md#hapi-647) | classify required invariant gaps | Review domain/JPA nullability mismatches and classify true business requirements vs migration/persistence-only constraints. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-648](ULTIMATE_TASKS.md#hapi-648) | enforce classified domain invariants | Single consolidated repository-wide task / single commit. Apply every HRA-050-approved invariant gap across all affected modules in one batch: required… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-649](ULTIMATE_TASKS.md#hapi-649) | classify anemic persistence mirrors | Classify the 393 behaviorless exact domain/JPA mirrors as REAL DOMAIN, READ/PERSISTENCE MODEL, or BOUNDARY MODEL. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-650](ULTIMATE_TASKS.md#hapi-650) | simplify classified domain persistence mirrors | Single consolidated repository-wide task / single commit. Apply every HRA-060-approved mirror simplification across all affected modules in one batch. Preserve… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-651](ULTIMATE_TASKS.md#hapi-651) | classify duplicate boundary contracts | Review the 65 exact Request/Command and 59 exact Response/SummaryDto pairs. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-654](ULTIMATE_TASKS.md#hapi-654) | simplify remaining boundary dto mappings | Consolidated execution of the remaining HRA-07x module slices, explicitly authorized as one commit: generate only the 114 still-approved exact pairs across 22… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-655](ULTIMATE_TASKS.md#hapi-655) | review duplicate enum vocabularies | Make semantic KEEP/MERGE/DELETE decisions; identical constants alone do not justify merging. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-656](ULTIMATE_TASKS.md#hapi-656) | remove redundant enum vocabularies | Apply only approved merges/deletions with persistence/API compatibility handling. | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-661](ULTIMATE_TASKS.md#hapi-661) | define domain enum exposure policy | Decide whether REST exposure of domain enums is an intentional wire contract; define mapping policy. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-662](ULTIMATE_TASKS.md#hapi-662) | inventory scalar reference integrity | Classify non-primary entity Id fields as same-module references, cross-module stable references, historical snapshots, external IDs, or non-relational identifiers. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-663](ULTIMATE_TASKS.md#hapi-663) | enforce classified internal reference integrity | Single consolidated repository-wide task / single commit. Apply every HRA-110-approved same-module integrity repair across all affected modules using fail-closed… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-664](ULTIMATE_TASKS.md#hapi-664) | close forensic remediation baseline | Re-run the same forensic methodology and compare against the baseline. After HRA-121 and HRA-122, rerun this same closure task against the new live head; do not… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-665](ULTIMATE_TASKS.md#hapi-665) | remove permission scope domain-record exposure | Replace identity.api.rest.request.EvaluatePermissionRequest.scope : AuthorizationScope with an API-owned boundary representation for authorization scope. Preserve… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-690](ULTIMATE_TASKS.md#hapi-690) | add kernel roadmap | Add this roadmap file | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-691](ULTIMATE_TASKS.md#hapi-691) | add kernel package skeleton | Add production package-info.java files only | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-692](ULTIMATE_TASKS.md#hapi-692) | add domain exception contracts | Add generic domain exceptions | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-693](ULTIMATE_TASKS.md#hapi-693) | add domain model contracts | Add AggregateRoot, Entity, ValueObject | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-694](ULTIMATE_TASKS.md#hapi-694) | add identity and tracing value objects | Add CorrelationId, RequestId, ActorId, OrganizationScopeId | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-695](ULTIMATE_TASKS.md#hapi-695) | add temporal range value objects | Add DateRange, TimeRange | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-696](ULTIMATE_TASKS.md#hapi-696) | add domain event contracts | Add DomainEvent, DomainEventId | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-697](ULTIMATE_TASKS.md#hapi-697) | add command and query markers | Add Command, Query | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-698](ULTIMATE_TASKS.md#hapi-698) | add pagination primitives | Add PageRequest, PageResult, SortDirection | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-699](ULTIMATE_TASKS.md#hapi-699) | add operation result primitives | Add OperationResult, ResultStatus | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-700](ULTIMATE_TASKS.md#hapi-700) | add API error primitives | Add ApiErrorCode, ApiErrorResponse, ValidationErrorDetail | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-701](ULTIMATE_TASKS.md#hapi-701) | add API response primitives | Add ApiResponse, PagedApiResponse | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-702](ULTIMATE_TASKS.md#hapi-702) | add kernel unit tests | Add unit tests for kernel files | P2 | — | VERIFIED | COMPLETED |
| [HAPI-703](ULTIMATE_TASKS.md#hapi-703) | add kernel architecture guardrail | Add ArchUnit boundary test if ArchUnit exists | P2 | — | VERIFIED | COMPLETED |
| [HAPI-704](ULTIMATE_TASKS.md#hapi-704) | finalize kernel checklist | Update this roadmap with final status | P2 | — | VERIFIED | COMPLETED |
| [HAPI-779](ULTIMATE_TASKS.md#hapi-779) | add platform roadmap | Add this roadmap file | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-780](ULTIMATE_TASKS.md#hapi-780) | add platform package skeleton | Add production package-info.java files only | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-781](ULTIMATE_TASKS.md#hapi-781) | add typed platform configuration properties | Add platform configuration properties and base config | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-782](ULTIMATE_TASKS.md#hapi-782) | add observability correlation support | Add correlation/request ID infrastructure | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-783](ULTIMATE_TASKS.md#hapi-783) | add logging context and masking support | Add logging context helpers and sensitive value masking | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-784](ULTIMATE_TASKS.md#hapi-784) | add global exception handling foundation | Add API error factory and exception handler | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-785](ULTIMATE_TASKS.md#hapi-785) | add security authentication foundation | Add Spring Security configuration and authentication error handler | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-786](ULTIMATE_TASKS.md#hapi-786) | add security context support | Add current principal and actor resolver | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-787](ULTIMATE_TASKS.md#hapi-787) | add persistence and auditing foundation | Add JPA/auditing/transaction infrastructure | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-788](ULTIMATE_TASKS.md#hapi-788) | add event serialization foundation | Add domain event serialization support | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-789](ULTIMATE_TASKS.md#hapi-789) | add outbox persistence foundation | Add outbox entity, repository, store, and migration | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-790](ULTIMATE_TASKS.md#hapi-790) | add domain event publisher foundation | Add outbox-backed domain event publisher | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-791](ULTIMATE_TASKS.md#hapi-791) | add health indicators and metrics tags | Add health indicators and platform metric tag support | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-792](ULTIMATE_TASKS.md#hapi-792) | add tenancy context foundation | Add tenant and organization scope context placeholders | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-793](ULTIMATE_TASKS.md#hapi-793) | add platform unit and slice tests | Add platform tests | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-794](ULTIMATE_TASKS.md#hapi-794) | add platform architecture guardrail | Add ArchUnit platform boundary tests | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-795](ULTIMATE_TASKS.md#hapi-795) | finalize platform checklist | Update this roadmap with final status | P2 | — | VERIFIED | COMPLETED |
| [HAPI-820](ULTIMATE_TASKS.md#hapi-820) | add repository recovery roadmap | Establish verified recovery plan | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-821](ULTIMATE_TASKS.md#hapi-821) | remove tracked build artifacts | Remove tracked bin/ and harden ignore rules | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-823](ULTIMATE_TASKS.md#hapi-823) | restore architecture indexes and inventories | Repair missing architecture docs and document module/persistence inventories | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-824](ULTIMATE_TASKS.md#hapi-824) | align Spring Boot 4 and repository links | Align README and POM comments with live build | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-825](ULTIMATE_TASKS.md#hapi-825) | restore baseline verification tests | Restore Boot smoke test and architecture guardrails without Boot test slices | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-826](ULTIMATE_TASKS.md#hapi-826) | add Maven verification workflow | Add repeatable Java 21 compile/test/verify CI | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-827](ULTIMATE_TASKS.md#hapi-827) | finalize repository recovery checklist | Record prepared state and required final validation | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| [HAPI-828](ULTIMATE_TASKS.md#hapi-828) | add foundation stabilization roadmap | add foundation stabilization roadmap | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-830](ULTIMATE_TASKS.md#hapi-830) | register rest mapper beans | register rest mapper beans | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-831](ULTIMATE_TASKS.md#hapi-831) | register jackson object mapper bean | register jackson object mapper bean | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-836](ULTIMATE_TASKS.md#hapi-836) | add explicit development security baseline | add explicit development security baseline | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-837](ULTIMATE_TASKS.md#hapi-837) | verify application boot baseline | verify application boot baseline | P2 | — | VERIFIED | COMPLETED |
| [HAPI-838](ULTIMATE_TASKS.md#hapi-838) | finalize foundation validation checklist | finalize foundation validation checklist | P2 | — | NOT_VERIFIABLE | COMPLETED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-024 — Security, DevSecOps and Quality

### 1. Description

Make security and exact-source verification fail closed while preserving meaningful CI evidence.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `platform`, `identity`. Green CI proves its tested software scope; it does not certify deployment, calibration or universal tenant isolation.

### 4. Existing Capabilities

Full/Fast/Docs workflows, PostgreSQL shards, raw report manifests, exact-SHA OpenAPI/catalog checks and unrestricted oracle equivalence exist. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Complete CI-003 representative/negative/performance qualification and explicit adoption decision; preserve mandatory fallback, security generations and IT/OT segregation. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: HPS-023, HPS-025 Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-043](ULTIMATE_TASKS.md#hapi-043) | Measured critical path and execution analysis | Refresh current job/step/raw-class timings, runner CPU/RAM/forks, cache and scheduling context. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-044](ULTIMATE_TASKS.md#hapi-044) | Evidence-justified CI scheduling correction | Apply only a justified bounded scheduling/resource/evidence correction if measurements require one; otherwise record no code needed. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-045](ULTIMATE_TASKS.md#hapi-045) | Equivalence and negative acceptance gates | Complete representative code/security, migration and API revision equivalence, cold/warm and negative acceptance proof. | P0 | HAPI-043, HAPI-044 | PARTIAL | IN_PROGRESS |
| [HAPI-046](ULTIMATE_TASKS.md#hapi-046) | Timing, runner cost and adoption decision | Evaluate repeatable timing/cost and make the independent adoption/closure decision. | P0 | HAPI-045 | NOT_VERIFIABLE | BLOCKED |
| [HAPI-434](ULTIMATE_TASKS.md#hapi-434) | parallelize isolated test JVM execution | Run the unchanged full test suite in two reused isolated JVM forks; retain Surefire reports and count summaries for performance/coverage comparison; preserve all… | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-435](ULTIMATE_TASKS.md#hapi-435) | HPR-CI-002 historical bounded obligation | HPR-CI-002 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-436](ULTIMATE_TASKS.md#hapi-436) | Integrated closure: automate fast feedback and preserve full verification gates | Independently reconcile and accept the complete child-deliverable matrix for HPR-CI-003; child tasks own implementation. automate fast feedback and preserve full… | P0 | HAPI-046 | PARTIAL | IN_PROGRESS |
| [HAPI-437](ULTIMATE_TASKS.md#hapi-437) | secure operational workbench data exposure | Re-inspect the exact-head Workbench path, replace automatic metamodel exposure with a fail-closed approved-resource policy, replace unrestricted reflective… | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-438](ULTIMATE_TASKS.md#hapi-438) | guard workbench sensitive data exposure | Add focused regression tests covering list, detail, filter/search and generic attribute maps; assert that credential resources and passwordHash cannot be returned. | P2 | HAPI-437 | IMPLEMENTED | COMPLETED |
| [HAPI-439](ULTIMATE_TASKS.md#hapi-439) | guard generic persistence exposure | Extend architecture/security guardrails so a future generic platform reader cannot silently introduce unrestricted module-JPA exposure outside an explicitly… | P2 | HAPI-437 | IMPLEMENTED | COMPLETED |
| [HAPI-440](ULTIMATE_TASKS.md#hapi-440) | declare openapi security requirements | Add machine-readable OpenAPI security scheme and applicable security requirements for secured endpoints without weakening runtime security. | P2 | HAPI-437 | VERIFIED | COMPLETED |
| [HAPI-441](ULTIMATE_TASKS.md#hapi-441) | clarify audit actor provenance | Revalidate caller-supplied actor-header handling; ensure it is not represented as authenticated audit identity unless a verified binding exists; correct… | P2 | HAPI-437 | VERIFIED | COMPLETED |
| [HAPI-442](ULTIMATE_TASKS.md#hapi-442) | establish canonical security baseline | Complete canonical security documents: SECURITY_ARCHITECTURE.md, TRUST_BOUNDARIES.md, THREAT_MODEL.md, SECRETS_AND_CERTIFICATES.md, and INCIDENT_RESPONSE.md, using… | P2 | HAPI-437 | VERIFIED | COMPLETED |
| [HAPI-443](ULTIMATE_TASKS.md#hapi-443) | close P0 security remediation | Run full Maven verification, architecture/security tests, database/Flyway startup verification and deterministic OpenAPI generation; record exact-head evidence and… | P2 | HAPI-437 | VERIFIED | COMPLETED |
| [HAPI-444](ULTIMATE_TASKS.md#hapi-444) | verify workbench http exposure boundary | Close audit Checks 6–7 with HTTP-level Workbench regression tests using the real controller/serialization path. Cover resource discovery, list, detail, and search;… | P2 | HAPI-437 | IMPLEMENTED | COMPLETED |
| [HAPI-445](ULTIMATE_TASKS.md#hapi-445) | require exact-head openapi evidence | Close audit Check 9 as an evidence gap, not a code reimplementation. Adjust CI triggering so canonical doc/ security/roadmap changes do not silently bypass… | P2 | HAPI-444 | IMPLEMENTED | COMPLETED |
| [HAPI-446](ULTIMATE_TASKS.md#hapi-446) | map application trust transitions | Close audit Check 11 by extending SECURITY_ARCHITECTURE.md and TRUST_BOUNDARIES.md with repository-verified API → controller → application/use-case → module… | P2 | HAPI-444 | VERIFIED | COMPLETED |
| [HAPI-447](ULTIMATE_TASKS.md#hapi-447) | extend operator threat model | Close audit Check 12 by extending THREAT_MODEL.md with existing telemetry source/point registration and operator-action abuse paths. For each threat record asset,… | P2 | HAPI-446 | VERIFIED | COMPLETED |
| [HAPI-448](ULTIMATE_TASKS.md#hapi-448) | approve secret and certificate lifecycle | Obtain owner-approved lifecycle decisions required by audit Check 13: secret-store ownership, JWT signing-secret routine/emergency rotation and invalidation… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-449](ULTIMATE_TASKS.md#hapi-449) | approve incident governance model | Obtain owner-approved incident-governance decisions required by audit Check 13: incident commander/decision authority, SOC/on-call/security escalation contacts,… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-450](ULTIMATE_TASKS.md#hapi-450) | operationalize security lifecycle procedures | After HPR-P0-012 and HPR-P0-013 approvals, replace the applicable TBD sections in SECRETS_AND_CERTIFICATES.md and INCIDENT_RESPONSE.md with the approved concrete… | P2 | HAPI-448 | VERIFIED | COMPLETED |
| [HAPI-451](ULTIMATE_TASKS.md#hapi-451) | close P0 audit verification gaps | Re-run the complete P0 verification gate on the exact final remediation SHA: full Maven verification, HTTP Workbench leakage tests, architecture/security tests,… | P2 | HAPI-444 | VERIFIED | COMPLETED |
| [HAPI-458](ULTIMATE_TASKS.md#hapi-458) | add production deployment runbook | Create DEPLOYMENT_RUNBOOK.md and ENVIRONMENT_CONFIGURATION.md; require explicit production profile, secrets, PostgreSQL preparation, Flyway, startup, acceptance… | P2 | HAPI-452 | VERIFIED | COMPLETED |
| [HAPI-463](ULTIMATE_TASKS.md#hapi-463) | close P1 survivability verification | Close P1 only after all repository defects identified by the 2026-10-06 re-audit are remediated and the integrated production-equivalent exercise evidence is… | P2 | HAPI-472 | VERIFIED | COMPLETED |
| [HAPI-464](ULTIMATE_TASKS.md#hapi-464) | approve production infrastructure stack | Obtain and record the concrete production infrastructure selections required to implement P1: deployment/runtime target, traffic-distribution mechanism, PostgreSQL… | P2 | — | VERIFIED | COMPLETED |
| [HAPI-469](ULTIMATE_TASKS.md#hapi-469) | add controlled deployment pipeline | Complete former HPR-P1-009 after deployment-target approval: implement controlled release promotion, approval, exact-artifact deployment, explicit… | P2 | HAPI-458, HAPI-464 | IMPLEMENTED | COMPLETED |
| [HAPI-470](ULTIMATE_TASKS.md#hapi-470) | implement production alerting | Complete former HPR-P1-010 after observability decisions: approve concrete SLIs/SLOs/evaluation windows and numeric thresholds, configure collection/dashboards/log… | P2 | HAPI-461, HAPI-464 | IMPLEMENTED | COMPLETED |
| [HAPI-477](ULTIMATE_TASKS.md#hapi-477) | wire production observability signals | Complete production observability wiring: provide an approved authenticated scrape path for protected HidraAPI metrics without exposing them publicly, enable the… | P2 | HAPI-470 | IMPLEMENTED | COMPLETED |
| [HAPI-491](ULTIMATE_TASKS.md#hapi-491) | establish verification documentation | Create test strategy, architecture testing, database testing, API testing and requirements traceability documents tied to executable evidence. | P2 | HAPI-482 | VERIFIED | COMPLETED |
| [HAPI-492](ULTIMATE_TASKS.md#hapi-492) | validate canonical documentation | Add documentation validation for canonical links/status/index drift and deterministic OpenAPI contract checks. | P2 | HAPI-481 | IMPLEMENTED | COMPLETED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.

## HPS-025 — Deployment and Operational Readiness

### 1. Description

Preserve controlled deployment, HA/backup/recovery procedures and separate PLATFORM from SITE acceptance.

### 2. Objectives

Deliver the supported owner-controlled business outcome with explicit engineering facts, server-side authority and independently reproducible acceptance. Preserve completed work; address only the remaining source-backed obligations in this phase.

### 3. Scope and Boundaries

Owning modules/Bounded Contexts: `platform`. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action.

### 4. Existing Capabilities

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These descriptions have baseline applicability and do not substitute for each task's independent implementation status or verified test scope.

### 5. Planned Capabilities

Complete integrated synthetic PLATFORM evidence and independent closure before any separately authorized SITE commissioning/release/deployment. The task definitions retain exact limitations, proposed paths and admission gates; blocked/deferred scopes are not silently executable.

### 6. Dependencies

Cross-phase prerequisites in the approved task graph: HPS-010, HPS-016, HPS-018, HPS-024 Dependency edges authorize no deployment, new owner policy or numerical-solver selection.

### 7. Tasks

| Task ID | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|
| [HAPI-452](ULTIMATE_TASKS.md#hapi-452) | define production runtime architecture | Create doc/architecture/RUNTIME_ARCHITECTURE.md from approved production decisions only; do not invent deployment technology. | P2 | HAPI-451 | VERIFIED | COMPLETED |
| [HAPI-453](ULTIMATE_TASKS.md#hapi-453) | define application high availability model | Approve stateless multi-node runtime and load-distribution mechanism; document node state, readiness/liveness integration and failure behavior. | P2 | HAPI-452 | VERIFIED | COMPLETED |
| [HAPI-454](ULTIMATE_TASKS.md#hapi-454) | define postgres high availability model | Approve PostgreSQL replication/failover topology, replication mode, failover authority, connection behavior, maintenance behavior and ownership. | P2 | HAPI-452 | VERIFIED | COMPLETED |
| [HAPI-455](ULTIMATE_TASKS.md#hapi-455) | define disaster recovery objectives | Obtain owner-approved RTO/RPO, backup frequency/retention and WAL/PITR strategy; keep values TBD until approved. | P2 | — | VERIFIED | COMPLETED |
| [HAPI-456](ULTIMATE_TASKS.md#hapi-456) | add disaster recovery runbook | Create doc/operations/DISASTER_RECOVERY_RUNBOOK.md with declaration authority, recovery roles, dependency order, restore steps and acceptance checks based on… | P2 | HAPI-455 | VERIFIED | COMPLETED |
| [HAPI-457](ULTIMATE_TASKS.md#hapi-457) | add high availability architecture | Create doc/operations/HIGH_AVAILABILITY_ARCHITECTURE.md covering approved application/database redundancy, failover, connection behavior and maintenance failover. | P2 | HAPI-453 | VERIFIED | COMPLETED |
| [HAPI-459](ULTIMATE_TASKS.md#hapi-459) | enforce openapi compatibility | Add real OpenAPI compatibility/breaking-change validation; generation/upload alone is not compatibility validation. | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-460](ULTIMATE_TASKS.md#hapi-460) | add controlled deployment pipeline | After deployment target approval, implement controlled deployment automation, promotion, approval, post-deployment health verification and rollback; document in… | P2 | HAPI-458 | IMPLEMENTED | COMPLETED |
| [HAPI-461](ULTIMATE_TASKS.md#hapi-461) | establish observability operating model | Create OBSERVABILITY_AND_SRE.md and implement approved alerting from existing Actuator/Prometheus signals; do not invent SLOs. | P2 | HAPI-452 | VERIFIED | COMPLETED |
| [HAPI-462](ULTIMATE_TASKS.md#hapi-462) | add database operations runbook | Create DATABASE_OPERATIONS_RUNBOOK.md covering Flyway, backup/restore, connection exhaustion, failover, maintenance and migration failures. | P2 | HAPI-454 | VERIFIED | COMPLETED |
| [HAPI-465](ULTIMATE_TASKS.md#hapi-465) | enforce production startup profile | Enforce production-context startup safety so an omitted or wrong Spring profile cannot silently fall back to dev/default behavior in an approved production… | P2 | HAPI-464 | VERIFIED | COMPLETED |
| [HAPI-466](ULTIMATE_TASKS.md#hapi-466) | implement application high availability | Implement the approved two-node HidraAPI runtime on the selected deployment/traffic platform, readiness-based admission/removal, graceful drain/replacement, and… | P2 | HAPI-453, HAPI-457, HAPI-464 | VERIFIED | COMPLETED |
| [HAPI-467](ULTIMATE_TASKS.md#hapi-467) | implement postgres failover recovery | Implement the selected PostgreSQL primary/standby replication, promotion/fencing and stable-endpoint mechanism; define explicit Hikari/JDBC recovery settings… | P2 | HAPI-454, HAPI-462, HAPI-464 | IMPLEMENTED | COMPLETED |
| [HAPI-468](ULTIMATE_TASKS.md#hapi-468) | implement backup and pitr operations | Bind the approved DR model to the selected backup and storage implementation with executable backup, WAL archive, restore, recovery-target/PITR, service-control… | P2 | HAPI-455, HAPI-462, HAPI-464 | IMPLEMENTED | COMPLETED |
| [HAPI-471](ULTIMATE_TASKS.md#hapi-471) | operationalize database procedures | Make the database runbook executable for the selected PostgreSQL/backup/HA stack: add reviewed maintenance and inspection commands for sessions, locks,… | P2 | HAPI-462, HAPI-464, HAPI-467 | VERIFIED | COMPLETED |
| [HAPI-472](ULTIMATE_TASKS.md#hapi-472) | backup/PITR binding evidence | Infra/Test/Doc | P2 | — | NOT_VERIFIABLE | COMPLETED |
| [HAPI-473](ULTIMATE_TASKS.md#hapi-473) | align patroni health checks with tls | Make HAProxy primary health checks compatible with Patroni REST mutual TLS and required client authentication; validate the rendered HAProxy/Patroni transport… | P2 | HAPI-467 | IMPLEMENTED | COMPLETED |
| [HAPI-474](ULTIMATE_TASKS.md#hapi-474) | strengthen application ha exercise | Strengthen the two-node HA exercise so it proves both nodes are initially admitted, continuously probes a representative authenticated REST operation during each… | P2 | HAPI-466 | IMPLEMENTED | COMPLETED |
| [HAPI-475](ULTIMATE_TASKS.md#hapi-475) | verify application failover recovery | Extend the database failover exercise to prove HidraAPI-managed Hikari connection replacement through the stable endpoint using authenticated database-backed… | P2 | HAPI-473 | IMPLEMENTED | COMPLETED |
| [HAPI-476](ULTIMATE_TASKS.md#hapi-476) | enforce availability preserving rollout | Harden controlled rollout so the surviving application node is verified before draining the target, drain completion/active-request safety is explicitly checked… | P2 | HAPI-469, HAPI-474 | IMPLEMENTED | COMPLETED |
| [HAPI-478](ULTIMATE_TASKS.md#hapi-478) | complete operational health inspections | Complete database health inspection coverage with PostgreSQL index validity/readiness and a reviewed bloat/space-risk method that does not assume unapproved… | P2 | HAPI-471 | IMPLEMENTED | COMPLETED |
| [HAPI-479](ULTIMATE_TASKS.md#hapi-479) | enforce p1 retention controls | Enforce the approved P1 retention controls in executable configuration: distinguish bootstrap from mature monthly coverage and verify distinct monthly repo2… | P2 | HAPI-472, HAPI-477 | IMPLEMENTED | COMPLETED |
| [HAPI-480](ULTIMATE_TASKS.md#hapi-480) | execute p1 survivability exercises | Execute and retain one governed production-equivalent P1 evidence campaign after HPR-P1-021..028: scheduled backup/WAL operation; intentional current-schema PITR… | P2 | HAPI-472 | IMPLEMENTED | COMPLETED |
| [HAPI-543](ULTIMATE_TASKS.md#hapi-543) | audit engineering foundation acceptance | audit engineering foundation acceptance | P2 | HAPI-621 | NOT_VERIFIABLE | BLOCKED |
| [HAPI-621](ULTIMATE_TASKS.md#hapi-621) | Integrated closure: four integrated PLATFORM qualification packages | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-058; child tasks own implementation. four integrated PLATFORM qualification… | P2 | HAPI-625 | NOT_VERIFIABLE | BLOCKED |
| [HAPI-622](ULTIMATE_TASKS.md#hapi-622) | Versioned PLATFORM fixture and criterion matrix | Freeze versioned supported fixture/case/contract/namespace matrix and exact test scope. | P2 | HAPI-604, HAPI-610, HAPI-616 | NOT_VERIFIABLE | BLOCKED |
| [HAPI-623](ULTIMATE_TASKS.md#hapi-623) | Operator/API/security/history end-to-end qualification | Actual operator/API/security/history end-to-end software qualification. | P2 | HAPI-622 | NOT_VERIFIABLE | BLOCKED |
| [HAPI-624](ULTIMATE_TASKS.md#hapi-624) | Integrated concurrency, replay and operational evidence | Integrated concurrency/replay/reference/rollback and observation/action qualification. | P2 | HAPI-623 | NOT_VERIFIABLE | BLOCKED |
| [HAPI-625](ULTIMATE_TASKS.md#hapi-625) | Exact-source PLATFORM evidence and disposition | Exact-SHA full database/contract/dictionary/24-module evidence and PLATFORM decision. | P2 | HAPI-624 | NOT_VERIFIABLE | BLOCKED |
| [HAPI-626](ULTIMATE_TASKS.md#hapi-626) | prepare 0.6.0 platform milestone | Align the project version to 0.6.0 and record the completed platform milestone; publish only the five-path release-alignment scope below; require successful… | P2 | — | IMPLEMENTED | COMPLETED |
| [HAPI-627](ULTIMATE_TASKS.md#hapi-627) | start 0.7.0 development | Record real v0.6.0 publication and immutable target; advance only the Maven project version to 0.7.0-SNAPSHOT; refresh release/development traceability within the… | P2 | HAPI-626 | IMPLEMENTED | COMPLETED |

### 8. Phase Completion Criteria

Every supported in-scope task has its independently established technical acceptance and consistent status; no unfinished active obligation is silently omitted. Actual owner/API/domain/persistence/security behavior and required concurrency/rollback/history evidence pass on the exact accepted source. Deferred or superseded tasks retain explicit disposition and do not become falsely completed. Documentation-only completion does not prove the associated application feature. PLATFORM acceptance is separate from owner calibration, SITE qualification and release/deployment authorization.
